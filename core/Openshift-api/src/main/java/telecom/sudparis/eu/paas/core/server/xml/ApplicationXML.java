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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.openshift.client.ApplicationScale;
import com.openshift.client.IApplication;

/**
 * An OpenShift application JAXB descriptor
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 */

@XmlRootElement(name = "application")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationXML {
	/**
	 * the appName
	 */
	@XmlAttribute
	private String appName;

	/**
	 * the AppId
	 */
	@XmlAttribute
	private String appId;

	/**
	 * the appUUID
	 */
	@XmlAttribute
	private String appUUID;

	/**
	 * the application Scale status
	 */
	@XmlAttribute
	private ApplicationScale applicationScale;

	/**
	 * Application's URL
	 */
	@XmlElement
	private String applicationUrl;

	/**
	 * Application's Cartridge
	 */
	@XmlElement
	private String cartridge;

	/**
	 * The list of environmentProperties
	 */
	@XmlElement
	private List<String> environmentProperties;

	/**
	 * The Application's GIT URL
	 */
	@XmlElement
	private String gitUrl;

	/**
	 * The Application's health Check URL
	 */
	@XmlElement
	private String healthCheckUrl;

	/**
	 * The deployable
	 */
	@XmlElement
	private DeployableXML deployable;

	/**
	 * List of the provided Links for the Application
	 */

	@XmlElement
	protected ApplicationXML.LinksList linksList;

	public ApplicationXML(IApplication iapp) {
		this.appName = iapp.getName();
		this.appUUID = iapp.getUUID();
		this.applicationScale = iapp.getApplicationScale();
		this.applicationUrl = iapp.getApplicationUrl();
		this.cartridge = iapp.getCartridge().getName();
		if (iapp.hasSSHSession())
			this.environmentProperties = iapp.getEnvironmentProperties();
		this.gitUrl = iapp.getGitUrl();
		this.healthCheckUrl = iapp.getHealthCheckUrl();
	}

	/**
	 * default Constructor
	 */
	public ApplicationXML() {
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the appUUID
	 */
	public String getAppUUID() {
		return appUUID;
	}

	/**
	 * @param appUUID
	 *            the appUUID to set
	 */
	public void setAppUUID(String appUUID) {
		this.appUUID = appUUID;
	}

	/**
	 * @return the applicationScale
	 */
	public ApplicationScale getApplicationScale() {
		return applicationScale;
	}

	/**
	 * @param applicationScale
	 *            the applicationScale to set
	 */
	public void setApplicationScale(ApplicationScale applicationScale) {
		this.applicationScale = applicationScale;
	}

	/**
	 * @return the applicationUrl
	 */
	public String getApplicationUrl() {
		return applicationUrl;
	}

	/**
	 * @param applicationUrl
	 *            the applicationUrl to set
	 */
	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}

	/**
	 * @return the cartridge
	 */
	// public CartridgeXML getCartridge() {
	// return cartridge;
	// }
	//
	// /**
	// * @param cartridge the cartridge to set
	// */
	// public void setCartridge(CartridgeXML cartridge) {
	// this.cartridge = cartridge;
	// }

	/**
	 * @return the environmentProperties
	 */
	public List<String> getEnvironmentProperties() {
		return environmentProperties;
	}

	/**
	 * @param environmentProperties
	 *            the environmentProperties to set
	 */
	public void setEnvironmentProperties(List<String> environmentProperties) {
		this.environmentProperties = environmentProperties;
	}

	/**
	 * @return the gitUrl
	 */
	public String getGitUrl() {
		return gitUrl;
	}

	/**
	 * @param gitUrl
	 *            the gitUrl to set
	 */
	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}

	/**
	 * @return the healthCheckUrl
	 */
	public String getHealthCheckUrl() {
		return healthCheckUrl;
	}

	/**
	 * @param healthCheckUrl
	 *            the healthCheckUrl to set
	 */
	public void setHealthCheckUrl(String healthCheckUrl) {
		this.healthCheckUrl = healthCheckUrl;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the deployable
	 */
	public DeployableXML getDeployable() {
		return deployable;
	}

	/**
	 * @param deployable
	 *            the deployable to set
	 */
	public void setDeployable(DeployableXML deployable) {
		this.deployable = deployable;
	}

	/**
	 * Gets the value of the linksList property.
	 * 
	 * @return possible object is {@link Application.LinksList }
	 * 
	 */
	public ApplicationXML.LinksList getLinksList() {
		return linksList;
	}

	/**
	 * Sets the value of the linksList property.
	 * 
	 * @param value
	 *            allowed object is {@link Application.LinksList }
	 * 
	 */
	public void setLinksList(ApplicationXML.LinksList value) {
		this.linksList = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class LinksList {

		@XmlElement
		protected List<ApplicationXML.LinksList.Link> link;

		public List<ApplicationXML.LinksList.Link> getLink() {
			if (link == null) {
				link = new ArrayList<ApplicationXML.LinksList.Link>();
			}
			return this.link;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "")
		public static class Link {

			@XmlAttribute
			protected String label;
			@XmlAttribute
			protected String action;
			@XmlAttribute
			protected String href;

			/**
			 * Gets the value of the label property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getLabel() {
				return label;
			}

			/**
			 * Sets the value of the label property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setLabel(String value) {
				this.label = value;
			}

			/**
			 * Gets the value of the action property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getAction() {
				return action;
			}

			/**
			 * Sets the value of the action property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setAction(String value) {
				this.action = value;
			}

			/**
			 * Gets the value of the href property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getHref() {
				return href;
			}

			/**
			 * Sets the value of the href property.
			 * 
			 * @param value
			 *            allowed object is {@link String }
			 * 
			 */
			public void setHref(String value) {
				this.href = value;
			}

		}

	}

}
