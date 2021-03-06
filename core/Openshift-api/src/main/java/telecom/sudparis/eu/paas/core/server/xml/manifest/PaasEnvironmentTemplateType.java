//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.02.26 at 10:10:09 AM CET 
//


package telecom.sudparis.eu.paas.core.server.xml.manifest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paas_environment_templateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paas_environment_templateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paas_environment_node" type="{}paas_environment_nodeType"/>
 *         &lt;element name="paas_environment_relation" type="{}paas_environment_relationType"/>
 *         &lt;element name="paas_environment_configuration" type="{}paas_environment_configurationType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="memory" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="disk" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_environment_templateType", propOrder = {
    "description",
    "paasEnvironmentNode",
    "paasEnvironmentRelation",
    "paasEnvironmentConfiguration"
})
public class PaasEnvironmentTemplateType {

    @XmlElement(required = true)
    protected String description;
    @XmlElement(name = "paas_environment_node", required = true)
    protected List<PaasEnvironmentNodeType> paasEnvironmentNode;
    @XmlElement(name = "paas_environment_relation", required = true)
    protected PaasEnvironmentRelationType paasEnvironmentRelation;
    @XmlElement(name = "paas_environment_configuration", required = true)
    protected PaasEnvironmentConfigurationType paasEnvironmentConfiguration;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    protected String memory;
    @XmlAttribute
    protected String disk;

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
     * Gets the value of the paasEnvironmentNode property.
     * 
     * @return
     *     possible object is
     *     {@link PaasEnvironmentNodeType }
     *     
     */
    public List<PaasEnvironmentNodeType> getPaasEnvironmentNode() {
        return paasEnvironmentNode;
    }

    /**
     * Sets the value of the paasEnvironmentNode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasEnvironmentNodeType }
     *     
     */
    public void setPaasEnvironmentNode(List<PaasEnvironmentNodeType> value) {
        this.paasEnvironmentNode = value;
    }

    /**
     * Gets the value of the paasEnvironmentRelation property.
     * 
     * @return
     *     possible object is
     *     {@link PaasEnvironmentRelationType }
     *     
     */
    public PaasEnvironmentRelationType getPaasEnvironmentRelation() {
        return paasEnvironmentRelation;
    }

    /**
     * Sets the value of the paasEnvironmentRelation property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasEnvironmentRelationType }
     *     
     */
    public void setPaasEnvironmentRelation(PaasEnvironmentRelationType value) {
        this.paasEnvironmentRelation = value;
    }

    /**
     * Gets the value of the paasEnvironmentConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link PaasEnvironmentConfigurationType }
     *     
     */
    public PaasEnvironmentConfigurationType getPaasEnvironmentConfiguration() {
        return paasEnvironmentConfiguration;
    }

    /**
     * Sets the value of the paasEnvironmentConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasEnvironmentConfigurationType }
     *     
     */
    public void setPaasEnvironmentConfiguration(PaasEnvironmentConfigurationType value) {
        this.paasEnvironmentConfiguration = value;
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

    /**
     * Gets the value of the memory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemory() {
        return memory;
    }

    /**
     * Sets the value of the memory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemory(String value) {
        this.memory = value;
    }

    /**
     * Gets the value of the disk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisk() {
        return disk;
    }

    /**
     * Sets the value of the disk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisk(String value) {
        this.disk = value;
    }

}
