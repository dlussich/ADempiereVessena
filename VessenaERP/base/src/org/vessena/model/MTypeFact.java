/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 20/11/2014
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
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MTypeFact
 * OpenUp Ltda. Issue #3315 
 * Description: Modelo de cabezal de documento de Registracion de Asiento Tipo
 * @author Gabriel Vila - 20/11/2014
 * @see
 */
public class MTypeFact extends X_UY_TypeFact implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;

	private static final long serialVersionUID = -4137246144142811920L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TypeFact_ID
	 * @param trxName
	 */
	public MTypeFact(Properties ctx, int UY_TypeFact_ID, String trxName) {
		super(ctx, UY_TypeFact_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTypeFact(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
	 * @see
	 * @return
	 */
	@Override
	public String completeIt() {

		//Re-Check
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		// Verifico periodo contable para documento
		MPeriod.testPeriodOpen(getCtx(), this.getDateTrx(), this.getC_DocType_ID(), this.getAD_Org_ID());
		
		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		this.setDateAcct(this.getDateTrx());
		
		// Actualizo totales por socio de negocio
		this.updatePartnerTotals();
		
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
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
	 * @author Gabriel Vila - 20/11/2014
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/***
	 * Obtiene y retorna modelo de linea asociada a este documento y para un determinado codifo de fila. 
	 * OpenUp Ltda. Issue #3315 
	 * @author Gabriel Vila - 24/11/2014
	 * @see
	 * @param trim
	 * @return
	 */
	public MTypeFactLine getLineForValue(String value) {
	
		String whereClause = X_UY_TypeFactLine.COLUMNNAME_UY_TypeFact_ID + "=" + this.get_ID() +
				" and " + X_UY_TypeFactLine.COLUMNNAME_Value + "='" + value + "'";
				
		MTypeFactLine model = new Query(getCtx(), I_UY_TypeFactLine.Table_Name, whereClause, get_TrxName()).first(); 
		
		return model;
		
	}

	/***
	 * Obtiene y retorna modelo de linea asociada a este documento y para un codigo de fila y socio de negocio 
	 * OpenUp Ltda. Issue #3315 
	 * @author Gabriel Vila - 24/11/2014
	 * @see
	 * @param trim
	 * @return
	 */
	public MTypeFactLine getLineForValueBP(String value, int cBPartnerID) {
	
		String whereClause = X_UY_TypeFactLine.COLUMNNAME_UY_TypeFact_ID + "=" + this.get_ID() +
				" and " + X_UY_TypeFactLine.COLUMNNAME_Value + "='" + value + "'" +
				" and " + X_UY_TypeFactLine.COLUMNNAME_C_BPartner_ID + "=" + cBPartnerID;
				
		MTypeFactLine model = new Query(getCtx(), I_UY_TypeFactLine.Table_Name, whereClause, get_TrxName()).first(); 
		
		return model;
		
	}

	
	/***
	 * Obtiene y retorna lineas de este documento. 
	 * OpenUp Ltda. Issue #3315 
	 * @author Gabriel Vila - 24/11/2014
	 * @see
	 * @return
	 */
	public List<MTypeFactLine> getLines() {

		String whereClause = X_UY_TypeFactLine.COLUMNNAME_UY_TypeFact_ID + "=" + this.get_ID();
			
		List<MTypeFactLine> lines = new Query(getCtx(), I_UY_TypeFactLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}

	
	/***
	 * Actualiza totales por socio de negocio
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 02/06/2015
	 * @see
	 */
	private void updatePartnerTotals(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = " select c_bpartner_id, coalesce(sum(amt),0) as monto " +
				  " from uy_typefactline " +
				  " where uy_typefact_id =? " +
				  " and isdebit='Y' " +
				  " and c_bpartner_id > 0 " +
				  " group by c_bpartner_id ";
				
				pstmt = DB.prepareStatement (sql, get_TrxName());
				pstmt.setInt(1, this.get_ID());
				
				rs = pstmt.executeQuery ();
			
				while (rs.next()){
					MTypeFactBP factBP = new MTypeFactBP(getCtx(), 0, get_TrxName());
					factBP.setUY_TypeFact_ID(this.get_ID());
					factBP.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
					factBP.setAmt(rs.getBigDecimal("monto"));
					factBP.saveEx();
				}

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
		
	}
	
	/***
	 * Obtiene saldo pendiente de un determinado cliente de este asiento tipo
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 02/06/2015
	 * @see
	 * @param cBPartnerID
	 * @return
	 */
	public BigDecimal getAmtOpen(int cBPartnerID) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);

		try {
			sql = " select amtopen " + 
		          " from alloc_invoiceamtopen " +
				  " where c_invoice_id =? " +
				  " and c_bpartner_id =? "; 

			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, this.get_ID());
			pstmt.setInt(2, cBPartnerID);
			rs = pstmt.executeQuery();

			if (rs.next())
				value = rs.getBigDecimal(1);
			else
				value = Env.ZERO;

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return value;
	}

	
}
