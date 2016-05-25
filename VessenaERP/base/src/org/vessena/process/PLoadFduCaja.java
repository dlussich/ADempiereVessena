package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MActivity;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MElementValue;
import org.compiere.model.MGLCategory;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTax;
import org.compiere.model.X_GL_Category;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MFduCajaRef;
import org.openup.model.MFduCajaType;
import org.openup.model.MFduCajaTypeLine;
import org.openup.model.MFduConnectionData;
import org.openup.model.MFduFile;
import org.openup.model.MFduLoad;
import org.openup.model.MFduSucursal;
import org.openup.model.MMoldeFduCaja;

/**
 * OpenUp. PLoadFduFile Descripcion : Obtención de datos de base de datos SQL
 * SERVER de Italcred, para realizar asientos contables.
 * 
 * @author Leonardo Boccone ISSUE #1932 - Version 2.5.1 Fecha : 05/03/2014
 */
public class PLoadFduCaja extends SvrProcess {

	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private static String TABLA_MOLDE = "UY_Molde_Fdu_Caja";
	private static String TABLA_TYPE = "UY_Fdu_Caja_Type";
	//private HashMap<String, RefBeanModelo> map = new HashMap<String, RefBeanModelo>();
	List<MJournalLine> journalLinesPesos = new ArrayList<MJournalLine>();
	List<MJournalLine> journalLinesDolares = new ArrayList<MJournalLine>();
	List<MJournalLine> journalLinesPesosA = new ArrayList<MJournalLine>();
	List<MJournalLine> journalLinesDolaresA = new ArrayList<MJournalLine>();
	List<MMoldeFduCaja> moldeAux = new ArrayList<MMoldeFduCaja>();
	List<MActivity> activityAux = new ArrayList<MActivity>();
	private int uyFduFileID = 1000016;
	MFduLoad load;

	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("StartDate")) {
					this.fechaInicio = (Timestamp) para[i].getParameter();
					this.fechaFin = (Timestamp) para[i].getParameter_To();
				}
			}
		}

	}

	@Override
	protected String doIt() throws Exception {

		this.deleteInstanciasViejasReporte();

		this.execute();

		this.ChargeCurrencyRate();

		//this.changeRefType();

		this.ChargeforType();

		this.loadData();

		// this.sendNotification();

		return "OK";
	}

	/**
	 * Tomo todas las lineas de la tabla molde y las sumo segun su clasificacion
	 * OpenUp Ltda. Issue #1932
	 * 
	 * @author Leonardo Boccone - 18/03/2014
	 * @see
	 */
	private void ChargeforType() {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		sql = "SELECT * FROM "
				+ TABLA_MOLDE
				+ " m"
				+ " INNER JOIN "
				+ TABLA_TYPE
				+ " t on m.uy_fdu_caja_type_id=t.uy_fdu_caja_type_id"
				+ " WHERE m.Fecha>= '"
				+ this.fechaInicio
				+ "' AND  m.Fecha<= '"
				+ this.fechaFin + "'"
				+ "ORDER BY m.Fecha";
		try {
			pstmt = DB.prepareStatement(sql, null);	
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MMoldeFduCaja molde = new MMoldeFduCaja(getCtx(),
						rs.getInt("UY_Molde_Fdu_Caja_Id"), null);

				boolean agrego = true;
				if (!moldeAux.isEmpty()) {
					for (MMoldeFduCaja m : moldeAux) {

						MActivity activity = new MActivity(getCtx(),molde.getC_Activity_ID(), null);
						if (!activityAux.contains(activity)) {
							activityAux.add(activity);
						}

						if (molde.getUY_Fdu_Caja_Type_ID() == m.getUY_Fdu_Caja_Type_ID() && molde.getC_Activity_ID() == m.getC_Activity_ID()&& molde.getC_Currency_ID() == m.getC_Currency_ID() && molde.getfecha().equals(m.getfecha())) {
						
								m.setentrada(m.getentrada().add(molde.getentrada()));
								m.setsalida(m.getsalida().add(molde.getsalida()));
								agrego = false;
								break;

							
						}

					}
					if (agrego) {
						moldeAux.add(molde);
					}

				} else if (rs.isFirst()) {
					moldeAux.add(molde);
				}

			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally 
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	/**
	 * Cuando falta el tc en algun movimiento busco alguno del mismo dia y de la
	 * misma sucursal y lo ingreso, si no lo encuentro tiro exception. OpenUp
	 * Ltda. Issue #1932
	 * 
	 * @author Leonardo Boccone - 14/03/2014
	 * @see
	 */
	private void ChargeCurrencyRate() {
		List<MMoldeFduCaja> chargeRate = new ArrayList<MMoldeFduCaja>();
		List<MMoldeFduCaja> tc = new ArrayList<MMoldeFduCaja>();
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		sql = "SELECT * FROM " + TABLA_MOLDE + " WHERE c_Currency_id=100";

		try {
			pstmt = DB.prepareStatement(sql, null);	
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MMoldeFduCaja molde = new MMoldeFduCaja(getCtx(),
						rs.getInt("UY_Molde_Fdu_Caja_Id"), null);
				if (molde.gettc().compareTo(Env.ZERO) == 0 && rs.isFirst()) {
					chargeRate.add(molde);
				} else if (molde.gettc().compareTo(Env.ZERO) == 0) {
					for (MMoldeFduCaja t : tc) {
						if (molde.getC_Activity_ID() == t.getC_Activity_ID()
								&& molde.getfecha().equals(t.getfecha())) {
							molde.settc(t.gettc());
							if (chargeRate.contains(molde)) {
								chargeRate.remove(molde);
							}
							molde.saveEx();
							break;
						}

					}
					if (!chargeRate.contains(molde)) {
						chargeRate.add(molde);
					}

				} else {
					tc.add(molde);
				}
				if (rs.isLast()) {
					if (!chargeRate.isEmpty()) {
						if (!tc.isEmpty()) {
							for (MMoldeFduCaja c : chargeRate) {
								for (MMoldeFduCaja t : tc) {
									if (c.getC_Activity_ID() == t
											.getC_Activity_ID()
											&& c.getfecha()
													.equals(t.getfecha())) {
										c.settc(t.gettc());
										c.saveEx();
										break;
									}
								}

							}

						} else {
							throw new AdempiereException(
									"No se puede cargar TC no se encuentra ninguno de la misma fecha y sucursal");
						}
					}
				}
			}
			for (MMoldeFduCaja c : chargeRate) {
				if (c.gettc().compareTo(Env.ZERO) == 0) {
					throw new AdempiereException(
							"No se puede cargar TC no se encuentra ninguno de la misma fecha y sucursal");
				}
			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	/**
	 * 
	 * OpenUp Ltda. Issue # 1932 Limpiamos la tabla molde de los datos
	 * anteriores
	 * 
	 * @author Leonardo Boccone - 12/03/2014
	 * @see
	 */

	private void deleteInstanciasViejasReporte() {

		// limpiamos la tabla molde de sus datos anteriores
		String sql = "";

		try {
			this.showHelp("Eliminando datos anteriores...");
			sql = "TRUNCATE " + TABLA_MOLDE;
			DB.executeUpdateEx(sql, null);
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/***
	 * 
	 * OpenUp Ltda. Issue #1932 Tomamos los datos de los movimientos de caha de
	 * una consulta sql y los calificamos segun las referencias parametrizadas
	 * guardamos los movimientos en una tabla molde
	 * 
	 * @author Leonardo Boccone - 10/03/2014
	 * @see
	 */
	public void execute() {

		String sql = "";
		Connection con = null;
		ResultSet rs = null;

		try {

			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM");

			sql = "SELECT q_cajas.num_sucursal, q_cajas.sucursal, q_cajas.CajaNro, q_cajas.Fecha, q_cajas.E_S, q_cajas.usuario, q_cajas.moneda, "
					+ " q_cajas.entrada, q_cajas.salida, q_cajas.TC, q_cajas.codigo_predef, q_cajas.automatico, q_cajas.ref_original, q_cajas.ref_tipo, q_cajas.ref_numero, q_cajas.referencia, q_cajas.tipo_operacion, q_cajas.codigo_operacion"
					+ " FROM FinancialPro.dbo.q_cajas q_cajas"
					+ " WHERE q_cajas.Fecha>= '"
					+ df.format(this.fechaInicio)
					+ "' AND  q_cajas.Fecha<='"
					+ df.format(this.fechaFin)
					+ "'" + " AND  q_cajas.num_sucursal <> 10";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String name = rs.getString("referencia");
				String namelower = name.toLowerCase();
				
				String cod = rs.getString("codigo_operacion");
				cod=cod.trim();
				int moneda = rs.getInt("moneda");
				if (moneda == 1) {
					moneda = 100;
				} else if (moneda == 0) {
					moneda = 142;
				} else {
					throw new AdempiereException("moneda no existe");
				}
				MFduCajaRef ref = MFduCajaRef.forValue(this.getCtx(),cod,moneda, rs.getBigDecimal("entrada"),rs.getBigDecimal("salida"), this.get_TrxName());

				if (ref != null) {
					// Si un movimiento de clasificacion es de tipo compra vta
					// me hay que sacar el tc de la referencia
					BigDecimal tc = rs.getBigDecimal("tc");

					MFduCajaType cajatype = new MFduCajaType(getCtx(),
							ref.getUY_Fdu_Caja_Type_ID(), null);

					
					String blanco = " ";
					if (moneda == 100) {
						if(tc.compareTo(Env.ZERO)==0){
							if (namelower.contains(blanco + "tc" + blanco)) {
								String[] aux = namelower.split("tc");
								String sinespacios = aux[1].trim();
								String str = sinespacios.replaceAll("[^\\d.]", "");
								tc = new BigDecimal(str);
							}							
						}		
						
					/* Sylvie Bouissa ISSUE #3122  - 17-10-2014
					 * --> Se acuerda con natalia b. que:
					 * Siempre que la moneda es pesos uruguayos se setea TC = 1 independientemente del TC que viene de la consulta a financial.					 
					 */						
					}else if(moneda == 142){
						tc = (Env.ONE);
					}
					
					/*if (cajatype.getName().equalsIgnoreCase("cobranza anulada")
							|| cajatype.getName().equalsIgnoreCase(
									"prestamo anulado")) {
						RefBeanModelo bean = new RefBeanModelo(
								rs.getTimestamp("fecha"),
								rs.getBigDecimal("entrada"),
								rs.getBigDecimal("salida"), moneda,
								ref.getUY_Fdu_Caja_Type_ID(),
								+ref.getUY_Fdu_Caja_Ref_ID(),
								rs.getString("referencia"));
						map.put(bean.getKey(), bean);
					}*/

					MMoldeFduCaja model = new MMoldeFduCaja(getCtx(), 0, null);
					int num_ccosto = rs.getInt("num_sucursal");
					Formatter fmt = new Formatter();
					fmt.format("%03d", num_ccosto);

					MFduSucursal ccosto = MFduSucursal.forValue(getCtx(), fmt,
							null);
					model.setC_Activity_ID(ccosto.getC_Activity_ID());

					model.setfecha(rs.getTimestamp("fecha"));
					model.setentrada(rs.getBigDecimal("entrada"));
					model.setsalida(rs.getBigDecimal("salida"));
					model.settc(tc);				
					model.setC_Currency_ID(moneda);					
					model.setUY_Fdu_Caja_Type_ID(ref.getUY_Fdu_Caja_Type_ID());
					model.setUY_Fdu_Caja_Ref_ID(ref.getUY_Fdu_Caja_Ref_ID());
					model.setreferencia(rs.getString("referencia"));

					if (!cajatype.getName().equalsIgnoreCase(
							"Movimientos Internos")) {
						model.saveEx();
					}

				} else {
					throw new AdempiereException(cod
							+ "no existe la referencia en el sistema");
				}
			}

			rs.close();
			con.close();

		} catch (Exception e) {

			throw new AdempiereException(e);

		} finally {

			if (con != null) {
				try {
					if (rs != null) {
						if (!rs.isClosed())
							rs.close();
					}
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
	}

	/***
	 * Cargo los movimientos de la tabla molde para comparalos con los del
	 * HashMap y cambiar los anulados que faltan OpenUp Ltda. Issue # 1932
	 * 
	 * @author Leonardo Boccone - 11/03/2014
	 * @see
	 */
	/*
	private void changeRefType() {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			sql = "SELECT * FROM " + TABLA_MOLDE + " m" + " INNER JOIN "
					+ TABLA_TYPE
					+ " t on m.uy_fdu_caja_type_id=t.uy_fdu_caja_type_id"
					+ " WHERE m.Fecha>= '" + this.fechaInicio
					+ "' AND  m.Fecha<='" + this.fechaFin
					+ "' AND t.name IN('Cobranzas','Ds Convenio','Prestamo')";

			pstmt = DB.prepareStatement(sql, null);	
			rs = pstmt.executeQuery();

			while (rs.next() && !map.isEmpty()) {
				MMoldeFduCaja model = new MMoldeFduCaja(getCtx(),
						rs.getInt("UY_Molde_Fdu_Caja_Id"), null);

				String key = model.getfecha().toString() + "_"
						+ model.getC_Currency_ID() + "_"
						+ model.getUY_Fdu_Caja_Ref_ID() + "_"
						+ model.getreferencia();
				;

				if (map.containsKey(key)) {

					RefBeanModelo aux = map.get(key);

					int idC = MFduCajaRef.IdTypeCaja(getCtx(), "Cobranzas",
							get_TrxName());
					int idD = MFduCajaRef.IdTypeCaja(getCtx(), "Ds Convenio",
							get_TrxName());
					int idP = MFduCajaRef.IdTypeCaja(getCtx(), "Prestamo",
							get_TrxName());
					int idPa = MFduCajaRef.IdTypeCaja(getCtx(),
							"Prestamo Anulado", get_TrxName());
					int idCa = MFduCajaRef.IdTypeCaja(getCtx(),
							"Cobranza Anulada", get_TrxName());

					BigDecimal entradamodelo = model.getentrada();
					BigDecimal salidamodelo = model.getsalida();
					BigDecimal entradaaux = aux.getEntrada();
					BigDecimal salidaaux = aux.getSalida();

					if ((model.getUY_Fdu_Caja_Type_ID() == idC || model.getUY_Fdu_Caja_Type_ID() == idD)&& (entradamodelo.compareTo(salidaaux)) == 0) {
						model.setUY_Fdu_Caja_Type_ID(idCa);
						model.saveEx();
						map.remove(key);
					} else if ((model.getUY_Fdu_Caja_Type_ID() == idP)
							&& (salidamodelo.compareTo(entradaaux)) == 0) {
						model.setUY_Fdu_Caja_Type_ID(idPa);
						model.saveEx();
						map.remove(key);
					}

				}
			}

		}

		catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
*/
	private Connection getConnection() throws Exception {

		Connection retorno = null;

		String connectString = "";
		try {

			MFduConnectionData conn = MFduConnectionData.forFduFileID(getCtx(),
					this.uyFduFileID, null);

			if (conn != null) {

				connectString = "jdbc:sqlserver://" + conn.getserver_ip()
						+ "\\" + conn.getServer() + ";databaseName="
						+ conn.getdatabase_name() + ";user="
						+ conn.getuser_db() + ";password="
						+ conn.getpassword_db();

				retorno = DriverManager.getConnection(connectString,
						conn.getuser_db(), conn.getpassword_db());
			}

		} catch (Exception e) {
			throw e;
		}

		return retorno;
	}

	/***
	 * Despliega avance en ventana splash OpenUp Ltda. Issue #60
	 * 
	 * @author Gabriel Vila - 29/10/2012
	 * @see
	 * @param text
	 */
	private void showHelp(String text) {
		if (this.getProcessInfo().getWaiting() != null) {
			this.getProcessInfo().getWaiting().setText(text);
		}
	}

	/***
	 * Envio de email con notificacion de fin de proceso. OpenUp Ltda. Issue #
	 * 
	 * @author Gabriel Vila - 27/12/2012
	 * @see
	 */
	/*
	 * private void sendNotification() {
	 * 
	 * try { MClient client = MClient.get(getCtx(), this.getAD_Client_ID());
	 * 
	 * client.sendEMail( 1003228,
	 * "Aviso Ejecucion Automatica Generacion de Asientos Archivo CC108.",
	 * "Proceso finalizado con Exito." + "\n" + "Numero : " +
	 * this.fduLoad.getDocumentNo() + "\n" + "Fecha Corrida : " +
	 * this.fduLoad.getUpdated(), null); } catch (Exception e) { // No hago nada
	 * en caso de error. }
	 * 
	 * }
	 */
	/***
	 * Tomamos las lineas molde guardadas en memoria y con esos datos generamos
	 * las lineas y cabezales . OpenUp Ltda. Issue #1932
	 * 
	 * @author Leonardo Boccone - 13/03/2014
	 * @see
	 */

	private void loadData() {

		Timestamp fechaAsiento = null, aux = null;
		BigDecimal totalDebePesos = Env.ZERO, totalHaberPesos = Env.ZERO, totalDebeDolares = Env.ZERO, totalHaberDolares = Env.ZERO; 
		BigDecimal totalDebePesosA = Env.ZERO, totalHaberPesosA = Env.ZERO, totalDebeDolaresA = Env.ZERO, totalHaberDolaresA = Env.ZERO;
		load = new MFduLoad(getCtx(),0, get_TrxName());	
		try {
			for (int i = 0; i < moldeAux.size(); i++) {

				boolean ultimo = false;
				if (i == moldeAux.size() - 1) {
					ultimo = true;
				}
				MMoldeFduCaja molde = moldeAux.get(i);
				fechaAsiento = molde.getfecha();
				if (i == 0) {
					aux = molde.getfecha();
				}
				if (!fechaAsiento.equals(aux)) {
					if(!journalLinesPesos.isEmpty()){
					this.generateSimpleJournal(aux, totalDebePesos,totalHaberPesos,journalLinesPesos,142,"Asiento caja diario Pesos");
					}
					if(!journalLinesDolares.isEmpty()){
					this.generateSimpleJournal(aux, totalDebeDolares,totalHaberDolares,journalLinesDolares,100,"Asiento caja diario Dolares");
					}
					if(!journalLinesPesosA.isEmpty()){
						this.generateSimpleJournal(aux, totalDebePesosA,totalHaberPesosA,journalLinesPesosA,142,"Asiento caja diario Pesos Anulado");
					}
					if(!journalLinesDolaresA.isEmpty()){
						this.generateSimpleJournal(aux, totalDebeDolaresA,totalHaberDolaresA,journalLinesDolaresA,100,"Asiento caja diario Doalres Anulado");
					}
					totalDebePesos = Env.ZERO;
					totalHaberPesos = Env.ZERO;
					totalDebeDolares = Env.ZERO;
					totalHaberDolares = Env.ZERO;
					totalDebePesosA = Env.ZERO;
					totalHaberPesosA = Env.ZERO;
					totalDebeDolaresA = Env.ZERO;
					totalHaberDolaresA = Env.ZERO;
					aux = fechaAsiento;
					this.journalLinesPesos = new ArrayList<MJournalLine>();
					this.journalLinesDolares = new ArrayList<MJournalLine>();
					this.journalLinesPesosA = new ArrayList<MJournalLine>();
					this.journalLinesDolaresA = new ArrayList<MJournalLine>();
				}

				// Creo todas las lineas para el asiento
				MJournalLine journalLine = new MJournalLine(getCtx(), 0, get_TrxName());
				MJournalLine journalLine2 = new MJournalLine(getCtx(), 0, get_TrxName());

				MFduCajaType cajaType = new MFduCajaType(getCtx(),molde.getUY_Fdu_Caja_Type_ID(), null);
				MFduCajaTypeLine linea = MFduCajaTypeLine.forC_Activity(getCtx(), cajaType.getUY_Fdu_Caja_Type_ID(),molde.getC_Activity_ID(), get_TrxName());

				journalLine.setC_Currency_ID(molde.getC_Currency_ID());
				journalLine2.setC_Currency_ID(molde.getC_Currency_ID());


				if(linea.isTotalizadorDEBE()){
					journalLine.setIsTotalizador(true);
				}
				journalLine.setC_ElementValue_ID(linea.getC_ElementValue_ID());										
				
				if(linea.isTotalizador()){
					journalLine2.setIsTotalizador(true);
				}
				journalLine2.setC_ElementValue_ID(linea.getc_elementvalue_id_2());	
				
				
				if(molde.getC_Currency_ID()==142){
					journalLine.setDescription(cajaType.getconcept_mn());
					journalLine2.setDescription(cajaType.getconcept_mn());
				}
				else if(molde.getC_Currency_ID()==100){
					journalLine.setDescription(cajaType.getconcept_usd());
					journalLine2.setDescription(cajaType.getconcept_usd());
				}
								
				journalLine.setC_Activity_ID_1(linea.getC_Activity_ID());
				journalLine2.setC_Activity_ID_1(linea.getC_Activity_ID());
				
				
				if(molde.getentrada().compareTo(Env.ZERO) == 0){
					journalLine.setAmtSourceDr(molde.getsalida());
					journalLine2.setAmtSourceCr(molde.getsalida());
				}
				else if(molde.getsalida().compareTo(Env.ZERO) == 0){
					journalLine.setAmtSourceDr(molde.getentrada());
					journalLine2.setAmtSourceCr(molde.getentrada());
				}
				
				journalLine.setAmtSourceCr(Env.ZERO);
				journalLine2.setAmtSourceDr(Env.ZERO);
				
				//Excepcion para Pago Anticipado
				if(cajaType.getName().equalsIgnoreCase("Pago anticipado ")||cajaType.getName().equalsIgnoreCase("Anula pago anticipado")){
					PagoAnticipado(journalLine,journalLine2,cajaType);
				}
				
				// Si la cuenta comienza con 4 agregar ccosto2 31100 ccosto3
				// 211100
				ChequearNdeCuenta(journalLine);
				ChequearNdeCuenta(journalLine2);
				
				
							
				// Cambio el tc para uno por que sino los calculos internos de
				// la persistencia me multiplican por 0
				if (molde.gettc().compareTo(Env.ZERO) == 0) {
					molde.settc(Env.ONE);
				}
				journalLine.setCurrencyRate(molde.gettc());
				journalLine2.setCurrencyRate(molde.gettc());
				
				journalLine = this.AmtAcctforCurrencyRate(journalLine);
				journalLine2 = this.AmtAcctforCurrencyRate(journalLine2);


					
			
				
				if(molde.getC_Currency_ID()==142){
					if(cajaType.isCancelled()){
						totalDebePesosA = totalDebePesosA.add(journalLine.getAmtAcctDr());
						totalHaberPesosA = totalHaberPesosA.add(journalLine.getAmtAcctCr());
						totalDebePesosA = totalDebePesosA.add(journalLine2.getAmtAcctDr());
						totalHaberPesosA = totalHaberPesosA.add(journalLine2.getAmtAcctCr());
						this.journalLinesPesosA.add(journalLine);
						this.journalLinesPesosA.add(journalLine2);
					}
					else{
						totalDebePesos = totalDebePesos.add(journalLine.getAmtAcctDr());
						totalHaberPesos = totalHaberPesos.add(journalLine.getAmtAcctCr());
						totalDebePesos = totalDebePesos.add(journalLine2.getAmtAcctDr());
						totalHaberPesos = totalHaberPesos.add(journalLine2.getAmtAcctCr());
						this.journalLinesPesos.add(journalLine);
						this.journalLinesPesos.add(journalLine2);					
					}					
				}else if(molde.getC_Currency_ID()==100) {
					if(cajaType.isCancelled()){
						totalDebeDolaresA = totalDebeDolaresA.add(journalLine.getAmtAcctDr());
						totalHaberDolaresA = totalHaberDolaresA.add(journalLine.getAmtAcctCr());
						totalDebeDolaresA = totalDebeDolaresA.add(journalLine2.getAmtAcctDr());
						totalHaberDolaresA = totalHaberDolaresA.add(journalLine2.getAmtAcctCr());
						this.journalLinesDolaresA.add(journalLine);
						this.journalLinesDolaresA.add(journalLine2);
					}
					else{
						totalDebeDolares = totalDebeDolares.add(journalLine.getAmtAcctDr());
						totalHaberDolares = totalHaberDolares.add(journalLine.getAmtAcctCr());
						totalDebeDolares = totalDebeDolares.add(journalLine2.getAmtAcctDr());
						totalHaberDolares = totalHaberDolares.add(journalLine2.getAmtAcctCr());
						this.journalLinesDolares.add(journalLine);
						this.journalLinesDolares.add(journalLine2);						
					}
				}

				// Proceso ultimo asiento
				if (ultimo) {
					if(!journalLinesPesos.isEmpty()){
						this.generateSimpleJournal(aux, totalDebePesos,totalHaberPesos,journalLinesPesos,142,"Asiento caja diario Pesos");
						}
					if(!journalLinesDolares.isEmpty()){
						this.generateSimpleJournal(aux, totalDebeDolares,totalHaberDolares,journalLinesDolares,100,"Asiento caja diario Dólares");
						}
					if(!journalLinesPesosA.isEmpty()){
						this.generateSimpleJournal(molde.getfecha(), totalDebePesosA,totalHaberPesosA,journalLinesPesosA,142,"Asiento caja diario Pesos Anulado");
					}
					if(!journalLinesDolaresA.isEmpty()){
						this.generateSimpleJournal(molde.getfecha(), totalDebeDolaresA,totalHaberDolaresA,journalLinesDolaresA,100,"Asiento caja diario Dólares Anulado");
					}
										
				}
			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}
	
	
private void PagoAnticipado(MJournalLine journalLine,MJournalLine journalLine2, MFduCajaType cajaType) {
	
		MTax tax= new MTax(getCtx(), 0, get_TrxName());
		tax = MTax.forValue(getCtx(), "basico", get_TrxName());
		BigDecimal iva =  tax.getRate();
		BigDecimal precision = Env.ONEHUNDRED;
		precision= precision.add(iva);
		BigDecimal cantIva= Env.ZERO;
		BigDecimal cantsinIva= Env.ZERO;
		
		if(cajaType.isCancelled()){				
			cantIva = journalLine.getAmtSourceDr().multiply(iva).divide(precision);
			cantsinIva = journalLine.getAmtSourceDr().subtract(cantIva);
			journalLine.setAmtSourceCr(cantsinIva);
			journalLine2.setAmtSourceDr(cantsinIva);
			//Genero las otras dos lineas que van a llevar el iva
			MFduCajaType typeaAnulIva = new MFduCajaType(getCtx(),0, null);
			typeaAnulIva = MFduCajaType.forName(getCtx(), "Anula Iva", get_TrxName());			
			MJournalLine journalLineAI = new MJournalLine(getCtx(), 0, get_TrxName());
			journalLineAI = journalLine;
			MJournalLine journalLineAI2 = new MJournalLine(getCtx(), 0, get_TrxName());
			journalLineAI2 = journalLine2;			
			MFduCajaTypeLine lineaAI = MFduCajaTypeLine.forC_Activity(getCtx(), typeaAnulIva.getUY_Fdu_Caja_Type_ID(),journalLine.getC_Activity_ID_1(), get_TrxName());
			journalLineAI.setC_ElementValue_ID(lineaAI.getC_ElementValue_ID());
			journalLineAI2.setC_ElementValue_ID(lineaAI.getc_elementvalue_id_2());
			
			
			journalLineAI.setC_Currency_ID(journalLine.getC_Currency_ID());
			journalLineAI.setC_Currency_ID(journalLine.getC_Currency_ID());
			if(lineaAI.isTotalizadorDEBE()){
				journalLineAI.setIsTotalizador(true);
			}
			journalLineAI.setC_ElementValue_ID(lineaAI.getC_ElementValue_ID());										
			
			if(lineaAI.isTotalizador()){
				journalLineAI2.setIsTotalizador(true);
			}
			journalLineAI2.setC_ElementValue_ID(lineaAI.getc_elementvalue_id_2());	
			journalLineAI.setDescription(cajaType.getconcept_mn());
			journalLineAI2.setDescription(cajaType.getconcept_mn());
			
			journalLineAI.setC_Activity_ID_1(journalLine.getC_Activity_ID_1());
			journalLineAI2.setC_Activity_ID_1(journalLine.getC_Activity_ID_1());
			
			journalLineAI.setAmtSourceCr(cantIva);
			journalLineAI2.setAmtSourceDr(cantIva);
			journalLineAI.setAmtSourceCr(Env.ZERO);
			journalLineAI2.setAmtSourceDr(Env.ZERO);
			this.journalLinesPesosA.add(journalLineAI);
			this.journalLinesPesosA.add(journalLineAI2);
			
			
		}
		else{				
			cantIva = journalLine2.getAmtSourceCr().multiply(iva).divide(precision);
			cantsinIva = journalLine2.getAmtSourceCr().subtract(cantIva);
			journalLine.setAmtSourceDr(cantsinIva);
			journalLine2.setAmtSourceCr(cantsinIva);
			//Genero las otras dos lineas que van a llevar el iva
			MFduCajaType typeIva = new MFduCajaType(getCtx(),0, null);
			typeIva = MFduCajaType.forName(getCtx(), "Iva", get_TrxName());
			
			MJournalLine journalLineI = new MJournalLine(getCtx(), 0, get_TrxName());
			MJournalLine journalLineI2 = new MJournalLine(getCtx(), 0, get_TrxName());			
			MFduCajaTypeLine lineaAI = MFduCajaTypeLine.forC_Activity(getCtx(), typeIva.getUY_Fdu_Caja_Type_ID(),journalLine.getC_Activity_ID_1(), get_TrxName());			
			journalLineI.setC_ElementValue_ID(lineaAI.getC_ElementValue_ID());
			journalLineI2.setC_ElementValue_ID(lineaAI.getc_elementvalue_id_2());
			journalLineI.setC_Currency_ID(journalLine.getC_Currency_ID());
			journalLineI2.setC_Currency_ID(journalLine.getC_Currency_ID());			
			if(lineaAI.isTotalizadorDEBE()){
				journalLineI.setIsTotalizador(true);
			}
			journalLineI.setC_ElementValue_ID(lineaAI.getC_ElementValue_ID());										
			
			if(lineaAI.isTotalizador()){
				journalLineI2.setIsTotalizador(true);
			}
			journalLineI2.setC_ElementValue_ID(lineaAI.getc_elementvalue_id_2());
			journalLineI.setDescription(cajaType.getconcept_mn());
			journalLineI2.setDescription(cajaType.getconcept_mn());
			journalLineI.setC_Activity_ID_1(journalLine.getC_Activity_ID_1());
			journalLineI2.setC_Activity_ID_1(journalLine.getC_Activity_ID_1());
			
			
			journalLineI.setAmtSourceDr(cantIva);
			journalLineI2.setAmtSourceCr(cantIva);			
			journalLineI.setAmtSourceCr(Env.ZERO);
			journalLineI2.setAmtSourceDr(Env.ZERO);
			this.journalLinesPesos.add(journalLineI);
			this.journalLinesPesos.add(journalLineI2);
		}	
	
	}

/**
 * Si la cuenta empieza en 4 hay que agregar 2 centros de costos.
 * OpenUp Ltda. Issue # 
 * @author leonardo.boccone - 11/04/2014
 * @see
 * @param journalLine
 */
	private void ChequearNdeCuenta(MJournalLine journalLine) {
		
		MElementValue cuenta = new MElementValue(getCtx(),journalLine.getC_ElementValue_ID(), null);	

		if (cuenta.getValue().startsWith("4")) {			
			MActivity act2 = MActivity.forValue(getCtx(), "311000",
					null);
			MActivity act3 = MActivity.forValue(getCtx(), "211100",
					null);
			journalLine.setC_Activity_ID_2(act2.getC_Activity_ID());
			journalLine.setC_Activity_ID_3(act3.getC_Activity_ID());
		}				
	}
	
	/***
	 * Genera asiento diario simple y lo completa. OpenUp Ltda. Issue #29
	 * 
	 * @author Gabriel Vila - 14/09/2012
	 * @see
	 */
	private void generateSimpleJournal(Timestamp dateAcct, BigDecimal totalDebe, BigDecimal totalHaber,List<MJournalLine> journalLines, int moneda, String descripcion) {
			
		
		Date aux = new Date();
		Timestamp DateDoc = new Timestamp(aux.getTime());
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		MDocType doc = MJournal.getSimpleJournalDocType(getCtx(), null);
		MFduFile file =  MFduFile.getMFduFileForValue(getCtx(), "CC250",get_TrxName());		
		
				
		this.load.setUY_FduFile_ID(file.get_ID());
		this.load.setC_DocType_ID(doc.get_ID());
		this.load.setDateTrx(DateDoc);
		this.load.setC_Period_ID(MPeriod.getC_Period_ID(getCtx(),load.getDateTrx(), Env.getAD_Org_ID(getCtx())));
		this.load.setIsManual(true);
		this.load.setProcessingDate(new Timestamp(System.currentTimeMillis()));
		this.load.setAD_Client_ID(client.getAD_Client_ID());
		this.load.saveEx();
		

		if ((doc == null) || (doc.get_ID() <= 0)) {
			throw new AdempiereException(
					"No se pudo obtener Documento para Asiento Diario Simple");
		}
				
		MJournal journal = new MJournal(getCtx(), 0, get_TrxName());
		journal.setAD_Org_ID(journal.getAD_Org_ID());
		journal.setC_AcctSchema_ID(schema.get_ID());
		journal.setC_DocType_ID(doc.get_ID());
		journal.setDescription(descripcion);
		journal.setDateDoc(DateDoc);
		journal.setDateAcct(dateAcct);
		journal.setUY_FduLoad_ID(load.get_ID());
		journal.setC_Currency_ID(moneda);
		journal.setC_ConversionType_ID(114);
		journal.setCurrencyRate(Env.ONE);
		journal.setTotalDr(totalDebe);
		journal.setTotalCr(totalHaber);
		journal.setGL_Category_ID(MGLCategory.getDefault(getCtx(),
				X_GL_Category.CATEGORYTYPE_Manual).get_ID());
		journal.set_ValueOfColumn("IsAsientoCaja", new String("Y"));
		journal.saveEx();

		// Seteo id del journal a las linas y las inserto
		if(!journalLines.isEmpty()){
			for (MJournalLine line : journalLines) {
				line.setGL_Journal_ID(journal.get_ID());
				line.setC_ConversionType_ID(114);
				line.setAD_Org_ID(line.getAD_Org_ID());
				line.saveEx();
			}			
		}
		// Completo asiento
		if (MSysConfig.getBooleanValue("UY_JOURNAL_COMPLETE_ONLOAD", false,
				this.getAD_Client_ID())) {
			if (!journal.processIt(DocumentEngine.ACTION_Complete)) {
				throw new AdempiereException(journal.getProcessMsg());
			}
		}

	}

	
	/**
	 * Tomo el valor en dolares de AmtSourceCr/Dr lo pesifico con el tc
	 * previamente obtenido y lo cargo AmtAcctDr OpenUp Ltda. Issue # 1932
	 * 
	 * @author Leonardo Boccone - 17/03/2014
	 * @see
	 * @param journalLine
	 * @return
	 */
	public MJournalLine AmtAcctforCurrencyRate(MJournalLine journalLine) {
		int precision = 4;
		BigDecimal importe = Env.ZERO;

		// Si viene moneda nacional
		if (journalLine.getC_Currency_ID() == 142) {			
				journalLine.setAmtAcctDr(journalLine.getAmtSourceDr());
				journalLine.setAmtAcctCr(journalLine.getAmtSourceCr());
			
		} else {
			if (journalLine.getCurrencyRate().compareTo(Env.ONE) != 0) {

				if (journalLine.getAmtSourceCr().compareTo(Env.ZERO) == 0) {
					importe = journalLine.getAmtSourceDr()
							.multiply(journalLine.getCurrencyRate())
							.setScale(precision, RoundingMode.HALF_UP);
					journalLine.setAmtAcctDr(importe);
				} else {
					importe = journalLine.getAmtSourceCr().multiply(journalLine.getCurrencyRate()).setScale(precision, RoundingMode.HALF_UP);
					journalLine.setAmtAcctCr(importe);
				}

			} else {
				throw new AdempiereException("Falta el TC");
			}

		}
		return journalLine;
	}

}
