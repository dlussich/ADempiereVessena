/**
 * MInvoiceBook.java
 * 29/03/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * OpenUp.
 * MInvoiceBook
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 29/03/2011
 */
public class MCheckBook extends X_UY_CheckBook {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836398863738909053L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InvoiceBook_ID
	 * @param trxName
	 */
	public MCheckBook(Properties ctx, int UY_CheckBook_ID, String trxName) {
		super(ctx, UY_CheckBook_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCheckBook(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.model.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		// Control finish date before control start date
		if (this.getDueDate().compareTo(this.getDateStart())<0) {
			throw new IllegalArgumentException("Fecha de vencimiento anterior a la fecha de comienzo de uso");		// TODO: translate
		}
		
		// Control finish date before control start date
		if (this.getDocumentNoEnd()<this.getDocumentNoStart()) {	
			throw new IllegalArgumentException("Numero final menor que el numero inicial");							// TODO: translate
		}
		
		return(true);
	}

}
