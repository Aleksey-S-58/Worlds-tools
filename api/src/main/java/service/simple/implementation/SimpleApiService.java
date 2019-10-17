package service.simple.implementation;

import java.nio.charset.Charset;

import javax.servlet.ServletContext;

import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import service.ApiService;

/**
 * This service loads help information from files.
 * <br>If you want to define another informational file for a particular resource then 
 * <br>allow custom specification by set property {@value #CUSTOM_SPECIFICATION_ENABLED} to true
 * <br>and specify a path to a new resource file.
 * <br>Use property
 * <br>{@value #API_INFO_PROPERTY} to define a resource that will be load by api info request,
 * <br>{@value #SUPPORTED_OBJECT_TYPES_PROPERTY} to define a resource that will be load by supported object types OPTIONS request,
 * <br>{@value #GET_OBJECT_PROPERTY} to define a resource that will be load by get object OPTIONS request,
 * <br>{@value #GET_MATERIAL_PROPERTY} to define a resource that will be load by get material OPTIONS request,
 * <br>{@value #GET_SPRITE_PROPERTY} to define a resource that will be load by get sprite OPTIONS request
 * 
 * @author Aleksey Shishkin
 *
 */
@Service
public class SimpleApiService extends ResourceReader implements ApiService {
	
	/**
	 * This is a boolean parameter that controls which files will be read to get methods specification. 
	 */
	public static final String CUSTOM_SPECIFICATION_ENABLED = "custom-specification-enabled";
	/**
	 * This property specifies a path to a common description of a map api.
	 */
	public static final String API_INFO_PROPERTY = "api-info";
	
	public static final String DEFAULT_API_INFO = "specification/map-info.json";
	/**
	 * This property specifies a path to a supported object types description.
	 */
	public static final String SUPPORTED_OBJECT_TYPES_PROPERTY = "supported-types";
	
	public static final String DEFAULT_SUPPORTED_OBJECT_TYPES_INFO = "specification/supported-types.json";
	
	/**
	 * This property specifies a path to a description of a getObject method.
	 */
	public static final String GET_OBJECT_PROPERTY = "getObject-info";
	
	public static final String DEFAULT_GET_OBJECT_INFO = "specification/getObject-info.json";
	
	/**
	 * This property specifies a path to a description of a getMaterial method.
	 */
	public static final String GET_MATERIAL_PROPERTY = "getMaterial-info";
	
	public static final String DEFAULT_GET_MATERIAL_INFO = "specification/getMaterial-info.json";
	
	/**
	 * This property specifies a path to a description of a getSprite method.
	 */
	public static final String GET_SPRITE_PROPERTY = "getSprite-info";
	
	public static final String DEFAULT_GET_SPRITE_INFO = "specification/getSprite-info.json";
	
	private boolean customSpecificationEnabled;
	private String apiInfoPath;
	private String supportedObjectTypesInfoPath;
	private String getObjectInfoPath;
	private String getMaterialInfoPath;
	private String getSpriteInfoPath;
	
	public SimpleApiService(ServletContext context, Environment environment) {
		this.context = context;
		logger = LoggerFactory.getLogger(SimpleApiService.class);
		setParameters(environment);
	}
	
	private void setParameters(Environment environment) {
		String caustomSpecificatio = environment.getProperty(CUSTOM_SPECIFICATION_ENABLED, "false");
		customSpecificationEnabled = Boolean.valueOf(caustomSpecificatio);
		if (customSpecificationEnabled) {
			// Try to load specified values.
			apiInfoPath = environment.getProperty(API_INFO_PROPERTY, DEFAULT_API_INFO);
			supportedObjectTypesInfoPath = environment.getProperty(SUPPORTED_OBJECT_TYPES_PROPERTY, DEFAULT_SUPPORTED_OBJECT_TYPES_INFO);
			getObjectInfoPath = environment.getProperty(GET_OBJECT_PROPERTY, DEFAULT_GET_OBJECT_INFO);
			getMaterialInfoPath = environment.getProperty(GET_MATERIAL_PROPERTY, DEFAULT_GET_MATERIAL_INFO);
			getSpriteInfoPath = environment.getProperty(GET_SPRITE_PROPERTY, DEFAULT_GET_SPRITE_INFO);
		} else {
			// Set default values if custom specification is disabled.
			apiInfoPath = DEFAULT_API_INFO;
			supportedObjectTypesInfoPath = DEFAULT_SUPPORTED_OBJECT_TYPES_INFO;
			getObjectInfoPath = DEFAULT_GET_OBJECT_INFO;
			getMaterialInfoPath = DEFAULT_GET_MATERIAL_INFO;
			getSpriteInfoPath = DEFAULT_GET_SPRITE_INFO;
		}
	}
	
	private String loadSpecification(String path) {
		try {
			byte[] bytes = readFile(path);
			return new String(bytes, Charset.defaultCharset());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return e.getMessage();
		}
	}
	
	/*
	 * OPTIONS response sample:
	 * OPTIONS
	 * HTTP/1.1 200 OK
	 * Allow: GET,HEAD,POST,OPTIONS,TRACE
	 * Content-Type: text/html; charset=UTF-8
	 * Date: Wed, 08 May 2013 10:24:43 GMT
	 * Content-Length: 0
	 * 
	 * Content-Length not 0 if there is a body for example with JSON 
	 * description of methods.
	 */

	@Override
	public String getApiInfo() {
		return loadSpecification(apiInfoPath);
	}

	@Override
	public String getSupportedObjectTypesInfo() {
		return loadSpecification(supportedObjectTypesInfoPath);
	}

	@Override
	public String getObjectOptions() {
		return loadSpecification(getObjectInfoPath);
	}

	@Override
	public String getMaterialOptions() {
		return loadSpecification(getMaterialInfoPath);
	}

	@Override
	public String getSpriteOptions() {
		return loadSpecification(getSpriteInfoPath);
	}

}
