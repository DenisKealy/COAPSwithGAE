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
	 * Environment State
	 */
	@XmlAttribute
	private int envState;

	/**
	 * Environment topology
	 */
	@XmlElement
	private TopologyXML envTopology;

	/**
	 * Environment ListApplicationVersionInstance
	 */
	@XmlElement
	private List<ApplicationVersionInstanceXML> envListApplicationVersionInstanceXML;

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

	public int getEnvState() {
		return envState;
	}

	public void setEnvState(int envState) {
		this.envState = envState;
	}

	public TopologyXML getEnvTopology() {
		return envTopology;
	}

	public void setEnvTopology(TopologyXML envTopology) {
		this.envTopology = envTopology;
	}

	public List<ApplicationVersionInstanceXML> getEnvListApplicationVersionInstance() {
		return envListApplicationVersionInstanceXML;
	}

	public void setEnvListApplicationVersionInstance(
			List<ApplicationVersionInstanceXML> envListApplicationVersionInstanceXML) {
		this.envListApplicationVersionInstanceXML = envListApplicationVersionInstanceXML;
	}

}
