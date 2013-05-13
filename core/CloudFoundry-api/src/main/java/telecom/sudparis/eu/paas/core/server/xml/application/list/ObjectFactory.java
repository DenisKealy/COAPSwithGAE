//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2013.05.13 à 05:46:20 PM CEST 
//


package telecom.sudparis.eu.paas.core.server.xml.application.list;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the telecom.sudparis.eu.paas.core.server.xml.application.list package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Applications_QNAME = new QName("", "applications");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: telecom.sudparis.eu.paas.core.server.xml.application.list
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ApplicationsType }
     * 
     */
    public ApplicationsType createApplicationsType() {
        return new ApplicationsType();
    }

    /**
     * Create an instance of {@link SimpleApplicationType }
     * 
     */
    public SimpleApplicationType createSimpleApplicationType() {
        return new SimpleApplicationType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "applications")
    public JAXBElement<ApplicationsType> createApplications(ApplicationsType value) {
        return new JAXBElement<ApplicationsType>(_Applications_QNAME, ApplicationsType.class, null, value);
    }

}
