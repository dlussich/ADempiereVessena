package org.openup.dgi;

import java.io.File;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Enumeration;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.codec.binary.Base64;
import org.apache.xml.security.utils.Constants;
import org.compiere.model.MImage;
import org.openup.aduana.ConsultaRespuestaMic;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.EnvioCFE.Caratula;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class CaratulaConverter {
	
	public static String getPublicCer(Properties ctx, int ad_client, int ad_org, String trxName){
		
		String ret = null;
		
		String idFileKeyStore = ConsultaRespuestaMic.getUyTrDigitalsignature("BinaryFile_ID",  ad_client, ad_org, trxName);
		String passKeyStore = ConsultaRespuestaMic.getUyTrDigitalsignature("KeystorePass",  ad_client, ad_org, trxName);
		
		byte[] bytesFileKeyStore = (new MImage(ctx, Integer.parseInt(idFileKeyStore), trxName)).getData();
		
		try{
			
			// Almacen de Claves
			Constants.setSignatureSpecNSprefix("");
			// Cargamos el almacen de claves
			KeyStore ks = KeyStore.getInstance("JKS");
			//ks.load(new FileInputStream("/tmp/firma/FirmaDigitalOpenup.pfx"), passKeyStore.toCharArray());
			ks.load(new ByteInputStream(bytesFileKeyStore, bytesFileKeyStore.length), passKeyStore.toCharArray());
			
			// Obtenemos la clave privada, pues la necesitaremos para encriptar.  
			PublicKey publicKey = null;
			String aliasEmpresa = "";
	        
			Enumeration aliasesEnum = ks.aliases();
			while (aliasesEnum.hasMoreElements()) {
				aliasEmpresa = (String) aliasesEnum.nextElement();
				if (ks.getKey(aliasEmpresa, passKeyStore.toCharArray()) != null) {
					publicKey = (PublicKey) ks.getCertificate(aliasEmpresa).getPublicKey();
					ret = new String(org.apache.commons.codec.binary.Base64.encodeBase64(ks.getCertificate(aliasEmpresa).getEncoded()));
					// store the private keys in an arraylist.
				}
			}
			// Fin Almacen de Claves		
			
		}catch(Exception ex){
			throw new AdempiereException("CFE Error: Area Caratula (Cert. Publico) - ex.getMessage(): " + ex.getMessage());
		}
		
		
		return ret;
		
	}
}
