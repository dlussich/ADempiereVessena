/**
 * MReserveFilter.java 08/12/2010
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.AWindow;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MReserveFilter
 * Descripcion :
 * @author Gabriel Vila, Nicolas Garcia #821
 * Fecha : 08/12/2010, 01/11/2011
 */
public class MReserveFilter extends X_UY_Reserve_Filter implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2970296612624964467L;
	private String processMsg = null;


	//private long inicio = 0;
	//private static final int RESERVA_DOCTYPE_ID = 1000056;

	private static final int UNIDAD_MEDIDA_DEFAULT = 100;

	private HashMap<Integer, BigDecimal> StokDisponible = new HashMap<Integer, BigDecimal>();
	HashMap<Integer, BigDecimal> StokDisponibleControFinal = new HashMap<Integer, BigDecimal>();
	private HashMap<Integer, MReserveProduct> ProdAcumulado = new HashMap<Integer, MReserveProduct>();

	private ArrayList<MReserveDetail> listaMReserveDetail = new ArrayList<MReserveDetail>();

	private AWindow window = null;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_Reserve_Filter_ID
	 * @param trxName
	 */
	public MReserveFilter(Properties ctx, int UY_Reserve_Filter_ID, String trxName) {
		super(ctx, UY_Reserve_Filter_ID, trxName);
		// TODO Auto-generated constructor stub

	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MReserveFilter(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentNo()
	 */
	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		// Proceso segun accion
		if (action.equalsIgnoreCase(DocumentEngine.ACTION_Prepare)) return this.sugerirReservaInicial();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete)) return this.generarReservaFinal();
		else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Se genera la reserva sugerida en base a los pedidos obtenidos considerando filtros.
	 * @return
	 * @author  Gabriel Vila , Nicolas Garcia .Issue #821.
	 * Fecha : 09/12/2010, 27/10/2011
	 */
	private boolean sugerirReservaInicial() {
		
		//inicio = System.currentTimeMillis();

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean result = true;

		try {
			log.info("Iniciando sugerencia de reserva comercial");

			this.showText("Iniciando Reserva");

			// Elimino posibles intentos previos de reserva para esta
			// transaccion
			DB.executeUpdateEx("DELETE FROM uy_reserve_detail " + " WHERE uy_reserve_product_id IN " + " (SELECT uy_reserve_product_id from uy_reserve_product "
					+ " WHERE uy_reserve_filter_id=" + this.getUY_Reserve_Filter_ID() + ")", get_TrxName());
			DB.executeUpdateEx("DELETE FROM uy_reserve_product " + " WHERE uy_reserve_filter_id=" + this.getUY_Reserve_Filter_ID(), get_TrxName());

			// Obtengo numero de transaccion
			int nroTrans = 0;
			String docNroTrans = MSequence.getDocumentNo(this.getAD_Client_ID(), "NumeroTransaccion", null);
			if (docNroTrans != null){
				nroTrans = Integer.parseInt(docNroTrans);	
			}
			
			// Condicion de Filtros
			String whereFiltros = "", whereZonas = "";

			// Si tengo pedidos de filtros en la tabla uy_reserve_orders
			// considero esos. Sino tengo que tomar filtros normales.
			if (this.hayFiltroPedidos()) {
				whereFiltros = "  AND hdr.c_order_id IN " + " (SELECT c_order_id FROM uy_reserve_orders " + " WHERE uy_reserve_filter_id="
						+ this.getUY_Reserve_Filter_ID() + ") ";
				if (this.getM_Product_ID() > 0) whereFiltros += " AND line.m_product_id =" + this.getM_Product_ID();
			} else {

				if (this.getuy_dateordered_from() != null) whereFiltros += " AND hdr.dateordered >='" + this.getuy_dateordered_from() + "'";
				if (this.getuy_dateordered_to() != null) whereFiltros += " AND hdr.dateordered <='" + this.getuy_dateordered_to() + "'";

				if (this.getM_Warehouse_ID() > 0) whereFiltros += " AND hdr.m_warehouse_id =" + this.getM_Warehouse_ID();
				if (this.getM_Product_ID() > 0) whereFiltros += " AND line.m_product_id =" + this.getM_Product_ID();
				if (this.getC_BPartner_ID() > 0) whereFiltros += " AND hdr.c_bpartner_id =" + this.getC_BPartner_ID();
				if (this.getUY_CanalVentas_ID() > 0) whereFiltros += " AND cli.uy_canalventas_id =" + this.getUY_CanalVentas_ID();
				if (this.getC_SalesRegion_ID() > 0) whereFiltros += " AND cliloc.c_salesregion_id =" + this.getC_SalesRegion_ID();
				if (this.getuy_datepromised_from() != null) whereFiltros += " AND hdr.datepromised >='" + this.getuy_datepromised_from() + "'";
				if (this.getuy_datepromised_to() != null) whereFiltros += " AND hdr.datepromised <='" + this.getuy_datepromised_to() + "'";
				if (this.getC_Order_ID() > 0) whereFiltros += " AND hdr.c_order_id =" + this.getC_Order_ID();

				// Zonas de Entrega
				if (this.getuy_zonareparto_filtro1() > 0) {
					whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + ")";
				}

				if (this.getuy_zonareparto_filtro2() > 0) {
					if (!whereZonas.equalsIgnoreCase("")) whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + ","
							+ getuy_zonareparto_filtro2() + ")";
					else
						whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro2() + ")";
				}

				if (this.getuy_zonareparto_filtro3() > 0) {
					if (whereZonas.equalsIgnoreCase("")) whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro3() + ")";
					else {
						if ((this.getuy_zonareparto_filtro1() > 0) && (this.getuy_zonareparto_filtro2() > 0)) {
							whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + "," + getuy_zonareparto_filtro2() + ","
									+ getuy_zonareparto_filtro3() + ")";
						} else if (this.getuy_zonareparto_filtro1() > 0) {
							whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + "," + getuy_zonareparto_filtro3() + ")";

						} else if (this.getuy_zonareparto_filtro2() > 0) {
							whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro2() + "," + getuy_zonareparto_filtro3() + ")";
						}
					}
				}
			}

			sql = " SELECT prod.m_locator_id, line.m_product_id,line.UY_TieneBonificCruzada,prod.c_uom_id as UnidadSimple,prod.c_uom_to_id as Funda, prod.value, prod.name as prodname, hdr.c_bpartner_id, "
					+ " hdr.c_order_id, line.qtyordered as pedida, coalesce(line.uy_bonificaregla,0) as bonificacion, "
					+ " (line.qtyordered - line.qtyreserved - line.qtyinvoiced + coalesce(uy_bonificareglaUM,0)) as pendiente, "
					+ " hdr.m_warehouse_id, line.c_orderline_id, line.c_uom_id, line.uy_factor as factorconv "
					+ " FROM c_orderline line "
					+ " INNER JOIN c_order hdr ON line.c_order_id = hdr.c_order_id " + " INNER JOIN m_product prod ON line.m_product_id = prod.m_product_id "
					+ " INNER JOIN c_bpartner cli ON hdr.c_bpartner_id = cli.c_bpartner_id "
					+ " INNER JOIN c_bpartner_location cliloc ON hdr.c_bpartner_location_id = cliloc.c_bpartner_location_id "
					+ " LEFT OUTER JOIN c_uom_conversion uomconv ON (line.m_product_id = uomconv.m_product_id AND line.c_uom_id = uomconv.c_uom_to_id) "
					+ " WHERE hdr.ad_client_id = ?" + " AND hdr.isactive='Y'" + " AND hdr.docstatus='CO'"
					+ " AND hdr.issotrx='Y' AND line.uy_esbonificcruzada='N' "
					+ " AND hdr.isapproved='Y'"
					+ " AND hdr.uy_credit_approved='Y'"
					+ " AND hdr.C_DocTypeTarget_ID <> 1000075"
					+ // OpenUp M.R. 02-06-2011 Issue#475 Agrego lineas para que
					// cuando se generen las reservas estas no contemplen
					// las ordenes pactadas
					" AND (line.qtyordered - line.qtyreserved - line.qtyinvoiced) > 0 " + whereFiltros + whereZonas
					+ " ORDER BY prod.value, cli.uy_prioridadgenent, hdr.datepromised, line.line";

			log.info(sql);

			this.showText("Ejecutando consulta SQL");

			// OpenUp. Gabriel Vila. 05/10/2011. Issue #894.
			// Cambio pstmt para que el cursor sea scrolable y de esta manera
			// obtener el row count a procesar.
			// pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt = DB.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			// Fin Issue #894

			pstmt.setInt(1, this.getAD_Client_ID());
			rs = pstmt.executeQuery();

			// OpenUp. Gabriel Vila. 05/10/2011. Issue #894.
			// Obtengo row count
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			// Fin Issue #894.

			// Salgo con aviso de error si no tuve datos de la consulta segun
			// filtros.
			if (totalRowCount <= 0) {
				this.processMsg = "No se obtuvieron pedidos a procesar segun filtros indicados.";
				return false;
			}

			BigDecimal disponibleProducto = Env.ZERO;

			// Recorro los pedidos ordenados por articulo.
			// Voy asignando por articulo hasta agotar disponible
			while (rs.next()) {

				this.showText("Procesando linea " + rowCount++ + " de " + totalRowCount);

				int idProducto = rs.getInt("m_product_id");
				int idWareHouse = rs.getInt("m_warehouse_id");
				int idLocator = rs.getInt("m_locator_id");

				// Obtengo el disponible
				disponibleProducto = this.getStockDisponible(idProducto, idWareHouse, idLocator);

				if ((disponibleProducto.compareTo(Env.ZERO) == 0) && (this.isOnlyAvailables())) continue;

				BigDecimal cantPedido = rs.getBigDecimal("pedida");
				
				BigDecimal bonificacion = rs.getBigDecimal("bonificacion");
				BigDecimal cantPendiente = rs.getBigDecimal("pendiente");
				BigDecimal cantAReservar = Env.ZERO;

				// Si tengo stock disponible para este articulo
				if (disponibleProducto.compareTo(Env.ZERO) > 0) {

					// Si tengo mas disponible que lo pendiente para esta linea
					// de pedido
					if (disponibleProducto.compareTo(cantPendiente) >= 0) {

						// Reservo stock por todo el pendiente
						cantAReservar = cantPendiente.divide(rs.getBigDecimal("factorconv"), 0, RoundingMode.DOWN);

					} else {
						// Si tengo para reservar parcial y el pedido tiene
						// bonificacion, no puedo reservar nada.
						if (bonificacion.compareTo(Env.ZERO) <= 0) {

							cantAReservar = disponibleProducto.divide(rs.getBigDecimal("factorconv"), 0, RoundingMode.DOWN);
						}
					}
				}

				// Nueva registro en detalle de reserva
				MReserveDetail detail = new MReserveDetail(getCtx(), 0, get_TrxName(), idWareHouse, rs.getInt("m_product_id"), rs.getInt("c_bpartner_id"), rs
						.getInt("c_order_id"), rs.getInt("c_orderline_id"), rs.getInt("c_uom_id"), nroTrans);

				detail.setQtyOrdered(cantPedido.divide(rs.getBigDecimal("factorconv"), 0, RoundingMode.DOWN));
				detail.setuy_bonificaregla(bonificacion);
				detail.setUY_BonificaReglaUM(bonificacion.multiply(rs.getBigDecimal("factorconv")));
				detail.setuy_qtypending(cantPendiente.divide(rs.getBigDecimal("factorconv"), 0, RoundingMode.DOWN));
				detail.setQtyEntered(cantAReservar);
				detail.setuy_factor(rs.getBigDecimal("factorconv"));

				if (rs.getString("UY_TieneBonificCruzada").compareToIgnoreCase("Y") == 0) {
					detail.setUY_TieneBonificCruzada(true);
				}

				// Si esta es padre de una bonificacion Cruzada entonces exploto
				if (detail.isUY_TieneBonificCruzada()&& cantAReservar.compareTo(Env.ZERO)>0) {
					// this.logicaSugiereBonificacionCruzada(detail, "");
					detail.logicaSugiereBonificacionCruzada(detail, "", this.get_ID(), StokDisponible, StokDisponibleControFinal, listaMReserveDetail);
				}

				// Seteo mensaje
				if (detail.getQtyEntered().compareTo(Env.ZERO) <= 0 && bonificacion.compareTo(Env.ZERO) > 0) {
					detail.setDescription("No Reserva porque el stock disponible no cubre lo ordenado + "
							+ "Bonificacion Simple. Por definicion no se reserva nada.");
				}

				// Consumo stock
				if (!this.consumirStockDisponible(null, idProducto, detail.getQtyEntered().multiply(rs.getBigDecimal("factorconv")))) {
					
				}

				// Agrego detalle
				listaMReserveDetail.add(detail);

			}// FIn while
			rs.close();
			pstmt.close();

			// Guardo
			guardarReserveProd();

			this.setDocStatus(DocumentEngine.STATUS_WaitingConfirmation);
			this.setDocAction(DocumentEngine.ACTION_Complete);
			this.set_ValueOfColumn("uy_nrotrx", nroTrans);
			this.saveEx();

			log.info("Fin sugerencia de reserva comercial");


		} catch (Exception e) {
			log.log(Level.SEVERE, sql.toString(), e);
			this.processMsg = e.getMessage();
			result = false;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return result;
	}

	/**
	 * 	
	 * OpenUp.	
	 * Descripcion : Metodo que crea una MreserveProduct .Issue #821.
	 * @param productID
	 * @param wareHouseID
	 * @param disponibleProducto
	 * @param nroTrans
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 20/10/2011
	 */
	private MReserveProduct crearMReserveProduct(int productID, int wareHouseID, int nroTrans) {

		MReserveProduct salida = new MReserveProduct(getCtx(), 0, get_TrxName());
		salida.setUY_Reserve_Filter_ID(this.getUY_Reserve_Filter_ID());
		salida.setM_Product_ID(productID);
		salida.setM_Warehouse_ID(wareHouseID);

		// salida.setuy_qtyonhand_before(disponibleProducto);
		// salida.setuy_qtyonhand_after(disponibleProducto);

		salida.set_ValueOfColumn("uy_nrotrx", nroTrans);

		// setReserveStatus(salida);
		return salida;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Generacion de reserva en tablas finales.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 10/12/2010
	 */
	private boolean generarReservaFinal() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean result = true;

		try {

			this.showText("Iniciando accion de Completar");

			// Obtengo lineas del detalle de la reserva tal que tenga cantidad
			// reservada mayor a cero
			sql = "SELECT a.*,(select COUNT(c_orderline_id) from c_orderline  where c_order_id =a.c_order_id AND uy_lineapadre_id =a.c_orderline_id ) as suma"
					+ " FROM UY_Reserve_Detail a INNER JOIN UY_Reserve_Product b ON a.UY_Reserve_Product_ID = b.UY_Reserve_Product_ID"
					+ " WHERE b.UY_Reserve_Filter_ID = ?  AND a.QtyEntered > 0 AND a.uy_esbonificcruzada='N'"
					+ " ORDER BY a.c_bpartner_id, a.c_order_id, a.uy_factor";

			// pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt = DB.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, get_TrxName());
			pstmt.setInt(1, this.getUY_Reserve_Filter_ID());

			this.showText("Ejecutando consulta SQL");

			rs = pstmt.executeQuery();

			// Obtengo row count
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			int idCliente = -1, idPedido = -1, contadorLineasUnidades = 1, contadorLineasBulto = 1, cantidadLineasBonif = 0;

			int maximoLineasReserva = MSysConfig.getIntValue("UY_MAX_LINEAS_FACTURA", 30, this.getAD_Client_ID());
			//int maximoLineasReserva = 15;

			boolean esLineaBulto = false, esLineaBultoAux = false;
			BigDecimal factorLinea = Env.ONE;

			MReservaPedidoHdr hdrBultos = null, hdrUnidades = null;

			// Recorro lineas de reserva haciendo corte por cliente - pedido y
			// teniendo en cuenta
			// maximo de lineas por orden de reserva (si se supera este maximo
			// se inicia una nueva
			// orden de reserva por las lineas restantes).
			while (rs.next()) {

				rowCount++;
				this.showText("Procesando linea " + rowCount + " de " + totalRowCount);

				// Obtengo idcliente y idpedido de esta nueva linea
				int idClienteAux = rs.getInt("c_bpartner_id");
				int idPedidoAux = rs.getInt("c_order_id");
				cantidadLineasBonif = rs.getInt("suma") + 1;

				// Nuevo Cabezal de Reserva auxilizar
				MReservaPedidoHdr hdrAux = new MReservaPedidoHdr(getCtx(), 0, get_TrxName());
								
				//OpenUp. Nicolas Sarlabos. 04/09/2013. #1206. Elimino ID quemado.
				int docID = DB.getSQLValueEx(get_TrxName(),"select c_doctype_id from c_doctype where docbasetype ='ROV'");
				if(docID > 0){
					hdrAux.setC_DocType_ID(docID);
				} else throw new AdempiereException ("Error al obtener documento de Reserva");
				//Fin OpenUp. #1206
				hdrAux.setDateTrx(this.getDateTrx());
				hdrAux.setC_Order_ID(idPedidoAux);
				hdrAux.setC_BPartner_ID(idClienteAux);
				hdrAux.setDescription("Reserva Generada Automaticamente.");
				hdrAux.setdatereserved(hdrAux.getDateTrx());
				hdrAux.set_ValueOfColumn("UY_NroTrx", this.get_Value("uy_nrotrx"));

				// Guardo si es bulto o unidad simple
				if (rs.getBigDecimal("uy_factor") != null) factorLinea = rs.getBigDecimal("uy_factor");
				if (factorLinea.compareTo(Env.ONE) <= 0) {
					factorLinea = Env.ONE;
					esLineaBultoAux = false;
				} else
					esLineaBultoAux = true;

				// Si cambia cliente o pedido, inicializo cabezales y contadores
				if ((idClienteAux != idCliente) || (idPedidoAux != idPedido)) {

					// Completo reserva en proceso si es que hay una
					if (hdrBultos != null) completeReserva(hdrBultos);
					if (hdrUnidades != null) completeReserva(hdrUnidades);

					// Inicializo
					hdrBultos = null;
					hdrUnidades = null;
					contadorLineasBulto = 0;
					contadorLineasUnidades = 0;
					idCliente = idClienteAux;
					idPedido = idPedidoAux;
					esLineaBulto = esLineaBultoAux;
				}

				// Si cambia entre bulto y unidades simples, inicializo
				// cabezales y contadores
				if (esLineaBultoAux != esLineaBulto) {

					// Completo reserva en proceso si es que hay una
					if (hdrBultos != null) completeReserva(hdrBultos);
					if (hdrUnidades != null) completeReserva(hdrUnidades);

					hdrBultos = null;
					hdrUnidades = null;
					contadorLineasBulto = 1;
					contadorLineasUnidades = 1;
					esLineaBulto = esLineaBultoAux;
				}

				// Si uno de los contadores llego el tope, inicializo cabezal
				// correspondiente a la unidad de esta nueva linea
				if (contadorLineasBulto + cantidadLineasBonif >= maximoLineasReserva && esLineaBulto) {
					if (hdrBultos != null) completeReserva(hdrBultos);
					contadorLineasBulto = 0;
					hdrBultos = hdrAux;
				} else if (contadorLineasUnidades + cantidadLineasBonif >= maximoLineasReserva && !esLineaBulto) {
					if (hdrUnidades != null) completeReserva(hdrUnidades);
					contadorLineasUnidades = 0;
					hdrUnidades = hdrAux;
				}

				if (esLineaBulto && hdrBultos == null) {
					hdrBultos = hdrAux;
					hdrBultos.saveEx(get_TrxName());
				} else if (!esLineaBulto && hdrUnidades == null) {
					hdrUnidades = hdrAux;
					hdrUnidades.saveEx(get_TrxName());
				}

				if (esLineaBulto && hdrBultos.getUY_ReservaPedidoHdr_ID() <= 0) {
					hdrBultos = hdrAux;
					hdrBultos.saveEx(get_TrxName());
				} else if (!esLineaBulto && hdrUnidades.getUY_ReservaPedidoHdr_ID() <= 0) {
					hdrUnidades = hdrAux;
					hdrUnidades.saveEx(get_TrxName());
				}

				// Nueva linea de Reserva en Proceso
				MReservaPedidoLine line = new MReservaPedidoLine(getCtx(), 0, get_TrxName());
				if (esLineaBulto) line.setUY_ReservaPedidoHdr_ID(hdrBultos.getUY_ReservaPedidoHdr_ID());
				else
					line.setUY_ReservaPedidoHdr_ID(hdrUnidades.getUY_ReservaPedidoHdr_ID());

				MOrderLine oline = new MOrderLine(getCtx(), rs.getInt("c_orderline_id"), null);
				line.setC_OrderLine_ID(oline.getC_OrderLine_ID());
				line.setM_Product_ID(oline.getM_Product_ID());
				line.setM_Warehouse_ID(oline.getM_Warehouse_ID());
				line.setM_AttributeSetInstance_ID(oline.getM_AttributeSetInstance_ID());
				line.setC_UOM_ID(oline.getC_UOM_ID());

				line.setQtyEntered(rs.getBigDecimal("qtyentered").subtract(rs.getBigDecimal("uy_bonificaregla")));
				line.setQtyOrdered(rs.getBigDecimal("qtyordered"));
				line.setQtyReserved(rs.getBigDecimal("qtyentered").multiply(rs.getBigDecimal("uy_factor")));
				line.setuy_bonificaregla(rs.getBigDecimal("uy_bonificaregla"));
				line.setUY_BonificaReglaUM(rs.getBigDecimal("UY_BonificaReglaUM"));
				line.setuy_factor(rs.getBigDecimal("uy_factor"));

				boolean tieneBOnifCruzada = false;
				if (rs.getString("UY_TieneBonificCruzada").compareToIgnoreCase("Y") == 0) {
					tieneBOnifCruzada = true;
				}
				line.setUY_TieneBonificCruzada(tieneBOnifCruzada);
				line.setUY_EsBonificCruzada(false);

				line.setProcessed(true);

				line.set_ValueOfColumn("UY_NroTrx", this.get_Value("uy_nrotrx"));
				line.saveEx(get_TrxName());

				// Actualizo informacion de cantidad reservada en linea de
				// pedido
				DB.executeUpdate("UPDATE C_Orderline SET QtyReserved = "
						+ oline.getQtyReserved().add(line.getQtyReserved().subtract(line.getUY_BonificaReglaUM())) + " WHERE c_orderline_id ="
						+ oline.getC_OrderLine_ID(), get_TrxName());

				// Caso que sea una linea bonificada
				if (tieneBOnifCruzada) {

					MReserveDetail det = new MReserveDetail(getCtx(), rs, get_TrxName());

					MReserveDetail[] hijas = det.getMReserveDetailHijas(get_TrxName());

					for (int i = 0; i < hijas.length; i++) {

						MReserveDetail detCurr = hijas[i];
						oline = (MOrderLine) detCurr.getC_OrderLine();
						line = new MReservaPedidoLine(getCtx(), 0, get_TrxName());

						if (esLineaBulto) line.setUY_ReservaPedidoHdr_ID(hdrBultos.getUY_ReservaPedidoHdr_ID());
						else
							line.setUY_ReservaPedidoHdr_ID(hdrUnidades.getUY_ReservaPedidoHdr_ID());


						line.setC_OrderLine_ID(oline.getC_OrderLine_ID());
						line.setM_Product_ID(oline.getM_Product_ID());
						line.setM_Warehouse_ID(oline.getM_Warehouse_ID());
						line.setM_AttributeSetInstance_ID(oline.getM_AttributeSetInstance_ID());
						line.setC_UOM_ID(oline.getC_UOM_ID());

						line.setQtyEntered(Env.ZERO);
						line.setQtyOrdered(Env.ZERO);
						line.setQtyReserved(detCurr.getUY_BonificaReglaUM());
						line.setuy_factor(detCurr.getuy_factor());
						line.setUY_BonificaReglaUM(detCurr.getUY_BonificaReglaUM());
						line.setuy_bonificaregla(detCurr.getuy_bonificaregla());

						line.setUY_TieneBonificCruzada(false);
						line.setUY_EsBonificCruzada(true);

						line.setProcessed(true);
						line.set_ValueOfColumn("UY_NroTrx", this.get_Value("uy_nrotrx"));
						line.saveEx(get_TrxName());

						// DB.executeUpdate("UPDATE C_Orderline SET QtyReserved = "
						// + oline.getQtyReserved().add(line.getQtyReserved())
						// + " WHERE c_orderline_id =" +
						// oline.getC_OrderLine_ID(), get_TrxName());

					}
				}




				// Incremento contador de lineas de reserva
				if (esLineaBulto) contadorLineasBulto++;
				else
					contadorLineasUnidades++;

			}

			// Completo ultima reserva en proceso si es que hay una
			if (hdrBultos != null) completeReserva(hdrBultos);
			if (hdrUnidades != null) completeReserva(hdrUnidades);

			// Marco proceso de reserva como completada
			this.setDocAction(DocumentEngine.ACTION_Close);
			this.setDocStatus(DocumentEngine.STATUS_Completed);
			this.setProcessed(true);
			this.saveEx(get_TrxName());
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
			this.processMsg = e.getMessage();
			result = false;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return result;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene pedidos comprendidos en los filtros de la generacion. Estos pedidos luego seran tomados a su vez
	 * como filtros para la generacion de las reservas.
	 * @return
	 * @author  Gabriel Vila ,Nicolas Garcia .Issue #821.
	 * Fecha : 11/01/2011
	 */
	public boolean getPedidosFiltros() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean result = true;

		try {

			// Eliminar posibles filtros de pedidos anteriores, para no
			// duplicar.
			DB.executeUpdate("DELETE FROM UY_Reserve_Orders WHERE UY_Reserve_Filter_ID =" + this.getUY_Reserve_Filter_ID(), get_TrxName());

			// Condicion de Filtros
			String whereFiltros = "", whereZonas = "", whereProducto = "";

			if (this.getuy_dateordered_from() != null) whereFiltros += " AND hdr.dateordered >='" + this.getuy_dateordered_from() + "'";
			if (this.getuy_dateordered_to() != null) whereFiltros += " AND hdr.dateordered <='" + this.getuy_dateordered_to() + "'";
			if (this.getM_Warehouse_ID() > 0) whereFiltros += " AND hdr.m_warehouse_id =" + this.getM_Warehouse_ID();
			if (this.getC_BPartner_ID() > 0) whereFiltros += " AND hdr.c_bpartner_id =" + this.getC_BPartner_ID();
			if (this.getUY_CanalVentas_ID() > 0) whereFiltros += " AND cli.uy_canalventas_id =" + this.getUY_CanalVentas_ID();
			if (this.getC_SalesRegion_ID() > 0) whereFiltros += " AND cliloc.c_salesregion_id =" + this.getC_SalesRegion_ID();
			if (this.getuy_datepromised_from() != null) whereFiltros += " AND hdr.datepromised >='" + this.getuy_datepromised_from() + "'";
			if (this.getuy_datepromised_to() != null) whereFiltros += " AND hdr.datepromised <='" + this.getuy_datepromised_to() + "'";
			if (this.getC_Order_ID() > 0) whereFiltros += " AND hdr.c_order_id =" + this.getC_Order_ID();

			if (this.getM_Product_ID() > 0)
				whereProducto = " AND EXISTS (" + " SELECT * FROM c_orderline WHERE m_product_id =" + this.getM_Product_ID()
						+ " AND c_orderline.c_order_id = hdr.c_order_id)";

			// Zonas de Entrega
			if (this.getuy_zonareparto_filtro1() > 0) {
				whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + ")";
			}

			if (this.getuy_zonareparto_filtro2() > 0) {
				if (!whereZonas.equalsIgnoreCase("")) whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + ","
						+ getuy_zonareparto_filtro2() + ")";
				else
					whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro2() + ")";
			}

			if (this.getuy_zonareparto_filtro3() > 0) {
				if (whereZonas.equalsIgnoreCase("")) whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro3() + ")";
				else {
					if ((this.getuy_zonareparto_filtro1() > 0) && (this.getuy_zonareparto_filtro2() > 0)) {
						whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + "," + getuy_zonareparto_filtro2() + ","
								+ getuy_zonareparto_filtro3() + ")";
					} else if (this.getuy_zonareparto_filtro1() > 0) {
						whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + "," + getuy_zonareparto_filtro3() + ")";

					} else if (this.getuy_zonareparto_filtro2() > 0) {
						whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro2() + "," + getuy_zonareparto_filtro3() + ")";
					}
				}
			}

			sql = " SELECT hdr.c_bpartner_id, hdr.c_order_id, hdr.dateordered, hdr.c_bpartner_location_id,"
					+ " (coalesce(loca.address1,'') || coalesce(loca.address2,'')) as direccion, "
					+ " (SELECT SUM((line.qtyordered - qtyreserved - qtyinvoiced)*priceactual) "
					+ "  FROM c_orderline line WHERE line.c_order_id = hdr.c_order_id) as totalpendiente, "
					+ " (SELECT SUM(line.qtyordered - qtyreserved - qtyinvoiced) "
					+ "  FROM c_orderline line WHERE line.c_order_id = hdr.c_order_id) as cantpendiente, " + " cliloc.uy_zonareparto_id "
					+ " FROM c_order hdr " + " INNER JOIN c_bpartner cli ON hdr.c_bpartner_id = cli.c_bpartner_id "
					+ " INNER JOIN c_bpartner_location cliloc ON hdr.c_bpartner_location_id = cliloc.c_bpartner_location_id "
					+ " LEFT OUTER JOIN c_location loca ON cliloc.c_location_id = loca.c_location_id " + " WHERE hdr.ad_client_id = ?"
					+ " AND hdr.isactive='Y'" + " AND hdr.docstatus='CO'" + " AND hdr.issotrx='Y'" + " AND isapproved='Y'"
					+ " AND hdr.C_DocTypeTarget_ID <> 1000075" + whereFiltros + whereZonas + whereProducto;

			log.info(sql);

			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.getAD_Client_ID());
			rs = pstmt.executeQuery();

			// Recorro los pedidos ordenados por articulo.
			// Voy asignando por articulo hasta agotar disponible
			while (rs.next()) {

				if (rs.getBigDecimal("cantpendiente").compareTo(Env.ZERO) > 0) {
					MReserveOrders rOrd = new MReserveOrders(getCtx(), 0, get_TrxName());
					rOrd.setUY_Reserve_Filter_ID(this.getUY_Reserve_Filter_ID());
					rOrd.setC_Order_ID(rs.getInt("c_order_id"));
					rOrd.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
					rOrd.setC_BPartner_Location_ID(rs.getInt("c_bpartner_location_id"));
					rOrd.setDateOrdered(rs.getTimestamp("DateOrdered"));
					rOrd.setGrandTotal(rs.getBigDecimal("totalpendiente"));

					MOrder ordHdr = new MOrder(getCtx(), rs.getInt("c_order_id"), null);
					rOrd.setUY_ZonaReparto_ID(rs.getInt("uy_zonareparto_id"));
					rOrd.setuy_cantbultos(ordHdr.getCantidadBultos());

					rOrd.saveEx();
				}
			}
			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			log.log(Level.SEVERE, sql.toString(), e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return result;
	}

	private boolean hayFiltroPedidos() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean value = false;

		try {
			sql = "SELECT * " + " FROM uy_reserve_orders " + " WHERE uy_reserve_filter_id =?";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, this.getUY_Reserve_Filter_ID());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				value = true;
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return value;
	}

	/**
	 * OpenUp. Gabriel Vila. 16/06/2011. Completo una reserva de pedido.
	 * @param resHdr
	 * @throws Exception
	 */
	private void completeReserva(MReservaPedidoHdr resHdr) throws Exception {
		if (resHdr != null)
			if (resHdr.getUY_ReservaPedidoHdr_ID() > 0)
				if (!resHdr.processIt(DocAction.ACTION_Complete)) throw new Exception("No se pudo completar Reserva (unidades) : " + resHdr.getDocumentNo()
						+ "\n" + resHdr.getProcessMsg());
				else
					resHdr.saveEx(get_TrxName());
	}



	// OpenUp. Gabriel Vila. 05/10/2011. Issue #894.
	// Get - Set de la ventana que puede contener o no al proceso.
	public AWindow getWindow() {
		return this.window;
	}

	public void setWindow(AWindow value) {
		this.window = value;
	}// Fin Issue #894.

	/**
	 * 
	 * OpenUp.	Metodo llamado desde metodo SugerirReservaBonificacionCruzada(), el mismo genera la bonificaciones cruzadas
	 * Descripcion : ISSUE #821
	 * @param detailPadre
	 * @param linePadre
	 * @author  Nicolas Garcia
	 * Fecha : 19/10/2011
	 * @param nroTrans 
	 * @param statusLineaReservaAnterior 
	 * @param product 
	 */


	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que acumula en el hashMap StockDisponible.Issue #821.
	 * @param productID
	 * @param cantidad
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 19/10/2011
	 * @param stokDisponibleBonif 
	 */
	private boolean consumirStockDisponible(HashMap<Integer, BigDecimal> stokDisponibleBonif, Integer productID, BigDecimal cantidad) {

		HashMap<Integer, BigDecimal> stok = stokDisponibleBonif;
		if (stok == null) {
			stok = this.StokDisponible;
		}

		if (stok.containsKey(productID)) {

			if (stok.get(productID).compareTo(cantidad) < 0) {
				return false;
			} else {
				stok.put(productID, stok.get(productID).subtract(cantidad));
				return true;
			}

		} else {
			return false;
		}
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :La funcion devuelve el disponible real (Contemplando lo ya usado en este proceso) de un producto, ya sea si fue sugerido antes (Primera Pasada) o si es la primera vez.Issue #821.
	 * @param productID
	 * @param wareHouseID
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 19/10/2011
	 */
	private BigDecimal getStockDisponible(Integer productID, int wareHouseID, int locatorID) {

		BigDecimal disponible = Env.ZERO;
		// Si el producto ya fue usado en la priemra recorrida (se consumio
		// parte del disponible)
		if (this.StokDisponible.containsKey(productID)) {

			disponible = this.StokDisponible.get(productID);

		} else {

			disponible = MStockTransaction.getQtyAvailable(wareHouseID, locatorID, productID, 0, 0, null, null);

			this.StokDisponible.put(productID, disponible);
			this.StokDisponibleControFinal.put(productID, disponible);
		}

		if (disponible.compareTo(Env.ZERO) > 0) {
			return disponible;
		}

		return Env.ZERO;
	}



	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que genera el agrupamiento por producto Issue #821.
	 * @author  Nicolas Garcia
	 * Fecha : 27/10/2011
	 */
	private void generoReserveProd() {

		MReserveProduct reserveProd = null;

		for (MReserveDetail det : this.listaMReserveDetail) {

			int idProducto = det.getM_Product_ID();

			// Si no existe el reserveProd en hashMap
			if (this.ProdAcumulado.containsKey(idProducto)) {
				reserveProd = this.ProdAcumulado.get(idProducto);
			} else {
				// Creo el objeto y lo guardo en memoria
				reserveProd = crearMReserveProduct(idProducto, det.getM_Warehouse_ID(), det.getuy_nrotrx());

				// Modelo del producto
				MProduct prod = (MProduct) reserveProd.getM_Product();

				BigDecimal factor = MUOMConversion.getProductRateFrom(Env.getCtx(), prod.getM_Product_ID(), prod.getC_UOM_To_ID());

				if (factor == null) {
					if (prod.getC_UOM_To_ID() == UNIDAD_MEDIDA_DEFAULT) {
						factor = Env.ONE;
					} else {
						MUOM uom = new MUOM(getCtx(), prod.getC_UOM_To_ID(), null);
						this.processMsg = "El Producto : " + prod.getValue() + " - " + prod.getName() + "\n"
								+ " no tiene FACTOR DE CONVERSION DEFINIDO entre la unidad de medida y la unidad de venta (" + uom.getUOMSymbol() + ")";
						throw new AdempiereException(this.processMsg);
					}
				}

				reserveProd.setuy_factor(factor);

				this.ProdAcumulado.put(idProducto, reserveProd);
			}

			reserveProd.actualizarCantidades(det.getQtyEntered(), det.getQtyOrdered(), det.getuy_qtypending(), det.getuy_factor());


		}
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :metodo que guarda los productos agrupados. Issue #821.
	 * @author  Nicolas Garcia
	 * Fecha : 20/10/2011
	 */

	private void guardarReserveProd() {

		// Genero ReserveProd
		this.generoReserveProd();

		Iterator<Integer> it = this.ProdAcumulado.keySet().iterator();

		BigDecimal StokInicial = null;
		BigDecimal StokFinal = null;

		int rowCount = 0;

		while (it.hasNext()) {

			rowCount++;

			this.showText("Procesando linea " + rowCount);
			MReserveProduct reserveProd = this.ProdAcumulado.get(it.next());

			BigDecimal factor = reserveProd.getuy_factor();

			// Stok inicial
			StokInicial = this.StokDisponibleControFinal.get(reserveProd.getM_Product_ID()).divide(factor, 2, RoundingMode.DOWN);
			reserveProd.setuy_qtyonhand_before(StokInicial);

			// Stok Final
			StokFinal = this.StokDisponible.get(reserveProd.getM_Product_ID());
			reserveProd.setUY_QtyOnHand_AfterUM(StokFinal);

			
			// Guardo
			reserveProd.saveEx();
		}

		// Despues de guardar esto guardo los detalles (Ordenes)
		this.guardarDetalles();
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que guarda las lineas de detalle (Producto) que estaban en memoria. Issue #821
	 * @author  Nicolas Garcia
	 * Fecha : 20/10/2011
	 */
	private void guardarDetalles() {

		int rowCount = 0;
		int totalRowCount = this.listaMReserveDetail.size();

		for (MReserveDetail det : this.listaMReserveDetail) {

			this.showText("Procesando linea " + rowCount++ + " de " + totalRowCount);

			det.setUY_Reserve_Product_ID(this.ProdAcumulado.get(det.getM_Product_ID()).get_ID());

			det.setuy_qtyonhand_before(this.StokDisponibleControFinal.get(det.getM_Product_ID()).divide(det.getuy_factor(), 2, RoundingMode.DOWN));

			BigDecimal aux = det.getuy_qtyonhand_before().subtract(det.getQtyEntered());
			det.setuy_qtyonhand_after(aux);

			det.saveEx();

			this.StokDisponibleControFinal.put(det.getM_Product_ID(), aux.multiply(det.getuy_factor()));
		}

		//ADialog.info(0, null, "Proceso terminado en " + new Time(System.currentTimeMillis() - inicio).toString().substring(3));
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	
	private void showText(String message){
		
		if (this.getWindow() != null){
			this.getWindow().setWaitingMessage("Iniciando Reserva");	
		}
	}
	
}
