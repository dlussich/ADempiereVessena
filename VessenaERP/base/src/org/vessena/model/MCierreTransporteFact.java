/**
 * MCierreTransporteFact.java
 * 14/02/2011
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MInvoice;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MCierreTransporteFact
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 14/02/2011
 */
public class MCierreTransporteFact extends X_UY_CierreTransporteFact {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7137432646042492059L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_CierreTransporteFact_ID
	 * @param trxName
	 */
	public MCierreTransporteFact(Properties ctx,
			int UY_CierreTransporteFact_ID, String trxName) {
		super(ctx, UY_CierreTransporteFact_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCierreTransporteFact(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Trae la factura asociada
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 02/05/2011
	 */
	public MInvoice  getFactura() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		MInvoice fact=null;
		
		
		try{
			sql ="SELECT c_invoice_id FROM uy_cierretransportefact " +
					"WHERE uy_cierretransportefact_id =?";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_CierreTransporteFact_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				fact= new MInvoice(Env.getCtx(), rs.getInt("c_invoice_id"), null);
				
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return fact;		
	}

}
