/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
package org.eevolution.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.openup.model.MHRCalculos;
import org.openup.model.MHRPatronalesDetail;
import org.openup.model.MHRPatronalesLine;
import org.openup.model.MHRResultDetail;

/**
 *	Payroll Concept for HRayroll Module
 *	
 *  @author Oscar Gómez Islas
 *  @version $Id: HRPayroll.java,v 1.0 2005/10/05 ogomezi
 *  
 *  @author Cristina Ghita, www.arhipac.ro
 */
public class MHRConcept extends X_HR_Concept
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7859469065116713767L;
	
	/** Cache */
	private static CCache<Integer, MHRConcept> s_cache = new CCache<Integer, MHRConcept>(Table_Name, 100);
	/** Cache by Value */
	private static CCache<String, MHRConcept> s_cacheValue = new CCache<String, MHRConcept>(Table_Name+"_Value", 100);
	
	public static MHRConcept get(Properties ctx, int HR_Concept_ID)
	{
		if (HR_Concept_ID <= 0)
			return null;
		//
		MHRConcept concept = s_cache.get(HR_Concept_ID);
		if (concept != null)
			return concept;
		//
		concept = new MHRConcept(ctx, HR_Concept_ID, null);
		if (concept.get_ID() == HR_Concept_ID)
		{
			s_cache.put(HR_Concept_ID, concept);
		}
		else
		{
			concept = null;	
		}
		return concept; 
	}

	/**
	 * Get Concept by Value
	 * @param ctx
	 * @param value
	 * @return
	 */
	public static MHRConcept forValue(Properties ctx, String value)
	{
		// OpenUp. Gabriel Vila. 15/06/2012. Issue #986.
		// La logica de este metodo se copio a nuevo metodo de igual nombre pero que recibe transaccion.
		return MHRConcept.forValue(ctx, value, null);
		
		/*
		if (Util.isEmpty(value, true))
		{
			return null;
		}
		
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final String key = AD_Client_ID+"#"+value;
		MHRConcept concept = s_cacheValue.get(key);
		if (concept != null)
		{
			return concept;
		}
		
		final String whereClause = COLUMNNAME_Value+"=? AND AD_Client_ID IN (?,?)"; 
		concept = new Query(ctx, Table_Name, whereClause, null)
							.setParameters(new Object[]{value, 0, AD_Client_ID})
							.setOnlyActiveRecords(true)
							.setOrderBy("AD_Client_ID DESC")
							.first();
		if (concept != null)
		{
			s_cacheValue.put(key, concept);
			s_cache.put(concept.get_ID(), concept);
		}
		return concept;
		*/
		// Fin OpenUp
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//validacion para campo "Tipo de Explosion"
		if (this.isexplosion()){
			
			if (this.getUY_HRTipoExplosion_ID() <= 0) throw new AdempiereException("Debe seleccionar un tipo de explosion");
		}
		
		if(this.isViaticoNacional() && this.isViaticoExtranjero()) throw new AdempiereException("El concepto no puede ser viatico nacional y extranjero a la vez");
		
		if(this.isTransport() && this.isTransportLine()) throw new AdempiereException("El concepto no puede ser padre e hijo a la vez en un cuadro de codigos de transporte");
		
		return true;
	}

	/***
	 * Obtiene modelo de concepto segun value recibido.
	 * OpenUp Ltda. Issue #986 
	 * @author Nico - 15/06/2012
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MHRConcept forValue(Properties ctx, String value, String trxName)
	{
		if (Util.isEmpty(value, true))
		{
			return null;
		}
		
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final String key = AD_Client_ID+"#"+value;
		MHRConcept concept = s_cacheValue.get(key);
		if (concept != null)
		{
			return concept;
		}
		
		final String whereClause = COLUMNNAME_Value+"=? AND AD_Client_ID IN (?,?)"; 
		concept = new Query(ctx, Table_Name, whereClause, trxName)
							.setParameters(new Object[]{value, 0, AD_Client_ID})
							.setOnlyActiveRecords(true)
							.setOrderBy("AD_Client_ID DESC")
							.first();
		if (concept != null)
		{
			s_cacheValue.put(key, concept);
			s_cache.put(concept.get_ID(), concept);
		}
		return concept;
	}

	
	/**
	 * 	Get Employee's of Payroll Type
	 *  @param payroll_id Payroll ID
	 *  @param department_id Department ID
	 *  @param employee_id Employee_ID
	 * 	@param sqlwhere Clause SQLWhere
	 * 	@return lines
	 */
	public static MHRConcept[] getConcepts (int payroll_id, int department_id, String sqlWhere)
	{
		Properties ctx = Env.getCtx();
		List<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();
		
		whereClause.append("AD_Client_ID in (?,?)");   
		params.add(0);
		params.add(Env.getAD_Client_ID(Env.getCtx()));
		
		whereClause.append(" AND (" + COLUMNNAME_HR_Payroll_ID + " =? OR "
				+COLUMNNAME_HR_Payroll_ID +" IS NULL)");
		params.add(payroll_id);
		
		if (department_id != 0 )
		{
			whereClause.append(" AND HR_Concept.HR_Department_ID=?");
			params.add(department_id);
		}
		
		if (!Util.isEmpty(sqlWhere))
		{
			whereClause.append(sqlWhere);
		}
		
		List<MHRConcept> list = new Query(ctx, Table_Name, whereClause.toString(), null)
										.setParameters(params)
										.setOnlyActiveRecords(true)
										.setOrderBy("COALESCE("+COLUMNNAME_SeqNo + ",999999999999) DESC, " + COLUMNNAME_Value)
										.list();
		return list.toArray(new MHRConcept[list.size()]);
	}	//	getConcept	

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param HR_Concept_ID
	 *	@param trxName
	 */
	public MHRConcept (Properties ctx, int HR_Concept_ID, String trxName)
	{
		super (ctx, HR_Concept_ID, trxName);
		if (HR_Concept_ID == 0)
		{
			setValue("");
			setName("");
			setDescription("");
			setIsEmployee(false);
			setIsPrinted(false);
			setHR_Payroll_ID(0);
			setHR_Job_ID(0);
			setHR_Department_ID(0);
		}		
	}	//	HRConcept

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName
	 */
	public MHRConcept (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public int getConceptAccountCR() 
	{
		String sql = " HR_Expense_Acct FROM HR_Concept c " +
		" INNER JOIN HR_Concept_Acct ca ON (c.HR_Concept_ID=ca.HR_Concept_ID)" +
		" WHERE c.HR_Concept_ID " + getHR_Concept_ID();
		int result = DB.getSQLValue("ConceptCR", sql);
		if (result > 0)
			return result;
		return 0;
	}

	public int getConceptAccountDR() 
	{
		String sql = " HR_Revenue_Acct FROM HR_Concept c " +
		" INNER JOIN HR_Concept_Acct ca ON (c.HR_Concept_ID=ca.HR_Concept_ID)" +
		" WHERE c.HR_Concept_ID " + getHR_Concept_ID();
		int result = DB.getSQLValue("ConceptCR", sql);
		if (result > 0)
			return result;
		return 0;
	}
	
	/**
	 *	Return Value + Name
	 *  @return Value
	 */
	public String toString()
	{
		return getValue() + " - " + getName();
	}   //  toString


	/**
	 * Calcula el monto de un determinado concepto para un determinado empleado.
	 * OpenUp Ltda. Issue # 986
	 * @author Hp - 23/04/2012
	 * @see
	 * @param employee
	 * @param calculatedConcepts
	 * @param globalVars 
	 * @param hrResultID 
	 * @param sumDrIrpf 
	 * @param sumCrIrpf 
	 * @param sumDrBPS 
	 * @param sumCrBPS 
	 * @param sumCrIrpfBPS 
	 * @param sumDrIrpfBPS 
	 * @return
	 */
	public BigDecimal calculate(MBPartner employee, HashMap<Integer, BigDecimal> calculatedConcepts, 
							    HashMap<String, Object> globalVars, int hrProcessID, int hrResultID, 
							    BigDecimal sumDrIrpf, BigDecimal sumCrIrp, BigDecimal sumDrBPS, BigDecimal sumCrBPS, 
							    BigDecimal sumDrIrpfBPS, BigDecimal sumCrIrpfBPS, BigDecimal sumDrIrpfEx, BigDecimal sumCrIrpfEx){
		
		BigDecimal value = Env.ZERO;
		long startTime = 0;
		MHRResultDetail resultDetail = null;
		
		// Me busco a mi mismo en el hash de conceptos calculados y si ya existo alli
		// devuelvo el valor calculado y salgo sin hacer mas nada
		if (calculatedConcepts.containsKey((Integer)this.get_ID())){
			//return calculatedConcepts.get((Integer)this.get_ID());
			return Env.ZERO;
		}
		
		try{

			// Instancio modelo de detalle resultado para este concepto y lo asocio al resultado del empleado
			resultDetail = new MHRResultDetail(getCtx(), 0, null);
			resultDetail.setUY_HRResult_ID(hrResultID);
			resultDetail.setHR_Concept_ID(this.get_ID());
			resultDetail.setType(this.getType());
			resultDetail.setAccountSign(this.getAccountSign());
			resultDetail.setColumnType(this.getColumnType());
			resultDetail.setSeqNo(this.getSeqNo());

			/*
			// Si este concepto no explota en otros conceptos hijos, lo guardo como resultado
			// De lo contrario se guardan solamente sus conceptos hijos como resultado
			if (!this.isexplosion()){
				resultDetail.saveEx();   // Guardo modelo para tener su ID	
			}
			*/

			// El calculo depende del tipo de concepto:
			// C : Concepto = Tiene calculo asociado
			// I : Informacion = Se ingresa por novedades
			// E : Regla = Tiene calculo pero se utiliza como parte del calculo de un concepto padre
			if (this.getType().equalsIgnoreCase(TYPE_Information)){
				startTime = System.currentTimeMillis();
				value = this.calculateNews(employee, hrProcessID);
				resultDetail.setWorkingTime(new Time(System.currentTimeMillis() - startTime).toString().substring(3));
			}
			else if (this.getType().equalsIgnoreCase(TYPE_Concept) 
				  || this.getType().equalsIgnoreCase(TYPE_RuleEngine)){
			
				// Obtengo formula a ejecutar desde base, si no tengo aviso con error
				int hrCalculoID = DB.getSQLValueEx(null, "SELECT UY_HRCalculos_ID FROM UY_HRCalculos WHERE HR_Concept_ID=?", this.get_ID()); 
				MHRCalculos calculos = new MHRCalculos(getCtx(), hrCalculoID, null);
				if (calculos.get_ID() <= 0){
					throw new Exception("El concepto " + this.getValue() + " no tiene definido el Calculo.");
				}
				
				String formulaDirty = calculos.getcodigo();

				if ((formulaDirty == null) || (formulaDirty.equalsIgnoreCase(""))){
					throw new Exception("El concepto " + this.getValue() + " no tiene definido el Calculo.");
				}

				resultDetail.setUY_HRCalculos_ID(hrCalculoID);
				
				// Limpio formula quitandole subformulas y variables globales
				String formulaClean = this.cleanFormula(formulaDirty, calculatedConcepts, globalVars, employee, 
														hrProcessID, hrResultID, sumDrIrpf, sumCrIrp, 
														sumDrBPS, sumCrBPS, sumDrIrpfBPS, sumCrIrpfBPS, sumDrIrpfEx, sumCrIrpfEx);
				
				resultDetail.setcodigo(formulaClean);
				
				startTime = System.currentTimeMillis();
				
				// Ejecuto formula asumiendo que es una select que solo retorna un valor numerico
				value = DB.getSQLValueBDEx(null, formulaClean);
				if (value == null) value = Env.ZERO;
				
				resultDetail.setWorkingTime(new Time(System.currentTimeMillis() - startTime).toString().substring(3));
				
			}
			// Si este concepto no tiene correctamente parametrizado el TYPE, aviso con error.
			else {
				throw new Exception("El concepto " + this.getValue() + " tiene un valor de TIPO no esperado para el Proceso de Liquidacion.");
			}		
						
			// Guardo resultado de este concepto en la lista de conceptos calculados
			calculatedConcepts.put((Integer)this.get_ID(), value);
			 
			// Si este concepto explota en otros, o sea es solo para hacer que otros conceptos dinamicos
			// se ejecuten (solo guardo el resultado de los que explotan y no de este concepto padre)
			if (this.isexplosion()){
				return this.calculateExplotion(employee, calculatedConcepts, hrProcessID, hrResultID);
			}
			else{
		
				// Guardo resultado exitoso de este concepto.
				resultDetail.setTotalAmt(value);
				resultDetail.setSuccess(true);
				resultDetail.saveEx();
			}
			
		}
		catch (Exception ex){
			if (resultDetail != null){
				// Guardo resultado de concepto como no exitoso
				resultDetail.setSuccess(false);
				resultDetail.setMessage(ex.getMessage().replaceAll("org.postgresql.util.PSQLException: ERROR:", ""));
				resultDetail.saveEx();
			}
			throw new AdempiereException(ex.getMessage());
		}
		
		if (value == null) value = Env.ZERO;
		value = value.setScale(2, RoundingMode.HALF_UP);
		
		return value;
		
	}

	/**
	 * Dado un concepto marcado como de tipo explosion, tengo que ejecutar este concepto padre, para 
	 * que se ejecuten a su vez los conceptos hijos y se den de alta en una tabla auxiliar.
	 * De esta manera en este metodo, se cargan los conceptos hijos ya calculados y se los agrega
	 * como detalle en el resultado de este proceso de liquidacion.
	 * OpenUp Ltda. Issue #986 
	 * @author Gabriel Vila - 05/07/2012
	 * @see
	 * @param employee
	 * @param calculatedConcepts
	 * @param globalVars
	 * @param hrProcessID
	 * @param hrResultID
	 * @param sumDrIrpfIncBpc
	 * @param sumDrIrpfNoIncBpc
	 * @param sumDrBPS
	 * @param sumCrBPS
	 * @param sumCrIrpfIncBpc
	 * @param sumCrIrpfNoIncBpc
	 * @return
	 */
	private BigDecimal calculateExplotion(MBPartner employee, HashMap<Integer, BigDecimal> calculatedConcepts,
										  int hrProcessID, int hrResultID) {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal sumAmt = Env.ZERO;
		
		try{
			sql =" select hr_concept_id, coalesce(amtacctdr,0) as amtacctdr, " +
				 " coalesce(amtacctcr,0) as amtacctcr " +
				 " from uy_hrprocesoliqexplosion " +
				 " where uy_hrprocess_id =? " +
				 " and c_bpartner_id =? " +
				 " and uy_hrtipoexplosion_id =? ";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, hrProcessID);
			pstmt.setInt(2, employee.get_ID());
			pstmt.setInt(3, this.getUY_HRTipoExplosion_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				int childConceptID = rs.getInt("hr_concept_id");
				MHRConcept childConcept = new MHRConcept(getCtx(), childConceptID, null);
				if (childConcept.get_ID() <= 0){
					throw new Exception("No se pudo obtener concepto con ID = " + childConceptID);
				}

				// El concepto hijo debe ser del tipo informacion
				/*if (!childConcept.getType().equalsIgnoreCase(TYPE_Information)){
					throw new Exception("El concepto " + childConcept.getValue() + " tiene un valor de TIPO distinto a Informacion.");
				}*/

				
				// Instancio modelo de detalle resultado para este concepto y lo asocio al resultado del empleado
				MHRResultDetail resultDetail = new MHRResultDetail(getCtx(), 0, null);
				resultDetail.setUY_HRResult_ID(hrResultID);
				resultDetail.setHR_Concept_ID(childConcept.get_ID());
				resultDetail.setType(childConcept.getType());
				resultDetail.setAccountSign(childConcept.getAccountSign());
				resultDetail.setColumnType(childConcept.getColumnType());
				resultDetail.setSeqNo(childConcept.getSeqNo());
				long startTime = System.currentTimeMillis();

				BigDecimal value = Env.ZERO;
				if (childConcept.getAccountSign().equalsIgnoreCase(X_HR_Concept.ACCOUNTSIGN_Debit)){
					value = rs.getBigDecimal("amtacctdr");
				}
				else if (childConcept.getAccountSign().equalsIgnoreCase(X_HR_Concept.ACCOUNTSIGN_Credit)){
					value = rs.getBigDecimal("amtacctcr");
				}
				
				resultDetail.setWorkingTime(new Time(System.currentTimeMillis() - startTime).toString().substring(3));
				
				value = value.setScale(2, RoundingMode.HALF_UP);
				
				//verifico que el valor total no supere el limite maximo del concepto
				if(this.getlimite()>0){
					
					BigDecimal limite = new BigDecimal(this.getlimite()); 
					if(value.compareTo(limite)>0){
						value = limite;
					}
										
				}

				// Guardo resultado de este concepto en la lista de conceptos calculados
				calculatedConcepts.put((Integer)childConcept.get_ID(), value);

				resultDetail.setTotalAmt(value);
				resultDetail.setSuccess(true);
				resultDetail.saveEx();
				
				sumAmt = sumAmt.add(value);
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
		
		return sumAmt;
		
	}

	/**
	 * Calcula suma de valores de novedades para este concepto.
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 24/04/2012
	 * @see
	 * @param employee
	 * @param hrProcessID 
	 * @return
	 */
	private BigDecimal calculateNews(MBPartner employee, int hrProcessID) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = Env.ZERO;
		
		try{
			sql =" select coalesce(line.amount,0) as amount, coalesce(line.qty,0) as qty, coalesce(line.porcentaje,0) as porcentaje " +
					 " from uy_hrnovedades hdr " +
					 " inner join uy_hrconceptoline line on hdr.uy_hrnovedades_id = line.uy_hrnovedades_id " +
					 " where hdr.uy_hrprocess_id =? " +
					 " and hdr.c_bpartner_id =? " +
					 " and line.hr_concept_id =? ";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, hrProcessID);
			pstmt.setInt(2, employee.get_ID());
			pstmt.setInt(3, this.get_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				
				// El resultado de este concepto depende de su tipo de columna
				if (this.getColumnType().equalsIgnoreCase(COLUMNTYPE_Amount)){
					value = rs.getBigDecimal("amount");
				}
				else if (this.getColumnType().equalsIgnoreCase(COLUMNTYPE_Quantity)){
					value = rs.getBigDecimal("qty");
				}
				else if (this.getColumnType().equalsIgnoreCase(COLUMNTYPE_Porcentaje)){
					value = rs.getBigDecimal("porcentaje");
				}
			}
			
			//verifico que el valor total no supere el limite maximo del concepto
			if(this.getlimite()>0){
				
				BigDecimal limite = new BigDecimal(this.getlimite()); 
				if(value.compareTo(limite)>0){
					value = limite;
				}
									
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
	 * Limpia formula de calculo reemplazando variables globales y sub-conceptos por sus valores calculados.
	 * OpenUp Ltda. Issue # 986 
	 * @author Hp - 24/04/2012
	 * @see
	 * @param formulaDirty
	 * @param calculatedConcepts
	 * @param globalVars
	 * @param employee
	 * @param hrProcessID 
	 * @param hrResultID 
	 * @param sumBPS 
	 * @param sumDrIrpf 
	 * @param sumCrIrpf 
	 * @param sumDrBPS 
	 * @param sumCrBPS 
	 * @param sumCrIrpfBPS 
	 * @param sumDrIrpfBPS 
	 * @return
	 */
	private String cleanFormula(String formulaDirty, HashMap<Integer, BigDecimal> calculatedConcepts, 
							    HashMap<String, Object> globalVars, MBPartner employee, 
							    int hrProcessID, int hrResultID, 
							    BigDecimal sumDrIrpf, BigDecimal sumCrIrpf, BigDecimal sumDrBPS, BigDecimal sumCrBPS, 
							    BigDecimal sumDrIrpfBPS, BigDecimal sumCrIrpfBPS, BigDecimal sumDrIrpfEx, BigDecimal sumCrIrpfEx) {

		String value = formulaDirty;

		// Seteo valores de variables globales que hayan en esta formula
		Map<String, Object> map = globalVars; 
		for (Map.Entry<String, Object> entry : map.entrySet()) { 
			value = value.replaceAll("@" + entry.getKey() + "@", entry.getValue().toString());
		}		

		// Busco y reemplazo tags para sumas de conceptos gravados irpf, y gravados BPS
		value = value.replaceAll("@SUM:IRPF@", (sumDrIrpf.subtract(sumCrIrpf).setScale(2, RoundingMode.HALF_UP)).toString());
		value = value.replaceAll("@SUM:IRPF-BPS@", (sumDrIrpfBPS.subtract(sumCrIrpfBPS).setScale(2, RoundingMode.HALF_UP)).toString());
		value = value.replaceAll("@SUM:DR-BPS@", ((sumDrBPS).setScale(2, RoundingMode.HALF_UP)).toString());
		value = value.replaceAll("@SUM:CR-BPS@", ((sumCrBPS).setScale(2, RoundingMode.HALF_UP)).toString());
		value = value.replaceAll("@SUM:IRPF-EX@", (sumDrIrpfEx.subtract(sumCrIrpfEx).setScale(2, RoundingMode.HALF_UP)).toString());
		
		// Busco, proceso y reemplazo valores de  sub-formulas
		boolean finLoop = false; 
		int start = -1; 
		int end = -1;
		while (!finLoop){

			start = value.indexOf("@HRC:");
			if (start < 0) {
				finLoop = true;
				continue;
			}
			
			end = value.indexOf("@", start + 1);
			if (end < 0) {
				throw new AdempiereException("Error en formula del concepto " + this.getValue() + ". Hay un concepto dentro de la formula que no tiene el formato @HRC:codigo@.");
			}

			// Obtengo value del subconcepto de esta formula
			String subConceptValue = value.substring(start + 5, end);
			// Obtengo concepto para este value y valido que exista
			MHRConcept subConcept = MHRConcept.forValue(getCtx(), subConceptValue, null);
			if (subConcept == null){
				throw new AdempiereException("Error en formula del concepto " + this.getValue() + ". Contiene un sub-concepto invalido (" + subConceptValue + ").");
			}

			// Secuencia del subconcepto sera igual a mi secuencia
			subConcept.setSeqNo(this.getSeqNo());
			
			// Si este subconcepto ya esta calculado, sustituyo valor en esta formula
			BigDecimal amount = Env.ZERO;
			if (calculatedConcepts.containsKey((Integer)subConcept.get_ID())){
				amount = calculatedConcepts.get((Integer)subConcept.get_ID());
			}
			else{
				// Calculo este subconcepto y sustituyo su resultado en la formula
				amount = subConcept.calculate(employee, calculatedConcepts, globalVars, hrProcessID, 
											  hrResultID, sumDrIrpf, sumCrIrpf, sumDrBPS, sumCrBPS, 
											  sumDrIrpfBPS, sumCrIrpfBPS, sumDrIrpfEx, sumCrIrpfEx);
			}
			value = value.replaceAll("@HRC:" + subConceptValue + "@", amount.toString());
		}
		
		return value;
	}
	
	/**
	 * Metodo que obtiene y devuelve el concepto de redondeo
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 31/07/2012
	 * @see
	 * @return
	 */
	
	public static MHRConcept getConceptRounding(Properties ctx,String trxName){
		
		MHRConcept concept = null;
		String sql = "";
		int concept_ID = 0;
		
		sql = "SELECT hr_concept_id FROM hr_concept WHERE isrounding='Y'";
		concept_ID = DB.getSQLValueEx(null, sql);
		
		if(concept_ID > 0) concept = new MHRConcept(ctx,concept_ID,trxName);
					
		return concept;
					
	}
	
	/**
	 * Metodo que devuelve el ID de categoria de conceptos de acuerdo al value recibido por parametro
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 02/08/2012
	 * @see
	 * @param trxName
	 * @return
	 */
	
	public static int getConceptCategoryID(String trxName,String value){
		
		int concept_id = 0;
		String sql = "";
		
		sql = "SELECT coalesce(hr_concept_category_id,0) FROM hr_concept_category WHERE value=" + "'" + value + "'";
		concept_id = DB.getSQLValueEx(trxName, sql);
				
		return concept_id;
		
	}

	/**
	 * Calcula el monto de un determinado concepto (metodo utilizado para calculo de aportes patronales)
	 * OpenUp Ltda. Issue #1059
	 * @author Nicolas Sarlabos - 03/07/2013
	 * @see
	 * @param trxName
	 * @return
	 */
	
	public void calculate(MHRPatronalesDetail detail, HashMap<String, Object> globalVars) {

		int lineID = 0;
		
		try {

			// Obtengo formula a ejecutar desde base, si no tengo aviso con error
			int hrCalculoID = DB.getSQLValueEx(null, "SELECT UY_HRCalculos_ID FROM UY_HRCalculos WHERE HR_Concept_ID=?", this.get_ID()); 
			MHRCalculos calculos = new MHRCalculos(getCtx(), hrCalculoID, null);
			
			if (calculos.get_ID() <= 0) throw new Exception("El concepto " + this.getValue() + " no tiene definido el Calculo.");
			
			String formula = calculos.getcodigo();
			
			if ((formula == null) || (formula.equalsIgnoreCase(""))){
				throw new Exception("El concepto " + this.getValue() + " no tiene definido el Calculo.");
			}
			
			globalVars.put("patronalesdetail", new Integer(detail.get_ID()));
		
			// Seteo valores de variables globales que hayan en esta formula
			Map<String, Object> map = globalVars; 
			for (Map.Entry<String, Object> entry : map.entrySet()) { 
				formula = formula.replaceAll("@" + entry.getKey() + "@", entry.getValue().toString());
			}
			
			// Ejecuto formula asumiendo que es una select que solo retorna un valor numerico
			//obtengo el ID de la linea que se inserta al ejecutar la formula, para luego actualizar algunos de sus atributos
			lineID = DB.getSQLValueEx(null, formula);

			if(lineID > 0) {
				MHRPatronalesLine line = new MHRPatronalesLine (getCtx(),lineID,null);
				line.setHR_Concept_ID(this.get_ID());
				line.setDescription(this.getName());
				line.saveEx();
			}


		} catch (Exception ex){

				throw new AdempiereException(ex.getMessage());
			}

		
		}
	
	/***
	 * Retorna lista de conceptos transporte padre.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 20/01/2014
	 * @see
	 * @return
	 */
	public static List<MHRConcept> getTransportConcept(){
		
		String whereClause = X_HR_Concept.COLUMNNAME_IsTransport + " = 'Y' and " + X_HR_Concept.COLUMNNAME_IsActive + " = 'Y'";
		
		List<MHRConcept> lines = new Query(Env.getCtx(), I_HR_Concept.Table_Name, whereClause, null).setOrderBy(X_HR_Concept.COLUMNNAME_Value).list();
		
		return lines;
		
	}
	
}	//	HRConcept