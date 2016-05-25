/**
 * 
 */
package org.openup.process;

import java.io.File;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.MTax;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.MFamilia;
import org.openup.model.MLineaNegocio;
import org.openup.model.MProdAttribute;
import org.openup.model.MProductGroup;
import org.openup.model.MProductUpc;
import org.openup.model.MSubFamilia;

/**
 * @author Nicolas
 *
 */
public class PRTLoadProduct extends SvrProcess {
	
	String fileName = null, type = null, loadUPC = null;
	Sheet hoja=null;
		
	Workbook workbook=null;
	Integer tope =0;
	AuxWorkCellXLS utiles;

	/**
	 * 
	 */
	public PRTLoadProduct() {
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
				if (name.equalsIgnoreCase("TypeLoad")){
					this.type = ((String)para[i].getParameter());
				}
			}
			//OpenUp. Nicolas Sarlabos. 07/04/2016. #5732.
			if (name!= null){
				if (name.equalsIgnoreCase("IsLoadUpc")){
					this.loadUPC = ((String)para[i].getParameter());
				}
			}
			//Fin OpenUp.
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

		if(this.type.equalsIgnoreCase("PRODUCTOS")){
			
			this.readXLSProd();
			
		} else this.readXLSUpc();		

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
	
	private String readXLSProd() throws Exception {

		String message = "";
		Cell cell = null;
		utiles = new AuxWorkCellXLS(getCtx(), getTable_ID(), getRecord_ID(),null, hoja,null);
		tope = hoja.getRows();
		MProduct prod = null;
		int cantVacias = 0;
		

		for (int recorrido = 3; recorrido < tope; recorrido++) {

			prod = null;

			try{

				//Se lee Codigo de Producto en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				String codProd=utiles.getStringFromCell(cell);
				if(codProd!=null && !codProd.equalsIgnoreCase("")){
					
					codProd = codProd.trim();
					
					MProduct aux = MProduct.forValue(getCtx(), codProd, get_TrxName());
					
					if(aux!=null && aux.get_ID()>0) codProd = null; //ya existe producto con el mismo codigo
					
				} else codProd = null;
				//-----------------------------------------------------------------------------

				//Se lee Nombre en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				String name=utiles.getStringFromCell(cell);
				if(name!=null && !name.equalsIgnoreCase("")){
					name = name.trim();					
				} else name = null;				
				//------------------------------------------------------------------------------
				
				//Se lee Seccion en columna C que es 2***************************
				cell = (hoja.getCell(2, recorrido));
				String seccion=utiles.getStringFromCell(cell);
				int seccionID = 0;
				if(seccion!=null && !seccion.equalsIgnoreCase("")){
					
					seccion = seccion.trim();
					
					MLineaNegocio linea = MLineaNegocio.forValue(getCtx(), seccion, get_TrxName());
					
					if(linea!=null && linea.get_ID()>0) seccionID = linea.get_ID();					
					
				} else seccion = null;				
				//------------------------------------------------------------------------------
				
				//Se lee Rubro en columna D que es 3***************************
				cell = (hoja.getCell(3, recorrido));
				String rubro=utiles.getStringFromCell(cell);
				int rubroID = 0;
				if(rubro!=null && !rubro.equalsIgnoreCase("")){
					
					rubro = rubro.trim();
					
					MProductGroup group = MProductGroup.forValue(getCtx(), rubro, get_TrxName());
					
					if(group!=null && group.get_ID()>0) rubroID = group.get_ID();					
					
				} else rubro = null;				
				//------------------------------------------------------------------------------
				
				//Se lee Familia en columna E que es 4***************************
				cell = (hoja.getCell(4, recorrido));
				String familia=utiles.getStringFromCell(cell);
				int familiaID = 0;
				if(familia!=null && !familia.equalsIgnoreCase("")){
					
					familia = familia.trim();
					
					MFamilia family = MFamilia.forValue(getCtx(), familia, get_TrxName());
					
					if(family!=null && family.get_ID()>0) familiaID = family.get_ID();					
					
				} else familia = null;				
				//------------------------------------------------------------------------------
				
				//Se lee SubFamilia en columna F que es 5***************************
				cell = (hoja.getCell(5, recorrido));
				String subfamilia=utiles.getStringFromCell(cell);
				int subfamiliaID = 0;
				if(subfamilia!=null && !subfamilia.equalsIgnoreCase("")){
					
					subfamilia = subfamilia.trim();
					
					MSubFamilia subFamily = MSubFamilia.forValue(getCtx(), subfamilia, get_TrxName());
					
					if(subFamily!=null && subFamily.get_ID()>0) subfamiliaID = subFamily.get_ID();					
					
				} else subfamilia = null;				
				//------------------------------------------------------------------------------
				
				//Se lee Impuesto en columna G que es 6***************************
				cell = (hoja.getCell(6, recorrido));
				String impuesto=utiles.getStringFromCell(cell);
				int taxCatID = 0;
				if(impuesto!=null && !impuesto.equalsIgnoreCase("")){
					
					impuesto = impuesto.trim();
					
					MTax tax = MTax.forValue(getCtx(), impuesto, get_TrxName());
					
					if(tax!=null && tax.get_ID()>0) taxCatID = tax.getC_TaxCategory_ID();					
					
				}			
				//------------------------------------------------------------------------------
				
				//Se lee Attr1 en columna H que es 7***************************
				cell = (hoja.getCell(7, recorrido));
				String attr1=utiles.getStringFromCell(cell);
				boolean atr1 = false;
				if(attr1!=null && !attr1.equalsIgnoreCase("")){
					
					attr1 = attr1.trim();
					
					if(attr1.toUpperCase().equalsIgnoreCase("S")) atr1 = true;						
					
				}			
				//------------------------------------------------------------------------------
				
				//Se lee Attr2 en columna I que es 8***************************
				cell = (hoja.getCell(8, recorrido));
				String attr2=utiles.getStringFromCell(cell);
				boolean atr2 = false;
				if(attr2!=null && !attr2.equalsIgnoreCase("")){
					
					attr2 = attr2.trim();
					
					if(attr2.toUpperCase().equalsIgnoreCase("S")) atr2 = true;						
					
				}			
				//------------------------------------------------------------------------------
				
				//Se lee Attr11 en columna J que es 9***************************
				cell = (hoja.getCell(9, recorrido));
				String attr11=utiles.getStringFromCell(cell);
				boolean atr11 = false;
				if(attr11!=null && !attr11.equalsIgnoreCase("")){
					
					attr11 = attr11.trim();
					
					if(attr11.toUpperCase().equalsIgnoreCase("S")) atr11 = true;						
					
				}			
				//------------------------------------------------------------------------------
				
				//Se lee Attr13 en columna K que es 10***************************
				cell = (hoja.getCell(10, recorrido));
				String attr13=utiles.getStringFromCell(cell);
				boolean atr13 = false;
				if(attr13!=null && !attr13.equalsIgnoreCase("")){
					
					attr13 = attr13.trim();
					
					if(attr13.toUpperCase().equalsIgnoreCase("S")) atr13 = true;						
					
				}			
				//------------------------------------------------------------------------------
				
				//Se lee Attr14 en columna L que es 11***************************
				cell = (hoja.getCell(11, recorrido));
				String attr14=utiles.getStringFromCell(cell);
				boolean atr14 = false;
				if(attr14!=null && !attr14.equalsIgnoreCase("")){
					
					attr14 = attr14.trim();
					
					if(attr14.toUpperCase().equalsIgnoreCase("S")) atr14 = true;						
					
				}			
				//------------------------------------------------------------------------------
				
				//Se lee Attr32 en columna M que es 12***************************
				cell = (hoja.getCell(12, recorrido));
				String attr32=utiles.getStringFromCell(cell);
				boolean atr32 = false;
				if(attr32!=null && !attr32.equalsIgnoreCase("")){
					
					attr32 = attr32.trim();
					
					if(attr32.toUpperCase().equalsIgnoreCase("S")) atr32 = true;						
					
				}			
				//------------------------------------------------------------------------------	
				//------------------------------------------------------------------------------------------------------------------

				if(codProd!=null && name!=null && taxCatID>0){

					prod = new MProduct(getCtx(), 0, null);
					prod.setValue(codProd);
					prod.setName(name);
					prod.setDescription(name);
					prod.setC_UOM_ID(100);
					prod.setC_TaxCategory_ID(taxCatID);

					//seteo los 4 atributos principales realizando validaciones
					if(subfamiliaID > 0) {

						//obtengo subfamilia para los atributos indicados
						if(seccionID > 0 && rubroID > 0 && familiaID > 0){

							MSubFamilia sub = MSubFamilia.forAttributes(getCtx(), seccionID, rubroID, familiaID, get_TrxName());

							if(sub!=null && sub.get_ID() > 0){

								//la subfamilia indicada esta OK, cargo los 4 atributos
								if(sub.get_ID() == subfamiliaID){

									prod.setUY_Linea_Negocio_ID(seccionID);
									prod.set_ValueOfColumn("UY_ProductGroup_ID", rubroID);
									prod.setUY_Familia_ID(familiaID);
									prod.setUY_SubFamilia_ID(subfamiliaID);	
									
								}
								
							}

						}							
						
					} else if (familiaID > 0){
						
						//obtengo familia para los atributos indicados
						if(seccionID > 0 && rubroID > 0){
							
							MFamilia fam = MFamilia.forAttributes(getCtx(), seccionID, rubroID, get_TrxName());
							
							if(fam!=null && fam.get_ID() > 0){

								//la familia indicada esta OK, cargo los 3 atributos
								if(fam.get_ID() == familiaID){

									prod.setUY_Linea_Negocio_ID(seccionID);
									prod.set_ValueOfColumn("UY_ProductGroup_ID", rubroID);
									prod.setUY_Familia_ID(familiaID);
																		
								}
								
							}						
							
						}					
						
					} else if (rubroID > 0){
						
						//obtengo rubro para los atributos indicados
						if(seccionID > 0){
							
							MProductGroup group = MProductGroup.forSection(getCtx(), seccionID, get_TrxName());
							
							if(group!=null && group.get_ID() > 0){
								
								if(group.get_ID() == rubroID){
									
									prod.setUY_Linea_Negocio_ID(seccionID);
									prod.set_ValueOfColumn("UY_ProductGroup_ID", rubroID);								
									
								}							
								
							}			
							
						}						
						
					} else if (seccionID > 0) prod.setUY_Linea_Negocio_ID(seccionID);					
					
					prod.saveEx(get_TrxName());	//guardo el producto				
					
					//actualizo valores de atributos
					MProdAttribute atr = null;
					
					if(atr1){
						
						atr = MProdAttribute.forValue(getCtx(), "attr_1", get_TrxName());
						
						if(atr!=null && atr.get_ID()>0){
							
							DB.executeUpdateEx("update m_productattribute set isselected = 'Y' where m_product_id = " + prod.get_ID() + 
									" and uy_prodattribute_id = " + atr.get_ID(), get_TrxName());						
							
						}						
					}
					
					if(atr2){
						
						atr = MProdAttribute.forValue(getCtx(), "attr_2", get_TrxName());
						
						if(atr!=null && atr.get_ID()>0){
							
							DB.executeUpdateEx("update m_productattribute set isselected = 'Y' where m_product_id = " + prod.get_ID() + 
									" and uy_prodattribute_id = " + atr.get_ID(), get_TrxName());						
							
						}						
					}
					
					if(atr11){
						
						atr = MProdAttribute.forValue(getCtx(), "attr_11", get_TrxName());
						
						if(atr!=null && atr.get_ID()>0){
							
							DB.executeUpdateEx("update m_productattribute set isselected = 'Y' where m_product_id = " + prod.get_ID() + 
									" and uy_prodattribute_id = " + atr.get_ID(), get_TrxName());						
							
						}						
					}
					
					if(atr13){
						
						atr = MProdAttribute.forValue(getCtx(), "attr_13", get_TrxName());
						
						if(atr!=null && atr.get_ID()>0){
							
							DB.executeUpdateEx("update m_productattribute set isselected = 'Y' where m_product_id = " + prod.get_ID() + 
									" and uy_prodattribute_id = " + atr.get_ID(), get_TrxName());						
							
						}						
					}
					
					if(atr14){
						
						atr = MProdAttribute.forValue(getCtx(), "attr_14", get_TrxName());
						
						if(atr!=null && atr.get_ID()>0){
							
							DB.executeUpdateEx("update m_productattribute set isselected = 'Y' where m_product_id = " + prod.get_ID() + 
									" and uy_prodattribute_id = " + atr.get_ID(), get_TrxName());						
							
						}						
					}
					
					if(atr32){
						
						atr = MProdAttribute.forValue(getCtx(), "attr_32", get_TrxName());
						
						if(atr!=null && atr.get_ID()>0){
							
							DB.executeUpdateEx("update m_productattribute set isselected = 'Y' where m_product_id = " + prod.get_ID() + 
									" and uy_prodattribute_id = " + atr.get_ID(), get_TrxName());						
							
						}						
					}
					
					//OpenUp. Nicolas Sarlabos. 07/04/2016. #5732. Se genera codigo de barras si asi se indico.
					if(this.loadUPC.equalsIgnoreCase("Y")){

						String upc = codProd.trim();

						//obtengo modelo para UPC
						MProductUpc prodUpc = MProductUpc.forUPC(getCtx(), upc, get_TrxName());

						if(prodUpc==null){
							
							MProductUpc prodUPC = new MProductUpc(getCtx(),0,get_TrxName());

							prodUPC.setM_Product_ID(prod.get_ID());
							prodUPC.setUPC(codProd);							
							prodUPC.saveEx();
							
						}		
						
					}					
					//Fin OpenUp.
					
					//OpenUp SBouissa 13-10-2015 Issue# ??(Se agrega el cálculo de hexa de los atributos del producto)
					if(0<prod.get_ID() && prod.isSold()){
						prod.updateVerssionNo(true);//Se calcula hexa y se guardan los cambios (true)
					}
				}				

				if(codProd==null && name==null && taxCatID==0) cantVacias ++; //aumento contador de filas vacias

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
	
	private String readXLSUpc() throws Exception {
		
		String message = "";
		Cell cell = null;
		utiles = new AuxWorkCellXLS(getCtx(), getTable_ID(), getRecord_ID(),null, hoja,null);
		tope = hoja.getRows();
		MProduct prod = null;
		int cantVacias = 0;
		
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

				//Se lee UPC en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				String upc=utiles.getStringFromCell(cell);
				if(upc!=null && !upc.equalsIgnoreCase("")){
					
					upc = upc.trim();
					
					//obtengo modelo para UPC
					MProductUpc prodUpc = MProductUpc.forUPC(getCtx(), upc, get_TrxName());
					
					if(prodUpc!=null) upc = null; //el upc ya existe en el sistema				
					
				} else upc = null;				
				//------------------------------------------------------------------------------				
				//------------------------------------------------------------------------------------------------------------------

				if(prodID > 0 && upc!=null){
					
					MProductUpc prodUPC = new MProductUpc(getCtx(),0,get_TrxName());
					
					prodUPC.setM_Product_ID(prodID);
					prodUPC.setUPC(upc);
					
					prodUPC.saveEx();				
					
				}					

				if(codProd==null && upc==null) cantVacias ++; //aumento contador de filas vacias

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
