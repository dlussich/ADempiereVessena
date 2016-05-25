/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoicePaySchedule;

/**
 * @author OpenUp. Gabriel Vila. 20/10/2011.
 *
 */
public class MAllocationInvoice extends X_UY_AllocationInvoice {

	private static final long serialVersionUID = 2801547192893649005L;

	/**
	 * @param ctx
	 * @param UY_AllocationInvoice_ID
	 * @param trxName
	 */
	public MAllocationInvoice(Properties ctx, int UY_AllocationInvoice_ID,
			String trxName) {
		super(ctx, UY_AllocationInvoice_ID, trxName);
		this.setRefreshWindow(true);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAllocationInvoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		this.setRefreshWindow(true);
	}

	/**
	 * OpenUp. Gabriel Vila. 20/10/2011.
	 * Obtengo y retorno modelo de cabezal de afectacion para esta linea de factura.
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

		//if (!newRecord){
			
		// Valido que monto a afectar no sea  mayor a saldo pendiente.
		if (this.getamtallocated().compareTo(this.getamtopen()) > 0)
			throw new AdempiereException("El Monto a Afectar no debe mayor al Saldo Pendiente de la Factura");
			
		//}
		//Ini OpenUp SBT 28/03/2016 Issue #5069
		//Si no tengo asociado el vencimiento lo intento asociar a partir de la invoice asociada 
		if (null==this.get_Value("C_InvoicePaySchedule_ID")){
			if(this.getC_Invoice().getpaymentruletype().equalsIgnoreCase("CR")){//Corresponde si el documento es credito
				int[] vtosId =  MInvoicePaySchedule.getAllIDs("C_InvoicePaySchedule",
						"C_Invoice_ID = "+this.getC_Invoice_ID(), get_TrxName());
				if(vtosId.length>0){
					MInvoicePaySchedule paySche = new MInvoicePaySchedule(getCtx(),vtosId[0],get_TrxName());
					if(null!= paySche && paySche.get_ID()>0){
						this.set_Value("C_InvoicePaySchedule_ID", vtosId[0]);
						this.set_Value("DueDate",paySche.getDueDate());
					}
				}
			}		
		}
		//FIN OpenUp SBT 28/03/2016 Issue #5069
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (success){
			// Actualizo cabezal de afectacion con total afectado
			this.getParent().updateTotalInvoices();
		}
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		if (success){
			// Actualizo cabezal de afectacion con total afectado
			this.getParent().updateTotalInvoices();
		}
		return true;
	}

}


