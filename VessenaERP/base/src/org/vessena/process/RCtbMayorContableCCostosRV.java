package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.InfoDocumentoBean;

/**
 * OpenUp.
 * RCtbMayorContableCCostosRV
 * Descripcion : Reporte Contable : Mayor Contable Por Centro de Costo
 * @author Guillermo Brust
 * ISSUE #15 - Version 2.5.1
 * Fecha : 31/08/2012
 */

public class RCtbMayorContableCCostosRV extends SvrProcess {

	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	
	private int UY_Categoria_CCostos_ID = 0;
	
	private int idCentroCostoDesde = 0;
	private int idCentroCostoHasta = 0;
	
	private int c_elementValue_idDesde = 0;
	private int c_elementValue_idHasta = 0;
	
	private String tipoMoneda = "";
	private boolean conSaldoInicial = true;
	
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;
	
	private String idReporte = "";
	

	private static final String TIPO_MONEDA_NACIONAL = "MN";	
	private static final String TABLA_MOLDE = "UY_MOLDE_RCtbMayorCCostosRV";
	
	/**
	 * Constructor
	 */
	public RCtbMayorContableCCostosRV() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){				
				if (name.equalsIgnoreCase("datetrx")){
					this.fechaDesde = (Timestamp)para[i].getParameter();
					this.fechaHasta = (Timestamp)para[i].getParameter_To();
				}
				if(name.equalsIgnoreCase("UY_Categoria_CCostos_ID")){
					if (para[i].getParameter()!=null)						
						this.UY_Categoria_CCostos_ID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("C_Activity_ID")){
					if (para[i].getParameter()!=null)
						this.idCentroCostoDesde = ((BigDecimal)para[i].getParameter()).intValueExact();
					if (para[i].getParameter_To()!=null)
						this.idCentroCostoHasta = ((BigDecimal)para[i].getParameter_To()).intValueExact();
				}	
				if (name.equalsIgnoreCase("C_ElementValue_ID")){
					if (para[i].getParameter()!=null)
						this.c_elementValue_idDesde = ((BigDecimal)para[i].getParameter()).intValueExact();
					if (para[i].getParameter_To()!=null)
						this.c_elementValue_idHasta = ((BigDecimal)para[i].getParameter_To()).intValueExact();
				}				
				if (name.equalsIgnoreCase("UY_TipoMonedaContable")){
					if (para[i].getParameter()!=null)
						this.tipoMoneda = (String)para[i].getParameter();
				}								
				if (name.equalsIgnoreCase("UY_ConSaldoInicial")){					
					this.conSaldoInicial = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				}			
				
				
				if (name.equalsIgnoreCase("AD_User_ID")){
					this.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Org_ID")){
					this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}			
				
			}	
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));
		
	}
		
	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.
		this.loadMovimientos();
		
		// Calculo saldos 
		this.calculoSaldos();
		
		// Me aseguro de no mostrar registros con importes en cero
		this.deleteBasuraTemporal();
		
		return "ok";
	}

	
	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id =" + this.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
	}

	/* Elimino registros de tabla temporal con entradas y salidas en cero. No deberia haber ningun registro pero me cubro de errores. */
	private void deleteBasuraTemporal(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND debe=0 " +
				  " AND haber=0 ";
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
	}

	/**
	 * OpenUp.	
	 * Descripcion : Carga movimientos de cuentas en la tabla molde.
	 * @author  Guillermo Brust
	 * Fecha : 29/08/2012
	 */
	private void loadMovimientos(){
		
		String insert = "", sql = "" , whereCuentas = "", camposImportes = "" , whereCentros = "", whereCategoria = "";
		
		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, ad_user_id, idreporte, fecreporte, datetrx, nombrecuenta, " +
					 									"idasiento, descasiento, observaciones, idmonedaasiento, c_currency_id, " +
					 									"imporigen,   saldoinicial, debe, haber, saldoperiodo, saldoacumulado, idsocionegocio, nombresocionegocio, idtabla, " +
					 									"nomtabla, c_activity_id, UY_Categoria_CCostos_ID, c_doctype_id, documentno, docname, c_elementValue_id, valorcuenta, aliascuenta) " ;
		// Condicion de cuentas	
			if(this.UY_Categoria_CCostos_ID > 0) {
				whereCategoria = " AND act.uy_categoria_ccostos_ID = " + this.UY_Categoria_CCostos_ID;
			}
			if (this.c_elementValue_idDesde>0) 
				whereCuentas = " AND a.account_id>=" + this.c_elementValue_idDesde;
			if (this.c_elementValue_idHasta>0) 
				whereCuentas += " AND a.account_id<=" + this.c_elementValue_idHasta;
			if (this.idCentroCostoDesde>0) 
				whereCentros = " AND a.c_activity_id>=" + this.idCentroCostoDesde;
			if (this.idCentroCostoHasta>0) 
				whereCentros += " AND a.c_activity_id<=" + this.idCentroCostoHasta;
			
			if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_NACIONAL))
				camposImportes = "a.amtacctdr, a.amtacctcr, "; 
			else
				camposImportes = "a.uy_amtnativedr, a.uy_amtnativecr, ";
			
			
			sql = "SELECT " + this.adClientID + "," + this.adOrgID + "," + this.adUserID + ",'" + this.idReporte +  "',current_date, a.dateacct, cta.name, " +
					  "a.record_id, '','', a.c_currency_id, a.uy_accnat_currency_id, 0, 0, " + 
					  camposImportes +
					  " 0, 0, a.c_bpartner_id, coalesce(bp.name,'') as bpname, a.ad_table_id, tab.tablename, a.c_activity_id, " + this.UY_Categoria_CCostos_ID + ", 0, '0', '', a.account_id, cta.value, cta.alias" + 
					  " FROM fact_acct a " +					
					  " INNER JOIN ad_table tab ON a.ad_table_id = tab.ad_table_id " +
					  " INNER JOIN c_elementvalue cta ON a.account_id = cta.c_elementvalue_id " +
					  " LEFT OUTER JOIN c_bpartner bp ON a.c_bpartner_id = bp.c_bpartner_id " +
					  " LEFT OUTER JOIN c_activity act ON act.c_activity_ID = a.c_activity_ID" +
					  " WHERE a.ad_client_id =" + this.adClientID +
					  " AND a.ad_org_id =" + this.adOrgID +
					  " AND a.dateacct between '" + this.fechaDesde + "' AND '" + this.fechaHasta + "' " +
					  whereCuentas + whereCentros + whereCategoria; 
					  
				  

			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
			throw new AdempiereException(e);
		}
	}

	/**
	 * OpenUp.	
	 * Descripcion : Calcula saldos y datos de documentos.
	 * @author  Gabriel Vila 
	 * Fecha : 03/10/2010
	 */
	private void calculoSaldos(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			BigDecimal saldoPeriodo = new BigDecimal(0);
			BigDecimal saldoAcumulado = new BigDecimal(0);
			BigDecimal saldoInicial = new BigDecimal(0);
			String valorcuenta = "";
			
			// Obtengo registros de la temporal ordenados por fecha - idAsiento - idtabla
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY c_activity_id, valorcuenta, datetrx,  idasiento, idtabla ASC";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();

			// Recorro registros y voy actualizando
			while (rs.next()){

				// Obtengo cuenta de este registro
				String valorCuentaAux = rs.getString("valorcuenta");
				
				// Si hay cambio de cuenta contable con respecto al registro anterior
				if (!valorCuentaAux.equalsIgnoreCase(valorcuenta)){
					
					// Si el usuario selecciona opcion de reporte con saldo inicial
					if (this.conSaldoInicial){
						// Obtengo saldo inicial de esta nueva cuenta contable
						saldoInicial = this.getSaldoInicial(rs.getInt("c_activity_id"), rs.getInt("c_elementValue_id"));

						// Actualizo saldo inicial para este nueva cuenta
						this.updateSaldoInicial(rs.getInt("c_activity_id"), saldoInicial, rs.getInt("c_elementValue_id"));
					}
					else{
						saldoInicial = Env.ZERO;
					}
					// Reseto acumulado = saldo inicial 
					saldoAcumulado = saldoInicial;
					// Reseteo Saldo Periodo
					saldoPeriodo = Env.ZERO;
					// Guardo nuevo valor de cuenta en proceso
					valorcuenta = valorCuentaAux;
				}
				
				// Calculo saldo del periodo y saldo acumulado segun el asiento sea al debe o al haber
				if (rs.getBigDecimal("debe").doubleValue()!=0){
					saldoPeriodo = saldoPeriodo.add(rs.getBigDecimal("debe"));
					saldoAcumulado = saldoAcumulado.add(rs.getBigDecimal("debe"));
				}
				if (rs.getBigDecimal("haber").doubleValue()!=0){
					saldoPeriodo = saldoPeriodo.subtract(rs.getBigDecimal("haber"));
					saldoAcumulado = saldoAcumulado.subtract(rs.getBigDecimal("haber"));
				}
				
				// Obtengo datos del documento asociado a este asiento, segun el tabla y idasiento
				InfoDocumentoBean infoDoc = this.getInfoDocumento(rs.getInt("idasiento"),rs.getString("nomtabla"));
				
				// Actualizo importes y datos de documento de este registro en la temporal
				this.updateDatosRegistro(rs.getInt("c_activity_id"), rs.getInt("c_elementValue_id"), rs.getTimestamp("datetrx"), rs.getInt("idasiento"), rs.getInt("idtabla"), 
					  				     saldoPeriodo, saldoAcumulado, infoDoc, rs.getString("nombresocionegocio"));
			}

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}


	/**
	 * OpenUp.	
	 * Descripcion : Dada una tabla y el id de un registro de la misma, obtengo informacion del documento asociado al registro.
	 * @param idRegistro
	 * @param nomTabla
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 03/10/2010
	 */
	private InfoDocumentoBean getInfoDocumento(int idRegistro, String nomTabla) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		InfoDocumentoBean value = new InfoDocumentoBean();
		
		try{
			
			if (nomTabla.equalsIgnoreCase("C_Invoice")){
				sql = " SELECT a.c_doctype_id, a.documentno, doc.docbasetype, doc.printname, coalesce(a.description,'') as observa " +
			  	" FROM " + nomTabla + " a " +
			  	" INNER JOIN C_DocType doc ON a.c_doctype_id = doc.c_doctype_id " +
			  	" WHERE a." + nomTabla + "_ID =" + idRegistro;
			}
			else if (!nomTabla.equalsIgnoreCase("C_AllocationHdr"))
				sql = " SELECT a.c_doctype_id, a.documentno, doc.docbasetype, doc.printname, '' as observa " +
				  	" FROM " + nomTabla + " a " +
				  	" INNER JOIN C_DocType doc ON a.c_doctype_id = doc.c_doctype_id " +
				  	" WHERE a." + nomTabla + "_ID =" + idRegistro;
			else
				sql = " SELECT 0 as c_doctype_id, a.documentno, 'AFE' as docbasetype, 'Afectacion Documentos' as printname, '' as observa " +
			  	" FROM " + nomTabla + " a " +
			  	" WHERE a." + nomTabla + "_ID =" + idRegistro;
				
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value.C_DocType_ID = rs.getInt("c_doctype_id");				
				value.PrintName = rs.getString("printname");
				value.DocumentNo = rs.getString("documentno");
				value.Observaciones = rs.getString("observa");
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	
	}

	/**
	 * OpenUp.	
	 * Descripcion : Actualiza saldo inicial para una determinada cuenta en tabla temporal.
	 * @param idCCosto
	 * @param valor
	 * @param valorCuenta 
	 * @author  Gabriel Vila 
	 * Fecha : 03/10/2010
	 */
	private void updateSaldoInicial(int idCCosto, BigDecimal valor, int c_elementValue_id){

		String sql = "";
		
		try{
			sql = "UPDATE " + TABLA_MOLDE + 
				  " SET saldoinicial = " + valor +
				  " WHERE c_elementValue_id =" + c_elementValue_id +
				  (idCCosto != 0 ? " AND c_activity_id =" + idCCosto : "");
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Actualiza datos de un determinado registro dado por cuenta-fecha-asiento-tabla en la tabla temporal.
	 * @param idCCosto 
	 * @param c_elementValue_id
	 * @param fechaDoc
	 * @param idAsiento
	 * @param idTabla
	 * @param saldoPeriodo
	 * @param saldoAcumulado
	 * @param infoDoc
	 * @author  Gabriel Vila 
	 * Fecha : 03/10/2010
	 */
	private void updateDatosRegistro(int idCCosto, int c_elementValue_id, Timestamp fechaDoc, int idAsiento, int idTabla,
									 BigDecimal saldoPeriodo, BigDecimal saldoAcumulado, InfoDocumentoBean infoDoc, String nomSocioNeg){
		
		String sql = "";
		
		try{

			sql = "UPDATE " + TABLA_MOLDE + 
				  " SET saldoperiodo = " + saldoPeriodo + ", " +
				  " saldoacumulado =" + saldoAcumulado + ", " +
				  " descasiento ='" + infoDoc.PrintName + "-" + infoDoc.DocumentNo + "-" + nomSocioNeg.replaceAll("'", "") + "', " +
				  " observaciones ='" + infoDoc.Observaciones + "', " +
				  " C_DocType_ID =" + infoDoc.C_DocType_ID + ", " +	
				  " docname ='" + infoDoc.PrintName + "', " +
				  " documentno ='" + infoDoc.DocumentNo + "' " +
				  " WHERE c_elementValue_id =" + c_elementValue_id + 
				  " AND datetrx = '" + fechaDoc + "'" +
				  " AND idasiento =" + idAsiento + 
				  " AND idtabla =" + idTabla +
				  ((idCCosto > 0) ? " AND c_activity_id=" + idCCosto : "");
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de asientos contables a determinado fecha para una determinada cuenta contable.
	 * @param idCCosto
	 * @param c_elementValue_id
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 */
	private BigDecimal getSaldoInicial(int idCCosto, int c_elementValue_id){
		
		String sql = "", camposImportes = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);

		
		if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_NACIONAL))
			camposImportes = "(a.amtacctdr - a.amtacctcr)"; 
		else
			camposImportes = "(a.uy_amtnativedr - a.uy_amtnativecr)";

		
		try{
			sql = "SELECT COALESCE(SUM(" + camposImportes + "),0) as saldo " +
				  " FROM fact_acct a " +
				  " WHERE a.ad_client_id =" + this.adClientID +
				  " AND a.ad_org_id =" + this.adOrgID +
				  (c_elementValue_id != 0 ? "AND a.account_id = " + c_elementValue_id : "") +
				  (idCCosto!=0? " AND a.c_activity_id = " + idCCosto : "") +
				  " AND a.dateacct <? "; 

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setTimestamp(1, this.fechaDesde);			
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value = rs.getBigDecimal("saldo");
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;		
	}

}
