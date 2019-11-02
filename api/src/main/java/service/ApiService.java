package service;

/**
 * This interface defines an informational methods of an api.
 * 
 * @author Aleksey Shishkin
 *
 */
public interface ApiService {

	/**
	 * 
	 * The method returns an information about api's methods and how they could be used.
	 * 
	 */
	public String getApiInfo();
	
	/**
	 * The method returns a list of all possible object types.
	 */
	public String getSupportedObjectTypesInfo();
	
	/**
	 * The method returns an information about request for an objects geometry.
	 */
	public String getObjectOptions();
	
	/**
	 * The method returns an information about request for an objects material.
	 */
	public String getMaterialOptions();
	
	/**
	 * The method returns an information about request for a sprite.
	 */
	public String getSpriteOptions();
	
}
