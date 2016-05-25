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
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class PTTPrintCardCarrier extends SvrProcess{

	private int boxID = 0;
	private String format = "";
	
	/**
	 * 
	 */
	public PTTPrintCardCarrier() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){					
						
				if (name.equalsIgnoreCase("UY_TT_Box_ID")){
					if (para[i].getParameter()!=null){
						this.boxID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}	
				
				if (name.equalsIgnoreCase("FormatType")){
					if (para[i].getParameter()!=null){
						this.format = ((String)para[i].getParameter());
					}
				}
		
			}
		}
		
	}

	@Override
	protected String doIt() throws Exception {
	
		String message = "Proceso finalizado OK", sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			int adProcessID = this.getPrintProcessID();
			if (adProcessID <= 0){
				throw new Exception("No se pudo obtener ID del proceso de Impresion de Card Carrier.");
			}
			
			sql = "select uy_tt_card_id from uy_tt_card where uy_tt_box_id = " + this.boxID + " order by locatorvalue";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
				instance.saveEx();
				
				ProcessInfo pi = new ProcessInfo ("CardCarrier", adProcessID);
				pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
				
				MPInstancePara para = new MPInstancePara(instance, 10);
				para.setParameter("UY_TT_Card_ID", new BigDecimal(rs.getInt("uy_tt_card_id")));
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
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = 0;
		String name = "";
		
		try{
			
			if(this.format.equalsIgnoreCase("A")){
				name = "uy_rtt_masivocardcarriera4";
			} else name = "uy_rtt_masivocardcarriercarta";
						
			sql = " select ad_process_id " +
				  " from ad_process " +
				  " where lower(name)='" + name + "'";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			if (rs.next()){
				value = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;

	}
	

}
