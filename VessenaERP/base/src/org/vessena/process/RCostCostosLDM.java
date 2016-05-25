/**
 * RPrdCostosLDM.java
 * 23/11/2010
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
 * OpenUp.
 * RPrdCostosLDM
 * Descripcion : Reporte de costos de Lista de Materiales.
 * @author Gabriel Vila
 * Fecha : 23/11/2010
 */
// org.openup.process.RCostCostosLDM
public class RCostCostosLDM extends SvrProcess {

	private int idProducto = 0;
	
	private int idEmpresa = 0;
	//private int idOrganizacion = 0;
	private Long idUsuario = new Long(0);
	//private String nombreEmpresa = "";
	//private String nombreUsuario = "";
	private String idReporte = "";

	Timestamp costingDay = null;

	private MProduct[] prods = {null,null,null,null,null};

	//private HashMap<Integer, MProduct[]> produts = new HashMap<Integer, MProduct[]>();

	private int[] bomIds = {0,0,0,0,0};
	private BigDecimal[] costos = {Env.ZERO,Env.ZERO,Env.ZERO,Env.ZERO,Env.ZERO};
	private BigDecimal[] cantidades = {Env.ZERO,Env.ZERO,Env.ZERO,Env.ZERO,Env.ZERO};
	private BigDecimal[] totales = {Env.ZERO,Env.ZERO,Env.ZERO,Env.ZERO,Env.ZERO};
	
	private static final String TABLA_MOLDE = "uy_molde_rprdcostosldm";
	
	/**
	 * Constructor
	 */
	public RCostCostosLDM() {
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
				if (name.equalsIgnoreCase("AD_User_ID")){
					this.idUsuario = ((BigDecimal)para[i].getParameter()).longValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.idEmpresa = ((BigDecimal)para[i].getParameter()).intValueExact();
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
		
		// Seteo titulo del reporte segun tipo cta corriente
		String tituloReporte = "Costos - Lista de Materiales";
		
		// Si tengo parametro para titulo de reporte
		if (paramTituloReporte!=null) paramTituloReporte.setParameter(tituloReporte);

		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);
		
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();

		this.cargoCostos();

		this.updateTotalesFinales();
		
		return "ok";

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


	
	/**
	 * OpenUp.	
	 * Descripcion : Carga costos.
	 * @author  Gabriel Vila 
	 * Fecha : 23/11/2010
	 */
	private void cargoCostos() {

		String sql = "", whereFiltros = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		//BigDecimal costo = Env.ZERO;
		
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
				prods[0] = prod;
				bomIds[0] = bom.getPP_Product_BOM_ID();
				costos[0] = Env.ZERO;
				cantidades[0] = Env.ONE;
				totales[0] = Env.ZERO;
				
				// Si no tiene hijos
				if (!this.recursivoCostos(bom.getPP_Product_BOM_ID(), 0)){
					// Guardo registro en temporal hasta este nivel
					this.saveRegistro();

					// Reseteo nivel del arbol para este hijo
					prods[0] = null;
					bomIds[0] = 0;
					costos[0] = Env.ZERO;
					totales[0] = Env.ONE;
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
	 * OpenUp.	
	 * Descripcion : Proceso que recorre recursivamente el arbol de la LDM y va guardando info en tabla molde.
	 * @param idBomPadre
	 * @param nivel
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 26/11/2010
	 */
	private boolean recursivoCostos(int idBomPadre, int nivel){

		// Si llego a nivel 4 (indice comienza en 0) no sigo en la recursividad.
		if (nivel==4) return false;
		
		boolean tieneHijos = false;
		
		
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
			
				prods[nivel] = productLine;
				bomIds[nivel] = bomLine.getPP_Product_BOMLine_ID();
				
				// costos[nivel] =
				// this.getCostoProducto(productLine.getM_Product_ID());

				//OpenUp Nicolas Sarlabos #944 13/02/2012..primero se busca el costo en la tabla de costos ajustados, si no se encuentra se calcula
				BigDecimal cost = MCost.getCostFromTable(productLine.getM_Product_ID(), null);

				if (cost != null) {

					if (cost.compareTo(Env.ZERO) > 0) costos[nivel] = cost;

				} else
					costos[nivel] = MProductCost.getProductCost(productLine.getM_Product_ID(), costingDay, null).setScale(6, RoundingMode.UP);

				//fin OpenUp Nicolas Sarlabos #944 13/02/2012

				//cantidades[nivel] = bomLine.getQtyBOM();
				cantidades[nivel] = bomLine.getQtyBOM().multiply(cantidades[nivel - 1]).setScale(6, RoundingMode.UP);
				
				totales[nivel] = costos[nivel].multiply(cantidades[nivel]).setScale(6, RoundingMode.UP);

				// OpenUp. Nicolas Garcia. 12/10/2011. Issue #898. Se repara
				// error, solo se debe traer listas de materiales activas.

				// Verifico si este producto tiene un id en la pp_product_bom. Este id es necesario para buscar hijos.
				// Si no tiene ID en esta tabla entonces no tiene hijos.
				// int idProductBOMHijo =
				// this.getProductBOMID(productLine.getM_Product_ID());
				int idProductBOMHijo = DB.getSQLValue(null, "SELECT pp_product_bom_id FROM pp_product_bom WHERE isactive='Y' AND m_product_id="
						+ productLine.getM_Product_ID());
				
				// Fin Issue #898

				// Si no tiene hijos
				if ((idProductBOMHijo <= 0) || (!this.recursivoCostos(idProductBOMHijo, nivel))){

					// Guardo registro en temporal hasta este nivel
					this.saveRegistro();
					
					// Reseteo nivel del arbol para este hijo
					prods[nivel] = null;
					bomIds[nivel] = 0;
					costos[nivel] = Env.ZERO;
					cantidades[nivel] = Env.ZERO;
					totales[nivel] = Env.ZERO;
				}
				
				// Actualizo 
				
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

	
	/*private BigDecimal getCostoProducto(int mProductID) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		BigDecimal value = Env.ZERO;
		
		try{
			sql = " SELECT coalesce(costo_mn,0) as costo_mn,metodo_costeo " +
				  " FROM uy_v_costosunicos a " +
				  " WHERE a.ad_client_id =" + this.idEmpresa + 
				  " AND a.M_Product_ID =" + mProductID;
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			
			String tipoCosteo="";
			
			if (rs.next()){
				value = rs.getBigDecimal(1);
				tipoCosteo=rs.getString("metodo_costeo");
			}
			
			if(tipoCosteo.equals("Ultima Factura")){
				//Pregunto si hay factura importacion para ese producto que sea mayor a la fecha de ultam fact
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
				
				sql = "SELECT c_invoiceline.c_invoice_id" +
						" FROM c_invoiceline"+
						" LEFT JOIN c_invoice ON c_invoice.c_invoice_id=c_invoiceline.c_invoice_id" +
						" WHERE c_invoice.docstatus ='CO' AND c_invoiceline.m_product_id=" +mProductID+
						"ORDER BY c_invoice.dateinvoiced desc";
			
				pstmt = DB.prepareStatement (sql, null);
				rs = pstmt.executeQuery ();
				//Se buscra la  ultima factura.
				MInvoice fact= null;
				if (rs.next()){
					fact= new MInvoice(getCtx(),rs.getInt("c_invoice_id"),null);
				}
				//Si exsiste factura
				if(fact!=null){
					//Pregunto si la ultima fact es de importacion
					if(fact.getC_DocType_ID()==1000060){ //TODO este ID es temporal hasta que el modulo de importacion este pronto
						//uso costo stander
						
						
						DB.close(rs, pstmt);
						rs = null; pstmt = null;
						
						sql = " SELECT costo_est FROM uy_v_costosall  " +
						  " WHERE M_Product_ID =" + mProductID;
					
						pstmt = DB.prepareStatement (sql, null);
						rs = pstmt.executeQuery ();
					
						if (rs.next()){
							value = (rs.getBigDecimal("costo_est"));
							
						}
						
						
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

		return value;
	}*/

	/*private int getProductBOMID(int mProductID) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		int value = -1;
		
		try{
			sql = "SELECT PP_Product_BOM_ID " +
				  " FROM PP_Product_BOM bom INNER JOIN M_Product a ON bom.m_product_id = a.m_product_id " +
				  " WHERE a.ad_client_id =" + this.idEmpresa + 
				  " AND a.isactive ='Y'" +
				  " AND bom.M_Product_ID =" + mProductID +
				  " ORDER BY a.value asc ";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			if (rs.next()){
				value = rs.getInt(1);
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
	}*/

	/**
	 * OpenUp.	
	 * Descripcion : Guarda registro en tabla molde con una linea de arbol de costo en un maximo de 5 niveles.
	 * @author  Gabriel Vila 
	 * Fecha : 26/11/2010
	 */
	private void saveRegistro(){

		String insert = "", values = "";
		
		try{
			insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, idusuario, fecreporte, " +
					"idbom1, idbom2, idbom3, idbom4, idbom5, " +
					"idprod1, valueprod1, nombreprod1, costo_mn_prod1, cant_prod1, total_prod1, " +
					"idprod2, valueprod2, nombreprod2, costo_mn_prod2, cant_prod2, total_prod2, " +
					"idprod3, valueprod3, nombreprod3, costo_mn_prod3, cant_prod3, total_prod3, " +
					"idprod4, valueprod4, nombreprod4, costo_mn_prod4, cant_prod4, total_prod4, " +
					"idprod5, valueprod5, nombreprod5, costo_mn_prod5, cant_prod5, total_prod5) ";
			
			values = " VALUES('" + this.idReporte + "'," + this.idUsuario + ",current_date," + 
			bomIds[0] + "," +  bomIds[1] + "," + bomIds[2] + "," + bomIds[3] + "," + bomIds[4] + ",";
			
			int p1 = (prods[0] == null) ? 0 : ((MProduct)prods[0]).getM_Product_ID();
			if (p1>0) values += p1 + ",'" + ((MProduct)prods[0]).getValue() + "','" + ((MProduct)prods[0]).getName() + "'," + costos[0] + "," + cantidades[0] + "," + totales[0] + ",";
			else values += "0,'0','0',0, 0, 0,";
			
			int p2 = (prods[1] == null) ? 0 : ((MProduct)prods[1]).getM_Product_ID();
			if (p2>0) values += p2 + ",'" + ((MProduct)prods[1]).getValue() + "','" + ((MProduct)prods[1]).getName() + "'," + costos[1] + "," + cantidades[1] + "," + totales[1] + ",";
			else values += "0,'0','0',0, 0, 0,";
			
			int p3 = (prods[2] == null) ? 0 : ((MProduct)prods[2]).getM_Product_ID();
			if (p3>0) values += p3 + ",'" + ((MProduct)prods[2]).getValue() + "','" + ((MProduct)prods[2]).getName() + "'," + costos[2] + "," + cantidades[2] + "," + totales[2] + ",";
			else values += "0,'0','0',0, 0, 0,";
			
			int p4 = (prods[3] == null) ? 0 : ((MProduct)prods[3]).getM_Product_ID();
			if (p4>0) values += p4 + ",'" + ((MProduct)prods[3]).getValue() + "','" + ((MProduct)prods[3]).getName() + "'," + costos[3] + "," + cantidades[3] + "," + totales[3] + ",";
			else values += "0,'0','0',0, 0, 0,";
			
			int p5 = (prods[4] == null) ? 0 : ((MProduct)prods[4]).getM_Product_ID();
			if (p5>0) values += p5 + ",'" + ((MProduct)prods[4]).getValue() + "','" + ((MProduct)prods[4]).getName() + "'," + costos[4] + "," + cantidades[4] + "," + totales[4] + ")";
			else values += "0,'0','0', 0, 0, 0)";
			
			log.log(Level.INFO, insert + values);
			DB.executeUpdate(insert + values, null);
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + values, e);
		}
	}
	
	
	private void updateTotalesFinales(){

		String update = "";
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		ResultSet rs2 = null;
		PreparedStatement pstmt2 = null;

		
		try{
			// Actualizo totales de segundo nivel
			sql = " SELECT DISTINCT idbom2 " +
			      " FROM " + TABLA_MOLDE +
			      " WHERE idReporte=?"; 
		
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, this.idReporte);
			rs = pstmt.executeQuery ();
	
			while (rs.next()){
				
				// Si este producto de nivel 2 tiene hijos 
				if (this.productoTieneHijos(2, rs.getInt(1))){
					update = "UPDATE " + TABLA_MOLDE +
							 " SET costo_mn_prod2 = null, total_prod2 = (SELECT SUM(distinct coalesce(total_prod3,0)) FROM " + TABLA_MOLDE + 
							 " WHERE idReporte ='" + this.idReporte + "'" +
							 " AND idbom2=" + rs.getInt(1) + ") " +
							 " WHERE idReporte ='" + this.idReporte + "'" +
							 " AND idbom2=" + rs.getInt(1);
					
					log.log(Level.INFO, update);
					DB.executeUpdate(update, null);
				}
			}
			
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
			
			// Actualizo totales de primer nivel
			sql = " SELECT DISTINCT idbom1 " +
			      " FROM " + TABLA_MOLDE +
			      " WHERE idReporte=?"; 
		
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, this.idReporte);
			rs = pstmt.executeQuery ();
	
			while (rs.next()){
				
				sql = " SELECT DISTINCT idbom2, total_prod2 " +
			      	  " FROM " + TABLA_MOLDE +
			          " WHERE idReporte=?" +
			          " AND idbom1=" + rs.getInt(1);
		
				pstmt2 = DB.prepareStatement (sql, null);
				pstmt2.setString(1, this.idReporte);
				rs2 = pstmt2.executeQuery ();
				
				BigDecimal totalAux = Env.ZERO;
				
				while(rs2.next()){
					totalAux = totalAux.add(rs2.getBigDecimal("total_prod2"));
				}

				DB.close(rs2, pstmt2);
				rs2 = null; pstmt2 = null;

				update = "UPDATE " + TABLA_MOLDE +
						 " SET costo_mn_prod1 = null, total_prod1 = " + totalAux +
						 " WHERE idReporte ='" + this.idReporte + "'" +
						 " AND idbom1=" + rs.getInt(1);
				
				log.log(Level.INFO, update);
				DB.executeUpdate(update, null);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, update, e);
		}
		finally{
			DB.close(rs2, pstmt2);
			rs2 = null; pstmt2 = null;
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}
	
	private boolean productoTieneHijos(int nivel, int idBomPadre){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean value = false;
		
		try{
			sql ="SELECT count(*) as cont " + 
 		  	" FROM " + TABLA_MOLDE + 
			 " WHERE idReporte ='" + this.idReporte + "'" +
			 " AND idbom"+ nivel + "=" + idBomPadre;
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				int contador = rs.getInt(1);
				if (contador>1)
					value = true;
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

	/*private void load(int productID, int nivel) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = " SELECT bom.m_product_id as padre,bomline.*" + " FROM pp_product_bom bom"
					+ " INNER JOIN pp_product_bomline bomline ON bomline.pp_product_bom_id=bom.pp_product_bom_id"
					+ " INNER JOIN m_product m_product ON m_product.m_product_id=bom.m_product_id" + " WHERE m_product.ad_client_id =" + this.getAD_Client_ID()
					+ "AND m_product.isactive ='Y' " + " AND bom.IsActive = 'Y' AND bom.m_product_id=" + productID + " ORDER BY bom.m_product_id asc ";

			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			
			
			while (rs.next()) {
				

			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}*/

	/*private void saveRegistro2(int nivel, int productID, BigDecimal cantidad, BigDecimal costo) {

		String insert = "", values = "";
		
		try{
			insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, idusuario, fecreporte, " +
					"idbom1, idbom2, idbom3, idbom4, idbom5, " +
					"idprod1, valueprod1, nombreprod1, costo_mn_prod1, cant_prod1, total_prod1, " +
					"idprod2, valueprod2, nombreprod2, costo_mn_prod2, cant_prod2, total_prod2, " +
					"idprod3, valueprod3, nombreprod3, costo_mn_prod3, cant_prod3, total_prod3, " +
					"idprod4, valueprod4, nombreprod4, costo_mn_prod4, cant_prod4, total_prod4, " +
					"idprod5, valueprod5, nombreprod5, costo_mn_prod5, cant_prod5, total_prod5) ";
			
			values = " VALUES('" + this.idReporte + "'," + this.idUsuario + ",current_date," + 
			"0,0,0,0,0,";
						
			switch (nivel) {
				case 1:

					break;
				case 2:

					break;
				case 3:

					break;
				case 4:

					break;
				case 5:

					break;

				default:
					break;
			}

			if (productID > 0)
				values += productID + ",'" + ((MProduct) prods[0]).getValue() + "','" + ((MProduct) prods[0]).getName() + "'," + costos[0] + ","
					+ cantidades[0] + "," + totales[0] + ",";
			
			
			log.log(Level.INFO, insert + values);
			DB.executeUpdate(insert + values, null);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}*/
}
