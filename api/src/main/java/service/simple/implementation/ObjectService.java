package service.simple.implementation;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.slf4j.LoggerFactory;

import service.ThreeDObjectService;

/**
 * This simple implementation is intended only for test mode 
 * to demonstrate an ability of the service while storing data in a
 * database is not ready.
 * This implementation reads data from files.
 * 
 * @author Aleksey Shishkin
 *
 */
public class ObjectService extends ResourceReader implements ThreeDObjectService {
	
	private static final String SAMPLES = "samples";
	private static final String MATERIAL_EXTENSION = ".mtl";
	private static final String GEOMETRY_EXTENSION = ".obj";
	private static final String SPRITE_EXTENSION = ".png";
	
	public ObjectService(ServletContext context) {
		this.context = context;
		logger = LoggerFactory.getLogger(ObjectService.class);
	}
	
	protected String getResourcePath(String name, String extension) {
		return '/' + SAMPLES + '/' + name + extension;
	}

	public byte[] getObject(String name) {
		try {
			return readFile(getResourcePath(name, GEOMETRY_EXTENSION));
		} catch (IOException e) {
			System.out.println(e);
			return new byte[0];
		}
	}

	public byte[] getMaterial(String name) {
		try {
			return readFile(getResourcePath(name, MATERIAL_EXTENSION));
		} catch (IOException e) {
			System.out.println(e);
			return new byte[0];
		}
	}

	@Override
	public byte[] getSprite(String name) {
		try {
			return readFile(getResourcePath(name, SPRITE_EXTENSION));
		} catch (IOException e) {
			System.out.println(e);
			return new byte[0];
		}
	}

}
