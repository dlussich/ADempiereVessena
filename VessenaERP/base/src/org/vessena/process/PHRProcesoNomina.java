package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.MHRProcesoNomina;

public class PHRProcesoNomina extends SvrProcess {

	private int recordID = 0;
	private boolean reProcessing = false;
	
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

	@Override
	protected String doIt() throws Exception {
		
		String mensajeRetorno = "Proceso finalizado.";
		
		try{
			MHRProcesoNomina model = new MHRProcesoNomina(getCtx(), this.recordID, null);

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
