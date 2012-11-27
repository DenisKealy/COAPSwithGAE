<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <body>
  <% java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("telecom.sudparis.eu.paas.core.server.ressources.credentials");
 %>
  <h1> The Openshift Implementation of COAPS is now running. </h1>
  <h3> See the WADL description at: <a href=http://localhost:8080/OS-api/rest/application.wadl> http://localhost:8080/OS-api/rest/application.wadl </a></h3>
  <h4> By default, This API implementation is connected on <b><%out.print(rb.getString("vcap.target")); %></b> as <b><%out.print(rb.getString("vcap.email")); %>.</b></h3>
  <h4> These parameters can be updated from: <b>telecom.sudparis.eu.paas.core.server.ressources.credentials.properties</b></h4>
 </body>
</html>
