/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 01/08/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;

/**
 * org.openup.model - MTTSeal
 * OpenUp Ltda. Issue #1180
 * Description: Modelo de precintos de bolsines.
 * @author Gabriel Vila - 01/08/2013
 * @see
 */
public class MTTSeal extends X_UY_TT_Seal {

	private static final long serialVersionUID = -5543590661529447857L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_Seal_ID
	 * @param trxName
	 */
	public MTTSeal(Properties ctx, int UY_TT_Seal_ID, String trxName) {
		super(ctx, UY_TT_Seal_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTSeal(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeDelete() {

		if (this.isConfirmed()){
			throw new AdempiereException("No es posible Eliminar Precintos Confirmados.");
		}
		
		return true;
	}

	/***
	 * Setea el Precinto como EN RECEPCION y valido si esta apto para ello.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 08/08/2013
	 * @see
	 * @return
	 */
	public String setInReceipt() {
		
		// Para ser recepcionado el precinto tiene que estar CERRADO o en Recepcion
		if ( (!this.getSealStatus().equalsIgnoreCase(SEALSTATUS_Cerrado))
				&& (!this.getSealStatus().equalsIgnoreCase(SEALSTATUS_RecepcionDeCuentas))){
			
			return "El Precinto seleccionado no tiene un estado apto para ser Recepcionado.";
		}
		
		if (!this.isConfirmed()){
			return "El Precinto seleccionado no esta confirmado y por lo tanto no es apto para Recepcionar.";		
		}
		
		// Cuando esta siendo recepcionado el precinto cambia de estado.
		// Recordar que puede estar siendo abierto y controlado por mas de un usuario al tiempo.
		if (!this.getSealStatus().equalsIgnoreCase(SEALSTATUS_RecepcionDeCuentas)){
			this.setSealStatus(SEALSTATUS_RecepcionDeCuentas);
		}
		
		// Si tengo cambios, los guardo.
		if (this.is_Changed()){
			this.saveEx();	
		}

		return null;
	}

	
	/**
	 * OpenUp. Guillemo Brust. 27/08/2013. ISSUE #
	 * Metodo que devuelve una lista de modelos de MTTSeal, solamente si estos cumplen con la condicion de que tengan el punto de distribucion origen y destino
	 * iguales a los que se pasan por paramtro, y a parte que este en estado PENDIENTE DE ENVÍO.
	 * 
	 * */
	public static List<MTTSeal> getForDelPointAndDelPointFrom(Properties ctx, String trxName, int delPoint, int delPointFrom){ 
						
		String whereClause = X_UY_TT_Seal.COLUMNNAME_UY_DeliveryPoint_ID + " = " + delPointFrom + 						
							" AND " + X_UY_TT_Seal.COLUMNNAME_UY_DeliveryPoint_ID_To + " = " + delPoint +	
							" AND " + X_UY_TT_Seal.COLUMNNAME_SealStatus + " = '" + SEALSTATUS_PendienteEnvio + "'" +	
							" AND " + X_UY_TT_Seal.COLUMNNAME_IsActive + " = 'Y'";
		
		List<MTTSeal> model = new Query(ctx, I_UY_TT_Seal.Table_Name, whereClause, trxName).list();		
		
		return model;		
	}
	
	
	/**
	 * OpenUp. Guillemo Brust. 27/08/2013. ISSUE #
	 * Metodo que devuelve modelo de MTTSeal segun value (SearchKey)
	 * 
	 * */
	public static MTTSeal forValue(Properties ctx, String trxName, String value){ 
						
		String whereClause = X_UY_TT_Seal.COLUMNNAME_Value + " = '" + value + "'";					
		
		MTTSeal model = new Query(ctx, I_UY_TT_Seal.Table_Name, whereClause, trxName).first();		
		
		return model;		
	}
	
	/***
	 * Actualiza contador de elementos contados que contiene este bolsin.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @param count : cantidad a sumar o restar.
	 * @param add : true si suma, false si resta.
	 * @param topCount : Tope de elementos que puede contener este bolsin.
	 */
	public void updateQtyCount(int count, boolean add, int topCount,
							  String statusNotComplete, String statusComplete){

		try {
		
			if (!this.lock()){
				throw new AdempiereException(" El Bolsin : " + this.getValue() + " esta bloqueado por otro usuario en este momento.\n" +
						 " Por favor aguarde unos instantes y reintente la operación."); 
			}

			if (add){
				
				if (this.isComplete()){
					throw new AdempiereException(" El Bolsin : " + this.getValue() + " esta Completo y por lo tanto no se le puede agregar mas Cuentas.");
				}
				
				this.setQtyCount(this.getQtyCount() + count);
				if (this.getQtyCount() == topCount){
					this.setIsComplete(true);
					this.setSealStatus(statusComplete);
				}
			}
			else{

				if (this.getQtyCount() == 0){
					throw new AdempiereException(" El Bolsin : " + this.getValue() + " esta Vacío y por lo tanto no se le puede restar Cuentas.");
				}
				
				// Si ya esta en el tope
				boolean isOnTop = (this.getQtyCount() == topCount);
				
				this.setQtyCount(this.getQtyCount() - count);
				if (this.getQtyCount() < topCount){
					this.setIsComplete(false);
					if (isOnTop){
						this.setSealStatus(statusNotComplete);
					}
				}
			}

			this.unlock(get_TrxName());
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
	}


	/***
	 * Actualiza contador de elementos enviados que contiene este bolsin.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @param count : cantidad a sumar o restar.
	 * @param add : true si suma, false si resta.
	 * @param topCount : Tope de elementos que puede contener este bolsin.
	 */
	public void updateQtyBook(int count, boolean add, int topCount,
							  String statusNotComplete, String statusComplete){

		try {
		
			if (!this.lock()){
				throw new AdempiereException(" El Bolsin : " + this.getValue() + " esta bloqueado por otro usuario en este momento.\n" +
						 " Por favor aguarde unos instantes y reintente la operación."); 
			}

			if (add){
				if (this.isComplete()){
					throw new AdempiereException(" El Bolsin : " + this.getValue() + " esta Completo y por lo tanto no se le puede agregar mas Cuentas.");
				}
				this.setQtyBook(this.getQtyBook() + count);
				if (this.getQtyBook() == topCount){
					this.setIsComplete(true);
					this.setSealStatus(statusComplete);
				}
			}
			else{

				if (this.getQtyBook() == 0){
					throw new AdempiereException(" El Bolsin : " + this.getValue() + " esta Vacío y por lo tanto no se le puede restar Cuentas.");
				}
				
				// Si ya esta en el tope
				boolean isOnTop = (this.getQtyBook() == topCount);
				
				this.setQtyBook(this.getQtyBook() - count);
				if (this.getQtyBook() < topCount){
					this.setIsComplete(false);
					if (isOnTop){
						this.setSealStatus(statusNotComplete);
					}
				}
			}

			this.unlock(get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
	}

	
}
