/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRFQRequisitionSection
 * OpenUp Ltda. Issue #379 
 * Description: Modelo para detalle de solicitud de cotizacion a proveedor por sector de compra.
 * @author Gabriel Vila - 21/02/2013
 * @see
 */
public class MRFQRequisitionSection extends X_UY_RFQ_RequisitionSection {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4640296124178035445L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RFQ_RequisitionSection_ID
	 * @param trxName
	 */
	public MRFQRequisitionSection(Properties ctx,
			int UY_RFQ_RequisitionSection_ID, String trxName) {
		super(ctx, UY_RFQ_RequisitionSection_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRFQRequisitionSection(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
