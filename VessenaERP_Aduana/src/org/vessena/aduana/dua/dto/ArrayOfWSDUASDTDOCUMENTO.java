//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.02 at 06:10:13 PM UYST 
//


package org.openup.aduana.dua.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfWSDUASDT.DOCUMENTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfWSDUASDT.DOCUMENTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WSDUASDT.DOCUMENTO" type="{www.aduanas.gub.uy/wsduasdt}WSDUASDT.DOCUMENTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfWSDUASDT.DOCUMENTO", propOrder = {
    "wsduasdtdocumento"
})
public class ArrayOfWSDUASDTDOCUMENTO {

    @XmlElement(name = "WSDUASDT.DOCUMENTO")
    protected List<WSDUASDTDOCUMENTO> wsduasdtdocumento;

    /**
     * Gets the value of the wsduasdtdocumento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wsduasdtdocumento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWSDUASDTDOCUMENTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WSDUASDTDOCUMENTO }
     * 
     * 
     */
    public List<WSDUASDTDOCUMENTO> getWSDUASDTDOCUMENTO() {
        if (wsduasdtdocumento == null) {
            wsduasdtdocumento = new ArrayList<WSDUASDTDOCUMENTO>();
        }
        return this.wsduasdtdocumento;
    }

}
