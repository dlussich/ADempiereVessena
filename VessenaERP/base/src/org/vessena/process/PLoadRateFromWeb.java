/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 14/07/2014
 */
package org.openup.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MConversionType;
import org.compiere.model.MCurrency;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.model.MLoadRate;
import org.openup.model.MLoadRateLine;

/**
 * org.openup.process - PLoadRateFromWeb
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 14/07/2014
 * @see
 */
public class PLoadRateFromWeb extends SvrProcess {

	private Timestamp fechaProceso = null;

	
	/**
	 * Constructor.
	 */
	public PLoadRateFromWeb() {
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
			//MClient client = this.getDefaultClient();
			MClient client = this.getRateClient();
			MAcctSchema schema = client.getAcctSchema();
			int cCurrencyIDTo = 142;
			if (schema != null)
				cCurrencyIDTo = schema.getC_Currency_ID();
			
			if (this.fechaProceso == null){
				this.fechaProceso = new Timestamp(Calendar.getInstance().getTimeInMillis());
			}
			
			Timestamp fechaTC = TimeUtil.trunc(TimeUtil.addDays(this.fechaProceso, 1), TimeUtil.TRUNC_DAY);
			
			// Obtengo monedas a considerar segun Parametrizaion de carga de TC
			List<MLoadRateLine> lines = MLoadRate.getLines(getCtx(), null);

			for (MLoadRateLine line: lines){

				boolean isUnidadIndexada = false;
				if (((MCurrency)line.getC_Currency()).getISO_Code().equalsIgnoreCase("UNI")){
					isUnidadIndexada = true;
				}
				
				BigDecimal importeTC = Env.ZERO;
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
					
					if (!isUnidadIndexada){

						newRate = new MConversionRate(getCtx(), 0, get_TrxName());
						newRate.setC_Currency_ID(line.getC_Currency_ID());
						newRate.setC_Currency_ID_To(cCurrencyIDTo);
						newRate.setC_ConversionType_ID(MConversionType.TYPE_SPOT);
						newRate.setIsManual(true);
						newRate.setValidFrom(fechaTC);
						newRate.setValidTo(fechaTC);
						newRate.setAD_Org_ID(0);
						newRate.setAD_Client_ID(client.getAD_Client_ID());
						
						String str;
					    boolean fin = false;
					    while ( ((str = in.readLine()) != null) && (!fin) ){
					    	if (str.contains(" " + line.getCode().trim() + " ")){   // Codigo de cotizacion de moneda a considerar
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
					    
						// Si no pude obtener el tipo de cambio de hoy 
						if (importeTC.compareTo(Env.ZERO) == 0){
							// Uso el ultimo tipo de cambio ingresado
							MConversionRate rate = MConversionRate.getLastRate(getCtx(), line.getC_Currency_ID(), cCurrencyIDTo, 
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

					    
					}
					else{
						// La unidad indexada tiene una particularidad distinta a las otra monedas.
						// Hay mas de una fecha valor en la pagina del BCU, por lo cual tengo mas de una cotizacion.
						String str;
					    while ((str = in.readLine()) != null){
					    	if (str.contains(" " + line.getCode().trim() + " ")){   // Codigo de cotizacion de moneda a considerar
					    		// Busco signo de pesos
					    		int posSimbolo = str.lastIndexOf("$");
					    		String tc = (str.substring(posSimbolo + 1).trim().replace(".","")).replace(",",".");
					    		String fecValor = tc; 
					    		
					    		// Busco espacio en blanco 
					    		int posSeparator = tc.indexOf(" ");
					    		if (posSeparator > 1){
					    			tc = tc.substring(0,posSeparator);
					    		}
					    		importeTC = new BigDecimal(tc);
					    		
					    		// Obtengo fecha valor
					    		fecValor = fecValor.substring(posSeparator);
					    		fecValor = fecValor.replace(" Valor ", "");
					    		String[] parts = fecValor.split("/");
					    		
					    		String dia = parts[0].trim();
					    		if (dia.length() == 1) dia = "0" + dia;
					    		
					    		fecValor = "20" + parts[2] + "-" + parts[1] + "-" + dia + " 00:00:00"; 
					    		Timestamp tsFecValor = Timestamp.valueOf(fecValor);
					    		
					    		// Verifico que ya no tenga rate cargado en la base para esta fecha valor
					    		BigDecimal rateAux = MConversionRate.getRate(line.getC_Currency_ID(), cCurrencyIDTo, tsFecValor, 0, client.getAD_Client_ID(), 0);
					    		if (rateAux == null){
									newRate = new MConversionRate(getCtx(), 0, get_TrxName());
									newRate.setC_Currency_ID(line.getC_Currency_ID());
									newRate.setC_Currency_ID_To(cCurrencyIDTo);
									newRate.setC_ConversionType_ID(MConversionType.TYPE_SPOT);
									newRate.setIsManual(true);
									newRate.setValidFrom(tsFecValor);
									newRate.setValidTo(tsFecValor);
									newRate.setAD_Org_ID(0);
									newRate.setAD_Client_ID(client.getAD_Client_ID());
									newRate.setMultiplyRate(importeTC);
									newRate.saveEx();
					    		}
					        }
					    }
					    in.close();
					}
				}
				else{

					if (!isUnidadIndexada){
						// Uso el ultimo tipo de cambio ingresado
						MConversionRate rate = MConversionRate.getLastRate(getCtx(), line.getC_Currency_ID(), cCurrencyIDTo, 
											   client.getAD_Client_ID(), 0, MConversionType.TYPE_SPOT, this.fechaProceso, get_TrxName());
						if (rate != null){
							newRate = new MConversionRate(getCtx(), 0, get_TrxName());
							newRate.setC_Currency_ID(line.getC_Currency_ID());
							newRate.setC_Currency_ID_To(cCurrencyIDTo);
							newRate.setC_ConversionType_ID(MConversionType.TYPE_SPOT);
							newRate.setIsManual(true);
							newRate.setValidFrom(fechaTC);
							newRate.setValidTo(fechaTC);
							newRate.setAD_Org_ID(0);
							newRate.setAD_Client_ID(client.getAD_Client_ID());
							newRate.setMultiplyRate(rate.getMultiplyRate());
							newRate.saveEx();
						}
					}

				}

			} // for

			return "OK : " + client.getName();
			
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
	@SuppressWarnings("unused")
	private MClient getDefaultClient(){
		
		//String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "!=0";
		String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "=1000006";
		
		MClient value = new Query(getCtx(), I_AD_Client.Table_Name, whereClause, null)
		.first();
		
		return value;
	}

	/***
	 * Obtiene y retorna compañia a procesar.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 16/02/2015
	 * @see
	 * @return
	 */
	private MClient getRateClient(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		MClient client = null;
		
		try{
			sql = " select min(ad_client_id_aux) from uy_loadrateclient ";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			if (rs.next()){
				client = new MClient(getCtx(), rs.getInt(1), null);
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
		
		return client;

	}
	
}
