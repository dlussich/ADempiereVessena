/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/09/2013
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
 * org.openup.model - MTTUnify
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de cabezal de unificacion de cardcarriers.
 * @author Gabriel Vila - 12/09/2013
 * @see
 */
public class MTTUnify extends X_UY_TT_Unify implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = -6795534922121209727L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_Unify_ID
	 * @param trxName
	 */
	public MTTUnify(Properties ctx, int UY_TT_Unify_ID, String trxName) {
		super(ctx, UY_TT_Unify_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTUnify(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {

		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
		List<MTTUnifyCard> cards = this.getScanCards();
		if ((cards == null) || (cards.size() <= 0)){
			this.processMsg = "Para Completar se debe escanear al menos una Cuenta.";
			return DocAction.STATUS_Invalid;
		}		
		
		// Por cada Caja asociada, si no esta siendo utilizado en ninguna otra unificacion, lo puedo marcar como cerrada.
		List<MTTUnifyBox> rBoxes = this.getBoxes();
		for (MTTUnifyBox rBox : rBoxes) {
			String sql = " select r.uy_tt_unify_id " +
				         " from uy_tt_unifybox rb " +
				         " inner join uy_tt_unify r on rb.uy_tt_unify_id = r.uy_tt_unify_id " +
			             " where r.uy_tt_unify_id !=" + this.get_ID() +
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
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
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
	 * @author Hp - 12/09/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/***
	 * Verifica si una determinada caja se encuentra asociada a esta unificacion. 
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 13/09/2013
	 * @see
	 * @param uyTTBoxID
	 * @return
	 */
	public boolean containsBox(int uyTTBoxID){
		
		String whereClause = X_UY_TT_UnifyBox.COLUMNNAME_UY_TT_Unify_ID + "=" + this.get_ID() +
						" AND " + X_UY_TT_UnifyBox.COLUMNNAME_UY_TT_Box_ID + "=" + uyTTBoxID;
		
		MTTUnifyBox model = new Query(getCtx(), I_UY_TT_UnifyBox.Table_Name, whereClause, get_TrxName()).first();
		
		return (model != null);
		
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
	public MTTUnifyBox getBoxDeliveryPoint(String boxStatus, int uyDeliveryPointID){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTUnifyBox model = null;
		
		try{
			
			sql = " select rb.uy_tt_unifybox_id " +
				  " from uy_tt_unifybox rb " +
				  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
				  " where rb.uy_tt_unify_id =? " +
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
				model = new MTTUnifyBox(getCtx(), rs.getInt(1), get_TrxName());
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
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 29/09/2013
	 * @see
	 * @param boxStatus
	 * @return
	 */
	public MTTUnifyBox getBoxRetention(String boxStatus, String retainedStatus){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTUnifyBox model = null;
		
		try{

			sql = " select rb.uy_tt_unifybox_id " +
					  " from uy_tt_unifybox rb " +
					  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
					  " where rb.uy_tt_unify_id =? " +
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
				model = new MTTUnifyBox(getCtx(), rs.getInt(1), get_TrxName());
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
	 * Obtiene y retona lista de Cajas asociadas a esta Unificacion.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @return
	 */
	public List<MTTUnifyBox> getBoxes(){
		
		String whereClause = X_UY_TT_UnifyBox.COLUMNNAME_UY_TT_Unify_ID + "=" + this.get_ID();
		
		List<MTTUnifyBox> lines = new Query(getCtx(), I_UY_TT_UnifyBox.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
		
	}

	/***
	 * Obtiene y retona lista de Cuentas Escaneadas asociadas a este modelo.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @return
	 */
	public List<MTTUnifyCard> getScanCards(){
		
		String whereClause = X_UY_TT_UnifyCard.COLUMNNAME_UY_TT_Unify_ID + "=" + this.get_ID();
		
		List<MTTUnifyCard> lines = new Query(getCtx(), I_UY_TT_UnifyCard.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
		
	}
	
	/***
	 * Obtiene y retorna caja de retencion para comunicacion a usuario.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 04/10/2013
	 * @see
	 * @param boxStatus
	 * @return
	 */
	public MTTUnifyBox getBoxAccountComunication(String boxStatus){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTTUnifyBox model = null;
		
		try{
			
			sql = " select rb.uy_tt_unifybox_id " +
				  " from uy_tt_unifybox rb " +
				  " inner join uy_tt_box box on rb.uy_tt_box_id = box.uy_tt_box_id " +
				  " where rb.uy_tt_unify_id =? " +
				  " and box.boxstatus=? " +
				  " and box.iscomplete='N' " +
				  " and rb.isretained='Y' " +
				  " and rb.ComunicaUsuario = 'Y' ";
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setString(2, boxStatus);
		
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				model = new MTTUnifyBox(getCtx(), rs.getInt(1), get_TrxName());
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
		
		// Por cada Caja asociada, si no esta siendo utilizado en ninguna otra unificacion, lo puedo marcar como cerrada.
		List<MTTUnifyBox> rBoxes = this.getBoxes();
		for (MTTUnifyBox rBox : rBoxes) {
			String sql = " select r.uy_tt_unify_id " +
				         " from uy_tt_unifybox rb " +
				         " inner join uy_tt_unify r on rb.uy_tt_unify_id = r.uy_tt_unify_id " +
			             " where r.uy_tt_unify_id !=" + this.get_ID() +
				         " and rb.uy_tt_box_id = " + rBox.getUY_TT_Box_ID() +
				         " and r.docstatus ='AY'";	
			int result = DB.getSQLValueEx(null, sql);
			if (result < 0){
				MTTBox box = (MTTBox)rBox.getUY_TT_Box();
				box.setBoxStatus(X_UY_TT_Box.BOXSTATUS_Cerrado);
				box.saveEx();
			}
		}
		
		return true;
	}

}
