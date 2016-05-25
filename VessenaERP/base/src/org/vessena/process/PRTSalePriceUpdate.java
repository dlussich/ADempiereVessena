/**
 * 
 */
package org.openup.process;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.MProductAttribute;
import org.openup.model.MProductPrintLabel;
import org.openup.model.MRTInterfaceProd;
import org.openup.model.MRTInterfaceScales;
import org.openup.retail.MRTRetailInterface;

/**
 * @author Nicolas
 *
 */
public class PRTSalePriceUpdate extends SvrProcess {
	
	String fileName = null;
	int listID = 0;
	Timestamp validFrom = null;
	Sheet hoja=null;
		
	Workbook workbook=null;
	Integer tope =0;
	AuxWorkCellXLS utiles;

	/**
	 * 
	 */
	public PRTSalePriceUpdate() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("FileName")){
					this.fileName = ((String)para[i].getParameter());
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("M_PriceList_ID")){
					this.listID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("ValidFrom")){
					this.validFrom = ((Timestamp)para[i].getParameter());
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		this.loadData();
		
		return "OK";
	}
	
	private void loadData() throws Exception{

		String s=validacionXLSInicial();

		//Validacion inicial
		if(!(s.equals(""))){
			throw new AdempiereException (s);
		}	
			
		this.readXLS();		

	}
	
	private String validacionXLSInicial(){

		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) return ("No se encontro la planilla Excel");

		try {

			// Get de workbook
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la primer hoja
			hoja = workbook.getSheet(0);

			if (hoja.getColumns() < 1) return ("La primer hoja de la planilla Excel no tiene columnas");


			if (hoja.getRows() < 1) return ("La primer hoja de la planilla Excel no tiene columnas");

		}catch (Exception e) {
			return ("Error al abrir planilla (TRY)");
		}

		return"";
	}
	
	private String readXLS() throws Exception {
		
		String message = "";
		MPriceList list = null;
		// MPriceListVersion versionActual = null;
		MPriceListVersion versionVigente = null;
		MPriceListVersion newVersion = null;
		Cell cell = null;
		utiles = new AuxWorkCellXLS(getCtx(), getTable_ID(), getRecord_ID(),null, hoja, null);
		tope = hoja.getRows();
		MProduct prod = null;
		int cantVacias = 0;
		int round = 2;
		String sql = "", insert = "";
		
		Timestamp today = TimeUtil.trunc(
				new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

		Timestamp dateValid = TimeUtil.trunc(this.validFrom, TimeUtil.TRUNC_DAY);
		
		list = new MPriceList(getCtx(),this.listID,get_TrxName());

		round = list.getPricePrecision();// obtengo precision de la lista de precios
		
		//obtengo version vigente de la lista seleccionada
		versionVigente = list.getVersionVigente(this.validFrom);
		
		if(versionVigente != null && versionVigente.get_ID()>0){

			// creo nueva version
			newVersion = new MPriceListVersion(getCtx(), 0, get_TrxName());
			newVersion.setM_PriceList_ID(list.get_ID());
			newVersion.setName(dateValid.toString());
			newVersion.setM_DiscountSchema_ID(versionVigente.getM_DiscountSchema_ID());
			newVersion.setValidFrom(dateValid);
			newVersion.setIsActive(true);
			newVersion.saveEx(get_TrxName());
			
			// seteo inactiva las versiones anteriores, si corresponde
			if(!dateValid.after(today) && dateValid.compareTo(versionVigente.getValidFrom())>=0)
				DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id <> " + newVersion.get_ID() +
						" and m_pricelist_id = " + newVersion.getM_PriceList_ID()  + " and isactive = 'Y'", get_TrxName());
			
			//clono la version
			insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
					"updatedby,pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction)";

			sql = "select " + newVersion.get_ID() + ",m_product_id,ad_client_id,ad_org_id,isactive,now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + 
					",pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction" +
					" from m_productprice" +
					" where m_pricelist_version_id = " + versionVigente.get_ID();

			DB.executeUpdateEx(insert + sql, get_TrxName());

		} else throw new AdempiereException("Error al obtener ultima version activa para la lista seleccionada");		

		for (int recorrido = 3; recorrido < tope; recorrido++) {

			prod = null;

			try{

				//Se lee Codigo de Producto en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				String codProd=utiles.getStringFromCell(cell);
				int prodID = 0;
				if(codProd!=null && !codProd.equalsIgnoreCase("")){

					codProd = codProd.trim();

					prod = MProduct.forValue(getCtx(), codProd, get_TrxName());//obtengo producto por value

					if(prod!=null && prod.get_ID() > 0){

						prodID = prod.get_ID();						

					} else codProd = null; 

				} else codProd = null;
				//-----------------------------------------------------------------------------
				
				//Se lee Precio en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				BigDecimal price = Env.ZERO;
				String amt=utiles.getStringFromCell(cell);
				if(amt!=null && !amt.equalsIgnoreCase("")){
					
					try {
						
						amt = amt.replace(",", "");
						price = new BigDecimal(amt);		
							
					} catch (Exception e){
						
						throw new AdempiereException(e.getMessage());											
					}								
				}
				//------------------------------------------------------------------------------------------------------------------

				if(prodID > 0){
					
					Timestamp dateAction = dateValid; 
					String active = "Y";
					
					//verifico si existe el producto en la version nueva
					MProductPrice pprice = MProductPrice.forVersionProduct(getCtx(), newVersion.get_ID(), prodID, get_TrxName());
					
					if(pprice!=null){ //si el producto existe lo actualizo 
						
						dateAction = (Timestamp) pprice.get_Value("DateAction"); 
												
						//si se modifico el precio entonces seteo fecha de vigencia indicada en el cabezal para este producto
						if(pprice.getPriceList().setScale(round, RoundingMode.HALF_UP).compareTo(price.setScale(round, RoundingMode.HALF_UP))!=0) {
							
							dateAction = dateValid;		
							
							//impacto en tabla de interfase
							MRTInterfaceProd.insertInterface(prod, list.getC_Currency_ID(), price, getCtx(), get_TrxName());	
							
							// Marco precio cambiado en producto para impresion de etiquetas de precio
							MProductPrintLabel plabel = MProductPrintLabel.forProduct(getCtx(), prod.get_ID(), get_TrxName());
							if ((plabel == null) || (plabel.get_ID() <= 0)){
								plabel = new MProductPrintLabel(getCtx(), 0, get_TrxName());
								plabel.setM_Product_ID(prod.get_ID());
								plabel.set_ValueOfColumn("C_Currency_ID", list.getC_Currency_ID());//SBT 08/04/2016 Issue #5733
								plabel.saveEx();
							}
							//Impacto si corresponde en tabla de interfaceo para Balanzas - SBT 18-02-2016 Issue #5351
							if(prod.get_Value("UsaBalanza")!=null){
								if(prod.get_ValueAsBoolean("UsaBalanza")){
									//OpenUp SBT 04/04/2016 se evia el codigo interno ya que es el unico que corresponde enviar a las balanzas
									//MRTRetailInterface.insertarProdABalanza(getCtx(), this.getAD_Client_ID(), "0", prod.get_ID(), get_TrxName());
									MRTRetailInterface.insertarProdABalanza(getCtx(), this.getAD_Client_ID(), prod.getValue(), prod.get_ID(), get_TrxName());
								}	
							}
						}
						
						if(!pprice.isActive()) active = "N";
						
						sql = "update m_productprice set isactive = '" + active + "',pricelist = " + price.setScale(round, RoundingMode.HALF_UP) + ",pricestd = " + 
								price.setScale(round, RoundingMode.HALF_UP) + ",pricelimit = " + price.setScale(round, RoundingMode.HALF_UP) + 
								",dateaction = '" + dateAction + "' where m_pricelist_version_id = " + newVersion.get_ID() + 
								" and m_product_id = " + prodID;
						
						DB.executeUpdateEx(sql, get_TrxName());							
						
					} else { //si el producto no existe, creo nuevo registro
						
						insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
								"updatedby,pricelist,pricestd,pricelimit,dateaction)";

						sql = "select " + newVersion.get_ID() + "," + prodID + "," + newVersion.getAD_Client_ID() + "," + newVersion.getAD_Org_ID() + ",'" + 
						       active + "',now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + "," + price.setScale(round, RoundingMode.HALF_UP) + 
						       "," + price.setScale(round, RoundingMode.HALF_UP) + "," + price.setScale(round, RoundingMode.HALF_UP) + ",'" + dateAction + "'";					

						DB.executeUpdateEx(insert + sql, get_TrxName());	
						
						//impacto en tabla de interfase
						MRTInterfaceProd.insertInterface(prod, list.getC_Currency_ID(), price, getCtx(), get_TrxName());

						// Marco precio cambiado en producto para impresion de etiquetas de precio
						MProductPrintLabel plabel = MProductPrintLabel.forProduct(getCtx(), prod.get_ID(), get_TrxName());
						if ((plabel == null) || (plabel.get_ID() <= 0)){
							plabel = new MProductPrintLabel(getCtx(), 0, get_TrxName());
							plabel.setM_Product_ID(prod.get_ID());
							plabel.set_ValueOfColumn("C_Currency_ID", list.getC_Currency_ID());//SBT 08/04/2016 Issue #5733
							plabel.saveEx();
						}
						//Impacto si corresponde en tabla de interfaceo para Balanzas - SBT 18-02-2016 Issue #5351
						if(prod.get_Value("UsaBalanza")!=null){
							if(prod.get_ValueAsBoolean("UsaBalanza")){
								MRTRetailInterface.insertarProdABalanza(getCtx(), this.getAD_Client_ID(), "0", prod.get_ID(), get_TrxName());
							}	
						}
						
					}						
					
				}								

				if(codProd==null && amt==null) cantVacias ++; //aumento contador de filas vacias

				if(cantVacias == 10) recorrido = tope; //permito un maximo de 10 lineas vacias leidas, superado ese tope salgo del FOR

			} catch (Exception e) {
				//Errores no contemplados
				throw new AdempiereException(e.getMessage());
			}			

		}

		if (workbook!=null){
			workbook.close();
		}

		if(message.equalsIgnoreCase("")) message="Proceso Finalizado OK";

		return message;				

	}

}
