package config.service;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * This condition arranges creating of a default TypeService bean.
 * <br>Default TypeService bean can be disabled by entering 
 * property {@value #TYPE_SERVICE_CONFIGURATION} of value CUSTOM
 * <br>Enumeration {@link TypeServiceConfiguration} contains 
 * all possible values of the property.
 * 
 * @author Aleksey Shishkin
 *
 */
public class SimpleTypeServiceCondition implements Condition {
	
	public static enum TypeServiceConfiguration {
		DEFAULT, CUSTOM;
	}
	
	/**
	 * Property name.
	 */
	public static final String TYPE_SERVICE_CONFIGURATION = "type.service.configuration";

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata arg1) {
		String configuration = context.getEnvironment().getProperty(TYPE_SERVICE_CONFIGURATION, 
				TypeServiceConfiguration.DEFAULT.name());
		return TypeServiceConfiguration.DEFAULT.name().equals(configuration.toUpperCase());
	}

}
