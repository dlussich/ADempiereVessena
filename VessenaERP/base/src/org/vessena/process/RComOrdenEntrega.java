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
import org.openup.model.MMoldeRemito;

public class RComOrdenEntrega extends SvrProcess {
	
	private static String TABLA_MOLDE = "UY_Molde_Remito";
	
	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	private int cbpartnerID = 0;
	private int activityID = 0;	
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;	
	private String idReporte = "";

	public RComOrdenEntrega() {
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
					this.fechaDesde = (Timestamp)para[i].getParameter();	
					this.fechaHasta = (Timestamp)para[i].getParameter_To();	
				}					
				if (name.equalsIgnoreCase("C_Activity_ID")){
					if (para[i].getParameter()!=null){
						this.activityID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
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
		
		//actualizo datos en tabla molde
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
	
	/* Carga movimientos en la tabla molde. */
	private void loadMovimientos(){
		
		String sql = "", insert = "", where = " where d.docstatus in ('CO','VO')";
		
		if(this.fechaDesde != null) where += " and d.datetrx >= '" + this.fechaDesde + "'";
				
		if(this.fechaHasta != null) where += " and d.datetrx <= '" + this.fechaHasta + "'";
				
		if(this.activityID > 0) where += " and b.c_activity_id = " + this.activityID;
					
		if(this.cbpartnerID > 0) where+= " and d.c_bpartner_id = " + this.cbpartnerID;
					
		if(this.adOrgID > 0) where+= " and d.ad_org_id = " + this.adOrgID;
				
		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE;
								
			sql = " select nextid(1001672,'N'),d.ad_org_id," + this.adClientID + "," + this.adUserID + ",'" + this.idReporte + "',b.uy_budget_id,d.c_bpartner_id,b.serie,p.name as nomCliente,p.bpartner_parent_id,(select name from c_bpartner where c_bpartner_id=p.bpartner_parent_id) as nomPadre," +
			      " b.c_activity_id,null,'',0,d.uy_budgetdelivery_id,d.datetrx,d.docstatus" + 
                  " from uy_budgetdelivery d" +
                  " inner join c_bpartner p on d.c_bpartner_id=p.c_bpartner_id" +
                  " inner join uy_budget b on d.uy_budget_id=b.uy_budget_id " + where + " order by d.uy_budgetdelivery_id asc";
									  			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
			
		}		
		
	}

	private void updateTable(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int counter = 0;
		int secuencia = 1;
		
		try {

			sql = "select count(uy_molde_remito_id)" +
					" from " + TABLA_MOLDE +
					" where idreporte=" + "'" + this.idReporte + "'";
			int count = DB.getSQLValueEx(get_TrxName(), sql);

			//si obtuve registros en la primer carga de tabla molde...
			if(count > 0){

				sql = "select *" +
						" from " + TABLA_MOLDE +
						" where idreporte=" + "'" + this.idReporte + "'" + 
						" order by datetrx desc,uy_budgetdelivery_id desc,c_bpartner_id desc";

				pstmt = DB.prepareStatement (sql, null);
				rs = pstmt.executeQuery ();

				while(rs.next()){

					ResultSet rs2 = null;
					PreparedStatement pstmt2 = null;
					
					//obtengo lineas de entrega
					sql = "select l.m_product_id,p.name,l.qty" +
							" from uy_budgetdeliveryline l" +
							" inner join uy_budgetdelivery d on l.uy_budgetdelivery_id = d.uy_budgetdelivery_id" +
							" inner join m_product p on l.m_product_id = p.m_product_id" + 
							" where d.uy_budgetdelivery_id = " + rs.getInt("uy_budgetdelivery_id") + " order by l.uy_budgetdeliveryline_id asc";

					pstmt2 = DB.prepareStatement (sql, null);
					rs2 = pstmt2.executeQuery ();

					counter = 1;

					while (rs2.next()){
										
						if(counter == 1){

							//update
							DB.executeUpdateEx("update " + TABLA_MOLDE + " set m_product_id=" + rs2.getInt("m_product_id") + ",descripcion='" + rs2.getString("name") + "',qtydelivered=" +
									rs2.getBigDecimal("qty") + " where uy_budgetdelivery_id=" + rs.getInt("uy_budgetdelivery_id") + " and uy_molde_remito_id=" + rs.getInt("uy_molde_remito_id") +
									" and idreporte=" + "'" + this.idReporte + "'",get_TrxName());
							
							DB.executeUpdateEx("update " + TABLA_MOLDE + " set secuencia=" + secuencia + " where uy_molde_remito_id=" + rs.getInt("uy_molde_remito_id") +
									" and idreporte=" + "'" + this.idReporte + "'",get_TrxName());
							
							secuencia ++;

							counter = 2;							

						} else {
							
							//consulto si existe una linea para el mismo remito y producto, si existe entonces actualizo su cantidad
							sql = "select uy_molde_remito_id" +
							       " from " + TABLA_MOLDE +
							       " where uy_budgetdelivery_id=" + rs.getInt("uy_budgetdelivery_id") + 
							       " and m_product_id=" + rs2.getInt("m_product_id") + " and idreporte=" + "'" + this.idReporte + "'";
							int lineID = DB.getSQLValueEx(get_TrxName(), sql);
							
							if(lineID > 0){ //actualizo cantidad de linea existente para mismo producto

								MMoldeRemito mr = new MMoldeRemito (getCtx(),lineID,get_TrxName());

								BigDecimal newQty = mr.getQtyDelivered().add(rs2.getBigDecimal("qty"));

								DB.executeUpdateEx("update " + TABLA_MOLDE + " set qtydelivered=" + newQty + " where uy_molde_remito_id=" + lineID,get_TrxName());
								
								DB.executeUpdateEx("update " + TABLA_MOLDE + " set secuencia=" + secuencia + " where uy_molde_remito_id=" + rs.getInt("uy_molde_remito_id") +
										" and idreporte=" + "'" + this.idReporte + "'",get_TrxName());
								
								secuencia ++;

							} else {

								//insert
								MMoldeRemito r = new MMoldeRemito (getCtx(),0,get_TrxName());
								
								r.setAD_Org_ID(rs.getInt("ad_org_id"));
								r.setAD_Client_ID(rs.getInt("ad_client_id"));
								r.setAD_User_ID(this.adUserID);
								r.setidReporte(this.idReporte);
								r.setUY_Budget_ID(rs.getInt("uy_budget_id"));
								r.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
								r.setserie(rs.getString("serie"));
								r.setnomcliente(rs.getString("nomcliente"));
								r.setBPartner_Parent_ID(rs.getInt("bpartner_parent_id"));
								if(rs.getString("nompadre")!=null) r.setnompadre(rs.getString("nompadre"));
								r.setC_Activity_ID(rs.getInt("c_activity_id"));
								r.setM_Product_ID(rs2.getInt("m_product_id"));
								r.setdescripcion(rs2.getString("name"));
								r.setQtyDelivered(rs2.getBigDecimal("qty"));
								r.setUY_BudgetDelivery_ID(rs.getInt("uy_budgetdelivery_id"));
								r.setDateTrx(rs.getTimestamp("datetrx"));
								r.setDocStatus(rs.getString("docstatus"));
								r.setsecuencia(secuencia);
								r.saveEx();
								
								secuencia ++;

							}

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
