package org.openup.cfe.provider.migrate;

import org.openup.cfe.provider.migrate.dto.EncabezadoRetornoCFEType;
import org.openup.cfe.provider.migrate.dto.EnvioCFERetorno;
import org.openup.cfe.provider.migrate.dto.ListaCFERetornoType;
import org.openup.cfe.provider.migrate.dto.ListaCFERetornoType.CFE.CFEDatosAvanzados;
import org.openup.cfe.provider.migrate.dto.ListaCFERetornoType.CFE.Erros;
import org.openup.cfe.provider.migrate.dto.ListaCFERetornoType.CFE.Erros.ErrosItem;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jdom2.Document;         // |
import org.jdom2.Element;          // |\ Librerías
import org.jdom2.JDOMException;    // |/ JDOM
import org.jdom2.input.SAXBuilder; // |
import org.jruby.ast.RootNode;

public class MigrateConvertResponse {

	public MigrateConvertResponse() {
		
	}
	
	public static EnvioCFERetorno getObjEnvioCFERetorno(String strEnvioCFERetorno) {
		EnvioCFERetorno ret = new EnvioCFERetorno();
		strEnvioCFERetorno = strEnvioCFERetorno.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");

		//Se crea un SAXBuilder para poder parsear el archivo
		SAXBuilder builder = new SAXBuilder();

		try {
			
			
			Document document = (Document) builder.build( new InputSource( new StringReader( strEnvioCFERetorno )));
			Element rootNode = document.getRootElement();
			
			EncabezadoRetornoCFEType encabezado = new EncabezadoRetornoCFEType();
			ret.setEncabezado(encabezado);
			loadEncabezado( rootNode.getChild("Encabezado"), encabezado) ;
			
			ListaCFERetornoType cfeResponses = new ListaCFERetornoType();
			ret.setListaCFE(cfeResponses);
			loadCfeResponses(rootNode.getChild("ListaCFE").getChildren(), cfeResponses);
			

			List list = rootNode.getChildren( "EnvioCFERetorno" );
			//Se recorre la lista de hijos de 'tables'
			for ( int i = 0; i < list.size(); i++ ) {
				System.out.println(list.get(i));
			}
			System.out.println();
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return ret;
	}
	
	private static void loadEncabezado(Element encabezado, EncabezadoRetornoCFEType objEncabezado) {
		objEncabezado.setEmpCodigo(getStringVal(encabezado, "EmpCodigo"));
		objEncabezado.setNroLinRetorno(getBigIntegerVal(encabezado, "NroLinRetorno"));
		objEncabezado.setMsgCod(getIntegerVal(encabezado, "MsgCod"));
		objEncabezado.setMsgDsc(getStringVal(encabezado, "MsgDesc"));
	}
	
	private static void loadCfeResponses(List<Element> cfeResponses, ListaCFERetornoType objCfeResponses) {
		for (Element cfeResponse : cfeResponses) {
			ListaCFERetornoType.CFE objCfeResponse = new ListaCFERetornoType.CFE();
			objCfeResponses.getCFE().add(objCfeResponse);
			
			objCfeResponse.setCFETipo(getBigIntegerVal(cfeResponse, "CFETipo"));
			objCfeResponse.setCFESerie(getStringVal(cfeResponse, "CFESerie"));
			objCfeResponse.setCFENro(getBigIntegerVal(cfeResponse, "CFENro"));
			objCfeResponse.setCFEStatus(getBigIntegerVal(cfeResponse, "CFEStatus"));
			objCfeResponse.setCFEEstadoAcuse(getBigIntegerVal(cfeResponse, "CFEEstadoAcuse"));
			objCfeResponse.setCFEMsgCod(getIntegerVal(cfeResponse, "CFEMsgCod"));
			objCfeResponse.setCFEMsgDsc(getStringVal(cfeResponse, "CFEMsgDsc"));
			objCfeResponse.setCFERepImpressa(getStringVal(cfeResponse, "CFERepImpressa"));
			objCfeResponse.setCFENumReferencia(getBigIntegerVal(cfeResponse, "CFENumReferencia"));
			objCfeResponse.setCFECodigoSeguridad(getStringVal(cfeResponse, "CFECodigoSeguridad"));
			
			
			
			ListaCFERetornoType.CFE.CFEDatosAvanzados objCfeDatosAvanzados = new CFEDatosAvanzados();
			objCfeResponse.setCFEDatosAvanzados(objCfeDatosAvanzados);
			Element cfeDatosAvanzados = cfeResponse.getChild("CFEDatosAvanzados");
			
			if (cfeDatosAvanzados != null) {
				objCfeDatosAvanzados.setCFEHASH(getStringVal(cfeDatosAvanzados, "CFEHASH"));
				objCfeDatosAvanzados.setCFEFchHorFirma(getXMLGregorianCalendar(cfeDatosAvanzados, "CFEFchHorFirma"));
				objCfeDatosAvanzados.setCFECAEId(getLongVal(cfeDatosAvanzados, "CFECAEId"));
				objCfeDatosAvanzados.setCFECAENroIni(getBigIntegerVal(cfeDatosAvanzados, "CFECAENroIni"));
				objCfeDatosAvanzados.setCFECAENroFin(getBigIntegerVal(cfeDatosAvanzados, "CFECAENroFin"));
				objCfeDatosAvanzados.setCFECAEFchVenc(getXMLGregorianCalendar(cfeDatosAvanzados, "CFECAEFchVenc"));
				objCfeDatosAvanzados.setCFERUTEmisor(getStringVal(cfeDatosAvanzados, "CFERUTEmisor"));
				objCfeDatosAvanzados.setCFETotMntPagar(getBigDecimalVal(cfeDatosAvanzados, "CFETotMntPagar"));
				objCfeDatosAvanzados.setNumResAutorizadora(getBigIntegerVal(cfeDatosAvanzados, "NumResAutorizadora"));
				objCfeDatosAvanzados.setAnoResAutorizadora(getBigIntegerVal(cfeDatosAvanzados, "AnoResAutorizadora"));
				objCfeDatosAvanzados.setDireccionInvoiCyWeb(getStringVal(cfeDatosAvanzados, "DireccionInvoiCyWeb"));
				objCfeDatosAvanzados.setCodBarAbtPrimera(getStringVal(cfeDatosAvanzados, "CodBarAbtPrimera"));
				objCfeDatosAvanzados.setCodBarAbtSegunda(getStringVal(cfeDatosAvanzados, "CodBarAbtSegunda"));
			}
			
			
			objCfeResponse.setErros(new Erros());
			List<ErrosItem> objCfeErros = objCfeResponse.getErros().getErrosItem();
			for (Element cfeErros : cfeResponse.getChild("Erros").getChildren()) {
				ErrosItem objErrosItem = new ErrosItem();
				objCfeErros.add(objErrosItem);
				objErrosItem.setCFEErrCod(getIntegerVal(cfeErros, "CFEErrCod", 0));
				objErrosItem.setCFEErrDesc(getStringVal(cfeErros, "CFEErrDesc"));
			}
			
			
		}
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
