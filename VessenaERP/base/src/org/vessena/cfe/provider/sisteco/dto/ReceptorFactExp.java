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
 * <p>Clase Java para Receptor_Fact_Exp complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Receptor_Fact_Exp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoDocRecep" type="{http://cfe.dgi.gub.uy}DocType" minOccurs="0"/>
 *         &lt;element name="CodPaisRecep" type="{http://cfe.dgi.gub.uy}CodPaisType" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="DocRecep" type="{http://cfe.dgi.gub.uy}NroDocType" minOccurs="0"/>
 *           &lt;element name="DocRecepExt" minOccurs="0">
 *             &lt;simpleType>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                 &lt;maxLength value="20"/>
 *                 &lt;whiteSpace value="collapse"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element name="RznSocRecep" type="{http://cfe.dgi.gub.uy}RznSocType"/>
 *         &lt;element name="DirRecep" type="{http://cfe.dgi.gub.uy}DireccionType"/>
 *         &lt;element name="CiudadRecep" type="{http://cfe.dgi.gub.uy}CiudadType"/>
 *         &lt;element name="DeptoRecep" type="{http://cfe.dgi.gub.uy}DeptoType"/>
 *         &lt;element name="PaisRecep" type="{http://cfe.dgi.gub.uy}PaisDscType"/>
 *         &lt;element name="CP" type="{http://cfe.dgi.gub.uy}CodPostalType" minOccurs="0"/>
 *         &lt;element name="InfoAdicional" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="150"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="LugarDestEnt" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CompraID" type="{http://cfe.dgi.gub.uy}CompraIDType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Receptor_Fact_Exp", propOrder = {
    "tipoDocRecep",
    "codPaisRecep",
    "docRecep",
    "docRecepExt",
    "rznSocRecep",
    "dirRecep",
    "ciudadRecep",
    "deptoRecep",
    "paisRecep",
    "cp",
    "infoAdicional",
    "lugarDestEnt",
    "compraID"
})
public class ReceptorFactExp {

    @XmlElement(name = "TipoDocRecep")
    protected Integer tipoDocRecep;
    @XmlElement(name = "CodPaisRecep")
    protected String codPaisRecep;
    @XmlElement(name = "DocRecep")
    protected String docRecep;
    @XmlElement(name = "DocRecepExt")
    protected String docRecepExt;
    @XmlElement(name = "RznSocRecep", required = true)
    protected String rznSocRecep;
    @XmlElement(name = "DirRecep", required = true)
    protected String dirRecep;
    @XmlElement(name = "CiudadRecep", required = true)
    protected String ciudadRecep;
    @XmlElement(name = "DeptoRecep", required = true)
    protected String deptoRecep;
    @XmlElement(name = "PaisRecep", required = true)
    protected String paisRecep;
    @XmlElement(name = "CP")
    protected Integer cp;
    @XmlElement(name = "InfoAdicional")
    protected String infoAdicional;
    @XmlElement(name = "LugarDestEnt")
    protected String lugarDestEnt;
    @XmlElement(name = "CompraID")
    protected String compraID;

    /**
     * Obtiene el valor de la propiedad tipoDocRecep.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoDocRecep() {
        return tipoDocRecep;
    }

    /**
     * Define el valor de la propiedad tipoDocRecep.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoDocRecep(Integer value) {
        this.tipoDocRecep = value;
    }

    /**
     * Obtiene el valor de la propiedad codPaisRecep.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodPaisRecep() {
        return codPaisRecep;
    }

    /**
     * Define el valor de la propiedad codPaisRecep.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodPaisRecep(String value) {
        this.codPaisRecep = value;
    }

    /**
     * Obtiene el valor de la propiedad docRecep.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocRecep() {
        return docRecep;
    }

    /**
     * Define el valor de la propiedad docRecep.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocRecep(String value) {
        this.docRecep = value;
    }

    /**
     * Obtiene el valor de la propiedad docRecepExt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocRecepExt() {
        return docRecepExt;
    }

    /**
     * Define el valor de la propiedad docRecepExt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocRecepExt(String value) {
        this.docRecepExt = value;
    }

    /**
     * Obtiene el valor de la propiedad rznSocRecep.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRznSocRecep() {
        return rznSocRecep;
    }

    /**
     * Define el valor de la propiedad rznSocRecep.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRznSocRecep(String value) {
        this.rznSocRecep = value;
    }

    /**
     * Obtiene el valor de la propiedad dirRecep.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirRecep() {
        return dirRecep;
    }

    /**
     * Define el valor de la propiedad dirRecep.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirRecep(String value) {
        this.dirRecep = value;
    }

    /**
     * Obtiene el valor de la propiedad ciudadRecep.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCiudadRecep() {
        return ciudadRecep;
    }

    /**
     * Define el valor de la propiedad ciudadRecep.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCiudadRecep(String value) {
        this.ciudadRecep = value;
    }

    /**
     * Obtiene el valor de la propiedad deptoRecep.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeptoRecep() {
        return deptoRecep;
    }

    /**
     * Define el valor de la propiedad deptoRecep.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeptoRecep(String value) {
        this.deptoRecep = value;
    }

    /**
     * Obtiene el valor de la propiedad paisRecep.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaisRecep() {
        return paisRecep;
    }

    /**
     * Define el valor de la propiedad paisRecep.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaisRecep(String value) {
        this.paisRecep = value;
    }

    /**
     * Obtiene el valor de la propiedad cp.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCP() {
        return cp;
    }

    /**
     * Define el valor de la propiedad cp.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCP(Integer value) {
        this.cp = value;
    }

    /**
     * Obtiene el valor de la propiedad infoAdicional.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfoAdicional() {
        return infoAdicional;
    }

    /**
     * Define el valor de la propiedad infoAdicional.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfoAdicional(String value) {
        this.infoAdicional = value;
    }

    /**
     * Obtiene el valor de la propiedad lugarDestEnt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLugarDestEnt() {
        return lugarDestEnt;
    }

    /**
     * Define el valor de la propiedad lugarDestEnt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLugarDestEnt(String value) {
        this.lugarDestEnt = value;
    }

    /**
     * Obtiene el valor de la propiedad compraID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompraID() {
        return compraID;
    }

    /**
     * Define el valor de la propiedad compraID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompraID(String value) {
        this.compraID = value;
    }

}
