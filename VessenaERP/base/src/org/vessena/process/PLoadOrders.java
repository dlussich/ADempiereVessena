/**
 * PPatchInventory.java
 * 29/09/2010
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MUOMConversion;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * PLoadOrder
 * Descripcion : Load orders from interface table
 * @author OpenUp FL
 * Fecha : 30/12/2010
 */
public class PLoadOrders extends SvrProcess {
	
	boolean save;
	String label;
	private final static int defaultWarehouseID = 1000013; 

	/**
	 * Constructor
	 */
	public PLoadOrders() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		String resultado = LoadLines(true); 
		commit();
	
		return resultado;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		// Get parrameters
//		ProcessInfoParameter[] para = getParameter();
//		for (int i = 0; i < para.length; i++)
//		{
//			String name = para[i].getParameterName().trim();
//			if (name!= null){
//				if (name.equalsIgnoreCase("C_ORDER_ID")){
//					this.C_Order_Id= (Integer)para[i].getParameter();
//				}
//			}
//		}

	}
	

	private String LoadLines(boolean save) throws Exception {
		
		int count=0;
		
		// Get all records and columns from the interfase
		String sql = "SELECT NroOrden, FechaOrden, FechaEntrega, GlnEntrega, GTIN, Cant, i_uy_ordenes_id FROM i_uy_ordenes WHERE processed='N' ORDER BY GlnEntrega, NroOrden, FechaOrden, FechaEntrega Desc";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Header variables
		MOrder header=null;
		String glnEntrega="";
		Integer nroOrden=new Integer(-1);
		
		try {
			// Prepare and excecute query
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
		
			// For all records
			while (rs.next()){
				
				// Get product, first by UPC, otherwise by Value
				MProduct product = getProductByUPC(rs.getString("GTIN"));
				if (product==null) {
					product = getProductByValue(rs.getString("GTIN"));
				}
	
				// Add new line only if a product was get
				if (product!=null) {
					
					// If the order number is diferent add a new header, but proccess and commit the previous if exist
					if ((!glnEntrega.equalsIgnoreCase(rs.getString("GlnEntrega")) || (nroOrden!=rs.getInt("NroOrden")))) {
						
						// If a previous header and transaction where created, process it and commit
						if (header!=null) {

							// Process the order
							if ((save)&&(header.processIt(MOrder.DOCACTION_Complete))) {
								
								if (save) {
									header.saveEx();
								}
								
								// Incrementa el contador de headers
								count++;
							}
						}
						// Set the new order to make the control
						glnEntrega=rs.getString("GlnEntrega");
						nroOrden=rs.getInt("NroOrden");
						
						// Load a header form lines
						header=LoadHeaderFormLine(rs, save);
					}
					
					// Add a new line only if header and transaction were sucsessfuly created
					if (save) {
						if (header!=null) {
	
							// Create a new line based on the header, set fields and save
							MOrderLine line = new MOrderLine(header);
							
							line.setM_Product_ID(product.getM_Product_ID());
							line.setM_Warehouse_ID(defaultWarehouseID);
							
							// Get the default factor of the producto and get the cant to avoid recordset get 
							//BigDecimal factor=product.getFactorUVDefualt();
							BigDecimal factor = MUOMConversion.getProductRateFrom(Env.getCtx(), product.getM_Product_ID(), product.getC_UOM_To_ID());
							BigDecimal cant=rs.getBigDecimal("Cant");
							
							// Controla que cantidades negativas o 0
							if (cant != null) {
								if (cant.compareTo(BigDecimal.ZERO)<=0) {
									// Cantidad negativa o 0
									setErrorMsg(rs.getInt("i_uy_ordenes_id"),"Cantidad");
								} 
								else {
									
									// Not factor found the units is one
									if (factor==null) {
										line.setC_UOM_ID(product.getC_UOM_ID()); 											
										line.setQtyEntered(cant);
										line.setQtyOrdered(cant);
									}
									else {
										if (cant.remainder(factor)!=BigDecimal.ZERO) {								// If there is no remainder, the division is not integer
											line.setC_UOM_ID(product.getC_UOM_ID()); 											
											line.setQtyEntered(cant);
											line.setQtyOrdered(cant);
										}
										else {																		// If there is remainder, the division is integer
											//String uy_unidadventa=(String) product.get_Value("uy_unidadventa");		// Get unidadventa from product
											//line.setC_UOM_ID(MUOM.getIDFromSymbol(uy_unidadventa));					// Set unit of measure acording to the unidaddventa
											line.setC_UOM_ID(product.getC_UOM_To_ID());
											line.setQtyEntered(cant.divide(factor));								// Set the quantity divided by de factor
											line.setQtyOrdered(cant);
										}
									}
									
									// Set prices
									line.setFormatInfo(null);
									
									// Save line
									if (save) {
										try {
											line.saveEx();
											delete(rs.getInt("i_uy_ordenes_id"));
										}
										catch (Exception e)	{
											setErrorMsg(rs.getInt("i_uy_ordenes_id"),"Error al grabar");
											log.info(e.getMessage());
										}
									}
									else {
										setControled(rs.getInt("i_uy_ordenes_id"));
									}
								}
							}
							else{
								// Cantidad negativa o 0
								setErrorMsg(rs.getInt("i_uy_ordenes_id"),"Cantidad");
							}
						}
					}
					else {
						// Get the default factor of the producto and get the cant to avoid recordset get 
						BigDecimal cant=rs.getBigDecimal("Cant");
						
						// Controla que cantidades negativas o 0
						if (cant.compareTo(BigDecimal.ZERO)<=0) {
							// Cantidad negativa o 0
							setErrorMsg(rs.getInt("i_uy_ordenes_id"),"Cantidad");
						} 
					}
				}
				else {
					// Producto incorrecto
					setErrorMsg(rs.getInt("i_uy_ordenes_id"),"Producto");
				}
			}
			
			
			// Last header and transaction process and commit, or rollback
			if (header!=null) {

				// Process the order
				if ((save)&&(header.processIt(MOrder.DOCACTION_Complete))) {
					if (save) {
						header.saveEx();
					}

					// Incrementa el ultimo contador de headers
					count++;
				}
			}
			
			
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		String message="Ordenes procesadas: "+count;
		return(message);
	}
	
	private MOrder LoadHeaderFormLine(ResultSet rs, boolean save) throws Exception{
		
		// Return object
		MOrder header=null; 
		
		// Default values for partner
		MBPartner partner=null;
		MBPartnerLocation location=null; 
				
		// Get bpartner by location, on sucsess reset default values
		location=getBPartnerLocationByGln(rs.getString("GlnEntrega").trim());
		if (location!=null) {			
			partner=new MBPartner(Env.getCtx(), location.getC_BPartner_ID(), null);
		}
		else {
			// When cannot get the location, try with the partner value 
			partner=getBPartnerByValue(rs.getString("GlnEntrega").trim());
			if (partner!=null) {
				
				// Just get de first location of the partner
				location=getBPartnerFirstLocation(partner.getC_BPartner_ID());
			}
		}
				
		// Save the header and line only when a partner was get
		if ((partner!=null)&&(location!=null)) {
			
			if (rs.getTimestamp("FechaOrden") == null) {
				// Fecha de orden incorrecta
				setErrorMsgToOrder(rs.getInt("NroOrden"),"Fecha orden");
			}
			else {
			
				// Create a new header, ID 0, set fields and save
				header = new MOrder(Env.getCtx(),0,get_TrxName());
								
				// Reference order
				header.setPOReference(rs.getString("NroOrden").trim());	
			
				// Set partner
				header.setC_BPartner_ID(partner.getC_BPartner_ID());					
				header.setC_BPartner_Location_ID(location.getC_BPartner_Location_ID());
	
				// Set document status and action
				header.setDocStatus (MOrder.DOCSTATUS_Drafted);
				header.setDocAction(MOrder.DOCACTION_Complete);
				
				// Set the user as the sales representative only if it was set at the partner
				if (partner.getSalesRep_ID()!=0) {							// OpenUp FL 11/01/2011
					header.setAD_User_ID(partner.getSalesRep_ID()); 							
				}
		
				// Set document type
				header.setC_DocType_ID(0);									// TODO: Review
				header.setC_DocTypeTarget_ID(1000030);						// TODO: Review,  Orden de Venta Standard
		
				// Set dates
				header.setDateOrdered (rs.getTimestamp("FechaOrden"));
				header.setDateAcct (rs.getTimestamp("FechaOrden"));
				
				header.set_ValueOfColumn("UY_ReservaStock", "N");
				
				if (rs.getTimestamp("FechaEntrega") == null) header.setDatePromised(rs.getTimestamp("FechaOrden"));
				else header.setDatePromised(rs.getTimestamp("FechaEntrega"));
				
				// Set the price list of the partner
				if (partner.getM_PriceList_ID() <= 0) header.setM_PriceList_ID(1000002);
				else header.setM_PriceList_ID(partner.getM_PriceList_ID()); 
	
				// Set the sales representative only if it was set at the partner
				if (partner.getSalesRep_ID()!=0) {							// OpenUp FL 11/01/2011
					header.setSalesRep_ID(partner.getSalesRep_ID()); 							
				}
		
				// Warehouse
				header.setM_Warehouse_ID(defaultWarehouseID);
				
				// Set flags
				header.setPosted (false);
				header.setProcessed (false);
						
				// Save
				if (save) {
					header.saveEx();
				}
			}	
		}
		else {
			// Cliente incorrecto
			setErrorMsgToOrder(rs.getInt("NroOrden"),"Cliente");
		}
		
		return(header);
	}
	
	private MProduct getProductByValue(String value) throws Exception  {
		
		MProduct model=null;		// When no product its found, null its returned
		
		if (value==null) return null;
		
		// Get all records from product having a value
		String sql = "SELECT * FROM m_product WHERE trim(value)=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setString(1,value.trim());
			rs = pstmt.executeQuery();
		
			// Just get the first record, else a null product will be returned
			if (rs.next()){				
				model = new MProduct(Env.getCtx(),rs,null);
			}
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(model);
	}
	
	private MProduct getProductByUPC(String upc) throws Exception  {
		
		MProduct model=null;		// When no product its found, null its returned
		
		if (upc==null) return null;
		
		// Get all records from product having a value
		String sql = "SELECT * FROM m_product WHERE trim(upc)=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		
		
		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setString(1,upc);
			rs = pstmt.executeQuery();
		
			// Just get the first record, else a null product will be returned
			if (rs.next()){				
				model = new MProduct(Env.getCtx(),rs,null);
			}
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(model);
	}
	
	private MBPartnerLocation getBPartnerLocationByGln(String gln) throws Exception  {
		
		MBPartnerLocation model=null;		// When no product its found, null its returned
		
		// Get all records from product having a value
		String sql = "SELECT * FROM c_bpartner_location WHERE trim(uy_gln)=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setString(1,gln.trim());
			rs = pstmt.executeQuery();
		
			// Just get the first record, else a null product will be returned
			if (rs.next()){				
				model = new MBPartnerLocation(Env.getCtx(),rs,null);
			}
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(model);
	}	

	private MBPartner getBPartnerByValue(String value) throws Exception  {
		
		MBPartner model=null;		// When no product its found, null its returned
		
		if (value==null) return null;
		
		// Get all records from product having a value
		String sql = "SELECT * FROM c_bpartner WHERE trim(value)=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setString(1,value);
			rs = pstmt.executeQuery();
		
			// Just get the first record, else a null product will be returned
			if (rs.next()){				
				model = new MBPartner(Env.getCtx(),rs,null);
			}
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(model);
	}	

	private MBPartnerLocation getBPartnerFirstLocation(int id) throws Exception  {
		
		MBPartnerLocation model=null;		// When no product its found, null its returned
		
		// Get all records from product having a value
		String sql = "SELECT * FROM c_bpartner_location WHERE c_bpartner_id=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1,id);
			rs = pstmt.executeQuery();
		
			// Just get the first record, else a null product will be returned
			if (rs.next()){				
				model = new MBPartnerLocation(Env.getCtx(),rs,null);
			}
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(model);
	}	

	private int setControled(int id) throws Exception  {
		
		int processed=0;			// By default 0 records are processed
		
		// Get all records from product having a value
		String sql = "UPDATE i_uy_ordenes SET controled= 'Y' WHERE i_uy_ordenes_id=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1,id);
			processed=pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(processed);
	}

	private int delete(int id) throws Exception  {
		
		int processed=0;			// By default 0 records are processed
		
		// Get all records from product having a value
		String sql = "DELETE FROM i_uy_ordenes WHERE i_uy_ordenes_id=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1,id);
			processed=pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(processed);
	}

	private int setErrorMsg(int id, String errorMsg) throws Exception  {
		
		int processed=0;			// By default 0 records are processed
		
		// Get all records from product having a value
		String sql = "UPDATE i_uy_ordenes SET errormsg = ? WHERE i_uy_ordenes_id=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setString(1,errorMsg);
			pstmt.setInt(2,id);
			processed=pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(processed);
	}

	private int setErrorMsgToOrder(Integer nroOrden, String errorMsg) throws Exception  {
		
		int processed=0;			// By default 0 records are processed
		
		// Get all records from product having a value
		String sql = "UPDATE i_uy_ordenes SET errormsgtoorder = ? WHERE nroorden=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setString(1,errorMsg);
			pstmt.setString(2,nroOrden.toString());
			processed=pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(processed);
	}

}
