/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 27/03/2014
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MTTAcuse
 * OpenUp Ltda. Issue #2056 
 * Description: Modelo de recepcion de recibos de acuse en el modulo de tracking.
 * @author Gabriel Vila - 02/04/2014
 * @see
 */
public class MTTAcuse extends X_UY_TT_Acuse implements DocAction {

	private static final long serialVersionUID = -5253609632728675321L;

	private String processMsg = null;
	private boolean justPrepared = false;

	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_Acuse_ID
	 * @param trxName
	 */
	public MTTAcuse(Properties ctx, int UY_TT_Acuse_ID, String trxName) {
		super(ctx, UY_TT_Acuse_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTAcuse(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
	 * @see
	 * @return
	 */
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

		
		// Valido que tenga cuenta asociada
		if (this.getUY_TT_Card_ID() <= 0){
			this.processMsg = "No esta definida la cuenta a procesar.";
			return DocAction.STATUS_Invalid;
		}

		// Si tengo un recepcion de acuse con esta cuenta, aviso y salgo
		MTTAcuse acuse = MTTAcuse.forTTCard(getCtx(), this.getUY_TT_Card_ID(), null);
		if (acuse != null){
			if (acuse.get_ID() > 0){
				this.processMsg = "Esta cuenta ya tiene una recepción de acuse procesada anteriormente.";
				return DocAction.STATUS_Invalid;
			}
		}
		
		// Si tengo acuse de sistema asociado, lo marco como recepcionado.
		if (this.getUY_TT_Hand_ID() > 0){
			String action = " update uy_tt_hand set isreceipt='Y' where uy_tt_hand_id =" + this.getUY_TT_Hand_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}
		
		// Cierro incidencia asociada a la cuenta e impacto legajo.
		MTTCard card = (MTTCard)this.getUY_TT_Card();
		
		MTTCardStatus cardst = (MTTCardStatus)card.getUY_TT_CardStatus();
		if (!cardst.getValue().equalsIgnoreCase("entregada")){
			// Marco la cuenta como entregada
			card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "entregada").get_ID());
			card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
			card.saveEx();
			
			MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
			cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
			cardTrack.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			cardTrack.setDescription("Entregada Automaticamente por Proceso de Acuse.");
			cardTrack.setobservaciones("Numero Proceso Acuse : " + this.getDocumentNo());
			cardTrack.setUY_TT_Card_ID(card.get_ID());
			cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
			//cardTrack.setUY_DeliveryPoint_ID_Actual(this.getUY_DeliveryPoint_ID());
			cardTrack.saveEx();
		}

		// Tracking de acuse en cuenta
		MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
		cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
		cardTrack.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
		cardTrack.setDescription("Procesada en Recepcion de Acuse.");
		cardTrack.setobservaciones("Numero Proceso Acuse : " + this.getDocumentNo());
		cardTrack.setUY_TT_Card_ID(card.get_ID());
		cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
		//cardTrack.setUY_DeliveryPoint_ID_Actual(this.getUY_DeliveryPoint_ID());
		cardTrack.saveEx();
		
		
		MRReclamo reclamo = (MRReclamo)card.getUY_R_Reclamo();
		reclamo.setJustification("Recepcion de Acuse de Entrega");
		reclamo.setReclamoResuelto(true);
		reclamo.setEndDate(new Timestamp(System.currentTimeMillis()));
		reclamo.saveEx();
		
		try {

			if (!reclamo.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException("No se pudo completar la acción de Cierre de Incidencia");
			}
			
			reclamo.setLegajoFinancial(false, false, null);
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 27/03/2014
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {
		
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		// Si tengo acuse de sistema asociado, lo marco como NO recepcionado.
		if (this.getUY_TT_Hand_ID() > 0){
			String action = " update uy_tt_hand set isreceipt='N' where uy_tt_hand_id =" + this.getUY_TT_Hand_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}		
		
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	 * @author Gabriel Vila - 27/03/2014
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
	
		try {
			
			// Segun tipo de recepecion de acuse
			if (this.getAcuseType().equalsIgnoreCase(X_UY_TT_Acuse.ACUSETYPE_SISTEMA)){

				// Si no tengo texto que indique numero de recibo a considerar, aviso y salgo.
				if ((this.getScanText() == null) || (this.getScanText().equalsIgnoreCase(""))){
					throw new AdempiereException("Debe scanear o ingresar el numero de acuse emitido previamente por el Sistema.");
				}
				
				int uyHandID = new Integer(this.getScanText());
				
				// Obtengo si existe el modelo del recibo de acuse a procesar
				MTTHand hand = new MTTHand(getCtx(), uyHandID, null);
				if (hand.get_ID() <= 0){
					throw new AdempiereException("No existe un Acuse de Recibo con este identificador.");
				}
				
				// Si este acuse ya fue recepcionado anteriormente
				if (hand.isReceipt()){
					throw new AdempiereException("Este Acuse de Recibo ya fue procesado anteriormente.");
				}
				
				// Todo bien, cargo datos del Acuse en ventana
				this.setUY_TT_Hand_ID(hand.get_ID());
				this.setUY_TT_Card_ID(hand.getUY_TT_Card_ID());
				this.setName(hand.getName());
				this.setCedula2(hand.getCedula2());
				this.setName2(hand.getName2());
				this.setTelephone(hand.getTelephone());
				this.setMobile(hand.getMobile());
				this.setEMail(hand.getEMail());
				this.setVinculo(hand.getVinculo());
				this.setTipoSoc(hand.getTipoSoc());
			}
			else if (this.getAcuseType().equalsIgnoreCase(X_UY_TT_Acuse.ACUSETYPE_MANUAL)){
				
				// Valido que para acuses manuales se ingresa cedula y numero de tarjeta para de esa manera obtener la cuenta.
				if ((this.getCedula() == null) || (this.getCedula().equalsIgnoreCase(""))){
					throw new AdempiereException("Debe ingresar numero de cedula a considerar.");
				}

				if ((this.getNroTarjetaTitular() == null) || (this.getNroTarjetaTitular().equalsIgnoreCase(""))){
					throw new AdempiereException("Debe ingresar numero de tarjeta a considerar.");
				}
				
				MTTCard card = MTTCard.forCedulaTarjeta(getCtx(), this.getCedula().trim(), this.getNroTarjetaTitular().trim(), null);
				if (card == null){
					throw new AdempiereException("No hay una Cuenta pendiente de Recepcion de Acuse con esa cedula y numero de tarjeta.");
				}

				// Si tengo un recepcion de acuse con esta cuenta, aviso y salgo
				MTTAcuse acuse = MTTAcuse.forTTCard(getCtx(), card.get_ID(), null);
				if (acuse != null){
					if (acuse.get_ID() > 0){
						throw new AdempiereException("Esta cuenta ya tiene una recepción de acuse procesada anteriormente.");
					}
				}

				// Todo bien, cargo datos de cuenta en ventana.
				this.setUY_TT_Card_ID(card.get_ID());
				this.setName(card.getName());

			}
			else if (this.getAcuseType().equalsIgnoreCase(X_UY_TT_Acuse.ACUSETYPE_CORREO)){

				// Si no tengo texto que indique cuenta, aviso y salgo...
				if ((this.getAccountNo() == null) || (this.getAccountNo().equalsIgnoreCase(""))){
					throw new AdempiereException("Debe ingresar numero de cuenta a considerar.");
				}
				
				// Obtengo si existe el modelo de la cuenta tracking
				MTTCardStatus st = MTTCardStatus.forValue(getCtx(), null, "entregada");
				MTTCard card = MTTCard.forAccountOpenFirstAndStatus(getCtx(), null, this.getAccountNo(), st.get_ID());

				if ((card == null) || (card.get_ID() <= 0)){
					throw new AdempiereException("No existe una Cuenta en Tracking Entregada en Correo con ese numero.");
				}
				
				// Si tengo un recepcion de acuse con esta cuenta, aviso y salgo
				MTTAcuse acuse = MTTAcuse.forTTCard(getCtx(), card.get_ID(), null);
				if (acuse != null){
					if (acuse.get_ID() > 0){
						throw new AdempiereException("Esta cuenta ya tiene una recepción de acuse procesada anteriormente.");
					}
				}
				
				// Todo bien, cargo datos de cuenta en ventana.
				this.setUY_TT_Card_ID(card.get_ID());
				this.setName(card.getName());
				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return true;
	}

	/***
	 * Obtiene y retorna un objeto de este modelo segun id de tracking de cuenta recibido.
	 * OpenUp Ltda. Issue #2056 
	 * @author Gabriel Vila - 02/04/2014
	 * @see
	 * @param ctx
	 * @param uyTTCardID
	 * @param trxName
	 * @return
	 */
	public static MTTAcuse forTTCard(Properties ctx, int uyTTCardID, String trxName) {

		String whereClause = X_UY_TT_Acuse.COLUMNNAME_UY_TT_Card_ID + "=" +  uyTTCardID +
				" and " + X_UY_TT_Acuse.COLUMNNAME_DocStatus + "='CO' ";
		
		MTTAcuse model = new Query(ctx, I_UY_TT_Acuse.Table_Name, whereClause, trxName).first();
		
		return model;
		
	}
	
	

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (!success) return success;
		
		
		return true;
	}

	
	
}
