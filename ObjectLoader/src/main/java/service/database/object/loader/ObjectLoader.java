package service.database.object.loader;

import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ObjectLoader extends AbstractLoader implements ThreeDObjectService {

	private static final String MESSAGE_FORMAT = "geometryRepository: %1$s, materialRepository: %2$s, spriteRepository: %3$s";

	private static final String OBJECT = "object";

	private static final String MATERIAL = "material";

	private static final String SPRITE = "sprite";

	private GeometryRepository geometryRepository;

	private MaterialRepository materialRepository;

	private SpriteRepository spriteRepository;

	public ObjectLoader(GeometryRepository geometryRepository, MaterialRepository materialRepository,
			SpriteRepository spriteRepository) {
		super();
		logger = LoggerFactory.getLogger(ObjectLoader.class);
		if (geometryRepository == null || materialRepository == null || spriteRepository == null) {
			String message = String.format(MESSAGE_FORMAT, geometryRepository, materialRepository, spriteRepository);
			throw new IllegalArgumentException(message);
		}
		this.geometryRepository = geometryRepository;
		this.materialRepository = materialRepository;
		this.spriteRepository = spriteRepository;
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
