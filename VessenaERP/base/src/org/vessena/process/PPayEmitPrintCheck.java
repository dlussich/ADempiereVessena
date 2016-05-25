/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/04/2013
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MPayEmit;


/**
 * org.openup.process - PPayEmitPrintCheck
 * OpenUp Ltda. Issue #678 
 * Description: Proceso para impresion masiva de cheques emitidos en una transaccion. 
 * @author Gabriel Vila - 12/04/2013
 * @see
 */
public class PPayEmitPrintCheck extends SvrProcess {

	MPayEmit payemit = null;
	
	/**
	 * Constructor.
	 */
	public PPayEmitPrintCheck() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/04/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		try{
			this.payemit = new MPayEmit(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 12/04/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		this.payemit.printChecks();
		
		return "OK";
		
	}

}
