/**
 * RPrdCostosLDM.java
 * 23/11/2010
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCost;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MProductCost;

/**
 * OpenUp.
 * RPrdCostosLDM
 * Descripcion : Reporte de costos de Lista de Materiales.
 * @author Gabriel Vila
 * Fecha : 23/11/2010
 */
public class RCostCostoDate extends SvrProcess {

	private int idProducto = 0;
	private int idTipoProducto = 0;
	private Timestamp fechaCosteo=null;
	
	private int idEmpresa = 0;
	//private int idOrganizacion = 0;
	private int ad_user_id = 0;
	//private String nombreEmpresa = "";
	//private String nombreUsuario = "";
	private String idReporte = "";


	
	private static final String TABLA_MOLDE = "UY_Molde_CostosProductoFecha";
	
	/**
	 * Constructor
	 */
	public RCostCostoDate() {
		// TODO Auto-generated constructor stub
	}
	


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
//				if (name.equalsIgnoreCase("AD_User_ID")){
//					this.ad_user_id = ((BigDecimal)para[i].getParameter()).longValueExact();
//				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.idEmpresa = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("M_Product_ID")){
					if(para[i].getParameter()!=null){
						this.idProducto = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("M_Product_Category_ID")){
					if(para[i].getParameter()!=null){
						this.idTipoProducto = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("fechacosto")){
					this.fechaCosteo = ((Timestamp)para[i].getParameter());
				}
				if (name.equalsIgnoreCase("AD_User_ID")){
					if(para[i].getParameter()!=null){
						this.ad_user_id= ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
			}
		}
		
		// Obtengo id para este reporte
		//this.idReporte = UtilReportes.getReportID(this.ad_user_id);
	
		this.idEmpresa=getAD_Client_ID();
		
		
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
		

		this.cargoProductos();
		return "ok";

	}

	private void cargoProductos() {

		String sql = "", whereFiltros = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		
		try{
			// Condiciones segun filtros
			if (idProducto>0) whereFiltros = " AND a.M_Product_ID=" + idProducto;
			if (idTipoProducto>0) whereFiltros += " AND a.m_product_category_id ="+ idTipoProducto;

			sql = "SELECT a.m_product_id " +
			  	" FROM M_Product a " +
			  	" WHERE a.ad_client_id =" + this.idEmpresa + whereFiltros +
			  	" AND a.isactive ='Y'" +
			    " ORDER BY a.value asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();
			this.getProcessInfo().getWaiting().setText("Obteniendo Datos");

			rs = pstmt.executeQuery();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			while (rs.next()){
				this.getProcessInfo().getWaiting().setText("Calculando " + rowCount++ + " de " + totalRowCount);

				MProduct product = new MProduct(getCtx(), rs.getInt("m_product_id"), null);
				this.saveRegistro(product, MProductCost.getProductCostObject(rs.getInt("m_product_id"), fechaCosteo, null, getCtx()));
			}

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
	}

	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE + 
				  " WHERE ad_user_id =" + this.ad_user_id;
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
	}


	/**
	 * OpenUp.	
	 * Descripcion : Guarda registro en tabla molde con una linea de arbol de costo en un maximo de 5 niveles.
	 * @author  Nicolas Garcia
	 * Fecha : 31/05/2011
	 */
	private void saveRegistro(MProduct product, MProductCost costo) {

		String descrip = "";

		if (product.getDescription() != null) {
			descrip = product.getDescription().replace("'", " ");
		}
		//OpenUp Nicolas Sarlabos #944 14/02/2012..se busca el costo en la tabla de costos ajustados
		BigDecimal cost = MCost.getCostFromTable(product.get_ID(), fechaCosteo);

		if (cost != null) {

			if (cost.compareTo(Env.ZERO) > 0) {
				costo.setAmtAcct(cost);
				costo.setUY_TipoCosteo("SA");
			}

		}
		//fin OpenUp Nicolas Sarlabos #944 14/02/2012
		String values = " (ad_client_id,ad_org_id,ad_user_id,created,createdby,updated,updatedby,costo,m_product_id,descripcion,m_product_category_id,fechaCosto,UY_TipoCosteo)";
		String datos = " values(1000005,1000005," + ad_user_id + ",'" + fechaCosteo + "'," + ad_user_id + ",'" + fechaCosteo + "'," + ad_user_id + ","
				+ costo.getAmtAcct()
				+ "," + product.getM_Product_ID() + ",'" + descrip + "'," +
 +product.getM_Product_Category_ID() + ",'" + fechaCosteo
				+ "','" + costo.getUY_TipoCosteo() + "')";
			
			String sql ="INSERT INTO "+TABLA_MOLDE +values+datos;
			
		try{
			sql ="INSERT INTO "+TABLA_MOLDE +values+datos;
			
			DB.executeUpdateEx(sql, this.get_TrxName());
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
	}
	
		
}
