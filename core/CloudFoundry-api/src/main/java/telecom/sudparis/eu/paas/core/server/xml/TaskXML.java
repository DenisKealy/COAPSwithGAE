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
/**
 * 
 */
package telecom.sudparis.eu.paas.core.server.xml;

/**
 * @author sellami
 *
 */

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "task")
public class TaskXML {

	/**
	 * Task id
	 */
	@XmlAttribute
	private Long id;

	/**
	 * Task status
	 */
	@XmlAttribute
	private String status;

	/**
     *
     */
	@XmlAttribute
	private Date startTime;

	@XmlAttribute
	private Date endTime;

	// @XmlAttribute
	// private String operationName;

	@XmlElement
	private List<Link> link;

	/**
	 * Default constructor
	 */
	public TaskXML() {
		// this.link = new ArrayList<Link>();
	}

	public Long getId() {
		return id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getStatus() {
		return status;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

	public List<Link> getLink() {
		return link;
	}

	public void setLink(List<Link> link) {
		this.link = link;
	}
}
