/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MCountry;
import org.compiere.util.DB;
import org.openup.aduana.PTRAduanaSendMICLogic;

/**
 * @author Nicolas
 *
 */
public class MTRMicCountry extends X_UY_TR_MicCountry {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2008126581762190657L;

	/**
	 * @param ctx
	 * @param UY_TR_MicCountry_ID
	 * @param trxName
	 */
	public MTRMicCountry(Properties ctx, int UY_TR_MicCountry_ID, String trxName) {
		super(ctx, UY_TR_MicCountry_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRMicCountry(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(!newRecord){
			
			if(this.getStatus().equalsIgnoreCase("VINCULADO") || this.getStatus().equalsIgnoreCase("ENBAJA")) 
				throw new AdempiereException("No se permite modificar, este pais de paso ya ha sido enviado a aduana");
			
		} else {
			
			MTRMic hdr = new MTRMic(getCtx(),this.getUY_TR_Mic_ID(),get_TrxName());
			
			//seteo contador de pais desde el cabezal
			this.setNumero(hdr.getContadorPais()); 
			
			//incremento contador de paises en el cabezal
			DB.executeUpdateEx("update uy_tr_mic set contadorpais = contadorpais + 1 where uy_tr_mic_id = " + hdr.get_ID(), get_TrxName());			
			
		}
		
		return true;
	}

	@Override
	protected boolean beforeDelete() {
		
		String sql = "";
		MTRMic hdr = new MTRMic(getCtx(),this.getUY_TR_Mic_ID(),get_TrxName());
		MCountry country = new MCountry(getCtx(),this.getC_Country_ID(),get_TrxName());

		if(this.getStatus().equalsIgnoreCase("ENBAJA") || this.getStatus().equalsIgnoreCase("VINCULADO")){

			DB.executeUpdateEx("update uy_tr_miccountry set status = 'ENBAJA' where uy_tr_miccountry_id = " + this.get_ID(), get_TrxName());

			//disparo clase para envio a aduana
			PTRAduanaSendMICLogic logic = new PTRAduanaSendMICLogic(getCtx(),get_TrxName());
			logic.execute(getCtx(), hdr ,get_TrxName());	

			sql = "select status from uy_tr_miccountry where uy_tr_miccountry_id = " + this.get_ID();
			String status = DB.getSQLValueStringEx(get_TrxName(), sql);

			if(status!=null){
				if(!status.equalsIgnoreCase("DESVINCULADO")) {

					ADialog.info(0,null,"No se pudo dar de baja el pais de paso '" + country.getName() + "'. Verifique el mensaje de error en la pestaña 'Envio Aduana'"); 

					return false;				

				}		
			
			} else {
				
				ADialog.info(0,null,"No se pudo obtener el estado de envio para el pais de paso '" + country.getName());
				
				return false;
			}
		}
		return true;
	}

}
