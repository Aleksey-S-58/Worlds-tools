package service.database.object.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import data.AbstractObject;
import repository.GeometryRepository;
import repository.MaterialRepository;
import repository.SpriteRepository;
import service.ThreeDObjectService;

import java.lang.IllegalArgumentException;
import java.util.Optional;

/**
 * This implementation uses relational database to store 3DO.
 * @author Aleksey Shishkin
 *
 */
@Service
public class ObjectLoader implements ThreeDObjectService {
	
	private static final String MESSAGE_FORMAT = "geometryRepository: %1$s, materialRepository: %2$s, spriteRepository: %3$s";
	
	private static final String DEBUG_MESSAGE = "<--------- Database object loader loads {} by name: {} ------------>";
	
	private static final String OBJECT = "object";
	
	private static final String MATERIAL = "material";
	
	private static final String SPRITE = "sprite";
	
	private static final char DOT = '.';
	
	private static final char UNDERLINE = '_';
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectLoader.class);
	
	private GeometryRepository geometryRepository;
	
	private MaterialRepository materialRepository;
	
	private SpriteRepository spriteRepository;
	
	public ObjectLoader(GeometryRepository geometryRepository, MaterialRepository materialRepository,
			SpriteRepository spriteRepository) {
		super();
		if (geometryRepository == null || materialRepository == null || spriteRepository == null) {
			String message = String.format(MESSAGE_FORMAT, geometryRepository, materialRepository, spriteRepository);
			throw new IllegalArgumentException(message);
		}
		this.geometryRepository = geometryRepository;
		this.materialRepository = materialRepository;
		this.spriteRepository = spriteRepository;
	}
	
	private String replaceDot(String str) {
		if (str.indexOf(DOT) > -1) {
			return str.replace(DOT, UNDERLINE);
		} else {
			return str;
		}
	}

	private <T extends AbstractObject> byte[] getBytes(Optional<T> o) {
		if (o == null || !o.isPresent()) {
			LOGGER.warn("Object wasn't found!");
			return new byte[0];
		} else {
			return o.get().getBytes();
		}
	}

	private <E extends AbstractObject, T extends CrudRepository<E, String>> 
	byte[] loadBytes(String name, T repsitory, String debugMessage) {
		LOGGER.debug(DEBUG_MESSAGE, debugMessage, name);
		if (StringUtils.isEmpty(name)) {
			LOGGER.warn("Empty name {}!", name);
			return new byte[0];
		} else {
			return getBytes(repsitory.findById(replaceDot(name)));
		}
	}

	@Transactional
	public byte[] getObject(String name) {
		return loadBytes(name, geometryRepository, OBJECT);
	}

	@Transactional
	public byte[] getMaterial(String name) {
		return loadBytes(name, materialRepository, MATERIAL);
	}

	@Transactional
	@Override
	public byte[] getSprite(String name) {
		return loadBytes(name, spriteRepository, SPRITE);
	}

}
