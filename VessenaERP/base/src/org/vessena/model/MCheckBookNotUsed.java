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
public class MCheckBookNotUsed extends X_UY_CheckBookNotUsed {

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
	public MCheckBookNotUsed(Properties ctx, int UY_CheckBookNotUsed_ID, String trxName) {
		super(ctx, UY_CheckBookNotUsed_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCheckBookNotUsed(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the invoice book, the parent
	 */
	public MCheckBookControl getParent()
	{
		return(new MCheckBookControl(getCtx(), this.getUY_CheckBookControl_ID(), get_TrxName()));
	}	//	getParent
	
	
	
	/**
	 * 	Called before Save for Pre-Save Operation
	 * 	@param newRecord new record
	 *	@return true if record can be saved
	 */
	protected boolean beforeSave(boolean newRecord)	{

		// Get the parent, it will be used many times
		//MCheckBookControl checkBookControl = new MCheckBookControl(getCtx(), this.getUY_CheckBookControl_ID(), get_TrxName());  
			//this.getParent();

		// End number outside range
//		if (!((this.getDocumentNoNotUsed()>=checkBookControl.getDocumentNoStart())&&(this.getDocumentNoNotUsed()<=checkBookControl.getDocumentNoEnd()))) {
		//	throw new IllegalArgumentException("Numero esta fuera de rango del control");									// TODO: translate
	//	}
		
		
		return(true);
	}
	
}
