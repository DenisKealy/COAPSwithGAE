/**
 * 
 */
package telecom.sudparis.eu.paas.core.server.ressources.manager.application;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import telecom.sudparis.eu.paas.api.ressources.manager.application.RestApplicationManager;
import telecom.sudparis.eu.paas.core.server.applications.pool.ApplicationPool;
import telecom.sudparis.eu.paas.core.server.environments.pool.EnvironmentPool;
import telecom.sudparis.eu.paas.core.server.ressources.exception.NotSupportedException;
import telecom.sudparis.eu.paas.core.server.xml.ApplicationXML;
import telecom.sudparis.eu.paas.core.server.xml.DeployableXML;
import telecom.sudparis.eu.paas.core.server.xml.EnvironmentXML;
import telecom.sudparis.eu.paas.core.server.xml.Error;
import telecom.sudparis.eu.paas.core.server.xml.OperationResponse;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasManifestType;

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

	/**
	 * The Application pool
	 */
	private ApplicationPool appPool;

	/**
	 * The Environment pool
	 */
	private EnvironmentPool envPool;

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
					e.printStackTrace();
				}
				// retrieve appName
				String appName = manifest.getPaasApplication().getName();
				String envName = manifest.getPaasApplication()
						.getEnvironement();
				String deployableName = manifest.getPaasApplication()
						.getPaasVersion().getPaasDeployable().getName();
				String deployableType = manifest.getPaasApplication()
						.getPaasVersion().getPaasDeployable().getContentType();
				String deployableDirectory = manifest.getPaasApplication()
						.getPaasVersion().getPaasDeployable().getLocation();

				ICartridge ic = null;
				// TODO voir le cas specific jenkins

				// Gets the environment with the name specified in the Manifest
				EnvironmentXML env = envPool.INSTANCE.getEnvByName(envName);

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
					Map<String, String> linksList = new HashMap<String, String>();
					linksList = addFindAppVersionsLink(linksList,
							Long.toString(id));
					linksList = addDescribeAppLink(linksList, Long.toString(id));
					linksList = addDeleteAppLink(linksList, Long.toString(id));
					app.setLinksList(linksList);

					appPool.INSTANCE.add(app);

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
			error.setValue("Failed to create the application: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response createApplicationVersion(String appId,
			String applicationVersionDescriptor) {
		throw new NotSupportedException();
	}

	@Override
	public Response findApplications() {
		try {
			copyDeployedApps2Pool();
			List<ApplicationXML> appListPool = appPool.INSTANCE.getAppList();

			if (appListPool != null && appListPool.size() > 0)
				return Response
						.status(Response.Status.OK)
						.entity(new GenericEntity<List<ApplicationXML>>(
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
			System.out.println(e.getStackTrace());
			e.printStackTrace();
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
		throw new NotSupportedException();
	}

	@Override
	public Response startApplicationVersionInstance(String appid,
			String versionid, String instanceid) {
		try {
			copyDeployedApps2Pool();
			ApplicationXML app = appPool.INSTANCE.getApp(appid);

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
			error.setValue("Failed to start the application: " + e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response describeApplication(String appid) {
		try {
			copyDeployedApps2Pool();
			ApplicationXML app = appPool.INSTANCE.getApp(appid);

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
			ApplicationXML app = appPool.INSTANCE.getApp(appid);

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
				appPool.INSTANCE.remove(app);
				or.setValue("The application with ID " + appid
						+ " was succefully deleted");
				return Response.status(Response.Status.OK).entity(or)
						.type(MediaType.APPLICATION_XML_TYPE).build();
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
			List<ApplicationXML> listapp = appPool.INSTANCE.getAppList();

			if (listapp != null) {
				IOpenShiftConnection iOpenShiftConnection = new OpenShiftConnectionFactory()
				.getConnection(TEST_USER_EMAIL, TEST_USER_EMAIL,
						TEST_USER_PASS, ccUrl);
				IUser user = iOpenShiftConnection.getUser();
				IDomain domain = user.getDefaultDomain();

				for (ApplicationXML a : listapp)
					domain.getApplicationByName(a.getAppName()).destroy();
			}
			appPool.INSTANCE.removeAll();
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
			copyDeployedApps2Pool();
			ApplicationXML app = appPool.INSTANCE.getApp(appid);

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
			error.setValue("Failed to stop the application: " + e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response findApplicationVersions(String appid) {
		throw new NotSupportedException();
	}

	// private methods

	private synchronized Long getNextId() {
		return new Long(appPool.INSTANCE.getNextID());
	}

	//
	private Map<String, String> addFindAppVersionsLink(
			Map<String, String> linksList, String appId) {
		String url = "http://localhost:8080/OpenShift-API/rest/app/" + appId
				+ "/version";
		linksList.put("[GET]findApplicationVersions(appid)", url);
		return linksList;
	}

	//
	private Map<String, String> addDescribeAppLink(
			Map<String, String> linksList, String appId) {
		String url = "http://localhost:8080/OpenShift-API/rest/app/" + appId;
		linksList.put("[GET]describeApplication(appid)", url);
		return linksList;
	}

	//
	private Map<String, String> addDeleteAppLink(Map<String, String> linksList,
			String appId) {
		String url = "http://localhost:8080/OpenShift-API/rest/app/" + appId
				+ "/delete";
		linksList.put("[DELETE]deleteApplication(appid)", url);
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

			List<ApplicationXML> appListPool = appPool.INSTANCE.getAppList();
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
