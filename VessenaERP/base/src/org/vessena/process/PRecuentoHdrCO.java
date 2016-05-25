package org.openup.process;

import org.compiere.process.SvrProcess;
import org.openup.model.MRecuentoHdr;

// org.openup.process.PRecuentoHdrCO
// OpenUp. Nicolas Garcia. 07/10/2011. Creado para Issue #889.
public class PRecuentoHdrCO extends SvrProcess {

	@Override
	protected String doIt() throws Exception {

		if (this.getRecord_ID() > 0) {
			MRecuentoHdr rec = new MRecuentoHdr(getCtx(), getRecord_ID(), this.get_TrxName());
			return rec.completeIt();
		}
		return "Debe Guardar antes de completar";
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

}
