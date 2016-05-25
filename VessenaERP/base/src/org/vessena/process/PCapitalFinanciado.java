/**
 * 
 */
package org.openup.process;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MBcuCapitalFinanciado;
import org.openup.model.MBcuDatosTxtCapital;
import org.openup.model.MBcuGracionTxtCapital;
import org.openup.model.MFduAfinidad;
import org.openup.model.MFduProductos;
import org.openup.util.OpenUpUtils;


/**
 * @author gbrust
 *
 */
public class PCapitalFinanciado extends SvrProcess {
	
	private boolean obtieneDatos = false;
	private int idConexion = 1000010;
	private int periodoID = 0;
	private String file_directory = "";
	private String fileName = "";	


	private MBcuGracionTxtCapital modelo = null;
	
	public PCapitalFinanciado() {		
	}

	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("ObtieneDatos")){
					if(para[i].getParameter().toString().equals("Y")){
						this.obtieneDatos = true;
					}else if(para[i].getParameter().toString().equals("N")){
						this.obtieneDatos = false;
					}		
				}
				if (name.equalsIgnoreCase("File_Directory")){
					if(!para[i].getParameter().toString().equals(""))this.file_directory = para[i].getParameter().toString();
				}			
			}
		}	
		
		this.modelo = new MBcuGracionTxtCapital(getCtx(), this.getRecord_ID(), get_TrxName());
		
		//this.periodoID = new MBcuGracionTxtCapital(this.getCtx(), this.getRecord_ID(), this.get_TrxName()).getC_Period_ID();
	}

	
	@Override
	protected String doIt() throws Exception {

		String retorno = "OK";
		
		if (obtieneDatos){
			if (modelo.get_ID() <= 0)
				throw new AdempiereException("No se pudo obtener modelo para la consulta.");

			modelo.setWaiting(this.getProcessInfo().getWaiting());
			modelo.execute();
		}
		else
		{ 
			if(!this.file_directory.equals("")){
			
				this.generarTxt();
				retorno = "Archivo Generado OK";
			}
			else {
				throw new AdempiereException("No se eligió ruta para guardar archivo");
			}

		}
		return retorno;
	}
	
	private void getData(){
		
		this.deleteInstanciasViejas();
		
		this.getDataConvenios();
		this.getDataTransactions();		
		this.getDataRevolving();
		
		this.setView();
		
		ADialog.info(0,null,"Obtención de datos realizada con éxito");
		
	}
	
	private void deleteInstanciasViejas(){
				
		try{

			this.showHelp("Elimando datos anteriores...");
			String sql = "DELETE FROM UY_Bcu_CapitalFinanciado WHERE UY_Bcu_GracionTxtCapital_ID = " + this.getRecord_ID();
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)		{
			throw new AdempiereException (e);		
		}
	}
	
	
	private void getDataConvenios(){		
						
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		MPeriod period = new MPeriod(getCtx(), this.periodoID, this.get_TrxName());
		Timestamp fechaHasta = period.getEndDate();
		
		try {

			this.showHelp("Obteniendo Datos...");	
			
			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = OpenUpUtils.getConnectionToSqlServer(idConexion);
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			sql = "select Cuenta, 142 as Moneda, Cuotas, (Cuotas - (CAST((Saldo / Cuota) AS INT))) as Cuota_Actual, Cuota, CAST(Tasa_Financiacion as INT) as Tasa, saldo as Importe" +
				  " from FinancialPro.dbo.q_convenios_hist" +
				  " where fecha_proceso = (select MIN(fecha_proceso)" +
				  " 					   from FinancialPro.dbo.q_convenios_hist" +
				  " 					   where fecha_proceso > '" + df.format(fechaHasta) + "')" +				  
				  " and Estado = 'C'";

			rs = stmt.executeQuery(sql);
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			while (rs.next()) {
				this.showHelp("Registro " + rowCount++ + " de " + totalRowCount);			
				
				MBcuCapitalFinanciado capFin = new MBcuCapitalFinanciado(getCtx(), 0, this.get_TrxName());
				
				capFin.setDateTrx(new Timestamp(new Date().getTime()));
				capFin.setAccountNo(rs.getString("Cuenta"));
				capFin.setC_Currency_ID(rs.getInt("Moneda"));				
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
				capFin.setUY_Bcu_GracionTxtCapital_ID(this.getRecord_ID());
				capFin.setcantidadoperaciones(null);
												
				capFin.saveEx();			
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
	
	
	private void getDataTransactions(){			
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		MPeriod period = new MPeriod(getCtx(), this.periodoID, this.get_TrxName());		
		Timestamp fechaHasta = period.getEndDate();
		Timestamp fechaDesde = OpenUpUtils.sumaTiempo(period.getStartDate(), Calendar.DAY_OF_MONTH, -10);
				
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		try {

			this.showHelp("Obteniendo Datos...");	
			
			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = OpenUpUtils.getConnectionToSqlServer(idConexion);
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
									
			sql = " IF EXISTS (SELECT name FROM sysobjects WHERE name = 'TableCuentasConvenios' AND type = 'U')" +
				  " DROP TABLE op_control.TableCuentasConvenios";
			
			stmt.executeUpdate(sql);
			
			sql = " select distinct conv_hist.Cuenta as Cuenta into op_control.TableCuentasConvenios" + 
				  " from FinancialPro.dbo.q_convenios_hist conv_hist" +
				  " where conv_hist.fecha_proceso = (select MIN(fecha_proceso)" +
				  " 								 from FinancialPro.dbo.q_convenios_hist" +
				  " 								 where fecha_proceso > '" + df.format(fechaHasta) + "')" +				  
				  " and conv_hist.Estado = 'C'";
			
			stmt.executeUpdate(sql);
			
			sql = "SELECT mov.MDNroCta as Cuenta, mov.MndCod as Moneda, tea.producto as Producto, tea.afinidad as Afinidad, mov.MDCntCuo as Cuotas," +
				  " mov.MDCuoVig as CuotaActual, mov.MDNroCup, tea.tea as Tea," +
				  " tea.vigencia as VigenciaTasa, mov.MDFecOpe as FechaOperacion, mov.MDFecCta as FechaCierre, mov.MDFecPre as FechaPresentacion," +
				  " CASE WHEN mov.MDDebCre = '1' THEN 'N' ELSE 'Y' END as IsDebit," + 
				  " CASE WHEN mov.MDDebCre = '1' THEN mov.MDImpTot ELSE (mov.MDImpTot * -1) END AS importeTransaccion," + 
				  " CASE WHEN mov.MDDebCre = '1' THEN ((mov.MDCntCuo - mov.MDCuoVig + 1) * mov.MDImpTot) ELSE ((mov.MDCntCuo - mov.MDCuoVig + 1) * mov.MDImpTot) * -1 END AS importeCapitalFinanciado," +
				  " CASE WHEN (SELECT top 1 cf.accountno" +
				  " 		   FROM FPOP.op_control.VUY_Bcu_CapitalFinanciado cf" +
				  " 		   WHERE cf.accountno = mov.MDNroCta" +
				  " 		   AND cf.cuotas = mov.MDCntCuo" +
				  "            AND cf.cupon = mov.MDNroCup" +
				  " 		   AND cf.fechacierre < mov.MDFecCta" +
				  " 		   AND cf.fechaoperacion = mov.MDFecOpe" +
				  " 		   AND ((mov.MDCuoVig - cf.cuotaactual) = 1)" +
				  "			   order by cf.fechacierre desc) is not null THEN 'N' ELSE 'Y' END as IsIncosistente" +
				  " FROM FinancialPro.dbo.MOVCTAD mov, FinancialPro.dbo.SOCIOS1 soc, op_control.VUY_Fdu_Tea tea" +
				  " WHERE mov.MDFecCta > (SELECT MAX(SFecCierre)" +
				  " 					  FROM FinancialPro.dbo.SALDOCTA" +
				  " 					  WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +				  
				  " 					  AND SNroCta = mov.MDNroCta)" +
				  " AND mov.MDFecCta < (SELECT MAX(SVePMPr)" +
				  " 				FROM FinancialPro.dbo.SALDOCTA" +
				  " 				WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +	
				  " 				AND SNroCta = mov.MDNroCta)" +
				  " AND not exists (select t.Cuenta from op_control.TableCuentasConvenios t where t.Cuenta = mov.MDNroCta)" +				
				  " AND soc.STNroCta =  mov.MDNroCta" +
				  " AND mov.MDCntCuo > 1" +
				  " AND mov.MDCuoVig > 1" +				  
				  " AND soc.STUltiReg = '1'" +
				  " AND soc.STTipoSoc = '0'" +
				  " AND mov.MDNroCom NOT IN(SELECT distinct value" +
				  " 						FROM op_control.VUY_Fdu_ComerciosNoIpc" +
				  " 						WHERE validity >= (SELECT max(validity)" +
				  " 										   FROM op_control.VUY_Fdu_ComerciosNoIpc" +
			      " 										   WHERE validity <=  mov.MDFecOpe))" +
			      " AND CAST(tea.producto AS INT) = soc.PTCod" +
			      " AND tea.afinidad = '0' + SUBSTRING(RTRIM(soc.GAFCod), 1, 4)" +
			      " AND tea.moneda = CASE WHEN mov.MndCod = 0 THEN '858' ELSE '840' END" +
			      " AND tea.cuota = mov.MDCntCuo" +
			      " AND tea.vigencia = (SELECT max(vigencia)" +
				  " 					FROM op_control.VUY_Fdu_Tea" +
				  " 					WHERE vigencia <= mov.MDFecOpe)" +  
				  " GROUP BY mov.MDNroCta, mov.MndCod, tea.producto, tea.afinidad, mov.MDCntCuo, mov.MDCuoVig, mov.MDNroCup, tea.tea, tea.vigencia, mov.MDFecOpe, mov.MDFecCta, mov.MDFecPre, mov.MDDebCre, mov.MDImpTot" +
				  " union" +
				  " SELECT mov.MDNroCta as Cuenta, mov.MndCod as Moneda, tea.producto as Producto, tea.afinidad as Afinidad, mov.MDCntCuo as Cuotas," +
				  " mov.MDCuoVig as CuotaActual, mov.MDNroCup, tea.tea as Tea," +
				  " tea.vigencia as VigenciaTasa, mov.MDFecOpe as FechaOperacion, mov.MDFecCta as FechaCierre, mov.MDFecPre as FechaPresentacion," +
				  " CASE WHEN mov.MDDebCre = '1' THEN 'N' ELSE 'Y' END as IsCredit," + 
				  " CASE WHEN mov.MDDebCre = '1' THEN mov.MDImpTot ELSE (mov.MDImpTot * -1) END AS importeTransaccion," + 
				  " CASE WHEN mov.MDDebCre = '1' THEN ((mov.MDCntCuo - mov.MDCuoVig + 1) * mov.MDImpTot) ELSE ((mov.MDCntCuo - mov.MDCuoVig + 1) * mov.MDImpTot) * -1 END AS importeCapitalFinanciado," +
				  " CASE WHEN (SELECT top 1 cf.accountno" +
				  " 		   FROM FPOP.op_control.VUY_Bcu_CapitalFinanciado cf" +
				  " 		   WHERE cf.accountno = mov.MDNroCta" +
				  " 		   AND cf.cuotas = mov.MDCntCuo" +
				  " 		   AND cf.cupon = mov.MDNroCup" +
				  " 		   AND cf.fechacierre < mov.MDFecCta" +
				  " 		   AND cf.fechaoperacion = mov.MDFecOpe" +
				  " 		   AND ((mov.MDCuoVig - cf.cuotaactual) = 1)" +
				  "			   order by cf.fechacierre desc) is not null THEN 'N' ELSE 'Y' END as IsIncosistente" +
				  " FROM FinancialPro.dbo.MOVCTAD mov, FinancialPro.dbo.SOCIOS1 soc, op_control.VUY_Fdu_Tea tea" +
				  " WHERE mov.MDFecCta > (SELECT MAX(SFecCierre)" +
				  " 		   			  FROM FinancialPro.dbo.SALDOCTA" +
				  " 		   			  WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +	
				  " 		   			  AND SNroCta = mov.MDNroCta)" +
				  " AND mov.MDFecCta < (SELECT MAX(SVePMPr)" +
				  " 				FROM FinancialPro.dbo.SALDOCTA" +
				  " 				WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +	
				  " 				AND SNroCta = mov.MDNroCta)" +
				  " AND mov.MDFecPre <= '" + df.format(fechaHasta) + "'" +
				  " AND not exists (select t.Cuenta from op_control.TableCuentasConvenios t where t.Cuenta = mov.MDNroCta)" +				
				  " AND soc.STNroCta =  mov.MDNroCta" +
				  " AND mov.MDCntCuo > 1" +
				  " AND mov.MDCuoVig < 2" +				  
				  " AND soc.STUltiReg = '1'" +
				  " AND soc.STTipoSoc = '0'" +
				  " AND mov.MDNroCom NOT IN(SELECT distinct value" +
				  " 						FROM op_control.VUY_Fdu_ComerciosNoIpc" +
				  " 						WHERE validity >= (SELECT max(validity)" +
				  " 										   FROM op_control.VUY_Fdu_ComerciosNoIpc" +
				  " 										   WHERE validity <=  mov.MDFecOpe))" +
				  " AND CAST(tea.producto AS INT) = soc.PTCod" +
				  " AND tea.afinidad = '0' + SUBSTRING(RTRIM(soc.GAFCod), 1, 4)" +
				  " AND tea.moneda = CASE WHEN mov.MndCod = 0 THEN '858' ELSE '840' END" +
				  " AND tea.cuota = mov.MDCntCuo" +
				  " AND tea.vigencia = (SELECT max(vigencia)" +
				  " 					FROM op_control.VUY_Fdu_Tea" +
				  " 					WHERE vigencia <= mov.MDFecOpe)" +  
				  " GROUP BY mov.MDNroCta, mov.MndCod, tea.producto, tea.afinidad, mov.MDCntCuo, mov.MDCuoVig, mov.MDNroCup, tea.tea, tea.vigencia, mov.MDFecOpe, mov.MDFecCta, mov.MDFecPre, mov.MDDebCre, mov.MDImpTot" +
				  " order by mov.MDNroCta, mov.MDNroCup";

			rs = stmt.executeQuery(sql);			
			
			while (rs.next()) {				
				
				MBcuCapitalFinanciado capFin = new MBcuCapitalFinanciado(getCtx(), 0, this.get_TrxName());
				
				capFin.setDateTrx(new Timestamp(new Date().getTime()));
				capFin.setAccountNo(rs.getString("Cuenta"));
				capFin.setC_Currency_ID((rs.getString("Moneda").equals("0") ? 142 : 100));
				int fduProductosID = MFduProductos.getMFduProductosForValue(getCtx(), rs.getString("Producto")).get_ID();
				capFin.setUY_Fdu_Productos_ID(fduProductosID);
				int fduAfinidadID = MFduAfinidad.getMFduAfinidadForValue(getCtx(), rs.getString("Afinidad")).get_ID();
				capFin.setUY_Fdu_Afinidad_ID(fduAfinidadID);
				capFin.setCuotas(rs.getInt("Cuotas"));
				capFin.setCuotaActual(rs.getInt("CuotaActual"));
				capFin.setCupon(String.valueOf(rs.getInt("MDNroCup")));				
				capFin.setTea(rs.getBigDecimal("Tea"));				
				capFin.setvigencia(rs.getTimestamp("VigenciaTasa"));
				capFin.setFechaOperacion(rs.getTimestamp("FechaOperacion"));
				capFin.setFechaCierre(rs.getTimestamp("FechaCierre"));
				capFin.setFechaPresentacion(rs.getTimestamp("FechaPresentacion"));
				capFin.setIsDebit(rs.getString("IsDebit").equals("Y") ? true : false);
				capFin.setAmount(rs.getBigDecimal("importeTransaccion"));
				capFin.setCapitalInteres(rs.getBigDecimal("ImporteCapitalFinanciado"));
				//boolean isIncosistente = this.getIsIncosistente(rs.getString("Cuenta"), rs.getInt("Cuotas"), String.valueOf(rs.getInt("MDNroCup")), rs.getTimestamp("FechaCierre"), rs.getTimestamp("FechaOperacion"), rs.getInt("CuotaActual"));
				capFin.setIsIncosistente(rs.getString("IsIncosistente").equals("Y") ? true : false);
				capFin.setTipoDato("TRANSACCION");
				capFin.setUY_Bcu_GracionTxtCapital_ID(this.getRecord_ID());
				
				capFin.saveEx();			
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
	
		
	private void getDataRevolving(){		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		MPeriod period = new MPeriod(getCtx(), this.periodoID, this.get_TrxName());		
		Timestamp fechaHasta = period.getEndDate();
		Timestamp fechaDesde = OpenUpUtils.sumaTiempo(period.getStartDate(), Calendar.DAY_OF_MONTH, -10);
								
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		try {

			this.showHelp("Obteniendo Datos...");	
			
			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = OpenUpUtils.getConnectionToSqlServer(idConexion);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			sql = "select 142 as Moneda, CAST(ROUND((POWER(1 + (saldo.STasaEfMe / 100), 12) -1) * 100, 0) AS INT) as Tasa," +
				  " round(SUM(saldo.SSalAPeDo - saldo.STotPgoPes), 2) as Importe, COUNT(saldo.SNroCta) as CantidadOperaciones" +
				  " from FinancialPro.dbo.SALDOCTA saldo" +
				  " where saldo.SFecCierre > (SELECT MAX(SFecCierre)" +
				  " 						  FROM FinancialPro.dbo.SALDOCTA" +
				  " 						  WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +	
				  " 						  AND SNroCta = saldo.SNroCta)" +
				  " AND saldo.SFecCierre < (SELECT MAX(SVePMPr)" +
				  " 						FROM FinancialPro.dbo.SALDOCTA" +
				  " 						WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +	
				  " 						AND SNroCta = saldo.SNroCta)" +
				  " and not exists (select t.Cuenta from op_control.TableCuentasConvenios t where t.Cuenta = saldo.SNroCta)" +
				  " and (saldo.SSalAPeDo - saldo.STotPgoPes) > 0" +
				  " group by saldo.STasaEfMe" +
				  " union" +
				  " select 100 as Moneda, CAST(ROUND((POWER(1 + (saldo.STasEMDol / 100), 12) -1) * 100, 0) AS INT) as Tasa," +
				  " round(SUM((saldo.SSdoAntDol - saldo.STotPgoDol)), 2) as Importe, COUNT(saldo.SNroCta) as CantidadOperaciones" +
				  " from FinancialPro.dbo.SALDOCTA saldo" +
				  " where saldo.SFecCierre > (SELECT MAX(SFecCierre)" +
				  " 						  FROM FinancialPro.dbo.SALDOCTA" +
				  " 						  WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +	
				  " 						  AND SNroCta = saldo.SNroCta)" +
				  " AND saldo.SFecCierre < (SELECT MAX(SVePMPr)" +
				  " 						FROM FinancialPro.dbo.SALDOCTA" +
				  " 						WHERE SFecCierre >= '" + df.format(fechaDesde) + "' AND SFecCierre <= '" + df.format(fechaHasta) + "'" +	
				  " 						AND SNroCta = saldo.SNroCta)" +
				  " and not exists (select t.Cuenta from op_control.TableCuentasConvenios t where t.Cuenta = saldo.SNroCta)" +
				  " and (saldo.SSdoAntDol - saldo.STotPgoDol) > 0" +
				  " group by saldo.STasEMDol";

			rs = stmt.executeQuery(sql);
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			while (rs.next()) {
				this.showHelp("Registro " + rowCount++ + " de " + totalRowCount);			
				
				MBcuCapitalFinanciado capFin = new MBcuCapitalFinanciado(getCtx(), 0, this.get_TrxName());
				
				capFin.setDateTrx(new Timestamp(new Date().getTime()));
				capFin.setAccountNo(null);
				capFin.setC_Currency_ID(rs.getInt("Moneda"));				
				capFin.setUY_Fdu_Productos_ID(0);				
				capFin.setUY_Fdu_Afinidad_ID(0);
				capFin.setCuotas(0);
				capFin.setCuotaActual(0);
				capFin.setCupon(null);
				capFin.setTasa(null);
				capFin.setTea(rs.getBigDecimal("Tasa"));			
				capFin.setvigencia(null);
				capFin.setFechaOperacion(fechaHasta);
				capFin.setFechaCierre(null);
				capFin.setIsDebit(false);
				capFin.setAmount(null);
				capFin.setCapitalInteres(rs.getBigDecimal("importe"));				
				capFin.setIsIncosistente(false);
				capFin.setTipoDato("REVOLVING");
				capFin.setUY_Bcu_GracionTxtCapital_ID(this.getRecord_ID());
				capFin.setcantidadoperaciones(rs.getBigDecimal("CantidadOperaciones"));
				capFin.setplazoremanente(new BigDecimal(rs.getInt("Plazo_Remanenete")));
																
				capFin.saveEx();			
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
	
	private void setView(){
		
		try {			
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			try {	
				
				String sql = "DELETE FROM UY_Bcu_DatosTxtCapital WHERE UY_Bcu_GracionTxtCapital_ID = " + this.getRecord_ID();
				DB.executeUpdateEx(sql,null);
						
				sql = "WITH temp AS (" +
						     " SELECT cf.c_currency_id," + 
						     " CASE" +
						     " 		WHEN cf.tipodato::text = 'TRANSACCION'::text THEN ((cf.cuotas - cf.cuotaactual) +1) * 30::numeric" +
						     " 		WHEN cf.tipodato::text = 'CONVENIO'::text THEN (cf.cuotas - cf.cuotaactual) * 30::numeric" +
						     "      ELSE 30::numeric" +
						     " END AS plazoremanente, cf.tea AS tasa, cf.capitalinteres AS acumuladodecapital,"	+ 
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
						     " WHERE cf.uy_bcu_graciontxtcapital_id = " + this.getRecord_ID() +						     
				     " )" +
				     " SELECT t.uy_bcu_graciontxtcapital_id, t.c_currency_id," + 
				     " t.plazoremanente, t.tasa, sum(t.acumuladodecapital) AS acumuladodecapital, sum(t.cantidadoperaciones) AS cantidadoperaciones, t.tipodato, t.orden" +
				     " FROM temp t" +
				     " GROUP BY t.c_currency_id, t.plazoremanente, t.tasa, t.uy_bcu_graciontxtcapital_id, t.tipodato, t.orden";
				
				pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, this.get_TrxName());
				rs = pstmt.executeQuery ();	
				
				while(rs.next()){
					
					MBcuDatosTxtCapital datos = new MBcuDatosTxtCapital(getCtx(), 0, this.get_TrxName());
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
				DB.close(rs, pstmt);	
				throw new AdempiereException(e);

			} finally{
				DB.close(rs, pstmt);				
			}		
			
		} catch (Exception e) {
			new AdempiereException(e);
		} 
	}
	
	
	private void generarTxt(){
		
		this.createFile();
		
		String cadena = "";
		BufferedWriter bw = null;	
		int lineasCapitalCero = 0;
				
		try {
			if(fileName != null ) bw = new BufferedWriter(new FileWriter(fileName,true));
			
			//CABEZAL			
			MPeriod period = (MPeriod)modelo.getC_Period();
			String anio = period.getStartDate().toString().substring(0, 4);			
			String mes = period.getStartDate().toString().substring(5, 7);
			cadena = "TS" + "CABEZAL"  + "0852" + anio + mes + "000000" + "0000";	
			
			if(cadena != null && !cadena.equals("")) this.saveInFile(cadena,bw); //escribo la linea en archivo			
			//FIN CABEZAL
			
			//DETALLE
			//Primero Convenios, luego Transacciones Comunes, y por ultimo el Revolving.
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			String sql = "";
			
			try {
				//Hacemos esto para cada moneda
				//Primero hacemos la moneda pesos, luego dolares
				for (int i = 0; i < 2; i++) {
					
					String campoMoneda = (i == 0 ? "6900" : "2222");
					String currencyID = (i == 0 ? "142" : "100");
					
					//CONVENIOS			
					sql = "select " + campoMoneda + " AS Moneda, plazoremanente, tea, capitalinteres, cantidadoperaciones" +
						  " from uy_bcu_capitalfinanciado" + 
						  " where uy_bcu_graciontxtcapital_id = " + this.getRecord_ID() +
						  " AND c_currency_id = " + currencyID +
						  " AND tipodato = 'CONVENIO'";
					
					pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
					rs = pstmt.executeQuery ();	
					
					while(rs.next()){
						
						BigDecimal capital = rs.getBigDecimal("CapitalInteres").setScale(2, RoundingMode.HALF_UP);	
						
						if(capital.compareTo(Env.ZERO)!=0){
							
							boolean capitalNegativo = false;
							boolean cantidadNegativa = false;
							boolean plazoNegativo = false;
							
							//Obtengo los datos
							String moneda = rs.getString("Moneda");
							String tasaConPunto = rs.getBigDecimal("Tea").toString() + "0000";
							
							capital = rs.getBigDecimal("CapitalInteres").setScale(2, RoundingMode.HALF_UP);												
							//Pregunto si el capital es negativo
							if (capital.compareTo(Env.ZERO) < 0) {
								capitalNegativo = true;
								capital = capital.negate();
							}
							String capitalAcumuladoConPunto = capital.toString();
							
							int cantidad = rs.getInt("CantidadOperaciones");						
							//Pregunto si el plazo es negativo
							if (cantidad < 0) {
								cantidadNegativa = true;
								cantidad = cantidad * -1;
							}
							String cantOperaciones = Integer.toString(cantidad);
							
							int plazo = rs.getInt("PlazoRemanente");						
							//Pregunto si el plazo es negativo
							if (plazo < 0) {
								plazoNegativo = true;
								plazo = plazo * -1;							
							}
							String plazoRemanente = Integer.toString(plazo);
												
							//Los formateo de manera correcta
							String tasa = "";
							for (int x=0; x < tasaConPunto.length(); x++) {
								  if (tasaConPunto.charAt(x) != '.') tasa += tasaConPunto.charAt(x);
							}		
							while(tasa.length() < 8) tasa = "0" + tasa;					
							String capitalAcumulado = "";
							for (int x=0; x < capitalAcumuladoConPunto.length(); x++) {
								  if (capitalAcumuladoConPunto.charAt(x) != '.') capitalAcumulado += capitalAcumuladoConPunto.charAt(x);
							}					
							while(capitalAcumulado.length() < 16) capitalAcumulado = "0" + capitalAcumulado;
							//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
							if(capitalAcumulado.length() == 16){
								if(capitalNegativo) capitalAcumulado = "-" + capitalAcumulado; else capitalAcumulado = "0" + capitalAcumulado;
							}	
							while(cantOperaciones.length() < 5) cantOperaciones = "0" + cantOperaciones;	
							//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
							if(cantOperaciones.length() == 5){
								if(cantidadNegativa) cantOperaciones = "-" + cantOperaciones; else cantOperaciones = "0" + cantOperaciones;
							}							
							while(plazoRemanente.length() < 4) plazoRemanente = "0" + plazoRemanente;
							//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
							if(plazoRemanente.length() == 4){
								if(plazoNegativo) plazoRemanente = "-" + plazoRemanente; else plazoRemanente = "0" + plazoRemanente;
							}	
							
							cadena = "TS" + "DETALLE"  + "0852" + anio + mes + "173001" + moneda + "R" + "97030" + tasa + capitalAcumulado + plazoRemanente + cantOperaciones + "_Convenio";	
							
							if(cadena != null && !cadena.equals("")) this.saveInFile(cadena,bw); //escribo la linea en archivo							
							
						} else lineasCapitalCero++;						
					}	
					//FIN CONVENIOS
					
					java.lang.Thread.sleep(3000);
					
					//TRANSACCIONES COMUNES				
					sql = "select " + campoMoneda + " AS Moneda, plazoremanente, tea, capitalinteres, cantidadoperaciones" +
						  " from uy_bcu_capitalfinanciado" + 
						  " where uy_bcu_graciontxtcapital_id = " + this.getRecord_ID() +
						  " AND c_currency_id = " + currencyID +
						  " AND tipodato = 'TRANSACCION'";
					
					pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
					rs = pstmt.executeQuery ();	
					
					while(rs.next()){
						
						BigDecimal capital = rs.getBigDecimal("CapitalInteres").setScale(2, RoundingMode.HALF_UP);	
						
						if(capital.compareTo(Env.ZERO)!=0){
							
							boolean capitalNegativo = false;
							boolean cantidadNegativa = false;
							boolean plazoNegativo = false;
							
							//Obtengo los datos
							String moneda = rs.getString("Moneda");
							String tasa = rs.getBigDecimal("Tea").toString() + "0000";						
							
						    capital = rs.getBigDecimal("CapitalInteres").setScale(2, RoundingMode.HALF_UP);												
							//Pregunto si el capital es negativo
							if (capital.compareTo(Env.ZERO) < 0) {
								capitalNegativo = true;
								capital = capital.negate();
							}
							String capitalAcumuladoConPunto = capital.toString();
							
							int cantidad = rs.getInt("CantidadOperaciones");						
							//Pregunto si el plazo es negativo
							if (cantidad < 0) {
								cantidadNegativa = true;
								cantidad = cantidad * -1;
							}
							String cantOperaciones = Integer.toString(cantidad);
							
							int plazo = rs.getInt("PlazoRemanente");						
							//Pregunto si el plazo es negativo
							if (plazo < 0) {
								plazoNegativo = true;
								plazo = plazo * -1;							
							}
							String plazoRemanente = Integer.toString(plazo);
							
							//Los formateo de manera correcta
							while(tasa.length() < 8) tasa = "0" + tasa;					
							String capitalAcumulado = "";
							for (int x=0; x < capitalAcumuladoConPunto.length(); x++) {
								  if (capitalAcumuladoConPunto.charAt(x) != '.') capitalAcumulado += capitalAcumuladoConPunto.charAt(x);
							}					
							while(capitalAcumulado.length() < 16) capitalAcumulado = "0" + capitalAcumulado;
							//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
							if(capitalAcumulado.length() == 16){
								if(capitalNegativo) capitalAcumulado = "-" + capitalAcumulado; else capitalAcumulado = "0" + capitalAcumulado;
							}	
							while(cantOperaciones.length() < 5) cantOperaciones = "0" + cantOperaciones;	
							//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
							if(cantOperaciones.length() == 5){
								if(cantidadNegativa) cantOperaciones = "-" + cantOperaciones; else cantOperaciones = "0" + cantOperaciones;
							}							
							while(plazoRemanente.length() < 4) plazoRemanente = "0" + plazoRemanente;
							//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
							if(plazoRemanente.length() == 4){
								if(plazoNegativo) plazoRemanente = "-" + plazoRemanente; else plazoRemanente = "0" + plazoRemanente;
							}						
							
							cadena = "TS" + "DETALLE"  + "0852" + anio + mes + "173001" + moneda + "R" + "97030" + tasa + capitalAcumulado + plazoRemanente + cantOperaciones + "_Transacciones";	
							
							if(cadena != null && !cadena.equals("")) this.saveInFile(cadena,bw); //escribo la linea en archivo					
							
						} else lineasCapitalCero++;				
						
					}	
					//FIN TRANSACCIONES COMUNES
					
					java.lang.Thread.sleep(3000);
					
					//REVOLVING				
					sql = "select " + campoMoneda + " AS Moneda, plazoremanente, tea, capitalinteres, cantidadoperaciones" +
						  " from uy_bcu_capitalfinanciado" + 
						  " where uy_bcu_graciontxtcapital_id = " + this.getRecord_ID() +
						  " AND c_currency_id = " + currencyID +
						  " AND tipodato = 'REVOLVING'";
				
					pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
					rs = pstmt.executeQuery ();	
					
					while(rs.next()){
						
						BigDecimal capital = rs.getBigDecimal("CapitalInteres").setScale(2, RoundingMode.HALF_UP);	
						
						if(capital.compareTo(Env.ZERO)!=0){
							
							boolean capitalNegativo = false;
							boolean cantidadNegativa = false;
							boolean plazoNegativo = false;
							
							//Obtengo los datos
							String moneda = rs.getString("Moneda");
							String tasa = Integer.toString(rs.getBigDecimal("Tea").intValueExact()) + "0000";
							
							capital = rs.getBigDecimal("CapitalInteres").setScale(2, RoundingMode.HALF_UP);												
							//Pregunto si el capital es negativo
							if (capital.compareTo(Env.ZERO) < 0) {
								capitalNegativo = true;
								capital = capital.negate();
							}
							String capitalAcumuladoConPunto = capital.toString();
							
							int cantidad = rs.getInt("CantidadOperaciones");						
							//Pregunto si el plazo es negativo
							if (cantidad < 0) {
								cantidadNegativa = true;
								cantidad = cantidad * -1;
							}
							String cantOperaciones = Integer.toString(cantidad);
							
							int plazo = rs.getInt("PlazoRemanente");						
							//Pregunto si el plazo es negativo
							if (plazo < 0) {
								plazoNegativo = true;
								plazo = plazo * -1;							
							}
							String plazoRemanente = Integer.toString(plazo);
							
							//Los formateo de manera correcta
							while(tasa.length() < 8) tasa = "0" + tasa;					
							String capitalAcumulado = "";
							for (int x=0; x < capitalAcumuladoConPunto.length(); x++) {
								  if (capitalAcumuladoConPunto.charAt(x) != '.') capitalAcumulado += capitalAcumuladoConPunto.charAt(x);
							}					
							while(capitalAcumulado.length() < 16) capitalAcumulado = "0" + capitalAcumulado;
							//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
							if(capitalAcumulado.length() == 16){
								if(capitalNegativo) capitalAcumulado = "-" + capitalAcumulado; else capitalAcumulado = "0" + capitalAcumulado;
							}	
							while(cantOperaciones.length() < 5) cantOperaciones = "0" + cantOperaciones;	
							//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
							if(cantOperaciones.length() == 5){
								if(cantidadNegativa) cantOperaciones = "-" + cantOperaciones; else cantOperaciones = "0" + cantOperaciones;
							}							
							while(plazoRemanente.length() < 4) plazoRemanente = "0" + plazoRemanente;
							//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
							if(plazoRemanente.length() == 4){
								if(plazoNegativo) plazoRemanente = "-" + plazoRemanente; else plazoRemanente = "0" + plazoRemanente;
							}						
							
							cadena = "TS" + "DETALLE"  + "0852" + anio + mes + "173001" + moneda + "R" + "97030" + tasa + capitalAcumulado + plazoRemanente + cantOperaciones + "_Revolving";	
							
							if(cadena != null && !cadena.equals("")) this.saveInFile(cadena,bw); //escribo la linea en archivo							
							
						} else lineasCapitalCero++; 
						
					}				
					//FIN REVOLVING	
					
					java.lang.Thread.sleep(3000);
					
					//FIN DETALLE					
					
					//CONTROL - TOTALES
					sql = "select " + campoMoneda + " AS Moneda, sum(capitalinteres) AS AcumuladoDeCapital, sum(cantidadoperaciones) - " + lineasCapitalCero + " AS CantidadOperaciones" +
						  " from uy_bcu_capitalfinanciado" +
						  " where uy_bcu_graciontxtcapital_id = " + this.getRecord_ID() +
						  " and c_currency_id = " + currencyID;
				
					pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
					rs = pstmt.executeQuery ();	
				
					while(rs.next()){	
						
						boolean capitalNegativo = false;
						boolean cantidadNegativa = false;					
						
						//Obtengo los datos
						String moneda = rs.getString("Moneda");

						BigDecimal capital = rs.getBigDecimal("AcumuladoDeCapital").setScale(2, RoundingMode.HALF_UP);												
						//Pregunto si el capital es negativo
						if (capital.compareTo(Env.ZERO) < 0) {
							capitalNegativo = true;
							capital = capital.negate();
						}
						String capitalAcumuladoConPunto = capital.toString();
						
						int cantidad = rs.getInt("CantidadOperaciones");						
						//Pregunto si el plazo es negativo
						if (cantidad < 0) {
							cantidadNegativa = true;
							cantidad = cantidad * -1;
						}
						String cantOperaciones = Integer.toString(cantidad);						
						
						String capitalAcumulado = "";
						for (int x=0; x < capitalAcumuladoConPunto.length(); x++) {
							  if (capitalAcumuladoConPunto.charAt(x) != '.') capitalAcumulado += capitalAcumuladoConPunto.charAt(x);
						}					
						while(capitalAcumulado.length() < 16) capitalAcumulado = "0" + capitalAcumulado;
						//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
						if(capitalAcumulado.length() == 16){
							if(capitalNegativo) capitalAcumulado = "-" + capitalAcumulado; else capitalAcumulado = "0" + capitalAcumulado;
						}	
						while(cantOperaciones.length() < 5) cantOperaciones = "0" + cantOperaciones;	
						//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
						if(cantOperaciones.length() == 5){
							if(cantidadNegativa) cantOperaciones = "-" + cantOperaciones; else cantOperaciones = "0" + cantOperaciones;
						}										
					
						cadena = "TS" + "TOTALES"  + "0852" + anio + mes + "173001" + moneda + capitalAcumulado +  cantOperaciones;	
					
						if(cadena != null && !cadena.equals("")) this.saveInFile(cadena,bw); //escribo la linea en archivo	
					}	
					//FIN CONTROL-TOTALES
				}
				
				java.lang.Thread.sleep(3000);
				
				//REGISTRO FINAL
				sql = "SELECT sum(cantidadoperaciones) - " + lineasCapitalCero + " AS CantidadOperaciones, count(uy_bcu_capitalfinanciado_id) - " + lineasCapitalCero + " AS Registros" +
					  " FROM uy_bcu_capitalfinanciado" +
					  " WHERE uy_bcu_graciontxtcapital_id = " + this.getRecord_ID();
			
				pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
				rs = pstmt.executeQuery ();	
			
				while(rs.next()){	
					
					boolean cantidadNegativa = false;
					
					//Obtengo los datos
					
					int cantidad = rs.getInt("CantidadOperaciones");						
					//Pregunto si el plazo es negativo
					if (cantidad < 0) {
						cantidadNegativa = true;
						cantidad = cantidad * -1;
					}
					String cantOperaciones = Integer.toString(cantidad);							
					
					String registros = Integer.toString(rs.getInt("Registros"));
					while(cantOperaciones.length() < 6) cantOperaciones = "0" + cantOperaciones;	
					//Si es capital negativo le agrego el signo a lo ultimo, sino le agrego un cero
					if(cantOperaciones.length() == 6){
						if(cantidadNegativa) cantOperaciones = "-" + cantOperaciones; else cantOperaciones = "0" + cantOperaciones;
					}		
					while(registros.length() < 7) registros = "0" + registros;					
				
					cadena = "TS" + "ULTIMO "  + "0852" + anio + mes + "999999" + "9999" + cantOperaciones + registros;	
				
					if(cadena != null && !cadena.equals("")) this.saveInFile(cadena,bw); //escribo la linea en archivo	
				}	
				//FIN REGISTRO FINAL					
				
			} catch (Exception e) {
				DB.close(rs, pstmt);	
				throw new AdempiereException(e);

			} finally{
				DB.close(rs, pstmt);
				bw.close();
			}
			
			
			bw.close();
			ADialog.info(0,null,"Generación de Archivo realizada con éxito");
			
		} catch (Exception e) {
			new AdempiereException(e);
		} 
	}
	
	
	private void createFile() {	
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
			
		if(file_directory != null && !file_directory.equals("")){
	
			this.fileName = this.file_directory + "\\BCU_" + this.getRecord_ID() + "_" + df.format(new Timestamp(System.currentTimeMillis())) + ".txt";
	
		} else throw new AdempiereException("Ruta destino invalida");
			
		//se crea el archivo a utilizar
		File file = new File (this.fileName);
		
		try {
			// A partir del objeto File se crea el archivo fisicamente
			if (!file.createNewFile()) throw new AdempiereException ("No se pudo crear el archivo. Compruebe que no existe un archivo de igual nombre en la carpeta destino.");

		} catch (IOException ex) {
			throw new AdempiereException(ex.getMessage());			
		}
			
	}	
	
	private void saveInFile(String cadena,BufferedWriter bf) throws Exception { 		
		
		try {
			if(cadena != null && bf != null){
				String ln = System.getProperty("line.separator");
				bf.append(cadena + ln);										
			}

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} 
	}

	
	/***
	 * Despliega avance en ventana splash
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 29/10/2012
	 * @see
	 * @param text
	 */
	private void showHelp(String text){
		if (this.getProcessInfo().getWaiting() != null){
			this.getProcessInfo().getWaiting().setText(text);
		}			
	}

}
