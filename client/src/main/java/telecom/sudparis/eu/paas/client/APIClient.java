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

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Servlet implementation class APIClient
 */
public class APIClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int paas;

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

		// récupérer la nom de la méthode
		String method = request.getParameter("method");
		// récupérer le path de la méthode
		String path = request.getParameter("path");
		// récupérer le body de la requête
		String body = request.getParameter("body");
		// récupérer le nom du paas (si 0 CloudFoundry, si 1 Openshift, si -1 non spécifié)
		paas = Integer.parseInt(request.getParameter("paas"));

		if (method == null || method.equals("") || path == null
				|| path.equals("")) {
			request.setAttribute("status", 404);
			request.setAttribute("output",
					"Select an Action from the proposed list.");
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		} else if (paas==-1) {
			request.setAttribute("status", 404);
			request.setAttribute("output",
					"Select a PaaS Solution from the proposed list.");
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);					
				}
		else {

			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			client.setConnectTimeout(0);
			WebResource service = client.resource(getBaseURI());

			// ServletOutputStream out=response.getOutputStream();

			// récupérer le type de la méthode (i.e. GET, POST,...)
			String[] methodSplit = method.split("-");
			method = methodSplit[0];

			ClientResponse cr = null;
			String output = null;
			if (method.equals("GET")) {
				cr = service.path(path).type(MediaType.APPLICATION_XML)
						.get(ClientResponse.class);
			} else if (method.equals("POST")) {
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
		if (paas == 1)
			return UriBuilder.fromUri("http://localhost:8080/OS-api/rest")
					.build();
		else
			return UriBuilder.fromUri("http://localhost:8080/CF-api/rest")
					.build();
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
