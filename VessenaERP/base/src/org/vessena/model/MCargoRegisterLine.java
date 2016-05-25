package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

public class MCargoRegisterLine extends X_UY_CargoRegisterLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6148184380752315664L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_CargoRegisterLine_ID
	 * @param trxName
	 */
	public MCargoRegisterLine(Properties ctx, int UY_CargoRegisterLine_ID,
			String trxName) {
		super(ctx, UY_CargoRegisterLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCargoRegisterLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(newRecord){
			
			MCargo cargo = new MCargo (getCtx(),this.getUY_Cargo_ID(),get_TrxName());

			if(cargo.get_ID()>0){
				
				List<MCargoLine> lines = cargo.getLines();

				if(lines.size()==0) throw new AdempiereException ("El cargo seleccionado no tiene lineas");
			}
			
		}		
		
		return true;
	}

}
