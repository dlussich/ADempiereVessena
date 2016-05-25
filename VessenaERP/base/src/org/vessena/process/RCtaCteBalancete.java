/**
 * RCtaCteBalancete.java
 * 22/02/2011
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.logging.Level;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.SaldoSocioNegocioBean;

/**
 * OpenUp.
 * RCtaCteBalancete
 * Descripcion : Reporte de Balancete de Cuenta Corriente.
 * @author Gabriel Vila
 * Fecha : 22/02/2011
 */
public class RCtaCteBalancete extends SvrProcess {

	private Timestamp fechaHasta = null;
	private boolean esClientes = true;
	private String tipoMoneda = "";
	private int codigoMoneda = 0;
	//private String tipoCtaReporte = "";
	//private BigDecimal codigoSocioNegocio = null;
	
	//private String nombreEmpresa = "";
	//private String nombreUsuario = "";

	//private Long idEmpresa = new Long(0);
	private int idEmpresa = 0;
	private Long idUsuario = new Long(0);
	private int idOrganizacion = 0;
	
	private int codigoCliente = -1;
	private int codigoProveedor = -1;
	
	private String idReporte = "";
	
	private static final String TIPO_MONEDA_SMN = "SMN";
	private static final String TIPO_MONEDA_SME = "SME";
	private static final String TIPO_MONEDA_TMN = "TMN";
	private static final String TIPO_MONEDA_TME = "TME";
	//private static final String CTA_ABIERTA = "CA";
	//private static final String CTA_DOCUMENTADA = "CD";
	private static final String CTA_CORRIENTE_CLIENTE = "CL";
	//private static final String CTA_CORRIENTE_PROVEEDOR = "PR";

	private static final String TABLA_MOLDE = "UY_MOLDE_RAdmBalanceteCtaCte";
	private static final String TABLA_MOLDE_SALDOSPEND = "UY_MOLDE_RAdmSaldosPend";
	
	private Hashtable<String, SaldoSocioNegocioBean> saldos = new Hashtable<String, SaldoSocioNegocioBean>();

	
	/**
	 * Constructor
	 */
	public RCtaCteBalancete() {
		// TODO Auto-generated constructor stub
	}


	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde, los comprobantes a procesar segun filtros ingresados por el usuario
		//this.loadComprobantes();
		
		// Calculo saldos pendientes y acumulados para los comprobantes cargados en la tabla temporal
		//this.calculoSaldos();
		
		this.getData();
		
		// Update de tabla temporal con los saldos ya calculados
		//this.updateSaldos();
		
		// Me aseguro de mostrar saldos pendientes en cero
		//this.deleteBasuraTemporal();
		
		// Inserto en temporal final la sumarizacion de saldos en Moneda Nacional
		//this.insertSaldosFinalesMN();
		
		// Actualizo en temporal final la sumarizacion de saldos en moneda extranjera seleccionada.
		//this.updateSaldoFinalME();
		
		// Delete de registros con saldos en cero en moneda extranjera y nacional
		this.deleteSaldosCero();
		
		return "ok";
	}

	@Override
	protected void prepare() {

		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;
		// Parametro para titulo del reporte
		ProcessInfoParameter paramTituloReporte = null;
		// Parametro para moneda
		ProcessInfoParameter paramMonedaReporte = null;
		
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("idReporte")){
					paramIDReporte = para[i]; 
				}
				if (name.equalsIgnoreCase("tituloReporte")){
					paramTituloReporte = para[i];
				}
				if (name.equalsIgnoreCase("EndDate")){
					this.fechaHasta = (Timestamp)para[i].getParameter();
					para[i].setParameter(this.fechaHasta);
				}
				if (name.equalsIgnoreCase("C_Currency_ID")){
					paramMonedaReporte = para[i];
				}
				if (name.equalsIgnoreCase("AD_User_ID")){
					this.idUsuario = ((BigDecimal)para[i].getParameter()).longValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.idEmpresa = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("C_BP_Group_ID")){
					this.esClientes = (para[i].getParameter().toString().equalsIgnoreCase(CTA_CORRIENTE_CLIENTE)) ? true : false;
				}
				if (name.equalsIgnoreCase("AD_Org_ID")){
					this.idOrganizacion = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter()!=null){
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")){
							this.codigoCliente = ((BigDecimal)para[i].getParameter()).intValue();
						}						
					}						
				}
				if (name.equalsIgnoreCase("C_BPartner_ID_P")){
					if (para[i].getParameter()!=null){
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")){
							this.codigoProveedor = ((BigDecimal)para[i].getParameter()).intValue();
						}						
					}						
				}
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(this.idUsuario);
		
		// Seteo titulo del reporte segun tipo cta corriente
		String tituloReporte = "";
		if (this.esClientes) tituloReporte = "Balancete Saldos - Deudores";
		else tituloReporte = "Balancete Saldos - Proveedores";
		
		// Si tengo parametro para titulo de reporte
		if (paramTituloReporte!=null) paramTituloReporte.setParameter(tituloReporte);

		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);
		
		// Moneda extranjera, tomo el id de moneda directamente del filtro segun seleccion del usuario
		if (paramMonedaReporte!=null) 
			this.codigoMoneda = paramMonedaReporte.getParameterAsInt();
		else
			this.codigoMoneda = 100; // Me cubro de errores y pongo dolares a mano
	
		// Seteo parametro moneda del reporte enviando la descripcion de la moneda seleccionada por el usuario
		if (paramMonedaReporte!=null)
			paramMonedaReporte.setParameter(this.getDescripcionMoneda(this.codigoMoneda));
	}

	/* Carga comprobantes iniciales en tabla molde*/
	private void loadComprobantes(){
	
		String insert ="", sql = "";
		String whereSocioNegocio = "", whereTipoMoneda = "";
		int idSocioNegocio;

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE_SALDOSPEND + " (idReporte, idUsuario, fecReporte, idInvoice, fecDoc, fecVenc, " + 
					"idDoc, docbasetype, tipoDoc, nroDoc, monedaOrigen, impOrigen, saldoPend, saldoAcumulado, idsocionegocio,isinvoice) " ;

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
			if (this.esClientes) whereEsCliente = " AND bp.isCustomer='Y' AND doc.issotrx='Y' ";
			else whereEsCliente = " AND bp.isVendor='Y' AND doc.issotrx='N' ";

			
			sql = "SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date, " +
				  "opamt.c_invoice_id, opamt.dateinvoiced, opamt.duedate, opamt.c_doctype_id, " +
				  "doc.docbasetype, doc.printname, " +
				  "opamt.documentno, opamt.c_currency_id, opamt.grandtotal, opamt.openamt, 0, " +
				  "opamt.c_bpartner_id,'Y' " +
				  " FROM vuy_prueba_invoiceopenamt opamt INNER JOIN c_doctype doc ON opamt.c_doctype_id = doc.c_doctype_id " +
				  " INNER JOIN c_bpartner bp ON opamt.c_bpartner_id = bp.c_bpartner_id " +
				  " WHERE opamt.ad_client_id =" + this.idEmpresa +
				  whereEsCliente + " and opamt.dateinvoiced <= '" + this.fechaHasta + "' " +  // OpenUp M.R. 12-05-2011 Agrego control de fecha para que el filtro tome esta fecha como tope para el calculo de saldos
				  whereSocioNegocio + whereTipoMoneda;
					
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

	/* Obtiene id de moneda nacional para la empresa actual*/
	private Integer getIDMonedaNacional(Integer idEmpresa){

		MClient client = new MClient(getCtx(), idEmpresa, null);
		MAcctSchema schema = client.getAcctSchema();

		return schema.getC_Currency_ID();
	}
	
	private String getDescripcionMoneda(int idMoneda){
		MCurrency model = new MCurrency(getCtx(), idMoneda ,get_TrxName());
		return model.getDescription();
	}
	
	/* Actualiza saldos pendientes y acumulados de los registros de la temporal*/
	private void calculoSaldos(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			// Obtengo registros de la temporal ordenados por socio de negocio - fecha documento ASC
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE_SALDOSPEND +
				  " WHERE idreporte=?" +
				  " ORDER BY idsocionegocio, fecdoc, idinvoice, isinvoice ASC";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
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
						BigDecimal importeConvertido = MConversionRate.convert(getCtx(), rs.getBigDecimal("imporigen"), rs.getInt("c_currency_id"), 
								this.codigoMoneda, rs.getTimestamp(""), 0, this.idEmpresa, this.idOrganizacion); 
						if (importeConvertido==null)
							importeConvertido = MConversionRate.convert(getCtx(), rs.getBigDecimal("imporigen"), rs.getInt("c_currency_id"), 
														this.codigoMoneda, this.idEmpresa, this.idOrganizacion);
						if (importeConvertido!=null)	
							importeOrigen = importeConvertido;
						else
							importeOrigen = Env.ZERO;						
					}
					
					importeOrigen = rs.getBigDecimal("imporigen");
					if (importeOrigen == null) importeOrigen = Env.ZERO;
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
		
				sql = "UPDATE " + TABLA_MOLDE_SALDOSPEND + 
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
			
			sql = "DELETE FROM " + TABLA_MOLDE_SALDOSPEND +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND saldopend = 0";
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}

	// Elimino registros con saldos cero
	private void deleteSaldosCero(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND saldopend_me = 0 " +
				  " AND saldopend_mn = 0";
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}

	
	private void insertSaldosFinalesMN(){

		String sql = "";
		try{
			
			sql = "INSERT INTO  " + TABLA_MOLDE + " (idreporte, idusuario, fecreporte, idmonedaextranjera, " +
					"idsocionegocio, saldopend_mn) " +
				  " SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date, " + this.codigoMoneda + ", " +
				  " sp.idsocionegocio, coalesce(SUM(sp.saldopend),0) as saldo " +
				  " FROM " + TABLA_MOLDE_SALDOSPEND + " AS sp " + 
				  " WHERE idreporte='" + this.idReporte + "'" +
				  " AND sp.monedaorigen=" + this.getIDMonedaNacional(idEmpresa) + 
				  " GROUP BY sp.idsocionegocio ";
			
			DB.executeUpdate(sql,null);
			
			
			sql = "INSERT INTO  " + TABLA_MOLDE + " (idreporte, idusuario, fecreporte, idmonedaextranjera, " +
					"idsocionegocio, saldopend_me) " +
				  " SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date, " + this.codigoMoneda + ", " +
				  " sp.idsocionegocio, coalesce(SUM(sp.saldopend),0) as saldo " +
				  " FROM " + TABLA_MOLDE_SALDOSPEND + " AS sp " + 
				  " WHERE idreporte='" + this.idReporte + "'" +
				  " AND sp.monedaorigen <>" + this.getIDMonedaNacional(idEmpresa) + 
				  " GROUP BY sp.idsocionegocio ";
	
			DB.executeUpdate(sql,null);
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}

	private void updateSaldoFinalME(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date, " + this.codigoMoneda + ", " +
			  " sp.idsocionegocio, coalesce(SUM(sp.saldopend),0) as saldo " +
			  " FROM " + TABLA_MOLDE_SALDOSPEND + " AS sp " + 
			  " WHERE idreporte='" + this.idReporte + "'" +
			  " AND sp.monedaorigen=" + this.codigoMoneda + 
			  " AND saldopend_me = 0" +
			  " GROUP BY sp.idsocionegocio ";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				String update = " UPDATE " + TABLA_MOLDE +
								" SET saldopend_me = " + rs.getBigDecimal("saldo") +
								" WHERE idreporte ='" + this.idReporte + "'" +
								" AND idsocionegocio = " + rs.getInt("idsocionegocio");
				DB.executeUpdate(update,null);
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


	private void getData(){

		String insert ="", sql = "";
		String whereSocioNegocio = "";
		int idSocioNegocio;

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try
		{
			insert = "INSERT INTO  " + TABLA_MOLDE + " (idreporte, idusuario, fecreporte, idmonedaextranjera, " +
					"idsocionegocio, saldopend_mn, saldopend_me) ";

			// ID de socio de negocio segun filtros indicados por usuario
			if (this.esClientes){
				idSocioNegocio = this.codigoCliente;
			}
			else{
				idSocioNegocio = this.codigoProveedor;
			}
			
			// Armo condicion segun socio de negocio seleccionado
			if (idSocioNegocio>=0) whereSocioNegocio = " AND opamt.C_BPartner_ID=" + idSocioNegocio; 	
		
			String whereEsCliente = "", whereDocBaseTypeFac = "", whereDocBaseTypePAYNC = "";
			if (this.esClientes) {
				whereEsCliente = " AND bp.isCustomer='Y' AND doc.issotrx='Y' ";
				whereDocBaseTypeFac = " AND opamt.docbasetype in ('ARI') "; 
				whereDocBaseTypePAYNC = " AND opamt.docbasetype in ('ARR','ARC') ";
			}
			else {
				whereEsCliente = " AND bp.isVendor='Y' AND doc.issotrx='N' ";
				whereDocBaseTypeFac = " AND opamt.docbasetype in ('API','AFI') ";
				whereDocBaseTypePAYNC = " AND opamt.docbasetype in ('APP','APC') ";
			}

			// Facturas en moneda nacional
			sql = "SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date, " + this.codigoMoneda + "," +
				  "opamt.c_bpartner_id, sum(opamt.grandtotal), 0 " +
				  " FROM vuy_prueba_invoiceopenamt opamt " +
				  " INNER JOIN c_doctype doc ON opamt.c_doctype_id = doc.c_doctype_id " +
				  " INNER JOIN c_bpartner bp ON opamt.c_bpartner_id = bp.c_bpartner_id " +
				  " WHERE opamt.ad_client_id =" + this.idEmpresa +
				  " AND opamt.c_currency_id = " + this.getIDMonedaNacional(idEmpresa) +
				  whereEsCliente + " and opamt.dateinvoiced <= '" + this.fechaHasta + "' " +  // OpenUp M.R. 12-05-2011 Agrego control de fecha para que el filtro tome esta fecha como tope para el calculo de saldos
				  whereSocioNegocio + whereDocBaseTypeFac +  
				  " GROUP BY opamt.c_bpartner_id";
					
			log.info(insert + sql);
			DB.executeUpdate(insert + sql, null);
			
			// Leo e inserto Pagos y NC en moneda nacional
			sql = "SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date, " + this.codigoMoneda + "," +
				  "opamt.c_bpartner_id, sum(opamt.grandtotal) as saldo " +
				  " FROM vuy_prueba_invoiceopenamt opamt " +
				  " INNER JOIN c_doctype doc ON opamt.c_doctype_id = doc.c_doctype_id " +
				  " INNER JOIN c_bpartner bp ON opamt.c_bpartner_id = bp.c_bpartner_id " +
				  " WHERE opamt.ad_client_id =" + this.idEmpresa +
				  " AND opamt.c_currency_id = " + this.getIDMonedaNacional(idEmpresa) +
				  whereEsCliente + " and opamt.dateinvoiced <= '" + this.fechaHasta + "' " +  // OpenUp M.R. 12-05-2011 Agrego control de fecha para que el filtro tome esta fecha como tope para el calculo de saldos
				  whereSocioNegocio + whereDocBaseTypePAYNC +				  
				  " GROUP BY opamt.c_bpartner_id";
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				
				BigDecimal saldo = rs.getBigDecimal("saldo");
				if (saldo == null) saldo = Env.ZERO;
				else
					if (saldo.compareTo(Env.ZERO) < 0) saldo = saldo.negate();
				
				String update = " UPDATE " + TABLA_MOLDE +
								" SET saldopend_mn = saldopend_mn - " + saldo +
								" WHERE idreporte ='" + this.idReporte + "'" +
								" AND idsocionegocio = " + rs.getInt("c_bpartner_id");
				int result = DB.executeUpdate(update,null);
				
				// Si no actualizo entonces insert
				if (result <= 0){
					String values = " VALUES ('" + this.idReporte + "'," + this.idUsuario + ",current_date, " + this.codigoMoneda + "," +
									rs.getInt("c_bpartner_id") + "," + saldo.negate() + ",0)"; 
					DB.executeUpdate(insert + values, null);
				}
			}
			DB.close(rs, pstmt);
			rs = null; pstmt = null;

			// AHORA FACTURA EN MONEDA DEL REPORTE
			sql = "SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date, " + this.codigoMoneda + "," +
				  "opamt.c_bpartner_id, sum(opamt.grandtotal) as saldo " +
				  " FROM vuy_prueba_invoiceopenamt opamt " +
				  " INNER JOIN c_doctype doc ON opamt.c_doctype_id = doc.c_doctype_id " +
				  " INNER JOIN c_bpartner bp ON opamt.c_bpartner_id = bp.c_bpartner_id " +
				  " WHERE opamt.ad_client_id =" + this.idEmpresa +
				  " AND opamt.c_currency_id = " + this.codigoMoneda +
				  whereEsCliente + " and opamt.dateinvoiced <= '" + this.fechaHasta + "' " +  // OpenUp M.R. 12-05-2011 Agrego control de fecha para que el filtro tome esta fecha como tope para el calculo de saldos
				  whereSocioNegocio + whereDocBaseTypeFac + 
				  " GROUP BY opamt.c_bpartner_id";
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				BigDecimal saldo = rs.getBigDecimal("saldo");
				if (saldo == null) saldo = Env.ZERO;
				else
					if (saldo.compareTo(Env.ZERO) < 0) saldo = saldo.negate();
				
				String update = " UPDATE " + TABLA_MOLDE +
								" SET saldopend_me = saldopend_me + " + saldo +
								" WHERE idreporte ='" + this.idReporte + "'" +
								" AND idsocionegocio = " + rs.getInt("c_bpartner_id");
				int result = DB.executeUpdate(update,null);
				
				// Si no actualizo entonces insert
				if (result <= 0){
					String values = " VALUES ('" + this.idReporte + "'," + this.idUsuario + ",current_date, " + this.codigoMoneda + "," +
									rs.getInt("c_bpartner_id") + ",0," + saldo + ")"; 
					DB.executeUpdate(insert + values, null);
				}
			}
			DB.close(rs, pstmt);
			rs = null; pstmt = null;


			// Leo e inserto Pagos y NC en moneda del reporte
			sql = "SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date, " + this.codigoMoneda + "," +
				  "opamt.c_bpartner_id, sum(opamt.grandtotal) as saldo " +
				  " FROM vuy_prueba_invoiceopenamt opamt " +
				  " INNER JOIN c_doctype doc ON opamt.c_doctype_id = doc.c_doctype_id " +
				  " INNER JOIN c_bpartner bp ON opamt.c_bpartner_id = bp.c_bpartner_id " +
				  " WHERE opamt.ad_client_id =" + this.idEmpresa +
				  " AND opamt.c_currency_id = " + this.codigoMoneda +
				  whereEsCliente + " and opamt.dateinvoiced <= '" + this.fechaHasta + "' " +  // OpenUp M.R. 12-05-2011 Agrego control de fecha para que el filtro tome esta fecha como tope para el calculo de saldos
				  whereSocioNegocio + whereDocBaseTypePAYNC +  
				  " GROUP BY opamt.c_bpartner_id";
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				
				BigDecimal saldo = rs.getBigDecimal("saldo");
				if (saldo == null) saldo = Env.ZERO;
				else
					if (saldo.compareTo(Env.ZERO) < 0) saldo = saldo.negate();
				
				String update = " UPDATE " + TABLA_MOLDE +
								" SET saldopend_me = saldopend_me - " + saldo +
								" WHERE idreporte ='" + this.idReporte + "'" +
								" AND idsocionegocio = " + rs.getInt("c_bpartner_id");
				int result = DB.executeUpdate(update,null);
				
				// Si no actualizo entonces insert
				if (result <= 0){
					String values = " VALUES ('" + this.idReporte + "'," + this.idUsuario + ",current_date, " + this.codigoMoneda + "," +
									rs.getInt("c_bpartner_id") + ",0," + saldo.negate() + ")"; 
					DB.executeUpdate(insert + values, null);
				}
			}
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
	}
	
}
