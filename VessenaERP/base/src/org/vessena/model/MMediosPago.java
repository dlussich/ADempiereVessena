package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactLine;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MLocation;
import org.compiere.model.MPeriod;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_DocType;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.util.Converter;

public final class MMediosPago extends X_UY_MediosPago implements DocAction {

	private static final long serialVersionUID = 9151475772054788222L;

	private String processMsg = null;
	private boolean justPrepared = false;
	
	public MMediosPago(Properties ctx, int UY_MediosPago_ID, String trxName) {
		super(ctx, UY_MediosPago_ID, trxName);
		//  New
		if (UY_MediosPago_ID == 0)
		{
			setDocAction(DOCACTION_Complete);
			setDocStatus(DOCSTATUS_Drafted);
			setProcessed(false);
			setPosted (false);
			setPayAmt(Env.ZERO);
			setDateTrx (new Timestamp(System.currentTimeMillis()));
			setDateAcct (getDateTrx());
		}
	}

	public MMediosPago (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		try {

			// Verifico que tenga importe
			if ((this.getPayAmt() == null) || (this.getPayAmt().compareTo(Env.ZERO) == 0)){
				throw new AdempiereException("Debe indicar importe");
			}

			// Me aseguro de tener medio de pago
			if (this.getUY_PaymentRule_ID() <= 0){
				MBankAccount ba = new MBankAccount(getCtx(), this.getC_BankAccount_ID(), null);
				this.setUY_PaymentRule_ID(ba.getUY_PaymentRule_ID());	
			}
			
			// Setea tendertype segun tipo que tiene la forma de pago
			MPaymentRule rule = new MPaymentRule(getCtx(), this.getUY_PaymentRule_ID(), null);
			if (rule.get_ID() > 0){
				if (rule.getpaymentruletype().equalsIgnoreCase(X_UY_PaymentRule.PAYMENTRULETYPE_CONTADO)){
					this.setTenderType(TENDERTYPE_Cash);
				}
				else{
					this.setTenderType(TENDERTYPE_Check);
				}
			}
			else{
				this.setTenderType(TENDERTYPE_Check);
			}
			
			// Seteo checkno desde libreta
			if (this.getUY_CheckReamLine_ID() > 0){
				MCheckReamLine rline = (MCheckReamLine)this.getUY_CheckReamLine();
				this.setCheckNo(String.valueOf(rline.getCheckNo()));
			}
			
			if (newRecord){
				this.validateCheckNo();
			}
			
			if (this.getDivideRate() != null){
				if (this.getDivideRate().compareTo(Env.ZERO) <= 0){
					// Obtengo moneda nacional del esquema contable
					MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
					MAcctSchema schema = client.getAcctSchema();
					int idMonedaNacional = schema.getC_Currency_ID();

					// Seteo tasa de cambio segun moneda de la cuenta
					if (this.getC_Currency_ID() != idMonedaNacional){
						this.setDivideRate(MConversionRate.getDivideRate(idMonedaNacional, this.getC_Currency_ID(), 
								this.getDateTrx(), 0, this.getAD_Client_ID(), this.getAD_Org_ID()));
					}
					else{
						this.setDivideRate(Env.ONE);
					}
				}
			}
			
			// Seteo literal del cheque
			this.setLiteral();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return true;

	}
	
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
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
		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null) return DocAction.STATUS_Invalid;

		if(!this.isInitialLoad()){
			MPeriod.testPeriodOpen(getCtx(), getDateTrx(), getC_DocType_ID(), getAD_Org_ID());
		}		

		// Estado emitido
		this.setestado(X_UY_MediosPago.ESTADO_Emitido);
		
		// Marco la linea de resma como utilizada		
		MCheckReamLine reamline = (MCheckReamLine)this.getUY_CheckReamLine();
		if (reamline != null){
			if (reamline.get_ID() > 0){
				reamline.setisused(true);
				reamline.setUY_MediosPago_ID(this.get_ID());
				reamline.saveEx();
			}
		}
		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null) return DocAction.STATUS_Invalid;
		
		setDefiniteDocumentNo();

		if(this.isInitialLoad()) setPosted(true);

		// Refresco atributos
		this.setDateAcct(this.getDateTrx());
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		
		//OpenUp Nicolas Sarlabos issue #895 19/10/2011
		// Si NO es carga inicial -> Verifico periodo contable 
		if(!this.isInitialLoad()){
			if (!MPeriod.isOpen(getCtx(), getDateAcct(),X_C_DocType.DOCBASETYPE_Cheque, getAD_Org_ID())) 
			{
				this.processMsg = "@PeriodClosed@";
				return DocAction.STATUS_Invalid;
			}
		}
		//fin OpenUp Nicolas Sarlabos issue #895 19/10/2011

		// Todo bien
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
		
	}

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean reActivateIt() {

		// Before reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (this.processMsg != null)
			return false;

		if(!this.isInitialLoad()){
			MPeriod.testPeriodOpen(getCtx(), getDateTrx(), getC_DocType_ID(), getAD_Org_ID());
		}
		
		// Verifico si los cheques tienen movimientos posteriores
		if (!this.validacionCheques()) return false;

		// Elimino de tabla sum de movimientos bancarios la entrada por este medio de pago.
		String action = " delete from uy_sum_accountstatus " +
						" where uy_mediospago_id =" + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		// Elimino asientos contables
		FactLine.deleteFact(X_UY_MediosPago.Table_ID, this.get_ID(), get_TrxName());

		if(this.getUY_CheckReamLine_ID() > 0){
			
			MCheckReamLine rLine = new MCheckReamLine(getCtx(),this.getUY_CheckReamLine_ID(),get_TrxName());
			//MCheckReam ream = new MCheckReam(getCtx(),rLine.getUY_CheckReam_ID(),get_TrxName());
			
			DB.executeUpdateEx("update uy_checkreamline set isused = 'N', uy_mediospago_id = null where uy_checkreamline_id = " + rLine.get_ID(), get_TrxName());
			
		}		
		
		// After reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,	ModelValidator.TIMING_AFTER_REACTIVATE);
		if (this.processMsg != null)
			return false;
		
		// Seteo estado de documento
		this.setProcessed(false);
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);

		return true;

	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean voidIt() {
		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null) return false;
	
		if(!this.isInitialLoad()){
			MPeriod.testPeriodOpen(getCtx(), getDateTrx(), getC_DocType_ID(), getAD_Org_ID());
		}
		
		// Verifico si los cheques tienen movimientos posteriores
		if (!this.validacionCheques()) return false;

		// Elimino de tabla sum de movimientos bancarios la entrada por este medio de pago.
		String action = " delete from uy_sum_accountstatus " +
						" where uy_mediospago_id =" + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		// Elimino asientos contables
		FactLine.deleteFact(X_UY_MediosPago.Table_ID, this.get_ID(), get_TrxName());

		//OpenUp. Nicolas Sarlabos. 13/10/2015. #3835.
		//Seteo numero de cheque anulado, utilizando secuencia
		//obtengo siguiente numero de secuencia
		BigDecimal nextVal = DB.getSQLValueBDEx(get_TrxName(), "select currentnext from ad_sequence where lower(name) like 'uy_mediospago_vo'");
				
		String value = "VO" + nextVal.toString() + "-" + this.getCheckNo();
				
		this.setCheckNo(value);
		
		//actualizo proximo valor de la secuencia
		DB.executeUpdateEx("update ad_sequence set currentnext = currentnext + incrementno where lower(name) like 'uy_mediospago_vo'", get_TrxName());
		//Fin #3835.
		
		/*
		//OpenUp. Nicolas Sarlabos. 25/09/2014. #2891.Si es una libreta, actualizo el cheque como NO usado.
		if(this.getUY_CheckReamLine_ID() > 0){
			
			MCheckReamLine rLine = new MCheckReamLine(getCtx(),this.getUY_CheckReamLine_ID(),get_TrxName());
			MCheckReam ream = new MCheckReam(getCtx(),rLine.getUY_CheckReam_ID(),get_TrxName());
			
			//pregunto si es una libreta de cheques
			if(!ream.get_ValueAsBoolean("IsReam")) 
				DB.executeUpdateEx("update uy_checkreamline set isused = 'N', uy_mediospago_id = null where uy_checkreamline_id = " + rLine.get_ID(), get_TrxName());			
		}		
		//Fin OpenUp.
		 */

		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null) return false;

		setProcessed(true);
		setPosted(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);

		return true;
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

	
	/**
	 * OpenUp.	
	 * Descripcion : Valida que los cheques no tengan movimientos posteriores.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 01/11/2010
	 */
	private boolean validacionCheques() {
		
		if (this.getestado() == null) return true;
		
		if (!this.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Emitido)){
			this.processMsg = "No es posible revertir el Cheque ya que tiene movimientos posteriores.";
			return false;
		}
		
		return true;
	}
	
	/**
	 * OpenUp. Gabriel Vila. 16/12/2011. 
	 * Valida que no exista un cheque con mismo numero y cuenta bancaria.
	 */
	private void validateCheckNo(){

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		//OpenUp. Nicolas SArlabos. 25/09/2014. #2891. Se modifica sql para filtrar por estado de documento
		sql = " SELECT * from UY_MediosPago " + " where checkno = ?"
				+ " and c_bankaccount_id = ? " + " and docstatus <> 'VO'";
		//Fin OpenUp.
		
		try {

			pstmt = DB.prepareStatement(sql, null); // Create the statement
			pstmt.setString(1, this.getCheckNo());
			pstmt.setInt(2, this.getC_BankAccount_ID());
			rs = pstmt.executeQuery();

			// Read the first record and get a new object
			if (rs.next()) {
				throw new Exception("Ya existe un cheque con el numero ingresado para este mismo banco");
			}

		} catch (Exception e) {
			throw new AdempiereException(e.toString());
		}
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/***
	 * Setea documento por defecto para este documento.
	 * OpenUp Ltda. Issue #351 
	 * @author Gabriel Vila - 21/02/2012
	 * @see
	 */
	public void setDefaultDocType() {
		
		MDocType doc = MDocType.forValue(getCtx(), "emicheque", null);
		if (doc.get_ID() > 0) this.setC_DocType_ID(doc.get_ID());
		
	}
	
	/***
	 * Setea literal del cheque en dos lineas.
	 * OpenUp Ltda. Issue #600 
	 * @author Gabriel Vila - 03/04/2013
	 * @see
	 */
	public void setLiteral(){

		try{
			
			this.setCheckLetter(null);
			this.setCheckLetter2(null);
			
			Converter conv = new Converter();
			
			int top1 = MSysConfig.getIntValue("UY_CHECK_LITERAL_LENGTH_1", 70, getAD_Client_ID());
			//int top2 = MSysConfig.getIntValue("UY_CHECK_LITERAL_LENGTH_2", 40, getAD_Client_ID());

			//OpenUp. Raul Capecce. 20/01/2015. # Cheque bradesco Comas.
			// Si el Pais del banco que tiene la cuenta es Brasil, imprimo el literal en Portugues, en caso contrario lo imprimo en espa;ol
			String literal = "";
			
			MBankAccount ba = (MBankAccount)this.getC_BankAccount();
			MBank bank = (MBank)ba.getC_Bank();
			if (bank.getC_Location_ID() >0){
				MLocation location = (MLocation)bank.getC_Location();
				if (location.getC_Country_ID() == 139){ // Si tengo c_location y el pais es Brasil, imprimo en portugues
					literal = Converter.convertirPortuguese(this.getPayAmt());
				} else { // si el id no es de Brasil, imprimo en espa;ol
					literal = conv.getStringOfBigDecimal(this.getPayAmt());
				}
			} else { // si no tengo c_location, imprimo en espa;ol
				literal = conv.getStringOfBigDecimal(this.getPayAmt());
			}
			// Fin # Cheque bradesco Comas.
			
			System.out.println(literal + " : " + this.getPayAmt());
			
			if (literal.length() <= top1){
				this.setCheckLetter(literal);
				return;
			}
			
			int posHasta1 = literal.indexOf(" ", top1);
			if (posHasta1 < 0) posHasta1 = top1;
			int posDesde1 = literal.lastIndexOf(" ", top1);
			if (posDesde1 < 0) posDesde1 = top1;
			
			int posSep1 = ((posHasta1 - top1) < ((posDesde1 - top1) * -1)) ? posHasta1 : posDesde1;
			
			this.setCheckLetter(literal.substring(0, posSep1));
			this.setCheckLetter2(literal.substring(posSep1 + 1));
			
		}		
		catch (Exception e){
			throw new AdempiereException();
		}
	}
	
}
