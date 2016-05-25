/**
 * 
 */
package org.openup.beans;

import java.sql.Timestamp;

/**
 * @author OpenUp. Gabriel Vila. 07/11/2011. Issue #902.
 * Clase para intercambio de informacion de filtros seleccionados por el usuario
 * en el reporte de Balance cuenta corriente.
 */
public class ReportBalanceCtaCte {

	public static final int GRUPO_CLIENTE = 1000001;
	public static final int GRUPO_PROVEEDOR = 1000002;
	public static final String PARTNER_CLIENTE = "CLIENTES";
	public static final String PARTNER_PROVEEDOR = "PROVEEDORES";
	
	
	public Timestamp dateTo;
	public int cCurrencyID = 0;
	public int cBPGroupID = 0;
	public int customerID = 0;
	public int vendorID = 0;
	public int adUserID = 0;
	public int adClientID = 0;
	public int adOrgID = 0;
	public String partnerType = null;
	
	
}
