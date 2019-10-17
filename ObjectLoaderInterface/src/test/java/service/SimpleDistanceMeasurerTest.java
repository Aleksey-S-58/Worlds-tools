package service;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
public class SimpleDistanceMeasurerTest {

	@Test
	public void test() {
		SimpleGeolocation location1 = new SimpleGeolocation(); // (0, 0, 0)
		SimpleGeolocation location2 = new SimpleGeolocation(2, 2, 1);
		DistanceMeasurer measurer = new SimpleDistanceMeasurer();
		double result = 3;
		Assert.assertEquals(result, measurer.getDistance(location1, location2), 0);
	}
}
