/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 17/06/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MProdAttribute
 * OpenUp Ltda. Issue #4404 
 * Description: Modelo de atributos de productos (se confunde con la MProductAttribute que 
 * es la clase que tiene la asociacion de uno de estos atributos con un producto determinado).
 * @author Gabriel Vila - 17/06/2015
 * @see
 */
public class MProdAttribute extends X_UY_ProdAttribute {

	private static final long serialVersionUID = 1092718365804526289L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ProdAttribute_ID
	 * @param trxName
	 */
	public MProdAttribute(Properties ctx, int UY_ProdAttribute_ID,
			String trxName) {
		super(ctx, UY_ProdAttribute_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProdAttribute(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author SBouissa 7/9/2015
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MProdAttribute forValue(Properties ctx, String value,
			String trxName) {
		String whereClause = X_UY_ProdAttribute.COLUMNNAME_Value + " = '" + value + "'" +													
				" AND " + X_UY_ProdAttribute.COLUMNNAME_IsActive + " = 'Y'";

		MProdAttribute model = new Query(ctx, I_UY_ProdAttribute.Table_Name, whereClause, trxName).first();		
		
		return model;	
	}

}
