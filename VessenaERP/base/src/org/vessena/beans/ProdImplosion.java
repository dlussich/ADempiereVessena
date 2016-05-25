package org.openup.beans;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.compiere.util.Env;

public class ProdImplosion {

	private int hijo_id = 0;
	private String hijo_nom = "";
	private int padre_id = 0;
	private String padre_value = "";
	private String padre_nom = "";
	private BigDecimal cant = Env.ZERO;
	private String unidad = "";
	private int categoria_id = 0;
	private int linea_id = 0;
	private int nivel = 0;

	private ArrayList<ProdImplosion> list = new ArrayList<ProdImplosion>();
	
	public int getHijo_id() {
		return hijo_id;
	}

	public void setHijo_id(int hijo_id) {
		this.hijo_id = hijo_id;
	}

	public String getHijo_nom() {
		return hijo_nom;
	}

	public void setHijo_nom(String hijo_nom) {
		this.hijo_nom = hijo_nom;
	}

	public int getPadre_id() {
		return padre_id;
	}

	public void setPadre_id(int padre_id) {
		this.padre_id = padre_id;
	}

	public String getPadre_value() {
		return padre_value;
	}

	public void setPadre_value(String padre_value) {
		this.padre_value = padre_value;
	}

	public String getPadre_nom() {
		return padre_nom;
	}

	public void setPadre_nom(String padre_nom) {
		this.padre_nom = padre_nom;
	}

	public BigDecimal getCant() {
		return cant;
	}

	public void setCant(BigDecimal cant) {
		this.cant = cant;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public int getCategoria_id() {
		return categoria_id;
	}

	public void setCategoria_id(int categoriaId) {
		categoria_id = categoriaId;
	}

	public int getLinea_id() {
		return linea_id;
	}

	public void setLinea_id(int lineaId) {
		linea_id = lineaId;
	}

	public ArrayList<ProdImplosion> getList() {
		return list;
	}

	public void setList(ArrayList<ProdImplosion> list) {
		this.list = list;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}




}
