/**
 * FiltrosGenEntregasBean.java
 * 17/10/2010
 */
package org.openup.beans;

import java.sql.Timestamp;

/**
 * OpenUp.
 * FiltrosGenEntregasBean
 * Descripcion : Bean para manejo de informacion de Filtros en el proceso de Generacion de Ordenes de Entrega.
 * @author Gabriel Vila
 * Fecha : 17/10/2010
 */
public class FiltrosGenEntregasBean {

	public int idDeposito = -1;
	public int idCanalVenta = -1;
	public int idCliente = -1;
	public Timestamp fechaEntregaDesde;
	public Timestamp fechaEntregaHasta;
}
