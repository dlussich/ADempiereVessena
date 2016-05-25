package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MBPRemuneracion extends X_UY_BPRemuneracion {

	private static final long serialVersionUID = 1L;

	public MBPRemuneracion(Properties ctx, int UY_BP_Remuneracion_ID, String trxName) {
		super(ctx, UY_BP_Remuneracion_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPRemuneracion(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		//se controla importe negativo
		if (this.getAmount().compareTo(Env.ZERO) < 0) {

			throw new AdempiereException("No se permite importe negativo");
		}

		String sql = "SELECT importe_cero FROM c_remuneration WHERE c_remuneration_id=" + this.getC_Remuneration_ID();
		String cero = DB.getSQLValueStringEx(null, sql);
		//solo se permitira importe cero si el tipo de remuneracion selecionado lo admite
		if (cero.equalsIgnoreCase("N") && this.getAmount().compareTo(Env.ZERO) == 0) {

			throw new AdempiereException("Importe debe ser mayor a cero");

		}

		return true;
	}
	
	/***
	 * Obtiene y retorna remuneracion para un empleado recibido.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 14/01/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MBPRemuneracion forBPartner(Properties ctx, int partnerID, String trxName){
		
		if (partnerID <= 0) return null;
				
		String whereClause = " c_bpartner_ID = " + partnerID;
		
		MBPRemuneracion model = new Query(ctx, I_UY_BPRemuneracion.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
	}

}
