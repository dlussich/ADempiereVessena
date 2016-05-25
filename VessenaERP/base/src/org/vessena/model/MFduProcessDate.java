/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MFduProcessDate extends X_UY_Fdu_ProcessDate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2503952264692663255L;

	/**
	 * @param ctx
	 * @param UY_Fdu_ProcessDate_ID
	 * @param trxName
	 */
	public MFduProcessDate(Properties ctx, int UY_Fdu_ProcessDate_ID,
			String trxName) {
		super(ctx, UY_Fdu_ProcessDate_ID, trxName);
		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduProcessDate(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);		
	}
	
	
	public MFduProcessDate getLastDate(int uy_fduFile_id){
		
		String whereClause = I_UY_Fdu_ProcessDate.COLUMNNAME_UY_FduFile_ID + " =  " + uy_fduFile_id + 
     			" AND " + I_UY_Fdu_ProcessDate.COLUMNNAME_IsSelected + " = 'Y'";			
	 	
		MFduProcessDate date = (new Query(getCtx(), I_UY_Fdu_ProcessDate.Table_Name, whereClause, null).setOrderBy(COLUMNNAME_DateTrx + " desc")).first();
		
		
 		return date;
	}
	
	public List<MFduProcessDate> getNotProcessed(int uy_fduFile_id){
		
		String whereClause = I_UY_Fdu_ProcessDate.COLUMNNAME_UY_FduFile_ID + " =  " + uy_fduFile_id + 
	             			" AND " + I_UY_Fdu_ProcessDate.COLUMNNAME_IsSelected + " = 'N'";	
		
		List<MFduProcessDate> lines = new Query(getCtx(), I_UY_Fdu_ProcessDate.Table_Name, whereClause, get_TrxName())
		.setOrderBy(COLUMNNAME_DateTrx).list();
		
 		return lines;
	}
	
	public List<MFduProcessDate> getNotProcessed(int uy_fduFile_id, Timestamp fechaInicio, Timestamp fechaFin){
		
		String whereClause = I_UY_Fdu_ProcessDate.COLUMNNAME_UY_FduFile_ID + " =  " + uy_fduFile_id + 
	             			" AND " + I_UY_Fdu_ProcessDate.COLUMNNAME_IsSelected + " = 'N'" +
	             			" AND " + I_UY_Fdu_ProcessDate.COLUMNNAME_DateTrx + " between '" + fechaInicio + "' AND '" + fechaFin + "'";	
		
		List<MFduProcessDate> lines = new Query(getCtx(), I_UY_Fdu_ProcessDate.Table_Name, whereClause, get_TrxName())
		.setOrderBy(COLUMNNAME_DateTrx).list();
		
 		return lines;
	}
	
	public List<MFduProcessDate> getProcessedDates(int uy_fduFile_id, Timestamp fechaInicio, Timestamp fechaFin){
		
		String whereClause = I_UY_Fdu_ProcessDate.COLUMNNAME_UY_FduFile_ID + " =  " + uy_fduFile_id + 
	             			" AND " + I_UY_Fdu_ProcessDate.COLUMNNAME_IsSelected + " = 'Y'" +
	             			" AND " + I_UY_Fdu_ProcessDate.COLUMNNAME_DateTrx + " between '" + fechaInicio + "' AND '" + fechaFin + "'";	
		
		List<MFduProcessDate> lines = new Query(getCtx(), I_UY_Fdu_ProcessDate.Table_Name, whereClause, get_TrxName())
		.setOrderBy(COLUMNNAME_DateTrx).list();
		
 		return lines;
	}
	
	public List<MFduProcessDate> getNotProcessedDates(int uy_fduFile_id){
		
		String whereClause = I_UY_Fdu_ProcessDate.COLUMNNAME_UY_FduFile_ID + " =  " + uy_fduFile_id + 
	             			" AND " + I_UY_Fdu_ProcessDate.COLUMNNAME_IsSelected + " = 'N'";	
		
		List<MFduProcessDate> lines = new Query(getCtx(), I_UY_Fdu_ProcessDate.Table_Name, whereClause, get_TrxName())
		.setOrderBy(COLUMNNAME_DateTrx).list();
		
 		return lines;
	}
	
	public static MFduProcessDate forDateAndFileID(Properties ctx, Timestamp fecha, int uy_fdufile_id, String trxName){
		
		String whereClause = I_UY_Fdu_ProcessDate.COLUMNNAME_DateTrx + " = '" + fecha + "' AND " + 
							 I_UY_Fdu_ProcessDate.COLUMNNAME_UY_FduFile_ID + " = " + uy_fdufile_id;     				
	 	
		MFduProcessDate date = (new Query(ctx, I_UY_Fdu_ProcessDate.Table_Name, whereClause, trxName).setOrderBy(COLUMNNAME_DateTrx + " desc")).first();
		
		
 		return date;
	}

}
