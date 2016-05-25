/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCurrency;
import org.compiere.model.MInvoice;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MProcess;
import org.compiere.model.MSysConfig;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.ReportStarter;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.util.Converter;

/**
 * @author Nicolas
 *
 */
public class PTRPrintValeFlete extends SvrProcess {
	
	private int invoiceID = 0;
	//private String directPrint = "Y";

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
				if (name.equalsIgnoreCase("C_Invoice_ID")){
					if (para[i].getParameter() != null) this.invoiceID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}			
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		MPInstance instance = null;
		ProcessInfo pi = null;
		MPInstancePara para = null;
		
		try {			
				
			MInvoice invoice = new MInvoice(getCtx(), this.invoiceID, get_TrxName());
			
			this.setAmountLiteral(invoice);

			// Instancio, seteo y ejecuto reporte de vale flete
			int adProcessID = MProcess.getProcess_ID("UY_RCpra_ValeFlete", null);
			
			if (adProcessID <= 0){
				throw new Exception("No se pudo obtener ID del proceso de impresion de Vale Flete");
			}
			
			instance = new MPInstance(getCtx(), adProcessID, 0);
			instance.saveEx();

			pi = new ProcessInfo ("ValeFlete", adProcessID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());		
			
			/*if(this.directPrint.equalsIgnoreCase("Y")){
				pi.setPrintPreview(false);				
			} else pi.setPrintPreview(true);*/
						
			para = new MPInstancePara(instance, 10);
			para.setParameter("C_Invoice_ID", new BigDecimal(this.invoiceID));
			para.saveEx();
	
			ReportStarter starter = new ReportStarter();
			starter.startProcess(getCtx(), pi, null);			

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return "OK";
		
	}

	/**
	 * OpenUp Ltda. Issue #4023 
	 * Description: obtiene importe de anticipos de este VF y lo setea en campo de forma literal
	 * @author Nicolas Sarlabos - 29/05/2015
	 * @see
	 */
	private void setAmountLiteral(MInvoice invoice) {
		
		String sql = "";		
		MCurrency cur = null;
		Converter conv = null;
		
		//obtengo monto de anticipos
		sql = "select coalesce(vista.amtallocated,0)" +
              " from c_invoice inv" +
              " left join alloc_invoiceamtopen vista on inv.c_invoice_id = vista.c_invoice_id" +
              " where inv.c_invoice_id = " + this.invoiceID;
		BigDecimal amt = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		if(amt.compareTo(Env.ZERO)>0){
			
			sql = "select vista.c_currency_id" +
					" from c_invoice inv" +
					" left join alloc_invoiceamtopen vista on inv.c_invoice_id = vista.c_invoice_id" +
					" where inv.c_invoice_id = " + this.invoiceID;
			int curID = DB.getSQLValueEx(get_TrxName(), sql);
			
			cur = new MCurrency(getCtx(), curID, get_TrxName());//obtengo moneda de anticipos
			
			conv = new Converter();
			
			amt = amt.setScale(2, RoundingMode.HALF_UP);
			
			String literal = conv.getStringOfBigDecimal(amt);
			
			literal = cur.getDescription() + ": " + literal;
									
			DB.executeUpdateEx("update c_invoice set literalnumber = null, literalnumber2 = null, literalnumber3 = '" + literal + 
					"' where c_invoice_id = " + invoice.get_ID(), null);
			
			BigDecimal saldo = invoice.getGrandTotal().subtract(amt);//obtengo saldo
			
			cur = new MCurrency(getCtx(), invoice.getC_Currency_ID(),
					get_TrxName());

			String curDescription = cur.getDescription(); // obtengo la
															// descripcion de la
															// moneda

			conv = new Converter();

			int top1 = MSysConfig.getIntValue("UY_CHECK_LITERAL_LENGTH_1", 70,
					getAD_Client_ID());
			// int top2 = MSysConfig.getIntValue("UY_CHECK_LITERAL_LENGTH_2",
			// 40, getAD_Client_ID());

			literal = conv.getStringOfBigDecimal(saldo);

			literal = curDescription + ":" + " " + literal;

			if (literal.length() <= top1) {
				
				DB.executeUpdateEx("update c_invoice set literalnumber = '" + literal + "' where c_invoice_id = " + invoice.get_ID(), null);
		
			} else {
				
				int posHasta1 = literal.indexOf(" ", top1);
				if (posHasta1 < 0)
					posHasta1 = top1;
				int posDesde1 = literal.lastIndexOf(" ", top1);
				if (posDesde1 < 0)
					posDesde1 = top1;

				int posSep1 = ((posHasta1 - top1) < ((posDesde1 - top1) * -1)) ? posHasta1
						: posDesde1;

				DB.executeUpdateEx("update c_invoice set literalnumber = '" + literal.substring(0, posSep1) + "' where c_invoice_id = " + invoice.get_ID(), null);
				DB.executeUpdateEx("update c_invoice set literalnumber2 = '" + literal.substring(posSep1 + 1) + "' where c_invoice_id = " + invoice.get_ID(), null);
			
			}			
			
		}		
		
	}

}
