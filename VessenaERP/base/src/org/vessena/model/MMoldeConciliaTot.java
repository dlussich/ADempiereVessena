package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

public class MMoldeConciliaTot extends X_UY_Molde_ConciliaTot {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1929390069769509919L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Molde_ConciliaDet_ID
	 * @param trxName
	 */
	public MMoldeConciliaTot(Properties ctx, int UY_Molde_ConciliaTot_ID, String trxName) {
		super(ctx, UY_Molde_ConciliaTot_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMoldeConciliaTot(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna modelo para un determinado detalle que puede ser del sistema o del banco.
	 * OpenUp Ltda. Issue #741 
	 * @author Gabriel Vila - 25/04/2013
	 * @see
	 * @param ctx
	 * @param uyMMoldeConciliaDetID
	 * @param isSistema
	 * @param trxName
	 * @return
	 */
	public static MMoldeConciliaTot forConciliaDet(Properties ctx, int uyMMoldeConciliaDetID, boolean isSistema, String trxName){
		
		String campo = (isSistema) ? X_UY_Molde_ConciliaTot.COLUMNNAME_uy_molde_conciliadet_id_sys : X_UY_Molde_ConciliaTot.COLUMNNAME_uy_molde_conciliadet_id_bk; 
		
		String whereClause = campo + "=" + uyMMoldeConciliaDetID;
		
		MMoldeConciliaTot value = new Query(ctx, I_UY_Molde_ConciliaTot.Table_Name, whereClause, trxName)
		.first();
		
		return value;
	}
	
}
