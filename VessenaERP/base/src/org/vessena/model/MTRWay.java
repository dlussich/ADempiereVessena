/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Nico - 11/11/2014
 */
 
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCountry;
import org.compiere.model.Query;

/**
 * org.openup.model - MTRWay
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Nico - 11/11/2014
 * @see
 */
public class MTRWay extends X_UY_TR_Way {

	private static final long serialVersionUID = 91139610789279134L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_Way_ID
	 * @param trxName
	 */
	public MTRWay(Properties ctx, int UY_TR_Way_ID, String trxName) {
		super(ctx, UY_TR_Way_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRWay(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
		
		//si se setean los codigos de ciudad en el VALUE del trayecto
		if(param.isCityCodeWay()){
			
			if(newRecord || is_ValueChanged("UY_Ciudad_ID") || is_ValueChanged("UY_Ciudad_ID_1")) {

				String city1 = "", city2 = "";
				
				MCiudad c1 = new MCiudad (getCtx(),this.getUY_Ciudad_ID(),get_TrxName());
				MCiudad c2 = new MCiudad (getCtx(),this.getUY_Ciudad_ID_1(),get_TrxName());

				if(c1.getCode()!=null && !c1.getCode().equalsIgnoreCase("")){
					
					city1 = c1.getCode();
					
				} else city1 = c1.getName();
				
				if(c2.getCode()!=null && !c2.getCode().equalsIgnoreCase("")){
					
					city2 = c2.getCode();
					
				} else city2 = c2.getName();
				
				String s = city1 + " - " + city2;

				this.setValue(s);	
			}			
			
		//si se setean los codigos de pais en el VALUE del trayecto	
		} else {
			
			if(newRecord || is_ValueChanged("C_Country_ID") || is_ValueChanged("C_Country_ID_1")) {

				String country1 = "", country2 = "";
				
				MCountry c1 = new MCountry (getCtx(),this.getC_Country_ID(),get_TrxName());
				MCountry c2 = new MCountry (getCtx(),this.getC_Country_ID_1(),get_TrxName());

				if(c1.getCountryCode()!=null && !c1.getCountryCode().equalsIgnoreCase("")){
					
					country1 = c1.getCountryCode();
					
				} else country1 = c1.getName();
				
				if(c2.getCountryCode()!=null && !c2.getCountryCode().equalsIgnoreCase("")){
					
					country2 = c2.getCountryCode();
					
				} else country2 = c2.getName();
				
				String s = country1 + " - " + country2;

				this.setValue(s);	
			}			
			
		}	
		
		if(this.isPrintDeclaration() && this.isPrintExpo()) throw new AdempiereException("No se pueden imprimir datos de importacion y exportacion");

		return true;
	}
	
	/***
	 * Obtiene y retorna un trayecto para los paises origen y destino recibidos
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 02/01/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRWay forCountry(Properties ctx, int countryFrom, int countryTo, String trxName){
		
		String whereClause = X_UY_TR_Way.COLUMNNAME_C_Country_ID + "=" + countryFrom + " and " + X_UY_TR_Way.COLUMNNAME_C_Country_ID_1 + "=" + countryTo;
		
		MTRWay way = new Query(ctx, I_UY_TR_Way.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return way;
	}

}
