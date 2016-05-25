/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Dec 21, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MStoreLoadOrderList
 * OpenUp Ltda. Issue #5150 
 * Description: Modelo de lista sugerida de productos en la orden de carga de tienda.
 * @author gabriel - Dec 21, 2015
*/
public class MStoreLoadOrderList extends X_UY_StoreLoadOrderList {

	private static final long serialVersionUID = 7876315047300709310L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_StoreLoadOrderList_ID
	 * @param trxName
	*/

	public MStoreLoadOrderList(Properties ctx, int UY_StoreLoadOrderList_ID, String trxName) {
		super(ctx, UY_StoreLoadOrderList_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MStoreLoadOrderList(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
