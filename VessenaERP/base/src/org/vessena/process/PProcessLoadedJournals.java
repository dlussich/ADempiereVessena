/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/11/2012
 */
package org.openup.process;

import java.math.BigDecimal;
import java.util.List;

import org.compiere.model.MJournal;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.MLoadCierreCtaCte;

/**
 * org.openup.process - PProcessLoadedJournals
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 15/11/2012
 * @see
 */
public class PProcessLoadedJournals extends SvrProcess {

	private int uyLoadCierreCtaCteID = 0;
	private String masiveAction = "";
	
	/**
	 * Constructor.
	 */
	public PProcessLoadedJournals() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 15/11/2012
	 * @see
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_LoadCierreCtaCte_ID"))
					this.uyLoadCierreCtaCteID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("MasiveAction"))
					this.masiveAction = (String)para[i].getParameter(); 
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 15/11/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		// Obtengo modelo de carga masiva
		MLoadCierreCtaCte model = new MLoadCierreCtaCte(getCtx(), this.uyLoadCierreCtaCteID, get_TrxName());
		
		if (model.get_ID() <= 0)
			throw new Exception("No se pudo obtener modelo de carga masiva para id recibido.");
		
		// Obtengo lista de asientos generados en esta carga segun opcion de accion masiva
		String docStatus = "CO";
		if ((this.masiveAction.equalsIgnoreCase("DR")) || (this.masiveAction.equalsIgnoreCase("CO"))){
			docStatus = "DR";
		}
		
		List<MJournal> journals = model.getJournalsByStatus(docStatus, get_TrxName());
		
		for (MJournal journal: journals){
			
			if (this.masiveAction.equalsIgnoreCase("DR")){
				journal.deleteEx(true);
			}
			else if (this.masiveAction.equalsIgnoreCase("CO")){
				if (!journal.processIt(DocumentEngine.ACTION_Complete)){
					String msg = "Error al completar Asiento : " + journal.getDocumentNo(); 
					if ((journal.getProcessMsg() != null) && (!journal.getProcessMsg().equalsIgnoreCase(""))){
						msg = journal.getProcessMsg() + "\n" + " Documento : " + journal.getDocumentNo();
					}
					throw new Exception(msg);
				}
			}
			else if (this.masiveAction.equalsIgnoreCase("VO")){
				if (!journal.processIt(DocumentEngine.ACTION_Void)){
					String msg = "Error al anular Asiento : " + journal.getDocumentNo(); 
					if ((journal.getProcessMsg() != null) && (!journal.getProcessMsg().equalsIgnoreCase(""))){
						msg = journal.getProcessMsg() + "\n" + " Documento : " + journal.getDocumentNo();
					}
					throw new Exception(msg);
				}				
			}
			
			
		}
		
		return "OK";	
	}

}
