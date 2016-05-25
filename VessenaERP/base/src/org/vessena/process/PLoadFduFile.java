package org.openup.process;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MSequence;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.openup.beans.UY_FduCod;
import org.openup.model.MFduCod;
import org.openup.model.MFduConnectionData;
import org.openup.model.MFduControlSettings;
import org.openup.model.MFduInvoiceType;
import org.openup.model.MFduLoad;
import org.openup.model.MFduLoadLine;
import org.openup.model.MFduPeriodInvoiced;
import org.openup.model.MFduProcessDate;
import org.openup.model.MFduStore;
import org.openup.model.X_UY_FduCod;
import org.openup.util.OpenUpUtils;




/**
 * OpenUp.
 * PLoadFduFile
 * Descripcion : Obtención de datos de base de datos SQL SERVER de Italcred, para realizar asientos contables.
 * @author Guillermo Brust
 * ISSUE #60 - Version 2.5.1
 * Fecha : 18/10/2012
 */

public class PLoadFduFile extends SvrProcess {	
	
	
	private static String TABLA_FDCOD = "UY_FduCod";
	
	private static String TABLA_MOLDE_CC108 = "UY_Molde_FduLoad_CC108";
	private static String TABLA_MOLDE_CC120 = "UY_Molde_FduLoad_CC120";
	private static String TABLA_MOLDE_CL750 = "UY_Molde_FduLoad_CL750";
	private static String TABLA_MOLDE_CL500 = "UY_Molde_FduLoad_CL500";
	
	private static String TABLA_CL750 = "Q_FDU_CL750D_DETALLE_40_CON_GRUPO_AFINIDAD";		
	private static String TABLA_TOTALIZADORES = "T_FDU_CC108D_DETALLE_T";
	private static String TABLA_CONSUMOS = "T_FDU_CC108D_DETALLE_C";
	private static String TABLA_AJUSTES = "T_FDU_CC108D_DETALLE_A";	
	private static String TABLA_CL500 = "q_Consumos_Diarios";	
	
	private int user;	
	private Timestamp fechaInicio;
	private Timestamp fechaFin;	
	private int uyFduFileID;	
	
	private MFduLoad fduLoad = null;
	private boolean isManual = false;
	//private MAcctSchema schema = null;
	
	private String whereFechas = "";
	private String campoFecha = "";
	private StringBuilder erroresConceptos = new StringBuilder();
	private StringBuilder erroresTotalesImportes = new StringBuilder();
	
	private HashMap<Integer, Timestamp> hash_codigosTot = new HashMap<Integer, Timestamp>();
	
	public PLoadFduFile() {
	}

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("StartDate")){
					this.fechaInicio = (Timestamp)para[i].getParameter();
					this.fechaFin = (Timestamp)para[i].getParameter_To();		
				}
				if (name.equalsIgnoreCase("UY_FduFile_ID")){
					this.uyFduFileID = ((BigDecimal)para[i].getParameter()).intValueExact();				
				}
				if (name.equalsIgnoreCase("AD_User_ID")){
					this.user = ((BigDecimal)para[i].getParameter()).intValueExact();				
				}
				if (name.equalsIgnoreCase("isManual")){
					this.isManual = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				}
			}
		}
		
		if(this.uyFduFileID == 1000008) this.campoFecha = "FchCie";
		if(this.uyFduFileID == 1000011) this.campoFecha = "FCHPREMES";
		if(this.uyFduFileID == 1000012) this.campoFecha = "\"Fecha Clearing\"";			
	}
	

	
	
	private void getFechasAProcesar(){		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		SimpleDateFormat dfPostgre = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		MFduProcessDate date = new MFduProcessDate(getCtx(), this.uyFduFileID, get_TrxName());
		
		String tabla = "";
		
		if(this.uyFduFileID == 1000008) tabla = TABLA_TOTALIZADORES;
		else if(this.uyFduFileID == 1000011) tabla = TABLA_CL750;
		else if(this.uyFduFileID == 1000012) tabla = TABLA_CL500;
		
		Timestamp inicio = null;
		if(this.fechaInicio == null){
			inicio = new Timestamp(System.currentTimeMillis());
			inicio = Timestamp.valueOf(dfPostgre.format(inicio));
		}else inicio = this.fechaInicio;
		
		Timestamp fin = null;
		if(this.fechaInicio == null){
			fin = new Timestamp(System.currentTimeMillis());
			fin = Timestamp.valueOf(dfPostgre.format(fin));
		}else fin = this.fechaFin;
		
		String sql = "SELECT distinct  " + this.campoFecha + " as fecha" +
				     " FROM " + tabla + 
				     " WHERE " + this.campoFecha + " between '" + df.format(inicio) + "' AND '" + df.format(fin) + "'"; //fechas en el rango de fechas
		
		//se obtienen las fechas procesadas en el rango de fecha ingresado
		List<MFduProcessDate> datesProcessed = date.getProcessedDates(this.uyFduFileID, inicio, fin);		
		if (datesProcessed.size() > 0){
			sql += " AND " + this.campoFecha + " not in (";
			for (MFduProcessDate fdudateProcessed: datesProcessed){
				sql += "'" + df.format(fdudateProcessed.getDateTrx()) + "',";
			}
			sql = sql.substring(0, (sql.length())-1);
			sql += ")";			
		}
		
		//se obtienen las fechas no procesadas
		List<MFduProcessDate> dateNotProcessed = date.getNotProcessedDates(this.uyFduFileID);	
		if (dateNotProcessed.size() > 0){
			sql += " OR " + this.campoFecha + " in (";
			for (MFduProcessDate fdudateNotProcessed: dateNotProcessed){
				sql += "'" + df.format(fdudateNotProcessed.getDateTrx()) + "',";
			}
			sql = sql.substring(0, (sql.length())-1);
			sql += ")";			
		}
		
		
		Connection con = null;
		ResultSet rs = null;
		
		try {			
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);			
			rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				this.whereFechas = " AND " + this.campoFecha + " in (";
				rs.beforeFirst();
			}		

			while (rs.next()) {	
				this.setFechasProcesadas(rs.getTimestamp("fecha"));
				this.whereFechas += "'" + df.format(rs.getTimestamp("fecha")) + "',";				
			}
			
			rs.beforeFirst();
			if(rs.next()){
				this.whereFechas = this.whereFechas.substring(0, (whereFechas.length())-1);
				this.whereFechas += ")";
			}			

			rs.close();
			con.close();			

		} catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
	}
	
	private void setFechasProcesadas(Timestamp fecha){					
		
		MFduProcessDate pr = MFduProcessDate.forDateAndFileID(getCtx(), fecha, this.uyFduFileID, null);
		
		if(pr == null){
			pr = new MFduProcessDate(getCtx(), 0, get_TrxName());
			pr.setUY_FduFile_ID(this.uyFduFileID);
			
			//OpenUp. Guillermo Brust. 19/06/2013. ISSUE #
			//Se setea la compañia, ya que como no se seteaba, se guardaba con 0, y de esta forma no se lograba modificar el registro
			//cuando se queria descliquear la fecha procesada. Haciendo este cambio se podra descliquear.
			
			pr.setAD_Client_ID(OpenUpUtils.getDefaultClient().get_ID());
							
			//Fin OpenUp.
			
			pr.setDateTrx(fecha);
			pr.setProcessed(false);	
			pr.setIsSelected(false);			
		}
		pr.saveEx();		
	}

	@Override
	protected String doIt() throws Exception {	
		
		if(this.uyFduFileID == 1000010){
			
				Trx m_trx = Trx.get(Trx.createTrxName("SvrProcess"), true);
				PLoadFduTransaction tran = new PLoadFduTransaction();
				boolean verdadero = tran.startProcess(getCtx(), getProcessInfo(), m_trx);
	
				m_trx.commit();
				
				if(!verdadero){
					throw new AdempiereException(getProcessInfo().getSummary());
					
				}
				
		}
		else if(this.uyFduFileID == 1000013){
			
			Trx m_trx = Trx.get(Trx.createTrxName("SvrProcess"), true);
			PLoadFduCaja caja = new PLoadFduCaja();
			boolean verdadero = caja.startProcess(getCtx(), getProcessInfo(), m_trx);
			m_trx.commit();
			
			if(!verdadero){
				throw new AdempiereException(getProcessInfo().getSummary());
				
			}
	
		}
		else{
			this.deleteInstanciasViejasReporte();
			
			this.setCombinations();
			
			this.getFechasAProcesar();
			
			if(this.whereFechas.equals("")) throw new AdempiereException("No existen datos para las fechas ingresadas");
			
			this.execute();
			
			this.sendNotification();
		}
		
		return "OK";
	}
	


	private void deleteInstanciasViejasReporte(){
		
		String tabla = "";
		
		//Segun que archivo fdu sea, es la tabla molde que se va a limpiar		
		if(this.uyFduFileID == 1000008) tabla = TABLA_MOLDE_CC108;			
		else if(this.uyFduFileID == 1000010) tabla = TABLA_MOLDE_CC120;
		else if(this.uyFduFileID == 1000011) tabla = TABLA_MOLDE_CL750;
		else if(this.uyFduFileID == 1000012 || this.uyFduFileID == 1000014) tabla = TABLA_MOLDE_CL500;		
		
		String sql = "";
		try{

			this.showHelp("Elimando datos anteriores...");
			sql = "TRUNCATE " + tabla;
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw new AdempiereException (e);		
		}
	}
	
	
	private Connection getConnection() throws Exception {
		
		Connection retorno = null;

		String connectString = ""; //"jdbc:sqlserver://192.168.150.175\\FP01;databaseName=FDU;user=adempiere;password=adempiere1144";
		//String user = "adempiere";
		//String password = "adempiere1144";

		try {

			MFduConnectionData conn = MFduConnectionData.forFduFileID(getCtx(), this.uyFduFileID, null);
			
			if(conn != null){
				
				connectString = "jdbc:sqlserver://" + conn.getserver_ip() + "\\" + conn.getServer() + 
								";databaseName=" + conn.getdatabase_name() + ";user=" + conn.getuser_db() + 
								";password=" + conn.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, conn.getuser_db(), conn.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}
	
	private Connection getConnectionForOtherFile(int fduFileID) throws Exception {
		
		Connection retorno = null;

		String connectString = ""; //"jdbc:sqlserver://192.168.150.175\\FP01;databaseName=FDU;user=adempiere;password=adempiere1144";
		//String user = "adempiere";
		//String password = "adempiere1144";

		try {

			MFduConnectionData conn = MFduConnectionData.forFduFileID(getCtx(), fduFileID, null);
			
			if(conn != null){
				
				connectString = "jdbc:sqlserver://" + conn.getserver_ip() + "\\" + conn.getServer() + 
								";databaseName=" + conn.getdatabase_name() + ";user=" + conn.getuser_db() + 
								";password=" + conn.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, conn.getuser_db(), conn.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}
	
	public void execute(){
		
		//OpenUp. Guillermo Brust. 27/05/2013. ISSUE# 
		//Validar que los conceptos que se encuentran en los archivos FDU se encuentren parametrizados en Adempiere.
		
		if(!this.validarConceptos()) {
			this.guardarErroresParametrizacion();
			throw new AdempiereException("No se han parametrizado algunos elementos, por favor verificar ventana de errores");
		}		

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			// Obtengo moneda nacional del esquema contable
			//MClient client = new MClient(Env.getCtx(), getAD_Client_ID(), null);
			//schema = client.getAcctSchema();

			MClient client = this.getDefaultClient();
			
			// Creo y seteo cabezal de carga de fdu
			this.fduLoad = new MFduLoad(getCtx(), 0, null);
			this.fduLoad.setAD_Client_ID(client.getAD_Client_ID());
			this.fduLoad.setAD_Org_ID(1000007);
			this.fduLoad.setDefaultDocTypeID();
			this.fduLoad.setDateTrx(TimeUtil.trunc(new Timestamp(System.currentTimeMillis()),TimeUtil.TRUNC_DAY));
			this.fduLoad.setC_Period_ID(MPeriod.getC_Period_ID(getCtx(), this.fduLoad.getDateTrx(), this.fduLoad.getAD_Org_ID()));
			this.fduLoad.setUY_FduFile_ID(this.uyFduFileID);
			this.fduLoad.setIsManual(this.isManual);
			this.fduLoad.setProcessingDate(new Timestamp(System.currentTimeMillis()));
			this.fduLoad.saveEx();
				
			//Segun sea el archivo fdu a procesar, es lo que se obtiene de la tabla molde correspondiente
			if(this.uyFduFileID == 1000008){
				
				// Se obtienen los Totalizadores que van a ser la guia para recorrer
				// los consumos y los ajustes
				sql = "SELECT * FROM " + TABLA_MOLDE_CC108 + " m" +
					      " INNER JOIN " + TABLA_FDCOD + " c ON m.UY_FduCod_Id = c.UY_FduCod_Id" +
						  " WHERE c.IsTotalizador='Y' AND c.IsActive='Y'";						
			
			}else if(this.uyFduFileID == 1000011){
				
				sql = "SELECT * FROM " + TABLA_MOLDE_CL750 + " m" +
					      " INNER JOIN " + TABLA_FDCOD + " c ON m.UY_FduCod_Id = c.UY_FduCod_Id" +
						  " WHERE c.IsActive='Y'";
			
			}else if(this.uyFduFileID == 1000012 || this.uyFduFileID == 1000014){
				
				//1000012 ---> Servicios UA, SE, Asucasmu
				//1000014 ---> Tecnixa
				
				sql = "SELECT * FROM " + TABLA_MOLDE_CL500 + " m";
			
			}

			this.showHelp("Leyendo combinaciones...");
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			while (rs.next()) {
				this.showHelp("Combinacion " + rowCount++ + " de " + totalRowCount);
				
				//Segun sea el archivo fdu, se ejecutara un metodo diferente de obtencion de los datos del SQLServer, para poder guardar las lineas en la UY_Fdu_LoadLine
				if(this.uyFduFileID == 1000008) this.executeFduFile_cc108(rs);				
				else if(this.uyFduFileID == 1000011) this.executeFduFile_cl750(rs);
				else if(this.uyFduFileID == 1000012) this.executeFduFile_cl500(rs);	
				//else if(this.uyFduFileID == 1000014) this.executeFduFile_cl600(rs);	
				
			}	
			
			//OpenUp. Guillermo Brust. 24/06/2013. ISSUE #1052
			//Se agrega control de totales obtenidos por archivo, si se obtiene los importes correctos se realizan los asientos.
			if(validarTotalesImporteEnCargas()){
				
				//----------Generación de Asientos----------------------
				this.showHelp("Generando Asientos Diarios...");
				
				//archivo 108
				if(this.uyFduFileID == 1000008){
					
					//Genero los asientos comunes
					this.fduLoad.generateJournals(this.get_TrxName());
					
					// Casos especiales del 108
					this.fduLoad.generateSpecialCases108();
				
				//archivo 750 (Arancel Diario)
				}else if(this.uyFduFileID == 1000011){
					
					//Genero los asientos comunes
					this.fduLoad.generateJournals(this.get_TrxName());
					
					//----------Generación de Factura----------------------
					this.showHelp("Generando Factura...");
					
					//Si esta todo el mes anterior cargado se debe generar una factura	
					if(this.fduLoad.mesAnteriorCargadoAndNoFacturado()) this.generateInvoiceAutomatic750();	
				}
				
				//Servicios UA, Socio Espectacular, Asucasmu 
				else if(this.uyFduFileID == 1000012){					
					
					//Genero los asientos de los servicios
					this.fduLoad.generateFirstJournalsForStores(this.get_TrxName());
					this.fduLoad.generateSecondJournalsForStores(this.get_TrxName());
					
					//----------Generación de Facturas----------------------
					this.showHelp("Generando Facturas...");
					
					if(this.fduLoad.mesAnteriorCargadoAndNoFacturado()){						
						this.generateFirstInvoiceAutomatic500();
						this.generateSecondInvoiceAutomatic500();	
					}				
				}
			
			    //Tecnixa
				else if(this.uyFduFileID == 1000014){
					
					//Genero los asientos
					this.fduLoad.generateJournalsForTecnixa(this.get_TrxName());				
				}				
				
			}else{				
				this.guardarErroresTotales();
				throw new AdempiereException("Los totales obtenidos no son iguales a los que existen realmente en cada archivo, por favor verificar ventana de errores");
			}
			
			//Fin OpenUp.				
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}
	
	/**
	 * 27/05/2013. Guillermo Brust OpenUp 
	 *Se validan que todos los conceptos que se encuentran en los archivos FDU se encuentren parametrizados en Adempiere.
	 *Esto es para cada tipo de archivo que se procesa, ya que se obtienen los datos de diferentes origenes
	 */
	private boolean validarConceptos(){
		
		//Por ahora esta solo implementado para los archivos 108 y 750
		if(this.uyFduFileID == 1000008 || this.uyFduFileID == 1000011){
			
			return this.validarProductoAfinidad() & this.validarCodigosFdu() & validarSucursales();
		}
		else return true;
	}
	
	
	/**
	 * 24/06/2013. Guillermo Brust OpenUp 
	 *Metodos para guardar errores en carga datos para la realizacion de los asientos.
	 */
	
	private void guardarErroresParametrizacion(){
		
		//Nota: se instancia con transaccion null para que siempre queden guardados los errores en esta tabla.
		MFduControlSettings tablaErrores = new MFduControlSettings(getCtx(), 0, null);
		
		tablaErrores.setAD_Client_ID(OpenUpUtils.getDefaultClient().get_ID());
		tablaErrores.setUY_FduFile_ID(this.uyFduFileID);
		tablaErrores.setDateTrx(new Timestamp(System.currentTimeMillis()));
		tablaErrores.setMessage(this.erroresConceptos.toString());
		
		tablaErrores.saveEx();
	}
	
	private void guardarErroresTotales(){
		
		//Nota: se instancia con transaccion null para que siempre queden guardados los errores en esta tabla.
		MFduControlSettings tablaErrores = new MFduControlSettings(getCtx(), 0, null);
		
		tablaErrores.setAD_Client_ID(OpenUpUtils.getDefaultClient().get_ID());
		tablaErrores.setUY_FduFile_ID(this.uyFduFileID);
		tablaErrores.setDateTrx(new Timestamp(System.currentTimeMillis()));
		tablaErrores.setMessage(this.erroresTotalesImportes.toString());
		
		tablaErrores.saveEx();
	}
	
	private boolean validarProductoAfinidad(){
		
		boolean retorno = true;
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			this.showHelp("Control de Productos y Afinidades parametrizadas...");

			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = this.getConnectionForOtherFile(1000015);
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			if(this.uyFduFileID == 1000011){
				sql = "select distinct CODPRO as producto, CAST(SUBSTRING(CAST(GRUPO_AFINIDAD AS VARCHAR), 0, 5) AS INT) as afinidad" +
					  " from FDU.dbo.Q_FDU_CL750D_DETALLE_40_CON_GRUPO_AFINIDAD" +
					  "	where CODPRO not in(" +
					  " 					  select distinct producto COLLATE MODERN_SPANISH_CI_AS" +
					  "     				  from op_control.VUY_Fdu_ProdAff" +
					  " 					  where prodfileid = " +	this.uyFduFileID + 			
					  "					     )" +
					  "	or (CAST(SUBSTRING(CAST(GRUPO_AFINIDAD AS VARCHAR), 0, 5) AS INT) not in (" +
					  "																				select distinct CAST(afinidad COLLATE MODERN_SPANISH_CI_AS AS INT)" +
					  "																				from op_control.VUY_Fdu_ProdAff" +
					  "																				where afffileid = " +	this.uyFduFileID + 
					  "																				and producto COLLATE MODERN_SPANISH_CI_AS = CODPRO" +
					  "																			   ))" +
					  this.whereFechas;

			}else{
				if(this.uyFduFileID == 1000008){
					
					sql = "select distinct CodProMar as producto, CodAFF as afinidad, 'Totalizadores' as tabla" +
						  " from fdu.dbo.T_FDU_CC108D_DETALLE_T" +
						  " where CodProMar not in(" +
						  " 						select distinct producto COLLATE MODERN_SPANISH_CI_AS" +
						  " 						from op_control.VUY_Fdu_ProdAff" +
						  " 						where afffileid = " + this.uyFduFileID +					
						  " 						)" +
						  " or (CodAFF not in (" +
						  " 					select distinct afinidad COLLATE MODERN_SPANISH_CI_AS" +
						  " 					from op_control.VUY_Fdu_ProdAff" +
						  " 					where afffileid = " + this.uyFduFileID +	
						  " 					and producto COLLATE MODERN_SPANISH_CI_AS = CodProMar" +
						  "						))" +
						  this.whereFechas + 
						  " union" +
						  " select distinct CodProMar, CodAFF, 'Ajustes' as tabla" +
						  " from fdu.dbo.T_FDU_CC108D_DETALLE_A" +
						  " where CodProMar not in(" +
						  " 						select distinct producto COLLATE MODERN_SPANISH_CI_AS" +
						  " 						from op_control.VUY_Fdu_ProdAff" +
						  " 						where afffileid =" + this.uyFduFileID +				
						  "							)" +
						  " or (CodAFF not in (" +
						  "						select distinct afinidad COLLATE MODERN_SPANISH_CI_AS " +
						  " 					from op_control.VUY_Fdu_ProdAff" +
						  "						where afffileid = " + this.uyFduFileID +
						  "						and producto COLLATE MODERN_SPANISH_CI_AS = CodProMar" +
						  "						))" +
						  this.whereFechas +
						  " union" +
						  " select distinct CodProMar, CodAFF, 'Consumos' as tabla" +
						  " from fdu.dbo.T_FDU_CC108D_DETALLE_C" +
						  " where CodProMar not in(" +
						  " 					   select distinct producto COLLATE MODERN_SPANISH_CI_AS" +
						  " 					   from op_control.VUY_Fdu_ProdAff" +
						  " 					   where afffileid = " + this.uyFduFileID +					
						  "						  )" +
						  " or (CodAFF not in (" +
						  " 				   select distinct afinidad COLLATE MODERN_SPANISH_CI_AS" +
						  " 				   from op_control.VUY_Fdu_ProdAff" +
						  "					   where afffileid = " + this.uyFduFileID +
						  "					   and producto COLLATE MODERN_SPANISH_CI_AS = CodProMar" +
						  "					  ))" +
						  this.whereFechas;
				}				
			}			

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				
				this.erroresConceptos.append(" Producto: ");
				this.erroresConceptos.append(rs.getString("producto"));
				this.erroresConceptos.append(" - ");
				this.erroresConceptos.append(" Afinidad: ");
				this.erroresConceptos.append(rs.getString("afinidad"));
			
				if(this.uyFduFileID == 1000008){
					this.erroresConceptos.append(" - En tabla ");
					this.erroresConceptos.append(rs.getString("tabla"));
				}
				
				this.erroresConceptos.append("  ||  ");				
				
				retorno = false;				
			}

			rs.close();
			con.close();		
			

		} catch (Exception e) {

			throw new AdempiereException(e);

		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
		return retorno;
	}
	
	private boolean validarCodigosFdu(){
		
		boolean retorno = true;
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		try {

			this.showHelp("Control de Codigos parametrizados...");	
			
			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = this.getConnectionForOtherFile(1000015);
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			if(this.uyFduFileID == 1000011){
				
				sql = "select distinct CODCON" +
					  " from FDU.dbo.Q_FDU_CL750D_DETALLE_40_CON_GRUPO_AFINIDAD" + 
					  " where CODCON not in(" + 
					  " 					select distinct value COLLATE MODERN_SPANISH_CI_AS" + 
					  " 					from op_control.VUY_Fdu_CodigosFdu" +
					  "						where uy_fdufile_id = " + this.uyFduFileID +	
					  "					   )" +
					  this.whereFechas;

			}else{
				if(this.uyFduFileID == 1000008){
					
					sql = "select distinct ConDet as codigo, 'Totalizadores' as tabla" +
						  " from fdu.dbo.T_FDU_CC108D_DETALLE_T" +
						  " where not exists(" +
						  "					 select value" +
						  " 				 from op_control.VUY_Fdu_CodigosFdu" +
						  " 				 where tabla = 'Tabla Totalizadores'" +							
						  "					 and uy_fdufile_id = " + this.uyFduFileID +
						  "					 and value COLLATE MODERN_SPANISH_CI_AS = ConDet" +
						  "					)" +
						  this.whereFechas +
						  " union" +
						  " select distinct CanPesConAju as codigo, 'Ajustes' as tabla" +
						  " from fdu.dbo.T_FDU_CC108D_DETALLE_A" +
						  " where not exists(" +
						  " 				select parentvalue, value" +
						  " 				from op_control.VUY_Fdu_CodigosFdu" +
						  "					where tabla = 'Tabla Ajustes'" +
						  "					and uy_fdufile_id = " + this.uyFduFileID +
						  " 				and value COLLATE MODERN_SPANISH_CI_AS = CanPesConAju" +
						  " 				and parentvalue COLLATE MODERN_SPANISH_CI_AS = Con" +							 
						  "				   )" +
						  this.whereFechas +
						  " union" +
						  " select distinct conCC as codigo, 'Consumos' as tabla" +
						  " from fdu.dbo.T_FDU_CC108D_DETALLE_C" +
						  " where not exists(" +
						  "					 select value" +
						  " 				 from op_control.VUY_Fdu_CodigosFdu" +
						  "					 where tabla = 'Tabla Consumos'" +						 
						  "					 and uy_fdufile_id = " + this.uyFduFileID +
						  " 				 and value COLLATE MODERN_SPANISH_CI_AS = conCC" +
						  " 				)" +
						  this.whereFechas;
				}				
			}			

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				
				this.erroresConceptos.append(" Codigo: ");
				this.erroresConceptos.append(rs.getString("codigo"));
				
				if(this.uyFduFileID == 1000008){
					this.erroresConceptos.append(" en tabla ");
					this.erroresConceptos.append(rs.getString("tabla"));
				}				
				
				this.erroresConceptos.append("  ||  ");		
							
				retorno = false;				
			}

			rs.close();
			con.close();		
			

		} catch (Exception e) {

			throw new AdempiereException(e);

		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
		return retorno;
	}
		
	private boolean validarSucursales(){
		
		boolean retorno = true;
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			this.showHelp("Control de Sucursales parametrizadas...");

			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = this.getConnectionForOtherFile(1000015);
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			if(this.uyFduFileID == 1000011){
				
				sql = "select distinct SUC as sucursal" +
					  " from FDU.dbo.Q_FDU_CL750D_DETALLE_40_CON_GRUPO_AFINIDAD" + 
					  "	where SUC not in(" + 
					  "					 select distinct value COLLATE MODERN_SPANISH_CI_AS" + 
					  "					 from op_control.VUY_Fdu_Sucursal" +				  	
					  "					)" + 
					  this.whereFechas;				

			}else{
				if(this.uyFduFileID == 1000008){
					
					sql = "select distinct Suc as sucursal, 'Totalizadores' as tabla" +
						  " from fdu.dbo.T_FDU_CC108D_DETALLE_T" +
						  " where Suc not in(" +
						  " 				 select distinct value COLLATE MODERN_SPANISH_CI_AS" + 
						  "					 from op_control.VUY_Fdu_Sucursal" +					
						  "					)" +
						  this.whereFechas +
						  " union" +
						  " select Suc as sucursal, 'Ajustes' as tabla" +
						  " from fdu.dbo.T_FDU_CC108D_DETALLE_A" +
						  " where Suc not in(" +
						  " 				 select distinct value COLLATE MODERN_SPANISH_CI_AS" + 
						  "					 from op_control.VUY_Fdu_Sucursal" +					
						  "					)" +
						  this.whereFechas +
						  " union" +
						  " select distinct Suc as sucursal, 'Consumos' as tabla" +
						  " from fdu.dbo.T_FDU_CC108D_DETALLE_C" +
						  " where Suc not in(" +
						  " 				 select distinct value COLLATE MODERN_SPANISH_CI_AS" + 
						  " 				 from op_control.VUY_Fdu_Sucursal" +					
						  "					)" +
						  this.whereFechas;
					
				}				
			}			

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				
				this.erroresConceptos.append(" Sucursal: ");
				this.erroresConceptos.append(rs.getString("sucursal"));
				
				if(this.uyFduFileID == 1000008){
					this.erroresConceptos.append(" en tabla ");
					this.erroresConceptos.append(rs.getString("tabla"));
				}				
				
				this.erroresConceptos.append("  ||  ");		
							
				retorno = false;						
			}

			rs.close();
			con.close();		
			

		} catch (Exception e) {

			throw new AdempiereException(e);

		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
		return retorno;
	}
		
	/**
	 * OpenUp. Guillermo Brust. 21/06/2013. ISSUE #1052
	 * Se controla los totales obtenidos de las cargas en la loadline, y se concilia con los totales de cada archivo Fdu.
	 * 
	 */
	private boolean validarTotalesImporteEnCargas(){
		boolean retorno = true;
		if(this.uyFduFileID == 1000008){
			//Se valida en pesos y en dolares
			retorno = this.validarTotalesImporte(142) & this.validarTotalesImporte(100);
		}
		return retorno;
	}
	
	
	
	
	private boolean validarTotalesImporte(int currencyID){
		
		boolean retorno = true;
		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		try {

			//esta es la conexion que me permite usar el esquema op_control, el cual contine la vista que utilizo mas abajo
			con = this.getConnectionForOtherFile(1000015);
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			if(this.uyFduFileID == 1000011){			
				//TODO: Realizar control de totales para archivo 750

			}else{
				if(this.uyFduFileID == 1000008){
					
					sql = "WITH temp as(" +
						  //AJUSTES
						  " SELECT a.FchCie, a.CanPesConAju, a.CodAFF, a.CodProMar, a.Suc, sum(a.impPes) as importe, 'Ajustes' as tabla" +
						  " FROM  fdu.dbo.T_FDU_CC108D_DETALLE_A a" +
						  " WHERE a.CodMonAju = " + (currencyID == 142 ? "'858'" : "'840'") +
						  " and a.CanPesConAju in (select distinct value COLLATE MODERN_SPANISH_CI_AS from op_control.VUY_Fdu_CodigosFdu where tabla = 'Tabla Ajustes' and contabiliza = 'Y')" +
						  this.whereFechas.replaceAll("FchCie", "a.FchCie") +
						  " group by a.FchCie, a.CanPesConAju, a.CodAFF, a.CodProMar, a.Suc" +
						  " union" + 
						  //CONSUMOS
						  " SELECT c.FchCie, c.conCC, c.CodAFF, c.CodProMar, c.Suc, sum(c.ImpCon) as importe, 'Consumos' as tabla" +
						  " FROM  fdu.dbo.T_FDU_CC108D_DETALLE_C c" +
						  " WHERE c.Mon = " + (currencyID == 142 ? "'858'" : "'840'") +
						  " and c.conCC in (select distinct value COLLATE MODERN_SPANISH_CI_AS from op_control.VUY_Fdu_CodigosFdu where tabla = 'Tabla Consumos' and contabiliza = 'Y')" +
						  this.whereFechas.replaceAll("FchCie", "c.FchCie") +
						  " group by c.FchCie, c.conCC, c.CodAFF, c.CodProMar, c.Suc" +
						  " union" +
						  //TOTALIZADORES
						  " SELECT t.FchCie, t.conDet, t.CodAFF, t.CodProMar, t.Suc, " + (currencyID == 142 ? "sum(t.impPes)" : "sum(t.impDol)") + " as importe, 'Totalizadores' as tabla" +
						  " FROM  fdu.dbo.T_FDU_CC108D_DETALLE_T t" +
						  " WHERE t.ConDet in (select value COLLATE MODERN_SPANISH_CI_AS from op_control.VUY_Fdu_CodigosFdu where tabla = 'Tabla Totalizadores' and calculatetype = 'TT' and contabiliza = 'Y')" +
						  this.whereFechas.replaceAll("FchCie", "t.FchCie") +
						  " group by t.FchCie, t.conDet, t.CodAFF, t.CodProMar, t.Suc" +
						  ")" +
						  
						  " select t.FchCie, SUM(ABS(t.importe)) as importe, t.tabla" + 
						  " from temp t where t.tabla = 'Ajustes'" +
						  " group by t.FchCie, t.tabla" +
						  " union" +
						  " select t.FchCie, SUM(ABS(t.importe)) as importe, t.tabla" + 
						  " from temp t where t.tabla = 'Consumos'" +
						  " group by t.FchCie, t.tabla" +
						  " union" +
						  " select t.FchCie, SUM(ABS(t.importe)) as importe, t.tabla" + 
						  " from temp t where t.tabla = 'Totalizadores'" +
						  " group by t.FchCie, t.tabla";			
				}				
			}			

			rs = stmt.executeQuery(sql);			
			
			while (rs.next()) {
				
				Timestamp fechaCierre = rs.getTimestamp("FchCie"); 
				BigDecimal importe = rs.getBigDecimal("importe");
				String tabla = rs.getString("tabla");
								
				String sqlPostgre = "";
				ResultSet rsPostgre = null;
				PreparedStatement pstmt = null;

				try {	
					
					if(tabla.equals("Ajustes")){
						
						sqlPostgre = " select coalesce(sum(ABS(l.amtsourcedr)), 0) as importe" +
									 " from uy_fduloadline l" +
									 " inner join uy_fducod cod on l.uy_fducod_id = cod.uy_fducod_id" +
									 " where l.uy_fduload_id = " + this.fduLoad.get_ID() + 
									 " and l.dateacct = '" + fechaCierre + "'" +
									 " and cod.value in (select distinct value from vuy_fdu_codigosfdu where tabla = 'Tabla Ajustes' and contabiliza = 'Y')" +
									 " and l.c_currency_id = " + currencyID;
					}else{
						if(tabla.equals("Consumos")){
							
							sqlPostgre = " select coalesce(sum(ABS(l.amtsourcedr)), 0) as importe" +
										 " from uy_fduloadline l" +
										 " inner join uy_fducod cod on l.uy_fducod_id = cod.uy_fducod_id" +
										 " where l.uy_fduload_id = " + this.fduLoad.get_ID() + 
										 " and l.dateacct = '" + fechaCierre + "'" +
										 " and cod.value in (select distinct value from vuy_fdu_codigosfdu where tabla = 'Tabla Consumos' and contabiliza = 'Y')" +
										 " and l.c_currency_id = " + currencyID;
						}else{
							if(tabla.equals("Totalizadores")){
								
								sqlPostgre = " select coalesce(sum(ABS(l.amtsourcedr)), 0) as importe" +
											 " from uy_fduloadline l" +
											 " inner join uy_fducod cod on l.uy_fducod_id = cod.uy_fducod_id" +
											 " where l.uy_fduload_id = " + this.fduLoad.get_ID() + 
											 " and l.dateacct = '" + fechaCierre + "'" +
											 " and cod.value in (select distinct value from vuy_fdu_codigosfdu where tabla = 'Tabla Totalizadores' and contabiliza = 'Y')" +
											 " and l.c_currency_id = " + currencyID;
								}
						}						
					}				

					this.showHelp("Control de importes totales obtenidos...");
					
					pstmt = DB.prepareStatement(sqlPostgre, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
					rsPostgre = pstmt.executeQuery();

					rsPostgre.last();
					int totalRowCount = rsPostgre.getRow(), rowCount = 0;
					rsPostgre.beforeFirst();
					
					while (rsPostgre.next()) {
						this.showHelp("Control " + rowCount++ + " de " + totalRowCount);
						
						SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
						
						if(rsPostgre.getBigDecimal("importe").compareTo(importe) != 0){
													
							this.erroresTotalesImportes.append(" Diferencia de importe en archivo de ");
							this.erroresTotalesImportes.append(tabla);
							this.erroresTotalesImportes.append(" de: ");
							this.erroresTotalesImportes.append(importe.subtract(rsPostgre.getBigDecimal("importe")));	
							this.erroresTotalesImportes.append((currencyID == 142 ? " pesos" : " dolares") + ", en fecha de cierre ");
							this.erroresTotalesImportes.append(df.format(fechaCierre));
							this.erroresTotalesImportes.append(" || ");	
										
							retorno = false;		
						}				
								
					}					
					
				} catch (Exception e) {
					throw new AdempiereException(e);
				} finally {
					DB.close(rsPostgre, pstmt);
					rsPostgre = null;
					pstmt = null;
				}

			}

			rs.close();
			con.close();		
			

		} catch (Exception e) {

			throw new AdempiereException(e);

		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
		return retorno;
	}
	
	
	
	/**
	 * OpenUp. Guillermo Brust. 18/09/2013. ISSUE #  
	 * Generacion automatica de factura de archivo 750
	 * 
	 */
	private void generateInvoiceAutomatic750(){
		
		MDocType doctype = MDocType.forValueAndSystem(getCtx(), "invoice750", null);
		
		//Obtengo la fecha del ultimo dia del mes anterior que es la fecha en la cual se debiera emitir la factura
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		MPeriod periodoActual = MPeriod.get(getCtx(), today, 0);
		MPeriod periodoAnterior = new MPeriod(getCtx(), periodoActual.get_ID() - 1, null);
		Timestamp fechaFactura = periodoAnterior.getEndDate();
		//Obtengo el socio de negocio al cual se realiza la factura, 
		//este dato lo obtengo de la definicion de la factura tipo para este archivo (750), como existe una unica definicion me quedo con el primero
		MBPartner cbpartner = (MBPartner) ((MFduInvoiceType) MFduInvoiceType.getMFduInvoiceTypeForFduFile(getCtx(), this.uyFduFileID).get(0)).getC_BPartner();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;		
		
		try {
		
			String sql = "select fact.c_currency_id as moneda, prod.m_product_id as producto, tax.c_tax_id as impuesto," + 
						 " (sum(fact.amtsourcecr) - sum(fact.amtsourcedr)) as importe," + 
						 " ROUND(((sum(fact.amtsourcecr) - sum(fact.amtsourcedr)) * (tax.rate / 100)), 2) as montoImpuesto" +
						 " from fact_acct fact" +
						 " inner join gl_journal journal on fact.record_id = journal.gl_journal_id" +
						 " inner join uy_fduload load on journal.uy_fduload_id = load.uy_fduload_id" +
						 " inner join c_validcombination vcomb on fact.account_id = vcomb.account_id" +
						 " inner join m_product_acct prodacct on vcomb.c_validcombination_id = prodacct.p_revenue_acct" +
						 " inner join uy_fdu_prodstore prodstore on prodacct.m_product_id = prodstore.m_product_id" +
						 " inner join uy_fdu_invoicetype invtype on prodstore.uy_fdu_invoicetype_id = invtype.uy_fdu_invoicetype_id" +
						 " inner join m_product prod on prodacct.m_product_id = prod.m_product_id" +
						 " inner join c_tax tax on prod.c_taxcategory_id = tax.c_taxcategory_id" +
						 " where fact.ad_table_id = 224" +
						 " and fact.dateacct between '" + periodoAnterior.getStartDate() + "' and '" + periodoAnterior.getEndDate() + "'" +
						 " and journal.docstatus = 'CO'" +
						 " and load.uy_fdufile_id = " + this.uyFduFileID +
						 " and invtype.uy_fdufile_id = " + this.uyFduFileID +
						 " group by fact.c_currency_id, prod.m_product_id, tax.c_tax_id" +
						 " order by fact.c_currency_id desc";
			
			
			pstmt = DB.prepareStatement (sql, null);			
		    rs = pstmt.executeQuery();
		    
		    //Primero creo el cabezal de la factura para las dos monedas
		    MInvoice invPesos = this.createInvoice(doctype.get_ID(), fechaFactura, cbpartner.get_ID(), 142, new BigDecimal(0));
		    MInvoice invDolares = this.createInvoice(doctype.get_ID(), fechaFactura, cbpartner.get_ID(), 100, new BigDecimal(0));
		   
		    		   		   	    		    
			while (rs.next()){	
				
				if(rs.getInt("moneda") == 142){
										
					this.createInvoiceLine(invPesos.get_ID(), rs.getInt("producto"), rs.getBigDecimal("importe"), rs.getBigDecimal("montoImpuesto"), rs.getInt("impuesto"));
					
					invPesos.setTotalLines(invPesos.getTotalLines().add(rs.getBigDecimal("importe")));					
					invPesos.setGrandTotal(invPesos.getGrandTotal().add(rs.getBigDecimal("importe").add(rs.getBigDecimal("montoImpuesto"))));
					invPesos.saveEx();
				
				}else if(rs.getInt("moneda") == 100){
					
					this.createInvoiceLine(invDolares.get_ID(), rs.getInt("producto"), rs.getBigDecimal("importe"), rs.getBigDecimal("montoImpuesto"), rs.getInt("impuesto"));
					
					invDolares.setTotalLines(invDolares.getTotalLines().add(rs.getBigDecimal("importe")));					
					invDolares.setGrandTotal(invDolares.getGrandTotal().add(rs.getBigDecimal("importe").add(rs.getBigDecimal("montoImpuesto"))));
					invDolares.saveEx();
				}				
			}
			
			if(invPesos.getGrandTotal().compareTo(new BigDecimal(0)) > 0){
				
				//Acá tengo que ingresar un registro en la tabla de periodos facturados
				MFduPeriodInvoiced pinv = new MFduPeriodInvoiced(getCtx(), 0, get_TrxName());
				pinv.setC_Period_ID(periodoAnterior.get_ID());
				pinv.setC_Invoice_ID(invPesos.get_ID());
				pinv.setUY_FduFile_ID(this.uyFduFileID);
				pinv.saveEx();
				
			}else invPesos.deleteEx(true);			
			
			if(invDolares.getGrandTotal().compareTo(new BigDecimal(0)) > 0) {
				
				//Acá tengo que ingresar un registro en la tabla de periodos facturados
				MFduPeriodInvoiced pinv = new MFduPeriodInvoiced(getCtx(), 0, get_TrxName());
				pinv.setC_Period_ID(periodoAnterior.get_ID());
				pinv.setC_Invoice_ID(invDolares.get_ID());
				pinv.setUY_FduFile_ID(this.uyFduFileID);
				pinv.saveEx();
				
			}else invDolares.deleteEx(true);		
		
		}	
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
		
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 20/09/2013. ISSUE #  
	 * Generacion automatica de la primer factura de archivo de servicios UA, Socio Espectacular y ASUCASMU
	 * 
	 */
	private void generateFirstInvoiceAutomatic500(){
		
		MDocType doctype = MDocType.forValueAndSystem(getCtx(), "fduserv", null);
		
		//Obtengo la fecha del ultimo dia del mes anterior que es la fecha en la cual se debiera emitir la factura
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		MPeriod periodoActual = MPeriod.get(getCtx(), today, 0);
		MPeriod periodoAnterior = new MPeriod(getCtx(), periodoActual.get_ID() - 1, null);
		Timestamp fechaFactura = periodoAnterior.getEndDate();
		//Obtengo el socio de negocio al cual se realiza la factura, 
		//este dato lo obtengo de la definicion de la factura tipo para este archivo (750), como existe una unica definicion me quedo con el primero
		MBPartner cbpartner = (MBPartner) ((MFduInvoiceType) MFduInvoiceType.getMFduInvoiceTypeForFduFile(getCtx(), this.uyFduFileID).get(0)).getC_BPartner();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;		
		
		try {
		
			String sql = "select fact.c_currency_id as moneda, prod.m_product_id as producto, tax.c_tax_id as impuesto," +
						 " (sum(fact.amtsourcecr) - sum(fact.amtsourcedr)) as importe," + 
						 " ROUND(((sum(fact.amtsourcecr) - sum(fact.amtsourcedr)) * (tax.rate / 100)), 2) as montoImpuesto" +
						 " from fact_acct fact" +
						 " inner join gl_journal journal on fact.record_id = journal.gl_journal_id" +
						 " inner join gl_journalline jline on journal.gl_journal_id = jline.gl_journal_id" +
						 " inner join uy_fdu_storeacct stacct on jline.gl_journalline_id = stacct.gl_journalline_id" +
						 " inner join uy_fdu_prodstore prodstore on stacct.uy_fdu_store_id = prodstore.uy_fdu_store_id" +
						 " inner join m_product prod on prodstore.m_product_id = prod.m_product_id" +
						 " inner join c_tax tax on prod.c_taxcategory_id = tax.c_taxcategory_id" +
						 " where fact.ad_table_id = 224" +
						 " and fact.dateacct between '" + periodoActual.getStartDate() +"' and '" + periodoActual.getEndDate() + "'" +
						 //" and journal.docstatus = 'CO'" +
						 " and stacct.tipoDato = 1" +
						 " and stacct.uy_fdufile_id = " + this.uyFduFileID +						 
						 " group by fact.c_currency_id, prod.m_product_id, tax.c_tax_id" +
						 " order by fact.c_currency_id desc";
			
			
			pstmt = DB.prepareStatement (sql, null);			
		    rs = pstmt.executeQuery();
		    
		    //Primero creo el cabezal de la factura para las dos monedas
		    MInvoice invPesos = this.createInvoice(doctype.get_ID(), fechaFactura, cbpartner.get_ID(), 142, new BigDecimal(0));
		    MInvoice invDolares = this.createInvoice(doctype.get_ID(), fechaFactura, cbpartner.get_ID(), 100, new BigDecimal(0));
		   
		    		   		   	    		    
			while (rs.next()){	
				
				if(rs.getInt("moneda") == 142){
										
					this.createInvoiceLine(invPesos.get_ID(), rs.getInt("producto"), rs.getBigDecimal("importe"), rs.getBigDecimal("montoImpuesto"), rs.getInt("impuesto"));
					
					invPesos.setTotalLines(invPesos.getTotalLines().add(rs.getBigDecimal("importe")));					
					invPesos.setGrandTotal(invPesos.getGrandTotal().add(rs.getBigDecimal("importe").add(rs.getBigDecimal("montoImpuesto"))));
					invPesos.saveEx();
				
				}else if(rs.getInt("moneda") == 100){
					
					this.createInvoiceLine(invDolares.get_ID(), rs.getInt("producto"), rs.getBigDecimal("importe"), rs.getBigDecimal("montoImpuesto"), rs.getInt("impuesto"));
					
					invDolares.setTotalLines(invDolares.getTotalLines().add(rs.getBigDecimal("importe")));					
					invDolares.setGrandTotal(invDolares.getGrandTotal().add(rs.getBigDecimal("importe").add(rs.getBigDecimal("montoImpuesto"))));
					invDolares.saveEx();
				}				
			}
			
			if(invPesos.getGrandTotal().compareTo(new BigDecimal(0)) > 0){
				
				//Acá tengo que ingresar un registro en la tabla de periodos facturados
				MFduPeriodInvoiced pinv = new MFduPeriodInvoiced(getCtx(), 0, get_TrxName());
				pinv.setC_Period_ID(periodoAnterior.get_ID());
				pinv.setC_Invoice_ID(invPesos.get_ID());
				pinv.setUY_FduFile_ID(this.uyFduFileID);
				pinv.saveEx();
				
			}else invPesos.deleteEx(true);			
			
			if(invDolares.getGrandTotal().compareTo(new BigDecimal(0)) > 0) {
				
				//Acá tengo que ingresar un registro en la tabla de periodos facturados
				MFduPeriodInvoiced pinv = new MFduPeriodInvoiced(getCtx(), 0, get_TrxName());
				pinv.setC_Period_ID(periodoAnterior.get_ID());
				pinv.setC_Invoice_ID(invDolares.get_ID());
				pinv.setUY_FduFile_ID(this.uyFduFileID);
				pinv.saveEx();
				
			}else invDolares.deleteEx(true);		
		
		}	
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
	}
	
	
	
	/**
	 * OpenUp. Guillermo Brust. 20/09/2013. ISSUE #  
	 * Generacion automatica de la primer factura de archivo de servicios UA, Socio Espectacular y ASUCASMU
	 * 
	 */
	private void generateSecondInvoiceAutomatic500(){
		
		MDocType doctype = MDocType.forValueAndSystem(getCtx(), "fduserv", null);
		
		//Obtengo la fecha del ultimo dia del mes anterior que es la fecha en la cual se debiera emitir la factura
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		MPeriod periodoActual = MPeriod.get(getCtx(), today, 0);
		MPeriod periodoAnterior = new MPeriod(getCtx(), periodoActual.get_ID() - 1, null);
		Timestamp fechaFactura = periodoAnterior.getEndDate();
		//Obtengo el socio de negocio al cual se realiza la factura, 
		//este dato lo obtengo de la definicion de la factura tipo para este archivo (750), como existe una unica definicion me quedo con el primero
		MBPartner cbpartner = (MBPartner) ((MFduInvoiceType) MFduInvoiceType.getMFduInvoiceTypeForFduFile(getCtx(), this.uyFduFileID).get(0)).getC_BPartner();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;		
		
		try {
		
			String sql = "select fact.c_currency_id as moneda, prod.m_product_id as producto, tax.c_tax_id as impuesto," +
						 " (sum(fact.amtsourcecr) - sum(fact.amtsourcedr)) as importe," + 
						 " ROUND(((sum(fact.amtsourcecr) - sum(fact.amtsourcedr)) * (tax.rate / 100)), 2) as montoImpuesto" +
						 " from fact_acct fact" +
						 " inner join gl_journal journal on fact.record_id = journal.gl_journal_id" +
						 " inner join gl_journalline jline on journal.gl_journal_id = jline.gl_journal_id" +
						 " inner join uy_fdu_storeacct stacct on jline.gl_journalline_id = stacct.gl_journalline_id" +
						 " inner join uy_fdu_prodstore prodstore on stacct.uy_fdu_store_id = prodstore.uy_fdu_store_id" +
						 " inner join m_product prod on prodstore.m_product_id = prod.m_product_id" +
						 " inner join c_tax tax on prod.c_taxcategory_id = tax.c_taxcategory_id" +
						 " where fact.ad_table_id = 224" +
						 " and fact.dateacct between '" + periodoActual.getStartDate() +"' and '" + periodoActual.getEndDate() + "'" +
						 //" and journal.docstatus = 'CO'" +
						 " and stacct.tipoDato = 2" +
						 " and stacct.uy_fdufile_id = " + this.uyFduFileID +
						 " group by fact.c_currency_id, prod.m_product_id, tax.c_tax_id" +
						 " order by fact.c_currency_id desc";
			
			
			pstmt = DB.prepareStatement (sql, null);			
		    rs = pstmt.executeQuery();
		    
		    //Primero creo el cabezal de la factura para las dos monedas
		    MInvoice invPesos = this.createInvoice(doctype.get_ID(), fechaFactura, cbpartner.get_ID(), 142, new BigDecimal(0));
		    MInvoice invDolares = this.createInvoice(doctype.get_ID(), fechaFactura, cbpartner.get_ID(), 100, new BigDecimal(0));
		   
		    		   		   	    		    
			while (rs.next()){	
				
				if(rs.getInt("moneda") == 142){
										
					this.createInvoiceLine(invPesos.get_ID(), rs.getInt("producto"), rs.getBigDecimal("importe"), rs.getBigDecimal("montoImpuesto"), rs.getInt("impuesto"));
					
					invPesos.setTotalLines(invPesos.getTotalLines().add(rs.getBigDecimal("importe")));					
					invPesos.setGrandTotal(invPesos.getGrandTotal().add(rs.getBigDecimal("importe").add(rs.getBigDecimal("montoImpuesto"))));
					invPesos.saveEx();
				
				}else if(rs.getInt("moneda") == 100){
					
					this.createInvoiceLine(invDolares.get_ID(), rs.getInt("producto"), rs.getBigDecimal("importe"), rs.getBigDecimal("montoImpuesto"), rs.getInt("impuesto"));
					
					invDolares.setTotalLines(invDolares.getTotalLines().add(rs.getBigDecimal("importe")));					
					invDolares.setGrandTotal(invDolares.getGrandTotal().add(rs.getBigDecimal("importe").add(rs.getBigDecimal("montoImpuesto"))));
					invDolares.saveEx();
				}				
			}
			
			if(invPesos.getGrandTotal().compareTo(new BigDecimal(0)) > 0){
				
				//Acá tengo que ingresar un registro en la tabla de periodos facturados
				MFduPeriodInvoiced pinv = new MFduPeriodInvoiced(getCtx(), 0, get_TrxName());
				pinv.setC_Period_ID(periodoAnterior.get_ID());
				pinv.setC_Invoice_ID(invPesos.get_ID());
				pinv.setUY_FduFile_ID(this.uyFduFileID);
				pinv.saveEx();
				
			}else invPesos.deleteEx(true);			
			
			if(invDolares.getGrandTotal().compareTo(new BigDecimal(0)) > 0) {
				
				//Acá tengo que ingresar un registro en la tabla de periodos facturados
				MFduPeriodInvoiced pinv = new MFduPeriodInvoiced(getCtx(), 0, get_TrxName());
				pinv.setC_Period_ID(periodoAnterior.get_ID());
				pinv.setC_Invoice_ID(invDolares.get_ID());
				pinv.setUY_FduFile_ID(this.uyFduFileID);
				pinv.saveEx();
				
			}else invDolares.deleteEx(true);		
		
		}	
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
		
	}	
	
		
	/**
	 * 03/12/2012 Guillermo Brust OpenUp v2.5.1
	 * Generacion automatica de facturas y notas de credito.
	 *
	 * 
	 */
	
	private MInvoice createInvoice(int c_doctype_id, Timestamp fechaFactura, int c_bpartner_id, int c_currency_id, BigDecimal amtTotal){
		
		MInvoice fact = new MInvoice(getCtx(), 0, get_TrxName());
		fact.setIsSOTrx(true);				
		fact.setDocumentNo(MSequence.getDocumentNo(c_doctype_id, get_TrxName(), false));
		fact.setDocStatus("DR");
		fact.setDocAction("CO");
		fact.setProcessing(false);
		fact.setProcessed(false);
		fact.setPosted(true);		
		fact.setC_DocType_ID(c_doctype_id);
		fact.setC_DocTypeTarget_ID(c_doctype_id);
		fact.setDescription("Documento emitido de forma automática por carga de archivo FDU");
		fact.setIsApproved(true);
		fact.setIsTransferred(false);
		fact.setIsPrinted(false);
		fact.setSalesRep_ID(1003173);
		fact.setDateInvoiced(fechaFactura);
		fact.setDateAcct(fechaFactura);
		fact.setC_BPartner_ID(c_bpartner_id);
		fact.setC_BPartner_Location_ID(new MBPartner(getCtx(), c_bpartner_id, get_TrxName()).get_ID());
		fact.setIsDiscountPrinted(false);
		fact.setC_Currency_ID(c_currency_id);
		fact.setPaymentRule("P");
		fact.setC_PaymentTerm_ID(1000043);
		fact.setChargeAmt(new BigDecimal(0));					
		fact.setTotalLines(new BigDecimal(0));
		fact.setGrandTotal(amtTotal);
		if (c_currency_id == 142) fact.setM_PriceList_ID(1000030); else fact.setM_PriceList_ID(1000031);
		fact.setIsTaxIncluded(false);
		fact.setIsPaid(false);
		fact.setCreateFrom("N");
		fact.setGenerateTo("N");
		fact.setSendEMail(false);
		fact.setCopyFrom("N");
		fact.setIsSelfService(false);
		fact.setC_ConversionType_ID(114);
		fact.setIsPayScheduleValid(false);
		fact.setIsInDispute(false);
		fact.setCustodio("O");
		fact.setUY_Invoice("1");
		fact.setuy_tipogeneracion("FAC");
		fact.setdatetimeinvoiced(fechaFactura);
		fact.setpaymentruletype("CR");
		fact.setIsDevengable(false);
		fact.setAmtRetention(new BigDecimal(0));
		fact.saveEx();
		
		return fact;
		
	}
	

	private void createInvoiceLine(int c_invoice_id, int m_product_id, BigDecimal subtotal, BigDecimal taxamt, int c_tax_id){
		
		MInvoiceLine line = new MInvoiceLine(getCtx(), 0, get_TrxName());
		line.setC_Invoice_ID(c_invoice_id);
		line.setLine(10);
		line.setM_Product_ID(m_product_id);
		line.setQtyInvoiced(new BigDecimal(1));
		line.setPriceList(new BigDecimal(1));
		line.setPriceActual(subtotal);
		line.setPriceLimit(new BigDecimal(1));
		line.setLineNetAmt(subtotal);
		line.setC_UOM_ID(100);
		line.setC_Tax_ID(c_tax_id);
		line.setTaxAmt(taxamt);
		line.setM_AttributeSetInstance_ID(0);
		line.setIsDescription(false);
		line.setIsPrinted(false);
		line.setLineTotalAmt(subtotal);
		line.setProcessed(false);
		line.setQtyEntered(new BigDecimal(1));
		line.setPriceEntered(subtotal);
		line.setRRAmt(new BigDecimal(0));
		line.setA_CreateAsset(false);
		line.setA_Processed(false);
		line.setuy_printprice(subtotal);
		line.setUY_BonificaReglaUM(new BigDecimal(0));
		line.setUY_EsBonificCruzada(false);
		line.setUY_TieneBonificCruzada(false);
		line.setIsProvisioned(false);
		line.setIsSelected(true);
		line.saveEx();
		
	}
	
	
	

	private void executeFduFile_cc108(ResultSet molde){
		
		Connection con = null;
		ResultSet rs = null;
		
		try {

			// Obtengo datos desde tabla de Totalizadores. Esta es la guia.
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			//SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM");
			
			String sql = " SELECT FchCie, ConDet, sum(ImpPes) as saldomn, sum(ImpDol) as saldome " +
				         " FROM " + TABLA_TOTALIZADORES +
				        // " WHERE FchCie between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +				        
				         " WHERE ConDet ='" + molde.getString("value") + "'" +
						 " AND Gru ='" + molde.getString("grupccvalue") + "'" +
				         " AND CodProMar ='" + molde.getString("prodvalue") + "'" +
				         " AND CodAFF ='" + molde.getString("affvalue") + "'" +				        
				         " AND suc ='" + molde.getString("sucvalue") + "'" +
				         this.whereFechas +
				         " GROUP BY FchCie, ConDet";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				
				// Proceso segun tipo de calculo para este FduCod
				if (molde.getString("CalculateType").equalsIgnoreCase(X_UY_FduCod.CALCULATETYPE_Totalizadores)){
					
					//obtengo el molde totalizador derecho					
					//version funcional  -  01/11/2012
					//UY_FduCod moldeTotalizador = this.getCodigo(molde, rs.getString("value"));	
					
					UY_FduCod moldeTotalizador = this.getCodigo(molde.getInt("uy_molde_fduload_id"));
					
					//Se guardan para poder comparar en la proxima vuelta y no repetirlos
					this.hash_codigosTot.put(molde.getInt("uy_fducod_id"), rs.getTimestamp("FchCie"));
					
					// Calculo totalizador se guarda derecho
					if (molde.getString("curvalue").equalsIgnoreCase("858")){
						this.saveFduLoadLine(moldeTotalizador, rs.getTimestamp("FchCie"), rs.getBigDecimal("saldomn"), rs.getBigDecimal("saldome"));
					}
				}
				else if (molde.getString("CalculateType").equalsIgnoreCase(X_UY_FduCod.CALCULATETYPE_Consumos)){
					//if (this.consumValidations(molde, rs.getBigDecimal("saldomn"), rs.getBigDecimal("saldome"))) this.consumProcess(molde);
					this.consumProcess(molde);
				}
				else if (molde.getString("CalculateType").equalsIgnoreCase(X_UY_FduCod.CALCULATETYPE_Ajustes)){
					//if (this.ajustValidations(molde, rs.getBigDecimal("saldomn"), rs.getBigDecimal("saldome")))	this.ajustProcess(molde);
					this.ajustProcess(molde);
				}
			
			}

			rs.close();
			con.close();		
			

		} catch (Exception e) {

			throw new AdempiereException(e);

		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
	}
	
	
	
	private void executeFduFile_cl750(ResultSet molde){
		
		Connection con = null;
		ResultSet rs = null;
			
		try {
						
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			//SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
			
			String sql = "SELECT FCHPREMES, CODCON, GRUPO_AFINIDAD, CODMON, CODPRO, SUC, SUM(IMPCON) AS IMPCON" +
						 " FROM " + TABLA_CL750 + 
						 //" WHERE FCHPREMES between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +
						
						 " WHERE CODCON ='" + molde.getString("value") + "'" +						
				         " AND CODPRO ='" + molde.getString("prodvalue") + "'" + 
				         " AND GRUPO_AFINIDAD like '%" +  molde.getString("affvalue").substring(1) + "%'" + 			        		        
				         " AND SUC ='" + molde.getString("sucvalue") + "'" +
				         (molde.getInt("C_Currency_ID") == 142 ? " AND CODMON = '858'" :  " AND CODMON = '840'") +
				          this.whereFechas +
				         " GROUP BY FCHPREMES, CODCON, GRUPO_AFINIDAD, CODMON, CODPRO, SUC";
				        
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String codcon=rs.getString("CODCON").trim();
				
				UY_FduCod moldeArancel = this.getCodigoForCL750(molde, codcon, TABLA_MOLDE_CL750);
				
				
		
				if (rs.getString("CODMON").equals("858")) this.saveFduLoadLine(moldeArancel, rs.getTimestamp("FCHPREMES"), rs.getBigDecimal("IMPCON"), new BigDecimal(0));				
				else this.saveFduLoadLine(moldeArancel, rs.getTimestamp("FCHPREMES"), new BigDecimal(0), rs.getBigDecimal("IMPCON"));
			}

			rs.close();
			con.close();

		} catch (Exception e) {

			throw new AdempiereException(e);

		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
	}
	
	private void executeFduFile_cl500(ResultSet molde){
		
		Connection con = null;
		ResultSet rs = null;
			
		try {
						
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);		
			
			String sql = "SELECT \"Fecha Clearing\" as Fecha_Clering, Nro_Comercio, Sucursal, PTCod, \"Monto Compra\" as Monto_Compra " +
					 	 " FROM " + TABLA_CL500 +
					 	 " WHERE Nro_Comercio = '" + molde.getString("value") + "'" +	
					 	 " AND PTCod = '" + molde.getString("prodvalue") + "'" +
					 	 this.whereFechas +
					 	 " ORDER BY \"Fecha Clearing\"";
			
			//TODO: verificar que tabla usar para filtrar por afinidad tambien que en esta vista no se encuentra el campo 
			        
			rs = stmt.executeQuery(sql);

			while (rs.next()) {							
							
				this.saveFduLoadLineForStores(molde, rs.getTimestamp("Fecha_Clering"), molde.getInt("ccdepartamento"), molde.getInt("ccproducto"), molde.getInt("ccafinidad"), rs.getBigDecimal("Monto_Compra"));				
			}

			rs.close();
			con.close();

		} catch (Exception e) {

			throw new AdempiereException(e);

		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
	}
	
	
	private void ajustProcess(ResultSet moldeTotalizador){
		
		Connection con = null;		
		ResultSet rs = null;
		
		try {

						
			// Obtengo datos desde tabla de Ajustes.
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			//SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		
			
			String sql = " SELECT FchCie, CodProMar, CodAFF, Con, CanPesConAju, codMonAju, Gru, sum(impPes) as impPes " +
				         " FROM " + TABLA_AJUSTES + 
				        // " WHERE FchCie between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +
				        
				         " WHERE Gru ='" + moldeTotalizador.getString("GrupCCValue") + "'" +
				         " AND CodProMar ='" + moldeTotalizador.getString("ProdValue") + "'" +
				         " AND CodAFF ='" + moldeTotalizador.getString("AffValue") + "'" +
				         " AND con = '" + moldeTotalizador.getString("value") + "'" +
				         " AND CanPesConAju in(" + this.getCodigosDeHijosActivos(getCtx(), get_TrxName(), moldeTotalizador) + ")" +	
				         (moldeTotalizador.getInt("C_Currency_ID") == 142 ? " AND codMonAju = '858'" :  " AND codMonAju = '840'") +	//NO VA ACA				       
				         " AND suc ='" + moldeTotalizador.getString("sucvalue") + "'" +
				         this.whereFechas +
				         " GROUP BY FchCie, CodProMar, CodAFF, Con, CanPesConAju, codMonAju, Gru ";
				
				        
			rs = stmt.executeQuery(sql);			
				
			while (rs.next()) {
				
				UY_FduCod moldeAjuste = this.getCodigo(moldeTotalizador, rs.getString("CanPesConAju"), TABLA_MOLDE_CC108);
					
				   //moldeAjuste.getC_currency_id() == 142
				if (rs.getString("codMonAju").equals("858")) this.saveFduLoadLine(moldeAjuste, rs.getTimestamp("FchCie"), rs.getBigDecimal("impPes"), new BigDecimal(0));
				else this.saveFduLoadLine(moldeAjuste, rs.getTimestamp("FchCie"), new BigDecimal(0), rs.getBigDecimal("impPes"));
					
			}	
				
			rs.close();
			con.close();

			
		} catch (Exception e) {

			throw new AdempiereException(e + " Ajust process");

		} 
		finally {
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}		
	}
	
	
	private void consumProcess(ResultSet moldeTotalizador){
		
		Connection con = null;
		ResultSet rs = null;
		
		try {

			// Obtengo datos desde tabla de Consumos.
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			//SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
				
			String sql = " SELECT FchCie, CodProMar, CodAFF, ConCC, Mon, Gru, sum(impCon) as impCon " +
				         " FROM " + TABLA_CONSUMOS +
				        // " WHERE FchCie between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +
				         
				         " WHERE Gru ='" + moldeTotalizador.getString("GrupCCValue") + "'" +
				         " AND CodProMar ='" + moldeTotalizador.getString("ProdValue") + "'" +
				         " AND CodAFF ='"  + moldeTotalizador.getString("AffValue") + "'" +
				         " AND ConCC in(" + this.getCodigosDeHijosActivos(getCtx(), get_TrxName(), moldeTotalizador) + ")" +
				        (moldeTotalizador.getInt("C_Currency_ID") == 142 ? " AND Mon = '858'" :  " AND Mon = '840'") +	//NO VA ACA			        
				         " AND suc ='" + moldeTotalizador.getString("sucvalue") + "'" +
				         this.whereFechas +
				         " GROUP BY FchCie, CodProMar, CodAFF, ConCC, Mon, Gru ";
				        

			rs = stmt.executeQuery(sql);			
			
			while (rs.next()) {		
				
				UY_FduCod moldeConsumo = this.getCodigo(moldeTotalizador, rs.getString("ConCC"), TABLA_MOLDE_CC108);
									
				if (rs.getString("Mon").equals("858")) this.saveFduLoadLine(moldeConsumo, rs.getTimestamp("FchCie"), rs.getBigDecimal("impCon"), new BigDecimal(0));				
				else this.saveFduLoadLine(moldeConsumo, rs.getTimestamp("FchCie"), new BigDecimal(0), rs.getBigDecimal("impCon"));
				
			}
			
			rs.close();
			con.close();

			
		} catch (Exception e) {

			throw new AdempiereException(e + "consum process");

		} 
		finally {
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}		
	}
	
	public UY_FduCod getCodigo(ResultSet totalizador, String codigo, String tablaMolde){		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		UY_FduCod retorno = null;

		try {		
			
			String sql = "SELECT *" +
						 " FROM " + tablaMolde + " " +
						 " WHERE value = '" + codigo + "'" +
						 " AND grupccvalue ='" + totalizador.getString("grupccvalue") + "'" +
						 " AND prodvalue ='" + totalizador.getString("prodvalue") + "'" +
						 " AND affvalue ='" + totalizador.getString("affvalue") + "'" +				        
						 " AND sucvalue ='" + totalizador.getString("sucvalue") + "'" + 	
						 " AND curvalue ='" + totalizador.getString("curvalue") + "'";
			//Se obtienen todas las lineas de consumo y ajustes para ese totalizador, porque se compara con los mismos datos que tiene el totalizador

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();	
			
			while(rs.next()){
				UY_FduCod cod = new UY_FduCod(rs.getInt("uy_fducod_id"), rs.getString("value"), rs.getInt("c_elementvalue_id"), rs.getInt("c_elementvalue_id_cr"), rs.getInt("c_currency_id"), rs.getString("curvalue"),
						                       rs.getString("grupccvalue"), rs.getInt("uy_fdu_grupocc_id"), rs.getString("sucvalue"), rs.getInt("ccdepartamento"), rs.getString("prodvalue"), rs.getInt("ccproducto"), rs.getString("affvalue"), rs.getInt("ccafinidad"));
				
				retorno = cod;
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);

		}
		finally{
			DB.close(rs, pstmt);
		}
		
		return retorno;
		
	}
	
	public UY_FduCod getCodigoForCL750(ResultSet totalizador, String codigo, String tablaMolde){		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		UY_FduCod retorno = null;
		String sql = "";

		try {			
			sql = "SELECT *" +
				 " FROM " + tablaMolde + " " +
				 " WHERE value = '" + codigo + "'" +				 
				 " AND prodvalue ='" + totalizador.getString("prodvalue") + "'" +
				 " AND affvalue ='" + totalizador.getString("affvalue") + "'" +				        
				 " AND sucvalue ='" + totalizador.getString("sucvalue") + "'" + 	
				 " AND curvalue ='" + totalizador.getString("curvalue") + "'";
			//Se obtienen todas las lineas de consumo y ajustes para ese totalizador, porque se compara con los mismos datos que tiene el totalizador

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();	
			
			UY_FduCod cod = null;
			
			while(rs.next()){				

				//si la tabla molde es la del 750 no guardo el grupo de cuenta corriente
				if(tablaMolde.equals(TABLA_MOLDE_CL750)) cod = new UY_FduCod(rs.getInt("uy_fducod_id"), rs.getString("value"), rs.getInt("c_elementvalue_id"), rs.getInt("c_elementvalue_id_cr"), rs.getInt("c_currency_id"), rs.getString("curvalue"),
	                                                                          rs.getString("sucvalue"), rs.getInt("ccdepartamento"), rs.getString("prodvalue"), rs.getInt("ccproducto"), rs.getString("affvalue"), rs.getInt("ccafinidad"));
								
				
				retorno = cod;
	
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);

		}
		finally{
			DB.close(rs, pstmt);
		}

		return retorno;
		
	}
	
	public UY_FduCod getCodigo(int uy_molde_fduload_id){		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		UY_FduCod retorno = null;

		try {		
			
			String sql = "SELECT *" +
						 " FROM " + TABLA_MOLDE_CC108 + " " +
						 " WHERE uy_molde_fduload_id = " + uy_molde_fduload_id;

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();	
			
			if(rs.next()){
				retorno = new UY_FduCod(rs.getInt("uy_fducod_id"), rs.getString("value"), rs.getInt("c_elementvalue_id"), rs.getInt("c_elementvalue_id_cr"), rs.getInt("c_currency_id"), rs.getString("curvalue"),
						                       rs.getString("grupccvalue"), rs.getInt("uy_fdu_grupocc_id"), rs.getString("sucvalue"), rs.getInt("ccdepartamento"), rs.getString("prodvalue"), rs.getInt("ccproducto"), rs.getString("affvalue"), rs.getInt("ccafinidad"));
				
				
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);

		}
		finally{
			DB.close(rs, pstmt);
		}
		
		return retorno;
		
	}
	
//	private boolean ajustValidations(ResultSet molde, BigDecimal saldomn, BigDecimal saldome){
//		
//		boolean retorno = false;
//		Connection con = null;
//		ResultSet rs = null;
//
//		try {
//			
//			if(!this.getTodosLosCodigosDeHijos(getCtx(), get_TrxName(), molde).equals("") ){
//				
//				// Obtengo la suma de todos los ajustes y los comparo con el importe del totalizador.
//				con = this.getConnection();
//				Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
//
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
//			
//				
//				String sql = " SELECT isnull(sum(impPes),0) as importe" +
//					         " FROM " + TABLA_AJUSTES + 
//					         " WHERE FchCie between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +
//					         " AND Gru ='" + molde.getString("GrupCCValue") + "'" +
//					         " AND CodProMar ='" + molde.getString("ProdValue") + "'" +
//					         " AND CodAFF ='" + molde.getString("AffValue") + "'" +
//					         " AND con = '" + molde.getString("value") + "'" +
//					         " AND CanPesConAju in(" + this.getTodosLosCodigosDeHijos(getCtx(), get_TrxName(), molde) + ")" +	
//					         (molde.getInt("C_Currency_ID") == 142 ? " AND codMonAju = '858'" :  " AND codMonAju = '840'") +				       
//					         " AND suc ='" + molde.getString("sucvalue") + "'";				        
//					        
//				rs = stmt.executeQuery(sql);
//
//				while (rs.next()) {
//					
//					if(molde.getInt("C_Currency_ID") == 142){
//						if(rs.getBigDecimal("importe").compareTo(saldomn) == 0) retorno = true;					
//					}else if(rs.getBigDecimal("importe").compareTo(saldome) == 0) retorno = true;		
//				}				
//				
//				rs.close();
//				con.close();
//
//			}
//			
//		} catch (Exception e) {
//
//			throw new AdempiereException(e + " Ajust validations");
//
//		} 
//		finally {
//			if (con != null){
//				try {
//					if (rs != null){
//						if (!rs.isClosed()) rs.close();
//					}
//					if (!con.isClosed()) con.close();
//				} catch (SQLException e) {
//					throw new AdempiereException(e);
//				}
//			}
//		}
//		return retorno;		
//	}
//
//	private boolean consumValidations(ResultSet molde, BigDecimal saldomn, BigDecimal saldome){
//				
//		boolean retorno = false;
//		Connection con = null;
//		ResultSet rs = null;
//
//		try {
//			
//			//Esto es porque no todas las combinaciones traen datos.
//			if(!this.getCodigosDeHijosActivos(getCtx(), get_TrxName(), molde).equals("") ){
//				
//				// Obtengo la suma de todos los consumos y los comparo con el importe del totalizador.
//				con = this.getConnection();
//				Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
//
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
//			
//				
//				String sql = " SELECT isnull(sum(impCon),0) as importe" +
//					         " FROM " + TABLA_CONSUMOS + 
//					         " WHERE FchCie between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +
//					         " AND Gru ='" + molde.getString("GrupCCValue") + "'" +
//					         " AND CodProMar ='" + molde.getString("ProdValue") + "'" +
//					         " AND CodAFF ='" + molde.getString("AffValue") + "'" +
//					         " AND ConCC in(" + this.getTodosLosCodigosDeHijos(getCtx(), get_TrxName(), molde) + ")" +				         
//					         (molde.getInt("C_Currency_ID") == 142 ? " AND Mon = '858'" :  " AND Mon = '840'") +
//					         " AND suc ='" + molde.getString("sucvalue") + "'";
//					        
//
//				rs = stmt.executeQuery(sql);
//
//				while (rs.next()) {
//					
//					if(molde.getInt("C_Currency_ID") == 142){
//						if(rs.getBigDecimal("importe").compareTo(saldomn) == 0) retorno = true;					
//					}else if(rs.getBigDecimal("importe").compareTo(saldome) == 0) retorno = true;				
//				}
//				
//				rs.close();
//				con.close();
//
//				
//			}			
//
//		} catch (Exception e) {
//
//			throw new AdempiereException(e + " consum validations");
//
//		} 
//		finally {
//			if (con != null){
//				try {
//					if (rs != null){
//						if (!rs.isClosed()) rs.close();
//					}
//					if (!con.isClosed()) con.close();
//				} catch (SQLException e) {
//					throw new AdempiereException(e);
//				}
//			}
//		}
//		return retorno;
//	}


	/***
	 * Genera nueva linea en carga fdu
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 19/10/2012
	 * @see
	 * @param fducod
	 * @param fechaCierre
	 * @param saldomn
	 * @param saldome
	 */
	private void saveFduLoadLine(UY_FduCod molde, Timestamp fechaCierre, BigDecimal saldomn, BigDecimal saldome){
		
		try{
			
			// Si tengo moneda nacional genero linea de carga para moneda nacional
			if (saldomn.compareTo(Env.ZERO) != 0){
				MFduLoadLine line = new MFduLoadLine(getCtx(), 0, null);
				line.setAD_Client_ID(this.fduLoad.getAD_Client_ID());
				line.setAD_Org_ID(this.fduLoad.getAD_Org_ID());
				line.setUY_FduLoad_ID(this.fduLoad.get_ID());
				line.setDateAcct(TimeUtil.trunc(fechaCierre, TimeUtil.TRUNC_DAY));
				
				// Segun signo del saldo. Si es positivo respeto las cuentas de la parametrizacion,
				// si es negativo tengo que dar vuelta el asiento.
				if (saldomn.compareTo(Env.ZERO) > 0){
					line.setC_ElementValue_ID(molde.getC_elementvalue_id());
					line.setC_ElementValue_ID_Cr(molde.getC_elementvalue_id_cr());
				}
				else{
					line.setC_ElementValue_ID(molde.getC_elementvalue_id_cr());
					line.setC_ElementValue_ID_Cr(molde.getC_elementvalue_id());

					//Esto se hace porque para los codigos 12200 y 12100 necesito tener el signo para saber a que cuenta va a la hora de generar el asiento
					if(molde.getUy_fducod_id() != 1000128 && molde.getUy_fducod_id() != 1000127){
						// Quito signo al saldo
						saldomn = saldomn.negate();
					}	
				}
				
				line.setUY_Fdu_GrupoCC_ID(molde.getGrupocc_id());
				line.setC_Activity_ID_1(molde.getCcdepartamento());
				line.setC_Activity_ID_2(molde.getCcproducto());
				line.setC_Activity_ID_3(molde.getCcafinidad());
				MFduCod cod = new MFduCod(getCtx(),molde.getUy_fducod_id(), null);
				line.setDescription(cod.getName());
				line.setC_Currency_ID(142);
				line.setUY_FduCod_ID(cod.get_ID());
				line.setAmtSourceCr(saldomn);
				line.setAmtSourceDr(saldomn);
				line.saveEx();
			}
			
			// Si tengo moneda extranjera genero linea de carga para moneda extranjera
			if (saldome.compareTo(Env.ZERO) != 0){
				MFduLoadLine line = new MFduLoadLine(getCtx(), 0, null);
				line.setUY_FduLoad_ID(this.fduLoad.get_ID());
				line.setDateAcct(TimeUtil.trunc(fechaCierre, TimeUtil.TRUNC_DAY));
				
				// Segun signo del saldo. Si es positivo respeto las cuentas de la parametrizacion,
				// si es negativo tengo que dar vuelta el asiento.
				if (saldome.compareTo(Env.ZERO) > 0){
					line.setC_ElementValue_ID(molde.getC_elementvalue_id());
					line.setC_ElementValue_ID_Cr(molde.getC_elementvalue_id_cr());
				}
				else{
					line.setC_ElementValue_ID(molde.getC_elementvalue_id_cr());
					line.setC_ElementValue_ID_Cr(molde.getC_elementvalue_id());
					
					//Esto se hace porque para los codigos 12200 y 12100 necesito tener el signo para saber a que cuenta va a la hora de generar el asiento
					if(molde.getUy_fducod_id() != 1000128 && molde.getUy_fducod_id() != 1000127){
						// Quito signo al saldo
						saldome = saldome.negate();
					}					
				}
				
				if(molde.getGrupocc_id() > 0){
					line.setUY_Fdu_GrupoCC_ID(molde.getGrupocc_id());
				}			
				line.setC_Activity_ID_1(molde.getCcdepartamento());
				line.setC_Activity_ID_2(molde.getCcproducto());
				line.setC_Activity_ID_3(molde.getCcafinidad());
				MFduCod cod = new MFduCod(getCtx(),molde.getUy_fducod_id(), null);
				line.setDescription(cod.getName());
				line.setC_Currency_ID(100);
				line.setUY_FduCod_ID(cod.get_ID());
				line.setAmtSourceCr(saldome);
				line.setAmtSourceDr(saldome);
				line.saveEx();
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}
	/**
	 * OpenUp Guillermo Brust. 20/11/2012
	 * Descripción: Se graban lineas de carga para realizar los asientos, pero con datos diferentes a otros archivos fdu
	 * @param molde
	 * @param fechaClearing
	 * @param saldomn
	 */
	
	private void saveFduLoadLineForStores(ResultSet molde, Timestamp fechaClearing, int c_activity_id_1, int c_activity_id_2, int c_activity_id_3, BigDecimal saldomn){
		
		try{	
			
			if (saldomn.compareTo(Env.ZERO) != 0){
				MFduLoadLine line = new MFduLoadLine(getCtx(), 0, null);
				line.setUY_FduLoad_ID(this.fduLoad.get_ID());
				line.setDateAcct(TimeUtil.trunc(fechaClearing, TimeUtil.TRUNC_DAY));
				line.setC_ElementValue_ID(Integer.parseInt(molde.getString("c_elementvalue_id")));
				line.setC_ElementValue_ID_Cr(Integer.parseInt(molde.getString("c_elementvalue_id_cr")));
				line.setC_ElementValue_ID_Cr2(Integer.parseInt(molde.getString("c_elementvalue_id_cr2")));
				line.setC_Activity_ID_1(c_activity_id_1);
				line.setC_Activity_ID_2(c_activity_id_2);
				line.setC_Activity_ID_3(c_activity_id_3);				
				line.setDescription(molde.getString("description"));
				line.setC_Currency_ID(142);
				MFduStore comercio = MFduStore.getStoreForValue(getCtx(), molde.getString("value"));
				line.setUY_Fdu_Store_ID(comercio.get_ID());		
				line.setAmtSourceCr(saldomn);
				line.setAmtSourceDr(saldomn);
				line.saveEx();
			}		
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	public void setCombinations(){
		
		String action = "";
		
		try{			
			this.showHelp("Cargando combinaciones...");
			
			//Segun sea el archivo fdu a procesar, se va a cargar una tabla molde diferente, con diferentes combinaciones.
			if(this.uyFduFileID == 1000008){
				
				action = "INSERT INTO " + TABLA_MOLDE_CC108 + "(uy_fducod_id, value, istotalizador, uy_fdu_journaltype_id, c_elementvalue_id," +
						" c_elementvalue_id_cr, c_currency_id, curvalue, uy_fdu_grupocc_id, grupccvalue, sucvalue, ccdepartamento, prodvalue, ccproducto, affvalue, ccafinidad, ad_user_id) " +
						" SELECT DISTINCT uy_fducod.uy_fducod_id, uy_fducod.value, uy_fducod.istotalizador, uy_fdu_journaltype.uy_fdu_journaltype_id," +
						" uy_fdu_journaltype.c_elementvalue_id,uy_fdu_journaltype.c_elementvalue_id_cr,  uy_fdu_currency.c_currency_id," +
						" uy_fdu_currency.value as curvalue, uy_fdu_grupocc.uy_fdu_grupocc_id, uy_fdu_grupocc.value as grupccvalue, uy_fdu_sucursal.value as sucvalue," + 
						" uy_fdu_sucursal.c_activity_id as ccdepartamento,  uy_fdu_productos.value as prodvalue, uy_fdu_productos.c_activity_id as ccproducto," +
						" uy_fdu_afinidad.value as affvalue, uy_fdu_afinidad.c_activity_id as ccafinidad," + this.user +  
						" FROM uy_fducod, uy_fdu_currency, uy_fdu_grupocc, uy_fdu_productos, uy_fdu_afinidad,  uy_fdu_journaltype, uy_fdu_sucursal, uy_fdufile" +
						" WHERE uy_fdufile.uy_fdufile_id = " + this.uyFduFileID + " AND uy_fdufile.uy_fdufile_id = uy_fducod.uy_fdufile_id" +
						" AND uy_fducod.uy_fducod_id = uy_fdu_journaltype.uy_fducod_id AND uy_fdu_productos.uy_fdu_productos_id = uy_fdu_afinidad.uy_fdu_productos_id" +
						" AND uy_fducod.isactive = 'Y' AND uy_fdu_currency.isactive = 'Y' AND uy_fdu_productos.uy_fdufile_id = " + this.uyFduFileID +  
						" AND uy_fdu_grupocc.isactive = 'Y' AND uy_fdu_productos.isactive = 'Y' AND uy_fdu_afinidad.isactive = 'Y' AND uy_fdu_journaltype.isactive = 'Y' AND uy_fdu_sucursal.isactive = 'Y'";
						//" AND uy_fdu_journaltype.c_elementvalue_id_cr = 1003870 ";
				
			}else if(this.uyFduFileID == 1000011){
				
				action = "INSERT INTO " + TABLA_MOLDE_CL750 + "(uy_fducod_id, value, uy_fdu_journaltype_id, c_elementvalue_id," +
						" c_elementvalue_id_cr, c_currency_id, curvalue, sucvalue, ccdepartamento, prodvalue, ccproducto, affvalue, ccafinidad, ad_user_id) " +
						" SELECT DISTINCT uy_fducod.uy_fducod_id, uy_fducod.value, uy_fdu_journaltype.uy_fdu_journaltype_id," +
						" uy_fdu_journaltype.c_elementvalue_id,uy_fdu_journaltype.c_elementvalue_id_cr,  uy_fdu_currency.c_currency_id," +
						" uy_fdu_currency.value as curvalue, uy_fdu_sucursal.value as sucvalue," + 
						" uy_fdu_sucursal.c_activity_id as ccdepartamento, uy_fdu_productos.value as prodvalue, uy_fdu_productos.c_activity_id as ccproducto," +
						" uy_fdu_afinidad.value as affvalue, uy_fdu_afinidad.c_activity_id as ccafinidad," + this.user +  
						" FROM uy_fducod, uy_fdu_currency, uy_fdu_grupocc, uy_fdu_productos, uy_fdu_afinidad,  uy_fdu_journaltype, uy_fdu_sucursal, uy_fdufile" +
						" WHERE uy_fdufile.uy_fdufile_id = " + this.uyFduFileID + " AND uy_fdufile.uy_fdufile_id = uy_fducod.uy_fdufile_id" +
						" AND uy_fducod.uy_fducod_id = uy_fdu_journaltype.uy_fducod_id AND uy_fdu_productos.uy_fdu_productos_id = uy_fdu_afinidad.uy_fdu_productos_id" +
						" AND uy_fducod.isactive = 'Y' AND uy_fdu_currency.isactive = 'Y' AND uy_fdu_productos.uy_fdufile_id = " + this.uyFduFileID + 
						" AND uy_fdu_grupocc.isactive = 'Y' AND uy_fdu_productos.isactive = 'Y' AND uy_fdu_afinidad.isactive = 'Y' AND uy_fdu_journaltype.isactive = 'Y' AND uy_fdu_sucursal.isactive = 'Y'";
				
			}else if(this.uyFduFileID == 1000012 || this.uyFduFileID == 1000014){
				
				action = "INSERT INTO " + TABLA_MOLDE_CL500 + "(uy_fdu_store_id, value, name, rate, description, c_elementvalue_id, c_elementvalue_id_cr, c_elementvalue_id_cr2," +
						 " sucvalue, ccdepartamento, prodvalue, ccproducto, affvalue, ccafinidad, ad_user_id)" +
						 " SELECT DISTINCT s.uy_fdu_store_id, s.value, s.name, s.rate, s.description, j.c_elementvalue_id, j.c_elementvalue_id_cr, j.c_elementvalue_id_cr2," + 
						 " r.value, r.c_activity_id, p.value, p.c_activity_id, a.value, a.c_activity_id, " + this.user +
						 " FROM uy_fdu_store s, uy_fdu_sucursal r, uy_fdu_productos p, uy_fdu_afinidad a, uy_fdu_journaltype j" + 
						 " WHERE s.isactive = 'Y' AND s.uy_fdufile_id = " + this.uyFduFileID + " AND p.uy_fdufile_id = " + this.uyFduFileID + " AND j.uy_fdufile_id = " + this.uyFduFileID + " AND a.uy_fdu_productos_id = p.uy_fdu_productos_id";
				
				
			}
			
			DB.executeUpdateEx(action, null);

		}
		catch (Exception e)
		{
			throw new AdempiereException (e);
		}
		
		
	}
	
	/***
	 * Obtiene los codigos de los hijos del totalizador del parametro
	 * OpenUp. Guillermo Brust. 26/10/2012. ISSUE #60.
	 * @param ctx
	 * @param trxName
	 * @param moldeTotalizador
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	
	public String getCodigosDeHijosActivos(Properties ctx, String trxName, ResultSet moldeTotalizador) {
		
		String retorno = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {		
			
			
			String sql =" SELECT distinct value" +
						" FROM UY_FduCod" +
						" WHERE isactive='Y'" +
						" AND parentValue='" +  moldeTotalizador.getString("Value") + "'" +
						" AND UY_FduCod_ID in (select uy_fducod_id from uy_fdu_journaltype where uy_fducod_id = UY_FduCod.uy_fducod_id)" + 
						" AND UY_FduFile_ID = " + this.uyFduFileID;		

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();

			while (rs.next()) {
				
				if(rs.isLast()) retorno += "'" + rs.getString("Value") + "'";
				else retorno += "'" + rs.getString("Value")  + "',";
				
			}
			
		} catch (Exception e) {

			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return retorno;	
	}	

	/***
	 * Despliega avance en ventana splash
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 29/10/2012
	 * @see
	 * @param text
	 */
	private void showHelp(String text){
		if (this.getProcessInfo().getWaiting() != null){
			this.getProcessInfo().getWaiting().setText(text);
		}			
	}
	
	
	/***
	 * Obtiene y retorna primer empresa que no es la System.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 06/11/2012
	 * @see
	 * @return
	 */
	private MClient getDefaultClient(){
		
		String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "!=0";
		
		MClient value = new Query(getCtx(), I_AD_Client.Table_Name, whereClause, null)
		.first();
		
		return value;
	}
	
	/***
	 * Envio de email con notificacion de fin de proceso.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 27/12/2012
	 * @see
	 */
	private void sendNotification(){
		
		try{
			MClient client = MClient.get(getCtx(), this.getAD_Client_ID());
			
			client.sendEMail(1003228, "Aviso Ejecucion Automatica Generacion de Asientos Archivo CC108.",
			"Proceso finalizado con Exito." + "\n" +
			"Numero : " + this.fduLoad.getDocumentNo() + "\n" +
			"Fecha Corrida : " + this.fduLoad.getUpdated(), null);			
		}
		catch (Exception e){
			// No hago nada en caso de error.
		}

		
	}
}
