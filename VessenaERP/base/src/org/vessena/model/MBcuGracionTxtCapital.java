/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.Waiting;
import org.compiere.model.MPeriod;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.util.OpenUpUtils;

/***
 * Clase para proceso de generacion de archivo txt por conceptos de tasas activas.
 * org.openup.model - MBcuGracionTxtCapital
 * OpenUp Ltda. Issue #1114 
 * Description: 
 * @author Gabriel Vila - 12/08/2013
 * @see
 */
public class MBcuGracionTxtCapital extends X_UY_Bcu_GracionTxtCapital {

	private static final long serialVersionUID = 8630302648226754807L;

	private Waiting waiting = null;
	private int idConexion = 1000010;
	
	
	/**
	 * @param ctx
	 * @param UY_Bcu_GracionTxtCapital_ID
	 * @param trxName
	 */
	public MBcuGracionTxtCapital(Properties ctx,
			int UY_Bcu_GracionTxtCapital_ID, String trxName) {
		super(ctx, UY_Bcu_GracionTxtCapital_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBcuGracionTxtCapital(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	public Waiting getWaiting() {
		return this.waiting;
	}

	public void setWaiting(Waiting value) {
		this.waiting = value;
	}
	
	/***
	 * Despliega avance en ventana splash
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 * @param text
	 */
	private void showHelp(String text){
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}			
	}

	/***
	 * Metodo donde se ejecuta todo el proceso de generacion de datos para el archivo.
	 * OpenUp Ltda. Issue #1114 
	 * @author Gabriel Vila - 12/08/2013
	 * @see
	 */
	public void execute() {
		
		try{
		
			this.deleteOldData();
			
			this.getDataConvenios();

			this.getDataTransactions();		
			
			this.getDataRevolving();
		
			this.setAcumulado();
			
		}
		catch (Exception ex){
			throw new AdempiereException(ex);
		}
		
	}

	
	/***
	 * Obtiene el capital financiado por concepto de revolving.
	 * OpenUp Ltda. Issue #1114 
	 * @author Guillermo Brust - 12/08/2013
	 * @see
	 */
	private void getDataRevolving() {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		MPeriod period = (MPeriod)this.getC_Period();		
		Timestamp fechaHasta = period.getEndDate();
		Timestamp fechaDesde = OpenUpUtils.sumaTiempo(period.getStartDate(), Calendar.DAY_OF_MONTH, -60);
								
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		try {

			this.showHelp("REVOLVING");	
			
			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = OpenUpUtils.getConnectionToSqlServer(idConexion);
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			//OpenUp. Nicolas Sarlabos. 22/07/2015. #4439. Se modifica obtencion de datos en dos consultas sin agrupar.
			//proceso para moneda Pesos
			
			sql = " select 142 as moneda, CAST(ROUND((POWER(1 + (saldo.STasaEfMe / 100), 12) -1) * 100, 0) AS INT) as Tasa, 30 as Plazo_Remanente," +
				  " round((saldo.SSalAPeDo - saldo.STotPgoPes), 2) as Importe" +
				  " from FinancialPro.dbo.SALDOCTA saldo" +
				  " where saldo.SFecCierre = (SELECT MAX(SFecCierre)" +
				  " 						  FROM FinancialPro.dbo.SALDOCTA" +
				  " 						  WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +	
				  " 						  AND SNroCta = saldo.SNroCta)" +
				  " AND saldo.SFecCierre < (SELECT MAX(SVePMAc)" +
				  " 						FROM FinancialPro.dbo.SALDOCTA" +
				  " 						WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +	
				  " 						AND SNroCta = saldo.SNroCta)" +
				  " and not exists (select t.Cuenta from op_control.TableCuentasConvenios t where t.Cuenta = saldo.SNroCta)" +
				  " and (saldo.SSalAPeDo - saldo.STotPgoPes) > 0";
				  				 
			rs = stmt.executeQuery(sql);
			
			/*
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			*/
			
			while (rs.next()) {
				
				MBcuCapitalFinanciado capFin = new MBcuCapitalFinanciado(getCtx(), 0, this.get_TrxName());
				
				capFin.setDateTrx(new Timestamp(new Date().getTime()));
				capFin.setC_Currency_ID(rs.getInt("moneda"));				
				capFin.setCuotas(0);
				capFin.setCuotaActual(0);
				capFin.setTea(rs.getBigDecimal("Tasa"));			
				capFin.setFechaOperacion(fechaHasta);
				capFin.setIsDebit(false);
				capFin.setCapitalInteres(rs.getBigDecimal("importe"));				
				capFin.setIsIncosistente(false);
				capFin.setTipoDato("REVOLVING");
				capFin.setUY_Bcu_GracionTxtCapital_ID(this.get_ID());
				capFin.setcantidadoperaciones(Env.ONE);
				capFin.setplazoremanente(new BigDecimal(rs.getInt("Plazo_Remanente")));
				
				capFin.saveEx();
			}

			rs.close();
			
			//proceso para moneda Dolares

			sql =  " select 100 as moneda, CAST(ROUND((POWER(1 + (saldo.STasEMDol / 100), 12) -1) * 100, 0) AS INT) as Tasa, 30 as Plazo_Remanente," +
					" round((saldo.SSdoAntDol - saldo.STotPgoDol), 2) as Importe" +
					" from FinancialPro.dbo.SALDOCTA saldo" +
					" where saldo.SFecCierre = (SELECT MAX(SFecCierre)" +
					" 						  FROM FinancialPro.dbo.SALDOCTA" +
					" 						  WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +	
					" 						  AND SNroCta = saldo.SNroCta)" +
					" AND saldo.SFecCierre < (SELECT MAX(SVePMAc)" +
					" 						FROM FinancialPro.dbo.SALDOCTA" +
					" 						WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +	
					" 						AND SNroCta = saldo.SNroCta)" +
					" and not exists (select t.Cuenta from op_control.TableCuentasConvenios t where t.Cuenta = saldo.SNroCta)" +
					" and (saldo.SSdoAntDol - saldo.STotPgoDol) > 0";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				MBcuCapitalFinanciado capFin = new MBcuCapitalFinanciado(getCtx(), 0, this.get_TrxName());

				capFin.setDateTrx(new Timestamp(new Date().getTime()));
				capFin.setC_Currency_ID(rs.getInt("moneda"));				
				capFin.setCuotas(0);
				capFin.setCuotaActual(0);
				capFin.setTea(rs.getBigDecimal("Tasa"));			
				capFin.setFechaOperacion(fechaHasta);
				capFin.setIsDebit(false);
				capFin.setCapitalInteres(rs.getBigDecimal("importe"));				
				capFin.setIsIncosistente(false);
				capFin.setTipoDato("REVOLVING");
				capFin.setUY_Bcu_GracionTxtCapital_ID(this.get_ID());
				capFin.setcantidadoperaciones(Env.ONE);
				capFin.setplazoremanente(new BigDecimal(rs.getInt("Plazo_Remanente")));

				capFin.saveEx();
			}

			rs.close();				
			con.close();	
			//Fin OpenUp. #4439.

		} catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
	}

	
	/***
	 * Obtiene el capital financiado por cuenta en cada compra y a la vez verifica que la cuota actual
	 * sea la siguiente a la procesada en el proceso anterior. 
	 * Obtiene ademas la tasa aplicada a cada compra segun la fecha de operacion realizada, el producto y 
	 * afinidad asociados al cliente, y la moneda de la compra.
	 * OpenUp Ltda. Issue #1114 
	 * @author Guillermo Brust - 12/08/2013
	 * @see
	 */
	private void getDataTransactions(){
		this.getDataTransactionsCredit();
		this.getDataTransactionsCreditComerciosNoIpc();
		this.getDataTransactionsContado();
	}
	
	
	private void getDataTransactionsCredit() {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		MPeriod period = (MPeriod)this.getC_Period();		
		Timestamp fechaHasta = period.getEndDate();
		Timestamp fechaDesde = OpenUpUtils.sumaTiempo(period.getStartDate(), Calendar.DAY_OF_MONTH, -60);
		Timestamp fechaDesdeTEA = OpenUpUtils.sumaTiempo(period.getStartDate(), Calendar.DAY_OF_MONTH, -750);
				
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		try {

			this.showHelp("TRANSACCIONES");	
			String action = "";
			Timestamp today = new Timestamp(System.currentTimeMillis());

			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = OpenUpUtils.getConnectionToSqlServer(idConexion);
			
			// Creo estructuras en sql server necesarias para este proceso.
			this.createTables(con, fechaHasta, fechaDesdeTEA);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			sql = " SELECT mov.MDNroCta as Cuenta, mov.MndCod as Moneda, tea.producto as Producto, tea.afinidad as Afinidad, mov.MDCntCuo as Cuotas, " +
				  " (((mov.MDCntCuo - mov.MDCuoVig) + 1) * 30) as Plazo_Remanente," +
				  " mov.MDCuoVig as CuotaActual, mov.MDNroCup, tea.tea as Tea, " +
				  " tea.vigencia as VigenciaTasa, mov.MDFecOpe as FechaOperacion, mov.MDFecCta as FechaCierre, mov.MDFecPre as FechaPresentacion, " +
				  " CASE WHEN mov.MDDebCre = '1' THEN 'N' ELSE 'Y' END as IsDebit, " +
				  " CASE WHEN mov.MDDebCre = '1' THEN mov.MDImpTot ELSE (mov.MDImpTot * -1) END AS importeTransaccion, " +
				  " CASE WHEN mov.MDDebCre = '1' THEN ((mov.MDCntCuo - mov.MDCuoVig + 1) * mov.MDImpTot) ELSE ((mov.MDCntCuo - mov.MDCuoVig + 1) * mov.MDImpTot) * -1 END AS importeCapitalFinanciado " +
				  " FROM FinancialPro.dbo.MOVCTAD mov, FinancialPro.dbo.SOCIOS1 soc, op_control.UY_Fdu_Tea tea " +
				  " WHERE mov.MDFecCta > (SELECT MAX(SFecCierre) " +
				  "   					  FROM FinancialPro.dbo.SALDOCTA " +
				  "			   		      WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +
			   	  "			              AND SNroCta = mov.MDNroCta) " +
			   	  " AND mov.MDFecCta < (SELECT MAX(SVePMPr) " +
			   	  "		   				FROM FinancialPro.dbo.SALDOCTA " +
			   	  "		   				WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +
			   	  "		   				AND SNroCta = mov.MDNroCta) " +
			   	  " AND not exists (select t.Cuenta from op_control.TableCuentasConvenios t where t.Cuenta = mov.MDNroCta) " +
			   	  " AND soc.STNroCta =  mov.MDNroCta " +
			   	  " AND mov.MDCntCuo > 1 " +
			   	  " AND soc.STUltiReg = '1' " +
			   	  " AND soc.STTipoSoc = '0' " +
			   	  " AND mov.MDNroCom NOT IN(SELECT distinct value " +
			   	  "   						FROM op_control.UY_Fdu_ComerciosNoIpc_aux " +
			   	  "   						WHERE validity = (SELECT max(validity) " +
			   	  "   										   FROM op_control.UY_Fdu_ComerciosNoIpc_aux " +
			   	  "    										   WHERE validity <=  mov.MDFecOpe)) " +
			   	  " AND CAST(tea.producto AS INT) = soc.PTCod " +
			   	  " AND tea.afinidad = '0' + SUBSTRING(RTRIM(soc.GAFCod), 1, 4) " +
			   	  " AND tea.moneda = CASE WHEN mov.MndCod = 0 THEN '858' ELSE '840' END " +
			   	  " AND tea.cuota = mov.MDCntCuo " +
			   	  " AND tea.vigencia = (SELECT max(vigencia) " +
			   	  "   					FROM op_control.UY_Fdu_Tea " +
			   	  "	   					WHERE vigencia <= mov.MDFecOpe) ";
			
			rs = stmt.executeQuery(sql);			
			
			while (rs.next()) {				
			
				if (rs.getInt("CuotaActual") == 1){
					if (rs.getTimestamp("FechaPresentacion").compareTo(fechaHasta) > 0){
						continue;
					}
				}	
				
				int fduProductosID = DB.getSQLValue(null, " select UY_Fdu_Productos_ID from UY_Fdu_Productos " +
														  " where value='" + rs.getString("Producto") + "'" +
														  " and UY_FduFile_ID= 1000008");	
				
				int fduAfinidadID = DB.getSQLValue(null, " select UY_Fdu_Afinidad_ID from UY_Fdu_Afinidad " +
						  								 " where value='" + rs.getString("Afinidad") + "'" +
						  								 " and UY_Fdu_Productos_ID = " + fduProductosID +
						  								 " and UY_FduFile_ID= 1000008");
				
				boolean inConsistente = MBcuCapitalFinanciado.getIsIncosistente(getCtx(), null, rs.getString("Cuenta"), 
						String.valueOf(rs.getInt("MDNroCup")), rs.getInt("Cuotas"), rs.getTimestamp("FechaCierre"), 
						rs.getTimestamp("FechaPresentacion"), rs.getInt("CuotaActual"));
				
				String inCons = (inConsistente) ? "Y" : "N";
				
				action = " INSERT INTO uy_bcu_capitalfinanciado(uy_bcu_capitalfinanciado_id, ad_client_id, ad_org_id, isactive, " +
						 " created, createdby, updated, updatedby, datetrx, accountno, c_currency_id, " +
						 " uy_fdu_productos_id, uy_fdu_afinidad_id, cuotas, cuotaactual, cupon, tasa, vigencia, " +
						 " fechaoperacion, fechacierre, fechapresentacion, isdebit, " +
						 " amount, capitalinteres, isincosistente, tipodato, uy_bcu_graciontxtcapital_id, tea, plazoremanente, cantidadoperaciones) " +
						 " VALUES (nextid(1001757,'N'), " + this.getAD_Client_ID() + "," + this.getAD_Org_ID() + ",'Y', " +
						 "'" + today + "',100,'" + today + "',100,'" + this.getDateTrx() + "','" + rs.getString("Cuenta") + "'," +
						 (rs.getString("Moneda").equals("0") ? 142 : 100) + "," + fduProductosID + "," + fduAfinidadID + "," +
						 rs.getInt("Cuotas") + "," + rs.getInt("CuotaActual") + ",'" + String.valueOf(rs.getInt("MDNroCup")) + "'," +
						 rs.getBigDecimal("Tea") + ",'" + rs.getTimestamp("VigenciaTasa") + "','" + rs.getTimestamp("FechaOperacion") + "','" +
						 rs.getTimestamp("FechaCierre") + "','" + rs.getTimestamp("FechaPresentacion") + "','" + rs.getString("IsDebit") + "'," +
						 rs.getBigDecimal("importeTransaccion") + "," + rs.getBigDecimal("ImporteCapitalFinanciado") + ",'" + 
						 inCons + "','TRANSACCION'," + this.get_ID() + "," + rs.getBigDecimal("Tea") + "," + rs.getInt("Plazo_Remanente") + ",1)";
				
				DB.executeUpdateEx(action, null);
				
			}

			rs.close();
			con.close();

		} catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
	}
	
	
	private void getDataTransactionsCreditComerciosNoIpc() {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		MPeriod period = (MPeriod)this.getC_Period();		
		Timestamp fechaHasta = period.getEndDate();
		Timestamp fechaDesde = OpenUpUtils.sumaTiempo(period.getStartDate(), Calendar.DAY_OF_MONTH, -60);
		Timestamp fechaDesdeTEA = OpenUpUtils.sumaTiempo(period.getStartDate(), Calendar.DAY_OF_MONTH, -750);
				
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		try {

			this.showHelp("TRANSACCIONES");	
			String action = "";
			Timestamp today = new Timestamp(System.currentTimeMillis());

			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = OpenUpUtils.getConnectionToSqlServer(idConexion);
			
			// Creo estructuras en sql server necesarias para este proceso.
			this.createTables(con, fechaHasta, fechaDesdeTEA);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			sql = " SELECT mov.MDNroCta as Cuenta, mov.MndCod as Moneda, tea.producto as Producto, tea.afinidad as Afinidad, mov.MDCntCuo as Cuotas, " +
				  " (((mov.MDCntCuo - mov.MDCuoVig) + 1) * 30) as Plazo_Remanente," +
				  " mov.MDCuoVig as CuotaActual, mov.MDNroCup, 0 as Tea, " +
				  " tea.vigencia as VigenciaTasa, mov.MDFecOpe as FechaOperacion, mov.MDFecCta as FechaCierre, mov.MDFecPre as FechaPresentacion, " +
				  " CASE WHEN mov.MDDebCre = '1' THEN 'N' ELSE 'Y' END as IsDebit, " +
				  " CASE WHEN mov.MDDebCre = '1' THEN mov.MDImpTot ELSE (mov.MDImpTot * -1) END AS importeTransaccion, " +
				  " CASE WHEN mov.MDDebCre = '1' THEN ((mov.MDCntCuo - mov.MDCuoVig + 1) * mov.MDImpTot) ELSE ((mov.MDCntCuo - mov.MDCuoVig + 1) * mov.MDImpTot) * -1 END AS importeCapitalFinanciado " +
				  " FROM FinancialPro.dbo.MOVCTAD mov, FinancialPro.dbo.SOCIOS1 soc, op_control.UY_Fdu_Tea tea " +
				  " WHERE mov.MDFecCta > (SELECT MAX(SFecCierre) " +
				  "   					  FROM FinancialPro.dbo.SALDOCTA " +
				  "			   		      WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +
			   	  "			              AND SNroCta = mov.MDNroCta) " +
			   	  " AND mov.MDFecCta < (SELECT MAX(SVePMPr) " +
			   	  "		   				FROM FinancialPro.dbo.SALDOCTA " +
			   	  "		   				WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +
			   	  "		   				AND SNroCta = mov.MDNroCta) " +
			   	  " AND not exists (select t.Cuenta from op_control.TableCuentasConvenios t where t.Cuenta = mov.MDNroCta) " +
			   	  " AND soc.STNroCta =  mov.MDNroCta " +
			   	  " AND mov.MDCntCuo > 1 " +
			   	  " AND soc.STUltiReg = '1' " +
			   	  " AND soc.STTipoSoc = '0' " +
			   	  " AND mov.MDNroCom IN(SELECT distinct value " +
			   	  "   					FROM op_control.UY_Fdu_ComerciosNoIpc_aux " +
			   	  "   					WHERE validity = (SELECT max(validity) " +
			   	  "   									  FROM op_control.UY_Fdu_ComerciosNoIpc_aux " +
			   	  "    									  WHERE validity <=  mov.MDFecOpe)) " +
			   	  " AND CAST(tea.producto AS INT) = soc.PTCod " +
			   	  " AND tea.afinidad = '0' + SUBSTRING(RTRIM(soc.GAFCod), 1, 4) " +
			   	  " AND tea.moneda = CASE WHEN mov.MndCod = 0 THEN '858' ELSE '840' END " +
			   	  " AND tea.cuota = mov.MDCntCuo " +
			   	  " AND tea.vigencia = (SELECT max(vigencia) " +
			   	  "   					FROM op_control.UY_Fdu_Tea " +
			   	  "	   					WHERE vigencia <= mov.MDFecOpe) ";
			
			rs = stmt.executeQuery(sql);			
			
			while (rs.next()) {				
			
				if (rs.getInt("CuotaActual") == 1){
					if (rs.getTimestamp("FechaPresentacion").compareTo(fechaHasta) > 0){
						continue;
					}
				}	
				
				int fduProductosID = DB.getSQLValue(null, " select UY_Fdu_Productos_ID from UY_Fdu_Productos " +
														  " where value='" + rs.getString("Producto") + "'" +
														  " and UY_FduFile_ID= 1000008");	
				
				int fduAfinidadID = DB.getSQLValue(null, " select UY_Fdu_Afinidad_ID from UY_Fdu_Afinidad " +
						  								 " where value='" + rs.getString("Afinidad") + "'" +
						  								 " and UY_Fdu_Productos_ID = " + fduProductosID +
						  								 " and UY_FduFile_ID= 1000008");
				
				boolean inConsistente = MBcuCapitalFinanciado.getIsIncosistente(getCtx(), null, rs.getString("Cuenta"), 
						String.valueOf(rs.getInt("MDNroCup")), rs.getInt("Cuotas"), rs.getTimestamp("FechaCierre"), 
						rs.getTimestamp("FechaPresentacion"), rs.getInt("CuotaActual"));
				
				String inCons = (inConsistente) ? "Y" : "N";
				
				action = " INSERT INTO uy_bcu_capitalfinanciado(uy_bcu_capitalfinanciado_id, ad_client_id, ad_org_id, isactive, " +
						 " created, createdby, updated, updatedby, datetrx, accountno, c_currency_id, " +
						 " uy_fdu_productos_id, uy_fdu_afinidad_id, cuotas, cuotaactual, cupon, tasa, vigencia, " +
						 " fechaoperacion, fechacierre, fechapresentacion, isdebit, " +
						 " amount, capitalinteres, isincosistente, tipodato, uy_bcu_graciontxtcapital_id, tea, plazoremanente, cantidadoperaciones) " +
						 " VALUES (nextid(1001757,'N'), " + this.getAD_Client_ID() + "," + this.getAD_Org_ID() + ",'Y', " +
						 "'" + today + "',100,'" + today + "',100,'" + this.getDateTrx() + "','" + rs.getString("Cuenta") + "'," +
						 (rs.getString("Moneda").equals("0") ? 142 : 100) + "," + fduProductosID + "," + fduAfinidadID + "," +
						 rs.getInt("Cuotas") + "," + rs.getInt("CuotaActual") + ",'" + String.valueOf(rs.getInt("MDNroCup")) + "'," +
						 rs.getBigDecimal("Tea") + ",'" + rs.getTimestamp("VigenciaTasa") + "','" + rs.getTimestamp("FechaOperacion") + "','" +
						 rs.getTimestamp("FechaCierre") + "','" + rs.getTimestamp("FechaPresentacion") + "','" + rs.getString("IsDebit") + "'," +
						 rs.getBigDecimal("importeTransaccion") + "," + rs.getBigDecimal("ImporteCapitalFinanciado") + ",'" + 
						 inCons + "','TRANSACCION'," + this.get_ID() + "," + rs.getBigDecimal("Tea") + "," + rs.getInt("Plazo_Remanente") + ",1)";
				
				DB.executeUpdateEx(action, null);
				
			}

			rs.close();
			con.close();

		} catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
	}
	
	
	private void getDataTransactionsContado() {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		MPeriod period = (MPeriod)this.getC_Period();		
		Timestamp fechaHasta = period.getEndDate();
		Timestamp fechaDesde = OpenUpUtils.sumaTiempo(period.getStartDate(), Calendar.DAY_OF_MONTH, -60);
		Timestamp fechaDesdeTEA = OpenUpUtils.sumaTiempo(period.getStartDate(), Calendar.DAY_OF_MONTH, -750);
				
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		try {

			this.showHelp("TRANSACCIONES");	
			String action = "";
			Timestamp today = new Timestamp(System.currentTimeMillis());

			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = OpenUpUtils.getConnectionToSqlServer(idConexion);
			
			// Creo estructuras en sql server necesarias para este proceso.
			this.createTables(con, fechaHasta, fechaDesdeTEA);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			sql = " SELECT mov.MDNroCta as Cuenta, mov.MndCod as Moneda, soc.PTCod as Producto, '0' + SUBSTRING(RTRIM(soc.GAFCod), 1, 4) as Afinidad, mov.MDCntCuo as Cuotas," +  
				  " 30 as Plazo_Remanente," +
				  "	mov.MDCuoVig as CuotaActual, mov.MDNroCup, 0 as Tea," +  
				  " mov.MDFecOpe as VigenciaTasa, mov.MDFecOpe as FechaOperacion, mov.MDFecCta as FechaCierre, mov.MDFecPre as FechaPresentacion," +  
				  " CASE WHEN mov.MDDebCre = '1' THEN 'N' ELSE 'Y' END as IsDebit," +  
				  " CASE WHEN mov.MDDebCre = '1' THEN mov.MDImpTot ELSE (mov.MDImpTot * -1) END AS importeTransaccion," +  
				  " CASE WHEN mov.MDDebCre = '1' THEN ((mov.MDCntCuo - mov.MDCuoVig + 1) * mov.MDImpTot) ELSE ((mov.MDCntCuo - mov.MDCuoVig + 1) * mov.MDImpTot) * -1 END AS importeCapitalFinanciado" +  
				  " FROM FinancialPro.dbo.MOVCTAD mov, FinancialPro.dbo.SOCIOS1 soc" +
				  " WHERE mov.MDFecCta > (SELECT MAX(SFecCierre)" +  
 				  " 					  FROM FinancialPro.dbo.SALDOCTA" +  
		   		  " 					  WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +
		          " 					  AND SNroCta = mov.MDNroCta)" +  
		          " AND mov.MDFecCta < (SELECT MAX(SVePMPr)" +  
	   			  " 					FROM FinancialPro.dbo.SALDOCTA" +  
	   			  " 					WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +
	   			  " 					AND SNroCta = mov.MDNroCta)" +  
	   			  " and mov.MDFecPre <= '" + df.format(fechaHasta) + "'" +
	   			  " AND not exists (select t.Cuenta from op_control.TableCuentasConvenios t where t.Cuenta = mov.MDNroCta)" +  
	   			  " AND mov.MDCntCuo = 0" +
	   			  " AND soc.STNroCta =  mov.MDNroCta" + 
	   			  " AND soc.STUltiReg = '1'" +  
	   			  " AND soc.STTipoSoc = '0'";
			
			rs = stmt.executeQuery(sql);			
			
			while (rs.next()) {				
			
				if (rs.getInt("CuotaActual") == 1){
					if (rs.getTimestamp("FechaPresentacion").compareTo(fechaHasta) > 0){
						continue;
					}
				}	
				
				int fduProductosID = DB.getSQLValue(null, " select UY_Fdu_Productos_ID from UY_Fdu_Productos " +
														  " where CAST(value AS INT) = " + rs.getInt("Producto") + 
														  " and UY_FduFile_ID= 1000008");	
				
				int fduAfinidadID = DB.getSQLValue(null, " select UY_Fdu_Afinidad_ID from UY_Fdu_Afinidad " +
						  								 " where value='" + rs.getString("Afinidad") + "'" +
						  								 " and UY_Fdu_Productos_ID = " + fduProductosID +
						  								 " and UY_FduFile_ID= 1000008");
				
				boolean inConsistente = MBcuCapitalFinanciado.getIsIncosistente(getCtx(), null, rs.getString("Cuenta"), 
						String.valueOf(rs.getInt("MDNroCup")), rs.getInt("Cuotas"), rs.getTimestamp("FechaCierre"), 
						rs.getTimestamp("FechaPresentacion"), rs.getInt("CuotaActual"));
				
				String inCons = (inConsistente) ? "Y" : "N";
				
				action = " INSERT INTO uy_bcu_capitalfinanciado(uy_bcu_capitalfinanciado_id, ad_client_id, ad_org_id, isactive, " +
						 " created, createdby, updated, updatedby, datetrx, accountno, c_currency_id, " +
						 " uy_fdu_productos_id, uy_fdu_afinidad_id, cuotas, cuotaactual, cupon, tasa, vigencia, " +
						 " fechaoperacion, fechacierre, fechapresentacion, isdebit, " +
						 " amount, capitalinteres, isincosistente, tipodato, uy_bcu_graciontxtcapital_id, tea, plazoremanente, cantidadoperaciones) " +
						 " VALUES (nextid(1001757,'N'), " + this.getAD_Client_ID() + "," + this.getAD_Org_ID() + ",'Y', " +
						 "'" + today + "',100,'" + today + "',100,'" + this.getDateTrx() + "','" + rs.getString("Cuenta") + "'," +
						 (rs.getString("Moneda").equals("0") ? 142 : 100) + "," + fduProductosID + "," + fduAfinidadID + "," +
						 rs.getInt("Cuotas") + "," + rs.getInt("CuotaActual") + ",'" + String.valueOf(rs.getInt("MDNroCup")) + "'," +
						 rs.getBigDecimal("Tea") + ",'" + rs.getTimestamp("VigenciaTasa") + "','" + rs.getTimestamp("FechaOperacion") + "','" +
						 rs.getTimestamp("FechaCierre") + "','" + rs.getTimestamp("FechaPresentacion") + "','" + rs.getString("IsDebit") + "'," +
						 rs.getBigDecimal("importeTransaccion") + "," + rs.getBigDecimal("ImporteCapitalFinanciado") + ",'" + 
						 inCons + "','TRANSACCION'," + this.get_ID() + "," + rs.getBigDecimal("Tea") + "," + rs.getInt("Plazo_Remanente") + ",1)";
								
				DB.executeUpdateEx(action, null);
				
			}

			rs.close();
			con.close();

		} catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
	}

	/***
	 * Crea tablas auxiliares al proceso en el sql server.
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/08/2013
	 * @see
	 * @param con
	 * @param fechaHasta
	 * @param fechaDesde
	 */
	private void createTables(Connection con, Timestamp fechaHasta, Timestamp fechaDesde) {

		String action = "", sql = "";
		
		try {
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
			
			
			// Convenios
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			action = " IF EXISTS (SELECT name FROM sysobjects WHERE name = 'TableCuentasConvenios' AND type = 'U')" +
				  " DROP TABLE op_control.TableCuentasConvenios";
			stmt.executeUpdate(action);
			
			sql = " select distinct conv_hist.Cuenta as Cuenta into op_control.TableCuentasConvenios" + 
				  " from FinancialPro.dbo.q_convenios_hist conv_hist" +
				  " where conv_hist.fecha_proceso = (select MIN(fecha_proceso)" +
				  " 								 from FinancialPro.dbo.q_convenios_hist" +
				  " 								 where fecha_proceso > '" + df.format(fechaHasta) + "')" +				  
				  " and conv_hist.Estado = 'C'";
			stmt.executeUpdate(sql);

			action = " create index idx_fdu_conv_1 on op_control.TableCuentasConvenios(cuenta) ";
			stmt.executeUpdate(action);
			
			// TEA
			action = " IF EXISTS (SELECT name FROM sysobjects WHERE name = 'UY_Fdu_Tea' AND type = 'U') " +
					 " DROP TABLE op_control.UY_Fdu_Tea ";
			stmt.executeUpdate(action);
			
			sql = " select * into op_control.UY_Fdu_Tea from op_control.VUY_Fdu_Tea " +
					" where vigencia between '" + fechaDesde + "' and '" + fechaHasta + "'";
			stmt.executeUpdate(sql);

			action = " create index idx_fdu_tea_1 on op_control.UY_Fdu_Tea(producto, afinidad, moneda, cuota, vigencia) ";
			stmt.executeUpdate(action);
			
			// COMERCIOS NO IPC
			action = " IF EXISTS (SELECT name FROM sysobjects WHERE name = 'UY_Fdu_ComerciosNoIpc_Aux' AND type = 'U') " +
					 " DROP TABLE op_control.UY_Fdu_ComerciosNoIpc_Aux ";
			stmt.executeUpdate(action);
			
			sql = " select * into op_control.UY_Fdu_ComerciosNoIpc_Aux from op_control.VUY_Fdu_ComerciosNoIpc " +
				  " where validity between '" + fechaDesde + "' and '" + fechaHasta + "'";
			stmt.executeUpdate(sql);
			
			action = "create index idx_bcu_comer_1 on op_control.UY_Fdu_ComerciosNoIpc_Aux (validity)";
			stmt.executeUpdate(action);

			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		

		
	}

	/***
	 * Obtiene datos de los convenios activos.
	 * OpenUp Ltda. Issue #1114 
	 * @author Guillermo Brust - 12/08/2013
	 * @see
	 */
	private void getDataConvenios() {
		
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		MPeriod period = (MPeriod)this.getC_Period();
		Timestamp fechaHasta = period.getEndDate();
		
		try {

			this.showHelp("Convenios");	
			
			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = OpenUpUtils.getConnectionToSqlServer(idConexion);
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			sql = " select Cuenta, Cuotas, (Cuotas - (CAST((Saldo / Cuota) AS INT))) as Cuota_Actual, Cuota, " +
				  " CASE WHEN Cuotas -(Cuotas - (CAST((Saldo / Cuota) AS INT))) = 0 THEN 30 ELSE (Cuotas - (Cuotas - (CAST((Saldo / Cuota) AS INT)))) * 30 END AS Plazo_Remanente," +
				  " CAST(Tasa_Financiacion as INT) as Tasa, saldo as Importe " +
				  " from FinancialPro.dbo.q_convenios_hist " +
				  " where fecha_proceso = (select MIN(fecha_proceso)" +
				  " 					   from FinancialPro.dbo.q_convenios_hist" +
				  " 					   where fecha_proceso > '" + df.format(fechaHasta) + "')" +				  
				  " and Estado = 'C'" + 
				  " and (Cuotas - (Cuotas - (CAST((Saldo / Cuota) AS INT))) <> 0 OR" +
				  " Cuotas - (Cuotas - (CAST((Saldo / Cuota) AS INT))) = 0 and Saldo > 100)";

			rs = stmt.executeQuery(sql);

			/*
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			*/
			
			String action = "";
			Timestamp today = new Timestamp(System.currentTimeMillis());
			
			while (rs.next()) {
				
				
				action = " INSERT INTO uy_bcu_capitalfinanciado(uy_bcu_capitalfinanciado_id, ad_client_id, ad_org_id, isactive, " +
						 " created, createdby, updated, updatedby, datetrx, accountno, c_currency_id, " +
						 " cuotas, cuotaactual, fechaoperacion, isdebit, " +
						 " amount, capitalinteres, isincosistente, tipodato, uy_bcu_graciontxtcapital_id, tea, plazoremanente, cantidadoperaciones) " +
						 " VALUES (nextid(1001757,'N'), " + this.getAD_Client_ID() + "," + this.getAD_Org_ID() + ",'Y', " +
						 "'" + today + "',100,'" + today + "',100,'" + this.getDateTrx() + "','" + rs.getString("Cuenta") + "',142," +
						 rs.getInt("Cuotas") + "," + rs.getInt("Cuota_Actual") + ",'" + fechaHasta + "','N'," +
						 rs.getBigDecimal("Cuota") + "," + rs.getBigDecimal("Importe") + ",'N','CONVENIO'," + this.get_ID() + "," +
						 rs.getBigDecimal("Tasa") + "," + rs.getInt("Plazo_Remanente") + ",1)";				
				
				DB.executeUpdateEx(action, null);
				
				//this.showHelp("Registro " + rowCount++ + " de " + totalRowCount);			

				/*
				MBcuCapitalFinanciado capFin = new MBcuCapitalFinanciado(getCtx(), 0, this.get_TrxName());
				
				capFin.setDateTrx(new Timestamp(new Date().getTime()));
				capFin.setAccountNo(rs.getString("Cuenta"));
				capFin.setC_Currency_ID(142);				
				capFin.setUY_Fdu_Productos_ID(0);				
				capFin.setUY_Fdu_Afinidad_ID(0);
				capFin.setCuotas(rs.getInt("Cuotas"));
				capFin.setCuotaActual(rs.getInt("Cuota_Actual"));
				capFin.setCupon(null);
				capFin.setTasa(null);
				capFin.setTea(rs.getBigDecimal("Tasa"));			
				capFin.setvigencia(null);
				capFin.setFechaOperacion(fechaHasta);
				capFin.setFechaCierre(null);
				capFin.setIsDebit(false);
				capFin.setAmount(rs.getBigDecimal("Cuota"));
				capFin.setCapitalInteres(rs.getBigDecimal("Importe"));				
				capFin.setIsIncosistente(false);
				capFin.setTipoDato("CONVENIO");
				capFin.setUY_Bcu_GracionTxtCapital_ID(this.get_ID());
				capFin.setcantidadoperaciones(null);
												
				capFin.saveEx();
				*/			
			}

			rs.close();
			con.close();			

		} catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
	}

	/***
	 * Elimina informacion de procesos anteriores para este ID de modelo.
	 * OpenUp Ltda. Issue #1114 
	 * @author Gabriel Vila - 12/08/2013
	 * @see
	 */
	private void deleteOldData() {
		
		String action = "";
		
		try{

			this.showHelp("Elimando datos anteriores...");

			action = " DELETE FROM uy_bcu_capitalfinanciado cascade WHERE UY_Bcu_GracionTxtCapital_ID = " + this.get_ID();
			DB.executeUpdateEx(action, null);
			
			action = " DELETE FROM uy_bcu_datostxtcapital cascade WHERE UY_Bcu_GracionTxtCapital_ID = " + this.get_ID();
			DB.executeUpdateEx(action, null);
			
		}
		catch (Exception e)		{
			throw new AdempiereException (e);		
		}
		
	}
	
	/***
	 * Sumariza detalle del proceso segun necesidades del archivo plano a generar.
	 * OpenUp Ltda. Issue #1114 
	 * @author Gabriel Vila - 12/08/2013
	 * @see
	 */
	private void setAcumulado(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {	
			
			String sql = "WITH temp AS (" +
					     " SELECT cf.c_currency_id," + 
					     " cf.plazoremanente AS plazoremanente, cf.tea AS tasa, cf.capitalinteres AS acumuladodecapital,"	+ 
					     " CASE" +
					     " 		WHEN cf.tipodato::text = 'TRANSACCION'::text OR cf.tipodato::text = 'CONVENIO'::text THEN" + 
					     " 			CASE" +
					     " 				WHEN cf.isdebit = 'Y'::bpchar THEN -1" +
					     " 			ELSE +1" +
					     " 		END::numeric" +
					     " ELSE cf.cantidadoperaciones" +
					     " END AS cantidadoperaciones, cf.tipodato, cf.uy_bcu_graciontxtcapital_id," +
					     " CASE" +
					     " 		WHEN cf.tipodato::text = 'CONVENIO'::text THEN 1" +
					     " 		WHEN cf.tipodato::text = 'TRANSACCION'::text THEN 2" +
					     " 		WHEN cf.tipodato::text = 'REVOLVING'::text THEN 3" +
					     " 		ELSE NULL::integer" +
					     " END AS orden" +   
					     " FROM uy_bcu_capitalfinanciado cf" +						     
					     " WHERE cf.uy_bcu_graciontxtcapital_id = " + this.get_ID() +						     
			     " )" +
			     " SELECT t.uy_bcu_graciontxtcapital_id, t.c_currency_id," + 
			     " t.plazoremanente, t.tasa, sum(t.acumuladodecapital) AS acumuladodecapital, sum(t.cantidadoperaciones) AS cantidadoperaciones, t.tipodato, t.orden" +
			     " FROM temp t" +
			     " GROUP BY t.c_currency_id, t.plazoremanente, t.tasa, t.uy_bcu_graciontxtcapital_id, t.tipodato, t.orden";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, this.get_TrxName());
			rs = pstmt.executeQuery ();	
			
			while(rs.next()){
				
				MBcuDatosTxtCapital datos = new MBcuDatosTxtCapital(getCtx(), 0, null);
				datos.setUY_Bcu_GracionTxtCapital_ID(rs.getInt("uy_bcu_graciontxtcapital_id"));
				datos.setC_Currency_ID(rs.getInt("c_currency_id"));
				datos.setplazoremanente(rs.getBigDecimal("plazoremanente"));
				datos.setTasa(rs.getBigDecimal("tasa"));
				datos.setacumuladodecapital(rs.getBigDecimal("acumuladodecapital"));
				datos.setcantidadoperaciones(rs.getBigDecimal("cantidadoperaciones"));
				datos.setTipoDato(rs.getString("tipodato"));					
				datos.setorden(rs.getInt("orden"));
				
				datos.saveEx();

			}				
			
		} catch (Exception e) {
			throw new AdempiereException(e);

		} finally{
			DB.close(rs, pstmt);				
		}		
	}
	
	
}
