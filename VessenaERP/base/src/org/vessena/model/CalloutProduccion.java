/**
 * CalloutProduccion.java
 * 13/09/2010
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MConfirmOrderline;
import org.compiere.model.MConfirmorderhdr;
import org.compiere.model.MDocType;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.X_UY_ConfirmOrderline;
import org.compiere.model.X_UY_Confirmorderhdr;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MPPOrder;
import org.eevolution.model.MPPOrderBOM;
import org.eevolution.model.MPPOrderBOMLine;
import org.eevolution.model.X_PP_Order;


/**
 * OpenUp.
 * CalloutProduccion
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 13/09/2010
 */
public class CalloutProduccion extends CalloutEngine {

	/**
	 * Constructor
	 */
	public CalloutProduccion() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp.	
	 * Descripcion : En la confirmacion de la orden, al momento de cargar el numero de orden, debo desplegar
	 * datos de la misma en la ventana de la confirmacion. 
		// OpenUp. Nicolas Garcia. 12/08/2011. Issue #760. Se cambia gran parte de este metodo 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 13/09/2010
	 */
	public String getOrderInfo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		try{

			if (value==null) return "";
			if (!mTab.getKeyColumnName().equalsIgnoreCase(X_UY_Confirmorderhdr.COLUMNNAME_UY_Confirmorderhdr_ID)) return "";
			
			// Verifico que este dentro de la ventana de confirmacion de la orden
						
			// Obtengo informacion del cabezal de la orden
			MPPOrder order = new MPPOrder(ctx, (Integer)value, null);
			
			// Si obtuve datos
			if (order.get_ID() > 0){
				// OpenUp. Nicolas Garcia. 12/08/2011. Issue #760.

				// Obtengo el id de la confirmacion que este en DR para esta
				// orden, si no hay devuevle 0
				int confirmaOrderID = DB.getSQLValue(null,
						"SELECT CASE WHEN EXISTS(SELECT uy_confirmorderhdr_id FROM uy_confirmorderhdr WHERE docstatus='DR' AND pp_order_id=?)"
								+ "THEN (SELECT uy_confirmorderhdr_id FROM uy_confirmorderhdr WHERE docstatus='DR' AND pp_order_id=?) ELSE 0 END", order
								.get_ID(), order.get_ID());

				// Si hay una confirmacion en DR
				if (confirmaOrderID > 0) {
					MConfirmorderhdr confirDR = new MConfirmorderhdr(ctx, confirmaOrderID, null);
					String error = "Existe una confirmacion para esta orden en estado BORRADOR \n " + "Confirmacion Nro: " + confirDR.getDocumentNo()
							+ " Cantidad a confirmar: " + confirDR.getQtyDelivered();

					// Borro pp_order_id
					mTab.setValue("PP_Order_ID", null);

					return error;
				}
				// Fin Issue #760
				

				// OpenUp. Nicolas Garcia. 12/08/2011. Issue #760.
				// Selecciono la fecha del la ultima confirmacion como buena,
				// sino usi la hora actual.
				Timestamp fechaComienzo = DB.getSQLValueTS(null, //OpenUp Nicolas Sarlabos 10/07/2012 #1043, se modifica sql para no considerar confirmaciones anuladas
						"SELECT CASE WHEN EXISTS(SELECT MAX(DateFinish) as fecha FROM uy_confirmorderhdr WHERE pp_order_id=? AND docstatus <> 'VO' GROUP BY pp_order_id) "
								+ "THEN (SELECT MAX(DateFinish) as fecha FROM uy_confirmorderhdr WHERE pp_order_id=? GROUP BY pp_order_id)"
								+ "ELSE (SELECT datepromised FROM pp_order WHERE pp_order_id=?) END as fecha", order.get_ID(), order.get_ID(), order.get_ID());
				//Fin OpenUp Nicolas Sarlabos 10/07/2012 #1043
				mTab.setValue("DateStart", fechaComienzo);// Fecha de comienzo
															// de confirmacion
				// Fecha de fin = fecha de comienzo +1 min
				mTab.setValue("DateFinish", new Timestamp(fechaComienzo.getTime() + new Long("1000")));

				mTab.setValue("DateTrx", fechaComienzo);

				// Fin Issue #760

				// Cargo campos de la ventana con la informacion de la orden
				mTab.setValue("M_Product_ID", order.getM_Product_ID());
				mTab.setValue("C_UOM_ID", order.getC_UOM_ID());
				mTab.setValue("DateAcct", mTab.getValue("DateTrx"));
				mTab.setValue("C_DocTypeTarget_ID", order.getC_DocType_ID());

				MPPOrderBOM orderBom = order.getMPPOrderBOM();

				mTab.setValue("PP_Order_BOM_ID", new BigDecimal(orderBom.getPP_Order_BOM_ID()));
				mTab.setValue("DateOrdered", order.getDateOrdered());
				mTab.setValue("DatePromised", order.getDatePromised());

				mTab.setValue("S_Resource_ID", order.getS_Resource_ID());
				mTab.setValue("M_Warehouse_ID", order.getM_Warehouse_ID());
				mTab.setValue("UY_Resource_ID", new BigDecimal((Integer)order.get_Value("UY_Resource_ID")));
				
				// Cargo Almacen asociado al Producto como Almacen Destino de la Confirmacion de la Orden
				//OpenUp. Nicolas Sarlabos. 30/09/2013. #1206. Se comenta sugerencia de ubicacion en confirmacion de orden.
				//MProduct prod = new MProduct(ctx, order.getM_Product_ID(), null);
				//MLocator locator = new MLocator(ctx, prod.getM_Locator_ID(), null);
				//mTab.setValue("UY_Almacendestino_ID",locator.getM_Warehouse_ID());
				//Fin OpenUp.
				if (mTab.getValue("UY_Resource_ID")==null)
					mTab.setValue("UY_Resource_ID", 1000025);
				
				mTab.setValue("AD_Workflow_ID", order.getAD_Workflow_ID());
				if (mTab.getValue("AD_Workflow_ID")==null)
					mTab.setValue("AD_Workflow_ID", 1000007);
				
				BigDecimal qtyDelivered = order.getQtyOrdered().subtract(order.getQtyDelivered());
				// OpenUp. Nicolas Salrlabos. 15/06/2011. Issue #657
				// Cambios en seteos de cantidades
				mTab.setValue("turno", order.get_Value("turno"));
				mTab.setValue("QtyBatchSize", order.getQtyBatchSize());
				mTab.setValue("QtyBatchs", order.getQtyBatchs());
				mTab.setValue("QtyEntered", order.getQtyOrdered().subtract(order.getQtyDelivered()));
				mTab.setValue("QtyOrdered", order.getQtyOrdered().subtract(order.getQtyDelivered()));
				mTab.setValue("QtyDelivered", qtyDelivered);
				mTab.setValue("QtyReject", order.getQtyReject());
				mTab.setValue("QtyScrap", order.getQtyScrap());
				// Fin Issue #657
				
				
				// Save pestaï¿½a actual (cabezal)
				mTab.dataSave(true);
				
				// Get current confirm order just to delete previous lines 
				MConfirmorderhdr currentConfirmOrder= new MConfirmorderhdr(ctx,(Integer) mTab.getValue("UY_Confirmorderhdr_ID"), null);
				
				// Delete de previous lines only if the confirm order exist 
				if (currentConfirmOrder!=null) {
	 				// Get lines from current confirm order just to delete them
					MConfirmOrderline[] currentLines=currentConfirmOrder.getLines();
					
					// Delete all lines form current confirm order
					for (int i=0;i<currentLines.length;i++) {
						currentLines[i].delete(true);
					}
				}
				
				// Refresca pestaï¿½a actual
				mTab.dataRefresh();
				
				// Obtengo lineas de la orden (PP_Order_BOMLine)
				MPPOrderBOMLine[] lines = order.getLines();	
				
				// Guardo lineas obtenidas en la tabla UY_ConfirmOrd
				if (lines!=null){
					for (int i=0;i<lines.length;i++){
						MConfirmOrderline confLine = new MConfirmOrderline(ctx, 0, null);
						confLine.setUY_Confirmorderhdr_ID((Integer)mTab.getValue("UY_Confirmorderhdr_ID"));
						confLine.setPP_Order_BOMLine_ID(lines[i].getPP_Order_BOMLine_ID());
						confLine.setM_Product_ID(lines[i].getM_Product_ID());
						
						// OpenUp. Nicolas Garcia. 18/08/2011. Issue #824.
						// Cargo Almacen origen desde las OrderBomLines
						confLine.setM_Warehouse_ID(lines[i].getM_Warehouse_ID());

						// preventivamente se sigue guardando este campo el cual
						// no se deberia de usar
						confLine.setuy_almacenorigen(lines[i].getM_Warehouse_ID());

						// Fin Issue #824
						
						confLine.setC_UOM_ID(lines[i].getC_UOM_ID());
						confLine.setQtyRequired(qtyDelivered.multiply(lines[i].getQtyBOM()));
						confLine.setQtyReserved(lines[i].getQtyReserved());
						confLine.setQtyBOM(lines[i].getQtyBOM());
						confLine.setQtyEntered(qtyDelivered.multiply(lines[i].getQtyBOM()));
						confLine.setQtyDelivered(qtyDelivered.multiply(lines[i].getQtyBOM()));
						confLine.setmanual(false);
						confLine.saveEx();
					}
				}
				else{
					return "La Orden No tiene Lineas y por lo tanto No podra ser Confirmada" + (Integer)value;
				}
			}
			else{
				return "No se pudo obtener la informacion de la Orden : " + (Integer)value; 
			}

			// Refresca pestaï¿½a actual
			mTab.dataRefresh();

			return "";
		}
		catch (Exception e){
			log.log(Level.SEVERE, "No se pudo obtener informacion de la Orden", e);
			return e.getMessage();
		}
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Modifica cantidades de las lineas de una confirmacion de orden segun nueva cantidad entregada 
	 * ingresada en el cabezal.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 15/09/2010
	 */
	public String setCantidadesSegunEntregada(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value,Object oldvalue){
		
		try{
			
			if (value==null) return "";
			
			if (!mTab.getKeyColumnName().equalsIgnoreCase(X_UY_Confirmorderhdr.COLUMNNAME_UY_Confirmorderhdr_ID)) return "";
			if (mTab.getValue(X_UY_Confirmorderhdr.COLUMNNAME_UY_Confirmorderhdr_ID)==null) return "";
			
			// Obtengo informacion del cabezal de esta confirmacion
			MConfirmorderhdr header = new MConfirmorderhdr(ctx, (Integer)mTab.getValue(X_UY_Confirmorderhdr.COLUMNNAME_UY_Confirmorderhdr_ID), null);
			if (header.get_ID() <= 0) return "";
			
			// Obtengo lineas para esta confirmacion
			MConfirmOrderline[] lines = header.getLines();
			if (lines==null) return "";

			//OpenUp Nicolas Garcia Issue#657
			// Si me viene valor cero aviso y dejo como estaba antes
			if (((BigDecimal)value).compareTo(Env.ZERO)==0){
				//header.setQtyEntered((BigDecimal)value);
				mTab.setValue( X_UY_Confirmorderhdr.COLUMNNAME_QtyDelivered,(BigDecimal) oldvalue);
				mTab.dataRefresh();
				return "No se puede ingresar cantidad CERO";
			}
			
			// Update header
			header.setQtyDelivered((BigDecimal)value);
			if(header.getQtyOrdered().compareTo(BigDecimal.ZERO)!=0){
				header.setYield((((BigDecimal)value).divide(header.getQtyOrdered(),2,RoundingMode.HALF_UP)).multiply(new BigDecimal(100)));
				mTab.setValue( X_UY_Confirmorderhdr.COLUMNNAME_Yield,header.getYield());
			}
				
			
						
			
			//end
			//header.saveEx();
			
			mTab.dataSave(true);
			
			// Actualizo cantidades de las lineas segun nueva cantidad entregada
			for (MConfirmOrderline line : lines){
				//if (!line.ismanual()){
					
					line.setQtyEntered(header.getQtyDelivered().multiply(line.getQtyBOM()));
					mTab.setValue( X_UY_ConfirmOrderline.COLUMNNAME_QtyEntered,header.getQtyEntered());
					
					line.setQtyRequired(header.getQtyDelivered().multiply(line.getQtyBOM()));
					mTab.setValue( X_UY_ConfirmOrderline.COLUMNNAME_QtyRequired,header.getQtyEntered().multiply(line.getQtyBOM()));
					
					line.setQtyDelivered(header.getQtyDelivered().multiply(line.getQtyBOM()));
					mTab.setValue( X_UY_ConfirmOrderline.COLUMNNAME_QtyDelivered,header.getQtyDelivered());
					
					BigDecimal consumoTotal = (line.getQtyDelivered().add(line.getqtymanual()).add(line.getQtyReject()));
					line.set_ValueOfColumn("UY_ConsumoTotal" , consumoTotal);
					mTab.setValue( "UY_ConsumoTotal" ,consumoTotal);
					
					line.saveEx();					
				//}
			}
			
			// OpenUp. Gabriel Vila. 27/04/2011. Issue #636.
			// Comento para que no me quede la pantalla bloqueada.

			//mTab.dataRefreshAll();
			
			// Fin OpenUp.
			
			return "";
		}
		catch (Exception e){
			log.log(Level.SEVERE, "No se pudo actualizar cantidades de las lineas segun nuevo valor de Cantidad Entregada.", e);
			mTab.setValue( X_UY_Confirmorderhdr.COLUMNNAME_QtyEntered,(BigDecimal) oldvalue);
			return e.getMessage();
		}
	}


	/**
	 * OpenUp.	
	 * Descripcion : Setea consumo total segun esta operacion : cantidad delivered + cantidad manual + cantidad rechazada.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 16/09/2010
	 */
	public String setConsumoTotal(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		try{
			
			if (value==null) return "";
			if (!mTab.getKeyColumnName().equalsIgnoreCase(X_UY_ConfirmOrderline.COLUMNNAME_UY_ConfirmOrderline_ID)) return "";
			if (mTab.getValue(X_UY_ConfirmOrderline.COLUMNNAME_UY_ConfirmOrderline_ID)==null) return "";

			// OpenUp. Nicolas Garcia. 15/08/2011. Issue #811.
			BigDecimal rechazada = (BigDecimal)mTab.getValue("QtyReject");
			// if (rechazada == null) rechazada = Env.ZERO;
			// if (rechazada.compareTo(Env.ZERO) < 0)
			// rechazada = rechazada.negate();


			// La cantida rechazada tiene que sumar y no restar
			BigDecimal consumoTotal = ((BigDecimal) mTab.getValue(X_UY_ConfirmOrderline.COLUMNNAME_QtyRequired)).add((BigDecimal) mTab.getValue("qtymanual"))
					.add((BigDecimal) mTab.getValue("QtyScrap")).add(rechazada);
			// Fin Issue #811
			

			mTab.setValue("QtyReject", rechazada);
			mTab.setValue("UY_ConsumoTotal", consumoTotal);
			
			return "";
		}
		catch (Exception e){
			log.log(Level.SEVERE, "No se pudo actualizar Consumo total de la linea.", e);
			return e.getMessage();
		}
	}

	/**
	 * OpenUp.	
	 * Descripcion : Calcula la cantidad de packs para el producto de una orden de produccion.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 15/10/2010
	 */
	public String setOrderPacks(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		try{
			
			if (value==null) return "";
			if (!mTab.getKeyColumnName().equalsIgnoreCase(X_PP_Order.COLUMNNAME_PP_Order_ID)) return "";
			if (mTab.getValue(X_PP_Order.COLUMNNAME_M_Product_ID)==null) return "";

			// Obtengo data del producto de la orden
			MProduct product = new MProduct(ctx, (Integer)mTab.getValue("M_Product_ID"), null);
			
			// Si no tengo producto salgo
			if (product.get_ID() <= 0) return null;
			
			// Si el valor de unidades por pack del producto es cero o nulo, lo dejo en uno para que no
			// de error la division
			int unitsPerPack = (product.getUnitsPerPack()==0) ? 1 : product.getUnitsPerPack();
			
			BigDecimal packs = ((BigDecimal)mTab.getValue("QtyEntered")).divide(new BigDecimal(unitsPerPack), 2, RoundingMode.HALF_UP);
			
			mTab.setValue("UY_Packs", packs);
			
			return "";
		}
		catch (Exception e){
			log.log(Level.SEVERE, "No se pudo actualizar cantidad de Packs de la Orden.", e);
			return e.getMessage();
		}
	}

	
	/**
	 * OpenUp.	
	 * Descripcion : Setea literal para packs de una orden de produccion.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 15/10/2010
	 */
	public String setOrderPacksLiteral(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		try{
			
			if (value==null) return "";
			if (!mTab.getKeyColumnName().equalsIgnoreCase(X_PP_Order.COLUMNNAME_PP_Order_ID)) return "";
			if (mTab.getValue(X_PP_Order.COLUMNNAME_M_Product_ID)==null) return "";

			// Obtengo data del producto de la orden
			MProduct product = new MProduct(ctx, (Integer)mTab.getValue("M_Product_ID"), null);

			// Si no tengo producto salgo
			if (product.get_ID() <= 0) return null;
			// OpenUp. Nicolas Garcia. 04/08/2011. Issue #811.
			int conversionID = DB.getSQLValue(null, "SELECT c_uom_conversion_id FROM c_uom_conversion WHERE m_product_id =?", product.get_ID());

			int uomID;

			if (conversionID < 1) {
				uomID = (Integer) mTab.getValue("C_UOM_ID");
			} else {
				MUOMConversion uomConv = new MUOMConversion(Env.getCtx(), conversionID, null);
				uomID = uomConv.getC_UOM_To_ID();
			}

			MUOM uom = new MUOM(Env.getCtx(), uomID, null);
			// Fin Issue #811


			
			// Si el valor de sku es nulo pongo SIN SKU a modo de aviso
			String sku = (uom == null) ? " - SIN SKU" : " - " + uom.getName();
			
			String packLiteral = "";
			if (mTab.getValue("UY_Packs")==null){
				packLiteral = "0" + sku;
			}
			else{
				packLiteral = mTab.getValue("UY_Packs").toString().trim() + sku;
			}
			
			mTab.setValue("UY_PacksLiteral", packLiteral);
			
			return "";
		}
		catch (Exception e){
			log.log(Level.SEVERE, "No se pudo actualizar Literal de cantidad de Packs de la Orden.", e);
			return e.getMessage();
		}
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : Set the default manufacter user for the rest of the production lines
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  
	 * Fecha : 06/12/2010
	 */
	public String setMaterialUser(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		try{

			// Get the material user id, to be set
			Integer materialUser=(Integer) value;

			// No action when the user is not set
			if ((materialUser==null)||(materialUser.equals(0))) {
				return("");
			}						

			// Get the id of the order, if null no action needed
			Integer PP_Order_ID = (Integer) mTab.getValue(MPPOrder.COLUMNNAME_PP_Order_ID);
			if (PP_Order_ID==null) {
				return("");
			}

			// Get the order object
			MPPOrder order=new MPPOrder(ctx,PP_Order_ID,null);
			
			// If no order, could be not saved, then do nothing and return no message
			if (order.get_ID() <= 0) {
				return("");
			}
			
			// For each bom line of the order
			MPPOrderBOMLine[] lines=order.getLines();
			for (int i = 0; i < lines.length; i++) {

				// Reset the materials user to the line
				MPPOrderBOMLine line=lines[i];
				line.set_ValueOfColumn(MPPOrderBOMLine.COLUMNNAME_AD_User_ID,materialUser);			// TODO: should be set with the setter, the model must be upated
				line.saveEx();
			}
			
			// return a message only if there are lines in the order
			if (lines.length>0) {
				return("Se sobre-escribio el usuario de materiales en todas las lineas");
			}
			return("");
		}
		catch (Exception e){
			log.log(Level.SEVERE, "No fue posible sobre-escribir el usuario de materiales en las lineas", e);
			return e.getMessage();
		}
	}
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Carga Fechas por defecto y PP_Order en pestaï¿½a Operarios
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 16/02/2011
	 */
	public String setDates(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		
		int confirmorderhdr_id =-1;
		//Pregunto si MConfirmorderhdr en Operarios no es null
		if(value!=null){
			confirmorderhdr_id=(Integer)value;		
			if (confirmorderhdr_id>0){
				MConfirmorderhdr confHdr= new MConfirmorderhdr(ctx, confirmorderhdr_id,null);
				if (confHdr!=null){
					//Si son null corresponde que se carge la fecha.
					if (mTab.getValue("DateStart")==null){
						mTab.setValue("DateStart",confHdr.getDateStart());
					}
					if (mTab.getValue("DateFinish")==null){
						mTab.setValue("DateFinish",confHdr.getDateFinish());
					}	//Carga el PP_Order_Id
					if (mTab.getValue("PP_Order_ID")==null){
						mTab.setValue("PP_Order_ID",confHdr.getPP_Order_ID());
					}
					
				}
			}
		}
		
		
		
		return "";
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Carga Fechas por defecto y pp_order_id en pestaña Paradas
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 29/03/2012
	 */
	public String setDatesParadas(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		
		int confirmorderhdr_id =-1;
		//Pregunto si MConfirmorderhdr en Operarios no es null
		if(value!=null){
			confirmorderhdr_id=(Integer)value;		
			if (confirmorderhdr_id>0){
				MConfirmorderhdr confHdr= new MConfirmorderhdr(ctx, confirmorderhdr_id,null);
				if (confHdr!=null){
					//Si son null corresponde que se carge la fecha.
					if (mTab.getValue("uy_horainicio")==null){
						mTab.setValue("uy_horainicio",confHdr.getDateStart());
					}
					if (mTab.getValue("uy_horafin")==null){
						mTab.setValue("uy_horafin",confHdr.getDateFinish());
					}	//Carga el PP_Order_Id
					if (mTab.getValue("PP_Order_ID")==null){
						mTab.setValue("PP_Order_ID",confHdr.getPP_Order_ID());
					}
					
				}
			}
		}
			
		return "";
	}
	
	/**
	 * OpenUp. Mario Reyes. 23/05/2011. Issue #656.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setProductCategory(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){	
		if (mTab.getValue("M_Product_ID")== null) return "";
		Integer productID = (Integer) mTab
				.getValue(X_PP_Order.COLUMNNAME_M_Product_ID);
		String sql = "SELECT M_Product_Category_ID FROM M_Product WHERE M_Product_ID = ?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal categoria = null;

		try {
			
			//OpenUp Nicolas Garcia issue #797
			//la unidad de medida de la orden tiene que ser igual a la unidad de medidad del producto
			mTab.setValue(MPPOrder.COLUMNNAME_C_UOM_ID, DB.getSQLValue(null,"SELECT COALESCE(C_UOM_ID,100) FROM M_Product WHERE M_Product_ID=?",productID));
			//Fin #797
			
			
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, productID);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				categoria = rs.getBigDecimal("M_Product_Category_ID");
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;

		}

		mTab.setValue(MPPOrder.COLUMNNAME_M_Product_Category_ID, categoria);

		// OpenUp. Nicolas Garcia. 18/08/2011. Issue #824.
		// Se sugiere alamacen
		sql = "SELECT coalesce(UY_ProductCategoryWareHouse.M_WareHouse_ID,0)  "
				+ "FROM UY_ProductCategoryWareHouse WHERE IsDefault='Y' AND UY_ProductCategoryWareHouse.M_Product_Category_ID="
				+ "(SELECT M_Product.M_Product_Category_ID FROM M_Product WHERE M_Product.M_Product_ID =" + productID + ")";
		int warehouseID = DB.getSQLValue(null, sql);
		mTab.setValue(MPPOrder.COLUMNNAME_M_Warehouse_ID, warehouseID);
		// Fin Issue #824

		return "";

	}
	
	/***
	 * Al seleccionar un presupuesto en una orden de fabricacion, debo traer y mostrar
	 * todos los datos asociados al mismo.
	 * OpenUp Ltda. Issue #79 
	 * @author Gabriel Vila - 21/10/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setManufacturerBudget(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		if (value == null) return "";
		
		int uyBudgetID = (Integer)value;
		if (uyBudgetID <= 0) return "";
		
		MBudget budget = new MBudget(ctx, uyBudgetID, null);
		mTab.setValue(X_UY_ManufOrder.COLUMNNAME_C_Activity_ID, budget.getC_Activity_ID());
		mTab.setValue(X_UY_ManufOrder.COLUMNNAME_serie, budget.getserie());
		mTab.setValue(X_UY_ManufOrder.COLUMNNAME_Pic1_ID, budget.getPic1_ID());
		mTab.setValue(X_UY_ManufOrder.COLUMNNAME_Pic2_ID, budget.getPic2_ID());
		mTab.setValue(X_UY_ManufOrder.COLUMNNAME_Pic3_ID, budget.getPic3_ID());
		
		return "";
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : En la confirmacion de la orden, cuando se selecciona el numero de orden, se cargan los
	 * datos de la misma en la ventana de la confirmacion. 
	// OpenUp. INes Fernandez. 05/05/2015. 
	 */
	public String getProdTransfInfo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		try{

			if (value==null) return "";
			if (!mTab.getKeyColumnName().equalsIgnoreCase(X_UY_Confirmorderhdr.COLUMNNAME_UY_Confirmorderhdr_ID)) return "";
			
			// Verifico que este dentro de la ventana de confirmacion de la orden
						
			// Obtengo informacion del cabezal de la orden
			//MPPOrder order = new MPPOrder(ctx, (Integer)value, null);
			MProdTransf order = new MProdTransf(ctx, (Integer)value, null);
			MProdTransfOut [] lines = order.getOutputLines(null);
			//BigDecimal qtyDelivered = Env.ZERO;
			
			MProdTransfOut line = null;					
			
			// Si obtuve datos
			if (order.get_ID() > 0){
				// OpenUp. INes Fernandez. 07/09/2015. Issue #?.
				//Modificación: se setea el C_DocTypeOP_ID con el docTypeTarget de la orden seleccionada
				int docTypeTarget = order.getC_DocTypeTarget_ID();
				MDocType doc = new MDocType(ctx, docTypeTarget, null);
				
				mTab.setValue("C_DocTypeOP_ID", docTypeTarget);
				//End Issue #?
				
				// Obtengo el id de la confirmacion que este en DR para esta
				// orden, si no hay devuevle 0
				/*int confirmaOrderID = DB.getSQLValue(null,
						"SELECT CASE WHEN EXISTS(SELECT uy_confirmorderhdr_id FROM uy_confirmorderhdr WHERE docstatus='DR' AND UY_prodTransf_ID=?)"
								+ "THEN (SELECT uy_confirmorderhdr_id FROM uy_confirmorderhdr WHERE docstatus='DR' AND UY_prodTransf_ID=?) ELSE 0 END", order
								.get_ID(), order.get_ID());

				// Si hay una confirmacion en DR
				if (confirmaOrderID > 0) {
					MConfirmorderhdr confirDR = new MConfirmorderhdr(ctx, confirmaOrderID, null);
					String error = "Existe una confirmacion para esta orden en estado BORRADOR \n " + "Confirmacion Nro: " + confirDR.getDocumentNo()
							+ " Cantidad a confirmar: " + confirDR.getQtyDelivered();

					// Borro pp_order_id
					mTab.setValue("UY_ProdTransf_ID", null);

					return error;
				}*/
				// Fin Issue #760				

				// OpenUp. Nicolas Garcia. 12/08/2011. Issue #760.
				// Selecciono la fecha del la ultima confirmacion como buena,
				// sino uso la hora actual.
				/*Timestamp fechaComienzo = DB.getSQLValueTS(null, //OpenUp Nicolas Sarlabos 10/07/2012 #1043, se modifica sql para no considerar confirmaciones anuladas
						"SELECT CASE WHEN EXISTS(SELECT MAX(DateFinish) as fecha FROM uy_confirmorderhdr WHERE UY_prodTransf_ID=? AND docstatus <> 'VO' GROUP BY UY_prodTransf_ID) "
								+ "THEN (SELECT MAX(DateFinish) as fecha FROM uy_confirmorderhdr WHERE UY_prodTransf_ID=? GROUP BY UY_prodTransf_ID)"
								+ "ELSE (SELECT now()::timestamp) END as fecha", order.get_ID(), order.get_ID(), order.get_ID());*/
				
				/*Timestamp fechaComienzo = DB.getSQLValueTS(null,"SELECT MAX(DateFinish) as fecha FROM uy_confirmorderhdr WHERE UY_prodTransf_ID=? AND docstatus <> 'VO'", order.get_ID());
				
				if(fechaComienzo==null) fechaComienzo = new Timestamp(System.currentTimeMillis());			
				
				//Fin OpenUp Nicolas Sarlabos 10/07/2012 #1043
				mTab.setValue("DateStart", fechaComienzo);// Fecha de comienzo
															// de confirmacion*/
				// Fecha de fin = fecha de comienzo +1 min
				mTab.setValue("DateFinish", new Timestamp(((Timestamp)mTab.getValue("DateStart")).getTime() + new Long("1000")));

				//mTab.setValue("DateTrx", fechaComienzo);

				// Fin Issue #760

				// Cargo campos de la ventana con la informacion de la orden
				//PARA AMBAS:
				mTab.setValue("DateAcct", mTab.getValue("DateTrx"));
			
				mTab.setValue("C_DocTypeTarget_ID", order.getC_DocType_ID());
				mTab.setValue("DateOrdered", order.getDateOrdered());
				mTab.setValue("DatePromised", order.getDatePromised());
				mTab.setValue("turno", order.getTurno());
				mTab.setValue("UY_Resource_ID", order.getUY_Resource_ID());
				mTab.setValue("observaciones", order.getobservaciones());	
				
				mTab.setValue("Fotopolimeros", order.get_ValueAsInt("Fotopolimeros"));
				mTab.setValue("Portaclises", order.get_ValueAsInt("Portaclises"));
				mTab.setValue("Tintas", order.get_ValueAsInt("Tintas"));
				mTab.setValue("Aditivos", order.get_ValueAsInt("Aditivos"));
				
								
				//Multiple Output (ORDEN DE CORTE)
				if(order.isMultipleOutput()){
					
					mTab.setValue("M_Product_ID", order.getM_Product_ID());
					mTab.setValue("M_Warehouse_ID", order.getM_Warehouse_ID());//ORIGEN
					mTab.setValue("C_UOM_ID", order.getC_UOM_ID()); 
					mTab.setValue("QtyRequired", order.getQtyOrdered());
					
				} else {
					
					if(doc.getValue().equalsIgnoreCase("ordenreb")){
						
						mTab.setValue("UY_Almacendestino_ID", order.getM_Warehouse_ID_1());//almacén destino
						mTab.setValue("M_Product_ID", order.getM_Product_To_ID());//producto de salida
						mTab.setValue("QtyLabel", order.getQtyLabel());
						mTab.setValue("MtsBobbin", order.getMtsBobbin());					
						
					} else {
						
						if (lines!=null && lines.length==1){
							
							line = lines[0];
							
							mTab.setValue("UY_Almacendestino_ID", line.getM_Warehouse_ID());//almacén destino
							mTab.setValue("M_Product_ID", line.getM_Product_ID());
							mTab.setValue("C_UOM_ID", line.getC_UOM_ID());
							mTab.setValue("QtyOrdered", line.getQtyEntered().subtract(order.getQtyDelivered()));	
							//qtyDelivered = line.getQtyOrdered().subtract(order.getQtyDelivered());							
							mTab.setValue("QtyEntered", line.getQtyOrdered().subtract(order.getQtyDelivered()));//VER*********
							
														
							//mTab.setValue("QtyDelivered", qtyDelivered);
							
							//mTab.setValue("UY_NumRollos", line.get_Value("UY_NumRollos"));
							//mTab.setValue("QtyReject", order.getQtyReject());
							//mTab.setValue("QtyScrap", order.getQtyScrap());						
						}							
						
					}			
										
				}
				
				// Save pestaña actual (cabezal)
				mTab.dataSave(true);
				
				//ToDo: ver lista de materiales
				//MPPOrderBOM orderBom = order.getMPPOrderBOM();

				//mTab.setValue("PP_Order_BOM_ID", new BigDecimal(orderBom.getPP_Order_BOM_ID()));					
				
				// Get current confirm order just to delete previous lines 
				if(mTab.getValue("UY_Confirmorderhdr_ID")!=null){
					MConfirmorderhdr currentConfirmOrder= new MConfirmorderhdr(ctx,(Integer) mTab.getValue("UY_Confirmorderhdr_ID"), null);
					
					// Delete the previous lines only if the confirm order exist 
					if (currentConfirmOrder!=null) {
		 				// Get lines from current confirm order just to delete them
						MConfirmOrderline[] currentLines=currentConfirmOrder.getLines();
						
						// Delete all lines form current confirm order
						for (int i=0;i<currentLines.length;i++) {
							currentLines[i].delete(true);
						}
					}
				}

				
				// Refresca pestaña actual
				mTab.dataRefresh();		
				
				//Multiple Output (ORDEN DE CORTE)
				if(order.isMultipleOutput()){
					
					MProdTransfOut [] outLines = order.getOutputLines("");
					
					int idStatusApproved = MStockStatus.getStatusApprovedID(null);
					
					for(MProdTransfOut oLine : outLines){
						
						//int product_ID= oLine.getM_Product_ID();
						
						MConfirmOrderline confLine = new MConfirmOrderline(ctx, 0, null);
						confLine.setUY_Confirmorderhdr_ID((Integer)mTab.getValue("UY_Confirmorderhdr_ID"));
						confLine.setM_Product_ID(oLine.getM_Product_ID());
						confLine.set_ValueOfColumn("UY_NumRollos", oLine.get_Value("UY_NumRollos"));
						confLine.set_ValueOfColumn("M_Warehouse_ID_1", oLine.getM_Warehouse_ID());
						confLine.setC_UOM_ID(oLine.getC_UOM_ID());
						/*String sql = "SELECT COALESCE(SUM(L.QtyDelivered), 0) FROM UY_ConfirmOrderHdr H, UY_ConfirmOrderline L"
								+ " WHERE L.UY_ConfirmOrderHdr_ID = H.UY_ConfirmOrderHdr_ID"
								+ "	AND H.UY_ProdTransf_ID = " + order.get_ID()  
								+ "	AND H.DocStatus <> 'VO'"
								+ "	AND L.M_Product_ID = " + product_ID; 
						BigDecimal totalAlreadyDeliv = DB.getSQLValueBDEx(null, sql).setScale(3);*/
						//BigDecimal pendiente = oLine.getQtyOrdered().subtract(totalAlreadyDeliv).setScale(3); //TODO: check scale
						//if(pendiente.compareTo(BigDecimal.ZERO)<0) pendiente = BigDecimal.ZERO;// para que no muestre valores negativos
						//confLine.setQtyDelivered(pendiente); comentado a pedido de LC ********
						confLine.setQtyDelivered(BigDecimal.ZERO);
						confLine.set_ValueOfColumn("IsFinalProduct", oLine.get_Value("IsFinalProduct"));
						confLine.setQtyDelivered(oLine.getQtyEntered());
						
						//confLine.setQtyDelivered(oLine.getQtyEntered());//puede usar distintas uom en el output de la orden
						confLine.setUY_StockStatus_ID(idStatusApproved);
						confLine.setmanual(false);

						confLine.saveEx();						
						
					}					
					
				} else {
					
					MProdTransfInput[] inputLines = order.getInputLines("");
					
					for (MProdTransfInput il : inputLines){
						
						MProduct prod = (MProduct)il.getM_Product();
						
						if (!doc.getValue().equalsIgnoreCase("ordencorte") && !doc.getValue().equalsIgnoreCase("ordenreb")) {
							
							if(prod.get_ValueAsBoolean("IsBobbin")){
								
								MConfirmOrderBobbin confBobbin = new MConfirmOrderBobbin(ctx, 0, null);
								confBobbin.setUY_Confirmorderhdr_ID((Integer)mTab.getValue("UY_Confirmorderhdr_ID"));
								confBobbin.setM_Product_ID(prod.get_ID());
								confBobbin.setUY_Desperdicio(Env.ZERO);
								confBobbin.setUY_RefileYDescarne(Env.ZERO);
								confBobbin.setWeight(Env.ZERO);
								confBobbin.setM_Warehouse_ID(il.getM_Warehouse_ID());
								confBobbin.setQtyOrdered(il.getQtyOrdered());
								confBobbin.saveEx();					
								
							} else {
								
								MConfirmOrderline confLine = new MConfirmOrderline(ctx, 0, null);
								confLine.setUY_Confirmorderhdr_ID((Integer)mTab.getValue("UY_Confirmorderhdr_ID"));
								confLine.setM_Product_ID(il.getM_Product_ID());
								confLine.setM_Warehouse_ID(il.getM_Warehouse_ID());
								confLine.setuy_almacenorigen(il.getM_Warehouse_ID());
								confLine.setC_UOM_ID(il.getC_UOM_ID());
								confLine.setQtyRequired(il.getQtyEntered());
								confLine.setQtyReserved(il.getQtyEntered());
								confLine.setQtyBOM(il.getQtyEntered());
								confLine.setQtyEntered(il.getQtyEntered());
								confLine.setQtyDelivered(il.getQtyEntered());
								confLine.setmanual(false);
								confLine.saveEx();	
							
							}
							
						} else {
							
							MConfirmOrderline confLine = new MConfirmOrderline(ctx, 0, null);
							confLine.setUY_Confirmorderhdr_ID((Integer)mTab.getValue("UY_Confirmorderhdr_ID"));
							//confLine.setPP_Order_BOMLine_ID(bomLines[i].getPP_Order_BOMLine_ID());
							confLine.setM_Product_ID(il.getM_Product_ID());
							
							// OpenUp. Nicolas Garcia. 18/08/2011. Issue #824.
							// Cargo Almacen origen desde las OrderBomLines
							confLine.setM_Warehouse_ID(il.getM_Warehouse_ID());

							// preventivamente se sigue guardando este campo el cual
							// no se deberia de usar
							confLine.setuy_almacenorigen(il.getM_Warehouse_ID());

							// Fin Issue #824
							
							confLine.setC_UOM_ID(il.getC_UOM_ID());
							confLine.setQtyRequired(il.getQtyEntered());
							confLine.setQtyReserved(il.getQtyEntered());
							confLine.setQtyBOM(il.getQtyEntered());
							confLine.setQtyEntered(il.getQtyEntered());
							confLine.setQtyDelivered(il.getQtyEntered());
							confLine.setmanual(false);
							confLine.saveEx();							
						}						
					}
					
					//Obtengo lineas de la orden (PP_Order_BOMLine)
					/*MPPOrderBOM orderBom = line.getMPPOrderBOMFromOutputLine(line.get_ID());
					MPPOrderBOMLine [] bomLines = orderBom.getLines();
					
					// Guardo lineas obtenidas en la tabla UY_ConfirmOrd
					if (bomLines!=null){
						for (int i=0;i<bomLines.length;i++){
							MConfirmOrderline confLine = new MConfirmOrderline(ctx, 0, null);
							confLine.setUY_Confirmorderhdr_ID((Integer)mTab.getValue("UY_Confirmorderhdr_ID"));
							confLine.setPP_Order_BOMLine_ID(bomLines[i].getPP_Order_BOMLine_ID());
							confLine.setM_Product_ID(bomLines[i].getM_Product_ID());
							
							// OpenUp. Nicolas Garcia. 18/08/2011. Issue #824.
							// Cargo Almacen origen desde las OrderBomLines
							confLine.setM_Warehouse_ID(bomLines[i].getM_Warehouse_ID());

							// preventivamente se sigue guardando este campo el cual
							// no se deberia de usar
							confLine.setuy_almacenorigen(bomLines[i].getM_Warehouse_ID());

							// Fin Issue #824
							
							confLine.setC_UOM_ID(bomLines[i].getC_UOM_ID());
							confLine.setQtyRequired(qtyDelivered.multiply(bomLines[i].getQtyBOM()));
							confLine.setQtyReserved(bomLines[i].getQtyReserved());
							confLine.setQtyBOM(bomLines[i].getQtyBOM());
							confLine.setQtyEntered(qtyDelivered.multiply(bomLines[i].getQtyBOM()));
							confLine.setQtyDelivered(qtyDelivered.multiply(bomLines[i].getQtyBOM()));
							confLine.setmanual(false);
							confLine.saveEx();
						}
					}
					else{
						return "La Orden No tiene Lineas y por lo tanto No podra ser Confirmada" + (Integer)value;
					}*/					
					
				}			
				
			}
			else{
				return "No se pudo obtener la informacion de la Orden : " + (Integer)value; 
			}

			// Refresca pestaï¿½a actual
			mTab.dataRefresh();

			return "";
		}
		catch (Exception e){
			log.log(Level.SEVERE, "No se pudo obtener informacion de la Orden", e);
			return e.getMessage();
		}
	}
	
	/**
 	 *	Order Line - Quantity.
 	 *		- called from C_UOM_ID, QtyEntered, QtyOrdered
 	 *		- enforces qty UOM relationship
 	 *  @param ctx      Context
 	 *  @param WindowNo current Window No
 	 *  @param mTab     Model Tab
 	 *  @param mField   Model Field
 	 *  @param value    The new value
 	 */
 	public String ProdTransfqty (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
 	{
 		if (value == null)
 			return "";
 		//setCalloutActive(true);
 
 		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
 		BigDecimal QtyOrdered = Env.ZERO ; 
 		BigDecimal QtyEntered = Env.ZERO ; //, PriceActual, PriceEntered;
 		//*******************
 		M_Product_ID= Integer.parseInt(mTab.get_ValueAsString("M_Product_ID"));
 
 		//	No Product
 		if (M_Product_ID == 0)
 		{
 			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
 			mTab.setValue("QtyOrdered", QtyEntered);
 		}
 		//	UOM Changed - convert from Entered -> Product
 		else if (mField.getColumnName().equals("C_UOM_ID"))
 		{
 
 			int C_UOM_To_ID = ((Integer)value).intValue();
 			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
 			QtyOrdered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
 					C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
 				QtyOrdered = QtyEntered;
 			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
 			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
 			mTab.setValue("QtyOrdered", QtyOrdered);
 		}
 		//	QtyEntered changed - calculate QtyOrdered
 		else if (mField.getColumnName().equals("QtyEntered"))
 		{
 			//int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
 			int C_UOM_To_ID = (Integer)mTab.getValue("C_UOM_ID");
 			QtyEntered = (BigDecimal)value;
 			//*******************
 			int docID = Env.getContextAsInt (ctx, WindowNo, 0, "C_DocTypeTarget_ID");
 			MDocType docTypeTarget = new MDocType(ctx, docID, null);
 			if(docTypeTarget!=null && docTypeTarget.getValue().equals("ordencorte")) C_UOM_To_ID = Integer.parseInt(mTab.get_ValueAsString("C_UOM_ID"));
 			
 			//*******************
 			QtyOrdered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
 					C_UOM_To_ID, QtyEntered);
 			if (QtyOrdered == null)
 				QtyOrdered = QtyEntered;
 			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
 			log.fine("qty - UOM=" + C_UOM_To_ID 
					+ ", QtyEntered=" + QtyEntered
 					+ " -> " + conversion 
 					+ " QtyOrdered=" + QtyOrdered);
 			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyOrdered", QtyOrdered);
		}
		//	QtyOrdered changed - calculate QtyEntered
		else if (mField.getColumnName().equals("QtyOrdered"))
		{
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyOrdered = (BigDecimal)value;
			QtyEntered = MUOMConversion.convertProductTo (ctx, M_Product_ID, 
					C_UOM_To_ID, QtyOrdered);
			if (QtyEntered == null)
				QtyEntered = QtyOrdered;
			boolean conversion = QtyOrdered.compareTo(QtyEntered) != 0;
			log.fine("qty - UOM=" + C_UOM_To_ID 
					+ ", QtyOrdered=" + QtyOrdered
					+ " -> " + conversion 
					+ " QtyEntered=" + QtyEntered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyEntered", QtyEntered);
		}
		//return qtyBatch(ctx,WindowNo,mTab,mField,value);
		return "";
	}	//	qty
 	
	/***
	 * Al seleccionar un producto de insumo en una OP de retail, debo traer y mostrar su UM.
	 * OpenUp Ltda. Issue #4125
	 * @author Nicolas Sarlabos - 19/10/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setUMProdTransfInput(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		if (value == null) return "";
		
		int prodID = (Integer)value;
		if (prodID <= 0) return "";
		
		MProduct prod = new MProduct(ctx, prodID, null);
		
		mTab.setValue("C_Uom_ID", prod.getC_UOM_ID());
		
		return "";
	}
	
	
}
