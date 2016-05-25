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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MGLCategory;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MSysConfig;
import org.compiere.model.X_GL_Category;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MFduCod;
import org.openup.model.MFduConnectionData;
import org.openup.model.MFduFile;
import org.openup.model.MFduJournalType;
import org.openup.model.MFduLoad;
import org.openup.model.MMoldeFduLoadCC120;


/**
 * 
 * org.openup.process - PLoadFduTransaction Descripcion : Obtención de datos de base de datos SQL
 * SERVER de Italcred, para realizar asientos contables.
 * OpenUp Ltda. Issue # 1864
 * Description: 
 * @author leonardo.boccone - 29/04/2014
 * @see
 */
public class PLoadFduTransaction extends SvrProcess{
	
	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private int uyFduFileID = 1000016;
	private static String TABLA_MOLDE_CC120 = "UY_Molde_FduLoad_CC120";
	List<MMoldeFduLoadCC120> moldeAux = new ArrayList<MMoldeFduLoadCC120>();
	List<MJournalLine> journalLinesPesos = new ArrayList<MJournalLine>();
	List<MJournalLine> journalLinesDolares = new ArrayList<MJournalLine>();
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
		this.loadDailyTransaction();
		this.ChargeforCode();
		this.loadData();

		return "OK";
	}
	
	/**
	 * 
	 * OpenUp Ltda. Issue # 1864 Limpiamos la tabla molde de los datos
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
			sql = "TRUNCATE " + TABLA_MOLDE_CC120;
			DB.executeUpdateEx(sql, null);
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
	private void loadDailyTransaction() {			
		
		String sql = "";
		Connection con = null;
		ResultSet rs = null;
		try {

			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM");

			sql = "SELECT q_Mov.Nro_Cuenta, q_Mov.Cod_Moneda,q_Mov.Fecha_Presen, q_Mov.Cod_Movimiento, q_Mov.Cant_Cuotas, q_Mov.[MCA DEBITO CREDITO],q_Mov.[Importe Total],q_Mov.[Importe Final],q_Mov.MDIntFin,q_Mov.[Cuota Vigente]"
					+ " FROM FinancialPro.dbo.q_Mov_Diarios_Adempiere q_Mov"
					+ " WHERE q_Mov.Fecha_Presen>= '"
					+ df.format(this.fechaInicio)
					+ "' AND  q_Mov.Fecha_Presen<='"
					+ df.format(this.fechaFin)+ "'";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				
				String codigo = rs.getString("cod_Movimiento");
				int cantCuotas = rs.getInt("Cant_Cuotas");
				MMoldeFduLoadCC120 molde = new MMoldeFduLoadCC120(getCtx(),0,null);
				
				molde.setcodigo(codigo);
				molde.setCantCtas(cantCuotas);
				molde.setfecha(rs.getTimestamp("Fecha_Presen"));
				molde.setcuenta(rs.getString("Nro_Cuenta"));
				int moneda = rs.getInt("Cod_Moneda");
						if (moneda == 1) {
							moneda = 100;
						} else if (moneda == 0) {
							moneda = 142;
						} else {
							throw new AdempiereException("moneda no existe");
						}
				molde.setC_Currency_ID(moneda);
				molde.setintereses(rs.getBigDecimal("MDIntFin"));
				molde.setDebCred(rs.getInt("MCA DEBITO CREDITO"));
						
				if(cantCuotas==0){	
					molde.setentrada(rs.getBigDecimal("Importe Total"));
					molde.saveEx();
				}
				else if(cantCuotas>0){
					if(rs.getInt("Cuota Vigente")==1){
						molde.setentrada(rs.getBigDecimal("Importe Final"));
						molde.saveEx();									
					}					
				}
				else{
					throw new AdempiereException("cantidad de cuotas no valida");
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


	private Connection getConnection() throws Exception {

		Connection retorno = null;

		String connectString = "";
		try {

			MFduConnectionData conn = MFduConnectionData.forFduFileID(getCtx(), this.uyFduFileID, get_TrxName());

			if (conn != null) {

				connectString = "jdbc:sqlserver://" + conn.getserver_ip()
						+ "\\" + conn.getServer() + ";databaseName="
						+ conn.getdatabase_name() + ";user="
						+ conn.getuser_db() + ";password="
						+ conn.getpassword_db();

				retorno = DriverManager.getConnection(connectString,conn.getuser_db(), conn.getpassword_db());
			}

		} catch (Exception e) {
			throw e;
		}

		return retorno;
	}
	
	/**
	 * Tomo todas las lineas de la tabla molde y las sumo segun su codigo
	 * OpenUp Ltda. Issue #1864
	 * 
	 * @author Leonardo Boccone - 02/05/2014
	 * @see
	 */
	private void ChargeforCode() {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		sql = "SELECT * FROM "
				+ TABLA_MOLDE_CC120
				+ " ORDER BY fecha";
		try {
			pstmt = DB.prepareStatement(sql, null);	
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MMoldeFduLoadCC120 molde = new MMoldeFduLoadCC120(getCtx(),rs.getInt("UY_Molde_FduLoad_CC120_ID"),get_TrxName());
				
				boolean agrego = true;
				if (!moldeAux.isEmpty()) {
					for (MMoldeFduLoadCC120 m : moldeAux) {

						if(molde.getcodigo().equalsIgnoreCase(m.getcodigo())&&molde.getfecha().equals(m.getfecha())&& molde.getC_Currency_ID()==m.getC_Currency_ID()&&molde.getDebCred()==m.getDebCred()){
							if(sameCode(m.getcodigo(),m.getCantCtas(),molde.getCantCtas(),m.getC_Currency_ID())){
								
								m.setentrada(m.getentrada().add(molde.getentrada()));
								m.setintereses(m.getintereses().add(molde.getintereses()));
								agrego = false;
								break;							
							}
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
	private boolean sameCode(String getcodigo, int cantCtas, int cantCtas2, int currency) {

		MFduCod cod1 = MFduCod.getMFduCodFor(getCtx(), get_TrxName(), getcodigo, currency, cantCtas);
		MFduCod cod2 = MFduCod.getMFduCodFor(getCtx(), get_TrxName(), getcodigo, currency, cantCtas2);
	
		if(cod1 == null) {
			throw new AdempiereException("codigo nulo " + getcodigo + "Moneda " + currency + "Cantidad de Cuotas " +cantCtas);
		}
		else if(cod2== null){
			throw new AdempiereException("codigo nulo " + getcodigo + "Moneda " + currency + "Cantidad de Cuotas " +cantCtas2);
		}
		if(cod1.equals(cod2)){
			return true;
		}
		else{
			return false;
		}
		
	}


	/**
	 * Preparo las listas de molde ordenadas por codigo para generar los asientos
	 * OpenUp Ltda. Issue # 1864
	 * @author Leonardo Boccone - 05/05/2014
	 * @see
	 */
	
	private void loadData() {

		Timestamp fechaAsiento = null, aux = null;
		BigDecimal totalDebePesos = Env.ZERO, totalHaberPesos = Env.ZERO, totalDebeDolares = Env.ZERO, totalHaberDolares = Env.ZERO; 
		load = new MFduLoad(getCtx(),0,this.get_TrxName());	
		try {
			for (int i = 0; i < moldeAux.size(); i++) {

				boolean ultimo = false;
				if (i == moldeAux.size() - 1) {
					ultimo = true;
				}
				MMoldeFduLoadCC120 molde = moldeAux.get(i);
				fechaAsiento = molde.getfecha();
				if (i == 0) {
					aux = molde.getfecha();
				}
				if (!fechaAsiento.equals(aux)) {
					if(!journalLinesPesos.isEmpty()){
						this.generateSimpleJournal(aux, totalDebePesos,totalHaberPesos,journalLinesPesos,142,"Transacciones diarias Pesos");
						}
					if(!journalLinesDolares.isEmpty()){
						this.generateSimpleJournal(aux, totalDebeDolares,totalHaberDolares,journalLinesDolares,100,"Transacciones diarias Dolares");
						}
					totalDebePesos = Env.ZERO;
					totalHaberPesos = Env.ZERO;
					totalDebeDolares = Env.ZERO;
					totalHaberDolares = Env.ZERO;

					aux = fechaAsiento;
					this.journalLinesPesos = new ArrayList<MJournalLine>();
					this.journalLinesDolares = new ArrayList<MJournalLine>();
				}

				// Creo todas las lineas para el asiento
				MJournalLine journalLine = new MJournalLine(getCtx(), 0, get_TrxName());
				MJournalLine journalLine2 = new MJournalLine(getCtx(), 0, get_TrxName());
				

				MFduCod cod = MFduCod.getMFduCodFor(getCtx(), get_TrxName(), molde.getcodigo(),molde.getC_Currency_ID(),molde.getCantCtas());

				if(cod==null){
					throw new AdempiereException("codigo nulo");
				}
				MFduJournalType jType = MFduJournalType.forCode(getCtx(),cod.getUY_FduCod_ID(),get_TrxName());
				if(jType==null){
					throw new AdempiereException("jType nulo");
				}
				
				journalLine.setDescription(cod.getDescription());
				journalLine2.setDescription(cod.getDescription());
				
				if(molde.getintereses().compareTo(Env.ZERO)>0){
					asientoIntereses(molde);
				}
				
				journalLine.setC_Currency_ID(molde.getC_Currency_ID());
				journalLine2.setC_Currency_ID(molde.getC_Currency_ID());
				
				if(molde.getDebCred()==1){

					journalLine.setC_ElementValue_ID(jType.getC_ElementValue_ID());
					journalLine2.setC_ElementValue_ID(jType.getC_ElementValue_ID_Cr());
					journalLine.setAmtSourceDr(molde.getentrada());
					journalLine.setAmtSourceCr(Env.ZERO);
					
					journalLine2.setAmtSourceCr(molde.getentrada());
					journalLine2.setAmtSourceDr(Env.ZERO);
					
				}
				else if(molde.getDebCred()==2){
					journalLine2.setC_ElementValue_ID(jType.getC_ElementValue_ID());
					journalLine.setC_ElementValue_ID(jType.getC_ElementValue_ID_Cr());
					
					journalLine.setAmtSourceCr(molde.getentrada());
					journalLine.setAmtSourceDr(Env.ZERO);
					
					journalLine2.setAmtSourceDr(molde.getentrada());
					journalLine2.setAmtSourceCr(Env.ZERO);
				}
				else{
					throw new AdempiereException("el valor de debito/credito es incorrecto");
				}
				
				
				if(molde.getC_Currency_ID()==142){				
						totalDebePesos = totalDebePesos.add(journalLine.getAmtSourceDr());
						totalHaberPesos = totalHaberPesos.add(journalLine.getAmtSourceCr());
						totalDebePesos = totalDebePesos.add(journalLine2.getAmtSourceDr());
						totalHaberPesos = totalHaberPesos.add(journalLine2.getAmtSourceCr());
						this.journalLinesPesos.add(journalLine);
						this.journalLinesPesos.add(journalLine2);					
										
				}else if(molde.getC_Currency_ID()==100) {
					
						totalDebeDolares = totalDebeDolares.add(journalLine.getAmtSourceDr());
						totalHaberDolares = totalHaberDolares.add(journalLine.getAmtSourceCr());
						totalDebeDolares = totalDebeDolares.add(journalLine2.getAmtSourceDr());
						totalHaberDolares = totalHaberDolares.add(journalLine2.getAmtSourceCr());
						this.journalLinesDolares.add(journalLine);
						this.journalLinesDolares.add(journalLine2);											
				}
				// Proceso ultimo asiento
				if (ultimo) {
					if(!journalLinesPesos.isEmpty()){
						this.generateSimpleJournal(aux, totalDebePesos,totalHaberPesos,journalLinesPesos,142,"Transacciones diarias Pesos");
						}
					if(!journalLinesDolares.isEmpty()){
						this.generateSimpleJournal(aux, totalDebeDolares,totalHaberDolares,journalLinesDolares,100,"Transacciones diarias Dolares");
						}
										
				}
			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	private void asientoIntereses(MMoldeFduLoadCC120 molde) {
		
		MJournalLine intereses1 = new MJournalLine(getCtx(), 0, get_TrxName());
		MJournalLine intereses2 = new MJournalLine(getCtx(), 0, get_TrxName());
		intereses1.setC_Currency_ID(molde.getC_Currency_ID());
		intereses2.setC_Currency_ID(molde.getC_Currency_ID());
		MFduJournalType jType= new MFduJournalType(getCtx(),0,get_TrxName());
		
		if(molde.getC_Currency_ID()==142){
			MFduCod cod = MFduCod.getMFduCodForValue(getCtx(), get_TrxName(), "1");
			jType = MFduJournalType.forCode(getCtx(),cod.getUY_FduCod_ID(),get_TrxName());
			intereses1.setDescription(cod.getDescription());
			intereses2.setDescription(cod.getDescription());
		}
		else if(molde.getC_Currency_ID()==100){
			MFduCod cod = MFduCod.getMFduCodForValue(getCtx(), get_TrxName(), "2");
			jType = MFduJournalType.forCode(getCtx(),cod.getUY_FduCod_ID(),get_TrxName());
			intereses1.setDescription(cod.getName());
			intereses2.setDescription(cod.getName());
		}
		if(molde.getDebCred()==1){

			intereses1.setC_ElementValue_ID(jType.getC_ElementValue_ID());
			intereses2.setC_ElementValue_ID(jType.getC_ElementValue_ID_Cr());
		
			intereses1.setAmtSourceDr(molde.getintereses());
			intereses1.setAmtSourceCr(Env.ZERO);
			
			intereses2.setAmtSourceCr(molde.getintereses());
			intereses2.setAmtSourceDr(Env.ZERO);
			
		}

		else if(molde.getDebCred()==2){
			
			intereses2.setC_ElementValue_ID(jType.getC_ElementValue_ID());
			intereses1.setC_ElementValue_ID(jType.getC_ElementValue_ID_Cr());
			
			intereses2.setAmtSourceCr(molde.getintereses());
			intereses2.setAmtSourceDr(Env.ZERO);
			
			intereses1.setAmtSourceDr(molde.getintereses());
			intereses1.setAmtSourceCr(Env.ZERO);
		}
		if(molde.getC_Currency_ID()==142){
			this.journalLinesPesos.add(intereses1);
			this.journalLinesPesos.add(intereses2);	
		}
		else if(molde.getC_Currency_ID()==100){
			this.journalLinesDolares.add(intereses1);
			this.journalLinesDolares.add(intereses2);	
			
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
		MFduFile file =  MFduFile.getMFduFileForValue(getCtx(), "CC120DD", this.get_TrxName());		
		
				
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
				
		MJournal journal = new MJournal(getCtx(), 0, this.get_TrxName());
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
		journal.setGL_Category_ID(MGLCategory.getDefault(getCtx(),X_GL_Category.CATEGORYTYPE_Manual).get_ID());
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
	
	



}
