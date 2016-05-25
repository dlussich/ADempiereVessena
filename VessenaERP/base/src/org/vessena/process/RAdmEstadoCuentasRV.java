/**
 * RAdmEstadoCuentas.java 22/09/2010
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
public class RAdmEstadoCuentasRV extends SvrProcess {

	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;

	private boolean esCliente = true;
	private String esClienteString = "";
	private int idMoneda = 0;
	private int idCliente = -1;
	private int idProveedor = -1;
	private int idEmpresa = 0;
	private int idOrganizacion = 0;
	private Long idUsuario = new Long(0);
	// private String nombreEmpresa = "";
	// private String nombreUsuario = "";
	private String idReporte = "";

	private static final String CTA_CORRIENTE_CLIENTE = "CL";
	// private static final String CTA_CORRIENTE_PROVEEDOR = "PR";

	private static final String TABLA_MOLDE = "UY_MOLDE_RAdmEstadoCuentas";

	/**
	 * Constructor
	 */
	public RAdmEstadoCuentasRV() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
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
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("idReporte")) {
					paramIDReporte = para[i];
				}
				if (name.equalsIgnoreCase("StartDate")) {
					paramStartDate = para[i];
				}
				if (name.equalsIgnoreCase("EndDate")) {
					paramEndDate = para[i];
				}
				if (name.equalsIgnoreCase("tituloReporte")) {
					paramTituloReporte = para[i];
				}
				if (name.equalsIgnoreCase("fecdoc")) {
					this.fechaDesde = (Timestamp) para[i].getParameter();
					this.fechaHasta = (Timestamp) para[i].getParameter_To();
					para[i].setParameter(this.fechaDesde);
					para[i].setParameter_To(this.fechaHasta);
				}
				/*
				 * if (name.equalsIgnoreCase("usuarioReporte")){
				 * this.nombreUsuario = (String)para[i].getParameter(); }
				 */
				if (name.equalsIgnoreCase("AD_User_ID")) {
					this.idUsuario = ((BigDecimal) para[i].getParameter()).longValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")) {
					this.idEmpresa = ((BigDecimal) para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("AD_Org_ID")) {
					this.idOrganizacion = ((BigDecimal) para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("C_Currency_ID")) {
					if (para[i].getParameter() != null) {
						paramMonedaReporte = para[i];
						this.idMoneda = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}

				if (name.equalsIgnoreCase("esCliente")) {
					this.esCliente = (para[i].getParameter().toString().equalsIgnoreCase(CTA_CORRIENTE_CLIENTE)) ? true : false;
					this.esClienteString = para[i].getParameter().toString();
				}

				if (name.equalsIgnoreCase("C_BPartner_ID")) {
					if (para[i].getParameter() != null) {
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")) {
							this.idCliente = ((BigDecimal) para[i].getParameter()).intValueExact();
						}
					}
				}
				if (name.equalsIgnoreCase("C_BPartner_ID_P")) {
					if (para[i].getParameter() != null) {
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")) {
							this.idProveedor = ((BigDecimal) para[i].getParameter()).intValueExact();
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
		else
			tituloReporte = "Estado de Cuenta - Proveedores";

		// Si tengo parametro para titulo de reporte
		if (paramTituloReporte != null) paramTituloReporte.setParameter(tituloReporte);

		// Si tengo parametro para idreporte
		if (paramIDReporte != null) paramIDReporte.setParameter(this.idReporte);

		// Si tengo parametros de fechas para mostrar en el reporte
		if (paramStartDate != null) paramStartDate.setParameter(this.fechaDesde);
		if (paramEndDate != null) paramEndDate.setParameter(this.fechaHasta);

		// Seteo parametro moneda del reporte enviando la descripcion de la
		// moneda seleccionada por el usuario
		if (paramMonedaReporte != null) paramMonedaReporte.setParameter(this.getDescripcionMoneda(this.idMoneda));

	}

	/*
	 * (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		RAdmEstadoCuentasLogica logica = new RAdmEstadoCuentasLogica(fechaDesde, fechaHasta, esCliente, idMoneda, idProveedor, idEmpresa, idOrganizacion,
				idUsuario, idReporte, log, get_TrxName(), idCliente);

		String salida = logica.procesar();

		adaptacionARV();
		return salida;

	}

	private void adaptacionARV() {

		DB.executeUpdate("UPDATE " + TABLA_MOLDE + " Set esCliente='" + esClienteString + "', ad_org_id=" + idOrganizacion + ", ad_client_id=" + idEmpresa
				+ ",c_currency_ID=" + idMoneda + ",AD_User_ID=" + idUsuario
				+ ",c_bpartner_id=idsocionegocio, c_bpartner_id_p=idsocionegocio   WHERE idReporte='" + idReporte + "'",
 null);

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = "SELECT c_bpartner.c_bpartner_id,c_bpartner.value,c_bpartner.name2 FROM uy_molde_radmestadocuentas "
					+ " JOIN c_bpartner ON c_bpartner.c_bpartner_id= uy_molde_radmestadocuentas.c_bpartner_id "
 + " WHERE idReporte='" + idReporte
					+ "' GROUP BY c_bpartner.c_bpartner_id,c_bpartner.value,c_bpartner.name2 ";

			pstmt = DB.prepareStatement(sql,null);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				DB.executeUpdate("UPDATE " + TABLA_MOLDE + " Set value='" + rs.getString("value") + "', name='" + rs.getString("name2") + "' WHERE idReporte='"
						+ idReporte + "' AND c_bpartner_id=" + rs.getInt("c_bpartner_id"), null);

			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	private String getDescripcionMoneda(int idMoneda) {
		MCurrency model = new MCurrency(getCtx(), idMoneda, get_TrxName());
		return model.getDescription();
	}
}