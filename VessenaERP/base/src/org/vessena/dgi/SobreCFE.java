package org.openup.dgi;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.adempiere.exceptions.AdempiereException;
import org.apache.axis.AxisFault;
import org.apache.axis.AxisProperties;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.SOAPHeaderElement;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSequence;
import org.compiere.util.DB;
import org.openup.aduana.conexion.DAERespuesta;
import org.openup.cfe.CfeUtils;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.EnvioCFE;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.EnvioCFE.Caratula;
import org.openup.model.MDgiEnvelope;

/**
 * org.openup.dgi - SobreCFE
 * OpenUp Ltda. Issue #4094 
 * Description: Clase encargada de empaquetar los CFEs en un sobre para generar el XML de envio.
 * Documento base: Formato del Sobre - Comprobante Fiscal Electronico - Version 04 16/07/2013 
 * @author Raul Capecce - 28/05/2015
 * @see
 */
public class SobreCFE {
	
	private Properties ctx;
	private String trxName;
	
	private Caratula caratula;
	private EnvioCFE envioCFE;
	
	private ArrayList<MInvoice> mInvoices; 
	private ArrayList<String> cfeStr;
	
	
	public SobreCFE(Properties ctx, String trxName){
		this.ctx = ctx;
		this.trxName = trxName;
		envioCFE = new EnvioCFE();
		envioCFE.setVersion("1.0");
		mInvoices = new ArrayList<MInvoice>();
		cfeStr = new ArrayList<String>();
		caratula = new Caratula();
	}
	
	public void addCFE(MInvoice mInvoice){
		CfeConverter cfe = new CfeConverter(ctx, mInvoice, trxName);
		cfe.loadCFE();
		cfeStr.add(cfe.getCfeStrFirmado());
		mInvoices.add(mInvoice);
	}
	
	public EnvioCFE getEnvioCFE(){
		return envioCFE;
	}
	
	public void generateEnvelopeToDGI(){
		try {
			Timestamp fechaSobre = new Timestamp(System.currentTimeMillis());
			
			
			loadCaratulaDGI(fechaSobre);
			
			
			
			
//			// Generando XML del Sobre
//			java.io.Writer swEnvelope = new StringWriter();
//			JAXBContext context = JAXBContext.newInstance(EnvioCFE.class);
//			Marshaller marshaller = context.createMarshaller();
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			marshaller.setProperty(Marshaller.JAXB_ENCODING, "iso-8859-1");
//			//marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://cfe.dgi.gub.uy EnvioCFE_v1.24.xsd");
//			
//			// Remuevo la propiedad standalone="yes"
//			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
//			swEnvelope.append("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n");
//			
//			marshaller.marshal(envioCFE, swEnvelope);
//			String xmlEnvelope = swEnvelope.toString();
			
			
			String xmlEnvelope = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
					"<DGICFE:EnvioCFE xmlns:DGICFE=\"http://cfe.dgi.gub.uy\" version=\"1.0\" xsi:schemaLocation=\"http://cfe.dgi.gub.uy EnvioCFE_v1.24.xsd\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns3=\"http://www.w3.org/2001/04/xmlenc#\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
					genrateEnvelopeXML(caratula, cfeStr);
			for (String str : cfeStr){
				xmlEnvelope += str;
			}
			xmlEnvelope += "</DGICFE:EnvioCFE>";
			
			
			String nameEnvelope = "Sob_" + caratula.getRUCEmisor() + "_" + String.valueOf(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(fechaSobre, false)).replace("-", "") + "_" + caratula.getIdemisor() + ".xml";
			
			Util.writeToFile("/tmp/" + nameEnvelope, xmlEnvelope);
			
			// Guardando sobre
			MDgiEnvelope mEnvelope = new MDgiEnvelope(ctx, 0, trxName);
			mEnvelope.setRutEmisor(new BigDecimal(caratula.getRUCEmisor()));
			mEnvelope.setRutReceptor(new BigDecimal(caratula.getRutReceptor()));
			mEnvelope.setIdEmisor(caratula.getIdemisor().intValue());
			mEnvelope.setCantidadCFE(new BigDecimal(getEnvioCFE().getCFE().size()));
			mEnvelope.setFechaCreacion(fechaSobre);
			
			
			String ret = enviar(xmlEnvelope);
			
			
			
			
			mEnvelope.saveEx();
			
			
			// Marcando Facturas enviadas en dicho sobre
			for (MInvoice mInv : mInvoices){
				mInv.set_ValueOfColumn("UY_DGI_Envelope_ID", new BigDecimal(mEnvelope.getUY_DGI_Envelope_ID()));
				mInv.saveEx();
			}
			
		} catch (AdempiereException ex){
			throw ex;
		} catch (Exception ex){
			throw new AdempiereException(ex);
		}
		
	}
	
	private String genrateEnvelopeXML(Caratula caratula, ArrayList<String> cfe) throws JAXBException, IOException{
		String ret = "";
		
		java.io.Writer swCaratula = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(Caratula.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		//marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://cfe.dgi.gub.uy");
		
		marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
		
		marshaller.marshal(caratula, swCaratula);
		ret = swCaratula.toString();
		
		ret = ret.replaceAll("<DGICFE:Caratula.*>", "<DGICFE:Caratula xmlns:DGICFE=\"http://cfe.dgi.gub.uy\" version=\"1.0\">");
		
		return ret;
	}
	
	private void loadCaratulaDGI(Timestamp fechaSobre) {
		envioCFE.setCaratula(caratula);
		caratula.setVersion("1.0");
		MOrgInfo orgInfo = MOrgInfo.get(ctx, Integer.valueOf(ctx.getProperty("#AD_Org_ID")), trxName);
		caratula.setRUCEmisor(orgInfo.getDUNS());
		caratula.setRutReceptor("214844360018"); // RUT DGI
		caratula.setCantCFE(cfeStr.size());
		MSequence sec = MSequence.get(ctx, "UY_DGI_Envio");
		int idEmisor = DB.getSQLValue(trxName, "select nextid("+sec.get_ID()+",'N')");
		caratula.setIdemisor(BigInteger.valueOf(idEmisor));
		
		caratula.setFecha(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(fechaSobre, true));
		caratula.setX509Certificate(CaratulaConverter.getPublicCer(ctx, Integer.valueOf(ctx.getProperty("#AD_Client_ID")), Integer.valueOf(ctx.getProperty("#AD_Org_ID")), trxName));
		
		// Prueba Certificado quemado
		//caratula.setX509Certificate("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");
	}
	
	private String enviar(String msjXml){
		String ret = null;
		
		try {

//			System.setProperty("javax.net.ssl.keyStore",                    "/tmp/ARDOINO2017.pfx");
//		    System.setProperty("javax.net.ssl.keyStorePassword",            "ngamarra");
//		    System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
//		    System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
			
//			System.setProperty("javax.net.ssl.trustStore", "cacerts");
//			System.setProperty("javax.net.ssl.trustStorePassword", "2014OPup");
			
//			System.clearProperty("javax.net.ssl.keyStore");
//		    System.clearProperty("javax.net.ssl.keyStorePassword");
//		    System.clearProperty("sun.security.ssl.allowUnsafeRenegotiation");
//		    System.clearProperty("javax.net.ssl.keyStoreType");
//			System.clearProperty("javax.net.ssl.trustStore");
//			System.clearProperty("javax.net.ssl.trustStorePassword");
			
			//System.setProperty("javax.net.ssl.trustStore", "/usr/lib/jvm/jdk1.6.0_45/jre/lib/security/cacerts");
			//System.setProperty("javax.net.ssl.trustStorePassword", "2014OPup");
			
			
			// Create the top-level WS-Security SOAP header XML name.
			QName headerName = new QName(
					"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd",
					"Security");
			SOAPHeaderElement header = new SOAPHeaderElement(headerName);
			// no intermediate actors are involved.
			header.setActor(null);
			// not important, "wsse" is standard
			header.setPrefix("wsse");
			header.setMustUnderstand(false);

			
			
			
			
			String xmlEnvio = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:dgi=\"http://dgi.gub.uy\">\n"
					+ "  <soapenv:Header/>\n"
					+ "  <soapenv:Body>\n"
					+ "     <dgi:WS_eFactura.EFACRECEPCIONSOBRE>\n"
					+ "        <dgi:Datain>\n";
			
			
			xmlEnvio += "<dgi:xmlData><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
					"<DGICFE:EnvioCFE xmlns:DGICFE=\"http://cfe.dgi.gub.uy\" version=\"1.0\" xsi:schemaLocation=\"http://cfe.dgi.gub.uy EnvioCFE_v1.24.xsd\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns3=\"http://www.w3.org/2001/04/xmlenc#\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
					"<DGICFE:Caratula xmlns:DGICFE=\"http://cfe.dgi.gub.uy\" version=\"1.0\">\n" +
					"    <DGICFE:RutReceptor>214844360018</DGICFE:RutReceptor>\n" +
					"    <DGICFE:RUCEmisor>213374740016</DGICFE:RUCEmisor>\n" +
					"    <DGICFE:Idemisor>234</DGICFE:Idemisor>\n" +
					"    <DGICFE:CantCFE>1</DGICFE:CantCFE>\n" +
					"    <DGICFE:Fecha>2015-06-24T11:06:14.033-03:00</DGICFE:Fecha>\n" +
					"    <DGICFE:X509Certificate>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</DGICFE:X509Certificate>\n" +
					"</DGICFE:Caratula><ns0:CFE xmlns:ns0=\"http://cfe.dgi.gub.uy\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" version=\"1.0\">\n" +
					"    <ns0:eFact>\n" +
					"        <ns0:TmstFirma>2015-06-02T00:00:00.000-03:00</ns0:TmstFirma>\n" +
					"        <ns0:Encabezado>\n" +
					"            <ns0:IdDoc>\n" +
					"                <ns0:TipoCFE>111</ns0:TipoCFE>\n" +
					"                <ns0:Serie>A</ns0:Serie>\n" +
					"                <ns0:Nro>85057</ns0:Nro>\n" +
					"                <ns0:FchEmis>2015-06-02</ns0:FchEmis>\n" +
					"                <ns0:MntBruto>1</ns0:MntBruto>\n" +
					"                <ns0:FmaPago>2</ns0:FmaPago>\n" +
					"                <ns0:FchVenc>2015-06-02</ns0:FchVenc>\n" +
					"            </ns0:IdDoc>\n" +
					"            <ns0:Emisor>\n" +
					"                <ns0:RUCEmisor>213374740016</ns0:RUCEmisor>\n" +
					"                <ns0:RznSoc>ARDOINO SOCIEDAD ANONIMA</ns0:RznSoc>\n" +
					"                <ns0:NomComercial>Ardoino S.A.</ns0:NomComercial>\n" +
					"                <ns0:CdgDGISucur>1</ns0:CdgDGISucur>\n" +
					"                <ns0:DomFiscal>Misiones 1589 Piso 1</ns0:DomFiscal>\n" +
					"                <ns0:Ciudad> CIUDAD VIEJA</ns0:Ciudad>\n" +
					"                <ns0:Departamento>MONTEVIDEO</ns0:Departamento>\n" +
					"            </ns0:Emisor>\n" +
					"            <ns0:Receptor>\n" +
					"                <ns0:TipoDocRecep>2</ns0:TipoDocRecep>\n" +
					"                <ns0:CodPaisRecep>UY</ns0:CodPaisRecep>\n" +
					"                <ns0:DocRecep>210002490013</ns0:DocRecep>\n" +
					"                <ns0:RznSocRecep>ALUMINIOS DEL URUGUAY S.A.</ns0:RznSocRecep>\n" +
					"                <ns0:DirRecep>RAMON MARQUEZ 3222</ns0:DirRecep>\n" +
					"                <ns0:CiudadRecep> MONTEVIDEO</ns0:CiudadRecep>\n" +
					"                <ns0:DeptoRecep>MONTEVIDEO</ns0:DeptoRecep>\n" +
					"                <ns0:PaisRecep>Uruguay</ns0:PaisRecep>\n" +
					"                <ns0:CP>11100</ns0:CP>\n" +
					"            </ns0:Receptor>\n" +
					"            <ns0:Totales>\n" +
					"                <ns0:TpoMoneda>USD</ns0:TpoMoneda>\n" +
					"                <ns0:TpoCambio>0.038</ns0:TpoCambio>\n" +
					"                <ns0:MntNoGrv>0.00</ns0:MntNoGrv>\n" +
					"                <ns0:MntExpoyAsim>0</ns0:MntExpoyAsim>\n" +
					"                <ns0:MntImpuestoPerc>0</ns0:MntImpuestoPerc>\n" +
					"                <ns0:MntIVaenSusp>0</ns0:MntIVaenSusp>\n" +
					"                <ns0:MntNetoIvaTasaMin>0</ns0:MntNetoIvaTasaMin>\n" +
					"                <ns0:MntNetoIVATasaBasica>0</ns0:MntNetoIVATasaBasica>\n" +
					"                <ns0:MntNetoIVAOtra>0</ns0:MntNetoIVAOtra>\n" +
					"                <ns0:IVATasaMin>10.000</ns0:IVATasaMin>\n" +
					"                <ns0:IVATasaBasica>22.000</ns0:IVATasaBasica>\n" +
					"                <ns0:MntIVATasaMin>0</ns0:MntIVATasaMin>\n" +
					"                <ns0:MntIVATasaBasica>0</ns0:MntIVATasaBasica>\n" +
					"                <ns0:MntIVAOtra>0</ns0:MntIVAOtra>\n" +
					"                <ns0:MntTotal>0.00</ns0:MntTotal>\n" +
					"                <ns0:MntTotRetenido>0</ns0:MntTotRetenido>\n" +
					"                <ns0:CantLinDet>1</ns0:CantLinDet>\n" +
					"                <ns0:MontoNF>0</ns0:MontoNF>\n" +
					"                <ns0:MntPagar>0.00</ns0:MntPagar>\n" +
					"            </ns0:Totales>\n" +
					"        </ns0:Encabezado>\n" +
					"        <ns0:Detalle>\n" +
					"            <ns0:Item>\n" +
					"                <ns0:NroLinDet>1</ns0:NroLinDet>\n" +
					"                <ns0:CodItem>\n" +
					"                    <ns0:TpoCod>INT1</ns0:TpoCod>\n" +
					"                    <ns0:Cod>fleteexp</ns0:Cod>\n" +
					"                </ns0:CodItem>\n" +
					"                <ns0:IndFact>1</ns0:IndFact>\n" +
					"                <ns0:IndAgenteResp>R</ns0:IndAgenteResp>\n" +
					"                <ns0:NomItem>FLETE EXPORTACION</ns0:NomItem>\n" +
					"                <ns0:DscItem>FLETE EXPORTACION</ns0:DscItem>\n" +
					"                <ns0:Cantidad>1.00</ns0:Cantidad>\n" +
					"                <ns0:UniMed>UNI</ns0:UniMed>\n" +
					"                <ns0:PrecioUnitario>0.00</ns0:PrecioUnitario>\n" +
					"                <ns0:RecargoPct>0</ns0:RecargoPct>\n" +
					"                <ns0:RecargoMnt>0</ns0:RecargoMnt>\n" +
					"                <ns0:MontoItem>0.0000</ns0:MontoItem>\n" +
					"            </ns0:Item>\n" +
					"        </ns0:Detalle>\n" +
					"        <ns0:DscRcgGlobal>\n" +
					"            <ns0:DRG_Item>\n" +
					"                <ns0:NroLinDR>1</ns0:NroLinDR>\n" +
					"                <ns0:TpoMovDR>D</ns0:TpoMovDR>\n" +
					"                <ns0:TpoDR>2</ns0:TpoDR>\n" +
					"                <ns0:ValorDR>10.000000000000</ns0:ValorDR>\n" +
					"                <ns0:IndFactDR>1</ns0:IndFactDR>\n" +
					"            </ns0:DRG_Item>\n" +
					"        </ns0:DscRcgGlobal>\n" +
					"        <ns0:CAEData>\n" +
					"            <ns0:CAE_ID>2</ns0:CAE_ID>\n" +
					"            <ns0:DNro>100</ns0:DNro>\n" +
					"            <ns0:HNro>200</ns0:HNro>\n" +
					"            <ns0:FecVenc>2015-06-30</ns0:FecVenc>\n" +
					"        </ns0:CAEData>\n" +
					"    </ns0:eFact>\n" +
					"<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
					"<ds:SignedInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
					"<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"/>\n" +
					"<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"/>\n" +
					"<ds:Reference URI=\"\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
					"<ds:Transforms xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
					"<ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"/>\n" +
					"<ds:Transform Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"/>\n" +
					"</ds:Transforms>\n" +
					"<ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"/>\n" +
					"<ds:DigestValue xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">GGGCMenjd9cpB/ssQVbScy+NTGo=</ds:DigestValue>\n" +
					"</ds:Reference>\n" +
					"</ds:SignedInfo>\n" +
					"<ds:SignatureValue xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
					"OLv0g2juA1W5nhYGnSxiFHqNOm4ks1mmzSaAx91FilabLhyRMSV4YA69YCs2qAD/WlXao4Ws+Fab\n" +
					"vaFxRBlHlyqaHHorNdf6zNGkv3W97VejsAi7HVK1nAxQWU2vgpkQMZHP6qGVpLi5OBrcBOFuoDcZ\n" +
					"2BzDlCuueaAQqTz1KMQZZqLsKM43TJGDNXzDZXlbk4r/14IarmhNqU+bx10ql3XcepLGKwUc1TDP\n" +
					"uKS75YyElDs1DS7kAYtkvesBLeXYmQ4m14Jcbm6U3cYxWaQso6t41m0GABq2d6yeyL1HBGfCiZ/3\n" +
					"U4bmW5EEwlfwu4EIjqxE32vUyVQtzOGOh99b1A==\n" +
					"</ds:SignatureValue>\n" +
					"<ds:KeyInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
					"<ds:X509Data xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
					"<ds:X509IssuerSerial xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
					"<ds:X509IssuerName xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">C=UY,O=AdministraciÃ³n Nacional de Correos,CN=Correo Uruguayo - CA</ds:X509IssuerName>\n" +
					"\n" +
					"<ds:X509SerialNumber xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">1638340170610021355252243959522134310127476408</ds:X509SerialNumber>\n" +
					"</ds:X509IssuerSerial>\n" +
					"</ds:X509Data>\n" +
					"</ds:KeyInfo>\n" +
					"</ds:Signature></ns0:CFE></DGICFE:EnvioCFE>]]></dgi:xmlData>\n" +
					"";
								
			xmlEnvio += "</dgi:Datain>\n"
					+ "     </dgi:WS_eFactura.EFACRECEPCIONSOBRE>\n"
					+ "  </soapenv:Body>\n"
					+ "</soapenv:Envelope>";			
			
			
			
			
			
			
			
			
			
			Service service = new Service();
			Call call = (Call) service.createCall();
			// Establecemos la dirección en la que está activado el WebService
			call.setTargetEndpointAddress(new java.net.URL("https://efactura.dgi.gub.uy:6443/ePrueba/ws_eprueba"));
			// Establecemos el nombre del método a invocar
			call.setOperationName(new QName("EFACRECEPCIONSOBRE"));
			call.setSOAPActionURI("http://dgi.gub.uyaction/AWS_EFACTURA.EFACRECEPCIONSOBRE");
			
			//call.addHeader(header);

			// Establecemos el usuario y contraseña para autenticar la conexión (en caso contrario, tirará error 401 No autorizado).			
			//call.setUsername(user_name);
			//call.setPassword(user_pass);

			//call.setEncodingStyle("http://dgi.gub.uy");
			
			
			
			//AxisProperties.setProperty("axis.socketSecureFactory", "org.openup.dgi.FluxitSSLSocketFactory", true);
			
			
			
			// Establecemos los parámetros que necesita el método
			// Observe que se deben especidicar correctamente tanto el nómbre como el tipo de datos. Esta información se puede obtener viendo el WSDL del servicio Web

			//gg-call.addParameter(new QName("http://dgi.gub.uy", "Datain"), XMLType.XSD_STRING, ParameterMode.IN);
			
			// Especificamos el tipo de datos que devuelve el método.
			//call.setReturnType(XMLType.XSD_ANYTYPE);
			
			//gg-call.setReturnType(new QName("http://dgi.gub.uy", "Dataout"));
			
			// Invocamos el método
			//String asdf = Util.readFromFile("/tmp/asdf");
			//ret = (String) call.invoke(new Object[] {"<xmlData>" + msjXml + "</xmlData>"});
			ret = (String) call.invoke(new Object[] {xmlEnvio});
			
			
			
			System.out.println(ret);
			
			// Imprimimos los resultados
			//System.out.println("Respuesta webservice: " + result);
			
		} catch (AxisFault e) {
			throw new AdempiereException(e.getMessage());
		} catch (RemoteException e) {
			throw new AdempiereException(e.getMessage());
		} catch (SecurityException e) {
			throw new AdempiereException(e.getMessage());
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		

		return ret;
	}
}
