/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MConversionRate;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductPrice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.model.MTRServiceOrder;
import org.openup.model.MTRServiceOrderProd;

/**
 * @author Nicolás
 *
 */
public class RTRReparaciones extends SvrProcess {
	
	private Timestamp fechaFacturaDesde = null;
	private Timestamp fechaFacturaHasta = null;
	private Timestamp fechaOrdenDesde = null;
	private Timestamp fechaOrdenHasta = null;
	private int truckID = 0;
	private String tipoTarea = "";
	private String detalle = "";
	private int failureID = 0;
	private int adUserID = 0;
	private int adClientID = 0;
	private int adOrgID = 0;
	
	private String idReporte = "";
	private static final String TABLA_MOLDE = "UY_Molde_Reparaciones";
	
	private static final String TIPO_REPARACIONES = "RP";
	private static final String TIPO_MANTENIMIENTOS = "MT";

	/**
	 * 
	 */
	public RTRReparaciones() {
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){

				if (name.equalsIgnoreCase("UY_TR_Truck_ID")){
					if (para[i].getParameter()!=null)
						this.truckID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("DateInvoiced")){
					this.fechaFacturaDesde = (Timestamp)para[i].getParameter();
					this.fechaFacturaHasta = (Timestamp)para[i].getParameter_To();
				}

				if (name.equalsIgnoreCase("DateOrdered")){
					this.fechaOrdenDesde = (Timestamp)para[i].getParameter();
					this.fechaOrdenHasta = (Timestamp)para[i].getParameter_To();
				}

				if (name.equalsIgnoreCase("tipo")){
					if (para[i].getParameter()!=null)
						this.tipoTarea = (String)para[i].getParameter();
				}

				if (name.equalsIgnoreCase("Description")){
					if (para[i].getParameter()!=null)
						this.detalle = (String)para[i].getParameter();
				}
				
				if (name.equalsIgnoreCase("UY_TR_Failure_ID")){
					if (para[i].getParameter()!=null)
						this.failureID = ((BigDecimal)para[i].getParameter()).intValueExact();
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

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
	
		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde segun filtros
		this.loadData();
		
		return "ok";
	}
	
	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id = " + this.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
	
	private void loadData(){

		String insert = "", sql = "";
		String whereFiltros = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		BigDecimal amount = Env.ZERO, dividerate = Env.ZERO, totalProdAmt = Env.ZERO;
		
		try {
			
			//si se pide mostrar reparaciones...
			if (this.tipoTarea.equalsIgnoreCase(TIPO_REPARACIONES)){
				
				whereFiltros = " where r.ad_client_id = " + this.adClientID + " and r.ad_org_id = " + this.adOrgID +
						" and o.isown = 'N'";
				
				if(this.truckID > 0) whereFiltros += " and r.uy_tr_truck_id = " + this.truckID;
				if(this.failureID > 0) whereFiltros += " and r.uy_tr_failure_id = " + this.failureID;
				
				if(this.fechaFacturaDesde!=null) whereFiltros += " and i.dateinvoiced >= '" + this.fechaFacturaDesde + "'";
				if(this.fechaFacturaHasta!=null) whereFiltros += " and i.dateinvoiced <= '" + this.fechaFacturaHasta + "'";
				
				if(this.fechaOrdenDesde!=null) whereFiltros += " and o.datetrx >= '" + this.fechaOrdenDesde + "'";
				if(this.fechaOrdenHasta!=null) whereFiltros += " and o.datetrx <= '" + this.fechaOrdenHasta + "'";
				
				if(this.detalle != null && !this.detalle.equalsIgnoreCase("")){
					
					String description = this.detalle.toLowerCase();
										
					whereFiltros += " and lower (r.description) like '%" + description + "%'";
				}				
				
				sql = "select r.ad_client_id, r.ad_org_id, r.uy_tr_truck_id, i.dateinvoiced, o.kilometros, r.uy_tr_serviceorder_id, coalesce(r.description,'') as description, r.c_bpartner_id," + 
						" r.c_invoice_id, r.c_currency_id, i.totallines, i.docstatus, r.uy_tr_failure_id, o.datetrx" +
						" from uy_tr_truckrepair r" +
						" inner join uy_tr_serviceorder o on r.uy_tr_serviceorder_id = o.uy_tr_serviceorder_id" +
						" left join c_invoice i on r.c_invoice_id = i.c_invoice_id " + whereFiltros;
				
				pstmt = DB.prepareStatement (sql, null);
				rs = pstmt.executeQuery ();
				
				while (rs.next()){
					
					amount = Env.ZERO; dividerate = Env.ZERO; totalProdAmt = Env.ZERO;
					
					//si hay factura
					if(rs.getInt("c_invoice_id")>0){
						
						if(rs.getInt("c_currency_id")!=100){ //si la moneda de factura no es U$
							
							Timestamp dateInv = TimeUtil.trunc(rs.getTimestamp("dateinvoiced"), TimeUtil.TRUNC_DAY);
							
							if(rs.getInt("c_currency_id")==142){ //si son $U
								
								dividerate = MConversionRate.getDivideRate(rs.getInt("c_currency_id"), 100, dateInv, 0, this.getAD_Client_ID(), 0);
								
								if(dividerate != null) {
									
									dividerate = dividerate.setScale(3, RoundingMode.HALF_UP);								
															
								}						
								
							} else { //si no son $U
								
								BigDecimal rateFrom = MConversionRate.getDivideRate(142, 100, dateInv, 0, this.getAD_Client_ID(), 0);
								BigDecimal rateTo = MConversionRate.getDivideRate(142, rs.getInt("c_currency_id"), dateInv, 0, this.getAD_Client_ID(), 0);

								if ((rateFrom != null) && (rateTo != null)){
									if (rateTo.compareTo(Env.ZERO) > 0) {
										dividerate = rateFrom.divide(rateTo, 3, RoundingMode.HALF_UP);		
									}
								}										
							}
							
							if(dividerate == null) dividerate = Env.ZERO;
							if(dividerate.compareTo(Env.ZERO)>0) amount = rs.getBigDecimal("totallines").divide(dividerate, RoundingMode.HALF_UP);						
							
						} else amount = rs.getBigDecimal("totallines");						
					}
					
					MTRServiceOrder order = new MTRServiceOrder(getCtx(),rs.getInt("uy_tr_serviceorder_id"),get_TrxName());
					
					//obtengo y recorro lista de repuestos usados
					List<MTRServiceOrderProd> servprods = order.getProducts();
					
					for(MTRServiceOrderProd prodLine : servprods){
						
						totalProdAmt = totalProdAmt.add(this.getProductCost(prodLine));						
						
					}
					
					BigDecimal totalAmt = amount.add(totalProdAmt); //total de factura + repuestos
					
					insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, idreporte, ad_user_id, fecreporte, uy_tr_truck_id, " + 
							" dateinvoiced, qtykm, uy_tr_serviceorder_id, description, c_bpartner_id, c_invoice_id, " +
							" c_currency_id, totallines, amount, currencyrate, docstatus, uy_tr_failure_id, dateordered, amount2, grandtotal)";
					
					sql = "SELECT " + rs.getInt("ad_client_id") + "," + rs.getInt("ad_org_id") + ",'" + this.idReporte + "'," + this.adUserID + ", current_date," +
							rs.getInt("uy_tr_truck_id") + ",'" + rs.getTimestamp("dateinvoiced") + "'," + rs.getInt("kilometros") + "," + rs.getInt("uy_tr_serviceorder_id") +
							",'" + rs.getString("description") + "'," + rs.getInt("c_bpartner_id") + "," + rs.getInt("c_invoice_id") + "," + rs.getInt("c_currency_id") +
							"," + rs.getBigDecimal("totallines") + "," + amount.setScale(2, RoundingMode.HALF_UP) + "," + dividerate + ",'" + rs.getString("docstatus") + 
							"'," + rs.getInt("uy_tr_failure_id") + ",'" + rs.getTimestamp("datetrx") + "'," + totalProdAmt.setScale(2, RoundingMode.HALF_UP) + 
							"," + totalAmt.setScale(2, RoundingMode.HALF_UP);				
					
					DB.executeUpdateEx(insert + sql, null);
					
				}			
				
			} else if (this.tipoTarea.equalsIgnoreCase(TIPO_MANTENIMIENTOS)){ //si se pide mostrar mantenimientos...
				
				whereFiltros = " where m.ad_client_id = " + this.adClientID + " and m.ad_org_id = " + this.adOrgID +
						" and o.isown = 'Y'";
				
				if(this.truckID > 0) whereFiltros += " and m.uy_tr_truck_id = " + this.truckID;
				if(this.failureID > 0) whereFiltros += " and m.uy_tr_failure_id = " + this.failureID;
			
				if(this.fechaOrdenDesde!=null) whereFiltros += " and o.datetrx >= '" + this.fechaOrdenDesde + "'";
				if(this.fechaOrdenHasta!=null) whereFiltros += " and o.datetrx <= '" + this.fechaOrdenHasta + "'";
				
				if(this.detalle != null && !this.detalle.equalsIgnoreCase("")){
					
					String description = this.detalle.toLowerCase();
										
					whereFiltros += " and lower (m.description) like '%" + description + "%'";
				}	
				
				insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, idreporte, ad_user_id, fecreporte, uy_tr_truck_id," + 
						" qtykm, uy_tr_serviceorder_id, description, uy_tr_failure_id, uy_tr_maintain_id, dateordered)";
				
				sql = "select m.ad_client_id, m.ad_org_id, '" + this.idReporte + "', " + this.adUserID + ", current_date, m.uy_tr_truck_id, o.kilometros," +
						" m.uy_tr_serviceorder_id, m.description, m.uy_tr_failure_id, m.uy_tr_maintain_id, o.datetrx" +
						" from uy_tr_truckmaintainhistory m" +
						" inner join uy_tr_serviceorder o on m.uy_tr_serviceorder_id = o.uy_tr_serviceorder_id" + whereFiltros;			
				
				DB.executeUpdateEx(insert + sql, null);
				
			}

		} catch (Exception e) {
			
			throw new AdempiereException(e.getMessage());
			
		} finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
	}

	private BigDecimal getProductCost(MTRServiceOrderProd prodLine) {
		
		MPriceListVersion versionDolar = null, versionPeso = null;
		MProductPrice ppriceDolar = null, ppricePeso = null;	
		boolean isDolares = false, isPesos = false;
		int prodID = prodLine.getM_Product_ID();
		BigDecimal totalAmt = Env.ZERO, dividerate = Env.ZERO, priceList = Env.ZERO;
		
		try{
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);			
			
			//obtengo ultima version de lista de precios de compra U$S
			versionDolar = MPriceListVersion.forCurrencyType(getCtx(), 100, false, get_TrxName());
			
			if(versionDolar==null) throw new AdempiereException("No se obtuvo ultima version de lista de precios en Dolares");
			
			//obtengo ultima version de lista de precios de compra $U
			versionPeso = MPriceListVersion.forCurrencyType(getCtx(), 142, false, get_TrxName());
			
			if(versionPeso==null) throw new AdempiereException("No se obtuvo ultima version de lista de precios en Pesos");
			
			ppriceDolar = MProductPrice.forVersionProduct(getCtx(), versionDolar.get_ID(), prodID, get_TrxName());//obtengo precio de producto actual en lista Dolares
			
			//si el producto esta en la lista Dolares
			if(ppriceDolar!=null && ppriceDolar.get_ID() > 0) isDolares = true;
								
			ppricePeso = MProductPrice.forVersionProduct(getCtx(), versionPeso.get_ID(), prodID, get_TrxName());//obtengo precio de producto actual en lista Pesos
			
			//si el producto esta en la lista Pesos y es mayor a 1
			if(ppricePeso!=null && ppricePeso.get_ID()>0 && ppricePeso.getPriceList().compareTo(Env.ONE)>0) isPesos = true;
					
			if(isDolares && !isPesos){ //si esta en lista Dolares y no esta en lista Pesos
				
				totalAmt = prodLine.getQtyRequired().multiply(ppriceDolar.getPriceList());

			} else if(!isDolares && isPesos){ //si no esta en lista Dolares y esta en lista Pesos
				
				priceList = ppricePeso.getPriceList();
				
				//debo convertir precio a Dolares
				dividerate = MConversionRate.getDivideRate(142, 100, today, 0, Env.getAD_Client_ID(getCtx()), Env.getAD_Org_ID(getCtx()));
				
				if (dividerate == null || dividerate.compareTo(Env.ZERO)==0) throw new AdempiereException ("No se obtuvo tasa de cambio $U -> U$S para fecha actual");
				
				priceList = priceList.divide(dividerate, 2, RoundingMode.HALF_UP);
				
				totalAmt = prodLine.getQtyRequired().multiply(priceList);

			} else if(isDolares && isPesos){ //si esta en lista Dolares y esta en lista Pesos
				
				//debo comparar y quedarme con el precio mas actual
				if(ppriceDolar.getCreated().compareTo(ppricePeso.getCreated()) >= 0){//si fecha de precio en Dolares >= fecha precio en Pesos, me quedo con precio Dolares
					
					totalAmt = prodLine.getQtyRequired().multiply(ppriceDolar.getPriceList());						
					
				} else {
					
					priceList = ppricePeso.getPriceList();
					
					//debo convertir precio a Dolares
					dividerate = MConversionRate.getDivideRate(142, 100, today, 0, Env.getAD_Client_ID(getCtx()), Env.getAD_Org_ID(getCtx()));
					
					if (dividerate == null || dividerate.compareTo(Env.ZERO)==0) throw new AdempiereException ("No se obtuvo tasa de cambio $U -> U$S para fecha actual");
					
					priceList = priceList.divide(dividerate, 2, RoundingMode.HALF_UP);
					
					totalAmt = prodLine.getQtyRequired().multiply(priceList);
					
				}

			}				
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}	
		
		return totalAmt;
	}

}
