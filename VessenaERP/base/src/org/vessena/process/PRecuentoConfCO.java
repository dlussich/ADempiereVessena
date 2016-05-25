package org.openup.process;

import org.compiere.process.SvrProcess;
import org.openup.model.MRecuentoConf;

// org.openup.process.PRecuentoConfCO
// OpenUp. Nicolas Garcia. 07/10/2011. Creado para Issue #889.
public class PRecuentoConfCO extends SvrProcess {

	@Override
	protected String doIt() throws Exception {

		if (this.getRecord_ID() > 0) {

			MRecuentoConf conf = new MRecuentoConf(getCtx(), getRecord_ID(), this.get_TrxName());

			conf.setWindow(this.getProcessInfo().getWindow());

			if (conf.getDocAction().equalsIgnoreCase("CO")) return conf.completeIt();

			if (conf.getDocAction().equalsIgnoreCase("VO")) {

				if (conf.voidIt()) return "Documento Revertido";

				return "Error al revertir documento";
			}
		}
		return "Debe Guardar antes de completar";
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

}