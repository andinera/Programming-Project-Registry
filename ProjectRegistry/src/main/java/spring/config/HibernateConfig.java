package spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.Properties;
import org.apache.commons.dbcp.BasicDataSource;

import spring.model.*;


@Configuration
@EnableTransactionManagement
@ComponentScans(value= {@ComponentScan("spring.dao"), @ComponentScan("spring.service")})
public class HibernateConfig {

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("spring.model");
		
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		hibernateProperties.setProperty("hibernate.show_sql", "false");
		hibernateProperties.setProperty("hibernate.format_sql", "true");
//		hibernateProperties.setProperty("hibernate.generate_statistics", "true");
		
		sessionFactory.setHibernateProperties(hibernateProperties);
		sessionFactory.setAnnotatedClasses(User.class, 
										   UserRole.class, 
										   Idea.class, 
										   Comment.class,
										   Development.class);
		return sessionFactory;
	}
	
	@Bean(name="dataSource")
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=MST");
		dataSource.setUsername("shane");
		dataSource.setPassword("Go0b3r@#");
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager hibernateTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
}
