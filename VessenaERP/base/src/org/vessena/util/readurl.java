package org.openup.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.compiere.util.TimeUtil;

public class readurl {

	public readurl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 08/10/2012
	 * @see
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			Timestamp hoy = new Timestamp(Calendar.getInstance().getTimeInMillis());
			Timestamp fechaTC = TimeUtil.trunc(TimeUtil.addDays(hoy, -1), TimeUtil.TRUNC_DAY);

			String fechaURL = new SimpleDateFormat("ddMMyy").format(fechaTC);

			URL url = new URL("http://www.bcu.gub.uy/Cotizaciones/oicot" + fechaURL + ".txt");

			boolean loadOK = true;
			BufferedReader in = null;
			
			try{
				 in = new BufferedReader(new InputStreamReader(url.openStream()));	
			}
			catch (Exception ex){
				// No encontro archivo o problema al leer
				loadOK = false;
			}
		    
			if (loadOK){
			    String str;
			    while ((str = in.readLine()) != null) {
			    	if (str.contains(" 2230 ")){   // Codigo de cotizacion de moneda a considerar
			    		// Busco signo de pesos
			    		int posSimbolo = str.lastIndexOf("$");
			    		String tc = (str.substring(posSimbolo + 1).trim().replace(".","")).replace(",",".");
			    		BigDecimal importeTC = new BigDecimal(tc);
			    		System.out.println("ese");
			        }
			    }
			    in.close();				
			}
			
			if (!loadOK){
				
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}

}
