/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 06/10/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * org.openup.model - MTTComunicaScan
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de escaneo de cuenta en proceso de cuentas en comunicacion a usuario.
 * @author Gabriel Vila - 06/10/2013
 * @see
 */
public class MTTComunicaScan extends X_UY_TT_ComunicaScan {

	private static final long serialVersionUID = -7537392835579472977L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ComunicaScan_ID
	 * @param trxName
	 */
	public MTTComunicaScan(Properties ctx, int UY_TT_ComunicaScan_ID,
			String trxName) {
		super(ctx, UY_TT_ComunicaScan_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTComunicaScan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		String mensaje = null, action = "";
		MTTBox boxOrigen = null;
		MTTBox boxDestino = null;
		
		String trackDescription = "", trackObservaciones = "";

		try {

			if (this.getScanText() == null) return true;
			if (this.getScanText().trim().equalsIgnoreCase("")) return true;
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			MTTComunica comunica = (MTTComunica)this.getUY_TT_Comunica();
			
			int windowNo = 0; 
					
			if (comunica.getTableModel() != null){
				windowNo =  comunica.getTableModel().getTabModel().getWindowNo();
			}
			else if (this.getTableModel() != null){
				windowNo =  this.getTableModel().getTabModel().getWindowNo();
			}
			
			// Punto de distribucion actual
			MDeliveryPoint dpActual = (MDeliveryPoint)comunica.getUY_DeliveryPoint();

			// Si no estoy en un punto de distribucion central, no permite unificar nada.			
			if (!dpActual.isCentral()){
				throw new AdempiereException("No se permite ejecutar este proceso en punto de distribución que no es Casa Central.");
			}
			
			// Obtengo cuenta y verifico si esta en tracking
			//Sumo el delivery point a la busqueda de cuenta para evitar errores.

			MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), this.getScanText().trim(), null);
			if (incidencia == null){
				throw new AdempiereException("No existe Incidencia con ese Número.");
			}
			
			MTTCard card = MTTCard.forIncidenciaAndDeliveryPoint(getCtx(), get_TrxName(), incidencia.get_ID(), dpActual.get_ID());
			if (card == null){
				throw new AdempiereException("Esta Cuenta no fue Recepcionada.");
			}

			// Valido estado actual de cuenta
			MTTCardStatus cardStatus = (MTTCardStatus)card.getUY_TT_CardStatus();
			if (!cardStatus.getValue().equalsIgnoreCase("retenidacom")){
				throw new AdempiereException("Esta Cuenta no esta Retenida para Comunicacio a Usuario.");
			}

			// Verifico que esta cuenta pertenezca a una de las cajas de comunicacion asociadas a este proceso.
			boxOrigen = (MTTBox)card.getUY_TT_Box();
			if (!boxOrigen.isComunicaUsuario()){
				throw new AdempiereException(" Esta Cuenta no se encuentra en una Caja de Comunicacion a Usuario");
			}
			if (!comunica.containsBox(boxOrigen.get_ID())){
				throw new AdempiereException(" Esta Cuenta no se encuentra en una Caja asociada a este Proceso");
			}

			// Verifico cuenta retenida
			if (!card.validateDelivery()){
				card.setIsDeliverable(false);
				card.setIsRetained(true);
				card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenida").get_ID());
				card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				card.saveEx();
			}

			// Obtengo caja destino considerando si la cuenta queda retenida o no
			MDeliveryPoint dpDestino = (MDeliveryPoint)card.getUY_DeliveryPoint();
			MTTComunicaBox cBox = null; 

			boolean retener = false;
			if (!card.isDeliverable()){
				retener = true;
				card.setRetainedStatus(X_UY_TT_Card.RETAINEDSTATUS_Inconsistente);
			}
			else if (!card.validateDelivery()){
				retener = true;
				card.setRetainedStatus(X_UY_TT_Card.RETAINEDSTATUS_Destruccion);
			}
			
			if (!retener){
				card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
				card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				card.setIsDeliverable(true);
				card.setIsRetained(false);

				cBox = comunica.getBoxDeliveryPoint(X_UY_TT_Box.BOXSTATUS_ComunicacionUsuario, card.getUY_DeliveryPoint_ID());
				if (cBox == null){
					throw new AdempiereException(" Debe registrar una Caja con destino : " + dpDestino.getName() + ".\n" +
								                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
								                 " Si es asi, registre una nueva caja.");
				}
				
				trackDescription = "Comunicacion a Usuario : Procesada Correctamente";
				trackObservaciones = "Recepcionada en : " + dpActual.getName();
				
			}
			else{
				card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenida").get_ID());
				card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				card.setIsDeliverable(false);
				card.setIsRetained(true);

				cBox = comunica.getBoxRetention(X_UY_TT_Box.BOXSTATUS_ComunicacionUsuario, card.getRetainedStatus());
				if (cBox == null){
					throw new AdempiereException(" Debe registrar una Caja para Cuentas Retenidas.\n" +
			                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
			                 " Si es asi, registre una nueva caja para retenciones.");
				}
				
				trackDescription = "Retenida en : " + dpActual.getName();
				trackObservaciones = ((card.getNotValidText() != null) ? card.getNotValidText() : "");
			}
			
			// Bloqueo caja de comunicacion a usuario 
			if (!boxOrigen.lock()){
				throw new AdempiereException(" La caja : " + boxOrigen.getValue() + " esta bloqueada por otro usuario en este momento.\n" +
						 " Por favor aguarde unos instantes y reintente la operación."); 
			}

			// Bloqueo caja destino
			boxDestino = (MTTBox)cBox.getUY_TT_Box();
			if (!boxDestino.lock()){
				throw new AdempiereException(" La caja : " + boxDestino.getValue() + " esta bloqueada por otro usuario en este momento.\n" +
						                     " Por favor aguarde unos instantes y reintente la operación."); 
			}

			// Resto una cuenta a la caja de comunicacion
			boxOrigen.updateQtyCount(1, false, config.getTopBox(), X_UY_TT_Box.BOXSTATUS_ComunicacionUsuario, 
									X_UY_TT_Box.BOXSTATUS_Cerrado, false, false, windowNo, false, null);
			
			// Elimino asociacion de cuenta con caja de comunicacion
			action = " delete from uy_tt_boxcard where uy_tt_box_id =" + boxOrigen.get_ID() +
					 " and uy_tt_card_id =" + card.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
			// Suma cuenta en caja destino
			int locatorValue = boxDestino.updateQtyCount(1, true, config.getTopBox(), 
					X_UY_TT_Box.BOXSTATUS_ComunicacionUsuario, X_UY_TT_Box.BOXSTATUS_Cerrado, false, false, windowNo, false, null);				
			
			// Aviso al usuario en que caja destino y ubicacion tiene que guardar la cuenta
			if (card.isDeliverable()){
				cardStatus = (MTTCardStatus)card.getUY_TT_CardStatus();
				mensaje = " Guardar en Caja " + boxDestino.getValue();
				mensaje += " con Destino : " + dpDestino.getName();
			}
			else{
				mensaje = " Cuenta RETENIDA por : " + card.getNotValidText() + "\n";
				mensaje += " Guardar en Caja de RETENCION : " + boxDestino.getValue() + "\n";
				mensaje += " UBICACION = " + locatorValue;
			}
			
			// Asocio caja con cuenta
			card.setUY_TT_Box_ID(boxDestino.get_ID());
			card.setLocatorValue(locatorValue);
			card.saveEx();
			
			MTTBoxCard bCard = new MTTBoxCard(getCtx(), 0, get_TrxName());
			bCard.setUY_TT_Box_ID(boxDestino.get_ID());
			bCard.setUY_TT_Card_ID(card.get_ID());
			bCard.setLocatorValue(card.getLocatorValue());
			bCard.setName(card.getName());			
			bCard.setDateTrx(new Timestamp(System.currentTimeMillis()));
			bCard.setAD_User_ID(comunica.getAD_User_ID());
			bCard.setUY_R_Reclamo_ID(card.getUY_R_Reclamo_ID());
			bCard.saveEx();

			if (!card.isDeliverable()){
				ADialog.info (windowNo, null, mensaje);	
			}

			// Desbloqueo caja destino
			boxDestino.unlock(get_TrxName());

			// Desbloqueo caja origen
			boxOrigen.unlock(get_TrxName());
			
			boxOrigen = null;
			boxDestino = null;
			
			// Asocio datos de cuenta y caja al scan
			this.setUY_TT_Card_ID(card.get_ID());
			this.setUY_TT_Box_ID(card.getUY_TT_Box_ID());
			this.setLocatorValue(card.getLocatorValue());
			
			// Tracking en cuenta
			MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
			cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
			cardTrack.setAD_User_ID(comunica.getAD_User_ID());
			cardTrack.setDescription(trackDescription);
			cardTrack.setobservaciones(trackObservaciones);
			cardTrack.setUY_TT_Card_ID(card.get_ID());
			cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
			cardTrack.setUY_DeliveryPoint_ID_Actual(card.getUY_DeliveryPoint_ID_Actual());
			if (card.getUY_TT_Box_ID() > 0){
				cardTrack.setUY_TT_Box_ID(card.getUY_TT_Box_ID());
				if (card.getLocatorValue() > 0) cardTrack.setLocatorValue(card.getLocatorValue());
			}
			cardTrack.saveEx();
			
			// Todo bien, muestro cuenta en grilla de recepcionadas.
			MTTComunicaCard cc = new MTTComunicaCard(getCtx(), 0, get_TrxName());
			cc.setIsValid(card.isDeliverable());
			cc.setUY_TT_Comunica_ID(this.getUY_TT_Comunica_ID());
			cc.setScanText(this.getScanText());
			cc.setCardDestination(card.getCardDestination());
			cc.setUY_DeliveryPoint_ID(card.getUY_DeliveryPoint_ID());
			cc.setDateTrx(new Timestamp(System.currentTimeMillis()));
			cc.setUY_TT_Card_ID(card.get_ID());
			cc.setUY_TT_Box_ID(card.getUY_TT_Box_ID());
			cc.setCardAction(card.getCardAction());
			cc.setProductoAux(card.getProductoAux());
			cc.setGAFCOD(card.getGAFCOD());
			cc.setGAFNOM(card.getGAFNOM());
			cc.setMLCod(card.getMLCod());
			cc.setCreditLimit(card.getCreditLimit());
			cc.setGrpCtaCte(card.getGrpCtaCte());
			cc.setIsRetained(!card.isDeliverable());
			cc.setInvalidText(card.getNotValidText());
			cc.saveEx();

			if (card.isDeliverable()){
				// Commit para no bloquear caja con destino que no se debe bloquear ya que no importa posicion
				Trx trxAux = Trx.get(get_TrxName(), false);
				if (trxAux != null){
					trxAux.commit();
					ADialog.info (windowNo, null, mensaje);
				}
			}
			
		} 
		catch (Exception e) {
			Trx trxAux = Trx.get(get_TrxName(), false);
			if (trxAux != null){
				trxAux.rollback();
			}
			if (boxOrigen != null) boxOrigen.unlock(null);
			if (boxDestino != null) boxDestino.unlock(null);

			throw new AdempiereException(e.getMessage());
		}

		return true;
	
	}
	
	
}
