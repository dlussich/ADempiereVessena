/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 01/12/2012
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.beans.ReportAccountElement;

/**
 * org.openup.process - RCtbAccountElementRV
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 01/12/2012
 * @see
 */
public class RCtbAccountElementRV extends SvrProcess {

	private ReportAccountElement reportFilters = new ReportAccountElement();
	
	
	/**
	 * Constructor.
	 */
	public RCtbAccountElementRV() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 01/12/2012
	 * @see
	 */
	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				
				if (name.equalsIgnoreCase("DateTrx")){
					this.reportFilters.dateFrom = (Timestamp)para[i].getParameter();
					this.reportFilters.dateTo = (Timestamp)para[i].getParameter_To();
				}

				if (name.equalsIgnoreCase("ReportType")){
					if (para[i].getParameter() != null) this.reportFilters.reportType = para[i].getParameter().toString();
				}
				if (name.equalsIgnoreCase("C_Currency_ID")){
					if (para[i].getParameter() != null) this.reportFilters.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("C_AcctSchema_ID")){
					this.reportFilters.cAcctSchemaID = ((BigDecimal)para[i].getParameter()).intValueExact();				
				}
				
				if (name.equalsIgnoreCase("nivel")){
					this.reportFilters.nivel = Integer.parseInt(((String)para[i].getParameter()));
				}
				
				if (name.equalsIgnoreCase("SinSaldo")){
					this.reportFilters.showAmtZeroAccounts = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				}

				if (name.equalsIgnoreCase("IsInitialLoad")){
					this.reportFilters.includeInitialLode = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				}

				if (name.equalsIgnoreCase("OtherCurrency")){
					this.reportFilters.showOtherCurrency = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				}
				
				if (name.equalsIgnoreCase("AD_User_ID")) this.reportFilters.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Client_ID")) this.reportFilters.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Org_ID")) this.reportFilters.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 01/12/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		RCtbAccountElement report = new RCtbAccountElement(this.reportFilters);
		report.setWaiting(this.getProcessInfo().getWaiting());
		report.execute();
		
		return "OK";
	}

}
