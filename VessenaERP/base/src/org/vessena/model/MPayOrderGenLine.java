/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/02/2013
 */
package org.openup.model;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MPayOrderGenLine
 * OpenUp Ltda. Issue #348 
 * Description: Linea de generacion de ordenes de pago
 * @author Gabriel Vila - 12/02/2013
 * @see
 */
public class MPayOrderGenLine extends X_UY_PayOrderGenLine {

	private static final long serialVersionUID = -3740974971912630828L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PayOrderGenLine_ID
	 * @param trxName
	 */
	public MPayOrderGenLine(Properties ctx, int UY_PayOrderGenLine_ID,
			String trxName) {
		super(ctx, UY_PayOrderGenLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPayOrderGenLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Obtiene y retorna linea de generacion de orden de pago segun linea de orden de pago recibida.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 30/04/2013
	 * @see
	 * @param ctx
	 * @param uyPayOrderLineID
	 * @param trxName
	 * @return
	 */
	public static MPayOrderGenLine forPayOrderLine(Properties ctx, int uyPayOrderLineID, String trxName){

		String whereClause = X_UY_PayOrderGenLine.COLUMNNAME_UY_PayOrderLine_ID + "=" + uyPayOrderLineID;
		
		MPayOrderGenLine value = new Query(ctx, I_UY_PayOrderGenLine.Table_Name, whereClause, trxName)
		.first();
		
		return value;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if (newRecord) return true;
						
		// Valido monto a pagar menor o igual a saldo pendiente
		if (this.getamtallocated().compareTo(this.getamtopen()) > 0){
			throw new AdempiereException("El Monto a Pagar NO puede ser Mayor que el Saldo Pendiente.");
		}
		
		if (this.getCurrencyRate() == null){
			throw new AdempiereException("Debe indicar Tasa de Cambio.");			
		}
		if (this.getCurrencyRate().compareTo(Env.ZERO) <= 0){
			throw new AdempiereException("Debe indicar Tasa de Cambio.");			
		}
		
		// Me aseguro de que el monto en cuenta bancario quede acorde a las monedas y tasa de cambio
		if (this.getamtallocated().compareTo(Env.ZERO) <= 0){
			throw new AdempiereException("El Monto a Pagar debe ser Mayor a CERO.");
		}
		
		MPayOrderGen ordgen = (MPayOrderGen)this.getUY_PayOrderGen();
		MClient client = new MClient(getCtx(), ordgen.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		MBankAccount ba = (MBankAccount)this.getC_BankAccount();
		MInvoice invoice = (MInvoice)this.getC_Invoice();
		
		// Marco el cabezal del generador como cambiado para que antes de completar obligue al usuario a refrescar las ordenes
		if (!ordgen.isChanged()){
			ordgen.setIsChanged(true);
			ordgen.saveEx();
		}
		
		if (invoice != null){
			if (invoice.getC_Currency_ID() != ba.getC_Currency_ID()){
				if (ba.getC_Currency_ID() == schema.getC_Currency_ID()){
					this.setAmount(this.getamtallocated().multiply(this.getCurrencyRate()).setScale(2, RoundingMode.HALF_UP));
				}
				else{
					this.setAmount(this.getamtallocated().divide(this.getCurrencyRate(), 2, RoundingMode.HALF_UP));				
				}
			}
			else{
				this.setAmount(this.getamtallocated());
			}
		}
		else{
			this.setAmount(this.getamtallocated());
		}
		
		// Si tengo linea de orden de pago asociada (orden de pago en borrador)
		if (this.getUY_PayOrderLine_ID() > 0){
			// Actualizo linea de orden de pago asociada
			MPayOrderLine pordline = (MPayOrderLine)this.getUY_PayOrderLine();
			pordline.setamtopen(this.getamtopen());
			pordline.setamtallocated(this.getamtallocated());
			pordline.setCurrencyRate(this.getCurrencyRate());
			pordline.setAmount(this.getamtallocated());
			pordline.saveEx();
		}
		
		return true;
	}

	@Override
	protected boolean beforeDelete() {

		try {
			
			// Me asguro de eliminar, si tiene, la linea de orden de pago asocidada
			if (this.getUY_PayOrderLine_ID() > 0){
				DB.executeUpdateEx(" delete from uy_payorderline where uy_payorderline_id =" + this.getUY_PayOrderLine_ID(), get_TrxName());
			}

			MPayOrderGen header = (MPayOrderGen)this.getUY_PayOrderGen();
			header.setIsChanged(true);
			header.saveEx();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return true;
	}
	
	
}
