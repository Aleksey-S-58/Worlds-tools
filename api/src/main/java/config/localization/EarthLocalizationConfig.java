package config.localization;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@Configuration
@Conditional(EarthLocalizationCondition.class)
@ComponentScan("localization.earth")
public class EarthLocalizationConfig {

}
