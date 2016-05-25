//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.18 at 02:38:03 PM UYT 
//


package org.openup.dgi.efactura.dto.uy.gub.dgi.cfe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Caratula">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RutReceptor" type="{http://cfe.dgi.gub.uy}RUCType"/>
 *                   &lt;element name="RUCEmisor" type="{http://cfe.dgi.gub.uy}RUCType"/>
 *                   &lt;element name="Idemisor">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="10"/>
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="CantCFE">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="1"/>
 *                         &lt;maxInclusive value="250"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Fecha" type="{http://cfe.dgi.gub.uy}FechaHoraType"/>
 *                   &lt;element name="X509Certificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="version" use="required" fixed="1.0">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;maxLength value="3"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="CFE_Adenda" type="{http://cfe.dgi.gub.uy}CFE_Empresas_Type" maxOccurs="250"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" fixed="1.0">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="3"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "caratula",
    "cfeAdenda"
})
@XmlRootElement(name = "EnvioCFE_entreEmpresas")
public class EnvioCFEEntreEmpresas {

    @XmlElement(name = "Caratula", required = true)
    protected EnvioCFEEntreEmpresas.Caratula caratula;
    @XmlElement(name = "CFE_Adenda", required = true)
    protected List<CFEEmpresasType> cfeAdenda;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Gets the value of the caratula property.
     * 
     * @return
     *     possible object is
     *     {@link EnvioCFEEntreEmpresas.Caratula }
     *     
     */
    public EnvioCFEEntreEmpresas.Caratula getCaratula() {
        return caratula;
    }

    /**
     * Sets the value of the caratula property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnvioCFEEntreEmpresas.Caratula }
     *     
     */
    public void setCaratula(EnvioCFEEntreEmpresas.Caratula value) {
        this.caratula = value;
    }

    /**
     * Gets the value of the cfeAdenda property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cfeAdenda property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCFEAdenda().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CFEEmpresasType }
     * 
     * 
     */
    public List<CFEEmpresasType> getCFEAdenda() {
        if (cfeAdenda == null) {
            cfeAdenda = new ArrayList<CFEEmpresasType>();
        }
        return this.cfeAdenda;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "1.0";
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="RutReceptor" type="{http://cfe.dgi.gub.uy}RUCType"/>
     *         &lt;element name="RUCEmisor" type="{http://cfe.dgi.gub.uy}RUCType"/>
     *         &lt;element name="Idemisor">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="10"/>
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="CantCFE">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="1"/>
     *               &lt;maxInclusive value="250"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Fecha" type="{http://cfe.dgi.gub.uy}FechaHoraType"/>
     *         &lt;element name="X509Certificate" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
     *       &lt;/sequence>
     *       &lt;attribute name="version" use="required" fixed="1.0">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;maxLength value="3"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "rutReceptor",
        "rucEmisor",
        "idemisor",
        "cantCFE",
        "fecha",
        "x509Certificate"
    })
    public static class Caratula {

        @XmlElement(name = "RutReceptor", required = true)
        protected String rutReceptor;
        @XmlElement(name = "RUCEmisor", required = true)
        protected String rucEmisor;
        @XmlElement(name = "Idemisor", required = true)
        protected BigInteger idemisor;
        @XmlElement(name = "CantCFE")
        protected int cantCFE;
        @XmlElement(name = "Fecha", required = true)
        protected XMLGregorianCalendar fecha;
        @XmlElement(name = "X509Certificate", required = true)
        protected byte[] x509Certificate;
        @XmlAttribute(name = "version", required = true)
        protected String version;

        /**
         * Gets the value of the rutReceptor property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRutReceptor() {
            return rutReceptor;
        }

        /**
         * Sets the value of the rutReceptor property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRutReceptor(String value) {
            this.rutReceptor = value;
        }

        /**
         * Gets the value of the rucEmisor property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRUCEmisor() {
            return rucEmisor;
        }

        /**
         * Sets the value of the rucEmisor property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRUCEmisor(String value) {
            this.rucEmisor = value;
        }

        /**
         * Gets the value of the idemisor property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getIdemisor() {
            return idemisor;
        }

        /**
         * Sets the value of the idemisor property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setIdemisor(BigInteger value) {
            this.idemisor = value;
        }

        /**
         * Gets the value of the cantCFE property.
         * 
         */
        public int getCantCFE() {
            return cantCFE;
        }

        /**
         * Sets the value of the cantCFE property.
         * 
         */
        public void setCantCFE(int value) {
            this.cantCFE = value;
        }

        /**
         * Gets the value of the fecha property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getFecha() {
            return fecha;
        }

        /**
         * Sets the value of the fecha property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setFecha(XMLGregorianCalendar value) {
            this.fecha = value;
        }

        /**
         * Gets the value of the x509Certificate property.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getX509Certificate() {
            return x509Certificate;
        }

        /**
         * Sets the value of the x509Certificate property.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setX509Certificate(byte[] value) {
            this.x509Certificate = value;
        }

        /**
         * Gets the value of the version property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVersion() {
            if (version == null) {
                return "1.0";
            } else {
                return version;
            }
        }

        /**
         * Sets the value of the version property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVersion(String value) {
            this.version = value;
        }

    }

}
