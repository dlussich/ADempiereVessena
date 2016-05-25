/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Aug 8, 2015
*/
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MFduConnectionData;
import org.openup.model.MTTCard;

/**
 * org.openup.process - RTTAnexoB
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Aug 8, 2015
*/
public class RTTAnexoB extends SvrProcess {

	private MTTCard card = null;

	private static final String TABLA_MOLDE = "UY_Molde_AnexoB";
	
	/***
	 * Constructor.
	*/
	public RTTAnexoB() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		try {
			
			int idCard = 0;
			
			// Obtengo parametros y los recorro
			ProcessInfoParameter[] para = getParameter();
			for (int i = 0; i < para.length; i++)
			{
				String name = para[i].getParameterName().trim();
				if (name!= null){
					if (name.equalsIgnoreCase("UY_TT_Card_ID")){
						idCard = ((BigDecimal)para[i].getParameter()).intValueExact(); 
					}			
				}
			}
			
			// Obtengo modelo de cuenta tracking
			if (idCard > 0){
				card = new MTTCard(getCtx(), idCard, null);
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		try {
			
			this.deleteOldData();
			
			this.getData();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return "OK";
		
	}

	/***
	 * Elimina registros anteriores para este usuario en la tabla molde. 
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 3, 2015
	 */
	private void deleteOldData() {
		
		String action = "";
		
		try {
			action = " delete from " + TABLA_MOLDE + " where ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action, null);
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/**
	 * Obtiene información y carga la misma en la tabla temporal. 
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 3, 2015
	 */
	private void getData() {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
		
			StringBuilder insert = new StringBuilder();
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			
			con = this.getFDUConnection(fduData);
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);	

			sql = " select TOP 1 * " +
				  " from q_tarjplas_adempiere_new " +
				  " where TPlCuenta = " + this.card.getAccountNo();
				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				insert.append(" insert into " + TABLA_MOLDE + " (ad_user_id, uy_tt_card_id) ");
				insert.append(" values (" + this.getAD_User_ID() + "," + this.card.get_ID() +  ")");
			}

			rs.close();
			con.close();			

			// Ejecuto insert en table molde
			DB.executeUpdateEx(insert.toString(), null);
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally {
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}
	}

	
	/***
	 * Realiza y retorna una nueva conexion a base de datos origen de la información.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 3, 2015
	 * @param fduData
	 * @return
	 * @throws Exception
	 */
	private Connection getFDUConnection(MFduConnectionData fduData){
		
		Connection retorno = null;

		String connectString = ""; 

		try {
			
			if (fduData != null){
				
				connectString = "jdbc:sqlserver://" + fduData.getserver_ip() + "\\" + fduData.getServer() + 
								";databaseName=" + fduData.getdatabase_name() + ";user=" + fduData.getuser_db() + 
								";password=" + fduData.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, fduData.getuser_db(), fduData.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return retorno;
	}
	
}
