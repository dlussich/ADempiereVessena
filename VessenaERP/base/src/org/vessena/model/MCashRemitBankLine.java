/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankAccount;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MCashRemitBankLine extends X_UY_CashRemitBankLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3151580797630957526L;

	/**
	 * @param ctx
	 * @param UY_CashRemitBankLine_ID
	 * @param trxName
	 */
	public MCashRemitBankLine(Properties ctx, int UY_CashRemitBankLine_ID,
			String trxName) {
		super(ctx, UY_CashRemitBankLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashRemitBankLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MCashRemitBank hdr = (MCashRemitBank)this.getUY_CashRemitBank();		
		
		MCashConfig conf = MCashConfig.forClient(getCtx(), get_TrxName());
		
		if(conf==null) throw new AdempiereException("No se obtuvieron parametros de caja para la empresa actual");		
		
		if(newRecord || is_ValueChanged("Amount")){
			if(this.getAmount().compareTo(Env.ZERO)<=0) throw new AdempiereException("Monto debe ser mayor a cero");			
		}
		
		if(newRecord || is_ValueChanged("QtyCount")){
			
			if(this.getQtyCount()<=0) throw new AdempiereException("Cantidad de billetes debe ser mayor a cero");
			
			if(this.getQtyCount()>conf.getCantBilletes()) throw new AdempiereException("Cantidad de billetes no puede ser mayor a " + conf.getCantBilletes());				
			
		}
		
		//verifico si la cuenta seleccionada requiere nro de sobre o no
		MCashConfigAccount confAcct = MCashConfigAccount.forBankAccount(getCtx(), hdr.getC_BankAccount_ID(), get_TrxName());
		
		if(confAcct!=null && confAcct.get_ID()>0){
			
			if(this.getNroSobre()==null || this.getNroSobre().equalsIgnoreCase(""))
				throw new AdempiereException("La cuenta bancaria seleccionada requiere indicar numero de sobre");					
			
		} else {
			
			if(this.getNroSobre()!=null && !this.getNroSobre().equalsIgnoreCase(""))
				throw new AdempiereException("La cuenta bancaria seleccionada no requiere indicar numero de sobre");	
			
		}
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		MCashRemitBank rBank = (MCashRemitBank)this.getUY_CashRemitBank();
		MCashRemittance hdr = (MCashRemittance)rBank.getUY_CashRemittance();
		MBankAccount account = (MBankAccount)rBank.getC_BankAccount(); 	
		MCashRemitSum sumLine = MCashRemitSum.forHdrAndCurrency(getCtx(), rBank.getUY_CashRemittance_ID(), account.getC_Currency_ID(), get_TrxName());		

		if(sumLine==null){

			sumLine = new MCashRemitSum(getCtx(), 0, get_TrxName());
			sumLine.setUY_CashRemittance_ID(rBank.getUY_CashRemittance_ID());
			sumLine.setC_Currency_ID(account.getC_Currency_ID());
			sumLine.saveEx();

		}

		sumLine.setAmount2(this.totalAmountForCurrency(account.getC_Currency_ID(),hdr.get_ID()));
		sumLine.setQtyCount2(this.totalQtyForCurrency(account.getC_Currency_ID(),hdr.get_ID()));
		sumLine.saveEx();
		
		if(sumLine.getAmount().compareTo(Env.ZERO)==0 && sumLine.getAmount2().compareTo(Env.ZERO)==0 && 
				sumLine.getQtyCount()==0 && sumLine.getQtyCount2()==0) sumLine.deleteEx(true);
		
		//si es ultima remesa
		if(hdr.isLastRemittance()){
			
			MCashConfig config = MCashConfig.forClient(getCtx(), get_TrxName());
			
			if(config==null) throw new AdempiereException("No se pudo obtener parametros de caja");
			
			MBankAccount fondoFijo = new MBankAccount(getCtx(),config.getC_BankAccount_ID_2(),get_TrxName());
			
			MCashConfigChest configChest = MCashConfigChest.forCurrency(getCtx(), 142, get_TrxName());
			
			if(configChest==null) throw new AdempiereException("No se pudo obtener Cofre en $ desde parametros de caja");	
			
			//actualizo monto de faltante en empresa si corresponde
			if(rBank.getC_BankAccount_ID()==fondoFijo.get_ID() || rBank.getC_BankAccount_ID()==configChest.getC_BankAccount_ID()){
				
				hdr.setAmount2(hdr.getAmount3().subtract(this.totalAmountCofreFondo(fondoFijo.get_ID(), configChest.getC_BankAccount_ID(), hdr.get_ID())));
				hdr.saveEx();
				
			}		
			
		}

		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
	
		MCashRemitBank rBank = (MCashRemitBank)this.getUY_CashRemitBank();
		MCashRemittance hdr = (MCashRemittance)rBank.getUY_CashRemittance();
		MBankAccount account = (MBankAccount)rBank.getC_BankAccount(); 	
		MCashRemitSum sumLine = MCashRemitSum.forHdrAndCurrency(getCtx(), rBank.getUY_CashRemittance_ID(), account.getC_Currency_ID(), get_TrxName());	

		if(sumLine==null){

			sumLine = new MCashRemitSum(getCtx(), 0, get_TrxName());
			sumLine.setUY_CashRemittance_ID(rBank.getUY_CashRemittance_ID());
			sumLine.setC_Currency_ID(account.getC_Currency_ID());
			sumLine.saveEx();

		}

		sumLine.setAmount2(this.totalAmountForCurrency(account.getC_Currency_ID(),hdr.get_ID()));
		sumLine.setQtyCount2(this.totalQtyForCurrency(account.getC_Currency_ID(),hdr.get_ID()));
		sumLine.saveEx();
		
		if(sumLine.getAmount().compareTo(Env.ZERO)==0 && sumLine.getAmount2().compareTo(Env.ZERO)==0 && 
				sumLine.getQtyCount()==0 && sumLine.getQtyCount2()==0) sumLine.deleteEx(true);
		
		//si es ultima remesa
		if(hdr.isLastRemittance()){
			
			MCashConfig config = MCashConfig.forClient(getCtx(), get_TrxName());
			
			if(config==null) throw new AdempiereException("No se pudo obtener parametros de caja");
			
			MBankAccount fondoFijo = new MBankAccount(getCtx(),config.getC_BankAccount_ID_2(),get_TrxName());
			
			MCashConfigChest configChest = MCashConfigChest.forCurrency(getCtx(), 142, get_TrxName());
			
			if(configChest==null) throw new AdempiereException("No se pudo obtener Cofre en $ desde parametros de caja");
			
			//actualizo monto de faltante en empresa si corresponde
			if(rBank.getC_BankAccount_ID()==fondoFijo.get_ID() || rBank.getC_BankAccount_ID()==configChest.getC_BankAccount_ID()){
				
				hdr.setAmount2(hdr.getAmount3().subtract(this.totalAmountCofreFondo(fondoFijo.get_ID(), configChest.getC_BankAccount_ID(), hdr.get_ID())));
				hdr.saveEx();
				
			}		
			
		}	

		return true;
	}
	
	public BigDecimal totalAmountForCurrency(int curID, int hdrID){
		
		BigDecimal total = Env.ZERO;
		
		String sql = "select coalesce(sum(bl.amount),0)" +
		             " from uy_cashremitbankline bl" +
		             " inner join uy_cashremitbank b on bl.uy_cashremitbank_id = b.uy_cashremitbank_id" +
		             " inner join c_bankaccount acct on b.c_bankaccount_id = acct.c_bankaccount_id" +
				     " where b.uy_cashremittance_id = " + hdrID +
				     " and acct.c_currency_id = " + curID;
		
		total = DB.getSQLValueBDEx(get_TrxName(), sql);
					
		return total;
	}
	
	public int totalQtyForCurrency(int curID, int hdrID){
		
		int total = 0;
		
		String sql =  "select coalesce(sum(bl.qtycount),0)" +
					  " from uy_cashremitbankline bl" +
	                  " inner join uy_cashremitbank b on bl.uy_cashremitbank_id = b.uy_cashremitbank_id" +
	                  " inner join c_bankaccount acct on b.c_bankaccount_id = acct.c_bankaccount_id" +
			          " where b.uy_cashremittance_id = " + hdrID +
			          " and acct.c_currency_id = " + curID;
		
		total = DB.getSQLValueEx(get_TrxName(), sql);
					
		return total;
	}
	
	public BigDecimal totalAmountCofreFondo(int fondoID, int cofreID, int hdrID){
		
		BigDecimal total = Env.ZERO;
		
		String sql = "select coalesce(sum(bl.amount),0)" +
		             " from uy_cashremitbankline bl" +
		             " inner join uy_cashremitbank b on bl.uy_cashremitbank_id = b.uy_cashremitbank_id" +
		             " where b.uy_cashremittance_id = " + hdrID +
				     " and b.c_bankaccount_id in (" + fondoID + "," + cofreID + ")";
		
		total = DB.getSQLValueBDEx(get_TrxName(), sql);
					
		return total;
	}
	
	

}
