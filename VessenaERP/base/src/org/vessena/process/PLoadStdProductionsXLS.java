package org.openup.process;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

import jxl.Cell;

import jxl.Sheet;
import jxl.Workbook;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.MEstandarProducciones;
import org.openup.model.MXLSIssue;

public class PLoadStdProductionsXLS {
	
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
	
	public PLoadStdProductionsXLS (Properties pctx,Integer ptable_ID,Integer precord_ID,CLogger plog,String pfileName, boolean pErrorRepetidos){
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
		HashMap<String,Integer> ls_resource=this.getLClaves("s_resource","",false);
		HashMap<String,Integer> lm_product=this.getLClaves("m_product","",false);
					
		//instancio clase auxiliar
		utiles = new AuxWorkCellXLS(ctx, table_ID,record_ID,fileName, hoja,log);
		
		Cell cell = null;
		tope = hoja.getRows();
		MEstandarProducciones std=null;
		String trxAux=null;
		Trx trans = null;
			
		
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
						//Valido que el codigo de producto exista y no se haya hecho un registro en Estandar Producciones
						if(isValid(codigo)){//TODO, tuupper, trim
							if(isRepeated(codigo)){
								if(errorRepetidos){
									utiles.addMsg(cell, "Ya existe un registro para éste producto");
								}
								codigo=null;
								
							}
						
						}else{
								//Mensaje codigo no existente
								utiles.addMsg(cell, "Código de producto no existe");
								codigo=null;
						
						
					}//---------------------------------------------------------------
																		
					//Se lee LINEA Ubicado en columna b que es 1***************************
					Integer lineaProd=this.searchID(recorrido, hoja.getCell(1, recorrido), ls_resource, "Linea de Producción");
					//---------------------------------------------------------------
					
					//Se lee UNIDADES X HORA en columna c que es 2***************************
					Integer unHora=utiles.getIntFromCell(hoja.getCell(2, recorrido));
					if(unHora==null){
						unHora=0;
					}else if(unHora<0){
						utiles.addMsg(hoja.getCell(2, recorrido), "No se acepta valor negativo");
						unHora=null;
					}//-----------------------------------------------------------------------------
				
			
					//Se lee OPERARIOS DIRECTOS en columna d que es 3***************************
					Integer opDir=utiles.getIntFromCell(hoja.getCell(3, recorrido));
					if(opDir==null){
						opDir=0;
					}else if(opDir<0){
						utiles.addMsg(hoja.getCell(3, recorrido), "No se acepta valor negativo");
						opDir=null;
					}//-----------------------------------------------------------------------------
					
					//Se lee OPERARIOS INDIRECTOS en columna e que es 4***************************
					Integer opInd=utiles.getIntFromCell(hoja.getCell(4, recorrido));
					if(opInd==null){
						opInd=0;
					}else if(opInd<0){
						utiles.addMsg(hoja.getCell(4, recorrido), "No se acepta valor negativo");
						opInd=null;
					}//-----------------------------------------------------------------------------
					
					//Se lee TIEMPO SETUP en columna f que es 5***************************
					Integer setup=utiles.getIntFromCell(hoja.getCell(5, recorrido));
					if(setup==null){
						setup=0;
					}else if(setup<0){
						utiles.addMsg(hoja.getCell(5, recorrido), "No se acepta valor negativo");
						setup=null;
					}//-----------------------------------------------------------------------------
					
					//Se lee TIEMPO PARADAS en columna g que es 6***************************
					Integer paradas=utiles.getIntFromCell(hoja.getCell(6, recorrido));
					if(paradas==null){
						paradas=0;
					}else if(paradas<0){
						utiles.addMsg(hoja.getCell(6, recorrido), "No se acepta valor negativo");
						paradas=null;
					}//-----------------------------------------------------------------------------
					
					//Se lee ESTADO Ubicado en columna h que es 7***************************
					
					String estadoString=utiles.getStringFromCell(hoja.getCell(7, recorrido));
					boolean estado;
					//Se diferencian entre los posibles estados.
					if (estadoString==null){
						utiles.addMsg(hoja.getCell(7, recorrido), "Estado vacío.Por defecto se deja Inactivo");
						estado =false;
					}else{
						if(estadoString.toUpperCase().trim().equals("N")){
							estado =false;
						}else if(estadoString.toUpperCase().trim().equals("Y")){
							estado =true;
						}else{
							utiles.addMsg(hoja.getCell(7, recorrido), "Estado Incorrecto.Por defecto se deja Inactivo");
							estado =false;
						}
					}//-----------------------------------------------------------------------------
									
					
					if (codigo!=null){

						trxAux=Trx.createTrxName();
						trans = Trx.get(trxAux, true);
						
						Integer prodId=this.searchID(recorrido, hoja.getCell(0, recorrido), lm_product, "Producto");
						
						std=new MEstandarProducciones(ctx, 0, null);
						std.setM_Product_ID(prodId);
						if(lineaProd!=null) std.setS_Resource_ID(lineaProd);
						else std.setS_Resource_ID(1000026);
						std.setuy_unidades_hora(unHora);
						std.setUY_Cantidad_Operarios_Dir(opDir);
						std.setUY_Cantidad_Operarios_Ind(opInd);
						std.setuy_tiempo_setup(setup);
						std.setuy_tiempo_paradas(paradas);
						std.setIsActive(estado);
							
						std.saveEx(trxAux);
											
					}
				}
					
					
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
					
			// Se vacia la lista de errores
			// MXLSIssue.Delete(ctx, table_ID,record_ID,null);

			if (hoja.getColumns() < 1) {
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}

			if (hoja.getRows() < 1) {
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}
		}catch (Exception e) {
			MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","Error al abrir planilla (TRY) "+e.toString(),null);
			return ("Error al abrir planilla (TRY)");
		}
			
		return"";
	}

	
	//Se valida que exista el codigo de producto
	private boolean isValid(String codigo)throws Exception{
		boolean s = false;

		String sql = "SELECT m_product_id FROM m_product WHERE value='"+codigo+"'";
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
	
	//Se valida que no exista un registro para ese producto
	private boolean isRepeated(String codigo)throws Exception{
		boolean s = false;

		String sql = "SELECT m_product_id FROM m_product WHERE value='"+codigo+"'";
		int id = DB.getSQLValue(null, sql);
		
		String sql2 = "SELECT m_product_id FROM uy_estandar_producciones WHERE m_product_id=" + id;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

	
		try {
			pstmt = DB.prepareStatement(sql2, null);
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
		if(auxNomb!=null){
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
