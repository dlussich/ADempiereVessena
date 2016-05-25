/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 25/06/2012
 */
 
package org.openup.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;

import org.compiere.util.Env;

/**
 * org.openup.util - Converter
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 25/06/2012
 * @see
 */
public class Converter {
	
	int counter=0;
	String value="";  

	
	public String getStringOfBigDecimal(BigDecimal amount){
		
		String value ="";
		
		int parteEntera = amount.intValue();
		
		value = this.getStringOfNumber(parteEntera);
		
		BigDecimal fraccion = amount.subtract(new BigDecimal(parteEntera)).setScale(2, RoundingMode.HALF_UP);
		
		String strFrac = "";
		if (fraccion.compareTo(Env.ZERO) != 0){
			strFrac = fraccion.toString().trim();
			int pos = strFrac.indexOf(".");
			if (pos > 0){
				strFrac = strFrac.substring(pos+1);
			}
			
			value = value + " con " + strFrac + "/100";
		}
		
		return value;
	}
	
	public String getStringOfNumber(int num){
        this.counter = num;
        return convertir(num);
    }
	
	/*
    private String convertir(int _counter){
        //Limite
        if(_counter >2000000)
            return "DOS MILLONES";
        switch(_counter){
            case 0: return "CERO";
            case 1: return "UN"; //UNO
            case 2: return "DOS";
            case 3: return "TRES";
            case 4: return "CUATRO";
            case 5: return "CINCO";
            case 6: return "SEIS";
            case 7: return "SIETE";
            case 8: return "OCHO";
            case 9: return "NUEVE";
            case 10: return "DIEZ";
            case 11: return "ONCE";
            case 12: return "DOCE";
            case 13: return "TRECE";
            case 14: return "CATORCE";
            case 15: return "QUINCE";
            case 20: return "VEINTE";
            case 30: return "TREINTA";
            case 40: return "CUARENTA";
            case 50: return "CINCUENTA";
            case 60: return "SESENTA";
            case 70: return "SETENTA";
            case 80: return "OCHENTA";
            case 90: return "NOVENTA";
            case 100: return "CIEN";
            case 200: return "DOSCIENTOS";
            case 300: return "TRESCIENTOS";
            case 400: return "CUATROCIENTOS";
            case 500: return "QUINIENTOS";
            case 600: return "SEISCIENTOS";
            case 700: return "SETECIENTOS";
            case 800: return "OCHOCIENTOS";
            case 900: return "NOVECIENTOS";
            case 1000: return "MIL";
            case 1000000: return "UN MILLON";
            case 2000000: return "DOS MILLONES";
        }
        if(_counter<20){
            //System.out.println(">15");
            return "DIECI"+ convertir(_counter-10);
        }
        if(_counter<30){
            //System.out.println(">20");
            return "VEINTI" + convertir(_counter-20);
        }
        if(_counter<100){
            //System.out.println("<100");
            return convertir( (int)(_counter/10)*10 ) + " Y " + convertir(_counter%10);
        }       
        if(_counter<200){
            //System.out.println("<200");
            return "CIENTO " + convertir( _counter - 100 );
        }        
        if(_counter<1000){
            //System.out.println("<1000");
            return convertir( (int)(_counter/100)*100 ) + " " + convertir(_counter%100);
        }
        if(_counter<2000){
            //System.out.println("<2000");
            return "MIL " + convertir( _counter % 1000 );
        }
        if(_counter<1000000){
            String var="";
            //System.out.println("<1000000");
            var = convertir((int)(_counter/1000)) + " MIL" ;
            if(_counter % 1000!=0){
                //System.out.println(var);
                var += " " + convertir(_counter % 1000);
            }
            return var;
        }
        if(_counter<2000000){
            return "UN MILLON " + convertir( _counter % 1000000 );
        }
        return "";
    }   
	*/
    
    private String convertir(int _counter){

    	if(_counter >99999999)
            return "CIEN MILLONES";
    	
    	switch(_counter){
            case 0: return "CERO";
            case 1: return "UN"; //UNO
            case 2: return "DOS";
            case 3: return "TRES";
            case 4: return "CUATRO";
            case 5: return "CINCO";
            case 6: return "SEIS";
            case 7: return "SIETE";
            case 8: return "OCHO";
            case 9: return "NUEVE";
            case 10: return "DIEZ";
            case 11: return "ONCE";
            case 12: return "DOCE";
            case 13: return "TRECE";
            case 14: return "CATORCE";
            case 15: return "QUINCE";
            case 20: return "VEINTE";
            case 30: return "TREINTA";
            case 40: return "CUARENTA";
            case 50: return "CINCUENTA";
            case 60: return "SESENTA";
            case 70: return "SETENTA";
            case 80: return "OCHENTA";
            case 90: return "NOVENTA";
            case 100: return "CIEN";
            case 200: return "DOSCIENTOS";
            case 300: return "TRESCIENTOS";
            case 400: return "CUATROCIENTOS";
            case 500: return "QUINIENTOS";
            case 600: return "SEISCIENTOS";
            case 700: return "SETECIENTOS";
            case 800: return "OCHOCIENTOS";
            case 900: return "NOVECIENTOS";
            case 1000: return "MIL";
            case 1000000: return "UN MILLON";
        }
        if(_counter<20){
            //System.out.println(">15");
            return "DIECI"+ convertir(_counter-10);
        }
        if(_counter<30){
            //System.out.println(">20");
            return "VEINTI" + convertir(_counter-20);
        }
        if(_counter<100){
            //System.out.println("<100");
            return convertir( (int)(_counter/10)*10 ) + " Y " + convertir(_counter%10);
        }       
        if(_counter<200){
            //System.out.println("<200");
            return "CIENTO " + convertir( _counter - 100 );
        }        
        if(_counter<1000){
            //System.out.println("<1000");
            return convertir( (int)(_counter/100)*100 ) + " " + convertir(_counter%100);
        }
        if(_counter<2000){
            //System.out.println("<2000");
            return "MIL " + convertir( _counter % 1000 );
        }
        if(_counter<1000000){
            String var="";
            //System.out.println("<1000000");
            var = convertir((int)(_counter/1000)) + " MIL" ;
            if(_counter % 1000!=0){
                //System.out.println(var);
                var += " " + convertir(_counter % 1000);
            }
            return var;
        }
        if(_counter<2000000){
            return "UN MILLON " + convertir( _counter % 1000000 );
        }
        if(_counter >= 2000000 && _counter<100000000){
            String var="";
            //System.out.println("<1000000");
            var = convertir((int)(_counter/1000000)) + " MILLONES" ;
            if(_counter % 1000000!=0){
                //System.out.println(var);
                var += " " + convertir(_counter % 1000000);
            }
            return var;
        }
        return "";
    }   

    public static String convertirPortuguese(BigDecimal amount){
    	
    	String value ="";
		
		int parteEntera = amount.intValue();
		
		value = convertirPortuguese(parteEntera);
		
		BigDecimal fraccion = amount.subtract(new BigDecimal(parteEntera)).setScale(2, RoundingMode.HALF_UP);
		
		String strFrac = "";
		if (fraccion.compareTo(Env.ZERO) != 0){
			strFrac = fraccion.toString().trim();
			int pos = strFrac.indexOf(".");
			if (pos > 0){
				strFrac = strFrac.substring(pos+1);
			}
			
			value = value + " com " + strFrac + "/100";
		}
		
		return value;
    }
    
    public static String convertirPortuguese(int _counter){

    	if(_counter >99999999)
            return "CEM MILHÕES";
    	
    	switch(_counter){
            case 0: return "ZERO";
            case 1: return "UM"; //UNO
            case 2: return "DOIS";
            case 3: return "TRÊS";
            case 4: return "QUATRO";
            case 5: return "CINCO";
            case 6: return "SEIS";
            case 7: return "SETE";
            case 8: return "OITO";
            case 9: return "NOVE";
            case 10: return "DEZ";
            case 11: return "ONZE";
            case 12: return "DOZE";
            case 13: return "TREZE";
            case 14: return "QUATORZE";
            case 15: return "QUINZE";
            case 20: return "VINTE";
            case 30: return "TRINTA";
            case 40: return "QUARENTA";
            case 50: return "CINQUENTA";
            case 60: return "SESSENTA";
            case 70: return "SETENTA";
            case 80: return "OITENTA";
            case 90: return "NOVENTA";
            case 100: return "CEM";
            case 200: return "DUZENTOS";
            case 300: return "TREZENTOS";
            case 400: return "QUATROCENTOS";
            case 500: return "QUINHENTOS";
            case 600: return "SEISCENTOS";
            case 700: return "SETECENTOS";
            case 800: return "OITOCENTOS";
            case 900: return "NOVECENTOS";
            case 1000: return "MIL";
            case 1000000: return "UM MILHÃO";
        }
        if(_counter<20){
            //System.out.println(">15");
            return "DEZES"+ convertirPortuguese(_counter-10);
        }
        if(_counter<30){
            //System.out.println(">20");
            return "VINTE E " + convertirPortuguese(_counter-20);
        }
        if(_counter<100){
            //System.out.println("<100");
            return convertirPortuguese( (int)(_counter/10)*10 ) + " E " + convertirPortuguese(_counter%10);
        }       
        if(_counter<200){
            //System.out.println("<200");
            return "CENTO E " + convertirPortuguese( _counter - 100 );
        }        
        if(_counter<1000){
            //System.out.println("<1000");
            return convertirPortuguese( (int)(_counter/100)*100 ) + " " + convertirPortuguese(_counter%100);
        }
        if(_counter<2000){
            //System.out.println("<2000");
            return "MIL E " + convertirPortuguese( _counter % 1000 );
        }
        if(_counter<1000000){
            String var="";
            //System.out.println("<1000000");
            var = convertirPortuguese((int)(_counter/1000)) + " MIL" ;
            if(_counter % 1000!=0){
                //System.out.println(var);
                var += " " + convertirPortuguese(_counter % 1000);
            }
            return var;
        }
        if(_counter<2000000){
            return "UM MILHÃO E " + convertirPortuguese( _counter % 1000000 );
        }
        if(_counter >= 2000000 && _counter<100000000){
            String var="";
            //System.out.println("<1000000");
            var = convertirPortuguese((int)(_counter/1000000)) + " MILHÕES" ;
            if(_counter % 1000000!=0){
                //System.out.println(var);
                var += " " + convertirPortuguese(_counter % 1000000);
            }
            return var;
        }
        return "";
    } 
    
    /**
     * 
     * OpenUp Ltda Issue#
     * @author Sylvie Bouissa 23/04/2015
     * @param dato
     * @param fch - Fecha en formato "2015-04-23"
     * @param dato - Hora en formato  HHMMss
     * @return
     */
    
	public static Timestamp convertirHHMMss(String dato,String fch){
	    Timestamp ret;
		if(dato.length()==6){
//			String hra = dato.substring(0, 2); //HH-->Incluse pos 0 y 1
//			String min = dato.substring(2,4); //MM
//			String seg = dato.substring(4,6); //ss
			
//			return (Timestamp.valueOf(fch+hra+":"+min+":"+seg));	
			return convertirYYYYMMddHHMMss(fch+dato);
		}
		
		return null;
		//ret.settimestampticket(Timestamp.valueOf(anio+"-"+mes+"-"+dia+" "+hra+":"+min+":"+seg));	    
	}
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 23/04/2015
	 * @param string
	 * @return
	 */
	public static Timestamp convertirYYYYMMddHHMMss(String fecha){
	    Timestamp ret;
		if(fecha.length()==14){			
			String anio = fecha.substring(0, 4);
			String mes = fecha.substring(4,6);
			String dia = fecha.substring(6,8);
			String hra = fecha.substring(8,10);
			String min = fecha.substring(10,12);
			String seg = fecha.substring(12,14);
			
			return (Timestamp.valueOf(anio+"-"+mes+"-"+dia+" "+hra+":"+min+":"+seg));	
		}
		
		return null;
		//ret.settimestampticket(Timestamp.valueOf(anio+"-"+mes+"-"+dia+" "+hra+":"+min+":"+seg));	    
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 24/04/2015
	 * @param string fecha en formato 24042015
	 * @return
	 */
	public static Timestamp convertirddMMYYYY(String fecha) {
		 Timestamp ret;
			if(fecha.length()==8){
				
				String dd = fecha.substring(0, 2); //dia
				String mm = fecha.substring(2,4); //Mes
				String yyyy = fecha.substring(4,8); //Año
				
				return (Timestamp.valueOf(yyyy+"-"+mm+"-"+dd+" 00:00:00"));	
			}
			
			return null;
	}
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 27/04/2015
	 * @param string ingresa en formato 27/04/2015
	 * @return
	 */
	public static Timestamp convertirddMMYYYY_YYYYMMdd(String fecha) {
		 Timestamp ret;
			if(fecha.length()==10){
				String[] fch = fecha.split("/");
				
				String dd = fch[0]; //dia
				String mm = fch[1]; //Mes
				String yyyy = fch[2]; //Año
				
				return (Timestamp.valueOf(yyyy+"-"+mm+"-"+dd+" 00:00:00"));	
			}
			
			return null;
	}
}
