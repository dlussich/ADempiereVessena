//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.18 at 02:38:53 PM UYT 
//


package org.openup.dgi.efactura.dto.uy.gub.dgi.cfe;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Item_Det_Fact complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Item_Det_Fact">
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
 *         &lt;element name="IndFact">
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
 *               &lt;enumeration value="10"/>
 *               &lt;enumeration value="11"/>
 *               &lt;enumeration value="12"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IndAgenteResp" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="1"/>
 *               &lt;enumeration value="R"/>
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
 *         &lt;element name="PrecioUnitario" type="{http://cfe.dgi.gub.uy}PrecioUnitarioType"/>
 *         &lt;element name="DescuentoPct" type="{http://cfe.dgi.gub.uy}PctType" minOccurs="0"/>
 *         &lt;element name="DescuentoMonto" type="{http://cfe.dgi.gub.uy}Monto_admite_negType" minOccurs="0"/>
 *         &lt;element name="SubDescuento" maxOccurs="5" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DescTipo">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://cfe.dgi.gub.uy}TipoDRType">
 *                         &lt;totalDigits value="1"/>
 *                         &lt;fractionDigits value="0"/>
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="DescVal" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RecargoPct" type="{http://cfe.dgi.gub.uy}PctType" minOccurs="0"/>
 *         &lt;element name="RecargoMnt" type="{http://cfe.dgi.gub.uy}Monto_admite_negType" minOccurs="0"/>
 *         &lt;element name="SubRecargo" maxOccurs="5" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RecargoTipo">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://cfe.dgi.gub.uy}TipoDRType">
 *                         &lt;totalDigits value="1"/>
 *                         &lt;fractionDigits value="0"/>
 *                         &lt;enumeration value="1"/>
 *                         &lt;enumeration value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="RecargoVal" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RetencPercep" type="{http://cfe.dgi.gub.uy}RetPerc" maxOccurs="5" minOccurs="0"/>
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
@XmlType(name = "Item_Det_Fact", propOrder = {
    "nroLinDet",
    "codItem",
    "indFact",
    "indAgenteResp",
    "nomItem",
    "dscItem",
    "cantidad",
    "uniMed",
    "precioUnitario",
    "descuentoPct",
    "descuentoMonto",
    "subDescuento",
    "recargoPct",
    "recargoMnt",
    "subRecargo",
    "retencPercep",
    "montoItem"
})
public class ItemDetFact {

    @XmlElement(name = "ns0:NroLinDet")
    protected int nroLinDet;
    @XmlElement(name = "ns0:CodItem")
    protected List<ItemDetFact.CodItem> codItem;
    @XmlElement(name = "ns0:IndFact", required = true)
    protected BigInteger indFact;
    @XmlElement(name = "ns0:IndAgenteResp")
    protected String indAgenteResp;
    @XmlElement(name = "ns0:NomItem", required = true)
    protected String nomItem;
    @XmlElement(name = "ns0:DscItem")
    protected String dscItem;
    @XmlElement(name = "ns0:Cantidad", required = true)
    protected BigDecimal cantidad;
    @XmlElement(name = "ns0:UniMed", required = true)
    protected String uniMed;
    @XmlElement(name = "ns0:PrecioUnitario", required = true)
    protected BigDecimal precioUnitario;
    @XmlElement(name = "ns0:DescuentoPct")
    protected BigDecimal descuentoPct;
    @XmlElement(name = "ns0:DescuentoMonto")
    protected BigDecimal descuentoMonto;
    @XmlElement(name = "ns0:SubDescuento")
    protected List<ItemDetFact.SubDescuento> subDescuento;
    @XmlElement(name = "ns0:RecargoPct")
    protected BigDecimal recargoPct;
    @XmlElement(name = "ns0:RecargoMnt")
    protected BigDecimal recargoMnt;
    @XmlElement(name = "ns0:SubRecargo")
    protected List<ItemDetFact.SubRecargo> subRecargo;
    @XmlElement(name = "ns0:RetencPercep")
    protected List<RetPerc> retencPercep;
    @XmlElement(name = "ns0:MontoItem", required = true)
    protected BigDecimal montoItem;

    /**
     * Gets the value of the nroLinDet property.
     * 
     */
    public int getNroLinDet() {
        return nroLinDet;
    }

    /**
     * Sets the value of the nroLinDet property.
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
     * {@link ItemDetFact.CodItem }
     * 
     * 
     */
    public List<ItemDetFact.CodItem> getCodItem() {
        if (codItem == null) {
            codItem = new ArrayList<ItemDetFact.CodItem>();
        }
        return this.codItem;
    }

    /**
     * Gets the value of the indFact property.
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
     * Sets the value of the indFact property.
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
     * Gets the value of the indAgenteResp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndAgenteResp() {
        return indAgenteResp;
    }

    /**
     * Sets the value of the indAgenteResp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndAgenteResp(String value) {
        this.indAgenteResp = value;
    }

    /**
     * Gets the value of the nomItem property.
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
     * Sets the value of the nomItem property.
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
     * Gets the value of the dscItem property.
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
     * Sets the value of the dscItem property.
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
     * Gets the value of the cantidad property.
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
     * Sets the value of the cantidad property.
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
     * Gets the value of the uniMed property.
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
     * Sets the value of the uniMed property.
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
     * Gets the value of the precioUnitario property.
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
     * Sets the value of the precioUnitario property.
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
     * Gets the value of the descuentoPct property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDescuentoPct() {
        return descuentoPct;
    }

    /**
     * Sets the value of the descuentoPct property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDescuentoPct(BigDecimal value) {
        this.descuentoPct = value;
    }

    /**
     * Gets the value of the descuentoMonto property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDescuentoMonto() {
        return descuentoMonto;
    }

    /**
     * Sets the value of the descuentoMonto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDescuentoMonto(BigDecimal value) {
        this.descuentoMonto = value;
    }

    /**
     * Gets the value of the subDescuento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subDescuento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubDescuento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemDetFact.SubDescuento }
     * 
     * 
     */
    public List<ItemDetFact.SubDescuento> getSubDescuento() {
        if (subDescuento == null) {
            subDescuento = new ArrayList<ItemDetFact.SubDescuento>();
        }
        return this.subDescuento;
    }

    /**
     * Gets the value of the recargoPct property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRecargoPct() {
        return recargoPct;
    }

    /**
     * Sets the value of the recargoPct property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRecargoPct(BigDecimal value) {
        this.recargoPct = value;
    }

    /**
     * Gets the value of the recargoMnt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRecargoMnt() {
        return recargoMnt;
    }

    /**
     * Sets the value of the recargoMnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRecargoMnt(BigDecimal value) {
        this.recargoMnt = value;
    }

    /**
     * Gets the value of the subRecargo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subRecargo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubRecargo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemDetFact.SubRecargo }
     * 
     * 
     */
    public List<ItemDetFact.SubRecargo> getSubRecargo() {
        if (subRecargo == null) {
            subRecargo = new ArrayList<ItemDetFact.SubRecargo>();
        }
        return this.subRecargo;
    }

    /**
     * Gets the value of the retencPercep property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the retencPercep property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRetencPercep().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RetPerc }
     * 
     * 
     */
    public List<RetPerc> getRetencPercep() {
        if (retencPercep == null) {
            retencPercep = new ArrayList<RetPerc>();
        }
        return this.retencPercep;
    }

    /**
     * Gets the value of the montoItem property.
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
     * Sets the value of the montoItem property.
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
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

        @XmlElement(name = "ns0:TpoCod", required = true)
        protected String tpoCod;
        @XmlElement(name = "ns0:Cod", required = true)
        protected String cod;

        /**
         * Gets the value of the tpoCod property.
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
         * Sets the value of the tpoCod property.
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
         * Gets the value of the cod property.
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
         * Sets the value of the cod property.
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
     *         &lt;element name="DescTipo">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://cfe.dgi.gub.uy}TipoDRType">
     *               &lt;totalDigits value="1"/>
     *               &lt;fractionDigits value="0"/>
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="DescVal" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
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
        "descTipo",
        "descVal"
    })
    public static class SubDescuento {

        @XmlElement(name = "ns0:DescTipo", required = true)
        protected BigInteger descTipo;
        @XmlElement(name = "ns0:DescVal", required = true)
        protected BigDecimal descVal;

        /**
         * Gets the value of the descTipo property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDescTipo() {
            return descTipo;
        }

        /**
         * Sets the value of the descTipo property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDescTipo(BigInteger value) {
            this.descTipo = value;
        }

        /**
         * Gets the value of the descVal property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getDescVal() {
            return descVal;
        }

        /**
         * Sets the value of the descVal property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setDescVal(BigDecimal value) {
            this.descVal = value;
        }

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
     *         &lt;element name="RecargoTipo">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://cfe.dgi.gub.uy}TipoDRType">
     *               &lt;totalDigits value="1"/>
     *               &lt;fractionDigits value="0"/>
     *               &lt;enumeration value="1"/>
     *               &lt;enumeration value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="RecargoVal" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
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
        "recargoTipo",
        "recargoVal"
    })
    public static class SubRecargo {

        @XmlElement(name = "ns0:RecargoTipo", required = true)
        protected BigInteger recargoTipo;
        @XmlElement(name = "ns0:RecargoVal", required = true)
        protected BigDecimal recargoVal;

        /**
         * Gets the value of the recargoTipo property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getRecargoTipo() {
            return recargoTipo;
        }

        /**
         * Sets the value of the recargoTipo property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setRecargoTipo(BigInteger value) {
            this.recargoTipo = value;
        }

        /**
         * Gets the value of the recargoVal property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getRecargoVal() {
            return recargoVal;
        }

        /**
         * Sets the value of the recargoVal property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setRecargoVal(BigDecimal value) {
            this.recargoVal = value;
        }

    }

}
