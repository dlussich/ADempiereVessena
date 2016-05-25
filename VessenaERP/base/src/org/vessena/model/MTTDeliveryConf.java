/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Oct 2, 2015
*/
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * org.openup.model - MTTDeliveryConf
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Oct 2, 2015
*/
public class MTTDeliveryConf extends X_UY_TT_DeliveryConf implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = 4732432025726889998L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_DeliveryConf_ID
	 * @param trxName
	*/

	public MTTDeliveryConf(Properties ctx, int UY_TT_DeliveryConf_ID, String trxName) {
		super(ctx, UY_TT_DeliveryConf_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MTTDeliveryConf(Properties ctx, ResultSet rs, String trxName) {
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
	 * @see org.compiere.process.DocAction#completeIt()
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
		
		MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta"); 
		MRReclamo reclamo = (MRReclamo)this.getUY_R_Reclamo();
		
		// Verifico si toda la documentacion requerida esta chequeada
		boolean faltaDoc = false;
		
		if (this.isRequired1() && !this.isPrintDoc1()){
			faltaDoc = true;
		}
		
		if (this.isRequired2() && !this.isPrintDoc2()){
			faltaDoc = true;
		}
		
		if (this.isRequired3() && !this.isPrintDoc3()){
			faltaDoc = true;
		}
		
		if (this.isRequired4() && !this.isPrintDoc4()){
			faltaDoc = true;
		}

		if (this.isRequired5() && !this.isPrintDoc5()){
			faltaDoc = true;
		}
		
		// Si no falta documentacion
		if (!faltaDoc){
			
			this.setStatus(X_UY_TT_DeliveryConf.STATUS_DOCUMENTACIONCOMPLETA);
			
			// Cierro incidencia
			reclamo.setJustification("Verificacion de Entrega");
			reclamo.setReclamoResuelto(true);
			reclamo.setEndDate(new Timestamp(System.currentTimeMillis()));
			reclamo.saveEx();
			
			try {

				if (!reclamo.processIt(DocumentEngine.ACTION_Complete)){
					throw new AdempiereException("No se pudo completar la acción de Cierre de Incidencia");
				}
				
				//reclamo.setLegajoFinancial(false, false, null);
				
			} catch (Exception e) {
				throw new AdempiereException(e.getMessage());
			}
		}
		else
		{
			this.setStatus(X_UY_TT_DeliveryConf.STATUS_FALTADOCUMENTACION);
			
			// Falta documentacion
			// Derivo incidencia
			MRArea area = new MRArea(getCtx(), config.getUY_R_Area_ID_2(), null);
			MRPtoResolucion ptoResol = new MRPtoResolucion(getCtx(), config.getUY_R_PtoResolucion_ID_2(), null);
			MUser user = MUser.forName(getCtx(), "adempiere", null);
			MRAction actionGest = MRAction.forValue(getCtx(), "DERIVAR", null);

			MRGestion gestion = new MRGestion(getCtx(), 0, get_TrxName());
			gestion.setUY_R_Reclamo_ID(reclamo.get_ID());
			gestion.setReclamoAccionType(X_UY_R_Gestion.RECLAMOACCIONTYPE_DerivarReclamo);
			gestion.setUY_R_Area_ID(area.get_ID());
			gestion.setUY_R_PtoResolucion_ID(ptoResol.get_ID());
			gestion.setDescription("Falta Documentación en Recepción de Entrega de Cuenta");
			gestion.setIsExecuted(true);
			gestion.setDateExecuted(new Timestamp (System.currentTimeMillis()));
			gestion.setReceptor_ID(user.get_ID());
			gestion.setUY_R_Action_ID(actionGest.get_ID());
			gestion.saveEx();

			// Actualizo bandeja de entrada segun derivacion
			String 	action = " update uy_r_inbox set assignto_id =null," +
					 " dateassign =null," +
					 " uy_r_ptoresolucion_id =" + gestion.getUY_R_PtoResolucion_ID() + 
					 " where uy_r_reclamo_id = " + reclamo.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			// Actualizo reclamo
			reclamo.setUY_R_Area_ID(area.getUY_R_Area_ID());
			reclamo.setUY_R_PtoResolucion_ID(ptoResol.getUY_R_PtoResolucion_ID());
			reclamo.saveEx();

			action = " update uy_r_reclamo set assignto_id =null," +
					 " assigndatefrom =null, " +
					 " statusreclamo ='" + X_UY_R_Reclamo.STATUSRECLAMO_PendienteDeGestion + "' " +
					 " where uy_r_reclamo_id = " + reclamo.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(new Timestamp(System.currentTimeMillis()));
			track.setAD_User_ID(user.get_ID());
			track.setDescription("Se deriva incidencia por Falta de Documentacion en Entrega");
			track.setUY_R_Reclamo_ID(reclamo.get_ID());		
			track.saveEx();
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
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
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
		return this.processMsg;
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

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if (this.getUY_R_Reclamo_ID() <= 0){
			throw new AdempiereException("Debe indicar numero valido de incidencia.");
		}
		
		return true;
	}

	
}
