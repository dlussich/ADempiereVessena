package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MBPOtrasRemuneraciones extends X_UY_BPOtrasRemuneraciones {

	private static final long serialVersionUID = 1L;

	public MBPOtrasRemuneraciones(Properties ctx, int UY_BPOtrasRemuneraciones_ID, String trxName) {
		super(ctx, UY_BPOtrasRemuneraciones_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPOtrasRemuneraciones(Properties ctx, ResultSet rs, String trxName) {
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

}
