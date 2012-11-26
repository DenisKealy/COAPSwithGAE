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
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.zip.ZipFile;

import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.archive.ApplicationArchive;
import org.cloudfoundry.client.lib.archive.DirectoryApplicationArchive;
import org.cloudfoundry.client.lib.archive.ZipApplicationArchive;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.cloudfoundry.client.lib.domain.CloudEntity;
import org.cloudfoundry.client.lib.domain.CloudService;
import org.cloudfoundry.client.lib.domain.Staging;
import org.springframework.web.client.HttpServerErrorException;


import telecom.sudparis.eu.paas.api.ressources.manager.environment.RestEnvironmentManager;
import telecom.sudparis.eu.paas.core.server.applications.pool.Application;
import telecom.sudparis.eu.paas.core.server.applications.pool.ApplicationPool;
import telecom.sudparis.eu.paas.core.server.environments.pool.Environment;
import telecom.sudparis.eu.paas.core.server.environments.pool.EnvironmentPool;
import telecom.sudparis.eu.paas.core.server.ressources.exception.NotSupportedException;
import telecom.sudparis.eu.paas.core.server.xml.Error;
import telecom.sudparis.eu.paas.core.server.xml.OperationResponse;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasManifestType;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasNodeType;

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
	private static ResourceBundle rb = ResourceBundle.getBundle("telecom.sudparis.eu.paas.core.server.ressources.credentials");
	
	/**
	 * The target cloud Foundry URL
	 */
	private static final String ccUrl=rb.getString("vcap.target");
	
	/**
	 * The CloudFoundry user mail 
	 */
	private static final String TEST_USER_EMAIL =rb.getString("vcap.email");
	
	/**
	 * The CloudFoundry user password
	 */
	private static final String TEST_USER_PASS =rb.getString("vcap.passwd");

	/**
	 * The Environment pool
	 */
	private EnvironmentPool envPool;

	/**
	 * The Environment pool
	 */
	private ApplicationPool appPool;

	/**
	 * An element to display The response
	 */
	private OperationResponse or = new OperationResponse();

	/**
	 * An element to display Errors
	 */
	private Error error = new Error();

	@Override
	public Response createEnvironment(String environmentTemplateDescriptor) {
		try {
			if (environmentTemplateDescriptor != null) {
				InputStream is = new ByteArrayInputStream(
						environmentTemplateDescriptor.getBytes());

				JAXBContext jaxbContext;
				PaasManifestType manifest = new PaasManifestType();
				try {
					jaxbContext = JAXBContext
							.newInstance("telecom.sudparis.eu.paas.core.server.xml.manifest");
					Unmarshaller jaxbUnmarshaller = jaxbContext
							.createUnmarshaller();

					JAXBElement<PaasManifestType> root = jaxbUnmarshaller
							.unmarshal(new StreamSource(is),
									PaasManifestType.class);
					manifest = root.getValue();

				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// retrieve envName
				String envName = manifest.getPaasApplication()
						.getPaasEnvironment().getName();

				// retrieve envDescription
				String envDescription = manifest.getPaasApplication()
						.getPaasEnvironment().getDescription();

				// retrieve staging.

				// We suppose that we associate the runtime java and the
				// framework "java_web" to the container tomcat
				// TODO consider the case of several containers
				List<String> containerNames = new ArrayList<String>();
				containerNames.add("tomcat");// we consider only one container
				List<String> serviceNames = new ArrayList<String>();
				List<PaasNodeType> lstNodes = manifest.getPaasApplication()
						.getPaasConfigurationTemplate().getPaasNode();

				for (PaasNodeType node : lstNodes) {
					// If no container was specified in the Environment
					// Descriptor, Tomcat will be choosen
					if (node.getContentType().equals("container"))
						containerNames.add(node.getName());
					else if (node.getContentType().equals("database"))
						serviceNames.add(node.getName());
				}

				Map<String, String> staging = new HashMap<String, String>();

				for (String cName : containerNames) {
					if (cName.equals("tomcat")) {
						staging.put("runtime", "java");
						staging.put("framework",  CloudApplication.JAVA_WEB);
						staging.put("command", "no");
					}else if (cName.equals("spring")) {
						staging.put("runtime", "java");
						staging.put("framework",  CloudApplication.SPRING);
						staging.put("command", "no");
					}else if (cName.equals("node.js")) {
						staging.put("runtime", "node");
						staging.put("framework",  "node");
						staging.put("command", "no");
					}
					else {
						// TODO consider the other containers
						staging.put("runtime", "java");
						staging.put("framework", "java_web");
						staging.put("command", "no");
					}
				}

				// generate envID
				Long id = getNextId();

				Environment env = new Environment();
				env.setEnvId(Long.toString(id));
				env.setEnvName(envName);
				env.setEnvDesc(envDescription);
				env.setStaging(staging);
				env.setServiceNames(serviceNames);

				// add the delete and get environment links
				Map<String, String> linksList = new HashMap<String, String>();
				linksList = addGetEnvLink(linksList, Long.toString(id));
				linksList = addDeleteEnvLink(linksList, Long.toString(id));
				env.setLinksList(linksList);

				envPool.INSTANCE.add(env);

				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<Environment>(env) {
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
			error.setValue("Failed to create the environment: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response deleteEnvironment(String envid) {
		try {
			boolean result = envPool.INSTANCE.delete(envid);
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
			error.setValue("Failed to delete the environment: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response findEnvironments() {
		List<Environment> envList = envPool.INSTANCE.getEnvList();
		if (envList != null) {
			return Response.status(Response.Status.OK)
					.entity(new GenericEntity<List<Environment>>(envList) {
					}).type(MediaType.APPLICATION_XML_TYPE).build();
		} else {
			System.out.println("Failed to retrieve the environments list.");
			error.setValue("Failed to retrieve the environments list.");
			return Response.status(Response.Status.NO_CONTENT).entity(error)
					.build();
		}
	}

	@Override
	public Response startEnvironment(String envid) {
		throw new NotSupportedException(
				"The environment in Cloud Foundry is started along with the application.");
	}

	@Override
	public Response stopEnvironment(String envid) {
		throw new NotSupportedException(
				"The environment in Cloud Foundry is stoped along with the application.");
	}

	@Override
	public Response deployApplication(String envid, String appid,
			String versionid, String instanceid) {
		try {
			Environment env = envPool.INSTANCE.getEnv(envid);
			Application app = appPool.INSTANCE.getApp(appid);

			CloudFoundryClient client = null;
			List<String> servicesList = env.getServiceNames();

			try {
				client = new CloudFoundryClient(new CloudCredentials(
						TEST_USER_EMAIL, TEST_USER_PASS), new URL(ccUrl));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (client == null) {
				System.out.println("Failed to create Client");
			} else {
				client.login();
				String runtime = env.getStaging().get("runtime");
				String framework = env.getStaging().get("framework");
				Staging staging = new Staging(runtime, framework);

				List<CloudService> servicesLst = client.getServices();

				for (CloudService cs : servicesLst) {
					System.out.println(cs.getName());
				}
				// Services Creation if not already exits
				// TODO create the other supported services
				if (servicesList != null && servicesList.size() != 0) {
					for (String serviceName : servicesList) {
						if (serviceName.toLowerCase().contains("mysql")) {
							if (!serviceExists(serviceName, client))
								createMySqlService(serviceName, client);
						} else if (serviceName.toLowerCase().contains("redis")) {
							if (!serviceExists(serviceName, client))
								createRedisService(serviceName, client);
						}
					}
				}
				// create the application
				client.createApplication(app.getAppName(), staging,
						(int) app.getMemory(), app.getUris(),
						env.getServiceNames(), app.isCheckExists());
				
				//Sets the number of instances
				if(app.getNbInstances()>0)
					client.updateApplicationInstances(app.getAppName(), app.getNbInstances());

				// Services binding
				if (servicesLst != null && servicesLst.size() != 0)
					for (String serviceName : servicesList) {
						if (serviceName != null) {
							client.bindService(app.getAppName(), serviceName);
						}
					}

				// TODO upload Application we treated only the case of an
				// aretfact
				if (app.getDeployable().getDeployableType().equals("artifact")) {
					String artefactFileName = app.getDeployable()
							.getDeployableName();
					String artefactFilePath = app.getDeployable()
							.getDeployableDirectory().replace("\\", "/");
					if (!artefactFilePath.endsWith("/"))
						artefactFilePath = artefactFilePath + "/";
					File artefactFile = new File(artefactFilePath
							+ artefactFileName);
					ZipFile zipFile = new ZipFile(artefactFile);
					ApplicationArchive appArchive = new ZipApplicationArchive(
							zipFile);
					try{
					client.uploadApplication(app.getAppName(), appArchive);
					}catch(HttpServerErrorException e){
						// this is to avoid timeout exceptions that occurs during 
						//the upload of large applications
						//TODO add a method to see the status of the upload
						System.out.println("[DeployApplication]: Entering HttpServerErrorException....");
					}
				}else if (app.getDeployable().getDeployableType().equals("folder")){
					String folderPath = app.getDeployable()
							.getDeployableDirectory().replace("\\", "/");
					File folderFile = new File(folderPath);
					ApplicationArchive archive = new DirectoryApplicationArchive(folderFile);				
					//client.uploadApplication(app.getAppName(), archive);
					try{
					client.uploadApplication(app.getAppName(), app.getDeployable()
							.getDeployableDirectory().replace("\\", "/"));
					}catch(HttpServerErrorException e){
						// this is to avoid timeout exceptions that occurs during the 
						// upload of large applications
						//TODO add a method to see the status of the upload
						System.out.println("[DeployApplication]: Entering HttpServerErrorException....");
					}	
				}
				client.logout();
			}
			// update the Application with describeApp and deleteApp links
			Map<String, String> linksList = appPool.INSTANCE.getApp(appid)
					.getLinksList();
			linksList = addDescribeAppLink(linksList, appid);
			linksList = addDeleteAppLink(linksList, appid);
			appPool.INSTANCE.updateApp(appid, linksList);

			return Response.status(Response.Status.OK).entity(app)
					.type(MediaType.APPLICATION_XML_TYPE).build();
		} catch (Exception e) {
			System.out.println("Failed to deploy the environment: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to deploy the environment: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response undeployApplication(String envid, String appid,
			String versionid, String instanceid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getEnvironment(String envid) {
		try {
			Environment env = envPool.INSTANCE.getEnv(envid);
			if (env != null) {
				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<Environment>(env) {
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
			error.setValue("Failed to display the environment: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response getDeployedApplicationVersionInstance(String envid) {
		throw new NotSupportedException();
	}

	// private methods
	private synchronized Long getNextId() {
		return new Long(envPool.INSTANCE.getNextID());
	}

	private void createMySqlService(String serviceName,
			CloudFoundryClient client) {
		CloudService service = new CloudService(
				CloudEntity.Meta.defaultMeta(), serviceName);

		service.setType("database");
		service.setVersion("5.1");
		service.setProvider("core");
		service.setVendor("mysql");
		service.setTier("free");

		client.createService(service);
	}

	private void createRedisService(String serviceName,
			CloudFoundryClient client) {
		CloudService service = new CloudService(
				CloudEntity.Meta.defaultMeta(), serviceName);

		service.setType("key-value");
		service.setVersion("2.2");
		service.setProvider("core");
		service.setVendor("redis");
		service.setTier("free");

		client.createService(service);
	}

	private boolean serviceExists(String serviceName, CloudFoundryClient client) {
		try {
			client.getService(serviceName);
			return true;
		} catch (org.cloudfoundry.client.lib.CloudFoundryException e) {
			return false;
		}
	}

	private Map<String, String> addDescribeAppLink(
			Map<String, String> linksList, String appId) {
		String url = "http://localhost:8080/CF-api/rest/app/" + appId;
		linksList.put("[GET]describeApplication(appid)", url);
		return linksList;
	}

	private Map<String, String> addDeleteAppLink(Map<String, String> linksList,
			String appId) {
		String url = "http://localhost:8080/CF-api/rest/app/" + appId
				+ "/delete";
		linksList.put("[DELETE]deleteApplication(appid)", url);
		return linksList;
	}

	private Map<String, String> addDeleteEnvLink(Map<String, String> linksList,
			String envId) {
		String url = "http://localhost:8080/CF-api/rest/environment/" + envId;
		linksList.put("[DELETE]deleteEnvironment(envid)", url);
		return linksList;
	}

	private Map<String, String> addGetEnvLink(Map<String, String> linksList,
			String envId) {
		String url = "http://localhost:8080/CF-api/rest/environment/" + envId;
		linksList.put("[GET]getEnvironment(envid)", url);
		return linksList;
	}

}
