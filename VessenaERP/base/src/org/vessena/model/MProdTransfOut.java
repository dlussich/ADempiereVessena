
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.MUOMConversion;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.eevolution.exceptions.BOMExpiredException;
import org.eevolution.model.MPPOrder;
import org.eevolution.model.MPPOrderBOM;
import org.eevolution.model.MPPOrderBOMLine;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;

/**
 * org.openup.OpenUp371 - MProdTransf
 * OpenUp Ltda. Issue #4126 
 * Description: Clase para el output de las ordenes de produccion de transformación.
 * @author INes Fernandez - 05/2015
  */
public class MProdTransfOut extends X_UY_ProdTransfOut {

	
	private static final long serialVersionUID = -6602859513403459653L;
	private MProdTransf header = null;// new MProdTransf(getCtx(), getUY_ProdTransf_ID(), get_TrxName());

	
	
	/**
	  * @param ctx
	 * @param UY_ProdTransfOut_ID
	 * @param trxName
	 */
	public MProdTransfOut(Properties ctx, int UY_ProdTransfOut_ID,
			String trxName) {
		super(ctx, UY_ProdTransfOut_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProdTransfOut(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//ctrol de lineas de output para ordenes de tipo Single Output
		header =  new MProdTransf(getCtx(), getUY_ProdTransf_ID(), get_TrxName());

		MDocType target = new MDocType(getCtx(), header.getC_DocTypeTarget_ID(), get_TrxName());
		
		if(header!=null && (!header.isMultipleOutput() /*&& !target.getValue().equalsIgnoreCase("ordenreb")*/)){
			if(header.getOutputLines(" and uy_prodtransfout_id <> " + this.get_ID()).length>0) 
				throw new AdempiereException("Este tipo de orden sólo admite una linea de output ");
			//if(this.getPP_Product_BOM_ID()<=0) 
				//throw new AdempiereException ("La lista de materiales es obligatoria para este tipo de orden");
		}
		
		//validaciones para lineas de ordenes multiple output
		if(header!=null && header.isMultipleOutput()){
			
			//if(newRecord || (!newRecord && (is_ValueChanged("M_Product_ID")))){
				//this.validateMOLine();
			//}
			
			//siempre seteo qtyordered
 			BigDecimal QtyOrdered = MUOMConversion.convertProductFrom (getCtx(), this.getM_Product_ID(),this.getC_UOM_ID(), this.getQtyEntered());
 			if (QtyOrdered == null)
 				QtyOrdered = this.getQtyEntered();
 			
 			boolean conversion = this.getQtyEntered().compareTo(QtyOrdered) != 0;
 			
 			log.fine("qty - UOM=" + this.getC_UOM_ID() 
					+ ", QtyEntered=" + this.getQtyEntered()
 					+ " -> " + conversion 
 					+ " QtyOrdered=" + QtyOrdered);
 			
 			this.setQtyOrdered(QtyOrdered);
			
		}
		
		// If UOM not filled, get it from Product
		if (getC_UOM_ID() <= 0 && getM_Product_ID() > 0)
		{
			setC_UOM_ID(getM_Product().getC_UOM_ID());
		}
		
		if(this.get_ValueAsInt("pp_product_BOM_ID")==0){
			if(getMPPProductBOM()!=null)
			{
				//set_ValueOfColumnReturningBoolean("pp_product_BOM_ID", getMPPProductBOM().get_ID());
				set_ValueOfColumn("pp_product_BOM_ID", getMPPProductBOM().get_ID());
			}
		}
		//controla que la sumatoria de kg de prods de output no supere los kg del input
		if(header!=null && header.isMultipleOutput()){
//			MProdTransfOut[] outputLines =header.getOutputLines("");
//			BigDecimal outputKG =BigDecimal.ZERO;
//			for(MProdTransfOut aux : outputLines){
//				outputKG = outputKG.add(aux.getQtyOrdered());
//			}
//			if(header.getQtyOrdered().compareTo(outputKG.add(this.getQtyOrdered()))<0){
//				throw new AdempiereException("Atencion: los kg de producto de salida superan los de producto de entrada");
//			}
			
			/*if(header.getQtyOrdered().compareTo(this.totalOrdered(header).add(this.getQtyOrdered()))<0){
				throw new AdempiereException("Atencion: los kg de producto de salida superan los de producto de entrada");
			}*/
					
		}		
		
		return true;
	}
	
	public BigDecimal totalOrdered (MProdTransf header) {
		
		String sql = "select coalesce(sum(qtyordered),0)" +
		             " from uy_prodtransfout" +
				     " where uy_prodtransfout_id <> " + this.get_ID() +
				     " and uy_prodtransf_id = " + header.get_ID();
		
		BigDecimal qty = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		return qty;
		
		
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
		{
			return false;
		}
		
		header =  new MProdTransf(getCtx(), getUY_ProdTransf_ID(), get_TrxName());
		

		if(MProdTransf.DOCACTION_Close.equals(header.getDocAction())
				|| MProdTransf.DOCACTION_Void.equals(header.getDocAction()))
		{
			return true;
		}
		
		/*if (!newRecord)
		{
			return success;
		}*/
		
		//explotion();		
		/*MDocType target = new MDocType(getCtx(), header.getC_DocTypeTarget_ID(), get_TrxName());
		if(!header.isMultipleOutput() && !target.getValue().equalsIgnoreCase("ordenreb")){
			
			if(newRecord || is_ValueChanged(MPPOrder.COLUMNNAME_QtyEntered) || is_ValueChanged(MPPOrder.COLUMNNAME_M_Product_ID)
					|| (is_ValueChanged(COLUMNNAME_PP_Product_BOM_ID))) {
				
				DB.executeUpdateEx("delete from pp_order_bom where uy_prodtransfout_id = " + this.get_ID(), get_TrxName());
				
				explotion();
			}
		}*/
		return true;
	} //	beforeSave
	
	
	/**
	 * Create PP_Order_BOM from UY_ProdTransfOut.
	 */
	/*private void explotion()
	{
		
		MLocator locator = null;
		MWarehouse warehouse = null;	
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		// Create BOM Head
		final MPPProductBOM PP_Product_BOM = MPPProductBOM.get(getCtx(), getPP_Product_BOM_ID()); 
		// Product from Order should be same as product from BOM - teo_sarca [ 2817870 ] 
		
		if(PP_Product_BOM==null) throw new AdempiereException("El producto no tiene lista de materiales");
		
		if (this.getM_Product_ID() != PP_Product_BOM.getM_Product_ID())
		{
			throw new AdempiereException("@NotMatch@ @PP_Product_BOM_ID@ , @M_Product_ID@");
		}
		// Product BOM Configuration should be verified - teo_sarca [ 2817870 ]
		final MProduct product = MProduct.get(getCtx(), PP_Product_BOM.getM_Product_ID());
		if (!product.isVerified())
		{// TODO: translate
			throw new AdempiereException("Product BOM Configuration not verified. Please verify the product first - "+product.getValue()); // TODO: translate
		}
		
		if (PP_Product_BOM.isValidFromTo(header.getDateOrdered()))
		{
			MPPOrderBOM PP_Order_BOM = new MPPOrderBOM(PP_Product_BOM, this.getUY_ProdTransf_ID(), get_TrxName());//VER*******
			PP_Order_BOM.setAD_Org_ID(getAD_Org_ID());
			PP_Order_BOM.set_ValueOfColumnReturningBoolean("UY_ProdTransfOut_ID", this.get_ID());
			PP_Order_BOM.saveEx();		
			
			for (MPPProductBOMLine PP_Product_BOMline : PP_Product_BOM.getLines(true))
			{
				//OBS: valida contra la fecha de la orden
				if (PP_Product_BOMline.isValidFromTo(header.getDateOrdered())) 
				{
					// OpenUp. Nicolas Garcia. 16/08/2011. Issue #824.
					// El almacen sera = al almacen por defecto para esa
					// categoria de producto
					MProduct productLine = (MProduct) PP_Product_BOMline.getM_Product();
					
					//si el producto es critico, se toma el almacen elegido en el cabezal de la orden y se verifica stock disponible
					if(PP_Product_BOMline.isCritical()){
						
						warehouse = (MWarehouse)header.getM_Warehouse();
						locator = (MLocator)warehouse.getDefaultLocator();
						
						BigDecimal qtyRequired = this.getQtyEntered().multiply(PP_Product_BOMline.getQtyBOM());
						
						BigDecimal stock = MStockTransaction.getQtyAvailable(warehouse.get_ID(), locator.get_ID(), productLine.get_ID(), 
								0, 0, now, this.get_TrxName());
						if(stock.compareTo(qtyRequired)<0) throw new AdempiereException("No hay stock suficiente en el almacén '" + warehouse.getName() + "'" + 
								" para el producto '" + productLine.getName() + "'");						
						
					} else {
						
						locator = (MLocator)productLine.getM_Locator();
						warehouse = (MWarehouse)locator.getM_Warehouse();						
						
					}					

					MPPOrderBOMLine PP_Order_BOMLine = new MPPOrderBOMLine(true, PP_Product_BOMline, getUY_ProdTransf_ID(), PP_Order_BOM.get_ID(), warehouse.get_ID(),
							get_TrxName());

					// Fin Issue #824
					PP_Order_BOMLine.setAD_Org_ID(getAD_Org_ID());
					
					//OpenUp Nicolas Garcia 27/12/2010
					//PP_Order_BOMLine.set_ValueOfColumn(MPPOrderBOMLine.COLUMNNAME_AD_User_ID,this.get_Value("UY_Ad_User_Id_bom"));
					//end OpenUp
				
					// OpenUp FL 28/01/2011 issue #173
					PP_Order_BOMLine.set_ValueOfColumn("UY_Operacion_Orden",PP_Product_BOMline.get_Value("UY_Operacion"));		// TODO: Should be changed with a modeles setter and getters, the modeles must be updated
					PP_Order_BOMLine.set_ValueOfColumn("UY_Secuencia_Orden",PP_Product_BOMline.getLine());						// TODO: Should be changed with a model setter, the model must be updated
					PP_Order_BOMLine.setQtyOrdered(header.getQtyOrdered());
					PP_Order_BOMLine.setQtyBOM(PP_Product_BOMline.getQtyBOM());
					PP_Order_BOMLine.setQtyRequiered(this.getQtyEntered().multiply(PP_Product_BOMline.getQtyBOM()));
					PP_Order_BOMLine.set_ValueOfColumnReturningBoolean("UY_ProdTransf_ID", header.getUY_ProdTransf_ID());//suple el vínculo a pp_Order
					PP_Order_BOMLine.saveEx();
				} // end if valid From / To    
				else
				{
					log.fine("BOM Line skiped - "+PP_Product_BOMline);
				}
			} // end Create Order BOM
		} // end if From / To parent
		else
		{
			//throw new BOMExpiredException(PP_Product_BOM, getDateStartSchedule());
			//OBS: valida contra la fecha de la orden
			throw new BOMExpiredException(PP_Product_BOM, header.getDateOrdered());
		}

//		// Create Workflow (Routing & Process)
//		final MWorkflow AD_Workflow = MWorkflow.get(getCtx(), getAD_Workflow_ID());
//		// Workflow should be validated first - teo_sarca [ 2817870 ]
//		if (!AD_Workflow.isValid())
//		{
//			throw new AdempiereException("Routing is not valid. Please validate it first - "+AD_Workflow.getValue()); // TODO: translate
//		}
//		if (AD_Workflow.isValidFromTo(getDateStartSchedule()))
//		{
//			MPPOrderWorkflow PP_Order_Workflow = new MPPOrderWorkflow(AD_Workflow, get_ID(), get_TrxName());
//			PP_Order_Workflow.setAD_Org_ID(getAD_Org_ID());
//			PP_Order_Workflow.saveEx();
//			for (MWFNode AD_WF_Node : AD_Workflow.getNodes(false, getAD_Client_ID()))
//			{
//				if (AD_WF_Node.isValidFromTo(getDateStartSchedule()))
//				{
//					MPPOrderNode PP_Order_Node = new MPPOrderNode(AD_WF_Node, PP_Order_Workflow,
//															getQtyOrdered(),
//															get_TrxName());
//					PP_Order_Node.setAD_Org_ID(getAD_Org_ID());
//					PP_Order_Node.saveEx();
//					
//					for (MWFNodeNext AD_WF_NodeNext : AD_WF_Node.getTransitions(getAD_Client_ID()))
//					{
//						MPPOrderNodeNext nodenext = new MPPOrderNodeNext(AD_WF_NodeNext, PP_Order_Node);
//						nodenext.setAD_Org_ID(getAD_Org_ID());
//						nodenext.saveEx();
//					}// for NodeNext
//					
//					for (MPPWFNodeProduct wfnp : MPPWFNodeProduct.forAD_WF_Node_ID(getCtx(), AD_WF_Node.get_ID()))
//					{
//						MPPOrderNodeProduct nodeOrderProduct = new MPPOrderNodeProduct(wfnp, PP_Order_Node);
//						nodeOrderProduct.setAD_Org_ID(getAD_Org_ID());
//						nodeOrderProduct.saveEx();
//					}
//					
//					for (MPPWFNodeAsset wfna : MPPWFNodeAsset.forAD_WF_Node_ID(getCtx(), AD_WF_Node.get_ID()))
//					{
//						MPPOrderNodeAsset nodeorderasset = new MPPOrderNodeAsset(wfna, PP_Order_Node);
//						nodeorderasset.setAD_Org_ID(getAD_Org_ID());
//						nodeorderasset.saveEx();
//					}					
//				}// for node 
//
//			}
//			// Update transitions nexts and set first node
//			PP_Order_Workflow.getNodes(true); // requery
//			for (MPPOrderNode orderNode : PP_Order_Workflow.getNodes(false, getAD_Client_ID()))
//			{
//				// set workflow start node
//				if (PP_Order_Workflow.getAD_WF_Node_ID() == orderNode.getAD_WF_Node_ID())
//				{
//					PP_Order_Workflow.setPP_Order_Node_ID(orderNode.getPP_Order_Node_ID());
//				}
//				// set node next
//				for (MPPOrderNodeNext next : orderNode.getTransitions(getAD_Client_ID()))
//				{
//					next.setPP_Order_Next_ID();
//					next.saveEx();
//				}
//			}
//			PP_Order_Workflow.saveEx();
//		} // workflow valid from/to
//		else
//		{
//			throw new RoutingExpiredException(AD_Workflow, getDateStartSchedule());
//		}
	}*/

	
	
	public MPPProductBOM getMPPProductBOM()
	{
		final String whereClause = MPPProductBOM.COLUMNNAME_M_Product_ID+"=?";
		return new Query(getCtx(), MPPProductBOM.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[]{getM_Product_ID()})
				.firstOnly();
	}
	
	
	public MPPOrderBOM getMPPOrderBOM()
	{
		final String whereClause = MPPOrderBOM.COLUMNNAME_M_Product_ID+"=?";
		return new Query(getCtx(), MPPOrderBOM.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[]{getM_Product_ID()})
				.firstOnly();
	}
	
	public MPPOrderBOM getMPPOrderBOMFromOutputLine(int lineID)
	{
		final String whereClause = " uy_prodtransfout_id = " + lineID;
		return new Query(getCtx(), MPPOrderBOM.Table_Name, whereClause, get_TrxName())
				.firstOnly();
	}

	
	/***
	 * validaciones para lineas caso Multiple Output
	 * @author INes Fernandez - 17/08/2015
	 * @see
	 * @return
	 */	
	private void validateMOLine() {
		//control para que no se repita un producto en lineas de una misma orden.
		header =  new MProdTransf(getCtx(), getUY_ProdTransf_ID(), get_TrxName());
		String where = "AND M_Product_ID = " + this.getM_Product_ID();
		if( header!=null && header.getOutputLines(where)!=null && header.getOutputLines(where).length>0){
			throw new AdempiereException ("Ya existe una linea con este producto");
		}
		
	}

}
