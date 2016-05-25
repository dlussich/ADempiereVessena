package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.AWindow;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

/**
 * OpenUp. Nicolas Garcia. 08/09/2011. Issue #872. Se crea metodo el cual recibe
 * la informacion y la procesa, se realiza pensando tanto en la carga de excel
 * como en una posible sincronizacion entre las bases.
 * actualizado para caso issue #887
**/
public class POrderLogic {

	private Collection<POrderHdr> pedidos = null;
	private Properties ctx = null;
	static int wareHouseID = 1000013;
	static int salesRepID = 0;
	String ms = "";
	private AWindow Window;
	CLogger log;
	String trxName = "";

	public static String run(Properties ctx, Collection<POrderHdr> pedidos, int salesRep_ID, AWindow window, CLogger log, String trxName) {

		return new POrderLogic(ctx, pedidos, salesRep_ID, window, log, trxName).loadOrders();

	}

	private POrderLogic(Properties ctx, Collection<POrderHdr> pedidos, int salesRep_ID, AWindow window, CLogger log, String TrxName) {
		this.ctx = ctx;
		this.pedidos = pedidos;
		POrderLogic.salesRepID = salesRep_ID;
		this.Window = window;
		this.log = log;
		this.trxName = TrxName;

	}

	private String loadOrders() {

		//int linesMal = 0;
		//int linesOK = 0;

		// recorro los pedidos
		for (POrderHdr hdr : pedidos) {

			if (hdr == null) continue;

			try {

				boolean una = false;
				boolean guardar = true;

				MBPartnerLocation partnerLocation = this.getPartnerLocator(hdr);

				MOrder order = LoadHeaderFormLine(partnerLocation, hdr, salesRepID, trxName);
				order.setDescription("Cargadas por sistema de carga masiva");

				order.saveEx(trxName);

				//int auxLinesOK = 0;

				int totalRowCount = hdr.getLineas().size(), rowCount = 0;

				for (POrderLine line : hdr.getLineas()) {

					// Mensaje
					rowCount++;
					this.Window.setWaitingMessage("Generando orden " + order.getDocumentNo() + " Linea " + rowCount + " de " + totalRowCount);

					MProduct product = getProduct(line);
					MUOM uom = new MUOM(ctx, getUOMID(line), null);

					if (product != null && uom != null) {

						try {

							if (uom.get_ID() <= 0) {
								throw new Exception("Para el producto " + product.getValue() + " no esta definida la unidad de medida "
										+ line.getUnidadMedida());
							}
							// Pregunto si existe en base de datos la
							// combinacion
							// cblocation, order y producto, c_uom

							String mensaje = this.claveLineaOK(hdr.getPoReference(), partnerLocation.getC_BPartner_Location_ID(), product.get_ID(), uom
									.get_ID());

							if (mensaje.equals("")) {

								// Guardo linea
								MOrderLine oline = this.LoadLine(order, trxName, line, product.get_ID(), uom.get_ID());

								if (oline != null) {
									oline.saveEx(trxName);
									//auxLinesOK += 1;

									// Rollback si no tiene por lo menos una
									// linea
									una = true;
								} else {
									guardar = false;
								}

							} else {
								ms += "\n Ya se ingreso el producto " + product.getValue() + " en la orden " + mensaje + " Con referencia "
										+ hdr.getPoReference();
								//linesMal += -1;
								guardar = false;
							}

						} catch (Exception e) {

							ms += " Producto " + product.get_ID() + " " + e.getMessage();
							guardar = false;
						}
					}

				} // fin for lineas

				if (!(una && guardar)) {

					Trx trans = Trx.get(trxName, false);
					trans.rollback(true);
					throw new Exception("No se pudo crear la orden con referencia " + hdr.getPoReference() + "\n Detalles: " + ms);

				} else {

					// Completo la orden
					if (!order.processIt(DocAction.ACTION_Complete)) {

						throw new AdempiereException("No se pudo completar Pedido : " + order.getDocumentNo() + "\n" + order.getProcessMsg());
					}

					// sumo lineas ok
					//linesOK += auxLinesOK;
					order.saveEx(trxName);

					log.log(Level.INFO, "Se creo Orden de compra " + order.getDocumentNo());
					System.out.println("Se creo Orden de compra " + order.getDocumentNo());
				}
			} catch (Exception e) {

				throw new AdempiereException(e.getMessage());

			}

		} // end for

		return ms;
	}

	private int getUOMID(POrderLine line) {

		String sql = "SELECT c_uom_id FROM c_uom WHERE LOWER(name)=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			String valor = line.getUnidadMedida().toLowerCase();

			if (valor.equalsIgnoreCase("unidad")) {
				valor = "uno";
			}

			if (valor.equalsIgnoreCase("cx4")) {
				valor = "cx04";
			}

			if (valor.equalsIgnoreCase("fx8")) {
				valor = "fx08";
			}

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, valor);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("c_uom_id");
			} else {
				return -1;
			}

		} catch (Exception e) {
			return -1;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	private MProduct getProduct(POrderLine line) throws Exception {

		MProduct product = null;

		String sql = "SELECT * FROM m_product  WHERE (trim(value)=? OR trim(upc)=?)";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Selecciono el Producto
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, line.getValueProducto());
			pstmt.setString(2, line.getValueProducto());
			rs = pstmt.executeQuery();

			// Selecciono el Producto, Si existe traer el producto, Se controla
			// si por el upc se trae repetidos.
			int cont = 0;

			while (rs.next()) {
				// Si hay dos registros con ese value o upc tira error
				if (cont == 0) {
					product = new MProduct(ctx, rs, null);
				}
				cont += 1;
			}

			// Si no se encontro producto por value o upc y no se encontro
			// productos repetidos
			if (product == null) {
				DB.close(rs, pstmt);
				// Se busca upc2
				sql = "SELECT * FROM m_product  WHERE trim(upc2)=?";
				rs = null;
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setString(1, line.getValueProducto());
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// Si hay dos registros con ese upc2 tira error
					if (cont == 0) {
						product = new MProduct(ctx, rs, null);
					}
					cont += 1;
				}

			}
			if (cont > 1) return null;

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (product == null) {
			ms += "Producto " + "'" + line.getValueProducto() + "'" + " NO EXISTE -- La línea no se creó \n";
		}
		return product;
	}

	private MBPartnerLocation getPartnerLocator(POrderHdr hdr) throws Exception {

		MBPartnerLocation model = null; // Retorna null si no encuentra nada

		String sql = "SELECT l.c_bpartner_location_id FROM  c_bpartner_location l " + "LEFT JOIN c_bpartner b ON b.c_bpartner_id=l.C_bpartner_id "
				+ "WHERE (trim(b.value)=? OR trim(l.uy_gln)=? )";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Create lines from interfase
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, hdr.getGln());
			pstmt.setString(2, hdr.getGln());
			rs = pstmt.executeQuery();

			// Devuelvo solo lo primero que encuentro.
			if (rs.next()) {
				model = new MBPartnerLocation(ctx, rs.getInt("c_bpartner_location_id"), null);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (model == null) {
			ms += "Proveedor " + "'" + hdr.getGln() + "'" + " NO EXISTE -- La línea no se creó \n";
		}

		return model;
	}

	private String claveLineaOK(String porReference, int c_bpartner_location_id, int m_product_id, int c_uom_id) throws Exception {
		// Se crea metodo para verificar si la clave esta ingresada actualmente
		// en el sistema.
		String salida = "";
		String sql = "SELECT o.documentno FROM c_order o LEFT JOIN c_orderline l ON l.c_order_id=o.c_order_id "
				+ "WHERE trim(o.poreference)=? AND o.c_bpartner_location_id=? AND l.m_product_id=? AND l.c_uom_id=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, porReference);
			pstmt.setInt(2, c_bpartner_location_id);
			pstmt.setInt(3, m_product_id);
			pstmt.setInt(4, c_uom_id);
			rs = pstmt.executeQuery();

			// En caso de que devuelva algo, mostrara los pedidos para que el
			// usuario pueda revisar
			while (rs.next()) {
				salida += rs.getString("documentno") + " ";
			}

		} catch (Exception e) {
			// log.info(e.getMessage());
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return salida;
	}

	private MOrder LoadHeaderFormLine(MBPartnerLocation location, POrderHdr hdr, int salesRepID, String trxNameAux) throws Exception {

		// Return object
		MOrder header = null;

		// Default values for partner
		MBPartner partner = null;

		if (location != null) {
			partner = new MBPartner(Env.getCtx(), location.getC_BPartner_ID(), null);

			// Save the header and line only when a partner was get
			if ((partner != null) && (location != null)) {

				if (hdr.getFechaOrden() != null) {

					// Create a new header, ID 0, set fields and save
					header = new MOrder(Env.getCtx(), 0, trxNameAux);// TRANSACCION

					// Reference order
					header.setPOReference(hdr.getPoReference());

					// Case
					header.setIsSOTrx(false);

					// Set partner
					header.setC_BPartner_ID(partner.getC_BPartner_ID());
					header.setC_BPartner_Location_ID(location.getC_BPartner_Location_ID());

					// Set document status and action
					header.setDocStatus(MOrder.DOCSTATUS_Drafted);
					header.setDocAction(MOrder.DOCACTION_Complete);

					// Set c_doctype_id
					String sql = "SELECT MAX(c_doctype_id) FROM c_doctype WHERE docbasetype='POO' AND docsubtypeso is null";
					int doc = DB.getSQLValue(trxNameAux, sql);
					header.setC_DocTypeTarget_ID(doc);
					header.setC_DocType_ID(doc);

					// Set dates
					header.setDateOrdered(hdr.getFechaOrden());
					header.setDateAcct(hdr.getFechaOrden());
					header.setDatePromised(hdr.getFechaEntrega());
					header.setSalesRep_ID(salesRepID);
					header.setUY_ReservaStock(false);

					// Lista de precio
					// Si no encuentro uso la estander

					header.setM_PriceList_ID(partner.getM_PriceList_ID());

					// Warehouse
					header.setM_Warehouse_ID(wareHouseID);

					// Set flags
					header.setPosted(false);
					header.setProcessed(false);

				}
			}
		}

		return (header);
	}


	private MOrderLine LoadLine(MOrder header, String trxNameAux, POrderLine lineAux, int productID, int uomID) throws Exception {

		MOrderLine line = null;
		if (header != null) {

			// Create a new line based on the header, set fields and save
			line = new MOrderLine(header);

			line.setM_Product_ID(productID);
			line.setM_Warehouse_ID(wareHouseID);// FIXME ?? defaultWarehouseID

			// Get the default factor of the product and get the cant to avoid
			// recordset get
			BigDecimal factor = MUOMConversion.getProductRateFrom(Env.getCtx(), productID, uomID);

			// Controla que cantidades negativas o 0
			if (lineAux.getQtyEntered() != null) {

				// Si no hay factor -> ERROR
				if (factor == null) {

					ms += "No se encontró factor multiplicador -- La línea no se creó \n";
					line = null;

				} else {
					// Seteo el factor
					line.setC_UOM_ID(uomID);
					line.setQtyEntered(lineAux.getQtyEntered());
					line.setQtyOrdered(lineAux.getQtyEntered().multiply(factor));

					// Set prices
					line.setFormatInfo(null);

				}

			}
		}

		return line;
	}
}
