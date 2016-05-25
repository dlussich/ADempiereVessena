//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.01 a las 04:27:27 PM UYT 
//


package org.openup.cfe.provider.sisteco.dto;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openup.cfe.provider.sisteco.dto package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Signature_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Signature");
    private final static QName _PGPData_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPData");
    private final static QName _DSAKeyValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValue");
    private final static QName _SPKIData_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKIData");
    private final static QName _SignedInfo_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignedInfo");
    private final static QName _RetrievalMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod");
    private final static QName _CanonicalizationMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "CanonicalizationMethod");
    private final static QName _Object_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Object");
    private final static QName _SignatureProperty_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureProperty");
    private final static QName _Manifest_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Manifest");
    private final static QName _SignatureValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureValue");
    private final static QName _Transforms_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transforms");
    private final static QName _Transform_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transform");
    private final static QName _X509Data_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509Data");
    private final static QName _SignatureMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureMethod");
    private final static QName _KeyInfo_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyInfo");
    private final static QName _CFEAdenda_QNAME = new QName("http://cfe.dgi.gub.uy", "CFE_Adenda");
    private final static QName _DigestValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestValue");
    private final static QName _DigestMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestMethod");
    private final static QName _SignatureProperties_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureProperties");
    private final static QName _MgmtData_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "MgmtData");
    private final static QName _KeyName_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyName");
    private final static QName _KeyValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyValue");
    private final static QName _Reference_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Reference");
    private final static QName _RSAKeyValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValue");
    private final static QName _TransformTypeXPath_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "XPath");
    private final static QName _SignatureMethodTypeHMACOutputLength_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "HMACOutputLength");
    private final static QName _SPKIDataTypeSPKISexp_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKISexp");
    private final static QName _X509DataTypeX509IssuerSerial_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial");
    private final static QName _X509DataTypeX509Certificate_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509Certificate");
    private final static QName _X509DataTypeX509SKI_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509SKI");
    private final static QName _X509DataTypeX509SubjectName_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509SubjectName");
    private final static QName _X509DataTypeX509CRL_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509CRL");
    private final static QName _PGPDataTypePGPKeyID_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPKeyID");
    private final static QName _PGPDataTypePGPKeyPacket_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPKeyPacket");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openup.cfe.provider.sisteco.dto
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ItemDetFact }
     * 
     */
    public ItemDetFact createItemDetFact() {
        return new ItemDetFact();
    }

    /**
     * Create an instance of {@link CFEDefType }
     * 
     */
    public CFEDefType createCFEDefType() {
        return new CFEDefType();
    }

    /**
     * Create an instance of {@link CFEDefType.EResg }
     * 
     */
    public CFEDefType.EResg createCFEDefTypeEResg() {
        return new CFEDefType.EResg();
    }

    /**
     * Create an instance of {@link CFEDefType.ERemExp }
     * 
     */
    public CFEDefType.ERemExp createCFEDefTypeERemExp() {
        return new CFEDefType.ERemExp();
    }

    /**
     * Create an instance of {@link CFEDefType.ERem }
     * 
     */
    public CFEDefType.ERem createCFEDefTypeERem() {
        return new CFEDefType.ERem();
    }

    /**
     * Create an instance of {@link CFEDefType.ERem.Encabezado }
     * 
     */
    public CFEDefType.ERem.Encabezado createCFEDefTypeERemEncabezado() {
        return new CFEDefType.ERem.Encabezado();
    }

    /**
     * Create an instance of {@link CFEDefType.EFactExp }
     * 
     */
    public CFEDefType.EFactExp createCFEDefTypeEFactExp() {
        return new CFEDefType.EFactExp();
    }

    /**
     * Create an instance of {@link CFEDefType.EFact }
     * 
     */
    public CFEDefType.EFact createCFEDefTypeEFact() {
        return new CFEDefType.EFact();
    }

    /**
     * Create an instance of {@link CFEDefType.ETck }
     * 
     */
    public CFEDefType.ETck createCFEDefTypeETck() {
        return new CFEDefType.ETck();
    }

    /**
     * Create an instance of {@link ItemDetFactExp }
     * 
     */
    public ItemDetFactExp createItemDetFactExp() {
        return new ItemDetFactExp();
    }

    /**
     * Create an instance of {@link ItemRem }
     * 
     */
    public ItemRem createItemRem() {
        return new ItemRem();
    }

    /**
     * Create an instance of {@link TotalesResg }
     * 
     */
    public TotalesResg createTotalesResg() {
        return new TotalesResg();
    }

    /**
     * Create an instance of {@link Referencia }
     * 
     */
    public Referencia createReferencia() {
        return new Referencia();
    }

    /**
     * Create an instance of {@link ItemRemExp }
     * 
     */
    public ItemRemExp createItemRemExp() {
        return new ItemRemExp();
    }

    /**
     * Create an instance of {@link MediosPago }
     * 
     */
    public MediosPago createMediosPago() {
        return new MediosPago();
    }

    /**
     * Create an instance of {@link DscRcgGlobal }
     * 
     */
    public DscRcgGlobal createDscRcgGlobal() {
        return new DscRcgGlobal();
    }

    /**
     * Create an instance of {@link org.openup.cfe.provider.sisteco.dto.Totales }
     * 
     */
    public org.openup.cfe.provider.sisteco.dto.Totales createTotales() {
        return new org.openup.cfe.provider.sisteco.dto.Totales();
    }

    /**
     * Create an instance of {@link SubTotInfo }
     * 
     */
    public SubTotInfo createSubTotInfo() {
        return new SubTotInfo();
    }

    /**
     * Create an instance of {@link CFEEmpresasType }
     * 
     */
    public CFEEmpresasType createCFEEmpresasType() {
        return new CFEEmpresasType();
    }

    /**
     * Create an instance of {@link IdDocRemExp }
     * 
     */
    public IdDocRemExp createIdDocRemExp() {
        return new IdDocRemExp();
    }

    /**
     * Create an instance of {@link RetPercResg }
     * 
     */
    public RetPercResg createRetPercResg() {
        return new RetPercResg();
    }

    /**
     * Create an instance of {@link RechazoSobreType }
     * 
     */
    public RechazoSobreType createRechazoSobreType() {
        return new RechazoSobreType();
    }

    /**
     * Create an instance of {@link IdDocRem }
     * 
     */
    public IdDocRem createIdDocRem() {
        return new IdDocRem();
    }

    /**
     * Create an instance of {@link IdDocFact }
     * 
     */
    public IdDocFact createIdDocFact() {
        return new IdDocFact();
    }

    /**
     * Create an instance of {@link ReceptorTck }
     * 
     */
    public ReceptorTck createReceptorTck() {
        return new ReceptorTck();
    }

    /**
     * Create an instance of {@link IdDocFactExp }
     * 
     */
    public IdDocFactExp createIdDocFactExp() {
        return new IdDocFactExp();
    }

    /**
     * Create an instance of {@link ReceptorResg }
     * 
     */
    public ReceptorResg createReceptorResg() {
        return new ReceptorResg();
    }

    /**
     * Create an instance of {@link ReceptorRem }
     * 
     */
    public ReceptorRem createReceptorRem() {
        return new ReceptorRem();
    }

    /**
     * Create an instance of {@link IdDocTck }
     * 
     */
    public IdDocTck createIdDocTck() {
        return new IdDocTck();
    }

    /**
     * Create an instance of {@link ReceptorRemExp }
     * 
     */
    public ReceptorRemExp createReceptorRemExp() {
        return new ReceptorRemExp();
    }

    /**
     * Create an instance of {@link IdDocResg }
     * 
     */
    public IdDocResg createIdDocResg() {
        return new IdDocResg();
    }

    /**
     * Create an instance of {@link ItemResg }
     * 
     */
    public ItemResg createItemResg() {
        return new ItemResg();
    }

    /**
     * Create an instance of {@link TotalesRemExp }
     * 
     */
    public TotalesRemExp createTotalesRemExp() {
        return new TotalesRemExp();
    }

    /**
     * Create an instance of {@link TotalesFactExp }
     * 
     */
    public TotalesFactExp createTotalesFactExp() {
        return new TotalesFactExp();
    }

    /**
     * Create an instance of {@link RetPerc }
     * 
     */
    public RetPerc createRetPerc() {
        return new RetPerc();
    }

    /**
     * Create an instance of {@link InfoRespOriType }
     * 
     */
    public InfoRespOriType createInfoRespOriType() {
        return new InfoRespOriType();
    }

    /**
     * Create an instance of {@link RechazoRepType }
     * 
     */
    public RechazoRepType createRechazoRepType() {
        return new RechazoRepType();
    }

    /**
     * Create an instance of {@link ReceptorFact }
     * 
     */
    public ReceptorFact createReceptorFact() {
        return new ReceptorFact();
    }

    /**
     * Create an instance of {@link ObsCFCType }
     * 
     */
    public ObsCFCType createObsCFCType() {
        return new ObsCFCType();
    }

    /**
     * Create an instance of {@link ReceptorFactExp }
     * 
     */
    public ReceptorFactExp createReceptorFactExp() {
        return new ReceptorFactExp();
    }

    /**
     * Create an instance of {@link RechazoCFEDGIType }
     * 
     */
    public RechazoCFEDGIType createRechazoCFEDGIType() {
        return new RechazoCFEDGIType();
    }

    /**
     * Create an instance of {@link RechazoCFEPartesType }
     * 
     */
    public RechazoCFEPartesType createRechazoCFEPartesType() {
        return new RechazoCFEPartesType();
    }

    /**
     * Create an instance of {@link KeyInfoType }
     * 
     */
    public KeyInfoType createKeyInfoType() {
        return new KeyInfoType();
    }

    /**
     * Create an instance of {@link SignedInfoType }
     * 
     */
    public SignedInfoType createSignedInfoType() {
        return new SignedInfoType();
    }

    /**
     * Create an instance of {@link RetrievalMethodType }
     * 
     */
    public RetrievalMethodType createRetrievalMethodType() {
        return new RetrievalMethodType();
    }

    /**
     * Create an instance of {@link DigestMethodType }
     * 
     */
    public DigestMethodType createDigestMethodType() {
        return new DigestMethodType();
    }

    /**
     * Create an instance of {@link SignatureMethodType }
     * 
     */
    public SignatureMethodType createSignatureMethodType() {
        return new SignatureMethodType();
    }

    /**
     * Create an instance of {@link SPKIDataType }
     * 
     */
    public SPKIDataType createSPKIDataType() {
        return new SPKIDataType();
    }

    /**
     * Create an instance of {@link X509DataType }
     * 
     */
    public X509DataType createX509DataType() {
        return new X509DataType();
    }

    /**
     * Create an instance of {@link PGPDataType }
     * 
     */
    public PGPDataType createPGPDataType() {
        return new PGPDataType();
    }

    /**
     * Create an instance of {@link SignatureType }
     * 
     */
    public SignatureType createSignatureType() {
        return new SignatureType();
    }

    /**
     * Create an instance of {@link DSAKeyValueType }
     * 
     */
    public DSAKeyValueType createDSAKeyValueType() {
        return new DSAKeyValueType();
    }

    /**
     * Create an instance of {@link ManifestType }
     * 
     */
    public ManifestType createManifestType() {
        return new ManifestType();
    }

    /**
     * Create an instance of {@link SignatureValueType }
     * 
     */
    public SignatureValueType createSignatureValueType() {
        return new SignatureValueType();
    }

    /**
     * Create an instance of {@link TransformsType }
     * 
     */
    public TransformsType createTransformsType() {
        return new TransformsType();
    }

    /**
     * Create an instance of {@link RSAKeyValueType }
     * 
     */
    public RSAKeyValueType createRSAKeyValueType() {
        return new RSAKeyValueType();
    }

    /**
     * Create an instance of {@link TransformType }
     * 
     */
    public TransformType createTransformType() {
        return new TransformType();
    }

    /**
     * Create an instance of {@link SignaturePropertyType }
     * 
     */
    public SignaturePropertyType createSignaturePropertyType() {
        return new SignaturePropertyType();
    }

    /**
     * Create an instance of {@link KeyValueType }
     * 
     */
    public KeyValueType createKeyValueType() {
        return new KeyValueType();
    }

    /**
     * Create an instance of {@link ReferenceType }
     * 
     */
    public ReferenceType createReferenceType() {
        return new ReferenceType();
    }

    /**
     * Create an instance of {@link CanonicalizationMethodType }
     * 
     */
    public CanonicalizationMethodType createCanonicalizationMethodType() {
        return new CanonicalizationMethodType();
    }

    /**
     * Create an instance of {@link SignaturePropertiesType }
     * 
     */
    public SignaturePropertiesType createSignaturePropertiesType() {
        return new SignaturePropertiesType();
    }

    /**
     * Create an instance of {@link ObjectType }
     * 
     */
    public ObjectType createObjectType() {
        return new ObjectType();
    }

    /**
     * Create an instance of {@link X509IssuerSerialType }
     * 
     */
    public X509IssuerSerialType createX509IssuerSerialType() {
        return new X509IssuerSerialType();
    }

    /**
     * Create an instance of {@link ItemDetFact.CodItem }
     * 
     */
    public ItemDetFact.CodItem createItemDetFactCodItem() {
        return new ItemDetFact.CodItem();
    }

    /**
     * Create an instance of {@link ItemDetFact.SubDescuento }
     * 
     */
    public ItemDetFact.SubDescuento createItemDetFactSubDescuento() {
        return new ItemDetFact.SubDescuento();
    }

    /**
     * Create an instance of {@link ItemDetFact.SubRecargo }
     * 
     */
    public ItemDetFact.SubRecargo createItemDetFactSubRecargo() {
        return new ItemDetFact.SubRecargo();
    }

    /**
     * Create an instance of {@link CFEDefType.EResg.Encabezado }
     * 
     */
    public CFEDefType.EResg.Encabezado createCFEDefTypeEResgEncabezado() {
        return new CFEDefType.EResg.Encabezado();
    }

    /**
     * Create an instance of {@link CFEDefType.EResg.Detalle }
     * 
     */
    public CFEDefType.EResg.Detalle createCFEDefTypeEResgDetalle() {
        return new CFEDefType.EResg.Detalle();
    }

    /**
     * Create an instance of {@link CFEDefType.ERemExp.Encabezado }
     * 
     */
    public CFEDefType.ERemExp.Encabezado createCFEDefTypeERemExpEncabezado() {
        return new CFEDefType.ERemExp.Encabezado();
    }

    /**
     * Create an instance of {@link CFEDefType.ERemExp.Detalle }
     * 
     */
    public CFEDefType.ERemExp.Detalle createCFEDefTypeERemExpDetalle() {
        return new CFEDefType.ERemExp.Detalle();
    }

    /**
     * Create an instance of {@link CFEDefType.ERem.Detalle }
     * 
     */
    public CFEDefType.ERem.Detalle createCFEDefTypeERemDetalle() {
        return new CFEDefType.ERem.Detalle();
    }

    /**
     * Create an instance of {@link CFEDefType.ERem.Encabezado.Totales }
     * 
     */
    public CFEDefType.ERem.Encabezado.Totales createCFEDefTypeERemEncabezadoTotales() {
        return new CFEDefType.ERem.Encabezado.Totales();
    }

    /**
     * Create an instance of {@link CFEDefType.EFactExp.Encabezado }
     * 
     */
    public CFEDefType.EFactExp.Encabezado createCFEDefTypeEFactExpEncabezado() {
        return new CFEDefType.EFactExp.Encabezado();
    }

    /**
     * Create an instance of {@link CFEDefType.EFactExp.Detalle }
     * 
     */
    public CFEDefType.EFactExp.Detalle createCFEDefTypeEFactExpDetalle() {
        return new CFEDefType.EFactExp.Detalle();
    }

    /**
     * Create an instance of {@link CFEDefType.EFact.Encabezado }
     * 
     */
    public CFEDefType.EFact.Encabezado createCFEDefTypeEFactEncabezado() {
        return new CFEDefType.EFact.Encabezado();
    }

    /**
     * Create an instance of {@link CFEDefType.EFact.Detalle }
     * 
     */
    public CFEDefType.EFact.Detalle createCFEDefTypeEFactDetalle() {
        return new CFEDefType.EFact.Detalle();
    }

    /**
     * Create an instance of {@link CFEDefType.ETck.Encabezado }
     * 
     */
    public CFEDefType.ETck.Encabezado createCFEDefTypeETckEncabezado() {
        return new CFEDefType.ETck.Encabezado();
    }

    /**
     * Create an instance of {@link CFEDefType.ETck.Detalle }
     * 
     */
    public CFEDefType.ETck.Detalle createCFEDefTypeETckDetalle() {
        return new CFEDefType.ETck.Detalle();
    }

    /**
     * Create an instance of {@link ItemDetFactExp.CodItem }
     * 
     */
    public ItemDetFactExp.CodItem createItemDetFactExpCodItem() {
        return new ItemDetFactExp.CodItem();
    }

    /**
     * Create an instance of {@link ItemDetFactExp.SubDescuento }
     * 
     */
    public ItemDetFactExp.SubDescuento createItemDetFactExpSubDescuento() {
        return new ItemDetFactExp.SubDescuento();
    }

    /**
     * Create an instance of {@link ItemDetFactExp.SubRecargo }
     * 
     */
    public ItemDetFactExp.SubRecargo createItemDetFactExpSubRecargo() {
        return new ItemDetFactExp.SubRecargo();
    }

    /**
     * Create an instance of {@link ItemRem.CodItem }
     * 
     */
    public ItemRem.CodItem createItemRemCodItem() {
        return new ItemRem.CodItem();
    }

    /**
     * Create an instance of {@link TotalesResg.RetencPercep }
     * 
     */
    public TotalesResg.RetencPercep createTotalesResgRetencPercep() {
        return new TotalesResg.RetencPercep();
    }

    /**
     * Create an instance of {@link Referencia.Referencia1 }
     * 
     */
    public Referencia.Referencia1 createReferenciaReferencia1() {
        return new Referencia.Referencia1();
    }

    /**
     * Create an instance of {@link ItemRemExp.CodItem }
     * 
     */
    public ItemRemExp.CodItem createItemRemExpCodItem() {
        return new ItemRemExp.CodItem();
    }

    /**
     * Create an instance of {@link MediosPago.MedioPago }
     * 
     */
    public MediosPago.MedioPago createMediosPagoMedioPago() {
        return new MediosPago.MedioPago();
    }

    /**
     * Create an instance of {@link DscRcgGlobal.DRGItem }
     * 
     */
    public DscRcgGlobal.DRGItem createDscRcgGlobalDRGItem() {
        return new DscRcgGlobal.DRGItem();
    }

    /**
     * Create an instance of {@link org.openup.cfe.provider.sisteco.dto.Totales.RetencPercep }
     * 
     */
    public org.openup.cfe.provider.sisteco.dto.Totales.RetencPercep createTotalesRetencPercep() {
        return new org.openup.cfe.provider.sisteco.dto.Totales.RetencPercep();
    }

    /**
     * Create an instance of {@link SubTotInfo.STIItem }
     * 
     */
    public SubTotInfo.STIItem createSubTotInfoSTIItem() {
        return new SubTotInfo.STIItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignatureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Signature")
    public JAXBElement<SignatureType> createSignature(SignatureType value) {
        return new JAXBElement<SignatureType>(_Signature_QNAME, SignatureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PGPDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "PGPData")
    public JAXBElement<PGPDataType> createPGPData(PGPDataType value) {
        return new JAXBElement<PGPDataType>(_PGPData_QNAME, PGPDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DSAKeyValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DSAKeyValue")
    public JAXBElement<DSAKeyValueType> createDSAKeyValue(DSAKeyValueType value) {
        return new JAXBElement<DSAKeyValueType>(_DSAKeyValue_QNAME, DSAKeyValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SPKIDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SPKIData")
    public JAXBElement<SPKIDataType> createSPKIData(SPKIDataType value) {
        return new JAXBElement<SPKIDataType>(_SPKIData_QNAME, SPKIDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignedInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignedInfo")
    public JAXBElement<SignedInfoType> createSignedInfo(SignedInfoType value) {
        return new JAXBElement<SignedInfoType>(_SignedInfo_QNAME, SignedInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrievalMethodType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "RetrievalMethod")
    public JAXBElement<RetrievalMethodType> createRetrievalMethod(RetrievalMethodType value) {
        return new JAXBElement<RetrievalMethodType>(_RetrievalMethod_QNAME, RetrievalMethodType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CanonicalizationMethodType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "CanonicalizationMethod")
    public JAXBElement<CanonicalizationMethodType> createCanonicalizationMethod(CanonicalizationMethodType value) {
        return new JAXBElement<CanonicalizationMethodType>(_CanonicalizationMethod_QNAME, CanonicalizationMethodType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Object")
    public JAXBElement<ObjectType> createObject(ObjectType value) {
        return new JAXBElement<ObjectType>(_Object_QNAME, ObjectType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignaturePropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureProperty")
    public JAXBElement<SignaturePropertyType> createSignatureProperty(SignaturePropertyType value) {
        return new JAXBElement<SignaturePropertyType>(_SignatureProperty_QNAME, SignaturePropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ManifestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Manifest")
    public JAXBElement<ManifestType> createManifest(ManifestType value) {
        return new JAXBElement<ManifestType>(_Manifest_QNAME, ManifestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignatureValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureValue")
    public JAXBElement<SignatureValueType> createSignatureValue(SignatureValueType value) {
        return new JAXBElement<SignatureValueType>(_SignatureValue_QNAME, SignatureValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransformsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Transforms")
    public JAXBElement<TransformsType> createTransforms(TransformsType value) {
        return new JAXBElement<TransformsType>(_Transforms_QNAME, TransformsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransformType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Transform")
    public JAXBElement<TransformType> createTransform(TransformType value) {
        return new JAXBElement<TransformType>(_Transform_QNAME, TransformType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link X509DataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509Data")
    public JAXBElement<X509DataType> createX509Data(X509DataType value) {
        return new JAXBElement<X509DataType>(_X509Data_QNAME, X509DataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignatureMethodType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureMethod")
    public JAXBElement<SignatureMethodType> createSignatureMethod(SignatureMethodType value) {
        return new JAXBElement<SignatureMethodType>(_SignatureMethod_QNAME, SignatureMethodType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KeyInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "KeyInfo")
    public JAXBElement<KeyInfoType> createKeyInfo(KeyInfoType value) {
        return new JAXBElement<KeyInfoType>(_KeyInfo_QNAME, KeyInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CFEEmpresasType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cfe.dgi.gub.uy", name = "CFE_Adenda")
    public JAXBElement<CFEEmpresasType> createCFEAdenda(CFEEmpresasType value) {
        return new JAXBElement<CFEEmpresasType>(_CFEAdenda_QNAME, CFEEmpresasType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DigestValue")
    public JAXBElement<byte[]> createDigestValue(byte[] value) {
        return new JAXBElement<byte[]>(_DigestValue_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DigestMethodType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DigestMethod")
    public JAXBElement<DigestMethodType> createDigestMethod(DigestMethodType value) {
        return new JAXBElement<DigestMethodType>(_DigestMethod_QNAME, DigestMethodType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignaturePropertiesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureProperties")
    public JAXBElement<SignaturePropertiesType> createSignatureProperties(SignaturePropertiesType value) {
        return new JAXBElement<SignaturePropertiesType>(_SignatureProperties_QNAME, SignaturePropertiesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "MgmtData")
    public JAXBElement<String> createMgmtData(String value) {
        return new JAXBElement<String>(_MgmtData_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "KeyName")
    public JAXBElement<String> createKeyName(String value) {
        return new JAXBElement<String>(_KeyName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KeyValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "KeyValue")
    public JAXBElement<KeyValueType> createKeyValue(KeyValueType value) {
        return new JAXBElement<KeyValueType>(_KeyValue_QNAME, KeyValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Reference")
    public JAXBElement<ReferenceType> createReference(ReferenceType value) {
        return new JAXBElement<ReferenceType>(_Reference_QNAME, ReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RSAKeyValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "RSAKeyValue")
    public JAXBElement<RSAKeyValueType> createRSAKeyValue(RSAKeyValueType value) {
        return new JAXBElement<RSAKeyValueType>(_RSAKeyValue_QNAME, RSAKeyValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "XPath", scope = TransformType.class)
    public JAXBElement<String> createTransformTypeXPath(String value) {
        return new JAXBElement<String>(_TransformTypeXPath_QNAME, String.class, TransformType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "HMACOutputLength", scope = SignatureMethodType.class)
    public JAXBElement<BigInteger> createSignatureMethodTypeHMACOutputLength(BigInteger value) {
        return new JAXBElement<BigInteger>(_SignatureMethodTypeHMACOutputLength_QNAME, BigInteger.class, SignatureMethodType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SPKISexp", scope = SPKIDataType.class)
    public JAXBElement<byte[]> createSPKIDataTypeSPKISexp(byte[] value) {
        return new JAXBElement<byte[]>(_SPKIDataTypeSPKISexp_QNAME, byte[].class, SPKIDataType.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link X509IssuerSerialType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509IssuerSerial", scope = X509DataType.class)
    public JAXBElement<X509IssuerSerialType> createX509DataTypeX509IssuerSerial(X509IssuerSerialType value) {
        return new JAXBElement<X509IssuerSerialType>(_X509DataTypeX509IssuerSerial_QNAME, X509IssuerSerialType.class, X509DataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509Certificate", scope = X509DataType.class)
    public JAXBElement<byte[]> createX509DataTypeX509Certificate(byte[] value) {
        return new JAXBElement<byte[]>(_X509DataTypeX509Certificate_QNAME, byte[].class, X509DataType.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509SKI", scope = X509DataType.class)
    public JAXBElement<byte[]> createX509DataTypeX509SKI(byte[] value) {
        return new JAXBElement<byte[]>(_X509DataTypeX509SKI_QNAME, byte[].class, X509DataType.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509SubjectName", scope = X509DataType.class)
    public JAXBElement<String> createX509DataTypeX509SubjectName(String value) {
        return new JAXBElement<String>(_X509DataTypeX509SubjectName_QNAME, String.class, X509DataType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509CRL", scope = X509DataType.class)
    public JAXBElement<byte[]> createX509DataTypeX509CRL(byte[] value) {
        return new JAXBElement<byte[]>(_X509DataTypeX509CRL_QNAME, byte[].class, X509DataType.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "PGPKeyID", scope = PGPDataType.class)
    public JAXBElement<byte[]> createPGPDataTypePGPKeyID(byte[] value) {
        return new JAXBElement<byte[]>(_PGPDataTypePGPKeyID_QNAME, byte[].class, PGPDataType.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "PGPKeyPacket", scope = PGPDataType.class)
    public JAXBElement<byte[]> createPGPDataTypePGPKeyPacket(byte[] value) {
        return new JAXBElement<byte[]>(_PGPDataTypePGPKeyPacket_QNAME, byte[].class, PGPDataType.class, ((byte[]) value));
    }

}
