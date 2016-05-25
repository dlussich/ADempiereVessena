/**
 * MReserveOrders.java
 * 11/01/2011
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;

/**
 * OpenUp.
 * MReserveOrders
 * Descripcion :
 * @author FL
 * Fecha : 11/01/2011
 */
// org.openup.model.MSimpleForecastBOM
public class MSimpleForecastBOM extends X_UY_SimpleForecastBOM {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7491956696508374757L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InOutType
	 * @param trxName
	 */
	public MSimpleForecastBOM(Properties ctx, int UY_InOutType_ID,String trxName) {
		super(ctx, UY_InOutType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MSimpleForecastBOM(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * BOM
	 * @return sucess
	 */
	public boolean BOMIt(int UY_SimpleForecastLine_ID,Timestamp DateTo, int UY_SimpleForecastBOM_ID,BigDecimal BOMQty, int levelNo)  throws Exception {
		
		Integer PP_Product_BOM_ID=this.getPP_Product_BOM_ID();
		
		// The product shoul have bom 
		if (PP_Product_BOM_ID==null) {
			return(false);
		}
		
		// OpenUp. Nicolas Garcia. 12/10/2011. Issue ##898.

		int idProductBOM = DB.getSQLValue(null, "SELECT pp_product_bom_id FROM pp_product_bom WHERE isactive='Y' AND pp_product_bom_id=" + PP_Product_BOM_ID);

		if (idProductBOM <= 0) {
			return (false);
		}

		// Get product BOM object
		MPPProductBOM productBOM = new MPPProductBOM(getCtx(), idProductBOM, null);

		// Get product object
		MProduct product = new MProduct(getCtx(), this.getM_Product_ID(),null);
		
		// Verifico que la lista este activa
		if (!productBOM.isActive()) {
			return (false);
		}
		// Fin Issue ##898

		// The product shuould be verified
		if (!product.isVerified()) {
			return(false);
		}
		
		// The product BOM should be valid 
		if (!productBOM.isValidFromTo(DateTo)) {						
			return(false);
		}
	
		// Product BOM lines
		for (MPPProductBOMLine productBOMLine : productBOM.getLines(true)) {
			
			if (productBOMLine.isValidFromTo(DateTo)) {
					
				// Create a new foracast BOM
				MSimpleForecastBOM simpleForecastBOM= new MSimpleForecastBOM(getCtx(),0,get_TrxName());
					
				// The next BOM quantity is proportional
				BigDecimal nextBOMQty=productBOMLine.getQty().multiply(BOMQty);
					
				// Set forecast BOM properties
				simpleForecastBOM.setUY_SimpleForecastLine_ID(UY_SimpleForecastLine_ID);
				simpleForecastBOM.setUY_SimpleForecastBOMBOM_ID(UY_SimpleForecastBOM_ID);
				simpleForecastBOM.setM_Product_ID(productBOMLine.getM_Product_ID());
				simpleForecastBOM.setC_UOM_ID(productBOM.getC_UOM_ID());
				simpleForecastBOM.setIsBOM(false);
				
				// Set forecast BOM quantity and level
				simpleForecastBOM.setQty(nextBOMQty);
				simpleForecastBOM.setLevelNo(levelNo);
				
				// Save the forecast BOM
				simpleForecastBOM.saveEx();
				
				// Recursive call to BOMIt
				if (simpleForecastBOM.BOMIt(UY_SimpleForecastLine_ID,DateTo,this.getUY_SimpleForecastBOM_ID(),nextBOMQty,levelNo+1)) {
					
					// Set BOM
					simpleForecastBOM.setIsBOM(true);
					simpleForecastBOM.saveEx();
				}
			}
		}
		
		return(true);
	}
	
	
	public Integer getPP_Product_BOM_ID()  throws Exception {
		
		// Get the last revision of the product BOM, the last y the greatest
		String SQL="SELECT PP_Product_BOM_ID FROM PP_Product_BOM WHERE M_Product_ID=? ORDER BY revision desc";
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		Integer PP_Product_BOM_ID=null;

		try {
			pstmt=DB.prepareStatement (SQL, null);
			pstmt.setInt(1,this.getM_Product_ID());
			
			rs=pstmt.executeQuery();
			if (rs.next()) {
				PP_Product_BOM_ID=new Integer(rs.getInt(1));
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			DB.close(rs,pstmt);
			rs= null; 
			pstmt= null;
		}
		
		return(PP_Product_BOM_ID);		
	}	
}
