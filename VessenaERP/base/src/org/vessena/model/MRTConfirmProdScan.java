/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author Nicolas
 *
 */
public class MRTConfirmProdScan extends X_UY_RT_ConfirmProdScan {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4701418658094845751L;

	/**
	 * @param ctx
	 * @param UY_RT_ConfirmProdScan_ID
	 * @param trxName
	 */
	public MRTConfirmProdScan(Properties ctx, int UY_RT_ConfirmProdScan_ID,
			String trxName) {
		super(ctx, UY_RT_ConfirmProdScan_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTConfirmProdScan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if(newRecord){
			if(this.getM_Product_ID()>0){

				MProduct prod = (MProduct)this.getM_Product();
				//MRTConfirmProd hdr = (MRTConfirmProd)this.getUY_RT_ConfirmProd();
				MRTConfirmProdUpc upcLine = null;

				if(prod!=null && prod.get_ID()>0){

					List<MProductUpc> upcLines = prod.getUpcLines();

					for(MProductUpc line : upcLines){

						upcLine = new MRTConfirmProdUpc(getCtx(),0,get_TrxName());
						upcLine.setM_Product_ID(line.getM_Product_ID());
						upcLine.setUPC(line.getUPC());
						upcLine.setUY_RT_ConfirmProd_ID(this.getUY_RT_ConfirmProd_ID());
						upcLine.setUY_RT_ConfirmProdScan_ID(this.get_ID());					
						upcLine.saveEx();				

					}				
				}			
			}		
		} 
		
		return true;
	}

	/***
	 * Metodo que impacta los cambios en el producto al confirmar el mismo.
	 * OpenUp Ltda. Issue #4435
	 * @author Nicolas Sarlabos - 22/06/2015
	 * @see
	 * @return
	 */
	public void confirmProduct() {
		
		MRTConfirmProd hdr = (MRTConfirmProd)this.getUY_RT_ConfirmProd();//instancio cabezal de documento
		MProduct prod = null;
		MProductUpc prodUpc = null;
		
		if(this.getM_Product_ID()>0){ //si tengo producto
			
			prod = (MProduct)this.getM_Product();//instancio el producto
			
			if(prod!=null){
				
				prod.setValue(this.getValue());
				prod.setName(this.getName());
				prod.setDescription(this.getDescription());
				prod.setC_UOM_ID(this.getC_UOM_ID());
				prod.setUY_Linea_Negocio_ID(this.getUY_Linea_Negocio_ID());
				prod.set_ValueOfColumn("UY_ProductGroup_id", this.getUY_ProductGroup_ID());
				prod.setUY_Familia_ID(this.getUY_Familia_ID());
				prod.setUY_SubFamilia_ID(this.getUY_SubFamilia_ID());
				prod.setC_TaxCategory_ID(this.getC_TaxCategory_ID());
				prod.setIsActive(this.isActive2());
				prod.saveEx();
				
				//confirmo los codigos de barras
				
				List<MRTConfirmProdUpc> upcLines = this.getLinesUpc();//obtengo lista de codigos de barra para esta lectura
				
				for(MRTConfirmProdUpc upcLine : upcLines){
					
					//obtengo modelo para producto y UPC
					MProductUpc pu = MProductUpc.forUPCProduct(getCtx(), upcLine.getUPC(), prod.get_ID(), get_TrxName());
					
					if(pu==null){
						
						prodUpc = new MProductUpc(getCtx(), 0, get_TrxName());
						prodUpc.setM_Product_ID(prod.get_ID());
						prodUpc.setUPC(upcLine.getUPC());
						prodUpc.saveEx();						
					}							
					
				}
				
				//si el UPC leido no estaba asociado a ningun producto, debo asociarlo al producto seleccionado
				if(!this.isVerified()){
					
					prodUpc = new MProductUpc(getCtx(), 0, get_TrxName());
					prodUpc.setM_Product_ID(prod.get_ID());
					prodUpc.setUPC(this.getScanText());
					prodUpc.saveEx();	
					
				}				 
				
				//marco como producto confirmado OK
				DB.executeUpdate("update uy_rt_confirmprodscan set isconfirmed = 'Y' where uy_rt_confirmprodscan_id = " + this.get_ID(), get_TrxName());
				
				//impacto tabla de productos confirmados de este documento
				//antes elimino registro para el producto actual
				DB.executeUpdateEx("delete from uy_rt_confirmprodline where uy_rt_confirmprod_id = " + hdr.get_ID() + 
						" and m_product_id = " + prod.get_ID(), get_TrxName());
				
				MRTConfirmProdLine confLine = new MRTConfirmProdLine(getCtx(), 0, get_TrxName());
				confLine.setUY_RT_ConfirmProd_ID(hdr.get_ID());
				confLine.setM_Product_ID(prod.get_ID());
				confLine.saveEx();				
			
			}			
			
		} else { //si NO tengo producto, se debe crear uno nuevo
			
			prod = new MProduct(getCtx(),0,get_TrxName());
			prod.setValue(this.getValue());
			prod.setName(this.getName());
			prod.setDescription(this.getDescription());
			prod.setC_UOM_ID(this.getC_UOM_ID());
			prod.setIsActive(this.isActive2());
			prod.setUY_Linea_Negocio_ID(this.getUY_Linea_Negocio_ID());
			prod.set_ValueOfColumn("UY_ProductGroup_ID", this.getUY_ProductGroup_ID());
			prod.setUY_Familia_ID(this.getUY_Familia_ID());
			prod.setUY_SubFamilia_ID(this.getUY_SubFamilia_ID());
			prod.setC_TaxCategory_ID(this.getC_TaxCategory_ID());
			prod.saveEx();
			
			DB.executeUpdateEx("update uy_rt_confirmprodscan set isnew = 'Y', m_product_id = " + prod.get_ID() + 
					" where uy_rt_confirmprodscan_id = " + this.get_ID(), get_TrxName());
			
			//confirmo los codigos de barras
			
			List<MRTConfirmProdUpc> upcLines = this.getLinesUpc();//obtengo lista de codigos de barra para esta lectura
			
			for(MRTConfirmProdUpc upcLine : upcLines){
				
				prodUpc = new MProductUpc(getCtx(), 0, get_TrxName());
				prodUpc.setM_Product_ID(prod.get_ID());
				prodUpc.setUPC(upcLine.getUPC());
				prodUpc.saveEx();				
				
			}
			
			//si el UPC leido no estaba asociado a ningun producto, debo asociarlo al producto seleccionado
			if(!this.isVerified()){
				
				prodUpc = new MProductUpc(getCtx(), 0, get_TrxName());
				prodUpc.setM_Product_ID(prod.get_ID());
				prodUpc.setUPC(this.getScanText());
				prodUpc.saveEx();	
				
			}				 
			
			//marco como producto confirmado OK
			DB.executeUpdate("update uy_rt_confirmprodscan set isconfirmed = 'Y' where uy_rt_confirmprodscan_id = " + this.get_ID(), get_TrxName());
			
			//impacto tabla de productos confirmados de este documento
			//antes elimino registro para el producto actual
			DB.executeUpdateEx("delete from uy_rt_confirmprodline where uy_rt_confirmprod_id = " + hdr.get_ID() + 
					" and m_product_id = " + prod.get_ID(), get_TrxName());
			
			MRTConfirmProdLine confLine = new MRTConfirmProdLine(getCtx(), 0, get_TrxName());
			confLine.setUY_RT_ConfirmProd_ID(hdr.get_ID());
			confLine.setM_Product_ID(prod.get_ID());
			confLine.saveEx();	
			
		}
		
	}
	
	/***
	 * Obtiene y retorna lineas de UPC de esta lectura.
	 * OpenUp Ltda. Issue #4435
	 * @author Nicolas Sarlabos - 22/06/2015
	 * @see
	 * @return
	 */
	public List<MRTConfirmProdUpc> getLinesUpc(){

		String whereClause = X_UY_RT_ConfirmProdUpc.COLUMNNAME_UY_RT_ConfirmProdScan_ID + "=" + this.get_ID();

		List<MRTConfirmProdUpc> lines = new Query(getCtx(), I_UY_RT_ConfirmProdUpc.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

}
