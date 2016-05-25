/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 01/08/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.window.FDialog;
import org.compiere.apps.ADialog;
import org.compiere.model.Query;

/**
 * org.openup.model - MTTBox
 * OpenUp Ltda. Issue #1181
 * Description: Modelo de cajas para tracking de entidades.
 * @author Gabriel Vila - 01/08/2013
 * @see
 */
public class MTTBox extends X_UY_TT_Box {

	private static final long serialVersionUID = 511830532581079696L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_Box_ID
	 * @param trxName
	 */
	public MTTBox(Properties ctx, int UY_TT_Box_ID, String trxName) {
		super(ctx, UY_TT_Box_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTBox(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Actualiza contador de elementos que contiene esta box.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @param count : cantidad a sumar o restar.
	 * @param add : true si suma, false si resta.
	 * @param topCount : Tope de elementos que puede contener esta box.
	 * @param statusNotComplete : Estado en que quedara esta box si la misma deja de estar completa.
	 * @param statusComplete : Estado en que quedara esta box si la misma queda completa.
	 * @param useLocatorValue : true si se debe informar de ubicacion o no.
	 * @param windowNo : numero de ventana a considerar para mostrar el dialogo de informacion de caja. 
	 */
	public int updateQtyCount(int count, boolean add, int topCount, 
							  String statusNotComplete, String statusComplete,
							  boolean showDialog, boolean showLocatorValue, int windowNo,
							  boolean withLock, String description){

		int value = -1;
		
		try {
		
			if (withLock){
				if (!this.lock()){
					throw new AdempiereException(" La caja : " + this.getValue() + " esta bloqueada por otro usuario en este momento.\n" +
							 " Por favor aguarde unos instantes y reintente la operación."); 
					
				}
			}

			if (add){
				
				if (this.isComplete()){
					throw new AdempiereException(" La caja : " + this.getValue() + " esta Completa y por lo tanto no se le puede agregar mas Cuentas.");
				}
				
				this.setQtyCount(this.getQtyCount() + count);
				if (this.getQtyCount() == topCount){
					this.setIsComplete(true);
					this.setBoxStatus(statusComplete);
				}
			}
			else{

				if (this.getQtyCount() == 0){
					throw new AdempiereException(" La caja : " + this.getValue() + " esta Vacía y por lo tanto no se le puede restar Cuentas.");
				}
				
				// Si ya esta en el tope
				boolean isOnTop = (this.getQtyCount() == topCount);
				
				this.setQtyCount(this.getQtyCount() - count);
				if (this.getQtyCount() < topCount){
					this.setIsComplete(false);
					if (isOnTop){
						this.setBoxStatus(statusNotComplete);
					}
				}
			}
			
			value = this.getQtyCount();

			this.saveEx();

			// Si estaba agregando elementos a esta caja, despliego mensaje, si estaba restando no tiene sentido.
			if (showDialog){
				this.showBoxMessage(showLocatorValue, windowNo, description);
			}
			
			if (withLock){
				this.unlock(get_TrxName());	
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return value;
	}

	
	/***
	 * Setea la caja como EN RECEPCION y valido si esta apta para ello.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 08/08/2013
	 * @see
	 * @return
	 */
	public String setInReceipt(){
		
		// Para ser recepcionada la caja tiene que estar Cerrada, Nueva o En Recepcion.
		if ( (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_Cerrado))
				&& (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_RecepcionCuentas))
				&& (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_Nuevo))){
			
			return "La Caja seleccionada no esta en Estado para ser Recepcionada.";
		}
		
		if (!this.isConfirmed()){
			return "La Caja seleccionada no esta confirmada y por lo tanto no es apta para Recepcionar.";		
		}
		
		// Cuando esta siendo recepcionada la caja cambia de estado.
		// Recordar que puede estar siendo abierto y controlado por mas de un usuario al tiempo.
		if (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_RecepcionCuentas)){
			this.setBoxStatus(BOXSTATUS_RecepcionCuentas);
		}
		
		// Si tengo cambios, los guardo.
		if (this.is_Changed()){
			this.saveEx();	
		}

		return null;
	}	


	/***
	 * Setea la caja como en Unificacion y valido si esta apta para ello.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 08/08/2013
	 * @see
	 * @return
	 */
	public String setInUnify(){
		
		// Para utilizar esta caja en unificacion de cardcarriers tiene que estar Cerrada, Nueva o En Unificacion.
		if ( (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_Cerrado))
				&& (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_UnificacionCardCarrier))
				&& (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_Nuevo))){
			
			return "La Caja seleccionada no esta en Estado para ser utilizada en este Proceso.";
		}
		
		if (!this.isConfirmed()){
			return "La Caja seleccionada no esta confirmada y por lo tanto no es apta para este Proceso.";		
		}
		
		// Cuando esta siendo recepcionada la caja cambia de estado.
		// Recordar que puede estar siendo abierto y controlado por mas de un usuario al tiempo.
		if (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_UnificacionCardCarrier)){
			this.setBoxStatus(BOXSTATUS_UnificacionCardCarrier);
		}
		
		// Si tengo cambios, los guardo.
		if (this.is_Changed()){
			this.saveEx();	
		}

		return null;
	}	

	
	/***
	 * Setea la caja como en Comunicacion a Usuario y valido si esta apta para ello.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 06/10/2013
	 * @see
	 * @return
	 */
	public String setInComunica(){
		
		// Para utilizar esta caja en unificacion de cardcarriers tiene que estar Cerrada, Nueva o En Comunicacion.
		if ( (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_Cerrado))
				&& (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_ComunicacionUsuario))
				&& (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_Nuevo))){
			
			return "La Caja seleccionada no esta en Estado para ser utilizada en este Proceso.";
		}
		
		if (!this.isConfirmed()){
			return "La Caja seleccionada no esta confirmada y por lo tanto no es apta para este Proceso.";		
		}
		
		// Cuando esta siendo recepcionada la caja cambia de estado.
		// Recordar que puede estar siendo abierto y controlado por mas de un usuario al tiempo.
		if (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_ComunicacionUsuario)){
			this.setBoxStatus(BOXSTATUS_ComunicacionUsuario);
		}
		
		// Si tengo cambios, los guardo.
		if (this.is_Changed()){
			this.saveEx();	
		}

		return null;
	}
	
	/***
	 * Setea la caja como en Impresion de Vale y valido si esta apta para ello.
	 * OpenUp Ltda. Issue #3273 
	 * @author Sylvie Bouissa - 06/01/2015
	 * @see
	 * @return
	 */
	public String setInImpresionVale(){
		
		// Para utilizar esta caja en unificacion de cardcarriers tiene que estar Cerrada, Nueva o En Comunicacion.
		if ( (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_Cerrado))
				&& (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_ComunicacionUsuario))
				&& (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_ImpresionVale))
				&& (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_Nuevo))){
			
			return "La Caja seleccionada no esta en Estado para ser utilizada en este Proceso.";
		}
		
		if (!this.isConfirmed()){
			return "La Caja seleccionada no esta confirmada y por lo tanto no es apta para este Proceso.";		
		}
		
		// Cuando esta siendo recepcionada la caja cambia de estado.
		// Recordar que puede estar siendo abierto y controlado por mas de un usuario al tiempo.
		if (!this.getBoxStatus().equalsIgnoreCase(BOXSTATUS_ImpresionVale)){
			this.setBoxStatus(BOXSTATUS_ImpresionVale);
		}
		
		// Si tengo cambios, los guardo.
		if (this.is_Changed()){
			this.saveEx();	
		}

		return null;
	}
	
	/***
	 * Despliega mensaje de caja utilizada con opcion a mostrar o no ubicacion
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/09/2013
	 * @see
	 */
	private void showBoxMessage(boolean showLocatorValue, int windowNo, String description){

		String mensaje = "Guardar en Caja " + this.getValue();
		
		if (this.isDestiny()){
			MDeliveryPoint dpDestino = new MDeliveryPoint(getCtx(), this.getUY_DeliveryPoint_ID_To(), null);
			mensaje += " con Destino : " + dpDestino.getName();
		}
		else{
			if (this.isRetained()){
				mensaje += " de Cuentas Retenidas ";
				if (this.isUnificaCardCarrier()){
					mensaje += " por Unificacion de Card-Carriers ";	
				}
				else if (this.isComunicaUsuario()){
					mensaje += " por Comunicacion a Usuario ";	
				}
			}
		}
		
		if (showLocatorValue){
			mensaje += " - Posicion = " + getQtyCount();
		}
		
		if (description != null){
			mensaje += "\n" + description;
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
	

	/***
	 * Actualiza ubicaciones de la caja evitando los huecos.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 12/09/2013
	 * @see
	 */
	public void refresh(boolean lockBox, boolean lockCard){
		
		try {

			if (lockBox){
				if (!this.lock()){
					throw new AdempiereException(" La caja : " + this.getValue() + " esta bloqueada por otro usuario en este momento.\n" +
							 " Por favor aguarde unos instantes y reintente la operación.");					
				}
			}

			List<MTTBoxCard> lines = this.getBoxCards();
			for (int i = 0; i < lines.size(); i++){
				MTTBoxCard boxCard = lines.get(i);
				MTTCard card = (MTTCard)boxCard.getUY_TT_Card();
				if (card.lock() || !lockCard || card.isProcessing()){
					card.setLocatorValue(i + 1);
					card.saveEx();
					boxCard.setLocatorValue(i + 1);
					boxCard.saveEx();
					
					if (lockCard){
						card.unlock(get_TrxName());
					}
					
				}
				else{
					throw new AdempiereException(" La Cuenta : " + card.getAccountNo() + " esta bloqueada por otro usuario en este momento.\n" +
						" Por favor aguarde unos instantes y reintente la operación."); 
				}
			}

			if (lockBox){
				this.unlock(get_TrxName());	
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

	}

	
	/***
	 * Obtiene y retorna lista de cuentas contenidas en esta caja.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 12/09/2013
	 * @see
	 * @return
	 */
	public List<MTTBoxCard> getBoxCards(){
	
		String whereClause = X_UY_TT_BoxCard.COLUMNNAME_UY_TT_Box_ID + "=" + this.get_ID();
		
		List<MTTBoxCard> lines = new Query(getCtx(), I_UY_TT_BoxCard.Table_Name, whereClause, get_TrxName()).setOrderBy(" LocatorValue ").list();
		
		return lines;
		
	}

	
	/***
	 * Obtiene y retorna lista de contratos contenidos en esta caja.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 12/09/2013
	 * @see
	 * @return
	 */
	public List<MTTBoxContract> getBoxContracts(){
	
		String whereClause = X_UY_TT_BoxContract.COLUMNNAME_UY_TT_Box_ID + "=" + this.get_ID();
		
		List<MTTBoxContract> lines = new Query(getCtx(), I_UY_TT_BoxContract.Table_Name, whereClause, get_TrxName()).setOrderBy(" LocatorValue ").list();
		
		return lines;
		
	}

	
	@Override 
	//Consultar a gabriel los controles
	protected boolean beforeSave(boolean newRecord) {

		// Valido retenidas por un solo motivo
		if (this.isRetained()){
			
			if (isUnificaCardCarrier() && isComunicaUsuario()) {
				throw new AdempiereException("No es posible marcar mas de una opcion de Retencion.");
			}
			
			if (this.getRetainedStatus() != null){
				if (this.isUnificaCardCarrier() || this.isComunicaUsuario()){
					throw new AdempiereException("No es posible marcar mas de una opcion de Retencion.");
				}
			}
			
		}
			
		
		return true;
	}
	/**
	 * metodo para eliminar una cuenta de una caja cuando fue reubicada en otra
	 * OpenUp Ltda. Issue # 
	 * @author leonardo.boccone - 08/10/2014
	 * @see
	 * @param bCardOld
	 */
	public void removeCard(MTTBoxCard bCardOld) {
		try{
			
		
		MTTConfig config = MTTConfig.forValue(this.getCtx(), null, "tarjeta");
		bCardOld.deleteEx(true, this.get_TrxName());
		this.updateQtyCount(1, false, config.getTopBox(), X_UY_TT_Box.BOXSTATUS_Cerrado, 
				X_UY_TT_Box.BOXSTATUS_Cerrado, false, false, 0, false, null);
		}
		catch(Exception e){
			throw new AdempiereException("Error al borrar la tarjeta de una cuenta para reubicarla en otra");
		}
		
	}

	/***
	 * Actualiza ubicaciones de la caja evitando los huecos.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 12/09/2013
	 * @see
	 */
	public void refreshContracts(){
		
		try {

			List<MTTBoxContract> lines = this.getBoxContracts();
			for (int i = 0; i < lines.size(); i++){

				MTTBoxContract boxContract = lines.get(i);
				
				MTTContract contract = (MTTContract)boxContract.getUY_TT_Contract();

				contract.setLocatorValue(i + 1);
				contract.saveEx();
				boxContract.setLocatorValue(i + 1);
				boxContract.saveEx();
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

	}
	
	
	
}
