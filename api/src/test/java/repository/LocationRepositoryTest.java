package repository;

import org.junit.Assert;
import org.junit.Test;
//import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import config.database.JpaConfig;
import config.property.PropertyConfigurer;
//import utils.IntegrationTest;
import utils.LocalisationConfiguration;
/**
 * 
 * @author Ivan Shishkin
 *
 */
//@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class, LocalisationConfiguration.class, PropertyConfigurer.class}, loader = AnnotationConfigContextLoader.class)
public class LocationRepositoryTest /*implements IntegrationTest*/ {
	// TODO Find out what's wrong when the tests are launched in integration phase.
	
	@Autowired
	private LocationRepository repository;
	
	@Test
	public void testGetNextObjectId() {
		long i = repository.getNextObjectId();
		Assert.assertEquals(i + 1, repository.getNextObjectId());
	}
	
	@Test
	public void testGetNextLocationId() {
		long i = repository.getNextLocationId();
		Assert.assertEquals(i + 1, repository.getNextLocationId());
	} 	
}
