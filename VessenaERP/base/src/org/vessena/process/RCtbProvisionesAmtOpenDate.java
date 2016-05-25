package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class RCtbProvisionesAmtOpenDate extends SvrProcess{

	private Timestamp fechaHasta = null;
	private int adClientID = 0;
	private int adOrgID = 0;
	
	
	private int cPeriodFromID = 0;
	private int cPeriodToID = 0;
	
	private int C_Activity_ID = 0;
	private int M_Product_ID = 0;
	private int C_ElementValue_ID = 0;
	private int C_BPartner_ID = 0;

	private int adUserID = 0;
	
	private String TABLA_MOLDE_PROVISION = "UY_MOLDE_ProvisionAmtOpenDate";
	
	public RCtbProvisionesAmtOpenDate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name!= null && para[i].getParameter()!=null){
				if (name.equalsIgnoreCase("DateInvoiced")){
					this.fechaHasta = ((Timestamp)para[i].getParameter());
					para[i].setParameter(this.fechaHasta);
				}else if (name.equalsIgnoreCase("C_Period_ID")){
					this.cPeriodFromID = ((BigDecimal)para[i].getParameter()).intValueExact();
					this.cPeriodToID = ((BigDecimal)para[i].getParameter_To()).intValueExact();
				} else if (name.equals("C_Activity_ID_1")) {
					this.C_Activity_ID = ((BigDecimal) para[i].getParameter()).intValue();
				} else if (name.equals("M_Product_ID")) {
					this.M_Product_ID = ((BigDecimal) para[i].getParameter()).intValue();
				} else if (name.equals("C_ElementValue_ID")) {
					this.C_ElementValue_ID = ((BigDecimal) para[i].getParameter()).intValue();
				} else if (name.equals("C_BPartner_ID")) {
					this.C_BPartner_ID = ((BigDecimal) para[i].getParameter()).intValue();
				} else if (name.equalsIgnoreCase("AD_User_ID")) {
					this.adUserID = ((BigDecimal) para[i].getParameter()).intValue();
				} else if (name.equalsIgnoreCase("Ad_Client_ID")) {
					this.adClientID = ((BigDecimal) para[i].getParameter()).intValueExact();
				}else if (name.equalsIgnoreCase("Ad_Org_ID")) {
					this.adOrgID = ((BigDecimal) para[i].getParameter()).intValueExact();
				}
			}
		}
		
	}

	@Override
	protected String doIt() throws Exception {

		this.deleteData();
		this.updateData();
		this.calculoSaldos();
		this.eliminoSinPendientes();
		return "OK";
	}

	



	private void deleteData() {

		try {

			String action = "";

			action = " DELETE FROM " + TABLA_MOLDE_PROVISION
					+ " WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action, null);


		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	


private void updateData() {
	
	String sql = "";
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	String condicionProveedor = "";
	String condicionProveedorProv = "";
	String condicionProducto = "";
	String condicionProductoProv = "";
	String condicionDepartamento ="";
	String condicionDepartamentoProv ="";
	String condicionPeriodoDesde = "";
	String condicionPeriodoDesdeProv = "";
	String condicionPeriodoHasta = "";
	String condicionPeriodoHastaProv = "";
	try{
		if (this.cPeriodFromID > 0){
			condicionPeriodoDesde = " AND provDate.c_period_id >= " + this.cPeriodFromID;
			condicionPeriodoDesdeProv = " AND prov.c_period_id >= " + this.cPeriodFromID;
		}
		if (this.cPeriodToID > 0){
			condicionPeriodoHasta = " AND provDate.c_period_id <= " + this.cPeriodToID;
			condicionPeriodoHastaProv = " AND prov.c_period_id <= " + this.cPeriodFromID;
		}
		if (this.C_BPartner_ID > 0){
			condicionProveedor = " AND provDate.c_bpartner_id = " + this.C_BPartner_ID; 
			condicionProveedorProv = " AND line.c_bpartner_id = " + this.C_BPartner_ID; 
			
		}
		if (this.M_Product_ID > 0){
			condicionProducto = " AND provDate.m_product_id = " + this.M_Product_ID;
			condicionProductoProv = " AND line.m_product_id = " + this.M_Product_ID;
		}
		if (this.C_Activity_ID > 0){
			condicionDepartamento = " AND provDate.c_activity_id_1 = " + this.C_Activity_ID;
			condicionDepartamentoProv = " AND line.c_activity_id_1 = " + this.C_Activity_ID;
		}
		
		/*sql = "SELECT provDate.ad_client_id, provDate.ad_org_id,  " + this.adUserID +" as ad_user_id, provDate.uy_provision_id, provDate.uy_provisionline_id,  " 
				+ " provDate.amt, provDate.amtallocated,  provDate.amtopen, " 
				+ " provDate.DateInvoiced, provDate.c_period_id, provDate.c_bpartner_id, provDate.c_elementvalue_id, "
				+ " provDate.m_product_id,  provDate.c_activity_id_1, provDate.c_currency_id  "
				+ " FROM vuy_provisionamtopen_date provDate "
				+ " JOIN uy_provision prov ON prov.uy_provision_id = provDate.uy_provision_id AND prov.datetrx <= '" + this.fechaHasta + "'"
				+ " WHERE provDate.DateInvoiced <= '" + this.fechaHasta + "' OR provDate.DateInvoiced is null"
				+ condicionPeriodoDesde
				+ condicionPeriodoHasta
				+ condicionProveedor
				+ condicionProducto
				+ condicionDepartamento
				+ " ORDER BY provDate.uy_provisionline_id";*/
		
		sql = "SELECT * FROM ( "
			+ "SELECT provDate.ad_client_id as ad_client_id , provDate.ad_org_id as ad_org_id ,  " + this.adUserID +"  as ad_user_id,"
			+ "	provDate.uy_provision_id as uy_provision_id, provDate.uy_provisionline_id as uy_provisionline_id,"   
			+ " provDate.amt as amt, provDate.amtallocated as amtallocated,  provDate.amtopen as amtopen,  provDate.DateInvoiced as DateInvoiced,"
			+ " provDate.c_period_id as c_period_id, provDate.c_bpartner_id as c_bpartner_id," 
			+ " provDate.c_elementvalue_id as c_elementvalue_id,  provDate.m_product_id as m_product_id,  provDate.c_activity_id_1 as c_activity_id_1,"
			+ " provDate.c_currency_id as c_currency_id"      
			+ "	FROM vuy_provisionamtopen_date provDate"   
			+ "		JOIN uy_provision prov ON prov.uy_provision_id = provDate.uy_provision_id" 
			+ "					AND prov.datetrx <= '" +  this.fechaHasta + "'"
			+ "	WHERE provDate.DateInvoiced <= '" + this.fechaHasta + "'"
			+ "		OR provDate.DateInvoiced is null "
			+ condicionPeriodoDesde
			+ condicionPeriodoHasta
			+ condicionProveedor
			+ condicionProducto
			+ condicionDepartamento
			+ " UNION "
			+ "	SELECT prov.ad_client_id as ad_client_id, prov.ad_org_id as ad_org_id, " + this.adUserID +"  as ad_user_id, prov.uy_provision_id as uy_provision_id," 
			+ "		line.uy_provisionline_id as uy_provisionline_id, round(COALESCE(line.amtsourceaverage, 0::numeric), 2) as amt, 0 as amtallocated, 0 as amtopen, prov.datetrx as DateInvoiced,"
			+ "		prov.c_period_id as c_period_id, line.c_bpartner_id as c_bpartner_id, line.c_elementvalue_id as c_elementvalue_id, line.m_product_id as m_product_id,"
			+ "		line.c_activity_id_1 as c_activity_id_1, line.c_currency_id as c_currency_id"
			+ "	FROM uy_provision prov JOIN uy_provisionline line on prov.uy_provision_id = line.uy_provision_id" 
			+ "	WHERE prov.datetrx <= '" +  this.fechaHasta + "'"
			+ "			 AND line.amtsourceaverage <> 0 "
			+ "			 AND prov.docstatus::text = 'CO'::text" 
			+ "			 AND prov.c_period_id >= 1000150::numeric"
			+ condicionPeriodoDesdeProv
			+ condicionPeriodoHastaProv
			+ condicionProveedorProv
			+ condicionProductoProv
			+ condicionDepartamentoProv
			+ "	) AS resultado"
			+ "	ORDER BY uy_provisionline_id";
		
		pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
		
		rs = pstmt.executeQuery ();

		rs.last();
		int totalRowCount = rs.getRow(), rowCount = 0;
		rs.beforeFirst();

		// Corte por cuenta y periodo
		int cBPartnerID = 0, cElementValueID = 0, mProductID = 0, cActivityID = 0, provisionID = 0, cPeriodID = 0, provisionlineID = 0;
		
		BigDecimal amtAllocated = Env.ZERO;
		
		// Recorro registros y voy actualizando
		while (rs.next()){
			
			rowCount++;
			
			if ( (rs.getInt("uy_provisionline_id") != provisionlineID)  ){

				// Si no estoy en primer registro actualizo cuenta anterior
				if (rowCount > 1){
					//this.updateProduct(cPeriodID, amt);
					String upDate = " UPDATE "+ TABLA_MOLDE_PROVISION + " SET amtallocated = " + amtAllocated + 
		           			" WHERE uy_provisionLine_id = " + provisionlineID;
					DB.executeUpdateEx(upDate, null); 
				}

				// Inserto nuevo registro en temporal final
				
				this.insertProduct(rs.getInt("ad_client_id"), rs.getInt("ad_org_id"), rs.getInt("ad_user_id"), rs.getInt("uy_provision_id"), rs.getInt("uy_provisionLine_id"),  rs.getFloat("amt"), 
								rs.getInt("c_elementvalue_id"), rs.getInt("c_bpartner_id"), rs.getInt("m_product_id"), rs.getInt("c_activity_id_1"), rs.getInt("c_period_id"), rs.getInt("c_currency_id"));
				
				cPeriodID = rs.getInt("c_period_id");
				cBPartnerID = rs.getInt("c_bpartner_id");
				cElementValueID = rs.getInt("c_elementvalue_id");
				mProductID = rs.getInt("m_product_id");
				cActivityID = rs.getInt("c_activity_id_1");
				provisionID = rs.getInt("uy_provision_id");
				provisionlineID = rs.getInt("uy_provisionline_id");
				amtAllocated = Env.ZERO;
			}
			
			amtAllocated = amtAllocated.add(rs.getBigDecimal("amtallocated"));
		}
		
		// Ultimo registro
		String upDate = " UPDATE "+ TABLA_MOLDE_PROVISION + " SET amtallocated = " + amtAllocated + 
       			" WHERE uy_provisionLine_id = " + provisionlineID;
		
		DB.executeUpdateEx(upDate , null); 

		// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
		//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
		//this.changePeriodsHeaders(gridTab);
		
	}
	catch (Exception e)
	{
		throw new AdempiereException(e);
	}
	finally
	{
		DB.close(rs, pstmt);
		rs = null; pstmt = null;
	}

	
}

private void insertProduct(int ad_client_id, int ad_org_id, int ad_user_id, int uy_provision_id, int uy_provisionLine_id, float amt,
		int c_elementvalue_id, int c_bpartner_id, int m_product_id, int c_activity_id_1, int c_period_id, int c_currency_id ) {
	
	String sql = "INSERT INTO " + TABLA_MOLDE_PROVISION + " (ad_client_id, ad_org_id, ad_user_id, uy_provision_id, uy_provisionLine_id, amt, " +
					"c_elementvalue_id, c_bpartner_id, m_product_id, c_activity_id_1, c_period_id, c_currency_id) VALUES (" + ad_client_id + ", " + ad_org_id + ", " +ad_user_id + ", " +
					uy_provision_id + ", " + uy_provisionLine_id + ", "  + amt + ", "+ c_elementvalue_id + ", " + c_bpartner_id + ", " + m_product_id + ", " + c_activity_id_1 +  ", "+ c_period_id + 
						", " + c_currency_id + ")"; 
	
	DB.executeUpdateEx(sql, null);
	
	
}
//Calculo del pendiente de las provisiones, restando lo afectado al total de la provision
private void calculoSaldos() {
	String upDate = " UPDATE "+ TABLA_MOLDE_PROVISION + " SET amtopen = amt - amtallocated";
   			
	
	DB.executeUpdateEx(upDate , null); 
}
//Elimino los registros que no tienen pendiente
private void eliminoSinPendientes() {
	
	String delete = "DELETE FROM " + TABLA_MOLDE_PROVISION + " WHERE amtopen between -0.9 and 0.9 ";
	DB.executeUpdateEx(delete, null);
}

}
