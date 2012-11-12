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
import java.util.Map;

import telecom.sudparis.eu.paas.core.server.applications.pool.Application;

/**
 * @author sellami
 * 
 */

public enum EnvironmentPool {
	INSTANCE;

	private List<Environment> envList = new ArrayList<Environment>();
	private int NextID=1;

	public List<Environment> getEnvList() {
		return envList;
	}

	public void add(Environment env) {
		envList.add(env);
		NextID++;
	}
	
	/**
	 * 
	 * @param env
	 * 		the environment to delete
	 * @return 0 if the env was not found, 1 if the env was deleted
	 */
	public boolean delete(String envId) {
		if (envList == null || envList.size() == 0) {
			System.out.println("[EnvironmentPool]: the Environment list is empty!");
			return false;
		} else {
			for (Environment env : envList) {
				if (env.getEnvId().equals(envId)){
					envList.remove(env);
					return true;
				}
			}
		}
		// The specified env was not found
		return false;
	}

	public Environment getEnv(String envId) {
		if (envList == null || envList.size() == 0) {
			System.out.println("[EnvironmentPool]: the Environment list is empty!");
			return null;
		} else {
			for (Environment env : envList) {
				if (env.getEnvId().equals(envId))
					return env;
			}
		}
		// The specified env was not found
		return null;
	}
	
	public void updateEnv(String envId, Map<String, String> linksList){
		Environment env=getEnv(envId);
		envList.remove(env);
		env.setLinksList(linksList);
		envList.add(env);		
	}

	public int getNextID() {
		return NextID;
	}

	public void setNextID(int size) {
		this.NextID = size;
	}
}

