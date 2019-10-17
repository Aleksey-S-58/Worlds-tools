package config.localization;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@Conditional(GameLocalizationCondition.class)
@ComponentScan("localization.game")
@Configuration
public class GameLocalizationConfig {

}
