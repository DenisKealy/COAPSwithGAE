package telecom.sudparis.eu.paas.core.server.ressources.manager.application;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

//import com.sun.jersey.multipart.FormDataParam;

import telecom.sudparis.eu.paas.api.ressources.manager.application.RestApplicationManager;
import telecom.sudparis.eu.paas.core.server.pool.application.ApplicationPool;
import telecom.sudparis.eu.paas.core.server.pool.environment.EnvironmentPool;
import telecom.sudparis.eu.paas.core.server.ressources.util.ApplicationLinkGenerator;
import telecom.sudparis.eu.paas.core.server.ressources.util.FileUtilities;
import telecom.sudparis.eu.paas.core.server.xml.Error;
import telecom.sudparis.eu.paas.core.server.xml.LinksListType;
import telecom.sudparis.eu.paas.core.server.xml.OperationResponse;
import telecom.sudparis.eu.paas.core.server.xml.application.ApplicationType;
import telecom.sudparis.eu.paas.core.server.xml.application.DeployableType;
import telecom.sudparis.eu.paas.core.server.xml.application.InstanceType;
import telecom.sudparis.eu.paas.core.server.xml.application.InstancesType;
import telecom.sudparis.eu.paas.core.server.xml.application.UrisType;
import telecom.sudparis.eu.paas.core.server.xml.environment.EnvironmentType;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasApplicationManifestType;
import telecom.sudparis.eu.paas.core.server.xml.manifest.PaasApplicationVersionInstanceType;

/**
 * REST resource of type EnvironmentManager
 * 
 * @author Eman (FCI-CU)
 * 
 */
@Path("app")
public class ApplicationManagerRessource implements RestApplicationManager {

	/**
	 * ressource bundle to get the connection credentials
	 */
	private static ResourceBundle rb = ResourceBundle
			.getBundle("telecom.sudparis.eu.paas.core.server.ressources.credentials");

	/**
	 * The target Google App Engine (GAE) URL
	 */
	private static final String ccUrl = rb.getString("vcap.target");

	/**
	 * The public GAE-PaaS API URL
	 */
	private static String apiUrl = rb.getString("api.public.url");

	/**
	 * The GAE user mail
	 */
	private static final String TEST_USER_EMAIL = rb.getString("vcap.email");

	/**
	 * The GAE user password
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

	private static boolean checkGAEApplications = false;

	/**
	 * The temp folder used to store uploaded files
	 */
	private static String localTempPath = System.getProperty("java.io.tmpdir");

	@Override
	@POST
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response createApplication(String cloudApplicationDescriptor) {
		System.out.println("start creating app in GAE, Yarab unga7");
		try {
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

				// retrieve app description

				String description = manifest.getPaasApplication()
						.getDescription().toString();
				app.setDescription(description);

				
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
				String deployableAppId = manifest.getPaasApplication().getPaasApplicationVersion()
						   .getPaasApplicationDeployable().getDeployedAppID();
				
				String deployableDirectory = manifest.getPaasApplication()
						.getPaasApplicationVersion()
						.getPaasApplicationDeployable().getLocation();
				// if the deployable is already in the DB we provide it's ID
				DeployableType dep = new DeployableType();
				dep.setDeployableId(deployableAppId);
				dep.setDeployableDirectory(deployableDirectory);
				dep.setDeployableName(deployableName);
				dep.setDeployableType(deployableType);
				app.setDeployable(dep);
				
				// retrieve uris
				UrisType uris = new UrisType();
				String runAppURL=ccUrl.replace("app_ID", deployableAppId);
				uris.getUri().add(runAppURL);
				app.setUris(uris);

				
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
				// app.setCheckExists(CHECK_EXISTS);
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
	@DELETE
	@Path("{appId}/delete")
	@Produces("application/xml")
	public Response deleteApplication(@PathParam("appId") String appid) {
		try {
			ApplicationType app = null;
			app = ApplicationPool.INSTANCE.getApp(appid);
			if (app != null) {
				// search to know how to delete app using appcfg
				ApplicationPool.INSTANCE.remove(app);
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
	@DELETE
	@Path("delete")
	@Produces("application/xml")
	public Response deleteApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deployApplication(String appid, String envid,
			InputStream uploadedInputStream) {
		try {
			System.out.println("HERE envi id and app id:" + envid + "  "
					+ appid);
			telecom.sudparis.eu.paas.core.server.xml.environment.EnvironmentType env = EnvironmentPool.INSTANCE
					.getEnv(envid);
			System.out.println("HERE env :" + env);
			ApplicationType app = ApplicationPool.INSTANCE.getApp(appid);
			System.out.println("HERE app :" + app);

			String uploadedFileLocation = app.getDeployable()
					.getDeployableDirectory();
			uploadedFileLocation=formatApiPath(uploadedFileLocation)+app.getDeployable().getDeployableName()+"\\war";
			System.out.println("uploadedFileLocation="+uploadedFileLocation);
			String appcfgQuery="";
			if(env.getEnvOperatingSystem().contains("win") && env.getEnvProgrammingLang().contains("java"))
			    appcfgQuery="appcfg.cmd --no_cookies --email="+TEST_USER_EMAIL+" update "+uploadedFileLocation;
			System.out.println("appcfgQuery="+appcfgQuery);
			Process p;
			p = Runtime.getRuntime().exec(appcfgQuery);
			String line;
	        OutputStream outputStream = p.getOutputStream();
	        PrintStream printStream = new PrintStream(outputStream);
	        printStream.println(TEST_USER_PASS);
	        printStream.flush();
	        printStream.close();
	        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        while((line=input.readLine()) != null){
	             System.out.println(line);
	             if(line.contains("java.lang.NullPointerException"))
	             { 
	            	error.setValue("Failed to deploy the application: wrong password");
	     			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	     					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build(); 
	             }
		    }
	         input.close();
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
			// update the status of the deployed application
			app.setStatus("STARTED");

			return Response.status(Response.Status.OK).entity(app)
								.type(MediaType.APPLICATION_XML_TYPE).build();
			/*or.setValue("your application is deployed successfuly");
			return Response.status(Response.Status.OK).entity(or)
					.type(MediaType.APPLICATION_XML_TYPE).build();
			 */
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
	public Response describeApplication(String appid) {
		try{
			ApplicationType app = ApplicationPool.INSTANCE.getApp(appid);
			if(app!=null)
			{
				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<ApplicationType>(app) {
						}).type(MediaType.APPLICATION_XML_TYPE).build();
			}
			else
			{
				System.out.println("No Application with ID " + appid
						+ " was found!");
				error.setValue("No Application with ID " + appid
						+ " was found! Start by creating the application");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity(error).build();
			}
		}catch (Exception e) {
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
	@GET
	public Response findApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@POST
	@Path("{appId}/restart")
	public Response restartApplication(@PathParam("appId") String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
    /*
     * this method is already included inside deployApplication method
     * when deploying an app to GAE --> it is started by default
     * 
     */
	@Override
	public Response startApplication(String appid) {
		ApplicationType app = ApplicationPool.INSTANCE.getApp(appid);
		app.setStatus("STARTED");
		return describeApplication(appid);
	}

	@Override
	@POST
	@Path("{appId}/stop")
	public Response stopApplication(@PathParam("appId") String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@POST
	@Path("/{appId}/action/undeploy/env/{envId}")
	public Response undeployApplication(@PathParam("envId") String arg0,
			@PathParam("appId") String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@POST
	@Path("{appId}/update")
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response updateApplication(@PathParam("appId") String arg0,
			String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	private synchronized Long getNextId() {
		return new Long(ApplicationPool.INSTANCE.getNextID());
	}

	@GET
	@Path("/hello")
	public Response hello() {

		String output = "Jersey say : Yarab createApp() tanga7\n";
		return Response.status(200).entity(output).build();
	}

	public static String formatApiPath(String apiPath) {
		apiPath = apiPath.trim();
		if (!apiPath.endsWith("\\"))
			apiPath = apiPath + "\\";
		return apiPath;
	}
}
