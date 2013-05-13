//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2013.04.18 à 05:02:15 PM CEST 
//

package telecom.sudparis.eu.paas.core.server.xml.manifest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java pour paas_applicationType complex type.
 * 
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette
 * classe.
 * 
 * <pre>
 * &lt;complexType name="paas_applicationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paas_application_version" type="{}paas_application_versionType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="environment" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_applicationType", propOrder = { "description",
		"paasApplicationVersion" })
public class PaasApplicationType {

	@XmlElement(required = true)
	protected String description;
	@XmlElement(name = "paas_application_version", required = true)
	protected PaasApplicationVersionType paasApplicationVersion;
	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "environment")
	protected String environment;

	/**
	 * Obtient la valeur de la propriété description.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Définit la valeur de la propriété description.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Obtient la valeur de la propriété paasApplicationVersion.
	 * 
	 * @return possible object is {@link PaasApplicationVersionType }
	 * 
	 */
	public PaasApplicationVersionType getPaasApplicationVersion() {
		return paasApplicationVersion;
	}

	/**
	 * Définit la valeur de la propriété paasApplicationVersion.
	 * 
	 * @param value
	 *            allowed object is {@link PaasApplicationVersionType }
	 * 
	 */
	public void setPaasApplicationVersion(PaasApplicationVersionType value) {
		this.paasApplicationVersion = value;
	}

	/**
	 * Obtient la valeur de la propriété name.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Définit la valeur de la propriété name.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Obtient la valeur de la propriété environment.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEnvironment() {
		return environment;
	}

	/**
	 * Définit la valeur de la propriété environment.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEnvironment(String value) {
		this.environment = value;
	}

}
