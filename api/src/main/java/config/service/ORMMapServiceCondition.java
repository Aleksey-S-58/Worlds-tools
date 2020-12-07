package config.service;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * The condition below is intended to specify whether {@link ORMBasedMap} should
 * be uploaded to Spring Context or not.
 * If property file contains value {@value #NAME} = {@value #ORM} then it returns true
 * and {@link ORMBasedMap} will be uploaded.
 * @author Ivan Shishkin
 *
 */
public class ORMMapServiceCondition implements Condition {
	private static final String ORM = "ORM";
	private static final String NAME = "map-service-type";
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		try {
			Properties properties = new Properties();
			properties.load(context.getClassLoader().getResourceAsStream("application.properties"));
			return ORM.equals(properties.getProperty(NAME));
		} catch (IOException e) {
			return false;
		}
	}

}
