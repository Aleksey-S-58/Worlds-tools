package service;

/**
 * A basic implementation that in addition to basic methods allows 
 * to measure a distance between locations.
 * @author Aleksey Shishkin
 *
 */
public class SimpleGeolocation implements Geolocation {

	private double latitude;
	
	private double longitude;
	
	private double hight;
	
	/**
	 * Strategy that is intended to measure a distance between two locations.
	 */
	private DistanceMeasurer measurer;

	/**
	 * By default a SimpleDistanceMeasurer will be used to measure a distance.
	 */
	public SimpleGeolocation() {
		measurer = new SimpleDistanceMeasurer();
	}
	
	public SimpleGeolocation(DistanceMeasurer measurer) {
		this.measurer = measurer;
	}
	
	public SimpleGeolocation(double latitude, double longitude, double hight) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.hight = hight;
		measurer = new SimpleDistanceMeasurer();
	}
	
	public SimpleGeolocation(double latitude, double longitude, double hight, DistanceMeasurer measurer) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.hight = hight;
		this.measurer = measurer;
	}
	
	public DistanceMeasurer getMeasurer() {
		return measurer;
	}

	public void setMeasurer(DistanceMeasurer measurer) {
		this.measurer = measurer;
	}

	public double getDistance(Geolocation location) {
		return measurer.getDistance(this, location);
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getHight() {
		return hight;
	}

	public void setHight(double hight) {
		this.hight = hight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(hight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleGeolocation other = (SimpleGeolocation) obj;
		if (Double.doubleToLongBits(hight) != Double.doubleToLongBits(other.hight))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		return true;
	}
	
}
