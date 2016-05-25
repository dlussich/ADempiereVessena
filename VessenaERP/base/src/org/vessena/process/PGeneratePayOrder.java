/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 30/04/2013
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MPayOrderGen;

/**
 * org.openup.process - PGeneratePayOrder
 * OpenUp Ltda. Issue #348 
 * Description: Proceso que dispara la generacion de ordenes de pago segun 
 * seleccion seleccion manual de factura y datos de generacion.
 * @author Gabriel Vila - 30/04/2013
 * @see
 */
public class PGeneratePayOrder extends SvrProcess {

	MPayOrderGen ordergen = null;
	
	/**
	 * Constructor.
	 */
	public PGeneratePayOrder() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 30/04/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		try{
			this.ordergen = new MPayOrderGen(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 30/04/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		this.ordergen.generatePayOrders();
		
		return "OK";
	}

}
