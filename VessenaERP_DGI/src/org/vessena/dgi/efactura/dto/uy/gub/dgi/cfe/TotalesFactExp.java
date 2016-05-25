//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.18 at 02:38:53 PM UYT 
//


package org.openup.dgi.efactura.dto.uy.gub.dgi.cfe;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Totales_Fact_Exp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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

    @XmlElement(name = "ns0:TpoMoneda", required = true)
    protected TipMonType tpoMoneda;
    @XmlElement(name = "ns0:TpoCambio")
    protected BigDecimal tpoCambio;
    @XmlElement(name = "ns0:MntExpoyAsim", required = true)
    protected BigDecimal mntExpoyAsim;
    @XmlElement(name = "ns0:MntTotal", required = true)
    protected BigDecimal mntTotal;
    @XmlElement(name = "ns0:CantLinDet")
    protected int cantLinDet;
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

}
