/**
 * 
 */
package org.openup.beans;

/**
 * @author root
 *
 */
public class MigrationTrlLine {

	private int id = 0;
	private String language = "";
	private String trlText = "";
	private String description = "";
	private String help = "";
	private String traslated = "";
	
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
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param trlText the trlText to set
	 */
	public void setTrlText(String trlText) {
		this.trlText = trlText;
	}
	/**
	 * @return the trlText
	 */
	public String getTrlText() {
		return trlText;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param help the help to set
	 */
	public void setHelp(String help) {
		this.help = help;
	}
	/**
	 * @return the help
	 */
	public String getHelp() {
		return help;
	}
	/**
	 * @param traslated the traslated to set
	 */
	public void setTraslated(String traslated) {
		this.traslated = traslated;
	}
	/**
	 * @return the traslated
	 */
	public String getTraslated() {
		return traslated;
	}
 
}
