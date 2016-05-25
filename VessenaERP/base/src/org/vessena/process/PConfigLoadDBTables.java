/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 11/11/2013
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MDBConfig;


/**
 * org.openup.process - PConfigLoadDBTables
 * OpenUp Ltda. Issue #1539 
 * Description: Proceso para carga de informacion de tablas definidas en base de datos de adempiere.
 * @author Gabriel Vila - 11/11/2013
 * @see
 */
public class PConfigLoadDBTables extends SvrProcess {

	MDBConfig config = null; 
	
	/**
	 * Constructor.
	 */
	public PConfigLoadDBTables() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 11/11/2013
	 * @see
	 */
	@Override
	protected void prepare() {
		try {
			config = new MDBConfig(getCtx(), this.getRecord_ID(), null);
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 11/11/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		config.loadDBTables();
		
		return "OK";
	}

}
