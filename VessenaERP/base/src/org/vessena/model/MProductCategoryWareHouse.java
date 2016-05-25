package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

public class MProductCategoryWareHouse extends X_UY_ProductCategoryWareHouse {

	private static final long	serialVersionUID	= 3561616012213445574L;

	public MProductCategoryWareHouse(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MProductCategoryWareHouse(Properties ctx, int UY_ProductCategoryWareHouse_ID, String trxName) {
		super(ctx, UY_ProductCategoryWareHouse_ID, trxName);
	}

	protected boolean beforeSave(boolean newRecord) {

		// Solo puede haber un almacen por categoria como Default
		if (this.isDefault()) {

			int resultado = DB.executeUpdate("UPDATE " + this.get_TableName() + " Set isDefault='N' WHERE M_Product_Category_ID="
					+ this.getM_Product_Category_ID(), get_TrxName());

			// Defensivo
			if (resultado < 0)
				return false;

			this.setIsDefault(true);

		}

		return true;
	}

}
