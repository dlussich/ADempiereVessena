/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 03/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MPOSectionCategory
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 03/11/2012
 * @see
 */
public class MPOSectionCategory extends X_UY_POSectionCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9189021431928107891L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_POSectionCategory_ID
	 * @param trxName
	 */
	public MPOSectionCategory(Properties ctx, int UY_POSectionCategory_ID,
			String trxName) {
		super(ctx, UY_POSectionCategory_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPOSectionCategory(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	
	/***
	 * Obtiene y retorna lista de productos de sector - categoria de compras.
	 * OpenUp Ltda. Issue #88 
	 * @author Gabriel Vila - 03/11/2012
	 * @see
	 * @return
	 */
	public List<MPOSectionProduct> getSectionProducts() {

		String whereClause = X_UY_POSectionProduct.COLUMNNAME_UY_POSectionCategory_ID + "=" + this.get_ID();
		
		List<MPOSectionProduct> lines = new Query(getCtx(), I_UY_POSectionProduct.Table_Name, whereClause, null)
		.list();
		
		return lines;
	}

}
