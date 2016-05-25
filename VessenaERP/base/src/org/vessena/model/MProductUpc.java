/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
//import org.compiere.model.MProduct; TODO: descomentar cuando se creen las tablas para generar archivos de configuracion de balanza
import org.compiere.model.Query;
import org.openup.retail.MRTRetailInterface;

/**
 * @author Nicolas
 *
 */
public class MProductUpc extends X_UY_ProductUpc {

	/**
	 * 
	 */
	private static final long serialVersionUID = -263269055280643616L;

	/**
	 * @param ctx
	 * @param UY_ProductUpc_ID
	 * @param trxName
	 */
	public MProductUpc(Properties ctx, int UY_ProductUpc_ID, String trxName) {
		super(ctx, UY_ProductUpc_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProductUpc(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna linea de modelo segun UPC recibido
	 * OpenUp Ltda. Issue #4435
	 * @author Nicolas Sarlabos - 22/06/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MProductUpc forUPC(Properties ctx, String value, String trxName){
		
		MProductUpc model = null;
		
		String whereClause = X_UY_ProductUpc.COLUMNNAME_UPC + "='" + value + "'"
				+ " AND "+X_UY_ProductUpc.COLUMNNAME_IsActive +" = 'Y' ";
		
		model = new Query(ctx, I_UY_ProductUpc.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return model;
	}
	
	/***
	 * Obtiene y retorna linea de modelo segun UPC y producto recibidos.
	 * OpenUp Ltda. Issue #4435
	 * @author Nicolas Sarlabos - 30/06/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MProductUpc forUPCProduct(Properties ctx, String value, int prodID, String trxName){
		
		MProductUpc model = null;
		
		String whereClause = X_UY_ProductUpc.COLUMNNAME_UPC + "='" + value + "' and " + X_UY_ProductUpc.COLUMNNAME_M_Product_ID + "=" + prodID;
		
		model = new Query(ctx, I_UY_ProductUpc.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return model;
	}
	
	// OpenUp. Ines Fernandez. 17/06/2015. Issue #4404
	// Se agrega una fila en la tabla de interfaz.
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{//public void toInterfaceRow(int prod_ID, String action, String UPC)
		if (!success)
			return success;
		if(newRecord){ //no admite update
//			if(this.isFirtsUPC()){//Issue #5515 Verificar si es necesario cambios scanntech 23-02-2016 SBT
//				MRTRetailInterface.crearArticulo(getCtx(), this.getAD_Client_ID(), this.getM_Product_ID(), get_TrxName());
//			}
			//SBT (26/01/2016-Queda en produccion, se comentan lineas sigueintes luego se eliminaran) Issue #5153 Se modifica la forma de insertar datos
			MRTRetailInterface.crearUPC(getCtx(), this.getAD_Client_ID(),this.getUPC(), this.getM_Product_ID(), get_TrxName());				

//			MRTInterfaceProd it= new MRTInterfaceProd(getCtx(), 0, get_TrxName());26/01/2016
//			it.toInterfaceRow(this.getM_Product_ID(),"insert", this.getUPC());26/01/2016
		
		}
		//OpenUp sevans 19/02/2016 Issue #5319
		//Se controla que el producto sea pesable para actualizarlo en el sistema de balanzas
		
		MProduct prod = new MProduct(getCtx(), this.getM_Product_ID(), get_TrxName());
		if(prod.get_ValueAsBoolean("UsaBalanza")){	
			//OpenUP SBT 04/04/2016 --
			//No se debe interfacear ya que los productos que se envian a balanza solo usan como upc el codigo del prod
			//MRTRetailInterface.insertarProdABalanza(getCtx(), getAD_Client_ID(), this.getUPC(), this.getM_Product_ID(), get_TrxName());
		}		
		return success;
		
	}	//	afterSave
	
	
	/**
	 * Indica si el upc que se acaba de agregar es el primero o no.
	 * @author OpenUp SBT Issue#5515  23/2/2016 10:05:17
	 * @return
	 */
	private boolean isFirtsUPC() {
		List<MProductUpc> model = null;
		
		String whereClause = X_UY_ProductUpc.COLUMNNAME_UPC + "='" + this.getUPC() + "' and " + X_UY_ProductUpc.COLUMNNAME_M_Product_ID + "=" + this.getM_Product_ID();
		
		model = new Query(this.getCtx(), I_UY_ProductUpc.Table_Name, whereClause, this.get_TrxName())
		.list();
		if(model.size()>1){
			return false;
		}else return true;
	}

		// OpenUp. Ines Fernandez. 17/06/2015. Issue #4404
	// Se agrega una fila en la tabla de interfaz.
		@Override
		protected boolean beforeDelete ()
		{
//			//SBT 21/01/2016 (Va a quedar en un futuro sisteco - scanntech) Issue #5153 Se modifica la forma de eliminar los datos
//			return (MRTRetailInterface.eliminarUPC(getCtx(), this.getAD_Client_ID(),this.getUPC(),
//					this.getM_Product_ID(), get_TrxName()));
			
			MRTInterfaceProd it = new MRTInterfaceProd(getCtx(), 0 , get_TrxName());
			try{
				it.toInterfaceRow(this.getM_Product_ID(), "delete", this.getUPC() );
				//OpenUp sevans 19/02/2016 Issue #5319
				//Se controla que el producto sea pesable para actualizarlo en el sistema de balanzas				
				MProduct prod = new MProduct(getCtx(), this.getM_Product_ID(), get_TrxName());
				if(prod.get_ValueAsBoolean("UsaBalanza")){			
					MRTRetailInterface.eliminarProdDeBalanza(getCtx(), getAD_Client_ID(), this.getUPC(), this.getM_Product_ID(), get_TrxName());
				}									
				return true;
			}
			catch (Exception e) {
				throw new AdempiereException(e);
			}
		}	//	beforeDelete

		/**
		 * Rertorna el primer codigo de barra para el producto ingresado
		 * @author OpenUp SBT Issue #5153  20/1/2016 11:06:46
		 * @param ctx
		 * @param prodID
		 * @param trxName
		 * @return
		 */
		public static MProductUpc forProduct(Properties ctx, int prodID,
				String trxName) {
			MProductUpc model = null;
			
			String whereClause = X_UY_ProductUpc.COLUMNNAME_M_Product_ID + "=" + prodID;
			
			model = new Query(ctx, I_UY_ProductUpc.Table_Name, whereClause, trxName)
			.setClient_ID()
			.first();
					
			return model;
		}

		/**
		 * Retorna la lista de upcs para el id de producto recibido
		 * 
		 * @author OpenUp SBT Issue # #5369  25/1/2016 16:14:41
		 * @param ctx
		 * @param prodID
		 * @param trxName
		 * @return
		 */
		public static List<MProductUpc> upcsForProduct(Properties ctx, int prodID,
				String trxName) {
			List<MProductUpc> model = null;
			
			String whereClause = X_UY_ProductUpc.COLUMNNAME_M_Product_ID + "=" + prodID;
			//OpenUp sevans 19/02/2016 #5319
			//Se controla que el producto sea pesable para actualizarlo en el sistema de balanzas			
			model = new Query(ctx, I_UY_ProductUpc.Table_Name, whereClause, trxName)
			.setClient_ID().list();
			return model;
			
		}
		
		@Override
		protected boolean beforeSave(boolean newRecord) {
			if(newRecord){
				try{
					int integer = Integer.valueOf(this.getUPC().trim());
				}catch(Exception e){
					System.out.println(e.toString());
				}
				
			}
			return true;
		}
}
