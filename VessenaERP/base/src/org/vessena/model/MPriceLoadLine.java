/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPriceList;
import org.compiere.model.Query;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MPriceLoadLine extends X_UY_PriceLoadLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4856468864634153065L;

	/**
	 * @param ctx
	 * @param UY_PriceLoadLine_ID
	 * @param trxName
	 */
	public MPriceLoadLine(Properties ctx, int UY_PriceLoadLine_ID,
			String trxName) {
		super(ctx, UY_PriceLoadLine_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPriceLoadLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/***
	 * Obtiene y retorna modelo de linea segun ID de cabezal y de producto recibidos. 
	 * OpenUp Ltda. Issue #4403
	 * @author Nicolas Sarlabos - 20/07/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MPriceLoadLine forHdrAndProduct(Properties ctx, int hdrID, int prodID, String trxName){

		String whereClause = X_UY_PriceLoadLine.COLUMNNAME_UY_PriceLoad_ID + "=" + hdrID + 
				" and " + X_UY_PriceLoadLine.COLUMNNAME_M_Product_ID + "=" + prodID;

		MPriceLoadLine model = new Query(ctx, I_UY_PriceLoadLine.Table_Name, whereClause, trxName)
		.first();

		return model;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MPriceLoad header = (MPriceLoad)this.getUY_PriceLoad();
		MPriceList priceList = (MPriceList)header.getM_PriceList();
		
		//si no es nuevo registro y no tengo producto elegido (se crea nuevo)
		if(!newRecord && this.getM_Product_ID()<=0){
			
			if(this.getName()==null || this.getName().equalsIgnoreCase("")) throw new AdempiereException("Debe ingresar nombre del producto");
			
			if(this.getC_UOM_ID()<=0) throw new AdempiereException("Debe seleccionar unidad de medida");
			
			if(this.getC_TaxCategory_ID()<=0) throw new AdempiereException("Debe seleccionar categoria de impuesto");		
			
		}		
		
		if(is_ValueChanged(X_UY_PriceLoadLine.COLUMNNAME_NewPrice)) {
			
			BigDecimal oldPrice = this.getPriceSOList();
			if (oldPrice == null) oldPrice = Env.ZERO;
			
			int precision = 0;
			if (priceList != null) precision = priceList.getPricePrecision();
			
			BigDecimal diffAmt = (this.getNewPrice().subtract(oldPrice)).setScale(precision, RoundingMode.HALF_UP);
			
			this.setDifferenceAmt(diffAmt);
		}		
		
		if (this.getDifferenceAmt() == null) this.setDifferenceAmt(Env.ZERO);
		
		return true;
	}

}
