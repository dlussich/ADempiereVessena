package org.openup.cfe;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class CfeUtils {

	public CfeUtils() {
		
	}
	
	public enum CfeType {
		eTicket,
		eTicket_NC,
		eTicket_ND,
		eFactura,
		eFactura_NC,
		eFactura_ND,
		eRemito,
		//eResguardo
	}
	

	/***
	 * Constantes de CFE con sus correspondientes codigos esperado por DGI.
	 * A medida de que se agregen mas tipos de CFE/CFC, se agregaran aca
	 * OpenUp Ltda. Issue #4094 
	 * @author Raul Capecce - 22/05/2015
	 * @see
	 */
	public static BiMap<String, CfeType> getCfeTypes() {

		BiMap<String, CfeType> ret = HashBiMap.create();
		ret.put("101", CfeType.eTicket);      // e-Ticket
		ret.put("102", CfeType.eTicket_NC);   // Nota de Credito e-Ticket
		ret.put("103", CfeType.eTicket_ND);   // Nota de Debito e-Ticket
		ret.put("111", CfeType.eFactura);     // e-Factura
		ret.put("112", CfeType.eFactura_NC);  // Nota de Credito e-Factura
		ret.put("113", CfeType.eFactura_ND);  // Nota de Debito e-Factura
		ret.put("181", CfeType.eRemito);      // e-Remito
		//ret.put("182", CfeType.eResguardo);   // e-Resguardo
		
		return ret;
	}
	
	public static String md5Encrypt(String input) {
        //input = "clave_de_acceso" + "<CFE>...</CFE>";
        String md5 = "";
        java.security.MessageDigest md;
        try {
            md = java.security.MessageDigest.getInstance("MD5");
            java.math.BigInteger hash = new java.math.BigInteger(1, md.digest(input.getBytes("utf-8")));
            md5 = hash.toString(16);
            while (md5.length() < 32) {
                md5 = '0' + md5;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }


	/***
	 * Convierte fechas del modelo (timestamp) a fechas en formato XMLGregorianCalendar
	 * OpenUp Ltda. Issue #4094 
	 * @author Raul Capecce - 22/05/2015
	 * @see
	 * @param timestamp
	 * @param withTime
	 */
	public static XMLGregorianCalendar Timestamp_to_XmlGregorianCalendar_OnlyDate(Timestamp timestamp, boolean withTime) {
		try {
			GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
			cal.setTime(timestamp);
			XMLGregorianCalendar xgcal;
			if (!withTime){
				xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
			} else {
				xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED );
				xgcal.setHour(cal.get(Calendar.HOUR_OF_DAY));
				xgcal.setMinute(cal.get(Calendar.MINUTE));
				xgcal.setSecond(cal.get(Calendar.SECOND));
				xgcal.setMillisecond(cal.get(Calendar.MILLISECOND));
				xgcal.setTimezone(-3*60); // GTM -3 en minutos
				
			}
			return xgcal;
		} catch (DatatypeConfigurationException e) {
			throw new AdempiereException(e);
		}
	}
	
}
