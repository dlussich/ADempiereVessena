package org.openup.process;

import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MElementValue;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class RCtbBalanceteContableLogica {
	
private Timestamp fechaHasta = null;
	
	private int idCuentaDesde = 0;
	private int idCuentaHasta = 0;
	
	private int idEmpresa = 0;
	private int idOrganizacion = 0;
	private Long idUsuario = new Long(0);
	private String idReporte = "";
	private CLogger log=null;
	
	
	private static final String TABLA_MOLDE = "UY_MOLDE_RCtbBalanceteContable";
	
	public RCtbBalanceteContableLogica (CLogger log,int idCuentaDesde,int idCuentaHasta,int idEmpresa, int idOrganizacion, Long idUsuario, String idReporte,Timestamp fechaHasta){
											
		this.log=log;
		this.idCuentaDesde = idCuentaDesde;
		this.idCuentaHasta = idCuentaHasta;
		this.idEmpresa = idEmpresa;
		this.idOrganizacion = idOrganizacion;
		this.fechaHasta=fechaHasta;
		this.idUsuario = idUsuario;
		this.idReporte = idReporte;
	}
	
	public String loadModelTable(){
		String salida="";
		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.
		salida=this.loadMovimientos();
		
		// Me aseguro de no mostrar registros con importes en cero
		this.deleteBasuraTemporal();
		return salida;
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
	
	/* Elimino registros de tabla temporal con saldos en cero. */
	private void deleteBasuraTemporal(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND saldomt=0 " +
				  " AND saldomn=0 " +
				  " AND uy_saldomesmt = 0 " +
				  " AND uy_saldomesmn = 0 ";
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : Carga movimientos de cuentas en la tabla molde.
	 * @author  Gabriel Vila 
	 * Fecha : 22/09/2010
	 */
	private String loadMovimientos(){
		
		String insert = "", sql = "";
		String whereCuentas = "";

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, idusuario,ad_client_id,ad_org_id, idcuenta,C_ElementValue_ID , valorcuenta, nombrecuenta, fecreporte, " +
					"  saldomt, saldomn) ";// OpenUp M.R. 20-07-2011 Issue#798 No se cargan las monedas de las cuentas, para poder agruparlas en las sumas
			
			if (this.idCuentaDesde > 0){
				MElementValue ctaDesde = new MElementValue(Env.getCtx(), this.idCuentaDesde, null);
				whereCuentas = " AND  cta.value>='"+ctaDesde.getValue()+"'";
			}
			if (this.idCuentaHasta> 0){			
				MElementValue ctaHasta = new MElementValue(Env.getCtx(), this.idCuentaHasta, null);
				whereCuentas += " AND cta.value<='"+ctaHasta.getValue()+"'";
			}
			
			int difCambioTableID = DB.getSQLValueEx(null, "select ad_table_id from ad_table where lower(tablename) = 'uy_exchangediffhdr'");
			
			/*
			sql = "SELECT '" + this.idReporte + "'," + this.idUsuario + ","+this.idEmpresa+","+this.idOrganizacion+", a.account_id,a.account_id, cta.value, cta.name,'"+this.fechaHasta+"', " +
			    //" a.c_currency_id, " +, a.uy_accnat_currency_id," + //OpenUp M.R. 20-07-2011 Issue#798 Comento lineas de monedas que no necesito
				  " COALESCE(SUM(a.uy_amtnativedr - a.uy_amtnativecr),0) as saldomt, " +
				  " COALESCE(SUM(a.amtacctdr - a.amtacctcr),0) as saldomn " +
				  " FROM fact_acct a " + 
				  " INNER JOIN c_elementvalue cta ON a.account_id = cta.c_elementvalue_id " +
				  " WHERE a.ad_client_id =" + this.idEmpresa +
				  " AND a.ad_org_id =" + this.idOrganizacion +
				  " AND a.dateacct <='" + this.fechaHasta + "' " +
				  whereCuentas +
				  " GROUP BY a.account_id, cta.value, cta.name, current_date ";// OpenUp M.R. 20-07-2011 Issue#798 Saco agrupacion por tipo de moneda para poder realizar la suma de los totales de cuentas
			*/

			sql = "SELECT '" + this.idReporte + "'," + this.idUsuario + ","+this.idEmpresa+","+this.idOrganizacion+", a.account_id,a.account_id, cta.value, cta.name,'"+this.fechaHasta+"', " +
				    //" a.c_currency_id, " +, a.uy_accnat_currency_id," + //OpenUp M.R. 20-07-2011 Issue#798 Comento lineas de monedas que no necesito
				    " COALESCE(SUM( " +
				    " case when a.ad_table_id !=" + difCambioTableID + " then a.uy_amtnativedr else 0 end - " +
				    " case when a.ad_table_id !=" + difCambioTableID + " then a.uy_amtnativecr else 0 end),0) as saldomt, " +
					  " COALESCE(SUM(a.amtacctdr - a.amtacctcr),0) as saldomn " +
					  " FROM fact_acct a " + 
					  " INNER JOIN c_elementvalue cta ON a.account_id = cta.c_elementvalue_id " +
					  " WHERE a.ad_client_id =" + this.idEmpresa +
					  " AND a.ad_org_id =" + this.idOrganizacion +
					  " AND a.dateacct <='" + this.fechaHasta + "' " +
					  whereCuentas +
					  " GROUP BY a.account_id, cta.value, cta.name, current_date ";// OpenUp M.R. 20-07-2011 Issue#798 Saco agrupacion por tipo de moneda para poder realizar la suma de los totales de cuentas

			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdate(insert + sql, null);
			//OpenUp M.R. 19-07-2011 Issue #798 Hago update de la tabla molde para dejar en cero el campo Moneda extranjera cuando es igual a moneda nacional dado que si son iguales
			// es porque son en moneda nacional
			sql = "UPDATE " + TABLA_MOLDE +
			" SET saldomt = 0 "+
			" where saldomt = saldomn and idreporte = '"+ this.idReporte +"'";
			//Fin OpenUp
			DB.executeUpdate(sql, null);
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			sql = "SELECT " +
			" a.account_id, cta.value, cta.name, COALESCE(SUM(a.amtacctdr - a.amtacctcr),0) as saldomn , " +
		    " COALESCE(SUM( " +
		    " case when a.ad_table_id !=" + difCambioTableID + " then a.uy_amtnativedr else 0 end - " +
		    " case when a.ad_table_id !=" + difCambioTableID + " then a.uy_amtnativecr else 0 end),0) as saldomt " +  //OpenUp. Nicolas Sarlabos. 30/05/2013. #905
			  " FROM fact_acct a " + 
			  " INNER JOIN c_elementvalue cta ON a.account_id = cta.c_elementvalue_id " +
			  " WHERE a.ad_client_id =" + this.idEmpresa +
			  " AND a.ad_org_id =" + this.idOrganizacion +
			  " AND a.dateacct between ( SELECT date_trunc('month', timestamp '"+ this.fechaHasta +"')) AND '"+this.fechaHasta + "' " +
			  whereCuentas +
			  " GROUP BY a.account_id, cta.value, cta.name, current_date ";


			try {
				pstmt = DB.prepareStatement(sql, null); // Create the statement
				rs = pstmt.executeQuery();

				while (rs.next()) {
					//OpenUp Nicolas Sarlabos #882 28/11/2011
					DB.executeUpdateEx("UPDATE " + TABLA_MOLDE + " SET uy_saldomesmt =  coalesce(" + rs.getBigDecimal("saldomt").setScale(2,RoundingMode.HALF_UP) + ",0), uy_saldomesmn =  coalesce(" + rs.getBigDecimal("saldomn").setScale(2,RoundingMode.HALF_UP) + ",0)"
							+ " where idcuenta = " + rs.getInt("account_id") + " and idreporte = '" + this.idReporte + "'", null);
					//Fin OpenUp Nicolas Sarlabos #882 28/11/2011

				}
				//OpenUp Nicolas Sarlabos #882 11/11/2011
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;

				sql = "UPDATE " + TABLA_MOLDE +
				" SET uy_saldomesmt = 0 "+
				" where uy_saldomesmt = uy_saldomesmn and idreporte = '"+ this.idReporte +"'";
				DB.executeUpdate(sql, null);
				//fin OpenUp Nicolas Sarlabos #882 11/11/2011
				
			} 
			catch (Exception e) {
				throw e;
				
			} finally {
				
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}


 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
			return e.getMessage();
		}
		return "OK";
	}

}
