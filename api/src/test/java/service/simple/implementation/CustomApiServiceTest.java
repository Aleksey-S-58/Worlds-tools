package service.simple.implementation;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomApiServiceTest extends AbstractApiServiceTest {

	@Before
	public void init() {
		Mockito.when(environment.getProperty(Mockito.eq(SimpleApiService.CUSTOM_SPECIFICATION_ENABLED), Mockito.eq("false")))
		.thenReturn("true");
		defaultInfo = false;
		prepareCustomSettings();
		prepareContext();
		service = new SimpleApiService(context, environment);
	}
	
}
