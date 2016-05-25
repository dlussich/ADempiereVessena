/**
 * MAsignaTransporteFact.java
 * 05/01/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MInvoice;
import org.compiere.model.X_C_Invoice;
import org.compiere.util.DB;

/**
 * OpenUp.
 * MAsignaTransporteFact
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 05/01/2011
 */
public class MAsignaTransporteFact extends X_UY_AsignaTransporteFact {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4416091547931693390L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_AsignaTransporteFact_ID
	 * @param trxName
	 */
	public MAsignaTransporteFact(Properties ctx,
			int UY_AsignaTransporteFact_ID, String trxName) {
		super(ctx, UY_AsignaTransporteFact_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAsignaTransporteFact(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * Constructor : Recibe factura.
	 * @param invoice
	 */
	public MAsignaTransporteFact(MInvoice invoice){
		this (invoice.getCtx(), 0, invoice.get_TrxName());
		this.setC_Invoice_ID(invoice.getC_Invoice_ID());
		this.setC_BPartner_ID(invoice.getC_BPartner_ID());
		this.setC_Order_ID(invoice.getC_Order_ID());
	}

	/**
	 * OpenUp. Gabriel Vila. 24/03/2011. Issue #487
	 * Actualizo cantidad de bultos manuales en la factura para dejar la asociacion.
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;

		String action = " UPDATE C_Invoice " + 
					    " SET " + X_C_Invoice.COLUMNNAME_uy_cantbultos_manual + "=" + this.getuy_cantbultos_manual() +
					    " WHERE c_invoice_id = " + this.getC_Invoice_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		return true;
	}	//	afterSave

}
