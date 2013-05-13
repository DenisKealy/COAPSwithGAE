/**
 * 
 */
package telecom.sudparis.eu.paas.core.server.ressources.manager.application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

import telecom.sudparis.eu.paas.api.ressources.manager.application.RestApplicationManager;
import telecom.sudparis.eu.paas.core.server.applications.pool.ApplicationPool;
import telecom.sudparis.eu.paas.core.server.environments.pool.EnvironmentPool;
import telecom.sudparis.eu.paas.core.server.ressources.exception.NotSupportedException;
import telecom.sudparis.eu.paas.core.server.xml.ApplicationXML;
import telecom.sudparis.eu.paas.core.server.xml.ApplicationXML.LinksList;
import telecom.sudparis.eu.paas.core.server.xml.ApplicationXML.LinksList.Link;
import telecom.sudparis.eu.paas.core.server.xml.DeployableXML;
import telecom.sudparis.eu.paas.core.server.xml.EnvironmentXML;
import telecom.sudparis.eu.paas.core.server.xml.Error;
import telecom.sudparis.eu.paas.core.server.xml.OperationResponse;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasApplicationManifestType;

import com.jcraft.jsch.JSch;
import com.openshift.client.IApplication;
import com.openshift.client.ICartridge;
import com.openshift.client.IDomain;
import com.openshift.client.IEmbeddableCartridge;
import com.openshift.client.IOpenShiftConnection;
import com.openshift.client.IUser;
import com.openshift.client.OpenShiftConnectionFactory;

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
	 * The target OpenShift URL
	 */
	private static final String ccUrl = rb.getString("vcap.target");

	/**
	 * The public OS-PaaS API URL
	 */
	private static String apiUrl = rb.getString("api.public.url");

	/**
	 * The OpenShift user mail
	 */
	private static final String TEST_USER_EMAIL = rb.getString("vcap.email");

	/**
	 * The OpenShift user password
	 */
	private static final String TEST_USER_PASS = rb.getString("vcap.passwd");

	/**
	 * An element to represent The response
	 */
	private OperationResponse or = new OperationResponse();

	/**
	 * An element to display Errors
	 */
	private Error error = new Error();
	
	private static String localTempPath = System.getProperty("java.io.tmpdir");

	private static boolean checkOSApplications = false;

	@Override
	public Response createApplication(String cloudApplicationDescriptor) {

		IOpenShiftConnection iOpenShiftConnection = new OpenShiftConnectionFactory()
				.getConnection(TEST_USER_EMAIL, TEST_USER_EMAIL,
						TEST_USER_PASS, ccUrl);

		IUser user = iOpenShiftConnection.getUser();
		IDomain domain = user.getDefaultDomain();
		

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
					e.printStackTrace();
				}
				// retrieve appName
				String appName = manifest.getPaasApplication().getName();
				String envName = manifest.getPaasApplication()
						.getEnvironement();
				String deployableName = manifest.getPaasApplication().getPaasApplicationVersion().getPaasApplicationDeployable().getName();
				String deployableType = manifest.getPaasApplication().getPaasApplicationVersion().getPaasApplicationDeployable().getContentType();
				String deployableDirectory = manifest.getPaasApplication().getPaasApplicationVersion().getPaasApplicationDeployable().getLocation();

				ICartridge ic = null;
				// TODO voir le cas specific jenkins

				// Gets the environment with the name specified in the Manifest
				EnvironmentXML env = EnvironmentPool.INSTANCE.getEnvByName(envName);

				if (env == null) {
					System.out.println("No Environment with name " + envName
							+ " was found!");
					error.setValue("No Environment with name " + envName
							+ " was found! Start by creating the environment");
					return Response.status(Response.Status.PRECONDITION_FAILED)
							.entity(error).build();
				} else {
					List<String> containerNames = env.getContainerNames();
					if (containerNames.size() == 1)
						envName = containerNames.get(0);
					else {
						System.out
								.println("Wrong number of environments found!");
						error.setValue("Wrong number of environments found! Check the Environment Manifest.");
						return Response
								.status(Response.Status.PRECONDITION_FAILED)
								.entity(error).build();
					}

					if (envName.toLowerCase().contains("jboss"))
						ic = ICartridge.JBOSSAS_7;
					else if (envName.toLowerCase().contains("jenkins"))
						ic = ICartridge.JENKINS_14;
					else if (envName.toLowerCase().contains("perl"))
						ic = ICartridge.PERL_51;
					else if (envName.toLowerCase().contains("php"))
						ic = ICartridge.PHP_53;
					else if (envName.toLowerCase().contains("python"))
						ic = ICartridge.PYTHON_26;
					else if (envName.toLowerCase().contains("rack"))
						ic = ICartridge.RACK_11;
					else if (envName.toLowerCase().contains("ruby"))
						ic = ICartridge.RUBY_18;
					else if (envName.toLowerCase().contains("wsgi"))
						ic = ICartridge.WSGI_32;
					else if(envName.toLowerCase().contains("tomcat"))// TODO see if tomcat is supported
						ic = ICartridge.JBOSSAS_7;

					IApplication createdApp = domain.createApplication(appName,
							ic);
					
					// Add the additional Cartridges
					List<String> cartridgeNames = env.getCartridgeNames();
					if (cartridgeNames != null && cartridgeNames.size() > 0) {
						for (String c : cartridgeNames) {
							IEmbeddableCartridge embeddableCartridge = null;
							if (c.toUpperCase().contains("MMS"))
								embeddableCartridge = IEmbeddableCartridge._10GEN_MMS_AGENT_01;
							else if (c.toUpperCase().contains("JENKINS"))
								embeddableCartridge = IEmbeddableCartridge.JENKINS_14;
							else if (c.toUpperCase().contains("METRICS"))
								embeddableCartridge = IEmbeddableCartridge.METRICS_01;
							else if (c.toUpperCase().contains("MONGODB"))
								embeddableCartridge = IEmbeddableCartridge.MONGODB_20;
							else if (c.toUpperCase().contains("MYSQL"))
								embeddableCartridge = IEmbeddableCartridge.MYSQL_51;
							else if (c.toUpperCase().contains("PHPMYADMIN"))
								embeddableCartridge = IEmbeddableCartridge.PHPMYADMIN_34;
							else if (c.toUpperCase().contains("POSTGRE"))
								embeddableCartridge = IEmbeddableCartridge.POSTGRESQL_84;
							else if (c.toUpperCase().contains("ROCKMONGO"))
								embeddableCartridge = IEmbeddableCartridge.ROCKMONGO_11;

							if (embeddableCartridge != null)
								createdApp
										.addEmbeddableCartridge(embeddableCartridge);
							createdApp.getGitUrl();
						}
					}

					// generate appID
					Long id = getNextId();

					ApplicationXML app = new ApplicationXML(createdApp);
					app.setAppId(Long.toString(id));
					app.setApplicationScale(createdApp.getApplicationScale());
					// Set the information relative to the deployable (war file
					// for example)
					DeployableXML depXML = new DeployableXML();
					depXML.setDeployableName(deployableName);
					depXML.setDeployableType(deployableType);
					depXML.setDeployableDirectory(deployableDirectory);
					app.setDeployable(depXML);

					app.setApplicationUrl(app.getApplicationUrl()
							+ deployableName.substring(0,
									deployableName.lastIndexOf('.')));

					// Update the corespending Application with the
					// AdequateLinkList
					// in the AppPool
					LinksList linksList = new LinksList();
					linksList = addFindAppVersionsLink(linksList,
							Long.toString(id));
					linksList = addDescribeAppLink(linksList, Long.toString(id));
					linksList = addDeleteAppLink(linksList, Long.toString(id));
					app.setLinksList(linksList);

					ApplicationPool.INSTANCE.add(app);

					return Response.status(Response.Status.OK)
							.entity(new GenericEntity<ApplicationXML>(app) {
							}).type(MediaType.APPLICATION_XML_TYPE).build();
				}

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
			List<ApplicationXML> appListPool = ApplicationPool.INSTANCE.getAppList();

			if (appListPool != null && appListPool.size() > 0)
				return Response
						.status(Response.Status.OK)
						.entity(new GenericEntity<List<ApplicationXML>>(
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
			ApplicationXML app = ApplicationPool.INSTANCE.getApp(appid);

			if (app == null) {
				or.setValue("Application not available.");
				return Response.status(Response.Status.OK).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			} else {
				IOpenShiftConnection iOpenShiftConnection = new OpenShiftConnectionFactory()
						.getConnection(TEST_USER_EMAIL, TEST_USER_EMAIL,
								TEST_USER_PASS, ccUrl);
				IUser user = iOpenShiftConnection.getUser();
				IDomain domain = user.getDefaultDomain();

				domain.getApplicationByName(app.getAppName()).start();
				return describeApplication(appid);
			}
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
			ApplicationXML app = ApplicationPool.INSTANCE.getApp(appid);

			if (app == null) {
				or.setValue("Application not available.");
				return Response.status(Response.Status.OK).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			} else {
				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<ApplicationXML>(app) {
						}).type(MediaType.APPLICATION_XML_TYPE).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to retrieve the application: "
					+ e.getMessage());
			e.printStackTrace();
			error.setValue("Failed to retrieve the application: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response deleteApplication(String appid) {
		try {
			copyDeployedApps2Pool();
			ApplicationXML app = ApplicationPool.INSTANCE.getApp(appid);

			if (app == null) {
				or.setValue("Application not available.");
				return Response.status(Response.Status.OK).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			} else {
				IOpenShiftConnection iOpenShiftConnection = new OpenShiftConnectionFactory()
						.getConnection(TEST_USER_EMAIL, TEST_USER_EMAIL,
								TEST_USER_PASS, ccUrl);
				IUser user = iOpenShiftConnection.getUser();
				IDomain domain = user.getDefaultDomain();

				domain.getApplicationByName(app.getAppName()).destroy();
				ApplicationPool.INSTANCE.remove(app);
				or.setValue("The application with ID " + appid
						+ " was succefully deleted");
				return Response.status(Response.Status.OK).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
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
			List<ApplicationXML> listapp = ApplicationPool.INSTANCE.getAppList();

			if (listapp != null) {
				IOpenShiftConnection iOpenShiftConnection = new OpenShiftConnectionFactory()
						.getConnection(TEST_USER_EMAIL, TEST_USER_EMAIL,
								TEST_USER_PASS, ccUrl);
				IUser user = iOpenShiftConnection.getUser();
				IDomain domain = user.getDefaultDomain();

				for (ApplicationXML a : listapp)
					domain.getApplicationByName(a.getAppName()).destroy();
			}
			ApplicationPool.INSTANCE.removeAll();
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
			copyDeployedApps2Pool();
			ApplicationXML app = ApplicationPool.INSTANCE.getApp(appid);

			if (app == null) {
				or.setValue("Application not available.");
				return Response.status(Response.Status.OK).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			} else {
				IOpenShiftConnection iOpenShiftConnection = new OpenShiftConnectionFactory()
						.getConnection(TEST_USER_EMAIL, TEST_USER_EMAIL,
								TEST_USER_PASS, ccUrl);
				IUser user = iOpenShiftConnection.getUser();
				IDomain domain = user.getDefaultDomain();

				domain.getApplicationByName(app.getAppName()).stop();
				return describeApplication(appid);
			}
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
				"The updateApplication is not yet implemented.");
	}

	@Override
	public Response restartApplication(String appid) {
		try {
			copyDeployedApps2Pool();
			ApplicationXML app = ApplicationPool.INSTANCE.getApp(appid);

			if (app == null) {
				or.setValue("Application not available.");
				return Response.status(Response.Status.OK).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			} else {
				IOpenShiftConnection iOpenShiftConnection = new OpenShiftConnectionFactory()
						.getConnection(TEST_USER_EMAIL, TEST_USER_EMAIL,
								TEST_USER_PASS, ccUrl);
				IUser user = iOpenShiftConnection.getUser();
				IDomain domain = user.getDefaultDomain();

				domain.getApplicationByName(app.getAppName()).restart();
				return describeApplication(appid);
			}
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
	public Response deployApplication(String envid, String appid,InputStream uploadedInputStream) {
		//TODO: consider the upload
		File theDir = null;
		String localPath = null;
		try {
			ApplicationXML app = ApplicationPool.INSTANCE.getApp(appid);
			EnvironmentXML env = EnvironmentPool.INSTANCE.getEnv(envid);

			// We only consider the case of an artifact applications
			if (!app.getDeployable().getDeployableType().equals("artifact")) {
				System.out.println("Cannot handle this type of application: "
						+ app.getDeployable().getDeployableType());
				error.setValue("Cannot handle this type of application: "
						+ app.getDeployable().getDeployableType());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(error).type(MediaType.APPLICATION_XML_TYPE)
						.build();

			} else {
				
				//We start by uploading the application files
				String uploadedFileLocation = localTempPath + "/"+app.getDeployable().getDeployableName();

				writeToFile(uploadedInputStream, uploadedFileLocation);
				
				//redefine the deployable element according to the uploaded file location
				DeployableXML newDep=new DeployableXML();
				newDep.setDeployableDirectory(localTempPath);
				newDep.setDeployableName(app.getDeployable().getDeployableName());
				newDep.setDeployableType(app.getDeployable().getDeployableType());
				
				//update the application pool with the uploaded file location
				ApplicationPool.INSTANCE.updateApp(appid, newDep);	
				
				//retrieve the gitURL from the created application
				String gitURL = app.getGitUrl();
				localPath = localTempPath
						+ "/PaaS-API-tmp-" + app.getAppUUID() + "/"
						+ app.getAppName() + "/";

				JSch.setConfig("StrictHostKeyChecking", "no");

				// create Local repository
				Repository localrepo = new FileRepository(localPath + ".git");

				// Create the git
				Git git = new Git(localrepo);

				// Clone the Openshift repo

				Git.cloneRepository().setURI(gitURL)
						.setDirectory(new File(localPath)).call();

				// If this is the default Git repository generated by Openshift,
				// delete the default app
				// git rm -r src/ pom.xml
				git.rm().addFilepattern("-r src/ pom.xml").call();

				// Add the application archive

				// Specify the copy destination in the Git repository
				// This depends on the type of the used environment

				// TODO consider the other types!!
				String copydestination = "deployments/";
				if (env.getContainerNames().get(0).toLowerCase()
						.contains("jboss"))
					copydestination = "deployments/";

				String deployableDirectory = app.getDeployable()
						.getDeployableDirectory();
				String deployableName = app.getDeployable().getDeployableName();
				copy(deployableDirectory + "/" + deployableName, localPath
						+ copydestination + deployableName);

				git.add().addFilepattern(".").call();

				// Commit changes
				git.commit().setMessage("Changed by the OS-PaaS API").call();

				// Push
				git.push().call();

				// removes the directory before exiting
				theDir = new File(localPath);
				System.out.println("Removing Existing directory: " + localPath);
				removeDirectory(theDir);
				//delete the temp uploaded deployable
				deletefile(uploadedFileLocation);

				return Response.status(Response.Status.OK).entity(app)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to deploy the application: "
					+ e.getMessage());
			e.printStackTrace();
			if (e.getMessage().contains(
					"already exists and is not an empty directory"))
				error.setValue("Failed to deploy the application: "
						+ e.getMessage() + "\n Delete the" + localPath
						+ "folder");
			else
				error.setValue("Failed to deploy the application: "
						+ e.getMessage());
			if (theDir != null && theDir.exists())
				removeDirectory(theDir);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}

	}

	@Override
	public Response undeployApplication(String envid, String appid) {
		File theDir = null;
		String localPath = null;
		try {
			ApplicationXML app = ApplicationPool.INSTANCE.getApp(appid);
			EnvironmentXML env = EnvironmentPool.INSTANCE.getEnv(envid);

			// We only consider the case of an artifact applications
			if (!app.getDeployable().getDeployableType().equals("artifact")) {
				System.out.println("Cannot handle this type of application: "
						+ app.getDeployable().getDeployableType());
				error.setValue("Cannot handle this type of application: "
						+ app.getDeployable().getDeployableType());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(error).type(MediaType.APPLICATION_XML_TYPE)
						.build();

			} else {
				String gitURL = app.getGitUrl();
				// String localPath = System.getProperty("user.home")
				// + "/PaaS-API-tmp/" + app.getAppName() + "/";

				localPath = "C:/Users/sellami" + "/PaaS-API-tmp-"
						+ app.getAppUUID() + "/" + app.getAppName() + "/";
				JSch.setConfig("StrictHostKeyChecking", "no");

				// create Local repository
				Repository localrepo = new FileRepository(localPath + ".git");

				// Create the git
				Git git = new Git(localrepo);

				// Clone the Openshift repo
				Git.cloneRepository().setURI(gitURL)
						.setDirectory(new File(localPath)).call();

				// If this is the default Git repository generated by Openshift,
				// delete the default app
				// git rm -r src/ pom.xml
				String copydestination = "deployments/";
				if (env.getContainerNames().get(0).toLowerCase()
						.contains("jboss"))
					copydestination = "deployments/";

				git.rm()
						.addFilepattern(
								"-r src/ " + copydestination + " pom.xml")
						.call();

				git.add().addFilepattern(".").call();

				// Commit changes
				git.commit()
						.setMessage("Application undeployed by the OS-PaaS API")
						.call();

				// Push
				git.push().call();

				// removes the directory before exiting
				theDir = new File(localPath);
				System.out.println("Removing Existing directory: " + localPath);
				removeDirectory(theDir);

				return Response.status(Response.Status.OK).entity(app)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to deploy the application: "
					+ e.getMessage());
			e.printStackTrace();
			if (e.getMessage().contains(
					"already exists and is not an empty directory"))
				error.setValue("Failed to deploy the application: "
						+ e.getMessage() + "\n Delete the" + localPath
						+ "folder");
			else
				error.setValue("Failed to deploy the application: "
						+ e.getMessage());
			if (theDir != null && theDir.exists())
				removeDirectory(theDir);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	// private methods

	private synchronized Long getNextId() {
		return new Long(ApplicationPool.INSTANCE.getNextID());
	}

	//
	private LinksList addFindAppVersionsLink(LinksList linksList, String appId) {

		String url = formatApiURL(apiUrl) + "app/" + appId + "/version";
		Link findAppVersionsLink = new Link();
		findAppVersionsLink.setAction("GET");
		findAppVersionsLink.setLabel("findApplicationVersions()");
		findAppVersionsLink.setHref(url);
		linksList.getLink().add(findAppVersionsLink);
		return linksList;

	}

	//
	private LinksList addDescribeAppLink(LinksList linksList, String appId) {
		String url = formatApiURL(apiUrl) + "app/" + appId;
		Link describeAppLink = new Link();
		describeAppLink.setAction("GET");
		describeAppLink.setLabel("describeApplication(appid)");
		describeAppLink.setHref(url);
		linksList.getLink().add(describeAppLink);
		return linksList;
	}

	//
	private LinksList addDeleteAppLink(LinksList linksList, String appId) {
		String url = formatApiURL(apiUrl) + "app/" + appId + "/delete";
		Link deleteAppLink = new Link();
		deleteAppLink.setAction("DELETE");
		deleteAppLink.setLabel("deleteApplication(appid)");
		deleteAppLink.setHref(url);
		linksList.getLink().add(deleteAppLink);
		return linksList;
	}

	private void copyDeployedApps2Pool() {
		if (!checkOSApplications) {
			checkOSApplications = true;

			IOpenShiftConnection iOpenShiftConnection = new OpenShiftConnectionFactory()
					.getConnection(TEST_USER_EMAIL, TEST_USER_EMAIL,
							TEST_USER_PASS, ccUrl);
			IUser user = iOpenShiftConnection.getUser();
			IDomain domain = user.getDefaultDomain();

			List<IApplication> appListOS = null;
			appListOS = domain.getApplications();

			
			for (IApplication ca : appListOS) {
				String id = Long.toString(getNextId());
				ApplicationXML app = new ApplicationXML();
				app.setAppId(id);
				app.setApplicationScale(ca.getApplicationScale());
				app.setAppName(ca.getName());
				app.setApplicationUrl(ca.getApplicationUrl());
				app.setAppUUID(ca.getUUID());
				if (ca.hasSSHSession())
					app.setEnvironmentProperties(ca.getEnvironmentProperties());
				app.setGitUrl(ca.getGitUrl());
				app.setHealthCheckUrl(ca.getHealthCheckUrl());

				LinksList linksList = new LinksList();
				linksList = addDescribeAppLink(linksList, id);
				linksList = addDeleteAppLink(linksList, id);
				app.setLinksList(linksList);
				ApplicationPool.INSTANCE.add(app);
			}
		}
	}

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

	private static void copy(String source, String destination) {
		InputStream inStream = null;
		OutputStream outStream = null;

		try {

			File sourcefile = new File(source);
			File destinationfile = new File(destination);

			inStream = new FileInputStream(sourcefile);
			outStream = new FileOutputStream(destinationfile);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);
			}
			inStream.close();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String formatApiURL(String apiUrl) {
		apiUrl = apiUrl.trim();
		if (!apiUrl.endsWith("/"))
			apiUrl = apiUrl + "/";
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
