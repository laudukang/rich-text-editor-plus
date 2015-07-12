# EditorDemo
ueditor demo jsp/ckeditor demo jsp/kindeditor demo jsp

#ueditor demo修改说明

1. 表情文件本地化，images目录下的所有表情文件夹复制到dialogs/emotion/images/文件夹下面，修改editor_config.js文件，去掉 emotionLocalization 项的注释，值改为true。

2. 列表文件本地化，解压放到你的themes/文件夹下（可以按照需求放置路径），修改editor_config.js文件，修改listiconpath配置项：
   
 `listiconpath : URL+'themes/ueditor-list/'  //如果是自己的目录，请使用  '/'开头的绝对路径`

 在发布文章的页面，引用uparse.js，并运行 uParse 函数，传入列表路径：
    `<script type="text/javascript">
    uParse('.content',{
        'liiconpath':'/UTest/ueditor/themes/ueditor-list/'    //使用 '/' 开头的绝对路径
    })
	</script>`

3. 升级UEditor的SyntaxHighlighter，ueditor默认使用了SyntaxHighlighter的Default主题，更多配置请参见http://www.crarun.com/article-3.html

4. 更改了ueditor的默认上传位置，使用了tomcat的虚拟路径，避免重新部署后丢失先前的文件，这里修改了com.baidu.ueditor包下的ConfigManager.java类，主要是对ueditor.config.js虚拟路径配置的处理，还修改了com.baidu.ueditor.hunter包下的FileManager.java和com.baidu.ueditor.upload包下的BinaryUploader.java，主要是对配置了tomcat虚拟路径后的处理，特别地，修改了原图片在线管理/文件在线附件的获取，原ueditor可以获取文件的个数并显示出来，但图片/文件显示不了，因为其封装的路径有文件，原ueditor的封装使用了绝对路径，即把
`D:\Tomcat 7.0.57\webapps\EditorDemo\upload\image\20150711\1436591786822080766.jpg`
这样的文件路径返回前台，这样子前台ueditor是获取不了文件的，所以改成了相对路径，见FileManager类下的getPath (File)方法；关于tomcat虚拟路径的配置，修改tomcat conf目录下的server.xml，在Host里面添加:
`<Context path="/EditorDemo/ueditorupload" docBase="D:\apache\EditorDemo\ueditor\ueditorupload"/>`；这部分内容参考了http://blog.csdn.net/will_awoke/article/details/39579061

5. 是否保存上传文件到虚拟路径的配置请参见ueditor/jsp/下的config.json，xxxRealMappingPath指定了物理路径的位置

6. 修改百度map组件的默认位置为广东广州

7. 该版本基于ubuilder_1_4_3-utf8-jsp修改

8. ueditor demo这部分的最后修改时间2015年7月11日22:31:04



#kindeditor demo修改说明
1. 基于kindeditor-4.1.10修改
2. 修改代码高亮为SyntaxHighlighter插件
3. 添加插入附件的文件类型图标，用于插入附件后在前面显示
4. 修改了kindeditor/jsp文件夹下的file_manager_json.jsp和upload_json.jsp文件，用于上传文件/文件管理器的支持
5. 添加/修改了jwplayer插件，用于播放流媒体文件
6. 虚拟路径
   `<Context path="/EditorDemo/kindeditorupload" docBase="D:\apache\EditorDemo\kindeditor\kindeditorupload"/>`
7. kindeditor demo这部分的最后修改时间2015年7月12日01:30:29


#ckeditor demo修改说明
1. 基于ckeditor_4.5.1_full修改
2. 添加了jsp/browse.jsp和jsp/upload.jsp两个文件，分别用于浏览服务器文件和上传图片/附件到服务器，config.js配置了这两个文件的调用，修改返回路径为服务器相对路径saveUrl，修改上传文件新文件名为当前日期+随机码，tomcat需要配置虚拟路径\
	`<Context path="/EditorDemo/ckeditorupload" docBase="D:\apache\EditorDemo\ckeditor\ckeditorupload"/>`
3. ckeditor demo这部分的最后修改时间2015年7月12日12:11:55
