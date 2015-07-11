<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>kindeditor demo</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="kindeditor">
	<meta http-equiv="description" content="kindeditor demo">
	<link rel="stylesheet" type="text/css"
	href="kindeditor/themes/default/default.css" />
	<link rel="stylesheet" type="text/css"
	href="kindeditor/plugins/code/prettify.css" />
	 <script src="kindeditor/kindeditor.js"></script>
	<script src="kindeditor/lang/zh_CN.js"></script>
	<script src="kindeditor/plugins/code/prettify.js"></script>
	<script src="kindeditor/plugins/flash/jwplayer.js"></script>
<script type="text/javascript">

var FullEditor;

KindEditor.ready(function(K) {
    FullEditor = K.create('textarea[name="publish_textarea"]', {
        cssPath : 'kindeditor/plugins/code/prettify.css',
        uploadJson : 'kindeditor/jsp/upload_json.jsp',
        fileManagerJson :  'kindeditor/jsp/file_manager_json.jsp',
        allowFileManager : true,
        autoHeightMode : true,
        minHeight : 360,
        afterCreate : function() {
            prettyPrint();
            var self = this;
            K.ctrl(document, 13, function() {
                self.sync();
                sendNewPublishToAction();
            });
            K.ctrl(self.edit.doc, 13, function() {
                self.sync();
                sendNewPublishToAction();
            });
            this.loadPlugin('autoheight');
        }
    });
});

function showContent(){
	alert("HTML原生代码：\n"+FullEditor.html());
}

</script>
  </head>
  
  <body>
    <br>
    <h3>kindeditor demo</h3>
    <br>
    <textarea name="publish_textarea" id="publish_textarea" cols="100"
			rows="14"
			style="width:98%;height:300px;visibility:hidden;min-width:800px;"></textarea>
    <br><br><input type="button" value="获取HTML原生代码" onclick="showContent()"/>
   
  </body>
</html>
