//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2013.04.18 à 05:02:15 PM CEST 
//

package telecom.sudparis.eu.paas.core.server.xml.manifest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java pour paas_application_versionType complex type.
 * 
 * <p>
 * Le fragment de schéma suivant indique le contenu attendu figurant dans cette
 * classe.
 * 
 * <pre>
 * &lt;complexType name="paas_application_versionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paas_application_deployable" type="{}paas_application_deployableType"/>
 *         &lt;element name="paas_application_version_instance" type="{}paas_application_version_instanceType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_application_versionType", propOrder = {
		"paasApplicationDeployable", "paasApplicationVersionInstance" })
public class PaasApplicationVersionType {

	@XmlElement(name = "paas_application_deployable", required = true)
	protected PaasApplicationDeployableType paasApplicationDeployable;
	@XmlElement(name = "paas_application_version_instance", required = true)
	protected List<PaasApplicationVersionInstanceType> paasApplicationVersionInstance;
	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "label")
	protected BigDecimal label;

	/**
	 * Obtient la valeur de la propriété paasApplicationDeployable.
	 * 
	 * @return possible object is {@link PaasApplicationDeployableType }
	 * 
	 */
	public PaasApplicationDeployableType getPaasApplicationDeployable() {
		return paasApplicationDeployable;
	}

	/**
	 * Définit la valeur de la propriété paasApplicationDeployable.
	 * 
	 * @param value
	 *            allowed object is {@link PaasApplicationDeployableType }
	 * 
	 */
	public void setPaasApplicationDeployable(PaasApplicationDeployableType value) {
		this.paasApplicationDeployable = value;
	}

	/**
	 * Gets the value of the paasApplicationVersionInstance property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the paasApplicationVersionInstance property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getPaasApplicationVersionInstance().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link PaasApplicationVersionInstanceType }
	 * 
	 * 
	 */
	public List<PaasApplicationVersionInstanceType> getPaasApplicationVersionInstance() {
		if (paasApplicationVersionInstance == null) {
			paasApplicationVersionInstance = new ArrayList<PaasApplicationVersionInstanceType>();
		}
		return this.paasApplicationVersionInstance;
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
	 * Obtient la valeur de la propriété label.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getLabel() {
		return label;
	}

	/**
	 * Définit la valeur de la propriété label.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setLabel(BigDecimal value) {
		this.label = value;
	}

}
