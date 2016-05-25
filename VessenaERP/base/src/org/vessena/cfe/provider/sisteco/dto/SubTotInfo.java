//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.01 a las 04:27:27 PM UYT 
//


package org.openup.cfe.provider.sisteco.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para SubTotInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SubTotInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="STI_Item" maxOccurs="20" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="NroSTI">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                         &lt;minInclusive value="1"/>
 *                         &lt;maxInclusive value="20"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="GlosaSTI">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://cfe.dgi.gub.uy}Glosa40Type">
 *                         &lt;maxLength value="40"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="OrdenSTI" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                         &lt;maxInclusive value="99"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ValSubtotSTI" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
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
@XmlType(name = "SubTotInfo", propOrder = {
    "stiItem"
})
public class SubTotInfo {

    @XmlElement(name = "STI_Item")
    protected List<SubTotInfo.STIItem> stiItem;

    /**
     * Gets the value of the stiItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stiItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSTIItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubTotInfo.STIItem }
     * 
     * 
     */
    public List<SubTotInfo.STIItem> getSTIItem() {
        if (stiItem == null) {
            stiItem = new ArrayList<SubTotInfo.STIItem>();
        }
        return this.stiItem;
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
     *         &lt;element name="NroSTI">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *               &lt;minInclusive value="1"/>
     *               &lt;maxInclusive value="20"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="GlosaSTI">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://cfe.dgi.gub.uy}Glosa40Type">
     *               &lt;maxLength value="40"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="OrdenSTI" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *               &lt;maxInclusive value="99"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ValSubtotSTI" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
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
        "nroSTI",
        "glosaSTI",
        "ordenSTI",
        "valSubtotSTI"
    })
    public static class STIItem {

        @XmlElement(name = "NroSTI")
        protected int nroSTI;
        @XmlElement(name = "GlosaSTI", required = true)
        protected String glosaSTI;
        @XmlElement(name = "OrdenSTI")
        protected Integer ordenSTI;
        @XmlElement(name = "ValSubtotSTI", required = true)
        protected BigDecimal valSubtotSTI;

        /**
         * Obtiene el valor de la propiedad nroSTI.
         * 
         */
        public int getNroSTI() {
            return nroSTI;
        }

        /**
         * Define el valor de la propiedad nroSTI.
         * 
         */
        public void setNroSTI(int value) {
            this.nroSTI = value;
        }

        /**
         * Obtiene el valor de la propiedad glosaSTI.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGlosaSTI() {
            return glosaSTI;
        }

        /**
         * Define el valor de la propiedad glosaSTI.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGlosaSTI(String value) {
            this.glosaSTI = value;
        }

        /**
         * Obtiene el valor de la propiedad ordenSTI.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getOrdenSTI() {
            return ordenSTI;
        }

        /**
         * Define el valor de la propiedad ordenSTI.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setOrdenSTI(Integer value) {
            this.ordenSTI = value;
        }

        /**
         * Obtiene el valor de la propiedad valSubtotSTI.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getValSubtotSTI() {
            return valSubtotSTI;
        }

        /**
         * Define el valor de la propiedad valSubtotSTI.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setValSubtotSTI(BigDecimal value) {
            this.valSubtotSTI = value;
        }

    }

}
