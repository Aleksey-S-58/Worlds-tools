package config.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * This condition returns true if a class 
 * DatabaseObjectLoaderCondition 
 * presents in classpath.
 * @author Aleksey Shishkin
 *
 */
public class DatabaseObjectLoaderCondition implements Condition {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseObjectLoaderCondition.class);

	private static final String OBJECT_LOADER = "service.database.object.loader.ObjectLoader";
	private static final String MESSAGE = "DatabaseObjectLoaderCondition: {}";
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata arg1) {
		boolean result = false;
		try {
			Class.forName(OBJECT_LOADER);
			result = true;
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage());
			result = false;
		}
		LOGGER.debug(MESSAGE, result);
		return result;
	}

}
