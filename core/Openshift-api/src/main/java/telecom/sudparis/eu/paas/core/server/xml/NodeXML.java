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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Node XML element
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 */
@XmlRootElement(name = "node")
@XmlAccessorType(XmlAccessType.FIELD)
public class NodeXML {

	/**
	 * Node Current size
	 */
	@XmlAttribute
	private int nodeCureentSize;

	/**
	 * Node ID
	 */
	@XmlAttribute
	private String nodeID;

	/**
	 * Node max size
	 */
	@XmlAttribute
	private int nodeMaxSize;

	/**
	 * Node min size
	 */
	@XmlAttribute
	private int nodeMinSize;

	/**
	 * Node Name
	 */
	@XmlAttribute
	private String nodeName;

	/**
	 * Default constructor
	 */
	public NodeXML() {

	}

	public int getNodeCureentSize() {
		return nodeCureentSize;
	}

	public void setNodeCureentSize(int nodeCureentSize) {
		this.nodeCureentSize = nodeCureentSize;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public int getNodeMaxSize() {
		return nodeMaxSize;
	}

	public void setNodeMaxSize(int nodeMaxSize) {
		this.nodeMaxSize = nodeMaxSize;
	}

	public int getNodeMinSize() {
		return nodeMinSize;
	}

	public void setNodeMinSize(int nodeMinSize) {
		this.nodeMinSize = nodeMinSize;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

}
