package org.openup.dgi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.exceptions.AdempiereException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.keys.content.X509Data;
import org.apache.xml.security.keys.content.x509.XMLX509IssuerSerial;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCountry;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MImage;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocation;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MProduct;
import org.compiere.model.MSequence;
import org.compiere.model.MTax;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.aduana.ConsultaRespuestaMic;
import org.openup.cfe.CFEMessages;
import org.openup.cfe.CfeUtils;
import org.openup.cfe.CfeUtils.CfeType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CAEDataType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType.EFact;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType.ETck;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.DscRcgGlobal;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.DscRcgGlobal.DRGItem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.IdDocFact;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.IdDocTck;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Emisor;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ItemDetFact;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ItemDetFact.CodItem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ReceptorFact;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ReceptorTck;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Referencia;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Referencia.Referencia1;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Totales;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.TipMonType;
import org.openup.model.MDepartamentos;
import org.openup.model.MDgiCae;
import org.openup.model.MLocalidades;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import org.apache.xml.security.keys.content.x509.XMLX509Certificate;

/**
 * org.openup.dgi - CfeConverter
 * OpenUp Ltda. Issue #4094 
 * Description: Clase encargada de mapear el objeto MInvoice al formato de clases dto para generar el XML.
 * Documento base: Formato de los CFE - Comprobante Fiscal Electronico - Version 13 26/05/2014 
 * @author Raul Capecce - 22/05/2015
 * @see
 */
public class CfeConverter {

	private String trxName;
	private Properties ctx;
	private MInvoice mInvoice;
	private CFEDefType objCfe;
	private String cfeStrFirmado;

	
	public MInvoice getmInvoice() {
		return mInvoice;
	}

	public CFEDefType getObjCfe() {
		return objCfe;
	}
	
	/***
	 * Constructor
	 * OpenUp Ltda. Issue #4094 
	 * @author Raul Capecce - 22/05/2015
	 * @param ctx
	 * @param mInvoice
	 * @param trxName
	 */
	public CfeConverter(Properties ctx, MInvoice mInvoice, String trxName){
		
		if (ctx == null) throw new AdempiereException("CFE Error: ctx null");
		if (mInvoice == null) throw new AdempiereException("CFE Error: mInvoice null");
		if (trxName == null) throw new AdempiereException("CFE Error: trxName null");
		
		this.ctx = ctx;
		this.mInvoice = mInvoice;
		this.trxName = trxName;
		
	}
	
	/***
	 * Valida y obtiene el tipo de CFE
	 * OpenUp Ltda. Issue #4094 
	 * @author Raul Capecce - 22/05/2015
	 */
	private CfeType getCFETypeFromInvoice(MInvoice mInvoice){
		CfeType ret = null;
		
		MDocType docType = (MDocType) mInvoice.getC_DocTypeTarget();
		boolean isCfe = docType.get_ValueAsBoolean("isCFE");
		String cfeType = docType.get_ValueAsString("cfeType");
		
		// Validaciones
		if (!isCfe) throw new AdempiereException(CFEMessages.IDDOC_002);
		if (!CfeUtils.getCfeTypes().containsKey(cfeType)) throw new AdempiereException(CFEMessages.IDDOC_002_2);
		
		ret = CfeUtils.getCfeTypes().get(cfeType);
		
		return ret;
	}
	
	/***
	 * Quita el acento a las vocales
	 * OpenUp Ltda. Issue #4094 
	 * @author Raul Capecce - 25/05/2015
	 * @param str
	 */
	private String replaceChr(String str) {
		return str
				.replace("á", "a")
				.replace("Á", "A")
				.replace("é", "e")
				.replace("É", "E")
				.replace("í", "i")
				.replace("Í", "I")
				.replace("ó", "o")
				.replace("Ó", "O")
				.replace("ú", "u")
				.replace("Ú", "U");
	}
	
	/**
	* Get lines from C_Invoice
	* @param whereClause starting with AND
	* @return lines
	*/
	private ArrayList<MInvoiceLine> getInvoiceLines (String whereClause){
		String whereClauseFinal = "C_Invoice_ID=?";
		if (whereClause != null) whereClauseFinal += whereClause;
		List<MInvoiceLine> list = new Query(ctx, I_C_InvoiceLine.Table_Name, whereClauseFinal, trxName)
		.setParameters(mInvoice.getC_Invoice_ID()).list();
		//return list.toArray(new MInvoiceLine[list.size()]);
		return new ArrayList<MInvoiceLine>(list);
	}
	
	/***
	 * Carga la informacion de la factura dependiendo del tipo de CFE especificado en el DocType
	 * OpenUp Ltda. Issue #4094 
	 * @author Raul Capecce - 22/05/2015
	 */
	public void loadCFE(){
		objCfe = new CFEDefType();
		CfeType cfeType = getCFETypeFromInvoice(mInvoice);
		
		switch (cfeType) {
		case eTicket:
			loadEncabezado_eTicket_eFactura(cfeType, objCfe);
			loadDetalleProductosOServicios_eTicket_eFactura(cfeType, objCfe);
			loadInformacionDeDescuentosORecargos_eTicket_eFactura(cfeType, objCfe);
			loadInfoReferencia_eTicket_eFactura(cfeType, objCfe);
			break;
		case eTicket_NC:
			loadEncabezado_eTicket_eFactura(cfeType, objCfe);
			loadDetalleProductosOServicios_eTicket_eFactura(cfeType, objCfe);
			loadInformacionDeDescuentosORecargos_eTicket_eFactura(cfeType, objCfe);
			loadInfoReferencia_eTicket_eFactura(cfeType, objCfe);
			break;
		case eTicket_ND:
			loadEncabezado_eTicket_eFactura(cfeType, objCfe);
			loadDetalleProductosOServicios_eTicket_eFactura(cfeType, objCfe);
			loadInformacionDeDescuentosORecargos_eTicket_eFactura(cfeType, objCfe);
			loadInfoReferencia_eTicket_eFactura(cfeType, objCfe);
			break;
		case eFactura:
			loadEncabezado_eTicket_eFactura(cfeType, objCfe);
			loadDetalleProductosOServicios_eTicket_eFactura(cfeType, objCfe);
			loadInformacionDeDescuentosORecargos_eTicket_eFactura(cfeType, objCfe);
			loadInfoReferencia_eTicket_eFactura(cfeType, objCfe);
			break;
		case eFactura_NC:
			loadEncabezado_eTicket_eFactura(cfeType, objCfe);
			loadDetalleProductosOServicios_eTicket_eFactura(cfeType, objCfe);
			loadInformacionDeDescuentosORecargos_eTicket_eFactura(cfeType, objCfe);
			loadInfoReferencia_eTicket_eFactura(cfeType, objCfe);
			break;
		case eFactura_ND:
			loadEncabezado_eTicket_eFactura(cfeType, objCfe);
			loadDetalleProductosOServicios_eTicket_eFactura(cfeType, objCfe);
			loadInformacionDeDescuentosORecargos_eTicket_eFactura(cfeType, objCfe);
			loadInfoReferencia_eTicket_eFactura(cfeType, objCfe);
			break;
		}
		
		
		loadCAE(cfeType, objCfe);
		loadTimestamp(cfeType, objCfe);
		//firmar(cfeType, objCfe);
		
		
	}
	
	/***
	 * Carga la informacion del cabezal para los siguientes CFE (comparten la misma información):
	 *     eTicket
	 *     eTicket con Nota de Credito
	 *     eTicket con Nota de Debito
	 *     eFactura
	 *     eFactura con Nota de Credito
	 *     eFactura con Nota de Debito
	 * 
	 * Pagina 13-24: A - Encabezado
	 * 
	 * OpenUp Ltda. Issue #4094 
	 * @author Raul Capecce - 22/05/2015
	 */
	private void loadEncabezado_eTicket_eFactura(CfeType cfeType, CFEDefType objCfe) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		ETck eTicket = new ETck();
		EFact eFactura = new EFact();
		ETck.Encabezado eTicketEncabezado = new ETck.Encabezado();
		EFact.Encabezado eFactEncabezado = new EFact.Encabezado();
		IdDocTck idDocTck = new IdDocTck();
		IdDocFact idDocFact = new IdDocFact();
		Emisor emisor = new Emisor();
		ReceptorTck receptorTck = new ReceptorTck();
		ReceptorFact receptorFact = new ReceptorFact();
		Totales totales = new Totales();
		
		if (cfeType == CfeType.eTicket || cfeType == CfeType.eTicket_ND || cfeType == CfeType.eTicket_NC){
			objCfe.setETck(eTicket);
			eTicket.setEncabezado(eTicketEncabezado);
			eTicketEncabezado.setIdDoc(idDocTck);
			eTicketEncabezado.setEmisor(emisor);
			eTicketEncabezado.setReceptor(receptorTck);
			eTicketEncabezado.setTotales(totales);
		} else if (cfeType == CfeType.eFactura || cfeType == CfeType.eFactura_ND || cfeType == CfeType.eFactura_NC){
			objCfe.setEFact(eFactura);
			eFactura.setEncabezado(eFactEncabezado);
			eFactEncabezado.setIdDoc(idDocFact);
			eFactEncabezado.setEmisor(emisor);
			eFactEncabezado.setReceptor(receptorFact);
			eFactEncabezado.setTotales(totales);
		}
		
		objCfe.setVersion("1.0");
		
		//  AREA: Identificacion del Comprobante
		
		MDocType doc = MDocType.get(ctx, mInvoice.getC_DocType_ID());
		BigInteger doctype = new BigInteger(doc.get_Value("CfeType").toString());
		/* 2   */ idDocTck.setTipoCFE(doctype);
		/* 2   */ idDocFact.setTipoCFE(doctype);
		
		MSequence sec = new MSequence(ctx, doc.getDefiniteSequence_ID(), trxName);
		if(sec.getPrefix() != null){
			/* 3   */ idDocTck.setSerie(sec.getPrefix());
			/* 3   */ idDocFact.setSerie(sec.getPrefix());
		} else {
			throw new AdempiereException(CFEMessages.IDDOC_003);
		}
		
		if(mInvoice.getDocumentNo() != null){ // Se obtiene nro de cae directamente del documentNo
			// Se quita serie del número para enviar
			String documentNo = mInvoice.getDocumentNo();
			documentNo = documentNo.replaceAll("[^0-9]", ""); // Expresión regular para quitar todo lo que no es número
			
			String docno = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(documentNo), 7, "0");
			BigInteger numero = new BigInteger(docno);
			/* 4   */ idDocTck.setNro(numero);// Número de CFE 7 digitos
			/* 4   */ idDocFact.setNro(numero);
		}
		else throw new AdempiereException(CFEMessages.IDDOC_004);
		
		if (mInvoice.getDateInvoiced() != null){
			/* 5   */ idDocTck.setFchEmis(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mInvoice.getDateInvoiced(), false));
			/* 5   */ idDocFact.setFchEmis(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mInvoice.getDateInvoiced(), false));
			
		} else {
			throw new AdempiereException(CFEMessages.IDDOC_005);
		}
		/* 6    - No Corresponde */ 
		/* 7   - Tipo de obligatoriedad 3 (dato opcional)*/ //idDocTck.setPeriodoDesde(null);
		/* 7   - Tipo de obligatoriedad 3 (dato opcional)*/ //idDocFact.setPeriodoDesde(null);
		/* 8   - Tipo de obligatoriedad 3 (dato opcional)*/ //idDocTck.setPeriodoHasta(null); 
		/* 8   - Tipo de obligatoriedad 3 (dato opcional)*/ //idDocFact.setPeriodoHasta(null);
		/* 10  */ idDocTck.setMntBruto(new BigInteger("0"));
		/* 10  */ idDocFact.setMntBruto(new BigInteger("0"));
		
		if(mInvoice.getpaymentruletype().equalsIgnoreCase("CO")){
			/* 11  */ idDocTck.setFmaPago(BigInteger.valueOf(1));
			/* 11  */ idDocFact.setFmaPago(BigInteger.valueOf(1));
		} else if(mInvoice.getpaymentruletype().equalsIgnoreCase("CR")){
			/* 11  */ idDocTck.setFmaPago(BigInteger.valueOf(2));
			/* 11  */ idDocFact.setFmaPago(BigInteger.valueOf(2));
		} else {
			throw new AdempiereException(CFEMessages.IDDOC_011);
		}
		
		if (mInvoice.getDueDate() != null) {		
			/* 12  */ idDocTck.setFchVenc(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mInvoice.getDueDate(), false));
			/* 12  */ idDocFact.setFchVenc(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mInvoice.getDueDate(), false));
		}
		
		/* 13   - No Corresponde */
		/* 14   - No Corresponde */
		/* 15   - No Corresponde */
		
		
		//  AREA: Emisor
		
		MOrgInfo orgInfo = MOrgInfo.get(ctx, mInvoice.getAD_Org_ID(), trxName);
		if (orgInfo == null) throw new AdempiereException(CFEMessages.EMISOR_ORG);

		if (orgInfo.getDUNS() == null) throw new AdempiereException(CFEMessages.EMISOR_040);
		/* 40  */ emisor.setRUCEmisor(orgInfo.getDUNS());
		
		if (orgInfo.getrznsoc() == null) throw new AdempiereException(CFEMessages.EMISOR_041);
		/* 41  */ emisor.setRznSoc(orgInfo.getrznsoc());
		MOrg mOrg = MOrg.get(ctx, orgInfo.getAD_Org_ID());
		if (mOrg != null && mOrg.getName() != null) { 
			/* 42  */ emisor.setNomComercial(MOrg.get(ctx, orgInfo.getAD_Org_ID()).getName());
		}
		/* 43  */ emisor.setGiroEmis(orgInfo.getgirotype());
		/* 44   - Tipo de obligatoriedad 3 (dato opcional) */
		/* 45  */ emisor.setCorreoEmisor(orgInfo.getEMail());
		MWarehouse casa = MWarehouse.get(ctx, orgInfo.getDropShip_Warehouse_ID());
		/* 46  */ emisor.setEmiSucursal(casa.getName()); 
		try {
			/* 47  */ emisor.setCdgDGISucur(BigInteger.valueOf(Long.valueOf(orgInfo.get_ValueAsString("TaxNo"))));
		}catch(Exception ex){
			throw new AdempiereException(CFEMessages.EMISOR_047);
		}
			
		MLocation mLocation = (MLocation) orgInfo.getC_Location();
		if (mLocation == null || mLocation.getAddress1() == null) throw new AdempiereException(CFEMessages.EMISOR_048); 
		/* 48  */ emisor.setDomFiscal(mLocation.getAddress1());
		MLocalidades mLocalidades = (MLocalidades) mLocation.getUY_Localidades();
		if (mLocalidades == null || mLocalidades.getName() == null) throw new AdempiereException(CFEMessages.EMISOR_049);
		/* 49  */ emisor.setCiudad(mLocalidades.getName());
		MDepartamentos mDepartamentos = (MDepartamentos) mLocation.getUY_Departamentos();
		if (mDepartamentos == null || mDepartamentos.getName() == null) throw new AdempiereException(CFEMessages.EMISOR_050);
		/* 50  */ emisor.setDepartamento(mDepartamentos.getName());
		
		
		//  Area: Receptor
		
		MBPartner partner =  MBPartner.get(ctx, mInvoice.getC_BPartner_ID());
		MBPartnerLocation partnerLocation = new MBPartnerLocation(ctx, mInvoice.getC_BPartner_Location_ID(), trxName);
		
		// OpenUp Ltda - #5627 - Raul Capecce - Si es una factura (o NC o ND) es obligatorio que tenga RUT 
		if (
				cfeType.equals(CfeType.eFactura)
				|| cfeType.equals(CfeType.eFactura_NC)
				|| cfeType.equals(CfeType.eFactura_ND)
				) {
			if (
					(partner.getDUNS() == null || partner.getDUNS().equalsIgnoreCase(""))
					|| (!partner.getDocumentType().equalsIgnoreCase(MBPartner.DOCUMENTTYPE_RUT))
					) {
				throw new AdempiereException(CFEMessages.RECEPTOR_FACTNORUT);
			}
		}
		// FIN - OpenUp Ltda - #5627
		
		int tipoDocRecep = 0;
		String docRecep = null;
		if (partner.getDUNS() != null) {
			if (partner.getDocumentType().equalsIgnoreCase(MBPartner.DOCUMENTTYPE_RUT)) {
				tipoDocRecep = 2;
			} else {
				tipoDocRecep = 4;
			}
			docRecep = partner.getDUNS();
		} else if (partner.get_Value("cedula") != null) {
			tipoDocRecep = 3;
			docRecep = partner.get_Value("cedula").toString();
		} else {
			tipoDocRecep = 4;
			docRecep = CFEMessages.RECEPTOR_NODOC.replace("{{documentNo}}", mInvoice.getDocumentNo());
		}
		/* 60  */ receptorTck.setTipoDocRecep(tipoDocRecep);
		/* 60  */ receptorFact.setTipoDocRecep(tipoDocRecep);
		MCountry mCountry = null;
		try {
			mCountry = MCountry.get(ctx, Integer.valueOf(partnerLocation.get_Value("C_Country_ID").toString()));
		} catch (Exception e) {
			throw new AdempiereException(CFEMessages.RECEPTOR_61);
		}
		if (mCountry == null) throw new AdempiereException(CFEMessages.RECEPTOR_61);
		/* 61  */ receptorTck.setCodPaisRecep(mCountry.getCountryCode());
		/* 61  */ receptorFact.setCodPaisRecep(mCountry.getCountryCode());
		
		if (tipoDocRecep == 2 || tipoDocRecep == 3) {
			/* 62  */ receptorTck.setDocRecep(docRecep);
			/* 62  */ receptorFact.setDocRecep(docRecep);
		} else if (tipoDocRecep == 4 || tipoDocRecep == 5 || tipoDocRecep == 6) {
			/* 62.1*/ receptorTck.setDocRecepExt(docRecep);
			/* 62.1*/ receptorFact.setDocRecep(docRecep);
		}
		
		/* 63  */ receptorTck.setRznSocRecep(partner.getName2());
		/* 63  */ receptorFact.setRznSocRecep(partner.getName2());
		String dirRecep = null;
		if (partnerLocation.getAddress1() != null) {
			if (partnerLocation.getAddress1().length() <= 70)
				dirRecep = partnerLocation.getAddress1();
			else
				dirRecep = partnerLocation.getAddress1().substring(0, 70);
		}
		/* 64  */ receptorTck.setDirRecep(dirRecep);
		/* 64  */ receptorFact.setDirRecep(dirRecep);
		/* 65  */ receptorTck.setCiudadRecep(partnerLocation.getUY_Localidades().getName());
		/* 65  */ receptorFact.setCiudadRecep(partnerLocation.getUY_Localidades().getName());
		/* 66  */ receptorTck.setDeptoRecep(partnerLocation.getUY_Departamentos().getName());
		/* 66  */ receptorFact.setDeptoRecep(partnerLocation.getUY_Departamentos().getName());
		/* 66.1*/ receptorTck.setPaisRecep(partnerLocation.getC_Country().getName());
		/* 66.1*/ receptorFact.setPaisRecep(partnerLocation.getC_Country().getName());
		try {
			/* 67  */ receptorTck.setCP(Integer.valueOf(partnerLocation.getUY_Localidades().getzipcode()));
			/* 67  */ receptorFact.setCP(Integer.valueOf(partnerLocation.getUY_Localidades().getzipcode()));
		} catch (Exception ex) { }
		
		
		//  Area: Totales Encabezado
		
		MCurrency mCurrency = (MCurrency) mInvoice.getC_Currency();
		if (mCurrency.getISO_Code() == null) throw new AdempiereException(CFEMessages.TOTALES_110);
		try {
			/* 110 */ totales.setTpoMoneda(TipMonType.valueOf(mCurrency.getISO_Code()));
			if (mCurrency.getC_Currency_ID() != 142) {
				if (MConversionRate.getDivideRate(mCurrency.getC_Currency_ID(), 142, TimeUtil.trunc(mInvoice.getDateInvoiced(), TimeUtil.TRUNC_DAY), 0, mInvoice.getAD_Client_ID(), mInvoice.getAD_Org_ID()) == Env.ZERO) throw new AdempiereException("CFE Error: Area Totales Encabezado (111) - Tasa de cambio no establecida para la factura de la fecha " + mInvoice.getDateInvoiced() + " para la moneda " + mCurrency.getISO_Code());
				/* 111 */ totales.setTpoCambio(MConversionRate.getDivideRate(mCurrency.getC_Currency_ID(), 142, TimeUtil.trunc(mInvoice.getDateInvoiced(), TimeUtil.TRUNC_DAY), 0, mInvoice.getAD_Client_ID(), mInvoice.getAD_Org_ID()).setScale(3, BigDecimal.ROUND_HALF_UP));
			}
		} catch (AdempiereException ex){
			throw ex;
		} catch (Exception ex){
			throw new AdempiereException(CFEMessages.TOTALES_110_2);			
		}
		
		
		
		// Inicializando Variables - Se toma como referencia codigo establecido para Migrate
		/* 112 */ totales.setMntNoGrv(Env.ZERO);
		/* 113 - No establecido */ totales.setMntExpoyAsim(Env.ZERO);
		/* 114 - No establecido */ totales.setMntImpuestoPerc(Env.ZERO);
		/* 115 - No aparece en código de Migrate */
		/* 116 */ totales.setMntNetoIvaTasaMin(Env.ZERO);
		/* 117 */ totales.setMntNetoIVATasaBasica(Env.ZERO);
		/* 118 */ totales.setMntNetoIVAOtra(Env.ZERO);
		
		/* 121 */ totales.setMntIVATasaMin(Env.ZERO);
		/* 122 */ totales.setMntIVATasaBasica(Env.ZERO);
		/* 123 */ totales.setMntIVAOtra(Env.ZERO);
		
		ArrayList<MInvoiceLine> invoiceLines = getInvoiceLines("");
		for (MInvoiceLine mInvL : invoiceLines){
			MTax tax = MTax.get(ctx, mInvL.getC_Tax_ID());
			if(tax == null) throw new AdempiereException("CFE Error: Area Totales Encabezado - Impuesto para linea no establecido");
			if(tax.getTaxIndicator() == null || tax.getTaxIndicator().equalsIgnoreCase("")) throw new AdempiereException("CFE Error: Area Totales Encabezado - Porcentaje de impuesto para la linea no establecido"); 
			
			// OpenUp Ltda. Raúl Capecce #5270 - 22/02/2016
			BigDecimal amtNacional = null;
			BigDecimal amtInternacional = null;
			
			try {
				amtNacional = BigDecimal.valueOf(Double.valueOf(mInvL.get_ValueAsString("NationalAmt")));
				amtInternacional = BigDecimal.valueOf(Double.valueOf(mInvL.get_ValueAsString("InternationalAmt")));
			} catch (Exception e) {
				amtNacional = Env.ZERO;
				amtInternacional = Env.ZERO;
			}
			
			if (amtInternacional.equals(Env.ZERO)) {
				// Si es una linea normal (no tiene monto internacional) se aplica el cálculo de IVA al total de la misma
				
				if (tax.getValue().equalsIgnoreCase("exento")){
					/* 112 */ totales.setMntNoGrv(totales.getMntNoGrv().add(mInvL.getLineTotalAmt()));
				} else if (tax.getValue().equalsIgnoreCase("minimo")){		
					/* 116 */ totales.setMntNetoIvaTasaMin(totales.getMntNetoIvaTasaMin().add(mInvL.getLineNetAmt()));	
					/* 121 */ totales.setMntIVATasaMin(totales.getMntIVATasaMin().add(mInvL.getTaxAmt()));
				} else if(tax.getValue().equalsIgnoreCase("basico")){				
					/* 117 */ totales.setMntNetoIVATasaBasica(totales.getMntNetoIVATasaBasica().add(mInvL.getLineNetAmt()));	
					/* 122 */ totales.setMntIVATasaBasica(totales.getMntIVATasaBasica().add(mInvL.getTaxAmt()));
				} else if(tax.getValue().equalsIgnoreCase("expoasi")){
					/* 113 */ totales.setMntExpoyAsim(totales.getMntExpoyAsim().add(mInvL.getLineTotalAmt()));
				} else{
					/* 118 */ totales.setMntNetoIVAOtra(totales.getMntNetoIVAOtra().add(mInvL.getLineNetAmt()));
					/* 123 */ totales.setMntIVAOtra(totales.getMntIVAOtra().add(mInvL.getTaxAmt()));
				}
			} else {
				// En caso de que sea por ejemplo un flete, se separa en dos lineas para poder sumar
				// el impuesto en territorio nacional y la linea exenta en territorio extranjero
				
				BigDecimal taxIndicator = BigDecimal.valueOf(Double.valueOf(tax.getTaxIndicator().replace("%", "")));
				

				// Monto Nacional
				BigDecimal amtTotNacional = amtNacional;
				BigDecimal amtIvaNacional = amtNacional.multiply(taxIndicator).divide(Env.ONEHUNDRED);

				if (tax.getValue().equalsIgnoreCase("exento")){
					/* 112 */ totales.setMntNoGrv(totales.getMntNoGrv().add(amtTotNacional));
				} else if (tax.getValue().equalsIgnoreCase("minimo")){		
					/* 116 */ totales.setMntNetoIvaTasaMin(totales.getMntNetoIvaTasaMin().add(amtTotNacional));	
					/* 121 */ totales.setMntIVATasaMin(totales.getMntIVATasaMin().add(amtIvaNacional));
				} else if(tax.getValue().equalsIgnoreCase("basico")){				
					/* 117 */ totales.setMntNetoIVATasaBasica(totales.getMntNetoIVATasaBasica().add(amtTotNacional));	
					/* 122 */ totales.setMntIVATasaBasica(totales.getMntIVATasaBasica().add(amtIvaNacional));
				} else if(tax.getValue().equalsIgnoreCase("expoasi")){
					/* 113 */ totales.setMntExpoyAsim(totales.getMntExpoyAsim().add(amtTotNacional));
				} else{
					/* 118 */ totales.setMntNetoIVAOtra(totales.getMntNetoIVAOtra().add(amtTotNacional));
					/* 123 */ totales.setMntIVAOtra(totales.getMntIVAOtra().add(amtIvaNacional));
				}
				
				// Monto Internacional
				/* 112 */ totales.setMntExpoyAsim(totales.getMntExpoyAsim().add(amtInternacional));
				
			}
			// Fin #5270
		}
		
		/* 119 */ totales.setIVATasaMin(MTax.forValue(ctx, "minimo", trxName).getRate().setScale(3));
		/* 120 */ totales.setIVATasaBasica(MTax.forValue(ctx, "basico", trxName).getRate().setScale(3));
		
		// Validacion de Montos Totals
//		BigDecimal validacionTotal = validacionTotal(totales);
//		if (mInvoice.getGrandTotal().setScale(1, RoundingMode.HALF_UP).compareTo(validacionTotal.setScale(1, RoundingMode.HALF_UP)) == 0){
			/* 124 */ totales.setMntTotal(mInvoice.getGrandTotal());// Total Monto Total
//		} else {
//			throw new AdempiereException("CFE Error: Area Totales Encabezado (124) - Error al validar Total Monto Total");
//		}
		
		// Validacion de Total a Pagar
		BigDecimal validacionPagar = validacionPagar(totales);
		if(mInvoice.getGrandTotal().compareTo(validacionPagar) == 0){
			totales.setMntPagar(mInvoice.getGrandTotal());// Monto total a pagar
		} else {
			throw new AdempiereException("error en validar Monto total a pagar");
		}
		
		// C126 - Validar que no exeda la cantidad de lineas admitida por cada tipo de CFE
		// eTicket (solo y con NC y ND): <= 700
		// Otros CFE: <= 200
		if (cfeType == CfeType.eTicket || cfeType == CfeType.eTicket_NC || cfeType == CfeType.eTicket_ND){
			if (invoiceLines.size() > 700) throw new AdempiereException(CFEMessages.TOTALES_126);
		} else {
			if (invoiceLines.size() > 200) throw new AdempiereException(CFEMessages.TOTALES_126_2);
		}
		totales.setCantLinDet(invoiceLines.size());
		
		/* 127 */ // TODO: Consultar con Marianela
		/* 128 */ // TODO: Consultar con Marianela
		
		/* 129 */ // TODO: Depende de los dos anteriores
		/* 130 */ // TODO: Depende de los dos anteriores
	}
	
	
	/***
	 * Carga la informacion de las lineas para los siguientes CFE (comparten la misma información):
	 *     eTicket
	 *     eTicket con Nota de Credito
	 *     eTicket con Nota de Debito
	 *     eFactura
	 *     eFactura con Nota de Credito
	 *     eFactura con Nota de Debito
	 * 
	 * Pagina 25-30: B - Detalle de Productos o Servicios
	 * 
	 * OpenUp Ltda. Issue #4094 
	 * @author Raul Capecce - 27/05/2015
	 */
	private void loadDetalleProductosOServicios_eTicket_eFactura(CfeType cfeType, CFEDefType objCfe) {
		
		
		ArrayList<MInvoiceLine> mInvoiceLines = getInvoiceLines("");
		List<ItemDetFact> lineas = null;
		
		
		if (cfeType == CfeType.eTicket || cfeType == CfeType.eTicket_ND || cfeType == CfeType.eTicket_NC){
			objCfe.getETck().setDetalle(new ETck.Detalle());
			lineas = objCfe.getETck().getDetalle().getItem();
		} else if (cfeType == CfeType.eFactura || cfeType == CfeType.eFactura_ND || cfeType == CfeType.eFactura_NC){
			objCfe.getEFact().setDetalle(new EFact.Detalle());
			lineas = objCfe.getEFact().getDetalle().getItem();
		}
		
		int position = 1;
		for (MInvoiceLine mInvoiceLine : mInvoiceLines){

			// OpenUp Ltda. Raúl Capecce #5270 - 22/02/2016
			BigDecimal amtNacional = null;
			BigDecimal amtInternacional = null;
			double porcentajeNacional = 0;
			double porcentajeInternacional = 0;
			boolean calcularImpuestosEnDosLineas = false;
			BigDecimal amtMntLinea = null;
			
			try {
				amtNacional = BigDecimal.valueOf(Double.valueOf(mInvoiceLine.get_ValueAsString("NationalAmt")));
			} catch (Exception e) {
				amtNacional = Env.ZERO;
			}
			try {
				amtInternacional = BigDecimal.valueOf(Double.valueOf(mInvoiceLine.get_ValueAsString("InternationalAmt")));
			} catch (Exception e) {
				amtInternacional = Env.ZERO;
			}
			try {
				porcentajeNacional = Double.valueOf(mInvoiceLine.get_ValueAsString("NationalPercentage"));
			} catch (Exception e) {
				porcentajeNacional = 0;
			}
			try {
				porcentajeInternacional = Double.valueOf(mInvoiceLine.get_ValueAsString("InterPercentage"));
			} catch (Exception e) {
				porcentajeInternacional = 0;
			}
			
			if (!amtInternacional.equals(Env.ZERO)) calcularImpuestosEnDosLineas = true;
			
			if (!calcularImpuestosEnDosLineas) {
				
				ItemDetFact detalleItem = new ItemDetFact();
				lineas.add(detalleItem);
				
				/* 1  */ detalleItem.setNroLinDet(position ++);
				MProduct mProduct = new MProduct(ctx, mInvoiceLine.getM_Product_ID(), trxName);
				CodItem codItem = null;
				
				// Codigo interno del Cliente
				if (mProduct.getValue() != null){
					codItem = new CodItem(); 
					/* 2  */ codItem.setTpoCod("INT1");
					/* 3  */ codItem.setCod(mProduct.getValue());
					detalleItem.getCodItem().add(codItem);
				}
				
				// Codigo EAN
				if (mProduct.getUPC() != null){
					codItem = new CodItem();
					/* 2  */ codItem.setTpoCod("EAN");
					/* 3  */ codItem.setCod(mProduct.getUPC());
					detalleItem.getCodItem().add(codItem);
				}
				
				MTax tax = new MTax(ctx, mInvoiceLine.getC_Tax_ID(), trxName);
				if(tax.getValue().equalsIgnoreCase("minimo")){
					/* 4  */ detalleItem.setIndFact(BigInteger.valueOf(2));
				} else if(tax.getValue().equalsIgnoreCase("basico")){
					/* 4  */ detalleItem.setIndFact(BigInteger.valueOf(3));
				} else if(tax.getValue().equalsIgnoreCase("exento")){
					/* 4  */ detalleItem.setIndFact(BigInteger.valueOf(1));
				} else if(tax.getValue().equalsIgnoreCase("expoasi")){
					/* 4  */ detalleItem.setIndFact(BigInteger.valueOf(1));
				} else {
					throw new AdempiereException(CFEMessages.DETALLE_004.replace("{{documentNo}}", mInvoice.getDocumentNo()));
				}
				
				/* 6  */ detalleItem.setIndAgenteResp(null);
				/* 7  */ detalleItem.setNomItem(mInvoiceLine.getProduct().getName());
				/* 8  */ detalleItem.setDscItem(mInvoiceLine.getProduct().getDescription());
				/* 9  */ detalleItem.setCantidad(mInvoiceLine.getQtyInvoiced());
				/* 10 */ detalleItem.setUniMed(mInvoiceLine.getProduct().getUOMSymbol());
				/* 11 */ detalleItem.setPrecioUnitario(mInvoiceLine.getPriceActual().setScale(2, RoundingMode.HALF_UP));
				
	
				if(mInvoiceLine.getFlatDiscount() != null && mInvoiceLine.getFlatDiscount().compareTo(Env.ZERO) > 0){
					BigDecimal descuento = mInvoiceLine.getPriceActual().subtract(mInvoiceLine.getPriceEntered());
					/* 12 */ detalleItem.setDescuentoPct(mInvoiceLine.getFlatDiscount());
					/* 13 */ detalleItem.setDescuentoMonto(descuento);
				}
				
				/* 14 - Tipo de obligatoriedad de tabla 3 (tabla opcional) */
				/* 15 - Tipo de obligatoriedad de tabla 3 (tabla opcional) */
				
				
				/* TODO: NOTA IMPORTANTE: RECARGO POR LINEA - campos 16 y 17 se marcan en 0 teniendose en cuenta
				 * de que no se aplicaran recargos por linea.
				 * Tener en cuenta que para agregar impuestos a la linea, falta agregar
				 * la columna de impuesto a la C_InvoiceLine (o manejar un descuento negativo).
				 */
				/* 16 */ detalleItem.setRecargoPct(Env.ZERO);
				/* 17 */ detalleItem.setRecargoMnt(Env.ZERO);
				
				/* 18 - Tipo de obligatoriedad de tabla 3 (tabla opcional) */
				/* 19 - Tipo de obligatoriedad de tabla 3 (tabla opcional) */
				
				
				/* TODO: CAMPOS 20, 21, 22 y 23 se dejan sin cargar
				 * al igual que los campos 127 y 128 del encabezado
				 */
				
				/* 24 - C24=(C9*C11)-C13+C17 */
				BigDecimal montoTotalLinea = Env.ZERO;
				montoTotalLinea = montoTotalLinea.add(mInvoiceLine.getQtyInvoiced());
				montoTotalLinea = montoTotalLinea.multiply(mInvoiceLine.getPriceActual().setScale(2, RoundingMode.HALF_UP));
				if(mInvoiceLine.getFlatDiscount() != null && mInvoiceLine.getFlatDiscount().compareTo(Env.ZERO) > 0){
					montoTotalLinea = montoTotalLinea.subtract(mInvoiceLine.getPriceActual().subtract(mInvoiceLine.getPriceEntered()));
				}
				montoTotalLinea = montoTotalLinea.add(detalleItem.getRecargoMnt()); // Aun no se toma en cuenta, por lo tanto mas arriba se setea en 0
				/* 24 */ detalleItem.setMontoItem(montoTotalLinea);
				
			} else {
				ItemDetFact detalleItemNacional = new ItemDetFact();
				ItemDetFact detalleItemInternacional = new ItemDetFact();
				
				if (porcentajeNacional > 0) {
					lineas.add(detalleItemNacional);
				}
				if (porcentajeInternacional > 0) {
					lineas.add(detalleItemInternacional);
				}
				
				/* 1  */ detalleItemNacional.setNroLinDet(position ++);
				/* 1  */ detalleItemInternacional.setNroLinDet(position ++);
				
				MProduct mProduct = new MProduct(ctx, mInvoiceLine.getM_Product_ID(), trxName);
				CodItem codItem = null;
				
				// Codigo interno del Cliente
				if (mProduct.getValue() != null){
					codItem = new CodItem(); 
					/* 2  */ codItem.setTpoCod("INT1");
					/* 3  */ codItem.setCod(mProduct.getValue());
					detalleItemNacional.getCodItem().add(codItem);
					detalleItemInternacional.getCodItem().add(codItem);
				}
				
				// Codigo EAN
				if (mProduct.getUPC() != null){
					codItem = new CodItem();
					/* 2  */ codItem.setTpoCod("EAN");
					/* 3  */ codItem.setCod(mProduct.getUPC());
					detalleItemNacional.getCodItem().add(codItem);
					detalleItemInternacional.getCodItem().add(codItem);
				}
				

				MTax tax = new MTax(ctx, mInvoiceLine.getC_Tax_ID(), trxName);
				BigDecimal taxPorcentaje = BigDecimal.valueOf(Double.valueOf(tax.getTaxIndicator().replace("%", "")));
				
				if(tax.getValue().equalsIgnoreCase("minimo")){
					/* 4  */ detalleItemNacional.setIndFact(BigInteger.valueOf(2));
				} else if(tax.getValue().equalsIgnoreCase("basico")){
					/* 4  */ detalleItemNacional.setIndFact(BigInteger.valueOf(3));
				} else if(tax.getValue().equalsIgnoreCase("exento")){
					/* 4  */ detalleItemNacional.setIndFact(BigInteger.valueOf(1));
				} else if(tax.getValue().equalsIgnoreCase("expoasi")){
					/* 4  */ detalleItemNacional.setIndFact(BigInteger.valueOf(10));
				} else {
					throw new AdempiereException("CFE Error: Area Detalle de Productos o Servicios - Tasa en linea de invoice nº " + mInvoice.getDocumentNo() + " no valida");
				}
				detalleItemInternacional.setIndFact(BigInteger.valueOf(10)); // Exportación y asimiladas: Es con impuesto 0 pero según Nicolas Gamarra, se debe contemplar este cuando es
				

				/* 6  */ detalleItemNacional.setIndAgenteResp(null);
				/* 6  */ detalleItemInternacional.setIndAgenteResp(null);
				/* 7  */ detalleItemNacional.setNomItem(mInvoiceLine.getProduct().getName());
				/* 7  */ detalleItemInternacional.setNomItem(mInvoiceLine.getProduct().getName());
				/* 8  */ detalleItemNacional.setDscItem(mInvoiceLine.getProduct().getDescription());
				/* 8  */ detalleItemInternacional.setDscItem(mInvoiceLine.getProduct().getDescription());
				/* 9  */ detalleItemNacional.setCantidad(BigDecimal.valueOf(1));
				/* 9  */ detalleItemInternacional.setCantidad(BigDecimal.valueOf(1));
				/* 10 */ detalleItemNacional.setUniMed(mInvoiceLine.getProduct().getUOMSymbol());
				/* 10 */ detalleItemInternacional.setUniMed(mInvoiceLine.getProduct().getUOMSymbol());
				/* 11 */ detalleItemNacional.setPrecioUnitario(amtNacional);
				/* 11 */ detalleItemInternacional.setPrecioUnitario(amtInternacional);
				
				
				/* 24 */ detalleItemNacional.setMontoItem(amtNacional);
				/* 24 */ detalleItemInternacional.setMontoItem(amtInternacional);
				
			}
		}
		
		
	}
	
	/***
	 * Carga la informacion de descuentos o recargos para los siguientes CFE (comparten la misma información):
	 *     eTicket
	 *     eTicket con Nota de Credito
	 *     eTicket con Nota de Debito
	 *     eFactura
	 *     eFactura con Nota de Credito
	 *     eFactura con Nota de Debito
	 * 
	 * Pagina 32-33: D - Informacion de Descuentos o Recargos
	 * 
	 * OpenUp Ltda. Issue #4094 
	 * @author Raul Capecce - 28/05/2015
	 */
	private void loadInformacionDeDescuentosORecargos_eTicket_eFactura(CfeType cfeType, CFEDefType objCfe) {
		DscRcgGlobal descRec = new DscRcgGlobal();
		
		if (cfeType == CfeType.eTicket || cfeType == CfeType.eTicket_ND || cfeType == CfeType.eTicket_NC){
			objCfe.getETck().setDscRcgGlobal(descRec);
		} else if (cfeType == CfeType.eFactura || cfeType == CfeType.eFactura_ND || cfeType == CfeType.eFactura_NC){
			objCfe.getEFact().setDscRcgGlobal(descRec);
		}
		
		// Descuento global del cliente
		BigDecimal descuentoGlobal = Env.ZERO;
		MBPartner mbPartner = (MBPartner) mInvoice.getC_BPartner();
		if (mbPartner != null) {
			if (mbPartner.get_ValueAsString("FlatDiscount") != null && mbPartner.get_ValueAsString("FlatDiscount") != ""){
				descuentoGlobal = mbPartner.getFlatDiscount();
			} else {
				MBPGroup mbpGroup = (MBPGroup) mbPartner.getC_BP_Group();
				if (mbpGroup.getFlatDiscount()!= null){
					descuentoGlobal = mbpGroup.getFlatDiscount();
				}
			}
		}
		
		if (!descuentoGlobal.equals(Env.ZERO)){
			// Tiene descuento global
			
			boolean hasIvaBasico = false;
			boolean hasIvaMinimo = false;
			boolean hasIvaExcento = false;
			
			ArrayList<MInvoiceLine> mInvoiceLines =  this.getInvoiceLines("");
			for (MInvoiceLine mInvL : mInvoiceLines){
				MTax tax = new MTax(ctx, mInvL.getC_Tax_ID(), trxName);
				if(tax.getValue().equalsIgnoreCase("minimo")){
					hasIvaMinimo = true;
				} else if(tax.getValue().equalsIgnoreCase("basico")){
					hasIvaBasico = true;
				} else if(tax.getValue().equalsIgnoreCase("exento")){
					hasIvaExcento = true;
				}
			}
			
			
			// Cargo Descuento Global IVA Basico
			int nroLinea = 1;
			
			if (hasIvaBasico){
				DRGItem drgItem = new DRGItem();
				/* 1  */ drgItem.setNroLinDR(nroLinea++);
				/* 2  */ drgItem.setTpoMovDR("D");
				/* 3  */ drgItem.setTpoDR(BigInteger.valueOf(2));
				/* 4  - Tipo de obligatoriedad 3 (dato opcional) */
						 drgItem.setCodDR(new BigInteger("1")); //--> Aparentemente Se debe setear valor sbt 03-11-2015
				/* 5  - Tipo de obligatoriedad 3 (dato opcional) */ 
						 drgItem.setGlosaDR("2");//-->Aparentemente Se debe setear valor sbt 03-11-2015
				/* 5  - Tipo de obligatoriedad 3 (dato opcional) */
				/* 6  */ drgItem.setValorDR(descuentoGlobal);
				/* 7  */ drgItem.setIndFactDR(BigInteger.valueOf(3));
				descRec.getDRGItem().add(drgItem);
			}
			if (hasIvaMinimo){
				DRGItem drgItem = new DRGItem();
				/* 1  */ drgItem.setNroLinDR(nroLinea++);
				/* 2  */ drgItem.setTpoMovDR("D");
				/* 3  */ drgItem.setTpoDR(BigInteger.valueOf(2));
				/* 4  - Tipo de obligatoriedad 3 (dato opcional) */
						 drgItem.setCodDR(new BigInteger("1")); //--> Aparentemente Se debe setear valor sbt 03-11-2015
				/* 5  - Tipo de obligatoriedad 3 (dato opcional) */ 
						 drgItem.setGlosaDR("2");//-->Aparentemente Se debe setear valor sbt 03-11-2015
				/* 5  - Tipo de obligatoriedad 3 (dato opcional) */
				/* 6  */ drgItem.setValorDR(descuentoGlobal);
				/* 7  */ drgItem.setIndFactDR(BigInteger.valueOf(2));
				descRec.getDRGItem().add(drgItem);
			}
			if (hasIvaExcento){
				DRGItem drgItem = new DRGItem();
				/* 1  */ drgItem.setNroLinDR(nroLinea++);
				/* 2  */ drgItem.setTpoMovDR("D");
				/* 3  */ drgItem.setTpoDR(BigInteger.valueOf(2));
				/* 4  - Tipo de obligatoriedad 3 (dato opcional) */
						 drgItem.setCodDR(new BigInteger("1")); //--> Aparentemente Se debe setear valor sbt 03-11-2015
				/* 5  - Tipo de obligatoriedad 3 (dato opcional) */ 
						 drgItem.setGlosaDR("2");//-->Aparentemente Se debe setear valor sbt 03-11-2015
				/* 5  - Tipo de obligatoriedad 3 (dato opcional) */
				/* 6  */ drgItem.setValorDR(descuentoGlobal);
				/* 7  */ drgItem.setIndFactDR(BigInteger.valueOf(1));
				descRec.getDRGItem().add(drgItem);
			}
		}
		
		
	}
	
	/***
	 * Carga la informacion referencia para los siguientes CFE (comparten la misma información):
	 *     eTicket
	 *     eTicket con Nota de Credito
	 *     eTicket con Nota de Debito
	 *     eFactura
	 *     eFactura con Nota de Credito
	 *     eFactura con Nota de Debito
	 * 
	 * Esto es obligatorio para Notas de Credito y Notas de debito para eTicket y eFactura
	 * 
	 * Pagina 35-36: F - Informacion de Referencia
	 * 
	 * OpenUp Ltda. Issue #4094 
	 * @author Raul Capecce - 28/05/2015
	 */
	private void loadInfoReferencia_eTicket_eFactura(CfeType cfeType, CFEDefType objCfe) {
		Referencia referencia = new Referencia();
		Referencia1 ref = new Referencia1();
		referencia.getReferencia1().add(ref);
		
		if (cfeType == CfeType.eTicket_NC || cfeType == CfeType.eTicket_ND || cfeType == CfeType.eFactura_NC || cfeType == CfeType.eFactura_ND) {
			
			if (cfeType == CfeType.eTicket_ND || cfeType == CfeType.eTicket_NC){
				objCfe.getETck().setReferencia(referencia);
			} else if (cfeType == CfeType.eFactura_ND || cfeType == CfeType.eFactura_NC){
				objCfe.getEFact().setReferencia(referencia);
			}
			
			/*
			 *  TODO: Tener en cuenta que pueden haber hasta 40 posibles repeticiones
			 * Actualmente se obtiene la referencia a una eFactura o eTicket
			 */
			
			MInvoice invReferenciada = MInvoice.get(ctx, mInvoice.getUY_Invoice_ID());
			if (invReferenciada == null) throw new AdempiereException("CFE Error: Area Informacion de Referencia - El documento referenciado para asociar al documento actual no esta establecido");
			
			/* 1  */ ref.setNroLinRef(1); // TODO: Inicialmente se toma como referencia 1 teniendo en cuenta que solo puede tener una referencia
			/* 2  - No corresponde */
			
			
			
			
			CfeType cfeTypeRef = getCFETypeFromInvoice(invReferenciada);
			//if (cfeTypeRef == CfeType.eTicket && cfeType != CfeType.eTicket_NC && cfeType != CfeType.eTicket_ND)
			if ((cfeType == CfeType.eTicket_NC || cfeType == CfeType.eTicket_NC) && cfeTypeRef != CfeType.eTicket)
				throw new AdempiereException(CFEMessages.INFOREF_003_ASOCETICKET);
			if ((cfeType == CfeType.eFactura_NC || cfeType == CfeType.eFactura_NC) && cfeTypeRef != CfeType.eFactura)
				throw new AdempiereException(CFEMessages.INFOREF_003_ASOCEFACTURA);
			
			try{
				/* 3  */ ref.setTpoDocRef(BigInteger.valueOf(Long.valueOf(CfeUtils.getCfeTypes().inverse().get(cfeTypeRef))));
			}catch(Exception ex){
				throw new AdempiereException(CFEMessages.INFOREF_003_PARSEERROR);
			}
			
			MDocType doc = MDocType.get(ctx, invReferenciada.getC_DocType_ID());
			
			MSequence sec = new MSequence(ctx, doc.getDefiniteSequence_ID(), trxName);
			if(sec.getPrefix() != null){
				/* 4  */ ref.setSerie(sec.getPrefix());
			} else throw new AdempiereException(CFEMessages.INFOREF_004_NODEF);
			
			if (invReferenciada.getDocumentNo() != null) {
				String documentNo = invReferenciada.getDocumentNo();
				documentNo = documentNo.replaceAll("[^0-9]", ""); // Expresión regular para quitar todo lo que no es número
				String docno = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(documentNo), 7, "0");
				
				/* 5  */ ref.setNroCFERef(new BigInteger(docno));
			} else throw new AdempiereException(CFEMessages.INFOREF_005_NODEF);
			
			if (invReferenciada.getDateInvoiced() != null) {
				/* 7  */ ref.setFechaCFEref(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(invReferenciada.getDateInvoiced(), false));
			} else throw new AdempiereException(CFEMessages.INFOREF_007);

			
		}
		
	}
	
	/***
	 * Carga la informacion referente al CAE correspondiente a cada CFE
	 * 
	 * Pagina 37: G - Constancia Auorizacion para Emision de CFE (CAE)
	 * 
	 * OpenUp Ltda. Issue #4094
	 * @author Raul Capecce	- 28/05/2015
	 */
	private void loadCAE(CfeType cfeType, CFEDefType objCfe) {
		
		CAEDataType caeDataType = new CAEDataType();
		
		switch (cfeType){
		case eFactura:
		case eFactura_NC:
		case eFactura_ND:
			objCfe.getEFact().setCAEData(caeDataType);
			break;
		case eTicket:
		case eTicket_NC:
		case eTicket_ND:
			objCfe.getETck().setCAEData(caeDataType);
			break;
		}
		
		int idCae = ((MDocType) mInvoice.getC_DocTypeTarget()).get_ValueAsInt("UY_DGI_CAE_ID");
		if (idCae == 0) throw new AdempiereException(CFEMessages.CAE_NODEF);
		MDgiCae mDgiCae = new MDgiCae(ctx, idCae, trxName);
		
		caeDataType.setCAEID(mDgiCae.getdocumentoNumero().toBigInteger());
		caeDataType.setDNro(mDgiCae.getnumeroInicio().toBigInteger());
		caeDataType.setHNro(mDgiCae.getnumeroFin().toBigInteger());
		caeDataType.setFecVenc(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mDgiCae.getfechaVencimiento(), false));//mDgiCae.getfechaVencimiento() Emi
		//caeDataType.setFecVenc(Timestamp_to_XmlGregorianCalendar_OnlyDate(mDgiCae.getfechaVencimiento(), false));
	}
	
	/***
	 * Firmar CFE con la firma electronica avanzada del emisor
	 * 
	 * Pagina 39: I - Firma Electrónica Avanzada Del Comprobante Completo
	 * 
	 * OpenUp Ltda. Issue #4094
	 * @author Raul Capecce	- 03/06/2015
	 */
	private void firmar(CfeType cfeType, CFEDefType objCfe) {
		
		String idFileKeyStore = ConsultaRespuestaMic.getUyTrDigitalsignature("BinaryFile_ID",  mInvoice.getAD_Client_ID(), mInvoice.getAD_Org_ID(), trxName);
		String passKeyStore = ConsultaRespuestaMic.getUyTrDigitalsignature("KeystorePass",  mInvoice.getAD_Client_ID(), mInvoice.getAD_Org_ID(), trxName);
		
		byte[] bytesFileKeyStore = (new MImage(ctx, Integer.parseInt(idFileKeyStore), trxName)).getData();
		
		try {
			// Serializar el objeto a XML
			java.io.Writer swCfe1 = new StringWriter();
			JAXBContext context1 = JAXBContext.newInstance(CFEDefType.class);
			Marshaller m1 = context1.createMarshaller();
			m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m1.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			m1.marshal(objCfe, swCfe1);
			String xmlCfe = swCfe1.toString().replaceAll("xmlns:ns3=\".*\"", "").replace("<CFE", "<ns0:CFE").replace("</CFE", "</ns0:CFE");
			
			//.replace("<CFE", "<ns0:CFE").replace("</CFE", "</ns0:CFE")
			
			// Crear documento temporal
			File fXmlFile = File.createTempFile("DGI_CFE_", null);
			fXmlFile.deleteOnExit();
			BufferedWriter out = new BufferedWriter(new FileWriter(fXmlFile));
			out.write(new String(xmlCfe.getBytes(), "UTF-8"));
			out.close();
			
			// Creando el documento para procesar
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			ByteInputStream keyStoreInputStream = new ByteInputStream(bytesFileKeyStore, bytesFileKeyStore.length);
			
			// Almacen de Claves
			Constants.setSignatureSpecNSprefix("");
			// Cargamos el almacen de claves
			KeyStore ks = KeyStore.getInstance("JKS");
			//ks.load(new FileInputStream("/tmp/firma/FirmaDigitalOpenup.pfx"), passKeyStore.toCharArray());
			ks.load(keyStoreInputStream, passKeyStore.toCharArray());
			
			// Obtenemos la clave privada, pues la necesitaremos para encriptar.  
			Key privateKey = null;
			File signatureFile = File.createTempFile("DGI_CFE_Firmado_", null);
			signatureFile.deleteOnExit();
			String baseURI = signatureFile.toURL().toString(); // BaseURI para las URL Relativas.
			String aliasEmpresa = "";
	        
			X509Certificate x509Cert = null;
			
			Enumeration aliasesEnum = ks.aliases();
			while (aliasesEnum.hasMoreElements()) {
				aliasEmpresa = (String) aliasesEnum.nextElement();
				if (ks.getKey(aliasEmpresa, passKeyStore.toCharArray()) != null) {
					privateKey = (PrivateKey) ks.getKey(aliasEmpresa,
							passKeyStore.toCharArray());
					x509Cert = (X509Certificate) ks.getCertificate(aliasEmpresa);
					// store the private keys in an arraylist.
				}
			}
			// Fin Almacen de Claves
			
			
			org.apache.xml.security.Init.init();
			
			
			// Instanciamos un objeto XMLSignature desde el Document. El algoritmo de firma serÃ¡ DSA  
	        XMLSignature xmlSignature = new XMLSignature(doc, baseURI, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);  
	
	        
	        // AÃ±adimos el nodo de la firma a la raiz antes de firmar.  
	        // Observe que ambos elementos pueden ser mezclados en una forma con referencias separadas  
	        Element ele = doc.getDocumentElement();
	        ele.appendChild(xmlSignature.getElement());  
	  
	        // Creamos el objeto que mapea: Document/Reference  
	        Transforms transforms = new Transforms(doc);  
	        transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
	        transforms.addTransform(org.apache.xml.security.c14n.Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
	        
	        //transforms.addTransform(Transforms.TRANSFORM_C14N_OMIT_COMMENTS);
	          
	        // AÃ±adimos lo anterior Documento / Referencia  
	        // ALGO_ID_DIGEST_SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";  
	        xmlSignature.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);  
	  
	        
	        XMLX509IssuerSerial xmlx509is = new XMLX509IssuerSerial(doc, x509Cert);
	        //SBT 12/11/2015
	        String publicKey = (CaratulaConverter.getPublicCer(ctx, Integer.valueOf(ctx.getProperty("#AD_Client_ID")), Integer.valueOf(ctx.getProperty("#AD_Org_ID")), trxName));
	        org.apache.xml.security.keys.content.x509.XMLX509Certificate x509c = new XMLX509Certificate(doc, publicKey.getBytes());
	        //SBT 12/11/2015
	        KeyInfo ki = xmlSignature.getKeyInfo();
	        X509Data x509data = new X509Data(doc); 
	        ki.add(x509data);
	        x509data.add(x509c); //SBT 12/11/2015
	        x509data.add(xmlx509is);
	        

	          
	        // Realizamos la firma  
	        xmlSignature.sign(privateKey);  
	        
	
			// Guardamos archivo de firma en disco
			FileOutputStream f = new FileOutputStream(signatureFile);
			TransformerFactory factorySignature = TransformerFactory.newInstance();
			Transformer transformerF = factorySignature.newTransformer();
			//transformerF.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter sw = new StringWriter();
			transformerF.transform(new DOMSource(doc), new StreamResult(sw));
			f.close();
			
			
	        
	        swCfe1 = new StringWriter();
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

	        transformer.transform(new DOMSource(doc), new StreamResult(swCfe1));
	        
	        
	        cfeStrFirmado = swCfe1.toString();
			
	        // No lo cargamos en un objetos jaxb debido a que lo insertamos directamente como un string en el sobre
//			// Lo cargamos en un objeto jaxb
//			JAXBContext jaxunContext = JAXBContext.newInstance(CFEDefType.class);
//			Unmarshaller unmarshaller = jaxunContext.createUnmarshaller();
//			StringReader reader = new StringReader(swCfe1.toString());
//			CFEDefType objCfeTmp = (CFEDefType) unmarshaller.unmarshal(reader);
//			objCfe.setSignature(objCfeTmp.getSignature());
			
		
		} catch (Exception ex){
			throw new AdempiereException("CFE Error: Area Firma Electronica - ex.getMessage(): " + ex.getMessage());
		}
	}
	

	 /***
	  * Cargar la fecha y hora del Invoice
	  * 
	  * Pagina 38: H - Fecha y Hora de Firma Electronica Avanzada del Comprobante
	  * 
	  * OpenUp Ltda. Issue #4094
	  * @author Raul Capecce	- 03/06/2015
	  */
	private void loadTimestamp(CfeType cfeType, CFEDefType objCfe) {
		
		switch (cfeType){
		case eFactura:
		case eFactura_NC:
		case eFactura_ND:
			objCfe.getEFact().setTmstFirma(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mInvoice.getDateInvoiced(), true));
			break;
		case eTicket:
		case eTicket_NC:
		case eTicket_ND:
			objCfe.getETck().setTmstFirma(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mInvoice.getDateInvoiced(), true));
			break;
		}
	}
	
	/**
	 * Validaciones que realiza DGI sobre los totales
	 * OpenUp Ltda. Issue #4094
	 * @author Raul Capecce - 26/05/2015
	 * @see
	 * @param totales
	 * @return
	 */
	private BigDecimal validacionTotal(Totales totales) {
		
		BigDecimal validacionTotal= Env.ZERO;
		
		if(totales.getMntIVaenSusp() == null) totales.setMntIVaenSusp(Env.ZERO);		
		validacionTotal = validacionTotal.add(totales.getMntIVaenSusp());//"TotMntIVaenSusp"
		
		if(totales.getMntNetoIvaTasaMin() == null) totales.setMntNetoIvaTasaMin(Env.ZERO);
		validacionTotal = validacionTotal.add(totales.getMntNetoIvaTasaMin());//"TotMntNetoIvaTasaMin"
		
		if(totales.getMntNetoIVATasaBasica() == null) totales.setMntNetoIVATasaBasica(Env.ZERO);
		validacionTotal = validacionTotal.add(totales.getMntNetoIVATasaBasica());//"TotMntNetoIVATasaBasica"
		
		if(totales.getMntNetoIVAOtra() == null) totales.setMntNetoIVAOtra(Env.ZERO);
		validacionTotal = validacionTotal.add(totales.getMntNetoIVAOtra());//"TotMntNetoIVAOtra"
		
		if(totales.getMntIVATasaMin() == null) totales.setMntIVATasaMin(Env.ZERO);
		validacionTotal = validacionTotal.add(totales.getMntIVATasaMin());//"TotMntIVATasaMin"
		
		if(totales.getMntIVATasaBasica() == null) totales.setMntIVATasaBasica(Env.ZERO);
		validacionTotal = validacionTotal.add(totales.getMntIVATasaBasica());//"TotMntIVATasaBasica"
		
		if(totales.getMntIVAOtra() == null) totales.setMntIVAOtra(Env.ZERO);
		validacionTotal = validacionTotal.add(totales.getMntIVAOtra());//"TotMntIVAOtra"
		
		if(totales.getMntImpuestoPerc() == null) totales.setMntImpuestoPerc(Env.ZERO);
		validacionTotal = validacionTotal.add(totales.getMntImpuestoPerc());//"TotMntImpuestoPerc"
		
		if(totales.getMntExpoyAsim() == null) totales.setMntExpoyAsim(Env.ZERO);
		validacionTotal = validacionTotal.add(totales.getMntExpoyAsim());//"TotMntExpoyAsim"
		
		if(totales.getMntNoGrv() == null) totales.setMntNoGrv(Env.ZERO);
		validacionTotal = validacionTotal.add(totales.getMntNoGrv());//"TotMntNoGrv"
		
		return validacionTotal;
	}
	
	/**
	 * Validaciones que realiza DGI sobre los totales
	 * OpenUp Ltda. Issue # 4094
	 * @author Raul Capecce - 26/05/2015
	 * @see
	 * @param totales
	 * @return
	 */
	private BigDecimal validacionPagar(Totales totales) {
		BigDecimal validacionPagar = Env.ZERO;
		if(totales.getMontoNF() == null){
			totales.setMontoNF(Env.ZERO);
		}
		validacionPagar = validacionPagar.add(totales.getMontoNF());//"TotMontoNF" 
		if(totales.getMntTotRetenido() == null){
			totales.setMntTotRetenido(Env.ZERO);
		}
		validacionPagar = validacionPagar.add(totales.getMntTotRetenido());//"TotMntTotRetenido"
		validacionPagar = validacionPagar.add(totales.getMntTotal());//"TotMntTotal"
		return validacionPagar;
	}
	
	
	
	public String getCfeStrFirmado(){
		return cfeStrFirmado;
	}
	
}
