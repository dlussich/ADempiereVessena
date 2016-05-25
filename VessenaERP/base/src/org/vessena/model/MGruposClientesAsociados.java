/**
 * MGruposClientesAsociados.java
 * 17/12/2010
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MGruposClientesAsociados
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 17/12/2010
 */
public class MGruposClientesAsociados extends X_UY_GruposClientesAsociados {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6900353354670529091L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_GruposClientesAsociados_ID
	 * @param trxName
	 */
	public MGruposClientesAsociados(Properties ctx,
			int UY_GruposClientesAsociados_ID, String trxName) {
		super(ctx, UY_GruposClientesAsociados_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGruposClientesAsociados(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @param cBPartnerID
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 17/12/2010
	 */
	public static MGruposClientes[] getGruposCliente(int cBPartnerID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MGruposClientes> grupoClientes = new ArrayList<MGruposClientes>();
		
		try{
			sql ="SELECT uy_gruposclientes_id " + 
 		  	" FROM " + X_UY_GruposClientesAsociados.Table_Name + 
		  	" WHERE c_bpartner_id =?"; 
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setLong(1, cBPartnerID);
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MGruposClientes grupoCliente = new MGruposClientes(Env.getCtx(), rs.getInt(1), null);
				grupoClientes.add(grupoCliente);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return grupoClientes.toArray(new MGruposClientes[grupoClientes.size()]);		
	}
	
}
