//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.01 a las 04:27:27 PM UYT 
//


package org.openup.cfe.provider.sisteco.dto;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para IdDoc_Fact complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="IdDoc_Fact">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoCFE">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://cfe.dgi.gub.uy}CFEType">
 *               &lt;enumeration value="111"/>
 *               &lt;enumeration value="112"/>
 *               &lt;enumeration value="113"/>
 *               &lt;enumeration value="211"/>
 *               &lt;enumeration value="212"/>
 *               &lt;enumeration value="213"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FchEmis" type="{http://cfe.dgi.gub.uy}FechaType"/>
 *         &lt;element name="PeriodoDesde" type="{http://cfe.dgi.gub.uy}Fecha_d2000_Type" minOccurs="0"/>
 *         &lt;element name="PeriodoHasta" type="{http://cfe.dgi.gub.uy}Fecha_d2000_Type" minOccurs="0"/>
 *         &lt;element name="MntBruto" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;enumeration value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FmaPago">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FchVenc" type="{http://cfe.dgi.gub.uy}Fecha_d2000_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdDoc_Fact", propOrder = {
    "tipoCFE",
    "fchEmis",
    "periodoDesde",
    "periodoHasta",
    "mntBruto",
    "fmaPago",
    "fchVenc"
})
public class IdDocFact {

    @XmlElement(name = "TipoCFE", required = true)
    protected BigInteger tipoCFE;
    @XmlElement(name = "FchEmis", required = true)
    protected XMLGregorianCalendar fchEmis;
    @XmlElement(name = "PeriodoDesde")
    protected XMLGregorianCalendar periodoDesde;
    @XmlElement(name = "PeriodoHasta")
    protected XMLGregorianCalendar periodoHasta;
    @XmlElement(name = "MntBruto")
    protected BigInteger mntBruto;
    @XmlElement(name = "FmaPago", required = true)
    protected BigInteger fmaPago;
    @XmlElement(name = "FchVenc")
    protected XMLGregorianCalendar fchVenc;

    /**
     * Obtiene el valor de la propiedad tipoCFE.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTipoCFE() {
        return tipoCFE;
    }

    /**
     * Define el valor de la propiedad tipoCFE.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTipoCFE(BigInteger value) {
        this.tipoCFE = value;
    }

    /**
     * Obtiene el valor de la propiedad fchEmis.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFchEmis() {
        return fchEmis;
    }

    /**
     * Define el valor de la propiedad fchEmis.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFchEmis(XMLGregorianCalendar value) {
        this.fchEmis = value;
    }

    /**
     * Obtiene el valor de la propiedad periodoDesde.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPeriodoDesde() {
        return periodoDesde;
    }

    /**
     * Define el valor de la propiedad periodoDesde.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPeriodoDesde(XMLGregorianCalendar value) {
        this.periodoDesde = value;
    }

    /**
     * Obtiene el valor de la propiedad periodoHasta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPeriodoHasta() {
        return periodoHasta;
    }

    /**
     * Define el valor de la propiedad periodoHasta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPeriodoHasta(XMLGregorianCalendar value) {
        this.periodoHasta = value;
    }

    /**
     * Obtiene el valor de la propiedad mntBruto.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMntBruto() {
        return mntBruto;
    }

    /**
     * Define el valor de la propiedad mntBruto.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMntBruto(BigInteger value) {
        this.mntBruto = value;
    }

    /**
     * Obtiene el valor de la propiedad fmaPago.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFmaPago() {
        return fmaPago;
    }

    /**
     * Define el valor de la propiedad fmaPago.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFmaPago(BigInteger value) {
        this.fmaPago = value;
    }

    /**
     * Obtiene el valor de la propiedad fchVenc.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFchVenc() {
        return fchVenc;
    }

    /**
     * Define el valor de la propiedad fchVenc.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFchVenc(XMLGregorianCalendar value) {
        this.fchVenc = value;
    }

}
