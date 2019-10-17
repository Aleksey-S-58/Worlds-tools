package service.simple.implementation;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;

import service.ApiService;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
public abstract class AbstractApiServiceTest {
	
	protected static final String CUSTOM_INFO = "test-api-info.json";
	
	protected static final String TEST_INFO = "{\"text\": \"This is a test for /map OPTIONS\"}";

	protected ApiService service;
	
	protected boolean defaultInfo;
	
	@Mock
	protected Environment environment;
	
	@Mock
	protected ServletContext context;
	
	protected InputStream getInputStream(String fileName) {
		return ClassLoader.getSystemResourceAsStream(fileName);
	}
	
	protected void prepareCustomSettings() {
		Mockito.when(environment.getProperty(Mockito.eq(SimpleApiService.API_INFO_PROPERTY), Mockito.anyString()))
		.thenReturn("test-api-info.json");
		Mockito.when(environment.getProperty(Mockito.eq(SimpleApiService.GET_MATERIAL_PROPERTY), Mockito.anyString()))
		.thenReturn("test-api-info.json");
		Mockito.when(environment.getProperty(Mockito.eq(SimpleApiService.GET_OBJECT_PROPERTY), Mockito.anyString()))
		.thenReturn("test-api-info.json");
		Mockito.when(environment.getProperty(Mockito.eq(SimpleApiService.GET_SPRITE_PROPERTY), Mockito.anyString()))
		.thenReturn("test-api-info.json");
		Mockito.when(environment.getProperty(Mockito.eq(SimpleApiService.SUPPORTED_OBJECT_TYPES_PROPERTY), Mockito.anyString()))
		.thenReturn("test-api-info.json");
	}
	
	protected void prepareContext() {
		Mockito.when(context.getResourceAsStream(Mockito.eq(SimpleApiService.DEFAULT_API_INFO)))
		.thenReturn(getInputStream(SimpleApiService.DEFAULT_API_INFO));
		Mockito.when(context.getResourceAsStream(Mockito.eq(SimpleApiService.DEFAULT_GET_MATERIAL_INFO)))
		.thenReturn(getInputStream(SimpleApiService.DEFAULT_GET_MATERIAL_INFO));
		Mockito.when(context.getResourceAsStream(Mockito.eq(SimpleApiService.DEFAULT_GET_OBJECT_INFO)))
		.thenReturn(getInputStream(SimpleApiService.DEFAULT_GET_OBJECT_INFO));
		Mockito.when(context.getResourceAsStream(Mockito.eq(SimpleApiService.DEFAULT_GET_SPRITE_INFO)))
		.thenReturn(getInputStream(SimpleApiService.DEFAULT_GET_SPRITE_INFO));
		Mockito.when(context.getResourceAsStream(Mockito.eq(SimpleApiService.DEFAULT_SUPPORTED_OBJECT_TYPES_INFO)))
		.thenReturn(getInputStream(SimpleApiService.DEFAULT_SUPPORTED_OBJECT_TYPES_INFO));
		Mockito.when(context.getResourceAsStream(Mockito.eq(CUSTOM_INFO)))
		.thenReturn(getInputStream(CUSTOM_INFO));
	}
	
	protected void checkInfo(String info) {
		Assert.assertNotNull(info);
		Assert.assertTrue(info.length() > 0);
		if(!defaultInfo) {
			Assert.assertEquals(TEST_INFO, info);
		}
	}
	
	@Test
	public void testApiInfo() {
		String info = service.getApiInfo();
		checkInfo(info);
	}
	
	@Test
	public void testSupportedObjectTypesInfo() {
		String info = service.getSupportedObjectTypesInfo();
		checkInfo(info);
	}
	
	@Test
	public void testObjectOptions() {
		String info = service.getObjectOptions();
		checkInfo(info);
	}
	
	@Test
	public void testMaterialOptions() {
		String info = service.getMaterialOptions();
		checkInfo(info);
	}
	
	@Test
	public void testSpriteOptions() {
		String info = service.getSpriteOptions();
		checkInfo(info);
	}
}
