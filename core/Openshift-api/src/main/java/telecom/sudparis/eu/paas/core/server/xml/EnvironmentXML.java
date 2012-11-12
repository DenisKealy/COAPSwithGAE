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
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Environment XML element
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 */
@XmlRootElement(name = "environment")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnvironmentXML {
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
	 * The container Names
	 */
	@XmlElement
	private List<String> containerNames;
	
	/**
	 * The cartridge Names
	 */
	@XmlElement
	private List<String> cartridgeNames;
	
	
	/**
	 * List of the provided Links for the Environment
	 */
	@XmlElement
	private Map<String, String> linksList;


	/**
	 * @return the containerNames
	 */
	public List<String> getContainerNames() {
		return containerNames;
	}

	/**
	 * @param containerNames the containerNames to set
	 */
	public void setContainerNames(List<String> containerNames) {
		this.containerNames = containerNames;
	}

	/**
	 * @return the cartridgeNames
	 */
	public List<String> getCartridgeNames() {
		return cartridgeNames;
	}

	/**
	 * @param cartridgeNames the cartridgeNames to set
	 */
	public void setCartridgeNames(List<String> cartridgeNames) {
		this.cartridgeNames = cartridgeNames;
	}

	/**
	 * @return the linksList
	 */
	public Map<String, String> getLinksList() {
		return linksList;
	}

	/**
	 * @param linksList the linksList to set
	 */
	public void setLinksList(Map<String, String> linksList) {
		this.linksList = linksList;
	}

	/**
	 * Default constructor
	 */
	public EnvironmentXML() {

	}

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




}
