//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.18 at 02:39:05 PM UYT 
//


package org.openup.dgi.efactura.dto.uy.gub.dgi.cfe;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RsmnData_Resg_Cont complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RsmnData_Resg_Cont">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Montos" type="{http://cfe.dgi.gub.uy}Montos_Res"/>
 *         &lt;element name="CantCFCEmi" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RsmnData_Resg_Cont", propOrder = {
    "montos",
    "cantCFCEmi"
})
public class RsmnDataResgCont {

    @XmlElement(name = "Montos", required = true)
    protected MontosRes montos;
    @XmlElement(name = "CantCFCEmi")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger cantCFCEmi;

    /**
     * Gets the value of the montos property.
     * 
     * @return
     *     possible object is
     *     {@link MontosRes }
     *     
     */
    public MontosRes getMontos() {
        return montos;
    }

    /**
     * Sets the value of the montos property.
     * 
     * @param value
     *     allowed object is
     *     {@link MontosRes }
     *     
     */
    public void setMontos(MontosRes value) {
        this.montos = value;
    }

    /**
     * Gets the value of the cantCFCEmi property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCantCFCEmi() {
        return cantCFCEmi;
    }

    /**
     * Sets the value of the cantCFCEmi property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCantCFCEmi(BigInteger value) {
        this.cantCFCEmi = value;
    }

}
