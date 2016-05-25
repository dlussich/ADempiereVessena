package org.openup.model;

/* (non-Javadoc)
 * @see org.compiere.process.DocAction#ApplyIt()
 * OpenUp Ltda. Issue # 
 * @author Andrea Odriozola - 29/07/2015
 * @see
 * @return
 */

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/***
 * Issue #4597
 * @author gabriel
 *
 */
public class MDocApproval extends X_UY_DocApproval implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	
	private static final long serialVersionUID = 1414391796839488021L;

	
	public MDocApproval(Properties ctx, int UY_DocApproval_ID, String trxName) {
		super(ctx, UY_DocApproval_ID, trxName);
	}

	public MDocApproval(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		// Todo bien		
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		boolean areLines = false;
		
		try {

			// Obtener y cargar lineas correspondientes a facturas de proveedor bloqueadas para pago
			
			sql = "select  c_doctypetarget_id, c_invoice_id, dateinvoiced " +
					" from c_invoice" +
					" where issotrx='N'" +
					" and docstatus='CO'" +
					" and isindispute='Y'";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
	
			
			while (rs.next()){
				areLines= true;
				MDocApprovalLine line = new MDocApprovalLine(getCtx(), 0, get_TrxName());				
				line.setUY_DocApproval_ID(this.getUY_DocApproval_ID());
				line.setC_DocType_ID(rs.getInt("c_doctypetarget_id"));
				line.setC_Invoice_ID(rs.getInt("c_invoice_id"));
				line.setDateInvoiced(rs.getTimestamp("dateinvoiced"));
				
			
				line.saveEx();
			}
			
			if (!areLines){
				throw new AdempiereException("No hay facturas bloqueadas actualmente");
			}
			else {
				
			this.setDocStatus(DocumentEngine.STATUS_Applied);
			this.setDocAction(DocAction.ACTION_Complete);
			}
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

		return true;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

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
		//traemos las lineas por el id del cabezal
		String whereClause = X_UY_DocApprovalLine.COLUMNNAME_UY_DocApproval_ID + "=" + this.get_ID();
		List<MDocApprovalLine> lines = new Query(getCtx(), I_UY_DocApprovalLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		// Por cada factura
		for (MDocApprovalLine line : lines) {			
		
			if(	line.isSelected()){				
	
				MInvoice model = new MInvoice(getCtx(), line.getC_Invoice_ID(), get_TrxName());
				model.setIsInDispute(false);
				model.saveEx();		
		}
		}
		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			
			return DocAction.STATUS_Invalid;

		
		
		
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

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
