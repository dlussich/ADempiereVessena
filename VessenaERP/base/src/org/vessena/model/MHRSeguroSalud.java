package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

public class MHRSeguroSalud extends X_UY_HRSeguroSalud {

	private static final long serialVersionUID = 1L;

	public MHRSeguroSalud(Properties ctx, int UY_HRSeguroSalud_ID, String trxName) {
		super(ctx, UY_HRSeguroSalud_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MHRSeguroSalud(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		//se valida el porcentaje
		if (this.getporcentaje() != null) {

			if (this.getporcentaje().compareTo(Env.ZERO) <= 0 || this.getporcentaje().compareTo(Env.ONEHUNDRED) > 0)
				throw new AdempiereException("Porcentaje debe ser entre 1 y 100");

		}

		return true;
	}

}
