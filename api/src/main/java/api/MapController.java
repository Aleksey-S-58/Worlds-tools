package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import data.TypeMarker;
import dto.ThreeDObject;
import service.ApiService;
import service.MapService;
import service.ThreeDObjectService;
import service.TypeService;

/**
 * This controller provides methods that allows:
 * <br>to load supported object types,
 * <br>to load object geometry by objects name,
 * <br>to load material by objects name,
 * <br>to load sprite by name, 
 * <br>to load objects locations by observer location (height, latitude, longitude),
 * <br>to load methods description via OPTIONS method
 * <br>to check health of the service.
 * @author Aleksey Shishkin
 *
 */
@Controller
public class MapController {

	public static final String ALLOW = "Allow";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_LENGTH = "Content-Length";

	private static final String PROJECT_REPOSITORY = "https://github.com/Aleksey-S-58/Worlds-tools.git";
	
	private ThreeDObjectService objectService;
	
	private MapService mapService;
	
	private ApiService apiService;
	
	private TypeService typeService;
	
	public MapController(ThreeDObjectService objectService, 
			@Qualifier("ormBasedMap") MapService mapService, 
			ApiService apiService, TypeService typeService) {
		this.objectService = objectService;
		this.mapService = mapService;
		this.apiService = apiService;
		this.typeService = typeService;
	}
	
	@RequestMapping(value = "/map", method = RequestMethod.GET, produces = "text/html")
	@ResponseBody
	public ResponseEntity<String> authors() {
		return ResponseEntity.ok("Map api is developed by Aleksey Shishkin, Ivan Shishkin. Visit our project on github: " + PROJECT_REPOSITORY);
	}
	
	// HEAD
	
	@RequestMapping(value = "/map", method = RequestMethod.HEAD, produces = "text/html")
	@ResponseBody
	public ResponseEntity<String> head() {
		return ResponseEntity.ok().build();
	}
	
	// OPTIONS
	
	private ResponseEntity<String> options(String allow, String info) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ALLOW, allow);
		headers.add(CONTENT_TYPE, "text/html; charset=UTF-8");
		headers.add(CONTENT_LENGTH, String.valueOf(info.length()));
		ResponseEntity<String> response = new ResponseEntity<>(info, headers, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/map", method = RequestMethod.OPTIONS, produces = "text/html")
	@ResponseBody
	public ResponseEntity<String> options() {
		String info = apiService.getApiInfo();
		return options("GET,HEAD,OPTIONS", info);
	}
	
	@RequestMapping(value = "/map/supported/object/types", method = RequestMethod.OPTIONS, produces = "text/html")
	@ResponseBody
	public ResponseEntity<String> getObjectTypesOptions() {
		String info = apiService.getSupportedObjectTypesInfo();
		return options("GET,OPTIONS", info);
	}
	
	@RequestMapping(value = "/map/object", method = RequestMethod.OPTIONS, produces = "text/html")
	@ResponseBody
	public ResponseEntity<String> getObjectsOptions() {
		String info = apiService.getObjectOptions();
		return options("GET,OPTIONS", info);
	}
	
	@RequestMapping(value = "/map/material", method = RequestMethod.OPTIONS, produces = "text/html")
	@ResponseBody
	public ResponseEntity<String> getMaterialOptions() {
		String info = apiService.getMaterialOptions();
		return options("GET,OPTIONS", info);
	}
	
	@RequestMapping(value = "/map/sprite", method = RequestMethod.OPTIONS, produces = "text/html")
	@ResponseBody
	public ResponseEntity<String> getSpriteOptions() {
		String info = apiService.getSpriteOptions();
		return options("GET,OPTIONS", info);
	}

	// Functionality

	@GetMapping(value = "/map/supported/object/types")
	@ResponseBody
	public ResponseEntity<Enum<? extends TypeMarker>[]> getObjectTypes() {
		return ResponseEntity.ok(typeService.getObjectTypes());
	}

	@GetMapping("/map/{hight}/{latitude}/{longitude}")
	@ResponseBody
	public ResponseEntity<List<ThreeDObject>> getObjects(@PathVariable("hight") double hight, 
			@PathVariable("latitude") double latitude, 
			@PathVariable("longitude") double longitude) {
		return ResponseEntity.ok(mapService.getObjects(hight, latitude, longitude));
	}
	
	/**
	 * headers:
	 * <br>Accept-Ranges: bytes
	 * <br>Content-Length: ...
	 * <br>Content-Type: application/x-tgif
	 * @param name - object's name
	 * @return an obj file
	 */
	@RequestMapping(value = "/map/object/{name}", method = RequestMethod.GET, produces = "application/x-tgif")
	@ResponseBody
	public byte[] getObject(@PathVariable("name") String name) {
		return objectService.getObject(name);
	}
	
	/**
	 * headers:
	 * <br>Accept-Ranges: bytes
	 * <br>Content-Length: ...
	 * <br>Content-Type: application/x-tgif, 
	 * image/bmp, image/jpg, image/png, image/tiff, image/gif
	 * @param name - object's name
	 * @return an mtl file
	 */	
	@RequestMapping(path = "/map/material/{name}", method = RequestMethod.GET, 
			produces = {"application/x-tgif", "image/bmp", "image/jpg", "image/png", "image/tiff", "image/gif"})
	@ResponseBody
	public byte[] getMaterial(@PathVariable("name") String name) {
		return objectService.getMaterial(name);
	}
	
	/**
	 * headers:
	 * <br>Accept-Ranges: bytes
	 * <br>Content-Length: ...
	 * <br>Content-Type: application/x-tgif, 
	 * image/bmp, image/jpg, image/png, image/tiff, image/gif
	 * @param name - a name of sprite
	 * @return an png or jpg file
	 */	
	@RequestMapping(path = "/map/sprite/{name}", method = RequestMethod.GET, 
			produces = {"application/x-tgif", "image/bmp", "image/jpg", "image/png", "image/tiff", "image/gif"})
	@ResponseBody
	public byte[] getSprite(@PathVariable("name") String name) {
		return objectService.getSprite(name);
	}

}
