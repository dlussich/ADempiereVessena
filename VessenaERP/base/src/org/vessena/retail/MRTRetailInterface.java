/**
 * 
 */
package org.openup.retail;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.compiere.model.MClient;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.PO;
import org.compiere.util.TimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openup.model.MRTInterfaceProd;

/**OpenUp Ltda Issue#
 * @author SBT 9/12/2015
 *
 */
public abstract class MRTRetailInterface {

	/**
	 * 
	 */
	public MRTRetailInterface() {
	}
		
	public static void getVersion(Properties ctx,int mClientID, int mCBPartnerID, String trxName){
		MRTScanntech sis = new MRTScanntech(ctx,trxName);
		sis.getVersionAux();
	}
	public abstract void getVersionAux();
	
	
	//METODOS INTERFACEAR CLIENTES
	public void crearCliente(Properties ctx,int mClientID, int mCBPartnerID, String trxName) {
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
//			MRTSisteco sis = new MRTSisteco();
//			sis.insertCliente(ctx, mClientID, mCBPartnerID, trxName);
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
			MRTScanntech sis = new MRTScanntech(ctx,trxName);
			sis.insertCliente(ctx, mClientID, mCBPartnerID, trxName);
		}
	}
	public abstract void insertCliente(Properties ctx,int mClientID, int mCBPartnerID, String trxName);
	
	public void actualizarCliente(Properties ctx,int mClientID, int mCBPartnerID, String trxName) {
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
//			MRTSisteco sis = new MRTSisteco();
//			sis.updateCliente(ctx, mClientID, mCBPartnerID, trxName);
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
			MRTScanntech sis = new MRTScanntech(ctx,trxName);
			sis.updateCliente(ctx, mClientID, mCBPartnerID, trxName);
		}
	}
	public abstract void updateCliente(Properties ctx,int mClientID, int mCBPartnerID, String trxName);
	
	public static void eliminarCliente(Properties ctx,int mClientID, int mCBPartnerID, String trxName){
		String provider = MSysConfig.getValue("UY_RT_Provider", mClientID);
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
//			MRTSisteco sis = new MRTSisteco();
//			sis.deleteCliente(ctx, mClientID, mCBPartnerID, trxName);
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
			MRTScanntech sis = new MRTScanntech(ctx,trxName);
			sis.deleteCliente(ctx, mClientID, mCBPartnerID, trxName);
		}
	}
	public abstract void deleteCliente(Properties ctx,int mClientID, int mCBPartnerID, String trxName);
	
	
	//---------------------METODOS PARA INTERFACEAR ARTÍCULOS
	
	/**
	 * Interfacear según cliente la creación de un producto
	 * @author OpenUp SBT Issue#  16/12/2015 16:26:20
	 * @param ctx
	 * @param mClientID
	 * @param mProductID
	 * @param trxName
	 */
	public static void crearArticulo(Properties ctx,int mClientID, int mProductID, String trxName){
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
		
			MRTSisteco sis = new MRTSisteco();
			sis.insertArticulo(ctx, mClientID, mProductID, trxName);
		
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->se envia por ws a scanntech
			//MProduct prod = new MProduct(ctx,mProductID,trxName);
//			MRTScanntech sis = new MRTScanntech(ctx,trxName);
//			String retorno = sis.insertArticulo(ctx, mClientID, mProductID, trxName);
//			
//			MRTInterfaceProd it = new MRTInterfaceProd(ctx, 0, trxName);
//			it.setM_Product_ID(mProductID);
//			it.insertInterface(true);	
//			it.setTablaOrigen("MProduct-Articulos");	
//			String desc = "INSERT producto - "+it.getDescription();
//			it.setDescription(desc);		
//			it.set_ValueOfColumn("ResultadoEnvioWS", retorno);
//			if("OK".equalsIgnoreCase(retorno)){
//				it.setReadingDate(new Timestamp (System.currentTimeMillis()));
//			}
//			it.saveEx();
		}
	}
	public abstract String insertArticulo(Properties ctx,int mClientID, int mProductID, String trxName);

	/**
	 * Interfacear según cliente la actualización de un producto
	 * @author OpenUp SBT Issue#  22/1/2016 12:38:28
	 * @param ctx
	 * @param mClientID
	 * @param mProductID
	 * @param trxName
	 */
	public static void actualizarProducto(Properties ctx,int mClientID, int mProductID, String trxName){
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco			
			
			MRTSisteco sis = new MRTSisteco();
			sis.updateArticulo(ctx, mClientID, mProductID, trxName);
			
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
			MRTScanntech sis = new MRTScanntech(ctx,trxName);
			String retorno = sis.updateArticulo(ctx, mClientID, mProductID, trxName);//------------------------
				
			MRTInterfaceProd it = new MRTInterfaceProd(ctx, 0, trxName);
			it.setM_Product_ID(mProductID);
			it.insertInterface(true);	
			it.setTablaOrigen("MProduct-Articulos");	
			String desc = "UPDATE de producto - "+it.getDescription();
			it.setDescription(desc);		
			it.set_ValueOfColumn("ResultadoEnvioWS", retorno);
			if("OK".equalsIgnoreCase(retorno)){
				it.setReadingDate(new Timestamp (System.currentTimeMillis()));
			}
			it.saveEx();	
		}
	}
	public abstract String updateArticulo(Properties ctx,int mClientID, int mProductID, String trxName);
	
	//--------- FALTA IMPLEMENTAR PORQUE NO SE TIENEN LOS DATOS CORRECTOS DEL PRODUCTO ------------
	public void eliminarProducto(Properties ctx,int mClientID, String prodCode, String trxName){
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
//			MRTSisteco sis = new MRTSisteco();
//			sis.deleteArticulo(ctx, mClientID, prodCode, trxName);
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
			MRTScanntech sis = new MRTScanntech(ctx,trxName);
			String retorno = sis.deleteArticulo(ctx, mClientID, prodCode, trxName);
			
//			MRTInterfaceProd it = new MRTInterfaceProd(ctx, 0, trxName);
//			it.setM_Product_ID(mProductID);
//			it.insertInterface(true);	
//			it.setTablaOrigen("MProduct-Articulos");	
//			String desc = "DELETE de producto - "+it.getDescription();
//			it.setDescription(desc);		
//			it.set_ValueOfColumn("ResultadoEnvioWS", retorno);
//			if("OK".equalsIgnoreCase(retorno)){
//				it.setReadingDate(new Timestamp (System.currentTimeMillis()));
//			}
//			it.saveEx();	
			
		}
	}
	public abstract String deleteArticulo(Properties ctx,int mClientID, String prodCode, String trxName);
	
//	METODOS PARA INTERFACEAR  PRECIOS
	
	/**
	 * Metodo para actualizar precio por producto
	 * @author OpenUp SBT Issue#  20/12/2015 9:26:33
	 * @param ctx
	 * @param mClientID
	 * @param mProductID
	 * @param trxName
	 */
	public static void actualizaPrecioArticulo(Properties ctx,int mClientID, int mProductID,int cCurrID,BigDecimal price, int adOrgID, String trxName){
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
			
			MRTSisteco sis = new MRTSisteco();
			sis.updateArticuloPrecio(ctx, mClientID, mProductID, cCurrID, price, adOrgID,trxName);
			
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
			MRTScanntech sis = new MRTScanntech(ctx,trxName);
			String retorno = sis.updateArticuloPrecio(ctx, mClientID, mProductID,cCurrID,price,adOrgID, trxName);
			
			MRTInterfaceProd it = new MRTInterfaceProd(ctx, 0, trxName);
			it.setM_Product_ID(mProductID);
			it.insertInterface(false);	
			it.setTablaOrigen("MProduct-PrecioArticulos");			
			it.setM_Product_ID(mProductID);
			it.setDescription("UPDATE precio de producto "+it.getDescription());
			it.set_ValueOfColumn("ResultadoEnvioWS", retorno);
			if("OK".equalsIgnoreCase(retorno)){
				it.setReadingDate(new Timestamp (System.currentTimeMillis()));
			}
			it.saveEx();		
		}
	}
	public abstract String updateArticuloPrecio(Properties ctx,int mClientID, int mProductID,int cCurrID,BigDecimal price,int mOrgID, String trxName);

	//METODOS PARA INTERFACEAR CODIGOS DE BARRA
	/**
	 * Interfacear según cliente 
	 * @author OpenUp SBT Issue#  16/12/2015 16:26:20
	 * @param ctx
	 * @param mClientID
	 * @param mProductID
	 * @param trxName
	 */
	public static void crearUPC(Properties ctx,int mClientID,String mUPC, int mProductID, String trxName){
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
		
			MRTSisteco sis = new MRTSisteco();
			sis.insertUPC(ctx, mClientID,mUPC, mProductID, trxName);
		
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->se envia por ws a scanntech
			
			MRTScanntech sis = new MRTScanntech(ctx,trxName);
			String retorno = sis.insertUPC(ctx, mClientID,mUPC, mProductID, trxName);
			
			MRTInterfaceProd it = new MRTInterfaceProd(ctx, 0, trxName);
			it.setM_Product_ID(mProductID);
			it.insertInterface(true);	
			it.setTablaOrigen("MProduct-Articulos-Barras");			
			it.setM_Product_ID(mProductID);
			it.setDescription("INSERT codigo de barra"+it.getDescription());
			it.set_ValueOfColumn("ResultadoEnvioWS", retorno);
			if("OK".equalsIgnoreCase(retorno)){
				it.setReadingDate(new Timestamp (System.currentTimeMillis()));
			}
			it.saveEx();
		}
	}
	public abstract String insertUPC(Properties ctx,int mClientID,String mUPC, int mProductID, String trxName);
//OJO SE ESPERA RESPUESTA DE SCANNTECH ---> 21/01/2016
	public static void actualizarUPC(Properties ctx,int mClientID,String mUPC, int mProductID, String trxName){
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
//			MRTSisteco sis = new MRTSisteco();
//			sis.updateArticulo(ctx, mClientID, mProductID, trxName);
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
			MRTScanntech sis = new MRTScanntech(ctx,trxName);
			String retorno = sis.updateUPC(ctx, mClientID, mUPC, mProductID, trxName);
			
			MRTInterfaceProd it = new MRTInterfaceProd(ctx, 0, trxName);
			it.setM_Product_ID(mProductID);
			it.insertInterface(false);	
			it.setTablaOrigen("MProduct-Articulos-Barras");			
			it.setM_Product_ID(mProductID);
			it.setDescription("UPODATE codigo de barra"+it.getDescription());
			it.set_ValueOfColumn("ResultadoEnvioWS", retorno);
			if("OK".equalsIgnoreCase(retorno)){
				it.setReadingDate(new Timestamp (System.currentTimeMillis()));
			}
			it.saveEx();
		}
	}
	public abstract String updateUPC(Properties ctx,int mClientID,String mUPC, int mProductID, String trxName);
	
	/**
	 * Permite interfacear la eliminación de un codigo de barra según el "proveedro" que corresponda 
	 * @author OpenUp SBT Issue#  21/1/2016 17:07:36
	 * @param ctx
	 * @param mClientID
	 * @param mUPC
	 * @param mProductID
	 * @param trxName
	 */
	public static Boolean eliminarUPC(Properties ctx,int mClientID, String mUPC, int mProductID, String trxName){
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
		String retorno="";
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
			MRTSisteco sis = new MRTSisteco();
			retorno = sis.deleteUPC(ctx, mClientID, mUPC,mProductID, trxName);
			
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
			MRTScanntech sis = new MRTScanntech(ctx,trxName);
			retorno = sis.deleteUPC(ctx, mClientID, mUPC,mProductID, trxName);
			
			MRTInterfaceProd it = new MRTInterfaceProd(ctx, 0, trxName);
			it.setM_Product_ID(mProductID);
			it.set_ValueOfColumn("ResultadoEnvioWS", retorno);
			it.setDescription("Eliminar codigo de barra "+mUPC+" del producto "+ mProductID);
			if("OK".equalsIgnoreCase(retorno)){
				it.setReadingDate(new Timestamp (System.currentTimeMillis()));
			}
			it.insertInterface(true);
		}
		if(retorno.equalsIgnoreCase("OK")){
			return true;
		}else return false;
		
	}
	public abstract String deleteUPC(Properties ctx,int mClientID,String mUPC, int mProductID, String trxName);
	
	//METODOS PARA ACTUALIZAR  MOVIMIENTOS
	
	/**Interfacear producto al sistema de balanza 
	 * @author OpenUp SBT 19-02-2016 Issue #5419
	 */
	public static Boolean insertarProdABalanza(Properties ctx,int mClientID, String mUPC, 
			int mProductID, String trxName){
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
		String retorno="";
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
			MRTSisteco sis = new MRTSisteco();
			retorno = sis.insertProdScale(ctx, mClientID, mUPC,mProductID, trxName);
			
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
			//FALTA IMPLEMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		}
		if(retorno.equalsIgnoreCase("OK")){
			return true;
		}else return false;
	}

	public abstract String insertProdScale(Properties ctx,int mClientID,String mUPC, int mProductID, String trxName);

	/**
	 * Metodo para interfacear eliminación de un producto al sistema de balanza
	 * @author OpenUp SBT Issue#  18/2/2016 17:21:57
	 * @param ctx
	 * @param mClientID
	 * @param mUPC
	 * @param mProductID
	 * @param trxName
	 * @return
	 */
	
	public static Boolean eliminarProdDeBalanza(Properties ctx,int mClientID, String mUPC, 
			int mProductID, String trxName){
		String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
		String retorno="";
		if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco
			MRTSisteco sis = new MRTSisteco();
			retorno = sis.deleteProdFromScale(ctx, mClientID, mUPC,mProductID, trxName);
			
		}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
			//FALTA IMPLEMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		}
		if(retorno.equalsIgnoreCase("OK")){
			return true;
		}else return false;
	}

	public abstract String deleteProdFromScale(Properties ctx,int mClientID,String mUPC, int mProductID, String trxName);

	
	//METODOS PARA CONSULTAR MOVIMIENTOS
		
		/*Devuelve el detalle de movimiento con número de ticket indicado.
			Para la consulta, debe especificar un rango de fechas (fecha de, fecha).
			El rango debería ser menos de 5 días.*/
		/**
		 * Metodo para obtener datos de un ticket específicamente
		 * @author OpenUp SBT Issue#  20/12/2015 9:27:01
		 * @param ctx
		 * @param mClientID
		 * @param trxName
		 * @param idLocal
		 * @param idCaja
		 * @param nroCupon
		 * @param fchDesde
		 * @param fchHasta
		 * @return
		 */
		public static JSONObject enviarMovimiento(Properties ctx,int mClientID, String trxName,int idLocal,
				int idCaja,String nroCupon,Timestamp fchDesde, Timestamp fchHasta){
			String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
			if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco

			}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
				String f1 = fchDesde.toString();
				String  formato = f1.substring(0, 10)+"T"+f1.substring(11)+"00-0300";
				System.out.println(formato);
				String f2 = fchHasta.toString();		//2015-03-16T00:00:00.000-0300
				String formato2 =  f2.substring(0, 10)+"T"+f2.substring(11)+"00-0300";
				System.out.println(formato2);
				
				MRTScanntech sis = new MRTScanntech(ctx,trxName);
				JSONObject retorno = sis.getMovimiento(ctx, mClientID,trxName,idLocal,idCaja,nroCupon,formato,formato2);
				return retorno;

			}
			return null;
		}	
		public abstract JSONObject getMovimiento(Properties ctx,int mClientID, String trxName, int idLocal,int idCaja,
				String nroCupon,String fchS,String fchTo);

		/*Devuelve el detalle de los movimientos compuestos por la cabecera, el detalle y pagos
			para cada movimiento procesada en POS después de la marca de tiempo especificado.
			Enviar todos los movimientos de la fecha indicada.*/
		/**
		 * Metodo para obtener datos de los tickets de un día específico
		 * @author OpenUp SBT Issue#  21/12/2015 9:27:28
		 * @param ctx
		 * @param mClientID
		 * @param idLocal
		 * @param trxName
		 * @param idCaja
		 * @param nroCupon
		 * @param fchTickets
		 * @param idEmpresa 
		 * @return
		 */
		public static JSONArray enviarMovimientoTimestamp(Properties ctx,int mClientID, String trxName,int idLocal,
				int idCaja,Timestamp fchTickets, int idEmpresa){
			String provider = MSysConfig.getValue("UY_RT_PROVIDER", mClientID);
			if(provider.equalsIgnoreCase("Covadonga")){//si es covadonga -->sisteco

			}else if (provider.equalsIgnoreCase("Planeta")) {//Si es planeta -->scanntech
				MRTScanntech sis = new MRTScanntech(ctx,trxName);
				String f1 = fchTickets.toString();
				String  formato = f1.substring(0, 10)+"T"+f1.substring(11)+"00-0300";
				JSONArray retorno = sis.getMovimientoTimestamp(ctx,trxName,mClientID, idLocal,idCaja,formato,idEmpresa);
				
				return retorno;
			}
			return null;
		}
		public abstract JSONArray getMovimientoTimestamp(Properties ctx,String trxName,int mClientID, int idLocal,int idCaja,String fchTicket,int idEmpresa);

		
}
