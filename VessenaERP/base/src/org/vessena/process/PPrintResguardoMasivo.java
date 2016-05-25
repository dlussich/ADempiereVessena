/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.process.ProcessInfo;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolás
 *
 */
public class PPrintResguardoMasivo extends SvrProcess {

	private int hdrID = 0;
	
	/**
	 * 
	 */
	public PPrintResguardoMasivo() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		this.hdrID = this.getRecord_ID();

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "", message = "Proceso finalizado OK";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try{
			
			int adProcessID = this.getPrintProcessID();
			if (adProcessID <= 0){
				throw new Exception("No se pudo obtener ID del proceso de Impresión Masivo de Resguardos.");
			}

			sql = "SELECT UY_Resguardo_ID FROM UY_Resguardo WHERE UY_ResguardoGen_ID = "+ this.hdrID + " " +
					" ORDER BY DocumentNo asc";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				message = "Proceso finalizado OK";
				MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
				instance.saveEx();
				
				ProcessInfo pi = new ProcessInfo ("ImpresionResguardoMasivo", adProcessID);
				pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
				
				MPInstancePara para = new MPInstancePara(instance, 10);
				para.setParameter("UY_Resguardo_ID", new BigDecimal(rs.getInt("UY_Resguardo_ID")));
				para.saveEx();
				
				ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
				worker.start();     
				
				// Pausa de 5 segundos preventiva para impresion masiva
				java.lang.Thread.sleep(5000);			
				
			}
			
			return message;
			
		} catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}				
	}
	
	private int getPrintProcessID() {
		
		String sql = "";
		int value = 0;

		sql = " select ad_process_id " +
				" from ad_process " +
				" where lower(value)='uy_rctacteresguardo'";
		
		value = DB.getSQLValueEx(get_TrxName(), sql);
		
		return value;
	}

}
