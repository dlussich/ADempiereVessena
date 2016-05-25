package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MRemuneration extends X_C_Remuneration{

	private static final long serialVersionUID = 8633572872910697539L;
	
	public MRemuneration(Properties ctx, int C_Remuneration_ID, String trxName) {
		super(ctx, C_Remuneration_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MRemuneration(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/***
	 * Obtiene y retorna tipo de remuneracion segun el nombre recibido
	 * OpenUp Ltda. Issue #1759
	 * @author Nicolas Sarlabos - 21/01/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRemuneration forName(Properties ctx, String value, String trxName){
		
		String whereClause = X_C_Remuneration.COLUMNNAME_Name + "='" + value + "'";
		
		MRemuneration rem = new Query(ctx, I_C_Remuneration.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return rem;
	}
	

}
