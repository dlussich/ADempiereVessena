//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.01 a las 04:27:27 PM UYT 
//


package org.openup.cfe.provider.sisteco.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para Referencia complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Referencia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Referencia_1" maxOccurs="40" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="NroLinRef">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://cfe.dgi.gub.uy}LineasInfoRef">
 *                         &lt;maxInclusive value="40"/>
 *                         &lt;minInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="IndGlobal" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                         &lt;totalDigits value="1"/>
 *                         &lt;enumeration value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="TpoDocRef" type="{http://cfe.dgi.gub.uy}CFEType" minOccurs="0"/>
 *                   &lt;element name="Serie" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="NroCFERef" type="{http://cfe.dgi.gub.uy}NroCFEType" minOccurs="0"/>
 *                   &lt;element name="RazonRef" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="90"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FechaCFEref" type="{http://cfe.dgi.gub.uy}FechaType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Referencia", propOrder = {
    "referencia1"
})
public class Referencia {

    @XmlElement(name = "Referencia_1")
    protected List<Referencia.Referencia1> referencia1;

    /**
     * Gets the value of the referencia1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referencia1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferencia1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Referencia.Referencia1 }
     * 
     * 
     */
    public List<Referencia.Referencia1> getReferencia1() {
        if (referencia1 == null) {
            referencia1 = new ArrayList<Referencia.Referencia1>();
        }
        return this.referencia1;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="NroLinRef">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://cfe.dgi.gub.uy}LineasInfoRef">
     *               &lt;maxInclusive value="40"/>
     *               &lt;minInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="IndGlobal" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *               &lt;totalDigits value="1"/>
     *               &lt;enumeration value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="TpoDocRef" type="{http://cfe.dgi.gub.uy}CFEType" minOccurs="0"/>
     *         &lt;element name="Serie" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="NroCFERef" type="{http://cfe.dgi.gub.uy}NroCFEType" minOccurs="0"/>
     *         &lt;element name="RazonRef" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="90"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FechaCFEref" type="{http://cfe.dgi.gub.uy}FechaType" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "nroLinRef",
        "indGlobal",
        "tpoDocRef",
        "serie",
        "nroCFERef",
        "razonRef",
        "fechaCFEref"
    })
    public static class Referencia1 {

        @XmlElement(name = "NroLinRef")
        protected int nroLinRef;
        @XmlElement(name = "IndGlobal")
        protected BigInteger indGlobal;
        @XmlElement(name = "TpoDocRef")
        protected BigInteger tpoDocRef;
        @XmlElement(name = "Serie")
        protected String serie;
        @XmlElement(name = "NroCFERef")
        protected BigInteger nroCFERef;
        @XmlElement(name = "RazonRef")
        protected String razonRef;
        @XmlElement(name = "FechaCFEref")
        protected XMLGregorianCalendar fechaCFEref;

        /**
         * Obtiene el valor de la propiedad nroLinRef.
         * 
         */
        public int getNroLinRef() {
            return nroLinRef;
        }

        /**
         * Define el valor de la propiedad nroLinRef.
         * 
         */
        public void setNroLinRef(int value) {
            this.nroLinRef = value;
        }

        /**
         * Obtiene el valor de la propiedad indGlobal.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getIndGlobal() {
            return indGlobal;
        }

        /**
         * Define el valor de la propiedad indGlobal.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setIndGlobal(BigInteger value) {
            this.indGlobal = value;
        }

        /**
         * Obtiene el valor de la propiedad tpoDocRef.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTpoDocRef() {
            return tpoDocRef;
        }

        /**
         * Define el valor de la propiedad tpoDocRef.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTpoDocRef(BigInteger value) {
            this.tpoDocRef = value;
        }

        /**
         * Obtiene el valor de la propiedad serie.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSerie() {
            return serie;
        }

        /**
         * Define el valor de la propiedad serie.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSerie(String value) {
            this.serie = value;
        }

        /**
         * Obtiene el valor de la propiedad nroCFERef.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNroCFERef() {
            return nroCFERef;
        }

        /**
         * Define el valor de la propiedad nroCFERef.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNroCFERef(BigInteger value) {
            this.nroCFERef = value;
        }

        /**
         * Obtiene el valor de la propiedad razonRef.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRazonRef() {
            return razonRef;
        }

        /**
         * Define el valor de la propiedad razonRef.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRazonRef(String value) {
            this.razonRef = value;
        }

        /**
         * Obtiene el valor de la propiedad fechaCFEref.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getFechaCFEref() {
            return fechaCFEref;
        }

        /**
         * Define el valor de la propiedad fechaCFEref.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setFechaCFEref(XMLGregorianCalendar value) {
            this.fechaCFEref = value;
        }

    }

}
