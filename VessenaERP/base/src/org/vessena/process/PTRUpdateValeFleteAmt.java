/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 15/01/2015
 */
package org.openup.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MTRTransOrder;


/**
 * org.openup.process - PTRUpdateValeFleteAmt
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 15/01/2015
 * @see
 */
public class PTRUpdateValeFleteAmt extends SvrProcess {

	private int torderID = 0;
	private int cCurrencyID = 0;
	private BigDecimal payAmt = Env.ZERO;
	
	/**
	 * Constructor.
	 */
	public PTRUpdateValeFleteAmt() {

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 15/01/2015
	 * @see
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_TR_TransOrder_ID")){
					this.torderID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("C_Currency_ID")){
					this.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("PayAmt")){
					this.payAmt = ((BigDecimal)para[i].getParameter());
				}
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 15/01/2015
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		try {
			
			if (this.payAmt == null){
				throw new AdempiereException("Debe indicar Monto Flete Tercero.");
			}
			
			// Verifico si esta orden de transporte no tiene un vale flete con el concepto de flete ya impreso. En ese caso aviso y salgo.
			String sql = " select pf.c_invoice_id " +
				 	     " from UY_TR_InvPrintFlete pf " +
				 	     " inner join c_invoice inv on pf.c_invoice_id = inv.c_invoice_id " +
				 	     " where inv.uy_tr_transorder_id =" + this.torderID +
				 	     " and pf.uy_tr_printflete_id IN (select uy_tr_printflete_id from uy_tr_printflete where value='flete') ";
			
			int idAux = DB.getSQLValueEx(null, sql);
			if (idAux > 0){
				throw new AdempiereException("Este Orden de Transporte ya tiene concepto de vale flete impreso y por lo tanto no es posible actualizar el monto.");
			}
			
			MTRTransOrder torder = new MTRTransOrder(getCtx(), this.torderID, get_TrxName());
			torder.setC_Currency_ID_2(this.cCurrencyID);
			torder.setPayAmt(this.payAmt);
			torder.saveEx();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return "OK";
	}

}
