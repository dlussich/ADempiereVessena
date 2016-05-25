/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 08/11/2012
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.util.DB;

/**
 * org.openup.model - MRFQMacroReqLine
 * OpenUp Ltda. Issue #95 
 * Description: Lineas de una macro solicitud de cotizacion
 * @author Gabriel Vila - 08/11/2012
 * @see
 */
public class MRFQMacroReqLine extends X_UY_RFQ_MacroReqLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5726268649130965185L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RFQ_MacroReqLine_ID
	 * @param trxName
	 */
	public MRFQMacroReqLine(Properties ctx, int UY_RFQ_MacroReqLine_ID,
			String trxName) {
		super(ctx, UY_RFQ_MacroReqLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRFQMacroReqLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (!success) return success;
		
		// Si se actualizo estado de aprobacion de cotizacion
		if ((!newRecord) && (is_ValueChanged(COLUMNNAME_IsApproved))){
			// Verifico si debo actualizar o no el estado de aprobacion de cotizaciones de 
			// la macro solicitud.
			MRFQMacroReq macro = (MRFQMacroReq)this.getUY_RFQ_MacroReq();
			macro.updateIsApproved();
			
			// Ejecuto evaluacion automatica de las cotizaciones hasta ahora aprobadas
			macro.evaluateQuotes();
		}
		
		return true;
	}

	/***
	 * Obtiene y retorna total de cotizacion asociada a esta linea de macro solicitud
	 * OpenUp Ltda. Issue #95 
	 * @author Gabriel Vila - 17/12/2012
	 * @see
	 * @return
	 */
	public BigDecimal getQuoteTotal() {

		try{			
			MQuoteVendor quote = (MQuoteVendor)this.getUY_QuoteVendor();
			return quote.getTotalAmt();
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Obtiene y retorna fecha de entrega estimada de cotizacion asociada a esta linea
	 * de macro solicitud.
	 * OpenUp Ltda. Issue #95 
	 * @author Gabriel Vila - 17/12/2012
	 * @see
	 * @return
	 */
	public Timestamp getQuoteDeliveryDate() {

		try{			
			MQuoteVendor quote = (MQuoteVendor)this.getUY_QuoteVendor();
			return quote.getDateRequired();
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	/***
	 * Obtiene y retorna clasificacion ABC del proveedor de la cotizacion asociada 
	 * a esta linea de macro solicitud.
	 * OpenUp Ltda. Issue #95 
	 * @author Gabriel Vila - 17/12/2012
	 * @see
	 * @return
	 */
	public String getQuoteVendorABC() {

		try{			
			MQuoteVendor quote = (MQuoteVendor)this.getUY_QuoteVendor();
			MBPartner vendor = (MBPartner)quote.getC_BPartner();

			String rating = "C";
			
			if (vendor.getRating() != null) rating = vendor.getRating();
			
			return rating;
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Si indico seleccionado manualmente, me aseguro de desmarcar las demas lineas
		// y que haya puesto motivo de seleccion manual.
		if ((!newRecord) && (is_ValueChanged(COLUMNNAME_ManualSelected))){
			
			if (this.isManualSelected()){
				
				// Primero valido que haya puesto motivo de seleccion manual
				/*
				if ((this.getDescription() == null) || (this.getDescription().equalsIgnoreCase(""))){
					throw new AdempiereException("Falta indicar Motivo de Seleccion Manual");
				}
				*/
				
				// Marco demas lineas como no seleccionadas manualmente.
				String action = " update uy_rfq_macroreqline " +
				        " set manualselected='N', description=null " +
				        " where uy_rfq_macroreq_id =" + this.getUY_RFQ_MacroReq_ID() +
				        " and uy_rfq_macroreqline_id !=" + this.get_ID();
		
				DB.executeUpdateEx(action, get_TrxName());
			}
		}
		
		return true;
	}

	
}
