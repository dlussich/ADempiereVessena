/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 24/06/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

/**
 * org.openup.model - MBGTransProd
 * OpenUp Ltda. Issue #4174 
 * Description: Modelo de asociación de un producto a una transaccion bursatil
 * @author Gabriel Vila - 24/06/2015
 * @see
 */
public class MBGTransProd extends X_UY_BG_TransProd {

	private static final long serialVersionUID = 8666722495439248393L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BG_TransProd_ID
	 * @param trxName
	 */
	public MBGTransProd(Properties ctx, int UY_BG_TransProd_ID, String trxName) {
		super(ctx, UY_BG_TransProd_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGTransProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}


	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		//Actualizo valores de Transaction (campos calculados)
		sumarizarXcolumna(getCtx(),this.getUY_BG_Transaction_ID(),X_UY_BG_Transaction.COLUMNNAME_amt2);
		sumarizarXcolumna(getCtx(),this.getUY_BG_Transaction_ID(),X_UY_BG_Transaction.COLUMNNAME_AmtRetention);
		sumarizarXcolumna(getCtx(),this.getUY_BG_Transaction_ID(),X_UY_BG_Transaction.COLUMNNAME_AmtRetention2);	
		return true;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {	
		return true;
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author SBouissa 7/9/2015
	 * @param ctx
	 * @param id
	 * @param campo
	 */
	private void sumarizarXcolumna(Properties ctx,Object id,String campo) {
		try{
			
			if(null!=id){
				int idT = (Integer) id;
				if(0<idT){
					MBGTransaction transac = new MBGTransaction(ctx, idT, get_TrxName());
					if(null!=transac){
						transac.sumarizarXCampo(campo);				
					}
				}
			}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}		
		
	}
	
}
