package org.openup.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocumentEngine;
import org.compiere.process.SvrProcess;
import org.openup.model.MCheckBookControl;


public class PControlLibreta  extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		
		MCheckBookControl x= new MCheckBookControl(getCtx(),this.getRecord_ID(),get_TrxName());
		String msg = "Ok";
		try {
			if (x.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Drafted) || x.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_InProgress)) {
				if (!x.processIt(x.getDocAction())) {
					msg = x.getProcessMsg();
					throw new Exception(x.getProcessMsg());
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new AdempiereException(e);
			
		}
		
		return msg;
		

	}

}
