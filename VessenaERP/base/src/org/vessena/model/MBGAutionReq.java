/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.wf.MWFNode;

/**
 * org.openup.model - MBGAutionReq
 * OpenUp Ltda. Issue # 4197
 * Description: Modelo para registro de subastas
 * @author Andrea Odriozola - 26/06/2015
 * @see
 */


public class MBGAutionReq extends X_UY_BG_AutionReq implements DocAction, IDynamicWF {
	
	private String processMsg = null;
	private boolean justPrepared = false;
	private boolean isParcialApproved = false;	
	
	private static final long serialVersionUID = 1854912321337429640L;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BG_AutionReq_ID
	 * @param trxName
	 */
	public MBGAutionReq(Properties ctx, int UY_BG_AutionReq_ID, String trxName) {
		super(ctx, UY_BG_AutionReq_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */

	
	public MBGAutionReq(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Andrea Odriozola - 26/06/2015
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
	 * @author Andrea Odriozola - 26/06/2015
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
	 * @author Andrea Odriozola - 26/06/2015
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
	 * @author Andrea Odriozola - 26/06/2015
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
	
	@Override
	public boolean approveIt() {

		try {
/**OpenUp Ltda. SBouissa 02-09-2015 Del docuemnto de revisión Sigbagsa se solicita: no obligar observacion al momento de aprobar
 * Se comenta ya que no es necesario se agregue comentario al momento de aprobar subastas 
 */
//			if ((this.getApprovalText() == null) || (this.getApprovalText().equalsIgnoreCase(""))){
//				throw new AdempiereException("Debe ingresar un Comentario en el Detalle de Aprobación");
//			}
				
			this.setIsApproved(true);
			
			this.setApprovalStatus(APPROVALSTATUS_AprobadoPorBAGSA);
			this.setDateApproved(new Timestamp(System.currentTimeMillis()));
			this.setApprovedType(APPROVEDTYPE_APROBADO);
			this.setApprovalUser_ID(Env.getAD_User_ID(Env.getCtx()));
			
			// Tracking
			MBGAutionTracking track = new MBGAutionTracking(getCtx(), 0, get_TrxName());
			track.setUY_BG_AutionReq_ID(this.get_ID());
			track.setDateTrx(this.getDateApproved());
			track.setAD_User_ID(this.getApprovalUser_ID());
			track.setDescription("Solicitud Aprobada");
			track.setobservaciones(this.getApprovalText().trim());
			track.saveEx();
			
			this.setDocStatus(DocumentEngine.STATUS_Approved);
			this.setDocAction(DocAction.ACTION_None);

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 * OpenUp Ltda. Issue # 
	 * @author Andrea Odriozola - 26/06/2015
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
	 * @author Andrea Odriozola - 26/06/2015
	 * @see
	 * @return
	 */
	@Override
	public boolean rejectIt() {

		try {

			if ((this.getApprovalText() == null) || (this.getApprovalText().equalsIgnoreCase(""))){
				throw new AdempiereException("Debe ingresar un Comentario en el Detalle de Aprobación");
			}
				
			this.setIsApproved(false);
			
			this.setApprovalStatus(APPROVALSTATUS_RechazadoPorBAGSA);
			this.setDateApproved(new Timestamp(System.currentTimeMillis()));
			this.setApprovedType(APPROVEDTYPE_RECHAZADO);
			this.setApprovalUser_ID(Env.getAD_User_ID(Env.getCtx()));
			
			// Tracking
			MBGAutionTracking track = new MBGAutionTracking(getCtx(), 0, get_TrxName());
			track.setUY_BG_AutionReq_ID(this.get_ID());
			track.setDateTrx(this.getDateApproved());
			track.setAD_User_ID(this.getApprovalUser_ID());
			track.setDescription("Solicitud Rechazada");
			track.setobservaciones(this.getApprovalText().trim());
			track.saveEx();
			
			this.setDocStatus(DocumentEngine.STATUS_NotApproved);
			this.setDocAction(DOCACTION_Request);

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		

		return true;

	}
	
	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Andrea Odriozola - 26/06/2015
	 * @see
	 * @return
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

			// Valido que antes de completarse se haya aprobado esta solicitud
			if (!this.isApproved()){
				this.processMsg = "No se puede completar el Documento ya que el mismo NO esta Aprobado.";
				return DocAction.STATUS_Invalid;
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

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
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
	
	
	
	/***
	 * Accion de Solicitud.
	 * OpenUp Ltda. Issue #4168 
	 * @author Gabriel Vila - 26/05/2015
	 * @see
	 * @return
	 */
	public boolean requestIt() {
		
		boolean result = true;
		
		try {

			// Limpio datos de aprobacion
			DB.executeUpdateEx(" update UY_BG_Transaction set ApprovedType = null, DateApproved = null, ApprovalUser_ID = null, ApprovalText = null where UY_BG_Transaction_ID =" + this.get_ID(), get_TrxName());
			
			// Estado de solicitud
			this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionBAGSA);
			
			// Seteo datos del solicitante
			this.setDateRequested(new Timestamp(System.currentTimeMillis()));
			this.setRequestedUser_ID(Env.getAD_User_ID(Env.getCtx()));

			// Tracking
			MBGAutionTracking track = new MBGAutionTracking(getCtx(), 0, get_TrxName());
			track.setUY_BG_AutionReq_ID(this.get_ID());
			track.setDateTrx(this.getDateRequested());
			track.setAD_User_ID(this.getRequestedUser_ID());
			track.setDescription("Solicitud Creada");
			track.setobservaciones(this.getDescription());
			track.saveEx();
			
			// Documento
			this.setDocStatus(DocumentEngine.STATUS_Requested);
			this.setDocAction(DocAction.ACTION_Approve);

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return result;
	}

	@Override
	public int getDynamicWFResponsibleID(MWFNode node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setApprovalInfo(int AD_WF_Responsible_ID, String textMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getWFActivityDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWFActivityHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean IsParcialApproved() {

		return this.isParcialApproved;
	}

	@Override
	public void processAutomaticApproval() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processAutomaticComplete() {

		try 
		{
			// Completo de manera automatico al aprobar documento
			if (!this.processIt(ACTION_Complete)){
				if (this.getProcessMsg() != null){
					throw new AdempiereException(this.getProcessMsg());
				}
			}
			else{
				this.saveEx();
			}
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
	}
	
	/**Obtener el precio maximo de subasta y la cantidad de "pujas" para dicha subasta
	 * (Usado desde ventana web)
	 * OpenUp Ltda Issue#
	 * @author SBouissa 4/8/2015
	 * @return
	 */
	public ArrayList<Object> getMaxPriceSubast() {
		ArrayList<Object> ret = null;
		String sql = "SELECT MAX(price),COUNT(UY_BG_AutionBid_ID), MIN(price) FROM UY_BG_AutionBid "
				+ " WHERE UY_BG_AutionReq_ID = "+this.get_ID()
				+ " AND IsActive = 'Y'";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try{
			pstm = DB.prepareStatement(sql, null);
			rs = pstm.executeQuery();
			if(rs.next()){
				ret = new ArrayList<Object>();
				ret.add(rs.getBigDecimal((1)));
				ret.add(rs.getInt(2));
				ret.add(rs.getBigDecimal(3));
			}
		}catch(Exception e){
			e.getMessage();
		}				
		return ret;
	}

	/**OpenUp Ltda. SBouissa 02-09-2015	
	 * 
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {
		//if(newRecord){
			if (this.getDateStart().after(this.getDateEnd())){
				throw new AdempiereException("Fecha inicial debe ser menor o igual a la final");
			}
			//SBouissa 02-09-2025 el campo buysell es necesario en movilidad
			if(this.getC_DocType().getValue().equals("bgsubastapo")){
				this.setBuySell(BUYSELL_COMPRA);
			}else if(this.getC_DocType().getValue().equals("bgsubasta")){
				this.setBuySell(BUYSELL_VENTA);
			}
		//}
			
			// Raúl Capecce 09-09-2015 #4711 - Pasa el precio base del campo editable al de solo lectura
			this.setPrice(this.getPrice2());
			// Fin Raúl Capecce 09-09-2015 #4711 
			
		return super.beforeSave(newRecord);
	}
}
