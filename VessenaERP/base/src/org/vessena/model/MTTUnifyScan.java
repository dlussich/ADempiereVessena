/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/09/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * org.openup.model - MTTUnifyScan
 * OpenUp Ltda. Issue #1173. 
 * Description: Modelo de scaneo de cuenta en documento de unificacion de cardcarriers.
 * @author Gabriel Vila - 12/09/2013
 * @see
 */
public class MTTUnifyScan extends X_UY_TT_UnifyScan {

	private static final long serialVersionUID = -7654196703783104954L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_UnifyScan_ID
	 * @param trxName
	 */
	public MTTUnifyScan(Properties ctx, int UY_TT_UnifyScan_ID, String trxName) {
		super(ctx, UY_TT_UnifyScan_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTUnifyScan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		String mensaje = null, action = "";
		HashMap<Integer, MTTBox> hashLockedBoxes = new HashMap<Integer, MTTBox>();
		MTTBox boxDestino = null;
		
		String trackDescription = "", trackObservaciones = "";
		
		try {
			
			if (this.getScanText() == null) return true;
			if (this.getScanText().trim().equalsIgnoreCase("")) return true;
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			MTTUnify unify = (MTTUnify)this.getUY_TT_Unify();
			
			int windowNo = 0; 
					
			if (unify.getTableModel() != null){
				windowNo =  unify.getTableModel().getTabModel().getWindowNo();
			}
			else if (this.getTableModel() != null){
				windowNo =  this.getTableModel().getTabModel().getWindowNo();
			}
			
			// Punto de distribucion actual
			MDeliveryPoint dpActual = (MDeliveryPoint)unify.getUY_DeliveryPoint();

			// Si no estoy en un punto de distribucion central, no permite unificar nada.			
			if (!dpActual.isCentral()){
				throw new AdempiereException("No se permite Unificar Card-Carriers en punto de distribución que no es Casa Central.");
			}
			
			MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), this.getScanText().trim(), null);
			if (incidencia == null){
				throw new AdempiereException("No existe Incidencia con ese Número.");
			}
			
			// Obtengo cuenta y verifico si esta en tracking
			MTTCard card = MTTCard.forIncidenciaAndDeliveryPoint(getCtx(),get_TrxName(), incidencia.get_ID(),dpActual.get_ID()) ;
			if (card == null){
				throw new AdempiereException("Esta Cuenta no fue Recepcionada.");
			}

			// Valido estado actual de cuenta
			MTTCardStatus cardStatus = (MTTCardStatus)card.getUY_TT_CardStatus();
			if (!cardStatus.getValue().equalsIgnoreCase("retenidacc")){
				throw new AdempiereException("Esta Cuenta no esta Retenida por Unificacion de Card-Carriers.");
			}

			// Verifico que esta cuenta pertenezca a una de las cajas de unificacion asociadas a este proceso.
			MTTBox box = (MTTBox)card.getUY_TT_Box();
			if (!box.isUnificaCardCarrier()){
				throw new AdempiereException(" Esta Cuenta no se encuentra en una Caja de Unificacion");
			}
			if (!unify.containsBox(box.get_ID())){
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
			MTTUnifyBox uBox = null; 

			boolean retener = false;
			if (!card.isDeliverable()){
				retener = true;
				card.setRetainedStatus(X_UY_TT_Card.RETAINEDSTATUS_Inconsistente);
			}
			else if (!card.validateDelivery()){
				retener = true;
				card.setRetainedStatus(X_UY_TT_Card.RETAINEDSTATUS_Destruccion);
			}

			boolean useLocatorValue = true;
			
			if (!retener){
				
				// Puede que la cuenta tenga una chequera asociada y por lo tanto debo guardarla en caja de comunicacion a usuario
				if (card.getUY_TT_ChequeraLine_ID() > 0){
					card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenidacom").get_ID());
					card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
					card.setIsDeliverable(true);
					card.setIsRetained(false);
					
					uBox = unify.getBoxAccountComunication(X_UY_TT_Box.BOXSTATUS_UnificacionCardCarrier);
					if (uBox == null){
						throw new AdempiereException(" Debe registrar una Caja para Cuentas Retenidas por Comunicacion a Usuario.\n" +
				                 					 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
				                 					 " Si es asi, registre una nueva caja.");
					}
					trackObservaciones = "Retenida por Comunicacion a Usuario en : " + dpActual.getName();
				}
				else{
					card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
					card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
					card.setIsDeliverable(true);
					card.setIsRetained(false);
					useLocatorValue = false;

					uBox = unify.getBoxDeliveryPoint(X_UY_TT_Box.BOXSTATUS_UnificacionCardCarrier, card.getUY_DeliveryPoint_ID());
					if (uBox == null){
						throw new AdempiereException(" Debe registrar una Caja con destino : " + dpDestino.getName() + ".\n" +
									                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
									                 " Si es asi, registre una nueva caja.");
					}
				}
				trackDescription = "Card-Carriers Unificados.";
				
			}
			else{
				card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenida").get_ID());
				card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				card.setIsDeliverable(false);
				card.setIsRetained(true);

				uBox = unify.getBoxRetention(X_UY_TT_Box.BOXSTATUS_UnificacionCardCarrier, card.getRetainedStatus());
				if (uBox == null){
					throw new AdempiereException(" Debe registrar una Caja para Cuentas Retenidas por " + card.getRetainedStatus() + ".\n" +
			                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
			                 " Si es asi, registre una nueva caja para retenciones.");
				}

				trackDescription = "Retenida en : " + dpActual.getName();
				trackObservaciones = ((card.getNotValidText() != null) ? card.getNotValidText() : "");

			}

			// Obtengo lista de cajas de unficacion donde se encuentran todos los demas cardcarriers de esta cuenta
			List<MTTBoxCard> boxcards = MTTBoxCard.getCardBoxes(getCtx(), card.get_ID(), get_TrxName());
			
			// Bloqueo todas las cajas y elimina la asociacion de cuenta con caja
			mensaje = " Obtenga los CardCarriers a Unificar de las siguientes Cajas : \n";
			for (MTTBoxCard boxcard: boxcards){
				
				MTTBox boxuni = (MTTBox)boxcard.getUY_TT_Box();
				if (!hashLockedBoxes.containsKey(boxuni.get_ID())){
					if (!boxuni.lock()){
						throw new AdempiereException(" La caja : " + boxuni.getValue() + " esta bloqueada por otro usuario en este momento.\n" +
								 " Por favor aguarde unos instantes y reintente la operación."); 
					}
					hashLockedBoxes.put(boxuni.get_ID(), boxuni);
				}
						
				mensaje += " Caja : " + boxuni.getValue() + " - Ubicacion : " + boxcard.getLocatorValue() + "\n";

				boxuni.updateQtyCount(1, false, config.getTopBox(), X_UY_TT_Box.BOXSTATUS_UnificacionCardCarrier, 
						X_UY_TT_Box.BOXSTATUS_Cerrado, false, false, windowNo, false, null);
				
				action = " delete from uy_tt_boxcard where uy_tt_box_id =" + boxuni.get_ID() +
						 " and uy_tt_card_id =" + card.get_ID();
				DB.executeUpdateEx(action, get_TrxName());

				boxuni.refresh(false, true);
			}
			
			// Bloqueo caja destino
			boxDestino = (MTTBox)uBox.getUY_TT_Box();
			if (!boxDestino.lock()){
				throw new AdempiereException(" La caja : " + boxDestino.getValue() + " esta bloqueada por otro usuario en este momento.\n" +
						                     " Por favor aguarde unos instantes y reintente la operación."); 
			}
			
			// Obtengo nueva ubicacion para cuenta
			int locatorValue = boxDestino.updateQtyCount(1, true, config.getTopBox(), 
					X_UY_TT_Box.BOXSTATUS_UnificacionCardCarrier, X_UY_TT_Box.BOXSTATUS_Cerrado, false, false, windowNo, false, null);				
			
			// Aviso al usuario de que cajas debe tomar los card carriers
			ADialog.info (windowNo, null, mensaje);
			
			// Aviso al usuario en que caja destino y ubicacion tiene que guardar la cuenta
			if (card.isDeliverable()){
				
				cardStatus = (MTTCardStatus)card.getUY_TT_CardStatus();
				if (cardStatus.getValue().equalsIgnoreCase("recepcionada")){
					mensaje = " Guardar en Caja " + boxDestino.getValue();
					mensaje += " con Destino : " + dpDestino.getName();
				}
				else if (cardStatus.getValue().equalsIgnoreCase("retenidacom")){
					mensaje = " Guardar en Caja para Comunicacion a Usuario : " + boxDestino.getValue()  + "\n";
					mensaje += " Ubicacion = " + locatorValue;
				}
			}
			else{
				mensaje = " Cuenta Retenida por : " + card.getNotValidText() + "\n";
				mensaje += " Guardar en Caja de Retencion : " + boxDestino.getValue() + "\n";
				mensaje += " Ubicacion = " + locatorValue;
			}
			
			if (useLocatorValue){
				ADialog.info (windowNo, null, mensaje);	
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
			bCard.setAD_User_ID(unify.getAD_User_ID());
			bCard.setUY_R_Reclamo_ID(card.getUY_R_Reclamo_ID());
			bCard.saveEx();
			
			// Desbloqueo caja destino
			boxDestino.unlock(get_TrxName());
			
			// Desbloqueo todas las cajas de unficacion
			for (MTTBox boxlock: hashLockedBoxes.values()){
				boxlock.unlock(get_TrxName());
			}
			
			hashLockedBoxes = null;
			boxDestino = null;
			
			// Tracking en cuenta
			MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
			cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
			cardTrack.setAD_User_ID(unify.getAD_User_ID());
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
			
			// Asocio datos de cuenta y caja al scan
			this.setUY_TT_Card_ID(card.get_ID());
			this.setUY_TT_Box_ID(card.getUY_TT_Box_ID());
			this.setLocatorValue(card.getLocatorValue());
			
			// Todo bien, muestro cuenta en grilla de recepcionadas.
			MTTUnifyCard uc = new MTTUnifyCard(getCtx(), 0, get_TrxName());
			uc.setIsValid(card.isDeliverable());
			uc.setUY_TT_Unify_ID(this.getUY_TT_Unify_ID());
			uc.setScanText(this.getScanText());
			uc.setCardDestination(card.getCardDestination());
			uc.setUY_DeliveryPoint_ID(card.getUY_DeliveryPoint_ID());
			uc.setDateTrx(new Timestamp(System.currentTimeMillis()));
			uc.setUY_TT_Card_ID(card.get_ID());
			uc.setUY_TT_Box_ID(card.getUY_TT_Box_ID());
			uc.setCardAction(card.getCardAction());
			uc.setProductoAux(card.getProductoAux());
			uc.setGAFCOD(card.getGAFCOD());
			uc.setGAFNOM(card.getGAFNOM());
			uc.setMLCod(card.getMLCod());
			uc.setCreditLimit(card.getCreditLimit());
			uc.setGrpCtaCte(card.getGrpCtaCte());
			uc.setIsRetained(!card.isDeliverable());
			uc.setInvalidText(card.getNotValidText());
			uc.saveEx();

			if (!useLocatorValue){
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
			// Me aseguro de desbloquer cajas utilizadas
			if (hashLockedBoxes != null){
				for (MTTBox boxlock: hashLockedBoxes.values()){
					boxlock.unlock(null);
				}
			}
			if (boxDestino != null){
				boxDestino.unlock(null);	
			}
			
			throw new AdempiereException(e.getMessage());
		}

		return true;

	}

	
}
