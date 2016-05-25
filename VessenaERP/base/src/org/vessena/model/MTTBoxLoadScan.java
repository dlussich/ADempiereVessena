/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 13/11/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

/**
 * org.openup.model - MTTBoxLoadScan
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo para scaneo en recarga de caja
 * @author Gabriel Vila - 13/11/2013
 * @see
 */
public class MTTBoxLoadScan extends X_UY_TT_BoxLoadScan {

	private static final long serialVersionUID = 1575846294841565988L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_BoxLoadScan_ID
	 * @param trxName
	 */
	public MTTBoxLoadScan(Properties ctx, int UY_TT_BoxLoadScan_ID,
			String trxName) {
		super(ctx, UY_TT_BoxLoadScan_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTBoxLoadScan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		try {
			
			MTTBoxLoad boxLoad = (MTTBoxLoad)this.getUY_TT_BoxLoad();
			MTTBox box =(MTTBox)boxLoad.getUY_TT_Box();
			MDeliveryPoint dpActual = (MDeliveryPoint)boxLoad.getUY_DeliveryPoint();
			
			if (this.getScanText() == null) return true;
			if (this.getScanText().trim().equalsIgnoreCase("")) return true;
			
			MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), this.getScanText().trim(), null);
			if (incidencia == null){
				throw new AdempiereException("No existe Incidencia con ese Número.");
			}
			
			MTTCard card = MTTCard.forIncidenciaAndDeliveryPoint(getCtx(), get_TrxName(), incidencia.get_ID(), dpActual.get_ID());
			
			if (card == null){
				throw new AdempiereException("No existe Cuenta con ese codigo y en proceso de Tracking.");
			}
			/*Ini Issue 3197 - Sylvie Bouissa 31/10/2014
			 * Se tiene que consultar si la tarjeta estaba asociada a otra caja.. 
			 * des ser asi se debe eliminar la linea asociada a la caja anterior que asocia la tarjeta con la caja anterior
			 *	Se Habla con Gabriel y no corresponde ya que la funcion no es pasar de una caja a la otra !!!
			*/
//			int idBoxOld = card.getUY_TT_Box_ID();
//			MTTBox boxOld = new MTTBox(getCtx(), idBoxOld, get_TrxName());
//			MTTBoxCard bboxCardAnt = null;
//			if(boxOld!=null){ 
////				bboxCardAnt = MTTBoxCard.forBoxIDAndCardID(getCtx(), get_TrxName(), boxOld.get_ID(), card.get_ID());
////				if(bboxCardAnt != null && (card.get_ID()==bboxCardAnt.getUY_TT_Card_ID())){
////					boxOld.removeCard(bboxCardAnt); // Metodo creado por leo... sin usar hasta el momento.
////				}
//			}else{
//				//Es necesario tirar excepcion porque si o si debe estar en otra caja??
//			}
			//FIN Issue 3197 - Sylvie Bouissa 31/10/2014
			

			// Dejo cuenta con estado correcto, sin retener y en puntos de distribucion correctos
			card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
			card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
			card.setIsRetained(false);
			card.setIsDeliverable(true);
			//card.setIsRequested(false);
			//A pedido de Vanina Grunberg no sete el punto de destino.
			//card.setUY_DeliveryPoint_ID(dpActual.get_ID());
			card.setUY_DeliveryPoint_ID_Actual(dpActual.get_ID());
			
			int locatorValue = box.updateQtyCount(1, true, 2000, 
									X_UY_TT_Box.BOXSTATUS_RecepcionCuentas, X_UY_TT_Box.BOXSTATUS_Cerrado, false, false,
									0, false, null);
			
			// Asocio caja con cuenta
			card.setUY_TT_Box_ID(box.get_ID());
			card.setLocatorValue(locatorValue);
			card.saveEx();
			
			MTTBoxCard bCard = new MTTBoxCard(getCtx(), 0, get_TrxName());
			bCard.setUY_TT_Box_ID(box.get_ID());
			bCard.setUY_TT_Card_ID(card.get_ID());
			bCard.setLocatorValue(card.getLocatorValue());
			bCard.setName(card.getName());
			bCard.setDateTrx(new Timestamp(System.currentTimeMillis()));
			bCard.setAD_User_ID(boxLoad.getAD_User_ID());
			bCard.setUY_R_Reclamo_ID(card.getUY_R_Reclamo_ID());
			bCard.saveEx();
			
			String action = " update uy_tt_card set uy_deliverypoint_id_req = null, uy_deliverypoint_id_to = null, " +
							" notvalidtext = null where uy_tt_card_id =" + card.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
			/*//Borro cuenta de caja anterior si tenia
			MTTBoxCard bCardOld = MTTBoxCard.forBoxIDAndCardID(getCtx(), get_TrxName(), card.getUY_TT_Box_ID(), card.get_ID());
			MTTBox oldbox = new MTTBox(getCtx(),card.getUY_TT_Box_ID() ,get_TrxName());
			if(oldbox!=null&&!oldbox.equals(box)){
				oldbox.removeCard(bCardOld);
			}*/
			
			// Tracking en cuenta
			MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
			cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
			cardTrack.setAD_User_ID(boxLoad.getAD_User_ID());
			cardTrack.setDescription("Recuento de Caja");
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
			MTTBoxLoadCard rc = new MTTBoxLoadCard(getCtx(), 0, get_TrxName());
			rc.setIsValid(card.isDeliverable());
			rc.setIsRetained(!card.isDeliverable());
			rc.setInvalidText(card.getNotValidText());
			rc.setUY_TT_BoxLoad_ID(this.getUY_TT_BoxLoad_ID());
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
			rc.saveEx();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return true;
	}



	
}
