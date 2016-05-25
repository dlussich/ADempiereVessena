/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/06/2012
 */
 
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRFQRequisitionLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 20/06/2012
 * @see
 */
public class MRFQRequisitionLine extends X_UY_RFQ_RequisitionLine {

	private static final long serialVersionUID = 4861087459833701949L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RFQ_RequisitionLine_ID
	 * @param trxName
	 */
	public MRFQRequisitionLine(Properties ctx, int UY_RFQ_RequisitionLine_ID,
			String trxName) {
		super(ctx, UY_RFQ_RequisitionLine_ID, trxName);

		if (UY_RFQ_RequisitionLine_ID <= 0){
			this.setM_AttributeSetInstance_ID(0);
		}
		
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRFQRequisitionLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
