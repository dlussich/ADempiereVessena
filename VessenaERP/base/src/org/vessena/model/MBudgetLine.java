/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/10/2012
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MUOM;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MBudgetLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 21/10/2012
 * @see
 */
public class MBudgetLine extends X_UY_BudgetLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -446628271225635085L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BudgetLine_ID
	 * @param trxName
	 */
	public MBudgetLine(Properties ctx, int UY_BudgetLine_ID, String trxName) {
		super(ctx, UY_BudgetLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBudgetLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//OpenUp. Nicolas Sarlabos. 26/10/2012
		int qtyCount = 0;
		MBudget budget = new MBudget (getCtx(),this.getUY_Budget_ID(),get_TrxName());
		
		if(!this.isApprovedQty1() && !this.isApprovedQty2() && !this.isApprovedQty3()) this.setQtyEntered(Env.ZERO); //OpenUp. Nicolas Sarlabos. 07/05/2014. #2094.
		
		if(this.isApprovedQty1()){
			this.setQtyEntered(this.getqty1());
			qtyCount ++;
			if(this.getprice1().compareTo(Env.ZERO)<=0) throw new AdempiereException ("El importe para la cantidad debe ser mayor a 0");
		}
		
		if(this.isApprovedQty2()){
			this.setQtyEntered(this.getqty2());
			qtyCount ++;
			if(this.getprice2().compareTo(Env.ZERO)<=0) throw new AdempiereException ("El importe para la cantidad debe ser mayor a 0");
		}
		
		if(this.isApprovedQty3()){
			this.setQtyEntered(this.getqty3());
			qtyCount ++;
			if(this.getprice3().compareTo(Env.ZERO)<=0) throw new AdempiereException ("El importe para la cantidad debe ser mayor a 0");
		}
		
		if(this.getprice1().compareTo(Env.ZERO)>0 && this.getqty1().compareTo(Env.ZERO)<=0){
			
			throw new AdempiereException ("La cantidad para el precio 1 debe ser mayor a 0");
			
		} else if(this.getprice2().compareTo(Env.ZERO)>0 && this.getqty2().compareTo(Env.ZERO)<=0) {
			
			throw new AdempiereException ("La cantidad para el precio 2 debe ser mayor a 0");
			
		} else if(this.getprice3().compareTo(Env.ZERO)>0 && this.getqty3().compareTo(Env.ZERO)<=0){
			
			throw new AdempiereException ("La cantidad para el precio 3 debe ser mayor a 0");
		}
				
		if(qtyCount > 1) throw new AdempiereException ("No se permite aprobar mas de una cantidad por concepto");
				
		//seteo los importes con descuento incluido
		
		//OpenUp. Guillermo Brust. 05/08/2013. ISSUE#1187
		//Se debe tomar solo las dos cifras despues de la coma en el precio con descuento y se admiten hasta 12 lugares despues de la coma en el descuento
		BigDecimal discount = (this.getDiscount().divide(Env.ONEHUNDRED)).setScale(12, RoundingMode.HALF_UP); //obtengo porcentaje de descuento
					
		this.setamt1(this.getqty1().multiply(this.getprice1().subtract(this.getprice1().multiply(discount)).setScale(2, RoundingMode.HALF_UP)));
		this.setamt2(this.getqty2().multiply(this.getprice2().subtract(this.getprice2().multiply(discount)).setScale(2, RoundingMode.HALF_UP)));
		this.setamt3(this.getqty3().multiply(this.getprice3().subtract(this.getprice3().multiply(discount)).setScale(2, RoundingMode.HALF_UP)));
		//Fin OpenUp
		
		//Fin OpenUp.
			
		//OpenUp. Nicolas Sarlabos. 31/10/2012
		if(budget.getserie().equalsIgnoreCase("B")){

			if(newRecord){

				//creo nuevo producto a partir del titulo del trabajo (necesario para facturacion)
				MProduct prod = new MProduct(getCtx(),0,get_TrxName());

				prod.setName(budget.getWorkName());
				prod.setC_UOM_ID(MUOM.getDefault_UOM_ID(getCtx()));
				prod.setIsPurchased(false);
				prod.setIsSold(true);
				prod.setIsStocked(false);
				prod.setM_Product_Category_ID(1000040);
				MTaxCategory taxCat = MTaxCategory.getDefault();
				if(taxCat.get_ID() > 0) prod.setC_TaxCategory_ID(taxCat.get_ID());
				prod.saveEx();

				if(prod.get_ID() > 0) this.setM_Product_ID(prod.get_ID());
				this.setDescription(budget.getWorkName());
				
				//al campo descripcion le seteo el nombre del producto presupuestado (titulo del presupuesto)
				this.setDescription(budget.getWorkName());

			}
					
		} else if(budget.getserie().equalsIgnoreCase("A")){
			
			if(newRecord){
				
				//si el usuario ingreso simultaneamente un producto y un concepto, el producto tiene la prioridad

				if(this.getM_Product_ID()>0){
					
					MProduct prod = new MProduct(getCtx(),this.getM_Product_ID(),get_TrxName());
					this.setDescription(prod.getName());

				} else {

					//creo nuevo producto a partir de la descripcion (necesario para facturacion)
					MProduct prod = new MProduct(getCtx(),0,get_TrxName());

					prod.setName(this.getDescription());
					prod.setC_UOM_ID(MUOM.getDefault_UOM_ID(getCtx()));
					prod.setIsPurchased(false);
					prod.setIsSold(true);
					prod.setIsStocked(false);
					prod.setM_Product_Category_ID(1000040);
					MTaxCategory taxCat = MTaxCategory.getDefault();
					if(taxCat.get_ID() > 0) prod.setC_TaxCategory_ID(taxCat.get_ID());
					prod.saveEx();

					if(prod.get_ID() > 0) this.setM_Product_ID(prod.get_ID());

				}

			} else {
				
				//si se modifico la descripcion entonces obtengo el producto y actualizo el nombre
				if(is_ValueChanged("description")){
					
					MProduct prod = new MProduct(getCtx(),this.getM_Product_ID(),get_TrxName());
					prod.setName(this.getDescription());
					prod.saveEx();
								
					
				}
				//si se eligio otro producto entonces actualizo la descripcion en la linea
				if(is_ValueChanged("m_product_id")){
					
					MProduct prod = new MProduct(getCtx(),this.getM_Product_ID(),get_TrxName());
					this.setDescription(prod.getName());
					
				}
			
			}

		}
		//Fin OpenUp
	
		return true;
	}

	/**
	 * 
	 * OpenUp.	issue #86
	 * Descripcion : Metodo que recorre las lineas del presupuesto y determina si el mismo esta aprobado, poniendo en true
	 * el check del cabezal
	 * @author  Nicolas Sarlabos 
	 * Fecha : 28/10/2012
	 */
	
	private void approveBudget() throws SQLException {
		

		MBudget budget = new MBudget(getCtx(),this.getUY_Budget_ID(),get_TrxName()); //obtengo cabezal

		boolean approved = false; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		sql = "SELECT isapprovedqty1,isapprovedqty2,isapprovedqty3,uy_budgetline_id" +
				" FROM uy_budgetline" +
				" WHERE uy_budget_id = ? ";
		
		pstmt = DB.prepareStatement(sql, get_TrxName());
		pstmt.setInt(1, budget.get_ID());
		rs = pstmt.executeQuery();

		while (rs.next()){  
	
						
			if((rs.getString("isapprovedqty1").equalsIgnoreCase("Y")) || (rs.getString("isapprovedqty2").equalsIgnoreCase("Y")) || (rs.getString("isapprovedqty3").equalsIgnoreCase("Y"))){
				approved = true; //si al menos una cantidad de una linea fue aprobada, entonces el presupuesto esta aprobado
			}

		}
				
		budget.setIsApproved(approved);
		budget.saveEx();
		

	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		try {
			approveBudget(); //llamo a metodo que determina si el presupuesto esta aprobado
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return true;
	}
	


}
