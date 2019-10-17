package config.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * This condition returns true if configuration 
 * databaseObjectLoaderConfig presents in spring context.
 * @author Aleksey Shishkin
 *
 */
public class SimpleObjectLoaderCondition implements Condition {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleObjectLoaderCondition.class);
	private static final String DATABASE_OBJECT_LOADER_CONFIG = "databaseObjectLoaderConfig";
	private static final String MESSAGE = "SimpleObjectLoaderCondition: {}";

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata arg1) {
		boolean flag = context.getBeanFactory().containsBean(DATABASE_OBJECT_LOADER_CONFIG);
		LOGGER.debug(MESSAGE, !flag);
		return !flag;
	}

}
