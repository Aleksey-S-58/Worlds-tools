package data;

/**
 * This enumeration contains a list of possible 3DO types.
 * You can specify your own object types in additional enumeration,
 * please implement interface marker {@link TypeMarker}
 * 
 * @author Aleksey Shishkin
 *
 */
public enum ObjectType implements TypeMarker {

	THREE_D_OBJECT, SPRITE;

}
