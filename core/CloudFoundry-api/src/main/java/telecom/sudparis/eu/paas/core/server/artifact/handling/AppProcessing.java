package telecom.sudparis.eu.paas.core.server.artifact.handling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class AppProcessing {

	String host;
	int port;
	String user;
	String pwd;
	String physicalPath;
	String logicalPath;
	String pathToFileDB;
	String fileDB;

	Session session = null;
	Channel channel = null;
	ChannelSftp channelSftp = null;
	private static ResourceBundle rb = ResourceBundle
			.getBundle("telecom.sudparis.eu.paas.core.server.ressources.credentials");

	public AppProcessing() {
		host = rb.getString("deployable.host");
		port = Integer.parseInt(rb.getString("deployable.port"));
		user = rb.getString("deployable.user");
		pwd = rb.getString("deployable.pwd");
		physicalPath = rb.getString("deployable.physicalPath");
		logicalPath = rb.getString("deployable.logicalPath");
		fileDB = rb.getString("deployable.fileDB");
	}

	public AppProcessing(String path) {
		host = rb.getString("deployable.host");
		port = Integer.parseInt(rb.getString("deployable.port"));
		user = rb.getString("deployable.user");
		pwd = rb.getString("deployable.pwd");
		physicalPath = rb.getString("deployable.physicalPath");
		logicalPath = rb.getString("deployable.logicalPath");
		fileDB = rb.getString("deployable.fileDB");
		this.pathToFileDB = path + "/" + fileDB;
	}

	protected void setPathToFileDB(String path) {

		this.pathToFileDB = path + fileDB;
	}

	public AppObj uploadAppToRemoteServer(InputStream uploadedInputStream,
			String deployableName) {

		AppObj app = null;

		try {

			// Get the uploaded file parameters
			// String fieldName = fi.getFieldName();
			String localFileName = deployableName;
			// String contentType = fi.getContentType();

			// generate an UUID for the file
			String logicalFileName = UUID.randomUUID().toString();
			String remoteName = logicalFileName + "-" + localFileName;

			String method = "wget http://" + host + logicalPath + remoteName;

			app = new AppObj(logicalFileName, "", "", logicalFileName,
					localFileName, remoteName, -1, method);

			// connect to the remote host
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(pwd);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;

			// change directory
			channelSftp.cd(physicalPath);

			// transfer the file
			channelSftp.put(uploadedInputStream, remoteName);

			// update the database
			AppList apps = readDBFileToAppList();
			apps.addAppToList(app);
			writeAppListToDBFile(apps);

			// System.out.println("File transfered: "+remoteName);
		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return app;
	}

	public InputStream getRemoteAppStreamByID(String appID) {
		InputStream is = null;

		// load database
		AppList apps = readDBFileToAppList();
		AppObj app = apps.searchForAppObj(appID);

		if (app != null)
			try {
				// connect to the remote host
				JSch jsch = new JSch();
				session = jsch.getSession(user, host, port);
				session.setPassword(pwd);
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				session.connect();
				channel = session.openChannel("sftp");
				channel.connect();
				channelSftp = (ChannelSftp) channel;

				// change directory
				channelSftp.cd(physicalPath);

				// get remote file
				is = channelSftp.get(app.getRemoteName());

				System.out.println(app.getRemoteName());

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		return is;
	}

	protected AppObj searchAppByID(String appID) {
		AppList apps = readDBFileToAppList();
		return apps.searchForAppObj(appID);
	}

	protected boolean writeAnInputStreamToFile(InputStream is,
			String destFileName) {
		try {
			// write the inputStream to a FileOutputStream
			OutputStream os = new FileOutputStream(new File(destFileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}

			is.close();
			os.flush();
			os.close();

			System.out.println("New file created!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	protected boolean deleteAnAppByID(String appID) {
		// load database
		AppList apps = readDBFileToAppList();
		AppObj app = apps.searchForAppObj(appID);

		if (app != null) {

			try {
				// connect to the remote host
				JSch jsch = new JSch();
				session = jsch.getSession(user, host, port);
				session.setPassword(pwd);
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				session.connect();
				channel = session.openChannel("sftp");
				channel.connect();
				channelSftp = (ChannelSftp) channel;

				// change directory
				channelSftp.cd(physicalPath);

				// remove file at remote host
				// System.out.println(app.getLogicalName());
				channelSftp.rm(app.getRemoteName());

				// update the database
				apps.deleteApp(app);
				writeAppListToDBFile(apps);

				// System.out.println("File transfered: "+remoteName);
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		return false;
	}

	protected boolean deleteAllApps() {

		try {
			// connect to the remote host
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(pwd);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;

			// change directory
			channelSftp.cd(physicalPath);

			// remove file at remote host
			// System.out.println(app.getLogicalName());
			channelSftp.rm("*");

			// update the database
			writeAppListToDBFile(new AppList());

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}

	protected AppList readDBFileToAppList() {
		AppList appList = new AppList();

		// create JAXB context and instantiate marshaller
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(AppList.class);
			Unmarshaller um = context.createUnmarshaller();
			try {
				System.out.println(pathToFileDB);
				appList = (AppList) um.unmarshal(new FileReader(pathToFileDB));
				// System.out.println(app1.getLogicalName());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return appList;
	}

	protected void writeAppListToDBFile(AppList listApps) {

		// create JAXB context and instantiate marshaller
		JAXBContext context;

		// System.out.println(fileToStoreDB);
		try {
			context = JAXBContext.newInstance(AppList.class);

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out
			m.marshal(listApps, System.out);

			// Write to File
			m.marshal(listApps, new File(pathToFileDB));

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
