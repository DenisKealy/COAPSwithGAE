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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour paas_manifestType complex type.
 * 
 * <p>Le fragment de schema suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="paas_manifestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paas_description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paas_application" type="{}paas_applicationType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_manifestType", propOrder = {
    "paasDescription",
    "paasApplication"
})
public class PaasManifestType {

    @XmlElement(name = "paas_description", required = true)
    protected String paasDescription;
    @XmlElement(name = "paas_application", required = true)
    protected PaasApplicationType paasApplication;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Obtient la valeur de la propriete paasDescription.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaasDescription() {
        return paasDescription;
    }

    /**
     * Definit la valeur de la propriete paasDescription.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaasDescription(String value) {
        this.paasDescription = value;
    }

    /**
     * Obtient la valeur de la propriete paasApplication.
     * 
     * @return
     *     possible object is
     *     {@link PaasApplicationType }
     *     
     */
    public PaasApplicationType getPaasApplication() {
        return paasApplication;
    }

    /**
     * Definit la valeur de la propriete paasApplication.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasApplicationType }
     *     
     */
    public void setPaasApplication(PaasApplicationType value) {
        this.paasApplication = value;
    }

    /**
     * Obtient la valeur de la propriete name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Definit la valeur de la propriete name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
