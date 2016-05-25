/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/**
 * @author gbrust
 *
 */
public class MTTSealLoadLine extends X_UY_TT_SealLoadLine {
	

	@Override
	protected boolean beforeSave(boolean newRecord) throws AdempiereException {				
		
		MTTSeal seal = null;
		
		try {
			
			//CABEZAL DE LA CARGA
			MTTSealLoad hdr = new MTTSealLoad(this.getCtx(), this.getUY_TT_SealLoad_ID(), this.get_TrxName());
			
			MDeliveryPoint dpActual = new MDeliveryPoint (getCtx(), hdr.getUY_DeliveryPoint_ID_From(), null);
			MDeliveryPoint dpDestino = new MDeliveryPoint(getCtx(), hdr.getUY_DeliveryPoint_ID(), null);
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			
			//int windowNo = hdr.getTableModel().getTabModel().getWindowNo();
			int windowNo = 0;
			
			//TOPES
			int topeSeal = MTTConfig.forValue(this.getCtx(), this.get_TrxName(), "tarjeta").getTopSeal();
			int topeBox = MTTConfig.forValue(this.getCtx(), this.get_TrxName(), "tarjeta").getTopBox();	
			
			//ESTADOS DE TARJETAS
			int statusRetenidaID = MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "retenida").get_ID();
			int statusPendEnvioID = MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "pendenvio").get_ID();
				
			MTTCard card = new MTTCard(getCtx(), this.getUY_TT_Card_ID(), this.get_TrxName());		
			MTTBox cardBox = null;
			
			if (card.getUY_TT_Box_ID() > 0){
				cardBox = (MTTBox)card.getUY_TT_Box();
			}
			else{
				List<MTTBoxCard> cardBoxes = MTTBoxCard.getCardBoxes(getCtx(), card.get_ID(), get_TrxName()); 
				if (cardBoxes != null){
					if (cardBoxes.size() > 0){
						cardBox = (MTTBox) ((MTTBoxCard)cardBoxes.get(0)).getUY_TT_Box();
						card.setUY_TT_Box_ID(cardBox.get_ID());
					}
				}
			}
			
			if (cardBox == null){
				throw new AdempiereException("No se pudo obtener caja para esta cuenta.");
			}
			
			//Vuelvo a hacer el proceso de validacion de tarjeta
			if (!card.validateDelivery()){
				if (!config.isForzed()){
					card.setIsDeliverable(false);
					card.setIsRetained(true);
				}
				else{
					card.setIsDeliverable(true);
					card.setIsRetained(false);
				}
			}
			
			if(card.isDeliverable()){

				//Aca verifico que la tarjeta leida se encuentre en alguna de las cajas que se definieron en el cabezal de la carga
				if(cardBox.isDestiny()){
					
					//Si la caja tiene destino y este es distinto al destino de la cuenta, esto es un error
					//Comento esta verificacion a pedido de Vanina Grunberg por entender que es incorrecta.
					/*if(card.getUY_DeliveryPoint_ID() != cardBox.getUY_DeliveryPoint_ID_To()){
						if (!config.isForzed()){
							ADialog.info(0,null,"La cuenta leida pertenece a una caja que no tiene punto de distribucion de destino definido");
							throw new AdempiereException("La cuenta leida pertenece a una caja que no tiene punto de distribucion de destino definido");
							
						}
					}*/				
				} 
								
				if(MTTSealLoadBox.existsBoxForSealLoad(getCtx(), this.get_TrxName(), this.getUY_TT_SealLoad_ID(), cardBox.get_ID())){					
										
					//Primero actualizo el contador de cuentas en el bolsin
					seal = (MTTSeal) hdr.getUY_TT_Seal();
					//Lo dejo carga bolsin porque recien lo dejo pendiente de envio cuando se completa la carga
					seal.updateQtyBook(1, true, topeSeal, MTTSeal.SEALSTATUS_CargaBolsin, MTTSeal.SEALSTATUS_CargaBolsin);
					seal.saveEx();
					
					//Luego actualizo el contador del cabezal de la carga
					hdr.setQtyBook(new BigDecimal(seal.getQtyCount()));
					hdr.saveEx();				
					
					// Seteo atributos a al cuenta				
					card.setUY_TT_CardStatus_ID(statusPendEnvioID);
					card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
					card.setUY_TT_Seal_ID(seal.get_ID());
					card.setIsDeliverable(true);
					card.setIsRetained(false);
					card.setUY_DeliveryPoint_ID_Actual(dpActual.get_ID());
					card.setIsReceived(false);
					card.saveEx();
					
					//Desligo la tarjeta de la caja contenedora
					DB.executeUpdateEx("UPDATE UY_TT_Card SET UY_TT_Box_ID = null, locatorvalue=null  WHERE UY_TT_Card_ID = " + card.get_ID(), this.get_TrxName());
					
					//Desligo la cuenta de la uy_tt_boxcard
					MTTBoxCard bcard = MTTBoxCard.forBoxIDAndCardID(this.getCtx(), this.get_TrxName(), cardBox.get_ID(), card.get_ID());
					if(bcard != null){
						bcard.deleteEx(true);
					}
					
					//Le quito 1 a la caja contenedora
					cardBox.updateQtyCount(1, false, topeBox, MTTBox.BOXSTATUS_Cerrado, MTTBox.BOXSTATUS_CargaBolsin, false, false, 0, true, null);
					
					//Asocio bolsin con cuenta
					MTTSealCard sCard = new MTTSealCard(getCtx(), 0, get_TrxName());				
					sCard.setUY_TT_Card_ID(card.get_ID());	
					sCard.setUY_TT_Seal_ID(seal.get_ID());
					sCard.setName(card.getName());
					sCard.setDateTrx(new Timestamp(System.currentTimeMillis()));
					sCard.setAD_User_ID(hdr.getAD_User_ID());				
					sCard.saveEx();
					
					// Tracking en Cuenta
					MTTCardTracking cardTrack = new MTTCardTracking(this.getCtx(), 0, this.get_TrxName());
					cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
					cardTrack.setAD_User_ID(hdr.getAD_User_ID());
					cardTrack.setDescription("Cargada en Bolsin : " + seal.getValue());
					cardTrack.setobservaciones("Para Destino : " + dpDestino.getName());
					cardTrack.setUY_TT_Card_ID(card.get_ID());
					cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
					cardTrack.setUY_DeliveryPoint_ID_Actual(card.getUY_DeliveryPoint_ID_Actual());
					if (card.getUY_TT_Seal_ID() > 0) cardTrack.setUY_TT_Seal_ID(card.getUY_TT_Seal_ID());
					cardTrack.saveEx();
					
				}else{
					ADialog.info(0,null,"Cuenta ubicada en Caja (" + cardBox.getValue() + ") NO asociada a este Proceso");
					throw new AdempiereException("Cuenta ubicada en Caja (" + cardBox.getValue() + ") NO asociada a este Proceso");
				}		
				
			}else{
				//OJO!: IsValid en true significa que es retenida la cuenta
				this.setIsValid(true);
				this.setNotDeliverableAction(card.getNotDeliverableAction());
				this.setInvalidText(card.getNotValidText());	
				
				// Seteo atributos de cuenta
				card.setIsDeliverable(false);
				card.setIsRetained(true);
				card.setNotValidText(card.getNotValidText());
				card.setRetainedStatus(X_UY_TT_Card.RETAINEDSTATUS_Destruccion);
				card.setUY_DeliveryPoint_ID_Actual(dpActual.get_ID());
				card.saveEx();
				
				//Guardo las cuentas a retener en la caja de retenciones

				//Acá tengo que verificar que la caja de RETENCION este en estado CERRADA O EN CARGA DE BOLSIN en la grilla de cajas y tenga disponibilidad
				MTTBox cajaRetencion = MTTSealLoadBox.getBoxRetainedCloseAndEnCarga(this.getCtx(), this.get_TrxName(), this.getUY_TT_SealLoad_ID(), card.getRetainedStatus());			
				
				if(cajaRetencion == null){
					ADialog.info(0,null," Debe registrar una Caja para Cuentas Retenidas por Destruccion.\n" +
			                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
			                 " Si es asi, registre una nueva caja.");
					throw new AdempiereException(" Debe registrar una Caja para Cuentas Retenidas por Destruccion.\n" +
			                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
			                 " Si es asi, registre una nueva caja.");
				}
				
				if(!cajaRetencion.isComplete()){
					
					//Acá tengo que sumar 1 al contador de cuentas guardadas en la caja de retenciones actual				
					int locatorValue = cajaRetencion.updateQtyCount(1, true, topeBox, MTTBox.BOXSTATUS_CargaBolsin, MTTBox.BOXSTATUS_Cerrado, true, true, windowNo, true, null);	
					
					//Le quito 1 a la caja contenedora
					cardBox.updateQtyCount(1, false, topeBox, MTTBox.BOXSTATUS_Cerrado, MTTBox.BOXSTATUS_CargaBolsin, false, false, 0, true, null);
					
					//Desligo la cuenta de la uy_tt_boxcard
					MTTBoxCard bcard = MTTBoxCard.forBoxIDAndCardID(this.getCtx(), this.get_TrxName(), cardBox.get_ID(), card.get_ID());
					if(bcard != null){
						bcard.deleteEx(true);
					}
					
					//Guardo la cuenta a retener en la caja de retencion ABIERTA y con disponibilidad		
					//Primero que nada guardo la caja contenedora anterior y la nuevo caja de retencion a la linea de la carga			
					this.setUY_TT_Box_ID(card.getUY_TT_Box_ID());				
					this.setUY_TT_Box_ID_1(cajaRetencion.get_ID());	
															
					// Asocio caja con cuenta
					card.setUY_TT_Box_ID(cajaRetencion.get_ID());
					card.setLocatorValue(locatorValue);
					card.setUY_TT_CardStatus_ID(statusRetenidaID);
					card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
					card.saveEx();
					
					MTTBoxCard bCard = new MTTBoxCard(getCtx(), 0, get_TrxName());
					bCard.setUY_TT_Box_ID(cajaRetencion.get_ID());
					bCard.setUY_TT_Card_ID(card.get_ID());
					bCard.setLocatorValue(card.getLocatorValue());
					bcard.setName(card.getName());
					bCard.setDateTrx(new Timestamp(System.currentTimeMillis()));
					bCard.setAD_User_ID(hdr.getAD_User_ID());
					bCard.setUY_R_Reclamo_ID(card.getUY_R_Reclamo_ID());
					bCard.saveEx();				
					
					// Tracking en Cuenta
					MTTCardTracking cardTrack = new MTTCardTracking(this.getCtx(), 0, this.get_TrxName());
					cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
					cardTrack.setAD_User_ID(hdr.getAD_User_ID());
					cardTrack.setDescription("Retenida en : " + dpActual.getName());
					cardTrack.setobservaciones(((card.getNotValidText() != null) ? card.getNotValidText() : ""));
					cardTrack.setUY_TT_Card_ID(card.get_ID());
					cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
					cardTrack.setUY_DeliveryPoint_ID_Actual(card.getUY_DeliveryPoint_ID_Actual());
					if (card.getUY_TT_Box_ID() > 0){
						cardTrack.setUY_TT_Box_ID(card.getUY_TT_Box_ID());
						if (card.getLocatorValue() > 0) cardTrack.setLocatorValue(card.getLocatorValue());
					}
					cardTrack.saveEx();
					
				}else{
					ADialog.info(0,null," Debe registrar una Caja para Cuentas Retenidas por Destruccion.\n" +
			                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
			                 " Si es asi, registre una nueva caja.");
					throw new AdempiereException(" Debe registrar una Caja para Cuentas Retenidas por Destruccion.\n" +
			                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
			                 " Si es asi, registre una nueva caja.");
				}
			}	
			
		} 
		catch (Exception e) {
			Trx trxAux = Trx.get(get_TrxName(), false);
			if (trxAux != null){
				trxAux.rollback();
			}
			if (seal != null){
				seal.unlock(null);
			}
			
			throw new AdempiereException(e.getMessage());
		}

		return true;
		
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9171759890741266287L;

	/**
	 * @param ctx
	 * @param UY_TT_SealLoadLine_ID
	 * @param trxName
	 */
	public MTTSealLoadLine(Properties ctx, int UY_TT_SealLoadLine_ID,
			String trxName) {
		super(ctx, UY_TT_SealLoadLine_ID, trxName);
		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTSealLoadLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}

}
