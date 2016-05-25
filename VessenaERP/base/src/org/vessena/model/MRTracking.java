/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 07/03/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.MAttachment;
import org.compiere.model.MQuery;
import org.compiere.util.Env;

/**
 * org.openup.model - MRTracking
 * OpenUp Ltda. Issue #281 
 * Description: 
 * @author Hp - 07/03/2013
 * @see
 */
public class MRTracking extends X_UY_R_Tracking implements IDynamicTask {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8375929149784548935L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Tracking_ID
	 * @param trxName
	 */
	public MRTracking(Properties ctx, int UY_R_Tracking_ID, String trxName) {
		super(ctx, UY_R_Tracking_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	public void setComment(String comment) {

		// Cuando agregan un comentario por la toolbar, el mismo se tiene que cargar
		// en la trazabilidad de esta incidencia.
		// Nuevo tracking
		
		MRReclamo reclamo = (MRReclamo)this.getUY_R_Reclamo();
		
		MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(new Timestamp(System.currentTimeMillis()));
		track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
		track.setDescription("Nuevo Comentario");
		track.setobservaciones(comment);
		track.setUY_R_Reclamo_ID(reclamo.get_ID());
		track.saveEx();
		
	}

	@Override
	public void setAttachment(MAttachment attachment) {
	}

	@Override
	public void changeMQuery(MQuery query) {
	}
	
}
