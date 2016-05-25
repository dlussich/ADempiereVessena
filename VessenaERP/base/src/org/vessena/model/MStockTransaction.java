/**
 * MStockTransaction.java
 * 29/03/2011
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MLocator;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.compiere.model.X_C_BPartner;
import org.compiere.model.X_C_DocType;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.StockCost;

/***
 * Gestion de stock.
 * org.openup.model - MStockTransaction
 * OpenUp Ltda. Issue #1 
 * Description: Gestion de Stock.
 * @author Gabriel Vila - 11/07/2014
 * @see
 */
public class MStockTransaction extends X_UY_StockTransaction {

	private static final long serialVersionUID = 1048379158560487400L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_StockTransaction_ID
	 * @param trxName
	 */
	public MStockTransaction(Properties ctx, int UY_StockTransaction_ID,
			String trxName) {
		super(ctx, UY_StockTransaction_ID, trxName);
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MStockTransaction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Nueva linea en tabla ficha de stock con todas las validaciones y verificaciones de calidad.
	 * OpenUp Ltda. Issue #1 
	 * @author Gabriel Vila - 11/07/2014
	 * @see
	 * @param poDocSource
	 * @param poDocAffected
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param stockStatusID
	 * @param movementDate
	 * @param movementQty
	 * @return
	 */
	public static String add (PO poDocSource, PO poDocAffected, int mWarehouseID, int mLocatorID, int mProductID, int mAttributeSetInstanceID, 
							  int stockStatusID, Timestamp movementDate, BigDecimal movementQty, int lineID, StockCost stkCostInfo) {
		
		String result = "", resultAux = "";
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		
		// Validacion de parametros recibidos
		if (poDocSource == null) result = "No se indica Documento.";
		
		if (mProductID <= 0) return null;

		if (movementQty == null) return null;
		
		if (movementQty.compareTo(Env.ZERO) == 0) return null;
		
		if (movementDate == null){
			resultAux = "No se indica Fecha de Movimiento.";
			result += ((result.equalsIgnoreCase("")) ?  resultAux : ("\n" + resultAux));
		}

		if (!result.equalsIgnoreCase("")){
			result = "Error al Crear Movimiento de Stock :\n" + result;
			return result;
		}

		// Valido que se puedan hacer movimientos con fecha anteriores al ultimo cierre de inventario.
		if (!MSysConfig.getBooleanValue("UY_STOCK_ALLOW_BEFORE_INV", true, poDocSource.getAD_Client_ID())){
			MInventory inventory = MInventory.getLastInventory();
			if (inventory != null){
				if (movementDate.before(inventory.getMovementDate())){
					result = "Fecha de Movimiento de Stock para esta transacción es Anterior al ultimo cierre del Inventario." + "\n" + 
							 "Numero Inventario : " + inventory.getDocumentNo() + "\n" +
							 "Fecha Inventario : " + inventory.getMovementDate();
					return result;
				}
			}
		}
 		
		// Instancio modelo de producto
		MProduct prod = new MProduct(poDocSource.getCtx(), mProductID, null);
		
		// Si el producto no es stockeable, no hago nada
		if (!prod.isStocked()) return null;
		
		// Obtengo y valido datos del documento origen 
		int cDocTypeID = poDocSource.get_ValueAsInt(X_C_DocType.COLUMNNAME_C_DocType_ID);
		int adTableID = poDocSource.get_Table_ID();
		int recordID = poDocSource.get_ID();
		String documentNo = poDocSource.get_ValueAsString(X_UY_StockTransaction.COLUMNNAME_DocumentNo);

		if (cDocTypeID <= 0) result = "No se indica Documento Origen.";
		if (adTableID <= 0){
			resultAux = "No se indica Tabla Origen.";
			result += ((result.equalsIgnoreCase("")) ?  resultAux : ("\n" + resultAux));
		}
		if (recordID <= 0){
			resultAux = "No se indica identificador del registro Origen.";
			result += ((result.equalsIgnoreCase("")) ?  resultAux : ("\n" + resultAux));
		}  
		if (!result.equalsIgnoreCase("")){
			result = "Error al Crear Movimiento de Stock :\n" + result;
			return result;
		}

		// Obtengo y valido datos del documento a afectar
		// Si no recibo documento a afectar, tomo el documento origen
		if (poDocAffected == null) poDocAffected = poDocSource;
		
		int cDocTypeAffectedID = poDocAffected.get_ValueAsInt(X_C_DocType.COLUMNNAME_C_DocType_ID);
		int adTableAffectedID = poDocAffected.get_Table_ID();
		int recordAffectedID = poDocAffected.get_ID();
		String documentNoAffected = poDocAffected.get_ValueAsString(X_UY_StockTransaction.COLUMNNAME_DocumentNo);

		if (cDocTypeAffectedID <= 0) result = "No se indica Documento a Afectar.";
		if (adTableAffectedID <= 0){
			resultAux = "No se indica Tabla a Afectar.";
			result += ((result.equalsIgnoreCase("")) ?  resultAux : ("\n" + resultAux));
		}
		if (recordAffectedID <= 0){
			resultAux = "No se indica identificador del registro a Afectar.";
			result += ((result.equalsIgnoreCase("")) ?  resultAux : ("\n" + resultAux));
		}  

		if (!result.equalsIgnoreCase("")){
			result = "Error al Crear Movimiento de Stock :\n" + result;
			return result;
		}
		
		try{

			// Obtengo parametros de manejo de estados de stock para este documento
			MDocType docTypeSource = new MDocType(poDocSource.getCtx(), cDocTypeID, poDocSource.get_TrxName());
			MStockStatusByDocHdr stockDocHdr = MStockStatusByDocHdr.get(docTypeSource);
			
			// Sino tengo definicion de estados de stock para este documento, salgo sin hacer nada.
			if (stockDocHdr == null) return null;
			if (stockDocHdr.getUY_StockStatusByDocHdr_ID() <= 0) return null;

			// Instancio modelo para movimiento de stock, lo seteo y lo guardo en DB
			MStockTransaction movStock = new MStockTransaction(poDocSource.getCtx(), 0, poDocSource.get_TrxName());
			movStock.setAD_Window_ID(Env.getWindowNo(null));
			movStock.setAD_User_ID(poDocSource.getCreatedBy());
			movStock.setDateTrx(today);
			
			// Si el modelo origen viene con organizacion, me aseguro de contemplarla en el movimiento de stock
			if(poDocSource.getAD_Org_ID() != 0){
				movStock.setAD_Org_ID(poDocSource.getAD_Org_ID());
			}
			
			// Me aseguro que la fecha de movimiento no tenga hora
			movementDate = TimeUtil.trunc(movementDate, TimeUtil.TRUNC_DAY);
			
			movStock.setMovementDate(movementDate);
			movStock.setC_Period_ID(MPeriod.getC_Period_ID(poDocSource.getCtx(), movementDate, movStock.getAD_Org_ID()));
			
			// Valido estado de stock a considerar segun el siguiente criterio:
			// 1. Si no recibo estado de stock, voy a tomarlo de la parametrizacion. Si en la parametrizacion hay mas de un estado posible, aviso con error, ya
			//    que no puedo tomar uno arbitrariamente.
			// 2. Si recibo estado de stock, valido que el mismo este dentro de la parametrizacion del documento.
			MStockStatusListHdr stsListHdr = new MStockStatusListHdr(poDocSource.getCtx(), stockDocHdr.getUY_StockStatusListHdr_ID(), null);  
			MStockStatusListLine[] stsListLines = stsListHdr.getLines();
			if (stsListLines.length <= 0){
				result = "Error al Crear Movimiento de Stock :\n" + "El Documento no tiene lista de estados de stock asociada en una de sus lineas : " + docTypeSource.getName();
				return result;
			}

			// Si no recibo estado de stock
			if (stockStatusID <= 0){
				if (stsListLines.length > 1){
					result = "Error al Crear Movimiento de Stock :\n" + "No se recibe estado de stock a considerar de lista de estados posibles : " + docTypeSource.getName();
					return result;
				}
				movStock.setUY_StockStatus_ID(stsListLines[0].getUY_StockStatus_ID());
			}
			else{
				// Recibo estado de stock, valido que este dentro de la lista de posibles
				boolean existOne = false;
				for (int j=0; (j<stsListLines.length) && (!existOne); j++){
					if (stsListLines[j].getUY_StockStatus_ID() == stockStatusID){
						existOne=true;
						movStock.setUY_StockStatus_ID(stsListLines[j].getUY_StockStatus_ID());
					}
				}
				// Si el estado de stock recibido no estaba dentro de los posibles
				if (!existOne){
					result = "Error al Crear Movimiento de Stock :\n" + "El estado de stock recibido no se encuentra dentro de la lista de estados posibles : " + docTypeSource.getName();
					return result;
				}
			}

			// Cantidad y signo. Cantidad siempre se guarda positiva y el signo le da la naturaleza
			BigDecimal signo = Env.ONE;
			if (movementQty.compareTo(Env.ZERO) < 0){
				signo = new BigDecimal(-1);
				movementQty = movementQty.multiply(new BigDecimal(-1));
			}
			
			movStock.setsign(signo);
			movStock.setMovementQty(movementQty);
			
			// Almacenamiento - producto - instancia
			movStock.setM_Product_ID(mProductID);
			movStock.setC_UOM_ID(prod.getC_UOM_ID());
			movStock.setM_AttributeSetInstance_ID(mAttributeSetInstanceID);
			
			// Si recibo warehouse y no recibo locator, considero locator por defecto del warehouse
			if ((mWarehouseID > 0) && (mLocatorID <= 0)){
				MWarehouse wHouse = new MWarehouse(poDocSource.getCtx(), mWarehouseID, null);
				movStock.setM_Warehouse_ID(mWarehouseID);
				movStock.setM_Locator_ID(wHouse.getDefaultLocator().getM_Locator_ID());
			}
			// Si no recibo warehouse y recibo locator, considero warehouse asociado al locator
			else if ((mWarehouseID <= 0) && (mLocatorID > 0)){
				MLocator locator = new MLocator(poDocSource.getCtx(), mLocatorID, null);
				movStock.setM_Warehouse_ID(locator.getM_Warehouse_ID());
				movStock.setM_Locator_ID(mLocatorID);
			}
			// Si recibo ambos, valido que el locator se corresponda con el warehouse, 
			// y si no se corresponde tomo el locator por defecto del warehouse
			else if ((mWarehouseID > 0) && (mLocatorID > 0)){
				MLocator locator = new MLocator(poDocSource.getCtx(), mLocatorID, null);
				int locatorAuxID = mLocatorID;
				if (mWarehouseID != locator.getM_Warehouse_ID()){
					MWarehouse wHouse = new MWarehouse(poDocSource.getCtx(), mWarehouseID, null);
					locatorAuxID = wHouse.getDefaultLocator().getM_Locator_ID();
				}
				movStock.setM_Warehouse_ID(mWarehouseID);
				movStock.setM_Locator_ID(locatorAuxID);
			}

			// Me aseguro de no dejar pasar un movimiento de stock sin warehouse, locator o unidad de medida
			if (movStock.getM_Warehouse_ID() <= 0) {
				result = "Error al Crear Movimiento de Stock :\n" + "No se indica Almacen para el Producto : " + prod.getValue();
				return result;
			}
			if (movStock.getM_Locator_ID() <= 0) {
				result = "Error al Crear Movimiento de Stock :\n" + "No se indica Ubicacion para el Producto : " + prod.getValue();
				return result;
			}
			if (movStock.getC_UOM_ID() <= 0) {
				result = "Error al Crear Movimiento de Stock :\n" + "No se indica Unidad de Medida Minima para el Producto : " + prod.getValue();
				return result;
			}
			
			// Trato de obtener socio de negocio desde el documento origen.
			if (poDocSource.get_ValueAsInt(X_C_BPartner.COLUMNNAME_C_BPartner_ID) > 0)
				movStock.setC_BPartner_ID(poDocSource.get_ValueAsInt(X_C_BPartner.COLUMNNAME_C_BPartner_ID));

			// Datos del documento origen (todos ya fueron previamente verificados)
			movStock.setC_DocType_ID(cDocTypeID);
			movStock.setAD_Table_ID(adTableID);
			movStock.setRecord_ID(recordID);
			movStock.setDocumentNo(documentNo);
			if (lineID > 0){
				movStock.setLine_ID(lineID);
			}
			
			// Si tengo datos de costos los guardo
			if (stkCostInfo != null){
				movStock.setC_Currency_ID(stkCostInfo.cCurrencyID);
				movStock.setCurrencyRate(stkCostInfo.currencyRate);
				movStock.setAmtSource(stkCostInfo.amtSource);
				movStock.setAmtAcct(stkCostInfo.amtAcct);
			}
			
			// Datos del documento a afectar (todos ya fueron previamente verificados)
			movStock.setc_doctype_affected_id(cDocTypeAffectedID);
			movStock.setad_table_affected_id(adTableAffectedID);
			movStock.setrecord_affected_id(recordAffectedID);
			movStock.setdocumentno_affected(documentNoAffected);
			
			// Accion del documento
			movStock.setDocAction(poDocSource.get_ValueAsString(COLUMNNAME_DocAction));
			
			// Insert DB
			movStock.saveEx(poDocSource.get_TrxName());
			
			// Verificacion de stock negativo
			result = MStockTransaction.checkNegativeStock(movStock.getM_Warehouse_ID(), movStock.getM_Locator_ID(), 
						movStock.getM_Product_ID(), movStock.getM_AttributeSetInstance_ID(), movStock.getUY_StockStatus_ID(), 
						movStock.getMovementDate(), (movStock.getMovementQty().multiply(movStock.getsign())), poDocSource.get_TrxName());

			
			// OpenUp. Gabriel Vila. 15/07/2014. Issue #1405
			// Terminado el impacto en la tabla de stock, ahora proceso los consumos de stock por partidas
			if (MSysConfig.getBooleanValue("UY_STOCK_HANDLE_CONSUME", false, poDocSource.getAD_Client_ID())){
				result = MStockConsume.executeConsume(movStock);
			}
			// Fin OpenUp. Issue #1405.
			
		}
		catch (Exception ex)
		{
			result = ex.getMessage();
		}
		
		if (result != null){
			if (result.equalsIgnoreCase("")) result = null;	
		}
		
		return result;
	}

	/**
	 * OpenUp. Gabriel Vila. 13/06/2011. Issue #719.
	 * Metodo que obtiene stock disponible segun parametros recibidos. 
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param uyAvailableMethodID
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getQtyAvailable(int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, int uyAvailableMethodID, Timestamp dateTo, String trxName){
		
		BigDecimal value = Env.ZERO;

		// Si viene un id en CERO lo tengo que enviar como null a la function de postgres
		Integer warehouse_id = null, locator_id = null, product_id = null, asi_id = null;
		if (mWarehouseID > 0) warehouse_id = mWarehouseID;
		if (mLocatorID > 0) locator_id = mLocatorID;
		if (mProductID > 0) product_id = mProductID;
		if (mAttributeSetInstanceID > 0) asi_id = mAttributeSetInstanceID;
		
		String sql = "SELECT stk_available(?,?,?,?,?,?)";
		value = DB.getSQLValueBD(trxName, sql, new Object[]{product_id, warehouse_id, locator_id, asi_id, uyAvailableMethodID, dateTo});
		
		return value;
	}
	
	/**
	 * OpenUp. Gabriel Vila. 14/06/2011. Issue #719.
	 * Metodo que obtiene stock aprobado segun parametros recibidos.
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param uyAvailableMethodID
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getQtyApproved(int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, Timestamp dateTo, String trxName){

		BigDecimal value = Env.ZERO;
		
		// Si viene un id en CERO lo tengo que enviar como null a la function de postgres
		Integer warehouse_id = null, locator_id = null, product_id = null, asi_id = null;
		if (mWarehouseID > 0) warehouse_id = mWarehouseID;
		if (mLocatorID > 0) locator_id = mLocatorID;
		if (mProductID > 0) product_id = mProductID;
		if (mAttributeSetInstanceID > 0) asi_id = mAttributeSetInstanceID;
		
		String sql = "SELECT stk_approved(?,?,?,?,?,?)";
		value = DB.getSQLValueBD(trxName, sql, new Object[]{product_id, warehouse_id, locator_id, asi_id, 0, dateTo});
		
		return value;
	}
	
	/**
	 * OpenUp. Gabriel Vila. 14/06/2011. Issue #719.
	 * Metodo que obtiene stock reservado segun parametros recibidos.
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param uyAvailableMethodID
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getQtyReserved(int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, Timestamp dateTo, String trxName){

		BigDecimal value = Env.ZERO;
		
		// Si viene un id en CERO lo tengo que enviar como null a la function de postgres
		Integer warehouse_id = null, locator_id = null, product_id = null, asi_id = null;
		if (mWarehouseID > 0) warehouse_id = mWarehouseID;
		if (mLocatorID > 0) locator_id = mLocatorID;
		if (mProductID > 0) product_id = mProductID;
		if (mAttributeSetInstanceID > 0) asi_id = mAttributeSetInstanceID;
		
		String sql = "SELECT stk_reserved(?,?,?,?,?,?)";
		value = DB.getSQLValueBD(trxName, sql, new Object[]{product_id, warehouse_id, locator_id, asi_id, 0, dateTo});
		
		return value;
	}	
	
	/**
	 * OpenUp. Gabriel Vila. 14/06/2011. Issue #719.
	 * Metodo que obtiene stock ordenado segun parametros recibidos.
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param uyAvailableMethodID
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getQtyOrdered(int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, Timestamp dateTo, String trxName){

		BigDecimal value = Env.ZERO;
		
		// Si viene un id en CERO lo tengo que enviar como null a la function de postgres
		Integer warehouse_id = null, locator_id = null, product_id = null, asi_id = null;
		if (mWarehouseID > 0) warehouse_id = mWarehouseID;
		if (mLocatorID > 0) locator_id = mLocatorID;
		if (mProductID > 0) product_id = mProductID;
		if (mAttributeSetInstanceID > 0) asi_id = mAttributeSetInstanceID;
		
		String sql = "SELECT stk_ordered(?,?,?,?,?,?)";
		value = DB.getSQLValueBD(trxName, sql, new Object[]{product_id, warehouse_id, locator_id, asi_id, 0, dateTo});
		
		return value;
	}
	
	/**
	 * OpenUp. Gabriel Vila. 14/06/2011. Issue #719.
	 * Metodo que verifica si hay stock suficiente para cubrir con una cantidad
	 * recibida.
	 * @param qtyNeeded
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param uyAvailableMethodID
	 * @param trxName
	 * @return
	 */
	public static boolean haveSufficientStock(BigDecimal qtyNeeded, int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, int uyAvailableMethodID, Timestamp dateTo, String trxName){
		
		if (qtyNeeded == null) qtyNeeded = Env.ZERO;
		
		BigDecimal qtyAvailable = MStockTransaction.getQtyAvailable(mWarehouseID, mLocatorID, mProductID, mAttributeSetInstanceID, uyAvailableMethodID, dateTo, trxName);
		return (qtyAvailable.compareTo(qtyNeeded) >= 0);
	}
	
	/**
	 * OpenUp. Gabriel Vila. 14/06/2011. Issue #719.
	 * Metodo que obtiene stock fisico segun parametros recibidos.
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getQtyPhysical(int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, Timestamp dateTo, String trxName){

		BigDecimal value = Env.ZERO;
		
		// Si viene un id en CERO lo tengo que enviar como null a la function de postgres
		Integer warehouse_id = null, locator_id = null, product_id = null, asi_id = null;
		if (mWarehouseID > 0) warehouse_id = mWarehouseID;
		if (mLocatorID > 0) locator_id = mLocatorID;
		if (mProductID > 0) product_id = mProductID;
		if (mAttributeSetInstanceID > 0) asi_id = mAttributeSetInstanceID;
		
		String sql = "SELECT stk_physical(?,?,?,?,?,?)";
		value = DB.getSQLValueBD(trxName, sql, new Object[]{product_id, warehouse_id, locator_id, asi_id, 0, dateTo});
		
		return value;
	}

	/**
	 * OpenUp. Gabriel Vila. 16/06/2011. Issue #719.
	 * Metodo que actuliza numero de documento de un determinado comprobante en 
	 * la tabla de movimientos de stock.
	 * @param model
	 * @param newDocumentNo
	 */
	public static void  updateDocumentNo(PO model, String newDocumentNo, String trxName){
		
		try{
			String action = " UPDATE UY_StockTransaction " +
					" SET DocumentNo ='" + newDocumentNo + "', " +
					" DocumentNo_Affected ='" + newDocumentNo + "'" +
					" WHERE ad_table_id =" + model.get_Table_ID() +
					" AND record_id =" + model.get_ID();
			DB.executeUpdateEx(action, trxName);
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	/**
	 * OpenUp. Gabriel Vila. 28/06/2011. Issue #719.
	 * Metodo que obtiene stock pendiente segun parametros recibidos.
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param uyAvailableMethodID
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getQtyPending(int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, Timestamp dateTo, String trxName){

		BigDecimal value = Env.ZERO;
		
		// Si viene un id en CERO lo tengo que enviar como null a la function de postgres
		Integer warehouse_id = null, locator_id = null, product_id = null, asi_id = null;
		if (mWarehouseID > 0) warehouse_id = mWarehouseID;
		if (mLocatorID > 0) locator_id = mLocatorID;
		if (mProductID > 0) product_id = mProductID;
		if (mAttributeSetInstanceID > 0) asi_id = mAttributeSetInstanceID;
		
		String sql = "SELECT stk_pending(?,?,?,?,?,?)";
		value = DB.getSQLValueBD(trxName, sql, new Object[]{product_id, warehouse_id, locator_id, asi_id, 0, dateTo});
		
		return value;
	}	

	
	/**
	 * OpenUp. Gabriel Vila. 28/06/2011. Issue #719.
	 * Metodo que obtiene stock en cuarentena segun parametros recibidos.
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param uyAvailableMethodID
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getQtyQuarantine(int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, Timestamp dateTo, String trxName){

		BigDecimal value = Env.ZERO;
		
		// Si viene un id en CERO lo tengo que enviar como null a la function de postgres
		Integer warehouse_id = null, locator_id = null, product_id = null, asi_id = null;
		if (mWarehouseID > 0) warehouse_id = mWarehouseID;
		if (mLocatorID > 0) locator_id = mLocatorID;
		if (mProductID > 0) product_id = mProductID;
		if (mAttributeSetInstanceID > 0) asi_id = mAttributeSetInstanceID;
		
		String sql = "SELECT stk_quarantine(?,?,?,?,?,?)";
		value = DB.getSQLValueBD(trxName, sql, new Object[]{product_id, warehouse_id, locator_id, asi_id, 0, dateTo});
		
		return value;
	}
	
	
	/**
	 * OpenUp. Gabriel Vila. 28/06/2011. Issue #719.
	 * Metodo que obtiene stock bloqueado segun parametros recibidos.
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param uyAvailableMethodID
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getQtyLocked(int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, Timestamp dateTo, String trxName){

		BigDecimal value = Env.ZERO;
		
		// Si viene un id en CERO lo tengo que enviar como null a la function de postgres
		Integer warehouse_id = null, locator_id = null, product_id = null, asi_id = null;
		if (mWarehouseID > 0) warehouse_id = mWarehouseID;
		if (mLocatorID > 0) locator_id = mLocatorID;
		if (mProductID > 0) product_id = mProductID;
		if (mAttributeSetInstanceID > 0) asi_id = mAttributeSetInstanceID;
		
		String sql = "SELECT stk_locked(?,?,?,?,?,?)";
		value = DB.getSQLValueBD(trxName, sql, new Object[]{product_id, warehouse_id, locator_id, asi_id, 0, dateTo});
		
		return value;
	}

	/**
	 * OpenUp. Gabriel Vila. 13/07/2011. Issue #746.
	 * Metodo que obtiene stock en libros a una determinada fecha. 
	 * @param mProductID
	 * @param mLocatorID
	 * @param mAttributeSetInstanceID
	 * @param dateTo
	 * @param trxName
	 * @return
	 * @author  mario 
	 * Fecha : 13/07/2011
	 */
	public static BigDecimal getQtyBook(int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, Timestamp dateTo, String trxName){
		
		BigDecimal value = Env.ZERO;
		
		// Si viene un id en CERO lo tengo que enviar como null a la function de postgres
		Integer warehouse_id = null, locator_id = null, product_id = null, asi_id = null;
		if (mLocatorID > 0) locator_id = mLocatorID;
		if (mProductID > 0) product_id = mProductID;
		if (mAttributeSetInstanceID > 0) asi_id = mAttributeSetInstanceID;
		
		String sql = "SELECT stk_book(?,?,?,?,?,?)";
		value = DB.getSQLValueBD(trxName, sql, new Object[]{product_id, warehouse_id, locator_id, asi_id, 0, dateTo});
		
		return value;
	}

	/**
	 * OpenUp. Issue #1010.
	 * Metodo que obtiene stock por estado a una determinada fecha. 
	 * @param mProductID
	 * @param mLocatorID
	 * @param mAttributeSetInstanceID
	 * @param dateTo
	 * @param trxName
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 26/04/2012
	 */
	public static BigDecimal getQtyByStatus(int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, Timestamp dateTo, int stockStatusID, String trxName){
		
		BigDecimal value = Env.ZERO;
		
		// Si viene un id en CERO lo tengo que enviar como null a la function de postgres
		Integer warehouse_id = null, locator_id = null, product_id = null, asi_id = null, stockStatus_id = null;
		if (mLocatorID > 0) locator_id = mLocatorID;
		if (mProductID > 0) product_id = mProductID;
		if (stockStatusID > 0) stockStatus_id = stockStatusID;
		if (mAttributeSetInstanceID > 0) asi_id = mAttributeSetInstanceID;
		
		String sql = "SELECT stk_bystatus(?,?,?,?,?,?,?)";
		value = DB.getSQLValueBD(trxName, sql, new Object[]{product_id, warehouse_id, locator_id, asi_id, 0, dateTo,stockStatus_id});
		
		return value;
	}
	
	/**
	 * OpenUp. Gabriel Vila. 19/07/2011. Issue #748.
	 * Verifica si para un determinado almacen-ubicacion-producto, el stock queda
	 * negativo o no. 
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param dateTo
	 * @param trxName
	 * @return String. Null si todo bien o mensaje de inconsistencia.
	 */
	public static String checkNegativeStock(int mWarehouseID, int mLocatorID, int mProductID, 
			int mAttributeSetInstanceID, int stockStatusID, Timestamp dateTo, 
			BigDecimal movementQty, String trxName){

		String result = "", resultAux = "";
		String sql = "";
		
		if (mProductID <= 0) return "";
		
		if (dateTo == null){
			resultAux = "No se indica Fecha a considerar para verificacion de stock negativo.";
			result += ((result.equalsIgnoreCase("")) ?  resultAux : ("\n" + resultAux));
		}
		if (stockStatusID <= 0){
			resultAux = "No se indica Estado de Stock a considerar para verificacion de stock negativo.";
			result += ((result.equalsIgnoreCase("")) ?  resultAux : ("\n" + resultAux));
		}
		if (!result.equalsIgnoreCase("")){
			result = "Error en verificacion de Stock Negativo :\n" + result;
			return result;
		}
		
		// Instancio modelo de producto
		MProduct prod = new MProduct(Env.getCtx(), mProductID, null);
		
		// Si el producto no es stockeable, no hago nada
		if (!prod.isStocked()) return "";	
		
		try{
			// Si recibo warehouse y no recibo locator, considero locator por defecto del warehouse
			if ((mWarehouseID > 0) && (mLocatorID <= 0)){
				MWarehouse wHouse = new MWarehouse(Env.getCtx(), mWarehouseID, null);
				mLocatorID = wHouse.getDefaultLocator().getM_Locator_ID();
			}
			// Si no recibo warehouse y recibo locator, considero warehouse asociado al locator
			else if ((mWarehouseID <= 0) && (mLocatorID > 0)){
				MLocator locator = new MLocator(Env.getCtx(), mLocatorID, null);
				mWarehouseID = locator.getM_Warehouse_ID();
			}
			// Si recibo ambos, valido que el locator se corresponda con el warehouse, 
			// y si no se corresponde tomo el locator por defecto del warehouse
			else if ((mWarehouseID > 0) && (mLocatorID > 0)){
				MLocator locator = new MLocator(Env.getCtx(), mLocatorID, null);
				if (mWarehouseID != locator.getM_Warehouse_ID()){
					MWarehouse wHouse = new MWarehouse(Env.getCtx(), mWarehouseID, null);
					mLocatorID = wHouse.getDefaultLocator().getM_Locator_ID();
				}
			}

			// Me aseguro de tener warehouse y locator para la validacion
			if (mWarehouseID <= 0) {
				result = "Error al Verificar Stock Negativo :\n" + "No se indica Almacen para el Producto : " + prod.getValue();
				return result;
			}
			if (mLocatorID <= 0) {
				result = "Error al Verificar Stock Negativo :\n" + "No se indica Ubicacion para el Producto : " + prod.getValue();
				return result;
			}

			MStockStatus sts = new MStockStatus(Env.getCtx(), stockStatusID, trxName);
			MWarehouse war = new MWarehouse(Env.getCtx(), mWarehouseID, trxName);
			MLocator locator = new MLocator(Env.getCtx(), mLocatorID, trxName);
			X_UY_StockStatusType stsType = new X_UY_StockStatusType(Env.getCtx(), sts.getUY_StockStatusType_ID(), trxName);
			
			// Verifico parametrizaciones de estado de stock y almacen con respecto a permitir o no stock negativo
			if ((stsType.isAllowNegativeStock()) && (war.isAllowNegativeStock())) return "";
			
			// Verifico stock negativo en almacen-ubicacion-producto
			sql = "SELECT stk_bystatus(?,?,?,?,?,?,?) ";
			BigDecimal value = DB.getSQLValueBD(trxName, sql, new Object[]{mProductID, mWarehouseID, 
							   mLocatorID, null, null, dateTo, stockStatusID});

			if (value.compareTo(Env.ZERO) < 0) 
				return "Validacion de Stock Negativo :\n" + 
					   "Almacen      : " + war.getName() + "\n" +
					   "Ubicacion    : " + locator.getValue() + "\n" +
					   "Producto     : " + prod.getValue() + "_" + prod.getName() + "\n" +
					   "Estado Stock : " + sts.getName() + "\n" +
					   "Cantidad a Mover : " + movementQty.toString() + "\n" +
					   "Saldo final de continuar con esta transaccion : " + value.toString();

			// Verifico stock negativo en almacen-producto
			sql = "SELECT stk_bystatus(?,?,?,?,?,?,?) ";
			value = DB.getSQLValueBD(trxName, sql, new Object[]{mProductID, mWarehouseID, 
							   null, null, null, dateTo, stockStatusID});

			if (value.compareTo(Env.ZERO) < 0) 
				return "Validacion de Stock Negativo :\n" + 
					   "Almacen      : " + war.getName() + "\n" +
					   "Producto     : " + prod.getValue() + "_" + prod.getName() + "\n" +
					   "Estado Stock : " + sts.getName() + "\n" +
					   "Cantidad a Mover : " + movementQty.toString() + "\n" +
					   "Saldo final de continuar con esta transaccion : " + value.toString();
		}
		catch (Exception ex)
		{
			result = ex.getMessage();
		}

		return result;
	}

	/**
	 * OpenUp. Gabriel Vila. 26/10/2011. Issue #748, #146, #883.
	 * Verifica disponibilidad de stock para un determinado almacen-ubicacion-producto. 
	 * @param mWarehouseID
	 * @param mLocatorID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param dateTo
	 * @param trxName
	 * @return String. Null si todo bien o mensaje de inconsistencia.
	 */
	public static String checkAvailability(int mWarehouseID, int mLocatorID, int mProductID, int mAttributeSetInstanceID, 
                                           Timestamp dateTo, int adClientID, String trxName) {

		String result = "", resultAux = "";
		String sql = "";

		if (!MSysConfig.getBooleanValue("UY_STOCK_CHECK_AVAILABLE", true, adClientID)) return null;
		
		if (mProductID <= 0) return null;
		
		if (dateTo == null){
			resultAux = "No se indica Fecha a considerar para verificacion de disponibilidad de stock.";
			result += ((result.equalsIgnoreCase("")) ?  resultAux : ("\n" + resultAux));
		}
		if (!result.equalsIgnoreCase("")){
			result = "Error en verificacion de disponibilidad de stock :\n" + result;
			return result;
		}
		
		// Instancio modelo de producto
		MProduct prod = new MProduct(Env.getCtx(), mProductID, null);
		
		// Si el producto no es stockeable, no hago nada
		if (!prod.isStocked()) return null;	
		
		try{
			// Si recibo warehouse y no recibo locator, considero locator por defecto del warehouse
			if ((mWarehouseID > 0) && (mLocatorID <= 0)){
				MWarehouse wHouse = new MWarehouse(Env.getCtx(), mWarehouseID, null);
				mLocatorID = wHouse.getDefaultLocator().getM_Locator_ID();
			}
			// Si no recibo warehouse y recibo locator, considero warehouse asociado al locator
			else if ((mWarehouseID <= 0) && (mLocatorID > 0)){
				MLocator locator = new MLocator(Env.getCtx(), mLocatorID, null);
				mWarehouseID = locator.getM_Warehouse_ID();
			}
			// Si recibo ambos, valido que el locator se corresponda con el warehouse, 
			// y si no se corresponde tomo el locator por defecto del warehouse
			else if ((mWarehouseID > 0) && (mLocatorID > 0)){
				MLocator locator = new MLocator(Env.getCtx(), mLocatorID, null);
				if (mWarehouseID != locator.getM_Warehouse_ID()){
					MWarehouse wHouse = new MWarehouse(Env.getCtx(), mWarehouseID, null);
					mLocatorID = wHouse.getDefaultLocator().getM_Locator_ID();
				}
			}

			// Me aseguro de tener warehouse y locator para la validacion
			if (mWarehouseID <= 0) {
				result = "Error al Verificar disponibilidad de stock :\n" + "No se indica Almacen para el Producto : " + prod.getValue();
				return result;
			}
			if (mLocatorID <= 0) {
				result = "Error al Verificar disponibilidad de stock :\n" + "No se indica Ubicacion para el Producto : " + prod.getValue();
				return result;
			}

			MWarehouse war = new MWarehouse(Env.getCtx(), mWarehouseID, trxName);
			MLocator locator = new MLocator(Env.getCtx(), mLocatorID, trxName);
			
			// Verifico disponibilidad de stock en almacen-ubicacion-producto
			sql = "SELECT stk_available(?,?,?,?,?,?) ";
			BigDecimal value = DB.getSQLValueBD(trxName, sql, new Object[]{mProductID, mWarehouseID, 
							   mLocatorID, null, null, dateTo});

			if (value.compareTo(Env.ZERO) < 0) 
				return "Validacion de Disponibilidad de Stock :\n" + 
					   "Almacen      : " + war.getName() + "\n" +
					   "Ubicacion    : " + locator.getValue() + "\n" +
					   "Producto     : " + prod.getValue() + "_" + prod.getName() + "\n" +
					   "Disponible final de continuar con esta transaccion : " + value.toString();

			// Verifico disponibilidad en almacen-producto
			sql = "SELECT stk_available(?,?,?,?,?,?) ";
			value = DB.getSQLValueBD(trxName, sql, new Object[]{mProductID, mWarehouseID, 
							   null, null, null, dateTo});

			if (value.compareTo(Env.ZERO) < 0) 
				return "Validacion de Disponibilidad de Stock :\n" + 
					   "Almacen      : " + war.getName() + "\n" +
					   "Producto     : " + prod.getValue() + "_" + prod.getName() + "\n" +
					   "Disponible final de continuar con esta transaccion : " + value.toString();
		}
		catch (Exception ex)
		{
			result = ex.getMessage();
		}

		if (result.equalsIgnoreCase("")) result = null;

		return result;
	}
}
