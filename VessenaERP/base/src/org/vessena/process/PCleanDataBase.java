/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/07/2012
 */
 
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * org.openup.process - PCleanDataBase
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 19/07/2012
 * @see
 */
public class PCleanDataBase extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PCleanDataBase() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 19/07/2012
	 * @see
	 */
	@Override
	protected void prepare() {

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 19/07/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		// Elimino registros de toda tabla funcional (no son tablas del diccionario de datos)
		this.deleteFunctionalTableRows();
		
		
		
		return "Ok";
	}

	private void deleteFunctionalTableRows() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			// Obtengo nombres de las tablas funcionales a procesar
			sql = " select table_name " +
				  " from information_schema.tables " +
				  " where table_schema not in ('information_schema','pg_catalog') " +
				  " and table_schema='adempiere' " +
				  " and table_type = 'BASE TABLE' " +
				  " and lower(table_name) not like 'ad%' " +
				  " order by table_name ";

			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

}
