/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Dec 14, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.DB;

/**
 * org.openup.model - MCFEInboxLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Dec 14, 2015
*/
public class MCFEInboxLine extends X_UY_CFE_InboxLine {

	private static final long serialVersionUID = -1785524081307800340L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_CFE_InboxLine_ID
	 * @param trxName
	*/

	public MCFEInboxLine(Properties ctx, int UY_CFE_InboxLine_ID, String trxName) {
		super(ctx, UY_CFE_InboxLine_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MCFEInboxLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Se genera una nueva Invoice en Borrador para este comprobante
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Jan 14, 2016
	 */
	public void generateInvoice() {
		
		try {
			
			MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
			invoice.setC_DocTypeTarget_ID(this.getC_DocType_ID());
			invoice.setC_DocType_ID(this.getC_DocType_ID());
			invoice.setDateInvoiced(this.getDateTrx());
			invoice.setC_BPartner_ID(this.getC_BPartner_ID());
			
			MBPartner bp = (MBPartner)this.getC_BPartner();
			MBPartnerLocation[] locations = bp.getLocations(true);
			if (locations.length > 0){
				invoice.setC_BPartner_Location_ID(locations[0].get_ID());
			}
			invoice.setIsSOTrx(true);
			invoice.setC_PaymentTerm_ID(this.getC_PaymentTerm_ID());
			invoice.setPOReference(this.getnrodoc());
			invoice.setC_Currency_ID(this.getC_Currency_ID());
			invoice.setDocStatus(DOCSTATUS_Drafted);
			invoice.setDocAction(DocAction.ACTION_Complete);
			invoice.setProcessed(false);
			invoice.saveEx();
			
			this.setC_Invoice_ID(invoice.get_ID());
			
			// Lineas
			List<MCFEInboxProd> lines = this.getLines();
			for (MCFEInboxProd line: lines){
				
				MInvoiceLine invLine = new MInvoiceLine(invoice);
				invLine.setM_Product_ID(line.getM_Product_ID());
				invLine.setQtyEntered(line.getQtyEntered());
				invLine.setQtyInvoiced(line.getQtyEntered());
				invLine.setPriceLimit(line.getPriceEntered());
				invLine.setPriceList(line.getPriceEntered());
				invLine.setPriceActual(line.getPriceEntered());
				invLine.setPriceEntered(line.getPriceEntered());
				invLine.setC_Tax_ID(line.getC_Tax_ID());
				invLine.saveEx();
				
				// Me aseguro de dejar los valores e impuestos segun vienen en la linea del proceso
				String action = " udpdate c_invoiceline set c_tax_id =" + line.getC_Tax_ID() + 
						", linenetamt =" + line.getLineNetAmt() +
						", taxamt =" + line.getTaxAmt() +
						", linetotalamt =" + line.getLineTotalAmt() +
						" where c_invoiceline_id =" + invLine.get_ID();
				DB.executeUpdateEx(action, get_TrxName());
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Obtiene y retorna lineas de este comprobante
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Jan 14, 2016
	 * @return
	 */
	private List<MCFEInboxProd> getLines() {
		
		String whereClause = X_UY_CFE_InboxProd.COLUMNNAME_UY_CFE_InboxLine_ID + "=" + this.get_ID();
		
		List<MCFEInboxProd> lines = new Query(getCtx(), I_UY_CFE_InboxProd.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}

}
