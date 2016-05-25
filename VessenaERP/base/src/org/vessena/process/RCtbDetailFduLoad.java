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
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MFduAfinidad;
import org.openup.model.MFduCod;
import org.openup.model.MFduConnectionData;
import org.openup.model.MFduGrupoCC;
import org.openup.model.MFduProductos;
import org.openup.model.MFduSucursal;


public class RCtbDetailFduLoad extends SvrProcess {
	
	private static String TABLA_MOLDE= "UY_Molde_DetailFduLoad";
	
	private static String TABLA_TOTALIZADORES = "T_FDU_CC108D_DETALLE_T";
	private static String TABLA_CONSUMOS = "T_FDU_CC108D_DETALLE_C";
	private static String TABLA_AJUSTES = "T_FDU_CC108D_DETALLE_A";	
	
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;	
	private String idReporte = "";
	
	private int uy_fduFile_id = 0;
	private String tablaSQlServer = "";
	private int UY_FduCod_ID = 0;
	private Timestamp dateAcct = null;
	private int grupocc = 0;
	private int c_currency_id = 0;
	private int c_activity_id_1 = 0;
	private int c_activity_id_2 = 0;
	private int c_activity_id_3 = 0;
	

	public RCtbDetailFduLoad() {
		
	}

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if(para[i].getParameter() != null) {
					
					if (name.equalsIgnoreCase("UY_FduFile_ID")) {
						this.uy_fduFile_id = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
					if (name.equalsIgnoreCase("FduTable")) {
						this.tablaSQlServer = para[i].getParameter().toString();
					}
					if (name.equalsIgnoreCase("UY_FduCod_ID")) {
						this.UY_FduCod_ID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}	
					if (name.equalsIgnoreCase("DateAcct")) {
						this.dateAcct = (Timestamp) para[i].getParameter();
					}
					if (name.equalsIgnoreCase("C_Currency_ID")) {
						this.c_currency_id = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
					if (name.equalsIgnoreCase("UY_Fdu_GrupoCC_ID")) {
						this.grupocc = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
					if (name.equalsIgnoreCase("UY_Fdu_Sucursal_ID")) {
						this.c_activity_id_1 = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
					if (name.equalsIgnoreCase("UY_Fdu_Productos_ID")) {
						this.c_activity_id_2 = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
					if (name.equalsIgnoreCase("UY_Fdu_Afinidad_ID")) {
						this.c_activity_id_3 = ((BigDecimal) para[i].getParameter()).intValueExact();
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
		
		
	}

	@Override
	protected String doIt() throws Exception {
		
		if(this.uy_fduFile_id == 1000008){
			// Delete de reportes anteriores de este usuario para ir limpiando la
			// tabla molde
			this.showHelp("Eliminando datos anteriores");
			this.deleteInstanciasViejasReporte();

			// Obtengo y cargo en tabla molde, los movimientos segun filtro indicado
			// por el usuario.
			this.showHelp("Cargando registros");
			this.execute();	

			this.showHelp("Iniciando Vista Previa");

			return "ok";
			
		}else{
			throw new AdempiereException("Reporte habilitado solo para Cierre de Cuenta Corriente CC108D");
		}				
		
	}
	
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE Ad_User_Id = " + this.adUserID;
			
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	
	private Connection getConnection() throws Exception {
		
		Connection retorno = null;

		String connectString = ""; //"jdbc:sqlserver://192.168.150.175\\FP01;databaseName=FDU;user=adempiere;password=adempiere1144";
		//String user = "adempiere";
		//String password = "adempiere1144";

		try {

			MFduConnectionData conn = MFduConnectionData.forFduFileID(getCtx(), this.uy_fduFile_id, null);
			
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
		Connection con = null;
		ResultSet rs = null;
		
		try {

			// Obtengo datos desde tabla de Consumos.
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");		
				
			String sqlTotalizadores = " SELECT FchCie as fecha, Gru as grupo, CodProMar as producto, CodAFF as afinidad, suc as sucursal, ConDet as codigo, ' ' as moneda, ImpPes as importePesos, ImpDol as importeDolares" +
									  " FROM " + TABLA_TOTALIZADORES +
									  " WHERE FchCie ='" + df.format(this.dateAcct) + "'" +									
									  this.whereCodigos("ConDet") +
									  " AND Gru ='" + new MFduGrupoCC(getCtx(), this.grupocc, null).getValue() + "'" +
									  " AND suc ='" + new MFduSucursal(getCtx(), this.c_activity_id_1, null).getValue() + "'" +
									  " AND CodProMar ='" + new MFduProductos(getCtx(), this.c_activity_id_2, null).getValue() + "'" +
									  " AND CodAFF ='" + new MFduAfinidad(getCtx(), c_activity_id_3, null).getValue() + "'";    
			
			String sqlConsumos  = " SELECT FchCie as fecha, Gru as grupo, CodProMar as producto, CodAFF as afinidad, suc as sucursal, ConCC as codigo, Mon as moneda, impCon as importe" +
			         			  " FROM " + TABLA_CONSUMOS +
			         			  " WHERE FchCie ='" + df.format(this.dateAcct) + "'" +
			         			  this.whereCodigos("ConCC") +
			         			  " AND Gru ='" + new MFduGrupoCC(getCtx(), this.grupocc, null).getValue() + "'" +
			         			  " AND suc ='" + new MFduSucursal(getCtx(), this.c_activity_id_1, null).getValue() + "'" +
			         			  " AND CodProMar ='" + new MFduProductos(getCtx(), this.c_activity_id_2, null).getValue() + "'" +
			         			  " AND CodAFF ='" + new MFduAfinidad(getCtx(), c_activity_id_3, null).getValue() + "'" +			         			
			         			  (this.c_currency_id == 142 ? " AND Mon = '858'" :  " AND Mon = '840'");	        
			         			
			        
			
			String sqlAjustes = " SELECT FchCie as fecha, Gru as grupo, CodProMar as producto, CodAFF as afinidad, suc as sucursal, CanPesConAju as codigo, codMonAju as moneda,  impPes as importe" +
			         		    " FROM " + TABLA_AJUSTES + 
			         		    " WHERE FchCie ='" + df.format(this.dateAcct) + "'" +
			         		    this.whereCodigos("CanPesConAju") +
			         			" AND Gru ='" + new MFduGrupoCC(getCtx(), this.grupocc, null).getValue() + "'" +
								" AND suc ='" + new MFduSucursal(getCtx(), this.c_activity_id_1, null).getValue() + "'" +
								" AND CodProMar ='" + new MFduProductos(getCtx(), this.c_activity_id_2, null).getValue() + "'" +
								" AND CodAFF ='" + new MFduAfinidad(getCtx(), c_activity_id_3, null).getValue() + "'" +     			
			         		    (this.c_currency_id == 142 ? " AND codMonAju = '858'" :  " AND codMonAju = '840'");				       
			         		  
				   
			
			if(this.tablaSQlServer.equals("TT")) rs = stmt.executeQuery(sqlTotalizadores);
			else if(this.tablaSQlServer.equals("CN")) rs = stmt.executeQuery(sqlConsumos);
			else if(this.tablaSQlServer.equals("AJ")) rs = stmt.executeQuery(sqlAjustes);
			
			while (rs.next()) {		
				
				this.loadMovimientos(rs);
				
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
	
	private void loadMovimientos(ResultSet rs){
		
		String insert = "";
		
		try {			
			
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, ad_user_id, idreporte, fecreporte, datetrx, grupocc, producto, " +
					"afinidad, sucursal, codigo, moneda, importePesos, importeDolares)" + 
					" values(" +
					this.adClientID + "," + this.adOrgID + "," + this.adUserID + ",'" + this.idReporte + "', current_date" + ",'" +
					rs.getTimestamp("fecha") + "','" + rs.getString("grupo") + "','" + rs.getString("producto") + "','" + rs.getString("afinidad") +
					"','" + rs.getString("sucursal") + "','" + rs.getString("codigo") + "','" + rs.getString("moneda") + "',";
					if(rs.getString("moneda").equals(" ")){
						insert += rs.getString("importePesos") + "," + rs.getString("importeDolares");
					}else if(rs.getString("moneda").equals("858")) {
						insert += rs.getString("importe") + "," + 0;
					}else{
						insert += 0 + "," + rs.getString("importe");
					}
					insert += ")";
			
			
			log.log(Level.INFO, insert);
			DB.executeUpdateEx(insert, get_TrxName());			
			
		} catch (Exception e) {
			log.log(Level.SEVERE, insert, e);
			throw new AdempiereException(e);

		}	
	}
	
	private String whereCodigos(String campoCodigo){
		String retorno = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			
			if(this.UY_FduCod_ID == 0){
				
				String sql =" SELECT value FROM uy_fducod WHERE calculatetype = '" + this.tablaSQlServer + "'";
				
				pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
				rs = pstmt.executeQuery ();
				
				retorno = " AND " + campoCodigo + " in (";

				while (rs.next()) {
					
					if(rs.isLast()) retorno += "'" + rs.getString("value") + "'";
					else retorno += "'" + rs.getString("value")  + "',";
					
				}
				
				retorno += ")";
			}else{
				retorno = " AND " + campoCodigo + " = '" + new MFduCod(getCtx(), this.UY_FduCod_ID, null).getValue() + "'";
			}
			
		} catch (Exception e) {

			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return retorno;	
	}
	
	
	private void showHelp(String message){
		
		if (this.getProcessInfo().getWaiting() != null)
			this.getProcessInfo().getWaiting().setText(message);
		
	}


}
