package org.openup.process;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class UtilReportes {
	
	/* Obtengo identificador unico para reporte considerando usuario */
	public static String getReportID(Long userID){

		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    
	    return userID + "_" + sdf.format(cal.getTime());
	}

	/* Obtengo identificador unico para procesos de Generacion */
	public static String getGenerateID(int idUser){
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    return idUser + "_" + sdf.format(cal.getTime());
	}
	
}
