//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2013.05.13 à 03:02:07 PM CEST 
//


package telecom.sudparis.eu.paas.core.server.xml.environment.list;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the telecom.sudparis.eu.paas.core.server.xml.list.env package. 
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

    private final static QName _Environments_QNAME = new QName("", "environments");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: telecom.sudparis.eu.paas.core.server.xml.list.env
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EnvironmentsType }
     * 
     */
    public EnvironmentsType createEnvironmentsType() {
        return new EnvironmentsType();
    }

    /**
     * Create an instance of {@link SimpleEnvironmentType }
     * 
     */
    public SimpleEnvironmentType createEnvironmentType() {
        return new SimpleEnvironmentType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnvironmentsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "environments")
    public JAXBElement<EnvironmentsType> createEnvironments(EnvironmentsType value) {
        return new JAXBElement<EnvironmentsType>(_Environments_QNAME, EnvironmentsType.class, null, value);
    }

}
