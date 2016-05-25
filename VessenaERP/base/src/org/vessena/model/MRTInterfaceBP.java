/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author SBT
 *
 */
public class MRTInterfaceBP extends X_UY_RT_InterfaceBP {

	/**
	 * @param ctx
	 * @param UY_RT_InterfaceBP_ID
	 * @param trxName
	 */
	public MRTInterfaceBP(Properties ctx, int UY_RT_InterfaceBP_ID,
			String trxName) {
		super(ctx, UY_RT_InterfaceBP_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTInterfaceBP(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * * Obtiene y retorna registro a interfacear para un determinado cliente recibido.
	 * OpenUp Ltda. Issue #
	 * @author SBouissa - 01/10/2015
	 * @param ctx
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static MRTInterfaceBP forClientNotRead(Properties ctx, int mCBPartnerID,int mActionID,
			String trxName) {
//int mActionID,
		String whereClause = X_UY_RT_InterfaceBP.COLUMNNAME_C_BPartner_ID + "=" + mCBPartnerID +
				" AND " + X_UY_RT_InterfaceBP.COLUMNNAME_ReadingDate + " is null "+
				" AND " + X_UY_RT_InterfaceBP.COLUMNNAME_IsActive+" = 'Y'"; 
				if(0==mActionID){
					whereClause = whereClause + " AND "+ X_UY_RT_InterfaceBP.COLUMNNAME_UY_RT_Action_ID 
							+" <> " +MRTAction.forValue(ctx, "delete", trxName).get_ID();	
				}else{
					whereClause = whereClause + " AND "+ X_UY_RT_InterfaceBP.COLUMNNAME_UY_RT_Action_ID 
							+" = "+ mActionID;	
				}		
		MRTInterfaceBP model = new Query(ctx, I_UY_RT_InterfaceBP.Table_Name, whereClause, trxName)
		.setOrderBy(X_UY_RT_InterfaceBP.COLUMNNAME_Updated +" desc")
		.first();
		if(null!= model && 0<model.get_ID())
			return model;
		
		return null;
	}
	
	/**
	 * Retorna lista con las lineas sin leer, ordenadas por actualización y por accion (Insert primero,etc)
	 * @author SBT OpenUp Issue#4857 - 01-10-2015
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static List<MRTInterfaceBP> lstCustomersNotR(Properties ctx,String trxName) {
        String whereClause = X_UY_RT_InterfaceBP.COLUMNNAME_IsActive + " = 'Y'" +
                " AND " + X_UY_RT_InterfaceBP.COLUMNNAME_ReadingDate + " is null ";               
        String orderBy =  X_UY_RT_InterfaceBP.COLUMNNAME_Updated +","+ 
        		X_UY_RT_InterfaceBP.COLUMNNAME_UY_RT_Action_ID;
        		//--1000000  I 
			    //--1000001  U
			    //--1000002  D
       
        List<MRTInterfaceBP> lines = new Query(ctx, I_UY_RT_InterfaceBP.Table_Name, whereClause, trxName).setOrderBy(orderBy).list();
        if(0<lines.size()){
        	return lines;
        }else{
        	return null;
        }
        
    }

}
