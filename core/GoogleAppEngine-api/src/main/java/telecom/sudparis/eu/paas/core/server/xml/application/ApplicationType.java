//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.07.31 at 01:34:22 � EEST 
//


package telecom.sudparis.eu.paas.core.server.xml.application;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import telecom.sudparis.eu.paas.core.server.xml.LinksListType;
import telecom.sudparis.eu.paas.core.server.xml.environment.EnvironmentType;


/**
 * <p>Java class for applicationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="applicationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uris" type="{}urisType"/>
 *         &lt;element name="deployable" type="{}deployableType"/>
 *         &lt;element name="Instances" type="{}InstancesType"/>
 *         &lt;element ref="{}environment"/>
 *         &lt;element name="linksList" type="{}linksListType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="appId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="appName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="envId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="nbInstances" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "application")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "applicationType", propOrder = {
    "description",
    "uris",
    "deployable",
    "instances",
    "environment",
    "linksList"
})
public class ApplicationType {

    protected String description;
    @XmlElement(required = true)
    protected UrisType uris;
    @XmlElement(required = true)
    protected DeployableType deployable;
    @XmlElement(name = "Instances", required = true)
    protected InstancesType instances;
    @XmlElement(required = true)
    protected EnvironmentType environment;
    @XmlElement(required = true)
    protected LinksListType linksList;
    @XmlAttribute
    protected Integer appId;
    @XmlAttribute
    protected String appName;
    @XmlAttribute
    protected Integer envId;
    @XmlAttribute
    protected Integer nbInstances;
    @XmlAttribute
    protected String status;

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
     * Gets the value of the uris property.
     * 
     * @return
     *     possible object is
     *     {@link UrisType }
     *     
     */
    public UrisType getUris() {
        return uris;
    }

    /**
     * Sets the value of the uris property.
     * 
     * @param value
     *     allowed object is
     *     {@link UrisType }
     *     
     */
    public void setUris(UrisType value) {
        this.uris = value;
    }

    /**
     * Gets the value of the deployable property.
     * 
     * @return
     *     possible object is
     *     {@link DeployableType }
     *     
     */
    public DeployableType getDeployable() {
        return deployable;
    }

    /**
     * Sets the value of the deployable property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeployableType }
     *     
     */
    public void setDeployable(DeployableType value) {
        this.deployable = value;
    }

    /**
     * Gets the value of the instances property.
     * 
     * @return
     *     possible object is
     *     {@link InstancesType }
     *     
     */
    public InstancesType getInstances() {
        return instances;
    }

    /**
     * Sets the value of the instances property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstancesType }
     *     
     */
    public void setInstances(InstancesType value) {
        this.instances = value;
    }

    /**
     * Gets the value of the environment property.
     * 
     * @return
     *     possible object is
     *     {@link EnvironmentType }
     *     
     */
    public EnvironmentType getEnvironment() {
        return environment;
    }

    /**
     * Sets the value of the environment property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnvironmentType }
     *     
     */
    public void setEnvironment(EnvironmentType value) {
        this.environment = value;
    }

    /**
     * Gets the value of the linksList property.
     * 
     * @return
     *     possible object is
     *     {@link LinksListType }
     *     
     */
    public LinksListType getLinksList() {
        return linksList;
    }

    /**
     * Sets the value of the linksList property.
     * 
     * @param value
     *     allowed object is
     *     {@link LinksListType }
     *     
     */
    public void setLinksList(LinksListType value) {
        this.linksList = value;
    }

    /**
     * Gets the value of the appId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAppId() {
        return appId;
    }

    /**
     * Sets the value of the appId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAppId(Integer value) {
        this.appId = value;
    }

    /**
     * Gets the value of the appName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Sets the value of the appName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppName(String value) {
        this.appName = value;
    }

    /**
     * Gets the value of the envId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEnvId() {
        return envId;
    }

    /**
     * Sets the value of the envId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEnvId(Integer value) {
        this.envId = value;
    }

    /**
     * Gets the value of the nbInstances property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNbInstances() {
        return nbInstances;
    }

    /**
     * Sets the value of the nbInstances property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNbInstances(Integer value) {
        this.nbInstances = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}