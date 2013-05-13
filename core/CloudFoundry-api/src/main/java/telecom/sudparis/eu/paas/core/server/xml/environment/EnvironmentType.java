//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2013.04.17 à 04:43:35 PM CEST 
//

package telecom.sudparis.eu.paas.core.server.xml.environment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import telecom.sudparis.eu.paas.core.server.xml.LinksListType;

/**
 * <p>
 * Classe Java pour environmentType complex type.
 * 
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette
 * classe.
 * 
 * <pre>
 * &lt;complexType name="environmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="configuration" type="{}configurationType"/>
 *         &lt;element name="linksList" type="{}linksListType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="envId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="envName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="envMemory" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="envDesc" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "environment")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "environmentType", propOrder = { "configuration", "linksList" })
public class EnvironmentType {

	@XmlElement(required = true)
	protected ConfigurationType configuration;
	@XmlElement(required = true)
	protected LinksListType linksList;
	@XmlAttribute(name = "envId")
	protected Integer envId;
	@XmlAttribute(name = "envName")
	protected String envName;
	@XmlAttribute(name = "envMemory")
	protected Integer envMemory;
	@XmlAttribute(name = "envDesc")
	protected String envDesc;

	/**
	 * Obtient la valeur de la propriété configuration.
	 * 
	 * @return possible object is {@link ConfigurationType }
	 * 
	 */
	public ConfigurationType getConfiguration() {
		return configuration;
	}

	/**
	 * Définit la valeur de la propriété configuration.
	 * 
	 * @param value
	 *            allowed object is {@link ConfigurationType }
	 * 
	 */
	public void setConfiguration(ConfigurationType value) {
		this.configuration = value;
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

	/**
	 * Obtient la valeur de la propriété envName.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEnvName() {
		return envName;
	}

	/**
	 * Définit la valeur de la propriété envName.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEnvName(String value) {
		this.envName = value;
	}

	/**
	 * Obtient la valeur de la propriété envMemory.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getEnvMemory() {
		return envMemory;
	}

	/**
	 * Définit la valeur de la propriété envMemory.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setEnvMemory(Integer value) {
		this.envMemory = value;
	}

	/**
	 * Obtient la valeur de la propriété envDesc.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEnvDesc() {
		return envDesc;
	}

	/**
	 * Définit la valeur de la propriété envDesc.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEnvDesc(String value) {
		this.envDesc = value;
	}

}
