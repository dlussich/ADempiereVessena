/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 11/11/2013
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * org.openup.model - MDBConfig
 * OpenUp Ltda. Issue #1539
 * Description: Modelo de configuracion inicial de DB
 * @author Gabriel Vila - 11/11/2013
 * @see
 */
public class MDBConfig extends X_UY_DB_Config {

	private static final long serialVersionUID = -3223066982286842715L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_DB_Config_ID
	 * @param trxName
	 */
	public MDBConfig(Properties ctx, int UY_DB_Config_ID, String trxName) {
		super(ctx, UY_DB_Config_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDBConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Carga de informacion de tablas definidas en base de datos actual.
	 * OpenUp Ltda. Issue #1539 
	 * @author Gabriel Vila - 11/11/2013
	 * @see
	 */
	public void loadDBTables(){

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			
			// Elimino informacion actual de tablas del sistema
			String action = " delete from uy_db_configtable ";
			DB.executeUpdateEx(action, get_TrxName());
			
			String sql = " select c.relname " +
						 " from pg_class c " +
						 " inner join pg_namespace ns on c.relnamespace = ns.oid " +
						 " where c.relkind ='r' " +
						 " and ns.nspname ='adempiere' " +
						 " order by  c.relname "; 

			pstmt = DB.prepareStatement(sql.toString(), null);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				MDBConfigTable tablaDB = new MDBConfigTable(getCtx(), 0, get_TrxName());
				tablaDB.setUY_DB_Config_ID(this.get_ID());
				tablaDB.setTableName(rs.getString("relname").trim());
				
				// Busco tabla en diccionario
				MTable tablaDD = MTable.get(getCtx(), tablaDB.getTableName());
				if ((tablaDD != null) && (tablaDD.get_ID() > 0)){
					tablaDB.setInDictionary(true);
					tablaDB.setAD_Table_ID(tablaDD.get_ID());
				}
				else{
					tablaDB.setInDictionary(false);
				}
				
				tablaDB.saveEx();
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
	
	/***
	 * Obtiene y retorna lista de tablas seleccionadas.
	 * OpenUp Ltda. Issue #1539 
	 * @author Gabriel Vila - 11/11/2013
	 * @see
	 * @return
	 */
	private List<MDBConfigTable> getSelectedTables(){
		
		String whereClause = X_UY_DB_ConfigTable.COLUMNNAME_UY_DB_Config_ID + "=" + this.get_ID() +
				" and " + X_UY_DB_ConfigTable.COLUMNNAME_IsSelected + "='Y'";
		
		List<MDBConfigTable> lines = new Query(getCtx(), I_UY_DB_ConfigTable.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}
	
	/***
	 * Elimina de la base y del sistema aquellas tablas seleccionadas
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 11/11/2013
	 * @see
	 */
	public void dropSelectedTables(){
		
		try {
			
			List<MDBConfigTable> lines = this.getSelectedTables();
			for (MDBConfigTable line: lines){
				if (line.isInDictionary()){
					MTable tableDD = (MTable)line.getAD_Table();
					tableDD.deleteEx(true);
				}
				DB.executeUpdateEx(" drop table " + line.getTableName(), get_TrxName());
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Elimina información de transacciones de todos los modulos en la base de datos.
	 * OpenUp Ltda. Issue #1539 
	 * @author Gabriel Vila - 11/11/2013
	 * @see
	 */
	public void deleteData(){
		
		try {
			
			deleteCuentaCorriente();
			deleteComercial();
			deleteCompras();
			deleteNomina();
			deleteStock();
			deleteProduccion();
			deleteLogistica();
			deleteBancos();
			deleteCaja();
			deleteContabilidad();
			deleteIncidencias();
			deleteTracking();
			deleteOthers();
			deleteSystem() ;
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
		
	private void deleteNomina(){
		
	}
	
	private void deleteContabilidad(){
		
	}
	
	private void deleteStock(){
		
		String action = "";
		
		try {

			action = " delete from c_invoicetax cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from uy_invoicecashpayment cascade ";
			DB.executeUpdateEx(action, get_TrxName());
			
			action = " delete from c_invoice cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from c_invoiceline cascade ";
			DB.executeUpdateEx(action, get_TrxName());
			
			action = " delete from c_ordertax cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from c_order cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from c_orderline cascade ";
			DB.executeUpdateEx(action, get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
	
	/***
	 * Eliminacion de datos generados por modulo comercial.
	 * OpenUp Ltda. Issue #1539 
	 * @author Gabriel Vila - 13/11/2013
	 * @see
	 */
	private void deleteComercial(){
		
		String action = "";
		
		try {

			action = " delete from c_invoicetax cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from uy_invoicecashpayment cascade ";
			DB.executeUpdateEx(action, get_TrxName());
			
			action = " delete from c_invoice cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from c_invoiceline cascade ";
			DB.executeUpdateEx(action, get_TrxName());
			
			action = " delete from c_ordertax cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from c_order cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from c_orderline cascade ";
			DB.executeUpdateEx(action, get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
	private void deleteCompras(){
		
	}
	
	/***
	 * Eliminacion de datos generados por modulo de cuenta corriente.
	 * OpenUp Ltda. Issue #1539 
	 * @author Gabriel Vila - 11/11/2013
	 * @see
	 */
	private void deleteCuentaCorriente(){
		
		String action = "";
		
		try {

			action = " delete from uy_allocdetailpayment cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from uy_allocdirectpayment cascade ";
			DB.executeUpdateEx(action, get_TrxName());
			
			action = " delete from uy_allocationdetail cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from uy_allocation cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from uy_allocationinvoice cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from uy_allocationpayment cascade ";
			DB.executeUpdateEx(action, get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
	private void deleteCaja(){
		
	}

	private void deleteBancos(){
		
	}
	
	private void deleteProduccion(){
		
	}
	
	private void deleteLogistica(){
		
	}
	
	private void deleteIncidencias(){
		
	}

	private void deleteTracking(){
		
	}
	
	private void deleteSystem(){

		String action = "";
		
		try {

			action = "";
			DB.executeUpdateEx(action, get_TrxName());

			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Elimina informacion de tablas de otros modulos
	 * OpenUp Ltda. Issue #1539 
	 * @author Gabriel Vila - 15/11/2013
	 * @see
	 */
	private void deleteOthers(){

		String action = "";
		
		try {

			action = " delete from uy_molde_fduload_cc108 cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from uy_fduload cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from uy_fduloadline cascade ";
			DB.executeUpdateEx(action, get_TrxName());

			
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	
}

