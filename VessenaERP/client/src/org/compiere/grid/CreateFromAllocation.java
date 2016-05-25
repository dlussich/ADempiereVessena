package org.compiere.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MLocator;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

/***
 * Create lines for allocation from invoices
 * @author FL 30/07/2010.
 * @version 
 */
public class CreateFromAllocation extends CreateFrom {
	
	// TODO: used just to review
	private int defaultLocator_ID=0;


	//public MBankAccount bankAccount;
	
	/*
	 * Constructor por defecto.
	 */
	public CreateFromAllocation(GridTab gridTab) {
		super(gridTab);
		log.info(gridTab.toString());
	}

	/**
	 *  Dynamic Init
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle(Msg.translate(Env.getCtx(), "C_AllocationHdr_ID") + " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));
		return true;
	}

	@Override
	public void info() {
		// TODO Auto-generated method stub
	}

	/**
	 *  Save Statement - Insert Data
	 *  @return true if saved
	 */
	public boolean save(IMiniTable miniTable, String trxName) {
		
		// Get header ID and  payment ID
		int C_AllocationHdr_ID = ((Integer)getGridTab().getValue("C_AllocationHdr_ID")).intValue();
		int C_Payment_ID = ((Integer)getGridTab().getValue("C_Payment_ID")).intValue();
		
		MAllocationHdr header = new MAllocationHdr(Env.getCtx(), C_AllocationHdr_ID, null);

		//  for all rows
		for (int i = 0; i < miniTable.getRowCount(); i++) {
			
			// For selected rows
			if (((Boolean) miniTable.getValueAt(i, 0)).booleanValue()) {
				
				// Get the KeyNamePair of the ID
				KeyNamePair invoice= (KeyNamePair) miniTable.getValueAt(i,1);
				
				// Insert a new line without the amount
				MAllocationLine line= new MAllocationLine(header, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
				line.setC_Invoice_ID(invoice.getKey());
				line.setC_Payment_ID(C_Payment_ID);
				
				// Save the line
				if (!(line.save())) {
					log.log(Level.SEVERE,Msg.getMsg(Env.getCtx(), "CannotSave")+ i);
				}
				
				// Get the amounts from the resent saved line
				BigDecimal grandTotal = (BigDecimal) line.get_Value("GrandTotal");
				BigDecimal openAmt= (BigDecimal) line.get_Value("OpenAmt");
				BigDecimal payOpenAmt= (BigDecimal) line.get_Value("PayOpenAmt");
				BigDecimal amount= (BigDecimal) miniTable.getValueAt(i,6);
				
				BigDecimal openAmtBeforeSave = openAmt.add(amount);
				
				// Correct the amount
				//amount=correctAmount(payOpenAmt,grandTotal,openAmt,amount);
				amount=correctAmount(payOpenAmt,grandTotal,openAmtBeforeSave,amount);

				// Set the amount
				line.setAmount(amount);
				
				// Save the line
				if (!(line.save())) {
					log.log(Level.SEVERE,Msg.getMsg(Env.getCtx(), "CannotSave")+ i);
				}
				
				
			}   //   if selected
		}   //  for all rows
		
		return true;
	}
	
	// Correct the amount to be set
	protected BigDecimal correctAmount(BigDecimal payOpenAmt, BigDecimal grandTotal, BigDecimal openAmt, BigDecimal amount) {
		
		// Invoices must have a positive totals, credit notes have a diferent tratement 
		if (grandTotal.compareTo(BigDecimal.ZERO)>=0) {

			// Negative amount
			if (amount.compareTo(BigDecimal.ZERO)<0) {
				amount=BigDecimal.ZERO;			//  El importe afectado debe ser mayor que 0 por que el saldo del documento es positivo
			}

			// Amount greater than document grand total
			if (amount.compareTo(grandTotal)>0) {
				amount=grandTotal;				//  El importe afectado es mayor que el total del documento
			}

			// Amount greater than document open amount 
			if (amount.compareTo(openAmt)>0) {
				amount=openAmt;					//  El importe afectado es mayor que el saldo documento
			} 

			// Amount greater than payment open amount
			if (amount.compareTo(payOpenAmt)>0) {
				amount=payOpenAmt;				// El importe afecto es mayor que el saldo del recibo
			}
		} 
		else { 		
			
			// Positive amount is not posible for credit notes
			if (amount.compareTo(BigDecimal.ZERO)>0) {
				amount=BigDecimal.ZERO;			//  El importe afectado debe ser menor que 0 por que el saldo del documento es negativo
			}

			// Amount lower than document grand total is not posible for credit notes 
			if (amount.compareTo(grandTotal)<0) {
				amount=grandTotal;				//  El total del documento es negativo y el importe afectado debe mayor que el total en este caso
			}
	
			// Amount lower than document open amount is not posible for credit notes
			if (amount.compareTo(openAmt)<0) {
				amount=openAmt;					//  El saldo del documento es negativo y el importe afectado debe ser menor que el saldo en este caso
			}

			// Amount lower than payment open amount is not posible for credit notes 
			if (amount.compareTo(payOpenAmt)>0) {
				amount=payOpenAmt;				//  El importe afectado es mayor que el saldo del recibo
			}
		}

		return(amount);
	}

	protected Vector<Vector<Object>> getData(int c_bpartner_id,int c_currency_id,String dateacct)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		// String sql="SELECT * FROM c_invoice WHERE c_bpartner_id=?";
		String sql=	"SELECT c_invoice.C_Invoice_ID, c_invoice.DocumentNo, c_invoice.C_DocType_ID, c_doctype.name, c_invoice.C_Currency_ID, c_currency.ISO_Code, " +
					"		(CASE WHEN charat(c_doctype.docbasetype::character varying,3)::text='C'::text THEN c_invoice.grandtotal*(-1)::numeric ELSE c_invoice.grandtotal END) AS GrandTotal, " +
					"		(CASE WHEN charat(c_doctype.docbasetype::character varying,3)::text='C'::text THEN c_invoice.grandtotal*(-1)::numeric ELSE c_invoice.grandtotal END)-coalesce(" +
					"(SELECT SUM(c_allocationline.amount+c_allocationline.discountamt+c_allocationline.writeoffamt) " +
					" FROM c_allocationline " +
					" INNER JOIN c_allocationhdr ON c_allocationline.c_allocationhdr_id = c_allocationhdr.c_allocationhdr_id " +
					" WHERE c_invoice.c_invoice_id=c_allocationline.c_invoice_id " +
					" AND c_allocationhdr.docstatus NOT IN('VO','RE') " +
					" AND c_allocationline.isactive='Y'),0) as OpenAmt " + 
					" FROM c_invoice, c_currency, c_doctype " +
					" WHERE c_invoice.c_currency_id=c_currency.c_currency_id AND c_invoice.c_doctype_id=c_doctype.c_doctype_id AND " +
					"      c_invoice.c_bpartner_id=? AND " +
					"      (c_invoice.DocStatus='CO' OR c_invoice.DocStatus='CL') AND c_invoice.C_Currency_ID=? AND DateInvoiced<=?::timestamp";   // This is the same as C_InvoiceCurrencyFilter
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, c_bpartner_id);
			pstmt.setInt(2, c_currency_id);
			pstmt.setString(3, dateacct);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				// Only open documents, openamt not equal zero. OpenUP FL 22/02/2011, issue #357
				if (rs.getBigDecimal("OpenAmt").compareTo(BigDecimal.ZERO)!=0) {
					Vector<Object> line = new Vector<Object>(7);
					line.add(new Boolean(false));       														// 0 Selection
					line.add(new KeyNamePair(rs.getInt("C_Invoice_ID"),rs.getString("DocumentNo")));			// 1 C_Invoice_ID
					line.add(new KeyNamePair(rs.getInt("C_DocType_ID"),rs.getString("name")));					// 2 C_DocType_ID
					line.add(new KeyNamePair(rs.getInt("C_Currency_ID"),rs.getString("ISO_Code")));				// 3 C_Currency_ID
					line.add(rs.getBigDecimal("GrandTotal"));													// 4 GrandTotal
					line.add(rs.getBigDecimal("OpenAmt"));														// 5 OpenAmt
					line.add(BigDecimal.ZERO); 																	// 6 Amount
					
					data.add(line);
				}
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
		miniTable.setColumnClass(1, String.class, true);    	//  1 C_Invoice_ID
		miniTable.setColumnClass(2, String.class, true);    	//  2 C_DocType_ID
		miniTable.setColumnClass(3, String.class, true);    	//  3 C_Currency_ID
		miniTable.setColumnClass(4, BigDecimal.class, true);    //  4 GrandTotal
		miniTable.setColumnClass(5, BigDecimal.class, true);    //  5 OpenAmt
		miniTable.setColumnClass(6, BigDecimal.class, false);   //  6 Amount
	}

	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(7);
		columnNames.add(Msg.getMsg(Env.getCtx(), 	"Select"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_Invoice_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_DocType_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_Currency_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "GrandTotal"));
		columnNames.add(Msg.translate(Env.getCtx(), "OpenAmt"));
		columnNames.add("Afectado");

	    return columnNames;
	}
	
	// TODO: used just to review
	protected KeyNamePair getLocatorKeyNamePair(int M_Locator_ID)
	{
		MLocator locator = null;
		
		// Load desired Locator
		if (M_Locator_ID > 0)
		{
			locator = MLocator.get(Env.getCtx(), M_Locator_ID);
			// Validate warehouse
			if (locator != null && locator.getM_Warehouse_ID() != getM_Warehouse_ID())
			{
				locator = null;
			}
		}
		
		// Try to use default locator from Order Warehouse
		if (locator == null && p_order != null && p_order.getM_Warehouse_ID() == getM_Warehouse_ID())
		{
			MWarehouse wh = MWarehouse.get(Env.getCtx(), p_order.getM_Warehouse_ID());
			if (wh != null)
			{
				locator = wh.getDefaultLocator();
			}
		}
		// Try to get from locator field
		if (locator == null)
		{
			if (defaultLocator_ID > 0)
			{
				locator = MLocator.get(Env.getCtx(), defaultLocator_ID);
			}
		}
		// Validate Warehouse
		if (locator == null || locator.getM_Warehouse_ID() != getM_Warehouse_ID())
		{
			locator = MWarehouse.get(Env.getCtx(), getM_Warehouse_ID()).getDefaultLocator();
		}
		
		KeyNamePair pp = null ;
		if (locator != null)
		{
			pp = new KeyNamePair(locator.get_ID(), locator.getValue());
		}
		return pp;
	}
}
