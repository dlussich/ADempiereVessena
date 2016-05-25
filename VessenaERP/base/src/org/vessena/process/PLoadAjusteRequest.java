/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 09/04/2013
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MRAjusteAction;

/**
 * org.openup.process - PLoadAjusteRequest
 * OpenUp Ltda. Issue #657 
 * Description: Carga lineas de solicitudes de ajuste
 * @author Gabriel Vila - 09/04/2013
 * @see
 */
public class PLoadAjusteRequest extends SvrProcess {

	MRAjusteAction ajuste = null;
	
	public PLoadAjusteRequest() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 */
	@Override
	protected void prepare() {
		
		try{
			this.ajuste = new MRAjusteAction(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		try{
		
			boolean hayInfo = this.ajuste.loadRequests();
			
			if (!hayInfo){
				throw new AdempiereException("No se obtuvieron solicitudes de ajuste validas para este Usuario Autorizador.");
			}
			
		}
		catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}

		return "OK";
		
	}

}
