package localization.depot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import service.Localization;
import service.Square;

/**
 * This service requires the following properties: 
 * <br>{@value #MIN_LATITUDE}, {@value #MAX_LATITUDE}, 
 * {@value #MIN_LONGITUDE}, {@value #MAX_LONGITUDE}, {@value #OBSERVATION_RADIUS}
 * <br>must be specified.
 * @author Aleksey Shishkin
 *
 */
@Service
public class DepotLocalizationStrategy implements Localization {
	
	public static final String MIN_LATITUDE = "min-latitude";
	
	public static final String MAX_LATITUDE = "max-latitude";
	
	public static final String MIN_LONGITUDE = "min-longitude";
	
	public static final String MAX_LONGITUDE = "max-longitude";
	
	public static final String OBSERVATION_RADIUS = "observation-radius";
	
	private Square mapBounds;
 
	private double radius; // Observation radius.
	
	private void setMapBounds(Environment environment) throws IllegalStateException {
		double minLatitude = Double.parseDouble(environment.getRequiredProperty(MIN_LATITUDE));
		double maxLatitude = Double.parseDouble(environment.getRequiredProperty(MAX_LATITUDE));
		double minLongitude = Double.parseDouble(environment.getRequiredProperty(MIN_LONGITUDE));
		double maxLongitude = Double.parseDouble(environment.getRequiredProperty(MAX_LONGITUDE));
		mapBounds = new Square(minLatitude, maxLatitude, minLongitude, maxLongitude);
		radius = Double.parseDouble(environment.getRequiredProperty(OBSERVATION_RADIUS));
	}
	
	public DepotLocalizationStrategy(Environment environment) {
		setMapBounds(environment);
	}
	
	public Square getMapBounds() {
		return mapBounds;
	}
	
	public double getRadius() {
		return radius;
	}
	
	private double getIncrement(double dh, double r) {
		double daSquare = r * r - dh * dh;
		return Math.sqrt(daSquare);
	}
	
	private double increment(double value, double boundary, double increment) {
		double result = value + increment;
		if (result > boundary) {
			result = boundary;
		}
		return result;
	}
	
	private double decrement(double value, double boundary, double decrement) {
		double result = value - decrement;
		if (result < boundary) {
			result = boundary;
		}
		return result;
	}

	@Override
	public List<Square> getSquare(double hight, double latitude, double longitude) {
		double increment = getIncrement(hight, radius);
		double minLatitude = decrement(latitude, mapBounds.getMinLatitude(), increment);
		double maxLatitude = increment(latitude, mapBounds.getMaxLatitude(), increment);
		double minLongitude = decrement(longitude, mapBounds.getMinLongitude(), increment);
		double maxLongitude = increment(longitude, mapBounds.getMaxLongitude(), increment);
		List<Square> result = new ArrayList<>();
		result.add(new Square(minLatitude, maxLatitude, minLongitude, maxLongitude));
		return result;
	}

}
