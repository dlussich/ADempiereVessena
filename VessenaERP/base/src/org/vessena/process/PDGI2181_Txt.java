/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/08/2013
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MDGIF2181;

/**
 * org.openup.process - PDGI2181_Txt
 * OpenUp Ltda. Issue # 
 * Description: Proceso de generacion de archivo plano para DGI correspondiente 
 * @author Gabriel Vila - 20/08/2013
 * @see
 */
public class PDGI2181_Txt extends SvrProcess {

	private MDGIF2181 dgi = null;
	
	/**
	 * Constructor.
	 */
	public PDGI2181_Txt() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 20/08/2013
	 * @see
	 */
	@Override
	protected void prepare() {
		
		try {
			
			this.dgi = new MDGIF2181(getCtx(), this.getRecord_ID(), get_TrxName());	

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 20/08/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		try{
			this.dgi.generateTXT();
		}
		catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}

		return "OK";

	}

}
