package org.openup.process;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;

import java.util.Properties;

import jxl.write.Label;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


import org.compiere.util.CLogger;
import org.compiere.util.Env;

import org.openup.beans.AuxWorkCellXLS;

/**
 * 
 * OpenUp.   issue #776
 * PLoadClockTXTtoXLS
 * Descripcion :
 * @author Nicolas Garcia
 * Fecha : 11/07/2011
 */
public class PLoadClockTXTtoXLS extends PLoadInherit{

	WritableWorkbook  libro;
	String destino;
	WritableSheet hoja;
	
	public PLoadClockTXTtoXLS(Properties pctx, Integer ptableID,
			Integer precordID, CLogger plog, String pfileName,
			boolean pErrorRepetidos) {
		super(pctx, ptableID, precordID, plog, pfileName, pErrorRepetidos);
		destino="C:\\Reloj\\DatosReloj.xls";//TODO esta quemado
	}

	public String procesar() throws Exception {
		
		return readTXT();
	}
	
	/**
	 * 
	 * OpenUp.	issue #776
	 * Descripcion : método que lee el archivo TXT y lo guarda como XLS
	 * @return
	 * @throws Exception
	 * @author  Nicolas Garcia
	 * Fecha : 11/07/2011
	 */
	
	public String readTXT() throws Exception{
		
		FileReader fr = new FileReader(fileName);
		
		BufferedReader bf = new BufferedReader(fr);
		
		String sCadena=null;
		// 0=codigo 1=fecha 2=hora 3=tipo;
		String[] datos = new String[7];
		prepareBook();
		int fila=2;
		
		
		while ((sCadena = bf.readLine())!=null) {
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
			
			// LLamo a funcion Guardar en excel
			saveInXLS8(datos,fila);
			fila+=1;
		}//fin while
		libro.write();
		libro.close();
		Env.startBrowser(destino);
		return "Proceso Terminado OK";
	}
	
	/**
	 * 
	 * OpenUp.	issue #776
	 * Descripcion : guarda los registros en el archivo XLS
	 * @param datos
	 * @param fila
	 * @throws Exception
	 * @author  Nicolas Garcia 
	 * Fecha : 11/07/2011
	 */
	private void saveInXLS8(String[] datos,int fila) throws Exception{
		
			// Codigo
			hoja.addCell(new Label(1, fila, datos[0]));
			// Fecha Hora
			hoja.addCell(new Label(3, fila, datos[1]+" "+datos[2]));
			// Código Concepto
			hoja.addCell(new Label(4, fila, datos[3]));
			//Descripcion Concepto
			hoja.addCell(new Label(5, fila, datos[4]));
			hoja.addCell(new Label(6, fila, "Datos Recabados Reloj"));
						
	}
	
	
	/**
	 * 
	 * OpenUp.	issue #776
	 * Descripcion : prepara Excel
	 * @throws Exception
	 * @author  Nicolas Garcia 
	 * Fecha : 11/07/2011
	 */
	
	private void prepareBook() throws Exception{
		
			libro =AuxWorkCellXLS.getWriteWorkbook(new File(destino));
			
			libro.createSheet("Inicio", 0);
			hoja = libro.getSheet(0);
			hoja.addCell(new Label(0, 1, "Proceso Nomina"));
			hoja.addCell(new Label(1, 1, "Nro Tarjeta Empleado"));
			hoja.addCell(new Label(2, 1, "Nombre Empleado"));
			hoja.addCell(new Label(3, 1, "Fecha-Hora"));
			hoja.addCell(new Label(4, 1, "Concepto Codigo"));
			hoja.addCell(new Label(5, 1, "Concepto Descripcion"));
			hoja.addCell(new Label(6, 1, "Descripcion"));
	}
	
}
