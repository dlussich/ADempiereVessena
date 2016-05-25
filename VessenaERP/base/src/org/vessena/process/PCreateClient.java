/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 03/11/2014
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctProcessor;
import org.compiere.model.MCalendar;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MGLCategory;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MRequestProcessor;
import org.compiere.model.MRole;
import org.compiere.model.MRoleOrgAccess;
import org.compiere.model.MSequence;
import org.compiere.model.X_C_AcctProcessor;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.process - PCreateClient
 * OpenUp Ltda. Issue #3154 
 * Description: Proceso para creacion de nueva compañia tomando otra como modelo.
 * @author Gabriel Vila - 03/11/2014
 * @see
 */
public class PCreateClient extends SvrProcess {

	private int adClientID = 0;
	private String clientName = null;
	private String orgValue = null;
	private String orgName = null;
	private int cCurrencyID = 0;
	private int cCountryID = 0;
	private int cRegionID = 0;
	private String cityName = null;
	private String postal = null;
	private String address1 = null;
	private String phone = null;
	private String eMail = null;
	private int cCityID = 0;
	private String languageName = null;

	private MClient newClient = null;
	private MOrg newOrg = null;
	private MRole adminRole = null;
	private MCalendar newCalendar = null;
	
	public static final int WINDOW_THIS_PROCESS = 9999;

	
	/**
	 * Constructor.
	 */
	public PCreateClient() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("ClientName"))
				this.clientName = ((String) para[i].getParameter()).trim();
			else if (name.equals("OrgValue"))
				this.orgValue = ((String) para[i].getParameter()).trim();
			else if (name.equals("OrgName"))
				this.orgName = ((String) para[i].getParameter()).trim();
			else if (name.equals("C_Currency_ID"))
				this.cCurrencyID = para[i].getParameterAsInt();
			else if (name.equals("AD_Client_ID"))
				this.adClientID = para[i].getParameterAsInt();
			else if (name.equals("C_Country_ID"))
				this.cCountryID = para[i].getParameterAsInt();
			else if (name.equals("C_Region_ID"))
				this.cRegionID = para[i].getParameterAsInt();
			else if (name.equals("CityName"))
				this.cityName = ((String) para[i].getParameter()).trim();
			else if (name.equals("C_City_ID"))
				this.cCityID = para[i].getParameterAsInt();
			else if (name.equals("Postal"))
				this.postal = (String) para[i].getParameter();
			else if (name.equals("Address1"))
				this.address1 = (String) para[i].getParameter();
			else if (name.equals("Phone"))
				this.phone = (String) para[i].getParameter();
			else if (name.equals("EMail"))
				this.eMail = (String) para[i].getParameter();
		}

		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		try {

			// Seteo lenguaje
			this.languageName = Env.getAD_Language(getCtx());
			
			// Nueva entrada de Compañia
			this.setNewClient();
			
			// Creo secuencias para nueva compañia
			this.setNewSequences();
			
			// Seteo informacion de nueva compañia
			if (!this.newClient.setupClientInfo(this.languageName)){
				throw new AdempiereException("No se pudo setear información de nueva Compañia");
			}
			
			// Creacion y seteo de informacion de nueva organizazion
			this.setNewOrg();
			
			// Creacion y seteo de nuevo Rol Admin para nueva compañia
			this.setNewRoles();
			
			// Asocio usuarios administrator menos System, al nuevo Rol Admin
			this.setUserRoles();
			
			// Creacion y seteo de procesadores para esta nueva compañia
			this.setNewProcessors();
			
			// Creacion y seteo de calendario contable
			//this.setNewCalendar();
			
			// Creacion y seteo de categorias de GL
			this.setGLCategories();
			
			// Creacion de documentos
			this.setDocuments();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return "OK";
	}

	
	/***
	 * Crea y setea información de nueva compañia.
	 * OpenUp Ltda. Issue #3154 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 * @return
	 */
	private void setNewClient(){
		
		try {
			
			this.newClient = new MClient(getCtx(), 0, true, get_TrxName());
			this.newClient.setValue(this.clientName);
			this.newClient.setName(this.clientName);
			this.newClient.saveEx();
			
			Env.setContext(getCtx(), WINDOW_THIS_PROCESS, "AD_Client_ID", newClient.get_ID());
			Env.setContext(getCtx(), "#AD_Client_ID", newClient.get_ID());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/***
	 * Crea y setea informacion de nueva organizacion
	 * OpenUp Ltda. Issue #3154 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 */
	private void setNewOrg(){
		
		try {
			
			this.newOrg = new MOrg(this.newClient, this.orgValue, this.orgName);
			this.newOrg.saveEx();
			
			Env.setContext(getCtx(), WINDOW_THIS_PROCESS, "AD_Org_ID", this.newOrg.get_ID());
			Env.setContext(getCtx(), "#AD_Org_ID", this.newOrg.get_ID());

			// Seteo informacion de la organizacion
			MOrgInfo orgInfo = MOrgInfo.get(getCtx(), this.newOrg.get_ID(), get_TrxName());
			orgInfo.setPhone(this.phone);
			orgInfo.setEMail(this.eMail);
			orgInfo.saveEx();
			
			
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	
	/***
	 * Crea y setea nuevo rol Admin para nueva Compañia
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 */
	private void setNewRoles(){

		try {

			String name = this.newClient.getName().trim() + " Admin";

			this.adminRole = new MRole(getCtx(), 0, get_TrxName());
			this.adminRole.setAD_Client_ID(this.newClient.get_ID());
			this.adminRole.setAD_Org_ID(0);
			this.adminRole.setName(name);
			this.adminRole.setUserLevel(MRole.USERLEVEL_ClientPlusOrganization);
			this.adminRole.setPreferenceType(MRole.PREFERENCETYPE_Client);
			this.adminRole.setIsShowAcct(true);
			this.adminRole.saveEx();
			
			//	OrgAccess x, 0
			MRoleOrgAccess adminClientAccess = new MRoleOrgAccess (this.adminRole, 0);
			adminClientAccess.saveEx();

			//  OrgAccess x,y
			MRoleOrgAccess adminOrgAccess = new MRoleOrgAccess (this.adminRole, this.newOrg.get_ID());
			adminOrgAccess.saveEx();
			
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	
	/***
	 * Para todo usuario administrator menos el System, le doy acceso al nuevo Rol Admin.
	 * OpenUp Ltda. Issue #3154 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 */
	private void setUserRoles(){
		
		String sql = "", action = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			// Obtengo usuarios administrators menos el System
			sql = " select distinct ad_user_id " +
				  " from ad_user " +
				  " where ad_client_id = 0 " +
				  " and ad_user_id not in(1003002,100) " +
				  " and name != 'System' " +
				  " order by ad_user_id desc ";
			
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				
				// Asigno rol de Admin para cada usuario
				action = " insert into ad_user_roles (ad_user_id, ad_role_id, ad_client_id, ad_org_id, isactive, created, createdby, " +
						 " updated, updatedby) " +
						 " values (" + rs.getInt("ad_user_id")  + "," + this.adminRole.get_ID() + "," + this.newClient.get_ID() + "," +
						 this.newOrg.get_ID() + ",'Y', now(), " + rs.getInt("ad_user_id") + ", now(), " + rs.getInt("ad_user_id") + ") ";
				DB.executeUpdateEx(action, get_TrxName());
				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}

	
	/***
	 * Crea y setea procesadores para esta nueva compañia.
	 * OpenUp Ltda. Issue #3154 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 */
	private void setNewProcessors(){
		
		try {
			
			MAcctProcessor ap = new MAcctProcessor(this.newClient, 100);
			ap.setFrequencyType (X_C_AcctProcessor.FREQUENCYTYPE_Day);
			ap.setFrequency(0);
			ap.setKeepLogDays(2);
			ap.saveEx();
			
			MRequestProcessor rp = new MRequestProcessor (this.newClient, 100);
			rp.saveEx();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
	
	/***
	 * Crea y setea numeradores para nueva compañia 
	 * OpenUp Ltda. Issue #3154 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 */
	private void setNewSequences(){

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			// Obtengo secuenciales actualmente definidos para la compañia tomada como modelo
			sql = " select name, description " +
				  " from ad_sequence " +
				  " where ad_client_id =? " +
				  " and isactive='Y' ";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.adClientID);

			rs = pstmt.executeQuery();
			
			while (rs.next()){
				
				// Copio secuencia para nueva empresa
				MSequence seq = new MSequence(getCtx(), 0, get_TrxName());
				seq.setAD_Client_ID(this.newClient.get_ID());
				seq.setAD_Org_ID(0);
				seq.setName(rs.getString("name"));
				seq.setDescription(rs.getString("description"));
				seq.saveEx();
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}
	
	
	/***
	 * Crea y setea información de nuevo calendario para nueva compañia.
	 * OpenUp Ltda. Issue #3154 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 */
	private void setNewCalendar(){
		
		try {
			
			this.newCalendar = new MCalendar(this.newClient);
			this.newCalendar.saveEx();

			if (this.newCalendar.createYear(this.newClient.getLocale()) == null){
				throw new AdempiereException("No se pudo crear Año del Periodo Contable");
			}
			
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}


	/***
	 * Crea y setea categorias de GL.
	 * OpenUp Ltda. Issue #3154 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 */
	private void setGLCategories(){
		
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			// Obtengo categorias gl de compañia modelo
			sql = " select *  " +
				  " from gl_category " +
				  " where ad_client_id =? ";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.adClientID);

			rs = pstmt.executeQuery();
			
			while (rs.next()){
				
				boolean isDefault = (rs.getString("IsDefault").equalsIgnoreCase("Y")) ? true : false;
				
				// Genero copa de categoria de GL para nueva compañia
				MGLCategory cat = new MGLCategory (getCtx(), 0, get_TrxName());
				cat.setName(rs.getString("name"));
				cat.setCategoryType(rs.getString("categorytype"));
				cat.setIsDefault(isDefault);
				cat.saveEx();
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}
	

	/***
	 * Crea y setea tipos de documento.
	 * OpenUp Ltda. Issue #3154 
	 * @author Gabriel Vila - 03/11/2014
	 * @see
	 */
	private void setDocuments(){
		
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			// Obtengo tipos de documento de compañia modelo
			sql = " select *  " +
				  " from c_doctype " +
				  " where ad_client_id =? ";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.adClientID);

			rs = pstmt.executeQuery();
			
			while (rs.next()){
				
				// Creo documento copia del original
				MDocType doc = new MDocType(getCtx(), 0, get_TrxName());
				doc.setAD_Client_ID(this.newClient.get_ID());
				doc.setAD_Org_ID(0);
				doc.setName(rs.getString("name"));
				doc.setPrintName(rs.getString("printname"));
				doc.setDescription(rs.getString("description"));
				doc.setDocBaseType(rs.getString("DocBaseType"));
				doc.setIsSOTrx((rs.getString("IsSOTrx").equalsIgnoreCase("Y")) ? true : false);
				doc.setDocSubTypeSO(rs.getString("DocSubTypeSO"));
				if (rs.getString("HasProforma") != null){
					doc.setHasProforma((rs.getString("HasProforma").equalsIgnoreCase("Y")) ? true : false);
				}
				doc.setIsDocNoControlled((rs.getString("IsDocNoControlled").equalsIgnoreCase("Y")) ? true : false);

				// Secuenciales
				if (rs.getInt("DocNoSequence_ID") > 0){
					String seqName = DB.getSQLValueStringEx(null, " select name from ad_sequence where ad_sequence_id =" + rs.getInt("DocNoSequence_ID"));
					int seqID = DB.getSQLValueEx(get_TrxName(), " select ad_sequence_id from ad_sequence where ad_client_id =" + this.newClient.get_ID() +
							" and name ='" + seqName + "'");
					if (seqID > 0){
						doc.setDocNoSequence_ID(seqID);
					}
					else{
						throw new AdempiereException("No fue posible setear secuencial para el documento :" + rs.getString("name"));
					}
				}

				if (rs.getInt("DefiniteSequence_ID") > 0){
					String seqName = DB.getSQLValueStringEx(null, " select name from ad_sequence where ad_sequence_id =" + rs.getInt("DefiniteSequence_ID"));
					int seqID = DB.getSQLValueEx(get_TrxName(), " select ad_sequence_id from ad_sequence where ad_client_id =" + this.newClient.get_ID() +
							" and name ='" + seqName + "'");
					if (seqID > 0){
						doc.setDefiniteSequence_ID(seqID);
					}
					else{
						throw new AdempiereException("No fue posible setear secuencial para el documento :" + rs.getString("name"));
					}
				}

				// Categoria GL
				if (rs.getInt("GL_Category_ID") > 0){
					String catName = DB.getSQLValueStringEx(null, " select name from gl_category where gl_category_id =" + rs.getInt("GL_Category_ID"));
					int catID = DB.getSQLValueEx(get_TrxName(), " select gl_category_id from gl_category where ad_client_id =" + this.newClient.get_ID() +
							" and name ='" + catName + "'");
					if (catID > 0){
						doc.setGL_Category_ID(catID);
					}
					else{
						throw new AdempiereException("No fue posible setear categoria GL para el documento :" + rs.getString("name"));
					}
				}
				else{
					int catID = DB.getSQLValueEx(get_TrxName(), " select gl_category_id from gl_category where Name ='None' and ad_client_id =" + this.newClient.get_ID()); 
					if (catID > 0){
						doc.setGL_Category_ID(catID);
					}
					else{
						throw new AdempiereException("No fue posible setear categoria GL para el documento :" + rs.getString("name"));
					}
				}
				
				doc.setHasCharges((rs.getString("HasCharges").equalsIgnoreCase("Y")) ? true : false);
				
				if (rs.getString("DocumentNote") != null){
					doc.setDocumentNote(rs.getString("DocumentNote"));					
				}

				doc.setIsDefault((rs.getString("IsDefault").equalsIgnoreCase("Y")) ? true : false);
				doc.setDocumentCopies(rs.getInt("DocumentCopies"));
				
				if (rs.getInt("AD_PrintFormat_ID") > 0){
					doc.setAD_PrintFormat_ID(rs.getInt("AD_PrintFormat_ID"));
				}

				doc.setIsDefaultCounterDoc((rs.getString("IsDefaultCounterDoc").equals("Y")) ? true : false);
				doc.setIsShipConfirm((rs.getString("IsShipConfirm").equalsIgnoreCase("Y")) ? true : false);
				doc.setIsPickQAConfirm((rs.getString("IsPickQAConfirm").equalsIgnoreCase("Y")) ? true : false);
				doc.setIsInTransit((rs.getString("IsInTransit").equalsIgnoreCase("Y")) ? true : false);
				doc.setIsSplitWhenDifference((rs.getString("IsSplitWhenDifference").equalsIgnoreCase("Y")) ? true : false);
				doc.setIsCreateCounter((rs.getString("IsCreateCounter").equalsIgnoreCase("Y")) ? true : false);
				doc.setIsIndexed((rs.getString("IsIndexed").equalsIgnoreCase("Y")) ? true : false);
				doc.setIsOverwriteSeqOnComplete((rs.getString("IsOverwriteSeqOnComplete").equalsIgnoreCase("Y")) ? true : false);
				doc.setIsOverwriteDateOnComplete((rs.getString("IsOverwriteDateOnComplete").equalsIgnoreCase("Y")) ? true : false);
				doc.setIsPrepareSplitDocument((rs.getString("IsPrepareSplitDocument").equalsIgnoreCase("Y")) ? true : false);
								
				doc.setAllocationBehaviour(rs.getString("AllocationBehaviour"));
				doc.setValue(rs.getString("Value"));
				
				if (rs.getInt("ad_window_id") > 0){
					doc.setAD_Window_ID(rs.getInt("ad_window_id"));
				}
				
				if (rs.getInt("UY_CashFlow_Concept_ID") > 0){
					doc.set_ValueOfColumn("UY_CashFlow_Concept_ID", rs.getInt("UY_CashFlow_Concept_ID"));
				}
				
				doc.saveEx();
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}

}
