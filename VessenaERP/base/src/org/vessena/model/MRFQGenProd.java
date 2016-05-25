/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/06/2012
 */
 
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRFQGenProd
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 18/06/2012
 * @see
 */
public class MRFQGenProd extends X_UY_RFQGen_Prod {

	private static final long serialVersionUID = -3203797914746703893L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RFQGen_Prod_ID
	 * @param trxName
	 */
	public MRFQGenProd(Properties ctx, int UY_RFQGen_Prod_ID, String trxName) {
		super(ctx, UY_RFQGen_Prod_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRFQGenProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Obtiene y retorna lista de proveedores asociados a este producto en un proceso
	 * de generacion de solicitudes de cotizacion.
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 07/11/2012
	 * @see
	 * @return
	 */
	public List<MRFQGenVendor> getVendors(){
		
		String whereClause = X_UY_RFQGen_Vendor.COLUMNNAME_UY_RFQGen_Prod_ID + "=" + this.get_ID();
		
		List<MRFQGenVendor> lines = new Query(getCtx(), I_UY_RFQGen_Vendor.Table_Name, whereClause, get_TrxName())
		.setOnlyActiveRecords(true)
		.list();
		
		return lines;
		
	}
}
