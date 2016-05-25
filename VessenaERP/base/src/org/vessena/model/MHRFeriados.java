package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

public class MHRFeriados extends X_UY_HRFeriados {

	private static final long serialVersionUID = 1L;

	public MHRFeriados(Properties ctx, int UY_HRFeriados_ID, String trxName) {
		super(ctx, UY_HRFeriados_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MHRFeriados(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		String sql = "";
		
		//se impide el ingreso de feriados repetidos (mismo mes y dia)
		sql = "SELECT COUNT(uy_hrferiados_id) FROM uy_hrferiados WHERE mes=" + "'" + this.getmes() + "'" + 
				" AND dias=" + "'" + this.getdias() + "'";
		
		int res = DB.getSQLValueEx(null, sql);
		
		if (res > 0) throw new AdempiereException("Ya se ingresó feriado para ésa fecha");
		
		
		return true;
	}
	
	
	
	
}
