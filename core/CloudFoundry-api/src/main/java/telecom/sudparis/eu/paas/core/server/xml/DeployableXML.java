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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Node XML element
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 */
@XmlRootElement(name = "deployable")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeployableXML {

	/**
	 * Deployable ID
	 */
	@XmlAttribute
	private String deployableId;

	/**
	 * Deployable Name
	 */
	@XmlAttribute
	private String deployableName;

	/**
	 * Deployable locationURL
	 */
	@XmlAttribute
	private String locationURL;

	/**
	 * Deployable slaEnforcement
	 */
	@XmlAttribute
	private String slaEnforcement;

	/**
	 * Deployable uploaded
	 */
	@XmlAttribute
	private Boolean uploaded;

	/**
	 * Deployable requirements
	 */
	@XmlElement
	private List<String> requirements;

	/**
	 * Default constructor
	 */
	public DeployableXML() {

	}

	public String getDeployableId() {
		return deployableId;
	}

	public void setDeployableId(String deployableId) {
		this.deployableId = deployableId;
	}

	public String getDeployableName() {
		return deployableName;
	}

	public void setDeployableName(String deployableName) {
		this.deployableName = deployableName;
	}

	public String getLocationURL() {
		return locationURL;
	}

	public void setLocationURL(String locationURL) {
		this.locationURL = locationURL;
	}

	public String getSlaEnforcement() {
		return slaEnforcement;
	}

	public void setSlaEnforcement(String slaEnforcement) {
		this.slaEnforcement = slaEnforcement;
	}

	public Boolean getUploaded() {
		return uploaded;
	}

	public void setUploaded(Boolean uploaded) {
		this.uploaded = uploaded;
	}

	public List<String> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<String> requirements) {
		this.requirements = requirements;
	}

}
