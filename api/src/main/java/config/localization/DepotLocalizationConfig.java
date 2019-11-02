package config.localization;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@Conditional(DepotLocalizationCondition.class)
@ComponentScan("localization.depot")
@Configuration
public class DepotLocalizationConfig {

}
