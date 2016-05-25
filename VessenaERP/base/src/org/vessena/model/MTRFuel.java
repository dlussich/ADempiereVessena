/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MPriceList;
import org.compiere.model.MTax;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MTRFuel extends X_UY_TR_Fuel implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3886996866357382267L;

	/**
	 * @param ctx
	 * @param UY_TR_Fuel_ID
	 * @param trxName
	 */
	public MTRFuel(Properties ctx, int UY_TR_Fuel_ID, String trxName) {
		super(ctx, UY_TR_Fuel_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRFuel(Properties ctx, ResultSet rs, String trxName) {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(this.getLitros().compareTo(Env.ZERO)<=0) throw  new AdempiereException("Cantidad de litros debe ser mayor a cero");
		
		if(this.getTotalAmt().compareTo(Env.ZERO)<=0) throw  new AdempiereException("Importe total debe ser mayor a cero");		
		
		return true;
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
		
		// Actualizo fecha de transaccion
		this.setDateTrx(new Timestamp(System.currentTimeMillis()));
		
		//creo y completo documento de lectura de kilometraje, luego de pasar validacion
		this.generateDocReadKm();
		
		//si el registro es manual y CONTADO, se genera documento de Gasto de Viaje
		if(this.isManual() && this.getpaymentruletype().equalsIgnoreCase("CO")) this.generateInvoice();
		
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
	 * Metodo que genera un gasto de viaje al completarse un consumo manual al contado.
	 * OpenUp Ltda. Issue #4340
	 * @author Nicolas Sarlabos - 10/06/2015
	 * @see
	 */
	private void generateInvoice() {

		try {
	
			MDocType doc = MDocType.forValue(getCtx(), "factgastoviaje", null);
			//MTRTruck truck = (MTRTruck)this.getUY_TR_Truck();
	
			// Seteo cabezal de factura
			MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
			invoice.setIsSOTrx(false);
			invoice.setBPartner((MBPartner)this.getC_BPartner());
			invoice.set_ValueOfColumn("UY_TR_Truck_ID", this.getUY_TR_Truck_ID());
			invoice.set_ValueOfColumn("UY_TR_Driver_ID", this.getUY_TR_Driver_ID());
			invoice.setDateInvoiced(this.getDateOperation());
			invoice.setDateVendor(this.getDateOperation());
			invoice.setDateAcct(this.getDateTrx());
			invoice.setDescription("Consumo de Combustible Numero : " + this.getDocumentNo());
			invoice.setC_DocType_ID(doc.get_ID());
			invoice.setC_DocTypeTarget_ID(doc.get_ID());
			invoice.setC_Currency_ID(this.getC_Currency_ID());
			
			MCurrency cur = (MCurrency)this.getC_Currency();
			
			MPriceList list = MPriceList.getDefault(getCtx(), false, cur.getISO_Code());
			
			if(list == null) throw new AdempiereException("No se obtuvo lista de precios de compra predeterminada para la moneda " + cur.getCurSymbol());
			
			invoice.setM_PriceList_ID(list.get_ID());				
			invoice.setpaymentruletype("CO");
			invoice.setC_PaymentTerm_ID(MPaymentTerm.forValue(getCtx(), "contado", null).get_ID());
			invoice.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			invoice.setDocStatus(DocumentEngine.STATUS_Drafted);
			invoice.setDocAction(DocumentEngine.ACTION_Complete);
			invoice.setDocumentNoAux(this.getfactura());
			invoice.setDocumentNo(this.getfactura());
			invoice.saveEx();
			
			// Obtengo modelo de producto correspondiente a combustible filtrando por linea de negocio combustible
			String sql = " select m_product_id from m_product " +
					     " where uy_linea_negocio_id = (select uy_linea_negocio_id from uy_linea_negocio where value='combustible')";
			int mProductID = DB.getSQLValue(null, sql);
			if (mProductID <= 0){
				throw new AdempiereException("No se pudo obtener el producto correspondiente para combustibles.");
			}

			// Obtengo Centro de costo del vehiculo
			//MActivity cCosto = new MActivity(getCtx(), truck.getC_Activity_ID(), get_TrxName());
						
			// Impuesto exento por defecto
			MTax tax = MTax.forValue(getCtx(), "exento", null);
			
			// Seteo linea de factura por producto combustible
			MInvoiceLine line = new MInvoiceLine(getCtx(), 0, get_TrxName());
			line.setC_Invoice_ID(invoice.get_ID());
			line.setM_Product_ID(mProductID);
			line.setPriceActual(this.getTotalAmt());
			line.setQtyEntered(Env.ONE);
			line.setQtyInvoiced(Env.ONE);
			//if(cCosto!=null && cCosto.get_ID()>0) line.setC_Activity_ID_1(cCosto.get_ID()); //TODO: preguntar si va el CC
			line.setPriceEntered(this.getTotalAmt());
			line.setLineNetAmt(this.getTotalAmt());
			line.setC_Tax_ID(tax.get_ID());
			line.setTaxAmt(Env.ZERO);
			line.setLineTotalAmt(this.getTotalAmt());
			line.saveEx();	    	

			// Completo factura
			if (!invoice.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException("No se pudo generar Gasto de Viaje asociado a este consumo : \n" + invoice.getProcessMsg());
			}
			
			//asocio documento con este consumo de combustible
			this.setC_Invoice_ID(invoice.get_ID());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Metodo que genera un gasto de viaje al completarse un consumo manual al contado,
	 * OpenUp Ltda. Issue #4340
	 * @author Nicolas Sarlabos - 10/06/2015
	 * @see
	 */
	/*private void generateInvoice() {

		try {
	
			MDocType doc = MDocType.forValue(getCtx(), "factprovsinoc", null);
			MTRTruck truck = (MTRTruck)this.getUY_TR_Truck();
			
			MTRLoadFuel load = (MTRLoadFuel)this.getUY_TR_LoadFuel();

			// Seteo cabezal de factura
			MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
			invoice.setIsSOTrx(false);
			invoice.setBPartner((MBPartner)this.getC_BPartner());
			
			if(load!=null && load.get_ID()>0){
			
				invoice.setDateInvoiced(load.getDateInvoiced());
				invoice.setDueDate(load.getDueDate());				
				
			} else invoice.setDateInvoiced(this.getDateOperation());
						
			invoice.setDateVendor(this.getDateOperation());			
			invoice.setDateAcct(this.getDateTrx());
			invoice.setDescription("Consumo de Combustible Numero : " + this.getDocumentNo());
			invoice.setC_DocType_ID(doc.get_ID());
			invoice.setC_DocTypeTarget_ID(doc.get_ID());
			invoice.setC_Currency_ID(this.getC_Currency_ID());
			
			MCurrency cur = (MCurrency)this.getC_Currency();
			
			MPriceList list = MPriceList.getDefault(getCtx(), false, cur.getISO_Code());
			
			if(list == null) throw new AdempiereException("No se obtuvo lista de precios de compra predeterminada para la moneda " + cur.getCurSymbol());
			
			invoice.setM_PriceList_ID(list.get_ID());				
			invoice.setpaymentruletype("CR");
			invoice.setC_PaymentTerm_ID(MPaymentTerm.forValue(getCtx(), "credito", null).get_ID());
			invoice.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			invoice.setDocStatus(DocumentEngine.STATUS_Drafted);
			invoice.setDocAction(DocumentEngine.ACTION_Complete);
			invoice.setDocumentNoAux(this.getfactura());
			invoice.setDocumentNo(this.getfactura());
			invoice.saveEx();
			
			// Obtengo modelo de producto correspondiente a combustible filtrando por linea de negocio combustible
			String sql = " select m_product_id from m_product " +
					     " where uy_linea_negocio_id = (select uy_linea_negocio_id from uy_linea_negocio where value='combustible')";
			int mProductID = DB.getSQLValue(null, sql);
			if (mProductID <= 0){
				throw new AdempiereException("No se pudo obtener el producto correspondiente para combustibles.");
			}

			// Obtengo Centro de costo del vehiculo
			MActivity cCosto = new MActivity(getCtx(), truck.getC_Activity_ID(), get_TrxName());
						
			// Impuesto exento por defecto
			MTax tax = MTax.forValue(getCtx(), "exento", null);
			
			// Seteo linea de factura por producto combustible
			MInvoiceLine line = new MInvoiceLine(getCtx(), 0, get_TrxName());
			line.setC_Invoice_ID(invoice.get_ID());
			line.setM_Product_ID(mProductID);
			line.setPriceActual(this.getTotalAmt());
			line.setQtyEntered(Env.ONE);
			line.setQtyInvoiced(Env.ONE);
			if(cCosto!=null && cCosto.get_ID()>0) line.setC_Activity_ID_1(cCosto.get_ID());
			line.setPriceEntered(this.getTotalAmt());
			line.setLineNetAmt(this.getTotalAmt());
			line.setC_Tax_ID(tax.get_ID());
			line.setTaxAmt(Env.ZERO);
			line.setLineTotalAmt(this.getTotalAmt());
			line.saveEx();	    	

			// Completo factura
			if (!invoice.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException("No se pudo generar Factura Asociada a este Consumo : \n" + invoice.getProcessMsg());
			}
			
			// Todo bien, asocio factura con este consumo de combustible
			this.setC_Invoice_ID(invoice.get_ID());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}*/	

	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
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
	 * Crea y completa documento de lectura de kilometraje a partir de este consumo de combustible.
	 * OpenUp Ltda. Issue #1617
	 * @author Nicolas Sarlabos - 29/11/2013
	 * @see
	 * @return
	 */	
	public void generateDocReadKm(){

		try{
			
			if(this.getKilometros() > 0){
				
				MTRReadKM read = new MTRReadKM(getCtx(),0,get_TrxName());
				read.setC_DocType_ID(MDocType.forValue(this.getCtx(), "readkm", null).get_ID());
				read.setDateTrx(new Timestamp(System.currentTimeMillis()));
				read.setDateAction(this.getDateOperation());
				read.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
				read.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());
				read.setKilometros(this.getKilometros());
				read.setIsValid(false);
				read.setIsManual(false);
				read.isHubodometro = false;
				read.saveEx();
				
				if(read.validateComplete().equalsIgnoreCase("")){
					
					//completo el documento
					if (!read.processIt(DocumentEngine.ACTION_Complete)){
						throw new AdempiereException(read.getProcessMsg());
					}					
					
				} else read.deleteEx(true);				
				
			}
			
		} catch (Exception e){
			throw new AdempiereException(e);
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
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
