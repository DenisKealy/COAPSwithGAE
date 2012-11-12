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
package telecom.sudparis.eu.paas.core.server.xml;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ApplicationVersionInstance XML element
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 */
@XmlRootElement(name = "applicationVersionInstanceXML")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationVersionInstanceXML {

	/**
	 * ApplicationVersionInstance ID
	 */
	@XmlAttribute
	private String appId;

	/**
	 * ApplicationVersionInstance instanceID
	 */
	@XmlAttribute
	private String instanceId;

	/**
	 * ApplicationVersionInstance instanceName
	 */
	@XmlAttribute
	private String instanceName;

	/**
	 * ApplicationVersionInstance state
	 */
	@XmlAttribute
	private int state;

	/**
	 * ApplicationVersionInstance target environment ID
	 */
	@XmlAttribute
	private String targetEnvId;

	/**
	 * ApplicationVersionInstance version ID
	 */
	@XmlAttribute
	private String versionID;

	/**
	 * ApplicationVersionInstance deployableTopologyMapping
	 */
	@XmlElement
	private Map<DeployableXML, NodeXML> deployableTopologyMapping;

	/**
	 * ApplicationVersionInstance requirements
	 */
	@XmlElement
	private List<String> requirements;

	/**
	 * ApplicationVersionInstance sortedDeployabesLis
	 */
	@XmlElement
	private List<DeployableXML> sortedDeployabesLis;

	/**
	 * ApplicationVersionInstance urlList
	 */
	@XmlElement
	private List<URI> urlList;

	/**
	 * ApplicationVersionInstance capabilities
	 */
	@XmlElement
	private List<Properties> capabilities;

	/**
	 * Default constructor
	 */
	public ApplicationVersionInstanceXML() {

	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTargetEnvId() {
		return targetEnvId;
	}

	public void setTargetEnvId(String targetEnvId) {
		this.targetEnvId = targetEnvId;
	}

	public String getVersionID() {
		return versionID;
	}

	public void setVersionID(String versionID) {
		this.versionID = versionID;
	}

	public List<Properties> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(List<Properties> capabilities) {
		this.capabilities = capabilities;
	}

	public Map<DeployableXML, NodeXML> getDeployableTopologyMapping() {
		return deployableTopologyMapping;
	}

	public void setDeployableTopologyMapping(
			Map<DeployableXML, NodeXML> deployableTopologyMapping) {
		this.deployableTopologyMapping = deployableTopologyMapping;
	}

	public List<String> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<String> requirements) {
		this.requirements = requirements;
	}

	public List<DeployableXML> getSortedDeployabesLis() {
		return sortedDeployabesLis;
	}

	public void setSortedDeployabesLis(List<DeployableXML> sortedDeployabesLis) {
		this.sortedDeployabesLis = sortedDeployabesLis;
	}

	public List<URI> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<URI> urlList) {
		this.urlList = urlList;
	}

}