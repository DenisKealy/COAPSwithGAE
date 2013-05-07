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
package telecom.sudparis.eu.paas.core.server.environments.pool;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import telecom.sudparis.eu.paas.core.server.applications.pool.Application;
import telecom.sudparis.eu.paas.core.server.xml.StagingXML;


/**
 * An Environment
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 */

/**
 * @author sellami
 * 
 */
@XmlRootElement(name = "environment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Environment {
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
	 * Environment memory
	 */
	@XmlAttribute
	private long envMemory;

	/**
	 * Environment description
	 */
	@XmlAttribute
	private String envDesc;

	/**
	 * The staging
	 */
	//@XmlElement
	//private Map<String, String> staging;
	 @XmlElement
	 private StagingXML staging;

	/**
	 * The service Names
	 */
	@XmlElement
	private List<String> serviceNames;

	/**
	 * List of the provided Links for the Application
	 */
	@XmlElement
    protected Environment.LinksList linksList;

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

	public StagingXML getStaging() {
		return staging;
	}

	public void setStaging(StagingXML staging) {
		this.staging = staging;
	}

	public List<String> getServiceNames() {
		return serviceNames;
	}

	public void setServiceNames(List<String> serviceNames) {
		this.serviceNames = serviceNames;
	}
		

	/**
     * Gets the value of the linksList property.
     * 
     * @return
     *     possible object is
     *     {@link Application.LinksList }
     *     
     */
    public Environment.LinksList getLinksList() {
        return linksList;
    }

    /**
     * Sets the value of the linksList property.
     * 
     * @param value
     *     allowed object is
     *     {@link Application.LinksList }
     *     
     */
    public void setLinksList(Environment.LinksList value) {
        this.linksList = value;
    }


    public long getEnvMemory() {
		return envMemory;
	}

	public void setEnvMemory(long envMemory) {
		this.envMemory = envMemory;
	}


	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class LinksList {

        @XmlElement
        protected List<Environment.LinksList.Link> link;

        public List<Environment.LinksList.Link> getLink() {
            if (link == null) {
                link = new ArrayList<Environment.LinksList.Link>();
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
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLabel() {
                return label;
            }

            /**
             * Sets the value of the label property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLabel(String value) {
                this.label = value;
            }

            /**
             * Gets the value of the action property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAction() {
                return action;
            }

            /**
             * Sets the value of the action property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAction(String value) {
                this.action = value;
            }

            /**
             * Gets the value of the href property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHref() {
                return href;
            }

            /**
             * Sets the value of the href property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHref(String value) {
                this.href = value;
            }

        }

    }

}
