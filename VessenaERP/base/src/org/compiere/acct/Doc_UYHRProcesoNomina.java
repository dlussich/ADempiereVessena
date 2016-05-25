/**
 * 
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaDefault;
import org.compiere.model.MActivity;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MHRProcesoNomina;

/**
 * @author Nicolas
 *
 */
public class Doc_UYHRProcesoNomina extends Doc {
	
	private MHRProcesoNomina proceso = null;
	private boolean conCentroCosto = true;

	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYHRProcesoNomina(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(ass, clazz, rs, defaultDocumentType, trxName);
	}

	/**
	 * Constructor
	 * @param ass
	 *            accounting schemata
	 * @param rs
	 *            record
	 * @param trxName
	 *            trx
	 */
	public Doc_UYHRProcesoNomina(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MHRProcesoNomina.class, rs, null, trxName);
	}

	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MHRProcesoNomina) getPO()).getDateAcct());
		return null;
	}

	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		
		Fact fact = new Fact(this, as, Fact.POST_Actual);
				
		// Modelo desde PO
		this.proceso = (MHRProcesoNomina)this.getPO();	

		// Eschema contable
		MClient client = new MClient(getCtx(), this.proceso.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		MBPartner [] partners = this.getPartners(); //obtengo empleados de la liquidacion actual

		if(partners.length > 0) {

			this.conCentroCosto = this.checkCentroCosto(partners[0].get_ID()); //verifico si se utilizan centros de costo en los empleados

			if (this.conCentroCosto) {

				fact = createFactConCCostos(fact,partners,schema);

			} else fact = createFactSinCCostos(fact,schema);	

		}
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;	

	}
	
	/***
	 * Obtiene y retorna empleados de liquidacion actual
	 * OpenUp Ltda. Issue #1058
	 * @author Nicolas Sarlabos - 27/06/2013
	 * @see
	 * @return
	 */
	private MBPartner[] getPartners(){
		
		List <MBPartner> list = new ArrayList <MBPartner>();
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			sql = "select c_bpartner_id from uy_hrresult where uy_hrprocesonomina_id = " + proceso.get_ID();
			
			pstmt = DB.prepareStatement (sql,getTrxName());
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				MBPartner p = new MBPartner(getCtx(), rs.getInt("c_bpartner_id"), getTrxName());
				list.add(p);
			}			
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}	

		return list.toArray(new MBPartner[list.size()]);
	}
	
	/***
	 * Recibe un ID de empleado y devuelve true si el empleado posee centro de costos
	 * OpenUp Ltda. Issue #1058
	 * @author Nicolas Sarlabos - 27/06/2013
	 * @see
	 * @return
	 */
	private boolean checkCentroCosto(int partnerID) {
		
		String sql = "select c_activity_id from hr_employee where c_bpartner_id = " + partnerID;
		
		int id = DB.getSQLValueEx(getTrxName(), sql);
		
		if(id > 0) {
			
			return true;
			
		} else return false;		
		
	}
	
	/***
	 * Metodo que crea el asiento contable de liquidacion SIN centros de costo
	 * OpenUp Ltda. Issue #1058
	 * @author Nicolas Sarlabos - 27/06/2013
	 * @see
	 * @return
	 */	
	private Fact createFactSinCCostos (Fact fact, MAcctSchema schema) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		FactLine fl = null;
		
		
		try {
			//obtengo c_elementvalue_id de los conceptos involucrados en esta liquidacion, existira 1 linea de asiento por cada cuenta contable
			sql = "select distinct hr_expense_acct" +
					" from hr_concept_acct ac" +
					" inner join hr_concept con on ac.hr_concept_id=con.hr_concept_id" +
					" where con.asientoliquidacion = 'Y' and con.hr_concept_id in (select hr_concept_id from uy_hrresultdetail det" +
					" inner join uy_hrresult res on det.uy_hrresult_id=res.uy_hrresult_id where res.uy_hrprocesonomina_id = " + this.proceso.get_ID() + ")";

			pstmt = DB.prepareStatement (sql,getTrxName());
			rs = pstmt.executeQuery ();
			
			BigDecimal sumGlobalDr = Env.ZERO;
			BigDecimal sumGlobalCr = Env.ZERO;

			while(rs.next()){

				BigDecimal totalHaberesDr = Env.ZERO;
				BigDecimal totalHaberesCr = Env.ZERO;
				BigDecimal totalHaberes = Env.ZERO;
				BigDecimal totalDescuentosDr = Env.ZERO;
				BigDecimal totalDescuentosCr = Env.ZERO;
				BigDecimal totalDescuentos = Env.ZERO;

				//OBTENGO TOTAL DE "HABERES"
				sql = "	SELECT coalesce(sum(rd.totalamt),0)" +
						" FROM uy_hrresult r" +
						" INNER JOIN uy_hrresultdetail rd on r.uy_hrresult_id=rd.uy_hrresult_id" +
						" INNER JOIN uy_hrprocesonomina p on r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" +
						" INNER JOIN hr_concept con on rd.hr_concept_id = con.hr_concept_id" +
						" INNER JOIN hr_concept_category cat on con.hr_concept_category_id = cat.hr_concept_category_id" +
						" INNER JOIN hr_concept_acct cont on con.hr_concept_id = cont.hr_concept_id" +
						" WHERE p.uy_hrprocesonomina_id = " + this.proceso.get_ID() + " and cat.value = '1001'" +
						" and con.accountsign='D' and cont.hr_expense_acct = " + rs.getInt("hr_expense_acct");
				totalHaberesDr = DB.getSQLValueBDEx(getTrxName(), sql);

				sql = "	SELECT coalesce(sum(rd.totalamt),0)" +
						" FROM uy_hrresult r" +
						" INNER JOIN uy_hrresultdetail rd on r.uy_hrresult_id=rd.uy_hrresult_id" +
						" INNER JOIN uy_hrprocesonomina p on r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" +
						" INNER JOIN hr_concept con on rd.hr_concept_id = con.hr_concept_id" +
						" INNER JOIN hr_concept_category cat on con.hr_concept_category_id = cat.hr_concept_category_id" +
						" INNER JOIN hr_concept_acct cont on con.hr_concept_id = cont.hr_concept_id" +
						" WHERE p.uy_hrprocesonomina_id = " + this.proceso.get_ID() + " and cat.value = '1001'" +
						" and con.accountsign='C' and cont.hr_expense_acct = " + rs.getInt("hr_expense_acct");
				totalHaberesCr = DB.getSQLValueBDEx(getTrxName(), sql);

				totalHaberes = totalHaberesDr.subtract(totalHaberesCr);

				//OBTENGO TOTAL DE "DESCUENTOS"
				sql = "	SELECT coalesce(sum(rd.totalamt),0)" +
						" FROM uy_hrresult r" +
						" INNER JOIN uy_hrresultdetail rd on r.uy_hrresult_id=rd.uy_hrresult_id" +
						" INNER JOIN uy_hrprocesonomina p on r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" +
						" INNER JOIN hr_concept con on rd.hr_concept_id = con.hr_concept_id" +
						" INNER JOIN hr_concept_category cat on con.hr_concept_category_id = cat.hr_concept_category_id" +
						" INNER JOIN hr_concept_acct cont on con.hr_concept_id = cont.hr_concept_id" +
						" WHERE p.uy_hrprocesonomina_id = " + this.proceso.get_ID() + " and cat.value = '1002'" +
						" and con.accountsign='D' and cont.hr_expense_acct = " + rs.getInt("hr_expense_acct");
				totalDescuentosDr = DB.getSQLValueBDEx(getTrxName(), sql);

				sql = "	SELECT coalesce(sum(rd.totalamt),0)" +
						" FROM uy_hrresult r" +
						" INNER JOIN uy_hrresultdetail rd on r.uy_hrresult_id=rd.uy_hrresult_id" +
						" INNER JOIN uy_hrprocesonomina p on r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" +
						" INNER JOIN hr_concept con on rd.hr_concept_id = con.hr_concept_id" +
						" INNER JOIN hr_concept_category cat on con.hr_concept_category_id = cat.hr_concept_category_id" +
						" INNER JOIN hr_concept_acct cont on con.hr_concept_id = cont.hr_concept_id" +
						" WHERE p.uy_hrprocesonomina_id = " + this.proceso.get_ID() + " and cat.value = '1002'" +
						" and con.accountsign='C' and cont.hr_expense_acct = " + rs.getInt("hr_expense_acct");
				totalDescuentosCr = DB.getSQLValueBDEx(getTrxName(), sql);

				totalDescuentos = totalDescuentosDr.subtract(totalDescuentosCr);
				
				if(totalDescuentos.compareTo(Env.ZERO)<0) totalDescuentos = totalDescuentos.negate();
				
				sql = "select account_id from c_validcombination where c_validcombination_id = " + rs.getInt("hr_expense_acct");
				int accountID = DB.getSQLValueEx(getTrxName(), sql);
				
				MAccount account = MAccount.forElementValue(getCtx(), accountID,getTrxName());
				
				// DR
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), totalHaberes, null);
				if (fl != null && this.proceso.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.proceso.getAD_Org_ID());	
				// Acumulo creditos
				sumGlobalDr = sumGlobalDr.add(totalHaberes);
				
				// CR
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), null, totalDescuentos);
				if (fl != null && this.proceso.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.proceso.getAD_Org_ID());
				// Acumulo creditos
				sumGlobalCr = sumGlobalCr.add(totalDescuentos);				
				
			}
			
			// Obtengo diferencia entre debitos y creditos sumarizados
			BigDecimal diff = sumGlobalDr.subtract(sumGlobalCr);
			MAcctSchemaDefault schemaDef = schema.getAcctSchemaDefault();
			MAccount payroll = new MAccount(getCtx(), schemaDef.getUnEarnedRevenue_Acct(), getTrxName()); //obtengo cuenta de sueldos a pagar
			// Si esta diferencia es positiva entonces tengo mas debitos
			// y por lo tanto hago un asiento al credito con la cuenta de sueldos a pagar
			if (diff.compareTo(Env.ZERO) > 0) {
				// CR - Cuenta Sueldos A Pagar
				fl = fact.createLine(null, payroll, schema.getC_Currency_ID(), null, diff);
				if (fl != null && this.proceso.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.proceso.getAD_Org_ID());
			}
			// Si esta diferencia es negativa entonces tengo mas creditos
			// y por lo tanto hago un asiento al debito con la cuenta de sueldos a pagar
			else if (diff.compareTo(Env.ZERO) < 0) {
				// DR  - Cuenta Sueldos A Pagar
				fl = fact.createLine(null, payroll, schema.getC_Currency_ID(), diff.negate(), null);
				if (fl != null && this.proceso.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.proceso.getAD_Org_ID());
			}
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}			
		
		return fact;
	}
	
	/***
	 * Metodo que crea el asiento contable de liquidacion CON centros de costo
	 * OpenUp Ltda. Issue #1058
	 * @author Nicolas Sarlabos - 27/06/2013
	 * @see
	 * @return
	 */	
	private Fact createFactConCCostos (Fact fact, MBPartner [] partners, MAcctSchema schema) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		FactLine fl = null;
		
		
		try {
			//obtengo c_elementvalue_id de los conceptos involucrados en esta liquidacion, existira 1 linea de asiento por cada cuenta contable
			sql = "select distinct hr_expense_acct" +
					" from hr_concept_acct ac" +
					" inner join hr_concept con on ac.hr_concept_id=con.hr_concept_id" +
					" where con.asientoliquidacion = 'Y' and con.hr_concept_id in (select hr_concept_id from uy_hrresultdetail det" +
					" inner join uy_hrresult res on det.uy_hrresult_id=res.uy_hrresult_id where res.uy_hrprocesonomina_id = " + this.proceso.get_ID() + ")";

			pstmt = DB.prepareStatement (sql,getTrxName());
			rs = pstmt.executeQuery ();
			
			BigDecimal sumGlobalDr = Env.ZERO;
			BigDecimal sumGlobalCr = Env.ZERO;

			while(rs.next()){

				BigDecimal totalHaberesDr = Env.ZERO;
				BigDecimal totalHaberesCr = Env.ZERO;
				BigDecimal totalHaberes = Env.ZERO;
				BigDecimal totalDescuentosDr = Env.ZERO;
				BigDecimal totalDescuentosCr = Env.ZERO;
				BigDecimal totalDescuentos = Env.ZERO;
				
				sql = "select account_id from c_validcombination where c_validcombination_id = " + rs.getInt("hr_expense_acct");
				int accountID = DB.getSQLValueEx(getTrxName(), sql); //obtengo la cuenta
				
				MAccount account = MAccount.forElementValue(getCtx(), accountID,getTrxName());

				MActivity [] ccostos = this.getCCostos(); //obtengo centros de costo de la liquidacion actual

				for (int i = 0; i < ccostos.length; i ++){
					
					MActivity act = new MActivity (getCtx(),ccostos[i].get_ID(),getTrxName());

					//OBTENGO TOTAL DE "HABERES"
					sql = "	SELECT coalesce(sum(rd.totalamt),0)" +
							" FROM uy_hrresult r" +
							" INNER JOIN uy_hrresultdetail rd on r.uy_hrresult_id=rd.uy_hrresult_id" +
							" INNER JOIN uy_hrprocesonomina p on r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" +
							" INNER JOIN hr_concept con on rd.hr_concept_id = con.hr_concept_id" +
							" INNER JOIN hr_concept_category cat on con.hr_concept_category_id = cat.hr_concept_category_id" +
							" INNER JOIN hr_concept_acct cont on con.hr_concept_id = cont.hr_concept_id" +
							" INNER JOIN c_bpartner bp on r.c_bpartner_id = bp.c_bpartner_id" +
							" INNER JOIN hr_employee e on bp.c_bpartner_id = e.c_bpartner_id" +
							" WHERE p.uy_hrprocesonomina_id = " + this.proceso.get_ID() + " and cat.value = '1001'" +
							" and con.accountsign='D' and cont.hr_expense_acct = " + rs.getInt("hr_expense_acct") + " and e.c_activity_id = " + act.get_ID();
					totalHaberesDr = DB.getSQLValueBDEx(getTrxName(), sql);

					sql = "	SELECT coalesce(sum(rd.totalamt),0)" +
							" FROM uy_hrresult r" +
							" INNER JOIN uy_hrresultdetail rd on r.uy_hrresult_id=rd.uy_hrresult_id" +
							" INNER JOIN uy_hrprocesonomina p on r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" +
							" INNER JOIN hr_concept con on rd.hr_concept_id = con.hr_concept_id" +
							" INNER JOIN hr_concept_category cat on con.hr_concept_category_id = cat.hr_concept_category_id" +
							" INNER JOIN hr_concept_acct cont on con.hr_concept_id = cont.hr_concept_id" +
							" INNER JOIN c_bpartner bp on r.c_bpartner_id = bp.c_bpartner_id" +
							" INNER JOIN hr_employee e on bp.c_bpartner_id = e.c_bpartner_id" +
							" WHERE p.uy_hrprocesonomina_id = " + this.proceso.get_ID() + " and cat.value = '1001'" +
							" and con.accountsign='C' and cont.hr_expense_acct = " + rs.getInt("hr_expense_acct") + " and e.c_activity_id = " + act.get_ID();
					totalHaberesCr = DB.getSQLValueBDEx(getTrxName(), sql);

					totalHaberes = totalHaberesDr.subtract(totalHaberesCr);
					
					// DR
					if(totalHaberes.compareTo(Env.ZERO) > 0) {

						fl = fact.createLine(null, account, schema.getC_Currency_ID(), totalHaberes, null);
						fl.setC_Activity_ID(act.get_ID()); //seteo Id de centro de costos
						if (fl != null && this.proceso.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.proceso.getAD_Org_ID());	
						// Acumulo creditos
						sumGlobalDr = sumGlobalDr.add(totalHaberes);

					}
					
				}

				//OBTENGO TOTAL DE "DESCUENTOS"
				sql = "	SELECT coalesce(sum(rd.totalamt),0)" +
						" FROM uy_hrresult r" +
						" INNER JOIN uy_hrresultdetail rd on r.uy_hrresult_id=rd.uy_hrresult_id" +
						" INNER JOIN uy_hrprocesonomina p on r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" +
						" INNER JOIN hr_concept con on rd.hr_concept_id = con.hr_concept_id" +
						" INNER JOIN hr_concept_category cat on con.hr_concept_category_id = cat.hr_concept_category_id" +
						" INNER JOIN hr_concept_acct cont on con.hr_concept_id = cont.hr_concept_id" +
						" WHERE p.uy_hrprocesonomina_id = " + this.proceso.get_ID() + " and cat.value = '1002'" +
						" and con.accountsign='D' and cont.hr_expense_acct = " + rs.getInt("hr_expense_acct");
				totalDescuentosDr = DB.getSQLValueBDEx(getTrxName(), sql);

				sql = "	SELECT coalesce(sum(rd.totalamt),0)" +
						" FROM uy_hrresult r" +
						" INNER JOIN uy_hrresultdetail rd on r.uy_hrresult_id=rd.uy_hrresult_id" +
						" INNER JOIN uy_hrprocesonomina p on r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" +
						" INNER JOIN hr_concept con on rd.hr_concept_id = con.hr_concept_id" +
						" INNER JOIN hr_concept_category cat on con.hr_concept_category_id = cat.hr_concept_category_id" +
						" INNER JOIN hr_concept_acct cont on con.hr_concept_id = cont.hr_concept_id" +
						" WHERE p.uy_hrprocesonomina_id = " + this.proceso.get_ID() + " and cat.value = '1002'" +
						" and con.accountsign='C' and cont.hr_expense_acct = " + rs.getInt("hr_expense_acct");
				totalDescuentosCr = DB.getSQLValueBDEx(getTrxName(), sql);

				totalDescuentos = totalDescuentosDr.subtract(totalDescuentosCr);
				
				if(totalDescuentos.compareTo(Env.ZERO)<0) totalDescuentos = totalDescuentos.negate();
								
				// CR
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), null, totalDescuentos);
				if (fl != null && this.proceso.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.proceso.getAD_Org_ID());
				// Acumulo creditos
				sumGlobalCr = sumGlobalCr.add(totalDescuentos);				
				
			}
			
			// Obtengo diferencia entre debitos y creditos sumarizados
			BigDecimal diff = sumGlobalDr.subtract(sumGlobalCr);
			MAcctSchemaDefault schemaDef = schema.getAcctSchemaDefault();
			MAccount payroll = new MAccount(getCtx(), schemaDef.getUnEarnedRevenue_Acct(), getTrxName()); //obtengo cuenta de sueldos a pagar
			// Si esta diferencia es positiva entonces tengo mas debitos
			// y por lo tanto hago un asiento al credito con la cuenta de sueldos a pagar
			if (diff.compareTo(Env.ZERO) > 0) {
				// CR - Cuenta Sueldos A Pagar
				fl = fact.createLine(null, payroll, schema.getC_Currency_ID(), null, diff);
				if (fl != null && this.proceso.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.proceso.getAD_Org_ID());
			}
			// Si esta diferencia es negativa entonces tengo mas creditos
			// y por lo tanto hago un asiento al debito con la cuenta de sueldos a pagar
			else if (diff.compareTo(Env.ZERO) < 0) {
				// DR  - Cuenta Sueldos A Pagar
				fl = fact.createLine(null, payroll, schema.getC_Currency_ID(), diff.negate(), null);
				if (fl != null && this.proceso.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.proceso.getAD_Org_ID());
			}
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}	
		
		return fact;
	}
	
	/***
	 * Obtiene y retorna centros de costo de liquidacion actual
	 * OpenUp Ltda. Issue #1058
	 * @author Nicolas Sarlabos - 28/06/2013
	 * @see
	 * @return
	 */
	private MActivity[] getCCostos(){
		
		List <MActivity> list = new ArrayList <MActivity>();
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			sql = "select distinct c_activity_id" +
                  " from hr_employee e" +
                  " where c_bpartner_id in (select c_bpartner_id from uy_hrresult where uy_hrprocesonomina_id = " + this.proceso.get_ID() + ")";
			
			pstmt = DB.prepareStatement (sql,getTrxName());
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				MActivity a = new MActivity(getCtx(), rs.getInt("c_activity_id"), getTrxName());
				list.add(a);
			}			
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}	

		return list.toArray(new MActivity[list.size()]);
	}
	

}
