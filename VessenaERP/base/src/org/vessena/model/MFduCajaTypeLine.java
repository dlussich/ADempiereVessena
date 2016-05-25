package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

public class MFduCajaTypeLine extends X_UY_Fdu_Caja_Type_Line{

	private static final long serialVersionUID = -275206378430824409L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Fdu_Caja_Type_Line_ID
	 * @param trxName
	 */
	public MFduCajaTypeLine(Properties ctx, int UY_Fdu_Caja_Type_Line_ID,
			String trxName) {
		super(ctx, UY_Fdu_Caja_Type_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduCajaTypeLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MFduCajaTypeLine forC_Activity(Properties ctx, int uy_fdu_caja_type_id,int c_activity, String trxName){
		
		String whereClause = X_UY_Fdu_Caja_Type_Line.COLUMNNAME_C_Activity_ID + "='" + c_activity + "'"+" AND " +X_UY_Fdu_Caja_Type_Line.COLUMNNAME_UY_Fdu_Caja_Type_ID + "='" + uy_fdu_caja_type_id + "'";
		
		MFduCajaTypeLine linea = new Query(ctx, I_UY_Fdu_Caja_Type_Line.Table_Name, whereClause, trxName)
		.first();
				
		return linea;
	}

	

}
