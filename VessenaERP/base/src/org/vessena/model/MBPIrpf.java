package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

public class MBPIrpf extends X_UY_BPIrpf {

	private static final long serialVersionUID = 1L;

	public MBPIrpf(Properties ctx, int UY_BPIrpf_ID, String trxName) {
		super(ctx, UY_BPIrpf_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBPIrpf(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		//se controlan valores negativos...
		if (this.getsin_discapacidad() < 0) throw new AdempiereException("No se permite valor negativo");

		if (this.getcon_discapacidad() < 0) throw new AdempiereException("No se permite valor negativo");

		if (this.getaporte_mensual() != null) {
			if (this.getaporte_mensual().compareTo(Env.ZERO) < 0) throw new AdempiereException("No se permite valor negativo");
		}

		if (this.getotros() != null) {
			if (this.getotros().compareTo(Env.ZERO) < 0) throw new AdempiereException("No se permite valor negativo");
		}

		return true;

	}

}
