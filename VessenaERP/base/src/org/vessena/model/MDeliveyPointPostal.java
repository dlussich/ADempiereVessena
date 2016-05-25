/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 23/09/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MDeliveryPointPostal
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de zona de entrega de un punto de distribucion.
 * @author Gabriel Vila - 23/09/2013
 * @see
 */
public class MDeliveyPointPostal extends X_UY_DeliveyPointPostal {

	private static final long serialVersionUID = -3287799106270343931L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_DeliveyPointPostal_ID
	 * @param trxName
	 */
	public MDeliveyPointPostal(Properties ctx, int UY_DeliveyPointPostal_ID,
			String trxName) {
		super(ctx, UY_DeliveyPointPostal_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDeliveyPointPostal(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		return true;
	}

	
	/***
	 * Obtengo modelo segun localidad y codigo postal recibidos.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 26/09/2013
	 * @see
	 * @param ctx
	 * @param localidad
	 * @param codPostal
	 * @param trxName
	 * @return
	 */
	public static MDeliveyPointPostal forLocalidadPostal(Properties ctx, String codPostal, String trxName){
		
		String whereClause = X_UY_DeliveyPointPostal.COLUMNNAME_Postal + "='" + codPostal + "'" +
					" AND " + X_UY_DeliveyPointPostal.COLUMNNAME_IsSelected + "='Y'";
		
		MDeliveyPointPostal model = new Query(ctx, I_UY_DeliveyPointPostal.Table_Name, whereClause, trxName).first();
		
		return model;
		
	}
	
	
}
