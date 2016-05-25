package org.openup.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class FduPagosRealizado {
	
	private Timestamp fechaPago;
	private BigDecimal importe;
	
	
	public FduPagosRealizado() {		
	}
	

	public Timestamp getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Timestamp fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	

}
