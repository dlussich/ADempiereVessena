/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 17/05/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttachment;
import org.compiere.model.MQuery;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * org.openup.model - MRGestion
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 17/05/2013
 * @see
 */
public class MRGestion extends X_UY_R_Gestion implements IDynamicTask {

	private static final long serialVersionUID = 344420543692597771L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Gestion_ID
	 * @param trxName
	 */
	public MRGestion(Properties ctx, int UY_R_Gestion_ID, String trxName) {
		super(ctx, UY_R_Gestion_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRGestion(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		MRReclamo reclamo = (MRReclamo)this.getUY_R_Reclamo();
		MRPtoResolucion ptoResol = (MRPtoResolucion)reclamo.getUY_R_PtoResolucion();
		MRAccionPtoResol accionPoint = (MRAccionPtoResol) this.getUY_R_AccionPtoResol();
		String sql = "";
		
		if (newRecord){
			
			this.setDateTrx(new Timestamp(System.currentTimeMillis()));						
		}		
		
		//OpenUp. Nicolas Sarlabos. 10/10/2013. #1383. 
		//Si se elige cambio de direccion y/o destino se debe setear la direccion actual y destino actual
		if(accionPoint.get_ID() > 0){
			if(accionPoint.getValue().equalsIgnoreCase("direccion")){
				
				this.setAddress1(reclamo.getDirection()); //seteo direccion actual
			 
				sql = "select uy_deliverypoint_id from uy_tt_card where uy_r_reclamo_id = " + this.getUY_R_Reclamo_ID();
				int pointID = DB.getSQLValueEx(get_TrxName(), sql);

				if(pointID > 0) this.setUY_DeliveryPoint_ID(pointID); //seteo destino actual			
			}
		}
		//Fin OpenUp.

		// Si la incidencia esta pendiente de confirmacion escrita y esta es la gestion resuelta
		if ( (reclamo.getStatusReclamo().equalsIgnoreCase(X_UY_R_Reclamo.STATUSRECLAMO_PendienteDeCanalEscrito))
				&& (this.isReclamoResuelto()) && (!newRecord) && 
				( (is_ValueChanged(COLUMNNAME_JustificationApproved)) || (is_ValueChanged(COLUMNNAME_UserConfirmaEscrita_ID)))){

			// Valido que el usuario que esta editando pertenezca al canal escrito
			MRCanal canal = MRCanal.forValue(getCtx(), "canalescrito", null);
			if (!canal.validUser(Env.getAD_User_ID(Env.getCtx()))){
				throw new AdempiereException("Usted no tiene permisos para Confirmar Motivos." + "\n" +
											 "No pertenece al Canal de Notificacion Escrita.");
			}
			
			// Seteo texto de email de notificacion escrita para mostrar como va quedando
			this.showCanalEscritoEmail(reclamo);
			
		}
		else{
			// No puedo editar gestiones de otro usuario
			if ((!newRecord) && (this.getCreatedBy() != Env.getAD_User_ID(Env.getCtx()))){
				throw new AdempiereException("Usted no tiene permisos para Modificar Gestiones creadas por otro Usuario.");
			}
			
			// Solo pueden gestionar esta incidencia, aquellos usuarios del punto de resolucion.
			if (!ptoResol.validUser(Env.getAD_User_ID(Env.getCtx()))){
				
				// No valido para gestiones automaticas con el usuario adempiere.
				if (this.getReceptor_ID() > 0){
					MUser userReceptor = new MUser(getCtx(), this.getReceptor_ID(), null);
					if (!userReceptor.getName().equalsIgnoreCase("adempiere")){
						throw new AdempiereException("Usted no tiene permisos para Gestionar este Incidencia." + "\n" +
								 "No pertenece al Punto de Resolucion o se encuentra en estado InActivo.");
					}
				}
			}
			
			// Si usuario selecciona accion de Solicitar Ajustes, valido que tenga permisos para
			// hacerlo segun area-punto de resolucion de este reclamo.
			if (this.getReclamoAccionType().equalsIgnoreCase(X_UY_R_Gestion.RECLAMOACCIONTYPE_SolicitarAjuste)){
				if (!ptoResol.canRequest(Env.getAD_User_ID(Env.getCtx()))){
					throw new AdempiereException("Usted no tiene permisos para Solicitar Ajustes en este Punto de Resolucion.");
				}
			}
			
			// Cuando actualiza la accion, debo asegurarme que no queden lineas de solicitud de ajuste basura
			if (!newRecord && is_ValueChanged("ReclamoAccionType")){
				if (!this.getReclamoAccionType().equalsIgnoreCase(RECLAMOACCIONTYPE_SolicitarAjuste)){
					DB.executeUpdateEx(" delete from uy_r_handlerajuste where uy_r_gestion_id =" + this.get_ID(), get_TrxName());	
				}
			}
		}
		
		return true;
	}

	/***
	 * Obtiene y retorna lista con lineas de ajustes solicitados en esta gestion.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 29/05/2013
	 * @see
	 * @return
	 */
	public List<MRHandlerAjuste> getAjusteLines(){
	
		String whereClause = X_UY_R_HandlerAjuste.COLUMNNAME_UY_R_Gestion_ID + "=" + this.get_ID();
		
		List<MRHandlerAjuste> lines = new Query(getCtx(), I_UY_R_HandlerAjuste.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}
	
	/***
	 * Ejecuta accion seleccionada.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 29/05/2013
	 * @see
	 */
	public String processAction() {

		String action = "";
		String description = "";
		
		try{
			
			MRReclamo reclamo = (MRReclamo)this.getUY_R_Reclamo();

			
			// Proceso segun tipo de accion

			// Otra Accion
			if (this.getReclamoAccionType().equalsIgnoreCase(X_UY_R_Gestion.RECLAMOACCIONTYPE_OtraAccion)){

			
				// Valido que haya indicado la accion
				if (this.getUY_R_AccionPtoResol_ID() <= 0){
					return "Debe indicar Otra Accion";
				}

				boolean descObligatorio = true;
				
				// Si estoy en tracking de tarjetas y selecciono accion de cambio de direccion/destino
				MRCause cause = (MRCause)reclamo.getUY_R_Cause();
				if (cause.getValue().equalsIgnoreCase("tracking")){
					MRAccionPtoResol actPtoResol = (MRAccionPtoResol)this.getUY_R_AccionPtoResol();
					if (actPtoResol.getValue().equalsIgnoreCase("direccion")){
						descObligatorio = false;
						MTTCard card = MTTCard.forReclamo(getCtx(), get_TrxName(), reclamo.get_ID());
						card.executeChangeAddress(this);
					}
					else if (actPtoResol.getValue().equalsIgnoreCase("nodireccion")){
						descObligatorio = false;
						MTTCard card = MTTCard.forReclamo(getCtx(), get_TrxName(), reclamo.get_ID());
						card.executeNoChangeAddress(this);
					}
					else if ( (actPtoResol.getValue().equalsIgnoreCase("notarjeta")) 
							|| (actPtoResol.getValue().equalsIgnoreCase("contarjeta"))
							|| (actPtoResol.getValue().equalsIgnoreCase("nocontacto"))){
						descObligatorio = false;
						MTTCard card = MTTCard.forReclamo(getCtx(), get_TrxName(), reclamo.get_ID());
						card.executeVerifyAction(this);
					}

				}

				// Tengo que ingresar un comentario
				if (descObligatorio){
					if ((this.getDescription() == null) || (this.getDescription().equalsIgnoreCase(""))){
						return "Debe ingresar Comentario.";
					}
				}
				
				// Actualizo gestion
				this.setIsExecuted(true);
				this.setDateExecuted(new Timestamp (System.currentTimeMillis()));
				this.saveEx();
				
				MRAccionPtoResol accionResol = (MRAccionPtoResol)this.getUY_R_AccionPtoResol();
				
				description = "Se Ejecuta Otra Accion: " + accionResol.getName();
			}
			// Solitiud de Ajuste
			else if (this.getReclamoAccionType().equalsIgnoreCase(X_UY_R_Gestion.RECLAMOACCIONTYPE_SolicitarAjuste)){
				
				// Valido fecha de ajuste
				if (this.getDateAction() == null){
					return "Debe indicar Fecha para los Ajustes solicitados";
				}
				
				// Si no tengo lineas de solicitud de ajuste, aviso y salgo
				List<MRHandlerAjuste> lines = this.getAjusteLines();
				if (lines.size() <= 0){
					return "Debe ingresar Lineas de Ajuste a Solicitar";
				}

				// Actualizo gestion
				this.setIsExecuted(true);
				this.setDateExecuted(new Timestamp (System.currentTimeMillis()));
				this.saveEx();
				
				description = "Se Ejecuta Accion de Solicitud de Ajustes"; 
				
			}
			// Derivar incidencia
			else if (this.getReclamoAccionType().equalsIgnoreCase(X_UY_R_Gestion.RECLAMOACCIONTYPE_DerivarReclamo)){
				
				// Valido datos de derivacion
				if (this.getUY_R_Area_ID() <= 0){
					return "Debe indicar Area a la cual se Deriva esta Incidencia.";
				}

				if (this.getUY_R_PtoResolucion_ID() <= 0){
					return "Debe indicar Punto de Resolucion al cual se Deriva esta Incidencia.";
				}

				// Tengo que ingresar un comentario
				if ((this.getDescription() == null) || (this.getDescription().equalsIgnoreCase(""))){
					return "Debe ingresar Comentario.";
				}

				// Actualizo gestion
				this.setIsExecuted(true);
				this.setDateExecuted(new Timestamp (System.currentTimeMillis()));
				this.saveEx();
				
				MRArea area = (MRArea)this.getUY_R_Area();
				MRPtoResolucion ptoResol = (MRPtoResolucion)this.getUY_R_PtoResolucion();
				MUser userDerivado = new MUser(getCtx(), this.getResponsable_ID(), null);

				String usuario = "";
				if (userDerivado != null){
					if (userDerivado.get_ID() > 0){
						usuario = ", Usuario : " + userDerivado.getDescription();
						
						
					}
				}
				
				description = "Se Deriva el Incidente al Area : " + area.getName() + ", " +
							  " Pto.Resolucion : " + ptoResol.getName() + usuario;

				Timestamp today = new Timestamp(System.currentTimeMillis());
				
				// Actualizo bandeja de entrada segun derivacion
				if ((userDerivado == null) || (userDerivado.get_ID() <= 0)){
					action = " update uy_r_inbox set assignto_id =null," +
							 " dateassign =null," +
							 " uy_r_ptoresolucion_id =" + this.getUY_R_PtoResolucion_ID() + 
							 " where uy_r_reclamo_id = " + reclamo.get_ID();
				}
				else{
					action = " update uy_r_inbox set assignto_id =" + this.getResponsable_ID() + "," +
							 " dateassign ='" + today + "'," +
							 " uy_r_ptoresolucion_id =" + this.getUY_R_PtoResolucion_ID() + 
							 " where uy_r_reclamo_id = " + reclamo.get_ID();
				}
				DB.executeUpdateEx(action, get_TrxName());

				reclamo.setUY_R_Area_ID(this.getUY_R_Area_ID());
				reclamo.setUY_R_PtoResolucion_ID(this.getUY_R_PtoResolucion_ID());
				
				if (this.getResponsable_ID() > 0){
					reclamo.setAssignTo_ID(this.getResponsable_ID());
					reclamo.setAssignDateFrom(today);
					reclamo.setStatusReclamo(X_UY_R_Reclamo.STATUSRECLAMO_EnGestion);
				}
				else{
					action = " update uy_r_reclamo set assignto_id =null," +
							 " assigndatefrom =null, " +
							 " statusreclamo ='" + X_UY_R_Reclamo.STATUSRECLAMO_PendienteDeGestion + "' " +
							 " where uy_r_reclamo_id = " + reclamo.get_ID();
					DB.executeUpdateEx(action, get_TrxName());
				}
				
				reclamo.saveEx();
				
				// Dejo usuario que derivo como observador de la incidencia
				/*
				MRReclamoObserver obs = MRReclamoObserver.forUserID(getCtx(), this.getReceptor_ID(), get_TrxName()); 
				if ((obs == null) || (obs.get_ID() <= 0)) {
					obs = new MRReclamoObserver(getCtx(), 0, get_TrxName());
					obs.setUY_R_Reclamo_ID(reclamo.get_ID());
					obs.setAD_User_ID(this.getReceptor_ID());
					obs.saveEx();
				}
				*/
				
			}

			// Notificar al cliente
			else if (this.getReclamoAccionType().equalsIgnoreCase(X_UY_R_Gestion.RECLAMOACCIONTYPE_NotificacionACliente)){

				// Tengo que ingresar un comentario
				if ((this.getDescription() == null) || (this.getDescription().equalsIgnoreCase(""))){
					return "Debe ingresar Comentario.";
				}

				int canalID = reclamo.getUY_R_CanalNotifica_ID();
				
				// Si el gestor marca la incidencia como resuelta
				if (this.isReclamoResuelto()){

					// Valido que no se puede resolver una incidencia si tiene ajustes pendientes.
					if (!reclamo.validateAjustesPendientes()){
						return "No es posible marcar esta Incidencia como Resuelta ya que tiene al menos una Solicitud de Ajuste Pendiente de Confirmacion.";
					}

					// Valido Reclamo a Favor de quien
					if (this.getResueltoType() == null){
						return "Debe indicar a favor de quien se Resolvio este Incidente.";
					}
					if (this.getResueltoType().equalsIgnoreCase(X_UY_R_Gestion.RESUELTOTYPE_Empresa)){
						if ((this.getJustification() == null) || (this.getJustification().equalsIgnoreCase(""))){
							return "Debe indicar texto de Justificacion de Resolucion a favor de la Empresa.";
						}
					
						reclamo.setJustification(this.getJustification());
						
						description = "Se Resuelve Incidente a Favor de la Empresa";
					}
					else{
						description = "Se Resuelve Incidente a Favor del Cliente";
					}

					// Actualizo gestion
					this.setIsExecuted(true);
					this.setDateExecuted(new Timestamp (System.currentTimeMillis()));
					this.saveEx();
					
					// Si la incidencia se resuelva a favor de la empresa, el canal de notificacion escrita debe primero
					// validar y confirmar el texto de justificacion. Luego se debe notificar.
					// Seteo los atributos que indiquen fin de gestion pero me aseguro de no notificar automaticamente.
					if (this.getResueltoType().equalsIgnoreCase(X_UY_R_Gestion.RESUELTOTYPE_Empresa)){
						reclamo.setIsPreNotificacion(false);
						reclamo.setReclamoResuelto(this.isReclamoResuelto());
						reclamo.setGestor_ID(this.getReceptor_ID());
						reclamo.setAssignDateTo(new Timestamp(System.currentTimeMillis()));
						reclamo.setUY_R_ActionType_ID_Notif(MRActionType.IDforValue(getCtx(), "clinotifica", null));
						reclamo.setStatusReclamo(X_UY_R_Reclamo.STATUSRECLAMO_PendienteDeCanalEscrito);
						
						MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
						track.setDateTrx(new Timestamp(System.currentTimeMillis()));
						track.setAD_User_ID(this.getReceptor_ID());
						track.setDescription(description);
						track.setobservaciones(this.getDescription());
						track.setUY_R_Reclamo_ID(reclamo.get_ID());		
						track.saveEx();

						// Dias de resolucion y de gestion
						Timestamp fecIni = TimeUtil.trunc(reclamo.getDateTrx(), TimeUtil.TRUNC_DAY);
						Timestamp fecIniGestion = TimeUtil.trunc(reclamo.getAssignDateFrom(), TimeUtil.TRUNC_DAY);						
						Timestamp fecResol = TimeUtil.trunc(reclamo.getAssignDateTo(), TimeUtil.TRUNC_DAY);
						reclamo.setdiasresolucion(TimeUtil.getDaysBetween(fecIni, fecResol));
						reclamo.setDiasGestion(TimeUtil.getDaysBetween(fecIniGestion, fecResol));
						reclamo.saveEx();
						
						canalID = MRCanal.forValue(getCtx(), "canalescrito", null).get_ID();

						description = "Se requiere Confirmacion de Motivos de Resolucion a Favor de Empresa";
						
					}
					else{
						// Si la via de notificacion es por Email se notifica de manera automatica enviando un email.
						// No se requiere que un canal gestione la notificacion.
						if (reclamo.getNotificationVia().equalsIgnoreCase(X_UY_R_Reclamo.NOTIFICATIONVIA_Email)){
							
							// Se envia email de notificacion y se cierra la incidencia.
							reclamo.setGestor_ID(this.getReceptor_ID());
							reclamo.setAssignDateTo(new Timestamp(System.currentTimeMillis()));
							reclamo.setReclamoResuelto(true);
							reclamo.setReclamoNotificado(true);
							reclamo.saveEx();
						
							// Completo reclamo
							if (!reclamo.processIt(DocAction.ACTION_Complete)){
								throw new AdempiereException(reclamo.getProcessMsg());
							}
						}
						else{
							// No lleva cierre automatico por email
							reclamo.setIsPreNotificacion(false);
							reclamo.setReclamoResuelto(this.isReclamoResuelto());
							reclamo.setGestor_ID(this.getReceptor_ID());
							reclamo.setAssignDateTo(new Timestamp(System.currentTimeMillis()));
							reclamo.setUY_R_ActionType_ID_Notif(MRActionType.IDforValue(getCtx(), "clinotifica", null));
							reclamo.setStatusReclamo(X_UY_R_Reclamo.STATUSRECLAMO_PendienteDeNotificacion);
							
							MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
							track.setDateTrx(new Timestamp(System.currentTimeMillis()));
							track.setAD_User_ID(this.getReceptor_ID());
							track.setDescription(description);
							track.setobservaciones(this.getDescription());
							track.setUY_R_Reclamo_ID(reclamo.get_ID());		
							track.saveEx();

							// Dias de resolucion y de gestion
							Timestamp fecIni = TimeUtil.trunc(reclamo.getDateTrx(), TimeUtil.TRUNC_DAY);
							Timestamp fecIniGestion = TimeUtil.trunc(reclamo.getAssignDateFrom(), TimeUtil.TRUNC_DAY);						
							Timestamp fecResol = TimeUtil.trunc(reclamo.getAssignDateTo(), TimeUtil.TRUNC_DAY);
							reclamo.setdiasresolucion(TimeUtil.getDaysBetween(fecIni, fecResol));
							reclamo.setDiasGestion(TimeUtil.getDaysBetween(fecIniGestion, fecResol));
							reclamo.saveEx();
							
							description = "Se requiere Notificar al Cliente";
						}
						
					}
					
				}
				else{
					
					// Actualizo gestion
					this.setIsExecuted(true);
					this.setDateExecuted(new Timestamp (System.currentTimeMillis()));
					this.saveEx();
					
					// El gestor no resuelve la incidencia y necesita una pre-notificacion
					reclamo.setIsPreNotificacion(true);
					reclamo.setUY_R_ActionType_ID_Notif(MRActionType.IDforValue(getCtx(), "cliprenotifica", null));
					reclamo.setStatusReclamo(X_UY_R_Reclamo.STATUSRECLAMO_PendienteDeNotificacion);
					reclamo.saveEx();
					
					description = "Se Requiere PreNotificar al Cliente.";
				}
				
				
				
				// Actualizo bandeja de entrada
				action = " update uy_r_inbox set assignto_id =null ," +
								" dateassign =null," +
								" uy_r_canal_id =" + canalID + "," +
								" statusreclamo ='" + reclamo.getStatusReclamo() + "' " +
								" where uy_r_reclamo_id = " + reclamo.get_ID();
				DB.executeUpdateEx(action, get_TrxName());
				
			}
			// Cerrar incidencia ya que no es notificable o es una incidencia interna
			else if (this.getReclamoAccionType().equalsIgnoreCase(X_UY_R_Gestion.RECLAMOACCIONTYPE_CerrarReclamo)){
				
				// Tengo que ingresar un comentario
				if ((this.getDescription() == null) || (this.getDescription().equalsIgnoreCase(""))){
					return "Debe ingresar Comentario.";
				}

				// No puedo tener ajustes pendientes
				boolean sinAjustesPendientes = reclamo.validateAjustesPendientes();
				if (!sinAjustesPendientes){
					return "No es posible marcar esta Incidencia como Resuelta ya que tiene al menos una Solicitud de Ajuste Pendiente de Confirmacion.";
				}

				// Actualizo gestion
				this.setIsExecuted(true);
				this.setDateExecuted(new Timestamp (System.currentTimeMillis()));
				this.saveEx();
				
				reclamo.setGestor_ID(this.getReceptor_ID());
				reclamo.setAssignDateTo(new Timestamp(System.currentTimeMillis()));
				reclamo.setReclamoResuelto(true);
				reclamo.setReclamoNotificado(true);
				reclamo.saveEx();
			
				// Completo reclamo
				if (!reclamo.processIt(DocAction.ACTION_Complete)){
					throw new AdempiereException(reclamo.getProcessMsg());
				}

			}
			else{
				return null;
			}

			/*
			// Actualizo gestion
			this.setIsExecuted(true);
			this.setDateExecuted(new Timestamp (System.currentTimeMillis()));
			this.saveEx();
			*/
			
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

	/***
	 * Despliega el texto del email que se estaría enviando al cliente cuando un usuario
	 * del canal escrito confirme los motivos a favor de la empresa. 
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 14/08/2013
	 * @see
	 */
	private void showCanalEscritoEmail(MRReclamo reclamo){
		
		try {
			
			MRConfig config = MRConfig.forValue(getCtx(), "general", null);
			
			StringBuilder body = new StringBuilder();			
			body.append(config.getMailText5());
			body = new StringBuilder(body.toString().replaceAll("#INCIDENCIA#", reclamo.getDocumentNo()));
			body = new StringBuilder(body.toString().replaceAll("#FECHA_INCIDENCIA#", new SimpleDateFormat("dd/MM/yyyy").format(reclamo.getDateTrx())));
			body = new StringBuilder(body.toString().replaceAll("#CLIENTE#", reclamo.getName()));			
			body = new StringBuilder(body.toString().replaceAll("#MOTIVOS#", this.getJustificationApproved()));
			
			this.setMailText(body.toString());
			
		} catch (Exception e) {
			throw new AdempiereException();
		}
		
	}

	@Override
	public void changeMQuery(MQuery query) {
	}
	
}
