/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 09/05/2012
 */
 
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

/**
 * org.openup.model - MClockInterfaceDetail
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 09/05/2012
 * @see
 */
public class MClockInterfaceDetail extends X_UY_ClockInterface_Detail {

	private static final long serialVersionUID = 4635043531429276109L;

	@Override
	protected boolean beforeDelete() {
		
		if(this.ismanual()){
			return true;
		}else throw new AdempiereException ("No se permite borrar registros NO manuales");
			
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ClockInterface_Detail_ID
	 * @param trxName
	 */
	public MClockInterfaceDetail(Properties ctx,
			int UY_ClockInterface_Detail_ID, String trxName) {
		super(ctx, UY_ClockInterface_Detail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MClockInterfaceDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
