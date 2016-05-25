/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 02/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MFduJournalType
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 02/11/2012
 * @see
 */
public class MFduJournalType extends X_UY_Fdu_JournalType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2759342125199270874L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Fdu_JournalType_ID
	 * @param trxName
	 */
	public MFduJournalType(Properties ctx, int UY_Fdu_JournalType_ID,
			String trxName) {
		super(ctx, UY_Fdu_JournalType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduJournalType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static MFduJournalType forCode(Properties ctx, int FduCod, String trxName) {
		
		String whereClause = X_UY_Fdu_JournalType.COLUMNNAME_UY_FduCod_ID + "='" + FduCod + "'";
		
		MFduJournalType type = new Query(ctx, I_UY_Fdu_JournalType.Table_Name, whereClause, trxName)
		.first();
				
		return type;
		
	}

}
