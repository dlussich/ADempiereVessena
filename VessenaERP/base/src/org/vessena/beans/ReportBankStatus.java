/**
 * 
 */
package org.openup.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author OpenUp. Santiago Evans. 03/02/2016. Issue #5176.
 * Clase para intercambio de informacion de filtros seleccionados por el usuario
 * en el reporte de Estado de Cuenta Bancario.
 */
public class ReportBankStatus {

	public Timestamp date;	
	public int cBankAccountID = 0;	
	public int adUserID = 0;
	public int cdocTypeID = 0;	
	public int adClientID = 0;
	public int adOrgID = 0;
	public boolean beforeBalance = false;

}