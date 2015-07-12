<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>ckeditor demo</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="ckeditor demo">
	<meta http-equiv="description" content="ckeditor demo">
	<script src="ckeditor/ckeditor.js"></script>
	<script src="ckeditor/samples/js/sample.js"></script>
	<link rel="stylesheet" href="ckeditor/samples/css/samples.css">
<script type="text/javascript">
function getData(){
	alert("获取HTML原生代码：\n"+CKEDITOR.instances.editor.getData());
	
}

</script>
  </head>
  
  <body>
   	<br><h3>ckeditor demo &nbsp;&nbsp;<input type="button" value="获取HTML原生代码" onclick="getData()"></h3>
    <div id="editor">
			
	</div>
	
	
	<script>
	initSample();
	</script>
  </body>
</html>
