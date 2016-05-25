package org.openup.process;



import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

import jxl.Cell;

import jxl.Sheet;
import jxl.Workbook;



import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.MUOMConversion;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.MXLSIssue;

//org.openup.process.PLoadProductsXLS
public class PLoadProductsXLS {
	Sheet hoja=null;
	String fileName = null;
	
	Workbook workbook=null;
	Integer tope =0;
	int linesOK=0;
	int linesMal=0;
	AuxWorkCellXLS utiles;
	Properties ctx;
	Integer table_ID;
	Integer record_ID;	
	CLogger	log ;
	boolean errorRepetidos;
	
	public PLoadProductsXLS (Properties pctx,Integer ptable_ID,Integer precord_ID,CLogger plog,String pfileName, boolean pErrorRepetidos){
		log=plog ;
		record_ID=precord_ID;
		table_ID =ptable_ID;
		fileName=pfileName;
		this.ctx =pctx;
		errorRepetidos=pErrorRepetidos;
	}
	
	
	public String procesar() throws Exception {
		
		
		// Validacion inicial Planilla
		String s=validacionXLSInicial();
		if(!(s.equals(""))){
			return s;
		}
		
		return this.readXLS();
	
	}
	
	
	private String readXLS() throws Exception {
		
		//Carga de datos a usar en memoria
		HashMap<String,Integer> luy_linea_negocio=this.getLClaves("uy_linea_negocio","",false);
		HashMap<String,Integer> luy_familia=this.getLClaves("uy_familia","",false);
		HashMap<String,Integer> luy_subfamilia=this.getLClaves("uy_subfamilia","",false);
		HashMap<String,Integer> lm_product_category=this.getLClaves("m_product_category","",false);
		HashMap<String,Integer> luy_empaque=this.getLClaves("uy_empaque","",false);
		HashMap<String,Integer> luy_tipo_empaque=this.getLClaves("uy_tipo_empaque","",false);
		HashMap<String,Integer> lunifafVenta=this.getLClaves("c_uom","",true);
		HashMap<String,Integer> lc_taxcategory=this.getLClaves("c_taxcategory", "",true);
		HashMap<String,Integer> luy_subcategoria_producto=this.getLClaves("uy_subcategoria_producto", "",true);
		HashMap<String,Integer> lm_locator=this.getLClaves("m_locator", "",false);
		HashMap<String,Integer> luy_grupos_producto=this.getLClaves("uy_grupos_producto", "",true);
		
		//instancio clase auxiliar
		utiles = new AuxWorkCellXLS(ctx, table_ID,record_ID,fileName, hoja,log);
		
		Cell cell = null;
		tope = hoja.getRows();
		MProduct product=null;
		Integer listaPreciosGeneral = this.searchListVersion();
		String trxAux=null;
		Trx trans = null;
			
		if(listaPreciosGeneral==null){
			MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(), "", "","No hay version de lista de precios General",null);
		}else{
			//TODO ARRANCA EN FILA 3
			for (int recorrido = 2; recorrido < tope; recorrido++) {
				try {
					
					//Se lee Value Ubicado en columna a que es 0***************************
					cell=hoja.getCell(0, recorrido);
					String codigo=utiles.getStringFromCell(cell);
					
					//Se pregunta si es null
					if(codigo==null){
						//Mensaje
						utiles.addMsg(cell, "Codigo producto vacio");
					}else{
						//Valido que el codigo no exista.
						if(isRepeated(codigo)){//TODO, tuupper, trim
							//Mensaje codigo repetido
							if(errorRepetidos){
								utiles.addMsg(cell, "Codigo ya fue usado para otro producto");
							}
							codigo=null;
						}
					}//---------------------------------------------------------------
					
					
					//Se lee Nombre Ubicado en columna b que es 1***************************
					cell=hoja.getCell(1, recorrido);
					String name=utiles.getStringFromCell(cell);
					
					//Se pregunta si es null
					if(name==null){
						//Mensaje
						utiles.addMsg(cell, "Nombre vacio");
					}//-----------------------------------------------------------------------------
					
					//Se lee Version Ubicado en columna c que es 2***************************
					cell=hoja.getCell(2, recorrido);
					String version=utiles.getStringFromCell(cell);
													
					//Se lee LINEA NEGOCIO Ubicado en columna d que es 3***************************
					Integer lineaNegocio=this.searchID(recorrido, hoja.getCell(3, recorrido), luy_linea_negocio, "Linea de Negocio");
					//---------------------------------------------------------------
					
					//Se lee FAMILIA Ubicado en columna e que es 4***************************				
					Integer familia=this.searchID(recorrido, hoja.getCell(4, recorrido), luy_familia, "Familia");
					//---------------------------------------------------------------
					
					//Se lee SUB FAMILIA Ubicado en columna f que es 5***************************
					//para controlar si fue puesto dato en planilla, controlar que sea valido
					//boolean marcaSubFamilia=true;
					//-1 para preguntar al crear el producto si se tiene que cargar familia o no
					//Integer subFamili=-1;
					//if (hoja.getCell(5, recorrido).getContents() != ""){
						 Integer subFamilia=this.searchID(recorrido, hoja.getCell(5, recorrido), luy_subfamilia, "Sub Familia");
						 if(familia==null) utiles.addMsg(hoja.getCell(5, recorrido), "No se ingresó Familia.SubFamilia NO ingresada.");
						//marcaSubFamilia=!(subFamili==null);
					//}
					
					//---------------------------------------------------------------
					
					//Se lee CATEGORIA Ubicado en columna g que es 6***************************
					Integer categoria=this.searchID(recorrido, hoja.getCell(6, recorrido), lm_product_category, "Categoria");
					//---------------------------------------------------------------
					
	
					/*//Se lee TIPO PRODUCTO Ubicado en columna H que es 7***************************
					Integer tipoProducto=this.searchID(recorrido, hoja.getCell(7, recorrido), luy_tipoproducto, "Tipo Producto");
					//---------------------------------------------------------------*/
					
					//Se lee UBICACION Ubicado en columna h que es 7***************************
					Integer ubicacion=this.searchID(recorrido, hoja.getCell(7, recorrido), lm_locator, "Ubicación");
					//---------------------------------------------------------------
					
					//Se lee EMPAQUE PRIMARIO Ubicado en columna i que es 8***************************
					Integer empaquePrimario=this.searchID(recorrido, hoja.getCell(8, recorrido), luy_empaque, "Envase Primario");
					//---------------------------------------------------------------
					
					//Se lee EMPAQUE SECUNDARIO Ubicado en columna j que es 9***************************
					Integer empaqueSecundario=this.searchID(recorrido, hoja.getCell(9, recorrido), luy_tipo_empaque, "Envase Secundario");
					//---------------------------------------------------------------
										
					//Se lee UNIDAD DE MEDIDA Ubicado en columna k que es 10***************************
					Integer unidadMedida=this.searchID(recorrido, hoja.getCell(10, recorrido), lunifafVenta, "Unidad de medida");
					//---------------------------------------------------------------
												
					//Se lee UNIDAD DE VENTA Ubicado en columna l que es 11***************************
					Integer unidadVenta=this.searchID(recorrido, hoja.getCell(11, recorrido), lunifafVenta, "Unidad de venta");
					//---------------------------------------------------------------
					
					//Se lee unidadConversion Ubicado en columna m que es 12***************************
					BigDecimal  unidadConversion=utiles.getBigDecimalFromCell(hoja.getCell(12, recorrido));
					if(unidadConversion==null){
						utiles.addMsg(hoja.getCell(12, recorrido), "Unidad Conversora invalida");
					}else if(unidadConversion.compareTo(BigDecimal.ZERO)<0){
						utiles.addMsg(hoja.getCell(12, recorrido), "No se aceptan Unidad Conversora negativa");
						unidadConversion=null;
					}//-----------------------------------------------------------------------------
					
					//Se lee UPC Ubicado en columna n que es 13***************************
					cell=hoja.getCell(13, recorrido);
					String upc=utiles.getStringFromCell(cell);
					if(upc==null){
						upc="";
					}
					//-----------------------------------------------------------------------------
					
					//Se lee ESTADO Ubicado en columna o que es 14***************************
					
					String estadoString=utiles.getStringFromCell(hoja.getCell(14, recorrido));
					boolean estado;
					//Se diferencian entre los posibles estados.
					if (estadoString==null){
						utiles.addMsg(hoja.getCell(14, recorrido), "Estado vacío.Por defecto se deja Inactivo");
						estado =false;
					}else{
						if(estadoString.toUpperCase().trim().equals("N")){
							estado =false;
						}else if(estadoString.toUpperCase().trim().equals("Y")){
							estado =true;
						}else{
							utiles.addMsg(hoja.getCell(14, recorrido), "Estado Incorrecto.Por defecto se deja Inactivo");
							estado =false;
						}
					}//-----------------------------------------------------------------------------
					
					//Se lee PRECIO EN LISTA Ubicado en columna p que es 15***************************
					BigDecimal  precioEnLista=utiles.getBigDecimalFromCell(hoja.getCell(15, recorrido));
					if(precioEnLista!=null && precioEnLista.compareTo(BigDecimal.ZERO)<0){
							precioEnLista=null;
					}//-----------------------------------------------------------------------------
					
					//Se lee CATEGORIA IMPUESTO Ubicado en columna q que es 16***************************
					Integer tipoImpuesto=this.searchID(recorrido, hoja.getCell(16, recorrido), lc_taxcategory , "Impuesto no valido revise tildes");
					//---------------------------------------------------------------	
					
					//Se lee UNIDAD DE ALMACENAMIENTO Ubicado en columna r que es 17***************************
					Integer unidadAlmacena=this.searchID(recorrido, hoja.getCell(17, recorrido), lunifafVenta, "Unidad de almacenamiento");
					//---------------------------------------------------------------
					
					//Se lee FACTOR en columna s que es 18***************************
					cell=hoja.getCell(18, recorrido);
					BigDecimal factor=utiles.getBigDecimalFromCell(cell);
					
					//Se lee UNIDADES X EMPAQUE en columna t que es 19***************************
					cell=hoja.getCell(19, recorrido);
					Integer empaque=utiles.getIntFromCell(cell);
										
					//Se lee SUBCATEGORIA PRODUCTO Ubicado en columna u que es 20***************************
					Integer subCat=this.searchID(recorrido, hoja.getCell(20, recorrido), luy_subcategoria_producto, "Subategoría de producto");
					//---------------------------------------------------------------
													
					//Se lee GRUPOS DE PRODUCTO Ubicado en columna v que es 21***************************
					Integer grupos=this.searchID(recorrido, hoja.getCell(21, recorrido), luy_grupos_producto, "Grupos de producto");
					//---------------------------------------------------------------
											
					//Se lee ALMACENADO Ubicado en columna w que es 22***************************
					String almacenado=utiles.getStringFromCell(hoja.getCell(22, recorrido));
					boolean alm = true;
					//Se diferencian entre los posibles estados.
					if (almacenado==null){
						utiles.addMsg(hoja.getCell(22, recorrido), "Almacenado vacío.Por defecto se deja Almacenado=Y");
						alm=true;
					}else{
						if(almacenado.toUpperCase().trim().equals("N")){
							alm=false;
						}else if(almacenado.toUpperCase().trim().equals("Y")){
							alm=true;
						}
					}//-----------------------------------------------------------------------------
					
					//Se lee COMPRADO Ubicado en columna x que es 23***************************
					String comprado=utiles.getStringFromCell(hoja.getCell(23, recorrido));
					boolean comp = true;
					//Se diferencian entre los posibles estados.
					if (comprado==null){
						utiles.addMsg(hoja.getCell(23, recorrido), "Comprado vacío.Por defecto se deja Comprado=Y");
						comp=true;
					}else{
						if(comprado.toUpperCase().trim().equals("N")){
							comp=false;
						}else if(comprado.toUpperCase().trim().equals("Y")){
							comp=true;
						}
					}//-----------------------------------------------------------------------------
					
					//Se lee VENDIDO Ubicado en columna y que es 24***************************
					String vendido=utiles.getStringFromCell(hoja.getCell(24, recorrido));
					boolean vend = true;
					//Se diferencian entre los posibles estados.
					if (vendido==null){
						utiles.addMsg(hoja.getCell(24, recorrido), "Vendido vacío.Por defecto se deja Vendido=Y");
						vend=true;
					}else{
						if(vendido.toUpperCase().trim().equals("N")){
							vend=false;
						}else if(vendido.toUpperCase().trim().equals("Y")){
							vend=true;
						}
					}//-----------------------------------------------------------------------------
					
					//Se lee SUSPENDIDO Ubicado en columna z que es 25***************************
					String suspendido=utiles.getStringFromCell(hoja.getCell(25, recorrido));
					boolean sus = true;
					//Se diferencian entre los posibles estados.
					if (suspendido==null){
						utiles.addMsg(hoja.getCell(25, recorrido), "Suspendido vacío.Por defecto se deja suspendido=N");
						sus=false;
					}else{
						if(suspendido.toUpperCase().trim().equals("N")){
							sus=false;
						}else if(suspendido.toUpperCase().trim().equals("Y")){
							sus=true;
						}
					}//-----------------------------------------------------------------------------
					
				
					// Valido si tengo los datos correctos
					if (codigo ==null ||name==null ||lineaNegocio==null || categoria ==null || empaque==null || 
							ubicacion==null || unidadVenta==null || unidadMedida==null ||  
							tipoImpuesto==null || unidadConversion==null){
						
					}else{

						 trxAux=Trx.createTrxName();
						 trans = Trx.get(trxAux, true);
						
						product=new MProduct(ctx, 0, null);
						product.setValue(codigo);
						product.setName(name);
						product.setUY_Linea_Negocio_ID(lineaNegocio);
						product.setM_Product_Category_ID(categoria );
						product.setUnitsPerPack(empaque);
						product.setM_Locator_ID(ubicacion);
						product.setC_TaxCategory_ID(tipoImpuesto);
						product.setC_UOM_To_ID(unidadVenta);
						product.setIsActive(estado);
						product.setIsStocked(alm);
						product.setIsPurchased(comp);
						product.setIsSold(vend);
						product.setDiscontinued(sus);
						
						if(familia!=null) product.setUY_Familia_ID(familia );
						if(version!=null) product.setVersionNo(version);
						if(empaquePrimario!=null) product.setUY_Empaque_ID(empaquePrimario);
						if(empaqueSecundario!=null) product.setUY_Tipo_Empaque_ID(empaqueSecundario);
						//if(unidadAlmacena!=null) product.setC_UOM_ALM_ID(unidadAlmacena);
						//if(factor!=null) product.setUY_Factor_Alm(factor);
						if(subCat!=null) product.setUY_SubCategoria_Producto_ID(subCat);
						if(grupos!=null) product.setUY_Grupos_Producto_ID(grupos);
						if(upc!=null) product.setUPC(upc);		
						
						//subfamilia
						if(familia!=null && subFamilia!=null)
						if(subFamilia.compareTo(0)>0){
							product.setUY_SubFamilia_ID(subFamilia);
						} 
												
						//Quemados
						product.setProductType("I");
						if(unidadMedida>0){
							product.setC_UOM_ID(unidadMedida);
						}else product.setC_UOM_ID(100);
						
						product.setDescription(name+" ( "+codigo+" ) ");
						//product.setM_Locator_ID(defaultWarehouseID );//masivo
						product.saveEx(trxAux);
												
						//Se agrega Conversion
						MUOMConversion s = new MUOMConversion(product);
						//Unidad de medida estander
						s.setC_UOM_ID(100);
						// unidad a la que se convierte
						s.setC_UOM_To_ID(unidadVenta );
						
						s.setDivideRate(unidadConversion );
						s.setMultiplyRate(Env.ONE.divide(unidadConversion, 6,RoundingMode.HALF_UP ));//TODO 
												
						//OpenUp. Nicolas Sarlabos. 25/04/2013. #773. 
						if(s.getDivideRate().compareTo(s.getMultiplyRate())!=0){
							s.saveEx(trxAux);
						}
						//Fin OpenUp.
						
						//se agrega el producto a la lista de precio estandar.
						if(precioEnLista!=null && precioEnLista.compareTo(Env.ZERO)>0) MProductPrice.add(product.getM_Product_ID(), listaPreciosGeneral, precioEnLista , ctx,trxAux );
						
						
					}//fin Validacion
			
					
				} catch (Exception e) {
					//Errores no contemplados
					trans.rollback();
					trans.close();
					MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);
					
				}
				if(trans!=null){
					trans.close();
				}
			}//Fin for
		}
		
		if (workbook!=null){
			workbook.close();
		}
		return "Proceso Terminado OK";
	}

	private HashMap<String,Integer> getLClaves(String nombre,String where, boolean buscarPorName)throws Exception{
		String clave="value";
		//Por defecto se busca por value, pero podes buscar por nombre
		if(buscarPorName){
			clave="name";
		}
		//Cargo datos a usar validos para nombre
		HashMap<String,Integer> salida=new HashMap<String,Integer> ();
		
		String sql = "SELECT "+nombre+"_id, "+clave+" FROM "+nombre+where;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

	
		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			// Si existe resultado
			while (rs.next()) {
				salida.put(rs.getString(clave).toUpperCase().trim(),rs.getInt(nombre+"_id"));
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return salida;
		
	}

	private String validacionXLSInicial(){
		
		
		if ((fileName == null) || (fileName.equals(""))) {
			MXLSIssue.Add(ctx, table_ID, record_ID, fileName,
					"", "", "", "El nombre de la planilla excel esta vacio",
					null);
			return ("El nombre de la planilla excel esta vacio");
		}
		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) {
			MXLSIssue.Add(ctx, table_ID, record_ID, fileName,
					"", "", "", "No se encontro la planilla Excel", null);
			return ("No se encontro la planilla Excel");
		}


		try {
			
			// Get de workbook
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la primer hoja
			hoja = workbook.getSheet(0);
			
			//http://code.google.com/p/shapan/source/browse/trunk/sg_market/src/report/JxlTest.java?r=257
			
			// Se vacia la lista de errores
			// MXLSIssue.Delete(ctx, table_ID,record_ID,null);

			if (hoja.getColumns() < 1) {
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}

			if (hoja.getRows() < 1) {
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","La primer hoja de la planilla Excel no tiene filas",null);
				return ("La primer hoja de la planilla Excel no tiene filas");
			}
		}catch (Exception e) {
			MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","Error al abrir planilla (TRY) "+e.toString(),null);
			return ("Error al abrir planilla (TRY)");
		}
			
		return"";
	}

	private Integer   searchListVersion() throws Exception{
		// Busco la version de lista de precios GENERAL mas nueva pero que no pase la fecha de la carga de los datos
		Integer s=null;
		
		String sql = "select m_pricelist_version_id from m_pricelist_version where m_pricelist_id=1000002 AND validfrom<=now() order by validfrom desc";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

	
		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			// Si existe resultado
			if (rs.next()) {
				//La primera es la que cumple
				s=rs.getInt("m_pricelist_version_id");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return s;
	}
	
	private boolean isRepeated(String codigo)throws Exception{
		boolean s = false;

		String sql = "SELECT * FROM m_product WHERE value='"+codigo+"'";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

	
		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			// Si existe resultado
			if (rs.next()) {
				s=true;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return s;
	}
	
	private Integer searchID(int recorrido,Cell cell, HashMap<String,Integer> lista , String nombreRecorrida ) throws Exception{
		
		String auxNomb =utiles.getStringFromCell(cell);
		Integer salida=null;
		
		//Se pregunta si es null
		if(auxNomb==null){
			//Mensaje Siempre y cuando no sea familia que puede ser null
			if(!(nombreRecorrida.equals("Sub Familia"))){
			utiles.addMsg(cell, nombreRecorrida+" vacia");
		}
		}else{
			//Valido que el nombre exista
			salida= lista.get(auxNomb.toUpperCase().trim());
			
			if(salida==null){
				//Mensaje categoria no encontrada
				utiles.addMsg(cell, nombreRecorrida+" no valida");
			}
	
		}
	return salida;
	}
}
