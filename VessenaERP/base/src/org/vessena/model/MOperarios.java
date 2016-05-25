package org.openup.model;

import java.util.Properties;

import org.compiere.apps.ADialog;
import org.compiere.model.MConfirmorderhdr;

public class MOperarios extends X_UY_Operarios{

	private static final long serialVersionUID = 2400939180715288617L;

	/**
	 * 
	 * OpenUp.
	 * MParadas
	 * Descripcion : issue #774
	 * @author Nicolas Garcia
	 * Fecha : 15/07/2011
	 */

	public MOperarios(Properties ctx, int UY_Operarios_ID, String trxName) {
		super(ctx, UY_Operarios_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		//Instancio Confirmacion de proceso
		MConfirmorderhdr confirmaOrder =(MConfirmorderhdr) this.getUY_Confirmorderhdr();
		
		String controlFechas="";
		//Controlo Fechas
		//Fecha inicial de la parada menor a fecha inicial de la orden
		if(this.getDateStart().compareTo(confirmaOrder.getDateStart())<0){
			
			controlFechas+="La fecha de inicio es menor a la fecha de inicio de la confirmacion \n";
		}
		//Fecha de fin de la parada mayor a la fecha de fin de la parada
		if(this.getDateFinish().compareTo(confirmaOrder.getDateFinish())>0){
			
			controlFechas+="La fecha de fin es mayor a la fecha de fin de la confirmacion \n";
		}
		//Fecha de inicio mayor a fecha de fin
		if(this.getDateFinish().compareTo(this.getDateStart())<=0){
			
			controlFechas+="La fecha de inicio tiene que ser menor a la fecha de fin \n";
		}
		
		//Si hay mensaje es que hay error
		if(!controlFechas.equals("")){
			ADialog.error(0, null, controlFechas);
			return false;
		}
		
		return true;
	}
	
}
