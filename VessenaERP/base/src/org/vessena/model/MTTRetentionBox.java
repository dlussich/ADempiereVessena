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
public class MTTRetentionBox extends X_UY_TT_RetentionBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6256481434744318997L;

	@Override
	protected boolean beforeSave(boolean newRecord) {

		this.setIsRetained(((MTTBox) this.getUY_TT_Box()).isRetained());
		return true;
	}

	/**
	 * @param ctx
	 * @param UY_TT_RetentionBox_ID
	 * @param trxName
	 */
	public MTTRetentionBox(Properties ctx, int UY_TT_RetentionBox_ID,
			String trxName) {
		super(ctx, UY_TT_RetentionBox_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeDelete() {
		
		((MTTBox) this.getUY_TT_Box()).setBoxStatus(X_UY_TT_Box.BOXSTATUS_Cerrado);
		return true;
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTRetentionBox(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 03/10/2013. ISSUE #
	 * Método que devuelve un modelo de la caja a partir del la retentionBox que contenga el UY_TT_Rentention_ID 
	 * y un UY_TT_Box_ID con UY_DeliveryPoint_ID pasado por parametro
	 * 
	 */
	public static MTTBox getBoxForDeliveryPointIDAndRetentionID(Properties ctx, String trxName, int ttDeliveryPointID, int RetentionID){
		
		String whereClause = X_UY_TT_RetentionBox.COLUMNNAME_UY_TT_Retention_ID + " = " + RetentionID;
		
		List<MTTRetentionBox> retentionBoxes = new Query(ctx, I_UY_TT_RetentionBox.Table_Name, whereClause, trxName)
		.list();
		
		for (MTTRetentionBox mttRetentionBox : retentionBoxes) {
			
			//Caja de esta linea
			MTTBox box = (MTTBox) mttRetentionBox.getUY_TT_Box();
			if(box.getUY_DeliveryPoint_ID_To() == ttDeliveryPointID){
				return box;
			}			
		}
		
		return null;
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 03/10/2013. ISSUE #
	 * Método que devuelve un modelo de la caja a partir del la retentionBox que contenga el UY_TT_Rentention_ID 
	 * y un UY_TT_Box_ID con UY_DeliveryPoint_ID pasado por parametro y para la accion que esta parametrizado
	 * 
	 */
	public static MTTBox getBoxForRetentionIDAndAction(Properties ctx, String trxName, int RetentionID, String action){
		
		String whereClause = X_UY_TT_RetentionBox.COLUMNNAME_UY_TT_Retention_ID + " = " + RetentionID;
		
		List<MTTRetentionBox> retentionBoxes = new Query(ctx, I_UY_TT_RetentionBox.Table_Name, whereClause, trxName)
		.list();
		
		for (MTTRetentionBox mttRetentionBox : retentionBoxes) {
			
			//Caja de esta linea
			MTTBox box = (MTTBox) mttRetentionBox.getUY_TT_Box();
			if(box.isRetained() && box.getRetainedStatus().equals(action)){
				return box;
			}			
		}
		
		return null;
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 03/10/2013. ISSUE #
	 * Método que devuelve una lista de retentionBoxes
	 * 
	 */
	public static List<MTTRetentionBox> getRetentionBoxesForRetentionID(Properties ctx, int RetentionID){
		
		String whereClause = X_UY_TT_RetentionBox.COLUMNNAME_UY_TT_Retention_ID + " = " + RetentionID;
		
		List<MTTRetentionBox> retentionBoxes = new Query(ctx, I_UY_TT_RetentionBox.Table_Name, whereClause, null)
		.list();
					
		return retentionBoxes;
	}

}
