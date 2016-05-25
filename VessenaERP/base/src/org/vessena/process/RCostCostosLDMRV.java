/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MCost;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.openup.model.MProductCost;

/**
 * @author Nicolas
 *
 */
public class RCostCostosLDMRV extends SvrProcess{

	private int idProducto = 0;
	private int idEmpresa = 0;
	private int idOrganizacion = 0;
	private Long idUsuario = new Long(0);
	private String idReporte = "";
	private int seqNo = 0;
	private int parentID = 0;
	private int mprodID = 0;
	private String nameParent = "";
	private String nameProd = "";
	private BigDecimal qty = Env.ZERO;
	private BigDecimal amount = Env.ZERO;
	private BigDecimal totalAmt = Env.ZERO;
	private String isParent = "N"; 
	
	Timestamp costingDay = null;
	private BigDecimal[] cantidades = {Env.ZERO,Env.ZERO,Env.ZERO,Env.ZERO,Env.ZERO};
	private static final String TABLA_MOLDE = "uy_molde_rcostldmrv";
	/**
	 * 
	 */
	public RCostCostosLDMRV() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		// Parametro para id de reporte
				ProcessInfoParameter paramIDReporte = null;

				// Obtengo parametros y los recorro
				ProcessInfoParameter[] para = getParameter();
				for (int i = 0; i < para.length; i++)
				{
					String name = para[i].getParameterName().trim();
					if (name!= null){
						if (name.equalsIgnoreCase("idReporte")){
							paramIDReporte = para[i]; 
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
						if (name.equalsIgnoreCase("M_Product_ID")){
							if (para[i].getParameter() != null) {
								this.idProducto = ((BigDecimal)para[i].getParameter()).intValueExact();
							}							
						}
					}
				}				
				// Obtengo id para este reporte
				this.idReporte = UtilReportes.getReportID(this.idUsuario);
				// Si tengo parametro para idreporte
				if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);
		
	}

	@Override
	protected String doIt() throws Exception {
	
		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();

		this.cargoCostos();
		
		this.updateTable();
		
		return "ok";
	}

	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id =" + this.idUsuario;
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	/**
	 * OpenUp.	#1443
	 * Descripcion : Carga costos.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 23/10/2013
	 */
	private void cargoCostos() {

		String sql = "", whereFiltros = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
			
		try{
			// Condiciones segun filtros
			if (idProducto>0) whereFiltros = " AND a.M_Product_ID=" + idProducto;

			sql = "SELECT bom.pp_product_bom_id " + " FROM PP_Product_BOM bom INNER JOIN M_Product a ON bom.m_product_id = a.m_product_id "
					+ " WHERE a.ad_client_id =" + this.idEmpresa + whereFiltros + " AND a.isactive ='Y'" //OpenUp. Nicolas Sarlabos. 16/05/2014. #2155. Se elimina filtro de categoria.
					+ " AND bom.isactive='Y'" + " ORDER BY a.value asc ";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while (rs.next()){
				
				MPPProductBOM bom = new MPPProductBOM(getCtx(), rs.getInt("pp_product_bom_id"), null);
				MProduct prod = new MProduct(getCtx(), bom.getM_Product_ID(), null);
				
				//cargo datos del padre para utilizar en todos los registros a insertar
				this.parentID = prod.get_ID();
				this.nameParent = prod.getName();
				
				cantidades[0] = Env.ONE;
							
				// Si no tiene hijos
				if (!this.recursivoCostos(bom.getPP_Product_BOM_ID(), 0)){
					// Guardo registro en temporal hasta este nivel
					this.saveRegistro();

				}
				
				// Si tengo filtro por articulo me cubro para que no me tire dos boms 
				if (idProducto>0) break;
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
	 * OpenUp. #1443	
	 * Descripcion : Proceso que recorre recursivamente el arbol de la LDM y va guardando info en tabla molde.
	 * @param idBomPadre
	 * @param nivel
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 23/10/2013
	 */
	private boolean recursivoCostos(int idBomPadre, int nivel){

		// Si llego a nivel 4 (indice comienza en 0) no sigo en la recursividad.
		if (nivel==4) return false;
		
		boolean tieneHijos = false;
		BigDecimal cost = Env.ZERO;
				
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			// Busco hijos
			sql = "SELECT bomline.pp_product_bomline_id, bomline.m_product_id, a.value, a.name, bomline.qtybom " +
				  " FROM PP_Product_BOMLine bomline " +
				  " INNER JOIN M_Product a ON bomline.m_product_id = a.m_product_id " +
				  " WHERE a.ad_client_id =" + this.idEmpresa + 
				  " AND a.isactive ='Y'" +
				  " AND bomline.PP_Product_BOM_ID =" + idBomPadre +
				  " ORDER BY a.value asc ";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			nivel++;
			
			// ProcesoHijos
			while (rs.next()){
				
				tieneHijos = true;
				
				MPPProductBOMLine bomLine = new MPPProductBOMLine(getCtx(), rs.getInt("pp_product_bomline_id"), null);
				MProduct productLine = new MProduct(getCtx(), rs.getInt("m_product_id"), null);
	
				//obtengo ID y nombre del producto hijo
				this.mprodID = productLine.get_ID();
				this.nameProd = productLine.getName(); 
				
				cost = MCost.getCostFromTable(productLine.getM_Product_ID(), null);

				if (cost != null) {

					if (cost.compareTo(Env.ZERO) > 0) this.amount = cost;

				} else amount = MProductCost.getProductCost(productLine.getM_Product_ID(), costingDay, null).setScale(6, RoundingMode.UP);

				cantidades[nivel] = bomLine.getQtyBOM().multiply(cantidades[nivel - 1]).setScale(6, RoundingMode.UP);
				
				this.qty = cantidades[nivel];
				
				this.totalAmt = this.amount.multiply(this.qty).setScale(6, RoundingMode.UP);
		
				// Verifico si este producto tiene un id en la pp_product_bom. Este id es necesario para buscar hijos.
				// Si no tiene ID en esta tabla entonces no tiene hijos.
				int idProductBOMHijo = DB.getSQLValue(null, "SELECT pp_product_bom_id FROM pp_product_bom WHERE isactive='Y' AND m_product_id="
						+ productLine.getM_Product_ID());
				
				if ((idProductBOMHijo > 0)) {
					
					this.isParent = "Y";
					this.saveRegistro();
				}
				
				// Si no tiene hijos
				if ((idProductBOMHijo <= 0) || (!this.recursivoCostos(idProductBOMHijo, nivel))){
					
					this.isParent = "N";

					// Guardo registro en temporal hasta este nivel
					this.saveRegistro();
					
					cantidades[nivel] = Env.ZERO;
		
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

		return tieneHijos;
	}
	
	/**
	 * OpenUp. #1443	
	 * Descripcion : Guarda registro en tabla molde.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 23/10/2013
	 */
	private void saveRegistro(){

		String insert = "", values = "";
		
		try{
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, ad_user_id, idreporte, fecreporte, seqno, parent_id, " +
					"name, m_product_id, name2, qty, amt, totalamt, isparent) ";
			
			values = " VALUES(" + this.idEmpresa + "," + this.idOrganizacion + "," + this.idUsuario + ",'" + this.idReporte + "'," + "current_date," + this.seqNo + 
					 "," +  this.parentID + ",'" + this.nameParent + "'," + this.mprodID + ",'" + this.nameProd + "'," + this.qty + "," + this.amount + "," + this.totalAmt + 
					 ",'" + this.isParent + "')" ;
			
			log.log(Level.INFO, insert + values);
			DB.executeUpdate(insert + values, null);
			
			seqNo ++; //incremento secuencia
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + values, e);
		}
	}	
	
	/**
	 * OpenUp. #1443	
	 * Descripcion : Actualiza tabla molde eliminando los costos unitarios y totales de los productos padres.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 27/10/2013
	 */
	private void updateTable(){

		String sql = "";

		try {

			sql = "update " + TABLA_MOLDE + " set amt = NULL, totalamt = NULL where isparent = 'Y' and idreporte = '" + this.idReporte + 
					"' and ad_user_id = " + this.idUsuario;
			DB.executeUpdateEx(sql, null);

		}catch (Exception e){
			log.log(Level.SEVERE, sql, e);
		}

	}
}
