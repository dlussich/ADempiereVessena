/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 02/03/2013
 */
package org.openup.process;

import java.math.BigDecimal;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.model.MDocType;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.I_UY_R_Inbox;
import org.openup.model.MRInbox;

/**
 * org.openup.process - POpenWindowSwing
 * OpenUp Ltda. Issue #116 
 * Description: Abre ventana para un determinada tabla-registro-documento.
 * @author Gabriel Vila - 02/03/2013
 * @see
 */
public class POpenWindowSwing extends SvrProcess {

	private int adTableID = 0;
	private int recordID = 0;
	private int cDocTypeID = 0;
	
	/**
	 * Constructor.
	 */
	public POpenWindowSwing() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 02/03/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("AD_Table_ID")){
					this.adTableID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("Record_ID")){
					this.recordID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("C_DocType_ID")){
					if (para[i].getParameter() != null){
						this.cDocTypeID = ((BigDecimal)para[i].getParameter()).intValueExact();	
					}
				}
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 02/03/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		try{

			// Ejecuto acciones extra segun desde que tabla se instancio el proceso
			this.executeOtherActions();
			
			// Instancio modelo de documento en caso de tenerlo
			MDocType doc = new MDocType(getCtx(), this.cDocTypeID, null);
			
			// Instancio modelo de tabla
			MTable table = new MTable(getCtx(), this.adTableID, null);
			
			String whereClause = table.getTableName() + "_ID =" + this.recordID;
			AWindow poFrame = new AWindow(null);
			MQuery query = new MQuery(table.getTableName());
			query.addRestriction(whereClause);
			
			int adWindowID = table.getAD_Window_ID();
			
			if (doc.get_ID() > 0){
				if (!doc.isSOTrx()) adWindowID = table.getPO_Window_ID();
				if (doc.getAD_Window_ID() > 0) adWindowID = doc.getAD_Window_ID();
			}
			
			boolean ok = poFrame.initWindow(adWindowID, query);
			if (ok){
				poFrame.pack(); 
				AEnv.showCenterScreen(poFrame);
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
		return "OK";
		
	}

	/***
	 * Segun desde donde que tabla se ejecuta este proceso pueden ejecutarse otras
	 * acciones.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 19/03/2013
	 * @see
	 */
	private void executeOtherActions() {

		try{
			
			// Para Bandeja de Entrada
			if (this.getTable_ID() == I_UY_R_Inbox.Table_ID){
				MRInbox inbox = new MRInbox(getCtx(), this.getRecord_ID(), get_TrxName());
				if (inbox.get_ID() > 0){
					inbox.actionTaken();
				}
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

}
