package com.baidu.ueditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.ueditor.define.ActionMap;

/**
 * 配置管理器
 * @author hancong03@baidu.com
 * 
 */
public final class ConfigManager {

	private  String rootPath;//去掉final，以便文件管理对rootPath进行自定义，laudukang，2015年7月11日19:52:26
	private final String originalPath;
	@SuppressWarnings("unused")
    private final String contextPath;
	private static final String configFileName = "config.json";
	private String parentPath = null;
	private JSONObject jsonConfig = null;
	// 涂鸦上传filename定义
	private final static String SCRAWL_FILE_NAME = "scrawl";
	// 远程图片抓取filename定义
	private final static String REMOTE_FILE_NAME = "remote";
	
	/*
	 * 通过一个给定的路径构建一个配置管理器， 该管理器要求地址路径所在目录下必须存在config.properties文件
	 */
	private ConfigManager ( String rootPath, String contextPath, String uri ) throws FileNotFoundException, IOException {
		
		rootPath = rootPath.replace( "\\", "/" );
		
		this.rootPath = rootPath;
		this.contextPath = contextPath;
		
		if ( contextPath.length() > 0 ) {
			this.originalPath = this.rootPath + uri.substring( contextPath.length() );
		} else {
			this.originalPath = this.rootPath + uri;
		}
		
		this.initEnv();
		
	}
	
	/**
	 * 配置管理器构造工厂
	 * @param rootPath 服务器根路径
	 * @param contextPath 服务器所在项目路径
	 * @param uri 当前访问的uri
	 * @return 配置管理器实例或者null
	 */
	public static ConfigManager getInstance ( String rootPath, String contextPath, String uri ) {
		
		try {
			return new ConfigManager(rootPath, contextPath, uri);
		} catch ( Exception e ) {
			return null;
		}
		
	}
	
	// 验证配置文件加载是否正确
	public boolean valid () {
		return this.jsonConfig != null;
	}
	
	public JSONObject getAllConfig () {
		
		return this.jsonConfig;
		
	}
	
	public Map<String, Object> getConfig ( int type ) {
		Map<String, Object> conf = new HashMap<String, Object>();
		String savePath = null;
		//是否使用虚拟路径映射 默认不使用
		boolean virtualPath = false;
		
		switch ( type ) {

		case ActionMap.UPLOAD_FILE:
				conf.put( "isBase64", "false" );
				conf.put( "maxSize", this.jsonConfig.getLong( "fileMaxSize" ) );
				conf.put( "allowFiles", this.getArray( "fileAllowFiles" ) );
				conf.put( "fieldName", this.jsonConfig.getString( "fileFieldName" ) );
				
				//for virtual path mapping 
				String filePathFormat = this.jsonConfig.getString("filePathFormat");
				String fileUsingVirtualPath = this.jsonConfig.getString("fileUsingVirtualPath");
				if("yes".equalsIgnoreCase(fileUsingVirtualPath)){
				    String imageRealMappingPath = this.jsonConfig.getString("fileRealMappingPath");
				    savePath = imageRealMappingPath + filePathFormat;
				    virtualPath = true;
				    conf.put( "realMappingPath", imageRealMappingPath);//put into conf map using key=realMappingPath
				}else if("no".equalsIgnoreCase(fileUsingVirtualPath)){
				    savePath = filePathFormat;
				}
				
				//savePath = this.jsonConfig.getString( "filePathFormat" );
				break;
				
			case ActionMap.UPLOAD_IMAGE:
				conf.put( "isBase64", "false" );
				conf.put( "maxSize", this.jsonConfig.getLong( "imageMaxSize" ) );
				conf.put( "allowFiles", this.getArray( "imageAllowFiles" ) );
				conf.put( "fieldName", this.jsonConfig.getString( "imageFieldName" ) );
				
				//for virtual path mapping 
				String imagePathFormat = this.jsonConfig.getString("imagePathFormat");
				String imageUsingVirtualPath = this.jsonConfig.getString("imageUsingVirtualPath");
				if("yes".equalsIgnoreCase(imageUsingVirtualPath)){
				    String imageRealMappingPath = this.jsonConfig.getString("imageRealMappingPath");
				    savePath = imageRealMappingPath + imagePathFormat;
				    virtualPath = true;
				    conf.put( "realMappingPath", imageRealMappingPath);//put into conf map using key=realMappingPath
				}else if("no".equalsIgnoreCase(imageUsingVirtualPath)){
				    savePath = imagePathFormat;
				}
				
				//savePath = this.jsonConfig.getString( "imagePathFormat" );
				break;
				
			case ActionMap.UPLOAD_VIDEO:
				conf.put( "maxSize", this.jsonConfig.getLong( "videoMaxSize" ) );
				conf.put( "allowFiles", this.getArray( "videoAllowFiles" ) );
				conf.put( "fieldName", this.jsonConfig.getString( "videoFieldName" ) );
				
				//for virtual path mapping 
                String videoPathFormat = this.jsonConfig.getString("videoPathFormat");
                String videoUsingVirtualPath = this.jsonConfig.getString("videoUsingVirtualPath");
                if("yes".equalsIgnoreCase(videoUsingVirtualPath)){
                    String videoRealMappingPath = this.jsonConfig.getString("videoRealMappingPath");
                    savePath = videoRealMappingPath + videoPathFormat;
                    virtualPath = true;
                    conf.put( "realMappingPath", videoRealMappingPath);//put into conf map using key=realMappingPath
                }else if("no".equalsIgnoreCase(videoUsingVirtualPath)){
                    savePath = videoPathFormat;
                }
                
				//savePath = this.jsonConfig.getString( "videoPathFormat" );
				break;
				
			case ActionMap.UPLOAD_SCRAWL:
				conf.put( "filename", ConfigManager.SCRAWL_FILE_NAME );
				conf.put( "maxSize", this.jsonConfig.getLong( "scrawlMaxSize" ) );
				conf.put( "fieldName", this.jsonConfig.getString( "scrawlFieldName" ) );
				conf.put( "isBase64", "true" );
				
				//for virtual path mapping 
				String scrawlPathFormat = this.jsonConfig.getString("scrawlPathFormat");
				String scrawlUsingVirtualPath = this.jsonConfig.getString("scrawlUsingVirtualPath");
				if("yes".equalsIgnoreCase(scrawlUsingVirtualPath)){
				    String scrawlRealMappingPath = this.jsonConfig.getString("scrawlRealMappingPath");
				    savePath = scrawlRealMappingPath + scrawlPathFormat;
				    virtualPath = true;
				    conf.put( "realMappingPath", scrawlRealMappingPath);//put into conf map using key=realMappingPath
				}else if("no".equalsIgnoreCase(scrawlUsingVirtualPath)){
				    savePath = scrawlPathFormat;
				}
				
				//savePath = this.jsonConfig.getString( "scrawlPathFormat" );
				break;
				
			case ActionMap.CATCH_IMAGE:
				conf.put( "filename", ConfigManager.REMOTE_FILE_NAME );
				conf.put( "filter", this.getArray( "catcherLocalDomain" ) );
				conf.put( "maxSize", this.jsonConfig.getLong( "catcherMaxSize" ) );
				conf.put( "allowFiles", this.getArray( "catcherAllowFiles" ) );
				conf.put( "fieldName", this.jsonConfig.getString( "catcherFieldName" ) + "[]" );
				

				//for virtual path mapping 
				String catcherPathFormat = this.jsonConfig.getString("catcherPathFormat");
				String catcherUsingVirtualPath = this.jsonConfig.getString("catcherUsingVirtualPath");
				if("yes".equalsIgnoreCase(catcherUsingVirtualPath)){
				    String catcherRealMappingPath = this.jsonConfig.getString("catcherRealMappingPath");
				    savePath = catcherRealMappingPath + catcherPathFormat;
				    virtualPath = true;
				    conf.put( "realMappingPath", catcherRealMappingPath);//put into conf map using key=realMappingPath
				}else if("no".equalsIgnoreCase(catcherUsingVirtualPath)){
				    savePath = catcherPathFormat;
				}
				
				
				//savePath = this.jsonConfig.getString( "catcherPathFormat" );
				break;
				
			case ActionMap.LIST_IMAGE:
				conf.put( "allowFiles", this.getArray( "imageManagerAllowFiles" ) );
				
				conf.put( "count", this.jsonConfig.getInt( "imageManagerListSize" ) );
				
				//for virtual path mapping 
				String imageManagerUsingVirtualPath = this.jsonConfig.getString("imageManagerUsingVirtualPath");
				if("yes".equalsIgnoreCase(imageManagerUsingVirtualPath)){
				    String imageManagerRealMappingPath = this.jsonConfig.getString("imageManagerRealMappingPath");
				    virtualPath = true;
				    //如果tomcat配置了虚拟路径，这里覆盖掉JSP页面传入的rootPath，laudukang，2015年7月11日19:54:43
				    this.rootPath=imageManagerRealMappingPath;
				}else if("no".equalsIgnoreCase(imageManagerUsingVirtualPath)){
				}
				
				conf.put( "dir", this.jsonConfig.getString( "imageManagerListPath" ) );
				break;
				
			case ActionMap.LIST_FILE:
				conf.put( "allowFiles", this.getArray( "fileManagerAllowFiles" ) );
				conf.put( "count", this.jsonConfig.getInt( "fileManagerListSize" ) );
				

				//for virtual path mapping 
				String fileManagerUsingVirtualPath = this.jsonConfig.getString("fileManagerUsingVirtualPath");
				if("yes".equalsIgnoreCase(fileManagerUsingVirtualPath)){
				    String fileManagerRealMappingPath = this.jsonConfig.getString("fileManagerRealMappingPath");
				    virtualPath = true;
				    //如果tomcat配置了虚拟路径，这里覆盖掉JSP页面传入的rootPath，laudukang，2015年7月11日19:54:43
				    this.rootPath=fileManagerRealMappingPath;
				}else if("no".equalsIgnoreCase(fileManagerUsingVirtualPath)){
				}
				
				conf.put( "dir", this.jsonConfig.getString("fileManagerListPath") );
				break;
				
		}
		
		conf.put( "savePath", savePath );
		conf.put( "rootPath", this.rootPath );
		conf.put( "virtualPath", virtualPath );//add put
		
		return conf;
		
	}
	
	private void initEnv () throws FileNotFoundException, IOException {
		
		File file = new File( this.originalPath );
		
		if ( !file.isAbsolute() ) {
			file = new File( file.getAbsolutePath() );
		}
		
		this.parentPath = file.getParent();
		
		String configContent = this.readFile( this.getConfigPath() );
		
		try{
			JSONObject jsonConfig = new JSONObject( configContent );
			this.jsonConfig = jsonConfig;
		} catch ( Exception e ) {
			this.jsonConfig = null;
		}
		
	}
	
	private String getConfigPath () {
		return this.parentPath + File.separator + ConfigManager.configFileName;
	}

	private String[] getArray ( String key ) {
		
		JSONArray jsonArray = this.jsonConfig.getJSONArray( key );
		String[] result = new String[ jsonArray.length() ];
		
		for ( int i = 0, len = jsonArray.length(); i < len; i++ ) {
			result[i] = jsonArray.getString( i );
		}
		
		return result;
		
	}
	
	private String readFile ( String path ) throws IOException {
		
		StringBuilder builder = new StringBuilder();
		
		try {
			
			InputStreamReader reader = new InputStreamReader( new FileInputStream( path ), "UTF-8" );
			BufferedReader bfReader = new BufferedReader( reader );
			
			String tmpContent = null;
			
			while ( ( tmpContent = bfReader.readLine() ) != null ) {
				builder.append( tmpContent );
			}
			
			bfReader.close();
			
		} catch ( UnsupportedEncodingException e ) {
			// 忽略
		}
		
		return this.filter( builder.toString() );
		
	}
	
	// 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
	private String filter ( String input ) {
		
		return input.replaceAll( "/\\*[\\s\\S]*?\\*/", "" );
		
	}

}
