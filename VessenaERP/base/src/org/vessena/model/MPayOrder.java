/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/02/2013
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MPayOrder
 * OpenUp Ltda. Issue #348 
 * Description: Ordenes de Pago
 * @author Gabriel Vila - 12/02/2013
 * @see
 */
public class MPayOrder extends X_UY_PayOrder implements DocAction {

	private static final long serialVersionUID = -4803882679297779097L;
	private String processMsg = null;
	private boolean justPrepared = false;

	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PayOrder_ID
	 * @param trxName
	 */
	public MPayOrder(Properties ctx, int UY_PayOrder_ID, String trxName) {
		super(ctx, UY_PayOrder_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPayOrder(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @param action
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
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
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
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
		
		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		BigDecimal totalAmtResguardo = Env.ZERO;
		
		// Valido facturas asociadas a las lineas de esta orden
		List<MPayOrderLine> lines = this.getLines();
		for (MPayOrderLine line: lines){
			
			MInvoice invoice = (MInvoice)line.getC_Invoice();
			if ((invoice != null) && (invoice.get_ID() > 0)) {
				// Valido factura en estado completo
				if (!invoice.getDocStatus().equalsIgnoreCase(STATUS_Completed)){
					this.processMsg = "Factura ya no esta en estado completo." + "\n" +
									  "Factura Numero : " + invoice.getDocumentNo();
					return DocAction.STATUS_Invalid;
				}
				
				// Valido saldo pendiente sin cambios
				if (invoice.getAmtOpen().compareTo(line.getamtopen()) != 0){
					this.processMsg = "Factura sufrió cambios en saldo pendiente." + "\n" +
							  		   "Factura Numero : " + invoice.getDocumentNo();
					return DocAction.STATUS_Invalid;					
				}
				
				// Calculo Resguardo en el que pueda participar esta factura.
				// El resguardo se utiliza solo una vez (solo en una orden de pago) por su total.
				// Por lo tanto si un resguardo tiene 3 facturas, se aplica entero en la primer factura
				// que aparezca en una orden de pago y luego se marca como utilizado.

				// Verifico si esta factura participa en un resguardo pendiente de afectación.
				if (invoice.getUY_Resguardo_ID() > 0){
					MResguardo resguardo = (MResguardo)invoice.getUY_Resguardo();
					if ((resguardo.getUY_PayOrder_ID() <= 0) && (resguardo.getC_Payment_ID() <= 0)){
						if (resguardo.getC_Currency_ID() == this.getC_Currency_ID()){
							// Afecto y tomo resguardo para esta orden 
							resguardo.setUY_PayOrder_ID(this.get_ID());
							resguardo.saveEx();
							
							line.setTieneResguardo(true);
							line.setUY_Resguardo_ID(resguardo.get_ID());
							line.saveEx();
							
							// Actualizo total de resguardos para esta orden de pago
							totalAmtResguardo = totalAmtResguardo.add(resguardo.getPayAmt());
							
							MPayOrderResguardo payOrderResg = new MPayOrderResguardo(getCtx(), 0, get_TrxName());
							payOrderResg.setUY_PayOrder_ID(this.get_ID());
							payOrderResg.setUY_Resguardo_ID(resguardo.get_ID());
							payOrderResg.setDateTrx(resguardo.getDateTrx());
							payOrderResg.setAmount(resguardo.getPayAmt());
							payOrderResg.saveEx();
							
						}
					}
				}
			
			}
			else{
				MTypeFact typeFact = new MTypeFact(getCtx(), line.getC_Invoice_ID(), get_TrxName());
				// Valido asiento tipe en estado completo
				if (!typeFact.getDocStatus().equalsIgnoreCase(STATUS_Completed)){
					this.processMsg = "Asiento Tipo ya no esta en estado completo." + "\n" +
									  "Numero : " + typeFact.getDocumentNo();
					return DocAction.STATUS_Invalid;
				}
				
				// Valido saldo pendiente sin cambios
				if (typeFact.getAmtOpen(line.getC_BPartner_ID()).compareTo(line.getamtopen()) != 0){
					MBPartner bp = (MBPartner)line.getC_BPartner();
					this.processMsg = "Asiento Tipo sufrió cambios en saldo pendiente." + "\n" +
							  		  "Numero : " + typeFact.getDocumentNo() + "\n" +
							  		  "Socio de Negocio : " + bp.getName();
					return DocAction.STATUS_Invalid;					
				}
			}
			
		}

		// Actualizo total resguardos y total de la orden de pago considerando los resguardos
		totalAmtResguardo = totalAmtResguardo.setScale(2, RoundingMode.HALF_UP);
		String update = " update uy_payorder set amtresguardo =" + totalAmtResguardo + ", " +
						" payamt = (payamt-" + totalAmtResguardo + ") " +
				        " where uy_payorder_id =" + this.get_ID(); 
		DB.executeUpdateEx(update, get_TrxName());
		
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


	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		//Ini OpenUp SBT 16-02-2016 Issue #5440 Permitir anulación de OP
		if (this.getC_Payment_ID()>0){
			this.processMsg = "No es posible anular la orden "+ this.getDocumentNo() +" porque está asociada en el Recibo Nº: "+this.getC_Payment().getDocumentNo();
			if (this.getUY_PayEmit_ID()>0){
				this.processMsg = this.processMsg+ " y en la Emision de Pago Nº: "+this.getUY_PayEmit().getDocumentNo();
			}	
			throw new AdempiereException(this.processMsg);
		}	
		
		if (this.getUY_PayEmit_ID()>0){
			this.processMsg = "No es posible anular la orden "+ this.getDocumentNo() +" porque está asociada a la Emision de Pago Nº: "+this.getUY_PayEmit().getDocumentNo();
			throw new AdempiereException(this.processMsg);
		}
		//FIN OpenUp SBT 16-02-2016 Issue #5440 Permitir anulación de OP

		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/02/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/***
	 * Obtiene y retorna lineas de esta orden de pago.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 12/02/2013
	 * @see
	 * @return
	 */
	public List<MPayOrderLine> getLines(){
		
		String whereClause = X_UY_PayOrderLine.COLUMNNAME_UY_PayOrder_ID + "=" + this.get_ID();
		
		List<MPayOrderLine> values = new Query(getCtx(), I_UY_PayOrderLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return values;
	}

	/**
	 * Obtiene y retorna lineas de resguardos aplicados en esta orden de pago
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Nov 19, 2015
	 * @return
	 */
	public List<MPayOrderResguardo> getResguardos(){

		String whereClause = X_UY_PayOrder_Resguardo.COLUMNNAME_UY_PayOrder_ID + "=" + this.get_ID();
		
		List<MPayOrderResguardo> values = new Query(getCtx(), I_UY_PayOrder_Resguardo.Table_Name, whereClause, get_TrxName())
		.list();
		
		return values;

	}

	/***
	 * Obtiene y retorna modelo de orden de pago segun identificador de resguardo recibido.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 30/04/2013
	 * @see
	 * @param ctx
	 * @param uyResguardoID
	 * @param trxName
	 * @return
	 */
	public static MPayOrder forResguardo(Properties ctx, int uyResguardoID, String trxName){

		String whereClause = X_UY_PayOrder_Resguardo.COLUMNNAME_UY_Resguardo_ID + "=" + uyResguardoID;
		
		MPayOrder pord = null;
		
		MPayOrderResguardo pordresg  = new Query(ctx, I_UY_PayOrder_Resguardo.Table_Name, whereClause, trxName)
		.first();
		
		if (pordresg != null){
			pord = (MPayOrder)pordresg.getUY_PayOrder();
		}
		
		return pord;
	}

	
	/***
	 * Actualiza grilla de resguardos y totales segun facturas asociadas a las lineas.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 30/04/2013
	 * @see
	 */
	public void updateResguardos() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
		
			// Elimino resguardos anteriores
			String action = " delete from uy_payorder_resguardo where uy_payorder_id=" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
			// Obtengo resguardos segun facturas
			sql = " select distinct uy_resguardo_id " +
				  " from uy_payorderline " +
				  " where uy_payorder_id = " + this.get_ID() +
				  " and resguardo_payord_id is not null ";
				
				pstmt = DB.prepareStatement(sql.toString(), null);

				rs = pstmt.executeQuery();

				BigDecimal sumResguardos = Env.ZERO;
				boolean hayResguardo = false;
				
				while (rs.next()) {
					
					MResguardo resguardo = new MResguardo(getCtx(), rs.getInt("uy_resguardo_id"), get_TrxName());
					MPayOrderResguardo pordresg = new MPayOrderResguardo(getCtx(), 0, get_TrxName());
					pordresg.setUY_PayOrder_ID(this.get_ID());
					pordresg.setUY_Resguardo_ID(resguardo.get_ID());
					pordresg.setDateTrx(resguardo.getDateTrx());
					pordresg.setAmount(resguardo.getPayAmt());
					pordresg.saveEx();
					
					sumResguardos = sumResguardos.add(pordresg.getAmount());
					hayResguardo = true;
				}

				this.setAmtResguardo(sumResguardos);
				this.setTieneResguardo(hayResguardo);
				this.saveEx();
			
		}
		catch (Exception e){
		
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {

		this.updatePayAmt();
		
		return super.beforeSave(newRecord);
	}

	/**
	 * Al Total se le debe descontar los resguardos y los descuentos
	 * @author OpenUp SBT Issue#  4/2/2016 12:20:10
	 */
	private void updatePayAmt() {
		BigDecimal totalDisc = Env.ZERO;
		if(this.get_Value("TotalDiscounts")!=null){
			totalDisc = new BigDecimal(this.get_ValueAsString("TotalDiscounts"));
		}
		if(this.getAmtResguardo()==null){
			this.setAmtResguardo(Env.ZERO);
		}
		this.setPayAmt(this.getSubtotal().subtract(this.getAmtResguardo().add(totalDisc)));
		
	}
}
