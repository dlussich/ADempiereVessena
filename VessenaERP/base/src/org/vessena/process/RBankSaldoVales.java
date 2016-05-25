package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MMovBancariosHdr;

/**
 * OpenUp. RBankSaldoVales 
 * Descripción:  
 * @author Guillermo Brust 
 * ISSUE #18 - Version 2.5.1 
 * Fecha : 05/09/2012
 */

public class RBankSaldoVales extends SvrProcess {
	
	private Timestamp fechaSaldoHasta = null;	
	private Timestamp fechaEmisionDesde = null;
	private Timestamp fechaEmisionHasta = null;
	private Timestamp fechaVencimientoDesde = null;
	private Timestamp fechaVencimientoHasta = null;	

	private int adUserID = 0;
	private int adClientID = 0;
	private int adOrgID = 0;
	
	
	private String idReporte = "";
		
	private static final String TABLA_MOLDE = "UY_Molde_RBSaldoValesV2";
	

	public RBankSaldoVales() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		// Obtengo parametros y los recorro
				ProcessInfoParameter[] para = getParameter();
				for (int i = 0; i < para.length; i++)
				{
					String name = para[i].getParameterName().trim();
					if (name!= null){
						
						if (name.equalsIgnoreCase("EndDate")){
							this.fechaSaldoHasta = (Timestamp)para[i].getParameter();							
						}

						if (name.equalsIgnoreCase("DateTrx")){
							this.fechaEmisionDesde = (Timestamp)para[i].getParameter();
							this.fechaEmisionHasta = (Timestamp)para[i].getParameter_To();
						}
						
						if (name.equalsIgnoreCase("DueDate")){
							this.fechaVencimientoDesde = (Timestamp)para[i].getParameter();
							this.fechaVencimientoHasta = (Timestamp)para[i].getParameter_To();
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
	}

	@Override
	protected String doIt() throws Exception {
		
		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();

		//Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.
		this.loadMovimientos();

		//Calculo saldos a pagar
		//this.calculoSaldosAPagar();

		//Me aseguro de no mostrar registros con saldo en cero
		this.deleteBasuraTemporal();

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

	/* Elimino registros de tabla temporal con entradas y salidas en cero. No deberia haber ningun registro pero me cubro de errores. */
	private void deleteBasuraTemporal(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE idreporte = '" + this.idReporte + "'" + 
				  " AND deuda = 0";
				 
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : Carga movimientos de cuentas en la tabla molde.
	 * @author  Guillermo Brust
	 * Fecha : 05/09/2012
	 */
	private void loadMovimientos(){
		
		String insert = "", sql = "", filtrosWhere = "";
		
		if(this.fechaEmisionDesde!= null) filtrosWhere = " AND vale.datetrx >= '" + this.fechaEmisionDesde + "'";
		if(this.fechaEmisionHasta!= null) filtrosWhere += " AND vale.datetrx <= " + this.fechaEmisionHasta + "'";
		if(this.fechaVencimientoDesde != null) filtrosWhere += " AND vale.duedate >= " +  this.fechaVencimientoDesde + "'";
		if(this.fechaVencimientoHasta !=null) filtrosWhere += " AND vale.duedate <= " + this.fechaVencimientoHasta + "'";
		if(this.fechaSaldoHasta != null) filtrosWhere += " AND vale.datetrx <= '" + this.fechaSaldoHasta + "'";
		
		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, ad_user_id, idreporte, datetrx, duedate, " + 
		                                                "documentno, uy_subtotal, deuda, c_currency_id, c_bankaccount_id) ";
			
			/*					
			sql = "SELECT " + this.adClientID + ", " + this.adOrgID + ", " + this.adUserID + ", '" + this.idReporte + "', current_date, hdr.c_bankaccount_id, hdr.datetrx," + 
					        " hdr.duedate, hdr.uy_movbancarioshdr_id, hdr.c_currency_id, hdr.uy_total_manual, 0, hdr.documentno" +
			      " FROM uy_movbancarioshdr hdr" + 
				  " INNER JOIN c_doctype doc ON hdr.c_doctype_id = doc.c_doctype_id" +
			      " WHERE doc.docbasetype = 'BPB' AND hdr.docstatus = 'CO'" + filtrosWhere;			
			*/
			
			//se modifica la select y se basa en la select de la vista que se utilizaba en el reporte anterior
			
			sql = "SELECT vale.ad_client_id, vale.ad_org_id, "+ this.adUserID + ", " + this.idReporte + ", vale.datetrx, vale.duedate," +
					" vale.documentno, vale.uy_subtotal, vale.uy_subtotal - COALESCE(sum(pagosvale.uy_subtotal), 0::numeric) AS deuda, vale.c_currency_id, vale.c_bankaccount_id" +
			      " FROM uy_movbancarioshdr vale" +
			      " LEFT JOIN uy_movbancarioshdr pagosvale ON COALESCE(pagosvale.uy_numvale::numeric, 0::numeric) = vale.uy_movbancarioshdr_id AND (pagosvale.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))" +
			      " LEFT JOIN c_doctype doc ON doc.c_doctype_id = vale.c_doctype_id" +
			      " WHERE doc.docbasetype = 'BPB'::bpchar AND vale.docstatus = 'CO'::bpchar " + filtrosWhere + 
			      " GROUP BY vale.datetrx, vale.duedate, vale.documentno, vale.uy_subtotal, vale.ad_client_id, vale.ad_org_id, vale.c_bankaccount_id, vale.c_currency_id " +
			      " ORDER BY vale.datetrx, vale.duedate, vale.documentno, vale.uy_subtotal, vale.ad_client_id, vale.ad_org_id";
									  
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
			
		}
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Calcula saldos a pagar hasta la fecha elegida por el usuario.
	 * @author  Guillermo Brust
	 * Fecha : 03/10/2010
	 */
	
	/*
	private void calculoSaldosAPagar(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		 
		
		try{	
			
			//Obtengo los numero de vale bancarios de la tabla molde
			sql = "SELECT uy_movbancarioshdr_id FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?";
				
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, this.idReporte);
			rs = pstmt.executeQuery ();
			
			while(rs.next()){				
				
				BigDecimal saldoPendiente = MMovBancariosHdr.getSaldoPendienteVale(getCtx(), rs.getInt("uy_movbancarioshdr_id"), 
										 this.fechaSaldoHasta, null);
			
				
				if (saldoPendiente.compareTo(Env.ZERO) != 0 )
					this.updateSaldoPendiente(saldoPendiente, rs.getInt("uy_movbancarioshdr_id"));						
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
	
	*/

	/**
	 * OpenUp.	
	 * Descripcion : Actualiza Saldo Pendiente y Total a Pagar para un determinado numero de vale en la tabla temporal.
	 * @param valor
	 * @param numero de vale 
	 * @author  Guillermo Brust 
	 * Fecha : 06/09/2012
	 */
	
	/*
	private void updateSaldoPendiente(BigDecimal saldoPendiente, int idNumeroVale){

		String sql = "";
		
		try{
			sql = "UPDATE " + TABLA_MOLDE + 
				  " SET saldo = " + saldoPendiente +
				  " WHERE idreporte='" + this.idReporte + "'" +
				  " AND uy_movbancarioshdr_id =" + idNumeroVale;
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	*/

}
