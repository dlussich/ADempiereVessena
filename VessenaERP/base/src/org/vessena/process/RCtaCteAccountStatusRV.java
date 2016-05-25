/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.beans.ReportAccountStatus;

/**
 * @author OpenUp. Gabriel Vila. 07/11/2011. Issue #902.
 * Informe y Proceso de Estado de Cuenta en formato Report View.
 */
public class RCtaCteAccountStatusRV extends SvrProcess {

	private ReportAccountStatus acctStatusFilters = new ReportAccountStatus();

	public RCtaCteAccountStatusRV() {
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
				
				if (name.equalsIgnoreCase("StartDate")){
					this.acctStatusFilters.dateFrom = (Timestamp)para[i].getParameter();
					this.acctStatusFilters.dateTo = (Timestamp)para[i].getParameter_To();
				}

				if (name.equalsIgnoreCase("C_Currency_ID")) this.acctStatusFilters.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("C_BP_Group_ID")){
					
					if(para[i].getParameter()!=null) this.acctStatusFilters.cBPGroupID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				
				if (name.equalsIgnoreCase("PartnerType")) this.acctStatusFilters.partnerType = (String)para[i].getParameter();
				
				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter() != null) this.acctStatusFilters.customerID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("C_BPartner_ID_P")){
					if (para[i].getParameter() != null) this.acctStatusFilters.vendorID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("AD_User_ID")) this.acctStatusFilters.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Client_ID")) this.acctStatusFilters.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Org_ID")) this.acctStatusFilters.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		RCtaCteAccountStatus report = new RCtaCteAccountStatus(this.acctStatusFilters);
		report.setWaiting(this.getProcessInfo().getWaiting());
		report.execute();
		
		return "OK";
	}

}
