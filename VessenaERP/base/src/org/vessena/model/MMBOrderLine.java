package org.openup.model;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MUOMConversion;



public class MMBOrderLine extends X_UY_MB_OrderLine {

	private static final long serialVersionUID = 1886585464577304669L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_MB_OrderLine_ID
	 * @param trxName
	 */
	public MMBOrderLine(Properties ctx, int UY_MB_OrderLine_ID, String trxName) {
		super(ctx, UY_MB_OrderLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMBOrderLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	/**
	 * 
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success){
			return success;
		}

		MOrderLine ol = new MOrderLine(this.getCtx(), 0,this.get_TrxName());
		
		MOrder o = MOrder.getforMBOrder(this.getCtx(),this.getUY_MB_Order_ID(),this.get_TrxName());

		if(o==null){
			throw new AdempiereException("No se encuentra la orden asociada a la linea");
		}
		
		ol.setC_Order_ID(o.get_ID());		
		ol.set_ValueOfColumn("UY_MB_OrderLine_ID", this.get_ID());
		ol.setM_Product_ID(this.getM_Product_ID());
		ol.setC_UOM_ID(this.getC_UOM_ID());
		ol.setQtyEntered(this.getQtyEntered());

		BigDecimal QtyOrdered = MUOMConversion.convertProductFrom (getCtx(), this.getM_Product_ID(), 
				this.getC_UOM_ID(), this.getQtyEntered());
		if (QtyOrdered == null)
			QtyOrdered = this.getQtyEntered();		
		
		ol.setQtyOrdered(QtyOrdered);
		//INI OPENUP Sylvie Bouissa - 24/09/2014 se agregan los datos:
		ol.setAD_Org_ID(this.getAD_Org_ID());
		ol.setAD_Client_ID(this.getAD_Client_ID());
		//FIN OPENUP Sylvie Bouissa - 24/09/2014 se agregan los datos:
		
		//MUOMConversion conv = new MUOMConversion(this.getCtx(),this.getC_UOM_ID(), this.get_TrxName());	
		//ol.setQtyOrdered(this.getQtyEntered().multiply(conv.getDivideRate()));

		//ol.setPriceList(this.getPriceList());
		//ol.setPriceActual(this.getPriceActual());
		//ol.setPriceEntered(this.getPriceEntered());
		//ol.setFlatDiscount(this.getFlatDiscount());
		ol.setDiscount(this.getDiscount());
		ol.setC_Tax_ID(this.getC_Tax_ID());
		//ol.setLineNetAmt(this.getLineNetAmt());
		//ol.setuy_promodiscount(this.getuy_promodiscount());
		ol.saveEx();
		
		return true;
	}



}
