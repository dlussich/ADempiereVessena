//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.01 a las 04:27:27 PM UYT 
//


package org.openup.cfe.provider.sisteco.dto;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Totales_Fact_Exp complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Totales_Fact_Exp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TpoMoneda" type="{http://cfe.dgi.gub.uy}TipMonType"/>
 *         &lt;element name="TpoCambio" type="{http://cfe.dgi.gub.uy}TipCambioType" minOccurs="0"/>
 *         &lt;element name="MntExpoyAsim" type="{http://cfe.dgi.gub.uy}MontoType"/>
 *         &lt;element name="MntTotal" type="{http://cfe.dgi.gub.uy}MontoType"/>
 *         &lt;element name="CantLinDet">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://cfe.dgi.gub.uy}LineasDetType">
 *               &lt;totalDigits value="3"/>
 *               &lt;fractionDigits value="0"/>
 *               &lt;maxInclusive value="700"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
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
@XmlType(name = "Totales_Fact_Exp", propOrder = {
    "tpoMoneda",
    "tpoCambio",
    "mntExpoyAsim",
    "mntTotal",
    "cantLinDet",
    "montoNF",
    "mntPagar"
})
public class TotalesFactExp {

    @XmlElement(name = "TpoMoneda", required = true)
    protected TipMonType tpoMoneda;
    @XmlElement(name = "TpoCambio")
    protected BigDecimal tpoCambio;
    @XmlElement(name = "MntExpoyAsim", required = true)
    protected BigDecimal mntExpoyAsim;
    @XmlElement(name = "MntTotal", required = true)
    protected BigDecimal mntTotal;
    @XmlElement(name = "CantLinDet")
    protected int cantLinDet;
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

}
