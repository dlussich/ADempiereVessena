/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 05/06/2015
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * org.openup.model - MBGBursa
 * OpenUp Ltda. Issue #4173 
 * Description: 
 * @author Gabriel Vila - 05/06/2015
 * @see
 */
public class MBGBursa extends X_UY_BG_Bursa {

	private static final long serialVersionUID = 3606385752486962120L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BG_Bursa_ID
	 * @param trxName
	 */
	public MBGBursa(Properties ctx, int UY_BG_Bursa_ID, String trxName) {
		super(ctx, UY_BG_Bursa_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGBursa(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	/**Obtngo id de bolsa correspondiente al usuario
	 * OpenUp Ltda Issue#
	 * @author SBouissa 31/7/2015
	 * @return
	 */
	public int getBolsa(int adUser) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="";
		try{
			sql = "SELECT UY_BG_Bursa_ID"
	    	 		+ " FROM UY_BG_Bursa WHERE AD_User_ID = "+adUser;
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt("UY_BG_Bursa_ID");
			}
			pstmt.close();
	     }catch(Exception e){
	    	 e.getMessage();
	     }finally{
	    	 
	    	 try{
	    		 pstmt.close();
	    		 rs.close();
	    	 }catch(Exception e){
	    		 
	    	 }
	     }
		return 0;
	}

}
