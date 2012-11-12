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
/**
 * 
 */
package telecom.sudparis.eu.paas.core.server.ressources.manager.application;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.cloudfoundry.client.lib.domain.CloudApplication.AppState;


import telecom.sudparis.eu.paas.api.ressources.manager.application.RestApplicationManager;
import telecom.sudparis.eu.paas.core.server.applications.pool.Application;
import telecom.sudparis.eu.paas.core.server.applications.pool.ApplicationPool;
import telecom.sudparis.eu.paas.core.server.applications.pool.Deployable;
import telecom.sudparis.eu.paas.core.server.applications.pool.VersionInstance;
import telecom.sudparis.eu.paas.core.server.ressources.exception.NotSupportedException;
import telecom.sudparis.eu.paas.core.server.xml.Error;
import telecom.sudparis.eu.paas.core.server.xml.OperationResponse;
import telecom.sudparis.eu.paas.core.server.xml.RessourcesXML;
import telecom.sudparis.eu.paas.core.server.xml.StagingXML;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasManifestType;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasVersionInstanceType;

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
	 * the paas application memory size In our Manifest we can not yet specify
	 * the memory
	 */
	private final long memory = 512;

	/**
	 * checks the existence of the application By default set to true
	 */
	private final boolean CHECK_EXISTS = true;

	/**
	 * The Application pool
	 */
	private ApplicationPool appPool;

	/**
	 * An element to represent The response
	 */
	private OperationResponse or = new OperationResponse();

	/**
	 * An element to display Errors
	 */
	private Error error = new Error();

	private static boolean checkCFApplications = false;

	@Override
	public Response createApplication(String cloudApplicationDescriptor) {
		try {
			copyDeployedApps2Pool();
			if (cloudApplicationDescriptor != null
					&& !cloudApplicationDescriptor.equals("")) {
				InputStream is = new ByteArrayInputStream(
						cloudApplicationDescriptor.getBytes());
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
				// retrieve appName
				String appName = manifest.getPaasApplication().getName();

				// TODO retrieve staging
				/*
				 * Map<String, String> staging = new HashMap<String, String>();
				 * staging.put("runtime", ""); staging.put("framework", "");
				 * staging.put("command", "");
				 */

				// retrieve uris
				List<String> uris = new ArrayList<String>();
				uris.add(appName + "." + HOST_NAME);

				// generate appID
				Long id = getNextId();

				// TODO retrieve serviceNames
				// List<String> serviceNames = new ArrayList<String>();
				// serviceNames.add("mysql");

				Application app = new Application();
				app.setAppName(appName);
				app.setUris(uris);
				app.setMemory(memory);
				app.setCheckExists(CHECK_EXISTS);
				app.setAppId(Long.toString(id));
				app.setStatus("STOPPED");

				appPool.INSTANCE.add(app);

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
			error.setValue("Failed to create the application: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response createApplicationVersion(String appId,
			String applicationVersionDescriptor) {
		try {
			copyDeployedApps2Pool();
			String appStatus = checkAppId(appId);
			if (appStatus.equals("OK")) {
				if (applicationVersionDescriptor != null) {
					InputStream is = new ByteArrayInputStream(
							applicationVersionDescriptor.getBytes());

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
					// retrieve deployableName
					String deployableName = manifest.getPaasApplication()
							.getPaasVersion().getPaasDeployable().getName();

					// retrieve deployableType
					String deployableType = manifest.getPaasApplication()
							.getPaasVersion().getPaasDeployable()
							.getContentType();

					// retrieve deployableDirectory
					String deployableDirectory = manifest.getPaasApplication()
							.getPaasVersion().getPaasDeployable().getLocation();

					Deployable dep = new Deployable();
					dep.setDeployableName(deployableName);
					dep.setDeployableType(deployableType);
					dep.setDeployableDirectory(deployableDirectory);

					// Update the corespending Application in the AppPool
					appPool.INSTANCE.updateApp(appId, dep);

					return Response.status(Response.Status.OK)
							.entity(appPool.INSTANCE.getApp(appId))
							.type(MediaType.APPLICATION_XML_TYPE).build();
				} else {
					System.out
							.println("Failed to retrieve the cloud Application Version Descriptor");
					error.setValue("Failed to retrieve the cloud Application  Version Descriptor.");
					return Response
							.status(Response.Status.INTERNAL_SERVER_ERROR)
							.entity(error).build();
				}
			} else {
				error.setValue(appStatus);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(error).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to create the application version: "
					+ e.getMessage());
			error.setValue("Failed to create the application version: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	// TODO add the links
	@Override
	public Response findApplications() {
		try {
			copyDeployedApps2Pool();
			CloudFoundryClient client = null;
			List<CloudApplication> appListCF = null;
			List<Application> appListPool = appPool.INSTANCE.getAppList();
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
						appPool.INSTANCE.updateApp(poolApp.getAppId(), poolApp);
					}
				}
			}
			if (appListPool != null && appListPool.size() > 0)
				return Response
						.status(Response.Status.OK)
						.entity(new GenericEntity<List<Application>>(
								appPool.INSTANCE.getAppList()) {
						}).type(MediaType.APPLICATION_XML_TYPE).build();
			else {
				or.setValue("No application available.");
				return Response.status(Response.Status.OK).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to display the applications: "
					+ e.getMessage());
			error.setValue("Failed to display the applications: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response findApplicationVersionInstances(String appid,
			String versionid) {
		throw new NotSupportedException();
	}

	@Override
	public Response createApplicationVersionInstance(String appId,
			String applicationVersionInstanceDescriptor) {
		try {
			copyDeployedApps2Pool();
			if (appPool.INSTANCE.getApp(appId) != null) {

				if (applicationVersionInstanceDescriptor != null) {
					InputStream is = new ByteArrayInputStream(
							applicationVersionInstanceDescriptor.getBytes());

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
					// retrieve ApplicationVersionInstance Names and number
					List<PaasVersionInstanceType> listOfInstances = manifest.getPaasApplication()
							.getPaasVersion().getPaasVersionInstance();
					List<VersionInstance> listVI=new ArrayList<VersionInstance>();
					int nbInstances=0;
					
					if(listOfInstances!=null && listOfInstances.size()>0)
						for (PaasVersionInstanceType p:listOfInstances){
							if(p!=null){
								//Ajout à la liste des Noms
								VersionInstance vi = new VersionInstance();
								vi.setInstanceName(p.getName());
								listVI.add(vi);
								nbInstances++;
							}
						}
					
					/*
					 * checks if the deployable element is defined in
					 * Application appId. If not added from the provided
					 * applicationVersionInstanceDescriptor
					 */

					if (appPool.INSTANCE.getApp(appId).getDeployable() == null) {
						// retrieve deployableName
						String deployableName = manifest.getPaasApplication()
								.getPaasVersion().getPaasDeployable().getName();

						// retrieve deployableType
						String deployableType = manifest.getPaasApplication()
								.getPaasVersion().getPaasDeployable()
								.getContentType();

						// retrieve deployableDirectory
						String deployableDirectory = manifest
								.getPaasApplication().getPaasVersion()
								.getPaasDeployable().getLocation();

						Deployable dep = new Deployable();
						dep.setDeployableName(deployableName);
						dep.setDeployableType(deployableType);
						dep.setDeployableDirectory(deployableDirectory);
						// Update the corespending Application with the
						// Deeployable
						// element in the AppPool
						appPool.INSTANCE.updateApp(appId, dep);
					}

					// Update the corespending Application with the
					// VersionInstance list
					// element in the AppPool
					appPool.INSTANCE.updateApp(appId, listVI);
					
					// Update the corespending Application with the
					// nbInstances
					// element in the AppPool
					appPool.INSTANCE.updateApp(appId, nbInstances);

					// Update the corespending Application with the
					// AdequateLinkList
					// in the AppPool
					Map<String, String> linksList = new HashMap<String, String>();
					linksList = addFindAppVersionsLink(linksList, appId);
					appPool.INSTANCE.updateApp(appId, linksList);

					return Response.status(Response.Status.OK)
							.entity(appPool.INSTANCE.getApp(appId))
							.type(MediaType.APPLICATION_XML_TYPE).build();
				} else {
					System.out
							.println("Failed to retrieve the cloud Application Version Descriptor");
					error.setValue("Failed to retrieve the cloud Application  Version Descriptor.");
					return Response
							.status(Response.Status.INTERNAL_SERVER_ERROR)
							.entity(error).build();
				}
			} else {// The specified appID doesnt exist
				System.out.println("No Application with ID " + appId
						+ " was found!");
				error.setValue("No Application with ID " + appId
						+ " was found! Start by creating the application");
				return Response.status(Response.Status.PRECONDITION_FAILED)
						.entity(error).build();
			}
		} catch (Exception e) {
			System.out
					.println("Failed to create the application version instance: "
							+ e.getMessage());
			error.setValue("Failed to create the application version instance: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response startApplicationVersionInstance(String appid,
			String versionid, String instanceid) {
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
				Application app = appPool.INSTANCE.getApp(appid);
				String appName = app.getAppName();
				if (client.getApplication(appName).getState() == AppState.STOPPED)
					client.startApplication(appName);
				client.logout();
			}
			return describeApplication(appid);
		} catch (Exception e) {
			System.out.println("Failed to satrt the application: "
					+ e.getMessage());
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
			Application app = appPool.INSTANCE.getApp(appid);
			List<CloudApplication> appListCF = null;
			CloudApplication appCF = null;
			CloudApplication cApp = null;

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
				appPool.INSTANCE.updateApp(app.getAppId(), app);
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
				app = appPool.INSTANCE.getApp(appid);

			}
			if (app != null) {
				client.login();
				String appName = app.getAppName();
				CloudApplication cApp = findAssociatedAppInCF(appName, client);
				if (cApp != null)
					client.deleteApplication(appName);
				appPool.INSTANCE.remove(app);
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
				appPool.INSTANCE.removeAll();
				client.logout();
			}

			or.setValue("All applications were succefully deleted");
			return Response.status(Response.Status.OK).entity(or)
					.type(MediaType.APPLICATION_XML_TYPE).build();
		} catch (Exception e) {
			System.out.println("Failed to delete all the applications: "
					+ e.getMessage());
			error.setValue("Failed to delete all the applications: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response stopApplicationVersionInstance(String appid,
			String versionid, String instanceid) {
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
				Application app = appPool.INSTANCE.getApp(appid);
				String appName = app.getAppName();
				if (client.getApplication(appName).getState() == AppState.STARTED)
					client.stopApplication(appName);
				client.logout();
			}
			return describeApplication(appid);
		} catch (Exception e) {
			System.out.println("Failed to stop the application: "
					+ e.getMessage());
			error.setValue("Failed to stop the application: " + e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response findApplicationVersions(String appid) {
		try {
			copyDeployedApps2Pool();
			Application app = appPool.INSTANCE.getApp(appid);

			if (app != null) {
				List<VersionInstance> vi = app.getVersionInstances();
				
				return Response.status(Response.Status.OK).entity(new GenericEntity<List<VersionInstance>>(
						vi) {
				}).type(MediaType.APPLICATION_XML_TYPE).build();
			} else {
				System.out.println("Application " + appid + " was not found!");
				error.setValue("Application " + appid + " was not found!");
				return Response.status(Response.Status.NO_CONTENT)
						.entity(error).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to find the application versions: "
					+ e.getMessage());
			error.setValue("Failed to find the application versions: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	// private methods

	private synchronized Long getNextId() {
		return new Long(appPool.INSTANCE.getNextID());
	}

	private Map<String, String> addFindAppVersionsLink(
			Map<String, String> linksList, String appId) {
		String url = "http://localhost:8080/CF-api/rest/app/" + appId
				+ "/version";
		linksList.put("[GET]findApplicationVersions(appid)", url);
		return linksList;
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

	private Application findAssociatedAppInThePool(String appName) {
		List<Application> appListPool = appPool.INSTANCE.getAppList();
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
			Application app = appPool.INSTANCE.getApp(appId);
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

			List<Application> appListPool = appPool.INSTANCE.getAppList();
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
				Map<String, String> linksList = new HashMap<String, String>();
				linksList = addFindAppVersionsLink(linksList, id);
				linksList = addDescribeAppLink(linksList, id);
				linksList = addDeleteAppLink(linksList, id);
				app.setLinksList(linksList);
				appPool.INSTANCE.add(app);
			}
		}
	}
}
