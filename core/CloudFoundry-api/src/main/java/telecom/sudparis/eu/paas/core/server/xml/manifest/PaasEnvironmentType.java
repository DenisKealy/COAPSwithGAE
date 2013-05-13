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
 * Classe Java pour paas_environmentType complex type.
 * 
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette
 * classe.
 * 
 * <pre>
 * &lt;complexType name="paas_environmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paas_environment_template" type="{}paas_environment_templateType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="template" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_environmentType", propOrder = { "paasEnvironmentTemplate" })
public class PaasEnvironmentType {

	@XmlElement(name = "paas_environment_template", required = true)
	protected PaasEnvironmentTemplateType paasEnvironmentTemplate;
	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "template")
	protected String template;

	/**
	 * Obtient la valeur de la propriété paasEnvironmentTemplate.
	 * 
	 * @return possible object is {@link PaasEnvironmentTemplateType }
	 * 
	 */
	public PaasEnvironmentTemplateType getPaasEnvironmentTemplate() {
		return paasEnvironmentTemplate;
	}

	/**
	 * Définit la valeur de la propriété paasEnvironmentTemplate.
	 * 
	 * @param value
	 *            allowed object is {@link PaasEnvironmentTemplateType }
	 * 
	 */
	public void setPaasEnvironmentTemplate(PaasEnvironmentTemplateType value) {
		this.paasEnvironmentTemplate = value;
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
	 * Obtient la valeur de la propriété template.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * Définit la valeur de la propriété template.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTemplate(String value) {
		this.template = value;
	}

}
