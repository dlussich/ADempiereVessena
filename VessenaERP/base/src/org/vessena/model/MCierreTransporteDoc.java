/**
 * MCierreTransporteDoc.java
 * 15/02/2011
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MDocType;
import org.compiere.model.MInOut;

import org.compiere.util.DB;


/**
 * OpenUp.
 * MCierreTransporteDoc
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 15/02/2011
 */
public class MCierreTransporteDoc extends X_UY_CierreTransporteDoc {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8856600121281689738L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_CierreTransporteDoc_ID
	 * @param trxName
	 */
	public MCierreTransporteDoc(Properties ctx, int UY_CierreTransporteDoc_ID,
			String trxName) {
		super(ctx, UY_CierreTransporteDoc_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCierreTransporteDoc(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Devuelve la Devolucion Directa asociada
	 * @return
	 * @author  Nicolas Garcia 
	 * Fecha : 04/05/2011
	 */
	public MInOut   getDevolucionDirecta() {
		
		MDocType docType= new MDocType(getCtx(),this.getC_DocType_ID(),null);
		
		if (docType.getDocBaseType().equals("MMR")&& docType.getC_DocType_ID()==this.getC_DocType_ID()){//Defendive
			
			String sql = "";
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			try{
						//Trae la devolucion directa 
				sql ="SELECT m_inout_id FROM m_inout WHERE documentno in" +
					"(SELECT documentno FROM uy_cierretransportedoc WHERE uy_cierretransportedoc_id=? AND c_doctype_id=?)";
						//FIXME: En este SQL fata contemplar si fue o no la devolucion directa 
				pstmt = DB.prepareStatement (sql, null);
				pstmt.setInt(1, this.getUY_CierreTransporteDoc_ID());
				pstmt.setInt(2, this.getC_DocType_ID());
				
				rs = pstmt.executeQuery ();
			
				if (rs.next()){
					return new MInOut(getCtx(), rs.getInt("m_inout_id"), null);
					
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
		}
		return null;	
	}

}
