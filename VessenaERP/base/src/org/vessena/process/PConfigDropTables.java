/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 11/11/2013
 */
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MDBConfig;

/**
 * org.openup.process - PConfigDropTables
 * OpenUp Ltda. Issue #1539 
 * Description: Proceso que elimina tablas tanto del diccionario como de la base de datos. 
 * @author Gabriel Vila - 11/11/2013
 * @see
 */
public class PConfigDropTables extends SvrProcess {

	MDBConfig config = null;
	
	/**
	 * Constructor.
	 */
	public PConfigDropTables() {
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

		config.dropSelectedTables();
		
		return "OK";

	}

}
