/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 14/11/2012
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MResguardo;
import org.openup.model.MResguardoLine;
import org.openup.model.MRetention;

/**
 * org.compiere.acct - Doc_UYResguardo
 * OpenUp Ltda. Issue #100 
 * Description: Contabilizacion de resguardos.
 * @author Gabriel Vila - 14/11/2012
 * @see
 */
public class Doc_UYResguardo extends Doc {

	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYResguardo(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(ass, clazz, rs, defaultDocumentType, trxName);
	}

	
	/**
	 * Constructor
	 * @param ass
	 *            accounting schemata
	 * @param rs
	 *            record
	 * @param trxName
	 *            trx
	 */
	public Doc_UYResguardo(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MResguardo.class, rs, null, trxName);
	}

	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 14/11/2012
	 * @see
	 * @return
	 */
	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MResguardo) getPO()).getDateAcct());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 14/11/2012
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 14/11/2012
	 * @see
	 * @param as
	 * @return
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		// Creacion de Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		
		// Si el tipo de documento provision
		if (getDocumentType().equals(DOCTYPE_Ctb_Resguardo))
		{
			MResguardo header = (MResguardo)getPO();
			List<MResguardoLine> lines = header.getLines();
			
			FactLine fl = null;
			
			// DR - Lineas - Cuenta transitoria de retencion
			// CR - Lineas - Cuenta de retencion
			for (MResguardoLine line: lines){
	
				MRetention retention = (MRetention)line.getUY_Retention();

				// DR
				MAccount acctRetDr = MAccount.get(getCtx(), retention.getR_Transit_Acct());
				fl = fact.createLine(null, acctRetDr, as.getC_Currency_ID(), line.getAmt(), null,
						header.getC_DocType_ID(), header.getDocumentNo());
				if (fl != null && retention.getAD_Org_ID() != 0) fl.setAD_Org_ID(retention.getAD_Org_ID());
				
				// CR 
				MAccount acctRet = MAccount.get(getCtx(), retention.getR_Retention_Acct());
				fl = fact.createLine(null, acctRet, as.getC_Currency_ID(), null, line.getAmt(),
						header.getC_DocType_ID(), header.getDocumentNo());
				if (fl != null && retention.getAD_Org_ID() != 0) fl.setAD_Org_ID(retention.getAD_Org_ID());				
				
			}
			
			// Redondeo.
			fact.createRounding(header.getC_DocType_ID(), header.getDocumentNo(), 0, 0, 0, 0);
		}
		else
		{
			p_Error = "DocumentType unknown: " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			fact = null;
		}

		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}

	/**
	 * Obtengo validcombinationID para una linea de recibo.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila. - 17/02/2012
	 * @see
	 * @param keyID
	 * @param currencyID
	 * @param isReceipt
	 * @param acctType
	 * @param acctSchema
	 * @return
	 */
	@SuppressWarnings("unused")
	private int getReciboValidCombination(int keyID, int currencyID, boolean isReceipt, 
					 				      int acctType, MAcctSchema acctSchema){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = -1;		
		boolean aplicaFiltroMoneda = false;
		
		try{

			// Cuentas en comun para pagos y cobranzas
			if (acctType==Doc.ACCTTYPE_BankInTransit) {
				sql = "SELECT B_InTransit_Acct FROM C_BankAccount_Acct WHERE C_BankAccount_ID=? AND C_AcctSchema_ID=?";
			}

			if (acctType==Doc.ACCTTYPE_BankAsset){
				sql = "SELECT B_Asset_Acct FROM C_BankAccount_Acct WHERE C_BankAccount_ID=? AND C_AcctSchema_ID=?";
			}

			if (acctType==Doc.ACCTTYPE_BankEmited){
				sql = "SELECT B_Emited_Acct FROM C_BankAccount_Acct WHERE C_BankAccount_ID=? AND C_AcctSchema_ID=?";
			}
		
			if (acctType==Doc.ACCTTYPE_IVA_COMPRAS){
				sql = "SELECT t_credit_acct FROM C_Tax_Acct WHERE C_Tax_ID=? AND C_AcctSchema_ID=?";
			}

			if (acctType==Doc.ACCTTYPE_IVA_VENTAS){
				sql = "SELECT t_due_acct FROM C_Tax_Acct WHERE C_Tax_ID=? AND C_AcctSchema_ID=?";
			}
			
			// Cuentas particulares de un recibo de cobranza
			if (isReceipt){
				// Si la moneda del recibo es moneda nacional
				if (currencyID==acctSchema.getC_Currency_ID()){
					if (acctType==Doc.ACCTTYPE_C_Receivable){
						sql = "SELECT C_Receivable_Acct FROM C_BP_Customer_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
					}
					if (acctType==Doc.ACCTTYPE_DiscountRev){
						sql = "SELECT PayDiscount_Rev_Acct FROM C_BP_Group_Acct a, C_BPartner bp "
							+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=?";
					}
				}
				else{
					// Moneda extranjera
					if (acctType==Doc.ACCTTYPE_C_Receivable){
						sql = "SELECT C_Receivable_Acct FROM UY_ClientesME_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						aplicaFiltroMoneda = true;
					}
					if (acctType==Doc.ACCTTYPE_DiscountRev){
						sql = "SELECT PayDiscount_Rev_Acct FROM UY_BP_Group_Acct a, C_BPartner bp "
							+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=? AND C_Currency_ID=?";
						aplicaFiltroMoneda = true;
					}
				}
			}
			else{
				// PAGO
				// Si la moneda del recibo es moneda nacional
				if (currencyID==acctSchema.getC_Currency_ID()){
					if (acctType==Doc.ACCTTYPE_V_Liability){
						sql = "SELECT V_Liability_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
					}
					if (acctType==Doc.ACCTTYPE_DiscountExp){
						sql = "SELECT PayDiscount_Exp_Acct FROM C_BP_Group_Acct a, C_BPartner bp "
							+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=?";
					}
				}
				else{
					// Moneda extranjera
					if (acctType==Doc.ACCTTYPE_V_Liability){
						sql = "SELECT V_Liability_Acct FROM UY_ProveedoresME_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						aplicaFiltroMoneda = true;
					}
					if (acctType==Doc.ACCTTYPE_DiscountExp){
						sql = "SELECT PayDiscount_Exp_Acct FROM UY_BP_Group_Acct a, C_BPartner bp "
							+ "WHERE a.C_BP_Group_ID=bp.C_BP_Group_ID AND bp.C_BPartner_ID=? AND a.C_AcctSchema_ID=? AND C_Currency_ID=?";
						aplicaFiltroMoneda = true;
					}
				}
			}
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, keyID);
			pstmt.setInt(2, acctSchema.getC_AcctSchema_ID());
			if (aplicaFiltroMoneda) pstmt.setInt(3, currencyID);
			
			rs = pstmt.executeQuery ();

			if (rs.next()) value = rs.getInt(1);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}

}
