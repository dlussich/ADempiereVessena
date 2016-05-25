package org.openup.aduana;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileReader;
import java.io.FileWriter;
import java.security.Key;
import java.security.KeyStore;  
import java.security.PrivateKey;  
import java.security.cert.X509Certificate;  
  
  

import java.util.Enumeration;
import java.util.Properties;
import java.io.FileOutputStream;  
  









import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.transform.OutputKeys;  
import javax.xml.transform.Transformer;  
import javax.xml.transform.TransformerFactory;  
import javax.xml.transform.dom.DOMSource;  
import javax.xml.transform.stream.StreamResult;  

import org.w3c.dom.*;  
import org.adempiere.exceptions.AdempiereException;
import org.apache.xml.security.signature.XMLSignature;  
import org.apache.xml.security.transforms.Transforms;  
import org.apache.xml.security.utils.Constants;  
import org.compiere.model.MImage;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class Firma {

	private static final String KEYSTORE_TYPE = "JKS";
    
	public Firma() {
		// TODO Auto-generated constructor stub
	}

	public static String FirmarEmbedXML(String objXml, String idFileKeyStore, String passKeyStore, String passPrivateKey, Properties ctx, String trxName) {
		String ret = "";
		Document doc = null;
		
		byte[] bytesFileKeyStore = (new MImage(ctx, Integer.parseInt(idFileKeyStore), trxName)).getData();
		
		org.apache.xml.security.Init.init();

		try {
			// Creando documento temporal para procesar
			File fXmlFile = File.createTempFile("DUAtmp", null);
			fXmlFile.deleteOnExit();
			BufferedWriter out = new BufferedWriter(new FileWriter(fXmlFile));
			out.write(new String(objXml.getBytes(), "UTF-8"));
			out.close();
			
			// Creando el documento para procesar
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			

			Constants.setSignatureSpecNSprefix("");
			// Cargamos el almacen de claves
			KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
			ks.load(new ByteInputStream(bytesFileKeyStore, bytesFileKeyStore.length), passKeyStore.toCharArray());
			
			// Obtenemos la clave privada, pues la necesitaremos para encriptar.  
			Key privateKey = null;
			File signatureFile = File.createTempFile("DUAtmpFirmado", null);
			signatureFile.deleteOnExit();
			String baseURI = signatureFile.toURL().toString(); // BaseURI para las URL Relativas.
			String aliasEmpresa = "";
	        
			Enumeration aliasesEnum = ks.aliases();
			while (aliasesEnum.hasMoreElements()) {
				aliasEmpresa = (String) aliasesEnum.nextElement();
				if (ks.getKey(aliasEmpresa, passPrivateKey.toCharArray()) != null) {
					privateKey = (PrivateKey) ks.getKey(aliasEmpresa,
							passPrivateKey.toCharArray());
					// store the private keys in an arraylist.
				}
			}
	        
	        // Instanciamos un objeto XMLSignature desde el Document. El algoritmo de firma será DSA  
	        XMLSignature xmlSignature = new XMLSignature(doc, baseURI, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);  
	  

	        // Añadimos el nodo de la firma a la raiz antes de firmar.  
	        // Observe que ambos elementos pueden ser mezclados en una forma con referencias separadas  
	        Element ele = doc.getDocumentElement();
	        ele.appendChild(xmlSignature.getElement());  
	  
	        // Creamos el objeto que mapea: Document/Reference  
	        Transforms transforms = new Transforms(doc);  
	        transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
	        
	        //transforms.addTransform(Transforms.TRANSFORM_C14N_OMIT_COMMENTS);
	          
	        // Añadimos lo anterior Documento / Referencia  
	        // ALGO_ID_DIGEST_SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";  
	        xmlSignature.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);  
	  
	        // Añadimos el KeyInfo del certificado cuya clave privada usamos  
	        X509Certificate cert = (X509Certificate) ks.getCertificate(aliasEmpresa);  
	        xmlSignature.addKeyInfo(cert);  
	        //xmlSignature.addKeyInfo(cert.getPublicKey());  
	          
	          
	        // Realizamos la firma  
	        xmlSignature.sign(privateKey);  
	          
	        
			// Guardamos archivo de firma en disco
			FileOutputStream f = new FileOutputStream(signatureFile);
			TransformerFactory factorySignature = TransformerFactory.newInstance();
			Transformer transformer = factorySignature.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(f));
			f.close();

			// Cargando archivo temporal firmado
			ret = "";
			int linea = 0;
			BufferedReader brSignature = new BufferedReader(new FileReader(signatureFile));
			while ((linea = brSignature.read()) != -1) {
				ret += (char) linea;
			}
			brSignature.close();
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

		// Retorno el xml firmado que se habia guardado en el archivo temporal
		return ret;
	}

}
