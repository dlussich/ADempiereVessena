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
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MTRBudget extends X_UY_TR_Budget implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2629426353791584881L;

	/**
	 * @param ctx
	 * @param UY_TR_Budget_ID
	 * @param trxName
	 */
	public MTRBudget(Properties ctx, int UY_TR_Budget_ID, String trxName) {
		super(ctx, UY_TR_Budget_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRBudget(Properties ctx, ResultSet rs, String trxName) {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		String value = "";
		MTRBudgetParameters param = MTRBudgetParameters.forClient(getCtx(), this.getAD_Org_ID(), get_TrxName());
		
		if(this.getDueDate().before(this.getDateTrx())) throw new AdempiereException("La fecha de vencimiento no puede ser menor a la fecha de transaccion");
		
		if(this.getUY_TR_Border_ID_1()>0 && this.getUY_TR_Border_ID()<=0) throw new AdempiereException("No puede seleccionar Frontera 2 sin indicar Frontera 1");
		
		//seteo el campo de GASTOS ADICIONALES
		if(newRecord || is_ValueChanged("ValoremPercentage") || is_ValueChanged("IncotermType")){

			if(this.getValoremPercentage().compareTo(Env.ZERO)>0){

				if(this.getIncotermType()!=null){

					value = "Ad-Valorem: " + this.getValoremPercentage().setScale(2, RoundingMode.HALF_UP) + " % del valor " + this.getIncotermType() + " de la mercadería.";

				} else value = "Ad-Valorem: " + this.getValoremPercentage().setScale(2, RoundingMode.HALF_UP) + " % del valor de la mercadería.";
										
			} else value = "Ad-Valorem: Incluido";
						
			if(param!=null && param.get_ID()>0){
				
				if(param.getExpense()!=null && !param.getExpense().equalsIgnoreCase("")){
					
					if(!value.equalsIgnoreCase("")){
						
						value = value + "\n" + param.getExpense();
						
					} else value = param.getExpense();					
					
				}
									
			}
			
			 this.setExpense(value);
			
		}
		
		//seteo el campo de FORMA DE PAGO
		if(newRecord || is_ValueChanged("C_PaymentTerm_ID") || is_ValueChanged("C_Currency_ID")){
			
			value = "";
			
			if(param!=null && param.get_ID()>0){
				
				if(param.getPaymentTermNote()!=null && !param.getPaymentTermNote().equalsIgnoreCase("")) value += param.getPaymentTermNote();			
				
			}
			
			MPaymentTerm term = (MPaymentTerm)this.getC_PaymentTerm();
			MCurrency cur = (MCurrency)this.getC_Currency();
			
			value += " en " + cur.getDescription() + ", " + term.getName() + ".";	
			
			this.setPaymentTermNote(value);
			
		}	
		
		//seteo las notas
		if(newRecord || is_ValueChanged("uy_tr_border_id") || is_ValueChanged("uy_tr_border_id_1")){
			
			value = "";
			
			if(this.getUY_TR_Border_ID()>0){
				
				MTRBorder border = (MTRBorder)this.getUY_TR_Border();
				
				value = "1) Con liberación de la mercadería en " + border.getName();				
				
			}			
			
			if(this.getUY_TR_Border_ID_1()>0){
				
				MTRBorder border2 = new MTRBorder(getCtx(),this.getUY_TR_Border_ID_1(),get_TrxName());
				
				if(value.equalsIgnoreCase("")){
					
					value = "1) Con liberación de la mercadería en " + border2.getName();
					
				} else value = value + " o " + border2.getName();				
				
			}
					
			if(param!=null && param.get_ID()>0){
				
				if(param.getNote()!=null && !param.getNote().equalsIgnoreCase("")) value = value + "\n" + param.getNote();								
			}
			
			this.setNote(value);			
		}
		
		return true;
	}
	
	/***
	 * Obtiene y retorna lineas de esta cotizacion.
	 * OpenUp Ltda. Issue #3399
	 * @author Nicolas Sarlabos - 06/02/2015
	 * @see
	 * @return
	 */
	public List<MTRBudgetLine> getLines(){

		String whereClause = X_UY_TR_BudgetLine.COLUMNNAME_UY_TR_Budget_ID + "=" + this.get_ID();

		List<MTRBudgetLine> lines = new Query(getCtx(), I_UY_TR_BudgetLine.Table_Name, whereClause, get_TrxName())
		.setOrderBy(X_UY_TR_BudgetLine.COLUMNNAME_UY_TR_BudgetLine_ID)
		.list();

		return lines;
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

		//no se permite completar sin que la cotizacion tenga lineas, y que exista al menos 1 linea aprobada
		List<MTRBudgetLine> bLines = this.getLines();//obtengo lineas de cotizacion
		
		if(bLines.size()<=0) throw new AdempiereException("Imposible completar cotizacion sin lineas");
		
		String sql = "select uy_tr_budgetline_id" +
		             " from uy_tr_budgetline" +
				     " where uy_tr_budget_id = " + this.get_ID() +
				     " and isapproved = 'Y'";
		
		int lineID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(lineID <= 0) throw new AdempiereException("La cotizacion debe tener al menos una linea APROBADA para poder completar");
		
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
	public boolean voidIt() {
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		//verifico que no exista expediente asociado a esta cotizacion
		MTRTrip trip = MTRTrip.forBudget(getCtx(), this.get_ID(), get_TrxName());
		
		if(trip!=null) throw new AdempiereException("Imposible anular la cotizacion por estar asociada al expediente N° " + trip.getDocumentNo());
		
		if(this.getUY_TR_BudgetReason_ID()<=0) throw new AdempiereException("Debe seleccionar un motivo de NO APROBACION para anular esta cotizacion");
		
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
		processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (processMsg != null)
			return false;

		// After reActivate
		processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (processMsg != null)
			return false;

		// Seteo estado de documento
		this.setProcessed(false);
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);

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
		// TODO Auto-generated method stub
		return null;
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
