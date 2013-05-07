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
package telecom.sudparis.eu.paas.core.server.ressources.manager.application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import org.cloudfoundry.client.lib.domain.CloudApplication.AppState;
import org.cloudfoundry.client.lib.domain.CloudEntity;
import org.cloudfoundry.client.lib.domain.CloudService;
import org.cloudfoundry.client.lib.domain.Staging;
import org.springframework.web.client.HttpServerErrorException;

import telecom.sudparis.eu.paas.api.ressources.manager.application.RestApplicationManager;
import telecom.sudparis.eu.paas.core.server.applications.pool.Application;
import telecom.sudparis.eu.paas.core.server.applications.pool.Application.LinksList;
import telecom.sudparis.eu.paas.core.server.applications.pool.Application.LinksList.Link;
import telecom.sudparis.eu.paas.core.server.applications.pool.ApplicationPool;
import telecom.sudparis.eu.paas.core.server.applications.pool.Deployable;
import telecom.sudparis.eu.paas.core.server.applications.pool.VersionInstance;
import telecom.sudparis.eu.paas.core.server.environments.pool.Environment;
import telecom.sudparis.eu.paas.core.server.environments.pool.EnvironmentPool;
import telecom.sudparis.eu.paas.core.server.ressources.exception.NotSupportedException;
import telecom.sudparis.eu.paas.core.server.xml.Error;
import telecom.sudparis.eu.paas.core.server.xml.OperationResponse;
import telecom.sudparis.eu.paas.core.server.xml.RessourcesXML;
import telecom.sudparis.eu.paas.core.server.xml.StagingXML;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasApplicationManifestType;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasApplicationVersionInstanceType;

/**
 * REST resource of type ApplicationManager
 * 
 * @author Mohamed Sellami (Telecom SudParis)
 * 
 */
@Path("app")
public class ApplicationManagerRessource implements RestApplicationManager {

	/**
	 * ressource bundle to get the connexion credentials
	 */
	private static ResourceBundle rb = ResourceBundle.getBundle("telecom.sudparis.eu.paas.core.server.ressources.credentials");
	
	/**
	 * The target cloud Foundry URL
	 */
	private static final String ccUrl=rb.getString("vcap.target");
	
	/**
	 * The public CF-PaaS API URL
	 */
	private static String apiUrl = rb.getString("api.public.url");
	
	/**
	 * The CloudFoundry user mail 
	 */
	private static final String TEST_USER_EMAIL =rb.getString("vcap.email");
	
	/**
	 * The CloudFoundry user password
	 */
	private static final String TEST_USER_PASS =rb.getString("vcap.passwd");
	
	/**
	 * The HOST_NAME of the used paas
	 */
	private static final String HOST_NAME =rb.getString("host");

	/**
	 * checks the existence of the application By default set to true
	 */
	private static final boolean CHECK_EXISTS = true;

	/**
	 * An element to represent The response
	 */
	private OperationResponse or = new OperationResponse();

	/**
	 * An element to display Errors
	 */
	private Error error = new Error();

	private static boolean checkCFApplications = false;
	
	/**
	 * The temp folder used to store uploaded files
	 */
	private static String localTempPath = System.getProperty("java.io.tmpdir");

	@Override
	public Response createApplication(String cloudApplicationDescriptor) {
		try {
			copyDeployedApps2Pool();
			if (cloudApplicationDescriptor != null
					&& !cloudApplicationDescriptor.equals("")) {
				InputStream is = new ByteArrayInputStream(
						cloudApplicationDescriptor.getBytes());
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
				// create the application object
				Application app = new Application();
				// retrieve appName
				String appName = manifest.getPaasApplication().getName();
				app.setAppName(appName);
				

				// retrieve uris
				List<String> uris = new ArrayList<String>();
				uris.add(appName + "." + HOST_NAME);
				app.setUris(uris);

				// generate appID
				Long id = getNextId();
				app.setAppId(Long.toString(id));

				// retrieve deployableName
				String deployableName = manifest.getPaasApplication().getPaasApplicationVersion().getPaasApplicationDeployable()
						.getName();

				// retrieve deployableType
				String deployableType = manifest.getPaasApplication()
						.getPaasApplicationVersion().getPaasApplicationDeployable()
						.getContentType();

				// retrieve deployableDirectory
				String deployableDirectory = manifest.getPaasApplication()
						.getPaasApplicationVersion().getPaasApplicationDeployable().getLocation();
				//Define the deployable element
				Deployable dep = new Deployable();
				dep.setDeployableName(deployableName);
				dep.setDeployableType(deployableType);
				dep.setDeployableDirectory(deployableDirectory);
				app.setDeployable(dep);
				
				// retrieve ApplicationVersionInstance Names and number
				List<PaasApplicationVersionInstanceType> listOfInstances = manifest.getPaasApplication()
						.getPaasApplicationVersion().getPaasApplicationVersionInstance();
				List<VersionInstance> listVI=new ArrayList<VersionInstance>();
				int nbInstances=0;
				
				if(listOfInstances!=null && listOfInstances.size()>0)
					for (PaasApplicationVersionInstanceType p:listOfInstances){
						if(p!=null){
							VersionInstance vi = new VersionInstance();
							vi.setInstanceName(p.getName());
							listVI.add(vi);
							nbInstances++;
						}
					}
				
				// Update the Application object with the
				// Adequate LinkList
				LinksList linksList = new LinksList();
				linksList = addDescribeAppLink(linksList, id.toString());
				app.setLinksList(linksList);
				
				app.setVersionInstances(listVI);
				app.setNbInstances(nbInstances);
				app.setCheckExists(CHECK_EXISTS);
				app.setStatus("STOPPED");	
				
				ApplicationPool.INSTANCE.add(app);

				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<Application>(app) {
						}).type(MediaType.APPLICATION_XML_TYPE).build();
			} else {
				System.out
						.println("Failed to retrieve the cloud Application Descriptor");
				error.setValue("Failed to retrieve the cloud Application Descriptor.");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(error).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to create the application: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to create the application: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}


	@Override
	public Response findApplications() {
		try {
			copyDeployedApps2Pool();
			CloudFoundryClient client = null;
			List<CloudApplication> appListCF = null;
			List<Application> appListPool = ApplicationPool.INSTANCE.getAppList();
			try {
				client = new CloudFoundryClient(new CloudCredentials(
						TEST_USER_EMAIL, TEST_USER_PASS), new URL(ccUrl));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			if (client == null) {
				System.out.println("Failed to create Client");
			} else {
				client.login();
				appListCF = client.getApplications();
				client.logout();
			}

			if (appListCF != null && appListPool != null) {
				for (CloudApplication cfApp : appListCF) {
					Application poolApp = findAssociatedAppInThePool(cfApp
							.getName());
					/// Update the status of the associated application in
					// the pool
					if (poolApp != null) {
						poolApp.setStatus(cfApp.getState().toString());
						poolApp.setStaging(new StagingXML(cfApp.getStaging()));
						poolApp.setServices(cfApp.getServices());
						poolApp.setResources(new RessourcesXML(cfApp
								.getResources()));
						ApplicationPool.INSTANCE.updateApp(poolApp.getAppId(), poolApp);
					}
				}
			}
			if (appListPool != null && appListPool.size() > 0)
				return Response
						.status(Response.Status.OK)
						.entity(new GenericEntity<List<Application>>(
								ApplicationPool.INSTANCE.getAppList()) {
						}).type(MediaType.APPLICATION_XML_TYPE).build();
			else {
				or.setValue("No application available.");
				return Response.status(Response.Status.OK).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to display the applications: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to display the applications: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	
	@Override
	public Response startApplication(String appid) {
		try {
			copyDeployedApps2Pool();
			CloudFoundryClient client = null;

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
				Application app = ApplicationPool.INSTANCE.getApp(appid);
				String appName = app.getAppName();
				try{
				if (client.getApplication(appName).getState() == AppState.STOPPED)
					client.startApplication(appName);
				}catch(HttpServerErrorException e){
					// this error is to avoid timeout exceptions that occurs during the upload of large applications
					//TODO add a method to see the status of the upload
					System.out.println("(Start Application):Entering HttpServerErrorException....");
				}
				client.logout();
			}
			return describeApplication(appid);
		} catch (Exception e) {
			System.out.println("Failed to satrt the application: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to start the application: " + e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response describeApplication(String appid) {
		try {
			copyDeployedApps2Pool();
			CloudFoundryClient client = null;
			Application app = ApplicationPool.INSTANCE.getApp(appid);
			CloudApplication appCF = null;

			try {
				client = new CloudFoundryClient(new CloudCredentials(
						TEST_USER_EMAIL, TEST_USER_PASS), new URL(ccUrl));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			if (client == null) {
				System.out.println("Failed to create Client");
			} else {
				client.login();
				if (app != null)
					appCF = findAssociatedAppInCF(app.getAppName(), client);
				client.logout();
			}

			if (appCF != null && app != null) {
				// recuperer les donnees (status,
				// staging,...) de la CFApplication
				// et les inserer dans
				// lapoolApplication
				app.setStatus(appCF.getState().toString());
				app.setStaging(new StagingXML(appCF.getStaging()));
				app.setServices(appCF.getServices());
				app.setResources(new RessourcesXML(appCF.getResources()));
				ApplicationPool.INSTANCE.updateApp(app.getAppId(), app);
				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<Application>(app) {
						}).type(MediaType.APPLICATION_XML_TYPE).build();
			} else if (appCF == null && app != null) {
				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<Application>(app) {
						}).type(MediaType.APPLICATION_XML_TYPE).build();
			} else {
				System.out.println("No Application with ID " + appid
						+ " was found!");
				error.setValue("No Application with ID " + appid
						+ " was found! Start by creating the application");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(error).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to display the application: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to display the application: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response deleteApplication(String appid) {
		try {
			copyDeployedApps2Pool();
			CloudFoundryClient client = null;
			Application app = null;
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
				app = ApplicationPool.INSTANCE.getApp(appid);

			}
			if (app != null) {
				client.login();
				String appName = app.getAppName();
				CloudApplication cApp = findAssociatedAppInCF(appName, client);
				if (cApp != null)
					client.deleteApplication(appName);
				ApplicationPool.INSTANCE.remove(app);
				client.logout();
				or.setValue("The application with ID " + appid
						+ " was succefully deleted");
				return Response.status(Response.Status.OK).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			} else {
				System.out.println("No Application with ID " + appid
						+ " was found!");
				error.setValue("No Application with ID " + appid
						+ " was found! Start by creating the application");
				return Response.status(Response.Status.ACCEPTED).entity(error)
						.build();
			}
		} catch (Exception e) {
			System.out.println("Failed to delete the application: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to delete the application: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response deleteApplications() {
		try {
			copyDeployedApps2Pool();
			CloudFoundryClient client = null;

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
				client.deleteAllApplications();
				//appPool.INSTANCE.removeAll();
				ApplicationPool.INSTANCE.removeAll();
				client.logout();
			}

			or.setValue("All applications were succefully deleted");
			return Response.status(Response.Status.OK).entity(or)
					.type(MediaType.APPLICATION_XML_TYPE).build();
		} catch (Exception e) {
			System.out.println("Failed to delete all the applications: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to delete all the applications: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response stopApplication(String appid) {
		try {
			CloudFoundryClient client = null;

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
				Application app = ApplicationPool.INSTANCE.getApp(appid);
				String appName = app.getAppName();
				if (client.getApplication(appName).getState() == AppState.STARTED)
					client.stopApplication(appName);
				client.logout();
			}
			return describeApplication(appid);
		} catch (Exception e) {
			System.out.println("Failed to stop the application: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to stop the application: " + e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}
	
	@Override
	public Response updateApplication(String appid,
			String cloudApplicationDescriptor) {
		throw new NotSupportedException("The update Application is not yet implemented.");
	}

	@Override
	public Response restartApplication(String appid) {
		try {
			CloudFoundryClient client = null;

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
				Application app = ApplicationPool.INSTANCE.getApp(appid);
				String appName = app.getAppName();
				client.restartApplication(appName);
				client.logout();
			}
			return describeApplication(appid);
		} catch (Exception e) {
			System.out.println("Failed to restart the application: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to restart the application: " + e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response deployApplication(String appid, String envid,InputStream uploadedInputStream) {
		try {
			Environment env = EnvironmentPool.INSTANCE.getEnv(envid);
			Application app = ApplicationPool.INSTANCE.getApp(appid);

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
				
				//We start by uploading the application files
				String uploadedFileLocation = localTempPath + "/"+app.getDeployable().getDeployableName();

				writeToFile(uploadedInputStream, uploadedFileLocation);
				
				//redefine the deployable element according to the uploaded file location
				Deployable newDep=new Deployable();
				newDep.setDeployableDirectory(localTempPath);
				newDep.setDeployableName(app.getDeployable().getDeployableName());
				newDep.setDeployableType(app.getDeployable().getDeployableType());
				
				//update the application pool
				ApplicationPool.INSTANCE.updateApp(appid, newDep);			
				
				String runtime = env.getStaging().map.get("runtime");
				System.out.println("env.getStaging().get(runtime) = "
						+ env.getStaging().map.get("runtime"));
				String framework = env.getStaging().map.get("framework");
				Staging staging = new Staging(runtime, framework);
				List<CloudService> servicesLst = client.getServices();

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
				//get the memory attribute from the environment
				app.setMemory(env.getEnvMemory());
				// create the application
				client.createApplication(app.getAppName(), staging,
						(int) app.getMemory(), app.getUris(),
						env.getServiceNames(), app.isCheckExists());

				// Sets the number of instances
				if (app.getNbInstances() > 0)
					client.updateApplicationInstances(app.getAppName(),
							app.getNbInstances());

				// Services binding
				if (servicesLst != null && servicesLst.size() != 0)
					for (String serviceName : servicesList) {
						if (serviceName != null) {
							client.bindService(app.getAppName(), serviceName);
						}
					}

				// TODO upload Application we treated only the case of an
				// artifact
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
					try {
						client.uploadApplication(app.getAppName(), appArchive);
					} catch (HttpServerErrorException e) {
						// this is to avoid timeout exceptions that occurs
						// during
						// the upload of large applications
						// TODO add a method to see the status of the upload
						System.out
						.println("[DeployApplication]: Entering HttpServerErrorException....");
					}
				} else if (app.getDeployable().getDeployableType()
						.equals("folder")) {
					//TODO
					String folderPath = app.getDeployable()
							.getDeployableDirectory().replace("\\", "/");
					File folderFile = new File(folderPath);
					ApplicationArchive archive = new DirectoryApplicationArchive(
							folderFile);
					// client.uploadApplication(app.getAppName(), archive);
					try {
						client.uploadApplication(app.getAppName(), app
								.getDeployable().getDeployableDirectory()
								.replace("\\", "/"));
					} catch (HttpServerErrorException e) {
						// this is to avoid timeout exceptions that occurs
						// during the
						// upload of large applications
						// TODO add a method to see the status of the upload
						System.out
						.println("[DeployApplication]: Entering HttpServerErrorException....");
					}
				}
				client.logout();
				//delete the temp uploaded deployable
				deletefile(uploadedFileLocation);
			}
			// update the Application with describeApp and deleteApp links
			LinksList linksList = ApplicationPool.INSTANCE.getApp(appid)
					.getLinksList();
			linksList = addDeleteAppLink(linksList, appid);
			ApplicationPool.INSTANCE.updateApp(appid, linksList);
			
			

			return Response.status(Response.Status.OK).entity(app)
					.type(MediaType.APPLICATION_XML_TYPE).build();
		} catch (Exception e) {
			System.out.println("Failed to deploy the application: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to deploy the application: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}


	@Override
	public Response undeployApplication(String envid, String appid) {
		throw new NotSupportedException();
	}

	
	// private methods

	private synchronized Long getNextId() {
		return new Long(ApplicationPool.INSTANCE.getNextID());
	}


	private LinksList addDescribeAppLink(
			LinksList linksList, String appId) {
		String url = formatApiURL(apiUrl)+"app/" + appId;
		Link describeAppLink=new Link();
		describeAppLink.setAction("GET");
		describeAppLink.setLabel("describeApplication()");
		describeAppLink.setHref(url);
		linksList.getLink().add(describeAppLink);
		return linksList;
	}

	private LinksList addDeleteAppLink(LinksList linksList,
			String appId) {
		String url = formatApiURL(apiUrl)+"app/" + appId
				+ "/delete";
		Link deleteAppLink=new Link();
		deleteAppLink.setAction("DELETE");
		deleteAppLink.setLabel("deleteApplication()");
		deleteAppLink.setHref(url);
		linksList.getLink().add(deleteAppLink);
		return linksList;
	}

	private Application findAssociatedAppInThePool(String appName) {
		List<Application> appListPool = ApplicationPool.INSTANCE.getAppList();
		for (Application poolApp : appListPool) {
			if (poolApp.getAppName().equals(appName))
				return poolApp;
		}
		return null;
	}

	private CloudApplication findAssociatedAppInCF(String appName,
			CloudFoundryClient client) {
		List<CloudApplication> appListCF = client.getApplications();
		if (appListCF != null)
			for (CloudApplication cfApp : appListCF) {
				if (cfApp.getName().equals(appName))
					return cfApp;
			}
		return null;
	}

	private String checkAppId(String appId) {
		if (appId.equals("{appId}"))
			return "The application ID was not specified in the URL";
		else {
			Application app = ApplicationPool.INSTANCE.getApp(appId);
			if (app == null)
				return "No application found with the specified ID :(" + appId
						+ ")";
			else
				return "OK";
		}

	}

	private void copyDeployedApps2Pool() {
		if (!checkCFApplications) {
			checkCFApplications = true;
			CloudFoundryClient client = null;
			List<CloudApplication> appListCF = null;
			try {
				client = new CloudFoundryClient(new CloudCredentials(
						TEST_USER_EMAIL, TEST_USER_PASS), new URL(ccUrl));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			if (client == null) {
				System.out.println("Failed to create Client");
			} else {
				client.login();
				appListCF = client.getApplications();
				client.logout();
			}

			//List<Application> appListPool = appPool.INSTANCE.getAppList();
			for (CloudApplication ca : appListCF) {
				String id = Long.toString(getNextId());
				Application app = new Application();
				app.setAppId(id);
				app.setAppName(ca.getName());
				app.setCheckExists(CHECK_EXISTS);
				app.setMemory(ca.getMemory());
				app.setResources(new RessourcesXML(ca.getResources()));
				app.setServices(ca.getServices());
				app.setStaging(new StagingXML(ca.getStaging()));
				app.setStatus(ca.getState().toString());
				app.setUris(ca.getUris());
				app.setNbInstances(ca.getInstances());
				LinksList linksList = new LinksList();
				linksList = addDescribeAppLink(linksList, id);
				linksList = addDeleteAppLink(linksList, id);
				app.setLinksList(linksList);
				ApplicationPool.INSTANCE.add(app);
				//appPool.INSTANCE.add(app);
			}
		}
	}

	private boolean serviceExists(String serviceName, CloudFoundryClient client) {
		try {
			client.getService(serviceName);
			return true;
		} catch (org.cloudfoundry.client.lib.CloudFoundryException e) {
			return false;
		}
	}
	
	private void createMySqlService(String serviceName,
			CloudFoundryClient client) {
		CloudService service = new CloudService(CloudEntity.Meta.defaultMeta(),
				serviceName);

		service.setType("database");
		service.setVersion("5.1");
		service.setProvider("core");
		service.setVendor("mysql");
		service.setTier("free");

		client.createService(service);
	}

	private void createRedisService(String serviceName,
			CloudFoundryClient client) {
		CloudService service = new CloudService(CloudEntity.Meta.defaultMeta(),
				serviceName);

		service.setType("key-value");
		service.setVersion("2.2");
		service.setProvider("core");
		service.setVendor("redis");
		service.setTier("free");

		client.createService(service);
	}
	
	private String formatApiURL(String apiUrl){
		apiUrl=apiUrl.trim();
		if (!apiUrl.endsWith("/"))
			apiUrl=apiUrl+"/";
		return apiUrl;
	}
	
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/****************************************************/
	private static boolean removeDirectory(File directory) {
		if (directory == null)
			return false;
		if (!directory.exists())
			return true;
		if (!directory.isDirectory())
			return false;

		String[] list = directory.list();

		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				File entry = new File(directory, list[i]);
				if (entry.isDirectory()) {
					if (!removeDirectory(entry))
						return false;
				} else {
					if (!entry.delete())
						return false;
				}
			}
		}

		return directory.delete();
	}
	
	private static boolean deletefile(String uploadedFileLocation) {
		try{
    		File file = new File(uploadedFileLocation);
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    			return true;
    		}else{
    			System.out.println("Delete operation is failed.");
    			return false;
    		}
 
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
		
	}


}
