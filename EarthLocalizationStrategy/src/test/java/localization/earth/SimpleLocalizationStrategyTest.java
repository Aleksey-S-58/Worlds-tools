package localization.earth;

import java.util.List;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Test;

import service.Localization;
import service.Square;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
public class SimpleLocalizationStrategyTest {
	
	private Localization strategy = new SimpleLocalizationStrategy(null);

	@Test
	public void testGetSquare() {
		System.out.println("h = 2 meters latitude, longitude (0, 0)"); // two meters 
		List<Square> list = strategy.getSquare(2, 0, 0);
		Assert.assertFalse(list.isEmpty());
		Assert.assertTrue("actual size = " + list.size(), list.size() == 1);
		Square square = list.get(0);
		System.out.println(square);
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() < 10 && s.getMaxLatitude() > 0 &&
					s.getMinLatitude() > -10 && s.getMinLatitude() < 0 &&
					s.getMinLongitude() > -10 &&s.getMinLongitude() < 0 && 
					s.getMaxLongitude() < 10 && s.getMaxLongitude() > 0;
		}));
		System.out.println("h = 80 meters latitude, longitude (0, 0)");
		System.out.println(strategy.getSquare(80, 0, 0));
	}
	
	/**
	 * latitude (-delta, +delta) 
	 * longitude (180 - delta, 180)
	 * longitude (-180, -180 + delta)
	 */
	@Test
	public void testLongitudeOutOfBound() {
		System.out.println("h = 2 meters latitude, longitude (0, 180)"); // two meters 
		List<Square> list = strategy.getSquare(2, 0, 180);
		Assert.assertTrue("actual size = " + list.size(), list.size() == 2);
		System.out.println(list);
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() < 10 && s.getMaxLatitude() > 0 &&
					s.getMinLatitude() > -10 && s.getMinLatitude() < 0 &&
					s.getMinLongitude() >= 170 && s.getMaxLongitude() == 180;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() < 10 && s.getMaxLatitude() > 0 &&
					s.getMinLatitude() > -10 && s.getMinLatitude() < 0 &&
					s.getMinLongitude() == -180 && s.getMaxLongitude() <= -175;
		}));
	}

	@Test
	public void testLatitudeOutOfBoundWest() {
		System.out.println("h = 2 meters latitude, longitude (-90, 10)");
		List<Square> list = strategy.getSquare(2, -90, 10);
		Assert.assertTrue("actual size = " + list.size(), list.size() == 2);
		System.out.println(list);
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() < -80 && s.getMaxLatitude() > -90 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() >= 5 && s.getMaxLongitude() < 15;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() < -80 && s.getMaxLatitude() > -90 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() > -175 && s.getMaxLongitude() <= -165;
		}));
	}
	
	/**
	 * A Result must has two elements:
	 * <br> latitude(90 - delta, 90), 180 > longitude > 0
	 * <br> latitude(90 - delta, 90), -180 < longitude < 0
	 */
	@Test
	public void testNorthLatitudeOutOfBoundWest() {
		System.out.println("h = 2 meters latitude, longitude (90, 10)");
		List<Square> list = strategy.getSquare(2, 90, 10);
		Assert.assertTrue("actual size = " + list.size(), list.size() == 2);
		System.out.println(list);
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() == 90 &&
					s.getMinLatitude() > 80 && s.getMinLatitude() < 90 &&
					s.getMinLongitude() >= 5 && s.getMaxLongitude() < 15;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() == 90 &&
					s.getMinLatitude() > 80 && s.getMinLatitude() < 90 &&
					s.getMinLongitude() > -175 && s.getMaxLongitude() <= -165;
		}));
	}
	
	/**
	 * A Result must has two elements:
	 * <br> latitude(90 - delta, 90), -180 < longitude < 0
	 * <br> latitude(90 - delta, 90), 180 > longitude > 0
	 */
	@Test
	public void testNorthLatitudeOutOfBoundEast() {
		System.out.println("h = 2 meters latitude, longitude (90, -10)");
		List<Square> list = strategy.getSquare(2, 90, -10);
		Assert.assertTrue("actual size = " + list.size(), list.size() == 2);
		System.out.println(list);
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() == 90 &&
					s.getMinLatitude() > 80 && s.getMinLatitude() < 90 &&
					s.getMinLongitude() >= -15 && s.getMaxLongitude() < -5;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() == 90 &&
					s.getMinLatitude() > 80 && s.getMinLatitude() < 90 &&
					s.getMinLongitude() > 165 && s.getMaxLongitude() <= 175;
		}));
	}
	
	/**
	 * latitude (-90, -90 + delta)
	 * longitude (-10 - delta, -10 + delta)
	 * longitude (180 - 10 - delta, 180 - 10 + delta)
	 */
	@Test
	public void testLatitudeOutOfBoundEast() {
		System.out.println("h = 2 meters latitude, longitude (-90, -10)");
		List<Square> list = strategy.getSquare(2, -90, -10);
		Assert.assertTrue("actual size = " + list.size(), list.size() == 2);
		System.out.println(list);
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() < -80 && s.getMaxLatitude() > -90 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() >= -15 && s.getMaxLongitude() < -5;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() < -80 && s.getMaxLatitude() > -90 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() > 165 && s.getMaxLongitude() <= 175;
		}));
	}
	
	/**
	 * latitude should be (-90, -90 + delta)
	 * longitude (-delta, +delta)
	 * longitude (180 - delta, 180)
	 * longitude (-180, -180 + delta)
	 */
	@Test
	public void testLatitudeOutOfBound() {
		System.out.println("h = 2 meters latitude, longitude (-90, 0)");
		List<Square> list = strategy.getSquare(2, -90, 0);
		Assert.assertTrue("actual size = " + list.size(), list.size() == 3);
		System.out.println(list);
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() < -80 && s.getMaxLatitude() > -90 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() >= -180 && s.getMaxLongitude() < -170;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() < -80 && s.getMaxLatitude() > -90 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() > 170 && s.getMaxLongitude() <= 180;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() < -80 && s.getMaxLatitude() > -90 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() > -10 && s.getMaxLongitude() <= 10;
		}));
	}
	
	/**
	 * Result must has three elements:
	 * <br>one latitude(-90, -90 + delta), longitude(-180, -180 + delta)
	 * <br>second latitude(-90, -90 + delta), longitude(180 - delta, 180)
	 * <br>third latitude(-90, -90 + delta), longitude(-delta, +delta)
	 */
	@Test
	public void testLatitudeAndEastLongitudeOutOfBound() {
		System.out.println("h = 2 meters latitude, longitude (-90, -180)");
		List<Square> list = strategy.getSquare(2, -90, -180);
		Assert.assertTrue("actual size = " + list.size(), list.size() == 3);
		System.out.println(list);
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() > -90 && s.getMaxLatitude() < -80 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() >= -180 && s.getMaxLongitude() < -170;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() > -90 && s.getMaxLatitude() < -80 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() > 170 && s.getMaxLongitude() <= 180;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() > -90 && s.getMaxLatitude() < -80 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() > -10 && s.getMaxLongitude() <= 10;
		}));
	}
	
	/**
	 * Result must has three elements:
	 * <br>one latitude(-90, -90 + delta), longitude(180 - delta, 180)
	 * <br>second latitude(-90, -90 + delta), longitude(-180, -180 + delta)
	 * <br>third latitude(-90, -90 + delta), longitude(-delta, +delta)
	 */
	@Test
	public void testLatitudeAndWestLongitudeOutOfBound() {
		System.out.println("h = 2 meters latitude, longitude (-90, 180)");
		List<Square> list = strategy.getSquare(2, -90, 180);
		Assert.assertTrue("actual size = " + list.size(), list.size() == 3);
		System.out.println(list);
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() > -90 && s.getMaxLatitude() < -80 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() >= -180 && s.getMaxLongitude() < -170;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() > -90 && s.getMaxLatitude() < -80 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() > 170 && s.getMaxLongitude() <= 180;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() > -90 && s.getMaxLatitude() < -80 &&
					s.getMinLatitude() == -90 &&
					s.getMinLongitude() > -10 && s.getMaxLongitude() <= 10;
		}));
	}
	
	/**
	 * Result must has three elements:
	 * <br>one latitude(90 - delta, 90), longitude(180 - delta, 180)
	 * <br>second latitude(90 - delta, 90), longitude(-180, -180 + delta)
	 * <br>third latitude(90 - delta, 90), longitude(-delta, +delta)
	 */
	@Test
	public void testNorthLatitudeAndWestLongitudeOutOfBound() {
		System.out.println("h = 2 meters latitude, longitude (90, 180)");
		List<Square> list = strategy.getSquare(2, 90, 180);
		Assert.assertTrue("actual size = " + list.size(), list.size() == 3);
		System.out.println(list);
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() == 90 && 
					s.getMinLatitude() > 0 && s.getMinLatitude() < 90 &&
					s.getMinLongitude() >= -180 && s.getMaxLongitude() < -170;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() == 90 && 
					s.getMinLatitude() > 0 && s.getMinLatitude() < 90 &&
					s.getMinLongitude() > 170 && s.getMaxLongitude() <= 180;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() == 90 && 
					s.getMinLatitude() > 0 && s.getMinLatitude() < 90 &&
					s.getMinLongitude() > -10 && s.getMaxLongitude() <= 10;
		}));
	}
	
	/**
	 * Result must has three elements:
	 * <br>one latitude(90 - delta, 90), longitude(-180, -180 + delta)
	 * <br>second latitude(90 - delta, 90), longitude(180 - delta, 180)
	 * <br>third latitude(90 - delta, 90), longitude(-delta, +delta)
	 */
	@Test
	public void testNorthLatitudeAndEastLongitudeOutOfBound() {
		System.out.println("h = 2 meters latitude, longitude (90, -180)");
		List<Square> list = strategy.getSquare(2, 90, -180);
		Assert.assertTrue("actual size = " + list.size(), list.size() == 3);
		System.out.println(list);
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() == 90 && 
					s.getMinLatitude() > 0 && s.getMinLatitude() < 90 &&
					s.getMinLongitude() >= -180 && s.getMaxLongitude() < -170;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() == 90 && 
					s.getMinLatitude() > 0 && s.getMinLatitude() < 90 &&
					s.getMinLongitude() > 170 && s.getMaxLongitude() <= 180;
		}));
		Assert.assertTrue(checkForSquare(list, s -> {
			return s != null && s.getMaxLatitude() == 90 && 
					s.getMinLatitude() > 0 && s.getMinLatitude() < 90 &&
					s.getMinLongitude() > -10 && s.getMaxLongitude() <= 10;
		}));
	}
	
	private boolean checkForSquare(List<Square> list, Predicate<Square> predicate) {
		for (Square square : list) {
			if (predicate.test(square)) {
				return true;
			}
		}
		return false;
	}
	
}
