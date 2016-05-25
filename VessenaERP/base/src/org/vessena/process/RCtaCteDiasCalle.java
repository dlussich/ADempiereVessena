/**
 * RCtaCteDiasCalle.java
 * 24/06/2011
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * RCtaCteDiasCalle
 * Descripcion : Reporte de Saldos de Clientes en la calle.
 * @author Mario Reyes
 * Fecha : 24/06/2011
 */
public class RCtaCteDiasCalle extends SvrProcess {

	private Timestamp fechaDesde = null;
	private boolean esClientes = true;
	private int idSocioNegocio = 0;
	private Long idUsuario = new Long(0);
	private String idReporte = "";
	private static final String CTA_CORRIENTE_CLIENTE = "CL";
	private static final String TABLA_MOLDE = "UY_MoldeComDiasCalle";
	/**
	 * Constructor
	 */
	public RCtaCteDiasCalle() {
		// TODO Auto-generated constructor stub
	}


	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde, los comprobantes a procesar segun filtros ingresados por el usuario
		this.loadComprobantes();

		return "ok";
	}

	@Override
	protected void prepare() {

		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;
		// Parametro para titulo del reporte
		ProcessInfoParameter paramTituloReporte = null;
		// Parametro para moneda
		//ProcessInfoParameter paramMonedaReporte = null;
		
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
				if (name.equalsIgnoreCase("DateInvoiced")){
					this.fechaDesde = (Timestamp)para[i].getParameter();
					para[i].setParameter(this.fechaDesde);
					para[i].setParameter_To(this.fechaDesde);
				}
				
				if (name.equalsIgnoreCase("AD_User_ID")){
					this.idUsuario = ((BigDecimal)para[i].getParameter()).longValueExact();
				}
				
				if (name.equalsIgnoreCase("C_BP_Group_ID")){
					this.esClientes = (para[i].getParameter().toString().equalsIgnoreCase(CTA_CORRIENTE_CLIENTE)) ? true : false;
				}
				
				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter()!=null){
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")){
							this.idSocioNegocio = ((BigDecimal)para[i].getParameter()).intValue();
						}						
					}						
				}
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(this.idUsuario);
		
		// Seteo titulo del reporte segun tipo cta corriente
		String tituloReporte = "";
		if (this.esClientes) tituloReporte = "Balancete Saldos - Deudores";
		else tituloReporte = "Balancete Saldos - Proveedores";
		
		// Si tengo parametro para titulo de reporte
		if (paramTituloReporte!=null) paramTituloReporte.setParameter(tituloReporte);

		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);
		
	}

	/* Carga comprobantes iniciales en tabla molde*/
	private void loadComprobantes() throws Exception{
	
		String insert ="", sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		//String trxAux=Trx.createTrxName();
		//Trx trans = Trx.get(trxAux, true);
		try
		{
			insert= "INSERT INTO " + TABLA_MOLDE + " (uy_canalventas_id, uy_zonareparto_id, salesrep_id, ad_user_id, ad_client_id ,ad_org_id ,value ,name ,c_paymentterm_id ,"+
			         " idreporte , idusuario, dateinvoiced ,totalcuenta ,  totalfacturado ,  diascalle, c_bpartner_id, open ,  doc )"; 
					  
						
			sql = "Select c_bpartner.uy_canalventas_id, c_bpartner.uy_zonareparto_id, c_bpartner.salesrep_id, "
					+ idUsuario
					+ " ,vuy_invoiceopenamt.ad_client_id, vuy_invoiceopenamt.ad_org_id,c_bpartner.value, "
					+ "c_bpartner.name, c_bpartner.c_paymentterm_id, '"
					+ idReporte
					+ "' ,"
					+ idUsuario
					+ ",'"
					+ fechaDesde
					+ "',0,0,0, (  "
					+
 "CASE when vuy_invoiceopenamt.c_bpartner_id > 0 then vuy_invoiceopenamt.c_bpartner_id "
					+
				"else 0 "+
				"end) as socio, sum( "+
 "CASE when vuy_invoiceopenamt.c_bpartner_id > 0 then openamt "
					+
				"else 0 "+
				"end) as open, ( "+
 "CASE WHEN vuy_invoiceopenamt.c_bpartner_id > 0 then coalesce((select sum(uy_mediospago.payamt)  from uy_mediospago  "
					+ "where uy_mediospago.c_bpartner_id = vuy_invoiceopenamt.c_bpartner_id "
					+
				"AND uy_mediospago.tipomp='TER' "+
				"and uy_mediospago.estado<>'REC' "+
				"and uy_mediospago.duedate >=adddays(now()::date, "+
				"(select cast(value as numeric(10,0)) "+
				"from ad_sysconfig "+
				"where ad_sysconfig.name='UY_DIAS_PLUS_CREDITO_VENCIDO')) "+
				"and uy_mediospago.docstatus in('CO','CL','RE') ),0) "+
				"else 0 "+
				"end) as doc "+
 "from vuy_invoiceopenamt "
					+ "inner join c_bpartner on vuy_invoiceopenamt.c_bpartner_id = c_bpartner.c_Bpartner_id "
					+
				"where c_bpartner.iscustomer = 'Y' "+  getWhereClause() + 
 "group by vuy_invoiceopenamt.c_bpartner_id, vuy_invoiceopenamt.ad_client_id, vuy_invoiceopenamt.ad_org_id,c_bpartner.value, c_bpartner.name,c_bpartner.c_paymentterm_id, c_bpartner.uy_canalventas_id, c_bpartner.uy_zonareparto_id, c_bpartner.salesrep_id "
					+
				"order by open desc ";
			
			DB.executeUpdate(insert + sql, null);
			
			DB.executeUpdate("UPDATE " + TABLA_MOLDE + " SET totalcuenta = (doc+open) where idreporte = '" +idReporte + "'", null);
			/*
			sql = "Select (doc + open) as totalcuenta, C_Bpartner_id from "+ TABLA_MOLDE +
			" where idreporte = '" +idReporte + "'";
				*/
			//pstmt = DB.prepareStatement (sql, null);
			//rs = pstmt.executeQuery ();
			/*
			while (rs.next()){
				BigDecimal totalcuenta = rs.getBigDecimal("totalcuenta").setScale(2, BigDecimal.ROUND_HALF_UP);
				if (totalcuenta.compareTo(Env.ZERO)!=0){
					
					//BigDecimal diasCalle = (totalcuenta.divide(totalfacturado, 2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(90));

					System.out.println("UPDATE "+ TABLA_MOLDE +" set totalcuenta = "+ totalcuenta  );					
					
					DB.executeUpdate("UPDATE "+ TABLA_MOLDE +" set totalcuenta = "+ totalcuenta + " where idreporte = '" +idReporte + "' AND idusuario = " +idUsuario + " and c_bpartner_id = " + rs.getInt("C_BPartner_ID")   , null);
				
				}
				}*/
					ResultSet rs1 = null;
					PreparedStatement pstmt1 = null;
					String sqltotal = "";
					
					sqltotal = 	" Select sum(((importemn * rate)/100) + importemn) as total, c_bpartner_id from "
							+ "(select ph_ventasglobal.dia, ph_ventasglobal.c_bpartner_id, ph_ventasglobal.m_product_id, c_tax.rate, ph_ventasglobal.importemn "
							+ "from ph_ventasglobal "
							+ "left join m_product on ph_ventasglobal.m_product_id = m_product.m_product_id "
							+ "left join c_taxcategory on m_product.c_taxcategory_id = c_taxcategory.c_taxcategory_id "
							+ "left join c_tax on c_taxcategory.c_taxcategory_id = c_tax.c_taxcategory_id) as p" + getWhereTruncClause()
							+ "group by c_bpartner_id ";
					
					pstmt1 = DB.prepareStatement (sqltotal, null);
					rs1 = pstmt1.executeQuery ();
					
				
			while (rs1.next()) {
				BigDecimal facturado = rs1.getBigDecimal("total").setScale(2, BigDecimal.ROUND_HALF_UP);
				if ((facturado.compareTo(Env.ZERO) > 0) || facturado.compareTo(Env.ZERO) < 0){
					DB.executeUpdate("UPDATE " + TABLA_MOLDE + " set totalfacturado = " + facturado + ", diascalle = ((totalcuenta / " + facturado
							+ ")*90) where idreporte = '" + idReporte + "' AND idusuario = " + idUsuario + " and c_bpartner_id = "
							+ rs1.getInt("c_bpartner_id"), null);
				}
 else {
					DB.executeUpdate("UPDATE " + TABLA_MOLDE + " set totalfacturado = " + facturado + ", diascalle = 0 where idreporte = '" + idReporte
							+ "' AND idusuario = " + idUsuario + " and c_bpartner_id = " + rs1.getInt("c_bpartner_id"), null);
				}
				
			}
				
				
			
			
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
			//trans.rollback();
			throw new Exception(e);
			
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		//trans.commit();
		//trans.close();
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
	private String getWhereClause(){
		
		String salida = " and vuy_invoiceopenamt.dateinvoiced <= '" + fechaDesde + "' ";
		if (idSocioNegocio > 0)
			salida += " AND vuy_invoiceopenamt.C_BPartner_ID = " + idSocioNegocio;
	return salida;
	}
	private String getWhereTruncClause(){
		
		String salida = " where dia between ( SELECT date_trunc('day', timestamp '" +fechaDesde+ "' ) - (interval '3 month') ) AND  ( SELECT (timestamp '" +fechaDesde+ "' ) - (interval '1 day') ) ";
		if (idSocioNegocio >0 ) salida += " AND C_BPartner_ID = "+ idSocioNegocio;
	return salida;
	}


}
