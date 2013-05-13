//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2013.04.17 à 07:21:53 PM CEST 
//

package telecom.sudparis.eu.paas.core.server.xml.application;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import telecom.sudparis.eu.paas.core.server.xml.LinkType;
import telecom.sudparis.eu.paas.core.server.xml.LinksListType;
import telecom.sudparis.eu.paas.core.server.xml.environment.ConfigurationType;
import telecom.sudparis.eu.paas.core.server.xml.environment.EntryType;
import telecom.sudparis.eu.paas.core.server.xml.environment.EnvironmentType;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the
 * telecom.sudparis.eu.paas.core.server.xml.application package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _Application_QNAME = new QName("", "application");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package:
	 * telecom.sudparis.eu.paas.core.server.xml.application
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link ApplicationType }
	 * 
	 */
	public ApplicationType createApplicationType() {
		return new ApplicationType();
	}

	/**
	 * Create an instance of {@link InstancesType }
	 * 
	 */
	public InstancesType createInstancesType() {
		return new InstancesType();
	}

	/**
	 * Create an instance of {@link ConfigurationType }
	 * 
	 */
	public ConfigurationType createConfigurationType() {
		return new ConfigurationType();
	}

	/**
	 * Create an instance of {@link UrisType }
	 * 
	 */
	public UrisType createUrisType() {
		return new UrisType();
	}

	/**
	 * Create an instance of {@link EntryType }
	 * 
	 */
	public EntryType createEntryType() {
		return new EntryType();
	}

	/**
	 * Create an instance of {@link DeployableType }
	 * 
	 */
	public DeployableType createDeployableType() {
		return new DeployableType();
	}

	/**
	 * Create an instance of {@link LinkType }
	 * 
	 */
	public LinkType createLinkType() {
		return new LinkType();
	}

	/**
	 * Create an instance of {@link LinksListType }
	 * 
	 */
	public LinksListType createLinksListType() {
		return new LinksListType();
	}

	/**
	 * Create an instance of {@link InstanceType }
	 * 
	 */
	public InstanceType createInstanceType() {
		return new InstanceType();
	}

	/**
	 * Create an instance of {@link EnvironmentType }
	 * 
	 */
	public EnvironmentType createEnvironmentType() {
		return new EnvironmentType();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationType }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "application")
	public JAXBElement<ApplicationType> createApplication(ApplicationType value) {
		return new JAXBElement<ApplicationType>(_Application_QNAME,
				ApplicationType.class, null, value);
	}

}
