package service;

import java.util.List;

import dto.ThreeDObject;

/**
 * This interface provides information about map.
 * @author Aleksey Shishkin.
 *
 */
public interface MapService {

	/**
	 * The method returns a list of objects that are in a specified square.
	 * latitude, longitude - coordinates of a central point.
	 * 
	 */
	public List<ThreeDObject> getObjects(double hight, double latitude, double longitude);
	
}
