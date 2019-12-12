package localization.earth;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import service.Localization;
import service.Square;

/**
 * Please configure following properties in a property file:
 * {@value #EARTH_RADIUS_PROPERTY}, {@value #OBSERVATION_RADIUS_PROPEERTY}
 * (earth radius and observation radius should be specified in one and the same units)
 * <br> The default earth radius: {@value #EARTH_RADIUS} * 1000
 * <br> default observation radius: {@value #RADIUS}
 * <br>
 * <br>latitude (-90) - 0 (source), 0 - (+90) north
 * <br>longitude (-180) - 0 (east), 0 - (+180) west
 * 
 * @author Aleksey Shishkin
 */
@Service
public class SimpleLocalizationStrategy implements Localization {

	public static final String OBSERVATION_RADIUS_PROPEERTY = "observation-radius";
	public static final String EARTH_RADIUS_PROPERTY = "earth-radius";

	private static final double RADIUS = 100; // meters.

	private static final double EARTH_RADIUS = 6371D; // km.

	private double observationRadius;
	private double earthRadius;

	public SimpleLocalizationStrategy(Environment environment) {
		if (environment != null) {
			try {
				loadFromProperties(environment);
			} catch (RuntimeException e) {
				setDefaultParameters();
			}
		} else {
			setDefaultParameters();
		}
	}

	public double getObservationRadius() {
		return observationRadius;
	}

	public double getEarthRadius() {
		return earthRadius;
	}

	private void loadFromProperties(Environment environment) {
		String str = environment.getProperty(EARTH_RADIUS_PROPERTY, Double.toString(EARTH_RADIUS * 1000));
		earthRadius = Double.parseDouble(str);
		str = environment.getProperty(OBSERVATION_RADIUS_PROPEERTY, Double.toString(RADIUS));
		observationRadius = Double.parseDouble(str);
	}

	private void setDefaultParameters() {
		earthRadius = EARTH_RADIUS * 1000;
		observationRadius = RADIUS;
	}

	/**
	 * latitude (-90) - 0 (source), 0 - (+90) north
	 * longitude (-180) - 0 (east), 0 - (+180) west
	 */

	@Override
	public List<Square> getSquare(double hight, double latitude, double longitude) {
		Square s = getPointsNeighborhood(hight, latitude, longitude);
		if (s.getMinLatitude() >= -90 && s.getMaxLatitude() <= 90) {
			if (s.getMinLongitude() >= -180 && s.getMaxLongitude() <= 180) {
				// The first case if we don't reach bounds by latitude and longitude.
				return Stream.of(s).collect(Collectors.toList());
			} else {
				return getSquareOutOfLongitudeBound(s);
			}
		} else {
			return getSquareOutOfLatitudeBound(s);
		}
	}

	/**
	 * 
	 * (hight, latitude, longitude) are an observer's coordinates.
	 * @return a neighborhood of observation point 
	 * according to the defined observation radius.
	 */
	private Square getPointsNeighborhood(double hight, double latitude, double longitude) {
		double dPhi = getDeltaPhi(hight, observationRadius);
		return new Square(latitude - dPhi, latitude + dPhi, longitude - dPhi, longitude + dPhi);
	}

	/**
	 * When latitude is out of bound we will have a square with opposite longitude.
	 * @param s - neighborhood of observation point.
	 */
	private List<Square> getSquareOutOfLatitudeBound(Square s) {
		if (s.getMinLatitude() < -90) {	// latitude < -90 (south)
			if (s.getMinLongitude() >= -180 && s.getMaxLongitude() <= 180) {
				return getSquare(s);
			} else {
				return getSquareOutOfBound(-90, s.getMaxLatitude(), s);
			}
		} else {
			// latitude > 90 (north)
			if (s.getMinLongitude() >= -180 && s.getMaxLongitude() <= 180) {
				return getSquare(s);
			} else {
				return getSquareOutOfBound(s.getMinLatitude(), 90, s);
			}
		}
	}

	/**
	 * This method is used when a square is in latitude bounds.
	 * @return a list of squares when only longitude out of bounds.
	 */
	private List<Square> getSquareOutOfLongitudeBound(Square s) {
		if (s.getMinLongitude() < -180) {
			Square s1 = new Square(s.getMinLatitude(), s.getMaxLatitude(), -180, s.getMaxLongitude());
			double delta = -180 - s.getMinLongitude();
			Square s2 = new Square(s.getMinLatitude(), s.getMaxLatitude(), 180 - delta, 180);
			return Stream.of(s1, s2).collect(Collectors.toList());
		} else {
			// longitude > 180
			Square s1 = new Square(s.getMinLatitude(), s.getMaxLatitude(), s.getMinLongitude(), 180);
			double delta = s.getMaxLongitude() - 180;
			Square s2 = new Square(s.getMinLatitude(), s.getMaxLatitude(), -180, delta - 180);
			return Stream.of(s1, s2).collect(Collectors.toList());
		}
	}

	/**
	 * This method returns a list of squares if an initial square is out only of latitude bounds.
	 * @param s - neighborhood of observation point.
	 */
	private List<Square> getSquare(Square s) {
		if (s.getMaxLatitude() > 90) {
			Square s1 = new Square(s.getMinLatitude(), 90, s.getMinLongitude(), s.getMaxLongitude());
			double minLatitude = 90 + 90 - s.getMaxLatitude();
			return getSquare(minLatitude, 90, s, s1);
		} else {
			// latitude < -90
			Square s1 = new Square(-90, s.getMaxLatitude(), s.getMinLongitude(), s.getMaxLongitude());
			double maxLatitude = -90 - 90 - s.getMinLatitude();
			return getSquare(-90, maxLatitude, s, s1);
		}
	}

	private List<Square> getSquare(double minLatitude, double maxLatitude, Square s, Square s1) {
		double minLongitude = s.getMinLongitude() >= 0 ? s.getMinLongitude() - 180 : 180 + s.getMinLongitude();
		double maxLongitude = s.getMaxLongitude() >= 0 ? s.getMaxLongitude() - 180 : 180 + s.getMaxLongitude();
		if ((minLongitude >= 0 && maxLongitude >= 0) || (minLongitude < 0 && maxLongitude < 0)) {
			Square s2 = new Square(minLatitude, maxLatitude, minLongitude, maxLongitude);
			return Stream.of(s1, s2).collect(Collectors.toList());
		}
		if (minLongitude >= 0 && maxLongitude < 0) {
			Square s2 = new Square(minLatitude, maxLatitude, minLongitude, 180);
			Square s3 = new Square(minLatitude, maxLatitude, -180, maxLongitude);
			return Stream.of(s1, s2, s3).collect(Collectors.toList());
		}
		return Stream.of(s).collect(Collectors.toList());
	}

	/**
	 * This method fills a list with a squares 
	 * if an initial square is out of both latitude an longitude bounds. 
	 * @param minLatitude - the minimal latitude of all result squares.
	 * @param maxLatitude - the minimal latitude of all result squares.
	 * @param s - initial square.
	 */
	private List<Square> getSquareOutOfBound(double minLatitude, double maxLatitude, Square s) {
		if (s.getMinLongitude() < -180) {
			// (-180, max), (180-delta, 180), (opposite square)
			return getPositiveOppositeSquare(minLatitude, maxLatitude, s);
		} else { 
			// (min, 180), (-180-delta, -180), (opposite square)
			// longitude > 180
			return getNegativeOppositeSquare(minLatitude, maxLatitude, s);
		}
	}

	/*
	 * Maybe we could override methods getPositiveOppositeSquare and getNegativeOppositeSquare
	 * as one but now they are more evident.
	 */

	/**
	 * Result square's latitude is in the range (minLatitude, maxLatitude),
	 * longitude is in range
	 * (-180, s.maxLongitude), (180-delta, 180), (opposite square)
	 * @param s - neighborhood of observation point.
	 */
	private List<Square> getPositiveOppositeSquare(double minLatitude, double maxLatitude, Square s) {
		// longitude < -180
		Square s1 = new Square(minLatitude, maxLatitude, -180, s.getMaxLongitude());
		double delta = -180 - s.getMinLongitude();
		Square s2 = new Square(minLatitude, maxLatitude, 180 - delta, 180);
		double minLongitude = s.getMinLongitude() + 180;
		double maxLongitude = s.getMaxLongitude() + 180;
		Square s3 = new Square(minLatitude, maxLatitude, minLongitude, maxLongitude);
		return Stream.of(s1, s2, s3).collect(Collectors.toList());
	}

	/**
	 * Result square's latitude is in the range (minLatitude, maxLatitude),
	 * longitude is in range
	 * (s.minLongitude, 180), (-180-delta, -180), (opposite square)
	 * @param s - neighborhood of observation point.
	 */
	private List<Square> getNegativeOppositeSquare(double minLatitude, double maxLatitude, Square s) {
		// longitude > 180
		Square s1 = new Square(minLatitude, maxLatitude, s.getMinLongitude(), 180);
		double delta = 180 - s.getMaxLongitude();
		Square s2 = new Square(minLatitude, maxLatitude, -180, -180 - delta);
		double minLongitude = s.getMinLongitude() - 180;
		double maxLongitude = s.getMaxLongitude() - 180;
		Square s3 = new Square(minLatitude, maxLatitude, minLongitude, maxLongitude);
		return Stream.of(s1, s2, s3).collect(Collectors.toList());
	}

	private double getDeltaPhi(double hight, double length) {
		if (hight > length) {
			return 0;
		} else {
			// dl = R * phi * Pi / 180
			// length = SQRT( dl^2 + hight^2 )
			// phi^2 = (length^2 - hight^2) * 180^2 / (R^2 * Pi^2)
			double phiSquare = (length * length - hight * hight) * 180 * 180 / (earthRadius * earthRadius * Math.PI * Math.PI);
			return Math.sqrt(phiSquare);
		}
	}

}
