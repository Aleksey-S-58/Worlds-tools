package service.database.object.loader;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.StringUtils;

import data.AbstractObject;

/**
 * This abstract class provides loading binary data from a database.
 * 
 * The main goal was to simplify development of services 
 * which could load binary data saved as AbstractObject based entities.
 * For example sound tracks and animations basically 
 * are not necessary for 3D map, but now services to manage such entities 
 * can be easily developed.
 * (See {@link ObjectLoader})
 * 
 * @author Aleksey Shishkin
 *
 */
public abstract class AbstractLoader {

	private static final String DEBUG_MESSAGE = "<--------- Database object loader loads {} by name: {} ------------>";

	public static final char DOT = '.';

	public static final char UNDERLINE = '_';

	protected static Logger logger;

	/**
	 * This method replaces the {@value #DOT} symbol by {@value #UNDERLINE}
	 */
	protected String replaceDot(String str) {
		if (str.indexOf(DOT) > -1) {
			return str.replace(DOT, UNDERLINE);
		} else {
			return str;
		}
	}

	/**
	 * 
	 * @param o - Optional.ofNullable(...), the argument can also be null.
	 * @return either an array of bytes from the entity 
	 * or an empty array if an entity is not present or is null.
	 */
	protected <T extends AbstractObject> byte[] getBytes(Optional<T> o) {
		if (o == null || !o.isPresent()) {
			logger.warn("Object wasn't found!");
			return new byte[0];
		} else {
			return o.get().getBytes();
		}
	}

	/**
	 * This method requests a binary data from database by the entity's name.
	 * @param <E> - some AbstractObject based entity type.
	 * @param <T> - a repository type to load an appropriate entity.
	 * @param name - entitie's name.
	 * @param repsitory - a CrudRepository to load data from a database.
	 * @param debugMessage - in a debug mode a message in the following 
	 * format {@value #DEBUG_MESSAGE} will be shown. 
	 * A debug message is a first parameter in it.
	 * @return either an array of bytes from the entity 
	 * or an empty array if an entity wasn't found.
	 */
	protected <E extends AbstractObject, T extends CrudRepository<E, String>> 
	byte[] loadBytes(String name, T repsitory, String debugMessage) {
		logger.debug(DEBUG_MESSAGE, debugMessage, name);
		if (StringUtils.isEmpty(name)) {
			logger.warn("Empty name {}!", name);
			return new byte[0];
		} else {
			return getBytes(repsitory.findById(replaceDot(name)));
		}
	}

}
