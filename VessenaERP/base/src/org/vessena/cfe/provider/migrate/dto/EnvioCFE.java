//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.29 at 05:47:04 PM UYT 
//


package org.openup.cfe.provider.migrate.dto;

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
 *         &lt;element name="Encabezado" type="{http://www.invoicy.com.uy/}EncabezadoEnvioType"/>
 *         &lt;element name="CFE" type="{http://www.invoicy.com.uy/}CFEInvoiCyCollectionType"/>
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
    "encabezado",
    "cfe"
})
@XmlRootElement(name = "EnvioCFE")
public class EnvioCFE {

    @XmlElement(name = "Encabezado", required = true)
    protected EncabezadoEnvioType encabezado;
    @XmlElement(name = "CFE", required = true)
    protected CFEInvoiCyCollectionType cfe;

    /**
     * Gets the value of the encabezado property.
     * 
     * @return
     *     possible object is
     *     {@link EncabezadoEnvioType }
     *     
     */
    public EncabezadoEnvioType getEncabezado() {
        return encabezado;
    }

    /**
     * Sets the value of the encabezado property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncabezadoEnvioType }
     *     
     */
    public void setEncabezado(EncabezadoEnvioType value) {
        this.encabezado = value;
    }

    /**
     * Gets the value of the cfe property.
     * 
     * @return
     *     possible object is
     *     {@link CFEInvoiCyCollectionType }
     *     
     */
    public CFEInvoiCyCollectionType getCFE() {
        return cfe;
    }

    /**
     * Sets the value of the cfe property.
     * 
     * @param value
     *     allowed object is
     *     {@link CFEInvoiCyCollectionType }
     *     
     */
    public void setCFE(CFEInvoiCyCollectionType value) {
        this.cfe = value;
    }

}
