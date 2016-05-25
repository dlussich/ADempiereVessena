/**
 * 
 */
package org.openup.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.util.Env;

/**
 * @author OpenUp. Gabriel Vila. 07/11/2011. Issue #902.
 * Clase para intercambio de informacion de filtros seleccionados por el usuario
 * en el reporte de Estado de Cuenta.
 */
public class ReportAccountStatus {

	public static final int GRUPO_CLIENTE = 1000001;
	public static final int GRUPO_PROVEEDOR = 1000002;
	public static final String PARTNER_CLIENTE = "CLIENTES";
	public static final String PARTNER_PROVEEDOR = "PROVEEDORES";
	
	public static final String REPORTCURRTYPE_UNA = "UNA"; //Se establecen las posibles 
	public static final String REPORTCURRTYPE_TODAS = "TODAS";
	public static final String REPORTCURRTYPE_TODASTRX = "TODASTRX";//SBT 30/03 Nuevo tipo de reporte
	
	public Timestamp dateFrom;
	public Timestamp dateTo;
	public int cCurrencyID = 0;
	public int cBPGroupID = 0;
	public int customerID = 0;
	public int vendorID = 0;
	public int adUserID = 0;
	public int adClientID = 0;
	public int adOrgID = 0;	
	public String partnerType = null;
	
	//Santiago Evans 08-03-2016 issue #5069
	public String reportType = null;
	public String acctStatusType = null;
	public String currencyType = null;
	//SBT 09/03/2016
	public BigDecimal currencyRate = Env.ONE;
	public Timestamp today;
	public int cBPartnerID = 0;
	public String isSoTrx = "";

}
