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
package telecom.sudparis.eu.paas.core.server.environments.pool;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An Environment
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 */

/**
 * @author sellami
 * 
 */
@XmlRootElement(name = "environment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Environment {
	/**
	 * Environment id
	 */
	@XmlAttribute
	private String envId;

	/**
	 * Environment name
	 */
	@XmlAttribute
	private String envName;

	/**
	 * Environment description
	 */
	@XmlAttribute
	private String envDesc;

	/**
	 * The staging
	 */
	@XmlElement
	private Map<String, String> staging;

	/**
	 * The service Names
	 */
	@XmlElement
	private List<String> serviceNames;

	/**
	 * List of the provided Links for the Application
	 */
	@XmlElement
	private Map<String, String> linksList;

	public String getEnvId() {
		return envId;
	}

	public void setEnvId(String envId) {
		this.envId = envId;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public String getEnvDesc() {
		return envDesc;
	}

	public void setEnvDesc(String envDesc) {
		this.envDesc = envDesc;
	}

	public Map<String, String> getStaging() {
		return staging;
	}

	public void setStaging(Map<String, String> staging) {
		this.staging = staging;
	}

	public List<String> getServiceNames() {
		return serviceNames;
	}

	public void setServiceNames(List<String> serviceNames) {
		this.serviceNames = serviceNames;
	}

	public Map<String, String> getLinksList() {
		return linksList;
	}

	public void setLinksList(Map<String, String> linksList) {
		this.linksList = linksList;
	}

}
