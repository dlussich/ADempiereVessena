package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MBankAccount;
import org.compiere.model.MDocType;
import org.compiere.model.MJournal;
import org.compiere.model.MPeriod;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MConciliation extends X_UY_Conciliation implements DocAction {
	
	private String processMsg = null;
	private boolean justPrepared = false;
	private MDocType doc = MDocType.forValue(getCtx(), "conciliated", get_TrxName());
	private static final long serialVersionUID = -5361343252659918945L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Conciliation_ID
	 * @param trxName
	 */
	public MConciliation(Properties ctx, int UY_Conciliation_ID, String trxName) {
		super(ctx, UY_Conciliation_ID, trxName);
	
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//OpenUp. Nicolas Sarlabos. 16/08/2013. 
		if(newRecord || is_ValueChanged("c_period_id") || is_ValueChanged("c_bankaccount_id")){

			//OpenUp. Nicolas Sarlabos. 12/09/2013. #1308. Modifico sql para tomar en cuenta el estado del documento.
			String sql = "select uy_conciliation_id from uy_conciliation where c_period_id = " + this.getC_Period_ID() +
					" and docstatus in ('CO','AY','IP') and c_bankaccount_id = " + this.getC_BankAccount_ID();
			int ID = DB.getSQLValueEx(get_TrxName(), sql);
			//Fin OpenUp.

			if(ID > 0) {
				
				String estado = "";
				
				MConciliation con = new MConciliation(getCtx(), ID, get_TrxName());
				
				if(con.getDocStatus().equalsIgnoreCase("AY")){
					
					estado = "APLICADO";
					
				} else if (con.getDocStatus().equalsIgnoreCase("CO")){
					
					estado = "COMPLETO";
					
				} else if (con.getDocStatus().equalsIgnoreCase("IP")) estado = "EN PROCESO";					
				
				throw new AdempiereException ("Ya existe la conciliacion N° " + con.getDocumentNo() + " en estado '" + estado + "' para la cuenta y periodo seleccionados");		
			}
			
		}
		
		return true;
		//Fin OpenUp.
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MConciliation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
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

	@Override
	public boolean applyIt() {
		this.loadData(); //cargo datos
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	/**OpenUp. Nicolas Sarlabos. 27/11/2012
	 * Metodo que carga las tablas de movimientos del banco y del sistema de acuerdo a los filtros
	 * @return
	 */
	private void loadData() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		//String whereDate = "";
		//Timestamp startDate = null;
		Timestamp endDate = null;
		MConciliaBank cbank = null;
		MConciliaSystem csystem = null;
		int elementValueID = 0;
		int currencyID = 0;
		
		MBankAccount account = new MBankAccount (getCtx(),this.getC_BankAccount_ID(),get_TrxName()); //instancio cuenta bancaria
		
		MPeriod period = new MPeriod (getCtx(),this.getC_Period_ID(),get_TrxName()); //instancio periodo para obtener las fechas del mismo
		
		if (period.get_ID()>0) endDate = period.getEndDate();
					
		sql = "SELECT c_currency_id FROM c_bankaccount WHERE c_bankaccount_id = " + account.getC_BankAccount_ID(); //obtengo moneda de la cuenta bancaria
		currencyID = DB.getSQLValue(get_TrxName(), sql);
		
		//COMIENZA CARGA DE MOVIMIENTOS DEL BANCO////////////////////////////////////////////////////////////////////////////////////////////////////////		
		try {
			//OpenUp. Nicolas Sarlabos. 01/11/2013. #1485. Se modifica sql para considerar cargas en estado completo.
			sql = "SELECT be.uy_bankextract_id,be.datetrx,be.description,be.documentno,be.amtsourcecr,be.amtsourcedr,be.c_bank_id,be.c_bankaccount_id,be.c_currency_id,be.c_doctype_id,be.uy_loadextract_id,be.sucursal,be.datevalue,be.totalamt,be.iserror" +
			      " FROM uy_bankextract be" +
			      " INNER JOIN uy_loadextract le ON be.uy_loadextract_id = le.uy_loadextract_id" +
				  " WHERE le.docstatus = 'CO' AND NOT EXISTS (SELECT b.uy_bankextract_id FROM uy_conciliabank b" +
                  " INNER JOIN uy_conciliation hdr ON b.uy_conciliation_id=hdr.uy_conciliation_id" +
                  " WHERE hdr.docstatus IN ('CO','IP') AND b.uy_conciliated_id>0 AND b.uy_bankextract_id = be.uy_bankextract_id) AND be.c_bank_id=" + this.getC_Bank_ID() + " AND be.c_bankaccount_id=" + this.getC_BankAccount_ID() + " AND be.datetrx <= " + "'" + endDate + "'" +
                  " AND be.c_currency_id= " + currencyID + " AND be.success='Y' ORDER BY amtsourcedr,amtsourcecr";
			//Fin OpenUp.
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
			
			while (rs.next()) {
				
				cbank = new MConciliaBank (getCtx(),0,get_TrxName());
				
				cbank.setUY_Conciliation_ID(this.get_ID());
				cbank.setUY_BankExtract_ID(rs.getInt("uy_bankextract_id"));
				cbank.setC_Currency_ID(rs.getInt("c_currency_id"));
				cbank.setC_Bank_ID(rs.getInt("c_bank_id"));
				cbank.setC_BankAccount_ID(rs.getInt("c_bankaccount_id"));
				cbank.setDateTrx(rs.getTimestamp("datetrx"));
				cbank.setDescription(rs.getString("description"));
				cbank.setDocumentNo(rs.getString("documentno"));
				cbank.setAmtSourceCr(rs.getBigDecimal("amtsourcecr"));
				cbank.setAmtSourceDr(rs.getBigDecimal("amtsourcedr"));
				cbank.setUY_LoadExtract_ID(rs.getInt("uy_loadextract_id"));
				cbank.setsucursal(rs.getString("sucursal"));
				cbank.setDateValue(rs.getTimestamp("datevalue"));
				cbank.setTotalAmt(rs.getBigDecimal("totalamt"));
				cbank.setC_DocType_ID(rs.getInt("c_doctype_id"));
				//openUp. Nicolas Sarlabos. 21/05/2013. #845
				if(rs.getString("iserror").equalsIgnoreCase("N")){
					
					cbank.setIsError(false);
										
				} else cbank.setIsError(true);
				//Fin OpenUp.
				cbank.saveEx();
								
			}
			//FINALIZA CARGA DE MOVIMIENTOS DEL BANCO
			
			//COMIENZA CARGA DE MOVIMIENTOS DEL SISTEMA///////////////////////////////////////////////////////////////////////////////////////////////7
			
			//obtengo el c_elementvalue_id a partir de la cuenta bancaria ingresada como filtro
			sql = "SELECT cv.account_id" +
                  " FROM c_validcombination cv" +
                  " INNER JOIN c_bankaccount_acct b ON cv.c_validcombination_id=b.b_asset_acct" +
                  " WHERE c_bankaccount_id=" + this.getC_BankAccount_ID();
			elementValueID = DB.getSQLValueEx(get_TrxName(), sql);
						
			int mTableID_mov = MTable.getTable_ID("UY_MovBancariosHdr"); //obtengo m_table_id de tablas para usar en sql
			int mTableID_journal = MTable.getTable_ID("GL_Journal");
			int mTableID_mp = MTable.getTable_ID("UY_MediosPago");
			int mTableID_pay = MTable.getTable_ID("C_Payment");
			int mTableID_exdiff = MTable.getTable_ID("UY_ExchangeDiffHdr");			
					
			sql = "SELECT f.fact_acct_id,f.record_id,f.uy_accnat_currency_id,f.dateacct,mov.documentno,mov.c_doctype_id,f.uy_amtnativedr,f.uy_amtnativecr, coalesce(mov.description,'') as description,f.conciliaerror " +
                  " FROM fact_acct f" +
                  " INNER JOIN uy_movbancarioshdr mov ON f.record_id=mov.uy_movbancarioshdr_id" +
                  " INNER JOIN c_period p ON f.c_period_id = p.c_period_id" +
                  " WHERE p.enddate <= " + "'" + endDate + "'" + " AND ad_table_id = " + mTableID_mov + " AND account_id=" + elementValueID +
                  " AND docstatus='CO' AND f.uy_accnat_currency_id= " + currencyID +
                  " AND f.conciliar='Y' AND NOT EXISTS (SELECT s.fact_acct_id FROM uy_conciliasystem s" +
                  " INNER JOIN uy_conciliation hdr ON s.uy_conciliation_id=hdr.uy_conciliation_id" +
                  " WHERE hdr.docstatus IN ('CO','IP') AND s.uy_conciliated_id>0 AND s.fact_acct_id = f.fact_acct_id) AND f.ad_table_id <> " + mTableID_exdiff +
                  " UNION" +
                  " SELECT f.fact_acct_id,f.record_id,f.uy_accnat_currency_id,f.dateacct,j.documentno,j.c_doctype_id,f.uy_amtnativedr,f.uy_amtnativecr, coalesce(j.description,'') as description,f.conciliaerror " +
                  " FROM fact_acct f" +
                  " INNER JOIN c_period p ON f.c_period_id = p.c_period_id" +
                  " INNER JOIN gl_journal j ON f.record_id=j.gl_journal_id" +
                  " WHERE p.enddate <= " + "'" + endDate + "'" + " AND ad_table_id = " + mTableID_journal + " AND account_id=" + elementValueID + " AND j.docstatus='CO'" +
                  " AND f.uy_accnat_currency_id= " + currencyID + " AND f.conciliar='Y' AND NOT EXISTS (SELECT s.fact_acct_id FROM uy_conciliasystem s" +
                  " INNER JOIN uy_conciliation hdr ON s.uy_conciliation_id=hdr.uy_conciliation_id" +
                  " WHERE hdr.docstatus IN ('CO','IP') AND s.uy_conciliated_id>0 AND s.fact_acct_id = f.fact_acct_id) AND f.ad_table_id <> " + mTableID_exdiff +
                  " UNION" +
                  " SELECT f.fact_acct_id,f.record_id,f.uy_accnat_currency_id,f.dateacct,mp.checkno as documentno,mp.c_doctype_id,f.uy_amtnativedr,f.uy_amtnativecr, '' as description,f.conciliaerror " + 
                  " FROM fact_acct f" + 
                  " INNER JOIN c_period p ON f.c_period_id = p.c_period_id" +
                  " INNER JOIN uy_mediospago mp ON f.record_id=mp.uy_mediospago_id" + 
                  " WHERE p.enddate <= " + "'" + endDate + "'" + " AND ad_table_id = " + mTableID_mp + " AND account_id=" + elementValueID + " AND docstatus='CO'" + 
                  " AND f.uy_accnat_currency_id=" + currencyID + " AND f.conciliar='Y' AND NOT EXISTS (SELECT s.fact_acct_id FROM uy_conciliasystem s INNER JOIN uy_conciliation hdr ON s.uy_conciliation_id=hdr.uy_conciliation_id" + 
                  " WHERE hdr.docstatus IN ('CO','IP') AND s.uy_conciliated_id>0 AND s.fact_acct_id = f.fact_acct_id) AND f.ad_table_id <> " + mTableID_exdiff + 
                  " UNION" +
                  " SELECT f.fact_acct_id,f.record_id,f.uy_accnat_currency_id,f.dateacct,pay.documentno,pay.c_doctype_id,f.uy_amtnativedr,f.uy_amtnativecr, coalesce(pay.description,'') as description,f.conciliaerror " + 
                  " FROM fact_acct f" + 
                  " INNER JOIN c_period p ON f.c_period_id = p.c_period_id" +
                  " INNER JOIN c_payment pay ON f.record_id=pay.c_payment_id" +
                  " WHERE p.enddate <= " + "'" + endDate + "'" + " AND ad_table_id = " + mTableID_pay + " AND account_id=" + elementValueID + " AND docstatus='CO'" + 
                  " AND f.uy_accnat_currency_id=" + currencyID + " AND f.conciliar='Y' AND NOT EXISTS (SELECT s.fact_acct_id FROM uy_conciliasystem s INNER JOIN uy_conciliation hdr ON s.uy_conciliation_id=hdr.uy_conciliation_id" + 
                  " WHERE hdr.docstatus IN ('CO','IP') AND s.uy_conciliated_id>0 AND s.fact_acct_id = f.fact_acct_id) AND f.ad_table_id <> " + mTableID_exdiff + 
                  " ORDER BY uy_amtnativedr,uy_amtnativecr";
						
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
			
			while (rs.next()) {
				
				csystem = new MConciliaSystem (getCtx(),0,get_TrxName());
				MDocType doc = new MDocType (getCtx(),rs.getInt("c_doctype_id"),get_TrxName());
				
				String descripcion = "";
				if (!rs.getString("description").equalsIgnoreCase("")){
					descripcion = " - " + descripcion;
				}
				
				csystem.setFact_Acct_ID(rs.getInt("fact_acct_id"));
				csystem.setRecord_ID(rs.getInt("record_id"));
				csystem.setC_Currency_ID(rs.getInt("uy_accnat_currency_id"));
				csystem.setC_Bank_ID(this.getC_Bank_ID());
				csystem.setC_BankAccount_ID(this.getC_BankAccount_ID());
				csystem.setDateTrx(rs.getTimestamp("dateacct"));
				csystem.setDocumentNo(rs.getString("documentno"));
				csystem.setC_DocType_ID(doc.get_ID());
				csystem.setUY_Conciliation_ID(this.get_ID());
				csystem.setAmtSourceDr(rs.getBigDecimal("uy_amtnativedr"));
				csystem.setAmtSourceCr(rs.getBigDecimal("uy_amtnativecr"));
				csystem.setDescription(doc.getPrintName() + descripcion);
				//openUp. Nicolas Sarlabos. 21/05/2013. #845
				if(rs.getString("conciliaerror").equalsIgnoreCase("N")){
					
					csystem.setIsError(false);
										
				} else csystem.setIsError(true);
				//Fin OpenUp.				
				//si es un asiento diario entonces cargo la descripcion, tomando la primera que encuentre de 
				//las lineas o en su defecto la del cabezal del documento
				if(doc.getDocBaseType().equalsIgnoreCase("GLJ")){
					
					ResultSet r = null;
					PreparedStatement p = null;
					
					MJournal journal = new MJournal (getCtx(),rs.getInt("record_id"),get_TrxName());
									
					sql = "SELECT distinct(description)" +
					      " FROM gl_journalline" +
						  " WHERE gl_journal_id=" + journal.get_ID() + " AND c_elementvalue_id=" + elementValueID;
					
					p = DB.prepareStatement (sql, get_TrxName());
					r = p.executeQuery ();
					
					if(r.next()){
						
						csystem.setDescription(r.getString("description"));
												
					} else csystem.setDescription(journal.getDescription());
														
				}
									
				csystem.saveEx();
				
			}
			//FINALIZA CARGA DE MOVIMIENTOS DEL SISTEMA
			
			this.conciliate(); //realizo sugerencia de conciliacion automatica
							
		} catch (Exception e) {
			throw new AdempiereException(e);
			
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
			
	}
	
	/**OpenUp. Nicolas Sarlabos. 28/11/2012
	 * Metodo que crea conciliaciones automaticas con relacion 1 a 1
	 * @return
	 */

	private void conciliate() throws SQLException  {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int id = 0;
		int toleranceDate = this.getTolerance();
						
		//se recorren los documentos del banco
		sql = "SELECT * from uy_conciliabank WHERE uy_conciliation_id=" + this.get_ID();
		
		pstmt = DB.prepareStatement (sql, get_TrxName());
		rs = pstmt.executeQuery ();
		
		try {
			
			while(rs.next()){
				
				int cbank_id = rs.getInt("uy_conciliabank_id");
				BigDecimal amtDr = rs.getBigDecimal("amtsourcedr");
				BigDecimal amtCr = rs.getBigDecimal("amtsourcecr");
				boolean hayOrden1 = false;
				boolean hayOrden2 = false;
				boolean hayOrden3 = false;
				
				//se procede a la busqueda comenzando por el orden 1, si no se obtuvo resultado se pasa al orden 2, y asi sucesivamente
				if (this.getOrden1().equalsIgnoreCase("CHQ")){

					id = searchForCheckNo(amtDr,amtCr,rs.getString("documentno"));

				}else if(this.getOrden1().equalsIgnoreCase("DOC")){

					id = searchForDocumentno(amtDr,amtCr,rs.getString("documentno"));

				}else if(this.getOrden1().equalsIgnoreCase("AMT")){

					id = searchForAmount(amtDr,amtCr);

				}else if(this.getOrden1().equalsIgnoreCase("FCH")){

					id = searchForDate(amtDr,amtCr,rs.getTimestamp("datetrx"),toleranceDate);

				}
				//si obtuve resultado creo la conciliacion
				if(id>0) {
					
					generateConciliation(cbank_id,id,doc);
					hayOrden1 = true;
										
				}
								
				if(!hayOrden1){
					
					if (this.getOrden2().equalsIgnoreCase("CHQ")){

						id = searchForCheckNo(amtDr,amtCr,rs.getString("documentno"));

					}else if(this.getOrden2().equalsIgnoreCase("DOC")){

						id = searchForDocumentno(amtDr,amtCr,rs.getString("documentno"));

					}else if(this.getOrden2().equalsIgnoreCase("AMT")){

						id = searchForAmount(amtDr,amtCr);

					}else if(this.getOrden2().equalsIgnoreCase("FCH")){

						id = searchForDate(amtDr,amtCr,rs.getTimestamp("datetrx"),toleranceDate);

					}
					//si obtuve resultado creo la conciliacion
					if(id>0) {
						
						generateConciliation(cbank_id,id,doc);
						hayOrden2 = true;
											
					}
					
					if(!hayOrden2){
						
						if (this.getOrden3().equalsIgnoreCase("CHQ")){

							id = searchForCheckNo(amtDr,amtCr,rs.getString("documentno"));

						}else if(this.getOrden3().equalsIgnoreCase("DOC")){

							id = searchForDocumentno(amtDr,amtCr,rs.getString("documentno"));

						}else if(this.getOrden3().equalsIgnoreCase("AMT")){

							id = searchForAmount(amtDr,amtCr);

						}else if(this.getOrden3().equalsIgnoreCase("FCH")){

							id = searchForDate(amtDr,amtCr,rs.getTimestamp("datetrx"),toleranceDate);

						}
						//si obtuve resultado creo la conciliacion
						if(id>0) {
							
							generateConciliation(cbank_id,id,doc);
							hayOrden3 = true;
												
						}
						
						if(!hayOrden3){
							
							if (this.getOrden4().equalsIgnoreCase("CHQ")){

								id = searchForCheckNo(amtDr,amtCr,rs.getString("documentno"));

							}else if(this.getOrden4().equalsIgnoreCase("DOC")){

								id = searchForDocumentno(amtDr,amtCr,rs.getString("documentno"));

							}else if(this.getOrden4().equalsIgnoreCase("AMT")){

								id = searchForAmount(amtDr,amtCr);

							}else if(this.getOrden4().equalsIgnoreCase("FCH")){

								id = searchForDate(amtDr,amtCr,rs.getTimestamp("datetrx"),toleranceDate);

							}
							//si obtuve resultado creo la conciliacion
							if(id>0) generateConciliation(cbank_id,id,doc);
						}					
						
					}
									
				}
							
			}//fin WHILE
			
		}catch (Exception e) {
			throw new AdempiereException(e);
			
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
			
		
	}
	
	/**OpenUp. Nicolas Sarlabos. 28/11/2012
	 * Metodo que crea cabezal de partidas conciliadas y marca la linea del banco y el sistema como conciliadas
	 * @param cbank_id
	 * @param csystem_id
	 * @param doc
	 */
	
	private void generateConciliation(int cbank_id,int csystem_id,MDocType doc){
		
		//creo cabezal
		MConciliated con = new MConciliated(getCtx(),0,get_TrxName());
		con.setUY_Conciliation_ID(this.get_ID());
		con.setC_DocType_ID(doc.get_ID());
		con.saveEx();
		
		//obtengo linea del banco y seteo ID del cabezal
		MConciliaBank cbank = new MConciliaBank(getCtx(),cbank_id,get_TrxName());
		cbank.setUY_Conciliated_ID(con.get_ID());
		cbank.saveEx();
		
		//obtengo linea del sistema y seteo ID del cabezal
		MConciliaSystem csystem = new MConciliaSystem(getCtx(),csystem_id,get_TrxName());
		csystem.setUY_Conciliated_ID(con.get_ID());
		csystem.saveEx();
					
	}
	
	/**OpenUp. Nicolas Sarlabos. 28/11/2012
	 * Metodo de busqueda por criterio de numero de cheque
	 * @param amtDr
	 * @param amtCr
	 * @param documentno
	 * @return
	 */
	
	private int searchForCheckNo(BigDecimal amtDr,BigDecimal amtCr,String documentno){
		
		int value = 0;
		String sql = "";

		sql = "SELECT uy_conciliasystem_id FROM uy_conciliasystem WHERE uy_conciliation_id= " + this.get_ID() + " AND lower(documentno)= lower(" + "'" + documentno + "') AND amtsourcedr=" + 
				amtCr + " AND amtsourcecr=" + amtDr + " AND uy_conciliated_id is null ORDER BY documentno";

		value = DB.getSQLValueEx(get_TrxName(), sql);

		return value;	
		
	}
	
	/**OpenUp. Nicolas Sarlabos. 28/11/2012
	 * Metodo de busqueda por criterio de numero de documento
	 * @param amtDr
	 * @param amtCr
	 * @param documentno
	 * @return
	 */
	
	private int searchForDocumentno(BigDecimal amtDr,BigDecimal amtCr,String documentno){
		
		int value = 0;
		String sql = "";

		sql = "SELECT uy_conciliasystem_id FROM uy_conciliasystem WHERE uy_conciliation_id= " + this.get_ID() + " AND lower(documentno)= lower(" + "'" + documentno + "') AND amtsourcedr=" + 
				amtCr + " AND amtsourcecr=" + amtDr + " AND uy_conciliated_id is null ORDER BY documentno";

		value = DB.getSQLValueEx(get_TrxName(), sql);

		return value;	
		
	}
	
	/**OpenUp. Nicolas Sarlabos. 28/11/2012
	 * Metodo de busqueda por criterio de monto
	 * @param amtDr
	 * @param amtCr
	 * @param documentno
	 * @return
	 */
	
	private int searchForAmount(BigDecimal amtDr,BigDecimal amtCr){
		
		int value = 0;
		String sql = "";

		sql = "SELECT uy_conciliasystem_id FROM uy_conciliasystem WHERE uy_conciliation_id= " + this.get_ID() + " AND amtsourcedr=" + 
				amtCr + " AND amtsourcecr=" + amtDr + " AND uy_conciliated_id is null";

		value = DB.getSQLValueEx(get_TrxName(), sql);

		return value;	
		
	}
	
	/**OpenUp. Nicolas Sarlabos. 28/11/2012
	 * Metodo de busqueda por criterio de fecha
	 * @param amtDr
	 * @param amtCr
	 * @param documentno
	 * @return
	 */
	
	private int searchForDate(BigDecimal amtDr,BigDecimal amtCr,Timestamp date,int tolerance){
		
		//Openup. Nicolas Sarlabos. 17/04/2013. #676
		int value = 0;
		String sql = "";
		Timestamp startDate = null;
		Timestamp endDate = null;
		String whereDate = null;
		
		if(tolerance >0){ //si hay tolerancia
			
			sql = "SELECT " + "'" + date + "'::date - " + tolerance;
			startDate = DB.getSQLValueTSEx(get_TrxName(), sql);
			
			sql = "SELECT " + "'" + date + "'::date + " + tolerance;
			endDate = DB.getSQLValueTSEx(get_TrxName(), sql);
			
			whereDate = " AND datetrx >= " + "'" + startDate + "'" + " AND datetrx <= " + "'" + endDate + "'";
						
		} else whereDate = " AND datetrx=" + "'" + date + "'";
		
		sql = "SELECT uy_conciliasystem_id FROM uy_conciliasystem WHERE uy_conciliation_id= " + this.get_ID() + " AND amtsourcedr=" + amtCr +
				" AND amtsourcecr=" + amtDr + " AND uy_conciliated_id is null " + whereDate;
		
		value = DB.getSQLValueEx(get_TrxName(), sql);

		return value;	
		//Fin OpenUp. #676
	}
	
	
	/**OpenUp. Nicolas Sarlabos. 29/11/2012
	 * Metodo que crea conciliaciones manuales
	 * @return
	 */
	
	public void createManualConciliation() {
		
		String sql = "", msg = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal toleranceAmt = this.getToleranceAmount();
		boolean conciliate = true;
		BigDecimal sumSystem = Env.ZERO;
		BigDecimal sumBank = Env.ZERO;		
		BigDecimal dif = Env.ZERO;
				
		if(toleranceAmt==null) toleranceAmt = Env.ZERO;
		
		if(toleranceAmt.compareTo(Env.ZERO)<0) toleranceAmt = toleranceAmt.negate(); //siempre utilizo tolerancia positiva
		
		List<MConciliaSystem> sysLines = this.getLinesSysManual();
		List<MConciliaBank> bankLines = this.getLinesBankManual();
				
		//SE REALIZA LA CONCILIACION ENTRE LAS DOS GRILLAS
		sql = "SELECT coalesce(SUM(amtsourcecr)-SUM(amtsourcedr),0) FROM uy_conciliasystem WHERE ismanual='Y'";
		sumSystem = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		sql = "SELECT coalesce(SUM(amtsourcedr)-SUM(amtsourcecr),0) FROM uy_conciliabank WHERE ismanual='Y'";
		sumBank = DB.getSQLValueBDEx(get_TrxName(), sql);
				
		if(toleranceAmt.compareTo(Env.ZERO)!=0){
			
			dif = sumSystem.subtract(sumBank);
			
			if(dif.compareTo(toleranceAmt)<=0 && dif.compareTo(toleranceAmt.negate())>=0){
				
				conciliate = true;
				
			} else {
				
				msg += "Imposible conciliar movimientos, los importes no coinciden. Verifique tolerancia de cuenta bancaria. \n";
				conciliate = false;
			}
			
		} else if (!(sumSystem.compareTo(sumBank)==0)) {
			
			msg += "Imposible conciliar movimientos, los importes no coinciden. Verifique tolerancia de cuenta bancaria. \n";
			conciliate = false;
		}
			
		if((sysLines.size() > 0 && bankLines.size() <= 0) || (sysLines.size() <= 0 && bankLines.size() > 0)) {
			
			msg += "Debe seleccionar movimientos en ambas grillas para conciliar manual \n";
			conciliate = false; 
		} else if ((sysLines.size() <= 0 && bankLines.size() <= 0)) conciliate = false;
		
		
		if(conciliate) {
			
			//creo cabezal
			MConciliated con = new MConciliated(getCtx(),0,get_TrxName());
			con.setUY_Conciliation_ID(this.get_ID());
			con.setC_DocType_ID(doc.get_ID());
			con.saveEx();
					
			try{
				
				//procedo con las lineas del banco...
				sql = "SELECT uy_conciliabank_id FROM uy_conciliabank WHERE ismanual='Y'";
				
				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery ();
				
				while(rs.next()){
					
					MConciliaBank cbank = new MConciliaBank(getCtx(),rs.getInt("uy_conciliabank_id"),get_TrxName());
					cbank.setUY_Conciliated_ID(con.get_ID());
					cbank.setIsManual(false);
					cbank.saveEx();
										
				}
				
				//procedo con las lineas del sistema...
				sql = "SELECT uy_conciliasystem_id FROM uy_conciliasystem WHERE ismanual='Y'";
				
				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery ();
				
				while(rs.next()){
					
					MConciliaSystem csystem = new MConciliaSystem(getCtx(),rs.getInt("uy_conciliasystem_id"),get_TrxName());
					csystem.setUY_Conciliated_ID(con.get_ID());
					csystem.setIsManual(false);
					csystem.saveEx();
										
				}							
				
			} catch (Exception e) {
				throw new AdempiereException(e);
				
			} finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
				
		}
				
		//SE REALIZA LA CONCILIACION EN GRILLA DEL SISTEMA
		String sys = this.createManualSameGrid(true);
				
		//SE REALIZA LA CONCILIACION EN GRILLA DEL BANCO
		String bank = this.createManualSameGrid(false);
				
		msg += sys + bank;
		
		if(msg.equals("")) msg = "OK";
					
		if(msg.equalsIgnoreCase("OK")){
			
			this.resetTotalPartial(); //reseteo los totalizadores parciales			
			ADialog.info(0,null,"Conciliaciones realizadas con exito");
			
		} else if(!msg.equalsIgnoreCase("")){
			
			String error = "Verifique las siguientes inconsistencias: \n ";
			error = error + msg;				
			ADialog.error(0,null,error);
			
		} else ADialog.error(0,null,"No se seleccionaron movimientos");
					
	}
	
	/**OpenUp. Nicolas Sarlabos. 01/11/2013. #1484
	 * Metodo que realiza la conciliacion para movimientos en la misma grilla.
	 * @return
	 */
	
	public String createManualSameGrid(boolean system) {
		
		String sql = "", table = "", msg = "", name = "";
		BigDecimal dif = Env.ZERO;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean conciliate = true;
		BigDecimal toleranceAmt = this.getToleranceAmount();
		int lines = 0;
		
		List<MConciliaSystem> sysLines = null;
		List<MConciliaBank> bankLines = null;
		
		if(system){
			table = "uy_conciliasystem";	
			name = "sistema";
		} else {
			table = "uy_conciliabank";
			name = "banco";
		}
		
		sql = "SELECT coalesce(SUM(amtsourcecr)-SUM(amtsourcedr),0) FROM " + table + " WHERE issamegrid='Y'";
		dif = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		if(toleranceAmt.compareTo(Env.ZERO)!=0){
									
			if(dif.compareTo(toleranceAmt)<=0 && dif.compareTo(toleranceAmt.negate())>=0){
				
				conciliate = true;
				
			} else {
				
				msg += "Imposible conciliar movimientos del " + name + " en igual grilla, los importes no coinciden. Verifique tolerancia de cuenta bancaria. \n";
				conciliate = false;
			}
			
		} else if (!(dif.compareTo(Env.ZERO)==0)) {
			
			msg += "Imposible conciliar movimientos del " + name + " en igual grilla, los importes no coinciden. Verifique tolerancia de cuenta bancaria. \n";
			conciliate = false;
		}
		
		if(system){

			sysLines = this.getLinesSysSameGrid();
			lines = sysLines.size();

		} else {

			bankLines = this.getLinesBankSameGrid();
			lines = bankLines.size();
		
		}		
		
		if(lines <= 0) conciliate = false;
		
		if(conciliate) {
			
			//creo cabezal
			MConciliated con = new MConciliated(getCtx(),0,get_TrxName());
			con.setUY_Conciliation_ID(this.get_ID());
			con.setC_DocType_ID(doc.get_ID());
			con.saveEx();
					
			try{
				
				sql = "SELECT " + table + "_id FROM " + table + " WHERE issamegrid='Y'";
				
				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery ();
				
				while(rs.next()){
					
					if(system){
						
						MConciliaSystem csystem = new MConciliaSystem(getCtx(),rs.getInt("uy_conciliasystem_id"),get_TrxName());
						csystem.setUY_Conciliated_ID(con.get_ID());
						csystem.setIsSameGrid(false);
						csystem.saveEx();
						
					} else {
						
						MConciliaBank cbank = new MConciliaBank(getCtx(),rs.getInt("uy_conciliabank_id"),get_TrxName());
						cbank.setUY_Conciliated_ID(con.get_ID());
						cbank.setIsSameGrid(false);
						cbank.saveEx();						
					}			
										
				}							
				
			} catch (Exception e) {
				throw new AdempiereException(e);
				
			} finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
						
		} 		
		
		return msg;		
	}
	
	/**OpenUp. Nicolas Sarlabos. 13/06/2013. #1002
	 * Metodo que refresca la grilla de movimientos del sistema
	 * @return
	 */
	
	public void refresh() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Timestamp endDate = null;
		MConciliaSystem csystem = null;
		int elementValueID = 0;
		int currencyID = 0;
		
		MBankAccount account = new MBankAccount (getCtx(),this.getC_BankAccount_ID(),get_TrxName()); //instancio cuenta bancaria
		
		MPeriod period = new MPeriod (getCtx(),this.getC_Period_ID(),get_TrxName()); //instancio periodo para obtener las fechas del mismo
		
		if (period.get_ID()>0) endDate = period.getEndDate();
					
		sql = "SELECT c_currency_id FROM c_bankaccount WHERE c_bankaccount_id = " + account.getC_BankAccount_ID(); //obtengo moneda de la cuenta bancaria
		currencyID = DB.getSQLValue(get_TrxName(), sql);
		
		try {
			
			//obtengo el c_elementvalue_id a partir de la cuenta bancaria ingresada como filtro
			sql = "SELECT cv.account_id" +
                  " FROM c_validcombination cv" +
                  " INNER JOIN c_bankaccount_acct b ON cv.c_validcombination_id=b.b_asset_acct" +
                  " WHERE c_bankaccount_id=" + this.getC_BankAccount_ID();
			elementValueID = DB.getSQLValueEx(get_TrxName(), sql);
						
			int mTableID_mov = MTable.getTable_ID("UY_MovBancariosHdr"); //obtengo m_table_id de tablas para usar en sql
			int mTableID_journal = MTable.getTable_ID("GL_Journal");
			int mTableID_mp = MTable.getTable_ID("UY_MediosPago");
			int mTableID_pay = MTable.getTable_ID("C_Payment");
			int mTableID_exdiff = MTable.getTable_ID("UY_ExchangeDiffHdr");			
					
			sql = "SELECT f.fact_acct_id,f.record_id,f.uy_accnat_currency_id,f.dateacct,mov.documentno,mov.c_doctype_id,f.uy_amtnativedr,f.uy_amtnativecr, coalesce(mov.description,'') as description,f.conciliaerror " +
                  " FROM fact_acct f" +
                  " INNER JOIN uy_movbancarioshdr mov ON f.record_id=mov.uy_movbancarioshdr_id" +
                  " INNER JOIN c_period p ON f.c_period_id = p.c_period_id" +
                  " WHERE p.enddate <= " + "'" + endDate + "'" + " AND ad_table_id = " + mTableID_mov + " AND account_id=" + elementValueID +
                  " AND docstatus='CO' AND f.uy_accnat_currency_id= " + currencyID +
                  " AND f.conciliar='Y' AND NOT EXISTS (SELECT s.fact_acct_id FROM uy_conciliasystem s" +
                  " INNER JOIN uy_conciliation hdr ON s.uy_conciliation_id=hdr.uy_conciliation_id" +
                  " WHERE hdr.docstatus='CO' AND s.uy_conciliated_id>0 AND s.fact_acct_id = f.fact_acct_id) AND f.ad_table_id <> " + mTableID_exdiff + " AND NOT EXISTS (SELECT fact_acct_id FROM uy_conciliasystem WHERE uy_conciliation_id = " + this.get_ID() + " and fact_acct_id = f.fact_acct_id)" +
                  " UNION" +
                  " SELECT f.fact_acct_id,f.record_id,f.uy_accnat_currency_id,f.dateacct,j.documentno,j.c_doctype_id,f.uy_amtnativedr,f.uy_amtnativecr, coalesce(j.description,'') as description,f.conciliaerror " +
                  " FROM fact_acct f" +
                  " INNER JOIN c_period p ON f.c_period_id = p.c_period_id" +
                  " INNER JOIN gl_journal j ON f.record_id=j.gl_journal_id" +
                  " WHERE p.enddate <= " + "'" + endDate + "'" + " AND ad_table_id = " + mTableID_journal + " AND account_id=" + elementValueID + " AND j.docstatus='CO'" +
                  " AND f.uy_accnat_currency_id= " + currencyID + " AND f.conciliar='Y' AND NOT EXISTS (SELECT s.fact_acct_id FROM uy_conciliasystem s" +
                  " INNER JOIN uy_conciliation hdr ON s.uy_conciliation_id=hdr.uy_conciliation_id" +
                  " WHERE hdr.docstatus='CO' AND s.uy_conciliated_id>0 AND s.fact_acct_id = f.fact_acct_id) AND f.ad_table_id <> " + mTableID_exdiff + " AND NOT EXISTS (SELECT fact_acct_id FROM uy_conciliasystem WHERE uy_conciliation_id = " + this.get_ID() + " and fact_acct_id = f.fact_acct_id)" +
                  " UNION" +
                  " SELECT f.fact_acct_id,f.record_id,f.uy_accnat_currency_id,f.dateacct,mp.checkno as documentno,mp.c_doctype_id,f.uy_amtnativedr,f.uy_amtnativecr, '' as description,f.conciliaerror " + 
                  " FROM fact_acct f" + 
                  " INNER JOIN c_period p ON f.c_period_id = p.c_period_id" +
                  " INNER JOIN uy_mediospago mp ON f.record_id=mp.uy_mediospago_id" + 
                  " WHERE p.enddate <= " + "'" + endDate + "'" + " AND ad_table_id = " + mTableID_mp + " AND account_id=" + elementValueID + " AND docstatus='CO'" + 
                  " AND f.uy_accnat_currency_id=" + currencyID + " AND f.conciliar='Y' AND NOT EXISTS (SELECT s.fact_acct_id FROM uy_conciliasystem s INNER JOIN uy_conciliation hdr ON s.uy_conciliation_id=hdr.uy_conciliation_id" + 
                  " WHERE hdr.docstatus='CO' AND s.uy_conciliated_id>0 AND s.fact_acct_id = f.fact_acct_id) AND f.ad_table_id <> " + mTableID_exdiff + " AND NOT EXISTS (SELECT fact_acct_id FROM uy_conciliasystem WHERE uy_conciliation_id = " + this.get_ID() + " and fact_acct_id = f.fact_acct_id)" + 
                  " UNION" +
                  " SELECT f.fact_acct_id,f.record_id,f.uy_accnat_currency_id,f.dateacct,pay.documentno,pay.c_doctype_id,f.uy_amtnativedr,f.uy_amtnativecr, coalesce(pay.description,'') as description,f.conciliaerror " + 
                  " FROM fact_acct f" + 
                  " INNER JOIN c_period p ON f.c_period_id = p.c_period_id" +
                  " INNER JOIN c_payment pay ON f.record_id=pay.c_payment_id" +
                  " WHERE p.enddate <= " + "'" + endDate + "'" + " AND ad_table_id = " + mTableID_pay + " AND account_id=" + elementValueID + " AND docstatus='CO'" + 
                  " AND f.uy_accnat_currency_id=" + currencyID + " AND f.conciliar='Y' AND NOT EXISTS (SELECT s.fact_acct_id FROM uy_conciliasystem s INNER JOIN uy_conciliation hdr ON s.uy_conciliation_id=hdr.uy_conciliation_id" + 
                  " WHERE hdr.docstatus='CO' AND s.uy_conciliated_id>0 AND s.fact_acct_id = f.fact_acct_id) AND f.ad_table_id <> " + mTableID_exdiff + " AND NOT EXISTS (SELECT fact_acct_id FROM uy_conciliasystem WHERE uy_conciliation_id = " + this.get_ID() + " and fact_acct_id = f.fact_acct_id)" + 
                  " ORDER BY uy_amtnativedr,uy_amtnativecr";
						
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
			
			while (rs.next()) {
				
				csystem = new MConciliaSystem (getCtx(),0,get_TrxName());
				MDocType doc = new MDocType (getCtx(),rs.getInt("c_doctype_id"),get_TrxName());
				
				String descripcion = "";
				if (!rs.getString("description").equalsIgnoreCase("")){
					descripcion = " - " + descripcion;
				}
				
				csystem.setFact_Acct_ID(rs.getInt("fact_acct_id"));
				csystem.setRecord_ID(rs.getInt("record_id"));
				csystem.setC_Currency_ID(rs.getInt("uy_accnat_currency_id"));
				csystem.setC_Bank_ID(this.getC_Bank_ID());
				csystem.setC_BankAccount_ID(this.getC_BankAccount_ID());
				csystem.setDateTrx(rs.getTimestamp("dateacct"));
				csystem.setDocumentNo(rs.getString("documentno"));
				csystem.setC_DocType_ID(doc.get_ID());
				csystem.setUY_Conciliation_ID(this.get_ID());
				csystem.setAmtSourceDr(rs.getBigDecimal("uy_amtnativedr"));
				csystem.setAmtSourceCr(rs.getBigDecimal("uy_amtnativecr"));
				csystem.setDescription(doc.getPrintName() + descripcion);
				
				if(rs.getString("conciliaerror").equalsIgnoreCase("N")){
					
					csystem.setIsError(false);
										
				} else csystem.setIsError(true);
							
				//si es un asiento diario entonces cargo la descripcion, tomando la primera que encuentre de 
				//las lineas o en su defecto la del cabezal del documento
				if(doc.getDocBaseType().equalsIgnoreCase("GLJ")){
					
					ResultSet r = null;
					PreparedStatement p = null;
					
					MJournal journal = new MJournal (getCtx(),rs.getInt("record_id"),get_TrxName());
									
					sql = "SELECT distinct(description)" +
					      " FROM gl_journalline" +
						  " WHERE gl_journal_id=" + journal.get_ID() + " AND c_elementvalue_id=" + elementValueID;
					
					p = DB.prepareStatement (sql, get_TrxName());
					r = p.executeQuery ();
					
					if(r.next()){
						
						csystem.setDescription(r.getString("description"));
												
					} else csystem.setDescription(journal.getDescription());
														
				}

				csystem.saveEx();

			}		

			//OpenUp. Nicolas Sarlabos. 19/06/2013. #1042. Se eliminan de la grilla los movimientos del sistema que han sido borrados de la contabilidad.
			sql = "delete from uy_conciliasystem where uy_conciliation_id = " + this.get_ID() +
				  " and fact_acct_id not in (select fact_acct_id from fact_acct where ad_client_id = " + this.getAD_Client_ID() + 
				  " and ad_org_id = " + this.getAD_Org_ID() + ") and uy_conciliated_id is null";

			DB.executeUpdateEx(sql, get_TrxName());
			//Fin #
			
		} catch (Exception e) {
			throw new AdempiereException(e);
			
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}		
	}	
	
	/**OpenUp. Nicolas Sarlabos. 20/05/2013 #845
	 * Metodo que actualiza movimientos que han sido marcados como error y asientos conciliados
	 * @return
	 */
	
	public void updateFactsComplete() {
		
		String sql = "", error = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			
			sql = "select uy_conciliasystem_id,fact_acct_id" +
			      " from uy_conciliasystem" +
				  " where uy_conciliation_id=" + this.get_ID();
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
			
			while(rs.next()){

				MConciliaSystem con = new MConciliaSystem(getCtx(),rs.getInt("uy_conciliasystem_id"),get_TrxName()); //instancio linea de movimiento contable
				
				if(con.get_ID() > 0) { 
					
					if(con.isError()){
						
						error = "Y";
						
					} else error = "N";
					
					DB.executeUpdateEx("update fact_acct set conciliaerror='" + error + "' where fact_acct_id=" + rs.getInt("fact_acct_id"),get_TrxName());
										
				}
								
			}

			sql = "select uy_conciliabank_id,uy_bankextract_id" +
					" from uy_conciliabank" +
					" where uy_conciliation_id=" + this.get_ID();

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();

			while(rs.next()){

				MBankExtract ext = new MBankExtract(getCtx(),rs.getInt("uy_bankextract_id"),get_TrxName()); //instancio linea de extracto bancario
				MConciliaBank con = new MConciliaBank(getCtx(),rs.getInt("uy_conciliabank_id"),get_TrxName()); //instancio linea de movimiento bancario

				if(ext.get_ID() >0 && con.get_ID() > 0) { 

					ext.setIsError(con.isError());
					ext.saveEx();
				}										
			}	

			sql = "select fact_acct_id" +
					" from uy_conciliasystem" +
					" where uy_conciliation_id = " + this.get_ID() + 
					" and uy_conciliated_id is not null" +
					" and fact_acct_id > 0";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();

			while(rs.next()){

				int rows = DB.executeUpdateEx("update fact_acct set uy_conciliation_id = " + this.get_ID() + " where fact_acct_id=" + rs.getInt("fact_acct_id"),get_TrxName());

				if(rows <= 0) throw new AdempiereException("Imposible completar, se han eliminado asientos contables mientras la conciliacion actual estaba en estado APLICADO");

			}

		} catch (Exception e) {
			throw new AdempiereException(e);
			
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}		
	}
	
	/**OpenUp. Nicolas Sarlabos. 12/11/2013 #1307
	 * Metodo que actualiza movimientos que al anular ya no estaran conciliados.
	 * @return
	 */
	
	public void updateFactsVoid() {
		
		String	sql = "update fact_acct set uy_conciliation_id = null where fact_acct_id in" +
				" (select fact_acct_id from uy_conciliasystem" +
				" where uy_conciliation_id = " + this.get_ID() + ")";
			
		DB.executeUpdateEx(sql, get_TrxName());			
			
		
	}
		
	/**OpenUp. Nicolas Sarlabos. 06/11/2013
	 * Metodo que setea en cero los 3 campos de importes parciales del cabezal. 
	 * que ningun movimiento se encuentre en otra conciliacion en estado completo
	 * @return
	 */
	public void resetTotalPartial(){
		
		String sql = "update uy_conciliation set amt1 = 0, amt2 = 0, diference = 0 where uy_conciliation_id = " + this.get_ID();
		
		DB.executeUpdateEx(sql, get_TrxName());
			
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		//	Re-Check
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		MPeriod period = new MPeriod (getCtx(),this.getC_Period_ID(),get_TrxName());
		
		// Verifico periodo contable para documento
		MPeriod.testPeriodOpen(getCtx(), period.getStartDate(), this.getC_DocType_ID(), this.getAD_Org_ID());
				
		//valido antes de completar
		validBeforeComplete();
		
		//actualizo movimientos conciliados y marcados como error
		updateFactsComplete();
		
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

	/**OpenUp. Nicolas Sarlabos. 29/11/2012
	 * Metodo que valida antes de completar el documento 
	 * que ningun movimiento se encuentre en otra conciliacion en estado completo o en proceso
	 * @return
	 */
	private void validBeforeComplete() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;
		PreparedStatement p = null;
		
		try {  //recorro los movimientos del sistema para la conciliacion actual
		
		sql = "SELECT fact_acct_id FROM uy_conciliasystem WHERE uy_conciliation_id= " + this.get_ID();
		
		pstmt = DB.prepareStatement (sql, get_TrxName());
		rs = pstmt.executeQuery ();
		
		//para cada movimiento(sistema y banco) me fijo si ya existe el mismo en otro documento de conciliacion
		//en estado completo y si ya fue conciliado en dicho documento, si es asi al encontrar el primero ya no sera posible completar 
		while(rs.next()){
						
			sql = "SELECT s.c_doctype_id,s.documentno FROM uy_conciliasystem s" +
                  " INNER JOIN uy_conciliation hdr ON s.uy_conciliation_id=hdr.uy_conciliation_id" +
                  " WHERE hdr.docstatus IN ('CO','IP') AND s.uy_conciliated_id > 0 AND hdr.uy_conciliation_id <> " + this.get_ID() +
                  " AND fact_acct_id=" + rs.getInt("fact_acct_id");
			
			p = DB.prepareStatement (sql, get_TrxName());
			r = p.executeQuery ();
			
			if(r.next()){
								
				MDocType doc = new MDocType(getCtx(),r.getInt("c_doctype_id"),get_TrxName());
				
				throw new AdempiereException ("Imposible completar: el documento " + doc.getName() + " N° " + r.getString("documentno") + " ya fue conciliado en otro documento de conciliacion");
								
			}
					
		}
		
		sql = "SELECT uy_bankextract_id FROM uy_conciliabank WHERE uy_conciliation_id= " + this.get_ID();
		
		pstmt = DB.prepareStatement (sql, get_TrxName());
		rs = pstmt.executeQuery ();
		
		while(rs.next()){
			
			sql = "SELECT b.documentno,b.description FROM uy_conciliabank b" +
                  " INNER JOIN uy_conciliation hdr ON b.uy_conciliation_id=hdr.uy_conciliation_id" +
                  " WHERE hdr.docstatus IN ('CO','IP') AND b.uy_conciliated_id > 0 AND hdr.uy_conciliation_id <> " + this.get_ID() +
                  " AND uy_bankextract_id=" + rs.getInt("uy_bankextract_id");
			
			p = DB.prepareStatement (sql, get_TrxName());
			r = p.executeQuery ();
			
			if(r.next()){
					
				throw new AdempiereException ("Imposible completar: el documento " + r.getString("description") + " N° " + r.getString("documentno") + " ya fue conciliado en otro documento de conciliacion");
				
			}
					
		}
				
		}catch (Exception e) {
			throw new AdempiereException(e);
			
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}
	
	/***
	 * Obtiene y retorna lineas del sistema marcadas para conciliar manual.
	 * OpenUp Ltda. Issue #1484
	 * @author Nicolas Sarlabos - 04/11/2013
	 * @see
	 * @return
	 */
	public List<MConciliaSystem> getLinesSysManual(){

		String whereClause = X_UY_ConciliaSystem.COLUMNNAME_UY_Conciliation_ID + "=" + this.get_ID() + " AND " + X_UY_ConciliaSystem.COLUMNNAME_IsManual + " = 'Y'";

		List<MConciliaSystem> lines = new Query(getCtx(), I_UY_ConciliaSystem.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lineas del sistema marcadas para conciliar manual en misma grilla.
	 * OpenUp Ltda. Issue #1484
	 * @author Nicolas Sarlabos - 04/11/2013
	 * @see
	 * @return
	 */
	public List<MConciliaSystem> getLinesSysSameGrid(){

		String whereClause = X_UY_ConciliaSystem.COLUMNNAME_UY_Conciliation_ID + "=" + this.get_ID() + " AND " + X_UY_ConciliaSystem.COLUMNNAME_IsSameGrid + " = 'Y'";

		List<MConciliaSystem> lines = new Query(getCtx(), I_UY_ConciliaSystem.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lineas del banco marcadas para conciliar manual.
	 * OpenUp Ltda. Issue #1484
	 * @author Nicolas Sarlabos - 04/11/2013
	 * @see
	 * @return
	 */
	public List<MConciliaBank> getLinesBankManual(){

		String whereClause = X_UY_ConciliaBank.COLUMNNAME_UY_Conciliation_ID + "=" + this.get_ID() + " AND " + X_UY_ConciliaBank.COLUMNNAME_IsManual + " = 'Y'";

		List<MConciliaBank> lines = new Query(getCtx(), I_UY_ConciliaBank.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lineas del banco marcadas para conciliar manual en misma grilla.
	 * OpenUp Ltda. Issue #1484
	 * @author Nicolas Sarlabos - 04/11/2013
	 * @see
	 * @return
	 */
	public List<MConciliaBank> getLinesBankSameGrid(){

		String whereClause = X_UY_ConciliaBank.COLUMNNAME_UY_Conciliation_ID + "=" + this.get_ID() + " AND " + X_UY_ConciliaBank.COLUMNNAME_IsSameGrid + " = 'Y'";

		List<MConciliaBank> lines = new Query(getCtx(), I_UY_ConciliaBank.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna importe de movimientos del sistema seleccionados para conciliar manual.
	 * OpenUp Ltda. Issue #1487
	 * @author Nicolas Sarlabos - 06/11/2013
	 * @see
	 * @return
	 */
	public BigDecimal getTotalSystem(){
		
		String sql = "SELECT coalesce(SUM(amtsourcedr)-SUM(amtsourcecr),0) FROM uy_conciliasystem WHERE ismanual='Y'";
		BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);

		return amount;
	}
	
	/***
	 * Obtiene y retorna importe de movimientos del banco seleccionados para conciliar manual.
	 * OpenUp Ltda. Issue #1487
	 * @author Nicolas Sarlabos - 06/11/2013
	 * @see
	 * @return
	 */
	public BigDecimal getTotalBank(){
		
		String sql = "SELECT coalesce(SUM(amtsourcecr)-SUM(amtsourcedr),0) FROM uy_conciliabank WHERE ismanual='Y'";
		BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);

		return amount;
	}

	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		MPeriod period = new MPeriod (getCtx(),this.getC_Period_ID(),get_TrxName());
		
		// Verifico periodo contable para documento
		MPeriod.testPeriodOpen(getCtx(), period.getStartDate(), this.getC_DocType_ID(), this.getAD_Org_ID());
		
		//actualizo movimientos conciliados
		updateFactsVoid();
		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
	}

	@Override
	public boolean closeIt() {
		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (this.processMsg != null)
			return false;
				
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Closed);
		setDocAction(DocAction.ACTION_None);
		
		return true;
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
		// Before reActivate
		processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (processMsg != null)
			return false;
		
		MPeriod period = new MPeriod (getCtx(),this.getC_Period_ID(),get_TrxName()); 
		
		// Verifico periodo contable para documento
		MPeriod.testPeriodOpen(getCtx(), period.getStartDate(), this.getC_DocType_ID(), this.getAD_Org_ID());
			
		// After reActivate
		processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (processMsg != null)
			return false;
		
		this.setDocAction(DOCACTION_Complete);
		this.setDocStatus(DOCSTATUS_InProgress);
		this.setProcessed(false);
		
		return true;
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
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}


}
