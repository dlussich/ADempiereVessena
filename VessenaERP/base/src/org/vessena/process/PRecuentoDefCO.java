package org.openup.process;

import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.openup.model.MRecuentoDef;

// org.openup.process.PRecuentoDefCO
// OpenUp. Nicolas Garcia. 07/10/2011. Creado para Issue #889.
public class PRecuentoDefCO extends SvrProcess {

	@Override
	protected String doIt() throws Exception {

		if (this.getRecord_ID() > 0) {
			MRecuentoDef rec = new MRecuentoDef(getCtx(), getRecord_ID(), this.get_TrxName());

			if (rec.getDocAction().equalsIgnoreCase("CO")) return rec.completeIt();

			if (rec.getDocAction().equalsIgnoreCase("VO")) {

				if (rec.voidIt()) return "Documento cancelado";

				return "Error al cancelar documento";
			}
			if (rec.getDocAction().equalsIgnoreCase(DocAction.ACTION_ReActivate)) {

				if (rec.reActivateIt()) return "Documento ReActivado";

				return "Error al ReActivar";
			}

		}

		return "Debe Guardar antes de completar";
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

}
