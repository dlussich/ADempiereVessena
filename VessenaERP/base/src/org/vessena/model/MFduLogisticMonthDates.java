/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MFduLogisticMonthDates extends X_UY_Fdu_LogisticMonthDates {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6464365503640733119L;

	/**
	 * @param ctx
	 * @param UY_Fdu_LogisticMonthDates_ID
	 * @param trxName
	 */
	public MFduLogisticMonthDates(Properties ctx,
			int UY_Fdu_LogisticMonthDates_ID, String trxName) {
		super(ctx, UY_Fdu_LogisticMonthDates_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduLogisticMonthDates(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		return super.beforeSave(newRecord);
	}

	public static List<MFduLogisticMonthDates> getFechasForMonth(Properties ctx, int uy_fdu_logisticMonth_id){
		
		String whereClause = I_UY_Fdu_LogisticMonthDates.COLUMNNAME_UY_Fdu_LogisticMonth_ID + " =  " + uy_fdu_logisticMonth_id;
     			
		List<MFduLogisticMonthDates> lines = new Query(ctx, I_UY_Fdu_LogisticMonthDates.Table_Name, whereClause, null)
		.setOrderBy(COLUMNNAME_DateTrx).list();

		return lines;
	}

}
