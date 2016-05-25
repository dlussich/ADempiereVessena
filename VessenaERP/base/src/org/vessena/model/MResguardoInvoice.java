/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/11/2012
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInvoice;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MResguardoInvoice
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 19/11/2012
 * @see
 */
public class MResguardoInvoice extends X_UY_ResguardoInvoice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3897694015568687868L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ResguardoInvoice_ID
	 * @param trxName
	 */
	public MResguardoInvoice(Properties ctx, int UY_ResguardoInvoice_ID,
			String trxName) {
		super(ctx, UY_ResguardoInvoice_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MResguardoInvoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (!success) return success;
		
		this.updateHeader();
		
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {

		if (!success) return success;
		
		this.updateHeader();
		
		return true;

	}

	/***
	 * Actualiza total del resguardo.
	 * OpenUp Ltda. Issue #100 
	 * @author Gabriel Vila - 20/11/2012
	 * @see
	 */
	private void updateHeader() {
		try{
			
			String action = "update uy_resguardo set payamt=" +
					"(select coalesce(sum(amtopen),0) from uy_resguardoinvoice " +
					" where uy_resguardo_id =" + this.getUY_Resguardo_ID() + ") " +
					" where uy_resguardo_id =" + this.getUY_Resguardo_ID();
			
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Si moneda no es nacional, tengo que obtener tipo cambio a fecha comprobante
		// y convertir los importes a moneda nacional
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		BigDecimal rate = Env.ONE;
		
		if (this.getC_Currency_ID() != schema.getC_Currency_ID()){

			MInvoice invoice = new MInvoice(getCtx(), this.getC_Invoice_ID(), null);
			
			rate = MConversionRate.getRate(this.getC_Currency_ID(), schema.getC_Currency_ID(), invoice.getDateInvoiced(), 0, this.getAD_Client_ID(), 0);
			if (invoice.getCurrencyRate() != null){
				if (invoice.getCurrencyRate().compareTo(Env.ZERO) > 0){
					rate = invoice.getCurrencyRate();
				}
			}
			if (rate == null){
				throw new AdempiereException("No se pudo obtener Tasa de Cambio para Fecha : " + invoice.getDateInvoiced());
			}
			
			// Guardo monto en moneda extranjera
			this.setTotalLinesSource(this.getTotalLines());
			this.setGrandTotalSource(this.getGrandTotal());
			
			this.setTotalLines(this.getTotalLines().multiply(rate).setScale(2, RoundingMode.HALF_UP));
			this.setGrandTotal(this.getGrandTotal().multiply(rate).setScale(2, RoundingMode.HALF_UP));
		}
		else{
			// No tengo monto en moneda extranjera. Esto es importante de marcar ya que 
			// despues debo validar que no seleccionene resguardos que no tienen moneda
			// extranjera en recibos de moneda extranjera.
			this.setTotalLinesSource(Env.ZERO);
			this.setGrandTotalSource(Env.ZERO);
		}

		this.setDivideRate(rate);
		
		return true;
	}

	
	
}
