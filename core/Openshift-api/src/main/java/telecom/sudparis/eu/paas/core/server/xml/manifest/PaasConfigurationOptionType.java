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
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java pour paas_configuration_optionType complex type.
 * 
 * <p>
 * Le fragment de schema suivant indique le contenu attendu figurant dans cette
 * classe.
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
	 * Obtient la valeur de la propriete changeSeverity.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getChangeSeverity() {
		return changeSeverity;
	}

	/**
	 * Definit la valeur de la propriete changeSeverity.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setChangeSeverity(String value) {
		this.changeSeverity = value;
	}

	/**
	 * Obtient la valeur de la propriete defaultValue.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Definit la valeur de la propriete defaultValue.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDefaultValue(String value) {
		this.defaultValue = value;
	}

	/**
	 * Obtient la valeur de la propriete maxLength.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMaxLength() {
		return maxLength;
	}

	/**
	 * Definit la valeur de la propriete maxLength.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMaxLength(String value) {
		this.maxLength = value;
	}

	/**
	 * Obtient la valeur de la propriete maxValue.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMaxValue() {
		return maxValue;
	}

	/**
	 * Definit la valeur de la propriete maxValue.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMaxValue(String value) {
		this.maxValue = value;
	}

	/**
	 * Obtient la valeur de la propriete minValue.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMinValue() {
		return minValue;
	}

	/**
	 * Definit la valeur de la propriete minValue.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMinValue(String value) {
		this.minValue = value;
	}

	/**
	 * Obtient la valeur de la propriete regex.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * Definit la valeur de la propriete regex.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRegex(String value) {
		this.regex = value;
	}

	/**
	 * Obtient la valeur de la propriete userDefined.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isUserDefined() {
		return userDefined;
	}

	/**
	 * Definit la valeur de la propriete userDefined.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setUserDefined(Boolean value) {
		this.userDefined = value;
	}

	/**
	 * Obtient la valeur de la propriete valueOptions.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValueOptions() {
		return valueOptions;
	}

	/**
	 * Definit la valeur de la propriete valueOptions.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setValueOptions(String value) {
		this.valueOptions = value;
	}

	/**
	 * Obtient la valeur de la propriete valueType.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValueType() {
		return valueType;
	}

	/**
	 * Definit la valeur de la propriete valueType.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setValueType(String value) {
		this.valueType = value;
	}

	/**
	 * Obtient la valeur de la propriete namespace.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * Definit la valeur de la propriete namespace.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNamespace(String value) {
		this.namespace = value;
	}

	/**
	 * Obtient la valeur de la propriete optionName.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOptionName() {
		return optionName;
	}

	/**
	 * Definit la valeur de la propriete optionName.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOptionName(String value) {
		this.optionName = value;
	}

	/**
	 * Obtient la valeur de la propriete value.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Definit la valeur de la propriete value.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
