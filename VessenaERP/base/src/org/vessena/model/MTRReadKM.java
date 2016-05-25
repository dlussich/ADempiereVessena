/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;


/**
 * @author Nicolas
 *
 */
public class MTRReadKM extends X_UY_TR_ReadKM implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;
	
	public boolean isHubodometro = true;

	/**
	 * 
	 */
	private static final long serialVersionUID = -512967364943512046L;

	/**
	 * @param ctx
	 * @param UY_TR_ReadKM_ID
	 * @param trxName
	 */
	public MTRReadKM(Properties ctx, int UY_TR_ReadKM_ID, String trxName) {
		super(ctx, UY_TR_ReadKM_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRReadKM(Properties ctx, ResultSet rs, String trxName) {
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
		
		String s = this.validateComplete();
		
		if(!s.equalsIgnoreCase("")) throw new AdempiereException(s);		
		
		// Actualizacion datos de vehiculo siempre y cuando esta lectura tenga fecha posterior o igual a la ultima lectura de este vehiculo.
		MTRTruck truck = (MTRTruck)this.getUY_TR_Truck();
		
		// Si esta lectura esta seteada como valida para disparar cambios en Kms de vehiculo, neumaticos y tareas de mantenimiento
		if(this.isValid()){

			if (truck.getDateAction() == null || this.getDateAction().after(truck.getDateAction()) || this.getDateAction().compareTo(truck.getDateAction())==0){				

				truck.setDateAction(this.getDateAction());
				truck.setQtyKm(this.getQtyKm());
				
				// Kms de entidades
				truck.updateKmsAll(this.getQtyKm());

				truck.saveEx();
			}		
			
		}		
		
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_Void);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MTRTruck truck = (MTRTruck)this.getUY_TR_Truck();
		
		if(this.getKilometros() <= 0) throw  new AdempiereException("Kilometros debe ser mayor a cero");
		
		int dif = this.getKilometros() - this.getQtyKmLast();
				
		//si esta lectura requiere contabilizar kms de hubodometro
		if(this.isHubodometro){
			
			this.setQtyKm(this.getQtyKmLast() + dif + truck.getQtyKmHubo());
			
		} else this.setQtyKm(this.getQtyKmLast() + dif);		
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if(success){
			
			if(newRecord || is_ValueChanged("UY_TR_Truck_ID") || is_ValueChanged("Kilometros")) this.loadMaintenance();			
			
		}		
		
		return true;
	}

	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		if(this.isManual()){
			
			this.voidRead();			
			
		} else throw new AdempiereException("Imposible anular, esta lectura no se ingresó manualmente");
		
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
	 * Realiza validaciones e impacta en kms de neumaticos.
	 * OpenUp Ltda. Issue #2718
	 * @author Nicolas Sarlabos - 01/09/2015
	 * @see
	 * @return
	 */	
	private void voidRead() {
		
		String sql = "";
		int readID = 0;
		MTRReadKM read = null;
		
		//obtengo ultima lectura completa posterior a la fecha de carga actual para la matricula
		sql = "select uy_tr_readkm_id from uy_tr_readkm where dateaction >= '" + this.getDateAction() + "' and uy_tr_truck_id = " + 
				this.getUY_TR_Truck_ID() + " and docstatus <> 'VO' and uy_tr_readkm_id <> " + this.get_ID() + " order by dateaction asc, updated asc";
		
		readID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(readID > 0){
			
			String status = "";
			
			read = new MTRReadKM(getCtx(),readID,get_TrxName());
			
			if(read.getDocStatus().equalsIgnoreCase("CO")){
				
				status = "Completo";
				
			} else if (read.getDocStatus().equalsIgnoreCase("DR")) status = "Borrador"; 
						
			throw new AdempiereException("Imposible anular, existe la lectura posterior n° " + read.getDocumentNo() + " en estado " + status);		
			
		}
		
		//obtengo ultima lectura completa anterior a la fecha de carga actual para la matricula
		sql = "select uy_tr_readkm_id from uy_tr_readkm where dateaction <= '" + this.getDateAction() + "' and uy_tr_truck_id = " + 
				this.getUY_TR_Truck_ID() + " and docstatus = 'CO' and uy_tr_readkm_id <> " + this.get_ID() + " order by dateaction desc, updated desc";
		
		readID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(readID > 0){
			
			read = new MTRReadKM(getCtx(),readID,get_TrxName());
			
			MTRTruck truck = (MTRTruck)this.getUY_TR_Truck();
			
			int diff = this.getQtyKm() - read.getQtyKm(); //obtengo diferencia de kms entre ambas lecturas
			
			//recorro los neumaticos del vehiculo y le resto la diferencia de kms
			List<MTRTire> tires = truck.getTires();
			
			for (MTRTire tire: tires){
				
				if (!tire.isAuxiliar()){
				
					if(tire.getEstadoActual().equalsIgnoreCase(MTRTire.ESTADOACTUAL_NUEVO)){
						
						tire.setQtyKm2(tire.getQtyKm2() - diff);
						tire.setQtyKm5(tire.getQtyKm2() + tire.getQtyKm4());
												
					} else if(tire.getEstadoActual().equalsIgnoreCase(MTRTire.ESTADOACTUAL_RECAUCHUTADO)){
						
						tire.setQtyKm4(tire.getQtyKm4() - diff);
						tire.setQtyKm5(tire.getQtyKm2() + tire.getQtyKm4());
												
					}				
					
				}	
				
				tire.setQtyKm6(read.getQtyKm());
				tire.saveEx();
				
			}				
			
			//actualizo datos del vehiculo
			DB.executeUpdate("update uy_tr_truck set qtykm = " + read.getQtyKm() + ", dateaction = '" + read.getDateAction() + 
					"' where uy_tr_truck_id = " + truck.get_ID(), get_TrxName());
			
			//actualizo tareas de mantenimiento
			DB.executeUpdate("update uy_tr_truckmaintain set qtykm = " + read.getQtyKm() + " where uy_tr_truck_id = " + truck.get_ID(), get_TrxName());			
			
		} else throw new AdempiereException("Imposible anular, no se obtuvo lectura anterior a la actual");		
		
	}

	/***
	 * Valida la lectura actual antes de completarla, comparandola con lectura anterior y posterior para la matricula y fecha.
	 * OpenUp Ltda. Issue #1617
	 * @author Nicolas Sarlabos - 29/11/2013
	 * @see
	 * @return
	 */	
	public String validateComplete(){
		
		String value = "", sql = "";
		int readID = 0;
		MTRReadKM read = null;
		
		//obtengo ultima lectura completa anterior a la fecha de carga actual para la matricula
		sql = "select uy_tr_readkm_id from uy_tr_readkm where dateaction <= '" + this.getDateAction() + "' and uy_tr_truck_id = " + 
				this.getUY_TR_Truck_ID() + " and docstatus = 'CO' and uy_tr_readkm_id <> " + this.get_ID() + " order by dateaction desc, updated desc";
		readID = DB.getSQLValueEx(get_TrxName(), sql);

		if(readID > 0){

			read = new MTRReadKM(getCtx(),readID,get_TrxName());
			
			if (read.getQtyKm() > this.getQtyKm()){ //si en la lectura anterior el kilometraje es mayor que en la actual...
				
				value = "Imposible completar el documento porque existe la lectura anterior N° " + read.getDocumentNo() + " con mayor kilometraje (" + read.getKilometros() + " Km.)";
				
				return value;					
			}		
		}
		
		//obtengo ultima lectura completa posterior a la fecha de carga actual para la matricula
		sql = "select uy_tr_readkm_id from uy_tr_readkm where dateaction > '" + this.getDateAction() + "' and uy_tr_truck_id = " + 
				this.getUY_TR_Truck_ID() + " and docstatus = 'CO' and uy_tr_readkm_id <> " + this.get_ID() + " order by dateaction asc, updated asc";
		readID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(readID > 0){

			read = new MTRReadKM(getCtx(),readID,get_TrxName());
			
			if (read.getQtyKm() < this.getQtyKm()){ //si en la lectura posterior el kilometraje es menor que en la actual...
				
				value = "Imposible completar el documento porque existe la lectura posterior N° " + read.getDocumentNo() + 
						" con menor kilometraje (" + read.getKilometros() + " Km.)";
				
				return value;					
			}		
		}		
		
		return value;
	}	
	
	/**Metodo que carga las tareas de mantenimiento expiradas del vehiculo.
	 * OpenUp Ltda. Issue #4678
	 * @author Nicolas Sarlabos - 28/08/2015
	 */
	private void loadMaintenance() {
	
		String sql = "";
		MTRReadKmMaintain line = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		
		try{
			
			DB.executeUpdateEx("delete from uy_tr_readkmmaintain where uy_tr_readkm_id = " + this.get_ID(), get_TrxName());
			
			sql = "select uy_tr_maintain_id, uy_tr_truckmaintain_id" +
				      " from uy_tr_truckmaintain" +
					  " where uy_tr_truck_id = " + this.getUY_TR_Truck_ID() +
					  " and (((qtykmnext - qtykm) <= 1000) or ((qtykmnext - " + this.getQtyKm() + ") <= 1000))" +
					  " and ad_org_id = " + this.getAD_Org_ID() +
					  " and ad_client_id = " + this.getAD_Client_ID();
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    
		    	line = new MTRReadKmMaintain(getCtx(),0,get_TrxName());
		    			    	
		    	line.setUY_TR_ReadKM_ID(this.get_ID());
		    	line.setUY_TR_Maintain_ID(rs.getInt("uy_tr_maintain_id"));
		    	line.saveEx();		    	
		    }			
			
		} catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
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
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
