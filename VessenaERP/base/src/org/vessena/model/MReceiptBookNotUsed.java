/**
 * MInvoiceBook.java
 * 29/03/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * OpenUp.
 * MInvoiceBookControl
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 29/03/2011
 */
public class MReceiptBookNotUsed extends X_UY_ReceiptBookNotUsed {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836398863738909053L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InvoiceBookNotUsed_ID
	 * @param trxName
	 */
	public MReceiptBookNotUsed(Properties ctx, int UY_ReceiptBookNotUsed_ID, String trxName) {
		super(ctx, UY_ReceiptBookNotUsed_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MReceiptBookNotUsed(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the invoice book, the parent
	 */
	public MReceiptBookControl getParent()
	{
		return(new MReceiptBookControl(getCtx(),this.getUY_ReceiptBookControl_ID(), get_TrxName()));
	}	//	getParent
	
	
	
	/**
	 * 	Called before Save for Pre-Save Operation
	 * 	@param newRecord new record
	 *	@return true if record can be saved
	 */
	protected boolean beforeSave(boolean newRecord)	{

		// Get the parent, it will be used many times
		MReceiptBookControl receiptBookControl=this.getParent();

		// End number outside range
		if (!((this.getDocumentNoNotUsed()>=receiptBookControl.getDocumentNoStart())&&(this.getDocumentNoNotUsed()<=receiptBookControl.getDocumentNoEnd()))) {
			throw new IllegalArgumentException("Numero esta fuera de rango del control");									// TODO: translate
		}
		
		
		return(true);
	}
	
}
