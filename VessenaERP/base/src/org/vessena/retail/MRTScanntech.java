/**
 * 
 */
package org.openup.retail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.MTaxCategory;
import org.compiere.util.Env;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openup.model.MProductUpc;
import org.openup.model.MRTConfig;
import org.openup.model.MRTConfigIdOrg;
import org.apache.commons.codec.binary.Base64;
/**OpenUp Ltda Issue#
 * @author SBT 9/12/2015
 *
 */
public class MRTScanntech extends MRTRetailInterface {

	private MRTConfig rtConfig = null;
	private String url = "http://test.parceiro.scanntech.com";
	private String urlMethod = "/products.apibackend.rest.server/api/minoristas/24028";
	
	
	/**
	 * 
	 */
	public MRTScanntech(Properties ctx, String trxName) {
		rtConfig = MRTConfig.forValue(ctx,"configscanntech",trxName);
		url = rtConfig.getURL();
		urlMethod = rtConfig.geturlmetodo();
	}


	@Override
	public void insertCliente(Properties ctx, int mClientID, int mCBPartnerID,
			String trxName) {
		// TODO Auto-generated method stub

	}


	@Override
	public String insertArticulo(Properties ctx, int mClientID, int mProductID,
			String trxName) {
		String mjeError = validarConfiguracion();
		if(!mjeError.equalsIgnoreCase("OK"))return mjeError;
		
		MProduct prod = new MProduct(ctx,mProductID,trxName);
		
		if(null!= prod && 0<prod.get_ID()){
		
			int[] idEmp = MRTConfigIdOrg.getDistinctsIds(ctx, mClientID, trxName);
			for(int i : idEmp){
				JSONObject json = productToJson(prod,ctx,trxName,i);
				if(json==null) return ("Error al parcear el producto");
				
				CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				try {///products.apibackend.rest.server/api/minoristas/24028/articulos
						HttpPost request = new HttpPost(url+urlMethod+rtConfig.getmantarticulo());
					    //HttpPost request = new HttpPost("http://test.parceiro.scanntech.com/products.apibackend.rest.server/api/minoristas/24028/articulos");
					    System.out.println(json.toString());
					    StringEntity params = new StringEntity(json.toString());
					    request.addHeader("Accept","application/json");
					    request.addHeader("content-type", "application/json");
					    request.setEntity(params);
					    
					   // String auth = "test_scanntech@scanntech.com" + ":" + "scanntech";
					    String auth = rtConfig.getuserscannatech() + ":" + rtConfig.getpswscanntech();
				       	byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
				       	String authHeader = "Basic " + new String(encodedAuth);
				       	request.addHeader(HttpHeaders.AUTHORIZATION, authHeader);			    
					    	    
					    CloseableHttpResponse response = httpClient.execute(request);
						System.out.println("----------------------------------------");
				        System.out.println(response.getStatusLine());
				        System.out.println(response.getStatusLine().getStatusCode());
				        if(response.getStatusLine().getStatusCode()==200){
//				           	 BufferedReader br = new BufferedReader(new InputStreamReader(
//				 	                   (response.getEntity().getContent())));
//				 	         String output  = getResponseText(response.getEntity().getContent());
//				 	         JSONObject jsonA = new JSONObject(output);
				 	         System.out.println("RESPUESTA OK Artículo creado");
//				 	         System.out.println(jsonA.toString());
				 	         return "OK";
				        }else{
				            return response.getStatusLine().toString();	
				        }
			           
					    
					// handle response here...
					} catch (Exception ex) {
					    // handle exception here
					} finally {
					    try {
							httpClient.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
			}
			
		}
		return "No se encontro producto con ID: "+mProductID;		
		
	}
	
	private static String getResponseText(InputStream inStream) {
		if(inStream!=null){
			return new Scanner(inStream).useDelimiter("\\A").next();
		}
		return "{NoDato:nodata}";
	}

	/**14/01/2016
	 * Actualizar producto difiere en que se le debe pasar el id de artículo en la url
	 */
	@Override
	public String updateArticulo(Properties ctx, int mClientID, int mProductID,
			String trxName) {
		String mjeError = validarConfiguracion();
		if(!mjeError.equalsIgnoreCase("OK"))return mjeError;
		
		MProduct prod = new MProduct(ctx,mProductID,trxName);
		
		if(null!= prod && 0<prod.get_ID()){
			JSONObject json = productToJson(prod,ctx,trxName,2097);//OJOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOoQUEMDAO EL ID
			if(json==null) return ("Error al parcear el producto");
			
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			try {//products.apibackend.rest.server/api/minoristas/24028/articulos/{idArticulo}
					HttpPut request = new HttpPut(url+urlMethod+rtConfig.getmantarticulo()+"/"+prod.getValue());
				    System.out.println(json.toString());
				    StringEntity params = new StringEntity(json.toString());
				    request.addHeader("Accept","application/json");
				    request.addHeader("content-type", "application/json");
				    request.setEntity(params);
				    
				   // String auth = "test_scanntech@scanntech.com" + ":" + "scanntech";
				    String auth = rtConfig.getuserscannatech() + ":" + rtConfig.getpswscanntech();
			       	byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
			       	String authHeader = "Basic " + new String(encodedAuth);
			       	request.addHeader(HttpHeaders.AUTHORIZATION, authHeader);			    
				    	    
				    CloseableHttpResponse response = httpClient.execute(request);
					System.out.println("----------------------------------------");
			        System.out.println(response.getStatusLine());
			        System.out.println(response.getStatusLine().getStatusCode());
			        if(response.getStatusLine().getStatusCode()==200){
//			           	 BufferedReader br = new BufferedReader(new InputStreamReader(
//			 	                   (response.getEntity().getContent())));
//			 	         String output  = getResponseText(response.getEntity().getContent());
//			 	         JSONObject jsonA = new JSONObject(output);
			 	         System.out.println("RESPUESTA ");
//			 	         System.out.println(jsonA.toString());
			 	         return "OK";
			        }else{
			            return response.getStatusLine().toString();	
			        }
		           
				    
				// handle response here...
				} catch (Exception ex) {
				    // handle exception here
				} finally {
				    try {
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
		return "No se encontro producto con ID: "+mProductID;
		
	}


	@Override
	public String deleteArticulo(Properties ctx, int mClientID, String prodCode,
			String trxName) {
		// TODO Auto-generated method stub
		return "NO IMPLEMENTADO";

	}


	@Override
	public void updateCliente(Properties ctx, int mClientID, int mCBPartnerID,
			String trxName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteCliente(Properties ctx, int mClientID, int mCBPartnerID,
			String trxName) {
		// TODO Auto-generated method stub
		
	}

	
	/* (non-Javadoc)
	 * @see org.openup.model.MRTRetailInterface#getVersionAux()
	 */
	@Override
	public void getVersionAux() {
		CloseableHttpClient httpclient1 = HttpClients.createDefault();
		HttpGet httpget1 = new HttpGet("http://test.parceiro.scanntech.com/products.apibackend.rest.server/api/api-info/version");  
		System.out.println("Executing request " + httpget1.getURI());   
        try{
        	CloseableHttpResponse response = httpclient1.execute(httpget1);
	        try {
	        	
	            System.out.println("----------------------------------------");
	            System.out.println(response.getStatusLine());
	            BufferedReader br = new BufferedReader(new InputStreamReader(
	                    (response.getEntity().getContent())));
	            int i = br.read();
	            String output  = getResponseText(response.getEntity().getContent());
	            JSONObject json = new JSONObject(output);
              String ver = "0";
              ver = json.getString("version");
              String idSb = json.getString("artifactId");
              httpget1.abort();
              System.out.println("version="+ver);
	        }catch(Exception e){
	        	System.out.println(e.getMessage());
	        	response.close();
	        } finally {
	            response.close();
	        }
        } catch(Exception e){
        	System.out.println(e.getMessage());
        }finally {
        	try {
				httpclient1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		
		
	}

	
	private String validarConfiguracion(){
	    if(rtConfig.getuserscannatech().equals("")|| rtConfig.getpswscanntech().equals("")) 
	    	return ("Parametrizar autenticación");
	    if(rtConfig.getidentifempresa()<=0)
	    	return ("Parametrizar Identificador Empresa");
		return "OK";

	}


	@Override
	public String updateArticuloPrecio(Properties ctx, int mClientID,
			int mProductID, int cCurrID, BigDecimal price,int adOrgID, String trxName) {
		String mjeError = validarConfiguracion();
		if(!mjeError.equalsIgnoreCase("OK"))return mjeError;

		MProduct prod = new MProduct(ctx,mProductID,trxName);
		MOrg org = new MOrg(ctx,adOrgID,trxName);
		if((null!= prod && 0<prod.get_ID())&&(null!= org && 0<org.get_ID())){
			JSONObject json = productUpdatePriceToJson(prod,org.getValue(),cCurrID);
			if(json==null) return ("Error al parcear el producto");
			
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			try {///products.apibackend.rest.server/api/minoristas/24028/articulos
					HttpPut request = new HttpPut(url+urlMethod+rtConfig.getmantarticulo()+
							"/"+prod.getValue()+rtConfig.getmantartprecio());
				    //HttpPost request = new HttpPost("http://test.parceiro.scanntech.com/products.apibackend.rest.server/api/minoristas/24028/articulos");
					System.out.println("----------------ENVIO------------------------");
					System.out.println(json.toString());
				    StringEntity params = new StringEntity(json.toString());
				    request.addHeader("Accept","application/json");
				    request.addHeader("content-type", "application/json");
				    request.setEntity(params);
				    
				   // String auth = "test_scanntech@scanntech.com" + ":" + "scanntech";
				    String auth = rtConfig.getuserscannatech() + ":" + rtConfig.getpswscanntech();
			       	byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
			       	String authHeader = "Basic " + new String(encodedAuth);
			       	request.addHeader(HttpHeaders.AUTHORIZATION, authHeader);			    
				    	    
				    CloseableHttpResponse response = httpClient.execute(request);
					System.out.println("----------------------------------------");
			        System.out.println(response.getStatusLine());
			        System.out.println(response.getStatusLine().getStatusCode());
			        if(response.getStatusLine().getStatusCode()==200){
//			           	 BufferedReader br = new BufferedReader(new InputStreamReader(
//			 	                   (response.getEntity().getContent())));
//			 	         String output  = getResponseText(response.getEntity().getContent());
//			 	         JSONObject jsonA = new JSONObject(output);
			 	         System.out.println("RESPUESTA OK");
//			 	         System.out.println(jsonA.toString());
			 	         return "OK";
			        }else{
			            return response.getStatusLine().toString();	
			        }
		           
				    
				// handle response here...
				} catch (Exception ex) {
				    // handle exception here
				} finally {
				    try {
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
		return "No se encontro producto con ID: "+mProductID;		
		
	}


	
//	@Override
//	public String getMovimiento(Properties ctx, int mClientID,
//			String trxName,int idCajaIn, String nroCuponFiscal,String fchDesde, String fchHasta) {
//		String mjeError = validarConfiguracion();
//		if(!mjeError.equalsIgnoreCase("OK"))return mjeError;
//
//		if(null!= prod && 0<prod.get_ID()){
//			JSONObject json = productUpdatePriceToJson(prod);
//			if(json==null) return ("Error al parcear el producto");
//			
//			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//			try {
//              ///products.apibackend.rest.server/api/minoristas/24028/locales/1/cajas/1/movimientos/AA0001?fechaConsultaDesde=2015-12-01&fechaConsultaHasta=2015-12-05
//					HttpPut request = new HttpPut(url+urlMethod+rtConfig.getmantlocal()+
//							"/1"+rtConfig.getmantcaja()+"/"+idCajaIn+rtConfig.getmantmovimiento()+"/"+nroCuponFiscal+"?fechaConsultaDesde="+
//							fchDesde+"&fechaConsultaHasta"+fchHasta);
//				    System.out.println(json.toString());
//				    StringEntity params = new StringEntity(json.toString());
//				    request.addHeader("Accept","application/json");
//				    request.addHeader("content-type", "application/json");
//				    request.setEntity(params);
//				    
//				   // String auth = "test_scanntech@scanntech.com" + ":" + "scanntech";
//				    String auth = rtConfig.getuserscannatech() + ":" + rtConfig.getpswscanntech();
//			       	byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
//			       	String authHeader = "Basic " + new String(encodedAuth);
//			       	request.addHeader(HttpHeaders.AUTHORIZATION, authHeader);			    
//				    	    
//				    CloseableHttpResponse response = httpClient.execute(request);
//					System.out.println("----------------------------------------");
//			        System.out.println(response.getStatusLine());
//			        System.out.println(response.getStatusLine().getStatusCode());
//			        if(response.getStatusLine().getStatusCode()==200){
//			           	 BufferedReader br = new BufferedReader(new InputStreamReader(
//			 	                   (response.getEntity().getContent())));
//			 	         String output  = getResponseText(response.getEntity().getContent());
//			 	         JSONObject jsonRespuesta = new JSONObject(output);
//			 	         System.out.println("RESPUESTA ");
//			 	         System.out.println(jsonRespuesta.toString());
//			 	         guardarMovimiento(jsonRespuesta);
//			 	         return "OK";
//			        }else{
//			            return response.getStatusLine().toString();	
//			        }
//		           
//				    
//				// handle response here...
//				} catch (Exception ex) {
//				    // handle exception here
//				} finally {
//				    try {
//						httpClient.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//		}
//		return "No se encontro producto con ID: ";		
//		
//	}
	
	
	
	private void guardarMovimiento(Properties ctx,String trxName,JSONObject jsonResp) {
		
		if(null!= jsonResp){
			try{
				//MRTMovement mov = new MRTMovement(ctx,0,trxName);
				
				int numeroOF=jsonResp.getInt("numeroOperacionFiscal");
			}catch(Exception e){
				
			}
		}
	}

	/**Parsea los datos del producto a json, como se especifica en la api de scantech
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 9/12/2015
	 * @param mProductID
	 * @return
	 */
	private JSONObject productToJson(MProduct prod,Properties ctx,String trxName,int idEmpres) {
		try{
			//Se crea el objeto articulo
			JSONObject ret = new JSONObject();
			//Se crea la lista de locales (Se debe agregar en todos los locales)
			List<JSONObject > sList = new ArrayList<JSONObject >();
			
			List<MOrg> lstOrgs = MRTConfigIdOrg.getOrgsXIdEmpresa(ctx, idEmpres, trxName);
			
			//for(MOrg org : MOrg.getOfClient(prod)){ // --> Es para producción ya que en la api de testing no existen estos locales
			for(MOrg org : lstOrgs){ // --> Es para producción ya que en la api de testing no existen estos locales
				JSONObject s = new JSONObject();
				s.put("codigoLocal", org.getValue());
				sList.add(s);
			}
//			JSONObject s = new JSONObject();
//			s.put("codigoLocal", "1");
//			sList.add(s);
//			JSONObject s2 = new JSONObject();
//			s2.put("codigoLocal", "2");
//			sList.add(s2);
//			
			ret.put("locales", sList);
			MProductUpc pUpc = MProductUpc.forProduct(ctx, prod.get_ID(), trxName);
			if(pUpc!=null){
				ret.put("codigoGTIN", pUpc.getUPC());//Asumimos que solo se va a asociar un upc sino se obtiene de uy_productupc 
			}
			ret.put("codigoArticulo", prod.getValue()); 
			
			ret.put("descipcion", (!(prod.getDescription().equals("")))?prod.getDescription():prod.getName());
			ret.put("descipcionReducida",prod.getName()); //14/01/2016
			
			String unidad = "Un"; //KG o Unidad o Litro
			String tipoPeso = "U";
			int codigoPesable = 0; //---> OJO tener en cuenta que no puede ser value ya que no acepta valor como 1000311
			if(null!=prod.getC_UOM()){
				if("UNI".equalsIgnoreCase(prod.getC_UOM().getUOMSymbol())){
					unidad  = "Un";
				}else if("KG".equalsIgnoreCase(prod.getC_UOM().getUOMSymbol())){
					unidad  = "Kg";
					tipoPeso = "P";
					if(pUpc!=null){
						ret.put("codigoGTIN", pUpc.getUPC());//Asumimos que solo se va a asociar un upc sino se obtiene de uy_productupc 
						//codigoPesable = Integer.valueOf(pUpc.getUPC());
						String codigoBalanza = prod.getValue().trim();
						if(codigoBalanza.length()>6) codigoBalanza = codigoBalanza.substring(0,6);
						codigoPesable = Integer.valueOf(codigoBalanza);
					}
					//codigoPesable = Integer.valueOf(prod.getUPC());
				}else if("Ltr".equalsIgnoreCase(prod.getC_UOM().getUOMSymbol())){
					unidad  = "Litro";
				}	
			}
			ret.put("plu", codigoPesable);
			ret.put("unidad", unidad); 
			ret.put("tipoPeso", tipoPeso); 
			
			String precioIni =  prod.get_ValueAsString("PriceActual");
			BigDecimal price = (BigDecimal) prod.get_Value("PriceActual");
			String decimales = "00";
			String precio="0";
				
			//OpenUp SBouissa 19-10-2015 Issue#
			//Se consulta el precio desde la lista actual
			//Lista de precio de venta
//			MPriceList list = MPriceList.getDefault(ctx, true);				
//			MProductPrice prodPrice = MProductPrice.forVersionProduct(ctx, list.getVersionVigente(null).get_ID(), prod.get_ID(), trxName);
//			BigDecimal price = null;
//			if(null!=prodPrice){
//				price = prodPrice.getPriceList();
//			}
			if(null!=price){
				//total.setScale(2, RoundingMode.HALF_UP)
				BigDecimal result = (price.setScale(2,RoundingMode.HALF_UP));
				//BigDecimal result = d.subtract(d.setScale(0, RoundingMode.FLOOR)).movePointRight(d.scale());      
				if(null!=result){
					if(!result.equals(BigDecimal.ZERO)){
						decimales = result.toString().substring(3);
					}
				}
				precio = String.valueOf(price.intValue())+"."+decimales;
			}else{
				precio = precio +"."+decimales;
			}		
			
			//ret.put("precioVenta", (precio!=null)?String.valueOf(precio):"0.00"); 
			ret.put("precioVenta", precio);
			
			//SBT 02-03-2016 Issue #5571
			//Campos nuevos a raíz de API nueva de scanntech para Uy
			BigDecimal ivaVenta = Env.ZERO;
			if(precio!=null){
				ivaVenta = calcularIvaVenta(prod,new BigDecimal(precio));
			}
			//ret.put("ivaVenta", ivaVenta.toString()); // Valor obligatorio
			String porcentaje = "0";
			if(prod.getC_TaxCategory_ID()>0){
				porcentaje = ((MTaxCategory)prod.getC_TaxCategory()).getDefaultTax().getTaxIndicator();
				porcentaje = porcentaje.replace("%", "");	
			}
			ret.put("ivaVenta", porcentaje); // Valor obligatorio
			//ret.put("codigoEmpresa",rtConfig.getidentifempresa()); // No tiene este atrinbuto en api nueva
			ret.put("usaBalanza",prod.get_ValueAsBoolean("UsaBalanza"));
			
			//FIN Issue #5571
			return ret;
		}catch(Exception e){
		
		}
		return null;
	}

	/**
	 * Calcular el monto de iva que corresponde al producto respecto al codigo de iva asociado
	 * @author OpenUp SBT Issue#  2/3/2016 15:35:25
	 * @param prod
	 * @return
	 */
	private BigDecimal calcularIvaVenta(MProduct prod,BigDecimal precio) {
		BigDecimal ret = Env.ZERO;
		if(null!=prod){
			if(prod.getC_TaxCategory_ID()>0){
				try{
					String porcentaje = ((MTaxCategory)prod.getC_TaxCategory()).getDefaultTax().getTaxIndicator();
					porcentaje = porcentaje.replace("%", "");
					int porc = Integer.valueOf(porcentaje);
					ret =  precio.multiply(new BigDecimal(porc)).divide(Env.ONEHUNDRED);
					ret = ret.setScale(2, RoundingMode.HALF_UP);
				}catch(Exception e){
					e.getMessage();
				}
				
			}
			
		}
		return ret;
	}


	/**
	 * Retorna objeto JSON para actualizacion de precio del producto ArticuloPrecio
	 * @author OpenUp SBT Issue#  16/12/2015 17:46:36
	 * @param prod
	 * @return
	 */
	private JSONObject productUpdatePriceToJson(MProduct prod,String adOrgIDValue,int curID) {
		try{
			JSONObject ret = new JSONObject();
			//SBt 03-03-2016 Issue #5515 PARA PONER EN PRODUCCION -->Api actual no tienen los locales que estan configurados.
//			List<JSONObject > sList = new ArrayList<JSONObject >();
//			JSONObject s = new JSONObject();
//			s.put("codigoLocal", Integer.valueOf(adOrgIDValue));
//			sList.add(s);
//			ret.put("locales", sList);
			//FIN PARA PONER EN PRODUCCION
			List<JSONObject > sList = new ArrayList<JSONObject >();
			JSONObject s = new JSONObject();
			s.put("codigoLocal", 1);
			sList.add(s);
			ret.put("locales", sList);
			//ret.put("codigoEmpresa",rtConfig.getidentifempresa());
			//ret.put("codigoArticulo", prod.getValue()); 
			BigDecimal precio = prod.getSalePrice(curID);
			String price = priceToPrint(precio);
			//ret.put("precioVenta", (precio!=null)?String.valueOf(precio):"1.0");
			ret.put("precioVenta", (precio!=null)?price:"0.00");
			ret.put("truncamiento", 0); //OJO LUEGO VERIFICAR DE DONDE OBTENGO
			ret.put("descuento", 0);//OJO LUEGO VERIFICAR DE DONDE OBTENG
			//ret.put("descuentoMax", 0.5);//OJO LUEGO VERIFICAR DE DONDE OBTENG
			return ret;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

//	private JSONObject movimientoTest(){
//		try{
//			JSONObject mov = new JSONObject();
//			mov.put("numeroOperacionFiscal",1);
//			mov.put("cuponCancelado",false);
//			mov.put("descuentoTotal",0.00);
//			mov.put("numeroSerieEcf","SW010600000000004005");
//			mov.put("numeroCuponFiscal",2959);
//			mov.put("codigoMoneda","986");
//			mov.put("redondeo",0.00);
//			mov.put("total",8.49);
//			mov.put("cantidadItems",1);		  
//			mov.put("fechaOperacion","2015-03-17T00:00:00.000-0300"); 
//				   
//			List<JSONObject > pagos = new ArrayList<JSONObject >();
//			JSONObject s = new JSONObject();
//			s.put("importe",8.49);
//			s.put("codigoTipoPago",9);
//			s.put("codigoMoneda","986");
//			pagos.add(s);
//			mov.put("pagos", pagos);
//					
//			List<JSONObject > detalle = new ArrayList<JSONObject >();
//			JSONObject s2 = new JSONObject();
//			s2.put("codigoTipoDetalle",4);
//			s2.put("tasaICMS","12");
//			s2.put("medidaVenta","Un");
//			s2.put("montoICMS","0");
//			s2.put("tipoTributoSalida","T");
//			s2.put("descuento",0.00);
//			s2.put("descripcionArticulo","Cafe Melitta Tr");		
//			s2.put("importe",8.49);
//			s2.put( "codigoArticulo","107000");
//			s2.put("importeUnitario",8.49);
//			s2.put("cantidad",1.00000);
//			s2.put( "codigoBarras","7891021006125");
//			List<JSONObject > desc = new ArrayList<JSONObject >();
//			JSONObject obj = new JSONObject();
//			desc.add(obj);
//			s2.put("descuentos", desc);
//			detalle.add(s2);
//			mov.put("detalles", detalle);
//			return mov;
//		}catch(Exception e){
//			
//		}
//		return null;
//	}
	
	private String priceToPrint(BigDecimal price){
		String decimales = "00";
		String precio="0";
		if(null!=price){
			BigDecimal d = (price);
			BigDecimal result = d.subtract(d.setScale(0, RoundingMode.FLOOR)).movePointRight(d.scale());      
			if(null!=result){
				if(!result.equals(BigDecimal.ZERO)){
					decimales = result.toString().substring(0, 2);
				}
			}
			precio = String.valueOf(price.intValue())+"."+decimales;
		}else{
			precio = precio +"."+decimales;
		}
		return precio;
	}

	@Override
	public JSONObject getMovimiento(Properties ctx, int mClientID, 
			String trxName,int idLocal, int idCaja, String nroCuponFiscal,String fchS,String fchTo) {
		
		String mjeError = validarConfiguracion();
		if(!mjeError.equalsIgnoreCase("OK"))return null;

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
          ///products.apibackend.rest.server/api/minoristas/24028/locales/1/cajas/1/movimientos/AA0001?fechaConsultaDesde=2015-12-01&fechaConsultaHasta=2015-12-05
				HttpGet request = new HttpGet(url+urlMethod+rtConfig.getmantlocal()+
							"/"+idLocal+rtConfig.getmantcaja()+"/"+idCaja+rtConfig.getmantmovimiento()+"/"+nroCuponFiscal+"?fechaConsultaDesde="+
							fchS+"&fechaConsultaHasta="
							+fchTo);
				   //"2015-03-16T00:00:00.000-0300"
				    
				String auth = rtConfig.getuserscannatech() + ":" + rtConfig.getpswscanntech();
			    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
			    String authHeader = "Basic " + new String(encodedAuth);
			    request.addHeader(HttpHeaders.AUTHORIZATION, authHeader);			    
				    	    
				CloseableHttpResponse response = httpClient.execute(request);
				System.out.println("----------------------------------------");
			    System.out.println(response.getStatusLine());
			    System.out.println(response.getStatusLine().getStatusCode());
			    if(response.getStatusLine().getStatusCode()==200){
			    	if(null==response.getEntity().getContent()){
			    		response.getEntity().getContent().toString();
			    	}else{
				       	BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				       	int i = br.read();
				       	if(-1<i){
				       		String line;
				       		StringBuilder sb = new StringBuilder();
					       	while ((line = br.readLine()) != null) {
					            sb.append(line);
					        }
					       	System.out.println(sb.toString());
					       	String output = "{"+ sb.toString()+"}";
					       	JSONObject jsonRespuesta = new JSONObject(output);
				       		/*InputStreamReader*/
				       		
//				       		String output  = getResponseText(response.getEntity().getContent());
//					 	    JSONObject jsonRespuesta = new JSONObject(output);
					 	    System.out.println("RESPUESTA ");
					 	    System.out.println(jsonRespuesta.toString());
					 	    
					 	    return jsonRespuesta;
					 	   // guardarMovimiento(jsonRespuesta);
					 	  // return "OK";
				       	}else{
				       		//return movimientoTest();
				       		return null;
				       	}
				       //	return "No hay datos para el nro de cupón";
				 	    
			    	}	 	   
			    }else{
			    	return null;
			    	//return response.getStatusLine().toString();	
			    }
		           
				    
				// handle response here...
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				    // handle exception here
				} finally {
				    try {
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		return null;
	}


	@Override
	public JSONArray getMovimientoTimestamp(Properties ctx, String trxName,
			int mProductID, int idLocal, int idCaja, String  fchTickets,int idEmpresa) {
		String mjeError = validarConfiguracion();
		if(!mjeError.equalsIgnoreCase("OK"))return null;
		System.out.println("SBT-Comienzo busqueda de movimientos");

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			///products.apibackend.rest.server/api/uy/minoristas/4539/locales/1/cajas/43/movimientos?fechaConsulta=2016-02-18T00:00:00.000-0300
			///products.apibackend.rest.server/api/minoristas/24028/locales/1/cajas/1/movimientos?fechaConsulta=2015-03-18T00:00:00.000-0300
				HttpGet request = new HttpGet(url+urlMethod+idEmpresa+rtConfig.getmantlocal()+
							"/"+idLocal+rtConfig.getmantcaja()+"/"+idCaja+rtConfig.getmantmovimiento()+"?fechaConsulta="+
							fchTickets);  //"2015-03-16T00:00:00.000-0300"
				    
				String auth = rtConfig.getuserscannatech() + ":" + rtConfig.getpswscanntech();
			    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
			    String authHeader = "Basic " + new String(encodedAuth);
			    request.addHeader(HttpHeaders.AUTHORIZATION, authHeader);			    
			   // System.out.println("SBT-Comienzo execute de movimientos");
				CloseableHttpResponse response = httpClient.execute(request);
			   // System.out.println("SBT-Fin execute de movimientos");
				System.out.println("----------------------------------------");
			    System.out.println(response.getStatusLine());
			    System.out.println(response.getStatusLine().getStatusCode());
			    if(response.getStatusLine().getStatusCode()==200){
					 System.out.println("SBT-Respuesta OK de movimientos");
			    	if(null==response.getEntity().getContent()){
			    		response.getEntity().getContent().toString();
			    	}else{
				       	BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				       	int i = br.read();
				       	if(-1<i){
				       		String line;
				       		StringBuilder sb = new StringBuilder();
					       	while ((line = br.readLine()) != null) {
					            sb.append(line);
					        }
					       //	System.out.println(sb.toString());					       	
							JSONTokener tokener = new JSONTokener("["+ sb.toString() +"]");
							JSONArray jsonRespuesta = new JSONArray( tokener );

					 	 //   System.out.println("RESPUESTA ");
					 	//    System.out.println(jsonRespuesta.toString());
					 	    return jsonRespuesta;
				       	}else{
				       		return null;
				       	}				 	    
			    	}	 	   
			    }else{
			    	return null;
			    }
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				} finally {
				    try {
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		return null;
	}


	@Override
	public String insertUPC(Properties ctx, int mClientID, String mUPC,int mProductID,
			String trxName) {
		String mjeError = validarConfiguracion();
		if(!mjeError.equalsIgnoreCase("OK"))return mjeError;
		
		MProduct prod = new MProduct(ctx,mProductID,trxName);
		
		if(null!= prod && 0<prod.get_ID()){
			JSONObject json = upcToJson(prod,mUPC);
			if(json==null) return ("Error al parcear el producto");
			
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			try {//minoristas/{idEmpresa}/articulos/{codigoArticulo}/barras
					HttpPost request = new HttpPost(url+urlMethod+rtConfig.getmantarticulo()+"/"+prod.getValue().trim()+rtConfig.getmantartbarra());
				    System.out.println(json.toString());
				    StringEntity params = new StringEntity(json.toString());
				    request.addHeader("Accept","application/json");
				    request.addHeader("content-type", "application/json");
				    request.setEntity(params);
				    
				    String auth = rtConfig.getuserscannatech() + ":" + rtConfig.getpswscanntech();
			       	byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
			       	String authHeader = "Basic " + new String(encodedAuth);
			       	request.addHeader(HttpHeaders.AUTHORIZATION, authHeader);			    
				    	    
				    CloseableHttpResponse response = httpClient.execute(request);
					System.out.println("----------------------------------------");//
			        System.out.println(response.getStatusLine());//
			        System.out.println(response.getStatusLine().getStatusCode());//
			        if(response.getStatusLine().getStatusCode()==200){
			           	 BufferedReader br = new BufferedReader(new InputStreamReader(
			 	                   (response.getEntity().getContent())));
			 	         String output  = getResponseText(response.getEntity().getContent());
			 	         JSONObject jsonA = new JSONObject(output);
			 	         System.out.println("RESPUESTA ");
			 	         System.out.println(jsonA.toString());
			 	         return "OK";
			        }else{
			            return response.getStatusLine().toString();	
			        }
		           
				    
				// handle response here...
				} catch (Exception ex) {
				    // handle exception here
				} finally {
				    try {
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
		return "No se encontro producto con ID: "+mProductID;
	}


	private JSONObject upcToJson(MProduct prod,String mUPC) {
		try{
			/*{
				"codigoEmpresa":24028,
				"codigoArticulo":"123509",
				"codigoGtin":"5000267023005"
			}*/
			JSONObject ret = new JSONObject();
			ret.put("codigoEmpresa", rtConfig.getidentifempresa());
			ret.put("codigoArticulo", prod.getValue());
			ret.put("codigoGtin", mUPC);
			
			return ret;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}


	@Override
	public String updateUPC(Properties ctx, int mClientID, String mUPC,
			int mProductID, String trxName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String deleteUPC(Properties ctx, int mClientID, String mUPC,
			int mProductID, String trxName) {
		
		String mjeError = validarConfiguracion();
		if(!mjeError.equalsIgnoreCase("OK"))return mjeError;
		
		MProduct prod = new MProduct(ctx,mProductID,trxName);
		
		if(null!= prod && 0<prod.get_ID()){
			//JSONObject json = upcToJson(prod,mUPC);
			//if(json==null) return ("Error al parcear el producto");
			
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			try {///products.apibackend.rest.server/api/minoristas/24028/articulos/123509/barras/5000267023056
					HttpDelete request = new HttpDelete(url+urlMethod+rtConfig.getmantarticulo()
							+"/"+prod.getValue().trim()+rtConfig.getmantartbarra()+"/"+mUPC);
				    //System.out.println(json.toString());
				    //StringEntity params = new StringEntity(json.toString());
				    request.addHeader("Accept","application/json");
				    request.addHeader("content-type", "application/json");
				    //request.setEntity();
				    
				    String auth = rtConfig.getuserscannatech() + ":" + rtConfig.getpswscanntech();
			       	byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
			       	String authHeader = "Basic " + new String(encodedAuth);
			       	request.addHeader(HttpHeaders.AUTHORIZATION, authHeader);			    
				    	    
				    CloseableHttpResponse response = httpClient.execute(request);
					System.out.println("----------------------------------------");//
			        System.out.println(response.getStatusLine());//
			        System.out.println(response.getStatusLine().getStatusCode());//
			        if(response.getStatusLine().getStatusCode()==200){
			           	 BufferedReader br = new BufferedReader(new InputStreamReader(
			 	                   (response.getEntity().getContent())));
			 	         String output  = getResponseText(response.getEntity().getContent());
			 	         JSONObject jsonA = new JSONObject(output);
			 	         System.out.println("RESPUESTA ");
			 	         System.out.println(jsonA.toString());
			 	         return "OK";
			        }else{
			            return response.getStatusLine().toString();	
			        }
		           
				    
				// handle response here...
				} catch (Exception ex) {
				    // handle exception here
				} finally {
				    try {
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
		return "No se encontro producto con ID: "+mProductID;
	}


	@Override
	public String deleteProdFromScale(Properties ctx, int mClientID,
			String mUPC, int mProductID, String trxName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String insertProdScale(Properties ctx, int mClientID, String mUPC,
			int mProductID, String trxName) {
		// TODO Auto-generated method stub
		return null;
	}


	


}
