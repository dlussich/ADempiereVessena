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
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MSysConfig;
import org.compiere.model.MWarehouse;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;


/**
 *  Inventory Valuation
 *  Process to fill UY_T_InventoryBOMValue, copy of InventarioValue wich fill T_InventoryValue, issue #194 
 *
 *  @author     FL
 *  @author 	OpenUP FL 02/03/2011, issue #194
 *  
 */
public class PInventoryBOMValue extends SvrProcess
{
	/** Price List Used         */
	private int         p_M_PriceList_Version_ID;
	/** Valuation Date          */
	private Timestamp   p_DateValue;
	/** Warehouse               */
	private int         p_M_Warehouse_ID;
	/** Currency                */
	private int         p_C_Currency_ID;
	/** Optional Cost Element	*/
	private int			p_M_CostElement_ID;

	/**
	 *  Prepare - get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("M_PriceList_Version_ID"))
				p_M_PriceList_Version_ID = para[i].getParameterAsInt();
			else if (name.equals("DateValue"))
				p_DateValue = (Timestamp)para[i].getParameter();
			else if (name.equals("M_Warehouse_ID"))
				p_M_Warehouse_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Currency_ID"))
				p_C_Currency_ID = para[i].getParameterAsInt();
			else if (name.equals("M_CostElement_ID"))
				p_M_CostElement_ID = para[i].getParameterAsInt();
		}
		if (p_DateValue == null)
			p_DateValue = new Timestamp (System.currentTimeMillis());
	}   //  prepare

	/**
	 *  Perform process.
	 *  <pre>
	 *  - Fill Table with QtyOnHand for Warehouse and Valuation Date
	 *  - Perform Price Calculations
	 *  </pre>
	 * @return Message
	 * @throws Exception
	 */
	protected String doIt() throws Exception
	{
		log.info("M_Warehouse_ID=" + p_M_Warehouse_ID
			+ ",C_Currency_ID=" + p_C_Currency_ID
			+ ",DateValue=" + p_DateValue
			+ ",M_PriceList_Version_ID=" + p_M_PriceList_Version_ID
			+ ",M_CostElement_ID=" + p_M_CostElement_ID);
		
		MWarehouse wh = MWarehouse.get(getCtx(), p_M_Warehouse_ID);
		MClient c = MClient.get(getCtx(), wh.getAD_Client_ID());
		MAcctSchema as = c.getAcctSchema();
		
		//  Delete (just to be sure)
		StringBuffer sql = new StringBuffer ("DELETE UY_T_InventoryBOMValue WHERE AD_PInstance_ID=");
		sql.append(getAD_PInstance_ID());
		int no = DB.executeUpdateEx(sql.toString(), get_TrxName());

		//	Insert Standard Costs
		sql = new StringBuffer ("INSERT INTO UY_T_InventoryBOMValue "
			+ "(AD_PInstance_ID, M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,"
			+ " AD_Client_ID, AD_Org_ID, CostStandard) "
			+ "SELECT ").append(getAD_PInstance_ID())
			.append(", w.M_Warehouse_ID, c.M_Product_ID, c.M_AttributeSetInstance_ID,"
			+ " w.AD_Client_ID, w.AD_Org_ID, c.CurrentCostPrice "
			+ "FROM M_Warehouse w"
			+ " INNER JOIN AD_ClientInfo ci ON (w.AD_Client_ID=ci.AD_Client_ID)"
			+ " INNER JOIN C_AcctSchema acs ON (ci.C_AcctSchema1_ID=acs.C_AcctSchema_ID)"
			+ " INNER JOIN M_Cost c ON (acs.C_AcctSchema_ID=c.C_AcctSchema_ID AND acs.M_CostType_ID=c.M_CostType_ID AND c.AD_Org_ID IN (0, w.AD_Org_ID))"
			+ " INNER JOIN M_CostElement ce ON (c.M_CostElement_ID=ce.M_CostElement_ID AND ce.CostingMethod='S' AND ce.CostElementType='M') "
			+ "WHERE w.M_Warehouse_ID=").append(p_M_Warehouse_ID);
		int noInsertStd = DB.executeUpdateEx(sql.toString(), get_TrxName());
		log.fine("Inserted Std=" + noInsertStd);
		if (noInsertStd == 0)
			return "No Standard Costs found";

		//	Insert addl Costs
		int noInsertCost = 0;
		if (p_M_CostElement_ID != 0)
		{
			sql = new StringBuffer ("INSERT INTO UY_T_InventoryBOMValue "
				+ "(AD_PInstance_ID, M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,"
				+ " AD_Client_ID, AD_Org_ID, CostStandard, Cost, M_CostElement_ID) "
				+ "SELECT ").append(getAD_PInstance_ID())
				.append(", w.M_Warehouse_ID, c.M_Product_ID, c.M_AttributeSetInstance_ID,"
				+ " w.AD_Client_ID, w.AD_Org_ID, 0, c.CurrentCostPrice, c.M_CostElement_ID "
				+ "FROM M_Warehouse w"
				+ " INNER JOIN AD_ClientInfo ci ON (w.AD_Client_ID=ci.AD_Client_ID)"
				+ " INNER JOIN C_AcctSchema acs ON (ci.C_AcctSchema1_ID=acs.C_AcctSchema_ID)"
				+ " INNER JOIN M_Cost c ON (acs.C_AcctSchema_ID=c.C_AcctSchema_ID AND acs.M_CostType_ID=c.M_CostType_ID AND c.AD_Org_ID IN (0, w.AD_Org_ID)) "
				+ "WHERE w.M_Warehouse_ID=").append(p_M_Warehouse_ID)
				.append(" AND c.M_CostElement_ID=").append(p_M_CostElement_ID)
				.append(" AND NOT EXISTS (SELECT * FROM UY_T_InventoryBOMValue iv "
					+ "WHERE iv.AD_PInstance_ID=").append(getAD_PInstance_ID())
					.append(" AND iv.M_Warehouse_ID=w.M_Warehouse_ID"
					+ " AND iv.M_Product_ID=c.M_Product_ID"
					+ " AND iv.M_AttributeSetInstance_ID=c.M_AttributeSetInstance_ID)");
			noInsertCost = DB.executeUpdateEx(sql.toString(), get_TrxName());
			log.fine("Inserted Cost=" + noInsertCost);
			//	Update Std Cost Records
			sql = new StringBuffer ("UPDATE UY_T_InventoryBOMValue iv "
				+ "SET (Cost, M_CostElement_ID)="
					+ "(SELECT c.CurrentCostPrice, c.M_CostElement_ID "
					+ "FROM M_Warehouse w"
					+ " INNER JOIN AD_ClientInfo ci ON (w.AD_Client_ID=ci.AD_Client_ID)"
					+ " INNER JOIN C_AcctSchema acs ON (ci.C_AcctSchema1_ID=acs.C_AcctSchema_ID)"
					+ " INNER JOIN M_Cost c ON (acs.C_AcctSchema_ID=c.C_AcctSchema_ID"
						+ " AND acs.M_CostType_ID=c.M_CostType_ID AND c.AD_Org_ID IN (0, w.AD_Org_ID)) "
					+ "WHERE c.M_CostElement_ID=" + p_M_CostElement_ID
					+ " AND iv.M_Warehouse_ID=w.M_Warehouse_ID"
					+ " AND iv.M_Product_ID=c.M_Product_ID"
					+ " AND iv.M_AttributeSetInstance_ID=c.M_AttributeSetInstance_ID) "
				+ "WHERE EXISTS (SELECT * FROM UY_T_InventoryBOMValue ivv "
					+ "WHERE ivv.AD_PInstance_ID=" + getAD_PInstance_ID()
					+ " AND ivv.M_CostElement_ID IS NULL)");
			int noUpdatedCost = DB.executeUpdateEx(sql.toString(), get_TrxName());
			log.fine("Updated Cost=" + noUpdatedCost);
		}		
		if ((noInsertStd+noInsertCost) == 0)
			return "No Costs found";
		
		//  Update Constants
		//  YYYY-MM-DD HH24:MI:SS.mmmm  JDBC Timestamp format
		String myDate = p_DateValue.toString();
		sql = new StringBuffer ("UPDATE UY_T_InventoryBOMValue SET ")
			.append("DateValue=TO_DATE('").append(myDate.substring(0,10))
			.append(" 23:59:59','YYYY-MM-DD HH24:MI:SS'),")
			.append("M_PriceList_Version_ID=").append(p_M_PriceList_Version_ID).append(",")
			.append("C_Currency_ID=").append(p_C_Currency_ID)
			.append(" WHERE AD_PInstance_ID=" + getAD_PInstance_ID());
		no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		log.fine("Constants=" + no);

		//  Get current QtyOnHand with ASI
		sql = new StringBuffer ("UPDATE UY_T_InventoryBOMValue iv SET QtyOnHand = "
				+ "(SELECT SUM(QtyOnHand) FROM M_Storage s"
				+ " INNER JOIN M_Locator l ON (l.M_Locator_ID=s.M_Locator_ID) "
				+ "WHERE iv.M_Product_ID=s.M_Product_ID"
				+ " AND iv.M_Warehouse_ID=l.M_Warehouse_ID"
				+ " AND iv.M_AttributeSetInstance_ID=s.M_AttributeSetInstance_ID) "
			+ "WHERE AD_PInstance_ID=").append(getAD_PInstance_ID())
			.append(" AND iv.M_AttributeSetInstance_ID<>0");

		// OpenUp. Gabriel Vila. 15/06/2011. Issue #719.
		// Si manejo el stock con el modelo de openup, debo considerar stock fisico segun este modelo
		if (MSysConfig.getBooleanValue("UY_USE_OPENUP_STOCK", true, this.getAD_Client_ID())){

			sql = new StringBuffer ("UPDATE UY_T_InventoryBOMValue iv SET QtyOnHand = "
					+ "(SELECT stk_physical(iv.M_Product_ID, iv.M_Warehouse_ID, null, iv.M_AttributeSetInstance_ID, 0)), "
					+ "WHERE AD_PInstance_ID=").append(getAD_PInstance_ID())
					.append(" AND iv.M_AttributeSetInstance_ID<>0");
		}
		// Fin OpenUp.

		no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		
		log.fine("QtHand with ASI=" + no);
		//  Get current QtyOnHand without ASI
		sql = new StringBuffer ("UPDATE UY_T_InventoryBOMValue iv SET QtyOnHand = "
				+ "(SELECT SUM(QtyOnHand) FROM M_Storage s"
				+ " INNER JOIN M_Locator l ON (l.M_Locator_ID=s.M_Locator_ID) "
				+ "WHERE iv.M_Product_ID=s.M_Product_ID"
				+ " AND iv.M_Warehouse_ID=l.M_Warehouse_ID) "
			+ "WHERE iv.AD_PInstance_ID=").append(getAD_PInstance_ID())
			.append(" AND iv.M_AttributeSetInstance_ID=0");

		// OpenUp. Gabriel Vila. 15/06/2011. Issue #719.
		// Si manejo el stock con el modelo de openup, debo considerar stock fisico segun este modelo
		if (MSysConfig.getBooleanValue("UY_USE_OPENUP_STOCK", true, this.getAD_Client_ID())){
			sql = new StringBuffer ("UPDATE UY_T_InventoryBOMValue iv SET QtyOnHand = "
					+ "(SELECT stk_physical(iv.M_Product_ID, iv.M_Warehouse_ID, null, null, 0)), "
					+ "WHERE AD_PInstance_ID=").append(getAD_PInstance_ID())
					.append(" AND iv.M_AttributeSetInstance_ID=0");
		}
		// Fin OpenUp.
		
		no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		log.fine("QtHand w/o ASI=" + no);
		
		//  Adjust for Valuation Date
		sql = new StringBuffer("UPDATE UY_T_InventoryBOMValue iv "
			+ "SET QtyOnHand="
				+ "(SELECT iv.QtyOnHand - NVL(SUM(t.MovementQty), 0) "
				+ "FROM M_Transaction t"
				+ " INNER JOIN M_Locator l ON (t.M_Locator_ID=l.M_Locator_ID) "
				+ "WHERE t.M_Product_ID=iv.M_Product_ID"
				+ " AND t.M_AttributeSetInstance_ID=iv.M_AttributeSetInstance_ID"
				+ " AND t.MovementDate > iv.DateValue"
				+ " AND l.M_Warehouse_ID=iv.M_Warehouse_ID) "
			+ "WHERE iv.M_AttributeSetInstance_ID<>0" 
			+ " AND iv.AD_PInstance_ID=").append(getAD_PInstance_ID());
		no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		log.fine("Update with ASI=" + no);
		//
		sql = new StringBuffer("UPDATE UY_T_InventoryBOMValue iv "
			+ "SET QtyOnHand="
				+ "(SELECT iv.QtyOnHand - NVL(SUM(t.MovementQty), 0) "
				+ "FROM M_Transaction t"
				+ " INNER JOIN M_Locator l ON (t.M_Locator_ID=l.M_Locator_ID) "
				+ "WHERE t.M_Product_ID=iv.M_Product_ID"
				+ " AND t.MovementDate > iv.DateValue"
				+ " AND l.M_Warehouse_ID=iv.M_Warehouse_ID) "
			+ "WHERE iv.M_AttributeSetInstance_ID=0 "
			+ "AND iv.AD_PInstance_ID=").append(getAD_PInstance_ID());

		no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		log.fine("Update w/o ASI=" + no);
		
		//  Delete Records w/o OnHand Qty
		sql = new StringBuffer("DELETE UY_T_InventoryBOMValue "
			+ "WHERE (QtyOnHand=0 OR QtyOnHand IS NULL) AND AD_PInstance_ID=").append(getAD_PInstance_ID());
		int noQty = DB.executeUpdateEx (sql.toString(), get_TrxName());
		log.fine("NoQty Deleted=" + noQty);
		
		
		// set BOM Cost
		this.setBOMCost();

		//  Update Prices
		sql = new StringBuffer("UPDATE UY_T_InventoryBOMValue iv "
			+ "SET PricePO = "
				+ "(SELECT MAX(currencyConvert (po.PriceList,po.C_Currency_ID,iv.C_Currency_ID,iv.DateValue,null, po.AD_Client_ID,po.AD_Org_ID))"
				+ " FROM M_Product_PO po WHERE po.M_Product_ID=iv.M_Product_ID"
				+ " AND po.IsCurrentVendor='Y'), "
			+ "PriceList = "
				+ "(SELECT currencyConvert(pp.PriceList,pl.C_Currency_ID,iv.C_Currency_ID,iv.DateValue,null, pl.AD_Client_ID,pl.AD_Org_ID)"
				+ " FROM M_PriceList pl, M_PriceList_Version plv, M_ProductPrice pp"
				+ " WHERE pp.M_Product_ID=iv.M_Product_ID AND pp.M_PriceList_Version_ID=iv.M_PriceList_Version_ID"
				+ " AND pp.M_PriceList_Version_ID=plv.M_PriceList_Version_ID"
				+ " AND plv.M_PriceList_ID=pl.M_PriceList_ID), "
			+ "PriceStd = "
				+ "(SELECT currencyConvert(pp.PriceStd,pl.C_Currency_ID,iv.C_Currency_ID,iv.DateValue,null, pl.AD_Client_ID,pl.AD_Org_ID)"
				+ " FROM M_PriceList pl, M_PriceList_Version plv, M_ProductPrice pp"
				+ " WHERE pp.M_Product_ID=iv.M_Product_ID AND pp.M_PriceList_Version_ID=iv.M_PriceList_Version_ID"
				+ " AND pp.M_PriceList_Version_ID=plv.M_PriceList_Version_ID"
				+ " AND plv.M_PriceList_ID=pl.M_PriceList_ID), "
			+ "PriceLimit = "
				+ "(SELECT currencyConvert(pp.PriceLimit,pl.C_Currency_ID,iv.C_Currency_ID,iv.DateValue,null, pl.AD_Client_ID,pl.AD_Org_ID)"
				+ " FROM M_PriceList pl, M_PriceList_Version plv, M_ProductPrice pp"
				+ " WHERE pp.M_Product_ID=iv.M_Product_ID AND pp.M_PriceList_Version_ID=iv.M_PriceList_Version_ID"
				+ " AND pp.M_PriceList_Version_ID=plv.M_PriceList_Version_ID"
				+ " AND plv.M_PriceList_ID=pl.M_PriceList_ID)"
				+ " WHERE iv.AD_PInstance_ID=").append(getAD_PInstance_ID());

		no = DB.executeUpdateEx (sql.toString(), get_TrxName());
		String msg = "";
		if (no == 0)
			msg = "No Prices";

		//	Convert if different Currency
		if (as.getC_Currency_ID() != p_C_Currency_ID)
		{
			sql = new StringBuffer ("UPDATE UY_T_InventoryBOMValue iv "
				+ "SET CostStandard= "
					+ "(SELECT currencyConvert(iv.CostStandard,acs.C_Currency_ID,iv.C_Currency_ID,iv.DateValue,null, iv.AD_Client_ID,iv.AD_Org_ID) "
					+ "FROM C_AcctSchema acs WHERE acs.C_AcctSchema_ID=" + as.getC_AcctSchema_ID() + "),"
				+ "	Cost= "
					+ "(SELECT currencyConvert(iv.Cost,acs.C_Currency_ID,iv.C_Currency_ID,iv.DateValue,null, iv.AD_Client_ID,iv.AD_Org_ID) "
					+ "FROM C_AcctSchema acs WHERE acs.C_AcctSchema_ID=" + as.getC_AcctSchema_ID() + "), "
				+ "	BOMCost= "
					+ "(SELECT currencyConvert(iv.BOMCost,acs.C_Currency_ID,iv.C_Currency_ID,iv.DateValue,null, iv.AD_Client_ID,iv.AD_Org_ID) "
					+ "FROM C_AcctSchema acs WHERE acs.C_AcctSchema_ID=" + as.getC_AcctSchema_ID() + ") "
				+ "WHERE iv.AD_PInstance_ID=" + getAD_PInstance_ID());
			no = DB.executeUpdateEx (sql.toString(), get_TrxName());
			log.fine("Converted=" + no);
		}
		
		//  Update Values
		no = DB.executeUpdateEx("UPDATE UY_T_InventoryBOMValue SET "
			+ "PricePOAmt = QtyOnHand * PricePO, "
			+ "PriceListAmt = QtyOnHand * PriceList, "
			+ "PriceStdAmt = QtyOnHand * PriceStd, "
			+ "PriceLimitAmt = QtyOnHand * PriceLimit, "
			+ "CostStandardAmt = QtyOnHand * CostStandard, "
			+ "CostAmt = QtyOnHand * Cost, "
			+ "BOMCostAmt = QtyOnHand * BOMCost "
			+ "WHERE AD_PInstance_ID=" + getAD_PInstance_ID()
			, get_TrxName());
		
		// Make calculations to get a computed cost and method. OpenUP NG 13/03/2011
		setSimpleCost();
		
		log.fine("Calculation=" + no);
		//
		return msg;
	}   //  doIt

	/**
	 *  OpenUP FL, issue #194, 03/03/2011 
	 *  set BOM Cost for all products in UY_T_InventoryBOMValue
	 *  
	 */
	private void setBOMCost() {		
		
		String SQL=	"SELECT	uy_t_inventorybomvalue.m_product_id " +
					"FROM 	uy_t_inventorybomvalue " +
					"		LEFT JOIN m_product ON m_product.m_product_id=uy_t_inventorybomvalue.m_product_id " +
					"WHERE	uy_t_inventorybomvalue.ad_pinstance_id=? AND (m_product.m_product_category_id=1000010 OR m_product.m_product_category_id=1000011)";
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		int M_Product_ID;

		try {
			pstmt=DB.prepareStatement (SQL, get_TrxName());
			pstmt.setInt(1,this.getAD_PInstance_ID());
			
			rs=pstmt.executeQuery();
			while (rs.next()) {
				
				// get the product id
				M_Product_ID=rs.getInt(1);
				
				// don't set if the product is its null
				if (M_Product_ID!=0) {
					
					// Set the BOM cost for this product
					setBOMCost(rs.getInt(1));
				}
				
			}
		}
		catch (Exception e) {
			log.log(Level.SEVERE, SQL, e);
		}
		finally {
			DB.close(rs,pstmt);
			rs= null; 
			pstmt= null;
		}
	}

	/**
	 *  OpenUP FL, issue #194, 03/03/2011 
	 *  set BOM Cost one products in UY_T_InventoryBOMValue
	 *  
	 */
	private void setBOMCost(int M_Product_ID) {		
		
		String SQL=	"UPDATE	uy_t_inventorybomvalue " +
					"SET	BOMCost=? " +
					"WHERE	ad_pinstance_id=? AND m_product_id=?";
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		BigDecimal BOMCost;
		
		try {
			// get BOM cost
			BOMCost=getBOMCost(M_Product_ID);
			
			SQL="UPDATE uy_t_inventorybomvalue " +
				"SET BOMCost="+BOMCost.toString()+" "+
				"WHERE ad_pinstance_id="+this.getAD_PInstance_ID()+" AND m_product_id="+M_Product_ID+";";

			// Prepare the statement to update
			pstmt=DB.prepareStatement (SQL,get_TrxName());
//			pstmt.setInt(1,this.getAD_PInstance_ID());
//			pstmt.setInt(2,M_Product_ID);
			
			// Update
			DB.executeUpdateEx(SQL,get_TrxName());
		}
		catch (Exception e) {
			log.log(Level.SEVERE, SQL, e);
		}
		finally {
			DB.close(rs,pstmt);
			rs= null; 
			pstmt= null;
		}
		
	}

	/**
	 *  OpenUP FL, issue #194, 03/03/2011 
	 *  get BOM Cost for one products 
	 *  
	 */
	private BigDecimal getBOMCost(int M_Product_ID) {
		
		// The BOM Cost to be calculated
		BigDecimal BOMCost=BigDecimal.ZERO;			
		
		// Get product BOM id, considering the revision
		int PP_Product_BOM_ID=this.getPP_Product_BOM_ID(M_Product_ID);
		
		// If the product don't have BOM, the the BOM Cost is the product cost
		if (PP_Product_BOM_ID==0) {
			BOMCost=this.getCost(M_Product_ID);
		} 
		else {
		
			// Get product BOM object 
			MPPProductBOM productBOM = new MPPProductBOM(getCtx(),PP_Product_BOM_ID,null);
	
			// The product BOM should be valid, if not, the BOM Cost is 0 
			if (!productBOM.isValidFromTo(this.p_DateValue)) {
				BOMCost=BigDecimal.ZERO;
			}
			else {
			
				// Get BOM lines
				MPPProductBOMLine[] lines =productBOM.getLines(true);
				
				// If no lines, its a leaf, then the BOM Cost is the product cost
				if (lines.length==0) {
					BOMCost=this.getCost(M_Product_ID);
				} 
				else {
				
					// Product BOM lines
					for (MPPProductBOMLine productBOMLine : lines) {
						
						if (productBOMLine.isValidFromTo(this.p_DateValue)) {
							
							// The BOM cost is proportional
							BOMCost=BOMCost.add(this.getBOMCost(productBOMLine.getM_Product_ID()).multiply(productBOMLine.getQty()));
						}
					}
				}
			}
		}
		
		return(BOMCost);
	}
	
	private BigDecimal getCost(int mProductID) {
		BigDecimal cost=BigDecimal.ZERO;
		

		String SQL=	"SELECT coalesce(a.costo_mn,0) as costo_mn " +
					"FROM	uy_v_costosunicos a " +
					"WHERE	a.M_Product_ID=? ";
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		try{
			pstmt = DB.prepareStatement(SQL,null);
			pstmt.setInt(1,mProductID);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				cost=rs.getBigDecimal(1);
				
				if (cost==null) {
					cost=BigDecimal.ZERO;
				}
			}

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, SQL, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return cost;
	}

	public int getPP_Product_BOM_ID(int M_Product_ID) {
		
		// Get the last revision of the product BOM, the last y the greatest
		String SQL="SELECT PP_Product_BOM_ID FROM PP_Product_BOM WHERE M_Product_ID=? ORDER BY revision desc";
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		int PP_Product_BOM_ID=0;

		try {
			pstmt=DB.prepareStatement (SQL, null);
			pstmt.setInt(1,M_Product_ID);
			
			rs=pstmt.executeQuery();
			if (rs.next()) {
				PP_Product_BOM_ID=rs.getInt(1);
			}
		}
		catch (Exception e) {
			log.log(Level.SEVERE, SQL, e);
		}
		finally {
			DB.close(rs,pstmt);
			rs= null; 
			pstmt= null;
		}
		
		return(PP_Product_BOM_ID);		
	}
	
	/**
	 * OpenUP NG, 13/04/2001
	 * Make calculations to get a computed cost and method
	 * 
	 */
	private void setSimpleCost() {

		ResultSet rs = null;
		PreparedStatement pstmt = null;

		String SQL = "UPDATE uy_t_inventorybomvalue "
				+ "SET ComputedCost=(CASE WHEN uy_t_inventorybomvalue.bomcost=0 OR uy_t_inventorybomvalue.bomcost IS null THEN (CASE WHEN uy_t_inventorybomvalue.coststandard=0 OR uy_t_inventorybomvalue.coststandard IS null THEN uy_t_inventorybomvalue.cost ELSE uy_t_inventorybomvalue.coststandard END) ELSE uy_t_inventorybomvalue.bomcost END), "
				+ "ComputedMethod=(CASE WHEN uy_t_inventorybomvalue.bomcost=0 OR uy_t_inventorybomvalue.bomcost IS null THEN (CASE WHEN uy_t_inventorybomvalue.coststandard=0 OR uy_t_inventorybomvalue.coststandard IS null THEN 'Cost' ELSE 'coststandard' END) ELSE 'bomcost' END) "
				+ " WHERE ad_pinstance_id="
				+ this.getAD_PInstance_ID();

		try {
			// Prepare the statement to update
			pstmt = DB.prepareStatement(SQL, get_TrxName());

			// Update
			DB.executeUpdateEx(SQL, get_TrxName());
		} catch (Exception e) {
			log.log(Level.SEVERE, SQL, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// Cargo ValuationStocks
		SQL = "UPDATE uy_t_inventorybomvalue "
				+ "SET ValuationStocks=computedcost*qtyonhand"
				+ " WHERE ad_pinstance_id=" + this.getAD_PInstance_ID();

		try {
			// Prepare the statement to update
			pstmt = DB.prepareStatement(SQL, get_TrxName());

			// Update
			DB.executeUpdateEx(SQL, get_TrxName());
		} catch (Exception e) {
			log.log(Level.SEVERE, SQL, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}


}   //  InventoryValue