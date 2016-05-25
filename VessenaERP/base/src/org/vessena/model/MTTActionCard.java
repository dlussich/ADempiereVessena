/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 10/10/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * org.openup.model - MTTActionCard
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de accion sobre cuentas en tracking.
 * @author Gabriel Vila - 10/10/2013
 * @see
 */
public class MTTActionCard extends X_UY_TT_ActionCard {

	private static final long serialVersionUID = 6633230656508111480L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ActionCard_ID
	 * @param trxName
	 */
	public MTTActionCard(Properties ctx, int UY_TT_ActionCard_ID, String trxName) {
		super(ctx, UY_TT_ActionCard_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTActionCard(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Ejecuta accion sobre cuentas recibidas.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 10/10/2013
	 * @see
	 * @return
	 */
	public String execute(List<MTTCard> cards) {

		String mensaje = null;
		String sql = "";
		String description = "", observation = "";
		boolean isSMS = false;
		
		try {
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");

			MTTCardStatus stTransito = MTTCardStatus.forValue(getCtx(), get_TrxName(), "enviada");
			MTTCardStatus stPendEnvio = MTTCardStatus.forValue(getCtx(), get_TrxName(), "pendenvio");
			
			for (MTTCard card: cards){
			
				MTTAction action = (MTTAction)this.getUY_TT_Action();

				if (action.getValue().equalsIgnoreCase("enviosms")){
					
					if (this.getUY_TT_SMS_ID() <= 0){
						throw new AdempiereException ("Debe Indicar Tipo de SMS");
					}
				
					isSMS = true;
					
					MTTActionCardSMS dsms = new MTTActionCardSMS(getCtx(), 0, get_TrxName());
					dsms.setUY_TT_ActionCard_ID(this.get_ID());
					dsms.setUY_TT_Card_ID(card.get_ID());
					dsms.setUY_TT_SMS_ID(this.getUY_TT_SMS_ID());
					dsms.setMobile(card.getMobile());
					dsms.saveEx();
					
					MTTSMS sms = (MTTSMS)this.getUY_TT_SMS();
					description = "Accion Ejecutada. Envio SMS.";
					observation = sms.getDescription();
					
					card.setUY_TT_Action_ID(action.get_ID());
					card.setDateAction(new Timestamp(System.currentTimeMillis()));
					card.saveEx();
				
				}
				else if (action.getValue().equalsIgnoreCase("destino")){
					
					if (this.getUY_DeliveryPoint_ID_To() <= 0){
						throw new AdempiereException ("Debe Indicar Nuevo Destino");
					}
					
					// Obtengo punto de distribucion de Casa central
					MDeliveryPoint dpCentral = MDeliveryPoint.forValue(getCtx(), "casacentral", null);

					// Si esta cuenta tiene como destino RedPagos y esta en estado pendiente de envio o en transito, no debo 
					// permitir cambio de dirección o destino.
					if ((card.getUY_TT_CardStatus_ID() == stTransito.get_ID()) || (card.getUY_TT_CardStatus_ID() == stPendEnvio.get_ID())){
						if ((card.getUY_DeliveryPoint_ID() == config.getUY_DeliveryPoint_ID())
								|| (card.getUY_DeliveryPoint_ID_Actual() == config.getUY_DeliveryPoint_ID())){

							throw new AdempiereException("No es posible cambiar destino o dirección a cuenta con destino Red Pagos "
									+ "y que se encuentra en Tránsito o Pendiente de Envío.\n" + "Cuenta: " + card.getAccountNo());
						}
					}
					
					// Si actualmente esta cuenta esta en casa central, solo cambio el destino.
					// Si actualmente esta cuenta esta en correo o en sucursal, debe primero pasar por casa central.
					
					if (card.getUY_DeliveryPoint_ID_Actual() == dpCentral.get_ID()){

						// Guardo nuevo destino
						card.setUY_DeliveryPoint_ID(this.getUY_DeliveryPoint_ID_To());
						card.setUY_DeliveryPoint_ID_To(this.getUY_DeliveryPoint_ID_To()); 
						card.setIsRequested(false);
						
						MDeliveryPoint dpSol = new MDeliveryPoint(getCtx(), this.getUY_DeliveryPoint_ID_To(), null);
						observation = "Desde Casa Central a Nuevo Destino : " + dpSol.getName();

					}
					else{
						card.setUY_DeliveryPoint_ID(dpCentral.get_ID());  // Destino Casa Central ya que tiene que venir para central primero
						card.setUY_DeliveryPoint_ID_To(this.getUY_DeliveryPoint_ID_To()); // Guardo nuevo destino
						card.setUY_DeliveryPoint_ID_Req(dpCentral.get_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
						card.setIsRequested(true);
						
						MDeliveryPoint dpSol = new MDeliveryPoint(getCtx(), this.getUY_DeliveryPoint_ID_To(), null);
						observation = " Solicitada en Casa Central para Nuevo Destino : " + dpSol.getName();
						
					}
					card.setIsAddressChanged(false);
					card.setUY_TT_Action_ID(action.get_ID());
					card.setDateAction(new Timestamp(System.currentTimeMillis()));

					card.saveEx();

					description = "Accion Ejecutada. Nuevo Destino.";
					
				}
				else if (action.getValue().equalsIgnoreCase("habdistrib")){
					card.setIsDeliverable(true);
					card.setIsRetained(false);
					card.setUY_TT_Action_ID(action.get_ID());
					card.setDateAction(new Timestamp(System.currentTimeMillis()));
					card.saveEx();
					
					DB.executeUpdateEx(" update uy_tt_card set notvalidtext = null where uy_tt_card_id =" + card.get_ID(), get_TrxName());

					description = "Accion Ejecutada. Habilitacion Cuenta.";
				
				}
				else if (action.getValue().equalsIgnoreCase("contactarcli")){

					if (this.getUY_TT_Mensaje_ID() <= 0){
						throw new AdempiereException ("Debe Indicar Tipo de Mensaje");
					}
					
					// Si esta cuenta actualmente no esta en estado contactar cliente, proceso
					MTTCardStatus cardStatus = (MTTCardStatus)card.getUY_TT_CardStatus();
					if (!cardStatus.getValue().equalsIgnoreCase("contactocli")){

						MTTMensaje msj = (MTTMensaje)this.getUY_TT_Mensaje();
						description = "Accion Ejecutada. Contactar Cliente.";
						observation = msj.getDescription();
						
						// Cambio estado a esta cuenta para identificar que esta para contacto a cliente (Ya no a partir de #2551)
						//card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "contactocli").get_ID());
						
						card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
						card.setUY_TT_Action_ID(action.get_ID());
						card.setDateAction(new Timestamp(System.currentTimeMillis()));
						
						card.saveEx();
						
						// Derivo incidencias al area y punto de resolucion indicados en parametros generales
						if (config.getUY_R_Area_ID() <= 0){
							return "Falta indicar en parametros generales, el Area a la cual derivar la accion de Contactar al Cliente.";
						}

						if (config.getUY_R_PtoResolucion_ID() <= 0){
							return "Falta indicar en parametros generales, el Punto de Resolucion al cual derivar la accion de Contactar al Cliente.";
						}

						MRReclamo reclamo = (MRReclamo)card.getUY_R_Reclamo();
						reclamo.setUY_R_Area_ID(config.getUY_R_Area_ID());
						reclamo.setUY_R_PtoResolucion_ID(config.getUY_R_PtoResolucion_ID());

						// Inserto datos de incidencia en bandeja de entrada
						reclamo.generateInbox();
						
						reclamo.saveEx();
						
						sql = " update uy_r_reclamo set assignto_id =null," +
								  " assigndatefrom =null, " +
								  " statusreclamo ='" + X_UY_R_Reclamo.STATUSRECLAMO_PendienteDeGestion + "' " +
								  " where uy_r_reclamo_id = " + reclamo.get_ID();
						DB.executeUpdateEx(sql, get_TrxName());
					}
					
				}
				else if (action.getValue().equalsIgnoreCase("destruccion")){

					description = "Accion Ejecutada. Destruccion de Cuenta.";
					
					// Cambio estado a esta cuenta para identificar que esta para contacto a cliente
					card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "destruida").get_ID());
					card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
					card.setUY_TT_Action_ID(action.get_ID());
					card.setDateAction(new Timestamp(System.currentTimeMillis()));
					card.saveEx();
					
					MRReclamo reclamo = (MRReclamo)card.getUY_R_Reclamo();
					reclamo.setJustification("Destruccion de Cuenta");
					reclamo.setReclamoResuelto(true);
					reclamo.setEndDate(new Timestamp(System.currentTimeMillis()));
					reclamo.saveEx();
					
					if (!reclamo.processIt(DocumentEngine.ACTION_Complete)){
						throw new AdempiereException("No se pudo completar la acción de Cierre de Incidencia");
					}
					
					reclamo.setLegajoFinancial(false, true, null);

				}
				
				// Tracking cuenta
				MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
				cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
				cardTrack.setAD_User_ID(this.getAD_User_ID());
				cardTrack.setDescription(description);
				cardTrack.setobservaciones(observation);
				cardTrack.setUY_TT_Card_ID(card.get_ID());
				cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
				cardTrack.setUY_DeliveryPoint_ID_Actual(card.getUY_DeliveryPoint_ID_Actual());
				if (card.getUY_TT_Box_ID() > 0){
					cardTrack.setUY_TT_Box_ID(card.getUY_TT_Box_ID());
					if (card.getLocatorValue() > 0) cardTrack.setLocatorValue(card.getLocatorValue());
				}
				if (card.getUY_TT_Seal_ID() > 0){
					cardTrack.setUY_TT_Seal_ID(card.getUY_TT_Seal_ID());
				}
				cardTrack.saveEx();

			}			
			
			if (isSMS){

				List<MTTConfigCardSMS> lines = config.getSMSLines(); //obtengo lineas de SMS a recorrer
				
				for (MTTConfigCardSMS smsLine: lines){
					
					MTTConfigCardSMS line = new MTTConfigCardSMS(getCtx(),smsLine.get_ID(),null);
					
					MTTActionCardSMS dsms = new MTTActionCardSMS(getCtx(), 0, get_TrxName());
					dsms.setUY_TT_ActionCard_ID(this.get_ID());
					dsms.setUY_TT_SMS_ID(this.getUY_TT_SMS_ID());
					dsms.setMobile(line.getMobile());
					dsms.setName(line.getName());
					dsms.saveEx();
										
				}				
			}
			
			this.setIsExecuted(true);
			this.saveEx();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return mensaje;
		
	}

}
