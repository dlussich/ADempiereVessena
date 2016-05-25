/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 08/11/2012
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.model.X_C_Order;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

/**
 * org.openup.model - MRFQMacroReq
 * OpenUp Ltda. Issue #95 
 * Description: Gestion de macro solicitudes de cotizacion a proveedor.
 * @author Gabriel Vila - 08/11/2012
 * @see
 */
public class MRFQMacroReq extends X_UY_RFQ_MacroReq implements DocAction {

	private static final long serialVersionUID = -1228967882964039716L;
	private String processMsg = null;
	private boolean justPrepared = false;
	
	private MRFQMacroReqLine selectedLine = null;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RFQ_MacroReq_ID
	 * @param trxName
	 */
	public MRFQMacroReq(Properties ctx, int UY_RFQ_MacroReq_ID, String trxName) {
		super(ctx, UY_RFQ_MacroReq_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRFQMacroReq(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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

		// Valido datos de la macro solicitud al momento de completar.
		if (!validateMacro()){
			return DocAction.STATUS_Invalid;
		}
		
		// Genera orden de compra asociada a la cotizacion ganadora
		this.generatePO();
		
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
	 * Valido informacion de la macro solicitud.
	 * OpenUp Ltda. Issue #95 
	 * @author Gabriel Vila - 26/12/2012
	 * @see
	 * @return
	 */
	private boolean validateMacro() {
		
		try{
			// Valido que haya una cotizacion de proveedor seleccionada.
			// Primero verifico si hay una seleccionada manual, y en caso de no haber, 
			// verifico si hay una seleccionada automaticamente.
			List<MRFQMacroReqLine> lines = this.getLines();
			
			if (lines.size() <= 0){
				this.processMsg = "Macro Solicitud sin lineas";
				return false;
			}
			
			boolean selManual = false, selAuto = false;
			int contSelManual = 0, contSelAuto = 0;
			
			boolean hasManualDescription = true;
			
			for (MRFQMacroReqLine line: lines){

				if (line.isManualSelected()){
					selManual = true;
					selAuto = false;
					contSelManual++;
					this.selectedLine = line;
					if ((line.getDescription() == null) || (line.getDescription().equalsIgnoreCase(""))){
						hasManualDescription = false;
					}
				}
				
				if (line.isAutoSelected()){
					if (!selManual){
						selAuto = true;
						contSelAuto++;
						this.selectedLine = line;
					}
				}
			}
			
			// Si no tengo ninguna linea seleccionada, aviso y salgo en false
			if ((!selManual) && (!selAuto)){
				this.processMsg = "Debo seleccionar la cotizacion de proveedor ganadora";
				return false;
			}
			
			// Si tengo mas de una linea seleccionada de manera manual, aviso y salgo en false
			if (selManual){
				if (contSelManual > 1){
					this.processMsg = "Solo puede seleccionar una cotizacion de proveedor de manera manual";
					return false;
				}
			}
			
			// Si tengo mas de una linea seleccionada de manera automatica, aviso y salgo en false
			if (selAuto){
				if (contSelAuto > 1){
					this.processMsg = "Solamente debe haber una cotizacion de proveedor seleccionada de manera automatica";
					return false;
				}
			}
	
			// Si no puso motivo de seleccion manual
			if (!hasManualDescription){
				this.processMsg = "Debe indicar Motivo de Selección Manual.";
				return false;
			}
			
			return true;
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
		
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 08/11/2012
	 * @see
	 * @return
	 */
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
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
	 * @author Hp - 08/11/2012
	 * @see
	 * @return
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 08/11/2012
	 * @see
	 * @return
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 08/11/2012
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}


	/***
	 * Obtiene y retona lista de lineas de esta macro solicitud
	 * OpenUp Ltda. Issue #95 
	 * @author Gabriel Vila - 08/11/2012
	 * @see
	 * @return
	 */
	public List<MRFQMacroReqLine> getLines(){
		
		String whereClause = X_UY_RFQ_MacroReqLine.COLUMNNAME_UY_RFQ_MacroReq_ID + "=" + this.get_ID();
		
		List<MRFQMacroReqLine> lines = new Query(getCtx(), I_UY_RFQ_MacroReqLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
		
	}

	/***
	 * Setea documento por defecto para este documento.
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 12/11/2012
	 * @see
	 */
	public void setDefaultDocType() {
		
		MDocType doc = MDocType.forValue(getCtx(), "reqmacro", null);
		if (doc.get_ID() > 0) this.setC_DocType_ID(doc.get_ID());
		
	}

	/***
	 * Actualiza estado de aprobacion de cotizaciones segun estados de mis lineas.
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 14/12/2012
	 * @see
	 */
	public void updateIsApproved() {

		boolean oneNotApproved = false;
		
		try{
			
			List<MRFQMacroReqLine> lines = this.getLines();
			for (MRFQMacroReqLine line: lines){
				if (!line.isApproved()) oneNotApproved = true;
			}
			
			this.setIsApproved(!oneNotApproved);
			this.saveEx();
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Evalua cotizaciones actualmente aprobadas para esta macro.
	 * OpenUp Ltda. Issue #95 
	 * @author Gabriel Vila - 17/12/2012
	 * @see
	 */
	public void evaluateQuotes() {
		try{
			
			// Puntos para evaluacion segun politica de compra de la generacion de esta macro
			MRFQGenFilter gen = (MRFQGenFilter)this.getUY_RFQGen_Filter();
			MPOPolicy policy = (MPOPolicy)gen.getUY_POPolicy();

			// A todas las cotizaciones aprobadas se le inicializan los puntos y la seleccion automatica
			String action = " update uy_rfq_macroreqline " +
							" set totalpoints = 0, autoselected='N' " +
							" where uy_rfq_macroreq_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
			// Obtengo y recorro cotizaciones de esta macro
			List<MRFQMacroReqLine> lines = this.getLines();

			HashMap<Integer, Integer> hashMinTotals = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> hashFirstDates = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> hashBestABC = new HashMap<Integer, Integer>();

			BigDecimal minTotal = null;
			Timestamp minDeliveryDate = null;
			int minABC = 3;
			boolean existsApproved = false;

			for (MRFQMacroReqLine line: lines){
				if (line.isApproved()){
					
					existsApproved = true;
					
					BigDecimal totalAmt = line.getQuoteTotal();
					Timestamp deliveryDate = line.getQuoteDeliveryDate();
					String abc = line.getQuoteVendorABC();
					
					int calification = 3;
					if (abc.equalsIgnoreCase("C")) calification = 3;
					else if (abc.equalsIgnoreCase("B")) calification = 2;
					else if (abc.equalsIgnoreCase("A")) calification = 1;
					
					// Puntos por precio
					if ((minTotal == null) || (totalAmt.compareTo(minTotal) < 0)){
						hashMinTotals = new HashMap<Integer, Integer>();
						hashMinTotals.put(line.get_ID(), line.get_ID());
						minTotal = totalAmt;
					}
					else if (totalAmt.compareTo(minTotal) == 0){
						hashMinTotals.put(line.get_ID(), line.get_ID());
					}
					
					// Puntos por fecha de entrega
					if ((minDeliveryDate == null) || (deliveryDate.before(minDeliveryDate))){
						hashFirstDates = new HashMap<Integer, Integer>();
						hashFirstDates.put(line.get_ID(), line.get_ID());
						minDeliveryDate = deliveryDate;
					}
					else if (deliveryDate.equals(minDeliveryDate)){
						hashFirstDates.put(line.get_ID(), line.get_ID());
					}
					
					if (calification < minABC){
						hashBestABC = new HashMap<Integer, Integer>();
						hashBestABC.put(line.get_ID(), line.get_ID());
						minABC = calification;
					}
					else if (calification == minABC){
						hashBestABC.put(line.get_ID(), line.get_ID());
					}
				}
			}
			
			// Actualizo puntos de lineas por menor precio
			for (Integer idline: hashMinTotals.values()){
				MRFQMacroReqLine line = new MRFQMacroReqLine(getCtx(), idline, get_TrxName());
				line.setTotalPoints(line.getTotalPoints() + policy.getPointPrice());
				line.saveEx();
			}

			// Actualizo puntos de menor fecha de entrega
			for (Integer idline: hashFirstDates.values()){
				MRFQMacroReqLine line = new MRFQMacroReqLine(getCtx(), idline, get_TrxName());
				line.setTotalPoints(line.getTotalPoints() + policy.getPointDate());
				line.saveEx();
			}
			
			// Actualizo puntos de calificacion ABC
			for (Integer idline: hashBestABC.values()){
				MRFQMacroReqLine line = new MRFQMacroReqLine(getCtx(), idline, get_TrxName());
				line.setTotalPoints(line.getTotalPoints() + policy.getPointABC());
				line.saveEx();
			}

			// Si hay al menos una linea aprobada
			if (existsApproved){
				// Obtengo linea con mayor puntaje, en caso de haber puntajes iguales
				// me quedo con la primer linea.
				MRFQMacroReqLine selectedLine = this.getPointsWinnerLine();
				// Seteo linea ganadora
				selectedLine.setAutoSelected(true);
				selectedLine.saveEx();
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Obtiene y retorna linea ganadora en puntos de evaluacion de cotizaciones para esta
	 * macro solicitud.
	 * OpenUp Ltda. Issue #95 
	 * @author Gabriel Vila - 17/12/2012
	 * @see
	 * @return
	 */
	private MRFQMacroReqLine getPointsWinnerLine() {
		
		MRFQMacroReqLine value = null;
		int maxPoints = 0;
		
		List<MRFQMacroReqLine> lines = this.getLines();

		for (MRFQMacroReqLine line: lines){
			if (line.getTotalPoints() >= maxPoints){
				value = line;
				maxPoints = line.getTotalPoints();
			}
		}
		
		return value;
	}

	/***
	 * Genera una orden de compra en borrador, asociada a la cotizacion de proveedor seleccionada.
	 * OpenUp Ltda. Issue #198 
	 * @author Gabriel Vila - 31/01/2013
	 * @see
	 */
	private void generatePO(){
		
		try{
			
			// Obtengo modelo de cotizacion de proveedor ganadora
			MQuoteVendor quote = (MQuoteVendor)this.selectedLine.getUY_QuoteVendor();
			
			// Documento para orden de compra con cotizacion
			MDocType doc = MDocType.forValue(getCtx(), "poquote", null);
			
			// Proveedor y localizacion
			MBPartner vendor = (MBPartner)quote.getC_BPartner();
			MBPartnerLocation[] locations = vendor.getLocations(true);
			if (locations.length <= 0)
				throw new AdempiereException("El proveedor " + vendor.getName() + ", no tiene al menos una localizacion definida.");
			MBPartnerLocation location = locations[0];
			
			if (quote.get_ID() <= 0)
				throw new AdempiereException("No se pudo obtener modelo de Cotizacion de Proveedor ganadora.");
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			MOrder order = new MOrder(getCtx(), 0, get_TrxName());
			order.setIsSOTrx(false);
			order.setDocStatus(DOCSTATUS_WaitingConfirmation);
			order.setDocAction(DOCACTION_None);
			order.setProcessing(false);
			order.setProcessed(false);
			order.setC_DocTypeTarget_ID(doc.get_ID());
			order.setC_DocType_ID(doc.get_ID());
			order.setDescription(quote.getDescription());
			order.setIsApproved(false);
			order.setDateOrdered(today);
			order.setDatePromised(quote.getDateRequired());
			order.setDateAcct(order.getDateOrdered());
			order.setC_BPartner_ID(quote.getC_BPartner_ID());
			order.setC_BPartner_Location_ID(location.get_ID());
			order.setPOReference(quote.getVendorDocumentNo());
			order.setC_Currency_ID(quote.getC_Currency_ID());
			order.setUY_RFQ_MacroReq_ID(this.get_ID());
			if (quote.getC_PaymentTerm_ID() > 0)
				order.setC_PaymentTerm_ID(quote.getC_PaymentTerm_ID());
			else
				order.setC_PaymentTerm_ID(vendor.getC_PaymentTerm_ID());
			order.setInvoiceRule("D");
			order.setDeliveryRule("A");
			order.setDeliveryViaRule("P");
			order.setPriorityRule("3");
			order.setTotalLines(quote.getTotalAmt());
			order.setUY_QuoteVendor_ID(quote.get_ID());
			order.setM_Warehouse_ID(this.getM_Warehouse_ID());
			
			order.setIsApproved(false);
			order.setIsApproved1(false);
			order.setIsApproved2(false);
			order.setNeedApprove1(true);
			order.setNeedApprove2(false);
			
			order.setApprovalStatus(X_C_Order.APPROVALSTATUS_PendienteAprobacionNivel1);
			order.saveEx();
			
			// Genero las lineas de la orden de compra asociadas a las 
			// lineas de la cotizacion ganadora
			List<MQuoteVendorLine> lines = quote.getLines(get_TrxName());
			int cont = 0;
			for (MQuoteVendorLine line: lines){
				
				cont ++;
				
				MProduct prod = (MProduct)line.getM_Product();
				MTaxCategory taxcat = (MTaxCategory)prod.getC_TaxCategory();
				
				if (taxcat == null){
					throw new AdempiereException("Falta definir categoria de impuesto por defecto en el producto : " + 
								prod.getValue() + " - " + prod.getName());
				}
				
				MTax tax = taxcat.getDefaultTax();

				if (tax == null){
					throw new AdempiereException("Falta definir impuesto por defecto para la categoria de impuesto : " + 
								taxcat.getName());
				}

				
				MOrderLine orderline = new MOrderLine(getCtx(), 0, get_TrxName());
				orderline.setC_Order_ID(order.get_ID());
				orderline.setLine(cont);
				orderline.setDateOrdered(order.getDateOrdered());
				orderline.setDescription(line.getDescription());
				orderline.setDatePromised(line.getDatePromised());
				orderline.setM_Product_ID(line.getM_Product_ID());
				orderline.setC_UOM_ID(line.getC_UOM_ID());
				orderline.setQtyOrdered(line.getQtyQuote());
				orderline.setC_Currency_ID(order.getC_Currency_ID());
				orderline.setPriceLimit(line.getPrice());
				orderline.setPriceList(line.getPrice());
				orderline.setPriceEntered(line.getPrice());
				orderline.setPriceActual(line.getPrice());
				orderline.setLineNetAmt(line.getTotalAmt());
				orderline.setC_Tax_ID(tax.get_ID());
				orderline.setQtyEntered(line.getQtyQuote());
				orderline.setuy_qtyentered_original(line.getQtyQuote());
				orderline.saveEx();
				
			}

			/*
			// Tengo que hacer commit en la transaccion ya que despues para lanzar el flujo de las ordenes parciales
			// y como esto es multihilo se hace lio con la transaccion. 
			Trx.get(this.get_TrxName(), false).commit();
			order.generatePOParcials();
			*/
			
			order.generatePOParcials();
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
	}
	
}
