/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MBPartner;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author Nicolas
 *
 */
public class MTRDriver extends X_UY_TR_Driver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5185340217225265600L;

	/**
	 * @param ctx
	 * @param UY_TR_Driver_ID
	 * @param trxName
	 */
	public MTRDriver(Properties ctx, int UY_TR_Driver_ID, String trxName) {
		super(ctx, UY_TR_Driver_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRDriver(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		String name = "";

		if(this.getFirstSurname() != null){

			name += this.getFirstSurname().trim();

			if(this.getSecondSurname() != null && !this.getSecondSurname().equalsIgnoreCase("")){
				name += " " + this.getSecondSurname().trim() + " ";
			}else name += " ";

			if(this.getFirstName() != null){
				name += this.getFirstName().trim();
			}

			if(this.getSecondName() != null && !this.getSecondName().equalsIgnoreCase("")){
				name += " " + this.getSecondName().trim();
			}

			if(name != null && !name.equalsIgnoreCase("")) {
				name = name.trim();
				this.setName(name.toUpperCase());

			}
		}			
		
		//obtengo, si existe, el empleado asociado a este conductor
		MBPartner partner = MBPartner.forDriver(getCtx(), this.get_ID(), get_TrxName());
		
		//si obtuve empleado asociado seteo atributos comunes
		if(partner != null) this.updatePartner(partner.get_ID());
			
		return true;
	}
	
	/***
	 * Actualiza datos del empleado recibido como parametro.
	 * OpenUp Ltda. Issue #1603
	 * @author Nicolas Sarlabos - 27/11/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public void updatePartner(int partnerID){

		String sql = "update c_bpartner set firstname = '" + this.getFirstName() + "', firstsurname = '" + this.getFirstSurname() + "'," +
				" secondname = '" + this.getSecondSurname() + "', secondsurname = '" + this.getSecondSurname() + "', nationality = '" + this.getnationality() + "'," +
				" nationalcode = '" + this.getNationalCode() + "', startdate = '" + this.getStartDate() + "' where c_bpartner_id = " + partnerID;

		DB.executeUpdateEx(sql, get_TrxName());		

	}
	
	/***
	 * Obtiene y retorna un conductor segun value recibido
	 * OpenUp Ltda. Issue #1609
	 * @author Nicolas Sarlabos - 28/11/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRDriver forValue(Properties ctx, String value, String trxName){
		
		MTRDriver driver = null;
		
		if(value != null){
			
			value = value.toLowerCase().trim();
			
			String whereClause = " lower(" + X_UY_TR_Driver.COLUMNNAME_Value + ")='" + value + "'";
			
			driver = new Query(ctx, I_UY_TR_Driver.Table_Name, whereClause, trxName)
			.setClient_ID()
			.first();			
			
		}
				
		return driver;
	}
	
	/***
	 * Obtiene y retorna un conductor segun ID de socio de negocio recibido.
	 * OpenUp Ltda. Issue #4340
	 * @author Nicolas Sarlabos - 11/06/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRDriver forPartner(Properties ctx, int partnerID, String trxName){

		MTRDriver driver = null;	

		if(partnerID >0){

			String whereClause = X_UY_TR_Driver.COLUMNNAME_C_BPartner_ID + "=" + partnerID;

			driver = new Query(ctx, I_UY_TR_Driver.Table_Name, whereClause, trxName)
			.setClient_ID()
			.first();	
			
		}

		return driver;
	}

}
