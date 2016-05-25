/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.openup.model.X_UY_RT_InterfaceScales;

/**OpenUp Ltda Issue#
 * @author sevans Jan 20, 2016
 *
 */
public class MRTInterfaceScales extends X_UY_RT_InterfaceScales {
	private static String fchTodayUpdate;
	private static final String SEPARATOR_L = ":";
	
	/**
	 * @param ctx
	 * @param UY_RT_InterfaceScales_ID
	 * @param trxName
	 */
	public MRTInterfaceScales(Properties ctx, int UY_RT_InterfaceScales_ID,
			String trxName) {
		super(ctx, UY_RT_InterfaceScales_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTInterfaceScales(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
		
	
	// OpenUp. Santiago Evans. 20/01/2016. Issue #5319
	// Se agrega una fila en la tabla de interfaz.	
	public void toInterfaceScalesRow(int prod_ID, String mUPC){
		try{

			MProduct prod = new MProduct(getCtx(), prod_ID, get_TrxName());
			
			if(mUPC.equalsIgnoreCase("0") || mUPC.equals("") ){
				MProductUpc prodUpc = MProductUpc.forProduct(getCtx(), prod_ID, get_TrxName());
				if (null!=prodUpc && 0<prodUpc.get_ID())mUPC = prodUpc.getUPC();
			}
			
			if(!(mUPC.equalsIgnoreCase("0")) &&  !(mUPC.equals(""))){
				this.setM_Product_ID(prod_ID);
				this.setcodprod(prod.getValue());
				this.setDescription(prod.getName());
				this.setAD_Org_ID(prod.getAD_Org_ID());
				this.setAction(MSysConfig.getValue("INSERT_SCALE_PRODUCT",0));						
				this.setcodupc(mUPC);
				
				this.saveEx();
			}
			
		}
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	} // toInterfaceScalesRow	

	
	// OpenUp. Santiago Evans. 18/02/2016. Issue #5319
	// Se agrega una fila en la tabla de interfaz.	
	public void deleteInterfaceScalesRow(int prod_ID, String mUPC){
		try{

			MProduct prod = new MProduct(getCtx(), prod_ID, get_TrxName());
			
			if(mUPC.equalsIgnoreCase("0") || mUPC.equals("") ){
				//En caso que se haya desactivado un producto se marcan como eliminados todos sus upc
				List<MProductUpc> prodUpc = prod.getUpcLines();
				if(prodUpc.size()>0){
					for(MProductUpc upc: prodUpc){
						MRTInterfaceScales sc = new MRTInterfaceScales(getCtx(), this.getUY_RT_InterfaceScales_ID(),get_TrxName());
						sc.setM_Product_ID(prod_ID);
						sc.setcodprod(prod.getValue());
						sc.setDescription(prod.getName());
						sc.setAD_Org_ID(prod.getAD_Org_ID());
						sc.setAction(MSysConfig.getValue("DELETE_SCALE_PRODUCT",0));						
						sc.setcodupc(upc.getUPC());					
						sc.saveEx();						
					}
				}else{
					MRTInterfaceScales sc = new MRTInterfaceScales(getCtx(), this.getUY_RT_InterfaceScales_ID(),get_TrxName());
					sc.setM_Product_ID(prod_ID);
					sc.setcodprod(prod.getValue());
					sc.setDescription(prod.getName());
					sc.setAD_Org_ID(prod.getAD_Org_ID());
					sc.setAction(MSysConfig.getValue("DELETE_SCALE_PRODUCT",0));						
					sc.setcodupc(prod.getValue());					
					sc.saveEx();
				}
				
			}else{
				this.setM_Product_ID(prod_ID);
				this.setcodprod(prod.getValue());
				this.setDescription(prod.getName());
				this.setAD_Org_ID(prod.getAD_Org_ID());
				this.setAction(MSysConfig.getValue("DELETE_SCALE_PRODUCT",0));						
				this.setcodupc(mUPC);
				
				this.saveEx();				
			}

			
		}
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	} // deleteInterfaceScalesRow		
	
	// OpenUp. Santiago Evans. 22/01/2016. Issue #5319
	// Se buscan en la tabla UY_RT_InterfaceScales las filas que no fueron procesadas.	
    public static List<MRTInterfaceScales> forScalesNotRead(Properties ctx,String trxName) {
        String whereClause = X_UY_RT_InterfaceScales.COLUMNNAME_IsActive+ " = 'Y'" +
                " AND " + X_UY_RT_InterfaceScales.COLUMNNAME_ReadingDate + " is null ";
               
        String orderBy =  X_UY_RT_InterfaceScales.COLUMNNAME_Created;
       
        List<MRTInterfaceScales> lines = new Query(ctx, I_UY_RT_InterfaceScales.Table_Name, whereClause, trxName).setOrderBy(orderBy).list();
        if(0<lines.size()){
        	return lines;
        }else{
        	return null;
        }
        
    }// forUPCNotRead	
    
    
	// OpenUp. Santiago Evans. 25/01/2016. Issue #5319
	// Se marcan como leidas las filas.	    
    public static void markScalesAsRead(Properties ctx, MRTInterfaceScales row, String trxName) {
		String[] hra = (new Timestamp (System.currentTimeMillis()).toString().split(SEPARATOR_L));
		String fecha =hra[0].replace("-", "").replace(" ", "_")+hra[1];
		fchTodayUpdate = fecha;    	
		String where = " WHERE "+MRTInterfaceScales.COLUMNNAME_UY_RT_InterfaceScales_ID +"="+row.getUY_RT_InterfaceScales_ID();
		String sql = "UPDATE "+MRTInterfaceScales.Table_Name+" SET "
					+MRTInterfaceScales.COLUMNNAME_ReadingDate+ " = '"+ fchTodayUpdate.toString()+"'";//" = (SELECT CURRENT_DATE) ";
		sql = sql +", "+MRTInterfaceScales.COLUMNNAME_Updated+" = (SELECT now()) " ;
		DB.executeUpdateEx(sql+where,trxName);
    }
    
}
