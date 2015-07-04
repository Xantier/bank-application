package com.hallila.teller.config;

import com.hallila.teller.App;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = App.class, includeFilters = @ComponentScan.Filter(Controller.class), useDefaultFilters = false)
public class PersistenceConfig {

   @Bean
   public SessionFactory sessionFactory() {
      LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
      builder.scanPackages("com.hallila.teller.entity").addProperties(getHibernateProperties());
      return builder.buildSessionFactory();
   }

   private Properties getHibernateProperties() {
      Properties prop = new Properties();
      prop.put("hibernate.hbm2ddl.auto", "create-drop");
      prop.put("hibernate.format_sql", "true");
      prop.put("hibernate.show_sql", "true");
      prop.put("hibernate.dialect",
            "org.hibernate.dialect.H2Dialect");
      return prop;
   }

   @Bean(name = "dataSource")
   public DataSource dataSource() {
      HikariConfig config = new HikariConfig();
      config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
      config.addDataSourceProperty("url", "jdbc:h2:mem:test");
      config.setUsername("sa");
      config.setPassword("sa");
      return new HikariDataSource(config);
   }

   @Bean
   public HibernateTransactionManager txManager() {
      return new HibernateTransactionManager(sessionFactory());
   }

}
