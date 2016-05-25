/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

/**
 * @author gbrust
 *
 */
public class MTTRetentionScan extends X_UY_TT_RetentionScan {

	/**
	 * 
	 */
	private static final long serialVersionUID = 830302211537326688L;

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if(success){
			
			MTTRetention retention = new MTTRetention(this.getCtx(),this.getUY_TT_Retention_ID(), this.get_TrxName());	   
			
			
			if (this.getScanText() == null) return true;
			if (this.getScanText().trim().equalsIgnoreCase("")) return true;
			
			MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), this.getScanText().trim(), null);
			if (incidencia == null){
				throw new AdempiereException("No existe Incidencia con ese Número.");
			}
			
			MTTCard model = MTTCard.forIncidenciaAndDeliveryPoint(this.getCtx(),this.get_TrxName(), incidencia.get_ID(), retention.getUY_DeliveryPoint_ID());		
			
			MTTRetentionLine line = new MTTRetentionLine(this.getCtx(), 0, this.get_TrxName());	
			line.setUY_TT_Retention_ID(retention.get_ID());
			line.setDateTrx(new Timestamp(System.currentTimeMillis()));
			line.setUY_TT_RetentionScan_ID(this.get_ID());
			
			//Primero verifico que la cuenta escaneada sea una cuenta RETENIDA
			if(model != null){
				MTTCardStatus status = (MTTCardStatus) model.getUY_TT_CardStatus();
				if(status.get_ID() <= 0) throw new AdempiereException("La cuenta no tiene un estado establecido");
				
				//Verifico que ya no se haya leido en este proceso
				MTTRetentionLine lineOld = MTTRetentionLine.forCardIDAndRetentionID(this.getCtx(), this.get_TrxName(), model.get_ID(), this.getUY_TT_Retention_ID());
				
				// OpenUp Sylvie Bouissa 26-11-2014 Issue# 3306 
				if(lineOld != null){
					throw new AdempiereException("La cuenta "+model.getAccountNo()+" ya fue procesada ");
					//throw new AdempiereException("La cuenta leida ya se encuentra marcada para su destrucción");				
				}		
				
				if((status).getValue().equals("retenida")){
					
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
					//OpenUp Sylvie Bouissa 26-11-2014 Issue# 3306 
					try{
						line.saveEx();
						return true;  
					}catch (Exception e){
						throw new AdempiereException(mensajeCte.toString());
					}		
				
				}else{
					throw new AdempiereException("La cuenta leida se encuentra en estado: "+ ((MTTCardStatus) model.getUY_TT_CardStatus()).getName() + " en la caja: " + ((MTTBox) model.getUY_TT_Box()).getValue());
				}					
				
			}else{
				throw new AdempiereException("Cuenta no ubicada en el sistema");
			}	
			
		}else return success;
	}

	/**
	 * @param ctx
	 * @param UY_TT_RetentionScan_ID
	 * @param trxName
	 */
	public MTTRetentionScan(Properties ctx, int UY_TT_RetentionScan_ID,
			String trxName) {
		super(ctx, UY_TT_RetentionScan_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTRetentionScan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	// INI OpenUp Sylvie Buissa 26-11-2014 Issue# 3306
	//Se agrega variable para que informe error correcto cuando no se puede crear la linea (uy_tt_retentionline)
	private static String mensajeCte = "Error al procesar cuenta";

	public static void setMensajeCte(String mjeIn) {
		mensajeCte = mjeIn;
	}
	// INI OpenUp Sylvie Buissa 26-11-2014 Issue# 3306
	
	
}
