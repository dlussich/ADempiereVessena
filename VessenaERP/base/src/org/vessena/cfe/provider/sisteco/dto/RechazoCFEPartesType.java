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
 * <p>Clase Java para RechazoCFE_PartesType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="RechazoCFE_PartesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Motivo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="3"/>
 *               &lt;enumeration value="E02"/>
 *               &lt;enumeration value="E03"/>
 *               &lt;enumeration value="E04"/>
 *               &lt;enumeration value="E05"/>
 *               &lt;enumeration value="E07"/>
 *               &lt;enumeration value="E20"/>
 *               &lt;enumeration value="E21"/>
 *               &lt;enumeration value="E22"/>
 *               &lt;enumeration value="E23"/>
 *               &lt;enumeration value="E24"/>
 *               &lt;enumeration value="E25"/>
 *               &lt;enumeration value="E26"/>
 *               &lt;enumeration value="E27"/>
 *               &lt;enumeration value="E28"/>
 *               &lt;enumeration value="E29"/>
 *               &lt;enumeration value="E30"/>
 *               &lt;enumeration value="E31"/>
 *               &lt;enumeration value="E32"/>
 *               &lt;enumeration value="E33"/>
 *               &lt;enumeration value="E34"/>
 *               &lt;enumeration value="E35"/>
 *               &lt;enumeration value="E36"/>
 *               &lt;enumeration value="E37"/>
 *               &lt;enumeration value="E38"/>
 *               &lt;enumeration value="E39"/>
 *               &lt;enumeration value="E40"/>
 *               &lt;enumeration value="E41"/>
 *               &lt;enumeration value="E42"/>
 *               &lt;enumeration value="E43"/>
 *               &lt;enumeration value="E44"/>
 *               &lt;enumeration value="E45"/>
 *               &lt;enumeration value="E46"/>
 *               &lt;enumeration value="E47"/>
 *               &lt;enumeration value="E48"/>
 *               &lt;enumeration value="E49"/>
 *               &lt;enumeration value="E50"/>
 *               &lt;enumeration value="E51"/>
 *               &lt;enumeration value="E52"/>
 *               &lt;enumeration value="E53"/>
 *               &lt;enumeration value="E54"/>
 *               &lt;enumeration value="E55"/>
 *               &lt;enumeration value="E56"/>
 *               &lt;enumeration value="E57"/>
 *               &lt;enumeration value="E58"/>
 *               &lt;enumeration value="E59"/>
 *               &lt;enumeration value="E60"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Glosa">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://cfe.dgi.gub.uy}Glosa50Type">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Detalle" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="500"/>
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
@XmlType(name = "RechazoCFE_PartesType", propOrder = {
    "motivo",
    "glosa",
    "detalle"
})
public class RechazoCFEPartesType {

    @XmlElement(name = "Motivo", required = true)
    protected String motivo;
    @XmlElement(name = "Glosa", required = true)
    protected String glosa;
    @XmlElement(name = "Detalle")
    protected String detalle;

    /**
     * Obtiene el valor de la propiedad motivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Define el valor de la propiedad motivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivo(String value) {
        this.motivo = value;
    }

    /**
     * Obtiene el valor de la propiedad glosa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlosa() {
        return glosa;
    }

    /**
     * Define el valor de la propiedad glosa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlosa(String value) {
        this.glosa = value;
    }

    /**
     * Obtiene el valor de la propiedad detalle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * Define el valor de la propiedad detalle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetalle(String value) {
        this.detalle = value;
    }

}
