package service.simple.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import dto.ThreeDObject;
import service.Localization;
import service.Square;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleMapServiceTest {
	
	private static final String FILE_NAME = "map.json";
	
	@Mock
	private Localization localization;
	
	@Mock
	private ServletContext context;
	
	private Square defaultSquare = new Square(-10, 10, -10, 10);
	
	private SimpleMapService service;
	
	@Before
	public void init() {
		Mockito.when(context.getResourceAsStream(Mockito.anyString()))
		.thenReturn(ClassLoader.getSystemResourceAsStream(FILE_NAME));
		List<Square> locations = new ArrayList<>();
		locations.add(defaultSquare);
		Mockito.when(localization.getSquare(Mockito.anyLong(), Mockito.anyDouble(), Mockito.anyDouble()))
		.thenReturn(locations);
		service = new SimpleMapService(context, "map.json", localization);
	}
	
	@Test
	public void simpleTest() {
		List<ThreeDObject> result = service.getObjects(1, 1, 1);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		Assert.assertTrue(result.size() == 1);
		Assert.assertTrue(result.get(0).getName().equals("test"));
	}

}
