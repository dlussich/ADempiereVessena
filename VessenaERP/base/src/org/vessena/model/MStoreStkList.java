/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Dec 21, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MStoreStkList
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Dec 21, 2015
*/
public class MStoreStkList extends X_UY_StoreStkList {

	private static final long serialVersionUID = 3159574894099786075L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_StoreStkList_ID
	 * @param trxName
	*/

	public MStoreStkList(Properties ctx, int UY_StoreStkList_ID, String trxName) {
		super(ctx, UY_StoreStkList_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MStoreStkList(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna lineas con los productos asociados a esta lista.
	 * OpenUp Ltda. Issue #5150
	 * @author gabriel - Dec 22, 2015
	 * @return
	 */
	public List<MStoreStkListProd> getLines(){
		
		String whereClause = X_UY_StoreStkListProd.COLUMNNAME_UY_StoreStkList_ID + "=" + this.get_ID();
		
		List<MStoreStkListProd> lines = new Query(getCtx(), I_UY_StoreStkListProd.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}
	
}
