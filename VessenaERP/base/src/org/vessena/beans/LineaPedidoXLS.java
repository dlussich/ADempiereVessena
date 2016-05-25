/**
 * LineaPedidoXLS.java
 * 11/03/2011
 */
package org.openup.beans;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import jxl.Cell;

import org.compiere.model.MProduct;
import org.compiere.util.CLogger;
import org.compiere.util.DB;



/**
 * OpenUp.
 * LineaPedidoXLS
 * Descripcion :
 * @author Administrador
 * Fecha : 11/03/2011
 */
public class LineaPedidoXLS {

	CLogger log=null;
	
	public MProduct product=null;
	public BigDecimal cant=null;
	public BigDecimal porcientoDescuent=null;
	public Cell cellPorcientoDescuent=null;
	
	
	// Constructo con log
	public LineaPedidoXLS(CLogger l){
		log=l;
	}
	
	
	
	// 2-Valido producto ubicado columna k que es 10
	public String setProduct( Cell aux, Properties ctx)throws Exception{
		String s = "Error en Codigo de Producto, verifique que exista y este activo";
		if (aux.getContents() != "" && !(aux.getContents().equals("0"))) {
			s = this.getProduct(aux.getContents().trim(),ctx);
			// Mensaje si no encontro nada.
			if (product !=null) {
				return "";
			}
		}
		return s;
	}

	// Valido porcientoDescuent que exista y que sea mayor a cero ubicado  columna r que es 17
	public boolean setPorcientoDescuent( Cell aux){
		cellPorcientoDescuent=aux;
		boolean s=false;
		if (aux.getContents() != "") {
			porcientoDescuent = formatPorcentaje(aux.getContents());
			return true;
		}
		return s;
	}

	// 5-Valido Cant que exista y que sea mayor a cero ubicado  columna p que es 15
	public boolean setCant( Cell aux){
		boolean s=false;
		if (aux.getContents() != "") {
			cant = formatCant(aux.getContents());
			if (!(cant.compareTo(BigDecimal.ZERO)<=0)) {
				// TODO:MENSAJE //Mensaje si no encontro nada.
				s= true;
			}
		}
		return s;
	}
	

	
	private String getProduct(String clave, Properties ctx) throws Exception {

		//MProduct this.product = null; // Nullo cuando no encuentra nada.
		String salida="";
		if (clave == null)
			return "Clave Invalida";

		// OpenUp. Nicolas Garcia. 15/09/2011. Issue #878.

		String sql = "SELECT * FROM m_product  WHERE (trim(value)=? OR trim(upc)=?) AND IsActive='Y'";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Selecciono el Producto
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, clave.trim());
			pstmt.setString(2, clave.trim());
			rs = pstmt.executeQuery();

			// Selecciono el Producto, Si existe traer el producto, Se controla si por el upc se trae repetidos.
			int cont=0;
			
			while (rs.next()) {
				// Si hay dos registros con ese value o upc tira error
				if(cont==0){
					this.product = new MProduct (ctx,rs,null);
				}
				cont+=1;
			}
		
			//Si no se encontro producto por value o upc y no se encontro productos repetidos
			if(this.product==null && salida.equals("")){
				DB.close(rs, pstmt);
				//Se busca upc2
				sql = "SELECT * FROM m_product  WHERE trim(upc2)=? AND IsActive='Y'";
				rs = null;
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setString(1, clave.trim());
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					// Si hay dos registros con ese upc2 tira error
					if(cont==0){
						this.product = new MProduct (ctx,rs,null);
					}
					cont+=1;
				}
				
				// Fin Issue #878
			}
			
			//Mensaje de error por repetidos
			if(cont>1){
				this.product=null;
				salida+="Hay "+cont+" productos registrado con el codigo: "+clave;
			}
			
			if(this.product==null){
				salida+="No existe producto registrado con el codigo: "+clave;
			}
			
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return salida;
	}
	

	private BigDecimal formatCant(String clave) {

		try {
			return BigDecimal.valueOf(Double.parseDouble(clave));
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}

	}
	private BigDecimal formatPorcentaje(String clave) {

		try {
			return BigDecimal.valueOf(Double.parseDouble(clave));
		} catch (Exception e) {
			return null;
		}

	}
}
