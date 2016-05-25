/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/10/2012
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MDocType;
import org.compiere.model.MImage;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * org.openup.model - MManufOrder
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 21/10/2012
 * @see
 */
public class MManufOrder extends X_UY_ManufOrder implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 964291904660195460L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ManufOrder_ID
	 * @param trxName
	 */
	public MManufOrder(Properties ctx, int UY_ManufOrder_ID, String trxName) {
		super(ctx, UY_ManufOrder_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MManufOrder(Properties ctx, ResultSet rs, String trxName) {
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

		this.setM_Warehouse_ID(1000025);
		this.setM_Locator_ID(1000027);

		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		this.verifyBudget(); //OpenUp. Nicolas Sarlabos. #1251. 16/12/2013. Verifica que el presupuesto asociado este completo

		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		//OpenUp. Nicolas Sarlabos. 27/05/2014. #2094. Se dispara vista previa de impresion para serie B.
		if(this.getserie().equalsIgnoreCase("B")){
			
			MPInstancePara para = null;
			
			int processID = this.getPrintProcessID();
						
			if (processID <= 0) throw new AdempiereException("No se pudo obtener ID del proceso de impresion de OF");
			
			MPInstance instance = new MPInstance(Env.getCtx(), processID, 0);
			instance.saveEx();
			
			ProcessInfo pi = new ProcessInfo ("ManufOrder", processID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());	
			pi.setTable_ID(X_UY_ManufOrder.Table_ID);
			
			para = new MPInstancePara(instance, 10);
			para.setParameter("UY_ManufOrder_ID", this.get_ID());
			para.saveEx();
			
			ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
			worker.start();		
			
		}
		//Fin #2094.
		
		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		//OpenUp. INes Fernandez. 19/3/2015. #3611. Se impacta como última OF Generada en el Presupuesto de Venta.
		MBudget bud = new MBudget(getCtx(),this.getUY_Budget_ID(),get_TrxName());
		if (bud!=null){
			bud.setUY_ManufOrder_ID(this.get_ID());
			bud.saveEx();
		}
		//Fin #3611 

		return DocAction.STATUS_Completed;

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
		
		//solo permito anular si no hay remitos completos para esta OF ni facturas en estado DR o CO
		this.verifyDeliveryOrder();
		
		if(!this.validateMOrderInvoice()) throw new AdempiereException ("Imposible anular: existen facturas para esta orden de fabricacion en estado borrador o completo");
				
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);

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

		// Before reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (this.processMsg != null)
			return false;
		
		//solo permito reactivar si no hay remitos ni facturas en borrador o completos para esta OF
		if(!validateMOrder()) throw new AdempiereException ("Imposible reactivar: existen ordenes de entrega y/o facturas para esta orden de fabricacion en estado borrador o completo");
				
		// After reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (this.processMsg != null)
			return false;
		
		// Seteo estado de documento
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);
		this.setProcessed(false);

		return true;
	}
	
	/**Verifica existencia de ordenes de entrega y facturas para esta orden de fabricacion
	 * OpenUp. Nicolas Sarlabos. 22/01/2013
	 * @param cash
	 * @param doc
	 */
	public boolean validateMOrder() {
		
		String sql = "";
		int cant = 0;

		sql = "select count(uy_budgetdelivery_id)" +
				" from uy_budgetdelivery" +
				" where uy_manuforder_id=" + this.get_ID() + " and docstatus in ('CO','DR') and ad_org_id=" + this.getAD_Org_ID() + " and ad_client_id=" + this.getAD_Client_ID();
		cant = DB.getSQLValueEx(get_TrxName(), sql);

		if(cant>0) return false;

		sql = "select count(c_invoice_id)" +
				" from c_invoice" +
				" where uy_manuforder_id=" + this.get_ID() + " and docstatus in ('CO','DR') and ad_org_id=" + this.getAD_Org_ID() + " and ad_client_id=" + this.getAD_Client_ID();
		cant = DB.getSQLValueEx(get_TrxName(), sql);

		if(cant>0) return false;

		return true;

	}
	
	/**Verifica existencia de facturas para esta orden de fabricacion
	 * OpenUp. Nicolas Sarlabos. 22/01/2013
	 * @param cash
	 * @param doc
	 */
	public boolean validateMOrderInvoice() {
		
		String sql = "";
		int cant = 0;

		sql = "select count(c_invoice_id)" +
				" from c_invoice" +
				" where uy_manuforder_id=" + this.get_ID() + " and docstatus in ('CO','DR') and ad_org_id=" + this.getAD_Org_ID() + " and ad_client_id=" + this.getAD_Client_ID();
		cant = DB.getSQLValueEx(get_TrxName(), sql);

		if(cant>0) return false;

		return true;

	}
	
	/***
	 * Verifica la no existencia de ordenes de entrega en estado completo para permitir anulacion de OF
	 * y elimina las OE en estado borrador.
	 * OpenUp Ltda. Issue #3410
	 * @author Nicolas Sarlabos - 02/02/2015
	 * @see
	 * @return
	 */
	private void verifyDeliveryOrder() {	
								
		try {
			
			List<MBudgetDelivery> ordersCO = this.getDeliveryOrders("CO"); //obtengo ordenes de entrega completas de esta OF
			
			if(ordersCO.size()>0) throw new AdempiereException ("Imposible anular la OF por tener ordenes de entrega asociadas en estado completo"); 
			
			List<MBudgetDelivery> ordersDR = this.getDeliveryOrders("DR"); //obtengo ordenes de entrega en borrador de esta OF
						
			for (MBudgetDelivery order: ordersDR){
				
				MBudgetDelivery dOrder = new MBudgetDelivery(getCtx(),order.get_ID(),get_TrxName()); //obtengo orden de entrega
				
				//elimino OE en estado borrador
				DB.executeUpdateEx("delete from uy_budgetdelivery cascade where uy_budgetdelivery_id = " + dOrder.get_ID(), get_TrxName());
							
			}

		} catch (Exception e)
		{
			throw new AdempiereException (e.getMessage());
		}		
		
	}
	
	/***
	 * Obtiene y retorna las ordenes de entrega segun el estado de documento recibido para la OF actual.
	 * OpenUp Ltda. Issue #3410
	 * @author Nicolas Sarlabos - 02/02/2015
	 * @see
	 * @return
	 */
	public List<MBudgetDelivery> getDeliveryOrders(String docStatus){

		String whereClause = X_UY_BudgetDelivery.COLUMNNAME_UY_ManufOrder_ID + "=" + this.get_ID() + " and docstatus = '" + docStatus + "'";

		List<MBudgetDelivery> lines = new Query(getCtx(), I_UY_BudgetDelivery.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/**Verifica que el presupuesto asociado a la OF esté en estado completo.
	 * OpenUp. #1251. Nicolas Sarlabos. 16/12/2013
	 * @param cash
	 * @param doc
	 */
	private void verifyBudget() {

		MBudget budget = new MBudget(getCtx(),this.getUY_Budget_ID(),null);

		if(budget != null){

			if(!budget.getDocStatus().equalsIgnoreCase("CO")) throw new AdempiereException("El presupuesto '" + budget.getDocumentNo() + 
					"' asociado a esta orden no esta completo, debe completar el presupuesto antes de completar la orden");			
		}		
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
	protected boolean beforeSave(boolean newRecord) {
		
		//controlo que la suma de las cantidad de cada diseño no supere la cantidad total presupuestada
		String sql = "";
		BigDecimal qtyApproved = Env.ZERO;
		BigDecimal sumQtyDesign = this.getQtyDesign1().add(this.getQtyDesign2()).add(this.getQtyDesign3());
		
		sql = "SELECT coalesce(sum(qtyentered),0) FROM uy_budgetline WHERE uy_budget_id=" + this.getUY_Budget_ID();
		qtyApproved = DB.getSQLValueBDEx(get_TrxName(), sql); //obtengo la cantidad total del presupuesto

		if(sumQtyDesign.compareTo(qtyApproved)>0) throw new AdempiereException ("La suma de las cantidades de diseños supera el total de cantidad aprobada");
		
		//OpenUp. Nicolas Sarlabos 14/01/2013. Obtengo y cargo las observaciones finales de la ultima orden de fabricacion finalizada para este presupuesto
		if(newRecord){
			if(this.getserie().equalsIgnoreCase("B")){

				String text = "";

				sql = "SELECT observaciones FROM uy_manuforder WHERE uy_budget_id=" + this.getUY_Budget_ID() + " AND docstatus='CO' ORDER BY datefinish DESC";
				text = DB.getSQLValueStringEx(get_TrxName(), sql);

				this.setobservaciones(text);

			}
		}
		//Fin OpenUp.

		// OpenUp. Gabriel Vila. 25/02/2013. Issue #401.
		// Guardo nombre de los archivos de diseños sin path.
		if ((newRecord) || (!newRecord && is_ValueChanged(COLUMNNAME_Pic1_ID))){
			if (this.getPic1_ID() > 0){
				MImage image = new MImage(getCtx(), this.getPic1_ID(), null);
				String absoluteFileName = image.getAbsoluteFileName();
				if (absoluteFileName == null) absoluteFileName ="";
				this.set_Value("Path1", absoluteFileName);
			} else this.set_Value("Path1", null);
		}
		if ((newRecord) || (!newRecord && is_ValueChanged(COLUMNNAME_Pic2_ID))){
			if (this.getPic2_ID() > 0){
				MImage image = new MImage(getCtx(), this.getPic2_ID(), null);
				String absoluteFileName = image.getAbsoluteFileName();
				if (absoluteFileName == null) absoluteFileName ="";
				this.set_Value("Path2", absoluteFileName);
			} else this.set_Value("Path2", null);			
		}		
		if ((newRecord) || (!newRecord && is_ValueChanged(COLUMNNAME_Pic3_ID))){
			if (this.getPic3_ID() > 0){
				MImage image = new MImage(getCtx(), this.getPic3_ID(), null);
				String absoluteFileName = image.getAbsoluteFileName();
				if (absoluteFileName == null) absoluteFileName ="";
				this.set_Value("Path3", absoluteFileName);
			} else this.set_Value("Path3", null);			
		}		
		// Fin OpenUp. Issue #401.
		
		//OpenUp. Nicolas Sarlabos. 17/12/2013. #1267. Seteo check de observaciones.
		if ((newRecord) || (!newRecord && is_ValueChanged(COLUMNNAME_observaciones))){
			
			if(this.getobservaciones()!=null && !this.getobservaciones().equalsIgnoreCase("")){
				
				this.setIsDescription(true);
				
			} else this.setIsDescription(false);			
		}
		//Fin OpenUp.	
				
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return success;

		// Cargo datos del presupuesto
		if ((newRecord) || (is_ValueChanged(COLUMNNAME_UY_Budget_ID))){

			MBudget budget = new MBudget(getCtx(), this.getUY_Budget_ID(), null);
			if (budget.get_ID() > 0){
				this.setC_Activity_ID(budget.getC_Activity_ID());
				this.setserie(budget.getserie());
				this.setPic1_ID(budget.getPic1_ID());
				this.setPic2_ID(budget.getPic2_ID());
				this.setPic3_ID(budget.getPic3_ID());

				// Seteo lineas de la orden con las lineas del presupuesto
				List<MBudgetLine> budlines = budget.getLines();

				for (MBudgetLine budline: budlines){
					MManufLine mline = new MManufLine(getCtx(), 0, get_TrxName());
					mline.setUY_ManufOrder_ID(this.get_ID());
					mline.setUY_BudgetLine_ID(budline.get_ID());
					mline.setDescription(budline.getDescription());
					mline.setQty(budline.getQtyEntered()); //OpenUp. Nicolas Sarlabos. 29/10/2012. Se modifica para setear la cantidad total
					mline.saveEx();
				}
			}

			//OpenUp. Nicolas Sarlabos. 29/10/2012.
			//En el cabezal, en Cantidad Diseño 1 se setea la cantidad del total de lineas de fabricacion
			List<MManufLine> manuflines = this.getLines();
			BigDecimal qtyDesign = Env.ZERO;

			for (MManufLine manufline: manuflines){
				qtyDesign = qtyDesign.add(manufline.getQty());			
			}

			this.setQtyDesign1(qtyDesign);
			this.saveEx();
			//Fin OpenUp.

		}

		//OpenUp. Nicolas Sarlabos. 17/12/2013. #1267. Cargo las observaciones de esta OF en la grilla del presupuesto.
		if ((newRecord) || (!newRecord && is_ValueChanged(COLUMNNAME_observaciones))){			

			String sql = "";
			MManufOrderDesc desc = null;
			MBudget budget = new MBudget(getCtx(), this.getUY_Budget_ID(), get_TrxName());

			//obtengo si ya existe un registro de descripcion para esta OF y presupuesto 
			sql = "select uy_manuforderdesc_id from uy_manuforderdesc where uy_budget_id = " + this.getUY_Budget_ID() +
					" and uy_manuforder_id = " + this.get_ID();
			int ID = DB.getSQLValueEx(get_TrxName(), sql);
			
			//si existe un registro lo actualizo, si no creo uno nuevo
			if(ID > 0){

				desc = new MManufOrderDesc(getCtx(), ID, get_TrxName());				

			} else desc = new MManufOrderDesc(getCtx(), 0, get_TrxName());	
			
			//si tengo observaciones tengo que insertar o actualizar en grilla del presupuesto. Si no tengo, entonces debo eliminar la existente (si la hay).
			if(this.getobservaciones()!=null && !this.getobservaciones().equalsIgnoreCase("")){		

				desc.setUY_Budget_ID(this.getUY_Budget_ID());
				desc.setUY_ManufOrder_ID(this.get_ID());
				desc.setDescription(this.getobservaciones());
				desc.saveEx();

				budget.setIsDescription(true);
				budget.saveEx();	

			} else if(desc != null) desc.deleteEx(true);	
			
		}
		//Fin OpenUp.

		return true;
	}


	/***
	 * Obtiene y retorna lineas de esta orden de fabricacion.
	 * OpenUp Ltda. Issue #80 
	 * @author Gabriel Vila - 21/10/2012
	 * @see
	 * @return
	 */
	public List<MManufLine> getLines(){

		String whereClause = X_UY_ManufLine.COLUMNNAME_UY_ManufOrder_ID + "=" + this.get_ID();

		List<MManufLine> lines = new Query(getCtx(), I_UY_ManufLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Genera la orden de entrega a partir de la orden de fabricacion actual
	 * OpenUp 
	 * @author Nicolas Sarlabos - 11/01/2013
	 * @see
	 * @return
	 */
	public void generateDelivOrder() {
		
		try {
			
			MBudgetDelivery order = new MBudgetDelivery(getCtx(),0,get_TrxName()); //instancio nueva orden a crear
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			//comienzo a setear atributos...
			MDocType doc = MDocType.forValue(getCtx(), "shipment", get_TrxName());
			order.setC_DocType_ID(doc.get_ID());
			order.setProcessing(false);
			order.setProcessed(false);
			order.setDocStatus(DocumentEngine.STATUS_Drafted);
			order.setDocAction(DocumentEngine.ACTION_Complete);
			order.setDateTrx(today);
			order.setC_BPartner_ID(this.getC_BPartner_ID());
			order.setUY_Budget_ID(this.getUY_Budget_ID());
			order.setUY_ManufOrder_ID(this.get_ID());
			order.setC_Activity_ID(this.getC_Activity_ID());
			order.setC_BPartner_Location_ID(this.getC_BPartner_Location_ID());
			order.setserie(this.getserie());
			order.saveEx();
			
			if(order.get_ID() > 0) {
				
				this.setUY_BudgetDelivery_ID(order.get_ID());
				this.saveEx();
				//ADialog.info(0,null,"Cabezal de Orden de Entrega N° " + order.getDocumentNo() + " creado con exito");
			}
			
		}catch (Exception e) {
			throw new AdempiereException(e);
		}	
		
	}
	
	/***
	 * Obtiene y retorna el ID del proceso de impresion para OF serie B.
	 * OpenUp Ltda. Issue #2094
	 * @author Nicolas Sarlabos - 27/05/2014
	 * @see
	 * @return
	 */
	private int getPrintProcessID() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = 0;
				
		try{
						
			sql = " select ad_process_id " +
				  " from ad_process " +
				  " where lower(name) = 'uy_pprintmorder'";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			if (rs.next()){
				value = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;
	}

}
