/**
 * @author OpenUp SBT Issue#  1/2/2016 11:01:37
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author OpenUp SBT Issue #5402  1/2/2016 11:01:37
 *
 */
public class MRTInternalInvoice extends X_UY_RT_InternalInvoice implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	/**
	 * @author OpenUp SBT Issue#  1/2/2016 11:02:07
	 * @param ctx
	 * @param UY_RT_InternalInvoiceLine_ID
	 * @param trxName
	 */
	public MRTInternalInvoice(Properties ctx,
			int UY_RT_InternalInvoiceLine_ID, String trxName) {
		super(ctx, UY_RT_InternalInvoiceLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author OpenUp SBT Issue#  1/2/2016 11:02:07
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTInternalInvoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
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
		// Si existe cbpartner_id obtener los remitos de esta org para la org de destino
		if(this.getC_BPartner_ID()>0 && this.getAD_Org_ID()>0){
			
			int cant = this.createDeliveryLines();
			if(cant>0){
				this.setDocStatus(DocumentEngine.STATUS_Applied);
				this.setDocAction(DocAction.ACTION_Complete);
				return true;
			}
		}
		return false;
	}

	private int createDeliveryLines() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int cant = 0;
		try {
			sql = " Select * " +
				  " from UY_RT_InternalDelivery " +
				  " where AD_Org_ID = " + this.getAD_Org_ID() +
				  " and C_BPartner_ID = " + this.getC_BPartner_ID() +
				  " and IsActive = 'Y' " + 
				  " AND DocStatus = 'CO' ";

			pstmt = DB.prepareStatement(sql, null); // Create the statement
			rs = pstmt.executeQuery();

			// Read the first record and get a new object
			MRTInternalInvoiceLine line = null;
			if (rs.next()) {
				line = new MRTInternalInvoiceLine(getCtx(), 0, get_TrxName());
				line.setUY_RT_InternalInvoice_ID(this.get_ID());
				line.setUY_RT_InternalDelivery_ID(rs.getInt("UY_RT_InternalDelivery_ID"));
				line.setamtdocument(rs.getBigDecimal("grandtotal"));
				line.setamtallocated(Env.ZERO);
				line.setamtopen(rs.getBigDecimal("grandtotal"));
				line.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
				line.setM_Warehouse_ID(rs.getInt("M_Warehouse_ID"));
				line.setAD_Org_ID_To(rs.getInt("AD_Org_ID_To"));
				line.saveEx();
				cant++;
			}

		} catch (Exception e) {
			throw new AdempiereException(e.toString());
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return cant;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
//		Re-Check
			if (!this.justPrepared)
			{
				String status = prepareIt();
				if (!DocAction.STATUS_InProgress.equals(status))
					return status;
			}
			if(this.getLines()==null || this.getLines().size()==0)throw new AdempiereException("No hay líneas para procesar");
			if(null==this.getnumeroticket() || !(0<this.getnumeroticket().length()))throw new AdempiereException("Ingrese número de ticket");
						
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

	private List<MRTInternalInvoiceLine> getLines() {
		// TODO Auto-generated method stub
		return MRTInternalInvoiceLine.forInternalInvoiceID(getCtx(),this.get_ID(),get_TrxName());
	}

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
	public String getDocumentNo() {
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

	@Override
	public String getDocAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
