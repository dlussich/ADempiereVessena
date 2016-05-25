/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MUser;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.MDeliveryPoint;
import org.openup.model.MRCedulaCuenta;
import org.openup.model.MRReclamo;
import org.openup.model.MTTCard;
import org.openup.model.MTTHand;
import org.openup.model.X_UY_TT_Hand;

/**
 * @author gbrust
 *
 */
public class PTTHand extends SvrProcess {
	
	private MTTCard card = null;
	private MDeliveryPoint deliveryPoint = null;
	String observation = "";
	String account = "";
	String nrotarjeta = "";

	
	public PTTHand() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("AccountNo"))					
					//this.card = MTTCard.forAccountOpen(this.getCtx(), para[i].getParameter().toString(), null);
					account = para[i].getParameter().toString();
			}
			if (name!= null){
				if (name.equalsIgnoreCase("UY_DeliveryPoint_ID")){
					this.deliveryPoint = new MDeliveryPoint(this.getCtx(), ((BigDecimal)para[i].getParameter()).intValueExact(), this.get_TrxName());
				}					
			}
			if (name!= null){
				if (name.equalsIgnoreCase("Description")){
					if(para[i].getParameter()!=null){
						observation = para[i].getParameter().toString();
					}				
					
				}					
			}
			if (name!= null){
				if (name.equalsIgnoreCase("NroTarjetaNueva")){
					nrotarjeta = para[i].getParameter().toString();
				}		
			}
		}

	}

	
	@Override
	protected String doIt() throws Exception {
		
		//Validamos la cuenta ingresada	
		this.card = MTTCard.forAccountNoandCardNo(getCtx(), account, nrotarjeta, get_TrxName());
		String retorno = this.validateCard();
						
		if(!retorno.equalsIgnoreCase("")) throw new AdempiereException(retorno);	
		
		//Si valida bien, entonces marco el punto actual, el punto que seleccionaron en el proceso y creo la entrega.
		this.card.setUY_DeliveryPoint_ID_Actual(this.deliveryPoint.get_ID());
		this.card.saveEx();
		
		//Busco el documento
		MDocType documento = MDocType.forValue(this.getCtx(), "trackhand", null);
				
		//Acá creo la entrega
		MTTHand hand = new MTTHand(this.getCtx(), 0, this.get_TrxName());
		
		hand.setDateTrx(new Timestamp(System.currentTimeMillis()));
		hand.setC_DocType_ID(documento.get_ID());
		hand.setDocStatus(X_UY_TT_Hand.DOCSTATUS_Drafted);
		hand.setDescription("Entrega Forzada creada por " + new MUser(this.getCtx(), this.getAD_User_ID(), null).getDescription() + ".- " + this.observation);
		hand.setAD_User_ID(this.getAD_User_ID());
		hand.setUY_DeliveryPoint_ID(this.deliveryPoint.get_ID());
		hand.setCedula(this.card.getCedula());
		hand.setName2(this.card.getName());
		hand.setCedula2(this.card.getCedula());
		hand.setIsForzed(true);
		
		MRCedulaCuenta cedcta = MRCedulaCuenta.forCedulaCuenta(this.getCtx(), this.card.getCedula(), this.card.getAccountNo(), null);
		if ((cedcta == null) || (cedcta.get_ID() <= 0)){
			cedcta = new MRCedulaCuenta(this.getCtx(), 0, null);
			cedcta.setValue(this.card.getCedula());
			cedcta.setAccountNo(this.card.getAccountNo());
		}
		cedcta.setUY_R_Reclamo_ID(this.card.getUY_R_Reclamo_ID());
		
		MRReclamo incidencia = (MRReclamo)this.card.getUY_R_Reclamo();
		cedcta.setInternalCode(incidencia.getDocumentNo());
		
		cedcta.saveEx();

		
		hand.setUY_R_CedulaCuenta_ID(cedcta.get_ID());
				
		hand.saveEx();
		
		//Aplico y Completo.
		try {
			if (!hand.processIt(DocumentEngine.ACTION_Apply)){
				throw new AdempiereException(hand.getProcessMsg());
			}		
			if (!hand.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException(hand.getProcessMsg());
			}else{
				retorno = "Se creó la entrega forzada número " + hand.getDocumentNo();
			}				
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}		
		
		
		
		
		return retorno;
	}
	
	
	/***
	 * OpenUp. Guillermo Brust. 17/12/2013. ISSUE #1725
	 * Método que verifica si una cuenta ya fue entregada, si esta pendiente de recepcion, si el punto actual es un correo
	 * 
	 * */
	private String validateCard(){
		
		StringBuilder retorno = new StringBuilder();
		
		if(this.card != null && this.card.get_ID() > 0){
			
			//Verificamos si la cuenta ya fue entregada
			if(this.card.getUY_TT_CardStatus().getValue().equalsIgnoreCase("entregada")) retorno.append("La cuenta ya fue entregada.- ");
			
			//verificamos si la cuenta esta pendiente de recepcion
			if(this.card.getUY_TT_CardStatus().getValue().equalsIgnoreCase("pendrec")) retorno.append("La cuenta no ha sido recepcionada.- ");
			
			//Verificamos si el punto actual es un correo
			//if(new MDeliveryPoint(this.getCtx(), card.getUY_DeliveryPoint_ID_Actual(), this.get_TrxName()).isPostOffice()) retorno.append("El punto de distribución actual es un Correo.- ");			
			
		}else{
			retorno.append("No se reconoce la cuenta ingresada, o no tiene un estado válido.- ");
		}		
		
		return retorno.toString();
	}

}
