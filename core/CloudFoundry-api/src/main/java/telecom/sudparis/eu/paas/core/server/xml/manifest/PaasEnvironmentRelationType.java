//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2013.04.18 à 04:42:34 PM CEST 
//

package telecom.sudparis.eu.paas.core.server.xml.manifest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java pour paas_environment_relationType complex type.
 * 
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette
 * classe.
 * 
 * <pre>
 * &lt;complexType name="paas_environment_relationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paas_environment_link" type="{}paas_environment_linkType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_environment_relationType", propOrder = { "paasEnvironmentLink" })
public class PaasEnvironmentRelationType {

	@XmlElement(name = "paas_environment_link", required = true)
	protected PaasEnvironmentLinkType paasEnvironmentLink;

	/**
	 * Obtient la valeur de la propriété paasEnvironmentLink.
	 * 
	 * @return possible object is {@link PaasEnvironmentLinkType }
	 * 
	 */
	public PaasEnvironmentLinkType getPaasEnvironmentLink() {
		return paasEnvironmentLink;
	}

	/**
	 * Définit la valeur de la propriété paasEnvironmentLink.
	 * 
	 * @param value
	 *            allowed object is {@link PaasEnvironmentLinkType }
	 * 
	 */
	public void setPaasEnvironmentLink(PaasEnvironmentLinkType value) {
		this.paasEnvironmentLink = value;
	}

}
