/**
 * 
 */
package org.openup.model;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

/**
 * @author OpenUp. Gabriel Vila. 20/10/2011.
 *
 */
public class MAllocationPayment extends X_UY_AllocationPayment {


	private static final long serialVersionUID = 4456777763406970105L;

	/**
	 * @param ctx
	 * @param UY_AllocationPayment_ID
	 * @param trxName
	 */
	public MAllocationPayment(Properties ctx, int UY_AllocationPayment_ID,
			String trxName) {
		
		super(ctx, UY_AllocationPayment_ID, trxName);

		this.setRefreshWindow(true);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAllocationPayment(Properties ctx, ResultSet rs, String trxName) {
		
		super(ctx, rs, trxName);

		this.setRefreshWindow(true);
	}

	/**
	 * OpenUp. Gabriel Vila. 20/10/2011.
	 * Obtengo y retorno modelo de cabezal de afectacion para esta linea de recibo.
	 * @return
	 */
	public MAllocation getParent(){
		if (this.getUY_Allocation_ID() > 0)
			return new MAllocation(getCtx(), this.getUY_Allocation_ID(), get_TrxName());
		else
			return null;
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
			
		// Valido que monto a afectar no sea  mayor a saldo pendiente.
		if (this.getamtallocated().compareTo(this.getamtopen()) > 0){
			// Considerar tolerancia
			if (!verifyAmounts(this.getamtallocated().subtract(this.getamtopen()))){
				throw new AdempiereException("El Monto a Afectar no debe mayor al Saldo Pendiente del Recibo");	
			}
		}
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (success){
			// Actualizo cabezal de afectacion con total afectado
			this.getParent().updateTotalPayments();
		}
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		if (success){
			// Actualizo cabezal de afectacion con total afectado
			this.getParent().updateTotalPayments();
		}
		
		return true;
	}

	/***
	 * Verifica si diferencia esta dentro de la tolerancia.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Oct 1, 2015
	 * @param diff
	 * @return
	 */
	private boolean verifyAmounts(BigDecimal diff) {
		
		BigDecimal bordeInferior = new BigDecimal(-0.99);
		BigDecimal bordeSuperior = new BigDecimal(0.99);
		
		if (diff.compareTo(bordeInferior)<0 || diff.compareTo(bordeSuperior)>0) return false;		
		
		return true;
	}	
}
