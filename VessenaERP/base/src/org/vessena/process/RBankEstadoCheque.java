/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 12/12/2014
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MCheckTracking;
/**
 * org.openup.process - RBankEstadoCheque
 * OpenUp Ltda. Issue #2359.
 * Description: Reporte de Estados de Cheques por cuenta bancaria.
 * @author Nicolas Sarlabos - 26/11/2015
 * @see
 */
public class RBankEstadoCheque extends SvrProcess {


	  private int adUserID = 0;
	  private int adOrgID = 0;
	  private int adClientID = 0;
	  private String estado = "";
	  private Timestamp duedate_Desde = null;
	  private Timestamp duedate_Hasta = null;
	  private Timestamp dateTrx_Desde = null;
	  private Timestamp dateTrx_Hasta = null;
	  private int c_bpartner_id = 0;
	  private int c_bankaccount_id = 0;
	  private Timestamp dateHasta = null;
	  private static final String TABLA_MOLDE = "UY_Molde_EstadoCheque";

	  /**
	   *Constructor.
	  */
	  public RBankEstadoCheque() {
	  }

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 12/12/2014
	 * @see
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			
			if (name != null) {
							
			
				if (name.equalsIgnoreCase("AD_Client_ID")) {
					this.adClientID = ((BigDecimal) para[i].getParameter()).intValueExact();
				}else{
					this.adClientID = Env.getAD_Client_ID(getCtx());
				}
				
		
				if (name.equalsIgnoreCase("AD_Org_ID")) {
					this.adOrgID = ((BigDecimal) para[i].getParameter()).intValueExact();
				}else{
					this.adOrgID = Env.getAD_Org_ID(getCtx());
				}
				
				
				if (name.equalsIgnoreCase("AD_User_ID")) {
					this.adUserID = ((BigDecimal) para[i].getParameter()).intValueExact();
	 			}else{
					this.adUserID= Env.getAD_User_ID(getCtx());
				}
				
			
				if (name.equalsIgnoreCase("DateTrx")){
					if (para[i].getParameter()!=null)
						this.dateTrx_Desde = (Timestamp)para[i].getParameter();
					if (para[i].getParameter_To()!=null)
						this.dateTrx_Hasta = (Timestamp)para[i].getParameter_To();
				}
				if (name.equalsIgnoreCase("DueDate")){
					if (para[i].getParameter()!=null)
						this.duedate_Desde = (Timestamp)para[i].getParameter();
					if (para[i].getParameter_To()!=null)
						this.duedate_Hasta = (Timestamp)para[i].getParameter_To();
				}
				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter()!=null){
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")){
							this.c_bpartner_id = ((BigDecimal)para[i].getParameter()).intValue();
						}						
					}
				}						
				if (name.equalsIgnoreCase("C_BankAccount_Id")){
					if (para[i].getParameter()!=null){
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")){
							this.c_bankaccount_id = ((BigDecimal)para[i].getParameter()).intValue();
						}						
					}	
				}
				if (name.equalsIgnoreCase("DateTo")){
					if (para[i].getParameter()!=null)
						this.dateHasta = (Timestamp)para[i].getParameter();
					}						
				}	
				
				if(name.equalsIgnoreCase("estado")){
					if (para[i].getParameter()!=null){
						this.estado = (String)para[i].getParameter();
				
					}
				}
			}
		}
			
		
	
	@Override
	protected String doIt() throws Exception {
		  
	    deleteInstanciasViejasReporte();
	    loadData();
	    
	    return "OK";
	}
	 
	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id = " + this.adUserID;
			
			DB.executeUpdate(sql,null);			
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
	
	  private void loadData() {

		  String sql = "", insert = "", whereFiltros = "";
		  MCheckTracking trackLine = null;
		  ResultSet rs = null;
		  PreparedStatement pstmt = null;	

		  try
		  {
			   

			  whereFiltros =  " and mp.ad_client_id = " + this.adClientID +    
					  " and mp.ad_org_id = " + this.adOrgID; 

			  if(this.c_bankaccount_id > 0){
				  whereFiltros = " AND mp.c_bankaccount_id =" + this.c_bankaccount_id;
			  }					
			  if(this.c_bpartner_id > 0){
				  whereFiltros = " AND mp.c_bpartner_id =" + this.c_bpartner_id;
			  }
			  if(this.duedate_Desde != null){
				  whereFiltros = " AND mp.duedate >= " + this.duedate_Desde;
			  }
			  if(this.duedate_Hasta != null){
				  whereFiltros += " AND mp.duedate <= " + this.duedate_Hasta;	
			  }
			  if(this.dateTrx_Desde != null){
				  whereFiltros = " AND mp.dateTrx >= '" + this.dateTrx_Desde;
			  }
			  if(this.dateTrx_Hasta != null){
				  whereFiltros +=  "' AND mp.dateTrx <= '" + this.dateTrx_Hasta + "'" ;
			  }			  								  				 

			  sql = " SELECT mp.uy_mediospago_id, tr.uy_checktracking_id, mp.datetrx, mp.c_doctype_id,mp.checkno, mp.c_bpartner_id," +
					  " mp.c_bankaccount_id, mp.duedate, mp.c_currency_id, mp.payamt, tr.datetrx as dateto, tr.checkstatus, coalesce(mp.micr,' ') as observaciones" +
					  " FROM uy_mediospago mp" +
					  " INNER JOIN uy_checktracking tr ON mp.uy_mediospago_id = tr.uy_mediospago_id" +
					  " WHERE mp.docstatus = 'CO' AND tr.docaction = 'CO'" +
					  " AND tr.checkstatus = '" + this.estado + "' AND tr.datetrx >= '2015-01-01 00:00:00' AND tr.datetrx <= '" + this.dateHasta + "'" + whereFiltros +
					  " ORDER BY tr.datetrx DESC";
			  
				pstmt = DB.prepareStatement (sql, null);
				rs = pstmt.executeQuery ();
				
				while (rs.next()){

					//verifico si hay una linea de tracking posterior para el cheque en estado completo
					trackLine = MCheckTracking.forHdrLineDate(getCtx(), rs.getInt("uy_mediospago_id"), rs.getInt("uy_checktracking_id"), this.dateHasta, get_TrxName());

					if(trackLine == null){

						insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, ad_user_id, datetrx, c_doctype_id, checkno, c_bpartner_id," +
								" c_bankaccount_id, duedate, c_currency_id, payamt, dateto, observaciones, estado) " ;

						sql = "SELECT " + this.adClientID + "," + this.adOrgID + "," + this.adUserID + ",'" + rs.getTimestamp("datetrx") + "'," +
								rs.getInt("c_doctype_id") + ",'" + rs.getString("checkno") + "'," + rs.getInt("c_bpartner_id") + "," + 
								rs.getInt("c_bankaccount_id") + ",'" + rs.getTimestamp("duedate") + "'," + rs.getInt("c_currency_id") + "," +
								rs.getBigDecimal("payamt") + ",'" + rs.getTimestamp("dateto") + "','" + rs.getString("observaciones") + "','" + 
								rs.getString("checkstatus") + "'";

						DB.executeUpdateEx(insert + sql, null);						

					}			

				}
				
		  }
		  catch (Exception e)
		  {
			  throw new AdempiereException(e);
		  }
		    
	}
}
