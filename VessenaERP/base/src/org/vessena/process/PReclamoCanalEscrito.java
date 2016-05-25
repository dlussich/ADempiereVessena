/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 02/08/2013
 */
package org.openup.process;

import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MRCanal;
import org.openup.model.MRGestion;
import org.openup.model.MRReclamo;
import org.openup.model.MRTracking;
import org.openup.model.X_UY_R_Reclamo;

/**
 * org.openup.process - PReclamoCanalEscrito
 * OpenUp Ltda. Issue #281 
 * Description: Proceso que se dispara cuando un usuario de canal escrito confirma motivos de 
 * resolucion de incidencia a favor de la empresa.
 * @author Gabriel Vila - 02/08/2013
 * @see
 */
public class PReclamoCanalEscrito extends SvrProcess {

	MRGestion gestion = null;
	
	/**
	 * Constructor.
	 */
	public PReclamoCanalEscrito() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 02/08/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		try{
			this.gestion = new MRGestion(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 02/08/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		// Valido que el usuario que esta editando pertenezca al canal escrito
		MRCanal canal = MRCanal.forValue(getCtx(), "canalescrito", null);
		if (!canal.validUser(this.getAD_User_ID())){
			throw new AdempiereException("Usted no tiene permisos para Confirmar Motivos." + "\n" +
										 "No pertenece al Canal de Notificacion Escrita.");
		}

		// Se confirma la notificacion a favor de la empresa.
		// Debo continuar con el mecanismo de notificacion.
		// Esta incidencia puede estar seteada con notificacion automatica por mail, en cuyo
		// caso este es el momento para enviarlo.
		// Si tiene notificacion por canal, solo cambiando el estado de la incidencia alcanza.
		gestion.setConfirmacionEscrita(true);
		gestion.setUserConfirmaEscrita_ID(this.getAD_User_ID());
		gestion.saveEx();

		MRReclamo reclamo = (MRReclamo)gestion.getUY_R_Reclamo();
		
		// Tracking
		MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(new Timestamp(System.currentTimeMillis()));
		track.setAD_User_ID(this.getAD_User_ID());
		track.setDescription("Confirmacion de Motivos de Resolucion a Favor de la Empresa.");
		track.setUY_R_Reclamo_ID(reclamo.get_ID());		
		track.saveEx();

		// Envio de email automatico de resolucion a favor de la empresa.
		// Este email se envia siempre que tengo un Email del cliente, sin importar mas nada.
		if (reclamo.getEMail() != null){
			if (reclamo.getEMail().contains("@")){
				reclamo.setJustification(this.gestion.getJustificationApproved());
				reclamo.sendNotificationEmail(this.gestion.getJustificationApproved());				
			}
		}
		
		// Si notificacion por email 
		if (reclamo.getNotificationVia().equalsIgnoreCase(X_UY_R_Reclamo.NOTIFICATIONVIA_Email)){
			
			reclamo.setReclamoNotificado(true);
			reclamo.saveEx();

			// Completo reclamo
			if (!reclamo.processIt(DocAction.ACTION_Complete)){
				throw new AdempiereException(reclamo.getProcessMsg());
			}
		}
		else{
			reclamo.setStatusReclamo(X_UY_R_Reclamo.STATUSRECLAMO_PendienteDeNotificacion);
			reclamo.saveEx();

			// Actualizo bandeja de entrada
			String action = " update uy_r_inbox set assignto_id =null ," +
							" dateassign =null," +
							" uy_r_canal_id =" + reclamo.getUY_R_CanalNotifica_ID() + "," +
							" statusreclamo ='" + reclamo.getStatusReclamo() + "' " +
							" where uy_r_reclamo_id = " + reclamo.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
		}
		
		
		return "OK";
	}

}
