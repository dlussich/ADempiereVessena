package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

public class MLabelPrintLine extends X_UY_LabelPrintLine{

	private static final long serialVersionUID = 8540802471876794060L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_LabelPrintLine_ID
	 * @param trxName
	 */
	public MLabelPrintLine(Properties ctx, int UY_LabelPrintLine_ID,
			String trxName) {
		super(ctx, UY_LabelPrintLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MLabelPrintLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static boolean numberOfLines(Properties Ctx, int get_ID, String TrxName) {
				
		String whereClause = X_UY_LabelPrintLine.COLUMNNAME_UY_LabelPrint_ID + "=" +  get_ID;
		
		List<MLabelPrintLine> lines = new Query(Ctx, I_UY_LabelPrintLine.Table_Name, whereClause, TrxName).list();
	
		if(lines.isEmpty()){
			return false;
		}
		return true;
	}

	
}
