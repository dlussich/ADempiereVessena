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
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.model.MDocType;
import org.compiere.model.MNote;
import org.compiere.model.MProduct;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;

/**
 * org.openup.model - MBudget
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 21/10/2012
 * @see
 */
public class MBudget extends X_UY_Budget implements DocAction, IDynamicReportInfo {

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(this.getserie().equalsIgnoreCase("B")){
			//si se modifica el titulo del presupuesto (nombre de producto) debo actualizar el nombre en el
			//producto y las lineas del presupuesto
			if(is_ValueChanged("workname")){
				
				int prodID = 0;
				
				List<MBudgetLine> budgetlines = this.getLines(); //obtengo lineas del presupuesto
				
				for (MBudgetLine budgetline: budgetlines){
					
					prodID = budgetline.getM_Product_ID();
					budgetline.setDescription(this.getWorkName());
					budgetline.saveEx();
										
				}
				//si hay lineas se obtuvo el ID del producto desde las mismas, entonces obtengo el producto y actualizo su nombre
				if(prodID>0){
					
					MProduct prod = new MProduct(getCtx(),prodID,get_TrxName());
					prod.setName(this.getWorkName());
					prod.saveEx();
				}			
			}
			//OpenUp. Guillermo Brust. 15/08/2013. ISSUE #
			//Se debe guardar en el campo description, el nombre del archivo del diseño1, para luego ser utilizado en las lineas de la proforma
			//como codigo del producto.
			
			this.setNombreArchivoDisenio();						
			
			//Fin OpenUp.	
		}
		
		return true;
	}
		
	/**
	 * OpenUp. Guillermo Brust. 15/08/2013. ISSUE #
	 * Guarda en campo de description el nombre del archivo del diseño1
	 * 
	 * */
	private void setNombreArchivoDisenio(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;		
		
		try{		
					
			String sql = "select imageurl from ad_image where ad_image_id in(select pic1_id from uy_budget where uy_budget_id = " + this.get_ID() + ")";				    
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		   	while (rs.next()){
		   		String imageurl = "";
		   		imageurl = rs.getString("imageurl");
		   		if(!imageurl.equals("")){
		   			int ultimaAparicion = imageurl.lastIndexOf("\\");
		   			String nombre = imageurl.substring(ultimaAparicion +1);
		   			String nombreSinExt = nombre.replaceAll(".jpg", "").replaceAll(".JPG", "").replaceAll(".PNG", "").replaceAll(".png", "");
		   			this.setDescription(nombreSinExt);	   			
		   		}				
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}


	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3678579065237008514L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Budget_ID
	 * @param trxName
	 */
	public MBudget(Properties ctx, int UY_Budget_ID, String trxName) {
		super(ctx, UY_Budget_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBudget(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
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

		//		Re-Check
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
		
		//si presupuesto NO esta aprobado, realizo aprobacion automatica para presupuestos serie B, en caso de ser posible
		if(this.getserie().equalsIgnoreCase("B") && !this.isApproved()) this.verifyApproved();
		
		//OpenUp. Nicolas Sarlabos. 28/10/2012, no permito completar presupuesto que no esta aprobado
		if(!this.isApproved()) throw new AdempiereException ("Imposible completar presupuesto NO aprobado");
		//Fin OpenUp
				
		if(this.getC_BPartner_ID()<=0) throw new AdempiereException ("Debe seleccionar cliente");
		
		this.updateManufOrder(); //OpenUp. Nicolas Sarlabos. 16/12/2013. #1251.Se actualizan atributos de la OF, en caso de que este presupuesto haya sido reactivado

		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		//OpenUp. Nicolas Sarlabos. 13/05/2013. #823
		this.setDateApproved(new Timestamp(System.currentTimeMillis()));
		//Fin OpenUp.

		// Se crea aviso de presupuesto aprobado
		MUser user = new MUser(getCtx(), Env.getAD_User_ID(Env.getCtx()), null);
		String mensaje = "Presupuesto N° " + this.getDocumentNo() + " aprobado por: ";
		if (user.get_ID() > 0) {
			mensaje += user.getName();
		} else {
			mensaje += "--";
		}

		MNote note = new MNote(getCtx(), 339, 1003166, this.get_Table_ID(), this.get_ID(), this.toString(), mensaje, get_TrxName());
		note.save();
		// Fin creacion de aviso
				
		return DocAction.STATUS_Completed;

	}

	/***
	 * Aprueba automaticamente el presupuesto serie B en caso de que tenga un unico precio.
	 * OpenUp Ltda. Issue #2094
	 * @author Nicolas Sarlabos - 07/05/2014
	 * @see
	 * @return
	 */	
	private void verifyApproved() {

		try {

			String sql = "";

			sql = "select uy_budgetline_id" +
					" from uy_budgetline" +
					" where uy_budget_id = " + this.get_ID();

			int lineID = DB.getSQLValueEx(get_TrxName(), sql);

			if(lineID > 0){

				MBudgetLine line = new MBudgetLine(getCtx(),lineID,get_TrxName());

				if(line.getamt1().compareTo(Env.ZERO) > 0 && line.getamt2().compareTo(Env.ZERO) == 0 && line.getamt3().compareTo(Env.ZERO) == 0){

					DB.executeUpdateEx("update uy_budgetline set isapprovedqty1 = 'Y' where uy_budgetline_id = " + line.get_ID(), get_TrxName());
					DB.executeUpdateEx("update uy_budgetline set qtyentered = qty1 where uy_budgetline_id = " + line.get_ID(), get_TrxName());
					this.setIsApproved(true);				

				} else if (line.getamt2().compareTo(Env.ZERO) > 0 && line.getamt1().compareTo(Env.ZERO) == 0 && line.getamt3().compareTo(Env.ZERO) == 0){

					DB.executeUpdateEx("update uy_budgetline set isapprovedqty2 = 'Y' where uy_budgetline_id = " + line.get_ID(), get_TrxName());
					DB.executeUpdateEx("update uy_budgetline set qtyentered = qty2 where uy_budgetline_id = " + line.get_ID(), get_TrxName());
					this.setIsApproved(true);			

				} else if (line.getamt3().compareTo(Env.ZERO) > 0 && line.getamt1().compareTo(Env.ZERO) == 0 && line.getamt2().compareTo(Env.ZERO) == 0){

					DB.executeUpdateEx("update uy_budgetline set isapprovedqty3 = 'Y' where uy_budgetline_id = " + line.get_ID(), get_TrxName());
					DB.executeUpdateEx("update uy_budgetline set qtyentered = qty3 where uy_budgetline_id = " + line.get_ID(), get_TrxName());
					this.setIsApproved(true);					

				}			

			}	

		} catch (Exception e) {
			throw new AdempiereException (e.getMessage());
		}		
		
	}

	/***
	 * Actualiza campos en la OF asociada cuando este presupuesto se completa luego de reactivarlo.
	 * OpenUp Ltda. Issue #1251
	 * @author Nicolas Sarlabos - 16/12/2013
	 * @see
	 * @return
	 */
	private void updateManufOrder() {
		
		BigDecimal qtyDesign = Env.ZERO;
		String sql = "";

		sql = "select uy_manuforder_id from uy_manuforder where docstatus <> 'VO' and uy_budget_id = " + this.get_ID() + " and ad_client_id = " + 
				this.getAD_Client_ID() + " and ad_org_id = " + this.getAD_Org_ID();
		int orderID = DB.getSQLValueEx(get_TrxName(), sql);

		if(orderID > 0){
			
			MManufOrder order = new MManufOrder(getCtx(),orderID,get_TrxName()); //obtengo OF asociada al presupuesto
			
			if(order != null){
			
				//seteo atributos en las lineas de la OF
				List<MBudgetLine> lines = this.getLines();
				
				for (MBudgetLine bLine: lines){
					
					qtyDesign = qtyDesign.add(bLine.getQtyEntered());

					sql = "update uy_manufline set qty = " + bLine.getQtyEntered() + ", description = '" + bLine.getDescription() + "' where " +
							" m_product_id = " + bLine.getM_Product_ID() + " and uy_budgetline_id = " + bLine.getUY_BudgetLine_ID() + " and ad_client_id = " + 
							this.getAD_Client_ID() + " and ad_org_id = " + this.getAD_Org_ID();
					DB.executeUpdateEx(sql, get_TrxName());					

				}
				
				//seteo atributos en el cabezal
				order.setPic1_ID(this.getPic1_ID());
				order.setPic2_ID(this.getPic2_ID());
				order.setPic3_ID(this.getPic3_ID());	
				//OpenUp. Nicolas Sarlabos. 05/03/2014. #1926. Solo se setea cantidad total al diseño 1 si la suma total es 0
				if((order.getQtyDesign1().add(order.getQtyDesign2()).add(order.getQtyDesign3())).compareTo(Env.ZERO)==0) order.setQtyDesign1(qtyDesign);
				//Fin #1926.
				order.saveEx();				
				
			}			
			
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
		
		this.verifyManufOrder(); //OpenUp. Nicolas Sarlabos. 12/06/2013. #990.

		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		//OpenUp. Guillermo Brust. 08/07/2013. #
		//Se crea aviso de presupuesto anulado
		MUser user = new MUser(getCtx(), Env.getAD_User_ID(Env.getCtx()), null);
		String mensaje = "Presupuesto N° " + this.getDocumentNo() + " anulado por: ";
		if (user.get_ID() > 0) {
			mensaje += user.getName();
		} else {
			mensaje += "--";
		}

		MNote note = new MNote(getCtx(), 339, 1003166, this.get_Table_ID(), this.get_ID(), this.toString(), mensaje, get_TrxName());
		note.save();
		// Fin creacion de aviso

		return true;
	}

	/***
	 * Verifica la no existencia de ordenes de fabricacion en estado completo para permitir anulacion de presupuesto
	 * y elimina las OF en estado borrador.
	 * OpenUp Ltda. Issue #990
	 * @author Nicolas Sarlabos - 12/06/2013
	 * @see
	 * @return
	 */
	private void verifyManufOrder() {	
								
		try {
			
			List<MManufOrder> ordersCO = this.getManufOrders("CO"); //obtengo ordenes de fabricacion completas de este presupuesto
			
			if(ordersCO.size()>0) throw new AdempiereException ("Imposible anular presupuesto por tener ordenes de fabricacion asociadas en estado completo"); 
			
			List<MManufOrder> ordersDR = this.getManufOrders("DR"); //obtengo ordenes de fabricacion en borrador de este presupuesto
						
			for (MManufOrder order: ordersDR){
				
				MManufOrder mOrder = new MManufOrder (getCtx(),order.get_ID(),get_TrxName()); //obtengo orden de fabricacion
				
				//elimino OF en estado borrador
				DB.executeUpdateEx("delete from uy_manuforder cascade where uy_manuforder_id = " + mOrder.get_ID(), get_TrxName());
							
			}

		} catch (Exception e)
		{
			throw new AdempiereException (e.getMessage());
		}		
		
	}	
	
	/***
	 * Obtiene y retorna las ordenes de fabricacion segun el estado de documento recibido.
	 * OpenUp Ltda. Issue #990
	 * @author Nicolas Sarlabos - 12/06/2013
	 * @see
	 * @return
	 */
	public List<MManufOrder> getManufOrders(String docStatus){

		String whereClause = X_UY_ManufOrder.COLUMNNAME_UY_Budget_ID + "=" + this.get_ID() + " and docstatus = '" + docStatus + "'";

		List<MManufOrder> lines = new Query(getCtx(), I_UY_ManufOrder.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
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

	/***
	 * Metodo que reactiva un presupuesto mediante validaciones.
	 * OpenUp Ltda. Issue #1251
	 * @author Nicolas Sarlabos - 16/12/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reActivateIt() {			

			// Before reActivate
			this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
			if (this.processMsg != null)
				return false;			

			List<MManufOrder> ordersCO = this.getManufOrders("CO"); //obtengo ordenes de fabricacion completas de este presupuesto
			List<MManufOrder> ordersDR = this.getManufOrders("DR"); //obtengo ordenes de fabricacion en borrador de este presupuesto
			int totalOrders = ordersDR.size() + ordersCO.size(); //obtengo total de ordenes de fabricacion

			//si no hay OF asociadas permito reactivar
			if(totalOrders <= 0){

				this.setProcessed(false);
				this.setDocStatus(DocAction.STATUS_InProgress);
				this.setDocAction(DocAction.ACTION_Complete);	
				
			}
			//si tiene mas de 1 Of asociada no permito reactivar
			//si pasa de este punto entonces solo existe 1 OF asociada
			if(totalOrders > 1) throw new AdempiereException ("Imposible reactivar, el presupuesto tiene asociada mas de 1 orden de fabricacion");
						
			if(ordersCO.size() > 0){

				for (MManufOrder order: ordersCO){

					MManufOrder mOrder = new MManufOrder (getCtx(),order.get_ID(),get_TrxName()); //obtengo orden de fabricacion

					if(!mOrder.validateMOrder()) throw new AdempiereException("Imposible reactivar, existen ordenes de entrega y/o facturas asociadas a este presupuesto");
										
					try{
						//reactivo la OF
						if (!mOrder.processIt(DocumentEngine.ACTION_ReActivate)){
							throw new AdempiereException(mOrder.getProcessMsg());
						}

					} catch (Exception e){
						throw new AdempiereException(e);
					}

					mOrder.saveEx();
					
					//reactivo el presupuesto
					this.setProcessed(false);
					this.setDocStatus(DocAction.STATUS_InProgress);
					this.setDocAction(DocAction.ACTION_Complete);
													
				}
			}
			
			//si la OF esta en borrador entonces reactivo directamente el presupuesto
			if(ordersDR.size() > 0){
				
				//reactivo el presupuesto
				this.setProcessed(false);
				this.setDocStatus(DocAction.STATUS_InProgress);
				this.setDocAction(DocAction.ACTION_Complete);
									
			}
			
			// After reActivate
			this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
			if (this.processMsg != null)
				return false;	

		return true;
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

	/***
	 * Obtiene y retorna lineas de este presupuesto.
	 * OpenUp Ltda. Issue #79 
	 * @author Gabriel Vila - 21/10/2012
	 * @see
	 * @return
	 */
	public List<MBudgetLine> getLines(){

		String whereClause = X_UY_BudgetLine.COLUMNNAME_UY_Budget_ID + "=" + this.get_ID() + " AND " + X_UY_BudgetLine.COLUMNNAME_QtyEntered + " > 0";

		List<MBudgetLine> lines = new Query(getCtx(), I_UY_BudgetLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lineas de materiales este presupuesto.
	 * OpenUp Ltda.
	 * @author Nicolas Sarlabos - 10/12/2012
	 * @see
	 * @return
	 */
	public List<MBudgetBOM> getBOMLines(){

		String whereClause = X_UY_BudgetBOM.COLUMNNAME_UY_Budget_ID + "=" + this.get_ID();

		List<MBudgetBOM> lines = new Query(getCtx(), I_UY_BudgetBOM.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}


	/***
	 * Obtiene y retorna BOM de este presupuesto.
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 22/10/2012
	 * @see
	 * @return
	 */
	public List<MBudgetBOM> getBOM(){

		String whereClause = X_UY_BudgetBOM.COLUMNNAME_UY_Budget_ID + "=" + this.get_ID();

		List<MBudgetBOM> lines = new Query(getCtx(), I_UY_BudgetBOM.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lineas de observaciones de OF de este presupuesto.
	 * OpenUp Ltda. Issue #1267 
	 * @author Nicolas Sarlabos - 17/12/2013
	 * @see
	 * @return
	 */
	public List<MManufOrderDesc> getDescLines(){

		String whereClause = X_UY_ManufOrderDesc.COLUMNNAME_UY_Budget_ID + "=" + this.get_ID();

		List<MManufOrderDesc> lines = new Query(getCtx(), I_UY_ManufOrderDesc.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}


	/***
	 * Metodo que clona totalmente un presupuesto
	 * OpenUp 
	 * @author Nicolas Sarlabos - 10/12/2012
	 * @see
	 * @return
	 */
	public void cloneBudget() {
		
		try{
			
			//OpenUp. Nicolas Sarlabos. 02/02/2015. #3489.
			//si el presupuesto tiene observaciones aviso para proseguir la clonacion
			if(this.isDescription()){
				
				boolean question = ADialog.ask(0, null, "El presupuesto actual tiene OBSERVACIONES. Indique si desea continuar");
				
				if(!question) return;		
				
			}			
			//Fin #3489.
			
			MBudget newBudget = new MBudget(getCtx(),0,get_TrxName()); //instancio nuevo presupuesto a crear

			//comienzo a setear atributos...
			newBudget.setAD_User_ID(this.getAD_User_ID());
			newBudget.setC_DocType_ID(this.getC_DocType_ID());
			newBudget.setserie(this.getserie());
			newBudget.setWorkName(this.getWorkName());
			newBudget.setDateTrx(new Timestamp(System.currentTimeMillis()));
			newBudget.setDescription(this.getDescription());
			newBudget.setSalesRep_ID(this.getSalesRep_ID());
			newBudget.setC_Currency_ID(this.getC_Currency_ID());
			newBudget.setC_PaymentTerm_ID(this.getC_PaymentTerm_ID());
			newBudget.setM_PriceList_ID(this.getM_PriceList_ID());
			newBudget.setC_Activity_ID(this.getC_Activity_ID());
			newBudget.setPic1_ID(this.getPic1_ID());
			newBudget.setPic2_ID(this.getPic2_ID());
			newBudget.setPic3_ID(this.getPic3_ID());
			newBudget.setheader_text(this.getheader_text());
			newBudget.setfoot_text(this.getfoot_text());
			newBudget.setDateApproved(new Timestamp(System.currentTimeMillis()));
			newBudget.setdescripcion(this.getdescripcion());
			newBudget.setPriorityRule(false);
			newBudget.setProcessing(false);
			newBudget.setProcessed(false);
			newBudget.setDocStatus(DocumentEngine.STATUS_Drafted);
			newBudget.setDocAction(DocumentEngine.ACTION_Complete);
			newBudget.saveEx(); //guardo el nuevo cabezal

			//si se grabo el cabezal procedo a crear las lineas y materiales
			if(newBudget.get_ID()>0){

				List<MBudgetLine> budlines = this.getLines();

				for (MBudgetLine budline: budlines){

					MBudgetLine line = new MBudgetLine(getCtx(),0,get_TrxName()); //instancio nueva linea

					line.setUY_Budget_ID(newBudget.get_ID());
					line.setDescription(budline.getDescription());
					line.setqty1(budline.getqty1());
					line.setqty2(budline.getqty2());
					line.setqty3(budline.getqty3());
					line.setprice1(budline.getprice1());
					line.setprice2(budline.getprice2());
					line.setprice3(budline.getprice3());
					line.setamt1(budline.getamt1());
					line.setamt2(budline.getamt2());
					line.setamt3(budline.getamt3());
					line.setDiscount(budline.getDiscount());
					line.setIsApprovedQty1(budline.isApprovedQty1());
					line.setIsApprovedQty2(budline.isApprovedQty2());
					line.setIsApprovedQty3(budline.isApprovedQty3());
					line.setQtyEntered(budline.getQtyEntered());
					line.setM_Product_ID(budline.getM_Product_ID());
					line.setDetailInfo(budline.getDetailInfo());
					line.setconcept(budline.getconcept());
					line.saveEx();

				}

				List<MBudgetBOM> bomlines = this.getBOMLines();

				for (MBudgetBOM bomline: bomlines){

					MBudgetBOM bom = new MBudgetBOM(getCtx(),0,get_TrxName());

					bom.setUY_Budget_ID(newBudget.get_ID());
					bom.setmaterials(bomline.getmaterials());
					bom.saveEx();

				}

			}
			//OpenUp. Nicolas Sarlabos. 20/01/2013. Guardo el ID del ultimo presupuesto clonado
			this.setUY_BudgetCloned_ID(newBudget.get_ID());
			this.saveEx();
			//Fin OpenUp.
			
			// OpenUp. Gabriel Vila. 25/02/2013. Issue #396.
			// Clono adjuntos
			if (this.getAttachment() != null){
				this.getAttachment().clone(I_UY_Budget.Table_ID, newBudget.get_ID());
			}
			// Fin OpenUp. Issue #396.					
			
			Trx trx = Trx.get(get_TrxName(), false);
			if (trx != null){
				trx.commit(true);
			}
			
			ADialog.info(0,null,"Presupuesto N° " + newBudget.getDocumentNo() + " creado con exito");
			
			//OpenUp. Nicolas Sarlabos. 07/05/2014. #2094. Se muestra presupuesto clonado.
			// Instancio modelo de tabla
			MTable table = new MTable(Env.getCtx(), I_UY_Budget.Table_ID, null);
			
			String whereClause = table.getTableName() + "_ID =" + newBudget.get_ID();
			AWindow poFrame = new AWindow(null);
			MQuery query = new MQuery(table.getTableName());
			query.addRestriction(whereClause);
			
			int adWindowID = table.getAD_Window_ID();
				
			boolean ok = poFrame.initWindow(adWindowID, query);
			if (ok){
				poFrame.pack(); 
				AEnv.showCenterScreen(poFrame);
				poFrame.toFront();
			}			
			//Fin OpenUp.
			
		} catch (Exception e) {
			throw new AdempiereException(e);

		} 

	}
	
	/***
	 * Genera la orden de fabricacion a partir del presupuesto actual
	 * OpenUp 
	 * @author Nicolas Sarlabos - 09/01/2013
	 * @see
	 * @return
	 */
	public void generateManufOrder() {
		
		try {
			
			MManufOrder order = new MManufOrder(getCtx(),0,get_TrxName()); //instancio nueva orden a crear
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			//comienzo a setear atributos...
			MDocType doc = MDocType.forValue(getCtx(), "manuforder", get_TrxName());
			order.setC_DocType_ID(doc.get_ID());
			order.setProcessing(false);
			order.setProcessed(false);
			order.setDocStatus(DocumentEngine.STATUS_Drafted);
			order.setDocAction(DocumentEngine.ACTION_Complete);
			order.setDateTrx(today);
			order.setC_BPartner_ID(this.getC_BPartner_ID());
			order.setUY_Budget_ID(this.get_ID());
			order.setC_Activity_ID(this.getC_Activity_ID());
			order.setC_BPartner_Location_ID(this.getC_BPartner_Location_ID());
			order.setserie(this.getserie());
			order.setPic1_ID(this.getPic1_ID());
			order.setPic2_ID(this.getPic2_ID());
			order.setPic3_ID(this.getPic3_ID());
			order.saveEx();
			
			if(order.get_ID() > 0) {
				
				this.setUY_ManufOrder_ID(order.get_ID());
				this.saveEx();
				//ADialog.info(0,null,"Orden de Fabricacion N° " + order.getDocumentNo() + " creada con exito");
			}
			
			// OpenUp. Gabriel Vila. 25/02/2013. Issue #401.
			// Adjuntos del presupuesto se copian a la orden de fabricacion
			if (this.getAttachment() != null){
				this.getAttachment().clone(I_UY_ManufOrder.Table_ID, order.get_ID());
			}
			// Fin OpenUp. Issue #401.
			
			//OpenUp. Nicolas Sarlabos. 07/05/2014. #2094. Se muestra OF generada.
			Trx trx = Trx.get(get_TrxName(), false);
			if (trx != null){
				trx.commit(true);
			}
			
			MTable table = new MTable(Env.getCtx(), I_UY_ManufOrder.Table_ID, null);
			
			String whereClause = table.getTableName() + "_ID =" + order.get_ID();
			AWindow poFrame = new AWindow(null);
			MQuery query = new MQuery(table.getTableName());
			query.addRestriction(whereClause);
			
			int adWindowID = table.getAD_Window_ID();
				
			boolean ok = poFrame.initWindow(adWindowID, query);
			if (ok){
				poFrame.pack(); 
				AEnv.showCenterScreen(poFrame);
				poFrame.toFront();
			}			
			//Fin OpenUp.

			
		}catch (Exception e) {
			throw new AdempiereException(e);
		}	
		
	}

	@Override
	public String getReportFileName() {
		
		return "Presupuesto " + this.getserie() + this.getDocumentNo() + " - " + this.getWorkName();
	}
	
	/***
	 * Obtiene y retorna un presupuesto segun el numero de documento recibido
	 * OpenUp Ltda. Issue #1188
	 * @author Nicolas Sarlabos - 14/08/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MBudget forDocumentNo(Properties ctx, String docNo, String trxName){
		
		String whereClause = X_UY_Budget.COLUMNNAME_DocumentNo + "='" + docNo + "'";
		
		MBudget budget = new Query(ctx, I_UY_Budget.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return budget;
	}
	
	/***
	 * Muestra un aviso con las ultimas observaciones cargadas en una OF para el presupuesto clonado
	 * OpenUp 
	 * @author Nicolas Sarlabos - 10/12/2012
	 * @see
	 * @return
	 */
	/*public void showManufNotes(){
		
		String sql = "",text = "";
		
		sql = "SELECT observaciones FROM Uy_manuforder WHERE uy_budget_id=" + this.get_ID() + " ORDER BY updated DESC";
		text = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		if(text!=null && text!=""){
			
			ADialog.info(0,null,"Observaciones: " + text);
			
		}
		
		
	}*/

}
