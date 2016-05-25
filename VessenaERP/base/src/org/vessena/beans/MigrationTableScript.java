/**
 * 
 */
package org.openup.beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MTable;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author root
 *
 */
public class MigrationTableScript {

	private int tableId = 0;
	private String tableScript = "";
	private ArrayList<String> foreignConstraints = new ArrayList<String>();

	
	/**
	 * Default Constructor.
	 */
	public MigrationTableScript(){
	}

	/**
	 * Constructor. Load translations.
	 * @param model
	 * @throws Exception 
	 */
	public MigrationTableScript(MTable table) throws Exception{
		if (table == null) return;
		this.tableId = table.getAD_Table_ID();
		this.tableScript = MigrationTableScript.getSQLCreate(this.tableId);
		try{
			this.loadForeignConstraints();	
		}
		catch (Exception e){
		}
	}
	
	
	/**
	 * @param tableId the tableId to set
	 */
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	/**
	 * @return the tableId
	 */
	public int getTableId() {
		return tableId;
	}
	/**
	 * @param tableScript the tableScript to set
	 */
	public void setTableScript(String tableScript) {
		this.tableScript = tableScript;
	}
	/**
	 * @return the tableScript
	 */
	public String getTableScript() {
		return tableScript;
	}
	/**
	 * @param foreignConstraints the foreignConstraints to set
	 */
	public void setForeignConstraints(ArrayList<String> foreignConstraints) {
		this.foreignConstraints = foreignConstraints;
	}
	/**
	 * @return the foreignConstraints
	 */
	public ArrayList<String> getForeignConstraints() {
		return foreignConstraints;
	}

	/**
	 * Get Foreign Constraints Script for a received table.
	 * @param table
	 * @throws Exception
	 */
	public void loadForeignConstraints() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MTable table = new MTable(Env.getCtx(), this.tableId, null);
		if (table.get_ID() <= 0) return;
		
		try{
			sql = " SELECT tc.constraint_name, tc.constraint_type, tc.is_deferrable, tc.initially_deferred " +
				  " FROM information_schema.table_constraints tc " +
				  " WHERE tc.table_name = ? " +
				  " AND constraint_type = 'FOREIGN KEY'";

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, table.getTableName().toLowerCase());
			rs = pstmt.executeQuery ();
			while (rs.next()){
				
				// Obtengo datos anexos a la constraint
				KeyColumnUsage kcol = this.getKeyColumnUsage(rs.getString("constraint_name"));
				ConstraintColumnUsage ccol = this.getConstraintColumnUsage(rs.getString("constraint_name"));
				ReferentialConstraint rcon = this.getReferentialConstraint(rs.getString("constraint_name"));
				
				String script = " ALTER TABLE " + table.getTableName() +
				" ADD CONSTRAINT " + rs.getString("constraint_name").trim() +
				" FOREIGN KEY (" + kcol.ColumnName + ") " +
				" REFERENCES " + ccol.TableName + 
				" (" + ccol.ColumnName + ") " +
				" MATCH SIMPLE ";
				
				if (!rcon.OnUpdate.contains("ACTION")) script += " ON UPDATE " + rcon.OnUpdate; 
				if (!rcon.OnDelete.contains("ACTION")) script += " ON DELETE " + rcon.OnDelete;					
				
				if (rs.getString("is_deferrable").equalsIgnoreCase("YES")){
					script += " DEFERRABLE INITIALLY DEFERRED "; 
				}
				
				this.foreignConstraints.add(script);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	/**
	 * OpenUp. Get key column usage info for a given constraint name.
	 * @param constraintName
	 * @return
	 * @throws Exception
	 */
	private KeyColumnUsage getKeyColumnUsage(String constraintName) throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		KeyColumnUsage value = null;
		
		try{
			sql = " SELECT column_name " +
				  " FROM information_schema.key_column_usage " +
				  " WHERE lower(constraint_name) = ?";

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, constraintName.toLowerCase());
			rs = pstmt.executeQuery ();
			if (rs.next()){
				value = new KeyColumnUsage();
				value.ColumnName = rs.getString(1);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}

	/**
	 * OpenUp. Get constraint column usage info for a given constraint name.
	 * @param constraintName
	 * @return
	 * @throws Exception
	 */
	private ConstraintColumnUsage getConstraintColumnUsage(String constraintName) throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		ConstraintColumnUsage value = null;
		
		try{
			sql = " SELECT table_name AS references_table, column_name AS references_field " +
				  " FROM information_schema.constraint_column_usage " +
				  " WHERE constraint_name = ?";

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, constraintName.toLowerCase());
			rs = pstmt.executeQuery ();
			if (rs.next()){
				value = new ConstraintColumnUsage();
				value.TableName = rs.getString(1);
				value.ColumnName = rs.getString(2);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}

	
	/**
	 * OpenUp. Get referential constraint info for a given constraint name.
	 * @param constraintName
	 * @return
	 * @throws Exception
	 */
	private ReferentialConstraint getReferentialConstraint(String constraintName) throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		ReferentialConstraint value = null;
		
		try{
			sql = " SELECT match_option, update_rule AS on_update, delete_rule AS on_delete " +
				  " FROM information_schema.referential_constraints " +
				  " WHERE constraint_name = ?";

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, constraintName.toLowerCase());
			rs = pstmt.executeQuery ();
			if (rs.next()){
				value = new ReferentialConstraint();
				value.OnUpdate = rs.getString(2);
				value.OnDelete = rs.getString(3);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}

	
	/**
	 * Delete table foreign constraints.
	 * @param trxName
	 * @throws Exception
	 */
	public void deleteForeignKeys(String trxName) throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String script = "";
		
		MTable table = new MTable(Env.getCtx(), this.tableId, trxName);
		if (table.get_ID() <= 0) return;
		
		try{
			sql = " SELECT c.conname AS constraint_name " +
				  "	FROM pg_constraint c " +
				  "	LEFT JOIN pg_class t  ON c.conrelid  = t.oid " +
				  " WHERE t.relname = ?" +
				  "	and c.contype = 'f'";

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, table.getTableName().toLowerCase());
			rs = pstmt.executeQuery ();
			while (rs.next()){
				
				script = " ALTER TABLE " + table.getTableName() +
				" DROP CONSTRAINT " + rs.getString("constraint_name").trim();
				
				System.out.println(script);
				
				DB.executeUpdate(script, trxName);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}
	
	/**
	 * OpenUp. Get table creation sql script.
	 * @param adTableID
	 * @return
	 * @throws Exception
	 */
	public static String getSQLCreate(int adTableID) throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		String value = "";
		
		try{

			MTable table = new MTable(Env.getCtx(), adTableID, null);
			if (table.get_ID() <= 0) return value;

			String script = " CREATE TABLE " + table.getTableName() + " ( ";
			boolean hayInfo = false;
			
			sql = " SELECT ordinal_position, column_name, data_type, column_default, " +
				  " is_nullable, character_maximum_length, numeric_precision " +
				  " FROM information_schema.columns " +
				  " WHERE table_name = ? " +
				  " ORDER BY ordinal_position";

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, table.getTableName().toLowerCase());
			rs = pstmt.executeQuery ();
			while (rs.next()){
				hayInfo = true;
				
				String dataType = rs.getString("data_type").trim();
				if (dataType.equalsIgnoreCase("numeric")){
					if (rs.getInt("numeric_precision") > 0)
						dataType += "(" + String.valueOf(rs.getInt("numeric_precision")).trim() + ",0) ";
				}
				if (rs.getInt("character_maximum_length") > 0){
					dataType += "(" + String.valueOf(rs.getInt("character_maximum_length")).trim() + ") ";
				}
				
				script += rs.getString("column_name").trim() + " " + dataType;

				if (rs.getString("is_nullable").trim().equalsIgnoreCase("NO"))
					script += " NOT NULL";
				
				if (rs.getString("column_default") != null)
					script += " DEFAULT " + rs.getString("column_default").trim();
			
				script += ",";
			}
			
			if (hayInfo) {
				// Add primary key
				script += " CONSTRAINT " + table.getTableName().toLowerCase() + "_key" + 
						  " PRIMARY KEY (" + table.getTableName().toLowerCase() + "_id))"; 
				value = script;
			}
			
			return value ;
		} 
		catch(Exception e){
			throw e;	
		}
	}
	
	/**
	 * OpenUp. Clases privadas para manejo de informacion de constraints.
	 */

	private class ConstraintColumnUsage{
		public String TableName = "";
		public String ColumnName = "";
	}
	
	private class KeyColumnUsage{
		public String ColumnName = "";
	}
	
	private class ReferentialConstraint{
		public String OnUpdate = "";
		public String OnDelete = "";
	}
}
