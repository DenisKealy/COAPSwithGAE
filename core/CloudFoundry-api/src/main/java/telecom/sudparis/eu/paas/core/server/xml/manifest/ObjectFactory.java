/*******************************************************************************
 * Copyright 2012 Mohamed Sellami, Telecom SudParis
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
//
// Ce fichier a ete genere par l'implementation de reference JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportee à ce fichier sera perdue lors de la recompilation du schema source. 
// Genere le : 2012.09.25 à 04:57:53 PM CEST 
//


package telecom.sudparis.eu.paas.core.server.xml.manifest;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.ow2.jonas.jpaas.core.server.xml.manifest package. 
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

    private final static QName _PaasManifest_QNAME = new QName("", "paas_manifest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.ow2.jonas.jpaas.core.server.xml.manifest
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PaasManifestType }
     * 
     */
    public PaasManifestType createPaasManifestType() {
        return new PaasManifestType();
    }

    /**
     * Create an instance of {@link PaasRelationType }
     * 
     */
    public PaasRelationType createPaasRelationType() {
        return new PaasRelationType();
    }

    /**
     * Create an instance of {@link PaasVersionType }
     * 
     */
    public PaasVersionType createPaasVersionType() {
        return new PaasVersionType();
    }

    /**
     * Create an instance of {@link PaasLinkType }
     * 
     */
    public PaasLinkType createPaasLinkType() {
        return new PaasLinkType();
    }

    /**
     * Create an instance of {@link PaasApplicationType }
     * 
     */
    public PaasApplicationType createPaasApplicationType() {
        return new PaasApplicationType();
    }

    /**
     * Create an instance of {@link PaasNodeType }
     * 
     */
    public PaasNodeType createPaasNodeType() {
        return new PaasNodeType();
    }

    /**
     * Create an instance of {@link PaasEnvironmentType }
     * 
     */
    public PaasEnvironmentType createPaasEnvironmentType() {
        return new PaasEnvironmentType();
    }

    /**
     * Create an instance of {@link PaasDeployableType }
     * 
     */
    public PaasDeployableType createPaasDeployableType() {
        return new PaasDeployableType();
    }

    /**
     * Create an instance of {@link PaasConfigurationTemplateType }
     * 
     */
    public PaasConfigurationTemplateType createPaasConfigurationTemplateType() {
        return new PaasConfigurationTemplateType();
    }

    /**
     * Create an instance of {@link PaasVersionInstanceType }
     * 
     */
    public PaasVersionInstanceType createPaasVersionInstanceType() {
        return new PaasVersionInstanceType();
    }

    /**
     * Create an instance of {@link PaasConfigurationOptionType }
     * 
     */
    public PaasConfigurationOptionType createPaasConfigurationOptionType() {
        return new PaasConfigurationOptionType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaasManifestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "paas_manifest")
    public JAXBElement<PaasManifestType> createPaasManifest(PaasManifestType value) {
        return new JAXBElement<PaasManifestType>(_PaasManifest_QNAME, PaasManifestType.class, null, value);
    }

}
