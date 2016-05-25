package org.openup.cfe.provider.sisteco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.adempiere.exceptions.AdempiereException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.openup.cfe.InterfaceCFEDTO;
import org.openup.cfe.provider.migrate.dto.EnvioCFE;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEEmpresasType;
import org.openup.model.MCFEProviderConfig;

public class SistecoCommunication {
	
	private CFEDefType cfeDefType;
	private Properties ctx;
	private String trxName;
	private MCFEProviderConfig mCfeProviderConfig;
	private SistecoResponseDTO sistecoResponseDTO;

	public SistecoCommunication(CFEDefType cfeDefType, Properties ctx, String trxName) {
		this.cfeDefType = cfeDefType;
		this.ctx = ctx;
		this.trxName = trxName;
		
		mCfeProviderConfig = MCFEProviderConfig.getProviderConfig(ctx, trxName);
	}

	public void Send() {
		
		try {
			
		
			
			CFEEmpresasType cfeEmpresasType = new CFEEmpresasType();
			cfeEmpresasType.setCFE(cfeDefType);
			
			File file = File.createTempFile("SistecoXMLCFE", ".xml");
			file.deleteOnExit();
			JAXBContext jaxbContext = JAXBContext.newInstance(CFEEmpresasType.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(cfeEmpresasType, file);
			
			FileReader fr = new FileReader(file);
	        BufferedReader br = new BufferedReader(fr);
	        
	        String linea;
	        String xml = "";
			while((linea=br.readLine())!=null) {
				xml += linea + "\n";
			}
			
			
//			// Quito namespaces
			xml = xml
//					//.replaceAll("xmlns:ns2=\"[a-zA-Z1-90:/.#]*\"", "")
//					//.replaceAll("xmlns:ns3=\"[a-zA-Z1-90:/.#]*\"", "")
//					//.replace("<CFE_Adenda  >", "<CFE_Adenda>")
//					//.replace("standalone=\"yes\"", "")
					.replace("<CFE xmlns:ns0=\"http://cfe.dgi.gub.uy\" version=\"1.0\">", "<ns0:CFE version=\"1.0\">")
					.replace("</CFE>","</ns0:CFE>")
					.replace("<CFE_Adenda ", "<ns0:CFE_Adenda xmlns:ns0=\"http://cfe.dgi.gub.uy\"")
					.replace("</CFE_Adenda>", "</ns0:CFE_Adenda>")
					.replace("xmlns:ns0=\"http://cfe.dgi.gub.uy\"xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "xmlns:ns0=\"http://cfe.dgi.gub.uy\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"");
			
			// Guardo XML sin los namespace
			PrintWriter pw = new PrintWriter(file);
			pw.println(xml);
			pw.close();
			
			
			
			
//			// XML DE PRUEBA DADO POR SISTECO - NO FUNCION� TAMPOCO
//			FileReader frPrueba = new FileReader(new File("/home/raul/Desarrollo/Proyectos/DGI/Facturaci�n Electr�nica/ProyectoCFE/Sisteco/PuebasXML/MailConsulta1/Respuesta/SistecoXMLCFE896827462540413998-OK.xml"));
//	        BufferedReader brPrueba = new BufferedReader(frPrueba);
//	        String lineaPrueba = null;
//	        xml = "";
//	        while((lineaPrueba=brPrueba.readLine())!=null) {
//				xml += lineaPrueba;
//			}
			
			
			

			Service service = new Service();
			Call call = (Call) service.createCall();
			// Establecemos la dirección en la que está activado el WebService
			call.setTargetEndpointAddress(new java.net.URL(mCfeProviderConfig.getSistecoEndpoint()));
			
			//call.setOperationName(new QName("efac", "http://www.objetos.com.uy/efactura/"));
			// Establecemos el nombre del método a invocar
			call.setOperationName(new QName(mCfeProviderConfig.getSistecoNamespaceRecepDoc(), mCfeProviderConfig.getSistecoMethodRecepDoc()));
			call.setSOAPActionURI(mCfeProviderConfig.getSistecoActionRecepDoc());

			// Establecemos los parámetros que necesita el método
			// Observe que se deben especidicar correctamente tanto el nómbre como el tipo de datos. Esta información se puede obtener viendo el WSDL del servicio Web
			call.addParameter(new QName(mCfeProviderConfig.getSistecoParameterInRecepDoc()), XMLType.XSD_STRING, ParameterMode.IN);

			// Especificamos el tipo de datos que devuelve el método.
			call.setReturnType(XMLType.XSD_STRING);

			// Invocamos el método
			String result = (String) call.invoke(mCfeProviderConfig.getSistecoNamespaceRecepDoc(), mCfeProviderConfig.getSistecoMethodRecepDoc(), new Object[] { "<![CDATA[" + xml + "]]>" });
			
			// Quitamos el CDATA, solo al comienzo y al final si estan en el string
			result = result.replaceAll("^<!\\[CDATA\\[", "").replaceAll("]]>$", "");
			
			
			// Guardo la respuesta de Sisteco
			File response = File.createTempFile("SistecoXMLCFEResponse", ".xml");
			response.deleteOnExit();
			FileWriter fichero = new FileWriter(response);
			PrintWriter pwResponse = new PrintWriter(fichero);
			pwResponse.print(result);
			pwResponse.close();
			
			sistecoResponseDTO = SistecoConvertResponse.getObjSistecoResponseDTO(result);
			
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	public SistecoResponseDTO getSistecoResponseDTO() {
		return sistecoResponseDTO;
	}

}
