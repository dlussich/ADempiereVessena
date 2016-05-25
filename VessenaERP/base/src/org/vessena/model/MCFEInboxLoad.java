/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Jan 8, 2016
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

/**
 * org.openup.model - MCFEInboxLoad
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Jan 8, 2016
*/
public class MCFEInboxLoad extends X_UY_CFE_InboxLoad {

	private static final long serialVersionUID = 1193684249974745831L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_CFE_InboxLoad_ID
	 * @param trxName
	*/

	public MCFEInboxLoad(Properties ctx, int UY_CFE_InboxLoad_ID, String trxName) {
		super(ctx, UY_CFE_InboxLoad_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MCFEInboxLoad(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		try {
			if (this.getCFELoadType().equalsIgnoreCase(CFELOADTYPE_VISTA)){
				if (this.getDateAction() == null){
					throw new AdempiereException("Debe indicar Fecha de Cierre");
				}
				if (this.getUY_CFE_InboxFileType_ID() <= 0){
					throw new AdempiereException("Debe indicar Tipo de Archivo");
				}
			}
			else if (this.getCFELoadType().equalsIgnoreCase(CFELOADTYPE_EXCEL)){
				if ((this.getFileName() == null) || (this.getFileName().equalsIgnoreCase(""))){
					throw new AdempiereException("Debe indicar Archivo Excel a Procesar");
				}
				if (this.getUY_CFE_InboxFileType_ID() <= 0){
					throw new AdempiereException("Debe indicar Tipo de Archivo");
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return true;
	}

	
}
