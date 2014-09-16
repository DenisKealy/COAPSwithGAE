package war;

import java.util.ResourceBundle;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ResourceBundle rb=ResourceBundle.getBundle("telecom.sudparis.eu.paas.core.server.ressources.credentials");//("com.resources.test");
		System.out.println(rb.getString("vcap.target"));
		System.out.println(rb.getString("vcap.email"));
		System.out.println(rb.getString("vcap.passwd"));
		System.out.println(rb.getString("host"));
		String localTempPath = System.getProperty("java.io.tmpdir");
		
    }
}
