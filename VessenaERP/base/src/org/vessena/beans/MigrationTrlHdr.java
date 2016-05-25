/**
 * 
 */
package org.openup.beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.util.DB;

/**
 * @author root
 *
 */
public class MigrationTrlHdr {

	private int id = 0;
	private String trlTableName = "";
	private String trlColumnName = "";
	private boolean hasExtraFields = false;
	private ArrayList<MigrationTrlLine> lines = new ArrayList<MigrationTrlLine>();

	/**
	 * Default Constructor.
	 */
	public MigrationTrlHdr(){
	}

	/**
	 * Constructor. Load translations.
	 * @param model
	 */
	public MigrationTrlHdr(PO model){
		if (model == null) return;
		try{
			this.getTranslations(model);	
		}
		catch (Exception e){
		}
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param lines the lines to set
	 */
	public void setLines(ArrayList<MigrationTrlLine> lines) {
		this.lines = lines;
	}
	/**
	 * @return the lines
	 */
	public ArrayList<MigrationTrlLine> getLines() {
		return lines;
	}
	
	public void getTranslations(PO model) throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		this.id = model.get_ID();
		this.trlTableName = model.get_TableName() + "_Trl";
		this.trlColumnName = model.get_KeyColumns()[0].trim();
		
		try{
			sql =" SELECT * " + 
  		  	     " FROM " + this.trlTableName + 
		  	     " WHERE " + this.trlColumnName + " =?";

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, model.get_ID());
			rs = pstmt.executeQuery ();
			while (rs.next()){
				MigrationTrlLine line = new MigrationTrlLine();
				line.setId(this.id);
				line.setLanguage(rs.getString("ad_language").trim());
				line.setTrlText(rs.getString("name"));
				line.setTraslated(rs.getString("istranslated"));
				
				try{
					this.hasExtraFields = true;
				
					if (rs.getString("description") != null)
						line.setDescription(rs.getString("description"));
					else
						line.setDescription("");
					
					if (rs.getString("help") != null)
						line.setHelp(rs.getString("help"));
					else
						line.setHelp("");
				
				} catch (Exception e){
					// No hago nada. Hay tablas de TRL que no tienen los campos description y help
					this.hasExtraFields = false;
				}
				
				this.lines.add(line);
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
	 * OpenUp. Delete all DD object transalations.  
	 * @param model
	 * @param trxName
	 * @throws Exception
	 */
	public void deleteTransalations(String trxName) {
		try{
			String action = " DELETE FROM " + this.trlTableName +
		  	     			" WHERE " + this.trlColumnName + " =" + this.id;
			DB.executeUpdateEx(action, trxName);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
	
	/**
	 * OpenUp.  
	 * @param model
	 * @param trxName
	 * @throws Exception
	 */
	public void insertTranslations(PO model, String trxName){
		
		String insert = "";
		
		try{
			String extraFields = "";
			String extraValues = "";
			
			if (this.hasExtraFields) {
				extraFields = ",description,help";
				if (this.trlTableName.equalsIgnoreCase("ad_element_trl"))
					extraFields = ",description,help,printname";
			}
				
				
				
			insert = " INSERT INTO " + this.trlTableName +
			" (" + this.trlColumnName + ",ad_language,ad_client_id,ad_org_id," +
			"isactive,created,createdby,updated,updatedby,name" + extraFields + ",istranslated) ";
		 
			for (MigrationTrlLine line : this.lines){
			
				if (this.hasExtraFields) {
					extraValues = line.getDescription().replace("'", "") + "','" + line.getHelp().replace("'", "") + "','";
					if (this.trlTableName.equalsIgnoreCase("ad_element_trl")){
						extraValues = line.getDescription().replace("'", "") + "','" + line.getHelp().replace("'", "") + "','" + line.getTrlText().replace("'", "") + "','";
					}
						
				}
				else extraValues = "";
				
				String values = " VALUES (" + this.id + ",'" + line.getLanguage() + "',0,0,'Y'," +
				"'" + model.getCreated().toString() + "'," + model.getCreatedBy() + "," + 
				"'" + model.getUpdated().toString() + "'," + model.getUpdatedBy() + ",'" +
				line.getTrlText().replace("'", "") + "','" + extraValues +
				line.getTraslated() + "')";
				
				System.out.println("Translation : " + insert + values);
				
				DB.executeUpdate(insert + values, trxName);	
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException();
		}
	}

	/**
	 * @param trlTableName the trlTableName to set
	 */
	public void setTrlTableName(String trlTableName) {
		this.trlTableName = trlTableName;
	}

	/**
	 * @return the trlTableName
	 */
	public String getTrlTableName() {
		return trlTableName;
	}

	/**
	 * @param trlColumnName the trlColumnName to set
	 */
	public void setTrlColumnName(String trlColumnName) {
		this.trlColumnName = trlColumnName;
	}

	/**
	 * @return the trlColumnName
	 */
	public String getTrlColumnName() {
		return trlColumnName;
	}

	/**
	 * @param hasExtraFields the hasExtraFields to set
	 */
	public void setHasExtraFields(boolean hasExtraFields) {
		this.hasExtraFields = hasExtraFields;
	}

	/**
	 * @return the hasExtraFields
	 */
	public boolean isHasExtraFields() {
		return hasExtraFields;
	}


}
