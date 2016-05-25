/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 29/05/2013
 */
package org.openup.process;

import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MUser;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MRCanal;
import org.openup.model.MRReclamo;
import org.openup.model.MRTracking;
import org.openup.model.X_UY_R_Reclamo;

/**
 * org.openup.process - PNotificarIncidencia
 * OpenUp Ltda. Issue #285 
 * Description: 
 * @author Hp - 29/05/2013
 * @see
 */
public class PNotificarIncidencia extends SvrProcess {

	MRReclamo reclamo = null;
	
	/**
	 * Constructor.
	 */
	public PNotificarIncidencia() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 29/05/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		try{
			this.reclamo = new MRReclamo(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}


	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 29/05/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		// Si esta incidencia esta en estado pendiente de canal escrito, no hay posibilidad de notificaciones todavia
		if (reclamo.getStatusReclamo().equalsIgnoreCase(X_UY_R_Reclamo.STATUSRECLAMO_PendienteDeCanalEscrito)){
			throw new AdempiereException("No es posible Notificar la incidencia ya que esta " +
					                     "Pendiente de Confirmacion por Parte del Canal de Notificacion Escrita");
		}
		
		// Debo verificar que este usuario tenga permisos para gestionar esta incidencia.
		MRCanal canal = new MRCanal(getCtx(), reclamo.getUY_R_CanalNotifica_ID(), null);

		// Solo pueden gestionar esta incidencia, aquellos usuarios del canal de notificacion
		if (!canal.validUser(this.getAD_User_ID())){
			throw new AdempiereException("Usted no tiene permisos para Notificar este Incidencia." + "\n" +
										 "No pertenece al Canal de Notificacion.");
		}
		
		// Si esta incidencia esta siendo gestionada por otro usuario. pregunto si
		// desea tomarla.
		if (reclamo.getNotificationAssignTo_ID() > 0){
			MUser user = new MUser(getCtx(), reclamo.getNotificationAssignTo_ID(), null);
			boolean ok = ADialog.ask(-1, null, "Notificacion de Incidencia", "Esta incidencia esta en proceso de Notificacion por el usuario : " + user.getDescription() + "\n" +
						 "Desea que esta tarea sea asignada a Usted ?");
			if (!ok){
				return "OK";
			}
		}
		
		this.reclamo.setNotificationAssignTo_ID(this.getAD_User_ID());
		
		if (this.reclamo.getNotificationDateFrom() == null){
			this.reclamo.setNotificationDateFrom(new Timestamp (System.currentTimeMillis()));	
		}
		
		this.reclamo.setStatusReclamo(X_UY_R_Reclamo.STATUSRECLAMO_EnNotificacion);
		this.reclamo.saveEx();
		
		// Nuevo Tracking
		MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(new Timestamp(System.currentTimeMillis()));
		track.setAD_User_ID(this.getAD_User_ID());
		track.setDescription("Inicio de Notificacion a Cliente");
		track.setUY_R_Reclamo_ID(reclamo.get_ID());		
		track.saveEx();

		String action = "";
		
		// Actualizo gestiones anteriores de esta incidencia como terminadas.
		action = " update uy_r_notification set isover ='Y' " + 
				 " where uy_r_reclamo_id = " + reclamo.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		// Actualizo bandeja de entrada
		Timestamp fecAux = reclamo.getAssignDateFrom();
		if (fecAux == null) fecAux = new Timestamp(System.currentTimeMillis());
		
		action = " update uy_r_inbox set assignto_id =" + this.getAD_User_ID() + "," +
						" dateassign = '" + fecAux + "'," +
						" statusreclamo ='" + reclamo.getStatusReclamo() + "', " +
						" statustarea ='CUR' " +
						" where uy_r_reclamo_id = " + reclamo.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		return "OK";
	}

}
