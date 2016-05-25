/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCurrency;
import org.compiere.model.MProduct;
import org.compiere.model.MTaxCategory;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.util.OpenUpUtils;


/**
 * org.openup.OpenUp371 - MRTInterfaceProd
 * OpenUp Ltda. Issue #4404 
 * Description: Clase para tabla de interfaz.  
 * @author INes Fernandez - 05/2015
  */

public class MRTInterfaceProd extends X_UY_RT_InterfaceProd{
	
	private static final long serialVersionUID = 1L;

	
	/** Articulo Equivalente = AE (dede codigo de barras) */
	public static final String TABLAORIGEN_ArtEquivalente = "AE";
	/** Tandem = T  (desde LDM) */
	public static final String TABLAORIGEN_Tandem = "T";
	/** Desde el Producto = A */
	public static final String TABLAORIGEN_Prod = "A";
	/** Desde el Cliente = "C" */
	public final String TABLAORIGEN_Cli = "C"; //VER: VA A IR A OTRA TABLA
	

	public MRTInterfaceProd(Properties ctx, int mInterfaceProdID, String trxName) {
		super(ctx, mInterfaceProdID, trxName);
	}

	public MRTInterfaceProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}


	/***
	 * Inserta un nuevo registro de interface de producto para mi producto.
	 * OpenUp Ltda. Issue #4404 
	 * @author Gabriel Vila - 17/06/2015
	 * @see
	 * @param newProduct : true si es un nuevo producto para retail, false si no.
	 * @return
	 */
	public void insertInterface(boolean newProduct){

		MProduct prod = new MProduct(getCtx(), this.getM_Product_ID(), get_TrxName());
		
		this.setTablaOrigen("A");
		this.setAttributesFromProduct(this.getM_Product_ID());

		String action= ((newProduct)? "insert" : "update" );
		this.setUY_RT_Action_ID(MRTAction.forValue(getCtx(), action, get_TrxName()).get_ID());
		
		MTaxCategory taxCat = (MTaxCategory)prod.getC_TaxCategory();
		
		this.setC_TaxCategory_ID(taxCat.get_ID());

		String sql = "select c_tax_id from c_tax where c_taxcategory_id = " + taxCat.get_ID() + " and isdefault = 'Y'";			

		int tax_id = DB.getSQLValueEx(get_TrxName(), sql);	
		
		this.setC_Tax_ID(tax_id);
		
		this.setProdAttr(this.getM_Product_ID());
		
		this.setIsActive(true);
		
		this.saveEx();


	}
	//carga los atributos propios del producto
	public String setAttributesFromProduct(int prod_ID){
		String result = null;
		try{
			MProduct prod = (MProduct)this.getM_Product();	
			this.setM_Product_ID(prod.get_ID());
			this.setProdCode(prod.getValue());
			this.setDescription(prod.getName());
			this.setEnvCode(0);//TODO
			this.setMeasureCode("0");//TODO: hace referencia al CFE
			this.setUY_SubFamilia_ID(prod.getUY_SubFamilia_ID());
			this.setUnitsPerPack(prod.getUnitsPerPack());
			
			//OpenUP SBT 07/04/2016 Issue #5733 (ahora se maneja venta en dolares y pesos
			//Primero se controla si existe precio en la lista en dolares sino busco en la de pesos
			BigDecimal price = null;
			int currID = 0;
			int[] lst = MCurrency.getAllIDs("C_Currency", " IsActive = 'Y' ",null);
			for (int i=0;i<lst.length;i++){
				if(null==price){
					currID = lst[i];
					price = prod.getSalePrice(currID);	
				}	
			}
			if(null==price || price.equals(Env.ZERO)){
				currID = OpenUpUtils.getSchemaCurrencyID(getCtx(),this.getAD_Client_ID(),null);
				price = prod.getSalePrice(currID);//Precio en moneda nacional
				if(null==price){
					price = Env.ZERO;
				}
			}//FIN SBT Issue #5733 08/04/2016		
				
			this.setPriceList(price);
			this.setC_Currency_ID(currID);		
//			if(prod.getSalePrice()!=null) this.setPriceList(prod.getSalePrice());			
//			this.setC_Currency_ID(prod.getSaleCurrency());
			
			boolean active = true;
			if (!prod.isActive()) active = false;
			setIsActive(active);
			
			this.setC_TaxCategory_ID(prod.getC_TaxCategory_ID());
			//this.saveEx();
			return result;
			}
			
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
	public String setProdAttr(int prod_ID){
		String result = null;
		try{
			MProduct prod = new MProduct(getCtx(), prod_ID, get_TrxName());
			List<MProductAttribute> prodatts = prod.getAttributes();
			for (MProductAttribute prodatt: prodatts){
				MProdAttribute att = (MProdAttribute)prodatt.getUY_ProdAttribute();
				this.set_Value(att.getValue(), ((prodatt.isSelected()) ? 1 : 0));
			}
			this.saveEx();
			} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
				
		return result;
	}
	
	
	
	/***
	 * Actualiza los datos propios del prod para un nuevo registro de interface.
	 * OpenUp Ltda. Issue #4404 
	 * @author INes Fernandez - 22/06/2015
	 * @see
	 * @param newProduct : true si es un nuevo producto para retail, false si no.
	 * @return
	 */
	public void updateInterface(){

			// Seteo atributos que salen directamente de la definicion del producto
			MProduct prod = (MProduct) this.getM_Product();
			this.setM_Product_ID(prod.get_ID());
			this.setProdCode(prod.getValue());
			this.setDescription(prod.getName());
			this.setEnvCode(0);// TODO
			this.setMeasureCode("0");//TODO: hace referencia al CFE
			this.setUY_SubFamilia_ID(prod.getUY_SubFamilia_ID());
			this.setUnitsPerPack(prod.getUnitsPerPack());
			this.setTablaOrigen("A");
			
			
			//OpenUP SBT Issue # (ahora se maneja venta en dolares y pesos
			//Primero se controla si existe precio en la lista en dolares sino busco en la de pesos
			
			int currID = 100;
			BigDecimal price = prod.getSalePrice(currID);//Precio en USD
			if(null==price || price.equals(Env.ZERO)){
				currID = OpenUpUtils.getSchemaCurrencyID(getCtx(),this.getAD_Client_ID(),null);
				price = prod.getSalePrice(currID);//Precio en moneda nacional
				if(null==price){
					price = Env.ZERO;
				}
			}
			this.setPriceList(price);
			this.setC_Currency_ID(currID);
			//this.setPriceList(prod.getSalePrice());			
			//this.setC_Currency_ID(prod.getSaleCurrency());

			boolean active = true;
			if (!prod.isActive())
				active = false;
			setIsActive(active);
			
			MTaxCategory taxCat = (MTaxCategory)prod.getC_TaxCategory();

			this.setC_TaxCategory_ID(taxCat.get_ID());
																
			String sql = "select c_tax_id from c_tax where c_taxcategory_id = " + taxCat.get_ID() + " and isdefault='Y'";
			
			int tax_id = DB.getSQLValueEx(this.get_TrxName(), sql);
			
			this.setC_Tax_ID(tax_id);
			
			setAttributesFromProduct(prod.get_ID());
			
			this.saveEx();
	}
	
	
	/***
	 * Obtiene y retorna registro a interfacear para un determinado producto recibido.
	 * OpenUp Ltda. Issue #4404 
	 * @author Gabriel Vila - 17/06/2015
	 * @see
	 * @param ctx
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static MRTInterfaceProd forProduct(Properties ctx, int mProductID, String trxName) {

		String whereClause = X_UY_RT_InterfaceProd.COLUMNNAME_M_Product_ID + "=" + mProductID +
				" AND " + X_UY_RT_InterfaceProd.COLUMNNAME_ReadingDate + " is null AND "+
				X_UY_RT_InterfaceProd.COLUMNNAME_TablaOrigen + " = 'A' AND "+
				X_UY_RT_InterfaceProd.COLUMNNAME_UY_RT_Action_ID +" <> " + 
				MRTAction.forValue(ctx, "insert", trxName).get_ID(); 
		
		MRTInterfaceProd model = new Query(ctx, I_UY_RT_InterfaceProd.Table_Name, whereClause, trxName).first();
		
		return model;
	}
	
	/***
	 * Impacta en la tabla de interfaz para productos se crea o elimina un código de barras (insert ó delete, los códigos de barras no admiten update)
	 * OpenUp Ltda. Issue #4404 
	 * @author INes Fernandez - 22/06/2015
	 * @see
	 * @param action: insert ó delete; no se permiten modificaciones sobre el código de barras
	 * @param UPC:código de barras
	 * @param get_ID
	 * @param get_TrxName
	 * 
	 * @return
	 */
	public void toInterfaceRow(int prod_ID, String action, String UPC){
		try{
			MProduct prod = new MProduct(getCtx(), prod_ID, get_TrxName());
			
			this.setM_Product_ID(prod.get_ID());
			this.setTablaOrigen("AE");
			this.setProdCode(prod.getValue());
			this.setDescription(prod.getName());
			this.setUY_RT_Action_ID(MRTAction.forValue(getCtx(), action, get_TrxName()).get_ID());
			/*if(action=="insert")*/ this.setUPC(UPC);
			this.saveEx();
		}
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
	/***
	 * Inserta lineas en la tabla de interfaz para productos cuando se cambian los precios masivamente, desde Actualización de Lista de Precios.
	 * OpenUp Ltda. Issue #4404 
	 * @author INes Fernandez - 30/06/2015
	 * @param pl lista de las lineas de precios a actualizar
	 * @param c_Currency_id 
	 * @param ctx
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static void insertInterfaceFromPriceList(List<MPriceUpdateLine> pl, int c_Currency_id, Properties ctx,  String trxName){

		
		for (MPriceUpdateLine line : pl) {
			MRTInterfaceProd it = new MRTInterfaceProd(ctx, 0, trxName);
			MProduct prod = (MProduct) line.getM_Product();

			it.setM_Product_ID(prod.get_ID());
			it.setProdCode(prod.getValue());
			it.setDescription(prod.getName());
			it.setEnvCode(0);// TODO
			it.setMeasureCode("0");// TODO: hace referencia al CFE
			it.setUY_SubFamilia_ID(prod.getUY_SubFamilia_ID());
			it.setUnitsPerPack(prod.getUnitsPerPack());

			it.setM_Product_ID(line.getM_Product_ID());
			it.setPriceList(line.getPriceList());
			it.setC_Currency_ID(c_Currency_id);

			it.setTablaOrigen(TABLAORIGEN_Prod);
			it.setUY_RT_Action_ID(MRTAction.forValue(ctx, "update", trxName)
					.get_ID());

			it.setProdAttr(prod.get_ID());
			
			MTaxCategory taxCat = (MTaxCategory)prod.getC_TaxCategory();
			
			it.setC_TaxCategory_ID(taxCat.get_ID());
			
			String sql = "select c_tax_id from c_tax where c_taxcategory_id = " + taxCat.get_ID() + " and isdefault='Y'";
						
			int tax_id = DB.getSQLValueEx(trxName, sql);
			
			it.setC_Tax_ID(tax_id);
			
			it.saveEx();
		}
	}
	
	/***
	 * Impacta en la tabla de interfaz para productos, insert/update con precios.
	 * OpenUp Ltda. Issue #4404 
	 * @author INes Fernandez - 30/06/2015
	 * @param mprod
	 * @param c_Currency_id 
	 * @param mPrice precio 
	 * @param ctx
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static void insertInterface(MProduct mprod, int c_Currency_ID, BigDecimal mPrice, Properties ctx,  String trxName){
	
		MRTInterfaceProd it= new MRTInterfaceProd(ctx, 0, trxName);
		it.setM_Product_ID(mprod.get_ID());
		
		it.setProdCode(mprod.getValue());
		it.setDescription(mprod.getName());
		it.setEnvCode(0);//TODO: hardcoded
		it.setMeasureCode("0");//TODO: hardcoded, ref: CFE
		it.setUY_SubFamilia_ID(mprod.getUY_SubFamilia_ID());
		it.setUnitsPerPack(mprod.getUnitsPerPack());
		it.setTablaOrigen("A");
				
		boolean active = true;
		if (!mprod.isActive()) active = false;
		it.setIsActive(active);
				
		it.setPriceList(mPrice);			
		it.setC_Currency_ID(c_Currency_ID);
		it.setUY_RT_Action_ID(MRTAction.forValue(ctx, "update", trxName).get_ID());
		it.setProdAttr(mprod.get_ID());
		
		MTaxCategory taxCat = (MTaxCategory)mprod.getC_TaxCategory();
		
		it.setC_TaxCategory_ID(taxCat.get_ID());
	
		String sql = "select c_tax_id from c_tax where c_taxcategory_id = " + taxCat.get_ID() + " and isdefault='Y'";
		
		int tax_id = DB.getSQLValueEx(trxName, sql);	
		
		it.setC_Tax_ID(tax_id);
		
		it.saveEx();
		
	}
	
	
	
	/**Retorna InterfaceProd no leidos y activos
     * OpenUp Ltda Issue#
     * @author SBouissa 18/6/2015
     * @return
     */
    public static List<MRTInterfaceProd> forProductsNotR(Properties ctx,String trxName) {
        String whereClause = X_UY_RT_InterfaceProd.COLUMNNAME_IsActive + " = 'Y'" +
                " AND " + X_UY_RT_InterfaceProd.COLUMNNAME_ReadingDate + " is null ";
               
        String orderBy =  X_UY_RT_InterfaceProd.COLUMNNAME_Updated ;//+","+ 
                //X_UY_RT_InterfaceProd.COLUMNNAME_UY_RT_Action_ID;
       
        List<MRTInterfaceProd> lines = new Query(ctx, I_UY_RT_InterfaceProd.Table_Name, whereClause, trxName).setOrderBy(orderBy).list();
        if(0<lines.size()){
        	return lines;
        }else{
        	return null;
        }
        
    }
    
    /***
	 * Impacta en la tabla de interfaz para productos insert/update/delete relativos al Tandem
	 * OpenUp Ltda. Issue #4404 
	 * @author INes Fernandez - 30/06/2015
	 * @param mprod
	 * @param mProdtandem_ID producto que se asocia
	 * @param action (UY_RT_Action) 
	 * @param ctx
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
  	public static void insertInterfaceFromTandem(MProduct mProd, int mProdtandem_ID, String action,  Properties ctx,  String trxName){
  	
  		MRTInterfaceProd it= new MRTInterfaceProd(ctx, 0, trxName);
		
		it.setTablaOrigen(TABLAORIGEN_Tandem);
		it.setM_Product_ID(mProd.get_ID());
		it.setUY_RT_Action_ID(MRTAction.forValue(ctx, action, trxName).get_ID());
		it.setProdCode(mProd.getValue());
		if(!action.equalsIgnoreCase("delete")) it.setM_Product_Tandem_ID(mProdtandem_ID);
		it.saveEx();
  		
  	}
  	
  	/**Recibe el producto actual, value del producto a eliminar (puede ser value anterior)
  	 * 
  	 * @author OpenUp SBT Issue #5369  25/1/2016 15:11:38
  	 * @param mProd
  	 * @param oldCodProd
  	 * @param ctx
  	 * @param trxName
  	 */
  	public static void insertInterfaceDeleteProd(MProduct mProd, String oldCodProd, Properties ctx,  String trxName){
  	  	String action = "delete";
  		MRTInterfaceProd it= new MRTInterfaceProd(ctx, 0, trxName);
		
  		try{
  			it.setTablaOrigen(TABLAORIGEN_Prod);
  			it.setM_Product_ID(mProd.get_ID());
  			it.setUY_RT_Action_ID(MRTAction.forValue(ctx, action, trxName).get_ID());
  			it.setProdCode(oldCodProd);//-->VALUE ANTERIOR
  			it.setDescription("Eliminar artículo con codigo "+oldCodProd);
  			it.saveEx();
  	  		
  		}catch(Exception e){
  			e.toString();
  		}
		
  	}
  	
  	/**
  	 * 
  	 * @author OpenUp SBT Issue # #5369  25/1/2016 16:01:47
  	 * @param mProd
  	 * @param oldCodProd
  	 * @param ctx
  	 * @param trxName
  	 */
  	public static void insertInterfaceDeleteAE(MProduct mProd, String oldCodProd, String mUPC,Properties ctx,  String trxName){
  	  	String action = "delete";
  		MRTInterfaceProd it= new MRTInterfaceProd(ctx, 0, trxName);
  		try{
  			it.setTablaOrigen(TABLAORIGEN_ArtEquivalente);
  			it.setM_Product_ID(mProd.get_ID());
  			it.setUY_RT_Action_ID(MRTAction.forValue(ctx, action, trxName).get_ID());
  			it.setUPC(mUPC);
  			it.setProdCode(oldCodProd);
  			it.setDescription("Eliminar codigo de barra asociado al codigo de producto "+oldCodProd);

  			it.saveEx();
  	  		
  		}catch(Exception e){
  			e.toString();
  		}
		
  	}
  	
  	/**
  	 * 
  	 * @author OpenUp SBT Issue #5369  25/1/2016 16:20:17
  	 * @param mProd
  	 * @param oldCodProd
  	 * @param ctx
  	 * @param trxName
  	 */
  	public static void insertInterfaceDeleteTandem(MProduct mProd, String oldCodProd, Properties ctx,  String trxName){
  	  	String action = "delete";
  		MRTInterfaceProd it= new MRTInterfaceProd(ctx, 0, trxName);
		
  		try{
  			it.setTablaOrigen(TABLAORIGEN_Tandem);
  			it.setM_Product_ID(mProd.get_ID());
  			it.setUY_RT_Action_ID(MRTAction.forValue(ctx, action, trxName).get_ID());
  			it.setProdCode(oldCodProd);
  			it.setDescription("Eliminar tandem asociado al codigo de producto "+oldCodProd);

  			it.saveEx();
  		}catch (Exception e){
  			e.toString();
  		}
		 		
  	}
}
