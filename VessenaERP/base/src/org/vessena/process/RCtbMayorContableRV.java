/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 27/08/2012
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCurrency;
import org.compiere.model.MElementValue;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.InfoDocumentoBean;
import org.openup.model.MAcctNav;
import org.openup.model.MAcctNavAccount;


/**
 * OpenUp.
 * RCtbMayorContableCCostosRV
 * Descripcion : Reporte Contable : Mayor Contable Por Centro de Costo
 * @author Guillermo Brust
 * ISSUE #16 - Version 2.5.1
 * Fecha : 31/08/2012
 */
public class RCtbMayorContableRV extends SvrProcess {

	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	
	private int idCuentaDesde = 0;
	private int idCuentaHasta = 0;

	private String tipoMoneda = "";
	private boolean conSaldoInicial = true;

	private int adUserID = 0;
	private int adClientID = 0;
	private int adOrgID = 0;
	private int uyAcctNavID = 0;
	private int cBPGroupID = 0;
	
	private String idReporte = "";

	private static final String TIPO_MONEDA_NACIONAL = "MN";
	private static final String TIPO_MONEDA_CUENTA = "MT";
	private static final String TIPO_MONEDA_SELECCION = "ME";
	
	private int cCurrencyID = 0;
	
	private static final String TABLA_MOLDE = "UY_MOLDE_RCtbMayorContableRV";

	
	/**
	 * Constructor.
	 */
	public RCtbMayorContableRV() {

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 27/08/2012
	 * @see
	 */
	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){

				if (name.equalsIgnoreCase("UY_AcctNav_ID")){
					if (para[i].getParameter()!=null)
						this.uyAcctNavID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("C_BP_Group_ID")){
					if (para[i].getParameter()!=null)
						this.cBPGroupID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				
				if (name.equalsIgnoreCase("DateTrx")){
					this.fechaDesde = (Timestamp)para[i].getParameter();
					this.fechaHasta = (Timestamp)para[i].getParameter_To();
				}

				if (name.equalsIgnoreCase("C_ElementValue_ID")){
					if (para[i].getParameter()!=null)
						this.idCuentaDesde = ((BigDecimal)para[i].getParameter()).intValueExact();
					if (para[i].getParameter_To()!=null)
						this.idCuentaHasta = ((BigDecimal)para[i].getParameter_To()).intValueExact();
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

				if (name.equalsIgnoreCase("C_Currency_ID")){
					if (para[i].getParameter()!=null)
						this.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
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

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 27/08/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.showHelp("Eliminando datos anteriores");
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.
		this.showHelp("Cargando registros");
		this.loadMovimientos();
		
		// Calculo saldos 
		this.calculoSaldos();
		
		// Me aseguro de no mostrar registros con importes en cero
		this.deleteBasuraTemporal();
		
		this.showHelp("Iniciando Vista Previa");
		
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
			throw new AdempiereException(e);
		}
	}

	/**
	 * OpenUp.	
	 * Descripcion : Carga movimientos de cuentas en la tabla molde.
	 * @author  Gabriel Vila 
	 * Fecha : 22/09/2010
	 */
	private void loadMovimientos(){
		
		String insert = "", sql = "";
		String whereFiltros = "", camposImportes = "";

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, idreporte, ad_user_id, c_elementvalue_id, valorcuenta, nombrecuenta, fecreporte, datetrx, " + 
					" c_doctype_id, docname, documentno, idasiento, descasiento, observaciones, idmonedaasiento, idmonedacuenta, " +
					" imporigen, saldoinicial, debe, haber, saldoperiodo, saldoacumulado, c_bpartner_id, nombresocionegocio, idtabla, " +
					" nomtabla, aliascuenta, monedareporte, fact_acct_id, line_id) ";
			
			// Los filtros lo puedo tomar directo segun lo seleccionado por el usuario en el informe y proceso
			// o me pueden venir desde el navegador contable. Si es asi debo obtenerlos.
			if (this.uyAcctNavID <= 0){
				// Condicion de cuentas
				if (this.idCuentaDesde>0){
					MElementValue ev = new MElementValue(getCtx(), this.idCuentaDesde, null);
					//whereFiltros = " AND a.account_id>=" + this.idCuentaDesde;
					whereFiltros = " AND cta.value>='" + ev.getValue() + "' ";
				}
				if (this.idCuentaHasta>0){
					MElementValue ev = new MElementValue(getCtx(), this.idCuentaHasta, null);
					//whereFiltros += " AND a.account_id<=" + this.idCuentaHasta;
					whereFiltros += " AND cta.value<='" + ev.getValue() + "' ";
				}
				
			}
			else{
				// Obtengo los filtros desde el Navagador Contable
				MAcctNav nav = new MAcctNav(getCtx(), this.uyAcctNavID, get_TrxName());
				this.fechaDesde = nav.getStartDate();
				this.fechaHasta = nav.getEndDate();
				this.tipoMoneda = nav.getCurrencyType();
				this.conSaldoInicial = nav.isWithCurrentBalance();

				List<MAcctNavAccount> accts = nav.getAccounts();
				
				if(accts.size() > 0) whereFiltros += " AND cta.c_elementValue_id in (";
				
				for (int i = 0; i <= accts.size() -1; i++) {
					
					if (accts.size() > 0 && i < accts.size() -1) whereFiltros += accts.get(i).getC_ElementValue_ID() + ",";			
					else if (i == (accts.size() -1)) whereFiltros += accts.get(i).getC_ElementValue_ID();					
				}
				
				if(accts.size() > 0) whereFiltros += ")";
			}

			String tipoMonedaDesc = "";
			if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_NACIONAL)){
				camposImportes = "a.amtacctdr, a.amtacctcr, ";
				tipoMonedaDesc = "Moneda Nacional";
			}				 
			else if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_CUENTA)){
				camposImportes = "a.uy_amtnativedr, a.uy_amtnativecr, ";
				whereFiltros += " AND lower(tab.tablename)!='uy_exchangediffhdr' ";
				tipoMonedaDesc = "Moneda Cuenta Contable";
			}
			else if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_SELECCION)){
				camposImportes = " case when a.c_currency_id = " + this.cCurrencyID + " then a.amtsourcedr " +
						         " else a.amtsourcedr * coalesce(currencyrate(a.c_currency_id, " + this.cCurrencyID + ", a.dateacct, 0, a.ad_client_id, 0), 0) " +
						         " end as amtsourcedr, " +
						         " case when a.c_currency_id = " + this.cCurrencyID + " then a.amtsourcecr " +
						         " else a.amtsourcecr * coalesce(currencyrate(a.c_currency_id, " + this.cCurrencyID + ", a.dateacct, 0, a.ad_client_id, 0), 0) " +
						         " end as amtsourcecr, ";
				whereFiltros += " AND lower(tab.tablename)!='uy_exchangediffhdr' ";
				
				if (this.cCurrencyID > 0){
					MCurrency cur = new MCurrency(getCtx(), this.cCurrencyID, null);
					tipoMonedaDesc = cur.getCurSymbol();
				}
				else{
					tipoMonedaDesc = "Seleccion Moneda";	
				}
				
			}
			
			// Filtro por grupo de cliente
			if (this.cBPGroupID > 0){
				whereFiltros += " and bp.c_bp_group_id =" + this.cBPGroupID;
			}
			
			
			sql = "SELECT a.ad_client_id, a.ad_org_id, '" + this.idReporte + "'," + this.adUserID + ", a.account_id, cta.value, cta.name, current_date, a.dateacct, " +
				  " 0, '', '0', a.record_id, " +
				  "'','', a.c_currency_id, a.uy_accnat_currency_id, 0, 0, " + 
				  camposImportes +
				  " 0, 0, a.c_bpartner_id, coalesce(bp.name,'') as bpname, a.ad_table_id, tab.tablename, cta.alias, '" + 
				  tipoMonedaDesc + "', a.fact_acct_id, coalesce(a.line_id,0) as line_id " +
				  " FROM fact_acct a " + 
				  " INNER JOIN ad_table tab ON a.ad_table_id = tab.ad_table_id " +
				  " INNER JOIN c_elementvalue cta ON a.account_id = cta.c_elementvalue_id " +
				  " LEFT OUTER JOIN c_bpartner bp ON a.c_bpartner_id = bp.c_bpartner_id " +
				  " WHERE a.ad_client_id =" + this.adClientID +
				  " AND a.ad_org_id =" + this.adOrgID +
				  " AND a.dateacct between '" + this.fechaDesde + "' AND '" + this.fechaHasta + "' " +
				  whereFiltros; 
				  
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
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
				  " ORDER BY valorcuenta, datetrx, idasiento, idtabla, fact_acct_id ASC ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				rowCount++;
				this.showHelp("Linea " + rowCount + " de " + totalRowCount);
				
				// Obtengo cuenta de este registro
				String valorCuentaAux = rs.getString("valorcuenta");				
			
				
				// Si hay cambio de cuenta contable con respecto al registro anterior
				if (!valorCuentaAux.equalsIgnoreCase(valorcuenta)){
					
					// Si el usuario selecciona opcion de reporte con saldo inicial
					if (this.conSaldoInicial){
						// Obtengo saldo inicial de esta nueva cuenta contable
						saldoInicial = this.getSaldoInicial(rs.getInt("c_elementvalue_id"));
						// Actualizo saldo inicial para este nueva cuenta
						this.updateSaldoInicial(saldoInicial, rs.getInt("c_elementvalue_id"));
						//tomo en cuenta saldo inicial
						saldoAcumulado = saldoAcumulado.add(saldoInicial);
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
				InfoDocumentoBean infoDoc = this.getInfoDocumento(rs.getInt("idasiento"),rs.getString("nomtabla"), rs.getInt("line_id"));
				
				// Actualizo importes y datos de documento de este registro en la temporal
				this.updateDatosRegistro(rs.getInt("c_elementvalue_id"), rs.getTimestamp("datetrx"), rs.getInt("idasiento"), rs.getInt("idtabla"), 
										 saldoPeriodo, saldoAcumulado, infoDoc, rs.getString("nombresocionegocio"), rs.getInt("fact_acct_id"));
			}

		}
		catch (Exception e)
		{
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
	 * @param cElementValueID 
	 */
	private InfoDocumentoBean getInfoDocumento(int idRegistro, String nomTabla, int lineID) {

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
			else if (nomTabla.equalsIgnoreCase("GL_Journal")){
				sql = " SELECT DISTINCT a.c_doctype_id, a.documentno, doc.docbasetype, doc.printname, line.description as observa " +
					  	" FROM " + nomTabla + " a " +
					  	" INNER JOIN C_DocType doc ON a.c_doctype_id = doc.c_doctype_id " +
					  	" INNER JOIN GL_JournalLine line ON a.gl_journal_id = line.gl_journal_id " +
					  	" WHERE a." + nomTabla + "_ID =" + idRegistro +
					  	" AND line.GL_JournalLine_ID =" + lineID;
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
				value.DocBaseType = rs.getString("docbasetype");
				value.PrintName = rs.getString("printname");
				value.DocumentNo = rs.getString("documentno");
				value.Observaciones = rs.getString("observa");
			}			
			
			// Si es asiento diario y no pude obtener descripcion de la linea, la tomo del cabezal del asiento diario
			if ((value.Observaciones == null) || (value.Observaciones.equalsIgnoreCase(""))){
				if (nomTabla.equalsIgnoreCase("GL_Journal")){
					sql = " select description from gl_journal where gl_journal_id =?";
					value.Observaciones = DB.getSQLValueStringEx(null, sql, idRegistro);
				}
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

	/**
	 * OpenUp.	
	 * Descripcion : Actualiza saldo inicial para una determinada cuenta en tabla temporal.
	 * @param valor
	 * @param valorCuenta 
	 * @author  Gabriel Vila 
	 * Fecha : 03/10/2010
	 */
	private void updateSaldoInicial(BigDecimal valor, int idCuenta){

		String sql = "";
		
		try{
			sql = "UPDATE " + TABLA_MOLDE + 
				  " SET saldoinicial = " + valor +
				  " WHERE idreporte='" + this.idReporte + "'" +
				  " AND c_elementvalue_id =" + idCuenta;
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Actualiza datos de un determinado registro dado por cuenta-fecha-asiento-tabla en la tabla temporal.
	 * @param idCuenta
	 * @param fechaDoc
	 * @param idAsiento
	 * @param idTabla
	 * @param saldoPeriodo
	 * @param saldoAcumulado
	 * @param infoDoc
	 * @author  Gabriel Vila 
	 * Fecha : 03/10/2010
	 */
	private void updateDatosRegistro(int idCuenta, Timestamp fechaDoc, int idAsiento, int idTabla,
									 BigDecimal saldoPeriodo, BigDecimal saldoAcumulado, InfoDocumentoBean infoDoc, 
									 String nomSocioNeg, int factAcctID){
		
		String sql = "";
		
		try{

			sql = "UPDATE " + TABLA_MOLDE + 
				  " SET saldoperiodo = " + saldoPeriodo + ", " +
				  " saldoacumulado =" + saldoAcumulado + ", " +
				  " descasiento ='" + infoDoc.PrintName + "-" + infoDoc.DocumentNo + "-" + nomSocioNeg.replaceAll("'", "") + "', " +
				  " observaciones ='" + infoDoc.Observaciones + "', " +
				  " c_doctype_id =" + infoDoc.C_DocType_ID + ", " +
				  //" docbasetype ='" + infoDoc.DocBaseType + "', " +
				  " docname ='" + infoDoc.PrintName + "', " +
				  " documentno ='" + infoDoc.DocumentNo + "' " +
				  " WHERE idreporte='" + this.idReporte + "'" +
				  " AND c_elementvalue_id =" + idCuenta + 
				  " AND datetrx = '" + fechaDoc + "'" +
				  " AND idasiento =" + idAsiento + 
				  " AND idtabla =" + idTabla +
				  " AND fact_acct_id =" + factAcctID;
			
			System.out.println(sql);
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de asientos contables a determinado fecha para una determinada cuenta contable.
	 * @param idCuenta
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 */
	private BigDecimal getSaldoInicial(int idCuenta){
		
		String sql = "", camposImportes = "", whereFiltros = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);

		
		if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_NACIONAL)){
			camposImportes = "(a.amtacctdr - a.amtacctcr)";
		}
		else if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_CUENTA)){
			camposImportes = "(a.uy_amtnativedr - a.uy_amtnativecr)";
			whereFiltros = " AND a.ad_table_id NOT IN (" +
							" select ad_table_id from ad_table " +
							" where lower(tablename)='uy_exchangediffhdr')";
		}
		else if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_SELECCION)){
			camposImportes = "case when a.c_currency_id = " + this.cCurrencyID + " then a.amtsourcedr " +
					" else a.amtsourcedr * coalesce(currencyrate(a.c_currency_id, " + this.cCurrencyID + ", a.dateacct, 0, a.ad_client_id, 0), 0) " +
					" end - " +
					" case when a.c_currency_id = " + this.cCurrencyID + " then a.amtsourcecr " +
					" else a.amtsourcecr * coalesce(currencyrate(a.c_currency_id, " + this.cCurrencyID + ", a.dateacct, 0, a.ad_client_id, 0), 0) end ";
			whereFiltros = " AND a.ad_table_id NOT IN (" +
							" select ad_table_id from ad_table " +
							" where lower(tablename)='uy_exchangediffhdr')";
		}
		
		try{
			sql = "SELECT COALESCE(SUM(" + camposImportes + "),0) as saldo " +
				  " FROM fact_acct a " +
				  " WHERE a.ad_client_id =" + this.adClientID +
				  " AND a.ad_org_id =" + this.adOrgID +
				  " AND a.account_id =" + idCuenta +
				  " AND a.dateacct <? " +
				  whereFiltros;

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setTimestamp(1, this.fechaDesde);			
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value = rs.getBigDecimal("saldo");
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e); 
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;		
	}

	/***
	 * Despliega ayuda en waiting window.
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 18/09/2012
	 * @see
	 * @param message
	 */
	private void showHelp(String message){
		
		if (this.getProcessInfo().getWaiting() != null)
			this.getProcessInfo().getWaiting().setText(message);
		
	}
	
}
