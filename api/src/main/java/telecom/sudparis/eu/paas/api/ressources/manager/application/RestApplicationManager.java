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
package telecom.sudparis.eu.paas.api.ressources.manager.application;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.FormDataParam;

@Path("app")
public interface RestApplicationManager {

	/**
	 * Creates a new application. If the application is multitenant, it will be
	 * accessible for all tenant Command: POST /app
	 * 
	 * @param cloudApplicationDescriptor
	 *            A Cloud Application Descriptor (manifest) must be provided.
	 * @return XML file An: enriched Cloud Application Descriptor (manifest).
	 *         The appID and Link element will be added to the Manifest.
	 */
	@POST
	@Consumes("application/xml")
	@Produces("application/xml")
	Response createApplication(String cloudApplicationDescriptor);
	
	
//	/**
//	 * Uploads a file. Used for the application deployable files uplaod 
//	 * Command: POST /app/upload
//	 * 
//	 * @param file
//	 *            The file to upload.
//	 * @return 
//	 */
//	@POST
//	@Path("{appId}/upload")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	Response upoadFile(@PathParam("appId") String appid,
//			@FormDataParam("file") InputStream uploadedInputStream,
//			@FormDataParam("file") FormDataContentDisposition fileDetail);
	
	
	/**
	 * Updates an existing application. Command: POST /app/{appId}/update
	 * 
	 * @param cloudApplicationDescriptor
	 *            A Cloud Application Descriptor (manifest) must be provided.
	 * @return XML file An: enriched Cloud Application Descriptor (manifest).
	 *         The appID and Link element will be added to the Manifest.
	 */
	@POST
	@Path("{appId}/update")
	@Consumes("application/xml")
	@Produces("application/xml")
	Response updateApplication(@PathParam("appId") String appid,String cloudApplicationDescriptor);

	/**
	 * List applications Command: GET /app/
	 * 
	 * @return A List of available applications
	 */
	@GET
	Response findApplications();

	/**
	 * Start an application version instance Command: POST
	 * /app/{appId}/version/{versionId}/instance/{instanceId}/action/start
	 * 
	 * @param appid
	 *            The application's ID
	 * @return \\TODO
	 */

	@POST
	@Path("{appId}/start")
	Response startApplication(@PathParam("appId") String appid);

	/**
	 * Stop an application version instance Command: POST
	 * /app/{appId}/version/{versionId}/instance/{instanceId}/action/start
	 * 
	 * @param appid
	 *            The application's ID
	 * @return \\TODO
	 */

	@POST
	@Path("{appId}/stop")
	Response stopApplication(@PathParam("appId") String appid);
	
	/**
	 * Restart an application Command: POST
	 * /app/{appId}/restart
	 * 
	 * @param appid
	 *            The application's ID
	 * @return \\TODO
	 */

	@POST
	@Path("{appId}/restart")
	Response restartApplication(@PathParam("appId") String appid);

	/**
	 * Describe application. Command: GET /app/{appId}
	 * 
	 * @param appid
	 *            The application's ID
	 * @return XML file An: The Cloud Application Descriptor
	 */

	@GET
	@Path("{appId}")
	@Produces("application/xml")
	Response describeApplication(@PathParam("appId") String appId);

	/**
	 * Delete application. Removes all existing versions. Command: DELETE
	 * /app/{appId}
	 * 
	 * @param appid
	 *            The application's ID
	 * @return HTTP status
	 */

	@DELETE
	@Path("{appId}/delete")
	@Produces("application/xml")
	Response deleteApplication(@PathParam("appId") String appId);

	/**
	 * Delete applications. Removes all existing applications and all their
	 * versions. Command: DELETE /app
	 * 
	 * @return HTTP status
	 */

	@DELETE
	@Path("delete")
	@Produces("application/xml")
	Response deleteApplications();
	
	/**
	 * Deploys an application instance on an available environment <br>
	 * Command: POST
	 * /environment/{envId}/action/deploy/app/{appId}/version/{versionId
	 * }/instance/{instanceId}
	 * 
	 * @param envid
	 *            The environment's ID.
	 * @param appid
	 *            The application's ID.
	 */
	@POST
	@Path("{appId}/action/deploy/env/{envId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/xml")
	Response deployApplication(
			@PathParam("appId") String appid, @PathParam("envId") String envid,
			@FormDataParam("file") InputStream uploadedInputStream);

	/**
	 * Undeploys an application instance on an available environment <br>
	 * Command: DELETE
	 * /environment/{envId}/action/undeploy/app/{appId}/version/{
	 * versionId}/instance/{instanceId}
	 * 
	 * @param envid
	 *            The environment's ID.
	 * @param appid
	 *            The application's ID.
	 */
	@POST
	@Path("/{appId}/action/undeploy/env/{envId}")
	Response undeployApplication(
			@PathParam("envId") String envid, @PathParam("appId") String appid);
}
