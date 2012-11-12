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
package telecom.sudparis.eu.paas.core.server.applications.pool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import telecom.sudparis.eu.paas.core.server.xml.RessourcesXML;
import telecom.sudparis.eu.paas.core.server.xml.StagingXML;

/**
 * An application
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 */

/**
 * @author sellami
 *
 */
@XmlRootElement(name = "application")
@XmlAccessorType(XmlAccessType.FIELD)
public class Application {
	/**
	 * the appName
	 */
	@XmlAttribute
	private String appName;
	
	/**
	 * the appID
	 */
	@XmlAttribute
	private String appId;
	
	/**
	 * Application status
	 */
	@XmlAttribute
	private String status;
	/**
	 * the memory size
	 */
	@XmlAttribute
	private long memory;
	/**
	 * the list of URIs
	 */
	@XmlElement
	private List<String> uris;
	/**
	 * The list of services Names
	 */
	@XmlElement
	private List<String> serviceNames;
	/**
	 * checks the existence of the application
	 * By default set to true
	 */
	@XmlAttribute
	private boolean checkExists;
	
	/**
	 * The deployable 
	 */
	@XmlElement
	private Deployable deployable;
	
	/**
	 * The Version Instance 
	 */
	@XmlElement
	private List<VersionInstance> versionInstances;
	
	/**
	 * The number of Version Instance 
	 */
	@XmlAttribute
	private int nbInstances;
	
	/**
	 * Application staging
	 */
	@XmlElement
	private StagingXML staging;
	
	/**
	 * Application services
	 */
	@XmlElement
	private List<String> services;
	
	/**
	 * Application resources
	 */
	@XmlElement
	private RessourcesXML resources;
	
	/**
	 * List of the provided Links for the Application
	 */
	@XmlElement
	private Map<String, String> linksList;
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<String> getUris() {
		return uris;
	}

	public void setUris(List<String> uris) {
		this.uris = uris;
	}

	public List<String> getServiceNames() {
		return serviceNames;
	}

	public void setServiceNames(List<String> serviceNames) {
		this.serviceNames = serviceNames;
	}

	public boolean isCheckExists() {
		return checkExists;
	}

	public long getMemory() {
		return memory;
	}

	public void setMemory(long memory) {
		this.memory = memory;
	}

	public void setCheckExists(boolean checkExists) {
		this.checkExists = checkExists;
	}
	
	public Deployable getDeployable() {
		return deployable;
	}

	public void setDeployable(Deployable deployable) {
		this.deployable = deployable;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<VersionInstance> getVersionInstances() {
		return versionInstances;
	}

	public void setVersionInstances(List<VersionInstance> versionInstances) {
		this.versionInstances = versionInstances;
	}

	public Map<String, String> getLinksList() {
		return linksList;
	}

	public void setLinksList(Map<String, String> linksList) {
		this.linksList = linksList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public StagingXML getStaging() {
		return staging;
	}

	public void setStaging(StagingXML staging) {
		this.staging = staging;
	}

	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}

	public RessourcesXML getResources() {
		return resources;
	}

	public void setResources(RessourcesXML resources) {
		this.resources = resources;
	}

	public int getNbInstances() {
		return nbInstances;
	}

	public void setNbInstances(int nbInstances) {
		this.nbInstances = nbInstances;
	}
	
	

}