/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 22/11/2012
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
import org.compiere.model.X_C_Payment;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.openup.model.MPaymentResguardo;
import org.openup.model.MResguardo;
import org.openup.model.MResguardoLine;

/**
 * org.compiere.grid - CreateFromResguardo
 * OpenUp Ltda. Issue #100 
 * Description: Seleccion de Resguardos pendientes de un determinado proveedor.
 * @author Gabriel Vila - 22/11/2012
 * @see
 */
public class CreateFromResguardo extends CreateFrom {

	/**
	 * Constructor.
	 * @param gridTab
	 */
	public CreateFromResguardo(GridTab gridTab) {
		super(gridTab);
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 22/11/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean dynInit() throws Exception {
		setTitle("Seleccion de Resguardos");
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#info()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 22/11/2012
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
		
		int cPaymentID = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_C_Payment_ID)).intValue();
		
		//  for all rows
		for (int i = 0; i < miniTable.getRowCount(); i++) {
			
			// For selected rows
			if (((Boolean) miniTable.getValueAt(i, 0)).booleanValue()) {
				
				KeyNamePair resguardo = (KeyNamePair) miniTable.getValueAt(i,2);
				KeyNamePair retencion = (KeyNamePair) miniTable.getValueAt(i,4);
				
				MPaymentResguardo line = new MPaymentResguardo(Env.getCtx(), 0, trxName);
				line.setC_Payment_ID(cPaymentID);
				line.setUY_Resguardo_ID(resguardo.getKey());
				line.setUY_Retention_ID(retencion.getKey());
				line.setAmt((BigDecimal)miniTable.getValueAt(i,5));
				
				MResguardoLine resgline = MResguardo.getLineForRetention(Env.getCtx(), line.getUY_Resguardo_ID(),
										  line.getUY_Retention_ID(), null);
				
				line.setUY_ResguardoLine_ID(resgline.get_ID());
				line.setAmtSource(resgline.getAmtSource());

				line.saveEx(trxName);
			}
		} 
		
		return true;
	}

	protected Vector<Vector<Object>> getData(int adClientID, int cBPartnerID, Timestamp dateTrx, int cPaymentID){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();

		String sql=	" SELECT resg.uy_resguardo_id, resg.c_doctype_id, doc.printname, " +
				        " resg.documentno, resg.datetrx, resgl.uy_retention_id, ret.name, " +
				        " resgl.uy_resguardoline_id, resgl.amt " +
					" FROM uy_resguardo resg " +
					" INNER JOIN uy_resguardoline resgl on resg.uy_resguardo_id = resgl.uy_resguardo_id " +
					" INNER JOIN uy_retention ret on resgl.uy_retention_id = ret.uy_retention_id " +
					" INNER JOIN c_doctype doc on resg.c_doctype_id = doc.c_doctype_id " +
					" WHERE resg.ad_client_id =? " +
					" AND resg.docstatus='CO' " +
					" AND resg.c_bpartner_id =? " +
					" AND resg.datetrx <=? " +
					" AND resgl.c_payment_id is null " +
					" AND resgl.uy_resguardoline_id NOT IN (SELECT uy_resguardoline_id FROM uy_payment_resguardo WHERE c_payment_id=" + cPaymentID + ")";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, adClientID);
			pstmt.setInt(2, cBPartnerID);
			pstmt.setTimestamp(3, dateTrx);
			
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(5);
				line.add(new Boolean(false));
				line.add(new KeyNamePair(rs.getInt("c_doctype_id"),rs.getString("printname")));
				line.add(new KeyNamePair(rs.getInt("uy_resguardo_id"),rs.getString("documentno")));
				line.add(rs.getTimestamp("datetrx"));
				line.add(new KeyNamePair(rs.getInt("uy_retention_id"),rs.getString("name")));
				line.add(rs.getBigDecimal("amt"));
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
		miniTable.setColumnClass(2, String.class, true);    	//  2 Resguardo_ID
		miniTable.setColumnClass(3, Timestamp.class, true);    	//  3 Resguardo_Date
		miniTable.setColumnClass(4, String.class, true);    	//  4 Retention_ID
		miniTable.setColumnClass(5, BigDecimal.class, true);    //  6 AmtOpen
	}
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(7);
		columnNames.add("Seleccionar");
		columnNames.add("Documento        ");
		columnNames.add("Numero    ");
		columnNames.add("Fecha    ");
		columnNames.add("Retencion     ");
		columnNames.add("Importe ");
		return columnNames;
	}

}
