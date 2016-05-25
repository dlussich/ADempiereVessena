/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 22/06/2012
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
 * org.openup.process - RHRReciboSueldo
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 22/06/2012
 * @see
 */
public class RHRReciboSueldo extends SvrProcess {

	private int uyHRProcesoNominaID = 0;
	
	/**
	 * Constructor.
	 */
	public RHRReciboSueldo() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 22/06/2012
	 * @see
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_HRProcesoNomina_ID")){
					if (para[i].getParameter() != null) this.uyHRProcesoNominaID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue #986 
	 * @author Hp - 22/06/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			int adProcessID = this.getPrintRecibosProcessID();
			if (adProcessID <= 0){
				throw new Exception("No se pudo obtener ID del proceso de Impresion de Recibos de Sueldo.");
			}
			
			// Obtiene empleados a considerar en la impresion de recibos de sueldo, 
			// segun un proceso de liquidacion recibido por parametro del proceso.
			sql = " select distinct c_bpartner_id " +
				  " from uy_hrresult " +
				  " where uy_hrprocesonomina_id =? " +
				  " and success ='Y' " +
				  " and imprimerecibos='Y'";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.uyHRProcesoNominaID);
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){

					MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
					instance.saveEx();

					ProcessInfo pi = new ProcessInfo ("ReciboSueldo", adProcessID);
					pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
					
					MPInstancePara para = new MPInstancePara(instance, 10);
					para.setParameter("UY_HRProcesoNomina_ID", new BigDecimal(this.uyHRProcesoNominaID));
					para.saveEx();
					
					para = new MPInstancePara(instance, 20);
					para.setParameter("C_BPartner_ID", new BigDecimal(rs.getInt("c_bpartner_id")));
					para.saveEx();
					
					ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
					worker.start();     
					
					// Pausa de 2 segundos preventiva para impresion masiva
					java.lang.Thread.sleep(2000);
					
			}
			
			return "OK";
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
	}

	/***
	 * Obtiene y retorna id del proceso de impresion de recibos de sueldo.
	 * OpenUp Ltda. Issue #986 
	 * @author Gabriel Vila - 22/06/2012
	 * @see
	 * @return
	 */
	private int getPrintRecibosProcessID() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = 0;
		
		try{
			sql = " select ad_process_id " +
				  " from ad_process " +
				  " where lower(value)='uy_phr_impresionrecibos'";
			
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
