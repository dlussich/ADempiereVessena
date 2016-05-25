/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 23/10/2012
 */
package org.openup.process;

import java.math.BigDecimal;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MConfirmorderhdr;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;


/**
 * org.openup.process - PVoidConfirmOrder
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 23/10/2012
 * @see
 */
public class PVoidConfirmOrder extends SvrProcess {

	int uyConfirmOrderHdrID =  0;
	
	/**
	 * Constructor.
	 */
	public PVoidConfirmOrder() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 23/10/2012
	 * @see
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_Confirmorderhdr_ID")){
					this.uyConfirmOrderHdrID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 23/10/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		MConfirmorderhdr hdr = new MConfirmorderhdr(getCtx(), this.uyConfirmOrderHdrID, get_TrxName());

		if (!hdr.processIt(DocumentEngine.ACTION_Void)){
			throw new AdempiereException(hdr.getProcessMsg());
		}
		
		hdr.saveEx();
		
		return "OK";
	}

}
