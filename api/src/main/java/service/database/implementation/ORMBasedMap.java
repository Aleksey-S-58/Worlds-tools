package service.database.implementation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.Description;
import data.Location;
import data.ObjectType;
import dto.ThreeDObject;
import repository.DescriptionRepository;
import repository.LocationRepository;
import service.Localization;
import service.MapService;
import service.SimpleGeolocation;
import service.Square;

/**
 * This class provides map loading from database.
 * @author Aleksey Shishkin
 *
 */
@Service("ormBasedMap")
public class ORMBasedMap implements MapService {
	
	public static final String UNKNOWN_OBJECT = "Unknown";
	
	private LocationRepository locationsRepository;
	
	private DescriptionRepository descriptionsRepository;
	
	private Localization localization;

	public ORMBasedMap(LocationRepository locationsRepository, 
			DescriptionRepository descriptionsRepository, Localization localization) {
		super();
		this.locationsRepository = locationsRepository;
		this.descriptionsRepository = descriptionsRepository;
		this.localization = localization;
	}
	
	public void setLocalization(Localization localization) {
		this.localization = localization;
	}
	
	private List<Long> getDescriptionsIdentifiers(List<Location> list) {
		return list.stream().map(location -> location.getObjectId())
				.distinct().collect(Collectors.toList());
	}
	
	/**
	 * This method looks for a nearest object.
	 * @param distance - a distance between an observer and an object.
	 * @param objectId - specified object identifier.
	 * @param descriptions - list of all descriptions in a square.
	 * @return description of a nearest object with a specified objectId.
	 */
	private Description getVisibleObject(double distance, long objectId, List<Description> descriptions) {
		return descriptions.stream()
				.filter(description -> description.getObjectId() == objectId && distance <= description.getRadius())
				.min((d1, d2) -> {
					if (d1.getRadius() == d2.getRadius()) {
						return 0;
					} else {
						return d1.getRadius() > d2.getRadius() ? 1 : -1;
					}
				}).orElse(new Description(0, UNKNOWN_OBJECT, ObjectType.THREE_D_OBJECT, objectId, Double.MAX_VALUE));
	}

	private List<ThreeDObject> mapToObjects(SimpleGeolocation observer, List<Location> locations, List<Description> descriptions) {
		return locations.stream().map(location -> {
			double distance = observer.getDistance(location);
			Description description = getVisibleObject(distance, location.getObjectId(), descriptions);
			ThreeDObject value = null;
			if (description.getName() != UNKNOWN_OBJECT) {
				value = new ThreeDObject(description.getName(), description.getType(),
						location.getLatitude(), location.getLongitude(), 
						location.getHight(), location.getAlphaX(), 
						location.getAlphaY(), location.getAlphaZ());
			}
			return Optional.ofNullable(value);
		}).filter(o -> o.isPresent()).map(o -> o.get()).collect(Collectors.toList());
	}
	
	private List<Location> getLocations(List<Square> localizations) {
		List<Location> locations = new LinkedList<>();
		for (Square square : localizations) {
			locations.addAll(locationsRepository.getObjectsInLocation(square.getMinLatitude(), 
					square.getMaxLatitude(), square.getMinLongitude(), square.getMaxLongitude()));
		}
		return locations.stream().distinct().collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<ThreeDObject> getObjects(double hight, double latitude, double longitude) {
		List<Square> localizations = localization.getSquare(hight, latitude, longitude);
		List<Location> locations = getLocations(localizations);
		if (!locations.isEmpty()) {
			List<Long> descriptionsIdentifiers = getDescriptionsIdentifiers(locations);
			List<Description> descriptions = descriptionsRepository.getWhichInList(descriptionsIdentifiers);
			return mapToObjects(new SimpleGeolocation(latitude, longitude, hight), 
					locations, descriptions);
		} else {
			return new ArrayList<ThreeDObject>();
		}
	}

}
