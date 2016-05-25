package org.openup.aduana;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.openup.model.MTRMic;

public class PTRAduanaConsultaStatusMIC extends SvrProcess{

	private int micID;
	
	public PTRAduanaConsultaStatusMIC() {
		// TODO Auto-generated constructor stub
	}

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
		MTRMic mic = new MTRMic(getCtx(),this.micID,get_TrxName());
		return SendSoap(mic);
	}
	
	protected String SendSoap(MTRMic mic){
		
		try {
			ConsultaRespuestaMic.consultarRespuestaAsincronica(null, mic, this.getAD_Client_ID(), Env.getAD_Org_ID(getCtx()), get_TrxName(), getCtx(), -1);
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return null;
		
	}

}
