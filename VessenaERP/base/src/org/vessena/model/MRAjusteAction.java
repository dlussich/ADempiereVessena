/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 09/04/2013
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.util.OpenUpUtils;

/**
 * org.openup.model - MRAjusteAction
 * OpenUp Ltda. Issue #657 
 * Description: Modelo de cabezal de ajuste Italcred.
 * @author Gabriel Vila - 09/04/2013
 * @see
 */
public class MRAjusteAction extends X_UY_R_AjusteAction implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;

	private HashMap<String, BigDecimal> hashSumCedula = new HashMap<String, BigDecimal>();
	private HashMap<String, String> hashTarjetaCuenta = new HashMap<String, String>();
	
	private int idConexionFinancial = 1000011;
	
	private static final long serialVersionUID = -1037273864286269448L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_AjusteAction_ID
	 * @param trxName
	 */
	public MRAjusteAction(Properties ctx, int UY_R_AjusteAction_ID,
			String trxName) {
		super(ctx, UY_R_AjusteAction_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRAjusteAction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @param action
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public String prepareIt() {
		// Todo bien
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public String completeIt() {

		//	Re-Check
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		
		//OpenUp. Guillermo Brust. 24/06/2013. ISSUE #1068
		//Se guardan los ajustes en la tabla de ajustes del Financial.	
		//Se debe usar una transaccion porque puede ser que la primer cuota se guarde un ajuste, pero en la siguientes si ocurre una error, no deberia guardar nada
		Connection con = null;
		try {
			
			//esta es la conexion al sql server, ahora apunta a una base de TESTING
			con = OpenUpUtils.getConnectionToSqlServer(idConexionFinancial);
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false); //esto para iniciar una transaccion
			
		    for (MRAjusteActionLine ajusteActionLine : this.getLines()) {

		    	// Si la solicitud viene por incidencia
		    	if (ajusteActionLine.getUY_R_HandlerAjuste_ID() > 0){
			    	MRHandlerAjuste handAjuste = (MRHandlerAjuste)ajusteActionLine.getUY_R_HandlerAjuste();
			    	handAjuste.setIsRejected(ajusteActionLine.isRejected());
			    	handAjuste.setIsConfirmed(ajusteActionLine.isSelected());
			    	handAjuste.saveEx();
		    	}
		    	// Si la solicitud viene por el documento de Solicitud de Ajuste
		    	if (ajusteActionLine.getUY_R_AjusteRequestLine_ID() > 0){
			    	MRAjusteRequestLine lineReq = (MRAjusteRequestLine)ajusteActionLine.getUY_R_AjusteRequestLine();
			    	lineReq.setIsRejected(ajusteActionLine.isRejected());
			    	lineReq.setIsConfirmed(ajusteActionLine.isSelected());
			    	lineReq.saveEx();
		    	}
		    	
		    	// Si esa linea fue confirmada
		    	if (ajusteActionLine.isSelected()){

		    		//La cantidad de cuotas que tenga el ajuste, es la cantidad de veces que se guarda 	
			    	int cantVecesAjuste = ajusteActionLine.getQtyQuote().intValue();
			    	Timestamp fechaAjuste = null;
			    		    	    			
			    	for (int i = 0; i < cantVecesAjuste; i++) {	    		
			    		
			    		String grupoCC = this.getGrupoCCForCustomer(ajusteActionLine.getAccountNo());
			    		
			    		//Se obtiene la fecha del ajuste, esta va a ser la que indique cuando se tiene que enviar a firstdata para que entre en cierta fecha de cierre
			    		if(i == 0){
			    			fechaAjuste = ajusteActionLine.getDateAction(); //primer cuota, la misma fecha
			    		}else if(i == 1){
			    			Timestamp proxFecCie = this.getProximaFechaCierre(fechaAjuste, grupoCC);
			    			if(proxFecCie != null){
			    				fechaAjuste = OpenUpUtils.sumaTiempo(proxFecCie, Calendar.DAY_OF_MONTH, 15); //segunda cuota, proxima fecha de cierre + 15 dias
			    			}else{
			    				throw new AdempiereException("Falta parametrizar fecha de cierre");
			    			}    				    			
			    		}else{
			    			fechaAjuste = OpenUpUtils.sumaTiempo(fechaAjuste, Calendar.DAY_OF_MONTH, 30); //a partir de la tercera cuota, es la fecha de la cuota anterior mas 30 dias  
			    		}	    			
		    			
		    			//obtengo el nuevo identficador para la nueva linea que vaya a guardar en el sqlserver
				    	int tablaAjusteID = this.obtenerIdentificadorTablaAjuste(con, stmt);   
				    	
				    	if(tablaAjusteID > 0){
				    		//guardo la linea del ajuste en sqlserver con el ID que obtuve
					    	this.guardarAjusteSQLServer(tablaAjusteID, ajusteActionLine, fechaAjuste, con, stmt);
				    	}else{
				    		throw new AdempiereException("No se logró obtener identificador unico para grabar ajuste");
				    	} 	
					}	    	 	
		    	}
		    	
			}
		    
		    //Luego de hacer todo, recien ahi termino la transaccion
	    	con.commit(); //This commits the transaction and starts a new one.
			stmt.close(); //This turns off the transaction.	    			
			con.close();

		} catch (Exception e) {
			try {
				if(!con.isClosed()){
					con.rollback();
				}				
			} catch (Exception e2) {
				throw new AdempiereException(e);
			}			
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){				
				try {						
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}	 	 	
		//Fin OpenUp.
		
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;

	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 25/06/2013. ISSUE #
	 * Se el grupo de cierre de cuenta corriente de un cliente con su numero de cuenta.
	 * */
	private String getGrupoCCForCustomer(String accountno){
		
		String retorno = "";
		Connection con = null;
		ResultSet rs = null;	
		String sql = "";
		
		try {
			//esta es la conexion al sql server, ahora apunta a una base de TESTING
			con = OpenUpUtils.getConnectionToSqlServer(idConexionFinancial);			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);		
				
			sql = "select GrpCtaCte" + 
				  " from q_cuentas_clientes_new" + 
				  " where STNroCta = '" + accountno + "'";
			
			rs = stmt.executeQuery(sql);

			while (rs.next()) {				
				retorno = rs.getString("GrpCtaCte");
			}
			   
			rs.close();
			stmt.close(); 
			con.close();
			
		} catch (Exception e) {	
			try {
				if(!con.isClosed()){
					con.rollback();
				}				
			} catch (Exception e2) {
				throw new AdempiereException(e);
			}			
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
	 * OpenUp. Guillermo Brust. 24/06/2013. ISSUE #1068
	 * Se obtiene un ID para el nuevo registro a guardar en la tabla de ajustes en sqlserver
	 * */
	private int obtenerIdentificadorTablaAjuste(Connection con, Statement stmt){
		
		int retorno = 0;
		ResultSet rs = null;			
		
		try {		
			//Modifico el ID para crear uno nuevo.
			stmt.executeUpdate("UPDATE FinancialPro.dbo.NUMERAFD SET NumFUltNro = NumFUltNro + 1 WHERE NumFCod = 14");			
			
			//Ahora obtengo ese valor que modifique			
			rs = stmt.executeQuery("select NumFUltNro from FinancialPro.dbo.NUMERAFD where NumFCod = 14");

			while (rs.next()) {				
				retorno = rs.getInt("NumFUltNro");
			}
			
			rs.close();			
			
		} catch (Exception e) {					
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){				
				try {					
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}					
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}	
		return retorno;
	}
		
	/**
	 * OpenUp. Guillermo Brust. 24/06/2013. ISSUE #1068
	 * Se guarda el ajuste en la tabla de ajustes del sqlserver llamada AjuCtaCte
	 * */
	private void guardarAjusteSQLServer(int tablaAjusteID, MRAjusteActionLine ajusteActionLine, Timestamp fechaAjuste, Connection con, Statement stmt){
						
		String sql = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.sss");
				
		try {			
			MRAjuste ajuste = new MRAjuste(this.getCtx(), ajusteActionLine.getUY_R_Ajuste_ID(), this.get_TrxName());	
			
			String observaciones = ajusteActionLine.getobservaciones();
					
			if(observaciones.length() > 120) observaciones = observaciones.substring(0, 119) ;	
			
			sql = "INSERT INTO AjuCtaCt (EmpCod, AjuNro, CliCod, AjuNCta, AjuDVer, AjuNTarj, MovCod, PTCod, AjuFecEmi," + 
				  " MndCod, AjuImport, AjuDebCre, AjuFecha, CAjCod, AjuTipo, AjuAfeSdo, AjuAplica, AjuMesAnt, AjuConf, AjuGen," +
				  " AjuFecConf, AjuUsu, AjuBaja, AjDvNroC, AjDvNroR, AjDvErr, AjDvObs, AjDvCodErr, AjuOrigen, AjuObs, AjuUsuAut, AjuFHAut," +
				  " AjuUsuAnu, AjuAnuObs, AjuFecHora, MotAjuCod, AjuFecCie, ERPAjuNro, ERPDAjuCod) " + 
				  " VALUES (" +
				  10 + "," + //EmpCod
				  tablaAjusteID + "," + //AjuNro
				  ajusteActionLine.getCedula() + "," + //CliCod
				  ajusteActionLine.getAccountNo() + "," + //AjuNCta
				  "(select distinct STDigVerNr from q_cuentas_clientes_new where STNroCta = '" + ajusteActionLine.getAccountNo() + "')," + //AjuDVer
				  hashTarjetaCuenta.get(ajusteActionLine.getAccountNo()) + "," + //AjuNTarj
				  (ajusteActionLine.getC_Currency_ID() == 142 ? 351 : 353) + "," + //MovCod
				  "(select distinct PTCod from q_cuentas_clientes_new where STNroCta = '" + ajusteActionLine.getAccountNo() + "')," + //PTCod				 
				  "'1753-01-01 00:00:00'" + "," + //AjuFecEmi
				  (ajusteActionLine.getC_Currency_ID() == 142 ? 0 : 1)  + "," + //MndCod
				  ajusteActionLine.getAmount() + "," + //AjuImport
				  "(select DAjuDebCre from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + ajuste.getValue() + "')," + //AjuDebCre				  
				  "'" + df.format(fechaAjuste) + "'," + //AjuFecha
				  "(select CAjCod from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + ajuste.getValue() + "')," + //CAjCod
				  "(select DAjuTipo from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + ajuste.getValue() + "')," + //AjuTipo
				  "(select DAjuAfeSdo from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + ajuste.getValue() + "')," + //AjuAfeSdo
				  "CASE WHEN (select DAjuAfeSdo from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + ajuste.getValue() + "') = 0 THEN 0 " + 
				  " ELSE (select DAjuApSAnt from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + ajuste.getValue() + "') END," +  //AjuAplica
				  "(select DAjuMesAnt from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + ajuste.getValue() + "')," + //AjuMesAnt				  
				  "'N'," + //AjuConf
				  0 + "," + //AjuGen
				  "'1753-01-01 00:00:00'" + "," + //AjuFecConf.
				  "'" + new MUser(this.getCtx(), ajusteActionLine.getReceptor_ID(), this.get_TrxName()).getName().toUpperCase() + "', " + //AjuUsu
				  "'1753-01-01 00:00:00'" + "," + //AjuBaja				 			  
				  0 + "," + //AjDvNroC
				  0 + "," + //AjDvNroR
				  0 + "," + //AjDvErr
				  0 + "," + //AjDvObs
				  0 + "," + //AjDvCodErr
				  0 + "," + //AjuOrigen	
				  "'" + observaciones + "'," + //AjuObs
				  "'" + new MUser(this.getCtx(), this.getUpdatedBy(), this.get_TrxName()).getName().toUpperCase() + "', " + //AjuUsuAut
				  "'" + df.format(this.getDateTrx()) + "', " + //AjuFHAut					  
				  "''" + "," + //AjuUsuAnu
				  "''" + "," + //AjuAnuObs
				  "'" + df.format(this.getUpdated()) + "'," + //AjuFecHora
				  0 + "," + //MotAjuCod				 
				  "'1753-01-01 00:00:00'," + //AjuFecCie
				  this.get_ID() + "," +  //ERPAjuNro
				  "'" + ajuste.getValue() + "'" + //ERPDAjuCod
				  ")";		 
			
			stmt.executeUpdate(sql);		
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}	
	
	/**
	 * OpenUp. Guillermo Brust. 25/06/2013. ISSUE #
	 * Obtiene y retorna la proxima fecha de cierre para el mismo grupo de cierre pasado por parametro, y que sea mayor a la fecha pasada por parametro.
	 * */
	private Timestamp getProximaFechaCierre(Timestamp fecha, String grupoCC){
		
		Timestamp retorno = null;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{			
			sql = "select min(datetrx) as fecha" +
				  " from uy_fdu_logisticmonthdates d" +
				  " inner join uy_fdu_grupocc cc on d.uy_fdu_grupocc_id = cc.uy_fdu_grupocc_id" +
				  " inner join uy_fdu_logisticmonth m on d.uy_fdu_logisticmonth_id = m.uy_fdu_logisticmonth_id" +
				  " where cc.value::numeric = '" + grupoCC + "'::numeric" +
				  " and datetrx >= '" + fecha + "'" +
				  " and m.isactive = 'Y'" +
				  " and d.isactive = 'Y'";
			
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				retorno = rs.getTimestamp("fecha");
			}
			
		} catch (Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}		
		return retorno;
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 *  OpenUp. Guillermo Brust. 24/06/2013. ISSUE #1068
	 *  Obtiene y retorno las lineas para este cabezal. 
	 */
	private List<MRAjusteActionLine> getLines()
	{
		String whereClause = I_UY_R_AjusteActionLine.COLUMNNAME_UY_R_AjusteAction_ID + " =  " + this.get_ID();
		
		List<MRAjusteActionLine> list = new Query(getCtx(), I_UY_R_AjusteActionLine.Table_Name, whereClause, get_TrxName()).list();

		return list;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public String getDocumentInfo() {

		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/04/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Obtiene solicitudes de ajustes cargadas en Incidencias.
	 * OpenUp Ltda. Issue #657 
	 * @author Gabriel Vila - 16/08/2013
	 * @see
	 * @return
	 */
	public boolean getAjustesPorIncidencias(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean hayInfo = false;
		
		try{
			
			sql = " select hand.*, gest.uy_r_reclamo_id, gest.receptor_id, gest.dateaction as fechaajuste" +
				  " from uy_r_handlerajuste hand " +
				  " inner join uy_r_gestion gest on hand.uy_r_gestion_id = gest.uy_r_gestion_id " +
				  " inner join uy_r_reclamo rec on gest.uy_r_reclamo_id = rec.uy_r_reclamo_id " +
				  " where gest.isexecuted ='Y' " +
				  " and hand.isconfirmed='N' " +
				  " and hand.isrejected='N' " +
				  " and gest.receptor_id !=" + this.getAD_User_ID(); 
			
			pstmt = DB.prepareStatement(sql.toString(), null);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				hayInfo = true;
				
				MRReclamo reclamo = new MRReclamo(getCtx(), rs.getInt("uy_r_reclamo_id"), get_TrxName());
				MRCedulaCuenta cedulacuenta = (MRCedulaCuenta)reclamo.getUY_R_CedulaCuenta();
				
				MRAjusteActionLine line = new MRAjusteActionLine(getCtx(), 0, get_TrxName());
				line.setUY_R_AjusteAction_ID(this.get_ID());
				line.setUY_R_Ajuste_ID(rs.getInt("uy_r_ajuste_id"));
				line.setDateTrx(this.getDateTrx());
				line.setReceptor_ID(rs.getInt("receptor_id"));
				line.setDateAction(rs.getTimestamp("fechaajuste"));
				line.setC_Currency_ID(rs.getInt("c_currency_id"));
				line.setAmount(rs.getBigDecimal("amount"));
				line.setQtyQuote(rs.getBigDecimal("qtyquote"));
				line.setDateReclamo(reclamo.getDateTrx());
				line.setIsSelected(true);
				line.setUY_R_Reclamo_ID(reclamo.get_ID());
				line.setUY_R_HandlerAjuste_ID(rs.getInt("uy_r_handlerajuste_id"));
				line.setDescription(rs.getString("description"));
				line.setobservaciones(rs.getString("description"));
				line.setCedula(reclamo.getCedula());
				line.setAccountNo(cedulacuenta.getAccountNo());
				line.saveEx();
				
				// Acumulo montos por cedula en hash
				BigDecimal importeAux = rs.getBigDecimal("linenetamt");
				if (line.getC_Currency_ID() != 142){
					importeAux = rs.getBigDecimal("linenetamtacct");
					if (importeAux == null) {
						importeAux = rs.getBigDecimal("linenetamt");
					}
				}
				BigDecimal monto = Env.ZERO;
				if (hashSumCedula.containsKey(line.getCedula())){
					monto = hashSumCedula.get(line.getCedula());
					monto = monto.add(importeAux);
				}
				else{
					monto = importeAux;
				}
				hashSumCedula.put(line.getCedula(), monto);
				
				// voy guardando numero de tarjeta titular por cuenta en hash
				if (!hashTarjetaCuenta.containsKey(line.getAccountNo())){
 					hashTarjetaCuenta.put(line.getAccountNo(), this.getNroTarjetaTitular(line.getAccountNo()));	
				}
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return hayInfo;
	}

	
	/***
	 * Obtiene solicitudes de ajustes cargadas en el documento de Solicitud de Ajuste.
	 * OpenUp Ltda. Issue #657 
	 * @author Gabriel Vila - 16/08/2013
	 * @see
	 * @return
	 */
	public boolean getAjustesPorSolicitud(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean hayInfo = false;
		
		try{
			
			sql = " select line.*, req.dateaction as fechaajuste, req.ad_user_id, req.datetrx " +
				  " from uy_r_ajusterequestline line " +
				  " inner join uy_r_ajusterequest req on line.uy_r_ajusterequest_id = req.uy_r_ajusterequest_id " +
				  " where line.isconfirmed='N' " +
				  " and line.isrejected='N' " +
				  " and req.docstatus='CO' " +
				  " and req.ad_user_id !=" + this.getAD_User_ID(); 
			
			pstmt = DB.prepareStatement(sql, null);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				hayInfo = true;
				
				MRAjusteRequest request = new MRAjusteRequest(getCtx(), rs.getInt("uy_r_ajusterequest_id"), get_TrxName());

				MRAjusteActionLine line = new MRAjusteActionLine(getCtx(), 0, get_TrxName());
				line.setUY_R_AjusteAction_ID(this.get_ID());
				line.setUY_R_Ajuste_ID(rs.getInt("uy_r_ajuste_id"));
				line.setDateTrx(this.getDateTrx());
				line.setReceptor_ID(rs.getInt("ad_user_id"));
				line.setDateAction(rs.getTimestamp("fechaajuste"));
				line.setC_Currency_ID(rs.getInt("c_currency_id"));
				line.setAmount(rs.getBigDecimal("amount"));
				line.setQtyQuote(rs.getBigDecimal("qtyquote"));
				line.setDateReclamo(rs.getTimestamp("datetrx"));
				line.setIsSelected(true);
				line.setUY_R_AjusteRequest_ID(request.get_ID());
				line.setUY_R_AjusteRequestLine_ID(rs.getInt("uy_r_ajusterequestline_id"));
				line.setDescription(rs.getString("description"));
				line.setobservaciones(rs.getString("description"));
				line.setCedula(rs.getString("cedula"));
				line.setAccountNo(rs.getString("accountno"));
				line.saveEx();
				
				// Acumulo montos por cedula en hash
				BigDecimal importeAux = rs.getBigDecimal("linenetamt");
				if (line.getC_Currency_ID() != 142){
					importeAux = rs.getBigDecimal("linenetamtacct");
					if (importeAux == null) {
						importeAux = rs.getBigDecimal("linenetamt");
					}
				}
				BigDecimal monto = Env.ZERO;
				if (hashSumCedula.containsKey(line.getCedula())){
					monto = hashSumCedula.get(line.getCedula());
					monto = monto.add(importeAux);
				}
				else{
					monto = importeAux;
				}
				hashSumCedula.put(line.getCedula(), monto);
				
				// voy guardando numero de tarjeta titular por cuenta en hash
				if (!hashTarjetaCuenta.containsKey(line.getAccountNo())){
 					hashTarjetaCuenta.put(line.getAccountNo(), this.getNroTarjetaTitular(line.getAccountNo()));	
				}

			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return hayInfo;
	}

	
	/***
	 * Valida que el usuario que quiere autorizar ajustes este habilitado
	 * OpenUp Ltda. Issue #657. 
	 * @author Gabriel Vila - 10/07/2013
	 * @see
	 * @return
	 */
	private String validateUser() {

		String value = null;
		
		try{

			// Valido primero que el usuario actual sea distinto al usuario que creo la solicitud 
			if (Env.getAD_User_ID(Env.getCtx()) != this.getAD_User_ID()){
				return "Esta solicitud no puede ser procesada por un Usuario distinto al Usuario que la creo.";
			}
			
			String sql = " select distinct ad_user_id " +
					     " from uy_r_ajusteusuario " +
					     " where ad_user_id =" + this.getAD_User_ID();
			
			int adUser = DB.getSQLValueEx(null, sql);
			
			if (adUser <= 0){
				return "Usuario NO habilitado para Confirmar Ajustes.";
			}
			
			return value;
		}
		catch (Exception ex){
			throw new AdempiereException(ex);
		}
	}

	/***
	 * Elimina informacion anterior para este modelo.
	 * OpenUp Ltda. Issue #657 
	 * @author Gabriel Vila - 16/08/2013
	 * @see
	 */
	private void deleteData(){
		
		try {
			
			// Antes de cargar me aseguro de eliminar registros anteriores
			String action = " delete from uy_r_ajusteactionline " +
					        " where uy_r_ajusteaction_id =" + this.get_ID();
			
			DB.executeUpdateEx(action, get_TrxName());
			
			this.hashSumCedula = new HashMap<String, BigDecimal>();
			this.hashTarjetaCuenta = new HashMap<String, String>();
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Obtiene solicitudes de ajustes pendientes y genera lineas para este ajuste a partir
	 * de ellas.
	 * OpenUp Ltda. Issue #657 
	 * @author Gabriel Vila - 09/04/2013
	 * @see
	 */
	public boolean loadRequests(){
		
		boolean hayPorIncidencias = false, hayPorSolicitud = false;

		// Valido usuario autorizador
		String valido = this.validateUser(); 
		if (valido != null){
			throw new AdempiereException(valido);
		}
		
		// Elimino datos anteriores
		this.deleteData();
		
		// Cargo solicitudes de ajustes cargada en Incidencias
		hayPorIncidencias = this.getAjustesPorIncidencias();
		
		// Cargo solicitudes de ajustes cargada por documento de Solicitud
		hayPorSolicitud = this.getAjustesPorSolicitud();
		
		// Por cada CEDULA que esta en el hash, debo validar que el monto a ajustar no supere el tope
		// que tiene este usuario para confirmar ajustes
		this.checkTopeMonto();
		
		return (hayPorIncidencias | hayPorSolicitud);
	}

	/***
	 * Valido que el tope que tiene este usuario para confirmar ajustes por cedula, no supere los totales
	 * de cada cedula a procesar.
	 * OpenUp Ltda. Issue #657 
	 * @author Gabriel Vila - 16/08/2013
	 * @see
	 */
	private void checkTopeMonto() {

		try {
			
			// Obtengo tope de monto para este usuario
			String sql = " select tope from uy_r_ajusteusuario where ad_user_id =? " +
					     " and c_currency_id = 142";
			BigDecimal topeUsuario = DB.getSQLValueBDEx(null, sql, this.getAD_User_ID());
			
			// Recorro cedulas y verifico si el tope se cumple
			for (String cedula: hashSumCedula.keySet()){
				
				BigDecimal montoCedula = hashSumCedula.get(cedula);
				if (topeUsuario.compareTo(montoCedula) < 0){
					// Debo eliminar lineas de ajuste para esta cedula
					String action = " delete from uy_r_ajusteactionline " +
							        " where uy_r_ajusteaction_id =" + this.get_ID() +
							        " and cedula ='" + cedula + "'";
					DB.executeUpdateEx(action, get_TrxName());
				}
			}
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Obtiene numero de tarjeta de titular desde Financial para determinada cuenta
	 * OpenUp Ltda. Issue #657 
	 * @author Gabriel Vila - 16/08/2013
	 * @see
	 * @param cuenta
	 * @return
	 */
	private String getNroTarjetaTitular(String cuenta){
		
		String retorno = "";
		Connection con = null;
		ResultSet rs = null;	
		String sql = "";
		
		try {
			//esta es la conexion al sql server, ahora apunta a una base de TESTING
			con = OpenUpUtils.getConnectionToSqlServer(idConexionFinancial);			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);		
				
			sql = " select coalesce(stnrotarj,'') as stnrotarj " + 
				  " from q_clientes_adempiere " + 
				  " where STNroCta = " + cuenta +
				  " and STDeriNro = 0";
			
			rs = stmt.executeQuery(sql);

			if (rs.next()) {				
				retorno = rs.getString("stnrotarj");
			}
			   
			rs.close();
			stmt.close(); 
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
	
}


