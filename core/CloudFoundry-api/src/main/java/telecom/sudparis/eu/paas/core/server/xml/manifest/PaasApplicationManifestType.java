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
 * Classe Java pour paas_application_manifestType complex type.
 * 
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette
 * classe.
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
@XmlType(name = "paas_application_manifestType", propOrder = { "description",
		"paasApplication", "paasEnvironment" })
public class PaasApplicationManifestType {

	@XmlElement(required = true)
	protected String description;
	@XmlElement(name = "paas_application", required = true)
	protected PaasApplicationType paasApplication;
	@XmlElement(name = "paas_environment", required = true)
	protected PaasEnvironmentType paasEnvironment;
	@XmlAttribute(name = "name")
	protected String name;

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
	 * Obtient la valeur de la propriété paasApplication.
	 * 
	 * @return possible object is {@link PaasApplicationType }
	 * 
	 */
	public PaasApplicationType getPaasApplication() {
		return paasApplication;
	}

	/**
	 * Définit la valeur de la propriété paasApplication.
	 * 
	 * @param value
	 *            allowed object is {@link PaasApplicationType }
	 * 
	 */
	public void setPaasApplication(PaasApplicationType value) {
		this.paasApplication = value;
	}

	/**
	 * Obtient la valeur de la propriété paasEnvironment.
	 * 
	 * @return possible object is {@link PaasEnvironmentType }
	 * 
	 */
	public PaasEnvironmentType getPaasEnvironment() {
		return paasEnvironment;
	}

	/**
	 * Définit la valeur de la propriété paasEnvironment.
	 * 
	 * @param value
	 *            allowed object is {@link PaasEnvironmentType }
	 * 
	 */
	public void setPaasEnvironment(PaasEnvironmentType value) {
		this.paasEnvironment = value;
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

}
