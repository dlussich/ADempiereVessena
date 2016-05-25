package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class RInvMovimientosStock extends SvrProcess {

	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	private int idAlmacen = 0;
	private int idCategoria = 0;
	private int idProductoDesde = 0;
	private int idProductoHasta = 0;
	private int idEmpresa = 0;
	private int idOrganizacion = 0;
	private String idTipoMovimiento = "";
	private Long idUsuario = new Long(0);
	//private String nombreEmpresa = "";
	//private String nombreUsuario = "";
	private String idReporte = "";

	private static final String TABLA_MOLDE = "UY_MOLDE_RInvMovStock";

	@Override
	protected void prepare() {
		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;
		// Parametro para titulo del reporte
		ProcessInfoParameter paramTituloReporte = null;
		
		// Parametros para fechas
		ProcessInfoParameter paramStartDate = null;
		ProcessInfoParameter paramEndDate = null;
		
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("idReporte")){
					paramIDReporte = para[i]; 
				}
				if (name.equalsIgnoreCase("StartDate")){
					paramStartDate = para[i]; 
				}
				if (name.equalsIgnoreCase("EndDate")){
					paramEndDate = para[i]; 
				}
				if (name.equalsIgnoreCase("tituloReporte")){
					paramTituloReporte = para[i];
				}
				if (name.equalsIgnoreCase("C_Period_ID")){
					this.fechaDesde = (Timestamp)para[i].getParameter();
					this.fechaHasta = (Timestamp)para[i].getParameter_To();
					para[i].setParameter(this.fechaDesde);
					para[i].setParameter_To(this.fechaHasta);
				}
				if (name.equalsIgnoreCase("AD_User_ID")){
					this.idUsuario = ((BigDecimal)para[i].getParameter()).longValueExact();
				}
				if (name.equalsIgnoreCase("C_DocType_ID")){
					if (para[i].getParameter()!=null)
						this.idTipoMovimiento = para[i].getParameter().toString().trim();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.idEmpresa = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Org_ID")){
					this.idOrganizacion = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("M_Warehouse_ID")){
					if (para[i].getParameter()!=null)
						this.idAlmacen = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("M_Product_ID")){
					if (para[i].getParameter()!=null){
						this.idProductoDesde = ((BigDecimal)para[i].getParameter()).intValueExact();												
					}
					if (para[i].getParameter_To()!=null)
						this.idProductoHasta = ((BigDecimal)para[i].getParameter_To()).intValueExact();
						
				}
				if (name.equalsIgnoreCase("M_Product_Category_ID")){
					if (para[i].getParameter()!=null)
						this.idCategoria = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(this.idUsuario);
		
		// Seteo titulo del reporte segun tipo cta corriente
		String tituloReporte = "Movimientos de Stock";		
		// Si tengo parametro para titulo de reporte
		if (paramTituloReporte!=null) paramTituloReporte.setParameter(tituloReporte);

		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);

		// Si tengo parametros de fechas para mostrar en el reporte
		if (paramStartDate!=null) paramStartDate.setParameter(this.fechaDesde);
		if (paramEndDate!=null) paramEndDate.setParameter(this.fechaHasta);
		
	}

	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde, los movimientos de stock segun filtro indicado por el usuario.
		this.loadMovimientos();
		
		// Calculo saldos 
		this.calculoSaldos();
		
		// Me aseguro de no mostrar registros con cantidad de movimiento en cero
		this.deleteBasuraTemporal();
		
		return "ok";
	}

	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE idusuario =" + this.idUsuario;
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}

	/* Elimino registros de tabla temporal con entradas y salidas en cero. No deberia haber ningun registro pero me cubro de errores. */
	private void deleteBasuraTemporal(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND entrada=0 " +
				  " AND salida=0 ";
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	/* Carga movimientos de stock en tabla molde*/
	private void loadMovimientos(){
	
		String insert ="";
		String sql = "";
		String where = "";

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (idReporte, idUsuario, fecReporte, fecDoc, " + 
					"idDoc, docbasetype, tipoDoc, nroDoc, m_product_id, productname, m_warehouse_id, warehousename, " +
					"m_locator_id," +
					"rack, columna, nivel, c_uom_id, unidadname,saldoinicial, entrada, salida, " +
					"saldoacumulado,m_transaction_id, nrofact, bpvalue, bpname) " ;
		
			// Armo condiciones segun filtros ingresados por el usuario
			
			// Rango fechas
			where = " WHERE mov.AD_Client_ID =" + this.idEmpresa + 
					" AND mov.AD_Org_ID =" + this.idOrganizacion +
					" AND mov.movementdate between '" + this.fechaDesde + "' AND '" + this.fechaHasta + "' ";
			
			// Almacen
			if (idAlmacen>0) where = where + " AND war.M_Warehouse_ID=" + idAlmacen;
			
			// Rango Producto
			if (idProductoDesde>0) where = where + " AND mov.M_Product_ID>=" + idProductoDesde;
			if (idProductoHasta>0) where = where + " AND mov.M_Product_ID<=" + idProductoHasta;
		
			// Categoria
			if (idCategoria>0) where = where + " AND prod.M_Product_Category_ID=" + idCategoria;

			// Tipo de movimiento
			if (idTipoMovimiento.equalsIgnoreCase("CLI")){
				where = where + " AND mov.movementtype IN ('C+','C-') ";
			}
			if (idTipoMovimiento.equalsIgnoreCase("MAL")){
				where = where + " AND mov.movementtype IN ('M+','M-') ";
			}
			if (idTipoMovimiento.equalsIgnoreCase("MIN")){
				where = where + " AND mov.movementtype IN ('I+','I-') ";
			}
			if (idTipoMovimiento.equalsIgnoreCase("OPR")){
				where = where + " AND mov.movementtype IN ('P+','P-','W+','W-') ";
			}
			if (idTipoMovimiento.equalsIgnoreCase("PRO")){
				where = where + " AND mov.movementtype IN ('V+','V-') ";
			}
			
			sql = "SELECT '" + this.idReporte + "'," + this.idUsuario + ",current_date," +
			"mov.movementdate, vtd.c_doctype_id, doc.docbasetype, doc.printname, vtd.documentno, " +
			"mov.m_product_id, prod.value || ' - ' || prod.name, loc.m_warehouse_id, war.name, mov.m_locator_id, " +
			"loc.x, loc.y, loc.z, prod.c_uom_id, uom.name,0, " + 
			" case " +
			" when mov.movementqty >=0 then mov.movementqty " +
			" else 0 " +
			" end as entrada, " +
			" case " + 
			" when mov.movementqty <0 then mov.movementqty*-1 " +
			" else 0 " +
			" end as salida,0, mov.m_transaction_id, " +
			" coalesce(iofact.nrofact,'') as nrofact, coalesce(iofact.value,'') as bpvalue, coalesce(iofact.name2,'') as bpname " +
			" FROM m_transaction mov " + 
			" LEFT OUTER JOIN vUY_TransactionsDoc vtd ON (mov.m_transaction_id = vtd.m_transaction_id) " +
			" LEFT OUTER JOIN c_doctype doc ON (vtd.c_doctype_id = doc.c_doctype_id) " +
			" INNER JOIN m_product prod ON (mov.m_product_id = prod.m_product_id) " +
			" INNER JOIN c_uom uom ON (prod.c_uom_id = uom.c_uom_id) " +
			" INNER JOIN m_locator loc ON (mov.m_locator_id = loc.m_locator_id) " +
			" INNER JOIN m_warehouse war ON (loc.m_warehouse_id = war.m_warehouse_id) " +
			" LEFT OUTER JOIN vuy_iofact iofact ON vtd.documentno=iofact.nroio " +
			where +
			" ORDER BY mov.m_product_id, loc.m_warehouse_id, mov.movementdate, vtd.documentno, mov.m_locator_id";
			
			log.info(insert + sql);
			
			DB.executeUpdate(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
		}
	}

	/* Calcula saldos inciales y acumulados y actualiza la temporal */
	private void calculoSaldos(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			// Obtengo registros de la temporal ordenados por producto - almacen - fecha
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY m_product_id, m_warehouse_id, fecdoc, iddoc, nrodoc, m_locator_id ASC";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();
			
			int idProducto = -1;
			int idAlmacen = -1;
			BigDecimal saldoAcumulado = new BigDecimal(0);
			BigDecimal saldoInicial = new BigDecimal(0);
			
			// Tengo que hacer corte de control por socio de negocio, ya que el acumulado se resetea cada
			// vez que inicio un nuevo socio de negocio
			while (rs.next()){
				int idProdAux = rs.getBigDecimal("m_product_id").intValueExact();
				int idWarehouseAux = rs.getBigDecimal("m_warehouse_id").intValueExact();
				
				// Si hay cambio de producto o almacen
				if ((idProdAux!=idProducto) || (idWarehouseAux!=idAlmacen)){
					// Guardo nuevos ids
					idProducto = idProdAux;
					idAlmacen = idWarehouseAux;
					// Obtengo saldo inicial para articulo-deposito
					saldoInicial = this.getSaldoInicial(idProducto, idAlmacen);
					// Actualizo saldo inicial para deposito articulo
					this.updateSaldoInicial(idProducto, idAlmacen, saldoInicial);
					// Acumulado es igual al inicial para el primer registro de este nuevo producto-almacen
					saldoAcumulado = saldoInicial;					
				}
				
				// Acumulo saldo y actualizo en tabla
				if (rs.getBigDecimal("entrada").compareTo(new BigDecimal(0))>0)
					saldoAcumulado = saldoAcumulado.add(rs.getBigDecimal("entrada"));
				if (rs.getBigDecimal("salida").compareTo(new BigDecimal(0))>0)
					saldoAcumulado = saldoAcumulado.subtract(rs.getBigDecimal("salida"));

				this.updateSaldoAcumulado(rs.getBigDecimal("m_transaction_id").intValueExact(), saldoAcumulado);
			}
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	/* Obtengo saldo inicial para un determinado producto - almacen - fecha hasta */
	private BigDecimal getSaldoInicial(int idProducto, int idAlmacen) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
			sql ="SELECT COALESCE(SUM(mov.movementqty),0) as saldo " + 
 		  	" FROM m_transaction mov INNER JOIN m_locator loc ON (mov.m_locator_id = loc.m_locator_id) " + 
		  	" WHERE mov.AD_Client_ID =? "+ 
			" AND mov.AD_Org_ID =?" +
		  	" AND mov.movementdate <? " +
		  	" AND mov.m_product_id =? " +
		  	" AND loc.m_warehouse_id =?";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.idEmpresa);
			pstmt.setInt(2, this.idOrganizacion);
			pstmt.setTimestamp(3, this.fechaDesde);			
			pstmt.setInt(4, idProducto);
			pstmt.setInt(5, idAlmacen);
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value = rs.getBigDecimal("saldo");
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}
	
	private void updateSaldoInicial(int idProducto, int idAlmacen, BigDecimal valor){

		String sql = "";
		
		try{
			sql = "UPDATE " + TABLA_MOLDE + 
			  " SET saldoinicial = " + valor +
			  " WHERE idreporte ='" + this.idReporte + "'" +
			  " AND m_product_id = " + idProducto + 
			  " AND m_warehouse_id =" + idAlmacen;
				
				DB.executeUpdate(sql,null);
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	private void updateSaldoAcumulado(int idTransaction, BigDecimal valor){

		String sql = "";
		
		try{
			sql = "UPDATE " + TABLA_MOLDE + 
			  " SET saldoacumulado = " + valor +
			  " WHERE m_transaction_id = " + idTransaction; 
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
}
