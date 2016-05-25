/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 06/02/2013
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.MQuery;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.util.OpenUpUtils;


/**
 * org.openup.model - MRReclamo
 * OpenUp Ltda. Issue #285 
 * Description: Ingreso de Reclamos 
 * @author Gabriel Vila - 06/02/2013
 * @see
 */
public class MRReclamo extends X_UY_R_Reclamo implements DocAction, IDynamicTask {

	private static final long serialVersionUID = 7196030767351657884L;

	private String processMsg = null;
	private boolean justPrepared = false;

	private int idConexionFinancial = 1000011;

	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Reclamo_ID
	 * @param trxName
	 */
	public MRReclamo(Properties ctx, int UY_R_Reclamo_ID, String trxName) {
		super(ctx, UY_R_Reclamo_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRReclamo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {

		MRType tipo = (MRType)this.getUY_R_Type();
		
		// Si el tipo de incidencia es manual, valido datos ingresados por el usuario
		if (tipo.isManual()){
			// Valido datos obligatorios. Los valido aca porque con el Check de incidencia interna,
			// me saltan los campos obligatorios al guardar y queda esteticamente feo.
			this.validateData();
			if (this.processMsg != null){
				return false;
			}
			
		}
		
		// Al aplicar seteo fecha de creación de incidencia.
		this.setDateTrx(new Timestamp(System.currentTimeMillis()));
		
		// Seteo periodo
		MPeriod period = MPeriod.get(getCtx(), this.getDateTrx(), 0);
		if (period != null) this.setC_Period_ID(period.get_ID());
		
		// Si esta incidencia esta marcada como Solucion Inmediata, la misma 
		// se cierra en este momento. Proceso esta situacion.
		if (this.isinmediate()){
			return this.processInmediateSolution();
		}
		
		MRCause cause = (MRCause)this.getUY_R_Cause();
		
		// Tipo de accion segun sea incidencia interna, segun se notifique al cliente o no, etc.
		if (this.isInternalIssue()){
			this.setUY_R_ActionType_ID(MRActionType.IDforValue(getCtx(), "interno", null));
		}
		else{
			if (cause.isNotificable()){
				this.setUY_R_ActionType_ID(MRActionType.IDforValue(getCtx(), "clinotifica", null));
			}
			else{
				this.setUY_R_ActionType_ID(MRActionType.IDforValue(getCtx(), "clinonotifica", null));
			}
		}
		
		this.setStatusReclamo(STATUSRECLAMO_PendienteDeGestion);
		
		if (this.getUY_R_CedulaCuenta_ID() > 0){
			MRCedulaCuenta cedulacuenta = (MRCedulaCuenta)this.getUY_R_CedulaCuenta();
			this.setAccountNo(cedulacuenta.getAccountNo());
		}
		
		// Guardo usuario que creo la incidencia como Observador de la misma
		MRReclamoObserver obs = new MRReclamoObserver(getCtx(), 0, get_TrxName());
		obs.setUY_R_Reclamo_ID(this.get_ID());
		obs.setAD_User_ID(this.getReceptor_ID());
		obs.saveEx();
		
		// Genero entrada en Bandeja
		if (cause.isGenerateInbox()){
			this.generateInbox();	
		}
		
		// Genero señales de semaforo para que el proceso automatico las ejecute
		this.setReclamoSigns();
		
		if (!this.isInternalIssue()){

			// Genero registro para envio de email para que el proceso automatico los envie.
			this.setReclamoEmails();

			// Impacto legajo del financial al ingresar (aplicar) la incidencia. ISSUE #1079	
			this.setLegajoFinancial(true, false, null);
			
		}
					
		// Envio email de notificacion de ingreso de incidencia
		this.sendCreateEmail();

		// Nuevo tracking
		MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(this.getDateTrx());
		track.setAD_User_ID(this.getReceptor_ID());
		track.setDescription("Ingreso de Incidencia");
		track.setUY_R_Reclamo_ID(this.get_ID());		
		track.saveEx();
		
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
		
	}

	/***
	 * 
	 * OpenUp Ltda. Issue # 1079
	 * @author Guillermo Brust - 27/06/2013
	 * @see
	 * @param b : true si ingresa incidencia, false si es cierre de incidencia.
	 */
	public void setLegajoFinancial(boolean ingresaIncidencia, boolean force, String description) {
		
		//OpenUp. Guillermo Brust. 27/06/2013. ISSUE # 1079
		//Se guardan lineas en el legajo.
		//Se debe usar una transaccion
		Connection con = null;
		try {
			
			
			// Solo se impacta si el tema esta parametrizado para impactar legajo y si no es forzado.
			MRCause cause = (MRCause)this.getUY_R_Cause();
			if (!cause.isConLegajo()){
				if (!force) return;
			}
			
			//esta es la conexion al sql server, ahora apunta a una base de TESTING
			con = OpenUpUtils.getConnectionToSqlServer(idConexionFinancial);
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false); //esto para iniciar una transaccion
			
			//obtengo el nuevo identficador para la nueva linea que vaya a guardar en el sqlserver
	    	int tablaAjusteID = this.obtenerIdentificadorTablaLegajos(con, stmt);   
	    	
	    	if(tablaAjusteID > 0){
	    		//guardo la linea del ajuste en sqlserver con el ID que obtuve
		    	this.guardarLegajoSQLServer(tablaAjusteID, ingresaIncidencia, con, stmt, description);
	    	}else{
	    		throw new AdempiereException("No se logró obtener identificador unico para grabar Legajos");
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
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 27/06/2013. ISSUE # 1079
	 * Se obtiene un ID para el nuevo registro a guardar en la tabla de legajos en sqlserver
	 * */
	private int obtenerIdentificadorTablaLegajos(Connection con, Statement stmt){
		
		int retorno = 0;
		ResultSet rs = null;			
		
		try {		
			//Modifico el ID para crear uno nuevo.
			stmt.executeUpdate("update NUME set NroUlt = NroUlt + 1 where NroDsc = 'Lin.Pdp Comentarios' and EmpCod = 1");			
			
			//Ahora obtengo ese valor que modifique			
			rs = stmt.executeQuery("select NroUlt from NUME where NroDsc = 'Lin.Pdp Comentarios' and EmpCod = 1");

			while (rs.next()) {				
				retorno = rs.getInt("NroUlt");
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
	 * OpenUp. Guillermo Brust. 28/06/2013. ISSUE #1079
	 * Se guarda una linea en la tabla de legajos del sqlserver
	 * */
	private void guardarLegajoSQLServer(int tablaLegajoID, boolean ingresaIncidencia, Connection con, Statement stmt, String description){
						
		String sql = "";
		SimpleDateFormat dfConHora = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.sss");
		SimpleDateFormat dfSinHora = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		
		try {		
			
			MRCause cause = new MRCause(getCtx(), this.getUY_R_Cause_ID(), this.get_TrxName());
			MRType type = new MRType(getCtx(), this.getUY_R_Type_ID(), this.get_TrxName());
			String usuario = "";
			if(ingresaIncidencia){
				usuario = new MUser(getCtx(), this.getReceptor_ID(), this.get_TrxName()).getName().toUpperCase();
				if(usuario.length() > 10){
					usuario = usuario.substring(0, 9);
				}
			}else{
				if(this.getNotificator_ID() > 0){
					usuario = new MUser(getCtx(), this.getNotificator_ID(), this.get_TrxName()).getName().toUpperCase();
					if(usuario.length() > 10){
						usuario = usuario.substring(0, 9);
					}
				}else if(this.getGestor_ID() > 0){
					usuario = new MUser(getCtx(), this.getGestor_ID(), this.get_TrxName()).getName().toUpperCase();
					if(usuario.length() > 10){
						usuario = usuario.substring(0, 9);
					}
				}else{
					new AdempiereException("No se logro obtener el usuario para guardar el legajo");				
				}
			}
			
			//OpenUp. Guillermo Brust. 21/10/2013. ISSUE #
			//Se cambia el metodo, para modificar la descripcion segun sea de donde venga. Para esto se pregunta si el parametro de description viene en null
			//se respeta el armado de la observacion segun estaba antes, sino la observacion sera el contenido del parametro description
			String observacion = "";
			if(description != null) observacion = description;
			else observacion = (ingresaIncidencia == true ? "Nueva: Nº " : "Cierre: Nº ") + this.getDocumentNo() + " / Tema: " + cause.getName() + " / Tipo: " + type.getName();
			//Fin OpenUp.			
			
			if(observacion.length() > 100){
				observacion = observacion.substring(0, 99) ;
			}
			
			sql = "INSERT INTO PROMPAG2 (EmpCod, SolNro, PdpNro, PdpLin, PdpLinFec, PdpText, PdpLFReal, PdpLinCli, PdpLinUsrI, PdpCCon, PdpVenCod)" + 
				  " VALUES (" +
				  10 + "," + //EmpCod
				  0 + "," + //SolNro
				  0 + "," + //PdpNro
				  tablaLegajoID + "," + //PdpLin
				  "'" + dfConHora.format(this.getUpdated()) + "'," + //PdpLinFec
				  //Nueva: Nº ###### / Tema: ##################### / Tipo: #####################
				  //Cierre: Nº ###### / Tema: ##################### / Tipo: #####################
				  "'" + observacion + "'," +				  
				  "'" + dfSinHora.format(this.getUpdated()) + "'," + //PdpLFReal
				  "'" + this.getCedula() + "'," + //PdpLinCli
				  "'" + usuario + "'," + //PdpLinUsrI
				  (ingresaIncidencia == true ? "'I1'" : "'I2'") + "," +
				  "''" +
				  ")";		 
			
			stmt.executeUpdate(sql);		
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}	

	/***
	 * Procesa una incidencia de manera inmediata y por lo tanto se cierra ahora.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 05/06/2013
	 * @see
	 * @return
	 */
	private boolean processInmediateSolution() {

		this.setStatusReclamo(STATUSRECLAMO_Cerrado);
		
		// Nuevo tracking
		MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(new Timestamp(System.currentTimeMillis()));
		track.setAD_User_ID(this.getReceptor_ID());
		track.setDescription("Resuelta de Manera Inmediata por el Receptor.");
		track.setUY_R_Reclamo_ID(this.get_ID());		
		track.saveEx();
		
		this.setReclamoResuelto(true);
		this.setReclamoNotificado(true);
		
		String completeOK = this.completeIt();
		if (completeOK.equalsIgnoreCase(DocAction.STATUS_Completed)){
			return true;			
		}
		else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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

		this.setStatusReclamo(STATUSRECLAMO_Cerrado);
		
		if (this.getUY_R_CedulaCuenta_ID() > 0){
			MRCedulaCuenta cedulacuenta = (MRCedulaCuenta)this.getUY_R_CedulaCuenta();
			this.setAccountNo(cedulacuenta.getAccountNo());
		}
		
		// Seteo fecha de cierre de incidencia
		this.setEndDate(new Timestamp(System.currentTimeMillis()));

		// Seteo cantidad de dias para resolver, gestionar, notificar y cerrar una incidencia.
		Timestamp fecIni = TimeUtil.trunc(this.getDateTrx(), TimeUtil.TRUNC_DAY);
		Timestamp fecFin = TimeUtil.trunc(this.getEndDate(), TimeUtil.TRUNC_DAY);		
		int cantDiasCierre = TimeUtil.getDaysBetween(fecIni, fecFin);
		int cantDiasResol = cantDiasCierre;
		int cantDiasGestion = 0, cantDiasNotif = 0;

		if (this.getAssignDateTo() != null){
			Timestamp fecResol = TimeUtil.trunc(this.getAssignDateTo(), TimeUtil.TRUNC_DAY);
			cantDiasNotif = TimeUtil.getDaysBetween(fecResol, fecFin);
			if (this.getdiasresolucion() <= 0){
				Timestamp fecIniGestion = TimeUtil.trunc(this.getAssignDateFrom(), TimeUtil.TRUNC_DAY);
				cantDiasGestion = TimeUtil.getDaysBetween(fecIniGestion, fecResol);
				cantDiasResol = TimeUtil.getDaysBetween(fecIni, fecResol);
			}
			else{
				cantDiasGestion = this.getDiasGestion();
				cantDiasResol = this.getdiasresolucion();
			}
		}
		this.setdiasresolucion(cantDiasResol);
		this.setDiasCierre(cantDiasCierre);
		this.setDiasGestion(cantDiasGestion);
		this.setDiasNotificacion(cantDiasNotif);
		
		
		// Nuevo Tracking
		MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(new Timestamp(System.currentTimeMillis()));
		track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
		track.setDescription("Cierre de incidencia");
		track.setUY_R_Reclamo_ID(this.get_ID());		
		track.saveEx();

		
		if (!this.isInternalIssue()){

			// Seteo Flag de resuelto a favor de la empresa o no.
			if (this.getJustification() == null){
				this.setResueltoEmpresa(false);
				// Envio email final.
				this.sendFinishEmail();
				
			}
			else{
				this.setResueltoEmpresa(true);
				// El email ya se envio al momento de confirmarse los motivos a favor de la empresa
				// por el canal escrito.
			}
			
			// Impacto legajo del financial al completar (cerrar para Italcred) la incidencia
			this.setLegajoFinancial(false, false, null);
		}
		
		
		// Elimino reclamo de bandeja de entrada
		String action = " delete from uy_r_inbox where uy_r_reclamo_id = " + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		// Elimino registro de emails en cola para envio
		action = " delete from uy_r_reclamoemail where uy_r_reclamo_id = " + this.get_ID() +
				 " and isexecuted ='N'";
		DB.executeUpdateEx(action, get_TrxName());

		// Elimino registro de señales de semaforo en cola para activarse
		action = " delete from uy_r_reclamosign where uy_r_reclamo_id = " + this.get_ID() +
				 " and isexecuted ='N'";
		DB.executeUpdateEx(action, get_TrxName());
		
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


	/***
	 * Valido datos ingresados.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 27/05/2013
	 * @see
	 */
	private void validateData() {

		if (this.getUY_R_Type_ID() <= 0){
			this.processMsg = "Debe indicar Tipo de Incidencia.";
			return;
		}

		if (this.getUY_R_Cause_ID() <= 0){
			this.processMsg = "Debe indicar Tema de Incidencia.";
			return;
		}
		
		if (this.getUY_R_Canal_ID() <= 0){
			this.processMsg = "Debe indicar Canal.";
			return;
		}
		
		if (this.getPriorityManual() == null){
			this.processMsg = "Debe indicar Prioridad Subjetiva.";
			return;
		}
		
		if (this.getPriorityManual().trim().equalsIgnoreCase("")){
			this.processMsg = "Debe indicar Prioridad Subjetiva.";
			return;
		}
		
		if (this.getDescription() == null){
			this.processMsg = "Debe ingresar Detalle de la Incidencia.";
			return;
		}

		if (this.getDescription().trim().equalsIgnoreCase("")){
			this.processMsg = "Debe ingresar Detalle de la Incidencia.";
			return;
		}
		
		if (!this.isInternalIssue()){
			if (this.getCedula() == null){
				this.processMsg = "Debe ingresar Cedula del Cliente.";
				return;
			}
			if (this.getCedula().trim().equalsIgnoreCase("")){
				this.processMsg = "Debe ingresar Cedula del Cliente.";
				return;
			}			
			if (this.getUY_R_CedulaCuenta_ID() <= 0){
				this.processMsg = "Debe ingresar Numero de Cuenta del Cliente.";
				return;
			}
			
			if (!this.validateCI()){
				this.processMsg = "Cedula Incorrecta. Verifique.";
				return;
			}
		}
		
		MRCause cause = (MRCause)this.getUY_R_Cause();
		
		// Si no hay subtema indicado, pero el tema tiene subtemas definidos,
		// aviso y salgo.
		if ((this.getuy_r_subcause_id_1() <= 0) && (this.getuy_r_subcause_id_2() <= 0)
				&& (this.getuy_r_subcause_id_3() <= 0) && (this.getuy_r_subcause_id_4() <= 0)){
			
			List<MRSubCause> subtemas = cause.getSubTemas();
			if (subtemas.size() > 0){
				this.processMsg = "Debe indicar al menos un SubTema.";
				return;
			}
		}
		
		// Si se indica via de notificacion por email, se valida que el campo
		// email tenga un formato valido.
		if (!this.isInternalIssue()){
			
			// Si el tema requiere notificacion al cliente
			if (cause.isNotificable()){

				if (this.getNotificationVia().equalsIgnoreCase(X_UY_R_Reclamo.NOTIFICATIONVIA_Email)){
					if ((this.getEMail() == null) || (this.getEMail().trim().equalsIgnoreCase(""))
							|| (!this.getEMail().contains("@") )){
						this.processMsg = "Cuando se indica Via de Notificacion por Email, se tiene que ingresar una dirección de Email válida.";
						return;
					}
					
					if (!cause.isNotificaEmail()){
						this.processMsg = "No es posible indicar Via de Notificacion por Email, ya que el Tema de la Incidencia No permite el envío de Emails.";
						return;
					}
				}

				// Valido notificaciones por celular
				if (this.getNotificationVia().equalsIgnoreCase(X_UY_R_Reclamo.NOTIFICATIONVIA_Celular)){
					if ((this.getMobile() == null) || (this.getMobile().trim().equalsIgnoreCase(""))){
						this.processMsg = "Cuando se indica Via de Notificacion por Celular, se tiene que ingresar numero de Celular.";
						return;
					}
				}
			
				// Valido notificaciones por telefono
				if (this.getNotificationVia().equalsIgnoreCase(X_UY_R_Reclamo.NOTIFICATIONVIA_TelefonoFijo)){
					if ((this.getTelephone() == null) || (this.getTelephone().trim().equalsIgnoreCase(""))){
						this.processMsg = "Cuando se indica Via de Notificacion por Telefono Fijo, se tiene que ingresar numero de Telefono Fijo.";
						return;
					}
				}
			}
		}

		// Valido cantidad de adjuntos obligatorios
		String sql = " select count(*) as cont " +
				     " from uy_r_reclamoadjunto " +
				     " where uy_r_reclamo_id =" + this.get_ID() +
				     " and ismandatory = 'Y'";

		int cantadjuntos = DB.getSQLValueEx(get_TrxName(), sql);
		if (cantadjuntos > 0) {
			// Obtengo ID de attachment
			sql = " select ad_attachment_id " +
				  " from ad_attachment " +
				  " where ad_table_id =" + I_UY_R_Reclamo.Table_ID + 
				  " and record_id = " + this.get_ID();
			int attID = DB.getSQLValueEx(get_TrxName(), sql);
			// Instancio modelo de attachment
			if (attID > 0){
				MAttachment attachment = new MAttachment(getCtx(), attID, null);
				if (attachment.getEntryCount() < cantadjuntos){
					this.processMsg = "Debe adjuntar por lo menos " + cantadjuntos + " Formulario/s Obligatorio/s.";
					return;
				}
			}
			else{
				if (cantadjuntos > 0){
					this.processMsg = "Debe adjuntar por lo menos " + cantadjuntos + " Formulario/s Obligatorio/s.";
					return;
				}
			}
		}
		
		return;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
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
	 * @author Hp - 06/02/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Verifica si un determinado cliente tiene reclamos pendientes.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 10/04/2013
	 * @see
	 * @param ctx
	 * @param cedula
	 * @return
	 */
	public static boolean havePendings(Properties ctx, String cedula){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean value = false;
		
		try{
			
			sql = " select * " +
				  " from vuy_reclamos_pend " +
				  " where cedula ='" + cedula + "'"; 
			
			pstmt = DB.prepareStatement(sql.toString(), null);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				value = true;
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}		
		
		return value;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Paso siempre el texto del detalle del reclamo a Mayusculas.
		if (this.getDescription() != null){
			this.setDescription(this.getDescription().toUpperCase());
		}
		
		//this.setDateTrx(new Timestamp(System.currentTimeMillis()));
		
		// Seteo adjuntos de esta incidencia segun tema y subtemas de la misma.
		if ( (newRecord) || (is_ValueChanged(COLUMNNAME_UY_R_Cause_ID)) 
				|| (is_ValueChanged(COLUMNNAME_uy_r_subcause_id_1)) || (is_ValueChanged(COLUMNNAME_uy_r_subcause_id_2))
				|| (is_ValueChanged(COLUMNNAME_uy_r_subcause_id_3)) || (is_ValueChanged(COLUMNNAME_uy_r_subcause_id_4))){
		
			this.setAdjuntos();
		}
		
		
		// Si estoy en borrador me aseguro de no tener atributos incorrectos por motivos de clonacion de incidencias
		if (this.getDocStatus().equalsIgnoreCase(DOCSTATUS_Drafted)){
			this.setReclamoResuelto(false);
			this.setStatusReclamo(STATUSRECLAMO_NuevaIncidencia);
			this.setJustification(null);
		}
		
		return true;
	}

	/***
	 * Refresca informacion del cliente asociado a este reclamo.
	 * OpenUp Ltda. Issue #737 
	 * @author Gabriel Vila - 18/04/2013
	 * @see
	 */
	public void refreshCustomerInfo() {

		Connection con = null;
		ResultSet rs = null;

		try{
			
			if (this.getCedula() == null) return;
			else if (this.getCedula().trim().equalsIgnoreCase("")) return;
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			con = this.getFDUConnection(fduData);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select stapenom,stcallenro,isnull(stdepto,'') as stdepto, " +
					 " isnull(clitel,'NO TIENE') as clitel, isnull(clicelular,'NO TIENE') as clicelular, " +
					 " isnull(stcodpos,0) as stcodpos, isnull(stnrocta,0) as stnrocta, isnull(climail,'NO TIENE - SOLICITAR !!') as climail, " +
					 " stnrotarj, stvenctarj, isnull(stlimcred,0) as stlimcred,  isnull(lccod,'') as producto " +
				     " from q_clientes_adempiere " +
				     " where clicod=" + this.getCedula();
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				
				int codPostal = rs.getInt("stcodpos");
				//int nroCuenta = rs.getInt("stnrocta");
			
				String strCodPostal = (codPostal > 0) ? String.valueOf(codPostal) : "NO TIENE";				
				//String strNroCuenta = (nroCuenta > 0) ? String.valueOf(nroCuenta) : "NO TIENE";
				String apto = "";
				if (rs.getString("stdepto") != null){
					if (!rs.getString("stdepto").trim().equalsIgnoreCase("")){
						apto = " - Apto.: " + rs.getString("stdepto");
					}
				}
	
				String email = rs.getString("climail");
				if ((email == null) || (email.trim().equalsIgnoreCase(""))){
					email = "NO TIENE - SOLICITAR !!" ;
				}
				
				this.setCustomerName(rs.getString("stapenom"));
				this.setDirection(rs.getString("stcallenro") + apto);
				this.setTelephone(rs.getString("clitel"));
				this.setMobile(rs.getString("clicelular"));
				this.setEMail(email);
				this.setPostal(strCodPostal);
				//this.setAccountNo(strNroCuenta);
				this.saveEx();
				
			}
			else{
				throw new AdempiereException("No hay registro de Cliente con ese Numero de Cedula.");
			}
			
			rs.close();
			con.close();		
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
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

	
	private Connection getFDUConnection(MFduConnectionData fduData) throws Exception {
		
		Connection retorno = null;

		String connectString = ""; 

		try {
			if(fduData != null){
				
				connectString = "jdbc:sqlserver://" + fduData.getserver_ip() + "\\" + fduData.getServer() + 
								";databaseName=" + fduData.getdatabase_name() + ";user=" + fduData.getuser_db() + 
								";password=" + fduData.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, fduData.getuser_db(), fduData.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}	

	/***
	 * Genera Entrada para esta incidencia en la bandeja.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 23/10/2013
	 * @see
	 */
	public void generateInbox() {
		
		try{

			MRCause cause = new MRCause(getCtx(), this.getUY_R_Cause_ID(), null);
			MRSign sign = null;
			
			// Si plazo optimo del tema es cero
			if (cause.getMediumTerm() <= 0) {
				// Si el plazo final tambien es cero
				if (cause.getDeadLine() <= 0){
					// Esta incidencia entra con semaforo en rojo
					sign = MRSign.forValue(getCtx(), "red", null);
				}
				else{
					// Esta incidencia entra con semaforo en amarillo
					sign = MRSign.forValue(getCtx(), "yellow", null);
				}
			}
			else{
				// Esta incidencia entra con semaforo en verde
				sign = MRSign.forValue(getCtx(), "green", null);
			}

			// Segundo semaforo. Es color gris salvo que tenga marcada denuncia DEFCON
			MRSign signCritical = null;
			if (this.isDenounced()){
				signCritical = MRSign.forValue(getCtx(), "red", null);
			}
			else{
				signCritical = MRSign.forValue(getCtx(), "grey", null);
			}
			
			
			MRInbox inbox = new MRInbox(getCtx(), 0, get_TrxName());
			inbox.setUY_R_Tarea_ID(MRTarea.forValue(getCtx(), "accionreclamo", null).get_ID());
			inbox.setAD_User_ID(this.getReceptor_ID());
			inbox.setDateTrx(this.getDateTrx());
			inbox.setPriorityManual(this.getPriorityManual());
			inbox.setTrackImage_ID(sign.getAD_Image_ID());
			inbox.setCriticalImage_ID(signCritical.getAD_Image_ID());
			inbox.setSeqNo(sign.getSeqNo());
			inbox.setCriticalSeqNo(signCritical.getSeqNo());
			inbox.setMediumTerm(cause.getMediumTerm());
			inbox.setUY_R_Type_ID(this.getUY_R_Type_ID());
			inbox.setUY_R_Cause_ID(this.getUY_R_Cause_ID());
			inbox.setStatusReclamo(this.getStatusReclamo());
			inbox.setUY_R_PtoResolucion_ID(this.getUY_R_PtoResolucion_ID());
			inbox.setuy_r_subcause_id_1(this.getuy_r_subcause_id_1());
			inbox.setuy_r_subcause_id_2(this.getuy_r_subcause_id_2());
			inbox.setuy_r_subcause_id_3(this.getuy_r_subcause_id_3());
			inbox.setuy_r_subcause_id_4(this.getuy_r_subcause_id_4());
			inbox.setPriority(this.getPriorityBase());
			inbox.setUY_R_Reclamo_ID(this.get_ID());
			inbox.setAD_Table_ID(I_UY_R_Reclamo.Table_ID);
			inbox.setC_DocType_ID(this.getC_DocType_ID());
			inbox.setRecord_ID(this.get_ID());
			if (cause.get_ID() > 0){
				inbox.setDueDate(TimeUtil.addDays(inbox.getDateTrx(), cause.getDeadLine()));
			}
			else{
				inbox.setDueDate(TimeUtil.addDays(inbox.getDateTrx(), 3));
			}
			
			inbox.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	@Override
	protected boolean beforeDelete() {

		if (this.getDocStatus().equalsIgnoreCase(DOCSTATUS_Applied)){
			throw new AdempiereException("No es posible Eliminar Incidencias Aplicadas.");
		}
		
		return true;
	}

	/***
	 * Acciones al tomar una incidencia desde la bandeja de entrada.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 27/05/2013
	 * @see
	 */
	public void inboxTaken() {

		try{

			// Nuevo tracking
			MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(new Timestamp(System.currentTimeMillis()));
			track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			track.setDescription("Visualizacion de Incidencia");
			track.setUY_R_Reclamo_ID(this.get_ID());
			track.saveEx();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	@Override
	public void setComment(String comment) {

		// Cuando agregan un comentario por la toolbar, el mismo se tiene que cargar
		// en la trazabilidad de esta incidencia.
		// Nuevo tracking
		MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(new Timestamp(System.currentTimeMillis()));
		track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
		track.setDescription("Nuevo Comentario");
		track.setobservaciones(comment);
		track.setUY_R_Reclamo_ID(this.get_ID());
		track.saveEx();
		
	}

	/***
	 * Setea aduntos que tiene esta incidencia segun tema y subtemas de la misma.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 20/06/2013
	 * @see
	 * @return
	 */
	private void setAdjuntos(){
		
		if (this.get_ID() <= 0) return;
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{
			
			// Elimino anteriores antes de volver a cargar
			DB.executeUpdateEx(" delete from uy_r_reclamoadjunto where uy_r_reclamo_id =" + this.get_ID(), get_TrxName());
			
			sql = " select uy_r_adjunto_id, isoptional " +
				  " from vuy_r_adjunto " +
				  " where (vuy_r_adjunto.uy_r_cause_id = " + this.getUY_R_Cause_ID() + ") " +
				  " or (vuy_r_adjunto.uy_r_cause_id = 0 " +
				  " and vuy_r_adjunto.uy_r_subcause_id IN(?,?,?,?)) ";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getuy_r_subcause_id_1());
			pstmt.setInt(2, this.getuy_r_subcause_id_2());
			pstmt.setInt(3, this.getuy_r_subcause_id_3());
			pstmt.setInt(4, this.getuy_r_subcause_id_4());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MRReclamoAdjunto recadj = new MRReclamoAdjunto(getCtx(), 0, get_TrxName());
				recadj.setUY_R_Adjunto_ID(rs.getInt("uy_r_adjunto_id"));
				recadj.setUY_R_Reclamo_ID(this.get_ID());
				
				boolean isMandatory = (!rs.getString("isoptional").equalsIgnoreCase("Y")) ? true : false;
				
				recadj.setIsMandatory(isMandatory);
				recadj.saveEx();
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

	@Override
	public void setAttachment(MAttachment attachment) {

		try{
			
			String adjuntosName = "";
			
			if (attachment.get_ID() <= 0) return;
			
			for (int i = 0; i < attachment.getEntries().length; i++){
				MAttachmentEntry attEntry = attachment.getEntries()[i];
				adjuntosName += attEntry.getName() + ", ";
			}
			if (!adjuntosName.equalsIgnoreCase("")){
				adjuntosName = adjuntosName.substring(0, adjuntosName.length() - 2);
				
				// Nuevo tracking
				MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
				track.setDateTrx(new Timestamp(System.currentTimeMillis()));
				track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
				track.setDescription("Se adjuntan archivos");
				track.setobservaciones(adjuntosName);
				track.setUY_R_Reclamo_ID(this.get_ID());
				track.saveEx();
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Valido que esta incidencia no tenga solicitudes de ajuste pendientes de confirmación.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 26/06/2013
	 * @see
	 * @return
	 */
	public boolean validateAjustesPendientes(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean value = true;
		
		try{
			
			sql = " select hand.uy_r_handlerajuste_id " +
				  " from uy_r_handlerajuste hand " +
				  " inner join uy_r_gestion gest on hand.uy_r_gestion_id = gest.uy_r_gestion_id " +
				  " inner join uy_r_reclamo rec on gest.uy_r_reclamo_id = rec.uy_r_reclamo_id " +
				  " where rec.uy_r_reclamo_id =? " +
				  " and (hand.isconfirmed ='N' and hand.isrejected='N') ";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.get_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = false;
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
	 * Envio de email al momento del ingreso de la incidencia.
	 * OpenUp Ltda. Issue #1077 
	 * @author Gabriel Vila - 01/07/2013
	 * @see
	 */
	public void sendCreateEmail(){

		// Me aseguro de no enviar email si el cliente no tiene direccion.
		if ((this.getEMail() == null) || this.getEMail().trim().equalsIgnoreCase("")){
			return;
		}
		
		if (!this.getEMail().contains("@")) return;
		
		try{
			
			// Si en la configuracion general esta parametrizado para que no se envie
			// un email automatico al momento de ingreso de una incidencia, no hago nada.
			MRConfig config = MRConfig.forValue(getCtx(), "general", null);
			if (!config.isSendEMailStart()){
				return;
			}
		
			// Si el tema de esta incidencia tiene parametrizado que NO se envian emails, salgo sin hacer nada. 
			MRCause cause = (MRCause)this.getUY_R_Cause();
			if (!cause.isNotificaEmail()){
				return;
			}
			
			StringBuilder body = new StringBuilder();			
			String subject = "Italcred - Atención al Cliente."; 
			String to = this.getEMail();
			
			body.append(config.getMailText());

			body = new StringBuilder(body.toString().replaceAll("#INCIDENCIA#", this.getDocumentNo()));
			body = new StringBuilder(body.toString().replaceAll("#CLIENTE#", this.getName()));
			
			this.sendEmail(body.toString(), subject, to, true, null);
			
			Timestamp today = new Timestamp(System.currentTimeMillis());
			
			// Tracking de email enviado
			MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(today);
			track.setAD_User_ID(this.getReceptor_ID());
			track.setDescription("Envio Automatico de Email por Creacion de Incidente.");
			//track.setobservaciones(this.getDescription());
			track.setUY_R_Reclamo_ID(this.get_ID());		
			track.saveEx();

			// Email enviado
			MRReclamoEmail recmail = new MRReclamoEmail(getCtx(), 0, get_TrxName());
			recmail.setUY_R_Reclamo_ID(this.get_ID());
			recmail.setStartDate(today);
			recmail.setEndDate(today);
			recmail.setIsExecuted(true);
			recmail.setReclamoEmailType(X_UY_R_ReclamoEmail.RECLAMOEMAILTYPE_CreacionDeIncidencia);
			recmail.setMailText(body.toString());
			recmail.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	
	/***
	 * Envio de email al momento de notificar resolucion a favor de la empresa.
	 * OpenUp Ltda. Issue #1077 
	 * @author Gabriel Vila - 01/07/2013
	 * @see
	 */
	public void sendNotificationEmail(String motivos){

		// Me aseguro de no enviar email si el cliente no tiene direccion.
		if ((this.getEMail() == null) || this.getEMail().trim().equalsIgnoreCase("")){
			return;
		}
		
		if (!this.getEMail().contains("@")) return;
		
		try{
			
			MRConfig config = MRConfig.forValue(getCtx(), "general", null);
			
			// OpenUp. Gabriel Vila. 23/06/2015. Issue #4380
			// Si en la configuracion general esta parametrizado para que no se envie
			// un email automatico al momento de ingreso de una incidencia, no hago nada.			
			if (!config.isSendEMailFinish()){
				return;
			}
			
			// Si el tema de esta incidencia tiene parametrizado que NO se envian emails, salgo sin hacer nada. 
			MRCause cause = (MRCause)this.getUY_R_Cause();
			if (!cause.isNotificaEmail()){
				return;
			}
			// Fin OpenUp. Issue #4380
			
			
			StringBuilder body = new StringBuilder();			
			String subject = "Italcred - Atención al Cliente."; 
			String to = this.getEMail();
			
			body.append(config.getMailText5());

			body = new StringBuilder(body.toString().replaceAll("#INCIDENCIA#", this.getDocumentNo()));
			body = new StringBuilder(body.toString().replaceAll("#FECHA_INCIDENCIA#", new SimpleDateFormat("dd/MM/yyyy").format(this.getDateTrx())));
			body = new StringBuilder(body.toString().replaceAll("#CLIENTE#", this.getName()));			
			body = new StringBuilder(body.toString().replaceAll("#MOTIVOS#", motivos));
			
			this.sendEmail(body.toString(), subject, to, true, null);
			
			Timestamp today = new Timestamp(System.currentTimeMillis());
			
			// Tracking de email enviado
			MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(today);
			track.setAD_User_ID(this.getReceptor_ID());
			track.setDescription("Envio Automatico de Email por Notificacion de Resolucion a favor de la Empresa.");
			//track.setobservaciones(this.getDescription());
			track.setUY_R_Reclamo_ID(this.get_ID());		
			track.saveEx();

			// Email enviado
			MRReclamoEmail recmail = new MRReclamoEmail(getCtx(), 0, get_TrxName());
			recmail.setUY_R_Reclamo_ID(this.get_ID());
			recmail.setStartDate(today);
			recmail.setEndDate(today);
			recmail.setIsExecuted(true);
			recmail.setReclamoEmailType(X_UY_R_ReclamoEmail.RECLAMOEMAILTYPE_NotificacionDeCanalEscrito);
			recmail.setMailText(body.toString());
			recmail.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}
	
	
	/***
	 * Envio de email al momento del cierre de la incidencia.
	 * OpenUp Ltda. Issue #1077 
	 * @author Gabriel Vila - 01/07/2013
	 * @see
	 */
	public void sendFinishEmail(){

		// Me aseguro de no enviar email si el cliente no tiene direccion.
		if ((this.getEMail() == null) || this.getEMail().trim().equalsIgnoreCase("")){
			return;
		}
		
		if (!this.getEMail().contains("@")) return;
		
		try{
			
			// Si en la configuracion general esta parametrizado para que no se envie
			// un email automatico al momento de ingreso de una incidencia, no hago nada.
			MRConfig config = MRConfig.forValue(getCtx(), "general", null);
			if (!config.isSendEMailFinish()){
				return;
			}
			
			// Si el tema de esta incidencia tiene parametrizado que NO se envian emails, salgo sin hacer nada. 
			MRCause cause = (MRCause)this.getUY_R_Cause();
			if (!cause.isNotificaEmail()){
				return;
			}
			
			Timestamp today = new Timestamp(System.currentTimeMillis());
			
			StringBuilder body = new StringBuilder();			
			String subject = "Italcred - Atención al Cliente."; 
			String to = this.getEMail();
		
			body.append(config.getMailText4());

			body = new StringBuilder(body.toString().replaceAll("#INCIDENCIA#", this.getDocumentNo()));
			body = new StringBuilder(body.toString().replaceAll("#FECHA_INCIDENCIA#", new SimpleDateFormat("dd/MM/yyyy").format(this.getDateTrx())));
			body = new StringBuilder(body.toString().replaceAll("#CLIENTE#", this.getName()));			
			
			this.sendEmail(body.toString(), subject, to, true, null);
			
			// Tracking de email enviado
			MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(today);
			track.setAD_User_ID(this.getReceptor_ID());
			track.setDescription("Envio Automatico de Email por Cierre de Incidencia.");
			//track.setobservaciones(this.getDescription());
			track.setUY_R_Reclamo_ID(this.get_ID());		
			track.saveEx();

			// Email enviado
			MRReclamoEmail recmail = new MRReclamoEmail(getCtx(), 0, get_TrxName());
			recmail.setUY_R_Reclamo_ID(this.get_ID());
			recmail.setStartDate(today);
			recmail.setEndDate(today);
			recmail.setIsExecuted(true);
			recmail.setReclamoEmailType(X_UY_R_ReclamoEmail.RECLAMOEMAILTYPE_CierreDeIncidencia);
			recmail.setMailText(body.toString());
			recmail.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	
	/***
	 * Envio de email correspondiente al primer aviso.
	 * OpenUp Ltda. Issue #1077 
	 * @author Gabriel Vila - 01/07/2013
	 * @see
	 */
	public String sendFirstAdviceEmail(){

		// Me aseguro de no enviar email si el cliente no tiene direccion.
		if ((this.getEMail() == null) || this.getEMail().trim().equalsIgnoreCase("")){
			return null;
		}
		
		if (!this.getEMail().contains("@")) return null;
		
		try{
			
			// Si en la configuracion general esta parametrizado para que no se envie
			// un email automatico al momento de ingreso de una incidencia, no hago nada.
			MRConfig config = MRConfig.forValue(getCtx(), "general", null);
			if (!config.isSendEMailFirstAdvice()){
				return null;
			}
			
			// Si el tema de esta incidencia tiene parametrizado que NO se envian emails, salgo sin hacer nada. 
			MRCause cause = (MRCause)this.getUY_R_Cause();
			if (!cause.isNotificaEmail()){
				return null;
			}

			
			StringBuilder body = new StringBuilder();			
			String subject = "Italcred - Atención al Cliente."; 
			String to = this.getEMail();
			
			body.append("<table><tr><td>" +
					"<p style='font-family:Arial; color:#104E8B; font-size:10pt'>Estimado Cliente:</p>" +
					"<p style='font-family:Arial; color:#104E8B; font-size:10pt'>Le informamos  que la incidencia Nº #INCIDENCIA# recepcionada el día #FECHA_INCIDENCIA# continua siendo " +
					"<br>analizada por nuestra Empresa. Para conocer más detalles sobre el estado en el que se " + 
					"<br>encuentra la misma, no dude en contactarnos a través de nuestro Call Center de Atención al " + 
					"<br>Cliente por el 2401 9999.</p>" +
					"<p style='font-family:Arial; color:#104E8B; font-size:10pt'>Atentamente,</p>" +
					"<p style='font-family:Arial; color:#104E8B; font-size:10pt'><b style='font-family:Arial; color:RGB(16,78,139); font-size:10pt'>Departamento de Atenci&oacute;n al Cliente</b></p>" +
					"<p style='font-family:Arial; color:#C2C2C2; font-size:8pt'>Tel&eacute;fono: 2401 9999" +
					"<br>E-mail: tumundocrece@italcred.com.uy" +
					"<br>Web: <a style='color:#0FECFC' href='http://www.italcred.com.uy'>www.italcred.com.uy</a></p>" +
					"<p style='font-family:Arial; color:#C2C2C2; font-size:8pt'><img src='http://www.italcred.com.uy/images/themes/default/logo-t-small.png' alt='Logo'>" +
					"<br>Seguinos en Facebook: <a style='color:#0FECFC' href='http://www.facebook.com/italcreduruguay'>Italcred Uruguay</a></p>" +
					"</td></tr></table>");

			body = new StringBuilder(body.toString().replaceAll("#INCIDENCIA#", this.getDocumentNo()));
			body = new StringBuilder(body.toString().replaceAll("#FECHA_INCIDENCIA#", this.getDateTrx().toString()));
			body = new StringBuilder(body.toString().replaceAll("#CLIENTE#", this.getCustomerName()));			
			
			this.sendEmail(body.toString(), subject, to, true, null);
			
			return body.toString();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/***
	 * Envio de email correspondiente al segundo aviso.
	 * OpenUp Ltda. Issue #1077 
	 * @author Gabriel Vila - 01/07/2013
	 * @see
	 */
	public String sendSecondAdviceEmail(){

		// Me aseguro de no enviar email si el cliente no tiene direccion.
		if ((this.getEMail() == null) || this.getEMail().trim().equalsIgnoreCase("")){
			return null;
		}
		
		if (!this.getEMail().contains("@")) return null;
		
		try{
			
			// Si en la configuracion general esta parametrizado para que no se envie
			// un email automatico al momento de ingreso de una incidencia, no hago nada.
			MRConfig config = MRConfig.forValue(getCtx(), "general", null);
			if (!config.isSendEMailSecondAdvice()){
				return null;
			}
			
			// Si el tema de esta incidencia tiene parametrizado que NO se envian emails, salgo sin hacer nada. 
			MRCause cause = (MRCause)this.getUY_R_Cause();
			if (!cause.isNotificaEmail()){
				return null;
			}

			
			StringBuilder body = new StringBuilder();			
			String subject = "Italcred - Atención al Cliente."; 
			String to = this.getEMail();
			
			body.append(config.getMailText2());

			body = new StringBuilder(body.toString().replaceAll("#INCIDENCIA#", this.getDocumentNo()));
			body = new StringBuilder(body.toString().replaceAll("#FECHA_INCIDENCIA#", new SimpleDateFormat("dd/MM/yyyy").format(this.getDateTrx())));
			body = new StringBuilder(body.toString().replaceAll("#CLIENTE#", this.getName()));
			
			this.sendEmail(body.toString(), subject, to, true, null);
			
			// Tracking de email enviado
			MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(new Timestamp(System.currentTimeMillis()));
			track.setAD_User_ID(this.getReceptor_ID());
			track.setDescription("Envio Automatico de Email por 15 días de atraso en Resolucion.");
			//track.setobservaciones(this.getDescription());
			track.setUY_R_Reclamo_ID(this.get_ID());		
			track.saveEx();

			return body.toString();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	
	/***
	 * Envio de email correspondiente al tercer aviso.
	 * OpenUp Ltda. Issue #1077 
	 * @author Gabriel Vila - 01/07/2013
	 * @see
	 */
	public String sendThirdAdviceEmail(){

		// Me aseguro de no enviar email si el cliente no tiene direccion.
		if ((this.getEMail() == null) || this.getEMail().trim().equalsIgnoreCase("")){
			return null;
		}
		
		if (!this.getEMail().contains("@")) return null;
		
		try{
			
			// Si en la configuracion general esta parametrizado para que no se envie
			// un email automatico al momento de ingreso de una incidencia, no hago nada.
			MRConfig config = MRConfig.forValue(getCtx(), "general", null);
			if (!config.isSendEMailThirdAdvice()){
				return null;
			}
			
			// Si el tema de esta incidencia tiene parametrizado que NO se envian emails, salgo sin hacer nada. 
			MRCause cause = (MRCause)this.getUY_R_Cause();
			if (!cause.isNotificaEmail()){
				return null;
			}
			
			StringBuilder body = new StringBuilder();			
			String subject = "Italcred - Atención al Cliente."; 
			String to = this.getEMail();
			
			body.append(config.getMailText3());

			body = new StringBuilder(body.toString().replaceAll("#INCIDENCIA#", this.getDocumentNo()));
			body = new StringBuilder(body.toString().replaceAll("#FECHA_INCIDENCIA#", new SimpleDateFormat("dd/MM/yyyy").format(this.getDateTrx())));
			body = new StringBuilder(body.toString().replaceAll("#CLIENTE#", this.getName()));			
			
			this.sendEmail(body.toString(), subject, to, true, null);
			
			// Tracking de email enviado
			MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(new Timestamp(System.currentTimeMillis()));
			track.setAD_User_ID(this.getReceptor_ID());
			track.setDescription("Envio Automatico de Email por 30 días de atraso en Resolucion.");
			//track.setobservaciones(this.getDescription());
			track.setUY_R_Reclamo_ID(this.get_ID());		
			track.saveEx();

			return body.toString();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	
	
	/***
	 * Envio de email automatico a clientes.
	 * OpenUp Ltda. Issue #1077 
	 * @author Gabriel Vila - 27/06/2013
	 * @see
	 * @param html
	 */
	public void sendEmail(String body, String subject, String to, boolean html, File attachment){

		try{
			if (to == null) return;
			if (to.trim().equalsIgnoreCase("")) return;

			MRConfig config = MRConfig.forValue(getCtx(), "general", null);
			MClient client = MClient.get(getCtx(), this.getAD_Client_ID());
			
			if ((config.getEMail() == null) || (config.getEMail().equalsIgnoreCase(""))){
				throw new AdempiereException("Falta configurar Direccion de Envio de Email");
			}
			
			if (!this.getEMail().contains("@")) return;
			
			EMail email = new EMail (client, config.getEMail(), to, subject, body, html);
			email.createAuthenticator(config.getEMail(), config.getPassword());

			if (attachment != null){
				email.addAttachment(attachment);	
			}
			
			if (email != null){
				String msg = email.send();
				if (EMail.SENT_OK.equals (msg))
				{
					log.info("Sent EMail " + subject + " to " + to);
				}
				else
				{
					log.warning("Could NOT Send Email: " + subject 
						+ " to " + to + ": " + msg);
				}
			}
			
		}

		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	
	/***
	 * Se generan las señales que se activaran para esta incidencia con la fecha-hora de 
	 * su activación. 
	 * OpenUp Ltda. Issue #1091 
	 * @author Gabriel Vila - 30/06/2013
	 * @see
	 */
	private void setReclamoSigns(){
		
		try{
			
			MRCause cause = (MRCause)this.getUY_R_Cause();

			// Si para este tema el plazo optimo y plazo final son distintos, 
			// voy a necesitar el semaforo en amarillo.
			if (cause.getMediumTerm() != cause.getDeadLine()){
				// Semaforo amarillo
				Timestamp fechaOptimo = OpenUpUtils.getToDate(this.getDateTrx(), Calendar.DAY_OF_MONTH, 
						cause.getMediumTerm(), this.getAD_Client_ID(), false, false, false);
				MRReclamoSign recSign = new MRReclamoSign(getCtx(), 0, get_TrxName());
				recSign.setUY_R_Reclamo_ID(this.get_ID());
				recSign.setUY_R_Sign_ID(MRSign.forValue(getCtx(), "yellow", null).get_ID());
				recSign.setStartDate(fechaOptimo);
				recSign.setIsExecuted(false);
				recSign.setReclamoSignType(X_UY_R_ReclamoSign.RECLAMOSIGNTYPE_Normal);
				recSign.saveEx();
			}
			
			// Semaforo rojo
			Timestamp fechaFinal = OpenUpUtils.getToDate(this.getDateTrx(), Calendar.DAY_OF_MONTH, 
					cause.getDeadLine(), this.getAD_Client_ID(), false, false, false);
			MRReclamoSign recSign = new MRReclamoSign(getCtx(), 0, get_TrxName());
			recSign.setUY_R_Reclamo_ID(this.get_ID());
			recSign.setUY_R_Sign_ID(MRSign.forValue(getCtx(), "red", null).get_ID());
			recSign.setStartDate(fechaFinal);			
			recSign.setIsExecuted(false);
			recSign.setReclamoSignType(X_UY_R_ReclamoSign.RECLAMOSIGNTYPE_Normal);			
			recSign.saveEx();
		
			
			// Semaforo critico en rojo cuando ademas no es DEFCON
			if (!this.isDenounced()){
				Timestamp fechaCritico = OpenUpUtils.getToDate(this.getDateTrx(), Calendar.DAY_OF_MONTH, 
						15, this.getAD_Client_ID(), true, true, true);
				MRReclamoSign criticalSign = new MRReclamoSign(getCtx(), 0, get_TrxName());
				criticalSign.setUY_R_Reclamo_ID(this.get_ID());
				criticalSign.setUY_R_Sign_ID(MRSign.forValue(getCtx(), "red", null).get_ID());
				criticalSign.setStartDate(fechaCritico);
				criticalSign.setIsExecuted(false);
				recSign.setReclamoSignType(X_UY_R_ReclamoSign.RECLAMOSIGNTYPE_Critico);			
				criticalSign.saveEx();
			}
			
		}
		catch (Exception ex){
			throw new AdempiereException(ex);
		}
		
	}

	/***
	 * Se generan los emails que se deberan enviar automaticamente para esta incidencia.
	 * OpenUp Ltda. Issue #1077 
	 * @author Gabriel Vila - 01/07/2013
	 * @see
	 */
	private void setReclamoEmails(){
		
		// Si no tengo direccion de email destino, no tiene sentido generar nada.
		if ((this.getEMail() == null) || this.getEMail().trim().equalsIgnoreCase("")){
			return;
		}
		
		try{
			
			Timestamp fechaEnvio = null;
			
			MRConfig config = MRConfig.forValue(getCtx(), "general", null);

			// Si en la configuracion general se indica envio de primer email de aviso de atraso
			if (config.isSendEMailFirstAdvice()){
				
				// Obtengo fecha de envio segun tiempo parametrizado en horas
				fechaEnvio = OpenUpUtils.getToDate(this.getDateTrx(), Calendar.HOUR_OF_DAY, 
							 config.getFirstEmailHourInterval(), this.getAD_Client_ID(), false, false, false);

				MRReclamoEmail recEmail = new MRReclamoEmail(getCtx(), 0, get_TrxName());
				recEmail.setUY_R_Reclamo_ID(this.get_ID());
				recEmail.setStartDate(fechaEnvio);
				recEmail.setIsExecuted(false);
				recEmail.setReclamoEmailType(X_UY_R_ReclamoEmail.RECLAMOEMAILTYPE_PrimerAviso);
				recEmail.saveEx();
				
				fechaEnvio = null;
			}

			// Si en la configuracion general se indica envio de segundo email de aviso de atraso
			if (config.isSendEMailSecondAdvice()){
				
				// Obtengo fecha de envio segun tiempo parametrizado en horas
				fechaEnvio = OpenUpUtils.getToDate(this.getDateTrx(), Calendar.DAY_OF_MONTH, 
							 config.getSecondEmailDayInterval(), this.getAD_Client_ID(), true, true, true);

				MRReclamoEmail recEmail = new MRReclamoEmail(getCtx(), 0, get_TrxName());
				recEmail.setUY_R_Reclamo_ID(this.get_ID());
				recEmail.setStartDate(fechaEnvio);
				recEmail.setIsExecuted(false);
				recEmail.setReclamoEmailType(X_UY_R_ReclamoEmail.RECLAMOEMAILTYPE_SegundoAviso);
				recEmail.saveEx();
				
				fechaEnvio = null;
			}
			
			// Si en la configuracion general se indica envio de tercer email de aviso de atraso
			if (config.isSendEMailThirdAdvice()){
				
				// Obtengo fecha de envio segun tiempo parametrizado en horas
				fechaEnvio = OpenUpUtils.getToDate(this.getDateTrx(), Calendar.DAY_OF_MONTH, 
							 config.getThirdEmailDayInterval(), this.getAD_Client_ID(), true, true, true);

				MRReclamoEmail recEmail = new MRReclamoEmail(getCtx(), 0, get_TrxName());
				recEmail.setUY_R_Reclamo_ID(this.get_ID());
				recEmail.setStartDate(fechaEnvio);
				recEmail.setIsExecuted(false);
				recEmail.setReclamoEmailType(X_UY_R_ReclamoEmail.RECLAMOEMAILTYPE_TercerAviso);
				recEmail.saveEx();
				
				fechaEnvio = null;
			}
			
		}
		catch (Exception ex){
			throw new AdempiereException(ex);
		}
	}
	
	/***
	 * Envia email de aviso segun tipo de aviso recibido.
	 * OpenUp Ltda. Issue #1077 
	 * @author Gabriel Vila - 01/07/2013
	 * @see
	 * @param emailType
	 */
	public String sendAdviceEmail(String emailAdviceType){
	
		try{
			
			String mailText = null;
			
			// Si el tema de esta incidencia tiene parametrizado que NO se envian emails, salgo sin hacer nada. 
			MRCause cause = (MRCause)this.getUY_R_Cause();
			if (!cause.isNotificaEmail()){
				return null;
			}
			
			// Proceso segun tipo de aviso de email 
			if (emailAdviceType.equalsIgnoreCase(X_UY_R_ReclamoEmail.RECLAMOEMAILTYPE_PrimerAviso)){
				mailText = this.sendFirstAdviceEmail();
			}
			else if (emailAdviceType.equalsIgnoreCase(X_UY_R_ReclamoEmail.RECLAMOEMAILTYPE_SegundoAviso)){
				mailText = this.sendSecondAdviceEmail();
			}
			else if (emailAdviceType.equalsIgnoreCase(X_UY_R_ReclamoEmail.RECLAMOEMAILTYPE_TercerAviso)){
				mailText = this.sendThirdAdviceEmail();
			}
			
			return mailText;
			
		}
		catch (Exception ex){
			throw new AdempiereException(ex);
		}
		
	}

	/***
	 * Valido cedula de indentidad contra base de FinancialPro
	 * OpenUp Ltda. Issue #281 
	 * @author Gabriel Vila - 12/07/2013
	 * @see
	 */
	public boolean validateCI() {

		Connection con = null;
		ResultSet rs = null;

		boolean result = false;
		
		try{
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			con = this.getFDUConnection(fduData);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select stapenom "+
				         " from q_clientes_adempiere " +
				         " where clicod=" + this.getCedula();
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				result = true;
			}

			rs.close();
			con.close();		
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
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
		
		return result;
	}

	/***
	 * Setea documento por defecto para este documento.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 12/09/2013
	 * @see
	 */
	public void setDefaultDocType() {
		MDocType doc = MDocType.forValue(getCtx(), "reclamo", null);
		if (doc.get_ID() > 0) this.setC_DocType_ID(doc.get_ID());
	}

	@Override
	public void changeMQuery(MQuery query) {
	}

	/***
	 * Obtiene y retorna modelo segun numero de documento recibido.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 13, 2015
	 * @param ctx
	 * @param documentNo
	 * @param trxName
	 * @return
	 */
	public static MRReclamo forDocumentNo(Properties ctx, String documentNo, String trxName){
		
		String whereClause = X_UY_R_Reclamo.COLUMNNAME_DocumentNo + "='" + documentNo + "'";
		
		MRReclamo model =  new org.compiere.model.Query(ctx, I_UY_R_Reclamo.Table_Name, whereClause, trxName).first();
		
		return model;
	}
	
	
}

