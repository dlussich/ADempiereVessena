package org.openup.cfe;

/**
 * OpenUp Ltda - #5560 - Raul Capecce - 01/03/2016
 * Clase encagrada de manejar los mensajes retornados al usuario
 * Utilizado desde todas los modelos que implementan la interface a DGI
 * @author raul
 */
public class CFEMessages {

	//  AREA: Identificacion del Comprobante
	
	/*   2 */ public final static String IDDOC_002 = "CFE Error: No es un tipo de documento para emitir CFE";
	/*   2 */ public final static String IDDOC_002_2 = "CFE Error: El tipo de CFE no es un tipo aceptado por DGI";
	/*   3 */ public final static String IDDOC_003 = "CFE Error: Area Identificacion del Comprobante (3) - Falta Prefijo de la secuencia";
	/*   4 */ public final static String IDDOC_004 = "CFE Error: Area Identificacion del Comprobante (4) - Numero de comprobante no asignado al documento";
	/*   5 */ public final static String IDDOC_005 = "CFE Error: Area Identificacion del Comprobante (5) - Fecha del comprobante no establecida";
	/*  11 */ public final static String IDDOC_011 = "CFE Error: Area Identificacion del Comprobante (11) - Forma de pago no aceptada por DGI (Contado o Credito)";

	
	// AREA: Emisor
	
	/*     */ public final static String EMISOR_ORG = "CFE Error: Area Emisor - Empresa no establecida";
	/*  40 */ public final static String EMISOR_040 = "CFE Error: Area Emisor (40) - RUT no establecido";
	/*  41 */ public final static String EMISOR_041 = "CFE Error: Area Emisor (41) - Razon Social no establecida";
	/*  47 */ public final static String EMISOR_047 = "CFE Error: Area Emisor (47) - Identificador de DGI para local no establecido";
	/*  48 */ public final static String EMISOR_048 = "CFE Error: Area Emisor (48) - Domicilio fiscal no establecido";
	/*  49 */ public final static String EMISOR_049 = "CFE Error: Area Emisor (49) - Ciudad para domicilio fiscal no establecida";
	/*  50 */ public final static String EMISOR_050 = "CFE Error: Area Emisor (50) - Departamento para domicilio fiscal no establecido";
	
	
	//  Area: Receptor
	
	/*     */ public final static String RECEPTOR_NODOC = "CFE Error: El receptor de la factura nº: {{documentNo}} no tiene ni cedula ni RUT";
	/*     */ public final static String RECEPTOR_FACTNORUT = "CFE Error: El receptor no tiene un RUT establecido y esta intentando emitir una E-Factura o sus notas de corrección";
	/*     */ public final static String RECEPTOR_REM_NOLOCATIONDEF = "CFE Error: El receptor debe tener solo una dirección a remitir";
	/*  61 */ public final static String RECEPTOR_61 = "CFE Error: Area Receptor (61) - Pais no definido para el Receptor";

	
	//  Area: Totales Encabezado
	
	/* 110 */ public final static String TOTALES_110 = "CFE Error: Area Totales Encabezado (110) - Tipo de moneda de la transaccion no establecida";
	/* 110 */ public final static String TOTALES_110_2 = "CFE Error: Area Totales Encabezado (110) - Tipo de moneda no aceptada por DGI"; 
	/* 126 */ public final static String TOTALES_126 = "CFE Error: Area Totales Encabezado (126) - Para un eTicket y sus correspondientes NC y ND, no se pueden exceder la cantidad de 700 lineas";
	/* 126 */ public final static String TOTALES_126_2 = "CFE Error: Area Totales Encabezado (126) - Para los CFE que no son eTickets ni sus correspondientes NC y ND, no se pueden exeder la cantidad de 200 lineas";
	
	
	//  Area: Detalle de Productos o Servicios
	
	/*   4 */ public final static String DETALLE_004 = "CFE Error: Area Detalle de Productos o Servicios - Tasa en linea de invoice nº {{documentNo}} no valida";
	
	
	//  Area: Información de referencia
	
	/*   3 */ public final static String INFOREF_003_ASOCETICKET = "CFE Error: Area Informacion de Referencia (3) - El doumento actual debe estar asociado a un eTicket";
	/*   3 */ public final static String INFOREF_003_ASOCEFACTURA = "CFE Error: Area Informacion de Referencia (3) - El doumento actual debe estar asociado a una eFactura";
	/*   3 */ public final static String INFOREF_003_PARSEERROR = "CFE Error: Area Informacion de Referencia (3) - Error al parsear codigo de CFE";
	/*   4 */ public final static String INFOREF_004_NODEF = "CFE Error: Area Informacion de Referencia (4) - Serie de documento no especificada";
	/*   5 */ public final static String INFOREF_005_NODEF = "CFE Error: Area Informacion de Referencia (5) - Número de documento no especificado";
	/*   7 */ public final static String INFOREF_007 = "CFE Error: Fecha no formateada";
	
	//  Area: CAE
	
	/*     */ public final static String CAE_NODEF = "CFE Error: Area Constancia de Autorizacion de Emision - CAE no asignado a la factura";
	
	/* Mensajes Invoicy      */	public final static String INVOICY_DOCNOLINES = "CFE OpenUp-Migrate Provider Error: Area Detalle del CFE (116) - El documento no tiene lineas";
	
	/* Genérico par el envío */ public final static String CFE_ERROR_PROVEEDOR = "CFE Error - Validación externa: ";
	
	
	
	
	
}
