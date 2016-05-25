package org.openup.beans;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

import jxl.Cell;
import jxl.DateCell;

import org.compiere.model.MBPartnerLocation;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

public class CabPedidoXLS {

	CLogger									log				= null;
	public String							GLN				= "";
	public String							nroOrden		= "";
	public MBPartnerLocation				partnerLocation	= null;
	public Timestamp						fechaOrden		= null;
	public Timestamp						fechaEntrega	= null;
	public HashMap<String, LineaPedidoXLS>	lista			= new HashMap<String, LineaPedidoXLS>();
	// OpenUp. Nicolas Garcia. 08/08/2011. Issue #812.
	public String							paymentTerm		= "";
	public BigDecimal                           descalpie       = null;
	public Cell                             cellDescAlPie   = null;
	public BigDecimal                       porcientoDescPie= null;

	public int getPaymentTermID() {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = "SELECT COALESCE ((SELECT c_paymentterm_id FROM c_paymentterm WHERE value=?),"
					+ "(SELECT c_paymentterm_id FROM c_bpartner WHERE c_bpartner_id =?)) as paymentterm";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, paymentTerm);
			pstmt.setInt(2, partnerLocation.getC_BPartner_ID());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("paymentterm");
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
			return 0;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return 0;
	}
	// Fin Issue #812
	
	
	
	
	// Constructo con log
	public CabPedidoXLS(CLogger l){
		log=l;
	}
	
	public BigDecimal getDescalpie() {
		return descalpie;
	}

	public void setDescalpie(Cell aux) {
	
		if (aux.getContents() != "") {
			porcientoDescPie = formatPorcentaje(aux.getContents());
		}
		
		
	}




	public String claveLinea(LineaPedidoXLS l){
		return nroOrden+"-"+partnerLocation.getC_BPartner_Location_ID()+"-"+l.product.getM_Product_ID();
	}
	public String claveCabezal(){
		return nroOrden+"-"+partnerLocation.getC_BPartner_Location_ID();
	}
	
 	public boolean validar(LineaPedidoXLS l){
		return (!(nroOrden.equals("")))&&partnerLocation!=null &&fechaEntrega!=null && fechaOrden!=null && l.product!=null && l.cant !=null;
	}
	// 1-Valido GLN o VALUE ubicado columna G que es 6
	public boolean setPartnerLocation( Cell aux, Properties ctx)throws Exception{
		boolean s=false;
		if (aux.getContents() != "") {
			partnerLocation = this.getPartnerLocation(aux.getContents().trim(),ctx);
			// Mensaje si no encontro nada.
			if (partnerLocation !=null) {
	
				s= true;
			}
		}
		return s;
	}
	
	
	// 3-Valido Nro Order No NULL ubicado columna a que es 0
	public boolean setNroOrden( Cell aux){
		boolean s=false;
		if (aux.getContents() != "") {
			nroOrden = (aux.getContents().trim());
			s=true;
		
		}
		return s;
	}

	// 4-Valido FechaOrden ubicado columna d que es 3
	public boolean setFechaOrden( Cell aux){
		boolean s=false;
		if (aux.getContents() != "") {
			fechaOrden = formatDate(aux);
		}
		if(fechaOrden !=null){
			s=true;
		}
		return s;
	}

	//OpenUp Nicolas Sarlabos 10/04/2012
	public Timestamp getFechaOrden() {
		return fechaOrden;
	}
	//Fin OpenUp Nicolas Sarlabos 10/04/2012

	// 4-Valido FechaEntrega ubicado columna e que es 4
	public boolean setFechaEntrega( Cell aux,Timestamp forden){
		boolean s=false;
		if (aux.getContents() != "") {
			fechaEntrega = formatDate(aux);
		}
		//OpenUp Nicolas Sarlabos 10/04/2012
		if(fechaEntrega !=null){
			// Si fecha entrega es menor o igual al dia de hoy -> fechaEntrega=actual + 1 dia
			if(fechaEntrega.compareTo(new Timestamp(System.currentTimeMillis()))<=0){
				fechaEntrega= new Timestamp(System.currentTimeMillis()+86400000);

			}
			// Si fecha entrega es menor o igual a la de la orden -> fechaEntrega=actual + 1 dia
			if(forden!=null){
				if(fechaEntrega.compareTo(forden)<=0){
					fechaEntrega= new Timestamp(System.currentTimeMillis()+86400000);
				}
			}
			//Fin OpenUp Nicolas Sarlabos 10/04/2012

			s=true;
			//OpenUp Nicolas Sarlabos 06/06/2012
		}else{
			if(forden!=null){
			      Calendar calCalendario = Calendar.getInstance();
				  calCalendario.setTimeInMillis(forden.getTime());
				  
				  calCalendario.add(Calendar.DATE, 1);
				  fechaEntrega = new Timestamp(calCalendario.getTimeInMillis());
					
			}
			
			s=true;
		}
		//Fin OpenUp Nicolas Sarlabos 06/06/2012
		return s;
	}
	
	private BigDecimal formatPorcentaje(String clave) {

		try {
			return BigDecimal.valueOf(Double.parseDouble(clave));
		} catch (Exception e) {
			return null;
		}

	}


	public MBPartnerLocation getPartnerLocation(String clave , Properties ctx) throws Exception {
		MBPartnerLocation model = null; // Retorna null si no encuentra nada

		if (clave == null)	return null;

		String sql = "SELECT l.c_bpartner_location_id FROM  c_bpartner_location l "
				+ "LEFT JOIN c_bpartner b ON b.c_bpartner_id=l.C_bpartner_id "
				+ "WHERE (trim(b.value)=? OR trim(l.uy_gln)=? ) AND l.isactive='Y' AND b.isactive='Y'";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Create lines form from interfase
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, clave);
			pstmt.setString(2, clave);
			rs = pstmt.executeQuery();

			// Devuelvo solo lo primero que encuentro.
			if (rs.next()) {
				model = new MBPartnerLocation(ctx, rs.getInt("c_bpartner_location_id"), null);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return (model);
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
		

	public void remover(LineaPedidoXLS linea) {
		lista.remove(this.claveLinea(linea));
		
	}

	public void agregarLinea(LineaPedidoXLS linea) {
		lista.put(this.claveLinea(linea), linea);		
	}
	}

