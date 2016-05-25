/**
 * MReserveOrders.java
 * 11/01/2011
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;
import org.compiere.model.MTable;
import org.compiere.util.DB;

/**
 * OpenUp.
 * MReserveOrders
 * Descripcion :
 * @author FL
 * Fecha : 29/03/2011
 */
public class MDiffTable extends X_AD_DiffTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4758633381879049846L;


	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InOutType
	 * @param trxName
	 */
	public MDiffTable(Properties ctx, int UY_InOutType_ID,String trxName) {
		super(ctx, UY_InOutType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDiffTable(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Factory method, delete all records of a child table
	 * @param trxName
	 * @throws Exception 
	 */
	public boolean DeleteDiff(String trxName) throws Exception {
		
		boolean deleted=false;

		String SQL="DELETE FROM ad_diff WHERE ad_difftable_id=?; ";
		PreparedStatement pstmt=null;
		
		try {
			pstmt=DB.prepareStatement(SQL, trxName);
			pstmt.setInt(1,this.getAD_DiffTable_ID());
			
			// Just run de query, 
			pstmt.executeUpdate();
			deleted=true;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			pstmt= null;
		}
		
		return(deleted);
		
	}

	/**
	 * 	Factory, get Table diff by table name
	 *	@param ctx context
	 *	@param tableName case insensitive table name
	 *	@return Table
	 * @throws Exception 
	 */
	public static MDiffTable getByTableName(Properties ctx, String tableName,String trxName) throws Exception {
		
		if (tableName==null) {
			return(null);
		}

		String SQL="SELECT * FROM ad_difftable WHERE UPPER(tableName)=?";
		ResultSet rs = null;
		PreparedStatement pstmt=null;

		MDiffTable diffTable=null;
		
		try {
			pstmt=DB.prepareStatement(SQL,trxName);
			pstmt.setString(1,tableName.toUpperCase());
			rs=pstmt.executeQuery();
			
			// Get the object 
			if (rs.next()) {
				diffTable=new MDiffTable(ctx,rs,trxName);
			}
			else { 
				diffTable=new MDiffTable(ctx,0,trxName);
				diffTable.setTableName(tableName);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(diffTable);			
	}
	
	/** Set DB Table Name and table id
	 * @param TableName 
	 * Name of the table in the database
	 */
	public void setTableName(String tableName) {
		
		// Set the table name
		super.setTableName(tableName);
		
		// Try to get the table model
		MTable table=MTable.get(getCtx(),tableName);
		
		// Set table ID, if the table model was get
		if (table!=null) {
			this.setAD_Table_ID(table.getAD_Table_ID());
		}
	}
	
	/** Set UpdateCount.
	 * UpdateCount
	 */
	public void setUpdateCount() throws Exception {
		
		if (this.hasColumn("updated")) {

			// Get all records from product having a value
			String sql = "SELECT count(*) FROM "+this.getTableName()+" WHERE updated>'2010-12-31'::date";
			ResultSet rs = null;
			PreparedStatement pstmt = null;
	
			// Get table names an compare it
			try{
				// Prepare and excecute
				pstmt = DB.prepareStatement (sql,null);
				rs = pstmt.executeQuery();
			
				// Continue only if the firest record can be readed 
				if (rs.next()) {
					this.setUpdateCount(rs.getInt(1)); 
				}
			}
			catch (Exception e)
			{
				log.info(e.getMessage());
				throw new Exception(e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
		}
	}
	
	/** Set CreateCount.
	 * UpdateCount
	 */
	public void setCreateCount() throws Exception {
		
		if (this.hasColumn("created")) {

			// Get all records from product having a value
			String sql = "SELECT count(*) FROM "+this.getTableName()+" WHERE created>'2010-12-31'::date";
			ResultSet rs = null;
			PreparedStatement pstmt = null;
	
			// Get table names an compare it
			try{
				// Prepare and excecute
				pstmt = DB.prepareStatement (sql,null);
				rs = pstmt.executeQuery();
			
				// Continue only if the firest record can be readed 
				if (rs.next()) {
					this.setCreateCount(rs.getInt(1)); 
				}
			}
			catch (Exception e)
			{
				log.info(e.getMessage());
				throw new Exception(e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
		}
	}
	
	/** Set rowcount.
	 * UpdateCount
	 */
	public void setrowcount() throws Exception {
		
		// Get all records from product having a value
		String sql = "SELECT count(*) FROM "+this.getTableName()+";";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Get table names an compare it
		try{
			// Prepare and excecute
			pstmt = DB.prepareStatement (sql,null);
			rs = pstmt.executeQuery();
		
			// Continue only if the firest record can be readed 
			if (rs.next()) {
				this.setrowcount(rs.getInt(1)); 
			}
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}
	

	
	/** has a especific column name 
	 * @param column name
	 * UpdateCount
	 */
	public boolean hasColumn(String columnName) throws Exception {

		// Get all records from product having a value
		String sql = "SELECT * FROM "+this.getTableName()+";";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean hasColumn=false;

		// Get table names an compare it
		try{
			// Prepare and excecute
			pstmt = DB.prepareStatement (sql,null);
			rs = pstmt.executeQuery();
		
			// Continue only if the firest record can be readed 
			if (rs.next()) {

				// Get the metadata
				ResultSetMetaData rsmd=rs.getMetaData();
				
				// Loop over resultset columns
				for (int column=1;column<=rsmd.getColumnCount();column++) {
					if (columnName.equalsIgnoreCase(rsmd.getColumnName(column))) {
						hasColumn=true;
					}
				}
			}
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(hasColumn);
	}
	
	


	
}
