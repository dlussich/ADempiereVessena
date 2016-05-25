package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.AWindow;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.LogicLoadPODB;

public class MLoadPOrderDBFilter extends X_UY_LoadPOrderDBFilter implements DocAction {

	private static final long serialVersionUID = 206041930438444209L;

	// ------------Configuracion--------------

	private HashMap<String, POrderHdr> hdrList = new HashMap<String, POrderHdr>();
	private String docNoInvoiceVessena = "'0'";

	private static String valueVessenaEnAlianzur = "10101";
	private static String idVessenaEnAlianzur = "1012661";
	private static String idAlianzurEnVessena = "1010702";
	private static int idComprador = 1001353;

	// ------------Fin--------------

	public AWindow Window = null;

	public MLoadPOrderDBFilter(Properties ctx, int UY_LoadPOrderDBFilter_ID, String trxName) {
		super(ctx, UY_LoadPOrderDBFilter_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MLoadPOrderDBFilter(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String prepareIt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processIt(String action) throws Exception {
		// Proceso segun accion

		if (action.equalsIgnoreCase(DocumentEngine.ACTION_Prepare))

		return this.leerOrdenesVenta();

		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete)) {

			String ms = this.generarOrdenesCompra();

			if (ms == null || ms.equalsIgnoreCase("")) {

				actualizarProcesados();
				generarRecepcionesMateriales();

				return true;

			} else {
				throw new Exception(ms);
			}

		} else
			return true;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Este metodo selecciona las facturas aptas en vessena y trae informacion a alianzur para poder seleccionar la deseada 
	 * para luego procesarla
	 * @return
	 * @throws Exception
	 * @author  Nicolas Garcia
	 * Fecha : 13/10/2011
	 */
	public boolean leerOrdenesVenta() throws Exception {

		Connection con = null;

		try {

			// Obtengo coneccion
			con = LogicLoadPODB.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT c_invoice_id,documentno, dateinvoiced ,totallines FROM c_invoice " + this.getWhereLeer() + " ORDER BY documentno,dateinvoiced";

			ResultSet rs = stmt.executeQuery(sql);

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			while (rs.next()) {

				// Mensaje
				rowCount++;
				this.Window.setWaitingMessage("Cargando linea " + rowCount + " de " + totalRowCount);

				MLoadPOrderDBLine line = new MLoadPOrderDBLine(getCtx(), 0, this.get_TrxName());

				line.setUY_LoadPOrderDBFilter_ID(this.get_ID());
				line.setidinvoice(rs.getInt("c_invoice_id"));
				line.setDocumentNoInvoice(rs.getString("documentno"));
				line.setDateInvoiced(rs.getTimestamp("dateinvoiced"));
				line.setTotalLines(rs.getBigDecimal("totallines"));
				line.setuy_procesar(false);
				line.saveEx();

			}

			this.setDocStatus("WC");
			this.setDocAction(DOCACTION_Complete);


		} catch (Exception e) {

			throw e;

		} finally {
			if (con != null) con.close();

		}

		return true;

	}

	private String getWhereLeer() {

		String salida = "WHERE c_invoice.c_bpartner_id =" + idAlianzurEnVessena
				+ " AND c_invoice.docstatus in('CO') AND c_invoice.issotrx ='Y' AND c_invoice.uy_pogenerate ='N' ";

		if (this.getDocumentNoInvoice() != null && !this.getDocumentNoInvoice().equalsIgnoreCase("")) {
			salida += " AND lower(documentno)='" + this.getDocumentNoInvoice().toLowerCase() + "' ";
		}

		if (this.getDateFrom() != null) {
			salida += " AND dateinvoiced>='" + this.getDateFrom() + "' ";
		}

		if (this.getDateTo() != null) {
			salida += " AND dateinvoiced<='" + this.getDateTo() + "' ";
		}

		return salida;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Proceso que genera las ordenes de compra a partir de lo seleccionado por el usuario.
	 * @return
	 * @throws Exception
	 * @author  Nicolas Garcia
	 * Fecha : 13/10/2011
	 */
	private String generarOrdenesCompra() throws Exception {

		Connection con = null;
		try {

			// Obtengo coneccion (Vessena)
			con = LogicLoadPODB.getConnection();

			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT c_invoice.documentno, c_invoice.dateinvoiced as  FecFact,NOW() as FecEntrega,m_product.value, c_invoiceline.qtyentered,c_uom.name "
					+ "FROM c_invoice "
					+ "JOIN c_invoiceline ON c_invoiceline.c_invoice_id=c_invoice.c_invoice_id "
					+ "JOIN m_product ON m_product.m_product_id=c_invoiceline.m_product_id "
					+ "JOIN c_uom ON c_uom.c_uom_id=c_invoiceline.c_uom_id "
					+ "WHERE c_invoice.c_bpartner_id ="
					+ idAlianzurEnVessena
					+ " AND c_invoice.docstatus in('CO') AND c_invoice.issotrx ='Y' "
					+ "AND c_invoice.uy_pogenerate ='N' AND c_invoice.c_invoice_id in(" + this.getWhereGenerar() + ")" + "ORDER BY documentno,value";

			ResultSet rs = stmt.executeQuery(sql);

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			POrderLine linea = null;
			POrderHdr head = null;

			// Recorro los resultado y cargo los mismos en las clases POrderLine
			// y POrderHdr para luego enviarselos a la clase POrderLogic.run(...
			// quien es la que tiene la logica para generar las ordenes de
			// compra etc
			while (rs.next()) {

				// Mensaje
				rowCount++;
				this.Window.setWaitingMessage("Leyendo linea " + rowCount + " de " + totalRowCount);

				linea = new POrderLine();
				head = new POrderHdr();

				// 1-Nro Orden
				head.setPoReference(rs.getString("documentno"));

				// 2-VALUE
				head.setGln(valueVessenaEnAlianzur);

				// 3-Fecha de Orden
				head.setFechaOrden(rs.getTimestamp("FecFact"));

				// 4-Fecha de Entrega
				head.setFechaEntrega(rs.getTimestamp("FecEntrega"));

				// 5-Producto
				linea.setValueProducto(rs.getString("value"));

				// 6-Cantidad
				linea.setQtyEntered(rs.getBigDecimal("qtyentered"));

				// 7-UOM
				linea.setUnidadMedida(rs.getString("name"));

				// Si contiene ya un cabezal
				if (hdrList.containsKey(head.claveCabezal())) {

					hdrList.get(head.claveCabezal()).agregarLinea(linea);

				} else {

					// Si no se crea y agrega
					hdrList.put(head.claveCabezal(), head);
					hdrList.get(head.claveCabezal()).agregarLinea(linea);
				}

			}
		} catch (Exception e) {

			throw e;
		} finally {

			if (con != null) con.close();
		}

		return POrderLogic.run(Env.getCtx(), hdrList.values(), idComprador, Window, log, get_TrxName());
	}

	private String getWhereGenerar() {

		String salida = "0";

		String sql = "SELECT idinvoice from uy_loadporderdbline WHERE uy_loadporderdbfilter_id  =" + this.get_ID() + " AND uy_procesar='Y'";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				salida += "," + rs.getString("idinvoice");
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (salida.equalsIgnoreCase("0")) {
			throw new AdempiereException("No hay lineas para procesar");
		}
		return salida;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Funcion que actualiza en vessena, marcando que ya fueron procesadas.
	 * @return
	 * @throws Exception
	 * @author  Nicolas Garcia
	 * Fecha : 13/10/2011
	 */
	private boolean actualizarProcesados() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {

			sql = " SELECT idinvoice,documentnoinvoice from uy_loadporderdbline WHERE uy_loadporderdbfilter_id  =" + this.get_ID()
					+ " AND uy_procesar='Y' AND "
					+ "EXISTS (SELECT c_order.c_order_id FROM c_order WHERE c_order.poreference=uy_loadporderdbline.documentnoinvoice "
					+ "AND c_order.c_bpartner_id=" + idVessenaEnAlianzur + ")";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			con = LogicLoadPODB.getConnection();
			con.setAutoCommit(false);

			while (rs.next()) {

				this.docNoInvoiceVessena += ",'" + rs.getInt("documentnoinvoice") + "'";
				this.actualizar(rs.getInt("idinvoice"), con);

			}

			con.commit();

			this.setDocStatus("CO");
			this.setDocAction(DOCACTION_None);
			this.setProcessed(true);


		} catch (Exception e) {

			con.rollback();
			throw e;

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;

			if (con != null) con.close();
		}

		return true;
	}

	// Es llamado desde la funcion actualizarProcesados()
	private void actualizar(int invoiceID, Connection con) throws Exception {

		try {

			Statement stmt = con.createStatement();

			String sql = "UPDATE c_invoice set uy_pogenerate ='Y'WHERE c_invoice_id=" + invoiceID;

			stmt.executeUpdate(sql);

		} catch (Exception e) {

			throw e;
		}

	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que genera la recepcion de compras para las ordenes de compra generadas por este proceso.
	 * @author  Nicolas Garcia
	 * Fecha : 13/10/2011
	 */
	private void generarRecepcionesMateriales() {

		this.setMemo("Detalles:\n");

		String sql = "SELECT * FROM c_order WHERE c_order.poreference in(" + docNoInvoiceVessena + ")" + "AND c_order.c_bpartner_id=" + idVessenaEnAlianzur
				+ " AND c_doctype_id =(SELECT MAX(c_doctype_id) FROM c_doctype WHERE docbasetype='POO' AND docsubtypeso is null)";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, get_TrxName());
			rs = pstmt.executeQuery();
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			while (rs.next()) {

				// Mensaje
				rowCount++;
				this.Window.setWaitingMessage("Generando Recepcion " + rowCount + " de " + totalRowCount);

				this.addRecepcionMaterial(new MOrder(getCtx(), rs, get_TrxName()));

			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	// Es llamado desde el metodo generarRecepcionesMateriales() para
	// simplificar codigo.
	private void addRecepcionMaterial(MOrder oCompra) throws Exception {

		try {
			int docTypeID = 1000014;
			Timestamp ahora = new Timestamp(System.currentTimeMillis());
			MInOut inCompra = new MInOut(getCtx(), 0, get_TrxName());
			inCompra.setDescription("Generado automaticamente. Referencia factura proveedor:" + oCompra.getPOReference());

			inCompra.setIsSOTrx(false);
			inCompra.setDocAction(DOCACTION_Complete);
			inCompra.setDocStatus("DR");
			inCompra.setPosted(true);
			inCompra.setC_DocType_ID(docTypeID);
			inCompra.setC_Order_ID(oCompra.get_ID());
			inCompra.setMovementType("V+");
			inCompra.setMovementDate(ahora);
			inCompra.setDateAcct(ahora);
			inCompra.setC_BPartner_ID(oCompra.getC_BPartner_ID());
			inCompra.setC_BPartner_Location_ID(oCompra.getC_BPartner_Location_ID());
			inCompra.setM_Warehouse_ID(oCompra.getM_Warehouse_ID());
			inCompra.setDeliveryRule("A");
			inCompra.setFreightCostRule("I");
			inCompra.setDeliveryViaRule("P");
			inCompra.setSalesRep_ID(oCompra.getSalesRep_ID());
			inCompra.setPOReference(oCompra.getPOReference());

			int stkStatus = MStockStatus.getStatusApprovedID(null);
			MOrderLine[] linesOrder = oCompra.getLines();

			inCompra.saveEx();
			// Lines
			for (int i = 0; i < linesOrder.length; i++) {

				MOrderLine oLine = linesOrder[i];
				MInOutLine inLine = new MInOutLine(inCompra);

				int wareHouseID = MProduct.getDefaultWareHouse(oLine.getM_Product_ID());
				int locatorID = DB.getSQLValue(null, "SELECT m_locator_id FROM m_locator  WHERE m_warehouse_id =" + wareHouseID);

				inLine.setC_OrderLine_ID(oLine.get_ID());
				inLine.setM_Product_ID(oLine.getM_Product_ID());
				inLine.setC_UOM_ID(oLine.getC_UOM_ID());
				inLine.setMovementQty(oLine.getQtyOrdered());
				inLine.setIsInvoiced(false);
				inLine.setIsDescription(false);
				inLine.setQtyEntered(oLine.getQtyEntered());
				inLine.setM_Warehouse_ID(wareHouseID);
				inLine.setM_Locator_ID(locatorID);
				inLine.setUY_StockStatus_ID(stkStatus);
				inLine.save(get_TrxName());

			}

			inCompra.saveEx();

			String mensaje = "Se genero 'Orden de Compra' " + oCompra.getDocumentNo() + " y 'Recepcion de Compra' " + inCompra.getDocumentNo()
					+ " a partir de la 'Factura de Proveedor' " + oCompra.getPOReference() + "\n";

			log.log(Level.INFO, mensaje);
			this.setMemo(this.getMemo()+mensaje);

		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
}
