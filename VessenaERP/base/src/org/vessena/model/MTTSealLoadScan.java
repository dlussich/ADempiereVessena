/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;

/**
 * @author gbrust
 *
 */
public class MTTSealLoadScan extends X_UY_TT_SealLoadScan {

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if(success){
			
			if ((this.getScanText() == null) || (this.getScanText().trim().equalsIgnoreCase(""))){
				return true;
			}
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			
			MTTSealLoad load = new MTTSealLoad(this.getCtx(),this.getUY_TT_SealLoad_ID(), this.get_TrxName());	   
			
			
			// Obtengo cuenta segun incidencia asociada
			//MTTCard model = MTTCard.forAccountDeliveryPointandBoxes(this.getCtx(), this.getScanText(),load.getUY_DeliveryPoint_ID_From(),boxes,this.get_TrxName());

			// Obtengo modelo de incidencia segun numero de la misma
			MTTCard model = null;
			
			MRReclamo incidencia = MRReclamo.forDocumentNo(this.getCtx(), this.getScanText().trim(), null);
			
			if (incidencia != null){
				model = MTTCard.forIncidencia(this.getCtx(), incidencia.get_ID(), this.get_TrxName());	
			}
			
			List<MTTSealLoadBox> boxes = MTTSealLoadBox.forSealLoad(this.getCtx(),this.getUY_TT_SealLoad_ID(), this.get_TrxName());
			
			MTTSealLoadLine line = new MTTSealLoadLine(this.getCtx(), 0, this.get_TrxName());	
			line.setUY_TT_SealLoad_ID(load.get_ID());
			line.setDateTrx(new Timestamp(System.currentTimeMillis()));
			line.setUY_TT_SealLoadScan_ID(this.get_ID());
			
			// Tengo cuenta 
			if(model != null){
				
				// Cuenta abierta para tracking
				if (!model.isOpenForTracking()){
					if (!config.isForzed()){
						ADialog.info(0,null,"La cuenta no se encuentra en proceso de Tracking.");
						throw new AdempiereException("La cuenta no se encuentra en proceso de Tracking.");
					}
				}
				
				// Verifico ubicación actual de cuenta
				if (model.getUY_DeliveryPoint_ID_Actual() != load.getUY_DeliveryPoint_ID_From()){
					if (!config.isForzed()){
						ADialog.info(0,null,"La cuenta no se encuentra en el punto de distriución origen.");
						throw new AdempiereException("La cuenta no se encuentra en el punto de distriución origen.");
					}
				}
				
				// Verifico que la cuenta este en una de las cajas asociadas a este documento
				boolean boxOK = false;
				for (MTTSealLoadBox box : boxes) {
					MTTBox aux = new MTTBox(this.getCtx(), box.getUY_TT_Box_ID(), this.get_TrxName());
					if (model.getUY_TT_Box_ID() == aux.get_ID()) {
						boxOK = true;
						break;
					}
				}
				if (!boxOK){
					if (!config.isForzed()){
						ADialog.info(0,null,"La cuenta no se encuentra en ninguna de las cajas asociadas.");
						throw new AdempiereException("La cuenta no se encuentra en ninguna de las cajas asociadas.");
					}
				}
				
				// Verifico que la cuenta escaneada sea una cuenta RECEPCIONADA
				MTTCardStatus status = (MTTCardStatus) model.getUY_TT_CardStatus();
				if(status.get_ID() <= 0) {
					if (!config.isForzed()){
						ADialog.info(0,null,"La cuenta no tiene un estado establecido");
						throw new AdempiereException("La cuenta no tiene un estado establecido");
					}
				}
				
				// Verifico que no se encuentre leida por alguna carga de bolsin (incluso esta) osea que pregunto si no esta en estado PENDIENTE DE ENVIO
				// Si la cuenta no esta recepcionada tengo que verificar si ya no fue leida y esta retenida por alguna instancia anterior
				if(((MTTCardStatus) model.getUY_TT_CardStatus()).getValue().equals("retenida")){
					if (!config.isForzed()){
						ADialog.info(0,null,"La cuenta leida ya se encuentra retenida en la caja: " + ((MTTBox) model.getUY_TT_Box()).getValue());
						throw new AdempiereException("La cuenta leida ya se encuentra retenida en la caja: " + ((MTTBox) model.getUY_TT_Box()).getValue());						
					}

				}else if(((MTTCardStatus) model.getUY_TT_CardStatus()).getValue().equals("pendenvio")){
					
					if (!config.isForzed()){
						ADialog.info(0,null,"La cuenta leida ya se encuentra en el bolsin: " + ((MTTSeal) model.getUY_TT_Seal()).getValue());
						throw new AdempiereException("La cuenta leida ya se encuentra en el bolsin: " + ((MTTSeal) model.getUY_TT_Seal()).getValue());				
					}
				}		

				// Si la cuenta no esta recepcionada pero tengo configuracion forzada sigo sin errores
				if(!status.getValue().equalsIgnoreCase("recepcionada")){
					if (config.isForzed()){
						status = MTTCardStatus.forValue(getCtx(), null, "recepcionada");
					}
				}
				
				if(status.getValue().equalsIgnoreCase("recepcionada")){
					
					line.setUY_TT_Card_ID(model.get_ID());
					line.setName(model.getName());		
					line.setProductoAux(model.getProductoAux());
					line.setGAFCOD(model.getGAFCOD());
					line.setGAFNOM(model.getGAFNOM());
					line.setGrpCtaCte(model.getGrpCtaCte());
					line.setMLCod(model.getMLCod());
					line.setUY_TT_Box_ID(model.getUY_TT_Box_ID());			
					line.setCreditLimit(model.getCreditLimit());
					line.setCardDestination(model.getCardDestination());
					line.setCardAction(model.getCardAction());
					line.setIsValid(false);
					line.setInvalidText("");
					
					line.saveEx();	
					
					return true;
				}
				else{
					ADialog.info(0,null,"Cuenta No Recepcionada según Numero de Incidencia Ingresado");
					throw new AdempiereException("Cuenta No Recepcionada según Numero de Incidencia Ingresado");
				}	
				
			}else{
				ADialog.info(0,null,"Cuenta No Recepcionada según Numero de Incidencia Ingresado");
				throw new AdempiereException("Cuenta No Recepcionada según Numero de Incidencia Ingresado");
			}	
			
		}else return success;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6036904368925059497L;

	/**
	 * @param ctx
	 * @param UY_TT_SealLoadScan_ID
	 * @param trxName
	 */
	public MTTSealLoadScan(Properties ctx, int UY_TT_SealLoadScan_ID,
			String trxName) {
		super(ctx, UY_TT_SealLoadScan_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTSealLoadScan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
