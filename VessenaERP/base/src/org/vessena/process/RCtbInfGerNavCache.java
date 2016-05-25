/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.AWindow;
import org.compiere.apps.Waiting;
import org.compiere.model.MConversionRate;
import org.compiere.model.MElementValue;
import org.compiere.model.MInvoice;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MGerencial;
import org.openup.model.MGerencialAccount;
import org.openup.model.MGerencialBP;
import org.openup.model.MGerencialCC;
import org.openup.model.MGerencialCCDet;
import org.openup.model.MGerencialDetail;
import org.openup.model.MGerencialJournal;
import org.openup.model.MGerencialMain;
import org.openup.model.MGerencialProd;
import org.openup.model.MGerencialProdDet;
import org.openup.model.MInfGerPeriodo;

/**
 * @author Nicolas
 *
 */
public class RCtbInfGerNavCache extends SvrProcess {
	
	private int uyGerencialID = 0;
	private MGerencial gerencial = null;
	//private HashMap<Integer, String> hashPeriodTitles = new HashMap<Integer, String>();
	
	private MGerencialAccount gerencialAccount = null;
	private MGerencialBP gerencialBP = null;
	private MGerencialDetail gerencialDet = null;
	private MGerencialJournal gerencialJournal = null;
	private MGerencialProd gerencialProd = null;
	private MGerencialCC gerencialCC = null;
	
	private int cPeriodFromID = 0;
	private int cPeriodToID = 0;
	//private int adUserID = 0;
	private int adClientID = 0;
	private String currencyType = "";
	
	//private String idReporte = "";
	public HashMap<Integer, Integer> hashPosicionXPeriodo = new HashMap<Integer, Integer>();
	public HashMap<Integer, BigDecimal> hashTasaCambioXPeriodo = new HashMap<Integer, BigDecimal>();
	public ArrayList <Integer> hashPeriodos = new ArrayList<Integer>();
	public ArrayList <Integer> hashPeriodosLoad = new ArrayList<Integer>();
	
	private Waiting waiting = null;
	private AWindow gerencialAWindow = null;
	
	private static final String TABLA_MOLDE_AUX = "UY_InfGer_Tot";
	//private static final String TABLA_MOLDE = "UY_MOLDE_InformeGerencial";
	private static final String TABLA_MOLDE_NAV_CTA = "UY_InfGer_Cta";
	private static final String TABLA_MOLDE_NAV_BP = "UY_InfGer_Bp";
	private static final String TABLA_MOLDE_NAV_JOURNAL = "UY_InfGer_Journal";
	private static final String TABLA_MOLDE_NAV_PROD = "UY_InfGer_Prod";
	private static final String TABLA_MOLDE_NAV_CC = "UY_InfGer_Cc";
	private static final String TABLA_MOLDE_NAV_PRODDET = "UY_InfGer_ProdDet";
	private static final String TABLA_MOLDE_NAV_CCDET = "UY_InfGer_CcDet";

	/**
	 * 
	 */
	public RCtbInfGerNavCache() {
	
	}

	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_Gerencial_ID")){
					if (para[i].getParameter()!=null)
						this.uyGerencialID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}
		
	}

	@Override
	protected String doIt() throws Exception {
	
		this.gerencial = new MGerencial(getCtx(), this.uyGerencialID, null);
		this.adClientID = getAD_Client_ID();
		//this.adUserID = getAD_User_ID();
		//this.idReporte = UtilReportes.getReportID(new Long(adUserID));
		this.currencyType = gerencial.getCurrencyType();
			
		this.setWaiting(this.getProcessInfo().getWaiting());
		this.setGerencialAWindow(this.getProcessInfo().getWindow());
		
		if(gerencial.isUseCache()){ //si se esta usando cache de datos verifico periodos a procesar
			
			this.verifyPeriods(gerencial.getC_Period_ID_From(),gerencial.getC_Period_ID_To());
			
			//si se cargaron periodos a procesar
			if(this.hashPeriodosLoad.size() > 0){
				
				//cargo tablas de cache
				this.execute();
				
				//se cargan periodos en table cache
				this.insertPeriodCache();
				
				// Obtengo titulos de periodos
				//this.getPeriodsTitles();				
				
			} else this.setPeriodos();
			
		} else { //si no se esta usando cache de datos
			
			this.cPeriodFromID = gerencial.getC_Period_ID_From();
			this.cPeriodToID = gerencial.getC_Period_ID_To();
			
			//cargo tablas de cache
			this.execute();
			
			// Obtengo titulos de periodos
			//this.getPeriodsTitles();
			
		}
			
		// Cargo Informacion para nevegar
		this.loadDataNav();
				
		return "OK";
	}

	/***
	 * Guarda los periodos procesados en la table de cache de periodos 
	 * OpenUp Ltda. Issue #1151 
	 * @author Nicolas Sarlabos - 25/07/2013
	 * @see
	 */
	private void insertPeriodCache() {

		for(int i=0; i < this.hashPeriodosLoad.size(); i++){

			MPeriod period = new MPeriod(getCtx(), this.hashPeriodosLoad.get(i),null);

			MInfGerPeriodo per = new MInfGerPeriodo(getCtx(),0,null);
			per.setC_Period_ID(period.get_ID());
			BigDecimal rate = MConversionRate.getRate(100, 142, period.getEndDate(), 0, adClientID, 0);
			per.setCurrencyRate(rate);
			per.saveEx();

		}		
		
	}

	/***
	 * Si se esta utilizando cache para el reporte, verifica en la tabla de periodos cerrados y setea periodo inicial y final 
	 * OpenUp Ltda. Issue #1151 
	 * @author Nicolas Sarlabos - 24/07/2013
	 * @see
	 */
	
	public void verifyPeriods(int periodFrom_id, int periodTo_id){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try {
			
			sql = "select c_period_id from c_period where c_period_id >= " + gerencial.getC_Period_ID_From() + " and c_period_id <= " + gerencial.getC_Period_ID_To() +
			    	" order by c_period_id";
			
			pstmt = DB.prepareStatement(sql,null);
			rs = pstmt.executeQuery ();
			
			while (rs.next()) {
				
				MPeriod period = new MPeriod(getCtx(),rs.getInt("c_period_id"),null);
				this.hashPeriodos.add(period.get_ID());
				
				sql = "select c_period_id from uy_infger_periodo where c_period_id = " + period.get_ID(); //consulto si el periodo esta en tabla de periodos cacheados
				int res = DB.getSQLValueEx(null, sql);
				
				if(res > 0){ //si el periodo esta en la tabla de periodos cacheados
					
					if(!isPeriodClosed(period.get_ID())){ //si el periodo NO esta cerrado
						
						this.deletePeriodFromCache(period); //elimino los datos del periodo actualmente almacenados
						this.hashPeriodosLoad.add(period.get_ID()); //agrego ID del periodo al array para utilizar en la carga
						
						
					}				
					
				} else {  //si el periodo NO esta en la tabla de periodos cacheados
					
					this.hashPeriodosLoad.add(period.get_ID()); //agrego ID del periodo al array para utilizar en la carga	
									
				}			
				
			}			
			
		} catch (Exception e){
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

	}
	
	/***
	 * Elimina los datos en todas las tablas de cache para el periodo recibido como parametro.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 25/07/2013
	 * @see
	 */	
	private void deletePeriodFromCache(MPeriod period) {
	
		try{
			
			String action = " DELETE FROM " + TABLA_MOLDE_AUX + " WHERE c_period_id = " + period.get_ID();
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_CTA + " WHERE c_period_id = " + period.get_ID();
			DB.executeUpdateEx(action,null);
		
			action = " DELETE FROM " + TABLA_MOLDE_NAV_BP + " WHERE c_period_id = " + period.get_ID();
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_JOURNAL + " WHERE c_period_id = " + period.get_ID();
			DB.executeUpdateEx(action,null);
			
			action = " DELETE FROM " + TABLA_MOLDE_NAV_PROD + " WHERE c_period_id = " + period.get_ID();
			DB.executeUpdateEx(action,null);
			
			action = " DELETE FROM " + TABLA_MOLDE_NAV_CC + " WHERE c_period_id = " + period.get_ID();
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_PRODDET + " WHERE c_period_id = " + period.get_ID();
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_CCDET + " WHERE c_period_id = " + period.get_ID();
			DB.executeUpdateEx(action,null);
			
			action = " DELETE FROM uy_infger_periodo WHERE c_period_id = " + period.get_ID();
			DB.executeUpdateEx(action,null);
			
						
		} catch (Exception e){
			throw new AdempiereException(e);
		}	
		
	}

	/***
	 * Devuelve true si TODOS los documentos del periodo recibido como parametro estan cerrados.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 25/07/2013
	 * @see
	 */		
	private boolean isPeriodClosed(int periodID) {
		
		String sql = "select count(pc.c_periodcontrol_id)" +
                     " from c_period p" +
                     " inner join c_periodcontrol pc on p.c_period_id = pc.c_period_id" +
                     " where pc.periodstatus='A' and p.c_period_id = " + periodID;
		
		int count = DB.getSQLValueEx(null, sql);
		
		if(count > 0) {
			
			return false;
			
		} else return true;
		
	}

	/***
	 * Ejecuta los metodos que cargan las tablas molde.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 23/07/2013
	 * @see
	 */	
	public void execute(){

		try{
			// Elimina datos anteriores para este usuario
			if(!gerencial.isUseCache()) this.deleteData();

			// Seteo posicion de periodos
			this.setPeriodos();

			// Carga datos iniciales en tabla temporal
			this.loadData();

			// Actualiza datos de temporal 
			//this.updateData();

			// Actualiza titulos de periodos para el reporte
			//this.updatePeriodsTitle();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/***
	 * Elimina datos anteriores para usuario que esta procesando el reporte.
	 * OpenUp Ltda. Issue #1151 
	 * @author Nicolas Sarlabos - 24/07/2013
	 * @see
	 */
	private void deleteData() {
		try{

			String action = " DELETE FROM " + TABLA_MOLDE_AUX;
			DB.executeUpdateEx(action,null);

			/*action = " DELETE FROM " + TABLA_MOLDE +
					" WHERE ad_user_id =" + adUserID;
			DB.executeUpdateEx(action,null);*/

			action = " DELETE FROM " + TABLA_MOLDE_NAV_CTA;
			DB.executeUpdateEx(action,null);
		
			action = " DELETE FROM " + TABLA_MOLDE_NAV_BP;
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_JOURNAL;
			DB.executeUpdateEx(action,null);
			
			action = " DELETE FROM " + TABLA_MOLDE_NAV_PROD;
			DB.executeUpdateEx(action,null);
			
			action = " DELETE FROM " + TABLA_MOLDE_NAV_CC;
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_PRODDET;
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_CCDET;
			DB.executeUpdateEx(action,null);
			
			action = " DELETE FROM uy_infger_periodo";
			DB.executeUpdateEx(action,null);			
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
	
	/***
	 * Setea posiciones por periodo.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 24/97/2013
	 * @see
	 */
	private void setPeriodos(){
		
		int posicion = 0;
		
		if(!this.gerencial.isUseCache()){
			for (int i = this.cPeriodFromID; i <= this.cPeriodToID; i++){
				posicion++;
				hashPosicionXPeriodo.put(i, posicion);

				// Si el reporte no es moneda nacional
				if (!this.currencyType.equalsIgnoreCase("MN")){
					// Guardo Tasa de cambio de ultimo dia del periodo
					MPeriod period = new MPeriod(Env.getCtx(), i, null);
					BigDecimal rate = MConversionRate.getRate(100, 142, period.getEndDate(), 0, adClientID, 0);	
					if (rate == null){
						throw new AdempiereException("No se pudo obtener Tasa de Cambio para la fecha : " + period.getEndDate());
					}
					hashTasaCambioXPeriodo.put(i, rate);
				}

			}
		} else {
			for (int i = 0; i < this.hashPeriodos.size(); i++){
				posicion++;
				hashPosicionXPeriodo.put(this.hashPeriodos.get(i), posicion);

				// Si el reporte no es moneda nacional
				if (!this.currencyType.equalsIgnoreCase("MN")){
					// Guardo Tasa de cambio de ultimo dia del periodo
					MPeriod period = new MPeriod(Env.getCtx(), this.hashPeriodos.get(i), null);
					BigDecimal rate = MConversionRate.getRate(100, 142, period.getEndDate(), 0, adClientID, 0);	
					if (rate == null){
						throw new AdempiereException("No se pudo obtener Tasa de Cambio para la fecha : " + period.getEndDate());
					}
					hashTasaCambioXPeriodo.put(this.hashPeriodos.get(i), rate);
				}

			}

		}
		// Si tengo mas de 13 periodos aviso con exception
		if (posicion > 13){
			throw new AdempiereException("El período posible a considerar NO puede ser mayor a un año");
		}
	}
	
	/***
	 * Carga temporal con informacion inicial del reporte.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 */
	private void loadData() {

		String insert ="", sql = "", wherePeriod = "";
		
		//armo WHERE para filtrar por periodos dependiendo de si se usa cache o no
		if(gerencial.isUseCache()){
						
			wherePeriod = " and fa.c_period_id in (";
			
			for(int i=0; i <= this.hashPeriodosLoad.size()-1; i++){
				
				int id = this.hashPeriodosLoad.get(i);
				
				if(i == this.hashPeriodosLoad.size()-1){ //estoy en el ultimo elemento
					
					wherePeriod += id + ")";
					
				} else wherePeriod += id + ",";
				
			}
			
		} else wherePeriod = " and fa.c_period_id >=" + this.cPeriodFromID + " and fa.c_period_id <=" + this.cPeriodToID;
		
		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE_AUX + " (accounttype, parent_id, c_elementvalue_id, seqno, c_period_id," +
					" saldomn, saldome, saldomm)";

			this.showHelp("Totales Cuentas informe...");
			
			sql = " select evig.accounttype, acctparent.parent_id, evig.c_elementvalue_id, " +
				  " acctparent.seqno, fa.c_period_id, (sum(fa.amtacctcr) - sum(fa.amtacctdr)) as saldomn, (sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id) as saldome," +
   				  " ((sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id))/1000 as saldomm" +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " inner join vuy_infoger_acctparent acctparent on evig.c_elementvalue_id = acctparent.node_id " +
				  " where evig.c_element_id = 1000008 " + wherePeriod +
				  " group by evig.accounttype, acctparent.parent_id, evig.c_elementvalue_id, " +
				  " acctparent.seqno, fa.c_period_id, fa.ad_client_id, fa.ad_org_id " +
				  " order by evig.accounttype asc, acctparent.parent_id asc, evig.c_elementvalue_id asc , fa.c_period_id asc ";
			
			DB.executeUpdateEx(insert + sql, null);
			
			// Para informe gerencial navegable, inserto en este momento en tabla molde por cuenta del plan
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_CTA + " (parent_id, c_elementvalue_id, accounttype, c_period_id," +
					" saldomn, saldome, saldomm)";

			this.showHelp("Totales Cuentas Plan...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, ev.accounttype, fa.c_period_id," + 
				  " (sum(fa.amtacctcr) - sum(fa.amtacctdr)) as saldomn, (sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id) as saldome," +
   				  " ((sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id))/1000 as saldomm" +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where evig.c_element_id = 1000008 " + wherePeriod +
				  " group by evig.c_elementvalue_id, fa.account_id, ev.accounttype, fa.c_period_id, fa.ad_client_id, fa.ad_org_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);
			
			// Para informe gerencial navegable, inserto en este momento en tabla molde por socio de negocio
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_BP + " (parent_id, c_elementvalue_id, c_bpartner_id, c_period_id, saldomn, saldome, saldomm)";

			this.showHelp("Totales SNeg...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.c_period_id, " +
				  " (sum(fa.amtacctcr) - sum(fa.amtacctdr)) as saldomn, (sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id) as saldome," +
   				  " ((sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id))/1000 as saldomm" +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_bpartner_id is not null and evig.c_element_id = 1000008 " + wherePeriod +
				  " group by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id , fa.c_period_id, fa.ad_client_id, fa.ad_org_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);


			this.showHelp("Totales Asientos Diarios...");
			
			// Para informe gerencial navegable, inserto en este momento en tabla molde por asiento diario
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_JOURNAL + " (parent_id, c_elementvalue_id, gl_journal_id, c_period_id, saldomn, saldome, saldomm)";
			
			sql = " select evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_period_id, " +
				  " (sum(fa.amtacctcr) - sum(fa.amtacctdr)) as saldomn, (sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id) as saldome," +
   				  " ((sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id))/1000 as saldomm" +
				  " from fact_acct fa " +
				  " inner join gl_journal journal on fa.record_id = journal.gl_journal_id " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.ad_table_id = 224 and evig.c_element_id = 1000008 " + wherePeriod +
				  " group by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_period_id, fa.ad_client_id, fa.ad_org_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);

			
			// Para informe gerencial navegable, inserto en este momento en tabla molde por producto
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_PROD + " (parent_id, c_elementvalue_id, c_bpartner_id, m_product_id, c_period_id, saldomn, saldome, saldomm)";

			this.showHelp("Totales Producto...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id, " +
				  " (sum(fa.amtacctcr) - sum(fa.amtacctdr)) as saldomn, (sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id) as saldome," +
   				  " ((sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id))/1000 as saldomm" +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_bpartner_id is not null and fa.m_product_id is not null and evig.c_element_id = 1000008 " + wherePeriod +
				  " group by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id , fa.m_product_id, fa.c_period_id, fa.ad_client_id, fa.ad_org_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);


			// Para informe gerencial navegable, inserto en este momento en tabla molde de detalle por producto
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_PRODDET + " (parent_id, c_elementvalue_id, c_bpartner_id, m_product_id, c_period_id, ad_table_id, record_id, c_doctype_id, documentno, dateacct, saldomn, saldome, saldomm, qty)";

			this.showHelp("Detalles Producto...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id, " +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct, " +
				  " (sum(fa.amtacctcr) - sum(fa.amtacctdr)) as saldomn, (sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id) as saldome," +
   				  " ((sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id))/1000 as saldomm," +
				  " sum(coalesce(fa.qty,1)) as cantidad " +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_bpartner_id is not null and fa.m_product_id is not null and evig.c_element_id = 1000008 " + wherePeriod +
				  " group by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id," +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct, fa.ad_client_id, fa.ad_org_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);
			
			
			// Para informe gerencial navegable, inserto en este momento en tabla molde por ccosto
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_CC + " (parent_id, c_elementvalue_id, gl_journal_id, c_activity_id_1, c_period_id, saldomn, saldome, saldomm)";
			
			this.showHelp("Totales Departamento...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, " +
				  " (sum(fa.amtacctcr) - sum(fa.amtacctdr)) as saldomn, (sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id) as saldome," +
   				  " ((sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id))/1000 as saldomm" +
				  " from fact_acct fa " +
				  " inner join gl_journal journal on fa.record_id = journal.gl_journal_id " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.ad_table_id = 224 and fa.c_activity_id_1 is not null and evig.c_element_id = 1000008 " + wherePeriod +
				  " group by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, fa.ad_client_id, fa.ad_org_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);


			// Para informe gerencial navegable, inserto en este momento en tabla molde por detalle de ccosto
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_CCDET + " (parent_id, c_elementvalue_id, gl_journal_id, c_activity_id_1, c_period_id, ad_table_id, record_id, c_doctype_id, documentno, dateacct, saldomn, saldome, saldomm) ";
			
			this.showHelp("Detalles Departamento...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, " +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct, " +					
				  " (sum(fa.amtacctcr) - sum(fa.amtacctdr)) as saldomn, (sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id) as saldome," +
   				  " ((sum(fa.amtacctcr) - sum(fa.amtacctdr))/currencyrate_period(100,142,fa.c_period_id,null,fa.ad_client_id,fa.ad_org_id))/1000 as saldomm" +
				  " from fact_acct fa " +
				  " inner join gl_journal journal on fa.record_id = journal.gl_journal_id " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.ad_table_id = 224 and fa.c_activity_id_1 is not null and evig.c_element_id = 1000008 " + wherePeriod +
				  " group by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, " +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct, fa.ad_client_id, fa.ad_org_id " +				  
				  " order by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, fa.dateacct asc ";
				
			DB.executeUpdateEx(insert + sql, null);

		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
	
	/***
	 * Cargo datos en tabla molde del reporte.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 24/07/2013
	 * @see
	 */
	/*private void updateData() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_AUX +
				  " ORDER BY c_elementvalue_id asc, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			
			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta y periodo
			int cElementValueID = 0, cPeriodID = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Procesando " + rowCount++ + " de " + totalRowCount);
				
				// Corte por cuenta
				if (rs.getInt("c_elementvalue_id") != cElementValueID){
					
					// Si no estoy en primer registro actualizo cuenta anterior
					if (rowCount > 1){
						this.updateMoldeRecord(cElementValueID, cPeriodID, amt);
					}

					// Inserto nuevo registro en temporal final
					this.newMoldeRecord(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), 
									    rs.getString("accounttype"), rs.getInt("seqno"));
					
					cElementValueID = rs.getInt("c_elementvalue_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
					
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateMoldeRecord(cElementValueID, cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			this.updateMoldeRecord(cElementValueID, cPeriodID, amt);

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
		
	}*/
	
	/***
	 * Actualiza registro de temporal del reporte.
	 * OpenUp Ltda. Issue #1151 
	 * @author Nicolas Sarlabos - 24/07/2013
	 * @see
	 * @param cElementValueID
	 * @param cPeriodID
	 * @param amt
	 */
	/*private void updateMoldeRecord(int cElementValueID, int cPeriodID, BigDecimal amt) {
	
		try{
			
			// Posicion del periodo
			int posicion = hashPosicionXPeriodo.get(cPeriodID);
			
			// Monto segun tipo de moneda
			// Dolares
			if (this.currencyType.equalsIgnoreCase("ME")){
				amt = amt.divide(hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.currencyType.equalsIgnoreCase("MM")){
				amt = amt.divide(hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP)
						.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
			}
			
			String action = " UPDATE " + TABLA_MOLDE + 
							" SET amt" + String.valueOf(posicion) + "=" + amt +
							" WHERE idreporte='" + this.idReporte + "'" +
							" AND c_elementvalue_id=" + cElementValueID;
			
			DB.executeUpdateEx(action, null);
							
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}*/

	/***
	 * Inserta nuevo registro en temporal del reporte.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 24/07/2013
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param accountType 
	 * @param parentName
	 * @param acctName
	 */
	/*private void newMoldeRecord(int parentID, int cElementValueID, String accountType, int seqNo) {
		
		try{
		
			String accountTypeName = "INGRESOS";
			if (accountType != null){
				if (!accountType.equalsIgnoreCase("R")){
					accountTypeName = "EGRESOS";
				}
			}
			
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();
			String acctName = new MElementValue(Env.getCtx(), cElementValueID, null).getName();
			
			// Obtengo secuencia del padre
			String sql = "select seqno from ad_treenode where ad_tree_id = 1000146 and node_id =?";
			int seqNoParent = DB.getSQLValue(null, sql, parentID);
			
			String action = " INSERT INTO " + TABLA_MOLDE + " (parent_id, c_elementvalue_id, parentname, acctname, " +
							"idreporte, ad_user_id, accounttype, SeqNo, SeqNoParent) " +
		  			        " VALUES(" + parentID + "," + cElementValueID + ",'" + parentName + "','" + acctName + "','" +
							this.idReporte + "'," + adUserID + ",'" + accountTypeName + "'," +
							seqNo +	"," + seqNoParent + ")";
			
			DB.executeUpdateEx(action, null);
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}*/
	
	/***
	 * Actualiza titulos de periodos en temporal para poder mostrarlos por nombre en el reporte.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 24/07/2013
	 * @see
	 */
	/*private void updatePeriodsTitle(){
		
		try{
			String per1 = "", per2 = "", per3 = "", per4 = "", per5 = "", per6 = "", per7 = "", per8 = "";
			String per9 = "", per10 = "", per11 = "", per12 = "", per13 = "";		
			
			Object[] keys = hashPosicionXPeriodo.keySet().toArray();
			
			for (int i = 0; i < keys.length; i++){
				
				int posicicion = hashPosicionXPeriodo.get(keys[i]);
				String periodName = new MPeriod(Env.getCtx(), (Integer)keys[i], null).getName();
				
				if (posicicion == 1) per1 = periodName;
				if (posicicion == 2) per2 = periodName;
				if (posicicion == 3) per3 = periodName;
				if (posicicion == 4) per4 = periodName;
				if (posicicion == 5) per5 = periodName;
				if (posicicion == 6) per6 = periodName;
				if (posicicion == 7) per7 = periodName;
				if (posicicion == 8) per8 = periodName;
				if (posicicion == 9) per9 = periodName;
				if (posicicion == 10) per10 = periodName;
				if (posicicion == 11) per11 = periodName;
				if (posicicion == 12) per12 = periodName;
				if (posicicion == 13) per13 = periodName;
				
			}
			
			String action = "update " + TABLA_MOLDE + 
					        " set period1='" + per1 + "'," +
					        " period2='" + per2 + "'," +   
					        " period3='" + per3 + "'," +
					        " period4='" + per4 + "'," +
					        " period5='" + per5 + "'," +
					        " period6='" + per6 + "'," +
					        " period7='" + per7 + "'," +
					        " period8='" + per8 + "'," +
					        " period9='" + per9 + "'," +
					        " period10='" + per10 + "'," +
					        " period11='" + per11 + "'," +
					        " period12='" + per12 + "'," +
					        " period13='" + per13 + "' " +
					        " where idreporte='" + this.idReporte + "'";
			DB.executeUpdateEx(action, null);
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}*/
	
	/***
	 * Obtiene titulos de periodos desde molde.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 24/07/2013
	 * @see
	 */
	/*private void getPeriodsTitles(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{
			
			hashPeriodTitles = new HashMap<Integer, String>();
			
			sql =" select coalesce(period1,'') as period1, coalesce(period2,'') as period2, coalesce(period3,'') as period3," +
				 " coalesce(period4,'') as period4, coalesce(period5,'') as period5, coalesce(period6,'') as period6," +
				 " coalesce(period7,'') as period7, coalesce(period8,'') as period8, coalesce(period9,'') as period9," +	
				 " coalesce(period10,'') as period10, coalesce(period11,'') as period11, coalesce(period12,'') as period12," +
				 " coalesce(period13,'') as period13 " +				 
				 " from uy_molde_informegerencial " +
				 " where ad_user_id = ? ";	
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getAD_User_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				hashPeriodTitles.put(1, rs.getString("period1"));
				hashPeriodTitles.put(2, rs.getString("period2"));
				hashPeriodTitles.put(3, rs.getString("period3"));
				hashPeriodTitles.put(4, rs.getString("period4"));
				hashPeriodTitles.put(5, rs.getString("period5"));
				hashPeriodTitles.put(6, rs.getString("period6"));
				hashPeriodTitles.put(7, rs.getString("period7"));
				hashPeriodTitles.put(8, rs.getString("period8"));
				hashPeriodTitles.put(9, rs.getString("period9"));
				hashPeriodTitles.put(10, rs.getString("period10"));
				hashPeriodTitles.put(11, rs.getString("period11"));
				hashPeriodTitles.put(12, rs.getString("period12"));
				hashPeriodTitles.put(13, rs.getString("period13"));
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
		
	}*/
	
	/***
	 * Carga información en las distintas tablas de las pestañas del reporte.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 25/07/2013
	 * @see
	 */
	private void loadDataNav() {

		try{

			this.loadMain();

			// Cargo pestaña de cuentas contables del plan de cuentas. Segundo Nivel.
			this.loadAccounts();

			// Cargo pestaña de proveedores por cuenta contables de egresos. Tercer Nivel.
			this.loadBPartners();

			// Carga pestaña de asientos diarios por cuenta contable. Tercer Nivel
			this.loadJournals();

			// Cargo pestaña de productos por proveedor. Cuarto Nivel.
			this.loadProducts();

			// Carga pestaña de centros de costo por asiento diario. Cuarto Nivel
			this.loadCCostos();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}
	
	/***
	 * Nuevo registro de detalle (que luego incluira grilla de proveedores y asientos) para una
	 * determinada cuenta. Tercer Nivel.
	 * OpenUp Ltda. Issue #1151 
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 */
	private void insertDetail(int parentID, int cElementValueID) {

		try{
			
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();
			
			// Obtengo ID del registro del segundo nivel para la cuenta contable
			MGerencialAccount geraccount = MGerencialAccount.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);

			if ((geraccount == null) || (geraccount.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + cElementValueID +  " en Segundo Nivel.");
			
			if ((germain == null) || (germain.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + parentID +  " en Primer Nivel.");
			
			this.gerencialDet = new MGerencialDetail(getCtx(), 0, null);
			this.gerencialDet.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialDet.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialDet.setUY_Gerencial_Account_ID(geraccount.get_ID());
			this.gerencialDet.setParent_ID(parentID);
			this.gerencialDet.setparentname(parentName);
			this.gerencialDet.setC_ElementValue_ID(cElementValueID);
			
			this.gerencialDet.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}

		
	}
	
	/***
	 * Carga totales. Primer Nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 */
	private void loadMain() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{
		
			DB.executeUpdateEx(" delete from uy_gerencial_main cascade where uy_gerencial_id=" + this.gerencial.get_ID(), null);
			
			sql = "select * from " + TABLA_MOLDE_AUX + " order by seqnoparent, seqno";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				
				String color = "";
				BigDecimal amt = Env.ZERO;
				String type = "";
				int posicion = this.hashPosicionXPeriodo.get(rs.getInt("c_period_id")); // Posicion del periodo
				
				MGerencialMain gmain = new MGerencialMain(getCtx(),0,null);
				gmain.setUY_Gerencial_ID(this.uyGerencialID);
				gmain.setAD_Org_ID(0);
				gmain.setParent_ID(rs.getInt("parent_id"));
				gmain.setC_ElementValue_ID(rs.getInt("c_elementvalue_id"));
				gmain.setseqnoparent(rs.getInt("seqnoparent"));
				gmain.setSeqNo(rs.getInt("seqno"));
				
				if(rs.getString("accounttype").equalsIgnoreCase("E")){
					
					color = "DARK_BLUE";
					
				} else color = "THIN_BLUE";
				
				gmain.setColorSelector(color);
				
				if(rs.getString("accounttype").equalsIgnoreCase("E")){
					
					type = "EGRESOS";
					
				} else type = "INGRESOS";
				
				gmain.setAccountType(type);
				
				// Monto segun tipo de moneda
				//Moneda Nacional
				if (this.currencyType.equalsIgnoreCase("MN")){
					amt = rs.getBigDecimal("saldomn");
				}				
				// Dolares
				else if (this.currencyType.equalsIgnoreCase("ME")){
					amt = rs.getBigDecimal("saldome");
				}
				// Miles de dolares
				else if (this.currencyType.equalsIgnoreCase("MM")){
					amt = rs.getBigDecimal("saldomm");
				}
				
				//seteo importe segun el periodo
				if (posicion == 1) gmain.setamt1(amt); 
				else if (posicion == 2) gmain.setamt2(amt);
				else if (posicion == 3) gmain.setamt3(amt);
				else if (posicion == 4) gmain.setamt4(amt);
				else if (posicion == 5) gmain.setamt5(amt);
				else if (posicion == 6) gmain.setamt6(amt);
				else if (posicion == 7) gmain.setamt7(amt);
				else if (posicion == 8) gmain.setamt8(amt);
				else if (posicion == 9) gmain.setamt9(amt);
				else if (posicion == 10) gmain.setamt10(amt);
				else if (posicion == 11) gmain.setamt11(amt);
				else if (posicion == 12) gmain.setamt12(amt);
				else if (posicion == 13) gmain.setamt13(amt);
						
				gmain.saveEx();				
				
			}			
			
			// Cargo registros de totales por cuenta padre
			this.loadMainParents();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}		
	}
	
	/***
	 * A modo visual doy de alta registros de totales por cuenta padre en el primer nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 */
	private void loadMainParents() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{
			sql =" select parent_id, seqnoparent, sum(amt1) amt1, sum(amt2) amt2, sum(amt3) amt3, " +
				 " sum(amt4) amt4, sum(amt5) amt5, sum(amt6) amt6, sum(amt7) amt7, sum(amt8) amt8, sum(amt9) amt9, " +
				 " sum(amt10) amt10, sum(amt11) amt11, sum(amt12) amt12, sum(amt13) amt13 " +
				 " from uy_gerencial_main " +
				 " where uy_gerencial_id =? " +
				 " group by parent_id, seqnoparent " +
				 " order by seqnoparent";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.gerencial.get_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MGerencialMain germain = new MGerencialMain(getCtx(), 0, null);
				germain.setUY_Gerencial_ID(this.gerencial.get_ID());
				germain.setParent_ID(rs.getInt("parent_id"));
				
				MElementValue ev = new MElementValue(getCtx(), rs.getInt("parent_id"), null);
				germain.setparentname(ev.getName());
				
				germain.setseqnoparent(rs.getInt("seqnoparent"));
				germain.setSeqNo(-1);
				germain.setamt1(rs.getBigDecimal("amt1"));
				germain.setamt2(rs.getBigDecimal("amt2"));
				germain.setamt3(rs.getBigDecimal("amt3"));
				germain.setamt4(rs.getBigDecimal("amt4"));
				germain.setamt5(rs.getBigDecimal("amt5"));
				germain.setamt6(rs.getBigDecimal("amt6"));
				germain.setamt7(rs.getBigDecimal("amt7"));
				germain.setamt8(rs.getBigDecimal("amt8"));
				germain.setamt9(rs.getBigDecimal("amt9"));
				germain.setamt10(rs.getBigDecimal("amt10"));
				germain.setamt11(rs.getBigDecimal("amt11"));
				germain.setamt12(rs.getBigDecimal("amt12"));
				germain.setamt13(rs.getBigDecimal("amt13"));
				
				germain.saveEx();
			}
			
			// Cambio dinamicamente los headers de la grilla (totales primer nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);

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
	}
	
	/***
	 * Carga detalle de cuentas contables del plan de cuenta asociadas a las cuentas del informe.
	 * Tanto proveedores como asientos se muestran como dos grillas juntas dentro de la pestaña de detalles. 
	 * OpenUp Ltda. Issue #1151 
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 */
	private void loadAccounts() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_NAV_CTA + 
				  " ORDER BY parent_id, c_elementvalue_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			
			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta informe (parent_id), cuenta plan (c_elementvalue_id) y periodo
			int parentID = 0, cElementValueID = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Procesando " + rowCount++ + " de " + totalRowCount);
				
				// Corte por cuentas
				if ((rs.getInt("parent_id") != parentID) 
						|| (rs.getInt("c_elementvalue_id") != cElementValueID)){
					
					// Si no estoy en primer registro actualizo cuenta anterior
					if (rowCount > 1){
						this.updateAccount(cPeriodID, amt);
					}

					// Inserto nuevo registro en temporal final
					seqNo++;
					this.insertAccount(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), 
									   rs.getString("accounttype"), seqNo);
					
					parentID = rs.getInt("parent_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateAccount(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				// Monto segun tipo de moneda
				//Moneda Nacional
				if (this.currencyType.equalsIgnoreCase("MN")){
					amt = rs.getBigDecimal("saldomn");
				}				
				// Dolares
				else if (this.currencyType.equalsIgnoreCase("ME")){
					amt = rs.getBigDecimal("saldome");
				}
				// Miles de dolares
				else if (this.currencyType.equalsIgnoreCase("MM")){
					amt = rs.getBigDecimal("saldomm");
				}
			}
			
			// Ultimo registro
			this.updateAccount(cPeriodID, amt);

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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

	}

	/***
	 * Actualiza datos de una cuenta del plan asociada a una cuenta informe.
	 * Segundo Nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateAccount(int cPeriodID, BigDecimal amt) {
		
		try{
			
			// Posicion del periodo
			int posicion = this.hashPosicionXPeriodo.get(cPeriodID);
						
			if (posicion == 1) this.gerencialAccount.setamt1(amt); 
			else if (posicion == 2) this.gerencialAccount.setamt2(amt);
			else if (posicion == 3) this.gerencialAccount.setamt3(amt);
			else if (posicion == 4) this.gerencialAccount.setamt4(amt);
			else if (posicion == 5) this.gerencialAccount.setamt5(amt);
			else if (posicion == 6) this.gerencialAccount.setamt6(amt);
			else if (posicion == 7) this.gerencialAccount.setamt7(amt);
			else if (posicion == 8) this.gerencialAccount.setamt8(amt);
			else if (posicion == 9) this.gerencialAccount.setamt9(amt);
			else if (posicion == 10) this.gerencialAccount.setamt10(amt);
			else if (posicion == 11) this.gerencialAccount.setamt11(amt);
			else if (posicion == 12) this.gerencialAccount.setamt12(amt);
			else if (posicion == 13) this.gerencialAccount.setamt13(amt);
			
			this.gerencialAccount.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Inserta nuevo registro en tabla de cuentas del plan por cuenta de informe.
	 * Segundo Nivel.
	 * OpenUp Ltda. Issue #1151 
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param accountType
	 * @param seqNo
	 */
	private void insertAccount(int parentID, int cElementValueID, String accountType, int seqNo) {
		
		try{
		
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();
			
			int seqNoParent = 0;
			
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);
			
			if ((germain == null) || (germain.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + parentID +  " en Primer Nivel.");
			
			this.gerencialAccount = new MGerencialAccount(getCtx(), 0, null);
			this.gerencialAccount.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialAccount.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialAccount.setParent_ID(parentID);
			this.gerencialAccount.setparentname(parentName);
			this.gerencialAccount.setC_ElementValue_ID(cElementValueID);
			this.gerencialAccount.setAccountType(accountType);
			this.gerencialAccount.setseqnoparent(seqNoParent);
			this.gerencialAccount.setSeqNo(seqNo);
			
			this.gerencialAccount.saveEx();
			
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}
	
	/***
	 * Carga detalle de proveedores asociados a una cuenta contable del plan. Tercer Nivel.
	 * Tanto proveedores como asientos se muestran como dos grillas juntas dentro de la pestaña de detalles.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 */
	private void loadBPartners() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_NAV_BP + 
				  " ORDER BY parent_id, c_elementvalue_id, c_bpartner_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			
			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta informe (parent_id), cuenta plan (c_elementvalue_id), socio de negocio y periodo
			int parentID = 0, cElementValueID = 0, cBPartnerID = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Lineas SNeg. " + rowCount++ + " de " + totalRowCount);
				
				// Al cambiar cuenta se crea un nuevo registro de detalle.
				if ((rs.getInt("parent_id") != parentID) || (rs.getInt("c_elementvalue_id") != cElementValueID)){
					insertDetail(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"));
				}
				
				// Corte por cuentas y socio de negocio
				if ((rs.getInt("parent_id") != parentID) 
						|| (rs.getInt("c_elementvalue_id") != cElementValueID)
						|| (rs.getInt("c_bpartner_id") != cBPartnerID)){
					
					// Si no estoy en primer registro actualizo proveedor anterior
					if (rowCount > 1){
						this.updateBP(cPeriodID, amt);
					}

					// Inserto nuevo proveedor
					seqNo++;
					this.insertBP(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), rs.getInt("c_bpartner_id"), seqNo);
					
					parentID = rs.getInt("parent_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					cBPartnerID = rs.getInt("c_bpartner_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateBP(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				// Monto segun tipo de moneda
				//Moneda Nacional
				if (this.currencyType.equalsIgnoreCase("MN")){
					amt = rs.getBigDecimal("saldomn");
				}				
				// Dolares
				else if (this.currencyType.equalsIgnoreCase("ME")){
					amt = rs.getBigDecimal("saldome");
				}
				// Miles de dolares
				else if (this.currencyType.equalsIgnoreCase("MM")){
					amt = rs.getBigDecimal("saldomm");
				}
				
			}
			
			// Ultimo registro
			this.updateBP(cPeriodID, amt);

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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
		
	}
	
	/**
	 * Inserta registro de proveedor para cuenta contable.
	 * Tercer Nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param cBPartnerID
	 * @param seqNo
	 */
	private void insertBP(int parentID, int cElementValueID, int cBPartnerID, int seqNo) {
		
		try{
		
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();

			int seqNoParent = 0;
			
			// Obtengo ID del registro del segundo nivel para la cuenta contable
			MGerencialAccount geraccount = MGerencialAccount.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);

			if ((geraccount == null) || (geraccount.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + cElementValueID +  " en Segundo Nivel.");
			
			this.gerencialBP = new MGerencialBP(getCtx(), 0, null);
			this.gerencialBP.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialBP.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialBP.setUY_Gerencial_Account_ID(geraccount.get_ID());
			this.gerencialBP.setUY_Gerencial_Detail_ID(this.gerencialDet.get_ID());
			this.gerencialBP.setParent_ID(parentID);
			this.gerencialBP.setC_ElementValue_ID(cElementValueID);
			this.gerencialBP.setparentname(parentName);
			this.gerencialBP.setC_BPartner_ID(cBPartnerID);
			this.gerencialBP.setseqnoparent(seqNoParent);
			this.gerencialBP.setSeqNo(seqNo);
			
			this.gerencialBP.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}
	
	/***
	 * Actualizo importe de un proveedor segun periodo.
	 * OpenUp Ltda. Issue #1151 
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateBP(int cPeriodID, BigDecimal amt) {
		
		try{
			
			// Posicion del periodo
			int posicion = this.hashPosicionXPeriodo.get(cPeriodID);
				
			if (posicion == 1) this.gerencialBP.setamt1(amt); 
			else if (posicion == 2) this.gerencialBP.setamt2(amt);
			else if (posicion == 3) this.gerencialBP.setamt3(amt);
			else if (posicion == 4) this.gerencialBP.setamt4(amt);
			else if (posicion == 5) this.gerencialBP.setamt5(amt);
			else if (posicion == 6) this.gerencialBP.setamt6(amt);
			else if (posicion == 7) this.gerencialBP.setamt7(amt);
			else if (posicion == 8) this.gerencialBP.setamt8(amt);
			else if (posicion == 9) this.gerencialBP.setamt9(amt);
			else if (posicion == 10) this.gerencialBP.setamt10(amt);
			else if (posicion == 11) this.gerencialBP.setamt11(amt);
			else if (posicion == 12) this.gerencialBP.setamt12(amt);
			else if (posicion == 13) this.gerencialBP.setamt13(amt);
			
			this.gerencialBP.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Carga detalle de asientos de una cuenta contable.
	 * Tercer Nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 */
	private void loadJournals() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_NAV_JOURNAL + 
				  " ORDER BY parent_id, c_elementvalue_id, gl_journal_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			
			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta informe (parent_id), cuenta plan (c_elementvalue_id), socio de negocio y periodo
			int parentID = 0, cElementValueID = 0, glJournalID = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Asiento " + rowCount++ + " de " + totalRowCount);
				
				// Corte por cuentas y asiento
				if ((rs.getInt("parent_id") != parentID) 
						|| (rs.getInt("c_elementvalue_id") != cElementValueID)
						|| (rs.getInt("gl_journal_id") != glJournalID)){
					
					// Si no estoy en primer registro actualizo asiento anterior
					if (rowCount > 1){
						this.updateJournal(cPeriodID, amt);
					}

					// Inserto nuevo asiento
					seqNo++;
					this.insertJournal(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), rs.getInt("gl_journal_id"), seqNo);
					
					parentID = rs.getInt("parent_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					glJournalID = rs.getInt("gl_journal_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateJournal(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				// Monto segun tipo de moneda
				//Moneda Nacional
				if (this.currencyType.equalsIgnoreCase("MN")){
					amt = rs.getBigDecimal("saldomn");
				}				
				// Dolares
				else if (this.currencyType.equalsIgnoreCase("ME")){
					amt = rs.getBigDecimal("saldome");
				}
				// Miles de dolares
				else if (this.currencyType.equalsIgnoreCase("MM")){
					amt = rs.getBigDecimal("saldomm");
				}
			}
			
			// Ultimo registro
			this.updateJournal(cPeriodID, amt);

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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
	}
	
	/***
	 * Inserta informacion de asiento en grilla. Tercer Nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param glJournalID
	 * @param seqNo
	 */
	private void insertJournal(int parentID, int cElementValueID, int glJournalID, int seqNo) {
		
		try{
		
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();

			int seqNoParent = 0;

			// Obtengo ID del registro del tercer nivel para el detalle
			MGerencialDetail gerDetail = MGerencialDetail.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			
			// Si no tengo detalle puede ser porque es una cuenta que al no tener proveedor no le fue creado un detalle
			// Creo el registro de detalle ahora
			if (gerDetail == null){
				insertDetail(parentID, cElementValueID);
			}
			
			// Obtengo ID del registro del segundo nivel para la cuenta contable
			MGerencialAccount geraccount = MGerencialAccount.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);
		
			if ((geraccount == null) || (geraccount.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + cElementValueID +  " en Segundo Nivel.");

			this.gerencialJournal = new MGerencialJournal(getCtx(), 0, null);
			this.gerencialJournal.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialJournal.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialJournal.setUY_Gerencial_Account_ID(geraccount.get_ID());
			
			if (gerDetail == null){
				this.gerencialJournal.setUY_Gerencial_Detail_ID(this.gerencialDet.get_ID());
			}
			else{
				this.gerencialJournal.setUY_Gerencial_Detail_ID(gerDetail.get_ID());	
			}		
			
			this.gerencialJournal.setParent_ID(parentID);
			this.gerencialJournal.setC_ElementValue_ID(cElementValueID);
			this.gerencialJournal.setparentname(parentName);
			this.gerencialJournal.setGL_Journal_ID(glJournalID);
			this.gerencialJournal.setseqnoparent(seqNoParent);
			this.gerencialJournal.setSeqNo(seqNo);
			
			this.gerencialJournal.saveEx();

			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}
	
	/***
	 * Actualizo importe de un asiento segun periodo.
	 * OpenUp Ltda. Issue #1151 
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateJournal(int cPeriodID, BigDecimal amt) {
		
		try{
			
			// Posicion del periodo
			int posicion = this.hashPosicionXPeriodo.get(cPeriodID);
						
			if (posicion == 1) this.gerencialJournal.setamt1(amt); 
			else if (posicion == 2) this.gerencialJournal.setamt2(amt);
			else if (posicion == 3) this.gerencialJournal.setamt3(amt);
			else if (posicion == 4) this.gerencialJournal.setamt4(amt);
			else if (posicion == 5) this.gerencialJournal.setamt5(amt);
			else if (posicion == 6) this.gerencialJournal.setamt6(amt);
			else if (posicion == 7) this.gerencialJournal.setamt7(amt);
			else if (posicion == 8) this.gerencialJournal.setamt8(amt);
			else if (posicion == 9) this.gerencialJournal.setamt9(amt);
			else if (posicion == 10) this.gerencialJournal.setamt10(amt);
			else if (posicion == 11) this.gerencialJournal.setamt11(amt);
			else if (posicion == 12) this.gerencialJournal.setamt12(amt);
			else if (posicion == 13) this.gerencialJournal.setamt13(amt);
			
			this.gerencialJournal.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Carga detalle de productos asociados a un proveedor. Cuarto Nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 */
	private void loadProducts() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_NAV_PROD + 
				  " ORDER BY parent_id, c_elementvalue_id, c_bpartner_id, m_product_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			
			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta informe (parent_id), cuenta plan (c_elementvalue_id), socio de negocio, producto y periodo
			int parentID = 0, cElementValueID = 0, cBPartnerID = 0, mProductID = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Lineas Prod. " + rowCount++ + " de " + totalRowCount);
				
				// Corte por cuentas, socio de negocio y producto
				if ((rs.getInt("parent_id") != parentID) 
						|| (rs.getInt("c_elementvalue_id") != cElementValueID)
						|| (rs.getInt("c_bpartner_id") != cBPartnerID)
						|| (rs.getInt("m_product_id") != mProductID)){
					
					// Si no estoy en primer registro actualizo producto anterior
					if (rowCount > 1){
						this.updateProduct(cPeriodID, amt);
					}

					// Inserto nuevo producto
					seqNo++;
					this.insertProduct(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), rs.getInt("c_bpartner_id"), 
									   rs.getInt("m_product_id"), seqNo);
					
					parentID = rs.getInt("parent_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					cBPartnerID = rs.getInt("c_bpartner_id");
					mProductID = rs.getInt("m_product_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateProduct(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				// Monto segun tipo de moneda
				//Moneda Nacional
				if (this.currencyType.equalsIgnoreCase("MN")){
					amt = rs.getBigDecimal("saldomn");
				}				
				// Dolares
				else if (this.currencyType.equalsIgnoreCase("ME")){
					amt = rs.getBigDecimal("saldome");
				}
				// Miles de dolares
				else if (this.currencyType.equalsIgnoreCase("MM")){
					amt = rs.getBigDecimal("saldomm");
				}
			}
			
			// Ultimo registro
			this.updateProduct(cPeriodID, amt);

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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
		
	}
	
	/**
	 * Inserta registro de producto para un determinado proveedor.
	 * Cuartro Nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param cBPartnerID
	 * @param seqNo
	 */
	private void insertProduct(int parentID, int cElementValueID, int cBPartnerID, int mProductID, int seqNo) {
		
		try{
		
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();

			int seqNoParent = 0;
			
			// Obtengo ID del registro del segundo nivel para la cuenta contable
			MGerencialAccount geraccount = MGerencialAccount.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);
			// Obtengo ID del registro de tercer nivel para proveedor y cuenta informe
			MGerencialBP gerBP = MGerencialBP.forAccountBP(getCtx(), this.gerencial.get_ID(), parentID, cBPartnerID, null);
			
			if ((geraccount == null) || (geraccount.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + cElementValueID +  " en Segundo Nivel.");
			
			this.gerencialProd = new MGerencialProd(getCtx(), 0, null);
			this.gerencialProd.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialProd.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialProd.setUY_Gerencial_Account_ID(geraccount.get_ID());
			this.gerencialProd.setParent_ID(parentID);
			this.gerencialProd.setC_ElementValue_ID(cElementValueID);
			this.gerencialProd.setparentname(parentName);
			this.gerencialProd.setC_BPartner_ID(cBPartnerID);
			this.gerencialProd.setM_Product_ID(mProductID);
			this.gerencialProd.setUY_Gerencial_BP_ID(gerBP.get_ID());
			this.gerencialProd.setseqnoparent(seqNoParent);
			this.gerencialProd.setSeqNo(seqNo);
			
			this.gerencialProd.saveEx();
			
			// Cargo detalle de este producto
			this.loadProductDetails();
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

	/***
	 * Carga detalle de productos. Quinto Nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 */
	private void loadProductDetails() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal amt = Env.ZERO;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_NAV_PRODDET +
				  " AND parent_id =" + this.gerencialProd.getParent_ID() +
				  " AND c_elementvalue_id =" + this.gerencialProd.getC_ElementValue_ID() +
				  " AND c_bpartner_id =" + this.gerencialProd.getC_BPartner_ID() +
				  " AND m_product_id =" + this.gerencialProd.getM_Product_ID() +
				  " ORDER BY dateacct asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			
			rs = pstmt.executeQuery ();

			//rs.last();
			//int totalRowCount = rs.getRow(), rowCount = 0;
			//rs.beforeFirst();

			// Recorro registros y voy actualizando
			while (rs.next()){
				
				//this.showHelp("Detalle Prod. " + rowCount++ + " de " + totalRowCount);

				MGerencialProdDet det = new MGerencialProdDet(getCtx(), 0, null);
				det.setUY_Gerencial_ID(this.gerencialProd.getUY_Gerencial_ID());
				det.setUY_Gerencial_Main_ID(this.gerencialProd.getUY_Gerencial_Main_ID());
				det.setUY_Gerencial_Account_ID(this.gerencialProd.getUY_Gerencial_Account_ID());
				det.setParent_ID(this.gerencialProd.getParent_ID());
				det.setC_ElementValue_ID(this.gerencialProd.getC_ElementValue_ID());
				det.setparentname(this.gerencialProd.getparentname());
				det.setacctname(this.gerencialProd.getacctname());
				det.setC_BPartner_ID(this.gerencialProd.getC_BPartner_ID());
				det.setM_Product_ID(this.gerencialProd.getM_Product_ID());
				det.setAccountType(this.gerencialProd.getAccountType());
				det.setUY_Gerencial_Prod_ID(this.gerencialProd.get_ID());
				det.setAD_Table_ID(rs.getInt("ad_table_id"));
				det.setRecord_ID(rs.getInt("record_id"));
				det.setC_DocType_ID(rs.getInt("c_doctype_id"));
				det.setDocumentNo(rs.getString("documentno"));
				det.setDateAcct(rs.getTimestamp("dateacct"));
				det.setC_Period_ID(rs.getInt("c_period_id"));
				
				// Monto segun tipo de moneda
				//Moneda Nacional
				if (this.currencyType.equalsIgnoreCase("MN")){
					amt = rs.getBigDecimal("saldomn");
				}				
				// Dolares
				else if (this.currencyType.equalsIgnoreCase("ME")){
					amt = rs.getBigDecimal("saldome");
				}
				// Miles de dolares
				else if (this.currencyType.equalsIgnoreCase("MM")){
					amt = rs.getBigDecimal("saldomm");
				}
				
				det.setAmt(amt);
				
				BigDecimal qty = rs.getBigDecimal("qty");
				if (qty.compareTo(Env.ZERO) == 0) qty = Env.ONE;
				det.setQty(qty);
				
				// Para facturas tengo que obtener la cantidad directamente desde el comprobante y no desde la contabilidad
				if (det.getAD_Table_ID() == 318){
					qty = MInvoice.getProductTotalQty(getCtx(), det.getRecord_ID(), det.getM_Product_ID(), null);
					if (qty != null){
						if (qty.compareTo(Env.ZERO) == 0) qty = Env.ONE;
						det.setQty(qty);
					}
				}
				
				BigDecimal price = det.getAmt().divide(det.getQty(), 3, RoundingMode.HALF_UP);
				det.setPrice(price);
				det.saveEx();
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
		
	}
	
	/***
	 * Actualizo importe de un producto segun periodo.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateProduct(int cPeriodID, BigDecimal amt) {
		
		try{
			
			// Posicion del periodo
			int posicion = this.hashPosicionXPeriodo.get(cPeriodID);
					
			if (posicion == 1) this.gerencialProd.setamt1(amt); 
			else if (posicion == 2) this.gerencialProd.setamt2(amt);
			else if (posicion == 3) this.gerencialProd.setamt3(amt);
			else if (posicion == 4) this.gerencialProd.setamt4(amt);
			else if (posicion == 5) this.gerencialProd.setamt5(amt);
			else if (posicion == 6) this.gerencialProd.setamt6(amt);
			else if (posicion == 7) this.gerencialProd.setamt7(amt);
			else if (posicion == 8) this.gerencialProd.setamt8(amt);
			else if (posicion == 9) this.gerencialProd.setamt9(amt);
			else if (posicion == 10) this.gerencialProd.setamt10(amt);
			else if (posicion == 11) this.gerencialProd.setamt11(amt);
			else if (posicion == 12) this.gerencialProd.setamt12(amt);
			else if (posicion == 13) this.gerencialProd.setamt13(amt);
			
			this.gerencialProd.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Carga detalle de centros de costo de un asiento de una cuenta contable.
	 * Cuarto Nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 */
	private void loadCCostos() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_NAV_CC + 
				  " ORDER BY parent_id, c_elementvalue_id, gl_journal_id, c_activity_id_1, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			
			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta informe (parent_id), cuenta plan (c_elementvalue_id), asiento, centro de costo y periodo
			int parentID = 0, cElementValueID = 0, glJournalID = 0, cActivityID1 = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("CCosto " + rowCount++ + " de " + totalRowCount);
				
				// Corte por cuentas, asiento y centro de costo
				if ((rs.getInt("parent_id") != parentID) 
						|| (rs.getInt("c_elementvalue_id") != cElementValueID)
						|| (rs.getInt("gl_journal_id") != glJournalID)
						|| (rs.getInt("c_activity_id_1") != cActivityID1)){
					
					// Si no estoy en primer registro actualizo asiento anterior
					if (rowCount > 1){
						this.updateCCosto(cPeriodID, amt);
					}

					// Inserto nuevo ccosto
					seqNo++;
					this.insertCCosto(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), rs.getInt("gl_journal_id"), 
									  rs.getInt("c_activity_id_1"), seqNo);
					
					parentID = rs.getInt("parent_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					glJournalID = rs.getInt("gl_journal_id");
					cActivityID1 = rs.getInt("c_activity_id_1");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateCCosto(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				// Monto segun tipo de moneda
				//Moneda Nacional
				if (this.currencyType.equalsIgnoreCase("MN")){
					amt = rs.getBigDecimal("saldomn");
				}				
				// Dolares
				else if (this.currencyType.equalsIgnoreCase("ME")){
					amt = rs.getBigDecimal("saldome");
				}
				// Miles de dolares
				else if (this.currencyType.equalsIgnoreCase("MM")){
					amt = rs.getBigDecimal("saldomm");
				}
			}
			
			// Ultimo registro
			this.updateCCosto(cPeriodID, amt);

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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
	}
	
	/***
	 * Actualizo centro de costo de un asiento segun periodo.
	 * OpenUp Ltda. Issue #1151 
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateCCosto(int cPeriodID, BigDecimal amt) {
		
		try{
			
			// Posicion del periodo
			int posicion = this.hashPosicionXPeriodo.get(cPeriodID);
					
			if (posicion == 1) this.gerencialCC.setamt1(amt); 
			else if (posicion == 2) this.gerencialCC.setamt2(amt);
			else if (posicion == 3) this.gerencialCC.setamt3(amt);
			else if (posicion == 4) this.gerencialCC.setamt4(amt);
			else if (posicion == 5) this.gerencialCC.setamt5(amt);
			else if (posicion == 6) this.gerencialCC.setamt6(amt);
			else if (posicion == 7) this.gerencialCC.setamt7(amt);
			else if (posicion == 8) this.gerencialCC.setamt8(amt);
			else if (posicion == 9) this.gerencialCC.setamt9(amt);
			else if (posicion == 10) this.gerencialCC.setamt10(amt);
			else if (posicion == 11) this.gerencialCC.setamt11(amt);
			else if (posicion == 12) this.gerencialCC.setamt12(amt);
			else if (posicion == 13) this.gerencialCC.setamt13(amt);
			
			this.gerencialCC.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}	
	
	/***
	 * Inserta informacion de un centro de costo de un asiento. Cuarto Nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param glJournalID
	 * @param cActivityID1
	 * @param seqNo
	 */
	private void insertCCosto(int parentID, int cElementValueID, int glJournalID, int cActivityID1, int seqNo) {
		
		try{
		
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();

			int seqNoParent = 0;

			// Obtengo ID del registro del tercer nivel para el detalle
			MGerencialDetail gerDetail = MGerencialDetail.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			
			// Si no tengo detalle puede ser porque es una cuenta que al no tener proveedor no le fue creado un detalle
			// Creo el registro de detalle ahora
			if (gerDetail == null){
				insertDetail(parentID, cElementValueID);
			}
			
			// Obtengo ID del registro del segundo nivel para la cuenta contable
			MGerencialAccount geraccount = MGerencialAccount.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);
			// Obtengo ID del registro de tercer nivel para cuenta - asiento.
			MGerencialJournal gerjour = MGerencialJournal.forAccountJournal(getCtx(), this.gerencial.get_ID(), parentID, glJournalID, null);
			
			if ((geraccount == null) || (geraccount.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + cElementValueID +  " en Segundo Nivel.");

			this.gerencialCC = new MGerencialCC(getCtx(), 0, null);
			this.gerencialCC.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialCC.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialCC.setUY_Gerencial_Account_ID(geraccount.get_ID());
			this.gerencialCC.setParent_ID(parentID);
			this.gerencialCC.setC_ElementValue_ID(cElementValueID);
			this.gerencialCC.setparentname(parentName);
			this.gerencialCC.setGL_Journal_ID(glJournalID);
			this.gerencialCC.setC_Activity_ID_1(cActivityID1);
			this.gerencialCC.setUY_Gerencial_Journal_ID(gerjour.get_ID());
			this.gerencialCC.setseqnoparent(seqNoParent);
			this.gerencialCC.setSeqNo(seqNo);
			
			this.gerencialCC.saveEx();
			
			// Cargo detalle de este departamento
			this.loadCCDetails();			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}
	
	/***
	 * Carga detalle de departamentos. Quinto Nivel.
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 26/07/2013
	 * @see
	 */
	private void loadCCDetails() {

		String sql = "";
		BigDecimal amt = Env.ZERO;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_NAV_CCDET + 
				  " AND parent_id =" + this.gerencialCC.getParent_ID() +
				  " AND c_elementvalue_id =" + this.gerencialCC.getC_ElementValue_ID() +
				  " AND gl_journal_id =" + this.gerencialCC.getGL_Journal_ID() +
				  " AND c_activity_id_1 =" + this.gerencialCC.getC_Activity_ID_1() +
				  " ORDER BY dateacct asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			
			rs = pstmt.executeQuery ();

			//rs.last();
			//int totalRowCount = rs.getRow(), rowCount = 0;
			//rs.beforeFirst();

			// Recorro registros y voy actualizando
			while (rs.next()){
				
				//this.showHelp("Detalle Prod. " + rowCount++ + " de " + totalRowCount);

				MGerencialCCDet det = new MGerencialCCDet(getCtx(), 0, null);
				det.setUY_Gerencial_ID(this.gerencialCC.getUY_Gerencial_ID());
				det.setUY_Gerencial_Main_ID(this.gerencialCC.getUY_Gerencial_Main_ID());
				det.setUY_Gerencial_Account_ID(this.gerencialCC.getUY_Gerencial_Account_ID());
				det.setParent_ID(this.gerencialCC.getParent_ID());
				det.setC_ElementValue_ID(this.gerencialCC.getC_ElementValue_ID());
				det.setparentname(this.gerencialCC.getparentname());
				det.setacctname(this.gerencialCC.getacctname());
				det.setGL_Journal_ID(this.gerencialCC.getGL_Journal_ID());
				det.setC_Activity_ID_1(this.gerencialCC.getC_Activity_ID_1());
				det.setAccountType(this.gerencialCC.getAccountType());
				det.setUY_Gerencial_CC_ID(this.gerencialCC.get_ID());
				det.setAD_Table_ID(rs.getInt("ad_table_id"));
				det.setRecord_ID(rs.getInt("record_id"));
				det.setC_DocType_ID(rs.getInt("c_doctype_id"));
				det.setDocumentNo(rs.getString("documentno"));
				det.setDateAcct(rs.getTimestamp("dateacct"));
				det.setC_Period_ID(rs.getInt("c_period_id"));
				
				// Monto segun tipo de moneda
				//Moneda Nacional
				if (this.currencyType.equalsIgnoreCase("MN")){
					amt = rs.getBigDecimal("saldomn");
				}				
				// Dolares
				else if (this.currencyType.equalsIgnoreCase("ME")){
					amt = rs.getBigDecimal("saldome");
				}
				// Miles de dolares
				else if (this.currencyType.equalsIgnoreCase("MM")){
					amt = rs.getBigDecimal("saldomm");
				}
				
				det.setAmt(amt);
				det.saveEx();
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
		
	}

	/***
	 * Despliega avance en ventana splash
	 * OpenUp Ltda. Issue #1151
	 * @author Nicolas Sarlabos - 24/07/2013
	 * @see
	 * @param text
	 */
	private void showHelp(String text){
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}
		if (this.getGerencialAWindow() != null){
			this.gerencialAWindow.setWaitingMessage(text);
		}
	}
	
	public AWindow getGerencialAWindow(){
		return this.gerencialAWindow;
	}
	
	public void setGerencialAWindow(AWindow value){
		this.gerencialAWindow = value;
	}
	
	public Waiting getWaiting() {
		return this.waiting;
	}

	public void setWaiting(Waiting value) {
		this.waiting = value;
	}

}
