<html>
 <head>
  <title> The COAPS Client</title>
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
            else if(selectedValue=="POST-createAppVerIns")
               document.getElementById('path').value='app/{appId}/version/1/instance';
			else if(selectedValue=="POST-createAppVer")
               document.getElementById('path').value='app/{appId}/version/create';              
            else if(selectedValue=="GET-findApps")
               document.getElementById('path').value='app';
            else if(selectedValue=="GET-findAppVer")
               document.getElementById('path').value='app/{appId}/version';  
            else if(selectedValue=="GET-findAppVerIns")
               document.getElementById('path').value='app/{appId}/version/1'; 
            else if(selectedValue=="GET-describeApp")
               document.getElementById('path').value='app/{appId}';  
            else if(selectedValue=="POST-startAppVerIns")
               document.getElementById('path').value='app/{appId}/version/1/instance/1/action/start';   
            else if(selectedValue=="POST-stopAppVerIns")
               document.getElementById('path').value='app/{appId}/version/1/instance/1/action/stop';
            else if(selectedValue=="DELETE-deleteApp")
               document.getElementById('path').value='app/{appId}/delete'; 
           else if(selectedValue=="DELETE-deleteApps")
               document.getElementById('path').value='app/delete'; 
           else if(selectedValue=="POST-createEnv")
               document.getElementById('path').value='environment';
           else if(selectedValue=="DELETE-deleteEnv")
               document.getElementById('path').value='environment/{envId}';     
           else if(selectedValue=="GET-findEnvs")
               document.getElementById('path').value='environment';     
           else if(selectedValue=="POST-startEnv")
               document.getElementById('path').value='environment/{envId}/action/start';    
           else if(selectedValue=="POST-stopEnv")
               document.getElementById('path').value='environment/{envId}/action/stop';       
           else if(selectedValue=="POST-deployApp")
               document.getElementById('path').value='environment/{envId}/action/deploy/app/{appId}/version/1/instance/1';  
           else if(selectedValue=="GET-getEnv")
               document.getElementById('path').value='environment/{envId}';      
           else if(selectedValue=="GET-getDepAppVerIns")
               document.getElementById('path').value='environment/{envId}/app';        
        }
    });
});
});//]]>  

</script>

 </head>
 <body bgcolor="#3E7087">

<div style="text-align: center;"> </div>
 <div align="center"><h2><font color="white">COmpatible Application Platform Service (COAPS)</font></h2><h4><font color="white">(<i>The Cloud Foundry & Openshift implementations</i>)</font> </h4> </div>
<form method="post" action="/client/APIClient" style="height: 959px; ">
 <div align="center">
	<table border="0" bordercolor="white" bgcolor="#1F4661" width="60%" cellpadding="3" cellspacing="3" style="height: 948px; ">
	<tr><td><font color="white">PaaS Solution: </font></td><td>&nbsp;</td> 
	<td>
	<select name="paas" id="paas" style="width: 589px; ">
	<option id="ns" value="-1">  </option>	
	<option id="cf" value="0"> Cloud Foundry </option>
	<option id="os" value="1"> Openshift </option>
	</select>
	</td>
	</tr>
	<tr><td>&nbsp;</td><td>&nbsp;</td> <td>	</td></tr>
	<tr>
		<td><font color="white">Action: </font></td>
		<td>&nbsp;&nbsp;&nbsp;</td>
		<td><select name="method" id="method" style="width: 589px; ">
	
			<optgroup label="Application Manager">
			<option value="POST-createApp"> createApplication(String appDescriptor)</option>
			<option value="POST-createAppVer"> createApplicationVersion(String appId,String appVerDescriptor);</option>
			<option value="POST-createAppVerIns"> createApplicationVersionInstance(String appId,String appVerInstDescriptor)</option>
			
			<option value="GET-findApps"> findApplications() </option>
			<option value="GET-findAppVer"> findApplicationVersions(String appId) </option>
			<option value="GET-findAppVerIns"> findApplicationVersionInstances(String appId,String versionId) </option>
			<option value="GET-describeApp"> describeApplication(String appId)</option>

			<option value="POST-startAppVerIns"> startApplicationVersionInstance(String appId)</option>
			<option value="POST-stopAppVerIns"> stopApplicationVersionInstance(String appId)</option>

			<option value="DELETE-deleteApp"> deleteApplication(String appId)</option>
			<option value="DELETE-deleteApps"> deleteApplications()</option>
			</optgroup>
			
			<optgroup label="Environment Manager">
				<option value="POST-createEnv"> createEnvironment(String envDescriptor)</option>
				<option value="DELETE-deleteEnv"> deleteEnvironment(String envId)</option>
				<option value="GET-findEnvs"> findEnvironments()</option>
				<option value="POST-startEnv"> startEnvironment(String envId)</option>
				<option value="POST-stopEnv"> stopEnvironment(String envId)</option>
				<option value="POST-deployApp"> deployApplication(String envId, String appId)</option>
				<option value="GET-getEnv"> getEnvironment(String envId)</option>
				<option value="GET-getDepAppVerIns"> getDeployedAppVerIns(String envId)</option>
			</optgroup>
		</select></td>
	</tr>
	
	<tr><td>&nbsp;</td><td>&nbsp;</td> </tr>

	<tr>
		<td><font color="white">Path: </font></td>
		<td></td>
		<td><input type="text" name="path" id="path" size=70 style="width: 589px; "></td>
	</tr>

	<tr><td>&nbsp;</td><td>&nbsp;</td> </tr>

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
