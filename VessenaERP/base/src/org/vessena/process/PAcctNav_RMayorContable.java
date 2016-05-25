/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 23/11/2012
 */
package org.openup.process;

import java.math.BigDecimal;

import org.compiere.apps.ProcessCtl;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

/**
 * org.openup.process - PAcctNav_RMayorContable
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 23/11/2012
 * @see
 */
public class PAcctNav_RMayorContable extends SvrProcess {

	private int uyAcctNavID = 0;
	
	/**
	 * Constructor.
	 */
	public PAcctNav_RMayorContable() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 23/11/2012
	 * @see
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_AcctNav_ID"))
					this.uyAcctNavID = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 23/11/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		// Disparo proceso de Reporte de Mayor Contable RV
		// Le paso los parametros que necesita via código
		// ID del proceso de Reporte de Mayor Contable RV
		int adProcessID = MProcess.getProcess_ID("UY_RCtb_MayorContableRV", null); 

		MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
		instance.saveEx();

		ProcessInfo pi = new ProcessInfo ("MayorContableRV", adProcessID);
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
		
		MPInstancePara para = new MPInstancePara(instance, 10);
		para.setParameter("UY_AcctNav_ID", new BigDecimal(this.uyAcctNavID));
		para.saveEx();
		
		para = new MPInstancePara(instance, 20);
		para.setParameter("AD_User_ID", new BigDecimal(this.getAD_User_ID()));
		para.saveEx();

		para = new MPInstancePara(instance, 30);
		para.setParameter("AD_Client_ID", new BigDecimal(this.getAD_Client_ID()));
		para.saveEx();

		para = new MPInstancePara(instance, 40);
		para.setParameter("AD_Org_ID", new BigDecimal(Env.getAD_Org_ID(getCtx())));
		para.saveEx();

	
		ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
		worker.start();     
		
		return "OK";
	}

}
