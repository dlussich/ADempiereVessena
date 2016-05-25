/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.model.Query;


/**
 * @author gbrust
 *
 */
public class MFduProdStore extends X_UY_Fdu_ProdStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1718756715071217986L;

	/**
	 * @param ctx
	 * @param UY_Fdu_ProdStore_ID
	 * @param trxName
	 */
	public MFduProdStore(Properties ctx, int UY_Fdu_ProdStore_ID, String trxName) {
		super(ctx, UY_Fdu_ProdStore_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduProdStore(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static List<MFduProdStore> getMFduProdStoreForInoviceType(Properties ctx, int uy_fdu_invoiceType_id){
		
		String whereClause = X_UY_Fdu_ProdStore.COLUMNNAME_UY_Fdu_InvoiceType_ID + "=" + uy_fdu_invoiceType_id;
		
		List<MFduProdStore> lines = new Query(ctx, I_UY_Fdu_ProdStore.Table_Name, whereClause, null)
		.list();
		
		return lines;		
	}
	
	public static String getStoresForInoviceType(Properties ctx, int uy_fdu_invoiceType_id){
		String retorno = "";
		String whereClause = X_UY_Fdu_ProdStore.COLUMNNAME_UY_Fdu_InvoiceType_ID + "=" + uy_fdu_invoiceType_id;
		
		List<MFduProdStore> lines = new Query(ctx, I_UY_Fdu_ProdStore.Table_Name, whereClause, null)
		.list();
		
		if (lines.size() > 0){
			for (MFduProdStore prodStore: lines){
				
				retorno = "'" + new MFduStore(ctx, prodStore.getUY_Fdu_Store_ID(), null).getValue() + "',";
			}			
		}
		return retorno;		
	}
	
	public static MFduProdStore getMFduProdStoreForStore(Properties ctx, int storeID){
		
		String whereClause = X_UY_Fdu_ProdStore.COLUMNNAME_UY_Fdu_Store_ID + "=" + storeID;
		
		MFduProdStore ret = new Query(ctx, I_UY_Fdu_ProdStore.Table_Name, whereClause, null).first();
		
		return ret;		
	}


}
