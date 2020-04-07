package config.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import data.ObjectType;
import service.TypeService;
import service.simple.implementation.SimpleTypeService;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@Conditional(SimpleTypeServiceCondition.class)
@Configuration
public class SimpleTypeServiceConfig {

	/**
	 * @return SimpleTypeService with all {@link ObjectType} values as argument.
	 */
	@Bean("simpleTypeService")
	public TypeService buildSimpleTypeService() {
		return new SimpleTypeService(ObjectType.values());
	}

}
