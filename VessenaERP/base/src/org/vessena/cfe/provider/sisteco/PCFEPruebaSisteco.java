package org.openup.cfe.provider.sisteco;

import org.compiere.model.MInvoice;
import org.compiere.process.SvrProcess;
import org.openup.cfe.CFEManager;
import org.openup.cfe.InterfaceCFEDTO;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;

public class PCFEPruebaSisteco extends SvrProcess {

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		
		
		MInvoice mInv = new MInvoice(getCtx(), 1009892, get_TrxName()); // documentNo 49162
		
		//CFEDefType cfeDefType = mInv.getCFEDTO();
		
		CFEManager cfeManager = new CFEManager(getAD_Client_ID(), getCtx(), get_TrxName());
		cfeManager.addCFE(mInv);
		cfeManager.SendCFE();
		
		
		return "OK";
	}

}
