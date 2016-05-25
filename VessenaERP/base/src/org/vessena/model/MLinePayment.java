package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.MPayment;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;

public class MLinePayment extends X_UY_LinePayment {
	
	private static final long serialVersionUID = -5939587943205729920L;
	
	/**
	 * @param ctx
	 * @param UY_LinePayment_ID
	 * @param trxName
	 */
	public MLinePayment(Properties ctx, int UY_LinePayment_ID, String trxName) {
		
		super(ctx, UY_LinePayment_ID, trxName);

		//  New
		if (UY_LinePayment_ID == 0)
		{
			setDocStatus(MPayment.DOCSTATUS_Drafted);
			setPayAmt(Env.ZERO);
			setDateTrx (new Timestamp(System.currentTimeMillis()));
			setProcessed(false);
		}
		
		this.setRefreshWindow(true);
	}
	
	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MLinePayment (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		this.setRefreshWindow(true);
	}


	/**
	 * OpenUp. Gabriel Vila. 26/10/2011.
	 * Obtengo y retorno modelo de cabezal de recibo para esta linea.
	 * @return
	 */
	public MPayment getParent(){
		if (this.getC_Payment_ID() > 0)
			return new MPayment(getCtx(), this.getC_Payment_ID(), get_TrxName());
		else
			return null;
	}

	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		// Obtengo modelo del cabezal del recibo
		MPayment payment = new MPayment(getCtx(), this.getC_Payment_ID(), get_TrxName());
		MBankAccount ba = (MBankAccount)this.getC_BankAccount();
		
		//OpenUp. Nicolas Sarlabos. 14/08/2012. #1218
		if (ba != null && payment != null){
			if(ba.getBankAccountType()!=null){
				if(ba.getBankAccountType().equalsIgnoreCase("X")){
					ba.validateCashOpen(payment.getDateTrx());
				}
			}		
		}		
		//Fin OpenUp #1218		
		
		//OpenUp SBT 11/02/2016 Issue #5413 -->Se agrega el campo moneda en la línea
		if(newRecord){
			if(this.get_Value("C_Currency_ID")==null || this.get_Value("moneda")==null){
				this.set_Value("C_Currency_ID", ba.getC_Currency_ID());
				//SBT 15/02/2016 se debe agregar el campo para validacione de ventana
		        this.set_Value("moneda",ba.getC_Currency_ID());
			}
		}
		//OpenUp. Nicolas Sarlabos. 20/03/2013 #324
		// Setea tendertype segun tipo que tiene la forma de pago		
		if (ba != null){
			if (ba.get_ID() > 0){
				MPaymentRule payrule = (MPaymentRule)ba.getUY_PaymentRule();
				if (payrule.getpaymentruletype().equalsIgnoreCase(X_UY_PaymentRule.PAYMENTRULETYPE_CONTADO)){
					this.setTenderType(X_UY_LinePayment.TENDERTYPE_Cash);
					
					// OpenUp. Gabriel Vila. 02/05/2013. Issue #792
					// Si el banco se maneja como banco, el tendertype lo pongo como cheque ya que
					// en esta cuenta se stockean medios de pago.
					MBank bank = (MBank)ba.getC_Bank();
					if (bank.isBankHandler()){
						this.setTenderType(X_UY_LinePayment.TENDERTYPE_Check);
					}
					// Fin OpenUp. Issue #792
					
				}
				else{
					this.setTenderType(X_UY_LinePayment.TENDERTYPE_Check);
				}
			}
		}
		//Fin OpenUp.
		// Si estoy actualizando el id del medio de pago asociado, no hago nada
		if ((!newRecord) && (this.is_ValueChanged(COLUMNNAME_UY_MediosPago_ID))) return true;
		
		// Trim del numero ingresado por las dudas que metan blancos
		if (this.getDocumentNo() == null) this.setDocumentNo("");
		else this.setDocumentNo(this.getDocumentNo().trim());

		MPaymentRule payRule = (MPaymentRule)this.getUY_PaymentRule();
		
		// Valido numero de documento segun medio de pago
		if (this.getTenderType().equalsIgnoreCase(TENDERTYPE_Check)){
			if (this.getDocumentNo().equalsIgnoreCase("")){
				// Si el medio de pago de esta linea requiere numero identificador
				if (payRule != null){
					if (payRule.get_ValueAsBoolean("NeedNumber")){
						throw new AdempiereException("Debe ingresar manualmente un Numero para esta Linea de Recibo.");
					}
					else{
						int id = MSequence.getNextID(this.getAD_Client_ID(), I_C_Payment.Table_Name, null);
						this.setDocumentNo(String.valueOf(id));
					}
				}
				else{
					throw new AdempiereException("Debe ingresar manualmente un Numero para esta Linea de Recibo.");	
				}
			}
		}
		else{
			// Para cualquier otro tipo de medio de pago que no controle numero, sino pongo lo dejo en cero.
			if (this.getDocumentNo().equalsIgnoreCase("")) this.setDocumentNo("0");	
		}

		// Para recibos de pago a proveedor 
		if (!payment.isReceipt()){
			// Valido que esta linea en caso de ser ingresada manualmente solo sea efectivo o ticket alimentacion. 
			// TODO Esto hay que parametrizarlo con modelo y ventana de medios de pago.
			if ( (!this.getTenderType().equalsIgnoreCase(TENDERTYPE_Cash)) && (this.getUY_MediosPago_ID() <= 0)){
				throw new AdempiereException("No es posible crear manualmente una linea con este Medio de Pago.");
			}

			// Valido que si esta linea no fue ingresada manualmente, no modifiquen datos de la misma
			if (this.getUY_MediosPago_ID() > 0){
				if ((!newRecord) && ((this.is_ValueChanged(COLUMNNAME_DocumentNo)) 
						|| (this.is_ValueChanged(COLUMNNAME_PayAmt))
						|| (this.is_ValueChanged(COLUMNNAME_C_BankAccount_ID))
						|| (this.is_ValueChanged(COLUMNNAME_TenderType)))){
					
					throw new AdempiereException("No es posible modificar informacion de una linea con este Medio de Pago.");
				}
			}
		}
		
		// Valido importe
		if (this.getPayAmt() == null)
			throw new AdempiereException("Debe ingresar el Importe de esta Linea de Recibo.");
		
		//OpenUp. Nicolas Sarlabos. 25/04/2013. #774. Controlo monto de linea negativo segun system configurator. 
		if (!MSysConfig.getBooleanValue("UY_ALLOW_NEGATIVE_LINEPAYMENT", false, this.getAD_Client_ID())){
			if (this.getPayAmt().compareTo(Env.ZERO) <= 0)
				throw new AdempiereException("Debe ingresar el Importe de esta Linea de Recibo.");
		}
		//Fin OpenUp.

		// La fecha de la linea debe ser igual a la fecha de transaccion del cabezal
		this.setDateTrx(payment.getDateTrx());
			
		// Valido fecha de vencimiento para recibos de cobro
		//OpenUp. Nicolas Sarlabos. 04/01/2013. 
		if (!MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){
			if (payment.isReceipt())
				if (this.getDueDate().before(this.getDateTrx()))
					throw new AdempiereException("La fecha de Vencimiento debe ser igual o posterior a la fecha transaccion.");
		}
		//Fin OpenUp.
		// Actualizo subtotal del cabezal
		payment.updateSubTotal();
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (success){
			// Actualizo cabezal de afectacion con total afectado
			this.getParent().updateSubTotal();
		}
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		if (success){
			// Actualizo cabezal de afectacion con total afectado
			this.getParent().updateSubTotal();
		}
		return true;
	}

	@Override
	protected boolean beforeDelete() {
		
		//OpenUp. Nicolas Sarlabos. 07/05/2015. #4061.
		MPayment hdr = (MPayment)this.getC_Payment();//instancio cabezal de recibo
		
		if(hdr.isReceipt()){ //si es recibo de cobro
			
			if(this.getUY_MediosPago_ID()>0){
				
				MMediosPago mp = (MMediosPago)this.getUY_MediosPago();//instancio el medio de pago
				
				if(mp!=null && !mp.getestado().equalsIgnoreCase("CAR")) 
					throw new AdempiereException("Imposible borrar linea de recibo, el estado del cheque n° " + mp.getCheckNo() + " no es en CARTERA");		
				
				mp.deleteEx(true);
				
			}			
		}	
		//Fin #4061.
		return true;
	}
	
	
	 
}
