/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * @author gbrust
 *
 */
public class MTTSealLoad extends X_UY_TT_SealLoad implements DocAction {

	/**
	 * 
	 */
	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = 4053024150763723551L;

	/**
	 * @param ctx
	 * @param UY_TT_SealLoad_ID
	 * @param trxName
	 */
	public MTTSealLoad(Properties ctx, int UY_TT_SealLoad_ID, String trxName) {
		super(ctx, UY_TT_SealLoad_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTSealLoad(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
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
	protected boolean beforeSave(boolean newRecord) {

		if (newRecord){

			if ((this.getValue() == null) || (this.getValue().equalsIgnoreCase(""))){
				throw new AdempiereException("Debe ingresar numero de Precinto");
			}
			
			this.setValue(this.getValue().trim().toUpperCase());
			
			MTTSeal seal = MTTSeal.forValue(getCtx(), null, this.getValue());
			int hdrDeliveryPointFrom = new MDeliveryPoint(getCtx(), this.getUY_DeliveryPoint_ID_From(), null).get_ID(); 
			int hdrDeliveryPoint = new MDeliveryPoint(getCtx(), this.getUY_DeliveryPoint_ID(), null).get_ID();		
			
			if(seal != null){
				
				//valido que tenga los mismos puntos de ditribucion de origen y destino que el cabezal y que sea propio
				if(seal.getUY_DeliveryPoint_ID() == hdrDeliveryPointFrom && seal.getUY_DeliveryPoint_ID_To() == hdrDeliveryPoint && seal.isOwn()){
					
					if(seal.getSealStatus().equals(MTTSeal.SEALSTATUS_Cerrado)){
						//Primero seteo el bolsin como CARGA DE BOLSIN
						seal.setSealStatus(MTTSeal.SEALSTATUS_CargaBolsin);
						seal.saveEx();
						
						//seteo el valor oculto de UY_TT_Seal_ID del cabezal con el id de este precinto ya existente
						this.setUY_TT_Seal_ID(seal.get_ID());
						
					}else if (seal.getSealStatus().equals(MTTSeal.SEALSTATUS_CargaBolsin)){
						
						//solo seteo el valor oculto de UY_TT_Seal_ID del cabezal con el id de este precinto ya existente
						this.setUY_TT_Seal_ID(seal.get_ID());
						
					}else{
						throw new AdempiereException("El bolsín ingresado no esta en estado CERRADO ó EN CARGA DE BOLSÍN");
					}				
					
				}else{
				 	throw new AdempiereException("Ya existe un código de precinto creado con este valor, pero no coincide con los puntos de distribución de origen y destino acá ingresados");
				}
				
			}
			else{
				MTTSeal sealAux = new MTTSeal(this.getCtx(), 0, get_TrxName());
				sealAux.setValue(this.getValue().trim());
				sealAux.setUY_DeliveryPoint_ID(this.getUY_DeliveryPoint_ID_From());
				sealAux.setUY_DeliveryPoint_ID_To(this.getUY_DeliveryPoint_ID());
				sealAux.setSealType("TARJETA");
				sealAux.setSealCountType(X_UY_TT_Seal.SEALCOUNTTYPE_Cuentas);
				sealAux.setSealStatus(X_UY_TT_Seal.SEALSTATUS_CargaBolsin); //Acá como lo estoy creando directamente para usarlo en esta carga, ya lo creo ABIERTO
				sealAux.setIsConfirmed(true);
				sealAux.setIsOwn(true);
				sealAux.setAD_User_ID(this.getAD_User_ID());
				sealAux.setQtyBook(0);
				sealAux.setQtyCount(0);
				
				sealAux.saveEx();		
				
				this.setUY_TT_Seal_ID(sealAux.get_ID());
			}
			
		}
		else{
			MTTSeal seal = (MTTSeal)this.getUY_TT_Seal();
			if ((seal != null) && (seal.get_ID() > 0)){
				seal.setValue(this.getValue().trim());
				seal.setUY_DeliveryPoint_ID(this.getUY_DeliveryPoint_ID_From());
				seal.setUY_DeliveryPoint_ID_To(this.getUY_DeliveryPoint_ID());
				seal.saveEx();
			}
		}
		
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
		// Todo bien
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
			
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		//		Re-Check
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

		
		//OpenUp. Guillermo Brust. 29/08/2013. ISSUE #1256
		//Se setea el bolsin como PENDIENTE DE ENVIO
		
		MTTSeal seal = (MTTSeal) this.getUY_TT_Seal();
		seal.setSealStatus(X_UY_TT_Seal.SEALSTATUS_PendienteEnvio);
		seal.saveEx();		
		
		//Fin OpenUp.
		
		
		//OpenUp. Guillermo Brust. 16/09/2013. ISSUE #1256
		//Por cada Caja asociada, si no esta siendo utilizado en ninguna otra carga de bolsin, lo puedo marcar como cerrada.
		
		String sql = "";
		int result = -1;
				
		List<MTTSealLoadBox> sealLoadBoxes = this.getBoxes();
		for (MTTSealLoadBox sealLoadBox : sealLoadBoxes) {
			sql = " select sl.uy_tt_sealload_id " +
				  " from uy_tt_sealloadbox slb " +
				  " inner join uy_tt_sealload sl on slb.uy_tt_sealload_id = sl.uy_tt_sealload_id " +
			      " where sl.uy_tt_sealload_id !=" + this.get_ID() +
				  " and slb.uy_tt_box_id = " + sealLoadBox.getUY_TT_Box_ID() +
				  " and sl.docstatus ='AY'";	
			result = DB.getSQLValueEx(null, sql);
			if (result < 0){
				MTTBox box = (MTTBox) sealLoadBox.getUY_TT_Box();
				box.setBoxStatus(X_UY_TT_Box.BOXSTATUS_Cerrado);
				box.saveEx();
			}
		}
		
		//Fin OpenUp.
	
		
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
	 * Obtiene y retona lista de Cajas asociadas a esta carga de bolsin;
	 * OpenUp Ltda. Issue #1256
	 * @author Guillermo Brust - 16/09/2013
	 * @see
	 * @return
	 */
	private List<MTTSealLoadBox> getBoxes(){
		
		String whereClause = X_UY_TT_SealLoadBox.COLUMNNAME_UY_TT_SealLoad_ID + " = " + this.get_ID();
		
		List<MTTSealLoadBox> lines = new Query(this.getCtx(), I_UY_TT_SealLoadBox.Table_Name, whereClause, this.get_TrxName())
		.list();
		
		return lines;
		
	}
	

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
	protected boolean beforeDelete() {

		if (this.getDocStatus().equalsIgnoreCase(DOCSTATUS_Applied)){
			throw new AdempiereException("No es posible eliminar Recepciones Aplicadas. Debe Completarla.");
		}
		
		String sql = "";
		int result = -1;
				
		List<MTTSealLoadBox> sealLoadBoxes = this.getBoxes();
		for (MTTSealLoadBox sealLoadBox : sealLoadBoxes) {
			sql = " select sl.uy_tt_sealload_id " +
				  " from uy_tt_sealloadbox slb " +
				  " inner join uy_tt_sealload sl on slb.uy_tt_sealload_id = sl.uy_tt_sealload_id " +
			      " where sl.uy_tt_sealload_id !=" + this.get_ID() +
				  " and slb.uy_tt_box_id = " + sealLoadBox.getUY_TT_Box_ID() +
				  " and sl.docstatus ='AY'";	
			result = DB.getSQLValueEx(null, sql);
			if (result < 0){
				MTTBox box = (MTTBox) sealLoadBox.getUY_TT_Box();
				box.setBoxStatus(X_UY_TT_Box.BOXSTATUS_Cerrado);
				box.saveEx();
			}
		}
		
		return true;
	}

}
