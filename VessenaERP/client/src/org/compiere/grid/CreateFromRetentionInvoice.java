/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/11/2012
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
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.openup.model.MResguardoInvoice;
import org.openup.model.X_UY_Resguardo;

/**
 * org.compiere.grid - CreateFromRetentionInvoice
 * OpenUp Ltda. Issue #100 
 * Description: Seleccionar facturas proveedor para resguardos.
 * @author Gabriel Vila - 19/11/2012
 * @see
 */
public class CreateFromRetentionInvoice extends CreateFrom {

	/**
	 * Constructor.
	 * @param gridTab
	 */
	public CreateFromRetentionInvoice(GridTab gridTab) {
		super(gridTab);
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 19/11/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean dynInit() throws Exception {
		setTitle("Seleccion de Facturas Proveedor");
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#info()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 19/11/2012
	 * @see
	 */
	@Override
	public void info() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#save(org.compiere.minigrid.IMiniTable, java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 19/11/2012
	 * @see
	 * @param miniTable
	 * @param trxName
	 * @return
	 */
	@Override
	public boolean save(IMiniTable miniTable, String trxName) {
		
		int uyResguardoID = ((Integer)getGridTab().getValue(X_UY_Resguardo.COLUMNNAME_UY_Resguardo_ID)).intValue();
		//MResguardo hdr = new MResguardo(Env.getCtx(), uyResguardoID, null);
		
		//  for all rows
		for (int i = 0; i < miniTable.getRowCount(); i++) {
			
			// For selected rows
			if (((Boolean) miniTable.getValueAt(i, 0)).booleanValue()) {
				
				KeyNamePair docType = (KeyNamePair) miniTable.getValueAt(i,1);
				KeyNamePair recibo = (KeyNamePair) miniTable.getValueAt(i,2);
				KeyNamePair currency = (KeyNamePair) miniTable.getValueAt(i,4);
				
				MResguardoInvoice line = new MResguardoInvoice(Env.getCtx(), 0, trxName);
				line.setUY_Resguardo_ID(uyResguardoID);
				line.setC_DocType_ID(docType.getKey());
				line.setDocumentNo(recibo.getName());
				line.setC_Currency_ID(currency.getKey());
				line.setC_Invoice_ID(recibo.getKey());
				
				line.setDateInvoiced((Timestamp)miniTable.getValueAt(i,3));
				line.setTotalLines((BigDecimal)miniTable.getValueAt(i,5));
				line.setGrandTotal((BigDecimal)miniTable.getValueAt(i,6));
				line.saveEx(trxName);
			}
		} 
		
		return true;
	}

	protected Vector<Vector<Object>> getData(int adClientID, String isSOTrx, int cBPartnerID, Timestamp dateTrx,
			 int cCurrencyID, int uyResguardoID){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();

		String sql=	" SELECT ci.c_invoice_id as recibo_id, ci.c_doctype_id, doc.printname, ci.documentno, " +
						" ci.dateinvoiced as recibo_date, ci.c_currency_id, cur.description, " +
						" ci.totallines as recibo_subtotal, ci.grandtotal as recibo_total " +
					" FROM c_invoice ci " +
					" INNER JOIN c_doctype doc on ci.c_doctype_id = doc.c_doctype_id " +
					" INNER JOIN c_currency cur on ci.c_currency_id = cur.c_currency_id " +
					" WHERE ci.ad_client_id =? " +
					" AND ci.issotrx=? " +
					" AND ci.docstatus='CO' " +
					" AND ci.c_bpartner_id =? " +
					" AND ci.c_currency_id =? " +
					" AND ci.dateinvoiced <=? " +
					" AND ci.uy_resguardo_id is null " +
					" AND ci.c_invoice_id NOT IN (SELECT coalesce(rinv.c_invoice_id,0) FROM uy_resguardoinvoice rinv WHERE rinv.uy_resguardo_id=" + uyResguardoID + ")";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, adClientID);
			pstmt.setString(2, isSOTrx);
			pstmt.setInt(3, cBPartnerID);
			pstmt.setInt(4, cCurrencyID);
			pstmt.setTimestamp(5, dateTrx);
			
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(6);
				line.add(new Boolean(false));
				line.add(new KeyNamePair(rs.getInt("c_doctype_id"),rs.getString("printname")));
				line.add(new KeyNamePair(rs.getInt("recibo_id"),rs.getString("documentno")));
				line.add(rs.getTimestamp("recibo_date"));
				line.add(new KeyNamePair(rs.getInt("c_currency_id"),rs.getString("description")));
				line.add(rs.getBigDecimal("recibo_subtotal"));
				line.add(rs.getBigDecimal("recibo_total"));
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
	}
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(7);
		columnNames.add("Seleccionar");
		columnNames.add("Documento        ");
		columnNames.add("Numero    ");
		columnNames.add("Fecha    ");
		columnNames.add("Moneda     ");
		columnNames.add("Subtotal ");
		columnNames.add("Total ");
		return columnNames;
	}

}
