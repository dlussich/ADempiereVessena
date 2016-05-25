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
import org.compiere.apps.ADialog;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;

/**
 * @author gbrust
 *
 */
public class MFduCoefficientHdr extends X_UY_Fdu_CoefficientHdr implements
		DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -281215005049049702L;
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * @param ctx
	 * @param UY_Fdu_CoefficientHdr_ID
	 * @param trxName
	 */
	public MFduCoefficientHdr(Properties ctx, int UY_Fdu_CoefficientHdr_ID,
			String trxName) {
		super(ctx, UY_Fdu_CoefficientHdr_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduCoefficientHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
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
		
		//Re-Check
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
	 * @see org.compiere.process.DocAction#getDocumentNo()
	 */
	@Override
	public String getDocumentNo() {
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
	 * Metodo que clona cabezal y sus lineas
	 * OpenUp 
	 * @author Guillermo Brust - 11/12/2012
	 * @see
	 * @return
	 */
	public void cloneFduCoefficient() {
		
		try{

			MFduCoefficientHdr hdr = new MFduCoefficientHdr(getCtx(),0,get_TrxName()); //instancio nuevo cabezal a crear

			//comienzo a setear atributos...
			hdr.setvalidity(this.getvalidity());			
			hdr.setProcessed(false);
			hdr.setProcessing(false);
			hdr.setDocAction(DocumentEngine.ACTION_Complete);
			hdr.setDocStatus(STATUS_Drafted);
			
			hdr.saveEx(); //guardo el nuevo cabezal

			//si se grabo el cabezal procedo a crear las lineas de las tea y las lineas de los comercios que no aplican ipc para esta vigencia
			
			if(hdr.get_ID()>0){

				List<MFduCoefficientLine> lines = this.getLines();

				for (MFduCoefficientLine coefline: lines){

					MFduCoefficientLine line = new MFduCoefficientLine(getCtx(), 0 ,get_TrxName()); //instancio nueva linea

					line.setUY_Fdu_CoefficientHdr_ID(hdr.get_ID());
					line.setUY_Fdu_Productos_ID(coefline.getUY_Fdu_Productos_ID());
					line.setUY_Fdu_Afinidad_ID(coefline.getUY_Fdu_Afinidad_ID());
					line.setUY_Fdu_Currency_ID(coefline.getUY_Fdu_Currency_ID());
					
					for (int i = 2; i <= 24; i++) {
						line.setCuota(i, coefline.getCuota(i));
					}
	
					line.saveEx();
				}	
				
				List<MFduComerciosNoIpc> comercios = this.getComercios();

				for (MFduComerciosNoIpc comercioline: comercios){

					MFduComerciosNoIpc comercio = new MFduComerciosNoIpc(getCtx(), 0,get_TrxName()); //instancio nuevo comercio

					comercio.setUY_Fdu_CoefficientHdr_ID(hdr.get_ID());
					comercio.setValue(comercioline.getValue());
					comercio.setName(comercioline.getName());
					comercio.setDescription(comercioline.getDescription());
					comercio.setIsActive(comercioline.isActive());
						
					comercio.saveEx();
				}				
			}
			
			
			ADialog.info(0,null,"Clonación realizada con éxito");
			
		} catch (Exception e) {
			throw new AdempiereException(e);

		} 

	}

	/***
	 * Obtiene y retorna lineas de este cabezal.
	 * OpenUp Ltda.
	 * @author Guillermo Brust - 11/01/2013
	 * @see
	 * @return
	 */
	private List<MFduCoefficientLine> getLines() {
		
		String whereClause = X_UY_Fdu_CoefficientLine.COLUMNNAME_UY_Fdu_CoefficientHdr_ID + "=" + this.get_ID();

		List<MFduCoefficientLine> lines = new Query(getCtx(), I_UY_Fdu_CoefficientLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	private List<MFduComerciosNoIpc> getComercios() {
		
		String whereClause = X_UY_Fdu_ComerciosNoIpc.COLUMNNAME_UY_Fdu_CoefficientHdr_ID + "=" + this.get_ID();

		List<MFduComerciosNoIpc> lines = new Query(getCtx(), I_UY_Fdu_ComerciosNoIpc.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

}
