/**
 * 
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MRePrintCheck;

/**
 * org.openup.process - PPayEmitPrintCheck
 * OpenUp Ltda. Issue #932
 * Description: Proceso para reimpresion masiva de cheques emitidos en una transaccion. 
 * @author Nicolas Sarlabos - 05/06/2013
 * @see
 */
public class PRePrintCheck extends SvrProcess {
	
	MRePrintCheck reprint = null;

	/**
	 * 
	 */
	public PRePrintCheck() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		try{
			this.reprint = new MRePrintCheck(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	@Override
	protected String doIt() throws Exception {

		this.reprint.printChecks();

		return "OK";
	}

}
