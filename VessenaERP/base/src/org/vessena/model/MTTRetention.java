/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
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
public class MTTRetention extends X_UY_TT_Retention implements DocAction {
	
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5623454647551618978L;

	/**
	 * @param ctx
	 * @param UY_TT_Retention_ID
	 * @param trxName
	 */
	public MTTRetention(Properties ctx, int UY_TT_Retention_ID, String trxName) {
		super(ctx, UY_TT_Retention_ID, trxName);
		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTRetention(Properties ctx, ResultSet rs, String trxName) {
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
	 * @see org.compiere.process.DocAction#applyIt()
	 */
	@Override
	public boolean applyIt() {
		
		//Acá pongo todas las cajas en estado PROCESO DE RETENCION
		List<MTTRetentionBox> retentionBoxes = MTTRetentionBox.getRetentionBoxesForRetentionID(this.getCtx(), this.get_ID());
		
		for (MTTRetentionBox mttRetentionBox : retentionBoxes) {
			
			MTTBox box = (MTTBox) mttRetentionBox.getUY_TT_Box();
			box.setBoxStatus(MTTBox.BOXSTATUS_ProcesoRetenidas);
			box.saveEx();			
		}
		
		
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
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

		MDeliveryPoint dpActual = (MDeliveryPoint)this.getUY_DeliveryPoint();
				
		//OpenUp. Guillermo Brust. 02/10/2013. ISSUE #
		//Por cada Caja asociada, si no esta siendo utilizado en ninguna otro proceso de retenidas, lo puedo marcar como cerrada.
		
		String sql = "";
		int result = -1;
				
		List<MTTRetentionBox> retentionsBoxes = this.getBoxes();
		for (MTTRetentionBox retentionBox : retentionsBoxes) {
			sql = " select r.uy_tt_retention_id " +
				  " from uy_tt_retentionbox rb " +
				  " inner join uy_tt_retention r on rb.uy_tt_retention_id = r.uy_tt_retention_id " +
			      " where r.uy_tt_retention_id !=" + this.get_ID() +
				  " and rb.uy_tt_box_id = " + retentionBox.getUY_TT_Box_ID() +
				  " and r.docstatus ='AY'";	
			result = DB.getSQLValueEx(null, sql);
			if (result < 0){
				MTTBox box = (MTTBox) retentionBox.getUY_TT_Box();
				box.setBoxStatus(X_UY_TT_Box.BOXSTATUS_Cerrado);
				box.saveEx();
			}
		}
		
		//Fin OpenUp.
		//Se comenta este codigo a pedido del Usuario Vanina Grunberg por que no deben ser destruidas las tarjetas en esta instancia
		/*//OpenUp. Guillermo Brust. 03/10/2013. ISSUE #
		//Se deben destruir los las cuentas que estan marcadas para destruir	
		int destruidaID = MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "destruida").get_ID();
		
		if(this.getCardsForDestroyOfLines().size() == 0) throw new AdempiereException("No se puede completar documento sin cuentas leidas.");
				
		for (MTTCard card : this.getCardsForDestroyOfLines()) {
			
			int topeBox = MTTConfig.forValue(this.getCtx(), this.get_TrxName(), "tarjeta").getTopBox();	
			
			//La destruyo
			card.setUY_TT_CardStatus_ID(destruidaID);
			card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
			card.saveEx();
			
			//Le quito 1 a la caja contenedora de retencion
			MTTBox cajaRetencion = (MTTBox) card.getUY_TT_Box();
			cajaRetencion.updateQtyCount(1, false, topeBox, MTTBox.BOXSTATUS_ProcesoRetenidas, MTTBox.BOXSTATUS_Cerrado, false, false, 0, true, null);
			
			//Desligo la tarjeta de la caja contenedora
			DB.executeUpdateEx("UPDATE UY_TT_Card SET UY_TT_Box_ID = NULL, locatorvalue = null  WHERE UY_TT_Card_ID = " + card.get_ID(), this.get_TrxName());
			
			//Desligo la cuenta de la uy_tt_boxcard
			MTTBoxCard bcard = MTTBoxCard.forBoxIDAndCardID(this.getCtx(), this.get_TrxName(), cajaRetencion.get_ID(), card.get_ID());
			if(bcard != null){
				bcard.deleteEx(true);
			}
			
			// Tracking en cuenta
			MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
			cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
			cardTrack.setAD_User_ID(this.getAD_User_ID());
			cardTrack.setDescription("Destruida en : " + dpActual.getName());
			cardTrack.setUY_TT_Card_ID(card.get_ID());
			cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
			cardTrack.setUY_DeliveryPoint_ID_Actual(card.getUY_DeliveryPoint_ID_Actual());
			cardTrack.saveEx();
			
			// Ejecuto cierre de tracking para esta cuenta por destruccion
			card.closeTrackingDestroyed();
			
		}		
		
		//Fin OpenUp.*/
		
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
	private List<MTTRetentionBox> getBoxes(){
		
		String whereClause = X_UY_TT_RetentionBox.COLUMNNAME_UY_TT_Retention_ID + " = " + this.get_ID();
		
		List<MTTRetentionBox> lines = new Query(this.getCtx(), I_UY_TT_RetentionBox.Table_Name, whereClause, this.get_TrxName())
		.list();
		
		return lines;
		
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
	
	/**
	 * OpenUp. Guillermo Brust. 03/10/2013. ISSUE #
	 * Método que devuelve una lista de cuentas marcadas para destruir
	 * 
	 */
	public List<MTTCard> getCardsForDestroyOfLines(){
		
		String whereClause = X_UY_TT_RetentionLine.COLUMNNAME_UY_TT_Retention_ID + " = " + this.get_ID() +
				            " AND " + X_UY_TT_RetentionLine.COLUMNNAME_IsDestroy + " = 'Y'";
		
		List<MTTRetentionLine> list = new Query(this.getCtx(), I_UY_TT_RetentionLine.Table_Name, whereClause, this.get_TrxName())
		.list();
		
		List<MTTCard> cards = new ArrayList<MTTCard>();
		
		for (MTTRetentionLine rline : list) {
			cards.add((MTTCard)rline.getUY_TT_Card());
		}
			
		return cards;
	}

	@Override
	protected boolean beforeDelete() {

		if (this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Applied)){
			throw new AdempiereException("No es posible eliminar Proceso de Retenidas Aplicadas. Debe Completarla.");
		}
		
		String sql = "";
		int result = -1;
				
		List<MTTRetentionBox> retentionsBoxes = this.getBoxes();
		for (MTTRetentionBox retentionBox : retentionsBoxes) {
			sql = " select r.uy_tt_retention_id " +
				  " from uy_tt_retentionbox rb " +
				  " inner join uy_tt_retention r on rb.uy_tt_retention_id = r.uy_tt_retention_id " +
			      " where r.uy_tt_retention_id !=" + this.get_ID() +
				  " and rb.uy_tt_box_id = " + retentionBox.getUY_TT_Box_ID() +
				  " and r.docstatus ='AY'";	
			result = DB.getSQLValueEx(null, sql);
			if (result < 0){
				MTTBox box = (MTTBox) retentionBox.getUY_TT_Box();
				box.setBoxStatus(X_UY_TT_Box.BOXSTATUS_Cerrado);
				box.saveEx();
			}
		}
		
		return true;
	}

	
}
