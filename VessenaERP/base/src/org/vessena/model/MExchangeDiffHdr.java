/**
 * MExchangeDiffHdr.java
 * 11/04/2011
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactLine;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.MPeriod;
import org.compiere.model.MTax;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * OpenUp.
 * MExchangeDiffHdr
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 11/04/2011
 */
public class MExchangeDiffHdr extends X_UY_ExchangeDiffHdr implements DocAction {

	private String processMsg = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3383499737481712621L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_ExchangeDiffHdr_ID
	 * @param trxName
	 */
	public MExchangeDiffHdr(Properties ctx, int UY_ExchangeDiffHdr_ID,
			String trxName) {
		super(ctx, UY_ExchangeDiffHdr_ID, trxName);
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MExchangeDiffHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {

		// Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
	
		// Test period
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		
		// After Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		// Refresco estados
		setProcessed(true);
		setDateAcct(getDateTrx());
		setDocStatus(DOCSTATUS_Completed);
		setDocAction(DOCACTION_None);
		setPosted(false);
		this.saveEx(get_TrxName());
		
		return DocAction.STATUS_Completed;		

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
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
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		// Proceso segun accion
		if (action.equalsIgnoreCase(DocumentEngine.ACTION_Prepare))
			return this.loadData();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete)){
			this.completeIt();
			return (this.processMsg == null);
		}
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Void)){
			this.voidIt();
			return (this.processMsg == null);
		}
		else
			return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null) return false;
		
		// Test period
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());

		
		// Elimino asientos contables asociados a este proceso de diferencia de cambio
		FactLine.deleteFact(X_UY_ExchangeDiffHdr.Table_ID, this.get_ID(), get_TrxName());
		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null) return false;
		
		this.setProcessed(true);
		this.setDocStatus(STATUS_Voided);
		this.setDocAction(DOCACTION_None);
		this.saveEx();
		
		return true;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene los asientos que se deben realizar para el ajuste por 
	 * diferencia de cambio y lo despliega en pantalla para confirmacion del usuario.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 12/04/2011
	 */
	private boolean loadData() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean result = true;
		
		try
		{
			
			MDocType doc = (MDocType)this.getC_DocType();
			if (doc.getValue() != null){
				if (doc.getValue().equalsIgnoreCase("difcambiovta")){
					return this.loadDataVentas();
				}
				else if (doc.getValue().equalsIgnoreCase("difcambiocpra")){
					return this.loadDataCompras();
				}
			}
			
			log.info("Iniciando proceso de diferencia de cambio...");

			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();

			int idMonedaNacional = schema.getC_Currency_ID();

			// Condicion de Filtros
			String whereFiltros = " AND fa.DateAcct <='" + this.getDateAcct() + "'";
			
			sql = " select account_id, uy_accnat_currency_id, sum(uy_amtnativedr) as amtsourcedr, sum(uy_amtnativecr) as amtsourcecr, " + //OpenUp Nicolas Sarlabos #139 29/11/2011
				  " sum(amtacctdr) as amtacctdr, sum(amtacctcr) as amtacctcr " +
				  " from fact_acct fa " +
				  " where fa.ad_client_id = ? " +
				  whereFiltros +
				  " and uy_accnat_currency_id =" + this.getC_Currency_ID() +			
				  " group by account_id, uy_accnat_currency_id ";
	
			
			sql = " select account_id, uy_accnat_currency_id, " +
				  " sum(case when ad_table_id = (select ad_table_id from ad_table where lower(tablename)='uy_exchangediffhdr') then 0 else uy_amtnativedr end) as amtsourcedr, " +
				  " sum(case when ad_table_id = (select ad_table_id from ad_table where lower(tablename)='uy_exchangediffhdr') then 0 else uy_amtnativecr end) as amtsourcecr, " +
				  " sum(amtacctdr) as amtacctdr, sum(amtacctcr) as amtacctcr " +
				  " from fact_acct fa " +
				  " where fa.ad_client_id = ? " +
				  whereFiltros +
				  " and uy_accnat_currency_id =" + this.getC_Currency_ID() +			
				  " group by account_id, uy_accnat_currency_id ";
			
			log.info(sql);
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.getAD_Client_ID());
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				MExchangeDiffLine line = new MExchangeDiffLine(getCtx(), 0, get_TrxName());
				line.setUY_ExchangeDiffHdr_ID(this.getUY_ExchangeDiffHdr_ID());
				line.setDateAcct(this.getDateAcct());
				line.setC_ElementValue_ID(rs.getInt("account_id"));
				line.setC_Currency_ID(rs.getInt("uy_accnat_currency_id"));
				line.setAmtSourceDr(rs.getBigDecimal("amtsourcedr"));
				line.setAmtSourceCr(rs.getBigDecimal("amtsourcecr"));
				line.setAmtAcctDr(rs.getBigDecimal("amtacctdr"));
				line.setAmtAcctCr(rs.getBigDecimal("amtacctcr"));
				//OpenUp Nicolas Sarlabos #139 30/01/2012
				Timestamp fechaCalculo = TimeUtil.addDays(line.getDateAcct(), 1); //para los calculos se toma la tasa de cambio del dia siguiente
				BigDecimal divideRate = MConversionRate.getDivideRate(idMonedaNacional, line.getC_Currency_ID(), fechaCalculo, 0, this.getAD_Client_ID(), this
						.getAD_Org_ID());
				BigDecimal multiplyRate = MConversionRate.getRate(line.getC_Currency_ID(), idMonedaNacional, fechaCalculo, 0, this.getAD_Client_ID(), this
						.getAD_Org_ID());
				//fin OpenUp Nicolas Sarlabos #139 30/01/2012
				
				if (divideRate == null || multiplyRate == null){
					this.processMsg = "No hay tipo de cambio definido para : Moneda Origen =" + idMonedaNacional + 
								       ", Moneda Destino ="
							+ line.getC_Currency_ID() + ", Fecha : " + fechaCalculo;
					return false;
				}
				
				line.setDivideRate(divideRate);
				
				// Calculo saldo en moneda cuenta y saldo en moneda nacional
				// Saldo = Debitos - Creditos
				BigDecimal saldoAcctDR = Env.ZERO, saldoAcctCR = Env.ZERO;
				if (line.getAmtAcctDr().compareTo(line.getAmtAcctCr())>=0)
					saldoAcctDR = line.getAmtAcctDr().subtract(line.getAmtAcctCr()); 
				else
					saldoAcctCR = line.getAmtAcctCr().subtract(line.getAmtAcctDr());

				BigDecimal saldoSourceDR = Env.ZERO, saldoSourceCR = Env.ZERO;
				if (line.getAmtSourceDr().compareTo(line.getAmtSourceCr())>=0)
					saldoSourceDR = line.getAmtSourceDr().subtract(line.getAmtSourceCr()); 
				else
					saldoSourceCR = line.getAmtSourceCr().subtract(line.getAmtSourceDr());

				// Obtengo saldo en pesos a tipo de cambio = fecha de proceso seleccionada
				BigDecimal saldoActualDR = saldoSourceDR.multiply(multiplyRate);
				BigDecimal saldoActualCR = saldoSourceCR.multiply(multiplyRate);

				// Si ambos saldos en moneda nacional (actual vs sum) son debitos
				if ((saldoActualDR.compareTo(Env.ZERO) > 0) && (saldoAcctDR.compareTo(Env.ZERO) > 0)){

					// Si el saldo actual es mayor que el saldo sum
					if (saldoActualDR.compareTo(saldoAcctDR) >= 0) { //OpenUp Nicolas Sarlabos #139 09/02/2012
						// Sumo diferencia a debitos
						line.setamtdiffdr(saldoActualDR.subtract(saldoAcctDR));
						line.setamtdiffcr(Env.ZERO);
					}
					else if (saldoActualDR.compareTo(saldoAcctDR) < 0){
						// Sumo diferencia a creditos
						line.setamtdiffcr(saldoAcctDR.subtract(saldoActualDR));
						line.setamtdiffdr(Env.ZERO);
					}
					
				}

				// Si ambos saldos en moneda nacional (actual vs sum) son creditos
				else if ((saldoActualCR.compareTo(Env.ZERO) > 0) && (saldoAcctCR.compareTo(Env.ZERO) > 0)){
				
					// Si el saldo actual es mayor que el saldo sum
					if (saldoActualCR.compareTo(saldoAcctCR) >= 0) { //OpenUp Nicolas Sarlabos #139 09/02/2012
						// Sumo diferencia a creditos
						line.setamtdiffcr(saldoActualCR.subtract(saldoAcctCR));
						line.setamtdiffdr(Env.ZERO);
					}
					else if (saldoActualCR.compareTo(saldoAcctCR) < 0){
						// Sumo diferencia a debitos
						line.setamtdiffdr(saldoAcctCR.subtract(saldoActualCR));
						line.setamtdiffcr(Env.ZERO);
					}
					
				}

				// Si saldo actual es debito y saldo sum es credito
				else if ((saldoActualDR.compareTo(Env.ZERO) > 0) && (saldoAcctCR.compareTo(Env.ZERO) > 0)){

					// Sumo diferencia a debitos
					line.setamtdiffdr(saldoActualDR.add(saldoAcctCR));
					line.setamtdiffcr(Env.ZERO);
					
				}

				// Si saldo actual es credito y saldo sum es debito
				else if ((saldoActualCR.compareTo(Env.ZERO) > 0) && (saldoAcctDR.compareTo(Env.ZERO) > 0)){
				
					// Sumo diferencia a creditos
					line.setamtdiffcr(saldoActualCR.add(saldoAcctDR));
					line.setamtdiffdr(Env.ZERO);
					
				}
				
				//OpenUp #139 Nicolas Sarlabos 19/03/2012
				// Si ambos saldos actuales son cero, veo si hay saldoacct credito o debito para guardar diferencia
				else if ((saldoActualCR.compareTo(Env.ZERO) == 0) && (saldoActualDR.compareTo(Env.ZERO) == 0)){
					// Si tengo saldo credito en Moneda Nacional de movimientos originales
					if (saldoAcctCR.compareTo(Env.ZERO) > 0){
						line.setamtdiffdr(saldoAcctCR);
						line.setamtdiffcr(Env.ZERO);
					}
					else if (saldoAcctDR.compareTo(Env.ZERO) > 0){
						line.setamtdiffcr(saldoAcctDR);
						line.setamtdiffdr(Env.ZERO);
					}
				}
				//Fin OpenUp #139 Nicolas Sarlabos 19/03/2012
				
				line.setProcessed(true);
				line.saveEx(get_TrxName());
			}
			
			rs.close();
			pstmt.close();

			// Actualizo atributos de filtros
			this.setDocStatus(DocumentEngine.STATUS_WaitingConfirmation);
			this.setDocAction(DocumentEngine.ACTION_Complete);
			this.saveEx();
			
			log.info("Fin proceso de Diferencia de Cambio.");
			
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			this.processMsg = e.getMessage();
			result = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return result;
	}

	/***
	 * Obtiene datos para calculo de diferencia de cambio VENTAS
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 12/03/2015
	 * @see
	 * @return
	 */
	private boolean loadDataVentas() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean result = true;
		
		try
		{
			MPeriod period = new MPeriod(getCtx(), this.get_ValueAsInt("C_Period_ID_To"), null);
			String whereFiltros = " and h.datetrx between ? and ? ";
			
			
			sql = " select l.c_payment_id, l.uy_allocation_id " +
				  " from uy_allocationpayment l " +
				  " inner join uy_allocation h on l.uy_allocation_id = h.uy_allocation_id " +
				  " where h.ad_client_id =? " +
				  " and h.docstatus='CO' " +
				  " and h.issotrx='Y' " + whereFiltros +
				  " and l.c_payment_id > 0 ";

			pstmt = DB.prepareStatement (sql, null);
			
			pstmt.setInt(1, this.getAD_Client_ID());
			pstmt.setTimestamp(2, period.getStartDate());
			pstmt.setTimestamp(3, period.getEndDate());
			
			rs = pstmt.executeQuery();

			while (rs.next()){
				
				// Proceso facturas
				MPayment payment = new MPayment(getCtx(), rs.getInt("c_payment_id"), null);
				MAllocation allocation = new MAllocation(getCtx(), rs.getInt("uy_allocation_id"), null); 
				
				this.procesoFacturas(payment, allocation);				
			}
			
			
			// Actualizo atributos de filtros
			this.setDocStatus(DocumentEngine.STATUS_WaitingConfirmation);
			this.setDocAction(DocumentEngine.ACTION_Complete);
			this.saveEx();
			
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			this.processMsg = e.getMessage();
			result = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return result;

	}

	/***
	 * Obtiene datos para calculo de diferencia de cambio COMPRAS
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 12/03/2015
	 * @see
	 * @return
	 */
	private boolean loadDataCompras() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean result = true;
		
		try
		{
			MPeriod period = new MPeriod(getCtx(), this.get_ValueAsInt("C_Period_ID_To"), null);
			String whereFiltros = " and h.datetrx between ? and ? ";
			
			sql = " select l.c_payment_id, l.uy_allocation_id " +
				  " from uy_allocationpayment l inner join uy_allocation h on l.uy_allocation_id = h.uy_allocation_id " +
				  " where h.ad_client_id =? " +
				  " and h.docstatus='CO' " +
				  " and h.issotrx='N' " + whereFiltros +
				  " and l.c_payment_id > 0 " +
				  " and exists (select c_invoice_id from uy_allocationinvoice where uy_allocation_id = h.uy_allocation_id and c_doctype_id in" +
				  " (select c_doctype_id from c_doctype where docbasetype in ('API','APC') and ad_client_id =" + this.getAD_Client_ID() + 
				  " and value not in('valeflete')))";

			pstmt = DB.prepareStatement (sql, null);
			
			pstmt.setInt(1, this.getAD_Client_ID());
			pstmt.setTimestamp(2, period.getStartDate());
			pstmt.setTimestamp(3, period.getEndDate());
			
			rs = pstmt.executeQuery();

			while (rs.next()){
				
				// Proceso facturas
				MPayment payment = new MPayment(getCtx(), rs.getInt("c_payment_id"), null);
				MAllocation allocation = new MAllocation(getCtx(), rs.getInt("uy_allocation_id"), null); 
				
				this.procesoFacturas(payment, allocation);				
			}
			
			
			// Actualizo atributos de filtros
			this.setDocStatus(DocumentEngine.STATUS_WaitingConfirmation);
			this.setDocAction(DocumentEngine.ACTION_Complete);
			this.saveEx();
			
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			this.processMsg = e.getMessage();
			result = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return result;

	}

	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna lineas del modelo.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 12/04/2011
	 */
	public MExchangeDiffLine[] getLines() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MExchangeDiffLine> list = new ArrayList<MExchangeDiffLine>();
		
		try{
			sql ="SELECT uy_exchangediffline_id " + 
 		  	" FROM " + X_UY_ExchangeDiffLine.Table_Name + 
		  	" WHERE uy_exchangediffhdr_id =?";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_ExchangeDiffHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MExchangeDiffLine value = new MExchangeDiffLine(Env.getCtx(), rs.getInt(1), null);
				list.add(value);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return list.toArray(new MExchangeDiffLine[list.size()]);		
	}

	
	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateTrx(new Timestamp (System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(), true, this);
			if (value != null)
				setDocumentNo(value);
		}
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	
	/***
	 * Procesa facturas para diferencia de cambio venta o compra 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 12/03/2015
	 * @see
	 * @param payment
	 * @param allocation
	 */
	private void procesoFacturas(MPayment payment, MAllocation allocation) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			sql = " select c_invoice_id, amtallocated " +
				  " from uy_allocationinvoice " +
				  " where uy_allocation_id =" + allocation.get_ID();

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){
				
				// Proceso impuestos
				MInvoice invoice = new MInvoice(getCtx(), rs.getInt("c_invoice_id"), null);
				
				this.procesoImpuestos(payment, invoice, allocation, rs.getBigDecimal("amtallocated"));
				
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

		
	}

	
	/***
	 * Proceso impuestos para diferencia de cambio venta o compra
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 12/03/2015
	 * @see
	 * @param payment
	 * @param invoice
	 * @param allocation
	 * @param amtallocated
	 */
	private void procesoImpuestos(MPayment payment, MInvoice invoice, MAllocation allocation, BigDecimal amtallocated) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			BigDecimal tcInvoice = MConversionRate.getRate(this.getC_Currency_ID(), 142, invoice.getDateInvoiced(), 0, this.getAD_Client_ID(), 0);
			BigDecimal tcPayment = MConversionRate.getRate(this.getC_Currency_ID(), 142, payment.getDateTrx(), 0, this.getAD_Client_ID(), 0);
			
			if (tcInvoice == null){
				throw new AdempiereException("Falta tasa de cambio para fecha :" + invoice.getDateInvoiced());
			}

			if (tcPayment == null){
				throw new AdempiereException("Falta tasa de cambio para fecha :" + payment.getDateTrx());
			}
			
			BigDecimal tcDiff = tcPayment.subtract(tcInvoice);
			
			sql = " select (coalesce(taxbaseamt,0) + coalesce(taxamt,0)) as importe, c_tax_id " +
				  " from c_invoicetax where c_invoice_id =" + invoice.get_ID();

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			boolean hayDatos = false;
			
			while (rs.next()){
				
				hayDatos = true;
				
				BigDecimal amtDiff = rs.getBigDecimal("importe").multiply(tcDiff).setScale(2, RoundingMode.HALF_UP);					
				BigDecimal amtDCIVA = Env.ZERO;
				
				MTax tax = new MTax(getCtx(), rs.getInt("c_tax_id"), null);

				if (tax.getValue().equalsIgnoreCase("basico")){
					BigDecimal ii = amtDiff.divide(new BigDecimal(1.22), 2, RoundingMode.HALF_UP); 
					amtDCIVA = amtDiff.subtract(ii); 
					amtDiff = ii;
				}
				
				MExchangeDiffLine exline = new MExchangeDiffLine(getCtx(), 0, get_TrxName());
				exline.setUY_ExchangeDiffHdr_ID(this.get_ID());
				exline.set_ValueOfColumn("C_Payment_ID", payment.get_ID());
				exline.set_ValueOfColumn("C_Invoice_ID", invoice.get_ID());
				exline.set_ValueOfColumn("datepayment", payment.getDateTrx());
				exline.set_ValueOfColumn("DateInvoiced", invoice.getDateInvoiced());
				exline.set_ValueOfColumn("TCPayment", tcPayment);
				exline.set_ValueOfColumn("TCInvoice", tcInvoice);
				exline.set_ValueOfColumn("AmtDC", amtDiff);
				exline.set_ValueOfColumn("AmtIVA", amtDCIVA);
				
				exline.saveEx();
				
			}

			if (!hayDatos){

				BigDecimal amtDiff = amtallocated.multiply(tcDiff).setScale(2, RoundingMode.HALF_UP);
				BigDecimal amtDCIVA = Env.ZERO;

				MExchangeDiffLine exline = new MExchangeDiffLine(getCtx(), 0, get_TrxName());
				exline.setUY_ExchangeDiffHdr_ID(this.get_ID());
				exline.set_ValueOfColumn("C_Payment_ID", payment.get_ID());
				exline.set_ValueOfColumn("C_Invoice_ID", invoice.get_ID());
				exline.set_ValueOfColumn("datepayment", payment.getDateTrx());
				exline.set_ValueOfColumn("DateInvoiced", invoice.getDateInvoiced());
				exline.set_ValueOfColumn("TCPayment", tcPayment);
				exline.set_ValueOfColumn("TCInvoice", tcInvoice);
				exline.set_ValueOfColumn("AmtDC", amtDiff);
				exline.set_ValueOfColumn("AmtIVA", amtDCIVA);
				
				exline.saveEx();
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
	}
}
