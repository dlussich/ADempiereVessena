/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/02/2013
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MPInstance;
import org.compiere.model.MProcess;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.model.X_M_Requisition;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.wf.MWFNode;

/**
 * org.openup.model - MOrderSection
 * OpenUp Ltda. Issue #381 
 * Description: Orden de compra parcial para un sector de compra. 
 * @author Gabriel Vila - 21/02/2013
 * @see
 */
public class MOrderSection extends X_UY_OrderSection implements DocAction, IDynamicWF {

	private static final long serialVersionUID = -82766599905401557L;
	private String processMsg = null;
	private boolean justPrepared = false;
	
	private boolean isParcialApproved;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_OrderSection_ID
	 * @param trxName
	 */
	public MOrderSection(Properties ctx, int UY_OrderSection_ID, String trxName) {
		super(ctx, UY_OrderSection_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MOrderSection(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {

		this.isParcialApproved = false;
		
		String valueDocApproved ="", parcialApprovedDescription = "";
		
		// Valido que usuario actual tenga permiso para aprobar esta solicitud
		if (Env.getAD_User_ID(Env.getCtx()) != this.getApprovalUser_ID()){

			// Verifico que no sea el usuario delegado
			MUser user = new MUser(getCtx(), this.getApprovalUser_ID(), null);
			if (user.get_ValueAsBoolean("DelegateTasks")){
				if (user.get_ValueAsInt("DelegateUser_ID") <= 0){
					throw new AdempiereException("Usted No tiene permisos para Aprobar este Documento.");		
				}
			}
			else{
				throw new AdempiereException("Usted No tiene permisos para Aprobar este Documento.");
			}

		}
		
		if ((this.getApprovalDescription() == null) || (this.getApprovalDescription().equalsIgnoreCase(""))){
			throw new AdempiereException("Debe ingresar Observaciones Autorizador.");
		}
		
		if (this.isApproved()) return true;

		MRInbox inbox = MRInbox.forTableAndRecord(getCtx(), I_UY_OrderSection.Table_ID, this.get_ID(), get_TrxName());
		MUser userApp = new MUser(getCtx(), Env.getAD_User_ID(Env.getCtx()), null);
		MPOSection section = (MPOSection)this.getUY_POSection();		
		
		// Puede haber n usuarios para aprobar en nivel 1. Estos usuarios estan ordenados por secuencia.
		// Si vengo en aprobacion de nivel 1 tengo que verificar si no hay otro usuario para aprobar en 
		// este nivel con secuencia mayor. Si hay uno entonces este documento sigue en estado solicitado
		// y lo que cambia es el usuario que tiene que aprobar. Actualizo bandeja de entrada y tracking.
		if (this.getApprovalStatus().equalsIgnoreCase(X_M_Requisition.APPROVALSTATUS_PendienteAprobacionNivel1)){
			// Intento obtener siguiente usuario autorizador para nivel 1.
			int nextAppUserID = section.getNextApprovalUser(X_UY_POPolicyUser.NIVEL_Nivel1, this.getUY_POPolicyRange_ID(), this.getApprovalUser_ID(), this.getApprovalSeqNo());
			if (nextAppUserID > 0){
				
				// Tengo otro usuario autorizador en nivel 1.
				MUser nextAppUser = new MUser(getCtx(), nextAppUserID, null);

				// Actualizo datos de autorizacion
				this.setApprovalUser_ID(nextAppUser.get_ID());
				this.isParcialApproved = true;
				parcialApprovedDescription = this.getApprovalDescription();
				this.setApprovalDescription(null);
				this.setApprovalDate(null);
				this.setApprovedType(null);
				this.setApprovalSeqNo(section.userAppSeqNo);
				
				// Actualizo tarea en bandeja de entrada
				inbox.setAssignTo_ID(nextAppUser.get_ID());
				inbox.setDateAssign(new Timestamp(System.currentTimeMillis()));
				inbox.saveEx();

				// Tracking
				MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				track.setDateTrx(this.getDateTrx());
				track.setAD_User_ID(this.getApprovalUser_ID());
				track.setDescription("Orden Parcial Aprobada Nivel 1 por " + userApp.getDescription());
				track.setobservaciones(parcialApprovedDescription);
				track.setUY_OrderSection_ID(this.get_ID());				
				track.saveEx();
				
				MPurchaseTracking track2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				track2.setDateTrx(this.getDateTrx());
				track2.setAD_User_ID(userApp.get_ID());
				track2.setDescription("Pendiente Aprobación Nivel 1 por " + nextAppUser.getDescription());
				track2.setUY_OrderSection_ID(this.get_ID());				
				track2.saveEx();
				
				// Tracking orden de compra padre
				MPurchaseTracking trackOrd1 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				trackOrd1.setDateTrx(this.getDateTrx());
				trackOrd1.setAD_User_ID(this.getApprovalUser_ID());
				trackOrd1.setDescription("Orden Parcial " + this.getDocumentNo() + " Aprobada Nivel 1 por " + userApp.getDescription());
				trackOrd1.setobservaciones(parcialApprovedDescription);
				trackOrd1.setC_Order_ID(this.getC_Order_ID());				
				trackOrd1.saveEx();
				
				MPurchaseTracking trackOrd2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				trackOrd2.setDateTrx(this.getDateTrx());
				trackOrd2.setAD_User_ID(userApp.get_ID());
				trackOrd2.setDescription("Orden Parcial " + this.getDocumentNo() + " Pendiente Aprobación Nivel 1 por " + nextAppUser.getDescription());
				trackOrd2.setC_Order_ID(this.getC_Order_ID());				
				trackOrd2.saveEx();
				
				return true;
			}
		
		}
		
		
		// Seteo aprobacion segun nivel
		if (this.getApprovalStatus().equalsIgnoreCase(APPROVALSTATUS_PendienteAprobacionNivel1)){
			this.setIsApproved1(true);
		}
		else if (this.getApprovalStatus().equalsIgnoreCase(APPROVALSTATUS_PendienteAprobacionNivel2)){
			this.setIsApproved2(true);
		}
		
		// Si aprobado nivel 1
		if (this.isApproved1()){
			this.setNeedApprove1(false);
			valueDocApproved="approved1";
			this.setApprovalStatus(APPROVALSTATUS_AprobadoNivel1);
			this.setIsApproved2(true);
			this.setApprovalDate(new Timestamp(System.currentTimeMillis()));
			this.setApprovedType(APPROVEDTYPE_AUTORIZADO);
		}

		
		
		if (this.isApproved1() && this.isApproved2()){
		
			this.setIsApproved(true);
			
			// Elimino tarea de bandeja de entrada
			inbox.deleteEx(true);
			
			// Tracking
			MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(this.getDateTrx());
			track.setUY_OrderSection_ID(this.get_ID());
			track.setAD_User_ID(userApp.get_ID());
			
			if (valueDocApproved.equalsIgnoreCase("approved1")){
				track.setDescription("Orden Parcial Aprobada Nivel 1 por " + userApp.getDescription());
			}
			else if (valueDocApproved.equalsIgnoreCase("approved2")){
				track.setDescription("Orden Parcial Aprobada Nivel 2 por " + userApp.getDescription());
			}
			else{
				track.setDescription("Orden Parcial Aprobada");	
			}			
			
			track.setobservaciones(this.getApprovalDescription());
			track.saveEx();

			this.saveEx();
			
			// Cuando se aprueba una order parcial, lanzo verificacion en la orden
			// de compra para ver si ya se aprobaron todas las parciales.
			if (this.getC_Order_ID() > 0){
				MOrder order = new MOrder(getCtx(), this.getC_Order_ID(), get_TrxName());
				order.checkPOParcialApproved();
			}
		}
		else{
			if (this.isParcialApproved){
				
				MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				track.setDateTrx(this.getDateTrx());
				track.setAD_User_ID(userApp.get_ID());
				track.setDescription("Solicitud Aprobada Nivel 1 por " + userApp.getDescription());
				track.setobservaciones(parcialApprovedDescription);
				track.setUY_OrderSection_ID(this.get_ID());
				track.saveEx();
				
				MUser userN2 = new MUser(getCtx(), this.getApprovalUser_ID(), null);
				
				MPurchaseTracking track2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				track2.setDateTrx(this.getDateTrx());
				track2.setAD_User_ID(userApp.get_ID());
				track2.setDescription("Pendiente Aprobación Nivel 2 por " + userN2.getDescription());
				track2.setUY_OrderSection_ID(this.get_ID());				
				track2.saveEx();
			}
		}	
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean rejectIt() {

		// Valido que usuario actual tenga permiso para aprobar esta solicitud
		if (Env.getAD_User_ID(Env.getCtx()) != this.getApprovalUser_ID()){
			
			// Verifico que no sea el usuario delegado
			MUser user = new MUser(getCtx(), this.getApprovalUser_ID(), null);
			if (user.get_ValueAsBoolean("DelegateTasks")){
				if (user.get_ValueAsInt("DelegateUser_ID") <= 0){
					throw new AdempiereException("Usted No tiene permisos para Rechazar este Documento.");		
				}
			}
			else{
				throw new AdempiereException("Usted No tiene permisos para Rechazar este Documento.");
			}
			
		}
		
		if ((this.getApprovalDescription() == null) || (this.getApprovalDescription().equalsIgnoreCase(""))){
			throw new AdempiereException("Debe ingresar observaciones con motivo de rechazo.");
		}
		
		MRInbox inbox = MRInbox.forTableAndRecord(getCtx(), I_UY_OrderSection.Table_ID, this.get_ID(), get_TrxName());
		
		// Actualizo estado de aprobacion segun desde que nivel se rechazo
		String valueDocReject = "";
		if (this.isApproved1()){
			this.setApprovalStatus(APPROVALSTATUS_RechazadoNivel2);
			valueDocReject = "rejected2";
			inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_NoAprobadoNivel2);
		}
		else{
			this.setApprovalStatus(APPROVALSTATUS_RechazadoNivel1);
			valueDocReject = "rejected1";
			inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_NoAprobadoNivel1);
		}
		
		MPOPolicy policy = (MPOPolicy)this.getUY_POPolicy();
		
		inbox.setAssignTo_ID(policy.getAD_User_ID());
		inbox.setDateAssign(new Timestamp(System.currentTimeMillis()));
		inbox.saveEx();
		
		// Actualizo datos de aprobacion
		this.setApprovedType(APPROVEDTYPE_RECHAZADO);
		this.setApprovalDate(new Timestamp(System.currentTimeMillis()));
		this.setApprovalUser_ID(Env.getAD_User_ID(Env.getCtx()));
		this.setApprovalSeqNo(0);
		
		// Seteo todo nivel de aprobacion como no aprobado
		this.setIsApproved1(false);
		this.setIsApproved2(false);
		this.setIsApproved(false);
		
		MUser responsible = new MUser(getCtx(), this.getApprovalUser_ID(), null);
		
		// Tracking
		MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(this.getDateTrx());
		track.setAD_User_ID(responsible.getAD_User_ID());
		track.setDescription("Solicitud Rechazada " + ((valueDocReject.equalsIgnoreCase("rejected1")) ? " Nivel 1 " : " Nivel 2 ") + " por " + responsible.getDescription());
		track.setobservaciones(this.getApprovalDescription());
		track.setUY_OrderSection_ID(this.get_ID());		
		track.saveEx();

		// Tracking Orden de compra padre
		MPurchaseTracking trackOrd = new MPurchaseTracking(getCtx(), 0, get_TrxName());
		trackOrd.setDateTrx(this.getDateTrx());
		trackOrd.setAD_User_ID(responsible.getAD_User_ID());
		trackOrd.setDescription("Orden Parcial " + this.getDocumentNo() + " Rechazada " + ((valueDocReject.equalsIgnoreCase("rejected1")) ? " Nivel 1 " : " Nivel 2 ") + " por " + responsible.getDescription());
		trackOrd.setobservaciones(this.getApprovalDescription());
		trackOrd.setC_Order_ID(this.getC_Order_ID());		
		trackOrd.saveEx();

		
		// Fin OpenUp
		
		this.setDocAction(DOCACTION_Request);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/02/2013
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

		if (!this.isApproved()){
			this.processMsg = "No se puede completar el Documento ya que el mismo NO esta Aprobado.";
			return DocAction.STATUS_Invalid;
		}
		
		// Me aseguro al completar de dejar todo aprobado
		this.setIsApproved1(true);
		this.setIsApproved2(true);
		this.setNeedApprove1(false);
		this.setNeedApprove2(false);
		
		// Tracking
		MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(this.getDateTrx());
		track.setUY_OrderSection_ID(this.get_ID());
		track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
		track.setDescription("Orden Parcial Completada");
		track.saveEx();

		// Tracking Orden de compra padre
		MPurchaseTracking trackOrd = new MPurchaseTracking(getCtx(), 0, get_TrxName());
		trackOrd.setDateTrx(this.getDateTrx());
		trackOrd.setC_Order_ID(this.getC_Order_ID());
		trackOrd.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
		trackOrd.setDescription("Orden Parcial " + this.getDocumentNo() + " Completada");
		trackOrd.saveEx();

		
		// Elimino de bandeja de entrada
		DB.executeUpdateEx(" delete from uy_r_inbox where ad_table_id =" + I_UY_OrderSection.Table_ID + " and record_id =" + this.get_ID(), get_TrxName());
		
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
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
	 * @author Hp - 21/02/2013
	 * @see
	 * @return
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/02/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Setea documento por defecto para este documento.
	 * OpenUp Ltda. Issue #381
	 * @author Gabriel Vila - 22/02/2012
	 * @see
	 */
	public void setDefaultDocType() {
		
		MDocType doc = MDocType.forValue(getCtx(), "poparcial", null);
		if (doc.get_ID() > 0) this.setC_DocType_ID(doc.get_ID());
		
	}

	/***
	 * Metodo que se ejecuta en la accion Solicitar.
	 * OpenUp Ltda. Issue #92 
	 * @author Gabriel Vila - 05/11/2012
	 * @see
	 * @return
	 */
	public boolean requestIt() {
		
		try{
		
			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			
			// Obtengo y me seteo el rango de politica de compra segun mi total
			MPOSection section = new MPOSection(getCtx(), this.getUY_POSection_ID(), null);

			MPOPolicyRange rango = section.getPolicyRange(this.getDateTrx(), this.getTotalLines(), 
								schema.getC_Currency_ID(), null);

			if (rango == null){
				throw new AdempiereException("No se pudo obtener rango de politica de compra segun Total del Documento.");
			}
			this.setUY_POPolicyRange_ID(rango.get_ID());

			// Segunda aprobacion se verifica y hace en la orden de compra.
			this.setNeedApprove2(false);
			
			// Actualizo informacion de estado de aprobacion
			this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionNivel1);
			this.setApprovalUser_ID(section.getApprovalUser(X_UY_POPolicyUser.NIVEL_Nivel1, this.getUY_POPolicyRange_ID()));
			this.setApprovalDescription(null);
			this.setApprovalDate(null);
			this.setApprovedType(null);
			this.setApprovalSeqNo(section.userAppSeqNo);
			
			this.setDocAction(DOCACTION_Approve);
			this.setDocStatus(DocumentEngine.STATUS_Requested);
			
			MUser userApp = new MUser(getCtx(), this.getApprovalUser_ID(), null);
			
			// Tracking
			MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(this.getDateTrx());
			track.setUY_OrderSection_ID(this.get_ID());
			track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			track.setDescription("Orden Parcial creada");
			track.saveEx();
			
			MPurchaseTracking track2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track2.setDateTrx(this.getDateTrx());
			track2.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			track2.setDescription("Pendiente Aprobación Nivel 1 por " + userApp.getDescription());
			track2.setUY_OrderSection_ID(this.get_ID());
			track2.saveEx();

			// Tracking Orden de compra padre
			MPurchaseTracking trackOrd1 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			trackOrd1.setDateTrx(this.getDateTrx());
			trackOrd1.setC_Order_ID(this.getC_Order_ID());
			trackOrd1.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			trackOrd1.setDescription("Orden Parcial " + this.getDocumentNo() + " creada");
			trackOrd1.saveEx();
			
			MPurchaseTracking trackOrd2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			trackOrd2.setDateTrx(this.getDateTrx());
			trackOrd2.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			trackOrd2.setDescription("Orden Parcial " + this.getDocumentNo() + " Pendiente Aprobación Nivel 1 por " + userApp.getDescription());
			trackOrd2.setC_Order_ID(this.getC_Order_ID());
			trackOrd2.saveEx();

			
			// Elimino de bandeja de entrada por si es una resolicitud
			DB.executeUpdateEx(" delete from uy_r_inbox where ad_table_id =" + I_UY_OrderSection.Table_ID + " and record_id =" + this.get_ID(), get_TrxName());
			
			// Ingreso en bandeja de entrada
			Timestamp today = new Timestamp(System.currentTimeMillis());
			MRType rType = MRType.forValue(getCtx(), "compras", null);
			if ((rType == null) || (rType.get_ID() <= 0)){
				throw new AdempiereException("Falta parametrizar el Tipo de Incidencia : compras");
			}
			MRCause cause = MRCause.forTypeAndValue(getCtx(), rType.get_ID(), "opcompra", null);
			if ((cause == null) || (cause.get_ID() <= 0)){
				throw new AdempiereException("Falta parametrizar el Tema de Inciencia : opcompra");
			}
			
			MRInbox inbox = new MRInbox(getCtx(),0, get_TrxName());						
			inbox.setUY_R_Type_ID(rType.get_ID());
			inbox.setUY_R_Cause_ID(cause.get_ID());
			inbox.setSign();
			inbox.setDateTrx(today);
			inbox.setDateAssign(today);
			inbox.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_PendienteAprobacionNivel1);
			inbox.setPriority(cause.getPriorityBase());
			inbox.setPriorityManual(this.getPriorityRule());
			inbox.setAssignTo_ID(this.getApprovalUser_ID());
			inbox.setDueDate(TimeUtil.addDays(inbox.getDateTrx(), cause.getDeadLine()));
			inbox.setAD_Table_ID(I_UY_OrderSection.Table_ID);
			inbox.setRecord_ID(this.get_ID());
			inbox.setC_DocType_ID(this.getC_DocType_ID());
			inbox.setUY_R_PtoResolucion_ID(cause.getUY_R_PtoResolucion_ID());
			inbox.setMediumTerm(cause.getMediumTerm());
			if (this.getDocumentNo() != null){
				inbox.setReferenceNo("Numero: " + this.getDocumentNo());	
			}			
			inbox.saveEx();
			
			// Envio email a usuario responsable
			MUser user = new MUser(getCtx(), this.getApprovalUser_ID(), null);
			String subject = "Aprobación Pendiente de Orden Parcial de Compra.";
			String message = "Estimado/a: " + user.getDescription() + "\n\n";
			message +="La Orden Parcial de Compra Número : " + this.getDocumentNo() + " esta en espera de su Aprobación.\n\n";
			message +="Atentamente.";
			client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			
			EMail email = new EMail (client, "compras@italcred.com.uy", user.getEMailUser(), subject, message.toString(), false);
			email.createAuthenticator("compras@italcred.com.uy", "c0MPras");
			
			if (email != null){
				String msg = email.send();
				if (EMail.SENT_OK.equals (msg)) log.info("Sent EMail " + subject + " to " + user.getEMailUser());
				else log.warning("Could NOT Send Email: " + subject + " to " + user.getEMailUser() + ": " + msg);
			}

			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
		return true;
	}

	@Override
	public int getDynamicWFResponsibleID(MWFNode node) {

		int value = 0;
		
		try{
		
			if ((node == null) || (node.getColumn() == null)) return value;
			
			MPOSection section = new MPOSection(getCtx(), this.getUY_POSection_ID(), null);
			
			if (node.getColumn().getName().equalsIgnoreCase(COLUMNNAME_IsApproved1)){

				return section.getApprovalUser(X_UY_POPolicyUser.NIVEL_Nivel1, this.getUY_POPolicyRange_ID());
						
			}
			else if (node.getColumn().getName().equalsIgnoreCase(COLUMNNAME_IsApproved2)){
				
				return section.getApprovalUser(X_UY_POPolicyUser.NIVEL_Nivel2, this.getUY_POPolicyRange_ID());

			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
		return value;

	}

	@Override
	public void setApprovalInfo(int AD_WF_Responsible_ID, String textMsg) {
		this.setApprovalUser_ID(AD_WF_Responsible_ID);
		this.setApprovalDescription(textMsg);		
	}

	@Override
	public String getWFActivityDescription() {
		MPOSection section = (MPOSection)this.getUY_POSection();
		return "Sector de Compra : " + section.getName();
	}

	@Override
	public String getWFActivityHelp() {
		return this.getHelp();
	}

	@Override
	public boolean IsParcialApproved() {
		return this.isParcialApproved ;
	}

	
	/***
	 * Si quiero solicitar una orden de compra parcial desde el codigo, tengo que hacerlo de tal manera que
	 * se ejecute en el flujo de trabajo. Eso lo logro poniendo la accion que quiero hacer (docstatus='RQ') y 
	 * luego invocando el proceso del boton DocAction que contiene el flujo de trabajo.
	 * OpenUp Ltda. Issue #381 
	 * @author Gabriel Vila - 26/02/2013
	 * @see
	 */
	public void executeDocActionProcess(){

		int adProcessID = MProcess.getProcess_ID("UY_POrderSection", null);

		MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
		instance.saveEx();

		ProcessInfo pi = new ProcessInfo("OrderSection", adProcessID);
		pi.setAD_PInstance_ID(instance.getAD_PInstance_ID());
		
		pi.setRecord_ID(this.get_ID());

		
		//String trxNameAux = Trx.createTrxName();
		//Trx trxName = Trx.get(trxNameAux, true);
		//Trx trxAux = Trx.get(this.get_TrxName(), false);
		
		
		ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
		//ProcessCtl worker = new ProcessCtl(null, 0, pi, trxName);
		//ProcessCtl worker = new ProcessCtl(null, 0, pi, trxAux);
		
		worker.start();

	}
	
	/***
	 * Una orden parcial es autorizada en workflow por el gerente de area.
	 * Si requiere seguna aprobacion se hara de manera conjunta en la orden de compra.
	 * OpenUp Ltda. Issue #381 
	 * @author Gabriel Vila - 03/03/2013
	 * @see
	 * @return
	 */
	public boolean needSecondApprove(){
		
		try{
			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			
			// Obtengo y me seteo el rango de politica de compra segun mi total
			MPOSection section = new MPOSection(getCtx(), this.getUY_POSection_ID(), null);
			MPOPolicyRange rango = section.getPolicyRange(this.getDateTrx(), this.getTotalLines(), 
								schema.getC_Currency_ID(), null);

			return section.needSecondApprove(rango.get_ID());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	@Override
	public void processAutomaticApproval() {

	}

	@Override
	public void processAutomaticComplete() {

		try 
		{
			if (!this.processIt(ACTION_Complete)){
				if (this.getProcessMsg() != null){
					throw new AdempiereException(this.getProcessMsg());
				}
			}
			else{
				this.saveEx();
			}
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

		
	}

	
	/***
	 * Retorna todas las ordenes de compra por sector asociadas a  
	 * esta orden de compra.
	 * OpenUp Ltda. Issue #3629 
	 * @author INes Fernandez - 24/02/2015
	 * @see
	 * @return
	 */
	public List<MOrderSectionLine> getAllPOSectionLines(){
		String whereClause= I_UY_OrderSectionLine.COLUMNNAME_UY_OrderSection_ID + "=" + get_ID();
		List<MOrderSectionLine> lines = new Query(getCtx(), I_UY_OrderSectionLine.Table_Name, whereClause, get_TrxName())
		.setOrderBy(I_UY_OrderSectionLine.COLUMNNAME_M_Product_ID).list();
		return lines;
		}
	
}
