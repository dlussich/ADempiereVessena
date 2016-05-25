//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.01 a las 04:27:27 PM UYT 
//


package org.openup.cfe.provider.sisteco.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CFE_Empresas_Type complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CFE_Empresas_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CFE" type="{http://cfe.dgi.gub.uy}CFEDefType"/>
 *         &lt;element name="Adenda" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CFE_Empresas_Type", propOrder = {
    "cfe",
    "adenda"
})
public class CFEEmpresasType {

    @XmlElement(name = "CFE", required = true)
    protected CFEDefType cfe;
    @XmlElement(name = "Adenda")
    protected Object adenda;

    /**
     * Obtiene el valor de la propiedad cfe.
     * 
     * @return
     *     possible object is
     *     {@link CFEDefType }
     *     
     */
    public CFEDefType getCFE() {
        return cfe;
    }

    /**
     * Define el valor de la propiedad cfe.
     * 
     * @param value
     *     allowed object is
     *     {@link CFEDefType }
     *     
     */
    public void setCFE(CFEDefType value) {
        this.cfe = value;
    }

    /**
     * Obtiene el valor de la propiedad adenda.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getAdenda() {
        return adenda;
    }

    /**
     * Define el valor de la propiedad adenda.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setAdenda(Object value) {
        this.adenda = value;
    }

}
