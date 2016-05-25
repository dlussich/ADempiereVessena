/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/06/2012
 */
 
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * org.openup.model - MRFQRequisition
 * OpenUp Ltda. Issue #1034 
 * Description: Cabezal de solicitud de cotizacion a proveedor. 
 * @author Gabriel Vila - 20/06/2012
 * @see
 */
public class MRFQRequisition extends X_UY_RFQ_Requisition implements DocAction {

	private static final long serialVersionUID = 5365831603692548595L;
	private String processMsg = null;
	private boolean justPrepared = false;
	

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RFQ_Requisition_ID
	 * @param trxName
	 */
	public MRFQRequisition(Properties ctx, int UY_RFQ_Requisition_ID,
			String trxName) {
		super(ctx, UY_RFQ_Requisition_ID, trxName);

		if (UY_RFQ_Requisition_ID <= 0){
			this.setDateTrx(new Timestamp(System.currentTimeMillis()));
		}
		
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRFQRequisition(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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

		// Si tengo generacion asociada, genero tracking por sector de compra
		if (this.getUY_RFQGen_Filter_ID() > 0){
			this.generateSectionTracking();
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
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
	 * @author Hp - 20/06/2012
	 * @see
	 * @return
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Obtiene y retorna lineas de esta transaccion.
	 * OpenUp Ltda. Issue #1031 
	 * @author Gabriel Vila. - 11/07/2012
	 * @see
	 * @param trxName
	 * @return
	 */
	public List<MRFQRequisitionLine> getLines(String trxName) {

		String whereClause = "uy_rfq_requisition_id = " + this.get_ID();
		
		List<MRFQRequisitionLine> lines = new Query(getCtx(), I_UY_RFQ_RequisitionLine.Table_Name, whereClause, trxName)
									   .list();
		
		return lines;
	}

	/***
	 * Genera una cotizacion a proveedor en estado borrador asociada a esta solicitud.
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 12/12/2012
	 * @see
	 */
	public void generateQuoteVendor() {
		
		try{
			
			MBPartner vendor = (MBPartner)this.getC_BPartner();
			
			MQuoteVendor quote = new MQuoteVendor(getCtx(), 0, get_TrxName());
			quote.setC_DocType_ID(MDocType.forValue(getCtx(), "quotevendor", null).get_ID());
			quote.setDateTrx(this.getDateTrx());
			quote.setUY_RFQ_Requisition_ID(this.get_ID());
			quote.setC_BPartner_ID(this.getC_BPartner_ID());
			quote.setUY_POPolicy_ID(this.getUY_POPolicy_ID());
			quote.setUY_RFQ_MacroReq_ID(this.getUY_RFQ_MacroReq_ID());
			quote.setUY_RFQ_MacroReqLine_ID(this.getUY_RFQ_MacroReqLine_ID());
			
			if (vendor.getC_PaymentTerm_ID() > 0) 
				quote.setC_PaymentTerm_ID(vendor.getPO_PaymentTerm_ID());

			quote.setC_Currency_ID(this.getC_Currency_ID());
			
			if (quote.getC_PaymentTerm_ID() > 0){
				quote.setpaymentruletype(quote.getC_PaymentTerm().getUY_PaymentRule().getpaymentruletype());
			}
			else{
				quote.setpaymentruletype(X_UY_PaymentRule.PAYMENTRULETYPE_CREDITO);	
			}
			
			// Plazo para resupuesta sera igual a la Maxima fecha requerida de uno de los productos de la solicitud de cotizacion
			Timestamp dueDate = this.getMaxDateRequired();
			if (dueDate == null) dueDate = new Timestamp(System.currentTimeMillis());
			quote.setEffectiveDate(dueDate);
			quote.setDocAction(DOCACTION_Request);
			quote.saveEx();
			
			this.setUY_QuoteVendor_ID(quote.get_ID());
			this.saveEx();
			
			MRFQMacroReqLine macroreqline = (MRFQMacroReqLine)this.getUY_RFQ_MacroReqLine();
			if (macroreqline != null){
				macroreqline.setUY_QuoteVendor_ID(quote.get_ID());
				macroreqline.saveEx();
			}
			
			// Envio de email al proveedor.
			//quote.sendEmail(true);
			
			// Las Lineas de la cotizacion se generan automaticamente en el aftersave de la MQuoteVendor
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Obtiene maxima fecha requerida de las lineas de esta solicitud.
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 12/12/2012
	 * @see
	 * @return
	 */
	private Timestamp getMaxDateRequired() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		Timestamp value = null;

		try{
		
			sql = " select max(daterequired) " +
				  " from uy_rfq_requisitionline " +
				  " where uy_rfq_requisition_id =? ";

			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, this.get_ID());
			
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				value = rs.getTimestamp(1);
			}
		}
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return value;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Genera lineas de seguimiento por Sector de Compra para la generacion padre
	 * de esta solicitud.
	 * OpenUp Ltda. Issue #379 
	 * @author Gabriel Vila - 21/02/2013
	 * @see
	 */
	private void generateSectionTracking(){		

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		
		try{
			
			sql = " select req.*, prod.m_product_id " +
				  " from uy_rfqgen_req req " +
				  " inner join uy_rfqgen_prod prod on req.uy_rfqgen_prod_id = prod.uy_rfqgen_prod_id " +
				  " inner join m_requisition poreq on req.m_requisition_id = poreq.m_requisition_id " +
				  " where req.isselected='Y' " +
				  " and prod.uy_rfqgen_filter_id =? " +
				  " and prod.m_product_id in " +
				     " (select m_product_id from uy_rfq_requisitionline " +
				     "  where uy_rfq_requisition_id =?) " +
				  " order by poreq.uy_posection_id, prod.m_product_id ";  
			
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, this.getUY_RFQGen_Filter_ID());
			pstmt.setInt(2, this.get_ID());

			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				// Nueva linea de seguimiento por sector
				MRFQRequisitionSection line = new MRFQRequisitionSection(getCtx(), 0, get_TrxName());
				line.setUY_RFQ_Requisition_ID(this.get_ID());
				line.setM_Requisition_ID(rs.getInt("m_requisition_id"));
				line.setM_RequisitionLine_ID(rs.getInt("m_requisitionline_id"));
				line.setM_Product_ID(rs.getInt("m_product_id"));
				line.setM_AttributeSetInstance_ID(0);

				MRequisition requi = (MRequisition)line.getM_Requisition();
				MRequisitionLine reqline = (MRequisitionLine)line.getM_RequisitionLine(); 

				line.setUY_POSection_ID(requi.getUY_POSection_ID());
				line.setDescription(reqline.getDescription());
				line.setC_UOM_ID(rs.getInt("c_uom_id"));
				line.setQtyRequired(rs.getBigDecimal("qtyrequired"));
				line.setLineNetAmt(reqline.getLineNetAmt());
				line.setDateRequired(rs.getTimestamp("daterequired"));
				line.saveEx();
				
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	public List<MRFQRequisitionSection> getSections() {

		String whereClause = "uy_rfq_requisition_id = " + this.get_ID();
		
		List<MRFQRequisitionSection> lines = new Query(getCtx(), I_UY_RFQ_RequisitionSection.Table_Name, whereClause, get_TrxName())
									   .list();
		
		return lines;
	}
	
}
