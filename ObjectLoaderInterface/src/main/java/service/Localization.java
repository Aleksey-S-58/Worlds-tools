package service;

import java.util.List;

/**
 * This strategy interface defines a method of square determining.
 * @author Aleksey Shishkin
 *
 */
public interface Localization {

	/**
	 * (latitude, longitude) - coordinates of a center.
	 * @param hight - height of observation point.
	 * @return a list of squares which dimensions depends on height of observation point.
	 */
	public List<Square> getSquare(long hight, double latitude, double longitude);
	
}
