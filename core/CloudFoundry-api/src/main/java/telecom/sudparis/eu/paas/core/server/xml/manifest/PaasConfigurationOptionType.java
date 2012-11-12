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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour paas_configuration_optionType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="paas_configuration_optionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="change_severity" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="default_value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="max_length" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="max_value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="min_value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="regex" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="user_defined" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="value_options" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="value_type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="namespace" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="option_name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paas_configuration_optionType")
public class PaasConfigurationOptionType {

    @XmlAttribute(name = "change_severity")
    protected String changeSeverity;
    @XmlAttribute(name = "default_value")
    protected String defaultValue;
    @XmlAttribute(name = "max_length")
    protected String maxLength;
    @XmlAttribute(name = "max_value")
    protected String maxValue;
    @XmlAttribute(name = "min_value")
    protected String minValue;
    @XmlAttribute(name = "regex")
    protected String regex;
    @XmlAttribute(name = "user_defined")
    protected Boolean userDefined;
    @XmlAttribute(name = "value_options")
    protected String valueOptions;
    @XmlAttribute(name = "value_type")
    protected String valueType;
    @XmlAttribute(name = "namespace")
    protected String namespace;
    @XmlAttribute(name = "option_name")
    protected String optionName;
    @XmlAttribute(name = "value")
    protected String value;

    /**
     * Obtient la valeur de la propri�t� changeSeverity.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangeSeverity() {
        return changeSeverity;
    }

    /**
     * D�finit la valeur de la propri�t� changeSeverity.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangeSeverity(String value) {
        this.changeSeverity = value;
    }

    /**
     * Obtient la valeur de la propri�t� defaultValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * D�finit la valeur de la propri�t� defaultValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Obtient la valeur de la propri�t� maxLength.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxLength() {
        return maxLength;
    }

    /**
     * D�finit la valeur de la propri�t� maxLength.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxLength(String value) {
        this.maxLength = value;
    }

    /**
     * Obtient la valeur de la propri�t� maxValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxValue() {
        return maxValue;
    }

    /**
     * D�finit la valeur de la propri�t� maxValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxValue(String value) {
        this.maxValue = value;
    }

    /**
     * Obtient la valeur de la propri�t� minValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinValue() {
        return minValue;
    }

    /**
     * D�finit la valeur de la propri�t� minValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinValue(String value) {
        this.minValue = value;
    }

    /**
     * Obtient la valeur de la propri�t� regex.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegex() {
        return regex;
    }

    /**
     * D�finit la valeur de la propri�t� regex.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegex(String value) {
        this.regex = value;
    }

    /**
     * Obtient la valeur de la propri�t� userDefined.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUserDefined() {
        return userDefined;
    }

    /**
     * D�finit la valeur de la propri�t� userDefined.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUserDefined(Boolean value) {
        this.userDefined = value;
    }

    /**
     * Obtient la valeur de la propri�t� valueOptions.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueOptions() {
        return valueOptions;
    }

    /**
     * D�finit la valeur de la propri�t� valueOptions.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueOptions(String value) {
        this.valueOptions = value;
    }

    /**
     * Obtient la valeur de la propri�t� valueType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueType() {
        return valueType;
    }

    /**
     * D�finit la valeur de la propri�t� valueType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueType(String value) {
        this.valueType = value;
    }

    /**
     * Obtient la valeur de la propri�t� namespace.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * D�finit la valeur de la propri�t� namespace.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamespace(String value) {
        this.namespace = value;
    }

    /**
     * Obtient la valeur de la propri�t� optionName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptionName() {
        return optionName;
    }

    /**
     * D�finit la valeur de la propri�t� optionName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptionName(String value) {
        this.optionName = value;
    }

    /**
     * Obtient la valeur de la propri�t� value.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * D�finit la valeur de la propri�t� value.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

}
