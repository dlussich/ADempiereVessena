//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.02 at 06:10:13 PM UYST 
//


package org.openup.aduana.dua.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="Dua" type="{www.aduanas.gub.uy/wsduasdt}WSDUASDT"/>
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
    "dua"
})
@XmlRootElement(name = "WSDUA.ExecuteResponse", namespace = "www.aduanas.gub.uy/wsdua")
public class WSDUAExecuteResponse {

    @XmlElement(name = "Dua", namespace = "www.aduanas.gub.uy/wsdua", required = true)
    protected WSDUASDT dua;

    /**
     * Gets the value of the dua property.
     * 
     * @return
     *     possible object is
     *     {@link WSDUASDT }
     *     
     */
    public WSDUASDT getDua() {
        return dua;
    }

    /**
     * Sets the value of the dua property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSDUASDT }
     *     
     */
    public void setDua(WSDUASDT value) {
        this.dua = value;
    }

}
