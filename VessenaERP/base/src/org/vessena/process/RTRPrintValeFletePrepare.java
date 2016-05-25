/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 09/01/2015
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInvoice;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.ReportStarter;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MInvoicePrintFlete;
import org.openup.model.MTRInvPrintFlete;
import org.openup.model.MTRPrintFlete;

/**
 * org.openup.process - RTRPrintValeFletePrepare
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 09/01/2015
 * @see
 */
public class RTRPrintValeFletePrepare extends SvrProcess {

	
	private int cInvoiceID = 0;
	private int uyTRPrintFleteID = 0;
	private String directPrint = "Y";
	private String approved = "N";
	private MInvoice inv = null;
	private MTRPrintFlete printflete = null;

	
	/**
	 * Constructor.
	 */
	public RTRPrintValeFletePrepare() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 09/01/2015
	 * @see
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("C_Invoice_ID")){
					if (para[i].getParameter() != null) this.cInvoiceID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("UY_TR_PrintFlete_ID")){
					if (para[i].getParameter() != null) this.uyTRPrintFleteID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("IsDirectPrint")){
					if (para[i].getParameter() != null) this.directPrint = ((String)para[i].getParameter());
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("IsApproved")){
					if (para[i].getParameter() != null) this.approved = ((String)para[i].getParameter());
				}
			}			
		}


	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 09/01/2015
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		MPInstance instance = null;
		ProcessInfo pi = null;
		MPInstancePara para = null;
		
		try {

			MTRInvPrintFlete invpf = MTRInvPrintFlete.forInvoicePrintFlete(getCtx(), this.cInvoiceID, this.uyTRPrintFleteID, get_TrxName());
			
			// Si solo es para aprobar la reimpresion de un concepto
			if(this.approved.equalsIgnoreCase("Y")){
				
				// Actualizo estado de impresion de este concepto
				if (invpf != null){
					if (invpf.get_ID() > 0){
						invpf.setIsPrinted(false);
						invpf.saveEx();
					}
				}
				return "OK";
			}
			
			this.inv = new MInvoice(getCtx(), cInvoiceID, get_TrxName());
			this.printflete = new MTRPrintFlete(getCtx(), this.uyTRPrintFleteID, null);

			boolean marcadoReImpresion = false;
			
			// Verifico si este concepto ya esta impreso. En ese caso aviso y salgo.
			
			if (invpf != null){
				if (invpf.get_ID() > 0){
					if (invpf.isPrinted()){
						throw new AdempiereException("Este concepto ya fue impreso anteriormente.\n" +
								 "Requiere Autorización para poder ser ReImpreso.");
					}
					else{
						// Fue impreso y luego marcado como no impreso en proceso de autorizacion de reimpresion.
						marcadoReImpresion = true;
					}
				}
			}

			// Calculo monto de vale flete en varias monedas
			this.setInvoicePrintFlete();

			// Instancio, seteo y ejecuto reporte de vale flete
			int adProcessID = MProcess.getProcess_ID("UY_RTR_ValeFlete", null);
			
			if (adProcessID <= 0){
				throw new Exception("No se pudo obtener ID del proceso de impresion para primer hoja de MIC/DTA");
			}
			
			//instance = new MPInstance(Env.getCtx(), adProcessID, 0);
			instance = new MPInstance(getCtx(), adProcessID, 0);
			instance.saveEx();

			pi = new ProcessInfo ("ValeFlete", adProcessID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());		
			
			if(this.directPrint.equalsIgnoreCase("Y")){
				pi.setPrintPreview(false);				
			} else pi.setPrintPreview(true);
						
			para = new MPInstancePara(instance, 10);
			para.setParameter("C_Invoice_ID", new BigDecimal(this.cInvoiceID));
			para.saveEx();
			
			para = new MPInstancePara(instance, 20);
			para.setParameter("UY_TR_PrintFlete_ID", new BigDecimal(this.uyTRPrintFleteID));
			para.saveEx();
			
			ReportStarter starter = new ReportStarter();
			starter.startProcess(getCtx(), pi, null);
			
			// Marco este concepto de impresion para este vale flete como impreso si es impresion directa.
			if (this.directPrint.equalsIgnoreCase("Y")){

				if (!marcadoReImpresion){

					MTRInvPrintFlete pflete = new MTRInvPrintFlete(getCtx(), 0, get_TrxName());
					pflete.setC_Invoice_ID(cInvoiceID);
					pflete.setUY_TR_PrintFlete_ID(uyTRPrintFleteID);
					pflete.setIsPrinted(true);
					pflete.saveEx();
					
					// Si el concepto de impresion considera anticipos
					if (this.printflete.isAllocatePayments()){

						// Al imprimir el vale flete se asume que los anticipos quedan afectados al vale flete y por lo tanto debo
						// hacer una afectacion automatica para actualizar el estado de cuenta del conductor (socio de negocio - proveedor).
						String message = this.inv.allocateValeFlete();
						if (message != null){
							throw new AdempiereException(message);
						}
					}
				}
				else{
					invpf.setIsPrinted(true);
					invpf.saveEx();
				}
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return "OK";
	}

	
	/***
	 * Calcula monto de vale flete en otras dos monedas, y con el resultado genera un registro en base para poder
	 * utiliarse en la impresion.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 12/01/2015
	 * @see
	 */
	private void setInvoicePrintFlete() {

		try {
			
			// Primero elimino registros anteriores
			DB.executeUpdateEx(" delete from c_invoiceprintflete where c_invoice_id =" + this.cInvoiceID, get_TrxName());
			
			String sql = "";
			BigDecimal amtAnticipos = Env.ZERO;

			// Si este concepto de impresion considera anticipos
			if (printflete.isAllocatePayments()){

				// Obtengo total de anticipos en caso de haberlos
				sql = " select coalesce(sum(amtopen),0) from uy_tr_paymentflete where c_invoice_id =" + inv.get_ID() + 
					  " and isselected='Y' ";
					  //" and isexecuted = 'N'"; 
						
				amtAnticipos = DB.getSQLValueBDEx(get_TrxName(), sql);
				if (amtAnticipos == null) amtAnticipos = Env.ZERO;
			}
			
			// Total de lineas por este concepto de impresion
			sql = " select coalesce(sum(linenetamt),0) from c_invoiceline where c_invoice_id =" + inv.get_ID() + " and uy_tr_printflete_id =" + this.uyTRPrintFleteID;
			BigDecimal amtConcepto = DB.getSQLValueBDEx(get_TrxName(), sql);
			
			// Total concepto - anticipos
			BigDecimal amtTotal = amtConcepto.subtract(amtAnticipos);
			
			// Debo generar registros para importe de factura en multiples monedas, necesarios para la impresion.
			MInvoicePrintFlete pflete = new MInvoicePrintFlete(getCtx(), 0, null);
			pflete.setC_Invoice_ID(this.cInvoiceID);
			pflete.setUY_TR_PrintFlete_ID(uyTRPrintFleteID);
			
			// Monedas y montos segun tasa de cambio del dia del vale flete
			pflete.setC_Currency_ID(inv.getC_Currency_ID());

			BigDecimal rateBR = MConversionRate.getDivideRate(142, 297, inv.getDateInvoiced(), 0, 1000006, 0);
			if (rateBR == null) rateBR = Env.ONE;
			
			BigDecimal rateUSD = MConversionRate.getDivideRate(142, 100, inv.getDateInvoiced(), 0, 1000006, 0);
			if (rateUSD == null) rateUSD = Env.ONE;

			BigDecimal rateBRUSD = rateUSD.divide(rateBR, 3, RoundingMode.HALF_UP);
			
			if (pflete.getC_Currency_ID() == 142){
				pflete.setC_Currency_ID_2(297);
				pflete.setC_Currency_ID_3(100);
				
				pflete.setAmt(amtTotal);
				pflete.setamt2(pflete.getAmt().divide(rateBR, 2, RoundingMode.HALF_UP));
				pflete.setamt3(pflete.getAmt().divide(rateUSD, 2, RoundingMode.HALF_UP));
				
			}
			else if (pflete.getC_Currency_ID() == 297){
				pflete.setC_Currency_ID_2(142);
				pflete.setC_Currency_ID_3(100);
				
				pflete.setAmt(amtTotal);
				pflete.setamt2(pflete.getAmt().multiply(rateBR).setScale(2, RoundingMode.HALF_UP));
				pflete.setamt3(pflete.getAmt().divide(rateBRUSD, 2, RoundingMode.HALF_UP));
			}
			else if (pflete.getC_Currency_ID() == 100){
				pflete.setC_Currency_ID_2(142);
				pflete.setC_Currency_ID_3(297);

				pflete.setAmt(amtTotal);
				pflete.setamt2(pflete.getAmt().multiply(rateUSD).setScale(2, RoundingMode.HALF_UP));
				pflete.setamt3(pflete.getAmt().multiply(rateBRUSD).setScale(2, RoundingMode.HALF_UP));
			}
			
			pflete.saveEx();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

}
