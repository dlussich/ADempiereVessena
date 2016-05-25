package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.ProdImplosion;

public class RPlanifImplosion extends SvrProcess {
	
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;
	private int mProductID = 0;
	private int mProductCatID = 0;
	private int insumoID = 0;
	private String insumoNom = "";
	private String insumoUM = "";
	private String nivel = "";
	private int nivelInt = -1;

	private String idReporte = "";
	private static final String TABLA_MOLDE = "UY_Molde_Implosion"; //tabla principal del reporte
	private static final String TABLA_MOLDE_AUX = "UY_Molde_Implosion_Aux"; //tabla molde auxiliar

	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando las
		// tablas moldes
		this.deleteInstanciasViejasReporte();

		// Cargo informacion a reportear en tabla molde principal
		this.getData();
	
		// Retorno con exito.
		return "ok";
	}

	/**
	 * Delete de registros de instancias viejas de este reporte para el usuario actual
	 */
	private void deleteInstanciasViejasReporte() {
		String sql = "";
		try {
			sql = " DELETE FROM " + TABLA_MOLDE + " WHERE ad_user_id =" + this.adUserID + ";";
			sql += " DELETE FROM " + TABLA_MOLDE_AUX + " WHERE ad_user_id =" + this.adUserID + ";";
			DB.executeUpdateEx(sql, get_TrxName());
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	@Override
	protected void prepare() {
		
		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("idReporte")) {
					paramIDReporte = para[i];
				}

				if (name.equalsIgnoreCase("M_Product_ID")) {
					if (para[i].getParameter() != null) {
						this.mProductID = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}
				
				if (name.equalsIgnoreCase("Ad_Client_ID")) {
					if (para[i].getParameter() != null) {
						this.adClientID = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}

				if (name.equalsIgnoreCase("Ad_Org_ID")) {
					if (para[i].getParameter() != null) {
						this.adOrgID = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}
				
				if (name.equalsIgnoreCase("M_Product_Category_ID")) {
					if (para[i].getParameter() != null) {
						this.mProductCatID = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}
				
				if (name.equalsIgnoreCase("nivel")) {
					if (para[i].getParameter() != null) {
						this.nivel = ((String) para[i].getParameter());
						this.nivelInt = Integer.parseInt(this.nivel);
					}
				}

			}
		}

		// Empresa - Usuario
		this.adClientID = getAD_Client_ID();
		this.adUserID = getAD_User_ID();

		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));

		// Si tengo parametro para idreporte
		if (paramIDReporte != null) paramIDReporte.setParameter(this.idReporte);

	}

	/**
	 * Obtiene los datos y carga la tabla molde auxiliar
	* OpenUp Ltda. Issue #946
	* @author Nicolas Sarlabos - 16/02/2012
	* @see http://1.1.20.123:86/eventum/view.php?id=946
	* @throws Exception
	 */

	private void getData() throws Exception {
		
		String sql = "";

		//si hay filtro de producto
		if (this.mProductID > 0) {

			MProduct prod = new MProduct(getCtx(), this.mProductID, null);

			//se valida que producto este activo
			if (prod.isActive()) {

				this.insumoID = prod.get_ID();
				this.insumoNom = prod.getName();
				this.insumoUM = this.getProdUM(prod); //seteo globalmente el nombre de la unidad de medida del insumo
				
				ArrayList<ProdImplosion> list = prod.getProductosImplosion(mProductCatID,nivelInt); //se obtiene implosion del insumo

				if (list.size() > 0) {

					for (int i = 0; i < list.size(); i++) {

						implosion(list.get(i));
					}
					
					this.updateInfo();

				}
			} else
				throw new AdempiereException("El producto está inactivo en el sistema");

		} else {

			//si no hay filtro de producto,se obtienen TODOS los productos activos
			sql = "SELECT m_product_id FROM m_product WHERE isactive='Y'";

			ResultSet rs = null;
			PreparedStatement pstmt = null;

			try {
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();

				while (rs.next()) {

					MProduct prod = new MProduct(getCtx(), rs.getInt("m_product_id"), null);

					this.insumoID = prod.get_ID();
					this.insumoNom = prod.getName();
					this.insumoUM = this.getProdUM(prod); //seteo globalmente el nombre de la unidad de medida del insumo
					
					sql = " DELETE FROM " + TABLA_MOLDE_AUX + " WHERE idreporte=" + "'" + this.idReporte + "'" + " AND ad_user_id =" + this.adUserID;
							DB.executeUpdateEx(sql, get_TrxName());

					ArrayList<ProdImplosion> list = prod.getProductosImplosion(mProductCatID,nivelInt);

					if (list.size() > 0) {

						for (int i = 0; i < list.size(); i++) {

							implosion(list.get(i)); //llamada a metodo recursivo
						}
					}
					
					this.updateInfo();
				}
				
			
			} catch (Exception e) {
				throw new AdempiereException(e.getMessage());
			} finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

		}

	}

	/**
	 * Metodo recursivo que realiza implosion de un objeto ProdImplosion e inserta en tabla molde auxiliar
	* OpenUp Ltda. Issue #946
	* @author Nicolas Sarlabos - 16/02/2012
	* @see http://1.1.20.123:86/eventum/view.php?id=946
	* @param p
	* @throws Exception
	 */
	private void implosion(ProdImplosion p) throws Exception {

		if (p != null) {

			String sql = "";
			BigDecimal newCant = Env.ZERO;
		
			try {
				//obtengo atributos necesarios
				int hijo_id = p.getHijo_id();
				int padre_id = p.getPadre_id();
				String padre_value = p.getPadre_value();
				String padre_nom = p.getPadre_nom();
				BigDecimal cant = p.getCant().setScale(8, RoundingMode.HALF_DOWN);
				String unidad = this.insumoUM;
				int categoria = p.getCategoria_id();
				int linea = p.getLinea_id();
				int nivel = p.getNivel();

				sql = "SELECT cantidad FROM " + TABLA_MOLDE_AUX + " WHERE m_product_id=" + hijo_id + " AND prod_id=" + padre_id + " AND idreporte=" + "'" + this.idReporte + "'"
							+ " AND ad_user_id=" + this.adUserID;
					BigDecimal qty = DB.getSQLValueBD(get_TrxName(), sql);

				if (qty != null) {
					
					sql = "SELECT cantidad FROM " + TABLA_MOLDE_AUX + " WHERE m_product_id=" + insumoID + " AND prod_id=" + hijo_id + " AND idreporte=" + "'" + this.idReporte + "'"
							+ " AND ad_user_id=" + this.adUserID;
					qty =  DB.getSQLValueBD(get_TrxName(), sql);
									
					if(qty!=null){
						
						sql = "UPDATE " + TABLA_MOLDE_AUX + " SET cantidad= cantidad + " + qty + " WHERE prod_id=" + padre_id + " AND m_product_id=" + insumoID + " AND idreporte=" + "'"
								+ this.idReporte + "'"
								+ " AND ad_user_id=" + this.adUserID;
						DB.executeUpdateEx(sql, get_TrxName());

						cant = qty;
						
					}
				
				} else{
					
					sql = "SELECT cantidad FROM " + TABLA_MOLDE_AUX + " WHERE m_product_id=" + insumoID + " AND prod_id=" + hijo_id + " AND idreporte=" + "'" + this.idReporte + "'"
							+ " AND ad_user_id=" + this.adUserID;
					qty = DB.getSQLValueBD(get_TrxName(), sql);
										
					if(qty!=null){
					
					newCant = qty.multiply(cant).setScale(8, RoundingMode.HALF_DOWN);
					cant = newCant;
					
					sql = "INSERT INTO "
							+ TABLA_MOLDE_AUX
								+ " (ad_client_id, ad_org_id, ad_user_id, fecreporte, idreporte, m_product_id, m_product_nom, prod_id, prod_value, prod_nom, cantidad, unidad, m_product_category_id,"
							+ " uy_linea_negocio_id, nivel) VALUES(" + this.adClientID + "," + this.adOrgID + "," + this.adUserID + ",current_date," + "'"
							+ this.idReporte
							+ "'"
							+ "," + insumoID + "," + "'" + insumoNom + "'" + "," + padre_id + "," + "'" + padre_value + "'" + "," + "'"
								+ padre_nom + "'" + "," + cant + "," + "'" + unidad
							+ "'" + ","
							+ categoria + "," + linea + "," + nivel + ")";

						DB.executeUpdateEx(sql, get_TrxName());
				
					}
					
				}
				
				sql = "SELECT prod_id FROM " + TABLA_MOLDE_AUX + " WHERE prod_id=" + padre_id + " AND m_product_id = " + insumoID + 
						" AND idreporte=" + "'" + this.idReporte + "'" + " AND ad_user_id=" + this.adUserID;
				int prodID = DB.getSQLValueEx(get_TrxName(), sql);
				
				/*sql = "SELECT cantidad FROM " + TABLA_MOLDE_AUX + " WHERE prod_id=" + padre_id + " AND m_product_id = " + insumoID + 
						" AND idreporte=" + "'" + this.idReporte + "'" + " AND ad_user_id=" + this.adUserID;
				BigDecimal cantExistente = DB.getSQLValueBDEx(get_TrxName(), sql);*/
				
				if (prodID <= 0) {
					
					sql = "INSERT INTO "
						+ TABLA_MOLDE_AUX
							+ " (ad_client_id, ad_org_id, ad_user_id, fecreporte, idreporte, m_product_id, m_product_nom, prod_id, prod_value, prod_nom, cantidad, unidad, m_product_category_id,"
						+ " uy_linea_negocio_id, nivel) VALUES(" + this.adClientID + "," + this.adOrgID + "," + this.adUserID + ",current_date," + "'"
						+ this.idReporte
						+ "'"
						+ "," + insumoID + "," + "'" + insumoNom + "'" + "," + padre_id + "," + "'" + padre_value + "'" + "," + "'"
							+ padre_nom + "'" + "," + cant + "," + "'" + unidad
						+ "'" + ","
						+ categoria + "," + linea + "," + nivel + ")";

					DB.executeUpdateEx(sql, get_TrxName());
					
				} else {
					
					sql = "UPDATE " + TABLA_MOLDE + " SET cantidad= cantidad + " + cant + " WHERE prod_id=" + padre_id + " AND m_product_id=" + insumoID + " AND idreporte=" + "'"
							+ this.idReporte + "'"
							+ " AND ad_user_id=" + this.adUserID;
					DB.executeUpdateEx(sql, get_TrxName());
					
					
				}
			
					if (p.getList().size() > 0) {

						for (int i = 0; i < p.getList().size(); i++) {

							implosion(p.getList().get(i));
						}

					}


			} catch (Exception e) {
				log.log(Level.SEVERE, sql, e);
				throw e;

			}
		}

	}

	/**
	 * Metodo que carga la tabla molde principal eliminando registros duplicados y haciendo la suma correspondiente para ingresar 
	 * un solo registro (necesario para los productos PACK)
	* OpenUp Ltda. Issue #946
	* @author Nicolas Sarlabos - 17/02/2012
	* @see http://1.1.20.123:86/eventum/view.php?id=946
	* @throws Exception
	 */

	public void updateInfo() throws Exception { 

		//se recorre toda la tabla auxiliar y se inserta en la tabla principal 
		String sql = "SELECT * FROM " + TABLA_MOLDE_AUX + " WHERE idreporte=" + "'" + this.idReporte + "'" + " AND ad_user_id=" + this.adUserID;

		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				//OpenUp Nicolas Sarlabos 25/05/2012 #1022,se corrige consulta sql
				sql = "SELECT prod_id FROM " + TABLA_MOLDE + " WHERE prod_id=" + rs.getInt("prod_id") + " AND m_product_id = " + rs.getInt("m_product_id") + 
						" AND idreporte=" + "'" + this.idReporte + "'" + " AND ad_user_id=" + this.adUserID;
				//fin Nicolas Sarlabos 25/05/2012 #1022
				int prodID = DB.getSQLValueEx(get_TrxName(), sql);
				
				sql = "SELECT m_product_id FROM " + TABLA_MOLDE + " WHERE prod_id=" + rs.getInt("prod_id") + " AND m_product_id = " + rs.getInt("m_product_id") + 
						" AND idreporte=" + "'" + this.idReporte + "'" + " AND ad_user_id=" + this.adUserID;
				int mprodID = DB.getSQLValueEx(get_TrxName(), sql);

				//si la dupla "producto hijo-producto padre" NO existe en la tabla principal se procede a insertarlo
				if (prodID <= 0) {

					sql = "INSERT INTO "
							+ TABLA_MOLDE
							+ " (ad_client_id, ad_org_id, ad_user_id, fecreporte, idreporte, m_product_id, m_product_nom, prod_id, prod_value, prod_nom, cantidad, unidad, m_product_category_id,"
							+ " uy_linea_negocio_id, nivel) VALUES(" + this.adClientID + "," + this.adOrgID + "," + this.adUserID + ",current_date," + "'"
							+ this.idReporte + "'" + "," + rs.getInt("m_product_id") + "," + "'" + rs.getString("m_product_nom") + "'" + ","
							+ rs.getInt("prod_id") + "," + "'"
							+ rs.getString("prod_value") + "'" + "," + "'" + rs.getString("prod_nom") + "'" + "," + rs.getBigDecimal("cantidad") + "," + "'"
							+ rs.getString("unidad") + "'" + "," + rs.getInt("m_product_category_id") + "," + rs.getInt("uy_linea_negocio_id") + ","
							+ rs.getInt("nivel") + ")";

					DB.executeUpdateEx(sql, get_TrxName());

				} else {
					//si la dupla "producto hijo-producto padre" ya existe en la tabla principal solamente se suma a la cantidad existente la nueva cantidad (EJ: producto 1040 pack) 
					BigDecimal cantASumar = rs.getBigDecimal("cantidad").setScale(8, RoundingMode.HALF_DOWN);

					sql = "UPDATE " + TABLA_MOLDE + " SET cantidad= cantidad + " + cantASumar + " WHERE prod_id=" + prodID + " AND m_product_id=" + mprodID + " AND idreporte=" + "'"
							+ this.idReporte + "'"
							+ " AND ad_user_id=" + this.adUserID;
					DB.executeUpdateEx(sql, get_TrxName());

				}

			}

		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	/**
	 * Metodo que obtiene el nombre de la unidad de medida del producto
	 * OpenUp Ltda. Issue #1022 
	 * @author Nicolas Sarlabos - 01/06/2012
	 * @see
	 * @param p
	 * @return
	 */
	
	private String getProdUM(MProduct p){
		
		String name = "";
		String sql = "";
		
		sql = "SELECT name FROM c_uom WHERE c_uom_id=" + p.getC_UOM_ID();
		name = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		return name;
		
	}
}
