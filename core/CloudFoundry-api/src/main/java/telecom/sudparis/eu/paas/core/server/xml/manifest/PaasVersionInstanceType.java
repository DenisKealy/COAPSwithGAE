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
// Toute modification apportee � ce fichier sera perdue lors de la recompilation du schema source. 
// Genere le : 2012.09.25 � 04:57:53 PM CEST 
//


package telecom.sudparis.eu.paas.core.server.xml.manifest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour paas_version_instanceType complex type.
 * 
 * <p>Le fragment de schema suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="paas_version_instanceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="uri" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="date_instantiated" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="state" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="default_instance" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_version_instanceType")
public class PaasVersionInstanceType {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "uri")
    protected String uri;
    @XmlAttribute(name = "date_instantiated")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateInstantiated;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "state")
    protected String state;
    @XmlAttribute(name = "default_instance")
    protected Boolean defaultInstance;

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
     * Obtient la valeur de la propriete uri.
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
     * Definit la valeur de la propriete uri.
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
     * Obtient la valeur de la propriete dateInstantiated.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateInstantiated() {
        return dateInstantiated;
    }

    /**
     * Definit la valeur de la propriete dateInstantiated.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateInstantiated(XMLGregorianCalendar value) {
        this.dateInstantiated = value;
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
     * Obtient la valeur de la propriete state.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Definit la valeur de la propriete state.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Obtient la valeur de la propriete defaultInstance.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDefaultInstance() {
        return defaultInstance;
    }

    /**
     * Definit la valeur de la propriete defaultInstance.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDefaultInstance(Boolean value) {
        this.defaultInstance = value;
    }

}
