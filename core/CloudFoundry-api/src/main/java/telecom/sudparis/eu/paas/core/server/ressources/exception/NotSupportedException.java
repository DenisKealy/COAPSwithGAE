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
package telecom.sudparis.eu.paas.core.server.ressources.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Mohamed Sellami (Telecom SudParis)
 * 
 */
public class NotSupportedException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public NotSupportedException(String msg) {
		super(Response.status(Response.Status.NOT_ACCEPTABLE).entity(msg)
				.type(MediaType.APPLICATION_XML).build());
	}

	public NotSupportedException() {
		super(
				Response.status(Response.Status.NOT_ACCEPTABLE)
						.entity("The requested operation is not supported by CloudFoundry")
						.type(MediaType.APPLICATION_XML).build());
	}
}
