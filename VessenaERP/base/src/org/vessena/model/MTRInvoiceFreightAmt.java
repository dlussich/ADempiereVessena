/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MTRInvoiceFreightAmt extends X_UY_TR_InvoiceFreightAmt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3396863146195205445L;

	/**
	 * @param ctx
	 * @param UY_TR_InvoiceFreightAmt_ID
	 * @param trxName
	 */
	public MTRInvoiceFreightAmt(Properties ctx, int UY_TR_InvoiceFreightAmt_ID,
			String trxName) {
		super(ctx, UY_TR_InvoiceFreightAmt_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRInvoiceFreightAmt(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo para ID de linea de orden de transporte recibido.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 15/04/2015
	 * @see
	 * @return
	 */
	public static MTRInvoiceFreightAmt forOTLine(Properties ctx, int lineID, String trxName){

		String whereClause = X_UY_TR_InvoiceFreightAmt.COLUMNNAME_UY_TR_TransOrderLine_ID + "=" + lineID;

		MTRInvoiceFreightAmt model = new Query(ctx, I_UY_TR_InvoiceFreightAmt.Table_Name, whereClause, trxName).first();

		return model;
	}
	
	/***
	 * Obtiene y retorna modelo para ID de OT y CRT recibidos.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 15/04/2015
	 * @see
	 * @return
	 */
	public static MTRInvoiceFreightAmt forOTCrt(int orderID, int crtID, String trxName){

		String whereClause = X_UY_TR_InvoiceFreightAmt.COLUMNNAME_UY_TR_TransOrder_ID + "=" + orderID + " and " + X_UY_TR_InvoiceFreightAmt.COLUMNNAME_UY_TR_Crt_ID + "=" + crtID;

		MTRInvoiceFreightAmt model = new Query(Env.getCtx(), I_UY_TR_InvoiceFreightAmt.Table_Name, whereClause, trxName).first();

		return model;
	}
	
	/***
	 * Obtiene y retorna modelo para ID de CRT y almacen recibidos.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 15/04/2015
	 * @see
	 * @return
	 */
	public static MTRInvoiceFreightAmt forWarehouseCrt(int warehouseID, int crtID, String trxName){

		String whereClause = X_UY_TR_InvoiceFreightAmt.COLUMNNAME_UY_TR_Crt_ID + "=" + crtID + " and " + X_UY_TR_InvoiceFreightAmt.COLUMNNAME_M_Warehouse_ID + "=" + warehouseID;

		MTRInvoiceFreightAmt model = new Query(Env.getCtx(), I_UY_TR_InvoiceFreightAmt.Table_Name, whereClause, trxName).first();

		return model;
	}
	
	

}
