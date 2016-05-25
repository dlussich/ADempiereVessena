/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MFFCashCount extends X_UY_FF_CashCount implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7159711317703565727L;

	/**
	 * @param ctx
	 * @param UY_FF_CashCount_ID
	 * @param trxName
	 */
	public MFFCashCount(Properties ctx, int UY_FF_CashCount_ID, String trxName) {
		super(ctx, UY_FF_CashCount_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFFCashCount(Properties ctx, ResultSet rs, String trxName) {
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
		this.loadData(); //cargo lineas de la reposicion en curso
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}
	
	/**OpenUp. Nicolas Sarlabos. 10/01/2014. #1745
	 * Metodo que carga campos de importe y lineas del documento de reposicion en curso 
	 * para la sucursal y moneda seleccionadas en el cabezal.
	 * @return
	 */
	private void loadData() {
		
		//obtengo documento de reposicion en curso para la sucursal y moneda actual
		MFFReplenish replenish = MFFReplenish.forBranchCurrency(getCtx(), this.getUY_FF_Branch_ID(), this.getC_Currency_ID(), get_TrxName());
		
		if(replenish==null) throw new AdempiereException("No se pudo obtener documento de reposicion en curso para el fondo fijo y moneda seleccionados");
		
		this.setamt3(replenish.getActualAmt().setScale(2, RoundingMode.HALF_UP)); //seteo total monedas y billetes sujetos a arqueo
		this.setamt4(replenish.getamtacumulate().setScale(2, RoundingMode.HALF_UP)); //seteo total comprobantes sujetos a arqueo
		
		BigDecimal totalFondosArq = this.getTotalFondosArqueados();
		BigDecimal totalFondosSujetosArq = this.getTotalFondosSujetosArqueo();
		
		this.setDifferenceAmt(totalFondosArq.subtract(totalFondosSujetosArq)); //seteo el sobrante/faltante
		
		List<MFFReplenishLine> lines = replenish.getLines(); //obtengo lineas de reposicion
		
		for (MFFReplenishLine rline: lines){  //recorro lineas de reposicion para ir generando las del arqueo

			MFFCashCountLine cline = new MFFCashCountLine(getCtx(),0,get_TrxName());
			cline.setUY_FF_CashCount_ID(this.get_ID());
			cline.setLine(rline.getLine());
			cline.setDateTrx(rline.getDateTrx());
			cline.setRecord_ID(rline.getRecord_ID());
			cline.setAD_Table_ID(rline.getAD_Table_ID());
			cline.setC_DocType_ID(rline.getC_DocType_ID());
			cline.setDocumentNo(rline.getDocumentNo());
			cline.setC_BPartner_ID(rline.getC_BPartner_ID());
			cline.setChargeName(rline.getChargeName());
			cline.setAD_User_ID(rline.getAD_User_ID());
			cline.setAmount(rline.getAmount());
			cline.setDescription(rline.getDescription());
			cline.setApprovedBy(rline.getApprovedBy());
			cline.setestado(false);
			cline.saveEx();
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
		
		//si el importe sobrante/faltante es diferente de cero actualizo documento de reposicion
		if(this.getDifferenceAmt().compareTo(Env.ZERO)!=0) this.updateReplenish(); 		
			
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
	
	@Override
	protected boolean beforeSave(boolean newRecord) {

		if(newRecord || (!newRecord && (is_ValueChanged("UY_FF_Branch_ID") || is_ValueChanged("C_Currency_ID")))){

			String sql = "select uy_ff_cashcount_id" +
					" from uy_ff_cashcount" +
					" where uy_ff_branch_id = " + this.getUY_FF_Branch_ID() +
					" and c_currency_id = " + this.getC_Currency_ID() +
					" and docstatus in ('DR','AY')" +
					" and uy_ff_cashcount_id <> " + this.get_ID();

			int ID = DB.getSQLValueEx(get_TrxName(), sql);

			if(ID > 0) throw new AdempiereException ("Ya existe un documento sin completar con igual sucursal y moneda");

		}

		return true;
	}

	/***
	 * Metodo que actualiza el documento de reposicion de FF.
	 * OpenUp Ltda. Issue #1745
	 * @author Nicolas Sarlabos - 13/01/2014
	 * @see
	 * @return
	 */	
	private void updateReplenish() {
		
		BigDecimal amt = Env.ZERO;
		MFFReplenish replenish = null;
		
		//si existe diferencia faltante o sobrante debo insertar en el doc de reposicion actual
		if(this.getDifferenceAmt().compareTo(Env.ZERO)!=0){
			
			//obtengo documento de reposicion en curso para la sucursal y moneda actual
			replenish = MFFReplenish.forBranchCurrency(getCtx(), this.getUY_FF_Branch_ID(), this.getC_Currency_ID(), get_TrxName());
			
			if(replenish==null) throw new AdempiereException("No se encontro documento de reposicion de fondo fijo en curso, por favor verifique");
			
			amt = this.getDifferenceAmt().negate(); //guardo monto a utilizar multiplicado por -1
			
			MUser user = new MUser(getCtx(),this.getAD_User_ID(),get_TrxName());
			
			MFFReplenishLine line = new MFFReplenishLine(getCtx(),0,get_TrxName());
			line.setUY_FF_Replenish_ID(replenish.get_ID());
			line.setDateTrx(this.getUpdated());
			line.setRecord_ID(this.get_ID());
			line.setAD_Table_ID(I_UY_FF_CashCount.Table_ID);
			line.setC_DocType_ID(this.getC_DocType_ID());
			line.setDocumentNo(this.getDocumentNo());
			line.setAD_User_ID(this.getAD_User_ID());
			line.setAmount(amt);
			line.setChargeName("--");
			line.setDescription("GENERADO AUTOMATICAMENTE POR DOCUMENTO DE ARQUEO N° " + this.getDocumentNo());
			
			if(user.getDescription()!=null && !user.getDescription().equalsIgnoreCase("")){
				line.setApprovedBy(user.getDescription());			
			} else line.setApprovedBy(user.getName());
			
			line.saveEx();			
			
			//actualizo importes en cabezal del doc de reposicion
			replenish.setamtacumulate(replenish.getTotalAmtAcumulate()); //seteo importe acumulado
			replenish.setActualAmt(replenish.getAmtOriginal().subtract(replenish.getamtacumulate())); //seteo importe de saldo actual
	    	replenish.saveEx();
			
		}	
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
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
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		//cargo lineas de valores de billetes y monedas (para evitar trabajo del usuario)
		if(newRecord){
			
			String values [] = {"2000","1000","500","200","100","50","20","10","5","2","1"};
			
			//recorro el array y voy creando las lineas
			for(int i = 0; i < values.length; i++){
				
				BigDecimal amt = new BigDecimal(values[i]);
				
				MFFCashCountMoney line = new MFFCashCountMoney(getCtx(),0,get_TrxName());
				line.setUY_FF_CashCount_ID(this.get_ID());
				line.setAmount(amt);
				line.saveEx();				
			}			
		}		
		
		return true;
	}
	
	/***
	 * Obtiene y retorna importe total de lineas de monedas y billetes de este documento.
	 * OpenUp Ltda. Issue #1745
	 * @author Nicolas Sarlabos - 10/01/2014
	 * @see
	 * @return
	 */
	public BigDecimal getTotalAmtMoney(){
		
		String sql = "select coalesce(sum(linetotalamt),0) from uy_ff_cashcountmoney where uy_ff_cashcount_id = " + this.get_ID();
		BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);

		return amount;
	}
	
	/***
	 * Obtiene y retorna importe total de lineas de comprobantes de este documento.
	 * OpenUp Ltda. Issue #1745
	 * @author Nicolas Sarlabos - 10/01/2014
	 * @see
	 * @return
	 */
	public BigDecimal getTotalAmtAcumulate(){
		
		String sql = "select coalesce(sum(amount),0)" +
		             " from uy_ff_cashcountline" +
				     " where uy_ff_cashcount_id = " + this.get_ID() +
				     " and estado = 'Y'";
		
		BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);

		return amount;
	}
	
	/***
	 * Obtiene y retorna importe total de fondos arqueados.
	 * OpenUp Ltda. Issue #1745
	 * @author Nicolas Sarlabos - 30/01/2014
	 * @see
	 * @return
	 */
	public BigDecimal getTotalFondosArqueados(){
		
		String sql = "select coalesce(amt1 + amt2, 0)" +
		             " from uy_ff_cashcount" +
				     " where uy_ff_cashcount_id = " + this.get_ID();
		
		BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);

		return amount;
	}
	
	/***
	 * Obtiene y retorna importe total de fondos sujetos a arqueo.
	 * OpenUp Ltda. Issue #1745
	 * @author Nicolas Sarlabos - 30/01/2014
	 * @see
	 * @return
	 */
	public BigDecimal getTotalFondosSujetosArqueo(){
		
		String sql = "select coalesce(amt3 + amt4, 0)" +
		             " from uy_ff_cashcount" +
				     " where uy_ff_cashcount_id = " + this.get_ID();
		
		BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);

		return amount;
	}
	
	/**OpenUp. Nicolas Sarlabos. 18/01/2014. #1771
	 * Metodo que refresca la grilla de comprobantes en el documento de arqueo.
	 * @return
	 */
	
	public void refresh() {
		
		MFFCashCountLine cline = null;
		String sql = "";
		
		//obtengo documento de reposicion en curso para la sucursal y moneda actual
		MFFReplenish replenish = MFFReplenish.forBranchCurrency(getCtx(), this.getUY_FF_Branch_ID(), this.getC_Currency_ID(), get_TrxName());
		
		if(replenish==null) throw new AdempiereException("No se pudo obtener documento de reposicion en curso para el fondo fijo y moneda seleccionados");
		
		this.setamt3(replenish.getActualAmt().setScale(2, RoundingMode.HALF_UP)); //seteo total monedas y billetes sujetos a arqueo
		this.setamt4(replenish.getamtacumulate().setScale(2, RoundingMode.HALF_UP)); //seteo total comprobantes sujetos a arqueo
		
		BigDecimal totalFondosArq = this.getTotalFondosArqueados();
		BigDecimal totalFondosSujetosArq = this.getTotalFondosSujetosArqueo();
		
		this.setDifferenceAmt(totalFondosArq.subtract(totalFondosSujetosArq)); //seteo el sobrante/faltante
		
		List<MFFReplenishLine> lines = replenish.getLines(); //obtengo lineas de reposicion
		
		for (MFFReplenishLine rline: lines){  //recorro lineas de reposicion para ir generando las del arqueo
			
			//verifico si ya existe la linea para no repetir
			sql = "select uy_ff_cashcountline_id" + 
			      " from uy_ff_cashcountline cl" +
				  " where cl.ad_table_id = " + rline.getAD_Table_ID() +
				  " and cl.record_id = " + rline.getRecord_ID() +
				  " and cl.documentno = '" + rline.getDocumentNo() + "'" +
				  " and cl.uy_ff_cashcount_id = " + this.get_ID();
			
			int count = DB.getSQLValueEx(get_TrxName(), sql);
			
			if(count <= 0){
				
				cline = new MFFCashCountLine(getCtx(),0,get_TrxName());
				cline.setUY_FF_CashCount_ID(this.get_ID());
				cline.setLine(rline.getLine());
				cline.setDateTrx(rline.getDateTrx());
				cline.setRecord_ID(rline.getRecord_ID());
				cline.setAD_Table_ID(rline.getAD_Table_ID());
				cline.setC_DocType_ID(rline.getC_DocType_ID());
				cline.setDocumentNo(rline.getDocumentNo());
				cline.setC_BPartner_ID(rline.getC_BPartner_ID());
				cline.setChargeName(rline.getChargeName());
				cline.setAD_User_ID(rline.getAD_User_ID());
				cline.setAmount(rline.getAmount());
				cline.setDescription(rline.getDescription());
				cline.setApprovedBy(rline.getApprovedBy());
				cline.setestado(false);
				cline.saveEx();				
			}	
		}			
		
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

}
