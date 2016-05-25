/**
 * 
 */
package org.openup.process;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * OpenUp Guillermo Brust 08/04/2013
 * Reporte de Presupuestos ISSUE #637
 * Reportes con las siguientes columnas en este orden;
 * Nº de presupuesto / Nombre cliente / nombre del trabajo / Nº orden de fabricación / estado de ese trabajo / fechas de entrega / tipo de moneda / monto total de ese trabajo.
 */
public class RProdPresupuestos extends SvrProcess {
	
	private static String TABLA_MOLDE = "UY_Molde_RProdPresupuestos";
	
	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	private int cbpartnerID = 0;
	private String docStatus = "";	
		
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;
	private int currencyID = 0;	
	private String idReporte = "";
	
		
	public RProdPresupuestos() {
		
	}
	
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
					
				if (name.equalsIgnoreCase("DateTrx")){
					this.fechaDesde = (Timestamp)para[i].getParameter();	
					this.fechaHasta = (Timestamp)para[i].getParameter_To();	
				}			
				
				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter()!=null){
						this.cbpartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				} 				
				if (name.equalsIgnoreCase("DocStatus")){
					if (para[i].getParameter()!=null){
						this.docStatus = (String)para[i].getParameter();
					}
				}			
				if (name.equalsIgnoreCase("AD_User_ID")){
					if (para[i].getParameter()!=null){
						this.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					if (para[i].getParameter()!=null){
						this.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
				if (name.equalsIgnoreCase("AD_Org_ID")){
					if (para[i].getParameter()!=null){
						this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}		
				//OpenUp. Nicolas Sarlabos. 30/09/2013. #1358.
				if (name.equalsIgnoreCase("C_Currency_ID")){
					if (para[i].getParameter()!=null){
						this.currencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
				//Fin OpenUp #1358
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));

	}

	
	@Override
	protected String doIt() throws Exception {
		
		//Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
								
		//Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.		
		this.loadMovimientos();	
						
		return "ok";
	}
	
	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE AD_User_ID =" + this.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
	}	
	
	/* Carga movimientos en la tabla molde. */
	private void loadMovimientos(){
		//OpenUp. Nicolas Sarlabos. 30/09/2013. #1358. Modificaciones varias para considerar segunda moneda.
		String insert = "", sql = "", where = " where", where2 = "";
		
		if(this.fechaDesde != null){
			if(this.fechaHasta != null){
				where += " b.dateapproved between '" + this.fechaDesde + "' and '" + this.fechaHasta + "'";
			}else throw new AdempiereException("Debe elegir la fecha final");
		}
		
		if(this.adOrgID > 0){
			if(where.equals(" where")){
				where+= " b.ad_org_id = " + this.adOrgID;
			}else where+= " and b.ad_org_id = " + this.adOrgID;
		}
		
		if(this.cbpartnerID > 0){
			if(where.equals(" where")){
				where+= " b.c_bpartner_id = " + this.cbpartnerID;
			}else where+= " and b.c_bpartner_id = " + this.cbpartnerID;
		}

		if(!this.docStatus.equals("")){
			if(where.equals(" where")){
				if(this.docStatus.equals("EN")){
					where+= " b.docstatus = 'DR' AND b.sent = 'Y'";
				}else where+= " b.docstatus = '" + this.docStatus + "'";

			}else{
				if(this.docStatus.equals("EN")){
					where+= " and b.docstatus = 'DR' AND b.sent = 'Y'";
				} else where+= " and b.docstatus = '" + this.docStatus + "'";
			}	
		}

		try
		{			
			// Obtengo esquema para saber cual es la moneda nacional.
			MClient client = new MClient(getCtx(), this.adClientID, null);
			MAcctSchema schema = client.getAcctSchema();
			int currencySchemaID = schema.getC_Currency_ID(); //obtengo ID de moneda nacional
			
			if(where.equalsIgnoreCase(" where")){
				where2+= " b.c_currency_id = " + currencySchemaID;
			} else where2+= " and b.c_currency_id = " + currencySchemaID;
					
			//Primero inserto solo para moneda del esquema contable
			insert = "INSERT INTO " + TABLA_MOLDE;
								
			sql = " select b.documentno, cb.name, b.workname, o.documentno, case when b.docstatus = 'CO' then 'APROBADO' when b.docstatus = 'DR' then (case when b.sent = 'Y' then 'ENVIADO' else 'BORRADOR' end) else 'ANULADO' END, b.dateapproved, cur.description, (amt1 + amt2 + amt3), " +
				  " b.ad_org_id, " + this.adClientID + ", " + this.adUserID + ", '" + idReporte + "', b.serie as SeriePresupuesto, o.serie as SerieOrden, 0" + 
				  " from uy_budget b" +
				  " left join uy_manuforder o on b.uy_budget_id = o.uy_budget_id" +
				  " inner join c_bpartner cb on b.c_bpartner_id = cb.c_bpartner_id" +
				  " inner join uy_budgetline bl on b.uy_budget_id = bl.uy_budget_id" +
				  " inner join c_currency cur on b.c_currency_id = cur.c_currency_id" +
				  (!where.equals("where") ? where : "") + where2;
									  
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, null);
			
			//agrego filtro de segunda moneda al WHERE
			if(this.currencyID > 0){
				if(where.equals(" where")){
					where+= " b.c_currency_id = " + this.currencyID;
				}else where+= " and b.c_currency_id = " + this.currencyID;
			}
			
			//Inserto solo para moneda elegida en el filtro (segunda moneda)
			insert = "INSERT INTO " + TABLA_MOLDE;
								
			sql = " select b.documentno, cb.name, b.workname, o.documentno, case when b.docstatus = 'CO' then 'APROBADO' when b.docstatus = 'DR' then (case when b.sent = 'Y' then 'ENVIADO' else 'BORRADOR' end) else 'ANULADO' END, b.dateapproved, cur.description, 0, " +
				  " b.ad_org_id, " + this.adClientID + ", " + this.adUserID + ", '" + idReporte + "', b.serie as SeriePresupuesto, o.serie as SerieOrden, (amt1 + amt2 + amt3)" + 
				  " from uy_budget b" +
				  " left join uy_manuforder o on b.uy_budget_id = o.uy_budget_id" +
				  " inner join c_bpartner cb on b.c_bpartner_id = cb.c_bpartner_id" +
				  " inner join uy_budgetline bl on b.uy_budget_id = bl.uy_budget_id" +
				  " inner join c_currency cur on b.c_currency_id = cur.c_currency_id" +
				  (!where.equals("where") ? where : "");									  
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, null);			
			//Fin OpenUp #1358
 		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
			
		}
	}

}
