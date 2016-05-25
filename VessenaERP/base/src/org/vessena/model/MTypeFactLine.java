/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 20/11/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MElementValue;
import org.compiere.util.DB;

/**
 * org.openup.model - MTypeFactLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 20/11/2014
 * @see
 */
public class MTypeFactLine extends X_UY_TypeFactLine {

	@Override
	protected boolean beforeSave(boolean newRecord) {
	
		try {
			
			MTypeFact header = (MTypeFact)this.getUY_TypeFact();
			
			// Al grabar la linea de la registracion del asiento tipo, debo calcular el monto segun parametrizacion
			// de este asiento tipo.
			MDocTypeFact dtf = MDocType.getDocTypeFact(getCtx(), header.getC_DocType_ID_2(), this.getC_ElementValue_ID(), null);
			
			if (dtf == null){
				throw new AdempiereException("No se encuentra paramtrizacion de cuenta en asiento tipo.");
			}
			
			// Valido cuenta que maneja socio de negocio
			if (dtf.getCalculate() == null){
				MElementValue ev = (MElementValue)dtf.getC_ElementValue();
				if (ev.get_ValueAsBoolean("ManageBPartner")){
					if (this.getC_BPartner_ID() <= 0){
						log.saveError(null, "Debe indicar Socio de Negocio para esta cuenta.");
			            return false;
					}
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2331461012558866467L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TypeFactLine_ID
	 * @param trxName
	 */
	public MTypeFactLine(Properties ctx, int UY_TypeFactLine_ID, String trxName) {
		super(ctx, UY_TypeFactLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTypeFactLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (success){
			// Actualizo cabezal de afectacion con total afectado
			this.updateParentHeader();
		}
		return true;

	}

	/***
	 * Actualiza totales del cabezal
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 01/06/2015
	 * @see
	 */
	private void updateParentHeader() {

		try {
			
			String action = " update uy_typefact set grandtotal = (select coalesce(sum(amt),0) from uy_typefactline where uy_typefact_id=" + this.getUY_TypeFact_ID() + " and isdebit='Y') " +
						    " where uy_typefact_id =" + this.get_ID(); 
			DB.executeUpdateEx(action, get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	
	
	
}
