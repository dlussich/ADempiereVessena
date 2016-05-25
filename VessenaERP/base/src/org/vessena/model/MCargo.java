package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.openup.model.X_UY_Cargo;

public class MCargo extends X_UY_Cargo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7370577687775278415L;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Cargo_ID
	 * @param trxName
	 */
	public MCargo(Properties ctx, int UY_Cargo_ID, String trxName) {
		super(ctx, UY_Cargo_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCargo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/***
	 * Obtiene y retorna lista de lineas de este cargo.
	 * OpenUp Ltda. Issue #760
	 * @author Nicolas Sarlabos - 09/05/2013
	 * @see
	 * @return
	 */
	public List<MCargoLine> getLines(){
		
		String whereClause = X_UY_Cargo.COLUMNNAME_UY_Cargo_ID + "=" + this.get_ID();
		
		List<MCargoLine> lines = new Query(getCtx(), I_UY_CargoLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}
	

}
