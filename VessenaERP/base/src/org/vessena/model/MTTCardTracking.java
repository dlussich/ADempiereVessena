/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * @author gbrust
 *
 */
public class MTTCardTracking extends X_UY_TT_CardTracking {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3902572599878960809L;

	/**
	 * @param ctx
	 * @param UY_TT_CardTracking_ID
	 * @param trxName
	 */
	public MTTCardTracking(Properties ctx, int UY_TT_CardTracking_ID,
			String trxName) {
		super(ctx, UY_TT_CardTracking_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTCardTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (!success) return success;
		
		if (newRecord){
			// Tracking en incidencia.
			MTTCard card = (MTTCard)this.getUY_TT_Card();
			MTTCardStatus cardStatus = (MTTCardStatus)card.getUY_TT_CardStatus();
			
			String statustext = "";
			if ((cardStatus != null) && (cardStatus.get_ID() > 0)){
				statustext = cardStatus.getName();
			}
			
			if (card.getUY_R_Reclamo_ID() > 0){
				MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
				track.setDateTrx(new Timestamp(System.currentTimeMillis()));
				track.setAD_User_ID(this.getAD_User_ID());
				track.setDescription(statustext + ". " + this.getDescription());
				track.setobservaciones(this.getobservaciones());
				track.setUY_R_Reclamo_ID(card.getUY_R_Reclamo_ID());		
				track.saveEx();
			}
		}
		
		return true;
	}

	
	
}
