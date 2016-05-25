package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.beans.ReportBankStatus;


public class RBankStatusRV extends SvrProcess {

	private ReportBankStatus bankStatusFilters = new ReportBankStatus();
	
	public RBankStatusRV() {
		
	}

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++){
			String name = para[i].getParameterName().trim();		
			if(name != null){
				if (name.equalsIgnoreCase("DateTrx")){
					this.bankStatusFilters.date = (Timestamp)para[i].getParameter();
				}
				if (name.equalsIgnoreCase("C_BankAccount_ID")){
					if(para[i].getParameter() != null){
						this.bankStatusFilters.cBankAccountID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}else{
						this.bankStatusFilters.cBankAccountID = -1;
					}
				}
				this.bankStatusFilters.adUserID = this.getAD_User_ID();

			}
		}		
	}

	@Override
	protected String doIt() throws Exception {
		RBankStatus report = new RBankStatus(this.bankStatusFilters);
		report.setWaiting(this.getProcessInfo().getWaiting());
		report.execute();
		
		return "OK";
	}

}
