/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 04/09/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.Query;

/**
 * org.openup.model - MAccountChapter
 * OpenUp Ltda. Issue #18 
 * Description: Modelo de Capitulo Contable
 * @author Gabriel Vila - 04/09/2012
 * @see
 */
public class MAccountChapter extends X_UY_AccountChapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6765321875741468645L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_AccountChapter_ID
	 * @param trxName
	 */
	public MAccountChapter(Properties ctx, int UY_AccountChapter_ID,
			String trxName) {
		super(ctx, UY_AccountChapter_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAccountChapter(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna un capitulo contable segun un Value.
	 * OpenUp Ltda. Issue #18 
	 * @author Gabriel Vila - 04/09/2012
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MAccountChapter forValue (Properties ctx, String value, String trxName)
	{
		String whereClause = X_UY_AccountChapter.COLUMNNAME_Value + " ='" + value + "' ";
		
		MAccountChapter chapter = new Query(ctx, I_UY_AccountChapter.Table_Name, whereClause, trxName)
		.setClient_ID()
		.setOnlyActiveRecords(true)
		.firstOnly();

		return chapter;
		
	}
	
}
