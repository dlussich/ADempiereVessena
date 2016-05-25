/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 19/01/2015
 */
package org.openup.process;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.process.DocumentEngine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MTRInvPrintFlete;
import org.openup.model.MTRPaymentFlete;
import org.openup.model.MTRPrintFlete;

/**
 * org.openup.process - PTRLoadAnticipos
 * OpenUp Ltda. Issue #1405 
 * Description: Refresca anticipos en Vale Flete.
 * @author Gabriel Vila - 19/01/2015
 * @see
 */
public class PTRLoadAnticipos extends SvrProcess {

	private MInvoice invoice = null;
	
	/**
	 * Constructor.
	 */
	public PTRLoadAnticipos() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 19/01/2015
	 * @see
	 */
	@Override
	protected void prepare() {
		
		this.invoice = new MInvoice(getCtx(), this.getRecord_ID(), get_TrxName());
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 19/01/2015
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		try {
			
			// Validaciones
			if (this.invoice.get_ID() <= 0){
				throw new AdempiereException("No se obtuvo Vale Flete a procesar.");
			}
			
			if (!this.invoice.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Applied)){
				throw new AdempiereException("El Vale Flete debe estar Aplicado para poder procesar.");
			}
			
			// Si ya tiene anticipos
			List<MTRPaymentFlete> anticipos = this.invoice.getLinesAnticipoFlete();
			if (anticipos.size() > 0){
				// Si tiene conceptos impresos verifico si alguno maneja anticipos
				List<MTRInvPrintFlete> pfletes = MTRInvPrintFlete.forInvoice(getCtx(), this.invoice.get_ID(), get_TrxName());
				for (MTRInvPrintFlete pflete: pfletes){
					MTRPrintFlete printflete = (MTRPrintFlete)pflete.getUY_TR_PrintFlete();
					if (printflete.isAllocatePayments()){
						throw new AdempiereException("No es posible refrescar anticipos ya que este Vale Flete ya tiene un Concepto Impreso.");
					}
				}
				
				// Delete de información de anticipos para poder luego refrescar
				DB.executeUpdateEx(" delete from uy_tr_paymentflete where c_invoice_id =" + this.invoice.get_ID(), get_TrxName());
			}
			
			// Cargo nuevamente anticipos
			this.invoice.setAdelantosValeFlete();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return "OK";
	}

}
