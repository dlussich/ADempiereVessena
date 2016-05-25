/**
 * 
 */
package org.openup.beans;

import java.sql.Timestamp;

/**
 * @author OpenUp. Gabriel Vila. 28/11/2011. Issue #902.
 * Clase para intercambio de informacion de filtros seleccionados por el usuario
 * en el reporte de Estado de Cuenta Bancario.
 */
public class ReportBankAccountStatus {

	public Timestamp dateFrom;
	public Timestamp dateTo;
	public int cBankAccountID = 0;
	public int cdocTypeID = 0;
	public int adUserID = 0;
	public int adClientID = 0;
	public int adOrgID = 0;
	public boolean beforeBalance = false;

}
