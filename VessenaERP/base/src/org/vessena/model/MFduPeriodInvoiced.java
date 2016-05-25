/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MFduPeriodInvoiced extends X_UY_Fdu_PeriodInvoiced {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6365034067351477869L;

	/**
	 * @param ctx
	 * @param UY_Fdu_PeriodInvoiced_ID
	 * @param trxName
	 */
	public MFduPeriodInvoiced(Properties ctx, int UY_Fdu_PeriodInvoiced_ID,
			String trxName) {
		super(ctx, UY_Fdu_PeriodInvoiced_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduPeriodInvoiced(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 19/09/2013. ISSUE #
	 * Método que devuelve un modelo de esta tabla segun fduFileID y periodID
	 * Se usa para saber si un periodo tiene una factura hecha
	 * 
	 */
	public static MFduPeriodInvoiced forFduFileAndPeriod(Properties ctx, int fduFileID, int periodID){
		
		String whereClause = X_UY_Fdu_PeriodInvoiced.COLUMNNAME_UY_FduFile_ID + " = " + fduFileID +
							" AND " + X_UY_Fdu_PeriodInvoiced.COLUMNNAME_C_Period_ID + " = " + periodID;
		
		MFduPeriodInvoiced model = new Query(ctx, I_UY_Fdu_PeriodInvoiced.Table_Name, whereClause, null)
		.first();		
		
		return model;
	}

}
