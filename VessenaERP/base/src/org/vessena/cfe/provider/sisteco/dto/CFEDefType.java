//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.02.01 a las 04:27:27 PM UYT 
//


package org.openup.cfe.provider.sisteco.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Comprobante Fiscal Electronico
 * 
 * <p>Clase Java para CFEDefType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CFEDefType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="eTck">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Encabezado">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Tck"/>
 *                               &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Tck" minOccurs="0"/>
 *                               &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="Detalle">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Det_Fact" maxOccurs="700"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
 *                     &lt;element name="DscRcgGlobal" type="{http://cfe.dgi.gub.uy}DscRcgGlobal" minOccurs="0"/>
 *                     &lt;element name="MediosPago" type="{http://cfe.dgi.gub.uy}MediosPago" minOccurs="0"/>
 *                     &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="eFact">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Encabezado">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Fact"/>
 *                               &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Fact"/>
 *                               &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="Detalle">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Det_Fact" maxOccurs="200"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
 *                     &lt;element name="DscRcgGlobal" type="{http://cfe.dgi.gub.uy}DscRcgGlobal" minOccurs="0"/>
 *                     &lt;element name="MediosPago" type="{http://cfe.dgi.gub.uy}MediosPago" minOccurs="0"/>
 *                     &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="eFact_Exp">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Encabezado">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Fact_Exp"/>
 *                               &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Fact_Exp"/>
 *                               &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales_Fact_Exp"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="Detalle">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Det_Fact_Exp" maxOccurs="200"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
 *                     &lt;element name="DscRcgGlobal" type="{http://cfe.dgi.gub.uy}DscRcgGlobal" minOccurs="0"/>
 *                     &lt;element name="MediosPago" type="{http://cfe.dgi.gub.uy}MediosPago" minOccurs="0"/>
 *                     &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="eRem">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Encabezado">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Rem"/>
 *                               &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Rem"/>
 *                               &lt;element name="Totales">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="CantLinDet">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://cfe.dgi.gub.uy}LineasDetType">
 *                                               &lt;totalDigits value="3"/>
 *                                               &lt;fractionDigits value="0"/>
 *                                               &lt;maxInclusive value="200"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="Detalle">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Rem" maxOccurs="200"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
 *                     &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="eRem_Exp">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Encabezado">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Rem_Exp"/>
 *                               &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Rem_Exp"/>
 *                               &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales_Rem_Exp"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="Detalle">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Rem_Exp" maxOccurs="200"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
 *                     &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="eResg">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="Encabezado">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Resg"/>
 *                               &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Resg"/>
 *                               &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales_Resg"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="Detalle">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Resg" maxOccurs="200"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
 *                     &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" fixed="1.0">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;maxLength value="3"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CFEDefType", propOrder = {
    "eTck",
    "eFact",
    "eFactExp",
    "eRem",
    "eRemExp",
    "eResg"
})
public class CFEDefType {

    protected CFEDefType.ETck eTck;
    protected CFEDefType.EFact eFact;
    @XmlElement(name = "eFact_Exp")
    protected CFEDefType.EFactExp eFactExp;
    protected CFEDefType.ERem eRem;
    @XmlElement(name = "eRem_Exp")
    protected CFEDefType.ERemExp eRemExp;
    protected CFEDefType.EResg eResg;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Obtiene el valor de la propiedad eTck.
     * 
     * @return
     *     possible object is
     *     {@link CFEDefType.ETck }
     *     
     */
    public CFEDefType.ETck getETck() {
        return eTck;
    }

    /**
     * Define el valor de la propiedad eTck.
     * 
     * @param value
     *     allowed object is
     *     {@link CFEDefType.ETck }
     *     
     */
    public void setETck(CFEDefType.ETck value) {
        this.eTck = value;
    }

    /**
     * Obtiene el valor de la propiedad eFact.
     * 
     * @return
     *     possible object is
     *     {@link CFEDefType.EFact }
     *     
     */
    public CFEDefType.EFact getEFact() {
        return eFact;
    }

    /**
     * Define el valor de la propiedad eFact.
     * 
     * @param value
     *     allowed object is
     *     {@link CFEDefType.EFact }
     *     
     */
    public void setEFact(CFEDefType.EFact value) {
        this.eFact = value;
    }

    /**
     * Obtiene el valor de la propiedad eFactExp.
     * 
     * @return
     *     possible object is
     *     {@link CFEDefType.EFactExp }
     *     
     */
    public CFEDefType.EFactExp getEFactExp() {
        return eFactExp;
    }

    /**
     * Define el valor de la propiedad eFactExp.
     * 
     * @param value
     *     allowed object is
     *     {@link CFEDefType.EFactExp }
     *     
     */
    public void setEFactExp(CFEDefType.EFactExp value) {
        this.eFactExp = value;
    }

    /**
     * Obtiene el valor de la propiedad eRem.
     * 
     * @return
     *     possible object is
     *     {@link CFEDefType.ERem }
     *     
     */
    public CFEDefType.ERem getERem() {
        return eRem;
    }

    /**
     * Define el valor de la propiedad eRem.
     * 
     * @param value
     *     allowed object is
     *     {@link CFEDefType.ERem }
     *     
     */
    public void setERem(CFEDefType.ERem value) {
        this.eRem = value;
    }

    /**
     * Obtiene el valor de la propiedad eRemExp.
     * 
     * @return
     *     possible object is
     *     {@link CFEDefType.ERemExp }
     *     
     */
    public CFEDefType.ERemExp getERemExp() {
        return eRemExp;
    }

    /**
     * Define el valor de la propiedad eRemExp.
     * 
     * @param value
     *     allowed object is
     *     {@link CFEDefType.ERemExp }
     *     
     */
    public void setERemExp(CFEDefType.ERemExp value) {
        this.eRemExp = value;
    }

    /**
     * Obtiene el valor de la propiedad eResg.
     * 
     * @return
     *     possible object is
     *     {@link CFEDefType.EResg }
     *     
     */
    public CFEDefType.EResg getEResg() {
        return eResg;
    }

    /**
     * Define el valor de la propiedad eResg.
     * 
     * @param value
     *     allowed object is
     *     {@link CFEDefType.EResg }
     *     
     */
    public void setEResg(CFEDefType.EResg value) {
        this.eResg = value;
    }

    /**
     * Obtiene el valor de la propiedad version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "1.0";
        } else {
            return version;
        }
    }

    /**
     * Define el valor de la propiedad version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }


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
     *         &lt;element name="Encabezado">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Fact"/>
     *                   &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Fact"/>
     *                   &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Detalle">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Det_Fact" maxOccurs="200"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
     *         &lt;element name="DscRcgGlobal" type="{http://cfe.dgi.gub.uy}DscRcgGlobal" minOccurs="0"/>
     *         &lt;element name="MediosPago" type="{http://cfe.dgi.gub.uy}MediosPago" minOccurs="0"/>
     *         &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
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
        "detalle",
        "subTotInfo",
        "dscRcgGlobal",
        "mediosPago",
        "referencia"
    })
    public static class EFact {

        @XmlElement(name = "Encabezado", required = true)
        protected CFEDefType.EFact.Encabezado encabezado;
        @XmlElement(name = "Detalle", required = true)
        protected CFEDefType.EFact.Detalle detalle;
        @XmlElement(name = "SubTotInfo")
        protected SubTotInfo subTotInfo;
        @XmlElement(name = "DscRcgGlobal")
        protected DscRcgGlobal dscRcgGlobal;
        @XmlElement(name = "MediosPago")
        protected MediosPago mediosPago;
        @XmlElement(name = "Referencia")
        protected Referencia referencia;

        /**
         * Obtiene el valor de la propiedad encabezado.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.EFact.Encabezado }
         *     
         */
        public CFEDefType.EFact.Encabezado getEncabezado() {
            return encabezado;
        }

        /**
         * Define el valor de la propiedad encabezado.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.EFact.Encabezado }
         *     
         */
        public void setEncabezado(CFEDefType.EFact.Encabezado value) {
            this.encabezado = value;
        }

        /**
         * Obtiene el valor de la propiedad detalle.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.EFact.Detalle }
         *     
         */
        public CFEDefType.EFact.Detalle getDetalle() {
            return detalle;
        }

        /**
         * Define el valor de la propiedad detalle.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.EFact.Detalle }
         *     
         */
        public void setDetalle(CFEDefType.EFact.Detalle value) {
            this.detalle = value;
        }

        /**
         * Obtiene el valor de la propiedad subTotInfo.
         * 
         * @return
         *     possible object is
         *     {@link SubTotInfo }
         *     
         */
        public SubTotInfo getSubTotInfo() {
            return subTotInfo;
        }

        /**
         * Define el valor de la propiedad subTotInfo.
         * 
         * @param value
         *     allowed object is
         *     {@link SubTotInfo }
         *     
         */
        public void setSubTotInfo(SubTotInfo value) {
            this.subTotInfo = value;
        }

        /**
         * Obtiene el valor de la propiedad dscRcgGlobal.
         * 
         * @return
         *     possible object is
         *     {@link DscRcgGlobal }
         *     
         */
        public DscRcgGlobal getDscRcgGlobal() {
            return dscRcgGlobal;
        }

        /**
         * Define el valor de la propiedad dscRcgGlobal.
         * 
         * @param value
         *     allowed object is
         *     {@link DscRcgGlobal }
         *     
         */
        public void setDscRcgGlobal(DscRcgGlobal value) {
            this.dscRcgGlobal = value;
        }

        /**
         * Obtiene el valor de la propiedad mediosPago.
         * 
         * @return
         *     possible object is
         *     {@link MediosPago }
         *     
         */
        public MediosPago getMediosPago() {
            return mediosPago;
        }

        /**
         * Define el valor de la propiedad mediosPago.
         * 
         * @param value
         *     allowed object is
         *     {@link MediosPago }
         *     
         */
        public void setMediosPago(MediosPago value) {
            this.mediosPago = value;
        }

        /**
         * Obtiene el valor de la propiedad referencia.
         * 
         * @return
         *     possible object is
         *     {@link Referencia }
         *     
         */
        public Referencia getReferencia() {
            return referencia;
        }

        /**
         * Define el valor de la propiedad referencia.
         * 
         * @param value
         *     allowed object is
         *     {@link Referencia }
         *     
         */
        public void setReferencia(Referencia value) {
            this.referencia = value;
        }


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
         *         &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Det_Fact" maxOccurs="200"/>
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
            "item"
        })
        public static class Detalle {

            @XmlElement(name = "Item", required = true)
            protected List<ItemDetFact> item;

            /**
             * Gets the value of the item property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the item property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getItem().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ItemDetFact }
             * 
             * 
             */
            public List<ItemDetFact> getItem() {
                if (item == null) {
                    item = new ArrayList<ItemDetFact>();
                }
                return this.item;
            }

        }


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
         *         &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Fact"/>
         *         &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Fact"/>
         *         &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales"/>
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
            "idDoc",
            "receptor",
            "totales"
        })
        public static class Encabezado {

            @XmlElement(name = "IdDoc", required = true)
            protected IdDocFact idDoc;
            @XmlElement(name = "Receptor", required = true)
            protected ReceptorFact receptor;
            @XmlElement(name = "Totales", required = true)
            protected org.openup.cfe.provider.sisteco.dto.Totales totales;

            /**
             * Obtiene el valor de la propiedad idDoc.
             * 
             * @return
             *     possible object is
             *     {@link IdDocFact }
             *     
             */
            public IdDocFact getIdDoc() {
                return idDoc;
            }

            /**
             * Define el valor de la propiedad idDoc.
             * 
             * @param value
             *     allowed object is
             *     {@link IdDocFact }
             *     
             */
            public void setIdDoc(IdDocFact value) {
                this.idDoc = value;
            }

            /**
             * Obtiene el valor de la propiedad receptor.
             * 
             * @return
             *     possible object is
             *     {@link ReceptorFact }
             *     
             */
            public ReceptorFact getReceptor() {
                return receptor;
            }

            /**
             * Define el valor de la propiedad receptor.
             * 
             * @param value
             *     allowed object is
             *     {@link ReceptorFact }
             *     
             */
            public void setReceptor(ReceptorFact value) {
                this.receptor = value;
            }

            /**
             * Obtiene el valor de la propiedad totales.
             * 
             * @return
             *     possible object is
             *     {@link org.openup.cfe.provider.sisteco.dto.Totales }
             *     
             */
            public org.openup.cfe.provider.sisteco.dto.Totales getTotales() {
                return totales;
            }

            /**
             * Define el valor de la propiedad totales.
             * 
             * @param value
             *     allowed object is
             *     {@link org.openup.cfe.provider.sisteco.dto.Totales }
             *     
             */
            public void setTotales(org.openup.cfe.provider.sisteco.dto.Totales value) {
                this.totales = value;
            }

        }

    }


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
     *         &lt;element name="Encabezado">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Fact_Exp"/>
     *                   &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Fact_Exp"/>
     *                   &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales_Fact_Exp"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Detalle">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Det_Fact_Exp" maxOccurs="200"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
     *         &lt;element name="DscRcgGlobal" type="{http://cfe.dgi.gub.uy}DscRcgGlobal" minOccurs="0"/>
     *         &lt;element name="MediosPago" type="{http://cfe.dgi.gub.uy}MediosPago" minOccurs="0"/>
     *         &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
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
        "detalle",
        "subTotInfo",
        "dscRcgGlobal",
        "mediosPago",
        "referencia"
    })
    public static class EFactExp {

        @XmlElement(name = "Encabezado", required = true)
        protected CFEDefType.EFactExp.Encabezado encabezado;
        @XmlElement(name = "Detalle", required = true)
        protected CFEDefType.EFactExp.Detalle detalle;
        @XmlElement(name = "SubTotInfo")
        protected SubTotInfo subTotInfo;
        @XmlElement(name = "DscRcgGlobal")
        protected DscRcgGlobal dscRcgGlobal;
        @XmlElement(name = "MediosPago")
        protected MediosPago mediosPago;
        @XmlElement(name = "Referencia")
        protected Referencia referencia;

        /**
         * Obtiene el valor de la propiedad encabezado.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.EFactExp.Encabezado }
         *     
         */
        public CFEDefType.EFactExp.Encabezado getEncabezado() {
            return encabezado;
        }

        /**
         * Define el valor de la propiedad encabezado.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.EFactExp.Encabezado }
         *     
         */
        public void setEncabezado(CFEDefType.EFactExp.Encabezado value) {
            this.encabezado = value;
        }

        /**
         * Obtiene el valor de la propiedad detalle.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.EFactExp.Detalle }
         *     
         */
        public CFEDefType.EFactExp.Detalle getDetalle() {
            return detalle;
        }

        /**
         * Define el valor de la propiedad detalle.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.EFactExp.Detalle }
         *     
         */
        public void setDetalle(CFEDefType.EFactExp.Detalle value) {
            this.detalle = value;
        }

        /**
         * Obtiene el valor de la propiedad subTotInfo.
         * 
         * @return
         *     possible object is
         *     {@link SubTotInfo }
         *     
         */
        public SubTotInfo getSubTotInfo() {
            return subTotInfo;
        }

        /**
         * Define el valor de la propiedad subTotInfo.
         * 
         * @param value
         *     allowed object is
         *     {@link SubTotInfo }
         *     
         */
        public void setSubTotInfo(SubTotInfo value) {
            this.subTotInfo = value;
        }

        /**
         * Obtiene el valor de la propiedad dscRcgGlobal.
         * 
         * @return
         *     possible object is
         *     {@link DscRcgGlobal }
         *     
         */
        public DscRcgGlobal getDscRcgGlobal() {
            return dscRcgGlobal;
        }

        /**
         * Define el valor de la propiedad dscRcgGlobal.
         * 
         * @param value
         *     allowed object is
         *     {@link DscRcgGlobal }
         *     
         */
        public void setDscRcgGlobal(DscRcgGlobal value) {
            this.dscRcgGlobal = value;
        }

        /**
         * Obtiene el valor de la propiedad mediosPago.
         * 
         * @return
         *     possible object is
         *     {@link MediosPago }
         *     
         */
        public MediosPago getMediosPago() {
            return mediosPago;
        }

        /**
         * Define el valor de la propiedad mediosPago.
         * 
         * @param value
         *     allowed object is
         *     {@link MediosPago }
         *     
         */
        public void setMediosPago(MediosPago value) {
            this.mediosPago = value;
        }

        /**
         * Obtiene el valor de la propiedad referencia.
         * 
         * @return
         *     possible object is
         *     {@link Referencia }
         *     
         */
        public Referencia getReferencia() {
            return referencia;
        }

        /**
         * Define el valor de la propiedad referencia.
         * 
         * @param value
         *     allowed object is
         *     {@link Referencia }
         *     
         */
        public void setReferencia(Referencia value) {
            this.referencia = value;
        }


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
         *         &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Det_Fact_Exp" maxOccurs="200"/>
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
            "item"
        })
        public static class Detalle {

            @XmlElement(name = "Item", required = true)
            protected List<ItemDetFactExp> item;

            /**
             * Gets the value of the item property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the item property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getItem().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ItemDetFactExp }
             * 
             * 
             */
            public List<ItemDetFactExp> getItem() {
                if (item == null) {
                    item = new ArrayList<ItemDetFactExp>();
                }
                return this.item;
            }

        }


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
         *         &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Fact_Exp"/>
         *         &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Fact_Exp"/>
         *         &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales_Fact_Exp"/>
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
            "idDoc",
            "receptor",
            "totales"
        })
        public static class Encabezado {

            @XmlElement(name = "IdDoc", required = true)
            protected IdDocFactExp idDoc;
            @XmlElement(name = "Receptor", required = true)
            protected ReceptorFactExp receptor;
            @XmlElement(name = "Totales", required = true)
            protected TotalesFactExp totales;

            /**
             * Obtiene el valor de la propiedad idDoc.
             * 
             * @return
             *     possible object is
             *     {@link IdDocFactExp }
             *     
             */
            public IdDocFactExp getIdDoc() {
                return idDoc;
            }

            /**
             * Define el valor de la propiedad idDoc.
             * 
             * @param value
             *     allowed object is
             *     {@link IdDocFactExp }
             *     
             */
            public void setIdDoc(IdDocFactExp value) {
                this.idDoc = value;
            }

            /**
             * Obtiene el valor de la propiedad receptor.
             * 
             * @return
             *     possible object is
             *     {@link ReceptorFactExp }
             *     
             */
            public ReceptorFactExp getReceptor() {
                return receptor;
            }

            /**
             * Define el valor de la propiedad receptor.
             * 
             * @param value
             *     allowed object is
             *     {@link ReceptorFactExp }
             *     
             */
            public void setReceptor(ReceptorFactExp value) {
                this.receptor = value;
            }

            /**
             * Obtiene el valor de la propiedad totales.
             * 
             * @return
             *     possible object is
             *     {@link TotalesFactExp }
             *     
             */
            public TotalesFactExp getTotales() {
                return totales;
            }

            /**
             * Define el valor de la propiedad totales.
             * 
             * @param value
             *     allowed object is
             *     {@link TotalesFactExp }
             *     
             */
            public void setTotales(TotalesFactExp value) {
                this.totales = value;
            }

        }

    }


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
     *         &lt;element name="Encabezado">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Rem"/>
     *                   &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Rem"/>
     *                   &lt;element name="Totales">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="CantLinDet">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://cfe.dgi.gub.uy}LineasDetType">
     *                                   &lt;totalDigits value="3"/>
     *                                   &lt;fractionDigits value="0"/>
     *                                   &lt;maxInclusive value="200"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Detalle">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Rem" maxOccurs="200"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
     *         &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
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
        "detalle",
        "subTotInfo",
        "referencia"
    })
    public static class ERem {

        @XmlElement(name = "Encabezado", required = true)
        protected CFEDefType.ERem.Encabezado encabezado;
        @XmlElement(name = "Detalle", required = true)
        protected CFEDefType.ERem.Detalle detalle;
        @XmlElement(name = "SubTotInfo")
        protected SubTotInfo subTotInfo;
        @XmlElement(name = "Referencia")
        protected Referencia referencia;

        /**
         * Obtiene el valor de la propiedad encabezado.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.ERem.Encabezado }
         *     
         */
        public CFEDefType.ERem.Encabezado getEncabezado() {
            return encabezado;
        }

        /**
         * Define el valor de la propiedad encabezado.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.ERem.Encabezado }
         *     
         */
        public void setEncabezado(CFEDefType.ERem.Encabezado value) {
            this.encabezado = value;
        }

        /**
         * Obtiene el valor de la propiedad detalle.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.ERem.Detalle }
         *     
         */
        public CFEDefType.ERem.Detalle getDetalle() {
            return detalle;
        }

        /**
         * Define el valor de la propiedad detalle.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.ERem.Detalle }
         *     
         */
        public void setDetalle(CFEDefType.ERem.Detalle value) {
            this.detalle = value;
        }

        /**
         * Obtiene el valor de la propiedad subTotInfo.
         * 
         * @return
         *     possible object is
         *     {@link SubTotInfo }
         *     
         */
        public SubTotInfo getSubTotInfo() {
            return subTotInfo;
        }

        /**
         * Define el valor de la propiedad subTotInfo.
         * 
         * @param value
         *     allowed object is
         *     {@link SubTotInfo }
         *     
         */
        public void setSubTotInfo(SubTotInfo value) {
            this.subTotInfo = value;
        }

        /**
         * Obtiene el valor de la propiedad referencia.
         * 
         * @return
         *     possible object is
         *     {@link Referencia }
         *     
         */
        public Referencia getReferencia() {
            return referencia;
        }

        /**
         * Define el valor de la propiedad referencia.
         * 
         * @param value
         *     allowed object is
         *     {@link Referencia }
         *     
         */
        public void setReferencia(Referencia value) {
            this.referencia = value;
        }


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
         *         &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Rem" maxOccurs="200"/>
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
            "item"
        })
        public static class Detalle {

            @XmlElement(name = "Item", required = true)
            protected List<ItemRem> item;

            /**
             * Gets the value of the item property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the item property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getItem().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ItemRem }
             * 
             * 
             */
            public List<ItemRem> getItem() {
                if (item == null) {
                    item = new ArrayList<ItemRem>();
                }
                return this.item;
            }

        }


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
         *         &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Rem"/>
         *         &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Rem"/>
         *         &lt;element name="Totales">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="CantLinDet">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://cfe.dgi.gub.uy}LineasDetType">
         *                         &lt;totalDigits value="3"/>
         *                         &lt;fractionDigits value="0"/>
         *                         &lt;maxInclusive value="200"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
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
        @XmlType(name = "", propOrder = {
            "idDoc",
            "receptor",
            "totales"
        })
        public static class Encabezado {

            @XmlElement(name = "IdDoc", required = true)
            protected IdDocRem idDoc;
            @XmlElement(name = "Receptor", required = true)
            protected ReceptorRem receptor;
            @XmlElement(name = "Totales", required = true)
            protected CFEDefType.ERem.Encabezado.Totales totales;

            /**
             * Obtiene el valor de la propiedad idDoc.
             * 
             * @return
             *     possible object is
             *     {@link IdDocRem }
             *     
             */
            public IdDocRem getIdDoc() {
                return idDoc;
            }

            /**
             * Define el valor de la propiedad idDoc.
             * 
             * @param value
             *     allowed object is
             *     {@link IdDocRem }
             *     
             */
            public void setIdDoc(IdDocRem value) {
                this.idDoc = value;
            }

            /**
             * Obtiene el valor de la propiedad receptor.
             * 
             * @return
             *     possible object is
             *     {@link ReceptorRem }
             *     
             */
            public ReceptorRem getReceptor() {
                return receptor;
            }

            /**
             * Define el valor de la propiedad receptor.
             * 
             * @param value
             *     allowed object is
             *     {@link ReceptorRem }
             *     
             */
            public void setReceptor(ReceptorRem value) {
                this.receptor = value;
            }

            /**
             * Obtiene el valor de la propiedad totales.
             * 
             * @return
             *     possible object is
             *     {@link CFEDefType.ERem.Encabezado.Totales }
             *     
             */
            public CFEDefType.ERem.Encabezado.Totales getTotales() {
                return totales;
            }

            /**
             * Define el valor de la propiedad totales.
             * 
             * @param value
             *     allowed object is
             *     {@link CFEDefType.ERem.Encabezado.Totales }
             *     
             */
            public void setTotales(CFEDefType.ERem.Encabezado.Totales value) {
                this.totales = value;
            }


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
             *         &lt;element name="CantLinDet">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://cfe.dgi.gub.uy}LineasDetType">
             *               &lt;totalDigits value="3"/>
             *               &lt;fractionDigits value="0"/>
             *               &lt;maxInclusive value="200"/>
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
            @XmlType(name = "", propOrder = {
                "cantLinDet"
            })
            public static class Totales {

                @XmlElement(name = "CantLinDet")
                protected int cantLinDet;

                /**
                 * Obtiene el valor de la propiedad cantLinDet.
                 * 
                 */
                public int getCantLinDet() {
                    return cantLinDet;
                }

                /**
                 * Define el valor de la propiedad cantLinDet.
                 * 
                 */
                public void setCantLinDet(int value) {
                    this.cantLinDet = value;
                }

            }

        }

    }


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
     *         &lt;element name="Encabezado">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Rem_Exp"/>
     *                   &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Rem_Exp"/>
     *                   &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales_Rem_Exp"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Detalle">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Rem_Exp" maxOccurs="200"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
     *         &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
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
        "detalle",
        "subTotInfo",
        "referencia"
    })
    public static class ERemExp {

        @XmlElement(name = "Encabezado", required = true)
        protected CFEDefType.ERemExp.Encabezado encabezado;
        @XmlElement(name = "Detalle", required = true)
        protected CFEDefType.ERemExp.Detalle detalle;
        @XmlElement(name = "SubTotInfo")
        protected SubTotInfo subTotInfo;
        @XmlElement(name = "Referencia")
        protected Referencia referencia;

        /**
         * Obtiene el valor de la propiedad encabezado.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.ERemExp.Encabezado }
         *     
         */
        public CFEDefType.ERemExp.Encabezado getEncabezado() {
            return encabezado;
        }

        /**
         * Define el valor de la propiedad encabezado.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.ERemExp.Encabezado }
         *     
         */
        public void setEncabezado(CFEDefType.ERemExp.Encabezado value) {
            this.encabezado = value;
        }

        /**
         * Obtiene el valor de la propiedad detalle.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.ERemExp.Detalle }
         *     
         */
        public CFEDefType.ERemExp.Detalle getDetalle() {
            return detalle;
        }

        /**
         * Define el valor de la propiedad detalle.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.ERemExp.Detalle }
         *     
         */
        public void setDetalle(CFEDefType.ERemExp.Detalle value) {
            this.detalle = value;
        }

        /**
         * Obtiene el valor de la propiedad subTotInfo.
         * 
         * @return
         *     possible object is
         *     {@link SubTotInfo }
         *     
         */
        public SubTotInfo getSubTotInfo() {
            return subTotInfo;
        }

        /**
         * Define el valor de la propiedad subTotInfo.
         * 
         * @param value
         *     allowed object is
         *     {@link SubTotInfo }
         *     
         */
        public void setSubTotInfo(SubTotInfo value) {
            this.subTotInfo = value;
        }

        /**
         * Obtiene el valor de la propiedad referencia.
         * 
         * @return
         *     possible object is
         *     {@link Referencia }
         *     
         */
        public Referencia getReferencia() {
            return referencia;
        }

        /**
         * Define el valor de la propiedad referencia.
         * 
         * @param value
         *     allowed object is
         *     {@link Referencia }
         *     
         */
        public void setReferencia(Referencia value) {
            this.referencia = value;
        }


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
         *         &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Rem_Exp" maxOccurs="200"/>
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
            "item"
        })
        public static class Detalle {

            @XmlElement(name = "Item", required = true)
            protected List<ItemRemExp> item;

            /**
             * Gets the value of the item property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the item property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getItem().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ItemRemExp }
             * 
             * 
             */
            public List<ItemRemExp> getItem() {
                if (item == null) {
                    item = new ArrayList<ItemRemExp>();
                }
                return this.item;
            }

        }


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
         *         &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Rem_Exp"/>
         *         &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Rem_Exp"/>
         *         &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales_Rem_Exp"/>
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
            "idDoc",
            "receptor",
            "totales"
        })
        public static class Encabezado {

            @XmlElement(name = "IdDoc", required = true)
            protected IdDocRemExp idDoc;
            @XmlElement(name = "Receptor", required = true)
            protected ReceptorRemExp receptor;
            @XmlElement(name = "Totales", required = true)
            protected TotalesRemExp totales;

            /**
             * Obtiene el valor de la propiedad idDoc.
             * 
             * @return
             *     possible object is
             *     {@link IdDocRemExp }
             *     
             */
            public IdDocRemExp getIdDoc() {
                return idDoc;
            }

            /**
             * Define el valor de la propiedad idDoc.
             * 
             * @param value
             *     allowed object is
             *     {@link IdDocRemExp }
             *     
             */
            public void setIdDoc(IdDocRemExp value) {
                this.idDoc = value;
            }

            /**
             * Obtiene el valor de la propiedad receptor.
             * 
             * @return
             *     possible object is
             *     {@link ReceptorRemExp }
             *     
             */
            public ReceptorRemExp getReceptor() {
                return receptor;
            }

            /**
             * Define el valor de la propiedad receptor.
             * 
             * @param value
             *     allowed object is
             *     {@link ReceptorRemExp }
             *     
             */
            public void setReceptor(ReceptorRemExp value) {
                this.receptor = value;
            }

            /**
             * Obtiene el valor de la propiedad totales.
             * 
             * @return
             *     possible object is
             *     {@link TotalesRemExp }
             *     
             */
            public TotalesRemExp getTotales() {
                return totales;
            }

            /**
             * Define el valor de la propiedad totales.
             * 
             * @param value
             *     allowed object is
             *     {@link TotalesRemExp }
             *     
             */
            public void setTotales(TotalesRemExp value) {
                this.totales = value;
            }

        }

    }


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
     *         &lt;element name="Encabezado">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Resg"/>
     *                   &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Resg"/>
     *                   &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales_Resg"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Detalle">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Resg" maxOccurs="200"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
     *         &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
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
        "detalle",
        "subTotInfo",
        "referencia"
    })
    public static class EResg {

        @XmlElement(name = "Encabezado", required = true)
        protected CFEDefType.EResg.Encabezado encabezado;
        @XmlElement(name = "Detalle", required = true)
        protected CFEDefType.EResg.Detalle detalle;
        @XmlElement(name = "SubTotInfo")
        protected SubTotInfo subTotInfo;
        @XmlElement(name = "Referencia")
        protected Referencia referencia;

        /**
         * Obtiene el valor de la propiedad encabezado.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.EResg.Encabezado }
         *     
         */
        public CFEDefType.EResg.Encabezado getEncabezado() {
            return encabezado;
        }

        /**
         * Define el valor de la propiedad encabezado.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.EResg.Encabezado }
         *     
         */
        public void setEncabezado(CFEDefType.EResg.Encabezado value) {
            this.encabezado = value;
        }

        /**
         * Obtiene el valor de la propiedad detalle.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.EResg.Detalle }
         *     
         */
        public CFEDefType.EResg.Detalle getDetalle() {
            return detalle;
        }

        /**
         * Define el valor de la propiedad detalle.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.EResg.Detalle }
         *     
         */
        public void setDetalle(CFEDefType.EResg.Detalle value) {
            this.detalle = value;
        }

        /**
         * Obtiene el valor de la propiedad subTotInfo.
         * 
         * @return
         *     possible object is
         *     {@link SubTotInfo }
         *     
         */
        public SubTotInfo getSubTotInfo() {
            return subTotInfo;
        }

        /**
         * Define el valor de la propiedad subTotInfo.
         * 
         * @param value
         *     allowed object is
         *     {@link SubTotInfo }
         *     
         */
        public void setSubTotInfo(SubTotInfo value) {
            this.subTotInfo = value;
        }

        /**
         * Obtiene el valor de la propiedad referencia.
         * 
         * @return
         *     possible object is
         *     {@link Referencia }
         *     
         */
        public Referencia getReferencia() {
            return referencia;
        }

        /**
         * Define el valor de la propiedad referencia.
         * 
         * @param value
         *     allowed object is
         *     {@link Referencia }
         *     
         */
        public void setReferencia(Referencia value) {
            this.referencia = value;
        }


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
         *         &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Resg" maxOccurs="200"/>
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
            "item"
        })
        public static class Detalle {

            @XmlElement(name = "Item", required = true)
            protected List<ItemResg> item;

            /**
             * Gets the value of the item property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the item property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getItem().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ItemResg }
             * 
             * 
             */
            public List<ItemResg> getItem() {
                if (item == null) {
                    item = new ArrayList<ItemResg>();
                }
                return this.item;
            }

        }


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
         *         &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Resg"/>
         *         &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Resg"/>
         *         &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales_Resg"/>
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
            "idDoc",
            "receptor",
            "totales"
        })
        public static class Encabezado {

            @XmlElement(name = "IdDoc", required = true)
            protected IdDocResg idDoc;
            @XmlElement(name = "Receptor", required = true)
            protected ReceptorResg receptor;
            @XmlElement(name = "Totales", required = true)
            protected TotalesResg totales;

            /**
             * Obtiene el valor de la propiedad idDoc.
             * 
             * @return
             *     possible object is
             *     {@link IdDocResg }
             *     
             */
            public IdDocResg getIdDoc() {
                return idDoc;
            }

            /**
             * Define el valor de la propiedad idDoc.
             * 
             * @param value
             *     allowed object is
             *     {@link IdDocResg }
             *     
             */
            public void setIdDoc(IdDocResg value) {
                this.idDoc = value;
            }

            /**
             * Obtiene el valor de la propiedad receptor.
             * 
             * @return
             *     possible object is
             *     {@link ReceptorResg }
             *     
             */
            public ReceptorResg getReceptor() {
                return receptor;
            }

            /**
             * Define el valor de la propiedad receptor.
             * 
             * @param value
             *     allowed object is
             *     {@link ReceptorResg }
             *     
             */
            public void setReceptor(ReceptorResg value) {
                this.receptor = value;
            }

            /**
             * Obtiene el valor de la propiedad totales.
             * 
             * @return
             *     possible object is
             *     {@link TotalesResg }
             *     
             */
            public TotalesResg getTotales() {
                return totales;
            }

            /**
             * Define el valor de la propiedad totales.
             * 
             * @param value
             *     allowed object is
             *     {@link TotalesResg }
             *     
             */
            public void setTotales(TotalesResg value) {
                this.totales = value;
            }

        }

    }


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
     *         &lt;element name="Encabezado">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Tck"/>
     *                   &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Tck" minOccurs="0"/>
     *                   &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Detalle">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Det_Fact" maxOccurs="700"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SubTotInfo" type="{http://cfe.dgi.gub.uy}SubTotInfo" minOccurs="0"/>
     *         &lt;element name="DscRcgGlobal" type="{http://cfe.dgi.gub.uy}DscRcgGlobal" minOccurs="0"/>
     *         &lt;element name="MediosPago" type="{http://cfe.dgi.gub.uy}MediosPago" minOccurs="0"/>
     *         &lt;element name="Referencia" type="{http://cfe.dgi.gub.uy}Referencia" minOccurs="0"/>
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
        "detalle",
        "subTotInfo",
        "dscRcgGlobal",
        "mediosPago",
        "referencia"
    })
    public static class ETck {

        @XmlElement(name = "Encabezado", required = true)
        protected CFEDefType.ETck.Encabezado encabezado;
        @XmlElement(name = "Detalle", required = true)
        protected CFEDefType.ETck.Detalle detalle;
        @XmlElement(name = "SubTotInfo")
        protected SubTotInfo subTotInfo;
        @XmlElement(name = "DscRcgGlobal")
        protected DscRcgGlobal dscRcgGlobal;
        @XmlElement(name = "MediosPago")
        protected MediosPago mediosPago;
        @XmlElement(name = "Referencia")
        protected Referencia referencia;

        /**
         * Obtiene el valor de la propiedad encabezado.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.ETck.Encabezado }
         *     
         */
        public CFEDefType.ETck.Encabezado getEncabezado() {
            return encabezado;
        }

        /**
         * Define el valor de la propiedad encabezado.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.ETck.Encabezado }
         *     
         */
        public void setEncabezado(CFEDefType.ETck.Encabezado value) {
            this.encabezado = value;
        }

        /**
         * Obtiene el valor de la propiedad detalle.
         * 
         * @return
         *     possible object is
         *     {@link CFEDefType.ETck.Detalle }
         *     
         */
        public CFEDefType.ETck.Detalle getDetalle() {
            return detalle;
        }

        /**
         * Define el valor de la propiedad detalle.
         * 
         * @param value
         *     allowed object is
         *     {@link CFEDefType.ETck.Detalle }
         *     
         */
        public void setDetalle(CFEDefType.ETck.Detalle value) {
            this.detalle = value;
        }

        /**
         * Obtiene el valor de la propiedad subTotInfo.
         * 
         * @return
         *     possible object is
         *     {@link SubTotInfo }
         *     
         */
        public SubTotInfo getSubTotInfo() {
            return subTotInfo;
        }

        /**
         * Define el valor de la propiedad subTotInfo.
         * 
         * @param value
         *     allowed object is
         *     {@link SubTotInfo }
         *     
         */
        public void setSubTotInfo(SubTotInfo value) {
            this.subTotInfo = value;
        }

        /**
         * Obtiene el valor de la propiedad dscRcgGlobal.
         * 
         * @return
         *     possible object is
         *     {@link DscRcgGlobal }
         *     
         */
        public DscRcgGlobal getDscRcgGlobal() {
            return dscRcgGlobal;
        }

        /**
         * Define el valor de la propiedad dscRcgGlobal.
         * 
         * @param value
         *     allowed object is
         *     {@link DscRcgGlobal }
         *     
         */
        public void setDscRcgGlobal(DscRcgGlobal value) {
            this.dscRcgGlobal = value;
        }

        /**
         * Obtiene el valor de la propiedad mediosPago.
         * 
         * @return
         *     possible object is
         *     {@link MediosPago }
         *     
         */
        public MediosPago getMediosPago() {
            return mediosPago;
        }

        /**
         * Define el valor de la propiedad mediosPago.
         * 
         * @param value
         *     allowed object is
         *     {@link MediosPago }
         *     
         */
        public void setMediosPago(MediosPago value) {
            this.mediosPago = value;
        }

        /**
         * Obtiene el valor de la propiedad referencia.
         * 
         * @return
         *     possible object is
         *     {@link Referencia }
         *     
         */
        public Referencia getReferencia() {
            return referencia;
        }

        /**
         * Define el valor de la propiedad referencia.
         * 
         * @param value
         *     allowed object is
         *     {@link Referencia }
         *     
         */
        public void setReferencia(Referencia value) {
            this.referencia = value;
        }


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
         *         &lt;element name="Item" type="{http://cfe.dgi.gub.uy}Item_Det_Fact" maxOccurs="700"/>
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
            "item"
        })
        public static class Detalle {

            @XmlElement(name = "Item", required = true)
            protected List<ItemDetFact> item;

            /**
             * Gets the value of the item property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the item property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getItem().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ItemDetFact }
             * 
             * 
             */
            public List<ItemDetFact> getItem() {
                if (item == null) {
                    item = new ArrayList<ItemDetFact>();
                }
                return this.item;
            }

        }


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
         *         &lt;element name="IdDoc" type="{http://cfe.dgi.gub.uy}IdDoc_Tck"/>
         *         &lt;element name="Receptor" type="{http://cfe.dgi.gub.uy}Receptor_Tck" minOccurs="0"/>
         *         &lt;element name="Totales" type="{http://cfe.dgi.gub.uy}Totales"/>
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
            "idDoc",
            "receptor",
            "totales"
        })
        public static class Encabezado {

            @XmlElement(name = "IdDoc", required = true)
            protected IdDocTck idDoc;
            @XmlElement(name = "Receptor")
            protected ReceptorTck receptor;
            @XmlElement(name = "Totales", required = true)
            protected org.openup.cfe.provider.sisteco.dto.Totales totales;

            /**
             * Obtiene el valor de la propiedad idDoc.
             * 
             * @return
             *     possible object is
             *     {@link IdDocTck }
             *     
             */
            public IdDocTck getIdDoc() {
                return idDoc;
            }

            /**
             * Define el valor de la propiedad idDoc.
             * 
             * @param value
             *     allowed object is
             *     {@link IdDocTck }
             *     
             */
            public void setIdDoc(IdDocTck value) {
                this.idDoc = value;
            }

            /**
             * Obtiene el valor de la propiedad receptor.
             * 
             * @return
             *     possible object is
             *     {@link ReceptorTck }
             *     
             */
            public ReceptorTck getReceptor() {
                return receptor;
            }

            /**
             * Define el valor de la propiedad receptor.
             * 
             * @param value
             *     allowed object is
             *     {@link ReceptorTck }
             *     
             */
            public void setReceptor(ReceptorTck value) {
                this.receptor = value;
            }

            /**
             * Obtiene el valor de la propiedad totales.
             * 
             * @return
             *     possible object is
             *     {@link org.openup.cfe.provider.sisteco.dto.Totales }
             *     
             */
            public org.openup.cfe.provider.sisteco.dto.Totales getTotales() {
                return totales;
            }

            /**
             * Define el valor de la propiedad totales.
             * 
             * @param value
             *     allowed object is
             *     {@link org.openup.cfe.provider.sisteco.dto.Totales }
             *     
             */
            public void setTotales(org.openup.cfe.provider.sisteco.dto.Totales value) {
                this.totales = value;
            }

        }

    }

}
