/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MLabelPrintScan extends X_UY_LabelPrintScan {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2055454151598818967L;

	/**
	 * @param ctx
	 * @param UY_LabelPrintScan_ID
	 * @param trxName
	 */
	public MLabelPrintScan(Properties ctx, int UY_LabelPrintScan_ID,
			String trxName) {
		super(ctx, UY_LabelPrintScan_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MLabelPrintScan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if(success){

			MProduct prod = MProduct.forUPC(getCtx(), this.getScanText(), get_TrxName());
			
			if(prod==null && this.getM_Product_ID()>0){
				
				prod = (MProduct)this.getM_Product();					
			}

			if(prod!=null && prod.get_ID()>0){

				MLabelPrintLine line = new MLabelPrintLine(getCtx(), 0, get_TrxName());
				line.setUY_LabelPrint_ID(this.getUY_LabelPrint_ID());
				line.setUY_LabelPrintScan_ID(this.get_ID());
				line.setM_Product_ID(prod.get_ID());
				line.setQty(Env.ONE);
				
				//OpenUP SBT 07-04-2016 Issue # (Ahora lista de venta en USD)
				int curID = this.get_ValueAsInt("C_Currency_ID");
				this.set_Value("C_Currency_ID", curID);
//				if(null!=line.get_Value("C_Currency_ID")) 
//					curID = line.get_ValueAsInt("C_Currency_ID");
				
				BigDecimal price = prod.getSalePrice(curID);
				
				if(price==null) price = Env.ZERO;
				
				line.setPrice(price.setScale(2, RoundingMode.HALF_UP));			
								
				String sql = "SELECT COALESCE(MAX(seqno),0)+10 FROM uy_labelprintline WHERE uy_labelprint_id = " + this.getUY_LabelPrint_ID();
				int seqNo = DB.getSQLValue (get_TrxName(), sql);
				
				line.setSeqNo(seqNo);			
				
				line.saveEx();			

			} else 	if (this.getScanText()!=null){
				
				log.saveError(null, "No existe producto para el codigo de barras ingresado");
	            return false;
								
			}		

		}	

		return true;
	}

}
