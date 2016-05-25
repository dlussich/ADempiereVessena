/**
 * 
 */
package org.openup.beans;

import java.sql.Timestamp;

/**
 * @author OpenUp. Gabriel Vila. 03/11/2011. Issue #902.
 * Clase para intercambio de informacion de filtros seleccionados por el usuario
 * en el reporte de Saldos Pendiente.
 */
public class ReportOpenAmt {

	public static final String TIPO_MONEDA_SMN = "SMN";
	public static final String TIPO_MONEDA_SME = "SME";
	public static final String TIPO_MONEDA_TMN = "TMN";
	public static final String TIPO_MONEDA_TME = "TME";
	public static final String CTA_ABIERTA = "CA";
	public static final String CTA_DOCUMENTADA = "CD";
	public static final String CTA_CORRIENTE_CLIENTE = "CL";
	public static final String CTA_CORRIENTE_PROVEEDOR = "PR";
	public static final int GRUPO_CLIENTE = 1000001;
	public static final int GRUPO_PROVEEDOR = 1000002;

	public static final String PARTNER_CLIENTE = "CLIENTES";
	public static final String PARTNER_PROVEEDOR = "PROVEEDORES";

	//OpenUp SBT 14-03-2016 Se establecen las posibles elecciones 
	public static final String REPORTCURRTYPE_UNA = "UNA"; 
	public static final String REPORTCURRTYPE_TODAS = "TODAS";
	//OpenUp SBT 29-03-2016 Se agrega opción Issue #5657
	public static final String REPORTCURRTYPE_TODASTRX = "TODASTRX";
	
	public Timestamp dateTo  = null;
	public String tipoMoneda = ""; //Opción Monetaria 
	public int cCurrencyID = 0;
	public String tipoReporte = "";
	public String tipoCtaCte = "";
	public int cBPGroupID = 0;
	public int customerID = 0;
	public int vendorID = 0;
	public int salesRepID = 0;
	public int collectorID = 0;
	public int canalVentaID = 0;
	public int departamentoID = 0;
	public int localidadID = 0;
	public int adUserID = 0;
	public int adClientID = 0;
	public int adOrgID = 0;	
	public String partnerType = null;
	public boolean isDueDate = false;
	
	//OpenUp SBT 11-03-2016 Issue #5069
	public String isSoTrx = "";
	public int cBPartnerID = 0;
	public String reportType = "";//-->Jasper o rv
}
