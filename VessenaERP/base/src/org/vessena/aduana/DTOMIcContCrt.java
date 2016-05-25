package org.openup.aduana;

import org.openup.aduana.ConsultaRespuestaMic.*;


public class DTOMIcContCrt {

	private CrtStatus crtStatus;
	private String secNroCrt;
	private LineImgStatus crtImgStatus;
	private LineImgStatus crtLineStatus;
	private String nroLinea;
	private String nroImg;
	private String secuencial;
	private String conocimientoOriginalNumero;
	private String nroCrtImgVinculada;
	private int id;
	
	
	public CrtStatus getCrtStatus() {
		return crtStatus;
	}


	public void setCrtStatus(CrtStatus crtStatus) {
		this.crtStatus = crtStatus;
	}


	public String getSecNroCrt() {
		return secNroCrt;
	}


	public void setSecNroCrt(String secNroCrt) {
		this.secNroCrt = secNroCrt;
	}


	public LineImgStatus getCrtImgStatus() {
		return crtImgStatus;
	}


	public void setCrtImgStatus(LineImgStatus crtImgStatus) {
		this.crtImgStatus = crtImgStatus;
	}


	public LineImgStatus getCrtLineStatus() {
		return crtLineStatus;
	}


	public void setCrtLineStatus(LineImgStatus crtLineStatus) {
		this.crtLineStatus = crtLineStatus;
	}


	public String getNroLinea() {
		return nroLinea;
	}


	public void setNroLinea(String nroLinea) {
		this.nroLinea = nroLinea;
	}


	public String getNroImg() {
		return nroImg;
	}


	public void setNroImg(String nroImg) {
		this.nroImg = nroImg;
	}


	public DTOMIcContCrt() {
		// TODO Auto-generated constructor stub
	}


	public String getSecuencial() {
		return secuencial;
	}


	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}


	public String getConocimientoOriginalNumero() {
		return conocimientoOriginalNumero;
	}


	public void setConocimientoOriginalNumero(String conocimientoOriginalNumero) {
		this.conocimientoOriginalNumero = conocimientoOriginalNumero;
	}


	public String getNroCrtImgVinculada() {
		return nroCrtImgVinculada;
	}


	public void setNroCrtImgVinculada(String nroCrtImgVinculada) {
		this.nroCrtImgVinculada = nroCrtImgVinculada;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

}
