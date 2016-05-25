/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 05/05/2014
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.openup.cfe.CFEConverter;
import org.openup.cfe.CFEManager;
import org.openup.cfe.CFEResponse;
import org.openup.cfe.InterfaceCFEDTO;
import org.openup.cfe.coverter.MInOutCFEConverter;
import org.openup.cfe.coverter.MTRRemitoCFEConverter;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;

/**
 * org.openup.model - MTRRemito
 * OpenUp Ltda. Issue #1626 
 * Description: Modelo para documento de Remito de Mercaderias en modulo de transporte.
 * @author Gabriel Vila - 05/05/2014
 * @see
 */
public class MTRRemito extends X_UY_TR_Remito implements DocAction, InterfaceCFEDTO {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = 6676910385880104506L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_Remito_ID
	 * @param trxName
	 */
	public MTRRemito(Properties ctx, int UY_TR_Remito_ID, String trxName) {
		super(ctx, UY_TR_Remito_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRRemito(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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

		
		
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		setDefiniteDocumentNo();
		
		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		
		/*
		 * OpenUp. Raul Capecce. 29/03/2016. ISSUE #5678
		 * Modulo de Facturacion Electronica
		 */
		CFEManager cfeManager = new CFEManager(getAD_Client_ID(), getCtx(), get_TrxName());
		cfeManager.addCFE(this);
		cfeManager.SendCFE();
		// FIN OpenUp. Raul Capecce. 29/03/2016. ISSUE #5678
		
		
		return DocAction.STATUS_Completed;
		
	}
	
	/***
	 * Obtiene y retorna un remito para un expediente y orden de transporte.
	 * OpenUp Ltda. Issue #3044 
	 * @author Nicolas Sarlabos - 01/10/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRRemito forFileTransOrder(Properties ctx, int tripID, int orderID, String trxName){
		
		String whereClause = X_UY_TR_Remito.COLUMNNAME_UY_TR_Trip_ID + "=" + tripID + " and " + X_UY_TR_Remito.COLUMNNAME_UY_TR_TransOrder_ID + "=" + orderID;
		
		MTRRemito rem = new Query(ctx, I_UY_TR_Remito.Table_Name, whereClause, trxName)
		.first();
				
		return rem;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
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
	 * @author Gabriel Vila - 05/05/2014
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateTrx(new Timestamp(System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(),
					true, this);
			if (value != null)
				setDocumentNo(value);
		}
	}
	
	
	// OpenUp Ltda - INICIO - INTEFACE CON MÓDULO DE FACTURACIÓN ELECTRÓNICA
	// Raul Capecce. 29/03/2016. ISSUE #5678

	@Override
	public CFEDefType getCFEDTO() {
		CFEConverter cfeConverter = new MTRRemitoCFEConverter(getCtx(), this, get_TrxName());
		cfeConverter.loadCFE();
		return cfeConverter.getObjCFE();
	}

	@Override
	public void onSendCFEResponse(CFEResponse cfeResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVoidCFEResponse(CFEResponse cfeResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onQueryCFEResponse(CFEResponse cfeResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDocumentoElectronico() {
		
		MDocType docType = (MDocType) this.getC_DocType();
		boolean isCfe = docType.get_ValueAsBoolean("isCFE");
		
		return isCfe;
	}

	@Override
	public void setDataEnvelope(MCFEDataEnvelope mCfeDataEnvelope) {
		
		set_Value("UY_CFE_DataEnvelope_ID", mCfeDataEnvelope.get_ID());
		this.saveEx();
		
	}
	
	// OpenUp Ltda - FIN - INTEFACE CON MÓDULO DE FACTURACIÓN ELECTRÓNICA
	


}
