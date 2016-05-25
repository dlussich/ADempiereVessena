package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.acct.FactLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Payment;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MMovBancariosHdr extends X_UY_MovBancariosHdr implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6555519259283812398L;
	private String processMsg = null;
	private boolean justPrepared = false;
	
	
	public MMovBancariosHdr(Properties ctx, int UY_MovBancariosHdr_ID, String trxName) {
		
		super(ctx, UY_MovBancariosHdr_ID, trxName);

		if (UY_MovBancariosHdr_ID == 0)
		{
			setDocAction(DOCACTION_Complete);
			setDocStatus(DOCSTATUS_Drafted);
			setProcessed(false);
			setPosted (false);
			setuy_totalmn(Env.ZERO);
			setuy_totalme(Env.ZERO);
			setUY_SubTotal(Env.ZERO);
			setDateTrx (new Timestamp(System.currentTimeMillis()));
			setDateAcct (getDateTrx());
		}
	}

	public MMovBancariosHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
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

		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null) return DocAction.STATUS_Invalid;
		
		if(!this.isInitialLoad()){
			MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		}

		// Obtengo informacion del documento de esta transaccion
		MDocType doc = new MDocType(getCtx(), this.getC_DocType_ID(), null);
		String docBaseType = doc.getDocBaseType();
		
		// Completo segun tipo de documento de esta transaccion
		
		// Rechazos de cheques de teceros y propios
		if ((docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_RechazoChequesPropios)) || (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_RechazoChequesTerceros))){			
			if (!this.completeRechazoCheques()) return DocAction.STATUS_Invalid;
		}

		// Cambio de cheques de terceros
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_CambioChequeTercero)){
			if (!this.completeCambioChequeTercero()) return DocAction.STATUS_Invalid;
		}
		
		// Cambio de cheques propios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_CambioChequePropio)){
			if (!this.completeCambioChequePropio()) return DocAction.STATUS_Invalid;
		}

		// Cobro de conformes
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_CobroConformes)){
			if (!this.completeCobroConformes()) return DocAction.STATUS_Invalid;
		}

		// Vales bancarios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_ValesBancarios)){
			if (!this.completeValesBancarios()) return DocAction.STATUS_Invalid;
		}		

		// Pago de vales bancarios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_PagoValeBancario)){
			if (!this.completePagoValeBancario()) return DocAction.STATUS_Invalid;
		}		

		// Conciliacion de cheques propios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_ConciliacionChequesPropios)){
			if (!this.completeConciliacionChequePropio()) return DocAction.STATUS_Invalid;
		}

		// Descuento de cheques de terceros
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DescuentoChequesTerceros)){
			if (!this.completeDescuentoChequeTercero()) return DocAction.STATUS_Invalid;
		}

		// Deposito de cheques de terceros
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DepositoChequeTerceros)){
			if (!this.completeDepositoChequeTercero()) return DocAction.STATUS_Invalid;
		}

		// Deposito de cheques propios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DepositoChequePropio)){
			if (!this.completeDepositoChequePropio()) return DocAction.STATUS_Invalid;
		}

		
		// Deposito en efectivo
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DepositoEfectivo)){
			if (!this.completeDepositoEfectivo()) return DocAction.STATUS_Invalid;
		}
		
		//OpUp. Nicolas Sarlabos. 22/02/2016. #5264.
		// Deposito de ticket alimentacion
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DepositoTicket)){
			if (!this.completeDepositoTicket()) return DocAction.STATUS_Invalid;
		}
		//Fin OpUp.

		// Gastos bancarios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_GastosBancarios)){
			if (!this.completeGastosBancarios()) return DocAction.STATUS_Invalid;
		}

		// Intereses bancarios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_InteresesBancarios)){
			if (!this.completeInteresesBancarios()) return DocAction.STATUS_Invalid;
		}

		// Transferencias bancarias
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_Transferencias)){
			if (!this.completeTransferencias(doc)) return DocAction.STATUS_Invalid;
		}

		// Timing After Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null) return DocAction.STATUS_Invalid;
		
		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		// Refresco estados
		//OpenUp Nicolas Sarlabos issue #895 31/10/2011
		if(this.isInitialLoad()) setPosted(true);
		//fin OpenUp Nicolas Sarlabos issue #895 31/10/2011
		
		this.setProcessed(true);
		this.setDateAcct(getDateTrx());
		this.setDocStatus(DOCSTATUS_Completed);
		this.setDocAction(DOCACTION_None);

		this.saveEx();
		
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
		
		//OpenUp Nicolas Sarlabos issue #895 18/10/2011
		// Si NO es carga inicial -> Verifico periodo contable 
		if(!this.isInitialLoad()){
			if (!MPeriod.isOpen(getCtx(), getDateAcct(),X_C_DocType.DOCBASETYPE_Cheque, getAD_Org_ID())) 
			{
				this.processMsg = "@PeriodClosed@";
				return DocAction.STATUS_Invalid;
			}
		}	
		//fin OpenUp Nicolas Sarlabos issue #895 18/10/2011

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
		// TODO Auto-generated method stub
		return false;
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
		
		// Timing Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null) return false;

		if(!this.isInitialLoad()){
			MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());//OpenUp M.R. 18-08-2011 Issue#723 se Agrega linea que comprueba si el periodo esta cerrado para este tipo de documento
		}

		// Obtengo informacion del documento de esta transaccion
		MDocType doc = new MDocType(getCtx(), this.getC_DocType_ID(), get_TrxName());
		String docBaseType = doc.getDocBaseType();
		
		// Anulo segun tipo de documento de esta transaccion
		
		// Rechazos de cheques de teceros y propios
		if ((docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_RechazoChequesPropios)) || (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_RechazoChequesTerceros))){			
			if (!this.voidRechazoCheques()) return false;
		}

		// Cambio de cheques de terceros
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_CambioChequeTercero)){
			if (!this.voidCambioChequeTercero()) return false;
		}
		
		// Cambio de cheques propios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_CambioChequePropio)){
			if (!this.voidCambioChequePropio()) return false;
		}

		// Cobro de conformes
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_CobroConformes)){
			if (!this.voidCobroConformes()) return false;
		}

		// Vales bancarios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_ValesBancarios)){
			if (!this.voidValesBancarios()) return false;
		}		

		// Pago de vales bancarios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_PagoValeBancario)){
			if (!this.voidPagoValeBancario()) return false;
		}		

		// Conciliacion de cheques propios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_ConciliacionChequesPropios)){
			if (!this.voidConciliacionChequePropio()) return false;
		}

		// Descuento de cheques de terceros
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DescuentoChequesTerceros)){
			if (!this.voidDescuentoChequeTercero()) return false;
		}
		
		// Deposito de cheques de terceros
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DepositoChequeTerceros)){
			if (!this.voidDepositoChequeTercero()) return false;
		}
		
		//OpenUp. Nicolas Sarlabos. 17/02/2016. #5482.
		// Deposito de cheques propios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DepositoChequePropio)){
			if (!this.voidDepositoChequePropio()) return false;
		}
		//Fin OpenUp.

		// Deposito en efectivo
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DepositoEfectivo)){
			if (!this.voidDepositoEfectivo()) return false;
		}
		
		//OpUp. Nicolas Sarlabos. 22/02/2016. #5264.
		// Deposito de ticket alimentacion
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DepositoTicket)){
			if (!this.voidDepositoTicket()) return false;
		}
		//Fin OpUp.

		// Gastos bancarios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_GastosBancarios)){
			if (!this.voidGastosBancarios()) return false;
		}

		// Intereses bancarios
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_InteresesBancarios)){
			if (!this.voidInteresesBancarios()) return false;
		}

		// Transferencias bancarias
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_Transferencias)){
			if (!this.voidTransferencias(doc)) return false;
		}
	
		// Elimino asientos contables
		FactLine.deleteFact(X_UY_MovBancariosHdr.Table_ID, this.get_ID(), get_TrxName());

		// Timing After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		this.setDocAction(DOCACTION_None);
		this.setDocStatus(DOCSTATUS_Voided);
		this.setProcessed(true);
		this.setPosted(true);
		this.saveEx();

		return true;
	}

	
	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Anulacion de transferencia bancaria.
	 * @return
	 */
	private boolean voidTransferencias(MDocType doc) {

		if((doc.getValue()!=null && !doc.getValue().equalsIgnoreCase("transfdir")) || (doc.getValue()==null)){
			
			// Recorro lineas 
			ArrayList<MMovBancariosLine> lines = this.getLines();
			if (lines.size() == 0) {
				this.processMsg = "Esta transacción no tiene lineas. No puede anularse.";
				return false;
			}

			for (int i = 0; i < lines.size(); i++) {
			
				MMovBancariosLine linea = lines.get(i);
				if (linea.getUY_MediosPago_ID() > 0) {
					
					MMediosPago mp = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());

					if (!mp.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Transferido)){
						this.processMsg = "El cheque numero : " + mp.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + ", no esta en estado Transferido.";
						return false;
					}
					
					// Obtengo tracking de cheque en estado anterior
					MCheckTracking trackLine = this.getCheckTracking(mp.getUY_MediosPago_ID(), mp.getoldstatus(), get_TrxName());
					String oldStatusChequeLinea = "";
					if (trackLine.get_ID() > 0){
						oldStatusChequeLinea = trackLine.getCheckOldStatus();
						if (oldStatusChequeLinea == null) oldStatusChequeLinea = "";
					}
					if (oldStatusChequeLinea.equalsIgnoreCase("")) oldStatusChequeLinea = X_UY_MediosPago.ESTADO_Emitido;
					
					mp.setestado(X_UY_MediosPago.ESTADO_Emitido);
					mp.setoldstatus(X_UY_MediosPago.ESTADO_Emitido);

					if (trackLine.getAD_Table_ID() == this.get_Table_ID()){
						mp.setUY_MovBancariosHdr_ID(trackLine.getRecord_ID());
						mp.setUY_MovBancariosLine_ID(0); // TODO : Setear bien el id de la linea del movimiento
					}
					else{
						mp.setUY_MovBancariosHdr_ID(0);
						mp.setUY_MovBancariosLine_ID(0);
					}
					
					mp.saveEx();
				}
				linea.setProcessed(true);
				linea.setDocStatus(DOCSTATUS_Voided);			
				linea.saveEx();
			}			
		}
		
		//OpenUp. Nicolas Sarlabos. 22/09/2014. #2891. Se eliminan los registros en tabla sumarizadora de estados de cuenta.
		String action = " delete from uy_sum_accountstatus where ad_table_id = " + I_UY_MovBancariosHdr.Table_ID +
				" and record_id = " + this.get_ID();
		DB.executeUpdateEx(action, this.get_TrxName());
		//Fin OpenUp.

		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Anulacion de intereses bancarios.
	 * @return
	 */
	private boolean voidInteresesBancarios() {

		ArrayList<MMovBancariosLine> lines = this.getLines();

		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Voided);			
			linea.saveEx();
		}
		
		//OpenUp. Nicolas Sarlabos. 22/09/2014. #2891. Se eliminan los registros en tabla sumarizadora de estados de cuenta.
		String action = " delete from uy_sum_accountstatus where ad_table_id = " + I_UY_MovBancariosHdr.Table_ID +
				" and record_id = " + this.get_ID();
		DB.executeUpdateEx(action, this.get_TrxName());
		//Fin OpenUp.
		
		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Anulacion de gastos bancarios.
	 * @return
	 */
	private boolean voidGastosBancarios() {

		ArrayList<MMovBancariosLine> lines = this.getLines();

		// Elimino transaccion de tabla sum
		String action = " delete from uy_sum_accountstatus where ad_table_id = " + I_UY_MovBancariosHdr.Table_ID +
				        " and record_id = " + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Voided);			
			linea.saveEx();
		}
		
		return true;
	}

	
	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Anulacion de deposito en efectivo.
	 * @return
	 */
	private boolean voidDepositoEfectivo() {

		ArrayList<MMovBancariosLine> lines = this.getLines();

		// Elimino transaccion de tabla sum
		String action = " delete from uy_sum_accountstatus where ad_table_id = " + I_UY_MovBancariosHdr.Table_ID +
				        " and record_id = " + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Voided);			
			linea.saveEx();
		}
		
		return true;
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 22/02/2016.
	 * Anulacion de deposito de ticket alimentacion.
	 * @return
	 */
	private boolean voidDepositoTicket() {

		ArrayList<MMovBancariosLine> lines = this.getLines();

		// Elimino transaccion de tabla sum
		String action = " delete from uy_sum_accountstatus where ad_table_id = " + I_UY_MovBancariosHdr.Table_ID +
				        " and record_id = " + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Voided);			
			linea.saveEx();
		}
		
		return true;
	}
	
	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Anulacion de deposito de cheques de terceros.
	 * @return
	 */
	private boolean voidDepositoChequeTercero() {

		// Recorro lineas 
		ArrayList<MMovBancariosLine> lines = this.getLines();
		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede anularse.";
			return false;
		}

		for (int i = 0; i < lines.size(); i++) {
		
			MMovBancariosLine linea = lines.get(i);
			if (linea.getUY_MediosPago_ID() > 0) {
				
				MMediosPago mp = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());

				if (!mp.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Depositado)){
					this.processMsg = "El cheque numero : " + mp.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + ", no esta en estado Depositado.";
					return false;
				}
			
				// Obtengo tracking de cheque en estado anterior
				MCheckTracking trackLine = this.getCheckTracking(mp.getUY_MediosPago_ID(), mp.getoldstatus(), get_TrxName());
				String oldStatusChequeLinea = "";
				if (trackLine.get_ID() > 0){
					oldStatusChequeLinea = trackLine.getCheckOldStatus();
					if (oldStatusChequeLinea == null) oldStatusChequeLinea = "";
				}
				if (oldStatusChequeLinea.equalsIgnoreCase("")) oldStatusChequeLinea = X_UY_MediosPago.ESTADO_Cartera;
				
				mp.setestado(mp.getoldstatus());
				mp.setoldstatus(oldStatusChequeLinea);

				if (trackLine.getAD_Table_ID() == this.get_Table_ID()){
					mp.setUY_MovBancariosHdr_ID(trackLine.getRecord_ID());
					mp.setUY_MovBancariosLine_ID(0); // TODO : Setear bien el id de la linea del movimiento
				}
				else{
					mp.setUY_MovBancariosHdr_ID(0);
					mp.setUY_MovBancariosLine_ID(0);
				}
				
				mp.saveEx();
				
				
				// Debo eliminar deposito de la tabla sum de estado de cuenta bancaria
				// y luego insertar un registro para este cheque en el estado anterior.
				String action = " delete from uy_sum_accountstatus " +
						" where uy_mediospago_id =" + mp.get_ID();
				DB.executeUpdateEx(action, get_TrxName());
				
				// Obtengo modelo origen de la transaccion anterior a este deposito
				boolean saveAnterior = true;
				MPayment payment = null;
				MMovBancariosHdr movHdr = null;
				MInvoice invoice = null;
				int cDocTypeID = 0, adTableID = 0, recordID = 0, cCurrencyID = 0;
				String documentNo = "", description = "";
				if (trackLine.get_ID() > 0){
					// Si fue un Recibo de Cobro
					if (trackLine.getAD_Table_ID() == I_C_Payment.Table_ID){
						payment = new MPayment(getCtx(), trackLine.getRecord_ID(), null);
						if (!payment.getDocStatus().equalsIgnoreCase(DOCSTATUS_Completed)){
							saveAnterior = false;
						}
						else{
							cDocTypeID = payment.getC_DocType_ID();
							adTableID = payment.get_Table_ID();
							recordID = payment.get_ID();
							cCurrencyID = payment.getC_Currency_ID();
							documentNo = payment.getDocumentNo();
							description = payment.getDescription();
						}
					}
					else if (trackLine.getAD_Table_ID() == I_UY_MovBancariosHdr.Table_ID){
						movHdr = new MMovBancariosHdr(getCtx(), trackLine.getRecord_ID(), null);
						if (!movHdr.getDocStatus().equalsIgnoreCase(DOCSTATUS_Completed)){
							saveAnterior = false;
						}
						else{
							cDocTypeID = movHdr.getC_DocType_ID();
							adTableID = movHdr.get_Table_ID();
							recordID = movHdr.get_ID();
							cCurrencyID = movHdr.getC_Currency_ID();
							documentNo = movHdr.getDocumentNo();
							description = movHdr.getDescription();
						}
					}
					else if (trackLine.getAD_Table_ID() == I_C_Invoice.Table_ID){
						invoice = new MInvoice(getCtx(), trackLine.getRecord_ID(), null);
						if (!invoice.getDocStatus().equalsIgnoreCase(DOCSTATUS_Completed)){
							saveAnterior = false;
						}
						else{
							cDocTypeID = invoice.getC_DocTypeTarget_ID();
							adTableID = invoice.get_Table_ID();
							recordID = invoice.get_ID();
							cCurrencyID = invoice.getC_Currency_ID();
							documentNo = invoice.getDocumentNo();
							description = invoice.getDescription();
						}
					}
					else{
						saveAnterior = false;
					}
				}
				else{
					saveAnterior = false;
				}
				
				if (saveAnterior){

					MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
					sumba.setC_BankAccount_ID(mp.getC_BankAccount_ID());
					sumba.setDateTrx(mp.getDateTrx());
					sumba.setC_DocType_ID(cDocTypeID);
					sumba.setDocumentNo(documentNo);
					sumba.setAD_Table_ID(adTableID);
					sumba.setRecord_ID(recordID);
					sumba.setDueDate(mp.getDueDate());
					sumba.setC_BPartner_ID(mp.getC_BPartner_ID());
					sumba.setDescription(description);
					sumba.setCheckNo(mp.getCheckNo());
					sumba.setStatus(mp.getestado());
					sumba.setUY_MediosPago_ID(mp.get_ID());
					sumba.setC_Currency_ID(cCurrencyID);
					sumba.setamtdocument(mp.getPayAmt());
					sumba.setAmtSourceCr(mp.getPayAmt());
					sumba.setAmtSourceDr(Env.ZERO);
					sumba.setAmtAcctDr(Env.ZERO);

					BigDecimal currencyRate = Env.ONE;
					MBankAccount ba = (MBankAccount)mp.getC_BankAccount();
					if (ba.getC_Currency_ID() != sumba.getC_Currency_ID()){
						currencyRate = MConversionRate.getRate(sumba.getC_Currency_ID(), ba.getC_Currency_ID(), sumba.getDateTrx(), 0, this.getAD_Client_ID(), 0);
						if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + sumba.getDateTrx());
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
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Voided);			
			linea.saveEx();
		}

		return true;
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 17/02/2016.
	 * Anulacion de deposito de cheques propios.
	 * @return
	 */
	private boolean voidDepositoChequePropio() {

		// Recorro lineas 
		ArrayList<MMovBancariosLine> lines = this.getLines();
		
		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede anularse.";
			return false;
		}

		for (int i = 0; i < lines.size(); i++) {
		
			MMovBancariosLine linea = lines.get(i);
			if (linea.getUY_MediosPago_ID() > 0) {
				
				MMediosPago mp = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());

				if (!mp.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Depositado)){
					this.processMsg = "El cheque numero : " + mp.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + ", no esta en estado Depositado.";
					return false;
				}
			
				// Obtengo tracking de cheque en estado anterior
				MCheckTracking trackLine = this.getCheckTracking(mp.getUY_MediosPago_ID(), mp.getoldstatus(), get_TrxName());
				String oldStatusChequeLinea = "";
				if (trackLine.get_ID() > 0){
					oldStatusChequeLinea = trackLine.getCheckOldStatus();
					if (oldStatusChequeLinea == null) oldStatusChequeLinea = "";
				}
				if (oldStatusChequeLinea.equalsIgnoreCase("")) oldStatusChequeLinea = X_UY_MediosPago.ESTADO_Emitido;
				
				mp.setestado(mp.getoldstatus());
				mp.setoldstatus(oldStatusChequeLinea);

				if (trackLine.getAD_Table_ID() == this.get_Table_ID()){
					mp.setUY_MovBancariosHdr_ID(trackLine.getRecord_ID());
					mp.setUY_MovBancariosLine_ID(linea.get_ID());
				}
				else{
					mp.setUY_MovBancariosHdr_ID(0);
					mp.setUY_MovBancariosLine_ID(0);
				}
				
				mp.saveEx();				
				
				// Debo eliminar deposito de la tabla sum de estado de cuenta bancaria
				// y luego insertar un registro para este cheque en el estado anterior.
				String action = " delete from uy_sum_accountstatus " +
						" where uy_mediospago_id =" + mp.get_ID();
				DB.executeUpdateEx(action, get_TrxName());
				
				// Obtengo modelo origen de la transaccion anterior a este deposito
				boolean saveAnterior = true;
				MPayment payment = null;
				MMovBancariosHdr movHdr = null;
				MInvoice invoice = null;
				int cDocTypeID = 0, adTableID = 0, recordID = 0, cCurrencyID = 0;
				String documentNo = "", description = "";
				if (trackLine.get_ID() > 0){
					// Si fue un Recibo de Cobro
					if (trackLine.getAD_Table_ID() == I_C_Payment.Table_ID){
						payment = new MPayment(getCtx(), trackLine.getRecord_ID(), null);
						if (!payment.getDocStatus().equalsIgnoreCase(DOCSTATUS_Completed)){
							saveAnterior = false;
						}
						else{
							cDocTypeID = payment.getC_DocType_ID();
							adTableID = payment.get_Table_ID();
							recordID = payment.get_ID();
							cCurrencyID = payment.getC_Currency_ID();
							documentNo = payment.getDocumentNo();
							description = payment.getDescription();
						}
					}
					else if (trackLine.getAD_Table_ID() == I_UY_MovBancariosHdr.Table_ID){
						movHdr = new MMovBancariosHdr(getCtx(), trackLine.getRecord_ID(), null);
						if (!movHdr.getDocStatus().equalsIgnoreCase(DOCSTATUS_Completed)){
							saveAnterior = false;
						}
						else{
							cDocTypeID = movHdr.getC_DocType_ID();
							adTableID = movHdr.get_Table_ID();
							recordID = movHdr.get_ID();
							cCurrencyID = movHdr.getC_Currency_ID();
							documentNo = movHdr.getDocumentNo();
							description = movHdr.getDescription();
						}
					}
					else if (trackLine.getAD_Table_ID() == I_C_Invoice.Table_ID){
						invoice = new MInvoice(getCtx(), trackLine.getRecord_ID(), null);
						if (!invoice.getDocStatus().equalsIgnoreCase(DOCSTATUS_Completed)){
							saveAnterior = false;
						}
						else{
							cDocTypeID = invoice.getC_DocTypeTarget_ID();
							adTableID = invoice.get_Table_ID();
							recordID = invoice.get_ID();
							cCurrencyID = invoice.getC_Currency_ID();
							documentNo = invoice.getDocumentNo();
							description = invoice.getDescription();
						}
					}
					else{
						saveAnterior = false;
					}
				}
				else{
					saveAnterior = false;
				}
				
				if (saveAnterior){

					MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
					sumba.setC_BankAccount_ID(mp.getC_BankAccount_ID());
					sumba.setDateTrx(mp.getDateTrx());
					sumba.setC_DocType_ID(cDocTypeID);
					sumba.setDocumentNo(documentNo);
					sumba.setAD_Table_ID(adTableID);
					sumba.setRecord_ID(recordID);
					sumba.setDueDate(mp.getDueDate());
					sumba.setC_BPartner_ID(mp.getC_BPartner_ID());
					sumba.setDescription(description);
					sumba.setCheckNo(mp.getCheckNo());
					sumba.setStatus(mp.getestado());
					sumba.setUY_MediosPago_ID(mp.get_ID());
					sumba.setC_Currency_ID(cCurrencyID);
					sumba.setamtdocument(mp.getPayAmt());
					sumba.setAmtSourceCr(mp.getPayAmt());
					sumba.setAmtSourceDr(Env.ZERO);
					sumba.setAmtAcctDr(Env.ZERO);

					BigDecimal currencyRate = Env.ONE;
					MBankAccount ba = (MBankAccount)mp.getC_BankAccount();
					if (ba.getC_Currency_ID() != sumba.getC_Currency_ID()){
						currencyRate = MConversionRate.getRate(sumba.getC_Currency_ID(), ba.getC_Currency_ID(), sumba.getDateTrx(), 0, this.getAD_Client_ID(), 0);
						if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + sumba.getDateTrx());
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
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Voided);			
			linea.saveEx();
		}

		return true;
	}

	
	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Anulacion de descuento de cheque de tercero.
	 * @return
	 */
	private boolean voidDescuentoChequeTercero() {

		// Recorro lineas 
		ArrayList<MMovBancariosLine> lines = this.getLines();
		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede anularse.";
			return false;
		}

		for (int i = 0; i < lines.size(); i++) {
		
			MMovBancariosLine linea = lines.get(i);
			if (linea.getUY_MediosPago_ID() > 0) {
				
				MMediosPago mp = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());

				if (!mp.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Descontado)){
					this.processMsg = "El cheque numero : " + mp.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + ", no esta en estado Descontado.";
					return false;
				}
			
				// Obtengo tracking de cheque en estado anterior
				MCheckTracking trackLine = this.getCheckTracking(mp.getUY_MediosPago_ID(), mp.getoldstatus(), get_TrxName());
				String oldStatusChequeLinea = "";
				if (trackLine.get_ID() > 0){
					oldStatusChequeLinea = trackLine.getCheckOldStatus();
					if (oldStatusChequeLinea == null) oldStatusChequeLinea = "";
				}
				if (oldStatusChequeLinea.equalsIgnoreCase("")) oldStatusChequeLinea = X_UY_MediosPago.ESTADO_Cartera;
				
				mp.setestado(mp.getoldstatus());
				mp.setoldstatus(oldStatusChequeLinea);

				if (trackLine.getAD_Table_ID() == this.get_Table_ID()){
					mp.setUY_MovBancariosHdr_ID(trackLine.getRecord_ID());
					mp.setUY_MovBancariosLine_ID(0); // TODO : Setear bien el id de la linea del movimiento
				}
				else{
					mp.setUY_MovBancariosHdr_ID(0);
					mp.setUY_MovBancariosLine_ID(0);
				}
				
				mp.saveEx();
			}
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Voided);			
			linea.saveEx();
		}
		
		// Elimino transaccion de tabla sum
		String action = " delete from uy_sum_accountstatus where ad_table_id = " + I_UY_MovBancariosHdr.Table_ID +
				        " and record_id = " + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());

		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Anulacion de conciliacion de cheques propios.
	 * @return
	 */
	private boolean voidConciliacionChequePropio() {

		try{
			// Recorro lineas 
			ArrayList<MMovBancariosLine> lines = this.getLines();
			if (lines.size() == 0) {
				this.processMsg = "Esta transacción no tiene lineas. No puede anularse.";
				return false;
			}

			for (int i = 0; i < lines.size(); i++) {
			
				MMovBancariosLine linea = lines.get(i);
				if (linea.getUY_MediosPago_ID() > 0) {
					
					MMediosPago mp = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());

					if (!mp.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Pago)){
						this.processMsg = "El cheque numero : " + mp.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + ", no esta en estado Pago.";
						return false;
					}
					
					// Obtengo tracking de cheque en estado anterior
					MCheckTracking trackLine = this.getCheckTracking(mp.getUY_MediosPago_ID(), mp.getoldstatus(), get_TrxName());
					String oldStatusChequeLinea = "EMI";
					if (trackLine.get_ID() > 0){
						oldStatusChequeLinea = trackLine.getCheckOldStatus();
						if (oldStatusChequeLinea == null) oldStatusChequeLinea = "";
					}
					if (oldStatusChequeLinea.equalsIgnoreCase("")) oldStatusChequeLinea = X_UY_MediosPago.ESTADO_Entregado;
					
					mp.setestado(mp.getoldstatus());
					mp.setoldstatus(oldStatusChequeLinea);

					if (trackLine.getAD_Table_ID() == this.get_Table_ID()){
						mp.setUY_MovBancariosHdr_ID(trackLine.getRecord_ID());
						mp.setUY_MovBancariosLine_ID(0); // TODO : Setear bien el id de la linea del movimiento
					}
					else{
						mp.setUY_MovBancariosHdr_ID(0);
						mp.setUY_MovBancariosLine_ID(0);
					}
					
					mp.saveEx();
				}
				linea.setProcessed(true);
				linea.setDocStatus(DOCSTATUS_Voided);			
				linea.saveEx();
			}
		
			return true;
			
		}catch (Exception e){
			this.processMsg = e.getMessage();
			return false;
		}
	}

	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Anulacion de pago de vale bancario.
	 * @return
	 */
	private boolean voidPagoValeBancario() {
		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Anulacion de vale bancario.
	 * @return
	 */
	private boolean voidValesBancarios() {
		return true;
	}

	
	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Anulacion de cobro de conforme.
	 * @return
	 */
	private boolean voidCobroConformes() {

		MMediosPago mediopago = new MMediosPago(getCtx(), this.getUY_MediosPago_ID(), get_TrxName());
		
		if ((mediopago == null) || (mediopago.get_ID() <= 0))
			throw new AdempiereException("No se pudo obtener informacion del cheque con ID : " + this.getUY_MediosPago_ID());

		// El conforme tiene que estar en estado cobrado
		if (!mediopago.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Cobrado)){
			this.processMsg = "No se puede anular el documento ya que el mismo no esta en estado cobrado.";
			return false;
		}

		// El conforme tiene que ser de terceros
		if (!mediopago.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Terceros)){
			this.processMsg = "No se puede anular el documento ya que el mismo no es de terceros.";
			return false;
		}		
		
		// Obtengo tracking de cheque en estado anterior a ser cobrado
		MCheckTracking track = this.getCheckTracking(mediopago.getUY_MediosPago_ID(), mediopago.getoldstatus(), get_TrxName());
		String oldStatus = "";
		if (track.get_ID() > 0){
			oldStatus = track.getCheckOldStatus();
			if (oldStatus == null) oldStatus = "";
		}
		if (oldStatus.equalsIgnoreCase("")) oldStatus = X_UY_MediosPago.ESTADO_Cartera;

		mediopago.setestado(mediopago.getoldstatus());
		mediopago.setoldstatus(oldStatus);

		if (track.getAD_Table_ID() == this.get_Table_ID()){
			mediopago.setUY_MovBancariosHdr_ID(track.getRecord_ID());
			mediopago.setUY_MovBancariosLine_ID(0); // TODO : Setear bien el id de la linea del movimiento
		}
		else{
			mediopago.setUY_MovBancariosHdr_ID(0);
			mediopago.setUY_MovBancariosLine_ID(0);
		}
		
		mediopago.saveEx();

		// Recorro lineas y Anulo los cheques de las lineas de este documento
		ArrayList<MMovBancariosLine> lines = this.getLines();
		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede anularse.";
			return false;
		}

		for (int i = 0; i < lines.size(); i++) {
			MMovBancariosLine linea = lines.get(i);
			if (linea.getUY_MediosPago_ID() > 0) {
				MMediosPago mp = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());
				mp.setUY_MovBancariosHdr_ID(this.get_ID());
				mp.setUY_MovBancariosLine_ID(linea.getUY_MovBancariosLine_ID());
				try {
					if (!mp.processIt(DocumentEngine.ACTION_Void)){
						this.processMsg = mediopago.getProcessMsg();
						return false;
					}
				} catch (Exception e) {
					throw new AdempiereException(e.getMessage());
				}
			}
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Voided);			
			linea.saveEx();
		}

		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 30/01/2012
	 * Anulacion de cambio de cheques propios.
	 * @return boolean. True si todo bien, false sino.
	 */	
	private boolean voidCambioChequePropio() {
		
		MMediosPago mediopago = new MMediosPago(getCtx(), this.getUY_MediosPago_ID(), get_TrxName());
		
		if ((mediopago == null) || (mediopago.get_ID() <= 0))
			throw new AdempiereException("No se pudo obtener informacion del cheque con ID : " + this.getUY_MediosPago_ID());

		// El cheque tiene que estar en estado cambiado
		if (!mediopago.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Cambiado)){
			this.processMsg = "No se puede anular el documento ya que el cheque no esta en estado cambiado.";
			return false;
		}

		// El cheque tiene que ser de terceros
		if (!mediopago.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Propio)){
			this.processMsg = "No se puede anular el documento ya que el cheque no es propio.";
			return false;
		}
		
		// Obtengo tracking de cheque en cuando fue rechazado
		MCheckTracking track = this.getCheckTracking(mediopago.getUY_MediosPago_ID(), X_UY_MediosPago.ESTADO_Rechazado, get_TrxName());
		String oldStatusRechazo = "";
		if (track.get_ID() > 0){
			oldStatusRechazo = track.getCheckOldStatus();
			if (oldStatusRechazo == null) oldStatusRechazo = "";
		}
		if (oldStatusRechazo.equalsIgnoreCase("")) oldStatusRechazo = X_UY_MediosPago.ESTADO_Pago;
		
		mediopago.setestado(X_UY_MediosPago.ESTADO_Rechazado);
		mediopago.setoldstatus(oldStatusRechazo);
		
		if (track.getAD_Table_ID() == this.get_Table_ID()){
			mediopago.setUY_MovBancariosHdr_ID(track.getRecord_ID());
			mediopago.setUY_MovBancariosLine_ID(0); // TODO : Setear bien el id de la linea del movimiento
		}
		else{
			mediopago.setUY_MovBancariosHdr_ID(0);
			mediopago.setUY_MovBancariosLine_ID(0);
		}
		
		mediopago.saveEx();

		// Recorro lineas 
		ArrayList<MMovBancariosLine> lines = this.getLines();
		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede anularse.";
			return false;
		}

		for (int i = 0; i < lines.size(); i++) {
			MMovBancariosLine linea = lines.get(i);
			if (linea.getUY_MediosPago_ID() > 0) {
				
				MMediosPago mp = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());

				if (!mp.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Entregado)){
					this.processMsg = "El cheque numero : " + mp.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + ", no esta en estado Entregado.";
					return false;
				}
				
				// Obtengo tracking de cheque en estado anterior
				MCheckTracking trackLine = this.getCheckTracking(mp.getUY_MediosPago_ID(), mp.getoldstatus(), get_TrxName());
				String oldStatusChequeLinea = "";
				if (trackLine.get_ID() > 0){
					oldStatusChequeLinea = trackLine.getCheckOldStatus();
				}
				if (oldStatusChequeLinea.equalsIgnoreCase("")) oldStatusChequeLinea = X_UY_MediosPago.ESTADO_Emitido;
				
				mp.setestado(mp.getoldstatus());
				mp.setoldstatus(oldStatusChequeLinea);

				if (trackLine.getAD_Table_ID() == this.get_Table_ID()){
					mp.setUY_MovBancariosHdr_ID(trackLine.getRecord_ID());
					mp.setUY_MovBancariosLine_ID(0); // TODO : Setear bien el id de la linea del movimiento
				}
				else{
					mp.setUY_MovBancariosHdr_ID(0);
					mp.setUY_MovBancariosLine_ID(0);
				}
				
				mp.saveEx();
			}
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Voided);			
			linea.saveEx();
		}

		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 30/01/2012
	 * Anulacion de cambio de cheque de tercero.
	 * @return boolean. True si todo bien, false sino.
	 */
	private boolean voidCambioChequeTercero() {

		MMediosPago mediopago = new MMediosPago(getCtx(), this.getUY_MediosPago_ID(), get_TrxName());
		
		if ((mediopago == null) || (mediopago.get_ID() <= 0))
			throw new AdempiereException("No se pudo obtener informacion del cheque con ID : " + this.getUY_MediosPago_ID());

		// El cheque tiene que estar en estado cambiado
		if (!mediopago.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Cambiado)){
			this.processMsg = "No se puede anular el documento ya que el cheque no esta en estado cambiado.";
			return false;
		}

		// El cheque tiene que ser de terceros
		if (!mediopago.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Terceros)){
			this.processMsg = "No se puede anular el documento ya que el cheque no es de terceros.";
			return false;
		}
		
		
		// Obtengo tracking de cheque en estado rechazado
		MCheckTracking track = this.getCheckTracking(mediopago.getUY_MediosPago_ID(), X_UY_MediosPago.ESTADO_Rechazado, get_TrxName());
		String oldStatusRechazo = "";
		if (track.get_ID() > 0){
			oldStatusRechazo = track.getCheckOldStatus();
			if (oldStatusRechazo == null) oldStatusRechazo = "";
		}
		if (oldStatusRechazo.equalsIgnoreCase("")) oldStatusRechazo = X_UY_MediosPago.ESTADO_Cartera;

		mediopago.setestado(X_UY_MediosPago.ESTADO_Rechazado);
		mediopago.setoldstatus(oldStatusRechazo);

		if (track.getAD_Table_ID() == this.get_Table_ID()){
			mediopago.setUY_MovBancariosHdr_ID(track.getRecord_ID());
			mediopago.setUY_MovBancariosLine_ID(0); // TODO : Setear bien el id de la linea del movimiento
		}
		else{
			mediopago.setUY_MovBancariosHdr_ID(0);
			mediopago.setUY_MovBancariosLine_ID(0);
		}
		
		mediopago.saveEx();

		// Recorro lineas y Anulo los cheques de las lineas de este documento
		ArrayList<MMovBancariosLine> lines = this.getLines();
		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede anularse.";
			return false;
		}

		for (int i = 0; i < lines.size(); i++) {
			MMovBancariosLine linea = lines.get(i);
			if (linea.getUY_MediosPago_ID() > 0) {
				MMediosPago mp = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());
				
				if (!mp.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Cartera)){
					this.processMsg = "El cheque numero : " + mp.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + ", no esta en estado Cartera.";
					return false;
				}
				
				mp.setUY_MovBancariosHdr_ID(this.get_ID());
				mp.setUY_MovBancariosLine_ID(linea.getUY_MovBancariosLine_ID());
				try {
					if (!mp.processIt(DocumentEngine.ACTION_Void)){
						this.processMsg = mediopago.getProcessMsg();
						return false;
					}
				} catch (Exception e) {
					throw new AdempiereException(e.getMessage());
				}
			}
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Voided);			
			linea.saveEx();
		}

		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 22/12/2011.
	 * Anulacion de rechazos de cheques propios y de terceros.
	 * @return boolean. True si todo bien, false sino.
	 * @throws Exception 
	 */
	private boolean voidRechazoCheques() {

		MMediosPago mediopago = new MMediosPago(getCtx(), this.getUY_MediosPago_ID(), get_TrxName());
		
		if ((mediopago == null) || (mediopago.get_ID() <= 0))
			throw new AdempiereException("No se pudo obtener informacion del cheque con ID : " + this.getUY_MediosPago_ID());

		// Para rechazar un cheque el mismo tiene que estar en estado rechazado
		if (!mediopago.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Rechazado)){
			this.processMsg = "No se puede anular el documento ya que el cheque no esta en estado rechazado.";
			return false;
		}		
		
		// Obtengo tracking de cheque cuando fue rechazado
		MCheckTracking track = this.getCheckTracking(mediopago.getUY_MediosPago_ID(), X_UY_MediosPago.ESTADO_Rechazado, get_TrxName());
		String oldStatusRechazo = "";
		if (track.get_ID() > 0){
			oldStatusRechazo = track.getCheckOldStatus();
			if (oldStatusRechazo == null) oldStatusRechazo = "";
		}
		if (oldStatusRechazo.equalsIgnoreCase("")){
			if (mediopago.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Propio))
				oldStatusRechazo = X_UY_MediosPago.ESTADO_Pago;
			else
				oldStatusRechazo = X_UY_MediosPago.ESTADO_Cartera;
		}
		
		// Obtengo tracking de cheque antes de ser rechazado
		MCheckTracking trackOld = this.getCheckTracking(mediopago.getUY_MediosPago_ID(), oldStatusRechazo, get_TrxName());
		String oldStatusPreRechazo = "";
		if (trackOld.get_ID() > 0){
			oldStatusPreRechazo = trackOld.getCheckOldStatus();
		}
		if (oldStatusPreRechazo.equalsIgnoreCase("")){
			if (mediopago.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Propio))
				oldStatusPreRechazo = X_UY_MediosPago.ESTADO_Emitido;
			else
				oldStatusPreRechazo = X_UY_MediosPago.ESTADO_Cartera;
		}
		
		mediopago.setestado(oldStatusRechazo);
		mediopago.setoldstatus(oldStatusPreRechazo);
		
		if (track.getAD_Table_ID() == this.get_Table_ID()){
			mediopago.setUY_MovBancariosHdr_ID(track.getRecord_ID());

			//OpenUp. Guillermo Brust. 14/09/2012. ISSUE #30. Version 2.5.1
			//Comentario: Se modifica el id de la linea del movimiento.
			//se encontraba el siguiento ExTODO : Setear bien el id de la linea del movimiento			
			mediopago.setUY_MovBancariosLine_ID(mediopago.getUY_MovBancariosLine_ID());			
			//Fin ISSUE #30			
		}
		else{
			mediopago.setUY_MovBancariosHdr_ID(0);
			mediopago.setUY_MovBancariosLine_ID(0);
		}

		mediopago.saveEx();
		
		// Obtengo modelo origen de la transaccion anterior a este deposito
		boolean saveAnterior = true;
		MPayment payment = null;
		MMovBancariosHdr movHdr = null;
		MInvoice invoice = null;
		int cDocTypeID = 0, adTableID = 0, recordID = 0, cCurrencyID = 0, cBankAccountID = 0;
		String documentNo = "", description = "";
		
		if (trackOld.get_ID() > 0){
			// Si fue un Recibo
			if (trackOld.getAD_Table_ID() == I_C_Payment.Table_ID){
				payment = new MPayment(getCtx(), trackOld.getRecord_ID(), null);
				if (!payment.getDocStatus().equalsIgnoreCase(DOCSTATUS_Completed)){
					saveAnterior = false;
				}
				else{
					cDocTypeID = payment.getC_DocType_ID();
					adTableID = payment.get_Table_ID();
					recordID = payment.get_ID();
					cCurrencyID = payment.getC_Currency_ID();
					cBankAccountID = mediopago.getC_BankAccount_ID();
					documentNo = payment.getDocumentNo();
					description = payment.getDescription();
				}
			}
			else if (trackOld.getAD_Table_ID() == I_UY_MovBancariosHdr.Table_ID){
				movHdr = new MMovBancariosHdr(getCtx(), trackOld.getRecord_ID(), null);
				if (!movHdr.getDocStatus().equalsIgnoreCase(DOCSTATUS_Completed)){
					saveAnterior = false;
				}
				else{
					cDocTypeID = movHdr.getC_DocType_ID();
					adTableID = movHdr.get_Table_ID();
					recordID = movHdr.get_ID();
					cCurrencyID = movHdr.getC_Currency_ID();
					cBankAccountID = movHdr.getC_BankAccount_ID();
					documentNo = movHdr.getDocumentNo();
					description = movHdr.getDescription();
				}
			}
			else if (trackOld.getAD_Table_ID() == I_C_Invoice.Table_ID){
				invoice = new MInvoice(getCtx(), trackOld.getRecord_ID(), null);
				if (!invoice.getDocStatus().equalsIgnoreCase(DOCSTATUS_Completed)){
					saveAnterior = false;
				}
				else{
					cDocTypeID = invoice.getC_DocTypeTarget_ID();
					adTableID = invoice.get_Table_ID();
					recordID = invoice.get_ID();
					cCurrencyID = invoice.getC_Currency_ID();
					cBankAccountID = mediopago.getC_BankAccount_ID();					
					documentNo = invoice.getDocumentNo();
					description = invoice.getDescription();
				}
			}
			else{
				saveAnterior = false;
			}
		}
		else{
			saveAnterior = false;
		}
		
		if (saveAnterior){

			MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
			sumba.setC_BankAccount_ID(cBankAccountID);
			sumba.setDateTrx(mediopago.getDateTrx());
			sumba.setC_DocType_ID(cDocTypeID);
			sumba.setDocumentNo(documentNo);
			sumba.setAD_Table_ID(adTableID);
			sumba.setRecord_ID(recordID);
			sumba.setDueDate(mediopago.getDueDate());
			sumba.setC_BPartner_ID(mediopago.getC_BPartner_ID());
			sumba.setDescription(description);
			sumba.setCheckNo(mediopago.getCheckNo());
			sumba.setStatus(mediopago.getestado());
			sumba.setUY_MediosPago_ID(mediopago.get_ID());
			sumba.setC_Currency_ID(cCurrencyID);
			sumba.setamtdocument(mediopago.getPayAmt());
			if (mediopago.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Propio)){
				sumba.setAmtSourceDr(mediopago.getPayAmt());
				sumba.setAmtSourceCr(Env.ZERO);
				sumba.setAmtAcctCr(Env.ZERO);
			}
			else{
				sumba.setAmtSourceCr(mediopago.getPayAmt());
				sumba.setAmtSourceDr(Env.ZERO);
				sumba.setAmtAcctDr(Env.ZERO);
			}

			BigDecimal currencyRate = Env.ONE;
			MBankAccount ba = (MBankAccount)mediopago.getC_BankAccount();
			if (ba.getC_Currency_ID() != sumba.getC_Currency_ID()){
				currencyRate = MConversionRate.getRate(sumba.getC_Currency_ID(), ba.getC_Currency_ID(), sumba.getDateTrx(), 0, this.getAD_Client_ID(), 0);
				if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + sumba.getDateTrx());
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

	/* OpenUp. Gabriel Vila. 18/08/2010.
	 * Obtengo array de lineas de un movimiento bancario como objetos MMovBancariosLine.
	 */
	public ArrayList<MMovBancariosLine> getLines(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<MMovBancariosLine> lines = new ArrayList<MMovBancariosLine>();		
		
		try{			
			sql = " SELECT UY_MovBancariosLine_ID " +
				  " FROM UY_MovBancariosLine "  +
				  " WHERE UY_MovBancariosHdr_ID =?"; 
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.getUY_MovBancariosHdr_ID());
			rs = pstmt.executeQuery ();
			while (rs.next()) {
				MMovBancariosLine line = new MMovBancariosLine(getCtx(), rs.getInt("UY_MovBancariosLine_ID"), get_TrxName());
				lines.add(line);
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
		return lines;
	}

	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Obtengo y retorno array de lineas de este movimiento bancario.
	 * @param trxName
	 * @return
	 */
	public MMovBancariosLine[] getLinesArray(String trxName) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MMovBancariosLine> list = new ArrayList<MMovBancariosLine>();
		
		try{
			sql = " SELECT UY_MovBancariosLine_ID " +
					  " FROM UY_MovBancariosLine "  +
					  " WHERE UY_MovBancariosHdr_ID =?"; 
			
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt(1, this.get_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MMovBancariosLine value = new MMovBancariosLine(Env.getCtx(), rs.getInt(1), trxName);
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
		
		return list.toArray(new MMovBancariosLine[list.size()]);		
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		//OpenUp M.R. 13-09-2011 Issue# 723 agrego linea para que la fecha de contabilizacion sea la misma del documento
		this.setDateAcct(this.getDateTrx());

		MDocType doc = new MDocType(getCtx(), this.getC_DocType_ID(),
				get_TrxName());
		String docBaseType = doc.getDocBaseType();
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_CobroConformes)) {
			if (this.getCheckNo() == null){
				String sql = "";
				sql = "Select checkno from uy_mediospago where uy_mediospago_id = "+ this.getUY_MediosPago_ID();
				
				PreparedStatement pstmt=null;								 
				ResultSet rs=null;
				String checkno = "";
				try {
					
					pstmt=DB.prepareStatement(sql,get_TrxName());		// Create the statement
					rs = pstmt.executeQuery();
					
					// Read the first record and get a new object
					if (rs.next()) {
						checkno = rs.getString("CheckNo");
					}
				}
				catch (Exception e) {
					log.log(Level.SEVERE, sql, e);
				}
				finally {										// Close and reset the statemente and recordset
					DB.close(rs, pstmt);
					rs = null; pstmt = null;
				}
				this.setCheckNo(checkno);
			}

		}
		
		//OpenUp. Nicolas Sarlabos. 18/08/2015. #4386.
		if(doc.getValue()!=null){
			
			// Transferencia directa
			if(doc.getValue().equalsIgnoreCase("transfdir")){
				
				if(this.getUY_C_BankAccount_From_ID()==this.getC_BankAccount_ID())
					throw new AdempiereException("Las cuentas origen y destino deben ser distintas");
				
				// Debe indicar importe en cuenta origen
				if ((this.getuy_total_manual() == null) || (this.getuy_total_manual().compareTo(Env.ZERO) == 0)){
					throw new AdempiereException("Debe indicar Importe en Cuenta Bancaria Origen");
				}
				
				// Calculo importe para cuenta bancaria destino en esa moneda
				// Si las monedas son iguales los importes tambien 
				if (this.getC_Currency_ID() == this.getUY_C_Currency_From_ID()){
					this.setuy_totalme(this.getuy_total_manual());
				}
				else{
					// Monedas distintas considera tasa y divido o multiplico segun flag seteado
					BigDecimal amt = Env.ZERO;
					BigDecimal rate = this.getDivideRate();
					if ((rate == null) || (rate.compareTo(Env.ZERO) == 0)) rate = Env.ONE;
					if (this.get_ValueAsBoolean("IsDivideRate")){
						amt = this.getuy_total_manual().divide(rate, 2, RoundingMode.HALF_UP);
					}
					else{
						amt = this.getuy_total_manual().multiply(rate).setScale(2, RoundingMode.HALF_UP);
					}
					this.setuy_totalme(amt);
				}
			}
		}
		//Fin OpenUp.
		
		return true;

	}
	
	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo rechazo de cheques propios y de terceros.
	 */
	private boolean completeRechazoCheques() {
	
		MMediosPago mediopago = new MMediosPago(getCtx(), this.getUY_MediosPago_ID(), get_TrxName());
		
		if ((mediopago == null) || (mediopago.get_ID() <= 0))
			throw new AdempiereException("No se pudo obtener informacion del cheque con ID : " + this.getUY_MediosPago_ID());

		// Para rechazar un cheque propio el mismo tiene que estar en estado pago
		if (mediopago.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Propio)){
			if (!mediopago.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Pago)){
//				this.processMsg = "No se puede rechazar este cheque ya que no esta conciliado.";
//				return false;
			}	
		}
		
		// Para rechazar un cheque de terceros el mismo tiene que estar en cartera, depositado o descontado
		if (mediopago.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Terceros)){
			if ((!mediopago.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Cartera))
					&& (!mediopago.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Depositado))
					&& (!mediopago.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Descontado))){
				this.processMsg = "No se puede rechazar este cheque de tercero ya que no esta en cartera o depositado o descontado.";
				return false;
			}	
		}	
						
		mediopago.setoldstatus(mediopago.getestado());
		mediopago.setestado(X_UY_MediosPago.ESTADO_Rechazado);
		mediopago.setUY_MovBancariosHdr_ID(this.get_ID());
		mediopago.setUY_MovBancariosLine_ID(mediopago.getUY_MovBancariosLine_ID());
		mediopago.saveEx();
		
		// OpenUp. Guillermo Brust. 04/09/2012. ISSUE #17 - Version 2.1.5
		// Descripción: Se agrega al metodo existente, el siguiente codigo, con la finalidad de guardar la cuenta
		// inicio del cheque que se esta rechazando, ya sea si fue depositado o descontado.
		// Se toma el medio de pago, y se busca como nexo la linea de movimientos bancarios, para llegar al cabezal
		// de movimientos bancarios y asi obtener el id de la cuenta destino del cheque.
		
		int cBankAccountFromID = 0;
		
		if (mediopago.getoldstatus().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Descontado)|| 
				mediopago.getoldstatus().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Depositado)){
			
			MMovBancariosLine line = new MMovBancariosLine(getCtx(), mediopago.getUY_MovBancariosLine_ID(), get_TrxName());
			if (line.get_ID() > 0){
				MMovBancariosHdr hdrDescDep = new MMovBancariosHdr(getCtx(), line.getUY_MovBancariosHdr_ID(), get_TrxName());
				cBankAccountFromID = hdrDescDep.getC_BankAccount_ID();
				
				//Se agrega el siguiente codigo para que quede el oldstatus del cheque en el movimiento bancario			
				this.setoldstatus(mediopago.getoldstatus());				
			}
		}		

		if (cBankAccountFromID > 0){
			this.setUY_C_BankAccount_From_ID(cBankAccountFromID	);			
		}
		// Fin Issue #17
		
		// Al completar un rechazo de un cheque debo eliminar informacion 
		// de ese cheque en la tabla sumarizadora.
		String action = " delete from uy_sum_accountstatus " +
				" where uy_mediospago_id =" + mediopago.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		return true;
	}


	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo transferencias bancarias.
	 */
	private boolean completeTransferencias(MDocType doc) {

		MSUMAccountStatus sumba = null;

		if((doc.getValue()!=null && !doc.getValue().equalsIgnoreCase("transfdir")) || (doc.getValue()==null)){

			ArrayList<MMovBancariosLine> lines = this.getLines();

			if (lines.size() == 0) {
				this.processMsg = "Esta transacción no tiene lineas. No puede completarse.";
				return false;
			}

			// Recorro lineas 
			for (int i = 0; i < lines.size(); i++) {

				MMovBancariosLine linea = lines.get(i);

				// Si esta linea tiene un medio de pago
				if (linea.getUY_MediosPago_ID() > 0) {

					MMediosPago mpLine = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());
					if (mpLine.get_ID() <= 0){
						this.processMsg = "No se pudo obtener el cheque con ID : " + this.getUY_MediosPago_ID() + " utilizado en la linea : " + linea.getDocumentNo() + " de esta transaccion";
						return false;
					}			

					if (!mpLine.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Propio)){
						this.processMsg = "El cheque numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + " no es propio.";
						return false;
					}

					if (!mpLine.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Emitido)){
						this.processMsg = "El cheque numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + " no esta Emitido.";
						return false;
					}

					mpLine.setoldstatus(mpLine.getestado());
					mpLine.setestado(X_UY_MediosPago.ESTADO_Transferido);
					mpLine.setUY_MovBancariosHdr_ID(this.get_ID());
					mpLine.setUY_MovBancariosLine_ID(linea.get_ID());
					mpLine.saveEx();
				}

				this.setDateTrx(this.getDateTrx());
				this.setDateAcct(this.getDateAcct());
				if (this.getDueDate() == null) this.setDueDate(this.getDateTrx());
				linea.setProcessed(true);
				linea.setDocStatus(DOCSTATUS_Completed);			
				linea.saveEx();
			}	

		}	

		//OpenUp. Nicolas Sarlabos. 22/09/2014.
		//se realiza el DEBITO de la cuenta ORIGEN
		
		BigDecimal amtSource = this.getuy_totalmn();
		if ((amtSource == null) || (amtSource.compareTo(Env.ZERO) == 0)) amtSource = this.getuy_total_manual();
		if (amtSource == null) amtSource = Env.ZERO;
		
		sumba = new MSUMAccountStatus(this.getCtx(), 0, this.get_TrxName());
		sumba.setC_BankAccount_ID(this.getUY_C_BankAccount_From_ID());
		sumba.setDateTrx(this.getDateTrx());
		sumba.setDueDate(this.getDateTrx());
		sumba.setC_DocType_ID(this.getC_DocType_ID());
		sumba.setDocumentNo(this.getDocumentNo());
		sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
		sumba.setRecord_ID(this.get_ID());
		sumba.setDescription(this.getDescription());
		sumba.setC_Currency_ID(this.getUY_C_Currency_From_ID());
		sumba.setamtdocument(amtSource);
		sumba.setAmtSourceDr(amtSource);
		sumba.setAmtSourceCr(Env.ZERO);
		sumba.setAmtAcctCr(Env.ZERO);
		sumba.setCurrencyRate(this.getDivideRate());
		sumba.saveEx();

		//se realiza el CREDITO de la cuenta DESTINO
		sumba = new MSUMAccountStatus(this.getCtx(), 0, this.get_TrxName());
		sumba.setC_BankAccount_ID(this.getC_BankAccount_ID());
		sumba.setDateTrx(this.getDateTrx());
		sumba.setDueDate(this.getDateTrx());
		sumba.setC_DocType_ID(this.getC_DocType_ID());
		sumba.setDocumentNo(this.getDocumentNo());
		sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
		sumba.setRecord_ID(this.get_ID());
		sumba.setDescription(this.getDescription());
		sumba.setC_Currency_ID(this.getC_Currency_ID());
		sumba.setamtdocument(this.getuy_totalme());
		sumba.setAmtSourceCr(this.getuy_totalme());
		sumba.setAmtSourceDr(Env.ZERO);
		sumba.setAmtAcctDr(Env.ZERO);
		sumba.setCurrencyRate(this.getDivideRate());
		sumba.saveEx();		
		//Fin OpenUp.	

		return true;

	}

	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo intereses bancarios.
	 */
	private boolean completeInteresesBancarios() {
		
		ArrayList<MMovBancariosLine> lines = this.getLines();

		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede completarse.";
			return false;
		}
		
		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);
			linea.setDateTrx(this.getDateTrx());
			linea.setDateAcct(this.getDateAcct());
			if (linea.getDueDate() == null) linea.setDueDate(this.getDateTrx());
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Completed);			
			linea.saveEx();
		}
		
		// OpenUp. Nicolas Sarlabos. 26/09/2014.
		// Actualizacion de tabla sumarizadora
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();

		MBankAccount ba = (MBankAccount)this.getC_BankAccount();
		MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
		sumba.setC_BankAccount_ID(this.getC_BankAccount_ID());
		sumba.setDateTrx(this.getDateTrx());
		sumba.setC_DocType_ID(this.getC_DocType_ID());
		sumba.setDocumentNo(this.getDocumentNo());
		sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
		sumba.setRecord_ID(this.get_ID());
		sumba.setDueDate(this.getDateTrx());
		sumba.setC_BPartner_ID(this.getC_BPartner_ID());
		sumba.setDescription(this.getDescription());
		sumba.setC_Currency_ID(ba.getC_Currency_ID());
		sumba.setamtdocument(this.getuy_totalme());
		
		if(this.isGanado()){
			
			sumba.setAmtSourceCr(this.getuy_totalme());
			sumba.setAmtSourceDr(Env.ZERO);
			sumba.setAmtAcctDr(Env.ZERO);		
			
		} else {
			
			sumba.setAmtSourceDr(this.getuy_totalme());
			sumba.setAmtSourceCr(Env.ZERO);
			sumba.setAmtAcctCr(Env.ZERO);			
		}
		
		BigDecimal currencyRate = Env.ONE;
		
		if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
			currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
			if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + this.getDateTrx());
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
		// Fin OpenUp.
		
		return true;
	}


	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo gastos bancarios.
	 */
	private boolean completeGastosBancarios() {
		
		ArrayList<MMovBancariosLine> lines = this.getLines();

		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede completarse.";
			return false;
		}
		
		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);
			linea.setDateTrx(this.getDateTrx());
			linea.setDateAcct(this.getDateAcct());
			if (linea.getDueDate() == null) linea.setDueDate(this.getDateTrx());
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Completed);			
			linea.saveEx();
			
		}

		
		// OpenUp. Gabriel Vila. 04/06/2013. Issue #935.
		// Actualizacion de tabla sumarizadora
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();

		MBankAccount ba = (MBankAccount)this.getC_BankAccount();
		MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
		sumba.setC_BankAccount_ID(this.getC_BankAccount_ID());
		sumba.setDateTrx(this.getDateTrx());
		sumba.setC_DocType_ID(this.getC_DocType_ID());
		sumba.setDocumentNo(this.getDocumentNo());
		sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
		sumba.setRecord_ID(this.get_ID());
		sumba.setDueDate(this.getDateTrx());
		sumba.setC_BPartner_ID(this.getC_BPartner_ID());
		sumba.setDescription(this.getDescription());
		sumba.setC_Currency_ID(ba.getC_Currency_ID());
		sumba.setamtdocument(this.getuy_totalme());
		sumba.setAmtSourceDr(this.getuy_totalme());
		sumba.setAmtSourceCr(Env.ZERO);
		sumba.setAmtAcctCr(Env.ZERO);
		BigDecimal currencyRate = Env.ONE;
		
		if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
			currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
			if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + this.getDateTrx());
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
		// Fin OpenUp. Issue #935.
		
		return true;
		
	}

	
	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo deposito en efectivo.
	 */
	private boolean completeDepositoEfectivo() {

		ArrayList<MMovBancariosLine> lines = this.getLines();

		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede completarse.";
			return false;
		}
		
		// OpenUp. Gabriel Vila. 04/06/2013. Issue #935.
		// Moneda nacional 
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		// Fin OpenUp. Issue #935.

		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);
			linea.setDateTrx(this.getDateTrx());
			linea.setDateAcct(this.getDateAcct());
			if (linea.getDueDate() == null) linea.setDueDate(this.getDateTrx());
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Completed);			
			linea.saveEx();
			
			// OpenUp. Gabriel Vila. 04/06/2013. Issue #935.
			// Actualizacion de tabla sumarizadora
			if (i == 0){				
				//se acredita en la cuenta destino
				MBankAccount ba = (MBankAccount)this.getC_BankAccount();
				MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(this.getC_BankAccount_ID());
				sumba.setDateTrx(this.getDateTrx());
				sumba.setC_DocType_ID(this.getC_DocType_ID());
				sumba.setDocumentNo(this.getDocumentNo());
				sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
				sumba.setRecord_ID(this.get_ID());
				sumba.setDueDate(this.getDateTrx());
				sumba.setC_BPartner_ID(this.getC_BPartner_ID());
				sumba.setDescription(this.getDescription());		
				sumba.setC_Currency_ID(ba.getC_Currency_ID());
				sumba.setamtdocument(this.getuy_totalme());
				sumba.setAmtSourceCr(this.getuy_totalme());
				sumba.setAmtSourceDr(Env.ZERO);
				sumba.setAmtAcctDr(Env.ZERO);
				BigDecimal currencyRate = Env.ONE;
				if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + this.getDateTrx());
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
			// Fin OpenUp. Issue #935.
			
			//OpenUp. Nicolas Sarlabos. 31/03/2016. #5395. Se debita en la cuenta origen.
			MBankAccount ba = (MBankAccount)linea.getC_BankAccount();
			MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
			sumba.setC_BankAccount_ID(ba.get_ID());
			sumba.setDateTrx(this.getDateTrx());
			sumba.setC_DocType_ID(this.getC_DocType_ID());
			sumba.setDocumentNo(this.getDocumentNo());
			sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
			sumba.setRecord_ID(this.get_ID());
			sumba.setDueDate(this.getDateTrx());
			sumba.setC_BPartner_ID(this.getC_BPartner_ID());
			sumba.setDescription(this.getDescription());		
			sumba.setC_Currency_ID(ba.getC_Currency_ID());
			sumba.setamtdocument(linea.getuy_totalamt());
			sumba.setAmtSourceDr(linea.getuy_totalamt());
			sumba.setAmtSourceCr(Env.ZERO);
			sumba.setAmtAcctCr(Env.ZERO);
			BigDecimal currencyRate = Env.ONE;
			if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
				currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
				if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + this.getDateTrx());
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
			//Fin OpenUp.		
			
		}
		
		return true;

	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 22/02/2016.
	 * Completo deposito de ticket alimentacion.
	 */
	private boolean completeDepositoTicket() {

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		ArrayList<MMovBancariosLine> lines = this.getLines();
		MBankAccount accountFrom = null;
		MBankAccount accountTo = (MBankAccount)this.getC_BankAccount();
		String sql = "";
		BigDecimal amount = Env.ZERO, saldo = Env.ZERO;
		int accountID = 0, qty = 0, saldoDoc = 0;
		
		if (lines.size() == 0) {
			this.processMsg = "Este documento no tiene lineas. No puede completarse.";
			return false;
		}

		try{
			
			sql = "select c_bankaccount_id, uy_totalamt, qtycount" +
					" from uy_movbancariosline" +
					" where uy_movbancarioshdr_id = " + this.get_ID() +
					" order by c_bankaccount_id asc";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){
				
				int account_ID = rs.getInt("c_bankaccount_id");
				BigDecimal amt = rs.getBigDecimal("uy_totalamt");
				int qtyCount = rs.getInt("qtycount");
				
				//corte de control por cuenta bancaria
				if(accountID != account_ID){
					
					if(amount.compareTo(Env.ZERO)>0){
						
						accountFrom = new MBankAccount(getCtx(),accountID,get_TrxName());
						
						//saldo de importe
						sql = "select coalesce((sum(amtsourcecr)-sum(amtsourcedr)),0)" +
								" from uy_sum_accountstatus" + 
								" where c_bankaccount_id = " + accountID +
								" and datetrx <= '" + this.getDateTrx() + "'";

						saldo = DB.getSQLValueBDEx(get_TrxName(), sql);
						
						//saldo de cantidad de tickets
						sql = "select coalesce(sum(qty),0)" +
								" from uy_sum_accountstatus" + 
								" where c_bankaccount_id = " + accountID +
								" and datetrx <= '" + this.getDateTrx() + "'";

						saldoDoc = DB.getSQLValueEx(get_TrxName(), sql);
						
						if(saldo.compareTo(amount) < 0) 
							throw new AdempiereException("El saldo de la cuenta origen '" + accountFrom.getDescription() + "' es insuficiente para el importe ingresado");
												
						if(saldoDoc < qty) 
							throw new AdempiereException("La cuenta origen '" + accountFrom.getDescription() + "' no tiene la cantidad de documentos necesaria");
						
						//registro movimiento al DEBE de la cuenta ORIGEN
						MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
						sumba.setC_BankAccount_ID(accountFrom.get_ID());
						sumba.setDateTrx(this.getDateTrx());
						sumba.setC_DocType_ID(this.getC_DocType_ID());
						sumba.setDocumentNo(this.getDocumentNo());
						sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
						sumba.setRecord_ID(this.get_ID());
						sumba.setDueDate(this.getDateTrx());
						sumba.setDescription(this.getDescription());		
						sumba.setC_Currency_ID(accountFrom.getC_Currency_ID());
						sumba.setamtdocument(amount);
						sumba.setAmtSourceDr(amount);
						sumba.setAmtSourceCr(Env.ZERO);
						sumba.setAmtAcctCr(Env.ZERO);
						sumba.setCurrencyRate(Env.ONE);
						sumba.setAmtAcctDr(sumba.getAmtSourceDr());
						sumba.setAmtAcctCr(sumba.getAmtSourceCr());
						sumba.setQty(qty * (-1));
						sumba.setUY_PaymentRule_ID(accountFrom.getUY_PaymentRule_ID());

						sumba.saveEx();						
					}				
					
					accountID = account_ID;
					amount = Env.ZERO;							
				}

				amount = amount.add(amt);
				qty += qtyCount;				
			}
			
			if(amount.compareTo(Env.ZERO)>0){
				
				accountFrom = new MBankAccount(getCtx(),accountID,get_TrxName());
				
				//saldo de importe
				sql = "select coalesce((sum(amtsourcecr)-sum(amtsourcedr)),0)" +
						" from uy_sum_accountstatus" + 
						" where c_bankaccount_id = " + accountID +
						" and datetrx <= '" + this.getDateTrx() + "'";

				saldo = DB.getSQLValueBDEx(get_TrxName(), sql);
				
				//saldo de cantidad de tickets
				sql = "select coalesce(sum(qty),0)" +
						" from uy_sum_accountstatus" + 
						" where c_bankaccount_id = " + accountID +
						" and datetrx <= '" + this.getDateTrx() + "'";

				saldoDoc = DB.getSQLValueEx(get_TrxName(), sql);
				
				if(saldo.compareTo(amount) < 0) 
					throw new AdempiereException("El saldo de la cuenta origen '" + accountFrom.getDescription() + "' es insuficiente para el importe ingresado");
				
				if(saldoDoc < qty) 
					throw new AdempiereException("La cuenta origen '" + accountFrom.getDescription() + "' no tiene la cantidad de documentos necesaria");
				
				//registro movimiento al DEBE de la cuenta ORIGEN
				MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(accountFrom.get_ID());
				sumba.setDateTrx(this.getDateTrx());
				sumba.setC_DocType_ID(this.getC_DocType_ID());
				sumba.setDocumentNo(this.getDocumentNo());
				sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
				sumba.setRecord_ID(this.get_ID());
				sumba.setDueDate(this.getDateTrx());
				sumba.setDescription(this.getDescription());		
				sumba.setC_Currency_ID(accountFrom.getC_Currency_ID());
				sumba.setamtdocument(amount);
				sumba.setAmtSourceDr(amount);
				sumba.setAmtSourceCr(Env.ZERO);
				sumba.setAmtAcctCr(Env.ZERO);
				sumba.setCurrencyRate(Env.ONE);
				sumba.setAmtAcctDr(sumba.getAmtSourceDr());
				sumba.setAmtAcctCr(sumba.getAmtSourceCr());
				sumba.setQty(qty * (-1));
				sumba.setUY_PaymentRule_ID(accountFrom.getUY_PaymentRule_ID());

				sumba.saveEx();				
				
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

		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {

			MMovBancariosLine linea = lines.get(i);			
			
			linea.setDateTrx(this.getDateTrx());
			linea.setDateAcct(this.getDateAcct());
			if (linea.getDueDate() == null) linea.setDueDate(this.getDateTrx());
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Completed);			
			linea.saveEx();			
		}
		
		//registro movimiento al HABER de la cuenta DESTINO
		sql = "select sum(QtyCount)" +
				" from uy_movbancariosline" +
				" where uy_movbancarioshdr_id = " + this.get_ID();
		
		qty = DB.getSQLValueEx(get_TrxName(), sql);
		
		MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
		sumba.setC_BankAccount_ID(this.getC_BankAccount_ID());
		sumba.setDateTrx(this.getDateTrx());
		sumba.setC_DocType_ID(this.getC_DocType_ID());
		sumba.setDocumentNo(this.getDocumentNo());
		sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
		sumba.setRecord_ID(this.get_ID());
		sumba.setDueDate(this.getDateTrx());
		sumba.setDescription(this.getDescription());		
		sumba.setC_Currency_ID(accountTo.getC_Currency_ID());
		sumba.setamtdocument(this.getuy_totalme());
		sumba.setAmtSourceCr(this.getuy_totalme());
		sumba.setAmtSourceDr(Env.ZERO);
		sumba.setAmtAcctDr(Env.ZERO);
		sumba.setCurrencyRate(Env.ONE);
		sumba.setAmtAcctDr(sumba.getAmtSourceDr());
		sumba.setAmtAcctCr(sumba.getAmtSourceCr());
		sumba.setQty(qty);
	
		sumba.saveEx();	
		
		return true;

	}
	
	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo deposito de cheque de tercero.
	 */
	private boolean completeDepositoChequeTercero() {

		ArrayList<MMovBancariosLine> lines = this.getLines();

		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede completarse.";
			return false;
		}

		// OpenUp. Gabriel Vila. 04/06/2013. Issue #935.
		// Moneda nacional 
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		// Fin OpenUp. Issue #935.
		
		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);
			
			// Si esta linea tiene un medio de pago
			if (linea.getUY_MediosPago_ID() > 0) {
				
				MMediosPago mpLine = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());
				if (mpLine.get_ID() <= 0){
					this.processMsg = "No se pudo obtener el cheque con ID : " + this.getUY_MediosPago_ID() + " utilizado en la linea : " + linea.getDocumentNo() + " de esta transaccion";
					return false;
				}			

				if (!mpLine.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Terceros)){
					this.processMsg = "El cheque numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + " no es de terceros.";
					return false;
				}

				if (!mpLine.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Cartera)){
					this.processMsg = "El cheque numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + " no esta en Cartera.";
					return false;
				}

				mpLine.setoldstatus(mpLine.getestado());
				mpLine.setestado(X_UY_MediosPago.ESTADO_Depositado);
				mpLine.setUY_MovBancariosHdr_ID(this.get_ID());
				mpLine.setUY_MovBancariosLine_ID(linea.get_ID());
				mpLine.saveEx();
				
				// OpenUp. Gabriel Vila. 04/06/2013. Issue #935.
				// Actualizacion de tabla sumarizadora

				// Elimino de esta tabla la entrada por el recibo a este cheque.
				String action = " delete from uy_sum_accountstatus " +
						" where uy_mediospago_id =" + mpLine.get_ID();
				int cant = DB.executeUpdateEx(action, get_TrxName());
				
				// Si no encontro el cheque por id me aseguro de buscarlo por numero de cheque
				if (cant <= 0){
					action = " delete from uy_sum_accountstatus " +
							 " where checkno ='" + mpLine.getCheckNo() + "' ";
					DB.executeUpdateEx(action, get_TrxName());					
				}
				
				MBankAccount ba = (MBankAccount)this.getC_BankAccount();
				MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(this.getC_BankAccount_ID());
				sumba.setDateTrx(this.getDateTrx());
				sumba.setC_DocType_ID(this.getC_DocType_ID());
				sumba.setDocumentNo(this.getDocumentNo());
				sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
				sumba.setRecord_ID(this.get_ID());
				sumba.setC_BPartner_ID(this.getC_BPartner_ID());
				sumba.setDescription(this.getDescription());						
				sumba.setCheckNo(mpLine.getCheckNo());
				sumba.setDueDate(this.getDateTrx());
				sumba.setStatus(mpLine.getestado());
				sumba.setC_Currency_ID(ba.getC_Currency_ID());
				sumba.setC_BPartner_ID(mpLine.getC_BPartner_ID());
				sumba.setamtdocument(mpLine.getPayAmt());
				sumba.setAmtSourceCr(mpLine.getPayAmt());
				sumba.setAmtSourceDr(Env.ZERO);
				sumba.setAmtAcctDr(Env.ZERO);
				sumba.setUY_MediosPago_ID(mpLine.get_ID());
				BigDecimal currencyRate = Env.ONE;
				
				if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + this.getDateTrx());
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
				// Fin OpenUp. Issue #935.
			}

			this.setDateTrx(this.getDateTrx());
			this.setDateAcct(this.getDateAcct());
			if (this.getDueDate() == null) this.setDueDate(this.getDateTrx());
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Completed);			
			linea.saveEx();
		}

		return true;

	}

	/***
	 * Deposito de cheques propios.
	 * OpenUp Ltda. Issue #5009
	 * @author gabriel - Nov 6, 2015
	 * @return
	 */
	private boolean completeDepositoChequePropio() {

		MBankAccount ba = null;
		MSUMAccountStatus sumba = null;
		BigDecimal currencyRate = Env.ZERO;
		
		ArrayList<MMovBancariosLine> lines = this.getLines();

		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede completarse.";
			return false;
		}

		// OpenUp. Gabriel Vila. 04/06/2013. Issue #935.
		// Moneda nacional 
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		// Fin OpenUp. Issue #935.
		
		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);
			
			// Si esta linea tiene un medio de pago
			if (linea.getUY_MediosPago_ID() > 0) {
				
				MMediosPago mpLine = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());
				if (mpLine.get_ID() <= 0){
					this.processMsg = "No se pudo obtener el cheque con ID : " + this.getUY_MediosPago_ID() + " utilizado en la linea : " + linea.getDocumentNo() + " de esta transaccion";
					return false;
				}			

				if (!mpLine.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Propio)){
					this.processMsg = "El medio de pago numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + " no es propio.";
					return false;
				}

				if (!mpLine.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Emitido)){
					this.processMsg = "El medio de pago numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + " no esta en Estado Emitido.";
					return false;
				}

				mpLine.setoldstatus(mpLine.getestado());
				mpLine.setestado(X_UY_MediosPago.ESTADO_Depositado);
				mpLine.setUY_MovBancariosHdr_ID(this.get_ID());
				mpLine.setUY_MovBancariosLine_ID(linea.get_ID());
				mpLine.saveEx();
				
				// OpenUp. Gabriel Vila. 04/06/2013. Issue #935.
				// Actualizacion de tabla sumarizadora

				// Elimino de esta tabla la entrada de este medio de pago.
				String action = " delete from uy_sum_accountstatus " +
						" where uy_mediospago_id =" + mpLine.get_ID();
				int cant = DB.executeUpdateEx(action, get_TrxName());
				
				// Si no encontro el cheque por id me aseguro de buscarlo por numero de cheque
				if (cant <= 0){
					action = " delete from uy_sum_accountstatus " +
							 " where checkno ='" + mpLine.getCheckNo() + "' ";
					DB.executeUpdateEx(action, get_TrxName());					
				}
				
				//se acredita en la cuenta destino
				ba = (MBankAccount)this.getC_BankAccount();
				sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(this.getC_BankAccount_ID());
				sumba.setDateTrx(this.getDateTrx());
				sumba.setC_DocType_ID(this.getC_DocType_ID());
				sumba.setDocumentNo(this.getDocumentNo());
				sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
				sumba.setRecord_ID(this.get_ID());
				sumba.setDescription(this.getDescription());						
				sumba.setCheckNo(mpLine.getCheckNo());
				sumba.setDueDate(mpLine.getDateTrx());
				sumba.setStatus(mpLine.getestado());
				sumba.setC_Currency_ID(ba.getC_Currency_ID());
				sumba.setC_BPartner_ID(mpLine.getC_BPartner_ID());
				sumba.setamtdocument(mpLine.getPayAmt());
				sumba.setAmtSourceCr(mpLine.getPayAmt());
				sumba.setAmtSourceDr(Env.ZERO);
				sumba.setAmtAcctDr(Env.ZERO);
				sumba.setUY_MediosPago_ID(mpLine.get_ID());
				currencyRate = Env.ONE;
				
				if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + this.getDateTrx());
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
				// Fin OpenUp. Issue #935.
				
				//se debita en la cuenta origen
				ba = (MBankAccount)linea.getC_BankAccount();
				sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(ba.get_ID());
				sumba.setDateTrx(this.getDateTrx());
				sumba.setC_DocType_ID(this.getC_DocType_ID());
				sumba.setDocumentNo(this.getDocumentNo());
				sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
				sumba.setRecord_ID(this.get_ID());
				sumba.setDescription(this.getDescription());						
				sumba.setCheckNo(mpLine.getCheckNo());
				sumba.setDueDate(mpLine.getDateTrx());
				sumba.setStatus(mpLine.getestado());
				sumba.setC_Currency_ID(ba.getC_Currency_ID());
				sumba.setC_BPartner_ID(mpLine.getC_BPartner_ID());
				sumba.setamtdocument(mpLine.getPayAmt());
				sumba.setAmtSourceDr(mpLine.getPayAmt());
				sumba.setAmtSourceCr(Env.ZERO);
				sumba.setAmtAcctCr(Env.ZERO);
				sumba.setUY_MediosPago_ID(mpLine.get_ID());
				currencyRate = Env.ONE;
				
				if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + this.getDateTrx());
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

			this.setDateTrx(this.getDateTrx());
			this.setDateAcct(this.getDateAcct());
			if (this.getDueDate() == null) this.setDueDate(this.getDateTrx());
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Completed);			
			linea.saveEx();
		}

		return true;

	}

	
	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo descuento de cheque de tercero.
	 */
	private boolean completeDescuentoChequeTercero() {

		ArrayList<MMovBancariosLine> lines = this.getLines();

		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede completarse.";
			return false;
		}

		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);
			
			// Si esta linea tiene un medio de pago
			if (linea.getUY_MediosPago_ID() > 0) {
				
				MMediosPago mpLine = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());
				if (mpLine.get_ID() <= 0){
					this.processMsg = "No se pudo obtener el cheque con ID : " + this.getUY_MediosPago_ID() + " utilizado en la linea : " + linea.getDocumentNo() + " de esta transaccion";
					return false;
				}			

				if (!mpLine.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Terceros)){
					this.processMsg = "El cheque numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + " no es de terceros.";
					return false;
				}

				if (!mpLine.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Cartera)){
					this.processMsg = "El cheque numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + " no esta en Cartera.";
					return false;
				}

				mpLine.setoldstatus(mpLine.getestado());
				mpLine.setestado(X_UY_MediosPago.ESTADO_Descontado);
				mpLine.setUY_MovBancariosHdr_ID(this.get_ID());
				mpLine.setUY_MovBancariosLine_ID(linea.get_ID());
				mpLine.saveEx();
			}

			this.setDateTrx(this.getDateTrx());
			this.setDateAcct(this.getDateAcct());
			if (this.getDueDate() == null) this.setDueDate(this.getDateTrx());
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Completed);			
			linea.saveEx();
		}
		
		// OpenUp. Nicolas Sarlabos. 16/02/2016. Issue #5388.
		// Actualizacion de tabla sumarizadora
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();

		MBankAccount ba = (MBankAccount)this.getC_BankAccount();
		MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
		sumba.setC_BankAccount_ID(ba.get_ID());
		sumba.setDateTrx(this.getDateTrx());
		sumba.setC_DocType_ID(this.getC_DocType_ID());
		sumba.setDocumentNo(this.getDocumentNo());
		sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
		sumba.setRecord_ID(this.get_ID());
		sumba.setDueDate(this.getDateTrx());
		sumba.setDescription(this.getDescription());
		sumba.setC_Currency_ID(ba.getC_Currency_ID());
		sumba.setamtdocument(this.getuy_totalme());
		sumba.setAmtSourceDr(Env.ZERO);
		sumba.setAmtAcctDr(Env.ZERO);
		sumba.setAmtSourceCr(this.getuy_totalme());
		BigDecimal currencyRate = Env.ONE;

		if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
			currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
			if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + this.getDateTrx());
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
		// Fin OpenUp. Issue #5388.

		return true;

	}

	
	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo conciliacion de cheques propios.
	 */
	private boolean completeConciliacionChequePropio() {

		ArrayList<MMovBancariosLine> lines = this.getLines();

		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede completarse.";
			return false;
		}

		// OpenUp. Gabriel Vila. 04/06/2013. Issue #935.
		// Moneda nacional 
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		// Fin OpenUp. Issue #935.
		
		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);
			
			// Si esta linea tiene un medio de pago
			if (linea.getUY_MediosPago_ID() > 0) {
				
				MMediosPago mpLine = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());
				if (mpLine.get_ID() <= 0){
					this.processMsg = "No se pudo obtener el cheque con ID : " + this.getUY_MediosPago_ID() + " utilizado en la linea : " + linea.getDocumentNo() + " de esta transaccion";
					return false;
				}			

				if (!mpLine.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Propio)){
					this.processMsg = "El cheque numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + " no es propio.";
					return false;
				}

				if ((!mpLine.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Entregado))
						&& (!mpLine.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Transferido))){
					
					this.processMsg = "El cheque numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + " no esta en estado Emitido o Transferido.";
					return false;
				}

				mpLine.setoldstatus(mpLine.getestado());
				mpLine.setestado(X_UY_MediosPago.ESTADO_Pago);
				mpLine.setUY_MovBancariosHdr_ID(this.get_ID());
				mpLine.setUY_MovBancariosLine_ID(linea.get_ID());
				mpLine.saveEx();

				// OpenUp. Gabriel Vila. 04/06/2013. Issue #935.
				// Actualizacion de tabla sumarizadora
				// Elimino de esta tabla la entrada por el recibo a este cheque.
				String action = " delete from uy_sum_accountstatus " +
						" where uy_mediospago_id =" + mpLine.get_ID();
				int cant = DB.executeUpdateEx(action, get_TrxName());
				// Si no encontro el cheque por id me aseguro de buscarlo por numero de cheque
				if (cant <= 0){
					action = " delete from uy_sum_accountstatus " +
							 " where checkno ='" + mpLine.getCheckNo() + "' " +
					DB.executeUpdateEx(action, get_TrxName());					
				}
				
				MBankAccount ba = (MBankAccount)this.getC_BankAccount();
				MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(this.getC_BankAccount_ID());
				sumba.setDateTrx(this.getDateTrx());
				sumba.setC_DocType_ID(this.getC_DocType_ID());
				sumba.setDocumentNo(this.getDocumentNo());
				sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
				sumba.setRecord_ID(this.get_ID());
				sumba.setC_BPartner_ID(this.getC_BPartner_ID());
				sumba.setDescription(this.getDescription());						
				sumba.setCheckNo(mpLine.getCheckNo());
				sumba.setDueDate(mpLine.getDueDate());
				sumba.setStatus(mpLine.getestado());
				sumba.setC_Currency_ID(ba.getC_Currency_ID());
				sumba.setC_BPartner_ID(mpLine.getC_BPartner_ID());
				sumba.setamtdocument(mpLine.getPayAmt());
				sumba.setAmtSourceDr(mpLine.getPayAmt());
				sumba.setAmtSourceCr(Env.ZERO);
				sumba.setAmtAcctCr(Env.ZERO);
				BigDecimal currencyRate = Env.ONE;
				
				if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + this.getDateTrx());
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
				// Fin OpenUp. Issue #935.

			}

			linea.setDateTrx(this.getDateTrx());
			linea.setDateAcct(this.getDateAcct());
			if (linea.getDueDate() == null) linea.setDueDate(this.getDateTrx());
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Completed);			
			linea.saveEx();
		}

		return true;

	}

	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo pago de vales bancarios.
	 */
	private boolean completePagoValeBancario() {
		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo vales bancarios.
	 */
	private boolean completeValesBancarios() {
		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo cobro de conforme.
	 */
	private boolean completeCobroConformes() {

		// Verifico conforme seleccionado
		MMediosPago mpHdr = new MMediosPago(getCtx(), this.getUY_MediosPago_ID(), get_TrxName());
		if (mpHdr.get_ID() <= 0){
			this.processMsg = "No se pudo obtener el conforme con ID : " + this.getUY_MediosPago_ID() + " utilizado en el cabezal de esta transaccion.";
			return false;
		}			

		if (!mpHdr.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Terceros)){
			this.processMsg = "El conforme a cobrar no es de terceros.";
			return false;
		}

		if ((!mpHdr.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Cartera))
				&& (!mpHdr.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Depositado))
				&& (!mpHdr.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Descontado))){
			this.processMsg = "No se puede cobrar este conforme ya que no esta en cartera o depositado o descontado.";
			return false;
		}	
		
		mpHdr.setoldstatus(mpHdr.getestado());
		mpHdr.setestado(X_UY_MediosPago.ESTADO_Cobrado);
		mpHdr.setUY_MovBancariosHdr_ID(this.get_ID());
		mpHdr.setUY_MovBancariosLine_ID(0);
		mpHdr.saveEx();
		
		ArrayList<MMovBancariosLine> lines = this.getLines();

		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede completarse.";
			return false;
		}

		BigDecimal amtLines = Env.ZERO;
		
		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);

			amtLines = amtLines.add(linea.getuy_totalamt());
			
			// Creo medio de pago ahora cuando no es efectivo
			if (!linea.getTenderType().equalsIgnoreCase(X_C_Payment.TENDERTYPE_Cash)) {
				
				MMediosPago mediopago = new MMediosPago(getCtx(), 0, get_TrxName());
				mediopago.setDateTrx(this.getDateTrx());
				mediopago.setDateAcct(this.getDateAcct());
				mediopago.setDueDate(linea.getDueDate());
				if (mediopago.getDueDate() == null) mediopago.setDueDate(this.getDateTrx());
				mediopago.setC_DocType_ID(1000044);
				mediopago.setC_BankAccount_ID(linea.getC_BankAccount_ID());
				mediopago.setC_BPartner_ID(this.getC_BPartner_ID());
				mediopago.setCheckNo(linea.getCheckNo());
				mediopago.setC_Currency_ID(this.getC_Currency_ID());
				mediopago.setPayAmt(linea.getuy_totalamt());
				mediopago.settipomp(X_UY_MediosPago.TIPOMP_Terceros);
				mediopago.setestado(X_UY_MediosPago.ESTADO_Cartera);
				mediopago.setDocStatus(MPayment.STATUS_Completed);
				mediopago.setPosted(true);
				mediopago.setProcessed(true);
				mediopago.setoldstatus(mediopago.getestado());
				mediopago.setUY_MovBancariosHdr_ID(this.get_ID());
				mediopago.setUY_MovBancariosLine_ID(linea.get_ID());
				mediopago.saveEx();
				
				linea.setUY_MediosPago_ID(mediopago.getUY_MediosPago_ID());
			}

			linea.setDateTrx(this.getDateTrx());
			linea.setDateAcct(this.getDateAcct());
			if (linea.getDueDate() == null) linea.setDueDate(this.getDateTrx());
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Completed);			
			linea.saveEx();
		}

		// Valido que suma de importes de las lineas sea igual al total del cabezal
		if (amtLines.compareTo(this.getuy_total_manual()) != 0){
			this.processMsg = "La suma de los importes de las lineas debe ser igual al importe del cabezal.";
			return false;
		}
		
		
		return true;

	}

	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo cambio de cheque de tercero.
	 */
	private boolean completeCambioChequePropio() {
		
		// Cambio estado del cheque del cabezal que origino la transaccion de cambio de cheque
		MMediosPago mpHdr = new MMediosPago(getCtx(), this.getUY_MediosPago_ID(), get_TrxName());
		if (mpHdr.get_ID() <= 0){
			this.processMsg = "No se pudo obtener el cheque con ID : " + this.getUY_MediosPago_ID() + " utilizado en el cabezal de esta transaccion.";
			return false;
		}			

		if (!mpHdr.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Propio)){
			this.processMsg = "El cheque a cambiar no es propio.";
			return false;
		}

		if (!mpHdr.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Rechazado)){
			this.processMsg = "El cheque a cambiar no esta en estado Rechazado.";
			return false;
		}
		
		mpHdr.setoldstatus(mpHdr.getestado());
		mpHdr.setestado(X_UY_MediosPago.ESTADO_Cambiado);
		mpHdr.setUY_MovBancariosHdr_ID(this.get_ID());
		mpHdr.setUY_MovBancariosLine_ID(0);
		mpHdr.saveEx();
		
		ArrayList<MMovBancariosLine> lines = this.getLines();

		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede completarse.";
			return false;
		}

		BigDecimal amtLines = Env.ZERO;
		
		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);

			amtLines = amtLines.add(linea.getuy_totalamt());
			
			// Si esta linea tiene un medio de pago propio asociado
			if (linea.getUY_MediosPago_ID() > 0) {
				
				MMediosPago mpLine = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID(), get_TrxName());
				if (mpLine.get_ID() <= 0){
					this.processMsg = "No se pudo obtener el cheque con ID : " + this.getUY_MediosPago_ID() + " utilizado en la linea : " + linea.getDocumentNo() + " de esta transaccion";
					return false;
				}			

				if (!mpLine.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Propio)){
					this.processMsg = "El cheque numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + ", no es propio.";
					return false;
				}

				if (!mpLine.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Emitido)){
					this.processMsg = "El cheque numero : " + mpLine.getCheckNo() + " utilizado en la linea : " + linea.getDocumentNo() + ", no esta en estado Emitido.";
					return false;
				}

				mpLine.setoldstatus(mpLine.getestado());
				mpLine.setestado(X_UY_MediosPago.ESTADO_Entregado);
				mpLine.setUY_MovBancariosHdr_ID(this.get_ID());
				mpLine.setUY_MovBancariosLine_ID(linea.get_ID());
				mpLine.saveEx();

			}

			linea.setDateTrx(this.getDateTrx());
			linea.setDateAcct(this.getDateAcct());
			if (linea.getDueDate() == null) linea.setDueDate(this.getDateTrx());
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Completed);			
			linea.saveEx();
		}

		// Valido que suma de importes de las lineas sea igual al total del cabezal
		if (amtLines.compareTo(this.getuy_total_manual()) != 0){
			this.processMsg = "La suma de los importes de las lineas debe ser igual al importe del cabezal.";
			return false;
		}
		
		return true;

	}

	/**
	 * OpenUp. Gabriel Vila. 19/12/2011.
	 * Completo cambio de cheque de tercero.
	 * @throws Exception 
	 */
	private boolean completeCambioChequeTercero() {

		// Cambio estado del cheque del cabezal que origino la transaccion de cambio de cheque
		MMediosPago mpHdr = new MMediosPago(getCtx(), this.getUY_MediosPago_ID(), get_TrxName());
		if (mpHdr.get_ID() <= 0){
			this.processMsg = "No se pudo obtener el cheque con ID : " + this.getUY_MediosPago_ID() + " utilizado en el cabezal de esta transaccion.";
			return false;
		}			

		if (!mpHdr.gettipomp().equalsIgnoreCase(X_UY_MediosPago.TIPOMP_Terceros)){
			this.processMsg = "El cheque a cambiar no es de terceros.";
			return false;
		}

		if (!mpHdr.getestado().equalsIgnoreCase(X_UY_MediosPago.ESTADO_Rechazado)){
			this.processMsg = "El cheque a cambiar no esta en estado Rechazado.";
			return false;
		}
		
		mpHdr.setoldstatus(mpHdr.getestado());
		mpHdr.setestado(X_UY_MediosPago.ESTADO_Cambiado);
		mpHdr.setUY_MovBancariosHdr_ID(this.get_ID());
		mpHdr.setUY_MovBancariosLine_ID(0);
		mpHdr.saveEx();
		
		ArrayList<MMovBancariosLine> lines = this.getLines();

		if (lines.size() == 0) {
			this.processMsg = "Esta transacción no tiene lineas. No puede completarse.";
			return false;
		}

		BigDecimal amtLines = Env.ZERO;
		
		// Recorro lineas 
		for (int i = 0; i < lines.size(); i++) {
			
			MMovBancariosLine linea = lines.get(i);

			amtLines = amtLines.add(linea.getuy_totalamt());
			
			// Creo medio de pago ahora cuando no es efectivo
			if (!linea.getTenderType().equalsIgnoreCase(X_C_Payment.TENDERTYPE_Cash)) {
				
				MMediosPago mediopago = new MMediosPago(getCtx(), 0, get_TrxName());
				mediopago.setDateTrx(this.getDateTrx());
				mediopago.setDateAcct(this.getDateAcct());
				mediopago.setDueDate(linea.getDueDate());
				if (mediopago.getDueDate() == null) mediopago.setDueDate(this.getDateTrx());
				MDocType doc = MDocType.forValue(getCtx(), "camcheqter", null);
				if ((doc == null) || (doc.get_ID() <= 0)){
					this.processMsg = "Falta parametrizar clave de búsqueda para el documento actual.";
					return false;
				}
				mediopago.setC_DocType_ID(doc.get_ID());
				mediopago.setC_BankAccount_ID(linea.getC_BankAccount_ID());
				mediopago.setC_BPartner_ID(this.getC_BPartner_ID());
				mediopago.setCheckNo(linea.getCheckNo());
				mediopago.setC_Currency_ID(this.getC_Currency_ID());
				mediopago.setPayAmt(linea.getuy_totalamt());
				mediopago.settipomp(X_UY_MediosPago.TIPOMP_Terceros);
				mediopago.setestado(X_UY_MediosPago.ESTADO_Cartera);
				mediopago.setoldstatus(mediopago.getestado());
				mediopago.setUY_MovBancariosHdr_ID(this.get_ID());
				mediopago.setUY_MovBancariosLine_ID(linea.get_ID());
				mediopago.saveEx();

				try {
					if (!mediopago.processIt(DocumentEngine.ACTION_Complete)){
						this.processMsg = mediopago.getProcessMsg();
						return false;
					}
				} catch (Exception e) {
					throw new AdempiereException(e.getMessage());
				}
				
				linea.setUY_MediosPago_ID(mediopago.getUY_MediosPago_ID());
				
				// Insert nuevo cheque en tabla sumarizadora
				MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(mediopago.getC_BankAccount_ID());
				sumba.setDateTrx(mediopago.getDateTrx());
				sumba.setC_DocType_ID(this.getC_DocType_ID());
				sumba.setDocumentNo(this.getDocumentNo());
				sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
				sumba.setRecord_ID(this.get_ID());
				sumba.setDueDate(mediopago.getDueDate());
				sumba.setC_BPartner_ID(this.getC_BPartner_ID());
				sumba.setDescription(this.getDescription());
				sumba.setCheckNo(mediopago.getCheckNo());
				sumba.setDueDate(mediopago.getDueDate());
				sumba.setStatus(mediopago.getestado());
				sumba.setUY_MediosPago_ID(mediopago.get_ID());
				sumba.setC_Currency_ID(this.getC_Currency_ID());
				sumba.setamtdocument(mediopago.getPayAmt());
				sumba.setAmtSourceCr(mediopago.getPayAmt());
				sumba.setAmtSourceDr(Env.ZERO);
				sumba.setAmtAcctDr(Env.ZERO);
				
				BigDecimal currencyRate = Env.ONE;
				MBankAccount ba = (MBankAccount)mediopago.getC_BankAccount();
				if (ba.getC_Currency_ID() != this.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(this.getC_Currency_ID(), ba.getC_Currency_ID(), mediopago.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + mediopago.getDateTrx());
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

			linea.setDateTrx(this.getDateTrx());
			linea.setDateAcct(this.getDateAcct());
			if (linea.getDueDate() == null) linea.setDueDate(this.getDateTrx());
			linea.setProcessed(true);
			linea.setDocStatus(DOCSTATUS_Completed);			
			linea.saveEx();
		}

		// Valido que suma de importes de las lineas sea igual al total del cabezal
		if (amtLines.compareTo(this.getuy_total_manual()) != 0){
			this.processMsg = "La suma de los importes de las lineas debe ser igual al importe del cabezal.";
			return false;
		}
		
		
		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 30/01/2012.
	 * Dado un cheque y un estado, busca el ultimo tracking de este cheque con este estado.
	 * @param uyMediosPagoID
	 * @param estado
	 * @param trxName
	 * @return
	 * @throws Exception
	 */
	private MCheckTracking getCheckTracking(int uyMediosPagoID, String estado, String trxName) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MCheckTracking value = new MCheckTracking(getCtx(), 0, null);		
		
		try{			
			sql = " select uy_checktracking_id " +
				  " from uy_checktracking " +	
				  " where uy_mediospago_id =? " +
				  " and checkstatus =? " + 
				  " order by updated desc";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, uyMediosPagoID);
			pstmt.setString(2, estado);
			rs = pstmt.executeQuery ();
			if (rs.next()) value = new MCheckTracking(getCtx(), rs.getInt("uy_checktracking_id"), trxName);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/***
	 * OpenUp. Guillermo Brust
	 * @param ctx
	 * @param uyMovBancariosHdrID
	 * @param dateTo
	 * @param trxName
	 * @return Saldo Pendiente a la fecha pasada por parametro
	 */
	public static BigDecimal getSaldoPendienteVale(Properties ctx, int uyMovBancariosHdrID, Timestamp dateTo, String trxName){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal value = Env.ZERO;		
		
		try{			
			sql = " SELECT (coalesce(hdr.uy_totalmn, 0) - coalesce(hdr.totcobrado, 0) - coalesce(uy_subtotal, 0)) as saldo " +
				  " FROM uy_movbancarioshdr hdr" + 
			      " INNER JOIN c_doctype doc ON hdr.c_doctype_id = doc.c_doctype_id" +
		          " WHERE doc.docbasetype = 'PVB' AND hdr.uy_numvale =? AND hdr.docstatus = 'CO'";
			
			if(dateTo != null){
				sql += " AND hdr.datetrx <=?";
			}else{
				sql += "";
			}
			
			sql += " ORDER BY hdr.datetrx DESC";
				
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, Integer.toString(uyMovBancariosHdrID));
			
			if(dateTo != null) pstmt.setTimestamp(2, dateTo);			

			rs = pstmt.executeQuery ();
			if (rs.next()) value = rs.getBigDecimal(1);
		
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
		
	}
	
	
	/**
	 * OpenUp. Gabriel Vila. 01/09/2013. Issue #
	 * Dado un documento, busco por cheque el ultimo documento completo anterior a este.
	 * @param uyMediosPagoID
	 * @param estado
	 * @param trxName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private MCheckTracking getLastDocument(int uyMediosPagoID, String estado, String trxName) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MCheckTracking value = new MCheckTracking(getCtx(), 0, null);		
		
		try{			
			sql = " ";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			if (rs.next()) value = new MCheckTracking(getCtx(), rs.getInt("uy_checktracking_id"), trxName);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}

	
}
