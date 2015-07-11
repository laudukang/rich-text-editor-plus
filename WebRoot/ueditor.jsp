<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>ueditor demo</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content=ueditor>
	<meta http-equiv="description" content="ueditor demo">
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.parse.js"></script>
    
    <style type="text/css">
        div{
            width:100%;
        }
    </style>

  </head>
  
  <body>
    <br>
  <h3>UEditor Demo&nbsp;&nbsp;/&nbsp;&nbsp;<a href="ueditor/">UEditor Demo 带事件</a></h3>
    <div>
    <script id="editor" type="text/plain" style="width:1024px;height:500px;"></script>
  	</div id="content">
  	<br>
  
 	<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    
    /* UE.sh_config.sh_js="shCore.js";    //语法解析文件
    UE.sh_config.sh_theme="Default";      //语法高亮主题,third-party里面只添加了Default主题的js、css,更多请参见http://www.crarun.com/article-3.html，laudukang，2015年7月11日22:06:08
    									  //一共有8中主题可选：Default,Django,Eclipse,Emacs,FadeToGrey,MDUltra,Midnight,RDark
    uParse(".content",{rootPath: "/ueditor"}); */
 	</script>
 
  </body>
</html>
