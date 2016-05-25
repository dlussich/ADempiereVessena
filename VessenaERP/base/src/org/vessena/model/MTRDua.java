package org.openup.model;

import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.adempiere.exceptions.AdempiereException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.Schema;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.aduana.dua.dto.WSDUASDT;
import org.openup.aduana.dua.dto.WSDUASDTITEM;


// OpenUp. Raul Capecce. issue #3321 20/03/2015
// Gestion de DUAs obtenidos desde el webservice del sistema Lucia de Aduanas
public class MTRDua extends X_UY_TR_Dua implements DocAction {
	
	private String processMsg = null;
	private boolean justPrepared = false;


	/**
	 * 
	 */
	private static final long serialVersionUID = 3543963769723607877L;
	private String endpoint;
	private String operationMethod;
	private String actionMethod;
	private String namespace;
	private String user_name;
	private String user_pass;
	
	private String wsInAduana;
	private String wsInAnioPresedente;
	private String wsInNumeroDua;
	
	public MTRDua(Properties ctx, int UY_TR_Dua_ID, String trxName) {
		super(ctx, UY_TR_Dua_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	// OpenUp. Raul Capecce. issue #3321 20/03/2015
	// Obtencion del DUA del webservice
	@Override
	public boolean applyIt() {
		
		WSDUASDT wsDua = null;
		
		// Obtener DUA del webservice
		wsDua = getDuaFromWebService(this.getCodigoAduana(), this.getAnioDua().toString(), this.getNumeroDua());
		

		// Validar que no haya otro DUA en estado Aplicado, Completo o En Progreso
		if(existActiveDUA(wsDua.getCODIADUAN(), new BigDecimal(wsDua.getANOPRESE()), wsDua.getNUMECORRE())){
			throw new AdempiereException("Ya existe un DUA activo en el sistema");
		}
		
		// Guardo el dua
		this.saveDua(wsDua);
		
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		
		return true;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
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

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void saveDua(WSDUASDT wsDua){
		this.setCodigoAduana(wsDua.getCODIADUAN());
		this.setAnioDua(wsDua.getANOPRESE());
		this.setNumeroDua(wsDua.getNUMECORRE());
		this.setCodigoAgente(wsDua.getCODIAGENT());
		this.setNombreAgente(wsDua.getNombreAgente());
		this.setvalorMercaderia(new BigDecimal(wsDua.getTOTFACT()).setScale(2, RoundingMode.HALF_UP));
		this.setvalorMercaderiaRestante(new BigDecimal(wsDua.getTOTFACT()).setScale(2, RoundingMode.HALF_UP));
		
		this.saveEx(this.get_TrxName());
		
		// Recorrer los Items del Dua para CARGARLOS EN EL DOCUMENTO.
		for (WSDUASDTITEM item : wsDua.getITEMS().getITEM()){
			
			MTRDuaLine objDuaLine = new MTRDuaLine(getCtx(), 0, get_TrxName());
			
			objDuaLine.setUY_TR_Dua_ID(this.getUY_TR_Dua_ID());
			objDuaLine.setNumeroSerie(item.getNUMESERIE());
			objDuaLine.setdescripcion(item.getDESC01());
			objDuaLine.setdescripcion2(item.getDESC02());
			objDuaLine.setdescripcion3(item.getDESC03());
			objDuaLine.setdescripcion4(item.getDESC04());
			objDuaLine.setdescripcion5(item.getDESC05());
			objDuaLine.setTipoBulto(item.getCLASE());
			objDuaLine.setCantidadBultos(new BigDecimal(item.getCANTBULTO()).setScale(2, RoundingMode.HALF_UP));
			objDuaLine.setCantidadBultosRestantes(new BigDecimal(item.getCANTBULTO()).setScale(2, RoundingMode.HALF_UP));
			objDuaLine.setpesoBruto(new BigDecimal(item.getPESOBRUTO()).setScale(2, RoundingMode.HALF_UP));
			objDuaLine.setpesoBrutoRestante(new BigDecimal(item.getPESOBRUTO()).setScale(2, RoundingMode.HALF_UP));
			objDuaLine.setpesoNeto(new BigDecimal(item.getPESONETO()).setScale(2, RoundingMode.HALF_UP));
			objDuaLine.setpesoNetoRestante(new BigDecimal(item.getPESONETO()).setScale(2, RoundingMode.HALF_UP));
			
			objDuaLine.saveEx(this.get_TrxName());
			
		}
		
	}
	
	protected boolean existActiveDUA(String cod_aduana, BigDecimal anio_presedente, String numero_dua){
		
		int cantidad = Integer.valueOf( DB.getSQLValueStringEx(get_TrxName(), "SELECT COUNT(*) FROM UY_TR_Dua WHERE codigoAduana = '" + cod_aduana + "' AND anioDua = '" + anio_presedente + "' AND numeroDua = '" + numero_dua + "'"));
		
		return cantidad > 0;
	}
	
	// OpenUp. Raul Capecce. issue #3321 20/03/2015
	// Carga de datos de configuraci�n para conectarse al webservice de obtencion de DUAs del sistema Lucia de Aduanas
	protected void loadSettingsConnection(){
		// Credenciales del usuario para autenticarse contra el webservice
		this.user_name = DB.getSQLValueStringEx(get_TrxName(), "SELECT username FROM uy_tr_webservice WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) + " AND ad_org_id = " + Env.getAD_Org_ID(getCtx()));
		this.user_pass = DB.getSQLValueStringEx(get_TrxName(), "SELECT password FROM uy_tr_webservice WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) + " AND ad_org_id = " + Env.getAD_Org_ID(getCtx()));
		
		// Configuraci�n del webservice
		this.namespace = DB.getSQLValueStringEx(get_TrxName(), "SELECT duaNamespace FROM uy_tr_webservice WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) + " AND ad_org_id = " + Env.getAD_Org_ID(getCtx()));
		this.operationMethod = DB.getSQLValueStringEx(get_TrxName(), "SELECT duaOperationMethod FROM uy_tr_webservice WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) + " AND ad_org_id = " + Env.getAD_Org_ID(getCtx()));
		this.actionMethod = DB.getSQLValueStringEx(get_TrxName(), "SELECT duaActionMethod FROM uy_tr_webservice WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) + " AND ad_org_id = " + Env.getAD_Org_ID(getCtx()));
		this.endpoint = DB.getSQLValueStringEx(get_TrxName(), "SELECT duaEndpoint FROM uy_tr_webservice WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) + " AND ad_org_id = " + Env.getAD_Org_ID(getCtx()));
		
		// Parametros de entrada del webservice para obtener el DUA
		this.wsInAduana = DB.getSQLValueStringEx(get_TrxName(), "SELECT ParameterDuaInCodiAduan FROM uy_tr_webservice WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) + " AND ad_org_id = " + Env.getAD_Org_ID(getCtx()));
		this.wsInAnioPresedente = DB.getSQLValueStringEx(get_TrxName(), "SELECT ParameterDuaInAnioPrese FROM uy_tr_webservice WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) + " AND ad_org_id = " + Env.getAD_Org_ID(getCtx()));
		this.wsInNumeroDua = DB.getSQLValueStringEx(get_TrxName(), "SELECT ParameterDuaInNumeCorre FROM uy_tr_webservice WHERE ad_client_id = " + Env.getAD_Client_ID(getCtx()) + " AND ad_org_id = " + Env.getAD_Org_ID(getCtx()));
		
	}

	// OpenUp. Raul Capecce. issue #3321 20/03/2015
	// Obtencion de DUAs del webservice del sistema Lucia de Aduanas
	protected WSDUASDT getDuaFromWebService(String cod_aduana, String anio_presedente, String numero_dua){
		loadSettingsConnection();
		WSDUASDT retWsDua = null;
		
		try {
			
			Service service = new Service();
			Call call = (Call) service.createCall();
			// Establecemos la dirección en la que está activado el WebService
			call.setTargetEndpointAddress(new java.net.URL(this.endpoint));

			// Establecemos el nombre del método a invocar
			call.setOperationName(new QName(this.namespace, this.operationMethod));
			call.setSOAPActionURI(this.actionMethod);

			// Establecemos el usuario y contraseña para autenticar la conexión (en caso contrario, tirará error 401 No autorizado).			
			call.setUsername(this.user_name);
			call.setPassword(this.user_pass);

			// Establecemos los parámetros que necesita el método
			// Observe que se deben especidicar correctamente tanto el nómbre como el tipo de datos. Esta información se puede obtener viendo el WSDL del servicio Web
			//call.addParameter(new QName(this.namespace, this.wsInAduana), XMLType.XSD_STRING, ParameterMode.IN);
			//call.addParameter(new QName(this.namespace, this.wsInAnioPresedente), XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName(this.namespace, this.wsInAduana), XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName(this.namespace, this.wsInAnioPresedente), XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName(this.namespace, this.wsInNumeroDua), XMLType.XSD_STRING, ParameterMode.IN);
			
			// Especificamos el tipo de datos que devuelve el método.
			call.setReturnType(XMLType.XSD_SCHEMA);

			
			// Invocamos el método
			Schema result =  (Schema) call.invoke(new Object[] { cod_aduana, anio_presedente, numero_dua });

			String resultStr = "<DUA xmlns=\"www.aduanas.gub.uy/wsduasdt\">"; // Necesario el tag raiz que no aparece con la respuesta del webservice			
			for (MessageElement msjElement : result.get_any()){
				resultStr += msjElement.getAsString();
			}
			resultStr += "</DUA>";
			
			JAXBContext jaxbContext = JAXBContext.newInstance(WSDUASDT.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(resultStr);
			retWsDua = (WSDUASDT) unmarshaller.unmarshal(reader);
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return retWsDua;
		
	}

}
