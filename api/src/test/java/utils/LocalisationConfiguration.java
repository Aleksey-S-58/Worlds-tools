package utils;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import service.Localization;
import service.Square;
/**
 * 
 * @author Ivan Shishkin
 *
 */
@Configuration
public class LocalisationConfiguration {
	@Bean
	public Localization getLocalization() {
		return new Localization() {
			
			@Override
			public List<Square> getSquare(double hight, double latitude, double longitude) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
