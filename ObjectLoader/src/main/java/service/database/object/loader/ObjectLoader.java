package service.database.object.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.AbstractObject;
import repository.GeometryRepository;
import repository.MaterialRepository;
import repository.SpriteRepository;
import service.ThreeDObjectService;

import java.lang.IllegalArgumentException;

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

	private byte[] getBytes(AbstractObject o) {
		if (o == null) {
			LOGGER.warn("Object not found!");
			return new byte[0];
		} else {
			return o.getBytes();
		}
	}

	@Transactional
	public byte[] getObject(String name) {
		LOGGER.debug(DEBUG_MESSAGE, OBJECT, name);
		return getBytes(geometryRepository.findOne(replaceDot(name)));
	}

	@Transactional
	public byte[] getMaterial(String name) {
		LOGGER.debug(DEBUG_MESSAGE, MATERIAL, name);
		return getBytes(materialRepository.findOne(replaceDot(name)));
	}

	@Transactional
	@Override
	public byte[] getSprite(String name) {
		LOGGER.debug(DEBUG_MESSAGE, SPRITE, name);
		return getBytes(spriteRepository.findOne(replaceDot(name)));
	}

}
