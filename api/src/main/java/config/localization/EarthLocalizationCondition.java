package config.localization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * This condition returns true if class 
 * localization.earth.SimpleLocalizationStrategy 
 * presents in classpath.
 * @author Aleksey Shishkin
 *
 */
public class EarthLocalizationCondition implements Condition {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EarthLocalizationCondition.class);
	
	private static final String EARTH_LOCALIZATION_STRATEGY = "localization.earth.SimpleLocalizationStrategy";
	private static final String MESSAGE = "EarthLocalizationCondition: {}";

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata arg1) {
		boolean result = false;
		try {
			Class.forName(EARTH_LOCALIZATION_STRATEGY);
			result = true;
		} catch (ClassNotFoundException e) {
			result = false;
		}
		LOGGER.debug(MESSAGE, result);
		return result;
	}

}
