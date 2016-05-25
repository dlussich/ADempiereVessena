package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MTTHand;
import org.openup.util.ItalcredSystem;

public class PTTHandCheckSignature extends SvrProcess {
	
	MTTHand entrega = null;
	private boolean isValeFirmadoFinancial = true;
	
	@Override
	protected void prepare() {
		try{
			this.entrega = new MTTHand(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	@Override
	protected String doIt() throws Exception {
		
		//OpenUp. LeonardoBoccone. 11/06/2014. ISSUE #2275
				//No se permite aplicar la entrega de tarjeta si el solicitante no firmo el documento
				ItalcredSystem italcred = new ItalcredSystem();
				
				isValeFirmadoFinancial = italcred.getSignatureConfirmation(entrega.getAccountNo(),entrega.getCedula());
				if(!isValeFirmadoFinancial){
					entrega.setobservaciones("usuario firmó vale y solicitud correctamente?");
					entrega.setisValeSignature(false);
					entrega.setIsNeedCheckVale(true);
					entrega.setCardAction("");
					entrega.saveEx();
					
				}
				else{
					entrega.setisValeSignature(true);
					entrega.setIsNeedCheckVale(false);
					entrega.setCardAction("confirmar");
					entrega.setobservaciones("Firma Confirmada");
					entrega.saveEx();
				}
				//Fin OpenUp.	
				return "OK";
	}

}
