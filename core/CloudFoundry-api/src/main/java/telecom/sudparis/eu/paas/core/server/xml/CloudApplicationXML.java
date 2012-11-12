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


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;

import org.cloudfoundry.client.lib.domain.CloudApplication;

import telecom.sudparis.eu.paas.core.server.xml.RessourcesXML;

/**
 * Environment XML element
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 */
@XmlRootElement(name = "CloudApplication")
@XmlAccessorType(XmlAccessType.FIELD)
public class CloudApplicationXML {
	/**
	 * CloudApplication name
	 */
	@XmlAttribute
	private String name;

	/**
	 * CloudApplication URI
	 */
	@XmlElement
	private List<String> uris;

	/**
	 * CloudApplication status
	 */
	@XmlAttribute
	private String status;
	
	/**
	 * CloudApplication staging
	 */
	@XmlElement
	private StagingXML staging;
	
	/**
	 * CloudApplication instances
	 */
	@XmlAttribute
	private int instances;
	
	/**
	 * CloudApplication services
	 */
	@XmlElement
	private List<String> services;
	
	
	/**
	 * CloudApplication debug mode
	 */
	@XmlAttribute
	private String debug;
	
	/**
	 * CloudApplication resources
	 */
	@XmlElement
	private RessourcesXML resources;
	
	/**
	 * CloudApplication running Instances
	 */
	@XmlAttribute
	private int runningInstances;
	
	/**
	 * CloudApplication environments
	 */
	@XmlElement
	private List<String> env = new ArrayList<String>();
	
	/**
	 * List of the provided Links for the Application
	 */
	@XmlElement
	private Map<String, String> linksList;
	

	public CloudApplicationXML(){
		
	}
	
	public CloudApplicationXML(CloudApplication app) {
		this.name = app.getName();
		this.uris = app.getUris();
		this.status = app.getState().toString();
		if (app.getDebug()==null)
			this.debug="suspend";
		else
			this.debug=app.getDebug().toString();
		this.env=app.getEnv();
		this.instances=app.getInstances();
		this.resources=new RessourcesXML(app.getResources());
		this.runningInstances=app.getRunningInstances();
		this.services=app.getServices();
		this.staging=new StagingXML(app.getStaging());
	}

	public StagingXML getStaging() {
		return staging;
	}

	public void setStaging(StagingXML staging) {
		this.staging = staging;
	}

	public int getInstances() {
		return instances;
	}

	public void setInstances(int instances) {
		this.instances = instances;
	}

	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}

	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}

	public RessourcesXML getResources() {
		return resources;
	}

	public void setResources(RessourcesXML resources) {
		this.resources = resources;
	}

	public int getRunningInstances() {
		return runningInstances;
	}

	public void setRunningInstances(int runningInstances) {
		this.runningInstances = runningInstances;
	}

	public List<String> getEnv() {
		return env;
	}

	public void setEnv(List<String> env) {
		this.env = env;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUris() {
		return uris;
	}

	public void setUris(List<String> uris) {
		this.uris = uris;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, String> getLinksList() {
		return linksList;
	}

	public void setLinksList(Map<String, String> linksList) {
		this.linksList = linksList;
	}


}
