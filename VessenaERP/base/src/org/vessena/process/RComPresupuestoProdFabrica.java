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
import org.openup.model.MMoldePresupuestoProdFab;

public class RComPresupuestoProdFabrica extends SvrProcess {
	
private static String TABLA_MOLDE = "UY_Molde_PresupuestoProdFab";
	
	private Timestamp fechaRemDesde = null;
	private Timestamp fechaRemHasta = null;
	private int activityID = 0;
	private int cbpartnerID = 0;
	private int parentID = 0;
	private int budgetID = 0;
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;	
	private String idReporte = "";
	

	public RComPresupuestoProdFabrica() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
					
				if (name.equalsIgnoreCase("DateTrx")){
					this.fechaRemDesde = (Timestamp)para[i].getParameter();	
					this.fechaRemHasta = (Timestamp)para[i].getParameter_To();	
				}			
				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter()!=null){
						this.cbpartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
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
				if (name.equalsIgnoreCase("C_Activity_ID")){
					if (para[i].getParameter()!=null){
						this.activityID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}	
				if (name.equalsIgnoreCase("BPartner_Parent_ID")){
					if (para[i].getParameter()!=null){
						this.parentID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				} 
				if (name.equalsIgnoreCase("UY_Budget_ID")){
					if (para[i].getParameter()!=null){
						this.budgetID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				
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
		this.loadBudget();
		//actualizo tabla molde
		this.updateTable();
		
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
	
	private void loadBudget(){
		
		String sql = "", insert = "", where = "where b.docstatus = 'CO'";
		
		if(this.activityID > 0){
			where += " and b.c_activity_id = " + this.activityID;			
		}
		
		if(this.parentID > 0) where+= " and cbp.bpartner_parent_id = " + this.parentID;
				
		if(this.cbpartnerID > 0) where+= " and b.c_bpartner_id = " + this.cbpartnerID;
				
		if(this.budgetID > 0) where+= " and b.uy_budget_id = " + this.budgetID;
				
		if(this.adOrgID > 0) where+= " and b.ad_org_id = " + this.adOrgID;
				
		try {
		
		insert = "INSERT INTO " + TABLA_MOLDE;
		
		sql = " select distinct nextid(1001671,'N'),b.ad_org_id," + this.adClientID + "," + this.adUserID + ",'" + this.idReporte + "',b.uy_budget_id,b.c_bpartner_id,coalesce(cbp.bpartner_parent_id,0),null::timestamp,b.c_activity_id,bl.uy_budgetline_id," +
				" bl.m_product_id,bl.description,b.salesrep_id," +
				" CASE WHEN bl.isapprovedqty1 = 'Y' THEN bl.qty1" +
				" WHEN bl.isapprovedqty2 = 'Y' THEN bl.qty2" +
				" WHEN bl.isapprovedqty3 = 'Y' THEN bl.qty3 ELSE NULL END AS cantidad,b.c_currency_id," +
				" CASE WHEN bl.isapprovedqty1 = 'Y' THEN bl.price1" +
				" WHEN bl.isapprovedqty2 = 'Y' THEN bl.price2" +
				" WHEN bl.isapprovedqty3 = 'Y' THEN bl.price3" +
				" ELSE NULL END AS price," +      
				" CASE WHEN bl.isapprovedqty1 = 'Y' THEN bl.amt1" +
				" WHEN bl.isapprovedqty2 = 'Y' THEN bl.amt2" +
				" WHEN bl.isapprovedqty3 = 'Y' THEN bl.amt3" +
				" ELSE NULL END AS totalLineaPresupNeto," +
				" CASE WHEN bl.isapprovedqty1 = 'Y' THEN bl.amt1 + bl.amt1 * 0.22" +
				" WHEN bl.isapprovedqty2 = 'Y' THEN bl.amt2 + bl.amt2 * 0.22" +
				" WHEN bl.isapprovedqty3 = 'Y' THEN bl.amt3 + bl.amt3 * 0.22 ELSE NULL" +
				" END AS totalLineaPresup,0,0,0,0,null::numeric,null::timestamp,null::numeric,null::timestamp,0," +
				" CASE WHEN bl.isapprovedqty1 = 'Y' THEN bl.amt1 * 0.22" +
				" WHEN bl.isapprovedqty2 = 'Y' THEN bl.amt2 * 0.22" +
				" WHEN bl.isapprovedqty3 = 'Y' THEN bl.amt3 * 0.22" +
				" ELSE NULL END AS iva,b.serie,0" +
				" from uy_budget b" +
				" inner join uy_budgetline bl on b.uy_budget_id = bl.uy_budget_id" +
				" JOIN c_bpartner cbp ON b.c_bpartner_id = cbp.c_bpartner_id" +
				" JOIN ad_user us ON b.salesrep_id = us.ad_user_id " + where + " order by b.uy_budget_id";
		

		log.log(Level.INFO, insert + sql);
		DB.executeUpdateEx(insert + sql, null);
		
		} catch (Exception e) {
			
			throw new AdempiereException(e);
			
		}
				
	}
	
	private void updateTable(){

		String sql = "", insert = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String where = "";
		String orderby = "";
		int counter = 0;
		int id = 0;
		MMoldePresupuestoProdFab[] lines = null;
		int partnerID = 0;
		int parentID = 0;
		int activityID = 0;
		int productID = 0;
		String descripcion = "";
		int salesrepID = 0;
		BigDecimal totalqty = Env.ZERO;
		int currencyID = 0;
		BigDecimal priceunit = Env.ZERO;
		BigDecimal importeneto = Env.ZERO;
		BigDecimal amount = Env.ZERO;
		BigDecimal qtydelivered = Env.ZERO;
		BigDecimal qtydeliverypend = Env.ZERO;
		BigDecimal amountqtydelivered = Env.ZERO;
		BigDecimal amountqtypend = Env.ZERO;
		int budgetdeliveryID = 0;
		Timestamp fecharemito = null;
		int invoiceID = 0;
		Timestamp dateinvoiced = null;
		BigDecimal amtfacturado = Env.ZERO;
		BigDecimal iva = Env.ZERO;
		String serie = "";
		BigDecimal qtyinvoiced = Env.ZERO;
		int budgetlineID = 0;
		
		try {

		sql = "select count(uy_molde_presupuestoprodfab_id)" +
				" from " + TABLA_MOLDE +
				" where idreporte=" + "'" + this.idReporte + "'";
		int count = DB.getSQLValueEx(get_TrxName(), sql);
		
		//si obtuve registros en la primer carga de tabla molde...
		if(count > 0){
			
			sql = "select *" +
					" from " + TABLA_MOLDE +
					" where idreporte=" + "'" + this.idReporte + "'";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			
			while (rs.next()){
				
				ResultSet rs2 = null;
				PreparedStatement pstmt2 = null;
				
				//se cargan fechas de entrega prometidas...
				sql = "select distinct md.datepromised,mo.uy_budget_id" +
                      " from uy_manuforder mo" + 
                      " left join uy_manufline ml on mo.uy_manuforder_id = ml.uy_manuforder_id" +
                      " left join uy_manufdelivery md on mo.uy_manuforder_id = md.uy_manuforder_id" +
                      " where mo.uy_budget_id = " + rs.getInt("uy_budget_id") + " and md.m_product_id = " + rs.getInt("m_product_id") +
                      " and md.datepromised is not null";
				
				pstmt2 = DB.prepareStatement (sql, null);
				rs2 = pstmt2.executeQuery ();
				
				counter = 1;
				
				while (rs2.next()){
					
					if(counter == 1){
						
						//update
						DB.executeUpdateEx("update " + TABLA_MOLDE + " set datepromised='" + rs2.getTimestamp("datepromised") + "' where uy_budget_id=" + rs2.getInt("uy_budget_id") +
								" and uy_budgetline_id=" + rs.getInt("uy_budgetline_id") + " and idreporte=" + "'" + this.idReporte + "'",get_TrxName());
							
						counter = 2;
						
					}else {
						
						partnerID = rs.getInt("c_bpartner_id");
						parentID = rs.getInt("bpartner_parent_id");
						activityID = rs.getInt("c_activity_id");
						budgetlineID = rs.getInt("uy_budgetline_id");
						productID = rs.getInt("m_product_id");
						descripcion = rs.getString("descripcion");
						salesrepID = rs.getInt("salesrep_id");
						totalqty = rs.getBigDecimal("totalqty");
						currencyID = rs.getInt("c_currency_id");
						priceunit = rs.getBigDecimal("priceunit");
						importeneto = rs.getBigDecimal("importeneto");
						amount = rs.getBigDecimal("amount");
						qtydelivered = rs.getBigDecimal("qtydelivered");
						qtydeliverypend = rs.getBigDecimal("qtydeliverypend");
						amountqtydelivered = rs.getBigDecimal("amountqtydelivered");
						amountqtypend = rs.getBigDecimal("amountqtypend");
						budgetdeliveryID = rs.getInt("uy_budgetdelivery_id");
						fecharemito = rs.getTimestamp("fecharemito");
						invoiceID = rs.getInt("c_invoice_id");
						dateinvoiced = rs.getTimestamp("dateinvoiced");
						amtfacturado = rs.getBigDecimal("amtfacturado");
						iva = rs.getBigDecimal("iva");
						serie = rs.getString("serie");
						qtyinvoiced = rs.getBigDecimal("qtyinvoiced");
						
						//insert
						insert = "INSERT INTO " + TABLA_MOLDE;
						
						sql = " select nextid(1001671,'N')," + rs.getInt("ad_org_id") + "," + rs.getInt("ad_client_id") + "," + this.adUserID + ",'" + this.idReporte + "'," + rs2.getInt("uy_budget_id") +
								"," + partnerID + "," + parentID + ",'" + rs2.getTimestamp("datepromised") + "'," + activityID + "," + budgetlineID + "," + productID + 
								",'" + descripcion + "'," + salesrepID + "," + totalqty + "," + currencyID + "," + priceunit + "," + importeneto + "," + amount + "," + 
								qtydelivered + "," + qtydeliverypend + "," + amountqtydelivered + "," + amountqtypend + "," + budgetdeliveryID + "," + fecharemito + "," + 
								invoiceID + "," + dateinvoiced + "," + amtfacturado + "," + iva + ",'" + serie + "'," + qtyinvoiced;
						
						log.log(Level.INFO, insert + sql);
						DB.executeUpdateEx(insert + sql, null);
															
					}				
					
				}
				
				DB.close(rs2, pstmt2);
				rs2 = null; pstmt2 = null;
				
				//se cargan datos de remitos
				
				String where2 = "";
				
				if(this.fechaRemDesde != null){
					where2+= " and del.datetrx >= '" + this.fechaRemDesde + "'";
				}
				
				if(this.fechaRemHasta != null){
					where2+= " and del.datetrx <= '" + this.fechaRemHasta + "'";
				}
				
				
				sql = "select distinct del.uy_budgetdelivery_id,del.datetrx,dl.qty,dl.m_product_id,dl.uy_budgetline_id,dl.uy_manufline_id,x.priceunit,x.totalqty" +
                      " from uy_molde_presupuestoprodfab x" +
                      " left join uy_budgetdeliveryline dl on x.uy_budgetline_id = dl.uy_budgetline_id" +
                      " left join uy_budgetdelivery del on dl.uy_budgetdelivery_id = del.uy_budgetdelivery_id" +
                      " where x.uy_budget_id = " + rs.getInt("uy_budget_id") + " and x.uy_budgetline_id = " + rs.getInt("uy_budgetline_id") + " and del.docstatus='CO'" +
                      " and x.idreporte='" + this.idReporte + "' " + where2 + " order by  del.uy_budgetdelivery_id asc";
				
				pstmt2 = DB.prepareStatement (sql, null);
				rs2 = pstmt2.executeQuery ();
				
				// Obtengo array de lineas de presupuesto insertadas anteriormente
				where = " where idreporte=" + "'" + this.idReporte + "'" + 
						" and uy_budgetline_id=" +  rs.getInt("uy_budgetline_id");
				orderby = " order by uy_molde_presupuestoprodfab_id asc ";
				lines = MMoldePresupuestoProdFab.getLines(getCtx(), where, orderby, get_TrxName());
				
				counter = 0;
				id = 0;

				while (rs2.next()){
					
					BigDecimal unitarioiva = (rs2.getBigDecimal("priceunit").add(rs2.getBigDecimal("priceunit").multiply(new BigDecimal(0.22))));
					qtydelivered = rs2.getBigDecimal("qty");
					budgetdeliveryID = rs2.getInt("uy_budgetdelivery_id");
					fecharemito = rs2.getTimestamp("datetrx");
					amountqtydelivered = unitarioiva.multiply(qtydelivered);
					
					sql = "select coalesce(sum(dl.qty),0)" +
                          " from uy_budgetdeliveryline dl" + 
                          " left join uy_budgetdelivery del on dl.uy_budgetdelivery_id = del.uy_budgetdelivery_id" + 
                          " where dl.uy_budgetline_id = " + rs2.getInt("uy_budgetline_id") + " and del.docstatus='CO' and del.uy_budgetdelivery_id <= " + rs2.getInt("uy_budgetdelivery_id");
					BigDecimal sumQtyDelivered = DB.getSQLValueBDEx(get_TrxName(), sql);
					
					if(sumQtyDelivered==null) sumQtyDelivered=Env.ZERO;
					
					qtydeliverypend = rs2.getBigDecimal("totalqty").subtract(sumQtyDelivered);					
					amountqtypend = unitarioiva.multiply(qtydeliverypend);
					
					if (counter < lines.length){

						//update
						DB.executeUpdateEx("update " + TABLA_MOLDE + " set qtydelivered=" + qtydelivered + ", qtydeliverypend=" + qtydeliverypend + ", amountqtydelivered=" +
						amountqtydelivered + ", amountqtypend=" + amountqtypend + ", uy_budgetdelivery_id=" + budgetdeliveryID +
								", fecharemito=" + "'" + fecharemito + "' where idreporte=" + "'" + this.idReporte + "'" + " and uy_molde_presupuestoprodfab_id=" +
								lines[id].get_ID(),get_TrxName());
						
						counter ++;
						id ++;

					} else {

						//insert
						insert = "INSERT INTO " + TABLA_MOLDE;
						
						sql = " select nextid(1001671,'N')," + rs.getInt("ad_org_id") + "," + rs.getInt("ad_client_id") + "," + this.adUserID + ",'" + this.idReporte + "'," + rs.getInt("uy_budget_id") +
								"," + rs.getInt("c_bpartner_id") + "," + rs.getInt("bpartner_parent_id") + "," + rs.getTimestamp("datepromised") + "," + rs.getInt("c_activity_id") + "," + rs.getInt("uy_budgetline_id") + "," + rs.getInt("m_product_id") + 
								",'" + rs.getString("descripcion") + "'," + rs.getInt("salesrep_id") + "," + rs.getBigDecimal("totalqty") + "," + rs.getInt("c_currency_id") + "," + rs.getBigDecimal("priceunit") + "," + rs.getBigDecimal("importeneto") + "," + rs.getBigDecimal("amount") + "," + 
								qtydelivered + "," + qtydeliverypend + "," + amountqtydelivered + "," + amountqtypend + "," + budgetdeliveryID + ",'" + fecharemito + "'," + 
								rs.getInt("c_invoice_id") + "," + rs.getTimestamp("dateinvoiced") + "," + rs.getBigDecimal("amtfacturado") + "," + rs.getBigDecimal("iva") + ",'" + rs.getString("serie") + "'," + rs.getBigDecimal("qtyinvoiced");
						
						log.log(Level.INFO, insert + sql);
						DB.executeUpdateEx(insert + sql, null);
						
						counter ++;
						id ++;

					}

				}
				
				DB.close(rs2, pstmt2);
				rs2 = null; pstmt2 = null;
				
				//se cargan datos de facturas
				sql = "select distinct inv.c_invoice_id,inv.dateinvoiced,(line.linenetamt + line.linenetamt*0.22) AS totalfacturado,line.qtyentered" +
                      " from uy_molde_presupuestoprodfab x" +
                      " left join uy_manuforder mo on (x.uy_budget_id = mo.uy_budget_id and mo.docstatus='CO')" +
                      " left join uy_manufline ml on mo.uy_manuforder_id = ml.uy_manuforder_id" +
                      " left join uy_manufdelivery md on mo.uy_manuforder_id = md.uy_manuforder_id" +
                      " left join c_invoiceline line on ml.uy_manufline_id = line.uy_manufline_id" +
                      " left join c_invoice inv ON line.c_invoice_id = inv.c_invoice_id" +
                      " where x.uy_budget_id = " + rs.getInt("uy_budget_id") + " and ml.uy_budgetline_id = " + rs.getInt("uy_budgetline_id") + " and inv.docstatus='CO'" + 
                      " and x.idreporte='" + this.idReporte + "'" + " order by inv.c_invoice_id asc";
				
				pstmt2 = DB.prepareStatement (sql, null);
				rs2 = pstmt2.executeQuery ();
				
				// Obtengo array de lineas de presupuesto insertadas anteriormente
				where = " where idreporte=" + "'" + this.idReporte + "'" + 
						" and uy_budgetline_id=" +  rs.getInt("uy_budgetline_id");
				orderby = " order by uy_molde_presupuestoprodfab_id asc ";
				lines = MMoldePresupuestoProdFab.getLines(getCtx(), where, orderby, get_TrxName());

				counter = 0;
				id = 0;

				while (rs2.next()){

					invoiceID = rs2.getInt("c_invoice_id");
					dateinvoiced = rs2.getTimestamp("dateinvoiced");
					amtfacturado = rs2.getBigDecimal("totalfacturado");
					qtyinvoiced = rs2.getBigDecimal("qtyentered");

					if (counter < lines.length){

						//update
						DB.executeUpdateEx("update " + TABLA_MOLDE + " set c_invoice_id=" + invoiceID + ", amtfacturado=" + amtfacturado +
								", dateinvoiced=" + "'" + dateinvoiced + "', qtyinvoiced=" + qtyinvoiced + " where idreporte=" + "'" + this.idReporte + "'" + " and uy_molde_presupuestoprodfab_id=" +
								lines[id].get_ID(),get_TrxName());

						counter ++;
						id ++;

					} else {

						//insert
						insert = "INSERT INTO " + TABLA_MOLDE;
						
						sql = " select nextid(1001671,'N')," + rs.getInt("ad_org_id") + "," + rs.getInt("ad_client_id") + "," + this.adUserID + ",'" + this.idReporte + "'," + rs.getInt("uy_budget_id") +
								"," + rs.getInt("c_bpartner_id") + "," + rs.getInt("bpartner_parent_id") + "," + rs.getTimestamp("datepromised") + "," + rs.getInt("c_activity_id") + "," + rs.getInt("uy_budgetline_id") + "," + rs.getInt("m_product_id") + 
								",'" + rs.getString("descripcion") + "'," + rs.getInt("salesrep_id") + "," + rs.getBigDecimal("totalqty") + "," + rs.getInt("c_currency_id") + "," + rs.getBigDecimal("priceunit") + "," + rs.getBigDecimal("importeneto") + "," + rs.getBigDecimal("amount") + "," + 
								rs.getBigDecimal("qtydelivered") + "," + rs.getBigDecimal("qtydeliverypend") + "," + rs.getBigDecimal("amountqtydelivered") + "," + rs.getBigDecimal("amountqtypend") + "," + rs.getInt("uy_budgetdelivery_id") + "," + rs.getTimestamp("fecharemito") + "," + 
								invoiceID + ",'" + dateinvoiced + "'," + amtfacturado + "," + rs.getBigDecimal("iva") + ",'" + rs.getString("serie") + "'," + qtyinvoiced;
						
						log.log(Level.INFO, insert + sql);
						DB.executeUpdateEx(insert + sql, null);
						
						counter ++;
						id ++;

					}

				}

				DB.close(rs2, pstmt2);
				rs2 = null; pstmt2 = null;
			}		


		}		

		}catch (Exception e){
			log.log(Level.SEVERE, sql, e);
		}
		finally	{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	

	}

}
