package config.service;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import service.Localization;
import service.MapService;
import service.simple.implementation.SimpleMapService;
/**
 * Configuration which creates simpleMapService bean.
 * This configuration turn on loading of map from JSON file.
 * It can be activated by setting of property {@link SimpleMapServiceCondition.NAME} = JSON in property file
 * @author Ivan Shishkin
 *
 */

@Conditional(SimpleMapServiceCondition.class)
@Configuration
public class SimpleMapServiceConfiguration {
	

	@Bean(name = "simpleMapService")
	public MapService getSimpleMapService(ServletContext context, @Value("${map}") String mapFileName, 
			Localization localization) {
		return new SimpleMapService(context, mapFileName, localization);
	}
}
