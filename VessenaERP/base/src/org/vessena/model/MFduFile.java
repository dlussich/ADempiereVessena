package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * 
 * org.openup.model - MFduFile
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author leonardo.boccone - 29/04/2014
 * @see
 */
public class MFduFile extends X_UY_FduFile{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6001603914523505029L;

	public MFduFile(Properties ctx, int UY_FduFile_ID, String trxName) {
		super(ctx, UY_FduFile_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MFduFile(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MFduFile getMFduFileForValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_FduFile.COLUMNNAME_Value + "=" + "'"+ value + "'";
		
		MFduFile file = new Query(ctx, I_UY_FduFile.Table_Name, whereClause, trxName)
		.firstOnly();
		
		return file;
	}
	


}
