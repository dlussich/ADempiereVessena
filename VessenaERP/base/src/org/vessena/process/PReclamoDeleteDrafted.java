/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 01/07/2013
 */
package org.openup.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MFduConnectionData;
import org.openup.model.MRAjuste;

/**
 * org.openup.process - PReclamoDeleteDrafted
 * OpenUp Ltda. Issue #1101
 * Description: Elimina reclamos en estado borrador con mas de 72 horas de creacion.
 * @author Gabriel Vila - 01/07/2013
 * @see
 */
public class PReclamoDeleteDrafted extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PReclamoDeleteDrafted() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 01/07/2013
	 * @see
	 */
	@Override
	protected void prepare() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 01/07/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		try{
			
			String action = " delete from uy_r_reclamo " +
					        " where docstatus='DR' " +
					        " and datetrx + 3 <= now() "; 
					
			DB.executeUpdateEx(action, get_TrxName());
			
			// Por ahora actulizo ajustes en adempiere traidos desde financial en este proceso
			// que se utiliza en un scheduler diario
			this.updateAjustes();
			
		}
		catch (Exception ex){
			throw new AdempiereException(ex);
		}
		
		return "OK";
	}

	/***
	 * Actualiza tipos de ajuste tomando como origen los definidos en la base 
	 * de Financial.
	 * OpenUp Ltda. Issue #657 
	 * @author Gabriel Vila - 09/04/2013
	 * @see
	 */
	private void updateAjustes(){
		
		Connection con = null;
		ResultSet rs = null;

		try{
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			con = getFDUConnection(fduData);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select DAjuCod, DAjuDesc, isnull(CajGravIVA,'SI') as aplicaiva, DAjuDebCre " +
					     " from q_defajust ";
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()){
				
				String finCode = rs.getString("DAjuCod").trim();
				if (finCode.length() == 1){
					finCode = "0" + finCode;
				}
				
				MRAjuste ajuste = MRAjuste.forFinancialCode(getCtx(), finCode, null);
				if (ajuste == null){
					ajuste = new MRAjuste(getCtx(), 0, get_TrxName());
					ajuste.setValue(finCode);
					ajuste.setfinancialcode(finCode);
					ajuste.setName(rs.getString("DAjuDesc"));
					ajuste.setDescription("Creado automaticamente desde Financial");
					
				}

				String drcr = null;
				if (rs.getInt("DAjuDebCre") == 1){
					drcr = "DB";
				}
				if (rs.getInt("DAjuDebCre") == 2){
					drcr = "CR";
				}
				if (drcr != null){
					ajuste.setDrCr(drcr);
				}
				
				boolean aplicaIVA = true;
				if (rs.getString("aplicaiva").trim().equalsIgnoreCase("NO")){
					aplicaIVA = false;
				}
				
				ajuste.setAplicaIVA(aplicaIVA);

				ajuste.setName(rs.getString("DAjuDesc"));
				ajuste.saveEx();
			}
			
			rs.close();
			con.close();		
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
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

	private Connection getFDUConnection(MFduConnectionData fduData) throws Exception {
		
		Connection retorno = null;

		String connectString = ""; 

		try {
			if(fduData != null){
				
				connectString = "jdbc:sqlserver://" + fduData.getserver_ip() + "\\" + fduData.getServer() + 
								";databaseName=" + fduData.getdatabase_name() + ";user=" + fduData.getuser_db() + 
								";password=" + fduData.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, fduData.getuser_db(), fduData.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}	

}
