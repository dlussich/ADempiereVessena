package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

public class MParametrosStdProdLine extends X_UY_ParametrosStdProdLine {

	private static final long serialVersionUID = 1L;

	public MParametrosStdProdLine(Properties ctx,
			int UY_ParametrosStdProdLine_ID, String trxName) {
		super(ctx, UY_ParametrosStdProdLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MParametrosStdProdLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		String sql="";
		//se controla que no se repitan registros con igual producto
		sql = "SELECT count(m_product_id) FROM UY_ParametrosStdProdLine" + " WHERE m_product_id=" + this.getM_Product_ID() + " AND ad_client_id="
				+ this.getAD_Client_ID() + " AND uy_parametrosstdprodhdr_id=" + this.getUY_ParametrosStdProdHdr_ID();

		int res = DB.getSQLValue(get_TrxName(), sql);

		if (res > 0) throw new AdempiereException("Ya existe un registro para este producto");

		return true;
	}
	
	
	
	

}
