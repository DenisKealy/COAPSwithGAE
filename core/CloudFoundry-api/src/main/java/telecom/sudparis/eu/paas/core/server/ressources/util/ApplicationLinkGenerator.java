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
public class ApplicationLinkGenerator {

	/**
	 * adds a describe application hplink {@see {@link LinkType} to the Links
	 * List {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param appId
	 *            the application ID
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addDescribeAppLink(LinksListType linksList,
			String appId, String apiUrl) {
		String url = formatApiURL(apiUrl) + "app/" + appId;
		LinkType describeAppLink = new LinkType();
		describeAppLink.setAction("GET");
		describeAppLink.setLabel("describeApplication");
		describeAppLink
				.setDescription("this Link provides a description of the application");
		describeAppLink.setType("hplink");
		describeAppLink.setHref(url);
		linksList.getLink().add(describeAppLink);
		return linksList;
	}

	/**
	 * adds a create application hplink {@see {@link LinkType} to the Links
	 * List {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addCreateAppLink(LinksListType linksList,
			String apiUrl) {
		String url = formatApiURL(apiUrl) + "app/";
		LinkType createAppLink = new LinkType();
		createAppLink.setAction("POST");
		createAppLink.setLabel("createApplication");
		createAppLink
				.setDescription("this Link allows the creation of an applications by providing its manifest");
		createAppLink.setType("hplink");
		createAppLink.setHref(url);
		linksList.getLink().add(createAppLink);
		return linksList;
	}

	/**
	 * adds a find applications hplink {@see {@link LinkType} to the Links List
	 * {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addFindAppsLink(LinksListType linksList,
			String apiUrl) {
		String url = formatApiURL(apiUrl) + "app/";
		LinkType createAppLink = new LinkType();
		createAppLink.setAction("GET");
		createAppLink.setLabel("findApplications");
		createAppLink
				.setDescription("this Link displays all existing applications");
		createAppLink.setType("hplink");
		createAppLink.setHref(url);
		linksList.getLink().add(createAppLink);
		return linksList;
	}

	/**
	 * adds a getEnvironmentsOfAnApp hplink {@see {@link LinkType} to the Links
	 * List {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param appId
	 *            the application ID
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addGetEnvsOfAppsLink(LinksListType linksList,
			String appId, String apiUrl) {
		String url = formatApiURL(apiUrl) + "app/" + appId + "/environment";
		LinkType createAppLink = new LinkType();
		createAppLink.setAction("GET");
		createAppLink.setLabel("getEnvironmentsOfAnApp");
		createAppLink
				.setDescription("this Link displays all environment of an application");
		createAppLink.setType("hplink");
		createAppLink.setHref(url);
		linksList.getLink().add(createAppLink);
		return linksList;
	}

	/**
	 * adds a start application state link {@see {@link LinkType} to the Links
	 * List {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param appId
	 *            the application ID
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addStartAppLink(LinksListType linksList,
			String appId, String apiUrl) {
		String url = formatApiURL(apiUrl) + "app/" + appId + "/start";
		LinkType startAppLink = new LinkType();
		startAppLink.setAction("POST");
		startAppLink.setLabel("startApplication");
		startAppLink.setDescription("this Link starts the application");
		startAppLink.setType("state");
		startAppLink.setHref(url);
		linksList.getLink().add(startAppLink);
		return linksList;
	}

	/**
	 * adds a stop application state link {@see {@link LinkType} to the Links
	 * List {@see {@link LinksListType}
	 * 
	 * @param linksList
	 *            the links list to update
	 * @param appId
	 *            the application ID
	 * @param apiUrl
	 *            the public URL of the used API
	 * @return the updated links list
	 */
	public static LinksListType addStopAppLink(LinksListType linksList,
			String appId, String apiUrl) {
		String url = formatApiURL(apiUrl) + "app/" + appId + "/stop";
		LinkType stopAppLink = new LinkType();
		stopAppLink.setAction("POST");
		stopAppLink.setLabel("stopApplication");
		stopAppLink.setDescription("this Link stops the application");
		stopAppLink.setType("state");
		stopAppLink.setHref(url);
		linksList.getLink().add(stopAppLink);
		return linksList;
	}

	/**
	 * adds a restart application state link {@see {@link LinkType} to the
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
	public static LinksListType addRestartAppLink(LinksListType linksList,
			String appId, String apiUrl) {
		String url = formatApiURL(apiUrl) + "app/" + appId + "/restart";
		LinkType restartAppLink = new LinkType();
		restartAppLink.setAction("POST");
		restartAppLink.setLabel("restartApplication");
		restartAppLink.setDescription("this Link restarts the application");
		restartAppLink.setType("state");
		restartAppLink.setHref(url);
		linksList.getLink().add(restartAppLink);
		return linksList;
	}

	/**
	 * adds an update application state link {@see {@link LinkType} to the
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
	public static LinksListType addUpdateAppLink(LinksListType linksList,
			String appId, String apiUrl) {
		String url = formatApiURL(apiUrl) + "app/" + appId + "/update";
		LinkType updateAppLink = new LinkType();
		updateAppLink.setAction("POST");
		updateAppLink.setLabel("updateApplication");
		updateAppLink.setDescription("this Link updates the application");
		updateAppLink.setType("state");
		updateAppLink.setHref(url);
		linksList.getLink().add(updateAppLink);
		return linksList;
	}

	/**
	 * adds a destroy application state link {@see {@link LinkType} to the
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
	public static LinksListType addDestroyAppLink(LinksListType linksList,
			String appId, String apiUrl) {
		String url = formatApiURL(apiUrl) + "app/" + appId;
		LinkType destroyAppLink = new LinkType();
		destroyAppLink.setAction("DELETE");
		destroyAppLink.setLabel("destroyApplication");
		destroyAppLink.setDescription("this Link destroys the application");
		destroyAppLink.setType("state");
		destroyAppLink.setHref(url);
		linksList.getLink().add(destroyAppLink);
		return linksList;
	}

	public static String formatApiURL(String apiUrl) {
		apiUrl = apiUrl.trim();
		if (!apiUrl.endsWith("/"))
			apiUrl = apiUrl + "/";
		return apiUrl;
	}
}
