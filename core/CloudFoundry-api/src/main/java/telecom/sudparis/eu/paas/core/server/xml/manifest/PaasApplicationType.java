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

package telecom.sudparis.eu.paas.core.server.xml.manifest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour paas_applicationType complex type.
 * 
 * <p>Le fragment de schema suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="paas_applicationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paas_version" type="{}paas_versionType"/>
 *         &lt;element name="paas_environment" type="{}paas_environmentType"/>
 *         &lt;element name="paas_configuration_template" type="{}paas_configuration_templateType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="date_created" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="environement" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_applicationType", propOrder = {
    "paasVersion",
    "paasEnvironment",
    "paasConfigurationTemplate"
})
public class PaasApplicationType {

    @XmlElement(name = "paas_version", required = true)
    protected PaasVersionType paasVersion;
    @XmlElement(name = "paas_environment", required = true)
    protected PaasEnvironmentType paasEnvironment;
    @XmlElement(name = "paas_configuration_template", required = true)
    protected PaasConfigurationTemplateType paasConfigurationTemplate;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "date_created")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCreated;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "environement")
    protected String environement;

    /**
     * Obtient la valeur de la propriete paasVersion.
     * 
     * @return
     *     possible object is
     *     {@link PaasVersionType }
     *     
     */
    public PaasVersionType getPaasVersion() {
        return paasVersion;
    }

    /**
     * Definit la valeur de la propriete paasVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasVersionType }
     *     
     */
    public void setPaasVersion(PaasVersionType value) {
        this.paasVersion = value;
    }

    /**
     * Obtient la valeur de la propriete paasEnvironment.
     * 
     * @return
     *     possible object is
     *     {@link PaasEnvironmentType }
     *     
     */
    public PaasEnvironmentType getPaasEnvironment() {
        return paasEnvironment;
    }

    /**
     * Definit la valeur de la propriete paasEnvironment.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasEnvironmentType }
     *     
     */
    public void setPaasEnvironment(PaasEnvironmentType value) {
        this.paasEnvironment = value;
    }

    /**
     * Obtient la valeur de la propriete paasConfigurationTemplate.
     * 
     * @return
     *     possible object is
     *     {@link PaasConfigurationTemplateType }
     *     
     */
    public PaasConfigurationTemplateType getPaasConfigurationTemplate() {
        return paasConfigurationTemplate;
    }

    /**
     * Definit la valeur de la propriete paasConfigurationTemplate.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasConfigurationTemplateType }
     *     
     */
    public void setPaasConfigurationTemplate(PaasConfigurationTemplateType value) {
        this.paasConfigurationTemplate = value;
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

    /**
     * Obtient la valeur de la propriete dateCreated.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateCreated() {
        return dateCreated;
    }

    /**
     * Definit la valeur de la propriete dateCreated.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCreated(XMLGregorianCalendar value) {
        this.dateCreated = value;
    }

    /**
     * Obtient la valeur de la propriete description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Definit la valeur de la propriete description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtient la valeur de la propriete environement.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnvironement() {
        return environement;
    }

    /**
     * Definit la valeur de la propriete environement.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnvironement(String value) {
        this.environement = value;
    }

}
