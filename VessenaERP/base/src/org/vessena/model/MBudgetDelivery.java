/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/10/2012
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MNote;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MBudgetDelivery
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 21/10/2012
 * @see
 */
public class MBudgetDelivery extends X_UY_BudgetDelivery implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2948622939155829335L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BudgetDelivery_ID
	 * @param trxName
	 */
	public MBudgetDelivery(Properties ctx, int UY_BudgetDelivery_ID,
			String trxName) {
		super(ctx, UY_BudgetDelivery_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBudgetDelivery(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @param action
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public String prepareIt() {
		this.justPrepared = true;
		
		List<MBudgetDeliveryLine> lines = getLines();
		if (lines.size() == 0) throw new AdempiereException ("El documento no tiene lineas");
				
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public String completeIt() {
		//Re-Check
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


		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(this.getserie().equalsIgnoreCase("B")) checkManufOrderEnd();
		
		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		//OpenUp. Nicolas Sarlabos. 07/02/2013. #257
		// Se crea aviso de remito completo
		MUser user = new MUser(getCtx(), Env.getAD_User_ID(Env.getCtx()), null);
		String mensaje = "Remito N° " + this.getDocumentNo() + " completo por: ";
		if (user.get_ID() > 0) {
			mensaje += user.getName();
		} else {
			mensaje += "--";
		}

		MNote note = new MNote(getCtx(), 339, 1003166, this.get_Table_ID(), this.get_ID(), this.toString(), mensaje, get_TrxName());
		note.save();
		//Fin OpenUp.
		//OpenUp. INes Fernandez. 19/3/2015. #3611. Se impacta como última OF Generada en el Presupuesto de Venta.
		MManufOrder order = new MManufOrder(getCtx(),this.getUY_ManufOrder_ID(),get_TrxName());
		if (order!=null) {
			order.setUY_BudgetDelivery_ID(this.get_ID());
			order.saveEx();
		}
		//Fin #3611
		
		
		return DocAction.STATUS_Completed;
	}

	
	/**
	 * 
	 * OpenUp Ltda.  
	 * Description: verifica si ya se completaron todas las entregas
	 * para la orden de fabricacion y setea la fecha de finalizacion en la misma
	 * @author Nicolas Sarlabos - 16/01/2013
	 * @see
	 */
	private void checkManufOrderEnd() {
		
		String sql = "";
		
		sql = "select uy_manufline_id" +
              " from uy_budgetdeliveryline dl" +
              " where dl.uy_budgetdelivery_id=" + this.get_ID();
		int manufLineID = DB.getSQLValueEx(get_TrxName(), sql);
		
		MManufLine mLine = new MManufLine (getCtx(),manufLineID,get_TrxName());
		BigDecimal qty = mLine.getQty(); //obtengo cantidad original en la OF
		
		sql = "select coalesce(sum(dl.qty),0)" +
              " from uy_budgetdeliveryline dl" +
              " inner join uy_budgetdelivery bd on dl.uy_budgetdelivery_id=bd.uy_budgetdelivery_id" +
              " where dl.uy_manufline_id=" + manufLineID + " and (bd.docstatus='CO' or bd.uy_budgetdelivery_id=" + this.get_ID() + ")";
		BigDecimal qtyDelivered = DB.getSQLValueBDEx(get_TrxName(), sql); //obtengo cantidad total entregada, suma de otras OE y la actual
		
		if(qty.compareTo(qtyDelivered)==0){ //si se entrego el total entonces seteo la fecha final en la OF
			
			MManufOrder order = new MManufOrder (getCtx(),this.getUY_ManufOrder_ID(),get_TrxName()); //obtengo OF
			order.setDateFinish(this.getDateTrx());
			order.saveEx();			
			
		}	
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;
		
		//OpenUp. Nicolas Sarlabos. 06/02/2013. #294. Se devuelven las cantidades al anularse la orden de entrega.
		resetQtyDelivered();
		//Fin OpenUp.

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		//OpenUp. Guillermo Brust. 08/07/2013. #
		// Se crea aviso de remito anulado
		MUser user = new MUser(getCtx(), Env.getAD_User_ID(Env.getCtx()), null);
		String mensaje = "Remito N° " + this.getDocumentNo() + " anulado por: ";
		if (user.get_ID() > 0) {
			mensaje += user.getName();
		} else {
			mensaje += "--";
		}

		MNote note = new MNote(getCtx(), 339, 1003166, this.get_Table_ID(), this.get_ID(), this.toString(), mensaje, get_TrxName());
		note.save();
		//Fin OpenUp.

		return true;
	}

	/**
	 * 
	 * OpenUp Ltda. Issue #294 
	 * Description: Resta las cantidades en la orden anulada a las cantidades entregadas de la orden de fabricacion
	 * De esta manera las cantidades vuelven a quedar pendientes de entrega
	 * @author Nicolas Sarlabos - 06/02/2013 
	 * @see
	 */
	private void resetQtyDelivered() {
		
		List<MBudgetDeliveryLine> delivLines = this.getLines(); //obtengo lineas de la orden
		
		for (MBudgetDeliveryLine line: delivLines){ //recorro lineas de la orden de entrega
			
			if(line.getQty().compareTo(Env.ZERO)>0){
				
				MManufLine manLine = new MManufLine (getCtx(),line.getUY_ManufLine_ID(),get_TrxName()); //instancio linea de fabricacion
				
				manLine.setQtyDelivered(manLine.getQtyDelivered().subtract(line.getQty())); //seteo (cant. entregada-cant. anulada)
				manLine.saveEx(); //guardo la linea
								
			}
								
		}
				
	}

	@Override
	protected boolean beforeDelete() {
		
		List<MBudgetDeliveryLine> delivLines = this.getLines(); //obtengo lineas de la orden

		for (MBudgetDeliveryLine line: delivLines){ //recorro lineas de la orden de entrega

			if(line.getQty().compareTo(Env.ZERO)>0){
				
				line.beforeDelete();
			}

		}
	
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/10/2012
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return success;

		// Cargo datos del presupuesto
		if ((newRecord) || (is_ValueChanged(COLUMNNAME_UY_Budget_ID))){

			MManufOrder manord = new MManufOrder(getCtx(), this.getUY_ManufOrder_ID(), null);
			if (manord.get_ID() > 0){
				this.setC_Activity_ID(manord.getC_Activity_ID());
				this.setserie(manord.getserie());
				this.setUY_Budget_ID(manord.getUY_Budget_ID());

				// Seteo lineas de la orden con las lineas del presupuesto
				/*List<MManufLine> manlines = manord.getLines();

				for (MManufLine manline: manlines){
					MBudgetDeliveryLine dline = new MBudgetDeliveryLine(getCtx(), 0, get_TrxName());
					dline.setUY_BudgetDelivery_ID(this.get_ID());
					dline.setUY_BudgetLine_ID(manline.get_ID());
					dline.setUY_ManufLine_ID(manline.get_ID());
					dline.setDescription(manline.getDescription());
					dline.setQty(manline.getQty());
					dline.saveEx();
				}*/
			}

		}

		return true;
	}
	
	/***
	 * Obtiene y retorna lineas de esta orden de entrega
	 * OpenUp Ltda. 
	 * @author Nicolas Sarlabos - 13/12/2012
	 * @see
	 * @return
	 */
	public List<MBudgetDeliveryLine> getLines(){

		String whereClause = X_UY_BudgetDeliveryLine.COLUMNNAME_UY_BudgetDelivery_ID + "=" + this.get_ID();

		List<MBudgetDeliveryLine> lines = new Query(getCtx(), I_UY_BudgetDeliveryLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}




}
