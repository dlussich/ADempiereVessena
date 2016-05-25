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
import org.openup.model.MMoldePresupAprobado;

public class RComPresupuestoAprobado extends SvrProcess{
	
private static String TABLA_MOLDE = "UY_Molde_PresupAprobado";
	
	private Timestamp fechaAprobacionDesde = null;
	private Timestamp fechaAprobacionHasta = null;
	private Timestamp fechaEntregaDesde = null;
	private Timestamp fechaEntregaHasta = null;
	private String isinvoiced = "";
	private String isreceipt = "";
	private int activityID = 0;
	private int cbpartnerID = 0;
	private int parentID = 0;
	private int budgetID = 0;
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;	
	private String idReporte = "";
	private int sequenceID = 0;

	public RComPresupuestoAprobado() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
					
				if (name.equalsIgnoreCase("DateApproved")){
					this.fechaAprobacionDesde = (Timestamp)para[i].getParameter();	
					this.fechaAprobacionHasta = (Timestamp)para[i].getParameter_To();	
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
				if (name.equalsIgnoreCase("isinvoiced")){
					if (para[i].getParameter()!=null){
						this.isinvoiced = ((String)para[i].getParameter());
					}
				}
				if (name.equalsIgnoreCase("isreceipt")){
					if (para[i].getParameter()!=null){
						this.isreceipt = ((String)para[i].getParameter());
					}
				}
				if (name.equalsIgnoreCase("fechaentrega")){
					this.fechaEntregaDesde = (Timestamp)para[i].getParameter();	
					this.fechaEntregaHasta = (Timestamp)para[i].getParameter_To();	
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
		//borro registros segun filtros
		this.deleteDataFromTable();
		
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
		
		if(this.fechaAprobacionDesde != null) where += " and b.dateapproved >= '" + this.fechaAprobacionDesde + "'";
		
		if(this.fechaAprobacionHasta != null) where += " and b.dateapproved <= '" + this.fechaAprobacionHasta + "'";
		
		if(this.activityID > 0) where += " and b.c_activity_id = " + this.activityID;			
				
		if(this.parentID > 0) where+= " and cbp.bpartner_parent_id = " + this.parentID;
				
		if(this.cbpartnerID > 0) where+= " and b.c_bpartner_id = " + this.cbpartnerID;
				
		if(this.budgetID > 0) where+= " and b.uy_budget_id = " + this.budgetID;
				
		if(this.adOrgID > 0) where+= " and b.ad_org_id = " + this.adOrgID;
						
		try {
			
		sql = "select ad_sequence_id from ad_sequence where lower(name) like '" + TABLA_MOLDE.toLowerCase() + "'";
		sequenceID = DB.getSQLValueEx(get_TrxName(), sql);
		
		insert = "INSERT INTO " + TABLA_MOLDE;
		
		sql = " select distinct nextid(" + sequenceID + ",'N'),b.ad_org_id," + this.adClientID + "," + this.adUserID + ",now()," + this.adUserID + ",now()," + this.adUserID + ",'" + this.idReporte + 
				"',b.uy_budget_id,b.serie,b.datetrx,b.dateapproved,b.c_bpartner_id,coalesce(cbp.bpartner_parent_id,0),b.c_activity_id,bl.uy_budgetline_id," +
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
				" CASE WHEN bl.isapprovedqty1 = 'Y' THEN bl.amt1 * 0.22" +
				" WHEN bl.isapprovedqty2 = 'Y' THEN bl.amt2 * 0.22" +
				" WHEN bl.isapprovedqty3 = 'Y' THEN bl.amt3 * 0.22" +
				" ELSE NULL END AS iva," +
				" CASE WHEN bl.isapprovedqty1 = 'Y' THEN bl.amt1 + bl.amt1 * 0.22" +
				" WHEN bl.isapprovedqty2 = 'Y' THEN bl.amt2 + bl.amt2 * 0.22" +
				" WHEN bl.isapprovedqty3 = 'Y' THEN bl.amt3 + bl.amt3 * 0.22 ELSE NULL" +
				" END AS totalLineaPresup,null::timestamp,'N',0,null::timestamp,0,0,'N',0,null::timestamp,0" +
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
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String where = "";
		String orderby = "";
		MMoldePresupAprobado[] lines = null;
		int counter = 0;
		int id = 0;
		Boolean hayRemitos = false;
		int invoiceID = 0;
		Timestamp dateinvoiced = null;
		BigDecimal amtfacturado = Env.ZERO;
		BigDecimal qtyinvoiced = Env.ZERO;
		int paymentID = 0;
		Timestamp fecharecibo = null;
		BigDecimal payamt = Env.ZERO;
		Boolean facturado = null;
		Boolean cobrado = null;			

		try {
			
			sql = "select count(uy_molde_presupaprobado_id)" +
					" from " + TABLA_MOLDE +
					" where idreporte=" + "'" + this.idReporte + "'";
			int count = DB.getSQLValueEx(get_TrxName(), sql);
			
			//si obtuve registros en la primer carga de tabla molde...
			if(count > 0){
				
				sql = "select *" +
						" from " + TABLA_MOLDE +
						" where idreporte=" + "'" + this.idReporte + "'" +
						" order by dateapproved desc";
				
				pstmt = DB.prepareStatement (sql, null);
				rs = pstmt.executeQuery ();
				
				while (rs.next()){
					
					ResultSet rs2 = null;
					PreparedStatement pstmt2 = null;
					
					//se cargan fechas de entrega de remitos (si los hay)
					sql = "select distinct del.datetrx" +
							" from uy_budgetdeliveryline dl" +
							" inner join uy_budgetdelivery del on dl.uy_budgetdelivery_id = del.uy_budgetdelivery_id" +
							" where del.uy_budget_id = " + rs.getInt("uy_budget_id") + " and dl.uy_budgetline_id = " + rs.getInt("uy_budgetline_id") + " and del.docstatus='CO'";
												
					pstmt2 = DB.prepareStatement (sql, null);
					rs2 = pstmt2.executeQuery ();
					
					counter = 1;		
					
					//si hay remitos
					while(rs2.next()){
						
						hayRemitos = true;
						
						if(counter == 1){
							
							//update
							MMoldePresupAprobado p = new MMoldePresupAprobado(getCtx(),rs.getInt("uy_molde_presupaprobado_id"),get_TrxName());
							p.setfechaentrega(rs2.getTimestamp("datetrx"));
							p.saveEx();		
																		
							counter = 2;							
							
						} else {
							
							//insert
							MMoldePresupAprobado p = new MMoldePresupAprobado(getCtx(),0,get_TrxName());
							p.setAD_Org_ID(rs.getInt("ad_org_id"));
							p.setAD_Client_ID(rs.getInt("ad_client_id"));
							p.setAD_User_ID(this.adUserID);
							p.setidReporte(this.idReporte);
							p.setUY_Budget_ID(rs.getInt("uy_budget_id"));
							p.setserie(rs.getString("serie"));
							p.setfechapresup(rs.getTimestamp("fechapresup"));
							p.setDateApproved(rs.getTimestamp("dateapproved"));
							p.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
							p.setBPartner_Parent_ID(rs.getInt("bpartner_parent_id"));
							p.setC_Activity_ID(rs.getInt("c_activity_id"));
							p.setUY_BudgetLine_ID(rs.getInt("uy_budgetline_id"));
							p.setM_Product_ID(rs.getInt("m_product_id"));
							p.setdescripcion(rs.getString("descripcion"));
							p.setSalesRep_ID(rs.getInt("salesrep_id"));
							p.setTotalQty(rs.getBigDecimal("totalqty"));
							p.setC_Currency_ID(rs.getInt("c_currency_id"));
							p.setpriceunit(rs.getBigDecimal("priceunit"));
							p.setimporteneto(rs.getBigDecimal("importeneto"));
							p.setiva(rs.getBigDecimal("iva"));
							p.setAmount(rs.getBigDecimal("amount"));
							p.setfechaentrega(rs2.getTimestamp("datetrx"));
							p.saveEx();			
														
						}						
						
					}
					
					DB.close(rs2, pstmt2);
					rs2 = null; pstmt2 = null;
					
					if(!hayRemitos){
								
						//se cargan fechas de entrega prometidas...
						sql = "select distinct md.datepromised" +
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
								MMoldePresupAprobado p = new MMoldePresupAprobado(getCtx(),rs.getInt("uy_molde_presupaprobado_id"),get_TrxName());
								p.setfechaentrega(rs.getTimestamp("datetrx"));
								p.saveEx();	
														
								counter = 2;
								
							}else {
																
								//insert
								MMoldePresupAprobado p = new MMoldePresupAprobado(getCtx(),0,get_TrxName());
								p.setAD_Org_ID(rs.getInt("ad_org_id"));
								p.setAD_Client_ID(rs.getInt("ad_client_id"));
								p.setAD_User_ID(this.adUserID);
								p.setidReporte(this.idReporte);
								p.setUY_Budget_ID(rs.getInt("uy_budget_id"));
								p.setserie(rs.getString("serie"));
								p.setfechapresup(rs.getTimestamp("fechapresup"));
								p.setDateApproved(rs.getTimestamp("dateapproved"));
								p.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
								p.setBPartner_Parent_ID(rs.getInt("bpartner_parent_id"));
								p.setC_Activity_ID(rs.getInt("c_activity_id"));
								p.setUY_BudgetLine_ID(rs.getInt("uy_budgetline_id"));
								p.setM_Product_ID(rs.getInt("m_product_id"));
								p.setdescripcion(rs.getString("descripcion"));
								p.setSalesRep_ID(rs.getInt("salesrep_id"));
								p.setTotalQty(rs.getBigDecimal("totalqty"));
								p.setC_Currency_ID(rs.getInt("c_currency_id"));
								p.setpriceunit(rs.getBigDecimal("priceunit"));
								p.setimporteneto(rs.getBigDecimal("importeneto"));
								p.setiva(rs.getBigDecimal("iva"));
								p.setAmount(rs.getBigDecimal("amount"));
								p.setfechaentrega(rs2.getTimestamp("datepromised"));
								p.saveEx();									
																	
							}							
							
						}
						
						DB.close(rs2, pstmt2);
						rs2 = null; pstmt2 = null;					
						
					}
					
					//se cargan datos de facturas y recibos
					sql = "select distinct inv.c_invoice_id,inv.dateinvoiced,(line.linenetamt + line.linenetamt*0.22) AS totalfacturado,line.qtyentered," +
					      " pay.c_payment_id,pay.datetrx,pay.payamt,case when inv.c_invoice_id > 0 then 'Y' else 'N' end as facturado,case when pay.c_payment_id > 0 then 'Y' else 'N' end as cobrado" +    
                          " from uy_molde_presupaprobado x" + 
                          " left join uy_manuforder mo on (x.uy_budget_id = mo.uy_budget_id and mo.docstatus='CO')" + 
                          " left join uy_manufline ml on mo.uy_manuforder_id = ml.uy_manuforder_id" + 
                          " left join uy_manufdelivery md on mo.uy_manuforder_id = md.uy_manuforder_id" + 
                          " left join c_invoiceline line on ml.uy_manufline_id = line.uy_manufline_id" + 
                          " left join c_invoice inv ON line.c_invoice_id = inv.c_invoice_id" +
                          " left join uy_allocdirectpayment al on inv.c_invoice_id = al.c_invoice_id" +
                          " left join c_payment pay on (al.c_payment_id = pay.c_payment_id and pay.docstatus='CO')" +
                          " where inv.docstatus='CO' and x.uy_budget_id = " + rs.getInt("uy_budget_id") + " and ml.uy_budgetline_id = " + rs.getInt("uy_budgetline_id") +
                          " and x.idreporte='" + this.idReporte + "' order by inv.c_invoice_id asc,pay.c_payment_id asc";
					
					pstmt2 = DB.prepareStatement (sql, null);
					rs2 = pstmt2.executeQuery ();
					
					// Obtengo array de lineas de presupuesto insertadas anteriormente
					where = " where idreporte=" + "'" + this.idReporte + "'" + 
							" and uy_budgetline_id=" +  rs.getInt("uy_budgetline_id");
					orderby = " order by uy_molde_presupaprobado_id asc ";
					lines = MMoldePresupAprobado.getLines(getCtx(), where, orderby, get_TrxName());

					counter = 0;
					id = 0;
					
					while (rs2.next()){

						invoiceID = rs2.getInt("c_invoice_id");
						dateinvoiced = rs2.getTimestamp("dateinvoiced");
						amtfacturado = rs2.getBigDecimal("totalfacturado");
						qtyinvoiced = rs2.getBigDecimal("qtyentered");
						paymentID = rs2.getInt("c_payment_id");
						fecharecibo = rs2.getTimestamp("datetrx");
						payamt = rs2.getBigDecimal("payamt");
						
						if(rs2.getString("facturado").equalsIgnoreCase("Y")){
							facturado = true;
						} else facturado = false;
						
						if(rs2.getString("cobrado").equalsIgnoreCase("Y")){
							cobrado = true;
						} else cobrado = false;

						if (counter < lines.length){

							//update
							MMoldePresupAprobado p = new MMoldePresupAprobado(getCtx(),lines[id].get_ID(),get_TrxName());
							p.setC_Invoice_ID(invoiceID);
							p.setDateInvoiced(dateinvoiced);
							p.setQtyInvoiced(qtyinvoiced);
							p.setamtfacturado(amtfacturado);
							p.setIsInvoiced(facturado);
							p.setC_Payment_ID(paymentID);
							p.setDateTrx(fecharecibo);
							p.setPayAmt(payamt);
							p.setIsReceipt(cobrado);
							p.saveEx();								
		
							counter ++;
							id ++;

						} else {

							//insert
							MMoldePresupAprobado p = new MMoldePresupAprobado(getCtx(),0,get_TrxName());
							p.setAD_Org_ID(rs.getInt("ad_org_id"));
							p.setAD_Client_ID(rs.getInt("ad_client_id"));
							p.setAD_User_ID(this.adUserID);
							p.setidReporte(this.idReporte);
							p.setUY_Budget_ID(rs.getInt("uy_budget_id"));
							p.setserie(rs.getString("serie"));
							p.setfechapresup(rs.getTimestamp("fechapresup"));
							p.setDateApproved(rs.getTimestamp("dateapproved"));
							p.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
							p.setBPartner_Parent_ID(rs.getInt("bpartner_parent_id"));
							p.setC_Activity_ID(rs.getInt("c_activity_id"));
							p.setUY_BudgetLine_ID(rs.getInt("uy_budgetline_id"));
							p.setM_Product_ID(rs.getInt("m_product_id"));
							p.setdescripcion(rs.getString("descripcion"));
							p.setSalesRep_ID(rs.getInt("salesrep_id"));
							p.setTotalQty(rs.getBigDecimal("totalqty"));
							p.setC_Currency_ID(rs.getInt("c_currency_id"));
							p.setpriceunit(rs.getBigDecimal("priceunit"));
							p.setimporteneto(rs.getBigDecimal("importeneto"));
							p.setiva(rs.getBigDecimal("iva"));
							p.setAmount(rs.getBigDecimal("amount"));
							p.setfechaentrega(rs.getTimestamp("fechaentrega"));
							p.setIsInvoiced(true);
							p.setC_Invoice_ID(invoiceID);
							p.setDateInvoiced(dateinvoiced);
							p.setQtyInvoiced(qtyinvoiced);
							p.setamtfacturado(amtfacturado);	
							p.saveEx();

							counter ++;
							id ++;
							
						}

						facturado = null;
						cobrado = null;
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
	
	private void deleteDataFromTable(){
		
		String where = " where";
		
		try {
			
			if(this.fechaEntregaDesde != null) where += " fechaentrega < '" + this.fechaEntregaDesde + "'";
			
			if(this.fechaEntregaHasta != null){
				if(where.equalsIgnoreCase(" where")){
					where += " fechaentrega > '" + this.fechaEntregaHasta + "'";
				} else where += " and fechaentrega > '" + this.fechaEntregaHasta + "'";
			}
						
			if(!this.isinvoiced.equalsIgnoreCase("")) {
				if(this.isinvoiced.equalsIgnoreCase("Y")) {
					if(where.equalsIgnoreCase(" where")){
						where += " isinvoiced='N'";
					} else where += " and isinvoiced='N'";

				} else{
					if(where.equalsIgnoreCase(" where")){
						where += " isinvoiced='Y'";
					} else where += " and isinvoiced='Y'";					
				}
			}
			
			if(!this.isreceipt.equalsIgnoreCase("")) {
				if(this.isreceipt.equalsIgnoreCase("Y")) {
					if(where.equalsIgnoreCase(" where")){
						where += " isreceipt='N'";
					} else where += " and isreceipt='N'";

				} else{
					if(where.equalsIgnoreCase(" where")){
						where += " isreceipt='Y'";
					} else where += " and isreceipt='Y'";					
				}
			}
			
			if(!where.equalsIgnoreCase(" where")){
						
				where += " and idreporte='" + this.idReporte + "'";
				DB.executeUpdateEx("delete from " + TABLA_MOLDE + where,get_TrxName());
			}		
									
			
		}catch (Exception e){
			throw new AdempiereException (e.getMessage());
		}	
		
	}
	
}
