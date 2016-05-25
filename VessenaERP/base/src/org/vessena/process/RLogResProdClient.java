package org.openup.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.compiere.model.MTable;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MStockStatus;

public class RLogResProdClient extends SvrProcess {

	// Variables para filtros del reporte
	private int adClientID = 0, adUserID = 0;
	private int mProductID = 0;
	private int cbpartnerID = 0;
	private boolean onlyNotAssigned = false;

	private String idReporte = "";
	private static final String TABLA_MOLDE = "UY_Molde_ResProdClient";

	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la
		// tabla molde
		this.deleteInstanciasViejasReporte();

		// Cargo informacion a reportear en tabla molde
		this.getData();

		// Actualizo valores necesarios en columnas de la tabla molde.
		this.updateData();

		// Retorno con exito.
		return "ok";
	}

	/**
	 * Delete de registros de instancias viejas de este reporte para el usuario actual
	 */
	private void deleteInstanciasViejasReporte() {
		String sql = "";
		try {
			sql = " DELETE FROM " + TABLA_MOLDE + " WHERE ad_user_id =" + this.adUserID;
			DB.executeUpdate(sql, null);
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		}
	}

	@Override
	protected void prepare() {

		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("idReporte")) {
					paramIDReporte = para[i];
				}
				if (name.equalsIgnoreCase("C_BPartner_ID")) {
					if (para[i].getParameter() != null) {
						this.cbpartnerID = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("M_Product_ID")) {
					if (para[i].getParameter() != null) {
						this.mProductID = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("onlyNotAssigned")) {
					if (para[i].getParameter() != null) {
						this.onlyNotAssigned = (((String) para[i].getParameter()).equalsIgnoreCase("Y")) ? true : false;
					}
				}
			}
		}

		// Empresa - Usuario
		this.adClientID = getAD_Client_ID();
		this.adUserID = getAD_User_ID();

		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));

		// Si tengo parametro para idreporte
		if (paramIDReporte != null) paramIDReporte.setParameter(this.idReporte);

	}

	private void getData() throws Exception {

		String insert = "", sql = "", cond = "";
		MStockStatus status = MStockStatus.forValue(getCtx(), "reservado", get_TrxName());
		MTable table1 = MTable.get(getCtx(), "PP_Order");
		MTable table2 = MTable.get(getCtx(), "UY_ReservaPedidoHdr");

		try {
			insert = "INSERT INTO " + TABLA_MOLDE
					+ " (ad_client_id, ad_org_id, ad_user_id, fecreporte, idreporte, uy_reservapedidohdr_id, pp_order_id, c_bpartner_id, bpname, datereserved,"
					+ " m_product_id, prodname, c_uom_id, uomname, cantidad)";

			// Filtros seleccionados por el usuario
			String filtros = " AND st.ad_client_id =" + this.adClientID;
			if (this.cbpartnerID > 0) filtros += " AND bp.c_bpartner_id =" + this.cbpartnerID;
			if (this.mProductID > 0) filtros += " AND st.m_product_id =" + this.mProductID;

			// Si se elige mostrar solo reservas sin ATR...
			if (this.onlyNotAssigned) {

				cond += " AND NOT (reshdr.uy_reservapedidohdr_id IN ( SELECT uy_asignatransporteline.uy_reservapedidohdr_id"
						+ " FROM uy_asignatransporteline"
						+ " JOIN uy_asignatransportehdr ON uy_asignatransporteline.uy_asignatransportehdr_id = uy_asignatransportehdr.uy_asignatransportehdr_id"
						+ " AND (uy_asignatransportehdr.docstatus = ANY (ARRAY['WC', 'CO']))))";
			}

			sql = " SELECT st.ad_client_id, st.ad_org_id,"
					+ this.adUserID
					+ ",current_date,"
					+ "'"
					+ this.idReporte
					+ "'"
					+ ",st.record_id AS uy_reservapedidohdr_id, 0 AS pp_order_id, st.c_bpartner_id, COALESCE(COALESCE(bp.name, bp.name2), '') AS bpname, reshdr.datereserved, st.m_product_id, prod.name AS prodname, resline.c_uom_id, uom.name AS uomname, COALESCE(sum(st.movementqty * st.sign) / resline.uy_factor, 0) AS cantidad"
					+ " FROM uy_stocktransaction st"
					+ " JOIN c_bpartner bp ON st.c_bpartner_id = bp.c_bpartner_id"
					+ " JOIN uy_reservapedidohdr reshdr ON reshdr.uy_reservapedidohdr_id = st.record_id"
					+ " JOIN uy_reservapedidoline resline ON reshdr.uy_reservapedidohdr_id = resline.uy_reservapedidohdr_id AND resline.m_product_id = st.m_product_id"
					+ " JOIN m_product prod ON st.m_product_id = prod.m_product_id"
					+ " JOIN c_uom uom ON resline.c_uom_id = uom.c_uom_id"
					+ " WHERE st.ad_table_id = "+ table2.get_ID() +" AND st.uy_stockstatus_id = " + status.get_ID()+ " AND reshdr.docstatus = 'CO'"
					+ " AND not exists (select uy_reservapedidohdr_id from c_invoice where reshdr.uy_reservapedidohdr_id=c_invoice.uy_reservapedidohdr_id and c_invoice.docstatus='CO')" //OpenUp Nicolas Sarlabos #907 20/01/2012
					+ filtros
					+ cond
					+ " GROUP BY st.ad_client_id, st.ad_org_id, st.record_id, st.c_bpartner_id, COALESCE(COALESCE(bp.name, bp.name2), ''), reshdr.datereserved, st.m_product_id, prod.name, resline.c_uom_id, uom.name, resline.uy_factor"
					+ " HAVING sum(st.movementqty * st.sign) > 0"
					+ " UNION"
					+ " SELECT st.ad_client_id, st.ad_org_id,"
					+ this.adUserID
					+ ",current_date, "
					+ "'"
					+ this.idReporte
					+ "'"
					+ ",0 AS uy_reservapedidohdr_id, st.record_id AS pp_order_id, 0 AS c_bpartner_id, '**SIN CLIENTE - ORDEN DE PACK**' AS bpname, reshdr.dateordered AS datereserved, st.m_product_id, prod.name AS prodname, st.c_uom_id, uom.name AS uomname, COALESCE(sum(st.movementqty * st.sign), 0) AS cantidad"
					+ " FROM uy_stocktransaction st"
					+ " JOIN pp_order reshdr ON reshdr.pp_order_id = st.record_id"
					+ " JOIN m_product prod ON st.m_product_id = prod.m_product_id"
					+ " JOIN c_uom uom ON st.c_uom_id = uom.c_uom_id"
					+ " WHERE st.ad_table_affected_id ="+ table1.get_ID() +" AND st.uy_stockstatus_id ="+ status.get_ID()+ " AND reshdr.docstatus = 'CL' AND reshdr.uy_cerroconfirmacion = 'N'"
					+ filtros
					+ " GROUP BY st.ad_client_id, st.ad_org_id, st.record_id, st.c_bpartner_id, reshdr.dateordered, st.m_product_id, prod.name, st.c_uom_id, uom.name"
					+ " HAVING sum(st.movementqty * st.sign) <> 0";



			log.log(Level.INFO, insert + sql);
			DB.executeUpdate(insert + sql, null);

		} catch (Exception e) {
			log.log(Level.SEVERE, insert + sql, e);
			throw e;
		}
	}

	private void updateData() {
		String sql = "";
		try {

			// Si el usuario indica ocultar registros con cantidad fisica cero
			if (this.onlyNotAssigned) {
				sql = " UPDATE " + TABLA_MOLDE + " SET onlyNotAssigned = 'Y' " + " WHERE idreporte ='" + this.idReporte + "'";
				DB.executeUpdate(sql, null);
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		}
	}



}
