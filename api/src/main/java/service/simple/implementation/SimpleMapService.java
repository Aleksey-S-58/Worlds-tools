package service.simple.implementation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.ThreeDObject;
import service.Localization;
import service.MapService;
import service.Square;

/**
 * This service loads data about map from a JSON file.
 * @author Aleksey Shishkin
 *
 */
public class SimpleMapService implements MapService {

	private ServletContext context;
	
	/**
	 * This file contains all objects on a map in a JSON format.
	 */
	private String mapFileName;
	
	/**
	 * Objects on a map.
	 */
	private List<ThreeDObject> objects;
	
	/**
	 * This strategy detects which objects should be shown to a client.
	 */
	private Localization strategy;
	
	public SimpleMapService(ServletContext context, @Value("${map}") String mapFileName, 
			Localization localization) {
		this.context = context;
		this.mapFileName = mapFileName;
		strategy = localization;
	}
	
	protected List<ThreeDObject> loadObjects(String name) {
		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = context.getResourceAsStream(name);
		try {
			return mapper.readValue(stream, new TypeReference<List<ThreeDObject>>() {});
		} catch (Exception e) {
			System.out.println(e);
			return new ArrayList<ThreeDObject>();
		}
	}
	
	protected List<ThreeDObject> getObjectsInLocation(Square location, List<ThreeDObject> objects) {
		return objects.stream().filter(o -> {
			if (o.getLatitude() < location.getMaxLatitude() && o.getLatitude() > location.getMinLatitude() &&
					o.getLongitude() < location.getMaxLongitude() && o.getLongitude() > location.getMinLongitude()) {
				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList());
	}
	
	protected List<ThreeDObject> getObjectsInLocation(List<Square> locations, List<ThreeDObject> objects) {
		List<ThreeDObject> result = new ArrayList<>();
		for (Square location : locations) {
			result.addAll(getObjectsInLocation(location, objects));
		}
		return result.stream().distinct().collect(Collectors.toList());
	}

	public List<ThreeDObject> getObjects(double hight, double latitude, double longitude) {
		if (objects == null)
			objects = loadObjects('/' + mapFileName);
		// We have to load objects from file lazily as we can't do it during bean initialization because application is not published yet.
		List<Square> locations = strategy.getSquare(hight, latitude, longitude);
		return getObjectsInLocation(locations, objects);
	}

}
