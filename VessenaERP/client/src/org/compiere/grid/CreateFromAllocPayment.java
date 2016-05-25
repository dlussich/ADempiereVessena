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
import org.compiere.model.MDocType;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.openup.model.MAllocation;
import org.openup.model.MAllocationPayment;
import org.openup.model.X_UY_Allocation;

/**
 * @author OpenUp. Gabriel Vila. 18/10/2011.
 *
 */
public class CreateFromAllocPayment extends CreateFrom {

	/**
	 * @param gridTab
	 */
	public CreateFromAllocPayment(GridTab gridTab) {
		super(gridTab);
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 */
	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle("Seleccion de Recibos y Notas de Credito");
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
		
		BigDecimal totalAfectarInvoices = Env.ZERO, divideRate = Env.ZERO;
		boolean tengoSaldoFacturas = false;
		
		int uyAllocationID = ((Integer)getGridTab().getValue(X_UY_Allocation.COLUMNNAME_UY_Allocation_ID)).intValue();
		MAllocation hdr = new MAllocation(Env.getCtx(), uyAllocationID, null);
		
		// Verifico si tengo o no saldo de factura a afectar
		if (hdr.getamtinvallocated().compareTo(Env.ZERO) > 0){
			tengoSaldoFacturas = true;
			totalAfectarInvoices = hdr.getamtinvallocated().subtract(hdr.getamtpayallocated());
			if (totalAfectarInvoices.compareTo(Env.ZERO) < 0) totalAfectarInvoices = Env.ZERO;
		}

		divideRate = hdr.getDivideRate();
		
		if (divideRate.compareTo(Env.ZERO) <= 0) divideRate = Env.ONE;
		
		//  for all rows
		for (int i = 0; i < miniTable.getRowCount(); i++) {
			
			// For selected rows
			if (((Boolean) miniTable.getValueAt(i, 0)).booleanValue()) {
				
				KeyNamePair docType = (KeyNamePair) miniTable.getValueAt(i,1);
				KeyNamePair recibo = (KeyNamePair) miniTable.getValueAt(i,2);
				KeyNamePair currency = (KeyNamePair) miniTable.getValueAt(i,4);
				
				int cDocTypeID = docType.getKey();
				MDocType doc = new MDocType(Env.getCtx(), cDocTypeID, null);
				boolean isReciboPago = (doc.getDocBaseType().equalsIgnoreCase("APP") || doc.getDocBaseType().equalsIgnoreCase("ARR")) ? true : false;
				
				MAllocationPayment line = new MAllocationPayment(Env.getCtx(), 0, trxName);
				line.setUY_Allocation_ID(uyAllocationID);
				line.setC_DocType_ID(docType.getKey());
				line.setDocumentNo(recibo.getName());
				line.setC_Currency_ID(currency.getKey());
				if (isReciboPago) 
					line.setC_Payment_ID(recibo.getKey());
				else 
					line.setC_Invoice_ID(recibo.getKey());
				
				line.setdatedocument((Timestamp)miniTable.getValueAt(i,3));
				line.setamtdocument((BigDecimal)miniTable.getValueAt(i,5));
				line.setamtopen((BigDecimal)miniTable.getValueAt(i,6));
				line.setamtallocated((BigDecimal)miniTable.getValueAt(i,7));				
				
				// Antes de guardar esta linea, tengo que verificar tope contra total a
				// afectar en facturas (comparo siempre contra moneda 1 de afectacion).
				BigDecimal montoAfectar = line.getamtallocated();
				if (line.getC_Currency_ID() == hdr.getC_Currency2_ID()){
					montoAfectar = montoAfectar.multiply(divideRate).setScale(2, RoundingMode.HALF_UP);
				}
				if (tengoSaldoFacturas){
					// Si el monto a afectar de este recibo es mayor a lo que tengo para afectar de facturas
					if (montoAfectar.compareTo(totalAfectarInvoices) > 0){
						montoAfectar = totalAfectarInvoices;
						totalAfectarInvoices = Env.ZERO;
						// Veo si tengo que convertir nuevo monto a afectar en moneda del recibo
						if (montoAfectar.compareTo(Env.ZERO) > 0){
							if (line.getC_Currency_ID() == hdr.getC_Currency2_ID()){
								montoAfectar = montoAfectar.divide(divideRate,2,RoundingMode.HALF_UP);
							}
						}
						line.setamtallocated(montoAfectar);
					}
					else{
						totalAfectarInvoices = totalAfectarInvoices.subtract(montoAfectar);
					}
				}
				
				line.saveEx(trxName);
			}   //   if selected
		}   //  for all rows
		
		return true;

	}

	protected Vector<Vector<Object>> getData(int adClientID, String isSOTrx, int cBPartnerID, Timestamp dateTrx,
											 int cCurrencyID, int cCurrency2ID, int uyAllocationID)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		String whereMonedas = "";
		if (cCurrency2ID > 0)
			whereMonedas = " AND al.c_currency_id IN (" + cCurrencyID + "," + cCurrency2ID + ")";
		else
			whereMonedas = " AND al.c_currency_id =" + cCurrencyID;
		
		String sql=	" SELECT al.c_payment_id as recibo_id, al.c_doctype_id, doc.printname, al.documentno, " +
				    	" al.datetrx as recibo_date, al.c_currency_id, cur.description, al.amtpay as recibo_total, " +
				    	" al.amtallocated, al.amtopen " +
				    " FROM alloc_paymentamtopen al " +
				    " INNER JOIN c_doctype doc on al.c_doctype_id = doc.c_doctype_id " +
				    " INNER JOIN c_currency cur on al.c_currency_id = cur.c_currency_id " +
				    " WHERE al.ad_client_id =? " +
				    " AND al.issotrx=? " +
				    " AND al.c_bpartner_id =? " +
				    " AND al.datetrx <=? " +
				    whereMonedas +
				    " AND al.amtopen > 0 " +
				    " AND al.c_payment_id NOT IN (SELECT coalesce(alp.c_payment_id,0) FROM uy_allocationpayment alp WHERE alp.uy_allocation_id=" + uyAllocationID + ")" +
				    " UNION " +
				    " SELECT al.c_invoice_id as recibo_id, al.c_doctype_id, doc.printname, al.documentno, " +
				    	" al.dateinvoiced as recibo_date, al.c_currency_id, cur.description, al.amtinvoiced as recibo_total, " +
				    	" al.amtallocated, al.amtopen " +
				    " FROM alloc_creditnoteamtopen al " +
				    " INNER JOIN c_doctype doc on al.c_doctype_id = doc.c_doctype_id " +
				    " INNER JOIN c_currency cur on al.c_currency_id = cur.c_currency_id " +
				    " WHERE al.ad_client_id =? " +
				    " AND al.issotrx=? " +
				    " AND al.c_bpartner_id =? " +
				    " AND al.dateinvoiced <=? " +
				    whereMonedas +
				    " AND al.amtopen > 0" +
				    " AND al.c_invoice_id NOT IN (SELECT coalesce(alp.c_invoice_id,0) FROM uy_allocationpayment alp WHERE alp.uy_allocation_id=" + uyAllocationID + ")";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, adClientID);
			pstmt.setString(2, isSOTrx);
			pstmt.setInt(3, cBPartnerID);
			pstmt.setTimestamp(4, dateTrx);
			pstmt.setInt(5, adClientID);
			pstmt.setString(6, isSOTrx);
			pstmt.setInt(7, cBPartnerID);
			pstmt.setTimestamp(8, dateTrx);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(7);
				line.add(new Boolean(false));
				line.add(new KeyNamePair(rs.getInt("c_doctype_id"),rs.getString("printname")));
				line.add(new KeyNamePair(rs.getInt("recibo_id"),rs.getString("documentno")));
				line.add(rs.getTimestamp("recibo_date"));
				line.add(new KeyNamePair(rs.getInt("c_currency_id"),rs.getString("description")));
				line.add(rs.getBigDecimal("recibo_total"));
				line.add(rs.getBigDecimal("amtopen"));
				line.add(BigDecimal.ZERO);
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
	}

	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(8);
		columnNames.add("Seleccionar");
		columnNames.add("Documento        ");
		columnNames.add("Numero    ");
		columnNames.add("Fecha    ");
		columnNames.add("Moneda     ");
		columnNames.add("Total Documento ");
		columnNames.add("Saldo Pendiente ");
		columnNames.add("Total a Afectar ");
	    return columnNames;
	}
}
