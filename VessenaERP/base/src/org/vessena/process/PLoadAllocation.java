/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Set;

import org.compiere.model.MAllocationHdr;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.process.DocumentEngine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.openup.model.MAllocation;
import org.openup.model.MAllocationInvoice;
import org.openup.model.MAllocationPayment;

/**
 * @author Hp
 *
 */
public class PLoadAllocation extends SvrProcess {

	public PLoadAllocation() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			this.getProcessInfo().getWaiting().setText("Iniciando...");
			
			sql = " select hdr.c_allocationhdr_id, hdr.documentno, hdr.datetrx, hdr.c_bpartner_id, line.c_payment_id, " +
				  " line.c_invoice_id, line.amount " +
				  " from c_allocationhdr hdr " +
				  " inner join c_allocationline line on hdr.c_allocationhdr_id = line.c_allocationhdr_id " +
				  " where hdr.docstatus in('CO','CL') " +
				  " and line.c_payment_id = 1001191 " +
				  " order by line.c_allocationhdr_id, line.c_payment_id ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
		
			rs = pstmt.executeQuery ();
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			int cAllocationHrdID = 0, cAllocationHrdIDAux = 0, cPaymentID = 0, cPaymentIDaux = 0, cInvoiceID = 0;
			BigDecimal amtInvoices = Env.ZERO, amtCreditNotes = Env.ZERO, amtLine = Env.ZERO;
			HashMap<Integer, BigDecimal> hmInvoices = new HashMap<Integer, BigDecimal>();
			String ayuda = "";
			
			// Corte de control cabezal de afectacion y recibo
			while (rs.next()){

				ayuda = rowCount++ + " de " + totalRowCount;
				this.getProcessInfo().getWaiting().setText(ayuda);
				
				cAllocationHrdIDAux = rs.getInt("c_allocationhdr_id");
				cPaymentIDaux = rs.getInt("c_payment_id");

				// Si hay cambio de afectacion o recibo
				if ((cAllocationHrdIDAux != cAllocationHrdID) || ((cPaymentIDaux != cPaymentID) && (cPaymentIDaux > 0))){

					// Save afectacion en nueva estructura
					if (cPaymentID > 0) this.saveAfectacion(cAllocationHrdID, cPaymentID, amtInvoices, amtCreditNotes, hmInvoices, ayuda);

					cAllocationHrdID = cAllocationHrdIDAux;
					cPaymentID = cPaymentIDaux;
					
					amtInvoices = Env.ZERO;
					amtCreditNotes = Env.ZERO;
					hmInvoices = new HashMap<Integer, BigDecimal>();
				}

				/*if (rs.getInt("c_payment_id") > 0){
					if ((cPaymentID > 0) && (rs.getInt("c_payment_id") != cPaymentID)){
						throw new Exception("Afectacion : " + rs.getString("documentno") + " con mas de un id de recibo.");
					}
					cPaymentID = rs.getInt("c_payment_id");
				}*/
				
				cPaymentID = cPaymentIDaux;
				
				amtLine = Env.ZERO;
				if (rs.getBigDecimal("amount") != null) amtLine = rs.getBigDecimal("amount");
				if (amtLine.compareTo(Env.ZERO) < 0) amtLine = amtLine.negate();
			
				if (rs.getInt("c_invoice_id") <= 0)
					throw new Exception("Afectacion : " + rs.getString("documentno") + " con id de invoice nulo.");
				
				cInvoiceID = rs.getInt("c_invoice_id");
				
				if (amtLine.compareTo(Env.ZERO) > 0){

					if (hmInvoices.containsKey((Integer)cInvoiceID)) 
						hmInvoices.put((Integer)cInvoiceID, hmInvoices.get((Integer)cInvoiceID).add(amtLine));
					else
						hmInvoices.put((Integer)cInvoiceID, amtLine);

					MInvoice invoice = new MInvoice(getCtx(), cInvoiceID, null);
					MDocType docInv = new MDocType(getCtx(), invoice.getC_DocTypeTarget_ID(), null);
					
					if (docInv.getAllocationBehaviour().equalsIgnoreCase("INV")){
						
						// En afectaciones viejas mal hechas me traia un monto superior al disponible de la factura, corrigjo
						BigDecimal amtOpenInvoice = DB.getSQLValueBD(null, "SELECT amtopen from alloc_invoiceamtopen where c_invoice_id =" + invoice.getC_Invoice_ID(), new Object[0]);
						if (amtOpenInvoice.compareTo(amtLine) < 0) 
							amtLine = amtOpenInvoice;
						
						amtInvoices = amtInvoices.add(amtLine);
					}
					if (docInv.getAllocationBehaviour().equalsIgnoreCase("PAY")) amtCreditNotes = amtCreditNotes.add(amtLine);
				}
				
			}
			
			// Save afectacion en nueva estructura
			if (cPaymentID > 0) this.saveAfectacion(cAllocationHrdID, cPaymentID, amtInvoices, amtCreditNotes, hmInvoices, ayuda);
			
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return "OK";
	}

	/**
	 * Save de afectacion antigua en nuevas estructuras.
	 * @param cAllocationHrdID
	 * @param cPaymentID
	 * @param amtPayment
	 * @param hmInvoices
	 * @throws Exception
	 */
	private void saveAfectacion(int cAllocationHrdID, int cPaymentID, BigDecimal amtInvoices, BigDecimal amtCreditNotes, 
								HashMap<Integer, BigDecimal> hmInvoices, String ayuda) throws Exception{

		Trx trans = null;
		
		try{	
		
			if (cPaymentID <= 0) return;
			if (cAllocationHrdID <= 0) return;
			if (amtInvoices.compareTo(Env.ZERO) <=0 ) return;
			if (hmInvoices.values().size() <= 0) return;
			
			// Genero nueva transaccion
			String trxNameAux = Trx.createTrxName();
			trans = Trx.get(trxNameAux, true);
			
			// Obtengo modelo de afectacion antigua
			MAllocationHdr allocHdr = new MAllocationHdr(getCtx(), cAllocationHrdID, trxNameAux);

			if (allocHdr.get_ID() <= 0){
				trans.rollback();
				return;
			}
			
			// Obtengo modelo del recibo
			MPayment payment = new MPayment(getCtx(), cPaymentID, trxNameAux);
			
			if (payment.get_ID() <= 0){
				trans.rollback();
				return;
			}
			
			BigDecimal amtPayment = payment.getPayAmt();
			BigDecimal amtOpenPayment = DB.getSQLValueBD(trxNameAux, "SELECT amtopen from alloc_paymentamtopen where c_payment_id =" + payment.getC_Payment_ID(), new Object[0]);
			
			if (amtOpenPayment.compareTo(Env.ZERO) <= 0){
				trans.rollback();
				return;
			}
			
			int cDocTpeID = 0;
			String sql = "";
			if (!payment.isReceipt())
				sql = " SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='ALP'";
			else
				sql = " SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='ALR'";
			
			cDocTpeID = DB.getSQLValue(null, sql);
			
			if (amtPayment.compareTo(Env.ZERO) < 0) amtPayment = amtPayment.negate();
			
			// Save cabezal afectacion
			MAllocation allocation = new MAllocation(getCtx(), 0, trxNameAux);
			allocation.setC_DocType_ID(cDocTpeID);
			allocation.setC_BPartner_ID(allocHdr.getC_BPartner_ID());
			allocation.setDateTrx(allocHdr.getDateTrx());
			allocation.setDateAcct(allocHdr.getDateAcct());
			allocation.setIsSOTrx(payment.isReceipt());
			allocation.setIsManual(true);
			allocation.setDescription("Carga Inicial. Documento antiguo = " + allocHdr.getDocumentNo());
			allocation.setPosted(true);
			allocation.setDivideRate(Env.ONE);
			allocation.setC_Currency_ID(allocHdr.getC_Currency_ID());
			allocation.setamtpayallocated(amtInvoices);
			allocation.setamtinvallocated(amtInvoices);
			allocation.saveEx();
			
			// Save recibo			
			MAllocationPayment alPayment = new MAllocationPayment(getCtx(), 0, trxNameAux);
			alPayment.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
			alPayment.setC_DocType_ID(payment.getC_DocType_ID());
			alPayment.setDocumentNo(payment.getDocumentNo());
			alPayment.setC_Currency_ID(payment.getC_Currency_ID());
			alPayment.setC_Payment_ID(payment.getC_Payment_ID());
			alPayment.setamtdocument(amtPayment);
			if (alPayment.getamtdocument().compareTo(Env.ZERO) < 0) alPayment.setamtdocument(alPayment.getamtdocument().negate());
			if (amtOpenPayment.compareTo(amtInvoices) < 0) 
				alPayment.setamtallocated(amtOpenPayment);
			else
				alPayment.setamtallocated(amtInvoices);

			alPayment.setamtopen(amtOpenPayment); // Este es solo para mostrar al usuario en la ventana, no es el saldo pendiente real.
			alPayment.setdatedocument(payment.getDateTrx());
			alPayment.saveEx();
			
			// Luego de afectar el recibo, resto lo afectado del monto de facturas que tengo para afectacion
			amtInvoices = amtInvoices.subtract(alPayment.getamtallocated());
			
			// Save invoices y notas de credito (notas de credito se guardan como payments)
			Set<Integer> keys = hmInvoices.keySet();
			for (Integer cinvoiceID : keys) {
				MInvoice invoice = new MInvoice(getCtx(), cinvoiceID.intValue(), trxNameAux);
				MDocType docInv = new MDocType(getCtx(), invoice.getC_DocTypeTarget_ID(), null);
				BigDecimal amtInvoice = hmInvoices.get(cinvoiceID);				
				if (docInv.getAllocationBehaviour().equalsIgnoreCase("INV")){
					BigDecimal amtOpenInvoice = DB.getSQLValueBD(trxNameAux, "SELECT amtopen from alloc_invoiceamtopen where c_invoice_id =" + invoice.getC_Invoice_ID(), new Object[0]);
					MAllocationInvoice alInvoice = new MAllocationInvoice(getCtx(), 0, trxNameAux);
					alInvoice.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
					alInvoice.setC_DocType_ID(invoice.getC_DocType_ID());
					alInvoice.setDocumentNo(invoice.getDocumentNo());
					alInvoice.setC_Currency_ID(invoice.getC_Currency_ID());
					alInvoice.setC_Invoice_ID(invoice.getC_Invoice_ID());
					alInvoice.setamtdocument(invoice.getGrandTotal());
					if (alInvoice.getamtdocument().compareTo(Env.ZERO) < 0) alInvoice.setamtdocument(alInvoice.getamtdocument().negate());
					if (amtOpenInvoice.compareTo(amtInvoice) < 0) 
						alInvoice.setamtallocated(amtOpenInvoice);
					else
						alInvoice.setamtallocated(amtInvoice);
					alInvoice.setamtopen(amtOpenInvoice); // Este es solo para mostrar al usuario en la ventana, no es el saldo pendiente real.
					alInvoice.setdatedocument(invoice.getDateInvoiced());
					alInvoice.saveEx();
				}
				else{
					BigDecimal amtOpenInvoice = DB.getSQLValueBD(trxNameAux, "SELECT amtopen from alloc_creditnoteamtopen where c_invoice_id =" + invoice.getC_Invoice_ID(), new Object[0]);
					MAllocationPayment alPaymentNC = new MAllocationPayment(getCtx(), 0, trxNameAux);
					alPaymentNC.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
					alPaymentNC.setC_DocType_ID(invoice.getC_DocType_ID());
					alPaymentNC.setDocumentNo(invoice.getDocumentNo());
					alPaymentNC.setC_Currency_ID(invoice.getC_Currency_ID());
					alPaymentNC.setC_Invoice_ID(invoice.getC_Invoice_ID());
					alPaymentNC.setamtdocument(invoice.getGrandTotal());
					if (alPaymentNC.getamtdocument().compareTo(Env.ZERO) < 0) alPaymentNC.setamtdocument(alPaymentNC.getamtdocument().negate());
					if (amtOpenInvoice.compareTo(amtInvoices) < 0) 
						alPaymentNC.setamtallocated(amtOpenInvoice);
					else
						alPaymentNC.setamtallocated(amtInvoices);
					alPaymentNC.setamtopen(amtOpenInvoice); // Este es solo para mostrar al usuario en la ventana, no es el saldo pendiente real.
					alPaymentNC.setdatedocument(invoice.getDateInvoiced());
					alPaymentNC.saveEx();
					
					// Luego de afectar la nota de credito, resto lo afectado del monto de facturas que tengo para afectacion
					amtInvoices = amtInvoices.subtract(alPaymentNC.getamtallocated());

					
				}
			}
			
			// Finalmente completo esta nueva afectacion
			if (!allocation.processIt(DocumentEngine.ACTION_Complete)){
				if (trans!=null){
					trans.rollback();
					trans = null;
				}
				throw new Exception("Afectacion vieja : " +  allocHdr.getDocumentNo() + " - " + allocation.getProcessMsg());
			}
			else{
				allocation.saveEx();
			}
		
			trans.close();
			
		}
		catch (Exception e){
			if (trans!=null){
				trans.rollback();
			}
			throw e;
		}
	}
}
