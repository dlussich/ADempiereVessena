/**
 * 
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MAllocation;

/**
 * @author Hp
 *
 */
public class Doc_UYAllocation extends Doc {

	private MAllocation allocation = null;
	
	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYAllocation(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(ass, clazz, rs, defaultDocumentType, trxName);
	}

	/**
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trxName trx
	 */
	public Doc_UYAllocation (MAcctSchema[] ass, ResultSet rs, String trxName)
	{
		super (ass, MAllocation.class, rs, null, trxName);
	}   

	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		this.allocation = (MAllocation) getPO();
		setDateDoc(this.allocation.getDateAcct());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		Fact fact = new Fact(this, as, Fact.POST_Actual);
		
		// Si la afectacion no esta en estado completado, no hago nada
		if (!this.allocation.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed))
			return new ArrayList<Fact>();
			
		// Calculo totales por moneda de recibos y de facturas en las dos monedas de esta afectacion
		BigDecimal totalPaymentsMoneda1 = this.allocation.getPaymentsTotalAllocated(this.allocation.getC_Currency_ID());
		BigDecimal totalPaymentsMoneda2 = this.allocation.getPaymentsTotalAllocated(this.allocation.getC_Currency2_ID());	
		BigDecimal totalInvoicesMoneda1 = this.allocation.getInvoicesTotalAllocated(this.allocation.getC_Currency_ID());
		BigDecimal totalInvoicesMoneda2 = this.allocation.getInvoicesTotalAllocated(this.allocation.getC_Currency2_ID());
		//OpenUp. Nicolas Sarlabos. 16/08/2013. #1204. Obtengo totales de recibos afectados con anticipo para cada moneda		
		BigDecimal prepaymentMoneda1 = this.allocation.getPrepaymentsAllocated(this.allocation.getC_Currency_ID());
		BigDecimal prepaymentMoneda2 = this.allocation.getPrepaymentsAllocated(this.allocation.getC_Currency2_ID());
		//Fin OpenUp.
		//OpenUp. Nicolas Sarlabos. 23/09/2013. #1204.
		// Si la afectacion es para una sola moneda y esa moneda es la moneda nacional, no hago asientos
		if ( (this.allocation.getC_Currency2_ID() <= 0) && (this.allocation.getC_Currency_ID() == as.getC_Currency_ID())){
			//si no tengo importes de anticipos
			if(prepaymentMoneda1.compareTo(Env.ZERO)==0) return new ArrayList<Fact>();			
		}		
		//Fin OpenUp.
		// Si el documento = afectacion de cobro (deudores)
		if (getDocumentType().equals(DOCTYPE_UY_Allocation_Invoice)) {
			
			// Proceso moneda de afectacion 1			
			int idValidCombination = this.getValidCombination(this.allocation.getC_BPartner_ID(), 
										 this.allocation.getC_Currency_ID(), true, Doc.ACCTTYPE_C_Receivable, as); 

			MAccount account = MAccount.get(as.getCtx(), idValidCombination);
			
			// Credito - CR (Facturas)
			fact.createLine(null, account, this.allocation.getC_Currency_ID(), null, totalInvoicesMoneda1,
					this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());
			
			// Debito - DR (Recibos) 
			fact.createLine(null, account, this.allocation.getC_Currency_ID(), totalPaymentsMoneda1, null,
					this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());

			// Proceso moneda de afectacion 2
			if (this.allocation.getC_Currency2_ID() > 0){
				
				this.setIsMultiCurrency(true);
				
				idValidCombination = this.getValidCombination(this.allocation.getC_BPartner_ID(), 
				 							this.allocation.getC_Currency2_ID(), true, Doc.ACCTTYPE_C_Receivable, as); 

				account = MAccount.get(as.getCtx(), idValidCombination);

				// Credito - CR (Facturas)
				if (totalInvoicesMoneda2.compareTo(Env.ZERO) > 0){
					FactLine fl = fact.createLine(null, account, this.allocation.getC_Currency2_ID(), null, totalInvoicesMoneda2,
							this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());
					fl.setDivideRate(this.allocation.getDivideRate());
					fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
					fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
				}
				
				// Debito - DR (Recibos) 
				if (totalPaymentsMoneda2.compareTo(Env.ZERO) > 0){
					FactLine fl = fact.createLine(null, account, this.allocation.getC_Currency2_ID(), totalPaymentsMoneda2, null,
							this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());
					fl.setDivideRate(this.allocation.getDivideRate());
					fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
					fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
				}
					
			}
			
		}
		// Si el documento = afectacion de pago (proveedores)
		else if (getDocumentType().equals(DOCTYPE_UY_Allocation_Payment)) {

			// Proceso moneda de afectacion 1				
			int idValidCombination = this.getValidCombination(this.allocation.getC_BPartner_ID(), 
										 this.allocation.getC_Currency_ID(), false, Doc.ACCTTYPE_V_Liability, as); 

			MAccount account = MAccount.get(as.getCtx(), idValidCombination);
			
			// Debito - DR (Facturas)
			fact.createLine(null, account, this.allocation.getC_Currency_ID(), totalInvoicesMoneda1, null,
					this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());
			
			// Credito - CR (Recibos)  - Dos cuentas, con y sin anticipo. 
			// Tengo recibos de Anticipos en moneda 1
			if (prepaymentMoneda1.compareTo(Env.ZERO) > 0){

				// Asiento Credito con Cuenta sin anticipo
				fact.createLine(null, account, this.allocation.getC_Currency_ID(), null, totalPaymentsMoneda1.subtract(prepaymentMoneda1),
								this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());

				// Obtengo cuenta de anticipo
				int idCombAnticipo = this.getValidCombination(this.allocation.getC_BPartner_ID(), 
						 			 this.allocation.getC_Currency_ID(), false, Doc.ACCTTYPE_V_Prepayment, as); 
				MAccount acctAnticipo = MAccount.get(as.getCtx(), idCombAnticipo);

				// Asiento Credito con Cuenta de anticipo
				fact.createLine(null, acctAnticipo, this.allocation.getC_Currency_ID(), null, prepaymentMoneda1,
								this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());
			}
			else{
				// No tengo recibos de anticipo, va todo a la cuenta del proveedor
				fact.createLine(null, account, this.allocation.getC_Currency_ID(), null, totalPaymentsMoneda1,
						this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());
			}

			// Proceso moneda de afectacion 2
			if (this.allocation.getC_Currency2_ID() > 0){
				
				this.setIsMultiCurrency(true);
				
				idValidCombination = this.getValidCombination(this.allocation.getC_BPartner_ID(), 
						 					this.allocation.getC_Currency2_ID(), false, Doc.ACCTTYPE_V_Liability, as); 

				account = MAccount.get(as.getCtx(), idValidCombination);
				
				// Debito - DR (Facturas)
				if (totalInvoicesMoneda2.compareTo(Env.ZERO) > 0){
					FactLine fl = fact.createLine(null, account, this.allocation.getC_Currency2_ID(), totalInvoicesMoneda2, null,
							this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());
					fl.setDivideRate(this.allocation.getDivideRate());
					fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()).setScale(2, RoundingMode.HALF_UP));
					fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()).setScale(2, RoundingMode.HALF_UP));

				}					
				
				// Credito - CR (Recibos) 
				if (totalPaymentsMoneda2.compareTo(Env.ZERO) > 0){
					
					// Tengo recibos de Anticipos en moneda 2
					if (prepaymentMoneda2.compareTo(Env.ZERO) > 0){

						// A la cuenta de proveedores en moneda 2 va la diferencia entre total y monto de anticipo
						FactLine fl = fact.createLine(null, account, this.allocation.getC_Currency2_ID(), null, totalPaymentsMoneda2.subtract(prepaymentMoneda2),
								this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());
						fl.setDivideRate(this.allocation.getDivideRate());
						fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
						fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
						
						// Obtengo cuenta de anticipo
						int idCombAnticipo = this.getValidCombination(this.allocation.getC_BPartner_ID(), 
								 			 this.allocation.getC_Currency2_ID(), false, Doc.ACCTTYPE_V_Prepayment, as); 
						MAccount acctAnticipo = MAccount.get(as.getCtx(), idCombAnticipo);

						// A la cuenta de proveedores en moneda 2 va la diferencia entre total y monto de anticipo
						FactLine fl2 = fact.createLine(null, acctAnticipo, this.allocation.getC_Currency2_ID(), null, prepaymentMoneda2,
								this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());
						fl2.setDivideRate(this.allocation.getDivideRate());
						fl2.setAmtAcctDr(fl2.getAmtSourceDr().multiply(fl2.getDivideRate()));
						fl2.setAmtAcctCr(fl2.getAmtSourceCr().multiply(fl2.getDivideRate()));

					}
					else{
						// No tengo recibos de anticipo en moneda 2, va todo a la cuenta de proveedores en moneda 2
						FactLine fl = fact.createLine(null, account, this.allocation.getC_Currency2_ID(), null, totalPaymentsMoneda2,
								this.allocation.getC_DocType_ID(), this.allocation.getDocumentNo());
						fl.setDivideRate(this.allocation.getDivideRate());
						fl.setAmtAcctDr(fl.getAmtSourceDr().multiply(fl.getDivideRate()));
						fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
					}
				}				
			}
			
		}
		else {
			p_Error = "Documento desconocido en contabilizacion de afectacion : " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			fact = null;
		}

		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}

	
	/**
	 * OpenUp. Gabriel Vila. 25/10/2011.	
	 * Descripcion : Obtine id de combinacion de cuenta para creacion de asientos contables.
	 * @param keyID
	 * @param currencyID
	 * @param isReceipt
	 * @param acctType
	 * @param acctSchema
	 * @return
	 */
	private int getValidCombination(int keyID, int currencyID, boolean isSOTrx, int acctType, MAcctSchema acctSchema){

		String sql = "", sql2 = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = -1;		
		boolean aplicaFiltroMoneda = false;
		
		try{

			// Obtengo grupo del socio de negocio
			MBPartner bp = new MBPartner(getCtx(), keyID, null);
			MBPGroup grupoBP = (MBPGroup)bp.getC_BP_Group();
			
			// Si es Duedores
			if (isSOTrx){
				// Si la moneda del recibo es moneda nacional
				if (currencyID==acctSchema.getC_Currency_ID()){
					if (acctType==Doc.ACCTTYPE_C_Receivable){
						sql = "SELECT C_Receivable_Acct FROM C_BP_Customer_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT C_Receivable_Acct FROM C_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";	
						}
						
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
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT C_Receivable_Acct FROM UY_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						}
						
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
				// Proveedores
				// Si la moneda del recibo es moneda nacional
				if (currencyID==acctSchema.getC_Currency_ID()){
					if (acctType==Doc.ACCTTYPE_V_Liability){
						sql = "SELECT V_Liability_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT V_Liability_Acct FROM C_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";
						}
						
					}
					if (acctType==Doc.ACCTTYPE_V_Prepayment){
						sql = "SELECT V_Prepayment_Acct FROM C_BP_Vendor_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT V_Prepayment_Acct FROM C_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=?";
						}
						
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

						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT V_Liability_Acct FROM UY_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						}
						aplicaFiltroMoneda = true;
					}
					if (acctType==Doc.ACCTTYPE_V_Prepayment){
						
						sql = "SELECT V_Prepayment_Acct FROM UY_ProveedoresME_Acct WHERE C_BPartner_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						
						if ((grupoBP != null) && (grupoBP.get_ID() > 0)){
							sql2 = "SELECT V_Prepayment_Acct FROM UY_BP_Group_Acct WHERE C_BP_Group_ID=? AND C_AcctSchema_ID=? AND C_Currency_ID=?";
						}
						
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
			
			// Si no obtuve desde cuentas del socio de negocio, busco cuentas en grupo del socio de negocio
			if ((value <= 0) && !sql2.equalsIgnoreCase("")) {

				DB.close(rs, pstmt);
				rs = null; pstmt = null;

				pstmt = DB.prepareStatement (sql2, null);
				pstmt.setInt(1, grupoBP.get_ID());
				pstmt.setInt(2, acctSchema.getC_AcctSchema_ID());
				if (aplicaFiltroMoneda) pstmt.setInt(3, currencyID);
				
				rs = pstmt.executeQuery ();

				if (rs.next()) value = rs.getInt(1);

			}
			

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
