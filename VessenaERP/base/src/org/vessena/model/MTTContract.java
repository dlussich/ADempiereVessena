/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Oct 4, 2015
*/
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

/**
 * org.openup.model - MTTContract
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Oct 4, 2015
*/
public class MTTContract extends X_UY_TT_Contract implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = -2450354550381469253L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_Contract_ID
	 * @param trxName
	*/

	public MTTContract(Properties ctx, int UY_TT_Contract_ID, String trxName) {
		super(ctx, UY_TT_Contract_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MTTContract(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
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
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 */
	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
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
		
		MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");

		int windowNo = -1;
		if (this.getTableModel() != null){
			windowNo = this.getTableModel().getTabModel().getWindowNo();
		}

		
		// Ubico contrato en caja
		MTTBox boxDestino = (MTTBox)this.getUY_TT_Box();
		int locatorValue = boxDestino.updateQtyCount(1, true, config.getTopBox(), 
				X_UY_TT_Box.BOXSTATUS_Cerrado, X_UY_TT_Box.BOXSTATUS_Cerrado, true, true,
				windowNo, false, "");
		
		// Asocio caja con contrato
		this.setLocatorValue(locatorValue);
		
		// Genero nueva incidencia
		this.generateIncidencia();
		
		MTTBoxContract bCont = new MTTBoxContract(getCtx(), 0, get_TrxName());
		bCont.setUY_TT_Box_ID(boxDestino.get_ID());
		bCont.setUY_TT_Contract_ID(this.get_ID());
		bCont.setLocatorValue(this.getLocatorValue());
		bCont.setDateTrx(new Timestamp(System.currentTimeMillis()));
		bCont.setUY_R_Reclamo_ID(this.getUY_R_Reclamo_ID());
		bCont.saveEx();
		
		// Asocio nueva incidencia generada para este contrato, con la incidencia de la cuenta
		MRReclamo incidenciaCuenta = (MRReclamo)this.getUY_R_Reclamo();
		incidenciaCuenta.setUY_R_Reclamo_ID_2(this.getUY_R_Reclamo_ID_2());
		incidenciaCuenta.saveEx();
		
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
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if (this.getUY_R_Reclamo_ID() <= 0){
			throw new AdempiereException("Debe indicar numero valido de contrato.");
		}
		
		return true;

	}

	/***
	 * Genera nueva incidencia asociada a este contrato
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Oct 11, 2015
	 */
	private void generateIncidencia(){

		try {
			
			MTTCard card = MTTCard.forIncidencia(getCtx(), this.getUY_R_Reclamo_ID(), null);
			if ((card == null) || (card.get_ID() <= 0)){
				throw new AdempiereException("No se pudo obtener Tracking de cuenta asociado a la incidencia de este contrato.");
			}
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY); 
			
			MRReclamo incidencia = new MRReclamo(getCtx(), 0, get_TrxName());
			incidencia.setDefaultDocType();
			incidencia.setDateTrx(today);
			incidencia.setUY_R_Type_ID(MRType.forValue(getCtx(), "gestdocumental", null).get_ID());
			
			MRCause cause = MRCause.forValue(getCtx(), "gestdocumental", null);

			incidencia.setUY_R_Cause_ID(cause.get_ID());
			incidencia.setPriorityBase(cause.getPriorityBase());
			incidencia.setPriorityManual(cause.getPriorityBase());
			
			incidencia.setisinmediate(false);
			incidencia.setDescription("Recepcion de Contrato");
			incidencia.setUY_R_Canal_ID(MRCanal.forValue(getCtx(), "tracking", null).get_ID());
			incidencia.setReceptor_ID(MUser.forName(getCtx(), "adempiere", null).get_ID());
			incidencia.setUY_R_Area_ID(cause.getUY_R_Area_ID());
			incidencia.setUY_R_PtoResolucion_ID(cause.getUY_R_PtoResolucion_ID());
			incidencia.setIsInternalIssue(false);
			incidencia.setNotificationVia(X_UY_R_Reclamo.NOTIFICATIONVIA_Celular);
			incidencia.setAccountNo(this.getAccountNo());
			incidencia.setUY_R_CanalNotifica_ID(MRCanal.forValue(getCtx(), "tracking", null).get_ID());
			incidencia.setIsObserver(false);
			incidencia.setReclamoResuelto(false);
			incidencia.setReclamoNotificado(false);
			incidencia.setNombreTitular(this.getName());
			incidencia.setNroTarjetaTitular(card.getValue());
			incidencia.setDueDateTitular(card.getDueDate());
			incidencia.setLimCreditoTitular(card.getCreditLimit());
			incidencia.setIsPreNotificacion(false);
			incidencia.setUY_R_ActionType_ID(MRActionType.IDforValue(getCtx(), "clinotifica", null));
			incidencia.setGrpCtaCte(card.getGrpCtaCte());
			incidencia.setGAFCOD(card.getGAFCOD());
			incidencia.setGAFNOM(card.getGAFNOM());
			incidencia.setMLCod(card.getMLCod());
			incidencia.setC_Period_ID(MPeriod.getC_Period_ID(getCtx(), today, 0));
			incidencia.setDocStatus(X_UY_R_Reclamo.DOCSTATUS_Drafted);
			incidencia.setDocAction(X_UY_R_Reclamo.DOCACTION_Apply);

			incidencia.setCedula(this.getCedula());
			incidencia.setCustomerName(this.getName());
			incidencia.setDirection(card.getAddress1());
			incidencia.setEMail(card.getEMail());
			incidencia.setTelephone(card.getTelephone());
			incidencia.setMobile(card.getMobile());
			incidencia.setPostal(card.getPostal());
			
			MRCedulaCuenta cedcta = MRCedulaCuenta.forCedulaCuenta(getCtx(), this.getCedula(), this.getAccountNo(), null);
			if ((cedcta == null) || (cedcta.get_ID() <= 0)){
				cedcta = new MRCedulaCuenta(getCtx(), 0, null);
				cedcta.setValue(this.getCedula());
				cedcta.setAccountNo(this.getAccountNo());
				cedcta.saveEx();
			}
			
			incidencia.setUY_R_CedulaCuenta_ID(cedcta.get_ID());
			
			incidencia.setUY_R_Reclamo_ID_2(this.getUY_R_Reclamo_ID());
			
			incidencia.saveEx();
			
			cedcta.setUY_R_Reclamo_ID(incidencia.get_ID());
			cedcta.setInternalCode(incidencia.getDocumentNo());
			cedcta.saveEx();

			// Tarjetas
			for (MTTCardPlastic plastic : card.getPlastics()) {
				MRDerivado derivado = new MRDerivado(getCtx(), 0, get_TrxName());
				derivado.setUY_R_Reclamo_ID(incidencia.get_ID());
				derivado.setName(plastic.getName());
				derivado.setAccountNo(this.getAccountNo());
				derivado.setNroTarjetaTitular(plastic.getValue());
				derivado.setDueDateTitular(plastic.getDueDate());
				derivado.setGrpCtaCte(card.getGrpCtaCte());
				derivado.setGAFCOD(plastic.getGAFCOD());
				derivado.setMLCod(plastic.getMLCod());
				derivado.setTipoSocio(plastic.getTipoSocio());
				derivado.saveEx();
			}

			// Aplico inciencia
			if (!incidencia.processIt(DocAction.ACTION_Apply)){
				throw new AdempiereException("No fue posible crear una nueva Incidencia asociada a este Contrato.");
			}
			
			// La dejo en gestion
			incidencia.setStatusReclamo(X_UY_R_Reclamo.STATUSRECLAMO_EnGestion);
			incidencia.setAssignTo_ID(incidencia.getReceptor_ID());
			incidencia.setAssignDateFrom(incidencia.getDateTrx());
			incidencia.saveEx();
			
			// Tracking gestion
			MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(new Timestamp(System.currentTimeMillis()));
			track.setAD_User_ID(incidencia.getAssignTo_ID());
			track.setDescription("Inicio de Gestion");
			track.setUY_R_Reclamo_ID(incidencia.get_ID());		
			track.saveEx();

			String action = "";
			
			// Actualizo bandeja de entrada
			action = " update uy_r_inbox set assignto_id =" + incidencia.getAssignTo_ID() + "," +
							" dateassign = '" + incidencia.getAssignDateFrom() + "'," +
							" statusreclamo ='" + incidencia.getStatusReclamo() + "', " +
							" statustarea ='CUR' " +
							" where uy_r_reclamo_id = " + incidencia.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
			// Seteo el numero de nueva incidencia
			this.setUY_R_Reclamo_ID_2(incidencia.get_ID());
			
			// Adjuntos de la inicidencia
			MRAdjunto anexoa = MRAdjunto.forValue(getCtx(), "anexoa", null);
			MRAdjunto copiaci = MRAdjunto.forValue(getCtx(), "copiaci", null);
			MRAdjunto consdom = MRAdjunto.forValue(getCtx(), "consdom", null);
			MRAdjunto recsueldo = MRAdjunto.forValue(getCtx(), "recsueldo", null);
			MRAdjunto vale = MRAdjunto.forValue(getCtx(), "vale", null);
			
			if (card.getCardAction().equalsIgnoreCase(X_UY_TT_Card.CARDACTION_Nueva)){
				MRReclamoAdjunto radj = new MRReclamoAdjunto(getCtx(), 0, get_TrxName());
				radj.setUY_R_Reclamo_ID(incidencia.get_ID());
				radj.setUY_R_Adjunto_ID(anexoa.get_ID());
				radj.setIsMandatory(true);
				radj.saveEx();
				
				this.setPrintDoc1(true);

			}
			
			if (card.isSolValeSN()){
				MRReclamoAdjunto radj = new MRReclamoAdjunto(getCtx(), 0, get_TrxName());
				radj.setUY_R_Reclamo_ID(incidencia.get_ID());
				radj.setUY_R_Adjunto_ID(vale.get_ID());
				radj.setIsMandatory(true);
				radj.saveEx();
				
				this.setPrintDoc2(true);
				
			}
			if (card.isCedulaSN()){
				MRReclamoAdjunto radj = new MRReclamoAdjunto(getCtx(), 0, get_TrxName());
				radj.setUY_R_Reclamo_ID(incidencia.get_ID());
				radj.setUY_R_Adjunto_ID(copiaci.get_ID());
				radj.setIsMandatory(true);
				radj.saveEx();
				
				this.setPrintDoc3(true);
				
			}
			if (card.isRecSueSN()){
				MRReclamoAdjunto radj = new MRReclamoAdjunto(getCtx(), 0, get_TrxName());
				radj.setUY_R_Reclamo_ID(incidencia.get_ID());
				radj.setUY_R_Adjunto_ID(recsueldo.get_ID());
				radj.setIsMandatory(true);
				radj.saveEx();
				
				this.setPrintDoc4(true);
				
			}

			if (card.isConsDomSN()){
				MRReclamoAdjunto radj = new MRReclamoAdjunto(getCtx(), 0, get_TrxName());
				radj.setUY_R_Reclamo_ID(incidencia.get_ID());
				radj.setUY_R_Adjunto_ID(consdom.get_ID());
				radj.setIsMandatory(true);
				radj.saveEx();
				
				this.setPrintDoc5(true);
				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
}
