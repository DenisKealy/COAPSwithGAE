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
package telecom.sudparis.eu.paas.core.server.ressources.util;

import telecom.sudparis.eu.paas.core.server.xml.LinkType;
import telecom.sudparis.eu.paas.core.server.xml.LinksListType;

/**
 * This class generates the required links (hplink and state) for the
 * application and environment representations.
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 * 
 */
public class EnvironmentLinkGenerator {

	/**
	 * adds a delete environment state link {@see {@link LinkType} to the Links
	 * List {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param envId
	 *            the environment ID
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addDestroyEnvLink(LinksListType linksList,
			String envId, String apiUrl) {
		String url = formatApiURL(apiUrl) + "environment/" + envId;
		LinkType deleteEnvLink = new LinkType();
		deleteEnvLink.setAction("DELETE");
		deleteEnvLink.setLabel("destroyEnvironment");
		deleteEnvLink.setDescription("this Link destroys the environment");
		deleteEnvLink.setType("state");
		deleteEnvLink.setHref(url);
		linksList.getLink().add(deleteEnvLink);
		return linksList;
	}

	/**
	 * adds an update environment state link {@see {@link LinkType} to the
	 * Links List {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param appId
	 *            the application ID
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addUpdateEnvLink(LinksListType linksList,
			String envId, String apiUrl) {
		String url = formatApiURL(apiUrl) + "environment/" + envId + "/update";
		LinkType deleteEnvLink = new LinkType();
		deleteEnvLink.setAction("POST");
		deleteEnvLink.setLabel("udpateEnvironment");
		deleteEnvLink
				.setDescription("this Link updates the environment using a manifest");
		deleteEnvLink.setType("state");
		deleteEnvLink.setHref(url);
		linksList.getLink().add(deleteEnvLink);
		return linksList;
	}

	/**
	 * adds a describe environment hplink {@see {@link LinkType} to the Links
	 * List {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param envId
	 *            the environment ID
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addGetEnvLink(LinksListType linksList,
			String envId, String apiUrl) {
		String url = formatApiURL(apiUrl) + "environment/" + envId;
		LinkType getEnvLink = new LinkType();
		getEnvLink.setAction("GET");
		getEnvLink
				.setDescription("this Link returns a description of the environment");
		getEnvLink.setLabel("getEnvironment");
		getEnvLink.setHref(url);
		getEnvLink.setType("hplink");
		linksList.getLink().add(getEnvLink);
		return linksList;
	}

	/**
	 * adds a create new environment hplink {@see {@link LinkType} to the Links
	 * List {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addnewEnvLink(LinksListType linksList,
			String apiUrl) {
		String url = formatApiURL(apiUrl) + "environment/";
		LinkType newEnvLink = new LinkType();
		newEnvLink.setAction("POST");
		newEnvLink.setDescription("this Link creates a new environment");
		newEnvLink.setLabel("newEnvironment");
		newEnvLink.setHref(url);
		newEnvLink.setType("hplink");
		linksList.getLink().add(newEnvLink);
		return linksList;
	}

	/**
	 * adds a get environments hplink {@see {@link LinkType} to the Links List
	 * {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addGetEnvsLink(LinksListType linksList,
			String apiUrl) {
		String url = formatApiURL(apiUrl) + "environment/";
		LinkType getEnvsLink = new LinkType();
		getEnvsLink.setAction("GET");
		getEnvsLink
				.setDescription("this Link returns a description of all existing environments");
		getEnvsLink.setLabel("findEnvironments");
		getEnvsLink.setHref(url);
		getEnvsLink.setType("hplink");
		linksList.getLink().add(getEnvsLink);
		return linksList;
	}

	/**
	 * adds a get environment information hplink {@see {@link LinkType} to the
	 * Links List {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addGetInfoLink(LinksListType linksList,
			String apiUrl) {
		String url = formatApiURL(apiUrl) + "environment/info";
		LinkType getInfoLink = new LinkType();
		getInfoLink.setAction("GET");
		getInfoLink
				.setDescription("this Link returns a description about the supported environment characteristics");
		getInfoLink.setLabel("getInformation");
		getInfoLink.setHref(url);
		getInfoLink.setType("hplink");
		linksList.getLink().add(getInfoLink);
		return linksList;
	}

	/**
	 * adds a get deployed applications hplink {@see {@link LinkType} to the
	 * Links List {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param envId
	 *            the environment ID
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addGetDeployedAppLink(LinksListType linksList,
			String envId, String apiUrl) {
		String url = formatApiURL(apiUrl) + "environment/" + envId + "/app";
		LinkType getDeployedAppLink = new LinkType();
		getDeployedAppLink.setAction("GET");
		getDeployedAppLink
				.setDescription("this Link returns the list of deployed applications on an environment");
		getDeployedAppLink.setLabel("getDeployedApplications");
		getDeployedAppLink.setHref(url);
		getDeployedAppLink.setType("hplink");
		linksList.getLink().add(getDeployedAppLink);
		return linksList;
	}

	public static String formatApiURL(String apiUrl) {
		apiUrl = apiUrl.trim();
		if (!apiUrl.endsWith("/"))
			apiUrl = apiUrl + "/";
		return apiUrl;
	}
}
