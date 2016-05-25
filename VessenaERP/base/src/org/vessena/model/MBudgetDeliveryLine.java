/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 22/10/2012
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MBudgetDeliveryLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 22/10/2012
 * @see
 */
public class MBudgetDeliveryLine extends X_UY_BudgetDeliveryLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5405972065941241867L;

	@Override
	protected boolean beforeDelete() {

		//al borrar una linea de entrega debo retornar la cantidad a la linea de fabricacion
		MBudgetDeliveryLine dline = new MBudgetDeliveryLine(getCtx(),this.get_ID(),get_TrxName()); //instancio linea de entrega eliminada

		if(dline!=null){

			MManufLine manufline = new MManufLine(getCtx(),dline.getUY_ManufLine_ID(),get_TrxName()); //obtengo la linea de la orden de fabricacion

			manufline.setQtyDelivered(manufline.getQtyDelivered().subtract(this.getQty()));
			manufline.saveEx();

		}

		return true;
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BudgetDeliveryLine_ID
	 * @param trxName
	 */
	public MBudgetDeliveryLine(Properties ctx, int UY_BudgetDeliveryLine_ID,
			String trxName) {
		super(ctx, UY_BudgetDeliveryLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBudgetDeliveryLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//OpenUp. Nicolas Sarlabos. 29/10/2012
		MManufLine manufline = new MManufLine (getCtx(),this.getUY_ManufLine_ID(),get_TrxName());
		
		if(this.getQty().compareTo(Env.ZERO)<=0) throw new AdempiereException ("La cantidad ingresada debe ser mayor a cero");
		
		if(manufline!=null){
			
			String sql = "";
			BigDecimal qtyAvailable = Env.ZERO;
			BigDecimal qtyDelivered = Env.ZERO;
					
			this.setUY_BudgetLine_ID(manufline.getUY_BudgetLine_ID());
			this.setM_Product_ID(manufline.getM_Product_ID()); //OpenUp. Nicolas Sarlabos. 31/10/2012.
					
			if(newRecord){
				
				if(this.getQty().compareTo(manufline.getQty().subtract(manufline.getQtyDelivered()))<=0){
					
					manufline.setQtyDelivered(manufline.getQtyDelivered().add(this.getQty()));
					manufline.saveEx();
									
				} else throw new AdempiereException ("La cantidad ingresada supera la cantidad disponible para entregas");

			
			} else if (is_ValueChanged("qty")){
				
				BigDecimal qty = manufline.getQty();//obtengo cantidad total en la OF
				
				sql = "select qty from uy_budgetdeliveryline where uy_budgetdeliveryline_id=" + this.get_ID();
				BigDecimal qtyActual = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP);//obtengo cantidad actual en la linea

				sql = "select coalesce(sum(dl.qty),0)" + 
                      " from uy_budgetdeliveryline dl" +
                      " where dl.uy_manufline_id=" + manufline.get_ID() + " and dl.uy_budgetdeliveryline_id <> " + this.get_ID();

				qtyDelivered = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP); //obtengo la cantidad presupuestada menos la cantidad ya ingresada para entregas
				qtyAvailable = qty.subtract(qtyDelivered);

				if(qtyAvailable.compareTo(Env.ZERO)>=0){

					if(this.getQty().compareTo(qtyAvailable)<=0) {

						manufline.setQtyDelivered((manufline.getQtyDelivered().subtract(qtyActual)).add(this.getQty()));
						manufline.saveEx();


					} else throw new AdempiereException ("La cantidad ingresada supera la cantidad disponible para entregas"); 

				}
			}
		}
		return true;
	}
}


