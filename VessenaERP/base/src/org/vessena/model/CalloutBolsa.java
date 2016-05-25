/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 20/07/2015
 */
package org.openup.model;

import java.math.BigDecimal;
import java.util.Properties;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.compiere.model.MSequence;


/**
 * org.openup.model - CalloutBolsa
 * OpenUp Ltda. Issue #4153 
 * Description: Callouts asociado a modulo de bolsa.
 * @author Gabriel Vila - 20/07/2015
 * @see
 */
public class CalloutBolsa extends CalloutEngine {

	/**
	 * Constructor.
	 */
	public CalloutBolsa() {
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/07/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setProdInstrument(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";

		int id = ((Integer)value).intValue();
		if (id <= 0) return "";

		MProduct prod = new MProduct(ctx, id, null);
		
		if (prod.get_ID() <= 0){
			return "";
		}
		
		mTab.setValue(X_UY_BG_Instrument.COLUMNNAME_C_UOM_ID, prod.getC_UOM_ID());
		mTab.setValue(X_UY_BG_Instrument.COLUMNNAME_UY_BG_Quality_ID, prod.get_Value("UY_BG_Quality_ID"));
		mTab.setValue(X_UY_BG_Instrument.COLUMNNAME_UY_BG_PackingMode_ID, prod.get_Value("UY_BG_PackingMode_ID"));
		
		// Instancio clasificacion del producto para obtener retencion
		mTab.setValue(X_UY_BG_Instrument.COLUMNNAME_UY_BG_Retention_ID, null);
		if ( prod.get_Value("UY_BG_ProdType_ID")!=null){			
		int idType = ((Integer) prod.get_Value("UY_BG_ProdType_ID")).intValue();
		MBGProdType prodType= new MBGProdType(ctx,idType,null);		
		mTab.setValue(X_UY_BG_Instrument.COLUMNNAME_UY_BG_Retention_ID, prodType.getUY_BG_Retention_ID());
		}
				
		return "";
	}
	
	/**
	 * org.openup.model - CalloutBolsa
	 * OpenUp Ltda. Issue #4153 
	 * Description: Calcular el monto total
	 * @author Andrea Odriozola - 20/07/2015
	 * @see
	 */
	

	//public class CalloutTotal extends CalloutEngine{

		public String test (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
			
				  // Tomar el primer valor, el precio del producto
				  BigDecimal Price = ((BigDecimal)mTab.getValue("PriceEntered"));
				  //Tomar la cantidad
				  if (Price.intValue()<=0)
					  return "";
				  BigDecimal Qty = ((BigDecimal)mTab.getValue("QtyEntered"));
				  if (Qty.intValue()<=0)
					  return "";
				  // El Total o monto es la multiplicacion de la cantidad y el precio
				 
				  BigDecimal Total = Qty.multiply(Price);
				      
				  // Set value to column field
				  mTab.setValue(X_UY_BG_Transaction.COLUMNNAME_Amt, Total);
				  
				  return "OK"; 

		}
		

		/***
		 * 
		 * OpenUp Ltda. Issue # 
		 * @author SBouissa - 01/09/2015
		 * @see
		 * @param ctx
		 * @param WindowNo
		 * @param mTab
		 * @param mField
		 * @param value
		 * @return
		 */
		public String setCodeToClient (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
			
			if (value == null) return "";
			//Obtengo el id de la bolsa seleccionada
			int id = ((Integer)value).intValue();
			if (id <= 0) return "";

			MBGBursa bolsa = new MBGBursa(ctx, id, null);
			
			if (bolsa.get_ID() <= 0){
				return "";
			}
			int idSequenciaCodigo = 1004245; //(UY_Seq_Code_RegCliente)
			MSequence seq = new MSequence(ctx, idSequenciaCodigo, null);
			int nextID = seq.getNextID(); //obtengo el valor y ya se incrementa al siguiente
			mTab.setValue(X_UY_UserReq.COLUMNNAME_Code, nextID);
					
			return "";
		}
		
		/***
		 * Solicitado via mail 01-09-2015 de Roberto Silva 
		 * Mostrar número de cédula según el sub-cliente seleccionado.
		 * OpenUp Ltda. Issue # 
		 * @author SBouissa - 02/09/2015
		 * @see
		 * @param ctx
		 * @param WindowNo
		 * @param mTab
		 * @param mField
		 * @param value
		 * @return
		 */
		public String setCedulaFromSubCustomer (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
			
			int i = mTab.getAD_Tab_ID();
			if(i!=1001229) return "";
			if (value == null) return "";
			//Obtengo el id del sibcliente seleccionado
			int id = ((Integer)value).intValue();
			if (id <= 0) return "";

			MBGSubCustomer subCust = new MBGSubCustomer(ctx, id, null);
			
			if (subCust.get_ID() <= 0){
				return "";
			}
			String cedula = subCust.getCedula();
			mTab.setValue(X_UY_BG_TransProd.COLUMNNAME_Cedula, cedula);
					
			return "";
		}
		
		public String loadDataClient (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
			String cedulaIn = value.toString().trim();
			MBGCustomer aux = MBGCustomer.forCedula(ctx, cedulaIn, null);
			if(null!=aux){
				mTab.setValue("FirstName", aux.getFirstName());
				mTab.setValue("SecondName", aux.getSecondName());
				mTab.setValue("FirstSurname", aux.getFirstSurname());
				mTab.setValue("SecondSurname", aux.getSecondSurname());
				mTab.setValue("RUC", aux.getRUC());
				mTab.setValue("Address1", aux.getAddress1());
				mTab.setValue("C_Region_ID", aux.getC_Region_ID());
				mTab.setValue("C_City_ID", aux.getC_City_ID());
				mTab.setValue("Phone", aux.getPhone());
				mTab.setValue("Phone_Ident", aux.isSmartPhone1());
				mTab.setValue("Phone_2", aux.getPhone_2());
				mTab.setValue("Phone_Ident_2", aux.isSmartPhone2());
				mTab.setValue("EMail", aux.getEMail());
				mTab.setValue("UY_BG_UserActivity_ID", aux.getUY_BG_UserActivity_ID());
				
			}
			return "";
		}
		
		public String loadDataSubClient (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
			String cedulaIn = value.toString().trim();
			MBGSubCustomer aux = MBGSubCustomer.forCedula(ctx, cedulaIn, null);
			if(null!=aux){
				mTab.setValue("FirstName", aux.getFirstName());
				mTab.setValue("SecondName", aux.getSecondName());
				mTab.setValue("FirstSurname", aux.getFirstSurname());
				mTab.setValue("SecondSurname", aux.getSecondSurname());
				//mTab.setValue("RUC", aux.getr);
				mTab.setValue("Address1", aux.getAddress1());
				mTab.setValue("C_Region_ID", aux.getC_Region_ID());
				mTab.setValue("C_City_ID", aux.getC_City_ID());
				mTab.setValue("Phone", aux.getPhone());
				mTab.setValue("Phone_Ident", aux.isSmartPhone1());
				mTab.setValue("Phone_2", aux.getPhone_2());
				mTab.setValue("Phone_Ident_2", aux.isSmartPhone2());
				mTab.setValue("EMail", aux.getEMail());
				mTab.setValue("UY_BG_UserActivity_ID", aux.getUY_BG_UserActivity_ID());
				
			}
			return "";

		}
		
//		/***
//		 * Solicitado via mail 01-09-2015 de Roberto Silva 
//		 * Cálculo de campo “Comisión Bagsa” en base al catálogo de Comisión Bagsa. Hay dos porcentajes de comisiones, una para Subastas  y otra para el resto de Contratos (Registro y Trader). En este caso, sería la comisión de Contratos.
//		 * OpenUp Ltda. Issue # 
//		 * @author SBouissa - 02/09/2015
//		 * @see
//		 * @param ctx
//		 * @param WindowNo
//		 * @param mTab
//		 * @param mField
//		 * @param value
//		 * @return
//		 */
//		public String calcRetentionDefIR (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
//			
//			int i = mTab.getAD_Tab_ID();
//			if(i!=1001229) return "";
//			if (value == null) return "";
//			
//			//Obtengo el id del producto seleccionado
//			int id = ((Integer)value).intValue();
//			if (id <= 0) return "";
//			MProduct prod = new MProduct(ctx, id, null);
//			if (prod.get_ID() <= 0)return "";
//			int clasifProdID = prod.get_ValueAsInt("UY_BG_ProdType_ID");
//			if (clasifProdID<=0) return "";
//			MBGProdType prodType = new MBGProdType(ctx, clasifProdID, null);
//			if(prodType.get_ID() <= 0)return "";
//			int taxRet = prodType.getUY_BG_Retention().gettax();
//			//Seteo el valor de retencion
//			mTab.setValue(X_UY_BG_TransProd.COLUMNNAME_Amt, taxRet);
//					
//			return "";
//		}
		
}
