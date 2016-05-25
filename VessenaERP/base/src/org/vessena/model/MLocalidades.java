package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.DB;

// OpenUp. Nicolas Garcia. 30/08/2011. Issue #759.
// Clase traida para este issue
public class MLocalidades extends X_UY_Localidades {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4601384010607656927L;

	public MLocalidades(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MLocalidades(Properties ctx, int UY_Localidades_ID, String trxName) {
		super(ctx, UY_Localidades_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public static MLocalidades[] getLocalidades(Properties ctx, int departamentosID) {

		ArrayList<MLocalidades> list = new ArrayList<MLocalidades>();

		String sql = "SELECT uy_localidades_id FROM uy_localidades WHERE uy_departamentos_id=" + departamentosID + " ORDER BY name;";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new MLocalidades(ctx, rs.getInt("uy_localidades_id"), null));
			}

		} catch (Exception e) {
			return new MLocalidades[new ArrayList<MLocalidades>().size()];
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		MLocalidades[] retValue = new MLocalidades[list.size()];
		list.toArray(retValue);

		return retValue;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}

	/***
	 * Obtiene y retorna modelo segun nombre y depto recibido.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Jan 10, 2016
	 * @param ctx
	 * @param ciudad
	 * @param get_ID
	 * @param object
	 * @return
	 */
	public static MLocalidades forNameAndDpto(Properties ctx, String name, int uyDeptoID, String trxName) {
		
		String whereClause = " lower(name) ='" + name.toLowerCase() + "' " +
				" and " + X_UY_Localidades.COLUMNNAME_UY_Departamentos_ID + "=" + uyDeptoID;

		MLocalidades model = new Query(ctx, I_UY_Localidades.Table_Name, whereClause, trxName).first();

		return model;
	}

}
