/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/10/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MBudgetBOM
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 21/10/2012
 * @see
 */
public class MBudgetBOM extends X_UY_BudgetBOM {

	@Override
	protected boolean beforeSave(boolean newRecord) {
		//OpenUp. Nicolas Sarlabos. 04/03/2013. #449. Se cambian tabulaciones por espacios en blanco para la
		//correcta impresion de la OF
		if(!this.getmaterials().equalsIgnoreCase("")){
			
			String cadena = this.getmaterials();
			
			cadena = cadena.replaceAll("\t", "     ");
			
			this.setmaterials(cadena);
		
		
		}
		//Fin OpenUp.
		
		/*if(this.getDescription()!=null && this.getDescription()!="" && this.getM_Product_ID()>0){
			throw new AdempiereException ("Debe seleccionar un producto o ingresar una descripcion, pero no ambos");
		}
		
		if((this.getDescription()==null || this.getDescription()=="") && this.getM_Product_ID()<=0){
			throw new AdempiereException ("Debe seleccionar producto o ingresar una descripcion");
		}
		
		if(this.getQty().compareTo(Env.ZERO)<=0) throw new AdempiereException ("Cantidad debe ser mayor a cero");
				
		//si tengo descripcion procedo a crear el producto a partir de la misma
		if(this.getDescription()!=null && this.getDescription()!=""){
			
			if(newRecord){

				//si es nuevo registro creo nuevo producto a partir de la descripcion de material ingresada
				MProduct prod = new MProduct(getCtx(),0,get_TrxName());

				prod.setName(this.getDescription());
				prod.setC_UOM_ID(MUOM.getDefault_UOM_ID(getCtx()));
				prod.setIsPurchased(true);
				prod.setIsSold(false);
				prod.setIsStocked(true);
				prod.setM_Product_Category_ID(1000040);
				MTaxCategory taxCat = MTaxCategory.getDefault();
				if(taxCat.get_ID() > 0) prod.setC_TaxCategory_ID(taxCat.get_ID());
				prod.saveEx();

				if(prod.get_ID() > 0) this.setM_Product_ID(prod.get_ID());

			} else {

				//si se modifico la descripcion entonces obtengo el producto y actualizo el nombre
				if(is_ValueChanged("description")){

					MProduct prod = new MProduct(getCtx(),this.getM_Product_ID(),get_TrxName());
					prod.setName(this.getDescription());
					prod.saveEx();


				}
			}	
		}
		
		//si tengo producto seteo el nombre al campo descripcion
		if(this.getM_Product_ID()>0) this.setDescription(this.getM_Product().getName());*/
		
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1918088772404393172L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BudgetBOM_ID
	 * @param trxName
	 */
	public MBudgetBOM(Properties ctx, int UY_BudgetBOM_ID, String trxName) {
		super(ctx, UY_BudgetBOM_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBudgetBOM(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
