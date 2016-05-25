package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class PReactivatePO extends SvrProcess{
		
	private int c_order_id = 0;
	private int m_product_id = 0;
	private String processMsg = "Proceso finalizado OK..." + "\n";

	@Override
	protected String doIt() throws Exception {
		return(updateOrder());
	}

	@Override
	protected void prepare() {
	
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
						
				if (name.equalsIgnoreCase("C_Order_ID")) {
					if (para[i].getParameter() != null) {
						this.c_order_id = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				
				if (name.equalsIgnoreCase("M_Product_ID")) {
					if (para[i].getParameter() != null) {
						this.m_product_id = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				
				
			}

		}
		
	}
	
	/**
	 * 
	 * OpenUp. #910	
	 * Descripcion : Metodo que dada una orden y opcionalmente un producto checkea si es apta para ser reabierta
	 * @author  Nicolas Sarlabos 
	 * Fecha : 08/12/2011
	 */
	
	private String updateOrder(){
		
		String sql ="";
		MOrder ord = new MOrder(getCtx(), this.c_order_id, get_TrxName());
		
		try{
			if(this.m_product_id>0){ //si se ingreso producto
				
				MProduct prod = new MProduct(getCtx(), this.m_product_id, get_TrxName());
				
				sql = "SELECT COUNT(c_orderline_id)" +
	                  " FROM c_orderline" +
	                  " WHERE c_order_id=" + ord.get_ID() + " AND m_product_id=" + this.m_product_id;
				
				if(DB.getSQLValue(get_TrxName(), sql)>0){
					
					sql = "SELECT c_orderline_id" +
	                      " FROM c_orderline" +
	                      " WHERE c_order_id=" + ord.get_ID() + " AND m_product_id=" + this.m_product_id;
					
					int ordLineID = DB.getSQLValue(get_TrxName(), sql);
					MOrderLine ordLine = new MOrderLine(getCtx(), ordLineID, get_TrxName());
					
					//Se obtiene la ubicacion
					sql = "SELECT inl.m_locator_id" +
                          " FROM m_inoutline inl" +
                          " WHERE inl.c_orderline_id=" + ordLineID;
					
					int locID = DB.getSQLValue(get_TrxName(), sql);
					//Se obtiene el almacen
					sql = "SELECT inl.m_warehouse_id" +
                          " FROM m_inoutline inl" +
                          " WHERE inl.c_orderline_id=" + ordLineID;
				
					int wareID = DB.getSQLValue(get_TrxName(), sql);
					
					//Si tengo almacen y ubicacion...
					if(wareID>0 && locID>0){
					
						BigDecimal disponible = ordLine.getQtyOrdered().subtract(ordLine.getQtyDelivered());
						//Se obtiene el porcentaje de tolerancia del producto
						sql = "SELECT tolerance_percentage" +
							  " FROM m_replenish" +
							  " WHERE m_product_id=" + prod.getM_Product_ID() +
							  " AND m_warehouse_id=" + wareID +
							  "	AND m_locator_id=" + locID;
						
						Integer percentage = DB.getSQLValue(get_TrxName(), sql);
												
						if(percentage != null){
							if(percentage > 0){
								
								   BigDecimal hundred = new BigDecimal(100);
								   BigDecimal perc = new BigDecimal(percentage);
								   BigDecimal percentageFactor = perc.divide(hundred,2, BigDecimal.ROUND_HALF_UP);
								   BigDecimal value = ordLine.getQtyOrdered().multiply(percentageFactor);
								   disponible = (ordLine.getQtyOrdered().add(value));
								
							} 
						}
						
						if(ordLine.getQtyDelivered().compareTo(disponible)<0 || ordLine.getQtyReserved().compareTo(Env.ZERO)>0){
							
							ordLine.setuy_polineclosed(false);
							ordLine.saveEx();
							
							if(ord.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Closed)){
								
								ord.setDocStatus(DocumentEngine.STATUS_Completed);
								ord.setDocAction(DocumentEngine.ACTION_None);
								ord.saveEx();
							}
							
						}else throw new Exception("Ya se recepcionó la totalidad del producto " + prod.getValue() + " en la orden Nº " + ord.getDocumentNo());
							
					}else throw new Exception("No existe recepción de materiales asociada");
						ordLine.setuy_polineclosed(false); //se deja abierta la linea por si accidentalmente se cerro en la recepcion
						ordLine.saveEx();
					
				}else throw new Exception("El producto " + prod.getValue() + " no pertenece a la orden Nº " + ord.getDocumentNo());
				
							
			}else{  //si no se ingreso producto
				
				ResultSet rs = null;
				PreparedStatement pstmt = null;
				
				try{
					sql = "SELECT c_orderline_id,m_product_id" +
	                      " FROM c_orderline" +
	                      " WHERE c_order_id=" + ord.get_ID();
					
					pstmt = DB.prepareStatement(sql, get_TrxName());
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						
						int ordLineID = rs.getInt("c_orderline_id");
						MOrderLine ordLine = new MOrderLine(getCtx(), ordLineID, get_TrxName());
						MProduct prod = new MProduct(getCtx(), rs.getInt("m_product_id"), get_TrxName());
						
																
						//Se obtiene la ubicacion
						sql = "SELECT inl.m_locator_id" +
	                          " FROM m_inoutline inl" +
	                          " WHERE inl.c_orderline_id=" + ordLineID;
						
						int locID = DB.getSQLValue(get_TrxName(), sql);
						//Se obtiene el almacen
						sql = "SELECT inl.m_warehouse_id" +
	                          " FROM m_inoutline inl" +
	                          " WHERE inl.c_orderline_id=" + ordLineID;
					
						int wareID = DB.getSQLValue(get_TrxName(), sql);
						
						//Si tengo almacen y ubicacion...
						if(wareID>0 && locID>0){
							
							BigDecimal disponible = ordLine.getQtyOrdered().subtract(ordLine.getQtyDelivered());
							//Se obtiene el porcentaje de tolerancia del producto
							sql = "SELECT tolerance_percentage" +
								  " FROM m_replenish" +
								  " WHERE m_product_id=" + prod.getM_Product_ID() +
								  " AND m_warehouse_id=" + wareID +
								  "	AND m_locator_id=" + locID;
							
							Integer percentage = DB.getSQLValue(get_TrxName(), sql);
													
							if(percentage != null){
								if(percentage > 0){
									
									   BigDecimal hundred = new BigDecimal(100);
									   BigDecimal perc = new BigDecimal(percentage);
									   BigDecimal percentageFactor = perc.divide(hundred,2, BigDecimal.ROUND_HALF_UP);
									   BigDecimal value = ordLine.getQtyOrdered().multiply(percentageFactor);
									   disponible = (ordLine.getQtyOrdered().add(value));
									
								} 
							}
							
							if(ordLine.getQtyDelivered().compareTo(disponible)<0 || ordLine.getQtyReserved().compareTo(Env.ZERO)>0){
								
								ordLine.setuy_polineclosed(false);
								ordLine.saveEx();
								
								if(ord.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Closed)){
									
									ord.setDocStatus(DocumentEngine.STATUS_Completed);
									ord.setDocAction(DocumentEngine.ACTION_None);
									ord.saveEx();
								}
								
							}else this.processMsg += "Ya se recepcionó la totalidad del producto " + prod.getValue() + " en la orden Nº " + ord.getDocumentNo() + "\n";
								
						}else this.processMsg += "No existe recepción de materiales asociada para el producto " + prod.getValue() + " en la orden Nº " + ord.getDocumentNo() + "\n";
							ordLine.setuy_polineclosed(false); //se deja abierta la linea por si accidentalmente se cerro en la recepcion
							ordLine.saveEx();
												
					}
								
				}catch (Exception e){
					log.log(Level.SEVERE, sql, e);
					throw new AdempiereException(e.getMessage());
					
				}finally{
					
					DB.close(rs, pstmt);
					rs = null;
					pstmt = null;
					
				}
					
						
			}
		}catch (Exception e){
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e.getMessage());
			
		
		}
	
		return processMsg;
		
		
		
		
	}
	
	
	
	

}
