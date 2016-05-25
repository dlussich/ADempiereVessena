/**
 * 
 */
package org.compiere.grid;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.PO;
import org.compiere.model.X_C_Invoice;
import org.compiere.model.X_C_Payment;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.openup.model.MAllocDirectCreditNote;
import org.openup.model.MAllocDirectPayment;

/**
 * @author OpenUp. Gabriel Vila. 28/10/2011.
 *
 */
public class CreateFromAllocDirectInvoice extends CreateFrom {

	/**
	 * @param gridTab
	 */
	public CreateFromAllocDirectInvoice(GridTab gridTab) {
		super(gridTab);
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 */
	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle("Seleccion de Facturas");
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#info()
	 */
	@Override
	public void info() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#save(org.compiere.minigrid.IMiniTable, java.lang.String)
	 */
	@Override
	public boolean save(IMiniTable miniTable, String trxName) {
		int cPaymentID = 0; int cInvoiceID = 0;
		//SBT 25/02/2016 Issue #5516 ahora hay que tener en cuenta desde que ventana estoy llamando el create from 
		
		int c_docType_id = ((Integer)getGridTab().getValue("C_DocType_ID")).intValue(); //Si es Mpayment
		if (c_docType_id==0) 
				c_docType_id = ((Integer)getGridTab().getValue("C_DocTypeTarget_ID")).intValue(); //Si es MInvoice

		MDocType docOrigen = new MDocType(Env.getCtx(), c_docType_id, null);
		if(docOrigen.getValue().equalsIgnoreCase("vendorncdesc")){
			cInvoiceID = ((Integer)getGridTab().getValue(X_C_Invoice.COLUMNNAME_C_Invoice_ID)).intValue(); //Se carga id de invoice actual
		}else{
			cPaymentID = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_C_Payment_ID)).intValue(); //Se carga el id de payment actual
		}
		
		//OpenUp Nicolas Sarlabos 18/01/2012
		BigDecimal totalAfectarPayments = Env.ZERO;
		boolean tengoSaldoPayment = false;

		
		if(cPaymentID>0){
			MPayment hdr = new MPayment(Env.getCtx(), cPaymentID, null);

			// Verifico si tengo o no saldo de recibo a afectar
			if (hdr.getPayAmt().compareTo(Env.ZERO) > 0) {
				tengoSaldoPayment = true;
				totalAfectarPayments = hdr.getAmtToAllocate();
				if (totalAfectarPayments.compareTo(Env.ZERO) < 0) totalAfectarPayments = Env.ZERO;
			}

			//  for all rows
			for (int i = 0; i < miniTable.getRowCount(); i++) {
				
				// For selected rows
				if (((Boolean) miniTable.getValueAt(i, 0)).booleanValue()) {
					
					KeyNamePair docType = (KeyNamePair) miniTable.getValueAt(i,1);
					KeyNamePair recibo = (KeyNamePair) miniTable.getValueAt(i,2);
					KeyNamePair currency = (KeyNamePair) miniTable.getValueAt(i,4);
					
					MAllocDirectPayment line = new MAllocDirectPayment(Env.getCtx(), 0, trxName);		
					line.setC_Payment_ID(cPaymentID);
					line.setC_DocType_ID(docType.getKey());
					line.setDocumentNo(recibo.getName());
					line.setC_Currency_ID(currency.getKey());
					line.setC_Invoice_ID(recibo.getKey());
					line.setdatedocument((Timestamp)miniTable.getValueAt(i,3));
					line.setamtdocument((BigDecimal)miniTable.getValueAt(i,5));
					line.setamtopen((BigDecimal)miniTable.getValueAt(i,6));
					line.setamtallocated((BigDecimal)miniTable.getValueAt(i,7));
					
					BigDecimal montoAfectar = Env.ZERO;
					MDocType doc = new MDocType(Env.getCtx(), docType.getKey(), null);
					//Si es nota de credito cliente (ARC) o Nota de Credito (APC) el signo es positivo 
					if ((doc.getDocBaseType().equalsIgnoreCase("ARC")) || (doc.getDocBaseType().equalsIgnoreCase("APC"))){
						line.setsign(Env.ONE);
						montoAfectar = line.getamtallocated().negate();
					}					
					else{
						line.setsign(new BigDecimal(-1));
						montoAfectar = line.getamtallocated();
					}

					// Antes de guardar esta linea, tengo que verificar tope contra total a afectar en recibos
					if (tengoSaldoPayment) {
						if(line.getC_Currency_ID()!=hdr.getC_Currency_ID()){ // #5413 Contemplo el caso de que sea multimoneda
							montoAfectar = montoAfectar.multiply(hdr.getCurrencyRate()).setScale(2, RoundingMode.HALF_UP);
							// Si el monto a afectar de esta factura es mayor a lo que tengo para afectar de recibos
							if(montoAfectar.compareTo(totalAfectarPayments) > 0){
								Timestamp fecha = hdr.getDateTrx();
								//Obtengo la tasa "opuesta"
								BigDecimal dividerate = MConversionRate.getDivideRate(hdr.get_ValueAsInt("C_Currency2_ID"), hdr.getC_Currency_ID(), fecha, 0, 
														hdr.getAD_Client_ID(),hdr.getAD_Org_ID());
								//Realizo el cálculo para indicar el monto a afectar en la línea.
								montoAfectar = totalAfectarPayments.multiply(dividerate).setScale(2, RoundingMode.HALF_UP);
								totalAfectarPayments = Env.ZERO;
								line.setamtallocated(montoAfectar.setScale(2, RoundingMode.HALF_UP));
							}else{
								totalAfectarPayments = totalAfectarPayments.subtract(montoAfectar.multiply(hdr.getCurrencyRate()));
							}
							
						}else{ // #5413 Contemplo el caso de que sea no sea multimoneda
							// Si el monto a afectar de esta factura es mayor a lo que tengo para afectar de recibos
							if (montoAfectar.compareTo(totalAfectarPayments) > 0) {
								montoAfectar = totalAfectarPayments;
								totalAfectarPayments = Env.ZERO;
								line.setamtallocated(montoAfectar);
							} else {
								totalAfectarPayments = totalAfectarPayments.subtract(montoAfectar);

							}
						}
						
					}

					line.saveEx(trxName);
				}
			} 
			//fin OpenUp Nicolas Sarlabos 18/01/2012
			
		}else if (cInvoiceID>0){ //SBT 25/02/2016 Issue # 5516 Se agrega poara el caso de que se esté llamando dede ventana con cabezal en la MInvoice
			MInvoice hdr = new MInvoice(Env.getCtx(), cInvoiceID, trxName);

			// Verifico si tengo o no saldo en la NC a afectar
			//if (hdr.getPayAmt().compareTo(Env.ZERO) > 0) {
			if (hdr.getGrandTotal().compareTo(Env.ZERO) > 0) {
				tengoSaldoPayment = true;
				totalAfectarPayments = (BigDecimal) hdr.get_Value("AmtToAllocate");//getAmtToAllocate();
				if (totalAfectarPayments.compareTo(Env.ZERO) < 0) totalAfectarPayments = Env.ZERO;
			}

			//  for all rows
			for (int i = 0; i < miniTable.getRowCount(); i++) {
				
				// For selected rows
				if (((Boolean) miniTable.getValueAt(i, 0)).booleanValue()) {
					
					KeyNamePair docType = (KeyNamePair) miniTable.getValueAt(i,1);
					KeyNamePair recibo = (KeyNamePair) miniTable.getValueAt(i,2);
					KeyNamePair currency = (KeyNamePair) miniTable.getValueAt(i,4);
					
					MAllocDirectCreditNote line = new MAllocDirectCreditNote(Env.getCtx(), 0, trxName);		
					line.setC_Invoice_ID(cInvoiceID);
					line.setC_DocType_ID(docType.getKey());
					line.setDocumentNo(recibo.getName());
					line.setC_Currency_ID(currency.getKey());
					line.setC_Invoice2_ID(recibo.getKey());
					line.setdatedocument((Timestamp)miniTable.getValueAt(i,3));
					line.setamtdocument((BigDecimal)miniTable.getValueAt(i,5));
					line.setamtopen((BigDecimal)miniTable.getValueAt(i,6));
					line.setamtallocated((BigDecimal)miniTable.getValueAt(i,7));
					
					BigDecimal montoAfectar = Env.ZERO;
					MDocType doc = new MDocType(Env.getCtx(), docType.getKey(), null);
					//Si es nota de credito cliente (ARC) o Nota de Credito (APC) el signo es positivo 
					if ((doc.getDocBaseType().equalsIgnoreCase("ARC")) || (doc.getDocBaseType().equalsIgnoreCase("APC"))){
						line.setsign(Env.ONE);
						montoAfectar = line.getamtallocated().negate();
					}					
					else{
						line.setsign(new BigDecimal(-1));
						montoAfectar = line.getamtallocated();
					}

					// Antes de guardar esta linea, tengo que verificar tope contra total a afectar en recibos
					if (tengoSaldoPayment) {
						if(line.getC_Currency_ID()!=hdr.getC_Currency_ID()){ // #5413 Contemplo el caso de que sea multimoneda
							montoAfectar = montoAfectar.multiply(hdr.getCurrencyRate()).setScale(2, RoundingMode.HALF_UP);
							// Si el monto a afectar de esta factura es mayor a lo que tengo para afectar de recibos
							if(montoAfectar.compareTo(totalAfectarPayments) > 0){
								Timestamp fecha = hdr.getDateInvoiced();
								//Obtengo la tasa "opuesta"
								BigDecimal dividerate = MConversionRate.getDivideRate(hdr.get_ValueAsInt("C_Currency2_ID"), hdr.getC_Currency_ID(), fecha, 0, 
														hdr.getAD_Client_ID(),hdr.getAD_Org_ID());
								//Realizo el cálculo para indicar el monto a afectar en la línea.
								montoAfectar = totalAfectarPayments.multiply(dividerate).setScale(2, RoundingMode.HALF_UP);
								totalAfectarPayments = Env.ZERO;
								line.setamtallocated(montoAfectar.setScale(2, RoundingMode.HALF_UP));
							}else{
								totalAfectarPayments = totalAfectarPayments.subtract(montoAfectar.multiply(hdr.getCurrencyRate()));
							}
							
						}else{ // #5413 Contemplo el caso de que sea no sea multimoneda
							// Si el monto a afectar de esta factura es mayor a lo que tengo para afectar de recibos
							if (montoAfectar.compareTo(totalAfectarPayments) > 0) {
								montoAfectar = totalAfectarPayments;
								totalAfectarPayments = Env.ZERO;
								line.setamtallocated(montoAfectar);

							} else {
								totalAfectarPayments = totalAfectarPayments.subtract(montoAfectar);

							}
						}
						
					}

					line.saveEx(trxName);
				}
			} 
			//fin OpenUp Nicolas Sarlabos 18/01/2012
		}
		
		
		
		return true;
	}

	protected Vector<Vector<Object>> getData(int adClientID, String isSOTrx, int cBPartnerID, Timestamp dateTrx, int cCurrencyID, int cPaymentID,int cInvoiceID){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		//SBT 11/02/2016 Issue #5413 Para contemplar casos de recibos multimuneda
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
			whereTablaHdr = " AND al.c_invoice_id NOT IN (SELECT coalesce(ali.c_invoice_id,0) FROM uy_allocdirectpayment ali WHERE ali.c_payment_id=" + cPaymentID + ") " ;

		}else if(cInvoiceID>0){
			whereTablaHdr = " AND al.c_invoice_id NOT IN (SELECT coalesce(ali.c_invoice2_id,0) FROM uy_allocdirectcreditnote ali WHERE ali.C_Invoice_ID=" + cInvoiceID + ") " ;
			//Tengo que condiderar que no puedo seleccionar notas de crédito
			whereTablaHdr = " AND doc.DocBaseType NOT IN ('ARC','APC') " + whereTablaHdr ;
			
			/*MDocType doc = new MDocType(Env.getCtx(), docType.getKey(), null);
			/Si es nota de credito cliente (ARC) o Nota de Credito (APC) el signo es positivo 
			if ((doc.getDocBaseType().equalsIgnoreCase("ARC")) || (doc.getDocBaseType().equalsIgnoreCase("APC"))){
					line.setsign(Env.ONE);
					montoAfectar = line.getamtallocated().negate();
			}					
			else{
				line.setsign(new BigDecimal(-1));
				montoAfectar = line.getamtallocated();
			}*/
		}
		
		
		
		String sql=	" SELECT al.c_invoice_id as recibo_id, al.c_doctype_id, doc.printname, al.documentno, " +
					" al.dateinvoiced as recibo_date, al.c_currency_id, cur.description, al.amtinvoiced as recibo_total, " +
					" al.amtallocated, al.amtopen, coalesce(inv.POReference,'') as poreference " +
				" FROM alloc_invoiceamtopen al " +
				" INNER JOIN c_doctype doc on al.c_doctype_id = doc.c_doctype_id " +
				" INNER JOIN c_currency cur on al.c_currency_id = cur.c_currency_id " +
				" LEFT OUTER JOIN c_invoice inv on al.c_invoice_id = inv.c_invoice_id " +
				
				/*
				" WHERE al.ad_client_id =? " +
				" AND al.issotrx=? " +
				*/
				
				" WHERE al.issotrx=? " +
				
				" AND al.c_bpartner_id =? " +
				" AND al.dateinvoiced <=? " +
					whereCurrency +
				//" AND al.c_currency_id =" + cCurrencyID + // se comenta para contemplar casos de multimoneda Issue #5413
				" AND al.amtopen > 0" +
				whereTablaHdr +
				//" AND al.c_invoice_id NOT IN (SELECT coalesce(ali.c_invoice_id,0) FROM uy_allocdirectpayment ali WHERE ali.c_payment_id=" + cPaymentID + ") " +
			    " UNION " +
			    " SELECT al.c_invoice_id as recibo_id, al.c_doctype_id, doc.printname, al.documentno, " +
			    	" al.dateinvoiced as recibo_date, al.c_currency_id, cur.description, al.amtinvoiced as recibo_total, " +
			    	" al.amtallocated, al.amtopen, coalesce(inv.POReference,'') as poreference " +
			    " FROM alloc_creditnoteamtopen al " +
			    " INNER JOIN c_doctype doc on al.c_doctype_id = doc.c_doctype_id " +
			    " INNER JOIN c_currency cur on al.c_currency_id = cur.c_currency_id " +
			    " LEFT OUTER JOIN c_invoice inv on al.c_invoice_id = inv.c_invoice_id " +				    

			    /*
			    " WHERE al.ad_client_id =? " +
			    " AND al.issotrx=? " +
			    */
			    " WHERE al.issotrx=? " +
			    
			    " AND al.c_bpartner_id =? " +
			    " AND al.dateinvoiced <=? " +
			    " AND al.c_currency_id =" + cCurrencyID +
			    " AND al.amtopen > 0" +
			    	whereTablaHdr ;
			  //  " AND al.c_invoice_id NOT IN (SELECT coalesce(ali.c_invoice_id,0) FROM uy_allocdirectpayment ali WHERE ali.c_payment_id=" + cPaymentID + ")";
			  	
		
		
			
		

		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			//pstmt.setInt(1, adClientID);
			pstmt.setString(1, isSOTrx);
			pstmt.setInt(2, cBPartnerID);
			pstmt.setTimestamp(3, dateTrx);
			//pstmt.setInt(5, adClientID);
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
		miniTable.setColumnClass(2, String.class, true);    	//  2 Recibo_ID
		miniTable.setColumnClass(3, Timestamp.class, true);    	//  3 Recibo_Date
		miniTable.setColumnClass(4, String.class, true);    	//  4 C_Currency_ID
		miniTable.setColumnClass(5, BigDecimal.class, true);    //  5 Recibo_Total
		miniTable.setColumnClass(6, BigDecimal.class, true);    //  6 AmtOpen
		miniTable.setColumnClass(7, BigDecimal.class, false);   //  7 Monto a Afectar
		miniTable.setColumnClass(8, String.class, true);    	//  8 Referencia Invoice
	}
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(9);
		columnNames.add("Seleccionar");
		columnNames.add("Documento        ");
		columnNames.add("Numero    ");
		columnNames.add("Fecha    ");
		columnNames.add("Moneda     ");
		columnNames.add("Total Documento ");
		columnNames.add("Saldo Pendiente ");
		columnNames.add("Total a Afectar ");
		columnNames.add("Referencia ");
		return columnNames;
	}

}
