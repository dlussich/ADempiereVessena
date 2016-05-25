/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Oct 12, 2015
*/
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
 * org.openup.model - MTTContractHand
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Oct 12, 2015
*/
public class MTTContractHand extends X_UY_TT_ContractHand implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = -3094972193712752232L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ContractHand_ID
	 * @param trxName
	*/

	public MTTContractHand(Properties ctx, int UY_TT_ContractHand_ID, String trxName) {
		super(ctx, UY_TT_ContractHand_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MTTContractHand(Properties ctx, ResultSet rs, String trxName) {
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

		MTTContract contract = (MTTContract)this.getUY_TT_Contract();
		
		if (contract.isPrintDoc1()){
			MTTContractHandLine anexoa = new MTTContractHandLine(getCtx(), 0, get_TrxName());
			anexoa.setUY_TT_ContractHand_ID(this.get_ID());
			anexoa.setValue("anexoa");
			anexoa.setName("Anexo A");
			anexoa.setIsDelivered(contract.isDelivered1());
			anexoa.saveEx();
		}
		else{
			contract.setIsDelivered1(true);
		}
		
		if (contract.isPrintDoc2()){
			MTTContractHandLine vale = new MTTContractHandLine(getCtx(), 0, get_TrxName());
			vale.setUY_TT_ContractHand_ID(this.get_ID());
			vale.setValue("vale");
			vale.setName("Vale");
			vale.setIsDelivered(contract.isDelivered2());
			vale.saveEx();
		}
		else{
			contract.setIsDelivered2(true);
		}
		
		if (contract.isPrintDoc3()){
			MTTContractHandLine copiaci = new MTTContractHandLine(getCtx(), 0, get_TrxName());
			copiaci.setUY_TT_ContractHand_ID(this.get_ID());
			copiaci.setValue("copiaci");
			copiaci.setName("Copia Cedula de Identidad");
			copiaci.setIsDelivered(contract.isDelivered3());
			copiaci.saveEx();
		}
		else{
			contract.setIsDelivered3(true);
		}
		
		if (contract.isPrintDoc4()){
			MTTContractHandLine recsueldo = new MTTContractHandLine(getCtx(), 0, get_TrxName());
			recsueldo.setUY_TT_ContractHand_ID(this.get_ID());
			recsueldo.setValue("recsueldo");
			recsueldo.setName("Recibo de Sueldo");
			recsueldo.setIsDelivered(contract.isDelivered4());
			recsueldo.saveEx();
		}
		else{
			contract.setIsDelivered4(true);
		}
	
		if (contract.isPrintDoc5()){
			MTTContractHandLine consdom = new MTTContractHandLine(getCtx(), 0, get_TrxName());
			consdom.setUY_TT_ContractHand_ID(this.get_ID());
			consdom.setValue("consdom");
			consdom.setName("Constancia de Domicilio");
			consdom.setIsDelivered(contract.isDelivered5());
			consdom.saveEx();
		}
		else{
			contract.setIsDelivered5(true);
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
		
		MTTContract contract = (MTTContract)this.getUY_TT_Contract();
		
		List<MTTContractHandLine> lines = this.getLines();

		for (MTTContractHandLine line: lines){
			if (line.isSelected()){
				if (line.getValue().trim().equalsIgnoreCase("anexoa")){
					contract.setIsDelivered1(true);
				}
				else if (line.getValue().trim().equalsIgnoreCase("vale")){
					contract.setIsDelivered2(true);
				}
				else if (line.getValue().trim().equalsIgnoreCase("copiaci")){
					contract.setIsDelivered3(true);
				}
				else if (line.getValue().trim().equalsIgnoreCase("recsueldo")){
					contract.setIsDelivered4(true);
				}
				else if (line.getValue().trim().equalsIgnoreCase("consdom")){
					contract.setIsDelivered5(true);
				}
			}
		}
		
		// Si todos los documentos del contrato fueron entregados
		if ((contract.isDelivered1()) && (contract.isDelivered2()) && (contract.isDelivered3())
				&& (contract.isDelivered4()) && (contract.isDelivered5())){
			
			// Marco contrato como totalmente entregado
			contract.setIsDelivered(true);
			
			// Libero posicion de caja para este contrato
			MTTBox box = (MTTBox)contract.getUY_TT_Box();
			
			DB.executeUpdateEx(" delete from uy_tt_boxcontract where uy_tt_box_id =" + box.get_ID() + " and uy_tt_contract_id =" + contract.get_ID(), get_TrxName());

			box.refreshContracts();
			
			// Cierro incidencia asociada
			MRReclamo reclamo = new MRReclamo(getCtx(), contract.getUY_R_Reclamo_ID_2(), get_TrxName());
			reclamo.setJustification("Entrega de Contrato");
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
		
		contract.saveEx();
		
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
	 * Obtiene y retorna lineas de este documento.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Oct 14, 2015
	 * @return
	 */
	private List<MTTContractHandLine> getLines(){
		
		String whereClause = X_UY_TT_ContractHandLine.COLUMNNAME_UY_TT_ContractHand_ID + "=" + this.get_ID();
		
		List<MTTContractHandLine> lines = new Query(getCtx(), I_UY_TT_ContractHandLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
	}
	
}
