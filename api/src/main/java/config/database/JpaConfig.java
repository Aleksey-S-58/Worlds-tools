package config.database;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@Configuration
@EnableJpaRepositories({"repository"})
@ComponentScan({"service.database.implementation"})
public class JpaConfig {
	
	private static final String PACKAGES_TO_SCAN = "data";
	
	public static final Properties JPA_PROPERTIES;
	static {
		JPA_PROPERTIES = new Properties();
		JPA_PROPERTIES.put("hibernate.max_fetch_depth", "3");
		JPA_PROPERTIES.put("hibernate.jdbc.fetch_size", "50");
		JPA_PROPERTIES.put("hibernate.jdbc.batch_size", "10");
		JPA_PROPERTIES.put("hibernate.show_sql", "true");
		JPA_PROPERTIES.put("hibernate.ddl-auto", "false");
		JPA_PROPERTIES.put("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation", "true");
	}

	@Bean(name = "dataSource", destroyMethod = "close")
	public DataSource getDataSource(Environment environment) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getProperty("jdbc.driver.name"));
		dataSource.setUrl(environment.getProperty("jdbc.url"));
		dataSource.setUsername(environment.getProperty("jdbc.username"));
		dataSource.setPassword(environment.getProperty("jdbc.password"));
		return dataSource;
	}
	
	@Bean(name = "jpaVendorAdapter")
	public JpaVendorAdapter getVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.POSTGRESQL); // TODO Think about how to move it out to property file, if necessary.
		adapter.setGenerateDdl(false);
		return adapter;
	}
	
	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory getEntityManagerFactory(DataSource dataSource, JpaVendorAdapter adapter, 
			Environment environment) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(adapter);
		factory.setPackagesToScan(PACKAGES_TO_SCAN);
		factory.setDataSource(dataSource);
		Properties jpaProperties = new Properties(JPA_PROPERTIES);
		// Add dialect from property file.
		jpaProperties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		factory.setJpaProperties(jpaProperties); // hibernate properties.
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager getTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}
}
