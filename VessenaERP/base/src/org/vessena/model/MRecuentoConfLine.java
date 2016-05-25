package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MRecuentoConfLine extends X_UY_RecuentoConfLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1610858211846947887L;

	public MRecuentoConfLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MRecuentoConfLine(Properties ctx, int UY_RecuentoConfLine_ID, String trxName) {
		super(ctx, UY_RecuentoConfLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Constructor Nico
	 */

	public String ValidarCantidades() {

		if (MStockTransaction.getQtyApproved(this.getM_Warehouse_ID(), this.getM_Locator_ID(), getM_Product_ID(), 0, null, null).compareTo(
				this.getQty_ApprovedBook()) != 0) {
			return "Hay diferencias en la cantidad de libros desde que se definio el recuent al momento de confirmar";
		}

		if (MStockTransaction.getQtyQuarantine(this.getM_Warehouse_ID(), this.getM_Locator_ID(), getM_Product_ID(), 0, null, null).compareTo(
				this.getQty_QuarantineBook()) != 0) {
			return "Hay diferencias en la cantidad de libros desde que se definio el recuent al momento de confirmar";
		}

		if (MStockTransaction.getQtyLocked(this.getM_Warehouse_ID(), this.getM_Locator_ID(), getM_Product_ID(), 0, null, null).compareTo(
				this.getQty_BlockedBook()) != 0) {
			return "Hay diferencias en la cantidad de libros desde que se definio el recuent al momento de confirmar";
		}

		return null;
	}
}
