package config.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@Conditional(DatabaseObjectLoaderCondition.class)
@Configuration
@ComponentScan({"service.database.object.loader"})
public class DatabaseObjectLoaderConfig {

}
