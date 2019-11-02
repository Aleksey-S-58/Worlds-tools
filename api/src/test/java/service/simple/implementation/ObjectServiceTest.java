package service.simple.implementation;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import service.ThreeDObjectService;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ObjectServiceTest {
	
	private static final String TEST_MATERIAL = "test-material";
	private static final String TEST_GEOMETRY = "test-geometry";
	private static final String TEST_SPRITE = "test-sprite";
	
	private static final String MATERIAL = "samples/test.mtl";
	private static final String GEOMETRY = "samples/test.obj";
	private static final String SPRITE = "samples/test.png";
	
	private static final String TEST = "test";
	
	@Mock
	private ServletContext context;
	
	private ThreeDObjectService service;
	
	private InputStream getInputStream(String fileName) {
		return ClassLoader.getSystemResourceAsStream(fileName);
	}
	
	@Before
	public void init() {
		Mockito.when(context.getResourceAsStream(Mockito.eq('/' + MATERIAL)))
		.thenReturn(getInputStream(MATERIAL));
		Mockito.when(context.getResourceAsStream(Mockito.eq('/' + GEOMETRY)))
		.thenReturn(getInputStream(GEOMETRY));
		Mockito.when(context.getResourceAsStream(Mockito.eq('/' + SPRITE)))
		.thenReturn(getInputStream(SPRITE));
		service = new ObjectService(context);
	}
	
	@Test
	public void getGeometryTest() {
		byte[] bytes = service.getObject(TEST);
		TEST_GEOMETRY.equals(new String(bytes));
	}
	
	@Test
	public void getMaterialTest() {
		byte[] bytes = service.getMaterial(TEST);
		TEST_MATERIAL.equals(new String(bytes));
	}
	
	@Test
	public void getSpriteTest() {
		byte[] bytes = service.getSprite(TEST);
		TEST_SPRITE.equals(new String(bytes));
	}

}
