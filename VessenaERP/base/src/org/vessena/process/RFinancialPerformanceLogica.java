package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.MPayment;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.openup.model.MMediosPago;

// OpenUp. Nicolas Garcia. 14/09/2011. Issue #698. Creado para este caso
public class RFinancialPerformanceLogica {
	
	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	private int idCliente = 0;

	private int idEmpresa = 0;
	private int idOrganizacion = 0;
	private int idUsuario = 0;
	private CLogger log = null;
	private String trx = "";
	
	private static final String TABLA_MOLDE = "UY_Molde_FinancialPerformance";

	/**
	 * 
	 */
	public RFinancialPerformanceLogica() {
		// TODO Auto-generated constructor stub
	}
	
	public RFinancialPerformanceLogica(CLogger log, Timestamp fechaDesde, Timestamp fechaHasta, int idCliente, int idEmpresa, int idOrganizacion,
			int idUsuario, String trx) {

		this.log = log;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.idCliente = idCliente;
		this.idEmpresa = idEmpresa;
		this.idOrganizacion = idOrganizacion;
		this.idUsuario = idUsuario;
		this.trx = trx;

	}
	
	public String loadModelTable() {
		String salida = "";
		// Delete de reportes anteriores de este usuario para ir limpiando la
		// tabla molde
		this.deleteInstanciasViejasReporte();

		// Obtengo y cargo en tabla molde, los movimientos segun filtro indicado
		// por el usuario.
		salida = this.loadMovimientos();

		return salida;
	}
	
	private void deleteInstanciasViejasReporte() {

		String sql = "";
		try {

			sql = "DELETE FROM " + TABLA_MOLDE + " WHERE createdby =" + this.idUsuario;

			DB.executeUpdate(sql, null);
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	private String loadMovimientos() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = "select i.c_invoice_id,i.c_bpartner_id,b.name,i.c_paymentterm_id,i.dateinvoiced,l.tendertype,p.c_payment_id,l.datetrx,l.payamt,l.uy_mediospago_id," +
                  " (SELECT date_part('day',(paymenttermduedate(i.c_paymentterm_id,i.dateinvoiced)- i.dateinvoiced))) as diasPlazo," +
                  " date_part('day',(l.datetrx - i.dateinvoiced)) as diasCobro" +
                  " from c_invoice i" +
                  " inner join c_bpartner b on i.c_bpartner_id = b.c_bpartner_id" +
                  " inner join uy_allocationinvoice ai on i.c_invoice_id = ai.c_invoice_id" +
                  " inner join uy_allocation a on ai.uy_allocation_id = a.uy_allocation_id" +
                  " inner join uy_allocationpayment ap on a.uy_allocation_id = ap.uy_allocation_id" +
                  " inner join c_payment p on ap.c_payment_id = p.c_payment_id" +
                  " inner join uy_linepayment l on p.c_payment_id = l.c_payment_id" + 
                  " where i.docstatus = 'CO' and p.docstatus = 'CO' and a.docstatus = 'CO' and i.issotrx='Y' and p.isreceipt='Y'" + this.getWhere() +
                  " order by i.C_bpartner_id,i.c_invoice_id,l.datetrx";
			
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			
			int curInvoiceID = 0;
			int curBPartnerID = 0;
			String partnerName = "";
			int curPaymentID = 0;
			Timestamp fechaFacturacion = null;
			int diasPlazo = 0;
			int diasCobroVencido = 0;

			ArrayList<Integer> mediosPago = new ArrayList<Integer>();
			
			while (rs.next()) {
				
				// Corte por ID de factura
				// Si cambio, guardo y reseteo acumulados
				if (rs.getInt("c_invoice_id") != curInvoiceID) {

					String error = saveLine(curInvoiceID, curBPartnerID, partnerName, fechaFacturacion, diasPlazo, diasCobroVencido, mediosPago, curPaymentID);

					if (!error.equalsIgnoreCase("")) {
						throw new Exception(error);
					}

					curInvoiceID = rs.getInt("c_invoice_id");
					curBPartnerID = rs.getInt("C_bpartner_id");
					partnerName = rs.getString("name");

					// Se toma el primero como referencia
					curPaymentID = rs.getInt("c_payment_id");

					fechaFacturacion = rs.getTimestamp("dateinvoiced");
					diasPlazo = rs.getInt("diasPlazo");

					diasCobroVencido = rs.getInt("diasCobro");

					// Vacio lista.
					mediosPago.clear();

				}

				// Instancio el tipo de pago
				String tendertype = rs.getString("tendertype");

				if (tendertype != null) mediosPago.add(rs.getInt("uy_mediosPago_id"));
								
				if(rs.isLast()){
					
					String error = saveLine(curInvoiceID, curBPartnerID, partnerName, fechaFacturacion, diasPlazo, diasCobroVencido, mediosPago, curPaymentID);

					if (!error.equalsIgnoreCase("")) {
						throw new Exception(error);
					}				
					
				}
			}			

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
			Trx trans = Trx.get(trx, true);
			trans.rollback();
			return e.getMessage();
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return "";
	}
	
	private String getWhere() {

		String salida = " AND i.ad_org_id = " + idOrganizacion + " AND i.ad_client_id = " + idEmpresa;

		// Solo facturas cliente
		salida += " AND i.c_doctype_id in (SELECT c_doctype_id FROM c_doctype WHERE docbasetype ='ARI') ";

		if (fechaDesde != null) salida += " AND i.dateinvoiced >= '" + fechaDesde + "' ";

		if (fechaHasta != null) salida += " AND i.dateinvoiced <= '" + fechaHasta + "' ";

		if (idCliente > 0) salida += " AND i.C_bpartner_id = " + idCliente;

		return salida;
	}
	
	private String saveLine(int curInvoiceID, int curBPartnerID, String partnerName, Timestamp fechaFacturacion, int diasPlazo, int diasCobroVencido,
			ArrayList<Integer> arrayMediosPago, int curPaymentID) throws Exception {
		try {
			// Si existe la factura
			if (curInvoiceID > 0) {
				
				int desvioMediosPago = 0;
				int diasVenMedioPagos = 0;				

				String campos = " ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, c_bpartner_id, name, DateInvoiced,c_invoice_id, diasPlazo, diasCobro";
				String values = "" + idEmpresa + "," + idOrganizacion + ",'Y',NOW()," + idUsuario + ",NOW()," + idUsuario + "," + curBPartnerID + ",'" + partnerName + "','"
						+ fechaFacturacion + "'," + +curInvoiceID + "," + diasPlazo + "," + diasCobroVencido;

				ArrayList<MMediosPago> mediosPagos = this.getMediosPagos(arrayMediosPago);
				if (curPaymentID > 0) {
					campos += ", c_payment_id";
					values += "," + curPaymentID;
				}

				// Si tiene medios pagos
				if (mediosPagos.size() > 0) {

					diasVenMedioPagos = this.getDiasVenMedioPagos(mediosPagos, fechaFacturacion);				
					
				} else {
					
					if (curPaymentID > 0) {
						
						MPayment pay = new MPayment(Env.getCtx(),curPaymentID,null);
						
						String sql = "SELECT date_part('day',(('" + pay.getDateTrx() + "'::timestamp)-('" + fechaFacturacion + "'::timestamp)))";
						diasVenMedioPagos = DB.getSQLValueEx(null, sql);							
						
					}					
				}

				// Cantidad dias que el medio pago se vencia desde la fecha
				// de facturacion - el vencimiento de la factura
				desvioMediosPago = diasVenMedioPagos - diasPlazo;

				// para un arreglo se uso esto que fue lo mas facil de
				// resolver
				Timestamp vencimientoPromCehques = this.getDiasVenMedioPagos(fechaFacturacion, diasVenMedioPagos);

				campos += ", FechaVencProm, diasVenMedioPagos, desvioMediosPago";
				values += ",'" + vencimientoPromCehques + "'" + "," + diasVenMedioPagos + "," + desvioMediosPago;
				

				String sql = "INSERT INTO " + TABLA_MOLDE + " (" + campos + ")VALUES (" + values + ")";

				int error = DB.executeUpdate(sql, trx);

				if (error < 0) {
					return "Error:01 Ponganse en contacto con el administrador del sistema";
				}

			}
		} catch (Exception e) {
			throw e;
		}
		return "";
	}

	private Timestamp getDiasVenMedioPagos(Timestamp fechaFacturacion, int diasVenMedioPagos) {

		Long fecha = (fechaFacturacion.getTime() + (86400000 * new Long(diasVenMedioPagos)));
		return new Timestamp(fecha);
	}

	private int getDiasVenMedioPagos(ArrayList<MMediosPago> mediosPagos, Timestamp fechaFacturacion) throws Exception {

		BigDecimal suma = Env.ZERO;
		String sql = "";

		try {

			for (MMediosPago mp : mediosPagos) {
				// Resto a la fecha de vencimiento del cheque la fecha de
				// facturacion, esto me da a cuantos dias de facturado se vence
				// el cheque.
				sql = "SELECT date_part('day',(('" + mp.getDueDate() + "'::timestamp)-('" + fechaFacturacion + "'::timestamp)))";
				suma = suma.add(new BigDecimal(DB.getSQLValue(null, sql)));

			}

			if (mediosPagos.size() > 0) { // Defensivo
				return suma.divide(new BigDecimal(mediosPagos.size()), 0).intValue();
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
			throw e;
		}
		return 0;
	}

	private ArrayList<MMediosPago> getMediosPagos(ArrayList<Integer> arrayMediosPago) {

		// Acumulador
		ArrayList<MMediosPago> salida = new ArrayList<MMediosPago>();

		for (Integer idMediosPagos : arrayMediosPago) {

			MMediosPago mp = new MMediosPago(Env.getCtx(), idMediosPagos, null);

			// Si el cheque existe en la base de datos lo guardo
			if (mp.get_ID() > 0) salida.add(mp);

		}

		return salida;
	}


}
