//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.01 a las 04:27:27 PM UYT 
//


package org.openup.cfe.provider.sisteco.dto;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para IdDoc_Rem complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="IdDoc_Rem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoCFE">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://cfe.dgi.gub.uy}CFEType">
 *               &lt;enumeration value="181"/>
 *               &lt;enumeration value="281"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FchEmis" type="{http://cfe.dgi.gub.uy}FechaType"/>
 *         &lt;element name="TipoTraslado">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
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
@XmlType(name = "IdDoc_Rem", propOrder = {
    "tipoCFE",
    "fchEmis",
    "tipoTraslado"
})
public class IdDocRem {

    @XmlElement(name = "TipoCFE", required = true)
    protected BigInteger tipoCFE;
    @XmlElement(name = "FchEmis", required = true)
    protected XMLGregorianCalendar fchEmis;
    @XmlElement(name = "TipoTraslado", required = true)
    protected BigInteger tipoTraslado;

    /**
     * Obtiene el valor de la propiedad tipoCFE.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTipoCFE() {
        return tipoCFE;
    }

    /**
     * Define el valor de la propiedad tipoCFE.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTipoCFE(BigInteger value) {
        this.tipoCFE = value;
    }

    /**
     * Obtiene el valor de la propiedad fchEmis.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFchEmis() {
        return fchEmis;
    }

    /**
     * Define el valor de la propiedad fchEmis.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFchEmis(XMLGregorianCalendar value) {
        this.fchEmis = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoTraslado.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTipoTraslado() {
        return tipoTraslado;
    }

    /**
     * Define el valor de la propiedad tipoTraslado.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTipoTraslado(BigInteger value) {
        this.tipoTraslado = value;
    }

}
