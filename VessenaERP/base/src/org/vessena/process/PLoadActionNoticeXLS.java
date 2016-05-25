package org.openup.process;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import oracle.sql.DATE;
import oracle.sql.TIMESTAMP;

import org.apache.tomcat.util.buf.TimeStamp;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.eevolution.model.MHRConcept;
import org.eevolution.model.MHREmployee;
import org.eevolution.model.MHRMovement;
import org.eevolution.model.MHRProcess;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.MMarcasReloj;
import org.openup.model.MXLSIssue;
import org.python.modules.newmodule;


/**
 * 
 * OpenUp. issue #776
 * PLoadActionNoticeXLS
 * Descripcion :
 * @author Nicolas Garcia
 * Fecha : 11/07/2011
 */


public class PLoadActionNoticeXLS extends PLoadInherit{
	
	
	public PLoadActionNoticeXLS(Properties pctx, Integer ptableID,
			Integer precordID, CLogger plog, String pfileName,
			boolean pErrorRepetidos) {
		super(pctx, ptableID, precordID, plog, pfileName, pErrorRepetidos);
		// TODO Auto-generated constructor stub
	}

	public String procesar() throws Exception{
		
		utiles = new AuxWorkCellXLS(ctx, table_ID,record_ID,fileName,log);
		//Luego de esta validacion ya queda en utiles.hoja la primera hoja
		utiles.validacionXLSInicial();
		hoja=utiles.hoja;
		readXLS();
		
		return null;
	}
		
	/**
	 * 
	 * OpenUp.	issue #776
	 * Descripcion : método que lee el archivo XLS y carga los datos en la tabla
	 * @return
	 * @throws Exception
	 * @author  Nicolas Sarlabos 
	 * Fecha : 11/07/2011
	 */
	public String readXLS() throws Exception{
		// Defino datos en memoria para luego buscar ID
		HashMap<String,Integer> lc_bpartner=this.getLHashMap("c_bpartner","WHERE IsEmployee='Y'","nrotarjeta"); //OpenUp Nicolas Sarlabos 19/04/2012,se corrige sql
		HashMap<String,Integer> luy_tiposMarcas=this.getLHashMap("uy_tipomarcareloj","","value");
		
		
		String trxAux=null;
		Trx trans = null;
		
		Integer tope= hoja.getRows();
				
		Cell cell = null;
		
			//TODO ARRANCA EN FILA 3
			for (int recorrido = 2; recorrido < tope; recorrido++) {
				try {
					 trxAux=Trx.createTrxName();
					trans = Trx.get(trxAux, true);
					System.out.println(recorrido);
					//Se lee tipo de marca ubicado en columna E que es 4 ***************************				
					String marca=utiles.getStringFromCell(hoja.getCell(4, recorrido));
						if(marca==null){
							utiles.addMsg(hoja.getCell(4, recorrido), "Tipo de marca vacío");
						}
						else if(!luy_tiposMarcas.containsKey(marca)){
							utiles.addMsg(hoja.getCell(4, recorrido), "El tipo de marca no existe");
							marca=null;
						}
					
					//Se lee Nº de tarjeta ubicado en columna B que es 1 ***************************				
					String tarjeta=utiles.getStringFromCell(hoja.getCell(1, recorrido));
					if(tarjeta==null){
						utiles.addMsg(hoja.getCell(1, recorrido), "Nº de tarjeta vacío");
					}else if(!lc_bpartner.containsKey(tarjeta)){
						utiles.addMsg(hoja.getCell(1, recorrido), "El nº de tarjeta no existe o no está asociado a ningún empleado");
						tarjeta=null;
					}
										
					
                   //Se lee FECHA EVENTO !!!!! Ubicado en columna D que es 3 ***************************				
					cell=hoja.getCell(3, recorrido);//
					Timestamp serviceDate=utiles.formatDateFromString(cell.getContents());//
					if(serviceDate==null){
						//Mensaje
						utiles.addMsg(cell, "Fecha inválida");//
					}
								
					
							//si existe la fecha,nro de legajo y el concepto entonces se guarda en la tabla
							if(serviceDate!=null && tarjeta!=null && marca!=null){
					
							try{
								MMarcasReloj mReloj = new MMarcasReloj(ctx, 0, null);
								mReloj.setnrotarjeta(tarjeta);
								mReloj.setC_BPartner_ID(lc_bpartner.get(tarjeta));
								mReloj.settipomarca_id(luy_tiposMarcas.get(marca));
								mReloj.setfechahora(serviceDate);
								mReloj.setFileName(this.fileName);
								mReloj.saveEx();
							}catch (Exception e) {
								utiles.addMsg(cell, e.toString());
							}
							}

				} catch (Exception e) {
					//Errores no contemplados
					trans.rollback();
					trans.close();
									
					cell = null;
					MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);
					
				}
				
			}//Fin FOR
			if(trans!=null){
				trans.close();
				cell = null;
			}
		return "Proceso terminado correctamente";
	}
	
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :
	 * @param recorrido
	 * @param cell
	 * @param lista
	 * @param nombreRecorrida
	 * @return
	 * @throws Exception
	 * @author  Nicolas García
	 * Fecha : 11/07/2011
	 */
	/*private Integer searchID(int recorrido,Cell cell, HashMap<String,Integer> lista , String nombreRecorrida ) throws Exception{
		
		String auxNomb =utiles.getStringFromCell(cell);
		Integer salida=null;
		
		//Se pregunta si es null
		if(auxNomb==null){
			//Mensaje Siempre y cuando no sea familia que puede ser null
			if(!(nombreRecorrida.equals("Sub Familia"))){
			utiles.addMsg(cell, nombreRecorrida+" vacia");
		}
		}else{
			//Valido que el nombre de la categoria exista
			salida= lista.get(auxNomb.toUpperCase().trim());
			
			if(salida==null){
				//Mensaje categoria no encontrada
				utiles.addMsg(cell, nombreRecorrida+" no valida");
			}
	
		}
	return salida;
	}*/

	/**
	 * 
	 * OpenUp.	issue #776
	 * Descripcion : Método utilizado para obtener la lista de tipos de marcas de reloj y de socios de negocio
	 * @param nombreTabla
	 * @param where
	 * @param claveBusqueda
	 * @return
	 * @throws Exception
	 * @author  Nicolas Sarlabos
	 * Fecha : 11/07/2011
	 */
	private HashMap<String,Integer> getLHashMap(String nombreTabla,String where, String claveBusqueda)throws Exception{
		
		//Cargo datos a usar validos para nombre
		HashMap<String,Integer> salida=new HashMap<String,Integer> ();
		
		String sql = "SELECT "+nombreTabla+"_id, "+claveBusqueda+" FROM "+nombreTabla+" "+where;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

	
		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			// Si existe resultado
			//OpenUp Nicolas Sarlabos #776 29/11/2011, defensivo por si nrotarjeta es null
			while (rs.next()) {
				if(rs.getString(2)!=null){
					salida.put(rs.getString(2).toUpperCase().trim(),rs.getInt(1));
				}else salida.put("",rs.getInt(1)); 
			}
			//fin OpenUp Nicolas Sarlabos #776 29/11/2011
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
}
