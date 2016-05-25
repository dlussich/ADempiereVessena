/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 29/05/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttachment;
import org.compiere.model.MQuery;
import org.compiere.model.MUser;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MRNotification
 * OpenUp Ltda. Issue #285 
 * Description: Modelo de notificacion de incidencia.
 * @author Gabriel Vila - 29/05/2013
 * @see
 */
public class MRNotification extends X_UY_R_Notification implements IDynamicTask {

	private static final long serialVersionUID = -6740189617831487807L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Notification_ID
	 * @param trxName
	 */
	public MRNotification(Properties ctx, int UY_R_Notification_ID,
			String trxName) {
		super(ctx, UY_R_Notification_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRNotification(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Procesa accion de notificacion a cliente.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 29/05/2013
	 * @see
	 * @return
	 */
	public String processAction() {
		
		String action = "";
		String description = "";
		
		try{
			
			MRReclamo reclamo = (MRReclamo)this.getUY_R_Reclamo();
			
			// Actualizo notification
			this.setIsNotified(true);
			this.setEndDate(new Timestamp (System.currentTimeMillis()));
			this.saveEx();
			
			// Proceso segun tipo de accion y si es notificacion o pre-notificacion.
			if (reclamo.isPreNotificacion()){
				
				if (this.getNotificationActionType().equalsIgnoreCase(X_UY_R_Notification.NOTIFICATIONACTIONTYPE_PreNotificarCliente)){
					// Actualizo reclamo
					reclamo.setIsPreNotificacion(false);
					reclamo.setStatusReclamo(X_UY_R_Reclamo.STATUSRECLAMO_EnGestion);
					reclamo.saveEx();

					// Limpio campos de informacion de notificacion.
					action = " update uy_r_reclamo set notificationassignto_id = null, " +
							 " notificationdatefrom = null " +
							 " where uy_r_reclamo_id =" + reclamo.get_ID();
					DB.executeUpdateEx(action, get_TrxName());
					
					// Actualizo bandeja de entrada
					action = " update uy_r_inbox set assignto_id =" + reclamo.getAssignTo_ID() + "," +
							 " dateassign ='" + new Timestamp(System.currentTimeMillis()) + "'," +
							 " statusreclamo ='" + reclamo.getStatusReclamo() + "'," +
							 " uy_r_canal_id = null " +
							 " where uy_r_reclamo_id = " + reclamo.get_ID();
					DB.executeUpdateEx(action, get_TrxName());
					
					description = "Se PreNotifica al Cliente";
				}

			}
			else{
				// Cierre de Inicidencia
				if (this.getNotificationActionType().equalsIgnoreCase(X_UY_R_Notification.NOTIFICATIONACTIONTYPE_CerrarIncidencia)){

					reclamo.setReclamoNotificado(true);
					reclamo.setNotificator_ID(this.getReceptor_ID());
					reclamo.setNotificationDateTo(new Timestamp(System.currentTimeMillis()));
					reclamo.saveEx();
				
					// Completo reclamo
					if (!reclamo.processIt(DocAction.ACTION_Complete)){
						throw new AdempiereException(reclamo.getProcessMsg());
					}

				}
				// Reabrir Incidencia
				else if (this.getNotificationActionType().equalsIgnoreCase(X_UY_R_Notification.NOTIFICATIONACTIONTYPE_ReabrirIncidencia)){
					
					// Valido datos de derivacion
					if (this.getUY_R_Area_ID() <= 0){
						return "Debe indicar Area a la cual se Deriva esta Incidencia.";
					}

					if (this.getUY_R_PtoResolucion_ID() <= 0){
						return "Debe indicar Punto de Resolucion al cual se Deriva esta Incidencia.";
					}
					
					MRArea area = (MRArea)this.getUY_R_Area();
					MRPtoResolucion ptoResol = (MRPtoResolucion)this.getUY_R_PtoResolucion();
					MUser userDerivado = new MUser(getCtx(), this.getResponsable_ID(), null);

					String usuario = "";
					if (userDerivado != null){
						if (userDerivado.get_ID() > 0){
							usuario = ", Usuario : " + userDerivado.getDescription();
						}
					}
					
					description = "Se REABRE la Incidencia. Se Deriva la misma al Area : " + area.getName() + ", " +
								  " Pto.Resolucion : " + ptoResol.getName() + usuario;

					// Actualizo reclamo y lo dejo nuevamente en gestion
					reclamo.setUY_R_Area_ID(this.getUY_R_Area_ID());
					reclamo.setUY_R_PtoResolucion_ID(this.getUY_R_PtoResolucion_ID());
					reclamo.setReclamoResuelto(false);
					reclamo.setStatusReclamo(X_UY_R_Reclamo.STATUSRECLAMO_PendienteDeGestion);
					reclamo.saveEx();
					
					action = " update uy_r_reclamo set assignto_id = null, " +
							 " assigndatefrom = null, notificationassignto_id = null, " +
							 " notificationdatefrom = null, assigndateto = null, gestor_id = null " +
							 " where uy_r_reclamo_id =" + reclamo.get_ID();
					DB.executeUpdateEx(action, get_TrxName());
					
					// Actualizo bandeja de entrada segun derivacion
					if ((userDerivado == null) || (userDerivado.get_ID() <= 0)){
						action = " update uy_r_inbox set assignto_id =null," +
								 " dateassign =null," +
								 " statusreclamo ='" + reclamo.getStatusReclamo() + "'," +
								 " uy_r_canal_id = null, " +
								 " uy_r_ptoresolucion_id =" + this.getUY_R_PtoResolucion_ID() + 
								 " where uy_r_reclamo_id = " + reclamo.get_ID();
					}
					else{
						Timestamp today = new Timestamp(System.currentTimeMillis());
						action = " update uy_r_inbox set assignto_id =" + this.getResponsable_ID() + "," +
								 " dateassign ='" + today + "'," +
								 " statusreclamo ='" + reclamo.getStatusReclamo() + "'," +
								 " uy_r_canal_id = null, " +							 
								 " uy_r_ptoresolucion_id =" + this.getUY_R_PtoResolucion_ID() + 
								 " where uy_r_reclamo_id = " + reclamo.get_ID();
					}
					DB.executeUpdateEx(action, get_TrxName());
				}
				
			}

			// Nuevo Tracking
			if (!description.equalsIgnoreCase("")){
				MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
				track.setDateTrx(new Timestamp(System.currentTimeMillis()));
				track.setAD_User_ID(this.getReceptor_ID());
				track.setDescription(description);
				track.setobservaciones(this.getDescription());
				track.setUY_R_Reclamo_ID(reclamo.get_ID());		
				track.saveEx();
			}
			
			return null;
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeMQuery(MQuery query) {
	}
	
}
