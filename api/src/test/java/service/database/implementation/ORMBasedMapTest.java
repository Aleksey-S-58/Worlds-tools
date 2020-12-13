package service.database.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import data.Description;
import data.Location;
import data.ObjectType;
import dto.ThreeDObject;
import repository.DescriptionRepository;
import repository.LocationRepository;
import service.Localization;
import service.MapService;
import service.Square;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ORMBasedMapTest {
	
	private static final long OBJECT_ID = 1;
	private static final String DESCRIPTION_1 = "Object-1";
	private static final String DESCRIPTION_2 = "Object-2";
	private static final String DESCRIPTION_3 = "Object-3";

	@Mock
	private LocationRepository locationsRepository;
	
	@Mock
	private DescriptionRepository descriptionsRepository;
	
	@Mock
	private Localization localization;
	
	private Square defaultSquare = new Square(-10, 10, -10, 10);
	private Location objectLocation = new Location(0, OBJECT_ID, 5, 5, 0, 0, 0, 0);
	
	private MapService getMapService() {
		ORMBasedMap service = new ORMBasedMap(locationsRepository, descriptionsRepository, localization);
		return service;
	}
	
	private void prepareLocalization() {
		List<Square> list = Stream.of(defaultSquare).collect(Collectors.toList());
		Mockito.when(localization.getSquare(Mockito.anyLong(), 
				Mockito.anyDouble(), Mockito.anyDouble()))
		.thenReturn(list);
	}
	
	private void prepareLocationsRepository() {
		List<Location> locations = new ArrayList<>();
		locations.add(objectLocation);
		Mockito.when(locationsRepository.getObjectsInLocation(Mockito.anyDouble(), 
				Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble()))
		.thenReturn(locations);
	}
	
	private void prepareDescriptionsRepository() {
		List<Description> descriptions = new ArrayList<>();
		descriptions.add(new Description(2, DESCRIPTION_2, ObjectType.THREE_D_OBJECT, OBJECT_ID, 2));
		descriptions.add(new Description(1, DESCRIPTION_1, ObjectType.THREE_D_OBJECT, OBJECT_ID, 1));
		descriptions.add(new Description(3, DESCRIPTION_3, ObjectType.THREE_D_OBJECT, OBJECT_ID, 3));
		Mockito.when(descriptionsRepository.getWhichInList(Mockito.anyListOf(Long.class)))
		.thenReturn(descriptions);
	}
	
	private void prepareAll() {
		prepareLocalization();
		prepareLocationsRepository();
		prepareDescriptionsRepository();
	}
	
	@Test
	public void simpleTest() {
		prepareAll();
		MapService service = getMapService();
		List<? extends ThreeDObject> result = service.getObjects(0, 0, 0);
		Assert.assertTrue(result.isEmpty());
	}
	
	@Test
	public void twoObjectsTest() {
		prepareAll();
		MapService service = getMapService();
		List<? extends ThreeDObject> result = service.getObjects(0, 4, 5);
		Assert.assertTrue(result.size() == 1);
		ThreeDObject object = result.get(0);
		Assert.assertTrue(object.getName().equals(DESCRIPTION_1));
	}
	
	@Test
	public void theSecondIsVisibleTest() {
		prepareAll();
		MapService service = getMapService();
		List<? extends ThreeDObject> result = service.getObjects(0, 3, 5);
		Assert.assertTrue(result.size() == 1);
		ThreeDObject object = result.get(0);
		Assert.assertTrue(object.getName().equals(DESCRIPTION_2));
	}
	
	@Test
	public void theThirdIsVisibleTest() {
		prepareAll();
		MapService service = getMapService();
		List<? extends ThreeDObject> result = service.getObjects(0, 3, 4);
		Assert.assertTrue(result.size() == 1);
		ThreeDObject object = result.get(0);
		Assert.assertTrue(object.getName().equals(DESCRIPTION_3));
	}
}
