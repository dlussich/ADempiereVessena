/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.util.DB;

/**
 * @author Hp
 *
 */
public class MAllocDirectPayment extends X_UY_AllocDirectPayment {

	private static final long serialVersionUID = 1979556465404827666L;

	/**
	 * @param ctx
	 * @param UY_AllocDirectPayment_ID
	 * @param trxName
	 */
	public MAllocDirectPayment(Properties ctx, int UY_AllocDirectPayment_ID,
			String trxName) {
		super(ctx, UY_AllocDirectPayment_ID, trxName);
		this.setRefreshWindow(true);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAllocDirectPayment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		this.setRefreshWindow(true);
	}

	
	/**
	 * OpenUp. Gabriel Vila. 28/10/2011.
	 * Obtengo y retorno modelo de cabezal de recibo.
	 * @return
	 */
	public MPayment getParent(){
		if (this.getC_Payment_ID() > 0)
			return new MPayment(getCtx(), this.getC_Payment_ID(), get_TrxName());
		else
			return null;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//if (!newRecord){
			
		// Valido que monto a afectar no sea  mayor a saldo pendiente.
		if (this.getamtallocated().compareTo(this.getamtopen()) > 0)
			throw new AdempiereException("El Monto a Afectar no debe mayor al Saldo Pendiente del Documento");
			
		//}
		
		this.checkIsRetentioned();
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (success){
			// Actualizo saldo afectacion en cabezal
			this.getParent().updateAllocDirect(true);
		}
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {

		if (success){
			// Actualizo saldo afectacion en cabezal
			this.getParent().updateAllocDirect(true);
		}
		return true;

	}
	
	/**
	 * OpenUp. Emiliano Bentancor. 4/2/2016.
	 * Se setea a la factura el idicador que tiene una retencion asociada
	 * #5164
	 * @return
	 */
	private void checkIsRetentioned(){
		MInvoice inv = new MInvoice(getCtx(), this.getC_Invoice_ID(), get_TrxName());
		if(this.get_Value("amtRetention") != null){
			if(inv.get_Value("IsRetentioned") != null){
				if(inv.get_ValueAsBoolean("IsRetentioned")){
					throw new AdempiereException("La factura ya tiene una retencion asociada.");
				}
			}
		}
	}
	
}
