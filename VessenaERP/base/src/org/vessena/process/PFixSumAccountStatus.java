/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 14/05/2013
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.I_UY_CashMove;
import org.openup.model.I_UY_MovBancariosHdr;
import org.openup.model.MMediosPago;
import org.openup.model.MSUMAccountStatus;
import org.openup.model.MStockAdjustment;
import org.openup.model.MStockAdjustmentLine;
import org.openup.model.MStockStatus;
import org.openup.model.X_UY_CashMove;

/**
 * org.openup.process - PFixSumAccountStatus
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 14/05/2013
 * @see
 */
public class PFixSumAccountStatus extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PFixSumAccountStatus() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 14/05/2013
	 * @see
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 14/05/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			// OpenUp. Gabriel Vila. 04/06/2013. Issue #935.
			// Moneda nacional 
			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			
			/*
			
			// PAYMENT
			sql = " select pay.ad_client_id, pay.ad_org_id, pay.updated, pay.updatedby, pay.created, pay.createdby, pay.c_payment_id, pay.c_bpartner_id, " +
				  " pay.isreceipt, pay.c_doctype_id, pay.documentno, line.c_bankaccount_id, line.uy_mediospago_id, coalesce(mp.checkno,'') as checkno, line.duedate, " +
				  " pay.c_currency_id, line.payamt, pay.datetrx, coalesce(mp.estado,'') as status " +
				  " from c_payment pay inner join uy_linepayment line on pay.c_payment_id = line.c_payment_id " +
				  " inner join c_bankaccount ba on line.c_bankaccount_id = ba.c_bankaccount_id " +
				  " left outer join uy_mediospago mp on line.uy_mediospago_id = mp.uy_mediospago_id " +
				  " where pay.docstatus='CO' ";


			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){

				MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(rs.getInt("c_bankaccount_id"));
				sumba.setDateTrx(rs.getTimestamp("datetrx"));
				sumba.setC_DocType_ID(rs.getInt("c_doctype_id"));
				sumba.setDocumentNo(rs.getString("documentno"));
				sumba.setAD_Table_ID(I_C_Payment.Table_ID);
				sumba.setRecord_ID(rs.getInt("c_payment_id"));
				sumba.setDueDate(rs.getTimestamp("duedate"));
				
				if (rs.getInt("uy_mediospago_id") > 0) {
					MMediosPago mp = new MMediosPago(getCtx(), rs.getInt("uy_mediospago_id"), null);
					if (mp.get_ID() > 0){
						sumba.setCheckNo(mp.getCheckNo());
						sumba.setDueDate(mp.getDueDate());
						sumba.setStatus(mp.getestado());
					}
				}
					
				sumba.setC_Currency_ID(rs.getInt("c_currency_id"));
				sumba.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				sumba.setamtdocument(rs.getBigDecimal("payamt"));
				boolean isreceipt = (rs.getString("isreceipt").equalsIgnoreCase("Y")) ? true : false;
				if (isreceipt){
					sumba.setAmtSourceDr(rs.getBigDecimal("payamt"));
					sumba.setAmtSourceCr(Env.ZERO);
					sumba.setAmtAcctCr(Env.ZERO);
				}
				else{
					sumba.setAmtSourceCr(rs.getBigDecimal("payamt"));
					sumba.setAmtSourceDr(Env.ZERO);
					sumba.setAmtAcctDr(Env.ZERO);
				}
				
				BigDecimal currencyRate = Env.ONE;
				MBankAccount ba = new MBankAccount(getCtx(), rs.getInt("c_bankaccount_id"), null);
				if (ba.getC_Currency_ID() != sumba.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(sumba.getC_Currency_ID(), ba.getC_Currency_ID(), sumba.getDateTrx(), 0, rs.getInt("ad_client_id"), 0);
					if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + sumba.getDateTrx());
				}
				
				sumba.setCurrencyRate(currencyRate);
				if (currencyRate.compareTo(Env.ONE) > 0){
					sumba.setAmtAcctDr(sumba.getAmtSourceDr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
					sumba.setAmtAcctCr(sumba.getAmtSourceCr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
				}
				else{
					sumba.setAmtAcctDr(sumba.getAmtAcctDr());
					sumba.setAmtAcctCr(sumba.getAmtAcctCr());
				}
				
				sumba.saveEx();
			}
			
			*/
			
			/*
			
			// DEPOSITO DE CHEQUES DE TERCERO
			sql = " select a.ad_client_id, a.ad_org_id, a.created, a.createdby, a.updated, a.updatedby, a.isactive, a.c_bankaccount_id, " +
					" a.datetrx, a.c_doctype_id, a.documentno, 1000040, a.uy_movbancarioshdr_id, mp.c_bpartner_id, a.description, " +
					" mp.checkno, mp.duedate, mp.estado, ba.c_currency_id, mp.payamt, line.uy_mediospago_id " +
					" from uy_movbancarioshdr a " +
					" inner join c_bankaccount ba on a.c_bankaccount_id = ba.c_bankaccount_id " +
					" inner join uy_movbancariosline line on a.uy_movbancarioshdr_id = line.uy_movbancarioshdr_id " +
					" inner join uy_mediospago mp on line.uy_mediospago_id = mp.uy_mediospago_id " +
					" where a.docstatus='CO' " +
					" and a.c_doctype_id = 1000164";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){

				MMediosPago mpLine = new MMediosPago(getCtx(), rs.getInt("uy_mediospago_id"), null);
				
				// Elimino de esta tabla la entrada por el recibo a este cheque.
				String action = " delete from uy_sum_accountstatus " +
						" where uy_mediospago_id =" + mpLine.get_ID();
				int cant = DB.executeUpdateEx(action, get_TrxName());
				
				// Si no encontro el cheque por id me aseguro de buscarlo por numero de cheque
				if (cant <= 0){
					action = " delete from uy_sum_accountstatus " +
							 " where checkno ='" + mpLine.getCheckNo() + "' ";
					DB.executeUpdateEx(action, get_TrxName());					
				}
				
				MBankAccount ba = new MBankAccount(getCtx(),rs.getInt("c_bankaccount_id"), null);
				MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(rs.getInt("c_bankaccount_id"));
				sumba.setDateTrx(rs.getTimestamp("datetrx"));
				sumba.setC_DocType_ID(rs.getInt("c_doctype_id"));
				sumba.setDocumentNo(rs.getString("documentno"));
				sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
				sumba.setRecord_ID(rs.getInt("uy_movbancarioshdr_id"));
				sumba.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				sumba.setDescription(rs.getString("description"));						
				sumba.setCheckNo(rs.getString("checkno"));
				sumba.setDueDate(rs.getTimestamp("duedate"));
				sumba.setStatus(rs.getString("estado"));
				sumba.setC_Currency_ID(ba.getC_Currency_ID());
				sumba.setC_BPartner_ID(mpLine.getC_BPartner_ID());
				sumba.setamtdocument(mpLine.getPayAmt());
				sumba.setAmtSourceCr(mpLine.getPayAmt());
				sumba.setAmtSourceDr(Env.ZERO);
				sumba.setAmtAcctDr(Env.ZERO);
				sumba.setUY_MediosPago_ID(mpLine.get_ID());
				BigDecimal currencyRate = Env.ONE;
				
				if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), rs.getTimestamp("datetrx"), 0, this.getAD_Client_ID(), 0);
					if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + rs.getTimestamp("datetrx"));
				}
				
				sumba.setCurrencyRate(currencyRate);
				if (currencyRate.compareTo(Env.ONE) > 0){
					sumba.setAmtAcctDr(sumba.getAmtSourceDr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
					sumba.setAmtAcctCr(sumba.getAmtSourceCr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
				}
				else{
					sumba.setAmtAcctDr(sumba.getAmtAcctDr());
					sumba.setAmtAcctCr(sumba.getAmtAcctCr());
				}
				
				sumba.saveEx();
			
			}

			*/
			
			/*
			
			// GASTOS BANCARIOS
			sql = " select a.ad_client_id, a.ad_org_id, a.created, a.createdby, a.updated, a.updatedby, a.isactive, a.c_bankaccount_id, " +
					" a.datetrx, a.c_doctype_id, a.documentno, 1000040, a.uy_movbancarioshdr_id, a.c_bpartner_id, a.description, ba.c_currency_id, a.uy_totalme " +
					" from uy_movbancarioshdr a " +
					" inner join c_bankaccount ba on a.c_bankaccount_id = ba.c_bankaccount_id " +
					" where a.docstatus='CO' " +
					" and a.c_doctype_id = 1000167"; 

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){
				
				MBankAccount ba = new MBankAccount(getCtx(),rs.getInt("c_bankaccount_id"), null);
				MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(rs.getInt("c_bankaccount_id"));
				sumba.setDateTrx(rs.getTimestamp("datetrx"));
				sumba.setC_DocType_ID(rs.getInt("c_doctype_id"));
				sumba.setDocumentNo(rs.getString("documentno"));
				sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
				sumba.setRecord_ID(rs.getInt("uy_movbancarioshdr_id"));
				sumba.setDueDate(rs.getTimestamp("datetrx"));
				sumba.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				sumba.setDescription(rs.getString("description"));						
				sumba.setC_Currency_ID(ba.getC_Currency_ID());
				sumba.setamtdocument(rs.getBigDecimal("uy_totalme"));
				sumba.setAmtSourceDr(rs.getBigDecimal("uy_totalme"));
				sumba.setAmtSourceCr(Env.ZERO);
				sumba.setAmtAcctCr(Env.ZERO);
				BigDecimal currencyRate = Env.ONE;
				
				if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), sumba.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					if (currencyRate == null) 
						throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + sumba.getDateTrx());
				}
				
				sumba.setCurrencyRate(currencyRate);
				if (currencyRate.compareTo(Env.ONE) > 0){
					sumba.setAmtAcctDr(sumba.getAmtSourceDr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
					sumba.setAmtAcctCr(sumba.getAmtSourceCr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
				}
				else{
					sumba.setAmtAcctDr(sumba.getAmtAcctDr());
					sumba.setAmtAcctCr(sumba.getAmtAcctCr());
				}
				
				sumba.saveEx();
			}

			*/

			/*
			// DEPOSITO EN EFECTIVO
			sql = " select a.ad_client_id, a.ad_org_id, a.created, a.createdby, a.updated, a.updatedby, a.isactive, a.c_bankaccount_id, " +
					" a.datetrx, a.c_doctype_id, a.documentno, 1000040, a.uy_movbancarioshdr_id, a.c_bpartner_id, a.description, ba.c_currency_id, a.uy_totalme " +
					" from uy_movbancarioshdr a " +
					" inner join c_bankaccount ba on a.c_bankaccount_id = ba.c_bankaccount_id " +
					" where a.docstatus='CO' " +
					" and a.c_doctype_id = 1000154"; 

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){
				
				MBankAccount ba = new MBankAccount(getCtx(),rs.getInt("c_bankaccount_id"), null);
				MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(rs.getInt("c_bankaccount_id"));
				sumba.setDateTrx(rs.getTimestamp("datetrx"));
				sumba.setC_DocType_ID(rs.getInt("c_doctype_id"));
				sumba.setDocumentNo(rs.getString("documentno"));
				sumba.setAD_Table_ID(I_UY_MovBancariosHdr.Table_ID);
				sumba.setRecord_ID(rs.getInt("uy_movbancarioshdr_id"));
				sumba.setDueDate(rs.getTimestamp("datetrx"));
				sumba.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				sumba.setDescription(rs.getString("description"));						
				sumba.setC_Currency_ID(ba.getC_Currency_ID());
				sumba.setamtdocument(rs.getBigDecimal("uy_totalme"));
				sumba.setAmtSourceCr(rs.getBigDecimal("uy_totalme"));
				sumba.setAmtSourceDr(Env.ZERO);
				sumba.setAmtAcctDr(Env.ZERO);
				BigDecimal currencyRate = Env.ONE;
				if (ba.getC_Currency_ID() != schema.getC_Currency_ID()){
					currencyRate = MConversionRate.getRate(ba.getC_Currency_ID(), schema.getC_Currency_ID(), sumba.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + sumba.getDateTrx());
				}
				sumba.setCurrencyRate(currencyRate);
				if (currencyRate.compareTo(Env.ONE) > 0){
					sumba.setAmtAcctDr(sumba.getAmtSourceDr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
					sumba.setAmtAcctCr(sumba.getAmtSourceCr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
				}
				else{
					sumba.setAmtAcctDr(sumba.getAmtAcctDr());
					sumba.setAmtAcctCr(sumba.getAmtAcctCr());
				}
				
				sumba.saveEx();
			}
			
			*/
			
			
			// MOVIMIENTOS DE CAJA
			sql = " select a.ad_client_id, a.ad_org_id, a.created, a.createdby, a.updated, a.updatedby, a.isactive, a.c_bankaccount_id, " +
					" a.datetrx, a.c_doctype_id, a.documentno, 1000359, a.uy_cashmove_id, a.description, ba.c_currency_id, a.amount, a.cashmovetype " +
					" from uy_cashmove a " +
					" inner join c_bankaccount ba on a.c_bankaccount_id = ba.c_bankaccount_id " +
					" where a.docstatus='CO'";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){

				MBankAccount ba = new MBankAccount(getCtx(),rs.getInt("c_bankaccount_id"), null);
				MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(rs.getInt("c_bankaccount_id"));
				sumba.setDateTrx(rs.getTimestamp("datetrx"));
				sumba.setC_DocType_ID(rs.getInt("c_doctype_id"));
				sumba.setDocumentNo(rs.getString("documentno"));
				sumba.setAD_Table_ID(I_UY_CashMove.Table_ID);
				sumba.setRecord_ID(rs.getInt("uy_cashmove_id"));
				sumba.setDueDate(rs.getTimestamp("datetrx"));
				sumba.setC_Currency_ID(ba.getC_Currency_ID());
				sumba.setamtdocument(rs.getBigDecimal("amount"));
				sumba.setDescription(rs.getString("description"));
				if (rs.getString("cashmovetype").equalsIgnoreCase(X_UY_CashMove.CASHMOVETYPE_Salida)){
					sumba.setAmtSourceDr(rs.getBigDecimal("amount"));
					sumba.setAmtSourceCr(Env.ZERO);
					sumba.setAmtAcctCr(Env.ZERO);
				}
				else{
					sumba.setAmtSourceCr(rs.getBigDecimal("amount"));
					sumba.setAmtSourceDr(Env.ZERO);
					sumba.setAmtAcctDr(Env.ZERO);
				}
				sumba.saveEx();
			}
			
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		

		return "ok";

	}

}
