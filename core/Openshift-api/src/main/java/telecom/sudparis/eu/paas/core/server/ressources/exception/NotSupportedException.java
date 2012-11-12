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
		super(Response.status(Response.Status.NOT_ACCEPTABLE).entity("The requested operation is not supported by Openshift")
				.type(MediaType.APPLICATION_XML).build());
	}
}


