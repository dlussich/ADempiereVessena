/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 08/08/2013
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
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;


/**
 * org.openup.model - MTTReceipt
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo para Recepciones de Bolsines con Precinto.
 * @author Gabriel Vila - 08/08/2013
 * @see
 */
public class MTTReceipt extends X_UY_TT_Receipt implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = 324898531572631620L;

	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_Receipt_ID
	 * @param trxName
	 */
	public MTTReceipt(Properties ctx, int UY_TT_Receipt_ID, String trxName) {
		super(ctx, UY_TT_Receipt_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTReceipt(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {
		
		// Seteo Precinto en recepcion y verifico si es valido.
		MTTSeal seal = (MTTSeal)this.getUY_TT_Seal();
		String valid = seal.setInReceipt();
		if (valid != null){
			throw new AdempiereException(valid);
		}

		// Segun condiciones muestro o no muestro grilla de plasticos
		MDeliveryPoint dpActual = (MDeliveryPoint)this.getUY_DeliveryPoint();
		MDeliveryPoint dpOrigen = new MDeliveryPoint(getCtx(), this.getUY_DeliveryPoint_ID_From(), null);
		this.setShowDetail(true);
		
		// Si estoy parado en una sucursal, no muestro grilla de plasticos
		if (!dpActual.isCentral()){
			this.setShowDetail(false);
		}
		else{
			// Si estoy parado en casa central y el origen es un correo contratado para distribuir,
			// no muestro grilla de plasticos
			if ((!dpOrigen.isOwn()) && (dpOrigen.isPostOffice())){
				this.setShowDetail(false);
			}
		}
		
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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

		// Verifico si tengo al menos una cuenta escaneada, sino tengo no dejo completar.
		List<MTTReceiptCard> cards = this.getScanCards();
		if ((cards == null) || (cards.size() <= 0)){
			this.processMsg = "Para Completar se debe escanear al menos una Cuenta.";
			return DocAction.STATUS_Invalid;
		}
	
		String sql = "";
		int result = -1;
		
		// Al completar recepcion, verifico si debo cambiar de estado de las cajas utilizadas
		// en esta recepcion. El bolsin cambia de estado a recepcionado cuando las cantidad contada iguala
		// la cantidad enviada (esto se hace en el beforesave de la MTTSeal).

		// Por cada Caja asociada, si no esta siendo utilizado en ninguna otra recepcion, lo puedo marcar como cerrada.
		List<MTTReceiptBox> rBoxes = this.getBoxes();
		for (MTTReceiptBox rBox : rBoxes) {
			sql = " select r.uy_tt_receipt_id " +
				  " from uy_tt_receiptbox rb " +
				  " inner join uy_tt_receipt r on rb.uy_tt_receipt_id = r.uy_tt_receipt_id " +
			      " where r.uy_tt_receipt_id !=" + this.get_ID() +
				  " and rb.uy_tt_box_id = " + rBox.getUY_TT_Box_ID() +
				  " and r.docstatus ='AY'";	
			result = DB.getSQLValueEx(null, sql);
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
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
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
	 * @author Hp - 08/08/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Obtiene y retorna caja para cuentas retenidas segun estado recibido
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @return
	 */
	public MTTReceiptBox getBoxRetention(String boxStatus, String retainedStatus){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTReceiptBox model = null;
		
		try{
			
			sql = " select rb.uy_tt_receiptbox_id " +
				  " from uy_tt_receiptbox rb " +
				  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
				  " where rb.uy_tt_receipt_id =? " +
				  " and box.boxstatus=? " +
				  " and box.iscomplete='N' " +				  
				  " and rb.isretained='Y' " +
				  " and rb.UnificaCardCarrier = 'N' " +
				  ((retainedStatus != null) ? " and rb.RetainedStatus ='" + retainedStatus + "'" : "");
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setString(2, boxStatus);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTReceiptBox(getCtx(), rs.getInt(1), get_TrxName());
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
	 * Obtiene y retorna caja de retencion para unificacion de cuentas.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @param boxStatus
	 * @return
	 */
	public MTTReceiptBox getBoxAccountUnification(String boxStatus){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTReceiptBox model = null;
		
		try{
			
			sql = " select rb.uy_tt_receiptbox_id " +
				  " from uy_tt_receiptbox rb " +
				  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
				  " where rb.uy_tt_receipt_id =? " +
				  " and box.boxstatus=? " +
				  " and box.iscomplete='N' " +
				  " and rb.isretained='Y' " +
				  " and rb.UnificaCardCarrier = 'Y' ";
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setString(2, boxStatus);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTReceiptBox(getCtx(), rs.getInt(1), get_TrxName());
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
	 * Obtiene y retorna caja de retencion para comunicacion a usuario.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 04/10/2013
	 * @see
	 * @param boxStatus
	 * @return
	 */
	public MTTReceiptBox getBoxAccountComunication(String boxStatus){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTReceiptBox model = null;
		
		try{
			
			sql = " select rb.uy_tt_receiptbox_id " +
				  " from uy_tt_receiptbox rb " +
				  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
				  " where rb.uy_tt_receipt_id =? " +
				  " and box.boxstatus=? " +
				  " and box.iscomplete='N' " +
				  " and rb.isretained='Y' " +
				  " and rb.ComunicaUsuario = 'Y' ";
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setString(2, boxStatus);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTReceiptBox(getCtx(), rs.getInt(1), get_TrxName());
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
	 * Obtiene y retorna caja de retencion para impresion de vale.
	 * OpenUp Ltda. Issue # 3273
	 * @author Sylvie Bouissa - 22/12/2014
	 * @see
	 * @param boxStatus // estado del la caja
	 * @return
	 */
	public MTTReceiptBox getBoxAccountComunicationVale(String boxStatus){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTReceiptBox model = null;
		
		try{
			
			sql = " select rb.uy_tt_receiptbox_id " +
				  " from uy_tt_receiptbox rb " +
				  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
				  " where rb.uy_tt_receipt_id =? " +
				  " and box.boxstatus=? " +
				  " and box.iscomplete='N' " +
				  " and rb.IsRetained='Y' " + // Retenida Y
				  " and rb.UnificaCardCarrier = 'N' "+ // Unifica Card Carrier N
				  " and rb.ComunicaUsuario = 'N' "+ // Comunica Usuario N (este valor es para chequera)
				  " and rb.ImpresionVale = 'Y' "; //Impresion Vale Y
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setString(2, boxStatus);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTReceiptBox(getCtx(), rs.getInt(1), get_TrxName());
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
	 * Obtiene y retorna caja para un punto de distribucion destino y un 
	 * determinado status.
	 * OpenUp Ltda. Issue #1173 
	 * @author Hp - 03/09/2013
	 * @see
	 * @param boxStatus
	 * @return
	 */
	public MTTReceiptBox getBoxDeliveryPoint(String boxStatus, int uyDeliveryPointID){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTReceiptBox model = null;
		
		try{
			
			sql = " select rb.uy_tt_receiptbox_id " +
				  " from uy_tt_receiptbox rb " +
				  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
				  " where rb.uy_tt_receipt_id =? " +
				  " and box.boxstatus=? " +
				  " and box.iscomplete='N' " +
				  " and rb.isretained='N' " +
				  " and rb.UnificaCardCarrier = 'N' " +
				  " and rb.uy_deliverypoint_id =? ";
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setString(2, boxStatus);
			pstmt.setInt(3, uyDeliveryPointID);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTReceiptBox(getCtx(), rs.getInt(1), get_TrxName());
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
	 * Obtiene y retona lista de Cajas asociadas a esta Recepcion.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @return
	 */
	public List<MTTReceiptBox> getBoxes(){
		
		String whereClause = X_UY_TT_ReceiptBox.COLUMNNAME_UY_TT_Receipt_ID + "=" + this.get_ID();
		
		List<MTTReceiptBox> lines = new Query(getCtx(), I_UY_TT_ReceiptBox.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
		
	}

	/***
	 * Obtiene y retona lista de Cuenta Escaneadas asociadas a esta Recepcion.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @return
	 */
	public List<MTTReceiptCard> getScanCards(){
		
		String whereClause = X_UY_TT_ReceiptCard.COLUMNNAME_UY_TT_Receipt_ID + "=" + this.get_ID();
		
		List<MTTReceiptCard> lines = new Query(getCtx(), I_UY_TT_ReceiptCard.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
		
	}
	
	/***
	 * Valido que para esta recepcion de tarjetas no exista una cuenta que no tenga al menos un plastico
	 * seleccionado. 
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 10/09/2013
	 * @see
	 * @return : Mensaje de invalidez o null si esta todo OK.
	 */
	public String validateSelectedPlastics(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String value = null;
		
		try{
			
			sql = " select uy_tt_card_id " +
				  " from vuy_tt_selectedplastic " +
				  " where uy_tt_receipt_id = ? " +
				  " and seleccionados = 0 ";
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
		
			rs = pstmt.executeQuery ();
			
			while (rs.next()){
				
				MTTCard card = new MTTCard(getCtx(), rs.getInt(1), null);

				// Solo si la cuenta es valida y entregable
				if (card.isDeliverable()){
					MTTBox box = (MTTBox)card.getUY_TT_Box();
					value = " Debe seleccionar al menos una Tarjeta para la Cuenta : " + card.getAccountNo() + "\n" +
							" que se encuentra en la Caja : " + box.getValue();
					break;
				}
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

		return value;
	}
	
	/***
	 * Obtiene y retorna caja para devolucion de cuentas a casa central segun estado recibido.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @return
	 */
	public MTTReceiptBox getBoxReturn(String boxStatus){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTReceiptBox model = null;
		
		try{
			
			sql = " select rb.uy_tt_receiptbox_id " +
				  " from uy_tt_receiptbox rb " +
				  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
				  " inner join uy_deliverypoint dp on rb.uy_deliverypoint_id = dp.uy_deliverypoint_id " +
				  " where rb.uy_tt_receipt_id =? " +
				  " and box.boxstatus=? " +
				  " and box.iscomplete='N' " +
				  " and rb.isretained='N' " +
				  " and rb.UnificaCardCarrier = 'N' " +
				  " and dp.iscentral ='Y'";
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setString(2, boxStatus);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTReceiptBox(getCtx(), rs.getInt(1), get_TrxName());
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
	 * Obtiene y retorna caja para distribucion en sucursal. 
	 * determinado status.
	 * OpenUp Ltda. Issue #1173 
	 * @author Hp - 03/09/2013
	 * @see
	 * @param boxStatus
	 * @return
	 */
	public MTTReceiptBox getBoxForDistribution(String boxStatus, int uyDeliveryPointID){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTReceiptBox model = null;
		
		try{
			
			sql = " select rb.uy_tt_receiptbox_id " +
				  " from uy_tt_receiptbox rb " +
				  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
				  " where rb.uy_tt_receipt_id =? " +
				  " and box.boxstatus=? " +
				  " and box.iscomplete='N' " +
				  " and rb.isretained='N' " +
				  " and rb.UnificaCardCarrier = 'N' " +
				  " and box.uy_deliverypoint_id =? " +
				  " and isdestiny='N'";
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setString(2, boxStatus);
			pstmt.setInt(3, uyDeliveryPointID);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTReceiptBox(getCtx(), rs.getInt(1), get_TrxName());
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

	@Override
	protected boolean beforeDelete() {

		if (this.getDocStatus().equalsIgnoreCase(DOCSTATUS_Applied)){
			throw new AdempiereException("No es posible eliminar Recepciones Aplicadas. Debe Completarla.");
		}
		
		String sql = "";
		int result = -1;

		// Por cada Caja asociada, si no esta siendo utilizado en ninguna otra recepcion, lo puedo marcar como cerrada.
		List<MTTReceiptBox> rBoxes = this.getBoxes();
		for (MTTReceiptBox rBox : rBoxes) {
			sql = " select r.uy_tt_receipt_id " +
				  " from uy_tt_receiptbox rb " +
				  " inner join uy_tt_receipt r on rb.uy_tt_receipt_id = r.uy_tt_receipt_id " +
			      " where r.uy_tt_receipt_id !=" + this.get_ID() +
				  " and rb.uy_tt_box_id = " + rBox.getUY_TT_Box_ID() +
				  " and r.docstatus ='AY'";	
			result = DB.getSQLValueEx(null, sql);
			if (result < 0){
				MTTBox box = (MTTBox)rBox.getUY_TT_Box();
				box.setBoxStatus(X_UY_TT_Box.BOXSTATUS_Cerrado);
				box.saveEx();
			}
		}
		
		return true;
	}
	
	public  MTTBox getBoxRetImpVale() {
		// TODO Auto-generated method stub
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTBox model = null;
		
		try{
	
			sql = " select rb.uy_tt_receiptbox_id,rb.uy_tt_box_id as boxID " +
					  " from uy_tt_receiptbox rb " +
					  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
					  " inner join uy_tt_receipt rpv on rpv.uy_tt_receipt_id = rb.uy_tt_receipt_id "+
					  " where rb.uy_tt_receipt_id =? " +
					  " and box.isretained='Y' " +
					  " and box.comunicausuario = 'N' " +
					  " and box.impresionvale = 'Y' "+
					  "";					
					 // ((retainedStatus != null) ? " and rb.RetainedStatus ='" + retainedStatus + "'" : "");					  
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			//pstmt.setString(2, boxStatus);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTBox(getCtx(), rs.getInt("boxID"), get_TrxName());
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

}
