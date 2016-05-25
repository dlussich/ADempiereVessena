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
package org.compiere.process;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.apps.ADialog;
import org.compiere.db.CConnection;
import org.compiere.interfaces.Server;
import org.compiere.model.I_UY_Confirmorderhdr;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MBankStatement;
import org.compiere.model.MCash;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MColumn;
import org.compiere.model.MInOut;
import org.compiere.model.MInventory;
import org.compiere.model.MInvoice;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalBatch;
import org.compiere.model.MMovement;
import org.compiere.model.MOrder;
import org.compiere.model.MPayment;
import org.compiere.model.MRequisition;
import org.compiere.model.MRole;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_HR_Process;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.openup.model.*;


/**
 *	Document Action Engine
 *	
 *  @author Jorg Janke 
 *  @author Karsten Thiemann FR [ 1782412 ]
 *  @author victor.perez@e-evolution.com www.e-evolution.com FR [ 1866214 ]  http://sourceforge.net/tracker/index.php?func=detail&aid=1866214&group_id=176962&atid=879335
 *  @version $Id: DocumentEngine.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 */
public class DocumentEngine implements DocAction
{
	/**
	 * 	Doc Engine (Drafted)
	 * 	@param po document
	 */
	public DocumentEngine (DocAction po)
	{
		this (po, STATUS_Drafted);
	}	//	DocActionEngine
	
	/**
	 * 	Doc Engine
	 * 	@param po document
	 * 	@param docStatus initial document status
	 */
	public DocumentEngine (DocAction po, String docStatus)
	{
		m_document = po;
		if (docStatus != null)
			m_status = docStatus;
	}	//	DocActionEngine

	/** Persistent Document 	*/
	private DocAction	m_document;
	/** Document Status			*/
	private String		m_status = STATUS_Drafted;
	/**	Process Message 		*/
	private String		m_message = null;
	/** Actual Doc Action		*/
	private String		m_action = null;
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(DocumentEngine.class);
	
	/**
	 * 	Get Doc Status
	 *	@return document status
	 */
	public String getDocStatus()
	{
		return m_status;
	}	//	getDocStatus

	/**
	 * 	Set Doc Status - Ignored
	 *	@param ignored Status is not set directly
	 * @see org.compiere.process.DocAction#setDocStatus(String)
	 */
	public void setDocStatus(String ignored)
	{
	}	//	setDocStatus

	/**
	 * 	Document is Drafted
	 *	@return true if drafted
	 */
	public boolean isDrafted()
	{
		return STATUS_Drafted.equals(m_status);
	}	//	isDrafted
	
	/**
	 * 	Document is Invalid
	 *	@return true if Invalid
	 */
	public boolean isInvalid()
	{
		return STATUS_Invalid.equals(m_status);
	}	//	isInvalid
	
	/**
	 * 	Document is In Progress
	 *	@return true if In Progress
	 */
	public boolean isInProgress()
	{
		return STATUS_InProgress.equals(m_status);
	}	//	isInProgress
	
	/**
	 * 	Document is Approved
	 *	@return true if Approved
	 */
	public boolean isApproved()
	{
		return STATUS_Approved.equals(m_status);
	}	//	isApproved
	
	/**
	 * 	Document is Not Approved
	 *	@return true if Not Approved
	 */
	public boolean isNotApproved()
	{
		return STATUS_NotApproved.equals(m_status);
	}	//	isNotApproved
	
	/**
	 * 	Document is Waiting Payment or Confirmation
	 *	@return true if Waiting Payment
	 */
	public boolean isWaiting()
	{
		return STATUS_WaitingPayment.equals(m_status)
			|| STATUS_WaitingConfirmation.equals(m_status);
	}	//	isWaitingPayment
	
	/**
	 * 	Document is Completed
	 *	@return true if Completed
	 */
	public boolean isCompleted()
	{
		return STATUS_Completed.equals(m_status);
	}	//	isCompleted
	
	/**
	 * 	Document is Reversed
	 *	@return true if Reversed
	 */
	public boolean isReversed()
	{
		return STATUS_Reversed.equals(m_status);
	}	//	isReversed
	
	/**
	 * 	Document is Closed
	 *	@return true if Closed
	 */
	public boolean isClosed()
	{
		return STATUS_Closed.equals(m_status);
	}	//	isClosed
	
	/**
	 * 	Document is Voided
	 *	@return true if Voided
	 */
	public boolean isVoided()
	{
		return STATUS_Voided.equals(m_status);
	}	//	isVoided
	
	/**
	 * 	Document Status is Unknown
	 *	@return true if unknown
	 */
	public boolean isUnknown()
	{
		return STATUS_Unknown.equals(m_status) || 
			!(isDrafted() || isInvalid() || isInProgress() || isNotApproved()
				|| isApproved() || isWaiting() || isCompleted()
				|| isReversed() || isClosed() || isVoided() );
	}	//	isUnknown

	
	/**
	 * 	Document is Requested, OpenUP FL 18/02/2011, issue #301
	 *	@return true if requested
	 */
	public boolean isRequested()
	{
		return STATUS_Requested.equals(m_status);
	}	//	isRequested
	
	/**
	 * 	Document is Asigned, OpenUP FL 18/02/2011, issue #301
	 *	@return true if asigned
	 */
	public boolean isAsigned()
	{
		return STATUS_Asigned.equals(m_status);
	}	//	isAsigned

	/**
	 * 	Document is Applied, OpenUp GV 25/05/2012, issue #1021
	 *	@return true if applied
	 */
	public boolean isApplied()
	{
		return STATUS_Applied.equals(m_status);
	}	//	isAsigned

	
	/**
	 * 	Document is recived, OpenUP FL 18/02/2011, issue #301
	 *	@return true if recived
	 */
	public boolean isRecived()
	{
		return STATUS_Recived.equals(m_status);
	}	//	isRecived
	
	/**
	 * 	Document is picked, OpenUP FL 18/02/2011, issue #301
	 *	@return true if picked
	 */
	public boolean isPicked()
	{
		return STATUS_Picked.equals(m_status);
	}	//	isPicked
	
	/**
	 * 	Process actual document.
	 * 	Checks if user (document) action is valid and then process action 
	 * 	Calls the individual actions which call the document action
	 *	@param processAction document action based on workflow
	 *	@param docAction document action based on document
	 *	@return true if performed
	 */
	public boolean processIt (String processAction, String docAction)
	{
		m_message = null;
		m_action = null;
		//	Std User Workflows - see MWFNodeNext.isValidFor
		
		if (isValidAction(processAction))	//	WF Selection first
			m_action = processAction;
		//
		else if (isValidAction(docAction))	//	User Selection second
			m_action = docAction;
		//	Nothing to do
		else if (processAction.equals(ACTION_None)
			|| docAction.equals(ACTION_None))
		{
			if (m_document != null)
				m_document.get_Logger().info ("**** No Action (Prc=" + processAction + "/Doc=" + docAction + ") " + m_document);
			return true;	
		}
		else
		{
			throw new IllegalStateException("Status=" + getDocStatus() 
				+ " - Invalid Actions: Process="  + processAction + ", Doc=" + docAction);
		}
		if (m_document != null)
			m_document.get_Logger().info ("**** Action=" + m_action + " (Prc=" + processAction + "/Doc=" + docAction + ") " + m_document);
		boolean success = processIt (m_action);
		if (m_document != null)
			m_document.get_Logger().fine("**** Action=" + m_action + " - Success=" + success);
		return success;
	}	//	process
	
	/**
	 * 	Process actual document - do not call directly.
	 * 	Calls the individual actions which call the document action
	 *	@param action document action
	 *	@return true if performed
	 */
	public boolean processIt (String action)
	{
		m_message = null;
		m_action = action;
		//
		if (ACTION_Unlock.equals(m_action))
			return unlockIt();
		if (ACTION_Invalidate.equals(m_action))
			return invalidateIt();
		if (ACTION_Prepare.equals(m_action))
			return STATUS_InProgress.equals(prepareIt());
		if (ACTION_Approve.equals(m_action))
			return approveIt();
		if (ACTION_Reject.equals(m_action))
			return rejectIt();
		if (ACTION_Complete.equals(m_action) || ACTION_WaitComplete.equals(m_action))
		{
			String status = null;
			if (isDrafted() || isInvalid())		//	prepare if not prepared yet
			{
				status = prepareIt();
				if (!STATUS_InProgress.equals(status))
					return false;
			}
			status = completeIt();
			boolean ok =   STATUS_Completed.equals(status)
						|| STATUS_InProgress.equals(status)
						|| STATUS_WaitingPayment.equals(status)
						|| STATUS_WaitingConfirmation.equals(status);
			if (m_document != null && ok)
			{
				// PostProcess documents when invoice or inout (this is to postprocess the generated MatchPO and MatchInv if any)
				ArrayList<PO> docsPostProcess = new ArrayList<PO>();;
				if (m_document instanceof MInvoice || m_document instanceof MInOut) {
					if (m_document instanceof MInvoice) {
						docsPostProcess  = ((MInvoice) m_document).getDocsPostProcess();
					}
					if (m_document instanceof MInOut) {
						docsPostProcess  = ((MInOut) m_document).getDocsPostProcess();
					}
				}
				if (m_document instanceof PO && docsPostProcess.size() > 0) {
					// Process (this is to update the ProcessedOn flag with a timestamp after the original document)
					for (PO docafter : docsPostProcess) {
						docafter.setProcessedOn("Processed", true, false);
						docafter.saveEx();
					}
				}
				
				if (STATUS_Completed.equals(status) && MClient.isClientAccountingImmediate())
				{
					m_document.saveEx();
					postIt();
					
					if (m_document instanceof PO && docsPostProcess.size() > 0) {
						for (PO docafter : docsPostProcess) {
							@SuppressWarnings("unused")
							String ignoreError = DocumentEngine.postImmediate(docafter.getCtx(), docafter.getAD_Client_ID(), docafter.get_Table_ID(), docafter.get_ID(), true, docafter.get_TrxName());
						}
					}
				}

			}
			return ok;
		}
		if (ACTION_ReActivate.equals(m_action))
			return reActivateIt();
		if (ACTION_Reverse_Accrual.equals(m_action))
			return reverseAccrualIt();
		if (ACTION_Reverse_Correct.equals(m_action)){
			
			// Openup. Gabriel Vila. 11/04/2011. Issue #385.
			// Si hubo error controlado debo mostrarlo al usuario
			if (!reverseCorrectIt()){
				ADialog.error(0, null, m_document.getProcessMsg());
			}
			else {
				return true;
			}
			//return reverseCorrectIt();
			// Fin OpenUp
		}
			
		if (ACTION_Close.equals(m_action))
			return closeIt();
		if (ACTION_Void.equals(m_action)){

			// Openup. Gabriel Vila. 11/04/2011. Issue #385.
			// Si hubo error controlado debo mostrarlo al usuario
			if (!voidIt()){
				ADialog.error(0, null, m_document.getProcessMsg());
			}
			else
				return true;
			//return voidIt();
			// Fin OpenUp.
		}
			
		if (ACTION_Post.equals(m_action))
			return postIt();

		// OpenUP FL, 18/02/2011, issue #301, process a request action
		if (ACTION_Request.equals(m_action)) {
			return requestIt();
		}

		// OpenUP FL, 18/02/2011, issue #301, process a asign action
		if (ACTION_Asign.equals(m_action)) {
			return asignIt();
		}

		// OpenUp GV, 25/05/2012, issue #1021, process an apply action
		if (ACTION_Apply.equals(m_action)) {
			return applyIt();
		}
		
		// OpenUP FL, 18/02/2011, issue #301, process a recive action
		if (ACTION_Recive.equals(m_action)) {
			return reciveIt();
		}

		// OpenUP FL, 18/02/2011, issue #301, process a pick action
		if (ACTION_Pick.equals(m_action)) {
			return pickIt();
		}

		return false;
	}	//	processDocument
	
	/**
	 * 	Unlock Document.
	 * 	Status: Drafted
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#unlockIt()
	 */
	public boolean unlockIt()
	{
		if (!isValidAction(ACTION_Unlock))
			return false;
		if (m_document != null)
		{
			if (m_document.unlockIt())
			{
				m_status = STATUS_Drafted;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Drafted;
		return true;
	}	//	unlockIt
	
	/**
	 * 	Invalidate Document.
	 * 	Status: Invalid
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#invalidateIt()
	 */
	public boolean invalidateIt()
	{
		if (!isValidAction(ACTION_Invalidate))
			return false;
		if (m_document != null)
		{
			if (m_document.invalidateIt())
			{
				m_status = STATUS_Invalid;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Invalid;
		return true;
	}	//	invalidateIt
	
	/**
	 *	Process Document.
	 * 	Status is set by process
	 * 	@return new status (In Progress or Invalid) 
	 * 	@see org.compiere.process.DocAction#prepareIt()
	 */
	public String prepareIt()
	{
		if (!isValidAction(ACTION_Prepare))
			return m_status;
		if (m_document != null)
		{
			m_status = m_document.prepareIt();
			m_document.setDocStatus(m_status);
		}
		return m_status;
	}	//	processIt

	/**
	 * 	Approve Document.
	 * 	Status: Approved
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#approveIt()
	 */
	public boolean  approveIt()
	{
		if (!isValidAction(ACTION_Approve))
			return false;
		if (m_document != null)
		{
			if (m_document.approveIt())
			{				
				// OpenUp. Gabriel Vila. 07/12/2012. Issue #154.
				// Si el documento es intancia de un workflow dinamico, pregunto si fue aprobado parcialmente, 
				// o sea si tiene mas de un nivel de aprobacion, y este no es el ultimo nivel.
				// En este caso no debo dejar el estado de documento como aprobado.
				if (m_document instanceof IDynamicWF){
					if (!((IDynamicWF)m_document).IsParcialApproved()){
						m_status = STATUS_Approved;
						m_document.setDocStatus(m_status);
						
						// OpenUp. Gabriel Vila. 29/01/2014. Issue #1840
						// Si el documento implementa interface de wf dinamico, llamo al metodo que posibilita completar automaticamente por sistema.
						((IDynamicWF)m_document).processAutomaticComplete();
						// Fin OpenUp. Issue #1840.
					}
				}
				else{
					m_status = STATUS_Approved;
					m_document.setDocStatus(m_status);
				}
				m_document.saveEx();
				// Fin OpenUp. 
				
				return true;
			}
			return false;
		}
		m_status = STATUS_Approved;
		return true;
	}	//	approveIt
	
	/**
	 * 	Reject Approval.
	 * 	Status: Not Approved
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#rejectIt()
	 */
	public boolean rejectIt()
	{
		if (!isValidAction(ACTION_Reject))
			return false;
		if (m_document != null)
		{
			if (m_document.rejectIt())
			{
				m_status = STATUS_NotApproved;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_NotApproved;
		return true;
	}	//	rejectIt
	
	/**
	 * 	Complete Document.
	 * 	Status is set by process
	 * 	@return new document status (Complete, In Progress, Invalid, Waiting ..)
	 * 	@see org.compiere.process.DocAction#completeIt()
	 */
	public String completeIt()
	{
		if (!isValidAction(ACTION_Complete))
			return m_status;
		if (m_document != null)
		{
			m_status = m_document.completeIt();
			m_document.setDocStatus(m_status);
		}
		return m_status;
	}	//	completeIt
	
	/**
	 * 	Post Document
	 * 	Does not change status
	 * 	@return true if success 
	 */
	public boolean postIt()
	{
		if (!isValidAction(ACTION_Post) 
			|| m_document == null)
			return false;

		String error = DocumentEngine.postImmediate(Env.getCtx(), m_document.getAD_Client_ID(), m_document.get_Table_ID(), m_document.get_ID(), true, m_document.get_TrxName());
		return (error == null);
	}	//	postIt
	
	/**
	 * 	Void Document.
	 * 	Status: Voided
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#voidIt()
	 */
	public boolean voidIt()
	{
		if (!isValidAction(ACTION_Void))
			return false;
		if (m_document != null)
		{
			if (m_document.voidIt())
			{
				m_status = STATUS_Voided;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Voided;
		return true;
	}	//	voidIt
	
	/**
	 * 	Close Document.
	 * 	Status: Closed
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#closeIt()
	 */
	public boolean closeIt()
	{
		if (m_document != null 	//	orders can be closed any time
			&& m_document.get_Table_ID() == MOrder.Table_ID)
			;
		else if (!isValidAction(ACTION_Close))
			return false;
		if (m_document != null)
		{
			if (m_document.closeIt())
			{
				m_status = STATUS_Closed;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Closed;
		return true;
	}	//	closeIt
	
	/**
	 * 	Reverse Correct Document.
	 * 	Status: Reversed
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	public boolean reverseCorrectIt()
	{
		if (!isValidAction(ACTION_Reverse_Correct))
			return false;
		if (m_document != null)
		{
			if (m_document.reverseCorrectIt())
			{
				m_status = STATUS_Reversed;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Reversed;
		return true;
	}	//	reverseCorrectIt
	
	/**
	 * 	Reverse Accrual Document.
	 * 	Status: Reversed
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	public boolean reverseAccrualIt()
	{
		if (!isValidAction(ACTION_Reverse_Accrual))
			return false;
		if (m_document != null)
		{
			if (m_document.reverseAccrualIt())
			{
				m_status = STATUS_Reversed;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_Reversed;
		return true;
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate Document.
	 * 	Status: In Progress
	 * 	@return true if success 
	 * 	@see org.compiere.process.DocAction#reActivateIt()
	 */
	public boolean reActivateIt()
	{
		if (!isValidAction(ACTION_ReActivate))
			return false;
		if (m_document != null)
		{
			if (m_document.reActivateIt())
			{
				m_status = STATUS_InProgress;
				m_document.setDocStatus(m_status);
				return true;
			}
			return false;
		}
		m_status = STATUS_InProgress;
		return true;
	}	//	reActivateIt


	/**
	 * 	Request Document. OpenUP FL 18/02/2011, isseue #301
	 * 	Status: Requested
	 * 	@return true if success 
	 * 	TODO: this should be implemented as a method interfase in DocAction, but it will generate errors
	 */
	public boolean requestIt()
	{
		if (!isValidAction(ACTION_Request))
			return false;
		if (m_document != null) 
		{

			// Test request for Shipments, to avoid changes to DocAction signature required class for this status should be tested
			if (m_document instanceof MInOut) {
				MInOut m_inout= (MInOut) m_document;
				if (!m_inout.requestIt()) {
					return(false);
				}
			}
			
			// Test request for Invoice, to avoid changes to DocAction signature required class for this status should be tested
			if (m_document instanceof MInvoice) {
				MInvoice m_invoice= (MInvoice) m_document;
				if (!m_invoice.requestIt()) {
					return(false);
				}
			}

			// OpenUp. Gabriel Vila. 05/11/2012. Issue #92.
			// Request en Solicitud de Compra
			if (m_document instanceof MRequisition) {
				MRequisition req = (MRequisition) m_document;
				if (!req.requestIt()) {
					return false;
				}
			}
			
			
			// OpenUp. Gabriel Vila. 05/11/2012. Issue #98.
			// Request en Cotizacion a proveedor
			if (m_document instanceof MQuoteVendor) {
				MQuoteVendor quote = (MQuoteVendor) m_document;
				if (!quote.requestIt()) {
					return false;
				}
			}

			// OpenUp. Gabriel Vila. 22/02/2013. Issue #381
			// Request en Orden de Compra
			if (m_document instanceof MOrder) {
				MOrder order = (MOrder) m_document;
				if (!order.requestIt()) {
					return false;
				}
				order.saveEx();
			}

			// OpenUp. Gabriel Vila. 22/02/2013. Issue #381
			// Request en Orden de Compra Parcial por Sector
			if (m_document instanceof MOrderSection) {
				MOrderSection ordersect = (MOrderSection) m_document;
				if (!ordersect.requestIt()) {
					return false;
				}
				ordersect.saveEx();
			}
			
			// OpenUp. Nicolas Sarlabos. 17/10/2013. Issue #1418
			// Request en Salida y Rendicion de Fondo Fijo
			if (m_document instanceof MFFCashOut) {
				MFFCashOut cash = (MFFCashOut) m_document;
				if (!cash.requestIt()) {
					return false;
				}
				cash.saveEx();
			}

			// OpenUp. Gabriel Vila. 09/08/2014. Issue #1618
			// Request en Gestion de Falla de Transporte
			if (m_document instanceof MTRFaultManage) {
				MTRFaultManage model = (MTRFaultManage) m_document;
				if (!model.requestIt()) {
					return false;
				}
				model.saveEx();
			}

			// OpenUp. Gabriel Vila. 09/08/2014. Issue #4168
			// Request en Registro de Usuario
			if (m_document instanceof MUserReq) {
				MUserReq model = (MUserReq) m_document;
				if (!model.requestIt()) {
					return false;
				}
				model.saveEx();
			}

			// OpenUp. Gabriel Vila. 09/08/2014. Issue #4173, #4174
			// Request en Registro de Transacciones Bursatile
			if (m_document instanceof MBGTransaction) {
				MBGTransaction model = (MBGTransaction) m_document;
				if (!model.requestIt()) {
					return false;
				}
				model.saveEx();
			}

			// OpenUp. Gabriel Vila. 09/08/2014. Issue #4197
			// Request en Registro de Subastas
			if (m_document instanceof MBGAutionReq) {
				MBGAutionReq model = (MBGAutionReq) m_document;
				if (!model.requestIt()) {
					return false;
				}
				model.saveEx();
			}
			
			// OpenUp. Nicolas Sarlabos. 28/07/2015. Issue #4449
			// Request en Remesa Efectivo/Valores
			if (m_document instanceof MCashRemittance) {
				MCashRemittance model = (MCashRemittance) m_document;
				if (!model.requestIt()) {
					return false;
				}
				model.saveEx();
			}
			
			m_status = STATUS_Requested;
			m_document.setDocStatus(m_status);
			
			
			// OpenUp. Gabriel Vila. 29/01/2014. Issue #1840
			// Si el documento implementa interface de wf dinamico, llamo al metodo que posibilita una aprobacion automatica por sistema.
			if (m_document instanceof IDynamicWF) {
				((IDynamicWF)m_document).processAutomaticApproval();
			}
			// Fin OpenUp. Issue #1840.
			
			
			m_document.saveEx();
			
			return true;
		}
		m_status = STATUS_Drafted;
		return true;
	}	//	requestIt
	
	/**
	 * 	Asign a Document. OpenUP FL 18/02/2011, isseue #301
	 * 	Status: Requested
	 * 	@return true if success 
	 * 	TODO: this should be implemented as a method interfase in DocAction, but it will generate errors
	 */
	public boolean asignIt()
	{
		if (!isValidAction(ACTION_Asign))
			return false;
		if (m_document != null) 
		{

			// Test request for Shipments, to avoid changes to DocAction signature required class for this status should be tested
			if (m_document instanceof MInOut) {
				MInOut m_inout= (MInOut) m_document;
				if (!m_inout.asignIt()) {
					return(false);
				}
			}
			
			m_status = STATUS_Asigned;
			m_document.setDocStatus(m_status);
			return true;
		}
		m_status = STATUS_Drafted;
		return true;
	}	//	asignIt

	/**
	 * OpenUp. Gabriel Vila. 25/05/2012. Issue #1021.
	 * Aplicacion de documentos.
	 */
	public boolean applyIt()
	{
		if (!isValidAction(ACTION_Apply))
			return false;
		
		if (m_document != null)
		{
			if (m_document.applyIt())
			{
				m_status = STATUS_Applied;

				// OpenUp. Gabriel Vila. 05/06/2013. Issue #285.
				// En algunos documentos cuando aplico, se requiere ademas ya dejarlo completo.
				// Agrego el IF para preguntar.
				if (!m_document.getDocStatus().equalsIgnoreCase(STATUS_Completed)){
					m_document.setDocStatus(m_status);	
				}
				// Fin OpenUp. 
		
				return true;
				
			}
			return false;
		}
		m_status = STATUS_Applied;
		return true;
	}	//	asignIt


	/**
	 * 	Recive Document. OpenUP FL 18/02/2011, isseue #301
	 * 	Status: Requested
	 * 	@return true if success 
	 * 	TODO: this should be implemented as a method interfase in DocAction, but it will generate errors
	 */
	public boolean reciveIt()
	{
		if (!isValidAction(ACTION_Recive))
			return false;
		if (m_document != null) 
		{

			// Test request for Shipments, to avoid changes to DocAction signature required class for this status should be tested
			if (m_document instanceof MInOut) {
				MInOut m_inout= (MInOut) m_document;
				if (!m_inout.reciveIt()) {
					return(false);
				}
			}
			
			m_status = STATUS_Recived;
			m_document.setDocStatus(m_status);
			return true;
		}
		m_status = STATUS_Drafted;
		return true;
	}	//	reciveIt
	
	
	/**
	 * 	Pick Document. OpenUP FL 18/02/2011, isseue #301
	 * 	Status: Requested
	 * 	@return true if success 
	 * 	TODO: this should be implemented as a method interfase in DocAction, but it will generate errors
	 */
	public boolean pickIt()
	{
		if (!isValidAction(ACTION_Pick))
			return false;
		if (m_document != null) 
		{

			// Test request for Shipments, to avoid changes to DocAction signature required class for this status should be tested
			if (m_document instanceof MInOut) {
				MInOut m_inout= (MInOut) m_document;
				if (!m_inout.pickIt()) {
					return(false);
				}
			}
			
			m_status = STATUS_Picked;
			m_document.setDocStatus(m_status);
			return true;
		}
		m_status = STATUS_Drafted;
		return true;
	}	//	pickIt
	
	
	/**
	 * 	Set Document Status to new Status
	 *	@param newStatus new status
	 */
	void setStatus (String newStatus)
	{
		m_status = newStatus;
	}	//	setStatus

	
	/**************************************************************************
	 * 	Get Action Options based on current Status
	 *	@return array of actions
	 */
	public String[] getActionOptions()
	{
		if (isInvalid())
			return new String[] {ACTION_Prepare, ACTION_Invalidate, 
				ACTION_Unlock, ACTION_Void, ACTION_Request, ACTION_Apply, ACTION_Approve, ACTION_Complete};	// OpenUP FL 18/02/2011, issue #301, ACTION_Request added

		if (isDrafted())
			return new String[] {ACTION_Prepare, ACTION_Invalidate, ACTION_Complete, 
				ACTION_Unlock, ACTION_Void, ACTION_Request, ACTION_Apply, ACTION_Approve};	// OpenUP FL 18/02/2011, issue #301, ACTION_Request added
		
		// Requested it just an intermediente state. It that can be set by users that are not autorized to complete the document. From this state, autorized users to proceed with next actions
		if (isRequested()) {
			return(new String[]{ACTION_Asign,ACTION_Prepare, ACTION_Invalidate, ACTION_Complete,ACTION_Unlock, ACTION_Void,ACTION_Approve, ACTION_Reject});		// OpenUP FL 18/02/2011, issue #301. OpenUP FL 15/03/2011, issue #224, approve and reject are also posible values  
		}
		
		// Asigned it just an intermediente state. It that can be set by users to asigne responsability over a requested document
		if (isAsigned()) {
			return(new String[]{ACTION_Recive,ACTION_Prepare, ACTION_Invalidate, ACTION_Complete,ACTION_Unlock, ACTION_Void,ACTION_Approve, ACTION_Reject});	// OpenUP FL 18/02/2011, issue #301. OpenUP FL 15/03/2011, issue #224, approve and reject are also posible values
		}

		// Applied it just an intermediente state. It that can be set by users to asigne responsability over a requested document
		if (isApplied()) {
			return(new String[]{ACTION_Prepare, ACTION_Invalidate, ACTION_Complete,ACTION_Unlock, ACTION_Void});	// OpenUP FL 18/02/2011, issue #301. OpenUP FL 15/03/2011, issue #224, approve and reject are also posible values
		}
		
		// Asigned it just an intermediente state. It that can be set by users to asigne responsability over a requested document
		if (isRecived()) {
			return(new String[]{ACTION_Pick,ACTION_Prepare, ACTION_Invalidate, ACTION_Complete,ACTION_Unlock, ACTION_Void,ACTION_Approve, ACTION_Reject,ACTION_Asign});		// OpenUP FL 18/02/2011, issue #301. OpenUP FL 15/03/2011, issue #224, approve and reject are also posible values
		}
		
		// Picked it just an intermediente state. It that can be set by users to pick a document before complete 
		if (isPicked()) {
			return(new String[]{ACTION_Prepare, ACTION_Invalidate, ACTION_Complete,ACTION_Unlock, ACTION_Void,ACTION_Approve, ACTION_Reject});					// OpenUP FL 18/02/2011, issue #301. OpenUP FL 15/03/2011, issue #224, approve and reject are also posible values
		}
		
		if (isInProgress() || isApproved())
			return new String[] {ACTION_Complete, ACTION_WaitComplete, 
				ACTION_Approve, ACTION_Reject, 
				ACTION_Unlock, ACTION_Void, ACTION_Prepare};
		
		if (isNotApproved())
			return new String[] {ACTION_Reject, ACTION_Prepare, 
				ACTION_Unlock, ACTION_Request, ACTION_Void}; // OpenUp. Agrego action REQUEST
		
		if (isWaiting())
			return new String[] {ACTION_Complete, ACTION_WaitComplete,
				ACTION_ReActivate, ACTION_Void, ACTION_Close};
		
		if (isCompleted())
			return new String[] {ACTION_Close, ACTION_ReActivate, 
				ACTION_Reverse_Accrual, ACTION_Reverse_Correct, 
				ACTION_Post, ACTION_Void};
		
		if (isClosed())
			return new String[] {ACTION_Post, ACTION_ReOpen};
		
		if (isReversed() || isVoided())
			return new String[] {ACTION_Post};
		
		return new String[] {};
	}	//	getActionOptions

	/**
	 * 	Is The Action Valid based on current state
	 *	@param action action
	 *	@return true if valid
	 */
	public boolean isValidAction (String action)
	{
		String[] options = getActionOptions();
		for (int i = 0; i < options.length; i++)
		{
			if (options[i].equals(action))
				return true;
		}
		return false;
	}	//	isValidAction

	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg ()
	{
		return m_message;
	}	//	getProcessMsg
	
	/**
	 * 	Get Process Message
	 *	@param msg clear text error message
	 */
	public void setProcessMsg (String msg)
	{
		m_message = msg;
	}	//	setProcessMsg
	
	
	/**	Document Exception Message		*/
	private static String EXCEPTION_MSG = "Document Engine is no Document"; 
	
	/*************************************************************************
	 * 	Get Summary
	 *	@return throw exception
	 */
	public String getSummary()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}
	
	/**
	 * 	Get Document No
	 *	@return throw exception
	 */
	public String getDocumentNo()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * 	Get Document Info
	 *	@return throw exception
	 */
	public String getDocumentInfo()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * 	Get Document Owner
	 *	@return throw exception
	 */
	public int getDoc_User_ID()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}
	
	/**
	 * 	Get Document Currency
	 *	@return throw exception
	 */
	public int getC_Currency_ID()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * 	Get Document Approval Amount
	 *	@return throw exception
	 */
	public BigDecimal getApprovalAmt()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * 	Get Document Client
	 *	@return throw exception
	 */
	public int getAD_Client_ID()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}

	/**
	 * 	Get Document Organization
	 *	@return throw exception
	 */
	public int getAD_Org_ID()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}
	
	/**
	 * 	Get Doc Action
	 *	@return Document Action
	 */
	public String getDocAction()
	{
		return m_action;
	}

	/**
	 * 	Save Document
	 *	@return throw exception
	 */
	public boolean save()
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}
	
	/**
	 * 	Save Document
	 *	@return throw exception
	 */
	public void saveEx() throws AdempiereException
	{
		throw new IllegalStateException(EXCEPTION_MSG);
	}
	
	/**
	 * 	Get Context
	 *	@return context
	 */
	public Properties getCtx()
	{
		if (m_document != null)
			return m_document.getCtx();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	getCtx

	/**
	 * 	Get ID of record
	 *	@return ID
	 */
	public int get_ID()
	{
		if (m_document != null)
			return m_document.get_ID();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	get_ID
	
	/**
	 * 	Get AD_Table_ID
	 *	@return AD_Table_ID
	 */
	public int get_Table_ID()
	{
		if (m_document != null)
			return m_document.get_Table_ID();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	get_Table_ID
	
	/**
	 * 	Get Logger
	 *	@return logger
	 */
	public CLogger get_Logger()
	{
		if (m_document != null)
			return m_document.get_Logger();
		throw new IllegalStateException(EXCEPTION_MSG);
	}	//	get_Logger

	/**
	 * 	Get Transaction
	 *	@return trx name
	 */
	public String get_TrxName()
	{
		return null;
	}	//	get_TrxName

	/**
	 * 	CreatePDF
	 *	@return null
	 */
	public File createPDF ()
	{
		return null;
	}
	
	/**
	 * Get list of valid document action into the options array parameter. 
	 * Set default document action into the docAction array parameter.
	 * @param docStatus
	 * @param processing
	 * @param orderType
	 * @param isSOTrx
	 * @param AD_Table_ID
	 * @param docAction
	 * @param options
	 * @return Number of valid options
	 * 				OpenUp. Gabriel Vila. 07/07/2011. Issue #646.
	 * 				NUEVO PARAMETRO para recibir el Record_ID.
	 * 				Fin Issue #646.
	 */
	public static int getValidActions(String docStatus, Object processing, 
			String orderType, String isSOTrx, int AD_Table_ID, int Record_ID, 
			String[] docAction, String[] options)
	{
		if (options == null)
			throw new IllegalArgumentException("Option array parameter is null");
		if (docAction == null)
			throw new IllegalArgumentException("Doc action array parameter is null");
		
		int index = 0;
		
//		Locked
		if (processing != null)
		{
			boolean locked = "Y".equals(processing);
			if (!locked && processing instanceof Boolean)
				locked = ((Boolean)processing).booleanValue();
			if (locked)
				options[index++] = DocumentEngine.ACTION_Unlock;
		}

		// OpenUp. Gabriel Vila. 08/12/2010.
		// Opciones de DocAction para generacion de reservas.
		if (AD_Table_ID == I_UY_Reserve_Filter.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Prepare; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
			{
				options[index++] = DocumentEngine.ACTION_Complete;
			}
 
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 12/04/2011. Issue #139
		// Opciones de DocAction para Proceso de Diferencia de Cambio.
		if (AD_Table_ID == I_UY_ExchangeDiffHdr.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Prepare; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
			{
				options[index++] = DocumentEngine.ACTION_Complete;
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
			}

			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 22/05/2013. Issue #855
		// OpenUp. Gabriel Vila. 12/04/2011. Issue #979
		// Opciones de DocAction para Asiento de Cierre de Ejercicio y Resultados Parciales
		if (AD_Table_ID == I_UY_YearEnd.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}

			return index;
		}
		// Fin OpenUp.
		// Fin OpenUp. #855
		
		// OpenUp. Gabriel Vila. 09/02/2011.
		// Opciones de DocAction para Aprobacion Crediticia.
		if (AD_Table_ID == I_UY_Credit_Filter.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Prepare; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
			{
				options[index++] = DocumentEngine.ACTION_Complete;
			}
 
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Nicolas Sarlabos issue #925 15/11/2011
		// Opciones de DocAction para Generacion de facturas desde remitos
		if (AD_Table_ID == I_UY_InvReferFilter.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Prepare; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
			{
				options[index++] = DocumentEngine.ACTION_Complete;
			}
 
			return index;
		}
		// Fin OpenUp. Nicolas Sarlabos issue #925 15/11/2011
		
		// OpenUp. Nicolas Sarlabos issue #908 06/12/2011
		// Opciones de DocAction para Cancelacion Masiva de Reservas
		if (AD_Table_ID == I_UY_CancelReservationFilter.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Prepare; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
			{
				options[index++] = DocumentEngine.ACTION_Complete;
			}
 
			return index;
		}
		// Fin OpenUp. Nicolas Sarlabos issue #908 06/12/2011

		// OpenUp. Gabriel Vila. 22/02/2013. Issue #381
		// Opciones de DocAction para Ordenes de Compra Parciales por Sector.
		if (AD_Table_ID == MOrderSection.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))
					  || (docStatus.equals(DocumentEngine.STATUS_NotApproved))){
				String [] sele={DocumentEngine.ACTION_Request};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Requested)){
				String [] sele={DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Reject};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Approved)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Voided)){
				String [] sele={DocumentEngine.ACTION_None};
				return updateArray(options,sele);					
			}
		}

		// OpenUp. Gabriel Vila. 25/05/2015. Issue #4168
		// Opciones de DocAction para Solicitud de Registro de Usuarios
		if (AD_Table_ID == I_UY_UserReq.Table_ID)
		{
			// Depende del documento
			MUserReq model = new MUserReq(Env.getCtx(), Record_ID, null);
			MDocType doc = new MDocType(Env.getCtx(), model.getC_DocType_ID(), null);
			
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))
					  || (docStatus.equals(DocumentEngine.STATUS_NotApproved))){
				
				if (doc.getValue().equalsIgnoreCase("bgreqsubcliente")){

					String [] sele={DocumentEngine.ACTION_Complete};
					return updateArray(options,sele);
					
				}
				else{
					String [] sele={DocumentEngine.ACTION_Request};
					return updateArray(options,sele);
				}
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Requested)){
				String [] sele={DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Reject};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Approved)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}
		}

		// OpenUp. Gabriel Vila. 01/02/2016.
		// Opciones de DocAction para Remitos Internos
		if (AD_Table_ID == I_UY_RT_InternalDelivery.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Apply};
				return updateArray(options,sele);

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Applied)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);		
			}
		}		
		
		
		// OpenUp. Gabriel Vila. 25/05/2015. Issue #4173, #1474
		// Opciones de DocAction para Solicitud de Registro de Transacciones bursatiles
		if (AD_Table_ID == I_UY_BG_Transaction.Table_ID)
		{
			// Depende del documento
			MBGTransaction model = new MBGTransaction(Env.getCtx(), Record_ID, null);
			MDocType doc = new MDocType(Env.getCtx(), model.getC_DocType_ID(), null);
			
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))
					  || (docStatus.equals(DocumentEngine.STATUS_NotApproved))){

				if ((doc.getValue().equalsIgnoreCase("bgprecontrato")) 
						|| (doc.getValue().equalsIgnoreCase("bgmandato"))){

					String [] sele={DocumentEngine.ACTION_Request};
					return updateArray(options,sele);


				}
				else {
					String [] sele={DocumentEngine.ACTION_Complete};
					return updateArray(options,sele);
				}
				
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Requested)){
				String [] sele={DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Reject};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Approved)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}
		}

		// OpenUp. Raul Capecce. 28/08/2015. Issue #4711
		// Opciones de DocAction para Adjudicacion de Subasta
		if (AD_Table_ID == I_UY_BG_Aution.Table_ID) {
			if ( docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_None};
				return updateArray(options,sele);
			}
		}
		
		// OpenUp. Gabriel Vila. 25/05/2015. Issue #4197
		// Opciones de DocAction para Solicitud de Registro de Subasta
		if (AD_Table_ID == I_UY_BG_AutionReq.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))
					  || (docStatus.equals(DocumentEngine.STATUS_NotApproved))){

					String [] sele={DocumentEngine.ACTION_Request};
					return updateArray(options,sele);
				
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Requested)){
				String [] sele={DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Reject};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Approved)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}
		}
		
		
		// OpenUp. Gabriel Vila. 04/12/2013. Issue #1618
		// Opciones de DocAction para Gestion de Falla de transporte.
		if (AD_Table_ID == I_UY_TR_FaultManage.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Request};
				return updateArray(options,sele);

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Requested)){
				
				String [] sele={DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Reject};
				return updateArray(options,sele);
				
			}
			else if ((docStatus.equals(DocumentEngine.STATUS_Approved))
					|| (docStatus.equals(DocumentEngine.STATUS_NotApproved))){

				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);

			}
		}		

		// OpenUp. Gabriel Vila. 09/05/2013. Issue #1617
		// Opciones de DocAction para Asociacion de vehiculos en modulo de transporte
		if (AD_Table_ID == I_UY_TR_TruckAssociation.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_None;
			}
			
		}		

		// OpenUp. Gabriel Vila. 21/12/2015. Issue #5150
		// Opciones de DocAction para Ordenes de carga de Tienda
		if (AD_Table_ID == I_UY_StoreLoadOrder.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_None;
			}
			
		}		

		
		// OpenUp. Gabriel Vila. 13/07/2015. Issue #4226
		// Opciones de DocAction para Ingreso de precios en modulo de bolsa
		if (AD_Table_ID == I_UY_BG_DailyPrice.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_None;
			}
			
		}		

		// OpenUp. Gabriel Vila. 13/07/2015. Issue #4226
		// Opciones de DocAction para Registro de Instrumentos
		if (AD_Table_ID == I_UY_BG_Instrument.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_None;
			}
		}		

		
		// OpenUp. Gabriel Vila. 13/07/2015. Issue #4226
		// Opciones de DocAction para Registro de Instrumentos
		if (AD_Table_ID == I_UY_BG_Contract.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);

			}
			
			return index;
			/*else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_None;
			}*/
		}		

		// OpenUp. Gabriel Vila. 23/07/2014. Issue #1405
		// Opciones de DocAction para Consumos de Stock de Productos
		if (AD_Table_ID == I_UY_ProductConsume.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				String [] sele = {DocumentEngine.ACTION_Void};
				return updateArray(options,sele);
			}
			
		}		

		// OpenUp. Ines Fernandez. 07/04/2015. Issue #2562
		// Opciones de DocAction para Produccion de Manufactura de Transformacion
		if (AD_Table_ID == I_UY_ProdTransf.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				//String [] sele = {DocumentEngine.ACTION_Close, DocumentEngine.ACTION_Void};
				String [] sele = {DocumentEngine.ACTION_Void};
				return updateArray(options,sele);
			}
			
		}		
		
		// OpenUp. Gabriel Vila. 27/03/2013. Issue #
		// Opciones de DocAction para Proceso de Acuse de recibo en Tracking
		if (AD_Table_ID == I_UY_TT_Acuse.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);

			}
		}		
		
		// OpenUp. Gabriel Vila. 27/03/2013. Issue #
		// Opciones de DocAction para Entrega de Tarjetas en Tracking
		if (AD_Table_ID == I_UY_TT_Hand.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Apply};
				return updateArray(options,sele);

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Applied)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);		
			}
		}		
		
		// OpenUp. Gabriel Vila. 20/08/2015. Issue #
		// Opciones de DocAction para Carga de Archivos Subagencias
		if (AD_Table_ID == I_UY_TT_LoadSubAgency.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Apply};
				return updateArray(options,sele);

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Applied)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);		
			}
		}

		// OpenUp. Gabriel Vila. 20/09/2015. Issue #
		// Opciones de DocAction para Carga de Archivos Incidencias
		if (AD_Table_ID == I_UY_R_LoadXls.Table_ID)
		{
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Apply};
				return updateArray(options,sele);

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Applied)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);		
			}
		}

		
		// OpenUp. Gabriel Vila. 04/03/2011. Issue #437
		// Opciones de DocAction para Actualizacion Masiva de Pedidos
		if (AD_Table_ID == I_UY_UpdOrder_Filter.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Prepare; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
			{
				options[index++] = DocumentEngine.ACTION_Complete;
			}
 
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Gabriel Vila. 04/08/2011. Issue #829
		// Opciones de DocAction para Ajustes de Inventario
		if (AD_Table_ID == I_UY_StockAdjustment.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Complete; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
			}
 
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Nicolas Sarlabos. 13/02/2012. Issue #944
		// Opciones de DocAction para Ajuste Manual de Costos
		if (AD_Table_ID == I_UY_CostAdjustment.Table_ID) {
			if (docStatus.equals(DocumentEngine.STATUS_Drafted)) {
				options[index++] = DocumentEngine.ACTION_Complete;
			}
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 24/10/2011. Issue #896
		// Opciones de DocAction para Nueva estructura de afectaciones
		if (AD_Table_ID == I_UY_Allocation.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocumentEngine.ACTION_Complete; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
			}
 
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 15/08/2013. Issue #285
		// Opciones de DocAction para Solicitud de Ajustes
		if (AD_Table_ID == I_UY_R_AjusteRequest.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocumentEngine.ACTION_Complete; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_None;
			}
 
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 02/10/2015. Issue #285
		// Opciones de DocAction para verificacion de entregas tracking
		if (AD_Table_ID == I_UY_TT_DeliveryConf.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocumentEngine.ACTION_Complete; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_None;
			}
 
			return index;
		}
		// Fin OpenUp.

		
		// OpenUp. Gabriel Vila. 02/10/2015. Issue #285
		// Opciones de DocAction para verificacion de entregas tracking
		if (AD_Table_ID == I_UY_TT_Contract.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocumentEngine.ACTION_Complete; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_None;
			}
 
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Gabriel Vila. 02/10/2015. Issue #285
		// Opciones de DocAction para entrega de contratos tracking
		if (AD_Table_ID == I_UY_TT_ContractHand.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_None;
			}
			return index;
			
		}
		// Fin OpenUp.
		
		// OpenUp. Gabriel Vila. 19/13/2013. Issue #366
		// Opciones de DocAction para Libreta o Resma de Cheques
		if (AD_Table_ID == I_UY_CheckReam.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocumentEngine.ACTION_Complete; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
			}
 
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Garcia. 02/06/2011. Issue #379
		// Opciones de DocAction para Actualizacion Masiva de Devoluciones
		if (AD_Table_ID == I_UY_UpdDevo_Filter.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Prepare; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
			{
				options[index++] = DocumentEngine.ACTION_Complete;
			}
 
			return index;
		}
		// Fin OpenUp.
		// OpenUp. Nicolas Garcia. 02/06/2011. Issue #887
		// Opciones de DocAction para Filtros creacion orden de compra vs
		// Factura vessena
		if (AD_Table_ID == I_UY_LoadPOrderDBFilter.Table_ID) {
			if (docStatus.equals(DocumentEngine.STATUS_Drafted)) {
				options[index++] = DocumentEngine.ACTION_Prepare;
			} else if (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation)) {
				options[index++] = DocumentEngine.ACTION_Complete;
			}

			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 05/07/2011. Issue #735
		// OpenUp. Nicolas Sarlabos. 28/09/2011. Issue #880, se hicieron cambios para cumplir con caso #880
		// Opciones de DocAction para aprobar masivamente nc dif de precio y apoyo 
		if (AD_Table_ID == I_UY_NoteCreditApprov_Filter.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Prepare; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Requested))
			{
				options[index++] = DocumentEngine.ACTION_Approve;
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Approved))
			{
				options[index++] = DocumentEngine.ACTION_Complete;
			}
			
		 
			return index;
		}
		// Fin OpenUp.

		
		// OpenUp. Nicolas Sarlabos. 12/07/2011. Issue #776
		// Opciones de DocAction para ventana "Interfaz de Reloj"
		if (AD_Table_ID == I_UY_ClockInterface_Filter.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Prepare; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
			{
				options[index++] = DocumentEngine.ACTION_Complete;
			}
		 
			return index;
		}
		// Fin OpenUp.



		
		// OpenUp. Gabriel Vila. 02/01/2011.
		// Opciones de DocAction para asignacion de transporte.
		if (AD_Table_ID == I_UY_AsignaTransporteHdr.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Prepare; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
			{
				options[index++] = DocumentEngine.ACTION_Complete;
			}
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 12/07/2011. Issue #503
		// Opciones de DocAction para cierre de transporte.
		if (AD_Table_ID == I_UY_CierreTransporteHdr.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 30/10/2012. Issue #
		// Opciones de DocAction para Presupuesto de Venta.
		if (AD_Table_ID == I_UY_Budget.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
				options[index++] = DocAction.ACTION_ReActivate; //OpenUp. Nicolas Sarlabos. 16/12/2013. #1251. Se agrega opcion de Reactivar.
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 30/10/2012. Issue #
		// Opciones de DocAction para Orden de Fabricacion.
		if (AD_Table_ID == I_UY_ManufOrder.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
				options[index++] = DocAction.ACTION_ReActivate;
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 30/10/2012. Issue #
		// Opciones de DocAction para Orden de Entrega.
		if (AD_Table_ID == I_UY_BudgetDelivery.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		//Fin OpenUp.

		
		// OpenUp. Gabriel Vila. 02/01/2011.
		// Opciones de DocAction para Picking.
		if (AD_Table_ID == I_UY_PickingHdr.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocumentEngine.ACTION_Prepare; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
			{
				options[index++] = DocumentEngine.ACTION_Complete;
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
			}
			return index;
		}
		// Fin OpenUp.
	
		// OpenUp. Gabriel Vila. 22/11/2012. Issue #128
		// Opciones de DocAction para Provisiones.
		if (AD_Table_ID == I_UY_Provision.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
				options[index++] = DocAction.ACTION_ReActivate;
			}
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 14/03/2013. Issue #296
		// Opciones de DocAction para Extorno de Provisiones.
		if (AD_Table_ID == I_UY_ExtProvision.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				String [] sele={DocumentEngine.ACTION_Apply};
				return updateArray(options,sele);				
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){

				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);				

			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{

				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);				
			}
			return index;
		}
		// Fin OpenUp.		
		
		// OpenUp. Gabriel Vila. 11/02/2013. Issue #348
		// Opciones de DocAction para Generacion de Ordenes de Pago
		if (AD_Table_ID == I_UY_PayOrderGen.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			//SBT Issue #5440
			else if (docStatus.equals(DocAction.STATUS_Voided)){
				options[index++] = DocAction.ACTION_None;
			}
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. SBT 16/02/2016. Issue #5440
		// Opciones de DocAction para Ordenes de Pago (Anterioremente se aplicaba el inf y proceso UY_PPayOrder)
		if (AD_Table_ID == I_UY_RT_InternalInvoice.Table_ID){
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
				String [] sele={DocumentEngine.ACTION_Apply};
				return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Applied)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);		
			}
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 13/02/2013. Issue #351
		// Opciones de DocAction para Emision de Pagos
		if (AD_Table_ID == I_UY_PayEmit.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 04/06/2013. Issue #932
		// Opciones de DocAction para Reimpresion de Cheques
		if (AD_Table_ID == I_UY_PayOrder.Table_ID){
			if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}			
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Gabriel Vila. 13/12/2012. Issue #93
		// Opciones de DocAction para Cotizacion de Proveedores.
		if (AD_Table_ID == I_UY_QuoteVendor.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))
					  || (docStatus.equals(DocumentEngine.STATUS_NotApproved))){
				String [] sele={DocumentEngine.ACTION_Request};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Requested)){
				String [] sele={DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Reject};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Approved)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Voided)){
				String [] sele={DocumentEngine.ACTION_None};
				return updateArray(options,sele);					
			}
			
			return index;
		}
		// Fin OpenUp.
	
		// OpenUp. Gabriel Vila. 26/12/2012. Issue #198
		// Opciones de DocAction para Macro Solicitudes.
		if (AD_Table_ID == I_UY_RFQ_MacroReq.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))
					  || (docStatus.equals(DocumentEngine.STATUS_NotApproved))
					  || (docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))){
				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}

			return index;
		}
		// Fin OpenUp.

		
		// OpenUp. Gabriel Vila. 05/05/2014. Issue #1626
		// Opciones de DocAction para Remito de Mercaderias
		if (AD_Table_ID == I_UY_RFQ_MacroReq.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}

			return index;
		}
		// Fin OpenUp.

		
		// OpenUp. Gabriel Vila. 26/12/2012. Issue #198
		// Opciones de DocAction para Generacion de Solicitudes de Cotizacion a Proveedores en compras.
		if (AD_Table_ID == I_UY_RFQGen_Filter.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete;
			}			
			return index;
		}
		// Fin OpenUp.

		
		// OpenUp. Guillermo Brust. 11/01/2013. Issue #142
		// Opciones de DocAction para Coeficientes de Interes Plan Cuota
		if (AD_Table_ID == I_UY_Fdu_CoefficientHdr.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				//options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Guillermo Brust. 19/11/2013. Issue #1395
		// Opciones de DocAction para automatizacion de ajustes por aviso de mora.
		if (AD_Table_ID == I_UY_Fdu_MassiveAdjustment.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			
			return index;
		}
		//Fin OpenUp.

		// OpenUp. Gabriel Vila. 05/02/2013. Issue #285
		// Opciones de DocAction para Ingreso de Reclamos.
		if (AD_Table_ID == I_UY_R_Reclamo.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			/*
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			*/
			
			return index;
		}
		//Fin OpenUp.

		// OpenUp. Gabriel Vila. 12/09/2013. Issue #1173.
		// Opciones de DocAction para Recepcion en Tracking.
		if (AD_Table_ID == I_UY_TT_Receipt.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			/*
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			*/
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 12/09/2013. Issue #1173.
		// Opciones de DocAction para Carga de Bolsin en Tracking.
		if (AD_Table_ID == I_UY_TT_SealLoad.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			/*
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			*/
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 12/09/2013. Issue #1173.
		// Opciones de DocAction para Proceso de Retenidas en Tracking.
		if (AD_Table_ID == I_UY_TT_Retention.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			/*
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			*/
			return index;
		}
		// Fin OpenUp.

		
		// OpenUp. Nicolas Sarlabos. 09/10/2013. Issue #1173.
		// Opciones de DocAction para Envio de Bolsin en Tracking.
		if (AD_Table_ID == I_UY_TT_Delivery.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			/*
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			*/
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 12/09/2013. Issue #1173.
		// Opciones de DocAction para Unificacion de CardCarrier en Tracking.
		if (AD_Table_ID == I_UY_TT_Unify.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			/*
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			*/
			return index;
		}
		// Fin OpenUp.		

		// OpenUp. Gabriel Vila. 18/10/2013. Issue #1173.
		// Opciones de DocAction para Control de Caja
		if (AD_Table_ID == I_UY_TT_BoxLoad.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			/*
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			*/
			return index;
		}
		// Fin OpenUp.		
		
		// OpenUp. Nicolas Sarlabos. 02/10/2013. #1363
		// Opciones de DocAction para Carga Chequera Tracking.
		if (AD_Table_ID == I_UY_TT_Chequera.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 09/10/2013. Issue #1378.
		// Opciones de DocAction para Recepcion de Courier.
		if (AD_Table_ID == I_UY_TT_ReceiptCourier.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			/*
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			*/
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 17/04/2013. Issue #693
		// Opciones de DocAction para Carga Extracto Bancario.
		if (AD_Table_ID == I_UY_LoadExtract.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 17/04/2013.
		// Opciones de DocAction para Conciliacion Bancaria.
		if (AD_Table_ID == I_UY_Conciliation.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
				options[index++] = DocAction.ACTION_ReActivate; //OpenUp. Nicolas Sarlabos. 25/06/2014. #2083.
			}
			
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 26/04/2013. Issue #760
		// Opciones de DocAction para Registro de Cargo Bancario.
		if (AD_Table_ID == I_UY_CargoRegister.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 26/09/2014. Issue #
		// Opciones de DocAction para Apertura Saldos Bancarios.
		if (AD_Table_ID == I_UY_BankBalance.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_ReActivate;
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 03/06/2013. Issue #927
		// Opciones de DocAction para Apertura de Caja
		if (AD_Table_ID == I_UY_CashOpen.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 03/06/2013. Issue #928
		// Opciones de DocAction para Cierre de Caja
		if (AD_Table_ID == I_UY_CashClose.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 03/06/2013. Issue #869
		// Opciones de DocAction para Movimiento de Caja.
		if (AD_Table_ID == I_UY_CashMove.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 24/08/2015. Issue #4449.
		// Opciones de DocAction para Efectivo de Cajeras.
		if (AD_Table_ID == I_UY_CashCashier.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_ReActivate;
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 23/07/2015. Issue #4449.
		// Opciones de DocAction para Remesa de Cajas.
		if (AD_Table_ID == I_UY_CashRemittance.Table_ID)
		{
			// Depende del documento
			MCashRemittance model = new MCashRemittance(Env.getCtx(), Record_ID, null);
			MDocType doc = new MDocType(Env.getCtx(), model.getC_DocType_ID(), null);

			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					|| (docStatus.equals(DocumentEngine.STATUS_InProgress))
					|| (docStatus.equals(DocumentEngine.STATUS_Invalid))
					|| (docStatus.equals(DocumentEngine.STATUS_NotApproved))){

				if (doc.getValue().equalsIgnoreCase("cashremittvalues") || doc.getValue().equalsIgnoreCase("cashremittcheck")){

					String [] sele={DocumentEngine.ACTION_Request};
					return updateArray(options,sele);

				}
				else {
					String [] sele={DocumentEngine.ACTION_Complete};
					return updateArray(options,sele);
				}

			}
			else if (docStatus.equals(DocumentEngine.STATUS_Requested)){
				String [] sele={DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Reject};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Approved)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocAction.STATUS_Completed)){
				
				if (doc.getValue().equalsIgnoreCase("cashremittvalues") || doc.getValue().equalsIgnoreCase("cashremittcheck")){
					
					String [] sele={DocumentEngine.ACTION_Void};
					return updateArray(options,sele);			
					
				} else {
					
					String [] sele={DocumentEngine.ACTION_ReActivate, DocumentEngine.ACTION_Void};
					return updateArray(options,sele);
					
				}
			
			}
			
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 08/09/2015. Issue #4437.
		// Opciones de DocAction para Arqueo de Cajas.
		if (AD_Table_ID == I_UY_CashCount.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply; 
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_ReActivate, DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 25/06/2013. Issue #1058
		// Opciones de DocAction para Proceso de Liquidacion de Nomina.
		if (AD_Table_ID == I_UY_HRProcesoNomina.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_ReActivate;
			}
			return index;
		}
		//Fin OpenUp.

		// OpenUp. Nicolas Sarlabos. 02/07/2013. Issue #1059
		// Opciones de DocAction para Simulacion de Factura BPS.
		if (AD_Table_ID == I_UY_HRAportesPatronales.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_ReActivate;
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 14/01/2014. Issue #1758.
		// Opciones de DocAction para Carga de Planilla de Conductores.
		if (AD_Table_ID == I_UY_HRLoadDriver.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}	
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 17/10/2013. Issue #1418
		// Opciones de DocAction para Salida de Caja
		if (AD_Table_ID == I_UY_FF_CashOut.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					|| (docStatus.equals(DocumentEngine.STATUS_InProgress))
					|| (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				options[index++] = DocAction.ACTION_Request;
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Requested)){

				options[index++] = DocAction.ACTION_Approve;
				options[index++] = DocAction.ACTION_Reject;
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Approved)){

				options[index++] = DocAction.ACTION_Complete;
				options[index++] = DocAction.ACTION_Void;
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed) 
					|| docStatus.equals(DocumentEngine.STATUS_NotApproved)){

				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 20/12/2013. Issue #1425
		// Opciones de DocAction para Devolucion de Salida de Fondo Fijo
		if (AD_Table_ID == I_UY_FF_CashOutReturn.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					|| (docStatus.equals(DocumentEngine.STATUS_InProgress))
					|| (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				options[index++] = DocAction.ACTION_Complete;
			}			
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 02/01/2014. Issue #1426
		// Opciones de DocAction para Reposicion de Fondo Fijo
		if (AD_Table_ID == I_UY_FF_Replenish.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					|| (docStatus.equals(DocumentEngine.STATUS_InProgress))
					|| (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				options[index++] = DocAction.ACTION_Complete;
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 02/01/2014. Issue #1427
		// Opciones de DocAction para Confirmacion de Reposicion de Fondo Fijo
		if (AD_Table_ID == I_UY_FF_ReplenishConf.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					|| (docStatus.equals(DocumentEngine.STATUS_InProgress))
					|| (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				options[index++] = DocAction.ACTION_Complete;
			}
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 10/01/2014. Issue #1745
		// Opciones de DocAction para Arqueo de Fondo Fijo.
		if (AD_Table_ID == I_UY_FF_CashCount.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}			
			return index;
		}
		//Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 27/11/2013. #1605.
		// Opciones de DocAction para Mantenimiento de Neumaticos.
		if (AD_Table_ID == I_UY_TR_TireMove.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 27/11/2013. #1609.
		// Opciones de DocAction para Carga Consumo Combustible.
		if (AD_Table_ID == I_UY_TR_LoadFuel.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			return index;
		}
		// Fin OpenUp. #1609.

		// OpenUp. Nicolas Sarlabos. 27/11/2013. #1609.
		// Opciones de DocAction para Orden de Servicio.
		if (AD_Table_ID == I_UY_TR_ServiceOrder.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed)){
				options[index++] = DocAction.ACTION_Close;
			}
			return index;
		}
		// Fin OpenUp. #1609.

		
		// OpenUp. Nicolas Sarlabos. 29/11/2013. #1609.
		// Opciones de DocAction para Consumo de Combustible.
		if (AD_Table_ID == I_UY_TR_Fuel.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		// Fin OpenUp. #1609.
		
		
		// OpenUp.INes Fernandez. 14/07/2015. #.
		// Opciones de DocAction para Reglas de Negocio, Bonificaciones de
		// Proveedor.
		if (AD_Table_ID == I_UY_DiscountRuleLine.Table_ID) {
			if (docStatus.equals(DocumentEngine.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)) {
				options[index++] = DocAction.ACTION_Complete;
			} else if (docStatus.equals(DocAction.STATUS_Completed)) {
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		// Fin OpenUp. #.
		
		
		// OpenUp. Nicolas Sarlabos. 02/12/2013. #1617.
		// Opciones de DocAction para Lectura de Kilometraje de Vehiculo.
		if (AD_Table_ID == I_UY_TR_ReadKM.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
			}

			return index;
		}
		// Fin OpenUp. #1609.
		
		// OpenUp. Guillermo Brust. 06/12/2013. #1606.
		// Opciones de DocAction para Lectura de Kilometraje de Vehiculo.
		if (AD_Table_ID == I_UY_TR_Recauchutaje.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);
 
			}
			return index;
		}
		// Fin OpenUp. #1607.
		
		
		// OpenUp. Guillermo Brust. 12/12/2013. #1625.
		// Opciones de DocAction para Gestion de Cargas
		if (AD_Table_ID == I_UY_TR_LoadManage.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocAction.ACTION_Complete; 
			}		
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
			}
			return index;
		}
		// Fin OpenUp. #1625.
		
		// OpenUp. Nicolas Sarlabos. 17/11/2014. #
		// Opciones de DocAction para Seguimiento de Cargas
		if (AD_Table_ID == I_UY_TR_LoadMonitor.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted)){ 

				String [] sele={DocumentEngine.ACTION_Apply};
						return updateArray(options,sele);
			}
			else if ((docStatus.equals(DocAction.STATUS_Applied))
					|| (docStatus.equals(DocumentEngine.STATUS_Invalid))
					|| (docStatus.equals(DocumentEngine.STATUS_InProgress))){
				
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);			
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_ReActivate, DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}

			return index;

		}
		// Fin OpenUp. #1625.		

		// OpenUp. Gabriel Vila. 24/11/2014. Issue #3315.
		// Opciones de DocAction para Registracion de Asientos Tipo
		if (AD_Table_ID == I_UY_TypeFact.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}
			return index;
		}
		// Fin OpenUp. Issue #3315.		

		
		// OpenUp. Guillermo Brust. 12/12/2013. #1624.
		// Opciones de DocAction para Orden de Transporte
		if (AD_Table_ID == I_UY_TR_TransOrder.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}			
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_None;
			}
			return index;
		}
		// Fin OpenUp. #1624.
		
		// OpenUp. Nicolas Sarlabos. 21/02/2014. #1622.
		// Opciones de DocAction para Gestion de Expediente.
		if (AD_Table_ID == I_UY_TR_Trip.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed)){
				options[index++] = DocAction.ACTION_ReActivate;
				options[index++] = DocumentEngine.ACTION_Void;
			}
			return index;
		}
		// Fin OpenUp. #1622.
		
		// OpenUp. Nicolas Sarlabos. 24/10/2014. #3169.
		// Opciones de DocAction para Cotizacion de Servicios.
		if (AD_Table_ID == I_UY_TR_Budget.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocumentEngine.ACTION_Complete; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
				options[index++] = DocAction.ACTION_ReActivate;
			}
 
			return index;
		}
		// Fin OpenUp. #3169.
		
		// OpenUp. Nicolas Sarlabos. 20/02/2014. #1628.
		// Opciones de DocAction para Emision CRT.
		if (AD_Table_ID == I_UY_TR_Crt.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed)){
				options[index++] = DocAction.ACTION_ReActivate;
				options[index++] = DocumentEngine.ACTION_Void;
			}
			return index;
		}
		// Fin OpenUp. #1609.
		// OpenUp. Raul Capecce. 19/03/2015. #3321.
		// Opciones de DocAction para Descarga de DUA.
		if (AD_Table_ID == I_UY_TR_Dua.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			
			return index;
		}
		// Fin OpenUp. #3321.
		

		// OpenUp. Nicolas Sarlabos. 31/03/2014. #1629.
		// Opciones de DocAction para Emision MIC/DTA.
		if (AD_Table_ID == I_UY_TR_Mic.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed)){
				options[index++] = DocAction.ACTION_ReActivate;
				options[index++] = DocAction.ACTION_Void;
			}
			return index;
		}
		// Fin OpenUp. #1629.
		
		// OpenUp. Nicolas Sarlabos. 19/03/2015. #3505.
		// Opciones de DocAction para Liquidacion de Viaje.
		if (AD_Table_ID == I_UY_TR_Clearing.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid)){
				options[index++] = DocAction.ACTION_Complete;
			}
			else if (docStatus.equals(DocAction.STATUS_Completed)){
				options[index++] = DocAction.ACTION_ReActivate;
			}
			return index;
		}
		// Fin OpenUp. #3505.
		
		// OpenUp. Nicolas Sarlabos. 10/04/2014. Issue #2026
		// Opciones de DocAction para Baja de Neumatico
		if (AD_Table_ID == I_UY_TR_TireDrop.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocumentEngine.ACTION_Complete; 
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
			}
 
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Leonardo Boccone. 22/04/2014. Issue #2048
		// Opciones de DocAction para Impresion Etiquetas
		if (AD_Table_ID == I_UY_LabelPrint.Table_ID) {
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocumentEngine.ACTION_Complete; 
			}
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 16/06/2015. Issue #4420.
		// Opciones de DocAction para Actualizacion Lista de Precios
		if (AD_Table_ID == I_UY_PriceUpdate.Table_ID) {
			//OpenUp Emiliano.. Issue# SEa grega diferente flujo para el doc...
			MPriceUpdate model = new MPriceUpdate(Env.getCtx(), Record_ID, null);
			MDocType doc = new MDocType(Env.getCtx(), model.getC_DocType_ID(), null);
			if (docStatus.equals(DocAction.STATUS_Drafted))
			{
				if(doc.getValue().equalsIgnoreCase("priceupdate")){
					options[index++] = DocAction.ACTION_Apply;
				}else if (doc.getValue().equalsIgnoreCase("salespriceupdate")){
					options[index++] = DocAction.ACTION_Complete;
				}
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid)){
				options[index++] = DocAction.ACTION_Complete;
			}			
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 29/06/2015. Issue #4403.
		// Opciones de DocAction para Carga de Lista de Precios
		if (AD_Table_ID == I_UY_PriceLoad.Table_ID) {
			if (docStatus.equals(DocAction.STATUS_Drafted))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid)){
				options[index++] = DocAction.ACTION_Complete;
			}			
			return index;
		}
		// Fin OpenUp.

		// OpenUp. Nicolas Sarlabos. 29/06/2015. Issue #4403.
		// Opciones de DocAction para Carga de Lista de Precios
		if (AD_Table_ID == I_UY_ResguardoGen.Table_ID) {
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}			
			return index;
		}
		// Fin OpenUp.
	
		// OpenUp. Nicolas Sarlabos. 18/06/2015. Issue #4435.
		// Opciones de DocAction para Confirmacion de Productos
		if (AD_Table_ID == I_UY_RT_ConfirmProd.Table_ID) {
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocumentEngine.ACTION_Complete; 
			}
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Raúl Capecce. 23/11/2015. Issue #4868
		// Opciones de DocAction para Facturacion Masiva
		if (AD_Table_ID == I_UY_InvoiceGenerate.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocAction.ACTION_Apply;
			}
			else if (docStatus.equals(DocAction.STATUS_Applied)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)){
				options[index++] = DocAction.ACTION_Complete;
			}
			return index;
		}
		// Fin OpenUp.
		
		// OpenUp. Nicolas Sarlabos. 27/11/2015. Issue #3069.
		// Opciones de DocAction para Control de Libreta de Cheques.
		if (AD_Table_ID == I_UY_CheckBookControl.Table_ID) {
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || 
					docStatus.equals(DocumentEngine.STATUS_Invalid) ||
					docStatus.equals(DocumentEngine.STATUS_InProgress))
			{
				options[index++] = DocumentEngine.ACTION_Complete; 
			}
			return index;
		}

		//	Approval required           ..  NA
		if (docStatus.equals(DocumentEngine.STATUS_NotApproved))
		{
			//options[index++] = DocumentEngine.ACTION_Prepare; // OpenUp. Comento
			options[index++] = DocumentEngine.ACTION_Request; // OpenUp. Nuevo
			options[index++] = DocumentEngine.ACTION_Void;
		}
		//	Draft/Invalid				..  DR/IN
		else if (docStatus.equals(DocumentEngine.STATUS_Drafted)
			|| docStatus.equals(DocumentEngine.STATUS_Invalid))
		{
			options[index++] = DocumentEngine.ACTION_Request;						// OpenUP FL 18/02/2011, issue #301
			options[index++] = DocumentEngine.ACTION_Approve;						// OpenUP GV 26/06/2012, problemas con devoluciones
			options[index++] = DocumentEngine.ACTION_Apply;						    // OpenUP GV 25/05/2012, issue #1021
			options[index++] = DocumentEngine.ACTION_Complete;
		//	options[index++] = DocumentEngine.ACTION_Prepare;
			//options[index++] = DocumentEngine.ACTION_Void;  // OpenUp. Gabriel Vila. 26/06/2012. No tiene sentido anular documentos en borrador.
		}
		
		//	Requested					..  RQ
		else if (docStatus.equals(DocumentEngine.STATUS_Requested)) {				// OpenUP FL 18/02/2011, isseue #301 
			options[index++] = DocumentEngine.ACTION_Asign;	  		// OpenUp. Gabriel Vila. 26/06/2012. No estamos usando la accion de asignar despues de solicitar.
		//	options[index++] = DocumentEngine.ACTION_Prepare;
			options[index++] = DocumentEngine.ACTION_Complete;		// OpenUp. Gabriel Vila. 26/06/2012. No estamos usando la accion completar despues de solicitar.
			options[index++] = DocumentEngine.ACTION_Void;
			options[index++] = DocumentEngine.ACTION_Approve;						// OpenUP FL 15/02/2011, issue #224
			options[index++] = DocumentEngine.ACTION_Reject;						// OpenUP FL 15/02/2011, issue #224
		}
		
		//	Asigned						..  AS
		else if (docStatus.equals(DocumentEngine.STATUS_Asigned)) {					// OpenUP FL 18/02/2011, isseue #301 
			options[index++] = DocumentEngine.ACTION_Recive;
		//	options[index++] = DocumentEngine.ACTION_Prepare;
			options[index++] = DocumentEngine.ACTION_Complete;
			options[index++] = DocumentEngine.ACTION_Void;
			options[index++] = DocumentEngine.ACTION_Approve;						// OpenUP FL 15/02/2011, issue #224
			options[index++] = DocumentEngine.ACTION_Reject;						// OpenUP FL 15/02/2011, issue #224
		}

		//	Applied						..  AS
		else if (docStatus.equals(DocumentEngine.STATUS_Applied)) {					// OpenUP GV 25/05/2012, isseue #1021 
			options[index++] = DocumentEngine.ACTION_Complete;
		}

		//	Recived						..  RV
		else if (docStatus.equals(DocumentEngine.STATUS_Recived)) {					// OpenUP FL 18/02/2011, isseue #301 
			options[index++] = DocumentEngine.ACTION_Pick;
		//	options[index++] = DocumentEngine.ACTION_Prepare;
			options[index++] = DocumentEngine.ACTION_Complete;
			options[index++] = DocumentEngine.ACTION_Void;
			options[index++] = DocumentEngine.ACTION_Approve;						// OpenUP FL 15/02/2011, issue #224
			options[index++] = DocumentEngine.ACTION_Reject;						// OpenUP FL 15/02/2011, issue #224
			options[index++] = DocumentEngine.ACTION_Asign;							// OpenUP FL 06/04/2011, issue #224
		}
		
		//	Picked						..  PK
		else if (docStatus.equals(DocumentEngine.STATUS_Picked)) {					// OpenUP FL 18/02/2011, isseue #301 
		//	options[index++] = DocumentEngine.ACTION_Prepare;
			options[index++] = DocumentEngine.ACTION_Complete;
			options[index++] = DocumentEngine.ACTION_Void;
			options[index++] = DocumentEngine.ACTION_Approve;						// OpenUP FL 15/02/2011, issue #224
			options[index++] = DocumentEngine.ACTION_Reject;						// OpenUP FL 15/02/2011, issue #224
		}

		//	In Process                  ..  IP
		else if (docStatus.equals(DocumentEngine.STATUS_InProgress)
			|| docStatus.equals(DocumentEngine.STATUS_Approved))
		{
			options[index++] = DocumentEngine.ACTION_Complete;
			options[index++] = DocumentEngine.ACTION_Void;
		}
		//	Complete                    ..  CO
		else if (docStatus.equals(DocumentEngine.STATUS_Completed))
		{
			options[index++] = DocumentEngine.ACTION_Void; // OpenUp. Gabriel Vila. 26/06/2012. Cuando esta completo podemos anular.
			options[index++] = DocumentEngine.ACTION_Close;
		}
		//	Waiting Payment
		else if (docStatus.equals(DocumentEngine.STATUS_WaitingPayment)
			|| docStatus.equals(DocumentEngine.STATUS_WaitingConfirmation))
		{
			options[index++] = DocumentEngine.ACTION_Void;
			options[index++] = DocumentEngine.ACTION_Prepare;
		}
		//	Closed, Voided, REversed    ..  CL/VO/RE
		else if (docStatus.equals(DocumentEngine.STATUS_Closed) 
			|| docStatus.equals(DocumentEngine.STATUS_Voided) 
			|| docStatus.equals(DocumentEngine.STATUS_Reversed))
			return 0;

		/********************
		 *  Order
		 */
		if (AD_Table_ID == MOrder.Table_ID)
		{
			// OpenUp. Gabriel Vila. 07/07/2011. Issue #792.
			// Seteo correctamente el flujo de estados de documentos posibles.
			// Comento anterior y pongo nuevo
			
			/*//	Draft                       ..  DR/IP/IN
			if (docStatus.equals(DocumentEngine.STATUS_Drafted)
				|| docStatus.equals(DocumentEngine.STATUS_InProgress)
				|| docStatus.equals(DocumentEngine.STATUS_Invalid))
			{
				options[index++] = DocumentEngine.ACTION_Prepare;
				options[index++] = DocumentEngine.ACTION_Close;
				//	Draft Sales Order Quote/Proposal - Process
				if ("Y".equals(isSOTrx)
					&& ("OB".equals(orderType) || "ON".equals(orderType)))
					docAction[0] = DocumentEngine.ACTION_Prepare;
			}
			//	Complete                    ..  CO
			else if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
				options[index++] = DocumentEngine.ACTION_ReActivate;
			}
			else if (docStatus.equals(DocumentEngine.STATUS_WaitingPayment))
			{
				options[index++] = DocumentEngine.ACTION_ReActivate;
				options[index++] = DocumentEngine.ACTION_Close;
			}*/
			
			/*
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				
				// OpenUp. Nicolas Sarlabos. 23/08/2011. Issue #780.
				//para permitir cerrar órdenes de compra
				MOrder mo = new MOrder(Env.getCtx(), Record_ID, null);
				MDocType doc = new MDocType(Env.getCtx(), mo.getC_DocType_ID(), null);
				if (doc.isSOTrx()){
					String [] sele={DocumentEngine.ACTION_Void, DocumentEngine.ACTION_ReActivate};
					return updateArray(options,sele);					
				}
				else{
					String [] sele={DocumentEngine.ACTION_Void, DocumentEngine.ACTION_ReActivate, DocumentEngine.ACTION_Close};
					return updateArray(options,sele);					
				}
				//Fin OpenUp. Issue #780
			}
			// Fin OpenUp. Issue #792
			 
			 */
			
			
			// OpenUp. Gabriel Vila. 22/02/2012. Issue #381.
			// Comento todo arriba y ahora contemplo ordenes de compra con cotizacion.
			MOrder order = new MOrder(Env.getCtx(), Record_ID, null);
			MDocType doc = new MDocType(Env.getCtx(), order.getC_DocTypeTarget_ID(), null);
			boolean defaultActions = true;
			if (doc.getValue() != null){
				if (doc.getValue().equalsIgnoreCase("poquote")){
					defaultActions = false;
					if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
							  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
							  || (docStatus.equals(DocumentEngine.STATUS_Invalid))
							  || (docStatus.equals(DocumentEngine.STATUS_NotApproved))){
						String [] sele={DocumentEngine.ACTION_Request};
								return updateArray(options,sele);
					}
					else if (docStatus.equals(DocumentEngine.STATUS_Requested)){
						String [] sele={DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Reject};
						return updateArray(options,sele);					
					}
					else if (docStatus.equals(DocumentEngine.STATUS_Approved)){
						String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);					
					}
					else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
						String [] sele={DocumentEngine.ACTION_Void};
						return updateArray(options,sele);					
					}
					else if (docStatus.equals(DocumentEngine.STATUS_Voided)){
						String [] sele={DocumentEngine.ACTION_None};
						return updateArray(options,sele);					
					}
					//OpenUp. Nicolas Sarlabos. 11/12/2013. #1588. Se agregan acciones para Orden de Compra Directa.	
				}else if (doc.getValue().equalsIgnoreCase("podirect")){
					defaultActions = false;
					if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
							|| (docStatus.equals(DocumentEngine.STATUS_InProgress))
							|| (docStatus.equals(DocumentEngine.STATUS_Invalid))){

						//String [] sele={DocumentEngine.ACTION_Request};
						String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);

					}
					else if (docStatus.equals(DocumentEngine.STATUS_Requested)){

						String [] sele={DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Reject};
						return updateArray(options,sele);

					}
					else if (docStatus.equals(DocumentEngine.STATUS_Approved)){

						String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);

					}					
					else if ((docStatus.equals(DocumentEngine.STATUS_Completed))
							|| (docStatus.equals(DocumentEngine.STATUS_NotApproved))){

						String [] sele={DocumentEngine.ACTION_Void};
						return updateArray(options,sele);

					}					
					
				}
				//Fin OpenUp.
			}
			if (defaultActions){
				if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
						  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
						  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
					String [] sele={DocumentEngine.ACTION_Complete};
							return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
					
					// OpenUp. Nicolas Sarlabos. 23/08/2011. Issue #780.
					//para permitir cerrar órdenes de compra
					if (doc.isSOTrx()){
						String [] sele={DocumentEngine.ACTION_Void, DocumentEngine.ACTION_ReActivate};
						return updateArray(options,sele);					
					}
					else{
						String [] sele={DocumentEngine.ACTION_Void, DocumentEngine.ACTION_ReActivate, DocumentEngine.ACTION_Close};
						return updateArray(options,sele);					
					}
				}
			}
			
			// Fin OpenUp. Issue #381. 

		}
		
		else if (AD_Table_ID == MRequisition.Table_ID)
		{
			// OpenUp. Gabriel Vila. 05/11/2012. Issue #92.
			// Comento y sustituyo codigo.
			
			//	Prepare
			//options[index++] = DocumentEngine.ACTION_Prepare;

			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))
					  || (docStatus.equals(DocumentEngine.STATUS_NotApproved))){
				String [] sele={DocumentEngine.ACTION_Request};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Requested)){
				String [] sele={DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Reject};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Approved)){
				String [] sele={DocumentEngine.ACTION_Complete};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Voided)){
				String [] sele={DocumentEngine.ACTION_None};
				return updateArray(options,sele);					
			}

			
			// Fin OpenUp. Issue #92. 
			
		}
		
		/********************
		 *  Shipment
		 */
		else if (AD_Table_ID == MInOut.Table_ID)
		{
			// OpenUp. Gabriel Vila. 07/07/2011. Issue #646.
			// Para notas de credito proveedor los estados de documento son :
			// Drafted--> Complete, Complete-->Void
			MInOut io = new MInOut(Env.getCtx(), Record_ID, null);
			MDocType doc = new MDocType(Env.getCtx(), io.getC_DocType_ID(), null);
			if ((doc.getDocBaseType().equalsIgnoreCase("MMS")) && (!doc.isSOTrx())){
				if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
				  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
				  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
					//OpenUp Nicolas Sarlabos 23/04/2012, se quitan acciones innecesarias
					String [] sele={DocumentEngine.ACTION_Complete};
					return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
					String [] sele={DocumentEngine.ACTION_Void};
					return updateArray(options,sele);
				}				
			}
			//Fin OpenUp Nicolas Sarlabos 23/04/2012
			// Fin Issue #646.
			
			// OpenUp - Raul Capecce - #5544 - 29/02/2016
			if ((doc.getValue().equalsIgnoreCase("remitocli"))){
				if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
				  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
				  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
					String [] sele={DocumentEngine.ACTION_Complete};
					return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
					String [] sele={DocumentEngine.ACTION_Void};
					return updateArray(options,sele);
				}				
			}
			// Fin #5544
			
			// OpenUp. Gabriel Vila. 05/03/2013. Issue #462.
			// Flujo de acciones para el documento de Recepcion de Stock
			if (doc.getValue() != null){
				if (doc.getValue().equalsIgnoreCase("stkreceipt")){
					if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
							|| (docStatus.equals(DocumentEngine.STATUS_InProgress))
							|| (docStatus.equals(DocumentEngine.STATUS_Invalid))){
								String [] sele={DocumentEngine.ACTION_Complete};
								return updateArray(options,sele);
					}
					else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
						String [] sele={DocumentEngine.ACTION_Void, DocumentEngine.ACTION_ReActivate};
						return updateArray(options,sele);
					}				
				}
			}			
			// Fin Issue #462
			
			//OpenUp Nicolas Sarlabos issue #859 03/10/2011
			//Estados de documento para Devoluciones Coordinadas de Clientes y Devoluciones Directas de Clientes
			if((doc.getDocBaseType().equalsIgnoreCase("MMR")) && (doc.isSOTrx())){
				if(doc.getDocSubTypeSO()!=null){
					if(doc.getDocSubTypeSO().equalsIgnoreCase("RM")){  //OpenUp Nicolas Sarlabos issue #996 09/04/2012
						if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
								  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
								  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

							
							// OpenUp. Gabriel Vila. 11/05/2012.
							// Devoluciones coordinadas requieren solicitud, devoluciones direectas no.
							// Distingo documentos por el tipo de contabilidad, esta es la unica diferencia que tienen.

							// Si es devolucion coordinada
							if (doc.getValue().equalsIgnoreCase("devolcoord")){ 
								String[] sele = { DocumentEngine.ACTION_Request };
								return updateArray(options,sele);
							}
							else if (doc.getValue().equalsIgnoreCase("devol")){
								String[] sele = { DocumentEngine.ACTION_Approve };
								return updateArray(options,sele);
							}
							else{
								String[] sele = { DocumentEngine.ACTION_Complete };
								return updateArray(options,sele);
							}
							
							//String[] sele = { DocumentEngine.ACTION_Complete};
							//return updateArray(options,sele);
							
						} else if (docStatus.equals(DocumentEngine.STATUS_Requested)) {
								
								String[] sele = { DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Void };
								return updateArray(options,sele);
								
						} else if (docStatus.equals(DocumentEngine.STATUS_Approved)) {
								
								String[] sele = { DocumentEngine.ACTION_Complete, DocumentEngine.ACTION_Void };
								return updateArray(options,sele);
								
						} else if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
							
								String[] sele = { DocumentEngine.ACTION_Void };
								return updateArray(options,sele);
							
						}
					}
												
				}
									
			}
			//fin OpenUp Nicolas Sarlabos issue #859 
			
			
			//	Complete                    ..  CO
			if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
				// OpenUp. Gabriel Vila. 07/07/2011. Issue #646
				// No permito que se haga inversa correccion de invoices.
				//options[index++] = DocumentEngine.ACTION_Reverse_Correct;
				// Fin OpenUp.
			}
		}
		/********************
		 *  Invoice
		 */
		else if (AD_Table_ID == MInvoice.Table_ID)
		{
			// OpenUp. Gabriel Vila. 07/07/2011. Issue #646.
			MInvoice inv = new MInvoice(Env.getCtx(), Record_ID, null);
			MDocType doc = new MDocType(Env.getCtx(), inv.getC_DocTypeTarget_ID(), null);
			String docSubTypeSO = (doc.getDocSubTypeSO() == null) ? "" : doc.getDocSubTypeSO();
			if (((doc.getDocBaseType().equalsIgnoreCase("ARC")) && (docSubTypeSO.equalsIgnoreCase("NP"))) || ((doc.getDocBaseType().equalsIgnoreCase("ARI")) && (docSubTypeSO.equalsIgnoreCase("FP")))){ // OpenUp M.R. 22-08-2011 Issue#755 Agrego validacion de factura de diferencia por apoyo
				if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
				  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
				  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
					String [] sele={DocumentEngine.ACTION_Request};
					return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Requested)){
					String [] sele={DocumentEngine.ACTION_Approve, DocumentEngine.ACTION_Reject};				
					return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Approved)){
					String [] sele={DocumentEngine.ACTION_Complete};				
					return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_NotApproved)){
					String [] sele={DocumentEngine.ACTION_Request};				
					return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
					String [] sele={DocumentEngine.ACTION_Void};
					return updateArray(options,sele);
				}
				
			//OpenUp. Nicolas Sarlabos. 26/11/2012. Se agregan acciones validas para Nota Credito Directa	
			} else if(doc.getValue().equalsIgnoreCase("customerncdir")) {
				
				if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
						  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
						  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
							String [] sele={DocumentEngine.ACTION_Complete};
							return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
					String [] sele={DocumentEngine.ACTION_Void};
					return updateArray(options,sele);
				}
				//OpenUp. Nicolas Sarlabos. 16/04/2013. #720. Se contempla el documento Devolucion Contado				
			} else if(doc.getValue().equalsIgnoreCase("remito") || doc.getValue().equalsIgnoreCase("devcontado")) {
				//Fin OpenUp. #720
				if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
						  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
						  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
							String [] sele={DocumentEngine.ACTION_Complete};
							return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
					String [] sele={DocumentEngine.ACTION_Void};
					return updateArray(options,sele);
				}				
			}
			//Fin OpenUp.
			//OpenUp. Nicolas Sarlabos. 07/01/2014. #1420. Agrego acciones para Factura Fondo Fijo
			else if(doc.getValue().equalsIgnoreCase("factfondofijo")) {
				if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
						|| (docStatus.equals(DocumentEngine.STATUS_InProgress))
						|| (docStatus.equals(DocumentEngine.STATUS_Invalid))){
					String [] sele={DocumentEngine.ACTION_Complete};
					return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
					String [] sele={DocumentEngine.ACTION_ReActivate};
					return updateArray(options,sele);

				}					
			}
			//Fin OpenUp.
			//OpenUp. Nicolas Sarlabos. 02/06/2014. #1623. Agrego acciones para Proforma Expediente
			else if(doc.getValue().equalsIgnoreCase("fileprofinvoice")) {
				if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
						|| (docStatus.equals(DocumentEngine.STATUS_InProgress))
						|| (docStatus.equals(DocumentEngine.STATUS_Invalid))){
					String [] sele={DocumentEngine.ACTION_Complete};
					return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
					String [] sele={DocumentEngine.ACTION_ReActivate, DocumentEngine.ACTION_Void};
					return updateArray(options,sele);

				}					
			}			
			//Fin OpenUp.
			//OpenUp. Gabriel Vila. 13/01/2014. #3205. Agrego acciones para Factura Vale Flete
			else if(doc.getValue().equalsIgnoreCase("inv_valeflete")) {
				if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
						|| (docStatus.equals(DocumentEngine.STATUS_InProgress))
						|| (docStatus.equals(DocumentEngine.STATUS_Invalid))){
					String [] sele={DocumentEngine.ACTION_Complete};
					return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
					String [] sele={DocumentEngine.ACTION_Void};
					return updateArray(options,sele);
				}					
			}
			//Fin OpenUp. Issue #3205
			//OpenUp. Gabriel Vila. 13/01/2014. #1405. Agrego acciones para documento Vale Flete
			else if(doc.getValue().equalsIgnoreCase("valeflete")) {
				if (docStatus.equals(DocumentEngine.STATUS_Drafted)){ 
					String [] sele={DocumentEngine.ACTION_Apply};
					return updateArray(options,sele);
				}
				else if ((docStatus.equals(DocumentEngine.STATUS_InProgress))
						|| (docStatus.equals(DocumentEngine.STATUS_Invalid))
						|| (docStatus.equals(DocumentEngine.STATUS_Applied))){

					String [] sele={DocumentEngine.ACTION_Complete};
					return updateArray(options,sele);
				}					
				else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
					String [] sele={DocumentEngine.ACTION_Void};
					return updateArray(options,sele);
				}					
			}
			//Fin OpenUp. Issue #3205
			
			else{
				if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
						  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
						  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
					String [] sele={DocumentEngine.ACTION_Complete};
							return updateArray(options,sele);
				}
				else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
					if (inv.isSOTrx()){
						String [] sele={DocumentEngine.ACTION_Void};
						return updateArray(options,sele);
					}
					else{
						String [] sele={DocumentEngine.ACTION_Void, DocumentEngine.ACTION_ReActivate};
						return updateArray(options,sele);
					}
				}				
			}
			// Fin OpenUP.
			
			//	Complete                    ..  CO
			if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
				
				// OpenUp. Gabriel Vila. 07/07/2011. Issue #646
				// No permito que se haga inversa correccion de invoices.

				//options[index++] = DocumentEngine.ACTION_Reverse_Correct;
				
				// Fin OpenUp.
			}
			
			//Ini OpenUp
			/**SBT 01/02/2015 Issue # 5402
			 * Facturación interna 
			 */
			if (AD_Table_ID == I_UY_RT_InternalInvoice.Table_ID)
			{
				if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) 
						  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
						  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){

					String [] sele={DocumentEngine.ACTION_Apply};
					return updateArray(options,sele);

				}
				else if (docStatus.equals(DocumentEngine.STATUS_Applied)){
					String [] sele={DocumentEngine.ACTION_Complete};
					return updateArray(options,sele);		
				}
			}
			//FIN OpenUp
			
			
		}
		/********************
		 *  Payment
		 */
		else if (AD_Table_ID == MPayment.Table_ID)
		{
			// OpenUp. Gabriel Vila. 05/08/2011. Issue #896
			// Cambio flujo de estados de documento.
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void, DocumentEngine.ACTION_ReActivate};
				return updateArray(options,sele);
			}
			
			//	Complete                    ..  CO
			/*if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				// OpenUp. Gabriel Vila. 25/10/2010.
				// Para recibos solo habilito la opcion reversion.
				
				//options[index++] = DocumentEngine.ACTION_Void;
				
				// Fin OpenUp.
				
				options[index++] = DocumentEngine.ACTION_Reverse_Correct;
			}*/
			
			// Fin Issue #896
			
		}
		/********************
		 *  GL Journal
		 */
		else if (AD_Table_ID == MJournal.Table_ID || AD_Table_ID == MJournalBatch.Table_ID)
		{
			
			// OpenUp. Gabriel Vila. 15/11/2012. Issue #29
			// Cambio flujo de estados de documento.
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void, DocumentEngine.ACTION_ReActivate};
				return updateArray(options,sele);
			}

			/*
			//	Complete                    ..  CO
			if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				//options[index++] = DocumentEngine.ACTION_Reverse_Correct; OpenUp MR 14-03-2011 Issue# 202 Elimino accion para evitar errores
				options[index++] = DocumentEngine.ACTION_Reverse_Accrual;
				options[index++] = DocumentEngine.ACTION_ReActivate;
			}
			*/
			// Fin Openup.
			
		}
		/********************
		 *  Allocation
		 */
		else if (AD_Table_ID == MAllocationHdr.Table_ID)
		{
			
			//	Complete                    ..  CO
			if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
				options[index++] = DocumentEngine.ACTION_Reverse_Correct;
			}
		}
		//[ 1782412 ]
		/********************
		 *  Cash
		 */
		else if (AD_Table_ID == MCash.Table_ID)
		{
			//	Complete                    ..  CO
			if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
			}
		}
		/********************
		 *  Bank Statement
		 */
		else if (AD_Table_ID == MBankStatement.Table_ID)
		{
			//	Complete                    ..  CO
			if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
			}
		}
		/********************
		 *  Inventory Movement, Physical Inventory
		 */
		else if (AD_Table_ID == MMovement.Table_ID
			|| AD_Table_ID == MInventory.Table_ID)
		{
			// OpenUp. Gabriel Vila. 05/08/2011. Issue #832
			// Cambio flujo de estados de documento.
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);
			}
			
			//	Complete                    ..  CO
			/*if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Void;
				options[index++] = DocumentEngine.ACTION_Reverse_Correct;
			}*/
			
			// Fin Issue #832.
		}
		/********************
		 *  Manufacturing Order
		 */
		//OpenUp Nicolas Garcia 17/06/2011 Issue#709
		else if (AD_Table_ID == I_PP_Order.Table_ID){
			// OpenUp. Nicolas Garcia. 01/08/2011. Issue #811.
			
			if (docStatus.equals(DocumentEngine.STATUS_Drafted) || docStatus.equals(DocumentEngine.ACTION_Prepare)) {
				
					String[] sele = { DocumentEngine.ACTION_Complete };
					return updateArray(options,sele);
					
			} else if (docStatus.equals(DocumentEngine.ACTION_Complete)) {
				
				String[] sele = { DocumentEngine.ACTION_Close, DocumentEngine.ACTION_ReActivate };
				return updateArray(options,sele);
				
			}
			// Fin Issue #811
		}
		//Fin issue 709

		// OpenUp. Nicolas Garcia. 07/10/2011. Issue #889.
		/********************
		* TODOS RECEUNTOS
		*/
		else if (AD_Table_ID == I_UY_RecuentoDef.Table_ID) {

			if (docStatus.equals(DocumentEngine.STATUS_Drafted)) {
				options[index++] = DocumentEngine.ACTION_Complete;
			} else if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
				options[index++] = DocumentEngine.ACTION_ReActivate;
				options[index++] = DocumentEngine.ACTION_Void;
			} else if (docStatus.equals(DocumentEngine.STATUS_InProgress)) {
				options[index++] = DocumentEngine.ACTION_Complete;
			}

			return index;
		}

		else if (AD_Table_ID == I_UY_RecuentoConf.Table_ID) {

			if (docStatus.equals(DocumentEngine.STATUS_Drafted)) {
				options[index++] = DocumentEngine.ACTION_Complete;
			} else if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
				options[index++] = DocumentEngine.ACTION_Void;
			}

			return index;
		}

		else if (AD_Table_ID == I_UY_RecuentoHdr.Table_ID) {

			if (docStatus.equals(DocumentEngine.STATUS_Drafted)) {
				options[index++] = DocumentEngine.ACTION_Complete;
			}
			return index;
		}
		// Fin Issue #889

		/********************
		 *  Manufacturing Cost Collector
		 */
		else if (AD_Table_ID == I_PP_Cost_Collector.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
				{
					options[index++] = DocumentEngine.ACTION_Prepare;
					options[index++] = DocumentEngine.ACTION_Close;
				}
				//	Complete                    ..  CO
				else if (docStatus.equals(DocumentEngine.STATUS_Completed))
				{
					options[index++] = DocumentEngine.ACTION_Void;
					options[index++] = DocumentEngine.ACTION_Reverse_Correct;
				}
		}
		/********************
		 *  Distribution Order
		 */
		else if (AD_Table_ID == I_DD_Order.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
				{

				
				
					options[index++] = DocumentEngine.ACTION_Prepare;
					options[index++] = DocumentEngine.ACTION_Close;
				}
				//	Complete                    ..  CO
				else if (docStatus.equals(DocumentEngine.STATUS_Completed))
				{
					options[index++] = DocumentEngine.ACTION_Void;
					options[index++] = DocumentEngine.ACTION_ReActivate;
				}
		}
		/********************
		 *  Payroll Process
		 */
		else if (AD_Table_ID == I_HR_Process.Table_ID)
		{
			if (docStatus.equals(DocumentEngine.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid))
				{
					options[index++] = DocumentEngine.ACTION_Prepare;
					options[index++] = DocumentEngine.ACTION_Close;
				}
				//	Complete                    ..  CO
				else if (docStatus.equals(DocumentEngine.STATUS_Completed))
				{
					options[index++] = DocumentEngine.ACTION_Reverse_Correct;
					options[index++] = DocumentEngine.ACTION_Void;
					options[index++] = DocumentEngine.ACTION_ReActivate;
				}
		}
		
		// OpenUp. Gabriel Vila. 01/11/2010.
		// Opciones de DocAction para bancos.
		else if (AD_Table_ID == I_UY_MovBancariosHdr.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);
			}
		}
//OpenUp M.R. 21-09-2011 Issue#721 Agrego acciones para ejecutar en el proceso de control de libretas de recibos
		else if (AD_Table_ID == I_UY_ReceiptBookControl.Table_ID) {
			if ((docStatus.equals(DocumentEngine.STATUS_Drafted)) || (docStatus.equals(DocumentEngine.STATUS_InProgress)) || (docStatus.equals(DocumentEngine.STATUS_Invalid))) {
				String[] sele = {DocumentEngine.ACTION_Prepare, DocumentEngine.ACTION_Complete};
				return updateArray(options, sele);

			} else if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
				String[] sele = {DocumentEngine.ACTION_None};
				return updateArray(options, sele);
			}
		}
		// Fin OpenUp.
		
		//OpenUp Nicolas Sarlabos 28/03/2012 #966 Agrego acciones para ejecutar en el proceso de control de libretas de cheques
		/*else if (AD_Table_ID == I_UY_CheckBookControl.Table_ID) {
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}

		}*/
				// Fin OpenUp Nicolas Sarlabos 28/03/2012 #966
		
		// OpenUp. Gabriel Vila. 08/11/2010.
		// Opciones de DocAction para emision de cheques.
		else if (AD_Table_ID == I_UY_MediosPago.Table_ID)
		{
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_ReActivate, DocumentEngine.ACTION_Void};
				return updateArray(options,sele);					
			}
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 06/01/2011.
		// Opciones de DocAction para reserva de pedidos.
		else if (AD_Table_ID == I_UY_ReservaPedidoHdr.Table_ID)
		{
			// OpenUp. Gabriel Vila. 05/08/2011. Issue #832
			// Cambio flujo de estados de documento.
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}
			else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
				String [] sele={DocumentEngine.ACTION_Void};
				return updateArray(options,sele);
			}

			/*if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Close;
				options[index++] = DocumentEngine.ACTION_Void;
			}*/
			
			// Fin Issue #832
		}
		// Fin OpenUp.

		
		// OpenUp FL 17/02/2011, issue #119, reverse confirm order
		else if (AD_Table_ID == I_UY_Confirmorderhdr.Table_ID) 
		{
			
			// OpenUp. Gabriel Vila. 05/08/2011. Issue #832
			// Cambio flujo de estados de documento.
			if ( (docStatus.equals(DocumentEngine.STATUS_Drafted)) 
					  || (docStatus.equals(DocumentEngine.STATUS_InProgress))
					  || (docStatus.equals(DocumentEngine.STATUS_Invalid))){
				String [] sele={DocumentEngine.ACTION_Complete};
						return updateArray(options,sele);
			}
			// OpenUp. Nicolas Garcia. 19/08/2011. Issue #824.
			// Se comenta codigo por lo requerido en el caso
			// else if (docStatus.equals(DocumentEngine.STATUS_Completed)){
			// String [] sele={DocumentEngine.ACTION_Void};
			// return updateArray(options,sele);
			// }

			// Fin Issue #824
			/*if (docStatus.equals(DocumentEngine.STATUS_Completed))
			{
				options[index++] = DocumentEngine.ACTION_Reverse_Correct;
			}*/
		}
		
		
		return index;
	}
	
	/**
	 * Fill Vector with DocAction Ref_List(135) values
	 * @param v_value
	 * @param v_name
	 * @param v_description
	 */
	public static void readReferenceList(ArrayList<String> v_value, ArrayList<String> v_name,
			ArrayList<String> v_description)
	{
		if (v_value == null) 
			throw new IllegalArgumentException("v_value parameter is null");
		if (v_name == null)
			throw new IllegalArgumentException("v_name parameter is null");
		if (v_description == null)
			throw new IllegalArgumentException("v_description parameter is null");
		
		String sql;
		if (Env.isBaseLanguage(Env.getCtx(), "AD_Ref_List"))
			sql = "SELECT Value, Name, Description FROM AD_Ref_List "
				+ "WHERE AD_Reference_ID=? ORDER BY Name";
		else
			sql = "SELECT l.Value, t.Name, t.Description "
				+ "FROM AD_Ref_List l, AD_Ref_List_Trl t "
				+ "WHERE l.AD_Ref_List_ID=t.AD_Ref_List_ID"
				+ " AND t.AD_Language='" + Env.getAD_Language(Env.getCtx()) + "'"
				+ " AND l.AD_Reference_ID=? ORDER BY t.Name";

		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, DocAction.AD_REFERENCE_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				String value = rs.getString(1);
				String name = rs.getString(2);
				String description = rs.getString(3);
				if (description == null)
					description = "";
				//
				v_value.add(value);
				v_name.add(name);
				v_description.add(description);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}

	/**
	 * Checks the access rights of the given role/client for the given document actions.
	 * @param clientId
	 * @param roleId
	 * @param docTypeId
	 * @param options
	 * @param maxIndex
	 * @return number of valid actions in the String[] options
	 */
	public static int checkActionAccess(int clientId, int roleId, int docTypeId, String[] options, int maxIndex) {
		return MRole.get(Env.getCtx(), roleId).checkActionAccess(clientId, docTypeId, options, maxIndex);
	}
	
	/**
	 *  Post Immediate
	 *
	 *	@param	ctx Client Context
	 *  @param  AD_Client_ID    Client ID of Document
	 *  @param  AD_Table_ID     Table ID of Document
	 *  @param  Record_ID       Record ID of this document
	 *  @param  force           force posting
	 *  @param  trxName			ignore, retained for backward compatibility
	 *  @return null, if success or error message
	 */
	public static String postImmediate (Properties ctx, 
		int AD_Client_ID, int AD_Table_ID, int Record_ID, boolean force, String trxName)
	{
		// Ensure the table has Posted column / i.e. GL_JournalBatch can be completed but not posted
		if (MColumn.getColumn_ID(MTable.getTableName(ctx, AD_Table_ID), "Posted") <= 0)
			return null;
			
		String error = null;
		if (MClient.isClientAccounting()) {
			log.info ("Table=" + AD_Table_ID + ", Record=" + Record_ID);
			MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(ctx, AD_Client_ID);
			error = Doc.postImmediate(ass, AD_Table_ID, Record_ID, force, trxName);
			return error;
		}
		
		//  try to get from Server when enabled
		if (CConnection.get().isAppsServerOK(true))
		{
			log.config("trying server");
			try
			{
				Server server = CConnection.get().getServer();
				if (server != null)
				{
					Properties p = Env.getRemoteCallCtx(Env.getCtx());
					error = server.postImmediate(p, AD_Client_ID,
						AD_Table_ID, Record_ID, force, null); // don't pass transaction to server
					log.config("from Server: " + error== null ? "OK" : error);
				}
				else
				{
					error = "NoAppsServer";
				}
			}
			catch (Exception e)
			{
				log.log(Level.WARNING, "(RE)", e);
				error = e.getMessage();
			}
		}
		
		return error;
	}	//	postImmediate
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo creado para actualizar guardar la info de un array a a un array b.
	 * Si b es de mayor tamaï¿½o deja el resto de los campos en null. el metodo devuelve un int que es el tamaï¿½o del array.
	 * @param aModificar
	 * @param datos
	 * @return
	 * @author  Nicolas Garcia.
	 * Fecha : 17/06/2011
	 */
	private static int updateArray(Object[] aModificar, Object[]datos){
		
		
		for(int i=0;aModificar.length>i;i++){			
			if(i<datos.length){
				aModificar[i]=datos[i];
			}else{
				aModificar[i]=null;
			}			
		}
		return datos.length;
		
			
		}
	
	
}	//	DocumentEnine
