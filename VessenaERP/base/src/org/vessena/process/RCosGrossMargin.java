package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MConversionRate;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class RCosGrossMargin extends SvrProcess {
	
	private int m_product_ID = 0;
	private int c_bpartner_ID = 0;
	private int m_product_category_ID = 0;
	private int uy_familia_ID = 0;
	private int c_invoice_ID = 0;
	private Timestamp fechaDesde=null;
	private Timestamp fechaHasta=null;
	private int ad_user_id = 0;
	private static final String TABLA_MOLDE = "UY_Molde_MargenBruto";

	@Override
	protected String doIt() throws Exception {
		deleteInstanciasViejasReporte();
		loadModelTable();
		return "ok";
	}

	/**
	 * 
	 * OpenUp.	issue #773
	 * Descripcion : Obtengo y cargo en tabla molde segun filtros indicados por el usuario.
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 22/08/2011
	 */
	private String loadModelTable() {

		String insert = "", sql = "";
		
		//OpenUp Nicolas Sarlabos #773 04/10/2011, se modifica la consulta para seleccionar el ID y nombre de cliente y producto
		try {
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_user_id,ad_client_id,ad_org_id,c_invoice_id,c_invoiceline_id,c_bpartner_id,bp_name,dateinvoiced,m_product_id,p_name," +
					 "m_product_category_id,uy_familia_id,qtyinvoiced,linenetamt,c_currency_id)";

			sql = "select "+this.ad_user_id+", inv.ad_client_id,inv.ad_org_id,inv.c_invoice_id,inl.c_invoiceline_id,bp.c_bpartner_id,bp.name,inv.dateinvoiced,p.m_product_id,p.name,"
			      + "pc.m_product_category_id,p.uy_familia_id,inl.qtyinvoiced,inl.linenetamt,inv.c_currency_id "
                  + "from c_invoice inv "
                  + "inner join c_invoiceline inl on inv.c_invoice_id = inl.c_invoice_id "
                  + "inner join c_bpartner bp on inv.c_bpartner_id = bp.c_bpartner_id "
                  + "inner join m_product p on inl.m_product_id = p.m_product_id "
                  + "left join m_product_category pc on p.m_product_category_id = pc.m_product_category_id "
                  + "left join uy_familia f on p.uy_familia_id = f.uy_familia_id "
                  + "where inv.docstatus='CO' " 
				  + filtros()+ " Order By inv.dateinvoiced DESC";
			//Fin OpenUp Nicolas Sarlabos #773 04/10/2011
			log.log(Level.INFO, insert + sql);
						
			DB.executeUpdate(insert + sql, null);
			
			updateTable();
						

		} catch (Exception e) {
			log.log(Level.SEVERE, insert + sql, e);
			return e.getMessage();
		}
		return "OK";

	}
	/**
	 * 
	 * OpenUp.  issue #773	
	 * Descripcion : Método que realiza los cálculos y actualiza los campos necesarios
	 * @author  Nicolas Sarlabos 
	 * Fecha : 30/08/2011
	 */
	private void updateTable() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		
		try{
						
			sql = "SELECT ad_user_id,m_product_id,dateinvoiced,qtyinvoiced,linenetamt,c_invoiceline_id,"
				  + "c_currency_id,c_invoice_id FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id =" + this.ad_user_id;
			  	
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();

			while (rs.next()){
				String sql2="";
				boolean inUSD =false;
				if(rs.getInt("c_currency_id")==100){
					inUSD=true;
				}
				
				MProduct product= new MProduct(getCtx(), rs.getInt("m_product_id"),null);
				
				BigDecimal bom = product.getCostofechaHasta(rs.getTimestamp("dateinvoiced"));
												
				if(inUSD){
					bom=MConversionRate.convert(getCtx(), bom, 100, 142, rs.getTimestamp("dateinvoiced"), 
							0, Env.getAD_Client_ID(getCtx()), Env.getAD_Org_ID(getCtx()));
				}
				bom = bom.multiply(rs.getBigDecimal("qtyinvoiced"));
				sql2+=("UPDATE "+TABLA_MOLDE +" SET costoBomCant=" + bom + 
				" WHERE c_invoiceline_id=" + rs.getInt("c_invoiceline_id") + " AND m_product_id=" + rs.getInt("m_product_id") + ";");
				
				//Calculo margen bruto y guardo
				BigDecimal margin = rs.getBigDecimal("linenetamt") .subtract(bom);
				sql2+=("UPDATE "+TABLA_MOLDE +" SET grossMargin=" + margin + 
				" WHERE c_invoiceline_id=" + rs.getInt("c_invoiceline_id") + " AND m_product_id=" + rs.getInt("m_product_id") + ";");
				
				//System.out.println(product.getValue()+" "+bom+" "+ rs.getRow()+ "\n");
			
				DB.executeUpdate(sql2, null);
				
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
	 * 
	 * OpenUp.	issue #773
	 * Descripcion : Se crea el string de filtros
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 22/08/2011
	 */

	private String filtros() {
		String salida="";
		// Si tengo parametro para producto
		if(this.m_product_ID>0){
			salida +=" AND p.m_product_id="+this.m_product_ID;
		}
	
		// Si tengo parametro para fecha "desde"
		if(fechaDesde !=null){
			salida +=" AND inv.dateinvoiced>='"+ this.fechaDesde+"'";
		}
		// Si tengo parametro para fecha "hasta"
		if(fechaHasta !=null){
			salida +=" AND inv.dateinvoiced<='"+ this.fechaHasta+"'";
		}
		// Si tengo parametro para cliente
		if(c_bpartner_ID > 0){
			salida +=" AND bp.c_bpartner_id="+ this.c_bpartner_ID;
		}
		// Si tengo parametro para categoría de producto
		if(m_product_category_ID > 0){
			salida +=" AND pc.m_product_category_id="+ this.m_product_category_ID;
		}
		// Si tengo parametro para familia de producto
		if(uy_familia_ID > 0){
			salida +=" AND p.uy_familia_id="+ this.uy_familia_ID;
		}
		// Si tengo parametro para factura
		if(c_invoice_ID > 0){
			salida +=" AND inv.c_invoice_id="+ this.c_invoice_ID;
		}
		return salida;
	}

	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {

				if (name.equalsIgnoreCase("M_Product_ID")) {
					if (para[i].getParameter() != null) {
						this.m_product_ID = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("AD_User_ID")) {
					if (para[i].getParameter() != null) {
						this.ad_user_id = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
					
				if (name.equalsIgnoreCase("dateinvoiced")) {
				
					if (para[i].getParameter()!=null){
						this.fechaDesde =  (Timestamp) para[i].getParameter();
					}
						
					if (para[i].getParameter_To()!=null){
						this.fechaHasta =  (Timestamp) para[i].getParameter_To();
					}
				}
				
				if (name.equalsIgnoreCase("C_BPartner_ID")) {
					if (para[i].getParameter() != null) {
						this.c_bpartner_ID = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				
				if (name.equalsIgnoreCase("M_Product_Category_ID")) {
					if (para[i].getParameter() != null) {
						this.m_product_category_ID = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				
				if (name.equalsIgnoreCase("UY_Familia_ID")) {
					if (para[i].getParameter() != null) {
						this.uy_familia_ID = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				
				if (name.equalsIgnoreCase("C_Invoice_ID")) {
					if (para[i].getParameter() != null) {
						this.c_invoice_ID = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				
			}

		}
		
	}

	/**
	 * 
	 * OpenUp. issue #773	
	 * Descripcion : Elimino registros de instancias anteriores del reporte para el usuario actual.
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 22/08/2011
	 */
	private void deleteInstanciasViejasReporte() {

		String sql = "";
		try {

			sql = "DELETE FROM " + TABLA_MOLDE + " WHERE ad_user_id ="
					+ this.ad_user_id;

			DB.executeUpdate(sql, null);
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	
}
