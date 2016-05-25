package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MBPDescRecurrentes extends X_UY_BPDescRecurrentes {

	private static final long serialVersionUID = 1L;

	public MBPDescRecurrentes(Properties ctx, int UY_BPDescRecurrentes_ID, String trxName) {
		super(ctx, UY_BPDescRecurrentes_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPDescRecurrentes(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//validacion para campo "Concepto"
		String sql = "SELECT uy_hrtipodescuento_id FROM uy_hrtipodescuento WHERE value='2'";
		int descID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if (this.getUY_HRTipoDescuento_ID() == descID){
			
			if (this.getHR_Concept_ID() <= 0) throw new AdempiereException("Debe seleccionar un concepto");
		}
	
		//validaciones para forma de descuento: % Nominal
		if (this.gettipo().equalsIgnoreCase("PN")) {

			if (this.getporcentaje().compareTo(Env.ZERO) <= 0 || this.getporcentaje().compareTo(Env.ONEHUNDRED) > 0)
				throw new AdempiereException("Porcentaje debe ser entre 1 y 100");

			
		}

		//validaciones para forma de descuento: Fijo Mensual
		if (this.gettipo().equalsIgnoreCase("FM")) {

			if (this.getamountfijo().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Importe debe ser mayor a cero");

		}
		
		//validaciones para forma de descuento: Cuotas
		if (this.gettipo().equalsIgnoreCase("CU")) {

			if (this.getfechacuota() == null) throw new AdempiereException("Debe seleccionar fecha de inicio");

			if (this.getcuotas() <= 0) throw new AdempiereException("Cantidad de cuotas inválida");

			if (this.getamountcuota().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Importe debe ser mayor a cero");

		}

		return true;
	}

}
