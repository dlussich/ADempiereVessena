/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.apps.ADialog;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.openup.model.I_UY_InOutLabel;
import org.openup.model.MInOutLabel;
import org.openup.model.X_UY_InOutLabel;

/**
 * 	InOut Line
 *
 *  @author Jorg Janke
 *  @version $Id: MInOutLine.java,v 1.5 2006/07/30 00:51:03 jjanke Exp $
 *
 *  @author Teo Sarca, www.arhipac.ro
 *  		<li>BF [ 2784194 ] Check Warehouse-Locator conflict
 *  			https://sourceforge.net/tracker/?func=detail&aid=2784194&group_id=176962&atid=879332
 */
public class MInOutLine extends X_M_InOutLine
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8630611882798722864L;

	/**
	 * 	Get Ship lines Of Order Line
	 *	@param ctx context
	 *	@param C_OrderLine_ID line
	 *	@param where optional addition where clause
	 *  @param trxName transaction
	 *	@return array of receipt lines
	 */
	public static MInOutLine[] getOfOrderLine (Properties ctx,
		int C_OrderLine_ID, String where, String trxName)
	{
		String whereClause = "C_OrderLine_ID=?" + (!Util.isEmpty(where, true) ? " AND "+where : "");
		List<MInOutLine> list = new Query(ctx, Table_Name, whereClause, trxName)
									.setParameters(C_OrderLine_ID)
									.list();
		return list.toArray (new MInOutLine[list.size()]);
	}	//	getOfOrderLine

	/**
	 * 	Get Ship lines Of RMA Line
	 *	@param ctx context
	 *	@param M_RMALine_ID line
	 *	@param where optional addition where clause
	 *  @param trxName transaction
	 *	@return array of receipt lines
	 */
	public static MInOutLine[] getOfRMALine (Properties ctx,
		int M_RMALine_ID, String where, String trxName)
	{
		String whereClause = "M_RMALine_ID=? " + (!Util.isEmpty(where, true) ? " AND "+where : "");
		List<MInOutLine> list = new Query(ctx, Table_Name, whereClause, trxName)
									.setParameters(M_RMALine_ID)
									.list();
		return list.toArray (new MInOutLine[list.size()]);
	}	//	getOfRMALine

	/**
	 * 	Get Ship lines Of Order Line
	 *	@param ctx context
	 *	@param C_OrderLine_ID line
	 *  @param trxName transaction
	 *	@return array of receipt lines2
	 */
	public static MInOutLine[] get (Properties ctx, int C_OrderLine_ID, String trxName)
	{
		return getOfOrderLine(ctx, C_OrderLine_ID, null, trxName);
	}	//	get


	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_InOutLine_ID id
	 *	@param trxName trx name
	 */
	public MInOutLine (Properties ctx, int M_InOutLine_ID, String trxName)
	{
		super (ctx, M_InOutLine_ID, trxName);
		if (M_InOutLine_ID == 0)
		{
		//	setLine (0);
		//	setM_Locator_ID (0);
		//	setC_UOM_ID (0);
		//	setM_Product_ID (0);
			setM_AttributeSetInstance_ID(0);
		//	setMovementQty (Env.ZERO);
			setConfirmedQty(Env.ZERO);
			setPickedQty(Env.ZERO);
			setScrappedQty(Env.ZERO);
			setTargetQty(Env.ZERO);
			setIsInvoiced (false);
			setIsDescription (false);
		}
	}	//	MInOutLine

	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *  @param trxName transaction
	 */
	public MInOutLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MInOutLine

	/**
	 *  Parent Constructor
	 *  @param inout parent
	 */
	public MInOutLine (MInOut inout)
	{
		this (inout.getCtx(), 0, inout.get_TrxName());
		setClientOrg (inout);
		setM_InOut_ID (inout.getM_InOut_ID());
		setM_Warehouse_ID (inout.getM_Warehouse_ID());
		setC_Project_ID(inout.getC_Project_ID());
		m_parent = inout;
	}	//	MInOutLine

	/**	Product					*/
	private MProduct 		m_product = null;
	/** Warehouse				*/
	private int				m_M_Warehouse_ID = 0;
	/** Parent					*/
	private MInOut			m_parent = null;

	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public MInOut getParent()
	{
		if (m_parent == null)
			m_parent = new MInOut (getCtx(), getM_InOut_ID(), get_TrxName());
		return m_parent;
	}	//	getParent

	/**
	 * 	Set Order Line.
	 * 	Does not set Quantity!
	 *	@param oLine order line
	 *	@param M_Locator_ID locator
	 * 	@param Qty used only to find suitable locator
	 */
	public void setOrderLine (MOrderLine oLine, int M_Locator_ID, BigDecimal Qty)
	{
		setC_OrderLine_ID(oLine.getC_OrderLine_ID());
		setLine(oLine.getLine());
		setC_UOM_ID(oLine.getC_UOM_ID());
		MProduct product = oLine.getProduct();
		if (product == null)
		{
			set_ValueNoCheck("M_Product_ID", null);
			set_ValueNoCheck("M_AttributeSetInstance_ID", null);
			set_ValueNoCheck("M_Locator_ID", null);
		}
		else
		{
			setM_Product_ID(oLine.getM_Product_ID());
			setM_AttributeSetInstance_ID(oLine.getM_AttributeSetInstance_ID());
			//
			if (product.isItem())
			{
				if (M_Locator_ID == 0)
					setM_Locator_ID(Qty);	//	requires warehouse, product, asi
				else
					setM_Locator_ID(M_Locator_ID);
			}
			else
				set_ValueNoCheck("M_Locator_ID", null);
		}
		setC_Charge_ID(oLine.getC_Charge_ID());
		setDescription(oLine.getDescription());
		setIsDescription(oLine.isDescription());
		//
		setC_Project_ID(oLine.getC_Project_ID());
		setC_ProjectPhase_ID(oLine.getC_ProjectPhase_ID());
		setC_ProjectTask_ID(oLine.getC_ProjectTask_ID());
		setC_Activity_ID(oLine.getC_Activity_ID());
		setC_Campaign_ID(oLine.getC_Campaign_ID());
		setAD_OrgTrx_ID(oLine.getAD_OrgTrx_ID());
		setUser1_ID(oLine.getUser1_ID());
		setUser2_ID(oLine.getUser2_ID());
	}	//	setOrderLine

	/**
	 * 	Set Invoice Line.
	 * 	Does not set Quantity!
	 *	@param iLine invoice line
	 *	@param M_Locator_ID locator
	 *	@param Qty qty only fo find suitable locator
	 */
	public void setInvoiceLine (MInvoiceLine iLine, int M_Locator_ID, BigDecimal Qty)
	{
		setC_OrderLine_ID(iLine.getC_OrderLine_ID());
		setLine(iLine.getLine());
		setC_UOM_ID(iLine.getC_UOM_ID());
		int M_Product_ID = iLine.getM_Product_ID();
		if (M_Product_ID == 0)
		{
			set_ValueNoCheck("M_Product_ID", null);
			set_ValueNoCheck("M_Locator_ID", null);
			set_ValueNoCheck("M_AttributeSetInstance_ID", null);
		}
		else
		{
			setM_Product_ID(M_Product_ID);
			setM_AttributeSetInstance_ID(iLine.getM_AttributeSetInstance_ID());
			if (M_Locator_ID == 0)
				setM_Locator_ID(Qty);	//	requires warehouse, product, asi
			else
				setM_Locator_ID(M_Locator_ID);
		}
		setC_Charge_ID(iLine.getC_Charge_ID());
		setDescription(iLine.getDescription());
		setIsDescription(iLine.isDescription());
		//
		setC_Project_ID(iLine.getC_Project_ID());
		setC_ProjectPhase_ID(iLine.getC_ProjectPhase_ID());
		setC_ProjectTask_ID(iLine.getC_ProjectTask_ID());
		setC_Activity_ID(iLine.getC_Activity_ID());
		setC_Campaign_ID(iLine.getC_Campaign_ID());
		setAD_OrgTrx_ID(iLine.getAD_OrgTrx_ID());
		setUser1_ID(iLine.getUser1_ID());
		setUser2_ID(iLine.getUser2_ID());
	}	//	setInvoiceLine

	/**
	 * 	Get Warehouse
	 *	@return Returns the m_Warehouse_ID.
	 */
	public int getM_Warehouse_ID()
	{	
		
		// OpenUp FL 18/01/2011. Get the warehouse of the line. TODO: Update de interfase and change this
		Integer ii = (Integer)get_Value(MInOut.COLUMNNAME_M_Warehouse_ID);
		if (ii == null) {
			this.m_M_Warehouse_ID=0;
		}
		else {
			this.m_M_Warehouse_ID=ii.intValue();
		}
		

		// If the wharehouse is not set at lines, then use the parent
		if (m_M_Warehouse_ID == 0)
			m_M_Warehouse_ID = getParent().getM_Warehouse_ID();
		return m_M_Warehouse_ID;
		
	}	//	getM_Warehouse_ID

	/**
	 * 	Set Warehouse
	 *	@param warehouse_ID The m_Warehouse_ID to set.
	 */
	public void setM_Warehouse_ID (int warehouse_ID)
	{

		// OpenUp FL 18/01/2011. Set the warehouse of the line. TODO: Update de interfase and change this
		set_Value (MInOut.COLUMNNAME_M_Warehouse_ID, new Integer(warehouse_ID));
		
		this.m_M_Warehouse_ID = warehouse_ID;
	}	//	setM_Warehouse_ID

	/**
	 * 	Set M_Locator_ID
	 *	@param M_Locator_ID id
	 */
	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 0)
			throw new IllegalArgumentException ("M_Locator_ID is mandatory.");
		//	set to 0 explicitly to reset
		set_Value (COLUMNNAME_M_Locator_ID, new Integer(M_Locator_ID));
	}	//	setM_Locator_ID

	/**
	 * 	Set (default) Locator based on qty.
	 * 	@param Qty quantity
	 * 	Assumes Warehouse is set
	 */
	public void setM_Locator_ID(BigDecimal Qty)
	{
		//	Locator established
		if (getM_Locator_ID() != 0)
			return;
		//	No Product
		if (getM_Product_ID() == 0)
		{
			set_ValueNoCheck(COLUMNNAME_M_Locator_ID, null);
			return;
		}

		//	Get existing Location
		int M_Locator_ID = MStorage.getM_Locator_ID (getM_Warehouse_ID(),
				getM_Product_ID(), getM_AttributeSetInstance_ID(),
				Qty, get_TrxName());
		//	Get default Location
		if (M_Locator_ID == 0)
		{
			MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
		}
		setM_Locator_ID(M_Locator_ID);
	}	//	setM_Locator_ID

	/**
	 * 	Set Movement/Movement Qty
	 *	@param Qty Entered/Movement Qty
	 */
	public void setQty (BigDecimal Qty)
	{
		setQtyEntered(Qty);
		setMovementQty(getQtyEntered());
	}	//	setQtyInvoiced

	/**
	 * 	Set Qty Entered - enforce entered UOM
	 *	@param QtyEntered
	 */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		if (QtyEntered != null && getC_UOM_ID() != 0)
		{
			int precision = MUOM.getPrecision(getCtx(), getC_UOM_ID());
			QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyEntered (QtyEntered);
	}	//	setQtyEntered

	/**
	 * 	Set Movement Qty - enforce Product UOM
	 *	@param MovementQty
	 */
	public void setMovementQty (BigDecimal MovementQty)
	{
		MProduct product = getProduct();
		if (MovementQty != null && product != null)
		{
			int precision = product.getUOMPrecision();
			MovementQty = MovementQty.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setMovementQty(MovementQty);
	}	//	setMovementQty

	/**
	 * 	Get Product
	 *	@return product or null
	 */
	public MProduct getProduct()
	{
		if (m_product == null && getM_Product_ID() != 0)
			m_product = MProduct.get (getCtx(), getM_Product_ID());
		return m_product;
	}	//	getProduct

	/**
	 * 	Set Product
	 *	@param product product
	 */
	public void setProduct (MProduct product)
	{
		m_product = product;
		if (m_product != null)
		{
			setM_Product_ID(m_product.getM_Product_ID());
			setC_UOM_ID (m_product.getC_UOM_ID());
		}
		else
		{
			setM_Product_ID(0);
			setC_UOM_ID (0);
		}
		setM_AttributeSetInstance_ID(0);
	}	//	setProduct

	/**
	 * 	Set M_Product_ID
	 *	@param M_Product_ID product
	 *	@param setUOM also set UOM from product
	 */
	public void setM_Product_ID (int M_Product_ID, boolean setUOM)
	{
		if (setUOM)
			setProduct(MProduct.get(getCtx(), M_Product_ID));
		else
			super.setM_Product_ID (M_Product_ID);
		setM_AttributeSetInstance_ID(0);
	}	//	setM_Product_ID

	/**
	 * 	Set Product and UOM
	 *	@param M_Product_ID product
	 *	@param C_UOM_ID uom
	 */
	public void setM_Product_ID (int M_Product_ID, int C_UOM_ID)
	{
		if (M_Product_ID != 0)
			super.setM_Product_ID (M_Product_ID);
		super.setC_UOM_ID(C_UOM_ID);
		setM_AttributeSetInstance_ID(0);
		m_product = null;
	}	//	setM_Product_ID

	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription

	/**
	 * 	Get C_Project_ID
	 *	@return project
	 */
	public int getC_Project_ID()
	{
		int ii = super.getC_Project_ID ();
		if (ii == 0)
			ii = getParent().getC_Project_ID();
		return ii;
	}	//	getC_Project_ID

	/**
	 * 	Get C_Activity_ID
	 *	@return Activity
	 */
	public int getC_Activity_ID()
	{
		int ii = super.getC_Activity_ID ();
		if (ii == 0)
			ii = getParent().getC_Activity_ID();
		return ii;
	}	//	getC_Activity_ID

	/**
	 * 	Get C_Campaign_ID
	 *	@return Campaign
	 */
	public int getC_Campaign_ID()
	{
		int ii = super.getC_Campaign_ID ();
		if (ii == 0)
			ii = getParent().getC_Campaign_ID();
		return ii;
	}	//	getC_Campaign_ID

	/**
	 * 	Get User2_ID
	 *	@return User2
	 */
	public int getUser1_ID ()
	{
		int ii = super.getUser1_ID ();
		if (ii == 0)
			ii = getParent().getUser1_ID();
		return ii;
	}	//	getUser1_ID

	/**
	 * 	Get User2_ID
	 *	@return User2
	 */
	public int getUser2_ID ()
	{
		int ii = super.getUser2_ID ();
		if (ii == 0)
			ii = getParent().getUser2_ID();
		return ii;
	}	//	getUser2_ID

	/**
	 * 	Get AD_OrgTrx_ID
	 *	@return trx org
	 */
	public int getAD_OrgTrx_ID()
	{
		int ii = super.getAD_OrgTrx_ID();
		if (ii == 0)
			ii = getParent().getAD_OrgTrx_ID();
		return ii;
	}	//	getAD_OrgTrx_ID

	/**************************************************************************
	 * 	Before Save
	 *	@param newRecord new
	 *	@return save
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		
		//OpenUp Nicolas Garcia issue #727 06/07/2011
		//Necesito los datos de Cabezal
		MInOut inOutHdr = new MInOut(getCtx(), this.getM_InOut_ID(), get_TrxName());
			
		//Necesito los datos de docType para preguntar por tipos
		MDocType docType = new MDocType(getCtx(),inOutHdr.getC_DocType_ID(), null);
		// Fin 06/07/2011 #727
		
		log.fine("");
		if (newRecord && getParent().isComplete()) {
			log.saveError("ParentComplete", Msg.translate(getCtx(), "M_InOutLine"));
			return false;
		}

		// OpenUp. Gabriel Vila. 01/04/2013. Issue #617
		// Para documento de recepcion de compra, si no tengo ubicacion, seteo por defecto del almacen
		if (docType.getValue() != null){
			if (docType.getValue().equalsIgnoreCase("poreception")){
				if (this.getM_Locator_ID() <= 0){
					MWarehouse warehouse = (MWarehouse)inOutHdr.getM_Warehouse();
					if (warehouse != null){
						MLocator locator = MLocator.getDefault(warehouse);
						if (locator != null){
							this.setM_Locator_ID(locator.get_ID());
						}
					}
				}
				
				MProduct prod = (MProduct)this.getM_Product();
				
				if(prod!=null && prod.get_ID()>0){
					
					this.set_Value("C_UOM_ALM_ID", prod.getC_UOM_ID());	
					
				}				
			}
		}
		// Fin Issue #617.

		
		// OpenUp FL 14/04/2011, specific before seve controls form customers returns 
		if (!(beforeSaveCustomerReturns(newRecord))) {
			return(false);
		}		
		
		// Locator is mandatory if no charge is defined - teo_sarca BF [ 2757978 ]
		if(getProduct() != null && MProduct.PRODUCTTYPE_Item.equals(getProduct().getProductType()))
		{
			if (getM_Locator_ID() <= 0 && getC_Charge_ID() <= 0)
			{
				throw new FillMandatoryException(COLUMNNAME_M_Locator_ID);
			}
		}

		//	Get Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM M_InOutLine WHERE M_InOut_ID=?";
			int ii = DB.getSQLValueEx (get_TrxName(), sql, getM_InOut_ID());
			setLine (ii);
		}
		//	UOM
		if (getC_UOM_ID() == 0)
			setC_UOM_ID (Env.getContextAsInt(getCtx(), "#C_UOM_ID"));
		if (getC_UOM_ID() == 0)
		{
			int C_UOM_ID = MUOM.getDefault_UOM_ID(getCtx());
			if (C_UOM_ID > 0)
				setC_UOM_ID (C_UOM_ID);
		}
		//	Qty Precision
		if (newRecord || is_ValueChanged("QtyEntered"))
			setQtyEntered(getQtyEntered());
		if (newRecord || is_ValueChanged("MovementQty"))
			setMovementQty(getMovementQty());

		// OpenUp. Gabriel Vila. 17/02/2012. 
		// Se comenta la siguiente validacion.
		
		/*
		//	Order/RMA Line
		if (getC_OrderLine_ID() == 0 && getM_RMALine_ID() == 0)
		{
			if (getParent().isSOTrx())
			{
				log.saveError("FillMandatory", Msg.translate(getCtx(), "C_Order_ID"));
				return false;
			}
		}
		*/
		// Fin OpenUp

		// Validate Locator/Warehouse - teo_sarca, BF [ 2784194 ]
		if (getM_Locator_ID() > 0)
		{
			MLocator locator = MLocator.get(getCtx(), getM_Locator_ID());
			if (getM_Warehouse_ID() != locator.getM_Warehouse_ID())
			{
				
				// OpenUp. Gabriel Vila. 11/09/2014.
				// Comento y sustituyo porque hace mal el tema almacen.
				
				this.setM_Warehouse_ID(locator.getM_Warehouse_ID());

				/*
				throw new WarehouseLocatorConflictException(
						MWarehouse.get(getCtx(), getM_Warehouse_ID()),
						locator,
						getLine());
				*/
			}
		}

	//	if (getC_Charge_ID() == 0 && getM_Product_ID() == 0)
	//		;

		/**	 Qty on instance ASI
		if (getM_AttributeSetInstance_ID() != 0)
		{
			MProduct product = getProduct();
			int M_AttributeSet_ID = product.getM_AttributeSet_ID();
			boolean isInstance = M_AttributeSet_ID != 0;
			if (isInstance)
			{
				MAttributeSet mas = MAttributeSet.get(getCtx(), M_AttributeSet_ID);
				isInstance = mas.isInstanceAttribute();
			}
			//	Max
			if (isInstance)
			{
				MStorage storage = MStorage.get(getCtx(), getM_Locator_ID(),
					getM_Product_ID(), getM_AttributeSetInstance_ID(), get_TrxName());
				if (storage != null)
				{
					BigDecimal qty = storage.getQtyOnHand();
					if (getMovementQty().compareTo(qty) > 0)
					{
						log.warning("Qty - Stock=" + qty + ", Movement=" + getMovementQty());
						log.saveError("QtyInsufficient", "=" + qty);
						return false;
					}
				}
			}
		}	/**/

		//OpenUp Nicolas Garcia 14-06-2011 Issue #731
		//Se apunta a controlar el qtyEntered y el movementqty, Logica sacada de calloutInOut
		//int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
		int C_UOM_To_ID = getC_UOM_ID();
		BigDecimal QtyEntered = this.getQtyEntered();
		BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(getCtx(), C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
		if (QtyEntered.compareTo(QtyEntered1) != 0)
		{			
			QtyEntered = QtyEntered1;
			//mTab.setValue("QtyEntered", QtyEntered);
			this.setQtyEntered(QtyEntered1);
		}
		
		if (getMovementQty() == null || getMovementQty().compareTo(Env.ZERO) == 0) {
			BigDecimal MovementQty = MUOMConversion.convertProductFrom(getCtx(), getM_Product_ID(), C_UOM_To_ID, QtyEntered);
			if (MovementQty == null) MovementQty = QtyEntered;
			else if (MovementQty.compareTo(Env.ZERO) == 0) MovementQty = QtyEntered;

			this.setMovementQty(MovementQty);
		}
		//Fin OpenUp Nicolas Garcia 14-06-2011 Issue #731
		
		//OpenUp Nicolas Garcia issue #727 06/07/2011
		//Para el caso que sea una Devolucion de cliente
		if(docType.getDocBaseType().equals("MMR")) {
			if (docType.getDocSubTypeSO() != null){
				if (docType.getDocSubTypeSO().equals("RM")){
					if(!caseBeforeSaveCostumerReturns(inOutHdr)){
						return false;
					}					
				}
			}
			
			//OpenUp Nicolas Sarlabos issue #780 24/08/2011
			//Para el caso que sea una recepcion de compra
			if (!docType.isSOTrx()){
				if(!caseBeforeSavePO(inOutHdr)){ 
					return false;
				}
			}
			// Fin OpenUp #780
			
		}// Fin 06/07/2011 #727
	
		
		
		return true;
	}	//	beforeSave

	/**
	 * 
	 * OpenUp.	#780
	 * Descripcion : Metodo que valida las cantidades de la orden de compra en la recepcion de materiales
	 * @param inOutHdr
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 24/08/2011
	 */
	protected boolean caseBeforeSavePO(MInOut inOutHdr) {
		//OpenUp Nicolas Sarlabos issue #901 14/10/2011
		String sql ="";
		MOrderLine line = (MOrderLine)this.getC_OrderLine();
		MProduct prod = (MProduct)this.getM_Product();
		
		if(is_ValueChanged("C_UOM_ID") || is_ValueChanged("QtyEntered")){

			if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID())){

				BigDecimal qtyMov = this.getQtyEntered();

				//si la UM ingresada no es igual a la UM de stock del producto
				if(this.getC_UOM_ID() != prod.getC_UOM_ID()){

					BigDecimal factor = MUOMConversion.getProductRateFrom(getCtx(), this.getM_Product_ID(), this.getC_UOM_ID());

					if(factor==null) throw new AdempiereException("No hay factor de conversion entre la UM indicada y la UM de stock del producto");

					qtyMov = this.getQtyEntered().multiply(factor);

				}

				this.setMovementQty(qtyMov);		
			}	
		}
	
		//OpenUP SBT 10/11/2015 Issue # 5028
		//Se agrega condicion para el caso de que la linea tenga bonificación simple 
		BigDecimal disponible ;
		if(line.get_ValueAsBoolean("UY_BonificaSimple")){
			disponible = line.getQtyOrdered().subtract(line.getQtyDelivered())
					.add((line.get_Value("QtyReward")!=null)? (BigDecimal)line.get_Value("QtyReward"):Env.ZERO);
		}else{
			
			if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID())){
				
				BigDecimal qtyOrdered = line.getQtyOrdered();
				
				//si la UM ingresada no es igual a la UM de stock del producto
				if(line.getC_UOM_ID() != this.getC_UOM_ID()){
					
					BigDecimal factor = MUOMConversion.getProductRateFrom(getCtx(), this.getM_Product_ID(), this.getC_UOM_ID());
					
					if(factor==null) throw new AdempiereException("No hay factor de conversion entre la UM indicada y la UM de stock del producto");
					
					qtyOrdered = qtyOrdered.multiply(factor);
					
				}
				
				disponible = qtyOrdered.subtract(line.getQtyDelivered());
				
			} else disponible = line.getQtyOrdered().subtract(line.getQtyDelivered());
		}
				
		sql = "SELECT tolerance_percentage" +
			  " FROM m_replenish" +
			  " WHERE m_product_id=" + this.getM_Product_ID() +
			  " AND m_warehouse_id=" + this.getM_Warehouse_ID() +
			  "	AND m_locator_id=" + this.getM_Locator_ID();
		
		Integer percentage = DB.getSQLValue(get_TrxName(), sql);
		
		
		if(percentage != null){
			if(percentage > 0){
				
				   BigDecimal hundred = new BigDecimal(100);
				   BigDecimal perc = new BigDecimal(percentage);
				   BigDecimal percentageFactor = perc.divide(hundred,2, BigDecimal.ROUND_HALF_UP);
				   BigDecimal value = line.getQtyOrdered().multiply(percentageFactor);
				   disponible = (line.getQtyOrdered().subtract(line.getQtyDelivered())).add(value);
				
			} 
		}
				
		//Lo entrado tiene que ser mayor que cero y <= disponible para recibir
		if (disponible.compareTo(Env.ZERO)>0){
			if(this.getQtyEntered().compareTo(Env.ZERO)>0){
				//OpenUp. INes Fernandez. 13/05/2015. Issue #3445
				//Modificado para contemplar el caso en que se usan distintas UoM,
				//compara en la misma unidad con el qtyOrdered
				// El valor tiene que ser menor o igual a la cantidad disponible.
				//if(disponible.compareTo(this.getQtyEntered())<0){ 	
				if(disponible.compareTo(this.getMovementQty())<0){
					throw new AdempiereException("El valor tiene que ser menor o igual a " + disponible);
				}
				//if(this.getQtyEntered().compareTo(line.getQtyOrdered())>=0){
				if(this.getMovementQty().compareTo(line.getQtyOrdered())>=0){
					//OpenUP SBT 10/11/2015 Issue # 5028
					//Se agrega condicion para el caso de que la linea tenga bonificación simple 
					if(line.get_ValueAsBoolean("UY_BonificaSimple")){
						line.setQtyReserved(line.getQtyReserved().subtract(this.getMovementQty()));
					}else{
						line.setQtyReserved(Env.ZERO);
					}
				}
				//End Issue #3445
				
			}else{
				//valor tiene que ser mayor a cero
				throw new AdempiereException("El valor tiene que ser mayor a cero");
			}
		}
		
		return true;
	}


	/**
	 * 
	 * OpenUp.	#780
	 * Descripcion : Luego de guardar la lï¿½nea "m_inoutline" asigna el mismo valor de su campo "uy_polineclosed"
	 * al de la "c_orderline"
	 * @param inOutHdr
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 26/08/2011
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		MInOut header = new MInOut(Env.getCtx(), getM_InOut_ID(), get_TrxName());
		MDocType docType = new MDocType(getCtx(), header.getC_DocType_ID(), get_TrxName());

		if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID())){

			if(!newRecord){

				if(is_ValueChanged("C_UOM_ID")){

					MOrderLine ol = (MOrderLine)this.getC_OrderLine();
					MProduct prod = (MProduct)this.getM_Product();
					BigDecimal qtyOrdered = ol.getQtyEntered();
					BigDecimal qtyReserved = ol.getQtyReserved();

					//si la UM ingresada no es igual a la UM de la orden
					if(this.getC_UOM_ID() != ol.getC_UOM_ID()){
						
						if(this.getC_UOM_ID() != prod.getC_UOM_ID()){
							
							BigDecimal factor = MUOMConversion.getProductRateFrom(getCtx(), this.getM_Product_ID(), this.getC_UOM_ID());

							if(factor==null) throw new AdempiereException("No hay factor de conversion entre la UM indicada y la UM de stock del producto");

							qtyOrdered = qtyOrdered.multiply(factor);
							qtyReserved = qtyReserved.multiply(factor);				
							
						}

					}

					DB.executeUpdateEx("update c_orderline set c_uom_id = " + this.getC_UOM_ID() + ", qtyordered = " + qtyOrdered + 
							", qtyreserved = " + qtyReserved + " where c_orderline_id = " + ol.get_ID(), get_TrxName());				

				}				
			}			
		}

		if (!docType.isSOTrx()){
			if (docType.getDocBaseType().equals("MMR")) {
				MOrderLine line = new MOrderLine(getCtx(), this.getC_OrderLine_ID(), get_TrxName());

				if (line.get_ID() > 0) {
					line.setuy_polineclosed(this.isuy_polineclosed());
					return line.save(this.get_TrxName());
				}
				
				// OpenUp. Matias Perez. 15/01/2015. Issue #2565
				// Si manejo atributos de producto, debo cargar automaticamente los mismos
				// cuando es nuevo registro.
				if (MSysConfig.getBooleanValue("UY_PO_LOAD_PROD_ATTRIBUTE", false, this.getAD_Client_ID())){
					//this.loadProductAttributes();
				}
				// Fin OpenUp. Issue #2565
				
			}			
		}
		
	
		
		return true;
	}
	
	/***
	 * Cuando un producto requiere gestion de atributo, se deben cargar los mismo de manera
	 * automatica desde la parametrizacion misma del producto.
	 * OpenUp Ltda. Issue #2565 
	 * @author Matias Perez - 15/01/2015
	 * @param COLUMNNAME_UY_ProdAttribute_ID 
	 * @see
	 */
	/*private void loadProductAttributes(String COLUMNNAME_UY_ProdAttribute_ID) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			this.getM_Product_ID(); 
			
			//ir a la tabla m_productattribute traer ordenados por secuencia todos los atributos
			
			sql = "SELECT * FROM m_productattribute " + 
				  "order by seqno " + 
				  "where m_product_id '" + this.m_product + "'" ; 
				  
		
			 pstmt = DB.prepareStatement(sql, get_TrxName());
			 rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				MMInoutAttribute x  = new MMInoutAttribute(this.getCtx(), 0, get_TrxName());	
				//x.setUY_ProdAttribute_ID(get_Value(COLUMNNAME_UY_ProdAttribute_ID));
				//x.setUY_ProdAttribute_ID(get_ValueAsString(COLUMNNAME_UY_ProdAttribute_ID));
				//x.setUY_ProdAttribute_ID(get_Value(COLUMNNAME_M_ProductAttribute_ID));
				//x.setUY_ProdAttribute_ID(ge)
				
				x.saveEx();
				
				
			
				// instanciar un modelo MInOutAttribute
				// setear propiedades (modelo.setUY_ProdAttribute_ID(.....))
				// hacer el saveex : modelo.saveEx()
			
			}			
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	private Object getUY_ProdAttribute_ID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion : Before save for customer returns
	 * @return
	 * @author  FL
	 * Fecha : 14/04/2011
	 */
	protected boolean beforeSaveCustomerReturns(boolean newRecord) {

		// Get the header, required to check if its a customer return without order or rma
		MInOut inOut=this.getParent();			

		// Check if its a customer return without order or rma
		if ((inOut.getMovementType().equals(MInOut.MOVEMENTTYPE_CustomerReturns))&&(inOut.getC_Order_ID()==0)&&(inOut.getM_RMA_ID()==0)) {
			
			// If the in/out is not set at lines get it from the header 
			if (this.getUY_InOutType_ID()==0) {
				
				// If the header in/out type is not set to the header and the line, its an error
				if (inOut.getUY_InOutType_ID()==0) {
					return(true);
				}
				
				// Set the in/out type from the header
				this.setUY_InOutType_ID(inOut.getUY_InOutType_ID());
			}

			// Get the in/out type to check the warehouse
			
			//OpenUp. Nicolas Sarlabos. 28/02/2013. #433.Se comenta codigo.
			//MInOutType inOutType=new MInOutType(getCtx(),this.getUY_InOutType_ID(),null);
			
			// If the in/out type warehouse is diferent from the line warehouse, reset it and, also important, the default locator
			/*if (this.getM_Warehouse_ID()!=inOutType.getM_Warehouse_ID()) {
				this.setM_Warehouse_ID(inOutType.getM_Warehouse_ID());
				this.setM_Locator_ID(inOutType.getDefaultM_Locator_ID());
			}	
			else {
				// Checke if the locator is set, if not, get it form the type
				if (this.getM_Locator_ID()==0) {
					this.setM_Locator_ID(inOutType.getDefaultM_Locator_ID());
				}
			}*/
			//Fin OpenUp.
			// Max lines for Customer Returns when there is no order and no RMA
			if (newRecord) {
				String SQL="SELECT count(*) FROM m_inoutline WHERE m_inoutline.m_inout_id=?";
				int count=DB.getSQLValueEx(get_TrxName(),SQL,getM_InOut_ID());
				
				// Get the system value
				int max = MSysConfig.getIntValue("UY_MAX_LINEAS_FACTURA", 30, inOut.getAD_Client_ID());
				
				if (count>max) {																																		
					throw new IllegalArgumentException("Las devoluciones pueden tener hasta "+max+" lineas, debe ingresar esta linea en una nueva devolucion por que ya tiene "+count+" lineas");			// TODO: translate
				}
				
			}
		}
	
		return(true);
	}

		
	/**
	 * 	Before Delete
	 *	@return true if drafted
	 */
	protected boolean beforeDelete ()
	{
		if (getParent().getDocStatus().equals(MInOut.DOCSTATUS_Drafted))
			return true;
		log.saveError("Error", Msg.getMsg(getCtx(), "CannotDelete"));
		return false;
	}	//	beforeDelete

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MInOutLine[").append (get_ID())
			.append(",M_Product_ID=").append(getM_Product_ID())
			.append(",QtyEntered=").append(getQtyEntered())
			.append(",MovementQty=").append(getMovementQty())
			.append(",M_AttributeSetInstance_ID=").append(getM_AttributeSetInstance_ID())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Get Base value for Cost Distribution
	 *	@param CostDistribution cost Distribution
	 *	@return base number
	 */
	public BigDecimal getBase (String CostDistribution)
	{
		if (MLandedCost.LANDEDCOSTDISTRIBUTION_Costs.equals(CostDistribution))
		{
			MInvoiceLine m_il = MInvoiceLine.getOfInOutLine(this);
			if (m_il == null)
			{
				log.severe("No Invoice Line for: " + this.toString());
				return Env.ZERO;
			}
			return this.getMovementQty().multiply(m_il.getPriceActual());  // Actual delivery
		}
		else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Line.equals(CostDistribution))
			return Env.ONE;
		else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Quantity.equals(CostDistribution))
			return getMovementQty();
		else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Volume.equals(CostDistribution))
		{
			MProduct product = getProduct();
			if (product == null)
			{
				log.severe("No Product");
				return Env.ZERO;
			}
			return getMovementQty().multiply(product.getVolume());
		}
		else if (MLandedCost.LANDEDCOSTDISTRIBUTION_Weight.equals(CostDistribution))
		{
			MProduct product = getProduct();
			if (product == null)
			{
				log.severe("No Product");
				return Env.ZERO;
			}
			return getMovementQty().multiply(product.getWeight());
		}
		//
		log.severe("Invalid Criteria: " + CostDistribution);
		return Env.ZERO;
	}	//	getBase

	public boolean sameOrderLineUOM()
	{
		if (getC_OrderLine_ID() <= 0)
			return false;

		MOrderLine oLine = new MOrderLine(getCtx(), getC_OrderLine_ID(), get_TrxName());

		if (oLine.getC_UOM_ID() != getC_UOM_ID())
			return false;

		// inout has orderline and both has the same UOM
		return true;
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion : Issue #726 Validaciones de devoluciones de cliente
	 * @param inOutHdr
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 26/07/2011
	 */
	private boolean caseBeforeSaveCostumerReturns(MInOut inOutHdr){
		if(inOutHdr.getDocStatus().equals("DR")){//Si esta en borrador
			//Dejan los campos PickedQty y QtyEntered igual al TargetQty
			if (this.getTargetQty().compareTo(Env.ZERO) > 0){
				this.setPickedQty(this.getTargetQty());
				this.setQtyEntered(this.getTargetQty());
			}
			
		}else if(inOutHdr.getDocStatus().equals("RQ")){//Si esta en estado solicitado
			
			if(this.getTargetQty().compareTo(this.getQtyEntered())<0){//Si lo entrado es mayor a lo solicitado, envio error
				ADialog.warn(0, null, null,"No se puede ingresar mas  de lo que fue solicitado");
				return false;
			}else{
				this.setPickedQty(this.getQtyEntered());
			}
		}	
		return true;
	}
	
	/**
	 * 	OpenUp. Nicolas Sarlabos. 05/11/2015.
	 *  Metodo que devuelve la lista de lineas de numeros de etiquetas,
	 *  en una recepcion de compra
	 * 	@return lines
	 */
	public List<MInOutLabel> getLabelLines(){

		String whereClause = X_UY_InOutLabel.COLUMNNAME_M_InOutLine_ID + "=" + this.get_ID();

		List<MInOutLabel> lines = new Query(getCtx(), I_UY_InOutLabel.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	
}	//	MInOutLine
