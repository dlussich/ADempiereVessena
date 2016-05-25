package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MHRNovedades extends X_UY_HRNovedades {

	private static final long serialVersionUID = 1L;

	public MHRNovedades(Properties ctx, int UY_HRNovedades_ID, String trxName) {
		super(ctx, UY_HRNovedades_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MHRNovedades(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//se impide ingresar mas de 1 registro para el mismo proceso de nomina y mismo empleado
		if (newRecord || (!newRecord && (is_ValueChanged("UY_HRProcess_ID") || is_ValueChanged("C_BPartner_ID")) )) {

			String sql = "SELECT count(uy_hrnovedades_id) FROM uy_hrnovedades WHERE uy_hrprocess_id= " + this.getUY_HRProcess_ID()
					     + " AND c_bpartner_id= " + this.getC_BPartner_ID();
			int res = DB.getSQLValueEx(null, sql);

			if (res > 0)  {
				
				MHRProcess process = new MHRProcess(getCtx(), this.getUY_HRProcess_ID(), get_TrxName());
				MBPartner partner = new MBPartner(getCtx(), this.getC_BPartner_ID(), get_TrxName());
								
				throw new AdempiereException("Ya existe un registro para el empleado: " + '"' + partner.getName() + '"' + 
						   " y la liquidacion: " + '"' + process.getName() + '"');								
				
			}

		}
				
		return true;
	}
	
	/**
	 * Metodo que devuelve el cabezal para ese empleado y liquidacion
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 11/05/2012
	 * @see
	 * @param partnerID
	 * @return
	 */	
	public static MHRNovedades forProcessEmployee(int partnerID, int processID, String trxName) {
		
		StringBuilder whereClause = new StringBuilder(X_UY_HRNovedades.COLUMNNAME_C_BPartner_ID + " =" + partnerID)
									.append(" AND " + X_UY_HRNovedades.COLUMNNAME_UY_HRProcess_ID + " =" + processID);

		MHRNovedades value = new Query(Env.getCtx(), I_UY_HRNovedades.Table_Name, whereClause.toString(), trxName)
							.first();

		return value;
		
	}
	
	/***
	 * Obtiene y retorna lineas de este cabezal de novedades recibiendo un WHERE opcionalmente.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 23/01/2014
	 * @see
	 * @return
	 */
	public List<MHRConceptoLine> getLines(String where){

		String whereClause = X_UY_HRConceptoLine.COLUMNNAME_UY_HRNovedades_ID + "=" + this.get_ID();
		
		if(where!=null && !where.equalsIgnoreCase("")) whereClause += where;

		List<MHRConceptoLine> lines = new Query(getCtx(), I_UY_HRConceptoLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

	@Override
	protected boolean beforeDelete() {
		
		if(this.getUY_HRLoadDriver_ID() > 0) throw new AdempiereException ("Imposible borrar cabezal de novedades por tener lineas generadas desde una carga de planilla de conductores");
				
		return true;
	}

}
