/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 01/12/2012
 */
package org.openup.beans;

import java.sql.Timestamp;

/**
 * org.openup.beans - ReportAccountElement
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 01/12/2012
 * @see
 */
public class ReportAccountElement {

	public Timestamp dateFrom;
	public Timestamp dateTo;
	public int nivel = 9;
	public int cCurrencyID = 0;
	public int cAcctSchemaID = 0;
	public int adUserID = 0;
	public int adClientID = 0;
	public int adOrgID = 0;	
	public String reportType ="";
	public boolean showOtherCurrency = false;
	public boolean showAmtZeroAccounts = false;
	public boolean includeInitialLode = true;
	
	/**
	 * Constructor.
	 */
	public ReportAccountElement() {
	}

}
