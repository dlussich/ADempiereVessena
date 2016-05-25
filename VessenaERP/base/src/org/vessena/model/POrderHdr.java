package org.openup.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;

import jxl.Cell;
import jxl.DateCell;

import org.compiere.util.Env;

/**
 * 
 * OpenUp. issue #872
 * POrderHdr
 * Descripcion : Clase encargada de crear el cabezal de la orden de compra
 * @author Nicolas Sarlabos
 * Fecha : 08/09/2011
 */
 //org.openup.model.POrderHdr
public class POrderHdr {
		
	private String poReference = "";
	private String gln = "";
	private String comprador = "";
	public HashMap<String, POrderLine>	lista= new HashMap<String, POrderLine>();
	private String descripcion = "";
	private Timestamp fechaOrden = null;
	private Timestamp fechaEntrega = null;

	public POrderHdr() {
				
	}	

	public Timestamp getFechaOrden() {
		return fechaOrden;
	}

	public void setFechaOrden(Timestamp fechaOrden) {
		this.fechaOrden = fechaOrden;
	}

	public Timestamp getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Timestamp fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getPoReference() {
		return poReference;
	}

	public void setPoReference(String poReference) {
		this.poReference = poReference;
	}

	public String getGln() {
		return gln;
	}

	public void setGln(String gln) {
		this.gln = gln;
	}

	public String getComprador() {
		return comprador;
	}

	public void setVendendor(String comprador) {
		this.comprador = comprador;
	}

	public Collection<POrderLine> getLineas() {
		return lista.values();
	}

	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public boolean setpoReference(Cell aux){
		boolean s=false;
		if (aux.getContents() != "") {
			poReference = (aux.getContents().trim());
			s=true;
		
		}
		return s;
	}
	
	public boolean setComprador(Cell aux){
		boolean s=false;
		if (aux.getContents() != "") {
			comprador = (aux.getContents().trim());
			s=true;
		
		}
		return s;
	}
	
	public boolean setFechaOrden(Cell aux){
		boolean s=false;
		if (aux.getContents() != "") {
			fechaOrden = formatDate(aux);
		}
		if(fechaOrden !=null){
			// Si fecha de orden es menor al dia de hoy. se pondra el dia de hoy
			if(fechaOrden.compareTo(new Timestamp(System.currentTimeMillis()))==-1){
				fechaOrden= new Timestamp(System.currentTimeMillis());
			}
			
			s=true;
		}
		return s;
	}
	
	public boolean setFechaEntrega(Cell aux){
		boolean s=false;
		if (aux.getContents() != "") {
			fechaEntrega = formatDate(aux);
		}
		if(fechaEntrega !=null){
			// Si fecha entrega es menor al dia de hoy. se pondra el dia de hoy
			if(fechaEntrega.compareTo(new Timestamp(System.currentTimeMillis()))==-1){
				fechaEntrega= new Timestamp(System.currentTimeMillis());
			}
			
			s=true;
		}
		return s;
	}
	
	public boolean setGln(Cell aux){
		boolean s=false;
		if (aux.getContents() != "") {
			gln = (aux.getContents().trim());
			s=true;
		
		}
		return s;
	}

	private Timestamp formatDate(Cell clave) {

		Timestamp aux=null;
		 try {
			DateCell dateCell = (DateCell) clave;

			//TODO pongo mas 1 porque no se encontro otra manera rapidamente	

			aux = new java.sql.Timestamp (dateCell.getDate().getTime()+86400000);
				
		} catch (Exception e) {
		
			aux= new Timestamp(System.currentTimeMillis());
		}
		
		
		return aux;
	}
	
	public boolean validar(POrderLine l){
		return (!(poReference.equals("")))&& gln!=null && fechaEntrega!=null && fechaOrden!=null && l.valueProducto!=null && (l.qtyEntered.compareTo(Env.ZERO)>0);
	}

	public String claveCabezal(){
		return poReference + "-" + gln;
	}
	// OpenUp. Nicolas Garcia. 13/10/2011. Issue #887.
	public String claveLinea(POrderLine l){
		return poReference + "-" + gln + "-" + l.valueProducto + "-" + l.unidadMedida;
	}
	//Fin Issue #887	
	public void remover(POrderLine linea) {
		lista.remove(this.claveLinea(linea));
		
	}
	public void agregarLinea(POrderLine linea) {
		lista.put(this.claveLinea(linea), linea);		
	}
	
}
