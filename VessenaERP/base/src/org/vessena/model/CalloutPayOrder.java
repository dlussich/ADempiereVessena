/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 30/04/2013
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInvoice;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - CalloutPayOrder
 * OpenUp Ltda. Issue #348 
 * Description: Callout para Ordenes de Pago.
 * @author Gabriel Vila - 30/04/2013
 * @see
 */
public class CalloutPayOrder extends CalloutEngine {

	
	/***
	 * Al cambiar cuenta bancaria de una linea de generacion de ordenes de pago,
	 * debo setear moneda, tasa de cambio y monto a pagar en moneda bancaria.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 30/04/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeBankAccount(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int cBankAccountID = ((Integer)value).intValue();
		
		if (cBankAccountID <= 0) return "";

		MBankAccount ba = new MBankAccount(Env.getCtx(), cBankAccountID, null);
		
		if (ba.get_ID() > 0){

			// Seteo moneda bancaria
			mTab.setValue(X_UY_PayOrderGenLine.COLUMNNAME_C_Currency_Bank_ID, ba.getC_Currency_ID());

			int uyPayOrderGenID = (Integer)mTab.getValue(X_UY_PayOrderGenLine.COLUMNNAME_UY_PayOrderGen_ID);
			MPayOrderGen ordgen = new MPayOrderGen(ctx, uyPayOrderGenID, null);

			int cInvoiceID = (Integer)mTab.getValue(X_UY_PayOrderGenLine.COLUMNNAME_C_Invoice_ID);
			MInvoice invoice = new MInvoice(ctx, cInvoiceID, null);
			
			MClient client = new MClient(ctx, ordgen.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			
			BigDecimal montoPagar = (BigDecimal)mTab.getValue(X_UY_PayOrderGenLine.COLUMNNAME_amtallocated);
			
			// Obtengo tasa de cambio
			BigDecimal currencyRate = Env.ONE;
			if (invoice.getC_Currency_ID() != ba.getC_Currency_ID()){
				if (ba.getC_Currency_ID() == schema.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(invoice.getC_Currency_ID(), ba.getC_Currency_ID(), ordgen.getDateTrx(), 0, ordgen.getAD_Client_ID(), 0).setScale(3, RoundingMode.HALF_UP);
					if (currencyRate == null){
						throw new AdempiereException("No se pudo obtener Tasa de Cambio para la Fecha : " + ordgen.getDateTrx());
					}

					mTab.setValue(X_UY_PayOrderGenLine.COLUMNNAME_Amount, montoPagar.multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
					
				}
				else{
					currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), invoice.getC_Currency_ID(), ordgen.getDateTrx(), 0, ordgen.getAD_Client_ID(), 0).setScale(3, RoundingMode.HALF_UP);
					if (currencyRate == null){
						throw new AdempiereException("No se pudo obtener Tasa de Cambio para la Fecha : " + ordgen.getDateTrx());
					}

					mTab.setValue(X_UY_PayOrderGenLine.COLUMNNAME_Amount, montoPagar.divide(currencyRate, 2, RoundingMode.HALF_UP));					
				}
			}
			else{
				mTab.setValue(X_UY_PayOrderGenLine.COLUMNNAME_Amount, montoPagar);
			}

			// Seteo tasa de cambio
			mTab.setValue(X_UY_PayOrderGenLine.COLUMNNAME_CurrencyRate, currencyRate);			
		}
		
		return "";
	}

	/***
	 * Al cambiar manualmente la tasa de cambio, debo actualizar monto a pagar en moneda de cuenta bancaria.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 09/05/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeRate(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value, Object oldvalue) {
		
		if (value == null) return "";

		BigDecimal rate = (BigDecimal)value;
		
		if (rate.compareTo(Env.ZERO) <= 0){
			mTab.setValue(X_UY_PayOrderGenLine.COLUMNNAME_CurrencyRate, oldvalue);
			return "";
		}
		
		int cBankAccountID = (Integer)mTab.getValue(X_UY_PayOrderGenLine.COLUMNNAME_C_BankAccount_ID);
		
		if (cBankAccountID <= 0) return "";

		MBankAccount ba = new MBankAccount(Env.getCtx(), cBankAccountID, null);
		
		if (ba.get_ID() > 0){

			int uyPayOrderGenID = (Integer)mTab.getValue(X_UY_PayOrderGenLine.COLUMNNAME_UY_PayOrderGen_ID);
			MPayOrderGen ordgen = new MPayOrderGen(ctx, uyPayOrderGenID, null);

			int cInvoiceID = (Integer)mTab.getValue(X_UY_PayOrderGenLine.COLUMNNAME_C_Invoice_ID);
			MInvoice invoice = new MInvoice(ctx, cInvoiceID, null);
			
			MClient client = new MClient(ctx, ordgen.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			
			BigDecimal montoPagar = (BigDecimal)mTab.getValue(X_UY_PayOrderGenLine.COLUMNNAME_amtallocated);
			
			if (invoice.getC_Currency_ID() != ba.getC_Currency_ID()){
				if (ba.getC_Currency_ID() == schema.getC_Currency_ID()){
					mTab.setValue(X_UY_PayOrderGenLine.COLUMNNAME_Amount, montoPagar.multiply(rate).setScale(2, RoundingMode.HALF_UP));
				}
				else{
					mTab.setValue(X_UY_PayOrderGenLine.COLUMNNAME_Amount, montoPagar.divide(rate, 2, RoundingMode.HALF_UP));					
				}
			}
		}
		
		return "";
	}
	
	
	/***
	 * Al cambiar manualemente el monto a pagar, debo actualizar monto a pagar en moneda de la cuenta bancaria.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 09/05/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldvalue
	 * @return
	 */
	public String changeAmtAllocated(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		BigDecimal amt = (BigDecimal)value;
		
		int cBankAccountID = (Integer)mTab.getValue(X_UY_PayOrderGenLine.COLUMNNAME_C_BankAccount_ID);
		if (cBankAccountID <= 0) return "";
		MBankAccount ba = new MBankAccount(Env.getCtx(), cBankAccountID, null);

		BigDecimal rate = (BigDecimal)mTab.getValue(X_UY_PayOrderGenLine.COLUMNNAME_CurrencyRate);
		if (rate == null) return "";
		
		if (ba.get_ID() > 0){

			int uyPayOrderGenID = (Integer)mTab.getValue(X_UY_PayOrderGenLine.COLUMNNAME_UY_PayOrderGen_ID);
			MPayOrderGen ordgen = new MPayOrderGen(ctx, uyPayOrderGenID, null);

			int cInvoiceID = (Integer)mTab.getValue(X_UY_PayOrderGenLine.COLUMNNAME_C_Invoice_ID);
			MInvoice invoice = new MInvoice(ctx, cInvoiceID, null);
			
			MClient client = new MClient(ctx, ordgen.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			
			if (invoice.getC_Currency_ID() != ba.getC_Currency_ID()){
				if (ba.getC_Currency_ID() == schema.getC_Currency_ID()){
					mTab.setValue(X_UY_PayOrderGenLine.COLUMNNAME_Amount, amt.multiply(rate).setScale(2, RoundingMode.HALF_UP));
				}
				else{
					mTab.setValue(X_UY_PayOrderGenLine.COLUMNNAME_Amount, amt.divide(rate, 2, RoundingMode.HALF_UP));					
				}
			}
			else{
				mTab.setValue(X_UY_PayOrderGenLine.COLUMNNAME_Amount, amt);
			}
		}
		
		return "";
	}
	
	/***
	 * Al seleccionar un medio de pago en la emision de medios de pago, seteo datos asociados.
	 * OpenUp Ltda. Issue #5009
	 * @author gabriel - Nov 10, 2015
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changePaymentRule(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int id = ((Integer)value).intValue();
		
		if (id <= 0) return "";

		MPaymentRule model = new MPaymentRule(Env.getCtx(), id, null);

		// Seteo si requiere o no libreta de medio de pago
		if (model.get_ID() > 0){
			mTab.setValue(X_UY_PayEmit.COLUMNNAME_IsStocked, model.get_ValueAsBoolean("IsStocked"));
		}
		
		return "";
	}
	
	
	//Ini OpenUp 04/02/2016 Issue #5427 --> Descuentos en cascada para ventana Generador de órdenes de pago tab Orden de Pago 
	/**
	 * Calcula el porcentaje sobre el monto a pagar al modificar el primer % de descuento
	 * @author OpenUp SBT Issue #5427 4/2/2016 10:09:35
	 */
	public String calculateDiscount1OP(Properties ctx, int WindowNo, GridTab mTab,
					GridField mField, Object value){
		if (value == null) return "";

		BigDecimal ptje = (BigDecimal) value;
				
		int payID = Integer.valueOf(mTab.get_ValueAsString("UY_PayOrder_ID"));//Obtengo ID de orden 
				
		BigDecimal afectDirecta = this.getAfectacionDirectaInicialOP(mTab,payID); //AFectacion directa Inicial
				
		if(ptje.compareTo(Env.ZERO)>0){
					
			mTab.setValue("TotalDiscounts", this.recalcularDescuentoOP(afectDirecta,mTab));
				
		}else if(ptje.compareTo(Env.ZERO)==0){//Si el primer descuento es cero el resto de los descuentos tiene que ser cero
					
			mTab.setValue("TotalDiscounts", this.recalcularDescuentoOP(afectDirecta,mTab));	
			mTab.setValue("Discount2",Env.ZERO);
					
		}else{
			mTab.setValue("Discount",Env.ZERO);
		}
				
		if (afectDirecta == null) {
			mTab.setValue("Discount",Env.ZERO);
				
		}

		return "";
	}
	/**
	 *Calcula el porcentaje sobre el monto a pagar al modificar el segundo % de descuento
	 * @author OpenUp SBT Issue#  3/2/2016 10:51:35
	 */
	public String calculateDiscount2OP(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		if (value == null) return "";
		
		BigDecimal ptje = (BigDecimal) value;
		int payID = Integer.valueOf(mTab.get_ValueAsString("UY_PayOrder_ID"));
		
		BigDecimal pmerDto = (BigDecimal) mTab.getValue("Discount");
		BigDecimal afectDirecta = this.getAfectacionDirectaInicialOP(mTab,payID); //AFectacion directa Inicial
		
		if(ptje.compareTo(Env.ZERO)>0 && pmerDto.compareTo(Env.ZERO)>0){
			
			mTab.setValue("TotalDiscounts", this.recalcularDescuentoOP(afectDirecta,mTab));
			
		}else if(ptje.compareTo(Env.ZERO)==0){//Si el descuento es cero tengo que recalcular descuento total
			
			mTab.setValue("Discount3",Env.ZERO);
			mTab.setValue("TotalDiscounts", this.recalcularDescuentoOP(afectDirecta,mTab));
			
		}else{
			mTab.setValue("Discount2",Env.ZERO);
		}

		return "";
	}
	/**
	 * Calcula el porcentaje sobre el monto a pagar al modificar el tercer % de descuento
	 * @author OpenUp SBT Issue#  3/2/2016 10:51:35
	 */
	public String calculateDiscount3OP(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		if (value == null) return "";
		BigDecimal ptje = (BigDecimal) value;
		
		int payID = Integer.valueOf(mTab.get_ValueAsString("UY_PayOrder_ID"));
		BigDecimal segDto = (BigDecimal) mTab.getValue("Discount2");
		BigDecimal afectDirecta = this.getAfectacionDirectaInicialOP(mTab,payID); //AFectacion directa Inicial

		if(ptje.compareTo(Env.ZERO)>0 && segDto.compareTo(Env.ZERO)>0){
			
			mTab.setValue("TotalDiscounts", this.recalcularDescuentoOP(afectDirecta,mTab));
			
		}else if(ptje.compareTo(Env.ZERO)==0){//Si el descuento es cero tengo que recalcular descuento total

			mTab.setValue("Discount4",Env.ZERO);
			mTab.setValue("TotalDiscounts", this.recalcularDescuentoOP(afectDirecta,mTab));
			
		}else{
			mTab.setValue("Discount3",Env.ZERO);
		}

		return "";
	}
	
	/**
	 * Calcula el porcentaje sobre el monto a pagar al modificar el cuarto % de descuento
	 * Generador de Ordenes de Pago
	 * @author OpenUp SBT Issue #5427  4/2/2016 11:51:35
	 */
	public String calculateDiscount4OP(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		if (value == null) return "";
		BigDecimal ptje = (BigDecimal) value;
		int payID = Integer.valueOf(mTab.get_ValueAsString("UY_PayOrder_ID"));
				
		BigDecimal tercerDto = (BigDecimal) mTab.getValue("Discount3");
	
		BigDecimal afectDirecta = this.getAfectacionDirectaInicialOP(mTab,payID); //AFectacion directa Inicial
		
		if(ptje.compareTo(Env.ZERO)>0 && tercerDto.compareTo(Env.ZERO)>0){
			
			mTab.setValue("TotalDiscounts", this.recalcularDescuentoOP(afectDirecta,mTab));

		}else if(ptje.compareTo(Env.ZERO)==0 ){	
			mTab.setValue("TotalDiscounts", this.recalcularDescuentoOP(afectDirecta,mTab));
		}else{
			mTab.setValue("Discount4",Env.ZERO);
		}
	
		return "";
	}
	 
	/**
	 * Calcula el monto de descuento total al modificar el valor de descuento manual en 
	 * Generador de Ordenes de Pago
	 * @author OpenUp SBT Issue #5427  4/2/2016 11:51:35
	 */
	public String calculateDiscountManualOP(Properties ctx, int WindowNo, GridTab mTab,
				GridField mField, Object value){
			if (value == null) return "";
			BigDecimal descManual = (BigDecimal) value;
			int payID = Integer.valueOf(mTab.get_ValueAsString("UY_PayOrder_ID"));
			
			if(descManual.compareTo(Env.ZERO)>=0){
				BigDecimal afectDirecta = this.getAfectacionDirectaInicialOP(mTab, payID);
				mTab.setValue("TotalDiscounts", this.recalcularDescuentoOP(afectDirecta,mTab));
			}
		
			return "";
		}
	/**
	 * Obtener el monto a pagar de la orden en
	 * Generador de Ordenes de Pago
	 * @author OpenUp SBT Issue #5427  4/2/2016 11:51:35
	 */
	private BigDecimal getAfectacionDirectaInicialOP(GridTab mTab, int payID) {
		BigDecimal afectDirectaInicial = Env.ZERO;
		
		String sql = "";ResultSet rs = null;PreparedStatement pstmt = null;
		try{
			/*  select sum(a.Amount) from UY_PayOrderLine a join c_invoice b on a.c_invoice_id = b.c_invoice_id where a.uy_payorder_id = 1001098
	and b.c_doctype_id = 1000202*/
			//sql = "select sum(Amount) from UY_PayOrderLine where UY_PayOrder_Id = ? ";
			sql = "SELECT sum(a.Amount) FROM UY_PayOrderLine a "
					+ "JOIN c_invoice b ON a.c_invoice_id = b.c_invoice_id "
					+ " WHERE b.c_doctype_id = 1000202 AND a.uy_payorder_id = ? "	;
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, payID);
			rs = pstmt.executeQuery ();
			
			while (rs.next()) {
				afectDirectaInicial = rs.getBigDecimal(1);
			}
			//SBT cambia el calculo los desc en cascada son sobre el monto a afectar
//			BigDecimal descManulActual = (BigDecimal) mTab.getValue("DiscountAmt");
//			if(descManulActual==null) descManulActual = Env.ZERO;
//			BigDecimal montoResguardo = (BigDecimal) mTab.getValue("AmtResguardo");
//			if(montoResguardo==null) montoResguardo = Env.ZERO;
//			//Al subtotal le resto el descuento manual y el monto de resguardo
//			BigDecimal afectDctaActual = afectDirectaInicial.subtract(descManulActual.add(montoResguardo));
			BigDecimal afectDctaActual = afectDirectaInicial;
			return afectDctaActual;

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return afectDirectaInicial;
	}
	
	/**
	 * Calcular Descuentos en cascada para ventana
	 * Generador de Ordenes de Pago
	 * @author OpenUp SBT Issue #5427  4/2/2016 11:51:35
	 */
	private BigDecimal recalcularDescuentoOP(BigDecimal afectDirecta,
			GridTab mTab) {
		BigDecimal primDto = (BigDecimal) mTab.getValue("Discount");
		BigDecimal sgdoDto = (BigDecimal) mTab.getValue("Discount2");
		BigDecimal tecrDto = (BigDecimal) mTab.getValue("Discount3");
		BigDecimal ctoDto = (BigDecimal) mTab.getValue("Discount4");
		BigDecimal afectDirectaInicial = afectDirecta;
		BigDecimal totalDescuento =  Env.ZERO;
		//Obtengo la afectación directa original
		
		if(primDto.compareTo(Env.ZERO)>0 && afectDirecta!=null){
			totalDescuento  =  afectDirectaInicial.multiply(primDto).divide(new BigDecimal("100"));
			if(sgdoDto.compareTo(Env.ZERO)>0){
				afectDirecta = afectDirectaInicial.subtract(totalDescuento);
				totalDescuento  =  totalDescuento.add(afectDirecta.multiply(sgdoDto).divide(new BigDecimal("100")));
				if(tecrDto.compareTo(Env.ZERO)>0){
					afectDirecta = afectDirectaInicial.subtract(totalDescuento);
					totalDescuento  =  totalDescuento.add(afectDirecta.multiply(tecrDto).divide(new BigDecimal("100")));
					if(ctoDto.compareTo(Env.ZERO)>0){
						afectDirecta = afectDirectaInicial.subtract(totalDescuento);
						totalDescuento  =  totalDescuento.add(afectDirecta.multiply(ctoDto).divide(new BigDecimal("100")));
					}
				}
			}
		}
		BigDecimal descManual = (BigDecimal) mTab.getValue("DiscountAmt");
		totalDescuento = totalDescuento.add(descManual);
		return totalDescuento.setScale(2, RoundingMode.HALF_UP);
	}//Fin OpenUp Descuentos en cascada para ventana Generador de Ordenes de Pago

}
