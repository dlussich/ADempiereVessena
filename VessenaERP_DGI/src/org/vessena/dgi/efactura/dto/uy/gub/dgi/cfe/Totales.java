//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.18 at 02:38:53 PM UYT 
//


package org.openup.dgi.efactura.dto.uy.gub.dgi.cfe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Totales complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Totales">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TpoMoneda" type="{http://cfe.dgi.gub.uy}TipMonType"/>
 *         &lt;element name="TpoCambio" type="{http://cfe.dgi.gub.uy}TipCambioType" minOccurs="0"/>
 *         &lt;element name="MntNoGrv" type="{http://cfe.dgi.gub.uy}MontoType" minOccurs="0"/>
 *         &lt;element name="MntExpoyAsim" type="{http://cfe.dgi.gub.uy}MontoType" minOccurs="0"/>
 *         &lt;element name="MntImpuestoPerc" type="{http://cfe.dgi.gub.uy}MontoType" minOccurs="0"/>
 *         &lt;element name="MntIVaenSusp" type="{http://cfe.dgi.gub.uy}MontoType" minOccurs="0"/>
 *         &lt;element name="MntNetoIvaTasaMin" type="{http://cfe.dgi.gub.uy}MontoType" minOccurs="0"/>
 *         &lt;element name="MntNetoIVATasaBasica" type="{http://cfe.dgi.gub.uy}MontoType" minOccurs="0"/>
 *         &lt;element name="MntNetoIVAOtra" type="{http://cfe.dgi.gub.uy}MontoType" minOccurs="0"/>
 *         &lt;element name="IVATasaMin" type="{http://cfe.dgi.gub.uy}TasaIVAType" minOccurs="0"/>
 *         &lt;element name="IVATasaBasica" type="{http://cfe.dgi.gub.uy}TasaIVAType" minOccurs="0"/>
 *         &lt;element name="MntIVATasaMin" type="{http://cfe.dgi.gub.uy}MontoType" minOccurs="0"/>
 *         &lt;element name="MntIVATasaBasica" type="{http://cfe.dgi.gub.uy}MontoType" minOccurs="0"/>
 *         &lt;element name="MntIVAOtra" type="{http://cfe.dgi.gub.uy}MontoType" minOccurs="0"/>
 *         &lt;element name="MntTotal" type="{http://cfe.dgi.gub.uy}MontoType"/>
 *         &lt;element name="MntTotRetenido" type="{http://cfe.dgi.gub.uy}Monto_admite_negType" minOccurs="0"/>
 *         &lt;element name="CantLinDet">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://cfe.dgi.gub.uy}LineasDetType">
 *               &lt;totalDigits value="3"/>
 *               &lt;fractionDigits value="0"/>
 *               &lt;maxInclusive value="700"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="RetencPercep" maxOccurs="20" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CodRet" type="{http://cfe.dgi.gub.uy}CodRetType"/>
 *                   &lt;element name="ValRetPerc" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MontoNF" type="{http://cfe.dgi.gub.uy}Monto_admite_negType" minOccurs="0"/>
 *         &lt;element name="MntPagar" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Totales", propOrder = {
    "tpoMoneda",
    "tpoCambio",
    "mntNoGrv",
    "mntExpoyAsim",
    "mntImpuestoPerc",
    "mntIVaenSusp",
    "mntNetoIvaTasaMin",
    "mntNetoIVATasaBasica",
    "mntNetoIVAOtra",
    "ivaTasaMin",
    "ivaTasaBasica",
    "mntIVATasaMin",
    "mntIVATasaBasica",
    "mntIVAOtra",
    "mntTotal",
    "mntTotRetenido",
    "cantLinDet",
    "retencPercep",
    "montoNF",
    "mntPagar"
})
public class Totales {

    @XmlElement(name = "ns0:TpoMoneda", required = true)
    protected TipMonType tpoMoneda;
    @XmlElement(name = "ns0:TpoCambio")
    protected BigDecimal tpoCambio;
    @XmlElement(name = "ns0:MntNoGrv")
    protected BigDecimal mntNoGrv;
    @XmlElement(name = "ns0:MntExpoyAsim")
    protected BigDecimal mntExpoyAsim;
    @XmlElement(name = "ns0:MntImpuestoPerc")
    protected BigDecimal mntImpuestoPerc;
    @XmlElement(name = "ns0:MntIVaenSusp")
    protected BigDecimal mntIVaenSusp;
    @XmlElement(name = "ns0:MntNetoIvaTasaMin")
    protected BigDecimal mntNetoIvaTasaMin;
    @XmlElement(name = "ns0:MntNetoIVATasaBasica")
    protected BigDecimal mntNetoIVATasaBasica;
    @XmlElement(name = "ns0:MntNetoIVAOtra")
    protected BigDecimal mntNetoIVAOtra;
    @XmlElement(name = "ns0:IVATasaMin")
    protected BigDecimal ivaTasaMin;
    @XmlElement(name = "ns0:IVATasaBasica")
    protected BigDecimal ivaTasaBasica;
    @XmlElement(name = "ns0:MntIVATasaMin")
    protected BigDecimal mntIVATasaMin;
    @XmlElement(name = "ns0:MntIVATasaBasica")
    protected BigDecimal mntIVATasaBasica;
    @XmlElement(name = "ns0:MntIVAOtra")
    protected BigDecimal mntIVAOtra;
    @XmlElement(name = "ns0:MntTotal", required = true)
    protected BigDecimal mntTotal;
    @XmlElement(name = "ns0:MntTotRetenido")
    protected BigDecimal mntTotRetenido;
    @XmlElement(name = "ns0:CantLinDet")
    protected int cantLinDet;
    @XmlElement(name = "ns0:RetencPercep")
    protected List<Totales.RetencPercep> retencPercep;
    @XmlElement(name = "ns0:MontoNF")
    protected BigDecimal montoNF;
    @XmlElement(name = "ns0:MntPagar", required = true)
    protected BigDecimal mntPagar;

    /**
     * Gets the value of the tpoMoneda property.
     * 
     * @return
     *     possible object is
     *     {@link TipMonType }
     *     
     */
    public TipMonType getTpoMoneda() {
        return tpoMoneda;
    }

    /**
     * Sets the value of the tpoMoneda property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipMonType }
     *     
     */
    public void setTpoMoneda(TipMonType value) {
        this.tpoMoneda = value;
    }

    /**
     * Gets the value of the tpoCambio property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTpoCambio() {
        return tpoCambio;
    }

    /**
     * Sets the value of the tpoCambio property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTpoCambio(BigDecimal value) {
        this.tpoCambio = value;
    }

    /**
     * Gets the value of the mntNoGrv property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntNoGrv() {
        return mntNoGrv;
    }

    /**
     * Sets the value of the mntNoGrv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntNoGrv(BigDecimal value) {
        this.mntNoGrv = value;
    }

    /**
     * Gets the value of the mntExpoyAsim property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntExpoyAsim() {
        return mntExpoyAsim;
    }

    /**
     * Sets the value of the mntExpoyAsim property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntExpoyAsim(BigDecimal value) {
        this.mntExpoyAsim = value;
    }

    /**
     * Gets the value of the mntImpuestoPerc property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntImpuestoPerc() {
        return mntImpuestoPerc;
    }

    /**
     * Sets the value of the mntImpuestoPerc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntImpuestoPerc(BigDecimal value) {
        this.mntImpuestoPerc = value;
    }

    /**
     * Gets the value of the mntIVaenSusp property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntIVaenSusp() {
        return mntIVaenSusp;
    }

    /**
     * Sets the value of the mntIVaenSusp property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntIVaenSusp(BigDecimal value) {
        this.mntIVaenSusp = value;
    }

    /**
     * Gets the value of the mntNetoIvaTasaMin property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntNetoIvaTasaMin() {
        return mntNetoIvaTasaMin;
    }

    /**
     * Sets the value of the mntNetoIvaTasaMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntNetoIvaTasaMin(BigDecimal value) {
        this.mntNetoIvaTasaMin = value;
    }

    /**
     * Gets the value of the mntNetoIVATasaBasica property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntNetoIVATasaBasica() {
        return mntNetoIVATasaBasica;
    }

    /**
     * Sets the value of the mntNetoIVATasaBasica property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntNetoIVATasaBasica(BigDecimal value) {
        this.mntNetoIVATasaBasica = value;
    }

    /**
     * Gets the value of the mntNetoIVAOtra property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntNetoIVAOtra() {
        return mntNetoIVAOtra;
    }

    /**
     * Sets the value of the mntNetoIVAOtra property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntNetoIVAOtra(BigDecimal value) {
        this.mntNetoIVAOtra = value;
    }

    /**
     * Gets the value of the ivaTasaMin property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIVATasaMin() {
        return ivaTasaMin;
    }

    /**
     * Sets the value of the ivaTasaMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIVATasaMin(BigDecimal value) {
        this.ivaTasaMin = value;
    }

    /**
     * Gets the value of the ivaTasaBasica property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIVATasaBasica() {
        return ivaTasaBasica;
    }

    /**
     * Sets the value of the ivaTasaBasica property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIVATasaBasica(BigDecimal value) {
        this.ivaTasaBasica = value;
    }

    /**
     * Gets the value of the mntIVATasaMin property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntIVATasaMin() {
        return mntIVATasaMin;
    }

    /**
     * Sets the value of the mntIVATasaMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntIVATasaMin(BigDecimal value) {
        this.mntIVATasaMin = value;
    }

    /**
     * Gets the value of the mntIVATasaBasica property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntIVATasaBasica() {
        return mntIVATasaBasica;
    }

    /**
     * Sets the value of the mntIVATasaBasica property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntIVATasaBasica(BigDecimal value) {
        this.mntIVATasaBasica = value;
    }

    /**
     * Gets the value of the mntIVAOtra property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntIVAOtra() {
        return mntIVAOtra;
    }

    /**
     * Sets the value of the mntIVAOtra property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntIVAOtra(BigDecimal value) {
        this.mntIVAOtra = value;
    }

    /**
     * Gets the value of the mntTotal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntTotal() {
        return mntTotal;
    }

    /**
     * Sets the value of the mntTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntTotal(BigDecimal value) {
        this.mntTotal = value;
    }

    /**
     * Gets the value of the mntTotRetenido property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntTotRetenido() {
        return mntTotRetenido;
    }

    /**
     * Sets the value of the mntTotRetenido property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntTotRetenido(BigDecimal value) {
        this.mntTotRetenido = value;
    }

    /**
     * Gets the value of the cantLinDet property.
     * 
     */
    public int getCantLinDet() {
        return cantLinDet;
    }

    /**
     * Sets the value of the cantLinDet property.
     * 
     */
    public void setCantLinDet(int value) {
        this.cantLinDet = value;
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
     * {@link Totales.RetencPercep }
     * 
     * 
     */
    public List<Totales.RetencPercep> getRetencPercep() {
        if (retencPercep == null) {
            retencPercep = new ArrayList<Totales.RetencPercep>();
        }
        return this.retencPercep;
    }

    /**
     * Gets the value of the montoNF property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoNF() {
        return montoNF;
    }

    /**
     * Sets the value of the montoNF property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoNF(BigDecimal value) {
        this.montoNF = value;
    }

    /**
     * Gets the value of the mntPagar property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMntPagar() {
        return mntPagar;
    }

    /**
     * Sets the value of the mntPagar property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMntPagar(BigDecimal value) {
        this.mntPagar = value;
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
     *         &lt;element name="CodRet" type="{http://cfe.dgi.gub.uy}CodRetType"/>
     *         &lt;element name="ValRetPerc" type="{http://cfe.dgi.gub.uy}Monto_admite_negType"/>
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
        "codRet",
        "valRetPerc"
    })
    public static class RetencPercep {

        @XmlElement(name = "ns0:CodRet", required = true)
        protected String codRet;
        @XmlElement(name = "ns0:ValRetPerc", required = true)
        protected BigDecimal valRetPerc;

        /**
         * Gets the value of the codRet property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodRet() {
            return codRet;
        }

        /**
         * Sets the value of the codRet property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodRet(String value) {
            this.codRet = value;
        }

        /**
         * Gets the value of the valRetPerc property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getValRetPerc() {
            return valRetPerc;
        }

        /**
         * Sets the value of the valRetPerc property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setValRetPerc(BigDecimal value) {
            this.valRetPerc = value;
        }

    }

}
