package org.openup.beans;

import java.math.BigDecimal;

import java.sql.Timestamp;


/**
 * 
 * OpenUp.
 * AuxWorkCellXLS
 * Descripcion :
 * @author Leonardo Boccone
 * Fecha : 10/03/2014
 */
public class RefBeanModelo {
	
	private Timestamp fecha; 
	private BigDecimal entrada;
	private BigDecimal salida;
	private int c_currency_id;
	private int uy_fdu_caja_type_id;
	private int uy_fdu_caja_ref_id;
	private String reference;
	
	
	
	/**
	 * Constructor.
	 * @param timestamp
	 * @param entrada
	 * @param salida
	 * @param c_currency_id
	 * @param uy_fdu_caja_type_id
	 * @param uy_fdu_caja_ref_id
	 * @param reference 
	 */
	public RefBeanModelo(Timestamp timestamp, BigDecimal entrada,
			BigDecimal salida, int c_currency_id,
			int uy_fdu_caja_type_id, int uy_fdu_caja_ref_id, String reference) {
		super();
		this.fecha = timestamp;
		this.entrada = entrada;
		this.salida = salida;
		this.c_currency_id = c_currency_id;
		this.uy_fdu_caja_type_id = uy_fdu_caja_type_id;
		this.uy_fdu_caja_ref_id = uy_fdu_caja_ref_id;
		this.reference=reference;
	}




	public String getReference() {
		return reference;
	}




	public void setReference(String reference) {
		this.reference = reference;
	}




	public Timestamp getFecha() {
		return fecha;
	}



	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}



	public BigDecimal getEntrada() {
		return entrada;
	}



	public void setEntrada(BigDecimal entrada) {
		this.entrada = entrada;
	}



	public BigDecimal getSalida() {
		return salida;
	}



	public void setSalida(BigDecimal salida) {
		this.salida = salida;
	}



	public int getC_currency_id() {
		return c_currency_id;
	}



	public void setC_currency_id(int c_currency_id) {
		this.c_currency_id = c_currency_id;
	}



	public int getUy_fdu_caja_type_id() {
		return uy_fdu_caja_type_id;
	}



	public void setUy_fdu_caja_type_id(int uy_fdu_caja_type_id) {
		this.uy_fdu_caja_type_id = uy_fdu_caja_type_id;
	}



	public int getUy_fdu_caja_ref_id() {
		return uy_fdu_caja_ref_id;
	}



	public void setUy_fdu_caja_ref_id(int uy_fdu_caja_ref_id) {
		this.uy_fdu_caja_ref_id = uy_fdu_caja_ref_id;
	}




	public String getKey() {
		String key= this.fecha.toString()+"_"+this.c_currency_id+"_"+this.uy_fdu_caja_ref_id+"_"+this.reference;
		return key;
	}
	
	
	
	
	
}
