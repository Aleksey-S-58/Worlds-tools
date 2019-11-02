package config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * This configuration defines base packages to scan.
 * @author Aleksey Shishkin
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan({"api", "service.simple.implementation", "config.property", 
	"config.database", "config.service", "config.localization"})
public class WebConfiguration {
	
}
