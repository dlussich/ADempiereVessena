/**
 * RAdmEstadoCuentas.java
 * 22/09/2010
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MCurrency;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * OpenUp.
 * RAdmEstadoCuentas
 * Descripcion : Reporte de Estado de Cuentas (Cuenta Corriente).
 * @author Gabriel Vila
 * Fecha : 22/09/2010
 */
public class RAdmEstadoCuentas extends SvrProcess {

	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;

	private boolean esCliente = true;
	private int idMoneda = 0;
	private int idCliente = -1;
	private int idProveedor = -1;
	private int idEmpresa = 0;
	private int idOrganizacion = 0;
	private Long idUsuario = new Long(0);
	private String idReporte = "";
	private static final String CTA_CORRIENTE_CLIENTE = "CL";
	private static final String TABLA_MOLDE = "UY_MOLDE_RAdmEstadoCuentas";
	
	
	/**
	 * Constructor
	 */
	public RAdmEstadoCuentas() {
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
		// Parametro para moneda
		ProcessInfoParameter paramMonedaReporte = null;

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
				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.idEmpresa = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("AD_Org_ID")){
					this.idOrganizacion = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				
				if (name.equalsIgnoreCase("C_Currency_ID")){
					if (para[i].getParameter()!=null){
						paramMonedaReporte = para[i];
						this.idMoneda = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}

				if (name.equalsIgnoreCase("C_BP_Group_ID")){
					this.esCliente = (para[i].getParameter().toString().equalsIgnoreCase(CTA_CORRIENTE_CLIENTE)) ? true : false;
				}

				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter()!=null){
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")){
							this.idCliente = ((BigDecimal)para[i].getParameter()).intValueExact();
						}						
					}						
				}
				if (name.equalsIgnoreCase("C_BPartner_ID_P")){
					if (para[i].getParameter()!=null){
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")){
							this.idProveedor = ((BigDecimal)para[i].getParameter()).intValueExact();
						}						
					}						
				}
				
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(this.idUsuario);
		
		// Seteo titulo del reporte segun tipo cta corriente
		String tituloReporte = "";
		if (this.esCliente) tituloReporte = "Estado de Cuenta - Deudores";
		else tituloReporte = "Estado de Cuenta - Proveedores";
		
		// Si tengo parametro para titulo de reporte
		if (paramTituloReporte!=null) paramTituloReporte.setParameter(tituloReporte);

		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);

		// Si tengo parametros de fechas para mostrar en el reporte
		if (paramStartDate!=null) paramStartDate.setParameter(this.fechaDesde);
		if (paramEndDate!=null) paramEndDate.setParameter(this.fechaHasta);
	
		// Seteo parametro moneda del reporte enviando la descripcion de la moneda seleccionada por el usuario
		if (paramMonedaReporte!=null)
			paramMonedaReporte.setParameter(this.getDescripcionMoneda(this.idMoneda));
		
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		RAdmEstadoCuentasLogica logica = new RAdmEstadoCuentasLogica(fechaDesde, fechaHasta, esCliente, idMoneda, idProveedor, idEmpresa, idOrganizacion,
				idUsuario, idReporte, log, get_TrxName(), idCliente);

		String salida = logica.procesar();
		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		// this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.
		// this.loadMovimientos();
		
		// Calculo saldos 
		// this.calculoSaldos();
		
		// Me aseguro de no mostrar registros con importes en cero
		// this.deleteBasuraTemporal();
		
		return salida;
		
	}
	
	private String getDescripcionMoneda(int idMoneda){
		MCurrency model = new MCurrency(getCtx(), idMoneda ,get_TrxName());
		return model.getDescription();
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
		
		String insert = "";
		String sqlInvoice = "";
		String sqlPayment = "";
		String isSOTRX = "";
		int idSocioNegocio = 0;
		String whereSocioNegocio = "";
		String whereMoneda = "";

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, idusuario, fecreporte, idcomprobante, fecdoc, fecvenc, " + 
					" iddoc, docbasetype, tipodoc, nrodoc, idmonedaorigen, imporigen, saldoinicial, debe, haber, saldoAcumulado, "+
					" idsocionegocio, isinvoice, issotrx) " ;

			// ID de socio de negocio segun filtros indicados por usuario
			if (this.esCliente){
				idSocioNegocio = this.idCliente;
				isSOTRX = "Y";
			}
			else{
				idSocioNegocio = this.idProveedor;
				isSOTRX = "N";
			}
			
			// Armo condicion segun socio de negocio seleccionado
			if (idSocioNegocio>=0) whereSocioNegocio = " AND a.C_BPartner_ID=" + idSocioNegocio; 	
		
			// Condicion de moneda
			if (this.idMoneda>0) whereMoneda = " AND a.C_Currency_ID = " + this.idMoneda;
			
			sqlInvoice = "SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date,a.c_invoice_id as idcomprobante, a.dateinvoiced as fechacomprobante, paymenttermduedate(a.c_paymentterm_id, a.dateinvoiced::timestamp with time zone) AS fechavencimiento, " +
							"a.c_doctype_id as idcomprobante, b.docbasetype, b.printname as tipocomprobante, " +
							"a.documentno as nrocomprobante, a.c_currency_id, a.grandtotal as importeorigen, " +
							"0, 0, 0, 0, a.C_BPartner_ID as idSocioNegocio,'Y', b.issotrx " +
						  " FROM C_Invoice a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
						  " WHERE a.ad_client_id =" + this.idEmpresa +
						  " AND a.ad_org_id =" + this.idOrganizacion +
						  " AND a.dateinvoiced between '" + this.fechaDesde + "' AND '" + this.fechaHasta + "' " +
						  " AND a.docstatus = 'CO' " +
						  " AND a.processed = 'Y' " + 
						  whereSocioNegocio + whereMoneda +
						  " AND b.issotrx='" + isSOTRX + "' ";
			
			sqlPayment = " UNION " + 
						 "SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date,a.c_payment_id as idcomprobante, " +
							"a.datetrx as fechacomprobante, a.datetrx AS fechavencimiento, " +
							"a.c_doctype_id as idcomprobante, b.docbasetype, b.printname as tipocomprobante, " +
							"a.documentno as nrocomprobante, a.c_currency_id, a.payamt as importeorigen, " +
							"0, 0, 0, 0, a.C_BPartner_ID as idSocioNegocio,'N', b.issotrx " +
						  " FROM C_Payment a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
						  " WHERE a.ad_client_id =" + this.idEmpresa +
						  " AND a.ad_org_id =" + this.idOrganizacion +
						  " AND a.datetrx between '" + this.fechaDesde + "' AND '" + this.fechaHasta + "' " +
						  " AND a.docstatus = 'CO' " +
						  " AND a.processed = 'Y' " +
						  whereSocioNegocio + whereMoneda +
						  " AND b.issotrx='" + isSOTRX + "' ";						  

			
			log.info(insert + sqlInvoice + sqlPayment);
			
			DB.executeUpdate(insert + sqlInvoice + sqlPayment, null);
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sqlInvoice + sqlPayment, e);
		}
	}

	private void calculoSaldos(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			BigDecimal saldoAcumulado = new BigDecimal(0);
			BigDecimal saldoInicial = new BigDecimal(0);
			int idSocioNegocio = -1;
			
			// Obtengo registros de la temporal ordenados por socio de negocio - fecha documento ASC
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY idsocionegocio, fecdoc, iddoc, idcomprobante, isinvoice ASC";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();

			// Recorro registros y voy actualizando
			while (rs.next()){
				
				int idSocioNegocioAux = rs.getInt("idsocionegocio");
				BigDecimal importeOrigen = rs.getBigDecimal("imporigen");
				if (importeOrigen.doubleValue() < 0) importeOrigen = importeOrigen.multiply(new BigDecimal(-1));
				
				// Si hay cambio de socio de negocio
				if (idSocioNegocioAux != idSocioNegocio){
					// Obtengo saldo inicial de este nuevo socio de negocio
					saldoInicial = this.getSaldoInicialDebe(idSocioNegocioAux).subtract(this.getSaldoInicialHaber(idSocioNegocioAux));
					//saldoInicial = new BigDecimal(0);
					// Actualizo saldo inicial para este nuevo socio de negocio
					this.updateSaldoInicial(saldoInicial, idSocioNegocioAux);
					// Reseto acumulado = saldo inicial 
					saldoAcumulado = saldoInicial;
					// Guardo nuevo id de socio de negocio en proceso
					idSocioNegocio = idSocioNegocioAux;
				}
				
				boolean isInvoice = rs.getString("isinvoice").trim().equalsIgnoreCase("Y");
				boolean isSOTrx = rs.getString("issotrx").trim().equalsIgnoreCase("Y");
				
				// Segun sea invoice o payment, actualizo saldo acumulado y doy vuelta el signo de 
				// los importes para cosmetica del reporte.
				if (isInvoice){
					// Si es nota de credito (cliente o proveedor) resto y doy vuelta el signo
					if ((rs.getString("docbasetype").equalsIgnoreCase("APC")) || (rs.getString("docbasetype").equalsIgnoreCase("ARC"))){
						//importeOrigen = importeOrigen.multiply(new BigDecimal(-1));
						if (isSOTrx){
							saldoAcumulado = saldoAcumulado.subtract(importeOrigen);
							this.updateImportes(false, importeOrigen, saldoAcumulado, rs.getInt("idcomprobante"), idSocioNegocio, rs.getInt("iddoc"));	
						}
						else{
							saldoAcumulado = saldoAcumulado.add(importeOrigen);
							this.updateImportes(true, importeOrigen, saldoAcumulado, rs.getInt("idcomprobante"), idSocioNegocio, rs.getInt("iddoc"));
						}
					}
					else{

						if (isSOTrx){
							saldoAcumulado = saldoAcumulado.add(importeOrigen);
							this.updateImportes(true, importeOrigen, saldoAcumulado, rs.getInt("idcomprobante"), idSocioNegocio, rs.getInt("iddoc"));	
						}
						else{
							saldoAcumulado = saldoAcumulado.subtract(importeOrigen);
							this.updateImportes(false, importeOrigen, saldoAcumulado, rs.getInt("idcomprobante"), idSocioNegocio, rs.getInt("iddoc"));
						}
					}
				}
				else{
					//importeOrigen = importeOrigen.multiply(new BigDecimal(-1));
					if (isSOTrx){
						saldoAcumulado = saldoAcumulado.subtract(importeOrigen);
						this.updateImportes(false, importeOrigen, saldoAcumulado, rs.getInt("idcomprobante"), idSocioNegocio, rs.getInt("iddoc"));	
					}
					else{
						saldoAcumulado = saldoAcumulado.add(importeOrigen);
						this.updateImportes(true, importeOrigen, saldoAcumulado, rs.getInt("idcomprobante"), idSocioNegocio, rs.getInt("iddoc"));
					}
				}
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
	 * Descripcion : Obtiene saldo inicial al DEBE de un determinado socio de negocio (deudor o proveedor).
	 * @param idSocioNegocio. int :  ID del socio de negocio a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 */
	private BigDecimal getSaldoInicialDebe(int idSocioNegocio) {
		
		BigDecimal value = new BigDecimal(0);
		
		// Si estoy en Estado de Cuenta para Deudores
		if (this.esCliente){
			// Debe = Facturas venta
			value = this.getSaldoInicialDebeDeudInvoice(idSocioNegocio);
		}
		else{
			// Estoy en Estado de Cuenta para Proveedores
			// Debe = Notas de Credito Proveedores + Pagos
			value = this.getSaldoInicialDebeProvInvoice(idSocioNegocio).add(this.getSaldoInicialDebeProvPayment(idSocioNegocio));
		}
		
		return value;
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial al HABER de un determinado socio de negocio (deudor o proveedor).
	 * @param idSocioNegocio. int :  ID del socio de negocio a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 */
	private BigDecimal getSaldoInicialHaber(int idSocioNegocio) {

		BigDecimal value = new BigDecimal(0);
		
		// Si estoy en Estado de Cuenta para Deudores
		if (this.esCliente){
			// Haber = Notas de Credito Clientes + Cobranzas
			value = this.getSaldoInicialHaberDeudInvoice(idSocioNegocio).add(this.getSaldoInicialHaberDeudPayment(idSocioNegocio));
		}
		else{
			// Estoy en Estado de Cuenta para Proveedores
			// Haber = Facturas proveedor
			value = this.getSaldoInicialHaberProvInvoice(idSocioNegocio);
		}
		
		return value;
	}
	
	private void updateSaldoInicial(BigDecimal valor, int idSocioNegocio){

		String sql = "";
		
		try{
			
			sql = "UPDATE " + TABLA_MOLDE + 
				  " SET saldoinicial = " + valor +
				  " WHERE idsocionegocio =" + idSocioNegocio;
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	private void updateImportes(boolean esDebe, BigDecimal importe, BigDecimal saldoAcumulado, int idcomprobante, int idsocionegocio, int idTipoDoc){
		
		String sql = "";
		
		try{

			String campo = (esDebe) ? "debe" : "haber";

			sql = "UPDATE " + TABLA_MOLDE + 
				  " SET " + campo + " = " + importe + ", " +
				  " saldoacumulado =" + saldoAcumulado +
				  " WHERE idcomprobante =" + idcomprobante +
				  " AND idsocionegocio =" + idsocionegocio + 
				  " AND iddoc =" + idTipoDoc;
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de facturas de venta para un determinado cliente.
	 * @param idSocioNegocio. int : ID del cliente a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 */
	private BigDecimal getSaldoInicialDebeDeudInvoice(int idSocioNegocio){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereSocioNegocio = "", whereMoneda = "";
			
			// Armo condicion segun socio de negocio seleccionado
			if (idSocioNegocio>=0) whereSocioNegocio = " AND a.C_BPartner_ID=" + idSocioNegocio; 	
			// Condicion de moneda
			if (this.idMoneda>0) whereMoneda = " AND a.C_Currency_ID = " + this.idMoneda;

			sql = "SELECT COALESCE(SUM(a.grandtotal),0) as saldo " +
				  " FROM C_Invoice a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.idEmpresa +
				  " AND a.ad_org_id =" + this.idOrganizacion +
				  " AND a.dateinvoiced <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.processed = 'Y' " + 
				  whereSocioNegocio + whereMoneda +
				  " AND b.issotrx='Y'" +
				  " AND b.docbasetype in ('ARI')";

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
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de notas de credito de venta para un determinado cliente.
	 * @param idSocioNegocio. int : ID del cliente a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 */
	private BigDecimal getSaldoInicialHaberDeudInvoice(int idSocioNegocio){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereSocioNegocio = "", whereMoneda = "";
			
			// Armo condicion segun socio de negocio seleccionado
			if (idSocioNegocio>=0) whereSocioNegocio = " AND a.C_BPartner_ID=" + idSocioNegocio; 	
			// Condicion de moneda
			if (this.idMoneda>0) whereMoneda = " AND a.C_Currency_ID = " + this.idMoneda;

			sql = "SELECT COALESCE(SUM(a.grandtotal),0) as saldo " +
				  " FROM C_Invoice a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.idEmpresa +
				  " AND a.ad_org_id =" + this.idOrganizacion +
				  " AND a.dateinvoiced <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.processed = 'Y' " + 
				  whereSocioNegocio + whereMoneda +
				  " AND b.issotrx='Y'" +
				  " AND b.docbasetype in ('ARC')";

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
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de cobranzas de ventas para un determinado cliente.
	 * @param idSocioNegocio. int : ID del cliente a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 */
	private BigDecimal getSaldoInicialHaberDeudPayment(int idSocioNegocio){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereSocioNegocio = "", whereMoneda = "";
			
			// Armo condicion segun socio de negocio seleccionado
			if (idSocioNegocio>=0) whereSocioNegocio = " AND a.C_BPartner_ID=" + idSocioNegocio; 	
			// Condicion de moneda
			if (this.idMoneda>0) whereMoneda = " AND a.C_Currency_ID = " + this.idMoneda;

			sql = "SELECT COALESCE(SUM(a.payamt),0) as saldo " +
				  " FROM C_Payment a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.idEmpresa +
				  " AND a.ad_org_id =" + this.idOrganizacion +
				  " AND a.datetrx <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.processed = 'Y' " + 
				  whereSocioNegocio + whereMoneda +
				  " AND b.issotrx='Y'" +
				  " AND b.docbasetype in ('ARR')";

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

	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de notas de credito de compra para un determinado proveedor.
	 * @param idSocioNegocio. int : ID del proveedor a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 */
	private BigDecimal getSaldoInicialDebeProvInvoice(int idSocioNegocio){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereSocioNegocio = "", whereMoneda = "";
			
			// Armo condicion segun socio de negocio seleccionado
			if (idSocioNegocio>=0) whereSocioNegocio = " AND a.C_BPartner_ID=" + idSocioNegocio; 	
			// Condicion de moneda
			if (this.idMoneda>0) whereMoneda = " AND a.C_Currency_ID = " + this.idMoneda;

			sql = "SELECT COALESCE(SUM(a.grandtotal),0) as saldo " +
				  " FROM C_Invoice a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.idEmpresa +
				  " AND a.ad_org_id =" + this.idOrganizacion +
				  " AND a.dateinvoiced <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.processed = 'Y' " + 
				  whereSocioNegocio + whereMoneda +
				  " AND b.issotrx='N'" +
				  " AND b.docbasetype in ('APC')";

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

	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de pagos de compras para un determinado proveedor.
	 * @param idSocioNegocio. int : ID del proveedor a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 */
	private BigDecimal getSaldoInicialDebeProvPayment(int idSocioNegocio){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereSocioNegocio = "", whereMoneda = "";
			
			// Armo condicion segun socio de negocio seleccionado
			if (idSocioNegocio>=0) whereSocioNegocio = " AND a.C_BPartner_ID=" + idSocioNegocio; 	
			// Condicion de moneda
			if (this.idMoneda>0) whereMoneda = " AND a.C_Currency_ID = " + this.idMoneda;

			sql = "SELECT COALESCE(SUM(a.payamt),0) as saldo " +
				  " FROM C_Payment a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.idEmpresa +
				  " AND a.ad_org_id =" + this.idOrganizacion +
				  " AND a.datetrx <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.processed = 'Y' " + 
				  whereSocioNegocio + whereMoneda +
				  " AND b.issotrx='N'" +
				  " AND b.docbasetype in ('APP')";

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

	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de facturas de compra para un determinado proveedor.
	 * @param idSocioNegocio. int : ID del proveedor a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 */
	private BigDecimal getSaldoInicialHaberProvInvoice(int idSocioNegocio){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereSocioNegocio = "", whereMoneda = "";
			
			// Armo condicion segun socio de negocio seleccionado
			if (idSocioNegocio>=0) whereSocioNegocio = " AND a.C_BPartner_ID=" + idSocioNegocio; 	
			// Condicion de moneda
			if (this.idMoneda>0) whereMoneda = " AND a.C_Currency_ID = " + this.idMoneda;

			sql = "SELECT COALESCE(SUM(a.grandtotal),0) as saldo " +
				  " FROM C_Invoice a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.idEmpresa +
				  " AND a.ad_org_id =" + this.idOrganizacion +
				  " AND a.dateinvoiced <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.processed = 'Y' " + 
				  whereSocioNegocio + whereMoneda +
				  " AND b.issotrx='N'" +
				  " AND b.docbasetype in ('API')";

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

}
