/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.openup.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentTerm;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * 	Callout for allocations
 *	
 *  @author OpenUp FL
 */
public class CalloutAllocation extends CalloutEngine
{

	/**
	 * OpenUp. Gabriel Vila. 18/10/2011.
	 * Cambio de moneda 1 en afectacion de ctacte.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeCurrency1(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		int cCurrency1 = ((Integer)value).intValue();
		if (cCurrency1 <= 0){
			mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, Env.ZERO);
			return "";
		}

		if (mTab.getValue(X_UY_Allocation.COLUMNNAME_C_Currency2_ID) == null){
			mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, Env.ZERO);
			return "";
		}

		int cCurrency2 = ((Integer)mTab.getValue(X_UY_Allocation.COLUMNNAME_C_Currency2_ID)).intValue();
		
		if (cCurrency1 == cCurrency2){
			mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, Env.ZERO);
			return "";
		}

		Timestamp fecha = (Timestamp)mTab.getValue(X_UY_Allocation.COLUMNNAME_DateTrx);
		BigDecimal dividerate = MConversionRate.getDivideRate(cCurrency1, cCurrency2, fecha, 0, 
								((Integer)mTab.getValue(X_UY_Allocation.COLUMNNAME_AD_Client_ID)).intValue(), 
								((Integer)mTab.getValue(X_UY_Allocation.COLUMNNAME_AD_Org_ID)).intValue());
		
		if (dividerate == null) dividerate = Env.ZERO;
		
		mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, dividerate);
		
		return "";
	}
	
	/**
	 * OpenUp. Gabriel Vila. 18/10/2011.
	 * Cambio de moneda 2 en afectacion de ctacte.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeCurrency2(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		int cCurrency2 = ((Integer)value).intValue();
		if (cCurrency2 <= 0){
			mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, Env.ZERO);
			return "";
		}

		if (mTab.getValue(X_UY_Allocation.COLUMNNAME_C_Currency_ID) == null){
			mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, Env.ZERO);
			return "";
		}

		int cCurrency1 = ((Integer)mTab.getValue(X_UY_Allocation.COLUMNNAME_C_Currency_ID)).intValue();
		
		if (cCurrency2 == cCurrency1){
			mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, Env.ZERO);
			return "";
		}

		Timestamp fecha = (Timestamp)mTab.getValue(X_UY_Allocation.COLUMNNAME_DateTrx);
		BigDecimal dividerate = MConversionRate.getDivideRate(cCurrency1, cCurrency2, fecha, 0, 
								((Integer)mTab.getValue(X_UY_Allocation.COLUMNNAME_AD_Client_ID)).intValue(), 
								((Integer)mTab.getValue(X_UY_Allocation.COLUMNNAME_AD_Org_ID)).intValue());
		
		if (dividerate == null) dividerate = Env.ZERO;
		
		mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, dividerate);
		
		return "";
	}
	

	/**
	 * OpenUp. Gabriel Vila. 18/10/2011.
	 * Cambio de fecha transaccion en afectacion de ctacte.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeDateTrx(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		
		if (mTab.getValue(X_UY_Allocation.COLUMNNAME_C_Currency_ID) == null){
			mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, Env.ZERO);
			return "";
		}

		if (mTab.getValue(X_UY_Allocation.COLUMNNAME_C_Currency2_ID) == null){
			mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, Env.ZERO);
			return "";
		}

		int cCurrency1 = ((Integer)mTab.getValue(X_UY_Allocation.COLUMNNAME_C_Currency_ID)).intValue();
		int cCurrency2 = ((Integer)mTab.getValue(X_UY_Allocation.COLUMNNAME_C_Currency2_ID)).intValue();

		if ((cCurrency1 <= 0) || (cCurrency2 <= 0)){
			mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, Env.ZERO);
			return "";
		}
		
		Timestamp fecha = (Timestamp)value;
		BigDecimal dividerate = MConversionRate.getDivideRate(cCurrency1, cCurrency2, fecha, 0, 
								((Integer)mTab.getValue(X_UY_Allocation.COLUMNNAME_AD_Client_ID)).intValue(), 
								((Integer)mTab.getValue(X_UY_Allocation.COLUMNNAME_AD_Org_ID)).intValue());
		
		if (dividerate == null) dividerate = Env.ZERO;
		
		mTab.setValue(X_UY_Allocation.COLUMNNAME_DivideRate, dividerate);
		
		return "";
	}

	/**
	 * OpenUp. Gabriel Vila. 18/10/2011.
	 * Cambio en el total a afectar de una linea de recibo en una afectacion.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changePaymentAmtAllocated(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		//mTab.dataSave(true);

		/*int uyAllocationID = ((Integer)mTab.getValue(X_UY_Allocation.COLUMNNAME_UY_Allocation_ID)).intValue();
		MAllocation af = new MAllocation(Env.getCtx(), uyAllocationID, null);

		af.updateTotalPayments();*/

		//AWindow win = (AWindow)Env.getWindow(WindowNo);
		//win.getAPanel().actionPerformed(new ActionEvent(this, 0, "Refresh"));
		
		return "";
	}
	
	
	
	/**
	 *  OpenUp FL 26/01/2011  
	 *  When change the payment, control the partner, set the currency and control the date 
	 *  
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */	
	public String setPayment(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		// Get the payment ID, it should be the value
		Integer C_Payment_ID = (Integer) value;
		
		// If the payment is set
		if ((C_Payment_ID != null) && (!(C_Payment_ID.equals(0)))) {
			
			// Get the payment
			MPayment payment = new MPayment(ctx, C_Payment_ID, null);

			// Get the partner id
			Integer C_BPartner_ID = (Integer) mTab.getValue("C_BPartner_ID");

			// Set the partner if not already set
			if (C_BPartner_ID == null || payment.getC_BPartner_ID() != C_BPartner_ID) {
				mTab.setValue("C_BPartner_ID", payment.getC_BPartner_ID());
			}

			// Set the currency from the payment
			mTab.setValue("C_Currency_ID", payment.getC_Currency_ID());
			
			// Set the transaction date only if it's null 
			Timestamp DateTrx=(Timestamp)  mTab.getValue("DateTrx");
			if (DateTrx==null) {
				mTab.setValue("DateTrx", payment.getDateTrx());
				mTab.setValue("DateAcct", payment.getDateTrx());	// And always DateAcct
			}
			else {
				// Reset the payment id if the trnasaction date is lower than the paymet
				if (DateTrx.compareTo(payment.getDateTrx())<0) {
					mTab.setValue("C_Payment_ID",null);
					return("El recibo es posterior");									// TODO: translate			
				}
			}
		}

		return ("");
	}
	
	/**
	 *  OpenUp Nicolas Garcia  
	 *  Update the buisness partner
	 *  
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */	
	public String controlBParnet(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) {
		//Si es = 0 entonces es un caso de Afectacion manual
		Integer nivel =mTab.getTabLevel();
		if (nivel==0){
			Integer C_BPartner_ID = (Integer) value;
			Integer C_Payment_ID = (Integer) mTab.getValue("C_Payment_ID");
			
			//Si el peyment no es vacio DEFENSIVO
			if(C_Payment_ID!=null && C_BPartner_ID!=null){
				// Traigo payment
				MPayment payment = new MPayment(ctx, C_Payment_ID, null);
				if(payment !=null){
					//pregunto si el c_bpartner_id de payment != c_bpartner_id de la ventana
					if(payment.getC_BPartner_ID()!=C_BPartner_ID){
						mTab.setValue("C_Payment_ID", null);
					}
				}
				
			}
			
		}
		
		
		return "";
	}

	/**
	 *  OpenUp FL 20/12/2010  
	 *  Update the amount when changing the invoice, set it to the open amount limited by the payment open amount. 
	 *  Save and refresh lines first to ensure acurate virtual columns
	 *
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */	
	public String changeInvoice(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		// Save and refresh the line to get virtual columns
		if (!(mTab.dataSave(false))) {
			return(Msg.getMsg(ctx, "CannotSave"));
		}
		
		mTab.dataRefresh();

		// Get the open amount of the document and the payment open amount
		BigDecimal grandTotal= (BigDecimal) mTab.getValue("GrandTotal");
		BigDecimal openAmt= (BigDecimal) mTab.getValue("OpenAmt");
		BigDecimal payOpenAmt= (BigDecimal) mTab.getValue("PayOpenAmt");
		
		// Invoices must have a positive totals
		if (grandTotal.compareTo(BigDecimal.ZERO)>=0) {
			
			// If the document open amount is greter than the payment open amount, then set the open amount, else set the document open amount 
			if (openAmt.compareTo(payOpenAmt)>=0) {
				mTab.setValue(MAllocationLine.COLUMNNAME_Amount,payOpenAmt);
			}
			else {
				mTab.setValue(MAllocationLine.COLUMNNAME_Amount,openAmt);
			}			
		}
		else {
			// For credit notes just set the open amount of the credit notes 
			mTab.setValue(MAllocationLine.COLUMNNAME_Amount,openAmt);
		}
		
		return("");
	} 
	
	/**
	 *  OpenUp FL 20/12/2010  
	 *  Validate the entred amount against the open amount and payment grand total.
	 *
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */	
	public String changeAmount(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldvalue) {
		
		// Get the amount entred and the old amount
		BigDecimal amount=(BigDecimal) value;
		BigDecimal oldAmount=(BigDecimal) oldvalue;

		// Get the open amount of the document and the payment open amount
		BigDecimal grandTotal= (BigDecimal) mTab.getValue("GrandTotal");
		BigDecimal openAmt= (BigDecimal) mTab.getValue("OpenAmt");
		BigDecimal payOpenAmt= (BigDecimal) mTab.getValue("PayOpenAmt");
		
		// Invoices must have a positive totals, credit notes have a diferent tratement 
		if (grandTotal.compareTo(BigDecimal.ZERO)>=0) {

			// Negative amount
			if (amount.compareTo(BigDecimal.ZERO)<0) {
				mTab.setValue(MAllocationLine.COLUMNNAME_Amount,oldAmount);
				return("El importe afectado debe ser mayor que 0 por que el saldo del documento es positivo");
			}

			// Amount greater than document grand total
			if (amount.compareTo(grandTotal)>0) {
				mTab.setValue(MAllocationLine.COLUMNNAME_Amount,oldAmount);
				return("El importe afectado es mayor que el total del documento");
			}

			// Amount greater than document open amount 
			if (amount.compareTo(openAmt)>0) {
				mTab.setValue(MAllocationLine.COLUMNNAME_Amount,oldAmount);
				return("El importe afectado es mayor que el saldo documento");
			} 

			// Amount greater than payment open amount
			if (amount.compareTo(payOpenAmt)>0) {
				mTab.setValue(MAllocationLine.COLUMNNAME_Amount,oldAmount);
				return("El importe afectado es mayor que el saldo del recibo");
			}
		} 
		else { 		
			
			// Positive amount is not posible for credit notes
			if (amount.compareTo(BigDecimal.ZERO)>0) {
				mTab.setValue(MAllocationLine.COLUMNNAME_Amount,oldAmount);
				return("El importe afectado debe ser menor que 0 por que el saldo del documento es negativo");
			}

			// Amount lower than document grand total is not posible for credit notes 
			if (amount.compareTo(grandTotal)<0) {
				mTab.setValue(MAllocationLine.COLUMNNAME_Amount,oldAmount);
				return("El total del documento es negativo y el importe afectado debe mayor que el total en este caso");
			}
	
			// Amount lower than document open amount is not posible for credit notes
			if (amount.compareTo(openAmt)<0) {
				mTab.setValue(MAllocationLine.COLUMNNAME_Amount,oldAmount);
				return("El saldo del documento es negativo y el importe afectado debe ser menor que el saldo en este caso");
			}

			// Amount lower than payment open amount is not posible for credit notes 
			if (amount.compareTo(payOpenAmt)>0) {
				mTab.setValue(MAllocationLine.COLUMNNAME_Amount,oldAmount);
				return("El importe afectado es mayor que el saldo del recibo");
			}
		}
		
		return("");
	}

	
	/**
	 *  OpenUp. Gabriel Vila. 22/08/2011. Issue #507.  
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */	
	public String setInvoiceAllocation(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		// Obtnego el id de la nota de credito
		Integer idNotaCredito = (Integer) value;
		
		// Si tengo datos
		if ((idNotaCredito != null) && (!(idNotaCredito.equals(0)))) {
			
			// Obtengo el model de la nota de credito
			MInvoice nc = new MInvoice(ctx, idNotaCredito, null);

			// Get the partner id
			Integer C_BPartner_ID = (Integer) mTab.getValue("C_BPartner_ID");

			// Set the partner if not already set
			if (C_BPartner_ID == null || nc.getC_BPartner_ID() != C_BPartner_ID) {
				mTab.setValue("C_BPartner_ID", nc.getC_BPartner_ID());
			}

			// Set the currency from the nc
			mTab.setValue("C_Currency_ID", nc.getC_Currency_ID());
			
			// Set the transaction date only if it's null 
			Timestamp DateTrx=(Timestamp)  mTab.getValue("DateTrx");
			if (DateTrx==null) {
				mTab.setValue("DateTrx", nc.getDateInvoiced());
				mTab.setValue("DateAcct", nc.getDateInvoiced());	// And always DateAcct
			}
			else {
				// Reset the nc id if the trnasaction date is lower than the nc
				if (DateTrx.compareTo(nc.getDateInvoiced())<0) {
					mTab.setValue("C_InvoiceAllocation_ID",null);
					return("El recibo es posterior");									// TODO: translate			
				}
			}
		}

		return ("");
	}

	/***
	 * Obtiene y setea un termino de pago por defecto segun forma de pago seleccionada
	 * OpenUp Ltda. Issue #76 
	 * @author Gabriel Vila - 18/10/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setPaymentTermDefault(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int uyPaymentRuleID = (Integer)value;
		
		if (uyPaymentRuleID <= 0) return "";

		// Seteo Tipo de forma de Pago
		MPaymentRule rule = new MPaymentRule(ctx, uyPaymentRuleID, null);
		if (rule.get_ID() > 0){
			mTab.setValue("paymentruletype", rule.getpaymentruletype());
		}
		
		// Obtengo termino de pago por defecto para esta forma de pago
		MPaymentTerm payterm = MPaymentTerm.forPaymentRuleDefault(ctx, uyPaymentRuleID, null);		
		
		if (payterm == null) return "";
		if (payterm.get_ID() <= 0) return "";
		
		mTab.setValue("C_PaymentTerm_ID", payterm.get_ID());
		
		
		return "";
	}
	
	
	
}	//	CalloutAllocation
