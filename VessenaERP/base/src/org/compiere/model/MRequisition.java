/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.wf.MWFNode;
import org.openup.model.IDynamicWF;
import org.openup.model.MPOPolicy;
import org.openup.model.MPOPolicyCategory;
import org.openup.model.MPOPolicyRange;
import org.openup.model.MPOSection;
import org.openup.model.MPurchaseTracking;
import org.openup.model.MRCause;
import org.openup.model.MRInbox;
import org.openup.model.MRType;
import org.openup.model.MStockTransaction;
import org.openup.model.X_UY_POPolicyUser;
import org.openup.model.X_UY_R_Inbox;

/**
 *	Requisition Model
 *	
 *  @author Jorg Janke
 *
 *  @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org 
 *			@see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962 
 *  @version $Id: MRequisition.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 *  @author red1
 *  		<li>FR [ 2214883 ] Remove SQL code and Replace for Query  
 *  @author Teo Sarca, www.arhipac.ro
 *  		<li>FR [ 2744682 ] Requisition: improve error reporting
 */
public class MRequisition extends X_M_Requisition implements DocAction, IDynamicWF
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 898606565778668659L;

	private boolean isParcialApproved = false;
	
	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_Requisition_ID id
	 */
	public MRequisition (Properties ctx, int M_Requisition_ID, String trxName)
	{
		super (ctx, M_Requisition_ID, trxName);
		if (M_Requisition_ID == 0)
		{
		//	setDocumentNo (null);
		//	setAD_User_ID (0);
		//	setM_PriceList_ID (0);
		//	setM_Warehouse_ID(0);
			setDateDoc(new Timestamp(System.currentTimeMillis()));
			setDateRequired (new Timestamp(System.currentTimeMillis()));
			setDocAction (DocAction.ACTION_Complete);	// CO
			setDocStatus (DocAction.STATUS_Drafted);		// DR
			setPriorityRule (PRIORITYRULE_Medium);	// 5
			setTotalLines (Env.ZERO);
			setIsApproved (false);
			setPosted (false);
			setProcessed (false);
		}
	}	//	MRequisition

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public MRequisition (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRequisition
	
	/** Lines						*/
	private MRequisitionLine[]		m_lines = null;
	
	/**
	 * 	Get Lines
	 *	@return array of lines
	 */
	public MRequisitionLine[] getLines()
	{
		if (m_lines != null) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		//red1 - FR: [ 2214883 ] Remove SQL code and Replace for Query  
 	 	final String whereClause = I_M_RequisitionLine.COLUMNNAME_M_Requisition_ID+"=?";
	 	List <MRequisitionLine> list = new Query(getCtx(), I_M_RequisitionLine.Table_Name, whereClause, get_TrxName())
			.setParameters(get_ID())
			.setOrderBy(I_M_RequisitionLine.COLUMNNAME_Line)
			.list();
	 	//  red1 - end -

		m_lines = new MRequisitionLine[list.size ()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getLines
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MRequisition[");
		sb.append(get_ID()).append("-").append(getDocumentNo())
			.append(",Status=").append(getDocStatus()).append(",Action=").append(getDocAction())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 * 	Get Document Info
	 *	@return document info
	 */
	public String getDocumentInfo()
	{
		return Msg.getElement(getCtx(), "M_Requisition_ID") + " " + getDocumentNo();
	}	//	getDocumentInfo
	
	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF

	/**
	 * 	Set default PriceList
	 */
	public void setM_PriceList_ID()
	{
		MPriceList defaultPL = MPriceList.getDefault(getCtx(), false,0);//OpenUp SBT 07-04-2016 Issue #
		if (defaultPL == null)
			defaultPL = MPriceList.getDefault(getCtx(), true,0);//OpenUp SBT 07-04-2016 Issue #
		if (defaultPL != null)
			setM_PriceList_ID(defaultPL.getM_PriceList_ID());
	}	//	setM_PriceList_ID()
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		// OpenUp. Gabriel Vila. 27/03/2012. Issue #1005.
		// No considero precios en requisicion

		/*
		if (getM_PriceList_ID() == 0)
			setM_PriceList_ID();
		*/
		// Fin OpenUp.
		
		return true;
	}	//	beforeSave
	
	@Override
	protected boolean beforeDelete() {
		for (MRequisitionLine line : getLines()) {
			line.deleteEx(true);
		}
		
		// OpenUp. Gabriel Vila. 05/02/2014. Issue #285
		// Elimino tarea de bandeja de entrada
		try{
			DB.executeUpdateEx(" delete from uy_r_inbox where ad_table_id =" + I_M_Requisition.Table_ID + " and record_id=" + this.get_ID(), get_TrxName());	
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
		// Fin OpenUp. Issue #285
		
		return true;
	}

	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (processAction, getDocAction());
	}	//	process
	
	/**	Process Message 			*/
	private String			m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean 		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success 
	 */
	public boolean unlockIt()
	{
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}	//	unlockIt
	
	/**
	 * 	Invalidate Document
	 * 	@return true if success 
	 */
	public boolean invalidateIt()
	{
		log.info("invalidateIt - " + toString());
		return true;
	}	//	invalidateIt
	
	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid) 
	 */
	public String prepareIt()
	{
		// OpenUP. Gabriel Vila. 27/03/2012. Issue #1005.
		// PrepareIt segun modelo OpenUp.
		return this.prepareItOpenUp();
		
		/*
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		MRequisitionLine[] lines = getLines();
		
		//	Invalid
		if (getAD_User_ID() == 0 
			|| getM_PriceList_ID() == 0
			|| getM_Warehouse_ID() == 0)
		{
			return DocAction.STATUS_Invalid;
		}
		
		if(lines.length == 0)
		{
			throw new AdempiereException("@NoLines@");
		}
		
		//	Std Period open?
		MPeriod.testPeriodOpen(getCtx(), getDateDoc(), MDocType.DOCBASETYPE_PurchaseRequisition, getAD_Org_ID());
		
		//	Add up Amounts
		int precision = MPriceList.getStandardPrecision(getCtx(), getM_PriceList_ID());
		BigDecimal totalLines = Env.ZERO;
		for (int i = 0; i < lines.length; i++)
		{
			MRequisitionLine line = lines[i];
			BigDecimal lineNet = line.getQty().multiply(line.getPriceActual());
			lineNet = lineNet.setScale(precision, BigDecimal.ROUND_HALF_UP);
			if (lineNet.compareTo(line.getLineNetAmt()) != 0)
			{
				line.setLineNetAmt(lineNet);
				line.saveEx();
			}
			totalLines = totalLines.add (line.getLineNetAmt());
		}
		if (totalLines.compareTo(getTotalLines()) != 0)
		{
			setTotalLines(totalLines);
			saveEx();
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
		
		*/
		// Fin OpenUp
		
	}	//	prepareIt
	
	/**
	 * 	Approve Document
	 * 	@return true if success 
	 */
	public boolean  approveIt()
	{
		log.info("approveIt - " + toString());
		
		// OpenUp. Gabriel Vila. 05/11/2012. Issue #92.
		// Al aprobarse me aseguro que todas las instancias de aprobacion queden marcadas
		// Comento y sustituyo codigo.
		
		// setIsApproved(true);
		
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

		if ((this.getHelp() == null) || (this.getHelp().equalsIgnoreCase(""))){
			throw new AdempiereException("Debe ingresar Observaciones Autorizador.");
		}

		if (this.isApproved()) return true;
		
		MRInbox inbox = MRInbox.forTableAndRecord(getCtx(), I_M_Requisition.Table_ID, this.get_ID(), get_TrxName());
		MUser userApp = new MUser(getCtx(), Env.getAD_User_ID(Env.getCtx()), null);
		String parcialApprovedDescription = "";
		MPOSection section = (MPOSection)this.getUY_POSection();
		
		// Antes que nada si esta solicitud esta siendo aprobada por concepto de stock disponible
		if (this.getApprovalStatus().equalsIgnoreCase(X_M_Requisition.APPROVALSTATUS_PendienteAprobacionCompras)){

			this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionNivel1);
			this.setApprovalUser_ID(section.getApprovalUser(X_UY_POPolicyUser.NIVEL_Nivel1, this.getUY_POPolicyRange_ID()));
			this.setApprovalSeqNo(section.userAppSeqNo);
			this.isParcialApproved = true;
			parcialApprovedDescription = this.getHelp();
			this.setHelp(null);
			this.setApprovalDate(null);
			this.setApprovedType(null);
			
			MUser nextAppUser = new MUser(getCtx(), this.getApprovalUser_ID(), null);
			
			// Actualizo tarea en bandeja de entrada
			inbox.setAssignTo_ID(nextAppUser.get_ID());
			inbox.setDateAssign(new Timestamp(System.currentTimeMillis()));
			inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_PendienteAprobacionNivel1);
			inbox.saveEx();

			// Tracking
			MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(this.getDateDoc());
			track.setAD_User_ID(this.getApprovalUser_ID());
			track.setDescription("Solicitud Aprobada en Stock por " + userApp.getDescription());
			track.setobservaciones(parcialApprovedDescription);
			track.setM_Requisition_ID(this.get_ID());				
			track.saveEx();
			
			MPurchaseTracking track2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track2.setDateTrx(this.getDateDoc());
			track2.setAD_User_ID(userApp.get_ID());
			track2.setDescription("Pendiente Aprobación Nivel 1 por " + nextAppUser.getDescription());
			track2.setM_Requisition_ID(this.get_ID());				
			track2.saveEx();
			
			return true;
		}
		
		
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
				parcialApprovedDescription = this.getHelp();
				this.setHelp(null);
				this.setApprovalDate(null);
				this.setApprovedType(null);
				this.setApprovalSeqNo(section.userAppSeqNo);
				
				// Actualizo tarea en bandeja de entrada
				inbox.setAssignTo_ID(nextAppUser.get_ID());
				inbox.setDateAssign(new Timestamp(System.currentTimeMillis()));
				inbox.saveEx();

				// Tracking
				MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				track.setDateTrx(this.getDateDoc());
				track.setAD_User_ID(this.getApprovalUser_ID());
				track.setDescription("Solicitud Aprobada Nivel 1 por " + userApp.getDescription());
				track.setobservaciones(parcialApprovedDescription);
				track.setM_Requisition_ID(this.get_ID());				
				track.saveEx();
				
				MPurchaseTracking track2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				track2.setDateTrx(this.getDateDoc());
				track2.setAD_User_ID(userApp.get_ID());
				track2.setDescription("Pendiente Aprobación Nivel 1 siguiente secuencia por " + nextAppUser.getDescription());
				track2.setM_Requisition_ID(this.get_ID());				
				track2.saveEx();
				
				return true;
			}
		
		}
		
		int  modoAprobacion = 0; 
		
		// Seteo aprobacion segun nivel
		if (this.getApprovalStatus().equalsIgnoreCase(X_M_Requisition.APPROVALSTATUS_PendienteAprobacionNivel1)){
			modoAprobacion = 1;
			this.setIsApproved1(true);
		}
		else if (this.getApprovalStatus().equalsIgnoreCase(X_M_Requisition.APPROVALSTATUS_PendienteAprobacionNivel2)){
			modoAprobacion = 2;
			this.setIsApproved2(true);
		}
		else if (this.getApprovalStatus().equalsIgnoreCase(X_M_Requisition.APPROVALSTATUS_PendienteAprobacionCategoria)){
			modoAprobacion = 3;
			this.setIsApprovedCategory(true);
		}

		// Aprobacion nivel 1
		if (modoAprobacion == 1){
			this.setIsApproved1(true);
			this.setNeedApprove1(false);
			// Verifico si necesito aprobacion de categoria
			if (this.getUY_POPolicyCategory_ID() > 0){
				this.setIsApprovedCategory(false);
				this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionCategoria);
				this.setApprovalUser_ID(this.getCategoryUser_ID());
				parcialApprovedDescription = this.getHelp();
				this.isParcialApproved = true;
				this.setHelp(null);
				this.setApprovalDate(null);
				this.setApprovedType(null);
				
				// Actualizo tarea en bandeja de entrada
				inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_PendienteAprobacionCategory);
				inbox.setAssignTo_ID(this.getApprovalUser_ID());
				inbox.setDateAssign(new Timestamp(System.currentTimeMillis()));
				inbox.saveEx();

				// Tracking
				MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				track.setDateTrx(this.getDateDoc());
				track.setAD_User_ID(this.getApprovalUser_ID());
				track.setDescription("Solicitud Aprobada Nivel 1 por " + userApp.getDescription());
				track.setobservaciones(parcialApprovedDescription);
				track.setM_Requisition_ID(this.get_ID());				
				track.saveEx();
				
				MUser userN2 = new MUser(getCtx(), this.getApprovalUser_ID(), null);
				MPurchaseTracking track2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				track2.setDateTrx(this.getDateDoc());
				track2.setAD_User_ID(userApp.get_ID());
				track2.setDescription("Pendiente Aprobación Categoria por " + userN2.getDescription());
				track2.setM_Requisition_ID(this.get_ID());				
				track2.saveEx();

			}
			else{
				// Si requiere aprobacion nivel 2
				if (this.isNeedApprove2()){
					this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionNivel2);
					this.setApprovalUser_ID(section.getApprovalUser(X_UY_POPolicyUser.NIVEL_Nivel2, this.getUY_POPolicyRange_ID()));
					parcialApprovedDescription = this.getHelp();
					this.isParcialApproved = true;
					this.setHelp(null);
					this.setApprovalDate(null);
					this.setApprovedType(null);
					
					// Actualizo tarea en bandeja de entrada
					inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_PendienteAprobacionNivel2);
					inbox.setAssignTo_ID(this.getApprovalUser_ID());
					inbox.setDateAssign(new Timestamp(System.currentTimeMillis()));
					inbox.saveEx();

					// Tracking
					MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
					track.setDateTrx(this.getDateDoc());
					track.setAD_User_ID(this.getApprovalUser_ID());
					track.setDescription("Solicitud Aprobada Nivel 1 por " + userApp.getDescription());
					track.setobservaciones(parcialApprovedDescription);
					track.setM_Requisition_ID(this.get_ID());				
					track.saveEx();
					
					MUser userN2 = new MUser(getCtx(), this.getApprovalUser_ID(), null);
					MPurchaseTracking track2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
					track2.setDateTrx(this.getDateDoc());
					track2.setAD_User_ID(userApp.get_ID());
					track2.setDescription("Pendiente Aprobación Nivel 2 por " + userN2.getDescription());
					track2.setM_Requisition_ID(this.get_ID());				
					track2.saveEx();
					
				}
				else{
					// No requiere aprobacion nivel 2 y tampoco aprobacion de categoria
					this.setApprovalStatus(APPROVALSTATUS_AprobadoNivel1);
					this.setIsApprovedCategory(true);
					this.setIsApproved2(true);
					this.setApprovalDate(new Timestamp(System.currentTimeMillis()));
					this.setApprovedType(APPROVEDTYPE_AUTORIZADO);
				}
			}
		}
		// Aprobacion nivel 2
		else if (modoAprobacion == 2){
			this.setIsApproved2(true);
			this.setNeedApprove2(false);
			this.setApprovalStatus(APPROVALSTATUS_AprobadoNivel2);
			this.setApprovalDate(new Timestamp(System.currentTimeMillis()));
			this.setApprovedType(APPROVEDTYPE_AUTORIZADO);
		}
		// Aprobacion categoria
		else if (modoAprobacion == 3){
			this.setIsApprovedCategory(true);
			// Si requiere aprobacion nivel 2
			if (this.isNeedApprove2()){
				this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionNivel2);
				this.setApprovalUser_ID(section.getApprovalUser(X_UY_POPolicyUser.NIVEL_Nivel2, this.getUY_POPolicyRange_ID()));
				parcialApprovedDescription = this.getHelp();
				this.isParcialApproved = true;
				this.setHelp(null);
				this.setApprovalDate(null);
				this.setApprovedType(null);
				
				// Actualizo tarea en bandeja de entrada
				inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_PendienteAprobacionNivel2);
				inbox.setAssignTo_ID(this.getApprovalUser_ID());
				inbox.setDateAssign(new Timestamp(System.currentTimeMillis()));
				inbox.saveEx();

				// Tracking
				MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				track.setDateTrx(this.getDateDoc());
				track.setAD_User_ID(this.getApprovalUser_ID());
				track.setDescription("Solicitud Aprobada Categoria por " + userApp.getDescription());
				track.setobservaciones(parcialApprovedDescription);
				track.setM_Requisition_ID(this.get_ID());				
				track.saveEx();
				
				MUser userN2 = new MUser(getCtx(), this.getApprovalUser_ID(), null);
				MPurchaseTracking track2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
				track2.setDateTrx(this.getDateDoc());
				track2.setAD_User_ID(userApp.get_ID());
				track2.setDescription("Pendiente Aprobación Nivel 2 por " + userN2.getDescription());
				track2.setM_Requisition_ID(this.get_ID());				
				track2.saveEx();
				
			}
			else{
				this.setIsApproved2(true);
				this.setApprovalStatus(APPROVALSTATUS_AprobadoCategoria);
				this.setApprovalDate(new Timestamp(System.currentTimeMillis()));
				this.setApprovedType(APPROVEDTYPE_AUTORIZADO);
			}
		}
		
		// Si tengo todo aprobado
		if (this.isApproved1() && this.isApproved2() && this.isApprovedCategory()){

			this.setIsApproved(true);
			this.isParcialApproved = false;
			
			// Elimino tarea de bandeja de entrada
			inbox.deleteEx(true);
			
			// Tracking
			MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(this.getDateDoc());
			track.setM_Requisition_ID(this.get_ID());
			track.setAD_User_ID(userApp.get_ID());
			
			if (modoAprobacion == 1){
				track.setDescription("Solicitud Aprobada Nivel 1 por " + userApp.getDescription());
			}
			else if (modoAprobacion == 2){
				track.setDescription("Solicitud Aprobada Nivel 2 por " + userApp.getDescription());
			}
			else if (modoAprobacion == 3){
				track.setDescription("Solicitud Aprobada Categoria por " + userApp.getDescription());
			}
			else{
				track.setDescription("Solicitud Aprobada");	
			}			
			
			track.setobservaciones(this.getHelp());
			track.saveEx();
		}
		// Fin OpenUp. Issue #92
		
		return true;
	}	//	approveIt
	
	/**
	 * 	Reject Approval
	 * 	@return true if success 
	 */
	public boolean rejectIt()
	{
		log.info("rejectIt - " + toString());
		
		// OpenUp. Gabriel Vila. 05/11/2012. Issue #92.
		
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
		
		if ((this.getHelp() == null) || (this.getHelp().equalsIgnoreCase(""))){
			throw new AdempiereException("Debe ingresar Observaciones Autorizador con motivos de rechazo.");
		}
		
		MRInbox inbox = MRInbox.forTableAndRecord(getCtx(), I_M_Requisition.Table_ID, this.get_ID(), get_TrxName());
		MUser responsible = new MUser(getCtx(), Env.getAD_User_ID(Env.getCtx()), null);
		
		String textoRechazo = "";
		
		// Seteo aprobacion segun nivel
		if (this.getApprovalStatus().equalsIgnoreCase(X_M_Requisition.APPROVALSTATUS_PendienteAprobacionNivel1)){
			this.setApprovalStatus(APPROVALSTATUS_RechazadoNivel1);
			inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_NoAprobadoNivel1);
			textoRechazo = " Nivel 1 ";
		}
		else if (this.getApprovalStatus().equalsIgnoreCase(X_M_Requisition.APPROVALSTATUS_PendienteAprobacionNivel2)){
			this.setApprovalStatus(APPROVALSTATUS_RechazadoNivel2);
			inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_NoAprobadoNivel2);
			textoRechazo = " Nivel 2 ";
		}
		else if (this.getApprovalStatus().equalsIgnoreCase(X_M_Requisition.APPROVALSTATUS_PendienteAprobacionCategoria)){
			this.setApprovalStatus(APPROVALSTATUS_RechazadoCategoria);
			inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_NoAprobadoCategoria);
			textoRechazo = " Categoria ";
		}
		else if (this.getApprovalStatus().equalsIgnoreCase(X_M_Requisition.APPROVALSTATUS_PendienteAprobacionCompras)){
			this.setApprovalStatus(APPROVALSTATUS_RechazadoPorCompras);
			inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_NoAprobadoPorStock);
			textoRechazo = " Stock Disponible ";
		}
		
		inbox.setAssignTo_ID(this.getAD_User_ID());
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
		this.setIsApprovedCategory(false);
		this.setIsApproved(false);
		
		// Tracking
		MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(this.getDateDoc());
		track.setAD_User_ID(responsible.getAD_User_ID());
		track.setDescription("Solicitud Rechazada " + textoRechazo + " por " + responsible.getDescription());
		track.setobservaciones(this.getHelp());
		track.setM_Requisition_ID(this.get_ID());		
		track.saveEx();
		
		// Fin OpenUp

		this.setDocAction(DOCACTION_Request);
		
		return true;
	}	//	rejectIt
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		
		if (!this.isApproved()){
			this.m_processMsg = "No se puede completar el Documento ya que el mismo NO esta Aprobado.";
			return DocAction.STATUS_Invalid;
		}
		
		// OpenUp. Gabriel Vila. 05/11/2012. Issue #92
		// Me aseguro al completar de dejar todo aprobado
		this.setIsApproved1(true);
		this.setIsApproved2(true);
		this.setNeedApprove1(false);
		this.setNeedApprove2(false);
		
		// Tracking
		MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(this.getDateDoc());
		track.setAD_User_ID(this.getAD_User_ID());
		track.setDescription("Solicitud Completada");
		track.setM_Requisition_ID(this.get_ID());		
		track.saveEx();
		
		// Elimino de bandeja de entrada
		DB.executeUpdateEx(" delete from uy_r_inbox where ad_table_id =" + I_M_Requisition.Table_ID + " and record_id =" + this.get_ID(), get_TrxName());

		/*
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		*/
		
		// Fin OpenUp. 
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(ACTION_None);
		return DocAction.STATUS_Completed;
	}	//	completeIt
	
	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateDoc(new Timestamp (System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(), true, this);
			if (value != null)
				setDocumentNo(value);
		}
	}

	/**
	 * 	Void Document.
	 * 	Same as Close.
	 * 	@return true if success 
	 */
	public boolean voidIt()
	{
		// OpenUp. Gabriel Vila. 05/11/2012. Issue #71
		// Anulacion segun modelo de openup
		
		return this.voidItOpenUp();
		
		/*		
		log.info("voidIt - " + toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		if (!closeIt())
			return false;
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		return true;
		
		*/
		
		// Fin OpenUp Issue #71
		
	}	//	voidIt
	
	/**
	 * 	Close Document.
	 * 	Cancel not delivered Qunatities
	 * 	@return true if success 
	 */
	public boolean closeIt()
	{
		// OpenUp. Gabriel Vila. 27/03/2012. Issue #1005.
		// Cierre segun modeo OpenUp
		return this.closeItOpenUp();
		
		/*
		log.info("closeIt - " + toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		
		//	Close Not delivered Qty
		MRequisitionLine[] lines = getLines();
		BigDecimal totalLines = Env.ZERO;
		for (int i = 0; i < lines.length; i++)
		{
			MRequisitionLine line = lines[i];
			BigDecimal finalQty = line.getQty();
			if (line.getC_OrderLine_ID() == 0)
				finalQty = Env.ZERO;
			else
			{
				MOrderLine ol = new MOrderLine (getCtx(), line.getC_OrderLine_ID(), get_TrxName());
				finalQty = ol.getQtyOrdered();
			}
			//	final qty is not line qty
			if (finalQty.compareTo(line.getQty()) != 0)
			{
				String description = line.getDescription();
				if (description == null)
					description = "";
				description += " [" + line.getQty() + "]"; 
				line.setDescription(description);
				line.setQty(finalQty);
				line.setLineNetAmt();
				line.saveEx();
			}
			totalLines = totalLines.add (line.getLineNetAmt());
		}
		if (totalLines.compareTo(getTotalLines()) != 0)
		{
			setTotalLines(totalLines);
			saveEx();
		}
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		
		return true;

		*/
		// Fin OpenUp.
		
	}	//	closeIt
	
	/**
	 * 	Reverse Correction
	 * 	@return true if success 
	 */
	public boolean reverseCorrectIt()
	{
		log.info("reverseCorrectIt - " + toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reverseCorrectionIt
	
	/**
	 * 	Reverse Accrual - none
	 * 	@return true if success 
	 */
	public boolean reverseAccrualIt()
	{
		log.info("reverseAccrualIt - " + toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;				
		
		return false;
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate
	 * 	@return true if success 
	 */
	public boolean reActivateIt()
	{
		log.info("reActivateIt - " + toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

	//	setProcessed(false);
		if (! reverseCorrectIt())
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return true;
	}	//	reActivateIt
	
	/*************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	public String getSummary()
	{
		// OpenUp. Gabriel Vila. 06/11/2012. Issue #71.
		// Mejoro texto que describe esta solicitud de compra
		// Comento y sustituyo.
		
		/*
		 
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		//	 - User
		sb.append(" - ").append(getUserName());
		//	: Total Lines = 123.00 (#1)
		sb.append(": ").
			append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
			.append(" (#").append(getLines().length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
		
		*/

		StringBuffer sb = new StringBuffer();
		
		if (this.getUY_POSection_ID() > 0){
			MPOSection section = new MPOSection(getCtx(), this.getUY_POSection_ID(), null);
			sb.append(Msg.translate(getCtx(),"UY_POSection_ID")).append(": ").append(section.getName()).append(" - ");
		}
		sb.append(Msg.getElement(getCtx(), "DocumentNo")).append(": ").append(this.getDocumentNo()).append(" - ");
		sb.append(Msg.translate(getCtx(),"AD_User_ID")).append(": ").append(getUserName()).append(" - ");
		sb.append("Total = ").append(getTotalLines().setScale(2, RoundingMode.HALF_UP))
			.append(" (Lineas ").append(getLines().length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();

		// Fin OpenUp.
		
	}	//	getSummary
	
	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg
	
	/**
	 * 	Get Document Owner
	 *	@return AD_User_ID
	 */
	public int getDoc_User_ID()
	{
		return getAD_User_ID();
	}
	
	/**
	 * 	Get Document Currency
	 *	@return C_Currency_ID
	 */
	public int getC_Currency_ID()
	{
		MPriceList pl = MPriceList.get(getCtx(), getM_PriceList_ID(), get_TrxName());
		return pl.getC_Currency_ID();
	}

	/**
	 * 	Get Document Approval Amount
	 *	@return amount
	 */
	public BigDecimal getApprovalAmt()
	{
		return getTotalLines();
	}
	
	/**
	 * 	Get User Name
	 *	@return user name
	 */
	public String getUserName()
	{
		return MUser.get(getCtx(), getAD_User_ID()).getName();
	}	//	getUserName

	/**
	 * 	Document Status is Complete or Closed
	 *	@return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds) 
			|| DOCSTATUS_Closed.equals(ds)
			|| DOCSTATUS_Reversed.equals(ds);
	}	//	isComplete

	
	/**
	 * Descarto precios e importes en requisicion segun modelo OpenUp.
	 * OpenUp Ltda. Issue # 1005
	 * @author Hp - 27/03/2012
	 * @see http://1.1.20.123:86/eventum/view.php?id=1005
	 * @return
	 */
	private String prepareItOpenUp()
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		MRequisitionLine[] lines = getLines();
		
		//	Invalid
		if (getAD_User_ID() == 0)
		{
			return DocAction.STATUS_Invalid;
		}
		
		if(lines.length == 0)
		{
			throw new AdempiereException("@NoLines@");
		}
		
		//	Std Period open?
		MPeriod.testPeriodOpen(getCtx(), getDateDoc(), MDocType.DOCBASETYPE_PurchaseRequisition, getAD_Org_ID());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}	//	prepareIt

	/**
	 * Cierre de requisicion segun modelo de OpenUp
	 * OpenUp Ltda. Issue # 1005
	 * @author Hp - 27/03/2012
	 * @see http://1.1.20.123:86/eventum/view.php?id=1005
	 * @return
	 */
	private boolean closeItOpenUp()
	{
		log.info("closeIt - " + toString());

		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		
		//	Close Not delivered Qty
		MRequisitionLine[] lines = getLines();
		for (int i = 0; i < lines.length; i++)
		{
			MRequisitionLine line = lines[i];
			BigDecimal finalQty = line.getQty();
			if (line.getC_OrderLine_ID() == 0)
				finalQty = Env.ZERO;
			else
			{
				MOrderLine ol = new MOrderLine (getCtx(), line.getC_OrderLine_ID(), get_TrxName());
				finalQty = ol.getQtyOrdered();
			}
			//	final qty is not line qty
			if (finalQty.compareTo(line.getQty()) != 0)
			{
				String description = line.getDescription();
				if (description == null)
					description = "";
				description += " [" + line.getQty() + "]"; 
				line.setDescription(description);
				line.setQty(finalQty);
				line.saveEx();
			}
		}

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	closeIt

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/***
	 * OpenUp. Gabriel Vila. 04/11/2012. Issue #92.
	 * Implemento metodo que obtiene responsable dinamico de wf.
	 */
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

	/***
	 * Metodo que se ejecuta en la accion Solicitar.
	 * OpenUp Ltda. Issue #92 
	 * @author Gabriel Vila - 05/11/2012
	 * @see
	 * @return
	 */
	public boolean requestIt() {
		
		try{
		
			MRequisitionLine[] lines = this.getLines();
			if (lines.length <= 0){
				throw new AdempiereException("El documento no tiene lineas.");
			}
			
			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			
			// Obtengo y me seteo el rango de politica de compra segun mi total
			MPOSection section = new MPOSection(getCtx(), this.getUY_POSection_ID(), null);
			MPOPolicyRange rango = section.getPolicyRange(this.getDateDoc(), this.getTotalLines(), 
								schema.getC_Currency_ID(), null);
			if (rango == null){
				throw new AdempiereException("No se pudo obtener rango de politica de compra segun Total de la Solicitud.");
			}			
			this.setUY_POPolicyRange_ID(rango.get_ID());

			// Valido que no haya conflicto con categorias especiales en las lineas
			String mensaje = this.validatePolicyCategories(section);
			if (mensaje != null){
				throw new AdempiereException(mensaje);
			}
					
			// Seteo si es necesaria una segunda aprobacion para esta solicitud
			this.setNeedApprove2(section.needSecondApprove(this.getUY_POPolicyRange_ID()));
			
			// Actualizo estado de solicitud
			this.setDateRequested(new Timestamp(System.currentTimeMillis()));

			// Recorro lineas de la solicitud para obtener disponibilidad de stock
			// En caso que haya al menos una unidad disponible de un producto stockeable, 
			// la solicitud tiene que tener primero la autorizacion del encargado de compras por stock disponible.
			boolean needStockApprovement = false;
			for (int i = 0; i < lines.length; i++){
				MProduct prod = (MProduct)lines[i].getM_Product();
				if (prod.isStocked()){
					BigDecimal qtyAvailable = MStockTransaction.getQtyAvailable(this.getM_Warehouse_ID(), 0, prod.get_ID(), 0, 0, null, null);
					if (qtyAvailable == null) qtyAvailable = Env.ZERO;
					lines[i].setQtyAvailable(qtyAvailable);
					if (qtyAvailable.compareTo(Env.ZERO) > 0){
						// Tengo al menos una unidad disponible de este producto.
						needStockApprovement = true;
					}
				}
			}
			
			// Actualizo informacion de estado de aprobacion
			if (needStockApprovement){
				this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionCompras);
				this.setApprovalUser_ID(this.getPOUser_ID());
				this.setApprovalSeqNo(0);
			}
			else{
				this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionNivel1);
				this.setApprovalUser_ID(section.getApprovalUser(X_UY_POPolicyUser.NIVEL_Nivel1, this.getUY_POPolicyRange_ID()));
				this.setApprovalSeqNo(section.userAppSeqNo);				
			}

			this.setHelp(null);
			this.setApprovalDate(null);
			this.setApprovedType(null);
			
			if (this.isNeedApprove2())
				this.setnivel(2);
			else
				this.setnivel(1);
				
			// Estado documento
			this.setDocAction(DOCACTION_Approve);
			
			MUser userApp = new MUser(getCtx(), this.getApprovalUser_ID(), null);
			
			// Tracking
			MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(this.getDateDoc());
			track.setAD_User_ID(this.getAD_User_ID());
			track.setDescription("Solicitud creada");
			track.setobservaciones(this.getDescription());
			track.setM_Requisition_ID(this.get_ID());
			track.saveEx();

			MPurchaseTracking track2 = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track2.setDateTrx(this.getDateDoc());
			track2.setAD_User_ID(this.getAD_User_ID());
			
			if (needStockApprovement){
				track2.setDescription("Pendiente Aprobación Stock por " + userApp.getDescription());
			}
			else{
				track2.setDescription("Pendiente Aprobación Nivel 1 por " + userApp.getDescription());	
			}
			
			track2.setM_Requisition_ID(this.get_ID());
			track2.saveEx();

			// Elimino de bandeja de entrada por si es una resolicitud
			DB.executeUpdateEx(" delete from uy_r_inbox where ad_table_id =" + I_M_Requisition.Table_ID + " and record_id =" + this.get_ID(), get_TrxName());
			
			// Ingreso en bandeja de entrada
			Timestamp today = new Timestamp(System.currentTimeMillis());
			MRType rType = MRType.forValue(getCtx(), "compras", null);
			if ((rType == null) || (rType.get_ID() <= 0)){
				throw new AdempiereException("Falta parametrizar el Tipo de Incidencia : compras");
			}
			MRCause cause = MRCause.forTypeAndValue(getCtx(), rType.get_ID(), "solcompra", null);
			if ((cause == null) || (cause.get_ID() <= 0)){
				throw new AdempiereException("Falta parametrizar el Tema de Inciencia : solcompra");
			}
			
			MRInbox inbox = new MRInbox(getCtx(),0, get_TrxName());						
			inbox.setUY_R_Type_ID(rType.get_ID());
			inbox.setUY_R_Cause_ID(cause.get_ID());
			inbox.setSign();
			inbox.setDateTrx(today);
			inbox.setDateAssign(today);
			inbox.setAD_User_ID(this.getAD_User_ID());
			if (needStockApprovement){
				inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_PendienteAprobacionStock);
			}
			else{
				inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_PendienteAprobacionNivel1);
			}
			
			inbox.setPriority(cause.getPriorityBase());
			inbox.setPriorityManual(this.getPriorityRule());
			inbox.setAssignTo_ID(this.getApprovalUser_ID());
			inbox.setDueDate(TimeUtil.addDays(inbox.getDateTrx(), cause.getDeadLine()));
			inbox.setAD_Table_ID(I_M_Requisition.Table_ID);
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
			String subject = "Aprobación Pendiente de Solicitud de Orden de Compra.";
			String message = "Estimado/a: " + user.getDescription() + "\n\n";
			message +="La Solicitud de Compra Número : " + this.getDocumentNo() + " esta en espera de su Aprobación.\n\n";
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

	/***
	 * Valida que no haya conflicto entre las categorias de las lineas de esta solicitud y las categorias especiales
	 * de la politica de compra asociada al sector de compra de esta solicitud.
	 * OpenUp Ltda. Issue #92 
	 * @author Gabriel Vila - 17/02/2014
	 * @see
	 * @return
	 */
	private String validatePolicyCategories(MPOSection section) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		String mensaje = null;
		int mProductCategoryID = 0;
		
		try {
			
			// Obtengo politica del sector asociado a esta solicitud
			MPOPolicy policy = (MPOPolicy)section.getUY_POPolicy();

			sql = " select distinct m_product_category_id " +
				  " from m_requisitionline" +
				  " where m_requisition_id =" + this.get_ID() +
				  " and m_product_category_id in " +
				  " (select m_product_category_id from uy_popolicycategory " +
				  " where uy_popolicy_id =" + policy.get_ID() + ") ";
				   
				
			pstmt = DB.prepareStatement(sql.toString(), null);

			rs = pstmt.executeQuery();

			boolean firstRow = true;
			
			while (rs.next()) {
				if (firstRow){
					mProductCategoryID = rs.getInt(1);
					firstRow = false;
				}
				else{
					mensaje = "No es posible generar una solicitud con lineas que tengan mas de una Categoria Especial.";
					return mensaje;
				}
			}

			if (mProductCategoryID > 0){
				sql = " select max(m_requisitionline_id) " +
						  " from m_requisitionline " +
						  " where m_requisition_id =" + this.get_ID() +
						  " and m_product_category_id !=" + mProductCategoryID;
				int auxID = DB.getSQLValueEx(get_TrxName(), sql);
				if (auxID > 0){
					MProductCategory prodCat = new MProductCategory(getCtx(), mProductCategoryID, null);
					mensaje = "No es posible tener lineas con categorias distintas a la categoria especial : " + prodCat.getName();
					return mensaje;
				}
				
				// Todo bien, seteo categoria especial en el cabezal de la solicitud para procesar aprobaciones
				sql = " select uy_popolicycategory_id " +
					  " from uy_popolicycategory " +
					  " where uy_popolicy_id =" + policy.get_ID() +
					  " and m_product_category_id =" + mProductCategoryID;
				int uyPOPolicyCategoryID = DB.getSQLValueEx(null, sql);
				if (uyPOPolicyCategoryID > 0){
					MPOPolicyCategory polCat = new MPOPolicyCategory(getCtx(), uyPOPolicyCategoryID, null);
					this.setUY_POPolicyCategory_ID(polCat.get_ID());
					this.setCategoryUser_ID(polCat.getAD_User_ID());
				}
			}
			
		} 
		catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return mensaje;
	}

	/***
	 * Anulacion de solicitud de compra segun modelo de OpenUp.
	 * OpenUp Ltda. Issue #71 
	 * @author Gabriel Vila - 05/11/2012
	 * @see
	 * @return
	 */
	public boolean voidItOpenUp()
	{

		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setPosted(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);
		
		return true;
	}	//	voidIt

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

	
}	//	MRequisition
