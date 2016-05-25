/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 03/09/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MTTBoxCard
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo para informacion de elemento contenido en una caja desde la perspectiva
 * de la caja.
 * @author Gabriel Vila - 03/09/2013
 * @see
 */
public class MTTBoxCard extends X_UY_TT_BoxCard {

	private static final long serialVersionUID = -708925567775361464L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_BoxCard_ID
	 * @param trxName
	 */
	public MTTBoxCard(Properties ctx, int UY_TT_BoxCard_ID, String trxName) {
		super(ctx, UY_TT_BoxCard_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTBoxCard(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 12/09/2013. ISSUE #
	 * Método que devuelve un modelo de de esta tabla que contenga la boxID y la CardID
	 * 
	 */
	public static MTTBoxCard forBoxIDAndCardID(Properties ctx, String trxName, int boxID, int cardID){
		
		String whereClause = X_UY_TT_BoxCard.COLUMNNAME_UY_TT_Box_ID + " = " + boxID +
							" AND " + X_UY_TT_BoxCard.COLUMNNAME_UY_TT_Card_ID + " = " + cardID;
		
		MTTBoxCard boxCard = new Query(ctx, I_UY_TT_BoxCard.Table_Name, whereClause, trxName)
		.first();		
		
		return boxCard;
	}

	
	/***
	 * Obtiene y retorna lista de cajas que contienen una determinada cuenta. 
	 * Aplica solamente para cuentas con mas de un cardcarrier.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 13/09/2013
	 * @see
	 * @param ctx
	 * @param cardID
	 * @param trxName
	 * @return
	 */
	public static List<MTTBoxCard> getCardBoxes(Properties ctx, int cardID, String trxName){
		
		String whereClause = X_UY_TT_BoxCard.COLUMNNAME_UY_TT_Card_ID + "=" + cardID;
		
		List<MTTBoxCard> lines = new Query(ctx, I_UY_TT_BoxCard.Table_Name, whereClause, trxName).list();
		
		return lines;

	}
	
}
