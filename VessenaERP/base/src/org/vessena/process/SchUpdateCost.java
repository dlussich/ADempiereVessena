/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 13/06/2012
 */
 
package org.openup.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.MProductCost;

/**
 * org.openup.process - SchUpdateCost
 * OpenUp Ltda. Issue #1031  
 * Description: Proceso scheduler para actualizacion de costos de productos. 
 * @author Gabriel Vila - 13/06/2012
 * @see
 */
public class SchUpdateCost extends SvrProcess {

	private int mProductID = 0; 
	private boolean usarTCFechaActual = false;
	
	/**
	 * Constructor.
	 */
	public SchUpdateCost() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue #1031 
	 * @author Hp - 13/06/2012
	 * @see
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() != null){
				
				if (name.equals("M_Product_ID"))
					this.mProductID = para[i].getParameterAsInt();
				
				else if (name.equalsIgnoreCase("UY_UsarFechaActual")){
					this.usarTCFechaActual = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue #1031 
	 * @author Hp - 13/06/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		MProductCost.calculateCosts(this.mProductID, this.usarTCFechaActual);

		return "@OK@";
	}

}