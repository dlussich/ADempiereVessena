package org.openup.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class RCtbDevengamiento extends SvrProcess{

	private String TABLA_MOLDE_DEVENGAMIENTO = "UY_MOLDE_Devengamiento";
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;
	private int cPeriodID = 0;
	private int cBPartnerID = 0;
		
	@Override
	protected void prepare() {
		
		// Obtengo parametros y los recorro
				ProcessInfoParameter[] para = getParameter();
				for (int i = 0; i < para.length; i++) {
					String name = para[i].getParameterName().trim();
					if (name!= null && para[i].getParameter()!=null){
						if (name.equalsIgnoreCase("C_Period_ID")){
							this.cPeriodID= ((BigDecimal)para[i].getParameter()).intValueExact();
						} else if (name.equalsIgnoreCase("AD_User_ID")) {
							adUserID = ((BigDecimal) para[i].getParameter()).intValue();
						} else if (name.equalsIgnoreCase("Ad_Client_ID")) {
							adClientID = ((BigDecimal) para[i].getParameter()).intValueExact();
						}else if (name.equalsIgnoreCase("Ad_Org_ID")) {
							adOrgID = ((BigDecimal) para[i].getParameter()).intValueExact();
						} else if (name.equals("C_BPartner_ID")) {
							cBPartnerID = ((BigDecimal) para[i].getParameter()).intValue();
						}
					}
				}
	}

	@Override
	protected String doIt() throws Exception {
		
		this.deleteData();
		
		this.loadData();
		
		return "OK";
	}

	
	private void deleteData() {

		try {

			String action = "";

			action = " DELETE FROM " + TABLA_MOLDE_DEVENGAMIENTO
					+ " WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action, null);


		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}	


private void loadData() {
		

		String insert = "", sql = "";
		String whereFiltros = "";
		MPeriod periodFilter = null;
		
		try {
			
			if(this.cPeriodID > 0){
				
			periodFilter = new MPeriod(getCtx(), this.cPeriodID, null);
			periodFilter.getStartDate();
			whereFiltros = " and hdr.c_period_id_from <=" + this.cPeriodID + 
						   " and hdr.c_period_id_to >=" + this.cPeriodID;
			}
			
			if (this.cBPartnerID > 0){
				whereFiltros += " and hdr.c_bpartner_id =" + cBPartnerID;  
			}
			
			
			insert = "INSERT INTO "
					+ TABLA_MOLDE_DEVENGAMIENTO
					+ " (ad_client_id, ad_org_id, ad_user_id, c_invoice_id, c_bpartner_id, dateinvoiced, totallines, c_currency_id, "
					+ "c_period_id_from, c_period_id_to, qtyquote, c_doctype_id, amtquote, m_product_id, c_activity_id, c_invoiceline_id, nroquote)";

			sql = " select hdr.ad_client_id, hdr.ad_org_id, "+ adUserID  +", hdr.c_invoice_id, hdr.c_bpartner_id, hdr.dateinvoiced, hdr.totallines, hdr.c_currency_id, " +
					  " hdr.c_period_id_from, hdr.c_period_id_to, hdr.qtyquote, hdr.c_doctype_id, " +
					  " round((line.linenetamt/qtyquote),2) as amtquote, line.m_product_id, " +
					  " line.c_activity_id_1, line.c_invoiceline_id, " +
					  " ((" + this.cPeriodID + " - c_period_id_from) + 1) as nroquote " +
					  " from c_invoice hdr " +
					  " inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
					  " inner join c_invoiceline line on hdr.c_invoice_id = line.c_invoice_id " +				  
					  " where hdr.ad_client_id = " + adClientID + 
					  " and (hdr.dateinvoiced <'" + periodFilter.getStartDate() + "'" +
					  " OR hdr.dateinvoiced >'" + periodFilter.getEndDate() + "')" +
					  " and hdr.docstatus='CO' " +
					  " and hdr.isdevengable = 'Y' " + whereFiltros +
					  " and doc.issotrx='N' " +
					  " and doc.docbasetype IN('API','APC') " +
					  " and line.c_invoiceline_id not in " +  
					  " (select c_invoiceline_id from uy_devengamientoline devl " +
					  " inner join uy_devengamiento dev on devl.uy_devengamiento_id = dev.uy_devengamiento_id " +
					  " where dev.docstatus='CO' and devl.isapproved='Y' and devl.actualquote = ((" + this.cPeriodID + " - hdr.c_period_id_from) + 1)) " +
					  " order by hdr.dateinvoiced";
					
			DB.executeUpdateEx(insert + sql, null);

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}
	
}
