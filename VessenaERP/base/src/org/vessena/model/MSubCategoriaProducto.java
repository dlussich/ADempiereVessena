/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 07/01/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MSubCategoriaProducto
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 07/01/2015
 * @see
 */
public class MSubCategoriaProducto extends X_UY_SubCategoria_Producto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7931959378321088116L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_SubCategoria_Producto_ID
	 * @param trxName
	 */
	public MSubCategoriaProducto(Properties ctx,
			int UY_SubCategoria_Producto_ID, String trxName) {
		super(ctx, UY_SubCategoria_Producto_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MSubCategoriaProducto(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Obtiene y retorna modelo segun value recibido.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 07/01/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MSubCategoriaProducto forValue(Properties ctx, String value, String trxName){
	
		String whereClause = X_UY_SubCategoria_Producto.COLUMNNAME_Value + "='" + value + "'";
		
		MSubCategoriaProducto model = new Query(ctx, I_UY_SubCategoria_Producto.Table_Name, whereClause, trxName).first();
		
		return model;
		
	}
}
