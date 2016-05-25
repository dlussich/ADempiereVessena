package org.openup.beans;

import java.math.BigDecimal;

public class SaldoSocioNegocioBean {

	public Long idSocioNegocio = new Long(0);
	public Long idTipoComprobante = new Long(0);
	public Long idComprobante = new Long(0);
	public BigDecimal saldoPendiente = new BigDecimal(0);
	public BigDecimal saldoAcumulado = new BigDecimal(0);
	public BigDecimal importeOriginal = new BigDecimal(0);
}
