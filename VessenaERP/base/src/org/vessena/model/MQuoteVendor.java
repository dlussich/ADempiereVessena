/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 11/07/2012
 */
 
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MProduct;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.report.ReportStarter;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.wf.MWFNode;

/**
 * org.openup.model - MQuoteVendor
 * OpenUp Ltda. Issue #301 
 * Description: 
 * @author Hp - 11/07/2012
 * @see
 */
public class MQuoteVendor extends X_UY_QuoteVendor implements DocAction, IDynamicWF {

	private static final long serialVersionUID = -45672181404560492L;
	private String processMsg = null;
	private boolean justPrepared = false;

	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_QuoteVendor_ID
	 * @param trxName
	 */
	public MQuoteVendor(Properties ctx, int UY_QuoteVendor_ID, String trxName) {
		super(ctx, UY_QuoteVendor_ID, trxName);
		
		if (UY_QuoteVendor_ID <= 0){
			this.setDateTrx(new Timestamp(System.currentTimeMillis()));
		}

	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MQuoteVendor(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/07/2012
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

	/**
	 * Validaciones de datos.
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		// Verifico que la solicitud seleccionada pertenezca al proveedor seleccionado
		if (this.getUY_RFQ_Requisition_ID() > 0){
			MRFQRequisition rfq = new MRFQRequisition(getCtx(), this.getUY_RFQ_Requisition_ID(), get_TrxName());
			if (rfq.getC_BPartner_ID() != this.getC_BPartner_ID()){
				
				// Ante la invalidez, elimino por las dudas las lineas de la cotizacion	
				DB.executeUpdateEx("DELETE FROM uy_quotevendorline WHERE uy_quotevendor_id =" + this.get_ID(), get_TrxName());
				
				this.processMsg = "La solicitud de cotizacion seleccionada NO pertenece a este Proveedor.";
				return false;
			}
			this.setUY_POPolicy_ID(rfq.getUY_POPolicy_ID());
			this.setUY_RFQ_MacroReq_ID(rfq.getUY_RFQ_MacroReq_ID());
			this.setUY_RFQ_MacroReqLine_ID(rfq.getUY_RFQ_MacroReqLine_ID());
		}
		
		return true;
	}

	/**
	 * Genera lineas de cotizacion a partir de las lineas de la solicitud de cotizacion.
	 * En caso de ya existir, verifica si debe regenerarlas o no.
	 * Gabriel Vila. 11/07/2012. 
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (!success) return false;

		if (!newRecord){
			if (is_ValueChanged(COLUMNNAME_UY_RFQ_Requisition_ID)){
				// Si modifico la solcitud de cotizacion, debo eliminar lineas existentes
				DB.executeUpdateEx("DELETE FROM uy_quotevendorline WHERE uy_quotevendor_id =" + this.get_ID(), get_TrxName());
				// Genero nuevas lineas de cotizacion
				this.setQuoteLines();
			}
		}
		else{
			// Genero nuevas lineas de cotizacion
			this.setQuoteLines();
		}
		
		return true;
	}

	/**
	 * Obtiene y retorna lineas de esta cotizacion.
	 * OpenUp Ltda. Issue #1031 
	 * @author Gabriel Vila. - 11/07/2012
	 * @see
	 * @param trxName
	 * @return
	 */
	public List<MQuoteVendorLine> getLines(String trxName) {

		String whereClause = "uy_quotevendor_id = " + this.get_ID();
		
		List<MQuoteVendorLine> lines = new Query(getCtx(), I_UY_QuoteVendorLine.Table_Name, whereClause, trxName)
									   .list();
		
		return lines;
	}

	/**
	 * Genera nuevas lineas de cotizacion tomando como base las lineas de la solicitud seleccionada.
	 * Puede no haber solicitud en el cabezal ya que existe la cotizacion directa.
	 * OpenUp Ltda. Issue #1031 
	 * @author Gabriel Vila - 11/07/2012
	 * @see
	 */
	private void setQuoteLines() {

		// Si no tengo solicitud de cotizacion seleccionada no hago nada
		if (this.getUY_RFQ_Requisition_ID() <= 0) return;
		
		MRFQRequisition rfq = new MRFQRequisition(getCtx(), this.getUY_RFQ_Requisition_ID(), get_TrxName());
		
		// Obtengo lineas de la solicitud de cotizacion seleccionada
		List<MRFQRequisitionLine> rfqLines = rfq.getLines(get_TrxName());
		
		// Genero lineas de cotizacion a partir de las lineas de la solicitud
		for (MRFQRequisitionLine rfqLine: rfqLines){
			
			MQuoteVendorLine line = new MQuoteVendorLine(getCtx(), 0, get_TrxName());
			line.setUY_QuoteVendor_ID(this.get_ID());
			line.setM_Product_ID(rfqLine.getM_Product_ID());
			line.setM_AttributeSetInstance_ID(rfqLine.getM_AttributeSetInstance_ID());
			line.setDescription(rfqLine.getDescription());
			line.setC_UOM_ID(rfqLine.getC_UOM_ID());
			line.setQtyRequired(rfqLine.getQtyRequired());
			line.setQtyQuote(rfqLine.getQtyRequired());
			line.setDatePromised(rfqLine.getDateRequired());
			line.setUY_RFQ_RequisitionLine_ID(rfqLine.get_ID());
			line.saveEx();
		}
		
	}

	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {

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
		
		this.setIsApproved1(true);
		
		if (this.isApproved1()){
			
			this.setNeedApprove1(false);
			this.setIsApproved(true);
			
			this.setApprovalStatus(APPROVALSTATUS_AprobadoPorCompras);
			this.setApprovalDate(new Timestamp(System.currentTimeMillis()));
			this.setApprovedType(APPROVEDTYPE_AUTORIZADO);
			
			// Actualizo cotizacion aprobada en la linea de macro solicitud
			if (this.getUY_RFQ_MacroReqLine_ID() > 0){
				MRFQMacroReqLine macroline = (MRFQMacroReqLine)this.getUY_RFQ_MacroReqLine();
				macroline.setIsApproved(true);
				macroline.saveEx();
			}
			
			MUser userApp = new MUser(getCtx(), Env.getAD_User_ID(Env.getCtx()), null);
			
			// Tracking
			MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(this.getDateTrx());
			track.setAD_User_ID(userApp.get_ID());
			track.setDescription("Cotizacion Aprobada por " + userApp.getDescription());			
			track.setobservaciones(this.getApprovalDescription());
			track.setUY_QuoteVendor_ID(this.get_ID());			
			track.saveEx();
			
			// Elimino tarea de bandeja de entrada
			MRInbox inbox = MRInbox.forTableAndRecord(getCtx(), I_UY_QuoteVendor.Table_ID, this.get_ID(), get_TrxName());
			inbox.deleteEx(true);

		}

		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
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
		
		
		this.setApprovalStatus(APPROVALSTATUS_RechazadoPorCompras);
		this.setApprovalDate(new Timestamp(System.currentTimeMillis()));
		this.setApprovedType(APPROVEDTYPE_RECHAZADO);
		this.setApprovalUser_ID(Env.getAD_User_ID(Env.getCtx()));
		
		this.setIsApproved1(false);
		
		this.setDocAction(DOCACTION_Request);
		
		this.setIsApproved(false);
		
		MUser responsible = new MUser(getCtx(), this.getApprovalUser_ID(), null);
		
		// Tracking
		MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(this.getDateTrx());
		track.setAD_User_ID(this.getApprovalUser_ID());
		track.setDescription("Cotizacion rechazada por " + responsible.getDescription());
		track.setobservaciones(this.getApprovalDescription());
		track.setUY_QuoteVendor_ID(this.get_ID());		
		track.saveEx();

		
		// Elimino tarea de bandeja de entrada
		MRInbox inbox = MRInbox.forTableAndRecord(getCtx(), I_UY_QuoteVendor.Table_ID, this.get_ID(), get_TrxName());
		inbox.deleteEx(true);

		// Envio email al proveedor
		this.sendEmailReject(false);

		/*
		inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_NoAprobado);
		inbox.setAssignTo_ID(this.getApprovalUser_ID());
		inbox.setDateAssign(new Timestamp(System.currentTimeMillis()));
		inbox.saveEx();
		*/		

		return true;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/07/2012
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
		
		this.setIsApproved1(true);
		this.setNeedApprove1(false);
		
		// Tracking
		MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(this.getDateTrx());
		track.setUY_QuoteVendor_ID(this.get_ID());
		track.setAD_User_ID(this.getUpdatedBy());
		track.setDescription("Cotizacion Completada");		
		track.saveEx();
		
		// Elimino de bandeja de entrada
		DB.executeUpdateEx(" delete from uy_r_inbox where ad_table_id =" + I_UY_QuoteVendor.Table_ID + " and record_id =" + this.get_ID(), get_TrxName());
		
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

	/***
	 * Validacion de lineas de cotizacion.
	 * OpenUp Ltda. Issue #315 
	 * @author Gabriel Vila - 08/03/2013
	 * @see
	 * @return
	 */
	private boolean validateLines() {
		
		List<MQuoteVendorLine> lines = this.getLines(get_TrxName());
		if (lines.size() <= 0){
			this.processMsg = "La cotizacion no tiene lineas.";
			return false;
		}
		
		for (MQuoteVendorLine line: lines){
			if (line.getPrice().compareTo(Env.ZERO) <= 0){
				MProduct prod = (MProduct)line.getM_Product();
				this.processMsg = "Debe indicar precio para el producto : " + prod.getName();
				return false;
			}
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
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
	 * @author Hp - 11/07/2012
	 * @see
	 * @return
	 */
	@Override
	public int getDoc_User_ID() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/07/2012
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		return null;
	}

	
	/***
	 * Metodo que se ejecuta en la accion Solicitar.
	 * OpenUp Ltda. Issue #98 
	 * @author Gabriel Vila - 12/11/2012
	 * @see
	 * @return
	 */
	public boolean requestIt() {
		
		try{
		
			// Valido datos obligatorios
			if (this.getDateRequired() == null){
				throw new AdempiereException("Debe indicar Fecha Estimada de Entrega");
			}			
			
			// Valido lineas
			if (!this.validateLines()){
				throw new AdempiereException(this.processMsg);
			}
			
			// Actualizo informacion de estado de aprobacion
			this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionCompras);
			this.setApprovalUser_ID(((MRFQMacroReq)this.getUY_RFQ_MacroReq()).getAD_User_ID());
			this.setApprovalDescription(null);
			this.setApprovalDate(null);
			this.setApprovedType(null);
			
			this.setDateResponse(new Timestamp(System.currentTimeMillis()));

			this.setDocAction(DOCACTION_Approve);
			this.setDocStatus(DOCSTATUS_Requested);
			
			MUser userApp = new MUser(getCtx(), this.getApprovalUser_ID(), null);
			
			// Tracking
			MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(this.getDateTrx());
			track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			track.setDescription("Cotización Ingresada.");
			track.setobservaciones(this.getDescription());
			track.setUY_QuoteVendor_ID(this.get_ID());
			track.saveEx();
			
			MPurchaseTracking track2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track2.setDateTrx(this.getDateTrx());
			track2.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			track2.setDescription("Pendiente Aprobación Compras por " + userApp.getDescription());
			track2.setUY_QuoteVendor_ID(this.get_ID());
			track2.saveEx();

			// Elimino de bandeja de entrada por si es una resolicitud
			DB.executeUpdateEx(" delete from uy_r_inbox where ad_table_id =" + I_UY_QuoteVendor.Table_ID + " and record_id =" + this.get_ID(), get_TrxName());
			
			// Ingreso en bandeja de entrada
			Timestamp today = new Timestamp(System.currentTimeMillis());
			MRType rType = MRType.forValue(getCtx(), "compras", null);
			if ((rType == null) || (rType.get_ID() <= 0)){
				throw new AdempiereException("Falta parametrizar el Tipo de Incidencia : compras");
			}
			MRCause cause = MRCause.forTypeAndValue(getCtx(), rType.get_ID(), "cotcompra", null);
			if ((cause == null) || (cause.get_ID() <= 0)){
				throw new AdempiereException("Falta parametrizar el Tema de Inciencia : cotcompra");
			}
			
			MRInbox inbox = new MRInbox(getCtx(),0, get_TrxName());						
			inbox.setUY_R_Type_ID(rType.get_ID());
			inbox.setUY_R_Cause_ID(cause.get_ID());
			inbox.setSign();
			inbox.setDateTrx(today);
			inbox.setDateAssign(today);
			inbox.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_PendienteAprobacion);
			inbox.setPriority(cause.getPriorityBase());
			inbox.setPriorityManual(cause.getPriorityBase());
			inbox.setAssignTo_ID(this.getApprovalUser_ID());
			inbox.setDueDate(TimeUtil.addDays(inbox.getDateTrx(), cause.getDeadLine()));
			inbox.setAD_Table_ID(I_UY_QuoteVendor.Table_ID);
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
			String subject = "Aprobación Pendiente de Cotizacion de Proveedor.";
			String message = "Estimado/a: " + user.getDescription() + "\n\n";
			message +="La Cotización de Proveedor Número : " + this.getDocumentNo() + " esta en espera de su Aprobación.\n\n";
			message +="Atentamente.";
			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			
			EMail email = new EMail (client, "compras@italcred.com.uy", user.getEMailUser(), subject, message.toString(), false);
			email.createAuthenticator("compras@italcred.com.uy", "c0MPras");
			
			if (email != null){
				String msg = email.send();
				if (EMail.SENT_OK.equals (msg)) log.info("Sent EMail " + subject + " to " + user.getEMailUser());
				else log.warning("Could NOT Send Email: " + subject + " to " + user.getEMailUser() + ": " + msg);
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
		return true;
	}

	
	/***
	 * OpenUp. Gabriel Vila. /11/2012. Issue #98.
	 * Implemento metodo que obtiene responsable dinamico de wf.
	 */
	@Override
	public int getDynamicWFResponsibleID(MWFNode node) {
		
		int value = 0;
		
		try{
		
			if ((node == null) || (node.getColumn() == null)) return value;
			
			MPOPolicy policy = new MPOPolicy(getCtx(), this.getUY_POPolicy_ID(), null);
			
			if (node.getColumn().getName().equalsIgnoreCase(COLUMNNAME_IsApproved1)){
				return policy.getAD_WF_Responsible_ID();
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
		
		MRFQRequisition requisition = (MRFQRequisition)this.getUY_RFQ_Requisition();
		return "Solicitud de Cotizacion : " + requisition.getDocumentNo() + " - " +
		       "Cotizacion Proveedor : " + this.getDocumentNo();


	}

	@Override
	public String getWFActivityHelp() {
		return this.getDescription();
	}

	@Override
	public boolean IsParcialApproved() {
		return false;
	}

	
	/***
	 * Envia email al proveedor de esta cotizacion.
	 * OpenUp Ltda. Issue #583 
	 * @author Gabriel Vila - 20/03/2013
	 * @see
	 */
	public void sendEmail(boolean html){

		try{
			
			MBPartner partner = (MBPartner)this.getC_BPartner();
			
			// Si el proveedor no tiene direccion de email asociada, no hago nada.
			if (partner.get_ValueAsString("EMail") == null) return;
			if (partner.get_ValueAsString("EMail").trim().equalsIgnoreCase("")) return;
			
			String emailAddress = partner.get_ValueAsString("EMail").trim();
			
			//emailAddress = "gabrielvila13@gmail.com";
			
			MClient client = MClient.get(getCtx(), this.getAD_Client_ID());
			
			String subject = "Italcred. Solicitud de Cotizacion.";
			StringBuilder message = new StringBuilder();

			message.append("Italcred tiene el agrado de solicitarles, a traves de este medio, la cotizacion de los siguientes productos/servicios: " + "\n\n\n");
			
			if (html){
				message.append("<br></br>");
				message.append("<br></br>");				
				message.append("<table width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"2\">");
				message.append("<tr>");
				message.append("<th>Nombre</th>");
				message.append("<th>Cantidad Requerida</th>");
				message.append("</tr>");
			}
			
			List<MQuoteVendorLine> lines = this.getLines(get_TrxName());
			for (MQuoteVendorLine line: lines){
				
				
				MProduct prod = (MProduct)line.getM_Product();
				
				if (html){
					message.append(html ? "<tr>" : "");

					message.append(html ? "<td>" : "");
					message.append(prod.getName());
					message.append(html ? "</td>" : "");	

					message.append(html ? "<td>" : " : ");
					message.append(line.getQtyRequired().intValue());
					message.append(html ? "</td>" : "\n");	
					
					message.append(html ? "</tr>" : "");
				}
			}
			
			if (html){
				message.append("</table>");
				message.append("<br></br>");
				message.append("<br></br>");				
			}
			else{
				message.append("\n\n");	
			}
			
			message.append("Para ingresar la cotizacion por favor acceda al siguiente link : ");
			message.append("http://200.58.145.178:8273/webui/index.zul");
			message.append(html ? "<br></br>" : "\n");
			message.append("Numero de Cotizacion : " + this.getDocumentNo());
			message.append(html ? "<br></br>" : "\n");
			message.append(html ? "<br></br>" : "\n");
			
			//client.sendEMail(emailAddress, subject, message.toString(), null, html);
			
			EMail email = new EMail (client, "compras@italcred.com.uy", emailAddress, subject, message.toString(), html);
			email.createAuthenticator("compras@italcred.com.uy", "c0MPras");
			
			// Obtengo y envio pdf de la cotizacion como archivo adjunto a este email.
			File pdfCotizacion = this.getPDFCotizacion();
			email.addAttachment(pdfCotizacion);
			
			if (email != null){
				String msg = email.send();
				if (EMail.SENT_OK.equals (msg))
				{
					log.info("Sent EMail " + subject + " to " + emailAddress);
				}
				else
				{
					log.warning("Could NOT Send Email: " + subject 
						+ " to " + emailAddress + ": " + msg);
				}
			}
			
		}

		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	
	/***
	 * Obtiene y retorna PDF para esta cotizacion. Para ello se ejecuta un proceso para obtener dicho PDF. 
	 * OpenUp Ltda. Issue #583 
	 * @author Gabriel Vila - 17/05/2013
	 * @see
	 * @return
	 */
	private File getPDFCotizacion() {

		try{

			Env.getCtx().put("UY_QuoteVendor_ID", this.get_ID());					

			
			MPInstance instance = new MPInstance(Env.getCtx(), 1000256, 0);
			instance.saveEx();

			ProcessInfo pi = new ProcessInfo ("PrintCheck", 1000256);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
			
			MPInstancePara para = new MPInstancePara(instance, 10);
			para.setParameter("UY_QuoteVendor_ID", this.get_ID());
			para.saveEx();
			
			ReportStarter starter = new ReportStarter();
			//starter.startProcess(getCtx(), pi, Trx.get(this.get_TrxName(), false));
			starter.startProcess(getCtx(), pi, null);
			
			//ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
			//worker.start();     
			
			java.lang.Thread.sleep(3000);

			return pi.getPDFReport();
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
	 * Envio de mail a proveedor de esta cotizacion cuando se rechaza la misma.
	 * OpenUp Ltda. Issue #583 
	 * @author Gabriel Vila - 28/05/2014
	 * @see
	 * @param html
	 */
	public void sendEmailReject(boolean html){
		
		try{
			
			MBPartner partner = (MBPartner)this.getC_BPartner();
			
			// Si el proveedor no tiene direccion de email asociada, no hago nada.
			if (partner.get_ValueAsString("EMail") == null) return;
			if (partner.get_ValueAsString("EMail").trim().equalsIgnoreCase("")) return;
			
			String emailAddress = partner.get_ValueAsString("EMail").trim();
			
			MClient client = MClient.get(getCtx(), this.getAD_Client_ID());
			
			String subject = "Italcred. Rechazo de Cotizacion.";
			StringBuilder message = new StringBuilder();

			message.append("Estimado Proveedor, " + "\n\n\n");
		
			
			message.append("Lamentablemente le debemos informar que su cotización fue rechazada por lo/s motivo/s que se detallan debajo : " + "\n\n\n");
			message.append("Motivo/s del rechazo: " + "\n");
			message.append(this.getApprovalDescription() + "\n\n\n");
			message.append("Por favor sírvase volver a cotizar si entiende que es necesario, accediendo al siguiente link: " + "\n\n");
			
			
			message.append("http://200.58.145.178:8273/webui/index.zul");
			message.append(html ? "<br></br>" : "\n");
			message.append("Numero de Cotizacion : " + this.getDocumentNo());
			message.append(html ? "<br></br>" : "\n");
			message.append(html ? "<br></br>" : "\n\n");
			
			message.append("Desde ya muchas gracias.");
			
			//client.sendEMail(emailAddress, subject, message.toString(), null, html);
			
			EMail email = new EMail (client, "compras@italcred.com.uy", emailAddress, subject, message.toString(), html);
			email.createAuthenticator("compras@italcred.com.uy", "c0MPras");
			
			if (email != null){
				String msg = email.send();
				if (EMail.SENT_OK.equals (msg))
				{
					log.info("Sent EMail " + subject + " to " + emailAddress);
				}
				else
				{
					log.warning("Could NOT Send Email: " + subject 
						+ " to " + emailAddress + ": " + msg);
				}
			}
			
		}

		catch (Exception e){
			throw new AdempiereException(e);
		}
	}
}
