//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2013.04.17 à 07:21:53 PM CEST 
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
 * <p>
 * Classe Java pour applicationType complex type.
 * 
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette
 * classe.
 * 
 * <pre>
 * &lt;complexType name="applicationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uris" type="{}urisType"/>
 *         &lt;element name="deployable" type="{}deployableType"/>
 *         &lt;element name="Instances" type="{}InstancesType"/>
 *         &lt;element name="environment" type="{}environmentType"/>
 *         &lt;element name="linksList" type="{}linksListType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="appName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="appId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="memory" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="checkExists" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="nbInstances" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="envId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "application")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "applicationType", propOrder = { "description", "uris", "deployable",
		"instances", "environment", "linksList" })
public class ApplicationType {

	@XmlElement(required = false)
	protected  String description;
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
	@XmlAttribute(name = "appName")
	protected String appName;
	@XmlAttribute(name = "appId")
	protected Integer appId;
	@XmlAttribute(name = "status")
	protected String status;
	//@XmlAttribute(name = "memory")
	//protected Integer memory;
	//@XmlAttribute(name = "checkExists")
	//protected Boolean checkExists;
	@XmlAttribute(name = "nbInstances")
	protected Integer nbInstances;
	@XmlAttribute(name = "envId")
	protected Integer envId;

	/**
	 * Obtient la valeur de la propriété uris.
	 * 
	 * @return possible object is {@link UrisType }
	 * 
	 */
	public UrisType getUris() {
		return uris;
	}

	/**
	 * Définit la valeur de la propriété uris.
	 * 
	 * @param value
	 *            allowed object is {@link UrisType }
	 * 
	 */
	public void setUris(UrisType value) {
		this.uris = value;
	}

	/**
	 * Obtient la valeur de la propriété deployable.
	 * 
	 * @return possible object is {@link DeployableType }
	 * 
	 */
	public DeployableType getDeployable() {
		return deployable;
	}

	/**
	 * Définit la valeur de la propriété deployable.
	 * 
	 * @param value
	 *            allowed object is {@link DeployableType }
	 * 
	 */
	public void setDeployable(DeployableType value) {
		this.deployable = value;
	}

	/**
	 * Obtient la valeur de la propriété instances.
	 * 
	 * @return possible object is {@link InstancesType }
	 * 
	 */
	public InstancesType getInstances() {
		return instances;
	}

	/**
	 * Définit la valeur de la propriété instances.
	 * 
	 * @param value
	 *            allowed object is {@link InstancesType }
	 * 
	 */
	public void setInstances(InstancesType value) {
		this.instances = value;
	}

	/**
	 * Obtient la valeur de la propriété environment.
	 * 
	 * @return possible object is {@link EnvironmentType }
	 * 
	 */
	public EnvironmentType getEnvironment() {
		return environment;
	}

	/**
	 * Définit la valeur de la propriété environment.
	 * 
	 * @param value
	 *            allowed object is {@link EnvironmentType }
	 * 
	 */
	public void setEnvironment(EnvironmentType value) {
		this.environment = value;
	}

	/**
	 * Obtient la valeur de la propriété linksList.
	 * 
	 * @return possible object is {@link LinksListType }
	 * 
	 */
	public LinksListType getLinksList() {
		return linksList;
	}

	/**
	 * Définit la valeur de la propriété linksList.
	 * 
	 * @param value
	 *            allowed object is {@link LinksListType }
	 * 
	 */
	public void setLinksList(LinksListType value) {
		this.linksList = value;
	}

	/**
	 * Obtient la valeur de la propriété appName.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * Définit la valeur de la propriété appName.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAppName(String value) {
		this.appName = value;
	}

	/**
	 * Obtient la valeur de la propriété appId.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getAppId() {
		return appId;
	}

	/**
	 * Définit la valeur de la propriété appId.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setAppId(Integer value) {
		this.appId = value;
	}

	/**
	 * Obtient la valeur de la propriété status.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Définit la valeur de la propriété status.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setStatus(String value) {
		this.status = value;
	}
//
//	/**
//	 * Obtient la valeur de la propriété memory.
//	 * 
//	 * @return possible object is {@link Integer }
//	 * 
//	 */
//	public Integer getMemory() {
//		return memory;
//	}
//
//	/**
//	 * Définit la valeur de la propriété memory.
//	 * 
//	 * @param value
//	 *            allowed object is {@link Integer }
//	 * 
//	 */
//	public void setMemory(Integer value) {
//		this.memory = value;
//	}
//
//	/**
//	 * Obtient la valeur de la propriété checkExists.
//	 * 
//	 * @return possible object is {@link Boolean }
//	 * 
//	 */
//	public Boolean isCheckExists() {
//		return checkExists;
//	}
//
//	/**
//	 * Définit la valeur de la propriété checkExists.
//	 * 
//	 * @param value
//	 *            allowed object is {@link Boolean }
//	 * 
//	 */
//	public void setCheckExists(Boolean value) {
//		this.checkExists = value;
//	}

	/**
	 * Obtient la valeur de la propriété nbInstances.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getNbInstances() {
		return nbInstances;
	}

	/**
	 * Définit la valeur de la propriété nbInstances.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setNbInstances(Integer value) {
		this.nbInstances = value;
	}

	/**
	 * Obtient la valeur de la propriété envId.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getEnvId() {
		return envId;
	}

	/**
	 * Définit la valeur de la propriété envId.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setEnvId(Integer value) {
		this.envId = value;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
