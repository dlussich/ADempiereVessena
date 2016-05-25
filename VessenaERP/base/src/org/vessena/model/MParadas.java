package org.openup.model;

import java.util.Properties;

import org.compiere.apps.ADialog;
import org.compiere.model.MConfirmorderhdr;

public class MParadas extends X_UY_Paradas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MParadas(Properties ctx, int UY_Paradas_ID, String trxName) {
		super(ctx, UY_Paradas_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	// OpenUp. Nicolas Garcia. 23/08/2011. Actualizado para Issue #842
	protected boolean beforeSave(boolean newRecord) {
		//Instancio Confirmacion de proceso
		MConfirmorderhdr confirmaOrder =(MConfirmorderhdr) this.getUY_Confirmorderhdr();
		
		String controlFechas="";
		//Controlo Fechas
		//Fecha inicial de la parada menor a fecha inicial de la orden
		if (this.getUY_horainicio().compareTo(confirmaOrder.getDateStart()) < 0) {
			
			controlFechas+="La fecha de inicio de la parada es menor a la fecha de inicio de la confirmacion \n";
		}
		//Fecha de fin de la parada mayor a la fecha de fin de la parada
		if(this.getUY_horafin().compareTo(confirmaOrder.getDateFinish())>0){
			
			controlFechas+="La fecha de fin de la parada es mayor a la fecha de fin de la confirmacion \n";
		}
		//Fecha de inicio mayor a fecha de fin
		if(this.getUY_horafin().compareTo(this.getUY_horainicio())<=0){
			
			controlFechas+="La fecha de inicio de la parada tiene que ser menor a la fecha de fin de la parada \n";
		}
		
		//Si hay mensaje es que hay error
		if(!controlFechas.equals("")){
			ADialog.error(0, null, controlFechas);
			return false;
		}
		
		return true;
	}

	
	
}
