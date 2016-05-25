package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MManufManpower extends X_UY_ManufManpower {

	/**
	 * 
	 */
	private static final long serialVersionUID = 673318403083564031L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ManufManpower_ID
	 * @param trxName
	 */
	public MManufManpower(Properties ctx, int UY_ManufManpower_ID, String trxName) {
		super(ctx, UY_ManufManpower_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MManufManpower(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
	
		//OpenUp. Nicolas Sarlabos. 04/03/2013. #449. Se cambian tabulaciones por espacios en blanco para la
		//correcta impresion de la OF
		if(!this.getmanpower().equalsIgnoreCase("")){
			
			String cadena = this.getmanpower();
			
			cadena = cadena.replaceAll("\t", "     ");
			
			this.setmanpower(cadena);
				
		}
		//Fin OpenUp.
		return true;
	}

}
