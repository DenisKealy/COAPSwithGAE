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
import java.io.FileInputStream;
import java.io.InputStream;
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
import telecom.sudparis.eu.paas.core.server.artifact.handling.AppObj;
import telecom.sudparis.eu.paas.core.server.artifact.handling.AppProcessing;
import telecom.sudparis.eu.paas.core.server.pool.application.ApplicationPool;
import telecom.sudparis.eu.paas.core.server.pool.environment.EnvironmentPool;
import telecom.sudparis.eu.paas.core.server.ressources.exception.NotSupportedException;
import telecom.sudparis.eu.paas.core.server.ressources.util.ApplicationLinkGenerator;
import telecom.sudparis.eu.paas.core.server.ressources.util.EnvironmentLinkGenerator;
import telecom.sudparis.eu.paas.core.server.ressources.util.FileUtilities;
import telecom.sudparis.eu.paas.core.server.xml.Error;
import telecom.sudparis.eu.paas.core.server.xml.LinksListType;
import telecom.sudparis.eu.paas.core.server.xml.OperationResponse;
import telecom.sudparis.eu.paas.core.server.xml.application.ApplicationType;
import telecom.sudparis.eu.paas.core.server.xml.application.DeployableType;
import telecom.sudparis.eu.paas.core.server.xml.application.InstanceType;
import telecom.sudparis.eu.paas.core.server.xml.application.InstancesType;
import telecom.sudparis.eu.paas.core.server.xml.application.UrisType;
import telecom.sudparis.eu.paas.core.server.xml.application.list.ApplicationsType;
import telecom.sudparis.eu.paas.core.server.xml.application.list.SimpleApplicationType;
import telecom.sudparis.eu.paas.core.server.xml.environment.ConfigurationType;
import telecom.sudparis.eu.paas.core.server.xml.environment.EntryType;
import telecom.sudparis.eu.paas.core.server.xml.environment.EnvironmentType;
import telecom.sudparis.eu.paas.core.server.xml.environment.list.EnvironmentsType;
import telecom.sudparis.eu.paas.core.server.xml.environment.list.SimpleEnvironmentType;
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
	private static ResourceBundle rb = ResourceBundle
			.getBundle("telecom.sudparis.eu.paas.core.server.ressources.credentials");

	/**
	 * The target cloud Foundry URL
	 */
	private static final String ccUrl = rb.getString("vcap.target");

	/**
	 * The public CF-PaaS API URL
	 */
	private static String apiUrl = rb.getString("api.public.url");

	/**
	 * The CloudFoundry user mail
	 */
	private static final String TEST_USER_EMAIL = rb.getString("vcap.email");

	/**
	 * The CloudFoundry user password
	 */
	private static final String TEST_USER_PASS = rb.getString("vcap.passwd");

	/**
	 * The HOST_NAME of the used paas
	 */
	private static final String HOST_NAME = rb.getString("host");

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
				ApplicationType app = new ApplicationType();
				// retrieve appName
				String appName = manifest.getPaasApplication().getName();
				app.setAppName(appName);

				//retrieve app description
				
				String description=manifest.getPaasApplication().getDescription().toString();
				app.setDescription(description);
				
				// retrieve uris
				// List<String> uris = new ArrayList<String>();
				// uris.add(appName + "." + HOST_NAME);
				UrisType uris = new UrisType();
				uris.getUri().add(appName + "." + HOST_NAME);
				app.setUris(uris);

				// generate appID
				Long id = getNextId();
				app.setAppId((int) (long) id);

				// retrieve deployableName
				String deployableName = manifest.getPaasApplication()
						.getPaasApplicationVersion()
						.getPaasApplicationDeployable().getName();

				// retrieve deployableType
				String deployableType = manifest.getPaasApplication()
						.getPaasApplicationVersion()
						.getPaasApplicationDeployable().getContentType();

				// retrieve deployableDirectory or deployableId
				String deployableId = null;
				String deployableDirectory = manifest.getPaasApplication()
						.getPaasApplicationVersion()
						.getPaasApplicationDeployable().getLocation();
				// if the deployable is already in the DB we provide it's ID
				DeployableType dep = new DeployableType();
				if (!(deployableDirectory.contains("/") || deployableDirectory
						.contains("\\"))) {
					dep.setDeployableId(deployableDirectory);
				}
				// Define the deployable element
				else {

					dep.setDeployableDirectory(deployableDirectory);

				}
				dep.setDeployableName(deployableName);
				dep.setDeployableType(deployableType);
				app.setDeployable(dep);
				// retrieve ApplicationVersionInstance Names and number
				List<PaasApplicationVersionInstanceType> listOfInstances = manifest
						.getPaasApplication().getPaasApplicationVersion()
						.getPaasApplicationVersionInstance();
				InstancesType listVI = new InstancesType();
				int nbInstances = 0;

				if (listOfInstances != null && listOfInstances.size() > 0)
					for (PaasApplicationVersionInstanceType p : listOfInstances) {
						if (p != null) {
							InstanceType vi = new InstanceType();
							vi.setInstanceName(p.getName());
							listVI.getInstance().add(vi);
							nbInstances++;
						}
					}

				// Update the Application object with the
				// Adequate LinkList
				LinksListType linksList = new LinksListType();
				linksList = ApplicationLinkGenerator.addDescribeAppLink(
						linksList, id.toString(), apiUrl);
				linksList = ApplicationLinkGenerator.addCreateAppLink(
						linksList, apiUrl);
				linksList = ApplicationLinkGenerator.addDestroyAppLink(
						linksList, id.toString(), apiUrl);
				linksList = ApplicationLinkGenerator.addFindAppsLink(linksList,
						apiUrl);
				linksList = ApplicationLinkGenerator.addUpdateAppLink(
						linksList, id.toString(), apiUrl);
				app.setLinksList(linksList);
				app.setInstances(listVI);
				app.setNbInstances(nbInstances);
				//app.setCheckExists(CHECK_EXISTS);
				app.setStatus("CREATED");

				ApplicationPool.INSTANCE.add(app);

				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<ApplicationType>(app) {
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
			List<ApplicationType> appListPool = ApplicationPool.INSTANCE
					.getAppList();
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
					ApplicationType poolApp = findAssociatedAppInThePool(cfApp
							.getName());
					// / Update the status of the associated application in
					// the pool
					if (poolApp != null) {
						poolApp.setStatus(cfApp.getState().toString());

						ApplicationPool.INSTANCE.updateApp(poolApp.getAppId()
								.toString(), poolApp);
					}
				}
			}
			
			
			
			if (appListPool != null && appListPool.size() > 0){
				
				List<SimpleApplicationType> appList = formatAppList(ApplicationPool.INSTANCE.getAppList());
				ApplicationsType apps=new ApplicationsType();
				apps.setApplication(appList);
				
				return Response
						.status(Response.Status.OK)
						.entity(new GenericEntity<ApplicationsType>(
								apps) {
						}).type(MediaType.APPLICATION_XML_TYPE).build();
			}else {
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
				ApplicationType app = ApplicationPool.INSTANCE.getApp(appid);
				String appName = app.getAppName();
				try {
					if (client.getApplication(appName).getState() == AppState.STOPPED)
						client.startApplication(appName);
				} catch (HttpServerErrorException e) {
					// this error is to avoid timeout exceptions that occurs
					// during the upload of large applications
					// TODO add a method to see the status of the upload
					System.out
							.println("(Start Application):Entering HttpServerErrorException....");
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
			ApplicationType app = ApplicationPool.INSTANCE.getApp(appid);
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
				ApplicationPool.INSTANCE.updateApp(app.getAppId().toString(),
						app);
				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<ApplicationType>(app) {
						}).type(MediaType.APPLICATION_XML_TYPE).build();
			} else if (appCF == null && app != null) {
				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<ApplicationType>(app) {
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
			ApplicationType app = null;
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
				// appPool.INSTANCE.removeAll();
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
				ApplicationType app = ApplicationPool.INSTANCE.getApp(appid);
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
		throw new NotSupportedException(
				"The update Application is not yet implemented.");
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
				ApplicationType app = ApplicationPool.INSTANCE.getApp(appid);
				String appName = app.getAppName();
				client.restartApplication(appName);
				client.logout();
			}
			return describeApplication(appid);
		} catch (Exception e) {
			System.out.println("Failed to restart the application: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to restart the application: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response deployApplication(String appid, String envid,
			InputStream uploadedInputStream) {
		try {
			System.out.println("HERE envi et app id:" + envid + "  " + appid);
			telecom.sudparis.eu.paas.core.server.xml.environment.EnvironmentType env = EnvironmentPool.INSTANCE
					.getEnv(envid);
			System.out.println("HERE env :" + env);
			ApplicationType app = ApplicationPool.INSTANCE.getApp(appid);

			CloudFoundryClient client = null;
			List<String> servicesList = getEnvironmentServiceList(env);
			// List<String> servicesList = env.getServiceNames();

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

				// We start by uploading the application files

				/**
				 * TODO getDeployableId getRemoteAppStreamByID
				 */

				String artefactId = app.getDeployable().getDeployableId();
				String uploadedFileLocation = localTempPath + "/"
						+ app.getDeployable().getDeployableName();
				AppObj appObj = null;
				AppProcessing appProcessing = new AppProcessing(localTempPath
						+ "/");
				if (artefactId == null) {
					FileUtilities.writeToFile(uploadedInputStream,
							uploadedFileLocation);
					InputStream is = new FileInputStream(new File(
							uploadedFileLocation));

					appObj = appProcessing.uploadAppToRemoteServer(is, app
							.getDeployable().getDeployableName());
					artefactId = appObj.getId();
					System.out.println("ID ID:" + artefactId);
				} else {
					FileUtilities.writeToFile(
							appProcessing.getRemoteAppStreamByID(artefactId),
							uploadedFileLocation);
				}

				// redefine the deployable element according to the uploaded
				// file location
				DeployableType newDep = new DeployableType();
				newDep.setDeployableDirectory(localTempPath);
				newDep.setDeployableName(app.getDeployable()
						.getDeployableName());
				newDep.setDeployableType(app.getDeployable()
						.getDeployableType());
				newDep.setDeployableId(artefactId);
				// update the application pool
				ApplicationPool.INSTANCE.updateApp(appid, newDep);

				// String runtime = env.getStaging().map.get("runtime");
				String runtime = getEnvironmentRuntime(env);

				String framework = getEnvironmentFramework(env);

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
				// get the memory attribute from the environment
				//app.setMemory(env.getEnvMemory());
				// create the application
				client.createApplication(app.getAppName(), staging,
						(int) env.getEnvMemory(), app.getUris().getUri(),
						servicesList, CHECK_EXISTS);

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
					// TODO
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
				// delete the temp uploaded deployable
				FileUtilities.deletefile(uploadedFileLocation);
			}
			// update the Application with describeApp and deleteApp links
			LinksListType linksList = ApplicationPool.INSTANCE.getApp(appid)
					.getLinksList();
			linksList = ApplicationLinkGenerator.addStartAppLink(linksList,
					appid, apiUrl);
			linksList = ApplicationLinkGenerator.addStopAppLink(linksList,
					appid, apiUrl);
			linksList = ApplicationLinkGenerator.addRestartAppLink(linksList,
					appid, apiUrl);

			ApplicationPool.INSTANCE.updateApp(appid, linksList);
			// adds the envID to the deployed application
			ApplicationPool.INSTANCE.updateApp(appid, envid);

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

	private String getEnvironmentRuntime(
			telecom.sudparis.eu.paas.core.server.xml.environment.EnvironmentType env) {
		List<telecom.sudparis.eu.paas.core.server.xml.environment.EntryType> list = env
				.getConfiguration().getEntry();

		for (telecom.sudparis.eu.paas.core.server.xml.environment.EntryType entry : list) {
			if (entry.getKey().toLowerCase().equals("runtime"))
				return entry.getValue();
		}
		return null;
	}

	private String getEnvironmentFramework(
			telecom.sudparis.eu.paas.core.server.xml.environment.EnvironmentType env) {
		List<telecom.sudparis.eu.paas.core.server.xml.environment.EntryType> list = env
				.getConfiguration().getEntry();

		for (telecom.sudparis.eu.paas.core.server.xml.environment.EntryType entry : list) {
			if (entry.getKey().toLowerCase().equals("framework"))
				return entry.getValue();
		}
		return null;
	}

	private List<String> getEnvironmentServiceList(
			telecom.sudparis.eu.paas.core.server.xml.environment.EnvironmentType env) {
		List<telecom.sudparis.eu.paas.core.server.xml.environment.EntryType> list = env
				.getConfiguration().getEntry();
		List<String> serviceList = new ArrayList<String>();
		System.out.println("HERE display list:" + list);
		if (list != null)
			for (telecom.sudparis.eu.paas.core.server.xml.environment.EntryType entry : list) {
				System.out.println("HERE display entry:" + entry);
				if (entry.getKey().toLowerCase().equals("service"))
					serviceList.add(entry.getValue());
				System.out.println("HERE display entry.getkey:"
						+ entry.getKey());
			}
		return serviceList;
	}

	private synchronized Long getNextId() {
		return new Long(ApplicationPool.INSTANCE.getNextID());
	}

	private ApplicationType findAssociatedAppInThePool(String appName) {
		List<ApplicationType> appListPool = ApplicationPool.INSTANCE
				.getAppList();
		for (ApplicationType poolApp : appListPool) {
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
			ApplicationType app = ApplicationPool.INSTANCE.getApp(appId);
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

			// List<Application> appListPool = appPool.INSTANCE.getAppList();
			for (CloudApplication ca : appListCF) {
				String id = Long.toString(getNextId());
				ApplicationType app = new ApplicationType();
				app.setAppId(Integer.parseInt(id));
				app.setAppName(ca.getName());
				//app.setCheckExists(CHECK_EXISTS);
				//app.setMemory(ca.getMemory());

				// Since we can not recreate an environment, we recover only the
				// configuration
				// elements and the memory
				EnvironmentType env = new EnvironmentType();
				ConfigurationType conf = new ConfigurationType();

				// get the runtime
				EntryType runtime = new EntryType();
				runtime.setKey("runtime");
				runtime.setValue(ca.getStaging().getRuntime());
				conf.getEntry().add(runtime);

				// get the framework
				EntryType framework = new EntryType();
				framework.setKey("framework");
				framework.setValue(ca.getStaging().getFramework());
				conf.getEntry().add(framework);

				// get the service Names
				for (String service : ca.getServices()) {
					EntryType e = new EntryType();
					e.setKey("service");
					e.setValue(service);
					conf.getEntry().add(e);
				}
				env.setConfiguration(conf);
				env.setEnvMemory(ca.getResources().get("memory"));
				app.setEnvironment(env);
				app.setStatus(ca.getState().toString());
				UrisType uris = new UrisType();
				uris.getUri().addAll(ca.getUris());
				app.setUris(uris);
				app.setNbInstances(ca.getInstances());

				LinksListType linksList = new LinksListType();
				linksList = ApplicationLinkGenerator.addDescribeAppLink(
						linksList, id, apiUrl);
				linksList = ApplicationLinkGenerator.addDestroyAppLink(
						linksList, id, apiUrl);
				linksList = ApplicationLinkGenerator.addCreateAppLink(
						linksList, apiUrl);
				linksList = ApplicationLinkGenerator.addFindAppsLink(linksList,
						apiUrl);
				linksList = ApplicationLinkGenerator.addUpdateAppLink(
						linksList, id, apiUrl);
				linksList = ApplicationLinkGenerator.addStartAppLink(linksList,
						id, apiUrl);
				linksList = ApplicationLinkGenerator.addStopAppLink(linksList,
						id, apiUrl);
				linksList = ApplicationLinkGenerator.addRestartAppLink(
						linksList, id, apiUrl);

				app.setLinksList(linksList);
				ApplicationPool.INSTANCE.add(app);
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
	
	private List<SimpleApplicationType> formatAppList(List<ApplicationType> appList) {
		List<SimpleApplicationType> listAppType=new ArrayList<SimpleApplicationType>();
		for (ApplicationType a:appList){
			SimpleApplicationType appType=new SimpleApplicationType();
			appType.setId(a.getAppId());
			appType.setDescription(a.getDescription());
			appType.setName(a.getAppName());
			appType.setUri(ApplicationLinkGenerator.formatApiURL(apiUrl) +"app/" + a.getAppId());
			listAppType.add(appType);
		}
		return listAppType;
	}

}
