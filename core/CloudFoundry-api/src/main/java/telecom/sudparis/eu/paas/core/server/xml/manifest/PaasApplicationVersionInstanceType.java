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
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java pour paas_application_version_instanceType complex type.
 * 
 * <p>
 * Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette
 * classe.
 * 
 * <pre>
 * &lt;complexType name="paas_application_version_instanceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="initial_state" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="default_instance" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_application_version_instanceType")
public class PaasApplicationVersionInstanceType {

	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "initial_state")
	protected Integer initialState;
	@XmlAttribute(name = "default_instance")
	protected Boolean defaultInstance;

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
	 * Obtient la valeur de la propri�t� initialState.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getInitialState() {
		return initialState;
	}

	/**
	 * D�finit la valeur de la propri�t� initialState.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setInitialState(Integer value) {
		this.initialState = value;
	}

	/**
	 * Obtient la valeur de la propri�t� defaultInstance.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * D�finit la valeur de la propri�t� defaultInstance.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setDefaultInstance(Boolean value) {
		this.defaultInstance = value;
	}

}
