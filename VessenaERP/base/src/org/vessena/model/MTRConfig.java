/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 10/09/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;

/**
 * org.openup.model - MTRConfig
 * OpenUp Ltda. Issue #1405 
 * Description: Modelo para configuracion general del modulo de transporte.
 * @author Gabriel Vila - 10/09/2014
 * @see
 */
public class MTRConfig extends X_UY_TR_Config {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4059509460308944924L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_Config_ID
	 * @param trxName
	 */
	public MTRConfig(Properties ctx, int UY_TR_Config_ID, String trxName) {
		super(ctx, UY_TR_Config_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	/***
	 * Obtiene y retorna modelo segun value recibido.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 10/09/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRConfig forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_TR_Config.COLUMNNAME_Value + "='" + value + "'";
		
		MTRConfig model = new Query(ctx, I_UY_TR_Config.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
	}
	
	/***
	 * Obtiene y retorna modelo segun compania recibida.
	 * OpenUp Ltda. Issue # 
	 * @author Nicolas Sarlabos - 31/12/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRConfig forClient(Properties ctx, String trxName){
		
		MTRConfig model = new Query(ctx, I_UY_TR_Config.Table_Name, null, trxName)
		.setClient_ID()
		.first();
		
		return model;
	}

	
	/***
	 * Obtiene y retorna modelo segun empresa recibida. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 01/01/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRConfig forClientID(Properties ctx, int adClientID, String trxName){
		
		String whereClause = X_UY_TR_Config.COLUMNNAME_AD_Client_ID + "=" + adClientID;
		
		MTRConfig model = new Query(ctx, I_UY_TR_Config.Table_Name, whereClause, trxName)
		.first();
		
		return model;
	}

	/***
	 * Obtiene y retorna producto para compra de flete en un vale flete.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 01/01/2015
	 * @see
	 * @param torder
	 * @return
	 */
	public int getProductValeFlete(MTRTransOrder torder) {

		int mProductID = 0;
		
		try {

			// Obtengo datos de parametrizacion
			MTRSuitability csuit = this.getConfigSuitability();
			MTRConfigVFlete cvflete = this.getConfigVFlete();
			
			// Modelo de ciudad origen de la orden de transporte
			MCiudad origen = new MCiudad(getCtx(), torder.getUY_Ciudad_ID(), null);
			
			// Si la empresa de esta configuracion no considera producto de vale flete segun CRTS
			if (!cvflete.isHandleCRT()){
				// Si la ciudad origen de la orden de transporte pertenece al mismo pais que la empresa
				if (csuit.getC_Country_ID() == origen.getC_Country_ID()){
					// Asumo que es una exportacion y retorno producto para vale flete de exportacion
					mProductID = cvflete.getM_Product_ID_2();
				}
				else{
					// Asumo que es una importacion y retorno producto para vale flete de importacion
					mProductID = cvflete.getM_Product_ID();
				}
			}
			else{
				// El producto depende de las empresas de los CRTS de la orden de transporte
				boolean mixClients = false;
				int adClientLines = 0;

				List<MTRTransOrderLine> tlines = torder.getLines();
				for (MTRTransOrderLine tline: tlines){
					MTRCrt crt = (MTRCrt)tline.getUY_TR_Crt();
					if ((crt.getAD_Client_ID() != adClientLines) && (adClientLines > 0)){
						mixClients = true;
						break;
					}
					adClientLines = crt.getAD_Client_ID();
				}
				
				// Si hay crts de mas de una empresa
				if (mixClients){
					// Retorno producto para mix de crts
					mProductID = cvflete.getM_Product_ID_3();
				}
				else{
					// Retorno producto para la unica empresa de los CRTS
					MTRConfigVFleteProd cvfprod = cvflete.getVFleteProdForClient(adClientLines);
					if (cvfprod != null){
						mProductID = cvfprod.getM_Product_ID();	
					}
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}

		return mProductID;
		
	}

	/***
	 * Obtiene modelo de parametrizacion de vale flete para esta configuracion. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 01/01/2015
	 * @see
	 * @return
	 */
	public MTRConfigVFlete getConfigVFlete() {
		
		String whereClause = X_UY_TR_ConfigVFlete.COLUMNNAME_UY_TR_Config_ID + "=" + this.get_ID();
		
		MTRConfigVFlete model = new Query(getCtx(), I_UY_TR_ConfigVFlete.Table_Name, whereClause, null).first();
		
		return model;
	}

	/***
	 * Obtiene modelo de parametrizacion de idoneidad para esta configuracion. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 01/01/2015
	 * @see
	 * @return
	 */
	public MTRSuitability getConfigSuitability() {
		
		String whereClause = X_UY_TR_Suitability.COLUMNNAME_UY_TR_Config_ID + "=" + this.get_ID();
		
		MTRSuitability model = new Query(getCtx(), I_UY_TR_Suitability.Table_Name, whereClause, null).first();
		
		return model;
	}
	
}
