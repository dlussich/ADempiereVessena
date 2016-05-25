//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.01.20 a las 03:09:36 PM UYT 
//


package org.openup.cfe.provider.migrate.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="Encabezado" type="{http://www.invoicy.com.uy/}EncabezadoRetornoCFEType"/>
 *         &lt;element name="ListaCFE" type="{http://www.invoicy.com.uy/}ListaCFERetornoType" minOccurs="0"/>
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
    "listaCFE"
})
@XmlRootElement(namespace = "http://www.invoicy.com.uy/", name = "EnvioCFERetorno")
public class EnvioCFERetorno {

    @XmlElement(name = "Encabezado", required = true)
    protected EncabezadoRetornoCFEType encabezado;
    @XmlElement(name = "ListaCFE")
    protected ListaCFERetornoType listaCFE;

    /**
     * Obtiene el valor de la propiedad encabezado.
     * 
     * @return
     *     possible object is
     *     {@link EncabezadoRetornoCFEType }
     *     
     */
    public EncabezadoRetornoCFEType getEncabezado() {
        return encabezado;
    }

    /**
     * Define el valor de la propiedad encabezado.
     * 
     * @param value
     *     allowed object is
     *     {@link EncabezadoRetornoCFEType }
     *     
     */
    public void setEncabezado(EncabezadoRetornoCFEType value) {
        this.encabezado = value;
    }

    /**
     * Obtiene el valor de la propiedad listaCFE.
     * 
     * @return
     *     possible object is
     *     {@link ListaCFERetornoType }
     *     
     */
    public ListaCFERetornoType getListaCFE() {
        return listaCFE;
    }

    /**
     * Define el valor de la propiedad listaCFE.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaCFERetornoType }
     *     
     */
    public void setListaCFE(ListaCFERetornoType value) {
        this.listaCFE = value;
    }

}
