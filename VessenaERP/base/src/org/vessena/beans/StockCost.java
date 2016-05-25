package org.openup.beans;

import java.math.BigDecimal;

/***
 * Clase para manejar informacion de costos en de stock.
 * org.openup.beans - StockCost
 * OpenUp Ltda. Issue #1405 
 * Description: Clase para manejar informacion de costos en de stock.
 * @author Gabriel Vila - 22/07/2014
 * @see
 */
public class StockCost {

	public int cCurrencyID = 0;
	public BigDecimal currencyRate = null;
	public BigDecimal amtSource = null;
	public BigDecimal amtAcct = null;
	
	
	public StockCost() {
	}

	
}
