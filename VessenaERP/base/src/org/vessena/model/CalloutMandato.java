package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.apache.ecs.xhtml.object;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.omg.CORBA.INTERNAL;
import org.zkoss.zhtml.Big;
/*
 * Autor: Oswaldo Blandon
 * Empresa: TIConsultores
 */

public class CalloutMandato extends CalloutEngine{
	
	/*
	
	/***
	 * Se realiza multiplicacion de cantidad por precio unitario se dara el resultado en total.
	 * se realiza calculo de total de comision y retencion.
	 * TICONSULTORES.
	 * OpenUp Ltda. Issue # 	  
	 * @author OBlandon - 10/09/2015
	 * @author SBouissa - 03/09/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
		public String total (Properties ctx, int windowno,GridTab mtab, GridField mfield, Object values ){
			BigDecimal multiA = (BigDecimal) mtab.getValue("Cantidad"); // campo de cantidad
			BigDecimal multiB = (BigDecimal) mtab.getValue("Price2"); // campo precio unitario
			BigDecimal multiC = BigDecimal.ZERO;
			Integer IntA = (Integer) multiA.intValue();
			Integer IntB = (Integer) multiB.intValue();
			Integer c =    (Integer) mtab.getValue("Name2");
			if (IntA == 0){
				System.out.println(" bien primer if");
				return "";
				
				}
			else if (IntB == 0){
				System.out.println(" bien segundo if");
				return "";
				
			}
			else if (c == 1){
				mtab.setValue("Name2", 0);
				System.out.println(c+" bien 3 if");
				return "";
			}
			
			
			else{
				multiC = multiA.multiply(multiB) ;	// calculo de total (campo * campo2)			
					;    
			    //se envia los resultados a sus correspondiente campos para ser registrado.
			    mtab.setValue("total2", (multiC).setScale(4, BigDecimal.ROUND_HALF_UP));
			    System.out.println(" total resultado");
			    /*Se debe realizar el calculo ya que depende del total.
				   *Se debe realizar el calculo ya que depende del total
				    Agregado por SBouissa.
				    */
				    calcRetentionDefIR(ctx,mtab,multiC);
					calcComisionBagsa(ctx,mtab,multiC);
					return "";
			}
			
			
			
		}
		/***
		 * Se realiza calculo de precio unitario cuando ingrese valor al campo total.
		 * TICONSULTORES.  
		 * @author OBlandon - 10/09/2015
		 * @see
		 * @param ctx
		 * @param WindowNo
		 * @param mTab
		 * @param mField
		 * @param value
		 * @return
		 */
		public String preciounitario (Properties ctx, int windowno,GridTab mtab, GridField mfield, Object values){
			BigDecimal divideA = (BigDecimal) mtab.getValue("total2"); // campo de cantidad
			BigDecimal divideB = (BigDecimal) mtab.getValue("Cantidad"); // campo precio unitario
			BigDecimal divideC = BigDecimal.ZERO;
			BigDecimal divideD = (BigDecimal) mtab.getValue("Price2");
			Integer X = (Integer) divideB.intValue(); //se realiza converción cast para redondear el valor obtenido
			Integer Y = (Integer) divideD.intValue(); //se realiza converción cast para redondear el valor obtenido
			if (X == 0)  { 
					
				return "";
				}
				
			
			
			else{
				divideC = divideA.divide(divideB,2);// calculo de precio unitario (campo / campo2)	
				
				//se envia los resultados a sus correspondiente campos para ser registrado.
				mtab.setValue("Name2", 1);
				mtab.setValue("Price2", divideC.setScale(4, BigDecimal.ROUND_HALF_UP));
				
			
				System.out.println(" else precio");
				return "";	
				
						    
			    
			    
			}
		}

		/***
		 * Solicitado via mail 01-09-2015 de Roberto Silva 
		 * Cálculo de campo “Comisión Bagsa” en base al catálogo de Comisión Bagsa. Hay dos porcentajes de comisiones, una para Subastas  y otra para el resto de Contratos (Registro y Trader). En este caso, sería la comisión de Contratos.
		 * OpenUp Ltda. Issue # 
		 * @author SBouissa - 03/09/2015
		 * @see
		 * @param ctx
		 * @param WindowNo
		 * @param mTab
		 * @param mField
		 * @param value
		 * @return
		 */
		public String calcRetentionDefIR (Properties ctx, GridTab mTab,BigDecimal total) {
			
			int i = mTab.getAD_Tab_ID();
			if(i!=1001229) return "";
			if (total == null)return "";
			//Obtengo el id del producto seleccionado
			Integer id = (Integer) mTab.getValue("M_Product_ID");// se cambio de int a Integer.
			if (id == null) return "";
			MProduct prod = new MProduct(ctx, id, null);
			if (prod.get_ID() <= 0){
				mTab.setValue(X_UY_BG_TransProd.COLUMNNAME_Amt, 0);
				return "";
			}
			int clasifProdID = prod.get_ValueAsInt("UY_BG_ProdType_ID");
			if (clasifProdID<=0){
				mTab.setValue(X_UY_BG_TransProd.COLUMNNAME_Amt, 0);
				return "";
			}
			MBGProdType prodType = new MBGProdType(ctx, clasifProdID, null);
			if(prodType.get_ID() <= 0)return "";
			
			BigDecimal taxRet = prodType.getUY_BG_Retention().gettax();
			BigDecimal resultado = total.multiply(taxRet.divide(new BigDecimal(100))) ;
			//Seteo el valor de retencion
			mTab.setValue(X_UY_BG_TransProd.COLUMNNAME_Amt, resultado.setScale(4, BigDecimal.ROUND_HALF_UP));
//
			return "";
		}
		
		/***
		 * Solicitado via mail 01-09-2015 de Roberto Silva 
		 * Cálculo de campo “Comisión Bagsa” en base al catálogo de Comisión Bagsa. Hay dos porcentajes de comisiones, una para Subastas  y otra para el resto de Contratos (Registro y Trader). En este caso, sería la comisión de Contratos.
		 * OpenUp Ltda. Issue # 
		 * @author SBouissa - 03/09/2015
		 * @see
		 * @param ctx
		 * @param WindowNo
		 * @param mTab
		 * @param mField
		 * @param value
		 * @return
		 */
		public String calcComisionBagsa (Properties ctx, GridTab mTab,BigDecimal total) {
			
			int i = mTab.getAD_Tab_ID();
			if(i!=1001229) return "";
			if (total == null) return "";  
			//@SQL=(SELECT Amount FROM UY_BG_Commission WHERE IsDefault = 'Y')
			
			//Obtengo la comision predeterminada UY_BG_Commission 
			MBGCommission comision = MBGCommission.forIsDefault(ctx,'Y',null);
			if (comision.get_ID() <= 0) return "";
			
			BigDecimal comContrato = comision.getAmount2();
			BigDecimal resultado = total.multiply(comContrato.divide(new BigDecimal(100))) ;
			//Seteo el valor de comision
			mTab.setValue(X_UY_BG_TransProd.COLUMNNAME_PriceEntered, resultado.setScale(4, BigDecimal.ROUND_HALF_UP));
					
			return "";
		}
}
