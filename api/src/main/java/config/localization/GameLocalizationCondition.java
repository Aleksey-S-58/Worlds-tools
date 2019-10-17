package config.localization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * This condition returns true if class 
 * localization.game.GameLocalizationStrategy 
 * presents in classpath.
 * @author Aleksey Shishkin
 *
 */
public class GameLocalizationCondition implements Condition {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GameLocalizationCondition.class);
	
	private static final String GAME_LOCALIZATION_STRATEGY = "localization.game.GameLocalizationStrategy";
	private static final String MESSAGE = "GameLocalizationCondition: {}";

	@Override
	public boolean matches(ConditionContext arg0, AnnotatedTypeMetadata arg1) {
		boolean result = false;
		try {
			Class.forName(GAME_LOCALIZATION_STRATEGY);
			result = true;
		} catch (ClassNotFoundException e) {
			result = false;
		}
		LOGGER.debug(MESSAGE, result);
		return result;
	}

}
