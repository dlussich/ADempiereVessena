/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCurrency;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MTRBudgetLine extends X_UY_TR_BudgetLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3087857043841790158L;

	/**
	 * @param ctx
	 * @param UY_TR_BudgetLine_ID
	 * @param trxName
	 */
	public MTRBudgetLine(Properties ctx, int UY_TR_BudgetLine_ID, String trxName) {
		super(ctx, UY_TR_BudgetLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRBudgetLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
			
		if(newRecord || is_ValueChanged("Amount") || is_ValueChanged("UY_TR_TruckType_ID") || is_ValueChanged("IsConsolidated")){
			
			String value = "";
			
			MCurrency cur = (MCurrency)this.getC_Currency(); //instancio la moneda
			
			if(this.isConsolidated()){
				
				value = cur.getCurSymbol() + " " + this.getAmount() + " a consolidar.";
				
				this.setDescription(value);				
				
			} else {
				
				if(this.getUY_TR_TruckType_ID() > 0){
					
					MTRTruckType truck = (MTRTruckType)this.getUY_TR_TruckType(); //instancio tipo de vehiculo
										
					// Capacidad de carga a texto con formato . para miles y , para decimales para campo de capacidad de carga del vehiculo
					BigDecimal impaux = truck.getWeight().setScale(0, RoundingMode.HALF_UP);
					DecimalFormat df = new DecimalFormat();
					df.setMaximumFractionDigits(0);
					df.setMinimumFractionDigits(0);
					df.setGroupingUsed(true);
					String weight = df.format(impaux);
					
					if (weight != null){
						weight = weight.replace(".", ";");
						weight = weight.replace(",", ".");
						weight = weight.replace(";", ",");
					}
					
					//Valor de mercaderia a texto con formato . para miles y , para decimales para campo de capacidad de carga del vehiculo
					impaux = this.getAmount().setScale(2, RoundingMode.HALF_UP);
					df = new DecimalFormat();
					df.setMaximumFractionDigits(2);
					df.setMinimumFractionDigits(2);
					df.setGroupingUsed(true);
					String amt = df.format(impaux);
					
					if (amt != null){
						amt = amt.replace(".", ";");
						amt = amt.replace(",", ".");
						amt = amt.replace(";", ",");
					}
									
					value = cur.getCurSymbol() + " " + amt + " por " + truck.getName() + " de hasta " + truck.getVolume().setScale(0, RoundingMode.HALF_UP) + " m3./" + weight + " kgs.";
					
					this.setDescription(value);
						
				}		
				
			}		
				
		}
		
		if(this.isApproved() && this.getAmount().compareTo(Env.ZERO)<=0) throw new AdempiereException("No se puede aprobar la oferta con monto de flete cero");
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if(newRecord && success){
			
			MTRBudget budget = (MTRBudget)this.getUY_TR_Budget();
			MTRWay way = (MTRWay)budget.getUY_TR_Way();
			MProduct prod = (MProduct)way.getM_Product();

			MTRBudgetDetail det = new MTRBudgetDetail(this.getCtx(), 0, this.get_TrxName());
			det.setM_Product_ID(prod.get_ID());
			det.setUY_TR_BudgetLine_ID(this.get_ID());
			det.setValoremPercentage(Env.ZERO);
			det.saveEx();		
			
		}		
		
		return true;
	}

	/***
	 * Obtiene y retorna lineas de detalle de esta oferta de cotizacion.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 20/12/2014
	 * @see
	 * @return
	 */
	public List<MTRBudgetDetail> getLines(){

		String whereClause = X_UY_TR_BudgetDetail.COLUMNNAME_UY_TR_BudgetLine_ID + "=" + this.get_ID();

		List<MTRBudgetDetail> lines = new Query(getCtx(), I_UY_TR_BudgetDetail.Table_Name, whereClause, get_TrxName())
		.setOrderBy(X_UY_TR_BudgetDetail.COLUMNNAME_UY_TR_BudgetDetail_ID)
		.list();

		return lines;
	}

}
