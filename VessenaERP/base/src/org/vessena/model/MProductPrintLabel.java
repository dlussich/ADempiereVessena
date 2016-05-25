/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Nov 12, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.Query;

/**
 * org.openup.model - MProductPrintLabel
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Nov 12, 2015
*/
public class MProductPrintLabel extends X_UY_ProductPrintLabel {

	private static final long serialVersionUID = 3735164163288254373L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_ProductPrintLabel_ID
	 * @param trxName
	*/

	public MProductPrintLabel(Properties ctx, int UY_ProductPrintLabel_ID, String trxName) {
		super(ctx, UY_ProductPrintLabel_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MProductPrintLabel(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna modelo segun id de producto recibido.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Nov 12, 2015
	 * @param ctx
	 * @param mProductID
	 * @param trxName
	 * @return
	 */
	public static MProductPrintLabel forProduct(Properties ctx, int mProductID, String trxName){
		
		String whereClause = X_UY_ProductPrintLabel.COLUMNNAME_M_Product_ID + "=" + mProductID;
		
		MProductPrintLabel model = new Query(ctx, I_UY_ProductPrintLabel.Table_Name, whereClause, trxName).first();
		
		return model;
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if(newRecord){
			//SBt 07/04/2016 Issue #5733 Se agrega el campo ya que es necesario identificar moneda
			//si el valor viene vacío se toma por moneda la del squema
			if(null == this.get_Value("C_Currency_ID")){
				MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
				MAcctSchema schema = client.getAcctSchema();
				this.set_Value("C_Currency_ID",  schema.getC_Currency_ID());
			}
		}
		return super.beforeSave(newRecord);
	}
}
