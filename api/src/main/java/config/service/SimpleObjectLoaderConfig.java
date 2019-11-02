package config.service;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import service.ThreeDObjectService;
import service.simple.implementation.ObjectService;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@Conditional(SimpleObjectLoaderCondition.class)
@Configuration
public class SimpleObjectLoaderConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleObjectLoaderConfig.class);

	@Bean("objectService")
	public ThreeDObjectService getSimpleObjectService(ServletContext context) {
		LOGGER.info("Create a simple object service!");
		return new ObjectService(context);
	}

}
