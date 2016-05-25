package org.openup.process;

import java.math.BigInteger;
import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.dgi.EnvioCFE;
import org.openup.util.ChargeEInvoice;


/**
 * 
 * org.openup.dgi.process - PEInvoicing OpenUp Ltda. Issue # 2208 Description:
 * 
 * @author Leonardo Boccone - 27/05/2014
 * @see
 */
public class PEInvoicing extends SvrProcess {

	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private BigInteger docType;
	EnvioCFE envio;
	

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("StartDate")) {
					this.fechaInicio = (Timestamp) para[i].getParameter();
					this.fechaFin = (Timestamp) para[i].getParameter_To();
				}
				if (name.equalsIgnoreCase("DocTypeName")) {
					this.docType = BigInteger.valueOf( para[i].getParameterAsInt());

				}
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		ChargeEInvoice aux = new ChargeEInvoice();
		
		aux.ChargeHead(this.getCtx(),this.get_TrxName());
		aux.ChargeBody(this.getCtx(),this.get_TrxName(),this.fechaInicio,this.fechaFin);
		aux.Serializar(this.getCtx(),this.get_TrxName());
		
		return "OK";
	}

	
	

}
