/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 03/06/2015
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import org.compiere.util.Env;
import org.compiere.wf.MWFNode;

/**
 * org.openup.model - MBGTransaction
 * OpenUp Ltda. Issue #4173, #4174 
 * Description: Modelo para documentos de registro de precontratos y mandatos en modulo de bolsa.
 * @author Gabriel Vila - 03/06/2015
 * @see
 */
public class MBGTransaction extends X_UY_BG_Transaction implements DocAction, IDynamicWF {

	private String processMsg = null;
	private boolean justPrepared = false;
	private boolean isParcialApproved = false;
	
	private static final long serialVersionUID = 1163619928954125333L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BG_Transaction_ID
	 * @param trxName
	 */
	public MBGTransaction(Properties ctx, int UY_BG_Transaction_ID,
			String trxName) {
		super(ctx, UY_BG_Transaction_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGTransaction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {

		try {

			if ((this.getApprovalText() == null) || (this.getApprovalText().equalsIgnoreCase(""))){
				throw new AdempiereException("Debe ingresar un Comentario en el Detalle de Aprobación");
			}
				
			this.setIsApproved(true);
			
			this.setApprovalStatus(APPROVALSTATUS_AprobadoPorBAGSA);
			this.setDateApproved(new Timestamp(System.currentTimeMillis()));
			this.setApprovedType(APPROVEDTYPE_APROBADO);
			this.setApprovalUser_ID(Env.getAD_User_ID(Env.getCtx()));
			
			// Tracking
			MBGTransTracking track = new MBGTransTracking(getCtx(), 0, get_TrxName());
			track.setUY_BG_Transaction_ID(this.get_ID());
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
			MBGTransTracking track = new MBGTransTracking(getCtx(), 0, get_TrxName());
			track.setUY_BG_Transaction_ID(this.get_ID());
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
	 * @author Gabriel Vila - 03/06/2015
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

		// Valido que antes de completarse se haya aprobado esta solicitud
		if (!this.isApproved()){
			this.processMsg = "No se puede completar el Documento ya que el mismo NO esta Aprobado.";
			return DocAction.STATUS_Invalid;
		}
		
		// Segun documento.
		MDocType doc = (MDocType)this.getC_DocType();
		if (doc.getValue().equalsIgnoreCase("bgmandato")){
			// Para mandatos debo generar tantos contratos como productos tenga asociados
			List<MBGTransProd> prods = this.getProducts();
			for (MBGTransProd prod: prods){
				
				MBGContract contract = new MBGContract(getCtx(), 0, get_TrxName());
				contract.setUY_BG_Transaction_ID(this.get_ID());
				contract.setM_Product_ID(prod.getM_Product_ID());
				
				MDocType docContract = MDocType.forValue(getCtx(), "bgcontrato", null);

				contract.setC_DocType_ID(docContract.get_ID());
				contract.setDateTrx(this.getDateTrx());
				contract.setUY_BG_Customer_ID(this.getUY_BG_Customer_ID());
				contract.setUY_BG_Bursa_ID(this.getUY_BG_Bursa_ID());
				contract.setprojecttype(this.getProjectType());
				contract.setDateDelivered(this.getDateDelivered());
				contract.setBodega(this.getBodega());
				contract.setpaymentruletype(this.getpaymentruletype());
				contract.setUY_BG_PackingMode_ID(prod.getUY_BG_PackingMode_ID());
				contract.setLocationFrom(this.getLocationFrom());
				contract.setVolume(prod.getCantidad());
				contract.setC_UOM_ID(prod.getC_UOM_ID());
				contract.setPriceEntered(prod.getPrice2());
				contract.setAmt(prod.gettotal2());
				contract.setC_Region_ID(this.getC_Region_ID());
				contract.setC_City_ID(this.getC_City_ID());
				contract.setAddress1(this.getAddress1());
				contract.setC_Region_ID_2(this.getC_Region_ID_2());
				contract.setC_City_ID_2(this.getC_City_ID_2());
				contract.setAddress2(this.getAddress2());
				//SBT OpenUp 07-09-2015 se agrega retencion, y comision
				contract.setAmtRetention(prod.getPriceEntered());// Comision Bagsa 
				contract.setAmtRetention2(prod.getAmt());// Retención común			
				contract.saveEx();
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
	 * @author Gabriel Vila - 03/06/2015
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
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
	 * @author Gabriel Vila - 03/06/2015
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
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
			MBGTransTracking track = new MBGTransTracking(getCtx(), 0, get_TrxName());
			track.setUY_BG_Transaction_ID(this.get_ID());
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
	protected boolean beforeSave(boolean newRecord) {
		
		try {

			// Valido segun accion de documento (solicitar o aprobar/rechazar)
			if ((this.getDocStatus().equalsIgnoreCase(DOCSTATUS_Drafted))
					|| (this.getDocStatus().equalsIgnoreCase(DOCSTATUS_NotApproved))){

				// No permito que un usuario distinto al usuario creador de esta solicitud, modifique este registro.
				if (!newRecord){
					if (Env.getAD_User_ID(Env.getCtx()) != this.getRequestedUser_ID()){
						log.saveError(null, "El usuario actual no tiene permisos para modificar esta Solicitud creada por otro usuario.");
			            return false;
					}
				}
				else{
			
					// Seteo usuario solicitante en un regitro nuevo
					this.setRequestedUser_ID(Env.getAD_User_ID(Env.getCtx()));
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return true;
	}


	/***
	 * Obtiene y retorna lista de productos asociados a esta transaccion
	 * OpenUp Ltda. Issue #4174 
	 * @author Gabriel Vila - 24/06/2015
	 * @see
	 * @return
	 */
	private List<MBGTransProd> getProducts(){
		
		String whereClause = X_UY_BG_TransProd.COLUMNNAME_UY_BG_Transaction_ID + "=" + this.get_ID();
		
		List<MBGTransProd> lines = new Query(getCtx(), I_UY_BG_TransProd.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
	}

	public boolean sumarizarXCampo(String campo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String res = ""; String sumo="";
	    try{
	    	if(campo.equals(X_UY_BG_Transaction.COLUMNNAME_amt2)){
	    		sumo = X_UY_BG_TransProd.COLUMNNAME_Amt;
	    	}else if(campo.equals(X_UY_BG_Transaction.COLUMNNAME_AmtRetention2)){
	    		sumo = X_UY_BG_TransProd.COLUMNNAME_total2;
	    	}else if(campo.equals(X_UY_BG_Transaction.COLUMNNAME_AmtRetention)){
	    		sumo =  X_UY_BG_TransProd.COLUMNNAME_PriceEntered;
	    	}else{
	    		return false;
	    	}
	    	String sql = "SELECT SUM("+sumo+"),count(UY_BG_TransProd_ID) FROM UY_BG_TransProd "
	    			+ " WHERE UY_BG_TRANSACTION_ID = "+this.get_ID();
	    	//select sum(uy_bg_transprod.Total2) from uy_bg_transprod where uy_bg_transprod.uy_bg_transaction_id = @UY_BG_Transaction_ID@)
	        //select sum(uy_bg_transprod.PriceEntered) from uy_bg_transprod where uy_bg_transprod.uy_bg_transaction_id = @UY_BG_Transaction_ID@)
	    	//select sum(uy_bg_transprod.amt) from uy_bg_transprod where uy_bg_transprod.uy_bg_transaction_id = 1000112
	        pstmt = DB.prepareStatement(sql, get_TrxName());

	        rs = pstmt.executeQuery();	           
	        if(rs!=null){
	        	while (rs.next()) {
	        		Object o = rs.getBigDecimal(1); 
	        		Object o2 = rs.getInt(2);
	        		this.set_Value(campo, rs.getObject(1));
	        		this.saveEx();
	        		return true;
	        	}
	        }
		
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	    return false;
	}
	
}
