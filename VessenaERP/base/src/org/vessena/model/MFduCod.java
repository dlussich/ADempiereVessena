/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/10/2012
 */
package org.openup.model;


import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;




/**
 * org.openup.model - MFduCod
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 19/10/2012
 * @see
 */
public class MFduCod extends X_UY_FduCod {

	private static final long serialVersionUID = 1520943644945264402L;
	
	
	private static String TABLA_MOLDE= "UY_Molde_FduLoad";
	// Todas las combinaciones posibles resultante del cruce de atributos de
	// codigos fdu.
		
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_FduCod_ID
	 * @param trxName
	 */
	public MFduCod(Properties ctx, int UY_FduCod_ID, String trxName) {
		super(ctx, UY_FduCod_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduCod(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtengo codigos que sean totalizadores.
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 19/10/2012
	 * @see
	 * @param ctx
	 * @param name
	 * @param trxName
	 * @return
	 */
	public static List<MFduCod> getTotalizadores(Properties ctx, String trxName) {

		String whereClause = "IsTotalizador='Y'";
		
		List<MFduCod> values = new Query(ctx, TABLA_MOLDE , whereClause, trxName)
		.list();
		
		return values;
	}
	
	public static List<MFduCod> getConsumos(Properties ctx, String trxName) {

		String whereClause = "IsTotalizador='N'";
		
		List<MFduCod> values = new Query(ctx, I_UY_FduCod.Table_Name, whereClause, trxName)
		.list();
		
		return values;
	}
	
	
	public String getCodigosDeHijos(Properties ctx, String trxName) {
		
		String retorno = "";
		
		//falta los demas campos que sean iguales al actual
		String whereClause = "parentValue='" + this.getValue() + "' AND IsTotalizador='N' AND IsActive='Y'";
		
		List<MFduCod> cods = new Query(ctx, I_UY_FduCod.Table_Name, whereClause, trxName).list();
		
		
		for (int i = 0; i < cods.size(); i++) {
			if(i == cods.size() -1) {retorno += "'" + cods.get(i).getValue() + "'";} 
			else {retorno += "'" + cods.get(i).getValue() + "',";}
		}
				
		return retorno;
	}
	
	public static MFduCod getMFduCodForValue(Properties ctx, String trxName, String value) {	
		
		String whereClause = I_UY_FduCod.COLUMNNAME_Value + " = '" + value + "'";
		
		return new Query(ctx, I_UY_FduCod.Table_Name, whereClause, trxName).first();	
	}
	

	/***
	 * Obtiene y guarda en memoria las combinaciones posibles de atributos de este codigo fdu.
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 25/10/2012
	 * @see
	 */
	public static MFduCod getMFduCodFor(Properties ctx, String trxName, String value, int currency, int cuotas) {
		
		String whereClause = I_UY_FduCod.COLUMNNAME_Value + " = '" + value + "' AND " + I_UY_FduCod.COLUMNNAME_C_Currency_ID +" = "+ currency + " AND " + I_UY_FduCod.COLUMNNAME_IsActive +" = 'Y'";
		
		List<MFduCod> cods = new Query(ctx, I_UY_FduCod.Table_Name, whereClause, trxName).list();
		
		
		for (int i = 0; i < cods.size(); i++) {
			if(cuotas!=0){
				try{
					String getCuotas = cods.get(i).getCuotas();
					String getCuotas2 = cods.get(i).getCuotas_2();
					if(cods.get(i).getCuotas()!=null&&cods.get(i).getCuotas()!=null){
						int cuotas1 =Integer.parseInt(getCuotas);
						int cuotas2 =Integer.parseInt(getCuotas2);
						if(cuotas>= cuotas1 && cuotas2>=cuotas) {
							MFduCod codigo = cods.get(i);
							return  codigo;
					}				
					} 
					
				}
				catch (Exception e) {
					throw new AdempiereException("falta parametro de cantidad cuotas");
				}
				
			}
			else if(cuotas==0){
				if(cods.get(i).getCuotas()==null&&cods.get(i).getCuotas()==null){
					MFduCod codigo = cods.get(i);
					return  codigo;
				}
			}
					
		}
				
		return null;	
	}
}
