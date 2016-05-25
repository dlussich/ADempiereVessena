/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.beans.ReportBalanceCtaCte;

/**
 * @author OpenUp. Gabriel Vila. 07/11/2011. Issue #902.
 * Informe y Proceso de Balancete cuenta corriente en formato Report View.
 */
public class RCtaCteBalanceRV extends SvrProcess {

	private ReportBalanceCtaCte balanceFilters = new ReportBalanceCtaCte();
	
	public RCtaCteBalanceRV() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				
				if (name.equalsIgnoreCase("DateTo")) this.balanceFilters.dateTo = (Timestamp)para[i].getParameter();
				if (name.equalsIgnoreCase("C_Currency_ID")) this.balanceFilters.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("C_BP_Group_ID")){
					if (para[i].getParameter() != null) this.balanceFilters.cBPGroupID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				
				if (name.equalsIgnoreCase("PartnerType")) this.balanceFilters.partnerType = (String)para[i].getParameter();

				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter() != null) this.balanceFilters.customerID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("C_BPartner_ID_P")){
					if (para[i].getParameter() != null) this.balanceFilters.vendorID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("AD_User_ID")) this.balanceFilters.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Client_ID")) this.balanceFilters.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Org_ID")) this.balanceFilters.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		RCtaCteBalance report = new RCtaCteBalance(this.balanceFilters);
		report.setWaiting(this.getProcessInfo().getWaiting());
		report.execute();
		
		return "OK";	}

}
