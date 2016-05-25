/**
 * 
 */
package org.openup.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.openup.util.ItalcredSystem;


/**
 * @author Nicolas
 *
 */
public class MDeliveryPoint extends X_UY_DeliveryPoint {

	private static final long serialVersionUID = -950709550900218324L;

	/**
	 * @param ctx
	 * @param UY_DeliveryPoint_ID
	 * @param trxName
	 */
	public MDeliveryPoint(Properties ctx, int UY_DeliveryPoint_ID,
			String trxName) {
		super(ctx, UY_DeliveryPoint_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDeliveryPoint(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
	
		if (!success) return success;
		
		// Cargo codigos postales para puntos que son correos
		if ((newRecord) && (!this.isOwn()) && (this.isPostOffice())){

			ItalcredSystem itsys = new ItalcredSystem();
			
			List<MDeliveyPointPostal> postals = itsys.getDeliveryPointPostals(getCtx(), get_TrxName());
			for (MDeliveyPointPostal postal: postals) {
				postal.setUY_DeliveryPoint_ID(this.get_ID());
				postal.saveEx();
			}
		}

		return true;

	}

	/**
	 * OpenUp. Guillermo Brust. 05/09/2013. ISSUE #
	 * Metodo que devuelve todos los registros de este modelo
	 * 
	 */
	public static List<MDeliveryPoint> getDeliveries(Properties ctx){	
		
		List<MDeliveryPoint> lines = new Query(ctx, I_UY_DeliveryPoint.Table_Name, "", null)
		.list();
		
		return lines;
	}


	/***
	 * Obtengo modelo segun value recibido.
	 * OpenUp Ltda. Issue #1170 
	 * @author Gabriel Vila - 06/08/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MDeliveryPoint forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_DeliveryPoint.COLUMNNAME_Value + "='" + value + "'";
		
		MDeliveryPoint model = new Query(ctx, I_UY_DeliveryPoint.Table_Name, whereClause, trxName)
		.first();
		
		
		return model;
	}
	
	public static MDeliveryPoint forUY_DeliveryPonint_ID(Properties ctx, int id, String trxName){
		
		String whereClause = X_UY_DeliveryPoint.COLUMNNAME_UY_DeliveryPoint_ID + "=" + id + "";
		
		MDeliveryPoint model = new Query(ctx, I_UY_DeliveryPoint.Table_Name, whereClause, trxName)
		.first();
		
		
		return model;
	}
	
	
	
	/***
	 * Obtiene modelo segun codigo interno recibido.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 27/08/2013
	 * @see
	 * @param ctx
	 * @param internalCode
	 * @param trxName
	 * @return
	 */
	public static MDeliveryPoint forInternalCode(Properties ctx, String internalCode, String trxName){
		
		String whereClause = X_UY_DeliveryPoint.COLUMNNAME_InternalCode + "='" + internalCode + "'";
		
		MDeliveryPoint model = new Query(ctx, I_UY_DeliveryPoint.Table_Name, whereClause, trxName)
		.first();
		
		
		return model;
	}
	
	/***
	 * Obtengo y devuelvo levantes pendientes para este courier
	 * OpenUp Ltda. Issue #1300
	 * @author Guillermo Brust - 13/09/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public HashMap<String, String> getLevantesPendientes(){
				
		HashMap<String, String> hash = new HashMap<String, String>();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		/*
		if(this.getValue().equals("marcopostal")) hash.put("250913", null);
		else if(this.getValue().equals("plaza")) hash.put("354385", "01/10/2013");		
		else if(this.getValue().equals("interpost")) hash.put("7889057", null);		
		*/
			
		//Acá tengo los levantes cuyos identificadores se encuentran asociados en las cuentas, esto significa que todavia esta pendiente de entrega esta cuenta
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		
		try{
			
			sql = " SELECT DISTINCT levante, fechaLevante FROM VUY_TT_LevanteOpen WHERE UY_DeliveryPoint_ID_Actual = " + this.get_ID(); // + " and levante='39322' ";
			 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);	
			rs = pstmt.executeQuery ();
			
			while (rs.next()){
				
				if(this.getValue().equals("marcopostal")) hash.put(rs.getString("levante"), null);
				else if(this.getValue().equals("plaza")) hash.put(rs.getString("levante"), df.format(rs.getTimestamp("fechaLevante")));
				else if(this.getValue().equals("interpost")) hash.put(rs.getString("levante"), null);
				
			}
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
				
		return hash;
	}
	
	
	/***
	 * Obtengo y devuelvo cuentas pendientes para este courier
	 * OpenUp Ltda. Issue #1374
	 * @author Guillermo Brust - 08/10/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public HashMap<Integer, MTTCard> getIncidenciasPendientes(){
						
		HashMap<Integer, MTTCard> hash = new HashMap<Integer, MTTCard>();
						
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		
		try{
			
			sql = " select distinct lev.uy_tt_card_id " +
				  " from vuy_tt_levanteopen lev " +
				  " inner join uy_tt_card card on lev.uy_tt_card_id = card.uy_tt_card_id " +
				  " inner join uy_tt_cardstatus status on card.uy_tt_cardstatus_id = status.uy_tt_cardstatus_id " +
				  " WHERE lev.uy_deliverypoint_id_actual ="+ this.get_ID() +
				  " and status.endtrackstatus='N' " ;
			 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);	
			rs = pstmt.executeQuery ();
			
			while (rs.next()){
				
				MTTCard card = new MTTCard(this.getCtx(), rs.getInt("UY_TT_Card_ID"), this.get_TrxName());
				hash.put(card.getUY_R_Reclamo_ID(), card);
				
			}	
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}			
			
		return hash;
	}

	/***
	 * Obtiene y retorna modelo segun valor recibido de subagencia.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Sep 28, 2015
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MDeliveryPoint forSubAgencyNo(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_DeliveryPoint.COLUMNNAME_SubAgencyNo + "='" + value + "'";
		
		MDeliveryPoint model = new Query(ctx, I_UY_DeliveryPoint.Table_Name, whereClause, trxName)
		.first();
		
		return model;
	}

	
}
