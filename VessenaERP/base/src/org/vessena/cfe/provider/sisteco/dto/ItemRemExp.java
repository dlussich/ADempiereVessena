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
 * <p>Clase Java para Item_Rem_Exp complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Item_Rem_Exp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NroLinDet">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://cfe.dgi.gub.uy}LineasDetType">
 *               &lt;maxInclusive value="700"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CodItem" maxOccurs="5" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TpoCod">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="10"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Cod">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="35"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="IndFact" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *               &lt;totalDigits value="2"/>
 *               &lt;fractionDigits value="0"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
 *               &lt;enumeration value="6"/>
 *               &lt;enumeration value="7"/>
 *               &lt;enumeration value="8"/>
 *               &lt;enumeration value="10"/>
 *               &lt;enumeration value="11"/>
 *               &lt;enumeration value="12"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NomItem">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="80"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DscItem" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Cantidad" type="{http://cfe.dgi.gub.uy}Cant_admite_negType"/>
 *         &lt;element name="UniMed">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PrecioUnitario" type="{http://cfe.dgi.gub.uy}MontoType"/>
 *         &lt;element name="MontoItem" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Item_Rem_Exp", propOrder = {
    "nroLinDet",
    "codItem",
    "indFact",
    "nomItem",
    "dscItem",
    "cantidad",
    "uniMed",
    "precioUnitario",
    "montoItem"
})
public class ItemRemExp {

    @XmlElement(name = "NroLinDet")
    protected int nroLinDet;
    @XmlElement(name = "CodItem")
    protected List<ItemRemExp.CodItem> codItem;
    @XmlElement(name = "IndFact")
    protected BigInteger indFact;
    @XmlElement(name = "NomItem", required = true)
    protected String nomItem;
    @XmlElement(name = "DscItem")
    protected String dscItem;
    @XmlElement(name = "Cantidad", required = true)
    protected BigDecimal cantidad;
    @XmlElement(name = "UniMed", required = true)
    protected String uniMed;
    @XmlElement(name = "PrecioUnitario", required = true)
    protected BigDecimal precioUnitario;
    @XmlElement(name = "MontoItem", required = true)
    protected BigDecimal montoItem;

    /**
     * Obtiene el valor de la propiedad nroLinDet.
     * 
     */
    public int getNroLinDet() {
        return nroLinDet;
    }

    /**
     * Define el valor de la propiedad nroLinDet.
     * 
     */
    public void setNroLinDet(int value) {
        this.nroLinDet = value;
    }

    /**
     * Gets the value of the codItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemRemExp.CodItem }
     * 
     * 
     */
    public List<ItemRemExp.CodItem> getCodItem() {
        if (codItem == null) {
            codItem = new ArrayList<ItemRemExp.CodItem>();
        }
        return this.codItem;
    }

    /**
     * Obtiene el valor de la propiedad indFact.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIndFact() {
        return indFact;
    }

    /**
     * Define el valor de la propiedad indFact.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIndFact(BigInteger value) {
        this.indFact = value;
    }

    /**
     * Obtiene el valor de la propiedad nomItem.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomItem() {
        return nomItem;
    }

    /**
     * Define el valor de la propiedad nomItem.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomItem(String value) {
        this.nomItem = value;
    }

    /**
     * Obtiene el valor de la propiedad dscItem.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDscItem() {
        return dscItem;
    }

    /**
     * Define el valor de la propiedad dscItem.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDscItem(String value) {
        this.dscItem = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidad.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * Define el valor de la propiedad cantidad.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCantidad(BigDecimal value) {
        this.cantidad = value;
    }

    /**
     * Obtiene el valor de la propiedad uniMed.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniMed() {
        return uniMed;
    }

    /**
     * Define el valor de la propiedad uniMed.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniMed(String value) {
        this.uniMed = value;
    }

    /**
     * Obtiene el valor de la propiedad precioUnitario.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Define el valor de la propiedad precioUnitario.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrecioUnitario(BigDecimal value) {
        this.precioUnitario = value;
    }

    /**
     * Obtiene el valor de la propiedad montoItem.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoItem() {
        return montoItem;
    }

    /**
     * Define el valor de la propiedad montoItem.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoItem(BigDecimal value) {
        this.montoItem = value;
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
     *         &lt;element name="TpoCod">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="10"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Cod">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="35"/>
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
        "tpoCod",
        "cod"
    })
    public static class CodItem {

        @XmlElement(name = "TpoCod", required = true)
        protected String tpoCod;
        @XmlElement(name = "Cod", required = true)
        protected String cod;

        /**
         * Obtiene el valor de la propiedad tpoCod.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTpoCod() {
            return tpoCod;
        }

        /**
         * Define el valor de la propiedad tpoCod.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTpoCod(String value) {
            this.tpoCod = value;
        }

        /**
         * Obtiene el valor de la propiedad cod.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCod() {
            return cod;
        }

        /**
         * Define el valor de la propiedad cod.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCod(String value) {
            this.cod = value;
        }

    }

}
