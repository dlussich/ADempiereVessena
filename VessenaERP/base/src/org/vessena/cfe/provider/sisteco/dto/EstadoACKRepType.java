//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.01 a las 04:27:27 PM UYT 
//


package org.openup.cfe.provider.sisteco.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para EstadoACKRepType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="EstadoACKRepType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;maxLength value="2"/>
 *     &lt;enumeration value="AR"/>
 *     &lt;enumeration value="BA"/>
 *     &lt;enumeration value="BR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EstadoACKRepType")
@XmlEnum
public enum EstadoACKRepType {

    AR,
    BA,
    BR;

    public String value() {
        return name();
    }

    public static EstadoACKRepType fromValue(String v) {
        return valueOf(v);
    }

}
