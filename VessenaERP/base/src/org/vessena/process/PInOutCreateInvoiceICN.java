package org.openup.process;

import java.util.logging.Level;

import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrderLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * 
 * OpenUp. issue #754
 * InOutCreateInvoiceICN
 * Descripcion : Clase creada para generar una Nota de Crédito Importaciones desde la 
 * ventana "Devoluciones Directas a Proveedores"
 * @author Nicolas Sarlabos
 * Fecha : 19/08/2011
 */

public class PInOutCreateInvoiceICN extends SvrProcess {

	/**	Shipment					*/
	private int 	p_M_InOut_ID = 0;
	/**	Price List Version			*/
	private int		p_M_PriceList_ID = 0;
	/* Document No					*/
	private String	p_InvoiceDocumentNo = null;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("M_PriceList_ID"))
				p_M_PriceList_ID = para[i].getParameterAsInt();
			else if (name.equals("InvoiceDocumentNo"))
				p_InvoiceDocumentNo = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_M_InOut_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Create Invoice.
	 *	@return document no
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		log.info("M_InOut_ID=" + p_M_InOut_ID 
			+ ", M_PriceList_ID=" + p_M_PriceList_ID
			+ ", InvoiceDocumentNo=" + p_InvoiceDocumentNo);
		if (p_M_InOut_ID == 0)
			throw new IllegalArgumentException("No Shipment");
		//
		MInOut ship = new MInOut (getCtx(), p_M_InOut_ID, null);
		if (ship.get_ID() == 0)
			throw new IllegalArgumentException("Shipment not found");
		if (!MInOut.DOCSTATUS_Completed.equals(ship.getDocStatus()))
			throw new IllegalArgumentException("Shipment not completed");
		
		MInvoice invoice = new MInvoice (ship, null);
		// Should not override pricelist for RMA
		if (p_M_PriceList_ID != 0 && ship.getM_RMA_ID() == 0)
			invoice.setM_PriceList_ID(p_M_PriceList_ID);
		if (p_InvoiceDocumentNo != null && p_InvoiceDocumentNo.length() > 0)
			invoice.setDocumentNo(p_InvoiceDocumentNo);
		// OpenUp. Nicolas Sarlabos. 19/08/2011.  issue #754
		//Se asocia el ID de la devolucion a la nota de credito y se setea documento.
		invoice.setM_InOut_ID(ship.get_ID());
		MDocType doc = new MDocType(getCtx(), ship.getC_DocType_ID(), null);
		if (!ship.isSOTrx()){
			if (doc.getDocBaseType().equalsIgnoreCase("MMS")){
				
				String sql = "SELECT c_doctype_id FROM c_doctype WHERE docbasetype='ANI' AND docsubtypeso='NI'"; 
				int idNC = DB.getSQLValue(get_TrxName(), sql);
				invoice.setC_DocTypeTarget_ID(idNC);  //ID Nota de Credito Importaciones
				invoice.setC_DocType_ID(idNC);
			}
			else{
				
				String sql = "SELECT c_doctype_id FROM c_doctype WHERE docbasetype='AFI'"; 
				int idFI = DB.getSQLValue(get_TrxName(), sql);
				invoice.setC_DocTypeTarget_ID(idFI);  //ID Factura de Importaciones
				invoice.setC_DocType_ID(idFI);
			}
		}
		
		MDocType docInv = MDocType.forValue(getCtx(), "invoicevendor", null);
		invoice.setC_DocTypeTarget_ID(docInv.get_ID());
		
		invoice.saveEx();
		// Fin OpenUp.
		
		MInOutLine[] shipLines = ship.getLines(false);
		for (int i = 0; i < shipLines.length; i++)
		{
			MInOutLine sLine = shipLines[i];
			MInvoiceLine line = new MInvoiceLine(invoice);
			line.setShipLine(sLine);
			if (sLine.sameOrderLineUOM())
				line.setQtyEntered(sLine.getQtyEntered());
			else
				line.setQtyEntered(sLine.getMovementQty());
			line.setQtyInvoiced(sLine.getMovementQty());
			
			
			
			// Me aseguro de poner bien la unidad de medida de venta en la linea de la invoice
			if (line.getC_OrderLine_ID() > 0) {
				MOrderLine oLine = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), get_TrxName());
				line.setC_UOM_ID(oLine.getC_UOM_ID()); 
			}
			else{
				line.setC_UOM_ID(sLine.getC_UOM_ID());
				line.setQtyEntered(sLine.getQtyEntered());
			}
			
			
			if (!line.save())
				throw new IllegalArgumentException("Cannot save Invoice Line");
		}
		// OpenUp. Nicolas Sarlabos. 19/08/2011. issue #754
		// Se guarda el ID de la factura en el envio y se guarda el envio
		ship.setC_Invoice_ID(invoice.getC_Invoice_ID());
		ship.saveEx();
		// Fin OpenUp.
		return invoice.getDocumentNo();
		
	}	//	InOutCreateInvoice
}
