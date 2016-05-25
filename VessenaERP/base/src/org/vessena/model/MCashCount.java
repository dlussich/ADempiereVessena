/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.util.OpenUpUtils;

/**
 * @author Nicolas
 *
 */
public class MCashCount extends X_UY_CashCount implements DocAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6215202010404291859L;

	private String processMsg = null;
	private boolean justPrepared = false;
	
	int loadTicketID = 0;
	int auditTicketID = 0;
	
	/**
	 * @param ctx
	 * @param UY_CashCount_ID
	 * @param trxName
	 */
	public MCashCount(Properties ctx, int UY_CashCount_ID, String trxName) {
		super(ctx, UY_CashCount_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashCount(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
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
		
		this.loadData();		

		this.setDocStatus(DocumentEngine.STATUS_Applied);
		this.setDocAction(DocAction.ACTION_Complete);

		return true;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
	
		if(this.getDateTrx().compareTo(this.getDateAction())<0) throw new AdempiereException("La fecha de arqueo no puede ser menor a la fecha de venta/remesa");
		
		if(newRecord || is_ValueChanged("DateAction")){
			
			Timestamp date = TimeUtil.trunc(this.getDateAction(), TimeUtil.TRUNC_DAY);
			
			MCashCount count = MCashCount.forDateRemit(getCtx(), date, get_TrxName());
			
			if(count!=null && count.get_ID()>0) throw new AdempiereException("Ya existe el arqueo nº " + count.getDocumentNo() + " para la fecha de remesa indicada");		
			
		}
		
		this.setDateAcct(this.getDateAction());
		
		//seteo tasa de cambio U$
		if(newRecord || is_ValueChanged("DateAction")){
			
			if(this.getCurrencyRate().compareTo(Env.ZERO)==0){
				
				MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
				int idCurrencyAcct = client.getAcctSchema().getC_Currency_ID(); 
				
				BigDecimal tc = MConversionRate.getRate(100, idCurrencyAcct, this.getDateAction(), 0, client.get_ID(), 0);
				if (tc == null){
					tc = Env.ONE;
				}
				else if (tc == Env.ZERO){
					tc = Env.ONE;
				}
				
				this.setCurrencyRate(tc);					
			}
		
		}
		
		return true;
	}

	/***
	 * Metodo que setea los campos de totales en el cabezal del arqueo. 
	 * OpenUp Ltda. Issue #4437
	 * @author Nicolas Sarlabos - 09/09/2015
	 */ 
	private void setTotalRemesaHdr() {

		String sql = "";
		BigDecimal amount = Env.ZERO;
		BigDecimal amtInitial = Env.ZERO, amtFinal = Env.ZERO;
		MBankAccount account = null;

		try{
			
			MCashConfig config = MCashConfig.forClient(getCtx(), get_TrxName());
			
			if(config==null) throw new AdempiereException("No se pudo obtener parametros de caja");
			
			MCashConfigChest configChest = MCashConfigChest.forCurrency(getCtx(), 142, get_TrxName());
			
			if(configChest==null) throw new AdempiereException("No se pudo obtener Cofre en $ desde parametros de caja");			

			//SALDOS INICIALES
			//seteo saldo inicial de cajas

			Timestamp date = TimeUtil.addDays(this.getDateAction(), -1);

			sql = "select coalesce(sum(amount),0)" +
					" from uy_cashcashier" +
					" where docstatus = 'CO' and c_currency_id = 142 and date_trunc ('day', datetrx) = '" + date + "'";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);
			
			this.setAmtCajas(amount);
			amtInitial = amtInitial.add(amount);	

			//seteo saldo inicial de fondo fijo
			if(config.getC_BankAccount_ID_2()>0){

				account = new MBankAccount(getCtx(),config.getC_BankAccount_ID_2(),get_TrxName());

				sql = "select coalesce((sum(amtsourcecr)-sum(amtsourcedr)),0)" +
						" from uy_sum_accountstatus" + 
						" where c_bankaccount_id = " + account.get_ID() +
						" and datetrx < '" + this.getDateAction() + "'";

				amount = DB.getSQLValueBDEx(get_TrxName(), sql);
				
				this.setAmtFondoFijo(amount);
				amtInitial = amtInitial.add(amount);

			}
			
			//seteo saldo inicial de cofre
			account = (MBankAccount)configChest.getC_BankAccount();

			sql = "select coalesce((sum(amtsourcecr)-sum(amtsourcedr)),0)" +
					" from uy_sum_accountstatus" + 
					" where c_bankaccount_id = " + account.get_ID() +
					" and datetrx < '" + this.getDateAction() + "'";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setAmtCofre(amount);
			amtInitial = amtInitial.add(amount);

			//seteo TOTAL INICIAL
			this.setAmtInitial(amtInitial);

			//seteo total de tickets
			sql = "select coalesce(sum(r.amount2),0)" +
					" from uy_cashcountpayrule r" +
					" inner join uy_paymentrule pr on r.uy_paymentrule_id = pr.uy_paymentrule_id" +
					" where r.uy_cashcount_id = " + this.get_ID() +
					" and pr.value in ('sodexo','luncheon')" +
					" and r.c_currency_id = 142";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setamtvtatktalimentacion(amount);			

			//seteo total de tarjeta de credito $
			sql = "select coalesce(sum(r.amount2),0)" +
					" from uy_cashcountpayrule r" +
					" inner join uy_paymentrule pr on r.uy_paymentrule_id = pr.uy_paymentrule_id" +
					" where r.uy_cashcount_id = " + this.get_ID() +
					" and pr.value = 'tarjeta'" +
					" and r.c_currency_id = 142";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setamtvtatarjeta(amount);

			//seteo total de tarjeta de credito U$
			sql = "select coalesce(sum(r.amount2),0)" +
					" from uy_cashcountpayrule r" +
					" inner join uy_paymentrule pr on r.uy_paymentrule_id = pr.uy_paymentrule_id" +
					" where r.uy_cashcount_id = " + this.get_ID() +
					" and pr.value = 'tarjeta'" +
					" and r.c_currency_id = 100";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setamtvtatarjeta2(amount);

			//seteo total de depositos en Pesos
			sql = "select coalesce(sum(bl.amount),0)" +
					" from uy_cashremitbankline bl" +
					" inner join uy_cashremitbank b on bl.uy_cashremitbank_id = b.uy_cashremitbank_id" +
					" inner join uy_cashremittance r on b.uy_cashremittance_id = r.uy_cashremittance_id" +
					" inner join c_bankaccount ba on b.c_bankaccount_id = ba.c_bankaccount_id" +
					" where r.docstatus = 'CO' and ba.c_currency_id = 142 and date_trunc ('day', r.datetrx) = '" + this.getDateAction() + "'";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setmontopesos(amount);

			//seteo total de depositos en Dolares
			sql = "select coalesce(sum(bl.amount),0)" +
					" from uy_cashremitbankline bl" +
					" inner join uy_cashremitbank b on bl.uy_cashremitbank_id = b.uy_cashremitbank_id" +
					" inner join uy_cashremittance r on b.uy_cashremittance_id = r.uy_cashremittance_id" +
					" inner join c_bankaccount ba on b.c_bankaccount_id = ba.c_bankaccount_id" +
					" where r.docstatus = 'CO' and ba.c_currency_id = 100 and date_trunc ('day', r.datetrx) = '" + this.getDateAction() + "'";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setmontodolares(amount);

			//seteo total de depositos de cheques en Pesos
			MDocType doc = MDocType.forValue(getCtx(), "cashremittcheck", get_TrxName());

			sql = "select coalesce(sum(l.amount),0)" +
					" from uy_cashremittance r" + 
					" inner join uy_cashremittanceline l on r.uy_cashremittance_id = l.uy_cashremittance_id" +
					" inner join c_doctype d on r.c_doctype_id = d.c_doctype_id" +
					" inner join c_bankaccount ba on l.c_bankaccount_id = ba.c_bankaccount_id" +
					" where r.docstatus = 'CO' and date_trunc ('day', r.datetrx) = '" + this.getDateAction() + "'" +
					" and ba.ispublic = 'Y' and l.c_currency_id = 142 and d.c_doctype_id = " + doc.get_ID();

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setamt1(amount);			

			//seteo total de depositos de cheques en Dolares
			sql = "select coalesce(sum(l.amount),0)" +
					" from uy_cashremittance r" + 
					" inner join uy_cashremittanceline l on r.uy_cashremittance_id = l.uy_cashremittance_id" +
					" inner join c_doctype d on r.c_doctype_id = d.c_doctype_id" +
					" inner join c_bankaccount ba on l.c_bankaccount_id = ba.c_bankaccount_id" +
					" where r.docstatus = 'CO' and date_trunc ('day', r.datetrx) = '" + this.getDateAction() + "'" +
					" and ba.ispublic = 'Y' and l.c_currency_id = 100 and d.c_doctype_id = " + doc.get_ID();

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setamt2(amount);		
			
			//seteo total de cheques remesa $
			sql = "select coalesce(sum(r.amount2),0)" +
					" from uy_cashcountpayrule r" +
					" inner join uy_paymentrule pr on r.uy_paymentrule_id = pr.uy_paymentrule_id" +
					" where r.uy_cashcount_id = " + this.get_ID() +
					" and pr.value in ('chequedia','cheque diferido')" +
					" and r.c_currency_id = 142";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setAmtChequeRem(amount);						
			
			//seteo total de cheques remesa U$
			sql = "select coalesce(sum(r.amount2),0)" +
					" from uy_cashcountpayrule r" +
					" inner join uy_paymentrule pr on r.uy_paymentrule_id = pr.uy_paymentrule_id" +
					" where r.uy_cashcount_id = " + this.get_ID() +
					" and pr.value in ('chequedia','cheque diferido')" +
					" and r.c_currency_id = 100";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setAmtChequeRem2(amount);				
			
			//seteo total de cobros de creditos en Pesos
			sql = "select coalesce(sum(payamt),0)" +
					" from c_payment" +
					" where docstatus = 'CO' and c_currency_id = 142 and date_trunc ('day', datetrx) = '" + this.getDateAction() + "'";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setamtcobrocredito(amount);

			//seteo total de cobros de creditos en Dolares
			sql = "select coalesce(sum(payamt),0)" +
					" from c_payment" +
					" where docstatus = 'CO' and c_currency_id = 100 and date_trunc ('day', datetrx) = '" + this.getDateAction() + "'";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setamtcobrocredito2(amount);

			//SALDOS FINALES
			//seteo saldo final de cajeras
			sql = "select coalesce(sum(amount),0)" +
					" from uy_cashcashier" +
					" where docstatus = 'CO' and c_currency_id = 142 and date_trunc ('day', datetrx) = '" + this.getDateAction() + "'";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);		

			this.setAmtCajas2(amount);
			amtFinal = amtFinal.add(amount);		
			
			//seteo saldo final de fondo fijo
			if(config.getC_BankAccount_ID_2()>0){

				account = new MBankAccount(getCtx(),config.getC_BankAccount_ID_2(),get_TrxName());

				sql = "select coalesce((sum(amtsourcecr)-sum(amtsourcedr)),0)" +
						" from uy_sum_accountstatus" + 
						" where c_bankaccount_id = " + account.get_ID() +
						" and datetrx <= '" + this.getDateAction() + "'";

				amount = DB.getSQLValueBDEx(get_TrxName(), sql);			

				this.setAmtFondoFijo2(amount);
				amtFinal = amtFinal.add(amount);
									
			}
			
			//seteo saldo final de cofre
			account = (MBankAccount)configChest.getC_BankAccount();

			sql = "select coalesce((sum(amtsourcecr)-sum(amtsourcedr)),0)" +
					" from uy_sum_accountstatus" + 
					" where c_bankaccount_id = " + account.get_ID() +
					" and datetrx <= '" + this.getDateAction() + "'";

			amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			this.setAmtCofre2(amount);
			amtFinal = amtFinal.add(amount);						

			//seteo TOTAL FINAL
			this.setAmtFinal(amtFinal);

		}  catch (Exception e){

			throw new AdempiereException(e.getMessage());
		}		

	}
	
	/***
	 * Metodo que setea los campos de totales por tipo de impuesto en el cabezal del arqueo. 
	 * OpenUp Ltda. Issue #5258
	 * @author Nicolas Sarlabos - 12/01/2016
	 */ 
	private void setTotalByTax() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal totalVentas = Env.ZERO;
		
		try{
			
			sql = "select * from vuy_rt_ventasxtipoiva where datetrx = '" + this.getDateAction() + "'";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				String code = rs.getString("commoditycode");
				BigDecimal amount = rs.getBigDecimal("precio");
				BigDecimal ivaAmt = rs.getBigDecimal("iva");
				
				totalVentas = totalVentas.add(amount);
				totalVentas = totalVentas.add(ivaAmt);
				
				if(code.equalsIgnoreCase("1")){//Exento
					
					this.setAmtVentasExento(amount);
					this.setIvaVentasExento(ivaAmt);					
					
				} else if (code.equalsIgnoreCase("2")){//Minimo
					
					this.setAmtVentasMinimo(amount);
					this.setIvaVentasMinimo(ivaAmt);					
					
				} else if (code.equalsIgnoreCase("3")){//Basico
					
					this.setAmtVentasBasico(amount);
					this.setIvaVentasBasico(ivaAmt);					
					
				} else if (code.equalsIgnoreCase("4")){//IVA Percibido Carniceria
					
					this.setAmtVentasPercibido(amount);
					this.setIvaVentasPercibido(ivaAmt);					
					
				} else if (code.equalsIgnoreCase("5")){//IVA Vegetales Minimo
					
					this.setAmtVentasVegetales(amount);
					this.setIvaVentasVegetales(ivaAmt);					
					
				} else if (code.equalsIgnoreCase("7")){//IVA Carniceria 22%
					
					this.setAmtVentasCarnes2(amount);
					this.setIvaVentasCarnes2(ivaAmt);					
					
				} else if (code.equalsIgnoreCase("8")){//IVA Carniceria 10%
					
					this.setAmtVentasCarnes(amount);
					this.setIvaVentasCarnes(ivaAmt);					
				}			
				
			}	
			
			//seteo totalizador en el cabezal
			this.setAmtVentasTotal(totalVentas);
			
			//seteo totalizador en Resumen del cabezal
			this.setamt3(totalVentas);
						
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
	}

	/***
	 * Metodo que carga los datos desde las remesas de efectivo y valores. 
	 * OpenUp Ltda. Issue #4437
	 * @author Nicolas Sarlabos - 08/09/2015
	 */ 
	private void loadRemesaLines() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		int payruleID = 0, currID = 0;
		MCashCountPayRule payLine = null;
				
		try {			
			
			MDocType doc = MDocType.forValue(getCtx(), "cashremittcheck", get_TrxName());
						
			sql = "select distinct r.uy_paymentrule_id,l.c_currency_id" + 
                  " from uy_cashremittance r" + 
                  " inner join uy_cashremittanceline l on r.uy_cashremittance_id = l.uy_cashremittance_id" +
                  " where r.docstatus = 'CO' and date_trunc ('day', r.datetrx) = '" + this.getDateAction() + "'" +
                  " and r.c_doctype_id <> " + doc.get_ID() +
                  " order by r.uy_paymentrule_id, l.c_currency_id";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				int payrule_ID = rs.getInt("UY_PaymentRule_ID");
				int curr_ID = rs.getInt("C_Currency_ID");
				
				//corte de control por medio de pago y moneda
				if(payruleID != payrule_ID || currID != curr_ID) {
								
					payruleID = payrule_ID;
					currID = curr_ID;
					
					//creo cabezal de medio de pago
					payLine = new MCashCountPayRule(getCtx(), 0, get_TrxName());
					payLine.setUY_CashCount_ID(this.get_ID());
					payLine.setUY_PaymentRule_ID(payrule_ID);
					payLine.setC_Currency_ID(curr_ID);
					payLine.saveEx();
					
					this.loadCashLines(payLine,"r.uy_paymentrule_id");				
															
				}				
				
			}			
			
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

	}
	
	/***
	 * Metodo que carga los datos desde las remesas de cheques. 
	 * OpenUp Ltda. Issue #4437
	 * @author Nicolas Sarlabos - 15/12/2015
	 */ 
	private void loadRemesaChequeLines() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		int payruleID = 0, currID = 0;
		MCashCountPayRule payLine = null;
				
		try {
			
			MDocType doc = MDocType.forValue(getCtx(), "cashremittcheck", get_TrxName());
						
			sql = "select distinct l.uy_paymentrule_id,l.c_currency_id" + 
                  " from uy_cashremittance r" + 
                  " inner join uy_cashremittanceline l on r.uy_cashremittance_id = l.uy_cashremittance_id" +
                  " where r.docstatus = 'CO' and date_trunc ('day', r.datetrx) = '" + this.getDateAction() + "'" +
                  " and r.c_doctype_id = " + doc.get_ID() +
                  " order by l.uy_paymentrule_id, l.c_currency_id";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				int payrule_ID = rs.getInt("UY_PaymentRule_ID");
				int curr_ID = rs.getInt("C_Currency_ID");
				
				//corte de control por medio de pago y moneda
				if(payruleID != payrule_ID || currID != curr_ID) {
								
					payruleID = payrule_ID;
					currID = curr_ID;
					
					//creo cabezal de medio de pago
					payLine = new MCashCountPayRule(getCtx(), 0, get_TrxName());
					payLine.setUY_CashCount_ID(this.get_ID());
					payLine.setUY_PaymentRule_ID(payrule_ID);
					payLine.setC_Currency_ID(curr_ID);
					payLine.saveEx();
					
					this.loadCashLines(payLine,"l.uy_paymentrule_id");				
															
				}				
				
			}			
			
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

	}

	private void loadCashLines(MCashCountPayRule payLine, String where) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			sql = "select distinct l.uy_rt_cashbox_id" + 
                  " from uy_cashremittance r" + 
                  " inner join uy_cashremittanceline l on r.uy_cashremittance_id = l.uy_cashremittance_id" +
                  " where r.docstatus = 'CO' and date_trunc ('day', r.datetrx) = '" + this.getDateAction() + "'" +
                  " and " + where + " = " + payLine.getUY_PaymentRule_ID() + " and l.c_currency_id = " + payLine.getC_Currency_ID() +
                  " order by l.uy_rt_cashbox_id";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				MRTCashBox box = new MRTCashBox(getCtx(),rs.getInt("UY_RT_CashBox_ID"),get_TrxName());
				
				this.createCashLineForBox(box,payLine,where);//creo linea de cajas				
				
			}			
			
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
		
	}

	private void createCashLineForBox(MRTCashBox box, MCashCountPayRule payLine, String where) {
		
		MCashCountLine line = null;

		try{

			//obtengo y cargo monto remesado
			String sql = "select distinct coalesce(sum(l.amount),0)" + 
					" from uy_cashremittance r" + 
					" inner join uy_cashremittanceline l on r.uy_cashremittance_id = l.uy_cashremittance_id" +
					" where r.docstatus = 'CO' and date_trunc ('day', r.datetrx) = '" + this.getDateAction() + "'" +
					" and " + where + " = " + payLine.getUY_PaymentRule_ID() +
					" and l.c_currency_id = " + payLine.getC_Currency_ID() +
					" and l.uy_rt_cashbox_id = " + box.get_ID();

			BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);
			
			if(amount.compareTo(Env.ZERO)>0){
				
				//creo linea de cajas
				line = new MCashCountLine(getCtx(),0,get_TrxName());
				line.setUY_CashCountPayRule_ID(payLine.get_ID());
				line.setUY_RT_CashBox_ID(box.get_ID());
				line.setAmount2(amount);
				line.saveEx();			
				
			}
			
			/*amount = Env.ZERO;
			
			//obtengo y cargo monto teorico
			if(payLine.getC_Currency_ID()==142){
				currency = "1";
			} else if(payLine.getC_Currency_ID()==100) currency = "2";			
			
			List<MRTCodigoMedioPago> payRules = this.getPayRules(payLine.getUY_PaymentRule_ID());//obtengo lista de medios de pago RT
			
			for(MRTCodigoMedioPago mp : payRules){
				
				BigDecimal amt = this.teoricoPorCajaMonedaFormaPago(getCtx(), box, currency, mp.get_ID(), this.loadTicketID, get_TrxName());
				
				if(amt!=null) amount = amount.add(amt);	
				
			}			
			
			if(amount.compareTo(Env.ZERO)>0){
				
				if(line==null){
					
					//creo linea de cajas
					line = new MCashCountLine(getCtx(),0,get_TrxName());
					line.setUY_CashCountPayRule_ID(payLine.get_ID());
					line.setUY_RT_CashBox_ID(box.get_ID());
					line.setAmount(amount);
					line.saveEx();					
					
				} else {
					
					line.setAmount(amount);
					line.saveEx();			
					
				}				
			}*/		
			
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}		

	}
	


	/***
	 * Metodo que carga los datos teoricos de SISTECO en las lineas de medios de pago. 
	 * OpenUp Ltda. Issue #4437
	 * @author Nicolas Sarlabos - 18/12/2015
	 */ 
	private void loadTeoricoLines() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		int mpagoID = 0;
		String monedaID = "";
		MCashCountPayRule payHdr = null;
					
		try {				
						
			sql = "select * from" +
					" (select distinct l.uy_rt_codigomediopago_id,l.codigomoneda" +
					" from uy_rt_ticket_header h" +
					" inner join uy_rt_ticket_lineefectivo l on h.uy_rt_ticket_header_id = l.uy_rt_ticket_header_id" +
					" where h.uy_rt_loadticket_id = " + this.loadTicketID + " and h.estadoticket = 'F'" +
					" union" +
					" select distinct l.uy_rt_codigomediopago_id,l.codigomoneda" +
					" from uy_rt_ticket_header h" +
					" inner join uy_rt_ticket_lineluncheon l on h.uy_rt_ticket_header_id = l.uy_rt_ticket_header_id" +
					" where h.uy_rt_loadticket_id = " + this.loadTicketID + " and h.estadoticket = 'F'" +
					" union" +
					" select distinct l.uy_rt_codigomediopago_id,l.codigomoneda" +
					" from uy_rt_ticket_header h" +
					" inner join uy_rt_ticket_linetarjeta l on h.uy_rt_ticket_header_id = l.uy_rt_ticket_header_id" +
					" where h.uy_rt_loadticket_id = " + this.loadTicketID + " and h.estadoticket = 'F') as x" +
					" order by uy_rt_codigomediopago_id,codigomoneda";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				int currencyID = 0;
				int mpago_ID = rs.getInt("uy_rt_codigomediopago_id");
				String moneda_ID = rs.getString("codigomoneda");
				
				//corte de control por medio de pago y moneda
				if(mpagoID != mpago_ID || monedaID != moneda_ID) {
								
					mpagoID = mpago_ID;
					monedaID = moneda_ID;
					
					MRTCodigoMedioPago codMed = new MRTCodigoMedioPago(getCtx(), mpagoID, get_TrxName());
					
					//si el codigo de medio de pago tiene asociada una forma de pago...
					if(codMed.getUY_PaymentRule_ID()>0){
						
						MPaymentRule payRule = new MPaymentRule(getCtx(), codMed.getUY_PaymentRule_ID(), get_TrxName());

						if(monedaID.equalsIgnoreCase("1")){
							currencyID = 142;
						} else if(monedaID.equalsIgnoreCase("2")) currencyID = 100;

						//obtengo, si existe, el cabezal para este medio de pago y moneda, si no lo creo
						payHdr = MCashCountPayRule.forPayruleAndCurrency(getCtx(), payRule.get_ID(), currencyID, this.get_ID(), get_TrxName());

						if(payHdr==null){
							
							//creo cabezal de medio de pago
							payHdr = new MCashCountPayRule(getCtx(), 0, get_TrxName());
							payHdr.setUY_CashCount_ID(this.get_ID());
							payHdr.setUY_PaymentRule_ID(payRule.get_ID());
							payHdr.setC_Currency_ID(currencyID);
							payHdr.saveEx();	

						} 						
						
						this.loadCashLinesTeorico(payHdr, codMed, monedaID, currencyID, payRule);						
					}					
															
				}				
				
			}			
			
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

	}
	
	private void loadCashLinesTeorico(MCashCountPayRule payHdr, MRTCodigoMedioPago codMed, String monedaCod, int currencyID, MPaymentRule payRule) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MCashCountLine line = null;
		BigDecimal amount = Env.ZERO;
		
		try{			
			
			if(codMed.getValue().equalsIgnoreCase("7")){

				sql = "select distinct h.codigocaja" +
						" from uy_rt_ticket_header h" +
						" inner join uy_rt_ticket_lineluncheon l on h.uy_rt_ticket_header_id = l.uy_rt_ticket_header_id" +
						" where h.uy_rt_loadticket_id = " + this.loadTicketID + " and h.estadoticket = 'F'" +
						" and l.uy_rt_codigomediopago_id = " + codMed.get_ID() + " and l.codigomoneda = '" + monedaCod + "'" +
						" order by h.codigocaja";				

			} else if (codMed.getValue().equalsIgnoreCase("2") || codMed.getValue().equalsIgnoreCase("6")){

				sql = "select distinct h.codigocaja" +
						" from uy_rt_ticket_header h" +
						" inner join uy_rt_ticket_linetarjeta l on h.uy_rt_ticket_header_id = l.uy_rt_ticket_header_id" +
						" where h.uy_rt_loadticket_id = " + this.loadTicketID + " and h.estadoticket = 'F'" +
						" and l.uy_rt_codigomediopago_id = " + codMed.get_ID() + " and l.codigomoneda = '" + monedaCod + "'" +
						" order by h.codigocaja";				

			} else {

				sql = "select distinct h.codigocaja" +
						" from uy_rt_ticket_header h" +
						" inner join uy_rt_ticket_lineefectivo l on h.uy_rt_ticket_header_id = l.uy_rt_ticket_header_id" +
						" where h.uy_rt_loadticket_id = " + this.loadTicketID + " and h.estadoticket = 'F'" +
						" and l.uy_rt_codigomediopago_id = " + codMed.get_ID() + " and l.codigomoneda = '" + monedaCod + "'" +
						" order by h.codigocaja";			

			}

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();	
			
			while(rs.next()){
				
				amount = Env.ZERO;
				
				MRTCashBox box = MRTCashBox.forValue(getCtx(), rs.getString("codigocaja"), get_TrxName());
				
				//obtengo, si existe, linea para esta caja, moneda y medio de pago. Si no existe creo una nueva.
				line = MCashCountLine.get(getCtx(), this.get_ID(), currencyID, box.get_ID(), payRule.get_ID(), get_TrxName());
				
				List<MRTCodigoMedioPago> payRules = this.getPayRules(payHdr.getUY_PaymentRule_ID());//obtengo lista de medios de pago RT

				for(MRTCodigoMedioPago mp : payRules){

					BigDecimal amt = this.teoricoPorCajaMonedaFormaPago(getCtx(), mp, box, monedaCod, this.loadTicketID, get_TrxName());

					if(amt!=null) amount = amount.add(amt);	

				}	
				
				if(amount.compareTo(Env.ZERO)>0){

					if(line==null){

						//creo linea de cajas
						line = new MCashCountLine(getCtx(),0,get_TrxName());
						line.setUY_CashCountPayRule_ID(payHdr.get_ID());
						line.setUY_RT_CashBox_ID(box.get_ID());
						line.setAmount(amount);
						line.setDifferenceAmt(amount);
						line.saveEx();					

					} else {

						line.setAmount(amount);
						line.setDifferenceAmt(amount.subtract(line.getAmount2()));
						line.saveEx();			

					}				
				}				
				
			}			
			
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
	}
	
	/***
	 * Obtiene y retorna codigos de medios de pago RT para determinada forma de pago.
	 * OpenUp Ltda. Issue #4437
	 * @author Nicolas Sarlabos - 18/12/2015
	 * @see
	 * @return
	 */
	public List<MRTCodigoMedioPago> getPayRules(int payRuleID){

		String whereClause = X_UY_RT_CodigoMedioPago.COLUMNNAME_UY_PaymentRule_ID + " = " + payRuleID;

		List<MRTCodigoMedioPago> lines = new Query(getCtx(), I_UY_RT_CodigoMedioPago.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

	/***
	 * Metodo que carga los datos teoricos del cabezal (SISTECO). 
	 * OpenUp Ltda. Issue #4437
	 * @author Nicolas Sarlabos - 08/09/2015
	 */ 
	private void loadTeoricoHdr() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal amount = Env.ZERO;
		
		try {
			
			//obtengo datos desde registro de auditoria para la fecha indicada
			sql = "select amtvtaefectivo,amtvtatarjeta,amtvtacheque,amtvtatarjetacuota,amtvtaluncheon,amtvtaefectivosodexo," +
				  " amtvtadevenvases,amtvtatarjetaofline,coalesce(vtatjaedenredsodexo,0) as vtatjaedenredsodexo,amtvtacredito," +
				  " amtvtapagodeservicio,amtvtadevenvases,amtvtaefectivodolares,amtvtatarjetadlrs,amtvtachequedlrs,amtvtacreditodlrs" +
                  " from uy_rt_auditloadticket" +
                  " where uy_rt_auditloadticket_id = " + this.auditTicketID +
                  " order by created desc";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				//seteo total de ventas $
				BigDecimal vtaPesos = rs.getBigDecimal("amtvtaefectivo").add(rs.getBigDecimal("amtvtatarjeta")).add(rs.getBigDecimal("amtvtacheque")).add(rs.getBigDecimal("amtvtatarjetacuota"))
						.add(rs.getBigDecimal("amtvtaluncheon")).add(rs.getBigDecimal("amtvtaefectivosodexo")).add(rs.getBigDecimal("amtvtadevenvases")).add(rs.getBigDecimal("amtvtatarjetaofline"))
						.add(rs.getBigDecimal("vtatjaedenredsodexo"));
				
				this.setamtventas(vtaPesos.setScale(2, RoundingMode.HALF_UP));
				this.setamt4(vtaPesos.setScale(2, RoundingMode.HALF_UP));
				
				//seteo monto de creditos en $
				this.setamtcredito(rs.getBigDecimal("amtvtacredito"));		
				
				//seteo monto de pagos de servicios en $
				this.setamtvtapagodeservicio(rs.getBigDecimal("amtvtapagodeservicio"));
				this.setamt5(rs.getBigDecimal("amtvtapagodeservicio"));
				
				//seteo monto de devolucion de envases $
				this.setamtvtadevenvases(rs.getBigDecimal("amtvtadevenvases"));				
							
				//seteo total de ventas U$
				BigDecimal ventasDolares = rs.getBigDecimal("amtvtaefectivodolares").add(rs.getBigDecimal("amtvtatarjetadlrs")).add(rs.getBigDecimal("amtvtachequedlrs"));
				this.setamtventas2(ventasDolares.setScale(2, RoundingMode.HALF_UP));	
				
				//seteo monto de creditos en U$
				this.setamtcredito2(rs.getBigDecimal("amtvtacreditodlrs"));		
				
			} else ADialog.info(0,null,"No se obtuvieron datos de auditoria de ventas para la fecha de venta/remesa seleccionada");
			
			//seteo total de cheques teoricos
			MRTCodigoMedioPago mp = MRTCodigoMedioPago.forValue(getCtx(), "3", get_TrxName());
			
			if(mp!=null && mp.get_ID()>0){
				
				//seteo total de cheques teorico $
				amount = this.teoricoPorCajaMonedaFormaPago(getCtx(), mp, null, "1", this.loadTicketID, get_TrxName());
				if(amount.compareTo(Env.ZERO)>0) this.setAmtChequeTeo(amount);				
				
				//seteo total de cheques teorico U$
				amount = this.teoricoPorCajaMonedaFormaPago(getCtx(), mp, null, "2", this.loadTicketID, get_TrxName());
				if(amount.compareTo(Env.ZERO)>0) this.setAmtChequeTeo2(amount);			
				
			}
			
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

	}
	
	/**Calcula importe TEORICO de ventas por caja, moneda y medio de pago 
	 * OpenUp Ltda Issue#4437
	 * @author Nicolas Sarlabos 18/12/2015
	 * @return
	 */
	public BigDecimal teoricoPorCajaMonedaFormaPago(Properties ctx, MRTCodigoMedioPago codMed, MRTCashBox box, String codMoneda, int Cov_LoadTicket_ID,
			String trxName) {

		BigDecimal retorno = BigDecimal.ZERO;	
		String sql = "", where = "";

		if(box!=null) where = " AND h.codigocaja = '" + box.getValue() + "'";
		
		//si es Efectivo, Sodexo, Cheque, Tarjeta Manual
		if(codMed.getValue().equalsIgnoreCase("1") || codMed.getValue().equalsIgnoreCase("3") 
											|| codMed.getValue().equalsIgnoreCase("10") || codMed.getValue().equalsIgnoreCase("14")){
			
			String calculo = "totalentregado - cambio";
			
			if(!codMoneda.equals("1")){//Si no es en pesos solo se contempla el total entregado
				calculo = "totalentregado";
			}
			
			MRTTicketType venta = MRTTicketType.forValueAndHeader(ctx, MRTTicketType.vVenta, "Y", trxName);
			MRTTicketType dev = MRTTicketType.forValueAndHeader(ctx,  MRTTicketType.vDevolucion, "Y", trxName);			

			sql = " SELECT coalesce(SUM(" + calculo + "),0)" + 
					" FROM UY_RT_Ticket_LineEfectivo" +
					" WHERE codigomoneda = '" + codMoneda + "' AND uy_rt_codigomediopago_id = " + codMed.get_ID() +
					" AND UY_RT_Ticket_Header_ID IN (SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header h" +
					" WHERE h.UY_RT_LoadTicket_ID = " + this.loadTicketID +
					" AND h.estadoticket = 'F' AND h.UY_RT_ticketType_ID IN (" + venta.get_ID() + "," + dev.get_ID() + ")" + where + " )";

			retorno = DB.getSQLValueBDEx(get_TrxName(), sql);			
			
			if(codMed.getValue().equalsIgnoreCase("1")){//si es venta efectivo $
				
				if(codMoneda.equalsIgnoreCase("1")){
					
					sql = " SELECT coalesce(SUM(cambio),0)" + 
							" FROM UY_RT_Ticket_LineEfectivo" +
							" WHERE codigomoneda = '2' AND uy_rt_codigomediopago_id = " + codMed.get_ID() +
							" AND UY_RT_Ticket_Header_ID IN (SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header h" +
							" WHERE h.UY_RT_LoadTicket_ID = " + this.loadTicketID +
							" AND h.estadoticket = 'F' AND h.UY_RT_ticketType_ID IN (" + venta.get_ID() + "," + dev.get_ID() + ")" + where + " )";
					
					BigDecimal cambio = DB.getSQLValueBDEx(get_TrxName(), sql);
					
					retorno = retorno.subtract(cambio);					
					
				}				
				
			}			
		
		//si es Tarjeta
		} else if (codMed.getValue().equalsIgnoreCase("2")){	
			
			sql = "SELECT coalesce(SUM(l.totalentregado),0)" + 
					" FROM UY_RT_Ticket_LineTarjeta l" +
					" INNER JOIN UY_RT_Ticket_Header h ON l.UY_RT_Ticket_Header_ID = h.UY_RT_Ticket_Header_ID" +
					" WHERE l.isactive = 'Y' AND l.codigomoneda = '" + codMoneda + "' AND l.cuotastarjetacredito = '1'" +
					" AND l.UY_RT_Ticket_Header_ID IN (SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header" +
					" WHERE UY_RT_LoadTicket_ID = " + this.loadTicketID + ")" + where;
			
			retorno = DB.getSQLValueBDEx(get_TrxName(), sql);			
			
		//si es Tarjeta Cuota
		} else if (codMed.getValue().equalsIgnoreCase("6")){	
			
			sql = "SELECT coalesce(SUM(l.totalentregado),0)" + 
					" FROM UY_RT_Ticket_LineTarjeta l" +
					" INNER JOIN UY_RT_Ticket_Header h ON l.UY_RT_Ticket_Header_ID = h.UY_RT_Ticket_Header_ID" +
					" WHERE l.isactive = 'Y' AND l.codigomoneda = '" + codMoneda + "' AND l.cuotastarjetacredito != '1'" +
					" AND l.UY_RT_Ticket_Header_ID IN (SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header" +
					" WHERE UY_RT_LoadTicket_ID = " + this.loadTicketID + ")" + where;
			
			retorno = DB.getSQLValueBDEx(get_TrxName(), sql);			
		
		//si es Luncheon Ticket
		} else if (codMed.getValue().equalsIgnoreCase("7")){
			
			sql = "SELECT coalesce(SUM(l.totalentregado),0)" + 
					" FROM UY_RT_Ticket_LineLuncheon l" +
					" INNER JOIN UY_RT_Ticket_Header h ON l.UY_RT_Ticket_Header_ID = h.UY_RT_Ticket_Header_ID" +
					" WHERE l.isactive = 'Y' AND l.codigomoneda = '" + codMoneda + "'" +
					" AND l.UY_RT_Ticket_Header_ID IN (SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header" +
					" WHERE UY_RT_LoadTicket_ID = " + this.loadTicketID + ")" + where;
			
			retorno = DB.getSQLValueBDEx(get_TrxName(), sql);			
			
		//si es Edenred/Sodexo
		} else if (codMed.getValue().equalsIgnoreCase("23")){
			
			if(codMoneda.equalsIgnoreCase("1")){//solo para Pesos
				
				sql = "SELECT coalesce(SUM(l.ImportePago),0)" + 
						" FROM UY_RT_Ticket_LineVTarjTACRE l" +
						" INNER JOIN UY_RT_Ticket_Header h ON l.UY_RT_Ticket_Header_ID = h.UY_RT_Ticket_Header_ID" +
						" WHERE l.isactive = 'Y' AND l.codigomoneda = '00' AND l.tipovaucher = '1'" +
						" AND l.UY_RT_Ticket_Header_ID IN (SELECT UY_RT_Ticket_Header_ID FROM UY_RT_Ticket_Header" +
						" WHERE UY_RT_LoadTicket_ID = " + this.loadTicketID + ")" + where;
				
				retorno = DB.getSQLValueBDEx(get_TrxName(), sql);		
				
			}		
			
		}		

		if(retorno!=null){
			return retorno;
		}else{
			return BigDecimal.ZERO;
		}
	}
		
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
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
		
		if(this.getCurrencyRate().compareTo(Env.ZERO)<=0) throw new AdempiereException("La Tasa de Cambio debe ser mayor a cero");
		
		boolean valid = this.validateCount();
		
		if(!valid){
			
			ADialog.info(0,null,"Se han encontrado inconsistencias, verifique pestaña ERRORES");
			
			return DocAction.STATUS_InProgress;			
			
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

	/**Realiza validaciones antes de completar el documento 
	 * OpenUp Ltda Issue#5260
	 * @author Nicolas Sarlabos 28/12/2015
	 * @return
	 */
	private boolean validateCount() {
		
		boolean salida = true;
		BigDecimal totalPesos = Env.ZERO;
		//BigDecimal totalPesos2 = Env.ZERO, difPesos = Env.ZERO, totalDolares1 = Env.ZERO, totalDolares2 = Env.ZERO, difDolares = Env.ZERO; 
		
		//borro errores anteriores
		DB.executeUpdateEx("delete from uy_cashcounterror where uy_cashcount_id = " + this.get_ID(), get_TrxName());
		
		MCashConfig config = MCashConfig.forClient(getCtx(), get_TrxName());
		
		if(config==null) throw new AdempiereException("No se pudo obtener parametros de caja");
		
		//verifico dinero total en empresa
		totalPesos = this.getAmtCajas2().add(this.getAmtFondoFijo2()).add(this.getAmtCofre2());
		
		if(totalPesos.compareTo(config.getAmount()) < 0) {
			
			MCashCountError error = new MCashCountError(getCtx(),0,get_TrxName());
			error.setUY_CashCount_ID(this.get_ID());
			error.setDescription("Monto total en empresa es menor a $ " + config.getAmount());
			error.saveEx();
			
			salida = false;			
		}
		
		/*totalPesos1 = this.getamtventas().add(this.getamtvtapagodeservicio());
		totalPesos2 = (this.getmontopesos().add(this.getamt1()).add(this.getamtvtatktalimentacion()).add(this.getamtvtatarjeta())).subtract(this.getamtcobrocredito());
		
		difPesos = totalPesos1.subtract(totalPesos2);
		
		if(difPesos.compareTo(Env.ZERO)!=0){//si la diferencia no es cero, debo ver la tolerancia
			
			BigDecimal tolerance = config.getToleranceAmount();
			
			BigDecimal bordeInferior = tolerance.negate();
			BigDecimal bordeSuperior = tolerance;
			
			if (difPesos.compareTo(bordeInferior)<0 || difPesos.compareTo(bordeSuperior)>0){
				
				MCashCountError error = new MCashCountError(getCtx(),0,get_TrxName());
				error.setUY_CashCount_ID(this.get_ID());
				error.setDescription("La diferencia de totales en pesos supera la tolerancia definida");
				error.saveEx();
				
				salida = false;				
				
			}			
		}
		
		totalDolares1 = this.getamtventas2();
		totalDolares2 = (this.getmontodolares().add(this.getamt2()).add(this.getamtvtatarjeta2())).subtract(this.getamtcobrocredito2());
		
		difDolares = totalDolares1.subtract(totalDolares2);
		
		if(difDolares.compareTo(Env.ZERO)!=0){//si la diferencia no es cero, debo ver la tolerancia
			
			BigDecimal tolerance = config.getTolerance();
			
			BigDecimal bordeInferior = tolerance.negate();
			BigDecimal bordeSuperior = tolerance;
			
			if (difDolares.compareTo(bordeInferior)<0 || difDolares.compareTo(bordeSuperior)>0){
				
				MCashCountError error = new MCashCountError(getCtx(),0,get_TrxName());
				error.setUY_CashCount_ID(this.get_ID());
				error.setDescription("La diferencia de totales en dolares supera la tolerancia definida");
				error.saveEx();
				
				salida = false;				
				
			}			
		}*/
		
		boolean salidaBox = this.verifyBoxes();//metodo que verifica los importes teoricos y remesados de cada caja y medio de pago	
		
		if(salida==true && salidaBox==false) salida = false;
		
		//comparo importes totales de remesas en $ y U$ contra ventas y envases
		boolean salidaAmt1 = this.verifyAmt(142);
		
		if(salida==true && salidaAmt1==false) salida = false;
		
		boolean salidaAmt2 = this.verifyAmt(100);
		
		if(salida==true && salidaAmt2==false) salida = false;		
		
		return salida;		
	}

	/**Verifica montos teoricos y remesados por cada caja, medio de pago y moneda.
	 * OpenUp Ltda Issue#
	 * @author Nicolas Sarlabos 29/12/2015
	 * @return
	 */
	private boolean verifyAmt(int currID) {
		
		boolean salida = true;
		BigDecimal amt2 = Env.ZERO;
		
		String currency = "", description = "";
		
		if(currID==142){
			
			currency = "$";
			
			amt2 = this.getamtventas().subtract(this.getamtvtadevenvases().add(this.getQuebranto()));
			description = "Monto total remesado en " + currency + " no coincide con la suma de ventas + envases";
			
		} else {
			
			currency = "U$";
			
			amt2 = this.getamtventas2();
			description = "Monto total remesado en " + currency + " no coincide con el total de ventas";
		}
		
		String sql = "select coalesce(sum(l.amount2),0) + coalesce(sum(l.quebranto),0)" +
					" from uy_cashcountline l" +
					" inner join uy_cashcountpayrule r on l.uy_cashcountpayrule_id = r.uy_cashcountpayrule_id" +
					" where r.c_currency_id = " + currID + " and r.uy_cashcount_id = " + this.get_ID();
		
		BigDecimal amt = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		if(amt.compareTo(amt2)!=0){
			
			MCashCountError error = new MCashCountError(getCtx(),0,get_TrxName());
			error.setUY_CashCount_ID(this.get_ID());
			error.setDescription(description);
			error.saveEx();
			
			salida = false;		
			
		}		
		
		return salida;
	}

	/**Verifica montos teoricos y remesados por cada caja, medio de pago y moneda.
	 * OpenUp Ltda Issue#5260
	 * @author Nicolas Sarlabos 29/12/2015
	 * @return
	 */
	private boolean verifyBoxes() {
		
		boolean salida = true;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			sql = "select box.value, p.name, cur.cursymbol" +
					" from uy_cashcountline l" +
					" inner join uy_cashcountpayrule r on l.uy_cashcountpayrule_id = r.uy_cashcountpayrule_id" +
					" inner join uy_rt_cashbox box on l.uy_rt_cashbox_id = box.uy_rt_cashbox_id" +
					" inner join uy_paymentrule p on r.uy_paymentrule_id = p.uy_paymentrule_id" +
					" inner join c_currency cur on r.c_currency_id = cur.c_currency_id" +
					" where r.uy_cashcount_id = " + this.get_ID() + " and coalesce(l.amount,0) <> (coalesce(l.amount2,0) + coalesce(l.quebranto,0))" +
					" and p.value not in ('cheque diferido','chequedia')" +
					" order by box.value, p.name, cur.cursymbol";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){
				
				MCashCountError error = new MCashCountError(getCtx(),0,get_TrxName());
				error.setUY_CashCount_ID(this.get_ID());
				error.setDescription("La caja " + rs.getString("value") + " con medio de pago '" + rs.getString("name") + 
						"' y moneda " + rs.getString("cursymbol") + " tiene diferencia entre monto Teorico y Remesado + Quebranto");
				
				error.saveEx();
				
				if(salida) salida = false;
				
			}
			
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}				

		return salida;
	}
	
	/***
	 * Obtiene y retorna documento de arqueo en estado distinto a anulado segun fecha de remesa recibida.
	 * OpenUp Ltda. Issue #5262
	 * @author Nicolas Sarlabos - 30/12/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MCashCount forDateRemit(Properties ctx, Timestamp date, String trxName){
		
		String whereClause = X_UY_CashCount.COLUMNNAME_DateAction + "='" + date + "' AND " + X_UY_CashCount.COLUMNNAME_DocStatus + " <> 'VO'";
		
		MCashCount model = new Query(ctx, I_UY_CashCount.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return model;
	}

	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		Timestamp date = OpenUpUtils.sumaTiempo(this.getDateAction(), Calendar.DAY_OF_MONTH, 1);
		
		MCashCount count = MCashCount.forDateRemit(getCtx(), date, get_TrxName());
		
		if(count!=null && count.get_ID()>0) 
			throw new AdempiereException("Imposible anular, existe el arqueo nº " + count.getDocumentNo() + " con fecha de remesa posterior a la indicada");	
		
		//borro asientos contables
		DB.executeUpdateEx("delete from fact_acct where ad_table_id = " + X_UY_CashCount.Table_ID + " and record_id = " + this.get_ID(), get_TrxName());		

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
		// TODO Auto-generated method stub
		return false;
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
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (this.processMsg != null)
			return false;	
		
		Timestamp date = OpenUpUtils.sumaTiempo(this.getDateAction(), Calendar.DAY_OF_MONTH, 1);
		
		MCashCount count = MCashCount.forDateRemit(getCtx(), date, get_TrxName());
		
		if(count!=null && count.get_ID()>0) 
			throw new AdempiereException("Imposible reactivar, existe el arqueo nº " + count.getDocumentNo() + " con fecha de remesa posterior a la indicada");	
		
		//borro asientos contables
		DB.executeUpdateEx("delete from fact_acct where ad_table_id = " + X_UY_CashCount.Table_ID + " and record_id = " + this.get_ID(), get_TrxName());
		
		// After reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (this.processMsg != null)
			return false;
		
		this.setProcessed(false);
		this.setDocStatus(DocAction.STATUS_InProgress);
		this.setDocAction(DocAction.ACTION_Complete);		
		
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
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
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
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	public void loadData() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			//si el estado de documento es distinto a BORRADOR, debo borrar todos los datos previamente cargados
			if(!this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Drafted)){

				//seteo en cero campos del cabezal
				sql = "update uy_cashcount set montopesos = 0, amtventas = 0, amt1 = 0, amtcredito = 0, amtvtatarjeta = 0," +
						" amtcobrocredito = 0, amtvtatktalimentacion = 0, amtvtapagodeservicio = 0, amtvtadevenvases = 0," +
						" amtchequerem = 0, amtchequeteo = 0, montodolares = 0, amtventas2 = 0, amt2 = 0, amtcredito2 = 0," +
						" amtvtatarjeta2 = 0, amtcobrocredito2 = 0, amtchequerem2 = 0, amtchequeteo2 = 0, amtcajas = 0, amtcajas2 = 0," +
						" amtfondofijo = 0, amtfondofijo2 = 0, amtcofre = 0, amtcofre2 = 0, amtinitial = 0, amtfinal = 0," +
						" amtventasbasico = 0, amtventasminimo = 0, amtventasexento = 0, amtventaspercibido = 0, amtventasvegetales = 0," +
						" amtventascarnes2 = 0, amtventascarnes = 0, ivaventasbasico = 0, ivaventasminimo = 0, ivaventasexento = 0," +
						" ivaventaspercibido = 0, ivaventasvegetales = 0, ivaventascarnes2 = 0, ivaventascarnes = 0" +
						" where uy_cashcount_id = " + this.get_ID();

				DB.executeUpdateEx(sql, get_TrxName());

				//elimino mensajes de error
				DB.executeUpdateEx("delete from uy_cashcounterror where uy_cashcount_id = " + this.get_ID(), get_TrxName());

				//elimino lineas de cajas
				sql = "delete from uy_cashcountline" +
						" where uy_cashcountline_id in (select uy_cashcountline_id from uy_cashcountline l" +
						" inner join uy_cashcountpayrule pr on l.uy_cashcountpayrule_id = pr.uy_cashcountpayrule_id" +
						" where pr.uy_cashcount_id = " + this.get_ID() + ")";
				
				DB.executeUpdateEx(sql, get_TrxName());
				
				//elimino cabezales medios de pago
				DB.executeUpdateEx("delete from uy_cashcountpayrule where uy_cashcount_id = " + this.get_ID(), get_TrxName());
			}			
			
			//cargo variables necesarias
			sql = "select uy_rt_auditloadticket_id, uy_rt_loadticket_id" +
					" from uy_rt_auditloadticket" +
					" where isactive = 'Y' and datevalue = '" + this.getDateAction() + "'";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();

			if(rs.next()){

				this.auditTicketID = rs.getInt("uy_rt_auditloadticket_id");
				this.loadTicketID = rs.getInt("uy_rt_loadticket_id");

			} else throw new AdempiereException("No se obtuvo datos de auditoria para fecha de venta/remesa");

			//proceso y cargo los datos necesarios
			this.loadRemesaLines();//cargo datos de remesas en lineas
			this.loadRemesaChequeLines();//cargo datos de remesas de cheques en lineas
			this.loadTeoricoLines();//cargo datos teoricos en lineas
			this.loadTeoricoHdr();//cargo datos teoricos en cabezal
			this.setTotalRemesaHdr();//cargo datos de remesas en cabezal
			this.setTotalByTax();//cargo totales por tipo de impuesto
			
			//seteo campos de totales de grupo Resumen
			this.setamt7(this.getamt3().add(this.getamt5()));
			
			if(this.getamtcredito2().compareTo(Env.ZERO)>0){
				
				BigDecimal amount = this.getamtcredito2().multiply(this.getCurrencyRate());
				
				this.setamt6(this.getamtcredito().add(amount));			
				
			} else this.setamt6(this.getamtcredito());
			
			if(this.getamtventas2().compareTo(Env.ZERO)>0){
				
				BigDecimal amount = this.getamtventas2().multiply(this.getCurrencyRate());
				
				this.setamt8(amount);
				
			}
			
			this.setamt9(this.getamt4().add(this.getamt6()).add(this.getamt8()));
			
			this.saveEx();

		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
		
	}

}
