package service;

/**
 * This interface provides 3DO loading.
 * @author Aleksey Shishkin.
 *
 */
public interface ThreeDObjectService {

	/**
	 * Load 3DO of an obj format.
	 * @param name - object name.
	 * @return 3DO file as an array of byte.
	 */
	public byte[] getObject(String name);
	
	/**
	 * Load mtl file of a specified object.
	 * @param name - object name.
	 * @return mtl file as an array of byte.
	 */
	public byte[] getMaterial(String name);
	
	/**
	 * Load a specified sprite.
	 * @param name - sprite name.
	 * @return byte representation of sprite.
	 */
	public byte[] getSprite(String name);
	
}
