/**
 * @author OpenUp SBT Issue#  16/3/2016 15:40:15
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MOrg;
import org.compiere.model.Query;


/**
 * @author OpenUp SBT Issue#  16/3/2016 15:40:15
 *
 */
public class MRTConfigIdOrg extends X_UY_RT_Config_IdOrg {

	/**
	 * @author OpenUp SBT Issue#  16/3/2016 15:40:47
	 * @param ctx
	 * @param UY_RT_Config_IdOrg_ID
	 * @param trxName
	 */
	public MRTConfigIdOrg(Properties ctx, int UY_RT_Config_IdOrg_ID,
			String trxName) {
		super(ctx, UY_RT_Config_IdOrg_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author OpenUp SBT Issue#  16/3/2016 15:40:47
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTConfigIdOrg(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Retorna los datos parametrizados para la compania correspondiente
	 * @author OpenUp SBT Issue#  16/3/2016 16:28:40
	 * @param ctx
	 * @param mClientID
	 * @param trxName
	 * @return
	 */
	public static List<MRTConfigIdOrg> getIds(Properties ctx,int mClientID, String trxName){
		
		String whereClause = X_UY_RT_Config_IdOrg.COLUMNNAME_AD_Client_ID + "=" +mClientID+
				" AND " + X_UY_RT_Config_IdOrg.COLUMNNAME_IsActive+ "='Y' ";
		
		List<MRTConfigIdOrg> lines = new Query(ctx, I_UY_RT_Config_IdOrg.Table_Name, whereClause, trxName)
		.setOrderBy(X_UY_RT_Config_IdOrg.COLUMNNAME_identifempresa)
		.list();
		
		return lines;
	}

	/**
	 * 
	 * @author OpenUp SBT Issue #5555  16/3/2016 18:21:00
	 * @param ctx
	 * @param mClientID
	 * @param trxName
	 * @return
	 */
	public static int[] getDistinctsIds(Properties ctx, int mClientID,
			String trxName) {
		int[] retorno = null;
		int unID=0;
		List<MRTConfigIdOrg> lines  = getIds(ctx,mClientID,trxName);
		int i = 0;
		for (MRTConfigIdOrg id:lines){
			if(unID==0||unID!=id.getidentifempresa()){
				retorno[i++] = id.getidentifempresa();
			}
		}
		return retorno;
	}

	/**
	 * Retorno lista de organizaciones (sucursales) asociadas al id de empresa recibido.
	 * @author OpenUp SBT Issue#  16/3/2016 18:30:59
	 * @param ctx
	 * @param idEmpres
	 * @param trxName
	 * @return
	 */
	@SuppressWarnings("null")
	public static List<MOrg> getOrgsXIdEmpresa(Properties ctx, int idEmpres,
			String trxName) {
		
		List<MOrg> listaOrg = null;
		
		String whereClause = X_UY_RT_Config_IdOrg.COLUMNNAME_identifempresa + "=" +idEmpres+
				" AND " + X_UY_RT_Config_IdOrg.COLUMNNAME_IsActive+ "='Y' ";
		
		List<MRTConfigIdOrg> lines = new Query(ctx, I_UY_RT_Config_IdOrg.Table_Name, whereClause, trxName)
		.setOrderBy(X_UY_RT_Config_IdOrg.COLUMNNAME_identifempresa)
		.list();
		
		for(MRTConfigIdOrg idEmp : lines){
			MOrg e = new MOrg(ctx, idEmp.getAD_Org_ID_To(), null);
			listaOrg.add(e);
		}
		
		return listaOrg;
		
			
	}

	public static int getIdEmpresaXOrg(Properties ctx, int mAD_Org_ID_To,
			String trxName) {
		String whereClause = X_UY_RT_Config_IdOrg.COLUMNNAME_AD_Org_ID_To + "=" +mAD_Org_ID_To+
				" AND " + X_UY_RT_Config_IdOrg.COLUMNNAME_IsActive+ "='Y' ";
		
		MRTConfigIdOrg model = new Query(ctx, I_UY_RT_Config_IdOrg.Table_Name, whereClause, trxName).first();
		if(null!=model)
			return model.getidentifempresa();
		else return 0;
	}
	
}
