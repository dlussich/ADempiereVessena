package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

public class MHRProcesoNomEmpleados extends X_UY_HRProcesoNomEmpleados {

	private static final long serialVersionUID = 1L;

	public MHRProcesoNomEmpleados(Properties ctx,
			int UY_HRProcesoNomEmpleados_ID, String trxName) {
		super(ctx, UY_HRProcesoNomEmpleados_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MHRProcesoNomEmpleados(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
				
		//se impide ingresar empleados repetidos
		String sql = "SELECT count(c_bpartner_id) FROM uy_hrprocesonomempleados WHERE c_bpartner_id=" + this.getC_BPartner_ID() +
				     " AND uy_hrprocesonomina_id=" + this.getUY_HRProcesoNomina_ID();
		int res = DB.getSQLValueEx(null, sql);

		if (res > 0) throw new AdempiereException("Ya existe registro para éste empleado");
		
		return true;
	}
	
	

}
