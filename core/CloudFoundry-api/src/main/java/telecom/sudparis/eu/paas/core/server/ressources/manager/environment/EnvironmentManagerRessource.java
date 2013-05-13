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

import org.cloudfoundry.client.lib.domain.Staging;

import telecom.sudparis.eu.paas.api.ressources.manager.environment.RestEnvironmentManager;
import telecom.sudparis.eu.paas.core.server.pool.environment.EnvironmentPool;
import telecom.sudparis.eu.paas.core.server.ressources.exception.NotSupportedException;
import telecom.sudparis.eu.paas.core.server.ressources.util.EnvironmentLinkGenerator;
import telecom.sudparis.eu.paas.core.server.xml.Error;
import telecom.sudparis.eu.paas.core.server.xml.LinksListType;
import telecom.sudparis.eu.paas.core.server.xml.OperationResponse;
import telecom.sudparis.eu.paas.core.server.xml.environment.ConfigurationType;
import telecom.sudparis.eu.paas.core.server.xml.environment.EntryType;
import telecom.sudparis.eu.paas.core.server.xml.environment.EnvironmentType;
import telecom.sudparis.eu.paas.core.server.xml.environment.list.EnvironmentsType;
import telecom.sudparis.eu.paas.core.server.xml.environment.list.SimpleEnvironmentType;
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
	 * The public CF-PaaS API URL
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

	/**
	 * The Frameworks supported in the current implementation
	 */
	public static final String JAVA_WEB = "java_web";
	public static final String SPRING_WEB = "spring_web/1.0";
	public static final String NODE = "node";

	/**
	 * The Runtimes supported in the current implementation
	 */
	public static final String JAVA6 = "java";
	public static final String JAVA7 = "java7";

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
				String envName = manifest.getPaasEnvironment().getName();

				// retrieve memory
				long envMemory = manifest.getPaasEnvironment()
						.getPaasEnvironmentTemplate().getMemory();

				// retrieve envDescription
				String envDescription = manifest.getPaasEnvironment()
						.getPaasEnvironmentTemplate().getDescription();

				// We suppose that we associate the runtime java and the
				// framework "java_web" to the container tomcat
				// TODO consider the case of several containers
				List<String> containerNames = new ArrayList<String>();
				List<String> containerVersions = new ArrayList<String>();
				// containerNames.add("tomcat");// we consider only one
				// container
				List<String> serviceNames = new ArrayList<String>();
				List<PaasEnvironmentNodeType> lstNodes = manifest
						.getPaasEnvironment().getPaasEnvironmentTemplate()
						.getPaasEnvironmentNode();

				for (PaasEnvironmentNodeType node : lstNodes) {
					// If no container was specified in the Environment
					// Descriptor, Tomcat will be choosen
					if (node.getContentType().equals("container")) {
						containerNames.add(node.getName());
						containerVersions.add(node.getVersion());
					} else if (node.getContentType().equals("database"))
						serviceNames.add(node.getName());
				}

				// TODO this value may be useful when handling several
				// containers
				int containerIndex = 0;
				// the default value of staging
				Staging s = new Staging(JAVA6, JAVA_WEB);
				s.setCommand("no");
				for (String cName : containerNames) {
					if (cName.equals("tomcat")) {
						if (containerVersions.get(containerIndex).startsWith(
								"6")) {
							s = new Staging(JAVA6, JAVA_WEB);
							s.setCommand("no");
							// staging.put("runtime", JAVA6);
						} else if (containerVersions.get(containerIndex)
								.startsWith("7")) {
							s = new Staging(JAVA7, JAVA_WEB);
							s.setCommand("no");
							// staging.put("runtime", JAVA7);
						}
						// staging.put("framework", JAVA_WEB);

					} else if (cName.equals("spring")) {
						s = new Staging(JAVA6, SPRING_WEB);
						s.setCommand("no");
						// staging.put("runtime", JAVA6);
						// staging.put("framework", SPRING_WEB);
						// staging.put("command", "no");
					} else if (cName.equals("node.js")) {
						s = new Staging(NODE, NODE);
						s.setCommand("no");
						// staging.put("runtime", NODE);
						// staging.put("framework", NODE);
						// staging.put("command", "no");
					}
					containerIndex++;
				}

				// retrieve the environment configuration (previously called
				// staging)
				// the different configuration elements will be saved in a
				// ConfigurationType element
				ConfigurationType conf = new ConfigurationType();

				// get the runtime
				EntryType runtime = new EntryType();
				runtime.setKey("runtime");
				runtime.setValue(s.getRuntime());
				conf.getEntry().add(runtime);

				// get the framework
				EntryType framework = new EntryType();
				framework.setKey("framework");
				framework.setValue(s.getFramework());
				conf.getEntry().add(framework);

				// get the command
				EntryType command = new EntryType();
				command.setKey("command");
				command.setValue(s.getCommand());
				conf.getEntry().add(command);

				// get the service Names
				for (String service : serviceNames) {
					EntryType e = new EntryType();
					e.setKey("service");
					e.setValue(service);
					conf.getEntry().add(e);
				}

				// StagingXML staging = new StagingXML(s);

				// generate envID
				Long id = getNextId();

				EnvironmentType env = new EnvironmentType();
				env.setEnvId((int) (long) id);
				// env.setEnvId(Long.toString(id));
				env.setEnvName(envName);
				env.setEnvDesc(envDescription);
				env.setConfiguration(conf);
				// env.setStaging(staging);
				// env.setServiceNames(serviceNames);
				env.setEnvMemory((int) (long) envMemory);

				// add the environment STATE and hplinks links
				LinksListType linksList = new LinksListType();
				linksList = EnvironmentLinkGenerator.addGetEnvLink(linksList,
						Long.toString(id), apiUrl);
				linksList = EnvironmentLinkGenerator.addGetDeployedAppLink(
						linksList, Long.toString(id), apiUrl);
				linksList = EnvironmentLinkGenerator.addGetEnvLink(linksList,
						Long.toString(id), apiUrl);
				linksList = EnvironmentLinkGenerator.addGetEnvsLink(linksList,
						apiUrl);
				linksList = EnvironmentLinkGenerator.addGetInfoLink(linksList,
						apiUrl);
				linksList = EnvironmentLinkGenerator.addnewEnvLink(linksList,
						apiUrl);

				linksList = EnvironmentLinkGenerator.addDestroyEnvLink(
						linksList, Long.toString(id), apiUrl);
				linksList = EnvironmentLinkGenerator.addUpdateEnvLink(
						linksList, Long.toString(id), apiUrl);

				env.setLinksList(linksList);

				EnvironmentPool.INSTANCE.add(env);

				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<EnvironmentType>(env) {
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
		List<SimpleEnvironmentType> envList = formatEnvList(EnvironmentPool.INSTANCE.getEnvList());
		EnvironmentsType envs=new EnvironmentsType();
		envs.setEnvironment(envList);
		
		if (envList != null) {
			return Response.status(Response.Status.OK)
					.entity(new GenericEntity<EnvironmentsType>(envs) {
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
			EnvironmentType env = EnvironmentPool.INSTANCE.getEnv(envid);
			if (env != null) {
				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<EnvironmentType>(env) {
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
	
	private List<SimpleEnvironmentType> formatEnvList(List<EnvironmentType> envList) {
		List<SimpleEnvironmentType> listEnvType=new ArrayList<SimpleEnvironmentType>();
		for (EnvironmentType e:envList){
			SimpleEnvironmentType envType=new SimpleEnvironmentType();
			envType.setDescription(e.getEnvDesc());
			envType.setId(e.getEnvId());
			envType.setName(e.getEnvName());
			envType.setUri(EnvironmentLinkGenerator.formatApiURL(apiUrl) + "environment/" + e.getEnvId());
			listEnvType.add(envType);
			 
		}
		return listEnvType;
	}

}
