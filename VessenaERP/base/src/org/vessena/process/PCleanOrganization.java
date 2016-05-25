/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/04/2013
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * org.openup.process - PCleanOrganization
 * OpenUp Ltda. Issue #703 
 * Description: Pasa datos de una organizacion a otra.
 * @author Gabriel Vila - 15/04/2013
 * @see
 */
public class PCleanOrganization extends SvrProcess {

	int adOrgIDFrom = 1000008;
	int adOrgIDTo = 0;
	
	/**
	 * Constructor.
	 */
	public PCleanOrganization() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 15/04/2013
	 * @see
	 */
	@Override
	protected void prepare() {

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 15/04/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			sql = " SELECT c.relname " +
				  " FROM pg_class c, pg_attribute a, pg_type t " +
				  " WHERE lower(a.attname)='ad_org_id' " +
				  " AND a.attnum > 0 " +
				  " AND a.attrelid = c.oid " +
				  " AND a.atttypid = t.oid " +
				  " AND c.relkind ='r' " +
				  " order by  c.relname "; 
			
			pstmt = DB.prepareStatement(sql.toString(), null);

			rs = pstmt.executeQuery();

			String action ="";
			
			while (rs.next()) {
				
				String tabla = rs.getString(1);
				if (!tabla.equalsIgnoreCase("ad_org") && !tabla.equalsIgnoreCase("ad_orginfo")
						&& !tabla.equalsIgnoreCase("ad_role_orgaccess")){

					System.out.println(tabla);
					
					action = " update " + tabla +
							 " set ad_org_id =" + adOrgIDTo +
							 " where ad_org_id =" + adOrgIDFrom;
					DB.executeUpdateEx(action, null);
				}
				
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return "OK";
	}

}
