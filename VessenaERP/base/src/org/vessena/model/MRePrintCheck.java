package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MBankAccount;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MRePrintCheck extends X_UY_RePrintCheck implements DocAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2138136368348680261L;
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RePrintCheck_ID
	 * @param trxName
	 */
	public MRePrintCheck(Properties ctx, int UY_RePrintCheck_ID, String trxName) {
		super(ctx, UY_RePrintCheck_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRePrintCheck(Properties ctx, ResultSet rs, String trxName) {
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
	
	/***
	 * Carga cheques para reimprimir segun filtros.
	 * OpenUp Ltda. Issue #932
	 * @author Nicolas Sarlabos - 04/06/2013
	 * @see
	 */
	private void loadData() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			String whereFiltros = "";

			if (this.getStartDate() != null) whereFiltros += " AND mp.datetrx >='" + this.getStartDate() + "'";
			if (this.getEndDate() != null) whereFiltros += " AND mp.datetrx <='" + this.getEndDate() + "'";
			
			if(this.getstartcheckno().compareTo(Env.ZERO) > 0) whereFiltros += " AND rl.checkno >= " + this.getstartcheckno();
			if(this.getendcheckno().compareTo(Env.ZERO) > 0) whereFiltros += " AND rl.checkno <= " + this.getendcheckno();
					
			sql = "select mp.uy_mediospago_id,rl.checkno,mp.datetrx,mp.duedate,mp.c_bpartner_id,mp.c_currency_id,mp.payamt" +
                  " from uy_mediospago mp" +
                  " inner join uy_checkreamline rl on mp.uy_mediospago_id = rl.uy_mediospago_id" +
                  " where mp.estado = 'EMI' and mp.tipomp = 'PRO' and mp.docstatus = 'CO' and mp.c_bankaccount_id = " + 
                   this.getC_BankAccount_ID() + " and mp.ad_client_id = " + this.getAD_Client_ID() + whereFiltros;
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				// Nueva linea de reimpresion
				MRePrintCheckLine line = new MRePrintCheckLine(getCtx(), 0, get_TrxName());
				line.setUY_RePrintCheck_ID(this.get_ID());
				line.setUY_MediosPago_ID(rs.getInt("uy_mediospago_id"));
				line.setCheckNo(rs.getBigDecimal("checkno"));
				line.setDateTrx(rs.getTimestamp("datetrx"));
				line.setDueDate(rs.getTimestamp("duedate"));
				line.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				line.setC_Currency_ID(rs.getInt("c_currency_id"));
				line.setPayAmt(rs.getBigDecimal("payamt"));		
				line.setIsSelected(true);
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

	@Override
	public String completeIt() {
		
//		Re-Check
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

			List<MRePrintCheckLine> lines = this.getLinesSelected();
			if (lines.size() <= 0) {
				this.processMsg = "No hay cheques seleccionados para procesar.";
				return DocAction.STATUS_Invalid;
			}
			
			MCheckReam checkReam = (MCheckReam)this.getUY_CheckReam();
			checkReam.lock();
			
			for (MRePrintCheckLine line: lines){
				
				// Obtengo siguiente linea de libreta con numero de cheque disponible para este nuevo recibo de pago.
				MCheckReamLine reamLine = checkReam.getNextLineNotUsed();
				if (reamLine == null){
					this.processMsg = "No hay mas cheques disponibles para la Libreta de Cheques seleccionada.";
					return DocAction.STATUS_Invalid;
				}
				
				MMediosPago mp = new MMediosPago(getCtx(),line.getUY_MediosPago_ID(),get_TrxName());
				
				// Genero cheque nuevo y anulo el anterior
				this.generateCheck(mp, reamLine);							
				
			}
			
			checkReam.unlock(get_TrxName());
			
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
	 * Obtiene y retorna lineas seleccionadas de este proceso de reimpresion.
	 * OpenUp Ltda. Issue #932
	 * @author Nicolas Sarlabos - 04/06/2013
	 * @see
	 * @return
	 */
	public List<MRePrintCheckLine> getLinesSelected(){
		
		String whereClause = X_UY_RePrintCheckLine.COLUMNNAME_UY_RePrintCheck_ID + "=" + this.get_ID() +
				" AND " + X_UY_RePrintCheckLine.COLUMNNAME_IsSelected + "='Y' ";
		
		List<MRePrintCheckLine> values = new Query(getCtx(), I_UY_RePrintCheckLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return values;
	}

	/***
	 * Genera cheque para medio de pago recibido.
	 * OpenUp Ltda. Issue #932
	 * @author Nicolas Sarlabos - 04/06/2013
	 * @see
	 * @param line
	 */
	private void generateCheck(MMediosPago mp, MCheckReamLine reamLine) {

		try{
			
			MCheckReam ream = new MCheckReam(getCtx(), this.getUY_CheckReam_ID(), null);
			MBankAccount ba = new MBankAccount(getCtx(), this.getC_BankAccount_ID(), null);
			
			// Alta de cheque a emitir como medio de pago
			MMediosPago mpago = new MMediosPago(getCtx(), 0, get_TrxName());
			mpago.setUY_CheckReam_ID(ream.get_ID());
			mpago.setDateTrx(mp.getDateTrx());
			mpago.setDueDate(mp.getDueDate());
			mpago.setDefaultDocType();
			mpago.setC_BankAccount_ID(mp.getC_BankAccount_ID());
			mpago.setC_BPartner_ID(mp.getC_BPartner_ID());
			mpago.setC_Currency_ID(mp.getC_Currency_ID());
			mpago.setPayAmt(mp.getPayAmt());
			mpago.setDateAcct(mp.getDateTrx());
			mpago.settipomp(mp.gettipomp());
			mpago.setestado(mp.getestado());
			mpago.setserie(ream.getserie());
			mpago.setoldstatus(mpago.getestado());
			mpago.setisrechazado(false);
			mpago.setuy_isreemplazo(false);
			mpago.setTenderType(mp.getTenderType());
			mpago.setIsInitialLoad(false);
			mpago.setUY_PaymentRule_ID(ba.getUY_PaymentRule_ID());
			mpago.setUY_CheckReamLine_ID(reamLine.get_ID());
			mpago.setCheckNo(String.valueOf(reamLine.getCheckNo()));
			mpago.setUY_PayOrder_ID(mp.getUY_PayOrder_ID());
			mpago.setUY_PayEmit_ID(mp.getUY_PayEmit_ID());
			mpago.saveEx();
			
			// Completo cheque
			if (!mpago.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException(mpago.getProcessMsg());
			}
			
			//Guardo trazabilidad del nuevo cheque y el anterior
			MRePrintCheckTracking track = new MRePrintCheckTracking (getCtx(),0,get_TrxName());
			track.setUY_RePrintCheck_ID(this.get_ID());
			track.setuy_oldmediospago_id(mp.get_ID());
			track.setuy_newmediospago_id(mpago.get_ID());
			track.setDateTrx(mpago.getDateTrx());
			track.setDueDate(mpago.getDueDate());
			track.setC_BPartner_ID(mpago.getC_BPartner_ID());
			track.setC_Currency_ID(mpago.getC_Currency_ID());
			track.setPayAmt(mpago.getPayAmt());
			track.setIsPrinted(false);
			track.saveEx();
									
			//Anulo cheque anterior
			if (!mp.processIt(DocumentEngine.ACTION_Void)){
				throw new AdempiereException(mp.getProcessMsg());
			}
		
		}
		catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}
	}
	
	/***
	 * Impresion de cheques emitidos en esta transaccion de reimpresion de cheques.
	 * OpenUp Ltda. Issue #932
	 * @author Nicolas Sarlabos - 04/06/2013
	 * @see
	 */
	public void printChecks(){

		try{
			
			MCheckReam ream = new MCheckReam(getCtx(), this.getUY_CheckReam_ID(), null);

			List<MRePrintCheckTracking> mpagos = this.getNotPrintedChecks();
			
			for (MRePrintCheckTracking mpago: mpagos){

				this.printCheck(mpago.getuy_newmediospago_id(), ream.getAD_Process_ID());

				// Pausa de 1 segundos preventiva para impresion masiva
				java.lang.Thread.sleep(1000);
				
				// Marco cheque como Impreso
				mpago.setIsPrinted(true);
				mpago.saveEx();

			}
			
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
	}
	
	
	/***
	 * Metodo que imprime un cheque.
	 * OpenUp Ltda. Issue #932 
	 * @author Nicolas Sarlabos - 04/06/2013
	 * @see
	 * @param uyMediosPagoID
	 * @param adProcessID
	 */
	private void printCheck(int uyMediosPagoID, int adProcessID){

		MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
		instance.saveEx();

		ProcessInfo pi = new ProcessInfo ("RePrintCheck", adProcessID);
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
		
		MPInstancePara para = new MPInstancePara(instance, 10);
		para.setParameter("UY_MediosPago_ID", new BigDecimal(uyMediosPagoID));
		para.saveEx();
		
		ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
		worker.start();     
		
	}
	
	/***
	 * Obtiene y retorna cheques emitidos.
	 * OpenUp Ltda. Issue #932
	 * @author Nicolas Sarlabos - 04/06/2013
	 * @see
	 * @return
	 */
	public List<MRePrintCheckTracking> getNotPrintedChecks(){
		
		String whereClause = X_UY_RePrintCheckTracking.COLUMNNAME_UY_RePrintCheck_ID + "=" + this.get_ID() +
				" AND " + X_UY_RePrintCheckTracking.COLUMNNAME_IsPrinted + "='N' ";
		
		List<MRePrintCheckTracking> values = new Query(getCtx(), I_UY_RePrintCheckTracking.Table_Name, whereClause, null)
		.setOrderBy(" uy_reprintchecktracking.uy_newmediospago_id ")
		.list();
		
		return values;
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
		// TODO Auto-generated method stub
		return null;
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

}
