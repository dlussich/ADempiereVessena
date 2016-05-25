package org.openup.cfe.provider.sisteco;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SistecoResponseDTO {
	private int status;
	private String descripcion;
	private int tipoCFE;
	private String serie;
	private BigDecimal mro;
	private Timestamp tmstFirma;
	private String digestValue;
	private int resolucion;
	private int anioResolucion;
	private String urlDocumentoDGI;
	private BigDecimal caeId;
	private BigDecimal dNro;
	private BigDecimal hNro;
	private Timestamp fecVenc;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getTipoCFE() {
		return tipoCFE;
	}
	public void setTipoCFE(int tipoCFE) {
		this.tipoCFE = tipoCFE;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public BigDecimal getMro() {
		return mro;
	}
	public void setMro(BigDecimal mro) {
		this.mro = mro;
	}
	public Timestamp getTmstFirma() {
		return tmstFirma;
	}
	public void setTmstFirma(Timestamp tmstFirma) {
		this.tmstFirma = tmstFirma;
	}
	public String getDigestValue() {
		return digestValue;
	}
	public void setDigestValue(String digestValue) {
		this.digestValue = digestValue;
	}
	public int getResolucion() {
		return resolucion;
	}
	public void setResolucion(int resolucion) {
		this.resolucion = resolucion;
	}
	public int getAnioResolucion() {
		return anioResolucion;
	}
	public void setAnioResolucion(int anioResolucion) {
		this.anioResolucion = anioResolucion;
	}
	public String getUrlDocumentoDGI() {
		return urlDocumentoDGI;
	}
	public void setUrlDocumentoDGI(String urlDocumentoDGI) {
		this.urlDocumentoDGI = urlDocumentoDGI;
	}
	public BigDecimal getCaeId() {
		return caeId;
	}
	public void setCaeId(BigDecimal caeId) {
		this.caeId = caeId;
	}
	public BigDecimal getdNro() {
		return dNro;
	}
	public void setdNro(BigDecimal dNro) {
		this.dNro = dNro;
	}
	public BigDecimal gethNro() {
		return hNro;
	}
	public void sethNro(BigDecimal hNro) {
		this.hNro = hNro;
	}
	public Timestamp getFecVenc() {
		return fecVenc;
	}
	public void setFecVenc(Timestamp fecVenc) {
		this.fecVenc = fecVenc;
	}
	
	
}
