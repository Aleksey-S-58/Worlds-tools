package config.property;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * This configuration defines property files that will be used.
 * @author Aleksey Shishkin
 *
 */
@Configuration
@PropertySource(value = {"classpath:application.properties", "classpath:jpa.properties"})
public class PropertyConfigurer {

}
