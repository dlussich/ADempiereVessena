/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
//package org.compiere.mfg.model;
package org.eevolution.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_M_Product;
import org.compiere.model.MProduct;
import org.compiere.model.MUOMConversion;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 * BOM Callouts
 *	
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>BF [ 2820743 ] CalloutBOM - apply ABP
 * 				https://sourceforge.net/tracker/?func=detail&aid=2820743&group_id=176962&atid=934929  
 */
public class CalloutBOM extends CalloutEngine
{
	/**
	 *	Parent cycle.
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 */
	public String parent (Properties ctx, int WindowNo, GridTab mTab, GridField  mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";
		final int M_Product_ID = (Integer)value;
		if (M_Product_ID <= 0)
			return "";
		// OpenUp Bernardo Ureta *** incidente 78 *** 10/09/2010
		// Con esto hago que se cargue automatico el valor de la unidad de medida
		int prodId = ((Integer)value).intValue();
		String sql = "SELECT c_uom_id " +
				" from m_product " +
				" where m_product_id = ? ";
			
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, prodId);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				mTab.setValue("C_UOM_ID", rs.getBigDecimal(1).intValueExact());
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "prodId", e);
		}
		// FIN OpenUp Bernardo Ureta *** incidente 78 *** 10/09/2010		
		
		//OpenUp. Nicolas Sarlabos. 25/10/2012.
		//para el caso de los productos HE0000 y HF0000 debo sugerir la cantidad a ingresar mediante un calculo, permitiendo poder cambiarla
		MProduct prod = new MProduct(ctx,prodId,null);
		if(prod!=null){
			if(!prod.isStocked() && prod.getProductType().equalsIgnoreCase("S")){ //solo productos no stockeables y de servicios
												
				int padreID = Env.getContextAsInt (ctx, WindowNo, 0, "M_Product_ID"); //obtengo el ID del producto padre en el cabezal

				//obtengo productividad teorica del producto padre en el estandar de producciones
				sql = "SELECT coalesce(uy_product_teorica,0)" +
				      " FROM uy_estandar_producciones WHERE m_product_id = " + padreID;
				BigDecimal qty = DB.getSQLValueBDEx(null, sql);
				
				qty = Env.ONE.divide(qty,5,RoundingMode.HALF_UP); //obtengo la cantidad final sugerida y luego la seteo
				
				mTab.setValue("qtybom",qty);
				
			}
			
		}
		//Fin OpenUp
		
		// OpenUp. Gabriel Vila. 17/07/2010.
		// Quito esta validacion ya que no la necesito en vessena por tema de graneles
		return "";
		// Fin OpenUp.
		
		/*I_PP_Product_BOMLine bomLine = GridTabWrapper.create(mTab, I_PP_Product_BOMLine.class);
        I_PP_Product_BOM bom = bomLine.getPP_Product_BOM();
        if (bom.getM_Product_ID() ==  bomLine.getM_Product_ID())
        {                                                                               
             throw new AdempiereException("@ValidComponent@ - Error Parent not be Component");				
        }
		return "";*/
	}
        
    public String qtyLine (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";

		final I_PP_Order_BOMLine bomLine = GridTabWrapper.create(mTab, I_PP_Order_BOMLine.class);
		final int M_Product_ID = bomLine.getM_Product_ID();
		final String columnName = mField.getColumnName();
		
		//	No Product
		if (M_Product_ID <= 0)
		{
			BigDecimal QtyEntered = bomLine.getQtyEntered();
			bomLine.setQtyRequiered(QtyEntered);
		}
		//	UOM Changed - convert from Entered -> Product
		//	QtyEntered changed - calculate QtyOrdered
		else if (I_PP_Order_BOMLine.COLUMNNAME_C_UOM_ID.equals(columnName)
			|| I_PP_Order_BOMLine.COLUMNNAME_QtyEntered.equals(columnName) )
		{
			final BigDecimal QtyEntered = bomLine.getQtyEntered();
			BigDecimal QtyRequiered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
					bomLine.getC_UOM_ID(), QtyEntered);
			if (QtyRequiered == null) // NO Conversion Found
				QtyRequiered = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyRequiered) != 0;
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion);
			bomLine.setQtyRequiered(QtyRequiered);
		}
		//	QtyOrdered changed - calculate QtyEntered
		else if (I_PP_Order_BOMLine.COLUMNNAME_QtyRequiered.equals(columnName))
		{
			final BigDecimal QtyRequiered = bomLine.getQtyRequiered();
			BigDecimal QtyEntered = MUOMConversion.convertProductTo (ctx, M_Product_ID, 
					bomLine.getC_UOM_ID(), QtyRequiered);
			if (QtyEntered == null) // No Conversion Found
				QtyEntered = QtyRequiered;
			boolean conversion = QtyRequiered.compareTo(QtyEntered) != 0;
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion);
			bomLine.setQtyEntered(QtyEntered);
		}
		//
		return "";
	}	//	qty
    
	/**
	 *	getdefaults   
	 *  get defaults for Product (search key, name, description, help and UOM)
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 */
	public String getdefaults (Properties ctx, int WindowNo, GridTab mTab, GridField  mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";
		int M_Product_ID = (Integer)value;
		if (M_Product_ID <= 0)
			return "";
		
        I_M_Product product =  MProduct.get(ctx, M_Product_ID);
        I_PP_Product_BOM bom = GridTabWrapper.create(mTab, I_PP_Product_BOM.class);
        bom.setValue(product.getValue());
        bom.setName(product.getName());
        bom.setDescription(product.getDescription());
        bom.setHelp(product.getHelp());
        bom.setC_UOM_ID(product.getC_UOM_ID());
        
		return "";
	}	//	getdefaults
}	//	CalloutOrder

