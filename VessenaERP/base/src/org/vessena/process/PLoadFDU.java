/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/11/2012
 */
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
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MClient;
import org.compiere.model.MPeriod;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.UY_FduCod;
import org.openup.model.MFduCod;
import org.openup.model.MFduConnectionData;
import org.openup.model.MFduLoad;
import org.openup.model.MFduLoadLine;
import org.openup.model.X_UY_FduCod;

/**
 * org.openup.process - PLoadFDU
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 15/11/2012
 * @see
 */
public class PLoadFDU extends SvrProcess {

	private int contador= 0;
	
	private static String TABLA_FDCOD = "UY_FduCod";
	
	private static String TABLA_MOLDE_CC108 = "UY_Molde_FduLoad_CC108";
	private static String TABLA_MOLDE_CC120 = "UY_Molde_FduLoad_CC120";
	private static String TABLA_MOLDE_CL750 = "UY_Molde_FduLoad_CL750";
	private static String TABLA_MOLDE_CC500 = "UY_Molde_FduLoad_CC500";
	
	private static String TABLA_TOTALIZADORES = "T_FDU_CC108D_DETALLE_T";
	private static String TABLA_CONSUMOS = "T_FDU_CC108D_DETALLE_C";
	private static String TABLA_AJUSTES = "T_FDU_CC108D_DETALLE_A";	
	
	private int user;	
	private Timestamp fechaInicio;
	private Timestamp fechaFin;	
	private int uyFduFileID;
	
	private MFduLoad fduLoad = null;
	private boolean isManual = true;
	//private MAcctSchema schema = null;
	
	private HashMap<Integer, Timestamp> hash_codigosTot = new HashMap<Integer, Timestamp>();

	
	/**
	 * Constructor.
	 */
	public PLoadFDU() {
		// TODO Auto-generated constructor stub
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
			}
		}
				
	}

	@Override
	protected String doIt() throws Exception {
		
		this.deleteInstanciasViejasReporte();
		//this.dropMolde();
		this.setCombinations();
		this.execute();
		
		return "OK";
	}
	


	private void deleteInstanciasViejasReporte(){
		
		String tabla = "";
		
		//Segun que archivo fdu sea, es la tabla molde que se va a limpiar		
		if(this.uyFduFileID == 1000008) tabla = TABLA_MOLDE_CC108;			
		else if(this.uyFduFileID == 1000010) tabla = TABLA_MOLDE_CC120;
		else if(this.uyFduFileID == 1000011) tabla = TABLA_MOLDE_CL750;
		else if(this.uyFduFileID == 1000012) tabla = TABLA_MOLDE_CC500;
		
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
	
	public void execute(){

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
			this.fduLoad.setDefaultDocTypeID();
			this.fduLoad.setDateTrx(TimeUtil.trunc(new Timestamp(System.currentTimeMillis()),TimeUtil.TRUNC_DAY));
			this.fduLoad.setC_Period_ID(MPeriod.getC_Period_ID(getCtx(),
			this.fduLoad.getDateTrx(), Env.getAD_Org_ID(getCtx())));
			this.fduLoad.setUY_FduFile_ID(this.uyFduFileID);
			this.fduLoad.setIsManual(this.isManual);
			this.fduLoad.setProcessingDate(new Timestamp(System.currentTimeMillis()));
			this.fduLoad.setAD_Client_ID(client.getAD_Client_ID());
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
			
			}else if(this.uyFduFileID == 1000012){
				
				sql = "SELECT * FROM " + TABLA_MOLDE_CC500 + " m" +
					      " INNER JOIN " + TABLA_FDCOD + " c ON m.UY_FduCod_Id = c.UY_FduCod_Id" +
						  " WHERE c.IsActive='Y'";
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
				else if(this.uyFduFileID == 1000010) this.executeFduFile_cc120(rs);
				else if(this.uyFduFileID == 1000011) this.executeFduFile_cl750(rs);
				else if(this.uyFduFileID == 1000012) this.executeFduFile_cc500(rs);
				
				
			}
			
			this.showHelp("Generando Asientos Diarios...");
			this.fduLoad.generateJournals(this.get_TrxName());
			
			// Casos especiales
			//if(this.uyFduFileID == 1000008)this.fduLoad.generateSpecialCases();
			
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}
	
	

	private void executeFduFile_cc108(ResultSet molde){
		
		Connection con = null;
		ResultSet rs = null;
		
		try {

			// Obtengo datos desde tabla de Totalizadores. Esta es la guia.
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM");
			
			String sql = " SELECT FchCie, ConDet, sum(ImpPes) as saldomn, sum(ImpDol) as saldome " +
				         " FROM " + TABLA_TOTALIZADORES +
				         " WHERE FchCie between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +
				         " AND ConDet ='" + molde.getString("value") + "'" +
						 " AND Gru ='" + molde.getString("grupccvalue") + "'" +
				         " AND CodProMar ='" + molde.getString("prodvalue") + "'" +
				         " AND CodAFF ='" + molde.getString("affvalue") + "'" +				        
				         " AND suc ='" + molde.getString("sucvalue") + "'" +
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
					this.saveFduLoadLine(moldeTotalizador, rs.getTimestamp("FchCie"), rs.getBigDecimal("saldomn"), rs.getBigDecimal("saldome"));							
					
				}
				else if (molde.getString("CalculateType").equalsIgnoreCase(X_UY_FduCod.CALCULATETYPE_Consumos)){
					if (this.consumValidations(molde, rs.getBigDecimal("saldomn"), rs.getBigDecimal("saldome"))) this.consumProcess(molde);					
				}
				else if (molde.getString("CalculateType").equalsIgnoreCase(X_UY_FduCod.CALCULATETYPE_Ajustes)){
					if (this.ajustValidations(molde, rs.getBigDecimal("saldomn"), rs.getBigDecimal("saldome")))	this.ajustProcess(molde);
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
	
	private void executeFduFile_cc120(ResultSet molde){
		
		Connection con = null;
		ResultSet rs = null;
		
		try {
			
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM");
			
			String sql = " ";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				
				
			
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
	
	private void executeFduFile_cc500(ResultSet molde){
		
		Connection con = null;
		ResultSet rs = null;
		
		try {
			
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
			
			String sql = "";
				        
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				
				
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

			SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
			
			String sql = "SELECT GRUPO_AFINIDAD, CODMON, FCHPREMES, CODPRO, SUC, CODCON, IMPCON" +
						 " FROM Q_FDU_CL750D_DETALLE_40_CON_GRUPO_AFINIDAD " +
						 " WHERE FCHPREMES between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +
						 " AND CODCON ='" + molde.getString("value") + "'" +						
				         " AND CODPRO ='" + molde.getString("prodvalue") + "'" + 
				         " AND GRUPO_AFINIDAD like '%" +  molde.getString("affvalue").substring(1) + molde.getString("grupccvalue").substring(1) + "'" + 			        		        
				         " AND SUC ='" + molde.getString("sucvalue") + "'" +
				         (molde.getInt("C_Currency_ID") == 142 ? " AND CODMON = '858'" :  " AND CODMON = '840'");
				        
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				
				UY_FduCod moldeArancel = this.getCodigo(molde, rs.getString("CODCON"), TABLA_MOLDE_CL750);
				
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
	
	
	private void ajustProcess(ResultSet moldeTotalizador){
		
		Connection con = null;		
		ResultSet rs = null;
		
		try {

						
			// Obtengo datos desde tabla de Ajustes.
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		
			
			String sql = " SELECT FchCie, CodProMar, CodAFF, Con, CanPesConAju, codMonAju, Gru, sum(impPes) as impPes " +
				         " FROM " + TABLA_AJUSTES + 
				         " WHERE FchCie between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +
				         " AND Gru ='" + moldeTotalizador.getString("GrupCCValue") + "'" +
				         " AND CodProMar ='" + moldeTotalizador.getString("ProdValue") + "'" +
				         " AND CodAFF ='" + moldeTotalizador.getString("AffValue") + "'" +
				         " AND con = '" + moldeTotalizador.getString("value") + "'" +
				         " AND CanPesConAju in(" + this.getCodigosDeHijosActivos(getCtx(), get_TrxName(), moldeTotalizador) + ")" +	
				         //(moldeTotalizador.getInt("C_Currency_ID") == 142 ? " AND codMonAju = '858'" :  " AND codMonAju = '840'") +	//NO VA ACA				       
				         " AND suc ='" + moldeTotalizador.getString("sucvalue") + "'" +
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

			SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
				
			String sql = " SELECT FchCie, CodProMar, CodAFF, ConCC, Mon, Gru, sum(impCon) as impCon " +
				         " FROM " + TABLA_CONSUMOS +
				         " WHERE FchCie between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +
				         " AND Gru ='" + moldeTotalizador.getString("GrupCCValue") + "'" +
				         " AND CodProMar ='" + moldeTotalizador.getString("ProdValue") + "'" +
				         " AND CodAFF ='"  + moldeTotalizador.getString("AffValue") + "'" +
				         " AND ConCC in(" + this.getCodigosDeHijosActivos(getCtx(), get_TrxName(), moldeTotalizador) + ")" +
				        //(moldeTotalizador.getInt("C_Currency_ID") == 142 ? " AND Mon = '858'" :  " AND Mon = '840'") +	//NO VA ACA			        
				         " AND suc ='" + moldeTotalizador.getString("sucvalue") + "'" +
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
	
	private boolean ajustValidations(ResultSet molde, BigDecimal saldomn, BigDecimal saldome){
		
		boolean retorno = false;
		Connection con = null;
		ResultSet rs = null;

		try {
			
			if(!this.getTodosLosCodigosDeHijos(getCtx(), get_TrxName(), molde).equals("") ){
				
				// Obtengo la suma de todos los ajustes y los comparo con el importe del totalizador.
				con = this.getConnection();
				Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

				SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
			
				
				String sql = " SELECT isnull(sum(impPes),0) as importe" +
					         " FROM " + TABLA_AJUSTES + 
					         " WHERE FchCie between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +
					         " AND Gru ='" + molde.getString("GrupCCValue") + "'" +
					         " AND CodProMar ='" + molde.getString("ProdValue") + "'" +
					         " AND CodAFF ='" + molde.getString("AffValue") + "'" +
					         " AND con = '" + molde.getString("value") + "'" +
					         " AND CanPesConAju in(" + this.getTodosLosCodigosDeHijos(getCtx(), get_TrxName(), molde) + ")" +	
					         (molde.getInt("C_Currency_ID") == 142 ? " AND codMonAju = '858'" :  " AND codMonAju = '840'") +				       
					         " AND suc ='" + molde.getString("sucvalue") + "'";				        
					        
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					
					if(molde.getInt("C_Currency_ID") == 142){
						if(rs.getBigDecimal("importe").compareTo(saldomn) == 0) retorno = true;					
					}else if(rs.getBigDecimal("importe").compareTo(saldome) == 0) retorno = true;		
				}				
				
				rs.close();
				con.close();

			}
			
		} catch (Exception e) {

			throw new AdempiereException(e + " Ajust validations");

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

	private boolean consumValidations(ResultSet molde, BigDecimal saldomn, BigDecimal saldome){
				
		boolean retorno = false;
		Connection con = null;
		ResultSet rs = null;

		try {
			
			//Esto es porque no todas las combinaciones traen datos.
			if(!this.getCodigosDeHijosActivos(getCtx(), get_TrxName(), molde).equals("") ){
				
				// Obtengo la suma de todos los consumos y los comparo con el importe del totalizador.
				con = this.getConnection();
				Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

				SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
			
				
				String sql = " SELECT isnull(sum(impCon),0) as importe" +
					         " FROM " + TABLA_CONSUMOS + 
					         " WHERE FchCie between '" + df.format(this.fechaInicio) + "' AND '" + df.format(this.fechaFin) + "' " +
					         " AND Gru ='" + molde.getString("GrupCCValue") + "'" +
					         " AND CodProMar ='" + molde.getString("ProdValue") + "'" +
					         " AND CodAFF ='" + molde.getString("AffValue") + "'" +
					         " AND ConCC in(" + this.getTodosLosCodigosDeHijos(getCtx(), get_TrxName(), molde) + ")" +				         
					         (molde.getInt("C_Currency_ID") == 142 ? " AND Mon = '858'" :  " AND Mon = '840'") +
					         " AND suc ='" + molde.getString("sucvalue") + "'";
					        

				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					
					if(molde.getInt("C_Currency_ID") == 142){
						if(rs.getBigDecimal("importe").compareTo(saldomn) == 0) retorno = true;					
					}else if(rs.getBigDecimal("importe").compareTo(saldome) == 0) retorno = true;				
				}
				
				rs.close();
				con.close();

				
			}			

		} catch (Exception e) {

			throw new AdempiereException(e + " consum validations");

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
				MFduLoadLine line = new MFduLoadLine(getCtx(), 0, get_TrxName());
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
				
				line.setUY_Fdu_GrupoCC_ID(molde.getGrupocc_id());
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
						" AND uy_fducod.isactive = 'Y' AND uy_fdu_currency.isactive = 'Y'" + 
						" AND uy_fdu_grupocc.isactive = 'Y' AND uy_fdu_productos.isactive = 'Y' AND uy_fdu_afinidad.isactive = 'Y' AND uy_fdu_journaltype.isactive = 'Y' AND uy_fdu_sucursal.isactive = 'Y'";
				
			}else if(this.uyFduFileID == 1000010){
				
			}else if(this.uyFduFileID == 1000011){
				
				action = "INSERT INTO " + TABLA_MOLDE_CL750 + "(uy_fducod_id, value, uy_fdu_journaltype_id, c_elementvalue_id," +
						" c_elementvalue_id_cr, c_currency_id, curvalue, uy_fdu_grupocc_id, grupccvalue, sucvalue, ccdepartamento, prodvalue, ccproducto, affvalue, ccafinidad, ad_user_id) " +
						" SELECT DISTINCT uy_fducod.uy_fducod_id, uy_fducod.value, uy_fdu_journaltype.uy_fdu_journaltype_id," +
						" uy_fdu_journaltype.c_elementvalue_id,uy_fdu_journaltype.c_elementvalue_id_cr,  uy_fdu_currency.c_currency_id," +
						" uy_fdu_currency.value as curvalue, uy_fdu_grupocc.uy_fdu_grupocc_id, uy_fdu_grupocc.value as grupccvalue, uy_fdu_sucursal.value as sucvalue," + 
						" uy_fdu_sucursal.c_activity_id as ccdepartamento,  uy_fdu_productos.value as prodvalue, uy_fdu_productos.c_activity_id as ccproducto," +
						" uy_fdu_afinidad.value as affvalue, uy_fdu_afinidad.c_activity_id as ccafinidad," + this.user +  
						" FROM uy_fducod, uy_fdu_currency, uy_fdu_grupocc, uy_fdu_productos, uy_fdu_afinidad,  uy_fdu_journaltype, uy_fdu_sucursal, uy_fdufile" +
						" WHERE uy_fdufile.uy_fdufile_id = " + this.uyFduFileID + " AND uy_fdufile.uy_fdufile_id = uy_fducod.uy_fdufile_id" +
						" AND uy_fducod.uy_fducod_id = uy_fdu_journaltype.uy_fducod_id AND uy_fdu_productos.uy_fdu_productos_id = uy_fdu_afinidad.uy_fdu_productos_id" +
						" AND uy_fducod.isactive = 'Y' AND uy_fdu_currency.isactive = 'Y'" + 
						" AND uy_fdu_grupocc.isactive = 'Y' AND uy_fdu_productos.isactive = 'Y' AND uy_fdu_afinidad.isactive = 'Y' AND uy_fdu_journaltype.isactive = 'Y' AND uy_fdu_sucursal.isactive = 'Y'";
				
			}else if(this.uyFduFileID == 1000012){
				
				action = "INSERT INTO " + TABLA_MOLDE_CC500 + "(uy_fducod_id, value, uy_fdu_journaltype_id, c_elementvalue_id," +
						" c_elementvalue_id_cr, c_currency_id, curvalue, uy_fdu_grupocc_id, grupccvalue, sucvalue, ccdepartamento, prodvalue, " +
						" ccproducto, affvalue, ccafinidad, ad_user_id, uy_fdu_store_id, storeValue)" +
						" SELECT DISTINCT uy_fducod.uy_fducod_id, uy_fducod.value, uy_fdu_journaltype.uy_fdu_journaltype_id," +
						" uy_fdu_journaltype.c_elementvalue_id,uy_fdu_journaltype.c_elementvalue_id_cr,  uy_fdu_currency.c_currency_id," +
						" uy_fdu_currency.value as curvalue, uy_fdu_grupocc.uy_fdu_grupocc_id, uy_fdu_grupocc.value as grupccvalue, uy_fdu_sucursal.value as sucvalue," + 
						" uy_fdu_sucursal.c_activity_id as ccdepartamento,  uy_fdu_productos.value as prodvalue, uy_fdu_productos.c_activity_id as ccproducto," +
						" uy_fdu_afinidad.value as affvalue, uy_fdu_afinidad.c_activity_id as ccafinidad," + this.user +  ", uy_fdu_store.uy_fdu_store_id, uy_fdu_store.value" + 
						" FROM uy_fducod, uy_fdu_currency, uy_fdu_grupocc, uy_fdu_productos, uy_fdu_afinidad,  uy_fdu_journaltype, uy_fdu_sucursal, uy_fdufile, uy_fdu_store" +
						" WHERE uy_fdufile.uy_fdufile_id = " + this.uyFduFileID + " AND uy_fdufile.uy_fdufile_id = uy_fducod.uy_fdufile_id" +
						" AND uy_fducod.uy_fducod_id = uy_fdu_journaltype.uy_fducod_id AND uy_fdu_productos.uy_fdu_productos_id = uy_fdu_afinidad.uy_fdu_productos_id" +
						" AND uy_fducod.isactive = 'Y' AND uy_fdu_currency.isactive = 'Y'" + 
						" AND uy_fdu_grupocc.isactive = 'Y' AND uy_fdu_productos.isactive = 'Y' AND uy_fdu_afinidad.isactive = 'Y' AND uy_fdu_journaltype.isactive = 'Y' AND uy_fdu_sucursal.isactive = 'Y' AND uy_fdu_store.isactive = 'Y'";
				
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
						" WHERE IsTotalizador='N' AND isactive='Y'" +
						" AND parentValue='" +  moldeTotalizador.getString("Value") + "'" ;		

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
	
	public String getTodosLosCodigosDeHijos(Properties ctx, String trxName, ResultSet moldeTotalizador) {
		
		String retorno = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {		
			
			
			String sql =" SELECT distinct value" +
						" FROM UY_FduCod" +
						" WHERE IsTotalizador='N'" +
						" AND parentValue='" +  moldeTotalizador.getString("Value") + "'" ;			  
			

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
}
