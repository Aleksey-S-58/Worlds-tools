package service.simple.implementation;

import java.util.HashSet;
import java.util.Set;

import data.TypeMarker;
import service.TypeService;

/**
 * This simple implementation stores enumeration 
 * of all registered object types which comes via constructor.
 * 
 * @author Aleksey Shishkin
 *
 */
public class SimpleTypeService implements TypeService {

	private Enum<? extends TypeMarker>[] types;

	@SafeVarargs
	@SuppressWarnings("unchecked")
	public SimpleTypeService(Enum<? extends TypeMarker>[]... types) {
		Set<String> names = new HashSet<>(); // registered type names.
		Set<Enum<? extends TypeMarker>> enumerations = new HashSet<>(); // registered types.
		for (Enum<? extends TypeMarker>[] type : types) {
			addAllNewTypes(names, enumerations, type);
		}
		// As we know this set contains only enumerations which implements TypeMarker interface.
		this.types = enumerations.toArray(new Enum[0]);
	}

	private void addAllNewTypes(Set<String> registeredNames, 
			Set<Enum<? extends TypeMarker>> registeredTypes, 
			Enum<? extends TypeMarker>[] incomeTypes) {
		for (Enum<? extends TypeMarker> value : incomeTypes) {
			if (!registeredNames.contains(value.name())) {
				registeredNames.add(value.name());
				registeredTypes.add(value);
			}
		}
	}

	@Override
	public Enum<? extends TypeMarker>[] getObjectTypes() {
		return types;
	}

}
