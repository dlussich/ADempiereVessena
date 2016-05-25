/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MHRConcept;

/**
 * @author Nicolas
 *
 */
public class MHRAportesPatronales extends X_UY_HRAportesPatronales implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2493604040176340198L;
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * @param ctx
	 * @param UY_HRAportesPatronales_ID
	 * @param trxName
	 */
	public MHRAportesPatronales(Properties ctx, int UY_HRAportesPatronales_ID,
			String trxName) {
		super(ctx, UY_HRAportesPatronales_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRAportesPatronales(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}



	@Override
	protected boolean beforeSave(boolean newRecord) {
	
		this.setDateAcct(this.getDateTrx());
		
		return true;
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
		
		// Valido que no exista otro documento para el mismo periodo en estado completo
		//if (!this.validate()) throw new AdempiereException ("Ya existe un documento para este periodo y en Estado Completo");
		
		//se cargan datos
		this.loadData();

		this.setDocStatus(DocumentEngine.STATUS_Applied);
		this.setDocAction(DocAction.ACTION_Complete);
		return true;
	}

	private void loadData() {
		
		// Obtengo los conceptos que participan de esta liquidacion ordenados por secuencia
		MHRConcept[] concepts = this.getConcepts();

		// Si no tengo conceptos a procesar aviso sin hacer nada
		if (concepts.length <= 0){
			throw new AdempiereException ("No hay Conceptos para procesar");
		}
		
		// Cargo valores de variables globales para la ejecucion de formulas de conceptos
		HashMap<String, Object> globalVars = new HashMap<String, Object>();
		globalVars = this.setGlobalVars();
		
		MHRPatronalesDetail detail = new MHRPatronalesDetail (getCtx(),0,null); //creo cabezal de la solapa de detalle
		detail.setUY_HRAportesPatronales_ID(this.get_ID());
		detail.saveEx();
				
		// Recorro y proceso empleados
		for(MHRConcept concept: concepts)
		{	
				concept.calculate(detail, globalVars) ;	
		}

		this.processMsg = "Proceso Finalizado.";
		
		
		
		
	}

	/**
	 * Valida si ya existe un documento completo para el mismo periodo.
	 * OpenUp Ltda. Issue #1059
	 * @author Nicolas Sarlabos - 02/07/2013
	 * @see 
	 * @return
	 */
	/*private boolean validate(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MHRProcess process = new MHRProcess(getCtx(), this.getUY_HRProcess_ID(), null);
		MHRPeriod period = new MHRPeriod(getCtx(), process.getHR_Period_ID(), null);

		boolean value = true;

		try{
			sql =" select uy_hraportespatronales_id " +
					" from uy_hraportespatronales " +
					" where c_period_id=? " +
					" and docstatus='CO'";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, period.getC_Period_ID());

			rs = pstmt.executeQuery ();

			if (rs.next()){
				value = false;
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return value;
	}*/
	
	/**
	 * Carga valores de variables globales para el proceso de formulas de conceptos.
	 * OpenUp Ltda. Issue # 986
	 * @author Hp - 24/04/2012
	 * @see
	 * @return
	 */
	private HashMap<String, Object> setGlobalVars() {
		
		HashMap<String, Object> vars = new HashMap<String, Object>();
		
		vars.put("patronalesdetail", new Integer(0));
		vars.put("liquidacion", new Integer(this.getUY_HRProcess_ID()));
		vars.put("empresa", new Integer(this.getAD_Client_ID()));
		vars.put("organizacion", this.getAD_Org_ID());
		vars.put("usuario", Env.getAD_User_ID(Env.getCtx()));
		
		return vars;
	}


	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

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
		// Before reActivate
		processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (processMsg != null)
			return false;	
	
		// Elimino asientos contables
		FactLine.deleteFact(X_UY_HRAportesPatronales.Table_ID, this.get_ID(), get_TrxName());
		this.setPosted(false);
		
		// After reActivate
		processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (processMsg != null)
			return false;
		
		this.setDocAction(DOCACTION_Complete);
		this.setDocStatus(DOCSTATUS_InProgress);
		//this.setProcessing(true);
		this.setProcessed(false);
		
		return true;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
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
	
	/**
	 * Obtiene y retorna conceptos, ordenados por secuencia, a considerar en el proceso. 
	 * OpenUp Ltda. Issue # 1059
	 * @author Nicolas Sarlabos - 02/07/2013
	 * @see
	 * @return
	 */
	private MHRConcept[] getConcepts() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MHRConcept> list = new ArrayList<MHRConcept>();
		
		try{
			sql = " select conc.hr_concept_id, coalesce(pconc.seqno,0) as seqno " +
				  " from hr_concept conc " +
				  " inner join hr_payrollconcept pconc on conc.hr_concept_id = pconc.hr_concept_id " +
				  " inner join hr_payroll pay on pconc.hr_payroll_id = pay.hr_payroll_id " +
				  " inner join uy_hrprocess pro on pay.hr_payroll_id = pro.hr_payroll_id " +
				  " where pro.uy_hrprocess_id =? " +
				  " and pconc.isactive='Y' " +
				  " and conc.isactive='Y' " +
				  " and conc.isrounding='N' " +
				  " order by pconc.seqno ";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_HRProcess_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MHRConcept value = new MHRConcept(Env.getCtx(), rs.getInt(1), null);
				value.setSeqNo(rs.getInt("seqno"));  // Me guardo la secuencia del concepto dentro de esta liquidacion
				list.add(value);
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return list.toArray(new MHRConcept[list.size()]);
	}
	

}
