/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MUser;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MTTCard;
import org.openup.model.MTTChequeraLine;
import org.openup.util.ItalcredSystem;

/**
 * @author Nicolas
 *
 */
public class PTTPrintCheckBook extends SvrProcess {

	private int boxID = 0;
	/**
	 * 
	 */
	public PTTPrintCheckBook() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
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
		
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
				
		String message = "Proceso finalizado OK", sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			int adProcessID = this.getPrintProcessID();
			if (adProcessID <= 0){
				throw new Exception("No se pudo obtener ID del proceso de Impresion de Chequeras.");
			}
			
			sql = "select uy_tt_card_id from uy_tt_card where uy_tt_box_id = " + this.boxID + " and uy_tt_chequeraline_id is not null order by locatorvalue";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			ItalcredSystem system = new ItalcredSystem();
			
			while(rs.next()){
				
				MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
				instance.saveEx();
				
				ProcessInfo pi = new ProcessInfo ("Chequera", adProcessID);
				pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
				
				MPInstancePara para = new MPInstancePara(instance, 10);
				para.setParameter("UY_TT_Card_ID", new BigDecimal(rs.getInt("uy_tt_card_id")));
				para.saveEx();
				
				ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
				worker.start();     
				
				// Pausa de 5 segundos preventiva para impresion masiva
				java.lang.Thread.sleep(5000);	
				
				//OpenUp. Guillermo Brust. 21/10/2013. ISSUE #1435
				//Se debe generar una linea en el legajo del titular de la MTTCard
				MTTCard card = new MTTCard(this.getCtx(), rs.getInt("UY_TT_Card_ID"), null);
				
				//Clase necesaria				
				MTTChequeraLine chequeraLine = new MTTChequeraLine(this.getCtx(), card.getUY_TT_ChequeraLine_ID(), null);								
				
				if(chequeraLine.get_ID() > 0){
					//Datos a guardar
					String usuario = new MUser(this.getCtx(), this.getAD_User_ID(), null).getName();
					String cedula = card.getCedula();
														
					String observacion = "Chequera Clin Cash 6,9 y 12 Cuotas – $" + chequeraLine.getAmount() + " .Válido hasta " + df.format(chequeraLine.getDueDate()) + ". " + chequeraLine.getName();
					
					String familiaLegajo = "72";
					Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());					
					
					//Guardo la linea en el legajo					
					system.newLegajo(usuario, cedula, observacion, familiaLegajo, fechaCreacion);
					card.setIsLegajoCheckSaved(true);
					card.saveEx();
				}
						
				
				//Fin OpenUp.
				
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
		
		try{
			sql = " select ad_process_id " +
				  " from ad_process " +
				  " where lower(value)='uy_rtt_impresionchequera'";
			
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
