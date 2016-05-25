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
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * org.openup.model - MTTRetPrintVale
 * Description: Modelo para proceso de Cuentas en retencion impresion vale en el modulo de Tracking de Tarjetas.
 * OpenUp Ltda. Issue #3273 
 * @author Sylvie Bouissa - 06/01/2015
 *
 */
public class MTTRetPrintVale extends X_UY_TT_RetPrintVale implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	/**
	 * @param ctx
	 * @param UY_TT_RetPrintVale_ID
	 * @param trxName
	 */
	public MTTRetPrintVale(Properties ctx, int UY_TT_RetPrintVale_ID,
			String trxName) {
		super(ctx, UY_TT_RetPrintVale_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTRetPrintVale(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 3273
	 * @author Sylvie Bouissa - 06/01/2015
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
	 * OpenUp Ltda. Issue #3273 
	 * @author Sylvie Bouissa - 06/01/2015
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
	 * OpenUp Ltda. Issue # 3273
	 * @author Sylvie Bouissa - 06/01/2015
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
	 * OpenUp Ltda. Issue # 3273
	 * @author Gabriel Vila - 06/01/2015
	 * @see
	 * @return
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
	 * OpenUp Ltda. Issue # 3273
	 * @author Sylvie Bouissa - 06/01/2015
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {
		if(getBoxRetentionFirmaValeVale()!=null){
			if(getCantDeBoxDestino()>0){
				this.setDocAction(DocAction.ACTION_Complete);
				this.setDocStatus(DocumentEngine.STATUS_Applied);
				return true;
			}else{
				throw new AdempiereException("Agregue al menos una caja de destino");
			}
		}else{	
			throw new AdempiereException("Es necesario agregar una caja de retenidas por Impresion de Vale");
		}
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
	 * OpenUp Ltda. Issue # 3273
	 * @author Sylvie Bouissa - 06/01/2015
	 * @see
	 * @return
	 */
	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
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
		
		// Verifico si tengo al menos una cuenta escaneada, sino tengo no dejo completar.
		List<MTTRetPrintValeCard> cards = this.getScanCards();
		if ((cards == null) || (cards.size() <= 0)){
			throw new AdempiereException("Para Completar se debe escanear al menos una Cuenta.");
			//this.processMsg = "Para Completar se debe escanear al menos una Cuenta.";
			//return DocAction.STATUS_Invalid;
		}
		// Por cada Caja asociada, si no esta siendo utilizado en ninguna otra comunicacion, lo puedo marcar como cerrada.
		List<MTTRetPrintValeBox> rBoxes = this.getBoxes();
		for (MTTRetPrintValeBox rBox : rBoxes) {
			String sql = " select r.uy_tt_retprintvale_id " +
						 " from uy_tt_retprintvalebox rb " +
						 " inner join uy_tt_retprintvale r on rb.uy_tt_retprintvale_id = r.uy_tt_retprintvale_id " +
					     " where r.uy_tt_retprintvale_id !=" + this.get_ID() +
						 " and rb.uy_tt_box_id = " + rBox.getUY_TT_Box_ID() +
						 " and r.docstatus ='AY'";	
			int result = DB.getSQLValueEx(null, sql);
			if (result < 0){
				MTTBox box = (MTTBox)rBox.getUY_TT_Box();
				box.setBoxStatus(X_UY_TT_Box.BOXSTATUS_Cerrado);
				box.saveEx();
			}
		}
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

	
	/***
	 * Obtiene y retona lista de Cajas asociadas a este proceso.
	 * OpenUp Ltda. Issue #3273 
	 * @author Sylvie Bouissa - 06/01/2015
	 * @see
	 * @return
	 */
	public List<MTTRetPrintValeBox> getBoxes(){
		
		String whereClause = X_UY_TT_RetPrintValeBox.COLUMNNAME_UY_TT_RetPrintVale_ID + "=" + this.get_ID();
		
		List<MTTRetPrintValeBox> lines = new Query(getCtx(), I_UY_TT_RetPrintValeBox.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
		
	}

	/***
	 * Obtiene y retona lista de Cuentas Escaneada asociadas a este proceso.
	 * OpenUp Ltda. Issue #3273
	 * @author Sylvie Bouissa - 06/01/2015
	 * @see
	 * @return
	 */
	public List<MTTRetPrintValeCard> getScanCards(){
		
		String whereClause = X_UY_TT_RetPrintValeCard.COLUMNNAME_UY_TT_RetPrintVale_ID + "=" + this.get_ID();
		
		List<MTTRetPrintValeCard> lines = new Query(getCtx(), I_UY_TT_RetPrintValeCard.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
		
	}

	/***
	 * Verifica si una determinada caja se encuentra asociada a este proceso. 
	 * OpenUp Ltda. Issue #3273 
	 * @author Sylvie Bouissa - 07/01/2015
	 * @see
	 * @param uyTTBoxID
	 * @return
	 */
	public boolean containsBox(int uyTTBoxID) {

		String whereClause = X_UY_TT_RetPrintValeBox.COLUMNNAME_UY_TT_RetPrintVale_ID + "=" + this.get_ID() +
				" AND " + X_UY_TT_RetPrintValeBox.COLUMNNAME_UY_TT_Box_ID + "=" + uyTTBoxID;

		MTTRetPrintValeBox model = new Query(getCtx(), I_UY_TT_RetPrintValeBox.Table_Name, whereClause, get_TrxName()).first();

		return (model != null);
	}

	/***
	 * Obtiene y retorna caja para un punto de distribucion destino y un 
	 * determinado status.
	 * OpenUp Ltda. Issue #3273 
	 * @author Sylvie Bouissa - 07/01/2015
	 * @see
	 * @param boxStatus
	 * @return
	 */
	public MTTRetPrintValeBox getBoxDeliveryPoint(
			String boxStatus, int uyDeliveryPointID) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTRetPrintValeBox model = null;
		
		try{
			
			sql = " select rb.uy_tt_retprintvalebox_id " +
				  " from uy_tt_retprintvalebox rb " +
				  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
				  " where rb.uy_tt_retprintvale_id =? " +
				  " and box.boxstatus=? " +
				  " and box.iscomplete='N' " +
				  " and rb.isretained='N' " +
				  " and rb.impresionvale = 'N' " +
				  " and rb.uy_deliverypoint_id =? ";
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setString(2, boxStatus);
			pstmt.setInt(3, uyDeliveryPointID);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTRetPrintValeBox(getCtx(), rs.getInt(1), get_TrxName());
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

		return model;
	}

	/***
	 * Obtiene y retorna caja para cuentas retenidas. 
	 * determinado status.
	 * OpenUp Ltda. Issue #3273 
	 * @author SylvieBouissa -07/01/2015
	 * @se
	 * @param boxStatus
	 * @return
	 */
	public MTTRetPrintValeBox getBoxRetention(String boxStatus,
			String retainedStatus) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTRetPrintValeBox model = null;
		
		try{

			sql = " select rb.uy_tt_retprintvalebox_id " +
					  " from uy_tt_retprintvalebox rb " +
					  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
					  " where rb.uy_tt_retprintvale_id =? " +
					  " and box.boxstatus=? " +
					  " and box.iscomplete='N' " +				  
					  " and rb.isretained='Y' " +
					 // " and rb.comunicausuario = 'N' " +
					  " and rb.impresionvale = 'N' " +
					  ((retainedStatus != null) ? " and rb.RetainedStatus ='" + retainedStatus + "'" : "");					  
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setString(2, boxStatus);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTRetPrintValeBox(getCtx(), rs.getInt(1), get_TrxName());
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

		return model;
	}

	/***
	 * Obtiene y retorna caja para cuentas retenidas del proceso que esta corriendo. 
	 * determinado status.
	 * OpenUp Ltda. Issue #3273 
	 * @author SylvieBouissa -07/01/2015
	 * @se
	 * @param boxStatus
	 * @return
	 */
	public MTTRetPrintValeBox getBoxRetentionFirmaValeVale() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTRetPrintValeBox model = null;
		
		try{
	
			sql = " select rb.uy_tt_retprintvalebox_id " +
					  " from uy_tt_retprintvalebox rb " +
					  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
					  " inner join uy_tt_retprintvale rpv on rpv.uy_tt_retprintvale_id = rb.uy_tt_retprintvale_id "+
					  " where rb.uy_tt_retprintvale_id =? " +
					  " and rb.isretained='Y' " +
					 // " and rb.comunicausuario = 'N' " +
					  " and rb.impresionvale = 'Y' "+
					  "";					
					 // ((retainedStatus != null) ? " and rb.RetainedStatus ='" + retainedStatus + "'" : "");					  
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			//pstmt.setString(2, boxStatus);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTRetPrintValeBox(getCtx(), rs.getInt(1), get_TrxName());
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
	
		return model;
	}
	
	/***
	 * Obtiene y retorna cant cajas para colocar las cuentas procesadas. 
	 * determinado status.
	 * OpenUp Ltda. Issue #3273 
	 * @author SylvieBouissa -13/01/2015
	 * @se
	 * @return
	 */
	public int getCantDeBoxDestino() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		int cant = 0;
		
		try{
	
			sql = " select rb.uy_tt_retprintvalebox_id " +
					  " from uy_tt_retprintvalebox rb " +
					  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
					  " inner join uy_tt_retprintvale rpv on rpv.uy_tt_retprintvale_id = rb.uy_tt_retprintvale_id "+
					  " where rb.uy_tt_retprintvale_id =? " +
					  " and rb.isActive = 'Y' "+
					 // " and rb.isretained='N' " +
					 // " and rb.comunicausuario = 'N' " +
					  " and rb.impresionvale = 'N' "+
					  "";					
					 // ((retainedStatus != null) ? " and rb.RetainedStatus ='" + retainedStatus + "'" : "");					  
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			//pstmt.setString(2, boxStatus);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				cant++;
				while(rs.next()){
					cant++;
				}
				//model = new MTTRetPrintValeBox(getCtx(), rs.getInt(1), get_TrxName());
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
	
		return cant;
	}
	
	/**Andets de eliminar es necesario verificar si no se ha procesado al menos una cuenta, de ser asi se debe completar.
	 * @author sylvie.bouissa OpenUP 13/02/2015
	 * Issue#3273(Mejora)
	 * */
	@Override
	protected boolean beforeDelete() {
		
		//Verifico si el documento esta aplicado
		if (this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Applied)){
			
			// Verifico si tengo al menos una cuenta escaneada no dejo eliminar.
			List<MTTRetPrintValeCard> cards = this.getScanCards();
			if ((cards != null) && (cards.size() > 0)){
				throw new AdempiereException("Tiene al menos una cuenta escanada, no es posible eliminar." +
											 "Debe Completarla.");
			}
			
		}
		
		
		String sql = "";
		int result = -1;

		// Por cada Caja asociada, si no esta siendo utilizado en ninguna otro proceso de impresion de vale, lo puedo marcar como cerrada.
		List<MTTRetPrintValeBox> rBoxes = this.getBoxes();
		for (MTTRetPrintValeBox rBox : rBoxes) {
			sql = " select r.uy_tt_retprintvale_id " +
				  " from uy_tt_retprintvalebox rb " +
				  " inner join uy_tt_retprintvale r on rb.uy_tt_retprintvale_id = r.uy_tt_retprintvale_id " +
			      " where r.uy_tt_retprintvale_id !=" + this.get_ID() +
				  " and rb.uy_tt_box_id = " + rBox.getUY_TT_Box_ID() +
				  " and r.docstatus ='AY'";	
			result = DB.getSQLValueEx(null, sql);
			if (result < 0){
				MTTBox box = (MTTBox)rBox.getUY_TT_Box();
				box.setBoxStatus(X_UY_TT_Box.BOXSTATUS_Cerrado);
				box.saveEx();
			}
			//elimino la caja asociada para el proceso que estoy eliminando
			rBox.deleteEx(false);
		}
		
		return true;
	}
}
