/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.Trx;

/**
 * @author gbrust
 * 
 */
public class MTTRetentionLine extends X_UY_TT_RetentionLine {

	@Override
	protected boolean beforeSave(boolean newRecord) {

		MTTBox boxDestino = null;
		MTTBox cajaActualCuenta = null;

		try {

			MTTRetention retention = (MTTRetention) this.getUY_TT_Retention();

			// int windowNo = hdr.getTableModel().getTabModel().getWindowNo();
			int windowNo = 0;

			// TOPE de CAJAS
			int topeBox = MTTConfig.forValue(this.getCtx(), this.get_TrxName(),
					"tarjeta").getTopBox();

			// ESTADO DE TARJETA
			int statusRecepcionadaID = MTTCardStatus.forValue(this.getCtx(),
					this.get_TrxName(), "recepcionada").get_ID();

			MTTCard card = new MTTCard(getCtx(), this.getUY_TT_Card_ID(),
					this.get_TrxName());

			// Primero obtengo el destino asociado a esta cuenta.
			MDeliveryPoint destinoAsociado = (MDeliveryPoint) card.getUY_DeliveryPoint();
			if (card.getSubAgencia() != null){
				MDeliveryPoint delSubAge = MDeliveryPoint.forSubAgencyNo(getCtx(), card.getSubAgencia(), null);
				if ((delSubAge != null) && (delSubAge.get_ID() > 0)){
					destinoAsociado = delSubAge;
				}
			}

			// Punto Actual
			MDeliveryPoint dpActual = (MDeliveryPoint) retention
					.getUY_DeliveryPoint();

			cajaActualCuenta = new MTTBox(this.getCtx(),
					card.getUY_TT_Box_ID(), this.get_TrxName());
			String action = ((MTTRetention) this.getUY_TT_Retention())
					.getRetainedStatus();

			MTTBox boxRetention = MTTRetentionBox
					.getBoxForRetentionIDAndAction(this.getCtx(),
							this.get_TrxName(), this.getUY_TT_Retention_ID(),
							action);
			if (boxRetention.get_ID() != cajaActualCuenta.get_ID()){ //OpenUp Sylvie Bouissa 26-11-2014 Issue# 3306 
				MTTRetentionScan.setMensajeCte("La cuenta leida no pertenece a una caja definida para este proceso, la misma pertenece a la caja "
						+ cajaActualCuenta.getValue());
				
				Trx trxAux = Trx.get(get_TrxName(), false);
				if (trxAux != null){
					trxAux.rollback();
				}
				if (cajaActualCuenta != null) cajaActualCuenta.unlock(null);
				
				return false;
//				throw new AdempiereException(
//						"La cuenta leida no pertenece a una caja definida para este proceso, la misma pertenece a la caja "
//								+ cajaActualCuenta.getValue());
			}
				

			// Vuelvo a hacer el proceso de validacion de tarjeta
			if (!card.validateDelivery()) {
				card.setIsDeliverable(false);
				card.setIsRetained(true);
			}

			if (card.isDeliverable()) {

				// Si existe una caja con destino igual al asociado a la cuenta
				// leida obtengo la misma
				
				
				boxDestino = MTTRetentionBox
						.getBoxForDeliveryPointIDAndRetentionID(this.getCtx(),
								this.get_TrxName(), destinoAsociado.get_ID(),
								this.getUY_TT_Retention_ID());
				if (boxDestino != null) {

					if (!boxDestino.isComplete()) {

						// BLOQUEOS DE CAJAS
						if (!boxDestino.lock()) {//OpenUp Sylvie Bouissa 26-11-2014 Issue# 3306
							MTTRetentionScan.setMensajeCte(" La caja : "
									+ boxDestino.getValue()
									+ " esta bloqueada por otro usuario en este momento.\n"
									+ " Por favor aguarde unos instantes y reintente la operación.");
							
							Trx trxAux = Trx.get(get_TrxName(), false);
							if (trxAux != null){
								trxAux.rollback();
							}
							if (boxDestino != null) boxDestino.unlock(null);
							if (cajaActualCuenta != null) cajaActualCuenta.unlock(null);
							
							return false;
//							throw new AdempiereException(
//									" La caja : "
//											+ boxDestino.getValue()
//											+ " esta bloqueada por otro usuario en este momento.\n"
//											+ " Por favor aguarde unos instantes y reintente la operación.");
						}

						if (!cajaActualCuenta.lock()) {//OpenUp Sylvie Bouissa 26-11-2014 Issue# 3306
							MTTRetentionScan.setMensajeCte(" La caja : "
									+ cajaActualCuenta.getValue()
									+ " esta bloqueada por otro usuario en este momento.\n"
									+ " Por favor aguarde unos instantes y reintente la operación.");
							
							Trx trxAux = Trx.get(get_TrxName(), false);
							if (trxAux != null){
								trxAux.rollback();
							}
							if (boxDestino != null) boxDestino.unlock(null);
							if (cajaActualCuenta != null) cajaActualCuenta.unlock(null);
														
							return false;
//							throw new AdempiereException(
//									" La caja : "
//											+ cajaActualCuenta.getValue()
//											+ " esta bloqueada por otro usuario en este momento.\n"
//											+ " Por favor aguarde unos instantes y reintente la operación.");
						}
						// FIN BLOQUEOS DE CAJAS

						// Acá tengo que sumar 1 al contador de cuentas
						// guardadas en la caja de destino para esta cuenta
						int locatorValue = boxDestino.updateQtyCount(1, true,
								topeBox, MTTBox.BOXSTATUS_ProcesoRetenidas,
								MTTBox.BOXSTATUS_Cerrado, true, true, windowNo,
								false, null);

						// Le quito 1 a la caja contenedora de retencion
						cajaActualCuenta.updateQtyCount(1, false, topeBox,
								MTTBox.BOXSTATUS_ProcesoRetenidas,
								MTTBox.BOXSTATUS_Cerrado, false, false, 0,
								false, null);

						// Guardo cajas anterior y actual en la linea
						this.setUY_TT_Box_ID(card.getUY_TT_Box_ID());
						this.setUY_TT_Box_ID_1(boxDestino.get_ID());

						// Asocio caja con cuenta
						card.setUY_TT_Box_ID(boxDestino.get_ID());
						card.setLocatorValue(locatorValue);
						card.setUY_TT_CardStatus_ID(statusRecepcionadaID);
						card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
						card.setUY_DeliveryPoint_ID_Actual(dpActual.get_ID());
						card.saveEx();

						// Tracking en cuenta
						MTTCardTracking cardTrack = new MTTCardTracking(
								getCtx(), 0, get_TrxName());
						cardTrack.setDateTrx(new Timestamp(System
								.currentTimeMillis()));
						cardTrack.setAD_User_ID(retention.getAD_User_ID());
						cardTrack.setDescription("Recepcionada en : "
								+ dpActual.getName());
						cardTrack.setUY_TT_Card_ID(card.get_ID());
						cardTrack.setUY_TT_CardStatus_ID(card
								.getUY_TT_CardStatus_ID());
						cardTrack.setUY_DeliveryPoint_ID_Actual(card
								.getUY_DeliveryPoint_ID_Actual());
						if (card.getUY_TT_Box_ID() > 0) {
							cardTrack.setUY_TT_Box_ID(card.getUY_TT_Box_ID());
							if (card.getLocatorValue() > 0)
								cardTrack.setLocatorValue(card
										.getLocatorValue());
						}
						cardTrack.saveEx();

						// Desligo la cuenta de la uy_tt_boxcard
						MTTBoxCard bcard = MTTBoxCard.forBoxIDAndCardID(
								this.getCtx(), this.get_TrxName(),
								cajaActualCuenta.get_ID(), card.get_ID());
						if (bcard != null) {
							bcard.deleteEx(true);
						}

						MTTBoxCard bCard = new MTTBoxCard(getCtx(), 0,
								get_TrxName());
						bCard.setUY_TT_Box_ID(boxDestino.get_ID());
						bCard.setUY_TT_Card_ID(card.get_ID());
						bCard.setLocatorValue(card.getLocatorValue());
						bCard.setDateTrx(new Timestamp(System
								.currentTimeMillis()));
						bcard.setName(card.getName());
						bCard.setAD_User_ID(((MTTRetention) this
								.getUY_TT_Retention()).getAD_User_ID());
						bCard.setUY_R_Reclamo_ID(card.getUY_R_Reclamo_ID());
						bCard.saveEx();

						// Actualizo posiciones en caja Reteneciones
						cajaActualCuenta.refresh(false, true);

						// Desbloqueo caja destino y retencion
						cajaActualCuenta.unlock(get_TrxName());
						boxDestino.unlock(get_TrxName());

					} else {//OpenUp Sylvie Bouissa 26-11-2014 Issue# 3306
						MTTRetentionScan.setMensajeCte("La caja: "
								+ boxDestino.getValue()
								+ " llegó a su tope, por favor seleccione una nueva caja de retención para esta carga de bolsines");
						
						Trx trxAux = Trx.get(get_TrxName(), false);
						if (trxAux != null){
							trxAux.rollback();
						}
						if (boxDestino != null) boxDestino.unlock(null);
						if (cajaActualCuenta != null) cajaActualCuenta.unlock(null);
						
						return false;
//						throw new AdempiereException(
//								"La caja: "
//										+ boxDestino.getValue()
//										+ " llegó a su tope, por favor seleccione una nueva caja de retención para esta carga de bolsines");
					}
				} else {//OpenUp Sylvie Bouissa 26-11-2014 Issue# 3306
					MTTRetentionScan.setMensajeCte("No existe caja definida con destino: "
							+ destinoAsociado.getName());
					
					Trx trxAux = Trx.get(get_TrxName(), false);
					if (trxAux != null){
						trxAux.rollback();
					}
					if (cajaActualCuenta != null) cajaActualCuenta.unlock(null);					
					
					return false;
//					throw new AdempiereException(
//							"No existe caja definida con destino: "
//									+ destinoAsociado.getName());
				}

			} else { // Si no es entregable, lo que debo hacer es marcarla para
						// detruccion

				// Guardo cajas anterior y actual en la linea
				this.setUY_TT_Box_ID(cajaActualCuenta.get_ID());
				this.setUY_TT_Box_ID_1(cajaActualCuenta.get_ID());
				this.setIsValid(true);
				this.setNotDeliverableAction(card.getNotDeliverableAction());
				this.setInvalidText(card.getNotValidText());

				// Solo si es una caja por destruccion dejo por defecto que se
				// destruya sino no queda seleccionado por defecto
				if (action.equals("DESTRUIR"))
					this.setIsDestroy(true);
				else if (action.equals("INCONSISTENTE"))
					this.setIsDestroy(false);
			}
		} 
		catch (Exception e) {

			Trx trxAux = Trx.get(get_TrxName(), false);
			if (trxAux != null){
				trxAux.rollback();
			}
			if (boxDestino != null) boxDestino.unlock(null);
			if (cajaActualCuenta != null) cajaActualCuenta.unlock(null);

			throw new AdempiereException(e.getMessage());
		}

		return true;

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7725442703655850580L;

	/**
	 * @param ctx
	 * @param UY_TT_RetentionLine_ID
	 * @param trxName
	 */
	public MTTRetentionLine(Properties ctx, int UY_TT_RetentionLine_ID,
			String trxName) {
		super(ctx, UY_TT_RetentionLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTRetentionLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp. Guillermo Brust. 03/10/2013. ISSUE # Método que devuelve un
	 * modelo de de esta tabla que contenga el UY_TT_Card_ID pasado por
	 * parametro
	 * 
	 */
	public static MTTRetentionLine forCardIDAndRetentionID(Properties ctx,
			String trxName, int ttCardID, int RetentionID) {

		String whereClause = X_UY_TT_RetentionLine.COLUMNNAME_UY_TT_Card_ID
				+ " = " + ttCardID + " AND "
				+ X_UY_TT_RetentionLine.COLUMNNAME_UY_TT_Retention_ID + " = "
				+ RetentionID;

		MTTRetentionLine model = new Query(ctx,
				I_UY_TT_RetentionLine.Table_Name, whereClause, trxName).first();

		return model;
	}

}
