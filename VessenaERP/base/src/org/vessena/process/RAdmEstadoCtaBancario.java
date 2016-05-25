package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class RAdmEstadoCtaBancario extends SvrProcess {

	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	private int idCuentaBancaria = 0;
	private int idTipoDocumento = 0;
	private int idEmpresa = 0;
	private int idOrganizacion = 1000005;
	private Long idUsuario = new Long(0);
	private String idReporte = "";

	private static final String TABLA_MOLDE = "UY_MOLDE_RAdmEstadoCtaBancario";
	
	public RAdmEstadoCtaBancario() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {

		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;
		// Parametro para titulo del reporte
		ProcessInfoParameter paramTituloReporte = null;
		
		// Parametros para fechas
		ProcessInfoParameter paramStartDate = null;
		ProcessInfoParameter paramEndDate = null;
		
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("idReporte")){
					paramIDReporte = para[i]; 
				}
				if (name.equalsIgnoreCase("StartDate")){
					paramStartDate = para[i]; 
				}
				if (name.equalsIgnoreCase("EndDate")){
					paramEndDate = para[i]; 
				}
				if (name.equalsIgnoreCase("tituloReporte")){
					paramTituloReporte = para[i];
				}
				if (name.equalsIgnoreCase("C_Period_ID")){
					this.fechaDesde = (Timestamp)para[i].getParameter();
					this.fechaHasta = (Timestamp)para[i].getParameter_To();
					para[i].setParameter(this.fechaDesde);
					para[i].setParameter_To(this.fechaHasta);
				}
				/*if (name.equalsIgnoreCase("usuarioReporte")){
					this.nombreUsuario = (String)para[i].getParameter();
				}*/
				if (name.equalsIgnoreCase("AD_User_ID")){
					this.idUsuario = ((BigDecimal)para[i].getParameter()).longValueExact();
				}
				if (name.equalsIgnoreCase("C_DocType_ID")){
					if (para[i].getParameter()!=null)
						this.idTipoDocumento = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.idEmpresa = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("uy_c_bankaccount_propio_id")){
					this.idCuentaBancaria = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Org_ID")){
					this.idOrganizacion = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(this.idUsuario);
		
		// Seteo titulo del reporte segun tipo cta corriente
		String tituloReporte = "Estado de Cuenta Bancario";		
		// Si tengo parametro para titulo de reporte
		if (paramTituloReporte!=null) paramTituloReporte.setParameter(tituloReporte);

		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);

		// Si tengo parametros de fechas para mostrar en el reporte
		if (paramStartDate!=null) paramStartDate.setParameter(this.fechaDesde);
		if (paramEndDate!=null) paramEndDate.setParameter(this.fechaHasta);
	}

	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.
		this.loadMovimientos();
		
		// Calculo saldos 
		this.calculoSaldos();
		
		// Me aseguro de no mostrar registros con cantidad de movimiento en cero
		this.deleteBasuraTemporal();
		
		return "ok";
		
	}

	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE idusuario =" + this.idUsuario;
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}

	/* Elimino registros de tabla temporal con entradas y salidas en cero. No deberia haber ningun registro pero me cubro de errores. */
	private void deleteBasuraTemporal(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND debe=0 " +
				  " AND haber=0 ";
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	/* Carga movimientos de stock en tabla molde*/
	private void loadMovimientos(){
	
		String insert ="";
		String sql = "";
		String where = "";

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (idReporte, idUsuario, fecReporte, fecDoc, " + 
					"idDoc, docbasetype, tipoDoc, nroDoc, " +
					"saldoinicial, debe, haber, saldoacumulado, uy_movbancarioshdr_id,isHdr) " ;
			
			// Filtros
			where = " WHERE v.AD_Client_ID =" + this.idEmpresa + 
					" AND v.AD_Org_ID =" + this.idOrganizacion +
					" AND v.datetrx between '" + this.fechaDesde + "' AND '" + this.fechaHasta + "'" +
					" AND v.c_bankaccount_id=" + this.idCuentaBancaria;
			
			// Tipo de documento
			if (idTipoDocumento>0) where = where + " AND v.c_doctype_id=" + idTipoDocumento + " ";
			
			sql = "SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date," +
			"v.datetrx, v.c_doctype_id, v.docbasetype, v.printname, v.documentno, 0, v.debe, v.haber, 0, v.uy_movbancarioshdr_id, v.ishdr " +
			" FROM vuy_estadoctabancario v " + where +
			" ORDER BY v.datetrx, v.c_doctype_id , v.documentno ";
			
			log.info(insert + sql);
			
			DB.executeUpdate(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
		}
	}

	/* Calcula saldos inciales y acumulados y actualiza la temporal */
	private void calculoSaldos(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			BigDecimal saldoAcumulado = new BigDecimal(0);
			BigDecimal saldoInicial = new BigDecimal(0);

			// Obtengo saldo inicial para la cuenta bancaria seleccionada por el usuario
			saldoInicial = this.getSaldoInicialDebe().subtract(this.getSaldoInicialHaber());
			// Actualizo saldo inicial que es igual para todos los registros de la temporal
			this.updateSaldoInicial(saldoInicial);
			// Acumulado es igual al inicial al empezar
			saldoAcumulado = saldoInicial;					
			
			// Obtengo registros de la temporal ordenados por fecha-documento
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY fecdoc, iddoc, nrodoc ASC";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();

			// Recorro registros para ir actualizando el saldo acumulado
			while (rs.next()){
				
				// Acumulo saldo y actualizo en tabla
				if (rs.getBigDecimal("debe").compareTo(new BigDecimal(0))>0)
					saldoAcumulado = saldoAcumulado.add(rs.getBigDecimal("debe"));
				if (rs.getBigDecimal("haber").compareTo(new BigDecimal(0))>0)
					saldoAcumulado = saldoAcumulado.subtract(rs.getBigDecimal("haber"));
				this.updateSaldoAcumulado(rs.getBigDecimal("uy_movbancarioshdr_id").intValueExact(),rs.getString("isHdr"), saldoAcumulado);
			}
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	/* Obtengo saldo inicial de debitos para cuenta bancaria seleccionada */
	private BigDecimal getSaldoInicialDebe() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
			sql ="SELECT COALESCE(SUM(v.debe),0) as saldo " + 
 		  	" FROM vuy_estadoctabancario v " +
		  	" WHERE v.AD_Client_ID =" + this.idEmpresa + 
			" AND v.AD_Org_ID =" + this.idOrganizacion +
		  	" AND v.datetrx <? " +
		  	" AND v.c_bankaccount_id=" + this.idCuentaBancaria;
			
			  //FIN OPENUP
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setTimestamp(1, this.fechaDesde);			
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value = rs.getBigDecimal("saldo");
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}
	
	/* Obtengo saldo inicial de creditos para cuenta bancaria seleccionada */
	private BigDecimal getSaldoInicialHaber() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
			sql ="SELECT COALESCE(SUM(v.haber),0) as saldo " + 
		 		  	" FROM vuy_estadoctabancario v " +
				  	" WHERE v.AD_Client_ID =" + this.idEmpresa + 
					" AND v.AD_Org_ID =" + this.idOrganizacion +
				  	" AND v.datetrx <? " +
				  	" AND v.c_bankaccount_id=" + this.idCuentaBancaria;

			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setTimestamp(1, this.fechaDesde);			
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value = rs.getBigDecimal("saldo");
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}
	
	private void updateSaldoInicial(BigDecimal valor){

		String sql = "";
		
		try{
			sql = "UPDATE " + TABLA_MOLDE + 
				  " SET saldoinicial = " + valor;
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	private void updateSaldoAcumulado(int idMovBancariosHdr,String isHdr, BigDecimal valor){

		String sql = "";
		
		try{
			sql = "UPDATE " + TABLA_MOLDE + 
			  " SET saldoacumulado = " + valor +
			  " WHERE uy_movbancarioshdr_id = " + idMovBancariosHdr+ " and isHdr='" +isHdr+"'" ; 
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}	
	
}
