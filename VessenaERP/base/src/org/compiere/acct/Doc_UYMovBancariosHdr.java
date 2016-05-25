package org.compiere.acct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.MCharge;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MPayment;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;
import org.openup.model.MMediosPago;
import org.openup.model.MMovBancariosHdr;
import org.openup.model.MMovBancariosLine;

public class Doc_UYMovBancariosHdr extends Doc {

	private MMovBancariosHdr header = null;
	private String trxName = "";

	// Le Paso la variable del tipo de documento
	//private static final int idDocCambioRechazadoChequepropio = 1000058;
	private static final String depositado = "DEP";

	public Doc_UYMovBancariosHdr(MAcctSchema[] ass, Class<?> clazz, ResultSet rs, String defaultDocumentType, String trxName) {
		super(ass, clazz, rs, defaultDocumentType, trxName);
		// TODO Auto-generated constructor stub

		this.trxName = trxName;
	}

	/**
	 * Constructor
	 * 
	 * @param ass
	 *            accounting schemata
	 * @param rs
	 *            record
	 * @param trxName
	 *            trx
	 */
	public Doc_UYMovBancariosHdr(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MMovBancariosHdr.class, rs, null, trxName);
		this.trxName = trxName;
	}

	/**
	 * 
	 * OpenUp. issue #895	
	 * Descripcion : 
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 18/10/2011
	 */	
	@Override
	public boolean isConvertible(MAcctSchema acctSchema) {
		if(this.header.isInitialLoad()) return true;
		else return super.isConvertible(acctSchema);
	}

	/**
	 * 
	 * OpenUp. issue #895	
	 * Descripcion : 
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 18/10/2011
	 */	
	@Override
	public boolean isPeriodOpen() {
		if(this.header.isInitialLoad()) return true;
		else return super.isPeriodOpen();
	}

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		// Creacion de Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		// Bank Account Org
		int AD_Org_ID = getBank_Org_ID();
		//OpenUp Nicolas Sarlabos issue #895 18/10/2011
		// Verifico si no es una carga inicial de saldo bancario
		if (header.isInitialLoad()){
			return new ArrayList<Fact>();
		}
		//fin OpenUp Nicolas Sarlabos issue #895 18/10/2011
		
		
		// Si el documento es Transferencias bancarias
		if (getDocumentType().equals(DOCTYPE_Banco_Transferencias)) {

			//OpenUp. Nicolas Sarlabos. 16/02/2016. #5394.
			MDocType doc = (MDocType)this.header.getC_DocType();
			
			if (doc.getValue()!=null && doc.getValue().equalsIgnoreCase("transfdir")){//si es transferencia directa
				
				BigDecimal montoHeader = this.header.getuy_totalme();
				BigDecimal tasaCambio = (BigDecimal) header.get_Value("DivideRate");
				int idMonedaNacional = this.getIDMonedaNacional(header.getAD_Client_ID());
				boolean tasaCambioManual = false;
				
				int monedaHeader = this.header.getC_Currency_ID();

				// Si la moneda destino no es moneda nacional y la moneda origen es
				// nacional
				// Tengo que contabilizar en pesos segun la tasa de cambio ingresada
				// manualmente
				// por el usuario en esta ventana.
				if ((this.header.getC_Currency_ID() != idMonedaNacional) && (this.header.getUY_C_Currency_From_ID() == idMonedaNacional)) {
					montoHeader = this.header.getuy_total_manual();
					monedaHeader = idMonedaNacional;
					tasaCambio = (BigDecimal) header.get_Value("DivideRate");
					tasaCambioManual = true;
				} else if ((this.header.getC_Currency_ID() == idMonedaNacional) && (this.header.getUY_C_Currency_From_ID() != idMonedaNacional)) {
					this.setIsMultiCurrency(true);
				}

				// Header (Debito)
				this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
				MAccount acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
				FactLine fl = fact.createLine(null, acct, monedaHeader, montoHeader, null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null){
					if(AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
					if (monedaHeader == idMonedaNacional){
						fl.setDivideRate(Env.ONE);
					}
					
					// Si aplico tasa de cambio manual
					if (tasaCambioManual) {
						fl.setDivideRate(tasaCambio);
						fl.setAmtAcctDr(this.header.getuy_total_manual());
						if(tasaCambio.compareTo(Env.ZERO)>0) fl.setUY_AmtNativeDr(fl.getAmtSourceDr().divide(tasaCambio, 3, RoundingMode.HALF_UP)); //OpenUp. Nicolas Sarlabos. 04/09/2013. #1100
					}

				}
				
				//Credito
				this.setC_BankAccount_ID(this.header.get_ValueAsInt("UY_C_BankAccount_From_ID"));
				acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
				fl = fact.createLine(null, acct, monedaHeader, null, montoHeader,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null){
					if(AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
					if (monedaHeader == idMonedaNacional){
						fl.setDivideRate(Env.ONE);
					}
					
					// Si aplico tasa de cambio manual
					if (tasaCambioManual) {
						fl.setDivideRate(tasaCambio);
						fl.setAmtAcctCr(this.header.getuy_total_manual());
						if(tasaCambio.compareTo(Env.ZERO)>0) fl.setUY_AmtNativeCr(fl.getAmtSourceCr().divide(tasaCambio, 3, RoundingMode.HALF_UP)); //OpenUp. Nicolas Sarlabos. 04/09/2013. #1100
					}

				}				
				
			} else {
				
				BigDecimal montoHeader = this.header.getuy_totalme();
				BigDecimal tasaCambio = (BigDecimal) header.get_Value("DivideRate");
				int idMonedaNacional = this.getIDMonedaNacional(header.getAD_Client_ID());
				boolean tasaCambioManual = false;
				boolean tasaDesdeLineasOrigen = false;

				int monedaHeader = this.header.getC_Currency_ID();

				// Si la moneda destino no es moneda nacional y la moneda origen es
				// nacional
				// Tengo que contabilizar en pesos segun la tasa de cambio ingresada
				// manualmente
				// por el usuario en esta ventana.
				if ((this.header.getC_Currency_ID() != idMonedaNacional) && (this.header.getUY_C_Currency_From_ID() == idMonedaNacional)) {
					montoHeader = this.header.getuy_totalmn();
					monedaHeader = idMonedaNacional;
					tasaCambio = (BigDecimal) header.get_Value("DivideRate");
					tasaCambioManual = true;
				} else if ((this.header.getC_Currency_ID() == idMonedaNacional) && (this.header.getUY_C_Currency_From_ID() != idMonedaNacional)) {
					tasaDesdeLineasOrigen = true;
					this.setIsMultiCurrency(true);
				}

				// Header (Debito)
				this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
				MAccount acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
				FactLine fl = fact.createLine(null, acct, monedaHeader, montoHeader, null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null){
					if(AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
					if (monedaHeader == idMonedaNacional){
						fl.setDivideRate(Env.ONE);
					}
					
					// Si aplico tasa de cambio manual
					if (tasaCambioManual) {
						fl.setDivideRate(tasaCambio);
						fl.setAmtAcctDr(this.header.getuy_totalmn());
						if(tasaCambio.compareTo(Env.ZERO)>0) fl.setUY_AmtNativeDr(fl.getAmtSourceDr().divide(tasaCambio, 3, RoundingMode.HALF_UP)); //OpenUp. Nicolas Sarlabos. 04/09/2013. #1100
					}

				}

				// Lines (Credito)
				this.setC_BankAccount_ID(this.header.getUY_C_BankAccount_From_ID());
				ArrayList<MMovBancariosLine> lines = this.header.getLines();
				for (int i = 0; i < lines.size(); i++) {
					MMovBancariosLine line = lines.get(i);

					BigDecimal montoLinea = line.getuy_totalamt(), tasaCambioMedioPago = Env.ONE;
					int idMonedaLinea = line.getC_Currency_ID();

					// Si tengo que aplicar tasa de cambio manual cargado al generar
					// los medios de pago
					if (tasaDesdeLineasOrigen) {
						MMediosPago mpago = new MMediosPago(getCtx(), line.getUY_MediosPago_ID(), trxName);
						if (mpago.get_Value("DivideRate") != null) {
							//montoLinea = montoHeader;// montoLinea.multiply((BigDecimal)
							// mpago.get_Value("DivideRate"));//OpenUp M.R. 25-07-2011 Issue#810 comento y modifico linea para que tome los valores desde el cabezal.
							//idMonedaLinea = idMonedaNacional;
							tasaCambioMedioPago = (BigDecimal)mpago.get_Value("DivideRate");
						}
					}

					fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_PaymentSelect, as), idMonedaLinea, null, montoLinea,
							this.header.getC_DocType_ID(), this.header.getDocumentNo());
					if (fl != null){
						if(AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);

						// Si aplico tasa de cambio manual
						if (tasaDesdeLineasOrigen){ 
							fl.setDivideRate(tasaCambioMedioPago);
							fl.setAmtAcctCr(fl.getAmtSourceCr().multiply(fl.getDivideRate()));
							if(tasaCambioMedioPago.compareTo(Env.ZERO)>0) fl.setUY_AmtNativeCr(fl.getAmtSourceCr().divide(tasaCambioMedioPago, 3, RoundingMode.HALF_UP)); //OpenUp. Nicolas Sarlabos. 04/09/2013. #1100
						}
					}
				}				
			}			
			//Fin OpenUp. #5394.
		} else if (getDocumentType().equals(DOCTYPE_Banco_DepositoChequeTerceros)) {

			// Header (Debito)
			this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
			MAccount acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), header.getuy_totalme(), null,
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Lines (Credito)
			this.setC_BankAccount_ID(this.header.getUY_C_BankAccount_From_ID());
			ArrayList<MMovBancariosLine> lines = this.header.getLines();
			for (int i = 0; i < lines.size(); i++) {
				MMovBancariosLine line = lines.get(i);
				fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankAsset, as), getC_Currency_ID(), null, line.getuy_totalamt(),
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
			}
			
		} else if (getDocumentType().equals(DOCTYPE_Banco_DescuentoChequesTerceros)) {

			// Header (Debito) - SubTotal
			this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
			MAccount acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), header.getuy_totalme(), null,
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Header (Debito) - Intereses
			MAccount intereses = getAccount(Doc.ACCTTYPE_InterestExp, as);
			fl = fact.createLine(null, intereses, getC_Currency_ID(), header.getuy_intereses(), null,
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Lines (Credito)
			this.setC_BankAccount_ID(this.header.getUY_C_BankAccount_From_ID());
			ArrayList<MMovBancariosLine> lines = this.header.getLines();
			for (int i = 0; i < lines.size(); i++) {
				MMovBancariosLine line = lines.get(i);
				fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankAsset, as), getC_Currency_ID(), null, line.getuy_totalamt(),
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
			}
		} else if (getDocumentType().equals(DOCTYPE_Banco_DepositoEfectivo)) {

			// Header (Debito)
			this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
			MAccount acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), header.getuy_totalme(), null,
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Lines (Credito)
			ArrayList<MMovBancariosLine> lines = this.header.getLines();
			for (int i = 0; i < lines.size(); i++) {
				MMovBancariosLine line = lines.get(i);
				this.setC_BankAccount_ID(line.getC_BankAccount_ID());
				fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankAsset, as), getC_Currency_ID(), null, line.getuy_totalamt(),
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
			}
		} else if (getDocumentType().equals(DOCTYPE_Banco_InteresesBancarios)) {
			// Pueden ser perdidos o ganados
			if (this.header.get_ValueAsBoolean("isganado")) {

				// Header (Debito)
				this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
				MAccount acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
				FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), header.getuy_totalme(), null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

				// Add the header total to the balance
				// balance=balance.add( header.getuy_totalme());

				// Lines (Credito)
				ArrayList<MMovBancariosLine> lines = this.header.getLines();
				for (int i = 0; i < lines.size(); i++) {
					MMovBancariosLine line = lines.get(i);
					this.setC_BankAccount_ID(line.getC_BankAccount_ID());
					MAccount lineAcct = null;
					if (line.getC_Charge_ID() != 0) lineAcct = MCharge.getAccount(line.getC_Charge_ID(), as, line.getuy_totalamt());
					else {
						p_Error = "Linea del documento no tiene Cargo asociado."; // TODO:
						// translate
						log.log(Level.SEVERE, p_Error);
						fact = null;
						return null;
					}
					fl = fact.createLine(null, lineAcct, getC_Currency_ID(), null, line.getuy_totalamt(),
							this.header.getC_DocType_ID(), this.header.getDocumentNo());
					if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
				}

				/*
				 * The balance should be 0 if (balance.equals(BigDecimal.ZERO))
				 * { p_Error =
				 * "Los cargos del documento no balancean exactamente."; //
				 * TODO: Translate log.log(Level.SEVERE, p_Error); fact = null;
				 * return null; }
				 */

			} else if (!this.header.get_ValueAsBoolean("isganado")) {

				// Header (Credito)
				this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
				MAccount acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
				FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), null, header.getuy_totalme(),
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

				// Lines (Debito)
				ArrayList<MMovBancariosLine> lines = this.header.getLines();
				for (int i = 0; i < lines.size(); i++) {
					MMovBancariosLine line = lines.get(i);
					this.setC_BankAccount_ID(line.getC_BankAccount_ID());
					MAccount lineAcct = null;
					if (line.getC_Charge_ID() != 0) lineAcct = MCharge.getAccount(line.getC_Charge_ID(), as, line.getuy_totalamt());
					else {
						p_Error = "Linea del documento no tiene Cargo asociado.";
						log.log(Level.SEVERE, p_Error);
						fact = null;
						return null;
					}
					fl = fact.createLine(null, lineAcct, getC_Currency_ID(), line.getuy_totalamt(), null,
							this.header.getC_DocType_ID(), this.header.getDocumentNo());
					if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
				}
			} else {
				p_Error = "Para Contabilizar los Intereses, debe indicar si son Ganados o Perdidos.";
				log.log(Level.SEVERE, p_Error);
				fact = null;
			}
		} else if (getDocumentType().equals(DOCTYPE_Banco_GastosBancarios)) {

			// Header (Credito)
			this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
			MAccount acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), null, header.getuy_totalme(),
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Lines (Debito)
			ArrayList<MMovBancariosLine> lines = this.header.getLines();
			for (int i = 0; i < lines.size(); i++) {
				MMovBancariosLine line = lines.get(i);
				this.setC_BankAccount_ID(line.getC_BankAccount_ID());
				MAccount lineAcct = null;
				if (line.getC_Charge_ID() != 0) lineAcct = MCharge.getAccount(line.getC_Charge_ID(), as, line.getuy_totalamt());
				else {
					p_Error = "Linea del documento no tiene Cargo asociado.";
					log.log(Level.SEVERE, p_Error);
					fact = null;
					return null;
				}
				fl = fact.createLine(null, lineAcct, getC_Currency_ID(), line.getuy_totalamt(), null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
			}
		} else if (getDocumentType().equals(DOCTYPE_Banco_ConciliacionChequesPropios)) {

			// Header (Credito)
			this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
			MAccount acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), null, header.getuy_totalme(),
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Lines (Debito)
			ArrayList<MMovBancariosLine> lines = this.header.getLines();
			for (int i = 0; i < lines.size(); i++) {
				MMovBancariosLine line = lines.get(i);
				this.setC_BankAccount_ID(line.getC_BankAccount_ID());
				fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankEmited, as), line.getC_Currency_ID(), line.getuy_totalamt(), null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
			}
		}
		else if (getDocumentType().equals(DOCTYPE_Banco_CambioChequePropio)) {

			// Header (Debito)
			this.setC_BankAccount_ID(this.getC_BankAccount_ID());
			MAccount acct = getAccount(Doc.ACCTTYPE_BankEmited, as);
			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), header.getuy_totalme(), null,
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

				// Lines (Debito)
				ArrayList<MMovBancariosLine> lines = this.header.getLines();
				for (int i = 0; i < lines.size(); i++) {
					MMovBancariosLine line = lines.get(i);
					this.setC_BankAccount_ID(line.getC_BankAccount_ID());
					MBankAccount baline = (MBankAccount)line.getC_BankAccount();
					if (line.get_Value("TenderType").toString().equalsIgnoreCase(MPayment.TENDERTYPE_Cash)) {
						fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankAsset, as), baline.getC_Currency_ID(), null, line.getuy_totalamt(),
								this.header.getC_DocType_ID(), this.header.getDocumentNo());
					} else {
						
						MBank bank = (MBank)baline.getC_Bank();
						if (bank.isBankHandler()){
							if (MSysConfig.getBooleanValue("UY_CHECK_EMITTED_ACCT_BANK", true, getAD_Client_ID())){
								fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankAsset, as), baline.getC_Currency_ID(), null, line.getuy_totalamt(),
										this.header.getC_DocType_ID(), this.header.getDocumentNo());
							}
							else{
								fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_PaymentSelect, as), baline.getC_Currency_ID(), null, line.getuy_totalamt(),
										this.header.getC_DocType_ID(), this.header.getDocumentNo());
							}
						}
						else{
							fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_PaymentSelect, as), baline.getC_Currency_ID(), null, line.getuy_totalamt(),
									this.header.getC_DocType_ID(), this.header.getDocumentNo());
						}
					}
					if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
				//Fin OpenUp
			}
		} else if (getDocumentType().equals(DOCTYPE_Banco_CambioChequeTercero)) {// !!

			// Header (Credito)
			this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
			MAccount acct = getAccount(Doc.ACCTTYPE_BankRevaluation, as);
			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), null, header.getuy_totalme(),
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Lines (Debito)
			ArrayList<MMovBancariosLine> lines = this.header.getLines();
			for (int i = 0; i < lines.size(); i++) {
				MMovBancariosLine line = lines.get(i);
				this.setC_BankAccount_ID(line.getC_BankAccount_ID());
				FactLine fldr = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankAsset, as), line.getC_Currency_ID(), line.getuy_totalamt(), null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fldr != null && AD_Org_ID != 0)
					fldr.setAD_Org_ID(AD_Org_ID);
					//Fin OpenUp
			}
		}
		else if (getDocumentType().equals(DOCTYPE_Banco_RechazoChequesPropios)) {

			// Header (Debito/Credito)
			this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
			MAccount acct = this.getAccount(Doc.ACCTTYPE_BankAsset, as);

			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), header.getuy_total_manual(), null,
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			
			MAccount acctrec = null;
			if (MSysConfig.getBooleanValue("UY_CHECK_EMITTED_ACCT_BANK", true, getAD_Client_ID())){
				acctrec = this.getAccount(Doc.ACCTTYPE_PaymentSelect, as);	
			}
			else{
				acctrec = this.getAccount(Doc.ACCTTYPE_BankEmited, as);
			}
			
			fl = fact.createLine(null, acctrec, getC_Currency_ID(), null, header.getuy_total_manual(),
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);
			
		}
		else if (getDocumentType().equals(DOCTYPE_Banco_RechazoChequesTerceros)) {
		
			if (header.getestado().equalsIgnoreCase(depositado)) {
				MMediosPago cheque = new MMediosPago(getCtx(), this.header.getUY_MediosPago_ID(), null);
				MMovBancariosLine line = null;

				try {
					line = MMovBancariosLine.getFromMedioPago(cheque.getUY_MediosPago_ID(), null);
				} catch (Exception e) {
					p_Error = "No se pudo obtener cuenta bancaria destino : " + e.getMessage();
					log.log(Level.SEVERE, p_Error);
					return null;
				}

				if (line == null) {
					p_Error = "No se pudo obtener cuenta bancaria destino para este medio de pago.";
					log.log(Level.SEVERE, p_Error);
					return null;
				}

				// this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
				MMovBancariosHdr cabezal = new MMovBancariosHdr(getCtx(), line.getUY_MovBancariosHdr_ID(), null);
				this.setC_BankAccount_ID(cabezal.getC_BankAccount_ID());
				// Header (Debito/Credito)
				MAccount acct = getAccount(Doc.ACCTTYPE_BankRevaluation, as);
				FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), header.getuy_total_manual(), null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
				fl = fact.createLine(null, acct, getC_Currency_ID(), null, header.getuy_total_manual(),
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);
				
			} else if (header.getestado().equalsIgnoreCase("DES")) {

				// Header (Debito/Credito)
				MMediosPago cheque = new MMediosPago(getCtx(), this.header.getUY_MediosPago_ID(), null);
				MMovBancariosLine line = null;

				try {
					line = MMovBancariosLine.getFromMedioPago(cheque.getUY_MediosPago_ID(), null);
				} catch (Exception e) {
					p_Error = "No se pudo obtener cuenta bancaria destino : " + e.getMessage();
					log.log(Level.SEVERE, p_Error);
					return null;
				}

				if (line == null) {
					p_Error = "No se pudo obtener cuenta bancaria destino para este medio de pago.";
					log.log(Level.SEVERE, p_Error);
					return null;
				}

				// this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
				MMovBancariosHdr cabezal = new MMovBancariosHdr(getCtx(), line.getUY_MovBancariosHdr_ID(), null);
				this.setC_BankAccount_ID(cabezal.getC_BankAccount_ID());
				MAccount acct = getAccount(Doc.ACCTTYPE_BankRevaluation, as);
				FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), header.getuy_total_manual(), null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
				fl = fact.createLine(null, acct, getC_Currency_ID(), null, header.getuy_total_manual(),
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);
			} else if (header.getestado().equalsIgnoreCase("CAR")) {

				// Header (Debito/Credito)
				this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
				MAccount acct = getAccount(Doc.ACCTTYPE_BankRevaluation, as);
				FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), header.getuy_total_manual(), null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				acct = getAccount(Doc.ACCTTYPE_BankEmited, as); // OpenUp M.R.
				// 20-05-2011 issue #656 Cambio Cuenta Contable
				fl = fact.createLine(null, acct, getC_Currency_ID(), null, header.getuy_total_manual(),
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);
			}
		
		} else if (getDocumentType().equals(DOCTYPE_Banco_ValesBancarios)) {
			// OpenUp 26-04-2011 M.R. Issue #567 Se comenta linea para que la
			// comprobacion sea por tipo de documento y no por ID
			/* if (header.getC_DocType_ID() == idDocValesBancarios) */{

				// Header (Debito/Credito)
				this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
				MAccount acctcr = getAccount(Doc.ACCTTYPE_BankAsset, as);
				FactLine fl = fact.createLine(null, acctcr, getC_Currency_ID(), header.getuy_total_manual(), null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				MAccount intereses = getAccount(Doc.ACCTTYPE_InterestExp, as);
				fl = fact.createLine(null, intereses, getC_Currency_ID(), header.getuy_intereses(), null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);
				this.setC_BankAccount_ID(header.getC_BankAccount_ID());// OpenUp
				// M.R. 05-08-2011 Issue#810 agrego linea para poder pasar el
				// parametro de la cuenta bancaria
				MAccount acctdr = getAccount(Doc.ACCTTYPE_ValesBancarios, as);
				fl = fact.createLine(null, acctdr, getC_Currency_ID(), null, header.getUY_SubTotal(),
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);
			}

		}
		else if (getDocumentType().equals(DOCTYPE_Banco_PagoValeBancario)) {

			// Header (Debito) - SubTotal
			this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
			MAccount acct = getAccount(Doc.ACCTTYPE_ValesBancarios, as);
			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), header.getUY_SubTotal(), null,
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Header (Debito) - Intereses
			MAccount intereses = getAccount(Doc.ACCTTYPE_InterestRev, as);
			fl = fact.createLine(null, intereses, getC_Currency_ID(), header.getuy_intereses(), null,
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Lines (Credito)
			MAccount banco = getAccount(Doc.ACCTTYPE_BankAsset, as);
			fl = fact.createLine(null, banco, getC_Currency_ID(), null, header.getuy_total_manual(),
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);
		}
		else if (getDocumentType().equals(DOCTYPE_Banco_CobroConformes)) {

			// Header (Debito) - SubTotal
			this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
			MAccount acct = getAccount(Doc.ACCTTYPE_CobroConformes, as);
			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), null, header.getuy_total_manual(),
					this.header.getC_DocType_ID(), this.header.getDocumentNo()); 
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Lines (Credito)
			ArrayList<MMovBancariosLine> lines = this.header.getLines();
			for (int i = 0; i < lines.size(); i++) {
				MMovBancariosLine line = lines.get(i);
				this.setC_BankAccount_ID(line.getC_BankAccount_ID());
				fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_BankAsset, as), line.getC_Currency_ID(), line.getuy_totalamt(), null,
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
			}
		}
		//OpenUp. Nicolas Sarlabos. 27/01/2016. #5382.
		else if (getDocumentType().equals(DOCTYPE_Banco_DepositoChequePropio)) {
			
			// Header (Debito)
			this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());
			MAccount acct = getAccount(Doc.ACCTTYPE_BankAsset, as);
			FactLine fl = fact.createLine(null, acct, getC_Currency_ID(), header.getuy_totalme(), null,
					this.header.getC_DocType_ID(), this.header.getDocumentNo());
			if (fl != null && AD_Org_ID != 0 && getC_Charge_ID() == 0) fl.setAD_Org_ID(AD_Org_ID);

			// Lines (Credito)			
			ArrayList<MMovBancariosLine> lines = this.header.getLines();
			for (int i = 0; i < lines.size(); i++) {
				MMovBancariosLine line = lines.get(i);
				this.setC_BankAccount_ID(line.getC_BankAccount_ID());
				fl = fact.createLine(null, getAccount(Doc.ACCTTYPE_PaymentSelect, as), getC_Currency_ID(), null, line.getuy_totalamt(),
						this.header.getC_DocType_ID(), this.header.getDocumentNo());
				if (fl != null && AD_Org_ID != 0) fl.setAD_Org_ID(AD_Org_ID);
			}			
			
		}
		//Fin #5382.
		else {
			p_Error = "DocumentType unknown: " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			fact = null;
		}

		this.setC_BankAccount_ID(this.header.getC_BankAccount_ID());

		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}

	@Override
	public BigDecimal getBalance() {
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}

	@Override
	protected String loadDocumentDetails() {
		// Obtengo informacion del modelo
		this.header = (MMovBancariosHdr) getPO();
		setDateDoc(this.header.getDateTrx());
		setAmount(Doc.AMTTYPE_Gross, header.getuy_totalme());
		return null;
	}

	/**
	 * Get AD_Org_ID from Bank Account
	 * 
	 * @return AD_Org_ID or 0
	 */
	private int getBank_Org_ID() {
		if (this.header.getC_BankAccount_ID() == 0) return 0;
		//
		MBankAccount ba = MBankAccount.get(getCtx(), this.header.getC_BankAccount_ID());
		return ba.getAD_Org_ID();
	} // getBank_Org_ID

	/* Obtiene id de moneda nacional para la empresa actual */
	private Integer getIDMonedaNacional(Integer idEmpresa) {
		
		MClient client = new MClient(getCtx(), idEmpresa, null);
		MAcctSchema schema = client.getAcctSchema();
		return schema.getC_Currency_ID();
	}

}
