//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2013.04.18 � 04:42:34 PM CEST 
//

package telecom.sudparis.eu.paas.core.server.xml.manifest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java pour paas_environment_configurationType complex type.
 * 
 * <p>
 * Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette
 * classe.
 * 
 * <pre>
 * &lt;complexType name="paas_environment_configurationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paas_environment_variable" type="{}paas_environment_variableType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_environment_configurationType", propOrder = { "paasEnvironmentVariable" })
public class PaasEnvironmentConfigurationType {

	@XmlElement(name = "paas_environment_variable", required = true)
	protected PaasEnvironmentVariableType paasEnvironmentVariable;

	/**
	 * Obtient la valeur de la propri�t� paasEnvironmentVariable.
	 * 
	 * @return possible object is {@link PaasEnvironmentVariableType }
	 * 
	 */
	public PaasEnvironmentVariableType getPaasEnvironmentVariable() {
		return paasEnvironmentVariable;
	}

	/**
	 * D�finit la valeur de la propri�t� paasEnvironmentVariable.
	 * 
	 * @param value
	 *            allowed object is {@link PaasEnvironmentVariableType }
	 * 
	 */
	public void setPaasEnvironmentVariable(PaasEnvironmentVariableType value) {
		this.paasEnvironmentVariable = value;
	}

}
