/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCountry;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MLocation;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MPriceList;
import org.compiere.model.MProductPricing;
import org.compiere.model.MRole;
import org.compiere.model.MTax;
import org.compiere.model.MUOMConversion;
import org.compiere.model.X_C_Invoice;
import org.compiere.model.X_C_Order;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author gbrust
 *
 */
public class CalloutTransporte extends CalloutEngine {

	
	public CalloutTransporte() {
		
	}
	
	
	/***
	 * Carga de datos del tipo de vehiculo segun el vehiculo seleccionado
	 * OpenUp Ltda. Issue #1605
	 * @author Guillermo Brust - 27/11/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadDataVehicle(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int truckID = ((Integer)value).intValue();
		
		if (truckID == 0) return "";		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;		

		try {		
			
			String sql = "select tt.axistype as tipoEje, tt.qtyaxis as cantEjes, tt.qtyaux as cantAux," +
			             " tt.locatorvalue as posicion, tt.qty as cantNeumaticos, coalesce(t.qtykm,0) as kilometros" +
						 " from uy_tr_trucktype tt" +
						 " inner join uy_tr_truck t on tt.uy_tr_trucktype_id = t.uy_tr_trucktype_id" +
						 " where t.uy_tr_truck_id = " + truckID;

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
		
			if(rs.next()){
				
				mTab.setValue(X_UY_TR_TireMove.COLUMNNAME_AxisType, rs.getString("tipoEje"));
				mTab.setValue(X_UY_TR_TireMove.COLUMNNAME_QtyAxis, rs.getInt("cantEjes"));
				mTab.setValue(X_UY_TR_TireMove.COLUMNNAME_QtyAux, rs.getInt("cantAux"));
				mTab.setValue(X_UY_TR_TireMove.COLUMNNAME_Qty, rs.getInt("cantNeumaticos"));	
				mTab.setValue(X_UY_TR_TireMove.COLUMNNAME_LocatorValue, rs.getInt("posicion"));
				mTab.setValue(X_UY_TR_TireMove.COLUMNNAME_QtyKm, rs.getInt("kilometros"));	
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}
		
		return "";
	}
	
	
	/***
	 * Seteo de valor del campo Tractor en Orden de Compra. Se debe cargar proveedor. 
	 * OpenUp Ltda. Issue #1626 
	 * @author Gabriel Vila - 05/05/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setOrderTruck(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int truckID = ((Integer)value).intValue();
		
		if (truckID == 0) return "";
		
		MTRTruck truck = new MTRTruck(ctx, truckID, null);
		
		if (truck.get_ID() <= 0) return "";
		
		mTab.setValue(X_C_Order.COLUMNNAME_C_BPartner_ID, truck.getC_BPartner_ID_P());//OpenUp. Nicolas Sarlabos. 21/04/2015. #4009.
		
		return "";
	}

	/***
	 * Seteo de valor del campo Frecuencia kilometros en un nueva asociacion de mantenimiento preventivo 
	 * y vehiculo.
	 * OpenUp Ltda. Issue #1626 
	 * @author Gabriel Vila - 05/07/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setTruckMaintainKm(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int maintainID = ((Integer)value).intValue();
		
		if (maintainID == 0) return "";
		
		MTRMaintain model = new MTRMaintain(ctx, maintainID, null);
		
		if (model.get_ID() <= 0) return "";
		
		if (model.getKilometros() > 0){
			mTab.setValue(X_UY_TR_TruckMaintain.COLUMNNAME_Kilometros, model.getKilometros());	
		}
		
		return "";
	}

	
	/***
	 * Cambio en check de seleccionado en la grilla de neumaticos para colocar
	 * OpenUp Ltda. Issue #1605
	 * @author Guillermo Brust - 28/11/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeSelectedTiresOpen(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";
		
		mTab.dataSave(true);
		
		return "";
	}
	
	
	
	/***
	 * Carga de datos del neumatico, ultimo vehiculo, posicion, y datos de kilometrajes
	 * OpenUp Ltda. Issue #1606
	 * @author Guillermo Brust - 05/12/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadDataLastVehicle(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int tireID = ((Integer)value).intValue();
		
		if (tireID == 0) return "";		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;		

		try {		
			
			String sql = "select max(mov.datetrx) as fechaTransaccion, mov.uy_tr_truck_id as vehiculo, line.locatorvalue as posicion," +
						 " tire.qtykm2 as realnuevo, tire.qtykm as estnuevo, tire.c_bpartner_id, tire.c_currency_id " +
						 " from uy_tr_tire tire" +
						 " left join uy_tr_tiremoveline line on tire.uy_tr_tire_id = line.uy_tr_tire_id" +
						 " left join uy_tr_tiremove mov on line.uy_tr_tiremove_id = mov.uy_tr_tiremove_id and mov.docstatus = 'CO'" +
						 " where tire.uy_tr_tire_id = " + tireID +
						 " group by mov.uy_tr_truck_id, line.locatorvalue, tire.qtykm2, tire.qtykm, " +
						 " tire.c_bpartner_id, tire.c_currency_id ";

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
		
			if(rs.next()){
				
				mTab.setValue(X_UY_TR_Recauchutaje.COLUMNNAME_UY_TR_Truck_ID, rs.getInt("vehiculo"));
				if (rs.getInt("posicion") > 0){
					mTab.setValue(X_UY_TR_Recauchutaje.COLUMNNAME_LocatorValue, rs.getInt("posicion"));	
				}
				
				mTab.setValue(X_UY_TR_Recauchutaje.COLUMNNAME_QtyKm, rs.getInt("realnuevo"));
				mTab.setValue(X_UY_TR_Recauchutaje.COLUMNNAME_QtyKm2, rs.getInt("estnuevo"));
				mTab.setValue(X_UY_TR_Recauchutaje.COLUMNNAME_C_BPartner_ID, rs.getInt("c_bpartner_id"));
				mTab.setValue(X_UY_TR_Recauchutaje.COLUMNNAME_C_Currency_ID, rs.getInt("c_currency_id"));
				
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}
		
		return "";
	}
	
	
	
	
	/***
	 * Carga de datos de remolque y si es consolidado o no
	 * OpenUp Ltda. Issue #1625
	 * @author Guillermo Brust - 12/12/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadDataLoadManage(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int transOrderID = ((Integer)value).intValue();
		
		if (transOrderID == 0) return "";		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "";

		try {		
			
			sql = "select o.remolque_id, count(ol.uy_tr_transorderline_id) as cargas" +
						 " from uy_tr_transorder o" +
						 " left join uy_tr_transorderline ol on o.uy_tr_transorder_id = ol.uy_tr_transorder_id" +
						 " where o.uy_tr_transorder_id = " + transOrderID +
						 " group by o.remolque_id";

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
		
			if(rs.next()){
				
				mTab.setValue(X_UY_TR_LoadManage.COLUMNNAME_Remolque_ID, rs.getInt("remolque_id"));				
				
				if(rs.getInt("cargas") > 1) {
					mTab.setValue(X_UY_TR_LoadManage.COLUMNNAME_IsConsolidated, "Y");
				}
				
			}
			
			//seteo tractor y conductor actualmente asociado a la orden de transporte para el expediente			
			int tripID = Env.getContextAsInt (ctx, WindowNo, 0, "UY_TR_Trip_ID");
			int orderID = Env.getContextAsInt (ctx, WindowNo, 0, "UY_TR_TransOrder_ID");
						
			if(orderID > 0){
				
				MTRTransOrder order = new MTRTransOrder(Env.getCtx(),orderID,null); //instancio orden de transporte
				
				mTab.setValue(X_UY_TR_LoadManage.COLUMNNAME_UY_Ciudad_ID, order.getUY_Ciudad_ID());
				mTab.setValue(X_UY_TR_LoadManage.COLUMNNAME_UY_Ciudad_ID_1, order.getUY_Ciudad_ID_1());		
				
				if(tripID > 0){
					
					sql = "select max(uy_tr_loadmanage_id)" +
							" from uy_tr_loadmanage" +
							" where uy_tr_transorder_id = " + orderID +
							" and uy_tr_trip_id = " + tripID +
							" and uy_tr_truck_id is not null";				
					int loadID = DB.getSQLValueEx(null, sql);
					
					if(loadID > 0) {
						
						MTRLoadManage load = new MTRLoadManage(Env.getCtx(),loadID,null); //instancio linea de gestion de carga
						MTRTruck truck = (MTRTruck) load.getUY_TR_Truck(); //obtengo tractor
						
						mTab.setValue(X_UY_TR_LoadManage.COLUMNNAME_UY_TR_Truck_ID, truck.get_ID());
						mTab.setValue(X_UY_TR_LoadManage.COLUMNNAME_UY_TR_Driver_ID, load.getUY_TR_Driver_ID());			
						
					}									
					
				}			
				
			}			
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}
		
		return "";
	}
	
	
	
	/***
	 * Carga de datos del CRT en la grilla de expedientes en las ordenes de transporte 
	 * OpenUp Ltda. Issue #1624
	 * @author Guillermo Brust - 12/12/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadDataTripTransOrder(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {

		if (value == null) return "";

		int crtID = ((Integer)value).intValue();

		if (crtID == 0) return "";		

		MTRCrt crt = new MTRCrt(Env.getCtx(), crtID, null);	
		//MTRTrip trip = new MTRTrip(Env.getCtx(), crt.getUY_TR_Trip_ID(), null); //instancio el expediente
		MInvoice inv = new MInvoice(Env.getCtx(), crt.getC_Invoice_ID(), null); //instancio la proforma

		mTab.setValue("UY_TR_Trip_ID", crt.getUY_TR_Trip_ID());

		mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_UY_TR_PackageType_ID, inv.get_ValueAsInt("UY_TR_PackageType_ID"));	
		mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_C_Currency_ID, crt.getC_Currency_ID());
		
		String sql = " select coalesce(qtyopen,0) from vuy_tr_qtyopencrt_otline where uy_tr_crt_id = " + crt.get_ID();			
		
		BigDecimal qtyDisponible = DB.getSQLValueBDEx(null, sql);
				
		mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_QtyPackage, qtyDisponible); //seteo cantidad de bultos disponible		
		
		if(qtyDisponible.compareTo(Env.ZERO)==0){ //si cantidad de bultos disponibles es cero, seteo en cero los demas valores

			mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Weight, Env.ZERO);
			mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Weight2, Env.ZERO);
			mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Volume, Env.ZERO);
			mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_ProductAmt, Env.ZERO);
			mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Amount, Env.ZERO);
		
		} else { //realizo prorrateos y seteo los demas valores

			BigDecimal pesoBruto = (crt.getWeight().multiply(qtyDisponible)).divide(inv.getuy_cantbultos(), 3, RoundingMode.HALF_UP);
			BigDecimal pesoNeto = (crt.getWeight2().multiply(qtyDisponible)).divide(inv.getuy_cantbultos(), 3, RoundingMode.HALF_UP);
			BigDecimal importe = (inv.getAmtOriginal().multiply(qtyDisponible)).divide(inv.getuy_cantbultos(), 3, RoundingMode.HALF_UP);
			BigDecimal volume = (crt.getVolume().multiply(qtyDisponible)).divide(inv.getuy_cantbultos(), 3, RoundingMode.HALF_UP);
			BigDecimal amount = (inv.getTotalLines().multiply(qtyDisponible)).divide(inv.getuy_cantbultos(), 2, RoundingMode.HALF_UP);

			mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Weight, pesoBruto);
			mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Weight2, pesoNeto);
			mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_ProductAmt, importe);
			mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Volume, volume);
			mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Amount, amount);


		}

		return "";
	}	

	
	/***
	 * 
	 * OpenUp Ltda. Issue #
	 * @author Guillermo Brust - 04/02/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadDataTripProduct(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		if(value.toString().equalsIgnoreCase("0")) return "";
		
		int docID = Env.getContextAsInt (ctx, WindowNo, 0, "C_DocTypeTarget_ID");
		
		if(docID <= 0) return "";
		
		MDocType doc = new MDocType(ctx,docID,null); //obtengo tipo de documento
		
		if(doc.getValue()!=null && (doc.getValue().equalsIgnoreCase("fileprofinvoice") || doc.getValue().equalsIgnoreCase("freightinvoice") 
				|| doc.getValue().equalsIgnoreCase("et-ticket") || doc.getValue().equalsIgnoreCase("et-nc") 
				|| doc.getValue().equalsIgnoreCase("trcotizacion") || doc.getValue().equalsIgnoreCase("customerncflete"))){
			
			BigDecimal porcentajeIngresado = (BigDecimal) mTab.getValue(mField.getColumnName());
			
			if(porcentajeIngresado.compareTo(Env.ZERO) < 0) throw new AdempiereException ("El porcentaje no puede ser menor a cero");
			if(porcentajeIngresado.compareTo(Env.ONEHUNDRED) > 0) throw new AdempiereException ("El porcentaje no puede ser mayor a 100%");
			
			if(porcentajeIngresado.compareTo(new BigDecimal(100)) > 0){
				return "";
			}
			
			BigDecimal porcentajeOpuesto = new BigDecimal(100).subtract(porcentajeIngresado);
			
			if(mField.getColumnName().equals("InterPercentage")){
				mTab.setValue("NationalPercentage", porcentajeOpuesto);
			}else{
				if(mField.getColumnName().equals("NationalPercentage")){
					mTab.setValue("InterPercentage", porcentajeOpuesto);
				}
			}		
			
			mTab.setValue("NationalAmt", Env.ZERO);
			mTab.setValue("InternationalAmt", Env.ZERO);
			
			mTab.dataSave(true);			
		}		
				
		return "";
	}	
	
	/***
	 * 
	 * OpenUp Ltda. Issue #3169.
	 * @author Nicolas Sarlabos - 31/10/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadBudgetPercentage(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {

		if (value == null) return "";
		if(value.toString().equalsIgnoreCase("0")) return "";
		
		BigDecimal porcentajeIngresado = (BigDecimal) mTab.getValue(mField.getColumnName());

		if(porcentajeIngresado.compareTo(Env.ZERO) < 0) throw new AdempiereException ("El porcentaje no puede ser menor a cero");
		if(porcentajeIngresado.compareTo(Env.ONEHUNDRED) > 0) throw new AdempiereException ("El porcentaje no puede ser mayor a 100%");

		if(porcentajeIngresado.compareTo(new BigDecimal(100)) > 0){
			return "";
		}

		BigDecimal porcentajeOpuesto = new BigDecimal(100).subtract(porcentajeIngresado);

		if(mField.getColumnName().equals("InterPercentage")){
			mTab.setValue("NationalPercentage", porcentajeOpuesto);
		}else{
			if(mField.getColumnName().equals("NationalPercentage")){
				mTab.setValue("InterPercentage", porcentajeOpuesto);
			}
		}		
		
		//mTab.setValue("NationalAmt", Env.ZERO);
		//mTab.setValue("InternationalAmt", Env.ZERO);
		
		BigDecimal price = (BigDecimal)mTab.getValue("PriceEntered");
	
		
		if(price==null) price = Env.ZERO;
		
		if(price.compareTo(Env.ZERO)>0){
			
			BigDecimal interAmt = Env.ZERO;
			BigDecimal natAmt = Env.ZERO;
			BigDecimal interPercent = (BigDecimal)mTab.getValue("InterPercentage");
			BigDecimal natPercent = (BigDecimal)mTab.getValue("NationalPercentage");
			
			if(interPercent==null) interPercent = Env.ZERO;
			if(natPercent==null) natPercent = Env.ZERO;			
			
			interAmt = (interPercent.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(price).setScale(2, RoundingMode.HALF_UP));
			natAmt = (natPercent.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(price).setScale(2, RoundingMode.HALF_UP));
			
			mTab.setValue("InternationalAmt", interAmt);
			mTab.setValue("NationalAmt", natAmt);			
			
		}
		
		//mTab.dataSave(true);
		
		return "";
	}	
	
	/***
	 * 
	 * OpenUp Ltda. Issue #
	 * @author Guillermo Brust - 04/02/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadDataTripProductAmt(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";
		
		int docID = Env.getContextAsInt (ctx, WindowNo, 0, "C_DocTypeTarget_ID");
		int prodID = 0;
		
		if(docID <= 0) return "";
		
		MDocType doc = new MDocType(ctx,docID,null); //obtengo tipo de documento
		
		if(doc.getValue()!=null && (doc.getValue().equalsIgnoreCase("fileprofinvoice") || doc.getValue().equalsIgnoreCase("freightinvoice"))
				|| doc.getValue().equalsIgnoreCase("et-ticket") || doc.getValue().equalsIgnoreCase("et-nc") 
				|| doc.getValue().equalsIgnoreCase("customerncflete")){
			
			if(mTab.getValue("M_Product_ID") != null) prodID = (Integer)mTab.getValue("M_Product_ID");
			BigDecimal lineNetAmt = (BigDecimal)mTab.getValue("LineNetAmt");
			
			if(lineNetAmt==null) lineNetAmt = Env.ZERO;
			
			mTab.setValue("LineNetAmt", Env.ZERO);
			//mTab.setValue("NationalPercentage", Env.ZERO);
			//mTab.setValue("InterPercentage", Env.ZERO);
			//mTab.setValue("NationalAmt", Env.ZERO);
			//mTab.setValue("InternationalAmt", Env.ZERO);
			mTab.setValue("LineTotal", Env.ZERO);
			
			if(prodID <= 0 || lineNetAmt.compareTo(Env.ZERO)<=0) return "";
			
			mTab.dataSave(true);
			
		}
				
		return "";
	}
	
	
	/***
	 * 
	 * OpenUp Ltda. Issue #
	 * @author Guillermo Brust - 12/02/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String fake(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
					
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";
		
		int docID = Env.getContextAsInt (ctx, WindowNo, 0, "C_DocTypeTarget_ID");
		int prodID = 0;
		
		if(docID <= 0) return "";
		
		MDocType doc = new MDocType(ctx,docID,null); //obtengo tipo de documento
		
		if(doc.getValue()!=null && (doc.getValue().equalsIgnoreCase("fileprofinvoice") || doc.getValue().equalsIgnoreCase("freightinvoice"))
				|| doc.getValue().equalsIgnoreCase("et-ticket") || doc.getValue().equalsIgnoreCase("et-nc") 
				|| doc.getValue().equalsIgnoreCase("customerncflete")){
			
			if(mTab.getValue("M_Product_ID") != null) prodID = (Integer)mTab.getValue("M_Product_ID");
			
			if(prodID <= 0) return "";
			
			mTab.dataSave(true);
			
			mTab.dataRefreshAll();
			
		}
		
		return "";
	}	
	
	/***
	 * 
	 * OpenUp Ltda. Issue #3169
	 * @author Nicolas Sarlabos - 31/10/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String fakeBudget(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {

		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";

		int prodID = 0;
		
		if(mTab.getValue("M_Product_ID") != null) prodID = (Integer)mTab.getValue("M_Product_ID");

		if(prodID <= 0) return "";

		mTab.dataSave(true);

		mTab.dataRefreshAll();
		
		return "";
	}	
	
	/***
	 * 
	 * OpenUp Ltda. Issue #1629
	 * @author Nicolas Sarlabos - 04/04/2014
	 * Metodo que carga los datos del camion original al ingresar la matricula en MIC/DTA.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadDataTruck(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) {

			mTab.setValue("originaltruck", null);
			mTab.setValue("rut1", null);
			mTab.setValue("uy_tr_mark_id", null); //seteo la marca del vehiculo
			mTab.setValue("weight", 0); //seteo capacidad de arrastre
			mTab.setValue("yearfrom", null); //seteo anio del vehiculo		

			return "";

		}

		int truckID = ((Integer)value).intValue();

		if (truckID == 0) return "";	
		
		MTRTruck truck = new MTRTruck(ctx, truckID, null); //instancio vehiculo
		
		String val = "", sql = "";

		if(truck != null && truck.get_ID() > 0){
			
			mTab.setValue("uy_tr_mark_id", truck.getUY_TR_Mark_ID()); //seteo la marca del vehiculo
			mTab.setValue("weight", truck.getArrastre()); //seteo capacidad de arrastre
			mTab.setValue("yearfrom", truck.getanio()); //seteo anio del vehiculo	
			
			//seteo datos del propietario del camion original
			if(truck.isOwn()){ //si el vehiculo es PROPIO
				
				MOrg org = MOrg.get(ctx, (Integer) mTab.getValue("ad_org_id")); //instancio organizacion
				MOrgInfo info = MOrgInfo.get(ctx, (Integer) mTab.getValue("ad_org_id"), null); //isntancio info de la organizacion
				
				if(org.getDescription()!=null && !org.getDescription().equalsIgnoreCase("")){
					
					val += org.getDescription().toUpperCase() + "\n";
					
					MLocation loc = new MLocation(ctx,info.getC_Location_ID(),null);
					
					if(loc!=null && loc.get_ID()>0){
						
						if(loc.getAddress1()!= null && !loc.getAddress1().equalsIgnoreCase("")){
							
							val += loc.getAddress1().toUpperCase() + "\n";
						}
						
						MCountry pais = new MCountry(ctx,loc.getC_Country_ID(),null);
						MDepartamentos depto = new MDepartamentos(ctx,loc.getUY_Departamentos_ID(),null);
												
						if((depto!=null && depto.get_ID()>0) && (pais!=null && pais.get_ID()>0)){
							
							val += depto.getName() + " " + pais.getName();							
							
						}						
						
					}
					
					if(!val.equalsIgnoreCase("")) mTab.setValue("originaltruck", val);
					
					if(info.getDUNS()!=null) mTab.setValue("rut1", val);				
					
				}				
				
			} else { //si el vehiculo NO es PROPIO
				
				//si el vehiculo no es propio, debo cargar los datos del socio de negocio TERCERO indicado en el vehiculo
				if(truck.getC_BPartner_ID() > 0){
					
					MBPartner partner = new MBPartner(ctx,truck.getC_BPartner_ID(),null);
					
					if(partner.getName2() != null && !partner.getName2().equalsIgnoreCase("")){
						
						val += partner.getName2().toUpperCase() + "\n";
						
					} else val += partner.getName().toUpperCase() + "\n";
					
					sql = "select loc.c_location_id" +
                          " from c_location loc" +
                          " inner join c_bpartner_location bloc on loc.c_location_id = bloc.c_location_id" +
                          " where bloc.isbillto = 'Y' and bloc.c_bpartner_id = " + partner.get_ID() +
                          " and bloc.isactive = 'Y'";
					int locID = DB.getSQLValueEx(null, sql);
					
					if(locID > 0){
						
						MLocation loc = new MLocation(ctx,locID,null);
						
						if(loc.getAddress1()!= null && !loc.getAddress1().equalsIgnoreCase("")){
							
							val += loc.getAddress1().toUpperCase() + "\n";
						}
						
						MCountry pais = new MCountry(ctx,loc.getC_Country_ID(),null);
						MDepartamentos depto = new MDepartamentos(ctx,loc.getUY_Departamentos_ID(),null);
						MLocalidades localidad = new MLocalidades(ctx,loc.getUY_Localidades_ID(),null);
						
						//si es Uruguay se muestra "departamento - pais", si no se muestra "localidad - departamento - pais"
						if(pais.get_ID()==336){
							
							if((depto!=null && depto.get_ID()>0) && (pais!=null && pais.get_ID()>0)){
								
								val += depto.getName() + " " + pais.getName();								
							}							
							
						} else {
							
							if((localidad!=null && localidad.get_ID()>0) && (depto!=null && depto.get_ID()>0) && (pais!=null && pais.get_ID()>0)){
								
								val += localidad.getName() + " " + depto.getName() + " " + pais.getName();								
							}							
							
						}					
						
					} else ADialog.info(0,null,"No se pudo obtener datos de localizacion para el propietario del vehiculo " + truck.getValue());
										
					if(!val.equalsIgnoreCase("")) mTab.setValue("originaltruck", val);
					
					if(partner.getDUNS()!=null) mTab.setValue("rut1", partner.getDUNS());						
					
				} else ADialog.info(0,null,"No se pudo obtener propietario del vehiculo " + truck.getValue());			
				
			}

		}
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #1629
	 * @author Nicolas Sarlabos - 03/04/2014
	 * Metodo que carga los datos del camion sustituto al ingresar la matricula en MIC/DTA.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadDataTruckSub(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) {
			
			mTab.setValue("substitutetruck", null);
			mTab.setValue("rut2", null);
			mTab.setValue("uy_tr_mark_id_1", null); //seteo la marca del vehiculo
			mTab.setValue("weight2", 0); //seteo capacidad de arrastre
			mTab.setValue("yearto", null); //seteo anio del vehiculo		
			
			return "";
			
		}
		
		int truckID = ((Integer)value).intValue();
		
		if (truckID == 0) return "";	
		
		MTRTruck truck = new MTRTruck(ctx, truckID, null); //instancio vehiculo
		
		String val = "", sql = "";

		if(truck != null && truck.get_ID() > 0){
			
			mTab.setValue("uy_tr_mark_id_1", truck.getUY_TR_Mark_ID()); //seteo la marca del vehiculo
			mTab.setValue("weight2", truck.getArrastre()); //seteo capacidad de arrastre
			mTab.setValue("yearto", truck.getanio()); //seteo anio del vehiculo	
			
			//seteo datos del propietario del camion sustituto
			if(truck.isOwn()){ //si el vehiculo es PROPIO
				
				MOrg org = MOrg.get(ctx, (Integer) mTab.getValue("ad_org_id")); //instancio organizacion
				MOrgInfo info = MOrgInfo.get(ctx, (Integer) mTab.getValue("ad_org_id"), null); //isntancio info de la organizacion
				
				if(org.getDescription()!=null && !org.getDescription().equalsIgnoreCase("")){
					
					val += org.getDescription().toUpperCase() + "\n";
					
					MLocation loc = new MLocation(ctx,info.getC_Location_ID(),null);
					
					if(loc!=null && loc.get_ID()>0){
						
						if(loc.getAddress1()!= null && !loc.getAddress1().equalsIgnoreCase("")){
							
							val += loc.getAddress1().toUpperCase() + "\n";
						}
						
						MCountry pais = new MCountry(ctx,loc.getC_Country_ID(),null);
						MDepartamentos depto = new MDepartamentos(ctx,loc.getUY_Departamentos_ID(),null);
												
						if((depto!=null && depto.get_ID()>0) && (pais!=null && pais.get_ID()>0)){
							
							val += depto.getName() + " " + pais.getName();							
							
						}						
						
					}
					
					if(!val.equalsIgnoreCase("")) mTab.setValue("substitutetruck", val);
					
					if(info.getDUNS()!=null) mTab.setValue("rut2", info.getDUNS());				
					
				}				
				
			} else { //si el vehiculo NO es PROPIO
				
				//si el vehiculo no es propio, debo cargar los datos del socio de negocio TERCERO indicado en el vehiculo
				if(truck.getC_BPartner_ID() > 0){
					
					MBPartner partner = new MBPartner(ctx,truck.getC_BPartner_ID(),null);
					
					if(partner.getName2() != null && !partner.getName2().equalsIgnoreCase("")){
						
						val += partner.getName2().toUpperCase() + "\n";
						
					} else val += partner.getName().toUpperCase() + "\n";
					
					sql = "select loc.c_location_id" +
                          " from c_location loc" +
                          " inner join c_bpartner_location bloc on loc.c_location_id = bloc.c_location_id" +
                          " where bloc.isbillto = 'Y' and bloc.c_bpartner_id = " + partner.get_ID() +
                          " and bloc.isactive = 'Y'";
					int locID = DB.getSQLValueEx(null, sql);
					
					if(locID > 0){
						
						MLocation loc = new MLocation(ctx,locID,null);
						
						if(loc.getAddress1()!= null && !loc.getAddress1().equalsIgnoreCase("")){
							
							val += loc.getAddress1().toUpperCase() + "\n";
						}
						
						MCountry pais = new MCountry(ctx,loc.getC_Country_ID(),null);
						MDepartamentos depto = new MDepartamentos(ctx,loc.getUY_Departamentos_ID(),null);
						MLocalidades localidad = new MLocalidades(ctx,loc.getUY_Localidades_ID(),null);
						
						//si es Uruguay se muestra "departamento - pais", si no se muestra "localidad - departamento - pais"
						if(pais.get_ID()==336){
							
							if((depto!=null && depto.get_ID()>0) && (pais!=null && pais.get_ID()>0)){
								
								val += depto.getName() + " " + pais.getName();								
							}							
							
						} else {
							
							if((localidad!=null && localidad.get_ID()>0) && (depto!=null && depto.get_ID()>0) && (pais!=null && pais.get_ID()>0)){
								
								val += localidad.getName() + " " + depto.getName() + " " + pais.getName();								
							}							
							
						}					
						
					} else ADialog.info(0,null,"No se pudo obtener datos de localizacion para el propietario del vehiculo " + truck.getValue());
										
					if(!val.equalsIgnoreCase("")) mTab.setValue("substitutetruck", val);
					
					if(partner.getDUNS()!=null) mTab.setValue("rut2", partner.getDUNS());						
					
				} else ADialog.info(0,null,"No se pudo obtener propietario del vehiculo " + truck.getValue());			
				
			}

		} 
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #1629
	 * @author Nicolas Sarlabos - 04/04/2014
	 * Metodo que carga los datos del remolque o semirremolque original al ingresar la matricula en MIC/DTA.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadDataTrailer(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) {
			
			mTab.setValue("isremolque", false);
			mTab.setValue("issemiremolque", false);
			
			return "";
		}
		
		int truckID = ((Integer)value).intValue();
		
		if (truckID == 0) return "";	
		
		MTRTruck truck = new MTRTruck(ctx, truckID, null); //instancio el remolque o semi
		
		if(truck != null && truck.get_ID() > 0){
			
			//obtengo grupo de tipo de vehiculo
			String sql = "select tt.truckgroup" +
                  " from uy_tr_truck t" +
                  " inner join uy_tr_trucktype tt on t.uy_tr_trucktype_id = tt.uy_tr_trucktype_id" +
                  " where t.uy_tr_truck_id = " + truck.get_ID();
			String group = DB.getSQLValueStringEx(null, sql);
			
			if(group!=null && !group.equalsIgnoreCase("")){
				
				if(group.equalsIgnoreCase("REMOLQUE")){
					mTab.setValue("isremolque", true);
				}else if(group.equalsIgnoreCase("SEMIRREMOLQUE")) mTab.setValue("issemiremolque", true);
			}						
			
		}
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #1629
	 * @author Nicolas Sarlabos - 03/04/2014
	 * Metodo que carga los datos del remolque o semirremolque sustituto al ingresar la matricula en MIC/DTA.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadDataTrailerSub(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) {
			
			mTab.setValue("isremolque2", false);
			mTab.setValue("issemiremolque2", false);
			
			return "";
		}
		
		int truckID = ((Integer)value).intValue();
		
		if (truckID == 0) return "";	
		
		MTRTruck truck = new MTRTruck(ctx, truckID, null); //instancio el remolque o semi
		
		if(truck != null && truck.get_ID() > 0){
			
			//obtengo grupo de tipo de vehiculo
			String sql = "select tt.truckgroup" +
                  " from uy_tr_truck t" +
                  " inner join uy_tr_trucktype tt on t.uy_tr_trucktype_id = tt.uy_tr_trucktype_id" +
                  " where t.uy_tr_truck_id = " + truck.get_ID();
			String group = DB.getSQLValueStringEx(null, sql);
			
			if(group!=null && !group.equalsIgnoreCase("")){
				
				if(group.equalsIgnoreCase("REMOLQUE")){
					mTab.setValue("isremolque2", true);
				}else if(group.equalsIgnoreCase("SEMIRREMOLQUE")) mTab.setValue("issemiremolque2", true);
			}						
			
		}
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #3294
	 * @author Nicolas Sarlabos - 21/11/2014
	 * Metodo que setea las ciudades origen y destino en cabezal de emision MIC/DTA, si se selecciona una OT en lastre.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromLastreMic(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int orderID = ((Integer)value).intValue();
		
		if (orderID == 0) return "";	
		
		MTRTransOrder order = new MTRTransOrder(ctx, orderID, null); //instancio la OT
		
		if(order != null && order.get_ID() > 0){
			
			mTab.setValue("UY_Ciudad_ID", order.getUY_Ciudad_ID());
			mTab.setValue("UY_Ciudad_ID_1", order.getUY_Ciudad_ID_1());			
		}
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #2057
	 * @author Nicolas Sarlabos - 23/04/2014
	 * Metodo que realiza prorrateo y setea peso bruto y neto, al modificarse la cantidad de bultos en cabezal de proforma.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	/*public String setWeight(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		MInvoice hdr = null;

		BigDecimal bultosActual = (BigDecimal)value;
		BigDecimal bultosAnterior = Env.ZERO;

		int invoiceID = Env.getContextAsInt (ctx, WindowNo, 0, "C_Invoice_ID"); 

		if(invoiceID > 0){

			hdr = new MInvoice(ctx,invoiceID,null);
			bultosAnterior = hdr.getuy_cantbultos();

			if(bultosAnterior.compareTo(Env.ZERO)>0){

				BigDecimal pesoBruto = (BigDecimal)mTab.getValue("weight");
				BigDecimal pesoNeto = (BigDecimal)mTab.getValue("weight2");
				BigDecimal amtOriginal = (BigDecimal)mTab.getValue("amtoriginal");

				pesoBruto = (pesoBruto.multiply(bultosActual)).divide(bultosAnterior, 2, RoundingMode.HALF_UP);
				pesoNeto = (pesoNeto.multiply(bultosActual)).divide(bultosAnterior, 2, RoundingMode.HALF_UP);
				amtOriginal = (amtOriginal.multiply(bultosActual)).divide(bultosAnterior, 2, RoundingMode.HALF_UP);

				mTab.setValue("weight", pesoBruto);
				mTab.setValue("weight2", pesoNeto);
				mTab.setValue("amtoriginal", amtOriginal);

			}

		}
		
		return "";
	}*/
	
	/***
	 * 
	 * OpenUp Ltda. Issue #2034
	 * @author Nicolas Sarlabos - 23/04/2014
	 * Metodo que setea campos de la Proforma desde el Expediente elegido.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setProformaFromFile(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int docID = Env.getContextAsInt (ctx, WindowNo, 0, "C_DocTypeTarget_ID");
		
		if(docID <= 0) return "";
		
		MDocType doc = new MDocType(ctx,docID,null); //obtengo tipo de documento		
		
		if(doc.getValue()!=null && (doc.getValue().equalsIgnoreCase("fileprofinvoice"))){
			
			int tripID = ((Integer)value).intValue();
			
			if (tripID == 0) return "";
			
			MTRTrip trip = new MTRTrip(ctx,tripID,null);
			
			if(trip != null && trip.get_ID() > 0){
				
				/*String sql = "SELECT COALESCE(sum(inv.uy_cantbultos), 0)" +
						" FROM c_invoice inv" +
						" WHERE uy_tr_trip_id = " + trip.get_ID() +
						" AND docstatus <> 'VO'";

				BigDecimal qtyUsed = DB.getSQLValueBDEx(null, sql);

				if(qtyUsed==null) qtyUsed = Env.ZERO;

				BigDecimal qtyDisponible = trip.getQtyPackage().subtract(qtyUsed); //obtengo bultos disponibles*/
				
				mTab.setValue("referenceno", trip.getReferenceNo());
				mTab.setValue("uy_tr_packagetype_id", trip.getUY_TR_PackageType_ID());
				mTab.setValue("incoterms", trip.getIncotermType());
				mTab.setValue("c_currency2_id", trip.getC_Currency_ID());
				mTab.setValue("uy_cantbultos", trip.getQtyPackage());
				mTab.setValue("amtoriginal", trip.getProductAmt());	
				mTab.setValue("weight", trip.getWeight());
				mTab.setValue("weight2", trip.getWeight2());
				
				if(trip.getUY_TR_Budget_ID()>0){
					
					MTRBudget budget = (MTRBudget)trip.getUY_TR_Budget();
					
					mTab.setValue("c_currency_id", budget.getC_Currency_ID());
					mTab.setValue("c_paymentterm_id", budget.getC_PaymentTerm_ID());		
					
				}
								
				int partnerID = Env.getContextAsInt (ctx, WindowNo, 0, "C_BPartner_ID");
				
				if(partnerID == trip.getC_BPartner_ID_From()){
					
					mTab.setValue("C_BPartner_ID_P", trip.getC_BPartner_ID_To());
					
				} else if(partnerID == trip.getC_BPartner_ID_To()) mTab.setValue("C_BPartner_ID_P", trip.getC_BPartner_ID_From());  
				
				//seteo direccion del cliente
				if((Integer)mTab.getValue("C_BPartner_ID")==trip.getC_BPartner_ID_To()){
											
					mTab.setValue("C_BPartner_Location_ID", trip.getC_BPartner_Location_ID_2());
					
				} else if ((Integer)mTab.getValue("C_BPartner_ID")==trip.getC_BPartner_ID_From()){
					
					mTab.setValue("C_BPartner_Location_ID", trip.getC_BPartner_Location_ID());
					
				}
			}				
			
		}
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #1620
	 * @author Nicolas Sarlabos - 14/05/2014
	 * Metodo que setea el nombre y descripcion de la falla al seleccionar un numero de gestion de falla
	 * en la linea de fallas autorizadas, en una orden de servicio.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFailureServiceOrder(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int faultManageID = ((Integer)value).intValue();
		
		if (faultManageID == 0) return "";	
		
		MTRFaultManage fm = new MTRFaultManage(ctx, faultManageID, null); //instancio la gestion de falla
		
		if(fm != null && fm.get_ID() > 0) {
			
			mTab.setValue("UY_TR_Failure_ID", fm.getUY_TR_Failure_ID());
			mTab.setValue("Description", fm.getDescription());
		}
					
		return "";
	}
	
	/***
	 * En Gestion de Carga seteo conductor primario al seleccionar el tractor. 
	 * OpenUp Ltda. Issue #1625
	 * @author Nicolas Sarlabos - 28/05/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setDriverLoadManage(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int truckID = ((Integer)value).intValue();
		
		if (truckID == 0) return "";
		
		MTRTruck truck = new MTRTruck(ctx, truckID, null);
		
		if (truck.get_ID() <= 0) return "";
		
		if(truck.getUY_TR_Driver_ID() > 0) {
			
			mTab.setValue(X_UY_TR_LoadManage.COLUMNNAME_UY_TR_Driver_ID, truck.getUY_TR_Driver_ID());
		
		} else mTab.setValue(X_UY_TR_LoadManage.COLUMNNAME_UY_TR_Driver_ID, null);
		
		return "";
	}

	/***
	 * 
	 * OpenUp Ltda. Issue #2222
	 * @author Nicolas Sarlabos - 30/05/2014
	 * Metodo que realiza prorrateo y setea peso bruto, importe de flete y de mercaderia al modificarse la cantidad de bultos en cabezal de MIC/DTA.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromQtyPackMicHdr(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		MTRMic hdr = null;
		BigDecimal bultosActual = (BigDecimal)value;
		BigDecimal bultosAnterior = Env.ZERO;
		
		int micHdrID = Env.getContextAsInt (ctx, WindowNo, 0, "UY_TR_Mic_ID"); 
		
		if(micHdrID > 0){
			
			hdr = new MTRMic(ctx,micHdrID,null);
			bultosAnterior = hdr.getQtyPackage();
			
			BigDecimal pesoBruto = (BigDecimal)mTab.getValue("pesoBruto");
			BigDecimal importeFlete = (BigDecimal)mTab.getValue("amount");
			BigDecimal valorFot = (BigDecimal)mTab.getValue("importe");
			
			if(bultosAnterior.compareTo(Env.ZERO) > 0){
				
				pesoBruto = (pesoBruto.multiply(bultosActual)).divide(bultosAnterior, 3, RoundingMode.HALF_UP);
				importeFlete = (importeFlete.multiply(bultosActual)).divide(bultosAnterior, 2, RoundingMode.HALF_UP);
				valorFot = (valorFot.multiply(bultosActual)).divide(bultosAnterior, 3, RoundingMode.HALF_UP);
				
				mTab.setValue("pesoBruto", pesoBruto);
				mTab.setValue("amount", importeFlete);
				mTab.setValue("importe", valorFot);
			}	
			
		}
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #2222
	 * @author Nicolas Sarlabos - 30/05/2014
	 * Metodo que realiza prorrateo y setea peso bruto, importe de flete y de mercaderia al modificarse la cantidad de bultos en continuacion de MIC/DTA.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromQtyPackMicCont(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		MTRMicCont cont = null;
		BigDecimal bultosActual = (BigDecimal)value;
		BigDecimal bultosAnterior = Env.ZERO;
		
		int micContID = Env.getContextAsInt (ctx, WindowNo, 0, "UY_TR_MicCont_ID"); 
		
		if(micContID > 0){
			
			cont = new MTRMicCont(ctx,micContID,null);
			
			if(mField.getColumnName().equalsIgnoreCase("QtyPackage")){				
			
				bultosAnterior = cont.getQtyPackage();
				
				BigDecimal pesoBruto = (BigDecimal)mTab.getValue("pesoBruto");
				BigDecimal importeFlete = (BigDecimal)mTab.getValue("amount");
				BigDecimal valorFot = (BigDecimal)mTab.getValue("importe");
				
				if(bultosAnterior.compareTo(Env.ZERO) > 0){
					
					pesoBruto = (pesoBruto.multiply(bultosActual)).divide(bultosAnterior, 3, RoundingMode.HALF_UP);
					importeFlete = (importeFlete.multiply(bultosActual)).divide(bultosAnterior, 2, RoundingMode.HALF_UP);
					valorFot = (valorFot.multiply(bultosActual)).divide(bultosAnterior, 3, RoundingMode.HALF_UP);
					
					mTab.setValue("pesoBruto", pesoBruto);
					mTab.setValue("amount", importeFlete);
					mTab.setValue("importe", valorFot);						
				}				
				
			} else if(mField.getColumnName().equalsIgnoreCase("QtyPackage2")){
				
				bultosAnterior = cont.getQtyPackage2();
				
				BigDecimal pesoBruto = (BigDecimal)mTab.getValue("pesoBruto2");
				BigDecimal importeFlete = (BigDecimal)mTab.getValue("amount2");
				BigDecimal valorFot = (BigDecimal)mTab.getValue("importe2");
				
				if(bultosAnterior.compareTo(Env.ZERO) > 0){
					
					pesoBruto = (pesoBruto.multiply(bultosActual)).divide(bultosAnterior, 3, RoundingMode.HALF_UP);
					importeFlete = (importeFlete.multiply(bultosActual)).divide(bultosAnterior, 2, RoundingMode.HALF_UP);
					valorFot = (valorFot.multiply(bultosActual)).divide(bultosAnterior, 3, RoundingMode.HALF_UP);
					
					mTab.setValue("pesoBruto2", pesoBruto);
					mTab.setValue("amount2", importeFlete);
					mTab.setValue("importe2", valorFot);					
				}				
			}		
		}
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #2870
	 * @author Nicolas Sarlabos - 05/09/2014
	 * Metodo que realiza prorrateos en la linea de la OT.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromTransOLine(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value, Object oldValue) {
		
		if (isCalloutActive() ||  value == null) return "";

		BigDecimal factorActual = (BigDecimal)value;
		BigDecimal factorAnterior = (BigDecimal)oldValue;
				
		if (factorAnterior == null) factorAnterior = factorActual;

		if(factorAnterior.compareTo(Env.ZERO) > 0){

			BigDecimal pesoBruto = (BigDecimal)mTab.getValue(X_UY_TR_TransOrderLine.COLUMNNAME_Weight);
			BigDecimal pesoNeto = (BigDecimal)mTab.getValue(X_UY_TR_TransOrderLine.COLUMNNAME_Weight2);
			BigDecimal importe = (BigDecimal)mTab.getValue(X_UY_TR_TransOrderLine.COLUMNNAME_ProductAmt);
			BigDecimal volume = (BigDecimal)mTab.getValue(X_UY_TR_TransOrderLine.COLUMNNAME_Volume);
			BigDecimal amount = (BigDecimal)mTab.getValue(X_UY_TR_TransOrderLine.COLUMNNAME_Amount);
			BigDecimal bultos = (BigDecimal)mTab.getValue(X_UY_TR_TransOrderLine.COLUMNNAME_QtyPackage);

			if (pesoBruto == null) pesoBruto = Env.ZERO;
			if (pesoNeto == null) pesoNeto = Env.ZERO;
			if (importe == null) importe = Env.ZERO;
			if (volume == null) volume = Env.ZERO;
			if (amount == null) amount = Env.ZERO;
			if (bultos == null) bultos = Env.ZERO;

			
			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_TransOrderLine.COLUMNNAME_Weight)){
				pesoBruto = (pesoBruto.multiply(factorActual)).divide(factorAnterior, 3, RoundingMode.HALF_UP);
				mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Weight, pesoBruto);
			}

			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_TransOrderLine.COLUMNNAME_Weight2)){
				pesoNeto = (pesoNeto.multiply(factorActual)).divide(factorAnterior, 3, RoundingMode.HALF_UP);				
				mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Weight2, pesoNeto);
			}

			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_TransOrderLine.COLUMNNAME_ProductAmt)){
				importe = (importe.multiply(factorActual)).divide(factorAnterior, 3, RoundingMode.HALF_UP);
				mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_ProductAmt, importe);
			}

			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_TransOrderLine.COLUMNNAME_Volume)){
				volume = (volume.multiply(factorActual)).divide(factorAnterior, 3, RoundingMode.HALF_UP);
				mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Volume, volume);				
			}
			
			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_TransOrderLine.COLUMNNAME_Amount)){
				amount = (amount.multiply(factorActual)).divide(factorAnterior, 2, RoundingMode.HALF_UP);
				mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_Amount, amount);				
			}
			
			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_TransOrderLine.COLUMNNAME_QtyPackage)){
				bultos = (bultos.multiply(factorActual)).divide(factorAnterior, 2, RoundingMode.HALF_UP);
				mTab.setValue(X_UY_TR_TransOrderLine.COLUMNNAME_QtyPackage, bultos);
			}

		}	
		
		return "";
	}

	
	/***
	 * Prorrateo de valores en CRT de movimiento de carga. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 21/12/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String setLoadMonitorLineProrrateo(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value, Object oldValue) {
		
		if (isCalloutActive() ||  value == null) return "";
		
		if (!mTab.getValueAsBoolean(X_UY_TR_LoadMonitorLine.COLUMNNAME_Prorrateo)){
			return "";
		}
		
		BigDecimal factorActual = (BigDecimal)value;
		BigDecimal factorAnterior = (BigDecimal)oldValue;
				
		if (factorAnterior == null) factorAnterior = factorActual;

		if(factorAnterior.compareTo(Env.ZERO) > 0){

			BigDecimal pesoBruto = (BigDecimal)mTab.getValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Weight);
			BigDecimal pesoNeto = (BigDecimal)mTab.getValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Weight2);
			BigDecimal importe = (BigDecimal)mTab.getValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_ProductAmt);
			BigDecimal volume = (BigDecimal)mTab.getValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Volume);
			BigDecimal amount = (BigDecimal)mTab.getValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Amount);
			BigDecimal bultos = (BigDecimal)mTab.getValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_QtyPackage);

			if (pesoBruto == null) pesoBruto = Env.ZERO;
			if (pesoNeto == null) pesoNeto = Env.ZERO;
			if (importe == null) importe = Env.ZERO;
			if (volume == null) volume = Env.ZERO;
			if (amount == null) amount = Env.ZERO;
			if (bultos == null) bultos = Env.ZERO;

			
			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_LoadMonitorLine.COLUMNNAME_Weight)){
				pesoBruto = (pesoBruto.multiply(factorActual)).divide(factorAnterior, 3, RoundingMode.HALF_UP);
				mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Weight, pesoBruto);
			}

			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_LoadMonitorLine.COLUMNNAME_Weight2)){
				pesoNeto = (pesoNeto.multiply(factorActual)).divide(factorAnterior, 3, RoundingMode.HALF_UP);				
				mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Weight2, pesoNeto);
			}

			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_LoadMonitorLine.COLUMNNAME_ProductAmt)){
				importe = (importe.multiply(factorActual)).divide(factorAnterior, 3, RoundingMode.HALF_UP);
				mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_ProductAmt, importe);
			}

			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_LoadMonitorLine.COLUMNNAME_Volume)){
				volume = (volume.multiply(factorActual)).divide(factorAnterior, 3, RoundingMode.HALF_UP);
				mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Volume, volume);				
			}
			
			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_LoadMonitorLine.COLUMNNAME_Amount)){
				amount = (amount.multiply(factorActual)).divide(factorAnterior, 2, RoundingMode.HALF_UP);
				mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Amount, amount);				
			}
			
			if (!mField.getColumnName().equalsIgnoreCase(X_UY_TR_LoadMonitorLine.COLUMNNAME_QtyPackage)){
				bultos = (bultos.multiply(factorActual)).divide(factorAnterior, 2, RoundingMode.HALF_UP);
				mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_QtyPackage, bultos);
			}

		}	
		
		return "";
	}

	
	/***
	 * 
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 18/11/2014
	 * Metodo que realiza prorrateos en la proforma.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setQtyProform(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value, Object oldValue) {
		
		if (isCalloutActive() ||  value == null) return "";
		
		int docID = Env.getContextAsInt (ctx, WindowNo, 0, "C_DocTypeTarget_ID");
		
		if(docID <= 0) return "";
		
		MDocType doc = new MDocType(ctx,docID,null); //obtengo tipo de documento		
		
		if(doc.getValue()!=null && (doc.getValue().equalsIgnoreCase("fileprofinvoice"))){
			
			BigDecimal factorActual = (BigDecimal)value;
			BigDecimal factorAnterior = (BigDecimal)oldValue;
					
			if (factorAnterior == null) factorAnterior = factorActual;

			if(factorAnterior.compareTo(Env.ZERO) > 0){

				BigDecimal pesoBruto = (BigDecimal)mTab.getValue("Weight");
				BigDecimal pesoNeto = (BigDecimal)mTab.getValue("Weight2");
				BigDecimal importe = (BigDecimal)mTab.getValue("AmtOriginal");
				BigDecimal bultos = (BigDecimal)mTab.getValue("UY_CantBultos");

				if (pesoBruto == null) pesoBruto = Env.ZERO;
				if (pesoNeto == null) pesoNeto = Env.ZERO;
				if (importe == null) importe = Env.ZERO;
				if (bultos == null) bultos = Env.ZERO;

				
				if (!mField.getColumnName().equalsIgnoreCase("Weight")){
					pesoBruto = (pesoBruto.multiply(factorActual)).divide(factorAnterior, 3, RoundingMode.HALF_UP);
					mTab.setValue("Weight", pesoBruto);
				}

				if (!mField.getColumnName().equalsIgnoreCase("Weight2")){
					pesoNeto = (pesoNeto.multiply(factorActual)).divide(factorAnterior, 3, RoundingMode.HALF_UP);				
					mTab.setValue("Weight2", pesoNeto);
				}

				if (!mField.getColumnName().equalsIgnoreCase("AmtOriginal")){
					importe = (importe.multiply(factorActual)).divide(factorAnterior, 3, RoundingMode.HALF_UP);
					mTab.setValue("AmtOriginal", importe);
				}
				
				if (!mField.getColumnName().equalsIgnoreCase("UY_CantBultos")){
					bultos = (bultos.multiply(factorActual)).divide(factorAnterior, 2, RoundingMode.HALF_UP);
					mTab.setValue("UY_CantBultos", bultos);
				}

			}	
			
		}	
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda.
	 * @author Nicolas Sarlabos - 13/11/2014
	 * Metodo que setea el remolque asociado al tractor elegido en una OT en lastre.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromTruckOT(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int truckID = ((Integer)value).intValue();
		
		if(truckID <= 0) return "";
		
		MTRTruck truck = new MTRTruck(ctx,truckID,null);
		
		if(truck.getUY_TR_Truck_ID_2() > 0) mTab.setValue("Remolque_ID", truck.getUY_TR_Truck_ID_2());
		
		return "";
	}

	
	/***
	 * Al seleccionar vehiculo en orden de transporte, se carga info asociada.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 08/12/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setOTTruckInfo(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int truckID = ((Integer)value).intValue();
		
		if(truckID <= 0) return "";
		
		MTRTruck truck = new MTRTruck(ctx, truckID, null);
		MTRTruckType truckType = (MTRTruckType)truck.getUY_TR_TruckType();
		
		mTab.setValue(X_UY_TR_TruckType.COLUMNNAME_IsAssociated, truckType.isAssociated());
		mTab.setValue(X_UY_TR_TruckType.COLUMNNAME_UY_TR_TruckType_ID, truckType.get_ID());
		mTab.setValue(X_UY_TR_TransOrder.COLUMNNAME_IsOwn, truck.isOwn());
		
		// Si este vehiculo tiene asociacion, la muestro y dejo cambiarla
		if (truck.getUY_TR_Truck_ID_2() > 0){
			
			MTRTruck associatedTruck = new MTRTruck(ctx, truck.getUY_TR_Truck_ID_2(), null);
			MTRTruckType associatedType = (MTRTruckType)associatedTruck.getUY_TR_TruckType();
			
			mTab.setValue(X_UY_TR_TransOrder.COLUMNNAME_UY_TR_Truck_ID_Aux, truck.getUY_TR_Truck_ID_2());
			mTab.setValue(X_UY_TR_TransOrder.COLUMNNAME_UY_TR_TruckType_ID_2, associatedType.get_ID());
		}
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #2352
	 * @author Nicolas Sarlabos - 27/07/2014
	 * Metodo que setea el estado de la ciudad en la ventana de emision MIC/DTA, para el envio a aduana.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setState(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int cityID = ((Integer)value).intValue();
		
		if (cityID <= 0) return "";	
		
		MCiudad city = new MCiudad(ctx,cityID,null);
			
		String sql = "select coalesce(d.uy_departamentos_id,0)" +
                     " from uy_departamentos d" +
                     " inner join uy_localidades l on d.uy_departamentos_id = l.uy_departamentos_id" +
                     " where lower (l.name) like '" + city.getName().toLowerCase() + "'";
		
		int stateID = DB.getSQLValueEx(null, sql);
		
		if(mField.getColumnName().equalsIgnoreCase("uy_ciudad_id_2")){
			
			mTab.setValue("uy_departamentos_id", null);
			if(stateID > 0) mTab.setValue("uy_departamentos_id", stateID);

		} else if (mField.getColumnName().equalsIgnoreCase("uy_ciudad_id_3")){
			
			mTab.setValue("uy_departamentos_id_1", null);
			if(stateID > 0) mTab.setValue("uy_departamentos_id_1", stateID);				
		}		
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #2949
	 * @author Nicolas Sarlabos - 18/09/2014
	 * Metodo que setea las direcciones fiscales de los clientes en el expediente.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setBPLocationTrip(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int partnerID = ((Integer)value).intValue();
		int locationID = 0;
		
		String sql = "select c_bpartner_location_id" +
			      " from c_bpartner_location" +
				  " where isbillto = 'Y' and c_bpartner_id = " + partnerID;
		locationID = DB.getSQLValueEx(null, sql);
			
		if(locationID > 0){
			
			if(mField.getColumnName().equalsIgnoreCase("C_BPartner_ID_From")){						
				
				if(locationID > 0) mTab.setValue("c_bpartner_location_ID", locationID);		
				
			} else if(mField.getColumnName().equalsIgnoreCase("C_BPartner_ID_To")) if(locationID > 0) mTab.setValue("c_bpartner_location_ID_2", locationID);			
			
		}		
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #3170
	 * @author Nicolas Sarlabos - 09/12/2014
	 * Metodo que setea el despachante del cliente seleccionado en el expediente.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setBPDespachanteTrip(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int partnerID = ((Integer)value).intValue();
		int count = 0;
		int despachanteID = 0;
		
		String sql = "select coalesce(count(c_bp_despachante_id),0)" +
			      " from c_bp_despachante" +
				  " where c_bpartner_id = " + partnerID;
		count = DB.getSQLValueEx(null, sql);
		
		if(count==1){
			
			sql = "select uy_tr_despachante_id" +
				      " from c_bp_despachante" +
					  " where c_bpartner_id = " + partnerID;
			despachanteID = DB.getSQLValueEx(null, sql);
			
			if(mField.getColumnName().equalsIgnoreCase("C_BPartner_ID_From")){						
				
				mTab.setValue("uy_tr_despachante_id", despachanteID);		
				
			} else if(mField.getColumnName().equalsIgnoreCase("C_BPartner_ID_To")) mTab.setValue("uy_tr_despachante_id_1", despachanteID);		
		}		
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #3169.
	 * @author Nicolas Sarlabos - 31/10/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromBudgetPrice(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {

		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";
		
		int prodID = 0;

		if(mTab.getValue("M_Product_ID") != null) prodID = (Integer)mTab.getValue("M_Product_ID");
		BigDecimal lineNetAmt = (BigDecimal)mTab.getValue("LineNetAmt");

		if(lineNetAmt==null) lineNetAmt = Env.ZERO;
		
		if(prodID <= 0 || lineNetAmt.compareTo(Env.ZERO)<=0) return "";

		mTab.setValue("LineNetAmt", Env.ZERO);
		mTab.setValue("LineTotal", Env.ZERO);
		//mTab.setValue("NationalPercentage", Env.ZERO);
		//mTab.setValue("InterPercentage", Env.ZERO);
		//mTab.setValue("NationalAmt", Env.ZERO);
		//mTab.setValue("InternationalAmt", Env.ZERO);
		
		BigDecimal price = (BigDecimal)mTab.getValue("PriceEntered");
		
		if(price==null) price = Env.ZERO;
		
		if(price.compareTo(Env.ZERO)>0){
			
			BigDecimal interAmt = Env.ZERO;
			BigDecimal natAmt = Env.ZERO;
			BigDecimal interPercent = (BigDecimal)mTab.getValue("InterPercentage");
			BigDecimal natPercent = (BigDecimal)mTab.getValue("NationalPercentage");
			
			if(interPercent==null) interPercent = Env.ZERO;
			if(natPercent==null) natPercent = Env.ZERO;			
			
			interAmt = (interPercent.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(price).setScale(2, RoundingMode.HALF_UP));
			natAmt = (natPercent.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(price).setScale(2, RoundingMode.HALF_UP));
			
			mTab.setValue("InternationalAmt", interAmt);
			mTab.setValue("NationalAmt", natAmt);			
			
		}		

		mTab.dataSave(true);
				
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #3169.
	 * @author Nicolas Sarlabos - 31/10/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String amtBudget (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";

		//	log.log(Level.WARNING,"amt - init");
		int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
		int StdPrecision = MPriceList.getStandardPrecision(ctx, M_PriceList_ID);
		BigDecimal QtyEntered, QtyInvoiced, PriceEntered, PriceActual, PriceLimit, Discount, PriceList;
		//	get values
		QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
		QtyInvoiced = (BigDecimal)mTab.getValue("QtyInvoiced");
		log.fine("QtyEntered=" + QtyEntered + ", Invoiced=" + QtyInvoiced + ", UOM=" + C_UOM_To_ID);
		//

		PriceEntered = ((BigDecimal)mTab.getValue("PriceEntered"));
		PriceActual = (BigDecimal)mTab.getValue("PriceActual");

		//	Discount = (BigDecimal)mTab.getValue("Discount");
		PriceLimit = (BigDecimal)mTab.getValue("PriceLimit");
		PriceList = (BigDecimal)mTab.getValue("PriceList");
		log.fine("PriceList=" + PriceList + ", Limit=" + PriceLimit + ", Precision=" + StdPrecision);
		log.fine("PriceEntered=" + PriceEntered + ", Actual=" + PriceActual);// + ", Discount=" + Discount);

		//		No Product
		if ( M_Product_ID == 0 )
		{
			// if price change sync price actual and entered
			// else ignore
			if (mField.getColumnName().equals("PriceActual"))
			{
				PriceEntered = (BigDecimal) value;
				mTab.setValue("PriceEntered", value);
			}
			else if (mField.getColumnName().equals("PriceEntered"))
			{
				PriceActual = (BigDecimal) value;
				mTab.setValue("PriceActual", value);
			}
		}
		//	Product Qty changed - recalc price
		else if ((mField.getColumnName().equals("QtyInvoiced") 
				|| mField.getColumnName().equals("QtyEntered")
				|| mField.getColumnName().equals("C_UOM_ID")
				|| mField.getColumnName().equals("M_Product_ID")) 
				&& !"N".equals(Env.getContext(ctx, WindowNo, "DiscountSchema")))
		{
			int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
			if (mField.getColumnName().equals("QtyEntered"))
				QtyInvoiced = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
						C_UOM_To_ID, QtyEntered);
			if (QtyInvoiced == null)
				QtyInvoiced = QtyEntered;
			boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
			MProductPricing pp = new MProductPricing (M_Product_ID, C_BPartner_ID, QtyInvoiced, IsSOTrx);
			pp.setM_PriceList_ID(M_PriceList_ID);
			int	M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
			pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
			Timestamp date = (Timestamp)mTab.getValue("DateInvoiced");
			pp.setPriceDate(date);
			//
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
					C_UOM_To_ID, pp.getPriceStd());
			if (PriceEntered == null)
				PriceEntered = pp.getPriceStd();
			//
			log.fine("amt - QtyChanged -> PriceActual=" + pp.getPriceStd() 
					+ ", PriceEntered=" + PriceEntered + ", Discount=" + pp.getDiscount());

			PriceActual = pp.getPriceStd();
			mTab.setValue("PriceActual", pp.getPriceStd());
			//	mTab.setValue("Discount", pp.getDiscount());
			mTab.setValue("PriceEntered", PriceEntered);
			Env.setContext(ctx, WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
		}
		else if (mField.getColumnName().equals("PriceActual"))
		{
			PriceActual = (BigDecimal)value;
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
					C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual;
			//
			log.fine("amt - PriceActual=" + PriceActual 
					+ " -> PriceEntered=" + PriceEntered);
			mTab.setValue("PriceEntered", PriceEntered);
		}
		else if (mField.getColumnName().equals("PriceEntered"))
		{
			PriceEntered = (BigDecimal)value;
			PriceActual = MUOMConversion.convertProductTo (ctx, M_Product_ID, 
					C_UOM_To_ID, PriceEntered);
			if (PriceActual == null)
				PriceActual = PriceEntered;
			//
			log.fine("amt - PriceEntered=" + PriceEntered 
					+ " -> PriceActual=" + PriceActual);
			mTab.setValue("PriceActual", PriceActual);
		}

		//	Check PriceLimit
		String epl = Env.getContext(ctx, WindowNo, "EnforcePriceLimit");
		boolean enforce = Env.isSOTrx(ctx, WindowNo) && epl != null && epl.equals("Y");
		if (enforce && MRole.getDefault().isOverwritePriceLimit())
			enforce = false;
		//	Check Price Limit?
		if (enforce && PriceLimit.doubleValue() != 0.0
				&& PriceActual.compareTo(PriceLimit) < 0)
		{
			PriceActual = PriceLimit;
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
					C_UOM_To_ID, PriceLimit);
			if (PriceEntered == null)
				PriceEntered = PriceLimit;
			log.fine("amt =(under) PriceEntered=" + PriceEntered + ", Actual" + PriceLimit);
			mTab.setValue ("PriceActual", PriceLimit);
			mTab.setValue ("PriceEntered", PriceEntered);
			mTab.fireDataStatusEEvent ("UnderLimitPrice", "", false);
			//	Repeat Discount calc
			if (PriceList.intValue() != 0)
			{
				Discount = new BigDecimal ((PriceList.doubleValue () - PriceActual.doubleValue ()) / PriceList.doubleValue () * 100.0);
				if (Discount.scale () > 2)
					Discount = Discount.setScale (2, BigDecimal.ROUND_HALF_UP);
				//	mTab.setValue ("Discount", Discount);
			}
		}

		//	Line Net Amt
		BigDecimal LineNetAmt = QtyInvoiced.multiply(PriceActual);
		if (LineNetAmt.scale() > StdPrecision)
			LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		log.info("amt = LineNetAmt=" + LineNetAmt);
		mTab.setValue("LineNetAmt", LineNetAmt);

		BigDecimal amount = LineNetAmt;

		BigDecimal TaxAmt = Env.ZERO;
		if (mField.getColumnName().equals("TaxAmt"))
		{
			TaxAmt = (BigDecimal)mTab.getValue("TaxAmt");
		}
		else
		{
			Integer taxID = (Integer)mTab.getValue("C_Tax_ID");
			if (taxID != null)
			{


				MTRBudget budget = new MTRBudget(ctx,Env.getContextAsInt(Env.getCtx(), WindowNo, "UY_TR_Budget_ID"),null); //instancio cabezal de cotizacion
				MTRWay way = (MTRWay)budget.getUY_TR_Way();
				MCountry countryFrom = new MCountry(ctx,way.getC_Country_ID(),null);
				MCountry countryTo = new MCountry(ctx,way.getC_Country_ID(),null);
				MTax tax = null;

				//si es importacion uruguaya y a su vez NO es en transito, se cobra IVA basico al terr. nacional
				if((countryFrom.getCountryCode().equalsIgnoreCase("BR") && countryTo.getCountryCode().equalsIgnoreCase("UY")) && !budget.isInTransit()){
					amount = (BigDecimal)mTab.getValue("NationalAmt");
					if(amount==null) amount=Env.ZERO;
				} else amount = Env.ZERO;

				if(amount.compareTo(Env.ZERO)==0) {

					tax = MTax.forValue(ctx, "exento", null);
					mTab.setValue("C_Tax_ID", tax.get_ID());

				} else {

					tax = MTax.forValue(ctx, "basico", null);
					mTab.setValue("C_Tax_ID", tax.get_ID());

				}

				int C_Tax_ID = taxID.intValue();
				tax = new MTax (ctx, C_Tax_ID, null);
				TaxAmt = tax.calculateTax(amount, isTaxIncluded(WindowNo), StdPrecision);
				mTab.setValue("TaxAmt", TaxAmt);
			}
		}

		BigDecimal priceEntered = (BigDecimal)mTab.getValue("PriceEntered");
		if(priceEntered==null) priceEntered=Env.ZERO;

		BigDecimal natAmt = (BigDecimal)mTab.getValue("NationalAmt");
		BigDecimal interAmt = (BigDecimal)mTab.getValue("InternationalAmt");

		if(interAmt == null) interAmt = Env.ZERO;
		if(natAmt == null) natAmt = Env.ZERO;

		if(priceEntered.compareTo(Env.ZERO)==0){

			priceEntered = natAmt.add(interAmt);

			mTab.setValue("PriceEntered", priceEntered);

		}

		if(natAmt.compareTo(Env.ZERO)==0) {

			mTab.setValue("NationalPercentage", Env.ZERO);

		}

		if(interAmt.compareTo(Env.ZERO)==0){

			mTab.setValue("InterPercentage", Env.ZERO);

		}

		mTab.setValue("LineTotalAmt", priceEntered.add(TaxAmt));

		return "";
	}	//	amt
	
	/**
	 * 	Is Tax Included
	 *	@param WindowNo window no
	 *	@return tax included (default: false)
	 */
	private boolean isTaxIncluded (int WindowNo)
	{
		String ss = Env.getContext(Env.getCtx(), WindowNo, "IsTaxIncluded");
		//	Not Set Yet
		if (ss.length() == 0)
		{
			int M_PriceList_ID = Env.getContextAsInt(Env.getCtx(), WindowNo, "M_PriceList_ID");
			if (M_PriceList_ID == 0)
				return false;
			ss = DB.getSQLValueString(null,
				"SELECT IsTaxIncluded FROM M_PriceList WHERE M_PriceList_ID=?", 
				M_PriceList_ID);
			if (ss == null)
				ss = "N";
			Env.setContext(Env.getCtx(), WindowNo, "IsTaxIncluded", ss);
		}
		return "Y".equals(ss);
	}	//	isTaxIncluded

	
	/***
	 * Al seleccionar un vale flete en el documento de factura vale flete, se deben cargar los demas valores.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadValeFleteInfo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int cInvoiceID = ((Integer)value).intValue();
		
		if (cInvoiceID == 0) return "";		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;		

		try {		
			
			String sql = " select inv.c_currency_id, inv.dateinvoiced, inv.c_paymentterm_id, vf.* " +
						 " from c_invoice inv " +
						 " inner join alloc_invflete_amtopen vf on inv.c_invoice_id = vf.c_invoice_id " +
						 " where inv.c_invoice_id =" + cInvoiceID;

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
		
			
			
			if(rs.next()){
				
				mTab.setValue(X_UY_Invoice_Flete.COLUMNNAME_C_Currency_ID, rs.getInt("c_currency_id"));
				mTab.setValue(X_UY_Invoice_Flete.COLUMNNAME_DateTrx, rs.getTimestamp("dateinvoiced"));
				mTab.setValue(X_UY_Invoice_Flete.COLUMNNAME_C_PaymentTerm_ID, rs.getInt("c_paymentterm_id"));
				mTab.setValue(X_UY_Invoice_Flete.COLUMNNAME_amtdocument, rs.getBigDecimal("amtdocument"));
				mTab.setValue(X_UY_Invoice_Flete.COLUMNNAME_amtopen, rs.getBigDecimal("amtopen"));
				mTab.setValue(X_UY_Invoice_Flete.COLUMNNAME_amtallocated, rs.getBigDecimal("amtopen"));
				mTab.setValue(X_UY_Invoice_Flete.COLUMNNAME_CurrencyRate, Env.ONE);
				
				// Tipo de cambio es el arbitraje entre moneda del cabezal y moneda de la linea
				Integer header = (Integer)mTab.getValue("C_Invoice_ID");
				if (header != null){
					if (header.intValue() > 0){
						MInvoice inv = new MInvoice(ctx, header.intValue(), null);
						if (inv.getC_Currency_ID() != rs.getInt("c_currency_id")){
							
							MClient client = new MClient(ctx, inv.getAD_Client_ID(), null);
							int idCurrencyAcct = client.getAcctSchema().getC_Currency_ID();
							
							BigDecimal rateHdr = MConversionRate.getRate(inv.getC_Currency_ID(), idCurrencyAcct, inv.getDateInvoiced(), 0, inv.getAD_Client_ID(), 0);
							BigDecimal rateLine = MConversionRate.getRate(rs.getInt("c_currency_id"), idCurrencyAcct, inv.getDateInvoiced(), 0, inv.getAD_Client_ID(), 0);
							
							if ((rateHdr != null) && (rateLine != null)){
								if (rateLine.compareTo(Env.ZERO) > 0){
									BigDecimal tc = rateHdr.divide(rateLine, 6, RoundingMode.HALF_UP);
									mTab.setValue(X_UY_Invoice_Flete.COLUMNNAME_CurrencyRate, tc);
								}
								else{
									mTab.setValue(X_UY_Invoice_Flete.COLUMNNAME_CurrencyRate, Env.ONE);
								}
							}
							else{
								mTab.setValue(X_UY_Invoice_Flete.COLUMNNAME_CurrencyRate, Env.ONE);
							}
						}
						else{
							mTab.setValue(X_UY_Invoice_Flete.COLUMNNAME_CurrencyRate, Env.ONE);
						}
					}
				}
				
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}
		
		return "";
	}	

	
	/***
	 * Al seleccionar una orden flete en el documento de factura vale flete, se deben cargar los demas valores.
	 * OpenUp Ltda. Issue #3205 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadOrderFleteInfo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int cOrderID = ((Integer)value).intValue();
		
		if (cOrderID == 0) return "";		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;		

		try {		
			
			String sql = " select ord.c_currency_id, ord.dateordered, ord.c_paymentterm_id, vf.* " +
						 " from c_order ord " +
						 " inner join alloc_ordflete_amtopen vf on ord.c_order_id = vf.c_order_id " +
						 " where ord.c_order_id =" + cOrderID;

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
			
			if(rs.next()){
				
				mTab.setValue(X_UY_Order_Flete.COLUMNNAME_C_Currency_ID, rs.getInt("c_currency_id"));
				mTab.setValue(X_UY_Order_Flete.COLUMNNAME_DateTrx, rs.getTimestamp("dateordered"));
				mTab.setValue(X_UY_Order_Flete.COLUMNNAME_C_PaymentTerm_ID, rs.getInt("c_paymentterm_id"));
				mTab.setValue(X_UY_Order_Flete.COLUMNNAME_amtdocument, rs.getBigDecimal("amtdocument"));
				mTab.setValue(X_UY_Order_Flete.COLUMNNAME_amtopen, rs.getBigDecimal("amtopen"));
				mTab.setValue(X_UY_Order_Flete.COLUMNNAME_amtallocated, rs.getBigDecimal("amtopen"));
				mTab.setValue(X_UY_Order_Flete.COLUMNNAME_CurrencyRate, Env.ONE);
				
				// Tipo de cambio es el arbitraje entre moneda del cabezal y moneda de la linea
				Integer header = (Integer)mTab.getValue("C_Invoice_ID");
				if (header != null){
					if (header.intValue() > 0){
						MInvoice inv = new MInvoice(ctx, header.intValue(), null);
						if (inv.getC_Currency_ID() != rs.getInt("c_currency_id")){
							
							MClient client = new MClient(ctx, inv.getAD_Client_ID(), null);
							int idCurrencyAcct = client.getAcctSchema().getC_Currency_ID();
							
							BigDecimal rateHdr = MConversionRate.getRate(inv.getC_Currency_ID(), idCurrencyAcct, inv.getDateInvoiced(), 0, inv.getAD_Client_ID(), 0);
							BigDecimal rateLine = MConversionRate.getRate(rs.getInt("c_currency_id"), idCurrencyAcct, inv.getDateInvoiced(), 0, inv.getAD_Client_ID(), 0);
							
							if ((rateHdr != null) && (rateLine != null)){
								if (rateLine.compareTo(Env.ZERO) > 0){
									BigDecimal tc = rateHdr.divide(rateLine, 6, RoundingMode.HALF_UP);
									mTab.setValue(X_UY_Order_Flete.COLUMNNAME_CurrencyRate, tc);
								}
								else{
									mTab.setValue(X_UY_Order_Flete.COLUMNNAME_CurrencyRate, Env.ONE);
								}
							}
							else{
								mTab.setValue(X_UY_Order_Flete.COLUMNNAME_CurrencyRate, Env.ONE);
							}
						}
						else{
							mTab.setValue(X_UY_Order_Flete.COLUMNNAME_CurrencyRate, Env.ONE);
						}
					}
				}
				
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}
		
		return "";
	}	

	
	/***
	 * 
	 * OpenUp Ltda. Issue # 
	 * @author Matías Pérez - 14/11/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadDataTireDrop(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int tireID = ((Integer)value).intValue();
		
		if (tireID == 0) return "";		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;		

		try {		
			
			String sql = " select  t.uy_tr_tiremark_id, t.observaciones, t.uy_tr_tiremeasure_id, t.qtykm5 " +
					     " from uy_tr_tire t " +
					     " where t.uy_tr_tire_id = " + tireID;

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
		
			if(rs.next()){
				
				//mTab.setValue(X_UY_TR_TireDrop.COLUMNNAME_UY_TR_TireDrop_ID, rs.getInt("uy_tr_tiredrop_id")) 				;
				mTab.setValue(X_UY_TR_TireDrop.COLUMNNAME_UY_TR_TireMark_ID, rs.getString("uy_tr_tiremark_id"));
				mTab.setValue(X_UY_TR_TireDrop.COLUMNNAME_observaciones, rs.getString("observaciones"));
				mTab.setValue(X_UY_TR_TireDrop.COLUMNNAME_UY_TR_TireMeasure_ID, rs.getInt("uy_tr_tiremeasure_id"));
				mTab.setValue(X_UY_TR_TireDrop.COLUMNNAME_QtyKm5, rs.getInt("qtykm5"));
				
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}
		
		return "";
	}

	/***
	 * En seguimiento de cargas al indicar nuevo estado de carga se setean atributos necesarios.
	 * OpenUp Ltda. Issue #1625 
	 * @author Gabriel Vila - 16/11/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setLoadMonitorStatus(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int id = ((Integer)value).intValue();
		
		if(id <= 0) return "";
		
		MTRLoadStatus model = new MTRLoadStatus(ctx, id, null);
		
		if(model.get_ID() > 0){
			
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_OnMove, model.isOnMove());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsWarehouseRequired, model.isWarehouseRequired());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsBeforeLoad, model.isBeforeLoad());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsTrasbordo, model.isTrasbordo());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_ChangeTruck, model.isChangeTruck());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_ChangeDriver, model.isChangeDriver());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_LoadProduct, model.isLoadProduct());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UnloadProduct, model.isEndTrackStatus());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsDelivered, model.isDelivered());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsLastre, model.isLastre());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsConfirmation, model.isConfirmation());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsPlanification, model.isPlanification());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_HandleCRT, model.isHandleCRT());
			
		}
		
		
		return "";
	}


	/***
	 * Para vale flete, al seleccionar una orden de transporte se carga info asociada a la misma.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 13/01/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setValeFleteOrderInfo(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int id = ((Integer)value).intValue();
		
		if(id <= 0) return "";
		
		MTRTransOrder model = new MTRTransOrder(ctx, id, null);
		
		if(model.get_ID() > 0){
			if (model.getC_Currency_ID_2() > 0){
				mTab.setValue(X_C_Invoice.COLUMNNAME_C_Currency_ID, model.getC_Currency_ID_2()); 
			}
		}
		
		
		return "";
	}
	
	
	/***
	 * En seguimiento de cargas al indicar orden de transporte se carga info de la misma en el cabezal de documento.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 16/11/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setLoadMonitorOT(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int id = ((Integer)value).intValue();
		
		if(id <= 0) return "";
		
		MTRTransOrder model = new MTRTransOrder(ctx, id, null);
		
		if(model.get_ID() > 0){
			
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_Ciudad_ID, model.getUY_Ciudad_ID());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_Ciudad_ID_1, model.getUY_Ciudad_ID_1());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_C_Currency_ID, model.getC_Currency_ID());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_TotalAmt, model.getTotalAmt());
			
		}
		
		
		return "";
	}

	
	/***
	 * En documento de asociacion de vehiculos al seleccionar el vehiculo se debe desplegar informacion. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 09/12/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setLoadTruckAssociation(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null){
			mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_TruckType_ID, null);
			mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_Truck_ID_Old, null);
			mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_TruckType_ID_2, null);
			mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_Truck_ID_New, null);
			mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_TruckType_ID_3, null);			

			return "";
		}
		
		int id = ((Integer)value).intValue();
		
		if(id <= 0) return "";
		
		MTRTruck model = new MTRTruck(ctx, id, null);
		
		if(model.get_ID() > 0){
			
			mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_TruckType_ID, model.getUY_TR_TruckType_ID());
			
			if (model.getUY_TR_Truck_ID_2() > 0){
				
				MTRTruck truck2 = new MTRTruck(ctx, model.getUY_TR_Truck_ID_2(), null);
				
				mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_Truck_ID_Old, model.getUY_TR_Truck_ID_2());
				mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_TruckType_ID_2, truck2.getUY_TR_TruckType_ID());
			}
			else{
				mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_Truck_ID_Old, null);
				mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_TruckType_ID_2, null);
			}
		}
		
		return "";
	}

	
	/***
	 * En documento de seguimiento de carga al seleccionar el vehiculo se debe desplegar informacion. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 09/12/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setLoadMonitorTruck(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null){
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID, null);
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Truck_ID_2, null);
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_2, null);
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Driver_ID, null);
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Driver_ID_2, null);
			return "";
		}
		
		int id = ((Integer)value).intValue();
		
		if(id <= 0) return "";
		
		MTRTruck model = new MTRTruck(ctx, id, null);
		
		if(model.get_ID() > 0){

			int remolqueID = 0, tractorID = 0;
			
			MTRTruckType type1 = (MTRTruckType)model.getUY_TR_TruckType();
			
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID, model.getUY_TR_TruckType_ID());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsAssociated, type1.isAssociated());			
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsOwn, model.isOwn());
			
			if (model.getUY_TR_Driver_ID() > 0){
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Driver_ID, model.getUY_TR_Driver_ID());	
			}
			else{
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Driver_ID, null);
			}
			
			if (type1.isContainer()){
				
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Remolque_ID, model.get_ID());				
				remolqueID = model.get_ID();
				
				if (!type1.isAssociated()){ // Si un tipo de vehiculo es contenedor y no asocia, asumo que es un TRUCK (tractor y remolque al mismo tiempo)
					tractorID = model.get_ID();
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Tractor_ID, model.get_ID());
				}
				else{
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Tractor_ID, null);
				}
				
			}
			else{
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Tractor_ID, model.get_ID());
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Remolque_ID, null);
				remolqueID = 0;
				tractorID = model.get_ID();
			}
			
			if (model.getUY_TR_Truck_ID_2() > 0){
				
				MTRTruck truck2 = new MTRTruck(ctx, model.getUY_TR_Truck_ID_2(), null);
				
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Truck_ID_2, model.getUY_TR_Truck_ID_2());
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_2, truck2.getUY_TR_TruckType_ID());
				
				if (!truck2.isOwn()) mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsOwn, truck2.isOwn());
				
				if (type1.isContainer()){
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Tractor_ID, truck2.get_ID());
				}
				else{
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Remolque_ID, truck2.get_ID());
					remolqueID = truck2.get_ID();
				}
				
			}
			else{
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Truck_ID_2, null);
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_2, null);
			}

			MTRLoadStatus sts = new MTRLoadStatus(ctx, (Integer)mTab.getValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_LoadStatus_ID), null);
			
			// Si tengo remolque, y no estoy en accion de enganche de vehiculo, busco orden de transporte aplicada
			if (remolqueID > 0){
				if (!sts.isChangeTruck()){
					MTRTransOrder torder = MTRTransOrder.forRemolqueIDApply(ctx, remolqueID, null);
					if ((torder != null) && (torder.get_ID() > 0)){
						mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID, torder.get_ID());
					}
					else{
						mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID, null);
					}
				}
				else{
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID, null);
				}
			}
			else{
				// Si estoy en accion de lastre (viaje o llegada), cargo orden de transporte si la hay.
				if (sts.isLastre()){
					MTRTransOrder torder = MTRTransOrder.forTractorIDApply(ctx, tractorID, null);
					if ((torder != null) && (torder.get_ID() > 0)){
						mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID, torder.get_ID());
					}
					else{
						mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID, null);
					}
				}
				else{
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID, null);	
				}
			}
		}
		
		return "";
	}

	/***
	 * En documento de Vale Flete al seleccionar el vehiculo se debe desplegar informacion. 
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 18/12/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setVFTruck(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null){
			mTab.setValue("UY_TR_TruckType_ID", null);
			mTab.setValue("UY_TR_Truck_ID_Aux", null);
			mTab.setValue("UY_TR_TruckType_ID_2", null);
			mTab.setValue("UY_TR_Driver_ID", null);
			return "";
		}
		
		int id = ((Integer)value).intValue();
		
		if(id <= 0) return "";
		
		MTRTruck model = new MTRTruck(ctx, id, null);
		
		if(model.get_ID() > 0){
			 
			MTRTruckType type1 = (MTRTruckType)model.getUY_TR_TruckType();
			
			mTab.setValue("UY_TR_TruckType_ID", model.getUY_TR_TruckType_ID());
						
			if (model.getUY_TR_Driver_ID() > 0){
				mTab.setValue("UY_TR_Driver_ID", model.getUY_TR_Driver_ID());	
			}
			else{
				mTab.setValue("UY_TR_Driver_ID", null);
			}
			
			if (type1.isContainer()){
				mTab.setValue("Remolque_ID", model.get_ID());
				mTab.setValue("Tractor_ID", null);
				mTab.setValue("C_BPartner_ID", model.get_ValueAsInt("C_BPartner_ID_P"));
			}
			else{
				mTab.setValue("Tractor_ID", model.get_ID());
				mTab.setValue("Remolque_ID", null);
				mTab.setValue("C_BPartner_ID", model.get_ValueAsInt("C_BPartner_ID_P"));
			}
			
			if (model.getUY_TR_Truck_ID_2() > 0){
				
				MTRTruck truck2 = new MTRTruck(ctx, model.getUY_TR_Truck_ID_2(), null);
				
				mTab.setValue("UY_TR_Truck_ID_Aux", model.getUY_TR_Truck_ID_2());
				mTab.setValue("UY_TR_TruckType_ID_2", truck2.getUY_TR_TruckType_ID());
				
				if (type1.isContainer()){
					mTab.setValue("Tractor_ID", truck2.get_ID());
					mTab.setValue("C_BPartner_ID", truck2.get_ValueAsInt("C_BPartner_ID_P"));					
				}
				else{
					mTab.setValue("Remolque_ID", truck2.get_ID());
				}
				
			}
			else{
				mTab.setValue("UY_TR_Truck_ID_Aux", null);
				mTab.setValue("UY_TR_TruckType_ID_2", null);
			}
		
			
		}
		
		return "";
	}
	
	/***
	 * En documento de seguimiento de carga al seleccionar el nuevo vehiculo se debe desplegar informacion. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 09/12/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setLoadMonitorTruck3(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null){
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_3, null);
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Truck_ID_4, null);
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_4, null);
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Driver_ID, null);
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Driver_ID_2, null);
			return "";
		}
		
		int id = ((Integer)value).intValue();
		
		if(id <= 0) return "";
		
		MTRTruck model = new MTRTruck(ctx, id, null);
		
		if(model.get_ID() > 0){

			int remolqueID = 0;
			
			MTRTruckType type1 = (MTRTruckType)model.getUY_TR_TruckType();
			
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_3, model.getUY_TR_TruckType_ID());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsAssociated2, type1.isAssociated());
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsOwn2, model.isOwn());			
			
			if (model.getUY_TR_Driver_ID() > 0){
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Driver_ID, model.getUY_TR_Driver_ID());	
			}
			else{
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Driver_ID, null);
			}
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Driver_ID_2, null);
			
			
			if (type1.isContainer()){
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Remolque_ID_2, model.get_ID());
				remolqueID = model.get_ID();
				
				if (!type1.isAssociated()){ // Si un tipo de vehiculo es contenedor y no asocia, asumo que es un TRUCK (tractor y remolque al mismo tiempo)
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Tractor_ID_2, model.get_ID());
				}
				else{
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Tractor_ID_2, null);
				}
				
			}
			else{
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Tractor_ID_2, model.get_ID());
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Remolque_ID_2, null);
				remolqueID = 0;
			}
			
			if (model.getUY_TR_Truck_ID_2() > 0){
				
				MTRTruck truck2 = new MTRTruck(ctx, model.getUY_TR_Truck_ID_2(), null);
				
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Truck_ID_4, model.getUY_TR_Truck_ID_2());
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_4, truck2.getUY_TR_TruckType_ID());
				
				if (!truck2.isOwn()) mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_IsOwn2, truck2.isOwn());
				
				if (type1.isContainer()){
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Tractor_ID_2, truck2.get_ID());
				}
				else{
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Remolque_ID_2, truck2.get_ID());
					remolqueID = truck2.get_ID();
				}
				
			}
			else{
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Truck_ID_4, null);
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_4, null);
			}

			// Si tengo remolque, busco orden de transporte aplicada
			if (remolqueID > 0){
				MTRTransOrder torder = MTRTransOrder.forRemolqueIDApply(ctx, remolqueID, null);
				if ((torder != null) && (torder.get_ID() > 0)){
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID_2, torder.get_ID());
				}
				else{
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID_2, null);
				}
			}
			else{
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID_2, null);
			}
			
		}
		
		return "";
	}
	

	
	/***
	 * Al seleccionar un nuevo vehiculo en el documento de asociacion de vehiculos se debe cargar info del mismo.
	 * OpenUp Ltda. Issue #1402 
	 * @author Gabriel Vila - 09/12/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setTruckAssociationTypeNew(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) {
			mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_TruckType_ID_3, null);
			return "";
		}
		
		int id = ((Integer)value).intValue();
		
		if(id <= 0) return "";
		
		MTRTruck model = new MTRTruck(ctx, id, null);
		
		if(model.get_ID() > 0){
			mTab.setValue(X_UY_TR_TruckAssociation.COLUMNNAME_UY_TR_TruckType_ID_3, model.getUY_TR_TruckType_ID());
		}
		
		return "";
	}

	
	/***
	 * Al seleccionar moneda en un gasto extra en la factura de vale flete, debo calcular tasa de cambio.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setTCInvLineValeFlete(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int id = ((Integer)value).intValue();
		
		if (id == 0) return "";		
		
		try {		

			MCurrency lineCurrency = new MCurrency(ctx, id, null);
			if (lineCurrency.get_ID() <= 0) return "";
			
			// Tipo de cambio es el arbitraje entre moneda del cabezal y moneda de la linea
			Integer header = (Integer)mTab.getValue("C_Invoice_ID");
			if (header != null){
				if (header.intValue() > 0){
					MInvoice inv = new MInvoice(ctx, header.intValue(), null);
					if (inv.getC_Currency_ID() != lineCurrency.get_ID()){
						
						MClient client = new MClient(ctx, inv.getAD_Client_ID(), null);
						int idCurrencyAcct = client.getAcctSchema().getC_Currency_ID();
						
						BigDecimal rateHdr = MConversionRate.getRate(inv.getC_Currency_ID(), idCurrencyAcct, inv.getDateInvoiced(), 0, inv.getAD_Client_ID(), 0);
						BigDecimal rateLine = MConversionRate.getRate(lineCurrency.get_ID(), idCurrencyAcct, inv.getDateInvoiced(), 0, inv.getAD_Client_ID(), 0);
						
						if ((rateHdr != null) && (rateLine != null)){
							if (rateLine.compareTo(Env.ZERO) > 0){
								BigDecimal tc = rateHdr.divide(rateLine, 6, RoundingMode.HALF_UP);
								mTab.setValue("CurrencyRate", tc);
							}
							else{
								mTab.setValue("CurrencyRate", Env.ONE);
							}
						}
						else{
							mTab.setValue("CurrencyRate", Env.ONE);
						}
					}
					else{
						mTab.setValue("CurrencyRate", Env.ONE);
					}
				}
			}
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return "";
	}	

	
	/***
	 * Al seleccionar un CRT en la linea del documento de seguimiento de carga, se debe traer la información asociada
	 * al mismo segun la accion de carga a procesar.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 14/12/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setLoadMonitorLineCRT(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (value == null) return "";

		int crtID = ((Integer)value).intValue();

		if (crtID == 0) return "";		

		ResultSet rs = null;
		PreparedStatement pstmt = null;		
		
		try {
			MTRCrt crt = new MTRCrt(Env.getCtx(), crtID, null);
			MTRTrip trip = (MTRTrip)crt.getUY_TR_Trip();

			MTRLoadMonitor header = new MTRLoadMonitor(ctx, (Integer)mTab.getValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_UY_TR_LoadMonitor_ID), null);
			MTRLoadStatus sts = (MTRLoadStatus)header.getUY_TR_LoadStatus();
			
			MTRConfig param = MTRConfig.forClient(ctx, null);
			
			if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
			
			MTRConfigRound round = MTRConfigRound.forConfig(ctx, param.get_ID(), null);
			
			if (round==null) throw new AdempiereException("No se obtuvieron parametros de precision decimal para la empresa actual");
			
			mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_UY_TR_Trip_ID, crt.getUY_TR_Trip_ID());
			mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_C_Currency_ID, crt.getC_Currency_ID());
			mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_UY_TR_PackageType_ID, trip.getUY_TR_PackageType_ID());

			String sql = "";
			
			// Para obtener la cantidad de bultos disponible, dependo de la accion de carga y del sitio de donde estoy considerando esa carga
			if ((header.isLoadProduct()) || (header.isHandleCRT() && header.isWarehouseRequired() && !sts.isEndTrackStatus()) || (header.isDelivered())){
				if ((header.getTypeLoad() != null) && (!header.getTypeLoad().equalsIgnoreCase(""))){
					if ((header.getTypeLoad() == null) || (header.getTypeLoad().equalsIgnoreCase(X_UY_TR_LoadMonitor.TYPELOAD_CLIENTE))){
						sql = " select productamt, amount, qtypackage, volume, weight, weight2 " +
							  " from vtr_crt_qtytotal " +
							  " where uy_tr_crt_id =" + crt.get_ID(); 
						
					}
					else if (header.getTypeLoad().equalsIgnoreCase(X_UY_TR_LoadMonitor.TYPELOAD_ALMACENPROPIO)){

						sql = " select productamt, amount, qtypackage, volume, weight, weight2 " +
								  " from alloc_tr_crt " +
								  " where uy_tr_crt_id =" + crt.get_ID() +
								  " and m_warehouse_id =" + header.getM_Warehouse_ID();
					}
					
					pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
					rs = pstmt.executeQuery ();				

					if (rs.next()){
						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_ProductAmt, rs.getBigDecimal("productamt").setScale(round.getProductAmt(), RoundingMode.HALF_UP));
						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Amount, rs.getBigDecimal("amount").setScale(round.getAmount(), RoundingMode.HALF_UP));
						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_QtyPackage, rs.getBigDecimal("qtypackage").setScale(round.getQtyPackage(), RoundingMode.HALF_UP));
						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Volume, rs.getBigDecimal("volume").setScale(round.getVolume(), RoundingMode.HALF_UP));
						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Weight, rs.getBigDecimal("weight").setScale(round.getWeight(), RoundingMode.HALF_UP));
						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Weight2, rs.getBigDecimal("weight2").setScale(round.getWeight2(), RoundingMode.HALF_UP));

						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_ProductAmt2, rs.getBigDecimal("productamt").setScale(round.getProductAmt(), RoundingMode.HALF_UP));
						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Amount2, rs.getBigDecimal("amount").setScale(round.getAmount(), RoundingMode.HALF_UP));
						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_QtyPackage2, rs.getBigDecimal("qtypackage").setScale(round.getQtyPackage(), RoundingMode.HALF_UP));
						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Volume2, rs.getBigDecimal("volume").setScale(round.getVolume(), RoundingMode.HALF_UP));
						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_WeightAux, rs.getBigDecimal("weight").setScale(round.getWeight(), RoundingMode.HALF_UP));
						mTab.setValue(X_UY_TR_LoadMonitorLine.COLUMNNAME_Weight2Aux, rs.getBigDecimal("weight2").setScale(round.getWeight2(), RoundingMode.HALF_UP));
					}
				}
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}
		
		return "";
		
	}	
	
	
	/***
	 * En documento de seguimiento de carga al cambiar vehiculo asociado debo traer info correcta. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 09/12/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setLoadMonitorTruck2(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (isCalloutActive()) return "";
		
		if (value == null){
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_2, null);
			return "";
		}
		
		int id = ((Integer)value).intValue();
		
		if(id <= 0) return "";
		
		MTRTruck model = new MTRTruck(ctx, id, null);
		
		try {

			if(model.get_ID() > 0){

				// Seteo tipo de vehiculo 2
				MTRTruckType type2 = (MTRTruckType)model.getUY_TR_TruckType();
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_2, model.getUY_TR_TruckType_ID());
				
				// Si tengo vehiculo 1
				MTRTruck truck1 = null; 
				if (mTab.getValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Truck_ID) != null){
					truck1 = new MTRTruck(ctx, (Integer)mTab.getValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Truck_ID), null);
				}
				if ((truck1 != null) && (truck1.get_ID() > 0)){
					// Obtengo tipo de de vehiculo 1
					MTRTruckType type1 = (MTRTruckType)truck1.getUY_TR_TruckType();
					
					// Si ambos tipos de vehiculo son iguales, aviso y no hago mas nada
					if ((type1.isContainer() && type2.isContainer()) || (!type1.isContainer() && !type2.isContainer())){
						throw new AdempiereException("No se pueden asociar vehiculos del mismo tipo.");
					}
				}
				
				int remolqueID = 0;

				if (type2.isContainer()){
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Remolque_ID, model.get_ID());
					remolqueID = model.get_ID();
				}
				else{
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Tractor_ID, model.get_ID());
					remolqueID = 0;
				}

				// Si tengo remolque, busco orden de transporte aplicada
				if (remolqueID > 0){
					
					MTRLoadStatus sts = new MTRLoadStatus(ctx, (Integer)mTab.getValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_LoadStatus_ID), null);
					if (!sts.isChangeTruck()){
						MTRTransOrder torder = MTRTransOrder.forRemolqueIDApply(ctx, remolqueID, null);
						if ((torder != null) && (torder.get_ID() > 0)){
							mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID, torder.get_ID());
						}
						else{
							mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID, null);
						}
					}
					else{
						mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TransOrder_ID, null);
					}
				}
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		
		return "";
	}
	
	
	/***
	 * En documento de seguimiento de carga al cambiar vehiculo asociado debo traer info correcta. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 09/12/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setLoadMonitorTruck4(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (isCalloutActive()) return "";
		
		if (value == null){
			mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_4, null);
			return "";
		}
		
		int id = ((Integer)value).intValue();
		
		if(id <= 0) return "";
		
		MTRTruck model = new MTRTruck(ctx, id, null);
		
		try {

			if(model.get_ID() > 0){

				// Seteo tipo de vehiculo 4
				MTRTruckType type4 = (MTRTruckType)model.getUY_TR_TruckType();
				mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_TruckType_ID_4, model.getUY_TR_TruckType_ID());
				
				// Si tengo vehiculo 3
				MTRTruck truck3 = null; 
				if (mTab.getValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Truck_ID_3) != null){
					String valor = (String)mTab.getValue(X_UY_TR_LoadMonitor.COLUMNNAME_UY_TR_Truck_ID_3);
					int truck3_ID = Integer.parseInt(valor);
					truck3 = new MTRTruck(ctx, truck3_ID, null);					
				}
				if ((truck3 != null) && (truck3.get_ID() > 0)){
					// Obtengo tipo de de vehiculo 3
					MTRTruckType type3 = (MTRTruckType)truck3.getUY_TR_TruckType();
					
					// Si ambos tipos de vehiculo son iguales, aviso y no hago mas nada
					if ((type3.isContainer() && type4.isContainer()) || (!type3.isContainer() && !type4.isContainer())){
						throw new AdempiereException("No se pueden asociar vehiculos del mismo tipo.");
					}
				}

				if (type4.isContainer()){
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Remolque_ID_2, model.get_ID());
				}
				else{
					mTab.setValue(X_UY_TR_LoadMonitor.COLUMNNAME_Tractor_ID_2, model.get_ID());
				}
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		
		return "";
	}	
	
	/***
	 * 
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 17/12/2014
	 * Metodo que setea tractor y remolque desde la OT en el MIC.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setTruckFromTransOrder(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) {
			
			mTab.setValue("tractor_id", null);
			mTab.setValue("remolque_id", null);
			
			return "";
		}
		
		int id = ((Integer)value).intValue();
		
		if(id <= 0) return "";
		
		MTRTransOrder order = new MTRTransOrder(ctx, id, null);

		if(order.getTractor_ID()>0) mTab.setValue("tractor_id", order.getTractor_ID());
		if(order.getRemolque_ID()>0) mTab.setValue("remolque_id", order.getRemolque_ID());

		if(order.getTractor_ID()<=0){

			if(order.getRemolque_ID()>0){

				MTRTruck rem = new MTRTruck(ctx,order.getRemolque_ID(),null);
				MTRTruckType type = (MTRTruckType)rem.getUY_TR_TruckType();

				if(type.isContainer() && !type.isAssociated()) mTab.setValue("tractor_id", order.getRemolque_ID());
			} 	
		}		
				
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda.
	 * @author Nicolas Sarlabos - 19/03/2015
	 * Metodo que setea los datos a partir del CRT seleccionado en ventana de Programacion de Cargas.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromCrtSchedule(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int crtID = ((Integer)value).intValue();
		
		if(crtID <= 0) return "";
		
		MTRCrt crt = new MTRCrt(ctx,crtID,null);
				
		MCountry countryFrom = null, countryTo = null;
		
		MTRTrip trip = (MTRTrip)crt.getUY_TR_Trip();
		
		if (trip.getUY_TR_Way_ID() > 0){
			MTRWay way = (MTRWay)trip.getUY_TR_Way();
			countryFrom = (MCountry)((MCiudad)way.getUY_Ciudad()).getC_Country();
			countryTo = (MCountry)(new MCiudad(ctx, way.getUY_Ciudad_ID_1(), null).getC_Country());
		}
		else{
			countryFrom = (MCountry)((MCiudad)trip.getUY_Ciudad()).getC_Country();
			countryTo = (MCountry)(new MCiudad(ctx, trip.getUY_Ciudad_ID_1(), null).getC_Country());
		}
		
		// Obtengo codigo de idoneidad para los dos paises obtenidos 
		String sql = " select coalesce(code,null) " +
					 " from uy_tr_suitability " +
					 " where ((c_country_id = " + countryFrom.get_ID() + 
					 " and c_country_id_1 = " +	countryTo.get_ID() + ") " +
					 " or (c_country_id = " + countryTo.get_ID() + 
					 " and c_country_id_1 = " + countryFrom.get_ID() + "))" +
					 " and ad_client_id = " + crt.getAD_Client_ID();
		
		String suitNumber = DB.getSQLValueStringEx(null, sql);
		
		//seteo permiso
		if(suitNumber!=null && !suitNumber.equalsIgnoreCase("")) mTab.setValue("TypeCRT", suitNumber);
		
		//seteo exportador e importador
		mTab.setValue("C_BPartner_ID_From", trip.getC_BPartner_ID_To());
		mTab.setValue("C_BPartner_ID_To", trip.getC_BPartner_ID_From());	
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue # 
	 * @author Nicolas Sarlabos - 26/03/2015
	 * Metodo que carga los datos del cabezal en una NC cliente, desde una factura de flete. 
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadFromFreightInvoice(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (value == null) return "";

		int invID = ((Integer)value).intValue();

		if (invID == 0) return "";		

		int docID = Env.getContextAsInt (ctx, WindowNo, 0, "C_DocTypeTarget_ID");

		if(docID <= 0) return "";

		MDocType doc = new MDocType(ctx,docID,null); //obtengo tipo de documento

		if(doc.getValue()!=null && (doc.getValue().equalsIgnoreCase("customerncflete") || doc.getValue().equalsIgnoreCase("et-nc"))){

			MInvoice inv = new MInvoice(ctx, invID, null);

			//se cargan datos del cabezal
			mTab.setValue("C_BPartner_Location_ID", inv.getC_BPartner_Location_ID());
			mTab.setValue("UY_TR_Trip_ID", inv.get_Value("UY_TR_Trip_ID"));
			mTab.setValue("UY_TR_Crt_ID", inv.get_Value("UY_TR_Crt_ID"));
			mTab.setValue("ReferenceNo", inv.get_Value("ReferenceNo"));
			mTab.setValue("uy_cantbultos", inv.getuy_cantbultos());
			mTab.setValue("UY_TR_PackageType_ID", inv.get_Value("UY_TR_PackageType_ID"));
			mTab.setValue("Weight", inv.get_Value("Weight"));
			mTab.setValue("Weight2", inv.get_Value("Weight2"));
			mTab.setValue("AmtOriginal", inv.getAmtOriginal());
			mTab.setValue("C_Currency_ID", inv.getC_Currency_ID());
			mTab.setValue("C_Currency2_ID", inv.get_Value("C_Currency2_ID"));
			mTab.setValue("C_PaymentTerm_ID", inv.getC_PaymentTerm_ID());
			mTab.setValue("C_Currency3_ID", inv.get_Value("C_Currency3_ID"));

		}

		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #3505 
	 * @author Nicolas Sarlabos - 12/04/2015
	 * Metodo que carga el conductor al seleccionar el vehiculo en ventana de liquidacion de viaje. 
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setDriverFromTruck(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (value == null) return "";

		int truckID = ((Integer)value).intValue();

		if (truckID == 0) return "";	
		
		MTRTruck truck = new MTRTruck(ctx, truckID, null);
		
		if(truck.getUY_TR_Driver_ID()>0) mTab.setValue("UY_TR_Driver_ID", truck.getUY_TR_Driver_ID());		

		return "";
	}

	
	/***
	 * 
	 * OpenUp Ltda.
	 * @author Nicolas Sarlabos - 19/03/2015
	 * Metodo que setea los datos a partir del CRT seleccionado en ventana de Programacion de Cargas.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromCrtMassiveAction(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int crtID = ((Integer)value).intValue();
		
		if(crtID <= 0) return "";
		
		MTRCrt crt = new MTRCrt(ctx, crtID, null);
		
		MTRTrip trip = (MTRTrip)crt.getUY_TR_Trip();
		
		// Seteo campos
		mTab.setValue("C_BPartner_ID_From", trip.getC_BPartner_ID_To());
		mTab.setValue("C_BPartner_ID_To", trip.getC_BPartner_ID_From());
		
		if (crt.getcodigo() != null){
			mTab.setValue(X_UY_TR_MassiveActionLine.COLUMNNAME_ImageNo, crt.getcodigo());
			mTab.setValue(X_UY_TR_MassiveActionLine.COLUMNNAME_IsExecuted, true);
		}
			
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #4631
	 * @author Nicolas Sarlabos - 17/08/2015
	 * Metodo que carga datos de kilometraje actual del vehiculo, al elegir vehiculo en ventana de lectura de KM. 
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromTruckReadKm(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (value == null) return "";

		int truckID = ((Integer)value).intValue();

		if (truckID == 0) return "";	
		
		MTRTruck truck = new MTRTruck(ctx, truckID, null);
		
		if(truck.get_ID() > 0){
			
			mTab.setValue("QtyKmLast", truck.getQtyKm());
			mTab.setValue("DateLast", truck.getDateAction());
		}

		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #4829.
	 * @author Nicolas Sarlabos - 26/10/2015
	 * Metodo que carga el kilometraje actual del vehiculo al seleccionarlo en ventana de OS. 
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setTruckKmOS(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (value == null) return "";

		int truckID = ((Integer)value).intValue();

		if (truckID == 0) return "";	
		
		MTRTruck truck = new MTRTruck(ctx, truckID, null);
		
		if(truck.getQtyKm()>=0) mTab.setValue("Kilometros", truck.getQtyKm());		

		return "";
	}

	
}
