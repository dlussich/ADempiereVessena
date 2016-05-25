/**
 * RCtbMayorContable.java
 * 02/10/2010
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.InfoDocumentoBean;

/**
 * OpenUp.
 * RCtbMayorContable
 * Descripcion : Reporte Contable : Mayor Contable
 * @author Gabriel Vila
 * Fecha : 02/10/2010
 */
public class RCtbMayorContable extends SvrProcess {

	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	
	private int idCuentaDesde = 0;
	private int idCuentaHasta = 0;

	private String tipoMoneda = "";
	private boolean conSaldoInicial = true;
	
	private int idEmpresa = 0;
	private int idOrganizacion = 0;
	private Long idUsuario = new Long(0);
	//private String nombreEmpresa = "";
	//private String nombreUsuario = "";
	private String idReporte = "";

	private static final String TIPO_MONEDA_NACIONAL = "MN";
	//private static final String TIPO_MONEDA_NATIVA = "MT";
	
	private static final String TABLA_MOLDE = "UY_MOLDE_RCtbMayorContable";
	
	/**
	 * Constructor
	 */
	public RCtbMayorContable() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;
		// Parametro para titulo del reporte
		ProcessInfoParameter paramTituloReporte = null;

		// Parametro para tipo de moneda
		ProcessInfoParameter paramTipoMoneda = null;
		// Parametros para fechas
		ProcessInfoParameter paramStartDate = null;
		ProcessInfoParameter paramEndDate = null;
		// Parametro para consideracion o no de saldo inicial
		//ProcessInfoParameter paramConSaldoInicial = null;
		
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
				if (name.equalsIgnoreCase("UY_ConSaldoInicial")){
					//paramConSaldoInicial = para[i];
					this.conSaldoInicial = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
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
				if (name.equalsIgnoreCase("C_ElementValue_ID")){
					if (para[i].getParameter()!=null)
						this.idCuentaDesde = ((BigDecimal)para[i].getParameter()).intValueExact();
					if (para[i].getParameter_To()!=null)
						this.idCuentaHasta = ((BigDecimal)para[i].getParameter_To()).intValueExact();
				}
				
				if (name.equalsIgnoreCase("AD_User_ID")){
					this.idUsuario = ((BigDecimal)para[i].getParameter()).longValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.idEmpresa = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Org_ID")){
					this.idOrganizacion = ((BigDecimal)para[i].getParameter()).intValueExact();
				}				
				if (name.equalsIgnoreCase("UY_TipoMonedaContable")){
					paramTipoMoneda = para[i];
					if (para[i].getParameter()!=null)
						this.tipoMoneda = (String)para[i].getParameter();
				}
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(this.idUsuario);
		
		// Seteo titulo del reporte segun tipo cta corriente
		String tituloReporte = "Mayor Contable";
		
		// Si tengo parametro para titulo de reporte
		if (paramTituloReporte!=null) paramTituloReporte.setParameter(tituloReporte);

		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);

		// Si tengo parametros de fechas para mostrar en el reporte
		if (paramStartDate!=null) paramStartDate.setParameter(this.fechaDesde);
		if (paramEndDate!=null) paramEndDate.setParameter(this.fechaHasta);
	
		// Parametro Tipo de Moneda para mostrar en el reporte
		if (paramTipoMoneda!=null)
			paramTipoMoneda.setParameter(this.tipoMoneda);

		// Parametro Consideracion de Saldo Inicial para mostrar en el reporte
		//if (paramConSaldoInicial!=null)
			//paramConSaldoInicial.setParameter(this.tipoMoneda);
	}
	
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.
		this.loadMovimientos();
		
		// Calculo saldos 
		this.calculoSaldos();
		
		// Me aseguro de no mostrar registros con importes en cero
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

	/**
	 * OpenUp.	
	 * Descripcion : Carga movimientos de cuentas en la tabla molde.
	 * @author  Gabriel Vila 
	 * Fecha : 22/09/2010
	 */
	private void loadMovimientos(){
		
		String insert = "", sql = "";
		String whereFiltros = "", camposImportes = "";

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, idusuario, idcuenta, valorcuenta, nombrecuenta, fecreporte, fecdoc, " + 
					" iddoc, docbasetype, tipodoc, nrodoc, idasiento, descasiento, observaciones, idmonedaasiento, idmonedacuenta, " +
					" imporigen, saldoinicial, debe, haber, saldoperiodo, saldoacumulado, idsocionegocio, nombresocionegocio, idtabla, nomtabla) ";
			
			// Condicion de cuentas
			
			if (this.idCuentaDesde>0) 
				whereFiltros = " AND a.account_id>=" + this.idCuentaDesde;
			if (this.idCuentaHasta>0) 
				whereFiltros += " AND a.account_id<=" + this.idCuentaHasta;
			
			if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_NACIONAL)){
				camposImportes = "a.amtacctdr, a.amtacctcr, ";
			}				 
			else{
				camposImportes = "a.uy_amtnativedr, a.uy_amtnativecr, ";
				whereFiltros += " AND lower(tab.tablename)!='uy_exchangediffhdr' ";
			}
				
			
			
			sql = "SELECT '" + this.idReporte + "'," + this.idUsuario + ", a.account_id, cta.value, cta.name, current_date, a.dateacct, " +
				  " 0, '', '', 0, a.record_id, " +
				  "'','', a.c_currency_id, a.uy_accnat_currency_id, 0, 0, " + 
				  camposImportes +
				  " 0, 0, a.c_bpartner_id, coalesce(bp.name,'') as bpname, a.ad_table_id, tab.tablename " +
				  " FROM fact_acct a " + 
				  " INNER JOIN ad_table tab ON a.ad_table_id = tab.ad_table_id " +
				  " INNER JOIN c_elementvalue cta ON a.account_id = cta.c_elementvalue_id " +
				  " LEFT OUTER JOIN c_bpartner bp ON a.c_bpartner_id = bp.c_bpartner_id " +
				  " WHERE a.ad_client_id =" + this.idEmpresa +
				  " AND a.ad_org_id =" + this.idOrganizacion +
				  " AND a.dateacct between '" + this.fechaDesde + "' AND '" + this.fechaHasta + "' " +
				  whereFiltros; 
				  

			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdate(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
		}
	}

	/**
	 * OpenUp.	
	 * Descripcion : Calcula saldos y datos de documentos.
	 * @author  Gabriel Vila 
	 * Fecha : 03/10/2010
	 */
	private void calculoSaldos(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			BigDecimal saldoPeriodo = new BigDecimal(0);
			BigDecimal saldoAcumulado = new BigDecimal(0);
			BigDecimal saldoInicial = new BigDecimal(0);
			String valorcuenta = "";
			
			// Obtengo registros de la temporal ordenados por fecha - idAsiento - idtabla
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY valorcuenta, fecdoc, idasiento, idtabla ASC";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();

			// Recorro registros y voy actualizando
			while (rs.next()){

				// Obtengo cuenta de este registro
				String valorCuentaAux = rs.getString("valorcuenta");
				
				// Si hay cambio de cuenta contable con respecto al registro anterior
				if (!valorCuentaAux.equalsIgnoreCase(valorcuenta)){
					
					// Si el usuario selecciona opcion de reporte con saldo inicial
					if (this.conSaldoInicial){
						// Obtengo saldo inicial de esta nueva cuenta contable
						saldoInicial = this.getSaldoInicial(rs.getInt("idcuenta"));
						// Actualizo saldo inicial para este nueva cuenta
						this.updateSaldoInicial(saldoInicial, rs.getInt("idcuenta"));
					}
					else{
						saldoInicial = Env.ZERO;
					}
					// Reseto acumulado = saldo inicial 
					saldoAcumulado = saldoInicial;
					// Reseteo Saldo Periodo
					saldoPeriodo = Env.ZERO;
					// Guardo nuevo valor de cuenta en proceso
					valorcuenta = valorCuentaAux;
				}
				
				// Calculo saldo del periodo y saldo acumulado segun el asiento sea al debe o al haber
				if (rs.getBigDecimal("debe").doubleValue()!=0){
					saldoPeriodo = saldoPeriodo.add(rs.getBigDecimal("debe"));
					saldoAcumulado = saldoAcumulado.add(rs.getBigDecimal("debe"));
				}
				if (rs.getBigDecimal("haber").doubleValue()!=0){
					saldoPeriodo = saldoPeriodo.subtract(rs.getBigDecimal("haber"));
					saldoAcumulado = saldoAcumulado.subtract(rs.getBigDecimal("haber"));
				}
				
				// Obtengo datos del documento asociado a este asiento, segun el tabla y idasiento
				InfoDocumentoBean infoDoc = this.getInfoDocumento(rs.getInt("idasiento"),rs.getString("nomtabla"));
				
				// Actualizo importes y datos de documento de este registro en la temporal
				this.updateDatosRegistro(rs.getInt("idcuenta"), rs.getTimestamp("fecdoc"), rs.getInt("idasiento"), rs.getInt("idtabla"), 
										 saldoPeriodo, saldoAcumulado, infoDoc, rs.getString("nombresocionegocio"));
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


	/**
	 * OpenUp.	
	 * Descripcion : Dada una tabla y el id de un registro de la misma, obtengo informacion del documento asociado al registro.
	 * @param idRegistro
	 * @param nomTabla
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 03/10/2010
	 */
	private InfoDocumentoBean getInfoDocumento(int idRegistro, String nomTabla) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		InfoDocumentoBean value = new InfoDocumentoBean();
		
		try{
			
			if (nomTabla.equalsIgnoreCase("C_Invoice")){
				sql = " SELECT a.c_doctype_id, a.documentno, doc.docbasetype, doc.printname, coalesce(a.description,'') as observa " +
			  	" FROM " + nomTabla + " a " +
			  	" INNER JOIN C_DocType doc ON a.c_doctype_id = doc.c_doctype_id " +
			  	" WHERE a." + nomTabla + "_ID =" + idRegistro;
			}
			else if (!nomTabla.equalsIgnoreCase("C_AllocationHdr"))
				sql = " SELECT a.c_doctype_id, a.documentno, doc.docbasetype, doc.printname, '' as observa " +
				  	" FROM " + nomTabla + " a " +
				  	" INNER JOIN C_DocType doc ON a.c_doctype_id = doc.c_doctype_id " +
				  	" WHERE a." + nomTabla + "_ID =" + idRegistro;
			else
				sql = " SELECT 0 as c_doctype_id, a.documentno, 'AFE' as docbasetype, 'Afectacion Documentos' as printname, '' as observa " +
			  	" FROM " + nomTabla + " a " +
			  	" WHERE a." + nomTabla + "_ID =" + idRegistro;
				
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value.C_DocType_ID = rs.getInt("c_doctype_id");
				value.DocBaseType = rs.getString("docbasetype");
				value.PrintName = rs.getString("printname");
				value.DocumentNo = rs.getString("documentno");
				value.Observaciones = rs.getString("observa");
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

	/**
	 * OpenUp.	
	 * Descripcion : Actualiza saldo inicial para una determinada cuenta en tabla temporal.
	 * @param valor
	 * @param valorCuenta 
	 * @author  Gabriel Vila 
	 * Fecha : 03/10/2010
	 */
	private void updateSaldoInicial(BigDecimal valor, int idCuenta){

		String sql = "";
		
		try{
			sql = "UPDATE " + TABLA_MOLDE + 
				  " SET saldoinicial = " + valor +
				  " WHERE idreporte='" + this.idReporte + "'" +
				  " AND idcuenta =" + idCuenta;
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Actualiza datos de un determinado registro dado por cuenta-fecha-asiento-tabla en la tabla temporal.
	 * @param idCuenta
	 * @param fechaDoc
	 * @param idAsiento
	 * @param idTabla
	 * @param saldoPeriodo
	 * @param saldoAcumulado
	 * @param infoDoc
	 * @author  Gabriel Vila 
	 * Fecha : 03/10/2010
	 */
	private void updateDatosRegistro(int idCuenta, Timestamp fechaDoc, int idAsiento, int idTabla,
									 BigDecimal saldoPeriodo, BigDecimal saldoAcumulado, InfoDocumentoBean infoDoc, String nomSocioNeg){
		
		String sql = "";
		
		try{

			sql = "UPDATE " + TABLA_MOLDE + 
				  " SET saldoperiodo = " + saldoPeriodo + ", " +
				  " saldoacumulado =" + saldoAcumulado + ", " +
				  " descasiento ='" + infoDoc.PrintName + "-" + infoDoc.DocumentNo + "-" + nomSocioNeg + "', " +
				  " observaciones ='" + infoDoc.Observaciones + "', " +
				  " iddoc =" + infoDoc.C_DocType_ID + ", " +
				  " docbasetype ='" + infoDoc.DocBaseType + "', " +
				  " tipodoc ='" + infoDoc.PrintName + "', " +
				  " nrodoc ='" + infoDoc.DocumentNo + "' " +
				  " WHERE idreporte='" + this.idReporte + "'" +
				  " AND idcuenta =" + idCuenta + 
				  " AND fecdoc = '" + fechaDoc + "'" +
				  " AND idasiento =" + idAsiento + 
				  " AND idtabla =" + idTabla;
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de asientos contables a determinado fecha para una determinada cuenta contable.
	 * @param idCuenta
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 */
	private BigDecimal getSaldoInicial(int idCuenta){
		
		String sql = "", camposImportes = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);

		
		if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_NACIONAL))
			camposImportes = "(a.amtacctdr - a.amtacctcr)"; 
		else
			camposImportes = "(a.uy_amtnativedr - a.uy_amtnativecr)";

		
		try{
			sql = "SELECT COALESCE(SUM(" + camposImportes + "),0) as saldo " +
				  " FROM fact_acct a " +
				  " WHERE a.ad_client_id =" + this.idEmpresa +
				  " AND a.ad_org_id =" + this.idOrganizacion +
				  " AND a.account_id =" + idCuenta +
				  " AND a.dateacct <? "; 

			pstmt = DB.prepareStatement (sql, null);
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

}
