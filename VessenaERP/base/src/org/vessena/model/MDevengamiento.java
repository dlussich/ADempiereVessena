/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 06/11/2012
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactLine;
import org.compiere.model.MActivity;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * org.openup.model - MDevengamiento
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 06/11/2012
 * @see
 */
public class MDevengamiento extends X_UY_Devengamiento implements DocAction{

	
	private static final long serialVersionUID = -7504180551626279369L;
	
	private String processMsg = null;
	private boolean justPrepared = false;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Devengamiento_ID
	 * @param trxName
	 */
	public MDevengamiento(Properties ctx, int UY_Devengamiento_ID,
			String trxName) {
		super(ctx, UY_Devengamiento_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDevengamiento(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Obtiene y retorna lista de lineas de este devengamiento.
	 * OpenUp Ltda. Issue #82 
	 * @author Gabriel Vila - 06/11/2012
	 * @see
	 * @return
	 */
	public List<MDevengamientoLine> getLines(){
		
		String whereClause = X_UY_DevengamientoLine.COLUMNNAME_UY_Devengamiento_ID + "=" + this.get_ID();
		
		List<MDevengamientoLine> lines = new Query(getCtx(), I_UY_DevengamientoLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
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
		this.loadData();
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
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

		this.setDateAcct(this.getDateTrx());
		
		// Test period
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());

		
		// Obtengo lineas
		List <MDevengamientoLine> lines = this.getLines();
		
		boolean hayProcesadas = false;
		
		for(MDevengamientoLine line: lines){
			if (line.isApproved()){
				hayProcesadas = true;
				// Verifico que esta linea sea valida
				if (!this.validateLine(line))
					return DocAction.STATUS_Invalid;
			}
		}
		
		if (!hayProcesadas){
			this.processMsg = "Debe marcar al menos una linea para Procesar.";
			return DocAction.STATUS_Invalid;
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

	/***
	 * Valida que la factura de una linea de devengamiento no hay sido procesada en otro devengamiento.
	 * OpenUp Ltda. Issue #82 
	 * @author Gabriel Vila - 06/11/2012
	 * @see
	 * @param line
	 * @return
	 */
	private boolean validateLine(MDevengamientoLine line) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean result = true;
		
		try{
			sql = " select dev.documentno " +
				  " from uy_devengamiento dev " +
				  " inner join uy_devengamientoline devl on dev.uy_devengamiento_id = devl.uy_devengamiento_id " +
				  " where dev.docstatus='CO' " +
				  " and devl.isapproved='Y' " +
				  " and devl.c_invoiceline_id =? " +
				  " and devl.actualquote =?"; 

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, line.getC_InvoiceLine_ID());
			pstmt.setInt(2, line.getActualQuote());
			rs = pstmt.executeQuery();
		
			if (rs.next()){

				result = false;
				
				MInvoice inv = new MInvoice(getCtx(), line.getC_Invoice_ID(), null);
				MProduct prod = (MProduct)line.getM_Product();
				MActivity ccosto = new MActivity(getCtx(), line.getC_Activity_ID_1(), null);
				
				this.processMsg = "La Factura : " + inv.getDocumentNo() +
								  " para el producto : " + prod.getName() +
								  ", departamento : " + ccosto.getName() +
								  " ya fue procesada en el Devengamiento Numero : " + rs.getString(1);
			}
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return result;
		
	}

	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		// Test period
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		
		
		// Elimino asientos contables
		FactLine.deleteFact(I_UY_Devengamiento.Table_ID, this.get_ID(), get_TrxName());
		
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
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Carga informacion de perdidas en los periodos requeridos.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 05/10/2012
	 * @see
	 */
	private void loadData(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		
		try{

			String whereFiltros = "";
			boolean hayInfo = false;
			
			MPeriod periodFilter = new MPeriod(getCtx(), this.getC_Period_ID(), null);
			
			whereFiltros = " and hdr.c_period_id_from <=" + this.getC_Period_ID() + 
						   " and hdr.c_period_id_to >=" + this.getC_Period_ID();
			
			if (this.getC_BPartner_ID() > 0){
				whereFiltros += " and hdr.c_bpartner_id =" + this.getC_BPartner_ID();  
			}
			
			sql = " select hdr.c_invoice_id, hdr.c_bpartner_id, hdr.dateinvoiced, hdr.totallines, hdr.c_currency_id, " +
				  " hdr.c_period_id_from, hdr.c_period_id_to, hdr.qtyquote, hdr.c_doctype_id, " +
				  " round((line.linenetamt/qtyquote),2) as amtquote, line.m_product_id, " +
				  " line.c_activity_id_1, line.c_invoiceline_id, " +
				  " ((" + this.getC_Period_ID() + " - c_period_id_from) + 1) as nroquote " +
				  " from c_invoice hdr " +
				  " inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
				  " inner join c_invoiceline line on hdr.c_invoice_id = line.c_invoice_id " +				  
				  " where hdr.ad_client_id =? " +
				  " and (hdr.dateinvoiced <'" + periodFilter.getStartDate() + "'" +
				  " OR hdr.dateinvoiced >'" + periodFilter.getEndDate() + "')" +
				  " and hdr.docstatus='CO' " +
				  " and hdr.isdevengable = 'Y' " + whereFiltros +
				  " and doc.issotrx='N' " +
				  " and doc.docbasetype IN('API','APC') " +
				  " and line.c_invoiceline_id not in " +  
				  " (select c_invoiceline_id from uy_devengamientoline devl " +
				  " inner join uy_devengamiento dev on devl.uy_devengamiento_id = dev.uy_devengamiento_id " +
				  " where dev.docstatus='CO' and devl.isapproved='Y' and devl.actualquote = ((" + this.getC_Period_ID() + " - hdr.c_period_id_from) + 1)) " +
				  " order by hdr.dateinvoiced";
				  
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.getAD_Client_ID());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				hayInfo = true;
				MDevengamientoLine devline = new MDevengamientoLine(getCtx(), 0, get_TrxName());
				devline.setC_DocType_ID(rs.getInt("c_doctype_id"));
				devline.setC_Invoice_ID(rs.getInt("c_invoice_id"));
				devline.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				devline.setDateTrx(rs.getTimestamp("dateinvoiced"));
				devline.setGrandTotal(rs.getBigDecimal("totallines"));
				devline.setQuoteAmt(rs.getBigDecimal("amtquote"));
				devline.setQtyQuote(new BigDecimal(rs.getInt("qtyquote")));
				devline.setC_Period_ID_From(rs.getInt("c_period_id_from"));
				devline.setC_Period_ID_To(rs.getInt("c_period_id_to"));
				devline.setIsApproved(true);
				devline.setActualQuote(rs.getInt("nroquote"));
				devline.setUY_Devengamiento_ID(this.get_ID());
				devline.setC_Currency_ID(rs.getInt("c_currency_id"));
				devline.setM_Product_ID(rs.getInt("m_product_id"));
				devline.setC_Activity_ID_1(rs.getInt("c_activity_id_1"));
				devline.setC_InvoiceLine_ID(rs.getInt("c_invoiceline_id"));
				
				devline.saveEx();
			}

			// Si la select no trajo registros para procesar, aviso
			if (!hayInfo){
				throw new AdempiereException("No se obtuvieron registros a procesar segun valores de Filtros.");
			}
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}

}
