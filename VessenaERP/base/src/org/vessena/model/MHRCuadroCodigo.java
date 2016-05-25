/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author Nicolas
 *
 */
public class MHRCuadroCodigo extends X_UY_HRCuadroCodigo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6748771230172172553L;

	/**
	 * @param ctx
	 * @param UY_HRCuadroCodigo_ID
	 * @param trxName
	 */
	public MHRCuadroCodigo(Properties ctx, int UY_HRCuadroCodigo_ID,
			String trxName) {
		super(ctx, UY_HRCuadroCodigo_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRCuadroCodigo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		String sql = "select uy_hrcuadrocodigo_id" +
		             " from uy_hrcuadrocodigo" +
				     " where hr_concept_id = " + this.getHR_Concept_ID() +
				     " and c_jobcategory_id = " + this.getC_JobCategory_ID() +
				     " and uy_hrcuadrocodigo_id <> " + this.get_ID();
		
		int ID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(ID > 0) throw new AdempiereException ("Ya existe un documento con el mismo concepto padre y categoria de posicion");
				
		return true;
	}
	
	/***
	 * Retorna las lineas del cuadro de codigos actual.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 14/01/2014
	 * @see
	 * @return
	 */
	public List<MHRCuadroCodigoLine> getLines(){
		
		String whereClause = X_UY_HRCuadroCodigo.COLUMNNAME_UY_HRCuadroCodigo_ID + "=" + this.get_ID();
		
		List<MHRCuadroCodigoLine> lines = new Query(getCtx(), I_UY_HRCuadroCodigoLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 14/01/2014. #1758.
	 * Metodo que devuelve un cuadro de codigos de nomina para un determinado concepto padre y categoria de empleado. 
	 * */
	public static MHRCuadroCodigo forConceptCategory(Properties ctx, int conceptID, int categoryID, String trxName){

		String whereClause = X_UY_HRCuadroCodigo.COLUMNNAME_HR_Concept_ID + "=" + conceptID + " AND " + X_UY_HRCuadroCodigo.COLUMNNAME_C_JobCategory_ID + "=" + categoryID;

		MHRCuadroCodigo hdr = new Query(ctx, I_UY_HRCuadroCodigo.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();

		return hdr;
	}

}
