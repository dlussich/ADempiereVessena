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
import org.compiere.model.MProcess;
import org.compiere.model.MUser;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MTTCard;
import org.openup.model.MTTPrintValeLine;
import org.openup.util.ItalcredSystem;

public class PTTPrintVale extends SvrProcess {

	private int boxID = 0;
	
	public PTTPrintVale() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
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

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		String message = "Proceso finalizado OK - No hay cuentas en la caja", sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try{
			
			int adProcessID = this.getPrintProcessID();
			if (adProcessID <= 0){
				throw new Exception("No se pudo obtener ID del proceso de Impresión de Vales.");
			}
			
//			sql = "SELECT UY_TT_Card_ID FROM UY_TT_Card " +
//					"WHERE UY_TT_Box_ID = " + this.boxID + " " +
//							"AND UY_TT_PrintValeLine_ID IS NOT NULL" +
//							" ORDER BY LocatorValue";
			sql = "SELECT UY_TT_Card_ID FROM UY_TT_BoxCard WHERE UY_TT_Box_ID = "+ this.boxID + " " +
					" ORDER BY LocatorValue desc";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();
			
//			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//			ItalcredSystem system = new ItalcredSystem();
			
			while(rs.next()){
				
				//Se debe generar una linea en el legajo del titular de la MTTCard
				MTTCard card = new MTTCard(this.getCtx(), rs.getInt("UY_TT_Card_ID"), null);
				
				
				message = "Proceso finalizado OK";
				MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
				instance.saveEx();
				
				ProcessInfo pi = new ProcessInfo ("ImpresionValeMasivo", adProcessID);
				pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
				
				MPInstancePara para = new MPInstancePara(instance, 10);
				para.setParameter("UY_TT_Card_ID", new BigDecimal(rs.getInt("uy_tt_card_id")));
				para.saveEx();
				
				ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
				worker.start();     
				
				// Pausa de 5 segundos preventiva para impresion masiva
				java.lang.Thread.sleep(5000);
				
				//INI OpenUp Sylvie Bouissa 04/03/2015 Issue# 3624
				//Además de imprimir el vale para la cuenta se imprime la caratula de solicitud para la misma.(LAS DOS IMPRESIONES A LA VEZ)
				
				int csprocessID = MProcess.getProcess_ID("10000014", null);
				MPInstance instance2 = new MPInstance(Env.getCtx(), csprocessID, 0);
				instance2.saveEx();

				ProcessInfo pi2 = new ProcessInfo ("CardCarrier", csprocessID);
				pi2.setAD_PInstance_ID (instance2.getAD_PInstance_ID());
				
				MPInstancePara para2 = new MPInstancePara(instance2, 10);
				para2.setParameter("UY_TT_Card_ID", new BigDecimal(card.get_ID()));
				para2.saveEx();
				
				ProcessCtl worker2 = new ProcessCtl(null, 0, pi2, null);
				worker2.start();
				
				// Pausa de 5 segundos preventiva para impresion masiva
				java.lang.Thread.sleep(5000);	
				
				//FIN OpenUp Sylvie Bouissa 04/03/2015 Issue# 3624

				//Clase necesaria
				MTTPrintValeLine valeLine = new MTTPrintValeLine(getCtx(), card.getUY_TT_PrintValeLine_ID(), null);								
				
				if(valeLine.get_ID() > 0){
					//Datos a guardar
					String usuario = new MUser(this.getCtx(), this.getAD_User_ID(), null).getName();
					String cedula = card.getCedula();
						
					//VER QUE ES LO QUE HAY QUE GUARDAR EN FINANCIAL
					//String observacion = "IMPRESION VALE");
					
					//String familiaLegajo = "A61"; 
					Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());					
					
					//Guardo la linea en el legajo	--> No se guarda en legajo solo se guarda cuando se firma el vale			
					//system.newLegajo(usuario, cedula, observacion, familiaLegajo, fechaCreacion);
					//card.setIsValeSigned(true); no se guarda como vale firmadoya que solo se imprime el mismo
					//card.saveEx();
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
		// TODO Auto-generated method stub
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = 0;
		
		try{
			sql = " select ad_process_id " +
				  " from ad_process " +
				  " where lower(value)='10000015'";
			
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
