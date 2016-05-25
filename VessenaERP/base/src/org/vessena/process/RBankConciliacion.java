package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBank;
import org.compiere.model.MPeriod;
import org.compiere.model.MTable;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MLoadExtract;
import org.openup.model.MMoldeConciliaDet;
import org.openup.model.MMoldeConciliaTot;


public class RBankConciliacion extends SvrProcess {
	
	private static final String TABLA_MOLDE_DET = "UY_MOLDE_ConciliaDet";
	private static final String TABLA_MOLDE_TOT = "UY_MOLDE_ConciliaTot";
	private int adClientID = 0;
	private int adOrgID = 0;
	private int periodID = 0;
	private int accountID = 0;
	private String idReporte = "";
	private String titulo = "";
	private Long idUsuario = new Long(0);
	private Timestamp fechaHasta = null;
	private Timestamp fechaDesde = null;
	private int conciliationID = 0;
	private BigDecimal saldoCont = Env.ZERO;
	private BigDecimal saldoBanco = Env.ZERO;
	private BigDecimal amtSumDrCont = Env.ZERO;
	private BigDecimal amtSumCrCont = Env.ZERO;
	private BigDecimal amtSumDrBanco = Env.ZERO;
	private BigDecimal amtSumCrBanco = Env.ZERO;
	private BigDecimal ajustesCont = Env.ZERO;
	private BigDecimal ajustesBanco = Env.ZERO;
	private BigDecimal saldoBancoAjustado = Env.ZERO;
	private BigDecimal saldoContAjustado = Env.ZERO;
	private BigDecimal diferencia = Env.ZERO;
	

	public RBankConciliacion() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;
			
		// Obtengo parametros y los recorro
				ProcessInfoParameter[] para = getParameter();
				for (int i = 0; i < para.length; i++) {
					String name = para[i].getParameterName().trim();
					if (name != null) {
						
						if (name.equalsIgnoreCase("idReporte")){
							paramIDReporte = para[i]; 
						}
																	
						if (name.equalsIgnoreCase("Ad_Client_ID")) {
							if (para[i].getParameter() != null) {
								this.adClientID = ((BigDecimal) para[i].getParameter()).intValueExact();
							}
						}

						if (name.equalsIgnoreCase("Ad_Org_ID")) {
							if (para[i].getParameter() != null) {
								this.adOrgID = ((BigDecimal) para[i].getParameter()).intValueExact();
							}
						}
						
						if (name.equalsIgnoreCase("AD_User_ID")){
							this.idUsuario = ((BigDecimal)para[i].getParameter()).longValueExact();
						}
						
						if (name.equalsIgnoreCase("C_Period_ID")) {
							if (para[i].getParameter() != null) {
								this.periodID = ((BigDecimal) para[i].getParameter()).intValueExact();
							}
						}
															
						if (name.equalsIgnoreCase("C_BAnkAccount_ID")) {
							if (para[i].getParameter() != null) {
								this.accountID = ((BigDecimal) para[i].getParameter()).intValueExact();
							}
						}					
				
					}
				}	
							
				// Obtengo id para este reporte
				this.idReporte = UtilReportes.getReportID(this.idUsuario);
				
				// Si tengo parametro para idreporte
				if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);
			
	}

	@Override
	protected String doIt() throws Exception {
		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tablas moldes.
		this.loadDataTable1();		
		this.loadDataTable2();
		
		verifyEmptyTable(); //OpenUp. Nicolas Sarlabos. 14/06/2013. #1019. 
		
		return "ok";
	}
	
	/**
	 * OpenUp.	#1019
	 * Descripcion : Si no hay datos cargados en tabla molde final, inserta campos de saldos.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 14/06/2013
	 */
	private void verifyEmptyTable() {
		
		String sql = "";
		
		sql = "select count(uy_molde_conciliatot_id) from uy_molde_conciliatot where idreporte='" + this.idReporte + "'";
		int cant = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(cant <= 0) {
			
			MMoldeConciliaTot m = new MMoldeConciliaTot (getCtx(),0,get_TrxName());
			m.setidReporte(this.idReporte);
			m.setfecreporte(new Timestamp(System.currentTimeMillis()));
			m.setidcuenta(this.accountID);
			m.setidperiodo(this.periodID);
			m.setsaldobanco(this.saldoBanco);
			m.setsaldocont(this.saldoCont);
			m.setsaldobancoajus(this.saldoBancoAjustado);
			m.setsaldocontajus(this.saldoContAjustado);
			m.setdiferencia(this.diferencia);
			m.setamtsumcrbank(this.amtSumCrBanco);
			m.setamtsumdrbank(this.amtSumDrBanco);
			m.setamtsumcrsystem(this.amtSumCrCont);
			m.setamtsumdrsystem(this.amtSumDrCont);
			m.settitulo(this.titulo);
			m.setidusuario((this.idUsuario).intValue());
			m.settipo(Env.ONE);
			m.setUY_Conciliation_ID(this.conciliationID); //OpenUp. Nicolas Sarlabos. 31/10/2013. #1486. Cargo Id de conciliacion.
			m.saveEx();
						
		}
		
	}

	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE_DET +
				  " WHERE idusuario =" + this.idUsuario;
			
			DB.executeUpdate(sql,null);
			
			sql = "DELETE FROM " + TABLA_MOLDE_TOT +
					  " WHERE idusuario =" + this.idUsuario;
				
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
	
	/**
	 * OpenUp.	#741
	 * Descripcion : Carga movimientos no conciliados en la primer tabla molde.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 18/04/2013
	 */
	private void loadDataTable1(){
		
		String sql = "";
		int elementValueID = 0;
		//BigDecimal saldoCont = Env.ZERO;
		//BigDecimal saldoBanco = Env.ZERO;
		int loadID = 0;
				
		MPeriod period = new MPeriod (getCtx(),this.periodID,get_TrxName()); //instancio periodo
		
		this.fechaHasta = period.getEndDate(); //obtengo fecha final del periodo
		this.fechaDesde = period.getStartDate(); //obtengo fecha inicial del periodo
		
		//obtengo ID de la ultima conciliacion completa,aplicada o en proceso para la cuenta y periodo actuales
		sql = "select coalesce(max(uy_conciliation_id),0) " +
              " from uy_conciliation c " +
              " inner join c_period p on c.c_period_id = p.c_period_id " +
              " where c.ad_client_id=" + this.adClientID + " and c.ad_org_id=" + this.adOrgID + " and c.c_bankaccount_id=" + this.accountID + 
              " and c.docstatus in ('AY','CO','IP') and p.enddate <= " + "'" + this.fechaHasta + "'"; //OpenUp. Nicolas Sarlabos. 31/10/2013. #1486. Agrego docs aplicados.
		this.conciliationID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(this.conciliationID <= 0) {
			
			throw new AdempiereException ("No hay conciliaciones completas, aplicadas o en proceso para la cuenta y/o periodo ingresados");
							
		}
		
		try {
								
			loadFromSystem(); //cargo no conciliados del sistema
			loadFromBank(); //cargo no conciliados del banco
			
			//obtengo el c_elementvalue_id a partir de la cuenta bancaria ingresada como filtro
			sql = "SELECT cv.account_id" +
                  " FROM c_validcombination cv" +
                  " INNER JOIN c_bankaccount_acct b ON cv.c_validcombination_id=b.b_asset_acct" +
                  " WHERE c_bankaccount_id=" + this.accountID;
			elementValueID = DB.getSQLValueEx(get_TrxName(), sql);
			
			int mTableID_exdiff = MTable.getTable_ID("UY_ExchangeDiffHdr");
			
			//obtengo saldo de la contabilidad del sistema
			sql = "select coalesce(sum(uy_amtnativedr) - sum(uy_amtnativecr),0) " +
                  " from fact_acct" +
                  " where account_id = " + elementValueID + " and dateacct <= " + "'" + this.fechaHasta + "'" +
                  " and ad_table_id <> " + mTableID_exdiff;
			this.saldoCont = DB.getSQLValueBDEx(get_TrxName(), sql);
			
			//obtengo saldo del banco			
			sql = "select distinct coalesce(l.amount,0)" +
                  " from uy_loadextract l" +
                  " left outer join uy_bankextract ext on l.uy_loadextract_id = ext.uy_loadextract_id" +
                  " where l.c_bankaccount_id = " + this.accountID +
                  " and ext.datetrx >= '" + this.fechaDesde + "' and ext.datetrx <= " + "'" + this.fechaHasta +"'" + " and l.docstatus='CO'"; //OpenUp. Nicolas Sarlabos. 21/06/2013. #993
			this.saldoBanco = DB.getSQLValueBDEx(get_TrxName(), sql);
			
			if(saldoBanco==null || saldoBanco.compareTo(Env.ZERO)==0){
				
				//obtengo ultimo ID de carga de extracto
				sql = "select coalesce(max(uy_loadextract_id),0) from uy_loadextract where c_bankaccount_id = " + this.accountID + " and docstatus='CO'"; //OpenUp. Nicolas Sarlabos. 13/06/2013. #993
				loadID = DB.getSQLValueEx(get_TrxName(), sql);
				
				if(loadID > 0){
					//obtengo saldo del banco de la ultima carga realizada
					MLoadExtract load = new MLoadExtract (getCtx(),loadID,get_TrxName());
					saldoBanco = load.getAmount();			
					
				}
								
			}
			
			if(saldoBanco==null) saldoBanco = Env.ZERO;
					
			//obtengo totales de debitos y creditos no conciliados de ambas partes
			sql = "select coalesce(sum(amount),0)" +
                  " from " + TABLA_MOLDE_DET +
                  " where uy_conciliasystem_id > 0 and tipo=1 and iserror='N' and idreporte=" + "'" + this.idReporte + "'";
			this.amtSumDrCont = DB.getSQLValueBDEx(get_TrxName() , sql);  //suma de debitos del sistema
			
			sql = "select coalesce(sum(amount),0)" +
	              " from " + TABLA_MOLDE_DET +
	              " where uy_conciliasystem_id > 0 and tipo=2 and iserror='N' and idreporte=" + "'" + this.idReporte + "'";
			this.amtSumCrCont = DB.getSQLValueBDEx(get_TrxName() , sql);  //suma de creditos del sistema
			
			sql = "select coalesce(sum(amount),0)" +
	              " from " + TABLA_MOLDE_DET +
	              " where uy_conciliabank_id > 0 and tipo=1 and iserror='N' and idreporte=" + "'" + this.idReporte + "'";
			this.amtSumDrBanco = DB.getSQLValueBDEx(get_TrxName() , sql);  //suma de debitos del banco
				
			sql = "select coalesce(sum(amount),0)" +
		          " from " + TABLA_MOLDE_DET +
		          " where uy_conciliabank_id > 0 and tipo=2 and iserror='N' and idreporte=" + "'" + this.idReporte + "'";
			this.amtSumCrBanco = DB.getSQLValueBDEx(get_TrxName() , sql);  //suma de creditos del banco
			
			//cambio a numeros negativos
			this.amtSumDrBanco = this.amtSumDrBanco.negate();
			this.amtSumCrCont = this.amtSumCrCont.negate();
			
			this.saldoBancoAjustado = (saldoBanco.add(this.amtSumDrCont)).add(this.amtSumCrCont);
			this.saldoContAjustado = (saldoCont.add(this.amtSumDrBanco)).add(this.amtSumCrBanco);
			this.diferencia = this.saldoContAjustado.subtract(this.saldoBancoAjustado);
			
			//inserto y actualizao datos en tabla molde
			updateTable(this.saldoBanco,this.saldoCont,this.amtSumDrBanco,this.amtSumCrBanco,this.amtSumDrCont,this.amtSumCrCont,saldoBancoAjustado,saldoContAjustado,diferencia,this.periodID);
						
		}		
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		
	}
	
	/**
	 * OpenUp.	#741
	 * Descripcion : Carga movimientos del sistema no conciliados.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 18/04/2013
	 */
	
	private void loadFromSystem(){
		
		String insert = "", sql = "";

		try {

			insert = "INSERT INTO " + TABLA_MOLDE_DET + " (uy_molde_conciliadet_id,ad_client_id,ad_org_id,created,createdby,updated,updatedby,isactive,idreporte, idusuario, uy_conciliasystem_id, idcuenta, fecreporte, fecdoc, descripcion, " +
					"amount, tipo, iserror) ";		

			sql = "select nextid(1001704,'N')," + this.adClientID + "," + this.adOrgID + ",current_date," + this.idUsuario + ",current_date," + this.idUsuario + ",'Y','" + this.idReporte + "'" + "," + this.idUsuario + ",s.uy_conciliasystem_id,s.c_bankaccount_id,current_date," +
					" s.datetrx,s.description,case when s.amtsourcedr>0 then s.amtsourcedr when s.amtsourcecr>0 then s.amtsourcecr end as amount," +
					" case when s.amtsourcedr > 0 then 1 when s.amtsourcecr > 0 then 2 end as tipo,s.iserror" +
					" from uy_conciliasystem s" +
					" where s.uy_conciliated_id is null and s.uy_conciliation_id=" + this.conciliationID +
					" order by s.datetrx asc";

			DB.executeUpdateEx(insert + sql, get_TrxName());

		} 
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}		

	}
	
	/**
	 * OpenUp.	#741
	 * Descripcion : Carga movimientos del banco no conciliados .
	 * @author  Nicolas Sarlabos 
	 * Fecha : 18/04/2013
	 */
	
	private void loadFromBank(){
		
		String insert = "", sql = "";

		try {

			insert = "INSERT INTO " + TABLA_MOLDE_DET + " (uy_molde_conciliadet_id,ad_client_id,ad_org_id,created,createdby,updated,updatedby,isactive,idreporte, idusuario, uy_conciliabank_id, idcuenta, fecreporte, fecdoc, descripcion, " +
					"amount, uy_loadextract_id, tipo, iserror) ";		

			sql = "select nextid(1001704,'N')," + this.adClientID + "," + this.adOrgID + ",current_date," + this.idUsuario + ",current_date," + this.idUsuario + ",'Y','" + this.idReporte + "'" + "," + this.idUsuario + ",b.uy_conciliabank_id,b.c_bankaccount_id,current_date," +
					" b.datetrx,b.description, case when b.amtsourcedr>0 then b.amtsourcedr when b.amtsourcecr>0 then b.amtsourcecr end as amount," +
					" b.uy_loadextract_id,case when b.amtsourcedr > 0 then 1 when b.amtsourcecr > 0 then 2 end as tipo,b.iserror" +
					" from uy_conciliabank b" +
					" where b.uy_conciliated_id is null and b.uy_conciliation_id=" + this.conciliationID + " and (b.amtsourcedr>0 or b.amtsourcecr>0)" +
					" order by b.datetrx asc";

			DB.executeUpdateEx(insert + sql, get_TrxName());

		} 
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}		

	}
	
	/**
	 * OpenUp.	#741
	 * Descripcion : Carga de montos calculados en tabla molde.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 21/04/2013
	 */

	private void updateTable(BigDecimal saldoBanco,BigDecimal saldoCont,BigDecimal amtSumDrBanco,BigDecimal amtSumCrBanco,BigDecimal amtSumDrCont,
			BigDecimal amtSumCrCont,BigDecimal saldoBancoAjustado,BigDecimal saldoContAjustado,BigDecimal diferencia,int idperiodo){

		String sql = "";

		try {
					
			MBank bank = MBank.getFromAccount(getCtx(),this.accountID);
			MPeriod period = new MPeriod (getCtx(),idperiodo,get_TrxName());
			
			sql = "SELECT c.cursymbol" +
			      " FROM c_currency c" +
				  " INNER JOIN c_bankaccount a ON c.c_currency_id = a.c_currency_id" +
			      " WHERE a.c_bankaccount_id=" + this.accountID;
			String symbol = DB.getSQLValueStringEx(get_TrxName(), sql);
			
			this.titulo = "CONCILIACION " + bank.getName().toUpperCase() + " " + symbol.toUpperCase() + " " + period.getName().toUpperCase();
			
			sql = "UPDATE " + TABLA_MOLDE_DET + " set saldobanco=" + saldoBanco + ",saldocont=" + saldoCont + ",amtsumdrbank=" + amtSumDrBanco + ",amtsumcrbank=" +
					amtSumCrBanco + ",amtsumdrsystem=" + amtSumDrCont + ",amtsumcrsystem=" + amtSumCrCont + ",saldobancoajus=" + saldoBancoAjustado + ",saldocontajus=" +
					saldoContAjustado + ",diferencia=" + diferencia + ",idperiodo=" + idperiodo + ",titulo=" + "'" + this.titulo + "'" + " WHERE idreporte=" + "'" + this.idReporte + "'";
			DB.executeUpdateEx(sql, get_TrxName());
			
			DB.executeUpdateEx("update " + TABLA_MOLDE_DET + " set idgrupo=0,descgrupoizq='Cheques Pendientes',descgrupoder='Cheques Pendientes' where uy_conciliasystem_id is not null and tipo=2 and iserror='N' and idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario,get_TrxName());
			DB.executeUpdateEx("update " + TABLA_MOLDE_DET + " set idgrupo=0,descgrupoizq='Cheques Pendientes',descgrupoder='Cheques Pendientes' where uy_conciliabank_id is not null and tipo=1 and iserror='N' and idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario,get_TrxName());
			DB.executeUpdateEx("update " + TABLA_MOLDE_DET + " set idgrupo=1,descgrupoizq='Depositos Pendientes',descgrupoder='Depositos Pendientes' where idgrupo is null and iserror='N' and idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario,get_TrxName());
			DB.executeUpdateEx("update " + TABLA_MOLDE_DET + " set idgrupo=2,descgrupoizq='+/- Ajustes Banco',descgrupoder='+/- Ajustes Contabilidad' where iserror='Y' and idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario,get_TrxName());
			
		} catch (Exception e){
			throw new AdempiereException(e);
		}				
		
	}
	
	/**
	 * OpenUp.	#741
	 * Descripcion : Carga datos en la segunda tabla molde.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 24/04/2013
	 */
	private void loadDataTable2(){
		
		String insert = "", sql = "";
		BigDecimal amt = Env.ZERO;

		try {
			//inserto en la segunda tabla molde los creditos del sistema///////////////////////////////////////////////////
			insert = "INSERT INTO " + TABLA_MOLDE_TOT + " (uy_molde_conciliatot_id,ad_client_id,ad_org_id,created,createdby,updated,updatedby,isactive,uy_molde_conciliadet_id_sys,idreporte,idusuario,fecreporte,titulo,idcuenta,idperiodo,saldobanco,saldocont," +
					 "saldobancoajus,saldocontajus,diferencia,fecdoc_sys,descripcion_sys,amount_sys,tipo,idgrupo,descgrupoizq,descgrupoder,uy_conciliasystem_id,uy_loadextract_id,amtsumdrbank,amtsumcrbank,amtsumdrsystem,amtsumcrsystem,concsys) ";		

			sql = "select nextid(1001705,'N')," + this.adClientID + "," + this.adOrgID + ",current_date," + this.idUsuario + ",current_date," + this.idUsuario + ",'Y',uy_molde_conciliadet_id,idreporte,idusuario,fecreporte,titulo,idcuenta,idperiodo,saldobanco,saldocont," +
                    " saldobancoajus,saldocontajus,diferencia,fecdoc,descripcion,amount,tipo,idgrupo,descgrupoizq,descgrupoder,uy_conciliasystem_id,uy_loadextract_id,amtsumdrbank,amtsumcrbank,amtsumdrsystem,amtsumcrsystem,0" +				
					" from " + TABLA_MOLDE_DET +
					" where ad_client_id= " + this.adClientID + " and ad_org_id=" + this.adOrgID + 
					" and idgrupo=0 and uy_conciliasystem_id is not null and iserror='N' and idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario +
					" order by fecdoc asc";

			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, get_TrxName());
			
			String where = "", orderby = "";
			
			// Obtengo array de creditos sistema 
			where = " where idreporte=" + "'" + this.idReporte + "'" + 
					" and idgrupo=0 and uy_conciliasystem_id is not null and iserror='N' ";
			orderby = " order by fecdoc asc ";
			MMoldeConciliaDet[] sisCreds = MMoldeConciliaDet.getLines(getCtx(), where, orderby, get_TrxName());
			
			// Obtengo array de debitos banco
			where = " where idreporte=" + "'" + this.idReporte + "'" + 
					" and idgrupo=0 and uy_conciliabank_id is not null and iserror='N' ";
			orderby = " order by fecdoc asc ";
			MMoldeConciliaDet[] bankDebs = MMoldeConciliaDet.getLines(getCtx(), where, orderby, get_TrxName());

			// Si tengo debitos de banco
			if (bankDebs.length > 0){
				
				for (int i = 0; i < bankDebs.length; i ++){
					
					// Mientras no me pase de la cantidad de registros que inserte para creditos del sistema
					if (i < sisCreds.length){
						
						MMoldeConciliaTot concTot = MMoldeConciliaTot.forConciliaDet(getCtx(), sisCreds[i].get_ID(), true, get_TrxName());
						concTot.setfecdoc_bk(bankDebs[i].getfecdoc());
						concTot.setdescripcion_bk(bankDebs[i].getdescripcion());
						concTot.setamount_bk(bankDebs[i].getAmount());
						concTot.setuy_molde_conciliadet_id_bk(bankDebs[i].get_ID());
						concTot.setUY_ConciliaBank_ID(bankDebs[i].getUY_ConciliaBank_ID());
						concTot.settipo(bankDebs[i].gettipo());
						concTot.saveEx();
						
					}
					else{
									
						insert = "INSERT INTO " + TABLA_MOLDE_TOT + " (uy_molde_conciliatot_id,ad_client_id,ad_org_id,created,createdby,updated,updatedby,isactive,uy_molde_conciliadet_id_bk,idreporte,idusuario,fecreporte,titulo,idcuenta,idperiodo,saldobanco,saldocont," +
								 "saldobancoajus,saldocontajus,diferencia,fecdoc_bk,descripcion_bk,amount_bk,tipo,idgrupo,descgrupoizq,descgrupoder,uy_conciliabank_id,uy_loadextract_id,amtsumdrbank,amtsumcrbank,amtsumdrsystem,amtsumcrsystem,concsys) ";		

						sql = "select nextid(1001705,'N')," + this.adClientID + "," + this.adOrgID + ",current_date," + this.idUsuario + ",current_date," + this.idUsuario + ",'Y'," + bankDebs[i].get_ID() + ",'" + this.idReporte + "'," + this.idUsuario + "," + "'" + bankDebs[i].getfecreporte() + "'" + ",'" + this.titulo + "'" +
								"," + bankDebs[i].getidcuenta() + "," + bankDebs[i].getidperiodo() + "," + bankDebs[i].getsaldobanco() + "," + bankDebs[i].getsaldocont() + "," + bankDebs[i].getsaldobancoajus() + "," + bankDebs[i].getsaldocontajus() +
								"," + bankDebs[i].getdiferencia() + "," + "'" + bankDebs[i].getfecdoc() + "'" + "," + "'" + bankDebs[i].getdescripcion() + "'" + "," + bankDebs[i].getAmount() + "," + bankDebs[i].gettipo() + "," + bankDebs[i].getidgrupo() + ",'" + bankDebs[i].getdescgrupoizq() + "','" + bankDebs[i].getdescgrupoder() + "'," + bankDebs[i].getUY_ConciliaBank_ID() + "," +
								bankDebs[i].getUY_LoadExtract_ID() + "," + bankDebs[i].getamtsumdrbank() + "," + bankDebs[i].getamtsumcrbank() + "," + bankDebs[i].getamtsumdrsystem() + "," + bankDebs[i].getamtsumcrsystem() + ",1";
						
						log.log(Level.INFO, insert + sql);
						DB.executeUpdateEx(insert + sql, get_TrxName());
						
					}
				}
			}			
			
			//inserto en la segunda tabla molde los debitos del sistema///////////////////////////////////////////////////
			insert = "INSERT INTO " + TABLA_MOLDE_TOT + " (uy_molde_conciliatot_id,ad_client_id,ad_org_id,created,createdby,updated,updatedby,isactive,uy_molde_conciliadet_id_sys,idreporte,idusuario,fecreporte,titulo,idcuenta,idperiodo,saldobanco,saldocont," +
					 "saldobancoajus,saldocontajus,diferencia,fecdoc_sys,descripcion_sys,amount_sys,tipo,idgrupo,descgrupoizq,descgrupoder,uy_conciliasystem_id,uy_loadextract_id,amtsumdrbank,amtsumcrbank,amtsumdrsystem,amtsumcrsystem,concsys) ";		

			sql = "select nextid(1001705,'N')," + this.adClientID + "," + this.adOrgID + ",current_date," + this.idUsuario + ",current_date," + this.idUsuario + ",'Y',uy_molde_conciliadet_id,idreporte,idusuario,fecreporte,titulo,idcuenta,idperiodo,saldobanco,saldocont," +
                    " saldobancoajus,saldocontajus,diferencia,fecdoc,descripcion,amount,tipo,idgrupo,descgrupoizq,descgrupoder,uy_conciliasystem_id,uy_loadextract_id,amtsumdrbank,amtsumcrbank,amtsumdrsystem,amtsumcrsystem,0" +				
					" from " + TABLA_MOLDE_DET +
					" where ad_client_id= " + this.adClientID + " and ad_org_id=" + this.adOrgID + 
					" and idgrupo=1 and uy_conciliasystem_id is not null and iserror='N' and idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario +
					" order by fecdoc asc";
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, get_TrxName());
			
			// Obtengo array de debitos sistema 
			where = " where idreporte=" + "'" + this.idReporte + "'" + 
					" and idgrupo=1 and uy_conciliasystem_id is not null and iserror='N' ";
			orderby = " order by fecdoc asc ";
			MMoldeConciliaDet[] sisDebs = MMoldeConciliaDet.getLines(getCtx(), where, orderby, get_TrxName());
						
			// Obtengo array de creditos banco
			where = " where idreporte=" + "'" + this.idReporte + "'" + 
					" and idgrupo=1 and uy_conciliabank_id is not null and iserror='N' ";
			orderby = " order by fecdoc asc ";
			MMoldeConciliaDet[] bankCreds = MMoldeConciliaDet.getLines(getCtx(), where, orderby, get_TrxName());
			
			// Si tengo creditos de banco
			if (bankCreds.length > 0){
				
				for (int i = 0; i < bankCreds.length; i ++){
					
					// Mientras no me pase de la cantidad de registros que inserte para debitos del sistema
					if (i < sisDebs.length){
						
						MMoldeConciliaTot concTot = MMoldeConciliaTot.forConciliaDet(getCtx(), sisDebs[i].get_ID(), true, get_TrxName());
						concTot.setfecdoc_bk(bankCreds[i].getfecdoc());
						concTot.setdescripcion_bk(bankCreds[i].getdescripcion());
						concTot.setamount_bk(bankCreds[i].getAmount());
						concTot.setuy_molde_conciliadet_id_bk(bankCreds[i].get_ID());
						concTot.setUY_ConciliaBank_ID(bankCreds[i].getUY_ConciliaBank_ID());
						concTot.settipo(bankCreds[i].gettipo());
						concTot.saveEx();

					}
					else{

						insert = "INSERT INTO " + TABLA_MOLDE_TOT + " (uy_molde_conciliatot_id,ad_client_id,ad_org_id,created,createdby,updated,updatedby,isactive,uy_molde_conciliadet_id_bk,idreporte,idusuario,fecreporte,titulo,idcuenta,idperiodo,saldobanco,saldocont," +
								"saldobancoajus,saldocontajus,diferencia,fecdoc_bk,descripcion_bk,amount_bk,tipo,idgrupo,descgrupoizq,descgrupoder,uy_conciliabank_id,uy_loadextract_id,amtsumdrbank,amtsumcrbank,amtsumdrsystem,amtsumcrsystem,concsys) ";		

						sql = "select nextid(1001705,'N')," + this.adClientID + "," + this.adOrgID + ",current_date," + this.idUsuario + ",current_date," + this.idUsuario + ",'Y'," + bankCreds[i].get_ID() + ",'" + this.idReporte + "'," + this.idUsuario + "," + "'" + bankCreds[i].getfecreporte() + "'," + "'" + this.titulo + "'" +  
								"," + bankCreds[i].getidcuenta() + "," + bankCreds[i].getidperiodo() + "," + bankCreds[i].getsaldobanco() + "," + bankCreds[i].getsaldocont() + "," + bankCreds[i].getsaldobancoajus() + "," + bankCreds[i].getsaldocontajus() +
								"," + bankCreds[i].getdiferencia() + "," + "'" + bankCreds[i].getfecdoc() + "'" + "," + "'" + bankCreds[i].getdescripcion() + "'" + "," + bankCreds[i].getAmount() + "," + bankCreds[i].gettipo() + "," + bankCreds[i].getidgrupo() + ",'" + bankCreds[i].getdescgrupoizq() + "','" + bankCreds[i].getdescgrupoder() + "'," + bankCreds[i].getUY_ConciliaBank_ID() + "," +
								bankCreds[i].getUY_LoadExtract_ID() + "," + bankCreds[i].getamtsumdrbank() + "," + bankCreds[i].getamtsumcrbank() + "," + bankCreds[i].getamtsumdrsystem() + "," + bankCreds[i].getamtsumcrsystem() + ",1";

						log.log(Level.INFO, insert + sql);
						DB.executeUpdateEx(insert + sql, get_TrxName());			


					}				
					
				}				
				
			}	
			//PARA ESTE GRUPO DEL REPORTE TENGO QUE INSERTAR AL REVES, LOS DATOS DEL BANCO EN LOS CAMPOS DEL SISTEMA Y VECEVERSA
			//inserto en la segunda tabla molde los ajustes por error del banco///////////////////////////////////////////////////
			insert = "INSERT INTO " + TABLA_MOLDE_TOT + " (uy_molde_conciliatot_id,ad_client_id,ad_org_id,created,createdby,updated,updatedby,isactive,uy_molde_conciliadet_id_sys,idreporte,idusuario,fecreporte,titulo,idcuenta,idperiodo,saldobanco,saldocont," +
					 "saldobancoajus,saldocontajus,diferencia,fecdoc_sys,descripcion_sys,amount_sys,tipo,idgrupo,descgrupoizq,descgrupoder,uy_conciliasystem_id,uy_loadextract_id,amtsumdrbank,amtsumcrbank,amtsumdrsystem,amtsumcrsystem,concsys) ";		

			sql = "select nextid(1001705,'N')," + this.adClientID + "," + this.adOrgID + ",current_date," + this.idUsuario + ",current_date," + this.idUsuario + ",'Y',uy_molde_conciliadet_id,idreporte,idusuario,fecreporte,titulo,idcuenta,idperiodo,saldobanco,saldocont," +
                    " saldobancoajus,saldocontajus,diferencia,fecdoc,descripcion,(case when tipo=2 then amount*(-1) else amount end) as amount,tipo,idgrupo,descgrupoizq,descgrupoder,uy_conciliabank_id,uy_loadextract_id,amtsumdrbank,amtsumcrbank,amtsumdrsystem,amtsumcrsystem,0" +				
					" from " + TABLA_MOLDE_DET +
					" where ad_client_id= " + this.adClientID + " and ad_org_id=" + this.adOrgID + 
					" and idgrupo=2 and uy_conciliabank_id is not null and iserror='Y' and idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario +
					" order by fecdoc asc";
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, get_TrxName());
			
			// Obtengo array de ajustes por error del banco
			where = " where idreporte=" + "'" + this.idReporte + "'" + 
					" and idgrupo=2 and uy_conciliabank_id is not null and iserror='Y' ";
			orderby = " order by fecdoc asc ";
			MMoldeConciliaDet[] bankErrors = MMoldeConciliaDet.getLines(getCtx(), where, orderby, get_TrxName());
						
			// Obtengo array de ajustes por error del sistema
			where = " where idreporte=" + "'" + this.idReporte + "'" + 
					" and idgrupo=2 and uy_conciliasystem_id is not null and iserror='Y' ";
			orderby = " order by fecdoc asc ";
			MMoldeConciliaDet[] sisErrors = MMoldeConciliaDet.getLines(getCtx(), where, orderby, get_TrxName());

			// Si tengo ajustes por error del sistema
			if (sisErrors.length > 0){

				for (int i = 0; i < sisErrors.length; i ++){
					
					if(sisErrors[i].gettipo().compareTo(Env.ONE)==0){
						amt = sisErrors[i].getAmount().negate();
					} else amt = sisErrors[i].getAmount();
					

					// Mientras no me pase de la cantidad de registros que inserte para ajustes por error del banco
					if (i < bankErrors.length){

						MMoldeConciliaTot concTot = MMoldeConciliaTot.forConciliaDet(getCtx(), bankErrors[i].get_ID(), true, get_TrxName());
						concTot.setfecdoc_bk(sisErrors[i].getfecdoc());
						concTot.setdescripcion_bk(sisErrors[i].getdescripcion());
						concTot.setamount_bk(amt);
						concTot.setuy_molde_conciliadet_id_sys(sisErrors[i].get_ID());
						concTot.setUY_ConciliaBank_ID(sisErrors[i].getUY_ConciliaSystem_ID());
						concTot.settipo(sisErrors[i].gettipo());
						concTot.saveEx();

					}
					else{
						
						insert = "INSERT INTO " + TABLA_MOLDE_TOT + " (uy_molde_conciliatot_id,ad_client_id,ad_org_id,created,createdby,updated,updatedby,isactive,uy_molde_conciliadet_id_bk,idreporte,idusuario,fecreporte,titulo,idcuenta,idperiodo,saldobanco,saldocont," +
								"saldobancoajus,saldocontajus,diferencia,fecdoc_bk,descripcion_bk,amount_bk,tipo,idgrupo,descgrupoizq,descgrupoder,uy_conciliabank_id,uy_loadextract_id,amtsumdrbank,amtsumcrbank,amtsumdrsystem,amtsumcrsystem,concsys) ";		

						sql = "select nextid(1001705,'N')," + this.adClientID + "," + this.adOrgID + ",current_date," + this.idUsuario + ",current_date," + this.idUsuario + ",'Y'," + sisErrors[i].get_ID() + ",'" + this.idReporte + "'," + this.idUsuario + "," + "'" + sisErrors[i].getfecreporte() + "'," + "'" + this.titulo + "'" +  
								"," + sisErrors[i].getidcuenta() + "," + sisErrors[i].getidperiodo() + "," + sisErrors[i].getsaldobanco() + "," + sisErrors[i].getsaldocont() + "," + sisErrors[i].getsaldobancoajus() + "," + sisErrors[i].getsaldocontajus() +
								"," + sisErrors[i].getdiferencia() + "," + "'" + sisErrors[i].getfecdoc() + "'" + "," + "'" + sisErrors[i].getdescripcion() + "'" + "," + amt + "," + sisErrors[i].gettipo() + "," + sisErrors[i].getidgrupo() + ",'" + sisErrors[i].getdescgrupoizq() + "','" + sisErrors[i].getdescgrupoder() + "'," + sisErrors[i].getUY_ConciliaBank_ID() + "," +
								sisErrors[i].getUY_LoadExtract_ID() + "," + sisErrors[i].getamtsumdrbank() + "," + sisErrors[i].getamtsumcrbank() + "," + sisErrors[i].getamtsumdrsystem() + "," + sisErrors[i].getamtsumcrsystem() + ",1";

						log.log(Level.INFO, insert + sql);
						DB.executeUpdateEx(insert + sql, get_TrxName());			


					}				

				}				

			}	
			
			//obtengo suma total de ajustes del banco
			sql = "select coalesce(sum(amount_sys),0)" +
			      " from " + TABLA_MOLDE_TOT +
			      " where idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario +
			      " and idgrupo=2";
			this.ajustesBanco = DB.getSQLValueBDEx(get_TrxName(), sql);
			
			//obtengo suma total de ajustes del sistema
			sql = "select coalesce(sum(amount_bk),0)" +
				  " from " + TABLA_MOLDE_TOT +
				  " where idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario +
				  " and idgrupo=2";
			this.ajustesCont = DB.getSQLValueBDEx(get_TrxName(), sql);
				
			this.saldoBancoAjustado = this.saldoBancoAjustado.add(this.ajustesBanco);
			this.saldoContAjustado = this.saldoContAjustado.add(this.ajustesCont);
			this.diferencia = this.saldoContAjustado.subtract(this.saldoBancoAjustado);
			
			DB.executeUpdateEx("update " + TABLA_MOLDE_TOT + " set amtsumsystem=" + this.amtSumCrCont + ",amtsumbank=" + this.amtSumDrBanco + " where idgrupo=0 and idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario,get_TrxName());
			DB.executeUpdateEx("update " + TABLA_MOLDE_TOT + " set amtsumsystem=" + this.amtSumDrCont + ",amtsumbank=" + this.amtSumCrBanco + " where idgrupo=1 and idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario,get_TrxName());
			DB.executeUpdateEx("update " + TABLA_MOLDE_TOT + " set amtsumsystem=" + this.ajustesBanco + ",amtsumbank=" + this.ajustesCont + " where idgrupo=2 and idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario,get_TrxName());
			DB.executeUpdateEx("update " + TABLA_MOLDE_TOT + " set saldobancoajus=" + this.saldoBancoAjustado + ", saldocontajus=" + this.saldoContAjustado + ", diferencia=" + this.diferencia + " where idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario,get_TrxName());
			DB.executeUpdateEx("update " + TABLA_MOLDE_TOT + " set uy_conciliation_id=" + this.conciliationID + " where idreporte=" + "'" + this.idReporte + "'" + " and idusuario=" + this.idUsuario,get_TrxName()); //OpenUp. Nicolas Sarlabos. 31/10/2013. #1486. Cargo Id de conciliacion.
			
		} 
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}		

}
