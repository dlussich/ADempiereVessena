package org.openup.model;

import java.math.BigDecimal;

import org.compiere.util.Env;

import jxl.Cell;

/**
 * 
 * OpenUp. issue #872
 * POrderLine
 * Descripcion : Clase encargada de crear las lineas de la orden de compra
 * @author Nicolas Sarlabos
 * Fecha : 08/09/2011
 */
public class POrderLine {
		
	public String unidadMedida="";
	public String valueProducto="";
	public BigDecimal qtyEntered=Env.ZERO;
	public String descripcion="";

	public POrderLine() {
		
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getValueProducto() {
		return valueProducto;
	}

	public void setValueProducto(String valueProducto) {
		this.valueProducto = valueProducto;
	}

	public BigDecimal getQtyEntered() {
		return qtyEntered;
	}

	public void setQtyEntered(BigDecimal qtyEntered) {
		this.qtyEntered = qtyEntered;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public boolean setValueProducto(Cell aux){
		boolean s=false;
		if (aux.getContents() != "") {
			valueProducto = (aux.getContents().trim());
			s=true;
		
		}
		return s;
	}
	
	public boolean setQtyEntered(Cell aux){
		boolean s=false;
		if (aux.getContents() != "") {
			qtyEntered = formatCant(aux.getContents());
			if (!(qtyEntered.compareTo(BigDecimal.ZERO)<=0)) {
				s= true;
				
			}
		}
		return s;
	}
	
	public boolean setUnidadMedida(Cell aux){
		boolean s=false;
		if (aux.getContents() != "") {
			unidadMedida = (aux.getContents().trim());
			s=true;
		
		}
		return s;
	}
	
	private BigDecimal formatCant(String clave) {

		try {
			return BigDecimal.valueOf(Double.parseDouble(clave));
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}

	}
	
	
	
}
