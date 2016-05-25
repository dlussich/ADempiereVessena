package org.openup.aduana;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.MTRMic;


public class PTRAduanaSendMIC extends SvrProcess{
	
	private int micID;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] parameters = getParameter();
		for (int i = 0; i < parameters.length; i++) {
			String name = parameters[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("uy_tr_mic_id")) {
					this.micID = Integer.parseInt(parameters[i]
							.getParameter().toString());
				}
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		PTRAduanaSendMICLogic logic = new PTRAduanaSendMICLogic(getCtx(),get_TrxName());
		MTRMic mic = new MTRMic(getCtx(),micID,get_TrxName());
		return logic.execute(getCtx(), mic ,get_TrxName());	
		
	}
	
	
}
