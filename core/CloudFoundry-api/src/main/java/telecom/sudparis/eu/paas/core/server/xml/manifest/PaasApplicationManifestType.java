//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2013.04.18 � 05:02:15 PM CEST 
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
 * Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette
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
	 * Obtient la valeur de la propri�t� description.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * D�finit la valeur de la propri�t� description.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Obtient la valeur de la propri�t� paasApplication.
	 * 
	 * @return possible object is {@link PaasApplicationType }
	 * 
	 */
	public PaasApplicationType getPaasApplication() {
		return paasApplication;
	}

	/**
	 * D�finit la valeur de la propri�t� paasApplication.
	 * 
	 * @param value
	 *            allowed object is {@link PaasApplicationType }
	 * 
	 */
	public void setPaasApplication(PaasApplicationType value) {
		this.paasApplication = value;
	}

	/**
	 * Obtient la valeur de la propri�t� paasEnvironment.
	 * 
	 * @return possible object is {@link PaasEnvironmentType }
	 * 
	 */
	public PaasEnvironmentType getPaasEnvironment() {
		return paasEnvironment;
	}

	/**
	 * D�finit la valeur de la propri�t� paasEnvironment.
	 * 
	 * @param value
	 *            allowed object is {@link PaasEnvironmentType }
	 * 
	 */
	public void setPaasEnvironment(PaasEnvironmentType value) {
		this.paasEnvironment = value;
	}

	/**
	 * Obtient la valeur de la propri�t� name.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * D�finit la valeur de la propri�t� name.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

}
