/**
 * RAdmAfectaciones.java
 * 19/11/2010
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * RAdmAfectaciones
 * Descripcion : Reporte de Afectaciones en Cuenta Corriente.
 * @author Gabriel Vila
 * Fecha : 19/11/2010
 */
public class RAdmAfectaciones extends SvrProcess {

	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;

	private boolean esCliente = true;
	private String nroDocumento ="";
	private int idTipoDocumento = -1;
	private int idSocioNegocio = -1;
	private int idEmpresa = 0;
	//private int idOrganizacion = 0;
	private Long idUsuario = new Long(0);
	//private String nombreEmpresa = "";
	//private String nombreUsuario = "";
	private String idReporte = "";

	private static final String CTA_CORRIENTE_CLIENTE = "CL";
	//private static final String CTA_CORRIENTE_PROVEEDOR = "PR";
	
	private static final String TABLA_MOLDE_PADRES = "UY_MOLDE_RAdmAfectaPadres";
	private static final String TABLA_MOLDE_HIJOS = "UY_MOLDE_RAdmAfectaHijos";

	
	/**
	 * Constructor
	 */
	public RAdmAfectaciones() {
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

				/*if (name.equalsIgnoreCase("AD_Org_ID")){
					this.idOrganizacion = ((BigDecimal)para[i].getParameter()).intValueExact();
				}*/
				
				if (name.equalsIgnoreCase("C_DocType_ID")){
					if (para[i].getParameter()!=null){
						this.idTipoDocumento = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				
				if (name.equalsIgnoreCase("DocumentNo")){
					if (para[i].getParameter()!=null){
						this.nroDocumento = para[i].getParameter().toString();
					}
				}

				if (name.equalsIgnoreCase("C_BP_Group_ID")){
					this.esCliente = (para[i].getParameter().toString().equalsIgnoreCase(CTA_CORRIENTE_CLIENTE)) ? true : false;
				}

				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter()!=null){
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")){
							this.idSocioNegocio = ((BigDecimal)para[i].getParameter()).intValueExact();
						}						
					}						
				}
				if (name.equalsIgnoreCase("C_BPartner_ID_P")){
					if (para[i].getParameter()!=null){
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")){
							this.idSocioNegocio = ((BigDecimal)para[i].getParameter()).intValueExact();
						}						
					}						
				}				
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(this.idUsuario);
		
		// Seteo titulo del reporte segun tipo cta corriente
		String tituloReporte = "";
		if (this.esCliente) tituloReporte = "Afectacion - Deudores";
		else tituloReporte = "Afectacion - Proveedores";
		
		// Si tengo parametro para titulo de reporte
		if (paramTituloReporte!=null) paramTituloReporte.setParameter(tituloReporte);

		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);

		// Si tengo parametros de fechas para mostrar en el reporte
		if (paramStartDate!=null) paramStartDate.setParameter(this.fechaDesde);
		if (paramEndDate!=null) paramEndDate.setParameter(this.fechaHasta);
		
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
		this.calculoAfectaciones();
		
		// Me aseguro de no mostrar registros con importes en cero
		//this.deleteBasuraTemporal();
		
		return "ok";

	}

	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE_PADRES +
				  " WHERE idusuario =" + this.idUsuario;
			DB.executeUpdate(sql,null);
			
			sql = "DELETE FROM " + TABLA_MOLDE_HIJOS +
			  " WHERE idusuario =" + this.idUsuario;
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
		String whereFiltros = "";

		try
		{
			// Cargo comprobantes padres
			insert = "INSERT INTO " + TABLA_MOLDE_PADRES + " (idreporte, idusuario, fecreporte, idtipodoc, docbasetype," +
					"nombretipodoc, iddocumento, nrodocumento, idsocionegocio, fecdoc, fecvenc, idmonedaorigen," +
					"nombremonedaorigen, imporigen, impafecta, isinvoice, issotrx, keyafectacion) "; 

			// ID de socio de negocio segun filtros indicados por usuario
			if (this.esCliente){
				isSOTRX = "Y";
			}
			else{
				isSOTRX = "N";
			}

			// Condiciones segun filtros
			if (idSocioNegocio>=0) whereFiltros = " AND a.C_BPartner_ID=" + idSocioNegocio;
			if (idTipoDocumento>=0) whereFiltros += " AND a.C_DocType_ID=" + idTipoDocumento;
			if (!nroDocumento.equalsIgnoreCase("")) whereFiltros += " AND a.DocumentNo='" + nroDocumento + "'";
			
			// Debo mostrar todas las afectaciones en las que aparecen un comprobante. Cada afectacion se muestra completa asi tenga mas comprobantes en ella.

			// Obtengo padres
			sqlInvoice = "SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date, a.c_doctype_id, b.docbasetype, b.printname, a.c_invoice_id, " +
							"a.documentno, a.C_BPartner_ID, a.dateinvoiced, paymenttermduedate(a.c_paymentterm_id, a.dateinvoiced::timestamp with time zone) AS fechavencimiento, " +
							"a.c_currency_id, curr.description, a.grandtotal, 0, 'Y', '" + isSOTRX +  "'," +
							"(a.c_invoice_id || '_' || a.c_doctype_id) as keyafecta " +
						  " FROM C_Invoice a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
						  	"INNER JOIN C_Currency curr ON a.c_currency_id = curr.c_currency_id " +
						  " WHERE a.ad_client_id =" + this.idEmpresa +
						  " AND a.dateinvoiced between '" + this.fechaDesde + "' AND '" + this.fechaHasta + "' " +
						  " AND a.docstatus IN('CO','CL','RE','VO') " +
						  " AND a.processed = 'Y' " +
						  whereFiltros +
						  " AND b.issotrx='" + isSOTRX + "'";
		
			sqlPayment = " UNION " + 
						"SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date, a.c_doctype_id, b.docbasetype, b.printname, a.c_payment_id, " +
						"a.documentno, a.C_BPartner_ID, a.datetrx, a.datetrx, " +
						"a.c_currency_id, curr.description, a.payamt, 0, 'N', '" + isSOTRX +  "'," +
						"(a.c_payment_id || '_' || a.c_doctype_id) as keyafecta " +
						" FROM C_Payment a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
					  		"INNER JOIN C_Currency curr ON a.c_currency_id = curr.c_currency_id " +
					  	" WHERE a.ad_client_id =" + this.idEmpresa +
					  	" AND a.datetrx between '" + this.fechaDesde + "' AND '" + this.fechaHasta + "' " +
					  	" AND a.docstatus IN('CO','CL','RE','VO') " +
					  	" AND a.processed = 'Y' " +
					  	whereFiltros +
					  	" AND b.issotrx='" + isSOTRX + "'";			 
			
			log.info(insert + sqlInvoice + sqlPayment);
			
			DB.executeUpdate(insert + sqlInvoice + sqlPayment, null);
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sqlInvoice + sqlPayment, e);
		}
	}

	
	private void calculoAfectaciones(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			// Obtengo registros de la temporal de padres
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE_PADRES +
				  " WHERE idreporte=?" +
				  " ORDER BY idsocionegocio, fecdoc, iddocumento";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();

			// Recorro padres, buscando sus afectaciones y grabando las mismas en la tabla molde de hijos
			while (rs.next()){
				
				// Obtengo id de afectaciones para este comprobante padre
				boolean isInvoice = (rs.getString("isinvoice").equalsIgnoreCase("Y")) ? true : false;
				List<Integer> idAfectaciones = this.getIDAfectacionesHijos(isInvoice, rs.getInt("iddocumento"));
				
				for (int i=0;i<idAfectaciones.size();i++){
					this.processAfectacionHijo(isInvoice, idAfectaciones.get(i) ,rs.getInt("iddocumento"), rs.getString("keyafectacion"));
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

	private void processAfectacionHijo(boolean isInvoice, Integer idAfectacion, int idDocumento, String keyAfectacion) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			// Obtengo lineas de afectacion
			sql = "SELECT a.*, b.description as nombremoneda " +
				  " FROM RV_Allocation a INNER JOIN C_Currency b ON a.c_currency_id = b.c_currency_id " +  
			      " WHERE a.c_allocationhdr_id = " + idAfectacion;
			      
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			// Recorro lineas
			while (rs.next()){
				MDocType doc = null;
				String nroDocumento = "";
				BigDecimal montoOrigen = Env.ZERO;
				Timestamp fecDoc = null, fecVenc = null;
				int idDocHijo = 0;
				
				// Si el padre es una factura
				if (isInvoice){
					
					// Si tengo Pago como afectador
					if (rs.getInt("c_payment_id")>0){
						idDocHijo = rs.getInt("c_payment_id");
						MPayment payment = new MPayment(getCtx(), idDocHijo, null);
						nroDocumento = payment.getDocumentNo();
						fecDoc = payment.getDateTrx();
						fecVenc = fecDoc;
						montoOrigen = payment.getPayAmt();
						doc = new MDocType(getCtx(), payment.getC_DocType_ID(), null);
					}
					// Es nota de credito
					else if (rs.getInt("c_invoice_id")>0){
						idDocHijo = rs.getInt("c_invoice_id");
						MInvoice invoice = new MInvoice(getCtx(), idDocHijo, null);
						nroDocumento = invoice.getDocumentNo();
						fecDoc = invoice.getDateInvoiced();
						fecVenc = fecDoc;
						montoOrigen = invoice.getGrandTotal();
						doc = new MDocType(getCtx(), invoice.getC_DocType_ID(), null);
					}
				}
				else{
					idDocHijo = rs.getInt("c_invoice_id");
					MInvoice invoice = new MInvoice(getCtx(), idDocHijo, null);
					nroDocumento = invoice.getDocumentNo();
					fecDoc = invoice.getDateInvoiced();
					fecVenc = fecDoc;
					montoOrigen = invoice.getGrandTotal();
					doc = new MDocType(getCtx(), invoice.getC_DocType_ID(), null);
				}
			
				// Monto
				BigDecimal monto = rs.getBigDecimal("amount");
				if (monto==null) monto = Env.ZERO;
				else if (monto.doubleValue()<0) monto = monto.multiply(new BigDecimal(-1));
				
				// Insert en temporal de afectaciones hijos
				String insert = "INSERT INTO " + TABLA_MOLDE_HIJOS + " (idreporte, idusuario, fecreporte, idallocationhdr," +
						"idallocationline, idtipodoc, docbasetype, nombretipodoc, iddocumento, nrodocumento, idsocionegocio," +
						"fecdoc, fecvenc, fecafecta, idmonedaorigen, nombremonedaorigen, imporigen, impafecta, keyafectacion) ";
				String values = " VALUES('" + this.idReporte + "'," + this.idUsuario + ",current_date," + idAfectacion + "," +
						rs.getInt("c_allocationline_id") + "," + doc.getC_DocType_ID() + ",'" + doc.getDocBaseType() + "','" +
						doc.getPrintName() + "'," + idDocHijo + ",'" + nroDocumento + "'," + rs.getInt("c_bpartner_id") + ",'" +
						fecDoc + "','" + fecVenc + "','" + rs.getTimestamp("DateTrx") + "'," + rs.getInt("c_currency_id") + ",'" +
						rs.getString("nombremoneda") + "'," + montoOrigen + "," + monto + ",'" + keyAfectacion + "')"; 
				
				log.log(Level.INFO, insert + values);
				DB.executeUpdate(insert + values, null);
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


	private List<Integer> getIDAfectacionesHijos(boolean isInvoice, int idDocumento){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<Integer> lista = new ArrayList<Integer>();
		
		try{

			// Filtro de documento segun sea invoice o payment
			String condiciones = "";
			if (isInvoice){
				condiciones =" AND a.C_Invoice_ID =" + idDocumento;
			}
			else {
				condiciones =" AND a.C_Payment_ID =" + idDocumento;
			}
			
			// Obtengo todos los ID de afectacion donde aparece este comprobante padre
			sql = "SELECT DISTINCT a.c_allocationhdr_id " +
				  " FROM RV_Allocation a INNER JOIN C_AllocationHdr b ON a.c_allocationhdr_id = b.c_allocationhdr_id " +  
			      " WHERE b.DocStatus IN ('CL','CO','RE','VO') " + condiciones;
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			// Recorro ids obtenidos
			while (rs.next()){
				lista.add(rs.getInt(1));
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
	
		return lista;
	}
	
}
