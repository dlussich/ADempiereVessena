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
 * <p>Clase Java para Totales complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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

    @XmlElement(name = "TpoMoneda", required = true)
    protected TipMonType tpoMoneda;
    @XmlElement(name = "TpoCambio")
    protected BigDecimal tpoCambio;
    @XmlElement(name = "MntNoGrv")
    protected BigDecimal mntNoGrv;
    @XmlElement(name = "MntExpoyAsim")
    protected BigDecimal mntExpoyAsim;
    @XmlElement(name = "MntImpuestoPerc")
    protected BigDecimal mntImpuestoPerc;
    @XmlElement(name = "MntIVaenSusp")
    protected BigDecimal mntIVaenSusp;
    @XmlElement(name = "MntNetoIvaTasaMin")
    protected BigDecimal mntNetoIvaTasaMin;
    @XmlElement(name = "MntNetoIVATasaBasica")
    protected BigDecimal mntNetoIVATasaBasica;
    @XmlElement(name = "MntNetoIVAOtra")
    protected BigDecimal mntNetoIVAOtra;
    @XmlElement(name = "IVATasaMin")
    protected BigDecimal ivaTasaMin;
    @XmlElement(name = "IVATasaBasica")
    protected BigDecimal ivaTasaBasica;
    @XmlElement(name = "MntIVATasaMin")
    protected BigDecimal mntIVATasaMin;
    @XmlElement(name = "MntIVATasaBasica")
    protected BigDecimal mntIVATasaBasica;
    @XmlElement(name = "MntIVAOtra")
    protected BigDecimal mntIVAOtra;
    @XmlElement(name = "MntTotal", required = true)
    protected BigDecimal mntTotal;
    @XmlElement(name = "MntTotRetenido")
    protected BigDecimal mntTotRetenido;
    @XmlElement(name = "CantLinDet")
    protected int cantLinDet;
    @XmlElement(name = "RetencPercep")
    protected List<Totales.RetencPercep> retencPercep;
    @XmlElement(name = "MontoNF")
    protected BigDecimal montoNF;
    @XmlElement(name = "MntPagar", required = true)
    protected BigDecimal mntPagar;

    /**
     * Obtiene el valor de la propiedad tpoMoneda.
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
     * Define el valor de la propiedad tpoMoneda.
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
     * Obtiene el valor de la propiedad tpoCambio.
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
     * Define el valor de la propiedad tpoCambio.
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
     * Obtiene el valor de la propiedad mntNoGrv.
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
     * Define el valor de la propiedad mntNoGrv.
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
     * Obtiene el valor de la propiedad mntExpoyAsim.
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
     * Define el valor de la propiedad mntExpoyAsim.
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
     * Obtiene el valor de la propiedad mntImpuestoPerc.
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
     * Define el valor de la propiedad mntImpuestoPerc.
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
     * Obtiene el valor de la propiedad mntIVaenSusp.
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
     * Define el valor de la propiedad mntIVaenSusp.
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
     * Obtiene el valor de la propiedad mntNetoIvaTasaMin.
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
     * Define el valor de la propiedad mntNetoIvaTasaMin.
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
     * Obtiene el valor de la propiedad mntNetoIVATasaBasica.
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
     * Define el valor de la propiedad mntNetoIVATasaBasica.
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
     * Obtiene el valor de la propiedad mntNetoIVAOtra.
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
     * Define el valor de la propiedad mntNetoIVAOtra.
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
     * Obtiene el valor de la propiedad ivaTasaMin.
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
     * Define el valor de la propiedad ivaTasaMin.
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
     * Obtiene el valor de la propiedad ivaTasaBasica.
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
     * Define el valor de la propiedad ivaTasaBasica.
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
     * Obtiene el valor de la propiedad mntIVATasaMin.
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
     * Define el valor de la propiedad mntIVATasaMin.
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
     * Obtiene el valor de la propiedad mntIVATasaBasica.
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
     * Define el valor de la propiedad mntIVATasaBasica.
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
     * Obtiene el valor de la propiedad mntIVAOtra.
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
     * Define el valor de la propiedad mntIVAOtra.
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
     * Obtiene el valor de la propiedad mntTotal.
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
     * Define el valor de la propiedad mntTotal.
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
     * Obtiene el valor de la propiedad mntTotRetenido.
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
     * Define el valor de la propiedad mntTotRetenido.
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
     * Obtiene el valor de la propiedad cantLinDet.
     * 
     */
    public int getCantLinDet() {
        return cantLinDet;
    }

    /**
     * Define el valor de la propiedad cantLinDet.
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
     * Obtiene el valor de la propiedad montoNF.
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
     * Define el valor de la propiedad montoNF.
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
     * Obtiene el valor de la propiedad mntPagar.
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
     * Define el valor de la propiedad mntPagar.
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
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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

        @XmlElement(name = "CodRet", required = true)
        protected String codRet;
        @XmlElement(name = "ValRetPerc", required = true)
        protected BigDecimal valRetPerc;

        /**
         * Obtiene el valor de la propiedad codRet.
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
         * Define el valor de la propiedad codRet.
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
         * Obtiene el valor de la propiedad valRetPerc.
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
         * Define el valor de la propiedad valRetPerc.
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
