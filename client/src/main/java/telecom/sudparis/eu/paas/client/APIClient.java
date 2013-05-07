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
package telecom.sudparis.eu.paas.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataMultiPart;

/**
 * Servlet implementation class APIClient
 */
public class APIClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String apiLocation;
	/**
	 * The temp folder used to store uploaded files
	 */
	private static String localTempPath = System.getProperty("java.io.tmpdir");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public APIClient() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// use a session to save appName from manifest while creating
		// application and the different parameters selected in the form
		HttpSession session = request.getSession(true);
		// session timeout 20 minutes
		session.setMaxInactiveInterval(20 * 60);
		List items = null;
		// Parse the request
		try {
			// Check that we have a file upload request
			// boolean isMultipart =
			// ServletFileUpload.isMultipartContent(request);

			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// the method name
		String method = null;
		// the method path
		String path = null;
		// the request body
		String body = null;

		// the file name
		String fileName = null;
		// the file Item
		FileItem file2upload = null;

		// Process the uploaded items
		Iterator iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();

			if (item.isFormField()) {
				String name = item.getFieldName();

				if (name.equals("method"))
					method = item.getString();
				else if (name.equals("path"))
					path = item.getString();
				else if (name.equals("body"))
					body = item.getString();
				else if (name.equals("apiLocation")) {
					apiLocation = item.getString();
					// Format the api location
					apiLocation = apiLocation.trim();
				}
			} else {
				fileName = item.getName();
				file2upload = item;
			}
		}

		if (!apiLocation.endsWith("/"))
			apiLocation = apiLocation + "/";
		session.setAttribute("apiLocation", apiLocation);

		if (method == null || method.equals("") || path == null
				|| path.equals("")) {
			request.setAttribute("status", 404);
			request.setAttribute("output",
					"Select an Action from the proposed list.");
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		} else {

			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			client.setConnectTimeout(0);
			WebResource service = client.resource(getBaseURI());

			// Get the type of the methods (i.e. GET, POST,...)
			String[] methodSplit = method.split("-");
			method = methodSplit[0];

			ClientResponse cr = null;

			if (method.equals("GET")) {
				cr = service.path(path).type(MediaType.APPLICATION_XML)
						.get(ClientResponse.class);
			} else if (method.equals("POST")) {
				// the deploy is a particular case, since we have to upload the
				// deployable files
				if (path.contains("deploy") && !path.contains("undeploy")) {
					// the file to upload
					InputStream uploadedStream = null;
					uploadedStream = file2upload.getInputStream();

					// String deployableName=(String)
					// session.getAttribute("deployableName");
					if (uploadedStream == null) {
						request.setAttribute("status", 404);
						request.setAttribute(
								"output",
								"The deployableName was not found! Possible cause: Application is not yet cretaed.");
						request.getRequestDispatcher("/index.jsp").forward(
								request, response);
					} else {
						// temp destination path
						fileName = localTempPath + '/' + fileName;
						// write the inputStream to a FileOutputStream
						File f = new File(fileName);
						
							OutputStream out = new FileOutputStream(f);
						
						int read = 0;
						byte[] bytes = new byte[1024];

						while ((read = uploadedStream.read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}

						uploadedStream.close();
						out.flush();
						out.close();


						FormDataMultiPart form = new FormDataMultiPart().field(
								"file", f, MediaType.MULTIPART_FORM_DATA_TYPE);
						cr = service.path(path)
								.type(MediaType.MULTIPART_FORM_DATA)
								.post(ClientResponse.class, form);
					}
				}// POST app is the creat app operation
				else if (path.equals("app")) {
					// parse the manifest to get the file location
					// String deployableName = getDeployableName(body);
					// session.setAttribute("deployableName", deployableName);
					cr = service.path(path).type(MediaType.APPLICATION_XML)
							.entity(body).post(ClientResponse.class);
				}
				// other POST methods
				else
					cr = service.path(path).type(MediaType.APPLICATION_XML)
					.entity(body).post(ClientResponse.class);
			} else if (method.equals("DELETE")) {
				cr = service.path(path).type(MediaType.APPLICATION_XML)
						.delete(ClientResponse.class);
			}
			

			request.setAttribute("status", cr.getStatus());

			if (cr.getStatus() == 200 || cr.getStatus() == 202)// if the
				// response will be an xml descriptor, format it
				request.setAttribute("output",
						prettyFormat(cr.getEntity(String.class), 2));
			else
				request.setAttribute("output", cr.getEntity(String.class));
			
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		}
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(apiLocation).build();
	}

	private static String prettyFormat(String input, int indent) {
		try {
			Source xmlInput = new StreamSource(new StringReader(input));
			StringWriter stringWriter = new StringWriter();
			StreamResult xmlOutput = new StreamResult(stringWriter);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			transformerFactory.setAttribute("indent-number", indent);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.transform(xmlInput, xmlOutput);
			return xmlOutput.getWriter().toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
