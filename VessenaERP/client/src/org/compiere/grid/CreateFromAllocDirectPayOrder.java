/**
 * @author OpenUp SBT Issue #5052  16/11/2015 10:43:15
 */
package org.compiere.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MDocType;
import org.compiere.model.MPayment;
import org.compiere.model.X_C_Payment;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.openup.model.MAllocDirectPayment;
import org.openup.model.MLinePayment;
import org.openup.model.MPayOrder;
import org.openup.model.MPayOrderLine;
import org.openup.model.MPayOrderResguardo;
import org.openup.model.MPayOrderRule;
import org.openup.model.MPaymentPayOrder;
import org.openup.model.MPaymentResguardo;

/**
 * @author OpenUp SBT Issue #5052  16/11/2015 10:43:15
 *
 */
public class CreateFromAllocDirectPayOrder extends CreateFrom {

	/**
	 * @author OpenUp SBT Issue #5052  16/11/2015 10:43:15
	 * @param gridTab
	 */
	public CreateFromAllocDirectPayOrder(GridTab gridTab) {
		super(gridTab);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 */
	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle("Seleccion de Orden de Pago");
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
		int cPaymentID = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_C_Payment_ID)).intValue();
		BigDecimal totalAfectarPayments = Env.ZERO;

		MPayment hdr = new MPayment(Env.getCtx(), cPaymentID, null);

		// Verifico si tengo o no saldo de recibo a afectar
		if (hdr.getPayAmt().compareTo(Env.ZERO) > 0) {
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
				
				MPayOrder pOrder = new MPayOrder(Env.getCtx(), recibo.getKey(), null);
				if(null!=pOrder){
					MPaymentPayOrder payOrder = new MPaymentPayOrder(Env.getCtx(), 0, trxName);
					payOrder.setC_Payment_ID(cPaymentID);
					payOrder.setC_DocType_ID(docType.getKey());
					payOrder.setDocumentNo(recibo.getName());
					payOrder.setC_Currency_ID(currency.getKey());
					payOrder.setUY_PayOrder_ID(recibo.getKey());
					payOrder.setdatedocument((Timestamp)miniTable.getValueAt(i,3));
					payOrder.setSubtotal((BigDecimal)miniTable.getValueAt(i,5));
					payOrder.setAmtResguardo((BigDecimal)miniTable.getValueAt(i,6));
					payOrder.setPayAmt((BigDecimal)miniTable.getValueAt(i,7));
					//SBT 04/02/2016 Issue #5428 Se agrega el campo de descuento que se infiere de la orden de pago
					payOrder.set_ValueOfColumn("TotalDiscounts", pOrder.get_Value("TotalDiscounts"));
					
					payOrder.saveEx(trxName);					
					
					List<MPayOrderLine> afectacionPago = pOrder.getLines();
					for(MPayOrderLine l : afectacionPago){
						MAllocDirectPayment afect = new MAllocDirectPayment(Env.getCtx(), 0, trxName);
						afect.setC_Payment_ID(cPaymentID);
						afect.set_ValueOfColumn("C_PaymentPayOrder_ID", payOrder.get_ID());
						afect.setC_DocType_ID(l.getC_DocType_ID());
						afect.setC_Invoice_ID(l.getC_Invoice_ID());
						afect.setDocumentNo(l.getC_Invoice().getDocumentNo());
						afect.setC_Currency_ID(l.getC_Currency_ID());
						afect.setdatedocument(l.getDateInvoiced());//Timestamp --
						afect.setamtdocument(l.getAmount());//Total Documento --amtinvoiced
						afect.setamtopen(l.getamtopen());//Saldo Pendiente --
						afect.setamtallocated(l.getamtallocated());//Total a Afectar --
						
						MDocType doc = new MDocType(Env.getCtx(), docType.getKey(), null);
						if ((doc.getDocBaseType().equalsIgnoreCase("ARC")) || (doc.getDocBaseType().equalsIgnoreCase("APC"))){
							afect.setsign(Env.ONE);
						}					
						else{
							afect.setsign(new BigDecimal(-1));
						}		
						afect.save(trxName);
						
					}
					
					List<MPayOrderResguardo> pOrderResgLines = pOrder.getResguardos();
					for (MPayOrderResguardo pOrderResg: pOrderResgLines){
						MPaymentResguardo line = new MPaymentResguardo(Env.getCtx(), 0, trxName);
						line.setUY_PayOrder_ID(pOrder.get_ID());
						line.setC_Payment_ID(cPaymentID);
						line.setUY_Resguardo_ID(pOrderResg.getUY_Resguardo_ID());
						line.setAmt(pOrderResg.getAmount());
						line.setAmtSource(pOrderResg.getAmount());
						line.setC_PaymentPayOrder_ID(payOrder.get_ID());
						line.saveEx(trxName);
					}
					
					List<MPayOrderRule> lineasPago = MPayOrderRule.getPayOrdeRules(Env.getCtx(),pOrder.get_ID(),trxName);
					for(MPayOrderRule poRule:lineasPago){
						MLinePayment lPago = new MLinePayment(Env.getCtx(), 0, trxName);
						lPago.set_ValueOfColumn("C_PaymentPayOrder_ID",  payOrder.get_ID());
						lPago.setC_Payment_ID(cPaymentID);
						lPago.setUY_PaymentRule_ID(poRule.getUY_PaymentRule_ID());
						lPago.setUY_MediosPago_ID(poRule.getUY_MediosPago_ID());
						lPago.setC_BankAccount_ID(poRule.getC_BankAccount_ID());
						//poRule.getUY_PayOrderRule_ID() ??
						lPago.setCheckNo(poRule.getCheckNo());
						//SBT 30/11/2015 Se debe agregar el mismo numero para los casos que necesitan dicho numero
						lPago.setDocumentNo(poRule.getCheckNo()); 
						lPago.setPayAmt(poRule.getPayAmt());
						lPago.setDueDate(poRule.getDueDate());
						
						lPago.saveEx(trxName);
					}
					
				}

			}
		} 
		//
		return true;
	}

	/**
	 * Tipo de datos de las columnas de orden de pago
	 * @author OpenUp SBT Issue#  16/11/2015 10:46:58
	 * @param miniTable
	 */
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);      //  0 Selection
		miniTable.setColumnClass(1, String.class, true);    	//  1 C_DocType_ID
		miniTable.setColumnClass(2, String.class, true);    	//  2 Recibo_ID
		miniTable.setColumnClass(3, Timestamp.class, true);    	//  3 Recibo_Date
		miniTable.setColumnClass(4, String.class, true);    	//  4 C_Currency_ID
		miniTable.setColumnClass(5, BigDecimal.class, true);    //  5 Subtotal
		miniTable.setColumnClass(6, BigDecimal.class, true);    //  6 Resguardos
		miniTable.setColumnClass(7, BigDecimal.class, true);    //  7 Total
	}
	
	/**
	 * Columnas de la ventana seleccion de orden de pago
	 * @author OpenUp SBT Issue #5052  16/11/2015 10:46:06
	 * @return
	 */
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(8);
		columnNames.add("Seleccionar ");
		columnNames.add("Documento  ");
		columnNames.add("Numero    ");
		columnNames.add("Fecha    ");
		columnNames.add("Moneda     ");
		columnNames.add("Subtotal ");
		columnNames.add("Resguardos ");
		columnNames.add("Total ");
		return columnNames;
	}
	
	/**
	 * Cargo las ordenes de pago completas para el proveedor que recibo (TENER EN CUENTA LA PROX CONDICION )
	 * @author OpenUp SBT Issue#  16/11/2015 11:14:40
	 * @param adClientID
	 * @param isSOTrx
	 * @param cBPartnerID
	 * @param dateTrx
	 * @param cCurrencyID
	 * @param cPaymentID
	 * @return
	 */
	protected Vector<Vector<Object>> getData(int adClientID, String isSOTrx, int cBPartnerID, Timestamp dateTrx, int cCurrencyID, int cPaymentID){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		String sql = " SELECT po.uy_PayOrder_ID as po_id, po.c_doctype_id, doc.printname, po.documentno, " +
					 " po.datetrx as po_date, po.c_currency_id, cur.description, po.payamt as po_total, " +
					 " po.subtotal, po.amtresguardo "+
					 " FROM UY_PayOrder po " +
					 " INNER JOIN C_DocType doc ON po.C_DocType_ID = doc.C_DocType_ID " +
					 " INNER JOIN C_Currency cur on po.C_Currency_ID = cur.C_Currency_ID " +
					
					//" WHERE po.issotrx=? " +
					" WHERE po.isactive= 'Y' " +
					
					" AND po.c_bpartner_id =? " +
					" AND po.datetrx <=? " +
					" AND po.c_currency_id =" + cCurrencyID +
					" AND po.payamt > 0" +
					" AND po.DocStatus = 'CO' AND po.C_Payment_ID IS NULL " + // 18/11 Se agrega columna a la tabla y se agrega condicion
		
					" AND po.uy_payorder_id NOT IN (SELECT coalesce(ali.uy_payorder_id,0) "
					+ "FROM C_PaymentPayOrder ali WHERE ali.c_payment_id=" + cPaymentID + ")";
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			//pstmt.setInt(1, adClientID);
			//pstmt.setString(1, isSOTrx);
			pstmt.setInt(1, cBPartnerID);
			pstmt.setTimestamp(2, dateTrx);
			
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(7);
				line.add(new Boolean(false));
				line.add(new KeyNamePair(rs.getInt("c_doctype_id"),rs.getString("printname")));
				line.add(new KeyNamePair(rs.getInt("po_id"),rs.getString("documentno")));
				line.add(rs.getTimestamp("po_date"));
				line.add(new KeyNamePair(rs.getInt("c_currency_id"),rs.getString("description")));
				line.add((rs.getBigDecimal("subtotal")!=null)?rs.getBigDecimal("subtotal"):Env.ZERO);
				line.add((rs.getBigDecimal("amtresguardo")!=null)?rs.getBigDecimal("amtresguardo"):Env.ZERO);
				line.add((rs.getBigDecimal("po_total")!=null)?rs.getBigDecimal("po_total"):Env.ZERO);
				//line.add(BigDecimal.ZERO);
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
}
