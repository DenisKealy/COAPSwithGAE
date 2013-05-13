//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2013.04.18 à 05:02:15 PM CEST 
//

package telecom.sudparis.eu.paas.core.server.xml.manifest;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the
 * telecom.sudparis.eu.paas.core.server.xml.manifest package.
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

	private final static QName _PaasApplicationManifest_QNAME = new QName("",
			"paas_application_manifest");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package:
	 * telecom.sudparis.eu.paas.core.server.xml.manifest
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link PaasApplicationManifestType }
	 * 
	 */
	public PaasApplicationManifestType createPaasApplicationManifestType() {
		return new PaasApplicationManifestType();
	}

	/**
	 * Create an instance of {@link PaasApplicationDeployableType }
	 * 
	 */
	public PaasApplicationDeployableType createPaasApplicationDeployableType() {
		return new PaasApplicationDeployableType();
	}

	/**
	 * Create an instance of {@link PaasApplicationVersionType }
	 * 
	 */
	public PaasApplicationVersionType createPaasApplicationVersionType() {
		return new PaasApplicationVersionType();
	}

	/**
	 * Create an instance of {@link PaasApplicationVersionInstanceType }
	 * 
	 */
	public PaasApplicationVersionInstanceType createPaasApplicationVersionInstanceType() {
		return new PaasApplicationVersionInstanceType();
	}

	/**
	 * Create an instance of {@link PaasApplicationType }
	 * 
	 */
	public PaasApplicationType createPaasApplicationType() {
		return new PaasApplicationType();
	}

	/**
	 * Create an instance of {@link PaasEnvironmentType }
	 * 
	 */
	public PaasEnvironmentType createPaasEnvironmentType() {
		return new PaasEnvironmentType();
	}

	/**
	 * Create an instance of {@link PaasEnvironmentNodeType }
	 * 
	 */
	public PaasEnvironmentNodeType createPaasEnvironmentNodeType() {
		return new PaasEnvironmentNodeType();
	}

	/**
	 * Create an instance of {@link PaasEnvironmentTemplateType }
	 * 
	 */
	public PaasEnvironmentTemplateType createPaasEnvironmentTemplateType() {
		return new PaasEnvironmentTemplateType();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link PaasApplicationManifestType }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "paas_application_manifest")
	public JAXBElement<PaasApplicationManifestType> createPaasApplicationManifest(
			PaasApplicationManifestType value) {
		return new JAXBElement<PaasApplicationManifestType>(
				_PaasApplicationManifest_QNAME,
				PaasApplicationManifestType.class, null, value);
	}

}
