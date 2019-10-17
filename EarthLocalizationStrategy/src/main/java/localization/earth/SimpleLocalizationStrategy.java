package localization.earth;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import service.Localization;
import service.Square;

/**
 * latitude (-90) - 0 (source), 0 - (+90) north
 * longitude (-180) - 0 (east), 0 - (+180) west
 * 
 * @author Aleksey Shishkin
 */
@Service
public class SimpleLocalizationStrategy implements Localization {
	
	private static final double RADIUS = 100; // meters.
	
	private static final double EARTH_RADIUS = 6371D; // km.
	
	/**
	 * latitude (-90) - 0 (source), 0 - (+90) north
	 * longitude (-180) - 0 (east), 0 - (+180) west
	 */

	@Override
	public List<Square> getSquare(long hight, double latitude, double longitude) {
		double dPhi = getDeltaPhi(hight, RADIUS);
		Square s = new Square(latitude - dPhi, latitude + dPhi, longitude - dPhi, longitude + dPhi);
		if (s.getMinLatitude() >= -90 && s.getMaxLatitude() <= 90) {
			if (s.getMinLongitude() >= -180 && s.getMaxLongitude() <= 180) {
				// The first case if we don't reach bounds by latitude and longitude.
				return Stream.of(s).collect(Collectors.toList());
			} else {
				return getSquareOutOfLongitudeBound(s);
			}
		} else {
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
	 */
	private List<Square> getSquare(/*double minLatitude, double maxLatitude, */Square s) {
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
			Square s1 = new Square(minLatitude, maxLatitude, -180, s.getMaxLongitude());
			double delta = -180 - s.getMinLongitude();
			Square s2 = new Square(minLatitude, maxLatitude, 180 - delta, 180);
			double minLongitude = s.getMinLongitude() + 180;
			double maxLongitude = s.getMaxLongitude() + 180;
			Square s3 = new Square(minLatitude, maxLatitude, minLongitude, maxLongitude);
			return Stream.of(s1, s2, s3).collect(Collectors.toList());
		} else {
			// longitude > 180
			Square s1 = new Square(minLatitude, maxLatitude, s.getMinLongitude(), 180);
			double delta = 180 - s.getMaxLongitude();
			Square s2 = new Square(minLatitude, maxLatitude, -180, -180 - delta);
			double minLongitude = s.getMinLongitude() - 180;
			double maxLongitude = s.getMaxLongitude() - 180;
			Square s3 = new Square(minLatitude, maxLatitude, minLongitude, maxLongitude);
			return Stream.of(s1, s2, s3).collect(Collectors.toList());
		}
	}
	
	private double getDeltaPhi(long hight, double length) {
		if (hight > length) { 
			return 0;
		} else {
			// dl = R * phi * Pi / 180
			// length = SQRT( dl^2 + hight^2 )
			// phi^2 = (length^2 - hight^2) * 180^2 / (R^2 * Pi^2)
			double phiSquare = (length * length - hight * hight) * 180 * 180 / (100000 * EARTH_RADIUS * EARTH_RADIUS * Math.PI * Math.PI);
			return Math.sqrt(phiSquare);
		}
	}

}
