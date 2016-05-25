package org.openup.cfe.provider.sisteco;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.exceptions.AdempiereException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.openup.cfe.provider.migrate.dto.EnvioCFERetorno;
import org.xml.sax.InputSource;

public class SistecoConvertResponse {

	public SistecoConvertResponse() {
		
	}
	
	public static SistecoResponseDTO getObjSistecoResponseDTO(String strEnvioCFERetorno) {
		SistecoResponseDTO ret = new SistecoResponseDTO();
		
		SAXBuilder builder = new SAXBuilder();
		
		try {
			Document document = (Document) builder.build( new InputSource( new StringReader( strEnvioCFERetorno )));
			Element rootNode = document.getRootElement();
			
			rootNode.getChildren();
			ret.setStatus(getIntegerVal(rootNode, "Status"));
			ret.setDescripcion(getStringVal(rootNode, "Descripcion"));
			ret.setTipoCFE(getIntegerVal(rootNode, "TipoCFE"));
			ret.setMro(getBigDecimalVal(rootNode, "Mro"));
			try {
				ret.setTmstFirma(new Timestamp(getXMLGregorianCalendar(rootNode, "TmstFirma").toGregorianCalendar().getTimeInMillis()));
			} catch (Exception e) { }
			ret.setDigestValue(getStringVal(rootNode, "DigestValue"));
			ret.setResolucion(getIntegerVal(rootNode, "Resolucion"));
			ret.setAnioResolucion(getIntegerVal(rootNode, "AnioResolucion"));
			ret.setUrlDocumentoDGI(getStringVal(rootNode, "UrlDocumentoDGI"));
			ret.setCaeId(getBigDecimalVal(rootNode, "CAE_ID"));
			ret.setdNro(getBigDecimalVal(rootNode, "DNro"));
			ret.sethNro(getBigDecimalVal(rootNode, "HNro"));
			try {
				ret.setFecVenc(new Timestamp(getXMLGregorianCalendar(rootNode, "FecVenc").toGregorianCalendar().getTimeInMillis()));
			} catch (Exception e) { }
			
		} catch (Exception e) {
			
		}
		//if (true) throw new AdempiereException("org.openup.cfe.provider.sisteco.SistecoConvertResponse: Cargar DTO");
		
		return ret;
	}

	private static Integer getIntegerVal(Element element, String name, Integer defaultValue) {
		Integer ret = getIntegerVal(element, name);
		return ret != null ? ret : defaultValue;
	}
	
	private static Integer getIntegerVal(Element element, String name) {
		String val = element.getChildText(name);
		Integer ret = null;
		try {
			ret = Integer.valueOf(val);
		} catch (Exception e) {
			ret = null;
		}
		
		return ret;
	}
	
	private static Long getLongVal(Element element, String name) {
		String val = element.getChildText(name);
		Long ret = null;
		try {
			ret = Long.valueOf(val);
		} catch (Exception e) {
			ret = null;
		}
		
		return ret;
	}

	private static BigDecimal getBigDecimalVal(Element element, String name) {
		String val = element.getChildText(name);
		BigDecimal ret = null;
		try {
			ret = BigDecimal.valueOf(Integer.valueOf(val));
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}
	
	private static BigInteger getBigIntegerVal(Element element, String name) {
		String val = element.getChildText(name);
		BigInteger ret = null;
		try {
			ret = BigInteger.valueOf(Integer.valueOf(val));
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}
	
	private static String getStringVal(Element element, String name) {
		return element.getChildText(name);
	}
	
	private static XMLGregorianCalendar getXMLGregorianCalendar(Element element, String name) {
		
		try {
			GregorianCalendar gcal = new GregorianCalendar();
			XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			
			xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(element.getChildText(name));
			
			return xgcal;
		} catch (Exception e) { }

		
		return null;
	}
	
}
