package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.logging.Level;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;

public class PCargaLDM extends SvrProcess {

	private final int cargaID = 0;
	private final String issuemethod = "1";
	//private static final String TABLA_PRODUCTO = "pp_product_bom_aux";
	private static final String ComponentType = "CO";
    private final String fecha = "1900-01-01 00:00:00"; 
    //private final int id_vessena = 100005;
    private final int c_uom_id = 100;
    private int documentNo = 1;
	HashSet<String> set = new HashSet<String>();
	BigDecimal ZERO = new BigDecimal(0);

	public PCargaLDM() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub

		// String idPadre = Integer.toString(productbom.get);
		// boolean tieneHijos = this.bomRecursivo(idPadre);

		// llamo al metodo que obtiene a los padres (productos que no tienen
		// hijos)
		this.procesoObteberPadres();

		return "Proceso finalizado.";
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
	}

	private void procesoObteberPadres() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {

			// Selecciono productos padres (que no figuran como hijos de nadie)
			sql = "SELECT l.*, v.num_version from  ldm_products_final l, ldm_version_act v " 
					+ " where l.id_prod NOT IN "
					+ " (SELECT distinct id_prod_derivado from ldm_lista_unicos2) " 
					+ " AND v.id_prod = l.id_prod ";

			//pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			// puse esta variable con alcance a todos los metodos // int documentNo = 1;
			
			while (rs.next()) {

				// Insert padre en PP_Product_BOM
				//MPPProductBOM bom = new MPPProductBOM(getCtx(), this.cargaID, get_TrxName());
				
				MPPProductBOM bom = new MPPProductBOM(getCtx(), this.cargaID, null);
				bom.setValue(rs.getString("pdf_cod_fla"));
				bom.setName(rs.getString("pdf_nombre"));
				bom.setRevision(rs.getString("num_version"));
				bom.setDocumentNo("CM" + String.valueOf(documentNo));
				bom.setDescription(rs.getString("pdf_nombre"));					
				bom.setCopyFrom("N");
				int mProductID = this.getMProductID(rs.getString("pdf_cod_fla").trim());
				if (mProductID<0){
					throw new Exception("****BSCANDO PADRES**** Producto Origen no se encuentra en tabla M_Product : " + rs.getString("pdf_cod_fla").trim());
				}
				bom.setM_Product_ID(mProductID);
				bom.setProcessing(false);
				bom.setValidFrom(Timestamp.valueOf (fecha));
				bom.setC_UOM_ID(this.getProduct_CUOM_ID(mProductID));
				bom.setC_UOM_ID(c_uom_id);
				bom.saveEx();

				set.add((rs.getString("pdf_cod_fla").trim().toUpperCase()));
				
				documentNo++;
				
				bomRecursivo(rs.getInt("id_prod"), bom.getPP_Product_BOM_ID());
				
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			try {
				throw new Exception(e.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	private void bomRecursivo(int idPadre, int padreBOMId){
		
		// traigo el producto
		boolean tieneHijos = false;		
		String sql = "select distinct lista.*, prods.pdf_cod_fla, prods.pdf_nombre " +
					 " from ldm_lista_unicos2 lista inner join ldm_products_final prods ON (lista.id_prod_derivado = prods.id_prod) " +
					 " where lista.id_prod = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, idPadre);
			rs = pstmt.executeQuery();
			
			// Recorro hijos
			while (rs.next()){
				
				
				
				// Inserto este hijo en pp_producto_bomline			
				//MPPProductBOMLine line = new MPPProductBOMLine(getCtx(), this.cargaID, get_TrxName());
				MPPProductBOMLine line = new MPPProductBOMLine(getCtx(), this.cargaID, null);
				line.setFeature("");					
				//line.setAD_Org_ID(id_vessena);
				line.setAssay(ZERO);
				line.setBackflushGroup("");				
				
				line.setComponentType(ComponentType);
				line.setForecast(Env.ZERO);
				line.setIssueMethod(issuemethod);
				
				String description = this.getLDProductDescription(rs.getString("id_prod_derivado").trim());
				if (description == null){
					//throw new Exception("Descripcion Origen no se encuentra en tabla ldm_products_final : " + rs.getString("id_prod_derivado").trim());
					log.warning("*****CARGANDO HIJOS EN BOMLINE***** Descripcion Origen no se encuentra en tabla ldm_products_final : " + rs.getString("id_prod_derivado").trim());
				}
				else{
					line.setDescription(description);		
				}
				line.setIsCritical(false);
				line.setIsQtyPercentage(false);
				line.setIssueMethod(issuemethod);
				line.setLeadTimeOffset(0);
				line.setLine(rs.getInt("secuencia"));
				int mProductID = this.getMProductID(rs.getString("pdf_cod_fla"));
				if (mProductID<0){
					throw new Exception("Producto Origen no se encuentra en tabla M_Product_ID : " + rs.getString("pdf_cod_fla").trim());
				}
				line.setM_Product_ID(mProductID);
				line.setC_UOM_ID(this.getProduct_CUOM_ID(mProductID));
				BigDecimal cant = new BigDecimal(rs.getString(("cantidad"))); 
				line.setQtyBOM(cant);
				line.setQtyBatch(ZERO);
				line.setScrap(ZERO);
				line.setValidFrom(Timestamp.valueOf (fecha));
				line.setPP_Product_BOM_ID(padreBOMId);
				line.saveEx();
				
				tieneHijos = this.tieneHijos(rs.getString("id_prod_derivado"));
				
				if (tieneHijos){
					
					if(!set.contains(rs.getString("pdf_cod_fla").trim().toUpperCase())){
						
					
					// Inserto por ahora este hijo en PP_Product_BOM, si luego detecto que no tiene hijos entonces lo elimino
					//MPPProductBOM header = new MPPProductBOM(getCtx(), this.cargaID, get_TrxName());
					MPPProductBOM header = new MPPProductBOM(getCtx(), this.cargaID, null);
					header.setValue(rs.getString("pdf_cod_fla"));
					header.setRevision(rs.getString("num_version"));
					header.setName(rs.getString("pdf_nombre"));
					header.setDocumentNo("CM" + String.valueOf(documentNo));
					header.setDescription(rs.getString("pdf_nombre"));					
					header.setCopyFrom("N");
					header.setM_Product_ID(mProductID);
					header.setProcessing(false);
					header.setValidFrom(Timestamp.valueOf (fecha));
					//header.setC_UOM_ID(c_uom_id);
					header.setC_UOM_ID(this.getProduct_CUOM_ID(mProductID));
					header.saveEx();
					
					set.add((rs.getString("pdf_cod_fla").trim().toUpperCase()));
					
					// Llamo recursiva para este hijo
					bomRecursivo(rs.getInt("id_prod_derivado"), header.getPP_Product_BOM_ID());
					}
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			try {
				throw new Exception(e.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}
	
	
	private int getMProductID(String pdfCodFla) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		int value = -1;
		
		try{
			sql ="SELECT m_product_id " + 
 		  	" FROM M_Product " +
		  	" WHERE value = ?";  
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString(1, pdfCodFla);			
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value = rs.getInt("m_product_id");
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
		return value;
	}
	
	
private int getProduct_CUOM_ID (int productID) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		int value = -1;
		
		try{
			sql ="SELECT c_uom_id" + 
 		  	" FROM M_Product " +
		  	" WHERE m_product_id  = ?";  
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, productID);			
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value = rs.getInt("c_uom_id");
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
		return value;
	}
	
	private String getLDProductDescription(String idProduct) {
		
		String sql1 = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String value = null;
		
		try{
			sql1 = "SELECT pdf_nombre " +
			      " FROM ldm_products_final " +
			      " WHERE id_prod = ?";  
			
			pstmt = DB.prepareStatement (sql1, null);
			pstmt.setInt(1, Integer.parseInt(idProduct));			
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value = rs.getString("pdf_nombre");
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql1, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}
	
	

	
private boolean tieneHijos(String idProduct) {
		
		String sql1 = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean value = false;
		
		try{
			sql1 = "select distinct lista.*, prods.pdf_cod_fla, pdf_nombre " +
			 " from ldm_lista_unicos2 lista inner join ldm_products_final prods ON (lista.id_prod_derivado = prods.id_prod) " +
			 " where lista.id_prod = ?";
			
			pstmt = DB.prepareStatement (sql1, null);
			pstmt.setInt(1, Integer.parseInt(idProduct));			
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value = true;
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql1, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}
	
}

	

