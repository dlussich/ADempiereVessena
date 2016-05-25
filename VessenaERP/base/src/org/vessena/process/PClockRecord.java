package org.openup.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MMarcasReloj;
import org.openup.beans.AuxWorkCellXLS;


public class PClockRecord extends SvrProcess {
	
	private String origen = null;
	private String historico = null;
	BufferedWriter bwh;
	AuxWorkCellXLS utiles;
		
	@Override
	protected void prepare() {
		
		utiles = new AuxWorkCellXLS(getCtx(), getTable_ID(),getRecord_ID(),getName(),log);
		getFiles(); //cargo nombres de archivos a utilizar
		
			
	}

	@Override
	protected String doIt() throws Exception {
		
		return read(); //llamo a funcion de lectura de archivos
				
	}
	
	/**
	 * Metodo que obtiene la ruta y nombre de los archivos de marcas diarias y del historico
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 03/05/2012
	 * @see
	 */
	
	private void getFiles() {
		
		String sql = "", value = "";
		//consulta para el archivo de marcas diario		
		sql = "SELECT filename FROM uy_hrparametros WHERE ad_client_id=" + this.getAD_Client_ID();
		value = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		if (value != null && !value.equalsIgnoreCase("")) {
			origen = value;
		} else throw new AdempiereException ("No se especifica archivo de marcas de reloj en parametros de nomina");
		
		//consulta para el archivo de marcas historico
		sql = "SELECT filenamehistorical FROM uy_hrparametros WHERE ad_client_id=" + this.getAD_Client_ID();
		value = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		if (value != null && !value.equalsIgnoreCase("")) {
			historico = value;
		} else throw new AdempiereException ("No se especifica archivo historico de marcas de reloj en parametros de nomina");
			
		
	}
	
	/**
	 * Metodo que lee el archivo de marcas diario y carga sus lineas en la tabla de marcas y en
	 * el archivo de marcas historicas
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 03/05/2012
	 * @throws Exception 
	 * @see
	 */
	
	private String read() throws Exception {
		
		String msg = "";

		try {

			FileReader fr = new FileReader(origen);
			BufferedReader br = new BufferedReader(fr);

			if (historico != null && historico != "")	bwh = new BufferedWriter(new FileWriter(historico,true));

			String sCadena=null;
			// 0=codigo 1=fecha 2=hora 3=tipo;
			String[] datos = new String[7];


			while ((sCadena = br.readLine())!=null) {

				if(historico != null && historico != "") {

					//antes que nada llamo a funcion para guardar en txt historico la linea tal cual se obtuvo
					saveInHistorical(sCadena,bwh);

				} 

				int auxDatos=0;
				datos[auxDatos]="";
				sCadena.trim();

				for(int c =0;sCadena.length()-1>c;c++){
					//Recorro letras.
					String letra = sCadena.substring(c, c+1);
					//Si donde estoy parado es espacio
					if(letra.equals(" ")){
						//Si la letra siguiente no es " "	
						if(!(sCadena.substring(c+1, c+2)).equals(" ")){
							auxDatos+=1;
							datos[auxDatos]="";
						}
					}else if(letra.equals("-")){
						auxDatos+=1;
						datos[auxDatos]="";
					}
					else{
						datos[auxDatos]+=letra;
					}
				}	

				// LLamo a funcion para guardar en tabla 
				saveRecord(datos,sCadena);

			}//fin while


			FileWriter fw = new FileWriter(origen);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(""); //borro el contenido del archivo diario
			br.close();
			bw.close();

			if(historico != null && historico != "") bwh.close();

			msg = "Proceso Terminado OK";

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);

		}
		
		return msg; 
		
	}
	
	/**
	 * Guarda las lineas en la tabla de marcas
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 03/05/2012
	 * @see
	 * @param datos
	 * @param fila
	 * @throws Exception
	 */
	
	private String saveRecord(String[] datos,String linea) throws Exception {

		String sql ="";

		try {

			int partnerId = getPartnerID(datos[0]);

			if (partnerId <= 0) {
				
				log.info("El nº de tarjeta " + "'" + datos[0] + "'" + " no esta asociado a ningun empleado en esta empresa");
				
				//throw new AdempiereException("El nº de tarjeta " + "'" + datos[0] + "'" + " no esta asociado a ningun empleado en esta empresa");
				
			}

			else {

				//obtengo ID del tipo de marca de reloj
				sql = "SELECT uy_tipomarcareloj_id FROM uy_tipomarcareloj WHERE value=" + "'" + datos[3] + "'";
				int marcaID = DB.getSQLValueEx(get_TrxName(), sql);

				if (marcaID <=0) {
					
					log.info("El tipo de marca " + "'" + datos[3] + "'" + " no existe");
					
					//throw new AdempiereException("El tipo de marca " + "'" + datos[3] + "'" + " no existe");				
					

				}else{

					Timestamp date = utiles.formatDateFromString(datos[1]+" "+datos[2]);

					MMarcasReloj model = new MMarcasReloj(getCtx(), 0, get_TrxName());
					model.setFileName(origen);
					model.setC_BPartner_ID(partnerId);
					model.setnrotarjeta(datos[0]);
					model.setfechahora(date);
					model.settipomarca_id(marcaID);
					model.setoriginal_line(linea);
					model.saveEx();

				}

			}
			
			} catch (Exception e) {
			return e.getMessage();
		}

		return "OK";
		
	}

	/**
	 * Obtiene Id del empleado a partir de su nº de tarjeta
	 * OpenUp Ltda. Issue #986 
	 * @author Nico - 03/05/2012
	 * @see
	 * @param datos
	 * @return
	 */
	
	private int getPartnerID(String datos){
		
		int id = 0;
		
		String sql = "SELECT c_bpartner_id FROM c_bpartner WHERE nrotarjeta=" + "'" +  datos + "'";
		id = DB.getSQLValueEx(get_TrxName(), sql);
						
		return id;
					
	}
		
	/**
	 * Guarda las lineas en el TXT historico
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 03/05/2012
	 * @see
	 * @param datos
	 * @param fila
	 * @throws Exception
	 */

	private void saveInHistorical(String cadena,BufferedWriter bf) throws Exception { 
				
		try {

			if(cadena != null && bf != null){

				String ln = System.getProperty("line.separator");
				bf.append(cadena + ln);
						
			}


		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}

		
	}


	
	
	
	
	

}
