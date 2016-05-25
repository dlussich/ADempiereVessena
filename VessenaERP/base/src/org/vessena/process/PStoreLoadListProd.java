/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Dec 21, 2015
*/
package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MStoreLoadOrder;
import org.openup.model.MStoreLoadOrderList;
import org.openup.model.MStoreStkList;

/**
 * org.openup.process - PStoreLoadListProd
 * OpenUp Ltda. Issue #5150 
 * Description: Proceso para carga de lista de productos sugeridos en orden de carga de tienda.
 * @author gabriel - Dec 21, 2015
*/
public class PStoreLoadListProd extends SvrProcess {

	MStoreLoadOrderList model = null;
	
	/***
	 * Constructor.
	*/

	public PStoreLoadListProd() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		this.model = new MStoreLoadOrderList(getCtx(), this.getRecord_ID(), get_TrxName());
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		try {
			
			MStoreLoadOrder header = (MStoreLoadOrder)this.model.getUY_StoreLoadOrder();
			MStoreStkList stkList = (MStoreStkList)this.model.getUY_StoreStkList();
			
			String message = header.loadProductList(stkList);
			
			if (message != null){
				throw new AdempiereException(message);
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return "OK";
		
	}

}
