/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MSysConfig;
import org.openup.model.MOrderSection;
import org.openup.model.MOrderSectionLine;
 
/**
 *	Create (Generate) Invoice from Shipment
 *	
 *  @author Jorg Janke
 *  @version $Id: InOutCreateInvoice.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class InOutCreateInvoice extends SvrProcess
{
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

		if (MSysConfig.getBooleanValue("UY_PO_CREATE_BY_ACTIVITY", false, this.getAD_Client_ID())){
			String message = this.executePOActivity();
			return message;
		}
		//
		MInOut ship = new MInOut (getCtx(), p_M_InOut_ID, get_TrxName());
		if (ship.get_ID() == 0)
			throw new IllegalArgumentException("Shipment not found");
		if (!MInOut.DOCSTATUS_Completed.equals(ship.getDocStatus()))
			throw new IllegalArgumentException("Shipment not completed");
		
		MInvoice invoice = new MInvoice (ship, null);
		// Should not override pricelist for RMA
		if (p_M_PriceList_ID != 0 && ship.getM_RMA_ID() == 0)
			invoice.setM_PriceList_ID(p_M_PriceList_ID);
		if (p_InvoiceDocumentNo != null && p_InvoiceDocumentNo.length() > 0){
			
			invoice.setDocumentNo(p_InvoiceDocumentNo);
			
			// OpenUp. Gabriel Vila. 09/04/2014. Issue #1829
			// Seteo numero auxiliar de documento utilizado en las facturas de compras
			invoice.setDocumentNoAux(p_InvoiceDocumentNo);
			// Fin OpenUp. Issue #1829
		}
			
		// OpenUp. Nicolas Sarlabos. 23/06/2011.  issue #646
		//Se asocia el ID de la devolucion a la nota de credito y se setea documento.
		invoice.setM_InOut_ID(ship.get_ID());
		invoice.setM_Warehouse_ID(ship.getM_Warehouse_ID());
		MDocType doc = new MDocType(getCtx(), ship.getC_DocType_ID(), null);
		if (!ship.isSOTrx()){
			if (doc.getDocBaseType().equalsIgnoreCase("MMS")){
				MDocType docAux = MDocType.forValue(getCtx(), "vendornc", null);
				invoice.setC_DocTypeTarget_ID(docAux.get_ID());
				invoice.setC_DocType_ID(docAux.get_ID());
			}
			else{
				MDocType docAux = MDocType.forValue(getCtx(), "invoicevendor", null);
				invoice.setC_DocTypeTarget_ID(docAux.get_ID());
				invoice.setC_DocType_ID(docAux.get_ID());
			}
		}
		else{
			// Cuando una devolucion de cliente (venta), por defecto el documento que creo es una nota de credito
			if (doc.getDocBaseType().equalsIgnoreCase("MMR")){
				MDocType nc = MDocType.forValue(getCtx(), "customernc", null);
				invoice.setC_DocTypeTarget_ID(nc.get_ID());
				invoice.setC_DocType_ID(nc.get_ID());
			}
		}

		invoice.LinesCreatedAutoFromPO = true; // Para que no genere lineas de factura desde orden de compra, sino que respete la entrega.
		
		invoice.saveEx();
		// Fin OpenUp.
		
		MInOutLine[] shipLines = ship.getLines(false);
		for (int i = 0; i < shipLines.length; i++)
		{
			MInOutLine sLine = shipLines[i];
			MInvoiceLine line = new MInvoiceLine(invoice);
			
			// OpenUp. Gabriel Vila. 02/05/2012.
			// Me aseguro de setear bien las cantidades en la linea sino me quedan mal los precios.
			// Comento codigo de adempiere
			line.setQtyEntered(sLine.getQtyEntered());
			line.setQtyInvoiced(sLine.getMovementQty());
			line.setShipLine(sLine);
						
			/*line.setShipLine(sLine);
			if (sLine.sameOrderLineUOM())
				line.setQtyEntered(sLine.getQtyEntered());
			else
				line.setQtyEntered(sLine.getMovementQty());
			line.setQtyInvoiced(sLine.getMovementQty());
			*/
			
			// OpenUp. Gabriel Vila. 10/11/2010.
			// Me aseguro de poner bien la unidad de medida de venta en la linea de la invoice
			if (line.getC_OrderLine_ID() > 0) {
				MOrderLine oLine = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), get_TrxName());
				line.setC_UOM_ID(oLine.getC_UOM_ID()); 
			}
			else{
				line.setC_UOM_ID(sLine.getC_UOM_ID());
				line.setQtyEntered(sLine.getQtyEntered());
			}
			// Fin OpenUp.
			
			if (!line.save())
				throw new IllegalArgumentException("Cannot save Invoice Line");
		}
		// OpenUp. Nicolas Sarlabos. 23/06/2011. issue #646
		// Se guarda el ID de la factura en el env�o y se guarda el env�o
		ship.setC_Invoice_ID(invoice.getC_Invoice_ID());
		ship.saveEx();
		// Fin OpenUp.
		return invoice.getDocumentNo();
	}	//	InOutCreateInvoice

	/**
	 * 
	 * OpenUp. issue #3629
	 * InOutCreateInvoiceICN
	 * Descripcion : Metodo creado para que Factura de proveedor con OC discrimine las lineas por centro de costo/sectores,
	 * desde la ventana "Recepcion de Compra"
	 * @author INes Fernandez
	 * Fecha : 04/03/2015
	 */
	private String executePOActivity() {
		String message ="OK";
		
		try {
			MInOut ship = new MInOut (getCtx(), p_M_InOut_ID, get_TrxName());
			if (ship.get_ID() == 0)
				throw new IllegalArgumentException("Shipment not found");
			if (!MInOut.DOCSTATUS_Completed.equals(ship.getDocStatus()))
				throw new IllegalArgumentException("Shipment not completed");
			//ToDo: DISABLE Generar Factura BTN?
			
			MInvoice invoice = new MInvoice (ship, null);
			// Should not override pricelist for RMA
			if (p_M_PriceList_ID != 0 && ship.getM_RMA_ID() == 0)
				invoice.setM_PriceList_ID(p_M_PriceList_ID);
			if (p_InvoiceDocumentNo != null && p_InvoiceDocumentNo.length() > 0){
				
				invoice.setDocumentNo(p_InvoiceDocumentNo);
				
				// OpenUp. Gabriel Vila. 09/04/2014. Issue #1829
				// Seteo numero auxiliar de documento utilizado en las facturas de compras
				invoice.setDocumentNoAux(p_InvoiceDocumentNo);
				
				// Fin OpenUp. Issue #1829
			}
			
			MDocType docAux = MDocType.forValue(getCtx(), "invoicevendor", null);
			invoice.setC_DocTypeTarget_ID(docAux.get_ID());
			invoice.setC_DocType_ID(docAux.get_ID());
			invoice.setDateVendor(invoice.getDateInvoiced());
			invoice.LinesCreatedAutoFromPO = true;
			invoice.saveEx();
			MInOutLine[] shipLines = ship.getLines(false);
			for (int i = 0; i < shipLines.length; i++)
			{
				MInOutLine sLine = shipLines[i];
				MOrderLine orderLine = new MOrderLine(getCtx(), sLine.getC_OrderLine_ID(), get_TrxName());
				MOrder order =new MOrder(getCtx(), orderLine.getC_Order_ID(), get_TrxName());
				
				for (MOrderSection aux : order.getAllPOSection()){
					for(MOrderSectionLine sectionLineAux : aux.getAllPOSectionLines()){
						if(sectionLineAux.getM_Product_ID()== sLine.getM_Product_ID()){
							MInvoiceLine line = new MInvoiceLine(invoice);
							line.setC_Activity_ID_1(aux.getUY_POSection().getC_Activity_ID_1());
							
							// OpenUp. Gabriel Vila. 02/05/2012.
							// Me aseguro de setear bien las cantidades en la linea sino me quedan mal los precios.
							// Comento codigo de adempiere
							line.setQtyEntered(sLine.getQtyEntered());
							line.setQtyInvoiced(sLine.getMovementQty());
							line.setShipLine(sLine);
							
												
							// OpenUp. Gabriel Vila. 10/11/2010.
							// Me aseguro de poner bien la unidad de medida de venta en la linea de la invoice
							if (line.getC_OrderLine_ID() > 0) {
								MOrderLine oLine = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), get_TrxName());
								line.setC_UOM_ID(oLine.getC_UOM_ID()); 
							}
							else{
								line.setC_UOM_ID(sLine.getC_UOM_ID());
								line.setQtyEntered(sLine.getQtyEntered());
							}
							// Fin OpenUp.
							
							if (!line.save())
								throw new IllegalArgumentException("Cannot save Invoice Line");
						}
					}
				}			
			}
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return message;
		
	}
	
}	//	InOutCreateInvoice
