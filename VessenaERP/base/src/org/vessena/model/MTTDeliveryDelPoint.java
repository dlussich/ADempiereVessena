/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MTTDeliveryDelPoint extends X_UY_TT_DeliveryDelPoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4487536684189211101L;

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Comento esta validacion ya que no tiene sentido en la operativa diaria
		/*
		
		//Acá tengo que validar que no se seleccionen puntos de distribucion internos y externos a la vez, y en el caso que se seleccionen externos tiene que ser uno solo
		List<MTTDeliveryDelPoint> lineasSeleccionadas = this.getLinesSelected();
		
		//Acá siginfica que no hay lineas seleccionadas todavia, osea que puedo guardar el cambio tranquilo
		if(lineasSeleccionadas.size() == 0) return true;
		
		boolean existeExterno = false;
		boolean existeInterno = false;
		
		//Primero recorro y voy guardando para ver cuales existen ya seleccionado
		for (MTTDeliveryDelPoint mttDeliveryDelPoint : lineasSeleccionadas) {			
			if(((MDeliveryPoint) mttDeliveryDelPoint.getUY_DeliveryPoint()).isOwn()) existeInterno = true;
			else existeExterno = true;			
		}
			
		//Punto de distribución seleccionado actualmente a guardar
		MDeliveryPoint puntoDistribucion = (MDeliveryPoint) this.getUY_DeliveryPoint();
		
		
		//Caso en el que el punto de distribucion es propio
		if(puntoDistribucion.isOwn()){
			if(existeExterno) throw new AdempiereException("Ya existe un punto de distribucion externo seleccionado");
			existeInterno = true;
		
		}else{
			if(existeExterno){
				//Ahora si tenemos que deseleccionar un item verificamos que este deseleccionado si no lanzamos exception porque se intenta ingresar dos 
				//puntos de distribucion externos.
				if(this.isSelected()) throw new AdempiereException("Ya existe un punto de distribucion externo seleccionado");
			}
			if(existeInterno) throw new AdempiereException("Ya existe al menos un punto de distribucion interno seleccionado");
			existeExterno = true;
		}			
		
	    */
					
		return true;
	}
	
	/**
	 * OpenUp. Guillermo Brust. 09/09/2013. ISSUE #
	 * Método que devuelve los puntos de distribucion seleccionados en esta grilla
	 * 
	 */
	private List<MTTDeliveryDelPoint> getLinesSelected(){
		
		String whereClause = X_UY_TT_DeliveryDelPoint.COLUMNNAME_IsSelected + " = 'Y'" +
							" AND " + X_UY_TT_DeliveryDelPoint.COLUMNNAME_UY_TT_Delivery_ID + " = " + this.getUY_TT_Delivery_ID();
		
		List<MTTDeliveryDelPoint> lista = new Query(this.getCtx(), I_UY_TT_DeliveryDelPoint.Table_Name, whereClause, this.get_TrxName())
		.list();
		
		
		return lista;
	}
	
	/**
	 * OpenUp. Guillermo Brust. 10/09/2013. ISSUE #
	 * Método que devuelve un modelo de de esta tabla que contenga el 
	 * 
	 */
	public static List<MTTDeliveryDelPoint> forDeliveryIDAndSelected(Properties ctx, int deliveryID){
		
		String whereClause = X_UY_TT_DeliveryDelPoint.COLUMNNAME_UY_TT_Delivery_ID + " = " + deliveryID +
							" AND " + X_UY_TT_DeliveryDelPoint.COLUMNNAME_IsSelected + " = 'Y'";
		
		List<MTTDeliveryDelPoint> lista = new Query(ctx, I_UY_TT_DeliveryDelPoint.Table_Name, whereClause, null)
		.list();
		
		
		return lista;
	}
	
	/**
	 * OpenUp. Guillermo Brust. 12/09/2013. ISSUE #
	 * Método que devuelve una lista de modelos de de esta tabla que esten seleccionados y sean para el punto de distribucion pasado por parametro 
	 * 
	 */
	public static List<MTTDeliveryDelPoint> forDeliveryPointIDAndSelected(Properties ctx, int deliveryPointID){
		
		String whereClause = X_UY_TT_DeliveryDelPoint.COLUMNNAME_UY_DeliveryPoint_ID + " = " + deliveryPointID +
							" AND " + X_UY_TT_DeliveryDelPoint.COLUMNNAME_IsSelected + " = 'Y'";
		
		List<MTTDeliveryDelPoint> lista = new Query(ctx, I_UY_TT_DeliveryDelPoint.Table_Name, whereClause, null)
		.list();
		
		
		return lista;
	}

	/**
	 * @param ctx
	 * @param UY_TT_DeliveryDelPoint_ID
	 * @param trxName
	 */
	public MTTDeliveryDelPoint(Properties ctx, int UY_TT_DeliveryDelPoint_ID,
			String trxName) {
		super(ctx, UY_TT_DeliveryDelPoint_ID, trxName);
		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTDeliveryDelPoint(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}

}
