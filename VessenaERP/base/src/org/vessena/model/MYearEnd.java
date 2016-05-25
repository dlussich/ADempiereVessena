/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 23/02/2012
 */
 
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

/**
 * org.openup.model - MYearEnd
 * OpenUp Ltda. Issue #979  
 * Description: Modelo para transaccion de Cierre de Ejercicio
 * @author Gabriel Vila - 23/02/2012
 * @see http://1.1.20.123:86/eventum/view.php?id=979
 */
public class MYearEnd extends X_UY_YearEnd implements DocAction {

	private static final long serialVersionUID = -8241705560804859157L;
	private String processMsg = null;
	private boolean justPrepared = false;


	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_YearEnd_ID
	 * @param trxName
	 */
	public MYearEnd(Properties ctx, int UY_YearEnd_ID, String trxName) {
		super(ctx, UY_YearEnd_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MYearEnd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue #979 
	 * @author Hp - 23/02/2012
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
	 * OpenUp Ltda. Issue #979 
	 * @author Hp - 23/02/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	//@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//Timestamp date = OpenUpUtils.sumaTiempo(this.getDateTrx(), Calendar.DAY_OF_MONTH, 1);
				
		this.setDateAcct(this.getDateTrx());
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 * OpenUp Ltda. Issue #979 
	 * @author Hp - 23/02/2012
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
	 * OpenUp Ltda. Issue #979 
	 * @author Hp - 23/02/2012
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
	 * @author Hp - 23/02/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 23/02/2012
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
	 * OpenUp Ltda. Issue #979 
	 * @author Hp - 23/02/2012
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

		// Verifico periodo contable abierto
		MPeriod.testPeriodOpen(getCtx(), this.getDateAcct(), this.getC_DocType_ID(), this.getAD_Org_ID());
		
		//Verifico periodos cerrados para permitir completar
		this.testPeriods();

		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;		

		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		//completo primer cabezal de asiento
		MYearEndResult r = new MYearEndResult(getCtx(),this.getHeader("uy_yearendresult"),get_TrxName()); 
		if (r.get_ID() > 0){
			try {
				if (!r.processIt(DocumentEngine.ACTION_Complete)) throw new AdempiereException(r.getProcessMsg());

			} catch (Exception e) {
				throw new AdempiereException (e.getMessage());
			}

		} else throw new AdempiereException ("No se pudo obtener cabezal de Resultados del Ejercicio");
		
		//OpenUp. Nicolas Sarlabos. 11/06/2013. #925. Completo documentos y genero los 3 asientos siguientes solo para documento de
		//Asiento de Cierre de Ejercicio, no lo hago si es doc de resultados parciales.
		MDocType doc = new MDocType (getCtx(),this.getC_DocType_ID(),get_TrxName());

		if(doc.getValue()!=null){

			if (doc.getValue().equalsIgnoreCase("yearend")) {
				
				//OpenUp. Nicolas Sarlabos. 04/11/2013. #1496
				if(this.isProcessIntegral()){

					//creo y completo segundo cabezal de asiento
					MYearEndIntegral i = this.createHdrIntegral(); 
					if (i.get_ID() > 0){
						try {
							if (!i.processIt(DocumentEngine.ACTION_Complete)) throw new AdempiereException(i.getProcessMsg());

						} catch (Exception e) {
							throw new AdempiereException (e.getMessage());
						}

					} else throw new AdempiereException ("No se pudo obtener cabezal de Cierre de Cuentas Integrales");		

					//creo y completo tercer cabezal de asiento
					MYearEndOpen o = this.createHdrOpen(i.get_ID());
					if (o.get_ID() > 0){
						try {
							if(o.getLines().size() > 0){
								if (!o.processIt(DocumentEngine.ACTION_Complete)) throw new AdempiereException(o.getProcessMsg());
							} else throw new AdempiereException ("No se obtuvieron lineas del asiento de Apertura de Cuentas Integrales");
						} catch (Exception e) {
							throw new AdempiereException (e.getMessage());				
						}

					} else throw new AdempiereException ("No se pudo obtener cabezal de Resultados Acumulados");

				}
				//Fin OpenUp. #1496.
				//creo y completo cuarto cabezal de asiento
				MYearEndAcumulate a = this.createHdrAcumulate(r.get_ID());
				if (a.get_ID() > 0){
					try {
						if(a.getLines().size() > 0){
							if (!a.processIt(DocumentEngine.ACTION_Complete)) throw new AdempiereException(a.getProcessMsg());
						} else throw new AdempiereException ("No se obtuvo linea de asiento de Resultado del Ejercicio");

					} catch (Exception e) {
						throw new AdempiereException (e.getMessage());
					}

				} else throw new AdempiereException ("No se pudo obtener cabezal de Resultados Acumulados");
			}

		}

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
	}
	
	/**
	 * Metodo que verifica que todos los periodos esten cerrados para poder realizar el cierre de ejercicio.
	 * OpenUp Ltda. Issue #1531
	 * @author Nicolas Sarlabos - 08/11/2013
	 * @see 
	 * @return
	 */	
	public void testPeriods(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MPeriod period = null;
		int periodToID = 0;
		int periodFromID = 0;
		String message = null;

		try {

			sql = "select c_period_id from c_period where '" + this.getDateAcct() + "' between startdate and enddate";
			periodToID = DB.getSQLValueEx(get_TrxName(), sql); //obtengo ID del periodo final, segun fecha del documento de cierre
			
			periodFromID = periodToID - 12; //obtengo ID del periodo inicial, restando 12 al ID del periodo final
			
			sql = "select c_period_id from c_periodcontrol where c_period_id >= " + periodFromID + " and c_period_id <= " + periodToID + 
					" and periodstatus='O' and docbasetype <> 'YRE'";
					
			pstmt = DB.prepareStatement (sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();
			
			if (rs.next()) message = "Imposible completar, existen documentos abiertos en los siguientes periodos: \n";
			
			rs.beforeFirst();
			
			while (rs.next()){

				period = new MPeriod(getCtx(),rs.getInt("c_period_id"),null);
				message += period.getName() + "\n";
				
			}
			
			if(message != null) throw new AdempiereException(message);						

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
	}

	/**
	 * Metodo que crea cabezal y lineas de apertura de cuentas integrales
	 * para este cierre de ejercicio y recibe como parametro el ID de cabezal de cierre de cuentas integrales
	 * OpenUp Ltda. Issue #855
	 * @author Nicolas Sarlabos - 30/05/2013
	 * @see 
	 * @return
	 */	
	private MYearEndOpen createHdrOpen(int integralID) {
		
		String sql = "";
		int tableID = 0;
		MYearEndOpen hdr = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			hdr = new MYearEndOpen(getCtx(),0,get_TrxName()); //instancio nuevo cabezal y seteo fecha
			hdr.setUY_YearEnd_ID(this.get_ID());
			hdr.setC_DocType_ID(this.getC_DocType_ID());
			hdr.setDocumentNo(this.getDocumentNo());
			hdr.setDateAcct(TimeUtil.addDays(this.getDateAcct(), 1));
			//hdr.setDateAcct(this.getDateAcct());
			hdr.setDocStatus(DocumentEngine.STATUS_Applied);
			hdr.setDocAction(DocAction.ACTION_Complete);
			hdr.setPosted(false);
			hdr.saveEx();			
			
			sql = "select ad_table_id from ad_table where lower(name) like 'uy_yearendintegral'";
			tableID = DB.getSQLValueEx(get_TrxName(), sql);
			
			//obtengo lineas del asiento creado para el cierre de cuentas integrales
			sql = "SELECT account_id,amtacctdr,amtacctcr" +
                  " FROM fact_acct" +
				  " WHERE ad_table_id=" + tableID + " and record_id=" + integralID;
			
			pstmt = DB.prepareStatement (sql,get_TrxName());
						
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				
				MYearEndOpenLine line = new MYearEndOpenLine(getCtx(),0,get_TrxName());
				line.setUY_YearEndOpen_ID(hdr.get_ID());
				line.setC_ElementValue_ID(rs.getInt("account_id"));
				line.setAmtSourceDr(rs.getBigDecimal("amtacctdr"));
				line.setAmtSourceCr(rs.getBigDecimal("amtacctcr"));
				line.saveEx();		
				
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
		return hdr;			
	}
	
	/**
	 * Metodo que crea cabezal y lineas de cierre de cuentas integrales para este cierre de ejercicio
	 * OpenUp Ltda. Issue #855
	 * @author Nicolas Sarlabos - 30/05/2013
	 * @see 
	 * @return
	 */	
	private MYearEndIntegral createHdrIntegral() {
		
		String sql = "";
		MYearEndIntegral hdr = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			hdr = new MYearEndIntegral(getCtx(),0,get_TrxName()); //instancio nuevo cabezal y seteo fecha
			hdr.setUY_YearEnd_ID(this.get_ID());
			hdr.setC_DocType_ID(this.getC_DocType_ID());
			hdr.setDocumentNo(this.getDocumentNo());
			hdr.setDateAcct(this.getDateAcct());
			hdr.setDocStatus(DocumentEngine.STATUS_Applied);
			hdr.setDocAction(DocAction.ACTION_Complete);
			hdr.setPosted(false);
			hdr.saveEx();			
			
			sql =" SELECT DISTINCT fa.account_id, sum(amtacctdr) as sumdr, sum(amtacctcr) as sumcr" +
					 " FROM fact_acct fa " +
					 " INNER JOIN c_elementvalue el ON fa.account_id = el.c_elementvalue_id " +
					 " WHERE fa.ad_client_id =" + this.getAD_Client_ID() +
					 " AND fa.dateacct<=? " +
					 " AND el.accounttype in('A','L','O','D','G') " +
					 " AND el.issummary='N' group by fa.account_id order by fa.account_id";
			
			pstmt = DB.prepareStatement (sql,get_TrxName());
			pstmt.setTimestamp(1,getDateAcct());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				
				MYearEndIntegralLine line = new MYearEndIntegralLine(getCtx(),0,get_TrxName());
				line.setUY_YearEndIntegral_ID(hdr.get_ID());
				line.setC_ElementValue_ID(rs.getInt("account_id"));
				line.setAmtSourceDr(rs.getBigDecimal("sumdr"));
				line.setAmtSourceCr(rs.getBigDecimal("sumcr"));
				line.saveEx();		
				
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
		return hdr;
	}
	
	/**
	 * Metodo que crea cabezal y lineas de resultados acumulados para este cierre de ejercicio
	 * y recibe como parametro el ID de cabezal de resultados del ejercicio
	 * OpenUp Ltda. Issue #855
	 * @author Nicolas Sarlabos - 31/05/2013
	 * @see 
	 * @return
	 */	
	private MYearEndAcumulate createHdrAcumulate(int resultID) {
		
		String sql = "";
		int tableID = 0;
		MYearEndAcumulate hdr = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			hdr = new MYearEndAcumulate(getCtx(),0,get_TrxName()); //instancio nuevo cabezal y seteo fecha
			hdr.setUY_YearEnd_ID(this.get_ID());
			hdr.setC_DocType_ID(this.getC_DocType_ID());
			hdr.setDocumentNo(this.getDocumentNo());
			hdr.setDateAcct(TimeUtil.addDays(this.getDateAcct(), 1));
			//hdr.setDateAcct(this.getDateAcct());
			hdr.setDocStatus(DocumentEngine.STATUS_Applied);
			hdr.setDocAction(DocAction.ACTION_Complete);
			hdr.setPosted(false);
			hdr.saveEx();			
			
			sql = "select ad_table_id from ad_table where lower(name) like 'uy_yearendresult'";
			tableID = DB.getSQLValueEx(get_TrxName(), sql);
			
			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			
			MAccount acctGainLoss = new MAccount(getCtx(), schema.getUY_Account_LossGain_ID(), get_TrxName());
			
			//obtengo linea del asiento para la cuenta de resultado del ejercicio creado anteriormente
			sql = "SELECT account_id,amtacctdr,amtacctcr" +
                  " FROM fact_acct" +
				  " WHERE ad_table_id=" + tableID + " and record_id=" + resultID + " and account_id=" + acctGainLoss.getAccount_ID();
			
			pstmt = DB.prepareStatement (sql,get_TrxName());
						
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				
				MYearEndAcumulateLine line = new MYearEndAcumulateLine(getCtx(),0,get_TrxName());
				line.setUY_YearEndAcumulate_ID(hdr.get_ID());
				line.setC_ElementValue_ID(rs.getInt("account_id"));
				line.setAmtSourceDr(rs.getBigDecimal("amtacctdr"));
				line.setAmtSourceCr(rs.getBigDecimal("amtacctcr"));
				line.saveEx();		
				
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
		return hdr;			
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue #979 
	 * @author Hp - 23/02/2012
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

		//anulo primer cabezal de asiento
		MYearEndResult r = new MYearEndResult(getCtx(),this.getHeader("uy_yearendresult"),get_TrxName()); 
		if (r.get_ID() > 0){
			try {
				if (!r.processIt(DocumentEngine.ACTION_Void)) throw new AdempiereException(r.getProcessMsg());

			} catch (Exception e) {
				throw new AdempiereException (e.getMessage());
			}

		} else throw new AdempiereException ("No se pudo obtener cabezal de Resultados del Ejercicio");

		//OpenUp. Nicolas Sarlabos. 11/06/2013. #925. Anulo documentos y los 3 asientos siguientes solo para documento de
		//Asiento de Cierre de Ejercicio, no lo hago si es doc de resultados parciales.
		MDocType doc = new MDocType (getCtx(),this.getC_DocType_ID(),get_TrxName());

		if(doc.getValue()!=null){

			if (doc.getValue().equalsIgnoreCase("yearend")) {

				//OpenUp. Nicolas Sarlabos. 04/11/2013. #1496
				if(this.isProcessIntegral()){

					//anulo segundo cabezal de asiento
					MYearEndIntegral i = new MYearEndIntegral(getCtx(),this.getHeader("uy_yearendintegral"),get_TrxName()); 
					if (i.get_ID() > 0){
						try {
							if (!i.processIt(DocumentEngine.ACTION_Void)) throw new AdempiereException(i.getProcessMsg());

						} catch (Exception e) {
							throw new AdempiereException (e.getMessage());
						}

					} else throw new AdempiereException ("No se pudo obtener cabezal de Cierre de Cuentas Integrales");

					//anulo tercer cabezal de asiento
					MYearEndOpen o = new MYearEndOpen(getCtx(),this.getHeader("uy_yearendopen"),get_TrxName()); 
					if (o.get_ID() > 0){
						try {
							if (!o.processIt(DocumentEngine.ACTION_Void)) throw new AdempiereException(o.getProcessMsg());

						} catch (Exception e) {
							throw new AdempiereException (e.getMessage());
						}

					} else throw new AdempiereException ("No se pudo obtener cabezal de Apertura de Cuentas Integrales");

				}
				//Fin OpenUp.
				
				//anulo cuarto cabezal de asiento
				MYearEndAcumulate a = new MYearEndAcumulate(getCtx(),this.getHeader("uy_yearendacumulate"),get_TrxName()); 
				if (a.get_ID() > 0){
					try {
						if (!a.processIt(DocumentEngine.ACTION_Void)) throw new AdempiereException(a.getProcessMsg());

					} catch (Exception e) {
						throw new AdempiereException (e.getMessage());
					}

				} else throw new AdempiereException ("No se pudo obtener cabezal de Resultados Acumulados");

			}
		}

		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);

		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 23/02/2012
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
	 * @author Hp - 23/02/2012
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
	 * @author Hp - 23/02/2012
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
	 * @author Hp - 23/02/2012
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
	 * @author Hp - 23/02/2012
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
	 * OpenUp Ltda. Issue #979 
	 * @author Hp - 23/02/2012
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
	 * @author Hp - 23/02/2012
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
	 * @author Hp - 23/02/2012
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
	 * @author Hp - 23/02/2012
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
	 * @author Hp - 23/02/2012
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
	 * @author Hp - 23/02/2012
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Valida si ya existe un cierre completo para la misma fecha.
	 * OpenUp Ltda. Issue #979 
	 * @author Gabriel Vila - 29/02/2012
	 * @see http://1.1.20.123:86/eventum/view.php?id=979
	 * @return
	 */
	private boolean validateYearEnd(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MDocType doc = (MDocType)this.getC_DocType();

		boolean value = true;
		
		try{
			sql =" select uy_yearend_id " +
				 " from uy_yearend " +
				 " where datetrx=? " +
				 " and c_doctype_id = " + doc.get_ID() +
				 " and docstatus='CO'";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setTimestamp(1, this.getDateAcct());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = false;
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
		
		return value;
	}

	@Override
	public boolean applyIt() {
		
		// Valido que no exista otro cierre con misma fecha y en estado completo
		if (!this.validateYearEnd()) throw new AdempiereException ("Ya existe un Asiento de Cierre de Ejercicio para esta fecha y en Estado Completo");
		
		//se cargan lineas del primer cabezal
		this.loadResult();

		this.setDocStatus(DocumentEngine.STATUS_Applied);
		this.setDocAction(DocAction.ACTION_Complete);
		return true;

	}
	
	/**
	 * Metodo que obtiene el ID del cabezal para este cierre de ejercicio segun nombre tabla recibido.
	 * OpenUp Ltda. Issue #855
	 * @author Nicolas Sarlabos - 28/05/2013
	 * @see 
	 * @return
	 */	
	private int getHeader(String tablename) {
		
		int id = 0;
		String sql = "";

		if(tablename != null && !tablename.equalsIgnoreCase("")){

			sql = "select " + tablename + "_id" +
					" from " + tablename +
					" where uy_yearend_id = " + this.get_ID();
			id = DB.getSQLValueEx(get_TrxName(), sql);
		}

		return id;
	}
	
	/**
	 * Metodo que carga las lineas del cabezal de resultados del ejercicio (4 y 5).
	 * OpenUp Ltda. Issue #855
	 * @author Nicolas Sarlabos - 28/05/2013
	 * @see 
	 * @return
	 */
	private void loadResult() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			MYearEndResult hdr = new MYearEndResult(getCtx(),0,get_TrxName()); //instancio nuevo cabezal y seteo fecha
			hdr.setUY_YearEnd_ID(this.get_ID());
			hdr.setC_DocType_ID(this.getC_DocType_ID());
			hdr.setDocumentNo(this.getDocumentNo());
			hdr.setDateAcct(this.getDateAcct());
			hdr.setDocStatus(DocumentEngine.STATUS_Applied);
			hdr.setDocAction(DocAction.ACTION_Complete);
			hdr.setPosted(false);
			hdr.saveEx();			
			
			sql =" SELECT DISTINCT fa.account_id, sum(amtacctdr) as sumdr, sum(amtacctcr) as sumcr" +
				 " FROM fact_acct fa " +
				 " INNER JOIN c_elementvalue el ON fa.account_id = el.c_elementvalue_id " +
				 " WHERE fa.ad_client_id =" + this.getAD_Client_ID() +
				 " AND fa.dateacct<=? " +
				 " AND el.accounttype in('E','R') " +
				 " AND el.issummary='N' group by fa.account_id order by fa.account_id";
			
			pstmt = DB.prepareStatement (sql,get_TrxName());
			pstmt.setTimestamp(1,getDateAcct());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				
				MYearEndResultLine line = new MYearEndResultLine(getCtx(),0,get_TrxName());
				line.setUY_YearEndResult_ID(hdr.get_ID());
				line.setC_ElementValue_ID(rs.getInt("account_id"));
				line.setAmtSourceDr(rs.getBigDecimal("sumdr"));
				line.setAmtSourceCr(rs.getBigDecimal("sumcr"));
				line.saveEx();		
				
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
		
	}
	
}
