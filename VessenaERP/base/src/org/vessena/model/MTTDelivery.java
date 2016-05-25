/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * @author gbrust
 *
 */
public class MTTDelivery extends X_UY_TT_Delivery implements DocAction{


	private String processMsg = null;
	private boolean justPrepared = false;

	private static final long serialVersionUID = 8952824862516279854L;

	/**
	 * @param ctx
	 * @param UY_TT_Delivery_ID
	 * @param trxName
	 */
	public MTTDelivery(Properties ctx, int UY_TT_Delivery_ID, String trxName) {
		super(ctx, UY_TT_Delivery_ID, trxName);		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTDelivery(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);		
	}

	@Override
	public boolean processIt(String action) throws Exception {		
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {		
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {		
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {		
		// Todo bien
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {		
		return false;
	}

	@Override
	public boolean applyIt() {		
		
		//Acá tengo que traer los bolsines que tengan los puntos de distribución seleccionados
		
		List<MTTDeliveryDelPoint> deliveryDelPoints = MTTDeliveryDelPoint.forDeliveryIDAndSelected(this.getCtx(), this.get_ID());
		
		for (MTTDeliveryDelPoint mttDeliveryDelPoint : deliveryDelPoints) {
			
			//Obtengo los bolsines que tengan el mismo punto de distribucion de origen y destino que este envio y a aparte que esten en estado PENDIENTE DE ENVIO
			List<MTTSeal> bolsines = MTTSeal.getForDelPointAndDelPointFrom(this.getCtx(), this.get_TrxName(), mttDeliveryDelPoint.getUY_DeliveryPoint_ID(), getUY_DeliveryPoint_ID_From());
			
			for (MTTSeal bolsin : bolsines) {
				MTTDeliveryLine line = new MTTDeliveryLine(this.getCtx(), 0, this.get_TrxName());
				
				line.setUY_TT_Delivery_ID(this.get_ID());
				line.setUY_TT_Seal_ID(bolsin.get_ID());
				line.setQtyBook(new BigDecimal(bolsin.getQtyBook()));
				line.setQtyCount(new BigDecimal(bolsin.getQtyCount()));			
				
				line.saveEx();		
			}	
		}
		
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;

	}

	@Override
	public boolean rejectIt() {		
		return false;
	}

	@Override
	public String completeIt() {		
		
		//	Re-Check
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		
		//Acá tengo que verificar que se haya ingresado el numero de levante
		if(this.getLevante() == null || this.getLevante() != null && this.getLevante().equals("")) throw new AdempiereException("No es posible completar sin ingresar número de levante");
		
		//Cuando se completa se deben dejar todos los bolsines que se encuentran en las lineas del envio en estado CERRADO
		List<MTTDeliveryLine> lineas = MTTDeliveryLine.getForMTTDeliveryID(getCtx(), this.get_TrxName(), this.get_ID());
		
		//Primero tengo que verificar que se tenga al menos un bolsin para enviar, sino no tiene sentido completar un envio sin bolsines
		if(lineas.size() <= 0) throw new AdempiereException("No es posible completar el envio sin tener bolsines para enviar");
		
		MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
		
		for (MTTDeliveryLine linea : lineas) {
			
			MTTSeal bolsin = (MTTSeal) linea.getUY_TT_Seal();
			bolsin.setSealStatus(X_UY_TT_Seal.SEALSTATUS_Cerrado);
			bolsin.setUY_TT_Delivery_ID(this.get_ID());
			bolsin.saveEx();
									
			MDeliveryPoint dpBolsin = new MDeliveryPoint(getCtx(), bolsin.getUY_DeliveryPoint_ID_To(), null);
			
			//Tambien tengo que cambiar el estado de las tarjetas
			int statusPendEnvioID = MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "pendenvio").get_ID();
			int statusEnviadaID = MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "enviada").get_ID();
			List<MTTCard> cuentasDeBolsin = MTTCard.forSealAndStatus(getCtx(), bolsin.get_ID(), statusPendEnvioID);
			
			for (MTTCard mttCard : cuentasDeBolsin) {				
				mttCard.setUY_TT_CardStatus_ID(statusEnviadaID);
				mttCard.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				mttCard.setUY_TT_Delivery_ID(this.get_ID());
				mttCard.setLevante(this.getLevante());
				mttCard.saveEx();				
				
				// Tracking en cuenta
				MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
				cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
				cardTrack.setAD_User_ID(this.getAD_User_ID());
				cardTrack.setDescription("Enviada a " + dpBolsin.getName());
				cardTrack.setobservaciones("Precinto : " + bolsin.getValue() + " - Levante : " + this.getLevante());
				cardTrack.setUY_TT_Card_ID(mttCard.get_ID());
				cardTrack.setUY_TT_CardStatus_ID(mttCard.getUY_TT_CardStatus_ID());
				cardTrack.setUY_TT_Seal_ID(mttCard.getUY_TT_Seal_ID());
				cardTrack.saveEx();
				
				// Actualizo datos de dias actuales 
				String accion = " update uy_tt_card set uy_deliverypoint_id_actual = null, diasactual = 0, dateassign = null where uy_tt_card_id =" + mttCard.get_ID();
				DB.executeUpdateEx(accion, get_TrxName());
				
				// Actualizo incidencia asociada a esta cuenta: cargo las cedulas autorizadas para retirar cuenta
				MTTConfigNeed confNeed = config.getNeedForCardAction(mttCard.getCardAction());
				if ((confNeed != null) && (confNeed.get_ID() > 0)){
					
					List<MTTCardPlastic> plastics = mttCard.getPlastics();
					for (MTTCardPlastic plastic: plastics){
						// Titular va siempre
						if (plastic.getTipoSocio() == 0){
							MRReclamoAutoriz raut = new MRReclamoAutoriz(getCtx(), 0, get_TrxName());
							raut.setUY_R_Reclamo_ID(mttCard.getUY_R_Reclamo_ID());
							raut.setCedula(plastic.getCedula());
							raut.setName(plastic.getName());
							raut.saveEx();
						}
						else{
							// Derivados solo dependiendo de la configuracion de la necesidad
							if (confNeed.getRetireType().equalsIgnoreCase(X_UY_TT_ConfigNeed.RETIRETYPE_TODOS)){
								MRReclamoAutoriz raut = new MRReclamoAutoriz(getCtx(), 0, get_TrxName());
								raut.setUY_R_Reclamo_ID(mttCard.getUY_R_Reclamo_ID());
								raut.setCedula(plastic.getCedula());
								raut.setName(plastic.getName());
								raut.saveEx();
							}
						}
					}
				}
			}
			
		}
		

		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
		
	}

	@Override
	public boolean voidIt() {		

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
	}

	@Override
	public boolean closeIt() {		
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {		
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {		
		return false;
	}

	@Override
	public boolean reActivateIt() {		
		return false;
	}

	@Override
	public String getSummary() {		
		return null;
	}

	@Override
	public String getDocumentInfo() {		

		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();

	}

	@Override
	public File createPDF() {		
		return null;
	}

	@Override
	public String getProcessMsg() {		
		return this.processMsg;
	}

	@Override
	public int getDoc_User_ID() {		
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {		
		return null;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return success;
		
		if(newRecord){
			
			//Tengo que crear las lineas que continene a los puntos de distribucion que distribuyen este envio		
			
			//Obtengo todos los puntos de distribucion existentes
			List<MDeliveryPoint> deliveries = MDeliveryPoint.getDeliveries(this.getCtx());
			for (MDeliveryPoint mDeliveryPoint : deliveries) {
				
				MTTDeliveryDelPoint delPoint = new MTTDeliveryDelPoint(getCtx(), 0, this.get_TrxName());
				delPoint.setUY_DeliveryPoint_ID(mDeliveryPoint.get_ID());
				delPoint.setUY_TT_Delivery_ID(this.get_ID());
				delPoint.setIsSelected(false);
				delPoint.saveEx();
			}	
		}		
		
		return true;

	}
	
	
	/***
	 * Obtengo modelo según punto de distribucion y numero de levante recibido
	 * OpenUp Ltda. Issue #
	 * @author Guillermo Brust - 11/09/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTTDelivery forDelvieryPointAndLevante(Properties ctx, int deliveryPointID, String levante, String trxName){
							
		//Recorre la grilla con los puntos de distribucion asociados (seleccionados) para el punto de distribucion pasado por parametro, en TODOS los envios
		List<MTTDeliveryDelPoint> LineasGrilla = MTTDeliveryDelPoint.forDeliveryPointIDAndSelected(ctx, deliveryPointID);
		
		if (LineasGrilla == null) return null;
		
		for (MTTDeliveryDelPoint mttDeliveryDelPoint : LineasGrilla) {
			
			if (mttDeliveryDelPoint != null){

				//Si el levante asociado al envio asociado al registro de la grilla que estoy recorriendo es igual al pasado por parametro, es porque estoy en el envio
				//que realiza el punto de distribucion pasado por parametro y con ese numero de levante
				if (mttDeliveryDelPoint.getUY_TT_Delivery() != null){

					String levanteAux = ((MTTDelivery) mttDeliveryDelPoint.getUY_TT_Delivery()).getLevante();
					
					if (levanteAux != null){
						if(levanteAux.equals(levante)){
							return (MTTDelivery) mttDeliveryDelPoint.getUY_TT_Delivery();
						}
					}
				}
			}
		}		
		
		return null;
	}
	
	@Override
	protected boolean beforeDelete() {

		if (this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Applied)){
			throw new AdempiereException("No es posible eliminar Recepciones Aplicadas. Debe Completarla.");
		}
		
		return true;
	}

	/***
	 * Obtiene y retorna lineas de este documento
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 19, 2015
	 * @return
	 */
	public List<MTTDeliveryLine> getSelectedLinesForDeliveryPoint(int deliveryPointID){
		
		String whereClause = X_UY_TT_DeliveryLine.COLUMNNAME_UY_TT_Delivery_ID + "=" + this.get_ID() +
				" and " + X_UY_TT_DeliveryLine.COLUMNNAME_IsSelected + "='Y' " +
				" and " + X_UY_TT_DeliveryLine.COLUMNNAME_UY_TT_Seal_ID + " in " +
				" (select uy_tt_seal_id from uy_tt_seal where uy_deliverypoint_id_to =" + deliveryPointID + ") "; 
		
		List<MTTDeliveryLine> lines = new Query(getCtx(), I_UY_TT_DeliveryLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
	}

	/***
	 * Obtiene y retorna cuentas asociadas a este envío sin importar el bolsín.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 21, 2015
	 * @return
	 */
	public List<MTTCard> getAccounts(){
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_UY_TT_Delivery_ID + "=" + this.get_ID();
		
		List<MTTCard> lines = new Query(getCtx(), I_UY_TT_Card.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
 		
	}

	/***
	 * Obtiene y retorna lineas de este documento
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 19, 2015
	 * @return
	 */
	public List<MTTDeliveryLine> getSelectedLinesForParentDeliveryPoint(int deliveryPointID){
		
		String whereClause = X_UY_TT_DeliveryLine.COLUMNNAME_UY_TT_Delivery_ID + "=" + this.get_ID() +
				" and " + X_UY_TT_DeliveryLine.COLUMNNAME_IsSelected + "='Y' " +
				" and " + X_UY_TT_DeliveryLine.COLUMNNAME_UY_TT_Seal_ID + " in " +
				" (select uy_tt_seal_id from uy_tt_seal where uy_deliverypoint_id_to in " +
				" (select uy_deliverypoint_id from uy_deliverypoint where uy_deliverypoint_id_from =" + deliveryPointID + ")) "; 
		
		List<MTTDeliveryLine> lines = new Query(getCtx(), I_UY_TT_DeliveryLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
	}
	
}


