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

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour paas_versionType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="paas_versionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paas_deployable" type="{}paas_deployableType"/>
 *         &lt;element name="paas_version_instance" type="{}paas_version_instanceType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}decimal" />
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
@XmlType(name = "paas_versionType", propOrder = {
    "paasDeployable",
    "paasVersionInstance"
})
public class PaasVersionType {

    @XmlElement(name = "paas_deployable", required = true)
    protected PaasDeployableType paasDeployable;
    @XmlElement(name = "paas_version_instance", required = true)
    protected List<PaasVersionInstanceType> paasVersionInstance;
    @XmlAttribute(name = "label")
    protected BigDecimal label;
    @XmlAttribute(name = "date_uptaded")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateUptaded;
    @XmlAttribute(name = "description")
    protected String description;

    /**
     * Obtient la valeur de la propri�t� paasDeployable.
     * 
     * @return
     *     possible object is
     *     {@link PaasDeployableType }
     *     
     */
    public PaasDeployableType getPaasDeployable() {
        return paasDeployable;
    }

    /**
     * D�finit la valeur de la propri�t� paasDeployable.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasDeployableType }
     *     
     */
    public void setPaasDeployable(PaasDeployableType value) {
        this.paasDeployable = value;
    }

    /**
     * Obtient la valeur de la propri�t� paasVersionInstance.
     * 
     * @return
     *     possible object is
     *     {@link PaasVersionInstanceType }
     *     
     */
    public List<PaasVersionInstanceType> getPaasVersionInstance() {
        return paasVersionInstance;
    }

    /**
     * D�finit la valeur de la propri�t� paasVersionInstance.
     * 
     * @param value
     *     allowed object is
     *     {@link PaasVersionInstanceType }
     *     
     */
    public void setPaasVersionInstance(List<PaasVersionInstanceType> value) {
        this.paasVersionInstance = value;
    }

    /**
     * Obtient la valeur de la propri�t� label.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLabel() {
        return label;
    }

    /**
     * D�finit la valeur de la propri�t� label.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLabel(BigDecimal value) {
        this.label = value;
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
