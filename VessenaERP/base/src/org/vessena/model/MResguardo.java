/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 14/11/2012
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactLine;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.cfe.CFEConverter;
import org.openup.cfe.CFEResponse;
import org.openup.cfe.InterfaceCFEDTO;
import org.openup.cfe.coverter.MResguardoCFEConverter;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;

/**
 * org.openup.model - MResguardo
 * OpenUp Ltda. Issue #100 
 * Description: 
 * @author Gabriel Vila - 14/11/2012
 * @see
 */
public class MResguardo extends X_UY_Resguardo implements DocAction, InterfaceCFEDTO {

	private static final long serialVersionUID = -9186701417291577607L;
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Resguardo_ID
	 * @param trxName
	 */
	public MResguardo(Properties ctx, int UY_Resguardo_ID, String trxName) {
		super(ctx, UY_Resguardo_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MResguardo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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

		// Verifico periodo contable para documento de afectacion
		MPeriod.testPeriodOpen(getCtx(), this.getDateAcct(), this.getC_DocType_ID(), this.getAD_Org_ID());
		
		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Si no tengo importe de resguardo no hago nada
		if (this.getPayAmt().compareTo(Env.ZERO) == 0){
			this.processMsg = "No hay Resguardos para Generar.";
			return DocAction.STATUS_Invalid;
		}
		
		// Verifico comprobantes y los voy seteando con resguardo
		if (!this.validateInvoices()){
			return DocAction.STATUS_Invalid;
		}
		
		this.setDateAcct(this.getDateTrx());
		
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
	 * Valido estado de comprobantes y los seteo con resguardo.
	 * OpenUp Ltda. Issue #100 
	 * @author Gabriel Vila - 21/11/2012
	 * @see
	 * @return
	 */
	private boolean validateInvoices() {

		// Obtengo comprobantes
		List<MResguardoInvoice> resginvs = this.getInvoices();
		
		if (resginvs.size() <= 0){
			this.processMsg = "No tengo comprobantes asociados a este Resguardo.";
			return false;
		}
		
		// Obtengo lineas de resguardo con sus retenciones
		//List<MResguardoLine> lines = this.getLines();
		
		for (MResguardoInvoice resginv: resginvs){
			// Valido comprobante completo y sin resguardos
			MInvoice invoice = new MInvoice(getCtx(), resginv.getC_Invoice_ID(), get_TrxName());
			if (!invoice.getDocStatus().equalsIgnoreCase(DOCSTATUS_Completed)){
				this.processMsg = "El comprobante numero : " + invoice.getDocumentNo() + " no esta en estado completo.";
				return false;
			}
			
			if (invoice.getUY_Resguardo_ID() > 0){
				MResguardo resgAux = new MResguardo(getCtx(), invoice.getUY_Resguardo_ID(), null);
				this.processMsg = "El comprobante numero : " + invoice.getDocumentNo() + " esta asociado a otro Resguardo (Numero : " + resgAux.getDocumentNo() + ")";
				return false;
			}
			
			// Todo bien, asocio resguardo a este comprobante
			invoice.setUY_Resguardo_ID(this.get_ID());
			
			/* Por ahora esto no va, pero no se si despues
			// Obtengo y guardo monto de retenciones aplicadas a esta factura
			BigDecimal amtRetentions = Env.ZERO, amtDocument = invoice.getGrandTotal();
			for (MResguardoLine line: lines){
				MRetention retention = (MRetention)line.getUY_Retention();
				amtRetentions = amtRetentions.add(amtDocument.multiply(retention.getConceptPorc().setScale(2, RoundingMode.HALF_UP)));
			}
			invoice.setAmtRetention(amtRetentions);
			*/
			
			invoice.saveEx();
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 14/11/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		// Test period
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		
		// Elimino asientos contables de este documento
		FactLine.deleteFact(I_UY_Resguardo.Table_ID, this.get_ID(), get_TrxName());
		
		// Quito asociacion de resguardo con sus comprobantes
		List<MResguardoInvoice> resginvs = this.getInvoices();
		for (MResguardoInvoice resginv: resginvs){
			DB.executeUpdateEx("UPDATE C_Invoice SET UY_Resguardo_ID = null WHERE c_invoice_id =" + resginv.getC_Invoice_ID(), get_TrxName());
		}

		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setPosted(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
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
	 * @author Hp - 14/11/2012
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/***
	 * Obtiene y retorna array de lineas de este modelo.
	 * OpenUp Ltda. Issue #100
	 * @author Gabriel vila - 14/11/2012
	 * @see
	 * @return
	 */
	public List<MResguardoLine> getLines() {

		String whereClause = X_UY_ResguardoLine.COLUMNNAME_UY_Resguardo_ID + "=" + this.get_ID();
		List<MResguardoLine> lines = new Query(getCtx(), I_UY_ResguardoLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}

	/***
	 * Obtiene y retorna array de invoices que participan de este resguardo.
	 * OpenUp Ltda. Issue #100 
	 * @author Gabriel Vila - 21/11/2012
	 * @see
	 * @return
	 */
	public List<MResguardoInvoice> getInvoices() {

		String whereClause = X_UY_ResguardoInvoice.COLUMNNAME_UY_Resguardo_ID + "=" + this.get_ID();
		List<MResguardoInvoice> lines = new Query(getCtx(), I_UY_ResguardoInvoice.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}

	/***
	 * Elimina lineas de este resguardo.
	 * OpenUp Ltda. Issue #100 
	 * @author Gabriel Vila - 21/11/2012
	 * @see
	 */
	public void deleteLines(){

		try{
			
			String action = " DELETE FROM uy_resguardoline " +
					        " where uy_resguardo_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/***
	 * Obtiene y retorna modelo de linea de resguardo para una determinada retencion de un determinado resguardo.
	 * OpenUp Ltda. Issue #100 
	 * @author Gabriel Vila - 23/11/2012
	 * @see
	 * @param ctx
	 * @param uy_Resguardo_ID
	 * @param uy_Retention_ID
	 * @param object
	 * @return
	 */
	public static MResguardoLine getLineForRetention(Properties ctx, int uyResguardoID, int uyRetentionID, String trxName) {

		String whereClause = X_UY_ResguardoLine.COLUMNNAME_UY_Resguardo_ID + "=" + uyResguardoID + 
						" AND " + X_UY_ResguardoLine.COLUMNNAME_UY_Retention_ID + "=" + uyRetentionID;
		MResguardoLine value = new Query(ctx, I_UY_ResguardoLine.Table_Name, whereClause, trxName)
		.first();
		
		return value;
	}
	

	// OpenUp Ltda - INICIO - INTEFACE CON MÓDULO DE FACTURACIÓN ELECTRÓNICA
	// Raul Capecce. 06/04/2016. ISSUE #5729

	@Override
	public CFEDefType getCFEDTO() {
		CFEConverter cfeConverter = new MResguardoCFEConverter(getCtx(), this, get_TrxName());
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
