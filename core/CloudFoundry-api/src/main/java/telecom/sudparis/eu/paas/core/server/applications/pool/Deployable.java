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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "deployable")
@XmlAccessorType(XmlAccessType.FIELD)
public class Deployable {
	
	@XmlAttribute
	private String deployableName;
	
	@XmlAttribute
	private String deployableType;
	
	@XmlAttribute
	private String deployableDirectory;
	
	
	public String getDeployableName() {
		return deployableName;
	}
	public void setDeployableName(String deployableName) {
		this.deployableName = deployableName;
	}
	public String getDeployableType() {
		return deployableType;
	}
	public void setDeployableType(String deployableType) {
		this.deployableType = deployableType;
	}
	public String getDeployableDirectory() {
		return deployableDirectory;
	}
	public void setDeployableDirectory(String deployableDirectory) {
		this.deployableDirectory = deployableDirectory;
	}
	
	/*deployableName -->paas_deployable name="war"
	Deployable Type --> content_type
	DeployableDirectory --> Location*/

}
