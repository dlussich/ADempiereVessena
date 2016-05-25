/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;


/**
 * @author emiliano
 *
 */
public class MResguardoGen extends X_UY_ResguardoGen implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 892256385717806765L;
	private String processMsg = null;
	private boolean justPrepared = false;
	

	/**
	 * @param ctx
	 * @param UY_ResguardoGen_ID
	 * @param trxName
	 */
	public MResguardoGen(Properties ctx, int UY_ResguardoGen_ID, String trxName) {
		super(ctx, UY_ResguardoGen_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MResguardoGen(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 */
	@Override
	public boolean applyIt() {
		
		loadData();
		
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		this.setDocAction(DocAction.ACTION_Complete);

		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {
		
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

		
		// LOGICA DEL MODELO AL COMPLETAR
		
		MResguardoGen resgGen = new MResguardoGen(getCtx(), this.get_ID(), get_TrxName());
		int[] arreBPs = resgGen.getBPIds();
		int size = arreBPs.length;
		int cont = 0;
		
		while(cont < size){
			int partnerId = arreBPs[cont];
			//Se traen todos los documentos en el resguardo por BP
			List<MResguardoGenDoc> resgGenDocs = resgGen.getResgGenDocByBP(partnerId);
			
			setResguardosNLines(resgGen, resgGenDocs, partnerId);
			
			cont++;	
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
	
	
	private void setResguardosNLines(MResguardoGen resgGen, List<MResguardoGenDoc> resgGenDocs, 
										int partnerId){
		
		int cantLineas = MSysConfig.getIntValue("UY_MAX_LINEAS_RESGUARDO", 8, this.getAD_Client_ID());
		int cont = 0;
		BigDecimal resgPayAmt = new BigDecimal(0); 
		MDocType docT = MDocType.forValue(getCtx(), "resguardo", null);
		ArrayList<MResguardoGenDoc> resgTmp = new ArrayList<MResguardoGenDoc>();
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		
		try {
			MResguardo resguardo = new MResguardo(getCtx(), 0, get_TrxName());
			resguardo.setUY_ResguardoGen_ID(resgGen.get_ID());
			resguardo.setC_BPartner_ID(partnerId);
			resguardo.setAD_Client_ID(resgGen.getAD_Client_ID());
			resguardo.setC_DocType_ID(docT.get_ID());
			resguardo.setDateTrx(today);
			resguardo.setDateAcct(today);
			resguardo.setDocStatus(DocumentEngine.STATUS_Drafted);
			resguardo.setDocAction(DocumentEngine.ACTION_Complete);
			resguardo.setAD_Org_ID(resgGen.getAD_Org_ID());
			resguardo.setC_Currency_ID(resgGen.getC_Currency_ID());
			resguardo.saveEx();
			
			while (cont < (resgGenDocs.size() < cantLineas ? resgGenDocs.size() : cantLineas)){
				PreparedStatement pstmt = null;
				ResultSet rs = null;			
				int contLines = 0;
				MResguardoGenDoc resgGenDoc = resgGenDocs.get(cont++);
				MResguardoInvoice resgInv = new MResguardoInvoice(getCtx(), 0, get_TrxName());
				resgInv.setAD_Client_ID(resgGenDoc.getAD_Client_ID());
				resgInv.setAD_Org_ID(resgGenDoc.getAD_Org_ID());
				resgInv.setC_Currency_ID(resgGenDoc.getC_Currency_ID());
				resgInv.setC_DocType_ID(resgGenDoc.getC_DocType_ID());
				resgInv.setDocumentNo(resgGenDoc.getDocumentNo());
				resgInv.setC_Invoice_ID(resgGenDoc.getC_Invoice_ID());
				resgInv.setDateInvoiced(resgGenDoc.getDateInvoiced());
				resgInv.setUY_Resguardo_ID(resguardo.get_ID());
				resgInv.setTotalLines(resgGenDoc.getTotalLines());
				resgInv.setTotalLinesSource(resgGenDoc.getTotalLinesSource());
				resgInv.setGrandTotal(resgGenDoc.getAmtRetention());
				resgInv.setGrandTotalSource(resgGenDoc.getGrandTotalSource());
				resgInv.saveEx();
				
				resgPayAmt = resgPayAmt.add(resgGenDoc.getAmtRetention());
				
//				String qry = "select uy_retention_id, sum(amt), sum(amtsource) from UY_ResguardoGenLine" + 
//								" where uy_resguardogen_id = " + resgGen.get_ID() +
//								" group by uy_retention_id";
				
				String qry = "select uy_retention_id, sum(amtretention), sum(amtretentionsource) "
								+ "from UY_ResguardoGenDet where uy_resguardogendoc_id = " + resgGenDoc.get_ID()
								+ " group by uy_retention_id";
				
				pstmt = DB.prepareStatement (qry, null);

				rs = pstmt.executeQuery();
				
				while(rs.next()){					
					MResguardoLine resLine = new MResguardoLine(getCtx(), 0, get_TrxName());
					resLine.setAD_Client_ID(resgGenDoc.getAD_Client_ID());
					resLine.setAD_Org_ID(resgGenDoc.getAD_Org_ID());
					resLine.setUY_Resguardo_ID(resguardo.get_ID());
					resLine.setUY_Retention_ID(rs.getInt(1));
					resLine.setAmt(rs.getBigDecimal(2));
					resLine.setAmtSource(rs.getBigDecimal(3));
					resLine.saveEx();
				}
				resgTmp.add(resgGenDoc);				
				
			}
			
			if(resgPayAmt.compareTo(Env.ZERO) == 0){
				resguardo.deleteEx(true);
			}else{
				resguardo.setPayAmt(resgPayAmt);
				resguardo.saveEx();
				if (!resguardo.processIt(ACTION_Complete)){
					this.processMsg = resguardo.getProcessMsg();
					throw new AdempiereException(resguardo.getProcessMsg());
				}
			}
			
			resgGenDocs.removeAll(resgTmp);
			if(resgGenDocs.size()>0){
				setResguardosNLines(resgGen, resgGenDocs, partnerId);
			}
			

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		

	}
	
	

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/***
	 * Carga los regsguardos en forma masiva.
	 * OpenUp Ltda. Issue #5144
	 * @author Emiliano Bentancor - 7/12/2015
	 * @see
	 * @param gestion
	 */
	private void loadData(){
		
		BigDecimal divrate = new BigDecimal("1.00");
//		MathContext mc = new MathContext(2, RoundingMode.FLOOR);
		String sql=	" SELECT ci.c_bpartner_id, ci.c_invoice_id as recibo_id, ci.c_doctype_id, doc.printname, ci.documentno, " +
				" ci.dateinvoiced as recibo_date, ci.c_currency_id, cur.description, " +
				" ci.totallines as recibo_subtotal, ci.grandtotal as recibo_total, coalesce(tax.taxamt,0) as amtiva " +
			" FROM c_invoice ci " +
			" INNER JOIN c_doctype doc on ci.c_doctype_id = doc.c_doctype_id " +
			" INNER JOIN c_currency cur on ci.c_currency_id = cur.c_currency_id " +
			" left outer join c_invoicetax tax on (ci.c_invoice_id = tax.c_invoice_id and tax.c_tax_id in " + 
			" (select c_tax_id from c_tax where lower(value)='basico')) " +
			" WHERE ci.ad_client_id =? " +
			" AND ci.issotrx=? " +
			" AND ci.docstatus='CO' " +
			" AND ci.UY_resguardo_id is null " +
//			" AND ci.c_bpartner_id =? " +
			" AND ci.c_currency_id =? ";
//			" AND ci.dateinvoiced <=? ";
//			" AND ci.uy_resguardo_id is null " +
//			" AND ci.c_invoice_id NOT IN (SELECT coalesce(rinv.c_invoice_id,0) FROM uy_resguardoinvoice rinv WHERE rinv.uy_resguardo_id=" + uyResguardoID + ")";
		
		if(this.getStartDate() != null) sql = sql + " AND ci.dateinvoiced >=? ";
		if(this.getEndDate() != null) sql = sql + " AND ci.dateinvoiced <=? ";
		
		List<MResguardoGenBP> listaBP = MResguardoGenBP.forResguardoGen(this.getCtx(),this.get_ID(),null);
		if(listaBP.size() > 0){
			sql = sql + " AND ci.c_bpartner_id in (";
			for(MResguardoGenBP rgBP : listaBP){
				sql = sql + rgBP.getC_BPartner_ID() + ",";
			}
			sql = sql.substring(0, sql.length()-1) + ") ";
		}
		
		sql = sql + " order by ci.c_bpartner_id asc, ci.dateinvoiced desc";
		
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
				
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.getAD_Client_ID());
			pstmt.setString(2, "N");
			pstmt.setInt(3, this.getC_Currency_ID());
			if(this.getStartDate() != null) pstmt.setTimestamp(4, this.getStartDate());
			if(this.getEndDate() != null){
				if(this.getStartDate() == null){
					pstmt.setTimestamp(4, this.getEndDate());
				}else{
					pstmt.setTimestamp(5, this.getEndDate());
				}
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				if (rs.getInt("c_currency_id") != schema.getC_Currency_ID()){
					divrate = new BigDecimal("0.00");
				}
					
				MResguardoGenDoc rgd = new MResguardoGenDoc(this.getCtx(), 0, null);
				rgd.setUY_ResguardoGen_ID(this.getUY_ResguardoGen_ID());
				rgd.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				rgd.setC_Invoice_ID(rs.getInt("recibo_id"));
				rgd.setGrandTotal(rs.getBigDecimal("recibo_total").multiply(divrate));//grandTotal
				rgd.setC_DocType_ID(rs.getInt("c_doctype_id"));
				rgd.setDocumentNo(rs.getString("documentno"));
				rgd.setC_Currency_ID(rs.getInt("c_currency_id"));
				rgd.setDateInvoiced(rs.getTimestamp("recibo_date"));
				//amtopen
				rgd.setTotalLines(rs.getBigDecimal("recibo_subtotal").multiply(divrate)); //totallines
				rgd.setDivideRate(divrate);//dividerate
				rgd.setTotalLinesSource(rs.getBigDecimal("recibo_subtotal"));
				rgd.setAmtIVA(rs.getBigDecimal("amtiva").multiply(divrate));
				rgd.setAmtIVAMT(rs.getBigDecimal("amtiva"));
				rgd.setGrandTotalSource(rs.getBigDecimal("recibo_total"));
				rgd.saveEx();
			}
			
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}
	
	/***
	 * Obtiene y retorna array de invoices que participan de este resguardo.
	 * OpenUp Ltda. Issue #5144
	 * @author Emiliano Bentancor - 8/12/2015 ///Chequear que este bien
	 * @see
	 * @return
	 */
	public List<MResguardoGenDoc> getInvoices() {

		String whereClause = X_UY_ResguardoGenDoc.COLUMNNAME_UY_ResguardoGen_ID + "=" + this.get_ID();
		List<MResguardoGenDoc> lines = new Query(getCtx(), I_UY_ResguardoGenDoc.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}
	
	/***
	 * Obtiene y retorna rs con ids de los BPs que participan de este resguardo.
	 * OpenUp Ltda. Issue #5144
	 * @author Emiliano Bentancor - 8/12/2015 ///Chequear que este bien
	 * @see
	 * @return
	 */
	public int[] getBPIds() {
		int[] arre;
		int rowcount = 0;
		int i = 0;
		String qry = "SELECT distinct("+ X_UY_ResguardoGenDoc.COLUMNNAME_C_BPartner_ID + ")"
						+ " FROM " + I_UY_ResguardoGenDoc.Table_Name + " where "
						+ X_UY_ResguardoGenDoc.COLUMNNAME_UY_ResguardoGen_ID + "=" + this.get_ID();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt = DB.prepareStatement(qry.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery();
			rs.last();
			rowcount = rs.getRow();
			rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element

			arre = new int[rowcount];
			while(rs.next()){
				arre[i] = rs.getInt("c_bpartner_id");
				i++;
			}
			
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, qry, e);
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return arre;
	}
	
	/***
	 * Obtiene y retorna array de invoices que participan de este resguardo por BP.
	 * OpenUp Ltda. Issue #5144
	 * @author Emiliano Bentancor - 8/12/2015 ///Chequear que este bien
	 * @see
	 * @return
	 */
	public List<MResguardoGenDoc> getResgGenDocByBP(int cBPId) {

		String whereClause = X_UY_ResguardoGenDoc.COLUMNNAME_UY_ResguardoGen_ID + "=" + this.get_ID() +
								" AND " + X_UY_ResguardoGenDoc.COLUMNNAME_C_BPartner_ID + "=" + cBPId;
		List<MResguardoGenDoc> lines = new Query(getCtx(), I_UY_ResguardoGenDoc.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}
	

}
