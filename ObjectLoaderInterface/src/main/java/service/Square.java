package service;

/**
 * This class represents an area that contains all objects on a map 
 * which are visible from an observers position.
 * @author Aleksey Shishkin
 *
 */
public class Square {
	
	private double minLatitude;
	
	private double maxLatitude;
	
	private double minLongitude;
	
	private double maxLongitude;

	public Square(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
		super();
		this.minLatitude = minLatitude;
		this.maxLatitude = maxLatitude;
		this.minLongitude = minLongitude;
		this.maxLongitude = maxLongitude;
	}

	public double getMinLatitude() {
		return minLatitude;
	}

	public void setMinLatitude(double minLatitude) {
		this.minLatitude = minLatitude;
	}

	public double getMaxLatitude() {
		return maxLatitude;
	}

	public void setMaxLatitude(double maxLatitude) {
		this.maxLatitude = maxLatitude;
	}

	public double getMinLongitude() {
		return minLongitude;
	}

	public void setMinLongitude(double minLongitude) {
		this.minLongitude = minLongitude;
	}

	public double getMaxLongitude() {
		return maxLongitude;
	}

	public void setMaxLongitude(double maxLongitude) {
		this.maxLongitude = maxLongitude;
	}

	@Override
	public String toString() {
		return "Square [minLatitude=" + minLatitude + ", maxLatitude=" + maxLatitude + ", minLongitude=" + minLongitude
				+ ", maxLongitude=" + maxLongitude + "]";
	}

}
