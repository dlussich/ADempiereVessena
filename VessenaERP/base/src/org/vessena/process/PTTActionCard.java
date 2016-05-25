/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 08/10/2013
 */
package org.openup.process;

import java.util.ArrayList;
import java.util.List;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.grid.GridController;
import org.compiere.model.GridTab;
import org.compiere.process.SvrProcess;
import org.openup.model.MTTActionCard;
import org.openup.model.MTTCard;

/**
 * org.openup.process - PTTActionCard
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 08/10/2013
 * @see
 */
public class PTTActionCard extends SvrProcess {

	MTTActionCard ac = null;
	
	/**
	 * Constructor.
	 */
	public PTTActionCard() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 08/10/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		try{
			this.ac = new MTTActionCard(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 08/10/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		List<MTTCard> cards = new ArrayList<MTTCard>();
		
		GridTab tab = getProcessInfo().getWindow().getAPanel().getGridTab(0);
		GridController gc = tab.getGridController();
		int[] selectedRows = gc.getSelection();

		for(int i = 0; i < selectedRows.length; i++)
		{
			cards.add(new MTTCard(getCtx(), tab.getKeyID(selectedRows[i]), null));
		}
			
		// Ejecuto Accion
		String result = ac.execute(cards);
		
		// Si algo fallo aviso
		if (result != null){
			throw new AdempiereException(result);
		}
		
		return "OK";
	}

}
