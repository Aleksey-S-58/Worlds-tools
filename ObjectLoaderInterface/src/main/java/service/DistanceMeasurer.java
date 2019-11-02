package service;

/**
 * Strategy that is intended to measure a distance between two locations.
 * @author Aleksey Shishkin
 *
 */
public interface DistanceMeasurer {

	public double getDistance(Geolocation location1, Geolocation location2);
	
}
