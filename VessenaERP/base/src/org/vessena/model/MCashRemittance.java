/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.apps.ADialog;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.wf.MWFNode;

/**
 * @author Nicolas
 *
 */
public class MCashRemittance extends X_UY_CashRemittance implements DocAction, IDynamicWF{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5809095060074767259L;
	
	private String processMsg = null;
	private boolean justPrepared = false;
	private boolean isParcialApproved = false;

	/**
	 * @param ctx
	 * @param UY_CashRemittance_ID
	 * @param trxName
	 */
	public MCashRemittance(Properties ctx, int UY_CashRemittance_ID,
			String trxName) {
		super(ctx, UY_CashRemittance_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashRemittance(Properties ctx, ResultSet rs, String trxName) {
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
		try {
			
			List<MCashRemittanceLine> lines = this.getLines();
			List<MCashRemittanceCharge> chargeLines = this.getChargeLines();
			
			if(lines.size()==0 && chargeLines.size()==0) throw new AdempiereException("Imposible solicitar, documento sin lineas");

			if ((this.getApprovalText() == null) || (this.getApprovalText().equalsIgnoreCase(""))){
				throw new AdempiereException("Debe ingresar un Comentario en el Detalle de Aprobación");
			}
				
			this.setIsApproved(true);
			
			this.setApprovalStatus(APPROVALSTATUS_AprobadoPorGerenteDeArea);
			this.setDateApproved(new Timestamp(System.currentTimeMillis()));
			this.setApprovedType(APPROVEDTYPE_AUTORIZADO);
			this.setApprovalUser_ID(Env.getAD_User_ID(Env.getCtx()));
			
			this.setDocStatus(DocumentEngine.STATUS_Approved);
			this.setDocAction(DocAction.ACTION_Complete);

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

		return true;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
	
		try {

			List<MCashRemittanceLine> lines = this.getLines();
			
			if(lines.size()==0) throw new AdempiereException("Imposible rechazar, documento sin lineas");
			
			if ((this.getApprovalText() == null) || (this.getApprovalText().equalsIgnoreCase(""))){
				throw new AdempiereException("Debe ingresar un Comentario en el Detalle de Aprobación");
			}
				
			this.setIsApproved(false);
			
			this.setApprovalStatus(APPROVALSTATUS_RechazadoPorGerenteDeArea);
			this.setDateApproved(new Timestamp(System.currentTimeMillis()));
			this.setApprovedType(APPROVEDTYPE_RECHAZADO);
			this.setApprovalUser_ID(Env.getAD_User_ID(Env.getCtx()));
		
			this.setDocStatus(DocumentEngine.STATUS_NotApproved);
			this.setDocAction(DOCACTION_Request);

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		

		return true;
	}
	
	/***
	 * Accion de Solicitud.
	 * OpenUp Ltda. Issue #4168 
	 * @author Gabriel Vila - 26/05/2015
	 * @see
	 * @return
	 */
	public boolean requestIt() {
		
		boolean result = true;
		
		try {
			
			List<MCashRemittanceLine> lines = this.getLines();
			List<MCashRemittanceCharge> chargeLines = this.getChargeLines();
			
			if(lines.size()==0 && chargeLines.size()==0) throw new AdempiereException("Imposible solicitar, documento sin lineas");
			
			// Limpio datos de aprobacion
			DB.executeUpdateEx(" update UY_CashRemittance set ApprovedType = null, DateApproved = null, ApprovalUser_ID = null, ApprovalText = null where UY_CashRemittance_ID =" + this.get_ID(), get_TrxName());
			
			// Estado de solicitud
			this.setApprovalStatus(APPROVALSTATUS_AprobadoPorGerenteDeArea);
			
			// Seteo datos del solicitante
			this.setDateRequested(new Timestamp(System.currentTimeMillis()));
			this.setRequestedUser_ID(Env.getAD_User_ID(Env.getCtx()));		
			
			this.setDocStatus(DocumentEngine.STATUS_Requested);
			this.setDocAction(DocAction.ACTION_Approve);

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return result;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//OpenUp. Nicolas Sarlabos. 29/02/216. #5545.
		MDocType doc = (MDocType)this.getC_DocType();
				
		if(doc.getValue()!=null){
			
			if(doc.getValue().equalsIgnoreCase("cashremitt")){//si es remesa efectivo
				
				Timestamp date = TimeUtil.addDays(this.getDateTrx(), -1);//fecha dia anterior
				
				MCashRemittance remit = MCashRemittance.forDateCash(getCtx(), date, doc.get_ID(), get_TrxName());
							
				if(remit !=null){
					
					MCashRemittance remitLast = MCashRemittance.forDateLastCash(getCtx(), date, doc.get_ID(), get_TrxName());
					
					if(remitLast==null) throw new AdempiereException ("No se encontro remesa de ultimo retiro y completa con fecha '" + date + "'");
					
				}						
				
			}			
		}
		//Fin OpenUp.
		
		//si es ultimo retiro, obtengo monto en empresa
		if(this.isLastRemittance() && !this.isChecked()){
			
			this.setMontoEmpresa();
			this.setChecked(true);
			
		}
		
		return true;
	}

	/***
	 * Obtiene y setea el monto total en empresa para ultima remesa del dia.
	 * OpenUp Ltda. Issue #5260
	 * @author Nicolas Sarlabos - 29/12/2015
	 * @see
	 * @return
	 */
	private void setMontoEmpresa() {
		
		String sql = "";
		BigDecimal amount = Env.ZERO;
		BigDecimal amtTotal = Env.ZERO;
		MBankAccount account = null;		
		
		MCashConfig config = MCashConfig.forClient(getCtx(), get_TrxName());
		
		if(config==null) throw new AdempiereException("No se pudo obtener parametros de caja");
		
		MCashConfigChest configChest = MCashConfigChest.forCurrency(getCtx(), 142, get_TrxName());
		
		if(configChest==null) throw new AdempiereException("No se pudo obtener Cofre en $ desde parametros de caja");	
		
		//obtengo monto de fondo fijo
		if(config.getC_BankAccount_ID_2()>0){

			account = new MBankAccount(getCtx(),config.getC_BankAccount_ID_2(),get_TrxName());

			sql = "select coalesce((sum(amtsourcecr)-sum(amtsourcedr)),0)" +
					" from uy_sum_accountstatus" + 
					" where c_bankaccount_id = " + account.get_ID() +
					" and datetrx <= '" + this.getDateTrx() + "'";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);
			amtTotal = amtTotal.add(amount);
		}
		
		//obtengo monto de cofre $
		account = (MBankAccount)configChest.getC_BankAccount();

		sql = "select coalesce((sum(amtsourcecr)-sum(amtsourcedr)),0)" +
				" from uy_sum_accountstatus" + 
				" where c_bankaccount_id = " + account.get_ID() +
				" and datetrx <= '" + this.getDateTrx() + "'";

		amount = DB.getSQLValueBDEx(get_TrxName(), sql);
		amtTotal = amtTotal.add(amount);
		
		//seteo monto de cajeras
		sql = "select coalesce(sum(amount),0)" +
				" from uy_cashcashier" +
				" where docstatus = 'CO' and c_currency_id = 142 and date_trunc ('day', datetrx) = '" + this.getDateTrx() + "'";

		amount = DB.getSQLValueBDEx(get_TrxName(), sql);
		amtTotal = amtTotal.add(amount);
		
		this.setAmount(amtTotal);		
		
		//obtengo diferencia entre el monto teorico y el real
		BigDecimal dif = config.getAmount().subtract(amtTotal);
		this.setAmount2(dif);
		this.setAmount3(dif);//seteo mismo valor en este campo para no perder la diferencia original
		
	}

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
		
		MDocType doc = (MDocType)this.getC_DocType();
		
		//realizo acciones en funcion del tipo de remesa
		if(doc.getValue()!=null){
			
			if(doc.getValue().equalsIgnoreCase("cashremitt")){
				
				this.verifyTotals();
				this.generateMovements();
				
			} else if(doc.getValue().equalsIgnoreCase("cashremittcheck")) {//remesa de cheques
				
				this.processCheckLines();
				
			} else if(doc.getValue().equalsIgnoreCase("cashremittvalues")) {//remesa de valores
				
				MPaymentRule luncheon = MPaymentRule.forValue(getCtx(), "luncheon", get_TrxName());
				MPaymentRule sodexo = MPaymentRule.forValue(getCtx(), "sodexo", get_TrxName());
				
				//si el medio de pago es luncheon o sodexo
				if(this.getUY_PaymentRule_ID()==luncheon.get_ID()){
					
					this.processValueLines("luncheon");					
					
				} else if (this.getUY_PaymentRule_ID()==sodexo.get_ID()) this.processValueLines("sodexo");
								
			}
			
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
	 * Recorre las lineas y realiza los depositos de cheques si corresponde.
	 * OpenUp Ltda. Issue #5142
	 * @author Nicolas Sarlabos - 16/12/2015
	 * @see
	 * @return
	 */
	private void processCheckLines() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		int accountID = 0, currID = 0;
		MMovBancariosHdr movHdr = null;
		MMovBancariosLine movLine = null;

		try{

			List<MCashRemittanceLine> lines = this.getLines();
			List<MCashRemittanceCharge> chargeLines = this.getChargeLines();
			
			if(lines.size()==0 && chargeLines.size()==0) throw new AdempiereException("Imposible solicitar, documento sin lineas");

			MCashConfig config = MCashConfig.forClient(getCtx(), get_TrxName());

			if(config == null) throw new AdempiereException("Imposible completar, no se encontraron parametros generales de caja");

			MDocType docDepTercero = MDocType.forValue(getCtx(), "bcodepcheqtercero", get_TrxName());

			//verifico existencia de tasa de cambio U$ para fecha de documento
			Timestamp date = TimeUtil.trunc(this.getDateTrx(), TimeUtil.TRUNC_DAY);
			BigDecimal divideRate = MConversionRate.getDivideRate(142, 100, date, 0, this.getAD_Client_ID(), this.getAD_Org_ID());
			if(divideRate.compareTo(Env.ZERO)==0) throw new AdempiereException("No se encontro tasa de cambio para fecha de documento");		

			//recorro lineas
			sql = "select rl.uy_cashremittanceline_id" +
					" from uy_cashremittanceline rl" +
					" inner join c_bankaccount ba on rl.c_bankaccount_id = ba.c_bankaccount_id" +
					" where rl.uy_cashremittance_id = " + this.get_ID() +
					" order by rl.c_bankaccount_id, rl.c_currency_id";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				MCashRemittanceLine rl = new MCashRemittanceLine(getCtx(), rs.getInt("uy_cashremittanceline_id"), get_TrxName());
				MBankAccount account = (MBankAccount)rl.getC_BankAccount();

				//creo el medio de pago
				MMediosPago mediopago = new MMediosPago(getCtx(), 0, get_TrxName());
				mediopago.setDateTrx(rl.getDateTrx());
				mediopago.setDateAcct(rl.getDateTrx());
				mediopago.setDueDate(rl.getDueDate());
				mediopago.setC_DocType_ID(MDocType.getDocType(Doc.DOCTYPE_Cheque));
				mediopago.setC_BPartner_ID(rl.getC_BPartner_ID());
				mediopago.setCheckNo(rl.getCheckNo());
				mediopago.setDocumentNo(rl.getCheckNo());
				mediopago.setC_Currency_ID(rl.getC_Currency_ID());
				mediopago.setPayAmt(rl.getAmount());
				mediopago.settipomp(X_UY_MediosPago.TIPOMP_Terceros);
				mediopago.setestado(X_UY_MediosPago.ESTADO_Cartera);
				mediopago.setoldstatus(X_UY_MediosPago.ESTADO_Cartera);
				mediopago.setPosted(true);
				mediopago.setProcessed(true);
				mediopago.setProcessing(false);
				mediopago.setDocAction(DocumentEngine.ACTION_None);
				mediopago.setDocStatus(DocumentEngine.STATUS_Completed);
				mediopago.setUY_PaymentRule_ID(rl.getUY_PaymentRule_ID());
				mediopago.setUY_MovBancariosHdr_ID(0);
				mediopago.setUY_MovBancariosLine_ID(0);

				//siempre queda en cartera en cuenta de tercero
				if(rl.getC_Currency_ID()==142){

					mediopago.setC_BankAccount_ID(config.getC_BankAccount_ID());

				} else if (rl.getC_Currency_ID()==100) mediopago.setC_BankAccount_ID(config.getC_BankAccount_ID_1());			

				mediopago.saveEx();

				rl.setUY_MediosPago_ID(mediopago.getUY_MediosPago_ID());//asocio ID de medio de pago a linea de remesa
				rl.saveEx();	

				//genero trazabilidad del medio de pago
				MCheckTracking track = new MCheckTracking(getCtx(), 0, get_TrxName());
				track.setUY_MediosPago_ID(mediopago.getUY_MediosPago_ID());
				track.setC_BPartner_ID(mediopago.getC_BPartner_ID());
				track.setC_DocType_ID(mediopago.getC_DocType_ID());
				track.setAD_Table_ID(mediopago.get_Table_ID());
				track.setRecord_ID(mediopago.get_ID());
				track.setDocumentNo(mediopago.getDocumentNo());
				track.setDateTrx(mediopago.getDateTrx());
				track.setCheckStatus(mediopago.getestado());
				track.setCheckOldStatus(mediopago.getoldstatus());
				track.setDocAction(DocumentEngine.ACTION_Complete);
				if (track.getCheckOldStatus() == null) track.setCheckOldStatus(track.getCheckStatus());
				if (track.getCheckOldStatus().equalsIgnoreCase("")) track.setCheckOldStatus(track.getCheckStatus());

				track.saveEx();

				int account_ID = rl.getC_BankAccount_ID();
				int curr_ID = rl.getC_Currency_ID();

				if(account.isPublic()){ //si la cuenta es propia, se realiza deposito

					//corte de control por cuenta bancaria y moneda
					if(accountID != account_ID || currID != curr_ID) {

						accountID = account_ID;
						currID = curr_ID;					

						if(movHdr != null){

							movHdr.processIt(MInvoice.DOCACTION_Complete);							

						}

						//creo cabezal de deposito de cheque tercero
						movHdr = new MMovBancariosHdr(getCtx(), 0, get_TrxName());
						movHdr.setC_DocType_ID(docDepTercero.get_ID());
						movHdr.setDateTrx(this.getDateTrx());
						movHdr.setC_BankAccount_ID(account_ID);
						movHdr.setC_Currency_ID(curr_ID);
						movHdr.setPosted(false);
						movHdr.setProcessed(false);
						movHdr.setProcessing(false);
						movHdr.setDocAction(DocumentEngine.ACTION_Complete);
						movHdr.setDocStatus(DocumentEngine.STATUS_Drafted);
						movHdr.saveEx();	
					}

					movLine = new MMovBancariosLine(getCtx(), 0, get_TrxName());
					movLine.setUY_MovBancariosHdr_ID(movHdr.get_ID());
					movLine.setUY_MediosPago_ID(mediopago.get_ID());
					movLine.setC_DocType_ID(mediopago.getC_DocType_ID());
					movLine.setDocumentNo(mediopago.getDocumentNo());
					movLine.setDateTrx(mediopago.getDateTrx());
					movLine.setDateAcct(mediopago.getDateAcct());
					movLine.setC_BankAccount_ID(mediopago.getC_BankAccount_ID());
					movLine.setC_Currency_ID(mediopago.getC_Currency_ID());
					movLine.setCheckNo(mediopago.getCheckNo());
					movLine.setDueDate(mediopago.getDueDate());
					movLine.setC_BPartner_ID(mediopago.getC_BPartner_ID());
					movLine.setestado(mediopago.getestado());
					movLine.setuy_totalamt(mediopago.getPayAmt());
					movLine.saveEx();

				}

			}

			if(movHdr != null){

				movHdr.processIt(MInvoice.DOCACTION_Complete);					
			}

			//proceso lineas de cobranzas, si las hay
			if(chargeLines.size() > 0){

				this.processCharges("Y");//para cuenta destino propia
				this.processCharges("N");//para cuenta destino NO propia	

			}					

		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	

	}
	
	/***
	 * Recorre las lineas y realiza los depositos de cheques si corresponde.
	 * OpenUp Ltda. Issue #5142
	 * @author Nicolas Sarlabos - 16/12/2015
	 * @see
	 * @return
	 */
	private void processValueLines(String ticketName) {

		String sql = "";
		MBankAccount account = null;
	
		List<MCashRemittanceLine> lines = this.getLines();
				
		if(lines.size()==0) throw new AdempiereException("Imposible solicitar, documento sin lineas");
		
		//obtengo suma de importes de lineas
		sql = "select coalesce(sum(amount),0)" +
				" from uy_cashremittanceline" +
				" where uy_cashremittance_id = " + this.get_ID();
		
		BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		//obtengo suma de cantidades de lineas
		sql = "select coalesce(sum(amount2),0)" +
				" from uy_cashremittanceline" +
				" where uy_cashremittance_id = " + this.get_ID();
		
		int qty = DB.getSQLValueEx(get_TrxName(), sql);
		
		MCashConfig config = MCashConfig.forClient(getCtx(), get_TrxName());

		if(config == null) throw new AdempiereException("Imposible completar, no se encontraron parametros generales de caja");
		
		//obtengo cuenta bancaria, dependiendo del tipo de ticket
		if(ticketName.equalsIgnoreCase("luncheon")){
			
			account = new MBankAccount(getCtx(),config.getC_BankAccount_ID_3(),get_TrxName());
					
		} else account = new MBankAccount(getCtx(),config.getC_BankAccount_ID_4(),get_TrxName());
					
		if(account!=null && account.get_ID() > 0){
			
			Timestamp date = TimeUtil.trunc(this.getDateTrx(), TimeUtil.TRUNC_DAY);
			
			//impacto movimiento
			MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
			sumba.setC_BankAccount_ID(account.get_ID());
			sumba.setDateTrx(date);
			sumba.setC_DocType_ID(this.getC_DocType_ID());
			sumba.setDocumentNo(this.getDocumentNo());
			sumba.setAD_Table_ID(I_UY_CashRemittance.Table_ID);
			sumba.setRecord_ID(this.get_ID());
			sumba.setDueDate(date);
			sumba.setC_Currency_ID(account.getC_Currency_ID());
			sumba.setamtdocument(amount);
			sumba.setAmtSourceCr(amount);
			sumba.setAmtSourceDr(Env.ZERO);
			sumba.setAmtAcctDr(Env.ZERO);
			sumba.setQty(qty);
			sumba.setUY_PaymentRule_ID(this.getUY_PaymentRule_ID());
			
			sumba.saveEx();		
				
		} else throw new AdempiereException("No se encontro cuenta bancaria para este medio de pago en parametros de caja");	
		
	}	

	/***
	 * Procesa lineas de cobranzas, realizando los depositos o cambios de cuenta destino.
	 * OpenUp Ltda. Issue #5258
	 * @author Nicolas Sarlabos - 15/01/2016
	 * @see
	 * @return
	 */
	private void processCharges(String isPublic) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MMediosPago mp = null;
		int accountID = 0;
		MBankAccount account = null;
		MMovBancariosHdr movHdr = null;
		MMovBancariosLine movLine = null;

		try{	

			MDocType docDepTercero = MDocType.forValue(getCtx(), "bcodepcheqtercero", get_TrxName());

			sql = "select rc.uy_mediospago_id, rc.c_bankaccount_id_1" +
					" from uy_cashremittancecharge rc" +
					" inner join c_bankaccount ba on rc.c_bankaccount_id_1 = ba.c_bankaccount_id" +
					" where rc.uy_cashremittance_id = " + this.get_ID() +
					" and ba.ispublic = '" + isPublic + "'" +
					" order by rc.c_bankaccount_id_1";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				mp = new MMediosPago(getCtx(),rs.getInt("uy_mediospago_id"),get_TrxName());
				account = new MBankAccount(getCtx(),rs.getInt("c_bankaccount_id_1"),get_TrxName());

				if(isPublic.equalsIgnoreCase("Y")){

					if(mp.getestado().equalsIgnoreCase("CAR")){

						int account_ID = account.get_ID();

						//corte de control por cuenta bancaria y moneda
						if(accountID != account_ID) {

							accountID = account_ID;

							if(movHdr != null){

								movHdr.processIt(MInvoice.DOCACTION_Complete);							

							}

							//creo cabezal de deposito de cheque tercero
							movHdr = new MMovBancariosHdr(getCtx(), 0, get_TrxName());
							movHdr.setC_DocType_ID(docDepTercero.get_ID());
							movHdr.setDateTrx(this.getDateTrx());
							movHdr.setC_BankAccount_ID(account_ID);
							movHdr.setC_Currency_ID(account.getC_Currency_ID());
							movHdr.setPosted(false);
							movHdr.setProcessed(false);
							movHdr.setProcessing(false);
							movHdr.setDocAction(DocumentEngine.ACTION_Complete);
							movHdr.setDocStatus(DocumentEngine.STATUS_Drafted);
							movHdr.saveEx();	
						}

						movLine = new MMovBancariosLine(getCtx(), 0, get_TrxName());
						movLine.setUY_MovBancariosHdr_ID(movHdr.get_ID());
						movLine.setUY_MediosPago_ID(mp.get_ID());
						movLine.setC_DocType_ID(mp.getC_DocType_ID());
						movLine.setDocumentNo(mp.getDocumentNo());
						movLine.setDateTrx(mp.getDateTrx());
						movLine.setDateAcct(mp.getDateAcct());
						movLine.setC_BankAccount_ID(mp.getC_BankAccount_ID());
						movLine.setC_Currency_ID(mp.getC_Currency_ID());
						movLine.setCheckNo(mp.getCheckNo());
						movLine.setDueDate(mp.getDueDate());
						movLine.setC_BPartner_ID(mp.getC_BPartner_ID());
						movLine.setestado(mp.getestado());
						movLine.setuy_totalamt(mp.getPayAmt());
						movLine.saveEx();

					}				

				} else {

					mp.setC_BankAccount_ID(rs.getInt("c_bankaccount_id_1"));
					mp.saveEx();			

				}	

			}
			
			//me aseguro de completar el ultimo deposito
			if(movHdr != null){

				movHdr.processIt(MInvoice.DOCACTION_Complete);					
			}

		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		

	}

	/***
	 * Verifica totales de montos y cantidades (cajas vs deposito).
	 * OpenUp Ltda. Issue #4449
	 * @author Nicolas Sarlabos - 27/07/2015
	 * @see
	 * @return
	 */
	private void verifyTotals() {
		
		List<MCashRemitSum> sumLines = this.getSumLines();
		
		if(sumLines.size()==0) throw new AdempiereException("Imposible completar, documento sin lineas");
		
		for(MCashRemitSum line : sumLines){
			
			MCurrency cur = (MCurrency)line.getC_Currency();
			
			//controlo montos
			if(line.getAmount().compareTo(line.getAmount2())!=0) 
				throw new AdempiereException("Importe total de cajas no coincide con el total depositado para la moneda '" + cur.getDescription() + "'");		
			
			//controlo cantidad de documentos
			if(line.getQtyCount()!=line.getQtyCount2()) 
				throw new AdempiereException("Cantidad total de documentos de cajas no coincide con el total depositado para la moneda '" + cur.getDescription() + "'");			
			
		}	
		
	}
	
	/***
	 * Metodo que genera los movimientos de cajas.
	 * OpenUp Ltda. Issue #4449
	 * @author Nicolas Sarlabos - 27/07/2015
	 * @see
	 * @return
	 */
	private void generateMovements() {

		MBankAccount account = null;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			List<MCashRemittanceLine> lines = this.getLines();
			List<MCashRemittanceCharge> chargeLines = this.getChargeLines();
			
			if(lines.size()==0 && chargeLines.size()==0) throw new AdempiereException("Imposible solicitar, documento sin lineas");
			
			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();

			//borro registros antes de generar nuevos
			DB.executeUpdateEx("delete from uy_sum_accountstatus where record_id = " + this.get_ID() + " and ad_table_id = " + I_UY_CashRemittance.Table_ID, get_TrxName());

			Timestamp date = TimeUtil.trunc(this.getDateTrx(), TimeUtil.TRUNC_DAY);

			List<MCashRemitBank> bankHdrLines = this.getBankHdrLines();//obtengo lista de cabezales de deposito

			//recorro cabezales de depositos
			for (MCashRemitBank line : bankHdrLines){

				account = (MBankAccount)line.getC_BankAccount();//instancio la cuenta bancaria

				List<MCashRemitBankLine> bankLines = line.getBankLines();

				//recorro lineas de depositos para el cabezal actual
				for(MCashRemitBankLine bankLine : bankLines){

					MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
					sumba.setC_BankAccount_ID(account.get_ID());
					sumba.setDateTrx(date);
					sumba.setC_DocType_ID(this.getC_DocType_ID());
					sumba.setDocumentNo(this.getDocumentNo());
					sumba.setAD_Table_ID(I_UY_CashRemittance.Table_ID);
					sumba.setRecord_ID(this.get_ID());
					sumba.setDueDate(date);

					if(bankLine.getNroSobre()!=null){

						if(!bankLine.getNroSobre().equalsIgnoreCase("")) sumba.setDescription("Sobre N°: " + bankLine.getNroSobre());		
					}

					sumba.setC_Currency_ID(account.getC_Currency_ID());
					sumba.setamtdocument(bankLine.getAmount());
					sumba.setAmtSourceCr(bankLine.getAmount());
					sumba.setAmtSourceDr(Env.ZERO);
					sumba.setAmtAcctDr(Env.ZERO);
					BigDecimal currencyRate = Env.ONE;
					if (account.getC_Currency_ID() != schema.getC_Currency_ID()){
						currencyRate = MConversionRate.getRate(account.getC_Currency_ID(), schema.getC_Currency_ID(), date, 0, this.getAD_Client_ID(), 0);
						if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + date);
					}
					sumba.setCurrencyRate(currencyRate);
					if (currencyRate.compareTo(Env.ONE) > 0){
						sumba.setAmtAcctDr(sumba.getAmtSourceDr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
						sumba.setAmtAcctCr(sumba.getAmtSourceCr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
					}
					else{
						sumba.setAmtAcctDr(sumba.getAmtAcctDr());
						sumba.setAmtAcctCr(sumba.getAmtAcctCr());
					}

					sumba.saveEx();				
				}			
			}	

			//proceso lineas de cobranzas, si las hay
			sql = "select c_bankaccount_id, coalesce(sum(amount),0) as amount" +
					" from uy_cashremittancecharge" +
					" where uy_cashremittance_id = " + this.get_ID() +
					" group by c_bankaccount_id";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){
				
				account = new MBankAccount(getCtx(), rs.getInt("c_bankaccount_id"), get_TrxName());//instancio la cuenta bancaria
				BigDecimal amount = rs.getBigDecimal("amount");
				
				MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(account.get_ID());
				sumba.setDateTrx(date);
				sumba.setC_DocType_ID(this.getC_DocType_ID());
				sumba.setDocumentNo(this.getDocumentNo());
				sumba.setAD_Table_ID(I_UY_CashRemittance.Table_ID);
				sumba.setRecord_ID(this.get_ID());
				sumba.setDueDate(date);
				sumba.setC_Currency_ID(account.getC_Currency_ID());
				sumba.setamtdocument(amount);
				sumba.setAmtSourceDr(amount);
				sumba.setAmtSourceCr(Env.ZERO);
				sumba.setAmtAcctCr(Env.ZERO);
				BigDecimal currencyRate = Env.ONE;
				if (account.getC_Currency_ID() != schema.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(account.getC_Currency_ID(), schema.getC_Currency_ID(), date, 0, this.getAD_Client_ID(), 0);
					if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + date);
				}
				sumba.setCurrencyRate(currencyRate);
				if (currencyRate.compareTo(Env.ONE) > 0){
					sumba.setAmtAcctDr(sumba.getAmtSourceDr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
					sumba.setAmtAcctCr(sumba.getAmtSourceCr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
				}
				else{
					sumba.setAmtAcctDr(sumba.getAmtAcctDr());
					sumba.setAmtAcctCr(sumba.getAmtAcctCr());
				}

				sumba.saveEx();	
				
			}

		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		

	}
	
	/***
	 * Obtiene y retorna lineas de totales de este documento.
	 * OpenUp Ltda. Issue #4449
	 * @author Nicolas Sarlabos - 27/07/2015
	 * @see
	 * @return
	 */
	public List<MCashRemitSum> getSumLines(){

		String whereClause = X_UY_CashRemitSum.COLUMNNAME_UY_CashRemittance_ID + "=" + this.get_ID();

		List<MCashRemitSum> lines = new Query(getCtx(), I_UY_CashRemitSum.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lineas de deposito de este documento.
	 * OpenUp Ltda. Issue #4449
	 * @author Nicolas Sarlabos - 27/07/2015
	 * @see
	 * @return
	 */
	public List<MCashRemitBank> getBankHdrLines(){

		String whereClause = X_UY_CashRemitBank.COLUMNNAME_UY_CashRemittance_ID + "=" + this.get_ID();

		List<MCashRemitBank> lines = new Query(getCtx(), I_UY_CashRemitBank.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lineas de cajas para este documento.
	 * OpenUp Ltda. Issue #4449
	 * @author Nicolas Sarlabos - 28/07/2015
	 * @see
	 * @return
	 */
	public List<MCashRemittanceLine> getLines(){

		String whereClause = X_UY_CashRemittanceLine.COLUMNNAME_UY_CashRemittance_ID + "=" + this.get_ID();

		List<MCashRemittanceLine> lines = new Query(getCtx(), I_UY_CashRemittanceLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

	/***
	 * Obtiene y retorna lineas de administracion de cobranzas para este documento.
	 * OpenUp Ltda. Issue #5258
	 * @author Nicolas Sarlabos - 14/01/2016
	 * @see
	 * @return
	 */
	public List<MCashRemittanceCharge> getChargeLines(){

		String whereClause = X_UY_CashRemittanceCharge.COLUMNNAME_UY_CashRemittance_ID + "=" + this.get_ID();

		List<MCashRemittanceCharge> lines = new Query(getCtx(), I_UY_CashRemittanceCharge.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	@Override
	public boolean voidIt() {
		
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		//se valida antes de anular
		this.validate("VO");
		
		//si es remesa de efectivo o cheques, debo deshacer movimientos
		MDocType doc = (MDocType)this.getC_DocType();
		
		if(doc.getValue()!=null){
			
			if(doc.getValue().equalsIgnoreCase("cashremittcheck")){
				
				this.processVoidRemittCheck();				
				
			} else {
				
				DB.executeUpdateEx("delete from uy_sum_accountstatus where record_id = " + this.get_ID() + 
						" and ad_table_id = " + I_UY_CashRemittance.Table_ID, get_TrxName());				
				
			}		
			
		}

		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);

		return true;
	}

	/***
	 * Metodo que elimina documentos al anular una remesa de cheques.
	 * OpenUp Ltda. Issue #5262
	 * @author Nicolas Sarlabos - 06/01/2016
	 * @see
	 * @return
	 */
	private void processVoidRemittCheck() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			MDocType docDepTercero = MDocType.forValue(getCtx(), "bcodepcheqtercero", get_TrxName());
			
			//obtengo depositos de cheques realizados anteriormente al completar, para grilla de cajas y de cobranzas
			sql = "select m.uy_movbancarioshdr_id" +
					" from uy_movbancarioshdr m" +
					" inner join uy_movbancariosline l on m.uy_movbancarioshdr_id = l.uy_movbancarioshdr_id" +
					" where l.uy_mediospago_id in (select uy_mediospago_id from uy_cashremittanceline" +
					" where uy_cashremittance_id  = " + this.get_ID() + ") and m.c_doctype_id = " + docDepTercero.get_ID() +
					" union" +
					" select m.uy_movbancarioshdr_id" +
					" from uy_movbancarioshdr m" +
					" inner join uy_movbancariosline l on m.uy_movbancarioshdr_id = l.uy_movbancarioshdr_id" +
					" where l.uy_mediospago_id in (select uy_mediospago_id from uy_cashremittancecharge" +
					" where uy_cashremittance_id  = " + this.get_ID() + ") and m.c_doctype_id = " + docDepTercero.get_ID();
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){
				
				MMovBancariosHdr mov = new MMovBancariosHdr(getCtx(),rs.getInt("uy_movbancarioshdr_id"),get_TrxName());
				
				//anulo el deposito de cheques
				if(mov.getDocStatus().equalsIgnoreCase("CO")){
					
					mov.processIt(MInvoice.DOCACTION_Void);					
				}
				
				//borro el deposito
				mov.deleteEx(true);			
				
			}
			
			//obtengo lineas de remesa
			List<MCashRemittanceLine> lines = this.getLines();
			
			//borro medios de pago
			for(MCashRemittanceLine rLine : lines){
				
				MMediosPago mp = new MMediosPago(getCtx(),rLine.getUY_MediosPago_ID(),get_TrxName());
				
				if(mp!=null && mp.get_ID()>0) mp.deleteEx(true);				
				
			}
			
			//dejo nulos los ID de medios de pago en lineas de esta remesa
			DB.executeUpdateEx("update uy_cashremittanceline set uy_mediospago_id = null where uy_cashremittance_id = " + this.get_ID(), get_TrxName());
			
			//para grilla de cobranzas, vuelvo a setear la cuenta bancaria original a los medios de pago con cuentas NO propias
			sql = "UPDATE uy_mediospago mp" +
					" SET c_bankaccount_id = rc.c_bankaccount_id_1 FROM uy_cashremittancecharge rc" +
					" INNER JOIN c_bankaccount ba ON rc.c_bankaccount_id_1 = ba.c_bankaccount_id" +
					" WHERE ba.ispublic = 'N' AND rc.uy_cashremittance_id = " + this.get_ID();
			
			DB.executeUpdateEx(sql, get_TrxName());
			
		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
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
		// Before reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (this.processMsg != null)
			return false;
		
		//se valida antes de reactivar
		this.validate("RE");	
		
		//si es remesa de efectivo o cheques, debo deshacer movimientos
		MDocType doc = (MDocType)this.getC_DocType();
		
		if(doc.getValue()!=null){
			
			if(doc.getValue().equalsIgnoreCase("cashremitt")){
				
				DB.executeUpdateEx("delete from uy_sum_accountstatus where record_id = " + this.get_ID() + 
						" and ad_table_id = " + I_UY_CashRemittance.Table_ID, get_TrxName());
				
			} 
			
		}

		// After reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (this.processMsg != null)
			return false;

		// Seteo estado de documento
		this.setProcessed(false);
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);

		return true;
	}

	/***
	 * Realiza validaciones antes de reactivar o anular.
	 * OpenUp Ltda. Issue #5262
	 * @author Nicolas Sarlabos - 30/12/2015
	 * @see
	 * @return
	 */
	private void validate(String action) {
		
		String texto = "";
		
		if(action.equalsIgnoreCase("RE")){
			
			texto = "reactivar";			
			
		} else texto = "anular";
		
		Timestamp date = TimeUtil.trunc(this.getDateTrx(), TimeUtil.TRUNC_DAY);
		
		MCashCount count = MCashCount.forDateRemit(getCtx(), date, get_TrxName());
		
		if(count!=null){
			
			if(count.getDocStatus().equalsIgnoreCase("AY") || count.getDocStatus().equalsIgnoreCase("IP")){
				
				ADialog.info(0,null,"Existe el documento de arqueo nº " + count.getDocumentNo() + 
						" en proceso, deberá reprocesar el mismo luego de completar esta remesa");				
				
			} else if(count.getDocStatus().equalsIgnoreCase("CO")) 
				throw new AdempiereException("Imposible " + texto + ", existe el documento de arqueo nº " + count.getDocumentNo() + " en estado Completo");			
			
		}	
		
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

	@Override
	public int getDynamicWFResponsibleID(MWFNode node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setApprovalInfo(int AD_WF_Responsible_ID, String textMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getWFActivityDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWFActivityHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean IsParcialApproved() {
	
		return this.isParcialApproved;
	}

	@Override
	public void processAutomaticApproval() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processAutomaticComplete() {
		
		try 
		{
			// Completo de manera automatico al aprobar documento
			if (!this.processIt(ACTION_Complete)){
				if (this.getProcessMsg() != null){
					throw new AdempiereException(this.getProcessMsg());
				}
			}
			else{
				this.saveEx();
			}
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
	}
	
	/***
	 * Obtiene y retorna documento de remesa Efectivo de ultimo retiro en estado completo para fecha y documento recibidos.
	 * OpenUp Ltda. Issue #5545
	 * @author Nicolas Sarlabos - 29/02/2016
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MCashRemittance forDateLastCash(Properties ctx, Timestamp date, int docTypeID, String trxName){

		String whereClause = X_UY_CashRemittance.COLUMNNAME_DateTrx + "='" + date + "' AND " + X_UY_CashRemittance.COLUMNNAME_DocStatus + " = 'CO' AND " +
				X_UY_CashRemittance.COLUMNNAME_IsLastRemittance + "='Y' AND " + X_UY_CashRemittance.COLUMNNAME_C_DocType_ID + "=" + docTypeID;

		MCashRemittance model = new Query(ctx, I_UY_CashRemittance.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return model;
	}

	/***
	 * Obtiene y retorna documento de remesa Efectivo en estado completo para fecha y documento recibidos.
	 * OpenUp Ltda. Issue #5545
	 * @author Nicolas Sarlabos - 29/02/2016
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MCashRemittance forDateCash(Properties ctx, Timestamp date, int docTypeID, String trxName){

		String whereClause = X_UY_CashRemittance.COLUMNNAME_DateTrx + "='" + date + "' AND " + X_UY_CashRemittance.COLUMNNAME_DocStatus + " = 'CO' AND " +
				X_UY_CashRemittance.COLUMNNAME_C_DocType_ID + "=" + docTypeID;

		MCashRemittance model = new Query(ctx, I_UY_CashRemittance.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return model;
	}
}
