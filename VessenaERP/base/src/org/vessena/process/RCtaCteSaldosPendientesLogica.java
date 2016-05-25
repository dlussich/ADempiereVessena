/**
 * RCtaCteSaldosPendientesLogica.java
 * 13/06/2011
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.logging.Level;
import org.compiere.model.MConversionRate;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.SaldoSocioNegocioBean;

/**
 * OpenUp.
 * RCtaCteSaldosPendientes
 * Descripcion :
 * @author Mario Reyes
 * Fecha : 13/06/2011
 */
public class RCtaCteSaldosPendientesLogica {

	
	private boolean esClientes  ;     //ok
	private String tipoMoneda = "";   //
	private int codigoMoneda = 0;
	private int codigoGrupo = 0;
	//private String tipoCtaReporte = "";
	//private BigDecimal codigoSocioNegocio = null;
	
	private String nombreEmpresa = "";
	private String nombreUsuario = "";

	private int idEmpresa = -1;
	private int idUsuario = -1;
	private int idOrganizacion = 0;
	private CLogger log = null;
	private String transaccion = "";
	
	private int codigoCliente = -1;
	private int codigoProveedor = -1;
	
	private String idReporte = "";
	private Timestamp fechaHasta = null;
	public static final String TIPO_MONEDA_SMN = "SMN";
	public static final String TIPO_MONEDA_SME = "SME";
	public static final String TIPO_MONEDA_TMN = "TMN";
	public static final String TIPO_MONEDA_TME = "TME";
	//private static final String CTA_ABIERTA = "CA";
	//private static final String CTA_DOCUMENTADA = "CD";
	//private static final String CTA_CORRIENTE_CLIENTE = "CL";
	//private static final String CTA_CORRIENTE_PROVEEDOR = "PR";

	private static final String TABLA_MOLDE = "UY_MOLDE_RAdmSaldosPend";
	
	private Hashtable<String, SaldoSocioNegocioBean> saldos = new Hashtable<String, SaldoSocioNegocioBean>();

	
	/**
	 * Constructor
	 */
	public RCtaCteSaldosPendientesLogica(int codigoMoneda, int idEmpresa, int idUsuario,  int idOrganizacion, CLogger log, boolean esClientes, int codigoCliente
			,int codigoProveedor, String idReporte, String TIPO_MONEDA_SME, String TIPO_MONEDA_SMN,String TIPO_MONEDA_TMN, String TIPO_MONEDA_TME, String TABLA_MOLDE, Timestamp fechaHasta, String trx, String nombreEmpresa, String nombreUsuario, String tipoMoneda, int codigoGrupo ){
		this.codigoMoneda= codigoMoneda;
		this.idEmpresa = idEmpresa;
		this.idUsuario = idUsuario;
		this.idOrganizacion = idOrganizacion;
		this.esClientes = esClientes;
		this.codigoCliente = codigoCliente;
		this.codigoProveedor = codigoProveedor;
		this.idReporte = idReporte;
		this.log = log;
		this.fechaHasta = fechaHasta;
		this.transaccion = trx;
		this.nombreEmpresa = nombreEmpresa;
		this.nombreUsuario = nombreUsuario;
		this.tipoMoneda = tipoMoneda;
		this.codigoGrupo = codigoGrupo;
		
		
	}


	
	public String Proceso() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde, los comprobantes a procesar segun filtros ingresados por el usuario
		this.loadComprobantes();
		
		// Calculo saldos pendientes y acumulados para los comprobantes cargados en la tabla temporal
		this.calculoSaldos();
		
		// Update de tabla temporal con los saldos ya calculados
		this.updateSaldos();
		
		// Me aseguro de mostrar saldos pendientes en cero
		this.deleteBasuraTemporal();
		
		return "ok";
	}

	
	
	/* Carga comprobantes iniciales en tabla molde*/
	private void loadComprobantes(){
	
		String insert ="", sql = "";
		String whereSocioNegocio = "", whereTipoMoneda = "";
		int idSocioNegocio;

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (idReporte, idUsuario, fecReporte, idInvoice, fecDoc, fecVenc, " + 
					"idDoc, docbasetype, tipoDoc, nroDoc, monedaOrigen, impOrigen, saldoPend, saldoAcumulado, " +
					"idsocionegocio, isinvoice, empresareporte, usuarioreporte, uy_tipomonedareporte, c_currency_id,  " +
					"c_bpartner_id, ad_user_id, ad_client_id, ad_org_id, fechahasta, c_bp_group_id, c_bpartner_id_p ) " ;

			// ID de socio de negocio segun filtros indicados por usuario
			if (this.esClientes){
				idSocioNegocio = this.codigoCliente;
			}
			else{
				idSocioNegocio = this.codigoProveedor;
			}
			
			// Armo condicion segun socio de negocio seleccionado
			if (idSocioNegocio>=0) whereSocioNegocio = " AND opamt.C_BPartner_ID=" + idSocioNegocio; 	
		
			// Armo condicion segun tipo de moneda y moneda seleccionada
			// Si el usuario indico solo moneda nacional o solo moneda extranjera tengo
			// que filtrar aquellos comprobantes que hayan sido ingresados en esa moneda
			if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_SMN) || this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_SME)) {
				whereTipoMoneda = " AND opamt.C_Currency_ID = " + this.codigoMoneda;
			}
		
			String whereEsCliente = "";
			if (this.esClientes) {
				whereEsCliente = " AND bp.isCustomer='Y' ";
			}
			else if (!this.esClientes){
				whereEsCliente = " AND bp.isVendor='Y' ";
			}
				
			
			sql = "SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date, " +
				  "opamt.c_invoice_id, opamt.dateinvoiced, opamt.duedate, opamt.c_doctype_id, " +
				  "doc.docbasetype, doc.printname, " +
				  "opamt.documentno, opamt.c_currency_id, opamt.grandtotal, coalesce(opamt.openamt,0) as openamt, 0, " +
				  "opamt.c_bpartner_id,'Y', '" + nombreEmpresa +"','" + nombreUsuario +"','" + tipoMoneda+"',"+ 
				  "opamt.c_currency_id," + "opamt.c_bpartner_id,"+ this.idUsuario + "," + this.idEmpresa  + "," +  
				  this.idEmpresa + ",'"+ fechaHasta+ "' ," + this.codigoGrupo + ",opamt.c_bpartner_id " +   
				  " FROM vuy_invoiceopenamt opamt INNER JOIN c_doctype doc ON opamt.c_doctype_id = doc.c_doctype_id " +
				  " INNER JOIN c_bpartner bp ON opamt.c_bpartner_id = bp.c_bpartner_id " +
				  " WHERE opamt.ad_client_id =" + this.idEmpresa +
				  whereEsCliente +
				  whereSocioNegocio + whereTipoMoneda+ " AND opamt.dateinvoiced < (date'"+ fechaHasta +"' + interval '1 day')"  ;
					
			log.info(insert + sql);
			
			DB.executeUpdate(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
		}
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

//	/* Obtiene id de moneda nacional para la empresa actual*/
//	private Integer getIDMonedaNacional(Integer idEmpresa){
//		MAcctSchema schema = new MAcctSchema(Env.getCtx(), idEmpresa ,this.transaccion );
//		return schema.getC_Currency_ID();
//	}
/*	
	private String getDescripcionMoneda(int idMoneda){
		MCurrency model = new MCurrency(Env.getCtx(), idMoneda ,transaccion);
		return model.getDescription();
	}
	*/
	/* Actualiza saldos pendientes y acumulados de los registros de la temporal*/
	private void calculoSaldos(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			this.updateSaldosPendAFechaFiltro();
			
			// OpenUp. Gabriel Vila. 26/10/2011. Issue #863.
			// Para filtro solo moneda extranjera, trae los saldos siempre en pesos.
			// Debo convertirlos a moneda extranjera de los comprobantes
			this.updateSaldosMonedaExtranjera();
			// Fin Issue #863

			
			// Obtengo registros de la temporal ordenados por socio de negocio - fecha documento ASC
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY idsocionegocio, fecdoc, idinvoice, isinvoice ASC";
			
			pstmt = DB.prepareStatement (sql, transaccion);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();
			
			String idSocioNegocio = "";
			BigDecimal saldoAcumulado = new BigDecimal(0);
			
			// Tengo que hacer corte de control por socio de negocio, ya que el acumulado se resetea cada
			// vez que inicio un nuevo socio de negocio
			while (rs.next()){
				String idSocioNegocioAux = String.valueOf(rs.getLong("idsocionegocio"));

				BigDecimal importeOrigen = rs.getBigDecimal("imporigen");
				
				// Si hay cambio de socio de negocio
				if (!idSocioNegocioAux.equalsIgnoreCase(idSocioNegocio)){
					// Reseto acumulado 
					saldoAcumulado = new BigDecimal(0);
					// Guardo nuevo id de socio de negocio en proceso
					idSocioNegocio = idSocioNegocioAux;
				}
				
				// Si el usuario selecciono todos los movimientos a una determinada moneda
				if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_TMN) || this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_TME)){
					// Si esta documento no es igual a la moneda del reporte
					if (this.codigoMoneda!=rs.getInt("c_currency_id")){
						// Debo convertir importe a moneda del reporte segun fecha del documento
						BigDecimal importeConvertido = MConversionRate.convert(Env.getCtx(), rs.getBigDecimal("imporigen"), rs.getInt("c_currency_id"), 
								this.codigoMoneda, rs.getTimestamp(""), 0, this.idEmpresa, this.idOrganizacion); 
						if (importeConvertido==null)
							importeConvertido = MConversionRate.convert(Env.getCtx(), rs.getBigDecimal("imporigen"), rs.getInt("c_currency_id"), 
														this.codigoMoneda, this.idEmpresa, this.idOrganizacion);
						if (importeConvertido!=null)	
							importeOrigen = importeConvertido;
						else
							importeOrigen = Env.ZERO;						
					}	
				}
				
				BigDecimal pendiente = rs.getBigDecimal("saldoPend");
				//boolean isInvoice = true;
				//String docBaseType = rs.getString("docbasetype");

				//if (docBaseType.equalsIgnoreCase("ARR") || docBaseType.equalsIgnoreCase("APP")) isInvoice=false;
				
				// Segun sea invoice o payment, actualizo saldo acumulado y doy vuelta el signo de 
				// los importes para cosmetica del reporte.
				
				saldoAcumulado = saldoAcumulado.add(pendiente);
				
				/*if (isInvoice){
					// Si es nota de credito (cliente o proveedor) resto y doy vuelta el signo
					if ((rs.getString("docbasetype").equalsIgnoreCase("APC")) || (rs.getString("docbasetype").equalsIgnoreCase("ARC"))){
						saldoAcumulado = saldoAcumulado.subtract(pendiente);
						importeOrigen = importeOrigen.multiply(new BigDecimal(-1));
						pendiente = pendiente.multiply(new BigDecimal(-1));
					}
					else{
						saldoAcumulado = saldoAcumulado.add(pendiente);	
					}
				}
				else{
					saldoAcumulado = saldoAcumulado.subtract(pendiente);
					importeOrigen = importeOrigen.multiply(new BigDecimal(-1));
					pendiente = pendiente.multiply(new BigDecimal(-1));
				}*/
				// Agrego saldos calculados para socio de negocio - id_tipocomprobante - id_comprobante
				String key = idSocioNegocio + "_" + String.valueOf(rs.getLong("iddoc")) + "_" + String.valueOf(rs.getLong("idinvoice"));
				SaldoSocioNegocioBean saldo = new SaldoSocioNegocioBean();
				saldo.idSocioNegocio = Long.parseLong(idSocioNegocio);
				saldo.idTipoComprobante = rs.getLong("iddoc");
				saldo.idComprobante = rs.getLong("idinvoice");
				saldo.saldoPendiente = pendiente;
				saldo.saldoAcumulado = saldoAcumulado;
				saldo.importeOriginal = importeOrigen;
				
				saldos.put(key, saldo);
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
	
	
	/* Recorro hash con saldos previamente calculados y hago el update correspondiente
	 * en la tabla temporal.
	 */
	private void updateSaldos(){

		String sql = "";
		
		try{
			// Recorro hash de saldos previamente calculados
			for (SaldoSocioNegocioBean saldo : saldos.values()){
		
				sql = "UPDATE " + TABLA_MOLDE + 
				  " SET saldopend = " + saldo.saldoPendiente + "," +
				  	" saldoacumulado = " + saldo.saldoAcumulado + "," +
				  	" imporigen = " + saldo.importeOriginal +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND idsocionegocio = " + saldo.idSocioNegocio + 
				  " AND iddoc = " + saldo.idTipoComprobante +
				  " AND idinvoice = " + saldo.idComprobante;
				
				DB.executeUpdate(sql,null);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}

	/* Elimino registros de tabla temporal con saldo pendiente cero. No deberia haber ningun registro pero me cubro de errores. */
	private void deleteBasuraTemporal(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND saldopend = 0";
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	/**
	 * OpenUp. Gabriel Vila. 26/10/2011. Issue #863.
	 * Si me indican por filtro que el reporte es solo a una moneda extranjera, 
	 * tengo el problema que la vista de saldos pendientes esta en pesos.
	 * Por lo tanto tengo que convertir esos saldos a la moneda extranjera.
	 * Se asume que los comprobantes filtrados tienen la moneda del filtro.
	 */
	private void updateSaldosMonedaExtranjera(){
		
		// Si el usuario no indico SOLO MONEDA EXTRANJERA, salgo.
		if (!this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_SME)) return;

		String sql = "";
		try{
			
			sql = " UPDATE " + TABLA_MOLDE +
				  " SET saldopend = currencyconvert(saldopend,142," + this.codigoMoneda + ",fecdoc,0," + this.idEmpresa + "," + idOrganizacion + ") " +
				  " WHERE idreporte ='" + this.idReporte + "'";
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}		
	}

	/**
	 * OpenUp. Gabriel Vila. 27/10/2011.
	 * Recalcula saldos pendientes de comprobantes cuando la fecha filtro es menor a hoy.
	 */
	private void updateSaldosPendAFechaFiltro(){
		
		// Si la fecha seleccionada en el filro es menor a la fecha de hoy, 
		// tengo que recalcular saldos pendientes a esa fecha filtro
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		if (!this.fechaHasta.before(today)) return;
		
		Hashtable<String, SaldoSocioNegocioBean> newSaldos = new Hashtable<String, SaldoSocioNegocioBean>();
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			// Obtengo registros de la temporal ordenados por socio de negocio - fecha documento ASC
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY idsocionegocio, fecdoc, idinvoice, isinvoice ASC";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, this.idReporte);
			rs = pstmt.executeQuery ();
			
			while (rs.next()){

				boolean isInvoice = true;
				String docBaseType = rs.getString("docbasetype");
				int recordID = rs.getInt("idInvoice");
				
				if (docBaseType.equalsIgnoreCase("ARR") || docBaseType.equalsIgnoreCase("APP")) isInvoice=false;
				
				BigDecimal newSaldoPend = this.getSaldoPendienteAFechaFiltro(recordID, isInvoice);
				
				String idSocioNegocio = String.valueOf(rs.getLong("idsocionegocio"));
				String key = idSocioNegocio + "_" + String.valueOf(rs.getLong("iddoc")) + "_" + String.valueOf(rs.getLong("idinvoice"));
				SaldoSocioNegocioBean saldo = new SaldoSocioNegocioBean();
				saldo.idSocioNegocio = Long.parseLong(idSocioNegocio);
				saldo.idTipoComprobante = rs.getLong("iddoc");
				saldo.idComprobante = rs.getLong("idinvoice");
				saldo.saldoPendiente = newSaldoPend;
				
				newSaldos.put(key, saldo);				
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

		try{
			// Recorro hash de saldos previamente calculados
			for (SaldoSocioNegocioBean saldo : newSaldos.values()){
		
				sql = "UPDATE " + TABLA_MOLDE + 
				  " SET saldopend = " + saldo.saldoPendiente +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND idsocionegocio = " + saldo.idSocioNegocio + 
				  " AND iddoc = " + saldo.idTipoComprobante +
				  " AND idinvoice = " + saldo.idComprobante;
				
				DB.executeUpdate(sql,null);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}

	}
	
	/**
	 * OpenUp. Gabriel Vila. 27/10/2011.
	 * Obtiene y retorna saldo pendiente de un paymento o invoice, a una determinada fecha. 
	 * @param record_id
	 * @param isInvoice
	 */
	private BigDecimal getSaldoPendienteAFechaFiltro(int record_id, boolean isInvoice){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal value = Env.ZERO;
		
		try{
			// La sql cambia dependiendo si es una invoice o un payment
			if (isInvoice)
				sql = " SELECT currencybase(" +
					  " CASE WHEN substring(c_doctype.docbasetype, 1, 2) = 'AP' " +
					  	" THEN (-1) " +
					  	" ELSE 1 " +
					  	" END * " +
					  " CASE WHEN charat(c_doctype.docbasetype, 3)= 'C' " +
					  " THEN (-1) " +
					  " ELSE 1 " +
					  " END * " +
					  " (c_invoice.grandtotal - " +
					  " COALESCE(( SELECT sum(abs(c_allocationline.amount + c_allocationline.discountamt + c_allocationline.writeoffamt)) AS sum " +
					  			 " FROM c_allocationline " +
					  			 " LEFT JOIN c_allocationhdr ON c_allocationhdr.c_allocationhdr_id = c_allocationline.c_allocationhdr_id " +
					  			 " WHERE c_allocationline.c_invoice_id = c_invoice.c_invoice_id " +
					  			 " AND c_allocationhdr.datetrx <=? " +
					  			 " AND (c_allocationhdr.docstatus = 'CO' OR c_allocationhdr.docstatus = 'CL')), 0)), " +
					  			 " c_invoice.c_currency_id, c_invoice.dateacct, c_invoice.ad_client_id, c_invoice.ad_org_id) AS openamt " +
					  " FROM c_invoice " +
					  " LEFT JOIN c_doctype ON c_doctype.c_doctype_id = c_invoice.c_doctype_id " +
					  " WHERE c_invoice.docstatus = 'CO'" +
					  " AND c_invoice.c_invoice_id =?";
			else
				sql = " SELECT currencybase(" +
					  " CASE WHEN substring(c_doctype.docbasetype, 1, 2) = 'AP' " +
					  " THEN (-1) " +
					  " ELSE 1 " +
					  " END * (-1) * " +
					  "(c_payment.payamt - " +
					  " COALESCE(( SELECT sum(c_allocationline.amount + c_allocationline.discountamt + c_allocationline.writeoffamt) AS sum " +
					  			 " FROM c_allocationline " +
					  			 " LEFT JOIN c_allocationhdr ON c_allocationhdr.c_allocationhdr_id = c_allocationline.c_allocationhdr_id " +
					  			 " WHERE c_allocationline.c_payment_id = c_payment.c_payment_id " +
					  			 " AND c_allocationhdr.datetrx <=? " +
					  			 " AND (c_allocationhdr.docstatus = 'CO' OR c_allocationhdr.docstatus = 'CL')), 0)), " +
					  			 " c_payment.c_currency_id, c_payment.dateacct, c_payment.ad_client_id, c_payment.ad_org_id) AS openamt " +
					  " FROM c_payment " +
					  " LEFT JOIN c_doctype ON c_doctype.c_doctype_id = c_payment.c_doctype_id " +
					  " WHERE (c_payment.docstatus = 'CO' OR c_payment.docstatus = 'CL') " +
					  " AND c_payment.c_payment_id =?";	
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setTimestamp(1, this.fechaHasta);
			pstmt.setInt(2, record_id);
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				value = rs.getBigDecimal(1);				
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
