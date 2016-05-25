package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import org.compiere.model.Query;
import org.compiere.util.DB;


// OpenUp. Nicolas Garcia. 30/08/2011. Issue #759.
//Clase traida para este issue
public class MDepartamentos extends X_UY_Departamentos {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3140234815533913275L;

	public MDepartamentos(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MDepartamentos(Properties ctx, int UY_Departamentos_ID, String trxName) {
		super(ctx, UY_Departamentos_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public static MDepartamentos[] getDepartamentos(Properties ctx, int countryID) {

		ArrayList<MDepartamentos> list = new ArrayList<MDepartamentos>();

		String sql = "SELECT uy_departamentos_id FROM uy_departamentos WHERE c_country_id=" + countryID + " ORDER BY name;";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new MDepartamentos(ctx, rs.getInt("uy_departamentos_id"), null));
			}

		} catch (Exception e) {
			return new MDepartamentos[new ArrayList<MDepartamentos>().size()];
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		MDepartamentos[] retValue = new MDepartamentos[list.size()];
		list.toArray(retValue);

		return retValue;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}

	/***
	 * Obtiene y retorna modelo segun nombre recibido. 
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Jan 10, 2016
	 * @param ctx
	 * @param name
	 * @param cCountryID
	 * @param trxName
	 * @return
	 */
	public static MDepartamentos forNameAndCountry(Properties ctx, String name, int cCountryID, String trxName) {

		String whereClause = " lower(name)='" + name.toLowerCase() + "' " +
							" and " + X_UY_Departamentos.COLUMNNAME_C_Country_ID + "=" + cCountryID;
		
		MDepartamentos model = new Query(ctx, I_UY_Departamentos.Table_Name, whereClause, trxName).first();
		
		return model;
	}
}
