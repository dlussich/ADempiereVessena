/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 28/02/2013
 */
package org.openup.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.compiere.model.I_AD_Client;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MConversionType;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * org.openup.process - PSetCurrencyRateFromWeb
 * OpenUp Ltda. Issue #189 
 * Description: Carga automatica de tasa de cambio desde web.
 * @author Gabriel Vila - 28/02/2013
 * @see
 */
public class PSetCurrencyRateFromWeb extends SvrProcess {

	private int cCurrencyFromID = 100;
	private String rateWebCode = "";
	private Timestamp fechaProceso = null;

	
	/**
	 * Constructor.
	 */
	public PSetCurrencyRateFromWeb() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 28/02/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("DateTrx")){
					this.fechaProceso = (Timestamp)para[i].getParameter();
				}
				if (name.equalsIgnoreCase("C_Currency_ID")){
					this.cCurrencyFromID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("RateWebCode")){
					this.rateWebCode = para[i].getParameter().toString().trim();
				}
			}
		}

		
	}


	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 28/02/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		MConversionRate newRate = null;
		
		try {

			// Moneda del esquema contable
			MClient client = this.getDefaultClient();
			MAcctSchema schema = client.getAcctSchema();
			int cCurrencyIDTo = 142;
			if (schema != null)
				cCurrencyIDTo = schema.getC_Currency_ID();
			
			BigDecimal importeTC = Env.ZERO;
			
			if (this.fechaProceso == null){
				this.fechaProceso = new Timestamp(Calendar.getInstance().getTimeInMillis());
			}
			
			Timestamp fechaTC = TimeUtil.trunc(TimeUtil.addDays(this.fechaProceso, 1), TimeUtil.TRUNC_DAY);
			
			newRate = new MConversionRate(getCtx(), 0, get_TrxName());
			newRate.setC_Currency_ID(this.cCurrencyFromID);
			newRate.setC_Currency_ID_To(cCurrencyIDTo);
			newRate.setC_ConversionType_ID(MConversionType.TYPE_SPOT);
			newRate.setIsManual(true);
			newRate.setValidFrom(fechaTC);
			newRate.setValidTo(fechaTC);
			newRate.setAD_Org_ID(0);
			newRate.setAD_Client_ID(client.getAD_Client_ID());
			
			String fechaURL = new SimpleDateFormat("ddMMyy").format(this.fechaProceso);
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
			    boolean fin = false;
			    while ( ((str = in.readLine()) != null) && (!fin) ){
			    	if (str.contains(" " + this.rateWebCode + " ")){   // Codigo de cotizacion de moneda a considerar
			    		// Busco signo de pesos
			    		int posSimbolo = str.lastIndexOf("$");
			    		String tc = (str.substring(posSimbolo + 1).trim().replace(".","")).replace(",",".");
			    		
			    		// Busco espacio en blanco 
			    		int posSeparator = tc.indexOf(" ");
			    		if (posSeparator > 1){
			    			tc = tc.substring(0,posSeparator);
			    		}
			    		importeTC = new BigDecimal(tc);
			    		fin = true;
			        }
			    }
			    in.close();				
			}
			
			// Si no pude obtener el tipo de cambio de hoy 
			if (importeTC.compareTo(Env.ZERO) == 0){
				// Uso el ultimo tipo de cambio ingresado
				MConversionRate rate = MConversionRate.getLastRate(getCtx(), this.cCurrencyFromID, cCurrencyIDTo, 
									   client.getAD_Client_ID(), 0, MConversionType.TYPE_SPOT, this.fechaProceso, get_TrxName());
				if (rate != null){
					newRate.setMultiplyRate(rate.getMultiplyRate());
				}
				
			}
			else{
				newRate.setMultiplyRate(importeTC);
			}
			
			// Si tengo tasa guardo
			if (newRate.getMultiplyRate().compareTo(Env.ZERO) > 0){
				newRate.saveEx(); 
			}

			return "OK";
			
		} catch (Exception e) {
			return(e.getMessage());
		} 	
		
	}

	
	/***
	 * Obtiene y retorna primer empresa que no es la System.
	 * OpenUp Ltda. Issue #189 
	 * @author Gabriel Vila - 28/02/2013
	 * @see
	 * @return
	 */
	private MClient getDefaultClient(){
		
		String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "!=0";
		
		MClient value = new Query(getCtx(), I_AD_Client.Table_Name, whereClause, null)
		.first();
		
		return value;
	}
	
}
