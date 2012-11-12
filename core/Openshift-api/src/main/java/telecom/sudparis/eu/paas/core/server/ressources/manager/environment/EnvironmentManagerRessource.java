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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

import com.jcraft.jsch.JSch;

import telecom.sudparis.eu.paas.api.ressources.manager.environment.RestEnvironmentManager;
import telecom.sudparis.eu.paas.core.server.applications.pool.ApplicationPool;
import telecom.sudparis.eu.paas.core.server.environments.pool.EnvironmentPool;
import telecom.sudparis.eu.paas.core.server.ressources.exception.NotSupportedException;
import telecom.sudparis.eu.paas.core.server.xml.ApplicationXML;
import telecom.sudparis.eu.paas.core.server.xml.EnvironmentXML;
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
	private static ResourceBundle rb = ResourceBundle
			.getBundle("telecom.sudparis.eu.paas.core.server.ressources.credentials");

	/**
	 * The target cloud Foundry URL
	 */
	private static final String ccUrl = rb.getString("vcap.target");

	/**
	 * The CloudFoundry user mail
	 */
	private static final String TEST_USER_EMAIL = rb.getString("vcap.email");

	/**
	 * The CloudFoundry user password
	 */
	private static final String TEST_USER_PASS = rb.getString("vcap.passwd");

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
		List<EnvironmentXML> envList = envPool.INSTANCE.getEnvList();
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
	public Response startEnvironment(String envid) {
		throw new NotSupportedException();

	}

	@Override
	public Response stopEnvironment(String envid) {
		throw new NotSupportedException();

	}

	@Override
	public Response undeployApplication(String envid, String appid,
			String versionid, String instanceid) {
		File theDir=null;
		String localPath=null;
		try {
			ApplicationXML app = appPool.INSTANCE.getApp(appid);
			EnvironmentXML env=envPool.INSTANCE.getEnv(envid);
			

			//We only consider the case of an artifact applications
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
				//String localPath = System.getProperty("user.home")
				//		+ "/PaaS-API-tmp/" + app.getAppName() + "/";
				
				localPath="C:/Users/sellami"+"/PaaS-API-tmp-"+app.getAppUUID()+ "/" +app.getAppName() + "/";
				JSch.setConfig("StrictHostKeyChecking", "no");

				// create Local repository
				Repository localrepo = new FileRepository(localPath + ".git");

				// Create the git
				Git git = new Git(localrepo);

				// Clone the Openshift repo
				git.cloneRepository().setURI(gitURL).setDirectory(new File(localPath)).call();								
				
				//If this is the default Git repository generated by Openshift, delete the default app
				//git rm -r src/ pom.xml
				String copydestination="deployments/";
				if (env.getContainerNames().get(0).toLowerCase().contains("jboss"))
					copydestination="deployments/";
				
				git.rm().addFilepattern("-r src/ "+copydestination+" pom.xml").call();

				git.add().addFilepattern(".").call();
				
				//Commit changes
			    git.commit()
		           .setMessage("Application undeployed by the OS-PaaS API")
		           .call();
			    
			    //Push
			    git.push()
		           .call();
			    
				// removes the directory before exiting
				theDir = new File(localPath);
				System.out.println("Removing Existing directory: "
						+ localPath);
				removeDirectory(theDir);

				return Response.status(Response.Status.OK).entity(app)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to deploy the application: "
					+ e.getMessage());
			e.printStackTrace();
			if(e.getMessage().contains("already exists and is not an empty directory"))
				error.setValue("Failed to deploy the application: "
						+ e.getMessage()+"\n Delete the"+ localPath + "folder");
			else
				error.setValue("Failed to deploy the application: "
					+ e.getMessage());
			if (theDir!=null && theDir.exists())
				removeDirectory(theDir);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response getEnvironment(String envid) {
		try {
			EnvironmentXML env = envPool.INSTANCE.getEnv(envid);
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

				// TODO consider the case of several containers
				List<String> containerNames = new ArrayList<String>();
				List<String> cartridgeNames = new ArrayList<String>();

				List<PaasNodeType> lstNodes = manifest.getPaasApplication()
						.getPaasConfigurationTemplate().getPaasNode();

				for (PaasNodeType node : lstNodes) {
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
				Map<String, String> linksList = new HashMap<String, String>();
				linksList = addGetEnvLink(linksList, Long.toString(id));
				linksList = addDeleteEnvLink(linksList, Long.toString(id));
				env.setLinksList(linksList);

				envPool.INSTANCE.add(env);

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
			error.setValue("Failed to create the environment: "
					+ e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	@Override
	public Response deployApplication(String envid, String appid,
			String versionid, String instanceid) {
		File theDir=null;
		String localPath=null;
		try {
			ApplicationXML app = appPool.INSTANCE.getApp(appid);
			EnvironmentXML env=envPool.INSTANCE.getEnv(envid);
			

			//We only consider the case of an artifact applications
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
				localPath = System.getProperty("java.io.tmpdir")+ "/PaaS-API-tmp-"+app.getAppUUID()+ "/" +app.getAppName() + "/";
				
				//localPath="C:/Users/sellami"+"/PaaS-API-tmp-"+app.getAppUUID()+ "/" +app.getAppName() + "/";
				System.out.println("localPath: "+localPath);
				JSch.setConfig("StrictHostKeyChecking", "no");

				// create Local repository
				Repository localrepo = new FileRepository(localPath + ".git");

				// Create the git
				Git git = new Git(localrepo);

				// Clone the Openshift repo

				git.cloneRepository().setURI(gitURL).setDirectory(new File(localPath)).call();								
				
				//If this is the default Git repository generated by Openshift, delete the default app
				//git rm -r src/ pom.xml
				git.rm().addFilepattern("-r src/ pom.xml").call();

				
				// Add the application archive
				
				//Specify the copy destination in the Git repository
				//This depends on the type of the used environment
				
				//TODO consider the other types!!
				String copydestination="deployments/";
				if (env.getContainerNames().get(0).toLowerCase().contains("jboss"))
					copydestination="deployments/";

				String deployableDirectory = app.getDeployable()
						.getDeployableDirectory();
				String deployableName = app.getDeployable().getDeployableName();
				copy(deployableDirectory + "/" + deployableName, localPath + copydestination +
						 deployableName);

				git.add().addFilepattern(".").call();
				
				//Commit changes
			    git.commit()
		           .setMessage("Changed by the OS-PaaS API")
		           .call();
			    
			    //Push
			    git.push()
		           .call();
			    
				// removes the directory before exiting
				theDir = new File(localPath);
				System.out.println("Removing Existing directory: "
						+ localPath);
				removeDirectory(theDir);

				return Response.status(Response.Status.OK).entity(app)
						.type(MediaType.APPLICATION_XML_TYPE).build();
			}
		} catch (Exception e) {
			System.out.println("Failed to deploy the application: "
					+ e.getMessage());
			e.printStackTrace();
			if(e.getMessage().contains("already exists and is not an empty directory"))
				error.setValue("Failed to deploy the application: "
						+ e.getMessage()+"\n Delete the"+ localPath + "folder");
			else
				error.setValue("Failed to deploy the application: "
					+ e.getMessage());
			if (theDir!=null && theDir.exists())
				removeDirectory(theDir);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error).type(MediaType.APPLICATION_XML_TYPE).build();
		}
	}

	// private methods
	private synchronized Long getNextId() {
		return new Long(envPool.INSTANCE.getNextID());
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

	public static boolean removeDirectory(File directory) {
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
		      if (entry.isDirectory())
		      {
		        if (!removeDirectory(entry))
		          return false;
		      }
		      else
		      {
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
	
	

}
