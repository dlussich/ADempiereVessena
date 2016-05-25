/**
 * RProductRotacion.java
 * 28/02/2011
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * OpenUp. RProductRotacion Descripcion : Para issue319
 * 
 * @author Nicolas Garcia Fecha : 28/02/2011
 */
// org.openup.process.RProductRotacion
public class RProductRotacion extends SvrProcess {
	private Timestamp fechaHasta = null;
	private Timestamp fechaDesde = null;

	private int idEmpresa = 0;
	private Long idUsuario = new Long(0);
	private int idOrganizacion = 0;

	private String idReporte = "";
	private int idAlmacen = 0;
	private int idProducto = 0;
	private String MovementType = "";
	private int idProductoCategory=0;
	private static final String TABLA_MOLDE = "UY_Model_RotacionStock";

	@Override
	protected String doIt() throws Exception {
		// Delete de reportes anteriores de este usuario para ir limpiando la
		// tabla molde
		deleteInstanciasViejasReporte();
		// Obtengo y cargo en tabla molde, los comprobantes a procesar segun
		// filtros ingresados por el usuario
		this.loadComprobantes();

		return "ok";
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
				if (name.equalsIgnoreCase("fecreporte")) {
					if (para[i].getParameter() != null) {
					this.fechaDesde = (Timestamp) para[i].getParameter();
					this.fechaHasta = (Timestamp) para[i].getParameter_To();
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
				if (name.equalsIgnoreCase("M_Warehouse_ID")) {
					if (para[i].getParameter() != null) {
					this.idAlmacen = ((BigDecimal) para[i].getParameter())
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
				if (name.equalsIgnoreCase("M_Product_Category_ID")) {
					if (para[i].getParameter() != null) {
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")) {
							this.idProductoCategory = ((BigDecimal) para[i].getParameter()).intValueExact();
						}
					}
				}
				if (name.equalsIgnoreCase("text")) {
					if (para[i].getParameter() != null) {
					this.MovementType = (String) para[i].getParameter();
					}
				}
			}
			
		}
		// set el id del reporte con el igual al id del usuario
		this.idReporte = UtilReportes.getReportID(this.idUsuario);

	}

	private void deleteInstanciasViejasReporte() {

		String sql = "";
		try {

			sql = "DELETE FROM " + TABLA_MOLDE + " WHERE idusuario ="
					+ this.idUsuario;

			DB.executeUpdate(sql, null);
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		}
	}

	private void loadComprobantes() {

		String insert = "";
		String where = "";
		String sql = "";
		try {
			insert = "INSERT INTO "
					+ TABLA_MOLDE
					+ " (idreporte, M_Product_Category_ID, AD_User_ID, idusuario,fecreporte, ad_org_id, ad_client_id, m_product_id, m_warehouse_id, text, c_uom_id, suma, "
					+ "cantidad) ";
			where = " WHERE m_transaction.ad_org_id =" + this.idOrganizacion
					+ " and m_transaction.ad_client_id =" + this.idEmpresa;

			if (fechaHasta != null) {
				where += " and m_transaction.movementdate <='" + fechaHasta
						+ "' ";
			}
			if (fechaDesde != null) {
				where += " and m_transaction.movementdate >='" + fechaDesde
						+ "' ";
			}
			if (idAlmacen > 0) {
				where += " and m_locator.m_warehouse_id  =" + idAlmacen;
			}
			if (idProducto > 0) {
				where += " and m_transaction.M_Product_ID=" + idProducto;
			}
			if (idProductoCategory > 0) {
				where += " and M_Product.M_Product_Category_ID=" + idProductoCategory;
			}
			if (!MovementType.equalsIgnoreCase("")) {
				where += " and m_transaction.MovementType ='" + MovementType
						+ "'";
			}
			

			// Armo SQL
			sql = "(SELECT '"
					+ this.idReporte
					+ "',"
					+"m_product.m_product_category_id, "
					+ this.idUsuario
					+ ","
					+ this.idUsuario
					+ ",current_date, "
					+ "m_transaction.ad_org_id,m_transaction.ad_client_id,"
					+ "m_transaction.m_product_id,m_locator.m_warehouse_id ,m_transaction.movementtype,"
					+ "m_product.c_uom_id ,sum(m_transaction.movementqty),"
					+ "count(m_transaction.m_product_id)"
					+ "from m_transaction"
					+ " left join m_product on m_product.m_product_id=m_transaction.m_product_id"
					+ " left join m_locator on m_locator.m_locator_id=m_transaction.m_locator_id"
					+ where
					+ " group by m_transaction.m_product_id ,m_product.c_uom_id,m_locator.m_warehouse_id ,m_transaction.movementtype ,m_transaction.ad_org_id,"
					+ "m_transaction.ad_client_id, m_product.m_product_category_id"
					+ " order by m_product_id asc)";

			insert += sql;
			log.info(insert);

			DB.executeUpdate(insert, null);

		} catch (Exception e) {
			log.log(Level.SEVERE, insert + sql, e);
		}
	}
}
