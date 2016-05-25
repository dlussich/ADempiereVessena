/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Feb 1, 2016
*/
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * org.openup.model - MRTInternalDelivery
 * OpenUp Ltda. Issue # 
 * Description: Modelo para remito interno en retail. 
 * @author gabriel - Feb 1, 2016
*/
public class MRTInternalDelivery extends X_UY_RT_InternalDelivery implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = 5191540912729090025L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_RT_InternalDelivery_ID
	 * @param trxName
	*/

	public MRTInternalDelivery(Properties ctx, int UY_RT_InternalDelivery_ID, String trxName) {
		super(ctx, UY_RT_InternalDelivery_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MRTInternalDelivery(Properties ctx, ResultSet rs, String trxName) {
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
		
		this.setC_Order_ID_To(this.getC_Order_ID());
		
		loadData();
		
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		this.setDocAction(DocAction.ACTION_Complete);

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
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		
		BigDecimal netAmt = Env.ZERO;
		BigDecimal totalAmt = Env.ZERO;
		List<MRTInternalDeliveryLine> lines = this.getLines();
		for (MRTInternalDeliveryLine line: lines){
			
			if (line.getLineNetAmt() != null){
				netAmt = netAmt.add(line.getLineNetAmt());	
			}
			
			if (line.getLineTotalAmt() != null){
				totalAmt = totalAmt.add(line.getLineTotalAmt());	
			}
		}
		
		this.setTotalLines(netAmt);
		this.setGrandTotal(totalAmt);
		
		DB.executeUpdateEx("update c_order set isdelivered='Y' where c_order_id=" + this.getC_Order_ID(), get_TrxName());

		
		
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
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Obtiene y carga lineas de orden de compra seleccionada en el cabezal
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Feb 1, 2016
	 */
	private void loadData(){

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try
		{
			sql = " select * from c_orderline where c_order_id = " + this.getC_Order_ID();
			
			pstmt = DB.prepareStatement(sql.toString(), null);

			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				MRTInternalDeliveryLine line = new MRTInternalDeliveryLine(getCtx(), 0, get_TrxName());
				line.setUY_RT_InternalDelivery_ID(this.get_ID());
				line.setC_OrderLine_ID(rs.getInt("c_orderline_id"));
				line.setM_Product_ID(rs.getInt("m_product_id"));
				line.setC_UOM_ID(rs.getInt("c_uom_id"));
				line.setQtyEntered(rs.getBigDecimal("qtyentered"));
				line.setqtyallocated(Env.ZERO);
				line.setqtyopen(rs.getBigDecimal("qtyentered"));
				line.setPriceEntered(rs.getBigDecimal("PriceEntered"));
				line.setLineNetAmt(rs.getBigDecimal("linenetamt"));
				line.setLineTotalAmt(rs.getBigDecimal("linenetamt"));
				line.saveEx();
			}
			
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}


	@Override
	protected boolean beforeSave(boolean newRecord) {

		try {
			
			this.setC_Order_ID_To(this.getC_Order_ID());
			
			if (newRecord){
				int cBPartnerID = DB.getSQLValueEx(null, " select c_bpartner_id from ad_org where ad_org_id =" + this.getAD_Org_ID_To());
				
				if (cBPartnerID <= 0){
					throw new AdempiereException("La sucursal solicitante no tiene socio de negocio definido");
				}
				
				this.setC_BPartner_ID(cBPartnerID);
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return true;
	}
	

	/***
	 * Obtiene y retorna lineas del modelo.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Feb 1, 2016
	 * @return
	 */
	private List<MRTInternalDeliveryLine> getLines(){
		
		String whereClause = X_UY_RT_InternalDeliveryLine.COLUMNNAME_UY_RT_InternalDelivery_ID + "=" + this.get_ID();
		
		List<MRTInternalDeliveryLine> lines = new org.compiere.model.Query(getCtx(), I_UY_RT_InternalDeliveryLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
	}
	
}
