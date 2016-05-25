/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 14/05/2013
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
import org.compiere.model.MBankAccount;
import org.compiere.model.X_C_Invoice;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.openup.model.MInvoiceCashPayment;
import org.openup.model.MMediosPago;

/**
 * org.compiere.grid - CreateFromInvCashPay
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 14/05/2013
 * @see
 */
public class CreateFromInvCashPay extends CreateFrom {

	int cInvoiceId = 0;
	
	/**
	 * Constructor.
	 * @param gridTab
	 */
	public CreateFromInvCashPay(GridTab gridTab) {
		super(gridTab);
		log.info(gridTab.toString());
	}


	@Override
	public boolean dynInit() throws Exception {

		log.config("");
		setTitle("Seleccion de Medios de Pago");

		// Guardo id del comprobante del cabezal 
		this.cInvoiceId = ((Integer)getGridTab().getValue(X_C_Invoice.COLUMNNAME_C_Invoice_ID)).intValue();
		
		return true;
	}

	@Override
	public void info() {
	}

	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);
		miniTable.setColumnClass(1, BigDecimal.class, true);
		miniTable.setColumnClass(2, Timestamp.class, true);
		miniTable.setColumnClass(3, String.class, true);
		miniTable.setColumnClass(4, String.class, true);
		miniTable.setColumnClass(5, Timestamp.class, true);
		miniTable.setColumnClass(6, String.class, true);
		miniTable.setColumnClass(7, String.class, true);
		miniTable.setColumnClass(8, Integer.class, true);
		//  Table UI
		miniTable.autoSize();
	}
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(6);
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add("Importe");
		columnNames.add(Msg.translate(Env.getCtx(), "Fecha Emision"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_DocType_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "Checkno"));
		columnNames.add(Msg.translate(Env.getCtx(), "DueDate"));
		columnNames.add(Msg.translate(Env.getCtx(), "Accountno"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_Currency_ID"));
		columnNames.add("ID MedioPago");

		return columnNames;
	}

	
	protected Vector<Vector<Object>> loadPayments (Object bPartner, int currencyID, 
			Object bankAccount, Object checkNo, Object dateFrom, Object dateTo)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();

		String sql = "SELECT mp.DateTrx,mp.UY_MediosPago_ID, mp.c_bankaccount_id, bank.description as bankacctname, " +
						"doc.docbasetype, mp.checkno, mp.duedate, mp.C_Currency_ID,curr.ISO_Code, mp.payamt " +
					 " FROM UY_MediosPago mp INNER JOIN C_DocType doc ON (mp.C_DocType_ID=doc.C_DocType_ID) " +
					 	" INNER JOIN C_BankAccount bankacct ON (mp.C_BankAccount_ID=bankacct.C_BankAccount_ID) " +
					 	" INNER JOIN C_Bank bank ON (bankacct.C_Bank_ID=bank.C_Bank_ID) " +
					 	" INNER JOIN C_Currency curr ON (mp.C_Currency_ID=curr.C_Currency_ID) ";
					 	//" inner join uy_paymentrule rule on bankacct.uy_paymentrule_id = rule.uy_paymentrule_id ";					 	
	
		sql = sql + getSQLWhere(bankAccount, checkNo, bPartner, dateFrom, dateTo, currencyID) + " ORDER BY mp.DateTrx, mp.UY_MediosPago_ID ASC";
		
		log.config(sql);
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(6);
				line.add(new Boolean(false));       
				line.add(rs.getBigDecimal("payamt"));
				line.add(rs.getTimestamp("DateTrx"));       
				line.add(rs.getString("docbasetype"));
				line.add(rs.getString("checkno"));
				line.add(rs.getTimestamp("duedate"));
				KeyNamePair bank = new KeyNamePair(rs.getInt("c_bankaccount_id"), rs.getString("bankacctname"));
				line.add(bank);
				KeyNamePair currency = new KeyNamePair(rs.getInt("C_Currency_ID"), rs.getString("ISO_Code"));
				line.add(currency);
				line.add(rs.getInt("UY_MediosPago_ID"));                       
				data.add(line);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		return data;
	}	
	
	@Override
	public boolean save(IMiniTable miniTable, String trxName) {

		//  Lines
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{
				// ID de Medio de pago
				int medioPagoID = (Integer)miniTable.getValueAt(i, 8);
				MMediosPago model = new MMediosPago (Env.getCtx(), medioPagoID, trxName);
				MBankAccount bankAcct = new MBankAccount(Env.getCtx(), model.getC_BankAccount_ID(), trxName);

				// Nueva linea de pago contado
				MInvoiceCashPayment lineaPago = new MInvoiceCashPayment(Env.getCtx(), 0, trxName);

				lineaPago.setnrodoc(model.getCheckNo());
				lineaPago.setTenderType(bankAcct.get_ValueAsString("TenderType"));
				lineaPago.setDateFrom(model.getDateTrx());
				lineaPago.setDueDate(model.getDueDate());
				lineaPago.setAmount(model.getPayAmt());
				lineaPago.setC_BankAccount_ID(model.getC_BankAccount_ID());
				lineaPago.setC_Invoice_ID(this.cInvoiceId);
				lineaPago.setUY_MediosPago_ID(medioPagoID);
				lineaPago.setUY_PaymentRule_ID(model.getUY_PaymentRule_ID());
				lineaPago.setC_Currency_ID(model.getC_Currency_ID());
				lineaPago.saveEx();
				
			}   //   if selected
		}   //  for all rows
		return true;
	}

	public String getSQLWhere(Object BankAccount,Object CheckNo, Object BPartner, Object DateFrom, Object DateTo, int cCurrencyID)
	{
		Integer bPartnerID = (Integer) BPartner;
		
		StringBuffer sql = new StringBuffer(" WHERE ((mp.C_BPartner_ID =" + bPartnerID.intValue() + ") OR ((tipomp='TER') AND (estado='CAR'))) " +
				 //" AND mp.C_Currency_ID =" + cCurrencyID + 
				 " AND mp.DocStatus ='CO' " +
				 " AND mp.uy_linepayment_id is null" +
				 " AND mp.PayAmt!=0" + 
				 " AND mp.estado IN ('EMI' ,'CAR')"+ 

				 // OpenUp. Gabriel Vila. 28/02/2013.
				 // Cambio tendertype por tipo de forma de pago. 
				 //" AND rule.paymentruletype='CR' " + 
				 // AND bankacct.tendertype in('K','J','F','I') "  
				 
				 " AND doc.docbasetype='CHQ'");
		
		sql.append(" AND mp.uy_mediospago_id NOT IN (SELECT coalesce(lp.uy_mediospago_id,0) FROM uy_invoicecashpayment lp WHERE lp.c_invoice_id=" + this.cInvoiceId + ") ");
		
		if (BankAccount != null){
			Integer bAccountID = (Integer) BankAccount;
			sql.append(" AND mp.C_BankAccount_ID = " + bAccountID.intValue());
		}
	    
		if (CheckNo!= null){
			if (!CheckNo.toString().equalsIgnoreCase("")){
				sql.append(" AND UPPER(mp.CheckNo) LIKE '" + CheckNo.toString() + "'");	
			}
		}
			

		if (DateFrom != null || DateTo != null)
		{
			Timestamp from = (Timestamp) DateFrom;
			Timestamp to = (Timestamp) DateTo;
			if (from == null && to != null)
				sql.append(" AND TRUNC(mp.DateTrx) <= '" + to + "'");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(mp.DateTrx) >= '" + from + "'");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(mp.DateTrx) BETWEEN '" + from + "' AND '" + to + "'");
		}

		log.fine(sql.toString());
		return sql.toString();
	}	//	getSQLWhere
	
}
