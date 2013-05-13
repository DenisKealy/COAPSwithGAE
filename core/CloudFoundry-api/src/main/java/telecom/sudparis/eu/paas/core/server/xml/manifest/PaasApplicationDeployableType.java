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
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java pour paas_application_deployableType complex type.
 * 
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette
 * classe.
 * 
 * <pre>
 * &lt;complexType name="paas_application_deployableType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="content_type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="location" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="multitenancy_level" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_application_deployableType")
public class PaasApplicationDeployableType {

	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "content_type")
	protected String contentType;
	@XmlAttribute(name = "location")
	protected String location;
	@XmlAttribute(name = "multitenancy_level")
	protected String multitenancyLevel;

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
	 * Obtient la valeur de la propriété contentType.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Définit la valeur de la propriété contentType.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setContentType(String value) {
		this.contentType = value;
	}

	/**
	 * Obtient la valeur de la propriété location.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Définit la valeur de la propriété location.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLocation(String value) {
		this.location = value;
	}

	/**
	 * Obtient la valeur de la propriété multitenancyLevel.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMultitenancyLevel() {
		return multitenancyLevel;
	}

	/**
	 * Définit la valeur de la propriété multitenancyLevel.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMultitenancyLevel(String value) {
		this.multitenancyLevel = value;
	}

}
