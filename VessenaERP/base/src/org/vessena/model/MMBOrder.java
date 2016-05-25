package org.openup.model;


import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MPeriod;
import org.compiere.process.DocumentEngine;



public class MMBOrder extends X_UY_MB_Order{

	private static final long serialVersionUID = 6812053230498198982L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_MB_Order_ID
	 * @param trxName
	 */
	public MMBOrder(Properties ctx, int UY_MB_Order_ID, String trxName) {
		super(ctx, UY_MB_Order_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMBOrder(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (!success){
			return success;
		}
		
		if (newRecord){
			MOrder o = new MOrder(this.getCtx(),0,this.get_TrxName());
			o.set_ValueOfColumn("UY_MB_Order_ID", this.get_ID());
			o.setAD_Client_ID(this.getAD_Client_ID());
			o.setAD_Org_ID(this.getAD_Org_ID());
			MDocType doctype= MDocType.forValue(this.getCtx(), "salesorder", null);
			o.setC_DocTypeTarget_ID(doctype.get_ID()); // se agrega por error que da al completar el documento - SylvieBouissa 06102014
			o.setC_DocType_ID(doctype.get_ID());
			o.setDateOrdered(this.getDateOrdered());
			o.setDatePromised(this.getDatePromised());
			o.setC_BPartner_ID(this.getC_BPartner_ID());
			o.setC_BPartner_Location_ID(this.getC_BPartner_Location_ID());
			o.setSalesRep_ID(this.getSalesRep_ID());
			o.setM_PriceList_ID(this.getM_PriceList_ID());
			o.setC_Currency_ID(this.getC_Currency_ID());
			o.setC_PaymentTerm_ID(this.getC_PaymentTerm_ID());		
			o.setGrandTotal(this.getGrandTotal());
			o.setTotalLines(this.getTotalLines());
			o.set_ValueOfColumn("UY_MB_Device_ID", this.getUY_MB_Device_ID());
			o.setDocStatus(DOCSTATUS_Drafted);
			o.setDocAction(DOCACTION_Complete);
			o.setDescription(this.getDescription());
			o.setuy_credit_approved(true);
			o.setM_Warehouse_ID(1000017);
			o.saveEx();
		}
		else{
			
			if (is_ValueChanged(COLUMNNAME_IsSynced)){
				if (this.isSynced()){
					MOrder order = MOrder.getforMBOrder(getCtx(), this.get_ID(), get_TrxName());
					MPeriod period = MPeriod.get (getCtx(), order.getDateAcct(), order.getAD_Org_ID());
					int C_Calendar_ID = MPeriod.getC_Calendar_ID(getCtx(),order.getAD_Org_ID());
					if(period!=null){					
						System.out.println(order.getAD_Org_ID());
						System.out.println(order.getAD_OrgTrx_ID());
					}else{
						System.out.println("Period es null");
					}
					if(C_Calendar_ID>0){
						System.out.println(C_Calendar_ID);
					}else{
						System.out.println("C_Calendar_Id is null");

					}
					
					if (!order.processIt(DocumentEngine.ACTION_Complete)){
						System.out.println(order.getAD_Org_ID());
						System.out.println(order.getProcessMsg());
						//throw new AdempiereException(order.getProcessMsg());
					}else{
						System.out.println(order.getProcessMsg());
					}
				}
			}
			
		}
		
		return true;
	}


	

}
