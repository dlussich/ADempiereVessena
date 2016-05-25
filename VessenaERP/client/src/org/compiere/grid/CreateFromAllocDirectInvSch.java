/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Feb 10, 2016
*/
package org.compiere.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.PO;
import org.compiere.model.X_C_Payment;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.openup.model.MAllocDirectPayment;

/**
 * org.compiere.grid - CreateFromAllocDirectInvSch
 * OpenUp Ltda. Issue #5036 
 * Description: Seleccion de vencimientos de documentos para afectacion directa 
 * @author gabriel - Feb 10, 2016
*/
public class CreateFromAllocDirectInvSch extends CreateFrom {

	/***
	 * Constructor.
	 * @param gridTab
	*/

	public CreateFromAllocDirectInvSch(GridTab gridTab) {
		super(gridTab);
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 */
	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle("Seleccion de Documentos a Afectar");
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#info()
	 */
	@Override
	public void info() {

	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#save(org.compiere.minigrid.IMiniTable, java.lang.String)
	 */
	@Override
	public boolean save(IMiniTable miniTable, String trxName) {
		
		int cPaymentID = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_C_Payment_ID)).intValue();
		BigDecimal totalAfectarPayments = Env.ZERO;
		boolean tengoSaldoPayments = false;

		MPayment hdr = new MPayment(Env.getCtx(), cPaymentID, null);

		// Verifico si tengo o no saldo de recibo a afectar
		if (hdr.getPayAmt().compareTo(Env.ZERO) > 0) {
			tengoSaldoPayments = true;
			totalAfectarPayments = hdr.getAmtToAllocate();
			if (totalAfectarPayments.compareTo(Env.ZERO) < 0) totalAfectarPayments = Env.ZERO;
		}

		//  Recorro documentos de grilla
		for (int i = 0; i < miniTable.getRowCount(); i++) {
			
			// Si este documento esta seleccionado
			if (((Boolean) miniTable.getValueAt(i, 0)).booleanValue()) {
				
				KeyNamePair docType = (KeyNamePair) miniTable.getValueAt(i,1);
				KeyNamePair recibo = (KeyNamePair) miniTable.getValueAt(i,2);
				KeyNamePair currency = (KeyNamePair) miniTable.getValueAt(i,4);
				KeyNamePair invsch = (KeyNamePair) miniTable.getValueAt(i,5);
				
				MAllocDirectPayment line = new MAllocDirectPayment(Env.getCtx(), 0, trxName);		
				line.setC_Payment_ID(cPaymentID);
				line.setC_DocType_ID(docType.getKey());
				line.setDocumentNo(recibo.getName());
				line.setC_Currency_ID(currency.getKey());
				line.setC_Invoice_ID(recibo.getKey());
				line.setC_InvoicePaySchedule_ID(invsch.getKey());
				line.setDueDate((Timestamp)miniTable.getValueAt(i,6));
				line.setdatedocument((Timestamp)miniTable.getValueAt(i,3));
				line.setamtdocument((BigDecimal)miniTable.getValueAt(i,7));
				line.setamtopen((BigDecimal)miniTable.getValueAt(i,8));
				line.setamtallocated((BigDecimal)miniTable.getValueAt(i,9));
				
				BigDecimal montoAfectar = Env.ZERO;
				MDocType doc = new MDocType(Env.getCtx(), docType.getKey(), null);
				if ((doc.getDocBaseType().equalsIgnoreCase("ARC")) || (doc.getDocBaseType().equalsIgnoreCase("APC"))){
					line.setsign(Env.ONE);
					montoAfectar = line.getamtallocated().negate();
				}					
				else{
					line.setsign(new BigDecimal(-1));
					montoAfectar = line.getamtallocated();
				}

				// Antes de guardar esta linea, tengo que verificar tope contra total a afectar en recibos
				if (tengoSaldoPayments) {
					// Si el monto a afectar de esta factura es mayor a lo que tengo para afectar de recibos
					if (montoAfectar.compareTo(totalAfectarPayments) > 0) {
						montoAfectar = totalAfectarPayments;
						totalAfectarPayments = Env.ZERO;
						line.setamtallocated(montoAfectar);

					} else {
						totalAfectarPayments = totalAfectarPayments.subtract(montoAfectar);

					}
				}

				line.saveEx(trxName);
			}
		} 
		return true;
	}

	protected Vector<Vector<Object>> getData(int adClientID, String isSOTrx, int cBPartnerID, 
			Timestamp dateTrx, int cCurrencyID, int cCurrencyID2, int cPaymentID,int cInvoiceID){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();

		
		String whereCurrency = ""; String whereTablaHdr = "";
		PO hdr = null;
		if(cPaymentID>0 && cInvoiceID==0){
			hdr = (MPayment) new MPayment(Env.getCtx(), cPaymentID, null);
		
		}else if(cPaymentID==0 && cInvoiceID>0){//si corresponde a una c_invoice (Nota de credito proveedro por descuento)
			hdr = (MInvoice )new MInvoice(Env.getCtx(), cInvoiceID, null);
		}	
		whereCurrency = " AND al.c_currency_id =" + cCurrencyID + " ";
		if(hdr.get_Value("C_Currency2_ID")!=null) 
				whereCurrency =  " AND (al.c_currency_id =" + cCurrencyID + " OR  al.c_currency_id ="+ hdr.get_ValueAsInt("C_Currency2_ID") +") ";
		if(cPaymentID>0){
			whereTablaHdr = " AND al.c_invoicepayschedule_id NOT IN (SELECT coalesce(ali.c_invoicepayschedule_id,0) FROM uy_allocdirectpayment ali WHERE ali.c_payment_id=" + cPaymentID + ") " ;

		}else if(cInvoiceID>0){
			whereTablaHdr = " AND al.c_invoicepayschedule_id NOT IN (SELECT coalesce(ali.c_invoicepayschedule_id,0) FROM uy_allocdirectcreditnote ali WHERE ali.C_Invoice_ID=" + cInvoiceID + ") " ;
			//Tengo que condiderar que no puedo seleccionar notas de crédito
			whereTablaHdr = " AND doc.DocBaseType NOT IN ('ARC','APC') " + whereTablaHdr ;
		}
//		String whereCurrency = " AND al.c_currency_id =" + cCurrencyID;
//		if (cCurrencyID2 > 0){
//			whereCurrency = " AND al.c_currency_id IN(" + cCurrencyID + "," + cCurrencyID2 + ") ";
//		}
		
		String sql=	" SELECT al.c_invoice_id as recibo_id, al.c_doctype_id, doc.printname, al.documentno, " +
						" al.dateinvoiced as recibo_date, al.c_currency_id, cur.description, " +
						" al.c_invoicepayschedule_id, al.duedate, invsch.literalquote, " +
						" al.amtinvoiced as recibo_total, " +
						" al.amtallocated, al.amtopen, coalesce(inv.POReference,'') as poreference " +
					" FROM alloc_invoiceamtopen_sch al " +
					" INNER JOIN c_doctype doc on al.c_doctype_id = doc.c_doctype_id " +
					" INNER JOIN c_currency cur on al.c_currency_id = cur.c_currency_id " +
					" INNER JOIN c_invoicepayschedule invsch on al.c_invoicepayschedule_id = invsch.c_invoicepayschedule_id " +
					" LEFT OUTER JOIN c_invoice inv on al.c_invoice_id = inv.c_invoice_id " +
					" WHERE al.issotrx=? " +
					" AND al.c_bpartner_id =? " +
					" AND al.dateinvoiced <=? " +
					whereCurrency +
					" AND al.amtopen > 0" + whereTablaHdr +
					//" AND al.c_invoicepayschedule_id NOT IN (SELECT coalesce(ali.c_invoicepayschedule_id,0) FROM uy_allocdirectpayment ali WHERE ali.c_payment_id=" + cPaymentID + ") " +
				    " UNION " +
				    " SELECT al.c_invoice_id as recibo_id, al.c_doctype_id, doc.printname, al.documentno, " +
				    	" al.dateinvoiced as recibo_date, al.c_currency_id, cur.description, " +
				    	" al.c_invoicepayschedule_id, al.duedate, invsch.literalquote, " +
				    	" al.amtinvoiced as recibo_total, " +
				    	" al.amtallocated, al.amtopen, coalesce(inv.POReference,'') as poreference " +
				    " FROM alloc_creditnoteamtopen_sch al " +
				    " INNER JOIN c_doctype doc on al.c_doctype_id = doc.c_doctype_id " +
				    " INNER JOIN c_currency cur on al.c_currency_id = cur.c_currency_id " +
				    " INNER JOIN c_invoicepayschedule invsch on al.c_invoicepayschedule_id = invsch.c_invoicepayschedule_id " +				    
				    " LEFT OUTER JOIN c_invoice inv on al.c_invoice_id = inv.c_invoice_id " +				    
				    " WHERE al.issotrx=? " +
				    " AND al.c_bpartner_id =? " +
				    " AND al.dateinvoiced <=? " +
				    whereCurrency +
				    " AND al.amtopen > 0" + 
				    whereTablaHdr;
				    //" AND al.c_invoicepayschedule_id NOT IN (SELECT coalesce(ali.c_invoicepayschedule_id,0) FROM uy_allocdirectpayment ali WHERE ali.c_payment_id=" + cPaymentID + ")";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setString(1, isSOTrx);
			pstmt.setInt(2, cBPartnerID);
			pstmt.setTimestamp(3, dateTrx);
			pstmt.setString(4, isSOTrx);
			pstmt.setInt(5, cBPartnerID);
			pstmt.setTimestamp(6, dateTrx);
			
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(8);
				line.add(new Boolean(false));
				line.add(new KeyNamePair(rs.getInt("c_doctype_id"),rs.getString("printname")));
				line.add(new KeyNamePair(rs.getInt("recibo_id"),rs.getString("documentno")));
				line.add(rs.getTimestamp("recibo_date"));
				line.add(new KeyNamePair(rs.getInt("c_currency_id"),rs.getString("description")));
				line.add(new KeyNamePair(rs.getInt("c_invoicepayschedule_id"),rs.getString("literalquote")));
				line.add(rs.getTimestamp("duedate"));
				line.add(rs.getBigDecimal("recibo_total"));
				line.add(rs.getBigDecimal("amtopen"));
				line.add(BigDecimal.ZERO);
				line.add(rs.getString("poreference"));
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return data;
	}

	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);      //  0 Selection
		miniTable.setColumnClass(1, String.class, true);    	//  1 C_DocType_ID
		miniTable.setColumnClass(2, String.class, true);    	//  2 ID Documento
		miniTable.setColumnClass(3, Timestamp.class, true);    	//  3 Fecha Documento
		miniTable.setColumnClass(4, String.class, true);    	//  4 C_Currency_ID
		miniTable.setColumnClass(5, String.class, true);    	//  5 ID Cuota
		miniTable.setColumnClass(6, Timestamp.class, true);    	//  6 Vencimiento Cuota
		miniTable.setColumnClass(7, BigDecimal.class, true);    //  5 Total Cuota
		miniTable.setColumnClass(8, BigDecimal.class, true);    //  6 Saldo Pendiente Cuota
		miniTable.setColumnClass(9, BigDecimal.class, false);   //  7 Monto a Afectar Cuota
		miniTable.setColumnClass(10, String.class, true);    	//  8 Referencia Invoice
	}
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(11);
		columnNames.add("Seleccionar");
		columnNames.add("Documento        ");
		columnNames.add("Numero    ");
		columnNames.add("Fecha    ");
		columnNames.add("Moneda     ");
		columnNames.add("Cuota     ");
		columnNames.add("Vencimiento     ");
		columnNames.add("Total Cuota ");
		columnNames.add("Saldo Pendiente ");
		columnNames.add("Total a Afectar ");
		columnNames.add("Referencia ");
		return columnNames;
	}
	
}
