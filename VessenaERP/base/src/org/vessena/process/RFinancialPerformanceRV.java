package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

// OpenUp. Nicolas Garcia. 14/09/2011. Issue #698. Creado para este caso
public class RFinancialPerformanceRV extends SvrProcess {

	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	private int idCliente = 0;

	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {

			String name = para[i].getParameterName().trim();

			if (name != null) {

				if (name.equalsIgnoreCase("C_BPartner_ID")) {
					if (para[i].getParameter() != null) {
						this.idCliente = ((BigDecimal) para[i].getParameter()).intValue();
					}
				}

				if (name.equalsIgnoreCase("DateInvoiced")) {
					if (para[i].getParameter() != null) {
						this.fechaDesde = (Timestamp) para[i].getParameter();
					}

					if (para[i].getParameter_To() != null) {
						this.fechaHasta = (Timestamp) para[i].getParameter_To();
					}
				}

			}
		}
	}

	@Override
	protected String doIt() throws Exception {

		String salida = "";

		// Instacio logica.
		RFinancialPerformanceLogica logica = new RFinancialPerformanceLogica(log, fechaDesde, fechaHasta, idCliente, Env.getAD_Client_ID(Env.getCtx()), Env
				.getAD_Org_ID(Env.getCtx()), Env.getAD_User_ID(Env.getCtx()), get_TrxName());

		salida = logica.loadModelTable();

		return salida;
	}

}