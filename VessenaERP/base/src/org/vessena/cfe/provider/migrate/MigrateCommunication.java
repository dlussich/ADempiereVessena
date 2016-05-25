package org.openup.cfe.provider.migrate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.adempiere.exceptions.AdempiereException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;
import org.openup.cfe.CfeUtils;
import org.openup.cfe.provider.migrate.dto.CFEInvoiCyCollectionType;
import org.openup.cfe.provider.migrate.dto.CFEInvoiCyType;
import org.openup.cfe.provider.migrate.dto.EncabezadoEnvioType;
import org.openup.cfe.provider.migrate.dto.EnvioCFE;
import org.openup.cfe.provider.migrate.dto.EnvioCFERetorno;
import org.openup.cfe.provider.migrate.dto.ListaCFERetornoType.CFE;
import org.openup.model.MCFEProviderConfig;

public class MigrateCommunication {

	private EnvioCFE envioCfe;
	private String generatedXml;
	private String responseXml;
	private EnvioCFERetorno envioCfeRetorno;

	private MCFEProviderConfig mCfeProviderConfig;
	
	private String empCodigo;
	private String empPK;
	private String empCK;
	
	public MigrateCommunication(EnvioCFE envioCfe, int ad_client, Properties ctx, String trxName) {
		
		this.envioCfe = envioCfe;
		
		
		// this.empPK = MSysConfig.getValue("UY_CFE_Invoicy_empPK", "UxMJu9uAsUTsyTWkMQFQZw==", ad_client);

		mCfeProviderConfig = MCFEProviderConfig.getProviderConfig(ctx, trxName);

		MOrg mOrg = new MOrg(ctx, Env.getAD_Org_ID(ctx), trxName);
		MOrgInfo mOrgInfo = mOrg.getInfo();
		
		try {
			this.empCK = mOrgInfo.get_ValueAsString("InvoicyEmpCK");
		} catch (Exception e) {
			// En caso de no estar la columna la obtiene del system configurator
			this.empCodigo = MSysConfig.getValue("UY_CFE_Invoicy_empCodigo", "72", ad_client);
		}
		try {
			this.empCodigo = mOrgInfo.get_ValueAsString("InvoicyEmpCodigo");
		} catch (Exception e) {
			// En caso de no estar la columna la obtiene del system configurator
			this.empCK = MSysConfig.getValue("UY_CFE_Invoicy_empCK", "KeX86Ojy0B9AuR2vXLvTJZjPAiPc0", ad_client);
		}
		//Env.getAD_Client_ID(ctx);
		
		this.empPK = mCfeProviderConfig.getInvoicyEmpPK();
	}
	
	public void Send() {

		try {
			
			File cfeFile = File.createTempFile("CFETag", ".xml");
			cfeFile.deleteOnExit();
			JAXBContext jaxbContextCfe = JAXBContext.newInstance(CFEInvoiCyCollectionType.class);
			Marshaller jaxbMarshallerCfe = jaxbContextCfe.createMarshaller();
			jaxbMarshallerCfe.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			jaxbMarshallerCfe.marshal(envioCfe.getCFE(), cfeFile);
			FileReader frCfe = new FileReader(cfeFile);
	        BufferedReader brCfe = new BufferedReader(frCfe);
	        
	        String lineaCfe;
	        String xmlCfe = "";
			while((lineaCfe=brCfe.readLine())!=null) {
				xmlCfe += lineaCfe;
			}
			// Elimino la metadata en la primera parte del CFE
			xmlCfe = this.empCK + 
					xmlCfe.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "")
					.replace(" xmlns=\"http://www.invoicy.com.uy/\"", "");;
			
			
			
			String hashXmlCfe = CfeUtils.md5Encrypt(xmlCfe);
			
			
			
			
			// Encabezado
			EncabezadoEnvioType encabezado = new EncabezadoEnvioType();
			envioCfe.setEncabezado(encabezado);
			encabezado.setEmpCK(hashXmlCfe);
			encabezado.setEmpPK(empPK);
			encabezado.setEmpCodigo(empCodigo);

			File file = File.createTempFile("InvoicyXML", ".xml");
			file.deleteOnExit();
			JAXBContext jaxbContext = JAXBContext.newInstance(EnvioCFE.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);

			jaxbMarshaller.marshal(envioCfe, file);
			
			FileReader fr = new FileReader(file);
	        BufferedReader br = new BufferedReader(fr);
	        
	        String linea;
	        String xml = "";
			while((linea=br.readLine())!=null) {
				xml += linea;
			}
			xml = xml.replace(" xmlns=\"http://www.invoicy.com.uy/\"", "");
			
			
			
			Service service = new Service();
			Call call = (Call) service.createCall();
			// Establecemos la dirección en la que está activado el WebService
			call.setTargetEndpointAddress(new java.net.URL(mCfeProviderConfig.getInvoicyEndpoint()));

			// Establecemos el nombre del método a invocar
			call.setOperationName(mCfeProviderConfig.getInvoicyMethodRecepDoc());
			call.setSOAPActionURI(mCfeProviderConfig.getInvoicyActionRecepDoc());


			// Establecemos los parámetros que necesita el método
			// Observe que se deben especidicar correctamente tanto el nómbre como el tipo de datos. Esta información se puede obtener viendo el WSDL del servicio Web
			call.addParameter(new QName(mCfeProviderConfig.getInvoicyNamespaceRecepDoc(), mCfeProviderConfig.getInvoicyParameterInRecepDoc()), XMLType.XSD_STRING, ParameterMode.IN);

			// Especificamos el tipo de datos que devuelve el método.
			call.setReturnType(XMLType.XSD_STRING);

			this.generatedXml = xml;
			// Invocamos el método
			String result = (String) call.invoke(new Object[] { xml });
			
			//result = result.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
			File response = File.createTempFile("InvoicyXMLResponse", ".xml");
			response.deleteOnExit();
			FileWriter fichero = new FileWriter(response);
			PrintWriter pw = new PrintWriter(fichero);
			pw.print(result);
			pw.close();
			
			this.responseXml = result;
			
			
//			// Cargando respuesta como objeto
//			JAXBContext jaxbContextResponse = JAXBContext.newInstance(EnvioCFERetorno.class);
//			Unmarshaller jaxbUnmarshaller = jaxbContextResponse.createUnmarshaller();
//			envioCfeRetorno = (EnvioCFERetorno)jaxbUnmarshaller.unmarshal(response); 
			
			
			envioCfeRetorno = MigrateConvertResponse.getObjEnvioCFERetorno(responseXml);
			
			
			
	         
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String getGeneratedXml() {
		return generatedXml;
	}

	public String getResponseXml() {
		return responseXml;
	}

	public EnvioCFERetorno getEnvioCfeRetorno() {
		return envioCfeRetorno;
	}
	
	

}
