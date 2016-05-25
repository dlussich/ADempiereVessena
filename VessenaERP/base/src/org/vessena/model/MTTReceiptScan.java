/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 30/08/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.window.FDialog;
import org.compiere.apps.ADialog;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.openup.util.ItalcredSystem;

/**
 * org.openup.model - MTTReceiptScan
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de lectura de cuenta.
 * @author Gabriel Vila - 30/08/2013
 * @see
 */
public class MTTReceiptScan extends X_UY_TT_ReceiptScan {

	private static final long serialVersionUID = -7252055987069423095L;

	// Bajo que concepto se guarda una cuenta en una box al ser recepcionada.
	private static final int NO_BOX = 0;
	private static final int FOR_RETENTION = 1;
	private static final int FOR_UNIFICATION = 2;
	private static final int FOR_DESTINY = 3;
	private static final int FOR_DISTRIBUTION = 4;
	private static final int FOR_RETURN = 5;
	private static final int FOR_COMUNICATION = 6;	
	private static final int FOR_IMPRESION_VALE = 7;	
	
	private boolean loadCardPlastics = false;
	private int boxDestinyMode = NO_BOX;
	private String trackDescription = "";
	private String trackObservations = "";

	private MTTCard card = null;

	private MTTBox boxDestino = null;
	private boolean useLocatorValue = false;
	private int windowNo = 0;
	private String descriptionDialog = "";
	
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ReceiptScan_ID
	 * @param trxName
	 */
	public MTTReceiptScan(Properties ctx, int UY_TT_ReceiptScan_ID,
			String trxName) {
		super(ctx, UY_TT_ReceiptScan_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTReceiptScan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		String mensaje = null;
		
		try {
			
			if ((this.getScanText() == null) || (this.getScanText().trim().equalsIgnoreCase(""))){
				return true;
			}
			
			if (this.getTableModel() != null){
				windowNo = this.getTableModel().getTabModel().getWindowNo();
			}
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			MTTReceipt receipt = (MTTReceipt)this.getUY_TT_Receipt();
			MTTSeal seal = (MTTSeal)receipt.getUY_TT_Seal();
			MDeliveryPoint dpSeal = (MDeliveryPoint)seal.getUY_DeliveryPoint();
			
			// Acciones y procesos segun Punto de Recepcion actual
			MDeliveryPoint dpActual = (MDeliveryPoint)receipt.getUY_DeliveryPoint();
			
			// Instancia cuenta si esta abierta para tracking.
			
			/*INI openup Sylvie Bouissa - 03/10/2014 Issue #3204  
			-- Se setean controles adicionales para la recepción (forAccountOpenAndUnreceipted) 
			y se toma en cuenta únicamente el método forAccountOpenAndUnreceipted"
			por lo tanto se comentan lo siguientes controles.
			*/
			//card = MTTCard.forAccountOpen(getCtx(), this.getScanText(), get_TrxName());
			
			//INI openpu Sylvie 19/03/2015 Issue# 3804
			//Se grega control para que si la recepcion es desde fdu solo contemple las cuentas que tienen estado PENDIENTE DE RECEPCION
			boolean fromFDU = false;
			if (dpActual.isCentral()){
				if (!seal.isOwn() && !dpSeal.isPostOffice()){
					fromFDU = true;
				}
			}
			
			card = MTTCard.forAccountOpenAndUnreceipted(getCtx(), this.getScanText(), get_TrxName(),fromFDU);
			//FIN openup Sylvie Bouissa - 03/10/2014 Issue #3204
			
			/*INI openup Sylvie bouissa 22/10/2014 # issue 3162
			-- 28/10/2012 Issue 3162
			 independientemente del pto de origen o pto de destino se necesita recepcionar cuentas NO recepcionadas   
			--> pregunto si la cuenta que me trae forAccountOpen esta como recepcionada --> De ser así obtengo la ultima sin recepcionar
			*/
			
//			if (card!=null && card.isReceived()){
//				card = null;
//				//--> Busco la ultima cuenta que NO esté recepcionada -- Se crea esta func. en MTTCard
//					card = MTTCard.forAccountOpenAndUnreceipted(getCtx(), this.getScanText(), get_TrxName());
//			}								
			// FIN openup Sylvie bouissa 22/10/2014 # issue 3162
			
			if(card==null){
				//Sylvie Bouissa 30/10/2014 Issue # 3142 -- Se indica el nro de cuenta leida de la cual no se encontró tracking
				throw new AdempiereException("Esta Cuenta no tiene tracking ("+this.getScanText() +")");
			}
			if (dpActual.isCentral()){
				
				boolean reprocesandoCuentas = true;
				
				// Si el bolsin que estoy escaneando viene de fdu (se indica que no se esta reprocesando cuentas !!)
				if (!seal.isOwn() && !dpSeal.isPostOffice()){
					// Antes que nada valido que no me haya quedado una cuenta ya scaneada con plasticos sin seleccionar.
					mensaje = receipt.validateSelectedPlastics();
					if (mensaje != null){
						throw new AdempiereException(mensaje);
					}
					reprocesandoCuentas = false;
				}
				//cuando recepciono en casa central seteo el levante en null para que no sea agarrado por futuras corridas de currier
				card.setLevante(null);
				mensaje = this.processCentralPoint(receipt, reprocesandoCuentas, config);
			}
			else{
				mensaje = this.processSucursal(receipt, config);
			}
			
			if (boxDestinyMode == NO_BOX){
				if (mensaje == null) mensaje = "No se pudo obtener Caja para Recepcion."; 
				throw new AdempiereException(mensaje);
			}

			// Marco esta cuenta como recepcionada
			card.setIsReceived(true);
			
			
			// Guardo en caja, bloqueando si es necesaria la ubicacion
			this.putInBox(receipt, card, boxDestinyMode, config.getTopBox());
			
			// Actualizo estado de cuenta
			this.updateCardStatus(receipt, card, boxDestinyMode);
			
			// Actualizo asociacion cuenta - precinto
			DB.executeUpdateEx(" update uy_tt_card set uy_tt_seal_id = null " +
					           " where uy_tt_card_id =" + card.get_ID(), get_TrxName());
			DB.executeUpdateEx(" delete from uy_tt_sealcard where uy_tt_card_id =" + card.get_ID(), get_TrxName());
			
			// Actualizo datos finales de cuenta
			card.setUY_DeliveryPoint_ID_Actual(receipt.getUY_DeliveryPoint_ID());
			card.setDateAssign(new Timestamp(System.currentTimeMillis()));
			card.setDiasActual(0);
			card.setNeedPrint(false);
			
			// Guardo todos los cambios hechos en la cuenta
			card.saveEx();
			
			// Tracking en cuenta
			MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
			cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
			cardTrack.setAD_User_ID(receipt.getAD_User_ID());
			cardTrack.setDescription(trackDescription);
			cardTrack.setobservaciones(trackObservations);
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
			MTTReceiptCard rc = new MTTReceiptCard(getCtx(), 0, get_TrxName());
			rc.setIsValid(card.isDeliverable());
			rc.setIsRetained(!card.isDeliverable());
			rc.setInvalidText(card.getNotValidText());
			rc.setUY_TT_Receipt_ID(this.getUY_TT_Receipt_ID());
			rc.setScanText(this.getScanText());
			rc.setCardDestination(card.getCardDestination());
			rc.setUY_DeliveryPoint_ID(card.getUY_DeliveryPoint_ID());
			rc.setDateTrx(new Timestamp(System.currentTimeMillis()));
			rc.setUY_TT_Card_ID(card.get_ID());
			rc.setUY_TT_Box_ID(card.getUY_TT_Box_ID());
			if (card.getLocatorValue() > 0) rc.setLocatorValue(card.getLocatorValue());
			rc.setCardAction(card.getCardAction());
			rc.setProductoAux(card.getProductoAux());
			rc.setGAFCOD(card.getGAFCOD());
			rc.setGAFNOM(card.getGAFNOM());
			rc.setMLCod(card.getMLCod());
			rc.setCreditLimit(card.getCreditLimit());
			rc.setGrpCtaCte(card.getGrpCtaCte());
			rc.setAD_Table_ID(I_UY_TT_Card.Table_ID);
			rc.setRecord_ID(card.get_ID());
			if (rc.isRetained()){
				rc.setColorSelector("RED");
			}
			
			rc.saveEx();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return true;
	}


	/***
	 * SOLO PARA TESTING
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 03/09/2013
	 * @see
	 */
	@SuppressWarnings("unused")
	private MTTCard newAccountForTesting(){

		MTTCard card = null;
		
		try {

			// Instancia cuenta y valido que la misma este apta para recepcionarse
			ItalcredSystem it = new ItalcredSystem();
			card = it.getCustomerData(getCtx(), this.getScanText(), get_TrxName());
			
			if (card == null){
				throw new AdempiereException("No existe Cuenta definida en Financial con ese Código.");
			}
			
			card.setCardAction(X_UY_TT_Card.CARDACTION_Nueva);
			card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "pendrec").get_ID());
			card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
			card.saveEx();

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return card;
	
	}

	
	/***
	 * Pone una cuenta en una box segun parametros recibidos. 
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @param receipt
	 * @param card
	 * @param forConcept
	 * @param topBoxCount
	 */
	private void putInBox(MTTReceipt receipt, MTTCard card, int forConcept, int topBoxCount){
	
		MTTReceiptBox rBox = null;
		
		try {

			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			MDeliveryPoint delRedPagos = new MDeliveryPoint(getCtx(), config.getUY_DeliveryPoint_ID(), null);
			
			switch (forConcept) {
				
				case FOR_RETENTION:
					String mensaje = card.getRetainedStatus();
					if (mensaje == null) mensaje = "Destruccion"; 
					
					rBox = receipt.getBoxRetention(X_UY_TT_Box.BOXSTATUS_RecepcionCuentas, card.getRetainedStatus());
					if (rBox == null){
						throw new AdempiereException(" Debe registrar una Caja para Cuentas Retenidas por " + mensaje.toUpperCase() + ".\n" +
									                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
									                 " Si es asi, registre una nueva caja.");
					}
					useLocatorValue = true;
					descriptionDialog = "Retenidas por " + mensaje + ".\n";  
					descriptionDialog += "Motivo : " + card.getNotValidText();
					break;
				
				case FOR_UNIFICATION:
					rBox = receipt.getBoxAccountUnification(X_UY_TT_Box.BOXSTATUS_RecepcionCuentas);
					if (rBox == null){
						throw new AdempiereException(" Debe registrar una Caja para Cuentas Retenidas por Unificacion de Card Carrier.\n" +
									                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
									                 " Si es asi, registre una nueva caja.");
					}
					useLocatorValue = true;
					break;

				case FOR_COMUNICATION:
					rBox = receipt.getBoxAccountComunication(X_UY_TT_Box.BOXSTATUS_RecepcionCuentas);
					if (rBox == null){
						throw new AdempiereException(" Debe registrar una Caja para Cuentas Retenidas por Comunicacion a Usuario.\n" +
									                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
									                 " Si es asi, registre una nueva caja.");
					}
					useLocatorValue = true;
					break;
				
				//Openup Sylvie Bouissa 22/12/2014 Issue# 3273
				//Se agrega para contemplar las tarjetas que se mandan la caja de impresion de vale
				case FOR_IMPRESION_VALE:
					rBox = receipt.getBoxAccountComunicationVale(X_UY_TT_Box.BOXSTATUS_RecepcionCuentas);
					if (rBox == null){
						throw new AdempiereException(" Debe registrar una Caja para Cuentas Retenidas por Impresión de Vale.\n" +
									                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
									                 " Si es asi, registre una nueva caja.");
					}
					useLocatorValue = true;
						break;

				case FOR_DESTINY:
					
					MDeliveryPoint dpDestino = (MDeliveryPoint)card.getUY_DeliveryPoint();
					if ((delRedPagos != null) && (delRedPagos.get_ID() > 0)){
						if (card.getSubAgencia() != null){
							dpDestino = MDeliveryPoint.forSubAgencyNo(getCtx(), card.getSubAgencia(), null);
							if (dpDestino == null){
								dpDestino = (MDeliveryPoint)card.getUY_DeliveryPoint();
							}
						}
					}
					
					rBox = receipt.getBoxDeliveryPoint(X_UY_TT_Box.BOXSTATUS_RecepcionCuentas, dpDestino.get_ID());
					if (rBox == null){
						throw new AdempiereException(" Debe registrar una Caja con destino : " + dpDestino.getName() + ".\n" +
									                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
									                 " Si es asi, registre una nueva caja.");
					}
					useLocatorValue = false;
					break;

				case FOR_RETURN:
					rBox = receipt.getBoxReturn(X_UY_TT_Box.BOXSTATUS_RecepcionCuentas);
					if (rBox == null){
						throw new AdempiereException(" Debe registrar una Caja para Devoluciones a Casa Central.\n" +
									                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
									                 " Si es asi, registre una nueva caja.");
					}
					useLocatorValue = true;
					break;

				case FOR_DISTRIBUTION:
					MDeliveryPoint dpOrigen = (MDeliveryPoint)card.getUY_DeliveryPoint();
					rBox = receipt.getBoxForDistribution(X_UY_TT_Box.BOXSTATUS_RecepcionCuentas, card.getUY_DeliveryPoint_ID());
					if (rBox == null){
						throw new AdempiereException(" Debe registrar una Caja para Ubicacion : " + dpOrigen.getName() + ".\n" +
									                 " Si ya tiene una registrada verifique que la misma no este Completa.\n" +
									                 " Si es asi, registre una nueva caja.");
					}
					useLocatorValue = true;
					break;
				
					
			}

			boxDestino = (MTTBox)rBox.getUY_TT_Box();
			int locatorValue = boxDestino.updateQtyCount(1, true, topBoxCount, 
					X_UY_TT_Box.BOXSTATUS_RecepcionCuentas, X_UY_TT_Box.BOXSTATUS_Cerrado, useLocatorValue, useLocatorValue,
					windowNo, useLocatorValue, descriptionDialog);
			
			// Asocio caja con cuenta
			card.setUY_TT_Box_ID(boxDestino.get_ID());
			if (useLocatorValue) card.setLocatorValue(locatorValue);
			card.saveEx();
			
			MTTBoxCard bCard = new MTTBoxCard(getCtx(), 0, get_TrxName());
			bCard.setUY_TT_Box_ID(boxDestino.get_ID());
			bCard.setUY_TT_Card_ID(card.get_ID());
			bCard.setLocatorValue(card.getLocatorValue());
			bCard.setName(card.getName());
			bCard.setDateTrx(new Timestamp(System.currentTimeMillis()));
			bCard.setAD_User_ID(receipt.getAD_User_ID());
			bCard.setPrintDoc1(card.isPrintDoc1());
			bCard.setPrintDoc2(card.isPrintDoc2());
			bCard.setPrintDoc3(card.isPrintDoc3());
			bCard.setPrintDoc4(card.isPrintDoc4());
			bCard.setUY_R_Reclamo_ID(card.getUY_R_Reclamo_ID());
			bCard.saveEx();

		} 
		catch (Exception e) {
			Trx trxAux = Trx.get(get_TrxName(), false);
			if (trxAux != null){
				trxAux.rollback();
			}
			if (boxDestino != null) boxDestino.unlock(null);
			
			throw new AdempiereException(e.getMessage());
		}
		
	}

	/***
	 * Actualiza estado de cuenta segun destino.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 18/09/2013
	 * @see
	 * @param receipt
	 * @param card
	 * @param forConcept
	 */
	private void updateCardStatus(MTTReceipt receipt, MTTCard card, int forConcept){
		
		MTTCardStatus cardStatus = null;
		
		try {
			switch (forConcept) {
				
				case FOR_RETENTION:
					cardStatus = MTTCardStatus.forValue(getCtx(), null, "retenida");
					break;
				
				case FOR_UNIFICATION:
					cardStatus = MTTCardStatus.forValue(getCtx(), null, "retenidacc");
					break;

				case FOR_COMUNICATION:
					cardStatus = MTTCardStatus.forValue(getCtx(), null, "retenidacom");
					break;
				//Openup Sylvie Bouissa 22/12/2014 Issue# 3273
				//Se agrega otro caso para contemplar las tarjetas que van a la caja de impresión de vale	
				case FOR_IMPRESION_VALE:
					cardStatus = MTTCardStatus.forValue(getCtx(), null, "retenidaimpvale");
					break;
					
				case FOR_DESTINY:
					cardStatus = MTTCardStatus.forValue(getCtx(), null, "recepcionada");
					break;

				case FOR_RETURN:
					cardStatus = MTTCardStatus.forValue(getCtx(), null, "recepcionada");
					break;

				case FOR_DISTRIBUTION:
					cardStatus = MTTCardStatus.forValue(getCtx(), null, "recepcionada");
					break;
					
			}

			if (cardStatus != null){
				card.setUY_TT_CardStatus_ID(cardStatus.get_ID());
				card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				card.saveEx();
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
	}
	/***
	 * Guarda recepcion de plasticos para esta cuenta.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 09/09/2013
	 * @see
	 * @param uyTTReceiptID
	 */
	private void saveReceiptPlastics(MTTCard card){
		
		try {
		
			List<MTTCardPlastic> plasticos = card.getPlastics();
			for (MTTCardPlastic plastico: plasticos){
				if (plastico.isSent()){
					MTTReceiptPlastic rp = new MTTReceiptPlastic(getCtx(), 0, get_TrxName());
					rp.setUY_TT_Card_ID(card.get_ID());
					rp.setUY_TT_CardPlastic_ID(plastico.get_ID());
					rp.setUY_TT_Receipt_ID(this.getUY_TT_Receipt_ID());
					rp.setUY_TT_ReceiptScan_ID(this.get_ID());
					rp.setValue(plastico.getValue());
					rp.setCedula(plastico.getCedula());
					rp.setName(plastico.getName());
					rp.setDueDate(plastico.getDueDate());
					rp.setIsTitular(plastico.isTitular());
					rp.setTipoSocio(plastico.getTipoSocio());
					rp.setIsSelected(plastico.isSelected());
					rp.saveEx();
				}
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return success;
		
		if ((this.getScanText() == null) || (this.getScanText().trim().equalsIgnoreCase(""))){
			return true;
		}
		
		MTTCard card = (MTTCard)this.getUY_TT_Card();
		
		// Si tengo que cargar la grilla de plasticos recepcionados, lo hago.
		if (this.loadCardPlastics){
			this.saveReceiptPlastics(card);
		}
		else{
			// Actualizo scanId de las lineas de recepcion de plasticos que no estan seleccionadas
			// Esta es una trampa para la verificacion de que el usuario seleccione plasticos en esta
			// cuenta unificada
			String action = " update uy_tt_receiptplastic set uy_tt_receiptscan_id =" + this.get_ID() +
					        " where uy_tt_card_id =" + card.get_ID() + " and isselected='N' ";
			DB.executeUpdate(action, get_TrxName());
			
		}
		
		this.loadCardPlastics = false;
		
		// Debo commitear transaccion si la caja no requiere posicion para desbloquear y mostrar mensaje
		if (!this.useLocatorValue){
			if (boxDestino != null){
				Trx trxAux = Trx.get(get_TrxName(), false);
				if (trxAux != null){
					trxAux.commit();
					this.showBoxMessage();
				}
			}
		}
		
		return true;
	}

	/***
	 * Proceso recepcion en casa central.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 27/09/2013
	 * @see
	 * @param receipt
	 * @return
	 */
	private String processCentralPoint(MTTReceipt receipt, boolean reprocesandoCuentas, MTTConfig config){

		String mensaje = null;
			
		try {
			// Valido cuenta ya recepcionada y por lo tanto no debe procesarse nada
			mensaje = validateReceivedCard();
			if (mensaje != null) return mensaje;
			
			int windowNo = 0;
			if (this.getTableModel() != null){
				windowNo = this.getTableModel().getTabModel().getWindowNo();
			}
			
			MDeliveryPoint dpActual = (MDeliveryPoint)receipt.getUY_DeliveryPoint();
			ItalcredSystem it = new ItalcredSystem();
			
			// Si no tengo tracking para esta cuenta, debo retener esta cuenta. 
			if (card == null){
				card = it.getCustomerData(getCtx(), this.getScanText(), get_TrxName());
				if (card == null){
					boxDestinyMode = NO_BOX;
					return "No existe Cuenta definida en Financial con este Codigo.";
				}
				
				card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenida").get_ID());
				card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				card.setIsDeliverable(false);
				card.setIsRetained(true);
				card.setNotValidText("Cuenta NO informada por FDU.");
				boxDestinyMode = FOR_RETENTION;
				trackDescription = "Retenida en : " + dpActual.getName();
				trackObservations = card.getNotValidText();

				// Genero incidencia para esta cuenta
				card.generateIncidencia();
			}

			MTTCardStatus cardStatus = (MTTCardStatus)card.getUY_TT_CardStatus();
			
			// Si esta cuenta esta en estado pendiente de recepcion (es el primer cardcarrier escaneado).
			// Proceso segnu estado actual de cuenta
			if (cardStatus.getValue().equalsIgnoreCase("pendrec")){
				
				String postalAux = card.getPostal();
				
				// Debo actualizar modo de distribucion y domicilio ya que pudo haber cambiado desde que entro la notificacion de FDU hasta que se recepciona
				it.setCardDeliveryMode(getCtx(), card);

				// Actualizo impresion de documentos
				it.setPrintDocs(getCtx(), card);
				
				if (card.getCardDestination().equalsIgnoreCase("DOMICILIO")){
					// Si cambio el codigo postal, aviso de reimpresion de cardcarrier
					if (postalAux != null){
						if (!postalAux.equalsIgnoreCase(card.getPostal())){
							card.setNeedPrint(true);
						}
						
					}

					// OJO ACA PORQUE ESTO TIENE QUE CAMBIAR YA QUE AHORA ME PASARON LAS CUENTAS Y SUS CORREOS
					// Actualizo correo destino ya que pudo haber cambiado o no tener asignado nada anteriormente
					if (card.getUY_DeliveryPoint_ID() <= 0){
						if (card.getPostal() != null){
							MDeliveyPointPostal ppostal = MDeliveyPointPostal.forLocalidadPostal(getCtx(), card.getPostal(), null);
							if (ppostal != null){
								MDeliveryPoint dp = (MDeliveryPoint)ppostal.getUY_DeliveryPoint();
								card.setUY_DeliveryPoint_ID(dp.get_ID());
							}
						}
					}

					// Si aun no tengo correo destino, retengo
					if (card.getUY_DeliveryPoint_ID() <= 0){
						card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenida").get_ID());
						card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
						card.setIsDeliverable(false);
						card.setIsRetained(true);
						card.setNotValidText("Cuenta NO Tiene Correo Destino definido.");
						boxDestinyMode = FOR_RETENTION;
						trackDescription = "Retenida en : " + dpActual.getName();
						trackObservations = card.getNotValidText();
					}
				}
				
				boolean retener = false;
				if (!card.isDeliverable()){
					retener = true;
					card.setRetainedStatus(X_UY_TT_Card.RETAINEDSTATUS_Inconsistente);
				}
				else if (!card.validateDelivery()){
					retener = true;
					card.setRetainedStatus(X_UY_TT_Card.RETAINEDSTATUS_Destruccion);
				}
				
				// Calculo cantidad de cardcarriers esperados segun cantidad de plasticos notificados por fdu
				List<MTTCardPlastic> tarjetas = card.getSentPlastics();
				if (tarjetas != null){
					int countPlastic = tarjetas.size();
					card.setQtyPlastic(countPlastic);
					if (countPlastic <= 0) card.setQtyCarrier(0);
					else if (countPlastic <= 2) card.setQtyCarrier(1); 
					else if (countPlastic <= 4) card.setQtyCarrier(2);
					else if (countPlastic == 5) card.setQtyCarrier(3);
					card.setQtyCarrierCounted(0);
				}
				
				// Si cuenta solo tiene un cardcarrier
				if (card.getQtyCarrier() <= 1){
					
					// Marco tarjetas como seleccionadas por defecto
					for (MTTCardPlastic tarjeta: tarjetas){
						tarjeta.setIsSelected(true);
						tarjeta.setUY_TT_Receipt_ID(receipt.get_ID());
						tarjeta.saveEx();
					}

					// Si debo retener cuenta, marco cuenta retenida y fijo destino cada de retenidas
					if (retener){
						card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenida").get_ID());
						card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
						card.setIsDeliverable(false);
						card.setIsRetained(true);
						boxDestinyMode = FOR_RETENTION;
						trackDescription = "Retenida en : " + dpActual.getName();
						trackObservations = ((card.getNotValidText() != null) ? card.getNotValidText() : "");
					}else{
						//OpenUP Sylvie  Mejora Recepcionar cuentas desde fdu y enviar a caja de impresion de vale si corresponde
						//Si la cuenta va a domicilio y no tiene vale firmado Issue #3273
						boolean imprimirVale = false;
						
						/*
						// No se imprime mas el vale
						if(!card.isValeSigned()){
							imprimirVale = true;									
						}
						*/
						
						// Verifico si esta cuenta tiene una impresion de cheque pendiente y no se esta reproceando la cuenta, tiene que ir a la caja de comunicacion a usuarios
						if ((card.getUY_TT_ChequeraLine_ID() > 0) && (!reprocesandoCuentas)){
							// Destino : Caja de comunicacion a usuarios.
							card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenidacom").get_ID());	
							card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
							boxDestinyMode = FOR_COMUNICATION;
							trackDescription = "Retenida en : " + dpActual.getName(); 
							trackObservations =  "Requiere Comunicacion a Usuario."; 

						}
						
						/*
						// No se imprime mas el vale
						 
						//OpenUp Sylvie B 11-12-2014 Issue# 3273 Mejora (En este lugar se va a codificar para lo de las cajas)
						// Verifico si esta cuenta tiene una impresion de VALE prendiente y no se esta reprocesando la cuenta, tiene que ir a la caja de impresion de vale
						// UY_TT_PrintValeLine_ID
						else if((card.getUY_TT_PrintValeLine_ID() > 0)  && (!reprocesandoCuentas) && !(cardStatus.getValue().equals("retenidaimpvale"))){
							//Sylvie 22/12/2014 Se crea un nuevo estado que es reteenida por impresion de vale
							// Destino  : Caja de impresion de vale ()
							card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenidaimpvale").get_ID());
							card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
							boxDestinyMode = FOR_IMPRESION_VALE;
							trackDescription = "Retenida en : " + dpActual.getName();
							trackObservations = "Requiere Impresion de Vale.";
							
						}
						*/
						
						else{							
							//OpenUp Sylvie Bouissa 09-12-2014 Issue# 3273 MEJORA 
							//Si la cuenta tiene destino domicilio se debe imprimir carátula de solicitud	
							MTTReceipt auxReceipt = (MTTReceipt) this.getUY_TT_Receipt();
							if(card.getCardDestination().equalsIgnoreCase("DOMICILIO")){
								
								if(!reprocesandoCuentas){
									if(!imprimirVale){
										    // si va domicilio y no se tiene que imprimir vale se imprime solo caratula de solicitud y se envia a la caja que corresponde
											
											// Cambios: ahora no se imprime caratula de solicitud
											//card.printCaratulaSolicitud(windowNo, true);
										
											if (card.isNeedPrint()){
												card.printCardCarrier(windowNo,false,true);
											}
																						
											// Todo bien, recepciono y sugiero caja
											// Destino : Esta cuenta tiene que ir a la caja destino
											card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
											card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
											card.setIsDeliverable(true);
											card.setIsRetained(false);
											boxDestinyMode = FOR_DESTINY;
											trackDescription = "Recepcionada en : " + dpActual.getName();
										
									}else{
										if(auxReceipt.getBoxRetImpVale()!=null && imprimirVale){
											// Si no estoy reprocesando (el bolsin viene de fdu),existe caja para impresion de vale envio a dicha caja no imprimo ningun doc

											// Destino  : Caja de impresion de vale ()
											card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenidaimpvale").get_ID());
											card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
											boxDestinyMode = FOR_IMPRESION_VALE;
											trackDescription = "Retenida en : " + dpActual.getName();
											trackObservations = "Requiere Impresion de Vale.";
											
										}else if(auxReceipt.getBoxRetImpVale()==null && imprimirVale){
											// Si no estoy reporcesando pero no tengo caja para impresion de vale, iesta cuenta debe reimprimir cardcarrier y se debe imprimir vale xq no hay caja de vale							
											card.printCaratulaSolicitud(windowNo, false);
											
											card.printValeToSign(windowNo,true); //ya esta creado 1000015
											
											//OpenUp #Issue 4651 SBouissa
											//debe ir al final de las tres impresiones para que no se tranque el proceso									
											if (card.isNeedPrint()){
												card.printCardCarrier(windowNo,true,true);
											}
											
											// Todo bien, recepciono y sugiero caja
											// Destino : Esta cuenta tiene que ir a la caja destino
											card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
											card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
											card.setIsDeliverable(true);
											card.setIsRetained(false);
											boxDestinyMode = FOR_DESTINY;
											trackDescription = "Recepcionada en : " + dpActual.getName();
										}
									}
									
								//Si la cuenta va a domicilio pero estoy reprocesando se supone que ya se mando a la caja correspondiente o se imprimio 1 a 1
								}else if(reprocesandoCuentas){
									if (card.isNeedPrint()){
										card.printCardCarrier(windowNo,false,false);
									}
									// Todo bien, recepciono y sugiero caja
									// Destino : Esta cuenta tiene que ir a la caja destino
									card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
									card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
									card.setIsDeliverable(true);
									card.setIsRetained(false);
									boxDestinyMode = FOR_DESTINY;
									trackDescription = "Recepcionada en : " + dpActual.getName();
								}
								
							}else{// si la cuenta no va a domicilio 
								// Si corresponde se imprime card carrier
								if (card.isNeedPrint()){
									card.printCardCarrier(windowNo,false,false);
								}
								// Todo bien, recepciono y sugiero caja
								// Destino : Esta cuenta tiene que ir a la caja destino
								card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
								card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
								card.setIsDeliverable(true);
								card.setIsRetained(false);
								boxDestinyMode = FOR_DESTINY;
								trackDescription = "Recepcionada en : " + dpActual.getName();
							}
					
						}
						
					}
					
				}				
				// Si tengo mas de un cardcarrier
				else if (card.getQtyCarrier() > 1){
					
					if (!retener){
						// Si esta cuenta debe reimprimir cardcarrier
						if (card.isNeedPrint()){
							card.printCardCarrier(windowNo,false,false);
						}
					}
										
					// Retenida por CardCarrier, va a caja de unificacion.
					card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenidacc").get_ID());
					card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
					boxDestinyMode = FOR_UNIFICATION;
					card.setQtyCarrierCounted(card.getQtyCarrierCounted() + 1);
					trackDescription = "Retenida en : " + dpActual.getName(); 
					trackObservations =" Requiere Unificacion de Card-Carriers. (Lectura " + card.getQtyCarrier() + " de " + card.getQtyCarrier() + ")"; 									
				}
				
				// Marco para cargar plasticos recepcionados
				this.loadCardPlastics = true;
				
				// Impacto en legajo de financial.
				MRReclamo reclamo = (MRReclamo)card.getUY_R_Reclamo();
				if (reclamo != null){
					reclamo.setLegajoFinancial(true, false, null);
					card.setIsLegajoSaved(true);
				}
				
				card.setDateReceived(new Timestamp(System.currentTimeMillis()));
				
			}  //Fin Pendiente de Recepcion.
			
			// Cuenta en estado de retenida por unificacion de cardcarrier 
			else if (cardStatus.getValue().equalsIgnoreCase("retenidacc")){
				// Cuenta ya recepcionada anteriormente y tiene mas de un card carrier.
				// Verifico que queden plasticos sin seleccionar para esta cuenta
				List<MTTCardPlastic> tarjSinSeleccionar = card.getSentPlasticsNotSelected();
				if ( (tarjSinSeleccionar != null) && (tarjSinSeleccionar.size() > 0)){

					// Si esta cuenta ya llego al tope de cardcarriers escaneados
					if (card.getQtyCarrierCounted() > 0){
						if (card.getQtyCarrierCounted() >= card.getQtyCarrier()){
							// Aviso que esta cuenta ya fue totalmente recepcionada
							boxDestinyMode = NO_BOX;
							return "Todos los Card-Carriers de esta Cuenta ya fueron Recepcionados.";
						}
					}
					
					// Si esta cuenta debe reimprimir cardcarrier
					if (card.isNeedPrint()){
						card.printCardCarrier(windowNo,false,false);
					}
					
					// Destino : Esta cuenta tiene que ir a la caja de retenidas por ubicacion
					boxDestinyMode = FOR_UNIFICATION;
					card.setQtyCarrierCounted(card.getQtyCarrierCounted() + 1);
					trackDescription = "Retenida en : " + dpActual.getName(); 
					trackObservations =" Requiere Unificacion de Card-Carriers. (Lectura " + card.getQtyCarrier() + " de " + card.getQtyCarrier() + ")"; 									
				}
				else{
					// Todos los plasticos de esta cuenta estan seleccionados.
					// Aviso que esta cuenta ya esta totalmente recepcionada.
					boxDestinyMode = NO_BOX;
					return "Todos los Card-Carriers de esta Cuenta ya fueron Recepcionados.";
				}

			} //Fin Retenida por CardCarrier
			
			// Cuenta enviada desde sucursal a casa central o desde correo
			else if ((cardStatus.getValue().equalsIgnoreCase("enviada")) 
					|| (cardStatus.getValue().equalsIgnoreCase("devuelta"))
					|| (cardStatus.getValue().equalsIgnoreCase("distribucion"))
					|| (cardStatus.getValue().equalsIgnoreCase("devacli"))){

				// Si esta cuenta fue solicitada por un nuevo destino
				if (card.isRequested()&& card.getUY_DeliveryPoint_ID_To()!=0){
					
					// Si esta cuenta debe reimprimir cardcarrier
					if (card.isNeedPrint()){
						card.printCardCarrier(windowNo,false,false);
					}
					
					// Seteo nuevo destino
					card.setUY_DeliveryPoint_ID(card.getUY_DeliveryPoint_ID_To());
					card.setUY_DeliveryPoint_ID_To(card.getUY_DeliveryPoint_ID_To()); 
					card.setUY_DeliveryPoint_ID_Req(card.getUY_DeliveryPoint_ID_To());
					
					/*
					// Desmarco solicitada
					card.setIsRequested(false);
					DB.executeUpdateEx(" update uy_tt_card set uy_deliverypoint_id_to = null, uy_deliverypoint_id_req = null where uy_tt_card_id = " + card.get_ID() , get_TrxName());
					*/
					
					// Todo bien, recepciono y sugiero caja
					// Destino : Esta cuenta tiene que ir a la caja destino
					card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
					card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
					card.setIsDeliverable(true);
					card.setIsRetained(false);
					boxDestinyMode = FOR_DESTINY;
					trackDescription = "Recepcionada en : " + dpActual.getName();						

				}
				else{
					// Marco cuenta como retenida y destino caja de retenidas
					card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenida").get_ID());
					card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
					card.setIsDeliverable(false);
					card.setIsRetained(true);
					card.setNotValidText("Cuenta sin Nuevo Destino.");
					card.setRetainedStatus(X_UY_TT_Card.RETAINEDSTATUS_Inconsistente);				
					boxDestinyMode = FOR_RETENTION;
					trackDescription = "Retenida en : " + dpActual.getName();
					trackObservations = ((card.getNotValidText() != null) ? card.getNotValidText() : "");
				}

			} //Fin cuenta en transito o en coordinacion
//			// Sylvie Mejora 3273
//			if(cardStatus.getValue().equalsIgnoreCase("retenidaimpvale")){
//				
//			}
			// Fin Sylvie
			// Leo una cuenta en cualquier otro estado
			else {
				
				if (!cardStatus.getValue().equalsIgnoreCase("retenida")){
					MTTCardStatus otherStatus = (MTTCardStatus)card.getUY_TT_CardStatus();
					// Marco cuenta como retenida y destino caja de retenidas
					card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "retenida").get_ID());
					card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
					card.setNotValidText("Retenida por Estado no Apto para Recepcion : " + otherStatus.getName());
					card.setRetainedStatus(X_UY_TT_Card.RETAINEDSTATUS_Inconsistente);					
					card.setIsDeliverable(false);
					card.setIsRetained(true);
					boxDestinyMode = FOR_RETENTION;
					trackDescription = "Retenida en : " + dpActual.getName();
					trackObservations = ((card.getNotValidText() != null) ? card.getNotValidText() : "");
				}
				
			}
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}

		return mensaje;
	}


	/***
	 * Valida si esta cuenta al estar recibida puede volver a escanearse.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 02/10/2013
	 * @see
	 * @return
	 */
	private String validateReceivedCard() {

		String mensaje = null;
		
		try {
			
			if (card == null) return null;
			
			if (!card.isReceived()) return null;
			
			// Segun cantidad de card-carriers
			if (card.getQtyCarrier() <= 1){
				
				MTTCardStatus status = (MTTCardStatus)card.getUY_TT_CardStatus();

				// Con un cardcarrier y ya recibida entonces no puedo procesarla nuevamente.
				// Aviso estado y posicion actual de esta cuenta.
				mensaje = " Esta Cuenta ya fue Recepcionada.\n" +
						  " Se encuentra en Estado : " + status.getName() + "\n";
				
				MTTBox box = (MTTBox)card.getUY_TT_Box();
				if (box != null){
					if (box.isRetained()){
						if (!box.isUnificaCardCarrier()){
							mensaje += " Caja de RETENIDAS : " + box.getValue();		
						}
						else{
							mensaje += " Caja para UNIFICACION DE CARD-CARRIERS : " + box.getValue();
						}
					}
					
					if (box.getUY_DeliveryPoint_ID_To() > 0){
						MDeliveryPoint dp = new MDeliveryPoint(getCtx(), box.getUY_DeliveryPoint_ID_To(), null);
						mensaje += " Caja con DESTINO " + dp.getName() + " : " + box.getValue();
					}
					else{
						mensaje += " Caja : " + box.getValue();
					}
				}
				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return mensaje;
	}

	/***
	 * Proceso de recepcion de cuenta en sucursales.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 27/09/2013
	 * @see
	 * @param receipt
	 * @return
	 */
	private String processSucursal(MTTReceipt receipt, MTTConfig config){

		String mensaje = null;
			
		try {

			// Valido cuenta ya recepcionada y por lo tanto no debe procesarse nada
			if (card.isReceived()){
				mensaje = " Esta Cuenta ya fue Recepcionada.";
				
				MTTBox box = (MTTBox)card.getUY_TT_Box();
				if (box != null){
					mensaje += "\n";
					mensaje += " Se encuentra en la Caja : " + box.getValue() + " - Posición : " + card.getLocatorValue();
				}
				
				return mensaje;
			}
			
			MDeliveryPoint dpActual = (MDeliveryPoint)receipt.getUY_DeliveryPoint();

			// Obtengo punto de distribucion de Casa central
			MDeliveryPoint dpCentral = MDeliveryPoint.forValue(getCtx(), "casacentral", null);

			
			// Si no tengo tracking para esta cuenta, debo retener esta cuenta. 
			if (card == null){
				return "No existe Cuenta en Tracking con este Codigo.";
			}

			MTTSeal seal = (MTTSeal)receipt.getUY_TT_Seal();
			
			// Si la cuenta tiene asociado otro precinto
			if (card.getUY_TT_Seal_ID() != seal.getUY_TT_Seal_ID()){
				// Si la cuenta tiene como destino esta sucursal aviso y salgo
				if (card.getUY_DeliveryPoint_ID() == receipt.getUY_DeliveryPoint_ID()){
					throw new AdempiereException ("Esta Cuenta NO esta registrada en este Bolsin");
				}
				else{
					// Tiene que volver a casa central
					mensaje = " Esta Cuenta NO esta registrada en este Bolsin.\n" +
							  " La misma se debe guardar en Caja con destino Casa Central.";
					
				}
			}

			// Valido que cuenta tenga como destino esta sucursal
			if (mensaje == null){
				// La cuenta no tiene como destino este punto de distribucion
				if (card.getUY_DeliveryPoint_ID() != receipt.getUY_DeliveryPoint_ID()){
					mensaje = " Esta Cuenta NO tiene como Destino esta Sucursal.\n" +
							  " La misma se debe guardar en Caja con destino Casa Central.";
				}
			}
			
			// Si la cuenta viene requerida por casa central
			if (mensaje == null){
				if (card.isRequested()){
					if (card.getUY_DeliveryPoint_ID_Req() == dpCentral.get_ID()){
						// Tiene que volver a casa central
						mensaje = " Esta Cuenta esta Requerida por Casa Central.\n" +
								  " La misma se debe guardar en Caja con destino Casa Central.";
					}
					else{
						if (card.getUY_DeliveryPoint_ID_Req() != dpActual.get_ID()){
							mensaje = " Esta Cuenta esta Requerida por Otra Sucursal.\n" +
									  " La misma se debe guardar en Caja con destino Casa Central.";
						}
					}
				}
			}
			
			// Si alguna validacion fallo retengo cuenta
			if ((mensaje != null) && (!config.isForzed())){
				card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
				card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				card.setNotValidText(mensaje);
				card.setIsDeliverable(false);
				card.setIsRetained(true);
				boxDestinyMode = FOR_RETURN;
				trackDescription = "Recepcionada y RETENIDA para Devolucion a Casa Central en : " + dpActual.getName(); 									
			}
			else{
				// Todo bien, guardo en caja de distribucion
				boxDestinyMode = FOR_DISTRIBUTION;
				trackDescription = "Recepcionada en : " + dpActual.getName();							
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}

		return mensaje;
	}

	/***
	 * Despliega mensaje de caja utilizada con opcion a mostrar o no ubicacion
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/09/2013
	 * @see
	 */
	private void showBoxMessage(){

		String mensaje = "Guardar en Caja " + this.boxDestino.getValue();
		
		if (this.boxDestino.isDestiny()){
			MDeliveryPoint dpDestino = new MDeliveryPoint(getCtx(), this.boxDestino.getUY_DeliveryPoint_ID_To(), null);
			mensaje += " con Destino : " + dpDestino.getName();
		}
		else{
			if (this.boxDestino.isRetained()){
				mensaje += " de Cuentas Retenidas ";
				if (this.boxDestino.isUnificaCardCarrier()){
					mensaje += " por Unificacion de Card-Carriers ";	
				}
				else if (this.boxDestino.isComunicaUsuario()){
					mensaje += " por Comunicacion a Usuario ";	
				}
			}
		}
		
		if (this.useLocatorValue){
			mensaje += " - Posicion = " + this.boxDestino.getQtyCount();
		}
		
		if (descriptionDialog != null){
			mensaje += "\n" + descriptionDialog;
		}

		boolean showOK = true;
		try{
			ADialog.info (windowNo, null, mensaje);	
		}
		catch (Exception e){
			showOK = false;
		}
		
		if (!showOK){
			try {
				FDialog.info(windowNo, null, mensaje);
			} 
			catch (Exception e) {
				showOK = false;
			}
		}

	}

	
	
}
