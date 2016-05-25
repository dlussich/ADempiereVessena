/**
 * MReserveOrders.java
 * 11/01/2011
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class MSimpleForecastLine extends X_UY_SimpleForecastLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6610927060104743635L;
	public MSimpleForecast forecast = null; //OpenUp Nicolas Sarlabos #959 01/02/2012

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InOutType
	 * @param trxName
	 */
	public MSimpleForecastLine(Properties ctx, int UY_InOutType_ID,String trxName) {
		super(ctx, UY_InOutType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MSimpleForecastLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * BOM
	 * @return sucess
	 */
	public boolean BOMIt()  throws Exception {


		// Only active lines
		if (!this.isActive()) {
			return(false);
		}
		
		Integer PP_Product_BOM_ID=this.getPP_Product_BOM_ID();
		
		// The product should have bom 
		if (PP_Product_BOM_ID==null) {
			return(false);
		}
		
		// Get product BOM object 
		MPPProductBOM productBOM = new MPPProductBOM(getCtx(),PP_Product_BOM_ID,null);

		// Get product object
		MProduct product = new MProduct(getCtx(), this.getM_Product_ID(),null);
		
		// The product shuould be verified
		if (!product.isVerified()) {
			return(false);
		}
		
		//OpenUp Nicolas Sarlabos #959 31/01/2012, se chequea que el producto se encuentre activo
		if (!product.isActive()) {
			forecast.isActiveMsg += "El producto " + product.getValue() + " está inactivo y no fué procesado" + "\n";
			return (false);
		}
		//fin OpenUp Nicolas Sarlabos #959 31/01/2012

		// OpenUp. Nicolas Garcia. 12/10/2011. Issue #898.
		
		// Verifico que la lista este activa
		if (!productBOM.isActive()) {
			return (false);
		}
		//Fin Issue #889

		// The product BOM should be valid 
		if (!productBOM.isValidFromTo(this.getCreated())) {						// FIXME: valid date colud not be creation date
			return(false);
		}
		
		// Get BOM lines
		MPPProductBOMLine[] lines =productBOM.getLines(true);
		
		// Do nothing if no lines
		if (lines.length==0) {
			return(false);
		}
		
		// BOM flag
		boolean isBOM=false;
		
		// Product BOM lines
		for (MPPProductBOMLine productBOMLine : lines) {
			
			if (productBOMLine.isValidFromTo(this.getCreated())) {				// FIXME: valid date colud not be creation date
				
				// Create a new foracast BOM
				MSimpleForecastBOM simpleForecastBOM= new MSimpleForecastBOM(getCtx(),0,get_TrxName());

				// The BOM quantity is proportional
				// OpenUp. Gabriel Vila. 24/10/2011. Issue #911. Le agrego un MathContexto con redondeo a 4 digitos en la multiplicacion.
				//BigDecimal BOMQty=productBOMLine.getQty().multiply(this.getQty(), new MathContext(4,RoundingMode.HALF_UP));
				BigDecimal BOMQty=productBOMLine.getQty().multiply(this.getQty());	
				
				// Set forecast BOM properties
				simpleForecastBOM.setUY_SimpleForecastLine_ID(this.getUY_SimpleForecastLine_ID());
				simpleForecastBOM.setM_Product_ID(productBOMLine.getM_Product_ID());
				simpleForecastBOM.setC_UOM_ID(productBOM.getC_UOM_ID());
				simpleForecastBOM.setIsBOM(false);

				// Set forecast BOM quantity and level
				simpleForecastBOM.setQty(BOMQty);
				simpleForecastBOM.setLevelNo(1);
				
				// Save the forecast BOM
				simpleForecastBOM.saveEx();
				
				// Set the BOM flag
				isBOM=true;

				// BOM the BOM, yes BOMBOM
				if (simpleForecastBOM.BOMIt(this.getUY_SimpleForecastLine_ID(),this.getDateTo(),simpleForecastBOM.getUY_SimpleForecastBOM_ID(),BOMQty,2)) {
				
					// Set is BOM
					simpleForecastBOM.setIsBOM(true);
					simpleForecastBOM.saveEx();
				}
			}
		}
		
		if (isBOM) {
			this.setIsBOM(isBOM);
			this.saveEx();
		}

		return(true);
	}
	
	
	public Integer getPP_Product_BOM_ID()  throws Exception {
		
		// Get the last revision of the product BOM, the last y the greatest
		//OpenUp Nicolas Garcia 27/06/2011 Issue #741 Se agrega en Sql "AND isActive='Y'" 
		String SQL="SELECT PP_Product_BOM_ID FROM PP_Product_BOM WHERE M_Product_ID=? AND isActive='Y' ORDER BY revision desc";
		//FIN OpenUp Nicolas Garcia 27/06/2011 Issue #741  
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
