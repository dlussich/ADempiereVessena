/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 11/07/2014
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.openup.beans.StockCost;
import org.openup.beans.StockIdentification;

/**
 * org.openup.model - MStockConsume
 * OpenUp Ltda. Issue #1405 
 * Description: Modelo para cabezal en trazabilidad de consumos de stock.
 * @author Gabriel Vila - 11/07/2014
 * @see
 */
public class MStockConsume extends X_UY_StockConsume {


	private static final long serialVersionUID = -5439751022277365452L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_StockConsume_ID
	 * @param trxName
	 */
	public MStockConsume(Properties ctx, int UY_StockConsume_ID, String trxName) {
		super(ctx, UY_StockConsume_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MStockConsume(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Proceso informacion para consumo de partidas de stock.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 20/07/2014
	 * @see
	 * @param stockTransaction
	 * @return
	 */
	public static String executeConsume(MStockTransaction stockTransaction) {
		
		String result = null;
	
		try {
			
			if (!MSysConfig.getBooleanValue("UY_STOCK_HANDLE_CONSUME", false, stockTransaction.getAD_Client_ID())){
				return null;
			}
			
			// Verifico si el estado de stock que se esta moviendo, esta parametrizado para manejar partidas y consumos de stock.
			MStockStatus stksts = (MStockStatus)stockTransaction.getUY_StockStatus();
			if (!stksts.isStockConsume()){
				return null;
			}

			// Verifico que el documento origen de este movimiento de stock, esta parametrizado para manejar partidas y 
			// consumos de stock segun signo del movimiento
			MDocType doc = (MDocType)stockTransaction.getC_DocType();
			MStockStatusByDocHdr stkdoc = MStockStatusByDocHdr.get(doc);
			if (stkdoc == null){
				return null;
			}
			
			// Santiago Evans 23/02/2016 Issue #5182
			// Se usa esta variable para controlar se se agrega o reduce stock 
			String consumeType;
			
			// Si es signo positivo									 
			if (stockTransaction.getsign().compareTo(Env.ONE) == 0){
				if (stkdoc.getConsumeTypeAdd().equalsIgnoreCase(X_UY_StockStatusByDocHdr.CONSUMETYPEADD_NINGUNO)){
					return null;
				}
				consumeType = stkdoc.getConsumeTypeAdd(); 
			}
			else{
				// Signo negativo
				if (stkdoc.getConsumeTypeSub().equalsIgnoreCase(X_UY_StockStatusByDocHdr.CONSUMETYPESUB_NINGUNO)){
					return null;
				}
				consumeType = stkdoc.getConsumeTypeSub();
			}

			// Si este movimiento genera una nueva partida para consumo de stock
			if (consumeType.equalsIgnoreCase(X_UY_StockStatusByDocHdr.CONSUMETYPEADD_GENERANUEVAPARTIDA)){

				MStockConsume stkConsume = new MStockConsume(stockTransaction.getCtx(), 0, stockTransaction.get_TrxName());
				stkConsume.setMovementDate(stockTransaction.getMovementDate());
				stkConsume.setMovementQty(stockTransaction.getMovementQty());
				stkConsume.setC_Period_ID(stockTransaction.getC_Period_ID());
				stkConsume.setM_Warehouse_ID(stockTransaction.getM_Warehouse_ID());
				stkConsume.setM_Locator_ID(stockTransaction.getM_Locator_ID());
				stkConsume.setUY_StockStatus_ID(stockTransaction.getUY_StockStatus_ID());
				stkConsume.setM_Product_ID(stockTransaction.getM_Product_ID());
				if (stockTransaction.getM_AttributeSetInstance_ID() > 0){
					stkConsume.setM_AttributeSetInstance_ID(stockTransaction.getM_AttributeSetInstance_ID());
				}
				if (stockTransaction.getC_BPartner_ID() > 0){
					stkConsume.setC_BPartner_ID(stockTransaction.getC_BPartner_ID());
				}
				stkConsume.setC_DocType_ID(stockTransaction.getC_DocType_ID());
				stkConsume.setDocumentNo(stockTransaction.getDocumentNo());
				stkConsume.setAD_Table_ID(stockTransaction.getAD_Table_ID());
				stkConsume.setRecord_ID(stockTransaction.getRecord_ID());
				stkConsume.setUY_StockTransaction_ID(stockTransaction.get_ID());
				stkConsume.setqtyopen(stkConsume.getMovementQty());
				stkConsume.setqtyallocated(Env.ZERO);
				if (stockTransaction.getLine_ID() > 0){
					stkConsume.setLine_ID(stockTransaction.getLine_ID());	
				}
				if (stockTransaction.getC_Currency_ID() > 0){
					stkConsume.setC_Currency_ID(stockTransaction.getC_Currency_ID());
				}
				stkConsume.setCurrencyRate(stockTransaction.getCurrencyRate());
				stkConsume.setAmtSource(stockTransaction.getAmtSource());
				stkConsume.setAmtAcct(stockTransaction.getAmtAcct());
				
				stkConsume.saveEx();
			}
			
			// Si se genera un consumo FIFO de una partida existente o tambien ademas de consumir se genera nueva partido por consumo
			else if ((consumeType.equalsIgnoreCase(X_UY_StockStatusByDocHdr.CONSUMETYPEADD_CONSUMEPARTIDAFIFO))
					 || (consumeType.equalsIgnoreCase(X_UY_StockStatusByDocHdr.CONSUMETYPEADD_NUEVAPARTIDACONTRACONSUMOFIFO))){
				
				
				// Obtengo partidas a consumir ordenadas FIFO
				List<MStockConsume> consumes = MStockConsume.getToConsumeFIFO(stockTransaction.getCtx(), stockTransaction.getMovementDate(),
						stockTransaction.getM_Warehouse_ID(), stockTransaction.getM_Locator_ID(), stockTransaction.getUY_StockStatus_ID(), 
						stockTransaction.getM_Product_ID(),	stockTransaction.getM_AttributeSetInstance_ID(), stockTransaction.get_TrxName());

				// Recorro lista y voy consumiendo el stock hasta agotarlo
				BigDecimal qtyToConsume = stockTransaction.getMovementQty();
				for (MStockConsume consume: consumes){

					if (qtyToConsume.compareTo(Env.ZERO) > 0){

						BigDecimal qtyLine = Env.ZERO;
						
						// Seteo cantidad a consumir y refresco cuanto me queda 
						if (consume.getqtyopen().compareTo(qtyToConsume) >= 0){
							qtyLine = qtyToConsume;
							qtyToConsume = Env.ZERO;
						}
						else{
							qtyLine = consume.getqtyopen();
							qtyToConsume = qtyToConsume.subtract(qtyLine);
						}					

						// Consumo partida
						consume.setqtyallocated(consume.getqtyallocated().add(qtyLine));
						consume.setqtyopen(consume.getqtyopen().subtract(qtyLine));
						consume.saveEx();

						// Guardo linea de consumo
						MStockConsumeLine consLine = new MStockConsumeLine(stockTransaction.getCtx(), 0, stockTransaction.get_TrxName());
						consLine.setUY_StockConsume_ID(consume.get_ID());
						consLine.setMovementDate(stockTransaction.getMovementDate());
						consLine.setMovementQty(qtyLine);
						consLine.setC_Period_ID(stockTransaction.getC_Period_ID());
						if (stockTransaction.getC_BPartner_ID() > 0){
							consLine.setC_BPartner_ID(stockTransaction.getC_BPartner_ID());	
						}
						consLine.setC_DocType_ID(stockTransaction.getC_DocType_ID());
						consLine.setDocumentNo(stockTransaction.getDocumentNo());
						consLine.setAD_Table_ID(stockTransaction.getAD_Table_ID());
						consLine.setRecord_ID(stockTransaction.getRecord_ID());
						consLine.setUY_StockTransaction_ID(stockTransaction.get_ID());
						if (stockTransaction.getLine_ID() > 0){
							consLine.setLine_ID(stockTransaction.getLine_ID());	
						}
						
						consLine.saveEx();

						// Si ademas de consumir FIFO debo generar nueva partida contra consumo por este registro
						if (consumeType.equalsIgnoreCase(X_UY_StockStatusByDocHdr.CONSUMETYPEADD_NUEVAPARTIDACONTRACONSUMOFIFO)){

							MStockConsume stkConsume = new MStockConsume(stockTransaction.getCtx(), 0, stockTransaction.get_TrxName());
							stkConsume.setMovementDate(stockTransaction.getMovementDate());
							stkConsume.setMovementQty(qtyLine);
							stkConsume.setC_Period_ID(stockTransaction.getC_Period_ID());
							stkConsume.setM_Warehouse_ID(stockTransaction.getM_Warehouse_ID());
							stkConsume.setM_Locator_ID(stockTransaction.getM_Locator_ID());
							stkConsume.setUY_StockStatus_ID(stockTransaction.getUY_StockStatus_ID());
							stkConsume.setM_Product_ID(stockTransaction.getM_Product_ID());
							if (stockTransaction.getM_AttributeSetInstance_ID() > 0){
								stkConsume.setM_AttributeSetInstance_ID(stockTransaction.getM_AttributeSetInstance_ID());
							}
							if (stockTransaction.getC_BPartner_ID() > 0){
								stkConsume.setC_BPartner_ID(stockTransaction.getC_BPartner_ID());
							}
							stkConsume.setC_DocType_ID(stockTransaction.getC_DocType_ID());
							stkConsume.setDocumentNo(stockTransaction.getDocumentNo());
							stkConsume.setAD_Table_ID(stockTransaction.getAD_Table_ID());
							stkConsume.setRecord_ID(stockTransaction.getRecord_ID());
							stkConsume.setUY_StockTransaction_ID(stockTransaction.get_ID());
							stkConsume.setqtyopen(stkConsume.getMovementQty());
							stkConsume.setqtyallocated(Env.ZERO);
							if (stockTransaction.getLine_ID() > 0){
								stkConsume.setLine_ID(stockTransaction.getLine_ID());	
							}
							
							// Los costos los toma de la partida que consume
							if (consume.getC_Currency_ID() > 0){
								stkConsume.setC_Currency_ID(consume.getC_Currency_ID());
							}
							stkConsume.setCurrencyRate(consume.getCurrencyRate());
							stkConsume.setAmtSource(consume.getAmtSource());
							stkConsume.setAmtAcct(consume.getAmtAcct());
							
							stkConsume.saveEx();
							
						}
							
					} 
					
				} // for

			} 
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return result;
	}

	
	/***
	 * Obtiene y retorna una partida a consumir segun criterio recibido y ordenado FIFO.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 20/07/2014
	 * @see
	 * @param ctx
	 * @param movementDate
	 * @param mWareHouseID
	 * @param mLocatorID
	 * @param uyStockStatusID
	 * @param mProductID
	 * @param mAttributeSetInstanceID
	 * @param trxName
	 * @return
	 */
	public static List<MStockConsume> getToConsumeFIFO(Properties ctx, Timestamp movementDate, int mWareHouseID, 
			int mLocatorID, int uyStockStatusID, int mProductID, int mAttributeSetInstanceID, String trxName){
		
		String whereClause = " m_warehouse_id =" + mWareHouseID +
							 " and m_locator_id =" + mLocatorID +
							 " and uy_stockstatus_id =" + uyStockStatusID +
							 " and m_product_id =" + mProductID +
							 " and movementdate <='" + movementDate + "' " +
							 " and coalesce(qtyopen,0) > 0 ";
		
		List<MStockConsume> lines = new Query(ctx, I_UY_StockConsume.Table_Name, whereClause, trxName)
		.setOrderBy(" movementdate asc ")
		.list();
		
		return lines;
		
	}
		
	
	/***
	 * Actualiza costos de partida y ficha de stock para un determinado registro. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 24/07/2014
	 * @see
	 * @param ctx
	 * @param adClientID
	 * @param adTableID
	 * @param lineID
	 * @param stkCostInfo
	 * @param trxName
	 * @return
	 */
	public static String updateConsumeCost(Properties ctx, int adClientID, int adTableID, int lineID, StockCost stkCostInfo, String trxName){
		
		String result = null;
		
		try {
			
			if (!MSysConfig.getBooleanValue("UY_STOCK_HANDLE_CONSUME", false, adClientID)){
				return null;
			}
			
			// Obtengo partida segun parametros recibidos
			String whereClause = "ad_table_id =" + adTableID + " and line_id =" + lineID;
			MStockConsume stkConsume = new Query(ctx, I_UY_StockConsume.Table_Name, whereClause, trxName).first();
			
			// Si no obtuve nada salgo sin error por ahora
			if ((stkConsume == null) || (stkConsume.get_ID() <= 0)){
				return null;
			}
			
			// Actualizo partida
			stkConsume.setC_Currency_ID(stkCostInfo.cCurrencyID);
			stkConsume.setCurrencyRate(stkCostInfo.currencyRate);
			stkConsume.setAmtAcct(stkCostInfo.amtAcct);
			stkConsume.setAmtSource(stkCostInfo.amtSource);
			stkConsume.saveEx();
			
			// Obtengo y actualizo ficha de stock
			MStockTransaction stkTrans = (MStockTransaction)stkConsume.getUY_StockTransaction();
			if ((stkTrans != null) && (stkTrans.get_ID() > 0)){
				stkTrans.setC_Currency_ID(stkCostInfo.cCurrencyID);
				stkTrans.setCurrencyRate(stkCostInfo.currencyRate);
				stkTrans.setAmtAcct(stkCostInfo.amtAcct);
				stkTrans.setAmtSource(stkCostInfo.amtSource);
				stkTrans.saveEx();
			}
			
		} 
		catch (Exception e) {
			result = e.getMessage();
		}
		
		return result;
		
	}
	
	/***
	 * Actualiza datos de ubicacion de partida para un determinado registro. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 24/07/2014
	 * @see
	 * @param ctx
	 * @param adClientID
	 * @param adTableID
	 * @param lineID
	 * @param stkCostInfo
	 * @param trxName
	 * @return
	 */
	public static String updateConsumeIdentificacion(Properties ctx, int adClientID, int adTableID, int lineID, StockIdentification stkIdent, String trxName){
		
		String result = null;
		
		try {
			
			if (!MSysConfig.getBooleanValue("UY_STOCK_HANDLE_CONSUME", false, adClientID)){
				return null;
			}
			
			// Obtengo partida segun parametros recibidos
			String whereClause = "ad_table_id =" + adTableID + " and line_id =" + lineID;
			List<MStockConsume> stkConsumes = new Query(ctx, I_UY_StockConsume.Table_Name, whereClause, trxName).list();
			
			// Si no obtuve nada salgo sin error por ahora
			if (stkConsumes == null){
				return null;
			}

			for (MStockConsume stkConsume: stkConsumes){

				// Actualizo partida
				stkConsume.setM_Warehouse_ID(stkIdent.mWarehouseID);
				stkConsume.setM_Locator_ID(stkIdent.mLocatorID);
				stkConsume.setUY_StockStatus_ID(stkIdent.uyStockStatusID);

				stkConsume.saveEx();
			}
			
		} 
		catch (Exception e) {
			result = e.getMessage();
		}
		
		return result;
		
	}
}
