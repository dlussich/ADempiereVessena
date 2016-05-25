package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

public class MCFEProviderConfig extends X_UY_CFE_ProviderConfig implements I_UY_CFE_ProviderConfig {

	private static final long serialVersionUID = -3771552135392352291L;

	public MCFEProviderConfig(Properties ctx, int UY_CFE_ProviderConfig_ID, String trxName) {
		super(ctx, UY_CFE_ProviderConfig_ID, trxName);

	}

	public static MCFEProviderConfig getProviderConfig(Properties ctx, String trxName) {
		
		String sql = "SELECT UY_CFE_ProviderConfig_ID FROM UY_CFE_ProviderConfig";
		MCFEProviderConfig mcfeProviderConfig = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			
			if (rs.next()){
				mcfeProviderConfig = new MCFEProviderConfig(ctx, rs.getInt("UY_CFE_ProviderConfig_ID"), trxName);
			}
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
		}
		
		return mcfeProviderConfig;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		// TODO: Hacer que se guarde solo un registro de este documento
		
		return super.beforeSave(newRecord);
	}
	
}
