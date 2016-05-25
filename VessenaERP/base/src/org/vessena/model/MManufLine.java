/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/10/2012
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.Env;

/**
 * org.openup.model - MManufLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 21/10/2012
 * @see
 */
public class MManufLine extends X_UY_ManufLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -422159984216831213L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ManufLine_ID
	 * @param trxName
	 */
	public MManufLine(Properties ctx, int UY_ManufLine_ID, String trxName) {
		super(ctx, UY_ManufLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MManufLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		//OpenUp. Nicolas Sarlabos. 31/10/2012.
		MBudgetLine budgetline = new MBudgetLine (getCtx(),this.getUY_BudgetLine_ID(),get_TrxName());
		if(budgetline.get_ID() > 0) this.setM_Product_ID(budgetline.getM_Product_ID());
		//Fin OpenUp.
		
		if(this.getQty().compareTo(Env.ZERO)<=0) throw new AdempiereException ("Cantidad debe ser mayor a cero");
				
		return true;
	}
	
	/**
	 * 
	 * OpenUp Ltda. Issue # 
	 * Description: metodo que devuelve el importe total de una linea de presupuesto
	 * @author Nicolas Sarlabos - 12/11/2012
	 * @see
	 */
	public BigDecimal getPrice(int budgetLineID) {
		
		BigDecimal price = Env.ZERO;
				
		MBudgetLine budline = new MBudgetLine (getCtx(), budgetLineID, get_TrxName());
		price = (budline.getamt1().add(budline.getamt2())).add(budline.getamt3()).setScale(2, RoundingMode.HALF_UP);
			
		return price;
	}
	
	/**
	 * 
	 * OpenUp Ltda.
	 * Description: metodo que devuelve la linea de la orden de fabricacion que tenga la uy_manufOder_Id y el m_product_id pasado por parametro
	 * @author Guillermo Brust. 02/05/2013
	 * 
	 */
	public static MManufLine getMManufLineForOrderAndProduct(Properties ctx, int manufOrderId, int productId) {
		
		String whereClause = X_UY_ManufLine.COLUMNNAME_UY_ManufOrder_ID + "=" + manufOrderId +
				" AND " + X_UY_ManufLine.COLUMNNAME_M_Product_ID + "=" + productId;				
		
		MManufLine line = new Query(ctx, I_UY_ManufLine.Table_Name, whereClause, null).first();
		
		return line;
	}

	
}
