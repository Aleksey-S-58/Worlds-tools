package service;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
public class SimpleDistanceMeasurer implements DistanceMeasurer {
	
	public static final String EXCEPTION = "Location can't be null!";

	/**
	 * The method calculates distance between two locations via 
	 * <br> SQRT(dh^2 + dLatitude^2 + dLongitude^2)
	 */
	@Override
	public double getDistance(Geolocation location1, Geolocation location2) {
		if (location1 == null || location2 == null) {
			throw new IllegalArgumentException(EXCEPTION);
		}
		double h = location1.getHight() - location2.getHight();
		double latitude = location1.getLatitude() - location2.getLatitude();
		double longitude = location1.getLongitude() - location2.getLongitude();
		double lSquare = h * h + latitude * latitude + longitude * longitude;
		return Math.sqrt(lSquare);
	}

}
