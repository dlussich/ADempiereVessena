/**
 * 
 */
package org.openup.process;

import java.util.List;

import org.compiere.process.SvrProcess;
import org.openup.model.MRReclamo;
import org.openup.model.MTTCard;

/**
 * @author gbrust
 *
 */
public class PTTSaveLegajo extends SvrProcess {

	/**
	 * 
	 */
	public PTTSaveLegajo() {
		
	}

	
	@Override
	protected void prepare() {

	}

	/***
	 * OpenUp. Guillermo Brust. 21/10/2013. ISSUE #1434
	 * Método que realiza impacto masivo de en legajo de Financial, para todas aquellas cuentas que no se haya impactado el legajo por Tracking de Tarjetas y que 
	 * el estado de la misma sea distinto de pendiente de recepción. 
	 * 
	 * */
	@Override
	protected String doIt() throws Exception {
		
		List<MTTCard> cards = MTTCard.getCardsForSaveLegajo(this.getCtx(), null);
		
		for (MTTCard card : cards) {
			
			// Impacto en legajo de financial.
			MRReclamo reclamo = (MRReclamo) card.getUY_R_Reclamo();
			if (reclamo != null){
				reclamo.setLegajoFinancial(true, true, null);
				card.setIsLegajoSaved(true);
				card.saveEx();
			}			
		}		
		
		return "Ok";		
	}

}
