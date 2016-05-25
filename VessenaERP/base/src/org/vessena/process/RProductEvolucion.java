/**
 * RProductEvolucion.java
 * 28/02/2011
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * OpenUp. RProductEvolucion Descripcion :  issue#363
 * 
 * @author Mario Reyes Fecha : 09/03/2011
 */
// org.openup.process.RProductEvolucion
public class RProductEvolucion extends SvrProcess {
	
	private int idProducto = 0;
	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	private int idFamilia = 0;
	private int idSubFamilia=0;
	private int idTipoProd=0;
	
	private int cPeriodID_Desde = 0;
	private int cPeriodID_Hasta = 0;
	
	private int idEmpresa = 0;
	private Long idUsuario = new Long(0);
	private int idOrganizacion = 0;
	private String idReporte = "";
		
	private static final String TABLA_MOLDE = "UY_Molde_ProdEvol";

	@Override
	protected String doIt() throws Exception {
		// Delete de reportes anteriores de este usuario para ir limpiando la
		// tabla molde
		deleteInstanciasViejasReporte();
		
		this.validoRangoFechaPeriodos();
		
		// Obtengo y cargo en tabla molde, segun
		// filtros ingresados por el usuario
		this.loadComprobantes();

		// Calcular netos por mes
		this.calculoNetoXMes();
		
		return "ok";
	}

	private void validoRangoFechaPeriodos() throws Exception {

		MPeriod periodoDesde = new MPeriod(getCtx(), this.cPeriodID_Desde, null);
		MPeriod periodoHasta = new MPeriod(getCtx(), this.cPeriodID_Hasta, null);
		
		if (periodoDesde == null) throw new Exception("Periodo Desde no es Valido.");
		if (periodoHasta == null) throw new Exception("Periodo Hasta no es Valido.");
		
		if (periodoHasta.getEndDate().before(periodoDesde.getStartDate()))
			throw new Exception("Periodo Hasta Debe ser MAYOR que el Periodo Desde.");
	
		if ((this.cPeriodID_Hasta - this.cPeriodID_Desde) > 12)
			throw new Exception("El periodo no puede ser mayor a un año.");
		
		this.fechaDesde = periodoDesde.getStartDate();
		this.fechaHasta = periodoHasta.getEndDate();
		
	}

	/**
	 * OpenUp. Calcula neto de ventas por mes.
	 * 
	 * Kacike 10/03/2011
	 * @throws Exception 
	 */
	private void calculoNetoXMes() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			// Obtengo id, fechas, etc., de periodos a considerar
			sql ="SELECT c_period_id, startdate, enddate " + 
 		  	" FROM c_period " +
 		  	" WHERE c_period_id >= " + this.cPeriodID_Desde + 
 		  	" AND c_period_id <= " + this.cPeriodID_Hasta +
 		  	" ORDER BY c_period_id ";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
		
			// Recorro periodos ordenados
			int contadorMes = 0;
			while (rs.next()){
				contadorMes++;
				
				String update = " UPDATE " + TABLA_MOLDE +
				" SET mes" + contadorMes + "=" + 
								" (SELECT coalesce(SUM(invline.linenetamt),0) " +
								" - " +
										" (SELECT coalesce(SUM(invline.linenetamt),0) " +
										" FROM c_invoiceline invline " +
										" INNER JOIN c_invoice invhdr ON invline.c_invoice_id = invhdr.c_invoice_id " +
										" INNER JOIN c_doctype doc ON invhdr.c_doctype_id = doc.c_doctype_id " +
										" WHERE invline.m_product_id = " + TABLA_MOLDE + ".m_product_id " +
										" AND invhdr.ad_client_id =" + this.idEmpresa +
										" AND invhdr.dateinvoiced BETWEEN '" + rs.getTimestamp("startdate") + "'" +
										" AND '" + rs.getTimestamp("enddate") + "'" +
										" AND invhdr.issotrx='Y' " +
										" AND invhdr.docstatus IN('CO','CL') " +
										" AND doc.docbasetype = 'ARC') " +
								" FROM c_invoiceline invline " +
								" INNER JOIN c_invoice invhdr ON invline.c_invoice_id = invhdr.c_invoice_id " +
								" INNER JOIN c_doctype doc ON invhdr.c_doctype_id = doc.c_doctype_id " +
								" WHERE invline.m_product_id = " + TABLA_MOLDE + ".m_product_id " +
								" AND invhdr.ad_client_id =" + this.idEmpresa +
								" AND invhdr.dateinvoiced BETWEEN '" + rs.getTimestamp("startdate") + "'" +
								" AND '" + rs.getTimestamp("enddate") + "'" +
								" AND invhdr.issotrx='Y' " +
								" AND invhdr.docstatus IN('CO','CL') " +
								" AND doc.docbasetype = 'ARI')" +
				" WHERE idReporte='" + this.idReporte + "'";

				DB.executeUpdate(update, null);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
	}

	@Override
	protected void prepare() {

		// Parametro para id de reporte
		//ProcessInfoParameter paramIDReporte = null;
		// Parametro para titulo del reporte
		//ProcessInfoParameter paramTituloReporte = null;

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("C_Period_ID")) {
					if (para[i].getParameter() != null) {
						this.cPeriodID_Desde = ((BigDecimal) para[i].getParameter()).intValueExact();
						this.cPeriodID_Hasta = ((BigDecimal) para[i].getParameter_To()).intValueExact();
					}
				}

				if (name.equalsIgnoreCase("AD_User_ID")) {
					this.idUsuario = ((BigDecimal) para[i].getParameter())
							.longValueExact();
				}
				if (name.equalsIgnoreCase("AD_Org_ID")) {
					this.idOrganizacion = ((BigDecimal) para[i].getParameter())
							.intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")) {
					this.idEmpresa = ((BigDecimal) para[i].getParameter())
							.intValueExact();
				}
				if (name.equalsIgnoreCase("UY_Familia_ID")) {
					if (para[i].getParameter() != null) {
					this.idFamilia = ((BigDecimal) para[i].getParameter())
							.intValueExact();
					}
				}
				
				if (name.equalsIgnoreCase("M_Product_ID")) {
					if (para[i].getParameter() != null) {
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")) {
							this.idProducto = ((BigDecimal) para[i].getParameter()).intValueExact();
						}
					}
				}
				if (name.equalsIgnoreCase("UY_SubFamilia_ID")) {
					if (para[i].getParameter() != null) {
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")) {
							this.idSubFamilia = ((BigDecimal) para[i].getParameter()).intValueExact();
						}
					}
				}
				if (name.equalsIgnoreCase("UY_TipoProducto_ID")) {
					if (para[i].getParameter() != null) {
					this.idTipoProd = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}
			}
			
		}
		// set el id del reporte con el igual al id del usuario
		this.idReporte = UtilReportes.getReportID(this.idUsuario);

	}

	private void deleteInstanciasViejasReporte() throws Exception {

		String sql = "";
		try {

			sql = "DELETE FROM " + TABLA_MOLDE + " WHERE ad_user_id ="
					+ this.idUsuario;

			DB.executeUpdate(sql, null);
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
			throw e;
		}
	}

	private void loadComprobantes() throws Exception {

		String insert = "";
		String where = "";
		String sql = "";
		try {
			insert = "INSERT INTO "
					+ TABLA_MOLDE
					+ " (ad_client_id, ad_org_id, idreporte, ad_user_id, uy_familia_id, uy_subfamilia_id, uy_tipoproducto_id, m_product_id, totalprod, c_period_id) ";
						
						
			where = " WHERE a.ad_org_id =" + this.idOrganizacion
					+ " and a.ad_client_id =" + this.idEmpresa;

			if (fechaDesde != null) {
				where += " and a.dateinvoiced >='" + fechaDesde + "' ";
			}
			if (fechaHasta != null) {
				where += " and a.dateinvoiced <='" + fechaHasta + "' ";
			}
			if (idFamilia > 0) {
				where += " and c.uy_familia_id  =" + idFamilia;
			}
			if (idSubFamilia > 0) {
				where += " and c.uy_subfamilia_id=" + idSubFamilia;
			}
			if (idTipoProd > 0) {
				where += " and c.uy_tipoproducto_id =" + idTipoProd;
			}
			if (idProducto > 0) {
				where += " and b.M_Product_ID =" + idProducto;
			}
			
			// Armo SQL
			sql = " SELECT a.ad_client_id, a.ad_org_id, " +	"'"	+ this.idReporte + "',"	+ this.idUsuario + ","
					+ " c.uy_familia_id, c.uy_subfamilia_id, c.uy_tipoproducto_id, b.m_product_id, SUM(linenetamt), " + this.cPeriodID_Desde 
					+ " from c_invoice a "
					+ " inner join c_invoiceline b on a.c_invoice_id=b.c_invoice_id"
					+ " inner join m_product c  on b.m_product_id=c.m_product_id"
					+ where 
					+ " AND a.issotrx='Y' " 
					+ " AND a.docstatus IN('CO','CL') "  
					+ " group by a.ad_client_id, a.ad_org_id, " 
					+ " c.uy_familia_id, c.uy_subfamilia_id, c.uy_tipoproducto_id, b.m_product_id ";

			insert += sql;
			log.info(insert);

			DB.executeUpdate(insert, null);

		} catch (Exception e) {
			log.log(Level.SEVERE, insert, e);
			throw e;
		}
	}
}
