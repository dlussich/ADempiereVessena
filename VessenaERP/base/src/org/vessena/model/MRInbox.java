/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 06/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * org.openup.model - MRInbox
 * OpenUp Ltda. Issue #325 
 * Description: Bandeja de Entrada de Tareas
 * @author Gabriel Vila - 06/02/2013
 * @see
 */
public class MRInbox extends X_UY_R_Inbox {

	private static final long serialVersionUID = -4737544484796184789L;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Inbox_ID
	 * @param trxName
	 */
	public MRInbox(Properties ctx, int UY_R_Inbox_ID, String trxName) {
		super(ctx, UY_R_Inbox_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRInbox(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Cuando una tarea es tomada, debo hacer varias verificaciones y procesos.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 19/03/2013
	 * @see
	 */
	public void actionTaken() {

		try{
			// Si tengo un reclamo
			if (this.getUY_R_Reclamo_ID() > 0){
				
				// Si esta tarea esta asignada no hago nada
				if (!this.getStatusTarea().equalsIgnoreCase(STATUSTAREA_NoAsignada)) return;
				
				// Elimino toda otra entrada para este reclamo
				String action = " delete from uy_r_inbox where uy_r_inbox_id !=" + this.get_ID() +
								" and uy_r_reclamo_id =" + this.getUY_R_Reclamo_ID();
				DB.executeUpdateEx(action, get_TrxName());
				
				// Marco este tarea de la bandeja como asignada
				this.setAssignTo_ID(Env.getAD_User_ID(Env.getCtx()));
				this.setDateAssign(TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY));
				this.setStatusTarea(STATUSTAREA_EnCurso);
				this.saveEx();

				// Tracking reclamo
				MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
				track.setDateTrx(this.getDateTrx());
				track.setAD_User_ID(this.getAssignTo_ID());
				track.setUY_R_Reclamo_ID(this.getUY_R_Reclamo_ID());
				track.setUY_R_Tarea_ID(this.getUY_R_Tarea_ID());
				track.saveEx();

			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Se setea el color de los semaforos segun atributos del tema.
	 * OpenUp Ltda. Issue #325 
	 * @author Gabriel Vila - 05/02/2014
	 * @see
	 */
	public void setSign(){
		
		try {

			MRSign sign = null;
			MRSign signCritical = null;

			// Segundo semaforo. Por defecto gris.
			signCritical = MRSign.forValue(getCtx(), "grey", null);

			
			// Si no tengo causa asociada pongo colores del semaforo por defecto y salgo
			if (this.getUY_R_Cause_ID() <= 0){
				sign = MRSign.forValue(getCtx(), "green", null);
			}
			else{
				MRCause cause = (MRCause)this.getUY_R_Cause();
				
				// Si plazo optimo del tema es cero
				if (cause.getMediumTerm() <= 0) {
					// Si el plazo final tambien es cero
					if (cause.getDeadLine() <= 0){
						// Esta incidencia entra con semaforo en rojo
						sign = MRSign.forValue(getCtx(), "red", null);
					}
					else{
						// Esta incidencia entra con semaforo en amarillo
						sign = MRSign.forValue(getCtx(), "yellow", null);
					}
				}
				else{
					// Esta incidencia entra con semaforo en verde
					sign = MRSign.forValue(getCtx(), "green", null);
				}
			}
			
			this.setTrackImage_ID(sign.getAD_Image_ID());
			this.setCriticalImage_ID(signCritical.getAD_Image_ID());
			this.setSeqNo(sign.getSeqNo());
			this.setCriticalSeqNo(signCritical.getSeqNo());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	
	/***
	 * Retorna modelo segun tabla y registro.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 05/02/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRInbox forTableAndRecord(Properties ctx, int adTableID, int recordID, String trxName){
		
		String whereClause = X_UY_R_Inbox.COLUMNNAME_AD_Table_ID + "=" + adTableID +  
				   " AND " + X_UY_R_Inbox.COLUMNNAME_Record_ID + "=" + recordID ;
		
		MRInbox model = new Query(ctx, I_UY_R_Inbox.Table_Name, whereClause, trxName).first();
		
		return model;
		
	}

	/***
	 * Metodo statico que delega tareas de un usuario origen a un usuario destino o visceversa.
	 * OpenUp Ltda. Issue #1260 
	 * @author Gabriel Vila - 12/03/2014
	 * @see
	 * @param ctx
	 * @param sourceUserID
	 * @param targetUserID
	 * @param fromSourceToTarget
	 * @param trxName
	 */
	public static void delegateUserTasks(Properties ctx, int sourceUserID, int targetUserID, boolean fromSourceToTarget, String trxName) {

		String action = "";
		
		try {
		
			// Si estoy delegando desde un usuario origen a uno destino
			if (fromSourceToTarget){
				action = " update uy_r_inbox set assignto_id = " + targetUserID + ", " +
						 " delegateuser_id = assignto_id " +
						 " where assignto_id =" + sourceUserID; 
						
			}
			else{
				action = " update uy_r_inbox set assignto_id = delegateuser_id, " +
						 " delegateuser_id = null " +
						 " where delegateuser_id =" + sourceUserID; 
			}
			
			DB.executeUpdateEx(action, trxName);
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}	
}
