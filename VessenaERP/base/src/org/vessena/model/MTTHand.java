/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 16/09/2013
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.window.FDialog;
import org.compiere.apps.ADialog;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * org.openup.model - MTTHand
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo para documento de entrega de tarjetas.
 * @author Gabriel Vila - 16/09/2013
 * @see
 */
public class MTTHand extends X_UY_TT_Hand implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	private int windowNo = 0;
	
	private static final long serialVersionUID = 9221051738185185101L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_Hand_ID
	 * @param trxName
	 */
	public MTTHand(Properties ctx, int UY_TT_Hand_ID, String trxName) {
		super(ctx, UY_TT_Hand_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTHand(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @param action
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public String prepareIt() {
		// Todo bien
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {
		
		MTTCard card = null;
		MTTBox box = null;

		String action = "";
		
		try {
		
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
		
			//Fin Openup ISSUE #2275

			// Obtengo cuenta
			card = (MTTCard)this.getUY_TT_Card();
			
			//OpenUp. LeonardoBoccone. 13/06/2014. ISSUE #2275
			//Si no esta la firma del cliente no dejar completar
			if(card.getCardAction().equalsIgnoreCase("NUEVA") && !this.isValeSignature() && !this.isForzed()){
				this.processMsg = "Falta firma del cliente";
				return false;
			}
			
			// Verifico nuevamente estado y caja actual de la cuenta
			MTTCardStatus cardStatus = (MTTCardStatus)card.getUY_TT_CardStatus();
			
			/*
			if ((!cardStatus.getValue().equalsIgnoreCase("recepcionada")) && (!cardStatus.getValue().equalsIgnoreCase("contactocli"))){
				throw new AdempiereException("Esta Cuenta no se encuentra en estado Recepcionada.");
			}
			*/
			
			// Gabriel. Por ahora no tranco mas las entregas. Issue #1933, Issue #1986.
			if (card.getUY_TT_Box_ID() <= 0){
				this.setIsForzed(true);
			}

			
			if(!this.isForzed()){
				
				if (card.getUY_TT_Box_ID() <= 0){
					throw new AdempiereException("Esta Cuenta no tiene Caja Asociada.");
				}
				
				// Bloqueo caja
				box = (MTTBox)card.getUY_TT_Box();
				if (!box.lock()){
					throw new AdempiereException(" La caja : " + box.getValue() + " esta bloqueada por otro usuario en este momento.\n" +
							 " Por favor aguarde unos instantes y reintente la operación."); 
				}

				// Aviso usuario de ubicacion de cuenta
				String mensaje = " Esta CUENTA se encuentra en la CAJA : " + box.getValue() + "\n" +
								 " Posicion = " + card.getLocatorValue();
				
				this.showMessageBox(mensaje);
				
				box.updateQtyCount(1, false, config.getTopBox(), X_UY_TT_Box.BOXSTATUS_Cerrado, 
						X_UY_TT_Box.BOXSTATUS_Cerrado, false, false, windowNo, false, null);
				
				action = " delete from uy_tt_boxcard where uy_tt_box_id =" + box.get_ID() +
						 " and uy_tt_card_id =" + card.get_ID();
				DB.executeUpdateEx(action, get_TrxName());
				
				// Actualizo caja
				box.refresh(false, false);
			}
			
			
			// Si el estado actual de la cuenta es contactar cliente, debo eliminar la tarea de la bandeja del callcenter y poner de nuevo el punto de
			// distribucion en tracking
			// Obtengo incidencia
			if (cardStatus.getValue().equalsIgnoreCase("contactocli")){
				MRReclamo reclamo = (MRReclamo)card.getUY_R_Reclamo();
				MRArea area = MRArea.forValue(getCtx(), "tracking", null);
				MRPtoResolucion ptoresol = MRPtoResolucion.forValue(getCtx(), "tracking", null);
				reclamo.setUY_R_Area_ID(area.get_ID());
				reclamo.setUY_R_PtoResolucion_ID(ptoresol.get_ID());
				reclamo.saveEx();
				DB.executeUpdateEx("delete from uy_r_inbox where uy_r_reclamo_id=" + reclamo.get_ID(), get_TrxName());
			}
			
			// OpenUp. Gabriel Vila. 19/08/2014. Issue #2711
			// Comento este cambio de estado de cuenta ya que no se debe hacer en el aplicar sino en el completar.
			
			/*
			// Cambio el estado de la cuenta
			card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "entregada").get_ID());
			card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
			card.setIsDeliverable(true);
			card.setIsRetained(false);
			card.setIsRequested(false);
			card.saveEx();
			
			*/
			// Fin OpenUp. Issue #2711

			// Termino asociacion de cuenta con caja
			DB.executeUpdateEx(" update uy_tt_card set uy_tt_box_id = null, locatorvalue = null where uy_tt_card_id =" + card.get_ID(), get_TrxName());
			
			if(!this.isForzed()){
				
				// Seteo atributos
				this.setUY_TT_Box_ID(box.get_ID());
				this.setLocatorValue(card.getLocatorValue());
								
				// Desbloqueo caja
				box.unlock(get_TrxName());				
			}			
			
			this.setDocAction(DocAction.ACTION_Complete);
			this.setDocStatus(DocumentEngine.STATUS_Applied);

		} 
		catch (Exception e) {
			Trx trxAux = Trx.get(get_TrxName(), false);
			if (trxAux != null){
				trxAux.rollback();	
			}
			if (box != null) box.unlock(null);

			throw new AdempiereException(e.getMessage());
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public String completeIt() {
		
		//OpenUp. LeonardoBoccone. 13/06/2014. ISSUE #2275
		//Si no esta la firma del cliente no dejar completar
		if(this.isValeSignature()==false || this.isNeedCheckVale()== true){
			throw new AdempiereException("Falta firma del cliente");
			
		}
		//Fin Openup ISSUE #2275
		
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

		MTTCard card = (MTTCard)this.getUY_TT_Card();
		
		String tipoSocio = "Titular";
		if (this.getTipoSoc() != null){
			if (this.getTipoSoc().equalsIgnoreCase("1")) tipoSocio = "Adicional";
			else if (this.getTipoSoc().equalsIgnoreCase("2")) tipoSocio = "Otro";
		}
		
		
		// Tracking cuenta
		MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
		cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
		cardTrack.setAD_User_ID(this.getAD_User_ID());
		
		
		//OpenUp. Guillermo Brust. 19/12/2013. ISSUE #
		//Se agrega comentario de es forzado en la trazabilidad si la entrega fue hecho por el proceso de entrega forzada
		String descripcion = "";
		if(this.isForzed()){
			descripcion = "Entrega Forzada a " + this.getName2();
		}else{
			descripcion = "Entregada a " + this.getName2();
		}
		
		cardTrack.setDescription(descripcion);
		//Fin OpenUp.		
		
		cardTrack.setobservaciones("Cedula : " + this.getCedula2() + " - Tipo Socio : " + tipoSocio);
		cardTrack.setUY_TT_Card_ID(card.get_ID());
		cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
		cardTrack.saveEx();
		
		// Cierro tracking de cuenta por Entregada
		card.closeTrackingDelivered(null);
		
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public String getDocumentInfo() {

		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/09/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
			
		if (newRecord || this.is_ValueChanged(COLUMNNAME_UY_R_CedulaCuenta_ID)){						

			if (this.getTableModel() != null){
				windowNo =  this.getTableModel().getTabModel().getWindowNo();
			}
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			
			// Si tengo cuenta seleccionada, busco tracking abierto de la misma.
			if (this.getUY_R_CedulaCuenta_ID() > 0){
			
				MRCedulaCuenta cedulacuenta = new MRCedulaCuenta(getCtx(), this.getUY_R_CedulaCuenta_ID(), null);
				//Se agrega el deliveryPoint para que no haya errores en la Entrega.
				
				//OpenUp Sylvie Bouissa 05/01/2015 Issue #3436 
				//Se comenta metodo y se suplanta por el creado: metodo forAccountNoAndDeliveryPointOpenForTracking en mttcard ya que no estaba retornando cuenta ya entregada 
				//MTTCard card = MTTCard.forAccountNoAndDeliveryPoint(getCtx(), get_TrxName(), cedulacuenta.getAccountNo(), this.getUY_DeliveryPoint_ID());			
				MTTCard card = MTTCard.forAccountNoAndDeliveryPointOpenForTracking(getCtx(), get_TrxName(), cedulacuenta.getAccountNo(), this.getUY_DeliveryPoint_ID());
				
				if (card == null){
					throw new AdempiereException("No existe una Entrega Pendiente para esta Cuenta.");
				}

				// Seteo necesidad de esta cuenta: Nueva, Renovacion, Reimpresion, etc.
				this.setCardAction(card.getCardAction());
						  
				/*
				// Valido que esta cuenta no este retenida
				if (!card.isDeliverable()){
					throw new AdempiereException("Esta Cuenta esta marcada como Retenida y no puede ser Entregada.");
				}
				*/
				
				// Valido estado de tracking de esta cuenta y ubicacion.
				MTTCardStatus cardStatus = (MTTCardStatus)card.getUY_TT_CardStatus();

				if (cardStatus.getValue().equalsIgnoreCase("entregada")){
					if (!config.isForzed()){
						throw new AdempiereException("Esta Cuenta ya fue Entregada anteriormente.");	
					}
					
				}
				
				// Permito estados : recepcionada y contactar cliente
				/*
				if ((!cardStatus.getValue().equalsIgnoreCase("recepcionada")) && (!cardStatus.getValue().equalsIgnoreCase("contactocli"))){
					throw new AdempiereException("Esta Cuenta no se encuentra en estado Recepcionada.");
				}
				*/
				
				/*
				// Valido que destino de esta cuenta sea el punto actual de distribucion
				if (card.getUY_DeliveryPoint_ID() != this.getUY_DeliveryPoint_ID()){
					throw new AdempiereException("Esta Cuenta no tiene como Destino este Punto de Distribucion.");
				}
				*/
				
				MTTBox box = (MTTBox)card.getUY_TT_Box();
				if (box == null){

					List<MTTBoxCard> cardBoxes = MTTBoxCard.getCardBoxes(getCtx(), card.get_ID(), get_TrxName()); 
					if (cardBoxes != null){
						if (cardBoxes.size() > 0){
							box = (MTTBox) ((MTTBoxCard)cardBoxes.get(0)).getUY_TT_Box();
							card.setUY_TT_Box_ID(box.get_ID());
						}
					}

				}

				if (box == null){
					if (!config.isForzed()){
						throw new AdempiereException("Esta Cuenta no tiene Caja asociada.");	
					}
				}
				
				/*
				if (box.getUY_DeliveryPoint_ID() != this.getUY_DeliveryPoint_ID()){
					throw new AdempiereException("Esta Cuenta se encuentra en una Caja que no pertenece a este Punto de Distribucion");
				}
				*/

				
				// Todo bien, seteo datos
				this.setUY_TT_Card_ID(card.getUY_TT_Card_ID());
				this.setAccountNo(card.getAccountNo());
				this.setName(card.getName());
				this.setNroTarjetaTitular(card.getNroTarjetaNueva());
				this.setCreditLimit(card.getCreditLimit());
				this.setGrpCtaCte(card.getGrpCtaCte());
				this.setGAFCOD(card.getGAFCOD());
				this.setGAFNOM(card.getGAFNOM());
				this.setMLCod(card.getMLCod());
				this.setProductoAux(card.getProductoAux());
				
				//OpenUp Sylvie Bouissa 10-12-2014 Issue# 3273 (Mostrar campo si el vale de la cuenta esta firmado o no)
				//Se setea si el vale esta firmado o no, por defecto el campo es Y, si esta firmado entonces el campo isneedcheck vale es false
				// se agrega la condiciòn de que el proceso no sea entrega forzada.
				
				if (!this.isForzed()){
					this.setisValeSignature(card.isValeSigned());
					if(this.isValeSignature()){
						this.setIsNeedCheckVale(false);
					}else{
						this.setIsNeedCheckVale(true);
					}
				}


				// Actualizo checks de plasticos de esta cuenta
				DB.executeUpdateEx(" update uy_tt_cardplastic set isselected='Y' where uy_tt_card_id=" + card.get_ID(), get_TrxName());

				
			}
			else{
				throw new AdempiereException("Debe indicar Cedula y Cuenta");
			}

		}
			
		//Se validan los datos ingresados del que recibe la tarjeta
		if(this.getDocStatus().equals(DocAction.ACTION_Apply)){
			
			if(this.getTelephone() != null){
				if(this.getTelephone().length() > 8) throw new AdempiereException("El telefono Fijo del receptor tiene un formato inválido");
			}
			
			if(this.getMobile() != null){
				if(this.getMobile().length() > 9) throw new AdempiereException("El telefono Móvil del receptor tiene un formato inválido");
			}
			
			if(this.getEMail() != null && this.getEMail().length() > 0){
				if(!this.getEMail().contains("@")) throw new AdempiereException("El Correo Electrónico del receptor tiene un formato inválido");
			}							
		}		
		
		//Se obtiene el tipo de socio que es el que vino a retirar
		if(this.getCedula2() != null){
			
			MTTCard card = new MTTCard(this.getCtx(), this.getUY_TT_Card_ID(), this.get_TrxName());
			MTTCardPlastic plastic = card.getPlastic(this.getCedula2());
			
			if(plastic != null){
				if(plastic.getTipoSocio() == 0) this.setTipoSoc(X_UY_TT_Hand.TIPOSOC_Titular);
				else if (plastic.getTipoSocio() == 1) this.setTipoSoc(X_UY_TT_Hand.TIPOSOC_Adicional);
			
			}else this.setTipoSoc(X_UY_TT_Hand.TIPOSOC_Otro);			
		}
		
		return true;
	}

	/***
	 * Despliega mensaje segun swing o webui.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 24/10/2013
	 * @see
	 * @param mensaje
	 */
	private void showMessageBox(String mensaje){

		boolean showOK = true;
		try{
			ADialog.info (windowNo, null, mensaje);	
		}
		catch (Exception e){
			showOK = false;
		}
		
		if (!showOK){
			try {
				FDialog.info(windowNo, null, mensaje);
			} 
			catch (Exception e) {
				showOK = false;
			}
		}
	}
	
}
