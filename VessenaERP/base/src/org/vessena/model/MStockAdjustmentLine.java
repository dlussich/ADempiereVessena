/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.util.Env;

/**
 * @author Hp
 *
 */
public class MStockAdjustmentLine extends X_UY_StockAdjustmentLine {

	@Override
	protected boolean beforeSave(boolean newRecord) {

		//OpenUp Nicolas Sarlabos #972 06/02/2012
		MStockAdjustment hdr = new MStockAdjustment(getCtx(), this.getUY_StockAdjustment_ID(), get_TrxName());
		MDocType doc = new MDocType(getCtx(), hdr.getC_DocType_ID(), null);

		if (newRecord){

			this.setUY_StockStatus_ID(hdr.getUY_StockStatus_ID());

		}

		if (doc.getDocBaseType().equalsIgnoreCase("MMM") && doc.getDocSubTypeSO().equalsIgnoreCase("BR")) {

			if (this.getQtyCount() == null) this.setQtyCount(Env.ZERO);

				if (this.getQtyBook().compareTo(Env.ZERO) > 0) {

				if (this.getQtyCount().compareTo(this.getQtyBook()) < 0 && this.getQtyCount().compareTo(Env.ZERO) >= 0) this.setMovementQty(this.getQtyCount()
						.subtract(this.getQtyBook()));
				else
					throw new AdempiereException("Cantidad final no puede ser negativa y debe ser menor a cantidad en libros");

			}

		}

		//fin OpenUp Nicolas Sarlabos #972 06/02/2012

		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8382466235568360819L;

	/**
	 * @param ctx
	 * @param UY_StockAdjustmentLine_ID
	 * @param trxName
	 */
	public MStockAdjustmentLine(Properties ctx, int UY_StockAdjustmentLine_ID,
			String trxName) {
		super(ctx, UY_StockAdjustmentLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MStockAdjustmentLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
