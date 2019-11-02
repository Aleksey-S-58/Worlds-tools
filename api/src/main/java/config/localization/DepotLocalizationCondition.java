package config.localization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * This condition returns true if class 
 * localization.depot.DepotLocalizationStrategy 
 * presents in classpath.
 * @author Aleksey Shishkin
 *
 */
public class DepotLocalizationCondition implements Condition {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DepotLocalizationCondition.class);
	
	private static final String DEPOT_LOCALIZATION_STRATEGY = "localization.depot.DepotLocalizationStrategy";
	private static final String MESSAGE = "DepotLocalizationCondition: {}";

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata arg1) {
		boolean result = false;
		try {
			Class.forName(DEPOT_LOCALIZATION_STRATEGY);
			result = true;
		} catch (ClassNotFoundException e) {
			result = false;
		}
		LOGGER.debug(MESSAGE, result);
		return result;
	}

}
