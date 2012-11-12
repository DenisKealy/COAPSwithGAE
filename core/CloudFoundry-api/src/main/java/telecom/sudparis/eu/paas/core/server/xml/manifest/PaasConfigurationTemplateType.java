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
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.6 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2012.09.25 � 04:57:53 PM CEST 
//


package telecom.sudparis.eu.paas.core.server.xml.manifest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour paas_configuration_templateType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="paas_configuration_templateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paas_configuration_option" type="{}paas_configuration_optionType"/>
 *         &lt;element name="paas_node" type="{}paas_nodeType" maxOccurs="unbounded"/>
 *         &lt;element name="paas_relation" type="{}paas_relationType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="uri" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="date_created" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="date_uptaded" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_configuration_templateType", propOrder = {
    "paasConfigurationOption",
    "paasNode",
    "paasRelation"
})
public class PaasConfigurationTemplateType {

    @XmlElement(name = "paas_configuration_option", required = true)
    protected PaasConfigurationOptionType paasConfigurationOption;
    @XmlElement(name = "paas_node", required = true)
    protected List<PaasNodeType> paasNode;
    @XmlElement(name = "paas_relation", required = true)
    protected PaasRelationType paasRelation;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "uri")
    protected String uri;
    @XmlAttribute(name = "date_created")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCreated;
    @XmlAttribute(name = "date_uptaded")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateUptaded;
    @XmlAttribute(name = "description")
    protected String description;

    /**
     * Obtient la valeur de la propri�t� paasConfigurationOption.
     * 
     * @return
     *     possible object is
     *     {@link PaasConfigurationOptionType }
     *     
     */
    public PaasConfigurationOptionType getPaasConfigurationOption() {
        return paasConfigurationOption;
    }

    /**
     * D�finit la valeur de la propri�t� paasConfigurationOption.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasConfigurationOptionType }
     *     
     */
    public void setPaasConfigurationOption(PaasConfigurationOptionType value) {
        this.paasConfigurationOption = value;
    }

    /**
     * Gets the value of the paasNode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paasNode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaasNode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaasNodeType }
     * 
     * 
     */
    public List<PaasNodeType> getPaasNode() {
        if (paasNode == null) {
            paasNode = new ArrayList<PaasNodeType>();
        }
        return this.paasNode;
    }

    /**
     * Obtient la valeur de la propri�t� paasRelation.
     * 
     * @return
     *     possible object is
     *     {@link PaasRelationType }
     *     
     */
    public PaasRelationType getPaasRelation() {
        return paasRelation;
    }

    /**
     * D�finit la valeur de la propri�t� paasRelation.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasRelationType }
     *     
     */
    public void setPaasRelation(PaasRelationType value) {
        this.paasRelation = value;
    }

    /**
     * Obtient la valeur de la propri�t� name.
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
     * D�finit la valeur de la propri�t� name.
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
     * Obtient la valeur de la propri�t� uri.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * D�finit la valeur de la propri�t� uri.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Obtient la valeur de la propri�t� dateCreated.
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
     * D�finit la valeur de la propri�t� dateCreated.
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
     * Obtient la valeur de la propri�t� dateUptaded.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateUptaded() {
        return dateUptaded;
    }

    /**
     * D�finit la valeur de la propri�t� dateUptaded.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateUptaded(XMLGregorianCalendar value) {
        this.dateUptaded = value;
    }

    /**
     * Obtient la valeur de la propri�t� description.
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
     * D�finit la valeur de la propri�t� description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
