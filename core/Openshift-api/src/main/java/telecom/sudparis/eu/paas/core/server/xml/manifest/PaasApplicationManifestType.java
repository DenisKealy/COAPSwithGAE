//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.02.26 at 10:10:09 AM CET 
//


package telecom.sudparis.eu.paas.core.server.xml.manifest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paas_application_manifestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paas_application_manifestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paas_application" type="{}paas_applicationType"/>
 *         &lt;element name="paas_environment" type="{}paas_environmentType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_application_manifestType", propOrder = {
    "description",
    "paasApplication",
    "paasEnvironment"
})
public class PaasApplicationManifestType {

    @XmlElement(required = true)
    protected String description;
    @XmlElement(name = "paas_application", required = true)
    protected PaasApplicationType paasApplication;
    @XmlElement(name = "paas_environment", required = true)
    protected PaasEnvironmentType paasEnvironment;
    @XmlAttribute
    protected String name;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the paasApplication property.
     * 
     * @return
     *     possible object is
     *     {@link PaasApplicationType }
     *     
     */
    public PaasApplicationType getPaasApplication() {
        return paasApplication;
    }

    /**
     * Sets the value of the paasApplication property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasApplicationType }
     *     
     */
    public void setPaasApplication(PaasApplicationType value) {
        this.paasApplication = value;
    }

    /**
     * Gets the value of the paasEnvironment property.
     * 
     * @return
     *     possible object is
     *     {@link PaasEnvironmentType }
     *     
     */
    public PaasEnvironmentType getPaasEnvironment() {
        return paasEnvironment;
    }

    /**
     * Sets the value of the paasEnvironment property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasEnvironmentType }
     *     
     */
    public void setPaasEnvironment(PaasEnvironmentType value) {
        this.paasEnvironment = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
