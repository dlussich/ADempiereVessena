package org.openup.aduana;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.util.IteratorIterable;
import org.openup.aduana.conexion.DAERespuesta;
import org.openup.aduana.conexion.DAERespuesta.Respuesta;
import org.openup.aduana.conexion.DAERespuesta.Respuesta.Referencia;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ParameterMode;

public class Envio {

	private static String endpoint;
	private static String operationMethod;
	private static String actionMethod;
	private static String namespace;
	private static String parameterIn;
	private static String user_name;
	private static String user_pass;
	
	public static enum Operacion{
		EnviarCrt,
		EnviarMic
	};
	
	public Envio() {
		// TODO Auto-generated constructor stub
	}

	protected static void CargarConfiguracionConexion(Operacion operacion, int ad_Client_ID, int ad_Org_ID, String trxName) throws Exception{
		
		// Cargar desde Adempiere
		
		/*  Usuario y contraseña de la empresa de transporte que se quiere comunicar con el sistema Lucia de Aduana
		 *  La empresa transportista solicitará estos datos al Correo Uruguayo.
		 */
		// Datos de prueba de Ardoino
		user_name = DB.getSQLValueStringEx(trxName, "SELECT username FROM uy_tr_webservice WHERE ad_client_id = " + ad_Client_ID + " AND ad_org_id = " + ad_Org_ID);
		user_pass = DB.getSQLValueStringEx(trxName, "SELECT password FROM uy_tr_webservice WHERE ad_client_id = " + ad_Client_ID + " AND ad_org_id = " + ad_Org_ID);
		
		// Datos genéricos del webservice
		namespace = DB.getSQLValueStringEx(trxName, "SELECT Namespace FROM uy_tr_webservice WHERE ad_client_id = " + ad_Client_ID + " AND ad_org_id = " + ad_Org_ID);
		operationMethod = DB.getSQLValueStringEx(trxName, "SELECT OperationMethod FROM uy_tr_webservice WHERE ad_client_id = " + ad_Client_ID + " AND ad_org_id = " + ad_Org_ID);
		parameterIn = DB.getSQLValueStringEx(trxName, "SELECT ParameterIn FROM uy_tr_webservice WHERE ad_client_id = " + ad_Client_ID + " AND ad_org_id = " + ad_Org_ID);
		
		switch (operacion) {
		case EnviarCrt:
			endpoint = DB.getSQLValueStringEx(trxName, "SELECT CrtEndPoint FROM uy_tr_webservice WHERE ad_client_id = " + ad_Client_ID + " AND ad_org_id = " + ad_Org_ID);
			actionMethod = DB.getSQLValueStringEx(trxName, "SELECT CrtActionMethod FROM uy_tr_webservice WHERE ad_client_id = " + ad_Client_ID + " AND ad_org_id = " + ad_Org_ID);
			break;
		case EnviarMic:
			endpoint = DB.getSQLValueStringEx(trxName, "SELECT MicEndPoint FROM uy_tr_webservice WHERE ad_client_id = " + ad_Client_ID + " AND ad_org_id = " + ad_Org_ID);
			actionMethod = DB.getSQLValueStringEx(trxName, "SELECT MicActionMethod FROM uy_tr_webservice WHERE ad_client_id = " + ad_Client_ID + " AND ad_org_id = " + ad_Org_ID);
			break;
		}

	}
	
	public static DAERespuesta EnviarMensajeAduana(String mensajeXML, Operacion operacion, int ad_Client_ID, int ad_Org_ID, String trxName)
			throws AdempiereException {
		
		DAERespuesta ret = null;
		
		try {

			// Cargando configuración desde Adempiere
			CargarConfiguracionConexion(operacion, ad_Client_ID, ad_Org_ID, trxName);
			
			
			Service service = new Service();
			Call call = (Call) service.createCall();
			// Establecemos la dirección en la que está activado el WebService
			call.setTargetEndpointAddress(new java.net.URL(endpoint));

			// Establecemos el nombre del método a invocar
			call.setOperationName(operationMethod);
			call.setSOAPActionURI(actionMethod);

			// Establecemos el usuario y contraseña para autenticar la conexión (en caso contrario, tirará error 401 No autorizado).			
			call.setUsername(user_name);
			call.setPassword(user_pass);

			// Establecemos los parámetros que necesita el método
			// Observe que se deben especidicar correctamente tanto el nómbre como el tipo de datos. Esta información se puede obtener viendo el WSDL del servicio Web
			call.addParameter(new QName(namespace, parameterIn), XMLType.XSD_STRING, ParameterMode.IN);

			// Especificamos el tipo de datos que devuelve el método.
			call.setReturnType(XMLType.XSD_STRING);

			// Invocamos el método
			String result = (String) call.invoke(new Object[] { mensajeXML });

			ret = getObjResponse(result);
			
			// Imprimimos los resultados
			//System.out.println("Respuesta webservice: " + result);
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		

		return ret;

	}
	
	protected static DAERespuesta getObjResponse(String strXMLResponse) {
		DAERespuesta objRet = new DAERespuesta();
		Document doc = null;

		try {
			// Creando documento temporal para procesar
			File fXmlFile = File.createTempFile("CRTResponse", null);
			fXmlFile.deleteOnExit();
			BufferedWriter out = new BufferedWriter(new FileWriter(fXmlFile));
			out.write(strXMLResponse);
			out.close();

			// Creando el documento para procesar
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			SAXBuilder builder = new SAXBuilder();  
			// Construimos el arbol DOM a partir del fichero xml
			doc = builder.build(new InputStreamReader(new FileInputStream(fXmlFile)), "UTF-8");  
			
			Element elemDAERespuesta = doc.getRootElement();
			List<Element> elemHijosRoot = elemDAERespuesta.getChildren();
			List<Element> elemRespuestas = null;
			for (Element elemHijoRoot:elemHijosRoot){
				if (elemHijoRoot.getName().equalsIgnoreCase("Respuestas"))
					elemRespuestas=elemHijoRoot.getChildren();
			}
			
			
			// Recorro las respuestas
			
			if (elemRespuestas != null) {
				for (Element elemRespuesta : elemRespuestas) {

					DAERespuesta.Respuesta objRespuesta = new DAERespuesta.Respuesta();
					objRet.getRespuestas().add(objRespuesta);

					// Recorro los tags hijos de la respuesta
					List<Element> elemHijosRespuesta = elemRespuesta.getChildren();
					for (Element elemHijoRespuesta : elemHijosRespuesta) {
						if (elemHijoRespuesta.getName().equalsIgnoreCase("Tipo"))
							objRespuesta.setTipo(elemHijoRespuesta.getValue());
						else if (elemHijoRespuesta.getName().equalsIgnoreCase("Codigo"))
							objRespuesta.setCodigo(elemHijoRespuesta.getValue());
						else if (elemHijoRespuesta.getName().equalsIgnoreCase("Descripcion"))
							objRespuesta.setDescripcion(elemHijoRespuesta.getValue());
						else if (elemHijoRespuesta.getName().equalsIgnoreCase("Ayuda"))
							objRespuesta.setAyuda(elemHijoRespuesta.getValue());
						else if (elemHijoRespuesta.getName().equalsIgnoreCase("Referencias")) {
							List<Element> elemHijosReferencia = elemHijoRespuesta.getChildren();
							for (Element elemHijoReferencia: elemHijosReferencia){
								List<Element> elemHijoUnaReferencia = elemHijoReferencia.getChildren();
								DAERespuesta.Respuesta.Referencia objReferencia = new DAERespuesta.Respuesta.Referencia();
								objRespuesta.getReferencias().add(objReferencia);
								for (Element elemReferencia:elemHijoUnaReferencia){
								if (elemReferencia.getName().equalsIgnoreCase("Codigo"))
									objReferencia.setCodigo(elemReferencia.getValue());
								else if (elemReferencia.getName().equalsIgnoreCase("Valor"))
									objReferencia.setValor(elemReferencia.getValue());
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		return objRet;
	}
	
	public static long generateIdTransaccionAduana(int ad_client_id, int ad_org_id, String trxName){
		String idTransaccion = "";
		long idTransaccionRet = 0;
		
		// Valor actual del campo
		String sql = "select transactionCode from UY_TR_WebService"
				+ " where ad_client_id = " + ad_client_id
				+ " and  ad_org_id = " + ad_org_id;
		idTransaccion = DB.getSQLValueStringEx(trxName, sql);

		try{
			idTransaccionRet = Long.valueOf(idTransaccion);
		}catch (Exception ex){
			idTransaccionRet = 1000;
		}
		
		String strSqlTr = "UPDATE UY_TR_WebService SET transactionCode = '" + ++idTransaccionRet +"'"
				+ " where ad_client_id = " + ad_client_id
				+ " and  ad_org_id = " + ad_org_id;
		DB.executeUpdateEx(strSqlTr, trxName);
		
		return idTransaccionRet;
	}

}
