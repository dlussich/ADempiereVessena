package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MCashFlowConcept extends X_UY_CashFlow_Concept {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1339003082917252507L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_CashFlow_Concept_ID
	 * @param trxName
	 */
	public MCashFlowConcept(Properties ctx, int UY_CashFlow_Concept_ID, String trxName) {
		super(ctx, UY_CashFlow_Concept_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashFlowConcept(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
