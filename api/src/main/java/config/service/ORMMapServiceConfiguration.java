package config.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import repository.DescriptionRepository;
import repository.LocationRepository;
import service.Localization;
import service.MapService;
import service.database.implementation.ORMBasedMap;

/**
 * Configuration which creates ORMBasedMapService bean.
 * This configuration turn on loading of map from database.
 * It can be activated by setting of property {@link ORMMapServiceCondition.NAME} = ORM in property file
 * @author Ivan Shishkin
 *
 */

@Conditional(ORMMapServiceCondition.class)
@Configuration
public class ORMMapServiceConfiguration {
	

	@Bean(name = "ormBasedMap")
	public MapService getOrmBasedMapService(LocationRepository locationsRepository, 
			DescriptionRepository descriptionsRepository, Localization localization) {
		return new ORMBasedMap(locationsRepository, 
				descriptionsRepository, localization);
	}
}

