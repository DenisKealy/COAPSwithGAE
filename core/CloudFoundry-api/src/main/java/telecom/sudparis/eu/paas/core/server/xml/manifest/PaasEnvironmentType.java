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
 * Classe Java pour paas_environmentType complex type.
 * 
 * <p>
 * Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette
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
	 * Obtient la valeur de la propri�t� paasEnvironmentTemplate.
	 * 
	 * @return possible object is {@link PaasEnvironmentTemplateType }
	 * 
	 */
	public PaasEnvironmentTemplateType getPaasEnvironmentTemplate() {
		return paasEnvironmentTemplate;
	}

	/**
	 * D�finit la valeur de la propri�t� paasEnvironmentTemplate.
	 * 
	 * @param value
	 *            allowed object is {@link PaasEnvironmentTemplateType }
	 * 
	 */
	public void setPaasEnvironmentTemplate(PaasEnvironmentTemplateType value) {
		this.paasEnvironmentTemplate = value;
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

	/**
	 * Obtient la valeur de la propri�t� template.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * D�finit la valeur de la propri�t� template.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTemplate(String value) {
		this.template = value;
	}

}
