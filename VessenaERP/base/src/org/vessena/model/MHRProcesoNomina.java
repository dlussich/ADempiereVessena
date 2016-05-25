/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/04/2012
 */
 
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactLine;
import org.compiere.apps.AWindow;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;
import org.compiere.model.MConversionRate;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.model.X_C_BPartner;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.MHRConcept;
import org.eevolution.model.MHRPayroll;
import org.eevolution.model.MHRPeriod;
import org.eevolution.model.X_HR_Concept;
import org.eevolution.model.X_HR_Payroll;
import org.openup.util.Converter;

/**
 * org.openup.model - MHRProcesoNomina
 * OpenUp Ltda. Issue # 986
 * Description: Proceso de liquidacion de nomina
 * @author Hp - 20/04/2012
 * @see
 */
public class MHRProcesoNomina extends X_UY_HRProcesoNomina implements DocAction{

	private static final long serialVersionUID = 7474939989831436701L;
	private String processMsg = null;
	private AWindow window = null;
	private boolean justPrepared = false;
	private BigDecimal divideRate = Env.ZERO;
	
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_HRProcesoNomina_ID
	 * @param trxName
	 */
	public MHRProcesoNomina(Properties ctx, int UY_HRProcesoNomina_ID,
			String trxName) {
		super(ctx, UY_HRProcesoNomina_ID, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//OpenUp. Nicolas Sarlabos. 25/06/2013. #1058.
		this.setDateAcct(this.getDateTrx());
		//Fin OpenUp. #1058
		
		//OpenUp. Nicolas Sarlabos. 21/06/2013. #1053. Comento codigo innecesario
		//OpenUp. Nicolas Sarlabos. 26/03/2013. #603. Controlo que no exista mas de una liq. de aguinaldo para igual periodo
		/*String sql = "";
		
		MHRProcess process = new MHRProcess (getCtx(),this.getUY_HRProcess_ID(),get_TrxName()); //obtengo liquidacion
		MHRPeriod period = new MHRPeriod (getCtx(),process.getHR_Period_ID(),get_TrxName()); //obtengo periodo
		MHRPayroll payroll = MHRPayroll.forValue(getCtx(), "1000002"); //obtengo tipo de liquidacion "Aguinaldo"
				
		sql = "SELECT p.uy_hrprocesonomina_id" +
              " FROM uy_hrprocesonomina p" + 
              " INNER JOIN uy_hrprocess pro on p.uy_hrprocess_id = pro.uy_hrprocess_id" +
              " INNER JOIN hr_period period on pro.hr_period_id = period.hr_period_id" +
              " WHERE period.c_period_id=" + period.getC_Period_ID() + " and pro.hr_payroll_id=" + payroll.get_ID() +
              " and p.uy_hrprocesonomina_id <> " + this.get_ID();
		int ID = DB.getSQLValue(get_TrxName(), sql);
		
		if(ID>0) throw new AdempiereException ("Ya existe una liquidacion de aguinaldo para el periodo: " + period.getName());*/
		//Fin OpenUp.
		//Fin OpenUp. #1053
		
		return true;
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRProcesoNomina(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public AWindow getWindow() {
		return this.window;
	}

	public void setWindow(AWindow value) {
		this.window = value;
	}
	
	/**
	 * Retorno mensaje de proceso.
	 * OpenUp Ltda. Issue # 986
	 * @author Hp - 20/04/2012
	 * @see
	 * @return
	 */
	public String getProcessMsg() {
		return this.processMsg;
	}

	
	/**
	 * Ejecuta proceso de liquidacion de nomina.
	 * OpenUp Ltda. Issue # 986
	 * @author Hp - 23/04/2012
	 * @see
	 * @param reProcessing
	 */
	public void execute(boolean reProcessing){
	
		try{

			// Obtengo lista de empleados a considerar en este proceso
			List<MBPartner> employees = this.getEmployees(reProcessing);

			// Si no tengo empleados a procesar aviso sin hacer nada
			if (employees.size() <= 0){
				this.processMsg = "No hay empleados para procesar.";
			}
		
			// Obtengo los conceptos que participan de esta liquidacion ordenados por secuencia
			MHRConcept[] concepts = this.getConcepts();
	
			// Si no tengo conceptos a procesar aviso sin hacer nada
			if (concepts.length <= 0){
				this.processMsg = "No hay Conceptos para procesar.";
			}

			// Elimino datos de proceso anterior para esta liquidacion
			this.deleteOldData(reProcessing);
			
			// Cargo valores de variables globales para la ejecucion de formulas de conceptos
			HashMap<String, Object> globalVars = new HashMap<String, Object>();
			globalVars = this.setGlobalVars();
			
			MHRProcess hrprocess = (MHRProcess)this.getUY_HRProcess();//instancio la liquidacion
						
			if(hrprocess.get_ValueAsBoolean("IsDateProcess")){//si se indico usar fecha de la liquidacion
				
				Timestamp dateProcess = TimeUtil.trunc(hrprocess.getDateTrx(), TimeUtil.TRUNC_DAY);
				
				this.divideRate = MConversionRate.getDivideRate(142, 100, dateProcess, 0, this.getAD_Client_ID(), this.getAD_Org_ID());
				if(this.divideRate.compareTo(Env.ZERO)==0) throw new AdempiereException("No se encontro tasa de cambio para fecha de liquidacion");				
				
			} else {//de lo contrario se toma la fecha actual
				
				Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
				
				this.divideRate = MConversionRate.getDivideRate(142, 100, today, 0, this.getAD_Client_ID(), this.getAD_Org_ID());
				if(this.divideRate.compareTo(Env.ZERO)==0) throw new AdempiereException("No se encontro tasa de cambio para la fecha actual");	
				
			}
			
			// Recorro y proceso empleados
			for(MBPartner employee: employees)
			{	
					globalVars.put("empleado", new Integer(employee.get_ID()));
					this.executeEmployee(employee, concepts, globalVars) ;	
			}

			this.processMsg = "Proceso Finalizado.";
		}
		catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}
		
	}

	/**
	 * Carga valores de variables globales para el proceso de formulas de conceptos.
	 * OpenUp Ltda. Issue # 986
	 * @author Hp - 24/04/2012
	 * @see
	 * @return
	 */
	private HashMap<String, Object> setGlobalVars() {
		
		HashMap<String, Object> vars = new HashMap<String, Object>();
		
		vars.put("empleado", new Integer(0));
		vars.put("liquidacion", new Integer(this.getUY_HRProcess_ID()));
		vars.put("empresa", new Integer(this.getAD_Client_ID()));
		vars.put("organizacion", this.getAD_Org_ID());
		vars.put("usuario", Env.getAD_User_ID(Env.getCtx()));
		
		return vars;
	}

	
	/**
	 * Proceso liquidacion de un empleado.
	 * OpenUp Ltda. Issue # 986
	 * @author Hp - 24/04/2012
	 * @see
	 * @param employee
	 * @param concepts
	 * @param globalVars
	 */
	public void executeEmployee(MBPartner employee, MHRConcept[] concepts, HashMap<String, Object> globalVars) {

		HashMap<Integer, BigDecimal> calculatedConcepts = new HashMap<Integer, BigDecimal>();
		BigDecimal sumDrIrpf = Env.ZERO, sumDrIrpfBPS = Env.ZERO, sumDrIrpfEx = Env.ZERO;
		BigDecimal sumCrIrpf = Env.ZERO, sumCrIrpfBPS = Env.ZERO, sumCrIrpfEx = Env.ZERO;
		BigDecimal sumDrBPS = Env.ZERO;
		BigDecimal sumCrBPS = Env.ZERO;
		MHRResult resultEmployee = null;
		int conceptCatHaberes_ID = MHRConcept.getConceptCategoryID(get_TrxName(), "1001");
		boolean isDriver = false;
		
		try{
			
			if(employee.get_ValueAsInt("UY_TR_Driver_ID") > 0) isDriver = true;
			
			// Instancio modelo de resultado de proceso de liquidacion para este empleado
			resultEmployee = new MHRResult(getCtx(), 0, null);
			resultEmployee.setUY_HRProcesoNomina_ID(this.get_ID());
			resultEmployee.setC_BPartner_ID(employee.get_ID());
			resultEmployee.saveEx();   // Guardo modelo para tener su ID
			
			// Si no recibo conceptos, los obtengo ahora
			if (concepts == null) concepts = this.getConcepts();
			
			// Si no tengo conceptos a procesar aviso sin hacer nada
			if (concepts.length <= 0){
				throw new Exception("No hay Conceptos para procesar.");
			}

			// Obtengo suma de haberes irpf de liquidaciones del mismo periodo
			sumDrIrpf = this.getSumDrIrpf(employee, false);
			sumDrIrpfBPS = this.getSumDrIrpf(employee, true);
			sumDrIrpfEx = this.getSumDrIrpfEx(employee);

			// Obtengo suma de descuentos irpf de liquidaciones del mismo periodo
			sumCrIrpf = this.getSumCrIrpf(employee, false);
			sumCrIrpfBPS = this.getSumCrIrpf(employee, true);
			sumCrIrpfEx = this.getSumCrIrpfEx(employee);
					
			// Recorro conceptos que ya vienen ordenados por secuencia
			for (int i = 0; i < concepts.length; i ++){
				
				MHRConcept concept = concepts[i];
				BigDecimal valueConcept = concept.calculate(employee, calculatedConcepts, globalVars, 
															this.getUY_HRProcess_ID(), resultEmployee.get_ID(),
															sumDrIrpf, sumCrIrpf, sumDrBPS, sumCrBPS, 
															sumDrIrpfBPS, sumCrIrpfBPS, sumDrIrpfEx, sumCrIrpfEx);
				
				// Considero el valor resultado del concepto si es de tipo Concepto o Informacion.
				if ((concept.getType().equalsIgnoreCase(X_HR_Concept.TYPE_Information)) ||
					(concept.getType().equalsIgnoreCase(X_HR_Concept.TYPE_Concept))){

					// Sumo valor del concepto segun naturaleza del mismo
					if (concept.getAccountSign().equalsIgnoreCase(X_HR_Concept.ACCOUNTSIGN_Debit)){
						
						// Si este concepto se imprime, entonces acumulo en haberes
						if (concept.isPrinted()){
							resultEmployee.setAmtAcctDr(resultEmployee.getAmtAcctDr().add(valueConcept));	
						}
						
						if(concept.isExclude()){
							sumDrIrpfEx = sumDrIrpfEx.add(valueConcept);								
						}

						// Si este concepto grava irpf
						if (concept.isgrabado_irpf()){
							if(concept.isViaticoNacional()){
								
								sumDrIrpf = sumDrIrpf.add(valueConcept.divide(new BigDecimal(2), 2, RoundingMode.HALF_UP));		
								
							} else if(concept.isViaticoExtranjero()){
								
								if(isDriver){ //si es chofer
									
									BigDecimal viaticoExt = this.getViaticoAmt(false);
									
									if(viaticoExt.compareTo(Env.ZERO)<=0) throw new AdempiereException("El valor de viatico extranjero en parametros de nomina debe ser mayor a cero");
									
									BigDecimal deduccion = this.getDeduccionViatExt();
									
									if(deduccion.compareTo(Env.ZERO)<=0) throw new AdempiereException("El importe de deduccion para viatico extranjero en parametros de nomina debe ser mayor a cero");
									
									BigDecimal amt = (this.divideRate.multiply(deduccion)).setScale(2, RoundingMode.HALF_UP);
									BigDecimal amt2 = viaticoExt.subtract(amt);
									BigDecimal amt3 = valueConcept.divide(viaticoExt, 2, RoundingMode.HALF_UP);
									
									sumDrIrpf = sumDrIrpf.add(amt3.multiply(amt2));										
									
								} else sumDrIrpf = sumDrIrpf.add(valueConcept.multiply(new BigDecimal(0.25))); //si no es chofer																											
								
							} else sumDrIrpf = sumDrIrpf.add(valueConcept);
							
							if (concept.isgrabado_bps()) {
								if(concept.isViaticoNacional()){
									
									sumDrIrpfBPS = sumDrIrpfBPS.add(valueConcept.divide(new BigDecimal(2), 2, RoundingMode.HALF_UP));
									
								} else if(concept.isViaticoExtranjero()) {
									
									if(isDriver){ //si es chofer
										
										BigDecimal viaticoExt = this.getViaticoAmt(false);
										
										if(viaticoExt.compareTo(Env.ZERO)<=0) throw new AdempiereException("El valor de viatico extranjero en parametros de nomina debe ser mayor a cero");
										
										BigDecimal deduccion = this.getDeduccionViatExt();
										
										if(deduccion.compareTo(Env.ZERO)<=0) throw new AdempiereException("El importe de deduccion para viatico extranjero en parametros de nomina debe ser mayor a cero");
										
										BigDecimal amt = (this.divideRate.multiply(deduccion)).setScale(2, RoundingMode.HALF_UP);
										BigDecimal amt2 = viaticoExt.subtract(amt);
										BigDecimal amt3 = valueConcept.divide(viaticoExt, 2, RoundingMode.HALF_UP);
										
										sumDrIrpfBPS = sumDrIrpfBPS.add(amt3.multiply(amt2));										
										
									} else sumDrIrpfBPS = sumDrIrpfBPS.add(valueConcept.multiply(new BigDecimal(0.25)));	 //si no es chofer	
									
								} else sumDrIrpfBPS = sumDrIrpfBPS.add(valueConcept);
							}							
						}
						
						if (concept.isgrabado_bps() && concept.getHR_Concept_Category_ID() == conceptCatHaberes_ID){
							if(concept.isViaticoNacional()){
								
								sumDrBPS = sumDrBPS.add(valueConcept.divide(new BigDecimal(2), 2, RoundingMode.HALF_UP));
								
							} else if (concept.isViaticoExtranjero()){
								
								if(isDriver){ //si es chofer
									
									BigDecimal viaticoExt = this.getViaticoAmt(false);
									
									if(viaticoExt.compareTo(Env.ZERO)<=0) throw new AdempiereException("El valor de viatico extranjero en parametros de nomina debe ser mayor a cero");
									
									BigDecimal deduccion = this.getDeduccionViatExt();
									
									if(deduccion.compareTo(Env.ZERO)<=0) throw new AdempiereException("El importe de deduccion para viatico extranjero en parametros de nomina debe ser mayor a cero");
									
									BigDecimal amt = (this.divideRate.multiply(deduccion)).setScale(2, RoundingMode.HALF_UP);
									BigDecimal amt2 = viaticoExt.subtract(amt);
									BigDecimal amt3 = valueConcept.divide(viaticoExt, 2, RoundingMode.HALF_UP);
									
									sumDrBPS = sumDrBPS.add(amt3.multiply(amt2));								
									
								} else sumDrBPS = sumDrBPS.add(valueConcept.multiply(new BigDecimal(0.25))); //si no es chofer									
								
							} else sumDrBPS = sumDrBPS.add(valueConcept);
						}
						
					}
					else if (concept.getAccountSign().equalsIgnoreCase(X_HR_Concept.ACCOUNTSIGN_Credit)){
						
						// Si este concepto se imprime, entonces acumulo en descuentos
						if (concept.isPrinted()){
							resultEmployee.setAmtAcctCr(resultEmployee.getAmtAcctCr().add(valueConcept));	
						}
						
						if(concept.isExclude()){
							sumCrIrpfEx = sumCrIrpfEx.add(valueConcept);								
						}

						// Si este concepto grava irpf
						if (concept.isgrabado_irpf()){
							sumCrIrpf = sumCrIrpf.add(valueConcept);
							if (concept.isgrabado_bps()) {
								sumCrIrpfBPS = sumCrIrpfBPS.add(valueConcept);
							}							
						}
						
						if (concept.isgrabado_bps() && concept.getHR_Concept_Category_ID() == conceptCatHaberes_ID){
							sumCrBPS = sumCrBPS.add(valueConcept);
						}
						
					}
					
				}

			}
			
			// Se calcula y aplica redondeo en caso de ser necesario
			BigDecimal total = (resultEmployee.getAmtAcctDr().subtract(resultEmployee.getAmtAcctCr()));
			BigDecimal totalRounding = total.setScale(0, RoundingMode.HALF_UP);
			BigDecimal diff = totalRounding.subtract(total); 
			
			if(diff.compareTo(Env.ZERO)!=0){
				
				MHRConcept concept = MHRConcept.getConceptRounding(getCtx(),get_TrxName()); //obtengo concepto de redondeo
				// Si tengo un concepto definido para redondeo
				if(concept != null){
					MHRResultDetail roundResDetail = new MHRResultDetail(getCtx(), 0, get_TrxName());			
					roundResDetail.setUY_HRResult_ID(resultEmployee.get_ID());
					roundResDetail.setHR_Concept_ID(concept.get_ID());
					roundResDetail.setType(concept.getType());
					roundResDetail.setColumnType(concept.getColumnType());
					roundResDetail.setSeqNo(concept.getSeqNo());


					if(diff.compareTo(Env.ZERO)>0){
						roundResDetail.setAccountSign("D"); //cargo en haberes
						resultEmployee.setAmtAcctDr(resultEmployee.getAmtAcctDr().add(diff));	
					}else if(diff.compareTo(Env.ZERO)<0){
						roundResDetail.setAccountSign("C"); //cargo en descuentos
						diff = diff.multiply(new BigDecimal(-1));
						resultEmployee.setAmtAcctCr(resultEmployee.getAmtAcctCr().add(diff));
					}

					roundResDetail.setTotalAmt(diff);
					roundResDetail.setSuccess(true);
					roundResDetail.saveEx(); 
				}
			}
			
			// Guardo resultado de proceso de liquidacion de este empleado
			resultEmployee.setsumCrBPS(sumCrBPS);
			resultEmployee.setsumDrBPS(sumDrBPS);
			resultEmployee.setTotalAmt(totalRounding);
			
			// Me cubro de un total negativo
			if (resultEmployee.getTotalAmt().compareTo(Env.ZERO)<0){
				throw new AdempiereException ("Monto negativo: " + resultEmployee.getTotalAmt());
			}
						
			//aplico redondeo
			resultEmployee.setAmtAcctCr(resultEmployee.getAmtAcctCr().setScale(2, RoundingMode.HALF_UP));
			resultEmployee.setAmtAcctDr(resultEmployee.getAmtAcctDr().setScale(2, RoundingMode.HALF_UP));
			resultEmployee.setSuccess(true);
			// Total en letras
			resultEmployee.setLiteralNumber(new Converter().getStringOfNumber(resultEmployee.getTotalAmt().intValueExact()));
			resultEmployee.saveEx();
			
		}
		catch (Exception ex){
			if (resultEmployee != null){
				// Guardo resultado de empleado como no exitoso
				resultEmployee.setSuccess(false);
				resultEmployee.setMessage(ex.getMessage().replaceAll("org.postgresql.util.PSQLException: ERROR:", ""));
				resultEmployee.saveEx();
			}
			else{
				throw new AdempiereException(ex.getMessage());	
			}			
		}
	}

	/***
	 * Obtiene suma de haberes para conceptos que gravan irpf, en liquidaciones de este periodo.
	 * OpenUp Ltda. Issue # 986
	 * @author Gabriel Vila - 12/06/2012
	 * @see
	 * @param employee
	 * @return
	 */
	private BigDecimal getSumDrIrpf(MBPartner employee, boolean incluyeBPS) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = Env.ZERO;
		BigDecimal valueDR = Env.ZERO;
		BigDecimal valueCR = Env.ZERO;
		
		try{
			MHRProcess process = new MHRProcess(getCtx(), this.getUY_HRProcess_ID(), null);
			MHRPeriod period = new MHRPeriod(getCtx(), process.getHR_Period_ID(), null);

			String whereBPS = (incluyeBPS) ? " and conc.grabado_bps='Y' " : "";
			
			sql = " select coalesce(sum(det.totalamt),0) as haberes " +
				  " from uy_hrresult res " +
				  " inner join uy_hrresultdetail det on res.uy_hrresult_id = det.uy_hrresult_id " +
				  " inner join hr_concept conc on det.hr_concept_id = conc.hr_concept_id " +
				  " inner join hr_concept_category cat on conc.hr_concept_category_id = cat.hr_concept_category_id " +
				  " inner join uy_hrprocesonomina pn on res.uy_hrprocesonomina_id = pn.uy_hrprocesonomina_id " +
				  " inner join uy_hrprocess pro on pn.uy_hrprocess_id = pro.uy_hrprocess_id " +
				  " inner join hr_period period on pro.hr_period_id = period.hr_period_id " +
				  " where period.c_period_id =? " +
				  " and res.c_bpartner_id =? " +
				  " and conc.grabado_irpf='Y' " + whereBPS +				  				  
				  " and cat.value='1001' and conc.accountsign='D'";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, period.getC_Period_ID());
			pstmt.setInt(2, employee.get_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				valueDR = rs.getBigDecimal(1);
			}
			
			sql = " select coalesce(sum(det.totalamt),0) as haberes " +
					  " from uy_hrresult res " +
					  " inner join uy_hrresultdetail det on res.uy_hrresult_id = det.uy_hrresult_id " +
					  " inner join hr_concept conc on det.hr_concept_id = conc.hr_concept_id " +
					  " inner join hr_concept_category cat on conc.hr_concept_category_id = cat.hr_concept_category_id " +
					  " inner join uy_hrprocesonomina pn on res.uy_hrprocesonomina_id = pn.uy_hrprocesonomina_id " +
					  " inner join uy_hrprocess pro on pn.uy_hrprocess_id = pro.uy_hrprocess_id " +
					  " inner join hr_period period on pro.hr_period_id = period.hr_period_id " +
					  " where period.c_period_id =? " +
					  " and res.c_bpartner_id =? " +
					  " and conc.grabado_irpf='Y' " + whereBPS +				  				  
					  " and cat.value='1001' and conc.accountsign='C'";
				
				pstmt = DB.prepareStatement (sql, null);
				pstmt.setInt(1, period.getC_Period_ID());
				pstmt.setInt(2, employee.get_ID());
				
				rs = pstmt.executeQuery ();
			
				if (rs.next()){
					valueCR = rs.getBigDecimal(1);
				}
				
				value = valueDR.subtract(valueCR).setScale(2, RoundingMode.HALF_UP);
			
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

	}

	/***
	 * Obtiene suma de descuentos para conceptos que gravan irpf, en liquidaciones de este periodo.
	 * OpenUp Ltda. Issue # 986
	 * @author Gabriel Vila - 02/07/2012
	 * @see
	 * @param employee
	 * @return
	 */
	private BigDecimal getSumCrIrpf(MBPartner employee, boolean incluyeBPS) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = Env.ZERO;
		
		try{
			MHRProcess process = new MHRProcess(getCtx(), this.getUY_HRProcess_ID(), null);
			MHRPeriod period = new MHRPeriod(getCtx(), process.getHR_Period_ID(), null);

			String whereBPS = (incluyeBPS) ? " and conc.grabado_bps='Y' " : "";
			
			sql = " select coalesce(sum(det.totalamt),0) as descuentos " +
				  " from uy_hrresult res " +
				  " inner join uy_hrresultdetail det on res.uy_hrresult_id = det.uy_hrresult_id " +
				  " inner join hr_concept conc on det.hr_concept_id = conc.hr_concept_id " +
				  " inner join hr_concept_category cat on conc.hr_concept_category_id = cat.hr_concept_category_id " +
				  " inner join uy_hrprocesonomina pn on res.uy_hrprocesonomina_id = pn.uy_hrprocesonomina_id " +
				  " inner join uy_hrprocess pro on pn.uy_hrprocess_id = pro.uy_hrprocess_id " +
				  " inner join hr_period period on pro.hr_period_id = period.hr_period_id " +
				  " where period.c_period_id =? " +
				  " and res.c_bpartner_id =? " +
				  " and conc.grabado_irpf='Y' " + whereBPS + 
				  " and cat.value='1002' ";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, period.getC_Period_ID());
			pstmt.setInt(2, employee.get_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = rs.getBigDecimal(1);
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

	}
	
	/***
	 * Obtiene suma de haberes para conceptos marcados para exclusion de regimen de retenciones.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 04/08/2015
	 * @see
	 * @param employee
	 * @return
	 */
	private BigDecimal getSumDrIrpfEx(MBPartner employee) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = Env.ZERO;
		BigDecimal valueDR = Env.ZERO;
		BigDecimal valueCR = Env.ZERO;
		
		try{
			MHRProcess process = new MHRProcess(getCtx(), this.getUY_HRProcess_ID(), null);
			MHRPeriod period = new MHRPeriod(getCtx(), process.getHR_Period_ID(), null);
		
			sql = " select coalesce(sum(det.totalamt),0) as haberes " +
				  " from uy_hrresult res " +
				  " inner join uy_hrresultdetail det on res.uy_hrresult_id = det.uy_hrresult_id " +
				  " inner join hr_concept conc on det.hr_concept_id = conc.hr_concept_id " +
				  " inner join hr_concept_category cat on conc.hr_concept_category_id = cat.hr_concept_category_id " +
				  " inner join uy_hrprocesonomina pn on res.uy_hrprocesonomina_id = pn.uy_hrprocesonomina_id " +
				  " inner join uy_hrprocess pro on pn.uy_hrprocess_id = pro.uy_hrprocess_id " +
				  " inner join hr_period period on pro.hr_period_id = period.hr_period_id " +
				  " where period.c_period_id =? " +
				  " and res.c_bpartner_id =? " +
				  " and conc.isexclude = 'Y' " +				  				  
				  " and cat.value='1001' and conc.accountsign='D'";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, period.getC_Period_ID());
			pstmt.setInt(2, employee.get_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				valueDR = rs.getBigDecimal(1);
			}
			
			sql = " select coalesce(sum(det.totalamt),0) as haberes " +
					  " from uy_hrresult res " +
					  " inner join uy_hrresultdetail det on res.uy_hrresult_id = det.uy_hrresult_id " +
					  " inner join hr_concept conc on det.hr_concept_id = conc.hr_concept_id " +
					  " inner join hr_concept_category cat on conc.hr_concept_category_id = cat.hr_concept_category_id " +
					  " inner join uy_hrprocesonomina pn on res.uy_hrprocesonomina_id = pn.uy_hrprocesonomina_id " +
					  " inner join uy_hrprocess pro on pn.uy_hrprocess_id = pro.uy_hrprocess_id " +
					  " inner join hr_period period on pro.hr_period_id = period.hr_period_id " +
					  " where period.c_period_id =? " +
					  " and res.c_bpartner_id =? " +
					  " and conc.isexclude = 'Y' " +			  				  
					  " and cat.value='1001' and conc.accountsign='C'";
				
				pstmt = DB.prepareStatement (sql, null);
				pstmt.setInt(1, period.getC_Period_ID());
				pstmt.setInt(2, employee.get_ID());
				
				rs = pstmt.executeQuery ();
			
				if (rs.next()){
					valueCR = rs.getBigDecimal(1);
				}
				
				value = valueDR.subtract(valueCR).setScale(2, RoundingMode.HALF_UP);
			
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

	}
	
	/***
	 * Obtiene suma de descuentos para conceptos marcados para exclusion de regimen de retenciones.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 04/08/2015
	 * @see
	 * @param employee
	 * @return
	 */
	private BigDecimal getSumCrIrpfEx(MBPartner employee) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = Env.ZERO;
		
		try{
			MHRProcess process = new MHRProcess(getCtx(), this.getUY_HRProcess_ID(), null);
			MHRPeriod period = new MHRPeriod(getCtx(), process.getHR_Period_ID(), null);
			
			sql = " select coalesce(sum(det.totalamt),0) as descuentos " +
				  " from uy_hrresult res " +
				  " inner join uy_hrresultdetail det on res.uy_hrresult_id = det.uy_hrresult_id " +
				  " inner join hr_concept conc on det.hr_concept_id = conc.hr_concept_id " +
				  " inner join hr_concept_category cat on conc.hr_concept_category_id = cat.hr_concept_category_id " +
				  " inner join uy_hrprocesonomina pn on res.uy_hrprocesonomina_id = pn.uy_hrprocesonomina_id " +
				  " inner join uy_hrprocess pro on pn.uy_hrprocess_id = pro.uy_hrprocess_id " +
				  " inner join hr_period period on pro.hr_period_id = period.hr_period_id " +
				  " where period.c_period_id =? " +
				  " and res.c_bpartner_id =? " +
				  " and conc.isexclude = 'Y' " +
				  " and cat.value='1002' ";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, period.getC_Period_ID());
			pstmt.setInt(2, employee.get_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = rs.getBigDecimal(1);
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

	}


	/**
	 * Obtiene y retorna conceptos, ordenados por secuencia, a considerar en el proceso. 
	 * OpenUp Ltda. Issue # 986
	 * @author Hp - 23/04/2012
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
	

	/**
	 * Obtiene y retorna lista de Empleados a procesar. Considera si tengo empleados en el filtro.
	 * OpenUp Ltda. Issue # 986 
	 * @author Hp - 23/04/2012
	 * @param reProcessing 
	 * @see
	 * @return List<MBPartner>. Lista de empleados.
	 */
	private List<MBPartner> getEmployees(boolean reProcessing) {
		
		MHRProcess process = new MHRProcess(getCtx(), this.getUY_HRProcess_ID(), get_TrxName()); //instancio liquidacion
		MHRPayroll payroll = new MHRPayroll(getCtx(), process.getHR_Payroll_ID(), get_TrxName()); //instancio tipo de liquidacion

		StringBuilder whereClause = new StringBuilder(X_C_BPartner.COLUMNNAME_IsEmployee + "='Y' AND " + X_C_BPartner.COLUMNNAME_nrolegajo + " <> '' AND " + X_C_BPartner.COLUMNNAME_IsHr + "='Y'");
		
		// Si es reproceso, solo considero los marcados para reprocesar
		whereClause.append((reProcessing) ? " AND c_bpartner_id in (select c_bpartner_id " +
				" from uy_hrresult where UY_HRProcesoNomina_ID=" + this.get_ID() +
				" and reprocessing='Y')" : "");
		
		if (this.haveEmployeeFilter())
			whereClause.append(" AND " + X_C_BPartner.COLUMNNAME_C_BPartner_ID + " IN (" +
					" SELECT pemp.c_bpartner_id " +
					" FROM " + X_UY_HRProcesoNomEmpleados.Table_Name + " pemp " +
					" WHERE pemp.uy_hrprocesonomina_id =" + this.get_ID() + ")");
		
		//si el tipo de liquidacion esta marcado para procesar solamente 
		//empleados que tienen novedades de nomina para dicha liquidacion
		if(payroll.issoloNovedades()){
			whereClause.append(" AND " + X_C_BPartner.COLUMNNAME_C_BPartner_ID + " IN (" + 
		            " SELECT e.c_bpartner_id " + 
                    " FROM " +X_C_BPartner.Table_Name + " e " + 
                    " INNER JOIN " + X_UY_HRNovedades.Table_Name + " n " +  " ON e.c_bpartner_id=n.c_bpartner_id " +
                    " INNER JOIN " + X_UY_HRProcess.Table_Name + " pr " + " ON n.uy_hrprocess_id=pr.uy_hrprocess_id " +
                    " INNER JOIN " + X_HR_Payroll.Table_Name + " pay " + " ON pr.hr_payroll_id=pay.hr_payroll_id " +
                    " WHERE pr.uy_hrprocess_id=" + this.getUY_HRProcess_ID() + ")");
			
		}
		
		List<MBPartner> employees = new Query(getCtx(), I_C_BPartner.Table_Name, whereClause.toString(), null)
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.list();
		
		//verifico que se haya especificado al menos 1 empleado si el tipo de liquidacion asi lo requiere
		if(payroll.isSelectRequired()){
			
			String sql = "SELECT count(c_bpartner_id)" +
						 " FROM uy_hrprocesonomempleados emp" +
					     " WHERE uy_hrprocesonomina_id=" + this.getUY_HRProcesoNomina_ID();
			int cant = DB.getSQLValueEx(get_TrxName(), sql);
			
			if(cant == 0) throw new AdempiereException ("Debe seleccionar empleados para procesar esta liquidacion");
						
			
		}
		
	
		return employees;
	}

	/**
	 * Verifica si hay filtro de empleados o no.
	 * OpenUp Ltda. Issue # 986
	 * @author Hp - 23/04/2012
	 * @see
	 * @return boolean. True si hay, false sino.
	 */
	private boolean haveEmployeeFilter() {

		String whereClause = X_UY_HRProcesoNomEmpleados.COLUMNNAME_UY_HRProcesoNomina_ID + "=?";
		int count = new Query(getCtx(), X_UY_HRProcesoNomEmpleados.Table_Name, whereClause, null)
		.setParameters(this.get_ID())
		.setOnlyActiveRecords(true)
		.count();
		
		return (count > 0);

	}

	/**
	 * Elimina resultados anteriores de esta liquidacion
	 * OpenUp Ltda. Issue # 986
	 * @author Hp - 25/04/2012
	 * @see
	 */
	private void deleteOldData(boolean reProcessing) {
		
		try{
			
			String whereProcessing = "";
			
			if (reProcessing){
				whereProcessing = " AND reprocessing ='Y' ";
			}
							
			DB.executeUpdateEx("DELETE FROM UY_HRResultDetail WHERE UY_HRResult_ID IN " +
					           "(SELECT UY_HRResult_ID FROM UY_HRResult " +
					           " WHERE UY_HRProcesoNomina_ID=" + this.get_ID() + whereProcessing + ")", null);

			DB.executeUpdateEx("DELETE FROM UY_HRResult WHERE UY_HRProcesoNomina_ID=" + this.get_ID() + whereProcessing, null);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
	}
	
	/***
	 * Obtiene y retorna una liquidacion de nomina segun liquidacion recibida.
	 * OpenUp Ltda. Issue #3237 
	 * @author Nicolas Sarlabos - 01/04/2015
	 */
	public static MHRProcesoNomina forProcess(Properties ctx, int processID, String trxName){
		
		String whereClause = X_UY_HRProcesoNomina.COLUMNNAME_UY_HRProcess_ID + "=" + processID;
		
		MHRProcesoNomina p = new Query(ctx, I_UY_HRProcesoNomina.Table_Name, whereClause, trxName)
		.first();
				
		return p;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
				
		String sql = "";
		
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
				
				sql = "select count(uy_hrresult_id) from uy_hrresult where success='Y' and uy_hrprocesonomina_id = " + this.get_ID();
				int cantProcessed = DB.getSQLValueEx(get_TrxName(), sql);
				
				sql = "select count(uy_hrresult_id) from uy_hrresult where success='N' and uy_hrprocesonomina_id = " + this.get_ID();
				int cantNotProcessed = DB.getSQLValueEx(get_TrxName(), sql);
				
				if(cantProcessed < 1 || cantNotProcessed > 0){
					
					throw new AdempiereException ("Imposible completar, compruebe empleados procesados y no procesados");
					
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
	
	/**
	 * Obtiene valor de viatico extranjero o nacional desde parametros de nomina.
	 * OpenUp Ltda. Issue # 1759.
	 * @author Nicolas Sarlabos - 05/05/2014
	 * @see
	 */
	public BigDecimal getViaticoAmt(boolean nacional){

		BigDecimal value = Env.ZERO;
		String tipo = "";
		
		if(nacional){
			
			tipo = "viaticonacional";
			
		} else tipo = "viaticoextranjero";
		
		String sql = "select coalesce(" + tipo + ",0)" +
		             " from uy_hrparametros" +
				     " where ad_client_id = " + this.getAD_Client_ID() +
				     " and ad_org_id = " + this.getAD_Org_ID();
		value = DB.getSQLValueBDEx(get_TrxName(), sql);		
				
		return value;
	}
	
	/**
	 * Obtiene importe de deduccion de viatico extranjero desde parametros de nomina.
	 * OpenUp Ltda. Issue # 1759.
	 * @author Nicolas Sarlabos - 05/05/2014
	 * @see
	 */
	public BigDecimal getDeduccionViatExt(){

		BigDecimal value = Env.ZERO;
		
		String sql = "select coalesce(deduccionviatext,0)" +
		             " from uy_hrparametros" +
				     " where ad_client_id = " + this.getAD_Client_ID() +
				     " and ad_org_id = " + this.getAD_Org_ID();
		value = DB.getSQLValueBDEx(get_TrxName(), sql);		
				
		return value;
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
		FactLine.deleteFact(X_UY_HRProcesoNomina.Table_ID, this.get_ID(), get_TrxName());
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
	
}
