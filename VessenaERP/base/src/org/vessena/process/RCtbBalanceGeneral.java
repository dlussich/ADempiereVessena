/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;

import org.compiere.apps.Waiting;
import org.compiere.model.MElementValue;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author OpenUp. Gabriel Vila. 09/11/2011.
 * Reporte Balance General Contable. Issue #891.
 *
 */
public class RCtbBalanceGeneral extends SvrProcess {
	private Waiting waiting = null;
	private Timestamp dateTo;
	private int uyCapituloID = 0, cAcctSchemaID = 0, adTreeID = 0, nivelFiltro = 0;
	private int accountFrom = 0, accountTo = 0;
	private int adClientID = 0, adOrgID = 0, adUserID = 0;
	private boolean resumido = false, sinSaldo = true;
	private ProcessInfoParameter paramResultadoEjercicio = null;
	private HashMap<Integer, Integer> nivelesCuentas = new HashMap<Integer, Integer>();	
	private String idReporte = "";
	private static final String TABLA_MOLDE = "uy_molde_balgral";
	private static final int NIVEL_DETALLADO = 6;
	
	public RCtbBalanceGeneral() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
	
		// Parametros del reporte 
		ProcessInfoParameter paramTituloReporte = null, paramIDReporte = null, paramCapitulo = null, paramDateTo = null;
		ProcessInfoParameter paramAccountFrom = null, paramAccountTo = null, paramAcctSchema = null;
		
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){

				if (name.equalsIgnoreCase("tituloReporte")) paramTituloReporte = para[i];
				if (name.equalsIgnoreCase("idReporte"))	paramIDReporte = para[i]; 
				if (name.equalsIgnoreCase("resultadoEjercicio")) this.paramResultadoEjercicio = para[i];
				if (name.equalsIgnoreCase("AD_User_ID")) this.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Client_ID")) this.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Org_ID")) this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
				
				if (name.equalsIgnoreCase("C_AcctSchema_ID")){
					this.cAcctSchemaID = ((BigDecimal)para[i].getParameter()).intValueExact();
					paramAcctSchema = para[i];
				}
				
				if (name.equalsIgnoreCase("EndDate")){
					this.dateTo = (Timestamp)para[i].getParameter();
					paramDateTo = para[i];
				}

				if (name.equalsIgnoreCase("UY_AccountChapter_ID")){
					if (para[i].getParameter() != null){
						this.uyCapituloID = ((BigDecimal)para[i].getParameter()).intValueExact();
						paramCapitulo = para[i];
					}
				}
				
				if (name.equalsIgnoreCase("C_ElementValue_ID")){
					if (para[i].getParameter() != null){
						this.accountFrom = ((BigDecimal)para[i].getParameter()).intValueExact();
						paramAccountFrom = para[i];
					}						
					if (para[i].getParameter_To() != null){
						this.accountTo = ((BigDecimal)para[i].getParameter_To()).intValueExact();
						paramAccountTo = para[i];
					}						
				}

				if (name.equalsIgnoreCase("nivel")){
						this.nivelFiltro = Integer.parseInt(((String)para[i].getParameter()));
						this.resumido = (this.nivelFiltro < NIVEL_DETALLADO) ? true : false;
				}

				if (name.equalsIgnoreCase("SinSaldo")){
					if (para[i].getParameter() != null)
						this.sinSaldo = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? false : true;
				}
			}
		}
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(Long.valueOf(this.adUserID));
		
		// Seteo Parametros para el reporte
		if (paramTituloReporte != null) 
			if (this.resumido) paramTituloReporte.setParameter("Balance General Resumido");
			else paramTituloReporte.setParameter("Balance General Detallado");

		if (paramIDReporte != null) paramIDReporte.setParameter(this.idReporte);
		if (paramDateTo != null) paramDateTo.setParameter(this.dateTo);
		if (paramCapitulo != null) paramCapitulo.setParameter("nombre capitulo");
		if (paramAccountTo != null) paramAccountTo.setParameter("cuenta desde");
		if (paramAccountFrom != null) paramAccountFrom.setParameter("cuenta hasta");
		if (paramAcctSchema != null) paramAcctSchema.setParameterName(String.valueOf(this.cAcctSchemaID));

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		this.setWaiting(this.getProcessInfo().getWaiting());
		
		this.deleteInstanciasViejasReporte();
		
		this.getData();
		
		this.getNivelesCuenta();
		
		this.updateData();
		
		this.deleteSaldoCero();
		
		this.deleteCuentasNivelFiltro();
		
		this.setParamResultadoEjercicio();
		
		this.showHelp("Iniciando Vista Previa...");
		//this.getProcessInfo().getWaiting().setText("Iniciando Vista Previa...");
		
		return "OK";
	}

	/**
	 * Elimina corridas anteriores de este reporte para este usuario.
	 * @throws Exception 
	 */
	private void deleteInstanciasViejasReporte() throws Exception{
		
		String sql = "";
		try{
			
			sql = " DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id =" + this.adUserID;
			
			this.showHelp("Eliminando anteriores...");
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Elimina cuentas con nivel mayor al seleccionado en el filtro.
	 * @throws Exception 
	 */
	private void deleteCuentasNivelFiltro() throws Exception{
		
		// Solo si se indica nivel distinto al de detalle
		if (this.nivelFiltro == NIVEL_DETALLADO) return;
		
		String sql = "";
		try{
			
			sql = " DELETE FROM " + TABLA_MOLDE +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND nivel >" + this.nivelFiltro;	

			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	
	/**
	 * Elimina cuentas con saldo cero.
	 * @throws Exception 
	 */
	private void deleteSaldoCero() throws Exception{
		
		if (!this.sinSaldo) return;
		
		String sql = "";
		try{
			
			sql = " DELETE FROM " + TABLA_MOLDE +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND account_saldo = 0";	
			
			/*if(null!=this.getProcessInfo().getWaiting()){
				this.getProcessInfo().getWaiting().setText("Eliminando sin saldo...");				
			}*/
			
			this.showHelp("Eliminando sin saldo...");
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	
	/**
	 * Carga datos en tabla molde considerando filtros.
	 * @throws Exception 
	 */
	private void getData() throws Exception{
		
		String insert ="", sql = "", whereFiltros = "";

		try
		{
			// Armo where de filtro
			if (this.uyCapituloID > 0) whereFiltros += " AND ev.uy_accountchapter_id = " + this.uyCapituloID;
			
			if (this.accountFrom > 0){
				MElementValue evFrom = new MElementValue(getCtx(), this.accountFrom, null);
				if (evFrom.get_ID() > 0) whereFiltros += " AND ev.value>='" + evFrom.getValue() + "'";
			}

			if (this.accountTo > 0){
				MElementValue evTo = new MElementValue(getCtx(), this.accountTo, null);
				if (evTo.get_ID() > 0) whereFiltros += " AND ev.value<='" + evTo.getValue() + "'";
			}
			
			insert = " INSERT INTO " + TABLA_MOLDE + " (idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
					 " c_acctschema_id, uy_accountchapter_id, account_id, account_value, account_name, " +
					 " account_saldo, account_saldo_factacct, nivel, seqno, ad_tree_id, issummary) ";

			StringBuilder strb = new StringBuilder("");
			
			this.showHelp("Obteniendo datos...");			
			
			// Arbol de cuentas
			strb.append(" SELECT '" + this.idReporte + "',current_date," + this.adUserID + "," + this.adClientID + "," + this.adOrgID + "," +
						" scele.c_acctschema_id, ev.uy_accountchapter_id, tr.node_id , ev.value, ev.name, " +
						" 0, 0, 0, 0, tr.ad_tree_id, ev.issummary " +
						" from ad_treenode tr " +
						" inner join c_element el on tr.ad_tree_id = el.ad_tree_id " +
						" inner join c_acctschema_element scele on (el.c_element_id = scele.c_element_id " +
						" and scele.ad_client_id =" + this.adClientID + " and scele.c_acctschema_id=" + this.cAcctSchemaID + " and scele.elementtype='AC') " +
						" inner join c_elementvalue ev on tr.node_id = ev.c_elementvalue_id" +
					    " WHERE tr.ad_client_id =" + this.adClientID + whereFiltros +
					    " AND ev.uy_accountchapter_id is not null " +
						" order by ev.value");
			
			sql = strb.toString();
			
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Actualiza datos de la tabla temporal.
	 * @throws Exception 
	 */
	private void updateData() throws Exception{
		this.updateNivelSecuencia();
		this.updateCuentasNotSummary();
		this.updateCuentasSummary();
	}
	
	/**
	 * Actualiza informacion de nivel y secuencua de cuentas en tabla temporal. 
	 * @throws Exception
	 */
	private void updateNivelSecuencia() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			this.showHelp("Calculo niveles...");			
			
			sql = " SELECT uy_accountchapter_id, account_id, account_value, ad_tree_id " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY uy_accountchapter_id, account_value";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();
			
			String action = "", valueCuenta = ""; 
			int accountID = 0, nivelCuenta = 0, secuencia = 0;
			
			while (rs.next()){
				
				this.adTreeID = rs.getInt("ad_tree_id");
				accountID = rs.getInt("account_id");
				valueCuenta = rs.getString("account_value").trim();
				nivelCuenta = nivelesCuentas.get(new Integer(valueCuenta.length()));
				
				// Actualizo nivel y secuencia
				action = " UPDATE " + TABLA_MOLDE + 
		 		 		 " SET nivel =" + nivelCuenta + "," +
						 " seqno =" + secuencia++ +
		 		 		 " WHERE idreporte ='" + this.idReporte + "'" +
		 		 		 " AND account_id =" + accountID;
				DB.executeUpdateEx(action, null);
			}
			
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	/**
	 * Actualiza saldo de cuentas que no son summary. 
	 * @throws Exception
	 */
	private void updateCuentasNotSummary() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			this.showHelp("Saldo Cuentas...");
			
			sql = " SELECT tm.uy_accountchapter_id, tm.account_id, tm.account_value " +
				  " FROM " + TABLA_MOLDE + " tm " +
				  " WHERE idreporte=?" +
				  " AND tm.issummary ='N'";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			String action = ""; 
			int accountID = 0;
			BigDecimal saldoCuenta = Env.ZERO;
			
			while (rs.next()){
				
				this.showHelp("Saldos linea " + rowCount++ + " de " + totalRowCount);
				
				accountID = rs.getInt("account_id");
				saldoCuenta = this.getSaldoCuenta(accountID);
				
				// Actualizo saldo
				action = " UPDATE " + TABLA_MOLDE + 
		 		 		 " SET account_saldo =" + saldoCuenta + "," + 
						 " account_saldo_factacct =" + saldoCuenta +
		 		 		 " WHERE idreporte ='" + this.idReporte + "'" +
		 		 		 " AND account_id =" + accountID;
				DB.executeUpdateEx(action, null);
			}
			
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	
	/**
	 * Obtiene saldo de cuenta contable a la fecha hasta seleccionada por el usuario.
	 * @param accountID
	 * @return
	 * @throws Exception 
	 */
	private BigDecimal getSaldoCuenta(int accountID) throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		BigDecimal value = Env.ZERO; 
		
		try{
			sql = " select coalesce((sum(coalesce(amtacctdr,0)) - sum(coalesce(amtacctcr,0))),0) as saldo " +
				  " from fact_acct " +
				  " where c_acctschema_id =? " +
				  " and account_id =?" +
				  " and dateacct <='" + this.dateTo + "'";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.cAcctSchemaID);
			pstmt.setInt(2, accountID);
			
			rs = pstmt.executeQuery ();

			if (rs.next()) value = rs.getBigDecimal(1);
			
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;
	}

	/**
	 * Obtengo y guardo en memoria los niveles de cuentas que tendra este reporte.
	 * @throws Exception 
	 */
	private void getNivelesCuenta() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		int nivelAux = 1;
		
		try{
			
			this.showHelp("Calculando niveles ...");			
			
			sql = " select distinct(char_length(trim(account_value))) as largocuenta " +
				  " from " + TABLA_MOLDE +
				  " where idreporte=?" +
				  " order by 1 ";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();

			while (rs.next()){
				nivelesCuentas.put(new Integer(rs.getInt(1)), new Integer(nivelAux));
				nivelAux++;
			}
			
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}
	
	/**
	 * Actualiza saldo de cuentas que son summary. 
	 * @throws Exception
	 */
	private void updateCuentasSummary() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			this.showHelp("Calculando totales...");			
			
			sql = " SELECT tm.uy_accountchapter_id, tm.account_id, tm.account_value " +
				  " FROM " + TABLA_MOLDE + " tm " +
				  " WHERE idreporte=?" +
				  " AND tm.issummary ='Y'" +
				  " ORDER BY tm.nivel DESC ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			String action = ""; 
			int accountID = 0;
			BigDecimal saldoCuenta = Env.ZERO;
			
			while (rs.next()){
				
				this.showHelp("Totales linea " + rowCount++ + " de " + totalRowCount);				
				
				accountID = rs.getInt("account_id");
				saldoCuenta = this.getTotalCuentaPadre(accountID);
				
				// Actualizo saldo cuenta padre
				action = " UPDATE " + TABLA_MOLDE + 
		 		 		 " SET account_saldo =" + saldoCuenta +
		 		 		 " WHERE idreporte ='" + this.idReporte + "'" +
		 		 		 " AND account_id =" + accountID;
				DB.executeUpdateEx(action, null);
			}
			
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	/**
	 * Obtiene saldo de cuenta padre, sumando saldos de cuentas hijas ya cargados en temporal.
	 * @param accountID
	 * @return
	 * @throws Exception 
	 */
	private BigDecimal getTotalCuentaPadre(int accountID) throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		BigDecimal value = Env.ZERO; 
		
		try{
			sql = " select coalesce(sum(coalesce(tm.account_saldo,0)),0) as total " +
				  " from " + TABLA_MOLDE + " tm " +
				  " inner join ad_treenode tree on tm.account_id = tree.node_id " +	
				  " where tm.idreporte ='" + this.idReporte + "'" +
				  " and tree.ad_client_id =?" +
				  " and tree.ad_tree_id =?" +
				  " and tree.parent_id =?";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.adClientID);
			pstmt.setInt(2, this.adTreeID);
			pstmt.setInt(3, accountID);
			
			rs = pstmt.executeQuery ();

			if (rs.next()) value = rs.getBigDecimal(1);
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;
	}

	/**
	 * Calcula resultado del ejercicio y se lo pasa al parametro del reporte.
	 * @throws Exception 
	 */
	private void setParamResultadoEjercicio() throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		BigDecimal ingresos = Env.ZERO, egresos = Env.ZERO, resultado = Env.ZERO; 
		
		try{
			sql = " select account_value, account_saldo " +
				  " from " + TABLA_MOLDE +
				  " where idreporte ='" + this.idReporte + "'" +
				  " and account_value in ('4','5')";
			
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery ();

			while (rs.next()){
				if (rs.getString("account_value").equalsIgnoreCase("4")) ingresos = rs.getBigDecimal("account_saldo");
				if (rs.getString("account_value").equalsIgnoreCase("5")) egresos = rs.getBigDecimal("account_saldo");					
			}
			
			if (egresos.compareTo(Env.ZERO) < 0)
				resultado = ingresos.add(egresos).setScale(2, RoundingMode.HALF_UP);
			else
				resultado = ingresos.subtract(egresos).setScale(2, RoundingMode.HALF_UP);
			
			this.paramResultadoEjercicio.setParameter(resultado);
			
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}
	
	public Waiting getWaiting() {
		return this.waiting;
	}

	public void setWaiting(Waiting value) {
		this.waiting = value;
	}
	
	private void showHelp(String text){  
		
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}			
	}
	
}
