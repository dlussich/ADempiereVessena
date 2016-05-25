/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/01/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MFDUControlType
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 15/01/2013
 * @see
 */
public class MFDUControlType extends X_UY_FDU_ControlType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -808822620041232662L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_FDU_ControlType_ID
	 * @param trxName
	 */
	public MFDUControlType(Properties ctx, int UY_FDU_ControlType_ID,
			String trxName) {
		super(ctx, UY_FDU_ControlType_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFDUControlType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna lineas de este tipo de control.
	 * OpenUp Ltda. Issue #133 
	 * @author Hp - 15/01/2013
	 * @see
	 * @return
	 */
	public List<MFDUControlTypeLine> getLines() {

		String whereClause = X_UY_FDU_ControlTypeLine.COLUMNNAME_UY_FDU_ControlType_ID + "=" + this.get_ID() +
				" AND " + X_UY_FDU_ControlTypeLine.COLUMNNAME_IsActive + "='Y' ";
		
		List<MFDUControlTypeLine> lines = new Query(getCtx(), I_UY_FDU_ControlTypeLine.Table_Name, whereClause, get_TrxName())
		.setOrderBy(X_UY_FDU_ControlTypeLine.COLUMNNAME_SeqNo).list();
		
		return lines;
		
	}

}
