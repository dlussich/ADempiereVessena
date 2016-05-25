/**
 * MConfirmOrderHdr.java
 * 13/09/2010
 */
package org.compiere.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MPPOrder;
import org.openup.model.MConfOrderBobbinProd;
import org.openup.model.MConfirmOrderBobbin;
import org.openup.model.MConfirmOrderBobbinReb;
import org.openup.model.MInOutLabel;
import org.openup.model.MOperarios;
import org.openup.model.MProdConfigLine;
import org.openup.model.MProdTransf;

/**
 * OpenUp.
 * MConfirmOrderHdr
 * Descripcion : Modelo para Header de Confirmacion de ordenes de produccion.
 * @author Gabriel Vila
 * Fecha : 13/09/2010
 */
public class MConfirmorderhdr extends X_UY_Confirmorderhdr implements DocAction {

	
	private static final long serialVersionUID = 2379074512298198525L;
	private String processMsg = null;
	private boolean justPrepared = false;

	
	/**	Invoice Lines			*/
	private MConfirmOrderline[]	m_lines;

	
	/**
	 * Constructor
	 * @param ctx
	 * @param UY_Confirmorderhdr_ID
	 * @param trxName
	 */
	public MConfirmorderhdr(Properties ctx, int UY_Confirmorderhdr_ID, String trxName) {

		super(ctx, UY_Confirmorderhdr_ID, trxName);

		if (UY_Confirmorderhdr_ID == 0)
		{
			setDocAction(ACTION_Complete);
			setDocStatus(STATUS_Drafted);
			setProcessed(false);
			setPosted (true);
			setDateTrx (new Timestamp(System.currentTimeMillis()));
			setDateAcct (getDateTrx());
		}
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MConfirmorderhdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
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
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	 // OpenUp. Nicolas Garcia. 04/08/2011. Issue #811.Se modifica metodo
	@Override
	public String completeIt() {

		//	Re-Check
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		// Before complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		
		//OpenUp. INes Fernandez. 02/06/2014. contempla caso prod. de transformacion. Issue #4125
		if(!MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID()))
		{	
			// Test period
			MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
			
			// Actualizo PP_Order
			MPPOrder order = new MPPOrder(getCtx(), this.getPP_Order_ID(), get_TrxName());
			order.setQtyDelivered(order.getQtyDelivered().add(this.getQtyDelivered()));
			order.setQtyReject(order.getQtyReject().add(this.getQtyReject()));
			order.setQtyScrap(order.getQtyScrap().add(this.getQtyScrap()));
			order.setIsApproved(true);
			order.setIsPrinted(true);
			order.setIsSelected(true);
			
			// After Complete
			String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
			if (valid != null)
			{
				this.processMsg = valid;
				return DocAction.STATUS_Invalid;
			}

			// Verifica si la cantidad entregada es >= a la cantidad ordenada
			if(this.getQtyDelivered().compareTo(this.getQtyOrdered()) >= 0 || this.isUY_CerroConfirmacion() ){
				this.processMsg = closeOrder(order);

				if (this.processMsg != null)
					return DocAction.STATUS_Invalid;
			}
			
			// Set the definite document number after completed (if needed)
			setDefiniteDocumentNo();
			

			//Seteo la fecha de inicio de trabajo sobre la orden = a la fecha de inicio mas chica de toda sus confirmaciones
			Timestamp fecha= DB.getSQLValueTS(null, "SELECT MIN(datestart) FROM uy_confirmorderhdr WHERE docstatus in('CO') AND pp_order_id =?", order.get_ID());
			
			if(fecha==null){
				//esta es la unica confirmacion
				fecha=this.getDateStart();
			}
			order.setDateStart(fecha);	
			
			order.setDespCreateFrom(true);// Dejo orden apta para para desplegarse
			order.saveEx();			
			
		}
		else
		{					
			MProdTransf order = this.getProdTransfOrder();
			MDocType doc = new MDocType(getCtx(),order.getC_DocTypeTarget_ID(),get_TrxName());
						
			if(doc.getValue()!=null){
				
				if(!doc.getValue().equalsIgnoreCase("ordenreb")){
					
					if(this.getLinesBobbinUsed("").length <= 0) throw new AdempiereException("Debe ingresar las bobinas usadas");
					
				}	
					
				if (doc.getValue().equalsIgnoreCase("ordenimp") || doc.getValue().equalsIgnoreCase("ordenimpvar") || 
						doc.getValue().equalsIgnoreCase("ordenimptroq") || doc.getValue().equalsIgnoreCase("ordentroq")) {
					
					if(this.getLinesBobbinProd("").length <= 0) throw new AdempiereException("Debe ingresar las bobinas producidas");				
					
				}				
				
				//valido campos de cantidades
				if(!doc.getValue().equalsIgnoreCase("ordenreb") && !doc.getValue().equalsIgnoreCase("ordencorte")){					
					
					//if(this.getQtyDelivered().compareTo(Env.ZERO)<=0) throw new AdempiereException("Cantidad de metros hechos debe ser mayor a cero");
					
					//verifico valores finales correctos
					BigDecimal qtyRefile = MConfirmOrderBobbin.totalWeightRefile(getCtx(), this.get_ID(), get_TrxName());
					
					if((qtyRefile).compareTo(Env.ZERO)<=0) throw new AdempiereException("Kg de Esqueleto/Refile debe ser mayor a cero");
					
					BigDecimal qtyLost = MConfirmOrderBobbin.totalWeightLost(getCtx(), this.get_ID(), get_TrxName());
					BigDecimal totalWeightInput = MConfirmOrderBobbin.totalWeightInput(getCtx(), this.get_ID(), get_TrxName());
					BigDecimal totalWeightReturn = MConfirmOrderBobbin.totalWeightReturn(getCtx(), this.get_ID(), get_TrxName());
					BigDecimal totalWeightProd = MConfOrderBobbinProd.totalWeightProd(getCtx(), this.get_ID(), get_TrxName());
					
					//kg totales entrada = kg totales producidos + devuelto + desperdicio + refile
					BigDecimal totalWeightOutput = totalWeightProd.add(totalWeightReturn).add(qtyLost).add(qtyRefile);
					
					if(totalWeightInput.compareTo(totalWeightOutput)!=0) throw new AdempiereException("Los valores no coinciden, verifique los valores de entrada y salida");					
					
					//verifico porcentaje de desperdicio
					MProdConfigLine conf = MProdConfigLine.forDoc(getCtx(), doc.get_ID(), get_TrxName());
					
					if(conf!=null && conf.get_ID()>0){
						
						BigDecimal maxPercentage = conf.getPercentage();
												
						BigDecimal qtyLostMax = totalWeightInput.multiply(maxPercentage.divide(Env.ONEHUNDRED, RoundingMode.HALF_UP));
						
						if(qtyLost.compareTo(qtyLostMax)>0) {
							
							this.set_Value("IsTolerance", true);
							
							ADialog.warn(0,null,"Cantidad de desperdicio supera el porcentaje maximo para este tipo de orden");						
						}
						
					}					
					
				} else if (doc.getValue().equalsIgnoreCase("ordencorte")){
					
					if(this.getLinesBobbinUsed("").length <= 0) throw new AdempiereException("Debe ingresar las bobinas usadas");
					if(this.getLines().length <= 0) throw new AdempiereException("Debe ingresar las bobinas seccionadas");		
					
					BigDecimal totalWeightInput = MConfirmOrderBobbin.totalWeightInput(getCtx(), this.get_ID(), get_TrxName());
					BigDecimal totalWeightProd = MConfirmOrderline.totalWeightProd(getCtx(), this.get_ID(), get_TrxName());
					BigDecimal totalWeightLost = MConfirmOrderBobbin.totalWeightLost(getCtx(), this.get_ID(), get_TrxName());
									
					//kg totales entrada = kg totales bobinas cortadas + desperdicio
					BigDecimal totalWeightOutput = totalWeightProd.add(totalWeightLost);
					
					if(totalWeightInput.compareTo(totalWeightOutput)!=0) throw new AdempiereException("Los valores no coinciden, verifique los valores de entrada y salida");
					
					//verifico porcentaje de desperdicio
					MProdConfigLine conf = MProdConfigLine.forDoc(getCtx(), doc.get_ID(), get_TrxName());
					
					if(conf!=null && conf.get_ID()>0){
						
						BigDecimal maxPercentage = conf.getPercentage();
												
						BigDecimal qtyLostMax = totalWeightInput.multiply(maxPercentage.divide(Env.ONEHUNDRED, RoundingMode.HALF_UP));
						
						if(totalWeightLost.compareTo(qtyLostMax)>0){
							
							this.set_Value("IsTolerance", true);
							
							ADialog.warn(0,null,"Cantidad de desperdicio supera el porcentaje maximo para este tipo de orden");						
						}
						
					}
					
					//OpenUp. Nicolas Sarlabos. 04/04/2016. #5720. Guardo trazabilidad de productos de salida.
					MConfirmOrderline[] lines = this.getLines();
					
					for(MConfirmOrderline oLine : lines){
						
						BigDecimal weight = (BigDecimal)oLine.get_Value("Weight");
						
						if(weight.compareTo(Env.ZERO)<= 0) throw new AdempiereException("Debe ingresar los pesos de las bobinas seccionadas");	
						
						MInOutLabel label = new MInOutLabel(getCtx(), 0, get_TrxName());
						label.setnumero(oLine.get_ValueAsString("numero"));
						label.setM_Product_ID(oLine.getM_Product_ID());
						label.setUY_Confirmorderhdr_ID(this.get_ID());
						label.saveEx();						
						
					}				
					//Fin OpenUp.					
									
				} else if (doc.getValue().equalsIgnoreCase("ordenreb")){
					
					if(this.getLinesBobbinReb("").length <= 0) throw new AdempiereException("Debe ingresar las bobinas utilizadas");
					if(this.getLinesBobbinProd("").length <= 0) throw new AdempiereException("Debe ingresar las bobinas producidas");
					
					BigDecimal totalWeightInput = MConfirmOrderBobbinReb.totalWeightInput(getCtx(), this.get_ID(), get_TrxName());
					BigDecimal totalWeightProd = MConfOrderBobbinProd.totalWeightProd(getCtx(), this.get_ID(), get_TrxName());
					BigDecimal qtyLost = (BigDecimal)this.get_Value("UY_Desperdicio");
					
					//kg totales entrada = kg totales bobinas cortadas + desperdicio
					BigDecimal totalWeightOutput = totalWeightProd.add(qtyLost);
					
					if(totalWeightInput.compareTo(totalWeightOutput)!=0) throw new AdempiereException("Los valores no coinciden, verifique los valores de entrada y salida");
					
					//verifico porcentaje de desperdicio
					MProdConfigLine conf = MProdConfigLine.forDoc(getCtx(), doc.get_ID(), get_TrxName());
					
					if(conf!=null && conf.get_ID()>0){
						
						BigDecimal maxPercentage = conf.getPercentage();
												
						BigDecimal qtyLostMax = totalWeightInput.multiply(maxPercentage.divide(Env.ONEHUNDRED, RoundingMode.HALF_UP));
						
						if(qtyLost.compareTo(qtyLostMax)>0){
							
							this.set_Value("IsTolerance", true);
							
							ADialog.warn(0,null,"Cantidad de desperdicio supera el porcentaje maximo para este tipo de orden");						
						}
						
					}				
					
				}			
				
			}				
		
			if(!order.isMultipleOutput()){
				order.setQtyDelivered(order.getQtyDelivered().add(this.getQtyDelivered()));
				order.setQtyReject(order.getQtyReject().add(this.getQtyReject()));
				order.setQtyScrap(order.getQtyScrap().add(this.getQtyScrap()));
				order.setIsApproved(true);
				order.setIsPrinted(true);
				order.setIsSelected(true);
			}

			// After Complete
			String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
			if (valid != null)
			{
				this.processMsg = valid;
				return DocAction.STATUS_Invalid;
			}

			// Verifica si la cantidad entregada es >= a la cantidad ordenada
			if(!order.isMultipleOutput()){//TODO:check UOM
				if(this.getQtyDelivered().compareTo(this.getQtyOrdered()) >= 0 || this.isUY_CerroConfirmacion() ){
					//this.processMsg = closeOrder(order);
					//this.closeOrder(order);
					this.setUY_CerroConfirmacion(true);					

					//if (this.processMsg != null)
						//return DocAction.STATUS_Invalid;
				}
			}
			//TODO: confirmar si interesa cierre tec para multiple output
//			else{ //is MultipleOutput
//				BigDecimal totalDelivered = BigDecimal.ZERO; //kg producidos, sumatoria de los distintos productos de salida
//				MConfirmOrderline[] lines= this.getLines();
//				for (MConfirmOrderline aux : lines){
//					totalDelivered.add(aux.getQtyDelivered());
//				}
//				if(totalDelivered.compareTo(this.getQtyOrdered()) >=0 || this.isUY_CerroConfirmacion())
//					this.setUY_CerroConfirmacion(true);
//			}

			// Set the definite document number after completed (if needed)
			setDefiniteDocumentNo();			

			//Seteo la fecha de inicio de trabajo sobre la orden = a la fecha de inicio mas chica de toda sus confirmaciones
			Timestamp fecha= DB.getSQLValueTS(null, "SELECT MIN(datestart) FROM uy_confirmorderhdr WHERE docstatus in('CO') AND pp_order_id =?", order.get_ID());
			
			if(fecha==null){
				//esta es la unica confirmacion
				fecha=this.getDateStart();
			}
			order.setDateStart(fecha);	
			
			//order.setDespCreateFrom(true);// Dejo orden apta para para desplegarse
			order.saveEx();
			
		}
		

		// Refresco estados
		setProcessed(true);
		setDocAction(ACTION_None);
		setDocStatus(DOCSTATUS_Completed);
		this.saveEx();
		return DocAction.STATUS_Completed;
		
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
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
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
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
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
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		//OpenUp Nicolas Garcia
		//Creo la orden porque la necesito.
		//MPPOrder ppOrder =(MPPOrder) this.getPP_Order();
		
		//OpenUp Nicolas Garcia 14/07/2011 Issue #774
			//Valido que exista por lo menos 1 operario en la orden
			int hayOperarios=DB.getSQLValue(null,"SELECT COALESCE((COUNT(uy_operarios_id)),0)FROM uy_operarios " +
											"WHERE UY_Confirmorderhdr_ID=?",this.getUY_Confirmorderhdr_ID());
			
			//Si no hay operarios
			if(hayOperarios<=0 && this.getDocAction().equals(DocAction.ACTION_Complete)){
				
				//OpenUp. INes Fernandez. 02/06/2014. contempla caso prod. de transformacion. Issue #4125
				//se comenta porque se decide cargarlo desde la orden
		/*		if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, this.getAD_Client_ID())){
					
					if(!ADialog.ask(0,null,null,"Confirmar Orden sin ningun operario?")){
						this.justPrepared = false;
						this.processMsg="Favor ingrese los operarios de esta confirmacion";
						return DocAction.STATUS_Invalid;
					}					
					
				} else {*/
					
					this.justPrepared = false;
					this.processMsg="Favor ingrese los operarios que trabajaron el la orden";
					return DocAction.STATUS_Invalid;			
					
				//}		
					
			}
			
			//Valido que exista por lo menos 1 parada en la orden sino muestro un cartel
			int hayParadas=DB.getSQLValue(null,"SELECT COALESCE((COUNT(uy_paradas_id)),0)FROM uy_paradas " +
											"WHERE UY_Confirmorderhdr_ID=?",this.getUY_Confirmorderhdr_ID());
			
			//Si no hay paradas
			if(!MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID()) && hayParadas<=0 && this.getDocAction().equals(DocAction.ACTION_Complete)){
				if(!ADialog.ask(0,null,null,"Confirmar Orden sin ninguna parada?")){
					this.justPrepared = false;
					this.processMsg="Favor ingrese las paradas de esta confirmacion";
					return DocAction.STATUS_Invalid;
				}
			}
		//Fin #774
		
		// Todo bien
		this.justPrepared = true;
		return DocAction.STATUS_InProgress;
	}

	@Override
	//OpenUp Nicolas Garcia Issue#657
	protected boolean beforeSave(boolean newRecord) {			
	
		//OpenUp. INes Fernandez. 02/06/2014. contempla caso prod. de transformacion. Issue #4125
		if(!MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, this.getAD_Client_ID())){
			
			//Valido que la cantidad no sea cero
			if (this.getQtyDelivered().compareTo(Env.ZERO)==0){
				this.processMsg = "La cantidad a considerar debe ser mayor a CERO.";
				return false;
			}
			
			// Instancio MPPOrder
			MPPOrder order = (MPPOrder)getPP_Order();
			// OpenUp. Nicolas Garcia. 12/08/2011. Issue #760.
			if (!validationDate(order)) {
				return false;
			}
		
			//Fin Issue #760
			
			this.updateLinesQty();
		}
		else{
			//Instancio MProdTransf
			MProdTransf order = (MProdTransf)getProdTransfOrder();
			
			if(!order.isMultipleOutput()){
				
				//Valido que la cantidad no sea cero
				/*if (this.getQtyDelivered().compareTo(Env.ZERO)==0){
					this.processMsg = "La cantidad a considerar debe ser mayor a CERO.";
					return false;
				}*/
				
				//this.updateLinesQty();
			}	
			
			
			if(!validationDate(order)) return false;
		}
		
		if(this.getQtyOrdered().compareTo(Env.ZERO)>0) this.setYield((this.getQtyDelivered().divide(this.getQtyOrdered(),2,RoundingMode.HALF_UP)).multiply(new BigDecimal(100)));
		
		
		
		//OpenUp Nicolas Sarlabos #993 29/03/2012, se setea la fecha de movimiento para que coincida siempre
		//con la fecha de inicio de confirmacion
		this.setDateTrx(this.getDateStart());
		//Fin OpenUp Nicolas Sarlabos #993 29/03/2012
		
		return true;
		//Fin Issue #4125
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
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
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
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		
		// Before reverseCorrect
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (this.processMsg != null)
			return false;

		// Check period
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(),getAD_Org_ID())) {
			this.processMsg = "@PeriodClosed@";											// TODO: Review if this messages its realy translated using messages
			return false;
		}

		// Deep reverse copy, just copy and negate quantities
		MConfirmorderhdr reversal = this.reverse();										
		if (reversal == null) {
			this.processMsg = "Could not create reversal";								// TODO: Translate
			return false;
		}

		// Complete the reversal
		try {
			if (!reversal.processIt(DocAction.ACTION_Complete)|| !reversal.getDocStatus().equals(DocAction.STATUS_Completed)) {
				this.processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Close the reversal
		reversal.closeIt();
		
		// Reset the status to reversed
		this.setProcessed(true);							// TODO: evaluate to add a isReversal property
		reversal.setDocStatus(STATUS_Reversed);
		reversal.setDocAction(ACTION_None);
		
		// Save the reversal after complete, close an reset the statos
		reversal.saveEx(get_TrxName());
		
		// Reset the status to the original object
		this.setProcessed(true);							// TODO: evaluate to add a Reversal_ID property
		this.setDocStatus(STATUS_Reversed);
		this.setDocAction(ACTION_None);
		
		// Dont need to save the original object, its part of the process 
		
		// After reverseCorrect
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (this.processMsg != null)
			return false;

		return(true);
	}
	
	/**
	 * 	Factory. Create a deep copy, including lines, negating quantities in order in reverse storage efect when completing
	 * 	@param requery
	 * 	@return lines
	 */
	public MConfirmorderhdr reverse() {

		// Create a new object
		MConfirmorderhdr reversed=new MConfirmorderhdr(getCtx(),0,get_TrxName());
		
		// Copy and reverse required properites, all propierties were keep as reference 
		reversed.setAD_Client_ID(this.getAD_Client_ID());
		reversed.setAD_Org_ID(this.getAD_Org_ID());
		reversed.setAD_User_ID(this.getAD_User_ID());
		reversed.setAD_Workflow_ID(this.getAD_Workflow_ID());
		reversed.setC_DocType_ID(this.getC_DocType_ID());
		reversed.setC_DocTypeTarget_ID(this.getC_DocTypeTarget_ID());
		reversed.setC_UOM_ID(this.getC_UOM_ID());
//		reversed.setCreated(this.getCreated());											// Don't set
//		reversed.setCreatedBy(this.getCreatedBy());										// Don't set
		reversed.setDateAcct(this.getDateAcct());
		reversed.setDateFinish(this.getDateFinish());
		reversed.setDateOrdered(this.getDateOrdered());
		reversed.setDatePromised(this.getDatePromised());
		reversed.setDateStart(this.getDateStart());
		reversed.setDateTrx(this.getDateTrx());
		reversed.setDocAction(MConfirmorderhdr.ACTION_Complete);						// Set to complete
		reversed.setDocStatus(MConfirmorderhdr.STATUS_Drafted);							// Set to draft
		reversed.setDocumentNo(this.getDocumentNo()+"^");								// Add ^
		reversed.setIsActive(this.isActive());
		reversed.setM_Product_ID(this.getM_Product_ID());
		reversed.setM_Warehouse_ID(this.getM_Warehouse_ID());
//		reversed.setPosted(this.isPosted());											// Don't set
		reversed.setPP_Order_BOM_ID(this.getPP_Order_BOM_ID());
		reversed.setPP_Order_ID(this.getPP_Order_ID());
//		reversed.setProcessed(this.isProcessed());										// Don't set
//		reversed.setProcessing(this.isProcessing());									// Don't set
		reversed.setQtyBatchs(this.getQtyBatchs().negate());							// Reversed qutantity
		reversed.setQtyBatchSize(this.getQtyBatchSize().negate());						// Reversed qutantity
		reversed.setQtyDelivered(this.getQtyDelivered().negate());						// Reversed qutantity
		reversed.setQtyEntered(this.getQtyEntered().negate());							// Reversed qutantity
		reversed.setQtyOrdered(this.getQtyOrdered().negate());							// Reversed qutantity
		reversed.setQtyReject(this.getQtyReject().negate());							// Reversed qutantity
		reversed.setQtyScrap(this.getQtyScrap().negate());								// Reversed qutantity
		reversed.setS_Resource_ID(this.getS_Resource_ID());
		reversed.setTurno(this.getTurno());
//		reversed.setUpdated(this.getUpdated());											// Don't set
		// reversed.setUpdatedBy(this.getUpdatedBy());
		// OpenUp. Nicolas Garcia. 18/08/2011. Issue #824.
		// Se crea cambpo mWarehause que sera el remplazo de uy_almacendestino
		reversed.setM_Warehouse_ID(this.getM_Warehouse_ID());
		reversed.setUY_Almacendestino_ID(this.getUY_Almacendestino_ID());
		// Fin Issue #824

//		reversed.setUY_CerroConfirmacion(this.isUY_CerroConfirmacion());				// Don't set
//		reversed.setUY_Confirmorderhdr_ID(this.getUY_Confirmorderhdr_ID());				// Don't set
		reversed.setUY_Packs(this.getUY_Packs().negate());								// Reversed qutantity
		reversed.setUY_Resource_ID(this.getUY_Resource_ID());
		reversed.setYield(this.getYield().negate());									// Reversed qutantity
		
		// Save the header
		reversed.saveEx(get_TrxName());
		
		// Reverse lines
		MConfirmOrderline[] lines=this.getLines();
		for (MConfirmOrderline line : lines) {
			
			// Create a new object
			MConfirmOrderline reversedLine=new MConfirmOrderline(getCtx(),0,get_TrxName());
			
			// Copy and reverse required properites, all propierties were keep as reference 
			reversedLine.setAD_Client_ID(line.getAD_Client_ID());
			reversedLine.setAD_Org_ID(line.getAD_Org_ID());
			reversedLine.setC_UOM_ID(line.getC_UOM_ID());
//			reversedLine.setCreated(line.getCreated());									// Don't set
//			reversedLine.setCreatedBy(line.getCreatedBy());								// Don't set
			reversedLine.setIsActive(line.isActive());
			reversedLine.setIsReversal(true);											// Set to true 
			reversedLine.setM_Product_ID(line.getM_Product_ID());
			reversedLine.setmanual(line.ismanual());
			reversedLine.setPP_Order_BOMLine_ID(line.getPP_Order_BOMLine_ID());
//			reversedLine.setProcessed(line.isProcessed());								// Don't set
			reversedLine.setQtyBOM(line.getQtyBOM().negate());							// Reversed qutantity
			reversedLine.setQtyDelivered(line.getQtyDelivered().negate());				// Reversed qutantity
			reversedLine.setQtyEntered(line.getQtyEntered().negate());					// Reversed qutantity
			reversedLine.setqtymanual(line.getqtymanual().negate());					// Reversed qutantity
			reversedLine.setQtyReject(line.getQtyReject().negate());					// Reversed qutantity
			reversedLine.setQtyRequired(line.getQtyRequired().negate());				// Reversed qutantity
			reversedLine.setQtyReserved(line.getQtyReserved().negate());				// Reversed qutantity
			reversedLine.setQtyScrap(line.getQtyScrap().negate());						// Reversed qutantity
//			reversedLine.setUpdated(line.getUpdated());									// Don't set
			// reversedLine.setUpdatedBy(line.getUpdatedBy());

			// OpenUp. Nicolas Garcia. 18/08/2011. Issue #824.
			reversed.setM_Warehouse_ID(this.getM_Warehouse_ID());
			// es cambiado por m_warehouse, se deja por las dudas y luego se
			// quitara
			reversedLine.setuy_almacenorigen(line.getuy_almacenorigen());
			// Fin Issue #824

			reversedLine.setUY_Confirmorderhdr_ID(reversed.getUY_Confirmorderhdr_ID());	// Set the the new reversed header
//			reversedLine.setUY_ConfirmOrderline_ID(line.getUY_ConfirmOrderline_ID());	// Don't set
			reversedLine.setUY_ConsumoTotal(line.getUY_ConsumoTotal());

			// Save the line
			reversedLine.saveEx(get_TrxName());			
		}
		
		// Return the header
		return(reversed);
	}
	

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
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

		if (DocAction.STATUS_Closed.equals(getDocStatus())
			|| DocAction.STATUS_Reversed.equals(getDocStatus())
			|| DocAction.STATUS_Voided.equals(getDocStatus()))
		{
			this.processMsg = "Document Closed: " + getDocStatus();
			setDocAction(DocAction.ACTION_None);
			return false;
		}

		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;
		
		this.updateOrderQty();
		
		if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, this.getAD_Client_ID())){
			
			this.set_Value("IsTolerance", false);
			
			//OpenUp. Nicolas Sarlabos. 04/04/2016. #5720.
			MProdTransf order = new MProdTransf(getCtx(),this.get_ValueAsInt("UY_ProdTransf_ID"),get_TrxName());
			MDocType doc = new MDocType(getCtx(),order.getC_DocTypeTarget_ID(),get_TrxName());
			
			if (doc.getValue()!=null && doc.getValue().equalsIgnoreCase("ordencorte"))
				DB.executeUpdateEx("delete from uy_inoutlabel where uy_confirmorderhdr_id = " + this.get_ID(), get_TrxName());				
			
		}

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
	}

	/**
	 * 	Get Invoice Lines
	 * 	@param requery
	 * 	@return lines
	 */
	public MConfirmOrderline[] getLines (boolean requery)
	{
		if (m_lines == null || m_lines.length == 0 || requery)
			m_lines = getLines(null);
		set_TrxName(m_lines, get_TrxName());
		return m_lines;
	}	//	getLines

	
	/**
	 * 	Get Lines of Invoice
	 * 	@return lines
	 */
	public MConfirmOrderline[] getLines()
	{
		return getLines(false);
	}	//	getLines

	
	/**
	 * 	Get Invoice Lines of Invoice
	 * 	@param whereClause starting with AND
	 * 	@return lines
	 */
	private MConfirmOrderline[] getLines (String whereClause)
	{
		String whereClauseFinal = "UY_Confirmorderhdr_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		List<MConfirmOrderline> list = new Query(getCtx(), MConfirmOrderline.Table_Name, whereClauseFinal, get_TrxName())
										.setParameters(new Object[]{getUY_Confirmorderhdr_ID()})
										.setOrderBy(MConfirmOrderline.COLUMNNAME_PP_Order_BOMLine_ID)
										.list();
		return list.toArray(new MConfirmOrderline[list.size()]);
	}	//	getLines
	
	public MConfirmOrderBobbin[] getLinesBobbinUsed (String whereClause)
	{
		String whereClauseFinal = "UY_Confirmorderhdr_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		List<MConfirmOrderBobbin> list = new Query(getCtx(), MConfirmOrderBobbin.Table_Name, whereClauseFinal, get_TrxName())
										.setParameters(new Object[]{getUY_Confirmorderhdr_ID()})
										.list();
		return list.toArray(new MConfirmOrderBobbin[list.size()]);
	}
	
	public MConfOrderBobbinProd[] getLinesBobbinProd (String whereClause)
	{
		String whereClauseFinal = "UY_Confirmorderhdr_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		List<MConfOrderBobbinProd> list = new Query(getCtx(), MConfOrderBobbinProd.Table_Name, whereClauseFinal, get_TrxName())
										.setParameters(new Object[]{getUY_Confirmorderhdr_ID()})
										.list();
		return list.toArray(new MConfOrderBobbinProd[list.size()]);
	}
	
	public MConfirmOrderBobbinReb[] getLinesBobbinReb (String whereClause)
	{
		String whereClauseFinal = "UY_Confirmorderhdr_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		List<MConfirmOrderBobbinReb> list = new Query(getCtx(), MConfirmOrderBobbinReb.Table_Name, whereClauseFinal, get_TrxName())
										.setParameters(new Object[]{getUY_Confirmorderhdr_ID()})
										.list();
		return list.toArray(new MConfirmOrderBobbinReb[list.size()]);
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
	 * 
	 * OpenUp.	
	 * Descripcion : Actualizar las lineas de detalle
	 * @author  Nicolas Sarlabos
	 * Fecha : 14/06/2011
	 */
	
	
	private void updateLinesQty(){
	
		// Obtengo lineas para esta confirmacion
		MConfirmOrderline[] lines = this.getLines();
		if (lines.length <= 0) return;
		
		// Actualizo cantidades de las lineas segun nueva cantidad entregada
		for (MConfirmOrderline line : lines){
			if (!line.ismanual()){
				line.setQtyEntered(this.getQtyDelivered().multiply(line.getQtyBOM()));
				line.setQtyRequired(this.getQtyDelivered().multiply(line.getQtyBOM()));
				line.setQtyDelivered(this.getQtyDelivered().multiply(line.getQtyBOM()));
				BigDecimal consumoTotal = (line.getQtyDelivered().add(line.getqtymanual()).add(line.getQtyReject()));
				line.setUY_ConsumoTotal(consumoTotal);
				line.saveEx();					
			}
		}
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion : #775 Se acomoda codigo. En est metodo se tendra que poner todo lo que sucede cuando se deba cerrar la orden
	 * actualizado OpenUp. Nicolas Garcia. 04/08/2011. Issue #811.
	 * @param order
	 * @author  Nicolas Garcia 
	 * Fecha : 12/07/2011
	 */
	private String closeOrder(MPPOrder order) {

		String salida = null;
		// OpenUp. Nicolas Garcia. 06/09/2011. Issue #825.
		this.setUY_CerroConfirmacion(true);
		//Fin Issue #825
		// OpenUp. Nicolas Garcia. 22/08/2011. Issue #824.
		// Se coloca el cierre de la orden en validatorOpenUp en el metodo "private String docValidate(MConfirmorderhdr confPPHdr, int timing)" "message = ppOrder.closeTechnical(confPPHdr);"
		//Fin Issue #824
		
		return salida;
	}
	
	
	//OpenUp. INes Fernandez. 02/06/2014. contempla caso prod. de transformacion. Issue #4125
	//agrega firma al método original
	/*private String closeOrder(MProdTransf order) {

		String salida = null;
		// OpenUp. Nicolas Garcia. 06/09/2011. Issue #825.
		this.setUY_CerroConfirmacion(true);
		//Fin Issue #825
		// OpenUp. Nicolas Garcia. 22/08/2011. Issue #824.
		// Se coloca el cierre de la orden en validatorOpenUp en el metodo "private String docValidate(MConfirmorderhdr confPPHdr, int timing)" "message = ppOrder.closeTechnical(confPPHdr);"
		//Fin Issue #824
		
		return salida;
	}*/

	/**
	 * 
	 * OpenUp.	
	 * Descripcion : Se genera este metodo para el issue #760, El es llamado desde el beforesave para controlar las fechas
	 * @param order
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 12/08/2011
	 */
	private boolean validationDate(MPPOrder order) {

		// CONTROLO FECHA DE INICIO DE LA ORDEN SEA MENOR A FECHA DE FIN.
		if (this.getDateStart().compareTo(this.getDateFinish()) >= 0) {
			ADialog.warn(0, null, null, "La fecha de inicio es mayor o igual a la fecha de fin\n Favor revise");
			return false;
		}

		// CONTROLO FECHA DE INICIO DE ESTA CONFIRMACION VS FECHA DE FIN DE LA
		// ULTIMA CONFIRMACION
		// Traigo el valor maximo
		//OpenUp Nicolas Sarlabos 10/07/2012 #1043, se modifica sql para no considerar confirmaciones anuladas
		String sql = "SELECT MAX(DateFinish) as fecha ,pp_order_id,uy_confirmorderhdr_id FROM uy_confirmorderhdr WHERE pp_order_id=? AND"
				+ " uy_confirmorderhdr_id!=? AND uy_confirmorderhdr.docstatus <> 'VO'" + " GROUP BY pp_order_id, uy_confirmorderhdr_id ORDER BY MAX(DateFinish)desc";
		//Fin OpenUp Nicolas Sarlabos 10/07/2012 #1043
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, order.get_ID());
			pstmt.setInt(2, this.get_ID());
			rs = pstmt.executeQuery();

			// Tomo solo el primer registro
			if (rs.next()) {

				Timestamp fechaFin = rs.getTimestamp("fecha");
				// Si la fecha de esta confirmacion es menor a la fechafin
				if (this.getDateStart().compareTo(fechaFin) < 0) {

					MConfirmorderhdr confAnterior = new MConfirmorderhdr(getCtx(), rs.getInt("uy_confirmorderhdr_id"), null);
					ADialog.warn(0, null, null, "La confirmación nº " + confAnterior.getDocumentNo()
							+ " tiene fecha fin mayor a la fecha de inicio colocada en esta orden");
					return false;
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return true;
	}
	//OpenUp. INes Fernandez. 02/06/2015. Issue #4125
	//adaptación del validationDate para prod de transf
	private boolean validationDate(MProdTransf order) {

		// CONTROLO FECHA DE INICIO DE LA ORDEN SEA MENOR A FECHA DE FIN.
		if (this.getDateStart().compareTo(this.getDateFinish()) >= 0) {
			ADialog.warn(0, null, null, "La fecha de inicio es mayor o igual a la fecha de fin\n Favor revise");
			return false;
		}

		// CONTROLO FECHA DE INICIO DE ESTA CONFIRMACION VS FECHA DE FIN DE LA
		// ULTIMA CONFIRMACION
		// Traigo el valor maximo
		//OpenUp Nicolas Sarlabos 10/07/2012 #1043, se modifica sql para no considerar confirmaciones anuladas
		String sql = "SELECT MAX(DateFinish) as fecha, UY_ProdTransf_ID, uy_confirmorderhdr_id FROM uy_confirmorderhdr WHERE UY_ProdTransf_ID=? AND"
				+ " uy_confirmorderhdr_id!=? AND uy_confirmorderhdr.docstatus <> 'VO'" + " GROUP BY UY_ProdTransf_ID, uy_confirmorderhdr_id ORDER BY MAX(DateFinish)desc";
		//Fin OpenUp Nicolas Sarlabos 10/07/2012 #1043
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, order.get_ID());
			pstmt.setInt(2, this.get_ID());
			rs = pstmt.executeQuery();

			// Tomo solo el primer registro
			if (rs.next()) {

				Timestamp fechaFin = rs.getTimestamp("fecha");
				// Si la fecha de esta confirmacion es menor a la fechafin
				if (this.getDateStart().compareTo(fechaFin) < 0) {

					MConfirmorderhdr confAnterior = new MConfirmorderhdr(getCtx(), rs.getInt("uy_confirmorderhdr_id"), null);
					ADialog.warn(0, null, null, "La confirmación nº " + confAnterior.getDocumentNo()
							+ " tiene fecha fin mayor a la fecha de inicio colocada en esta orden");
					return false;
				}

			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}


		return true;
	}
	

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Metodo que calcula y setea las cantidades de la orden luego de anular una confirmacion
	 * OpenUp Ltda. Issue #1043 
	 * @author Nicolas Sarlabos - 10/07/2012
	 * @see
	 */
	public void updateOrderQty(){
		
		if(!MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, this.getAD_Client_ID())){ //si no es orden de transformacion
			
			// Obtengo datos de orden de proceso asociada a esta confirmacion
			MPPOrder hdr = new MPPOrder(getCtx(), this.getPP_Order_ID(), get_TrxName());
			
			if(hdr!=null){
				//calculo cantidad reservada total a setear a la orden luego de anulacion
				BigDecimal qtyReserved = hdr.getQtyReserved().add(this.getQtyDelivered());
				//si la cantidad a setear es mayor a la ordenada entonces seteo el valor de cantidad ordenada a la cantidad reservada
				if(qtyReserved.compareTo(hdr.getQtyOrdered())>0) qtyReserved=hdr.getQtyOrdered(); 
				hdr.setQtyReserved(qtyReserved);
							
				BigDecimal qtyDelivered = hdr.getQtyDelivered().subtract(this.getQtyDelivered());
				hdr.setQtyDelivered(qtyDelivered);
							
				hdr.setDescription("");
				hdr.setUY_CerroConfirmacion(false);
				hdr.setuy_Incluidoenplan(true);

				hdr.saveEx();
			
			}			
			
		} else {
			
			if(this.get_ValueAsInt("UY_ProdTransf_ID")>0){
				
				MProdTransf order = new MProdTransf(getCtx(), this.get_ValueAsInt("UY_ProdTransf_ID"), get_TrxName());				
				
				if(order!=null && order.get_ID()>0){
					
					MDocType doc = new MDocType(getCtx(), order.getC_DocTypeTarget_ID(), get_TrxName());
					
					if(doc!=null && doc.get_ID()>0){
						
						if(doc.getValue()!=null){
							
							if(doc.getValue().equalsIgnoreCase("ordencorte")){ //TODO: VERIFICAR!!!
								
								if(order!=null){
									//calculo cantidad reservada total a setear a la orden luego de anulacion
									BigDecimal qtyReserved = order.getQtyReserved().add(this.getQtyDelivered());
									//si la cantidad a setear es mayor a la ordenada entonces seteo el valor de cantidad ordenada a la cantidad reservada
									if(qtyReserved.compareTo(order.getQtyOrdered())>0) qtyReserved=order.getQtyOrdered(); 
									order.setQtyReserved(qtyReserved);
												
									BigDecimal qtyDelivered = order.getQtyDelivered().subtract(this.getQtyDelivered());
									order.setQtyDelivered(qtyDelivered);
									order.setDescription("");
									order.setUY_CerroConfirmacion(false);
									
									order.saveEx();								
								}								
								
							} else { //si la orden NO es corte
								
								if(order!=null){
									//calculo cantidad reservada total a setear a la orden luego de anulacion
									BigDecimal qtyReserved = order.getQtyReserved().add(this.getQtyDelivered());
									//si la cantidad a setear es mayor a la ordenada entonces seteo el valor de cantidad ordenada a la cantidad reservada
									if(qtyReserved.compareTo(order.getQtyOrdered())>0) qtyReserved=order.getQtyOrdered(); 
									order.setQtyReserved(qtyReserved);
												
									BigDecimal qtyDelivered = order.getQtyDelivered().subtract(this.getQtyDelivered());
									order.setQtyDelivered(qtyDelivered);
									order.setDescription("");
									order.setUY_CerroConfirmacion(false);
									
									order.saveEx();
								
								}										
								
							}					
							
						}					
						
					} else throw new AdempiereException("Error al obtener tipo de documento");					
					
				}				
				
			}		
			
		}		
	}
	
	public MProdTransf getProdTransfOrder () throws RuntimeException
    {
		MProdTransf order = new MProdTransf(getCtx(), get_ValueAsInt("UY_ProdTransf_ID"), get_TrxName());
		return order;
	}
	
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return true if can be saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success/* || newRecord*/)
			return success;
		//carga operario asignado
		if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID()) && (newRecord || is_ValueChanged("UY_ProdTransf_ID"))){ //se crea el registro o actualiza el numero de order
			MProdTransf order = new MProdTransf(getCtx(), this.get_ValueAsInt("UY_ProdTransf_ID"), get_TrxName());
			//si existen MOperarios asociados a esta confirmacion los elimina
			String sql ="DELETE FROM uy_operarios WHERE UY_ConfirmOrderHdr_ID = " + this.get_ID();
			DB.executeUpdateEx(sql, get_TrxName());
			//crea uno con el operario designado en la orden y carga los dates
			MOperarios mop = new MOperarios(getCtx(), 0, get_TrxName());
			mop.setC_BPartner_ID(order.get_ValueAsInt("C_BPartner_ID"));
			mop.setDateStart(this.getDateStart());
			mop.setDateFinish(this.getDateFinish());
			mop.setUY_Confirmorderhdr_ID(this.get_ID());
			mop.saveEx();
		}
		//
		return true;
	}	//	afterSave
		


}
