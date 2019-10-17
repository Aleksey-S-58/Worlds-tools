package service.simple.implementation;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
public class ResourceReaderTest {
	
	private static final String FILE_NAME = "test-api-info.json";
	private ServletContext contextMock = Mockito.mock(ServletContext.class);

	private class TestReader extends ResourceReader {
		
		public TestReader(ServletContext context) {
			this.context = context;
			logger = LoggerFactory.getLogger(ResourceReaderTest.class);
		}
	};
	
	private void testResourceReader() {
		ResourceReader reader = new TestReader(contextMock);
		try {
			byte[] bytes = reader.readFile(FILE_NAME);
			Assert.assertNotNull(bytes);
			Assert.assertTrue(bytes.length > 0);
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void simpleTest() {
		Mockito.when(contextMock.getResourceAsStream(Mockito.anyString())).thenReturn(ClassLoader.getSystemResourceAsStream(FILE_NAME));
		testResourceReader();
	}
	
	@Test
	public void test() {
		ClassLoader loader = Mockito.mock(ClassLoader.class);
		Mockito.when(loader.getResourceAsStream(Mockito.anyString())).thenReturn(ClassLoader.getSystemResourceAsStream(FILE_NAME));
		Mockito.when(contextMock.getClassLoader()).thenReturn(loader);
		testResourceReader();
	}
}
