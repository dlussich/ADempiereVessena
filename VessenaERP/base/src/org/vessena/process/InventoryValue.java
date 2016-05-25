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
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MWarehouse;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;


/**
 *  Inventory Valuation.
 *  Process to fill T_InventoryValue
 *
 *  @author     Jorg Janke
 *  @version    $Id: InventoryValue.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 *  @author 	Michael Judd (mjudd) Akuna Ltd - BF [ 2685127 ]
 *  
 */
public class InventoryValue extends SvrProcess
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
	/** Optional product */
	private int			p_M_Product_ID=0;								// OpenUP FL 17/03/2011, issue #533

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
			else if (name.equals("M_Product_ID"))						// OpenUP FL 17/03/2011, issue #533
				p_M_Product_ID = para[i].getParameterAsInt();
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
		
		// Product filter. OpenUP FL 17/03/2011, issue #533
		String whereProduct="";
		if (p_M_Product_ID!=0) {
			whereProduct=" AND c.m_product_id="+p_M_Product_ID;//OpenUp M.R. Issue#885 agrego entrada de tabla para la seleccion de producto
		}
		
		//  Delete (just to be sure)
		StringBuffer sql = new StringBuffer ("DELETE T_InventoryValue WHERE AD_PInstance_ID=");
		sql.append(getAD_PInstance_ID());
		int no = DB.executeUpdateEx(sql.toString(), get_TrxName());

		//	Insert Standard Costs. OpenUP FL 17/03/2011, issue #533, product filter added
		sql = new StringBuffer ("INSERT INTO T_InventoryValue "
			+ "(AD_PInstance_ID, M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,"
			+ " AD_Client_ID, AD_Org_ID, CostStandard) "
			+ "SELECT ").append(getAD_PInstance_ID())
			.append(", w.M_Warehouse_ID, c.M_Product_ID, c.M_AttributeSetInstance_ID,"
			+ " w.AD_Client_ID, w.AD_Org_ID, c.CurrentCostPrice "
			+ "FROM M_Warehouse w"
			+ " INNER JOIN M_Locator loc on loc.M_Warehouse_ID = w.M_Warehouse_ID" // OpenUp M.R. 11-07-2011 Issue #750 Agrego linea para poder aplicar filtro por almacen
			+ " INNER JOIN AD_ClientInfo ci ON (w.AD_Client_ID=ci.AD_Client_ID)"
			+ " INNER JOIN C_AcctSchema acs ON (ci.C_AcctSchema1_ID=acs.C_AcctSchema_ID)"
			+ " INNER JOIN M_Cost c ON (acs.C_AcctSchema_ID=c.C_AcctSchema_ID AND acs.M_CostType_ID=c.M_CostType_ID AND c.AD_Org_ID IN (0, w.AD_Org_ID))"
			+ " INNER JOIN M_CostElement ce ON (c.M_CostElement_ID=ce.M_CostElement_ID AND ce.CostingMethod='S' AND ce.CostElementType='M') "
			+ " INNER JOIN M_Product p ON c.M_Product_ID = p.M_Product_ID  and loc.M_Locator_ID = p.M_Locator_ID " // OpenUp M.R. 11-07-2011 Issue #750 Agrego linea para poder aplicar filtro por almacen
			+ " WHERE w.M_Warehouse_ID=").append(p_M_Warehouse_ID).append(whereProduct);
		int noInsertStd = DB.executeUpdateEx(sql.toString(), get_TrxName());
		log.fine("Inserted Std=" + noInsertStd);
		if (noInsertStd == 0)
			return "No Standard Costs found";

		//	Insert addl Costs
		int noInsertCost = 0;
		if (p_M_CostElement_ID != 0)
		{
			// OpenUP FL 17/03/2011, issue #533, product filter added
			sql = new StringBuffer ("INSERT INTO T_InventoryValue "
				+ "(AD_PInstance_ID, M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,"
				+ " AD_Client_ID, AD_Org_ID, CostStandard, Cost, M_CostElement_ID) "
				+ "SELECT ").append(getAD_PInstance_ID())
				.append(", w.M_Warehouse_ID, c.M_Product_ID, c.M_AttributeSetInstance_ID,"
				+ " w.AD_Client_ID, w.AD_Org_ID, 0, c.CurrentCostPrice, c.M_CostElement_ID "
				+ "FROM M_Warehouse w"
				+ " INNER JOIN M_Locator loc on loc.M_Warehouse_ID = w.M_Warehouse_ID" // OpenUp M.R. 11-07-2011 Issue #750 Agrego linea para poder aplicar filtro por almacen
				+ " INNER JOIN AD_ClientInfo ci ON (w.AD_Client_ID=ci.AD_Client_ID)"
				+ " INNER JOIN C_AcctSchema acs ON (ci.C_AcctSchema1_ID=acs.C_AcctSchema_ID)"
				+ " INNER JOIN M_Cost c ON (acs.C_AcctSchema_ID=c.C_AcctSchema_ID AND acs.M_CostType_ID=c.M_CostType_ID AND c.AD_Org_ID IN (0, w.AD_Org_ID)) "
				+ " INNER JOIN M_Product p on c.M_Product_ID = p.M_Product_ID AND loc.M_Locator_ID = p.M_Locator_ID" // OpenUp M.R. 11-07-2011 Issue #750 Agrego linea para poder aplicar filtro por almacen
				+ " WHERE w.M_Warehouse_ID=").append(p_M_Warehouse_ID).append(whereProduct)
				.append(" AND c.M_CostElement_ID=").append(p_M_CostElement_ID)
				.append(" AND NOT EXISTS (SELECT * FROM T_InventoryValue iv "
					+ "WHERE iv.AD_PInstance_ID=").append(getAD_PInstance_ID())
					.append(" AND iv.M_Warehouse_ID=w.M_Warehouse_ID"
					+ " AND iv.M_Product_ID=c.M_Product_ID"
					+ " AND iv.M_AttributeSetInstance_ID=c.M_AttributeSetInstance_ID)");
			noInsertCost = DB.executeUpdateEx(sql.toString(), get_TrxName());
			log.fine("Inserted Cost=" + noInsertCost);
			//	Update Std Cost Records
			sql = new StringBuffer ("UPDATE T_InventoryValue iv "
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
				+ "WHERE EXISTS (SELECT * FROM T_InventoryValue ivv "
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
		sql = new StringBuffer ("UPDATE T_InventoryValue SET ")
			.append("DateValue=TO_DATE('").append(myDate.substring(0,10))
			.append(" 23:59:59','YYYY-MM-DD HH24:MI:SS'),")
			.append("M_PriceList_Version_ID=").append(p_M_PriceList_Version_ID).append(",")
			.append("C_Currency_ID=").append(p_C_Currency_ID)
			.append(" WHERE AD_PInstance_ID=" + getAD_PInstance_ID());
		no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		log.fine("Constants=" + no);

		//  Get current QtyOnHand with ASI
		sql = new StringBuffer ("UPDATE T_InventoryValue iv SET QtyOnHand = "
				+ "(SELECT SUM(QtyOnHand) FROM M_Storage s"
				+ " INNER JOIN M_Locator l ON (l.M_Locator_ID=s.M_Locator_ID) "
				+ "WHERE iv.M_Product_ID=s.M_Product_ID"
				+ " AND iv.M_Warehouse_ID=l.M_Warehouse_ID"
				+ " AND iv.M_AttributeSetInstance_ID=s.M_AttributeSetInstance_ID) "
			+ "WHERE AD_PInstance_ID=").append(getAD_PInstance_ID())
			.append(" AND iv.M_AttributeSetInstance_ID<>0");
		no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		log.fine("QtHand with ASI=" + no);
		//  Get current QtyOnHand without ASI
		sql = new StringBuffer ("UPDATE T_InventoryValue iv SET QtyOnHand = "
				+ "(SELECT SUM(QtyOnHand) FROM M_Storage s"
				+ " INNER JOIN M_Locator l ON (l.M_Locator_ID=s.M_Locator_ID) "
				+ "WHERE iv.M_Product_ID=s.M_Product_ID"
				+ " AND iv.M_Warehouse_ID=l.M_Warehouse_ID) "
			+ "WHERE iv.AD_PInstance_ID=").append(getAD_PInstance_ID())
			.append(" AND iv.M_AttributeSetInstance_ID=0");
		no = DB.executeUpdateEx(sql.toString(), get_TrxName());
		log.fine("QtHand w/o ASI=" + no);
		
		//  Adjust for Valuation Date
		sql = new StringBuffer("UPDATE T_InventoryValue iv "
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
		sql = new StringBuffer("UPDATE T_InventoryValue iv "
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
		// OpenUp M.R. issue# 750 agrego linea para actualizar por el fisico desde las tablas de Stock nuevas
		//stk_physical(product_id numeric, warehouse_id numeric, locator_id numeric, asi_id numeric, method_id numeric, dateto timestamp without time zone)
		if (MSysConfig.getBooleanValue("UY_USE_OPENUP_STOCK", true, getAD_Client_ID(), Env.getAD_Org_ID(getCtx()))) {
			sql = new StringBuffer("UPDATE T_InventoryValue iv SET QtyOnHand = " + "(Select stk_physical(iv.M_Product_ID, iv.M_Warehouse_ID, null, null, null, '" + p_DateValue + "'))");
			DB.executeUpdateEx (sql.toString(), get_TrxName());
		}
		// Fin OpenUp

		  //  Delete Records w/o OnHand Qty
		  sql = new StringBuffer("DELETE T_InventoryValue "
			  +	"WHERE (QtyOnHand=0 OR QtyOnHand IS NULL) AND AD_PInstance_ID=").append(getAD_PInstance_ID());
		  int noQty = DB.executeUpdateEx (sql.toString(), get_TrxName());
		  log.fine("NoQty Deleted=" + noQty);

		// set BOM Cost. OpenUP FL 02/03/2011, issue #194
		this.setBOMCost();

		//  Update Prices
		sql = new StringBuffer("UPDATE T_InventoryValue iv "
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
			sql = new StringBuffer ("UPDATE T_InventoryValue iv "
				+ "SET CostStandard= "
					+ "(SELECT currencyConvert(iv.CostStandard,acs.C_Currency_ID,iv.C_Currency_ID,iv.DateValue,null, iv.AD_Client_ID,iv.AD_Org_ID) "
					+ "FROM C_AcctSchema acs WHERE acs.C_AcctSchema_ID=" + as.getC_AcctSchema_ID() + "),"
				+ "	Cost= "
					+ "(SELECT currencyConvert(iv.Cost,acs.C_Currency_ID,iv.C_Currency_ID,iv.DateValue,null, iv.AD_Client_ID,iv.AD_Org_ID) "
					+ "FROM C_AcctSchema acs WHERE acs.C_AcctSchema_ID=" + as.getC_AcctSchema_ID() + ") "
				+ "	BOMCost= "																																// OpenUP FL 02/03/2011, issue #194
					+ "(SELECT currencyConvert(iv.BOMCost,acs.C_Currency_ID,iv.C_Currency_ID,iv.DateValue,null, iv.AD_Client_ID,iv.AD_Org_ID) "
					+ "FROM C_AcctSchema acs WHERE acs.C_AcctSchema_ID=" + as.getC_AcctSchema_ID() + ") "
				+ "WHERE iv.AD_PInstance_ID=" + getAD_PInstance_ID());
			no = DB.executeUpdateEx (sql.toString(), get_TrxName());
			log.fine("Converted=" + no);
		}
		UpdateCosto();// OpenUp M.R. 21-06-2011 aca actualizo el costo del producto
		// OpenUp FL, 13/04/2001, issue #471
		sql=new StringBuffer("UPDATE T_InventoryValue SET  ComputedCost=(CASE WHEN T_InventoryValue.BOMCost=0 OR T_InventoryValue.BOMCost IS NULL THEN (CASE WHEN T_InventoryValue.Cost=0 OR T_InventoryValue.Cost IS NULL THEN T_InventoryValue.CostStandard ELSE T_InventoryValue.Cost END) ELSE T_InventoryValue.BOMCost END), ComputedMethod=(CASE WHEN T_InventoryValue.BOMCost=0 OR T_InventoryValue.BOMCost IS NULL THEN (CASE WHEN T_InventoryValue.Cost=0 OR T_InventoryValue.Cost IS NULL THEN 'Estandar' ELSE 'Ultima Factura' END) ELSE 'BOM' END) WHERE ad_pinstance_id="+ this.getAD_PInstance_ID()); // OpenUp M.R. Issue #750 cambio leyenda en metodo de calculo de stock
		no = DB.executeUpdateEx (sql.toString(), get_TrxName());                                                                                                                                                                                                                                                                                                                                                                                                                                        //OpenUp Nicolas Sarlabos issue #890 18/10/2011, cambio orden 'Ultima Factura' - 'Estandar'                                   
		log.fine("Cost calculation=" + no);
		
		//  Update Values
		no = DB.executeUpdateEx("UPDATE T_InventoryValue SET "
			+ "PricePOAmt = QtyOnHand * PricePO, "
			+ "PriceListAmt = QtyOnHand * PriceList, "
			+ "PriceStdAmt = QtyOnHand * PriceStd, "
			+ "PriceLimitAmt = QtyOnHand * PriceLimit, "
			+ "CostStandardAmt = QtyOnHand * CostStandard, "
			+ "BOMCostAmt = QtyOnHand * BOMCost, "
			+ "computedcostamt = QtyOnHand * ComputedCost, "
			+ "CostAmt = QtyOnHand * Cost "
			+ "WHERE AD_PInstance_ID=" + getAD_PInstance_ID()
			, get_TrxName());
		log.fine("Calculation=" + no);
		
		return msg;
	}   //  doIt
	
	
	/**
	 *  OpenUP FL, issue #194, 03/03/2011 
	 *  set BOM Cost for all products in T_InventoryValue
	 *  
	 */
	private void setBOMCost() {		
		
		String SQL=	"SELECT	T_InventoryValue.m_product_id " +
					"FROM 	T_InventoryValue " +
					"		LEFT JOIN m_product ON m_product.m_product_id=T_InventoryValue.m_product_id " +
					"WHERE	T_InventoryValue.ad_pinstance_id="+getAD_PInstance_ID()+" AND (m_product.m_product_category_id=1000010 OR m_product.m_product_category_id=1000011)";
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		int M_Product_ID;

		try {
			pstmt=DB.prepareStatement (SQL, get_TrxName());
			
			rs=pstmt.executeQuery();
			while (rs.next()) {
				
				// get the product id
				M_Product_ID=rs.getInt(1);
				
				// don't set if the product is its null
				if (M_Product_ID!=0) {
					
					// Set the BOM cost for this product
					setBOMCost(M_Product_ID);
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
	 *  set BOM Cost one products in T_InventoryValue
	 *  
	 */
	private void setBOMCost(int M_Product_ID) {		

		// TODO: review why this don't work with parameters
		String SQL=	"UPDATE	T_InventoryValue " +
					"SET	BOMCost=? " +
					"WHERE	ad_pinstance_id=? AND m_product_id=?";
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		BigDecimal BOMCost;
		
		try {
			// get BOM cost
			BOMCost=getBOMCost(M_Product_ID);
			
			// TODO: replace this with parameters
			SQL="UPDATE T_InventoryValue " +
				"SET BOMCost="+BOMCost.toString()+" "+
				"WHERE ad_pinstance_id="+this.getAD_PInstance_ID()+" AND m_product_id="+M_Product_ID+";";

			// Prepare the statement to update
			pstmt=DB.prepareStatement (SQL,get_TrxName());
			
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
	
	/**
	 *  OpenUP FL, issue #194, 03/03/2011 
	 *  get BOM Cost for one products 
	 *  
	 */
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

	/**
	 *  OpenUP FL, issue #194, 03/03/2011 
	 *  get BOM Cost for one products 
	 *  
	 */
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
	//OpenUp M.R. 21-06-2011 Issue #750 Agrego metodo para actualizar el costo del o los productos
private void UpdateCosto(){


	String SQL=	"SELECT m_product_id, qtyonhand " +
				"FROM	t_inventoryvalue  " +
				"where AD_Pinstance_ID= " + getAD_PInstance_ID();
	ResultSet rs=null;
	PreparedStatement pstmt=null;
	
	try{
		pstmt = DB.prepareStatement(SQL,get_TrxName());
		rs = pstmt.executeQuery();
		while (rs.next()) {
			int productID = rs.getInt("M_Product_ID");
			//RCostCostoDate costo = new RCostCostoDate();
			//BigDecimal Costos = costo.recursivoCostos(productID, Env.ONE);
			MProduct producto = new MProduct(getCtx(), productID, null);
			BigDecimal Costo = Env.ZERO;
		//	int metodo = rs.getInt("m_costelement_id");
		// OpenUp M.R. Issue#750 Aca pregunto si el producto tiene hijos osea lista de materiales si tiene trae el costo bom de la lista, de lo contrario deja el costo bom en cero
			BigDecimal BomCostAmt = Env.ZERO;
			if(producto.isBOM()){
				Costo = producto.getCostofechaHasta(p_DateValue);
				BomCostAmt = Costo.multiply(rs.getBigDecimal("qtyonhand")); 
				
			}

			 String SQL2 = "UPDATE t_inventoryvalue set bomcost = "+ Costo+ ", bomcostamt = "+BomCostAmt+ " where AD_PInstance_ID = "+getAD_PInstance_ID() + " AND m_product_id = " + productID;
			
			DB.executeUpdate(SQL2,get_TrxName());
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
	
//Fin OpenUp
}   //  InventoryValue
}
