/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.beans.ReportBankAccountStatus;

/**
 * @author Hp
 *
 */
public class RBankAccountStatusRV extends SvrProcess {

	private ReportBankAccountStatus bankAcctStatusFilters = new ReportBankAccountStatus();

	public RBankAccountStatusRV() {
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
					this.bankAcctStatusFilters.dateFrom = (Timestamp)para[i].getParameter();
					this.bankAcctStatusFilters.dateTo = (Timestamp)para[i].getParameter_To();
				}

				if (name.equalsIgnoreCase("C_BankAccount_ID")){
					if (para[i].getParameter() != null) this.bankAcctStatusFilters.cBankAccountID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("C_DocType_ID")){
					if (para[i].getParameter() != null) this.bankAcctStatusFilters.cdocTypeID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("IsBeforeBalance")){
					this.bankAcctStatusFilters.beforeBalance = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				}
				
				if (name.equalsIgnoreCase("AD_User_ID")) this.bankAcctStatusFilters.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Client_ID")) this.bankAcctStatusFilters.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Org_ID")) this.bankAcctStatusFilters.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		RBankAccountStatus report = new RBankAccountStatus(this.bankAcctStatusFilters);
		report.setWaiting(this.getProcessInfo().getWaiting());
		report.execute();
		
		return "OK";
	}

}
