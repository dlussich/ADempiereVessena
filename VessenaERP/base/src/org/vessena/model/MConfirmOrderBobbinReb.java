/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MConfirmorderhdr;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * @author Nicolas
 *
 */
public class MConfirmOrderBobbinReb extends X_UY_ConfirmOrderBobbinReb {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2114861963705914582L;

	/**
	 * @param ctx
	 * @param UY_ConfirmOrderBobbinReb_ID
	 * @param trxName
	 */
	public MConfirmOrderBobbinReb(Properties ctx, int UY_ConfirmOrderBobbinReb_ID, String trxName) {
		super(ctx, UY_ConfirmOrderBobbinReb_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MConfirmOrderBobbinReb(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static BigDecimal totalWeightInput (Properties ctx, int confHdrID, String trxName) {
		
		BigDecimal value = Env.ZERO;
		
		String sql = "select coalesce(sum(weight),0)" +
		             " from uy_confirmorderbobbinreb" +
				     " where uy_confirmorderhdr_id = " + confHdrID;
		
		value = DB.getSQLValueBDEx(trxName, sql);		
		
		return value;
		
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if(this.getWeight().compareTo(Env.ZERO)<=0) throw new AdempiereException("El peso de be ser mayor a cero");		

		if(!MSysConfig.getBooleanValue("UY_ALLOW_NEGATIVE_STOCK_PRODTRANSF", false, this.getAD_Client_ID())){ //si no se permite stock negativo en OP

			MConfirmorderhdr hdr = (MConfirmorderhdr)this.getUY_Confirmorderhdr();

			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			MProduct prod = (MProduct) hdr.getM_Product();
			MUOM uomProd = (MUOM)prod.getC_UOM();
			MWarehouse warehouse = (MWarehouse)this.getM_Warehouse();
			MLocator locator = (MLocator)warehouse.getDefaultLocator();
			BigDecimal qty = this.getWeight();

			int uomActual = MUOM.getIDFromSymbol("KG");

			if(uomActual <= 0) throw new AdempiereException("No se obtuvo unidad de medida 'Kilogramo'");

			//obtengo cantidad disponible en UM del producto
			BigDecimal stock = MStockTransaction.getQtyAvailable(warehouse.get_ID(), locator.get_ID(), prod.get_ID(), 
					0, 0, today, this.get_TrxName());

			//si la UM ingresada actual no es igual a la UM de stock del producto
			if(uomActual != prod.getC_UOM_ID()){

				BigDecimal factor = MUOMConversion.getProductRateFrom(getCtx(), prod.get_ID(), uomActual);

				if(factor==null) throw new AdempiereException("No se obtuvo tasa de conversion");

				qty = this.getWeight().multiply(factor);

			}			

			if(stock.compareTo(qty)<0) throw new AdempiereException("No hay stock suficiente en el almacén '" + warehouse.getName() + "'" + 
					" para este producto. \n Cantidad necesaria: " + qty.setScale(2, RoundingMode.HALF_UP) + " " + uomProd.getUOMSymbol() + 
					" \n Cantidad disponible: " + stock.setScale(2, RoundingMode.HALF_UP) + " " + uomProd.getUOMSymbol());			

		}

		if(is_ValueChanged("numero")) this.verifyTrackingNo();

		return true;
	}

	/**
	 * OpenUp. Nicolas Sarlabos. 07/01/2016. Issue #5281.
	 * Verifica el numero de bobina ingresado en la confirmacion.
	 */
	private void verifyTrackingNo() {
		
		MConfirmorderhdr hdr = (MConfirmorderhdr)this.getUY_Confirmorderhdr(); 

		String sql = "select lb.uy_inoutlabel_id" +
				" from uy_inoutlabel lb" +
				" inner join m_inoutline ol on lb.m_inoutline_id = ol.m_inoutline_id" +
				" inner join m_inout hdr on ol.m_inout_id = hdr.m_inout_id" +
				" where hdr.docstatus = 'CO' and ol.m_product_id = " + hdr.getM_Product_ID() +
				" and trim(lb.numero) = trim('" + this.getnumero() + "')";

		int lineID = DB.getSQLValueEx(get_TrxName(), sql);

		if(lineID <= 0) throw new AdempiereException("El numero de bobina ingresado no existe");	

	}

}
