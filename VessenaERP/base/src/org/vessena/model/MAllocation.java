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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactLine;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoicePaySchedule;
import org.compiere.model.MPayment;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.util.OpenUpUtils;

/**
 * @author OpenUp. Gabriel Vila. 18/10/2011.
 * Modelo para cabezal de Afectaciones de ctacte.
 */
public class MAllocation extends X_UY_Allocation implements DocAction {


	private static final long serialVersionUID = -7347696036926286401L;
	private String processMsg = null;
	private boolean justPrepared = false;
	
	/**
	 * @param ctx
	 * @param UY_Allocation_ID
	 * @param trxName
	 */
	public MAllocation(Properties ctx, int UY_Allocation_ID, String trxName) {
		super(ctx, UY_Allocation_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAllocation(Properties ctx, ResultSet rs, String trxName) {
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
		
		// Todo bien
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
		//	Re-Check
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		// Verifico periodo contable para documento de afectacion
		MPeriod.testPeriodOpen(getCtx(), this.getDateAcct(), this.getC_DocType_ID(), this.getAD_Org_ID());
		
		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Verifico que los totales de recibos y facturas sean iguales.
		if(!this.verifyAmounts()) {
			this.processMsg = "El Total Afectado de Recibos DEBE SER IGUAL al Total afectado de Facturas."; 
			return DocAction.STATUS_Invalid;
		}
		
		/*if (this.getamtinvallocated().compareTo(this.getamtpayallocated()) != 0){
			this.processMsg = "El Total Afectado de Recibos DEBE SER IGUAL al Total afectado de Facturas."; 
			return DocAction.STATUS_Invalid;
		}*/
		
		// Elimino recibos y facturas con monto a afectar cero
		this.deletePaymentsAmtAllocatedZero();
		this.deleteInvoicesAmtAllocatedZero();
		
		// Procesa informacion de afectacion de documentos y valida que todo este consistente. 
		if (!this.processDocuments()){
			return DocAction.STATUS_Invalid;
		}

		// Marca documentos como procesados
		this.setPaymentsProcessed();
		this.setInvoicesProcessed();
		
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
	 * Verifica que el total de recibos y facturas sean iguales, o entren dentro de la tolerancia permitida.
	 * OpenUp Ltda. Issue #1303
	 * @author Nicolas Sarlabos - 11/11/2013
	 * @see
	 * @return
	 */	
	private boolean verifyAmounts() {
		
		BigDecimal diff = this.getamtpayallocated().subtract(this.getamtinvallocated()); //obtengo la diferencia entre importes
		BigDecimal bordeInferior = new BigDecimal(-0.99);
		BigDecimal bordeSuperior = new BigDecimal(0.99);
		
		if (diff.compareTo(bordeInferior)<0 || diff.compareTo(bordeSuperior)>0) return false;		
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		// Elimino asientos contables de esta afectacion
		FactLine.deleteFact(X_UY_Allocation.Table_ID, this.getUY_Allocation_ID(), get_TrxName());
		
		// Si es una afectacion directa, elimino afectacion del recibo origen
		if (!this.isManual()){
			if (this.getC_Payment_ID() > 0){
				MPayment paymentAfecDirect = new MPayment(getCtx(), this.getC_Payment_ID(), get_TrxName());
				if (!paymentAfecDirect.deleteDirectAllocation()){
					this.processMsg = paymentAfecDirect.getProcessMsg();
					return false;
				}
			}
		}
		
		// En cada recibo de esta afectacion actualizo el estado de documento
		this.updateStatusPaymentAllocDetail(DOCSTATUS_Voided);
		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setPosted(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
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

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// No dejo tocar datos del cabezal si tengo documentos cargados en una o ambas grillas
		if (!newRecord){
			
			if ((!this.is_ValueChanged(COLUMNNAME_amtinvallocated)) 
					&& (!this.is_ValueChanged(COLUMNNAME_amtpayallocated))
					&& (!this.is_ValueChanged(COLUMNNAME_amtinvallocated))
					&& (!this.is_ValueChanged(COLUMNNAME_DocumentNo))
					&& (!this.is_ValueChanged(COLUMNNAME_DocStatus))
					&& (!this.is_ValueChanged(COLUMNNAME_DocAction))
					&& (!this.is_ValueChanged(COLUMNNAME_Processed))
					&& (!this.is_ValueChanged(COLUMNNAME_Posted))){
				
				boolean tengoDocsCargados = false;
				MAllocationPayment[] payments = this.getPaymentLines(get_TrxName());
				if (payments.length > 0) tengoDocsCargados = true;
				if (!tengoDocsCargados){
					MAllocationInvoice[] invoices = this.getInvoiceLines(get_TrxName());
					if (invoices.length > 0) tengoDocsCargados = true;
				}
				if (tengoDocsCargados){
					throw new AdempiereException("No puede modificar datos de la afectacion ya que tiene recibos y/o facturas." + 
												 " Elimine primero estos documentos antes de modificar los datos de la afectacion");
				}				
			}
		}
		
		// Valido datos del modelo
		if (!validInput()){
			throw new AdempiereException(this.processMsg);
		}
		
		// Igualo fecha de contabilizacion con la fecha de transaccion
		this.setDateAcct(this.getDateTrx());
		
		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 18/10/2011.
	 * Valida datos del modelo.
	 * @return boolean. True si todo bien, False sino.
	 */
	private boolean validInput() {

		if (this.getC_Currency_ID() <= 0){
			this.processMsg = "Debe indicar Moneda de Afectacion 1";
			return false;
		}
		
		if (this.getC_Currency_ID() == this.getC_Currency2_ID()){
			this.processMsg = "Las monedas de Afectacion no pueden ser iguales.";
			return false;			
		}
		
		if (this.getC_Currency2_ID() > 0){
			if (this.getDivideRate().compareTo(Env.ZERO) <= 0){
				this.processMsg = "No hay tasa de cambio definida para fecha y monedas seleccionadas.";
				return false;				
			}
		}
		
		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 20/10/2011.
	 * Actualizo campo de total de recibos. 
	 * Hago traduccion de importes a moneda de afectacion 1 si es necesario.
	 */
	public void updateTotalPayments(){
		
		BigDecimal total = Env.ZERO;
		
		// Obtengo y recorro lineas de recibos de esta afectacion
		MAllocationPayment[] payments = this.getPaymentLines(get_TrxName());
		for (int i=0; i < payments.length; i++){
			
			MAllocationPayment payment = payments[i];
			
			// Si la moneda de la linea es igual a la moneda de afectacion 1
			if (payment.getC_Currency_ID() == this.getC_Currency_ID())
				total = total.add(payment.getamtallocated());	// Sumo afectacion al total, sin necesidad de conversion
			else{
				//OpenUp SBT 11/04/2016 Issue #5753
				// Necesito convertir el monto afectado de la linea a la moneda de afectacion 1. dependiendo de la moneda
				//Si la moneda de la factura es igual a la moneda nacional
				int monedaNacional = OpenUpUtils.getSchemaCurrencyID(this.getCtx(), payment.getAD_Client_ID(), null);
				//Si la moneda de la factura es igual a la moneda nacional
				if(payment.getC_Currency_ID() == monedaNacional){
					//Tengo que dividir el monto por la tasa de cambio
					total = total.add(payment.getamtallocated().divide(this.getDivideRate(),2,RoundingMode.HALF_UP));
				}else{
					total = total.add(payment.getamtallocated().multiply(this.getDivideRate()));
				}
			}
		}

		this.setamtpayallocated(total.setScale(2, RoundingMode.HALF_UP));
		this.saveEx();
	}
	
	
	/**
	 * OpenUp. Gabriel Vila. 20/10/2011.
	 * Actualizo campo de total de facturas. 
	 * Hago traduccion de importes a moneda de afectacion 1 si es necesario.
	 */
	public void updateTotalInvoices(){
		
		BigDecimal total = Env.ZERO;
		
		// Obtengo y recorro lineas de facturas de esta afectacion
		MAllocationInvoice[] invoices = this.getInvoiceLines(get_TrxName());
		for (int i=0; i < invoices.length; i++){
			
			MAllocationInvoice invoice = invoices[i];
			
			// Si la moneda de la linea es igual a la moneda de afectacion 1
			if (invoice.getC_Currency_ID() == this.getC_Currency_ID())
				total = total.add(invoice.getamtallocated());	// Sumo afectacion al total, sin necesidad de conversion
			else{//Si la moneda de la linea es distinta a la moneda de afectación 1
				//OpenUp SBT 11/04/2016 Issue #5753
				//Se debe convertir el monto afectado de la linea a la moneda de afectacion 1. dependiendo de la moneda es la conversión
				//Obtengo el id de la moneda nacional
				int monedaNacional = OpenUpUtils.getSchemaCurrencyID(this.getCtx(), invoice.getAD_Client_ID(), null);
				//Si la moneda de la factura es igual a la moneda nacional
				if(invoice.getC_Currency_ID() == monedaNacional){
					//Tengo que dividir el monto por la tasa de cambio
					total = total.add(invoice.getamtallocated().divide(this.getDivideRate(),2,RoundingMode.HALF_UP));
				}else{//Si no es de la misma moneda multiplico por la tc
					total = total.add(invoice.getamtallocated().multiply(this.getDivideRate()));
				}
				
			}
		}
		
		this.setamtinvallocated(total.setScale(2, RoundingMode.HALF_UP));
		this.saveEx();
	}
	
	/**
	 * OpenUp. Gabriel Vila. 20/10/2011.
	 * Obtengo y retorno array de lineas de recibo asociadas a esta afectacion.
	 * @param trxName
	 * @return
	 */
	public MAllocationPayment[] getPaymentLines(String trxName) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MAllocationPayment> list = new ArrayList<MAllocationPayment>();
		
		try{
			sql =" SELECT " + X_UY_AllocationPayment.COLUMNNAME_UY_AllocationPayment_ID + 
 		  	     " FROM " + X_UY_AllocationPayment.Table_Name + 
		  	     " WHERE " + X_UY_AllocationPayment.COLUMNNAME_UY_Allocation_ID + "=?" +
		  	     " ORDER BY DateDocument ";
			
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt(1, this.getUY_Allocation_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MAllocationPayment value = new MAllocationPayment(Env.getCtx(), rs.getInt(1), trxName);
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
		
		return list.toArray(new MAllocationPayment[list.size()]);		
	}


	/**
	 * OpenUp. Gabriel Vila. 20/10/2011.
	 * Obtengo y retorno array de lineas de factura asociadas a esta afectacion.
	 * @param trxName
	 * @return
	 */
	public MAllocationInvoice[] getInvoiceLines(String trxName) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MAllocationInvoice> list = new ArrayList<MAllocationInvoice>();
		
		try{
			sql =" SELECT " + X_UY_AllocationInvoice.COLUMNNAME_UY_AllocationInvoice_ID + 
 		  	     " FROM " + X_UY_AllocationInvoice.Table_Name + 
		  	     " WHERE " + X_UY_AllocationInvoice.COLUMNNAME_UY_Allocation_ID + "=?" +
 		  	     " ORDER BY DateDocument ";
			
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt(1, this.getUY_Allocation_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MAllocationInvoice value = new MAllocationInvoice(Env.getCtx(), rs.getInt(1), trxName);
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
		
		return list.toArray(new MAllocationInvoice[list.size()]);		
	}

	/**
	 * OpenUp. Gabriel Vila. 24/10/2011.
	 * Procesa la informacion de afectacion de documentos mientras valida que todo este consistente.
	 * @return
	 */
	private boolean processDocuments(){

		// Obtengo lineas de recibos y lineas de facturas
		MAllocationPayment[] allocPayments = this.getPaymentLines(get_TrxName());
		MAllocationInvoice[]  allocInvoices = this.getInvoiceLines(get_TrxName());
		
		// Valido recibos
		if (!this.validateAllocPayments(allocPayments)) return false;
			
		// Valido facturas
		if (!this.validateAllocInvoices(allocInvoices)) return false;
		
		BigDecimal montoInvoicesAfectar = Env.ZERO;
		
		// Matching entre recibos y facturas ordenados por fecha de documento
		for (int i=0; i < allocPayments.length; i++){

			MAllocationPayment allocPayment = allocPayments[i];

			if (allocPayment.getamtallocated().compareTo(Env.ZERO) <= 0) continue;
			
			// Guardo detalle de afectacion para el recibo
			this.savePaymentAllocationDetail(allocPayment);
			
			// Monto a afectar del recibo en moneda de afectacion 1
			BigDecimal montoReciboAfectar = allocPayment.getamtallocated();
			if (allocPayment.getC_Currency_ID() != this.getC_Currency_ID()) 
				montoReciboAfectar = montoInvoicesAfectar.divide(this.getDivideRate(), 2, RoundingMode.HALF_UP);
			
			// Recorro facturas para ir matcheando contra este recibo
			boolean finLoopFacturas = false;
			for (int j=0; (j < allocInvoices.length) && (!finLoopFacturas); j++){
				
				MAllocationInvoice allocInvoice = allocInvoices[j];
				
				if (allocInvoice.getamtallocated().compareTo(Env.ZERO) <= 0 ) continue;
				
				// Monto a afectar de la factura en moneda de afectacion 1
				BigDecimal auxMonto = allocInvoice.getamtallocated();
				if (allocInvoice.getC_Currency_ID() != this.getC_Currency_ID()) 
					auxMonto = auxMonto.divide(this.getDivideRate(), 2, RoundingMode.HALF_UP);
				
				// Si el monto afectacion de la factura es menor o igual al monto afectacion del recibo
				if(auxMonto.compareTo(montoReciboAfectar) <= 0){
					this.saveAllocationDetail(allocPayment, allocInvoice, auxMonto);
					montoReciboAfectar = montoReciboAfectar.subtract(auxMonto);
					allocInvoice.setamtallocated(Env.ZERO);
				}
				else{
					this.saveAllocationDetail(allocPayment, allocInvoice, montoReciboAfectar);
					allocInvoice.setamtallocated(auxMonto.subtract(montoReciboAfectar)); // Guardo resto a afectar para considerar en siguiente loop.
					finLoopFacturas = true;
				}
			}
		}
		return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 24/10/2011.
	 * Valido consistencia de recibos que participan en esta afectacion.
	 * @return
	 */
	private boolean validateAllocPayments(MAllocationPayment[] allocPayments){

		// Valido que tenga lineas
		if (allocPayments.length <= 0){
			this.processMsg = "La afectacion no tiene Recibos asociados.";
			return false;
		}

		// Recorro recibos
		for (int i=0; i < allocPayments.length; i++){
			
			MAllocationPayment allocPayment = allocPayments[i];
			MDocType docPay = new MDocType(getCtx(), allocPayment.getC_DocType_ID(), null);
			BigDecimal amtOpen = Env.ZERO;
			int cBParnterID = 0;
			Timestamp fechaDoc = null;
			
			// Valido bpartner, fecha de recibo, y saldo pendiente contra info de afectacion
			if (allocPayment.getC_Payment_ID() > 0){
				MPayment pay = new MPayment(getCtx(), allocPayment.getC_Payment_ID(), get_TrxName());
				amtOpen = pay.getAmtOpen();
				cBParnterID = pay.getC_BPartner_ID();
				fechaDoc = pay.getDateTrx();
			}
			else{
				MInvoice notaCredito = new MInvoice(getCtx(), allocPayment.getC_Invoice_ID(), get_TrxName());
				amtOpen = notaCredito.getAmtOpen();
				cBParnterID = notaCredito.getC_BPartner_ID();
				fechaDoc = notaCredito.getDateInvoiced();				
			}
			
			if (amtOpen.compareTo(allocPayment.getamtopen()) != 0){
				this.processMsg = "El saldo pendiente del Recibo : " + docPay.getPrintName() +
								  " - " + allocPayment.getDocumentNo() + " ha sido modificado por otro usuario." +
								  " Refresque este documento para poder continuar con el proceso.";
				return false;
			}
			
			if (cBParnterID != this.getC_BPartner_ID()){
				this.processMsg = "El socio de negocio del Recibo : " + docPay.getPrintName() +
						  " - " + allocPayment.getDocumentNo() + " no es igual al socio de negocio de la afectacion." +
						  " Elimine este documento para poder continuar con el proceso.";
				return false;
			}
			
			if (fechaDoc.after(this.getDateTrx())){
				this.processMsg = "La fecha del Recibo : " + docPay.getPrintName() +
						  " - " + allocPayment.getDocumentNo() + " es posterior a la fecha de afectacion." +
						  " Elimine este documento para poder continuar con el proceso.";
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * OpenUp. Gabriel Vila. 24/10/2011.
	 * Valido consistencia de facturas que participan en esta afectacion.
	 * @return
	 */
	private boolean validateAllocInvoices(MAllocationInvoice[] allocInvoices){

		// Valido que tenga lineas
		if (allocInvoices.length <= 0){
			this.processMsg = "La afectacion no tiene Facturas asociadas.";
			return false;
		}

		// Recorro facturas
		for (int i=0; i < allocInvoices.length; i++){
			
			MAllocationInvoice allocInvoice = allocInvoices[i];
			MDocType docPay = new MDocType(getCtx(), allocInvoice.getC_DocType_ID(), null);
			
			MInvoice invoice = new MInvoice(getCtx(), allocInvoice.getC_Invoice_ID(), get_TrxName());
			//OpenUp SBT Issue #5587 Si la factura es por cuotas hay que contemplar los montos de los vencimientos
			MInvoicePaySchedule invoiceVto = new MInvoicePaySchedule(getCtx(),allocInvoice.get_ValueAsInt("C_InvoicePaySchedule_ID"),null);
			
			if ((invoice != null) && (invoice.get_ID() > 0)){

				if (invoice.getAmtOpen().compareTo(allocInvoice.getamtopen()) != 0){
					//Controlo si tengo que controlar el monto frente a un vencimiento	
					if(invoiceVto!= null && invoiceVto.get_ID()>0){
							if(invoiceVto.getDueAmt().compareTo(allocInvoice.getamtopen()) != 0){
								this.processMsg = "El saldo pendiente de la Factura : " + docPay.getPrintName() +
										  " - " + allocInvoice.getDocumentNo() + " ha sido modificado por otro usuario." +
										  " Refresque este documento para poder continuar con el proceso.";
								return false;	
							}				
					}else{
						this.processMsg = "El saldo pendiente de la Factura : " + docPay.getPrintName() +
								  " - " + allocInvoice.getDocumentNo() + " ha sido modificado por otro usuario." +
								  " Refresque este documento para poder continuar con el proceso.";
						return false;	
					}
					
				}
				
				if (invoice.getC_BPartner_ID() != this.getC_BPartner_ID()){
					this.processMsg = "El socio de negocio de la Factura : " + docPay.getPrintName() +
							  " - " + allocInvoice.getDocumentNo() + " no es igual al socio de negocio de la afectacion." +
							  " Elimine este documento para poder continuar con el proceso.";
					return false;
				}
				
				if (invoice.getDateInvoiced().after(this.getDateTrx())){
					//Controlo si tengo que controlar la fecha frente a un vencimiento	Issue #5587
					if(invoiceVto!= null && invoiceVto.get_ID()>0){
						if(invoice.getDateInvoiced().after(this.getDateTrx())){
							this.processMsg = "La fecha de la Factura : " + docPay.getPrintName() +
									  " - " + allocInvoice.getDocumentNo() + " es posterior a la fecha de afectacion." +
									  " Elimine este documento para poder continuar con el proceso.";
							return false;
						}
					}else{
						this.processMsg = "La fecha de la Factura : " + docPay.getPrintName() +
							  " - " + allocInvoice.getDocumentNo() + " es posterior a la fecha de afectacion." +
							  " Elimine este documento para poder continuar con el proceso.";
						return false;
					}
				}
				
			}
			else{
				MTypeFact typeFact = new MTypeFact(getCtx(), allocInvoice.get_ID(), get_TrxName());
				if ((typeFact != null) && (typeFact.get_ID() > 0)){
					if (typeFact.getAmtOpen(this.getC_BPartner_ID()).compareTo(allocInvoice.getamtopen()) != 0){
						this.processMsg = "El saldo pendiente del documento : " + docPay.getPrintName() +
										  " - " + allocInvoice.getDocumentNo() + " ha sido modificado por otro usuario." +
										  " Refresque este documento para poder continuar con el proceso.";
						return false;
					}
					
					if (typeFact.getDateTrx().after(this.getDateTrx())){
						this.processMsg = "La fecha del documento : " + docPay.getPrintName() +
								  " - " + allocInvoice.getDocumentNo() + " es posterior a la fecha de afectacion." +
								  " Elimine este documento para poder continuar con el proceso.";
						return false;
					}
				}
			}
			
		}
		
		return true;
	}
	
	/**
	 * OpenUp. Gabriel Vila. 24/10/2011.
	 * Creo y guardo nuevo detalle de afectacion segun recibo y factura recibidos. 
	 * Se recibe monto de afectacion entre ambos en moneda 1.
	 * @param allocPayment
	 * @param allocInvoice
	 * @param montoAfectacion. Monto afectacion en moneda 1.
	 */
	private void saveAllocationDetail(MAllocationPayment allocPayment, MAllocationInvoice allocInvoice, BigDecimal montoAfectacion){

		// Obtengo monto en moneda afectacion 2
		BigDecimal montoAfectacionMoneda2 = Env.ZERO;
		if (this.getC_Currency2_ID() > 0){
			montoAfectacionMoneda2 = montoAfectacion.divide(this.getDivideRate(), 2, RoundingMode.HALF_UP);
		}
		
		MAllocationDetail detail = new MAllocationDetail(getCtx(), 0, get_TrxName());
		detail.setDateTrx(this.getDateTrx());
		detail.setIsSOTrx(this.isSOTrx());
		detail.setUY_Allocation_ID(this.getUY_Allocation_ID());
		detail.setUY_AllocationPayment_ID(allocPayment.getUY_AllocationPayment_ID());
		detail.setUY_AllocationInvoice_ID(allocInvoice.getUY_AllocationInvoice_ID());
		
		if (allocPayment.getC_Payment_ID() > 0)
			detail.setC_Payment_ID(allocPayment.getC_Payment_ID());
		
		if (allocPayment.getC_Invoice_ID() > 0)
			detail.setcreditnote_id(allocPayment.getC_Invoice_ID());
		
		detail.setC_Invoice_ID(allocInvoice.getC_Invoice_ID());
		detail.setc_currency_allocation_id(this.getC_Currency_ID());
		detail.setc_currency_payment_id(allocPayment.getC_Currency_ID());
		detail.setc_currency_invoice_id(allocInvoice.getC_Currency_ID());
		detail.setamtallocated(montoAfectacion);
		
		if (allocPayment.getC_Currency_ID() != this.getC_Currency_ID())
			detail.setamtpaynativeallocated(montoAfectacionMoneda2);
		else
			detail.setamtpaynativeallocated(montoAfectacion);
		
		if (allocInvoice.getC_Currency_ID() != this.getC_Currency_ID())
			detail.setamtinvnativeallocated(montoAfectacionMoneda2);
		else
			detail.setamtinvnativeallocated(montoAfectacion);
			
		detail.setDivideRate(this.getDivideRate());
		detail.setDateInvoiced(allocInvoice.getdatedocument());
		detail.setdatepayment(allocPayment.getdatedocument());
		
		detail.saveEx(get_TrxName());		
	}
	
	/**
	 * OpenUp. Gabriel Vila. 24/10/2011.
	 * Elimina recibos con monto a afectar cero.
	 */
	private void deletePaymentsAmtAllocatedZero(){
		String action = " DELETE FROM " + X_UY_AllocationPayment.Table_Name +
						" WHERE " + X_UY_AllocationPayment.COLUMNNAME_UY_Allocation_ID + "=" + this.getUY_Allocation_ID() +
						" AND " + X_UY_AllocationPayment.COLUMNNAME_amtallocated + "= 0";
		DB.executeUpdate(action, get_TrxName());
	}

	
	/**
	 * OpenUp. Gabriel Vila. 24/10/2011.
	 * Elimina facturas con monto a afectar cero.
	 */
	private void deleteInvoicesAmtAllocatedZero(){
		String action = " DELETE FROM " + X_UY_AllocationInvoice.Table_Name +
						" WHERE " + X_UY_AllocationInvoice.COLUMNNAME_UY_Allocation_ID + "=" + this.getUY_Allocation_ID() +
						" AND " + X_UY_AllocationInvoice.COLUMNNAME_amtallocated + "= 0";
		DB.executeUpdate(action, get_TrxName());
	}


	/**
	 * OpenUp. Gabriel Vila. 24/10/2011.
	 * Marca facturas de esta afectacion como procesadas.
	 */
	private void setInvoicesProcessed(){
		String action = " UPDATE " + X_UY_AllocationInvoice.Table_Name +
						" SET " + X_UY_AllocationInvoice.COLUMNNAME_Processed + "='Y' " +
						" WHERE " + X_UY_AllocationInvoice.COLUMNNAME_UY_Allocation_ID + "=" + this.getUY_Allocation_ID();
		DB.executeUpdate(action, get_TrxName());
	}
	
	
	/**
	 * OpenUp. Gabriel Vila. 24/10/2011.
	 * Marca Recibos de esta afectacion como procesadas.
	 */
	private void setPaymentsProcessed(){
		String action = " UPDATE " + X_UY_AllocationPayment.Table_Name +
						" SET " + X_UY_AllocationPayment.COLUMNNAME_Processed + "='Y' " +
						" WHERE " + X_UY_AllocationPayment.COLUMNNAME_UY_Allocation_ID + "=" + this.getUY_Allocation_ID();
		DB.executeUpdate(action, get_TrxName());
	}
	
	/**
	 * Obtiene y retorna total afectado de recibos de esta afectacion para 
	 * una determinada moneda recibida.
	 * @param cCurrencyID
	 * @return
	 */
	public BigDecimal getPaymentsTotalAllocated(int cCurrencyID){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
			sql =" SELECT COALESCE(SUM(amtallocated),0) as total " + 
 		  	     " FROM " + X_UY_AllocationPayment.Table_Name + 
		  	     " WHERE " + X_UY_AllocationPayment.COLUMNNAME_UY_Allocation_ID + " =? " +
 		  	     " AND " + X_UY_AllocationPayment.COLUMNNAME_C_Currency_ID + " =?";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.getUY_Allocation_ID());
			pstmt.setInt(2, cCurrencyID);
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = rs.getBigDecimal(1);
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
		return value;
	}
	
	/**
	 * Obtiene y retorna total afectado de recibos con anticipo de esta afectacion para 
	 * una determinada moneda recibida.
	 * @param cCurrencyID
	 * @return
	 */
	public BigDecimal getPrepaymentsAllocated(int cCurrencyID){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
			sql = "SELECT COALESCE(SUM(ap.amtallocated),0) as total" +
                  " FROM UY_AllocationPayment ap" +
                  " inner join c_payment pay on ap.c_payment_id = pay.c_payment_id" +
                  " WHERE ap.UY_Allocation_ID = ?" +
                  " AND ap.C_Currency_ID = ?" +
                  " and ap.c_payment_id is not null and pay.isprepayment='Y'";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.getUY_Allocation_ID());
			pstmt.setInt(2, cCurrencyID);
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = rs.getBigDecimal(1);
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
		return value;
	}
	
	
	/**
	 * Obtiene y retorna total afectado de facturas de esta afectacion para 
	 * una determinada moneda recibida.
	 * @param cCurrencyID
	 * @return
	 */
	public BigDecimal getInvoicesTotalAllocated(int cCurrencyID){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
			sql =" SELECT COALESCE(SUM(amtallocated),0) as total " + 
 		  	     " FROM " + X_UY_AllocationInvoice.Table_Name + 
		  	     " WHERE " + X_UY_AllocationInvoice.COLUMNNAME_UY_Allocation_ID + " =? " +
 		  	     " AND " + X_UY_AllocationInvoice.COLUMNNAME_C_Currency_ID + " =?";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.getUY_Allocation_ID());
			pstmt.setInt(2, cCurrencyID);
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = rs.getBigDecimal(1);
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
		return value;
	}
	
	/**
	 * OpenUp. Gabriel Vila. 31/10/2011.
	 * Dado un recibo deudor/proveedor, setea el tipo de documento de esta afectacion.
	 * @param isReceipt
	 */
	public void setDocTypeFromPayment(boolean isReceipt){
		
		String docBaseType = (isReceipt) ? "ALR" : "ALP";
		int cDocTypeID = MDocType.getDocType(docBaseType);
		if (cDocTypeID > 0) this.setC_DocType_ID(cDocTypeID);
		
	}

	/**
	 * OpenUp. Gabriel Vila. 01/11/2011. Issue #896.
	 * Guarda informacion de afectacion en los detalles del recibo mismo.
	 */
	private void savePaymentAllocationDetail(MAllocationPayment allocpay){
		
		// Si no es un recibo no hago nada
		if (allocpay.getC_Payment_ID() <= 0) return;
		
		MAllocDetailPayment detpay = new MAllocDetailPayment(getCtx(), 0, get_TrxName());
		detpay.setC_Payment_ID(allocpay.getC_Payment_ID());
		detpay.setUY_Allocation_ID(allocpay.getUY_Allocation_ID());
		detpay.setC_DocType_ID(this.getC_DocType_ID());
		detpay.setDocumentNo(this.getDocumentNo());
		detpay.setC_Currency_ID(allocpay.getC_Currency_ID());
		detpay.setamtallocated(allocpay.getamtallocated());
		detpay.setdateallocated(this.getDateTrx());
		detpay.setIsManual(this.isManual());
		detpay.setdocstatusallocation(this.getDocAction());
		detpay.setAD_User_ID(this.getUpdatedBy());
		detpay.setdatemodified(this.getUpdated());
		detpay.saveEx();
	}

	/**
	 * OpenUp. Gabriel Vila. 01/11/2011. Issue #896.
	 * Actualiza estado de documento de esta afectacion en los detalles de afectacion de recibos.
	 */
	private void updateStatusPaymentAllocDetail(String docStatus){
		String action = " UPDATE " + X_UY_AllocDetailPayment.Table_Name +
						" SET " + X_UY_AllocDetailPayment.COLUMNNAME_docstatusallocation + "='" + docStatus + "'" + 
						" WHERE " + X_UY_AllocDetailPayment.COLUMNNAME_UY_Allocation_ID + "=" + this.getUY_Allocation_ID();
		DB.executeUpdate(action, get_TrxName());
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
}
