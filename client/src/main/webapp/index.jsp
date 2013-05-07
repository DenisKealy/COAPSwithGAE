<html>
 <head>
  <title> The *-PaaS API Web client</title>
  <script type='text/javascript' src='http://code.jquery.com/jquery-1.7.1.js'></script>
<!-- Code Mirror -->
<link rel="stylesheet" href="ressources/codeMirror/lib/codemirror.css">
    <script src="ressources/codeMirror/lib/codemirror.js"></script>
    <script src="ressources/codeMirror/mode/xml/xml.js"></script>
    <style type="text/css">.foo{border-right: 1px solid red} .CodeMirror {border-top: 1px solid black; border-bottom: 1px solid black;}</style>
    <link rel="stylesheet" href="ressources/codeMirror/doc/docs.css">
    <link rel="stylesheet" href="ressources/codeMirror/theme/rubyblue.css">
    <link rel="stylesheet" href="ressources/codeMirror/theme/eclipse.css">
    <script src="ressources/codeMirror/lib/util/formatting.js"></script>

<!-- for the table -->
<style type="text/css">
table {border: 1px solid;overflow:hidden; width:whatever its width is in pixels, percent, or em; margin: 0 auto;}
</style>
<script type='text/javascript'>//<![CDATA[ 
$(window).load(function(){
var timesSelectClicked = 0;


$(function() {
    $(document).on('click keypress', 'select', function (e) {
    var selectedValue=document.getElementById('method').value;
        if (timesSelectClicked == 0)
        {
            timesSelectClicked += 1;
        }
        else if (timesSelectClicked == 1)
        {
            timesSelectClicked = 0;
            if (selectedValue=="POST-createApp")
               document.getElementById('path').value='app';
            else if(selectedValue=="POST-updateApp")
               document.getElementById('path').value='app/{appId}/update';    
            else if(selectedValue=="GET-findApps")
               document.getElementById('path').value='app';
            else if(selectedValue=="GET-describeApp")
               document.getElementById('path').value='app/{appId}';  
            else if(selectedValue=="POST-startApp")
               document.getElementById('path').value='app/{appId}/start';   
            else if(selectedValue=="POST-stopApp")
               document.getElementById('path').value='app/{appId}/stop';
            else if(selectedValue=="POST-restartApp")
                   document.getElementById('path').value='app/{appId}/restart';               
            else if(selectedValue=="DELETE-deleteApp")
               document.getElementById('path').value='app/{appId}/delete'; 
           else if(selectedValue=="DELETE-deleteApps")
               document.getElementById('path').value='app/delete';
           else if(selectedValue=="POST-deployApp")
               document.getElementById('path').value='app/{appId}/action/deploy/env/{envId}'; 
           else if(selectedValue=="POST-undeployApp")
                   document.getElementById('path').value='app/{appId}/action/undeploy/env/{envId}';                     
           else if(selectedValue=="POST-createEnv")
               document.getElementById('path').value='environment';
           else if(selectedValue=="POST-updateEnv")
               document.getElementById('path').value='environment/{envId}/update';
           else if(selectedValue=="DELETE-deleteEnv")
               document.getElementById('path').value='environment/{envId}';     
           else if(selectedValue=="GET-findEnvs")
               document.getElementById('path').value='environment';           
           else if(selectedValue=="GET-getEnv")
               document.getElementById('path').value='environment/{envId}';      
           else if(selectedValue=="GET-getDepApps")
               document.getElementById('path').value='environment/{envId}/app';
           else if(selectedValue=="GET-getInformations")
               document.getElementById('path').value='environment/info';    
                      
         /* if(selectedPaaS=="0")
          	document.getElementById('apiLocation').value='http://localhost:8080/CF-api/rest';
          else if(selectedPaaS=="1")
          	document.getElementById('apiLocation').value='http://localhost:8080/OS-api/rest';     */    
        }
    });
});
});//]]>  

</script>

 </head>
 <body bgcolor="#3E7087">

<div style="text-align: center;"> </div>
 <div align="center"><h2><font color="white">*-PaaS API Web client</font></h2><h4> </h4> </div>
<form method="post" action="/client/APIClient" style="height: 959px; " enctype="multipart/form-data">
 <div align="center">
	<table border="0" bordercolor="white" bgcolor="#1F4661" width="60%" cellpadding="3" cellspacing="3" style="height: 948px; ">
	
	<tr><td><font color="white">*-PaaS  API location: </font></td><td>&nbsp;</td> <td>	
		<%
	String apiLocation=(String)session.getAttribute("apiLocation");
	if (apiLocation==null)
		apiLocation="http://localhost:8080/CF-api/rest";
	 %>
	<input type=text name="apiLocation" id="apiLocation" size="70" style="width: 589px; " value="<%=apiLocation%>">
	<br><font color="white"><small><i>the default location for the CF-PaaS api</i>:  http://localhost:8080/CF-api/rest</small></font>
	<br><font color="white"><small><i>the default location for the OS-PaaS api</i>:  http://localhost:8080/OS-api/rest</small></font>
	</td></tr>
	<tr>
		<td><font color="white">Action: </font></td>
		<td>&nbsp;&nbsp;&nbsp;</td>
		<td><select name="method" id="method" style="width: 589px; ">
	
			<optgroup label="Application Manager">
			<option value="POST-createApp"> createApplication(String appDescriptor)</option>
			
			<option value="POST-startApp"> startApplication(String appId)</option>
			<option value="POST-stopApp"> stopApplication(String appId)</option>
			<option value="POST-restartApp"> restartApplication(String appId)</option>

			<option value="POST-deployApp"> deployApplication(String appId,String envId)</option>
			<option value="POST-undeployApp"> unDeployApplication(String appId,String envId)</option>
			
			<option value="POST-updateApp"> updateApplication(String appId,String appDescriptor)</option>
						
			<option value="GET-findApps"> findApplications() </option>
			<option value="GET-describeApp"> describeApplication(String appId)</option>

			<option value="DELETE-deleteApp"> deleteApplication(String appId)</option>
			<option value="DELETE-deleteApps"> deleteApplications()</option>
			</optgroup>
			
			<optgroup label="Environment Manager">
				<option value="POST-createEnv"> createEnvironment(String envDescriptor)</option>
				<option value="POST-updateEnv"> updateEnvironment(String envId,String envDescriptor)</option>
				<option value="DELETE-deleteEnv"> deleteEnvironment(String envId)</option>
				<option value="GET-findEnvs"> findEnvironments()</option>
				<option value="GET-getEnv"> getEnvironment(String envId)</option>
				<option value="GET-getDepApps"> getDeployedApplications(String envId)</option>
				<option value="GET-getInformations"> getInformations()</option>
			</optgroup>
		</select></td>
	</tr>
	
	<tr><td><font color="white">Select the application artifacts:</font></td><td>&nbsp;</td> 
	<td><input type="file" name="file" size="45" style="width: 590px; "/> 
	<br><font color="white"><small><i>When choosing the deploy Action, you have to specify you artifacts here (e.g. WAR file)</i></small></font>
	
</td>
	
	</tr>

	<tr>
		<td><font color="white">Path: </font></td>
		<td></td>
		<td><input type="text" name="path" id="path" size="70" style="width: 589px; "></td>
	</tr>

	<tr><td></td><td>&nbsp;</td> </tr>

	<tr>
		<td><font color="white">Request <br>Body: </font></td>
		<td></td>
		<td><div style="width: 589px; border:solid 1px black;">
		<textarea style="background:white" id="input" name="body" rows="5" cols="70"></textarea></div></td>
	</tr>

	<tr><td>&nbsp; </td><td>&nbsp;</td> </tr>

	<tr>
		<td><font color="white">Status code: </font></td>
		<td></td>
		<td><div style="width: 589px; border:solid 1px black;"><h2>
		<%
		Object status=request.getAttribute("status");
		if(status!=null){
			if (status.toString().equals("500"))
				out.print("<font color=red>"+status.toString()+"</font>");
			else if (status.toString().equals("200"))
				out.print("<font color=green>"+status.toString()+" OK </font>");
			else
				out.print("<font color=green>"+status.toString()+"</font>");
		}		
		%>
		</h2></div></td>
	</tr>

	<tr style="height: 18px; "><td>&nbsp;</td><td>&nbsp;</td> </tr>

	<tr>
		<td><font color="white">Response<br>Body: </font></td>
		<td></td>
		<td><div style="width: 589px; border:solid 1px black;"> 
		<textarea id="output" name="output" rows="5" cols="70" style="width: 587px"><%
		Object output=request.getAttribute("output");
		if(output!=null)
			out.print(output.toString());		
		%></textarea></div></td>
	</tr>
		<tr>
		<td></td>
		<td></td>
		<td align=right style="height: 52px; "><input type="submit" value="Submit"></td>
	</tr>
	</table>
	</div>
  </form>	
    <script>
      var editor = CodeMirror.fromTextArea(document.getElementById("output"), {
        mode: {name: "xml", alignCDATA: true},
        lineNumbers: true, theme: "rubyblue"
      });
           var editor2 = CodeMirror.fromTextArea(document.getElementById("input"), {
        mode: {name: "xml", alignCDATA: true},
        lineNumbers: false, theme: "eclipse", indentWithTabs: true, indentUnit: 4
  
      });
      
    </script>	
 </body>
</html>
