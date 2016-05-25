package org.openup.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MBGMarketType;
import org.openup.model.MFduConnectionData;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public abstract class OpenUpUtils {

	public OpenUpUtils() {		
	}
	
	/***
	 * Retorno si el sistema operativo es WIndows o no
	 * OpenUp Ltda. Issue # 
	 * @author Guillermo Brust. 19/06/2013
	 * @see
	 * @return
	 */
	public static boolean isOSWindows()
	{
		String osName = System.getProperty("os.name");
		if(osName.startsWith("Windows"))
			return true;
		else
			return false;
	}
	
	/***
	 * Obtiene y retorna primer empresa que no es la System.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 06/11/2012
	 * @see
	 * @return
	 */
	public static MClient getDefaultClient(){
		
		String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "!=0";
		
		MClient value = new Query(Env.getCtx(), I_AD_Client.Table_Name, whereClause, null)
		.first();
		
		return value;
	}
	
	/***
	 * Obtiene conexion a base sql server.
	 * OpenUp Ltda. Issue #133 
	 * @author Guillermo Brust - 15/01/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnectionToSqlServer(int connectionDataID) throws Exception {
		
		Connection retorno = null;

		String connectString = ""; 

		try {

			MFduConnectionData conn = new MFduConnectionData(Env.getCtx(), connectionDataID, null);
			
			if(conn != null){
				
				connectString = "jdbc:sqlserver://" + conn.getserver_ip() + "\\" + conn.getServer() + 
								";databaseName=" + conn.getdatabase_name() + ";user=" + conn.getuser_db() + 
								";password=" + conn.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, conn.getuser_db(), conn.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}
	
	/***
	 * Despliega avance en ventana splash
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 29/10/2012
	 * @see
	 * @param text
	 */
	public static ProcessInfo showHelp(ProcessInfo pi, String text){
		if (pi.getWaiting() != null){
			pi.getWaiting().setText(text);			
		}		
		return pi;
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 25/06/2013. ISSUE #
	 * Metodo que devuelve la fecha la cantidad de dias, o meses, o anios segun sea el parametro
	 * Calendar.DAY_OF_MONTH por ejemplo es el entero field que identifica que se modificara los dias del mes.
	 * 
	 * */
	public static Timestamp sumaTiempo(Timestamp fechaOriginal, int field, int amount) {
		
		Calendar calendario = Calendar.getInstance();
		calendario.setTimeInMillis(fechaOriginal.getTime());
		calendario.add(field, amount);
		Timestamp fechaResultante = new Timestamp(calendario.getTimeInMillis());

		return fechaResultante;
	}

	
	/***
	 * Dada una fecha inicial y un plazo de dias, obtengo fecha resultado de sumar ambos.
	 * Se considera o no los dias sabado, domingo y/o feriados segun parametros recibidos.
	 * OpenUp Ltda. Issue #1076 
	 * @author Gabriel Vila - 27/06/2013
	 * @see
	 * @param fromDate
	 * @param field
	 * @param amount
	 * @param adClientID
	 * @param includeSaturdays
	 * @param includeSundays
	 * @param includeNonBusinessDays
	 * @return
	 */
	public static Timestamp getToDate(Timestamp fromDate, int field, int amount, int adClientID,
			boolean includeSaturdays, boolean includeSundays, boolean includeNonBusinessDays){
		
		Timestamp toDate = null;
		int cont = 0;
		
		try{
			
			// Primero obtengo fecha final para dias corridos
			toDate = OpenUpUtils.sumaTiempo(fromDate, field, amount);
			
			if (!includeSaturdays) cont += OpenUpUtils.saturdayCount(fromDate, toDate);
			if (!includeSundays) cont += OpenUpUtils.sundayCount(fromDate, toDate);
			if (!includeNonBusinessDays) cont += OpenUpUtils.nonBusinessDayCount(fromDate, toDate, adClientID);
			
			if (cont > 0){
				toDate = OpenUpUtils.getToDate(toDate, field, cont, adClientID, includeSaturdays, includeSundays, includeNonBusinessDays);
			}

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
		return toDate;
	}
	
	/***
	 * Obtiene y retorna cantidad de dias entre dos fechas.
	 * OpenUp Ltda.
	 * @author Nicolas Sarlabos - 10/08/2014
	 * @see
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public static int diffDays(Date dateTo, Date dateFrom){		

		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		String fechainiciostring = df.format(dateFrom);
		
		try {
			dateFrom = df.parse(fechainiciostring);
		}
		catch (ParseException ex) {
		}

		String fechafinalstring = df.format(dateTo);
		
		try {
			dateTo = df.parse(fechafinalstring);
		}
		catch (ParseException ex) {
		}

		long fechainicialms = dateFrom.getTime();
		long fechafinalms = dateTo.getTime();
		long diferencia = fechafinalms - fechainicialms;
		double dias = Math.floor(diferencia / 86400000L);// 3600*24*1000 

		return ( (int) dias);		
		
	}
	
	/***
	 * Obtiene y retorna cantidad de sabados que hay entre dos fechas incluyendo los bordes.
	 * OpenUp Ltda. Issue #1076 
	 * @author Gabriel Vila - 27/06/2013
	 * @see
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int saturdayCount(Timestamp fromDate, Timestamp toDate){
		
		int cont = 0;
		
		//if (fromDate.getDay() == 6) cont++;
		
		while (fromDate.before(toDate)){
			fromDate = sumaTiempo(fromDate, Calendar.DAY_OF_MONTH, 1);
			if (fromDate.getDay() == 6){
				cont++;
			}
		}

		// Considero los bordes
		//if (toDate.getDay() == 6) cont++;

		return cont;
	}
	
	/***
	 * Obtiene y retorna cantidad de domingos que hay entre dos fechas incluyendo los bordes.
	 * OpenUp Ltda. Issue #1076 
	 * @author Gabriel Vila - 27/06/2013
	 * @see
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int sundayCount(Timestamp fromDate, Timestamp toDate){
		
		int cont = 0;
		
		//if (fromDate.getDay() == 0) cont++;
		
		while (fromDate.before(toDate)){
			fromDate = sumaTiempo(fromDate, Calendar.DAY_OF_MONTH, 1);
			if (fromDate.getDay() == 0){
				cont++;
			}
		}
		
		// Considero los bordes		
		//if (toDate.getDay() == 0) cont++;
		
		return cont;
	}
	
	/***
	 * Obtiene y retorna cantidad de feriados que hay entre dos fechas incluyendo los bordes.
 	 * OpenUp Ltda. Issue #1076 
	 * @author Gabriel Vila - 27/06/2013
	 * @see
	 * @param fromDate
	 * @param toDate
	 * @param adClientID
	 * @return
	 */
	public static int nonBusinessDayCount(Timestamp fromDate, Timestamp toDate, int adClientID){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
	
		int cont = 0;
		
		try{
			
			sql = " select count(*) from c_nonbusinessday " +
				  " where ad_client_id =? " +
				   " and date1 between ? and ? ";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, adClientID);			
			pstmt.setTimestamp(2, fromDate);
			pstmt.setTimestamp(3, toDate);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cont = rs.getInt(1);
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return cont;
	}
	
	/***
	 * Valida RUT de socio de negocio segun algoritmo de validacion de DGI.
	 * OpenUp Ltda. Issue #1242 
	 * @author Gabriel Vila - 22/08/2013
	 * @see
	 * @param rut
	 * @return
	 */
	public static boolean validateRUT(String rut){

		boolean value = true;
		
		try {

			// Saco ultimo digito al RUT recibido
			String digitoVerificadorRUT = rut.substring(rut.length()-1);
			String rutAux = rut.substring(0, rut.length()-1);
			
			int[] digitos = new int[rutAux.length()];
			int factor = 2, suma = 0, modulo = 0, digitoVerificador = -1;

			int total = digitos.length-1;
			
			for (int i = total; i >= 0 ; i--) {
				digitos[i] = Integer.parseInt("" + rutAux.charAt(i));
				suma = suma + (digitos[i]*factor);
				factor = factor==9 ? 2 : (factor+1); 
			}
			
			// Calculo el modulo 11 de la suma
			modulo = suma % 11;

			digitoVerificador = 11 - modulo;
			
			if(digitoVerificador == 11) digitoVerificador = 0;
			
			if(digitoVerificador == 10) digitoVerificador = 1;
			
			if (digitoVerificador != Integer.parseInt(digitoVerificadorRUT)){
				value = false;
			}
			
		} 
		catch (Exception e) {
			value = false;
		}

		return value;
	}
	
	
	/***
	 * Retorna valor numerico formateado a String segun separadores de miles y decimales recibidos.
	 * OpenUp Ltda. Issue #2550 
	 * @author Gabriel Vila - 29/07/2014
	 * @see
	 * @param value
	 * @param pattern
	 * @param decimalSeparator
	 * @param groupingSeparator
	 * @return
	 */
	public static String formatSeparators(BigDecimal value, String pattern, String decimalSeparator, String groupingSeparator) {
		
		if(value == null) value = Env.ZERO;
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator(decimalSeparator.charAt(0));
        otherSymbols.setGroupingSeparator(groupingSeparator.charAt(0));
        DecimalFormat df = new DecimalFormat(pattern, otherSymbols);
        
        return df.format(value);
        
    }
	
	
	/**Verifico si el periodo esta abierto
	 * OpenUp Ltda Issue#
	 * @author SBouissa 4/8/2015
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isOpen(int idMarket){
		MBGMarketType mercado = new MBGMarketType(Env.getCtx(), idMarket, null);
		Boolean today = false;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		if(mercado!=null){
			switch (now.getDay()) {
			case 0:
				if(mercado.isOnSunday()){
					today = true;
				}
				break;
			case 1:
				if(mercado.isOnMonday()){
					today = true;
				}
				break;
			case 2:
				if(mercado.isOnTuesday()){
					today = true;
				}
				break;
			case 3:
				if(mercado.isOnWednesday()){
					today = true;
				}
				break;
			case 4:
				if(mercado.isOnThursday()){
					today = true;
				}
				break;
			case 5:
				if(mercado.isOnFriday()){
					today = true;
				}
				break;
			case 6:
				if(mercado.isOnSaturday()){
					today = true;
				}
				break;
			default:
				today = false;
				break;
			}
			if(today){
				Timestamp desde = mercado.getstartingtime();
				Timestamp hasta = mercado.getfinishingtime();
				
				if(compareDates(desde,now)){
					if(compareDates(now,hasta)){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else return false;
		}else return false;
		
	}
	
	/**Retorna true si uno es menor o igual a dos
	 * OpenUp Ltda Issue#
	 * @author SBouissa 4/8/2015
	 * @param one
	 * @param two
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static boolean compareDates(Timestamp one, Timestamp two){
		if(one.getHours()<two.getHours())return true;
		if(one.getHours()>two.getHours())return false;
		if(one.getHours() ==  two.getHours()){
			if(one.getMinutes()<two.getMinutes()) return true;
			if(one.getMinutes()==two.getMinutes())return true;
		}
		return false;
	}

	/**Metodo para convertir bits en Hexagesimal
	 * OpenUp Ltda Issue# 4408
	 * @author Sylvie Bouissa 06/10/2015
	 * @return
	 */

	public static String bitsToHexa(String[] hexIn) {
		//System.out.println(hexIn);
		String hexString = ""; String hex="";
		if(0!=hexIn.length){
			
			for(int j=0;j<hexIn.length;j++){

				hex = hexIn[j].trim();
				long num = Long.parseLong(hex);
				long rem;
				while(num > 0){
					rem = num % 10;
					num = num / 10;
					if(rem != 0 && rem != 1){//Es binario?
						return hexString;
					}
				}
				int i= Integer.parseInt(hex,2);
				hexString = hexString + Integer.toHexString(i); //Concateno los valores
			}
			
		}
		//System.out.println(hexString);
		return hexString.toUpperCase();
	}
	
	/**
	 * Retorna la tasa de cambio para la fecha, y las monedas ingresadas.. 
	 * Convertir de currency1(142) a currency2 (100) (pesos a dolares)
	 * @author OpenUp SBT Issue#  16/2/2016 10:09:58
	 * @param date
	 * @param cCurrency1
	 * @param cCurrency2
	 * @param adClientID
	 * @param adOrgID
	 * @return
	 */
	public static BigDecimal getCurrencyRateForCur1Cur2(Timestamp date,int cCurrency1,int cCurrency2,int adClientID, int adOrgID){
		Timestamp fecha = date;
		BigDecimal dividerate = MConversionRate.getDivideRate(cCurrency1, cCurrency2, fecha, 0, adClientID,adOrgID);
		if (dividerate == null) dividerate = Env.ZERO;
		return dividerate;
	}
	
	
	/***
	 * 
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 16/9/2015
	 * @param fecha - 20160301
	 * @return
	 */
	public static Timestamp converTimestampYYYYMMdd(String fecha){
		 Timestamp ret;
			if(fecha.length()==8){
				String yyyy = fecha.substring(0,4); //A�o 1234 56 78
				String mm = fecha.substring(4,6); //Mes
				String dd = fecha.substring(6,8); //dia
				
				return (Timestamp.valueOf(yyyy+"-"+mm+"-"+dd+" 00:00:00"));	
			}
			
			return null;
	}

	/**
	 * Retorna la moneda local
	 * @author OpenUp SBT Issue#  7/4/2016 14:49:42
	 * @param ctx
	 * @param mClientID
	 * @param trx
	 * @return
	 */
	public static int getSchemaCurrencyID(Properties ctx,int mClientID,String trx) {
		MClient client = new MClient(ctx, mClientID, null);
		MAcctSchema schema = client.getAcctSchema();
		return schema.getC_Currency_ID();
	}
}
