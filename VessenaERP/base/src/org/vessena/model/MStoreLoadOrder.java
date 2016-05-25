/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Dec 21, 2015
*/
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MProduct;
import org.compiere.model.MTaxCategory;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * org.openup.model - MStoreLoadOrder
 * OpenUp Ltda. Issue #5150 
 * Description: 
 * @author gabriel - Dec 21, 2015
*/
public class MStoreLoadOrder extends X_UY_StoreLoadOrder implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = -2388249596168604202L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_StoreLoadOrder_ID
	 * @param trxName
	*/

	public MStoreLoadOrder(Properties ctx, int UY_StoreLoadOrder_ID, String trxName) {
		super(ctx, UY_StoreLoadOrder_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MStoreLoadOrder(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
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


		this.setStoreOrderStatus(X_UY_StoreLoadOrder.STOREORDERSTATUS_PENDIENTE);
		
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
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
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
	 * Carga lista de productos sugerida como lineas de este documento.
	 * OpenUp Ltda. Issue #5150
	 * @param storeStkList: Modelo de lista de productos sugeridos a cargar.
	 * @author gabriel - Dec 21, 2015
	 * @return
	 */
	public String loadProductList(MStoreStkList storeStkList){

		String message = null;
		
		try {
			
			// Obtengo productos de la lista 
			List<MStoreStkListProd> lines = storeStkList.getLines();
			
			for(MStoreStkListProd line: lines){
				MStoreLoadOrderLine ordLine = new MStoreLoadOrderLine(getCtx(), 0, get_TrxName());
				ordLine.setUY_StoreLoadOrder_ID(this.get_ID());
				ordLine.setM_Product_ID(line.getM_Product_ID());
				ordLine.setC_UOM_ID(line.getC_UOM_ID());
				ordLine.setQtyEntered(line.getQtyEntered());
				ordLine.setUPC(line.getUPC());
				ordLine.saveEx();
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return message;
	}
	
	/**
	 * Issue #5213 - Emiliano Bentancor
	 * Crea la invoice y sus lineas
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (!success){
			return success;
		}
		
		if(this.isExecuted()){
			this.generateInvoice();
			this.setStoreOrderStatus(MStoreLoadOrder.STOREORDERSTATUS_CERRADA);
		}
		
		return true;
	}
	
	private void generateInvoice(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			String qry = "select uy_storelosale_id from uy_storeloSale where uy_storeloadorder_id = " + this.get_ID();
			ps = DB.prepareStatement(qry, get_TrxName());
			rs = ps.executeQuery();
			if(rs.next()){
				int docTypeID = MDocType.forValueWOClinetID(getCtx(), "custinvoice", get_TrxName()).get_ID();
				int cBPartnerID = MBPartner.forValueWOClientID(getCtx(), "consfinal", get_TrxName()).get_ID();
				do{
					int saleId = rs.getInt(1);
					MStoreLOSale sale = new MStoreLOSale(getCtx(), saleId, get_TrxName());
					MInvoice inv = new MInvoice(getCtx(), 0, get_TrxName());
					inv.setAD_Client_ID(this.getAD_Client_ID());
					inv.setAD_Org_ID(this.getAD_Org_ID());
					inv.setIsSOTrx(true);
					inv.setDocStatus(DOCSTATUS_Drafted);
					inv.setDocAction(DocAction.ACTION_Complete);
					if(docTypeID != 0){
						inv.setC_DocTypeTarget_ID(docTypeID);
						inv.setC_DocType_ID(docTypeID);
					}
					inv.setDateInvoiced(sale.getsaledate());
					inv.setC_BPartner_ID(cBPartnerID);
					MBPartner bp = new MBPartner(getCtx(), cBPartnerID, get_TrxName());
					MBPartnerLocation[] locations = bp.getLocations(true);
					if (locations.length > 0){
						inv.setC_BPartner_Location_ID(locations[0].get_ID());
					}
					inv.setC_Currency_ID(100);
					inv.setGrandTotal(sale.getTotalAmt());
					inv.set_ValueOfColumn("ismobile", "Y");
					inv.set_ValueOfColumn("UY_StoreLOSale_ID", sale.get_ID());
					inv.setProcessed(false);
					inv.saveEx();
					
					List<MStoreLOSaleLine> lines = this.getLines(getCtx(), saleId, get_TrxName());
					for(MStoreLOSaleLine line: lines){
						
						MProduct prod = new MProduct(getCtx(), line.getM_Product_ID(), get_TrxName());
						MTaxCategory tax = new MTaxCategory(getCtx(), prod.getC_TaxCategory_ID(), get_TrxName());
						
						MInvoiceLine invLine = new MInvoiceLine(inv);
						invLine.setM_Product_ID(line.getM_Product_ID());
						invLine.setAD_Client_ID(this.getAD_Client_ID());
						invLine.setAD_Org_ID(this.getAD_Org_ID());
						invLine.setQtyEntered(line.getproductqty() );
						invLine.setQtyInvoiced(line.getproductqty());
						invLine.setPriceLimit(line.getproductpu());
						invLine.setPriceList(line.getproductpu());
						invLine.setPriceActual(line.getproductpu());
						invLine.setPriceEntered(line.getproductpu());
						invLine.setC_Tax_ID(tax.getDefaultTax().get_ID());
						invLine.saveEx();
					}
					
					List<MStoreLOPay> payLines = this.getPaymentLines(getCtx(), saleId, get_TrxName());
					String sql = "", sql2 = "";
					for(MStoreLOPay payLine: payLines){
						
						MInvoiceCashPayment invPay = new MInvoiceCashPayment(getCtx(),0,get_TrxName());
						invPay.setC_Invoice_ID(inv.get_ID());
						invPay.setAD_Client_ID(this.getAD_Client_ID());
						invPay.setAD_Org_ID(this.getAD_Org_ID());
						invPay.setTenderType("X");
						
						invPay.set_ValueOfColumn("UY_StoreLoadOrder_ID", this.get_ID());
						invPay.set_ValueOfColumn("UY_StoreLOSale_ID", sale.get_ID());
						//obtengo y asigno cuenta bancaria
//						sql = "SELECT c_bankaccount_id from c_bankaccount where isdefault='Y' and ispublic='Y' and c_currency_id=" + inv.getC_Currency_ID();
//						int currencyID = DB.getSQLValueEx(get_TrxName(), sql);
						invPay.setC_BankAccount_ID(1000121); //TODO Emi, cambiar a rate de la tienda
						
						//asigno monto total de factura
						invPay.setPayAmt(payLine.getamtusd());
						if(payLine.getpaytype().equals("Y")){
							invPay.setnrodoc(payLine.gettransno());
						}
						//obtengo y asigno forma de pago CONTADO
						sql2 = "SELECT uy_paymentrule_id FROM uy_paymentrule WHERE lower(value) like 'contado'";
						int payruleID = DB.getSQLValueEx(get_TrxName(),sql2);
						invPay.setUY_PaymentRule_ID(payruleID);

						invPay.setAmount(payLine.getamtmt());

						invPay.setDateFrom(inv.getdatetimeinvoiced());
						invPay.setCurrencyRate(new BigDecimal(1)); //TODO Emi, cambiar a rate de la tienda
						invPay.setC_Currency_ID(payLine.getC_Currency_ID());
						
						invPay.saveEx(); //guardo linea de pago contado
						
					}
					
				}while(rs.next());
			}
			
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			DB.close(rs, ps);
		}
		
	}
	
	private List<MStoreLOSaleLine> getLines(Properties ctx, int saleID, String trxName){
		
		String whereClause = X_UY_StoreLOSaleLine.COLUMNNAME_UY_StoreLOSale_ID + "=" + saleID;
		
		List<MStoreLOSaleLine> lines = new Query(ctx, I_UY_StoreLOSaleLine.Table_Name, whereClause, trxName).list();
		
		return lines;
	}
	
	private List<MStoreLOPay> getPaymentLines(Properties ctx, int saleID, String trxName){
		
		String whereClause = X_UY_StoreLOPay.COLUMNNAME_UY_StoreLOSale_ID + "=" + saleID;
		
		List<MStoreLOPay> lines = new Query(ctx, I_UY_StoreLOPay.Table_Name, whereClause, trxName).list();
		
		return lines;
	}
	
}
