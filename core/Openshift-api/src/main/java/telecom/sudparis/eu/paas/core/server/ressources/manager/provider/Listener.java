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
package telecom.sudparis.eu.paas.core.server.ressources.manager.provider;

import javax.ws.rs.ext.Provider;

import com.sun.jersey.api.model.AbstractResourceModelContext;
import com.sun.jersey.api.model.AbstractResourceModelListener;

@Provider
public class Listener implements AbstractResourceModelListener {

	static final String ccUrl = System.getProperty("vcap.target",
			"https://api.cloudfoundry.com");

	static final String TEST_USER_EMAIL = System.getProperty("vcap.email",
			"mohamed.mohamed@it-sudparis.eu");

	static final String TEST_USER_PASS = System.getProperty("vcap.passwd",
			"budfSzrq");

	@Override
	public void onLoaded(AbstractResourceModelContext modelContext) {
		System.out.println("##### resource model initiated");
	}
}
