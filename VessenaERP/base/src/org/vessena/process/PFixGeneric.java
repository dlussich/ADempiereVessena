/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 23/07/2013
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MElementValue;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MDeliveryPoint;
import org.openup.model.MRReclamo;
import org.openup.model.MTTCard;
import org.openup.model.MTTCardStatus;
import org.openup.model.MTTCardTracking;

/**
 * org.openup.process - PFixGeneric
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 23/07/2013
 * @see
 */
public class PFixGeneric extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PFixGeneric() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 23/07/2013
	 * @see
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 23/07/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		/*
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			sql = " select * from c_elementvalue where issummary='N' "; 
			
			pstmt = DB.prepareStatement(sql.toString(), null);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MElementValue ev = new MElementValue(getCtx(), rs.getInt("c_elementvalue_id"), null);
				ev.setAccountInfo();
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return "OK";
		
		*/
		// Estados a considerar
		MTTCardStatus stRecepcionada = MTTCardStatus.forValue(getCtx(), get_TrxName(), "recepcionada");
		MTTCardStatus stEntregada = MTTCardStatus.forValue(getCtx(), null, "entregada");
		

		MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), "157346", get_TrxName());
		MTTCard card = MTTCard.forIncidencia(getCtx(), incidencia.get_ID(), get_TrxName());
		
		card.setUY_TT_CardStatus_ID(stRecepcionada.get_ID()); //cambio estado de la tarjeta
		card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
		card.setUY_DeliveryPoint_ID_Actual(1010060); //cambio el punto de distribucion actual de la tarjeta
		//card.setSubAgencia(line.getSubAgencyNo());
		card.setDateAssign(new Timestamp(System.currentTimeMillis()));
		card.setDiasActual(0);
		card.saveEx();
		
		//seteo a null el ID de precinto
		DB.executeUpdateEx("update uy_tt_card set uy_tt_seal_id = null where uy_tt_card_id = " + card.get_ID(), get_TrxName()); 
		
		// Quito cuenta de precinto
		DB.executeUpdateEx(" delete from uy_tt_sealcard where uy_tt_card_id =" + card.get_ID(), get_TrxName());
		
		// Obtengo punto de distribucion de esta subagencia
		MDeliveryPoint delPointSubAge = MDeliveryPoint.forSubAgencyNo(getCtx(), card.getSubAgencia().trim(), null);
		String datosSubAge = "";
		if ((delPointSubAge != null) && (delPointSubAge.get_ID() > 0)){
			if (delPointSubAge.getAddress1()!=null){
				datosSubAge +=delPointSubAge.getAddress1();
			}
			if (delPointSubAge.getTelephone() != null){
				datosSubAge += " - " + delPointSubAge.getTelephone();
			}
			if (delPointSubAge.getDeliveryTime() != null){
				datosSubAge += " - L.a V.: " + delPointSubAge.getDeliveryTime(); 
			}
			if (delPointSubAge.getDeliveryTime2() != null){
				datosSubAge += " - Sab.: " + delPointSubAge.getDeliveryTime2(); 
			}
			if (delPointSubAge.getDeliveryTime3() != null){
				datosSubAge += " - Dom.: " + delPointSubAge.getDeliveryTime3(); 
			}
		}
		
		// Tracking cuenta 
		MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
		cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
		cardTrack.setAD_User_ID(1003002);
		cardTrack.setDescription("Recepcionada en SubAgencia: " + card.getSubAgencia());
		cardTrack.setobservaciones(datosSubAge);
		cardTrack.setUY_TT_Card_ID(card.get_ID());
		cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
		cardTrack.setUY_DeliveryPoint_ID_Actual(card.getUY_DeliveryPoint_ID_Actual());
		cardTrack.setSubAgencia(card.getSubAgencia());
		cardTrack.saveEx();
		
		return "ok";
	}

}
