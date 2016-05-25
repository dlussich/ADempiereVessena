/**
 * MReserveOrders.java
 * 11/01/2011
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.apps.ADialog;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MReserveOrders
 * Descripcion :
 * @author FL
 * Fecha : 11/01/2011
 */
public class MSimpleForecast extends X_UY_SimpleForecast {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4758633381879049856L;
	public String isActiveMsg = ""; //OpenUp Nicolas Sarlabos #959 01/02/2012


	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InOutType
	 * @param trxName
	 */
	public MSimpleForecast(Properties ctx, int UY_InOutType_ID,String trxName) {
		super(ctx, UY_InOutType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MSimpleForecast(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 	Get Lines
	 *	@return sucess
	 */
	public boolean BOMIt()  throws Exception  {
		
		// Delete previouse BOM
		DeleteBOM();
		
		MSimpleForecastLine[] lines=this.getLines();
		for (int i=0;i<lines.length;i++) {
			if (lines[i].BOMIt()) {
				this.setIsBOM(true);
				this.saveEx();
			} 
			else {
				this.setIsBOM(true);
				this.saveEx();
			}
		}

		if (isActiveMsg != "") ADialog.warn(0, null, null, isActiveMsg); //OpenUp Nicolas Sarlabos #959 01/02/2012
		return(true);
	}
	
	
	public void DeleteBOM() throws Exception {
		
		String SQL="DELETE FROM uy_simpleForecastbOM WHERE uy_simpleforecastline_id IN(SELECT uy_simpleforecastline_id FROM uy_simpleforecastline WHERE uy_simpleforecast_id=?)";
		PreparedStatement pstmt=null;
		
		try {
			pstmt=DB.prepareStatement (SQL, null);
			pstmt.setInt(1,this.getUY_SimpleForecast_ID());
			
			// Just run de query, 
			pstmt.executeUpdate();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			pstmt= null;
		}
	}

	public void DeleteLines() throws Exception {
		
		String SQL="DELETE FROM uy_simpleForecastline WHERE uy_simpleforecast_id=?";
		PreparedStatement pstmt=null;
		
		try {
			pstmt=DB.prepareStatement (SQL, null);
			pstmt.setInt(1,this.getUY_SimpleForecast_ID());
			
			// Just run de query, 
			pstmt.executeUpdate();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			pstmt= null;
		}
	}


	/**
	 * 	Get Lines
	 *	@return lines
	 */
	public MSimpleForecastLine[] getLines()  throws Exception {		
		
		String SQL="SELECT * FROM UY_SimpleForecastLine WHERE UY_SimpleForecast_ID=?";
		ResultSet rs=null;
		PreparedStatement pstmt=null;

		List<MSimpleForecastLine> list=new ArrayList<MSimpleForecastLine>();
		
		try {
			pstmt=DB.prepareStatement (SQL, null);
			pstmt.setInt(1,this.getUY_SimpleForecast_ID());
			
			rs=pstmt.executeQuery();
			while (rs.next()) {
				MSimpleForecastLine value=new MSimpleForecastLine(Env.getCtx(),rs,null);
				value.forecast = this; //OpenUp Nicolas Sarlabos #959 01/02/2012
				list.add(value);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			DB.close(rs,pstmt);
			rs= null; 
			pstmt= null;
		}
		
		return list.toArray(new MSimpleForecastLine[list.size()]);		
	}
}
