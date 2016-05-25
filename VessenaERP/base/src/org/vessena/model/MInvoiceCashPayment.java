/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/10/2012
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInvoice;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.util.Env;

/**
 * org.openup.model - MInvoiceCashPayment
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 18/10/2012
 * @see
 */
public class MInvoiceCashPayment extends X_UY_InvoiceCashPayment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4245753274659392229L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_InvoiceCashPayment_ID
	 * @param trxName
	 */
	public MInvoiceCashPayment(Properties ctx, int UY_InvoiceCashPayment_ID,
			String trxName) {
		super(ctx, UY_InvoiceCashPayment_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoiceCashPayment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		//OpenUp. Nicolas Sarlabos. 14/08/2012. #1218
		MBankAccount ba = (MBankAccount)this.getC_BankAccount();
		MBank bank = (MBank)ba.getC_Bank();
		MInvoice invoice = (MInvoice)this.getC_Invoice();

		if (ba != null && bank != null && invoice != null){
			if(ba.getBankAccountType()!=null){
				if(!bank.isBankHandler() && ba.getBankAccountType().equalsIgnoreCase("X")){
					ba.validateCashOpen(invoice.getDateInvoiced());
				}
			}		
		}		
		//Fin OpenUp #1218		
		
		// Setea tendertype tipo de forma de pago
		if (ba != null){
			if (ba.get_ID() > 0){

				MPaymentRule payrule = (MPaymentRule)this.getUY_PaymentRule();
				if (payrule == null) payrule = (MPaymentRule)ba.getUY_PaymentRule();				
					
				if (payrule.getpaymentruletype().equalsIgnoreCase(X_UY_PaymentRule.PAYMENTRULETYPE_CONTADO)){
					this.setTenderType(X_UY_LinePayment.TENDERTYPE_Cash);
					
					// OpenUp. Gabriel Vila. 02/05/2013. Issue #792
					// Si el banco se maneja como banco, el tendertype lo pongo como cheque ya que
					// en esta cuenta se stockean medios de pago.
					//MBank bank = (MBank)ba.getC_Bank();
					if (bank.isBankHandler()){
						this.setTenderType(X_UY_LinePayment.TENDERTYPE_Check);
					}
					// Fin OpenUp. Issue #792
					
				}
				else{
					this.setTenderType(X_UY_LinePayment.TENDERTYPE_Check);
				}
			}
		}	
		
		// Si se permite pagos contado multi-moneda
		if (MSysConfig.getBooleanValue("UY_INVOICE_CASH_MULTI_CUR", false, this.getAD_Client_ID())){

			// Valido que no me hayan cambiado la moneda en caso de cheques
			if (this.getUY_MediosPago_ID() > 0){
				MMediosPago mpago = (MMediosPago)this.getUY_MediosPago();
				if (mpago.getC_Currency_ID() != this.getC_Currency_ID()){
					throw new AdempiereException("No es posible modificar moneda asociada a este cheque.");
				}
				if (mpago.getPayAmt().compareTo(this.getAmount()) != 0){
					throw new AdempiereException("No es posible modificar Importe asociado a este cheque.");
				}
			}
			
			// Si tengo tasa de cambia manual, solo me aseguro de refrescar el importe en moneda del cabezal
			if (this.getCurrencyRate().compareTo(Env.ONE) > 0){
				if (invoice.getC_Currency_ID() == this.getC_Currency_ID()){
					this.setPayAmt(this.getAmount());
					this.setCurrencyRate(Env.ONE);
				}
				else{
					//OpenUp. Nicolas Sarlabos. 28/05/2013. #883
					if(invoice.getC_Currency_ID()!=142 && this.getC_Currency_ID()==142){
						this.setPayAmt(this.getAmount().divide(this.getCurrencyRate(), 2, RoundingMode.HALF_UP));	 
					} else if(invoice.getC_Currency_ID()==142 && this.getC_Currency_ID()!=142){
						this.setPayAmt(this.getAmount().multiply(this.getCurrencyRate()).setScale(2, RoundingMode.HALF_UP));
					}
					//Fin OpenUp. #883
				}

			}
			else{

				MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
				MAcctSchema schema = client.getAcctSchema();
				
				// Obtengo tasa de cambio
				BigDecimal currencyRate = Env.ONE;
				if (invoice.getC_Currency_ID() != this.getC_Currency_ID()){
					if (this.getC_Currency_ID() == schema.getC_Currency_ID()){
						currencyRate = MConversionRate.getRate(invoice.getC_Currency_ID(), this.getC_Currency_ID(), invoice.getDateInvoiced(), 0, invoice.getAD_Client_ID(), 0).setScale(3, RoundingMode.HALF_UP);
						if (currencyRate == null){
							throw new AdempiereException("No se pudo obtener Tasa de Cambio para la Fecha : " + invoice.getDateInvoiced());
						}

						this.setPayAmt(this.getAmount().divide(currencyRate, 2, RoundingMode.HALF_UP));
					}
					else{
						currencyRate = MConversionRate.getRate(this.getC_Currency_ID(), invoice.getC_Currency_ID(), invoice.getDateInvoiced(), 0, invoice.getAD_Client_ID(), 0).setScale(3, RoundingMode.HALF_UP);
						if (currencyRate == null){
							throw new AdempiereException("No se pudo obtener Tasa de Cambio para la Fecha : " + invoice.getDateInvoiced());
						}
						this.setPayAmt(this.getAmount().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));						
					}
				}
				else{
					this.setPayAmt(this.getAmount());
				}

				// Seteo tasa de cambio
				this.setCurrencyRate(currencyRate);			
				
			}

		}
		
		return true;

	}
	
	/***
	 * Obtiene y retorna modelo segun ID de factura recibido
	 * OpenUp Ltda. Issue #4298.
	 * @author Nicolas Sarlabos - 03/07/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MInvoiceCashPayment forInvoice(Properties ctx, int invoiceID, String trxName){
		
		String whereClause = X_UY_InvoiceCashPayment.COLUMNNAME_C_Invoice_ID + "=" + invoiceID;
		
		MInvoiceCashPayment model = new Query(ctx, I_UY_InvoiceCashPayment.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return model;
	}
	
	/***
	 * Obtiene y retorna lista de modelos segun ID de factura recibido
	 * OpenUp Ltda. Issue #4298.
	 * @author Nicolas Sarlabos - 06/07/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static List<MInvoiceCashPayment> getLines(Properties ctx, int invoiceID, String trxName){

		String whereClause = X_UY_InvoiceCashPayment.COLUMNNAME_C_Invoice_ID + "=" + invoiceID;

		List<MInvoiceCashPayment> lines = new Query(ctx, I_UY_InvoiceCashPayment.Table_Name, whereClause, trxName)
		.setOrderBy(X_UY_InvoiceCashPayment.COLUMNNAME_UY_InvoiceCashPayment_ID)
		.list();

		return lines;
	}

}
