/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCountry;
import org.compiere.model.MCurrency;
import org.compiere.model.MProduct;
import org.compiere.model.MTax;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MTRBudgetDetail extends X_UY_TR_BudgetDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -650569710285391416L;

	/**
	 * @param ctx
	 * @param UY_TR_BudgetDetail_ID
	 * @param trxName
	 */
	public MTRBudgetDetail(Properties ctx, int UY_TR_BudgetDetail_ID,
			String trxName) {
		super(ctx, UY_TR_BudgetDetail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRBudgetDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
			
		if(this.getNationalPercentage().compareTo(Env.ZERO) < 0) throw new AdempiereException ("El porcentaje nacional no puede ser menor a cero");
		if(this.getNationalPercentage().compareTo(Env.ONEHUNDRED) > 0) throw new AdempiereException ("El porcentaje nacional no puede ser mayor a 100%");
		if(this.getInterPercentage().compareTo(Env.ZERO) < 0) throw new AdempiereException ("El porcentaje internacional no puede ser menor a cero");
		if(this.getInterPercentage().compareTo(Env.ONEHUNDRED) > 0) throw new AdempiereException ("El porcentaje internacional no puede ser mayor a 100%");

		if(this.getInternationalAmt().compareTo(Env.ZERO)>0 || this.getNationalAmt().compareTo(Env.ZERO)>0){

			BigDecimal totalAmt = this.getNationalAmt().add(this.getInternationalAmt());

			this.setPriceEntered(totalAmt);
			
		}
		
		this.calculate(newRecord);

		return true;
	}

	/***
	 * 
	 * OpenUp Ltda. Issue #3169
	 * @author Nicolas Sarlabos - 06/11/2014
	 * Metodo que realiza calculos y setea campos en la linea de detalle de cotizacion.
	 */
	private void calculate(boolean newRecord) {
		
		MTRBudgetLine bLine = new MTRBudgetLine(getCtx(),this.getUY_TR_BudgetLine_ID(),get_TrxName());
		MTRBudget budget = new MTRBudget(getCtx(),bLine.getUY_TR_Budget_ID(),get_TrxName());
		MTRWay way = (MTRWay)budget.getUY_TR_Way();
		MCountry countryFrom = new MCountry(getCtx(),way.getC_Country_ID(),get_TrxName());
		MCountry countryTo = new MCountry(getCtx(),way.getC_Country_ID_1(),get_TrxName());
		MProduct prod = (MProduct) this.getM_Product();
		BigDecimal internationalAmt = Env.ZERO;
		BigDecimal nationalAmt = Env.ZERO;
		BigDecimal interPercentage = Env.ZERO;
		BigDecimal nationalPercentage = Env.ZERO;
		BigDecimal productAmt = Env.ZERO;
		MTax tax = null;
		Boolean isImpUY = false;
				
		//importacion uruguaya
		if(countryFrom.getCountryCode().equalsIgnoreCase("BR") && countryTo.getCountryCode().equalsIgnoreCase("UY")){
			
			tax = MTax.forValue(getCtx(), "basico", get_TrxName());
			
			if(tax==null) throw new AdempiereException("No se pudo obtener tipo de impuesto Basico");
			
			isImpUY = true;			
			
		} else {
			
			tax = MTax.forValue(getCtx(), "exento", get_TrxName());
			
			if(tax==null) throw new AdempiereException("No se pudo obtener tipo de impuesto Exento");
			
			isImpUY = false;			
					
		}
		
		this.setC_Tax_ID(tax.get_ID());	

		if(prod != null && prod.getM_Product_ID() > 0){
			if(prod.getValue().equalsIgnoreCase("fleteimp") || prod.getValue().equalsIgnoreCase("fleteexp")){
				
				if(this.isAplicaValorem()){

					if(this.forBudgetAndValorem(budget) == null){

						MTRBudgetDetail det = new MTRBudgetDetail(this.getCtx(), 0, this.get_TrxName());		
						MProduct valorem = MProduct.forValue(this.getCtx(), "valorem", this.get_TrxName());
						det.setM_Product_ID(valorem.get_ID());
						det.setUY_TR_BudgetLine_ID(this.getUY_TR_BudgetLine_ID());	
						det.setInterPercentage(this.getInterPercentage());
						det.setNationalPercentage(this.getNationalPercentage());
						det.setValoremPercentage(budget.getValoremPercentage());
						det.saveEx();
					}					
				}else{
					MTRBudgetDetail det = this.forBudgetAndValorem(budget);
					if( det != null && det.get_ID() > 0) det.delete(true);
				}
				
				productAmt = this.getPriceEntered();

				if(productAmt.compareTo(new BigDecimal(0)) > 0){			
				
					if(this.getInternationalAmt() == null || this.getInternationalAmt().compareTo(Env.ZERO)==0 || (is_ValueChanged("InterPercentage") && this.getInterPercentage().compareTo(Env.ZERO)>0)){
						internationalAmt = (this.getInterPercentage().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(productAmt).setScale(2, RoundingMode.HALF_UP));
						this.setInternationalAmt(internationalAmt);
					} else {
						
						interPercentage = (this.getInternationalAmt().multiply(Env.ONEHUNDRED)).divide(productAmt, 2, RoundingMode.HALF_UP);
						this.setInterPercentage(interPercentage);
						
					}
					
					if((this.getNationalAmt()) == null || this.getNationalAmt().compareTo(Env.ZERO)==0 || (is_ValueChanged("NationalPercentage") && this.getNationalPercentage().compareTo(Env.ZERO)>0)){
						nationalAmt = (this.getNationalPercentage().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(productAmt).setScale(2, RoundingMode.HALF_UP));
						this.setNationalAmt(nationalAmt);
					} else {
						
						nationalPercentage = (this.getNationalAmt().multiply(Env.ONEHUNDRED)).divide(productAmt, 2, RoundingMode.HALF_UP);
						this.setNationalPercentage(nationalPercentage);						
						
					}
					
					this.setLineNetAmt(this.getPriceEntered());
					this.setTaxAmt(isImpUY);
					
				}		

			}else if((newRecord || is_ValueChanged("valorempercentage")) && prod.getValue().equalsIgnoreCase("valorem")){
				
				//si moneda de carga y de factura son distintas, realizo conversion de valor de la carga a moneda de factura
				productAmt = bLine.getProductAmt();
				int curFromID = bLine.getC_Currency_ID();
				int curToID = budget.getC_Currency_ID();
				
				if(curFromID != curToID){
					
					BigDecimal dividerate = MConversionRate.getDivideRate(curFromID, curToID, budget.getDateTrx(), 0, this.getAD_Client_ID(), this.getAD_Org_ID());				
					
					if (dividerate == null || dividerate.compareTo(Env.ZERO)==0){
						
						ADialog.warn(0,null,"No se obtuvo tasa de cambio para fecha de cotizacion");
						dividerate = Env.ONE;
					}
					
					productAmt = productAmt.divide(dividerate, RoundingMode.HALF_UP);					
					
				} else productAmt = bLine.getProductAmt();;	

				if(productAmt.compareTo(new BigDecimal(0)) > 0){
					
					BigDecimal valoremPercentage = (BigDecimal)this.get_Value("ValoremPercentage");
					
					if(valoremPercentage==null) valoremPercentage = Env.ZERO;
					
					BigDecimal mercaderiaValorem = (valoremPercentage.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(productAmt).setScale(2, RoundingMode.HALF_UP));

					interPercentage = this.getInterPercentage();
					nationalPercentage = this.getNationalPercentage();
					
					if(nationalPercentage.compareTo(Env.ZERO)==0) nationalPercentage = Env.ONEHUNDRED;					
					
					internationalAmt = (interPercentage.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(mercaderiaValorem).setScale(2, RoundingMode.HALF_UP));
					nationalAmt = (nationalPercentage.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(mercaderiaValorem).setScale(2, RoundingMode.HALF_UP));

					this.setInternationalAmt(internationalAmt);
					this.setNationalAmt(nationalAmt);
					this.setPriceEntered(nationalAmt.add(internationalAmt));
					this.setPriceActual(nationalAmt.add(internationalAmt));
					this.setLineNetAmt(nationalAmt.add(internationalAmt));
					this.setTaxAmt(isImpUY);
					
				}		 
			} else {
				
				productAmt = this.getPriceEntered();

				if(productAmt.compareTo(new BigDecimal(0)) > 0){				

					if(this.getInternationalAmt() == null || this.getInternationalAmt().compareTo(Env.ZERO)==0){
						internationalAmt = (this.getInterPercentage().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP)).multiply(productAmt).setScale(2, RoundingMode.HALF_UP);
						this.setInternationalAmt(internationalAmt);
					}
					
					if(this.getNationalAmt() == null || this.getNationalAmt().compareTo(Env.ZERO)==0){
						nationalAmt = (this.getNationalPercentage().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP)).multiply(productAmt).setScale(2, RoundingMode.HALF_UP);
						this.setNationalAmt(nationalAmt);
					}
										
					this.setLineNetAmt(this.getPriceEntered());
					this.setTaxAmt(isImpUY);
					
				}					
			}
		}else{
			if(newRecord){	
				/*
				MProduct valorem = MProduct.forValue(this.getCtx(), "servicios", this.get_TrxName());
				this.setM_Product_ID(valorem.get_ID());

				this.set_Value("InterPercentage",null);
				this.set_Value("NationalPercentage",null);
				this.set_Value("InternationalAmt",null);
				this.set_Value("NationalAmt",null);
				this.set_Value("ValoremPercentage",null);
				this.set_Value("PriceEntered",null);
				*/
				
			}else{
				if(this.get_ValueAsBoolean("AplicaValorem")){
					throw new AdempiereException("Los Servicios no aplican AD_VALOREM");
				}
			}				
			
		}	
		
		this.setPriceList(this.getPriceEntered());
		this.setPriceLimit(this.getPriceEntered());
		this.setQtyEntered(Env.ONE);
		this.setQtyInvoiced(Env.ONE);		
		
	}
	
	public void setTaxAmt (boolean isImpUY)
	{
		BigDecimal TaxAmt = Env.ZERO;
		BigDecimal amount = getLineNetAmt();

		if (getC_Tax_ID() == 0)
			return;

		MTax tax = MTax.get (getCtx(), getC_Tax_ID());

		MTRBudgetLine bLine = new MTRBudgetLine(getCtx(), this.getUY_TR_BudgetLine_ID(),get_TrxName());
		MTRBudget budget = new MTRBudget(getCtx(), bLine.getUY_TR_Budget_ID(),get_TrxName());

		//si es importacion o nacional, y a su vez NO es en transito, se cobra IVA basico al terr. nacional
		if(isImpUY && !budget.isInTransit()){

			amount = (BigDecimal)this.get_Value("NationalAmt"); 

		} else amount = Env.ZERO;

		if(amount==null) amount = Env.ZERO;

		if(amount.compareTo(Env.ZERO)==0) {

			tax = MTax.forValue(getCtx(), "exento", get_TrxName());
			this.setC_Tax_ID(tax.get_ID());

		} else {

			tax = MTax.forValue(getCtx(), "basico", get_TrxName());
			this.setC_Tax_ID(tax.get_ID());

		}

		TaxAmt = tax.calculateTax(amount, false, 2);

		setLineTotalAmt(this.getPriceEntered().add(TaxAmt));

		super.setTaxAmt (TaxAmt);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		MTRBudgetLine bLine = (MTRBudgetLine)this.getUY_TR_BudgetLine();
		
		this.updateLine(bLine);
				
		return true;
	}

	private void updateLine(MTRBudgetLine bLine) {
		
		if(bLine != null && bLine.get_ID() > 0){

			//actualizo importe del flete
			String sql = "select coalesce(sum (det.linenetamt),0)" + 
                         " from uy_tr_budgetdetail det" + 
                         " inner join m_product p on det.m_product_id = p.m_product_id" +
                         " where p.value in ('fleteimp','fleteexp','fletenac') and det.uy_tr_budgetline_id = " + bLine.get_ID();
			BigDecimal amtFlete = DB.getSQLValueBDEx(get_TrxName(), sql);

			DB.executeUpdateEx("update uy_tr_budgetline set amount = " + amtFlete + " where uy_tr_budgetline_id = " + bLine.get_ID(), get_TrxName());
			
			//actualizo importe total
			sql = "select coalesce(sum (det.linenetamt),0)" + 
                    " from uy_tr_budgetdetail det" + 
                    " inner join m_product p on det.m_product_id = p.m_product_id" +
                    " where det.uy_tr_budgetline_id = " + bLine.get_ID();
			BigDecimal amtTotal = DB.getSQLValueBDEx(get_TrxName(), sql);

			DB.executeUpdateEx("update uy_tr_budgetline set amount2 = " + amtTotal + " where uy_tr_budgetline_id = " + bLine.get_ID(), get_TrxName());

			String value = "";

			MCurrency cur = (MCurrency)bLine.getC_Currency(); //instancio la moneda
			
			//Valor de mercaderia a texto con formato . para miles y , para decimales para campo de capacidad de carga del vehiculo
			BigDecimal impaux = amtFlete.setScale(2, RoundingMode.HALF_UP);
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);
			df.setGroupingUsed(true);
			String amt = df.format(impaux);

			if (amt != null){
				amt = amt.replace(".", ";");
				amt = amt.replace(",", ".");
				amt = amt.replace(";", ",");
			}

			if(bLine.isConsolidated()){

				value = cur.getCurSymbol() + " " + amt + " a consolidar.";

				DB.executeUpdateEx("update uy_tr_budgetline set description = '" + value + "' where uy_tr_budgetline_id = " + bLine.get_ID(), get_TrxName());
												

			} else {

				if(bLine.getUY_TR_TruckType_ID() > 0){

					MTRTruckType truck = (MTRTruckType)bLine.getUY_TR_TruckType(); //instancio tipo de vehiculo

					// Capacidad de carga a texto con formato . para miles y , para decimales para campo de capacidad de carga del vehiculo
					impaux = truck.getWeight().setScale(0, RoundingMode.HALF_UP);
					df = new DecimalFormat();
					df.setMaximumFractionDigits(0);
					df.setMinimumFractionDigits(0);
					df.setGroupingUsed(true);
					String weight = df.format(impaux);

					if (weight != null){
						weight = weight.replace(".", ";");
						weight = weight.replace(",", ".");
						weight = weight.replace(";", ",");
					}

					value = cur.getCurSymbol() + " " + amt + " por " + truck.getName() + " de hasta " + truck.getVolume().setScale(0, RoundingMode.HALF_UP) + " m3./" + weight + " kgs.";

					DB.executeUpdateEx("update uy_tr_budgetline set description = '" + value + "' where uy_tr_budgetline_id = " + bLine.get_ID(), get_TrxName());

				}		

			}	

		}	
		
	}

	@Override
	protected boolean afterDelete(boolean success) {
		
		MTRBudgetLine bLine = (MTRBudgetLine)this.getUY_TR_BudgetLine();
		
		this.updateLine(bLine);
			
		return true;
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #3169
	 * @author Nicolas Sarlabos - 06/11/2014
	 * Metodo que .
	 * @return
	 */
	private MTRBudgetDetail forBudgetAndValorem(MTRBudget hdr){
		
		MTRBudgetDetail value = null;
		
		MProduct valorem = MProduct.forValue(this.getCtx(), "valorem", this.get_TrxName());
				
		String sql = "select det.uy_tr_budgetdetail_id" +
		             " from uy_tr_budgetdetail det" +
				     " inner join uy_tr_budgetline line on det.uy_tr_budgetline_id = line.uy_tr_budgetline_id" +
				     " where line.uy_tr_budget_id = " + hdr.get_ID() +
		             " and det.m_product_id = " + valorem.get_ID();
		
		int ID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(ID > 0) value = new MTRBudgetDetail(getCtx(),ID,get_TrxName());
		
		return value;				
	}

}
