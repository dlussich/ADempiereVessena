package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.openup.model.X_UY_CargoLine;

public class MCargoLine extends X_UY_CargoLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7876094193105953256L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_CargoLine_ID
	 * @param trxName
	 */
	public MCargoLine(Properties ctx, int UY_CargoLine_ID, String trxName) {
		super(ctx, UY_CargoLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//OpenUp. Nicolas Sarlabos. 09/04/2013. #760
		if(newRecord){
			MCargo cargo = (MCargo) this.getUY_Cargo();
			List<MCargoLine> lines = cargo.getLines();

			if(lines.size() > 0) throw new AdempiereException("El maximo permitido es 1 linea");
		}

		return true;
		//Fin OpenUp.
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCargoLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	
}
