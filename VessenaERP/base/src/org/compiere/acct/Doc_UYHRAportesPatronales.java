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
import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MHRConcept;
import org.eevolution.model.MHRPeriod;
import org.openup.model.MHRAportesPatronales;
import org.openup.model.MHRPatronalesLine;
import org.openup.model.MHRProcess;

/**
 * @author Nicolas
 *
 */
public class Doc_UYHRAportesPatronales extends Doc {

	private MHRAportesPatronales patronales = null;

	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYHRAportesPatronales(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
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
	public Doc_UYHRAportesPatronales(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MHRAportesPatronales.class, rs, null, trxName);
	}

	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MHRAportesPatronales) getPO()).getDateAcct());
		return null;
	}

	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	//@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		Fact fact = new Fact(this, as, Fact.POST_Actual);
		String sql = "";
		int account_id = 0;
		MAccount account = null;
		BigDecimal amount = Env.ZERO;
	
		// Modelo desde PO
		this.patronales = (MHRAportesPatronales)this.getPO();	

		// Eschema contable
		MClient client = new MClient(getCtx(), this.patronales.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		MAcctSchemaDefault schemaDef = schema.getAcctSchemaDefault();
		MAccount bps = new MAccount(getCtx(), schemaDef.getNotInvoicedReceipts_Acct(), getTrxName()); //obtengo cuenta de BPS a pagar

		FactLine fl = null;
		
		BigDecimal sumBPS = this.getSumBPS(); //obtengo saldo gravado BPS del periodo
		
		MHRPatronalesLine [] lines = this.getLines(); //obtengo lineas de conceptos
		
		//se recorren los conceptos
		for (int i = 0; i < lines.length; i++){

			MHRConcept con = new MHRConcept (getCtx(),lines[i].getHR_Concept_ID(),getTrxName()); //instancio el concepto

			if(con.getValue()!=null){

				sql = "select vc.account_id" +
						" from hr_concept_acct ac" +
						" inner join hr_concept con on ac.hr_concept_id=con.hr_concept_id" +
						" inner join c_validcombination vc on ac.hr_expense_acct=vc.c_validcombination_id" +
						" where con.hr_concept_id = " + con.get_ID();
				account_id = DB.getSQLValueEx(getTrxName(), sql);

				if(account_id > 0) {

					account = MAccount.forElementValue(getCtx(),account_id,getTrxName());

				} else throw new AdempiereException ("No se obtuvo cuenta contable para el concepto " + con.getValue()); 		

				if(con.getValue().equalsIgnoreCase("2")){

					sql = "select coalesce(porcjubpatronal/100,0) from uy_hrparametros where ad_client_id=" + this.patronales.getAD_Client_ID() +
							" and ad_org_id=" + this.patronales.getAD_Org_ID();
					BigDecimal porcJubPatronal = DB.getSQLValueBDEx(null, sql);

					amount = sumBPS.multiply(porcJubPatronal);

				} else if (con.getValue().equalsIgnoreCase("8")){

					BigDecimal sumAguinaldo = this.getSumAguinaldo();

					sql = "select coalesce(porcfonpatronal/100,0) from uy_hrparametros where ad_client_id=" + this.patronales.getAD_Client_ID() +
							" and ad_org_id=" + this.patronales.getAD_Org_ID();
					BigDecimal porcFonasaPatronal = DB.getSQLValueBDEx(null, sql);

					amount = (sumBPS.subtract(sumAguinaldo)).multiply(porcFonasaPatronal);

				} else if(con.getValue().equalsIgnoreCase("47")){

					sql = "select coalesce(porcfrlpatronal/100,0) from uy_hrparametros where ad_client_id=" + this.patronales.getAD_Client_ID() +
							" and ad_org_id=" + this.patronales.getAD_Org_ID();
					BigDecimal porcFrlPatronal = DB.getSQLValueBDEx(null, sql);

					amount = sumBPS.multiply(porcFrlPatronal);

				} else amount = lines[i].getAmount(); //obtengo importe de la linea	directamente				

				// DR
				fl = fact.createLine(null, account, schema.getC_Currency_ID(), amount, null);
				if (fl != null && this.patronales.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.patronales.getAD_Org_ID());	

				// CR
				fl = fact.createLine(null, bps, schema.getC_Currency_ID(), null, amount);
				if (fl != null && this.patronales.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.patronales.getAD_Org_ID());

			} else throw new AdempiereException ("No se pudo obtener la clave de busqueda para el concepto actual.Verifique los conceptos a procesar.");				

		}
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;		

	}

	/***
	 * Obtiene suma de haberes gravados BPS para un periodo.
	 * OpenUp Ltda. Issue # 1059
	 * @author Nicolas Sarlabos - 15/07/2013
	 * @see
	 * @param
	 * @return
	 */
	private BigDecimal getSumBPS() {

		BigDecimal totalDR = Env.ZERO;
		BigDecimal totalCR = Env.ZERO;
		BigDecimal total = Env.ZERO;
		String sql = "";
		
		try{
			MHRProcess process = new MHRProcess(getCtx(), patronales.getUY_HRProcess_ID(), null);
			MHRPeriod period = new MHRPeriod(getCtx(), process.getHR_Period_ID(), null);
		
			sql = "select coalesce(sum(det.totalamt),0)" +
                  " from uy_hrresult res" +
                  " inner join uy_hrresultdetail det on res.uy_hrresult_id = det.uy_hrresult_id" +
                  " inner join hr_concept conc on det.hr_concept_id = conc.hr_concept_id" +
                  " inner join hr_concept_category cat on conc.hr_concept_category_id = cat.hr_concept_category_id" +
                  " inner join uy_hrprocesonomina pn on res.uy_hrprocesonomina_id = pn.uy_hrprocesonomina_id" +
                  " inner join uy_hrprocess pro on pn.uy_hrprocess_id = pro.uy_hrprocess_id" +
                  " inner join hr_period per on pro.hr_period_id = per.hr_period_id" +
                  " inner join c_period cp on per.c_period_id = cp.c_period_id" +
                  " where cp.c_period_id = " + period.getC_Period_ID() + " and conc.grabado_bps='Y' and cat.value='1001' and conc.accountsign='D'";
			totalDR = DB.getSQLValueBDEx(getTrxName(), sql); //obtengo suma de debitos
			
			sql = "select coalesce(sum(det.totalamt),0)" +
	                  " from uy_hrresult res" +
	                  " inner join uy_hrresultdetail det on res.uy_hrresult_id = det.uy_hrresult_id" +
	                  " inner join hr_concept conc on det.hr_concept_id = conc.hr_concept_id" +
	                  " inner join hr_concept_category cat on conc.hr_concept_category_id = cat.hr_concept_category_id" +
	                  " inner join uy_hrprocesonomina pn on res.uy_hrprocesonomina_id = pn.uy_hrprocesonomina_id" +
	                  " inner join uy_hrprocess pro on pn.uy_hrprocess_id = pro.uy_hrprocess_id" +
	                  " inner join hr_period per on pro.hr_period_id = per.hr_period_id" +
	                  " inner join c_period cp on per.c_period_id = cp.c_period_id" +
	                  " where cp.c_period_id = " + period.getC_Period_ID() + " and conc.grabado_bps='Y' and cat.value='1001' and conc.accountsign='C'";
			totalCR = DB.getSQLValueBDEx(getTrxName(), sql); //obtengo suma de creditos
			
			total = totalDR.subtract(totalCR); //obtengo diferencia
						
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
				
		return total;

	}

	/***
	 * Obtiene suma de aguinaldos para un periodo.
	 * OpenUp Ltda. Issue # 1059
	 * @author Nicolas Sarlabos - 15/07/2013
	 * @see
	 * @param
	 * @return
	 */
	private BigDecimal getSumAguinaldo() {

		BigDecimal total = Env.ZERO;
		String sql = "";
		
		try{
			MHRProcess process = new MHRProcess(getCtx(), patronales.getUY_HRProcess_ID(), null);
			MHRPeriod period = new MHRPeriod(getCtx(), process.getHR_Period_ID(), null);
			
			MHRConcept concept1 = MHRConcept.forValue(getCtx(), "1000", getTrxName()); //obtengo concepto Aguinaldo
			MHRConcept concept2 = MHRConcept.forValue(getCtx(), "5000", getTrxName()); //obtengo concepto Aguinaldo Mes Egreso
		
			sql = "	select coalesce(sum(det.totalamt),0)" +
	              " from uy_hrresultdetail det" +
	              " inner join uy_hrresult res on det.uy_hrresult_id = res.uy_hrresult_id" +
	              " inner join uy_hrprocesonomina pro on res.uy_hrprocesonomina_id = pro.uy_hrprocesonomina_id" +
	              " inner join uy_hrprocess p on pro.uy_hrprocess_id = p.uy_hrprocess_id" +
	              " inner join hr_period per on p.hr_period_id = per.hr_period_id" +
	              " inner join c_period pd on per.c_period_id = pd.c_period_id" +
	              " where hr_concept_id in (" + concept1.get_ID() + "," + concept2.get_ID() + ") and pd.c_period_id = " + period.getC_Period_ID();
			total = DB.getSQLValueBDEx(getTrxName(), sql); //obtengo importe
						
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
				
		return total;		
		
	}
	
	/***
	 * Obtiene y retorna lineas de conceptos
	 * OpenUp Ltda. Issue #1059
	 * @author Nicolas Sarlabos - 15/07/2013
	 * @see
	 * @return
	 */
	private MHRPatronalesLine[] getLines(){
		
		List <MHRPatronalesLine> list = new ArrayList <MHRPatronalesLine>();
		int detail_ID = 0;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			MHRConcept concept = MHRConcept.forValue(getCtx(), "108", getTrxName()); //obtengo concepto Aporte Adicional SNIS Dep., el cual no se debe considerar
			
			sql = "select uy_hrpatronalesdetail_id from uy_hrpatronalesdetail where uy_hraportespatronales_id = " + this.patronales.get_ID();
			detail_ID = DB.getSQLValueEx(getTrxName(), sql);
			
			sql = " select uy_hrpatronalesline_id " +
				  " from uy_hrpatronalesline " +
			      " where uy_hrpatronalesdetail_id = " + detail_ID + " and hr_concept_id <> " + concept.get_ID();
			
			pstmt = DB.prepareStatement (sql,getTrxName());
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				MHRPatronalesLine l = new MHRPatronalesLine(getCtx(), rs.getInt("uy_hrpatronalesline_id"), getTrxName());
				list.add(l);
			}			
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}	

		return list.toArray(new MHRPatronalesLine[list.size()]);
	}

}
