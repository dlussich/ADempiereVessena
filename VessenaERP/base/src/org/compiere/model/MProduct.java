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
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.openup.beans.ProdImplosion;
import org.openup.model.IDynamicTask;
import org.openup.model.I_M_ProductAttribute;
import org.openup.model.I_UY_ProductUpc;
import org.openup.model.MProdAttribute;
import org.openup.model.MProductAttribute;
import org.openup.model.MProductPrintLabel;
import org.openup.model.MProductUpc;
import org.openup.model.MRTAction;
import org.openup.model.MRTInterfaceProd;
import org.openup.model.MRTInterfaceScales;
import org.openup.retail.MRTRetailInterface;
import org.openup.model.X_M_ProductAttribute;
import org.openup.model.X_UY_ProductUpc;
import org.openup.util.OpenUpUtils;


/**
 * 	Product Model
 *
 *	@author Jorg Janke
 *	@version $Id: MProduct.java,v 1.5 2006/07/30 00:51:05 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>FR [ 1885153 ] Refactor: getMMPolicy code
 * 			<li>BF [ 1885414 ] ASI should be always mandatory if CostingLevel is Batch/Lot
 * 			<li>FR [ 2093551 ] Refactor/Add org.compiere.model.MProduct.getCostingLevel
 * 			<li>FR [ 2093569 ] Refactor/Add org.compiere.model.MProduct.getCostingMethod
 * 			<li>BF [ 2824795 ] Deleting Resource product should be forbidden
 * 				https://sourceforge.net/tracker/?func=detail&aid=2824795&group_id=176962&atid=879332
 * 
 * @author Mark Ostermann (mark_o), metas consult GmbH
 * 			<li>BF [ 2814628 ] Wrong evaluation of Product inactive in beforeSave()
 */
public class MProduct extends X_M_Product implements IDynamicTask
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 285926961771269935L;

	/**
	 * 	Get MProduct from Cache
	 *	@param ctx context
	 *	@param M_Product_ID id
	 *	@return MProduct or null
	 */
	public static MProduct get (Properties ctx, int M_Product_ID)
	{
		if (M_Product_ID <= 0)
		{
			return null;
		}
		Integer key = new Integer (M_Product_ID);
		MProduct retValue = (MProduct) s_cache.get (key);
		if (retValue != null)
		{
			return retValue;
		}
		retValue = new MProduct (ctx, M_Product_ID, null);
		if (retValue.get_ID () != 0)
		{
			s_cache.put (key, retValue);
		}
		return retValue;
	}	//	get

	/**
	 * 	Get MProduct from Cache
	 *	@param ctx context
	 *	@param whereClause sql where clause
	 *	@param trxName trx
	 *	@return MProduct
	 */
	public static MProduct[] get (Properties ctx, String whereClause, String trxName)
	{
		List<MProduct> list = new Query(ctx, Table_Name, whereClause, trxName)
								.setClient_ID()
								.list();
		return list.toArray(new MProduct[list.size()]);
	}	//	get


	/**
	 * Get MProduct using UPC/EAN (case sensitive)
	 * @param  ctx     Context
	 * @param  upc     The upc to look for
	 * @return List of MProduct
	 */
	public static List<MProduct> getByUPC(Properties ctx, String upc, String trxName)
	{
		final String whereClause = "UPC=?";
		Query q = new Query(ctx, Table_Name, whereClause, trxName);
		q.setParameters(upc).setClient_ID();
		return(q.list());
	}

	/**
	 * Get Product from Cache
	 * @param ctx context
	 * @param S_Resource_ID resource ID
	 * @return MProduct or null if not found
	 * @deprecated Since 3.5.3a. Please use {@link #forS_Resource_ID(Properties, int, String)}
	 */
	public static MProduct forS_Resource_ID(Properties ctx, int S_Resource_ID)
	{
		return forS_Resource_ID(ctx, S_Resource_ID, null);
	}
	
	/**
	 * Get Product from Cache
	 * @param ctx context
	 * @param S_Resource_ID resource ID
	 * @param trxName
	 * @return MProduct or null if not found
	 */
	public static MProduct forS_Resource_ID(Properties ctx, int S_Resource_ID, String trxName)
	{
		if (S_Resource_ID <= 0)
		{
			return null;
		}
		
		// Try Cache
		if (trxName == null)
		{
			for (MProduct p : s_cache.values())
			{
				if (p.getS_Resource_ID() == S_Resource_ID)
				{
					return p;
				}
			}
		}
		// Load from DB
		MProduct p = new Query(ctx, Table_Name, COLUMNNAME_S_Resource_ID+"=?", trxName)
						.setParameters(new Object[]{S_Resource_ID})
						.firstOnly();
		if (p != null && trxName == null)
		{
			s_cache.put(p.getM_Product_ID(), p);
		}
		return p;
	}
	
	
	/**
	 * 	Is Product Stocked
	 * 	@param ctx context
	 *	@param M_Product_ID id
	 *	@return true if found and stocked - false otherwise
	 */
	public static boolean isProductStocked (Properties ctx, int M_Product_ID)
	{
		MProduct product = get (ctx, M_Product_ID);
		return product.isStocked();
	}	//	isProductStocked
	
	/**	Cache						*/
	private static CCache<Integer,MProduct> s_cache	= new CCache<Integer,MProduct>(Table_Name, 40, 5);	//	5 minutes
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_Product_ID id
	 *	@param trxName transaction
	 */
	public MProduct (Properties ctx, int M_Product_ID, String trxName)
	{
		super (ctx, M_Product_ID, trxName);
		if (M_Product_ID == 0)
		{
		//	setValue (null);
		//	setName (null);
		//	setM_Product_Category_ID (0);
		//	setC_TaxCategory_ID (0);
		//	setC_UOM_ID (0);
		//
			setProductType (PRODUCTTYPE_Item);	// I
			setIsBOM (false);	// N
			setIsInvoicePrintDetails (false);
			setIsPickListPrintDetails (false);
			setIsPurchased (true);	// Y
			setIsSold (true);	// Y
			setIsStocked (true);	// Y
			setIsSummary (false);
			setIsVerified (false);	// N
			setIsWebStoreFeatured (false);
			setIsSelfService(true);
			setIsExcludeAutoDelivery(false);
			setProcessing (false);	// N
			setLowLevel(0);
		}
	}	//	MProduct

	/**
	 * 	Load constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MProduct (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProduct

	/**
	 * 	Parent Constructor
	 *	@param et parent
	 */
	public MProduct (MExpenseType et)
	{
		this (et.getCtx(), 0, et.get_TrxName());
		setProductType(X_M_Product.PRODUCTTYPE_ExpenseType);
		setExpenseType(et);
	}	//	MProduct
	
	/**
	 * 	Parent Constructor
	 *	@param resource parent
	 *	@param resourceType resource type
	 */
	public MProduct (MResource resource, MResourceType resourceType)
	{
		this (resource.getCtx(), 0, resource.get_TrxName());
		setAD_Org_ID(resource.getAD_Org_ID());
		setProductType(X_M_Product.PRODUCTTYPE_Resource);
		setResource(resource);
		setResource(resourceType);
	}	//	MProduct

	/**
	 * 	Import Constructor
	 *	@param impP import
	 */
	public MProduct (X_I_Product impP)
	{
		this (impP.getCtx(), 0, impP.get_TrxName());
		setClientOrg(impP);
		setUpdatedBy(impP.getUpdatedBy());
		//
		setValue(impP.getValue());
		setName(impP.getName());
		setDescription(impP.getDescription());
		setDocumentNote(impP.getDocumentNote());
		setHelp(impP.getHelp());
		setUPC(impP.getUPC());
		setSKU(impP.getSKU());
		setC_UOM_ID(impP.getC_UOM_ID());
		setM_Product_Category_ID(impP.getM_Product_Category_ID());
		setProductType(impP.getProductType());
		setImageURL(impP.getImageURL());
		setDescriptionURL(impP.getDescriptionURL());
	}	//	MProduct
	
	/** Additional Downloads				*/
	private MProductDownload[] m_downloads = null;
	
	/**
	 * 	Set Expense Type
	 *	@param parent expense type
	 *	@return true if changed
	 */
	public boolean setExpenseType (MExpenseType parent)
	{
		boolean changed = false;
		if (!PRODUCTTYPE_ExpenseType.equals(getProductType()))
		{
			setProductType(PRODUCTTYPE_ExpenseType);
			changed = true;
		}
		if (parent.getS_ExpenseType_ID() != getS_ExpenseType_ID())
		{
			setS_ExpenseType_ID(parent.getS_ExpenseType_ID());
			changed = true;
		}
		if (parent.isActive() != isActive())
		{
			setIsActive(parent.isActive());
			changed = true;
		}
		//
		if (!parent.getValue().equals(getValue()))
		{
			setValue(parent.getValue());
			changed = true;
		}
		if (!parent.getName().equals(getName()))
		{
			setName(parent.getName());
			changed = true;
		}
		if ((parent.getDescription() == null && getDescription() != null)
			|| (parent.getDescription() != null && !parent.getDescription().equals(getDescription())))
		{
			setDescription(parent.getDescription());
			changed = true;
		}
		if (parent.getC_UOM_ID() != getC_UOM_ID())
		{
			setC_UOM_ID(parent.getC_UOM_ID());
			changed = true;
		}
		if (parent.getM_Product_Category_ID() != getM_Product_Category_ID())
		{
			setM_Product_Category_ID(parent.getM_Product_Category_ID());
			changed = true;
		}
		if (parent.getC_TaxCategory_ID() != getC_TaxCategory_ID())
		{
			setC_TaxCategory_ID(parent.getC_TaxCategory_ID());
			changed = true;
		}
		//
		return changed;
	}	//	setExpenseType
	
	/**
	 * 	Set Resource
	 *	@param parent resource
	 *	@return true if changed
	 */
	public boolean setResource (MResource parent)
	{
		boolean changed = false;
		if (!PRODUCTTYPE_Resource.equals(getProductType()))
		{
			setProductType(PRODUCTTYPE_Resource);
			changed = true;
		}
		if (parent.getS_Resource_ID() != getS_Resource_ID())
		{
			setS_Resource_ID(parent.getS_Resource_ID());
			changed = true;
		}
		if (parent.isActive() != isActive())
		{
			setIsActive(parent.isActive());
			changed = true;
		}
		//
		if (!parent.getValue().equals(getValue()))
		{
			setValue(parent.getValue());
			changed = true;
		}
		if (!parent.getName().equals(getName()))
		{
			setName(parent.getName());
			changed = true;
		}
		if ((parent.getDescription() == null && getDescription() != null)
			|| (parent.getDescription() != null && !parent.getDescription().equals(getDescription())))
		{
			setDescription(parent.getDescription());
			changed = true;
		}
		//
		return changed;
	}	//	setResource

	/**
	 * 	Set Resource Type
	 *	@param parent resource type
	 *	@return true if changed
	 */
	public boolean setResource (MResourceType parent)
	{
		boolean changed = false;
		if (PRODUCTTYPE_Resource.equals(getProductType()))
		{
			setProductType(PRODUCTTYPE_Resource);
			changed = true;
		}
		//
		if (parent.getC_UOM_ID() != getC_UOM_ID())
		{
			setC_UOM_ID(parent.getC_UOM_ID());
			changed = true;
		}
		if (parent.getM_Product_Category_ID() != getM_Product_Category_ID())
		{
			setM_Product_Category_ID(parent.getM_Product_Category_ID());
			changed = true;
		}
		if (parent.getC_TaxCategory_ID() != getC_TaxCategory_ID())
		{
			setC_TaxCategory_ID(parent.getC_TaxCategory_ID());
			changed = true;
		}
		//
		return changed;
	}	//	setResource
	
	
	/**	UOM Precision			*/
	private Integer		m_precision = null;
	
	/**
	 * 	Get UOM Standard Precision
	 *	@return UOM Standard Precision
	 */
	public int getUOMPrecision()
	{
		if (m_precision == null)
		{
			int C_UOM_ID = getC_UOM_ID();
			if (C_UOM_ID == 0)
				return 0;	//	EA
			m_precision = new Integer (MUOM.getPrecision(getCtx(), C_UOM_ID));
		}
		return m_precision.intValue();
	}	//	getUOMPrecision
	
	
	/**
	 * 	Create Asset Group for this product
	 *	@return asset group id
	 */
	public int getA_Asset_Group_ID()
	{
		MProductCategory pc = MProductCategory.get(getCtx(), getM_Product_Category_ID());
		return pc.getA_Asset_Group_ID();
	}	//	getA_Asset_Group_ID

	/**
	 * 	Create Asset for this product
	 *	@return true if asset is created
	 */
	public boolean isCreateAsset()
	{
		MProductCategory pc = MProductCategory.get(getCtx(), getM_Product_Category_ID());
		return pc.getA_Asset_Group_ID() != 0;
	}	//	isCreated

	/**
	 * 	Get Attribute Set
	 *	@return set or null
	 */
	public MAttributeSet getAttributeSet()
	{
		if (getM_AttributeSet_ID() != 0)
			return MAttributeSet.get(getCtx(), getM_AttributeSet_ID());
		return null;
	}	//	getAttributeSet
	
	/**
	 * 	Has the Product Instance Attribute
	 *	@return true if instance attributes
	 */
	public boolean isInstanceAttribute()
	{
		if (getM_AttributeSet_ID() == 0)
			return false;
		MAttributeSet mas = MAttributeSet.get(getCtx(), getM_AttributeSet_ID());
		return mas.isInstanceAttribute();
	}	//	isInstanceAttribute
	
	/**
	 * 	Create One Asset Per UOM
	 *	@return individual asset
	 */
	public boolean isOneAssetPerUOM()
	{
		MProductCategory pc = MProductCategory.get(getCtx(), getM_Product_Category_ID());
		if (pc.getA_Asset_Group_ID() == 0)
			return false;
		MAssetGroup ag = MAssetGroup.get(getCtx(), pc.getA_Asset_Group_ID());
		return ag.isOneAssetPerUOM();
	}	//	isOneAssetPerUOM
	
	/**
	 * 	Product is Item
	 *	@return true if item
	 */
	public boolean isItem()
	{
		return PRODUCTTYPE_Item.equals(getProductType());
	}	//	isItem
		
	/**
	 * 	Product is an Item and Stocked
	 *	@return true if stocked and item
	 */
	@Override
	public boolean isStocked ()
	{
		return super.isStocked() && isItem();
	}	//	isStocked
	
	/**
	 * 	Is Service
	 *	@return true if service (resource, online)
	 */
	public boolean isService()
	{
		//	PRODUCTTYPE_Service, PRODUCTTYPE_Resource, PRODUCTTYPE_Online
		return !isItem();	//	
	}	//	isService
	
	/**
	 * 	Get UOM Symbol
	 *	@return UOM Symbol
	 */
	public String getUOMSymbol()
	{
		int C_UOM_ID = getC_UOM_ID();
		if (C_UOM_ID == 0)
			return "";
		return MUOM.get(getCtx(), C_UOM_ID).getUOMSymbol();
	}	//	getUOMSymbol
		
	/**
	 * 	Get Active(!) Product Downloads
	 * 	@param requery requery
	 *	@return array of downloads
	 */
	public MProductDownload[] getProductDownloads (boolean requery)
	{
		if (m_downloads != null && !requery)
			return m_downloads;
		//
		List<MProductDownload> list = new Query(getCtx(), I_M_ProductDownload.Table_Name, "M_Product_ID=?", get_TrxName())
										.setOnlyActiveRecords(true)
										.setOrderBy(I_M_ProductDownload.COLUMNNAME_Name)
										.setParameters(get_ID())
										.list();
		m_downloads = list.toArray(new MProductDownload[list.size()]);
		return m_downloads;
	}	//	getProductDownloads
	
	/**
	 * 	Does the product have downloads
	 *	@return true if downloads exists
	 */
	public boolean hasDownloads()
	{
		return getProductDownloads(false).length > 0;
	}	//	hasDownloads
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MProduct[");
		sb.append(get_ID()).append("-").append(getValue())
			.append("]");
		return sb.toString();
	}	//	toString

	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		//	Check Storage
		/*if (!newRecord && 	//	
			((is_ValueChanged("IsActive") && !isActive())		//	now not active 
			|| (is_ValueChanged("IsStocked") && !isStocked())	//	now not stocked
			|| (is_ValueChanged("ProductType") 					//	from Item
				&& PRODUCTTYPE_Item.equals(get_ValueOld("ProductType")))))
		{
			MStorage[] storages = MStorage.getOfProduct(getCtx(), get_ID(), get_TrxName());
			BigDecimal OnHand = Env.ZERO;
			BigDecimal Ordered = Env.ZERO;
			BigDecimal Reserved = Env.ZERO;
			for (int i = 0; i < storages.length; i++)
			{
				OnHand = OnHand.add(storages[i].getQtyOnHand());
				Ordered = Ordered.add(storages[i].getQtyOrdered());
				Reserved = Reserved.add(storages[i].getQtyReserved());
			}
			String errMsg = "";
			if (OnHand.signum() != 0)
				errMsg = "@QtyOnHand@ = " + OnHand;
			if (Ordered.signum() != 0)
				errMsg += " - @QtyOrdered@ = " + Ordered;
			if (Reserved.signum() != 0)
				errMsg += " - @QtyReserved@" + Reserved;
			if (errMsg.length() > 0)
			{
				log.saveError("Error", Msg.parseTranslation(getCtx(), errMsg)); 
				return false;
			}
		}*/	//	storage
	
		//OpenUp. Nicolas Sarlabos. 18/11/2015. #5089.
		// it checks if UOM has been changed , if so disallow the change if the condition is true.
		/*if ((!newRecord) && is_ValueChanged("C_UOM_ID") && hasInventoryOrCost ()) {
			log.saveError("Error", Msg.getMsg(getCtx(), "SaveUomError"));
			return false; 
		}*/
		//Fin OpenUp.
		
		//	Reset Stocked if not Item
		//AZ Goodwill: Bug Fix isStocked always return false
		//if (isStocked() && !PRODUCTTYPE_Item.equals(getProductType()))
		if (!PRODUCTTYPE_Item.equals(getProductType()))
			setIsStocked(false);
		
		//	UOM reset
		if (m_precision != null && is_ValueChanged("C_UOM_ID"))
			m_precision = null;
		
		// AttributeSetInstance reset
		if (is_ValueChanged(COLUMNNAME_M_AttributeSet_ID))
		{
			MAttributeSetInstance asi = new MAttributeSetInstance(getCtx(), getM_AttributeSetInstance_ID(), get_TrxName());
			setM_AttributeSetInstance_ID(0);
			// Delete the old m_attributesetinstance
			try {
				asi.deleteEx(true, get_TrxName());
			} catch (AdempiereException ex)
			{
				log.saveError("Error", "Error deleting the AttributeSetInstance");
				return false;
			}
		}
		
		//OpenUp Nicolas Sarlabos 26/06/2012 #1036.Se impide la repeticion de codigos de producto
		MProduct prod = MProduct.forValue(getCtx(), this.getValue(), null);
		if (prod != null) {
			if(prod.getM_Product_ID() != this.get_ID())
				throw new AdempiereException("Ya existe un producto con el cï¿½digo " + this.getValue());
		}	
		//Fin OpenUp Nicolas Sarlabos 26/06/2012 #1036
		
		//INI --> OpenUp SBouissa 13-10-2012 Issue# 4902
		//Si no es un nuevo registro se cambio unidadd de medida, es prod "vendible" y es retail -  
		//Se debe actualizar los atributos correspondiente y al final se deben calcular el valor hexa 
		//que se infiere de los atributos de este producto
		if(MSysConfig.getBooleanValue("UY_RETAIL_INTERFACE", false, this.getAD_Client_ID())) {
			if(!newRecord && this.isSold()){
				if( is_ValueChanged("C_UOM_ID")){
					updateAttributosForCUOM(this.getC_UOM_ID(),this.get_ID());
					this.updateVerssionNo(false);//Se calcula hexa sin guardar cambios ya que estoy en befor save
					//OpenUp SBT 19-02-2016 si el producto es pesable debe ser enviado a balanza Issue #5319
					if(this.getC_UOM().getName().equalsIgnoreCase("KG")){
						this.set_Value("UsaBalanza", "Y");
					}
				}
				if( is_ValueChanged("M_Product_Tandem_ID")){
					updateAttributosForAsocTandem(this.get_Value("M_Product_Tandem_ID"),this.get_ID());
					this.updateVerssionNo(false);//Se calcula hexa sin guardar cambios ya que estoy en befor save
				}
			}
			
			/*
			if (newRecord && this.isSold() && this.isActive()){
				if (this.get_Value("PriceActual") == null){
					throw new AdempiereException("Debe indicar Precio de Venta para este nuevo producto.");
				}
				else{
					BigDecimal price = (BigDecimal)this.get_Value("PriceActual");
					if (price.compareTo(Env.ZERO) <= 0){
						throw new AdempiereException("Debe indicar Precio de Venta para este nuevo producto.");
					}
				}
			}
			*/
	
		}//FIN --> OpenUp SBouissa 13-10-2012 Issue# 4902
		return true;
	}	//	beforeSave

	/**
	 * Seteo atributo 9 en Y si se setea tandem, seteo atributo n9 en N si se quita tandem
	 * @author OpenUp SBT Issue#  13/10/2015 
	 * @param get_Value
	 * @param get_ID
	 */
	private void updateAttributosForAsocTandem(Object idTandem, int mprodId) {
		try{
			
			if(null==idTandem){
				String sql = "UPDATE M_ProductAttribute set IsSelected = 'N' WHERE M_Product_ID = "+mprodId
						+" AND IsActive='Y' AND UY_ProdAttribute_ID In ("
						+ "SELECT UY_ProdAttribute_ID FROM UY_ProdAttribute WHERE value IN ('attr_9')) ";
				DB.executeUpdateEx(sql, get_TrxName());
			}else{
				int idtandem = (Integer) idTandem;
				if(0<idtandem){
					String sql = "UPDATE M_ProductAttribute set IsSelected = 'Y' WHERE M_Product_ID = "+mprodId
							+" AND IsActive='Y' AND UY_ProdAttribute_ID In ("
							+ "SELECT UY_ProdAttribute_ID FROM UY_ProdAttribute WHERE value IN ('attr_9')) ";
					DB.executeUpdateEx(sql, get_TrxName());
				}
			}

		}catch(Exception e){
			e.getMessage();
		}
		
	}
	
	/**
	 * Indicar la selección del atributo 1-Comestible
	 * IsSelected = 'Y'
	 * @author OpenUp SBT Issue#  21/3/2016 14:52:54
	 * @param get_ID
	 */
	private void updateAttributoComestible(int mprodID) {
		try{	
			String sql = "UPDATE M_ProductAttribute set IsSelected = 'Y' WHERE M_Product_ID = "+mprodID
						+" AND IsActive='Y' AND UY_ProdAttribute_ID In ("
						+ "SELECT UY_ProdAttribute_ID FROM UY_ProdAttribute WHERE value IN ('attr_1')) ";
			DB.executeUpdateEx(sql, get_TrxName());
		}catch(Exception e){
			e.getMessage();
		}
	}
	

	/**
	 * Seteo atributos en true a partir de la unidad de medida
	 * @author OpenUp SBT Issue#4902  13/10/2015 
	 * @param c_UOM_ID
	 */
	private void updateAttributosForCUOM(int c_UOM_ID, int mprodId) {
		MUOM unidad = new MUOM(getCtx(), c_UOM_ID, null);
		try{
			if(0<unidad.get_ID()){
				if(unidad.getName().equals("KG")){
					
					String sql = "UPDATE M_ProductAttribute set IsSelected = 'Y' WHERE M_Product_ID = "+mprodId
							+"  AND IsActive='Y' AND UY_ProdAttribute_ID In ("
							+ "SELECT UY_ProdAttribute_ID FROM UY_ProdAttribute WHERE value IN ('attr_2','attr_13','attr_14','attr_32')) ";
					DB.executeUpdateEx(sql, get_TrxName());
					
					String sql2 = "UPDATE M_ProductAttribute set IsSelected = 'N' WHERE M_Product_ID = "+mprodId
							+"  AND IsActive='Y' AND UY_ProdAttribute_ID In ("
							+ "SELECT UY_ProdAttribute_ID FROM UY_ProdAttribute WHERE value IN ('attr_11')) ";
					DB.executeUpdateEx(sql2, get_TrxName());
					
					this.set_Value("UsaBalanza", true);
					
				}else if(unidad.getName().equals("Unidad Simple")){
					
					String sql = "UPDATE M_ProductAttribute set IsSelected = 'Y' WHERE M_Product_ID = "+mprodId
							+"  AND IsActive='Y' AND UY_ProdAttribute_ID In ("
							+ "SELECT UY_ProdAttribute_ID FROM UY_ProdAttribute WHERE value IN ('attr_11','attr_32')) ";
					DB.executeUpdateEx(sql, get_TrxName());
					
					String sql2 = "UPDATE M_ProductAttribute set IsSelected = 'N' WHERE M_Product_ID = "+mprodId
							+"  AND IsActive='Y' AND UY_ProdAttribute_ID In ("
							+ "SELECT UY_ProdAttribute_ID FROM UY_ProdAttribute WHERE value IN ('attr_2','attr_13','attr_14')) ";
					DB.executeUpdateEx(sql2, get_TrxName());
				}
			
			}
		}catch(Exception e){
			e.getMessage();
		}
	}

	/**
	 * 	HasInventoryOrCost 
	 *	@return true if it has Inventory or Cost
	 */
	protected boolean hasInventoryOrCost ()
	{
		//check if it has transactions 
		boolean hasTrx = new Query(getCtx(), MTransaction.Table_Name,
										MTransaction.COLUMNNAME_M_Product_ID+"=?", get_TrxName())
								.setOnlyActiveRecords(true)
								.setParameters(new Object[]{get_ID()})
								.match();
		if (hasTrx)
		{
			return true;
		}

		//check if it has cost
		boolean hasCosts = new Query(getCtx(), I_M_CostDetail.Table_Name,
				I_M_CostDetail.COLUMNNAME_M_Product_ID+"=?", get_TrxName())
									.setOnlyActiveRecords(true)
									.setParameters(get_ID())
									.match();
		if (hasCosts)
		{
			return true;
		}

		return false;
	}
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		
		//	Value/Name change in Account
		if (!newRecord && (is_ValueChanged("Value") || is_ValueChanged("Name")))
			MAccount.updateValueDescription(getCtx(), "M_Product_ID=" + getM_Product_ID(), get_TrxName());
		
		//	Name/Description Change in Asset	MAsset.setValueNameDescription
		if (!newRecord && (is_ValueChanged("Name") || is_ValueChanged("Description")))
		{
			String sql = "UPDATE A_Asset a "
				+ "SET (Name, Description)="
					+ "(SELECT SUBSTR((SELECT bp.Name FROM C_BPartner bp WHERE bp.C_BPartner_ID=a.C_BPartner_ID) || ' - ' || p.Name,1,60), p.Description "
					+ "FROM M_Product p "
					+ "WHERE p.M_Product_ID=a.M_Product_ID) "
				+ "WHERE IsActive='Y'"
			//	+ " AND GuaranteeDate > SysDate"
				+ "  AND M_Product_ID=" + getM_Product_ID();
			int no = DB.executeUpdate(sql, get_TrxName());
			log.fine("Asset Description updated #" + no);
		}
		
		//	New - Acct, Tree, Old Costing
		if (newRecord)
		{
			insert_Accounting("M_Product_Acct", "M_Product_Category_Acct",
				"p.M_Product_Category_ID=" + getM_Product_Category_ID());
			insert_Tree(X_AD_Tree.TREETYPE_Product);
			//
			MAcctSchema[] mass = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), get_TrxName());
			for (int i = 0; i < mass.length; i++)
			{
				//	Old
				MProductCosting pcOld = new MProductCosting(this, mass[i].getC_AcctSchema_ID());
				pcOld.save();
			}
		}
				
		//	New Costing
		if (newRecord || is_ValueChanged("M_Product_Category_ID"))
			MCost.create(this);

		// OpenUp. Ines Fernandez. 17/06/2015. Issue #4404
		// Para Retail, si tengo que interfacear productos, proceso.
		if (MSysConfig.getBooleanValue("UY_RETAIL_INTERFACE", false, this.getAD_Client_ID())) {

			// Si el producto se VENDE, aplican cambios para interface con sistema de POS
			if (this.isSold()){

				//carga la lista de attributos en false
				if(newRecord){

					String sql1 = "select ad_sequence_id from ad_sequence where lower(name) like 'm_productattribute'";

					int seqID= DB.getSQLValueEx(null, sql1);
					String sql2="insert into m_productattribute (m_productattribute_id,ad_client_id,ad_org_id,isactive,created,createdby,"
							+ "updated,updatedby,uy_prodattribute_id,m_product_id,isselected,seqno)"
							+ "select nextID("+seqID+",'N'),prod.ad_client_id,prod.ad_org_id,'Y',now(),"+Env.getAD_User_ID(getCtx())+",now(),"+Env.getAD_User_ID(getCtx())+",a.uy_prodattribute_id,"
							+ "prod.m_product_id,'N',(a.uy_prodattribute_id - 999999)*10 "
							+ "from uy_prodattribute a, m_product prod"
							+ " where m_product_id = "+ this.get_ID();
					DB.executeUpdateEx(sql2, get_TrxName());
					
					//OpenUp SBouissa 21-03-2016 Issue #5623
					//Se setea el atributo comestible cuando se crea un producto.
					updateAttributoComestible(this.get_ID());
					//FIN OpenUp SBouissa 21-03-2016 Issue #5623
					
					//--INI OpenUp SBouissa 13-10-2015 Issue# 4902
					//Si el producto es nuevo a partir de la unidad de medida se setean los atributos correspondiente 
					if(0<this.getC_UOM_ID()){// si tiene seteado unidad de medida se setan los valores correspondientes de atributos
						updateAttributosForCUOM(this.getC_UOM_ID(),this.get_ID());
					}
					//Si el producto es nuevo y tiene seteado teandem se actauliza el atributo correspondiente
					if(null!=this.get_Value("M_Product_Tandem_ID") && 0<(Integer)this.get_Value("M_Product_Tandem_ID")){
						updateAttributosForAsocTandem(this.get_Value("M_Product_Tandem_ID"),this.get_ID());
					}
					if(0<this.getC_UOM_ID() || null!=this.get_Value("M_Product_Tandem_ID"))
						this.updateVerssionNo(true);//Se calcula hexa y se guardan cambios ya que estoy en el after save	
					//--FIN OpenUp SBouissa 13-10-2015 Issue# 4902
					
					if (this.get_Value("PriceActual") != null){
						BigDecimal price = (BigDecimal)this.get_Value("PriceActual");
						if (price.compareTo(Env.ZERO) > 0){
							MProductPrintLabel plabel = new MProductPrintLabel(getCtx(), 0, get_TrxName());
							plabel.setM_Product_ID(this.get_ID());
							plabel.saveEx();
						}
					}
					
				}//Fin newRecord

				if(!newRecord){
					
					if(!is_ValueChanged(X_M_Product.COLUMNNAME_Value)){ // Sino cambió el codigo interno del producto 
						if(is_ValueChanged("Value") || is_ValueChanged("Name") || is_ValueChanged("C_TaxCategory_ID")){				

							// Obtengo (si existe) registro de interface para este producto que
							// aun no haya sido leido
							
// para poner luego en produccion SBT 03-03-2016	MRTRetailInterface.actualizarProducto(getCtx(), this.getAD_Client_ID(), this.get_ID(), get_TrxName());
							
							MRTInterfaceProd it = (MRTInterfaceProd) MRTInterfaceProd
									.forProduct(getCtx(), this.get_ID(), get_TrxName());

							if ((it != null) && (it.get_ID() > 0)) {
								// Si ya tengo uno, lo actualizo con datos de la definicion del producto
								it.setUY_RT_Action_ID(MRTAction.forValue(getCtx(), "update", get_TrxName()).get_ID());
								it.updateInterface();
							}	
							else {
								// Nueva interface para este producto
								it = new MRTInterfaceProd(getCtx(), 0, get_TrxName());
								it.setM_Product_ID(this.get_ID());
								it.insertInterface(newRecord);
							}
							// OpenUp. Santiago Evans. 18/02/2016. Issue #5319
							// Se agrega una fila en la tabla de interfaz.
							MProductUpc upc = MProductUpc.forProduct(getCtx(), this.getM_Product_ID(), get_TrxName());
							if(this.get_ValueAsBoolean("UsaBalanza")){
								//OpenUp SBT 04/04/2016 Se cambia el valor 0 por el codigo interno del producto ya que unicamente se tienene en cuenta el codigo del mismo para enviar a las balanzas
								MRTRetailInterface.insertarProdABalanza(getCtx(), getAD_Client_ID(), this.getValue(), this.getM_Product_ID(), get_TrxName());
							}	
						}
					}else{//SBT 25/01/2016 Issue #5369
						//Se deben eliminar los codigos de barras asociados los tandoem y luego eliminar el prod con el value anterior
						//Luego insertar el nuevo producto con sus correspondientes codigos de barra y tandem
						String oldValue = (String) this.get_ValueOld(X_M_Product.COLUMNNAME_Value);
						
						if(null!=this.get_Value("M_Product_Tandem_ID")){
							//Elimino realación anterior tandem
							MRTInterfaceProd.insertInterfaceDeleteTandem(this,oldValue,getCtx(),get_TrxName());
						}
						
						List<MProductUpc> lstUPCs = MProductUpc.upcsForProduct(getCtx(), this.get_ID(), get_TrxName());
						for(MProductUpc upc : lstUPCs){
							//Elimino relación anterior de upc
							MRTInterfaceProd.insertInterfaceDeleteAE(this, oldValue,upc.getUPC(),getCtx(),get_TrxName());
						}								
							
						//Elimino el prdoucto con codigo de prod anterior
						MRTInterfaceProd.insertInterfaceDeleteProd(this, oldValue,getCtx(),get_TrxName());
						
						//Realizo el insert del producto
						MRTRetailInterface.crearArticulo(getCtx(), getAD_Client_ID(), this.get_ID(), get_TrxName());
						
						//Ingreso tandem
						if(null!=this.get_Value("M_Product_Tandem_ID")){
							MRTInterfaceProd.insertInterfaceFromTandem(this,
									Integer.valueOf(this.get_Value("M_Product_Tandem_ID").toString()),
								"insert",getCtx(),get_TrxName());
						}
						//Ingreso articulos equivalentesif(null!=lstUPCs){
						for(MProductUpc upc2 : lstUPCs){
							MRTRetailInterface.crearUPC(getCtx(), this.getAD_Client_ID(), upc2.getUPC(), 
									this.get_ID(), get_TrxName());
						}
						
					}
					

					if(is_ValueChanged("IsActive")){
						
						MProdAttribute prodAtr = MProdAttribute.forValue(getCtx(), "attr_29", get_TrxName());
						
						if(prodAtr!=null && prodAtr.get_ID()>0){
							
							MProductAttribute atrLine = MProductAttribute.forAttributeProd(getCtx(), prodAtr.get_ID(), this.get_ID(), get_TrxName());
							
							if(atrLine!=null && atrLine.get_ID()>0){
								//OpenUp sevans 19/02/2016 Issue #5319
								//Se controla que el producto sea pesable, en caso de serlo, se desactiva o activa del sistema de balanzas

								if(this.isActive()){									
									
									if(this.get_Value("UsaBalanza")!=null && this.get_ValueAsBoolean("UsaBalanza")){										
										MRTRetailInterface.insertarProdABalanza(getCtx(), getAD_Client_ID(), this.getValue(), this.getM_Product_ID(), get_TrxName());
									}										
									atrLine.setIsSelected(false);																	
								} else {									
									if(this.get_Value("UsaBalanza")!=null && this.get_ValueAsBoolean("UsaBalanza")){
										//OpenUp SBT 04/04/2016 Se cambia el valor 0 por el codigo interno del producto ya que unicamente se tienene en cuenta el codigo del mismo para enviar a las balanzas
										//MRTRetailInterface.eliminarProdDeBalanza(getCtx(), getAD_Client_ID(), "0", this.getM_Product_ID(), get_TrxName());
										MRTRetailInterface.eliminarProdDeBalanza(getCtx(), getAD_Client_ID(), this.getValue(), this.getM_Product_ID(), get_TrxName());
									}										
									atrLine.setIsSelected(true);							
								}
								
								atrLine.saveEx();
								
							}						
							
						}
											
					}					
					if(is_ValueChanged("UsaBalanza")){

						//OpenUp sevans 19/02/2016 Issue #5319
						//Se controla que el producto sea pesable, en caso de serlo, se desactiva o activa del sistema de balanzas

						if(this.get_ValueAsBoolean("UsaBalanza")){									
							MRTRetailInterface.insertarProdABalanza(getCtx(), getAD_Client_ID(), this.getValue(), this.getM_Product_ID(), get_TrxName());
							this.set_Value("UsaBalanza", "Y");																																	
						} else {																		
							MRTRetailInterface.eliminarProdDeBalanza(getCtx(), getAD_Client_ID(), this.getValue(), this.getM_Product_ID(), get_TrxName());
							this.set_Value("UsaBalanza", "N");
						}

					}
					
					//OpenUp SBT 15/01/2016 (Interfaceo Insercion nuevo producto)
					//COMENTADO TEMPORALMENTE HASTA PONER EN PRODUCCION
					//MRTRetailInterface.actualizarProducto(getCtx(), getAD_Client_ID(), this.get_ID(), get_TrxName());
				} else {

					// Nueva interface para este producto
//					MRTInterfaceProd it = new MRTInterfaceProd(getCtx(), 0, get_TrxName());
//					it.setM_Product_ID(this.get_ID());
//					it.insertInterface(newRecord);
					
					//OpenUp SBT 21/12/2015 (Interfaceo Insercion nuevo producto)
					//SE PONE EN PRODUCCIÓN - SE COMENTA TEMPORALMENTE LINEAS ANTERIOR LUEGO SE ELIMINARA.. 26/01/2016 - SBT
					MRTRetailInterface.crearArticulo(getCtx(), getAD_Client_ID(), this.get_ID(),get_TrxName());
				
				}	
				
				//Nueva interfaz por Tandem
				//if(this.isTandem() || this.get_ValueOldAsInt("M_Product_Tandem_ID")!=0)
				if(this.is_ValueChanged("M_Product_Tandem_ID"))	createTandemInterfaceLine(newRecord);
				// Fin OpenUp. Issue #4404

				// Para retail y productos que se venden, la primera vez se le da de alta en lista de precios de venta desde aca. 
				if (!this.get_ValueAsBoolean("IsSalesPriceSet")){
					// Verifico si producto no esta en version vigente de lista de venta seleccionada
					if (this.get_ValueAsInt("M_PriceList_ID") > 0){
						if (this.get_Value("PriceActual") != null){
							BigDecimal priceAmt = (BigDecimal)this.get_Value("PriceActual");
							if (priceAmt.compareTo(Env.ZERO) > 0){
								MPriceList priceList = new MPriceList(getCtx(), this.get_ValueAsInt("M_PriceList_ID"), get_TrxName());
								if (priceList.get_ID() > 0){
									MPriceListVersion listVersion = priceList.getVersionVigente(null);
									if (listVersion != null){
										MProductPrice prodPrice = MProductPrice.forVersionProduct(getCtx(), listVersion.get_ID(), this.get_ID(), get_TrxName());
										if (prodPrice == null){
											// Inserto nuevo producto en ultima version de lista sin necesidad de crear nueva version
											prodPrice = new MProductPrice(getCtx(), listVersion.get_ID(), this.get_ID(), get_TrxName());
											prodPrice.setPriceList(priceAmt.setScale(priceList.getStandardPrecision(), RoundingMode.HALF_UP));
											prodPrice.setPriceStd(priceAmt.setScale(priceList.getStandardPrecision(), RoundingMode.HALF_UP));
											prodPrice.setPriceLimit(priceAmt.setScale(priceList.getStandardPrecision(), RoundingMode.HALF_UP));
											prodPrice.set_Value("DateAction", TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY));
											prodPrice.saveEx();
											
											
										}
										//INI OpenUp SBT 08/12/2015 Issue #5170
										// Se debe interfacear cambio para que sea enviado al sistema de cajas (sisteco)
										MRTInterfaceProd it = (MRTInterfaceProd) MRTInterfaceProd.forProduct(getCtx(), this.get_ID(), get_TrxName());
										if ((it != null) && (it.get_ID() > 0)) {
											// Si ya tengo uno, lo actualizo con datos de la definicion del producto
											it.setUY_RT_Action_ID(MRTAction.forValue(getCtx(), "update", get_TrxName()).get_ID());
											it.updateInterface();
										}	
										else {
											// Nueva interface para este producto
											it = new MRTInterfaceProd(getCtx(), 0, get_TrxName());
											it.setM_Product_ID(this.get_ID());
											it.insertInterface(newRecord);
										}
										// FIN OpenUp SBT 08/12/2015 Issue #5170
										// Marco producto como utilizado en una lista de venta
										DB.executeUpdateEx("update m_product set IsSalesPriceSet='Y' where m_product_id ="+ this.get_ID(), get_TrxName());
										//this.set_Value("IsSalesPriceSet", true);
									}
								}
							}
						}
					}
				}
				
			}// Fin IsSold
			
			
		}
		
		return success;

	}	//	afterSave

	/***
	 * Impacta en la tabla de interfaz para productos al asociar/desasociar/modificar el producto Tandem asociado
	 * OpenUp Ltda. Issue #4404 
	 * @author INes Fernandez - 30/06/2015
	 * @see
	 */
	private void createTandemInterfaceLine(boolean newRecord){
		int oldTandem_ID = this.get_ValueOldAsInt("M_Product_Tandem_ID");
		if (this.isTandem()) {
			if (newRecord) {// crea un insert
				MRTInterfaceProd.insertInterfaceFromTandem(this,
						get_ValueAsInt("M_Product_Tandem_ID"), "insert",
						getCtx(), get_TrxName());
			} 
			else {// es un update del producto: un delete y un insert
				
				if (oldTandem_ID == 0) {// se agrega
					MRTInterfaceProd.insertInterfaceFromTandem(this,
							this.get_ValueAsInt("M_Product_Tandem_ID"),
							"insert", getCtx(), get_TrxName());
				} else {// se cambia por otro tandem
					MRTInterfaceProd.insertInterfaceFromTandem(this,
							oldTandem_ID, "delete", getCtx(), get_TrxName());
					MRTInterfaceProd.insertInterfaceFromTandem(this,
							this.get_ValueAsInt("M_Product_Tandem_ID"),
							"insert", getCtx(), get_TrxName());
				}
			}
		}
		else{//ya no es tandem, es un delete
			MRTInterfaceProd.insertInterfaceFromTandem(this,
					oldTandem_ID, "delete", getCtx(), get_TrxName());
		}
	}

	@Override
	protected boolean beforeDelete ()
	{
		if (PRODUCTTYPE_Resource.equals(getProductType()) && getS_Resource_ID() > 0)
		{
			throw new AdempiereException("@S_Resource_ID@<>0");
		}
		//	Check Storage
		if (isStocked() || PRODUCTTYPE_Item.equals(getProductType()))
		{
			MStorage[] storages = MStorage.getOfProduct(getCtx(), get_ID(), get_TrxName());
			BigDecimal OnHand = Env.ZERO;
			BigDecimal Ordered = Env.ZERO;
			BigDecimal Reserved = Env.ZERO;
			for (int i = 0; i < storages.length; i++)
			{
				OnHand = OnHand.add(storages[i].getQtyOnHand());
				Ordered = OnHand.add(storages[i].getQtyOrdered());
				Reserved = OnHand.add(storages[i].getQtyReserved());
			}
			String errMsg = "";
			if (OnHand.signum() != 0)
				errMsg = "@QtyOnHand@ = " + OnHand;
			if (Ordered.signum() != 0)
				errMsg += " - @QtyOrdered@ = " + Ordered;
			if (Reserved.signum() != 0)
				errMsg += " - @QtyReserved@" + Reserved;
			if (errMsg.length() > 0)
			{
				log.saveError("Error", Msg.parseTranslation(getCtx(), errMsg)); 
				return false;
			}
			
		}
		//	delete costing
		MProductCosting[] costings = MProductCosting.getOfProduct(getCtx(), get_ID(), get_TrxName());
		for (int i = 0; i < costings.length; i++)
			costings[i].delete(true, get_TrxName());
		
		MCost.delete(this);
		
		// [ 1674225 ] Delete Product: Costing deletion error
		/*MAcctSchema[] mass = MAcctSchema.getClientAcctSchema(getCtx(),getAD_Client_ID(), get_TrxName());
		for(int i=0; i<mass.length; i++)
		{
			// Get Cost Elements
			MCostElement[] ces = MCostElement.getMaterialWithCostingMethods(this);
			MCostElement ce = null;
			for(int j=0; j<ces.length; j++)
			{
				if(MCostElement.COSTINGMETHOD_StandardCosting.equals(ces[i].getCostingMethod()))
				{
					ce = ces[i];
					break;
				}
			}
			
			if(ce == null)
				continue;
			
			MCost mcost = MCost.get(this, 0, mass[i], 0, ce.getM_CostElement_ID());
			mcost.delete(true, get_TrxName());
		}*/
		
		// @Trifon Delete Product UOM Conversion
		final String whereClause = MProduct.COLUMNNAME_M_Product_ID +"=?";
		List<MUOMConversion> conversions = new Query(getCtx(), I_C_UOM_Conversion.Table_Name, whereClause, get_TrxName())
			.setClient_ID()
			.setParameters( get_ID() )
			.setOnlyActiveRecords( false )
			.list();
		for(MUOMConversion conversion: conversions)
		{	
			conversion.deleteEx(true);
		}
		// @Trifon Delete Product Downloads
		List<MProductDownload> downloads = new Query(getCtx(), I_M_ProductDownload.Table_Name, whereClause, get_TrxName())
			.setClient_ID()
			.setParameters( get_ID() )
			.setOnlyActiveRecords( false )
			.list();
		for(MProductDownload download : downloads)
		{	
			download.deleteEx(true);
		}
		
		//
		return delete_Accounting("M_Product_Acct"); 
	}	//	beforeDelete
	
	@Override
	protected boolean afterDelete (boolean success)
	{
		if (success)
			delete_Tree(X_AD_Tree.TREETYPE_Product);
		return success;
	}	//	afterDelete
	
	/**
	 * Get attribute instance for this product by attribute name
	 * @param name
	 * @param trxName
	 * @return
	 */
	public MAttributeInstance getAttributeInstance(String name, String trxName) {
		MAttributeInstance instance = null;
		
		MTable table = MTable.get(Env.getCtx(), MAttribute.Table_ID);
		MAttribute attribute = (MAttribute)table.getPO("Name = ?", new Object[]{name}, trxName);
		if ( attribute == null ) return null;
		table = MTable.get(Env.getCtx(), MAttributeInstance.Table_ID);
		instance = (MAttributeInstance)table.getPO(
				MAttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID + "=?" 
				+ " and " + MAttributeInstance.COLUMNNAME_M_Attribute_ID + "=?" ,
				new Object[]{getM_AttributeSetInstance_ID(), attribute.getM_Attribute_ID()},
				trxName);
		return instance;
	}

	/**
	 * Gets Material Management Policy.
	 * Tries: Product Category, Client (in this order) 
	 * @return Material Management Policy
	 */
	public String getMMPolicy() {
		MProductCategory pc = MProductCategory.get(getCtx(), getM_Product_Category_ID());
		String MMPolicy = pc.getMMPolicy();
		if (MMPolicy == null || MMPolicy.length() == 0)
			MMPolicy = MClient.get(getCtx()).getMMPolicy();
		return MMPolicy;
	}
	
	/**
	 * Check if ASI is mandatory
	 * @param isSOTrx is outgoing trx?
	 * @return true if ASI is mandatory, false otherwise
	 */
	public boolean isASIMandatory(boolean isSOTrx) {
		//
		//	If CostingLevel is BatchLot ASI is always mandatory - check all client acct schemas
		MAcctSchema[] mass = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), get_TrxName());
		for (MAcctSchema as : mass)
		{
			String cl = getCostingLevel(as);
			if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(cl)) {
				return true;
			}
		}
		//
		// Check Attribute Set settings
		int M_AttributeSet_ID = getM_AttributeSet_ID();
		if (M_AttributeSet_ID != 0)
		{
			MAttributeSet mas = MAttributeSet.get(getCtx(), M_AttributeSet_ID);
			if (mas == null || !mas.isInstanceAttribute())
				return false;
			// Outgoing transaction
			else if (isSOTrx)
				return mas.isMandatory();
			// Incoming transaction
			else // isSOTrx == false
				return mas.isMandatoryAlways();
		}
		//
		// Default not mandatory
		return false;
	}
	
	/**
	 * Get Product Costing Level
	 * @param as accounting schema
	 * @return product costing level
	 */
	public String getCostingLevel(MAcctSchema as)
	{
		MProductCategoryAcct pca = MProductCategoryAcct.get(getCtx(), getM_Product_Category_ID(), as.get_ID(), get_TrxName());
		
		// OpenUp. Gabriel Vila. 24/10/2012. Issue #83.
		// Puede pasar que la categoria no tenga cuentas asociadas, verifico si null para no tener problemas.
		// Comento codigo original y sustituyo.
		String costingLevel = null;
		if (pca != null){
			costingLevel = pca.getCostingLevel();
		}

		if (costingLevel == null)
		{
			costingLevel = as.getCostingLevel();
		}		
		
		return costingLevel;
		
		/*
		String costingLevel = pca.getCostingLevel();
		if (costingLevel == null)
		{
			costingLevel = as.getCostingLevel();
		}
		return costingLevel;
		*/
		
		// Fin OpenUp. Issue #83.
	}
	
	/**
	 * Get Product Costing Method
	 * @param C_AcctSchema_ID accounting schema ID
	 * @return product costing method
	 */
	public String getCostingMethod(MAcctSchema as)
	{
		MProductCategoryAcct pca = MProductCategoryAcct.get(getCtx(), getM_Product_Category_ID(), as.get_ID(), get_TrxName());
		
		if(pca!=null){
			
			String costingMethod = pca.getCostingMethod();
			if (costingMethod == null)
			{
				costingMethod = as.getCostingMethod();
			}
			return costingMethod;		
			
		} else return "";
		
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene factor de UV por defecto de este Producto.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 02/01/2011
	 */
	public BigDecimal getFactorUVDefualt(){
		int idUV = MUOM.getIDFromSymbol(this.get_ValueAsString("uy_unidadventa"));
		return MUOMConversion.getProductRateFrom(Env.getCtx(), this.getM_Product_ID(), idUV);
	}

	/**
	 * OpenUp.	
	 * Descripcion : Get pero dentro de una transaction.
	 * @param ctx
	 * @param M_Product_ID
	 * @param trxName
	 * @return
	 * @author  Administrador 
	 * Fecha : 31/03/2011
	 */
	public static MProduct get (Properties ctx, int M_Product_ID, String trxName)
	{
		if (M_Product_ID <= 0)
		{
			return null;
		}
		Integer key = new Integer (M_Product_ID);
		MProduct retValue = (MProduct) s_cache.get (key);
		if (retValue != null)
		{
			return retValue;
		}
		retValue = new MProduct (ctx, M_Product_ID, trxName);

		if (retValue.get_ID () != 0)
		{
			s_cache.put (key, retValue);
		}
		return retValue;
	}	//	get
	
	//OpenUp M.R. 21-06-2011 Agrego metodo de obtener el costo centralizado desde el producto
	public BigDecimal getCostofechaHasta(Timestamp fechaCosteo){
		
		return this.recursivoCostos(this.get_ID(), BigDecimal.ONE, fechaCosteo).setScale(5, BigDecimal.ROUND_HALF_UP);
	}
	
	private BigDecimal recursivoCostos(int productID,BigDecimal cantidad, Timestamp fechaCosteo){

		
		boolean tieneHijos = false;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal aux=Env.ZERO;		
		
		try{
			// Busco hijos
				
			sql = " SELECT bomline.m_product_id,bomline.qtybom "+
		 	" FROM pp_product_bom bom"+
		 	" INNER JOIN pp_product_bomline bomline ON bomline.pp_product_bom_id=bom.pp_product_bom_id"+
		 	" INNER JOIN m_product m_product ON m_product.m_product_id=bom.m_product_id"+
		 	" WHERE m_product.ad_client_id ="+ Env.getAD_Client_ID(getCtx())+ "AND m_product.isactive ='Y' " +
		 	// OpenUp nicolas garcia Issue#734
		 	" AND bom.IsActive = 'Y' "+
		 	" AND bom.m_product_id ="+ productID+" ORDER BY m_product.value asc ";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			// ProcesoHijos
			while (rs.next()){
				// Si tiene Hijos
				tieneHijos=true;					//el linebom en el llamado se convierte en bom
				aux = aux.add(recursivoCostos(rs.getInt("m_product_id"), (rs.getBigDecimal("qtybom").multiply(cantidad)), fechaCosteo));				
			}
			//Si no tiene hijos
			//Significa que se llego al limite, Aqui recien se calcula precios
			if(!(tieneHijos)){
				aux = aux.add(getCostoProducto(productID,cantidad, fechaCosteo ));

			}
			
		

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return aux;


	}	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion : Primero trata de traer el costo de la ultima factura, si no lo encuentra trae el costo stander. Issue #750.
	 * @param mProductID
	 * @param cantidad
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 31/05/2011
	 */
	public BigDecimal getCostoProducto(int mProductID, BigDecimal cantidad, Timestamp fechaCosteo) { 

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String whereFiltros ="";
		BigDecimal salida = Env.ZERO;
		
		//OpenUp. Nicolas Sarlabos. 03/12/2015. #5001.
		if (fechaCosteo!=null) whereFiltros += " AND a.fechauf <=(date '"+fechaCosteo+"') ";
		//Fin OpenUp.
		try{
			
			sql = " SELECT coalesce(costoUF,0) as costoUF,fechauf " +
				  " FROM vuy_costolastinvoice a " +
				  " WHERE a.ad_client_id =" + this.getAD_Client_ID() + 	whereFiltros+			  
				  " AND a.M_Product_ID =" + mProductID+
				  " ORDER BY fechauf desc";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			
			Timestamp fecha =null;
			//Ultima Factura
			if (rs.next()){
				fecha=rs.getTimestamp("fechauf");
				salida = (rs.getBigDecimal("costoUF")).multiply(cantidad);				
			} 
			
			//ISSUE 730 
			//Si tiene ultima factura
			if(salida.compareTo(BigDecimal.ZERO)>0){
				//Pregunto si hay factura importacion para ese producto que sea mayor a la fecha de ultam fact
					DB.close(rs, pstmt);
					rs = null; pstmt = null;
					
					sql = "select 'facImp' as tipo,c_invoice.dateinvoiced FROM c_invoiceline LEFT JOIN c_invoice ON c_invoice.c_invoice_id=c_invoiceline.c_invoice_id " +
						" WHERE c_invoice.c_doctype_id =1000060 AND M_Product_ID =" + mProductID + "AND dateinvoiced>='"+fecha+"'"+
						" ORDER BY c_invoice.dateinvoiced desc";
				
					pstmt = DB.prepareStatement (sql, null);
					rs = pstmt.executeQuery ();
					//Si existe uno se usa costo estander
					if (rs.next()){
						salida = Env.ZERO; //Cero entra al calculo de costo estander.
					}
			}
			//----
			
			//Si es cero ya sea porque no tiene coso ultia fact o porque el mismo es cero.
			//Costo estander
			if(salida.compareTo(BigDecimal.ZERO)<=0){
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
				
				sql = " SELECT costo_est FROM uy_v_costosall  " +
				  " WHERE M_Product_ID =" + mProductID;
			
				pstmt = DB.prepareStatement (sql, null);
				rs = pstmt.executeQuery ();
			
				if (rs.next()){
					salida = (rs.getBigDecimal("costo_est")).multiply(cantidad);
					
				}
			}
			

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
			
		return salida;
	}

/**
	 * 
	 * OpenUp.	
	 * Descripcion :Issue #888 Se obtiene el almacen por defecto
	 * @param mProductID
	 * @param cantidad
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 10/07/2011
	 */
	public static int getDefaultWareHouse(int productID) {
		String sql = "SELECT coalesce(UY_ProductCategoryWareHouse.M_WareHouse_ID,0)  "
				+ "FROM UY_ProductCategoryWareHouse WHERE IsDefault='Y' AND UY_ProductCategoryWareHouse.M_Product_Category_ID="
				+ "(SELECT M_Product.M_Product_Category_ID FROM M_Product WHERE M_Product.M_Product_ID =" + productID + ")";
		return DB.getSQLValue(null, sql);
	}// Fin OpenUp

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Issue #869
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 02/11/2011
	 */
	public static Integer[] getFathersBom(int productID) {

		List<Integer> list = new ArrayList<Integer>(); // Selected BOM Lines
															// Only

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = "SELECT m_product_id from pp_product_bom WHERE pp_product_bom_id in (SELECT pp_product_bom_id FROM pp_product_bomline WHERE m_product_id ="
					+ productID + ")  " +
				  " and m_product_id != " + productID +	
				  " GROUP BY m_product_id  ";

			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getInt("m_product_id"));
			}

		} catch (Exception e) {
			// log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return list.toArray(new Integer[list.size()]);
	}

	/**
	 * 
	 * OpenUp.	#946
	 * Descripcion : Metodo que devuelve un arraylist conteniendo los datos de la implosion de un producto
	 * @author  Nicolas Sarlabos
	 * Fecha : 01/02/2012
	 */

	public ArrayList<ProdImplosion> getProductosImplosion() {

		//defino arraylist a devolver
		ArrayList<ProdImplosion> salida = new ArrayList<ProdImplosion>();

		if (this.get_ID() > 0) {

			String sql = "";
			ResultSet rs = null;
			PreparedStatement pstmt = null;

			sql = "select *,(select uy_nivel from m_product_category where m_product_category_id=categoria) as nivel"
					+ " from(select l.m_product_id as insumo_id,p.name insumo_nom,(select m_product_id from m_product mp where mp.m_product_id=b.m_product_id) as prod_id,"
					+ " (select value from m_product mp where mp.m_product_id=b.m_product_id) as prod_value,"
					+ " (select name from m_product mp where mp.m_product_id=b.m_product_id) as prod_nom,l.qtybom as cant,"
					+ " (select name from c_uom where c_uom_id=l.c_uom_id) as unidad,(select m_product_category_id from m_product mp where mp.m_product_id=b.m_product_id)"
					+ " as categoria,(select uy_linea_negocio_id from m_product mp where mp.m_product_id=b.m_product_id) as linea" + " from m_product p"
					+ " inner join pp_product_bomline l on p.m_product_id=l.m_product_id"
					+ " inner join pp_product_bom b on l.pp_product_bom_id=b.pp_product_bom_id" + " where l.m_product_id=? and p.isactive='Y' and b.isactive='Y' order by b.m_product_id) as f";

			try {
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, this.get_ID());
				rs = pstmt.executeQuery();

				while (rs.next()) {

					ProdImplosion p = new ProdImplosion();
					p.setHijo_id(rs.getInt("insumo_id"));
					p.setHijo_nom(rs.getString("insumo_nom"));
					int padreID = rs.getInt("prod_id");
					p.setPadre_id(padreID);
					p.setPadre_value(rs.getString("prod_value"));
					String padreNom = rs.getString("prod_nom").replace("'", " ");
					p.setPadre_nom(padreNom);
					p.setCant(rs.getBigDecimal("cant").setScale(8, RoundingMode.HALF_DOWN));
					p.setUnidad(rs.getString("unidad"));
					p.setCategoria_id(rs.getInt("categoria"));
					p.setLinea_id(rs.getInt("linea"));
					p.setNivel(rs.getInt("nivel"));

					MProduct prodHijo = new MProduct(getCtx(), padreID, null);
					p.setList(prodHijo.getProductosImplosion());
				
					salida.add(p);
				}

			} catch (Exception e) {
				throw new AdempiereException(e.getMessage());
			} finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

		}

		return salida;

	}

	/**
	 * 
	 * OpenUp.	#1022
	 * Descripcion : Metodo que devuelve un arraylist conteniendo los datos de la implosion de un producto, con la posibilidad de recibir
	 * parametros para filtrar categoria del producto y nivel
	 * @author  Nicolas Sarlabos
	 * Fecha : 28/05/2012
	 */

	public ArrayList<ProdImplosion> getProductosImplosion(int mProdCatID,int nivel) {

		//defino arraylist a devolver
		ArrayList<ProdImplosion> salida = new ArrayList<ProdImplosion>();

		if (this.get_ID() > 0) {
			
			String and = "";
			String where = "";
			String sql = "";
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			if(mProdCatID > 0) and += " AND p.m_product_category_id=" + mProdCatID;
			if(nivel >=0) where += " WHERE nivel=" + nivel;

			sql = "select * from(select *,(select uy_nivel from m_product_category where m_product_category_id=categoria) as nivel"
					+ " from(select l.m_product_id as insumo_id,p.name insumo_nom,(select m_product_id from m_product mp where mp.m_product_id=b.m_product_id) as prod_id,"
					+ " (select value from m_product mp where mp.m_product_id=b.m_product_id) as prod_value,"
					+ " (select name from m_product mp where mp.m_product_id=b.m_product_id) as prod_nom,l.qtybom as cant,"
					+ " (select name from c_uom where c_uom_id=l.c_uom_id) as unidad,(select m_product_category_id from m_product mp where mp.m_product_id=b.m_product_id)"
					+ " as categoria,(select uy_linea_negocio_id from m_product mp where mp.m_product_id=b.m_product_id) as linea" + " from m_product p"
					+ " inner join pp_product_bomline l on p.m_product_id=l.m_product_id"
					+ " inner join pp_product_bom b on l.pp_product_bom_id=b.pp_product_bom_id" + " where l.m_product_id=? and p.isactive='Y' and b.isactive='Y'"+ and + " order by b.m_product_id) as f) as g " +
					where;

			try {
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, this.get_ID());
				rs = pstmt.executeQuery();

				while (rs.next()) {

					ProdImplosion p = new ProdImplosion();
					p.setHijo_id(rs.getInt("insumo_id"));
					p.setHijo_nom(rs.getString("insumo_nom"));
					int padreID = rs.getInt("prod_id");
					p.setPadre_id(padreID);
					p.setPadre_value(rs.getString("prod_value"));
					String padreNom = rs.getString("prod_nom").replace("'", " ");
					p.setPadre_nom(padreNom);
					p.setCant(rs.getBigDecimal("cant").setScale(8, RoundingMode.HALF_DOWN));
					p.setUnidad(rs.getString("unidad"));
					p.setCategoria_id(rs.getInt("categoria"));
					p.setLinea_id(rs.getInt("linea"));
					p.setNivel(rs.getInt("nivel"));

					MProduct prodHijo = new MProduct(getCtx(), padreID, null);
					p.setList(prodHijo.getProductosImplosion(mProdCatID,nivel));

					salida.add(p);
				}

			} catch (Exception e) {
				throw new AdempiereException(e.getMessage());
			} finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

		}

		return salida;

	}
	
	/***
	 * Obtiene y retorna lista de proveedores activos de compra de este producto.
	 * OpenUp Ltda. Issue #1034 
	 * @author Gabriel Vila - 18/06/2012
	 * @see
	 * @return
	 */
	public List<MProductPO> getPOVendors() {
	
		String whereClause = " m_product_id =" + this.get_ID();
		List<MProductPO> poVendors = new Query(getCtx(), X_M_Product_PO.Table_Name, whereClause, null)
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.list();

		return poVendors;
	}

	/***
	 * Obtiene modelo de producto segun un determinado value recibido.
	 * OpenUp Ltda. Issue #1036 
	 * @author Nicolas Sarlabos - 26/06/2012
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MProduct forValue(Properties ctx, String value, String trxName)
	{
		if (Util.isEmpty(value, true))
		{
			return null;
		}
		
		MProduct prod = null;
		
		final String whereClause = "trim(lower(value))=? AND AD_Client_ID IN (?,?)"; 
		prod = new Query(ctx, Table_Name, whereClause, trxName)
							.setParameters(new Object[]{value.toLowerCase().trim(), 0, Env.getAD_Client_ID(ctx)})
							.setOnlyActiveRecords(false)
							.first();
		return prod;
	}

	
	/***
	 * Obtiene modelo de producto segun un determinado value recibido sin filtrar empresa.
	 * OpenUp Ltda. Issue #1036 
	 * @author Nicolas Sarlabos - 26/06/2012
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MProduct forValueAllClients(Properties ctx, String value, String trxName)
	{
		if (Util.isEmpty(value, true))
		{
			return null;
		}
		
		MProduct prod = null;
		
		final String whereClause = "trim(lower(value))=? "; 
		prod = new Query(ctx, Table_Name, whereClause, trxName)
							.setParameters(new Object[]{value.toLowerCase().trim()})
							.setOnlyActiveRecords(false)
							.first();
		return prod;
	}
	
	/***
	 * Obtiene modelo de producto segun UPC recibido.
	 * OpenUp Ltda. Issue #4435
	 * @author Nicolas Sarlabos - 18/06/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MProduct forUPC(Properties ctx, String value, String trxName)
	{
		if (Util.isEmpty(value, true))
		{
			return null;
		}

		MProduct prod = null;

		String sql = "select m_product_id" +
				" from uy_productupc" +
				" where upc = '" + value + "'";
		int prodID = DB.getSQLValueEx(trxName, sql);

		if(prodID > 0) prod = new MProduct(ctx,prodID,trxName);	

		return prod;
	}

	
	/***
	 * Obtiene y retorna contabilidad de este producto. Si no tengo contabilidad en el producto
	 * la busco en su categoria.
	 * OpenUp Ltda. Issue #89 
	 * @author Gabriel Vila - 30/10/2012
	 * @see
	 * @return
	 */
	public X_M_Product_Acct getProductAccounting(){
		
		// Obtengo cuentas del producto
		X_M_Product_Acct acct = new Query(getCtx(), I_M_Product_Acct.Table_Name, "M_Product_ID=?", get_TrxName())
        .setParameters(this.get_ID())
        .first();

		// Si no tiene cuentas asociadas, busco cuentas en la categoria
		if (acct == null){
			if (this.getM_Product_Category_ID() > 0){
				MProductCategory cat = new MProductCategory(getCtx(), this.getM_Product_Category_ID(), get_TrxName());
				MProductCategoryAcct catacct = cat.getCategoryAccounting(); 
				if (catacct != null){
					acct = new X_M_Product_Acct(getCtx(), 0, null);
					acct.setP_InventoryClearing_Acct(catacct.getP_InventoryClearing_Acct());
					acct.setP_Revenue_Acct(catacct.getP_Revenue_Acct());
					acct.setP_COGS_Acct(catacct.getP_COGS_Acct());
					acct.setConsume_Acct_ID(catacct.getConsume_Acct_ID());
				}
			}			
		}

		return acct;
	}
	
	/***
	 * Obtiene y retorna contabilidad de la categoria de este producto.
	 * OpenUp Ltda. Issue #5182 
	 * @author Santiago Evans - 18/03/2016
	 * @see
	 * @return
	 */	
	public X_M_Product_Acct getProductCategoryAccounting(){
		X_M_Product_Acct acct = new Query(getCtx(), I_M_Product_Acct.Table_Name, "M_Product_ID=?", get_TrxName())
        .setParameters(this.get_ID())
        .first();
		
		if (this.getM_Product_Category_ID() > 0){
			MProductCategory cat = new MProductCategory(getCtx(), this.getM_Product_Category_ID(), get_TrxName());
			MProductCategoryAcct catacct = cat.getCategoryAccounting(); 
			if (catacct != null){
				acct = new X_M_Product_Acct(getCtx(), 0, null);
				acct.setP_InventoryClearing_Acct(catacct.getP_InventoryClearing_Acct());
				acct.setP_Revenue_Acct(catacct.getP_Revenue_Acct());
				acct.setP_COGS_Acct(catacct.getP_COGS_Acct());
				acct.setConsume_Acct_ID(catacct.getConsume_Acct_ID());
			}
		}	
		return acct;
	}	

	/***
	 * Metodo que obtiene y retorna una determinada cuenta contable de un producto.
	 * OpenUp Ltda. Issue #1633 
	 * @author Gabriel Vila - 20/05/2014
	 * @see
	 * @param string
	 * @param as
	 * @return
	 */
	public MAccount getAccount(String acctType, MAcctSchema as) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		int validCombinationID = 0;
		MAccount acct = null;
		
		try{
				
			sql = " select " + acctType +
				  " from m_product_acct " +
				  " where m_product_id =? AND c_acctschema_id =?";	
			
			pstmt = DB.prepareStatement (sql, null);

			pstmt.setInt(1, this.get_ID());
			pstmt.setInt(2, as.getC_AcctSchema_ID());
			
			rs = pstmt.executeQuery();

			if (rs.next()){
				validCombinationID = rs.getInt(1);				
			}

			if (validCombinationID > 0){
				acct = MAccount.get(as.getCtx(), validCombinationID);
			}
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return acct;

	}
	
	/***
	 * Obtiene y retorna atributos asociados a este producto.
	 * OpenUp Ltda. Issue #4404 
	 * @author Gabriel Vila - 17/06/2015
	 * @see
	 * @return
	 */
	public List<MProductAttribute> getAttributes(){
		
		String whereClause = X_M_ProductAttribute.COLUMNNAME_M_Product_ID + "=" + this.get_ID();
		
		List<MProductAttribute> lines = new Query(getCtx(), I_M_ProductAttribute.Table_Name, whereClause, get_TrxName())
		.setOrderBy(X_M_ProductAttribute.COLUMNNAME_UY_ProdAttribute_ID)
		.list();
		
		return lines;
	}
	
	/***
	 * Obtiene y retorna codigos de barra asociados a este producto.
	 * OpenUp Ltda. Issue #4435
	 * @author Nicolas Sarlabos - 18/06/2015
	 * @see
	 * @return
	 */
	public List<MProductUpc> getUpcLines(){
		
		String whereClause = X_UY_ProductUpc.COLUMNNAME_M_Product_ID + "=" + this.get_ID();
		
		List<MProductUpc> lines = new Query(getCtx(), I_UY_ProductUpc.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}
	
	/***
	 * Obtiene y retorna el precio de venta del producto, desde la lista predeterminada
	 * y ultima version activa segun fecha de vigencia.
	 * OpenUp Ltda. Issue #4458
	 * @author Nicolas Sarlabos - 25/06/2015
	 * @see
	 * @return
	 */
	public BigDecimal getSalePrice(int cCurrencyID){
		
		BigDecimal price = null;
		String sql = "";
		
		MPriceList list = MPriceList.getDefault(getCtx(), true, cCurrencyID);//obtengo lista de precios predeterminada de ventas
		
		if(list!=null){
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			//obtengo ultima version activa segun fecha de vigencia
			sql = "select max(m_pricelist_version_id)" +
			      " from m_pricelist_version" +
				  " where m_pricelist_id = " + list.get_ID() +
				  " and isactive = 'Y'" +
				  " and validfrom <= '" + today + "'";
			
			int versionID = DB.getSQLValueEx(get_TrxName(), sql);
			
			if(versionID > 0){
				
				MProductPrice pprice = MProductPrice.forVersionProduct(getCtx(), versionID, this.get_ID(), get_TrxName());
				
				if(pprice!=null) price = pprice.getPriceList();				
				
			}		
			
		}
		
		return price;
	}
	
	/***
	 * Obtiene y retorna la moneda de venta del producto, desde la lista predeterminada
	 * y ultima version activa segun fecha de vigencia.
	 * OpenUp Ltda. Issue #4458
	 * @author Nicolas Sarlabos - 25/06/2015
	 * @see
	 * @return
	 */
	public int getSaleCurrency(){
		
		int currency = 0;
				
		MPriceList list = MPriceList.getDefault(getCtx(), true,0);//obtengo lista de precios predeterminada de ventas
		
		if(list!=null) currency = list.getC_Currency_ID();
				
		return currency;
	}	
	/***
	 * Retorna true si tiene un producto asociado(tandem, para RT)
	 * OpenUp Ltda. Issue #4404
	 * @author INes Fernandez - 26/06/2015
	 */
	private boolean isTandem(){
		if(this.get_ValueAsInt("M_Product_Tandem_ID")!=0) return true;
		return false;
	}
	
	
	/**Retorna todos los productos activos
     * OpenUp Ltda Issue# --Para generar archivo carga maestro Covadonga
     * @author SBouissa 10/07/2015
     * @return
     */
    public static List<MProduct> forProductsNotR(Properties ctx,String trxName) {
        String whereClause = X_M_Product.COLUMNNAME_IsActive + " = 'Y' "
        		+ " AND " + X_M_Product.COLUMNNAME_IsSold + " = 'Y' "
        		+ " AND " + X_M_Product.COLUMNNAME_Value +"!= 'NO DEFINIDO' ";
       
        List<MProduct> lines = new Query(ctx, I_M_Product.Table_Name, whereClause, trxName).setOrderBy("Value").list();
        if(0<lines.size()){
        	return lines;
        }else{
        	return null;
        }
        
    }

    /**Retorna lista de productos "vendibles" que no tiene asignado atributos 
     * @param ctx
     * @param trxName
     * @return
     */
    public static List<MProduct> forProductsWithoutAttr (Properties ctx,String trxName) {
    	//SELECT Distinct(M_Product_ID FROM M_ProductAttribute)
        String whereClause = X_M_Product.COLUMNNAME_IsActive + " = 'Y' AND "
        		+ X_M_Product.COLUMNNAME_IsSold  + " = 'Y' AND "
        		+ X_M_Product.COLUMNNAME_M_Product_ID +" NOT IN (SELECT Distinct(M_Product_ID) FROM M_ProductAttribute)";
       
        List<MProduct> lines = new Query(ctx, I_M_Product.Table_Name, whereClause, trxName).setOrderBy("Value").list();
        if(0<lines.size()){
        	return lines;
        }else{
        	return null;
        }
        
    }
    /**Set atributos bit a bit (Covadonga)
     * OpenUp Ltda Issue#
     * @author SBouissa 7/9/2015
     * @param listaAtributos
     */
	public void setAtributosBitABit(String listaAtributos) {
		PreparedStatement pstmt = null;
		String val = "";
		try{
			if(listaAtributos.length()==64){
				int pos = 0;
				for(int i = 0;i<48;i++){
					pos = i+1;
					MProdAttribute attr = MProdAttribute.forValue(getCtx(),"attr_"+pos,null);//"attr_1"
					if(null!=attr){
						char atr = listaAtributos.charAt(i);
						if(String.valueOf(atr).equals("0")){
							val = "N";
						}else{
							val = "Y";
						}
						String sql = "UPDATE M_ProductAttribute SET IsSelected = '"+val+"'"
				    			+ " WHERE M_Product_ID = "+this.get_ID() +" AND UY_ProdAttribute_ID = "+attr.get_ID();
				    	pstmt = DB.prepareStatement(sql, get_TrxName());
				    	int resultado = pstmt.executeUpdate();
				    	if(1!=resultado){
				    		throw new AdempiereException("Error al acutalizar atributo");	 
				    	}
				    	pstmt.close();
					}
					
				}
			}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	/**
	 * Se calcula y actualiza el valor hexa del producto que hace referencia a los atributos del mismo
	 * @author OpenUp SBT Issue#  6/10/2015
	 */
	public boolean updateVerssionNo(Boolean saveIt) {
		
		List<MProductAttribute> prodatts = this.getAttributes();
		String[] retorno = new String[16] ;
		if(0>=prodatts.size()){
			System.out.println("NO TIENE ATRIBUTOSSSSS "+this.get_ID());
			return false;
		}

		String acumulo = "";
		for(int i=0;i<prodatts.size();i++){
			//String aux = "";

			MProductAttribute prod = prodatts.get(i);
			MProdAttribute att = (MProdAttribute)prod.getUY_ProdAttribute();
			if(att.getValue().equalsIgnoreCase("attr_1")||
				att.getValue().equalsIgnoreCase("attr_2")||
				att.getValue().equalsIgnoreCase("attr_3")||
				att.getValue().equalsIgnoreCase("attr_4")){
					if(retorno[0]==null){
						retorno[0]="";
					}
					if(prod.isSelected()){
						retorno[0] = retorno[0]+"1";
					}else{
						retorno[0] = retorno[0]+"0";
					}
					if(acumulo.length()==4){
						acumulo="";
					}
				}
			else if(att.getValue().equalsIgnoreCase("attr_5")||
					att.getValue().equalsIgnoreCase("attr_6")||
					att.getValue().equalsIgnoreCase("attr_7")
						){
					if(retorno[1]==null){
						retorno[1]="";
					}
						if(prod.isSelected()){
							retorno[1]= retorno[1]+"1";
						}else{
							retorno[1]= retorno[1]+"0";
						}
						if(att.getValue().equalsIgnoreCase("attr_7")){
							retorno[1]= retorno[1]+"0";
						}
						if(acumulo.length()==4){
							acumulo="";
						}

					}
			else if(att.getValue().equalsIgnoreCase("attr_9")||
					att.getValue().equalsIgnoreCase("attr_11")||
					att.getValue().equalsIgnoreCase("attr_12")
						){
					if(retorno[2]==null){
						retorno[2]="";
					}
						if(prod.isSelected()){
							retorno[2]= retorno[2]+"1";
						}else{
							retorno[2]= retorno[2]+"0";
						}
						if(att.getValue().equalsIgnoreCase("attr_9")){
							retorno[2]= retorno[2]+"0";
						}
						if(acumulo.length()==4){
							acumulo="";
						}
					}

			else if(att.getValue().equalsIgnoreCase("attr_13")||
					att.getValue().equalsIgnoreCase("attr_14")||
					att.getValue().equalsIgnoreCase("attr_15")||
						att.getValue().equalsIgnoreCase("attr_16")
						){
					if(retorno[3]==null){
						retorno[3]="";
					}
						if(prod.isSelected()){
							retorno[3] = retorno[3]+"1";
						}else{
							retorno[3] = retorno[3]+"0";
						}
						if(acumulo.length()==4){
							acumulo="";
						}
					}
			else if(att.getValue().equalsIgnoreCase("attr_17")||
						att.getValue().equalsIgnoreCase("attr_19")||
						att.getValue().equalsIgnoreCase("attr_20")
						){
					if(retorno[4]==null){
						retorno[4]="";
					}
						if(prod.isSelected()){
							retorno[4] = retorno[4]+"1";
						}else{
							retorno[4] = retorno[4]+"0";
						}
						if(att.getValue().equalsIgnoreCase("attr_17")){
							retorno[4] = retorno[4]+"0";
						}
						if(acumulo.length()==4){
							acumulo="";
						}
					}
			else if(att.getValue().equalsIgnoreCase("attr_21")||
						att.getValue().equalsIgnoreCase("attr_22")||
						att.getValue().equalsIgnoreCase("attr_24")
						){
					if(retorno[5]==null){
						retorno[5]="";
					}
						if(prod.isSelected()){
							retorno[5] = retorno[5]+"1";
						}else{
							retorno[5] = retorno[5]+"0";
						}
						if(att.getValue().equalsIgnoreCase("attr_22")){
							retorno[5] = retorno[5]+"0";
						}
						if(acumulo.length()==4){
							acumulo="";
						}
					}
			else if(att.getValue().equalsIgnoreCase("attr_25")||
						att.getValue().equalsIgnoreCase("attr_27")		
						){
					if(retorno[6]==null){
						retorno[6]="";
					}
						if(prod.isSelected()){
							retorno[6] = retorno[6]+"1";
						}else{
							retorno[6] = retorno[6]+"0";
						}
						if(att.getValue().equalsIgnoreCase("attr_25")||att.getValue().equalsIgnoreCase("attr_27")){
							retorno[6] = retorno[6]+"0";
						}
						if(acumulo.length()==4){
							acumulo="";
						}
					}
			else if(att.getValue().equalsIgnoreCase("attr_29")||
						att.getValue().equalsIgnoreCase("attr_30")||
						att.getValue().equalsIgnoreCase("attr_31")||
						att.getValue().equalsIgnoreCase("attr_32")	
						){
					if(retorno[7]==null){
						retorno[7]="";
					}
						if(prod.isSelected()){
							retorno[7] = retorno[7]+"1";
						}else{
							retorno[7] = retorno[7]+"0";
						}
						if(acumulo.length()==4){
							acumulo="";
						}
					}
			else if(att.getValue().equalsIgnoreCase("attr_36")
						){
					if(retorno[8]==null){
						retorno[8]="";
					}
						if(prod.isSelected()){//33-34-35
							retorno[8] = "000"+"1";
						}else{
							retorno[8] = "000"+"0";
						}
						if(acumulo.length()==4){
							acumulo="";
						}
					}
			else if(att.getValue().equalsIgnoreCase("attr_38")||
						att.getValue().equalsIgnoreCase("attr_39")||
						att.getValue().equalsIgnoreCase("attr_40")
						){
					if(retorno[9]==null){
						retorno[9]="";
					}
						if(att.getValue().equalsIgnoreCase("attr_38")){
							retorno[9] = retorno[9]+"0";
						}
						if(prod.isSelected()){
							retorno[9] = retorno[9]+"1";
						}else{
							retorno[9] = retorno[9]+"0";
						}
						
						if(acumulo.length()==4){
							acumulo="";
						}
					}
			else if(att.getValue().equalsIgnoreCase("attr_41")){
					if(retorno[10]==null){
						retorno[10]="";
					}
						if(prod.isSelected()){
							retorno[10] = "1"+"000";
						}else{
							retorno[10] = "0"+"000";
						}
						
						if(acumulo.length()==4){
							acumulo="";
						}
					}
			else if(att.getValue().equalsIgnoreCase("attr_45")||
						att.getValue().equalsIgnoreCase("attr_46")||
						att.getValue().equalsIgnoreCase("attr_47")||
						att.getValue().equalsIgnoreCase("attr_48")
						){
					if(retorno[11]==null){
						retorno[11]="";
					}
						if(prod.isSelected()){
							retorno[11] = retorno[11]+"1";
						}else{
							retorno[11] = retorno[11]+"0";
						}
						
						if(acumulo.length()==4){
							acumulo="";
						}
					}

			}
		retorno[12] = "0000";retorno[13] = "0000";retorno[14] = "0000";retorno[15] = "0000";		

		//Pasar a hexagesimal lista de atributos
		String atributosHexa = OpenUpUtils.bitsToHexa(retorno);
		this.set_Value("VersionNo", atributosHexa.toUpperCase());
		if(saveIt){
			String sql = ("UPDATE M_Product SET VersionNo = '"+atributosHexa+"' WHERE M_Product_ID = "
					+this.get_ID());
			int i = DB.executeUpdateEx(sql, get_TrxName());
			if(0<i) return true;
		}
		return true;
	}

	@Override
	public void setComment(String comment) {
	}

	@Override
	public void setAttachment(MAttachment attachment) {
	}

	@Override
	public void changeMQuery(MQuery query) {

		// OpenUp. Gabriel Vila. 24/11/2015. Issue #5104.
		// Si hay UPC como columna seleccionada y estoy en Retail, aplico el fitro a la tabla de N upcs por producto.
		
		try {
			
			if (query == null) return;
			String whereC = "";
			for (int i = 0; i < query.getRestrictionCount(); i++){
				if (query.getInfoName(i) != null){
					if (query.getInfoName(i).toLowerCase().trim().equalsIgnoreCase("upc")){
						
						String operator ="", code ="";

						if (query.getOperator(i) != null){
							operator = query.getOperator(i).toString();
						}
						if (query.getCode(i) != null){
							code = query.getCode(i).toString();
						}
						
						if (!operator.equalsIgnoreCase("") && !code.equalsIgnoreCase("")){
							//String whereClause = " uy_tt_card_id in (select distinct uy_tt_card_id from uy_tt_cardplastic where cedula " + operator + " '" + code + "')";
							if(whereC.length()>0){
								whereC += " AND m_product_id in (select distinct m_product_id from uy_productupc where upc " + operator + " '" + code + "')";
							}else{
								whereC = " m_product_id in (select distinct m_product_id from uy_productupc where upc " + operator + " '" + code + "')";
							}
							//query.addRestriction(whereClause, false, 0);
						}
					}				
				}
			}
			query.addRestriction(whereC, false, 0);
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	
}	//	MProduct


