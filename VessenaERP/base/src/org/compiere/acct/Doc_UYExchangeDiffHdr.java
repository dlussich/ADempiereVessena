/**
 * Doc_UYExchangeDiffHdr.java
 * 12/04/2011
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MElementValue;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MExchangeDiffHdr;
import org.openup.model.MExchangeDiffLine;

/**
 * OpenUp.
 * Doc_UYExchangeDiffHdr
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 12/04/2011
 */
public class Doc_UYExchangeDiffHdr extends Doc {

	/**
	 * Constructor
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYExchangeDiffHdr(MAcctSchema[] ass, Class<?> clazz,
			ResultSet rs, String defaultDocumentType, String trxName) {

		super(ass, clazz, rs, defaultDocumentType, trxName);
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
	public Doc_UYExchangeDiffHdr(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MExchangeDiffHdr.class, rs, null, trxName);
	}

	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		// Creacion de Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		
		// Si el documento es Cheque
		if (getDocumentType().equals(DOCTYPE_Ctb_ExchangeDiff))
		{
			MExchangeDiffHdr header = (MExchangeDiffHdr)getPO();
			MExchangeDiffLine[] lines = header.getLines();

			// Si no tengo lineas para procesar, salgo sin hacer nada
			if (lines.length <= 0) return null;

			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			
			
			// Para facturas de vale flete en modulo de transporte hay una contabilizacion especial
			MDocType doc = new MDocType(getCtx(), header.getC_DocType_ID(), null);
			if (doc.getValue() != null){
				if (doc.getValue().equalsIgnoreCase("difcambiovta")){
					return this.crearAsientosDCVenta(as, header);
				}
				else if (doc.getValue().equalsIgnoreCase("difcambiocpra")){
					return this.crearAsientosDCCompra(as, header);
				}
			}
			
			// Para cada cuenta-moneda a procesar
			for (int i=0; i<lines.length; i++){

				// Modelo de la cuenta
				MElementValue elementValue = new MElementValue(getCtx(), lines[i].getC_ElementValue_ID(), null);
				
				// Cuenta de la linea
				int cValidCombinationID = elementValue.getValidCombinationID();
				MAccount acctCuentaLinea = MAccount.get(getCtx(), cValidCombinationID);

				// Cuenta para diferencia de cambio ganada y perdida
				MAccount acctDifGan = null, acctDifPerd = null;
				try {
					
					if (elementValue.get_ValueAsInt("C_EV_ID_DC_Win") > 0){
						acctDifGan = MAccount.get(getCtx(), new MElementValue(getCtx(), elementValue.get_ValueAsInt("C_EV_ID_DC_Win"), null).getValidCombinationID());
					}
					else{
						acctDifGan = MAccount.get(getCtx(), MElementValue.getByValue(getCtx(), elementValue.getuy_ctadifgan().trim(), null).getValidCombinationID());	
					}
					
					if (elementValue.get_ValueAsInt("C_EV_ID_DC_Lost") > 0){
						acctDifPerd = MAccount.get(getCtx(), new MElementValue(getCtx(), elementValue.get_ValueAsInt("C_EV_ID_DC_Lost"), null).getValidCombinationID());
					}
					else{
						acctDifPerd = MAccount.get(getCtx(), MElementValue.getByValue(getCtx(), elementValue.getuy_ctadifper().trim(), null).getValidCombinationID());	
					}
					
					
				} catch (Exception e) {
					log.log(Level.SEVERE, e.getMessage());
					return null;
				} 

				FactLine fl = null;
				
				// Si la diferencia a guardar es debito
				if (lines[i].getamtdiffdr().compareTo(Env.ZERO) > 0){
					
					// DR  - Cuenta de la linea
					fl = fact.createLine(null, acctCuentaLinea, schema.getC_Currency_ID(), lines[i].getamtdiffdr(), null,
							header.getC_DocType_ID(), header.getDocumentNo());
					if (fl != null && this.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.getAD_Org_ID());

					if (fl != null){
						if (header.getC_Activity_ID() > 0){
							fl.setC_Activity_ID(header.getC_Activity_ID());
							fl.setC_Activity_ID_1(header.getC_Activity_ID());
						}
					}			

					
					// CR - Cuenta diferencia de cambio ganada
					fl = fact.createLine(null, acctDifGan, schema.getC_Currency_ID(), null, lines[i].getamtdiffdr(),
							header.getC_DocType_ID(), header.getDocumentNo());
					if (fl != null && this.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.getAD_Org_ID());

					if (fl != null){
						if (header.getC_Activity_ID() > 0){
							fl.setC_Activity_ID(header.getC_Activity_ID());
							fl.setC_Activity_ID_1(header.getC_Activity_ID());
						}
					}			

				}

				// Si la diferencia a guardar es credito
				else if (lines[i].getamtdiffcr().compareTo(Env.ZERO) > 0){
					
					// DR  - Cuenta diferencia de cambio perdida
					fl = fact.createLine(null, acctDifPerd, schema.getC_Currency_ID(), lines[i].getamtdiffcr(), null,
							header.getC_DocType_ID(), header.getDocumentNo());
					if (fl != null && this.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.getAD_Org_ID());

					if (fl != null){
						if (header.getC_Activity_ID() > 0){
							fl.setC_Activity_ID(header.getC_Activity_ID());
							fl.setC_Activity_ID_1(header.getC_Activity_ID());
						}
					}			

					// CR - Cuenta de la linea
					fl = fact.createLine(null, acctCuentaLinea, schema.getC_Currency_ID(), null, lines[i].getamtdiffcr(),
							header.getC_DocType_ID(), header.getDocumentNo());
					if (fl != null && this.getAD_Org_ID() != 0) fl.setAD_Org_ID(this.getAD_Org_ID());

					if (fl != null){
						if (header.getC_Activity_ID() > 0){
							fl.setC_Activity_ID(header.getC_Activity_ID());
							fl.setC_Activity_ID_1(header.getC_Activity_ID());
						}
					}			

				}
				
			}
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

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MExchangeDiffHdr) getPO()).getDateAcct());
		return null;
	}

	
	private ArrayList<Fact> crearAsientosDCVenta(MAcctSchema as, MExchangeDiffHdr model){
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		// Obtengo suma de diferencia y suma de iva
		String sql = " select coalesce(sum(amtdc),0) as monto from uy_exchangediffline where uy_exchangediffhdr_id =" + model.get_ID() +
				" and coalesce(amtiva,0) != 0 ";
		BigDecimal sumDiff = DB.getSQLValueBDEx(null, sql);

		sql = " select coalesce(sum(amtiva),0) as monto from uy_exchangediffline where uy_exchangediffhdr_id =" + model.get_ID();
		BigDecimal sumIVA = DB.getSQLValueBDEx(null, sql);
		
		// IVA
		MAccount acctIVA = new MAccount(getCtx(), 1007683, null);
		
		// Diferencia de cambio
		MAccount acctDC = new MAccount(getCtx(), 1007897, null);

		// Ventas
		MAccount acctVentas = new MAccount(getCtx(), 1007682, null);
		
		
		// DR - Al debito va IVA y Diferencia de Cambio
		fact.createLine (null, acctDC, as.getC_Currency_ID(), sumDiff, null, model.getC_DocType_ID(), model.getDocumentNo());
		fact.createLine (null, acctIVA, as.getC_Currency_ID(), sumIVA, null, model.getC_DocType_ID(), model.getDocumentNo());
		
		// CR - Al credito va Ventas
		fact.createLine (null, acctVentas, as.getC_Currency_ID(), null, sumDiff.add(sumIVA), model.getC_DocType_ID(), model.getDocumentNo());
		
		// Redondeo.
		fact.createRounding(model.getC_DocType_ID(), model.getDocumentNo(), 0, 0, 0, 0);

		facts.add(fact);
		return facts;
		
	}

	private ArrayList<Fact> crearAsientosDCCompra(MAcctSchema as, MExchangeDiffHdr model){
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		// Obtengo suma de diferencia y suma de iva
		String sql = " select coalesce(sum(amtdc),0) as monto from uy_exchangediffline where uy_exchangediffhdr_id =" + model.get_ID() +
				" and coalesce(amtiva,0) != 0 ";
		BigDecimal sumDiff = DB.getSQLValueBDEx(null, sql);

		sql = " select coalesce(sum(amtiva),0) as monto from uy_exchangediffline where uy_exchangediffhdr_id =" + model.get_ID();
		BigDecimal sumIVA = DB.getSQLValueBDEx(null, sql);
		
		// IVA
		MAccount acctIVA = new MAccount(getCtx(), 1007575, null);
		
		// Diferencia de cambio
		MAccount acctDC = new MAccount(getCtx(), 1007901, null);

		// Compras
		MAccount acctCompras = new MAccount(getCtx(), 1007622, null);
		
		
		// DR - Al debito va IVA y Diferencia de Cambio
		fact.createLine (null, acctDC, as.getC_Currency_ID(), sumDiff, null, model.getC_DocType_ID(), model.getDocumentNo());
		fact.createLine (null, acctIVA, as.getC_Currency_ID(), sumIVA, null, model.getC_DocType_ID(), model.getDocumentNo());
		
		// CR - Al credito va Compras
		fact.createLine (null, acctCompras, as.getC_Currency_ID(), null, sumDiff.add(sumIVA), model.getC_DocType_ID(), model.getDocumentNo());
		
		// Redondeo.
		fact.createRounding(model.getC_DocType_ID(), model.getDocumentNo(), 0, 0, 0, 0);

		facts.add(fact);
		return facts;
		
	}

}
