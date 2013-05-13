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
package telecom.sudparis.eu.paas.core.server.pool.application;

import java.util.ArrayList;
import java.util.List;

import telecom.sudparis.eu.paas.core.server.xml.LinksListType;
import telecom.sudparis.eu.paas.core.server.xml.application.ApplicationType;
import telecom.sudparis.eu.paas.core.server.xml.application.DeployableType;

/**
 * @author sellami
 * 
 */

public enum ApplicationPool {
	INSTANCE;

	private List<ApplicationType> appList = new ArrayList<ApplicationType>();
	private int NextID = 1;

	public List<ApplicationType> getAppList() {
		return appList;
	}

	public void add(ApplicationType app) {
		appList.add(app);
		setNextID(getNextID() + 1);
	}

	public void remove(ApplicationType app) {
		appList.remove(app);
	}

	public void removeAll() {
		appList.removeAll(appList);
		setNextID(1);
	}

	public ApplicationType getApp(String appId) {
		if (appList == null || appList.size() == 0) {
			System.out
					.println("[ApplicationPool]: the Application list is empty!");
			return null;
		} else {
			for (ApplicationType app : appList) {
				if (app.getAppId().toString().equals(appId))
					return app;
			}
		}
		// The specified app was not found
		return null;
	}

	public void updateApp(String appId, DeployableType dep) {
		ApplicationType app = getApp(appId);
		appList.remove(app);
		app.setDeployable(dep);
		appList.add(app);
	}

	public void updateApp(String appId, int nbInstances) {
		ApplicationType app = getApp(appId);
		appList.remove(app);
		app.setNbInstances(nbInstances);
		appList.add(app);
	}

	public void updateApp(String appId, LinksListType linksList) {
		ApplicationType app = getApp(appId);
		appList.remove(app);
		app.setLinksList(linksList);
		appList.add(app);
	}

	/**
	 * Once deployed, add the used envID to the deployed application
	 * representation
	 * 
	 * @param appId
	 *            the application ID
	 * @param envId
	 *            the environnment ID
	 */
	public void updateApp(String appId, String envId) {
		ApplicationType app = getApp(appId);
		appList.remove(app);
		app.setEnvId(Integer.parseInt(envId));
		appList.add(app);
	}

	public void updateApp(String appId, ApplicationType newApp) {
		appList.remove(getApp(appId));
		appList.add(newApp);
	}

	public int getNextID() {
		return NextID;
	}

	public void setNextID(int size) {
		this.NextID = size;
	}

}
