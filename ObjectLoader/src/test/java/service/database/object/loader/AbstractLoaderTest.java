package service.database.object.loader;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

import data.AbstractObject;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractLoaderTest {

	private static final String ID = "id.obj";
	private static final String UNKNOWN_ID = "some.obj";
	private static final String CORRECT_ID = "id_obj";

	@Mock
	private CrudRepository<TestObject, String> repository;

	public static class TestLoader extends AbstractLoader {
		static {
			logger = LoggerFactory.getLogger(AbstractLoaderTest.class);
		}
	}

	public static class TestObject extends AbstractObject {

		public TestObject(byte[] bytes) {
			this.bytes = bytes;
		}

	}

	@Test
	public void replaceDotTest() {
		TestLoader loader = new TestLoader();
		String name = "1.jpg";
		String result = loader.replaceDot(name);
		Assert.assertNotNull(result);
		Assert.assertTrue(result.contains("_"));
		Assert.assertFalse(result.contains("."));
	}

	@Test
	public void getBytesTest() {
		TestLoader loader = new TestLoader();
		byte[] result = loader.getBytes(Optional.empty());
		Assert.assertNotNull(result);
		Assert.assertTrue(result.length == 0);
		result = loader.getBytes(null);
		Assert.assertNotNull(result);
		Assert.assertTrue(result.length == 0);
		byte[] bytes = {10, 15, 17};
		TestObject o = new TestObject(bytes);
		result = loader.getBytes(Optional.of(o));
		Assert.assertNotNull(result);
		Assert.assertArrayEquals(bytes, result);
	}

	@Test
	public void loadBytesTest() {
		TestLoader loader = new TestLoader();
		byte[] bytes = {10, 11, 12};
		Mockito.when(repository.findById(Mockito.anyString()))
			.thenReturn(Optional.empty()); // Entity wasn't found.
		Mockito.when(repository.findById(Mockito.eq(CORRECT_ID)))
			.thenReturn(Optional.of(new TestObject(bytes)));
		byte[] result = loader.loadBytes(ID, repository, "correct id test");
		Assert.assertNotNull(result);
		Assert.assertArrayEquals(bytes, result);
		result = loader.loadBytes(UNKNOWN_ID, repository, "test for unknown id");
		Assert.assertNotNull(result);
		Assert.assertTrue(result.length == 0);
	}

}
