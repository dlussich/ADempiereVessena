/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/09/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MDocType;

/**
 * org.openup.model - MTTLoadPlastic
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de carga de plasticos desde Proveedor 
 * @author Gabriel Vila - 20/09/2013
 * @see
 */
public class MTTLoadPlastic extends X_UY_TT_LoadPlastic {

	private static final long serialVersionUID = -4929277396575990983L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_LoadPlastic_ID
	 * @param trxName
	 */
	public MTTLoadPlastic(Properties ctx, int UY_TT_LoadPlastic_ID,
			String trxName) {
		super(ctx, UY_TT_LoadPlastic_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTLoadPlastic(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Setea documento por defecto para este documento.
	 * OpenUp Ltda. Issue #351 
	 * @author Gabriel Vila - 21/02/2012
	 * @see
	 */
	public void setDefaultDocType() {
		
		MDocType doc = MDocType.forValue(getCtx(), "ttloadplastic", null);
		if (doc.get_ID() > 0) this.setC_DocType_ID(doc.get_ID());
		
	}	
	
}
