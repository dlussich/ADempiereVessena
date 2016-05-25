/**
 * 
 */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author IN
 *
 */
public class MBPartnerProduct extends X_C_BPartner_Product {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4151371066153731295L;


	/**
	 * @param ctx
	 * @param C_BPartner_Product_ID
	 * @param trxName
	 */
	public MBPartnerProduct(Properties ctx, int C_BPartner_Product_ID,
			String trxName) {
		super(ctx, C_BPartner_Product_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBPartnerProduct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/***
	 * Retorna modelo de linea de proveedor asociado al producto, segun proveedor y producto recibidos.
	 * OpenUp Ltda. Issue #4403.
	 * @author INes Fernandez - 20/07/2015
	 * @see
	 * @return
	 */
	public static MBPartnerProduct forBPProduct(Properties ctx,int partnerID, int productID, String trxName) {
		
		String whereClause = X_C_BPartner_Product.COLUMNNAME_C_BPartner_ID + "=" + partnerID + " and " + 
				X_C_BPartner_Product.COLUMNNAME_M_Product_ID + "=" + productID;
		
		MBPartnerProduct model = new Query(ctx, I_C_BPartner_Product.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();	
		
		return model;
	}
	
	/***
	 * Limpia los campos asociados a las cond de negocio.
	 * OpenUp Ltda. Issue #4527.
	 * @author INes Fernandez - 22/07/2015
	 * @see
	 * @return
	 */
	public void clearCondNegocio(){
		this.setUY_DtoOperativo(BigDecimal.ZERO);
		this.setUY_DtoFinancFact(BigDecimal.ZERO);
		this.setUY_DtoFinancFueraFact(BigDecimal.ZERO);
		this.setUY_DtoFianancAlPago(BigDecimal.ZERO);
		this.setUY_PorcentajeRetorno(BigDecimal.ZERO);
		this.setUY_IsRetorno(false);
		this.setUY_IsBonifS(false);
		this.setUY_IsBonifC(false);
	}
	
	

}
