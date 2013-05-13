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
package telecom.sudparis.eu.paas.api.ressources.manager.environment;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("environment")
public interface RestEnvironmentManager {

	/**
	 * Creates a new environment <br>
	 * Command: POST /environment/
	 * 
	 * @param environmentTemplateDescriptor
	 *            An environment template descriptor must be provided.
	 * @return An enriched environment template descriptor. The envID and Link
	 *         element will be added to the descriptor
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	Response createEnvironment(String environmentTemplateDescriptor);

	
	/**
	 * Updates an existing environment <br>
	 * Command: POST /environment/{envId}/update
	 * 
	 * @param environmentTemplateDescriptor
	 *            An environment template descriptor must be provided.
	 * @return An enriched environment template descriptor. The envID and Link
	 *         element will be added to the descriptor
	 */
	@POST
	@Path("{envId}/update")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	Response updateEnvironment(@PathParam("envId") String envid,String environmentTemplateDescriptor);
	
	/**
	 * Deletes an environment <br>
	 * Command: DELETE /environment/{envId}
	 * 
	 * @param envid
	 *            The environment's ID.
	 */
	@DELETE
	@Path("{envId}")
	@Produces(MediaType.APPLICATION_XML)
	Response deleteEnvironment(@PathParam("envId") String envid);

	/**
	 * Finds the list of the available environments <br>
	 * Command: GET /environment
	 * 
	 * @return a list of the available environment descriptions.
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	Response findEnvironments();

	/**
	 * Get the description of an environment <br>
	 * Command: GET /environment/{envId}
	 * 
	 * @param envid
	 *            The environment's ID.
	 * @return the description of the environment envid
	 */
	@GET
	@Path("{envId}")
	@Produces(MediaType.APPLICATION_XML)
	Response getEnvironment(@PathParam("envId") String envid);

	/**
	 * List the deployed application instances in an environment <br>
	 * Command: GET /environment/{envId}/app/
	 * 
	 * @param envid
	 *            The environment's ID.
	 * @return a list of application version instances in the environment envid
	 */
	@GET
	@Path("{envId}/app")
	@Produces(MediaType.APPLICATION_XML)
	Response getDeployedApplications(
			@PathParam("envId") String envid);
	
	/**
	 * lists the runtimes, frameworks and services supported by the targeted PaaS <br>
	 * Command: GET /environment/info/
	 * 
	 * @return a list of runtimes, frameworks and services supported by the targeted PaaS
	 */
	@GET
	@Path("info")
	@Produces(MediaType.APPLICATION_XML)
	Response getInformations();

}
