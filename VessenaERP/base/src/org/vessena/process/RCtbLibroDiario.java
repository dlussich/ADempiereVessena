package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_GL_Journal;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.I_UY_MovBancariosHdr;

/**
 * OpenUp. RLibroDiario 
 * Descripción:  reporte de libro diario, el reporte obtendrá todos los asientos contables que se hicieron en un periodo determinado  
 * @author Guillermo Brust 
 * ISSUE #26 - Version 2.5.1 
 * Fecha : 10/09/2012
 */

public class RCtbLibroDiario extends SvrProcess {
	
	private int periodoDesde = 0;
	private int periodoHasta = 0;
	private int C_Acctschema_ID = 0;
	private String empresaReporte = "";
	private Long transporte = new Long(0);
	 
	private int adUserID = 0;
	private int adClientID = 0;
	private int adOrgID = 0;	
	
	private String idReporte = "";
		
	private static final String TABLA_MOLDE = "UY_Molde_RctbLibroDiario";
	

	public RCtbLibroDiario() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		//OpenUp. Nicolas Sarlabos. 06/08/2013. #776. Agrego parametro de idReporte
		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;

		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				
				if (name.equalsIgnoreCase("idReporte")){
					paramIDReporte = para[i]; 
				}
				 
				if(name.equalsIgnoreCase("C_Period_ID")){
					this.periodoDesde = ((BigDecimal)para[i].getParameter()).intValueExact();
					this.periodoHasta = ((BigDecimal)para[i].getParameter_To()).intValueExact();
				}
								
				if (name.equalsIgnoreCase("C_Acctschema_ID")){
					this.C_Acctschema_ID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}	
				
				if (name.equalsIgnoreCase("empresaReporte")){
					this.empresaReporte = para[i].getParameter().toString();
				}
				
				if (name.equalsIgnoreCase("transporte")){
					this.transporte = ((BigDecimal)para[i].getParameter()).longValueExact();
				}
				
				if (name.equalsIgnoreCase("AD_User_ID")){
					this.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("AD_Org_ID")){
					this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}		
			}
		}
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));
		
		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);
		//Fin #776 06/08/2013
	}

	@Override
	protected String doIt() throws Exception {
		// Delete de reportes anteriores de este usuario para ir limpiando la
		// tabla molde
		this.deleteInstanciasViejasReporte();

		// Obtengo y cargo en tabla molde, los movimientos segun filtro indicado
		// por el usuario.
		this.loadMovimientos();
		
		//Se Actualiza las descripciones para los asientos diarios
		this.descripcionParaAsientosDiarios();
		
		//Se Actualiza las descripciones para los asientos generados por movimiento bancario
		this.descripcionParaAsientosMovBank();
		
		//Se Actualiza las descripciones para los asientos generados por compra o venta
		this.descripcionParaAsientosCompraVenta();

		// Me aseguro de no mostrar registros con
		//this.deleteBasuraTemporal();

		return "ok";
	}

	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id =" + this.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}


	/* Carga movimientos en la tabla molde. */
	private void loadMovimientos(){
		
		String insert = "", sql = "", where = "";
		
		if(this.periodoDesde != 0) where += " AND asiento.dateacct >= (SELECT startdate FROM c_period WHERE c_period_id = " + this.periodoDesde + ")";
		if(this.periodoHasta != 0) where += " AND asiento.dateacct <= (SELECT enddate FROM c_period WHERE c_period_id = " + this.periodoHasta + ")";
		if(this.C_Acctschema_ID != 0) where += " AND asiento.c_acctschema_id = " + this.C_Acctschema_ID;
		
				
		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, ad_user_id, idreporte, fecreporte, " +
		                                              "dateacct, record_id, c_elementvalue_id, descuenta, amtacctdr, amtacctcr, desasiento, ad_table_id, empresaReporte, transporte)" ;
			
								
			sql = " SELECT " + this.adClientID + ", " + this.adOrgID + ", " + this.adUserID + ", '" + this.idReporte + "', current_date, asiento.dateacct, asiento.record_id, " +
						  "asiento.account_id, cuenta.name, asiento.amtacctdr, asiento.amtacctcr, asiento.description, asiento.ad_table_id, '" + this.empresaReporte + "', " + this.transporte + 					     
			      " FROM fact_acct asiento " + 			     
				  " INNER JOIN c_elementvalue cuenta ON asiento.account_id = cuenta.c_elementvalue_id" +			      
				  " WHERE 1=1 " + where +
			      " ORDER BY asiento.dateacct asc, asiento.record_id asc " ;
									  
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
			
		}
	}
	
	/*  Se actualiza la descripcion del asiento, se logra con TipoDoc, Documentno y cuentaBancaria, de los movimientos bancarios */
	private void descripcionParaAsientosMovBank(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		 
		
		try{			
			
			//Obtengo los numero de asiento de la tabla molde, pero solo los que tambien se encuentran en la tabla uy_movbancarioshdr, de esta forma me aseguro de que
			//son asiento de movimientos bancarios
			sql = "SELECT " + TABLA_MOLDE + ".record_id, coalesce(movbancarios.documentno,'') as documentno, doc.printname, coalesce(ba.description,'') as badesc " +
				  " FROM " + TABLA_MOLDE +
				  " INNER JOIN uy_movbancarioshdr movBancarios ON " + TABLA_MOLDE + ".record_id = movBancarios.uy_movbancarioshdr_id" +
				  " INNER JOIN c_doctype doc on movbancarios.c_doctype_id = doc.c_doctype_id " +
				  " LEFT OUTER JOIN c_bankaccount ba on movbancarios.c_bankaccount_id = ba.c_bankaccount_id " +
				  " WHERE idreporte=?" +
				  " AND " + TABLA_MOLDE + ".ad_table_id = " + I_UY_MovBancariosHdr.Table_ID ;
				  
				
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, this.idReporte);
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				
				/*
				MMovBancariosHdr hdr = new MMovBancariosHdr(getCtx(), rs.getInt("record_id"), null);
				String documentName = new MDocType(getCtx(), hdr.getC_DocType_ID(), null).getName();
				String nombreCuentaBancaria = new MBankAccount(getCtx(), hdr.getC_BankAccount_ID(), null).getDescription();
				*/
				
				String desc = rs.getString("printname") + "_" + rs.getString("documentno") + "_" + rs.getString("badesc");
									
				//mando a actualizar la descripcion
				if (!desc.equals(""))
					this.updateDescripcion(desc, rs.getInt("record_id"));		
															
			}				

		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;			
		}
	}
	
	/*  Se actualiza la descripcion del asiento, se logra con /*TipoDoc, Documentno y SocioNegocio, de los las compras y ventas */
	private void descripcionParaAsientosCompraVenta(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		 
		
		try{			
			
			//Obtengo los numero de asiento de la tabla molde, pero solo los que tambien se encuentran en la tabla c_invoice, de esta forma me aseguro de que
			//son asiento de compras o ventas
			sql = "SELECT " + TABLA_MOLDE + ".record_id, doc.printname, bp.name, inv.documentno " +
				  " FROM " + TABLA_MOLDE +
				  " INNER JOIN c_invoice inv ON " + TABLA_MOLDE + ".record_id = inv.c_invoice_id " +
				  " INNER JOIN c_doctype doc ON inv.c_doctypetarget_id = doc.c_doctype_id " +
				  " INNER JOIN c_bpartner bp ON inv.c_bpartner_id = bp.c_bpartner_id " +
				  " WHERE idreporte=? and inv.docstatus = 'CO'" +
				  " AND " + TABLA_MOLDE + ".ad_table_id = " + I_C_Invoice.Table_ID;
				  
				
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, this.idReporte);
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				
				/*
				MInvoice inv = new MInvoice(getCtx(), rs.getInt("record_id"), null);
				String documentName = new MDocType(getCtx(), inv.getC_DocType_ID(), null).getName();
				String socioNegocio = new MBPartner(getCtx(), inv.getC_BPartner_ID() , null).getName();
				*/
				
				String desc = rs.getString("printname") + "_" + rs.getString("documentno") + "_" + rs.getString("name");
						
				//mando a actualizar la descripcion
				if (!desc.equals(""))
					this.updateDescripcion(desc, rs.getInt("record_id"));										
			}				

		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;			
		}
	}
	
	/*  Se actualiza la descripcion del asiento, se logra con /*TipoDoc, Documentno y descripcionAnterior */
	private void descripcionParaAsientosDiarios(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;		 
		
		try{			
			
			//Obtengo los numero de asiento de la tabla molde, pero solo los que tambien se encuentran en la gl_journal, de esta forma me aseguro de que
			//son asiento diarios
			sql = "SELECT " + TABLA_MOLDE +	".record_id, " + TABLA_MOLDE +	".desasiento" +
				  " FROM " + TABLA_MOLDE +	
				  " INNER JOIN gl_journal gl ON gl.gl_journal_id = " + TABLA_MOLDE + ".record_id" +
				  " WHERE idreporte=? " +				  
				  " AND " + TABLA_MOLDE + ".ad_table_id = " + I_GL_Journal.Table_ID;
				
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, this.idReporte);
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
									
				String desc = "Asiento Diario_" + rs.getString("desasiento");
						
				//mando a actualizar la descripcion
				if (!desc.equals(""))
					this.updateDescripcion(desc, rs.getInt("record_id"));										
			}				

		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;			
		}
	}
	

	/* Actualiza la descripcion del asiento pasado por parametro con la descripcion tambien pasada por parametro*/
	private void updateDescripcion(String unaDescripcion, int unNroAsiento){

		String sql = "";
		
		try{
			sql = "UPDATE " + TABLA_MOLDE + 
				  " SET desasiento = '" + unaDescripcion + "'" +
				  " WHERE idreporte='" + this.idReporte + "'" +
				  " AND record_id =" + unNroAsiento;
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	

}
