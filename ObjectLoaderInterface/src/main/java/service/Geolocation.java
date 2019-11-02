package service;

/**
 * This interface defines the basic properties for any location (latitude, longitude, height).
 * @author Aleksey Shishkin
 *
 */
public interface Geolocation {

	public double getLatitude();

	public void setLatitude(double latitude);

	public double getLongitude();

	public void setLongitude(double longitude);

	public double getHight();

	public void setHight(double hight);
	
	// TODO Should we have a method to measure a distance between two locations.
	
}
