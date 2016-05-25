/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MDocType;
import org.compiere.model.MPayment;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MLinePayment;
import org.openup.model.MPaymentRule;
import org.openup.model.MTRClearing;
import org.openup.model.MTRDriver;

/**
 * @author Nicolás
 *
 */
public class PTRGenerateAdvance extends SvrProcess {
	
	private int clearingID = 0;
	private int processID = 0;
	private String docNo = "";

	/**
	 * 
	 */
	public PTRGenerateAdvance() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_TR_Clearing_ID")){
					this.clearingID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("HR_Process_ID")){
					this.clearingID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("DocumentNo")){
					this.docNo = ((String)para[i].getParameter());					
				}
			}
			
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "select coalesce(sum(montopesos),0)" +
				" from uy_tr_clearingline" +
				" where uy_tr_clearing_id = " + this.clearingID;

		BigDecimal amt = DB.getSQLValueBDEx(get_TrxName(), sql);

		//si hay monto de adelanto de sueldo
		if(amt.compareTo(Env.ZERO)>0){
			
			MTRClearing hdr = new MTRClearing(getCtx(),this.clearingID,get_TrxName());

			MDocType doc = MDocType.forValue(getCtx(), "adelanto", get_TrxName());
			
			MPaymentRule payRule = MPaymentRule.forValue(getCtx(), "Contado", get_TrxName());
			
			MTRDriver driver = (MTRDriver)hdr.getUY_TR_Driver();
			
			if(driver.getC_BPartner_ID()>0){
				
				//creo cabezal del adelanto
				MPayment pay = new MPayment(getCtx(),0,get_TrxName());
				pay.setDocumentNo(this.docNo);
				pay.setDateTrx(hdr.getDateTrx());
				pay.setDueDate(hdr.getDateTrx());
				pay.setC_DocType_ID(doc.get_ID());
				pay.setTrxType("C");
				pay.setTenderType("K");
				pay.setC_Currency_ID(142);
				pay.setC_BPartner_ID(driver.getC_BPartner_ID());
				pay.set_ValueOfColumn("HR_Process_ID", this.processID);
				pay.set_ValueOfColumn("UY_TR_Clearing_ID", this.clearingID);
				pay.setDocStatus(DocumentEngine.STATUS_Drafted);
				pay.setDocAction(DocumentEngine.ACTION_Complete);
				pay.saveEx();
				
				//creo linea del adelanto
				MLinePayment line = new MLinePayment(getCtx(),0,get_TrxName());
				line.setC_Payment_ID(pay.get_ID());
				line.setUY_PaymentRule_ID(payRule.get_ID());
				line.setPayAmt(amt);
				line.setDueDate(hdr.getDateTrx());
				line.saveEx();
				
				//TODO: falta campo de cuenta bancaria de salida!!
				
				
				
				
				
				
				
				
				
				
				
				
			} else throw new AdempiereException("No se obtuvo proveedor asociado al conductor de esta liquidacion");


			














		} else ADialog.warn(0,null,"El importe de adelantos debe ser mayor a cero");	




		return "OK";
	}

}
