/**
 * 
 */
package org.openup.process;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.openup.beans.FduPagosRealizado;
import org.openup.model.MFduCargo;
import org.openup.model.MFduCargoImporte;
import org.openup.model.MFduCargoModelo;
import org.openup.model.MFduCod;
import org.openup.model.MFduLogisticMonthDates;
import org.openup.model.MFduMassiveAdjustLine;
import org.openup.model.MFduMassiveAdjustment;
import org.openup.model.MFduModeloLiquidacion;
import org.openup.util.ItalcredSystem;
import org.openup.util.OpenUpUtils;

/**
 * @author gbrust
 *
 */
public class PFduAjusteMasivo extends SvrProcess {

	
	private MFduMassiveAdjustment mFduMassiveAdjustment = null;
	private Timestamp fechaCierre = null;
	private Timestamp fechaCorrida = null;
	private MFduCargo cargo = null;
		
	
	public PFduAjusteMasivo() {
		
	}

	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null && para[i].getParameter()!=null){
				if (name.equalsIgnoreCase("UY_Fdu_MassiveAdjustment_ID"))
					this.mFduMassiveAdjustment = new MFduMassiveAdjustment(this.getCtx(), ((BigDecimal)para[i].getParameter()).intValueExact(), this.get_TrxName());
			}
		}		
		
		if(this.mFduMassiveAdjustment != null && this.mFduMassiveAdjustment.get_ID() > 0) {
			this.fechaCierre = this.mFduMassiveAdjustment.getUY_Fdu_LogisticMonthDates().getDateTrx();
			this.fechaCorrida = this.mFduMassiveAdjustment.getDateTrx();
		}
		//Agregamos este proceso a la agenda de procesos, aca cargamos la mFduMasiveAdjusment Manualmente
		 //OpenUp. Leonardo Boccone. 03/09/2014. ISSUE #2501
		else{
			String sql = "select uy_fdu_logisticmonthdates_id from uy_fdu_logisticmonthdates where datetrx>= now() order by datetrx";
			int LogisticMonth = DB.getSQLValue(this.get_TrxName(), sql);			
			this.fechaCorrida = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			this.mFduMassiveAdjustment = new MFduMassiveAdjustment(this.getCtx(),0, this.get_TrxName());			
			this.mFduMassiveAdjustment.setDateTrx(fechaCorrida);		
			MFduLogisticMonthDates cierre =new MFduLogisticMonthDates(this.getCtx(),LogisticMonth,this.get_TrxName());
			this.fechaCierre = cierre.getDateTrx();
			this.mFduMassiveAdjustment.setUY_Fdu_LogisticMonthDates_ID(cierre.get_ID());
			MClient client = this.getDefaultClient();
			this.mFduMassiveAdjustment.setAD_Client_ID(client.getAD_Client_ID());
			this.mFduMassiveAdjustment.setAD_Org_ID(client.getAD_Org_ID());
			// Sylvie Bouissa Issue #2501 14-11-2014  
			// MDocType docType = MDocType.forValue(this.getCtx(), "ajumasivo", this.get_TrxName());//--> aca!!!
			MDocType docType = MDocType.forValueAndSystem(this.getCtx(), "ajumasivo", this.get_TrxName());
			this.mFduMassiveAdjustment.setC_DocType_ID(docType.get_ID());			
			MUser usuario = MUser.forNameAndSystem(this.getCtx(), "adempiere", this.get_TrxName());
			this.mFduMassiveAdjustment.setAD_User_ID(usuario.get_ID());
			this.mFduMassiveAdjustment.setDocStatus("DR");
			this.mFduMassiveAdjustment.setDocAction("CO");
			
			this.mFduMassiveAdjustment.saveEx();
		}
		//Fin Openup ISSUE #2501
		//Obtengo el codigo de aviso de mora
		MFduCod cod = MFduCod.getMFduCodForValue(this.getCtx(), null, "120");		
		
		//instancio el cargo que voy a aplicar, aca tengo el importe que debo aplicar		
		this.cargo = MFduCargo.forCodigoIDAndMaxValidate(this.getCtx(), cod.get_ID(), this.fechaCierre, this.get_TrxName());
	}

	
	/***
	 * Obtiene y retorna primer empresa que no es la System.
	 * OpenUp Ltda. Issue #189 
	 * @author Gabriel Vila - 28/02/2013
	 * @see
	 * @return
	 */
	private MClient getDefaultClient(){
		
		String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "!=0";
		
		MClient value = new Query(getCtx(), I_AD_Client.Table_Name, whereClause, null)
		.first();
		
		return value;
	}


	@Override
	protected String doIt() throws Exception {				
		
		//Borro lineas para este cabezal.
		this.borrarInstanciasViejas();
					
		//Obtengo cuentas a aplicar
		this.obtenerCuentasAvisoDeMora();
		
		//Actualizo cantidad de cargo de aviso de mora al anio y cantidad de dias de mora.
		this.updateLinesAvisoMora();
				
		//Aplico la logica para las cuentas que tengo en la grilla, y marco con un check las que aplican aviso de mora
		this.marcarCuentasParaAplicarAvisoDeMora();	
		
			
		return "Ok";
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 13/11/2013. ISSUE#
	 * Método que borra los registros de la UY_Fdu_MassiveAdjustLine que contengan el UY_Fdu_MassiveAdjustment_ID asociado a este proceso
	 * 
	 * */
	private void borrarInstanciasViejas(){		
		
		try{
			DB.executeUpdateEx("DELETE FROM UY_Fdu_MassiveAdjustLine WHERE UY_Fdu_MassiveAdjustment_ID = " + this.mFduMassiveAdjustment.get_ID(), null);		
			
		}catch (Exception e){
			throw new AdempiereException (e);		
		}	
	}
	
	
	
	/**
	 * OpenUp. Guillermo Brust. 21/10/2013. ISSUE #
	 * Método que obtiene los datos de las cuentas que se procesan para realizar el ajuste por aviso de mora
	 * 
	 * 
	 * ***/
	 private void obtenerCuentasAvisoDeMora(){

		 //SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		 Timestamp today = new Timestamp(System.currentTimeMillis());

		 Connection con = null;
		 ResultSet rs = null;

		 try {

			 con = new ItalcredSystem().getConnectionToSqlServer(1000010);
			 Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			 /*
			 String sql = "SELECT DISTINCT saldos.SNroCta as Cuenta, socios.CliCod as Cedula, socios.STNroTarj as Tarjeta, CASE WHEN socios.PTCod = '72' THEN '00072' ELSE '00001' END as Producto, '0' + SUBSTRING(RTRIM(socios.GAFCod), 1, 4) as Afinidad, socios.MLCod as ModeloLiquidacion," + 
					 	  " saldos.SFecCAnt as CierreAnterior, saldos.SVePMAn as VencimientoAnterior, pagos.PgoFecha as FechaPago," +
					 	  " (select sal.SSdoToCP" +
					 	  "  from FinancialPro.dbo.SALDOCTA sal" +
					 	  "  where sal.SFecCierre = (select s.SFecCAnt" + 
					 	  "                          from FinancialPro.dbo.SALDOCTA s" + 
					 	  "                          where s.SNroCta = saldos.SNroCta" + 
					 	  "  						 and s.SFecCierre = '" + df.format(this.fechaCierre) + "')" +					 	  
					 	  "  and sal.SNroCta = saldos.SNroCta) as SaldoAnterior," +
					 	  " (select case when sal.SDbCrSCP = '1' then '+' else '-' end" +
					 	  "  from FinancialPro.dbo.SALDOCTA sal" +
					 	  "  where sal.SFecCierre = (select s.SFecCAnt" + 
					 	  " 						 from FinancialPro.dbo.SALDOCTA s" + 
					 	  " 						 where s.SNroCta = saldos.SNroCta" + 
					 	  " 						 and s.SFecCierre = '" + df.format(this.fechaCierre) + "')" +
					 	  " and sal.SNroCta = saldos.SNroCta) as SignoSaldo," +
					 	  " (select sal.SPgoMiAct" +
					 	  " from FinancialPro.dbo.SALDOCTA sal" +
					 	  " where sal.SFecCierre = (select s.SFecCAnt" + 
					 	  "                         from FinancialPro.dbo.SALDOCTA s" + 
					 	  " 						where s.SNroCta = saldos.SNroCta" + 
					 	  " 						and s.SFecCierre = '" + df.format(this.fechaCierre) + "')" +
					 	  " and sal.SNroCta = saldos.SNroCta) as PagoMinimoAnterior," +					
					 	  " pagos.PgoImport as ImportePago, pagos.MndCod as MonedaPago, saldos.ScotAnt as CotizacionDolar" +
					 	  " FROM FinancialPro.dbo.SALDOCTA saldos" +
					 	  " INNER JOIN FinancialPro.dbo.q_clientes_adempiere socios on  salPFduAdjustmentCallFdudos.SNroCta = socios.STNroCta and socios.STDeriNro = '0'" +
					 	  " LEFT JOIN FinancialPro.dbo.PGOTARJ pagos on saldos.SNroCta = pagos.PgoNroCta and pagos.PgoFecCie = '" + df.format(this.fechaCierre) + "'" +
					 	  " WHERE saldos.SFecCierre = '" + df.format(this.fechaCierre) + "'" +
					 	  " AND saldos.SVePMAn <> '1753-01-01 00:00:00'" +
					 	  " order by saldos.SNroCta";
			 */
			 /*
			 String sql = "SELECT DISTINCT saldos.SNroCta as Cuenta, socios.CliCod as Cedula, socios.STNroTarj as Tarjeta, CASE WHEN socios.PTCod = '72' THEN '00072' ELSE '00001' END as Producto," + 
					 	  //" '0' + SUBSTRING(RTRIM(socios.GAFCod), 1, 4) as Afinidad, " +
					 	 " CASE WHEN socios.GAFCod = '0' THEN '00000' " +
					 	 "		WHEN socios.GAFCod < 100000 THEN '0' + CAST(socios.gafcod/10 as CHAR(5)) " +
					 	 "		WHEN socios.GAFCod >= 100000 THEN 'CAST(socios.gafcod/10 as CHAR(5)) END as Afinidad," +
					 	 // " CASE WHEN socios.GAFCod = '0' THEN '00000' ELSE '0' + SUBSTRING(RTRIM(socios.GAFCod), 1, 4) END as Afinidad," +
					 	  " socios.MLCod as ModeloLiquidacion," + 
					 	  " saldos.SFecCierre as CierreAnterior, saldos.SVePMAc as VencimientoAnterior, pagos.PgoFecha as FechaPago," + 
					 	  " saldos.SSdoToCP as SaldoAnterior, case when saldos.SDbCrSCP = '1' then '+' else '-' end as SignoSaldo, saldos.SPgoMiAct as PagoMinimoAnterior," +			
					 	  " pagos.PgoImport as ImportePago, pagos.MndCod as MonedaPago, saldos.ScotAct as CotizacionDolar" +
					 	  " FROM FinancialPro.dbo.SALDOCTA saldos" +
					 	  " INNER JOIN FinancialPro.dbo.q_clientes_adempiere socios on  saldos.SNroCta = socios.STNroCta and socios.STDeriNro = '0'" +
					 	  " LEFT JOIN FinancialPro.dbo.PGOTARJ pagos on saldos.SNroCta = pagos.PgoNroCta and pagos.PgoFecCie > saldos.SFecCierre" +
					 	  " WHERE saldos.SFecCierre = (SELECT MAX(e.fechaCierre)" +
						  "							   FROM FPOP.op_control.VUY_Fdu_Expiration e" + 
						  " 						   WHERE e.fechaCierre < '" + this.fechaCierre + "'" +
						  " 						   AND e.grupocc = (SELECT grupocc FROM FPOP.op_control.VUY_Fdu_Expiration WHERE fechaCierre = '" + this.fechaCierre + "'))" +
					      //" AND saldos.SVePMAn <> '1753-01-01 00:00:00'" +
						  " AND saldos.SDbCrSCP = '1'" +
					      " ORDER BY saldos.SNroCta";
					      */
			 //OpenUp. Sylvie Bouissa 22/10/2014 #Issue 2501
			 // se cambia la condicion en la otención de Afinidad ya que ahora puede haber afinidades 1xxxxx y 
			 // no es valido agregar un cero adelante sino quedarnos con los primeros 5 digitos (menos elúltimo)
			 String sql = "SELECT DISTINCT saldos.SNroCta as Cuenta, socios.CliCod as Cedula, socios.STNroTarj as Tarjeta, CASE WHEN socios.PTCod = '72' THEN '00072' ELSE '00001' END as Producto," + 
				 	  //" '0' + SUBSTRING(RTRIM(socios.GAFCod), 1, 4) as Afinidad, " +
				 	  " CASE WHEN socios.GAFCod = '0' THEN '00000' WHEN socios.GAFCod < 100000 THEN '0' + SUBSTRING(RTRIM(socios.GAFCod), 1, 4)" +
				 	  "  ELSE SUBSTRING(RTRIM(socios.GAFCod), 1, 5) END as Afinidad," +
				 	  " socios.MLCod as ModeloLiquidacion," + 
				 	  " saldos.SFecCierre as CierreAnterior, saldos.SVePMAc as VencimientoAnterior, pagos.PgoFecha as FechaPago," + 
				 	  " saldos.SSdoToCP as SaldoAnterior, case when saldos.SDbCrSCP = '1' then '+' else '-' end as SignoSaldo, saldos.SPgoMiAct as PagoMinimoAnterior," +			
				 	  " pagos.PgoImport as ImportePago, pagos.MndCod as MonedaPago, saldos.ScotAct as CotizacionDolar" +
				 	  " FROM FinancialPro.dbo.SALDOCTA saldos" +
				 	  " INNER JOIN FinancialPro.dbo.q_clientes_adempiere socios on  saldos.SNroCta = socios.STNroCta and socios.STDeriNro = '0'" +
				 	  " LEFT JOIN FinancialPro.dbo.PGOTARJ pagos on saldos.SNroCta = pagos.PgoNroCta and pagos.PgoFecCie > saldos.SFecCierre" +
				 	  " WHERE saldos.SFecCierre = (SELECT MAX(e.fechaCierre)" +
					  "							   FROM FPOP.op_control.VUY_Fdu_Expiration e" + 
					  " 						   WHERE e.fechaCierre < '" + this.fechaCierre + "'" +
					  " 						   AND e.grupocc = (SELECT grupocc FROM FPOP.op_control.VUY_Fdu_Expiration WHERE fechaCierre = '" + this.fechaCierre + "'))" +
				      //" AND saldos.SVePMAn <> '1753-01-01 00:00:00'" +
					  " AND saldos.SDbCrSCP = '1'" +
				      " ORDER BY saldos.SNroCta";

			 rs = stmt.executeQuery(sql);

			 while (rs.next()) {
				 			 
				 MFduCargoImporte cargoImporte = null;
				 
				 int fduProductosID = DB.getSQLValue(null, " select UY_Fdu_Productos_ID from UY_Fdu_Productos " +
														   " where value='" + rs.getString("Producto") + "'" +
														   " and UY_FduFile_ID= 1000008");	

				 int fduAfinidadID = DB.getSQLValue(null, " select UY_Fdu_Afinidad_ID from UY_Fdu_Afinidad " +
														  " where value='" + rs.getString("Afinidad") + "'" +
														  " and UY_Fdu_Productos_ID = " + fduProductosID +
														  " and UY_FduFile_ID= 1000008");
				 
				 //El cargo que se pasa por parametro es el que se carga en el prepare, donde existe un metodo en la MduCargo que devuelve el modelo de esta tabla tal que tenga la el UY_FduCod_ID del ajuste que se esta lanzando y que tenga una validez menor a la fecha que se esta tirando el proceso.
				 if(fduProductosID > 0 && fduAfinidadID > 0){
					 cargoImporte = MFduCargoImporte.forCargoProductoAfinidad(this.getCtx(), this.get_TrxName(), this.cargo.get_ID(), fduProductosID, fduAfinidadID);					 
				 }else {
					 throw new AdempiereException("Producto o Afinidad no definido en Adempiere.- " + rs.getString("Producto") + " - " + rs.getString("Afinidad") + " - Cuenta " + rs.getString("Cuenta"));
				 }	
				 
				 
				 MFduModeloLiquidacion modelo = MFduModeloLiquidacion.getMFduModeloLiquidacionForValue(this.getCtx(), null, rs.getString("ModeloLiquidacion"));
				 int modeloLiquidacionID = 0;
				 if(modelo != null && modelo.get_ID() > 0){
					 modeloLiquidacionID = modelo.get_ID();				 
				 }else {
					 throw new AdempiereException("Modelo de luquidacion no definido en Adempiere ó no definido en vista FinancialPro.dbo.q_clientes_adempiere.- " + rs.getString("ModeloLiquidacion") + " - Cuenta " + rs.getString("Cuenta"));
				 }
				 
				 
				 MFduCod codFdu = new MFduCod(getCtx(), this.cargo.getUY_FduCod_ID(), null);
				 
				 String fechaPago = "";
				 if(rs.getTimestamp("FechaPago") != null) fechaPago = "'" + rs.getTimestamp("FechaPago") + "'";
				 else fechaPago = "null";
				 
				 String pagoImporte = "";
				 if(rs.getBigDecimal("ImportePago") != null) pagoImporte = rs.getBigDecimal("ImportePago").toString();
				 else pagoImporte = "null";
				 
				 String monedaPago = "";
				 if(rs.getString("MonedaPago") != null) {
					 if(rs.getString("MonedaPago").equals("0")) monedaPago = "142";
					 else monedaPago = "100";
				 }else monedaPago = "null";				 
				 
				 String montoCargo = "";
				 if(cargoImporte != null) montoCargo = cargoImporte.getAmount().toString();
				 else montoCargo = "null";
				
				 String cotizacion = "";
				 if(rs.getString("MonedaPago") != null){
					 if(!rs.getString("MonedaPago").equals("0")) cotizacion = rs.getBigDecimal("CotizacionDolar").toString();
					 else cotizacion = "null";
				 }else cotizacion = "null";
				 
				 
				 
				 String action = "INSERT INTO uy_fdu_massiveadjustline(uy_fdu_massiveadjustline_id, ad_client_id, ad_org_id, isactive," +
						 		 " created, createdby, updated, updatedby," +
						 			" uy_fdu_massiveadjustment_id, accountno, uy_fdu_productos_id, uy_fdu_afinidad_id, uy_fdu_modeloliquidacion_id, cierreanterior, vencimientoanterior, fechapago," +
						 			" saldoanterior, pagominimoanterior, importepago, c_currency_id, diasatraso, moraenanio, isvalid, signosaldo, cedula, " + 
						 			"nrotarjetatitular, codigoajuste, importe, cotizacion)" +
						 			
						 			" VALUES (nextid(1001889,'N'), " + this.mFduMassiveAdjustment.getAD_Client_ID() + "," + this.mFduMassiveAdjustment.getAD_Org_ID() + ",'Y', " +
						 			"'" + today + "', " + this.mFduMassiveAdjustment.getAD_User_ID() + ", '" + today + "', " + this.mFduMassiveAdjustment.getAD_User_ID() + ", " + 
						 			this.mFduMassiveAdjustment.get_ID() + ",'" + rs.getString("Cuenta") + "', " + fduProductosID + ", " + fduAfinidadID + ", " + modeloLiquidacionID + ", '" + rs.getTimestamp("CierreAnterior") + "','" + rs.getTimestamp("VencimientoAnterior") + "'," + fechaPago + "," +
						 			rs.getBigDecimal("SaldoAnterior") + ", " + rs.getBigDecimal("PagoMinimoAnterior") + ", " + pagoImporte + ", " + monedaPago + ", null, null, 'N', '" + rs.getString("SignoSaldo") + "', '" +  rs.getString("Cedula") + "', '" +
						 			rs.getString("Tarjeta") + "', '" + codFdu.getOperationCode() + "', " + montoCargo + ", " + cotizacion + ")";
						 			
				 
				 
				 DB.executeUpdateEx(action, this.get_TrxName());
				 
				 /*

				 //Creo una linea por cada linea obtenida del resulset
				 MFduMassiveAdjustLine line = new MFduMassiveAdjustLine(this.getCtx(), 0, this.get_TrxName());
				 
				 //Le asigno el id del padre
				 line.setUY_Fdu_MassiveAdjustment_ID(this.mFduMassiveAdjustment.get_ID());
				 
				 //Seteo los campos restantes 
				 line.setAccountNo(rs.getString("Cuenta"));
				 line.setCedula(rs.getString("Cedula"));
				 line.setNroTarjetaTitular(rs.getString("Tarjeta"));	
				 MFduProductos producto = MFduProductos.getMFduProductosForValue(this.getCtx(), rs.getString("Producto"));
				 if(producto != null) line.setUY_Fdu_Productos_ID(producto.get_ID());
				 MFduAfinidad afinidad = MFduAfinidad.getMFduAfinidadForValue(this.getCtx(), rs.getString("Afinidad"));
				 if(afinidad != null) line.setUY_Fdu_Afinidad_ID(afinidad.get_ID());
				 int modeloLiquidacionID = MFduModeloLiquidacion.getMFduModeloLiquidacionForValue(this.getCtx(), this.get_TrxName(), rs.getString("ModeloLiquidacion")).get_ID();
				 line.setUY_Fdu_ModeloLiquidacion_ID(modeloLiquidacionID);
				 line.setcierreanterior(rs.getTimestamp("CierreAnterior"));
				 line.setVencimientoAnterior(rs.getTimestamp("VencimientoAnterior"));
				 if(rs.getTimestamp("FechaPago") != null) line.setfechapago(rs.getTimestamp("FechaPago"));
				 line.setSaldoAnterior(rs.getBigDecimal("SaldoAnterior"));
				 line.setSignoSaldo(rs.getString("SignoSaldo"));				 
				 line.setPagoMinimoAnterior(rs.getBigDecimal("PagoMinimoAnterior"));
				 if(rs.getBigDecimal("ImportePago") != null) line.setImportePago(rs.getBigDecimal("ImportePago"));
				 if(rs.getString("MonedaPago") != null) {
					 int currencyID = rs.getString("MonedaPago").equals("0") ? 142 : 100;
					 line.setC_Currency_ID(currencyID);
					 
					 if(line.getC_Currency_ID() == 100){
						 line.setCotizacion(rs.getBigDecimal("CotizacionDolar"));				 
					 }
				 }	
				 
				 if(producto != null && afinidad != null){
					 MFduCargoImporte cargoImporte = MFduCargoImporte.forCargoProductoAfinidad(this.getCtx(), this.get_TrxName(), this.cargo.get_ID(), producto.get_ID(), afinidad.get_ID());
					 if(cargoImporte != null) line.setImporte(cargoImporte.getAmount());
				 }				 
				 
				 MFduCod codFdu = new MFduCod(getCtx(), this.cargo.getUY_FduCod_ID(), this.get_TrxName());
				 line.setCodigoAjuste(codFdu.getOperationCode());				 
				 
				 line.saveEx();
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
	 
	 
	 /**
	 * OpenUp. Guillermo Brust. 25/10/2013. ISSUE #
	 * Método que modifica las lineas cargadas en la MFduMassiveAdjustLine
	 * 
	 * 
	 * ***/
	 private void updateLinesAvisoMora(){

		 SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		 Connection con = null;
		 ResultSet rs = null;

		 List<MFduMassiveAdjustLine> lines = this.mFduMassiveAdjustment.getLines(this.get_TrxName());

		 try {
			 //Le paso el id de la conexion que tiene la base FPOP y el schema op_control ya que utilizo una vista que esta en este schema
			 con = new ItalcredSystem().getConnectionToSqlServer(1000010);
			 Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			 for (MFduMassiveAdjustLine line : lines) {
				 
				 int debito = 0;
				 int credito = 0;

				 //Primero avisos al anio (debitos)
				 String sql = "SELECT COUNT(a.FchCie) AS AvisosAlAnio" +
						 " FROM FDU.dbo.T_FDU_CC108D_DETALLE_A a" +
						 " WHERE a.CanPesConAju = '" + new MFduCod(this.getCtx(), this.cargo.getUY_FduCod_ID(), this.get_TrxName()).getValue() + "'" +
						 " AND a.FchCie in (select e.fechaCierre from FPOP.op_control.VUY_Fdu_Expiration e where e.grupocc COLLATE MODERN_SPANISH_CI_AS = a.Gru COLLATE MODERN_SPANISH_CI_AS and e.anio = '" + this.mFduMassiveAdjustment.getUY_Fdu_LogisticMonthDates().getUY_Fdu_LogisticMonth().getC_Year().getFiscalYear() + "')" +
						 " AND a.FchCie < '" + df.format(this.fechaCierre) + "'" +
						 " AND CAST(a.NroCue AS INT) = '" + line.getAccountNo() + "'" +
						 " AND a.Sig = '+'";

				 rs = stmt.executeQuery(sql);

				 if (rs.next()) {					 
					 debito = rs.getInt("AvisosAlAnio");
					 DB.executeUpdateEx("UPDATE UY_Fdu_MassiveAdjustLine SET QtyDebito = " + debito + " WHERE UY_Fdu_MassiveAdjustLine_ID = " + line.get_ID(), this.get_TrxName());
				 }
				 
				 
				//Luego avisos al anio (creditos)
				 sql = "SELECT COUNT(a.FchCie) AS AvisosAlAnio" +
						 " FROM FDU.dbo.T_FDU_CC108D_DETALLE_A a" +
						 " WHERE a.CanPesConAju = '" + new MFduCod(this.getCtx(), this.cargo.getUY_FduCod_ID(), this.get_TrxName()).getValue() + "'" +
						 " AND a.FchCie in (select e.fechaCierre from FPOP.op_control.VUY_Fdu_Expiration e where e.grupocc COLLATE MODERN_SPANISH_CI_AS = a.Gru COLLATE MODERN_SPANISH_CI_AS and e.anio = '" + this.mFduMassiveAdjustment.getUY_Fdu_LogisticMonthDates().getUY_Fdu_LogisticMonth().getC_Year().getFiscalYear() + "')" +
						 " AND a.FchCie < '" + df.format(this.fechaCierre) + "'" +
						 " AND CAST(a.NroCue AS INT) = '" + line.getAccountNo() + "'" + 
						 " AND a.Sig = '-'";

				 rs = stmt.executeQuery(sql);

				 if (rs.next()) {					 
					 credito = rs.getInt("AvisosAlAnio");
					 DB.executeUpdateEx("UPDATE UY_Fdu_MassiveAdjustLine SET QtyCredito = " + credito + " WHERE UY_Fdu_MassiveAdjustLine_ID = " + line.get_ID(), this.get_TrxName());
				 }

				 
				 //Por ultimo el neto
				 DB.executeUpdateEx("UPDATE UY_Fdu_MassiveAdjustLine SET MoraEnAnio = " + (debito - credito) + " WHERE UY_Fdu_MassiveAdjustLine_ID = " + line.get_ID(), this.get_TrxName());

				 //Despues dias de atraso
				 //OpenUp. Guillermo Brust. 03/02/2014. ISSUE #1851
				 //Se tiene que tomar los dias de atraso para la fecha en que se está tirando el proceso, si no trae nada la consulta es porque tiene 0 días
				 /*Codigo viejo 03/02/2014
				 sql = "SELECT COALESCE((SELECT distinct atrasos.dias as DiasMora" +
						 "                 FROM FinancialPro.dbo.ATRASOS_HIST atrasos" + 
						 "                 WHERE atrasos.Fecha = (select MAX(h.Fecha) from FinancialPro.dbo.ATRASOS_HIST h where h.NroCta = atrasos.NroCta and h.Fecha < '" + df.format(this.fechaCierre) + "')" +
						 "                 AND atrasos.NroCta = '" + line.getAccountNo() + "'), 0) as DiasMora";
				 */
				 
				 sql = "SELECT COALESCE((SELECT distinct atrasos.dias as DiasMora" +
						 "                 FROM FinancialPro.dbo.ATRASOS_HIST atrasos" + 
						 "                 WHERE atrasos.Fecha = '" + df.format(this.fechaCorrida) + "'" +
						 "                 AND atrasos.NroCta = '" + line.getAccountNo() + "'), 0) as DiasMora";
				 
				 //Fin OpenUp. ISSUE #1851

				 rs = stmt.executeQuery(sql);

				 if (rs.next()) {					 
					 //line.setDiasAtraso(rs.getInt("DiasMora"));
					 DB.executeUpdateEx("UPDATE UY_Fdu_MassiveAdjustLine SET DiasAtraso = " + rs.getInt("DiasMora") + " WHERE UY_Fdu_MassiveAdjustLine_ID = " + line.get_ID(), this.get_TrxName());
				 }else {
					 //line.setMoraEnAnio(0);
					 DB.executeUpdateEx("UPDATE UY_Fdu_MassiveAdjustLine SET DiasAtraso = 0 WHERE UY_Fdu_MassiveAdjustLine_ID = " + line.get_ID(), this.get_TrxName());
				 }

				 //line.saveEx();
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
	 
	 /*
	 private void v(){
		 List<MFduMassiveAdjustLine> lines = this.mFduMassiveAdjustment.getLines(this.get_TrxName());
		 
		
		 for (MFduMassiveAdjustLine line : lines) {		
			 
			 line.setIsValid(false);
			 line.saveEx();
		 }
	 }
	 */
	 
	 
	 /**
	 * OpenUp. Guillermo Brust. 5/11/2013. ISSUE #
	 * Método que marca para aplicar a aquellas cuentas que segun la logica aplicada son factibles para aplicar. 
	 * 
	 **/
	 /*
	 private void marcarCuentasParaAplicarAvisoDeMora(){

		 List<MFduMassiveAdjustLine> lines = this.mFduMassiveAdjustment.getLines(this.get_TrxName());
		 
		 MFduMassiveAdjustLine lineAnterior = null;		 
		 ArrayList<FduPagosRealizado> pagos = new ArrayList<FduPagosRealizado>();		 	 

		 for (MFduMassiveAdjustLine line : lines) {	
			 
			 //Verifico que la cuenta tenga un IT valido para aplicar el ajuste
			 MFduCargoModelo cargoModelo = MFduCargoModelo.forCargoAndModelo(this.getCtx(), this.get_TrxName(), this.cargo.get_ID(), line.getUY_Fdu_ModeloLiquidacion_ID());
			 
			 if(cargoModelo != null && cargoModelo.get_ID() > 0){
				 
				 boolean aplicaCargo = true;
				 boolean posibleMultaPorMora = false;
				 BigDecimal totalPagos = new BigDecimal(0);			 
				 
				 if(lineAnterior != null && !line.getAccountNo().equalsIgnoreCase(lineAnterior.getAccountNo())){
					 
					 //Lógica para revisar los pagos realizados, para ver si son validos para sumar al total de pagos realizados
					 for (FduPagosRealizado pagoRealizado : pagos) {
						 
						 //chequeamos que el pago se realice antes del vencimiento
						 if(pagoRealizado.getFechaPago().before(OpenUpUtils.sumaTiempo(lineAnterior.getVencimientoAnterior(), Calendar.DAY_OF_MONTH, 1))){
							 
							 	totalPagos = totalPagos.add(pagoRealizado.getImporte());					 
							
						 //verifico los pagos entre las 48 horas despues del vencimiento, ya que no aplican aviso de mora sino multa por mora	 
						 }else if(pagoRealizado.getFechaPago().after(lineAnterior.getVencimientoAnterior()) &&
								 pagoRealizado.getFechaPago().before(OpenUpUtils.sumaTiempo(lineAnterior.getVencimientoAnterior(), Calendar.DAY_OF_MONTH, 3))){
							 
							 totalPagos = totalPagos.add(pagoRealizado.getImporte());
							 posibleMultaPorMora = true;
						 }					
					 }
									 
					 //Si realizo algun pago
					 if(totalPagos.compareTo(new BigDecimal(0)) > 0){
						 
						 //Primero chequeamos cuantos avisos de mora tiene al anio, no puede tener mas del valor que se encuentra en el modelo del cargo aplicado
						 if(lineAnterior.getMoraEnAnio() >= this.cargo.getMoraEnAnio()) {						
							 aplicaCargo = false;
						 }
						 
						 //Luego chequeamos cuantos días de mora tiene, si tiene mas del valor que se encuentra en el modelo del cargo aplicado
						 if(lineAnterior.getDiasAtraso() >= this.cargo.getDiasAtraso()){					
							 aplicaCargo = false;
						 }
						 
						 //Luego chequeamos que el signo del saldo sea positivo
						 if(lineAnterior.getSignoSaldo().trim().equals("-")){						 
							 aplicaCargo = false;
						 }
						 
						 //Luego chequeamos que el saldo sea mayor a 100
						 if(lineAnterior.getSaldoAnterior().compareTo(new BigDecimal(100)) < 0) {						
							 aplicaCargo = false;				 
						 }
						 					 
						 //Por ultimo chequeamos que la diferencia del pago minimo menos el total de pagos sea mayor que cero						
						 if(lineAnterior.getPagoMinimoAnterior().subtract(totalPagos).compareTo(new BigDecimal(100)) <= 0  || (lineAnterior.getPagoMinimoAnterior().compareTo(totalPagos) == 0 && posibleMultaPorMora)){
							 aplicaCargo = false;						 
						 }					 

						 //Si pasa por todas las validaciones, entonces lo marcamos para que se le aplique aviso de mora
						 if(aplicaCargo){
							 
							 for(MFduMassiveAdjustLine lineaAplicar : this.mFduMassiveAdjustment.getLines(this.get_TrxName(), lineAnterior.getAccountNo())){
								 lineaAplicar.setIsValid(true);
								 lineaAplicar.saveEx();						 
							 }				
						 }				 					 
					 
					 }else{//Sino realiza ningun pago, y si el saldo anterior es mayor que 100 y el pago minimo es mayor que cero, y no tiene mas avisos que los permitidos y tiene menos dias de mora permiti
						 
						 if(lineAnterior.getSaldoAnterior().compareTo(new BigDecimal(100)) > 0 && 
								 lineAnterior.getPagoMinimoAnterior().compareTo(new BigDecimal(0)) > 0 &&
								 lineAnterior.getMoraEnAnio() < this.cargo.getMoraEnAnio() &&
								 lineAnterior.getDiasAtraso() < this.cargo.getDiasAtraso()){	
							 
							 for(MFduMassiveAdjustLine lineaAplicar : this.mFduMassiveAdjustment.getLines(this.get_TrxName(), lineAnterior.getAccountNo())){
								 lineaAplicar.setIsValid(true);
								 lineaAplicar.saveEx();						 
							 }							 						 
						 }
					 }				 				 
					 pagos = new ArrayList<FduPagosRealizado>();				 
				 }		

				 //Verifico pagos en linea actual y los guardo en una estructura temporal para verificarlos en la ultima linea para esta cuenta
				 if(line.getImportePago() != null) {
					 
					 if(line.getImportePago().compareTo(new BigDecimal(0)) > 0){
						 
						 FduPagosRealizado pago = new FduPagosRealizado();
						 
						 pago.setFechaPago(line.getfechapago());
						 
						 //Si el pago se hizo en dólares tengo que multiplicar el importe del pago por la cotizacion para dejarlo en pesos y sumar todo en pesos
						 if(line.getC_Currency_ID() == 100){					 
							 pago.setImporte(line.getImportePago().multiply(line.getCotizacion()).setScale(2, RoundingMode.HALF_UP));				 
						 }else {
							 pago.setImporte(line.getImportePago());	 
						 }
						 
						 pagos.add(pago);
					 }				 	 
				 }			 
				 lineAnterior = line;				 
			 }		 
		 }
	 }
	 */
	 
	 
	 /**
	 * OpenUp. Guillermo Brust. 5/11/2013. ISSUE #
	 * Método que marca para aplicar a aquellas cuentas que segun la logica aplicada son factibles para aplicar. 
	 * 
	 **/
	 private void marcarCuentasParaAplicarAvisoDeMora(){

		 List<MFduMassiveAdjustLine> lines = this.mFduMassiveAdjustment.getLines(this.get_TrxName());
		 
		 MFduMassiveAdjustLine lineAnterior = null;		 
		 ArrayList<FduPagosRealizado> pagos = new ArrayList<FduPagosRealizado>();
		 
		 BigDecimal totalPagos = new BigDecimal(0);
		 boolean aplicaCargo = true;
		 boolean posibleMultaPorMora = false;

		 for (MFduMassiveAdjustLine line : lines) {	
			 
			 //Verifico que la cuenta tenga un IT valido para aplicar el ajuste
			 MFduCargoModelo cargoModelo = MFduCargoModelo.forCargoAndModelo(this.getCtx(), this.get_TrxName(), this.cargo.get_ID(), line.getUY_Fdu_ModeloLiquidacion_ID());
			 
			 if(cargoModelo != null && cargoModelo.get_ID() > 0){
				 
				 aplicaCargo = true;
				 posibleMultaPorMora = false;
				 totalPagos = new BigDecimal(0);				 
				 
				 if(lineAnterior != null && !line.getAccountNo().equalsIgnoreCase(lineAnterior.getAccountNo())){
					 
					 //Lógica para revisar los pagos realizados, para ver si son validos para sumar al total de pagos realizados
					 for (FduPagosRealizado pagoRealizado : pagos) {
						 
						 //chequeamos que el pago se realice antes del vencimiento
						 if(pagoRealizado.getFechaPago().before(OpenUpUtils.sumaTiempo(lineAnterior.getVencimientoAnterior(), Calendar.DAY_OF_MONTH, 1))){
							 
							 	totalPagos = totalPagos.add(pagoRealizado.getImporte());					 
							
						 //verifico los pagos entre las 48 horas despues del vencimiento, ya que no aplican aviso de mora sino multa por mora	 
						 }else if(pagoRealizado.getFechaPago().after(lineAnterior.getVencimientoAnterior()) &&
								 pagoRealizado.getFechaPago().before(OpenUpUtils.sumaTiempo(lineAnterior.getVencimientoAnterior(), Calendar.DAY_OF_MONTH, 3))){
							 
							 totalPagos = totalPagos.add(pagoRealizado.getImporte());
							 posibleMultaPorMora = true;
						 }					
					 }
									 
					 //Si realizo algun pago
					 if(totalPagos.compareTo(new BigDecimal(0)) > 0){
						 
						 //Primero chequeamos cuantos avisos de mora tiene al anio, no puede tener mas del valor que se encuentra en el modelo del cargo aplicado
						 if(lineAnterior.getMoraEnAnio() >= this.cargo.getMoraEnAnio()) {						
							 aplicaCargo = false;
						 }
						 
						 //Luego chequeamos cuantos días de mora tiene, si tiene mas del valor que se encuentra en el modelo del cargo aplicado
						 if(lineAnterior.getDiasAtraso() >= this.cargo.getDiasAtraso()){					
							 aplicaCargo = false;
						 }
						 
						 //Luego chequeamos que el signo del saldo sea positivo
						 if(lineAnterior.getSignoSaldo().trim().equals("-")){						 
							 aplicaCargo = false;
						 }
						 
						 //Luego chequeamos que el saldo sea mayor a 100
						 if(lineAnterior.getSaldoAnterior().compareTo(new BigDecimal(100)) < 0) {						
							 aplicaCargo = false;				 
						 }
						 					 
						 //Por ultimo chequeamos que la diferencia del pago minimo menos el total de pagos sea mayor que cero						
						 if(lineAnterior.getPagoMinimoAnterior().subtract(totalPagos).compareTo(new BigDecimal(100)) <= 0  || (lineAnterior.getPagoMinimoAnterior().compareTo(totalPagos) == 0 && posibleMultaPorMora)){
							 aplicaCargo = false;						 
						 }					 

						 //Si pasa por todas las validaciones, entonces lo marcamos para que se le aplique aviso de mora
						 if(aplicaCargo){
							 
							 for(MFduMassiveAdjustLine lineaAplicar : this.mFduMassiveAdjustment.getLines(this.get_TrxName(), lineAnterior.getAccountNo())){
								 lineaAplicar.setIsValid(true);
								 lineaAplicar.saveEx();						 
							 }				
						 }				 					 
					 
					 }else{//Sino realiza ningun pago, y si el saldo anterior es mayor que 100 y el pago minimo es mayor que cero, y no tiene mas avisos que los permitidos y tiene menos dias de mora permiti
						 
						 if(lineAnterior.getSaldoAnterior().compareTo(new BigDecimal(100)) > 0 && 
								 lineAnterior.getPagoMinimoAnterior().compareTo(new BigDecimal(0)) > 0 &&
								 lineAnterior.getMoraEnAnio() < this.cargo.getMoraEnAnio() &&
								 lineAnterior.getDiasAtraso() < this.cargo.getDiasAtraso()){	
							 
							 for(MFduMassiveAdjustLine lineaAplicar : this.mFduMassiveAdjustment.getLines(this.get_TrxName(), lineAnterior.getAccountNo())){
								 lineaAplicar.setIsValid(true);
								 lineaAplicar.saveEx();						 
							 }							 						 
						 }
					 }				 				 
					 pagos = new ArrayList<FduPagosRealizado>();				 
				 }		

				 //Verifico pagos en linea actual y los guardo en una estructura temporal para verificarlos en la ultima linea para esta cuenta
				 if(line.getImportePago() != null) {
					 
					 if(line.getImportePago().compareTo(new BigDecimal(0)) > 0){
						 
						 FduPagosRealizado pago = new FduPagosRealizado();
						 
						 pago.setFechaPago(line.getfechapago());
						 
						 //Si el pago se hizo en dólares tengo que multiplicar el importe del pago por la cotizacion para dejarlo en pesos y sumar todo en pesos
						 if(line.getC_Currency_ID() == 100){					 
							 pago.setImporte(line.getImportePago().multiply(line.getCotizacion()).setScale(2, RoundingMode.HALF_UP));				 
						 }else {
							 pago.setImporte(line.getImportePago());	 
						 }
						 
						 pagos.add(pago);
					 }				 	 
				 }	
				 
				 lineAnterior = line;				 
			 }		 
		 }
		 
		 //OpenUp. Guillermo Brust. 03/02/2014. ISSUE #1860
		 //Se agrega codigo para procesar el último registro de la lista, ya que no se estaba procesando en el corte de control.
		 //Los pagos ya los traigo del while anterior, ya que se guardaron los pagos de la ultima linea, lo que no se hizo hasta ahora fue la logica para ver si aplica o no,
		 //porque se haria en la siguiente pasada pero no hay siguiente pasada por eso se hace esto para el último registro
		 
		 int indiceUltimoRegistro = this.mFduMassiveAdjustment.getLines(this.get_TrxName()).size() -1;		 
		 MFduMassiveAdjustLine ultimaLinea = this.mFduMassiveAdjustment.getLines(this.get_TrxName()).get(indiceUltimoRegistro);
		 
		//Verifico que la cuenta tenga un IT valido para aplicar el ajuste
		 MFduCargoModelo cargoModelo = MFduCargoModelo.forCargoAndModelo(this.getCtx(), this.get_TrxName(), this.cargo.get_ID(), ultimaLinea.getUY_Fdu_ModeloLiquidacion_ID());
		 
		 if(cargoModelo != null && cargoModelo.get_ID() > 0){
			 
			 aplicaCargo = true;
			 posibleMultaPorMora = false;
			 totalPagos = new BigDecimal(0);	

			 //Lógica para revisar los pagos realizados, para ver si son validos para sumar al total de pagos realizados
			 for (FduPagosRealizado pagoRealizado : pagos) {

				 //chequeamos que el pago se realice antes del vencimiento
				 if(pagoRealizado.getFechaPago().before(OpenUpUtils.sumaTiempo(ultimaLinea.getVencimientoAnterior(), Calendar.DAY_OF_MONTH, 1))){

					 totalPagos = totalPagos.add(pagoRealizado.getImporte());					 

					 //verifico los pagos entre las 48 horas despues del vencimiento, ya que no aplican aviso de mora sino multa por mora	 
				 }else if(pagoRealizado.getFechaPago().after(ultimaLinea.getVencimientoAnterior()) &&
						 pagoRealizado.getFechaPago().before(OpenUpUtils.sumaTiempo(ultimaLinea.getVencimientoAnterior(), Calendar.DAY_OF_MONTH, 3))){

					 totalPagos = totalPagos.add(pagoRealizado.getImporte());
					 posibleMultaPorMora = true;
				 }					
			 }

			 //Si realizo algun pago
			 if(totalPagos.compareTo(new BigDecimal(0)) > 0){

				 //Primero chequeamos cuantos avisos de mora tiene al anio, no puede tener mas del valor que se encuentra en el modelo del cargo aplicado
				 if(ultimaLinea.getMoraEnAnio() >= this.cargo.getMoraEnAnio()) {						
					 aplicaCargo = false;
				 }

				 //Luego chequeamos cuantos días de mora tiene, si tiene mas del valor que se encuentra en el modelo del cargo aplicado
				 if(ultimaLinea.getDiasAtraso() >= this.cargo.getDiasAtraso()){					
					 aplicaCargo = false;
				 }

				 //Luego chequeamos que el signo del saldo sea positivo
				 if(ultimaLinea.getSignoSaldo().trim().equals("-")){						 
					 aplicaCargo = false;
				 }

				 //Luego chequeamos que el saldo sea mayor a 100
				 if(ultimaLinea.getSaldoAnterior().compareTo(new BigDecimal(100)) < 0) {						
					 aplicaCargo = false;				 
				 }

				 //Por ultimo chequeamos que la diferencia del pago minimo menos el total de pagos sea mayor que cero						
				 if(ultimaLinea.getPagoMinimoAnterior().subtract(totalPagos).compareTo(new BigDecimal(100)) <= 0  || (ultimaLinea.getPagoMinimoAnterior().compareTo(totalPagos) == 0 && posibleMultaPorMora)){
					 aplicaCargo = false;						 
				 }					 

				 //Si pasa por todas las validaciones, entonces lo marcamos para que se le aplique aviso de mora
				 if(aplicaCargo){

					 for(MFduMassiveAdjustLine lineaAplicar : this.mFduMassiveAdjustment.getLines(this.get_TrxName(), ultimaLinea.getAccountNo())){
						 lineaAplicar.setIsValid(true);
						 lineaAplicar.saveEx();						 
					 }				
				 }				 					 

			 }else{//Sino realiza ningun pago, y si el saldo anterior es mayor que 100 y el pago minimo es mayor que cero, y no tiene mas avisos que los permitidos y tiene menos dias de mora permiti

				 if(ultimaLinea.getSaldoAnterior().compareTo(new BigDecimal(100)) > 0 && 
						 ultimaLinea.getPagoMinimoAnterior().compareTo(new BigDecimal(0)) > 0 &&
						 ultimaLinea.getMoraEnAnio() < this.cargo.getMoraEnAnio() &&
						 ultimaLinea.getDiasAtraso() < this.cargo.getDiasAtraso()){	

					 for(MFduMassiveAdjustLine lineaAplicar : this.mFduMassiveAdjustment.getLines(this.get_TrxName(), ultimaLinea.getAccountNo())){
						 lineaAplicar.setIsValid(true);
						 lineaAplicar.saveEx();						 
					 }							 						 
				 }
			 }		 
		 } 

		 //Fin OpenUp. ISSUE #1860
	 }

}
