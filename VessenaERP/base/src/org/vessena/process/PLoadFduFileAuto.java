/**
 * 
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
 * @author gbrust
 * 
 */
public class PLoadFduFileAuto extends SvrProcess {
	
	private int uyFduFileID = 0;
	

	/**
	 * 
	 */
	public PLoadFduFileAuto() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_FduFile_ID")){
					this.uyFduFileID = ((BigDecimal)para[i].getParameter()).intValueExact();				
				}				
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		int adProcessID = MProcess.getProcess_ID("PLoadFduFile", null);

		MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
		instance.saveEx();

		ProcessInfo pi = new ProcessInfo("CargaAutomatica", adProcessID);
		pi.setAD_PInstance_ID(instance.getAD_PInstance_ID());

		MPInstancePara para = new MPInstancePara(instance, 10);
		para.setParameter("UY_FduFile_ID", new BigDecimal(this.uyFduFileID));
		para.saveEx();

		para = new MPInstancePara(instance, 20);
		para.setParameter("AD_User_ID", new BigDecimal(this.getAD_User_ID()));
		para.saveEx();
		
		para = new MPInstancePara(instance, 20);
		para.setParameter("isManual", 'Y');
		para.saveEx();	

		ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
		worker.start();

		return "OK";
	}

}
