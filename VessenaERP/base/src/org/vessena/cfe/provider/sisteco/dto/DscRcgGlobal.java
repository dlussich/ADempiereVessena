//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.01 a las 04:27:27 PM UYT 
//


package org.openup.cfe.provider.sisteco.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DscRcgGlobal complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DscRcgGlobal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DRG_Item" maxOccurs="20" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="NroLinDR">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                         &lt;maxInclusive value="20"/>
 *                         &lt;minInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="TpoMovDR">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="D"/>
 *                         &lt;enumeration value="R"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="TpoDR" type="{http://cfe.dgi.gub.uy}TipoDRType" minOccurs="0"/>
 *                   &lt;element name="CodDR" type="{http://cfe.dgi.gub.uy}CodDescRecType" minOccurs="0"/>
 *                   &lt;element name="GlosaDR" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://cfe.dgi.gub.uy}Glosa50Type">
 *                         &lt;maxLength value="50"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ValorDR" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
 *                   &lt;element name="IndFactDR" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
 *                         &lt;enumeration value="3"/>
 *                         &lt;enumeration value="4"/>
 *                         &lt;enumeration value="6"/>
 *                         &lt;enumeration value="7"/>
 *                         &lt;enumeration value="10"/>
 *                         &lt;enumeration value="11"/>
 *                         &lt;enumeration value="12"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
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
@XmlType(name = "DscRcgGlobal", propOrder = {
    "drgItem"
})
public class DscRcgGlobal {

    @XmlElement(name = "DRG_Item")
    protected List<DscRcgGlobal.DRGItem> drgItem;

    /**
     * Gets the value of the drgItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the drgItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDRGItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DscRcgGlobal.DRGItem }
     * 
     * 
     */
    public List<DscRcgGlobal.DRGItem> getDRGItem() {
        if (drgItem == null) {
            drgItem = new ArrayList<DscRcgGlobal.DRGItem>();
        }
        return this.drgItem;
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
     *         &lt;element name="NroLinDR">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *               &lt;maxInclusive value="20"/>
     *               &lt;minInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="TpoMovDR">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="D"/>
     *               &lt;enumeration value="R"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="TpoDR" type="{http://cfe.dgi.gub.uy}TipoDRType" minOccurs="0"/>
     *         &lt;element name="CodDR" type="{http://cfe.dgi.gub.uy}CodDescRecType" minOccurs="0"/>
     *         &lt;element name="GlosaDR" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://cfe.dgi.gub.uy}Glosa50Type">
     *               &lt;maxLength value="50"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ValorDR" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
     *         &lt;element name="IndFactDR" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
     *               &lt;enumeration value="3"/>
     *               &lt;enumeration value="4"/>
     *               &lt;enumeration value="6"/>
     *               &lt;enumeration value="7"/>
     *               &lt;enumeration value="10"/>
     *               &lt;enumeration value="11"/>
     *               &lt;enumeration value="12"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
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
    @XmlType(name = "", propOrder = {
        "nroLinDR",
        "tpoMovDR",
        "tpoDR",
        "codDR",
        "glosaDR",
        "valorDR",
        "indFactDR"
    })
    public static class DRGItem {

        @XmlElement(name = "NroLinDR")
        protected int nroLinDR;
        @XmlElement(name = "TpoMovDR", required = true)
        protected String tpoMovDR;
        @XmlElement(name = "TpoDR")
        protected BigInteger tpoDR;
        @XmlElement(name = "CodDR")
        protected BigInteger codDR;
        @XmlElement(name = "GlosaDR")
        protected String glosaDR;
        @XmlElement(name = "ValorDR", required = true)
        protected BigDecimal valorDR;
        @XmlElement(name = "IndFactDR")
        protected BigInteger indFactDR;

        /**
         * Obtiene el valor de la propiedad nroLinDR.
         * 
         */
        public int getNroLinDR() {
            return nroLinDR;
        }

        /**
         * Define el valor de la propiedad nroLinDR.
         * 
         */
        public void setNroLinDR(int value) {
            this.nroLinDR = value;
        }

        /**
         * Obtiene el valor de la propiedad tpoMovDR.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTpoMovDR() {
            return tpoMovDR;
        }

        /**
         * Define el valor de la propiedad tpoMovDR.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTpoMovDR(String value) {
            this.tpoMovDR = value;
        }

        /**
         * Obtiene el valor de la propiedad tpoDR.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTpoDR() {
            return tpoDR;
        }

        /**
         * Define el valor de la propiedad tpoDR.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTpoDR(BigInteger value) {
            this.tpoDR = value;
        }

        /**
         * Obtiene el valor de la propiedad codDR.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getCodDR() {
            return codDR;
        }

        /**
         * Define el valor de la propiedad codDR.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCodDR(BigInteger value) {
            this.codDR = value;
        }

        /**
         * Obtiene el valor de la propiedad glosaDR.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGlosaDR() {
            return glosaDR;
        }

        /**
         * Define el valor de la propiedad glosaDR.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGlosaDR(String value) {
            this.glosaDR = value;
        }

        /**
         * Obtiene el valor de la propiedad valorDR.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getValorDR() {
            return valorDR;
        }

        /**
         * Define el valor de la propiedad valorDR.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setValorDR(BigDecimal value) {
            this.valorDR = value;
        }

        /**
         * Obtiene el valor de la propiedad indFactDR.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getIndFactDR() {
            return indFactDR;
        }

        /**
         * Define el valor de la propiedad indFactDR.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setIndFactDR(BigInteger value) {
            this.indFactDR = value;
        }

    }

}
