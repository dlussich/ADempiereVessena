/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 25/05/2015
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MRole;
import org.compiere.model.MSequence;
import org.compiere.model.MUser;
import org.compiere.model.MUserRoles;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.wf.MWFNode;

/**
 * org.openup.model - MUserReq
 * OpenUp Ltda. Issue #4168 
 * Description: Modelo para flujo de solicitud y aprobaciï¿½n de registro de usuarios.
 * @author Gabriel Vila - 25/05/2015
 * @see
 */
public class MUserReq extends X_UY_UserReq implements DocAction, IDynamicWF {

	private String processMsg = null;
	private boolean justPrepared = false;
	private boolean isParcialApproved = false;
	
	private static final long serialVersionUID = -4633735408628745905L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_UserReq_ID
	 * @param trxName
	 */
	public MUserReq(Properties ctx, int UY_UserReq_ID, String trxName) {
		super(ctx, UY_UserReq_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUserReq(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {

		try {

			// Valido rol y permiso para solicitud
			MBGConfigBursa config = MBGConfigBursa.forClient(getCtx(), null);
			String value = this.validateRole(config.getRole2_ID());
			if (value != null){
				throw new AdempiereException(value);
			}

			
			if ((this.getApprovalText() == null) || (this.getApprovalText().equalsIgnoreCase(""))){
				throw new AdempiereException("Debe ingresar un Comentario en el Detalle de Aprobaciï¿½n");
			}
				
			this.setIsApproved(true);
			
			this.setApprovalStatus(APPROVALSTATUS_AprobadoPorBAGSA);
			this.setDateApproved(new Timestamp(System.currentTimeMillis()));
			this.setApprovedType(APPROVEDTYPE_APROBADO);
			this.setApprovalUser_ID(Env.getAD_User_ID(Env.getCtx()));
			
			// Tracking
			MUserReqTracking track = new MUserReqTracking(getCtx(), 0, get_TrxName());
			track.setUY_UserReq_ID(this.get_ID());
			track.setDateTrx(this.getDateApproved());
			track.setAD_User_ID(this.getApprovalUser_ID());
			track.setDescription("Solicitud Aprobada");
			track.setobservaciones(this.getApprovalText().trim());
			track.saveEx();
			
			this.setDocStatus(DocumentEngine.STATUS_Approved);
			this.setDocAction(DocAction.ACTION_None);

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

		return true;

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
	 * @see
	 * @return
	 */
	@Override
	public boolean rejectIt() {

		try {

			// Valido rol y permiso para solicitud
			MBGConfigBursa config = MBGConfigBursa.forClient(getCtx(), null);
			String value = this.validateRole(config.getRole2_ID());
			if (value != null){
				throw new AdempiereException(value);
			}
			
			if ((this.getApprovalText() == null) || (this.getApprovalText().equalsIgnoreCase(""))){
				throw new AdempiereException("Debe ingresar un Comentario en el Detalle de Aprobaciï¿½n");
			}
				
			this.setIsApproved(false);
			
			this.setApprovalStatus(APPROVALSTATUS_RechazadoPorBAGSA);
			this.setDateApproved(new Timestamp(System.currentTimeMillis()));
			this.setApprovedType(APPROVEDTYPE_RECHAZADO);
			this.setApprovalUser_ID(Env.getAD_User_ID(Env.getCtx()));
			
			// Tracking
			MUserReqTracking track = new MUserReqTracking(getCtx(), 0, get_TrxName());
			track.setUY_UserReq_ID(this.get_ID());
			track.setDateTrx(this.getDateApproved());
			track.setAD_User_ID(this.getApprovalUser_ID());
			track.setDescription("Solicitud Rechazada");
			track.setobservaciones(this.getApprovalText().trim());
			track.saveEx();
			
			this.setDocStatus(DocumentEngine.STATUS_NotApproved);
			this.setDocAction(DOCACTION_Request);

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		

		return true;

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 25/05/2015
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

		// Parametros generales del modulo de bolsa
		MBGConfigBursa config = MBGConfigBursa.forClient(getCtx(), null);
		
		// Procedo segun tipo de documento
		MDocType doc = (MDocType)this.getC_DocType();
		if (doc.getValue().equalsIgnoreCase("bgreqptobolsa")){

			// Valido que antes de completarse se haya aprobado esta solicitud
			if (!this.isApproved()){
				this.processMsg = "No se puede completar el Documento ya que el mismo NO esta Aprobado.";
				return DocAction.STATUS_Invalid;
			}
			
			// Genero registro y asociaciones de nuevo puesto de bolsa (ahora ya esta aprobado)
			MBGBursa bolsa = new MBGBursa(getCtx(), 0, get_TrxName());
			bolsa.setName(this.getName());
			bolsa.setValue(this.getCode());
			bolsa.setC_BPartner_ID(this.getC_BPartner_ID());
			bolsa.setAD_User_ID(this.getRequestedUser_ID());
			
			bolsa.saveEx();
			
			// Seteo nueva fecha de vencimiento para usuario de bolsa aprobado			
			MUser bolsaUser = new MUser(getCtx(), bolsa.getAD_User_ID(), get_TrxName());
			bolsaUser.set_ValueOfColumn("DueDate", "01/01/2050");
			bolsaUser.saveEx();
			
			// Asigno rol al usuario de bolsa para que pueda operar en bolsa
			MUserRoles bolsarol = new MUserRoles(getCtx(), bolsa.getAD_User_ID(), config.getRole3_ID(), get_TrxName());
			bolsarol.saveEx();
			
			List<MUserReqBroker> brokers = this.getBrokers();
			for (MUserReqBroker reqBroker: brokers){
			
				MUser user = new MUser(getCtx(), 0, get_TrxName());
				String userName = reqBroker.getFirstName().toLowerCase().charAt(0) + reqBroker.getFirstSurname().toLowerCase()+"-"+ this.getCode();
				user.setName(userName);
				user.setValue(userName);
				user.setPassword(userName);
				user.setEMail(this.getEMail());
				user.set_ValueOfColumn("canlogin", true);				
				user.saveEx();

				// Asigno rol al usuario
				MUserRoles urol = new MUserRoles(getCtx(), user.get_ID(), config.getRole3_ID(), get_TrxName());
				urol.saveEx();
				
				MBGBroker bursaBroker = new MBGBroker(getCtx(), 0, get_TrxName());
				bursaBroker.setUY_BG_Bursa_ID(bolsa.get_ID());							
				bursaBroker.setAD_User_ID(user.get_ID());				
				bursaBroker.setFirstName(reqBroker.getFirstName());
				bursaBroker.setSecondName(reqBroker.getSecondName());
				bursaBroker.setFirstSurname(reqBroker.getFirstSurname());
				bursaBroker.setSecondSurname(reqBroker.getSecondSurname());
				bursaBroker.setName(reqBroker.getFirstName()+"" +reqBroker.getFirstSurname());
				bursaBroker.setEMail(reqBroker.getEMail());			
				bursaBroker.setMobile1(reqBroker.getMobile1());
				bursaBroker.setIsSmartPhone1(this.isPhone_Ident());
				bursaBroker.setMobile2(reqBroker.getMobile2());
				bursaBroker.setIsSmartPhone2(this.isPhone_Ident_2());	
				bursaBroker.setValue(this.getCode());
				bursaBroker.saveEx();
				
			}
			

		}
		//Cliente 
		else if (doc.getValue().equalsIgnoreCase("bgreqcliente")){

			// Valido que antes de completarse se haya aprobado esta solicitud
			if (!this.isApproved()){
				this.processMsg = "No se puede completar el Documento ya que el mismo NO esta Aprobado.";
				return DocAction.STATUS_Invalid;
			}
			//Verifico si existe cliente con dicha cedula de identidad
			MBGCustomer cust = MBGCustomer.forCedula(getCtx(),this.getCedula(),null);
			
			if(null==cust){
				// Genero un nuevo usuario en la base
				MUser user = new MUser(getCtx(), 0, get_TrxName());
				String userName = this.getFirstName().toLowerCase().charAt(0) + this.getFirstSurname().toLowerCase()+"-"+ this.getCode();
				user.setName(userName);
				user.setValue(userName);
				user.setPassword(userName);
				user.setEMail(this.getEMail());
				user.set_ValueOfColumn("canlogin", true);
				user.saveEx();
				
				// Asigno rol al usuario
				MUserRoles urol = new MUserRoles(getCtx(), user.get_ID(), config.getRole3_ID(), get_TrxName());
				urol.saveEx();
				
				// Genero un nuevo cliente de bolsa en la base
				MBGCustomer model = new MBGCustomer(getCtx(), 0, get_TrxName());
				//Agrego el usuario
				model.setAD_User_ID(user.get_ID());
				model.setName(this.getName());
				model.setValue(this.getCode());
				model.setRUC(this.getRUC());
				model.setCedula(this.getCedula());
				model.setFirstName(this.getFirstName());
				model.setSecondName(this.getSecondName());
				model.setFirstSurname(this.getFirstSurname());
				model.setSecondSurname(this.getSecondSurname());
				model.setC_City_ID(this.getC_City_ID());
				model.setC_Region_ID(this.getC_Region_ID());
				model.setPhone(this.getPhone());
				model.setPhone_2(this.getPhone_2());
				model.setEMail(this.getEMail());
				model.setUY_BG_UserActivity_ID(this.getUY_BG_UserActivity_ID());
				model.setIsSmartPhone1(this.isPhone_Ident());
				model.setIsSmartPhone2(this.isPhone_Ident_2());	
				model.setAddress1(this.getAddress1());
				model.saveEx();
				
				//Al agregar un nuevo cliente lo debo asociar como cliente a ese puesto de bolsa
				MBGBursaCust bursaCust = new MBGBursaCust(getCtx(), 0, get_TrxName());
				bursaCust.setUY_BG_Bursa_ID(this.getUY_BG_Bursa_ID());
				bursaCust.setUY_BG_Customer_ID(model.get_ID());			
				bursaCust.saveEx();
			
			//OpenUp Ltda. SBouissa 03-09-2015 
			//Se debe controlar si existe el cliente con dicha cedula,en tal caso 
			//solo se asocia el cliente a la bolsa actual	
			}else{
				MBGBursaCust bursaCust = MBGBursaCust.forBursaAndCust(getCtx(),this.getUY_BG_Bursa_ID(),cust.get_ID(),null);
				if(null==bursaCust){
					//Si el cliente existe pero no esta asociado a la bolsa
					//lo debo asociar como cliente a este puesto de bolsa
					bursaCust = new MBGBursaCust(getCtx(), 0, get_TrxName());
					bursaCust.setUY_BG_Bursa_ID(this.getUY_BG_Bursa_ID());
					bursaCust.setUY_BG_Customer_ID(cust.get_ID());			
					bursaCust.saveEx();
				}else{
					throw new AdempiereException("El cliente con CI: "+this.getCedula()
							+" ya se encuentra asociado a la bolsa "+this.getUY_BG_Bursa().getName());
				}
			}
			
			
		}
		else if (doc.getValue().equalsIgnoreCase("bgreqsubcliente")){
			
			//Verifico si existe cliente con dicha cedula de identidad
			MBGSubCustomer subCust = MBGSubCustomer.forCedula(getCtx(),this.getCedula(),null);
			
			if(null==subCust){
				// Genero un nuevo usuario en la base
				MUser user = new MUser(getCtx(), 0, get_TrxName());
				String userName = this.getFirstName().toLowerCase().charAt(0) + this.getFirstSurname().toLowerCase()+"-"+ this.getCode();
				user.setName(userName);
				user.setValue(userName);
				user.setPassword(userName);
				user.setEMail(this.getEMail());
				user.set_ValueOfColumn("canlogin", true);
				user.saveEx();
				
				// Asigno rol al usuario
				MUserRoles urol = new MUserRoles(getCtx(), user.get_ID(), config.getRole3_ID(), get_TrxName());
				urol.saveEx();
				
				// Genero un nuevo subcliente de bolsa en la base
				subCust = new MBGSubCustomer(getCtx(), 0, get_TrxName());
				subCust.setName(this.getName());
				subCust.setValue(this.getCode());			
				subCust.setCedula(this.getCedula());
				subCust.setFirstName(this.getFirstName());
				subCust.setSecondName(this.getSecondName());
				subCust.setFirstSurname(this.getFirstSurname());
				subCust.setSecondSurname(this.getSecondSurname());
				subCust.setC_City_ID(this.getC_City_ID());
				subCust.setC_Region_ID(this.getC_Region_ID());
				subCust.setPhone(this.getPhone());
				subCust.setPhone_2(this.getPhone_2());
				subCust.setEMail(this.getEMail());
				subCust.setUY_BG_UserActivity_ID(this.getUY_BG_UserActivity_ID());
				subCust.setIsSmartPhone1(this.isPhone_Ident());
				subCust.setIsSmartPhone2(this.isPhone_Ident_2());	
				subCust.setAddress1(this.getAddress1());
				
				//Agrego el usuario
				subCust.setAD_User_ID(user.get_ID());
				
				//El subcliente se registra en un puesto de bolsa para un cliente 
				//OpenUp SBouissa 03-09-2015 se comentan las siguientes lineas ya que cambia la log. de negocio.
//				subCust.setUY_BG_Bursa_ID(this.getUY_BG_Bursa_ID());
//				subCust.setUY_BG_Customer_ID(this.getUY_BG_Customer_ID());			
				subCust.saveEx();
				
				//Al agregar un nuevo subcliente lo debo asociar como subcliente al cliente correspondiente
				MBGCustSubCust custSubCust = new MBGCustSubCust(getCtx(), 0, get_TrxName());
				custSubCust.setUY_BG_SubCustomer_ID(subCust.get_ID());
				custSubCust.setUY_BG_Customer_ID(this.getUY_BG_Customer_ID());			
				custSubCust.saveEx();
			}
			//OpenUp Ltda. SBouissa 03-09-2015  
			//Se debe controlar si existe el subcliente con dicha cedula y 
			else{
				MBGCustSubCust custSubCust = MBGCustSubCust.forSubCustAndCust(getCtx(),
						subCust.get_ID(),this.getUY_BG_Customer_ID(),null);
				if(null==custSubCust){
					//Si el cliente existe pero no esta asociado al cliente
					//lo debo asociar como subcliente del cliente
					custSubCust = new MBGCustSubCust(getCtx(), 0, get_TrxName());
					custSubCust.setUY_BG_SubCustomer_ID(subCust.get_ID());
					custSubCust.setUY_BG_Customer_ID(this.getUY_BG_Customer_ID());			
					custSubCust.saveEx();
				}else{
					throw new AdempiereException("El subcliente con CI: "+this.getCedula()
							+" ya se encuentra asociado al cliente "+this.getUY_BG_Customer().getName());
				}
				
			}
		}
		
		/**OpenUp Ltda Issue# (Movilidad)
		 * SBouissa 14/07/2015
		 */
		else if (doc.getValue().equalsIgnoreCase("bgreqmobile")){

			// Valido que antes de completarse se haya aprobado esta solicitud
			if (!this.isApproved()){
				this.processMsg = "No se puede completar el Documento ya que el mismo NO esta Aprobado.";
				return DocAction.STATUS_Invalid;
			}
			this.setDateTrx(getDateTrx());
			// Genero un nuevo cliente de bolsa en la base
			MUser model = new MUser(getCtx(), 0, get_TrxName());
			model.setName(this.getCode());//username
			model.setPassword(this.get_ValueAsString("Password"));
			model.setDescription(this.getName());
			model.setValue(this.getCode()); //username			
			model.setEMail(this.getEMail());
			model.setPhone(this.getPhone());			
			model.setIsActive(true);
			model.set_ValueOfColumn("canlogin", true);
			model.saveEx();
					
			// Asigno rol al usuario
			MUserRoles urol = new MUserRoles(getCtx(), model.get_ID(), config.getRole3_ID(), get_TrxName());
			urol.saveEx();
						
		}//FIN  SBouissa 14/07/2015
		
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 25/05/2015
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
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
	 * @author Gabriel Vila - 25/05/2015
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}


	/***
	 * Accion de Solicitud.
	 * OpenUp Ltda. Issue #4168 
	 * @author Gabriel Vila - 26/05/2015
	 * @see
	 * @return
	 */
	public boolean requestIt() {
		
		boolean result = true;
		
		try {
			
			MDocType doc = (MDocType)this.getC_DocType();
			
			// Valido rol y permiso para solicitud
			MBGConfigBursa config = MBGConfigBursa.forClient(getCtx(), null);
			String value = this.validateRole(config.getRole1_ID());

			if (value != null){
				throw new AdempiereException(value);
			}

			// Valido aceptacion de condiciones para registro de puestos de bolsa
			if (doc.getValue().equalsIgnoreCase("bgreqptobolsa")){
				if (!this.isSelected()){
					throw new AdempiereException("Debe Aceptar las Condiciones del Solicitante antes de proceder con esta solicitud");
				}
			}
			
			// Limpio datos de aprobacion
			DB.executeUpdateEx(" update UY_UserReq set ApprovedType = null, DateApproved = null, ApprovalUser_ID = null, ApprovalText = null where UY_UserReq_ID =" + this.get_ID(), get_TrxName());
			
			// Estado de solicitud
			this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionBAGSA);
			
			// Seteo datos del solicitante
			this.setDateRequested(new Timestamp(System.currentTimeMillis()));
			this.setRequestedUser_ID(Env.getAD_User_ID(Env.getCtx()));

			// Genero nuevo socio de negocio para este punto de bolsa.
			// Esto es para poder ingresar factura y recibo de cobro al puesto, lo cual es un paso previo y necesario para su posterior aprobaciï¿½n.			
			MBPartner bp = new MBPartner(getCtx(), 0, get_TrxName());
			bp.setName(this.getName());
			bp.setName2(this.getName());
			bp.setIsSummary(false);
			MBPGroup bpg = MBPGroup.getDefault(getCtx());
			if ((bpg != null) && (bpg.get_ID() > 0)){
				bp.setC_BP_Group_ID(bpg.get_ID());
			}
			bp.setIsVendor(false);
			bp.setIsCustomer(true);
			bp.setIsEmployee(false);
			bp.setIsSalesRep(false);
			bp.setDUNS(this.getRUC());
			bp.setAD_Language(Env.getAD_Language(Env.getCtx()));
			bp.set_ValueOfColumn("IsPosto", "N");						
			bp.saveEx();

			this.setC_BPartner_ID(bp.get_ID());
			
			// Tracking
			MUserReqTracking track = new MUserReqTracking(getCtx(), 0, get_TrxName());
			track.setUY_UserReq_ID(this.get_ID());
			track.setDateTrx(this.getDateRequested());
			track.setAD_User_ID(this.getRequestedUser_ID());
			track.setDescription("Solicitud Creada");
			track.setobservaciones(this.getDescription());
			track.saveEx();
			
			// Documento
			this.setDocStatus(DocumentEngine.STATUS_Requested);
			this.setDocAction(DocAction.ACTION_Approve);

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return result;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		try {

			MDocType doc = (MDocType)this.getC_DocType();
			
			// Valido segun accion de documento (solicitar o aprobar/rechazar)
			if ((this.getDocStatus().equalsIgnoreCase(DOCSTATUS_Drafted))
					|| (this.getDocStatus().equalsIgnoreCase(DOCSTATUS_NotApproved))){

				// No permito que un usuario distinto al usuario creador de esta solicitud, modifique este registro.
				if (!newRecord){
					if (Env.getAD_User_ID(Env.getCtx()) != this.getRequestedUser_ID()){
						log.saveError(null, "El usuario actual no tiene permisos para modificar esta Solicitud creada por otro usuario.");
			            return false;
					}
				}
				else{
			
					// Seteo usuario solicitante en un regitro nuevo
					this.setRequestedUser_ID(Env.getAD_User_ID(Env.getCtx()));
					
					// Valido rol y permiso en la creaciï¿½n del registro
					MBGConfigBursa config = MBGConfigBursa.forClient(getCtx(), null);
					String value = this.validateRole(config.getRole1_ID());
					
					if (value != null){
						log.saveError(null, value);
			            return false;
					}
					
					// Si es un documento de registro de cliente o subcliente
					if (doc.getValue().equalsIgnoreCase("bgreqcliente") || doc.getValue().equalsIgnoreCase("bgreqsubcliente")){
						// Concateno nombres y apellidos en el campo name
						this.setName(this.getFirstName() + " " +this.getFirstSurname());
						if (this.getSecondName()!=null && this.getSecondSurname()==null)		         
							this.setName(this.getFirstName() + " " +this.getSecondName()+" "+this.getFirstSurname());
						if (this.getSecondName()!=null && this.getSecondSurname()!=null)
							this.setName(this.getFirstName() + " " +this.getSecondName()+" "+this.getFirstSurname() + " " + this.getSecondSurname());
						if (this.getSecondName()==null && this.getSecondSurname()!=null)   
							this.setName(this.getFirstName() +" "+this.getFirstSurname() + " " + this.getSecondSurname());
					}
					
					// SBT 04-09-2015 Si es un reg de cliente o bolsa se debe controlar ruc y cedula
					if(doc.getValue().equalsIgnoreCase("bgreqptobolsa")||doc.getValue().equalsIgnoreCase("bgreqcliente")){
						//Se debe controlar cantidad de caracterer y posteriormente formato CI y RUC
						
						if(!(this.getRUC().trim().length()==14)){
							int cant = this.getRUC().trim().length();
							System.out.println(cant);
							throw new AdempiereException("RUC incorrecto");
						}
						if(!(this.getCedula().trim().length()==16)){
							int cant = this.getCedula().trim().length();
							System.out.println(cant);
							throw new AdempiereException("Cedula incorrecta");
						}else{
							String [] datos = this.getCedula().trim().split("-");
							if(datos.length!=3){
								throw new AdempiereException("Cedula incorrecta, formato 123-123456-12345");
							}
						}
					}
					//Controles unicamente para registro de clientes
					if(doc.getValue().equalsIgnoreCase("bgreqcliente")){
						
						if(null != this.getEMail()){
							if(!this.getEMail().trim().contains("@")){
								//return false;
								throw new AdempiereException("Email no válido");
							}
						}else{
							throw new AdempiereException("Email campo requerido");
						}
						if(null != this.getEmail2()){
							if(!this.getEmail2().trim().contains("@")){
								//return false;
								throw new AdempiereException("Email secundario, no válido");
							}
						}
						if (this.getCode()!=null){
							int idSequenciaCodigo = 1004245; //(UY_Seq_Code_RegCliente)
							MSequence seq = new MSequence(getCtx(), idSequenciaCodigo, get_TrxName());
							int nextID = seq.getNextID();//obtengo el valor y ya se incrementa al siguiente
							if(!(seq.getPrefix()+String.valueOf(nextID)).equals(this.getCode())){
								throw new AdempiereException("Error en codigo de cliente");
							}else{
								//seq.setCurrentNext(nextID+1);
								//Seteo las primeras 3 letras de la bolsa
								//this.setCode(this.getUY_BG_Bursa().getName().substring(0, 3)+"-"+nextID);
								seq.saveEx();
							}
						}
					}
					if(doc.getValue().equalsIgnoreCase("bgreqsubcliente")){
						
						//Se debe controlar email si hay dato
						if(null != this.getEMail()){
							if(!this.getEMail().trim().contains("@")){
								throw new AdempiereException("Email no válido");
							}
						}else{
							throw new AdempiereException("Email campo requerido");
						}
						if(null != this.getEmail2()){
							if(!this.getEmail2().trim().contains("@")){
								//return false;
								throw new AdempiereException("Email secundario, no válido");
							}
						}
						
						//Se debe controlar cantidad de caracterer y posteriormente formato CI 
						
						if(!(this.getCedula().trim().length()==16)){
							int cant = this.getCedula().trim().length();
							System.out.println(cant);
							throw new AdempiereException("Cedula incorrecta");
						}else{
							String [] datos = this.getCedula().trim().split("-");
							if(datos.length!=3){
								throw new AdempiereException("Cedula incorrecta, formato 123-123456-12345");
							}
						}
						if (this.getCode()!=null){
							int idSequenciaCodigo = 1004250; //(UY_Seq_Code_RegSubCliente)
							MSequence seq = new MSequence(getCtx(), idSequenciaCodigo, get_TrxName());
							int nextID = seq.getNextID();//obtengo el valor y ya se incrementa al siguiente
							if(!(seq.getPrefix()+String.valueOf(nextID)).equals(this.getCode())){
								throw new AdempiereException("Error en codigo de sub cliente");
							}else{
								//seq.setCurrentNext(nextID+1);
								//Seteo las primeras 3 letras del cliente
								//this.setCode(this.getUY_BG_Customer().getName().substring(0, 3)+"-"+nextID);
								seq.saveEx();
							}
						}
						
					}
					
					//* INI  01-09-2015 SBouissa revision trader #Issue
					//  Si se crea un puesto de bolsa:
					if(doc.getValue().equalsIgnoreCase("bgreqptobolsa")){
					//Se debe controlar que el RUC,Direccion y Telefono no sean vacíos
					 if(null==this.getRUC() || null == this.getAddress2() || null ==this.getPhone()){
						 throw new AdempiereException("Los campos RUC, Dirección, Teléfono y Email son obligatorios");
					 }
					 if("".equals(this.getRUC()) || "".equals(this.getAddress2()) || this.getPhone().length()==5){
						 throw new AdempiereException("Los campos RUC, Dirección, Teléfono y Email son obligatorios");
					 }
					 if(this.getPhone().length()<13){
						 throw new AdempiereException("Teléfono incorrecto");
					 }
					 				 
					//Se debe controlar email
						if(null != this.getEMail()){
							if(!this.getEMail().trim().contains("@")){
								throw new AdempiereException("Email primario, no válido");
							}
						}else{
							throw new AdempiereException("Email primario obligatorio"); //hablado con roberto el 02/09/15 SBT
						}
						if(null != this.getEmail2()){
							if(!this.getEmail2().trim().contains("@")){
								//return false;
								throw new AdempiereException("Email secundario, no válido");
							}
						}
					
						//04-09-2015 SBT
						 if(null!=this.getCode()){
							 int idSequenciaCodigo = 1004254; //(UY_Seq_Code_RegPtoBolsa)
							 MSequence seq = new MSequence(getCtx(), idSequenciaCodigo, get_TrxName());
							 int nextID = seq.getNextID();//obtengo el valor y ya se incrementa al siguiente
							 if(!String.valueOf(seq.getPrefix()+nextID).equals(this.getCode())){
							 	throw new AdempiereException("Error en codigo de sub cliente");
							 }else{
							 	//seq.setCurrentNext(nextID+1);
							 	seq.saveEx();
							 }
						 }
						 //FIN 04-09-2015	
					//* FIN  01-09-2015 SBouissa revision trader #Issue
				}
				
				}
				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return true;
	}

	@Override
	public int getDynamicWFResponsibleID(MWFNode node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setApprovalInfo(int AD_WF_Responsible_ID, String textMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getWFActivityDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWFActivityHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean IsParcialApproved() {
		
		return this.isParcialApproved;
		
	}

	@Override
	public void processAutomaticApproval() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processAutomaticComplete() {

		try 
		{
			// Completo de manera automatico al aprobar documento
			if (!this.processIt(ACTION_Complete)){
				if (this.getProcessMsg() != null){
					throw new AdempiereException(this.getProcessMsg());
				}
			}
			else{
				this.saveEx();
			}
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
	}
	
	
	/***
	 * Verifica si estoy logeado con un rol recibido por parametro.
	 * En caso de no coincidir verifico si el rol de login es padre del rol recibido, en cuyo caso es valido igual.
	 * OpenUp Ltda. Issue #4168 
	 * @author Gabriel Vila - 27/05/2015
	 * @see
	 * @param validRoleID
	 * @return
	 */
	private String validateRole(int validRoleID){

		String value = null;
		boolean isValid = false;
		
		try {

			// Rol de Login
			MRole roleLogin = MRole.get(getCtx(), Env.getAD_Role_ID(Env.getCtx()));

			if ((roleLogin == null) || (roleLogin.get_ID() <= 0)){
				value = "No se pudo obtener Rol actual para el proceso de validaciï¿½n de permisos.";
				return value;
			}
			
			// Si no estoy logeado con el rol parametrizado para esta accion
			if (validRoleID != roleLogin.get_ID()){

				// Verifico si el rol parametrizado es hijo del rol de login
				List<MRole> childRoles = roleLogin.getIncludedRoles(true);

				for (MRole childRole: childRoles){
					if (childRole.get_ID() == validRoleID){
						isValid = true;
						break;
					}
				}
			}
			else{
				isValid = true;
			}

			if (!isValid){
				value = "El usuario actual no tiene permisos para esta accion en este Documento.";
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return value;
	}

	
	/***
	 * Obtiene y retorna lineas de corredores de este documento
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 05/06/2015
	 * @see
	 * @return
	 */
	private List<MUserReqBroker> getBrokers(){
		
		String whereClause = X_UY_UserReqBroker.COLUMNNAME_UY_UserReq_ID + "=" + this.get_ID();
		
		List<MUserReqBroker> lines = new Query(getCtx(), I_UY_UserReqBroker.Table_Name, whereClause, get_TrxName()).list();
	
		return lines;
		
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		return super.afterSave(newRecord, success);
	}
	
}
