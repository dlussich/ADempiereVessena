package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MCierreTransporteDoc;
import org.openup.model.MCierreTransporteHdr;

//org.openup.process.PCierreATRVincularCobranza
public class PCierreATRGetCobranzas extends SvrProcess  {

	MCierreTransporteHdr cierreTransporteHdr =null;
	@Override
	protected String doIt() throws Exception {
		
		cierreTransporteHdr=new MCierreTransporteHdr(getCtx(),getRecord_ID(), null);
		
		return getDevolucionesDirectas();
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}
	
	public String getDevolucionesDirectas() throws Exception{
		borrarDatos();
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		
		
		try{
			//Devoluciones directas
						

		  	sql =" SELECT c_doctype_id, documentno, datetrx, c_bpartner_id " + 
 		  	" FROM C_Payment " + 
		  	" WHERE uy_asignatransportehdr_id =? " +
		  	" AND DocStatus IN('CO','CL') AND c_doctype_id =1000008";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, cierreTransporteHdr.getUY_AsignaTransporteHdr_ID());
		
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MCierreTransporteDoc doc = new MCierreTransporteDoc(getCtx(), 0, null);
				doc.setC_BPartner_ID( rs.getInt("c_bpartner_id"));
				doc.setC_DocType_ID(rs.getInt("c_doctype_id"));
				doc.setDateTrx(rs.getTimestamp("datetrx"));
				doc.setDocumentNo(rs.getString("documentno"));
				doc.setUY_CierreTransporteHdr_ID(cierreTransporteHdr.getUY_CierreTransporteHdr_ID());
				doc.saveEx();
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
		
		return "OK";		
	}
	
	public String borrarDatos() throws Exception{
		
		String sql = "";
		
		PreparedStatement pstmt = null;

		
		
		try{
			//Devoluciones directas
			
			
			sql =" DELETE FROM uy_cierretransportedoc  " +
			" WHERE uy_cierretransportehdr_id =?" + 
		  	" AND c_doctype_id =1000008";	 //TODO parametrizar 				
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, cierreTransporteHdr.getUY_CierreTransporteHdr_ID());
			
		
			
			pstmt.execute();
		
					}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(pstmt);
			
		}
		
		return "OK";		
	}
}
