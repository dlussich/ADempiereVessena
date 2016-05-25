/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 26/12/2012
 */
package org.openup.beans;

import java.math.BigDecimal;

import org.compiere.util.Env;

/**
 * org.openup.beans - InvoiceLineAmtByProdAct
 * OpenUp Ltda. Issue #49 
 * Description: Suma de lineas de factura agrupadas por producto y centro de costo.
 * @author Gabriel Vila - 26/12/2012
 * @see
 */
public class InvoiceLineAmtByProdAct {

	
	public int cInvoiceID = 0;
	public BigDecimal sumAmt = Env.ZERO;
	public BigDecimal sumQty = Env.ZERO;
	public int mProductID = 0;
	public int cActivityID = 0;
	
	/**
	 * Constructor.
	 */
	public InvoiceLineAmtByProdAct() {

	}

}
