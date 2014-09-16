package telecom.sudparis.eu.paas.core.server.test.fci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class TestResourceBundle {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ResourceBundle rb=ResourceBundle.getBundle("telecom.sudparis.eu.paas.core.server.ressources.credentials");//("com.resources.test");
		System.out.println(rb.getString("vcap.target"));
		System.out.println(rb.getString("vcap.email"));
		System.out.println(rb.getString("vcap.passwd"));
		System.out.println(rb.getString("host"));
		String localTempPath = System.getProperty("java.io.tmpdir");
		System.out.println(localTempPath);
		
	}

}
