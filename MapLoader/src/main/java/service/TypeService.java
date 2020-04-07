package service;

import data.TypeMarker;

/**
 * This interface defines a method which should return enumeration of all possible object types.
 * 
 * @author Aleksey Shishkin
 *
 */
public interface TypeService {

	public Enum<? extends TypeMarker>[] getObjectTypes();

}
