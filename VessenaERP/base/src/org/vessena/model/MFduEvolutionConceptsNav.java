/**
 * 
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

/**
 * @author gbrust
 *
 */
public class MFduEvolutionConceptsNav extends X_UY_Fdu_EvolutionConceptsNav {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7096992584331909174L;

	/**
	 * @param ctx
	 * @param UY_Fdu_EvolutionConceptsNav_ID
	 * @param trxName
	 */
	public MFduEvolutionConceptsNav(Properties ctx,
			int UY_Fdu_EvolutionConceptsNav_ID, String trxName) {
		super(ctx, UY_Fdu_EvolutionConceptsNav_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduEvolutionConceptsNav(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	public String getAdjustmentsCodes(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String retorno = "";
		
		try{		
					
			String sql = "SELECT cod.value" +
						 " FROM uy_fducod cod" +
						 " INNER JOIN uy_fdu_adjustments aj ON cod.uy_fducod_id = aj.uy_fducod_id" +
						 " WHERE aj.uy_fdu_evolutionconceptsnav_id = " +  this.get_ID() +
						 " AND aj.isactive = 'Y'";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    		    
		   	while (rs.next()){
				retorno += "'" + rs.getString("value") + "',";
			}
		   	
		   	if(!retorno.equals("")){
		   		retorno = retorno.substring(0, (retorno.length()) -1);
		   	}		   	

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return retorno;
	}
	
	
	public String getConsumptionsCodes(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String retorno = "";
		
		try{		
					
			String sql = "SELECT cod.value" +
						 " FROM uy_fducod cod" +
						 " INNER JOIN uy_fdu_consumptions cons ON cod.uy_fducod_id = cons.uy_fducod_id" +
						 " WHERE cons.uy_fdu_evolutionconceptsnav_id = " +  this.get_ID() +
						 " AND cons.isactive = 'Y'";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    		    
		   	while (rs.next()){
				retorno += "'" + rs.getString("value") + "',";
			}
		   	
		   	if(!retorno.equals("")){
		   		retorno = retorno.substring(0, (retorno.length()) -1);
		   	}		   	

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return retorno;
	}

}
