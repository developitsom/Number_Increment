/**
 * 
 */
package com.glaucus.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * @author Somendra
 *
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.glaucus")
@PropertySource("classpath:database.properties")
public class ApplicationConfiguration {

	@Autowired
	public Environment environment;

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(environment.getProperty("driver"));
		driverManagerDataSource.setUrl(environment.getProperty("url"));
		driverManagerDataSource.setUsername(environment.getProperty("dbuser"));
		driverManagerDataSource.setPassword(environment.getProperty("dbpassword"));
		driverManagerDataSource.setSchema(environment.getProperty("dbschema"));
		
		return driverManagerDataSource;
	}

	@Bean
	public ViewResolver viewMethod() {

		InternalResourceViewResolver viewRes = new InternalResourceViewResolver();
		viewRes.setViewClass(JstlView.class);
		viewRes.setPrefix("/WEB-INF/folder1/");
		viewRes.setSuffix(".jsp");

		return viewRes;
	}
	
	private static Properties hibernateProperties() {

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.put("hibernate.show_sql", false);
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");

        return hibernateProperties;
    }

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.glaucus.entity" });
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}
	
	//It is used to translate all errors generated during the persistence process (HibernateExceptions, PersistenceExceptions, etc.) into DataAccessException objects
	 @Bean
	   public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	      return new PersistenceExceptionTranslationPostProcessor();
	   }
}