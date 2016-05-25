/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 14/03/2013
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactLine;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MExtProvision
 * OpenUp Ltda. Issue #296 
 * Description: Modelo y accion de documento para Extornos de Provisiones de Gasto Pendientes.
 * @author Gabriel Vila - 14/03/2013
 * @see
 */
public class MExtProvision extends X_UY_ExtProvision implements DocAction {

	private static final long serialVersionUID = -3465461666383313084L;
	private String processMsg = null;
	private boolean justPrepared = false;

	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ExtProvision_ID
	 * @param trxName
	 */
	public MExtProvision(Properties ctx, int UY_ExtProvision_ID, String trxName) {
		super(ctx, UY_ExtProvision_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MExtProvision(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {
		
		this.getProvisionAmtOpen();
		
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
		
		this.setDateAcct(this.getDateTrx());

		// Verifico periodo contable para documento de afectacion
		MPeriod.testPeriodOpen(getCtx(), this.getDateAcct(), this.getC_DocType_ID(), this.getAD_Org_ID());
		
		List<MExtProvisionLine> lines = this.getSelectedLines();
		if (lines.size() <= 0){
			this.processMsg = "No hay lineas seleccionadas.";
			return DocAction.STATUS_Invalid;
		}
		
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
	 * @author Hp - 14/03/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		// Test period
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		
		// Elimino asientos contables
		FactLine.deleteFact(I_UY_ExtProvision.Table_ID, this.get_ID(), get_TrxName());
		
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reActivateIt() {

		// Before reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (this.processMsg != null)
			return false;

		// Test period
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		
		// Elimino asientos contables
		FactLine.deleteFact(I_UY_Provision.Table_ID, this.get_ID(), get_TrxName());
		
		// After reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (this.processMsg != null)
			return false;

		// Seteo estado de documento
		this.setProcessed(false);
		this.setPosted(false);
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);
		
		return true;

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
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
	 * @author Hp - 14/03/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Carga informacion de provisiones pendientes segun filtros.
	 * OpenUp Ltda. Issue #296 
	 * @author Gabriel Vila - 14/03/2013
	 * @see
	 */
	@SuppressWarnings("unused")
	private void loadData(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String whereFiltros = "";
		boolean hayInfo = false;
		
		try{
			// Filtro periodo
			if (this.getC_Period_ID() > 0){
				MPeriod period = (MPeriod)this.getC_Period();
				whereFiltros = " AND fa.dateacct BETWEEN '" + period.getStartDate() + "' AND '" + period.getEndDate() + "' ";
			}
			else{
				// Si no se indica periodo me aseguro de no considerar fechas anteriores a periodo inicial 
				whereFiltros = " AND fa.dateacct >= '2014-01-01 00:00:00' ";
			}
			
			// Filtro departamento
			if (this.getC_Activity_ID_1() > 0) whereFiltros += " AND fa.c_activity_id_1 =" + this.getC_Activity_ID_1();

			// Filtro proveedores
			if (this.getFilterPartners().size() > 0) {
				whereFiltros += " AND fa.c_bpartner_id in (select c_bpartner_id from uy_provisionpartner where uy_provision_id=" + this.get_ID() + ") ";
			}
			
			// Filtro cuentas
			if (this.getFilterAccounts().size() > 0) {
				whereFiltros += " AND fa.account_id in (select c_elementvalue_id from uy_provisionaccount where uy_provision_id=" + this.get_ID() + ") ";
			}

			sql = " select fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_activity_id_1, fa.c_period_id, " +
				  " fa.c_currency_id, fa.record_id, sum(coalesce(fa.amtsourcedr,0) - coalesce(fa.amtsourcecr,0)) as amt " +
				  " from fact_acct fa " +
				  " inner join c_bpartner bp on fa.c_bpartner_id = bp.c_bpartner_id " +
				  " inner join m_product prod on fa.m_product_id = prod.m_product_id " +
				  " inner join c_elementvalue ev on fa.account_id =  ev.c_elementvalue_id " +
				  " where fa.ad_client_id =? " +
				  " and fa.ad_table_id = (select ad_table_id from ad_table where lower(tablename)='uy_provision') " +				  
				  " and bp.isvendor='Y' " +
				  " and bp.isactive='Y' " +
				  " and bp.isrecurrent='Y' " + whereFiltros +
				  " and ev.accounttype='E' " +				  
				  " and coalesce(prod.uy_linea_negocio_id,0) not in " +
				  " (select uy_linea_negocio_id from uy_linea_negocio where value='redondeo') " +
				  " group by fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_activity_id_1, fa.c_period_id, fa.c_currency_id, fa.record_id " + 
				  " order by fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_activity_id_1, fa.c_period_id, fa.c_currency_id, fa.record_id "; 
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.getAD_Client_ID());

			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				hayInfo = true;
				
				MExtProvisionLine line = new MExtProvisionLine(getCtx(), 0, get_TrxName());
				line.setUY_ExtProvision_ID(this.get_ID());
				line.setC_Period_ID(rs.getInt("c_period_id"));
				line.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				line.setC_ElementValue_ID(rs.getInt("account_id"));
				line.setM_Product_ID(rs.getInt("m_product_id"));
				line.setC_Activity_ID_1(rs.getInt("c_activity_id_1"));
				line.setC_Currency_ID(rs.getInt("c_currency_id"));
				line.setUY_Provision_ID(rs.getInt("record_id"));
				line.setamtallocated(rs.getBigDecimal("amt"));
				line.setIsSelected(true);
				
				// Obtengo linea de provision correspondiente e instancio modelo
				sql = " select uy_provisionline_id " +
					  " from uy_provisionline " +
					  " where uy_provision_id = " + line.getUY_Provision_ID() +
					  " and c_elementvalue_id = " + line.getC_ElementValue_ID() +
					  " and c_bpartner_id = " + line.getC_BPartner_ID() +
					  " and m_product_id = " + line.getM_Product_ID() +
					  " and c_activity_id_1 = " + line.getC_Activity_ID_1() +
					  " and c_currency_id = " + line.getC_Currency_ID();
				
				int provLineID = DB.getSQLValue(null, sql);
				if (provLineID > 0) {
					
					MProvisionLine provLine = new MProvisionLine(getCtx(), provLineID, null);
					
					BigDecimal amtTotal = provLine.getAmtSourceAverage();
					if (amtTotal == null) amtTotal = Env.ZERO;
					
					line.setamtdocument(amtTotal);
					line.setamtopen(line.getamtdocument().subtract(line.getamtallocated()));
					
					if (line.getamtopen().compareTo(Env.ZERO) != 0){
						line.saveEx();
					}
						
				}
				
			}

			// Si la select no trajo registros para procesar, aviso
			if (!hayInfo){
				throw new AdempiereException("No se obtuvieron registros a procesar segun valores de Filtros.");
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}

	
	/***
	 * Carga informacion de provisiones pendientes segun filtros.
	 * OpenUp Ltda. Issue #296 
	 * @author Gabriel Vila - 14/03/2013
	 * @see
	 */
	private void getProvisionAmtOpen(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String whereFiltros = "";
		boolean hayInfo = false;
		
		try{
			// Filtro periodo
			if (this.getC_Period_ID() > 0){
				whereFiltros = " AND prov.c_period_id =" + this.getC_Period_ID();
			}
			else{
				// Si no se indica periodo me aseguro de no considerar fechas anteriores a periodo inicial 
				whereFiltros = " AND prov.c_period_id >=" + MPeriod.getC_Period_ID(getCtx(), Timestamp.valueOf("2014-01-01 00:00:00"), 0);
			}
			
			// Filtro departamento
			if (this.getC_Activity_ID_1() > 0) whereFiltros += " AND line.c_activity_id_1 =" + this.getC_Activity_ID_1();

			// Filtro proveedores
			if (this.getFilterPartners().size() > 0) {
				whereFiltros += " AND line.c_bpartner_id in (select c_bpartner_id from uy_provisionpartner where uy_provision_id=" + this.get_ID() + ") ";
			}
			
			// Filtro cuentas
			if (this.getFilterAccounts().size() > 0) {
				whereFiltros += " AND line.c_elementvalue_id in (select c_elementvalue_id from uy_provisionaccount where uy_provision_id=" + this.get_ID() + ") ";
			}

			sql = " select line.c_elementvalue_id, line.c_bpartner_id, line.m_product_id, line.c_activity_id_1, prov.c_period_id, " +
				  " line.c_currency_id, prov.uy_provision_id, line.uy_provisionline_id, " +
				  " alloc.amt, alloc.amtallocated, alloc.amtopen " +
				  " from uy_provisionline line " +
				  " inner join uy_provision prov on line.uy_provision_id = prov.uy_provision_id " +
				  " inner join alloc_provisionlineamtopen alloc on line.uy_provisionline_id = alloc.uy_provisionline_id " +
				  " where prov.ad_client_id =? " + whereFiltros +
				  " and alloc.amtopen != 0 ";
 
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.getAD_Client_ID());

			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				hayInfo = true;

				MExtProvisionLine line = new MExtProvisionLine(getCtx(), 0, get_TrxName());
				
				line.setUY_ExtProvision_ID(this.get_ID());
				line.setUY_Provision_ID(rs.getInt("uy_provision_id"));
				line.setUY_ProvisionLine_ID(rs.getInt("uy_provisionline_id"));
				
				line.setC_Period_ID(rs.getInt("c_period_id"));
				line.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				line.setC_ElementValue_ID(rs.getInt("c_elementvalue_id"));
				line.setM_Product_ID(rs.getInt("m_product_id"));
				line.setC_Activity_ID_1(rs.getInt("c_activity_id_1"));
				line.setIsSelected(true);
				line.setC_Currency_ID(rs.getInt("c_currency_id"));
				
				line.setamtdocument(rs.getBigDecimal("amt"));
				line.setamtallocated(rs.getBigDecimal("amtallocated"));
				line.setamtopen(rs.getBigDecimal("amtopen"));
				
				line.saveEx();
			}

			// Si la select no trajo registros para procesar, aviso
			if (!hayInfo){
				throw new AdempiereException("No se obtuvieron registros a procesar segun valores de Filtros.");
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}

	
	/***
	 * Obtiene y retorna filtro de cuentas contables seleccionadas por el usuario.
	 * OpenUp Ltda. Issue #296 
	 * @author Gabriel Vila - 14/03/2013
	 * @see
	 * @return
	 */
	public List<MProvisionAccount> getFilterAccounts(){
		
		String whereClause = X_UY_ProvisionAccount.COLUMNNAME_UY_ExtProvision_ID + "=" + this.get_ID();
		
		List<MProvisionAccount> values = new Query(getCtx(), I_UY_ProvisionAccount.Table_Name, whereClause, get_TrxName())
		.list();

		return values;
	}
	
	/***
	 * Obtiene y retorna filtro de proveedores seleccionados por el usuario.
	 * OpenUp Ltda. Issue #296 
	 * @author Gabriel Vila - 14/03/2013
	 * @see
	 * @return
	 */
	public List<MProvisionPartner> getFilterPartners(){
		
		String whereClause = X_UY_ProvisionPartner.COLUMNNAME_UY_ExtProvision_ID + "=" + this.get_ID();
		
		List<MProvisionPartner> values = new Query(getCtx(), I_UY_ProvisionPartner.Table_Name, whereClause, get_TrxName())
		.list();

		return values;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		this.setDateAcct(this.getDateTrx());
		
		return true;
	}

	
	/***
	 * Obtiene y retorna array de lineas de este modelo.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel vila - 09/10/2012
	 * @see
	 * @return
	 */
	public List<MExtProvisionLine> getLines() {

		String whereClause = X_UY_ExtProvisionLine.COLUMNNAME_UY_ExtProvision_ID + "=" + this.get_ID();
		List<MExtProvisionLine> lines = new Query(getCtx(), I_UY_ExtProvisionLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}
	
	/***
	 * Obtiene y retorna array de lineas de este modelo.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel vila - 09/10/2012
	 * @see
	 * @return
	 */
	public List<MExtProvisionLine> getSelectedLines() {

		String whereClause = X_UY_ExtProvisionLine.COLUMNNAME_UY_ExtProvision_ID + "=" + this.get_ID() +
				" AND " + X_UY_ExtProvisionLine.COLUMNNAME_IsSelected + "='Y' ";
		List<MExtProvisionLine> lines = new Query(getCtx(), I_UY_ExtProvisionLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}
	
}
