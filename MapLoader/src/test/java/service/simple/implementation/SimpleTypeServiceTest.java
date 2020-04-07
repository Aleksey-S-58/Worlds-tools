package service.simple.implementation;

import org.junit.Assert;
import org.junit.Test;

import data.ObjectType;
import data.TypeMarker;
import service.TypeService;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
public class SimpleTypeServiceTest {

	@Test
	public void testInterface() {
		SimpleTypeService service = new SimpleTypeService();
		Assert.assertNotNull(service);
		Assert.assertTrue(service instanceof TypeService);
	}

	@Test
	public void testConstructor() {
		SimpleTypeService service = new SimpleTypeService(ObjectType.values());
		Assert.assertNotNull(service);
		Enum<? extends TypeMarker>[] types = service.getObjectTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.length == ObjectType.values().length);
		for (Enum<? extends TypeMarker> value : types) {
			Assert.assertTrue(contains(ObjectType.values(), value));
		}
	}

	@Test
	public void testDuplicatedArguments() {
		SimpleTypeService service = new SimpleTypeService(ObjectType.values(), ObjectType.values());
		Assert.assertNotNull(service);
		Enum<? extends TypeMarker>[] types = service.getObjectTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.length == ObjectType.values().length);
		for (Enum<? extends TypeMarker> value : types) {
			Assert.assertTrue(contains(ObjectType.values(), value));
		}
	}

	private boolean contains(Enum<? extends TypeMarker>[] types, Enum<? extends TypeMarker> type) {
		for (Enum<? extends TypeMarker> value : types) {
			if (value.equals(type)) {
				return true;
			}
		}
		return false;
	}

}
