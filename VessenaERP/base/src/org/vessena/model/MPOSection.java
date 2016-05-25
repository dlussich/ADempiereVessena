/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 03/11/2012
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;

/**
 * org.openup.model - MPOSection
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 03/11/2012
 * @see
 */
public class MPOSection extends X_UY_POSection {

	private static final long serialVersionUID = 8046868546628802849L;

	public int userAppSeqNo = 0;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_POSection_ID
	 * @param trxName
	 */
	public MPOSection(Properties ctx, int UY_POSection_ID, String trxName) {
		super(ctx, UY_POSection_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPOSection(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna modelo de categoria de sector segun id de categoria recibido.
	 * OpenUp Ltda. Issue #88 
	 * @author Gabriel Vila - 03/11/2012
	 * @see
	 * @param mProductCategoryID
	 * @return
	 */
	public MPOSectionCategory getSectionCategory(int mProductCategoryID) {

		String whereClause = X_UY_POSectionCategory.COLUMNNAME_UY_POSection_ID + "=" + this.get_ID() +
				" AND " + X_UY_POSectionCategory.COLUMNNAME_M_Product_Category_ID + "=" + mProductCategoryID;
		
		MPOSectionCategory value = new Query(getCtx(), I_UY_POSectionCategory.Table_Name, whereClause, null)
		.first();
		
		return value;
		
	}

	
	/***
	 * Obtiene y retorna rango de monto segun monto recibido y politica de compra de este sector.
	 * OpenUp Ltda. Issue #92 
	 * @author Gabriel Vila - 05/11/2012
	 * @see
	 * @param dateTrx
	 * @param amt
	 * @param cCurrencyID
	 * @param currencyRate
	 * @return
	 */
	public MPOPolicyRange getPolicyRange(Timestamp dateTrx, BigDecimal amt, int cCurrencyID, BigDecimal currencyRate){
		
		MPOPolicy policy = new MPOPolicy(getCtx(), this.getUY_POPolicy_ID(), null);
		if (policy.get_ID() <= 0){
			throw new AdempiereException("Falta definir politica de compra para la seccion : " + this.getName());
		}
		
		return policy.getRangeForAmount(dateTrx, amt, cCurrencyID, currencyRate);
	}
	
	
	/***
	 * Obtiene usuario autorizador segun rango de monto de la politica
	 * de este sector de compra.
	 * OpenUp Ltda. Issue #92 
	 * @author Gabriel Vila - 05/11/2012
	 * @see
	 * @param nivel
	 * @param dateTrx
	 * @param amt
	 * @param cCurrencyID
	 * @param currencyRate
	 * @return
	 */
	public int getApprovalUser(String nivel, int uyPOPolicyRangeID) {

		int value = 0;
		
		try{

			MPOPolicyRange rango = new MPOPolicyRange(getCtx(), uyPOPolicyRangeID, null);

			String whereClause = "uy_popolicyrange_id =" + rango.get_ID() + 
								 " and uy_posection_id =" + this.get_ID() +
								 " and nivel='" + nivel + "'" +
								 " and seqno is not null ";
			
			MPOPolicyUser poluser = new Query(getCtx(), I_UY_POPolicyUser.Table_Name, whereClause, null)
			.setOnlyActiveRecords(true)
			.setOrderBy(" seqno ")
			.first();
			
			if ((poluser == null) || (poluser.get_ID() <= 0)){
				value = 0;
				this.userAppSeqNo = 0;
			}
			else{
				value = poluser.getAD_User_ID();
				this.userAppSeqNo = poluser.getSeqNo();
			}
					
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
		return value;
	}


	/***
	 * Verifica si para un determinado rango de politica de compra de este sector, se
	 * necesita de un segundo nivel de aprobacion.
	 * OpenUp Ltda. Issue #92 
	 * @author Gabriel Vila - 05/11/2012
	 * @see
	 * @param uyPOPolicyRangeID
	 * @return
	 */
	public boolean needSecondApprove(int uyPOPolicyRangeID){
		
		boolean value = false;
		
		try{

			MPOPolicyRange rango = new MPOPolicyRange(getCtx(), uyPOPolicyRangeID, null);

			String whereClause = " uy_popolicyrange_id =" + rango.get_ID() + 
								 " and uy_posection_id =" + this.get_ID() +
								 " and nivel='" + X_UY_POPolicyUser.NIVEL_Nivel2 + "'";
			MPOPolicyUser poluser = new Query(getCtx(), I_UY_POPolicyUser.Table_Name, whereClause, null)
			.setOnlyActiveRecords(true)
			.first();
			
			if ((poluser == null) || (poluser.get_ID() <= 0)){
				value = false;
			}
			else{
				value = true;	
			}
					
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
		return value;
	}
	
	
	/***
	 * Dado un usuario obtiene el siguiente usuario autorizador para un determinado nivel, rango, sector, ordenado por secuencia.
	 * OpenUp Ltda. Issue #92 
	 * @author Gabriel Vila - 08/03/2014
	 * @see
	 * @param nivel
	 * @param uyPOPolicyRangeID
	 * @param pastADUserID
	 * @return
	 */
	public int getNextApprovalUser(String nivel, int uyPOPolicyRangeID, int pastADUserID, int pastSeqNo) {

		int value = 0;
		
		try{

			MPOPolicyRange rango = new MPOPolicyRange(getCtx(), uyPOPolicyRangeID, null);

			String whereClause = " uy_popolicyrange_id =" + rango.get_ID() + 
								 " and uy_posection_id =" + this.get_ID() +
								 " and nivel='" + nivel + "'" +
								 " and seqno is not null " +
								 " and seqno >" + pastSeqNo +
								 " and ad_user_id !=" + pastADUserID;

			MPOPolicyUser poluser = new Query(getCtx(), I_UY_POPolicyUser.Table_Name, whereClause, null)
			.setOnlyActiveRecords(true)
			.setOrderBy(" seqno ")
			.first();
			
			if ((poluser == null) || (poluser.get_ID() <= 0)){
				value = 0;
				this.userAppSeqNo = 0;
			}
			else{
				value = poluser.getAD_User_ID();
				this.userAppSeqNo = poluser.getSeqNo();
			}
					
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
		return value;

	}
	
}
