package localization.depot;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import service.Square;

@RunWith(MockitoJUnitRunner.class)
public class DepotLocalizationStrategyTest {
	
	@Mock
	private Environment environment;
	
	private DepotLocalizationStrategy strategy;
	
	@Before
	public void init() {
		Mockito.when(environment.getRequiredProperty(Mockito.eq(DepotLocalizationStrategy.MIN_LATITUDE)))
		.thenReturn("-50");
		Mockito.when(environment.getRequiredProperty(Mockito.eq(DepotLocalizationStrategy.MAX_LATITUDE)))
		.thenReturn("50");
		Mockito.when(environment.getRequiredProperty(Mockito.eq(DepotLocalizationStrategy.MIN_LONGITUDE)))
		.thenReturn("-50");
		Mockito.when(environment.getRequiredProperty(Mockito.eq(DepotLocalizationStrategy.MAX_LONGITUDE)))
		.thenReturn("50");
		Mockito.when(environment.getRequiredProperty(Mockito.eq(DepotLocalizationStrategy.OBSERVATION_RADIUS)))
		.thenReturn("10");
		strategy = new DepotLocalizationStrategy(environment);
	}
	
	@Test
	public void simpleTest() {
		List<Square> locations = strategy.getSquare(2, 0, 0);
		Assert.assertNotNull(locations);
		Assert.assertTrue(locations.size() == 1);
		Square location = locations.get(0);
		Assert.assertTrue(location.getMinLatitude() < 0 && location.getMinLatitude() > -50);
		Assert.assertTrue(location.getMaxLatitude() > 0 && location.getMaxLatitude() < 50);
		Assert.assertTrue(location.getMinLongitude() < 0 && location.getMinLongitude() > -50);
		Assert.assertTrue(location.getMaxLongitude() > 0 && location.getMaxLongitude() < 50);
	}
	
	@Test
	public void testForBounds() {
		List<Square> locations = strategy.getSquare(2, -50, -50);
		Assert.assertNotNull(locations);
		Assert.assertTrue(locations.size() == 1);
		Square location = locations.get(0);
		Assert.assertEquals(-50, location.getMinLatitude(), 0);
		Assert.assertTrue(location.getMaxLatitude() < 0);
		Assert.assertEquals(-50, location.getMinLongitude(), 0);
		Assert.assertTrue(location.getMaxLongitude() < 0);
	}
}
