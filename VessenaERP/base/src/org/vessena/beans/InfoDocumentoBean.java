/**
 * InfoDocumentoBean.java
 * 03/10/2010
 */
package org.openup.beans;

import java.sql.Timestamp;

/**
 * OpenUp.
 * InfoDocumentoBean
 * Descripcion : Informacion de un documento.
 * @author Gabriel Vila
 * Fecha : 03/10/2010
 */
public class InfoDocumentoBean {

	public int C_DocType_ID = 0;
	public String DocBaseType = "";
	public String PrintName = "";
	public String DocumentNo = "";
	public String Observaciones="";
	public Timestamp DateTrx;
	public int C_BPartner_ID = 0;
	
}
