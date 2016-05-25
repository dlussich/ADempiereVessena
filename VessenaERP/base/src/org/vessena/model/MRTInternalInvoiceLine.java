/**
 * @author OpenUp SBT Issue#  1/2/2016 11:12:08
 */
package org.openup.model;

import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.process.DocumentEngine;

import java.util.List;
import java.util.Properties;

/**
 * @author OpenUp SBT Issue#  1/2/2016 11:12:08
 *
 */
public class MRTInternalInvoiceLine extends X_UY_RT_InternalInvoiceLine {

	/**
	 * @author OpenUp SBT Issue#  1/2/2016 11:12:11
	 * @param ctx
	 * @param UY_RT_InternalInvoiceLine_ID
	 * @param trxName
	 */
	public MRTInternalInvoiceLine(Properties ctx,
			int UY_RT_InternalInvoiceLine_ID, String trxName) {
		super(ctx, UY_RT_InternalInvoiceLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author OpenUp SBT Issue#  1/2/2016 11:12:11
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTInternalInvoiceLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static List<MRTInternalInvoiceLine> forInternalInvoiceID(
			Properties ctx, int get_ID, String trxName) {
		String whereClause = X_UY_RT_InternalInvoiceLine.COLUMNNAME_UY_RT_InternalInvoice_ID + " = " + get_ID +
				" AND " + X_UY_RT_InternalInvoiceLine.COLUMNNAME_IsActive+ " = 'Y' "
				+ " AND "+ X_UY_RT_InternalInvoiceLine.COLUMNNAME_amtallocated + " IS NOT NULL "
				+ " AND "+ X_UY_RT_InternalInvoiceLine.COLUMNNAME_amtdocument + " IS NOT NULL ";

		
		List<MRTInternalInvoiceLine> lines = new Query(ctx, I_UY_RT_InternalInvoiceLine.Table_Name, whereClause, trxName)
		.list();
		
		return lines;
	}

	
	@Override
	protected boolean beforeDelete() {

		if( this.getUY_RT_InternalInvoice().getDocStatus() != DocumentEngine.STATUS_Drafted){
			throw new AdempiereException("No se puede borrar el registro por razones de auditoría ");
		}
		return super.beforeDelete();
	}
}
