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
package telecom.sudparis.eu.paas.core.server.ressources.manager.environment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import telecom.sudparis.eu.paas.api.ressources.manager.environment.RestEnvironmentManager;
import telecom.sudparis.eu.paas.core.server.environments.pool.EnvironmentPool;
import telecom.sudparis.eu.paas.core.server.ressources.exception.NotSupportedException;
import telecom.sudparis.eu.paas.core.server.xml.EnvironmentXML;
import telecom.sudparis.eu.paas.core.server.xml.EnvironmentXML.LinksList;
import telecom.sudparis.eu.paas.core.server.xml.EnvironmentXML.LinksList.Link;
import telecom.sudparis.eu.paas.core.server.xml.Error;
import telecom.sudparis.eu.paas.core.server.xml.OperationResponse;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasApplicationManifestType;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasEnvironmentNodeType;

/**
 * REST resource of type EnvironmentManager
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 * 
 */
@Path("environment")
public class EnvironmentManagerRessource implements RestEnvironmentManager {

	/**
	 * ressource bundle to get the connexion credentials
	 */
	private static ResourceBundle rb = ResourceBundle
			.getBundle("telecom.sudparis.eu.paas.core.server.ressources.credentials");

	/**
	 * The public OS-PaaS API URL
	 */
	private static String apiUrl = rb.getString("api.public.url");

	/**
	 * An element to display The response
	 */
	private OperationResponse or = new OperationResponse();

	/**
	 * An element to display Errors
	 */
	private Error error = new Error();

	@Override
	public Response deleteEnvironment(String envid) {
		try {
			boolean result = EnvironmentPool.INSTANCE.delete(envid);
			if (result) {
				or.setValue("The environment " + envid
						+ " was succesfully deleted from the Environment pool.");
				return Response.status(Response.Status.OK).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			} else {
				or.setValue("The environment " + envid
						+ " was not found in the Environment pool.");
				return Response.status(Response.Status.ACCEPTED).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to delete the environment: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to delete the environment: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response findEnvironments() {
		List<EnvironmentXML> envList = EnvironmentPool.INSTANCE.getEnvList();
		if (envList != null) {
			return Response.status(Response.Status.OK)
					.entity(new GenericEntity<List<EnvironmentXML>>(envList) {
					}).type(MediaType.APPLICATION_XML_TYPE).build();
		} else {
			System.out.println("Failed to retrieve the environments list.");
			error.setValue("Failed to retrieve the environments list.");
			return Response.status(Response.Status.NO_CONTENT).entity(error)
					.build();
		}
	}

	@Override
	public Response getEnvironment(String envid) {
		try {
			EnvironmentXML env = EnvironmentPool.INSTANCE.getEnv(envid);
			if (env != null) {
				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<EnvironmentXML>(env) {
						}).type(MediaType.APPLICATION_XML_TYPE).build();
			} else {
				System.out.println("Failed to find the cloud Environment "
						+ envid);
				error.setValue("Failed to find the cloud Environment " + envid);
				return Response.status(Response.Status.ACCEPTED).entity(error)
						.build();
			}
		} catch (Exception e) {
			System.out.println("Failed to display the environment: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to display the environment: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response createEnvironment(String environmentTemplateDescriptor) {
		try {
			if (environmentTemplateDescriptor != null) {
				InputStream is = new ByteArrayInputStream(
						environmentTemplateDescriptor.getBytes());

				JAXBContext jaxbContext;
				PaasApplicationManifestType manifest = new PaasApplicationManifestType();
				try {
					jaxbContext = JAXBContext
							.newInstance("telecom.sudparis.eu.paas.core.server.xml.manifest");
					Unmarshaller jaxbUnmarshaller = jaxbContext
							.createUnmarshaller();

					JAXBElement<PaasApplicationManifestType> root = jaxbUnmarshaller
							.unmarshal(new StreamSource(is),
									PaasApplicationManifestType.class);
					manifest = root.getValue();

				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// retrieve envName
				String envName = manifest.getPaasEnvironment()
						.getName();

				// retrieve envDescription
				String envDescription = manifest
						.getPaasEnvironment().getPaasEnvironmentTemplate().getDescription();

				// TODO consider the case of several containers
				List<String> containerNames = new ArrayList<String>();
				List<String> cartridgeNames = new ArrayList<String>();

				List<PaasEnvironmentNodeType> lstNodes = manifest.getPaasEnvironment().getPaasEnvironmentTemplate().getPaasEnvironmentNode();

				for (PaasEnvironmentNodeType node : lstNodes) {
					if (node.getContentType().equals("container"))
						containerNames.add(node.getName());
					else if (node.getContentType().equals("database"))
						cartridgeNames.add(node.getName());
				}

				// generate envID
				Long id = getNextId();

				EnvironmentXML env = new EnvironmentXML();
				env.setEnvId(Long.toString(id));
				env.setEnvName(envName);
				env.setEnvDesc(envDescription);
				env.setContainerNames(containerNames);
				env.setCartridgeNames(cartridgeNames);

				// add the delete and get environment links
				LinksList linksList = new LinksList();
				linksList = addGetEnvLink(linksList, Long.toString(id));
				linksList = addDeleteEnvLink(linksList, Long.toString(id));
				env.setLinksList(linksList);

				EnvironmentPool.INSTANCE.add(env);

				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<EnvironmentXML>(env) {
						}).type(MediaType.APPLICATION_XML_TYPE).build();

			} else {
				System.out
						.println("Failed to retrieve the cloud Environment Descriptor");
				error.setValue("Failed to retrieve the cloud Environment Descriptor.");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(error).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to create the environment: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to create the environment: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response updateEnvironment(String envid,
			String environmentTemplateDescriptor) {
		throw new NotSupportedException(
				"The updateEnvironment is not yet implemented.");
	}

	@Override
	public Response getDeployedApplications(String envid) {
		throw new NotSupportedException(
				"The getDeployedApplications is not yet implemented.");
	}

	@Override
	public Response getInformations() {
		throw new NotSupportedException(
				"The getInformations is not yet implemented.");
	}

	// private methods
	private synchronized Long getNextId() {
		return new Long(EnvironmentPool.INSTANCE.getNextID());
	}

	private LinksList addDeleteEnvLink(LinksList linksList, String envId) {
		String url = formatApiURL(apiUrl) + "environment/" + envId;
		Link deleteEnvLink = new Link();
		deleteEnvLink.setAction("DELETE");
		deleteEnvLink.setLabel("deleteEnvironment(envid)");
		deleteEnvLink.setHref(url);
		linksList.getLink().add(deleteEnvLink);
		return linksList;
	}

	private LinksList addGetEnvLink(LinksList linksList, String envId) {
		String url = formatApiURL(apiUrl) + "environment/" + envId;
		Link getEnvLink = new Link();
		getEnvLink.setAction("GET");
		getEnvLink.setLabel("getEnvironment(envid)");
		getEnvLink.setHref(url);
		linksList.getLink().add(getEnvLink);
		return linksList;
	}

	private String formatApiURL(String apiUrl) {
		apiUrl = apiUrl.trim();
		if (!apiUrl.endsWith("/"))
			apiUrl = apiUrl + "/";
		return apiUrl;
	}

}
