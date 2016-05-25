/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/01/2013
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.AWindow;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MFDUControlProcess
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 15/01/2013
 * @see
 */
public class MFDUControlProcess extends X_UY_FDU_ControlProcess {

	private static final long serialVersionUID = 847725253524908713L;
	private String processMsg = null;
	private AWindow window = null;
	MFduConnectionData fduData = null;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_FDU_ControlProcess_ID
	 * @param trxName
	 */
	public MFDUControlProcess(Properties ctx, int UY_FDU_ControlProcess_ID,
			String trxName) {
		super(ctx, UY_FDU_ControlProcess_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFDUControlProcess(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public AWindow getWindow() {
		return this.window;
	}

	public void setWindow(AWindow value) {
		this.window = value;
	}
	
	public String getProcessMsg() {
		return this.processMsg;
	}
	
	/***
	 * Ejecuta controles de generacion de cuenta corriente.
	 * OpenUp Ltda. Issue #133 
	 * @author Gabriel Vila - 15/01/2013
	 * @see
	 * @param reProcessing
	 */
	public void execute(boolean reProcessing){
		
		try{
		
			this.fduData = new MFduConnectionData(getCtx(), 1000010, null);
			
			// Obtengo lista de controles a ejecutar ordenados por secuencia
			// Esta lista depende del tipo de control
			MFDUControlType type = (MFDUControlType)this.getUY_FDU_ControlType();
			
			List<MFDUControlTypeLine> typelines = type.getLines();
			
			if (typelines.size() <= 0){
				this.processMsg = "El Tipo de Control seleccionado no tiene controles definidos para ejecutar.";
				return;
			}
			
			// Elimina datos anteriores de esta ejecucion
			this.deleteOldData(reProcessing);
			
			// Cargo valores de variables globales para la ejecucion de formulas de conceptos
			HashMap<String, Object> globalVars = new HashMap<String, Object>();
			globalVars = this.setGlobalVars();
			
			for (MFDUControlTypeLine typeline: typelines){
				
				globalVars.put("control_id", new Integer(typeline.getUY_FDU_Control_ID()));

				MFDUControl control = (MFDUControl)typeline.getUY_FDU_Control();
				
				this.executeControl(control, globalVars, this.fduData);
				
			}
			
			this.processMsg = "Proceso Finalizado.";
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Ejecucion de calculo de validacion de un determinado control.
	 * OpenUp Ltda. Issue #133 
	 * @author Gabriel Vila - 15/01/2013
	 * @see
	 * @param control
	 * @param globalVars
	 */
	private void executeControl(MFDUControl control, HashMap<String, Object> globalVars, MFduConnectionData fduData) {

		MFDUControlResult result = null;
		long startTime = 0;
		
		try{
		
			// Instancio modelo de resultado de validacion
			result = new MFDUControlResult(getCtx(), 0, null);
			result.setUY_FDU_ControlProcess_ID(this.get_ID());
			result.setUY_FDU_Control_ID(control.get_ID());
			result.saveEx();

			startTime = System.currentTimeMillis();
			
			// Ejecuto validacion de este control. Si ejecuta bien, recibe true y si ejecuta mal recibe false.
			boolean isvalid = control.evaluate(globalVars, fduData);
			
			result.setWorkingTime(new Time(System.currentTimeMillis() - startTime).toString().substring(3));
			result.setcodigo(control.getFormulaClean());
			result.setSuccess(isvalid);
			
			// Si el resultado no es valido, busco detalles en base fdu y las cargo en base adempiere			
			if (!isvalid){
				this.setResultDetails(result, control);
			}
			
			
			result.saveEx();
		}
		catch (Exception ex){
			if (result != null){
				// Guardo resultado como no exitoso
				result.setSuccess(false);
				result.setMessage(ex.getMessage().replaceAll("org.postgresql.util.PSQLException: ERROR:", ""));
				result.saveEx();
			}
			else{
				throw new AdempiereException(ex.getMessage());	
			}			
		}		
	}

	
	/***
	 * Obtiene detalle de control no valido desde base FDU y lo guarda en base de adempiere.
	 * OpenUp Ltda. Issue #133 
	 * @author Gabriel Vila - 16/01/2013
	 * @see
	 * @param result
	 * @param control
	 */
	private void setResultDetails(MFDUControlResult result, MFDUControl control) {

		Connection con = null;
		ResultSet rs = null;
		
		try{
			
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select * from " + fduData.getSchema().trim() + ".UY_Fdu_Ctrl_Errors " +
					     " where run_id =" + this.get_ID() +
					     " and control_id =" + control.get_ID() +
					     " order by UY_Fdu_Ctrl_Errors_ID ";
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()){
				
				MFDUControlResultDetail det = new MFDUControlResultDetail(getCtx(), 0, null);
				det.setUY_FDU_ControlResult_ID(result.get_ID());
				det.setUY_FDU_Control_ID(control.get_ID());
				det.setSuccess(false);
				det.setMessage(rs.getString("MessageError"));
				det.setDateTrx(rs.getTimestamp("DeadLine"));
				det.setDeadLinePrevious(rs.getTimestamp("DeadLinePrevious"));
				det.setAccount(rs.getString("Account"));				
				det.setSaldoInicialPesos(rs.getBigDecimal("SaldoInicialPesos"));
				det.setSaldoInicialDolares(rs.getBigDecimal("SaldoInicialDolares"));
				det.setSaldoFinalPesos(rs.getBigDecimal("SaldoFinalPesos"));
				det.setSaldoFinalDolares(rs.getBigDecimal("SaldoFinalDolares"));
				det.setDiferenciaPesos(rs.getBigDecimal("DifferencePesos"));
				det.setDiferenciaDolares(rs.getBigDecimal("DifferenceDolares"));
				det.setAgencia(rs.getString("AgenciaCobranza"));				
				det.setSeqNo(rs.getInt("UY_Fdu_Ctrl_Errors_ID"));
				det.setEjecutor(rs.getString("Ejecutor"));
				det.setAutorizador(rs.getString("Autorizador"));				
				det.setcomercio(rs.getString("Comercio"));				
				det.setCupon(rs.getString("Cupon"));
				if(rs.getString("Producto")!= null && !rs.getString("Producto").equals("")){
					int UY_Fdu_Productos_ID = MFduProductos.getMFduProductosForValue(this.getCtx(), rs.getString("Producto")).get_ID();
					det.setUY_Fdu_Productos_ID(UY_Fdu_Productos_ID);					
				}
				if(rs.getString("Afinidad") != null && !rs.getString("Afinidad").equals("")){
					int UY_Fdu_Afinidad_ID = MFduAfinidad.getMFduAfinidadForValue(this.getCtx(), rs.getString("Afinidad")).get_ID();
					det.setUY_Fdu_Afinidad_ID(UY_Fdu_Afinidad_ID);					
				}
				det.setCuotas(rs.getString("Cuotas"));
				det.setMontoCuota(rs.getBigDecimal("MontoCuota"));
				det.setCoefAdempiere(rs.getBigDecimal("CoefAdempiere"));
				det.setInteresAdempiere(rs.getBigDecimal("InteresAdempiere"));
				det.setInteresFinancial(rs.getBigDecimal("InteresFinancial"));
				
				
				det.saveEx();
			}
			
			rs.close();
			con.close();		
			
		}	
		catch (Exception e) {
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

	/***
	 * Elimina resultados anteriores de esta ejecucion.
	 * OpenUp Ltda. Issue #133 
	 * @author Hp - 15/01/2013
	 * @see
	 * @param reProcessing
	 */
	private void deleteOldData(boolean reProcessing) {
		
		try{
			
			String whereProcessing = "";
			
			if (reProcessing){
				whereProcessing = " AND reprocessing ='Y' ";
			}
							
			DB.executeUpdateEx(" DELETE FROM uy_fdu_controlresultdetail " +
					           " WHERE uy_fdu_controlresult_id IN " +
					           "(SELECT uy_fdu_controlresult_id FROM uy_fdu_controlresult " +
					           " WHERE uy_fdu_controlprocess_id =" + this.get_ID() + whereProcessing + ")", null);

			DB.executeUpdateEx("DELETE FROM uy_fdu_controlresult WHERE uy_fdu_controlprocess_id=" + this.get_ID() + whereProcessing, null);
			
			this.deleteFDUErrors();
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
	}
	
	/**
	 * Carga valores de variables globales para el proceso de formulas de controles.
	 * OpenUp Ltda. Issue #133
	 * @author Hp - 15/01/2013
	 * @see
	 * @return
	 */
	private HashMap<String, Object> setGlobalVars() {
		
		HashMap<String, Object> vars = new HashMap<String, Object>();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		
		vars.put("control_id", new Integer(0));
		vars.put("run_id", new Integer(this.get_ID()));
		vars.put("fechaDesde", "'" + df.format(this.getStartDate()) + "'");
		vars.put("fechaHasta", "'" + df.format(this.getEndDate()) + "'");
		
		BigDecimal diff = this.getDifferenceAmt();
		if (diff == null) diff = Env.ZERO;
		
		vars.put("difference", diff);
		
		return vars;
	}

	/***
	 * Elimina detalle de errores previos generados en este proceso.
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 16/01/2013
	 * @see
	 */
	private void deleteFDUErrors(){
		
		Connection con = null;
		Statement stmt = null;
		
		try{
			con = this.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " delete from " + fduData.getSchema().trim() + ".UY_Fdu_Ctrl_Errors " +
					     " where run_id =" + this.get_ID();
			
			stmt.execute(sql);
			stmt.close();

			con.close();		
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {
			
			if (con != null){
				try {
					if (stmt != null){
						if (!stmt.isClosed()) stmt.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}

		
	}
	
	
	/***
	 * Obtiene conexion a base sql server.
	 * OpenUp Ltda. Issue #133 
	 * @author Guillermo Brust - 15/01/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		
		Connection retorno = null;

		String connectString = ""; 

		try {
			if(this.fduData != null){
				
				connectString = "jdbc:sqlserver://" + this.fduData.getserver_ip() + "\\" + this.fduData.getServer() + 
								";databaseName=" + this.fduData.getdatabase_name() + ";user=" + this.fduData.getuser_db() + 
								";password=" + this.fduData.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, this.fduData.getuser_db(), this.fduData.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}

}
