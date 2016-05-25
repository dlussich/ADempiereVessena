/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/01/2013
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.MFDUControlProcess;

/**
 * org.openup.process - PFDUProcessControl
 * OpenUp Ltda. Issue #133 
 * Description: Proceso de ejecucion de controles de generacion de cuenta corriente.
 * @author Gabriel Vila - 15/01/2013
 * @see
 */
public class PFDUProcessControl extends SvrProcess {

	private int recordID = 0;
	private boolean reProcessing = false;
	
	/**
	 * Constructor.
	 */
	public PFDUProcessControl() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 15/01/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		// Guardo ID del registro a considerar
		recordID = getRecord_ID();
		
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				
				if (name.equalsIgnoreCase("ReProcessing")){
					this.reProcessing = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				}
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 15/01/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Proceso finalizado.";
		
		try{
			MFDUControlProcess model = new MFDUControlProcess(getCtx(), this.recordID, null);

			if (model.get_ID() <= 0) 
				return "No se pudo obtener modelo.";
			
			model.setWindow(this.getProcessInfo().getWindow());
			model.execute(this.reProcessing);
			mensajeRetorno = model.getProcessMsg();
			model.setDocStatus(DocumentEngine.STATUS_InProgress);
			model.saveEx();
						

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		}
		
		return mensajeRetorno;
		
	}

}
