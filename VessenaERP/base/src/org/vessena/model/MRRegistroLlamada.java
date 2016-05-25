/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 31/07/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * org.openup.model - MRRegistroLlamada
 * OpenUp Ltda. Issue #281 
 * Description: Modelo para registrar llamadas de notificacion a clientes.
 * @author Gabriel Vila - 31/07/2013
 * @see
 */
public class MRRegistroLlamada extends X_UY_R_RegistroLlamada {

	private static final long serialVersionUID = 7337880137557443921L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_RegistroLlamada_ID
	 * @param trxName
	 */
	public MRRegistroLlamada(Properties ctx, int UY_R_RegistroLlamada_ID,
			String trxName) {
		super(ctx, UY_R_RegistroLlamada_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRRegistroLlamada(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		MRNotification notif = (MRNotification)this.getUY_R_Notification();
		if ((notif == null) || (notif.get_ID() <= 0)) return true;
		
		// Nuevo Tracking para registro de llamada.
		MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(new Timestamp(System.currentTimeMillis()));
		track.setAD_User_ID(this.getReceptor_ID());
		track.setDescription("Registro de Llamada");
		track.setUY_R_Reclamo_ID(notif.getUY_R_Reclamo_ID());
		
		String observaciones = ""; 
		
		
		if (this.getComunicationResult().equalsIgnoreCase(COMUNICATIONRESULT_AvisoAFamiliar)){
			observaciones ="Aviso a Familiar. ";
		}
		else if (this.getComunicationResult().equalsIgnoreCase(COMUNICATIONRESULT_Contesta)){
			observaciones ="Cliente Contesta. ";
		}
		else if (this.getComunicationResult().equalsIgnoreCase(COMUNICATIONRESULT_NoContesta)){
			observaciones ="No Contesta. ";
		}
		else if (this.getComunicationResult().equalsIgnoreCase(COMUNICATIONRESULT_Otros)){
			observaciones ="Otros. ";
		}
		else if (this.getComunicationResult().equalsIgnoreCase(COMUNICATIONRESULT_SeLeEnviaSMS)){
			observaciones ="Se envia SMS. ";
		}
		
		if (this.getDescription() != null) 
			observaciones = observaciones + this.getDescription();
		
		track.setobservaciones(observaciones);
		track.saveEx();
		
		return true;

	}
	
}
