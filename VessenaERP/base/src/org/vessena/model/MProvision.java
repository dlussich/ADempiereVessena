/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/10/2012
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
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
 * org.openup.model - MProvision
 * OpenUp Ltda. Issue #49 
 * Description: 
 * @author Gabriel Vila - 05/10/2012
 * @see
 */
public class MProvision extends X_UY_Provision implements DocAction {

	private static final long serialVersionUID = -2628538983975221107L;
	private String processMsg = null;
	private boolean justPrepared = false;

	private MPeriod period1 = null, period2 = null, period3 = null, periodFilter = null;
	
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Provision_ID
	 * @param trxName
	 */
	public MProvision(Properties ctx, int UY_Provision_ID, String trxName) {
		super(ctx, UY_Provision_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProvision(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {
		this.loadData();
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {
		
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		// Si alguna linea de esta provision ya fue utilizada
		List<MInvoiceProvision> invlines = this.getInvoicedLines();
		if (invlines.size() > 0){
			this.processMsg = "No esta permitido Anular esta Provision ya que tiene lineas utilizadas en Facturas.";
			return false;			
		}
		
		// Test period
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		
		// Elimino asientos contables
		FactLine.deleteFact(I_UY_Provision.Table_ID, this.get_ID(), get_TrxName());
		
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean reActivateIt() {

		/*
		// Si alguna linea de esta provision ya fue utilizada
		List<MInvoiceProvision> invlines = this.getInvoicedLines();
		if (invlines.size() > 0){
			this.processMsg = "No esta permitido Reactivar esta Provision ya que tiene lineas utilizadas en Facturas.";
			return false;			
		}
		*/
		
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

	
	/***
	 * Obtiene y retorna lista de lineas de esta provisio que estan siendo utilizadas
	 * para la provision de una factura.
	 * OpenUp Ltda. Issue #128 
	 * @author Gabriel Vila - 22/11/2012
	 * @see
	 * @return
	 */
	private List<MInvoiceProvision> getInvoicedLines() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MInvoiceProvision> lines = new ArrayList<MInvoiceProvision>();
		
		try{
			
			sql = " select line.uy_invoice_provision_id " +
				  " from uy_invoice_provision line " +
				  " inner join c_invoice inv on line.c_invoice_id = inv.c_invoice_id " +
				  " where inv.docstatus !='VO' " +
				  " and line.uy_provisionline_id in " +
				  " (select uy_provisionline_id " +
				  "  from uy_provisionline " +
				  "  where uy_provision_id =? )";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.get_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MInvoiceProvision value = new MInvoiceProvision(Env.getCtx(), rs.getInt(1), get_TrxName());
				lines.add(value);
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return lines;		

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
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
	 * @author Hp - 05/10/2012
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/***
	 * Carga periodos a considerar para el proceso. Son 3 periodos anteriores y el periodo actual que es
	 * el que selecciona el usuario.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 05/10/2012
	 * @see
	 */
	private void setPeriods(){
		
		try{
			
			this.periodFilter = new MPeriod(getCtx(), this.getC_Period_ID(), null);
			this.period1 = new MPeriod(getCtx(), this.getC_Period_ID() - 1, null);
			this.period2 = new MPeriod(getCtx(), this.getC_Period_ID() - 2, null);
			this.period3 = new MPeriod(getCtx(), this.getC_Period_ID() - 3, null);
		}
		catch(Exception ex){
			throw new AdempiereException(ex);
		}
		
	}
	
	/***
	 * Carga informacion de perdidas en los periodos requeridos.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 05/10/2012
	 * @see
	 */
	private void loadData(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String whereFiltros = "";
		boolean hayInfo = false;
		
		try{

			// Obtengo periodos a considerar para el proceso
			this.setPeriods();
			
			// Obtengo fecha desde a considerar que sera igual a la fecha de inicio del primer periodo
			// que tenga ordenado por fecha ascendente.
			Timestamp fechaDesde = null;
			if (period3.get_ID() > 0) {
				fechaDesde = period3.getStartDate();
			}
			else if (period2.get_ID() > 0) {
				fechaDesde = period2.getStartDate();
			}  
			else if (period1.get_ID() > 0) {
				fechaDesde = period1.getStartDate();
			}  
			else{
				throw new AdempiereException("No hay periodos anteriores a considerar para el proceso.");
			}

			whereFiltros = " AND fa.dateacct BETWEEN '" + fechaDesde + "' AND '" + this.periodFilter.getEndDate() + "' "; 
			
			if (this.getC_Activity_ID_1() > 0) whereFiltros += " AND fa.c_activity_id_1 =" + this.getC_Activity_ID_1();

			if (this.getFilterPartners().size() > 0) {
				//whereFiltros += " AND invhdr.c_bpartner_id in (select c_bpartner_id from uy_provisionpartner where uy_provision_id=" + this.get_ID() + ") ";
				whereFiltros += " AND fa.c_bpartner_id in (select c_bpartner_id from uy_provisionpartner where uy_provision_id=" + this.get_ID() + ") ";
			}
			
			if (this.getFilterAccounts().size() > 0) {
				whereFiltros += " AND fa.account_id in (select c_elementvalue_id from uy_provisionaccount where uy_provision_id=" + this.get_ID() + ") ";
			}

			sql = " select fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_activity_id_1, fa.c_period_id, " +
				  " fa.c_currency_id, sum(coalesce(fa.qty,0)) as qty, sum(fa.amtsourcedr-fa.amtsourcecr) as amt " +
				  " from fact_acct fa " +
				  " inner join c_bpartner bp on fa.c_bpartner_id = bp.c_bpartner_id " +
				  " inner join m_product prod on fa.m_product_id = prod.m_product_id " +
				  " inner join c_elementvalue ev on fa.account_id =  ev.c_elementvalue_id " +
				  " where fa.ad_client_id =? " +
				  " and bp.isvendor='Y' " +
				  " and bp.isactive='Y' " +
				  " and bp.isrecurrent='Y' " + whereFiltros +
				  " and ev.accounttype='E' " +				  
				  " and coalesce(prod.uy_linea_negocio_id,0) not in " +
				  " (select uy_linea_negocio_id from uy_linea_negocio where value='redondeo') " +
				  " group by fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_activity_id_1, fa.c_period_id, fa.c_currency_id " + 
				  " order by fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_activity_id_1, fa.c_period_id, fa.c_currency_id"; 
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.getAD_Client_ID());
			//pstmt.setInt(2, this.getAD_Org_ID());

			rs = pstmt.executeQuery();

			// Corte por cuenta, proveedor, articulo, centro de costo, moneda
			int accountID = 0, cBPartnerID = 0, mProductID = 0, cCostoID = 0, cCurrencyID = 0;
			int accountIDAux = 0, cBPartnerIDAux = 0, mProductIDAux = 0, cCostoIDAux = 0, cCurrencyIDAux = 0;
			
			// Acumuladores
			BigDecimal amt1 = Env.ZERO, amt2 = Env.ZERO, amt3 = Env.ZERO, amtActual = Env.ZERO;
			BigDecimal qty1 = Env.ZERO, qty2 = Env.ZERO, qty3 = Env.ZERO, qtyActual = Env.ZERO;
			
			MProvisionLine provline = null;
			int periodCount = 0;
			
			while (rs.next()) {
			
				hayInfo = true;
				
				accountIDAux = rs.getInt("account_id");
				cBPartnerIDAux = rs.getInt("c_bpartner_id");
				mProductIDAux = rs.getInt("m_product_id");
				cCostoIDAux = rs.getInt("c_activity_id_1");
				cCurrencyIDAux = rs.getInt("c_currency_id");
			
				// Si tengo corte
				if ((accountIDAux != accountID) || (cBPartnerIDAux != cBPartnerID) ||
					(mProductIDAux != mProductID) || (cCostoIDAux != cCostoID) || (cCurrencyIDAux != cCurrencyID)){

					if (provline !=null){
						provline.setQtySource(qtyActual);
						provline.setQtySource1(qty1);
						provline.setQtySource2(qty2);
						provline.setQtySource3(qty3);

						periodCount = 0;
						if (qty1.compareTo(Env.ZERO) != 0) periodCount++;
						if (qty2.compareTo(Env.ZERO) != 0) periodCount++;
						if (qty3.compareTo(Env.ZERO) != 0) periodCount++;
						if (periodCount == 0) periodCount = 1;
						
						provline.setQtySourceAverage((qty1.add(qty2).add(qty3)).divide(new BigDecimal(periodCount), 2, RoundingMode.HALF_UP));
						provline.setQtySourceAverage(provline.getQtySourceAverage().subtract(qtyActual));
						provline.setAmtSource(amtActual);
						provline.setAmtSource1(amt1);
						provline.setAmtSource2(amt2);
						provline.setAmtSource3(amt3);
						
						periodCount = 0;
						if (amt1.compareTo(Env.ZERO) != 0) periodCount++;
						if (amt2.compareTo(Env.ZERO) != 0) periodCount++;
						if (amt3.compareTo(Env.ZERO) != 0) periodCount++;
						if (periodCount == 0) periodCount = 1;
						
						provline.setAmtSourceAverage((amt1.add(amt2).add(amt3)).divide(new BigDecimal(periodCount), 2, RoundingMode.HALF_UP));
						provline.setAmtSourceAverage(provline.getAmtSourceAverage().subtract(amtActual));
						provline.saveEx();
					}
					
					accountID = accountIDAux; cBPartnerID = cBPartnerIDAux; mProductID = mProductIDAux;
					cCostoID = cCostoIDAux; cCurrencyID = cCurrencyIDAux;
					
					provline = new MProvisionLine(getCtx(), 0, get_TrxName());
					provline.setUY_Provision_ID(this.get_ID());
					provline.setC_ElementValue_ID(accountID);
					provline.setC_BPartner_ID(cBPartnerID);
					provline.setM_Product_ID(mProductID);
					provline.setC_Activity_ID_1(cCostoID);
					provline.setC_Currency_ID(cCurrencyID);
					
					amt1 = Env.ZERO; amt2 = Env.ZERO; amt3 = Env.ZERO; amtActual = Env.ZERO;
					qty1 = Env.ZERO; qty2 = Env.ZERO; qty3 = Env.ZERO; qtyActual = Env.ZERO;
				}

				/*
				// Si tengo cuenta contable
				if (rs.getInt("account_id") > 0){
					// Si esta linea ya fue provisionada tomo importe a la perdida que se hizo en el asiento contable
					BigDecimal factacctLineAmt = this.getFactAcctLineAmt(rs.getInt("c_invoice_id"), rs.getInt("account_id"),
							rs.getInt("m_product_id"), rs.getInt("c_activity_id_1"));
					if (factacctLineAmt.compareTo(Env.ZERO) != 0){
						if (factacctLineAmt.compareTo(lineAmt) != 0) 
							lineAmt = factacctLineAmt;	
					}
				}
				*/

				/*
				// Acumulo cantidad e importe segun periodo en el cual cae esta factura
				// Considero tipo de documento para diferenciar facturas de notas de credito
				BigDecimal qtyAux = Env.ZERO, amtAux = Env.ZERO;
				if (rs.getString("docbasetype").equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){
					qtyAux = rs.getBigDecimal("qtyinvoiced");
					amtAux = lineAmt;
				}
				else if (rs.getString("docbasetype").equalsIgnoreCase(Doc.DOCTYPE_APCredit)){
					qtyAux = rs.getBigDecimal("qtyinvoiced").negate();
					amtAux = lineAmt.negate();
				}
				*/
				
				int periodID = rs.getInt("c_period_id");
				if (period1.get_ID() == periodID){
					qty1 = qty1.add(rs.getBigDecimal("qty"));
					amt1 = amt1.add(rs.getBigDecimal("amt"));
				}
				else if (period2.get_ID() == periodID){
					qty2 = qty2.add(rs.getBigDecimal("qty"));
					amt2 = amt2.add(rs.getBigDecimal("amt"));
				}
				else if (period3.get_ID() == periodID){
					qty3 = qty3.add(rs.getBigDecimal("qty"));
					amt3 = amt3.add(rs.getBigDecimal("amt"));
				}
				else if (periodFilter.get_ID() == periodID){
					qtyActual = qtyActual.add(rs.getBigDecimal("qty"));
					amtActual = amtActual.add(rs.getBigDecimal("amt"));
				}
			}

			// Si la select no trajo registros para procesar, aviso
			if (!hayInfo){
				throw new AdempiereException("No se obtuvieron registros a procesar segun valores de Filtros.");
			}
			
			// Ultimo registro
			if (provline !=null){
				provline.setQtySource(qtyActual);
				provline.setQtySource1(qty1);
				provline.setQtySource2(qty2);
				provline.setQtySource3(qty3);

				periodCount = 0;
				if (qty1.compareTo(Env.ZERO) != 0) periodCount++;
				if (qty2.compareTo(Env.ZERO) != 0) periodCount++;
				if (qty3.compareTo(Env.ZERO) != 0) periodCount++;
				if (periodCount == 0) periodCount = 1;

				provline.setQtySourceAverage((qty1.add(qty2).add(qty3)).divide(new BigDecimal(periodCount), 2, RoundingMode.HALF_UP));
				provline.setQtySourceAverage(provline.getQtySourceAverage().subtract(qtyActual));
				
				provline.setAmtSource(amtActual);
				provline.setAmtSource1(amt1);
				provline.setAmtSource2(amt2);
				provline.setAmtSource3(amt3);

				periodCount = 0;
				if (amt1.compareTo(Env.ZERO) != 0) periodCount++;
				if (amt2.compareTo(Env.ZERO) != 0) periodCount++;
				if (amt3.compareTo(Env.ZERO) != 0) periodCount++;
				if (periodCount == 0) periodCount = 1;
				
				provline.setAmtSourceAverage((amt1.add(amt2).add(amt3)).divide(new BigDecimal(periodCount), 2, RoundingMode.HALF_UP));
				provline.setAmtSourceAverage(provline.getAmtSourceAverage().subtract(amtActual));
				provline.saveEx();
			}
			
			// Agrego proveedores recurrentes que no tuvieron ventas
			this.addVendorsWithoutPO();
			
			// Actualizo centro de costo 1 a null para los que quedaron en cero
			DB.executeUpdateEx("update uy_provisionline set c_activity_id_1 = null " +
							   " where uy_provision_id =" + this.get_ID() + 
							   " and c_activity_id_1 = 0", get_TrxName());
			
		} catch (Exception e) {

			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	/***
	 * Obtiene y carga proveedores recurrentes sin ventas.
	 * OpenUp Ltda. Issue #29 
	 * @author Gabriel Vila - 20/11/2012
	 * @see
	 */
	private void addVendorsWithoutPO() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
		
			sql = " select distinct c_bpartner_id " +
				  " from c_bpartner " +
				  " where ad_client_id =? " +
				  " and isvendor='Y' " +
				  " and isactive='Y' " +
				  " and isrecurrent='Y' " +
				  " and c_bpartner_id not in " +
				  " (select c_bpartner_id from uy_provisionline where uy_provision_id =?)"; 

			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, this.getAD_Client_ID());
			pstmt.setInt(2, this.get_ID());
			rs = pstmt.executeQuery();
		
			while (rs.next()){
				MProvisionLine provline = new MProvisionLine(getCtx(), 0, get_TrxName());
				provline.setUY_Provision_ID(this.get_ID());
				provline.setC_BPartner_ID(rs.getInt(1));
				provline.saveEx();
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

	/***
	 * Obtiene y retorna monto a perdida de una determinada cuenta contable de una factura
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 20/11/2012
	 * @see
	 * @param cInvoiceID
	 * @param accountID
	 * @return
	 */
	@SuppressWarnings("unused")
	private BigDecimal getFactAcctLineAmt(int cInvoiceID, int accountID, int mProductID, int cActivityID1) {

		BigDecimal value = Env.ZERO;
		
		try{
		
			String sql = "select coalesce(amtsourcedr,0) as amt " +
					     " from fact_acct " +
					     " where ad_table_id = 318 " +
					     " and record_id =" + cInvoiceID +
					     " and account_id =" + accountID +
					     " and m_product_id =" + mProductID +
					     " and c_activity_id_1 =" + cActivityID1;
					     
					     
			value = DB.getSQLValueBDEx(null, sql);
			
			if (value == null) 
				value = Env.ZERO;
			
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
		
		return value;
	}

	/***
	 * Obtiene y retorna filtro de cuentas contables seleccionadas por el usuario.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 05/10/2012
	 * @see
	 * @return
	 */
	public List<MProvisionAccount> getFilterAccounts(){
		
		String whereClause = X_UY_ProvisionAccount.COLUMNNAME_UY_Provision_ID + "=" + this.get_ID();
		
		List<MProvisionAccount> values = new Query(getCtx(), I_UY_ProvisionAccount.Table_Name, whereClause, get_TrxName())
		.list();

		return values;
	}
	
	/***
	 * Obtiene y retorna filtro de proveedores seleccionados por el usuario.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 05/10/2012
	 * @see
	 * @return
	 */
	public List<MProvisionPartner> getFilterPartners(){
		
		String whereClause = X_UY_ProvisionPartner.COLUMNNAME_UY_Provision_ID + "=" + this.get_ID();
		
		List<MProvisionPartner> values = new Query(getCtx(), I_UY_ProvisionPartner.Table_Name, whereClause, get_TrxName())
		.list();

		return values;
	}

	/***
	 * Obtiene y retorna array de lineas de esta provision
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel vila - 09/10/2012
	 * @see
	 * @return
	 */
	public List<MProvisionLine> getLines() {

		String whereClause = X_UY_ProvisionLine.COLUMNNAME_UY_Provision_ID + "=" + this.get_ID();
		List<MProvisionLine> lines = new Query(getCtx(), I_UY_ProvisionLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}

	/***
	 * Obtiene y retorna lista de lineas de provisiones pendientes para un determinado
	 * proveedor - articulo.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 10/10/2012
	 * @see
	 * @param ctx
	 * @param adClientID
	 * @param adOrgID
	 * @param cBpartnerID
	 * @param mProductID
	 * @param trxName
	 * @return
	 */
	public static List<MProvisionLine> getOpenAmtLines(Properties ctx, int adClientID, int adOrgID, 
									int cBpartnerID, int mProductID, String trxName) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<MProvisionLine> lines = new ArrayList<MProvisionLine>();
		
		try{
			sql = "SELECT line.uy_provisionline_id " + 
 		  	" FROM uy_provisionline line " +
 		  	" INNER JOIN alloc_provisionlineamtopen alloc ON line.uy_provisionline_id = alloc.uy_provisionline_id " +
		  	" WHERE line.ad_client_id =?" +
		  	" AND line.c_bpartner_id =? " +
		  	" AND line.m_product_id =? " +
		  	" AND alloc.amtopen != 0";
		  	
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, adClientID);
			pstmt.setInt(2, cBpartnerID);
			pstmt.setInt(3, mProductID);
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MProvisionLine value = new MProvisionLine(ctx, rs.getInt(1), trxName);
				lines.add(value);
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

		return lines;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		this.setDateAcct(this.getDateTrx());
		
		return true;
	}
	
	
	
}
